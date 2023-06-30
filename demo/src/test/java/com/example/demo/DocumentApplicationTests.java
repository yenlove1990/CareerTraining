package com.example.demo;

import com.example.demo.Controller.DocumentController;
import com.example.demo.DAO.DocumentDAO;
import com.example.demo.Entity.Document;
import com.example.demo.Service.AlreadyExistingException;
import com.example.demo.Service.DocumentService;
import com.example.demo.Service.NotExistException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class DocumentApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	DocumentDAO dao;

	@Test
	void contextLoads() throws Exception {

		List<Document> list = new ArrayList<>();
		Document document1 = new Document();
		document1.setId(1);
		document1.setContent("Hi");

		list.add(document1);
		when(dao.findAll()).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/documents")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"content\":\"Hi\"}]"));
	}
	@Test
	void deleteDocumentTest() throws Exception {
		List<Document> list = new ArrayList<>();
		Document document = new Document();
		document.setId(1);
		document.setContent("Hi");
		list.add(document);

		when(dao.findAll()).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/documents")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"content\":\"Hi\"}]"));

		when(dao.existsById(1)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.delete("/documents/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Document deleted id :1"));

		verify(dao, times(1)).deleteById(1);
	}
	@Test
	void saveDocumentTest() throws Exception {
		Document document = new Document();
		document.setId(1);
		document.setContent("Hi");

		when(dao.save(any(Document.class))).thenReturn(document);

		mockMvc.perform(MockMvcRequestBuilders.post("/documents")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\": 1, \"content\": \"Hi\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Document created!"));

		verify(dao, times(1)).save(any(Document.class));
	}

	@Test
	void getDocumentTest() throws Exception {
		Document document = new Document();
		document.setId(1);
		document.setContent("Hi");

		when(dao.findById(1)).thenReturn(Optional.of(document));

		mockMvc.perform(MockMvcRequestBuilders.get("/documents/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hi"));

		verify(dao, times(1)).findById(1);
	}
	@Test
	void updateDocumentTest() throws Exception {
		Document document = new Document();
		document.setId(1);
		document.setContent("Hi");

		when(dao.existsById(1)).thenReturn(true);
		when(dao.save(any(Document.class))).thenReturn(document); // 使用any()匹配参数

		mockMvc.perform(MockMvcRequestBuilders.put("/documents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\": 1, \"content\": \"Updated content\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Updated content"));

		verify(dao, times(1)).existsById(1);
		verify(dao, times(1)).save(any(Document.class)); // 使用any()匹配参数
	}
	@Test
	void exceptionSaveDocumentTest() throws Exception {
		// 省略测试代码...

		// 模拟dao.save()方法抛出AlreadyExistingException异常
		when(dao.save(any(Document.class))).thenThrow(new AlreadyExistingException("Document already exists"));

		mockMvc.perform(MockMvcRequestBuilders.post("/documents")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\": 1, \"content\": \"Hi\"}"))
				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Document already exists"));

		verify(dao, times(1)).save(any(Document.class));
	}

	@Test
	void exceptionGetDocumentTest() throws Exception {
		// 省略测试代码...

		// 模拟dao.findById()方法返回空Optional，表示文档不存在
		when(dao.findById(1)).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get("/documents/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Document not found"));

		verify(dao, times(1)).findById(1);
	}

	@Test
	void exceptionUpdateDocumentTest() throws Exception {
		when(dao.existsById(1)).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.put("/documents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\": 1, \"content\": \"Updated content\"}"))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Document with id 1 not found"));

		verify(dao, times(1)).existsById(1);
		verify(dao, never()).save(any(Document.class));
	}
}
