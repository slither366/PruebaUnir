package svb.fact_electronica.pdf.bean;

public class Empresa {
    private String ruta_imagen;
    private String razon_social;
    private String direccion_emisor;
    private String dep_prov_dist_emisor;
    private String telefono_emisor;
    private String fax_emisor;
    private String web_emisor;
    private String correo_emisor;
    private String ruc_emisor;
    private String ruta_pdf_A4;
    private String url_portal_fe;
    private String ruta_plantillas_jasper;

    public Empresa(String ruta_imagen, String razon_social, String direccion_emisor, String dep_prov_dist_emisor, String telefono_emisor, String fax_emisor, String web_emisor, String correo_emisor, String ruc_emisor, String ruta_pdf_A4, String url_portal_fe, String ruta_plantillas_jasper) {
        this.ruta_imagen = ruta_imagen;
        this.razon_social = razon_social;
        this.direccion_emisor = direccion_emisor;
        this.dep_prov_dist_emisor = dep_prov_dist_emisor;
        this.telefono_emisor = telefono_emisor;
        this.fax_emisor = fax_emisor;
        this.web_emisor = web_emisor;
        this.correo_emisor = correo_emisor;
        this.ruc_emisor = ruc_emisor;
        this.ruta_pdf_A4 = ruta_pdf_A4;
        this.url_portal_fe = url_portal_fe;
        this.ruta_plantillas_jasper = ruta_plantillas_jasper;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion_emisor() {
        return direccion_emisor;
    }

    public void setDireccion_emisor(String direccion_emisor) {
        this.direccion_emisor = direccion_emisor;
    }

    public String getDep_prov_dist_emisor() {
        return dep_prov_dist_emisor;
    }

    public void setDep_prov_dist_emisor(String dep_prov_dist_emisor) {
        this.dep_prov_dist_emisor = dep_prov_dist_emisor;
    }

    public String getTelefono_emisor() {
        return telefono_emisor;
    }

    public void setTelefono_emisor(String telefono_emisor) {
        this.telefono_emisor = telefono_emisor;
    }

    public String getFax_emisor() {
        return fax_emisor;
    }

    public void setFax_emisor(String fax_emisor) {
        this.fax_emisor = fax_emisor;
    }

    public String getWeb_emisor() {
        return web_emisor;
    }

    public void setWeb_emisor(String web_emisor) {
        this.web_emisor = web_emisor;
    }

    public String getCorreo_emisor() {
        return correo_emisor;
    }

    public void setCorreo_emisor(String correo_emisor) {
        this.correo_emisor = correo_emisor;
    }

    public String getRuc_emisor() {
        return ruc_emisor;
    }

    public void setRuc_emisor(String ruc_emisor) {
        this.ruc_emisor = ruc_emisor;
    }

    public String getRuta_pdf_A4() {
        return ruta_pdf_A4;
    }

    public void setRuta_pdf_A4(String ruta_pdf_A4) {
        this.ruta_pdf_A4 = ruta_pdf_A4;
    }

    public String getUrl_portal_fe() {
        return url_portal_fe;
    }

    public void setUrl_portal_fe(String url_portal_fe) {
        this.url_portal_fe = url_portal_fe;
    }

    public String getRuta_plantillas_jasper() {
        return ruta_plantillas_jasper;
    }

    public void setRuta_plantillas_jasper(String ruta_plantillas_jasper) {
        this.ruta_plantillas_jasper = ruta_plantillas_jasper;
    }
}
