package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class Usuario extends Persona implements Serializable {

    @Getter
    @Setter
    @ManyToOne
    @ToString.Include
    private Ciudad ciudad;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "favorito",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_lugar"))
    @JsonIgnore
    private List<Lugar> lugaresFavoritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuarioCreador")
    @JsonIgnore
    private List<Lugar> lugaresCreados;

    @Builder
    public Usuario(String nombre, String nickname, String email, String password, Ciudad ciudad) {
        super(nombre, nickname, email, password);
        this.ciudad = ciudad;
    }

    public Usuario(Integer id, String nombre, String nickname, String email, String password) {
        super(id, nombre, nickname, email, password);
    }
}
