package uni.isw.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tecnico")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Integer id_tecnico;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;




}
