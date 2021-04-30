package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepo extends JpaRepository<Comentario, Integer> {

    @Query("select c from Comentario c where c.calificacion > ?1")
    List<Comentario> obtenerListaPorCalificacion(int calificacion);

    @Query("select distinct c.usuario from Comentario c where c.lugar.id = :idLugar")
    List<Usuario> usuariosComentarios(Integer idLugar);

}