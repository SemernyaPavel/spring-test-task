package com.pavel.test.task.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pavel.test.task.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{
	List<Item> findByItemName(String itemName);
	List<Item> findByTags(String tags);
	Optional<Item> findById(Long id);
}
