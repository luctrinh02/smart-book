package com.dantn.bookStore.api;

import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/coupon")
public class CouponApi {

    @Autowired
    private CouponService couponService;

    @Autowired
    private BillService billService;

    @GetMapping("/getPage")
    public ResponseEntity<?> getPage(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                     @RequestParam(name = "sortCoupon", defaultValue = "0") Integer sortCoupon,
                                     @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                     @RequestParam(name = "getCoupon", defaultValue = "0") Integer getCoupon) {
        HashMap<String, Object> mapReturn = couponService.getPage(pageIndex, sortCoupon, keyWord, getCoupon);
        return ResponseEntity.ok(mapReturn);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                             @RequestParam(name = "sortPublisher", defaultValue = "0") Integer sortPublisher,
                                             @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                             @RequestParam(name = "getPublisher", defaultValue = "0") Integer getPublisher,
                                             @RequestParam(name = "value") String value,
                                             @RequestParam(name = "discount") Integer discount,
                                             @RequestParam(name = "startDate") java.sql.Date startDate,
                                             @RequestParam(name = "endDate") Date endDate,
                                             @RequestParam(name = "min_money") BigDecimal min_money,
                                             @RequestParam(name = "money") BigDecimal money) {
        HashMap<String, Object> mapReturn = couponService.create(pageIndex, sortPublisher, keyWord, getPublisher, value,startDate,endDate,discount,min_money,money);
        return ResponseEntity.ok(mapReturn);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCoupon(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                             @RequestParam(name = "sortCoupon", defaultValue = "0") Integer sortCoupon,
                                             @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                             @RequestParam(name = "getCoupon", defaultValue = "0") Integer getCoupon,
                                             @RequestParam(name = "value") String value,
                                          @RequestParam(name = "discount") Integer discount,
                                          @RequestParam(name = "startDate") java.sql.Date startDate,
                                          @RequestParam(name = "endDate") Date endDate,
                                          @RequestParam(name = "min_money") BigDecimal min_money,
                                          @RequestParam(name = "money") BigDecimal money,
                                          @RequestParam(name = "element") Integer element) {
        HashMap<String, Object> mapReturn = couponService.update(pageIndex, sortCoupon, keyWord, getCoupon, value,startDate,endDate,discount,min_money,money, element);
        return ResponseEntity.ok(mapReturn);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCoupon(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                             @RequestParam(name = "sortCoupon", defaultValue = "0") Integer sortCoupon,
                                             @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                             @RequestParam(name = "getCoupon", defaultValue = "0") Integer getCoupon,
                                             @RequestParam(name = "element") Integer element) {
        HashMap<String, Object> mapReturn = couponService.delete(pageIndex, sortCoupon, keyWord, getCoupon, element);
        return ResponseEntity.ok(mapReturn);
    }

    @DeleteMapping("/deleteList")
    public ResponseEntity<?> deleteListCoupon(
            @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortCoupon", defaultValue = "0") Integer sortCoupon,
            @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
            @RequestParam(name = "getCoupon", defaultValue = "0") Integer getCoupon,
            @RequestParam(name = "listId") Integer[] listId) {
        HashMap<String, Object> mapReturn = couponService.delete(pageIndex, sortCoupon, keyWord, getCoupon, getCoupon, listId);
        return ResponseEntity.ok(mapReturn);
    }
}
