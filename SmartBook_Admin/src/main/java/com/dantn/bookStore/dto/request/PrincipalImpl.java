package com.dantn.bookStore.dto.request;

import java.security.Principal;

public class PrincipalImpl implements Principal{
	private String username;
	
	public PrincipalImpl(String username) {
		super();
		this.username = username;
	}
	
	public PrincipalImpl() {
		super();
	}

	@Override
	public String getName() {
		return username;
	}
	
}
