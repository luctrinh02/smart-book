package com.dantn.bookStore.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dantn.bookStore.dto.request.ShipmentRequest;
import com.dantn.bookStore.services.ShipmentService;

@RestController
public class ShipmentApi {
    @Autowired
    private ShipmentService service;
    @GetMapping("/api/admin/shipment")
    public ResponseEntity<?> get(Principal principal){
        return ResponseEntity.ok(service.getByUser(principal));
    }
    @PutMapping("/api/admin/shipment")
    public ResponseEntity<?> change(@RequestBody ShipmentRequest request){
        return ResponseEntity.ok(service.changeStatus(request));
    }
}
