package KAgriFarm.KAgriFarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.appconfig.JWTprovider;
import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.repository.UserRepositoryImplementation;
import KAgriFarm.KAgriFarm.service.KagriPostServiceClass;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/VisitorUser")
public class VisitorUserController {
	@Autowired
	KagriPostServiceClass recipeServiceClass; 
	@Autowired
	JWTprovider jwTprovider;
	@Autowired
	UserRepositoryImplementation implementation;
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


}
