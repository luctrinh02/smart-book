package com.dantn.bookStore.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.dto.request.ShipmentRequest;
import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.entities.ReturnBillDetail;
import com.dantn.bookStore.entities.ReturnBillDetailPK;
import com.dantn.bookStore.entities.Shipment;
import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.repositories.IBookRepository;
import com.dantn.bookStore.repositories.IShipmentRepository;
import com.dantn.bookStore.ultilities.AppConstraint;
import com.dantn.bookStore.ultilities.BillStatusSingleton;
import com.dantn.bookStore.ultilities.DataUltil;
import com.dantn.bookStore.ultilities.TranSnUltil;

@Service
public class ShipmentService {
    @Autowired
    private IShipmentRepository repository;
    @Autowired
    private BillStatusService billStatusService;
    @Autowired
    private UserService userService;
    @Autowired
    private BillService billService;
    @Autowired
    private ReturnBillService returnBillService;
    @Autowired
    private ReturnBillDetailService returnBillDetail;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired 
    private IBookRepository bookRepository;
    public Shipment save(Shipment shipment) {
        return this.repository.save(shipment);
    }

    public Shipment getById(Integer id) {
        Optional<Shipment> optional = this.repository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public List<Shipment> getByUser(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        return this.repository.findByUserAndStatus(user, BillStatusSingleton.getInstance(billStatusService).get(3));
    }

    public Object getBillOptional(Shipment shipment) {
        if (shipment.getBill()) {
            return billService.getById(shipment.getBillId());
        } else {
            return returnBillService.getById(shipment.getBillId());
        }
    }

    public HashMap<String, Object> changeStatus(ShipmentRequest request){
        Shipment shipment = this.getById(request.getId());
        switch (request.getStatus()) {
            case 2:// hủy
                   // check message
                if (request.getMessage() == null || "".equals(request.getMessage().trim())) {
                    return DataUltil.setData("blank", "");
                } else {
                    // đưa shipment về trạng thái hủy
                    shipment.setUpdatedTime(new Date());
                    shipment.setStatus(BillStatusSingleton.getInstance(billStatusService).get(2));
                    this.save(shipment);
                    if (shipment.getBill()) {
                        // nếu là bill thì hủy cũ
                        Bill bill = (Bill) this.getBillOptional(shipment);
                        bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(2));
                        bill.setUpdatedTime(new Date());
                        bill.setMessage(request.getMessage());
                        bill = billService.save(bill);
                        // tạo bill mới
                        List<BillDetail> details = bill.getDetails();
                        for (BillDetail x : details) {
                            if(x.getBook().getAmount()<x.getAmount()) {
                                return DataUltil.setData("amount", "Không đủ sách để tạo đơn");
                            }
                        }
                        Bill newBill=new Bill();
                        newBill.setCreatedTime(new Date());
                        newBill.setUpdatedTime(null);
                        newBill.setBookMoney(bill.getBookMoney());
                        newBill.setCoupon(bill.getCoupon());
                        newBill.setTotalMoney(bill.getTotalMoney());
                        newBill.setTransportFee(bill.getTransportFee());
                        newBill.setTranSn(TranSnUltil.getTranSn());
                        newBill.setUser(bill.getUser());
                        newBill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(0));
                        newBill = billService.save(newBill);
                        List<BillDetail> details2=new ArrayList<>();
                        for (BillDetail x : details) {
                        	BillDetailPK pk = new BillDetailPK();
                            pk.setBillId(newBill.getId());
                            BillDetail newDetail = new BillDetail();
                            pk.setBookId(x.getBook().getId());
                            newDetail.setBillDetailPK(pk);
                            newDetail.setBill(newBill);
                            newDetail.setBook(x.getBook());
                            newDetail.setAmount(x.getAmount());
                            newDetail.setPrice(x.getPrice());
                            newDetail.setAvailable(x.getAvailable());
                            billDetailService.save(newDetail);
                            details2.add(newDetail);
                            Book book=x.getBook();
                            book.setAmount(book.getAmount()-x.getAmount());
                            bookRepository.save(book);
                        }
                        newBill.setDetails(details2);
                        newBill.setMessage("Đơn tạo mới do lỗi vận chuyển");
                        billService.save(newBill);
                        return DataUltil.setData("ok", "Thành công");
                    } else {
                        // nếu là return bill thì đưa về trạng thái hủy
                        ReturnBill bill=(ReturnBill) getBillOptional(shipment);
                        bill.setMessage(request.getMessage());
                        bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(2));
                        bill.setUpdatedTime(new Date());
                        bill=returnBillService.save(bill);
                        //tạo mơis
                        List<ReturnBillDetail> details=bill.getBillDetails();
                        for (ReturnBillDetail x : details) {
                            if(x.getBook().getAmount()<x.getAmount()) {
                                return DataUltil.setData("amount", "Không đủ sách để tạo đơn");
                            }
                        }
                        ReturnBill newBill=new ReturnBill();
                        newBill.setCreatedTime(new Date());
                        newBill.setUpdatedTime(null);
                        newBill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(0));
                        newBill.setBill(bill.getBill());
                        newBill.setCreatedTime(new Date());
                        newBill = returnBillService.save(newBill);
                        List<ReturnBillDetail> details2=new ArrayList<>();
                        for (ReturnBillDetail x : details) {
                        	ReturnBillDetailPK pk = new ReturnBillDetailPK();
                            pk.setReturnBillId(bill.getId());
                            ReturnBillDetail newDetail = new ReturnBillDetail();
                            pk.setBookId(x.getBook().getId());
                            newDetail.setPK(pk);
                            newDetail.setReturnBill(bill);
                            newDetail.setBook(x.getBook());
                            newDetail.setAmount(x.getAmount());
                            returnBillDetail.save(newDetail);
                            details2.add(newDetail);
                            Book book=x.getBook();
                            book.setAmount(book.getAmount()-x.getAmount());
                            bookRepository.save(book);
                        }
                        bill.setBillDetails(details2);
                        bill.setMessage("Đơn tạo mới do lỗi vận chuyển");
                        returnBillService.save(bill);
                        return DataUltil.setData("ok", "Thành công");
                    }
                }
            case 5:// khách không nhận
                    // đưa shipment về trạng thái hủy
                    shipment.setUpdatedTime(new Date());
                    shipment.setStatus(BillStatusSingleton.getInstance(billStatusService).get(5));
                    this.save(shipment);
                    if (shipment.getBill()) {
                        // nếu là bill thì hủy cũ
                        Bill bill = (Bill) this.getBillOptional(shipment);
                        bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(5));
                        bill.setUpdatedTime(new Date());
                        bill.setMessage("Khách không nhận đơn");
                        bill = billService.save(bill);
                        List<BillDetail> details=bill.getDetails();
                        for (BillDetail x : details) {
                            Book book=x.getBook();
                            book.setAmount(book.getAmount()+x.getAmount());
                            bookRepository.save(book);
                        }
                        return DataUltil.setData("ok", "Thành công");
                    } else {
                        // nếu là return bill thì đưa về trạng thái hủy
                        ReturnBill bill=(ReturnBill) getBillOptional(shipment);
                        bill.setMessage(request.getMessage());
                        bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(5));
                        bill.setUpdatedTime(new Date());
                        bill=returnBillService.save(bill);
                        List<ReturnBillDetail> details=bill.getBillDetails();
                        for (ReturnBillDetail x : details) {
                            Book book=x.getBook();
                            book.setAmount(book.getAmount()+x.getAmount());
                            bookRepository.save(book);
                        }
                        return DataUltil.setData("ok", "Thành công");
                    }
            default:
                shipment.setUpdatedTime(new Date());
                shipment.setStatus(BillStatusSingleton.getInstance(billStatusService).get(5));
                this.save(shipment);
                if (shipment.getBill()) {
                    // nếu là bill thì done
                    Bill bill = (Bill) this.getBillOptional(shipment);
                    bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(request.getStatus()));
                    bill.setUpdatedTime(new Date());
                    bill = billService.save(bill);
                    return DataUltil.setData("ok", "Thành công");
                } else {
                    // nếu là return bill thì đưa về trạng thái mơi
                    ReturnBill bill=(ReturnBill) getBillOptional(shipment);
                    bill.setStatus(BillStatusSingleton.getInstance(billStatusService).get(request.getStatus()));
                    bill.setUpdatedTime(new Date());
                    bill=returnBillService.save(bill);
                    return DataUltil.setData("ok", "Thành công");
                }
        }
    }
}
