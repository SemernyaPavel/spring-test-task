package com.pavel.test.task.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pavel.test.task.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
	Cart findByItemsId(Long id);
}
