package com.dantn.bookStore.api;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillCreateRequest;
import com.dantn.bookStore.dto.request.BillStatusRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.Cart;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.BillDetailService;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.CartService;
import com.dantn.bookStore.services.PaymentTypeService;
import com.dantn.bookStore.services.ShipmentService;
import com.dantn.bookStore.services.TransportTypeService;
import com.dantn.bookStore.services.UserBuyService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.services.WardService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.PaymentTypeSingleton;
import com.dantn.bookStore.ultilities.TranSnUltil;
import com.dantn.bookStore.ultilities.TransportTypeSingleton;

@RestController
public class BillApi {
	private BillService billService;
	private BillDetailService detailService;
	private UserService userService;
	private BookService bookService;
	private CartService cartService;
	private BillStatusService statusService;
	private UserBuyService buyService;
	private ShipmentService shipmentService;
	private TransportTypeService transportTypeService;
	private PaymentTypeService paymentTypeService;
	private WardService wardService;

	public BillApi(BillService billService, BillDetailService detailService, UserService userService,
			BookService bookService, CartService cartService, BillStatusService statusService,
			UserBuyService buyService, ShipmentService shipmentService, TransportTypeService transportTypeService,
			PaymentTypeService paymentTypeService, WardService wardService) {
		super();
		this.billService = billService;
		this.detailService = detailService;
		this.userService = userService;
		this.bookService = bookService;
		this.cartService = cartService;
		this.statusService = statusService;
		this.buyService = buyService;
		this.shipmentService = shipmentService;
		this.transportTypeService = transportTypeService;
		this.paymentTypeService = paymentTypeService;
		this.wardService = wardService;
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
			HashMap<String, Object> map = DataUltil.setData("error", "B???n kh??ng c?? quy???n xem h??a ????n n??y");
			return ResponseEntity.ok(map);
		}
	}

	@PostMapping("/api/payment")
	public ResponseEntity<?> payment(@RequestBody BillCreateRequest request) {
		List<CartPK> cartPKs = request.getCartPKs();
		List<Cart> carts = cartService.getByIds(cartPKs);
		return ResponseEntity.ok(carts);
	}

	@PostMapping("/api/bill/before")
	public ResponseEntity<?> beforePay(@RequestBody @Valid BillCreateRequest request, BindingResult result) {
		List<CartPK> cartPKs = request.getCartPKs();
		List<Cart> carts = cartService.getByIds(cartPKs);
		for (Cart cart : carts) {
			if (cart.getAmount() > cart.getBook().getAmount()) {
				// @formatter:off
				StringBuilder builder=new StringBuilder().append("S???n ph???m ")
						.append(cart.getBook().getName()).append(" ch??? c??n ")
						.append(cart.getBook().getAmount()).append(" cu???n.");
				// @formatter:on
				HashMap<String, Object> map = DataUltil.setData("max", builder.toString());
				return ResponseEntity.ok(map);
			}
		}
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("error", errors);
			return ResponseEntity.ok(map);
		}
		return ResponseEntity.ok(DataUltil.setData("ok", ""));
	}

	@PostMapping("/api/bill")
	public ResponseEntity<?> add(@RequestBody @Valid BillCreateRequest request, BindingResult result,
			Principal principal) {
		List<CartPK> cartPKs = request.getCartPKs();
		User user = userService.getByEmail(principal.getName());
		List<Cart> carts = cartService.getByIds(cartPKs);
		for (Cart cart : carts) {
			if (cart.getAmount() > cart.getBook().getAmount()) {
				// @formatter:off
				StringBuilder builder=new StringBuilder().append("S??ch ")
						.append(cart.getBook().getName()).append(" ch??? c??n ")
						.append(cart.getBook().getAmount());
				// @formatter:on
				HashMap<String, Object> map = DataUltil.setData("max", builder.toString());
				return ResponseEntity.ok(map);
			}
		}
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			HashMap<String, Object> map = DataUltil.setData("error", errors);
			return ResponseEntity.ok(map);
		}
		Bill bill = new Bill();
		bill.setCreatedTime(new Date());
		bill.setMessage("");
		String transn = TranSnUltil.getTranSn();
		bill.setTranSn(transn);
		bill.setUser(user);
		bill.setMissed(false);
		bill.setStatus(BillStatusSingleton.getInstance(statusService).get(0));
		bill.setFullname(request.getFullname());
		bill.setPhoneNumber(request.getPhoneNumber());
		bill.setTransType(TransportTypeSingleton.getInstance(transportTypeService).get(request.getTransportType()));
		bill.setPayType(PaymentTypeSingleton.getInstance(paymentTypeService).get(request.getPaymentType()));
		bill.setAddressDetail(request.getAddressDetail());
		
		bill.setWard(wardService.findById(request.getWard()));
		
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
			detail.setPrice( book.getPrice().multiply(new BigDecimal(100 - book.getDiscount())).divide(new BigDecimal(100)));
			detailService.save(detail);
			book.setSaleAmount(cart.getAmount() + book.getSaleAmount());
			bookService.save(book);
			cartService.delete(cart.getCartPK());
			bookMoney = bookMoney.add(detail.getPrice().multiply(new BigDecimal(cart.getAmount())));
			buyService.save(book);
		}
		bill.setBookMoney(bookMoney);
		bill.setTransportFee(request.getTransportFee());
		// x??? l?? m?? khuy???n m??i ??? ????y

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
		HashMap<String, Object> map = DataUltil.setData("ok", "?????t h??ng th??nh c??ng");
		map.put("tranSn", transn);
		return ResponseEntity.ok(map);
	}

	@PutMapping("/api/bill/{id}")
	public ResponseEntity<?> cancel(@PathVariable("id") Integer id, @RequestBody @Valid BillStatusRequest request) {
		HashMap<String, Object> map = new HashMap<>();
		BillStatus status = null;
		Bill b = billService.getById(id);
		switch (request.getStatus()) {
		case 1:
			if (request.getMessage() == null || request.getMessage().length() == 0) {
				map = DataUltil.setData("error", "Vui l??ng nh???p message");
			} else {
				status = BillStatusSingleton.getInstance(statusService).get(2);
				if (b.getStatus().getId() == 1) {
					b.setMessage(request.getMessage());
					b.setStatus(status);
					b.setUpdatedTime(new Date());
					map = DataUltil.setData("ok", "H???y ????n th??nh c??ng");
				} else {
					map = DataUltil.setData("error", "B???n kh??ng th??? h???y ????n do ????n ???? ???????c h???y ho???c ch???p nh???n");
				}
			}
			break;
		case 4:
			if (b.getStatus().getId() == 4) {
				try {
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate());
					if (date.getTime() - new Date().getTime() <= 86400000) {
						map = DataUltil.setData("error", "Ng??y d???i ph???i sau ng??y h??m nay");
						break;
					}
				} catch (Exception e) {
					map = DataUltil.setData("error", "Ng??y kh??ng ????ng ?????nh d???ng");
					break;
				}
				b.setMessage("D???i ng??y nh???n: " + request.getDate());
				b.setMissed(true);
				b.setUpdatedTime(new Date());
				Shipment shipment = shipmentService.getByBillId(b.getId());
				shipment.setMessage("D???i ng??y nh???n: " + request.getDate());
				shipmentService.save(shipment);
				map = DataUltil.setData("ok", "X??c nh???n ????n th??nh c??ng");
			} else {
				map = DataUltil.setData("error", "Y??u c???u kh??ng h???p l???");
			}
			break;
		case 5:
			status = BillStatusSingleton.getInstance(statusService).get(6);
			if (b.getStatus().getId() == 5) {
				b.setStatus(status);
				b.setUpdatedTime(new Date());
				map = DataUltil.setData("ok", "X??c nh???n ????n th??nh c??ng");
			} else {
				map = DataUltil.setData("error", "Y??u c???u kh??ng h???p l???");
			}
			break;
		default:
			map = DataUltil.setData("error", "Y??u c???u kh??ng h???p l???");
			break;
		}
		b = billService.save(b);
		map.put("bill", b);
		return ResponseEntity.ok(map);
	}
}
