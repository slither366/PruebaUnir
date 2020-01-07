package venta.fidelizacion.reference;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.fidelizacion.DlgBusquedaMedicoCamp;
import venta.fidelizacion.DlgFidelizacionBuscarTarjetas;
import venta.fidelizacion.DlgFidelizacionClientes;
import venta.hilos.Fidelizacion;
import venta.modulo_venta.DlgMedicoCampana;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuxiliarFidelizacion {
    
    private static String DLG_RESUMEN_PEDIDO  = "R";
    private static String DLG_LISTA_PRODUCTOS = "L";
    
    private static final Logger log = 
        LoggerFactory.getLogger(AuxiliarFidelizacion.class);

    public AuxiliarFidelizacion() {
    }

    public static void funcionF12(String pCodCampanaCupon,JTextField txtObj,Frame  myFrame,JLabel lblObj,
                                  JLabel lblCli,JDialog dlgObj,String pTipo,JLabel lblDniSinComision) {
        log.debug("Funcion F12");
        VariablesFidelizacion.tmpCodCampanaCupon = pCodCampanaCupon;
        if (VariablesModuloVentas.vEsPedidoConvenio||(VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)) {
                
            FarmaUtility.showMessage(dlgObj, 
                                     "No puede agregar una tarjeta a un " + 
                                     "pedido por convenio.", txtObj);
            return;
        }
        mostrarBuscarTarjetaPorDNI(myFrame,lblObj,lblCli,dlgObj,pTipo,txtObj);
        VariablesFidelizacion.tmpCodCampanaCupon = "";
        
        if(UtilityFidelizacion.getIndComisionnew())
        {
        
        /// LEVANTA EL HILO DE FIDELIZACION
        /////////////////////////////////////////////////////////////////
        // INICIO dubilluz 18.05.2012
        Fidelizacion subFidelacion = new Fidelizacion(VariablesFidelizacion.vDniCliente,dlgObj,txtObj,lblDniSinComision);
        subFidelacion.start();
        // FIN    dubilluz 18.05.2012
        /////////////////////////////////////////////////////////////////
        }
    }

    private static void mostrarBuscarTarjetaPorDNI(Frame myParentFrame,JLabel lblObj,JLabel lblCli,
                                                   JDialog dlgObj,String pTipo,JTextField txtObj
                                                   ) {

        DlgFidelizacionBuscarTarjetas dlgBuscar = 
            new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true);
        dlgBuscar.setVisible(true);
        log.debug("vv DIEGO:" + FarmaVariables.vAceptar);
        log.debug("dat_1:" + VariablesFidelizacion.vDataCliente);
        log.debug(" VariablesFidelizacion.vNomCliente_1:" + 
                           VariablesFidelizacion.vNomCliente);
        log.debug(" VariablesFidelizacion.vDniCliente_1:" + 
                           VariablesFidelizacion.vDniCliente);
        if (FarmaVariables.vAceptar) {
            log.debug("en aceptar");
            log.debug("dat:" + VariablesFidelizacion.vDataCliente);
            ArrayList array = 
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            log.debug("des 1");
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            log.debug("des 2");
            /*FarmaUtility.showMessage(this,
                                     "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                     null);*/
            log.debug(" VariablesFidelizacion.vNomCliente:" + 
                               VariablesFidelizacion.vNomCliente);
            log.debug(" VariablesFidelizacion.vDniCliente:" + 
                               VariablesFidelizacion.vDniCliente);
            FarmaUtility.showMessage(dlgObj, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " + 
                    VariablesFidelizacion.vApePatCliente + " " + 
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 
                0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(), 
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin 
            //jcallo 02.10.2008
            lblCli.setText(VariablesFidelizacion.vNomCliente + " " + 
                               VariablesFidelizacion.vApePatCliente + " " + 
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008
            //DAUBILLUZ -- Filtra los DNI anulados
            //25.05.2009
            VariablesFidelizacion.vDNI_Anulado = 
                    UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo = 
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            log.info("Variable de DNI_ANULADO: " + 
                     VariablesFidelizacion.vDNI_Anulado);
            log.info("Variable de vAhorroDNI_x_Periodo: " + 
                     VariablesFidelizacion.vAhorroDNI_x_Periodo);
            log.info("Variable de vMaximoAhorroDNIxPeriodo: " + 
                     VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
            setMensajeDNIFidelizado(lblObj,pTipo,txtObj,dlgObj);
            if (VariablesFidelizacion.vDNI_Anulado) {
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampa�asFidelizacion(VariablesFidelizacion.vNumTarjeta);

                //cargando las campa�as automaticas limitadas en cantidad de usos desde matriz
                log.debug("**************************************");
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion = 
                        FarmaConstants.INDICADOR_N;
                log.debug("**************************************");
                //if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   //VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se coment� pues no es necesario que valide ya que se consultar� al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + 
                          VariablesFidelizacion.vDniCliente);
                VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz = 
                       UtilityFidelizacion.CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);

                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" + VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz);
                // } // JCHAVEZ 27092009. se coment� pues no es necesario que valide ya que se consultar� al local
                //cargando las campa�as automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }
        }
    }


    public static void setMensajeDNIFidelizado(JLabel lblObj,String pTipo,JTextField txtObj,JDialog dlgObj) {
        if (VariablesFidelizacion.vDniCliente.trim().length() > 7 && 
            VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            if (!VariablesFidelizacion.vDNI_Anulado) {
                lblObj.setText("  DNI no afecto a Descuento.");
                lblObj.setVisible(true);
            } else {
                lblObj.setText("");
                lblObj.setVisible(false);
                ////////////////////////////////////////////
                if(pTipo.trim().equalsIgnoreCase(DLG_RESUMEN_PEDIDO))
                {
                    //Se evalua si ya esta en el limite de ahorro diario
                    //DUBILLUZ 28.05.2009
                    log.info("VariablesFidelizacion.vAhorroDNI_Pedido:" + 
                                       VariablesFidelizacion.vAhorroDNI_Pedido);
                    log.info("VariablesFidelizacion.vAhorroDNI_x_Periodo:" + 
                                       VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    log.info("VariablesFidelizacion.vMaximoAhorroDNIxPeriodo:" + 
                                       VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);
                    log.info("VariablesFidelizacion.vIndComprarSinDcto:" + 
                                       VariablesFidelizacion.vIndComprarSinDcto);
                    if (VariablesFidelizacion.vAhorroDNI_Pedido + 
                        VariablesFidelizacion.vAhorroDNI_x_Periodo >= 
                        VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) {
                        if (!VariablesFidelizacion.vIndComprarSinDcto) {
                            FarmaUtility.showMessage(dlgObj, 
                                                     "El tope de descuento por persona es de S/ " + 
                                                     FarmaUtility.formatNumber(VariablesFidelizacion.vMaximoAhorroDNIxPeriodo) + 
                                                     "\n" +
                                    "El cliente ya lleg� a su tope.", 
                                    txtObj);
                            VariablesFidelizacion.vIndComprarSinDcto = true;
                        }
    
                    } else {
                        VariablesFidelizacion.vIndComprarSinDcto = false;
                    }      
                }
                ////////////////////////////////////////////
            }

        }
    }
    
    public static void ingresoMedico(Frame myParentFrame,JLabel lbMed,JLabel lblMsj,JLabel lblCli,
                                     JDialog dlgObj,String pTipo_in,JLabel lblSinComision,JTextField txtObj){
        String pPermiteIngresoMedido = 
            UtilityFidelizacion.getPermiteIngresoMedido();

        if (pPermiteIngresoMedido.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            if (VariablesModuloVentas.vEsPedidoConvenio||(VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)) {
                FarmaUtility.showMessage(dlgObj, 
                                         "No puede ingresar el M�dido porque tiene" + 
                                         "seleccionado convenio.", 
                                         txtObj);
                return;
            }
            DlgBusquedaMedicoCamp dlgLista = new DlgBusquedaMedicoCamp(myParentFrame,"",true,lbMed,
                                                                       lblMsj,lblCli,dlgObj,pTipo_in,lblSinComision);
            dlgLista.setVisible(true);
            /*
            if(FarmaVariables.vAceptar){
                pExiste = "S";
            }
            else{
                pExiste = "NO_SELECCIONO";
            }
            */
        }
        else
            FarmaUtility.showMessage(dlgObj,"Por el momento no existen promociones por Receta.",txtObj);
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");
        log.debug("VariablesFidelizacion.V_NUM_CMP:"+VariablesFidelizacion.V_NUM_CMP);
        log.debug("VariablesFidelizacion.V_NOMBRE:"+VariablesFidelizacion.V_NOMBRE);
        log.debug("VariablesFidelizacion.V_DESC_TIP_COLEGIO:"+VariablesFidelizacion.V_DESC_TIP_COLEGIO);
        log.debug("VariablesFidelizacion.V_TIPO_COLEGIO:"+VariablesFidelizacion.V_TIPO_COLEGIO);
        log.debug("VariablesFidelizacion.V_COD_MEDICO:"+VariablesFidelizacion.V_COD_MEDICO);        
        log.debug("****** ====VARIABLES DE MEDICO ==========================******");        
    }

}
