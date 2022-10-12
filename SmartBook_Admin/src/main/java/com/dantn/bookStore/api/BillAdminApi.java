package com.dantn.bookStore.api;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@RestController
@RequestMapping("/api/admin/bill")
public class BillAdminApi {
	private BillService billService;
	private BillStatusService billStatusService;
	
	public BillAdminApi(BillService billService, BillStatusService billStatusService) {
        super();
        this.billService = billService;
        this.billStatusService = billStatusService;
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
}
