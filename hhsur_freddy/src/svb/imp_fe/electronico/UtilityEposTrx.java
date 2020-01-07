package svb.imp_fe.electronico;


import common.FarmaConstants;
import common.FarmaUtility;
import common.FarmaVariables;

import java.sql.SQLException;

import javax.swing.JDialog;

import svb.imp_fe.electronico.epos.UtilityEposCnx;
import svb.imp_fe.electronico.epos.reference.DBEpos;
import svb.imp_fe.electronico.epos.reference.EposConstante;
import svb.imp_fe.electronico.epos.reference.EposVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;

import venta.cnx.FarmaVentaCnxUtility;

import venta.convenioBTLMF.reference.FacadeConvenioBTLMF;


public class UtilityEposTrx {

    private static final Logger log = LoggerFactory.getLogger(UtilityEposTrx.class);

    public static FarmaVentaCnxUtility cnxUtil = new FarmaVentaCnxUtility();
    public static UtilityEposCnx sc = new UtilityEposCnx();


    public static boolean vPermiteCobro(JDialog pJDialog) {
        EposVariables.limpiaVariables();
        boolean result = false;
        return result;
    }

    public static boolean vPermiteAnular() {
        EposVariables.limpiaVariables();
        boolean result = false;
        return result;
    }

    /**
     * Verifica que la funcionalidad esta activa en el LOCAL
     * @return
     */
    public static boolean isActFuncElectronica() {
        boolean vResultado = false;
        try {
            vResultado = FarmaConstants.INDICADOR_S.equals(DBEpos.getIndElectronicoLocal().toString()) ? true : false;
        } catch (Exception e) {
            // TODO: Add catch code
            log.error("",e);
        }

        return vResultado;
    }

    public static boolean isValidoParamCnxEpos() throws Exception {
        boolean vResultado = true;
        return vResultado;
    }

    //El Id epos es el ultimo segmento del IP

    public static String getIdEpos() {
        String[] cadena = FarmaUtility.getHostAddress().split("\\.");
        return cadena[3].toString();
    }

    public static boolean isConexionEPOS() throws Exception {
        boolean resultado = false;
        //try {
        resultado = sc.sentEchoEpos(getIdEpos());

        /*} catch (Exception e) {
            resultado = false;
            FarmaUtility.showMessage(new JDialog(), "Alerta al Validar Conexion EPOS \n" +
                    "*********************************************" + "\n" +
                    e.getMessage() + "\n" +
                    "*********************************************" + "\n" +
                    "Comuníquese con Mesa de Ayuda.", null);
        }*/
        return resultado;
    }

    public static String getNumCompPagoE(String tipoCompPago, String numPedidoVta, String SecCompPago,
                                         String tipClientConvenio, boolean isValidaRAC) throws Exception {
        String[] listaRespuesta = null;
        try{
            listaRespuesta = getNumCompPagoE(tipoCompPago, numPedidoVta, SecCompPago, tipClientConvenio, isValidaRAC, true, true);
        }catch(ElectronicoException ex){
            log.error("",ex);
            throw new Exception("Error de comunicación con EPOS.\nPedido se encuentra Pendiente de Impresión, verifique!!! ");
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return listaRespuesta[2].toString();
    }

    /**
     * KMONCADA 20.10.2014 MODIFICADO PARA VALIDAR DUPLICIDAD DE DOCUMENTOS.
     * @param tipoCompPago
     * @param numPedidoVta
     * @param SecCompPago
     * @param tipClientConvenio
     * @param isValidaRAC VERIFICA SI VALIDA EL COMP GENERADO EN RAC
     * @return
     * @throws SQLException
     */
    public static String[] getNumCompPagoE(String tipoCompPago, String numPedidoVta, String SecCompPago,
                                           String tipClientConvenio, boolean isValidaRAC, boolean isValidaLocal,
                                           boolean isEnviaConfirmacion) throws Exception {

        String mensaje = "";
        String[] listaRespuesta = null;
        mensaje = sc.getNumCompEpos(tipoCompPago, numPedidoVta, SecCompPago, tipClientConvenio, getIdEpos());
        String trama = sc.getMensaje();


        log.info("*************mensaje***************" + mensaje);
        if (mensaje.startsWith(EposConstante.MSJ_EXITO)) {
            listaRespuesta = mensaje.split("\\|", 4);
            // Diferente a 99 el tipo comp pago, OBTENER DE LA CABECERA
            /*if (tipoCompPago.equals("99")) {
                tipoCompPago = VariablesVentas.vTip_Comp_Ped;
            }
            */
            String numero = listaRespuesta[2].toString();

            // VALIDACION DE DUPLICIDAD DE DOCUMENTOS
            boolean isNuevoComprobante = true;
            if (isValidaLocal) {
                // VALIDA PTOVENTA
                isNuevoComprobante =true;
                        /*isNuevoComprobante && !DBCaja.isExisteComprobanteElectronico(FarmaVariables.vCodGrupoCia,
                                                                                     FarmaVariables.vCodLocal,
                                                                                     tipoCompPago, numero);*/
                log.info("*************isNuevoComprobante***************" + isNuevoComprobante);
            }
            log.info("*************isValidaRAC***************" + isValidaRAC);
            if (isValidaRAC) {
                //VALIDO DOC EN EL RAC SI EXISTEN
                if (isNuevoComprobante) {
                    isNuevoComprobante = true;
                } else {
                    //return "L" + numero;
                    throw new Exception("Alerta Local:\n" +
                                        "El Número de Comprobante electrónico :" + numero + ", ya fue registrada anteriormente.");
                }
            }
            log.info("*************isNuevoComprobante***************" + isNuevoComprobante);

            if (isNuevoComprobante) {
                if (isEnviaConfirmacion) {
                    mensaje = enviaConfirmacion(numPedidoVta, SecCompPago, tipoCompPago, numero, listaRespuesta);
                }

                return listaRespuesta;

            } else {
                throw new Exception("Alerta Matriz:\n" +
                                    "El Número de Comprobante electrónico :" + numero + ", ya fue registrada anteriormente.");
            }
        } else {
            String emailEnvio = "";
            try {
                emailEnvio = "daubilluz@hotmail.com";//DBVentas.getDestinatarioFarmaEmail(ConstantsPtoVenta.FARMA_EMAIL_EPOS);
            } catch (Exception exc) {
                log.error("",exc);
            }
            
            if(emailEnvio.trim().length()>0){
                String msjAsunto = "";
                if(mensaje!=null && mensaje.length()>0){
                    msjAsunto = mensaje;
                    if(msjAsunto!=null){
                        msjAsunto = msjAsunto.trim();
                        msjAsunto = msjAsunto.toUpperCase();
                        if(msjAsunto.contains("ERROR ARCHIVO XML")){
                            msjAsunto = "ERROR ARCHIVO XML";
                        }else{
                            if(msjAsunto.contains("SE CUMPLIO TIMEOUT")){
                                msjAsunto = msjAsunto.substring(0, msjAsunto.indexOf("CLIENTE"));
                            }
                        }
                    }
                }
                
                FarmaVentaCnxUtility.enviaCorreoPorCnx(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, emailEnvio,
                                                   "ERROR DE SERVIDOR EPOS - "+msjAsunto+" ", "ERROR DE COMUNICACION CON SERVIDOR EPOS",
                                                   "Error en conexion con servidor EPOS<br>" + 
                                                   "NroPedido:"+numPedidoVta+"<br>"+
                                                   "Tipo Comprobante/Sec Comp.Pago: "+tipoCompPago+" - "+SecCompPago+"<br>"+
                                                   "Trama:<br>"+trama+"<br><br>"+
                                                   "Mensaje de respuesta :<br>" +
                                                   "Alerta al obtener Comprobante Electronico:<br>" + mensaje +
                                                   "<br>" + "DATOS DE CONEXION<br>" + "IP SERVIDOR: " + EposVariables.vIpSocketServidor + "<br>" + "PUERTO: " + EposVariables.vPuertoEPOS + "<br>" + "MODO" + EposVariables.vModo +
                                                   "<br>", "");
            }else{
                log.error("NO SE PUDO OBTENER CORREO DE ENVIO DE ERROR DE EPOS");
            }

            throw new Exception("Alerta Al Obtener Comprobante Electrónico!:\n" + mensaje );
        }
        //return EposConstante.MSJ_ERROR;
    }

    /**
     * Valiar si se emite comprobante electrónico
     * */
    public static boolean validacionEmiteElectronico() {

        log.info("FarmaVariables.vFlagComprobanteE>" + EposVariables.vFlagComprobanteE);
        // NO TOCAR
        // DUBILLUZ 05.11.2014
        return EposVariables.vFlagComprobanteE;
    }

    public static void actualizarFlagComprobanteElectronico(String numPedidoVta) throws Exception {
        DBEpos.updFlagCompElectronico(numPedidoVta);
    }

    public static String obtieneNumCompPagoE(String pNumPedVta, String cSecCompPago) throws Exception {
        return DBEpos.getNumCompPagoE(pNumPedVta, cSecCompPago);
    }

    public static String modificarTipCompPago(String vTipCompPaqo, String cSecCompPago) throws Exception {
        return DBEpos.updTipCompPago(vTipCompPaqo, cSecCompPago);
    }

    public static String obtieneTipoProcesoPago(String vNumCompPago) throws Exception {
        return DBEpos.getTipProcesPago(vNumCompPago);
    }

    //Por defecto siempre es TRUE
    //porque falta validar como se va hacer esta validacion
    //que solo permite generar notas de credito si el comprobante tiene el CDR
    //dubilluz 10.11.2014

    public static boolean getCDR(String string) {
        return true;
    }

    public static String enviaConfirmacion(String numPedidoVta, String SecCompPago, String tipoCompPago, String numero,
                                           String[] listaRespuesta) throws Exception {
        String mensaje;

        String numCompPagoE = numero.substring(0, 4) + "-" + numero.substring(4, numero.length());
        log.info("**********numCompPagoE************" + numCompPagoE);

        mensaje = sc.sentMsjConfirmacion(numPedidoVta, SecCompPago, tipoCompPago, numCompPagoE, getIdEpos(), svb.imp_fe.electronico.epos.reference.EposConstante.tipoMensaje.CONFIRMAR_DE);
        //KMONCADA 26.04.2016 ARCANGEL DEBE DE ENVIAR DOBLE CONFIRMACION
        if(DBEpos.isEnviaDobleConfirmacionEpos()){
            log.info("[EPOS]: ENVIO DE RECONFIRMACION AL EPOS");
            mensaje = sc.sentMsjConfirmacion(numPedidoVta, SecCompPago, tipoCompPago, numCompPagoE, getIdEpos(), svb.imp_fe.electronico.epos.reference.EposConstante.tipoMensaje.RECONFIRMAR_DE);
        }
        
        log.info("*************mensaje sentMsjConfirmacion***************" + mensaje);
        if (mensaje.startsWith(EposConstante.MSJ_EXITO)) {

            log.info("*************updateMsjConf_E********");
            // exito, retorna numero de comprobante electronico
            if (listaRespuesta.length > 2 && listaRespuesta != null) {
                if (listaRespuesta[2].toString() != null) {
                    log.info(listaRespuesta[2].toString());
                    log.info("*************SENT respuesta ***************");
                    //Actualiza datos del comprobante temporal y guarda en la tabla de mensaje los datos: codigo PDF417 y NumCompPagoE
                    DBEpos.updDocECommit(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, numPedidoVta,
                                         SecCompPago, listaRespuesta[2].toString(), listaRespuesta[3].toString(),
                                         tipoCompPago);//                           (----------PDF415----------)

                    //Actualiza el codigo PDF417 y NumCompPagoE del comprobante
                    DBEpos.updDocE(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, numPedidoVta, SecCompPago,
                                   listaRespuesta[2].toString(), listaRespuesta[3].toString(), 
                                   tipoCompPago);//              (----------PDF415----------)

                    //FarmaUtility.showMessage(new JDialog(), "update sin commit", null);
                    // exito, retorna numero de comprobante electronico
                    return listaRespuesta[2].toString();
                } else {
                    throw new Exception("Alerta Al Obtener Numero Comprobante:\n" +
                            "Es Nulo el Numero Electrónico." + "\n" +
                            "Por favor llamar a Mesa de Ayuda.");
                }
            } else {
                throw new Exception("Alerta Al Obtener Numero Comprobante:\n" +
                        "Es Nulo el Numero Electrónico y/o codigo PDF417." + "\n" +
                        "Por favor llamar a Mesa de Ayuda.");
            }

        } else {
            throw new Exception("Alerta Al Obtener Confirmacion de Comprobante Electrónico!:\n" +
                    mensaje + "\n" +
                    "Por favor llamar a Mesa de Ayuda.");
        }
    }
}
