package com.example.hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GreetingServiceTests {
	
	@Test
    public void testGetMessage() {
        // Create an instance of the service class
        GreetingService myService = new GreetingService();

        // Call the method you want to test
        String result = myService.greet();

        // Assert the result
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testMockedService() {
        // Create a mock instance of the service class
    	GreetingService myServiceMock = mock(GreetingService.class);

        // Define the behavior of the mock
        when(myServiceMock.greet()).thenReturn("Mocked Hello, World!");

        // Call the method you want to test
        String result = myServiceMock.greet();

        // Assert the result
        assertEquals("Mocked Hello, World!", result);
    }

}
