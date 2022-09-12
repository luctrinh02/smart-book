package com.dantn.bookStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.UserStatus;

@Repository
public interface IUserStatusRepository extends JpaRepository<UserStatus,Integer >{

}
