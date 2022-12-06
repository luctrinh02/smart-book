package com.dantn.bookStore.api;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillCreateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Cart;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.BillDetailService;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.CartService;
import com.dantn.bookStore.services.UserBuyService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.TranSnUltil;

@RestController
public class BillApi {
	private BillService billService;
	private BillDetailService detailService;
	private UserService userService;
	private BookService bookService;
	private CartService cartService;
	private BillStatusService statusService;
	private UserBuyService buyService;

	public BillApi(BillService billService, BillDetailService detailService, UserService userService,
			BookService bookService, CartService cartService, BillStatusService statusService,
			UserBuyService buyService) {
		super();
		this.billService = billService;
		this.detailService = detailService;
		this.userService = userService;
		this.bookService = bookService;
		this.cartService = cartService;
		this.statusService = statusService;
		this.buyService = buyService;
	}

	@GetMapping("/api/bill")
	public ResponseEntity<?> history(@RequestParam(name = "page", defaultValue = "0") Integer pageNum,
			@RequestParam(name = "transn", required = false) String transn, Principal principal) {
		User user = userService.getByEmail(principal.getName());
		if (transn == null || "".equals(transn)) {
			Page<Bill> page = billService.getByUser(user, pageNum);
			HashMap<String, Object> map = DataUltil.setData("ok", page);
			return ResponseEntity.ok(map);
		} else {
			Page<Bill> bill = billService.getByTranSn(user, transn, pageNum);
			HashMap<String, Object> map = DataUltil.setData("ok", bill);
			return ResponseEntity.ok(map);
		}
	}

	@GetMapping("/api/bill/{id}")
	public ResponseEntity<?> detail(@PathVariable(name = "id") Integer id, Principal principal) {
		Bill bill = billService.getById(id);
		if (bill.getUser().getEmail().equals(principal.getName())) {
			List<BillDetail> list = detailService.getByBill(bill);
			HashMap<String, Object> map = DataUltil.setData("ok", list);
			return ResponseEntity.ok(map);
		} else {
			HashMap<String, Object> map = DataUltil.setData("error", "Bạn không có quyền xem hóa đơn này");
			return ResponseEntity.ok(map);
		}
	}

	@PostMapping("/api/bill")
	public ResponseEntity<?> add(@RequestBody BillCreateRequest request, Principal principal) {
		List<CartPK> cartPKs = request.getCartPKs();
		User user = userService.getByEmail(principal.getName());
//		List<CartPK> pks=Arrays.asList(cartPKs);
		List<Cart> carts = cartService.getByIds(cartPKs);
		for (Cart cart : carts) {
			if (cart.getAmount() > cart.getBook().getAmount()) {
				// @formatter:off
				StringBuilder builder=new StringBuilder().append("Sách ")
						.append(cart.getBook().getName()).append(" chỉ còn ")
						.append(cart.getBook().getAmount());
				// @formatter:on
				HashMap<String, Object> map = DataUltil.setData("error", builder.toString());
				return ResponseEntity.ok(map);
			}
		}
		Bill bill = new Bill();
		bill.setCreatedTime(new Date());
		bill.setMessage("");
		bill.setTranSn(TranSnUltil.getTranSn());
		bill.setUser(user);
		bill.setStatus(BillStatusSingleton.getInstance(statusService).get(0));
		bill = billService.save(bill);
		BigDecimal bookMoney = BigDecimal.ZERO;
		for (Cart cart : carts) {
			Book book = cart.getBook();
			BillDetailPK detailPK = new BillDetailPK();
			detailPK.setBillId(bill.getId());
			detailPK.setBookId(book.getId());
			BillDetail detail = new BillDetail();
			detail.setBillDetailPK(detailPK);
			detail.setAmount(cart.getAmount());
			detail.setAvailable(cart.getAmount());
			book.setAmount(book.getAmount() - cart.getAmount());
			detail.setBill(bill);
			detail.setBook(book);
			detail.setIsComment(false);
			detail.setAvailable(cart.getAmount());
			detail.setPrice(
					book.getPrice().multiply(new BigDecimal(100 - book.getDiscount())).divide(new BigDecimal(100)));
			detailService.save(detail);
			book.setSaleAmount(cart.getAmount() + book.getSaleAmount());
			bookService.save(book);
			cartService.delete(cart.getCartPK());
			bookMoney = bookMoney.add(detail.getPrice().multiply(new BigDecimal(cart.getAmount())));
			buyService.save(book);
		}
		bill.setBookMoney(bookMoney);
		bill.setTransportFee(request.getTransportFee());
		// xử lý mã khuyến mãi ở đây

		//
		BigDecimal total = bookMoney.add(request.getTransportFee());

		if (total.compareTo(new BigDecimal(3600000)) >= 0) {
			total = total.subtract(new BigDecimal(500000));
		} else if (total.compareTo(new BigDecimal(2400000)) >= 0) {
			total = total.subtract(new BigDecimal(300000));
		} else if (total.compareTo(new BigDecimal(1200000)) >= 0) {
			total = total.subtract(new BigDecimal(120000));
		}

		bill.setTotalMoney(total);
		billService.save(bill);
		HashMap<String, Object> map = DataUltil.setData("ok", "Đặt hàng thành công");
		return ResponseEntity.ok(map);
	}

	@PutMapping("/api/bill/{id}")
	public ResponseEntity<?> cancel(@PathVariable("id") Integer id, @RequestBody String mesage) {
		if (mesage == null || mesage.length() == 0) {
			HashMap<String, Object> map = DataUltil.setData("error", "Vui lòng nhập message");
			return ResponseEntity.ok(map);
		} else {
			BillStatus status = BillStatusSingleton.getInstance(statusService).get(2);
			Bill b = billService.getById(id);
			if (b.getStatus().getId() == 1) {
				b.setMessage(mesage);
				b.setStatus(status);
				b.setUpdatedTime(new Date());
				billService.save(b);
				HashMap<String, Object> map = DataUltil.setData("ok", "Hủy đơn thành công");
				return ResponseEntity.ok(map);
			} else {
				HashMap<String, Object> map = DataUltil.setData("error",
						"Bạn không thể hủy đơn do đơn đã được hủy hoặc chấp nhận");
				return ResponseEntity.ok(map);
			}
		}
	}
}
