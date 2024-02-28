package KAgriFarm.KAgriFarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.service.recipeServiceClass;

@RestController
public class recipeClass {
@Autowired
recipeServiceClass recipeServiceClass;
@PostMapping("/CreateNewPost/{userId}")
public recipe createRecipe(@RequestBody recipe recipe,@PathVariable Long userId)
{
	recipe createdRecipe=recipeServiceClass.CreateRecipe(recipe, null);
	return createdRecipe;
	
}
}
