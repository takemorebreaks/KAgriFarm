package KAgriFarm.KAgriFarm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.repository.userRepository;
@Service
public class UserServiceClass {
@Autowired
userRepository repository;

public UserModel CreatenewUser(UserModel user) throws Exception
{
	UserModel emailexistOrNot = repository.findByEmail(user.getEmail());
	if(emailexistOrNot!=null)
	{
		 throw new Exception("this email already exist...");
	}
	repository.save(user);
	return user;
}

public boolean findUserByUserId(long id) {
    Optional<UserModel> userOptional = repository.findById(id);
    return userOptional.isPresent(); // Returns true if user with given ID exists, false otherwise
}

}
