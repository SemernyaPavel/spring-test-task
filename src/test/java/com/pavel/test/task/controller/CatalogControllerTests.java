package com.pavel.test.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.test.task.entity.Item;
import com.pavel.test.task.repo.ItemRepository;
import com.pavel.test.task.web.controller.CatalogController;

@RunWith(SpringJUnit4ClassRunner.class)
public class CatalogControllerTests {

	private MockMvc mockMvc;
	
	@Mock
	private ItemRepository itemRepository;
	
	@InjectMocks
	private CatalogController Cc;
	
	@Before
	public void setUp() throws Exception{
		 mockMvc = MockMvcBuilders.standaloneSetup(Cc)
	                .build();
	}
	
	@Test
	public void testCatalog() throws Exception {
		 mockMvc.perform(get("/user/catalog"))
		 .andExpect(status().isOk());
	}

	@Test
	public void testFilterByName() throws Exception {
		Item item1 = new Item(1L, "Philips hairdryer", "Some description", "hairdryer");
		Item item2 = new Item(2L, "Samsung GT50", "Some description", "TV");
		Item item3 = new Item(3L, "IPhone", "Apple", "telephone");
		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);

		mockMvc.perform(post("/user/catalog/searchByName")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(new ObjectMapper().writeValueAsString(item3.getItemName())))
        		.andExpect(status().isOk());
	}

	@Test
	public void testFilterByTags() throws Exception {
		Item item1 = new Item(1L, "Philips hairdryer", "Some description", "hairdryer");
		Item item2 = new Item(2L, "Samsung GT50", "Some description", "TV");
		Item item3 = new Item(3L, "IPhone", "Apple", "telephone");
		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);

		mockMvc.perform(post("/user/catalog/searchByName")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("TV"))
        		.andExpect(status().isOk());

	}
}


