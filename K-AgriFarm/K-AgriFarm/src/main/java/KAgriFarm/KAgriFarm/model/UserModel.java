package KAgriFarm.KAgriFarm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class UserModel {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private String password;
private String email;
private String fullname;
}
