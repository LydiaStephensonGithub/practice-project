package com.example.ducks.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import nl.jqno.equalsverifier.EqualsVerifier;

@ActiveProfiles("test") //sets current profile to test
public class DuckTest {
	
	//remember to import the equals verifier dependency
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(Duck.class).usingGetClass().verify();
	}
	
	@Test
	public void getAndSetTest() {
		//create empty duck object
		Duck duck = new Duck();
		
		//use setter method to add values to each field
		duck.setId(1L);
		duck.setAge(4);
		duck.setName("Duck");
		duck.setHabitat("pond");
		duck.setGender("female");
		
		//check that the fields are not null
		assertNotNull(duck.getId());
		assertNotNull(duck.getAge());
		assertNotNull(duck.getName());
		assertNotNull(duck.getHabitat());
		assertNotNull(duck.getGender());
		
		//make sure that the getters have the correct value
		assertEquals(duck.getId(), 1L);
		assertEquals(duck.getAge(), 4);
		assertEquals(duck.getName(), "Duck");
		assertEquals(duck.getHabitat(), "pond");
		assertEquals(duck.getGender(), "female");
	}
	
	@Test
	public void allArgsConstructor() {
		Duck duck = new Duck(1L, 4, "Duck", "pond", "female");
		
		assertEquals(duck.getId(), 1L);
		assertEquals(duck.getAge(), 4);
		assertEquals(duck.getName(), "Duck");
		assertEquals(duck.getHabitat(), "pond");
		assertEquals(duck.getGender(), "female");
	}
	
	@Test
	public void noIdConstructor() {
		Duck duck = new Duck(4, "Duck", "pond", "female");
		
		assertEquals(duck.getAge(), 4);
		assertEquals(duck.getName(), "Duck");
		assertEquals(duck.getHabitat(), "pond");
		assertEquals(duck.getGender(), "female");
	}
}
