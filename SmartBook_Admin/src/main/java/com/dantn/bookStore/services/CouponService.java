package com.dantn.bookStore.services;

import com.dantn.bookStore.entities.Content;
import com.dantn.bookStore.entities.Coupon;
import com.dantn.bookStore.entities.Publisher;
import com.dantn.bookStore.repositories.ICouponRepository;
import com.dantn.bookStore.ultilities.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private ICouponRepository rep;

    public Coupon create(Coupon obj) {
        obj.setId(null);
        return rep.save(obj);
    }

    public Coupon update(Coupon obj) {
        return rep.save(obj);
    }

    public Integer delete(Integer id) {
        rep.deleteById(id);
        return id;
    }

    public Coupon findById(Integer id) {
        if(rep.findById(id).isPresent()) {
            return rep.findById(id).get();
        } else return null;
    }

    public List<Coupon> findByName(String name) {
        return (List<Coupon>) rep.findByName(name);
    }

    public List<Coupon> getAll() {
        return rep.findAll();
    }

    public List<Integer> delete(List<Integer> listId) {
        if (listId != null) {
            List<Coupon> listDelete = rep.findAllById(listId);
            rep.deleteAll(listDelete);
            return listId;
        } else
            return null;
    }

    public Page<Coupon> getPage(int pageIndex,int pageSize, String sortBy, Boolean sortCoupon, Integer toSize, Integer fromSize, String keyWord) {
        Pageable page;
        if (sortCoupon) {
            page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending());
        } else {
            page = PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).descending());
        }
        return  rep.getPage(keyWord, toSize, fromSize, page);
    }

    public HashMap<String, Object> getPage(Integer pageIndex, Integer sortCoupon, String keyWord, Integer getCoupon){
        HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        Page<Coupon> data = getData(pageIndex, sortCoupon, keyWord, getCoupon);
        mapReturn.put("data", data);
        mapReturn.put("listBill", getListBill(data));
        return mapReturn;
    }

    public HashMap<String, Object> update(Integer pageIndex, Integer sortCoupon, String keyWord, Integer getCoupon,String value, java.sql.Date date1, Date date2, Integer discount, BigDecimal min_money, BigDecimal money,Integer element){
        HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        try {
            if (value.trim() == "") {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }

            Coupon coupon = (Coupon) this.findByName(value.trim());

            if (coupon != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }

            Coupon cUpdate = this.findById(element);
            cUpdate.setName(value.trim());
            cUpdate.setStartDate(date1);
            cUpdate.setEndDate(date2);
            cUpdate.setDiscount(discount);
            cUpdate.setMinMoney(min_money);
            cUpdate.setMoney(money);
            this.update(cUpdate);

            Page<Coupon> data = getData(pageIndex, sortCoupon, keyWord, getCoupon);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBill", getListBill(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
    }

    public HashMap<String, Object> delete(Integer pageIndex, Integer sortCoupon, String keyWord, Integer getCoupon,Integer element){
        HashMap<String, Object> mapReturn = new HashMap<String, Object>();

        try {
            this.delete(element);
            mapReturn.put("statusCode", "ok");
            Page<Coupon> data = getData(pageIndex, sortCoupon, keyWord, getCoupon);

            if(pageIndex > 0 && data.isEmpty()) {
                data = getData(pageIndex - 1, sortCoupon, keyWord, getCoupon);
            }

            mapReturn.put("statusCode", "ok");
            mapReturn.put("listBill", getListBill(data));
            mapReturn.put("data", data);
        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
    }

    public HashMap<String, Object> delete(Integer pageIndex, Integer sortCoupon, String keyWord, Integer getCoupon,Integer element,Integer[] listId){
        HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        try {
            this.delete(Arrays.asList(listId));
            mapReturn.put("statusCode", "ok");
            Page<Coupon> data = getData(pageIndex, sortCoupon, keyWord, getCoupon);

            if(pageIndex > 0 && data.isEmpty()) {
                data = getData(pageIndex - 1, sortCoupon, keyWord, getCoupon);
            }
            mapReturn.put("listBill", getListBill(data));
            mapReturn.put("data", data);
        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
    }

    private Page<Coupon> getData(Integer pageIndex, Integer sortCoupon, String keyWord, Integer getCoupon) {
        Page<Coupon> pageReturn;
        // Get Type
        switch (sortCoupon) {
            case 0:
                pageReturn = this.getPage(pageIndex, 10, "id", false, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
            case 1:
                pageReturn = this.getPage(pageIndex, 10, "id", true, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
            case 2:
                pageReturn = this.getPage(pageIndex, 10, "name", true, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
            case 3:
                pageReturn = this.getPage(pageIndex, 10, "name", false, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
            case 4:
                pageReturn = this.getPage(pageIndex, 10, "bill.size", false, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
            default:
                pageReturn = this.getPage(pageIndex, 10, "bill.size", true, getToSize(getCoupon),
                        getFromSize(getCoupon), "%" + keyWord + "%");
                break;
        }

        // Return View
        return pageReturn;
    }

    private Integer getToSize(Integer getCoupon) {
        switch (getCoupon) {
            case 0:
                return Integer.MIN_VALUE;
            case 1:
                return 1;
            case 2:
                return 51;
            default:
                return 101;
        }
    }

    private HashMap<String, Object> getListBill(Page<Coupon> page) {
        HashMap<String, Object> mapReturn = new HashMap<String, Object>();
        for (Coupon coupon : page) {
            mapReturn.put(coupon.getId() + "", coupon.getBills());
        }
        return mapReturn;
    }

    private Integer getFromSize(Integer getCoupon) {
        switch (getCoupon) {
            case 0:
                return Integer.MAX_VALUE;
            case 1:
                return 50;
            case 2:
                return 100;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public HashMap<String, Object> create(Integer pageIndex, Integer sortCoupon, java.lang.String keyWord, Integer getCoupon, java.lang.String value, java.sql.Date date1, Date date2, Integer discount, BigDecimal min_money, BigDecimal money) {
        HashMap<java.lang.String, java.lang.Object> mapReturn = new HashMap<java.lang.String, java.lang.Object>();

        try {
            if (value.trim() == ""||date1==null||date2==null||discount==null||min_money==null||money==null) {
                mapReturn = DataUltil.setData("blank", null);
                return mapReturn;
            }
            else if(date1.after(date2)) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            } else if (date1.equals(date2)) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            } else if (discount < 0 || discount > 100) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            } else if (min_money.compareTo(BigDecimal.ZERO) < 0) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            } else if (money.compareTo(BigDecimal.ZERO) < 0) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }
            Coupon coupon = (Coupon) this.findByName(value.trim());

            if (coupon != null) {
                mapReturn = DataUltil.setData("invalid", null);
                return mapReturn;
            }

            Coupon coupon1 = new Coupon();
            coupon1.setName(value);
            coupon1.setStartDate(date1);
            coupon1.setEndDate(date2);
            coupon1.setDiscount(discount);
            coupon1.setMinMoney(min_money);
            coupon1.setMoney(money);
            this.create(coupon1);

            Page<Coupon> data = getData(pageIndex, sortCoupon, keyWord, getCoupon);
            mapReturn.put("statusCode", "ok");
            mapReturn.put("data", data);
            mapReturn.put("listBill", getListBill(data));

        } catch (Exception e) {
            mapReturn = DataUltil.setData("error", null);
        }

        return mapReturn;
    }
}
