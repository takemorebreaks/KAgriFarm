package KAgriFarm.KAgriFarm.controller;

import java.util.regex.Pattern;

import javax.management.relation.Role;

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
import KAgriFarm.KAgriFarm.appconfig.jwtFilter;
import KAgriFarm.KAgriFarm.customeExceptionHandler.CredantialError;
import KAgriFarm.KAgriFarm.customeExceptionHandler.RegistrationDataValidationException;
import KAgriFarm.KAgriFarm.customeExceptionHandler.UserAlreadyExist;
import KAgriFarm.KAgriFarm.model.AuthResponse;
import KAgriFarm.KAgriFarm.model.EnumRole;
import KAgriFarm.KAgriFarm.model.LoginRequest;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Authentication")
public class AuthController {
@Autowired	
private userRepository userrepo;
@Autowired	
private CustomeUserDetailsService customeUserDetailsService;
@Autowired	
private JWTprovider jwTprovider;
@Autowired	
private PasswordEncoder passwordEncoder;
    @PostMapping("/Registers")
	public AuthResponse Signup(@RequestBody UserModel user)throws Exception{
		String email=user.getEmail();
		String password=user.getPassword();
		String fullname=user.getFullname();
		String role=user.getRole().getName();
	    validateUser(user);
		UserModel userData=userrepo.findByEmail(email);

		if(userData!=null)
		{
			 throw new UserAlreadyExist("Emaliid already exist, please try another one...");
		}
		UserModel createnewUser=new UserModel();
		createnewUser.setEmail(email);
		createnewUser.setPassword(passwordEncoder.encode(password));
		createnewUser.setFullname(fullname);
		createnewUser.setRole(user.getRole()); 
		UserModel savedUser=userrepo.save(createnewUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String token=jwTprovider.generateToken(user);
		AuthResponse response=new AuthResponse();
//		response.setJwt(token);
		response.setMessage("Successfully registered...");
		return response;
}
    @PostMapping("/loginAuthenticationCheck")
    public AuthResponse Signin(@RequestBody LoginRequest login)throws Exception{
    	String Username=login.getEmail();
    	String Password=login.getPassword();
    	UserModel LoginAuth = new UserModel();
    	UserDetails authentication=authenticate(Username,Password);
    	UserModel userData=customeUserDetailsService.PassUserData(Username);
    	LoginAuth.setEmail(userData.getEmail());
    	LoginAuth.setPassword(userData.getPassword());
    	LoginAuth.setRole(userData.getRole());
    	String token=jwTprovider.generateToken(LoginAuth);
    	AuthResponse response=new AuthResponse();
    	response.setJwt(token);
    	response.setMessage("SingUp Successfull...");
    	response.setUserName(userData.getFullname());
    	return response;
    	
    }
    private UserDetails authenticate(String username, String password) throws Exception {
        System.out.println("inside the Authentication..." + username);
            UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);

            if (userDetails == null) {
                throw new CredantialError("Authentication failed, Incorrect credentials.please verify...");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            	 throw new CredantialError("Authentication failed, please verify the password...");
            }

            return userDetails;
        
    }
    public boolean isRoleValid(String roleValue) {
        for (EnumRole enumRole : EnumRole.values()) {
        	if (enumRole.name().equals(roleValue)) {
                return true; // Role value found in enum list
            }
        }
        return false; // Role value not found in enum list

}
    public void validateUser(UserModel user) throws Exception  {
    if (user == null) {
        throw new RegistrationDataValidationException("User object cannot be null");
    }

    String email = user.getEmail();
    String password = user.getPassword();
    String fullname = user.getFullname();
    String roleName=user.getRole().getName();
	Long role=user.getRole().getId();

    if (email == null || email.isEmpty()||!isValidEmail(email)) {
        throw new RegistrationDataValidationException("Email cannot be null or empty");
    }

    if (password == null || password.isEmpty()) {
        throw new RegistrationDataValidationException("Password cannot be null or empty");
    }

    if (fullname == null || fullname.isEmpty()) {
        throw new RegistrationDataValidationException("Full name cannot be null or empty");
    }

    if (!isRoleValid(roleName) || roleName.isEmpty()) {
        throw new RegistrationDataValidationException("Role name cannot be null or empty");
    }
}
    private boolean isValidEmail(String email) {
        // Simple email validation using regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    }
