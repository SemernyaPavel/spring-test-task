package com.pavel.test.task.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavel.test.task.entity.Cart;
import com.pavel.test.task.entity.Item;
import com.pavel.test.task.entity.User;
import com.pavel.test.task.repo.CartRepository;
import com.pavel.test.task.repo.ItemRepository;
import com.pavel.test.task.repo.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cart", description = "APIs for working with cart")
@RestController
@RequestMapping("/user/cart")
public class CartController {

	@Autowired
    public JavaMailSender emailSender;
	
	private CartRepository cartRepository;
	private UserRepository userRepository;
	private ItemRepository itemRepository;
	
	@Autowired
	public CartController(CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}
	
	@Operation(summary = "Add item to cart")
	@PostMapping(value = "/{id}/add")
	public String addItemToCart(@RequestParam Item item, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
		  User user = userRepository.findByUsername(userName);
		  Cart cart = user.getCart();
		  Optional<Item> newItem = itemRepository.findById(item.getId());
			  if(item == newItem.get()) {
				  model.addAttribute("message", "This item is already in the cart.");
				  return "redirect:/user/catalog";
			  }
		  if(newItem != null) {
          newItem.get().setCart(cart);
          cart.addItem(newItem.get());
	      cartRepository.save(cart);
		  }
		return "redirect:/user/cart";
	}
	
	@Operation(summary = "Show delete page")
	@GetMapping(value = "/{id}/remove")
	public String delete() {
		return "deleteFromCartPage";
	}
	
	@Operation(summary = "Delete item from cart")
	@DeleteMapping(value = "/{id}/remove")
	public String deleteFromCart(@PathVariable("id") Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Item> itemFromDb = itemRepository.findById(id);
        User user = userRepository.findByUsername(userName);
		Cart cart = user.getCart();
		for(Item item : cart.getItems()) {
			if(item.toString() == itemFromDb.toString()) {
				cart.getItems().remove(item);
				return "redirect:/user/cart";
			}
		}
		return "redirect:/user/catalog";
	}
	
	@Operation(summary = "Clean user cart and send message on email")
	@PostMapping(value = "/buy")
	public String buyItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        
        SimpleMailMessage message = new SimpleMailMessage();
        
        User user = userRepository.findByUsername(userName);
		user.getCart().getItems().clear();
		
		message.setTo(user.getEmail());
	    message.setSubject("Message After Buying");
	    message.setText("Hello, thank you for buying in our shop!");
	    
	    this.emailSender.send(message);
		return "redirect:/user/catalog";
	}
}
