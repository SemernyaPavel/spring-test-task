package com.pavel.test.task.web.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pavel.test.task.entity.Item;
import com.pavel.test.task.repo.ItemRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Catalog", description = "APIs for working with user catalog")
@Controller
@RequestMapping("/user")
public class CatalogController {
	
	private ItemRepository itemRepository;
	
	@Autowired
	public CatalogController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Operation(summary = "Show all item from db")
	@GetMapping(value = "/catalog")
	public String catalog(Model model) {
		Iterable<Item> items = itemRepository.findAll();
		model.addAllAttributes((Collection<?>) items);
		return "catalogPage";
	}
	
	@Operation(summary = "Filter item by name")
	@PostMapping(value = "/catalog/searchByName")
	public String filterByName(@RequestParam String filterByName,
			Map<String, Object> model) {
		Iterable<Item> items;
		
		if(filterByName != null && !filterByName.isEmpty()) {
			items = itemRepository.findByItemName(filterByName);
		}else {
			items = itemRepository.findAll();
		}
		
		model.put("items", items);
		return "redirect:/user/catalog";
	}

	@Operation(summary = "Filter item by tags")
	@PostMapping(value = "/catalog/searchByTags")
	public String filterByTags(@RequestParam String filterByTags,
			Map<String, Object> model) {
		Iterable<Item> items;
		
		if(filterByTags != null && !filterByTags.isEmpty()) {
			items = itemRepository.findByTags(filterByTags);
		}else {
			items = itemRepository.findAll();
		}
		
		model.put("items", items);
		return "cfghdhg";
	}
	
	
}