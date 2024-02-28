package KAgriFarm.KAgriFarm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class UserModel {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
private String password;
private String email;
private String fullname;
private List<Long>SavedPost;
// Set default value to false
private boolean BlockUser = false;
@ManyToOne(fetch = FetchType.EAGER) 
@JoinColumn(name = "role_id")
private Role role;
}
