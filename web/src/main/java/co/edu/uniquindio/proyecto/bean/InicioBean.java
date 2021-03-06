package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.dto.MarkerDTO;
import co.edu.uniquindio.proyecto.entidades.Lugar;
import co.edu.uniquindio.proyecto.entidades.TipoLugar;
import co.edu.uniquindio.proyecto.servicios.LugarServicio;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ViewScoped
public class InicioBean implements Serializable {

    @Autowired
    private LugarServicio lugarServicio;

    @Getter @Setter
    private List<Lugar> lugares;

    @Getter @Setter
    private List<TipoLugar> tipoLugares;

    @PostConstruct
    public void inicializar(){
        this.lugares = new ArrayList<>();
        this.tipoLugares = lugarServicio.listarTiposLugares();
    }

    public void calcularDistancias(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Float lng = Float.parseFloat( params.get("lng") );
        Float lat = Float.parseFloat( params.get("lat") );

        if(lng!=-1 && lat!=-1){
            this.lugares = lugarServicio.obtenerLugaresCercanos(lng, lat, 10.0);
        }else{
            this.lugares = lugarServicio.listarLugares();
        }

        PrimeFaces.current().executeScript("crearMapa("+new Gson().toJson(this.lugares.stream().map(l -> new MarkerDTO(l.getId(), l.getNombre(), l.getDescripcion(), l.getLatitud(), l.getLongitud())).collect(Collectors.toList()))+");");
        PrimeFaces.current().ajax().update("form:lista-lugares");
    }

    public String irADetalle(Integer id){
        return "/detalleLugar?faces-redirect=true&amp;lugar="+id;
    }
}
