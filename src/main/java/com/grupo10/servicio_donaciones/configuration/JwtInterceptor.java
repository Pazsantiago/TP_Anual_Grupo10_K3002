package com.grupo10.servicio_donaciones.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class JwtInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest currentRequest = attributes.getRequest();
            HttpSession session = currentRequest.getSession(false);

            if (session != null && session.getAttribute("jwtToken") != null) {
                String token = (String) session.getAttribute("jwtToken");
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
        }

        return execution.execute(request, body);
    }
}