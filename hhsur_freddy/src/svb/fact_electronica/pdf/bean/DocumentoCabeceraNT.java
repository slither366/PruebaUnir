package svb.fact_electronica.pdf.bean;

public class DocumentoCabeceraNT {
    private String tipo_documento;
    private String titulo_documento;
    private String numero_documento;
    private String razon_social_cliente;
    private String direccion_cliente;
    private String tipo_documento_cliente;
    private String ruc_cliente;
    private String moneda_pago_cliente;
    private String fecha_emision_documento;
    private String condicion_pago_documento;
    private String doc_referencia;
    private String fecha_doc_referencia;
    private String sustento_emision;
    private String subtotal;
    private String monto_igv;
    private String monto_total;
    private String codeQR;
    private String monto_total_letras;

    public DocumentoCabeceraNT(String tipo_documento, String titulo_documento, String numero_documento, String razon_social_cliente, String direccion_cliente, String tipo_documento_cliente, String ruc_cliente, String moneda_pago_cliente, String fecha_emision_documento, String condicion_pago_documento, String doc_referencia, String fecha_doc_referencia, String sustento_emision, String subtotal, String monto_igv, String monto_total, String codeQR, String monto_total_letras) {
        this.tipo_documento = tipo_documento;
        this.titulo_documento = titulo_documento;
        this.numero_documento = numero_documento;
        this.razon_social_cliente = razon_social_cliente;
        this.direccion_cliente = direccion_cliente;
        this.tipo_documento_cliente = tipo_documento_cliente;
        this.ruc_cliente = ruc_cliente;
        this.moneda_pago_cliente = moneda_pago_cliente;
        this.fecha_emision_documento = fecha_emision_documento;
        this.condicion_pago_documento = condicion_pago_documento;
        this.doc_referencia = doc_referencia;
        this.fecha_doc_referencia = fecha_doc_referencia;
        this.sustento_emision = sustento_emision;
        this.subtotal = subtotal;
        this.monto_igv = monto_igv;
        this.monto_total = monto_total;
        this.codeQR = codeQR;
        this.monto_total_letras = monto_total_letras;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getTitulo_documento() {
        return titulo_documento;
    }

    public void setTitulo_documento(String titulo_documento) {
        this.titulo_documento = titulo_documento;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public String getRazon_social_cliente() {
        return razon_social_cliente;
    }

    public void setRazon_social_cliente(String razon_social_cliente) {
        this.razon_social_cliente = razon_social_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccion_cliente = direccion_cliente;
    }

    public String getTipo_documento_cliente() {
        return tipo_documento_cliente;
    }

    public void setTipo_documento_cliente(String tipo_documento_cliente) {
        this.tipo_documento_cliente = tipo_documento_cliente;
    }

    public String getRuc_cliente() {
        return ruc_cliente;
    }

    public void setRuc_cliente(String ruc_cliente) {
        this.ruc_cliente = ruc_cliente;
    }

    public String getMoneda_pago_cliente() {
        return moneda_pago_cliente;
    }

    public void setMoneda_pago_cliente(String moneda_pago_cliente) {
        this.moneda_pago_cliente = moneda_pago_cliente;
    }

    public String getFecha_emision_documento() {
        return fecha_emision_documento;
    }

    public void setFecha_emision_documento(String fecha_emision_documento) {
        this.fecha_emision_documento = fecha_emision_documento;
    }

    public String getCondicion_pago_documento() {
        return condicion_pago_documento;
    }

    public void setCondicion_pago_documento(String condicion_pago_documento) {
        this.condicion_pago_documento = condicion_pago_documento;
    }

    public String getDoc_referencia() {
        return doc_referencia;
    }

    public void setDoc_referencia(String doc_referencia) {
        this.doc_referencia = doc_referencia;
    }

    public String getFecha_doc_referencia() {
        return fecha_doc_referencia;
    }

    public void setFecha_doc_referencia(String fecha_doc_referencia) {
        this.fecha_doc_referencia = fecha_doc_referencia;
    }

    public String getSustento_emision() {
        return sustento_emision;
    }

    public void setSustento_emision(String sustento_emision) {
        this.sustento_emision = sustento_emision;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getMonto_igv() {
        return monto_igv;
    }

    public void setMonto_igv(String monto_igv) {
        this.monto_igv = monto_igv;
    }

    public String getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(String monto_total) {
        this.monto_total = monto_total;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public String getMonto_total_letras() {
        return monto_total_letras;
    }

    public void setMonto_total_letras(String monto_total_letras) {
        this.monto_total_letras = monto_total_letras;
    }
}
