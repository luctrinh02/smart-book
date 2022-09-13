package com.dantn.bookStore.api;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.CartPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.services.BillDetailService;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.services.BookService;
import com.dantn.bookStore.services.CartService;
import com.dantn.bookStore.services.UserService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
public class BillApi {
	private BillService billService;
	private BillDetailService detailService;
	private UserService userService;
	private BookService bookService;
	private CartService cartService;
	private BillStatusService statusService;
	
	public BillApi(BillService billService, BillDetailService detailService, UserService userService,
			BookService bookService, CartService cartService, BillStatusService statusService) {
		super();
		this.billService = billService;
		this.detailService = detailService;
		this.userService = userService;
		this.bookService = bookService;
		this.cartService = cartService;
		this.statusService = statusService;
	}
	@GetMapping("/api/bill")
	public ResponseEntity<?> history(@RequestParam(name = "page",defaultValue = "0") Integer pageNum
			,@RequestParam("transn") String transn){
		if(transn==null) {
			Page<Bill> page=billService.getAll(pageNum);
			HashMap<String, Object> map=DataUltil.setData("ok", page);
			return ResponseEntity.ok(map);
		}else {
			Bill bill=billService.getByTranSn(transn);
			HashMap<String, Object> map=DataUltil.setData("ok", bill);
			return ResponseEntity.ok(map);
		}
	}
	@GetMapping("/api/bill/{id}")
	public ResponseEntity<?> detail(@PathVariable(name = "id") Integer id,Principal principal
			){
		Bill bill=billService.getById(id);
		if(bill.getUser().getEmail().equals(principal.getName())) {
			List<BillDetail> list=detailService.getByBill(bill);
			HashMap<String, Object> map=DataUltil.setData("ok", list);
			return ResponseEntity.ok(map);
		}else {
			HashMap<String, Object> map=DataUltil.setData("error", "Bạn không có quyền xem hóa đơn này");
			return ResponseEntity.ok(map);
		}
	}
	@PostMapping("/api/bill")
	public ResponseEntity<?> add(
			@RequestParam(name = "chk[]",required = false) Integer[] chk
			,@RequestParam(name = "amount[]",required = false,defaultValue = "0") Integer[] amountArr
			,Principal principal){
		User user=userService.getByEmail(principal.getName());
		List<Integer> bookIds=Arrays.asList(chk);
		List<Integer> amounts=Arrays.asList(amountArr);
		BigDecimal total=BigDecimal.ZERO;
		for(int i=0;i<chk.length;i++) {
			Book b=bookService.getById(chk[i]);
			if(amountArr[i]>b.getAmount()) {
				HashMap<String, Object> map=DataUltil.setData("error", "Sản phẩm "+b.getName()+" chỉ còn "+b.getAmount());
				return ResponseEntity.ok(map);
			}else {
				BigDecimal amountBigDecimal=new BigDecimal(amountArr[i]);
				BigDecimal discount=new BigDecimal((100-b.getDiscount())/100);
				total=total.add(amountBigDecimal.multiply(b.getPrice().multiply(discount)));
			}
		}
		Bill bill=new Bill();
		bill.setCreatedTime(new Date());
		bill.setStatus(BillStatusSingleton.getInstance(statusService).get(0));
		bill.setTotalMoney(total);
		bill.setUser(user);
		bill=billService.save(bill);
		List<Book> books=bookService.getByIds(bookIds);
		for(Book x:books) {
			BillDetailPK pk=new BillDetailPK();
			pk.setBillId(bill.getId());
			pk.setUserId(user.getId());
			CartPK cartPK=new CartPK();
			BillDetail detail=new BillDetail();
//			detail.set
		}
		return ResponseEntity.ok("");
	}
	
	@PatchMapping("/api/bill/{id}")
	public ResponseEntity<?> cancel(@PathVariable("id") Integer id,@RequestParam("message") String mesage){
		if(mesage==null || mesage.length()==0) {
			HashMap<String, Object> map=DataUltil.setData("error", "Vui lòng nhập message");
			return ResponseEntity.ok(map);
		}else {
			BillStatus status=BillStatusSingleton.getInstance(statusService).get(1);
			Bill b=billService.getById(id);
			if(b.getStatus().getId()==0) {
				b.setMessage(mesage);
				b.setStatus(status);
				b.setUpdatedTime(new Date());
				billService.save(b);
				HashMap<String, Object> map=DataUltil.setData("ok", "Hủy đơn thành công");
				return ResponseEntity.ok(map);
			}else {
				HashMap<String, Object> map=DataUltil.setData("error", "Bạn không thể hủy đơn do đơn đã được hủy hoặc chấp nhận");
				return ResponseEntity.ok(map);
			}
		}
	}
}
