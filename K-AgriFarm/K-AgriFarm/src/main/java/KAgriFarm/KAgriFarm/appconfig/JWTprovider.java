package KAgriFarm.KAgriFarm.appconfig;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTprovider {
	@Autowired
	userRepository repository;
	 private final long validityInMilliseconds = 3600000;
	 private static final String SECRET_KEY = "!@#$kjasnd3446567kjkj897897bdasjbdbd34235345346@#^#%$%^&&^&^&^&^"; // Replace with your actual secret key
	 Date now = new Date();
     Date validity = new Date(now.getTime() + validityInMilliseconds);
     //Generate Token...
	    public String generateToken(UserModel auth) {
	    	Map<String,Object>claims=new HashMap<>();
	    	String jwt=Jwts.builder()
	    	.setClaims(claims)
	    	.setSubject(auth.getEmail())
	    	.setIssuedAt(validity)
	    	.setExpiration(new Date(System.currentTimeMillis()+1000*60))
	    	.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
			return jwt;
	       
	    }
	    //Decode token...
	    private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	    //Extract all data...
	    public Claims ExtractAllClaims(String token) {
	    	Claims ExtractedToken=Jwts.parserBuilder().setSigningKey(getSignKey())
	    			.build()
	    			.parseClaimsJws(token)
	    			.getBody();
			return ExtractedToken;
	    }
	    public String EmailId(String token){
	        return extractClaim(token,Claims::getSubject);
	    }
	    public Date extractExpiration(String token){
	        return extractClaim(token,Claims::getExpiration);
	    }
	    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
	        final Claims claims = ExtractAllClaims(token);
	        return claimResolver.apply(claims);
	    }
	    private Boolean isTokenExpired(String token){
	        return extractExpiration(token).before(new Date());
	    }
	    public Boolean validateToken(String token){
	    	final String EmailId=EmailId(token);
	    	UserModel userDetails=repository.findByEmail(EmailId);
	    	return (EmailId.equals(userDetails.getEmail()))&&!isTokenExpired(token);
	    }
	    

	}