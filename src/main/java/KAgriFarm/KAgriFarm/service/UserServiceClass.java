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
   public boolean BlockUserByOwner(UserModel user) {
	   repository.save(user);
	   return true;
   }
   
   public boolean DeleteUserByUserId(long id) {
	    try {
	        repository.deleteById(id);
	        return true; // Deletion successful
	    } catch (Exception e) {
	        return false; // Deletion failed
	    }
	}

public UserModel UpdateUserData(UserModel updateData, UserModel OldData) throws Exception {
	if(OldData!=null&&updateData!=null)
	{
		if(updateData.getEmail()!=null) {
			OldData.setEmail(updateData.getEmail());
		}
		if(updateData.getPassword()!=null) {
			OldData.setPassword(updateData.getPassword());
		}
		if(updateData.getFullname()!=null) {
			OldData.setFullname(updateData.getFullname());
		}		
		repository.save(OldData);
		return OldData;
	}
	else{
		throw new Exception("Some Error occured...");
	}
}

}
