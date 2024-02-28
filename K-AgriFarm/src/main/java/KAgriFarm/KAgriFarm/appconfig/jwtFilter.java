package KAgriFarm.KAgriFarm.appconfig;

import java.io.IOException;

import javax.security.auth.login.AccountExpiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import KAgriFarm.KAgriFarm.Interceptor.BasicAuthInterceptor;
import KAgriFarm.KAgriFarm.customeExceptionHandler.CredantialError;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.UserRepositoryImplementation;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.MalformedJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class jwtFilter extends OncePerRequestFilter {
   @Autowired
   private userRepository userRepository;
   @Autowired
   private UserRepositoryImplementation implementation;
    

//    @Autowired
//    private JWTprovider jwTprovider;
    
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    	
        String token = null;
        String emailId = null;
        //JWTprovider jwTprovider=new JWTprovider(userRepository);
        log.info("doo filterrrr");
            // Skip JWT validation for login and registration endpoints
            if (isLoginOrRegistrationEndpoint(request.getRequestURI())) {
            	 log.info("request.getRequestURI() "+request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
                JWTprovider jwTprovider = new JWTprovider();
                log.info(jwTprovider.EmailId(token));
                emailId = jwTprovider.EmailId(token);
                // Validate and extract emailId from the token
                try {
					boolean tokenValidation=jwTprovider.validateToken(token);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
                try {
					if(emailId!=null && jwTprovider.validateToken(token)) {
					// Authenticate the user
					Authentication authentication = new UsernamePasswordAuthenticationToken(emailId, null, null);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					// Continue the filter chain
					filterChain.doFilter(request, response);
					}
					else {
						 throw new CredantialError("Invalid Token...");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
            	 throw new CredantialError("Please Login...");
            }
        } 
        
    private String[] excludedEndpoints = { "/Authentication/loginAuthenticationCheck", "/Authentication/Registers" };

    private boolean isLoginOrRegistrationEndpoint(String string) {
    
        
        for (String endpoint : excludedEndpoints) {
            if (string.equals(endpoint)) {
            	 log.info("request.getRequestURI() "+string);
                return true; // Match found, it's a login or registration endpoint
            }
        }
        log.info("request.getRequestURI() "+string);
        return false; // No match found
    }

	
    
}

