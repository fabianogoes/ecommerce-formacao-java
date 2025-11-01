package com.ecommerce.config;

import com.ecommerce.model.User;
import com.ecommerce.repositoty.UserRepository;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AutoLoginConfig implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ServletContext servletContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Verificar se o profile 'admin' está ativo
        if (Arrays.asList(environment.getActiveProfiles()).contains("admin")) {
            System.out.println("=".repeat(60));
            System.out.println("AUTO LOGIN ADMIN MODE ENABLED");
            System.out.println("=".repeat(60));

            // Buscar usuário admin
            Optional<User> adminUser = userRepository.findByEmail("admin@admin.com");

            if (adminUser.isPresent()) {
                System.out.println("Admin user found: " + adminUser.get().getEmail());
                System.out.println("Admin will be automatically logged in on first request");
                System.out.println("Navigate to: http://localhost:8080/admin");
                System.out.println("=".repeat(60));

                // Armazenar o admin user em um atributo da aplicação para uso posterior
                servletContext.setAttribute("autoLoginUser", adminUser.get());
                System.out.println("Auto-login user stored in ServletContext");
            } else {
                System.out.println("WARNING: Admin user not found!");
                System.out.println("=".repeat(60));
            }
        }
    }
}
