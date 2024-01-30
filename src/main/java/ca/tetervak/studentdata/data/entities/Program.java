package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "program")

public class Program {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name = "program_name")
    @NotBlank
    @Size(max = 30)
    private String programName = "";

    public Program(String programName) {
        this.programName = programName;
    }
}
