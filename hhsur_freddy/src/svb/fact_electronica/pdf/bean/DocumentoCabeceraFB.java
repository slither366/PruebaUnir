package svb.fact_electronica.pdf.bean;

public class DocumentoCabeceraFB {
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
    private String monto_exonerado;
    private String monto_inafecto;
    private String monto_gratuito;
    private String monto_gravado;
    private String monto_igv;
    private String monto_total;
    private String codeQR;
    private String monto_total_letras;

    public DocumentoCabeceraFB(String tipo_documento, String titulo_documento, String numero_documento, String razon_social_cliente, String direccion_cliente, String tipo_documento_cliente, String ruc_cliente, String moneda_pago_cliente, String fecha_emision_documento, String condicion_pago_documento, String monto_exonerado, String monto_inafecto, String monto_gratuito, String monto_gravado, String monto_igv, String monto_total, String codeQR, String monto_total_letras) {
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
        this.monto_exonerado = monto_exonerado;
        this.monto_inafecto = monto_inafecto;
        this.monto_gratuito = monto_gratuito;
        this.monto_gravado = monto_gravado;
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

    public String getMonto_exonerado() {
        return monto_exonerado;
    }

    public void setMonto_exonerado(String monto_exonerado) {
        this.monto_exonerado = monto_exonerado;
    }

    public String getMonto_inafecto() {
        return monto_inafecto;
    }

    public void setMonto_inafecto(String monto_inafecto) {
        this.monto_inafecto = monto_inafecto;
    }

    public String getMonto_gratuito() {
        return monto_gratuito;
    }

    public void setMonto_gratuito(String monto_gratuito) {
        this.monto_gratuito = monto_gratuito;
    }

    public String getMonto_gravado() {
        return monto_gravado;
    }

    public void setMonto_gravado(String monto_gravado) {
        this.monto_gravado = monto_gravado;
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
