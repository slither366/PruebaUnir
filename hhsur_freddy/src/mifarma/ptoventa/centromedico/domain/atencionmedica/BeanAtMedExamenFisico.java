package mifarma.ptoventa.centromedico.domain.atencionmedica;

public class BeanAtMedExamenFisico {
    
    private String codGrupoCia;
    private String codCia;
    private String codLocal;
    private String nroAtencionMedica;
    private String estadoGeneral;
    private String estadoConciencia;
    //Dflores: 26/08/2019
    private double imc;
    private double medCintura;    
    //--
    private String examenFisicoDirigido;
 
    public BeanAtMedExamenFisico() {
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

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoConciencia(String estadoConciencia) {
        this.estadoConciencia = estadoConciencia;
    }

    public String getEstadoConciencia() {
        return estadoConciencia;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getImc() {
        return imc;
    }

    public void setMedCintura(double medCintura) {
        this.medCintura = medCintura;
    }

    public double getMedCintura() {
        return medCintura;
    }

    public void setExamenFisicoDirigido(String examenFisicoDirigido) {
        this.examenFisicoDirigido = examenFisicoDirigido;
    }

    public String getExamenFisicoDirigido() {
        return examenFisicoDirigido;
    }
}
