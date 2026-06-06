package com.examease.config;

import com.examease.service.WellnessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ProfileInterceptor implements HandlerInterceptor {

    private final WellnessService wellnessService;

    public ProfileInterceptor(WellnessService wellnessService) {
        this.wellnessService = wellnessService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // Skip check for static resources, error page, and profile setup itself
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/") 
            || uri.startsWith("/profile/setup") || uri.startsWith("/h2-console") || uri.equals("/error")) {
            return true;
        }

        if (wellnessService.getProfile() == null) {
            response.sendRedirect("/profile/setup");
            return false;
        }
        
        return true;
    }
}
