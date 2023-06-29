package com.example.demo;

import com.example.demo.DAO.DocumentDAO;
import com.example.demo.Entity.Document;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class DocumentApplicationTests {
	@MockBean
	DocumentDAO dao;

	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
		List<Document> list = new ArrayList();
		Document document1 = new Document();
		document1.setId(1);
		document1.setContent("Hi");

		list.add(document1);
		when(dao.findAll()).thenReturn(list);
//		mockMvc.perform(MockMvcRequestBuilders.get("/documents)"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().json("[{}]"));
	}

}
