package com.dantn.bookStore.services;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.BillUpdateRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IReturnBillRepository;
import com.dantn.bookStore.repositories.IShipmentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;

@Service
public class ReturnBillService {
    private IReturnBillRepository repository;
    private BillStatusService billStatusService;
    private IShipmentRepository shipmentRepository;
    private UserService service;
   

    

    public ReturnBillService(IReturnBillRepository repository, BillStatusService billStatusService,
            IShipmentRepository shipmentRepository, UserService service) {
        super();
        this.repository = repository;
        this.billStatusService = billStatusService;
        this.shipmentRepository = shipmentRepository;
        this.service = service;
    }

    public ReturnBill save(ReturnBill returnBill) {
        return this.repository.save(returnBill);
    }

    public Page<ReturnBill> getReturnBills(Integer page, Integer statusIndex) {
        BillStatus status = BillStatusSingleton.getInstance(billStatusService).get(statusIndex);
        return this.repository.findByStatus(status,
                PageRequest.of(0, AppConstraint.PAGE_NUM, Sort.by("id").ascending()));
    }

    public ReturnBill getById(Integer id) {
        Optional<ReturnBill> optional = repository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public HashMap<String, Object> changeStatus(BillUpdateRequest request,Principal principal) {
        User user=service.getByEmail(principal.getName());
        HashMap<String, Object> map;
        switch (request.getStatusIndex()) {
            case 2:
                if (request.getMessage() == null || "".equals(request.getMessage().trim())) {
                    map = DataUltil.setData("blank", "");
                    return map;
                } else {
                    // đưa đơn về hủy
                    ReturnBill returnBill = getById(request.getId());
                    returnBill.setMessage(request.getMessage());
                    returnBill.setUpdatedTime(new Date());
                    returnBill.setStatus(
                            BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex()));
                    this.save(returnBill);
                    map = DataUltil.setData("ok", "");
                    return map;
                }
            case 3:
                ReturnBill bill = getById(request.getId());
                Shipment s=new Shipment();
                s.setBill(true);
                s.setStatus(BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex()));
                s.setBillId(bill.getId());
                s.setCreatedTime(new Date());
                s.setUser(user);
                this.shipmentRepository.save(s);
                bill.setUpdatedTime(new Date());
                bill.setStatus(
                        BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex()));
                this.save(bill);
                map=DataUltil.setData("ok", "");
                return map;
            default:
                ReturnBill returnBill = getById(request.getId());
                returnBill.setUpdatedTime(new Date());
                returnBill.setStatus(
                        BillStatusSingleton.getInstance(billStatusService).get(request.getStatusIndex()));
                this.save(returnBill);
                map = DataUltil.setData("ok", "");
                return map;

        }
    }
}
