package com.pavel.test.task.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.test.task.entity.Item;
import com.pavel.test.task.repo.CartRepository;
import com.pavel.test.task.repo.ItemRepository;
import com.pavel.test.task.web.controller.AdminCatalogController;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class AdminCatalogControllerTests {
	
	private MockMvc mockMvc;

	@Mock
	private ItemRepository itemRepository;
	
	@Mock
	private CartRepository cartRepository;
	
	@InjectMocks
	private AdminCatalogController Acc;
	
	
	@Before
	public void setUp() throws Exception{
		 mockMvc = MockMvcBuilders.standaloneSetup(Acc)
	                .build();
	}
	
	 @Test
	 public void testCatalog() throws Exception {
		 mockMvc.perform(get("/admin/catalog"))
		 .andExpect(status().isOk());	 
	 }
		
	 @Test
	 public void testAddItems() throws JsonProcessingException, Exception {
		Item item = new Item();
		item.setId(5L);
		item.setItemName("Philips");
		item.setDescriptions("Some description");
		item.setTags("hairdryer");
		
        mockMvc.perform(post("/admin/catalog/addItem")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(item)))
        		.andExpect(status().isOk());
	 }
	 
	 @Test
	 public void testEditItemTest() throws Exception {
		Item item = new Item();
		item.setId(5L);
		item.setItemName("Philips");
		item.setDescriptions("Some description");
		item.setTags("hairdryer");
        itemRepository.save(item);

        item.setDescriptions("Some new description");
        
        mockMvc.perform(post("/admin/catalog/5/editItem")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(item)))
        		.andExpect(status().isOk());        	
	 }
	 
	 @Test
	 public void testDeleteItem() throws JsonProcessingException, Exception {
		 Item item = new Item();
			item.setId(5L);
			item.setItemName("Philips");
			item.setDescriptions("Some description");
			item.setTags("hairdryer");
	        itemRepository.save(item);
	        
	        mockMvc.perform(delete("/admin/catalog/5/editItem")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(new ObjectMapper().writeValueAsString(item)))
	        		.andExpect(status().isOk());
	 }
}
