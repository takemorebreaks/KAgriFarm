package KAgriFarm.KAgriFarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.appconfig.JWTprovider;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.repository.UserRepositoryImplementation;
import KAgriFarm.KAgriFarm.repository.userRepository;
import KAgriFarm.KAgriFarm.service.KagriPostServiceClass;
import KAgriFarm.KAgriFarm.service.UserServiceClass;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Owner")
public class OwnerController {
	KagriPostServiceClass recipeServiceClass; 
	JWTprovider jwTprovider;
	UserRepositoryImplementation implementation;
	userRepository userRepository;
	UserServiceClass userServiceClass;
	@Autowired
	public OwnerController(UserRepositoryImplementation implementation,JWTprovider jwTprovider,KagriPostServiceClass recipeServiceClass,userRepository userRepository,UserServiceClass userServiceClass) {
	this.implementation=implementation;
	this.jwTprovider=jwTprovider;
	this.recipeServiceClass=recipeServiceClass;
	this.userRepository=userRepository;
	this.userServiceClass=userServiceClass;
	}
	@PostMapping("/CreateNewPost")
	public recipe CreateRecipe(@RequestBody recipe recipe,HttpServletRequest JwtToken) {
		// Get the token from the request attribute
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId = jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		recipe createdRecipe = recipeServiceClass.CreateRecipe(recipe, user);
		
		return createdRecipe;
	}

	@PostMapping("/UpdateOldPost")
	public recipe UpdateOldRecipe(@RequestBody recipe recipe, HttpServletRequest JwtToken)
			throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		Long UserId = user.getId();
		recipe UpdatedRecipe = recipeServiceClass.Updaterecipe(recipe, UserId);
		return UpdatedRecipe;
	}
	
	@PostMapping("/BlockUser")
	public String BlockUserByOwner(@RequestParam(name="usermailid")String usermailid,HttpServletRequest JwtToken)
			throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		UserModel BlockUser=implementation.findByEmail(usermailid);
		boolean isblocked=BlockUser.isBlockUser();
		if(isblocked) {
			return "User is already blocked...";
		}
		else {
			BlockUser.setBlockUser(true);
			userServiceClass.BlockUserByOwner(BlockUser);
		}
		return usermailid;	
	}
	@PostMapping("/UnBlockUser")
	public String UnBlockUserByOwner(@RequestParam(name="usermailid")String usermailid,HttpServletRequest JwtToken)
			throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		UserModel BlockUser=implementation.findByEmail(usermailid);
		boolean isblocked=BlockUser.isBlockUser();
			BlockUser.setBlockUser(false);
			userServiceClass.BlockUserByOwner(BlockUser);
		return "User Unblocked...";	
	}


	@PostMapping("/LikePost")
	public recipe LikePost(@RequestParam(name = "ImageId") long ImageId,HttpServletRequest JwtToken) throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		recipe UpdatedRecipe = recipeServiceClass.likeRecipe(ImageId, user);
		return null;
	}
	@GetMapping("/PaginationFindAllPost")
	public List<recipe> PaginationFindAllPost(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize) throws Exception {
		List<recipe> AllPost = recipeServiceClass.getAllrecipeByPagination(pageNo,pageSize);
		if (AllPost.isEmpty()) {
			throw new Exception("Some error occure , try again...");
		}
		return AllPost;
	}

	@GetMapping("/FindAllPost")
	public List<recipe> ShowAllPost() throws Exception {
		List<recipe> AllPost = recipeServiceClass.findallrecipe();
		if (AllPost.isEmpty()) {
			throw new Exception("Some error occure , try again...");
		}
		return AllPost;
	}

	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
		try {
			recipeServiceClass.Deleterecipe(id);
			return ResponseEntity.ok("Recipe with ID " + id + " deleted successfully");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete recipe with ID " + id + ": " + e.getMessage());
		}
	}
	@DeleteMapping("/User/{id}")
	public ResponseEntity<String>deleteUser(@PathVariable Long id){
		try {
			userServiceClass.DeleteUserByUserId(id);
			return ResponseEntity.ok("User with ID " + id + " deleted successfully");
		}
	catch (Exception e) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Failed to delete user with ID " + id + ": " + e.getMessage());
	}
	}
	@PostMapping("/UpdateOwnerProfile")
	public UserModel UpdateOwnerProfile(@RequestBody UserModel UpdateData, HttpServletRequest JwtToken)
			throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		UserModel UpdatedUserData = userServiceClass.UpdateUserData(UpdateData,user);
		return UpdatedUserData;
	}


}
