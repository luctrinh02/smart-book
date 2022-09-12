package com.dantn.bookStore.adapter;

public interface DtoToEntity<ENTITY> {
	public ENTITY changeToEntity(ENTITY e);
}
