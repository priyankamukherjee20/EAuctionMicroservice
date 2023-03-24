package com.eAution.eAuction.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {

	@Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private AuthenticationException authException;
    
    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCommence() {
		
		try {
			jwtAuthenticationEntryPoint.commence(request, response, authException);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}