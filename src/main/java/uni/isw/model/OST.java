package uni.isw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ordenserviciotecnico")
public class OST {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ost")
    private Long id_ost;

    @ManyToOne // Relación muchos a uno con Cliente
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idvehiculo")
    private Vehiculo vehiculo;

    @Column(name = "fechahora")
    private LocalDateTime fechahora;

    @Column(name = "fallareportada")
    private String fallareportada;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "informetecnico")
    private String informeTecnico;

    @Column(name = "estado")
    private String estado;

    @JsonIgnore
    @OneToMany(mappedBy = "ost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trabajo> trabajos;
}

