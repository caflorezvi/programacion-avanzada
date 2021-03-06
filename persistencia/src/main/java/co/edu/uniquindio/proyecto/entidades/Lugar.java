package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Lugar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, length = 200)
    @NotBlank
    @Size(max = 200)
    private String nombre;

    @Lob
    @Column(nullable = false)
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;

    @Column(nullable = false)
    private Float latitud;

    @Column(nullable = false)
    private Float longitud;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAprobacion;

    private Boolean estado;

    @ElementCollection
    @JoinColumn(nullable = false)
    @Column(name="url_imagen")
    private List<String> imagenes;

    @ElementCollection
    @CollectionTable(
            name = "lugar_telefonos",
            joinColumns=@JoinColumn(name = "id_lugar", referencedColumnName = "id")
    )
    @Column(name="numero_telefono")
    private List<String> telefonos;

    @ManyToOne
    private Moderador moderador;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TipoLugar tipo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuarioCreador;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ciudad ciudad;

    @OneToMany(mappedBy = "lugar")
    @ToString.Exclude
    @JsonIgnore
    private List<Horario> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "lugar")
    @ToString.Exclude
    @JsonIgnore
    private List<Comentario> comentarios;

    @ManyToMany(mappedBy = "lugaresFavoritos")
    @ToString.Exclude
    @JsonIgnore
    private List<Usuario> usuariosFavoritos;

    @Builder
    public Lugar(String nombre, String descripcion, Float latitud, Float longitud, List<String> imagenes, TipoLugar tipo, Usuario usuarioCreador, Ciudad ciudad, List<Horario> horarios, boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagenes = imagenes;
        this.tipo = tipo;
        this.usuarioCreador = usuarioCreador;
        this.ciudad = ciudad;
        this.horarios = horarios;
        this.estado = estado;
    }

    public String getImagenPrincipal(){
        if(imagenes!=null && !imagenes.isEmpty()){
            return imagenes.get(0);
        }
        return "default.png";
    }

    public String getAbierto(){
        LocalDateTime horaActual = LocalDateTime.now();
        int diaActual = LocalDateTime.now().getDayOfWeek().getValue();
        LocalTime horaI, horaF;
        LocalTime ltActual = LocalTime.of(horaActual.getHour(), horaActual.getMinute());

        for(Horario h : horarios){
            if( diaActual == getDiaSemana( h.getDiaSemana()) ){
                horaI = new Date(h.getHoraInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                horaF = new Date(h.getHoraFin().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                if( ltActual.compareTo(horaI) > 0 && ltActual.compareTo(horaF) < 0 ){
                    return "Abierto";
                }
            }
        }
        return "Cerrado";
    }

    public int getDiaSemana(String dia){
        switch (dia){
            case "lunes":
                return 1;
            case "martes":
                return 2;
            case "miercoles":
                return 3;
            case "jueves":
                return 4;
            case "viernes":
                return 5;
            case "sabado":
                return 6;
            case "domingo":
                return 7;
        }
        return 0;
    }

}
