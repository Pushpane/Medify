package com.psl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IStoreDAO;
import com.psl.entity.Store;

@RunWith(SpringRunner.class)
@SpringBootTest
class StoreServiceTest {
	
	@Autowired
	private StoreService storeService;
	
	@MockBean
	private IStoreDAO repository;


	@Test
	public void deleteUserTest() {
		storeService.deleteStore(1L);
		verify(repository, times(1)).deleteById(1L);
	}

}
