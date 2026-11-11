package org.example.donacionesfrontend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // Si no hay un token guardado en la sesión lo redirigimos al login
        if (session.getAttribute("jwtToken") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}