package KAgriFarm.KAgriFarm.appconfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtFilter extends OncePerRequestFilter {
@Autowired
private JWTprovider jwTService;
@Autowired
userRepository Userrepository;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		  String token = null;
	      String emailId = null;
	      String authHeader = request.getHeader("Authorization");

	        if (authHeader != null && authHeader.startsWith("Bearer")) {
	            token = authHeader.substring(7);
	            try {
                    emailId=jwTService.EmailId(token);   
                    Authentication authentication= new UsernamePasswordAuthenticationToken(emailId,null,null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	            catch(Exception e) {
	            	throw new BadCredentialsException("Invalide token...");
			}
	        }
	        filterChain.doFilter(request, response);

	}
}
