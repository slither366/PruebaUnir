package svb.fact_electronica.pdf.bean;

public class DocumentoDetalleFB {
    private String item;
    private String cantidad;
    private String unidad;
    private String descripcion;
    private String p_unitario;
    private String subtotal;

    public DocumentoDetalleFB(String item, String cantidad, String unidad, String descripcion, String p_unitario, String subtotal) {
        this.item = item;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.descripcion = descripcion;
        this.p_unitario = p_unitario;
        this.subtotal = subtotal;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getP_unitario() {
        return p_unitario;
    }

    public void setP_unitario(String p_unitario) {
        this.p_unitario = p_unitario;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
