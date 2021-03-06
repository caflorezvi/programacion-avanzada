package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoLugar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipo")
    @ToString.Exclude
    @JsonIgnore
    private List<Lugar> lugares;

    public TipoLugar(String nombre) {
        this.nombre = nombre;
    }
}
