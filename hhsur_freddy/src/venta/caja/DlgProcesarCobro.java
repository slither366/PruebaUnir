package venta.caja;

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

import java.util.HashMap;

import venta.caja.reference.FacadeCaja;
import venta.convenio.reference.UtilityConvenio;
import venta.recaudacion.reference.ConstantsRecaudacion;
import venta.recaudacion.reference.FacadeRecaudacion;
import venta.recaudacion.reference.UtilityRecaudacion;
import venta.recetario.reference.ConstantsRecetario;
import venta.recetario.reference.DBRecetario;
import venta.recetario.reference.FacadeRecetario;
import venta.reference.VariablesPtoVenta;
import venta.recetario.reference.VariablesRecetario;


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
public class DlgProcesarCobro extends JDialog {
	
  private static final Logger log = LoggerFactory.getLogger(DlgProcesarCobro.class);

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
  private FacadeRecetario facadeRecetario = new FacadeRecetario();
  private FacadeCaja facadeCaja = new FacadeCaja();
  private FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
  
  public DlgProcesarCobro()
  {
    this(null, "", false);
  }

  /**
   * Constructor con parametros.
   * @param parent
   * @param title
   * @param modal
   */
  public DlgProcesarCobro(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }

  public DlgProcesarCobro(Frame parent, String title, boolean modal, 
                          JTable pTableModel, JLabel pVuelto, 
                          JTable pDetallePago, JTextField pNroPedido) {
	super(parent, title, modal);
	myParentFrame = parent;
	tblFormasPago = pTableModel;
	lblVuelto = pVuelto;
	tblDetallePago = pDetallePago;
	txtNroPedido = pNroPedido;
	
	try
	{
	  jbInit();
	}
	catch (Exception e)
	{
	  log.error("",e);
	}
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(238, 104));
    this.getContentPane().setLayout(null);
    this.setTitle("Procesando Informaci�n . . .");
    this.getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }
        });
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  void this_windowOpened(WindowEvent e)
  { 
    FarmaUtility.centrarVentana(this);
    procesar();
    cerrarVentana(false);
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  /**
   * Validaciones de Cobro de Pedido
   * @author Dubilluz
   * @since  05.03.2009
   */
  private boolean validacionesCobroPedido(){
      //valida que haya sido seleccionado un pedido.
       if (VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                 return false;

       //validando que se haya cubierta el total del monto del pedido
       if (!VariablesCaja.vIndTotalPedidoCubierto){
               FarmaUtility.showMessage(this, "El Pedido tiene saldo pendiente de cobro.Verifique!!!", tblFormasPago);
               return false;
       }

       //validando que el pedido este en esta PENDIENTE DE COBRO
       if (!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta,ConstantsCaja.ESTADO_PENDIENTE, null)){
           return false;
       } 

       //Inicio Adicion Paulo. Validacion para cajeros
       if (!UtilityCaja.existeCajaUsuarioImpresora(this, null)){
               cerrarVentana(false);
           return false;
       }
       //Fin Adicion Paulo.
       
       /*
        * Validacion de Fecha actual del sistema contra
        * la fecha del cajero que cobrara
        * */
       if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
               FarmaUtility.liberarTransaccion();
           return false;
       }
       
       //valida que exista RUC si es FACTURA
       if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
               if (VariablesModuloVentas.vRuc_Cli_Ped.trim().equalsIgnoreCase("")){
                       FarmaUtility.liberarTransaccion();
                       FarmaUtility.showMessage(this, "Debe ingresar el numero de RUC!!!", tblFormasPago);
                   return false;
               }
       }
       
       /**
        * 
        */     
       if(!UtilityFidelizacion.validaPedidoFidelizado(VariablesCaja.vNumPedVta, VariablesModuloVentas.vRuc_Cli_Ped,
                                                      this,
                                                     tblFormasPago,
                                                      // dubilluz 01.06.2012
                                                      VariablesFidelizacion.vNumTarjeta
                                                     ))
       {
           return false;
           
       }
       /**
        * Bloqueo de caja
        */
       return true;
       
       
  }
  private void procesar()
  {
      long tmpIni,tmpFin,tmpT1,tmpT2,tmpT3,tmpT4;
      
      tmpIni = System.currentTimeMillis();
      tmpT1 = System.currentTimeMillis();
      
     //INICIO DE VALIDACIONES
      if(!validacionesCobroPedido())
          return;
      
      tmpT2 = System.currentTimeMillis();      
      log.debug("t1 Validaciondes Iniciales Cobro : "+(tmpT2 - tmpT1)+" milisegundos");
      
      
      tmpT1 = System.currentTimeMillis();      
      //UtilityCaja.bloqueoCajaApertura(VariablesCaja.vSecMovCaja);    
      tmpT2 = System.currentTimeMillis();      
      log.debug("t2 Bloqueo a una tabla para validar que se cierre la caja en otra PC : "+(tmpT2 - tmpT1)+" milisegundos");
      
      /**
       * Se valida que la caja este abierta y se bloquea al mismo tiempo.
       * @AUTHOR JCORTEZ 
       * @SINCE 18.05.09
       * */
      tmpT3 = System.currentTimeMillis();      
      if(!validaCajaAbierta())
       return;
      tmpT4 = System.currentTimeMillis(); 
      log.debug("t3 Bloqueo Caja_Pago para validar que no cobre pedido si se cierra caja : "+(tmpT3 - tmpT4)+" milisegundos");
      
    //INICIO PROCESO DE COBRO
    try {
        
    	//-- inicio validacion cupones
        //Se consulta para obtener los cupones usados en el pedido
        tmpT1 = System.currentTimeMillis();      
        
        VariablesCaja.vIndLinea = "";
        VariablesCaja.listCuponesUsadosPedido = new ArrayList();
        VariablesCaja.vIndEnvioRecargar = false;//se agrego para tener un indicador si se mando realizar la recarga virtual
        
        DBCaja.getcuponesPedido(VariablesCaja.vNumPedVta,
                                FarmaConstants.INDICADOR_N,
                                VariablesCaja.listCuponesUsadosPedido,
                                ConstantsCaja.CONSULTA_VALIDA_CUPONES);
        
        log.debug("JCALLO VariablesCaja.listCuponesUsadosPedido:"+VariablesCaja.listCuponesUsadosPedido);
        //validar los cupones usados en el pedido
        if(VariablesCaja.listCuponesUsadosPedido.size()>0){
        	//validacion de cupon activos, ya que alguno pudiera estar inactivo por xmotivos
            log.debug("entro a validar cupones");
            if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            }
            boolean resp = validaUsoCupones(VariablesCaja.vNumPedVta,
                                            FarmaConstants.INDICADOR_N,
                                            VariablesCaja.listCuponesUsadosPedido,
                                            VariablesCaja.vIndLinea);
            //si alguno esta inactivo cancelar el proceso de cobros
            if(!resp){
            	FarmaUtility.liberarTransaccion();
                return;
            }
        }
        
          tmpT2 = System.currentTimeMillis();      
          log.debug("t3 Validacion de cupones Usados: "+(tmpT2 - tmpT1)+" milisegundos");
          tmpT1 = System.currentTimeMillis();      
        log.debug("jcallo:  validacion de campa�as limitadas en cantidad de uso");
        //validacion de campa�as limitadas en cantidad de uso        
        //obteniendo el dni del cliente si se trata de una venta cliente fidelizado
        String dniClienteFidelizado = obtenerDniClienteFidelizado(VariablesCaja.vNumPedVta).trim();
        log.debug("jcallo :dniClienteFidelizado:"+dniClienteFidelizado);
        if(dniClienteFidelizado.length()>0){//quiere decir que es pedido de venta fidelizado
        	ArrayList listaCampLimitTerminados = new ArrayList();
        	ArrayList listaCampAutomaticasPedido = new ArrayList();
        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);//obtiene todas las campa�as automaticas usados en el pedido
        	log.debug("jcallo :listaCampAutomaticasPedido:"+listaCampAutomaticasPedido);
                //DUBILLUZ - 01.12.2009
                //Correccion reportado por Jose.
        	/*
        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
                */
        	
        	/*if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){//si hay linea con matriz
        		//traer todas las campa�as limitadas que no deben aplicar para el cliente
        		listaCampLimitTerminados = CampLimitadasUsadosDeMatrizXCliente(dniClienteFidelizado);
        		log.debug("jcallo :listaCampLimitTerminados de MATRIZ:"+listaCampLimitTerminados);
        	}else{
        		listaCampLimitTerminados = CampLimitadasUsadosDeLocalXCliente(dniClienteFidelizado);
        		log.debug("jcallo :listaCampLimitTerminados de LOCAL:"+listaCampLimitTerminados);
        	}
                */
                //Valida localmente
                //14.04.2009 DUBILLUZ
                listaCampLimitTerminados = CampLimitadasUsadosDeLocalXCliente(dniClienteFidelizado);
                log.debug("jcallo :listaCampLimitTerminados de LOCAL:"+listaCampLimitTerminados);
        	
        	
        	boolean flag = false;
        	
        	for(int i = 0; i<listaCampLimitTerminados.size();i++){
        		String cod_camp = listaCampLimitTerminados.get(i).toString();
        		log.debug(" jcallo : cod_camp :"+cod_camp);
        		if( listaCampAutomaticasPedido.contains(cod_camp) ){
        			log.debug(" jcallo : cod_camp ENCONTRADO UNO QUE NO DEBERIA PODER USARSE:"+cod_camp);
        			flag = true;
        			break;
        		}
        	}
        	
        	if(flag){//quiere decir que encontro al menos una campa�a que ya no deberia de aplicar anular el pedido
        		FarmaUtility.liberarTransaccion();
        		FarmaUtility.showMessage(this, "Error al cobrar pedido !\nEl descuento de la campa�a ya fue usado por el cliente !", tblFormasPago);
                return;
        	}
        	
        }
          tmpT2 = System.currentTimeMillis();      
          log.debug("t4 Validaciondes de Fidelizados.Uso de campa�as Automatica: "+(tmpT2 - tmpT1)+" milisegundos");        
        
        log.debug("jcallo:  fin de validacion de campa�as limitadas en cantidad de uso");
          tmpT1 = System.currentTimeMillis();      
        VariablesCaja.mostrarValoresVariables();
        //fin de la validacion de campa�as limitadas en cantidad de usos
        
        //verificar si es pedido por convenio
        String pIndPedConvenio = DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta);
            VariablesModuloVentas.vEsPedidoConvenio = (pIndPedConvenio.equals("S")) ? true:false;
        
        log.debug("jcallo VariablesVentas.vEsPedidoConvenio:" + VariablesModuloVentas.vEsPedidoConvenio);
        //fin verificar si es pedido por convenio

        //numero de liner por BOLETA
        if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA)){
        	if(VariablesModuloVentas.vEsPedidoConvenio){
        		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(
        				DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_BOLETA_CON_CONVENIO));
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }else{
                VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO = Integer.parseInt(
                		DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_BOLETA_SIN_CONVENIO));
                VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		VariablesCaja.TOTAL_LINEAS_POR_BOLETA_CONVENIO);
            }
        } else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)) { //numero de linea por FACTURA
             if(VariablesModuloVentas.vEsPedidoConvenio){
                 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_FACTURA_CON_CONVENIO));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
             }else{
                 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_FACTURA_SIN_CONVENIO));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_FACTURA_CONVENIO);
             }
        }else if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET)) { //JCORTEZ 25.03.09 numero de linea por TICKET
             if(VariablesModuloVentas.vEsPedidoConvenio){
                 VariablesCaja.TOTAL_LINEAS_POR_TICKET = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_TICKET));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_TICKET);
             }else{
                 VariablesCaja.TOTAL_LINEAS_POR_TICKET = Integer.parseInt(
                		 DBCaja.getLineasDetalleDocumento(ConstantsModuloVenta.LINEAS_TICKET));
                 VariablesCaja.vNumSecImpresionComprobantes = DBCaja.agrupaImpresionDetallePedido(
                		 VariablesCaja.TOTAL_LINEAS_POR_TICKET);
             }
        }
        
        //muestra las secuencia de numero de comprobantes
        log.debug("jcallo:VariablesCaja.vNumSecImpresionComprobantes : " + VariablesCaja.vNumSecImpresionComprobantes);
        //obtiene el monto del vuelto
        String vueltoPedido = lblVuelto.getText().trim();
        colocaVueltoDetallePago(vueltoPedido);
        log.debug("jcallo: despues de colocar vuelto");
        //detalle de formas de pago
        VariablesCaja.vDescripcionDetalleFormasPago = "";
        VariablesCaja.vDescripcionDetalleFormasPago = ConstantsCaja.COLUMNAS_DETALLE_FORMA_PAGO;
        int vFila = 0;
        for (int i = 0; i < tblDetallePago.getRowCount(); i++)
        {
            vFila++;
        	//grabar forma de pago del pedido
                //20.03.2013 LTERRAZOS Se modifica el metodo que inserta a la tabla grabaFormaPagoPedido  contiene campos de tarjeta
                DBCaja.grabaFormaPagoPedido(((String) tblDetallePago.getValueAt(i,0)).trim(), 
                                       ((String) tblDetallePago.getValueAt(i,4)).trim(),      
                                       ((String) tblDetallePago.getValueAt(i,6)).trim(),      
                                       VariablesCaja.vValTipoCambioPedido,                    
                                       ((String) tblDetallePago.getValueAt(i,7)).trim(),      
                                       ((String) tblDetallePago.getValueAt(i,5)).trim(),      
                                       ((String) tblDetallePago.getValueAt(i,8)).trim(),      
                                       ((String) tblDetallePago.getValueAt(i,9)).trim(),      
                                       ((String) tblDetallePago.getValueAt(i,10)).trim(),     
                                       ((String) tblDetallePago.getValueAt(i,2)).trim(),
                                              
                                       ((String) tblDetallePago.getValueAt(i,12)).trim(),
                                       ((String) tblDetallePago.getValueAt(i,13)).trim(),
                                       ((String) tblDetallePago.getValueAt(i,14)).trim(),
            ((String) tblDetallePago.getValueAt(i,15)).trim(),
                                            vFila);     
                
        	//descripcion de la forma de pago en el detalle
        	VariablesCaja.vDescripcionDetalleFormasPago = VariablesCaja.vDescripcionDetalleFormasPago + 
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
        if(!VariablesModuloVentas.vCod_Cli_Local.equalsIgnoreCase("")) {
           log.debug("Actualizando datos del cliente en local ...");
           actualizaClientePedido(VariablesCaja.vNumPedVta, VariablesModuloVentas.vCod_Cli_Local);
        }
        
        //cobrar pedido DEVOLVERA EXITO. si cobro correctamente
        //JCORTEZ 18.08.09 Se agrega DNI para guardar en cupone generados
        String resultado = DBCaja.cobraPedido(VariablesModuloVentas.vTip_Comp_Ped,VariablesCaja.vPermiteCampa�a.trim(),dniClienteFidelizado.trim());
        log.debug(" verificando si el pedido es EXITOSO resultado Verifica: " + resultado);
        
        
          tmpT2 = System.currentTimeMillis();      
          log.debug("t5 Proceso de Cobro: "+(tmpT2 - tmpT1)+" milisegundos");        
        
        if ( resultado.trim().equalsIgnoreCase("EXITO") ) {
            
            tmpT1 = System.currentTimeMillis();      
            if (!UtilityCaja.validaAgrupacionComprobante(this,tblFormasPago)) {//el liberar transaccion esta dentro del metodo
            //FarmaUtility.liberarTransaccion();
                log.debug("error al agrupar comprobantes ... !!!!");
                VariablesCaja.vIndPedidoCobrado = false;
                return;
            }
            tmpT2 = System.currentTimeMillis();      
            log.debug("t6 Agrupa Comprobantes: "+(tmpT2 - tmpT1)+" milisegundos");        
            
            VariablesCaja.vIndPedidoCobrado = true;
            log.debug("jcallo:VariablesCaja.vIndPedidoConvenio=" + VariablesCaja.vIndPedidoConvenio + ", VariablesConvenio.vValCoPago=" +VariablesConvenio.vValCoPago);
            //si es pedido convenio y va usar credito de convenio
          if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && 
              FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0) {
              tmpT1 = System.currentTimeMillis(); 
              //JMIRANDA 23.06.2010 COMENTADO PARA LA NUEVA VALIDACION
          /*
        	  log.debug("jcallo: actualizar el monto del cliente. VariablesConvenio.vValCredDis=" + 
                            	  	VariablesConvenio.vValCredDis + " convenio=" + VariablesCaja.usoConvenioCredito);
        	  //uso convenio credito
        	  if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
        	//	  //validar credito del cliente si es que hay linea con matriz
                        if (VariablesCaja.vIndLinea.length() < 
                            1) { //quiere decir que no se validado aun el indicador de linea en matriz
                            VariablesCaja.vIndLinea = 
                                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                                   FarmaConstants.INDICADOR_S);
                        }
        	    
        		  if(VariablesCaja.vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                            String valor = 
                                DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, 
                                                            VariablesConvenio.vCodCliente, 
                                                            "" + 
                                                            VariablesConvenio.vValCredDis, 
                                                            FarmaConstants.INDICADOR_S);
                            log.debug("credito disponible que tendria despues del pedido : " + 
                                      valor);
                            double vValCredDisponible = 
                                FarmaUtility.getDecimalNumber(valor);
                            if (vValCredDisponible < 
                                0) { //quiere decir que no tiene saldo suficiente
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(this, 
                                                         "Cliente no tiene saldo suficiente.\nSe excede en S/." + 
                                                         vValCredDisponible + 
                                                         " soles !", 
                                                         tblFormasPago);
                                return;
                            } else { //quiere decir que tiene saldo suficiente
                                //actualiza consumo del cliente en matriz
                                DBConvenio.actualizaConsumoClienteEnMatriz(VariablesConvenio.vCodConvenio, 
                                                                           VariablesConvenio.vCodCliente, 
                                                                           "" + 
                                                                           VariablesConvenio.vValCredDis, 
                                                                           FarmaConstants.INDICADOR_N, 
                                                                           VariablesCaja.vNumPedVta, 
                                                                           FarmaVariables.vIdUsu);
                                //JMIRANDA 25/08/2009
                                //En el Metodo ActualizaConsumoClienteEnMatriz se inserta los Datos del consumo
                                //del convenio en la tabla CON_REG_VENTA


                                VariablesCaja.vIndCommitRemota = 
                                        true; //indica que debera hacer commit remotamente si to_do el proceso es exitoso
                                //actualizar credito disponible del cliente en local * / ---/-/-/
                                DBConvenio.actualizarCreditoDisp(VariablesConvenio.vCodConvenio, 
                                                                 VariablesConvenio.vCodCliente, 
                                                                 VariablesCaja.vNumPedVta, 
                                                                 vValCredDisponible);

                            }
        		  }else{//si no hay linea con matriz
        			  FarmaUtility.liberarTransaccion();
        			  log.error("jcallo:no hay conexion a matriz, para validar y actualizar pedido por convenio");
    				  FarmaUtility.showMessage(this, 
                              "Error: En este momento no hay linea con matriz.\nSi el problema persiste comunicarse con el operador de sistema !",
                              tblFormasPago);
    				  return;
        		  }
        	  }
                  */
              
              //////////////////
                    String vIndLinea = 
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                       FarmaConstants.INDICADOR_S);
                    boolean indExisteConv = false;
                    boolean indMontoValido = false;

                    //JMIRANDA 23.06.2010
                    //NUEVO METODO DE CONVENIO
                    if (vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        log.debug("Existe conexion a Matriz");
                        String valor = 
                            DBConvenio.validaCreditoCli(VariablesConvenio.vCodConvenio, 
                                                        VariablesConvenio.vCodCliente, 
                                                        "" + 
                                                        VariablesConvenio.vValCredDis, 
                                                        FarmaConstants.INDICADOR_S);
                        log.debug("credito disponible que tendria despues del pedido : " + 
                                  valor);
                        double vValCredDisponible = 
                            FarmaUtility.getDecimalNumber(valor);

                        //Paso 1 valida que exista el convenio
                        indExisteConv = 
                                UtilityConvenio.getIndClienteConvActivo(this, 
                                                                        tblFormasPago, 
                                                                        VariablesConvenio.vCodConvenio, 
                                                                        VariablesConvenio.vNumDocIdent,
                                                                        VariablesConvenio.vCodCliente);
                        log.error("PASO 1. FORMA PAGO.  indExisteConv: " + 
                                  indExisteConv);
                        if (indExisteConv) {
                            //Paso 2 validar el monto disponible
                            indMontoValido = 
                                    UtilityConvenio.getIndValidaMontoConvenio(this, 
                                                                              tblFormasPago, 
                                                                              VariablesConvenio.vCodConvenio, 
                                                                              VariablesConvenio.vNumDocIdent, 
                                                                              FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago),
                                                                                VariablesConvenio.vCodCliente
                                                                              );
                            log.error("PASO 2. FORMA PAGO indMontoValido: " + 
                                      indMontoValido);
                            if (indMontoValido) {
                                log.error("eNTRO. FORMA PAGO indMontoValido: " + 
                                          indMontoValido);
                                //El convenio est� activo y el monto a usar es correcto  


                                //actualiza consumo del cliente en matriz
                                /*
                                DBConvenio.actualizaConsumoClienteEnMatriz(VariablesConvenio.vCodConvenio, 
                                                                           VariablesConvenio.vCodCliente, 
                                                                           "" + 
                                                                           VariablesConvenio.vValCredDis, 
                                                                           FarmaConstants.INDICADOR_N, 
                                                                           VariablesCaja.vNumPedVta, 
                                                                           FarmaVariables.vIdUsu);
                                */
                                DBConvenio.actualizaConsumoClienteEnMatriz_v2(VariablesConvenio.vCodConvenio, 
                                                                           VariablesConvenio.vCodCliente, 
                                                                           "" +VariablesConvenio.vValCredDis, 
                                                                           FarmaConstants.INDICADOR_N, 
                                                                           VariablesCaja.vNumPedVta, 
                                                                           FarmaVariables.vIdUsu,
                                                                           VariablesConvenio.vNumDocIdent);
                                //JMIRANDA 25/08/2009
                                //En el Metodo ActualizaConsumoClienteEnMatriz se inserta los Datos del consumo
                                //del convenio en la tabla CON_REG_VENTA


                                VariablesCaja.vIndCommitRemota = true;
                                //indica que debera hacer commit remotamente si to_do el proceso es exitoso
                                //actualizar credito disponible del cliente en local*/
                                DBConvenio.actualizarCreditoDisp(VariablesConvenio.vCodConvenio, 
                                                                 VariablesConvenio.vCodCliente, 
                                                                 VariablesCaja.vNumPedVta, 
                                                                 vValCredDisponible);
                                
                                indUpdateEnConvenio = true;
                                
                            }
                        }

                    } else { //si no hay linea con matriz
                        FarmaUtility.liberarTransaccion();
                        log.error("jcallo:no hay conexion a matriz, para validar y actualizar pedido por convenio");
                        FarmaUtility.showMessage(this, 
                                                 "Error: En este momento no hay linea con matriz.\nSi el problema persiste comunicarse con el operador de sistema !", 
                                                 tblFormasPago);
                        return;
                    }

                    //////////////////
              
              tmpT2 = System.currentTimeMillis();      
              log.debug("t7 Validaciones de convenio: "+(tmpT2 - tmpT1)+" milisegundos");        
          }
            tmpT1 = System.currentTimeMillis();      
          log.debug("jcallo: VariablesCaja.vIndPedidoConProdVirtual="+VariablesCaja.vIndPedidoConProdVirtual);
          //obtener flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          indCommitBefore =  getIndCommitAntesRecargar();
            tmpT2 = System.currentTimeMillis();      
            log.debug("t8 Obtiene Indicador de Commit antes de Recargar: "+(tmpT2 - tmpT1)+" milisegundos");        
     
            if(dniClienteFidelizado.length()>0){//quiere decir que es pedido de venta fidelizado
                    tmpT1 = System.currentTimeMillis();      
                    boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada(
                                                                                            FarmaConstants.INDICADOR_N,//NO HAY LINEA
                                                                                            VariablesCaja.vNumPedVta,
                                                                                            this,
                                                                                            ConstantsCaja.ACCION_COBRO,
                                                                                            txtNroPedido,
                                                                                            FarmaConstants.INDICADOR_N
                                                                                           );
                    log.debug("jcallo: pRspCampanaAcumulad="+pRspCampanaAcumulad);
                    if (!pRspCampanaAcumulad){
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                   FarmaConstants.INDICADOR_S);
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this, 
                                                    "El pedido no puede ser cobrado. \n" +
                                                    "Presenta un producto regalo de campa�a que no se puede validar con Matriz. \n" +
                                                    "Int�ntelo nuevamente de lo contrario anule el pedido y gen�relo nuevamente." + 
                                                    "\n" +
                                                    "Gracias.",
                                                    tblFormasPago);
                            return; //se olvido dubilluz
                    }
            
            
            tmpT2 = System.currentTimeMillis();
            log.debug("t8 Obtiene Indicador de Commit antes de Recargar: "+(tmpT2 - tmpT1)+" milisegundos");
            
            }//FIN DE LOGICA DE CAMPABIAAS ACUMULADAS            
	          
          //flag de IND PARA SABER SI IMPRIMIRA ANTES DE LA RECARGA VIRTUAL
          if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
	        log.debug("indCommitBefore : S ");
	        log.debug("###VariablesCaja.vIndPedidoConProdVirtual  "+VariablesCaja.vIndPedidoConProdVirtual);
                
	        if (VariablesCaja.vIndPedidoConProdVirtual) 
                {
	            tmpT1 = System.currentTimeMillis();     	
	            	//viendo si tiene indicador linea matriz 
	            	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
		    	     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
	            	}
	            	
	            if (VariablesCaja.vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
	                VariablesCaja.vIndEnvioRecargar = true;//indicador de que se mando a recargar
	                //LLEIVA 13-Jun-2013 - Se a�ade la validaci�n si existe algun error al enviar la informaci�n al servidor central
                        ejecutaRecargaVirtual();
                        /*{   FarmaUtility.liberarTransaccion();
                            //FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                            //                                       FarmaConstants.INDICADOR_S);
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this, 
                                                    "El pedido no puede ser cobrado. \n" +
                                                    "Int�ntelo nuevamente de lo contrario anule el pedido y gen�relo nuevamente." + 
                                                    "\n" +
                                                    "Gracias.",
                                                    tblFormasPago);
                        }*/
                        //FIN LLEIVA
	            } else {
	                FarmaUtility.liberarTransaccion();
	                VariablesCaja.vIndPedidoCobrado = false;
	                FarmaUtility.showMessage(this, 
	                                         "El pedido no puede ser cobrado. \n" +	
	                                         "No hay linea com matriz.\n" +
	                                         "Int�ntelo nuevamente.", 
	                                         tblFormasPago);
	                return; //se olvido dubilluz
	            }
	            tmpT2 = System.currentTimeMillis();      
	            log.debug("t9 Ejecuta la Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
	        }
	        //evalua indicador de impresion por error
	        tmpT1 = System.currentTimeMillis();      	        
	        String vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
	        log.debug("vIndImpre :"+vIndImpre);
                tmpT2 = System.currentTimeMillis();      
                log.debug("t10 Obtiene Indicador de Impresion de Recarga: "+(tmpT2 - tmpT1)+" milisegundos");
                
                    if (!vIndImpre.equals("N")){
                    
                        if(VariablesCaja.listCuponesUsadosPedido.size() > 0){
                            log.debug("antes de actualizar los cupones en matriz");
                            //viendo si tiene indicador linea matriz 
            	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
            	        	tmpT1 = System.currentTimeMillis();      
            			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            	        	    tmpT2 = System.currentTimeMillis();      
            	        	    log.debug("t11 Obtiene IND_LINEA para actualizar cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");
            	        	}
                            /*
                            if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){
                                tmpT1 = System.currentTimeMillis();      
                                actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta, 
                                                         VariablesCaja.listCuponesUsadosPedido,
                                                         VariablesCaja.vIndLinea);
                                tmpT2 = System.currentTimeMillis();      
                                log.debug("t12 Actualiza Cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");
                            }
                            else{
                                log.debug("No actualiza cupones en Matriz");
                            }
                            */
                            tmpT1 = System.currentTimeMillis();      
                            actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta, 
                                                     VariablesCaja.listCuponesUsadosPedido,
                                                     VariablesCaja.vIndLinea);
                            tmpT2 = System.currentTimeMillis();      
                            log.debug("t12 Actualiza Cupones en Local: "+(tmpT2 - tmpT1)+" milisegundos dubilluz 23.04.2010");
                                
                        }
                        
                        /*** JCALLO ****/
                        if( dniClienteFidelizado.length() > 0 ){//quiere decir que es pedido fidelizado
                        	ArrayList listaCampAutomaticasPedido = new ArrayList();
                                tmpT1 = System.currentTimeMillis();      
                        	listaCampAutomaticasPedido = getCampAutomaticasPedido(VariablesCaja.vNumPedVta);//obtiene todas las campa�as automaticas usados en el pedido
                        	tmpT2 = System.currentTimeMillis();                                      
                        	log.debug("t13 Lista campa�as Acumuladas inscritas: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                                tmpT1 = System.currentTimeMillis();                                      
                        	for(int i=0;i< listaCampAutomaticasPedido.size();i++){
                        		String cod_camp_limit = listaCampAutomaticasPedido.get(i).toString().trim();
                        		//CAMBIAR EL A POR L
                        		if(cod_camp_limit.indexOf("L")>-1){//quiere decir que es una campa�a limitante
                        			log.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        			DBCaja.registrarUsoCampLimitLocal(cod_camp_limit, dniClienteFidelizado);
                        		}
                        	}
                                
                                tmpT2 = System.currentTimeMillis();                                      
                                log.debug("t14 Fin de proceso de Campa�as Acumuladas: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                        }
                        /*** FIN JCALLO ***/
                        
                        /*
                         * Validacion de Fecha actual del sistema contra
                         * la fecha del cajero que cobrara
                         * Se a�adio para validar pedido Cobrado 
                         * despues de una fecha establecida al inicio
                         * dubilluz 04.03.2009
                         **/
                        log.debug("antes de validar");
                        if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
                                FarmaUtility.liberarTransaccion();
                                return;
                        }
                        tmpFin = System.currentTimeMillis();
                        log.debug("t15 Fin PROCESO COBRO antes del Commit a�n falta Imprimir: "+(tmpFin - tmpIni)+" milisegundos");                        
                  //      FarmaUtility.aceptarTransaccion();//haciendo commit en el pedido como cobrado
                       
                    /*    log.debug("despues de supuestamente actualizar cupones en matriz");
                        log.info("VariablesCaja.vIndCommitRemota:"+VariablesCaja.vIndCommitRemota);
                        if(VariablesCaja.vIndCommitRemota){
                            log.debug("entroa  hacer commmit remota");                            
                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);                            
                            log.debug("despues de hacer commmit remota");
                        }*/
     /////********
     //JMIRANDA 24.06.2010
     //VALIDAR SI HIZO UPDATE DAR COMMIT
     if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
          &&                              
         FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
             String vIndLineaMat = 
                 FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                FarmaConstants.INDICADOR_S);
         
           if(((indUpdateEnConvenio ) && 
              VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
              vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
                           FarmaUtility.aceptarTransaccion();
                       if(VariablesCaja.vIndCommitRemota){
                                            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                       }
            }else {
                  //DUBILLUZ 25.06.2010
                  VariablesCaja.vIndPedidoCobrado = false;
                  FarmaUtility.liberarTransaccion();
                  FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);                 
                  return;                             
                  }
         }else{
                    FarmaUtility.aceptarTransaccion();
                     if(VariablesCaja.vIndCommitRemota){
                     FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                     }
                  }                                    

     /////********                   
                        
                        if(VariablesCaja.vIndPedidoConProdVirtual) {
                            tmpT1 = System.currentTimeMillis();
                            log.debug("indicador de prodcutos virtual");
                            evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);//MUESTRA MENSAJE SI SE RECARGO O NO
                            tmpT2 = System.currentTimeMillis();
                            log.debug("t16 Evalua Mensaje de Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
                        }
                        
                        log.debug("***VariablesCaja.vIndPedidoConProdVirtual***:"+VariablesCaja.vIndPedidoConProdVirtual);
                        
                        if(!VariablesCaja.vIndPedidoConProdVirtual){
                            log.debug("mostrando mensaje");
                            //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito", txtNroPedido);
                            log.info("Pedido Cobrado con Exito");
                            log.debug("mostrando mensaje");
                        }
                        log.debug("...VariablesCaja.vIndPedidoConProdVirtual:"+VariablesCaja.vIndPedidoConProdVirtual);
                        /** DESPUES DEL MENSAJE DE COBRADO CON EXITO***/
		                //HASTA AQUI EL PEDIDO SE ENCUENTRA EN ESTADO S
		                tmpT1 = System.currentTimeMillis();
		                UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
		                //obtiene informacion del vendedor
		                UtilityCaja.obtieneInfoVendedor();
		                //proceso de impresion de comprobante del pedido
		                //JCALLO ...corregir to do este metodo, se agrego el indicador de linea con matriz
		                //pIndLineaMatriz
		                VariablesCaja.vIndLineaMatriz = VariablesCaja.vIndLinea;
                                tmpT2 = System.currentTimeMillis();
                                log.debug("t17 Obtiene informacion de cajero y de vendedor: "+(tmpT2 - tmpT1)+" milisegundos");
                                
                                
                                //tmpT1 = System.currentTimeMillis();
                                //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
                                
                                try{
                                    DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                                    FarmaUtility.aceptarTransaccion();
                                    log.debug("Grabo el tiempo de fin de cobro");
                                    
                                }
                                catch(SQLException sql){
                                    log.error("",sql);
                                    FarmaUtility.liberarTransaccion();
                                    log.debug("Error al grabar el tiempo de fin de cobro");
                                }       
                                //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
		                UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                                
		                if(VariablesCaja.vIndPedidoConProdVirtual) {
		                	//evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
		                	FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
		                }
		                
                    }else{//si el indicador de impresion es N
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Error en Aplicacion al cobrar el Pedido.",tblFormasPago);
                    }
	       // }
          } else {//quiere decir que el indicador de IMPRIMIR ANTES DE RECARGAR ES DIFERENTE DE S
		if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                    log.debug("indCommitBefore : N ");
                    // Se mantiene la logica anterior , cobra realiza la recarga
                    // y solo si se obtuvo exito colocara el codigo de respuesta.
                    if (VariablesCaja.vIndPedidoConProdVirtual) {
                    	
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}
        	        	
                        if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            tmpT1 = System.currentTimeMillis();
                            ejecutaRecargaVirtual();
                            tmpT2 = System.currentTimeMillis();
                            log.debug("t18 Proceso de Recarga Virtual: "+(tmpT2 - tmpT1)+" milisegundos");                            
                        } else {
                            FarmaUtility.liberarTransaccion();
                            VariablesCaja.vIndPedidoCobrado = false;
                            FarmaUtility.showMessage(this,  "El pedido no puede ser cobrado. \n" +
                                                            "No hay linea com matriz.\n" +
                                                            "Int�ntelo nuevamente.", 
                                        		    tblFormasPago);
                        }
                    }                        
                    
                    
                	if(VariablesCaja.listCuponesUsadosPedido.size()>0){//solo si se uso algun cupon
                    	//viendo si tiene indicador linea matriz 
        	        	if (VariablesCaja.vIndLinea.length()<1){//quiere decir que no se validado aun el indicador de linea en matriz
        			     	VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        	        	}
                	    tmpT1 = System.currentTimeMillis();
                        actualizaCuponesEnMatriz(VariablesCaja.vNumPedVta,
                                                VariablesCaja.listCuponesUsadosPedido,
                                                VariablesCaja.vIndLinea);
                	    tmpT2 = System.currentTimeMillis();
                	    log.debug("t19 Actualiza Cupones en Matriz: "+(tmpT2 - tmpT1)+" milisegundos");                            
                	}
                    /*
                     * Validacion de Fecha actual del sistema contra
                     * la fecha del cajero que cobrara
                     * Se a�adio para validar pedido Cobrado 
                     * despues de una fecha establecida al inicio
                     * dubilluz 04.03.2009
                     **/
                    //log.debug("antes de validar");
                    if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
                            FarmaUtility.liberarTransaccion();
                            return;
                    }
                    tmpFin = System.currentTimeMillis();
                    log.debug("t19 Fin de Proceso de Cobro antes de commit: "+(tmpFin - tmpIni)+" milisegundos");
                    //FarmaUtility.aceptarTransaccion();
                    //**
                    //JMIRANDA 24.06.2010
                    //VALIDAR SI HIZO UPDATE DAR COMMIT
                    if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
                         &&                              
                        FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
                            String vIndLineaMat = 
                                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                               FarmaConstants.INDICADOR_S);
                        
                          if(((indUpdateEnConvenio ) && 
                             VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
                             vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
                                          FarmaUtility.aceptarTransaccion();
                                      if(VariablesCaja.vIndCommitRemota){
                                                           FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                      }
                           }else {
                                     //DUBILLUZ 25.06.2010
                                     VariablesCaja.vIndPedidoCobrado = false;
                              
                                 FarmaUtility.liberarTransaccion();
                                 FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                 FarmaUtility.showMessage(this,"Error al actualizar el uso del Convenio.\nComun�quese con el Operador de Sistemas.",null);
                                 return;                             
                                 }
                        }else{
                                   FarmaUtility.aceptarTransaccion();
                                    if(VariablesCaja.vIndCommitRemota){
                                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                                    }
                                 }                                   

                    //**
                    //if(VariablesCaja.vIndCommitRemota)
                        //FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
                        
                    if(VariablesCaja.vIndPedidoConProdVirtual) {
                        tmpT1 = System.currentTimeMillis();
                        evaluaMsjVentaVirtualGenerado(VariablesCaja.vTipoProdVirtual);
                        tmpT2 = System.currentTimeMillis();
                        log.debug("t20 Evalua Mensaje Virtual: "+(tmpT2 - tmpT1)+" milisegundos");
                    }
                
                    //FarmaUtility.showMessage(this, "Pedido Cobrado con Exito",txtNroPedido);
                    log.info("Pedido Cobrado con Exito");
                    
                    tmpT1 = System.currentTimeMillis();
                    UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                    UtilityCaja.obtieneInfoVendedor();
                    tmpT2 = System.currentTimeMillis();
                    //log.debug("t21 Obtiene info. cajero y vendedor: "+(tmpT2 - tmpT1)+" milisegundos");
                    
                    
                    tmpT1 = System.currentTimeMillis();
                    //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
                    
                    //LLEIVA 28-Jun-2013 - Si se cobro correctamente, se actualizan los datos en FASA
                    //if(VariablesRecetario.strAccion!=null && VariablesRecetario.strAccion!="")
                    //    facadeRecetario.enviaTramaActualizaRecetFASA(VariablesCaja.vNumPedVta,
                    //                                                 VariablesRecetario.strAccion);
                    //LLEIVA 13-Jun-2013 - Si se grabo correctamente el cobro, eliminar la variable de recetario magistral
                    //VariablesRecetario.strCodigoRecetario = "";
                                                          
                    try{
                        DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
                        FarmaUtility.aceptarTransaccion();
                        //log.debug("Grabo el tiempo de fin de cobro");
                        
                    }
                    catch(SQLException sql){
                        log.error("",sql);
                        FarmaUtility.liberarTransaccion();
                        log.debug("Error al grabar el tiempo de fin de cobro");
                    }       
                    //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
                    UtilityCaja.procesoImpresionComprobante(this, txtNroPedido); 
                    tmpT2 = System.currentTimeMillis();
                    //log.debug("t22 Proceso de Impresion de Comprobante: "+(tmpT2 - tmpT1)+" milisegundos");
                
                    if(VariablesCaja.vIndPedidoConProdVirtual)  {
                        FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
                    }
                    
                    
                        
                }
            } 
          //FIN DE QUE SE HAYA COBRADO EXITOSAMENTE
        } else if (resultado.trim().equalsIgnoreCase("ERROR")) {
          FarmaUtility.liberarTransaccion();
          VariablesCaja.vIndPedidoCobrado = false;
          FarmaUtility.showMessage(this, 
                                   "El pedido no puede ser cobrado. \n" +
                                   "Los totales de formas de pago y cabecera no coinciden. \n" +
                                   "Comun�quese con el Operador de Sistemas inmediatamente." + 
                                   ". \n" +
                                   "NO CIERRE LA VENTANA.", 
                                   tblFormasPago);
        }

      } catch (SQLException sql) {//error de base de datos al cobrar
        
    	FarmaUtility.liberarTransaccion();
        //log.error("",sql);
        log.error(null,sql);
        String pMensaje = sql.getMessage();
        
        int nIsSecCajaNull = pMensaje.indexOf("CHECK_SEC_MOV_CAJA");
        
        log.debug("nIsSecCajaNull:"+nIsSecCajaNull);
        if(nIsSecCajaNull>0){
            FarmaUtility.showMessage(this, "No se pudo cobrar el pedido.\nInt�ntelo nuevamente", tblFormasPago);            
        }
        else{

            if ( VariablesCaja.vIndEnvioRecargar ){
                log.error("ERROR de BASE DE DATOS AL MOMENTO DE COBRAR UN RECARGAR VIRTUAL...PERO IGUAL SE MANDO A RECARGAR!");            
                try{
                    FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                  FarmaVariables.vCodLocal,
                                                  //"jcallo",
                                                  VariablesPtoVenta.vDestEmailErrorCobro, //JMIRANDA 04/08/09
                                                  "Error Recarga Virtual, error de base datos",
                                                  "Error de Recarga Virtual",
                                                  "Error al realizar recarga virtual al numero : "+VariablesCaja.vNumeroCelular,
                                                  "IP PC: " + FarmaVariables.vIpPc + "<br>"+ //JMIRANDA 30/07/09
                                                  //"dubilluz"
                                                  "");
                }catch(Exception e){
                    log.error("ERROR AL TRATAR de enviar correo de alerta de recarga virtual");
                }
            }
  /*          VariablesCaja.vIndPedidoCobrado = false;
            FarmaUtility.showMessage(this, "Error en BD al cobrar el Pedido.\n" + sql.getMessage(), tblFormasPago);
*/
///******
                    log.error("",sql);
                    if(sql.getErrorCode()>20000)
                    {
                      VariablesCaja.vIndPedidoCobrado = false;  
                          /*
                          FarmaUtility.showMessage(this,""+VariablesConvenio.vCodCliente+ "\n"+
                                                          ""+VariablesConvenio.vCodConvenio+ "\n"+
                                                          ""+VariablesConvenio.vNumDocIdent+ "\n" ,tblFormasPago);
                            */
                      FarmaUtility.showMessage(this,sql.getMessage().substring(10,sql.getMessage().indexOf("ORA-06512")),null);  
                      } else{
                          VariablesCaja.vIndPedidoCobrado = false;
                          FarmaUtility.showMessage(this, "Error en BD al cobrar el Pedido.\n" + sql.getMessage(), tblFormasPago);
                      }
///******
                        
        }
      } catch (Exception ex) {//error inesperado
    	  log.error("",ex);//error inesperado
    	  if(indCommitBefore.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
    		  if(VariablesCaja.vIndPedidoConProdVirtual) {
    			  //evalua indicador de impresion por error
    			  String vIndImpre="N";
    			  try {
    				  vIndImpre = DBCaja.obtieneIndImpresionRecarga(VariablesVirtual.vCodigoRespuesta);
    			  }catch(Exception e){
    				  log.error("jcallo: no pudo obtener el indicador de impresion de recarga");
    				  vIndImpre = "N";
    			  }
    			  log.debug("vIndImpre :"+vIndImpre);
    			  
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
    			      if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
    			              FarmaUtility.liberarTransaccion();
    			              return;
    			      }
                              
    			      tmpFin = System.currentTimeMillis();
    			      log.debug("t23 Finaliza el Proceso de Cobro antes de Commit: "+(tmpFin - tmpIni)+" milisegundos");
                              
    				  //FarmaUtility.aceptarTransaccion();
    				  //**
    				  //JMIRANDA 24.06.2010
    				  //VALIDAR SI HIZO UPDATE DAR COMMIT
    				  if ( VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
    				       &&                              
    				      FarmaUtility.getDecimalNumber(VariablesConvenio.vValCoPago) != 0){  
    				          String vIndLineaMat = 
    				              FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
    				                                             FarmaConstants.INDICADOR_S);
    				      
    				        if(((indUpdateEnConvenio ) && 
    				           VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) &&
    				           vIndLineaMat.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ){                   
    				                        FarmaUtility.aceptarTransaccion();
    				                    if(VariablesCaja.vIndCommitRemota){
    				                                         FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				                    }
    				         }else {
    				                   //DUBILLUZ 25.06.2010
    				                   VariablesCaja.vIndPedidoCobrado = false;
                                            
    				               FarmaUtility.liberarTransaccion();
    				               FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
    				               FarmaUtility.showMessage(this,"Error al actualizar el uso del Convenio.\nComun�quese con el Operador de Sistemas.",null);
    				               return;                             
    				               }
    				      }else{
    				                 FarmaUtility.aceptarTransaccion();
    				                  if(VariablesCaja.vIndCommitRemota){
    				                  FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
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
    			      log.debug("t24 Obtiene Info. de Vendedor y Cajero: "+(tmpT2 - tmpT1)+" milisegundos");
                              
    			      tmpT1 = System.currentTimeMillis();
    			      //JCHAVEZ 09.07.2009.sn graba el tiempo dei fin de cobro
    			   
    			      try{
    			          DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"F");
    			          FarmaUtility.aceptarTransaccion();
    			          log.debug("Grabo el tiempo de fin de cobro");
    			         
    			      }
    			      catch(SQLException sql){
    			          log.error("",sql);
    			          FarmaUtility.liberarTransaccion();
    			          log.debug("Error al grabar el tiempo de fin de cobro");
    			      }       
    			      //JCHAVEZ 09.07.2009.en graba el tiempo de fin de cobro
		              UtilityCaja.procesoImpresionComprobante(this, txtNroPedido);
    			      tmpT2 = System.currentTimeMillis();
    			      log.debug("t25 Finaliza Proceso de Impresion de Comprobantes: "+(tmpT2 - tmpT1)+" milisegundos");
                              
		              log.error("jcallo:fin proceso de impresion de comprobante");
		              log.error("FIN imprimir si se trata de un producto virtual a pesar del error");
		
			           
		              FarmaUtility.showMessage(this, 
		                                       "Error en Aplicacion al cobrar el Pedido.\n" + 
		                                       ex.getMessage(),
		                                       tblFormasPago);
		        
		              FarmaUtility.showMessage(this, "Comprobantes Impresos con Exito",tblFormasPago);
    			  }else{
    				  FarmaUtility.liberarTransaccion();
    				  if(VariablesCaja.vIndCommitRemota){
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
    	 log.debug("VariablesCaja.vIndLinea"+VariablesCaja.vIndLinea);
         log.info("cerrando conexion remota sea matriz, adm, delivery, vta institucional, etc");
         FarmaConnectionRemoto.closeConnection();//dentro metodo solo cierra si estuvo abierta alguna conexion
         //verifica si existen pedidos pendientes de anulacion despues de N minutos
         tmpT1 = System.currentTimeMillis();
         UtilityCaja.verificaPedidosPendientes(this);
          tmpT2 = System.currentTimeMillis();
          log.debug("t26 Verifica Pedidos Pendientes Anular: "+(tmpT2 - tmpT1)+" milisegundos");
      }
      
      tmpFin = System.currentTimeMillis();
      log.debug("t27 Fin de Todo el Proceso de Cobro: "+(tmpFin - tmpIni)+" milisegundos");
      
      cerrarVentana(false);
  }

  private void colocaVueltoDetallePago(String pVuelto) {
    if (tblDetallePago.getRowCount() <= 0){
      return;
    }
    boolean existeSoles = false;
    boolean existeDolares = false;
    int filaSoles = 0;
    int filaDolares = 0;
    for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
      if ( ( (String)tblDetallePago.getValueAt(i, 0) ).trim()
    		  .equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
        existeSoles = true;
        filaSoles = i;
        break;
      } else if (((String) tblDetallePago.getValueAt(i, 0)).trim()
    		  .equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES)) {
        existeDolares = true;
        filaDolares = i;
      }
    }
    if (existeSoles){
      tblDetallePago.setValueAt(pVuelto, filaSoles, 7);
    } else if (existeDolares && !existeSoles){
      tblDetallePago.setValueAt(pVuelto, filaDolares, 7);
    }
    tblDetallePago.repaint();
  }

  /**
   * descripcion de las formas de pago
   * para la impresion
   */
  private void formasPagoImpresion()
  {
	  //varificar que haya al menos una forma de pago declarado
	  if (tblDetallePago.getRowCount() <= 0) {
		  VariablesCaja.vFormasPagoImpresion = "";
		  return;
	  }
	  //obtiene la descripcion de las formas de pago para la impresion
      for (int i = 0; i < tblDetallePago.getRowCount(); i++) {
    	  if (i == 0) {
    		  VariablesCaja.vFormasPagoImpresion = ((String) tblDetallePago.getValueAt(i, 1)).trim();
    	  } else {
    		  VariablesCaja.vFormasPagoImpresion += ", " + ((String) tblDetallePago.getValueAt(i, 1)).trim();
    	  }
      }
      
      String codFormaPagoDolares = getCodFormaPagoDolares();
      String codFormaPago = "";
      if (codFormaPagoDolares.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
    	  log.debug("La Forma de Pago Dolares esta Inactiva");
      } else {
	      for (int i = 0; i < tblDetallePago.getRowCount(); i++)
	      {
	        codFormaPago = ((String) tblDetallePago.getValueAt(i, 0)).trim();
	        if (codFormaPagoDolares.equalsIgnoreCase(codFormaPago))
	          VariablesCaja.vFormasPagoImpresion = 
	              VariablesCaja.vFormasPagoImpresion + "  Tipo Cambio:  " + 
	              VariablesCaja.vValTipoCambioPedido;
	      }
      }
  }

  private void actualizaClientePedido(String pNumPedVta, 
                                      String pCodCliLocal) throws SQLException {
    DBCaja.actualizaClientePedido(pNumPedVta, pCodCliLocal, VariablesModuloVentas.vNom_Cli_Ped, VariablesModuloVentas.vDir_Cli_Ped, VariablesModuloVentas.vRuc_Cli_Ped);
  }

  
  private void procesaPedidoVirtual() throws Exception
  { //ERIOS 30.05.2013 Envia el pedido de preparado hacia el sistema Recetario Magistral
      //ERIOS 16.07.2013 Implementacion de recargas FarmaSix
    obtieneInfoPedidoVirtual();
    if (VariablesVirtual.vArrayList_InfoProdVirtual.size() != 1)
    {
      throw new Exception("Error al validar info del pedido virtual");
    }
    colocaInfoPedidoVirtual();
    if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA) ||
       VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA)   )
    {   
        if(VariablesPtoVenta.vIndFarmaSix.equals(FarmaConstants.INDICADOR_S) ){ 
            
            //GFonseca 14/08/2013. Se a�ade logica para realizar recargas virtuales con el SIX
            String codProd = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 0);
            String monto = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
            String telefono = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);    
            String terminal = VariablesCaja.vNumPedVta.substring(2);
            String comercio = ConstantsRecaudacion.ID_RECARGAS+""+facadeRecaudacion.getCodLocalMigra().substring(1);//FarmaVariables.vDescCortaLocal;
            String ubicacion = FarmaVariables.vDescCortaDirLocal;            
            ArrayList rptSix = null;
            Long codTrssc = null;            
            boolean bRpt;
            boolean bMsj;
            String strResponseCode = "";
            String strCodAutorizacion = "";
            String strConcentrador="";

            if(ConstantsModuloVenta.TARJ_RECARGA_MOVISTAR_VIRTUAL.equals(codProd)){
                
                codTrssc = facadeCaja.registrarTrsscRecarga(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                               ConstantsRecaudacion.TRNS_RECARGA, ConstantsRecaudacion.TIPO_REC_MOVISTAR, monto, terminal, 
                                                              comercio, ubicacion, telefono, VariablesCaja.vNumPedVta,
                                                              FarmaVariables.vIdUsu); 
                
                rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_RECARGA_SIX, 
                                                         ConstantsRecaudacion.RCD_PAGO_SIX_RECARGA_VIRTUAL_MOVISTAR, 
                                                         codTrssc );
                strConcentrador = ConstantsRecaudacion.COD_CONCENTRADOR_MOVISTAR;
                
            }else if (ConstantsModuloVenta.TARJ_RECARGA_CLARO_VIRTUAL.equals(codProd)){
                
                codTrssc = facadeCaja.registrarTrsscRecarga(ConstantsRecaudacion.MSJ_SIX_PETICION_TRSSC_200, ConstantsRecaudacion.ESTADO_SIX_PENDIENTE,
                                               ConstantsRecaudacion.TRNS_RECARGA, ConstantsRecaudacion.TIPO_REC_CLARO, monto, terminal, 
                                                              comercio, ubicacion, telefono, VariablesCaja.vNumPedVta,
                                                              FarmaVariables.vIdUsu);
                
                rptSix = facadeRecaudacion.obtenerRespuestaSix(ConstantsRecaudacion.RCD_MODO_RECARGA_SIX, 
                                                         ConstantsRecaudacion.RCD_PAGO_SIX_RECARGA_VIRTUAL_CLARO, 
                                                         codTrssc );
                strConcentrador = ConstantsRecaudacion.COD_CONCENTRADOR_CLARO;
            
            }                   
            
            bRpt = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPUESTA);
            bMsj = (Boolean) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MSJ);
            strResponseCode = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_RESPONSE_CODE);
            //strMontoPagar = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_MONTO_PAGAR);
            //strCodAuditoria = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUDITORIA); 
            strCodAutorizacion = (String) rptSix.get(ConstantsRecaudacion.RCD_PAGO_COD_AUTORIZ);// SE GUARDA EN LA CABECERA DE RECAUDACION PARA COMPRA Y VENTA CMR, EN RECARGAS SE GUARDA EN ADM 
            
            UtilityRecaudacion utilityRecaudacion = new UtilityRecaudacion();
            //utilityRecaudacion.initMensajesVentana(this, null, null);
            DBCaja.grabaRespuestaRecargaVirtual(strResponseCode, VariablesCaja.vNumPedVta); 
            
            //ERIOS 07.10.2013 Si existe error, no continua con el proceso.
            //if( bMsj ){
                //FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO , null);
                //throw new Exception(ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO);
            //}           
            
            VariablesVirtual.vCodigoAprobacion = strCodAutorizacion;
            VariablesVirtual.vNumTrace = codTrssc.toString();
            UtilityCaja.actualizaInfoPedidoVirtual(this);
            
            if( bRpt && ConstantsRecaudacion.COD_SOLICITUD_EXITOSA.equals(strResponseCode) ){     
                //INICIO CONCILIACION
                String PCL_COD_ID_CONCENTRADOR = strConcentrador; //052 Claro y 055 Movistar
                String PCL_NUMERO_TELEFONO = telefono;
                String PCL_COD_AUTORIZACION = codTrssc.toString(); //Codigo de Autorizacion
                String PCL_COD_VENDEDOR = FarmaVariables.vNuSecUsu; //Codigo de Vendedor
                String PCL_NUMERO_DOCUMENTO= VariablesCaja.vNumPedVta; //Comprobante
                String PCL_COD_COMERCIO = facadeRecaudacion.getCodLocalMigra(); //Ccodigo de Local
                String PCL_COD_TERMINAL= VariablesCaja.vNumCaja; //Nro de Caja
                String PCL_MONTO_VENTA = monto; //monto recarga
                String PCL_ID_TRANSACCION = strCodAutorizacion; //ID enviado por la Empresa telefonica
                
                String PCL_FECHA_VENTA=ConstantsRecaudacion.FECHA_RCD;
                String PCL_HORA_VENTA=ConstantsRecaudacion.HORA_RCD;
                            
                String vSalida = facadeRecaudacion.setDatosRecargaConciliacion(PCL_COD_ID_CONCENTRADOR, 
                                                                               PCL_NUMERO_TELEFONO, 
                                                                               PCL_COD_AUTORIZACION, 
                                                                               PCL_COD_VENDEDOR, 
                                                                               PCL_FECHA_VENTA, 
                                                                               PCL_HORA_VENTA, 
                                                                               PCL_NUMERO_DOCUMENTO, 
                                                                               PCL_COD_COMERCIO, 
                                                                               PCL_COD_TERMINAL, 
                                                                               PCL_MONTO_VENTA, 
                                                                               PCL_ID_TRANSACCION
                                                                               );
                log.info("Respuesta conciliacion recargas: "+vSalida);
                //FIN CONCILIACION            
            }else {
                if(ConstantsRecaudacion.COD_SERV_INACTIVO.equals(strResponseCode)){// codigo 96 es cuando el servicio del six esta en Down
                FarmaUtility.showMessage(this, ConstantsRecaudacion.RCD_MSJ_CLARO_SERV_INACTIVO ,null); 
                }else{
                    throw new Exception(ConstantsRecaudacion.RCD_PAGO_SIX_MSJ_COBRO_FALLIDO);
                }                
            }      
                                                        
            
        }else{
            try
            {
              UtilityCaja.procesaVentaProductoVirtual(this, txtNroPedido);
            }
            catch (Exception ex)
            {
              throw new Exception("Error al procesar el pedido virtual - \n" + 
                                  ex.getMessage());
        
            }
            /*
             * Se grabara la respuesta obtenida por el proveedor al realizar la
             * recarga virtual
             */
            DBCaja.grabaRespuestaRecargaVirtual(VariablesVirtual.respuestaTXBean.getCodigoRespuesta(),
                                                VariablesCaja.vNumPedVta);
            
            if (!validaCodigoRespuestaTransaccion())
            {
              throw new Exception("Error al realizar la transaccion con el proveedor.\n" + 
                                  VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + 
                                  " - " + 
                                  VariablesVirtual.respuestaTXBean.getDescripcion());
            }
        }
    }else if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_MAGISTRAL) &&
            VariablesPtoVenta.vIndVerReceMagis.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
        HashMap<String,String> hRecetario = new HashMap<String,String>(); 
        
        DBRecetario.getNumeroRecetario(VariablesCaja.vNumPedVta,hRecetario);
        
        String numRecetario = hRecetario.get("NUM_RECETARIO");
        String estRecetario = hRecetario.get("EST_RECETARIO");

        if(!numRecetario.equals("")){
            
            if(estRecetario.equals(ConstantsRecetario.Estado.PENDIENTE.getValor())){
                String tramaRecetario = DBRecetario.getTramaRecetario(numRecetario);
                
                //Envia la trama al sistema de Fasa
                String rptaRecetario = facadeRecetario.enviaTramaRecetario(tramaRecetario);
                
                if(rptaRecetario.equals("OK")){   
                    DBRecetario.actualizaEstadoRecetario(numRecetario,ConstantsRecetario.Estado.ENVIADO);                    
                }else{   
                    //indCommitBefore = "N";
                    log.error("Trama resp: "+rptaRecetario);
                    throw new Exception("Se ha presentado un error al enviar el recetario.\n");
                }
            }else if(estRecetario.equals(ConstantsRecetario.Estado.GUIA.getValor())){
                //Los recetarios que se generan a partir de [G]uias, no se envian.
                DBRecetario.actualizaEstadoRecetario(numRecetario,ConstantsRecetario.Estado.COBRADO);                    
            }
        }else{
            throw new Exception("No se encuentra el numero de Recetario.");
        }
    }
  }

  private void evaluaMsjVentaVirtualGenerado(String pTipoProdVirtual)
  {
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
  public String getCodFormaPagoDolares()
  {
    String codFP = "";
    try
    {
      codFP = DBCaja.getCodFPDolares();
    }
    catch (SQLException ex)
    {
      log.error("",ex);
      FarmaUtility.showMessage(this, "Error al Obtener el codidgo de Forma de Pago Dolares.\n" + 
                               ex.getMessage(), tblFormasPago);
    }
    log.debug("jcallo: codforma de pago dolares "+codFP);
    return codFP;
  }

  private void obtieneInfoPedidoVirtual()
    throws Exception
  {
    try
    {
      DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual, 
                                      VariablesCaja.vNumPedVta);
      log.debug("vArrayList_InfoProdVirtual : " + 
                         VariablesVirtual.vArrayList_InfoProdVirtual);
    }
    catch (SQLException sql)
    {
      log.error("",sql);
      throw new Exception("Error al obtener informacion del pedido virtual - \n" + 
                          sql);
    }
  }

  private void colocaInfoPedidoVirtual()
  {
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


  private void muestraTarjetaVirtualGenerado()
  {
    DlgNumeroTarjetaGenerado dlgNumeroTarjetaGenerado = 
      new DlgNumeroTarjetaGenerado(myParentFrame, "", true);
    dlgNumeroTarjetaGenerado.setVisible(true);
    FarmaVariables.vAceptar = false;
  }
  
  /**
     * Obti
     * @return
     */
  private String getIndCommitAntesRecargar()
  {
    String ind;  
    try
    {
       ind = DBCaja.obtieneIndCommitAntesdeRecargar();
       log.debug("ind Impr Antes de Recargar" +ind);
    }
    catch (SQLException sql)
    {
        ind = "N";
        log.error("",sql);
    }
      
     return ind.trim(); 
  }
         
  private void ejecutaRecargaVirtual() throws Exception{
      procesaPedidoVirtual();
  }
  
  /**
   * Se generan los cupones por pedido luego de ser cobrados 
   * @author JCORTEZ
   * @since 03.07.2008
   * */
  private boolean generarPedidoCupon(String NumPed){
    boolean valor=false;
   try
    {
       DBCaja.generarCuponPedido(NumPed);
       valor=true;
       log.debug("Se generaron los cupones del pedido :" +NumPed);
    }
    catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Se genero un error al generar los cupones\n"+sql.getMessage(),tblFormasPago);
    }
  return valor;
  }
  
  
  
  private void cargarHora(String men){
     try{
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
      log.debug("FECHA HORA------------------------------------------------->"+sysdate+"dentro del proceso"+men);
      log.debug(sysdate);
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy HH:mm:ss");
    }catch(SQLException e)
    {
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
                                         String pIndLinea
                                         ){
       
       ArrayList listCupones = new ArrayList(); 
       listCupones = (ArrayList)pListaCuponesPedido.clone(); 
       String vIndLineaBD = "";
       String vCodCupon  = "";
       String vResActMatriz = "";
       boolean vActCupon = false;
       int COL_COD_CUPON = 0;
       int COL_COD_FECHA_INI = 1;
       int COL_COD_FECHA_FIN = 2;   
       String vEstCuponMatriz = "";
       String vRetorno  = "";
       String vFechIni ="";
       String vFechFin ="";
       String indMultiUso ="";       
       try
       {
           if(listCupones.size()>0)
           { 
               if(listCupones.size()==1){
                   String vValor = "";
                   vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,COL_COD_CUPON);
                   if(vValor.equalsIgnoreCase("NULO")){
                      return;
                   }
               }
                
               //  2. Se verificara si hay linea con matriz
               //--El segundo parametro indica si se cerrara la conexion
               vIndLineaBD = pIndLinea;
               //SE ESTA FORZANDO QUE NO HAYA LINEA
               vIndLineaBD = FarmaConstants.INDICADOR_N;
               if(vIndLineaBD.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                   log.debug("No existe linea se cerrara la conexion ...");
                   FarmaConnectionRemoto.closeConnection();
               }
               
               // 3. SE ACTUALIZA EL CUPON 
               for(int i=0 ; i<listCupones.size() ; i++){
                   vCodCupon = 
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_CUPON);
                   vFechIni  =
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_FECHA_INI);
                   
                   vFechFin  =
                       FarmaUtility.getValueFieldArrayList(listCupones,
                                                           i,COL_COD_FECHA_FIN);                          
                   
                   indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,
                                                               vCodCupon).trim();
                   
                   if(indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                   {
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
                   }
                   else
                       log.debug("Es un cupon multiuso..");
                   
               }
             }           
       }
       catch(SQLException e)
       {
           
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
       if(!UtilityCaja.validaFechaMovimientoCaja(this, tblFormasPago)){
               FarmaUtility.liberarTransaccion();
               return;
       }
       if(vActCupon)
       FarmaUtility.aceptarTransaccion();
       
       if(VariablesCaja.vIndCommitRemota)
           FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                 FarmaConstants.INDICADOR_N);
   }
    
   /**
    * Valida el uso de cupones
    * @author dubilluz
    * @since  20.08.2008
    */
   private boolean validaUsoCupones(String pNumPedVta,String pIndCloseConecction,
                                    ArrayList pListaCuponesPedido,
                                    String  pIndLinea)
   {
       log.debug("validando uso de cupones");
       
       ArrayList listCupones = new ArrayList(); 
       ArrayList ltDatosCupon = new ArrayList();
       String vCodCupon = "";
       String indMultiUso = "";       
       String vIndLineaBD = "";
       String valida = "";
       String vEstCupon = "";
       boolean retorno = false;
       try{
           listCupones = (ArrayList)pListaCuponesPedido.clone();           
           log.debug("listCupones " + listCupones);
           // 1. SE VERIFICA SI EL VALOR DE LA LISTA NO FUE NULO
           if(listCupones.size()>0)
           { 
               if(listCupones.size()==1){
                   String vValor = "";
                   vValor = FarmaUtility.getValueFieldArrayList(listCupones,0,0);
                   if(vValor.equalsIgnoreCase("NULO")){
                      retorno =  true;
                      return retorno;
                   }
               }
                
               //  2. Se verificara si hay linea con matriz
               //--El segundo parametro indica si se cerrara la conexion
               vIndLineaBD = pIndLinea.trim();
    
               // 3. SE VALIDARA CADA CUPON 
               for(int i=0 ; i<listCupones.size() ; i++){
                   vCodCupon = FarmaUtility.getValueFieldArrayList(listCupones,i,0);
                   indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPedVta,vCodCupon).trim();
                   
                   //Se valida el Cupon en el local
                    //Modificado por DVELIZ 04.10.08
                    DBModuloVenta.verificaCupon(vCodCupon,ltDatosCupon,indMultiUso,
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
                   vEstCupon = DBCaja.getEstCuponBloqueo(pNumPedVta,vCodCupon).trim();
                   log.debug("Se bloquea el estado .. " + vEstCupon);
                   
               }
           }
           
           retorno = true;
           
           
       }
       catch(SQLException e)
       {
           //cierra la conexion
           FarmaConnectionRemoto.closeConnection();
           retorno =  false;
          log.error("",e);
           log.error(null,e);
           switch(e.getErrorCode())
           {
               case 20003: FarmaUtility.showMessage(this,"La campa�a no es valida.",tblFormasPago); break;
               case 20004: FarmaUtility.showMessage(this,"Local no valido para el uso del cupon.",tblFormasPago); break;
               case 20005: FarmaUtility.showMessage(this,"Local de emisi�n no valido.",tblFormasPago); break;
               case 20006: FarmaUtility.showMessage(this,"Local de emisi�n no es local de venta.",tblFormasPago); break;
               case 20007: FarmaUtility.showMessage(this,"Cup�n ya fue usado.",tblFormasPago); break;
               case 20008: FarmaUtility.showMessage(this,"Cup�n esta anulado.",tblFormasPago); break;
               case 20009: FarmaUtility.showMessage(this,"Campa�a no valido.",tblFormasPago); break;
               case 20010: FarmaUtility.showMessage(this,"Cupon no esta vigente .",tblFormasPago); break;
               default: FarmaUtility.showMessage(this,"Error al validar el cupon.\n"+e.getMessage(),tblFormasPago); break;

           }
           
       }
       log.debug("**FIN**");
       return retorno;           
   }
   
   /**
    * metodo encargado de obtener el dni del cliente fidelizado que realizo la compra
    * 
    * */
   private String obtenerDniClienteFidelizado(String nroPedido){
	   String dniClienteFid = "";
	   try {
		   dniClienteFid = DBCaja.obtieneDniClienteFidVenta(nroPedido).trim(); 
	   } catch (Exception e) {
		   dniClienteFid = "";
		   log.debug("error al obtener DNI cliente del pedido : "+e.getMessage());
	   }
	   
	   return dniClienteFid;
   }
   
   
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList getCampAutomaticasPedido(String nroPedido){
	   ArrayList listaCampAutomaticas = new ArrayList();
	   try {
		   listaCampAutomaticas = DBCaja.getListaCampAutomaticasVta(nroPedido);
		   if (listaCampAutomaticas.size()>0 ){
			   listaCampAutomaticas = (ArrayList)listaCampAutomaticas.get(0);
		   }
		   log.debug("listaCampAutomaticas listaCampAutomaticas ===> "+listaCampAutomaticas);
	   } catch (Exception e) {
		   log.debug("error al obtener campa�as automaticas usados en el pedido : "+e.getMessage());
	   }
	   return listaCampAutomaticas;
   }
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList CampLimitadasUsadosDeLocalXCliente(String dniCliente){
	   ArrayList listaCampLimitUsadosLocal = new ArrayList();
	   try {
		   listaCampLimitUsadosLocal = DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
		   if (listaCampLimitUsadosLocal.size()>0 ){
			   listaCampLimitUsadosLocal = (ArrayList)listaCampLimitUsadosLocal.get(0);
		   }
		   log.debug("listaCampLimitUsadosLocal listaCampLimitUsadosLocal ===> "+listaCampLimitUsadosLocal);
	   } catch (Exception e) {
		   log.debug("error al obtener las campa�as limitadas ya usados por cliente en LOCAL : "+e.getMessage());
	   }
	   return listaCampLimitUsadosLocal;
   }
   
   /**
    * obtener todas las campa�as de fidelizacion automaticas usados en el pedido
    * 
    * */
   private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente){
	   ArrayList listaCampLimitUsadosMatriz = new ArrayList();
	   try {
		   //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
		   listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
		   if (listaCampLimitUsadosMatriz.size()>0 ){
			  listaCampLimitUsadosMatriz = (ArrayList)listaCampLimitUsadosMatriz.get(0);
		   }
		   log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> "+listaCampLimitUsadosMatriz);
	   } catch (Exception e) {
		   log.debug("error al obtener las campa�as limitadas ya usados por cliente en MATRIZ : "+e.getMessage());		   
	   }
	   return listaCampLimitUsadosMatriz;
   }
   
   
   

    private boolean validaCajaAbierta(){

    boolean result=true;
    String Indicador="";
        try {
                //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
                 log.debug("VariablesCaja.vNumCaja ===> "+VariablesCaja.vNumCaja);
                Indicador = DBCaja.obtieneEstadoCaja();
                if (Indicador.trim().equalsIgnoreCase("N")){
                            FarmaUtility.showMessage(this, "La caja no se encuentra aperturada. Verifique!!!", null);
                        result=false;
                }
                log.debug("Se valida apertura de caja para el cobro ===> "+Indicador);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            result=false;
                log.debug("error al obtener indicador de caja abierta : "+e.getMessage());                 
        }
        
        //bloque de caja
         return result;
    }  

}