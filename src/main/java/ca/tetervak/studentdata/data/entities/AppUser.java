package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName = "";

    @Column(name = "password", nullable = false)
    private String password = "";

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="app_user_role",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List<AppRole> roles = new ArrayList<>(2);
}
