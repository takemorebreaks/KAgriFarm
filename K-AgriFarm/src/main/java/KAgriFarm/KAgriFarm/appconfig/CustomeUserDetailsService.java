package KAgriFarm.KAgriFarm.appconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
@Service
public class CustomeUserDetailsService implements UserDetailsService {
	@Autowired
	private userRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
	        UserModel user = userRepository.findByEmail(username);   
	        if (user == null) 
	        {
	        	return null;
	        }
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        return new User(user.getEmail(), user.getPassword(), authorities);
	    }
	}

	


