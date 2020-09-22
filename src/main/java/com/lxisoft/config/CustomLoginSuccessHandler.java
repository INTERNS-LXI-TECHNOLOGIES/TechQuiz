package com.lxisoft.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Configuration
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = (User)authentication.getPrincipal();
        System.out.println("principal" + user.getUsername());
        boolean isAdmin = false;

        Iterator<GrantedAuthority> grantedAuthorityIterator = user.getAuthorities().iterator();

        while (grantedAuthorityIterator.hasNext())
        {
            if(grantedAuthorityIterator.next().getAuthority().equalsIgnoreCase("ROLE_ADMIN"))
            {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            response.sendRedirect("techquiz");
        } else {
            response.sendRedirect("userview");
        }
    }
}
