package com.dantn.bookStore.services;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.ReturnRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.entities.ReturnBillDetailPK;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IReturnBillRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@Service
public class ReturnBillService {
	private IReturnBillRepository repository;
	private ReturnBillDetailService detailService;
	private BillDetailService billDetailService;
	private BillService billService;
	private BillStatusService billStatusService;
	private UserService userService;
	

	public ReturnBillService(IReturnBillRepository repository, ReturnBillDetailService detailService,
			BillDetailService billDetailService, BillService billService, BillStatusService billStatusService,
			UserService userService) {
		super();
		this.repository = repository;
		this.detailService = detailService;
		this.billDetailService = billDetailService;
		this.billService = billService;
		this.billStatusService = billStatusService;
		this.userService = userService;
	}

	public ReturnBill save(ReturnBill bill) {
		return this.repository.save(bill);
	}

	public HashMap<String, Object> beforeCreate(List<ReturnRequest> requests){
		//checking
		int flagZero=0;
		boolean flagAvailable=true;
		for(ReturnRequest x:requests) {
			if(x.getAmount()>0) {
				BillDetail detail=billDetailService.getById(x.getPk());
				if(x.getAmount()>detail.getAvailable()) {
					flagAvailable=false;
					break;
				}
			}else {
				flagZero++;
			}
		}
		HashMap<String, Object> map;
		if(flagZero==requests.size()) {
			map=DataUltil.setData("error", "Vui lòng nhập số lượng lớn hơn 0");
		}else if(!flagAvailable) {
			map=DataUltil.setData("error", "Bạn không đủ sách để đổi");
		}else {
			map=DataUltil.setData("oke", "Không có lỗi");
		}
		return map;
	}

	@Transactional(rollbackOn = Exception.class)
	public HashMap<String, Object> create(List<ReturnRequest> requests){
		try {
			ReturnBill rBill=new ReturnBill();
			Bill b=billService.getById(requests.get(0).getPk().getBillId());
			rBill.setBill(b);
			rBill.setCreatedTime(new Date());
			rBill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(0));
			rBill=this.save(rBill);
			for(ReturnRequest x:requests) {
				if(x.getAmount()>0) {
					ReturnBillDetail detail=new ReturnBillDetail();
					detail.setAmount(x.getAmount());
					ReturnBillDetailPK pk=new ReturnBillDetailPK();
					pk.setBookId(x.getPk().getBookId());
					pk.setReturnBillId(rBill.getId());
					detail.setPK(pk);
					BillDetail billDetail=billDetailService.getById(x.getPk());
					billDetail.setAvailable(billDetail.getAvailable()-x.getAmount());
					billDetailService.save(billDetail);
					detailService.save(detail);
				}
			}
			HashMap<String, Object> map=DataUltil.setData("ok", "Yêu cầu đổ đang đợi duyệt");
			return map;
		} catch (Exception e) {
			HashMap<String, Object> map=DataUltil.setData("error", "Lỗi dữ liệu");
			return map;
		}
	}
	public Page<ReturnBill> getByUser(Principal principal,Integer pn){
		User user=userService.getByEmail(principal.getName());
		Page<ReturnBill> page=repository.findByUser(user,PageRequest.of(pn, AppConstraint.PAGE_NUM,Sort.by("id").descending()));
		return page;
	}
	public Page<ReturnBill> getByBill(String transn,Integer pn){
		Bill bill=billService.getByTranSn(transn);
		Page<ReturnBill> page=repository.findByBill(bill, PageRequest.of(pn, AppConstraint.PAGE_NUM,Sort.by("id").descending()));
		return page;
	}
	public ReturnBill getById(Integer id) {
		Optional<ReturnBill> optional=repository.findById(id);
		return optional.isPresent()?optional.get():null;
	}
	public Boolean cancel(Integer id){
		ReturnBill returnBill=this.getById(id);
		if(returnBill.getStatus().getId()!=1) {
			return false;
		}else {
			returnBill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(2));
			List<ReturnBillDetail> list=detailService.getByReturn(returnBill);
			for(ReturnBillDetail x:list) {
				BillDetailPK pk=new BillDetailPK();
				pk.setBillId(x.getReturnBill().getBill().getId());
				pk.setBookId(x.getBook().getId());
				BillDetail billDetail=billDetailService.getById(pk);
				billDetail.setAvailable(billDetail.getAvailable()+x.getAmount());
				billDetailService.save(billDetail);
			}
			this.save(returnBill);
			return true;
		}
	}
	public List<ReturnBillDetail> getDetail(Integer id){
		ReturnBill returnBill=this.getById(id);
		List<ReturnBillDetail> details=detailService.getByReturn(returnBill);
		return details;
	}
}
