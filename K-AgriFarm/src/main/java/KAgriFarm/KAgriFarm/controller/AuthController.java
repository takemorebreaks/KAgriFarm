package KAgriFarm.KAgriFarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.appconfig.CustomeUserDetailsService;
import KAgriFarm.KAgriFarm.appconfig.JWTprovider;
import KAgriFarm.KAgriFarm.customeExceptionHandler.CredantialError;
import KAgriFarm.KAgriFarm.customeExceptionHandler.UserAlreadyExist;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.Claims;

@RestController
//@RequestMapping("/auth")
public class AuthController {
@Autowired	
private userRepository userrepo;
@Autowired	
private CustomeUserDetailsService customeUserDetailsService;
@Autowired	
private JWTprovider jwTprovider;
@Autowired	
private PasswordEncoder passwordEncoder;


    @PostMapping("/Register")
	public AuthResponse Signup(@RequestBody UserModel user)throws Exception{
		String email=user.getEmail();
		String password=user.getPassword();
		String fullname=user.getFullname();
		UserModel userData=userrepo.findByEmail(email);
		if(userData!=null)
		{
			 throw new UserAlreadyExist("Emaliid already exist, please try another one...");
		}
		UserModel createnewUser=new UserModel();
		createnewUser.setEmail(email);
		createnewUser.setPassword(passwordEncoder.encode(password));
		createnewUser.setFullname(fullname);
		UserModel savedUser=userrepo.save(createnewUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwTprovider.generateToken(user);
		AuthResponse response=new AuthResponse();
		response.setJwt(token);
		response.setMessage("Successfully registered...");
		return response;
}
    @PostMapping("/loginAuthenticationCheck")
    public AuthResponse Signin(@RequestBody LoginRequest login)throws Exception{
    	String Username=login.getEmail();
    	String Password=login.getPassword();
    	UserModel LoginAuth = new UserModel();
    	LoginAuth.setEmail(Username);
    	LoginAuth.setPassword(Password);
    	Authentication authentication=authenticate(Username,Password);
    	String token=jwTprovider.generateToken(LoginAuth);
    	AuthResponse response=new AuthResponse();
    	response.setJwt(token);
    	response.setMessage("SingUp Successfull...");
    	return response;
    	
    }
    private Authentication authenticate(String username, String password) throws Exception {
        System.out.println("inside the Authentication..." + username);

        
            UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);

            if (userDetails == null) {
                throw new CredantialError("Username is not Invalide, please verify...");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            	 throw new CredantialError("Password is not matching, please verify password...");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
    }


}
