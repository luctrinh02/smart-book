package com.dantn.bookStore.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.services.ReturnBillService;

@RestController
public class ReturnBillApi {
    @Autowired
    private ReturnBillService returnBillService;
    @GetMapping("/api/admin/return")
    public ResponseEntity<?> get(@RequestParam(name = "page",defaultValue = "0") Integer page
            ,@RequestParam(name = "status",defaultValue = "0") Integer statusIndex){
        Page<ReturnBill> page2=returnBillService.getReturnBills(page, statusIndex);
        return ResponseEntity.ok(page2);
    }
    @GetMapping("/api/admin/return/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        return ResponseEntity.ok(returnBillService.getById(id));
    }
    @PutMapping("/api/admin/return")
    public ResponseEntity<?> change(@RequestBody BillUpdateRequest request,Principal principal){
        return ResponseEntity.ok(returnBillService.changeStatus(request,principal));
    }
}
