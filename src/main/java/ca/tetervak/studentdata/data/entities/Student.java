package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "student")
public class Student {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 30)
    private String lastName = "";

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "international")
    private Boolean international = false;

    @Column(name = "program_year")
    @Min(1)
    @Max(3)
    private Integer programYear = 0;

    @Column(name = "program_coop")
    private Boolean programCoop = false;
}
