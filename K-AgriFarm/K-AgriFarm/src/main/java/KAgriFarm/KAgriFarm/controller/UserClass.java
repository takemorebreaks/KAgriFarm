package KAgriFarm.KAgriFarm.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.service.UserServiceClass;

@RestController
public class UserClass {
	@Autowired
	UserServiceClass userServiceClass;
	@PostMapping("/RegisterNewUser")
public UserModel CreateNewUser(@RequestBody UserModel user) throws Exception
{
	userServiceClass.CreatenewUser(user);
	return user;	
}
	public boolean FindUserId(UserModel user)throws Exception{
		boolean existOrNot=userServiceClass.findUserByUserId(user.getId());
		return existOrNot;
	}
}
