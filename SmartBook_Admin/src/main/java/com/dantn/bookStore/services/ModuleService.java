package com.dantn.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantn.bookStore.entities.Module;
import com.dantn.bookStore.repositories.IModuleRepository;

@Service
public class ModuleService {
	@Autowired
	private IModuleRepository rep;
	
	public List<Module> getAll(){
		return rep.findAll();
	}
}
