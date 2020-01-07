package svb.fact_electronica.reference;


import common.FarmaConstants;
import common.FarmaUtility;
import common.FarmaVariables;

import java.util.ArrayList;

import javax.swing.JDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import svb.imp_fe.electronico.epos.UtilityEposCnx;

import venta.caja.reference.UtilityBarCode;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;


public class UtilityCPE {

    private static boolean activoFuncionalidad = false;
    private static boolean registradoPCEnEpos = false;
    private static boolean comunicaPCConEPOS = false;
    private static boolean estaContigenciaEPOS = false;
    private static boolean volverEjecutar = false;

    private static final Logger log = LoggerFactory.getLogger(UtilityCPE.class);
    //private BeanConexionEPOS beanConexion;
    //private FacadeCPE facade;
    
    public UtilityCPE(){
        
    }
    
    /**
     * Activa la funcionalidad de Facturacion Electronica
     * @creado KMONCADA
     * @param activoFuncionalidad
     * @since 15.11.2016
     */
    private void setActivoFuncionalidad(boolean activoFuncionalidad) {
        this.activoFuncionalidad = activoFuncionalidad;
    }
    
    /**
     * Verifica si se encuentra activa la funcionalidad de Facturacion Electronica
     * @creado KMONCADA
     * @return true/false
     * @since 15.11.2016 
     */
    public static boolean isActivoFuncionalidad() {
        return activoFuncionalidad;
    }
    
    /**
     * Configura si la PC se encuentra registrada en el EPOS
     * @creado KMONCADA
     * @param registradoPCEnEpos
     * @since
     */
    private void setRegistradoPCEnEpos(boolean registradoPCEnEpos){
        this.registradoPCEnEpos = registradoPCEnEpos;
    }
    
    /**
     * Indicador si la PC se encuentra registrada en EPOS
     * @creado KMONCADA
     * @return
     * @since
     */
    public boolean isRegistradoPCEnEpos() {
        return this.registradoPCEnEpos;
    }
    
    /**
     * Configura si la PC interactura con el EPOS, para caso de PC que solo consultan data 
     * @creado KMONCADA
     * @param conectaPCConEpos
     * @since
     */
    private void setComunicaPCConEPOS(boolean conectaPCConEpos){
        this.comunicaPCConEPOS = conectaPCConEpos;
    }
    
    /**
     * Indicador si la PC trabajara con el EPOS enviando comprobantes de pago
     * @creado KMONCADA
     * @return
     * @since
     */
    public boolean isComunicaPCConEPOS(){
        return this.comunicaPCConEPOS;
    }
  

    
    /**
     * Obtiene el identificador de la PC/Caja para el EPOS.
     * @creado KMONCADA
     * @return ultimo segmento de la IP de la PC.
     * @since
     */
    private static String getIdCajaEpos(){
        String[] cadena = FarmaVariables.vIpPc.split("\\.");
        return cadena[3].toString();
    }

    
    /**
     * Metodo que verifica datos validos
     * @creado KMONCADA
     * @param dato
     * @param nombreCampo
     * @return
     * @throws Exception
     * @since
     */
    private static String isDatoValido(String dato, String nombreCampo)throws Exception{
        if(dato == null || (dato != null && dato.trim().length()==0)){
            throw new Exception("PARAMETROS "+nombreCampo+" INCOMPLETO.");
        }
        return dato;
    }
    
    /**
     * Verifica validez de las parte de la trama del comprobante de pago
     * @creado KMONCADA
     * @param trama
     * @param parte
     * @throws Exception
     * @since
     */
    public static void isValidaParteTrama(String trama, String parte)throws Exception{
        if(trama == null || (trama != null && trama.trim().length()==0)){
            throw new Exception("COMPROBANTE DE PAGO ELECTRONICO:\n " + parte+" --> SIN DATOS");
        }
    }
    
    /**
     * Verifica validez de las parte de la trama del comprobante de pago para V2
     * @creado KMONCADA
     * @param trama
     * @param parte
     * @throws Exception
     * @since
     */
    public static void isValidaParteTrama_V2(String trama, String parte)throws Exception{
        if(trama == null || (trama != null && trama.length()==0)){
            throw new Exception("COMPROBANTE DE PAGO ELECTRONICO:\n " + parte+" --> SIN DATOS");
        }
    }
 
        
    /**
     * 
     * @creado KMONCADA
     * @param pTipoCP
     * @return
     * @throws Exception
     * @since
     */
    
    public boolean procesarComprobanteAlEPOS(JDialog pJDialog, String pNumPedVta, String pSecCompPago, String pNumComprobante){
        boolean comprobanteProcesado = true;
       
        
        if(!comprobanteProcesado){
            ArrayList lstComprobantes = UtilityCaja.obtieneComprobantesPago(pNumPedVta, pSecCompPago);
            if(lstComprobantes!=null){
                try{
                    for(int i=0;i<lstComprobantes.size();i++){
                        String secCompPago = ((String)((ArrayList)lstComprobantes.get(i)).get(1)).trim();
                        String tipoCP = ((String)((ArrayList)lstComprobantes.get(i)).get(2)).trim();
                        String tipoClienteConv = ((String)((ArrayList)lstComprobantes.get(i)).get(3)).trim();
                        String nroComprobante = ((String)((ArrayList)lstComprobantes.get(i)).get(4)).trim();
                        String indElectronico = ((String)((ArrayList)lstComprobantes.get(i)).get(5)).trim();
                        
                        if("04".equalsIgnoreCase(tipoCP))
                            tipoClienteConv = "";

                        
                    }
                }catch(Exception ex){
                    log.error("", ex);
                    comprobanteProcesado = false;
                    FarmaUtility.showMessage(pJDialog, ex.getMessage(), null);
                    
                }
            }else{
                FarmaUtility.showMessage(pJDialog, "Comunicación EPOS:\n"+
                                                   "Error inesperado, al consultar los datos del comprobante de pago "+pNumComprobante+"\n"+
                                                   "Comprobante de pago se envia a pendiente de impresion.", null);
            }
        }
        return comprobanteProcesado;
    }
    



    public static boolean isEstaContigenciaEPOS() {
        return estaContigenciaEPOS;
    }

    private void setEstaContigenciaEPOS(boolean contigenciaEPOS) {
        UtilityCPE.estaContigenciaEPOS = contigenciaEPOS;
    }

    public static boolean isVolverEjecutar() {
        return volverEjecutar;
    }

    public static void setVolverEjecutar(boolean volverEjecutar) {
        UtilityCPE.volverEjecutar = volverEjecutar;
    }
}