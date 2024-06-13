package uni.isw.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="problematecnico")
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproblema")
    private Long idproblema;

    @Column(nullable = false,unique = true)
    private String descripcion;
    @Column(nullable = true,unique = false)
    private String solucion;
}
