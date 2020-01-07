package mifarma.ptoventa.centromedico.domain.atencionmedica;

public class BeanAtMedAnexo {
    
    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String nroAtencionMedica;
    
    private String codAnexo;
    private String nomAnexo;
    private String obsAnexo;
    private String rutaLocal;
    private String nomFile;
    private String extFile;
    private String rutaServidor;
    private String itemTblAnexos;
    
    public BeanAtMedAnexo() {
        super();
    }
    
    public void setCodGrupoCia(String codGrupoCia) {
        this.codGrupoCia = codGrupoCia;
    }

    public String getCodGrupoCia() {
        return codGrupoCia;
    }

    public void setCodCia(String codCia) {
        this.codCia = codCia;
    }

    public String getCodCia() {
        return codCia;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setNroAtencionMedica(String nroAtencionMedica) {
        this.nroAtencionMedica = nroAtencionMedica;
    }

    public String getNroAtencionMedica() {
        return nroAtencionMedica;
    }

    public void setCodAnexo(String codAnexo) {
        this.codAnexo = codAnexo;
    }

    public String getCodAnexo() {
        return codAnexo;
    }

    public void setNomAnexo(String nomAnexo) {
        this.nomAnexo = nomAnexo;
    }

    public String getNomAnexo() {
        return nomAnexo;
    }

    public void setObsAnexo(String obsAnexo) {
        this.obsAnexo = obsAnexo;
    }

    public String getObsAnexo() {
        return obsAnexo;
    }

    public void setRutaLocal(String rutaLocal) {
        this.rutaLocal = rutaLocal;
    }

    public String getRutaLocal() {
        return rutaLocal;
    }

    public void setNomFile(String nomFile) {
        this.nomFile = nomFile;
    }

    public String getNomFile() {
        return nomFile;
    }

    public void setExtFile(String extFile) {
        this.extFile = extFile;
    }

    public String getExtFile() {
        return extFile;
    }

    public void setRutaServidor(String rutaServidor) {
        this.rutaServidor = rutaServidor;
    }

    public String getRutaServidor() {
        return rutaServidor;
    }

    public void setItemTblAnexos(String itemTblAnexos) {
        this.itemTblAnexos = itemTblAnexos;
    }

    public String getItemTblAnexos() {
        return itemTblAnexos;
    }
}
