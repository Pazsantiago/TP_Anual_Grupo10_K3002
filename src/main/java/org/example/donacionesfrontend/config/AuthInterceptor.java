package org.example.donacionesfrontend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        //Es una validación para recursos estaticos y que no se rompa la petición.
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//
//        HandlerMethod metodo = (HandlerMethod) handler;
//        String carpetaDelController = metodo.getBeanType().getPackageName();
//
//        // Si está en la carpeta controllers (home o login), es público
//        if (carpetaDelController.endsWith(".controllers")) {
//            return true;
//        }
//
//        // Ahora si como esta en controller.otraCarpeta, entonces ahi pedimos el token para acceder
//        HttpSession session = request.getSession();
//        // Si no hay un token guardado en la sesión lo redirigimos al login
//        if (session.getAttribute("jwtToken") == null) {
//            response.sendRedirect("/login") ;
//            return false;
//        }
//
//        return true;
//    }
}