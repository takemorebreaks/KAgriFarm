package KAgriFarm.KAgriFarm.repository;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import KAgriFarm.KAgriFarm.model.UserModel;
@Repository
@Primary
public interface userRepository extends JpaRepository<UserModel,Long> {
public UserModel findByEmail(String Email);
}
