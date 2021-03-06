package venta.convenioBTLMF;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.DlgNumeroTarjetaGenerado;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesVirtual;
import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.VariablesConvenio;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import componentes.gs.componentes.JPanelWhite;

import venta.convenio.reference.UtilityConvenio;
import venta.convenioBTLMF.reference.DBConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgProcesarCobro.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      07.01.2008   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgProcesarCobroBTLMF extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesarCobroBTLMF.class);

    private Frame myParentFrame;
    //private boolean vValorProceso;
    private JTable tblFormasPago;
    private JLabel lblVuelto;
    private JTable tblDetallePago;
    private JTextField txtNroPedido;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private String indCommitBefore = "";

    private boolean indUpdateEnConvenio = false;

    public DlgProcesarCobroBTLMF() {
        this(null, "", false);
    }

    /**
     * Constructor con parametros.
     * @param parent
     * @param title
     * @param modal
     */
    public DlgProcesarCobroBTLMF(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public DlgProcesarCobroBTLMF(Frame parent, String title, boolean modal,
                            JTable pTableModel, JLabel pVuelto,
                            JTable pDetallePago, JTextField pNroPedido) {
        super(parent, title, modal);
        myParentFrame = parent;
        tblFormasPago = pTableModel;
        lblVuelto = pVuelto;
        tblDetallePago = pDetallePago;
        txtNroPedido = pNroPedido;

        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(238, 104));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Pedido Convenio . . .");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    void this_windowOpened(WindowEvent e)
    {
       FarmaUtility.centrarVentana(this);
	   procesarBTLMF();
       cerrarVentana(false);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Validaciones de Cobro de Pedido
     * @author Dubilluz
     * @since  05.03.2009
     */
    private boolean validacionesCobroPedido() {
        //valida que haya sido seleccionado un pedido.
        if (VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        //validando que se haya cubierta el total del monto del pedido
        if (!VariablesCaja.vIndTotalPedidoCubierto) {
            FarmaUtility.showMessage(this,"El Pedido tiene saldo pendiente de cobro.Verifique!!!",
                                          tblFormasPago);
            return false;
        }

        //validando que el pedido este en esta PENDIENTE DE COBRO
        if (!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta,
                                              ConstantsCaja.ESTADO_PENDIENTE,
                                              null)) {
            return false;
        }

        //Inicio Adicion Paulo. Validacion para cajeros
        if (!UtilityCaja.existeCajaUsuarioImpresora(this, null)) {
            cerrarVentana(false);
            return false;
        }
        //Fin Adicion Paulo.

        /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * */
        if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
            FarmaUtility.liberarTransaccion();
            return false;
        }

         //valida que exista RUC si es FACTURA
        if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)) {
            if (VariablesModuloVentas.vRuc_Cli_Ped.trim().equalsIgnoreCase("")) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Debe ingresar el numero de RUC!!!",
                                         tblFormasPago);
                return false;
            }
        }

//        /**
//        *
//        */
//        if (!UtilityFidelizacion.validaPedidoFidelizado(VariablesCaja.vNumPedVta,
//                                                        VariablesVentas.vRuc_Cli_Ped,
//                                                        this, tblFormasPago)) {
//            return false;
//
//        }


        log.debug("VariablesCaja.vValTotalPagar:"+FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar));

        if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
        {
        	if(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vMontoSaldo))
        	{
        	  FarmaUtility.showMessage(this,"Saldo insuficiente del Benificiario!!!", tblFormasPago);
        	  return false;
        	}
        }
        /**
        * Bloqueo de caja
        */
        return true;

    }


    private void procesarBTLMF()
    {
        log.debug("<<<<<<<<<<<<<<<<<<<<<Metodo:procesarBTLMF>>>>>>>>>>>>>>>>>>>>");

        long tmpIni, tmpFin, tmpT1, tmpT2, tmpT3, tmpT4;

        tmpIni = System.currentTimeMillis();
        tmpT1 = System.currentTimeMillis();

        //INICIO DE VALIDACIONES
        if (!validacionesCobroPedido())
            return;

        tmpT2 = System.currentTimeMillis();
        log.debug("t1 Validaciondes Iniciales Cobro : " + (tmpT2 - tmpT1) +
                  " milisegundos");


        tmpT1 = System.currentTimeMillis();
        //UtilityCaja.bloqueoCajaApertura(VariablesCaja.vSecMovCaja);
        tmpT2 = System.currentTimeMillis();
        log.debug("t2 Bloqueo a una tabla para validar que se cierre la caja en otra PC : " +
                  (tmpT2 - tmpT1) + " milisegundos");

        /**
       * Se valida que la caja este abierta y se bloquea al mismo tiempo.
       * @AUTHOR JCORTEZ
       * @SINCE 18.05.09
       * */
        tmpT3 = System.currentTimeMillis();
        if (!validaCajaAbierta())
            return;
        tmpT4 = System.currentTimeMillis();
        log.debug("t3 Bloqueo Caja_Pago para validar que no cobre pedido si se cierra caja : " +
                  (tmpT3 - tmpT4) + " milisegundos");

        //INICIO PROCESO DE COBRO
        try
        {
            tmpT1 = System.currentTimeMillis();

            VariablesCaja.mostrarValoresVariables();
            //fin de la validacion de campa�as limitadas en cantidad de usos

            //verificar si es pedido por convenio
            String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
            VariablesModuloVentas.vEsPedidoConvenio =
                    (pIndPedConvenio.equals("S")) ? true : false;

            log.debug("jcallo VariablesVentas.vEsPedidoConvenio:" + VariablesModuloVentas.vEsPedidoConvenio);
            //fin verificar si es pedido por convenio


            //obtiene el monto del vuelto
            String vueltoPedido = lblVuelto.getText().trim();
            colocaVueltoDetallePago(vueltoPedido);
            log.debug("jcallo: despues de colocar vuelto");
            //detalle de formas de pago
            VariablesCaja.vDescripcionDetalleFormasPago = "";
            VariablesCaja.vDescripcionDetalleFormasPago =
                    ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
            for (int i = 0; i < tblDetallePago.getRowCount(); i++)
            {
                //grabar forma de pago del pedido
                DBCaja.grabaFormaPagoPedido(((String)tblDetallePago.getValueAt(i, 0)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 4)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 6)).trim(),
                                            VariablesCaja.vValTipoCambioPedido,
                                            ((String)tblDetallePago.getValueAt(i, 7)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 5)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 8)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 9)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 10)).trim(),
                                            ((String)tblDetallePago.getValueAt(i, 2)).trim());

                //descripcion de la forma de pago en el detalle
                VariablesCaja.vDescripcionDetalleFormasPago =
                        VariablesCaja.vDescripcionDetalleFormasPago +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 0) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " +
                        FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }

            //obtiene la descrip de la formas de pago para la impresion
            log.debug("jcallo: antes de la descripcion de formas de pago impresion");
            formasPagoImpresion();
            log.debug("jcallo: fin de descripcion de formas de pago");
            //actualiza datos del cliente como nombre direccion ruc, etc
            if (!VariablesModuloVentas.vCod_Cli_Local.equalsIgnoreCase(""))
            {
                log.debug("Actualizando datos del cliente en local ...");
                actualizaClientePedido(VariablesCaja.vNumPedVta, VariablesModuloVentas.vCod_Cli_Local);
            }

            String resultado = "";
            String resMatriz = "";
            log.debug("VariablesConvenioBTLMF.vFlgValidaLincreBenef"+VariablesConvenioBTLMF.vFlgValidaLincreBenef);

            boolean esComprobanteCredito =false;           
            if (Double.parseDouble(VariablesCaja.vValMontoPagado)>0)
            {   esComprobanteCredito=true;                
            }               
            
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1") || esComprobanteCredito)
            {
                String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
                if (indConexionRac.equals("S"))
                {
                    //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
                    resultado = DBConvenioBTLMF.cobraPedido();
                    
                    if(resultado.trim().equalsIgnoreCase("EXITO"))
                    {
                        resMatriz = DBConvenioBTLMF.grabarTablasTmp();
                        if(resMatriz.equals("S"))
                        {
                            log.debug("-------------------GRABO EXITOSAMENTE MATRIZ-------- ");
                            DBConvenioBTLMF.grabarCobrarPedidoRac(FarmaConstants.INDICADOR_S);
                            DBConvenioBTLMF.actualizaFechaProcesoRac();
                        }
                    }
                }
                else
                {
                    FarmaUtility.showMessage(this, "No puede procesar el cobro. \n" +
                                                                       "Porque no hay una Conexi�n a la Base de Datos del RAC. \n" +
                                                                       "", tblFormasPago);
                    resultado = "ERROR";
                }
            }
            else
            {   resultado = DBConvenioBTLMF.cobraPedido();
            }

            log.debug(" Verificando si el pedido es EXITOSO resultado Verifica: " + resultado);
            tmpT2 = System.currentTimeMillis();
            log.debug("t5 Proceso de Cobro: " + (tmpT2 - tmpT1) +
                      " milisegundos");
            log.debug("Imprimiendo...VariablesCaja.vNumPedVta:" +VariablesCaja.vNumPedVta);
            //UtilityConvenioBTLMF.imprimirPedidoVta(this, null,VariablesCaja.vNumPedVta,VariablesConvenioBTLMF.vCodConvenio);

            if (resultado.trim().equalsIgnoreCase("EXITO"))
            {
                log.debug("antes de validar");
                if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago))
                {
                    FarmaUtility.liberarTransaccion();
                    return;
                }
                tmpFin = System.currentTimeMillis();
                log.debug("t15 Fin PROCESO COBRO antes del Commit a�n falta Imprimir: " +
                          (tmpFin - tmpIni) + " milisegundos");
    
                VariablesCaja.vIndPedidoCobrado = true;
    
                //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
                try
                {
                    if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
                    {
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                        FarmaUtility.aceptarTransaccion();
                    }
                    else
                    {
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                        FarmaUtility.aceptarTransaccion();
                    }
                    log.debug("Grabo el tiempo de fin de cobro");
                }
                catch (SQLException sql)
                {
                    log.error("",sql);
                    if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
                    {   FarmaUtility.liberarTransaccion();
                    }
                    else
                    {   FarmaUtility.liberarTransaccion();
                    }
                    log.debug("Error al grabar el tiempo de fin de cobro");
                }
                //Ejecutamos el Proceso de Impresion de Comprobantes
                UtilityConvenioBTLMF.procesoImpresionComprobante(this, null);
            }
            else if (resultado.trim().equalsIgnoreCase("ERROR"))
            {
            	if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
            	{   FarmaUtility.liberarTransaccion();
            	}
            	else
            	{   FarmaUtility.liberarTransaccion();
            	}

                VariablesCaja.vIndPedidoCobrado = false;
                FarmaUtility.showMessage(this,
                                         "El pedido no puede ser cobrado. \n" +
                        "Los totales de formas de pago y cabecera no coinciden. \n" +
                        "Comun�quese con el Operador de Sistemas inmediatamente." +
                        ". \n" +
                        "NO CIERRE LA VENTANA.", tblFormasPago);
            }

            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
            {
            	if (!resMatriz.equals("S"))
                {
                    FarmaUtility.liberarTransaccion();
                    VariablesCaja.vIndPedidoCobrado = false;
                    log.debug("Error al grabar datos Matriz");
                    FarmaUtility.showMessage(this, "Error al grabar datos en Matriz", tblFormasPago);
                }
            }

        } catch (SQLException sql)
        { //error de base de datos al cobrar

            log.error("",sql);
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
            {   FarmaUtility.liberarTransaccion();
            }
            else
            {   FarmaUtility.liberarTransaccion();
            }
            
            log.error(null, sql);
            String pMensaje = sql.getMessage();
            int nIsSecCajaNull = pMensaje.indexOf("CHECK_SEC_MOV_CAJA");
            log.debug("nIsSecCajaNull:" + nIsSecCajaNull);
            if (nIsSecCajaNull > 0)
            {   FarmaUtility.showMessage(this,
                                         "No se pudo cobrar el pedido.\nInt�ntelo nuevamente",
                                         tblFormasPago);
            } else
            {   log.error("",sql);
                if (sql.getErrorCode() > 20000)
                {
                    VariablesCaja.vIndPedidoCobrado = false;
                    FarmaUtility.showMessage(this,
                                             sql.getMessage().substring(10,
                                                                        sql.getMessage().indexOf("ORA-06512")),
                                             null);
                }else
                {
                    VariablesCaja.vIndPedidoCobrado = false;
                    FarmaUtility.showMessage(this, "Error en BD al cobrar el Pedido.\n" +
                            sql.getMessage(), tblFormasPago);
                }
            }
        }
        catch (Exception ex)
        { //error inesperado
        	log.error("",ex);
            log.error("",ex); //error inesperado
            if (indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                if (VariablesCaja.vIndPedidoConProdVirtual) {
                    //evalua indicador de impresion por error
                    String vIndImpre = "N";
                    try {
                        vIndImpre =
                                DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
                    } catch (Exception e) {
                        log.error("jcallo: no pudo obtener el indicador de impresion de recarga");
                        vIndImpre = "N";
                    }
                    log.debug("vIndImpre :" + vIndImpre);

                    if (!vIndImpre.equals("N")) {
                        log.error("imprimir si se trata de un producto virtual a pesar del error");
                        /*
    			       * Validacion de Fecha actual del sistema contra
    			       * la fecha del cajero que cobrara
    			       * Se a�adio para validar pedido Cobrado
    			       * despues de una fecha establecida al inicio
    			       * dubilluz 04.03.2009
    			       **/
                        log.debug("antes de validar");
                        if (!UtilityCaja.validaFechaMovimientoCaja(this,
                                                                   tblFormasPago)) {
                            FarmaUtility.liberarTransaccion();
                            return;
                        }

                        tmpFin = System.currentTimeMillis();
                        log.debug("t23 Finaliza el Proceso de Cobro antes de Commit: " +
                                  (tmpFin - tmpIni) + " milisegundos");

                        //FarmaUtility.aceptarTransaccion();
                        //**
                        //JMIRANDA 24.06.2010
                        //VALIDAR SI HIZO UPDATE DAR COMMIT
                        if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                            FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) !=
                            0) {
                            String vIndLineaMat =
                                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                               FarmaConstants.INDICADOR_S);

                            if (((indUpdateEnConvenio) &&
                                 VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
                                vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                FarmaUtility.aceptarTransaccion();
                                if (VariablesCaja.vIndCommitRemota) {
                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                          FarmaConstants.INDICADOR_N);
                                }
                            } else {
                                //DUBILLUZ 25.06.2010
                                VariablesCaja.vIndPedidoCobrado = false;

                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                      FarmaConstants.INDICADOR_N);
                                FarmaUtility.showMessage(this,
                                                         "Error al actualizar el uso del Convenio.\nComun�quese con el Operador de Sistemas.",
                                                         null);
                                return;
                            }
                        } else {
                            FarmaUtility.aceptarTransaccion();
                            if (VariablesCaja.vIndCommitRemota) {
                                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                      FarmaConstants.INDICADOR_N);
                            }
                        }
                        //**
                        /*if(VariablesCaja.vIndCommitRemota){
    					  //FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				  }*/
                        tmpT1 = System.currentTimeMillis();
                        log.error("jcallo:obtiene informacion de cajero");
                        UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                        log.error("jcallo:obtiene informacion del vendedor");
                        UtilityCaja.obtieneInfoVendedor();
                        log.error("jcallo:proceso de impresion de comprobante");
                        tmpT2 = System.currentTimeMillis();
                        log.debug("t24 Obtiene Info. de Vendedor y Cajero: " +
                                  (tmpT2 - tmpT1) + " milisegundos");

                        tmpT1 = System.currentTimeMillis();
                        //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro

                        try {
                            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,
                                                                    "F");
                            FarmaUtility.aceptarTransaccion();
                            log.debug("Grabo el tiempo de fin de cobro");

                        } catch (SQLException sql) {
                            log.error("",sql);
                            FarmaUtility.liberarTransaccion();
                            log.debug("Error al grabar el tiempo de fin de cobro");
                        }
                        //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
                        UtilityCaja.procesoImpresionComprobante(this,
                                                                txtNroPedido);
                        tmpT2 = System.currentTimeMillis();
                        log.debug("t25 Finaliza Proceso de Impresion de Comprobantes: " +
                                  (tmpT2 - tmpT1) + " milisegundos");

                        log.error("jcallo:fin proceso de impresion de comprobante");
                        log.error("FIN imprimir si se trata de un producto virtual a pesar del error");


                        FarmaUtility.showMessage(this,
                                                 "Error en Aplicacion al cobrar el Pedido.\n" +
                                ex.getMessage(), tblFormasPago);

                        FarmaUtility.showMessage(this,
                                                 "Comprobantes Impresos con Exito",
                                                 tblFormasPago);
                    } else {
                        FarmaUtility.liberarTransaccion();
                        if (VariablesCaja.vIndCommitRemota) {
                            log.debug("jcallo: liberando transaccione remota");
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                  FarmaConstants.INDICADOR_N);
                        }
                        FarmaUtility.showMessage(this,
                                                 "Error en Aplicacion al cobrar el Pedido.\n" +
                                ex.getMessage(), tblFormasPago);

                    }
                }
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Error en Aplicacion al cobrar el Pedido.\n" +
                        ex.getMessage(), tblFormasPago);
            }

            //no se pudo cobrar el pedido
            VariablesCaja.vIndPedidoCobrado = false;

        } finally {
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            log.debug("VariablesCaja.vIndLinea" + VariablesCaja.vIndLinea);
            log.info("cerrando conexion remota sea matriz, adm, vta institucional, etc");
            FarmaConnectionRemoto.closeConnection(); //dentro metodo solo cierra si estuvo abierta alguna conexion
            //verifica si existen pedidos pendientes de anulacion despues de N minutos
            tmpT1 = System.currentTimeMillis();
            UtilityCaja.verificaPedidosPendientes(this);
            tmpT2 = System.currentTimeMillis();
            log.debug("t26 Verifica Pedidos Pendientes Anular: " +
                      (tmpT2 - tmpT1) + " milisegundos");
        }

        tmpFin = System.currentTimeMillis();
        log.debug("t27 Fin de Todo el Proceso de Cobro: " + (tmpFin - tmpIni) +" milisegundos");

        cerrarVentana(false);


    }


    private void colocaVueltoDetallePago(String pVuelto) {
        if (tblDetallePago.getRowCount() <= 0) {
            return;
        }
        boolean existeSoles = false;
        boolean existeDolares = false;
        int filaSoles = 0;
        int filaDolares = 0;
        for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
            if (((String)tblDetallePago.getValueAt(i,
                                                   0)).trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
                existeSoles = true;
                filaSoles = i;
                break;
            } else if (((String)tblDetallePago.getValueAt(i,
                                                          0)).trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES)) {
                existeDolares = true;
                filaDolares = i;
            }
        }
        if (existeSoles) {
            tblDetallePago.setValueAt(pVuelto, filaSoles, 7);
        } else if (existeDolares && !existeSoles) {
            tblDetallePago.setValueAt(pVuelto, filaDolares, 7);
        }
        tblDetallePago.repaint();
    }

    /**
     * descripcion de las formas de pago
     * para la impresion
     */
    private void formasPagoImpresion() {
        //varificar que haya al menos una forma de pago declarado
        if (tblDetallePago.getRowCount() <= 0) {
            VariablesCaja.vFormasPagoImpresion = "";
            return;
        }
        //obtiene la descripcion de las formas de pago para la impresion
        for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
            if (i == 0) {
                VariablesCaja.vFormasPagoImpresion =
                        ((String)tblDetallePago.getValueAt(i, 1)).trim();
            } else {
                VariablesCaja.vFormasPagoImpresion +=
                        ", " + ((String)tblDetallePago.getValueAt(i,
                                                                  1)).trim();
            }
        }

        String codFormaPagoDolares = getCodFormaPagoDolares();
        String codFormaPago = "";
        if (codFormaPagoDolares.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            log.debug("La Forma de Pago Dolares esta Inactiva");
        } else {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
                codFormaPago =
                        ((String)tblDetallePago.getValueAt(i, 0)).trim();
                if (codFormaPagoDolares.equalsIgnoreCase(codFormaPago))
                    VariablesCaja.vFormasPagoImpresion =
                            VariablesCaja.vFormasPagoImpresion +
                            "  Tipo Cambio:  " +
                            VariablesCaja.vValTipoCambioPedido;
            }
        }
    }

    private void actualizaClientePedido(String pNumPedVta,
                                        String pCodCliLocal) throws SQLException {
        DBCaja.actualizaClientePedido(pNumPedVta, pCodCliLocal, VariablesModuloVentas.vNom_Cli_Ped, VariablesModuloVentas.vDir_Cli_Ped, VariablesModuloVentas.vRuc_Cli_Ped);
    }

    private void procesaPedidoVirtual() throws Exception {
        obtieneInfoPedidoVirtual();
        if (VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1) {
            throw new Exception("Error al validar info del pedido virtual");
        }
        colocaInfoPedidoVirtual();
        try {
            UtilityCaja.procesaVentaProductoVirtual(this, txtNroPedido);
        } catch (Exception ex) {
            throw new Exception("Error al procesar el pedido virtual - \n" +
                    ex.getMessage());

        }
        /*
     * Se grabara la respuesta obtenida por el proveedor al realizar la
     * recarga virtual
     */
        DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                            VariablesCaja.vNumPedVta);

        if (!validaCodigoRespuestaTransaccion()) {
            throw new Exception("Error al realizar la transaccion con el proveedor.\n" +
                    VariablesVirtual.respuestaTXBean.getCodigoRespuesta() +
                    " - " + VariablesVirtual.respuestaTXBean.getDescripcion());
        }
    }

    private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual) {
        if(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA.equalsIgnoreCase(pTipoProdVirtual))
            muestraTarjetaVirtualGenerado();
        else if (ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA.equalsIgnoreCase(pTipoProdVirtual))
        {
            if(VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL))
                FarmaUtility.showMessage(this, 
                                        "La recarga autom�tica se realiz� satisfactoriamente.", 
                                        null);
            else
                FarmaUtility.showMessage(this, 
                                    "Verifique en su m�dulo de consulta la confirmaci�n de la recargas\n" +
                                    "No se pudo Obtener la respuesta del proveedor por lentitud en conexi�n.", 
                                    null);                
                
        }
    }

    /**
     * Obtiene el codFormaPago Dolares
     * @author dubilluz
     * @since  13.10.2007
     */
    public String getCodFormaPagoDolares() {
        String codFP = "";
        try {
            codFP = DBCaja.getCodFPDolares();
        } catch (SQLException ex) {
            log.error("",ex);
            FarmaUtility.showMessage(this,
                                     "Error al Obtener el codidgo de Forma de Pago Dolares.\n" +
                    ex.getMessage(), tblFormasPago);
        }
        log.debug("jcallo: codforma de pago dolares " + codFP);
        return codFP;
    }

    private void obtieneInfoPedidoVirtual() throws Exception {
        try {
            DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual,
                                            VariablesCaja.vNumPedVta);
            log.debug("vArrayList_InfoProdVirtual : " +
                               VariablesVirtual.vArrayList_InfoProdVirtual);
        } catch (SQLException sql) {
            log.error("",sql);
            throw new Exception("Error al obtener informacion del pedido virtual - \n" +
                    sql);
        }
    }

    private void colocaInfoPedidoVirtual() {
        VariablesCaja.vCodProd =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 0);
        VariablesCaja.vTipoProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 1);
        VariablesCaja.vPrecioProdVirtual =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 2);
        VariablesCaja.vNumeroCelular =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 3);
        VariablesCaja.vCodigoProv =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 4);
        VariablesCaja.vTipoTarjeta =
                FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual,
                                                    0, 7);
    }

    private boolean validaCodigoRespuestaTransaccion()
    {
        boolean result = false;
        if (VariablesVirtual.vCodigoRespuesta != null) {
            log.debug("VariablesVirtual.vCodigoRespuesta - " + VariablesVirtual.vCodigoRespuesta);
            if (VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_OK_TAR_VIRTUAL) ||
                VariablesVirtual.vCodigoRespuesta.trim().equalsIgnoreCase(ConstantsCaja.COD_RESPUESTA_COBRA_REVISE_EST_TAR_VIRTUAL))
                result = true;

            log.debug("validaCodigoRespuestaTransaccion result - " + result);
        }
        return result;
    }


    private void muestraTarjetaVirtualGenerado() {
        DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado =
            new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
        dlgNumeroTarjetaGenerado.setVisible(true);
        FarmaVariables.vAceptar = false;
    }

    /**
     * Obti
     * @return
     */
    private String getIndCommitAntesRecargar() {
        String ind;
        try {
            ind = DBCaja.obtieneIndCommitAntesdeRecargar();
            log.debug("ind Impr Antes de Recargar" + ind);
        } catch (SQLException sql) {
            ind = "N";
            log.error("",sql);
        }

        return ind.trim();
    }

    private void ejecutaRecargaVirtual() throws Exception {
        procesaPedidoVirtual();
        log.debug("VariablesVirtual.vCodigoRespuesta 2" +
                           VariablesVirtual.vCodigoRespuesta);
        log.debug("**** graba la respuesta obtenida... ** ");

    }

    /**
     * Se generan los cupones por pedido luego de ser cobrados
     * @author JCORTEZ
     * @since 03.07.2008
     * */
    private boolean generarPedidoCupon(String NumPed) {
        boolean valor = false;
        try {
            DBCaja.generarCuponPedido(NumPed);
            valor = true;
            log.debug("Se generaron los cupones del pedido :" +
                               NumPed);
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this,
                                     "Se genero un error al generar los cupones\n" +
                    sql.getMessage(), tblFormasPago);
        }
        return valor;
    }


    private void cargarHora(String men) {
        try {
            String sysdate =
                FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
            log.debug("FECHA HORA------------------------------------------------->" +
                               sysdate + "dentro del proceso" + men);
            log.debug(sysdate);
            Date date1 =
                FarmaUtility.getStringToDate(sysdate, "dd/MM/yyyy HH:mm:ss");
        } catch (SQLException e) {
            log.error("",e);
        }
    }


    /**
     * Actualiza los cupones en Matriz
     * @author Diego Ubilluz
     * @param pNumPedVta
     */
    private void actualizaCuponesEnMatriz(String pNumPedVta,
                                          ArrayList pListaCuponesPedido,
                                          String pIndLinea) {

        ArrayList listCupones = new ArrayList();
        listCupones = (ArrayList)pListaCuponesPedido.clone();
        String vIndLineaBD = "";
        String vCodCupon = "";
        String vResActMatriz = "";
        boolean vActCupon = false;
        int COL_COD_CUPON = 0;
        int COL_COD_FECHA_INI = 1;
        int COL_COD_FECHA_FIN = 2;
        String vEstCuponMatriz = "";
        String vRetorno = "";
        String vFechIni = "";
        String vFechFin = "";
        String indMultiUso = "";
        try {
            if (listCupones.size() > 0) {
                if (listCupones.size() == 1) {
                    String vValor = "";
                    vValor =
                            FarmaUtility.getValueFieldArrayList(listCupones, 0,
                                                                COL_COD_CUPON);
                    if (vValor.equalsIgnoreCase("NULO")) {
                        return;
                    }
                }

                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea;
                //SE ESTA FORZANDO QUE NO HAYA LINEA
                vIndLineaBD = FarmaConstants.INDICADOR_N;
                if (vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                    log.debug("No existe linea se cerrara la conexion ...");
                    FarmaConnectionRemoto.closeConnection();
                }

                // 3. SE ACTUALIZA EL CUPON
                for (int i = 0; i < listCupones.size(); i++) {
                    vCodCupon =
                            FarmaUtility.getValueFieldArrayList(listCupones, i,
                                                                COL_COD_CUPON);
                    vFechIni =
                            FarmaUtility.getValueFieldArrayList(listCupones, i,
                                                                COL_COD_FECHA_INI);

                    vFechFin =
                            FarmaUtility.getValueFieldArrayList(listCupones, i,
                                                                COL_COD_FECHA_FIN);

                    indMultiUso =
                            DBCaja.getIndCuponMultiploUso(pNumPedVta, vCodCupon).trim();

                    if (indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        log.debug("Actualiza en local  ...");
                        DBCaja.actualizaCuponGeneral(vCodCupon,
                                                     ConstantsCaja.CONSULTA_ACTUALIZA_CUPON_LOCAL);


                        vActCupon = true;
                        //Si hay linea se actualizara en matriz
                        /*
                         * if (vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {


                            vEstCuponMatriz =
                                    DBCaja.getEstadoCuponEnMatriz(vCodCupon,
                                                                  FarmaConstants.INDICADOR_N).trim();

                            //--Si valor de retorno es "0" es porque el cupon
                            //  no existe asi que se creara en Matriz
                            if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                log.debug("Se graba el cupon en Matriz");
                                vRetorno =
                                        DBCaja.grabaCuponEnMatriz(vCodCupon,
                                                                  vFechIni,
                                                                  vFechFin,
                                                                  FarmaConstants.INDICADOR_N).trim();
                            }

                            log.debug("Actualiza en matriz  ...");

                            vResActMatriz =
                                    DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon,
                                                                        ConstantsCaja.CUPONES_USADOS,
                                                                        FarmaConstants.INDICADOR_N);



                            //--Si la actualizacion se realizo con exito se actualiza
                            //  en el local que el cupon ya se proceso en Matriz
                            if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                DBCaja.actualizaCuponGeneral(vCodCupon.trim(),
                                                             ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                            }

                            VariablesCaja.vIndCommitRemota = true;
                            log.debug("Fin de actualizacion");

                        }
                        */
                    } else
                        log.debug("Es un cupon multiuso..");

                }
            }
        } catch (SQLException e) {

            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_N);
        }
        /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * Se a�adio para validar pedido Cobrado
        * despues de una fecha establecida al inicio
        * dubilluz 04.03.2009
        **/
        log.debug("antes de validar");
        if (!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)) {
            FarmaUtility.liberarTransaccion();
            return;
        }
        if (vActCupon)
            FarmaUtility.aceptarTransaccion();

        if (VariablesCaja.vIndCommitRemota)
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_N);
    }

    /**
     * Valida el uso de cupones
     * @author dubilluz
     * @since  20.08.2008
     */
    private boolean validaUsoCupones(String pNumPedVta,
                                     String pIndCloseConecction,
                                     ArrayList pListaCuponesPedido,
                                     String pIndLinea) {
        log.debug("validando uso de cupones");

        ArrayList listCupones = new ArrayList();
        ArrayList ltDatosCupon = new ArrayList();
        String vCodCupon = "";
        String indMultiUso = "";
        String vIndLineaBD = "";
        String valida = "";
        String vEstCupon = "";
        boolean retorno = false;
        try {
            listCupones = (ArrayList)pListaCuponesPedido.clone();
            log.debug("listCupones " + listCupones);
            // 1. SE VERIFICA SI EL VALOR DE LA LISTA NO FUE NULO
            if (listCupones.size() > 0) {
                if (listCupones.size() == 1) {
                    String vValor = "";
                    vValor =
                            FarmaUtility.getValueFieldArrayList(listCupones, 0,
                                                                0);
                    if (vValor.equalsIgnoreCase("NULO")) {
                        retorno = true;
                        return retorno;
                    }
                }

                //  2. Se verificara si hay linea con matriz
                //--El segundo parametro indica si se cerrara la conexion
                vIndLineaBD = pIndLinea.trim();

                // 3. SE VALIDARA CADA CUPON
                for (int i = 0; i < listCupones.size(); i++) {
                    vCodCupon =
                            FarmaUtility.getValueFieldArrayList(listCupones, i,
                                                                0);
                    indMultiUso =
                            DBCaja.getIndCuponMultiploUso(pNumPedVta, vCodCupon).trim();

                    //Se valida el Cupon en el local
                    //Modificado por DVELIZ 04.10.08
                    DBModuloVenta.verificaCupon(vCodCupon, ltDatosCupon,
                                           indMultiUso,
                                           VariablesFidelizacion.vDniCliente);

                    //Se validara el cupon en matriz si hay linea
                    /*
                   if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                      log.debug("Validando en matriz  ...");
                      valida = DBVentas.verificaCuponMatriz(vCodCupon,indMultiUso,
                                                            FarmaConstants.INDICADOR_N);
                      log.debug("");
                      if(!valida.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                       if(valida.trim().equalsIgnoreCase("B"))
                         {
                           retorno = false;
                           FarmaUtility.showMessage(this,"La campa�a no es valida.",tblFormasPago);
                           break;
                         }else
                         {
                           retorno = false;
                           break;
                         }
                      }
                   }
                   */
                    vEstCupon =
                            DBCaja.getEstCuponBloqueo(pNumPedVta, vCodCupon).trim();
                    log.debug("Se bloquea el estado .. " + vEstCupon);

                }
            }

            retorno = true;


        } catch (SQLException e) {
            //cierra la conexion
            FarmaConnectionRemoto.closeConnection();
            retorno = false;
            log.error("",e);
            log.error(null, e);
            switch (e.getErrorCode()) {
            case 20003:
                FarmaUtility.showMessage(this, "La campa�a no es valida.",
                                         tblFormasPago);
                break;
            case 20004:
                FarmaUtility.showMessage(this,
                                         "Local no valido para el uso del cupon.",
                                         tblFormasPago);
                break;
            case 20005:
                FarmaUtility.showMessage(this, "Local de emisi�n no valido.",
                                         tblFormasPago);
                break;
            case 20006:
                FarmaUtility.showMessage(this,
                                         "Local de emisi�n no es local de venta.",
                                         tblFormasPago);
                break;
            case 20007:
                FarmaUtility.showMessage(this, "Cup�n ya fue usado.",
                                         tblFormasPago);
                break;
            case 20008:
                FarmaUtility.showMessage(this, "Cup�n esta anulado.",
                                         tblFormasPago);
                break;
            case 20009:
                FarmaUtility.showMessage(this, "Campa�a no valido.",
                                         tblFormasPago);
                break;
            case 20010:
                FarmaUtility.showMessage(this, "Cupon no esta vigente .",
                                         tblFormasPago);
                break;
            default:
                FarmaUtility.showMessage(this, "Error al validar el cupon.\n" +
                        e.getMessage(), tblFormasPago);
                break;

            }

        }
        log.debug("**FIN**");
        return retorno;
    }

    /**
     * metodo encargado de obtener el dni del cliente fidelizado que realizo la compra
     *
     * */
    private String obtenerDniClienteFidelizado(String nroPedido) {
        String dniClienteFid = "";
        try {
            dniClienteFid = DBCaja.obtieneDniClienteFidVenta(nroPedido).trim();
        } catch (Exception e) {
            dniClienteFid = "";
            log.debug("error al obtener DNI cliente del pedido : " +
                      e.getMessage());
        }

        return dniClienteFid;
    }


    /**
     * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList getCampAutomaticasPedido(String nroPedido) {
        ArrayList listaCampAutomaticas = new ArrayList();
        try {
            listaCampAutomaticas =
                    DBCaja.getListaCampAutomaticasVta(nroPedido);
            if (listaCampAutomaticas.size() > 0) {
                listaCampAutomaticas = (ArrayList)listaCampAutomaticas.get(0);
            }
            log.debug("listaCampAutomaticas listaCampAutomaticas ===> " +
                      listaCampAutomaticas);
        } catch (Exception e) {
            log.debug("error al obtener campa�as automaticas usados en el pedido : " +
                      e.getMessage());
        }
        return listaCampAutomaticas;
    }

    /**
     * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList CampLimitadasUsadosDeLocalXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosLocal = new ArrayList();
        try {
            listaCampLimitUsadosLocal =
                    DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
            if (listaCampLimitUsadosLocal.size() > 0) {
                listaCampLimitUsadosLocal =
                        (ArrayList)listaCampLimitUsadosLocal.get(0);
            }
            log.debug("listaCampLimitUsadosLocal listaCampLimitUsadosLocal ===> " +
                      listaCampLimitUsadosLocal);
        } catch (Exception e) {
            log.debug("error al obtener las campa�as limitadas ya usados por cliente en LOCAL : " +
                      e.getMessage());
        }
        return listaCampLimitUsadosLocal;
    }

    /**
     * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
     *
     * */
    private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz =
                    DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            if (listaCampLimitUsadosMatriz.size() > 0) {
                listaCampLimitUsadosMatriz =
                        (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " +
                      listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.debug("error al obtener las campa�as limitadas ya usados por cliente en MATRIZ : " +
                      e.getMessage());
        }
        return listaCampLimitUsadosMatriz;
    }


    private boolean validaCajaAbierta() {

        boolean result = true;
        String Indicador = "";
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            log.debug("VariablesCaja.vNumCaja ===> " + VariablesCaja.vNumCaja);
            Indicador = DBCaja.obtieneEstadoCaja();
            if (Indicador.trim().equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(this,
                                         "La caja no se encuentra aperturada. Verifique!!!",
                                         null);
                result = false;
            }
            log.debug("Se valida apertura de caja para el cobro ===> " +
                      Indicador);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            result = false;
            log.debug("error al obtener indicador de caja abierta : " +
                      e.getMessage());
        }

        //bloque de caja
        return result;
    }

}
