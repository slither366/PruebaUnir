package cilator.mantenimiento.reference;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AdministracionReceta {
    private static final Log log = LogFactory.getLog(AdministracionReceta.class);
    private Receta receta;
    
    public AdministracionReceta(Receta receta){
        this.setReceta(receta);
        this.receta.setListaComponentes(new ArrayList<ComponenteReceta>());
    }
    
    public void agregarComponente(ComponenteReceta cr){        
        this.getReceta().getListaComponentes().add(cr);
        log.info("COMPONENTE AGREGADO A LA LISTA");
    }
    
    public void removerComponente(ComponenteReceta cr){        
        this.getReceta().getListaComponentes().remove(cr);
        log.info("COMPONENTE REMOVIDO DE LA LISTA");
    }
    
    public void editarComponente(ComponenteReceta cr){        
        List<ComponenteReceta> lista = this.getReceta().getListaComponentes();
        for(ComponenteReceta componente:lista){
            if(componente.equals(cr)){
                componente.setCodComponente(cr.getCodComponente());
                componente.setCantidad(cr.getCantidad());
                componente.setDescComponente(cr.getDescComponente());
                componente.setEstado(cr.getEstado());
                componente.setReceta(cr.getReceta());
                componente.setValorFraccion(cr.getValorFraccion());
                componente.setIndVtaStock(cr.getIndVtaStock());
                log.info("COMPONENTE EDITADO");
                break;
            }
        }        
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
}
