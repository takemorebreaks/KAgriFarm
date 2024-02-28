package KAgriFarm.KAgriFarm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import KAgriFarm.KAgriFarm.model.UserModel;

public interface userRepository extends JpaRepository<UserModel,Long> {
public UserModel findByEmail(String Email);
}
