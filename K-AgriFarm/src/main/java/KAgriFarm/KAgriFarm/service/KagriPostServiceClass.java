package KAgriFarm.KAgriFarm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import KAgriFarm.KAgriFarm.interfaces.recipeInterface;
import KAgriFarm.KAgriFarm.model.UserModel;
import KAgriFarm.KAgriFarm.model.recipe;
import KAgriFarm.KAgriFarm.repository.recipeRepository;
import KAgriFarm.KAgriFarm.repository.userRepository;
import io.jsonwebtoken.lang.Collections;
@Service
public class KagriPostServiceClass implements recipeInterface{
@Autowired
private  recipeRepository recipeRepository;
@Autowired
private  recipe recipeModelClass;
@Autowired
private userRepository userRepository;
@Override
public recipe CreateRecipe(recipe recipe, UserModel user) {
//	recipeModelClass.setTitle(recipe.getTitle());
//	recipeModelClass.setImage(recipe.getImage());
//	recipeModelClass.setLocalTime(recipe.getLocalTime());
	recipe.setUser(user);
//	recipeModelClass.setDescription(recipe.getDescription());
//	recipeModelClass.setVegitarian(recipe.isVegitarian());
	recipeRepository.save(recipe);
	return recipe;
}
@Override
public recipe FindrecipeById(Long id) throws Exception {
    recipe recipeData = recipeRepository.findById(id)
                                        .orElse(null);
    if (recipeData != null) {
        return recipeData;
    } else {
        throw new Exception("Recipe not found with this id: " + id);
    }
}
@Override
public List<recipe> FindAllPostByUserId(Long userId) throws Exception {
    // Find all posts by user ID
    List<recipe> allUserPosts = recipeRepository.findByUserId(userId);

    // Check if any posts are found
    if (allUserPosts.isEmpty()) {
        throw new Exception("No posts found for user ID: " + userId);
    }

    return allUserPosts;
}

@Override
public void Deleterecipe(Long id) throws Exception {
	FindrecipeById(id);
	recipeRepository.deleteById(id);
}
public void DeletePostAfterValidation(Long id, UserModel user) throws Exception {
    Optional<recipe> dataFromDBforDeletion = Optional.of(FindrecipeById(id));
    long userId=user.getId();
    if (dataFromDBforDeletion.get().getUser().getId()==userId) {
        recipeRepository.delete(dataFromDBforDeletion.get());
    } else {
        throw new Exception(user.getFullname()+"You are not authorized to delete...");
    }
}
@Override
public recipe Updaterecipe(recipe recipe, Long id) throws Exception {
	recipe oldrecipe=FindrecipeById(recipe.getId());
	if(oldrecipe.getUser().getId()==id)
	{
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
	}
	else{
		throw new Exception("You are not authorized to update this post...");
	}
	return null;
}

@Override
public List<recipe> findallrecipe() {
    List<recipe> allPosts = recipeRepository.findAll();
    return allPosts;
}
public List<recipe> getAllrecipeByPagination(int pageNo, int pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
    Page<recipe> pagingUser = recipeRepository.findAll(pageRequest);
    //pagingUser.hasContent(); -- to check pages are there or not
    return pagingUser.getContent();
}

@Override
public recipe likeRecipe(long recipeId, UserModel user) throws Exception {
	recipe detailsOfProduct=FindrecipeById(recipeId);
	 List<Long> likes = detailsOfProduct.getLikes();
	    Long userId = user.getId();

	    // Print user ID and elements in the list for debugging
	    System.out.println("User ID: " + userId);
	    System.out.println("Likes List: " + likes);
	    System.out.print("Likes List: [");
	    for (int i = 0;i<=likes.size();i++) {
	        System.out.print(i + ", ");
	    }
	    System.out.println("]");

	    if (likes.contains(userId)) {
		System.out.println("User IDs inside likes:");
        
		  detailsOfProduct.getLikes().remove(user.getId());
	        recipeRepository.save(detailsOfProduct); // Save the updated recipe
			}
	else
	{
		System.out.println(detailsOfProduct.getLikes());
		 // Add the user ID to the likes list
        detailsOfProduct.getLikes().add(userId);
        recipeRepository.save(detailsOfProduct); // Save the updated recipe
    }
	return null;
}

public String SavePostById(UserModel user, long postId) {
    List<Long>AlreadySavedPost=user.getSavedPost();
    if (AlreadySavedPost.contains(postId)) {
        return "Post is already saved...";
    } else {
        // Post is not already saved, add postId to the list
    	AlreadySavedPost.add(postId);
        user.setSavedPost(AlreadySavedPost);
        
        // Save the updated UserModel in the repository
        userRepository.save(user);
        
        return "Post saved successfully.";
    }
}

public List<recipe> SavedPostByUserId(String emailId) {
	UserModel userData=userRepository.findByEmail(emailId);
	List<Long>savedPostId=userData.getSavedPost();
	List<recipe> savedPosts = new ArrayList<>();
	 for (Long postId : savedPostId) {
	        Optional<recipe> recipe = recipeRepository.findById(postId);
	        if (recipe.isPresent()) {
	            savedPosts.add(recipe.get());
	        }
	    }
	return savedPosts;
}


}
