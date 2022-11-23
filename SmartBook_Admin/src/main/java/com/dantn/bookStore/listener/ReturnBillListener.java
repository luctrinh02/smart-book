package com.dantn.bookStore.listener;

import javax.persistence.PostUpdate;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dantn.bookStore.entities.Notication;
import com.dantn.bookStore.entities.ReturnBill;
import com.dantn.bookStore.services.NoticationService;
import com.dantn.bookStore.ultilities.AppConstraint;
@Configurable
public class ReturnBillListener {
	@Autowired
	private ObjectFactory<NoticationService> factory;

	@PostUpdate
	public void update(ReturnBill bill) {
		Notication n=new Notication();
		n.setUser(bill.getUser());
		switch (bill.getStatus().getId()) {
		case 2:
			n.setColor(AppConstraint.C_PRIMARY);
			n.setMessage(getMessage(bill.getId(), bill.getStatus().getValue()));
			factory.getObject().save(n);
			break;
		case 3:
			n.setColor(AppConstraint.C_DANGER);
			n.setMessage(getMessage(bill.getId(), bill.getStatus().getValue()));
			factory.getObject().save(n);
			break;
		case 4:
			n.setColor(AppConstraint.C_WARNING);
			n.setMessage(getMessage(bill.getId(), bill.getStatus().getValue()));
			factory.getObject().save(n);
			break;
		case 5:
			n.setColor(AppConstraint.C_SUCCESS);
			n.setMessage(getMessage(bill.getId(), bill.getStatus().getValue()));
			factory.getObject().save(n);
			break;
		default:
			break;
		}
	}
	public String getMessage(Integer id,String status) {
		StringBuilder builder=new StringBuilder();
		builder.append("Đơn trả số ");
		builder.append(id);
		builder.append(" ");
		builder.append(status);
		return builder.toString();
	}
}
