package com.dantn.bookStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.entities.User;
import com.dantn.bookStore.entities.UserSearch;

@Repository
public interface IUserSearchRepository extends JpaRepository<UserSearch, Integer>{
	List<UserSearch> findTop2ByUserOrderByIdDesc(User user);
}
