package com.dantn.bookStore.websocket;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.dantn.bookStore.dto.response.MessageBillResponse;

import org.springframework.messaging.simp.SimpMessagingTemplate;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class SocketMessage {
	@Autowired
	private SimpMessagingTemplate template;
	@MessageMapping("/socket/bill")
	public void BillSocket(Principal principal,@Header("simpSessionId") String sessionId) {
		MessageBillResponse billResponse=new MessageBillResponse();
		template.convertAndSendToUser(principal.getName(), "/queue/bill",billResponse);
	}
	@MessageMapping("/socket/reset")
	@SendTo("/topic/bill")
	public void reset() {
		
	}
}
