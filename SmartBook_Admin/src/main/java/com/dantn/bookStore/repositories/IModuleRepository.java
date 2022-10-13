package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.Module;

@Repository
public interface IModuleRepository extends JpaRepository<Module,Integer > {

}
