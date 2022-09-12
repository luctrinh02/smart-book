package com.dantn.bookStore.adapter;

public interface EntityToDto <Entity,DTO>{
	public DTO changeToDto(Entity entity);
}
