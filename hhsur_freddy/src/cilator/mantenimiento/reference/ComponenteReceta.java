package cilator.mantenimiento.reference;

public class ComponenteReceta {

    private Receta receta;
    private String codComponente;
    private String descComponente;
    private String descPresenacion;
    private String estado;
    private int cantidad;
    private int valorFraccion;
    private String indVtaStock;
    
    public ComponenteReceta(Receta receta, String codComponente){
        this.setReceta(receta);
        this.setCodComponente(codComponente);        
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getCodComponente() {
        return codComponente;
    }

    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }

    public String getDescComponente() {
        return descComponente;
    }

    public void setDescComponente(String descComponente) {
        this.descComponente = descComponente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorFraccion() {
        return valorFraccion;
    }

    public void setValorFraccion(int valorFraccion) {
        this.valorFraccion = valorFraccion;
    }
    
    public String getDescPresenacion() {
        return descPresenacion;
    }

    public void setDescPresenacion(String descPresenacion) {
        this.descPresenacion = descPresenacion;
    }
    
    public String getIndVtaStock() {
        return indVtaStock;
    }

    public void setIndVtaStock(String indVtaStock) {
        this.indVtaStock = indVtaStock;
    }

    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof ComponenteReceta)){
            return false;
        } else if(((ComponenteReceta)obj).getCodComponente().compareTo(this.codComponente)==0 &&
                    ((ComponenteReceta)obj).getReceta().equals(this.receta)){
            return true;
        }
        return false;
    }

    public String toString() {
        return "[Receta]:"+this.getReceta().getCodProducto()+
               ", [codComponente]:"+this.codComponente+
               ", [descComponente]:"+this.descComponente+
               ", [descPresenacion]:" + this.getDescPresenacion() +
            ", [estado]:"+this.estado+
               ", [cantidad]:"+this.cantidad+
               ", [valorFraccion]:"+this.valorFraccion+
               ", [indVtaStock]:" + this.getIndVtaStock();
    }
}
