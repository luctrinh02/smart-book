package com.dantn.bookStore.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.entities.Module;
import com.dantn.bookStore.services.ModuleService;

@RestController
@RequestMapping("/api/module")
public class ModuleApi {
	@Autowired
	private ModuleService moduleService;
	
	@GetMapping("/getModule")
	public ResponseEntity<?> getModule() {
	    HashMap<String, Object> mapReturn = new HashMap<String, Object>();
	    List<Module> listModule = moduleService.getAll();
	    listModule.sort((Module m1, Module m2) -> Integer.compare(m1.getIndexModule(),m2.getIndexModule()));
	    mapReturn.put("data", listModule);
	    mapReturn.put("listSubModule", getListSubModule(listModule));
        return ResponseEntity.ok(mapReturn);
	}
	
	 private HashMap<String, Object> getListSubModule(List<Module> listModule) {
	        HashMap<String, Object> mapReturn = new HashMap<String, Object>();
	        for (Module module : listModule) {
	            mapReturn.put(module.getId() + "", module.getSubs());
	        }
	        return mapReturn;
	  }
}
