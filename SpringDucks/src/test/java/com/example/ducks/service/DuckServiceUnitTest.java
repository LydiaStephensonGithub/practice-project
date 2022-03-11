package com.example.ducks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.ducks.entity.Duck;
import com.example.ducks.repo.DuckRepo;

@ActiveProfiles("test") //sets current profile to test
@SpringBootTest
public class DuckServiceUnitTest {
	
	//tells spring to insert this object into the class
	@Autowired
	private DuckService service;
	
	//mocking the repository class as we don't want to rely on the class itself
	@MockBean
	private DuckRepo repo;
	
	@Test
	public void createDuckTest() {
		Duck input = new Duck(6, "Duck", "pond", "male");
		Duck output = new Duck(1L, 6, "Duck", "pond", "male");
		
		//here we are testing actual method with the create method in service class
		Mockito.when(this.repo.save(input)).thenReturn(output);
		
		//here we are checking the expected value (output) is the same as the output when the create method in service is run
		assertEquals(output, this.service.create(input));
		
		//verifies that the repo is run 1 time and saves the input
		Mockito.verify(this.repo, Mockito.times(1)).save(input);
	}
	
	@Test
	public void readByIdTest() {
		Optional<Duck> optionalOutput = Optional.of(new Duck(1L, 6, "Duck", "pond", "male"));
		Duck output = new Duck(1L, 6, "Duck", "pond", "male");
		
		Mockito.when(this.repo.findById(Mockito.anyLong())).thenReturn(optionalOutput);
		
		assertEquals(output, this.service.readById(Mockito.anyLong()));
		
		Mockito.verify(this.repo, Mockito.times(1)).findById(Mockito.anyLong());
	}
	
	@Test 
	public void deleteTrueTest() {
		Mockito.when(this.repo.existsById(1L)).thenReturn(false);
		
		assertTrue(this.service.delete(1L));
		
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(1L);
		Mockito.verify(this.repo, Mockito.times(1)).existsById(1L);
	}
	
	@Test 
	public void deleteFalseTest() {
		Mockito.when(this.repo.existsById(1L)).thenReturn(true);
		
		assertFalse(this.service.delete(1L));
		
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(1L);
		Mockito.verify(this.repo, Mockito.times(1)).existsById(1L);
	}
}
