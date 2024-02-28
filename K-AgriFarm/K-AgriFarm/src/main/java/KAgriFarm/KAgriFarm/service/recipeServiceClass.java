package KAgriFarm.KAgriFarm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import KAgriFarm.KAgriFarm.interfaces.recipeInterface;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.repository.recipeRepository;
@Service
public class recipeServiceClass implements recipeInterface{
private final recipeRepository recipeRepository;
private final recipe recipeModelClass;
@Autowired
public recipeServiceClass(recipeRepository recipeRepository,recipe recipeModelClass) {
   this.recipeRepository=recipeRepository;
   this.recipeModelClass=recipeModelClass;
}

@Override
public recipe CreateRecipe(recipe recipe, UserModel user) {
	recipeModelClass.setTitle(recipe.getTitle());
	recipeModelClass.setImage(recipe.getImage());
	recipeModelClass.setLocalTime(recipe.getLocalTime());
	recipeModelClass.setUser(user);
	recipeModelClass.setDescription(recipe.getDescription());
	recipeRepository.save(recipeModelClass);
	return null;
}

@Override
public recipe FindrecipeById(Long id) throws Exception {
	Optional<recipe> optional=recipeRepository.findById(id);
	if(optional.isPresent())
	{
		return optional.get();
	}
	throw new Exception("Recipe not found with this id..."+id);
}

@Override
public void Deleterecipe(Long id) throws Exception {
	FindrecipeById(id);
	recipeRepository.deleteById(id);
}

@Override
public recipe Updaterecipe(recipe recipe, Long id) throws Exception {
	recipe oldrecipe=FindrecipeById(id);
	if(oldrecipe!=null)
	{
		if(recipe.getTitle()!=null) {
		oldrecipe.setTitle(recipe.getTitle());
		}
		if(recipe.getImage()!=null)
		{
			oldrecipe.setImage(recipe.getImage()); 
		}
		if(recipe.getDescription()!=null)
		{
			oldrecipe.setDescription(recipe.getDescription());
		}
		if(recipe.getLocalTime()!=null)
		{
			oldrecipe.setLocalTime(recipe.getLocalTime());
		}
		recipeRepository.save(oldrecipe);
	}
	return null;
}

@Override
public List<recipe> findallrecipe() {

	return recipeRepository.findAll();
}

@Override
public recipe likeRecipe(long recipeId, UserModel user) throws Exception {
	recipe detailsOfProduct=FindrecipeById(recipeId);
	if(detailsOfProduct.getLikes().contains(user.getId()))
			{
		    detailsOfProduct.getLikes().remove(user.getId());
			}
	else
	{
		detailsOfProduct.getLikes().add(user.getId());
	}
	return null;
}
}
