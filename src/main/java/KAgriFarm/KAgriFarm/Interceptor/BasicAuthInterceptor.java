package KAgriFarm.KAgriFarm.Interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import KAgriFarm.KAgriFarm.appconfig.JWTprovider;
import KAgriFarm.KAgriFarm.appconfig.jwtFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class BasicAuthInterceptor implements HandlerInterceptor{
	@Autowired
    private jwtFilter filter;
	@Autowired
	private JWTprovider jwTprovider;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		  log.info("request.getRequestURI() "+request.getRequestURI());
		  log.info("request.getRequestURI() "+request.getRequestURI());
		log.info("BasicAuthInterceptor::preHandle()");
		if (!(request.getRequestURI().equals("/Authentication/loginAuthenticationCheck") || request.getRequestURI().equals("/Authentication/Registers"))) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// Extract the token from the Authorization header
			String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
			if(ValidateRoleAndIdFromToken(token,request)) {
				request.setAttribute("Authorization", token);
			}
			else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
	}
		else {
			return true;
		}
		return true;
	}

@Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("BasicAuthInterceptor::postHandle()");
}

@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    log.info("BasicAuthInterceptor::afterCompletion()"); 
}
private boolean ValidateRoleAndIdFromToken(String token,HttpServletRequest request) {
	Map<String, String> roleIdAndRoleName = new HashMap<>();
	  log.info("request.getRequestURI() "+request.getRequestURI());
	//stored all apis related to roles of users...
	Map<String,String>CollectionOFApis=new HashMap<>();
	CollectionOFApis.put("OWNER","/Owner");
	CollectionOFApis.put("MANAGER","/Manager");
	CollectionOFApis.put("HR","/Hr");
	CollectionOFApis.put("USER","/User");
	CollectionOFApis.put("VISITORUSER","/VisitorUser");
	String CheckApi=request.getRequestURI();
	roleIdAndRoleName=jwTprovider.ExtractRollFromToken(token);
	String roleNumber = roleIdAndRoleName.get("RoleNumber");
	String roleName = roleIdAndRoleName.get("RoleName"); 
	// Check if roleName matches any role in collectionOfApis
    for (Map.Entry<String, String> entry : CollectionOFApis.entrySet()) {
        if (entry.getKey().equals(roleName)) {
            // Role matched, get the corresponding API value
            String roleApi = entry.getValue();
            
            // Now check if the requested API matches the role's API
            if (CheckApi.startsWith(roleApi)) {
                return true; // Role is authorized to access this API
            } else {
                return false; // Role is not authorized to access this API
            }
        }
    }
	return false;
}
}
