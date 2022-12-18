package com.dantn.bookStore.ready;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.dantn.bookStore.dto.response.BookRedis;
import com.dantn.bookStore.repositories.ICommentRepository;

@Component
public class SyntheticComment implements CommandLineRunner{
	@Autowired
	private RedisTemplate<Object, Object> template;
	@Autowired
	private ICommentRepository repository;
	@Override
	public void run(String... args) throws Exception {
		List<BookRedis> list=repository.getComment();
		for(BookRedis x:list) {
			template.opsForValue().set(x.getBook().getId(), x);
		}
	}
}
