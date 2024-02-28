package KAgriFarm.KAgriFarm.appconfig;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.customeExceptionHandler.TokenValidationException;
import KAgriFarm.KAgriFarm.customeExceptionHandler.UserAlreadyExist;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.UserRepositoryImplementation;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.ExpiredJwtException;
import java.security.SignatureException;
//import javax.inject.Inject; 
//@Service("JWTprovider")
@Slf4j
@RestController 
public class JWTprovider {
    private final long validityInMilliseconds = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
    private static final String SECRET_KEY = "!@#$kjasnd3446567kjkj897897bdasjbdbd34235345346@#^#%$%^&&^&^&^&^"; // Replace with your actual secret key
//    @Autowired
//    public JWTprovider(userRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    
    // Generate Token...
    public String generateToken(UserModel auth) {
    	 Map<String, Object> claims = new HashMap<>();
    	    if (auth.getRole() == null || auth.getRole().getId() == null) {
    	        claims.put("RoleNumber", "5");
    	        claims.put("RoleName", "VISITORUSER");
    	    } else {
    	        claims.put("RoleNumber", auth.getRole().getId());
    	        claims.put("RoleName", auth.getRole().getName());
    	    }
        String jwt = Jwts.builder() 
                .setClaims(claims)
                .setSubject(auth.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        return jwt;
    }
	    //Decode token...
	    private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	    // Extract all data...
	    public Claims ExtractAllClaims(String token) {
	        try {
	            return Jwts.parserBuilder()
	                    .setSigningKey(getSignKey())
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody();
	        } catch (ExpiredJwtException e) {
	            throw new TokenValidationException("Token expired...", e);
	        } catch (MalformedJwtException e) {
	            throw new TokenValidationException("Malformed token...", e);
	        } catch (JwtException e) {
	            throw new TokenValidationException("Token validation failed for an unknown reason...", e);
	        }
	    }
	    public String EmailId(String token){
	    	try {
	        return extractClaim(token,Claims::getSubject);}
	    	catch(Exception e) {
	    		throw new TokenValidationException("Invalide Token...",e);
	    	}
	    }
	    public Date extractExpiration(String token){
	    	try{
	        return extractClaim(token,Claims::getExpiration);}
	    	catch(Exception e) {
	    		throw new TokenValidationException("Token Expired...",e);
	    	
	    	}
	    }
	    public Map<String,String> ExtractRollFromToken(String token){
	    	
			return ExtractRoleIdAndRoleName(token);  	
	    }
	    private Map<String, String> ExtractRoleIdAndRoleName(String token) {
	        final Claims claims = ExtractAllClaims(token);
	        Map<String, String> roleIdAndRoleName = new HashMap<>();
	        String roleName = claims.get("RoleName", String.class);
	        String roleNumber = String.valueOf(claims.get("RoleNumber", Long.class));
	       // String roleNumber=(claims.get("RoleNumber",String.class));
	        roleIdAndRoleName.put("RoleName", roleName);
	        roleIdAndRoleName.put("RoleNumber", roleNumber);
	        return roleIdAndRoleName;
	    }
	    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
	        final Claims claims = ExtractAllClaims(token);
	        return claimResolver.apply(claims);
	    }
	    private Boolean isTokenExpired(String token){
	        return extractExpiration(token).before(new Date());
	    }
	    
	    public Boolean validateToken( String token) throws Exception {
	    	final String EmailId=EmailId(token);
	    	UserRepositoryImplementation userRepository = new UserRepositoryImplementation();
	    	UserModel user= userRepository.findByEmail(EmailId);
	    	return (EmailId.equals(user.getEmail()))&&!isTokenExpired(token);	    	
	    }
	    

	}