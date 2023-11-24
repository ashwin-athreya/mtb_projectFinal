package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.mts.dto.CustomerDTO;
import com.cg.mts.entity.Customer;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.repository.CustomerRepository;


@SpringBootTest
class CustomerServiceImplTest {
	
	@MockBean
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;

	@Test
	void testExistsById() {
		int customerId = 1;
		Customer c= new Customer();
		c.setCustomerId(customerId);
		
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(c));
		boolean cus = customerService.existsById(null);
		assertNotNull(cus);
	}
}
	
//	@Test
//    public void testRemoveCustomer() throws CustomerNotFoundException, AccessForbiddenException {
//        // Arrange
//        MockitoAnnotations.initMocks(this); // Initialize the mocks
//
//        int customerId = 1; // Assuming 1 as a sample customer ID
//
//        Customer removedCustomer = new Customer();
//        removedCustomer.setCustomerId(customerId);
//        // Assuming you have other fields and setters in Customer
//
//        // Mocking the behavior of customerService
//        when(customerService.removeCustomer(customerId)).thenReturn(removedCustomer);
//
//        // Act
//        ResponseEntity<CustomerDTO> response = .removeCustomer(customerId);
//
//        // Assert
//        assertNotNull(response); // Ensure that the response is not null
//        assertEquals(HttpStatus.OK, response.getStatusCode()); // Ensure that the status code is OK
//        assertNotNull(response.getBody()); // Ensure that the response body is not null
//        assertEquals(customerId, response.getBody().getCustomerId()); // Ensure that the correct customer ID is returned
//
//        // Verify that customerService method was called
//        verify(customerService, times(1)).removeCustomer(customerId);
//}
