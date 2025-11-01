package com.ecommerce.config;

import com.ecommerce.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Sempre criar ou obter a sessão
        HttpSession session = request.getSession(true);

        // Verificar se auto login está habilitado e usuário ainda não está logado
        if (session.getAttribute("currentUser") == null) {
            Object autoLoginUser = request.getServletContext().getAttribute("autoLoginUser");

            System.out.println("DEBUG - Interceptor - Path: " + request.getRequestURI());
            System.out.println("DEBUG - Interceptor - autoLoginUser from context: " + autoLoginUser);

            if (autoLoginUser != null) {
                session.setAttribute("currentUser", autoLoginUser);
                System.out.println("Auto-login: User " + ((User) autoLoginUser).getEmail() + " automatically logged in for session: " + session.getId());
            } else {
                System.out.println("DEBUG - Interceptor - autoLoginUser is null, checking ServletContext attributes");
            }
        } else {
            System.out.println("DEBUG - Interceptor - currentUser already in session: " + ((User) session.getAttribute("currentUser")).getEmail());
        }

        return true;
    }
}
