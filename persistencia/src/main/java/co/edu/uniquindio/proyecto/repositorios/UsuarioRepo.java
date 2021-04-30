package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Lugar;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

    @Query("select u from Usuario u")
    List<Usuario> obtenerUsuarios();

    @Query("select u from Usuario u")
    List<Usuario> obtenerUsuarios(Pageable pageable);

    @Query("select u from Usuario u")
    List<Usuario> obtenerUsuarios(Sort sort);

    @Query("select u from Usuario u where u.email = :email and u.nombre = :nombre")
    Usuario obtenerUsario( @Param("email") String email, @Param("nombre") String nombre);

    Usuario findByEmailAndNombre(String email, String nombre);

    Optional<Usuario> findByNickname(String nickname);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNombre(String nombre);

    Usuario findByEmailAndPassword(String email, String password);

    @Query("select f from Usuario u, IN(u.lugaresFavoritos) f where u.id = :id")
    List<Lugar> obtenerLugaresFavoritos(Integer id);

    @Query("select f from Usuario u join u.lugaresFavoritos f where u.id = :id")
    List<Lugar> obtenerLugaresFavoritos2(Integer id);

    @Query("select u.email, l from Usuario u left join u.lugaresCreados l")
    List<Object[]> obtenerLugaresPublicadosUsuarios();

    @Query("select u from Usuario u order by u.nombre")
    List<Usuario> obtenerListaUsuariosAlfabeticamente();

    @Query("select u from Usuario u where u.email like '%@gmail.%' ")
    List<Usuario> obtenerUsuariosDeGmail();

    @Query("select u from Usuario u where u.email like concat('%', :dominio, '%') ")
    List<Usuario> obtenerUsuariosDeDominio(String dominio);

}