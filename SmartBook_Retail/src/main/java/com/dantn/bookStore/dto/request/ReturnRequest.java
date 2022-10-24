package com.dantn.bookStore.dto.request;

import com.dantn.bookStore.entities.BillDetailPK;

public class ReturnRequest {
	private BillDetailPK pk;
	private Long amount;
	public BillDetailPK getPk() {
		return pk;
	}
	public void setPk(BillDetailPK pk) {
		this.pk = pk;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
