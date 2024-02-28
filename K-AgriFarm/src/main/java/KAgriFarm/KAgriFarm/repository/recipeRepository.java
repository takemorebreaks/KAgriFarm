package KAgriFarm.KAgriFarm.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import KAgriFarm.KAgriFarm.model.recipe;
@Repository
public interface recipeRepository extends JpaRepository<recipe,Long> {
	 List<recipe> findById(long recipeId);
	 List<recipe> findByUserId(long userId);
}
