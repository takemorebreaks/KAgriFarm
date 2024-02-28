package KAgriFarm.KAgriFarm.interfaces;

import java.util.List;

import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.model.recipe;

public interface recipeInterface {
public recipe CreateRecipe(recipe recipe,UserModel user);
public recipe FindrecipeById(Long id)throws Exception;
public void Deleterecipe(Long id)throws Exception;
public recipe Updaterecipe(recipe recipe,Long id)throws Exception;
public List<recipe> findallrecipe();
public recipe likeRecipe(long recipeId,UserModel user)throws Exception;
}
