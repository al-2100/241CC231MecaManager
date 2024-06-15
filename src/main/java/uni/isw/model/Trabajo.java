package uni.isw.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trabajo")
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajo")
    private Long id_trabajo;

    @ManyToOne
    @JoinColumn(name = "id_ost", nullable = false)
    private OST ost;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = false)
    private Tecnico tecnico;

    @Column(name = "descripcion")
    private String descripcion;

    // Getters and setters...
}
