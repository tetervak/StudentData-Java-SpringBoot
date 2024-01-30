package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "app_role")
@NoArgsConstructor
@Getter
@Setter
public class AppRole {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName = "";

    @ManyToMany(mappedBy="roles")
    private List<AppUser> users;
}
