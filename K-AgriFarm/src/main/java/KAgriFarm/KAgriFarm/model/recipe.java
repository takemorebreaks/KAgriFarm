package KAgriFarm.KAgriFarm.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Component
public class recipe {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private String title;
private String image;
@ManyToOne
private UserModel user;
private boolean vegitarian;
private String description;
private LocalDateTime localTime;
private List<Long>Likes=new ArrayList<>();
}
