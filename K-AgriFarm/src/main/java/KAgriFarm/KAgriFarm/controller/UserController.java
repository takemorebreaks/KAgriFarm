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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.appconfig.JWTprovider;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.repository.UserRepositoryImplementation;
import KAgriFarm.KAgriFarm.service.KagriPostServiceClass;
import KAgriFarm.KAgriFarm.service.UserServiceClass;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/User")
public class UserController {
	@Autowired
	KagriPostServiceClass recipeServiceClass; 
	@Autowired
	JWTprovider jwTprovider;
	@Autowired
	UserRepositoryImplementation implementation;
	@Autowired
	UserServiceClass userServiceClass;

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
	@PostMapping("/UpdateUserProfile")
	public UserModel UpdateUserProfile(@RequestBody UserModel UpdateData, HttpServletRequest JwtToken)
			throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		UserModel UpdatedUserData = userServiceClass.UpdateUserData(UpdateData,user);
		return UpdatedUserData;
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

	@GetMapping("/FindPostById")
	public recipe FindPostById(@RequestParam(name = "PostId") long PostId,HttpServletRequest JwtToken) throws Exception {
		recipe recipe= recipeServiceClass.FindrecipeById(PostId);
		if (recipe==null) {
			throw new Exception("Some error occure , try again...");
		}
		return recipe;
	}
	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<String> deleteRecipe(@PathVariable Long id,HttpServletRequest JwtToken) {
		try {
			String token = (String) JwtToken.getAttribute("Authorization");
			String EmailId=jwTprovider.EmailId(token);
			UserModel user = implementation.findByEmail(EmailId);
			recipeServiceClass.DeletePostAfterValidation(id,user);
			return ResponseEntity.ok("Recipe with ID " + id + " deleted successfully");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
	@PostMapping("/SavePostById")
	public recipe SavePostById(@RequestParam(name = "PostId") long PostId,HttpServletRequest JwtToken) throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		UserModel user = implementation.findByEmail(EmailId);
		String UpdatedRecipe = recipeServiceClass.SavePostById(user, PostId);
		return null;
	}
	@GetMapping("/SavedPostByUserId")
	public List<recipe> SavedPostByUserId(HttpServletRequest JwtToken) throws Exception {
		String token = (String) JwtToken.getAttribute("Authorization");
		String EmailId=jwTprovider.EmailId(token);
		List<recipe> SavedPostId = recipeServiceClass.SavedPostByUserId(EmailId);
		return SavedPostId;
	}
}





