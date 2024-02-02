package KAgriFarm.KAgriFarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KAgriFarm.KAgriFarm.model.recipe;

public interface recipeRepository extends JpaRepository<recipe,Long> {

}
