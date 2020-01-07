package cilator.mantenimiento.reference;

import java.util.List;

public class Receta {
    
    private String codProducto;
    private String descProducto;
    List<ComponenteReceta> listaComponentes;
    
    public Receta(String codProducto, String descProducto) {
        this.setCodProducto(codProducto);
        this.setDescProducto(descProducto);        
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public List<ComponenteReceta> getListaComponentes() {
        return listaComponentes;
    }

    public void setListaComponentes(List<ComponenteReceta> listaComponentes) {
        this.listaComponentes = listaComponentes;
    }
    
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof Receta)){
            return false;
        }else if(((Receta)obj).codProducto.compareTo(this.codProducto)==0){
            return true;
        }
        return false;
    }
    
    public String toString(){
        return "[codProducto]:"+this.codProducto+
               ", [descProducto]:"+this.descProducto+
               "\n[listaComponentes]:"+this.listaComponentes;
    }
}
