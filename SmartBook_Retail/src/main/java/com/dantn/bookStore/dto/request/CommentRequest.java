package com.dantn.bookStore.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.dantn.bookStore.adapter.DtoToEntity;
import com.dantn.bookStore.entities.BillDetailPK;
import com.dantn.bookStore.entities.Comment;

public class CommentRequest implements DtoToEntity<Comment>{
	@Positive(message = "Điểm đánh giá là số nguyên lớn hơn 0")
	@Max(value = 5,message = "Điểm đánh giá không quá 5")
	private Integer rate;
	@NotBlank(message = "Không bỏ trống nội dung")
	private String content;
	private BillDetailPK detailId;
	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public BillDetailPK getDetailId() {
		return detailId;
	}

	public void setDetailId(BillDetailPK detailId) {
		this.detailId = detailId;
	}

	@Override
	public Comment changeToEntity(Comment e) {
		e.setContent(content);
		e.setRate(rate);
		return e;
	}
}
