package com.pavel.test.task.web.controller;

import java.util.Collection; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavel.test.task.entity.Cart;
import com.pavel.test.task.entity.Item;
import com.pavel.test.task.repo.CartRepository;
import com.pavel.test.task.repo.ItemRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin Catalog", description = "APIs for working with item catalog")
@RestController
@RequestMapping("/admin/catalog")
public class AdminCatalogController {
	
private ItemRepository itemRepository;
private CartRepository cartRepository;
	
	@Autowired
	public AdminCatalogController(ItemRepository itemRepository, CartRepository cartRepository) {
		this.itemRepository = itemRepository;
		this.cartRepository = cartRepository;
	}
	
	
	@Operation(summary = "Show item catalog")
	@GetMapping
	public String catalog(Model model) {
		Iterable<Item> items = itemRepository.findAll();
		model.addAllAttributes((Collection<?>) items);
		return "catalogPage";
	}
	
	@Operation(summary = "Show add item page")
	@GetMapping("/addItem")
	public String addItem() {
		return "addItemPage";
	}
	
	@Operation(summary = "Add new item to db")
	@PostMapping(value = "/addItem")
	public String addItem(@RequestBody Item item) {
		itemRepository.save(item);
		return "redirect:/admin/catalog";
	}
	
	
	@Operation(summary = "Show edit item page")
	@GetMapping("/{id}/editItem")
	public String editItem(@PathVariable("id") Long id) {
		itemRepository.findById(id);
		return "editItemPage";
	}
	
	@Operation(summary = "Edit item and send message to admin, if this is item there is in user's cart")
	@PostMapping(value ="/{id}/editItem")
	public String editItem(@RequestParam Item item, Model model) {
		Cart carts = cartRepository.findByItemsId(item.getId());
			if(carts != null) {
				model.addAttribute("message", "Item now in cart. Do you want use force update?");
				return "redirect:/{id}/force_update";
			}
		
			Optional<Item> itemOptionalFromDb = itemRepository.findById(item.getId());
			Item itemFromDb = itemOptionalFromDb.get();
			itemFromDb.setDescriptions(item.getDescriptions());
			itemFromDb.setItemName(item.getItemName());
			itemFromDb.setTags(item.getTags());
			itemRepository.save(itemFromDb);
		return "redirect:/admin/catalog";
	}
	
	
	@Operation(summary = "Force update if item there is in user's cart")
	@PutMapping(value = "/{id}/force_update")
	public String forceUpdate(@RequestParam Item item) {
		Optional<Item> itemOptionalFromDb = itemRepository.findById(item.getId());
		Item itemFromDb = itemOptionalFromDb.get();
		itemFromDb.setDescriptions(item.getDescriptions());
		itemFromDb.setItemName(item.getItemName());
		itemFromDb.setTags(item.getTags());
		itemRepository.save(itemFromDb);
		return "redirect:/admin/catalog";
	}
	
	@Operation(summary = "Delete item from db")
	@DeleteMapping(value = "/{id}/delete")
	public String deleteItem(@PathVariable("id") Long id) {
		itemRepository.deleteById(id);
		return "redirect:/admin/catalog";
	}
}
