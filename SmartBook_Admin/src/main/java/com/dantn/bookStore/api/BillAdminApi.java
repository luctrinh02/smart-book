package com.dantn.bookStore.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.services.BillDetailService;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/admin/bill")
public class BillAdminApi {
	private BillService billService;
	private BillStatusService billStatusService;
	private BillDetailService billDetailService;
	
	public BillAdminApi(BillService billService, BillStatusService billStatusService,
            BillDetailService billDetailService) {
        super();
        this.billService = billService;
        this.billStatusService = billStatusService;
        this.billDetailService = billDetailService;
    }
    @GetMapping("")
	public ResponseEntity<?> getByStatus(@RequestParam("status") Integer statusIndex
			,@RequestParam("page")Integer pageNum){
		BillStatus billStatus=BillStatusSingleton.getInstance(billStatusService).get(statusIndex);
		Page<Bill> page=billService.getByAcepted(billStatus, pageNum);
		return ResponseEntity.ok(page);
	}
	@PutMapping("")
	public ResponseEntity<?> updateStatus(@RequestBody BillUpdateRequest request){
		HashMap<String, Object> map=billService.update(request, billStatusService);
		return ResponseEntity.ok(map);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable("id") Integer id){
	    Bill b=billService.getById(id);
	    List<BillDetail> details=billDetailService.getByBill(b);
        return ResponseEntity.ok(details);
	}
}
