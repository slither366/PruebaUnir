package venta.caja.reference;


import com.gs.mifarma.MetodosBprepaid;
import com.gs.mifarma.MetodosTXFactory;
import com.gs.mifarma.MetodosTXVirtual;

import java.awt.Frame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import common.DlgLogin;
import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaPrintServiceTicket;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.util.List;

import venta.administracion.impresoras.reference.DBImpresoras;
import venta.administracion.impresoras.reference.ImpresoraTicket;
import venta.caja.DlgFormaPago;
import venta.caja.DlgNuevoCobro;
import venta.caja.DlgProcesarNuevoCobro;
import venta.caja.DlgProcesarNuevoCobroBTLMF;
import venta.caja.DlgProcesarProductoVirtual;
import venta.ce.reference.DBCajaElectronica;
import venta.ce.reference.UtilityCajaElectronica;
import venta.convenio.reference.DBConvenio;
import venta.convenioBTLMF.reference.DBConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.delivery.reference.VariablesDelivery;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.recaudacion.reference.ConstantsRecaudacion;
import venta.recepcionCiega.reference.DBRecepCiega;
import venta.recetario.reference.DBRecetario;
import venta.recetario.reference.FacadeRecetario;
import venta.recetario.reference.VariablesRecetario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.DlgMensajeImpresion;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import svb.fact_electronica.reference.DBFactElectronica;
import svb.fact_electronica.reference.UtilityCPE;
import svb.fact_electronica.reference.UtilityFactElectronica;

import svb.imp_fe.electronico.UtilityImpCompElectronico;

import venta.caja.DlgFormaPagoHHSur;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : UtilityCaja.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      06.03.2005   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class UtilityCaja {

  private static final Logger log = LoggerFactory.getLogger(UtilityCaja.class);
  
  private static int numeroCorrel = 1;
  public static  int acumuCorre=0;
  /**
   * Constructor
   */
  public UtilityCaja() {
  }
      
    /**
     * Metodo que sirve para validar que existe conexion en matriz
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void validarConexionMatriz(){
        
        
        VariablesCaja.vIndConexion= FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_MATRIZ,
                                     FarmaConstants.INDICADOR_N);
        
        
        if(VariablesCaja.vIndConexion.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           log.debug("No existe linea se cerrara la conexion ...");
           FarmaConnectionRemoto.closeConnection();
        
        }
        
                         
     
    }

  //private static MetodosG proveedorTarjeta;

  /**
   * El proveedor ahora es Brightstar
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static MetodosBprepaid proveedorTarjetaBprepaid;
  
  /**
   * Se utiliza el patron Factory para invocar a estos metodos.
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static MetodosTXVirtual proveedorTarjetaVirtual;


  public static String obtieneEstadoPedido(JDialog pDialog, String pNumPedVta)
  {
    String estadoPed = "";
    try {
      estadoPed = DBCaja.obtieneEstadoPedido(pNumPedVta);
      return estadoPed;
    } catch (SQLException sqlException) {
      //log.error("",sqlException);
      log.error(null,sqlException);
      //FarmaUtility.showMessage(pDialog, "Error al obtener Estado del Pedido !!! - " + sqlException.getErrorCode(), pObjectFocus);
      return estadoPed;
    }
  }
  
  /**
     * Valida si existe impresora asociada al cajero que realiza el cobro
     * @param pDialog
     * @param pObjectFocus
     * @return
     */
  public static boolean existeCajaUsuarioImpresora(JDialog pDialog, Object pObjectFocus) {
        try {
            boolean existeCajaUsuarioImpresora = true;
            int cajaUsuario;
            cajaUsuario = DBCaja.obtieneNumeroCajaUsuario();
            if (cajaUsuario != 0) {
                VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();
                int cajaAbierta = DBCaja.verificaCajaAbierta();
                log.debug("Indicador Caja Abierta: "+cajaAbierta);
                if (cajaAbierta == 0) {
                    VariablesCaja.vNumCaja = "";
                    existeCajaUsuarioImpresora = false;
                    FarmaUtility.showMessage(pDialog, 
                                             "La Caja relacionada al Usuario no ha sido Aperturada. Verifique !!!", 
                                             pObjectFocus);
                } else {
                    log.debug("VariablesCaja.vNumCaja = " + 
                              VariablesCaja.vNumCaja);
                    VariablesCaja.vSecMovCaja = 
                            DBCaja.obtieneSecuenciaMovCaja();
                    log.debug("VariablesCaja.vSecMovCaja = " + 
                              VariablesCaja.vSecMovCaja);
                    if (VariablesCaja.vSecMovCaja.equals("0")) {
                        existeCajaUsuarioImpresora = false;
                        VariablesCaja.vSecMovCaja = "";
                        FarmaUtility.showMessage(pDialog, 
                                                 "No se pudo determinar el Movimiento de Caja. Verifique !!!", 
                                                 pObjectFocus);
                    } else {
                        if (!existeImpresorasVenta(pDialog, pObjectFocus))
                            existeCajaUsuarioImpresora = false;
                    }
                }
            } else {
                existeCajaUsuarioImpresora = false;
                FarmaUtility.showMessage(pDialog, 
                                         "El usuario No tiene caja relacionada. Verifique !!!", 
                                         pObjectFocus);
            }
            return existeCajaUsuarioImpresora;
        } catch (SQLException sqlException) {
            //log.error("",sqlException);
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialog, 
                                     "Error al obtener Datos de la Relacion Caja - Usuario - Impresora !!! - " + 
                                     sqlException.getErrorCode(), 
                                     pObjectFocus);
            return false;
        }
  }

  public static boolean existeImpresorasVenta(JDialog pDialog, Object pObjectFocus) throws SQLException {
    boolean existeImpresorasVenta = true;
    //JCORTEZ 24.03.09 No se valida por ahora la relacion de caja impresora obligatoria
    //String tipoComprobanteImpresora = DBCaja.verificaRelacionCajaImpresoras();
   /* if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Boletas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Facturas. Verifique !!!", pObjectFocus);
    } else if ( tipoComprobanteImpresora.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA) )
    {
      existeImpresorasVenta = false;
      FarmaUtility.showMessage(pDialog, "No se pudo determinar la existencia de la Impresora para Guias. Verifique !!!", pObjectFocus);
    } else
    {*/
      ArrayList myArray = new ArrayList();
      DBCaja.obtieneSecuenciaImpresorasVenta(myArray);
      if(myArray.size() <= 0)
      {
        VariablesCaja.vSecImprLocalBoleta = "";
        VariablesCaja.vSecImprLocalFactura = "";
        VariablesCaja.vSecImprLocalGuia = "";
        VariablesCaja.vSecImprLocalTicket="";
        VariablesCaja.vSerieImprLocalTicket="";
        existeImpresorasVenta = false;
        FarmaUtility.showMessage(pDialog, "Error al leer informacion de las impresoras.", pObjectFocus);
      } else
      {
        VariablesCaja.vSecImprLocalBoleta = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        VariablesCaja.vSecImprLocalFactura = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        VariablesCaja.vSecImprLocalGuia = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        VariablesCaja.vSecImprLocalTicket=((String)((ArrayList)myArray.get(0)).get(3)).trim();
        VariablesCaja.vSerieImprLocalTicket=((String)((ArrayList)myArray.get(0)).get(4)).trim();
        log.debug("VariablesCaja.vSecImprLocalBoleta : " + VariablesCaja.vSecImprLocalBoleta);
        log.debug("VariablesCaja.vSecImprLocalFactura : " + VariablesCaja.vSecImprLocalFactura);
        log.debug("VariablesCaja.vSecImprLocalGuia : " + VariablesCaja.vSecImprLocalGuia);
        log.debug("VariablesCaja.vSecImprLocalTicket : " + VariablesCaja.vSecImprLocalTicket);
        existeImpresorasVenta = true;   
      }
    //}
    return existeImpresorasVenta;
  }

  public static void imprimeComprobantePago(JDialog pJDialog,
                                            ArrayList pDetalleComprobante,
                                            ArrayList pTotalesComprobante,
                                            String pTipCompPago,
                                            String pNumComprobante,
                                            boolean indAnulado,
                                            int valor) throws Exception {

    String pValTotalBruto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(0)).trim();
    String pValTotalNeto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(1)).trim();
    String pValTotalDescuento = ((String)((ArrayList)pTotalesComprobante.get(0)).get(2)).trim();
    String pValTotalImpuesto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(3)).trim();
    String pValTotalAfecto = ((String)((ArrayList)pTotalesComprobante.get(0)).get(4)).trim();
    String pValRedondeo = ((String)((ArrayList)pTotalesComprobante.get(0)).get(5)).trim();
    String pPorcIgv = ((String)((ArrayList)pTotalesComprobante.get(0)).get(6)).trim();
    String pNomImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(7)).trim();
    String pNumDocImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(8)).trim();
    String pDirImpreso = ((String)((ArrayList)pTotalesComprobante.get(0)).get(9)).trim();
    String fechaBD = ((String)((ArrayList)pTotalesComprobante.get(0)).get(10)).trim();
    String pValTotalAhorro = ((String)((ArrayList)pTotalesComprobante.get(0)).get(11)).trim();
    
    pValTotalBruto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalBruto),2);
    pValTotalNeto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalNeto),2);
    pValTotalDescuento = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalDescuento),2);
    pValTotalImpuesto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalImpuesto),2);
    pValTotalAfecto = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValTotalAfecto),2);
    pValRedondeo = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pValRedondeo),2);
    pPorcIgv = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pPorcIgv),2);

    /**
     * Ruta para la generecion del archivo
     * @author JCORTEZ
     * @since 06.07.09
     * */
        String ruta ="";
        ruta=UtilityPtoVenta.obtieneDirectorioComprobantes();
       
        //Se agrega la Fecha al archivo Impreso.
        //JMIRANDA  07/07/2009
        Date vFecImpr = new Date();
        String fechaImpresion;
              
        String DATE_FORMAT = "yyyyMMdd";
           SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // log.debug("Today is " + sdf.format(vFecImpr));
           fechaImpresion =  sdf.format(vFecImpr);                
        log.debug("fecha : " +fechaImpresion);
        
  if ( pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA) ){
		log.debug("*******imprimir BOLETA **********");
      //ruta=ruta+"B_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
      //JMIRANDA 07/07/09 se agrega FECHA al Nombre
      ruta=ruta+fechaImpresion+"_"+"B_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
      
      DlgMensajeImpresion dlgLogin = new DlgMensajeImpresion(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true,
                                                             ConstantsModuloVenta.TIPO_COMP_BOLETA);
        dlgLogin.setVisible(true);      
      //impresion 
      //imprimeBoleta
      UtilityCajaRenova.imprimeBoletaRenova
                    (pJDialog,
                    fechaBD,
                    pDetalleComprobante,
                    pValTotalNeto,
                    pValRedondeo,
                    pNumComprobante,
                    pNomImpreso,
                    pDirImpreso,
                    pValTotalAhorro,
                    ruta,true);

                    
  }
    else if (pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET))
    {   //JCORTEZ  25.03.09
        log.debug("*******JCORTEZ**********");
        log.debug("PARAMETROS-->");
        log.debug("pNomImpreso-->"+pNomImpreso);
        log.debug("pDirImpreso-->"+pDirImpreso);
        //ruta=ruta+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        //JMIRANDA 07/07/09 se agrega FECHA al Nombre
        ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        log.debug("fecha : " +fechaImpresion);
        log.debug("*******imprimir COMP_TICKET **********");
        //impresion 
        imprimeBoletaTicket(pJDialog,
                            fechaBD,
                            pDetalleComprobante,
                            pValTotalNeto,
                            pValRedondeo,
                            pNumComprobante,
                            pNomImpreso,
                            pNumDocImpreso,
                            pValTotalAhorro,
                            ruta,
                            indAnulado,
                            pTipCompPago,
                            valor); 
         
    }
    //LLEIVA 24-Ene-2014 Se a�adio el tipo de comprobante Ticket Factura
    else if (pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET_FACT))
    {   //JCORTEZ  25.03.09
        log.debug("*******JCORTEZ**********");
        log.debug("PARAMETROS-->");
        log.debug("pNomImpreso-->"+pNomImpreso);
        log.debug("pDirImpreso-->"+pDirImpreso);
        //ruta=ruta+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        //JMIRANDA 07/07/09 se agrega FECHA al Nombre
        ruta=ruta+fechaImpresion+"_"+"T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        log.debug("fecha : " +fechaImpresion);
        log.debug("*******imprimir TICKET - FACTURA **********");
        //impresion 
        imprimeFactTicket(pJDialog,
                                fechaBD,
                                pDetalleComprobante,
                                pValTotalNeto,
                                pValRedondeo,
                                pNumComprobante,
                                pNomImpreso,
                                pNumDocImpreso,
                                pValTotalAhorro,
                                ruta,
                                indAnulado,
                                pTipCompPago,
                                valor); 
       
    }
    else if ( pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA) )
    {

      if(true)
      {
          //ruta=ruta+"F_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
          //JMIRANDA 07/07/09 se agrega FECHA al Nombre
          ruta=ruta+fechaImpresion+"_"+"F_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        //impresion
        log.info("****************************************IMPRIMIENDO FACTURA MIFARMA*********************************");
        //Cesar Huanes 31.01.2014 muestra el mensaje de alerta de impresion de factura antes de imprimir
              VariablesModuloVentas.vNumDocImpreso= acumuCorre+1;//Indica el numero sgte de impresion de documento  
        DlgMensajeImpresion dlgLogin = new DlgMensajeImpresion(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true,
                                                               ConstantsModuloVenta.TIPO_COMP_FACTURA);
          dlgLogin.setVisible(true);
        /*imprimeFactura(pJDialog,
                       fechaBD,
                       pDetalleComprobante,
                       pValTotalBruto,
                       pValTotalNeto,
                       pValTotalAfecto,
                       pValTotalDescuento,
                       pValTotalImpuesto,
                       pPorcIgv,
                       pValRedondeo,
                       pNumComprobante,
                       pNomImpreso,
                       pNumDocImpreso,
                       pDirImpreso,
                       pValTotalAhorro,
                       ruta, true);*/
        //double pValor = Double.parseDouble(pNumComprobante.trim().replace("-", ""));
        UtilityCajaDentalTayloc.imprimeFactura(pJDialog,
                       fechaBD,
                       pDetalleComprobante,
                       pValTotalBruto,
                       pValTotalNeto,
                       pValTotalAfecto,
                       pValTotalDescuento,
                       pValTotalImpuesto,
                       pPorcIgv,
                       pValRedondeo,
                       pNumComprobante,
                       pNomImpreso,
                       pNumDocImpreso,
                       pDirImpreso,
                       pValTotalAhorro,
                       ruta, true);

        acumuCorre++;
         
      }
    /*else if ( VariablesCaja.vTiComprobante.equalsIgnoreCase(ConstantsVentas.TIPO_GUIA) )
      imprimeGuia(pJDialog,
                  fechaBaseDatos,
                  pDetallePedido,
                  pVaTotalVenta,
                  pVaTotalDescuento,
                  pVaTotalImpuesto,
                  pVaTotalPrecioVenta,
                  pVaTotalPrecioVentaConvenio,
                  pVaSaldoRedondeo,
                  nombreVendedor,
                  pEsConvenio,
                  pACuenta,
                  pPendiente,
                  pComprobante,
                  pImprimeDescuento,
                  pDetalleImpresion,
                  pDeducible);
    */
    
    } 
    
      //GFonseca 27.12.2013 Se a�ade nuevo m�todo de abrir gabeta.
      //UtilityCaja.abrirGabeta(null, false,VariablesCaja.vNumPedVta);
  }



    public static void main(String[] args) {
        FarmaPrintService vPrint = new FarmaPrintService(24, "\\\\127.0.0.1\\boleta", false);
        if ( !vPrint.startPrintService() ) {
        System.out.println("no se puede ");
        }
        else{
            System.out.println("puede ");
            
            vPrint.activateCondensed();
            /*
            for(int i=0;i<20;i++){
                String pCad = completeWithSymbol(i+"",2, "@","I");
                vPrint.printLine("_____ i"+ pCad +"****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);

            }*/
            
            vPrint.printLine("_____ i01   *                                                                                                       ___",true);
            vPrint.printLine("_____ i02   *                                                                                                       ___",true);
            vPrint.printLine("_____ i03   *                                                                                                       ___",true);
            vPrint.printLine("_____ i04   *                                                                                                       ___",true);
            vPrint.printLine("_____ i05   *                                                                               1 001-0006006           ___",true);
            vPrint.printLine("_____ i06   *                                                                                                       ___",true);
            vPrint.printLine("_____ i07        Diego Armando Ubilluz Carrillo                                             03/11/2015              ___",true);
            vPrint.printLine("_____ i08          Jr Octavio Infantes 3284 Canto                                           44324600                ___",true);
            vPrint.printLine("_____ i09   *                                                                                                       ___",true);
            vPrint.printLine("_____ i10   *                                                                                                       ___",true);
            vPrint.printLine("_____ i11  999 Producto de atencion numero 1                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i12  999 Producto de atencion numero 2                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i13  999 Producto de atencion numero 3                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i14  999 Producto de atencion numero 4                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i15  999 Producto de atencion numero 5                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i16  999 Producto de atencion numero 6                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i17  999 Producto de atencion numero 7                                         9,999.99    99,999.99   ___",true);
            vPrint.printLine("_____ i18   *                                                                                                       ___",true);
            vPrint.printLine("_____ i19   *                                                                     Total : S/ 99,999,999.99 ___",true);

            /*
            vPrint.printLine("_____ i01****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i02****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i03****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i04****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**1 001-0006006  123**12F ___",true);
            vPrint.printLine("_____ i05****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i05****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i06       Diego Armando Ubilluz Carrillo            **123**123***123***123**123**     03/11/2015     123**12F ___",true);
            vPrint.printLine("_____ i07       Jr Octavio Infantes 3284 Canto            **123**123***123***123**123**     44324600       123**12F ___",true);
            vPrint.printLine("_____ i08****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i09****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 1                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 2                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 3                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 4                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 5                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 6                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i1099999 Producto de atencion numero 7                                         9,999.99    99,999.99 F ___",true);
            vPrint.printLine("_____ i17****123***123**123***123**123**123**123**123**123**123**123***123***123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i18****123***123**123***123**123**123**123**123**123**123**123***123***123**Total : S/ 99,999,999.99 ___",true);
            */
            /*
            vPrint.printLine("_____ i01****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i02****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i03****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i04****123***123**123**123**123**123**   001- 00060001   ___",true);
            vPrint.printLine("_____ i05****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____   Diego Armando Ubilluz Carrillo           02/11/2015  F ___",true);
            vPrint.printLine("_____      Jr Octavio Infantes 3284 Canto        44324600    F ___",true);
            vPrint.printLine("_____ i08****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ i09****123***123**123**123**123**123**123**123**123**12F ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ 9999 Producto de atencion numero 1    9,999.99  9,999.99 ___",true);
            vPrint.printLine("_____ REDO: 0.00 CAJERO: NORMA ALTAMIRANO                  12F ___",true);
            vPrint.printLine("_____ i18****123***123**123**123**123**123**  S/ 99,999,999.99 ___",true);*/

            vPrint.endPrintService();
        }
        
    }
    
    public static String completeWithSymbol(String pValue, int pLength, 
                                            String pSymbol, String pAlign) {
        String tempString = pValue;
        for (int i = pValue.length(); i < pLength; i++) {
            if (pAlign.trim().equalsIgnoreCase("I"))
                tempString = pSymbol + tempString;
            else
                tempString += pSymbol;
        }
        return tempString;
    }

  private static void imprimeBoleta(JDialog   pJDialog,
                                    String    pFechaBD,
                                    ArrayList pDetalleComprobante,
                                    String    pValTotalNeto,
                                    String    pValRedondeo,
                                    String    pNumComprobante,
                                    String    pNomImpreso,
                                    String    pDirImpreso,
                                    String    pValTotalAhorro,
                                    String    pRuta,
                                    boolean   bol) throws Exception {
    log.debug("IMPRIMIR BOLETA No : " + pNumComprobante);
    String indProdVirtual = "";
    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    
    //JCORTEZ 06.07.09 ruta para la genericon de archivo
   // if(bol) VariablesCaja.vRutaImpresora=pRuta;
    
    //FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora + "boleta" + pNumComprobante + ".txt", false);
    FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);

      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
      
    log.debug("Ruta : " + VariablesCaja.vRutaImpresora + "boleta" + VariablesCaja.vNumPedVta + ".txt");
  //  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
     log.debug("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
  if ( !vPrint.startPrintService() ) {
      
      
      
                VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                 log.info("**** Fecha :"+ pFechaBD);
                 log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                 log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                 log.info("**** IP :" + FarmaVariables.vIpPc);
                 log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
                    }
  
  else {
      try {
    vPrint.activateCondensed();
          if(VariablesPtoVenta.vIndDirMatriz){
              vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionCortaMatriz ,true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionCortaMatriz ,true);
          }
   //JMIRANDA 22.08.2011 Cambio para verificar si imprime
   if(UtilityModuloVenta.getIndImprimeCorrelativo()){        
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
   }
   else{
       vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
   }
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
      vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
    int linea = 0;
    for (int i=0; i<pDetalleComprobante.size(); i++) {
      //Agregado por DVELIZ 13.10.08
      String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        log.debug("valor 1:"+valor);
      if(valor.equals("0.000")) valor = " ";
      //fin DVELIZ
      log.debug("Deta "+ (ArrayList)pDetalleComprobante.get(i) );
        log.debug("valor 2:"+valor);
      vPrint.printLine("" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
                       //Agregado por DVELIZ 10.10.08
                       FarmaPRNUtility.alinearDerecha(valor,8) + "" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);
                       
        vPrintArchivo.printLine("" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
                       //Agregado por DVELIZ 10.10.08
                       FarmaPRNUtility.alinearDerecha(valor,8) + "" +
                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);                       
      linea += 1;
      indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
      //verifica que solo se imprima un producto virtual en el comprobante
      if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
      else
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    }

    if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
    {
      vPrint.printLine("", true);
       vPrintArchivo.printLine("",true);
      impresionInfoVirtual(vPrint,vPrintArchivo,
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                           VariablesCaja.vNumPedVta,//Se a�adio el parametro
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto

      linea = linea + 4;
   }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }
    //MODIFICADO POR DVELIZ 13.10.08
    //
    if(!VariablesModuloVentas.vEsPedidoConvenio){
        if(pDetalleComprobante.size()< 8){
            for (int j=linea; j<=8; j++) {
                    if(!VariablesCaja.vImprimeFideicomizo){
                            vPrint.printLine(" ",true);
                            vPrintArchivo.printLine(" ",true);
                    }
                }
        }
    }else{
        for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)  if(!VariablesCaja.vImprimeFideicomizo)vPrint.printLine(" ",true);
    }
    
    //*************************************INFORMACION DEL CONVENIO*************************************************//
    //*******************************************INICIO************************************************************//

    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      try
      {
        ArrayList aInfoPedConv = new ArrayList();
        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

        for(int i=0; i<aInfoPedConv.size(); i++)
        {
          ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
        String Ind_Comp=((String)registro.get(8)).trim();
        if(Ind_Comp.equalsIgnoreCase("N")){
          vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);
                           
           vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);                           
          /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
          String vCredDisp = ((String)registro.get(7)).trim();
          if(vCredDisp.equals(""))
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                               ,true);
          }else
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                             FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                               FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                               ,true);
          } 
         } 
        }

      }
        //ASOLIS 
                //IMPRIMIR  EL  IP ,NUMERO COMPROBANTE y HORA DE IMPRESI�N  EN CASO DE ERROR.*/
              catch(SQLException sql)
              {
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                  
                //log.error("",sql);
                log.debug("Error de BD "+ sql.getMessage());
                
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al obtener informacion del Pedido Convenio ");
                  log.info("Error al imprimir la BOLETA : ");
                  log.error(null,sql);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
              }
              
                catch(Exception e){
                  
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                  
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir la BOLETA : "+e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
              }
      
      //vPrint.printLine(" ",true);
    }
     else
    {
    //dveliz 13.10.08
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
    }
    
    //ERIOS 25.07.2008 imprime el monto ahorrado.
    double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
    
    //DUBILLUZ 22.08.2008 MSG DE CUPONES
    String msgCumImpresos = " ";
    if(VariablesCaja.vNumCuponesImpresos>0){
        String msgNumCupon = "";
        if(VariablesCaja.vNumCuponesImpresos==1){
            msgNumCupon = "CUPON";
        }
        else{
            msgNumCupon = "CUPONES";
        }
        msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+
                         msgNumCupon;
    }
      
    //MODIFICADO POR DVELIZ 02.10.08
    //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr, true);
    if(auxTotalDcto > 0)
    {
      /* old 01.09.2009
      vPrint.printLine("                         UD. HA AHORRADO S/. "+
                       pValTotalAhorro+
                       " EN ESTA COMPRA"+
                       msgCumImpresos,
                       true);
        vPrintArchivo.printLine("                         UD. HA AHORRADO S/. "+
                         pValTotalAhorro+
                         " EN ESTA COMPRA"+
                         msgCumImpresos,
                         true);
        */
log.info("Imprimiendo Ahorro");
        
        //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
        String obtenerMensaje="";
        String indFidelizado="";
        log.info("Identificando cliente fidelizado");
        if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
            indFidelizado="S";
        }else 
         { indFidelizado="N"; }
        log.info("Fidelizado--> "+indFidelizado);    
        obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
        vPrint.printLine(""+obtenerMensaje+" "+" S/. "+pValTotalAhorro+"  "+msgCumImpresos,true);
          vPrintArchivo.printLine(""+obtenerMensaje+" S/. "+pValTotalAhorro+"  "+msgCumImpresos,true);
         /* vPrint.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA"+msgCumImpresos,true);
             vPrintArchivo.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA"+msgCumImpresos,true);*/
    
    }else
    {
        if(VariablesCaja.vNumCuponesImpresos>0){
           vPrint.printLine("                         "+msgCumImpresos,true);
        vPrintArchivo.printLine("                         "+msgCumImpresos,true);
           //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr+msgCumImpresos,true);
       }else{
           vPrint.printLine(" ",true);
               vPrintArchivo.printLine(" ",true);
           }
    }
    
    //*********************************************FIN*************************************************************//
    //*************************************INFORMACION DEL CONVENIO***********************************************//

                VariablesModuloVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
    VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
    {
                    VariablesModuloVentas.vTituloDelivery = "" ;
    } else
                    VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
    
/*
    log.debug("****************DIEGO************************");
    log.debug("VariablesVentas.vTipoPedido " + VariablesVentas.vTipoPedido);
    log.debug("VariablesCaja.vFormasPagoImpresion " + VariablesCaja.vFormasPagoImpresion);
    log.debug("VariablesVentas.vTituloDelivery " + VariablesVentas.vTituloDelivery);
    log.debug("******************************************************");
*/
    vPrint.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),65) + " " +
                     " Total Venta   S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
      vPrintArchivo.printLine(" SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),65) + " " +
                     " Total Venta   S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
    vPrint.printLine(" REDO: " + pValRedondeo +
                     " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     " CAJA: " + VariablesCaja.vNumCajaImpreso +
                     " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                     " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
      vPrintArchivo.printLine(" REDO: " + pValRedondeo +
                       " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                       " CAJA: " + VariablesCaja.vNumCajaImpreso +
                       " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                       " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
    vPrint.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion  + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery ,true);
      vPrintArchivo.printLine(" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion  + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery ,true);
    /*dubilluz 2011.09.16*/
    if(VariablesCaja.vImprimeFideicomizo){
        String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
        if(lineas.length>0){
            for(int i=0;i<lineas.length;i++){
            vPrint.printLine(""+lineas[i].trim(),true);
            vPrintArchivo.printLine(""+lineas[i].trim(),true);
            }
        }
        else{
        vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
        vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
        }
    }
    /*FIN dubilluz 2011.09.16*/
          
    vPrint.deactivateCondensed();
    vPrint.endPrintService();
      vPrintArchivo.endPrintService();
      
    log.info("Fin al imprimir la boleta: " + pNumComprobante);
    VariablesCaja.vEstadoSinComprobanteImpreso="N";
    
      //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
      DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
      log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
  }               
                                        catch(SQLException sql)
                                              {
                                                //log.error("",sql);
                                                VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                log.debug("Error de BD "+ sql.getMessage());
                                                
                                                  log.info("**** Fecha :"+ pFechaBD);
                                                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                  log.info("**** IP :" + FarmaVariables.vIpPc);
                                                  log.info("Error al imprimir la boleta : " + sql.getMessage());
                                                  log.error(null,sql);
                                                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                    enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
                                              }
                                              
                                                catch(Exception e){
                                                  VariablesCaja.vEstadoSinComprobanteImpreso="S";
                                                  log.info("**** Fecha :"+ pFechaBD);
                                                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                                                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                                                  log.info("**** IP :" + FarmaVariables.vIpPc);
                                                  log.info("Error al imprimir la boleta: "+e);
                                                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                                                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                                              } 
                                    
                                    
                                    }
  
  } 
  //factura renova 2016.12.28  
  private static void imprimeFactura(JDialog   pJDialog,
                                     String    pFechaBD,
                                     ArrayList pDetalleComprobante,
                                     String    pValTotalBruto,
                                     String    pValTotalNeto,
                                     String    pValTotalAfecto,
                                     String    pValTotalDcto,
                                     String    pValTotalIgv,
                                     String    pPorcIgv,
                                     String    pValRedondeo,
                                     String    pNumComprobante,
                                     String    pNomImpreso,
                                     String    pNumDocImpreso,
                                     String    pDirImpreso,
                                     String    pValTotalAhorro,
                                     String    pRuta,
                                     boolean   bol) throws Exception {
    log.debug("IMPRIMIR FACTURA No : " + pNumComprobante);
    String indProdVirtual = "";
    
    //jcortez 06.07.09 Se verifica ruta 
   // if(bol) VariablesCaja.vRutaImpresora=pRuta;
  
    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt",false);
    FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora,false);
    
      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
    

    log.debug("Ruta : " + VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt");
      if ( !vPrint.startPrintService() ) {
                     VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                     log.info("**** Fecha :"+ pFechaBD);
                     log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                     log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                     log.info("**** IP :" + FarmaVariables.vIpPc);
                     log.info("ERROR DE IMPRESORA : No se pudo imprimir la factura");
          }
    
    
      else{
    
          try{
    String dia = pFechaBD.substring(0,2);
    String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3,5)));
    String ano = pFechaBD.substring(6,10);
    String hora = pFechaBD.substring(11,19);
    vPrint.activateCondensed();
              
    if(VariablesPtoVenta.vIndDirMatriz){
        ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(0).toString() ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(0).toString() ,true);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(1).toString(),true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(1).toString(),true);
        
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + 
                        FarmaPRNUtility.alinearIzquierda( FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal ,37)+ 
                        lstDirecMatriz.get(2).toString() ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + 
                        FarmaPRNUtility.alinearIzquierda( FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal ,37)+ 
                        lstDirecMatriz.get(2).toString() ,true);
    }else{
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);  
        
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);   
        
        //JMIRANDA 22.08.2011 Cambio para verificar si imprime
        if(UtilityModuloVenta.getIndImprimeCorrelativo()){
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) + "CORR." + VariablesCaja.vNumPedVta,true);
            vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) + "CORR." + VariablesCaja.vNumPedVta,true);
        }else{
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) ,true);
                vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) ,true);        
        }
    }
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),true);
    
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + pDirImpreso.trim(),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + pDirImpreso.trim(),true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(12) + "     " + pNumDocImpreso.trim() ,true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(12) + "     " + pNumDocImpreso.trim() ,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
              vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(" ",true);
              vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
              vPrintArchivo.printLine(" ",true);
    int linea = 0;
    double pMontoOld = 0,pMontoNew = 0,pMontoDescuento = 0;
    
    
              log.info("" + VariablesModuloVentas.vTipoPedido);          
              
    for (int i=0; i<pDetalleComprobante.size(); i++) {
        vPrint.printLine(" " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                           ,true
                        ); 
                        
        vPrintArchivo.printLine(" " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                           ,true
                        ); 

        
      linea += 1;
      indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
      //verifica que solo se imprima un producto virtual en el comprobante
      if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
      else
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
    }

    if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
    {
      vPrint.printLine("", true);
        vPrintArchivo.printLine("", true);
      impresionInfoVirtual(vPrint,vPrintArchivo,
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                           VariablesCaja.vNumPedVta,//Se a�adio el parametro
                           FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto

      linea = linea + 4;
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }

    //MODIFICADO POR DVELIZ 13.10.08
    //
     if(!VariablesModuloVentas.vEsPedidoConvenio){
         if(pDetalleComprobante.size() < 10){
             for (int j=linea; j<=10; j++) { 
                 vPrint.printLine(" ",true);
                 vPrintArchivo.printLine(" ",true);
             }
         }  
     }else{
         for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
     }
    //*************************************INFORMACION DEL CONVENIO*************************************************//
    //*******************************************INICIO************************************************************//

    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
      try
      {
        log.debug("****Imprimiendo... "+VariablesCaja.vNumPedVta);
        ArrayList aInfoPedConv = new ArrayList();
        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

        for(int i=0; i<aInfoPedConv.size(); i++)
        {
          ArrayList registro = (ArrayList) aInfoPedConv.get(i);
         //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
         String Ind_Comp=((String)registro.get(8)).trim();
         if(Ind_Comp.equalsIgnoreCase("N")){
          log.debug("registro "+registro);
          vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                           //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                           FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                           ,true);
                           
             vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                              //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                              FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                              ,true);                           
          /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
          String vCredDisp = ((String)registro.get(7)).trim();
          if(vCredDisp.equals(""))
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                             ,true);
              vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                               ,true);
          }else
          {
            vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                             FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                             FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                             FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                             ,true);
            vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                               FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                               ,true);
          }
         }
        }

      }catch(SQLException sql)
      {
        //log.error("",sql);
        log.debug("Error de BD "+ sql.getMessage());
          VariablesCaja.vEstadoSinComprobanteImpreso="S";      
          log.info("**** Fecha :"+ pFechaBD);
          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
          log.info("**** IP :" + FarmaVariables.vIpPc);
          log.info("Error al obtener Informacion Pedido Convenio: ");
          log.info("Error al imprimir la factura : " + sql.getMessage());
          log.error(null,sql);
          
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
      }
      
        catch(Exception e){
          VariablesCaja.vEstadoSinComprobanteImpreso="S";      
          log.info("**** Fecha :"+ pFechaBD);
          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
          log.info("**** IP :" + FarmaVariables.vIpPc);
          log.info("Error al obtener Informacion Pedido Convenio : ");
          log.info("Error al imprimir la factura: "+e);
          
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
      }
      
      
      
      

      //vPrint.printLine(" ",true);
    }else
    {
        //dveliz 13.10.08
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
      //vPrint.printLine(" ",true);
    }
    //*********************************************FIN*************************************************************//
    //*************************************INFORMACION DEL CONVENIO***********************************************//
    
     //MODIFICADO POR DVELIZ 02.10.08
    //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr, true);
      
      
    //ERIOS 25.07.2008 imprime el monto ahorrado.
    double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
    if(auxTotalDcto > 0)
    {
     /* old
      vPrint.printLine(" UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
        vPrintArchivo.printLine(" UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
      */
		
        log.info("Imprimiendo Ahorro");
        
        //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
        String obtenerMensaje="";
        String indFidelizado="";
        log.info("Identificando cliente fidelizado");
        if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
            indFidelizado="S";
        }else 
         { indFidelizado="N"; }
        log.info("Fidelizado--> "+indFidelizado);    
        obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
         vPrint.printLine(""+obtenerMensaje+" "+" S/. "+pValTotalAhorro,true);
           vPrintArchivo.printLine(""+obtenerMensaje+" S/. "+pValTotalAhorro,true);
         /* vPrint.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);
             vPrintArchivo.printLine("UD. HA AHORRADO S/. "+pValTotalAhorro+" EN ESTA COMPRA",true);*/
  
    }else
    {
      vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
        vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
    {
                    VariablesModuloVentas.vTituloDelivery = "" ;
    }else
                    VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;

    //vPrint.printLine(" ",true);
                  
    //ERIOS 12.09.2013 Imprime direccion local
    String vLinea = "",vLineaDirecLocal1="",vLineaDirecLocal2="",vLineaDirecLocal3="";      
    if(VariablesPtoVenta.vIndDirLocal){     
        ArrayList lstDirecLocal = FarmaUtility.splitString("NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal, 46);
        vLineaDirecLocal1 = lstDirecLocal.get(0).toString();
        vLineaDirecLocal2 = ((lstDirecLocal.size()>1)?lstDirecLocal.get(1).toString():"");
        vLineaDirecLocal3 = ((lstDirecLocal.size()>2)?lstDirecLocal.get(2).toString():"");
    }           
              
    vLinea = " SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67);
    vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal1;
    vPrint.printLine(vLinea,true);
    vPrintArchivo.printLine(vLinea,true);
    
    vLinea = " REDO:" + pValRedondeo +
                     " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     " CAJA:" + VariablesCaja.vNumCajaImpreso +
                     " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                     " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso;
    vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal2;
    vPrint.printLine(vLinea,true);
    vPrintArchivo.printLine(vLinea,true);
    
    vLinea = " Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery;
    vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal3;
    vPrint.printLine(vLinea,true);
    vPrintArchivo.printLine(vLinea,true);
    
    //dubilluz
              if(!VariablesCaja.vImprimeFideicomizo){
        vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                            vPrintArchivo.printLine(" ",true);
              }
              
    //vPrint.printLine(" ",true);
    vPrint.printLine("     " +
                     "00000" + FarmaPRNUtility.llenarBlancos(12) +
                     FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(10) +
                     FarmaPRNUtility.alinearDerecha(pPorcIgv,6) + FarmaPRNUtility.llenarBlancos(11) +
                     FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(8) +
                     "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
     vPrintArchivo.printLine("     " +
                               "00000" + FarmaPRNUtility.llenarBlancos(12) +
                               FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(10) +
                               FarmaPRNUtility.alinearDerecha(pPorcIgv,6) + FarmaPRNUtility.llenarBlancos(11) +
                               FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(8) +
                               "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
      /*dubilluz 2011.09.16*/
      
      if(VariablesCaja.vImprimeFideicomizo){
          String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
          if(lineas.length>0){
              for(int i=0;i<lineas.length;i++){
              vPrint.printLine(""+lineas[i].trim(),true);
              vPrintArchivo.printLine(""+lineas[i].trim(),true);
              }
          }
          else{
          vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
          vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
          }
      }
    
      /*FIN dubilluz 2011.09.16*/
     /* vPrintArchivo.printLine(" XXXXX ",true);
                              vPrint.printLine("XXXXX ",true);
                              vPrint.printLine("YYYYY",true);
                                        vPrintArchivo.printLine("YYYYY",true);*/
    vPrint.endPrintService();
     vPrintArchivo.endPrintService();
     
              //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
              DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
              log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
    
    log.info("Fin al imprimir la factura: " + pNumComprobante);
              
              VariablesCaja.vEstadoSinComprobanteImpreso="N";      
          }
  
       
         catch(Exception e){
                  log.error("",e);
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir Factura: " + e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                 
              }
          
        
     }
  }

  private static void imprimeFacturaGuia(JDialog   pJDialog,
                                         String    pFechaBD,
                                         ArrayList pDetalleComprobante,
                                         String    pValTotalBruto,
                                         String    pValTotalNeto,
                                         String    pValTotalAfecto,
                                         String    pValTotalDcto,
                                         String    pValTotalIgv,
                                         String    pPorcIgv,
                                         String    pValRedondeo,
                                         String    pNumComprobante,
                                         String    pNomImpreso,
                                         String    pNumDocImpreso,
                                         String    pDirImpreso,
                                         String    pRuta,
                                         boolean   bol) throws Exception {
    log.debug("IMPRIMIR FACTURA No : " + pNumComprobante);

        //jcortez 06.07.09 Se verifica ruta 
      //  if(bol) VariablesCaja.vRutaImpresora=pRuta;
    //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt",false);
    FarmaPrintService vPrint = new FarmaPrintService(66,VariablesCaja.vRutaImpresora,false);
    
      //JCORTEZ 16.07.09 Se genera archivo linea por linea
      FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
      vPrintArchivo.startPrintService();
      
    log.debug("Ruta : " + VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt");
   // if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
   
      if ( !vPrint.startPrintService() ) {
                      VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                     log.info("**** Fecha :"+ pFechaBD);
                     log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                     log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                     log.info("**** IP :" + FarmaVariables.vIpPc);
                     log.info("Fin error de impresion: " + pNumComprobante);
                     log.info("ERROR DE IMPRESORA : No se pudo imprimir la factura Guia");
          }
      
      else{
          try{
          //JMIRANDA 15.12.09
            //ALMACENO LA VARIABLE PUNTO DE LLEGADA 
            String vPuntoPartidaLlegada = DBCaja.getPuntoPartidaLlegada();
              log.error("LLegada y Partida: "+vPuntoPartidaLlegada);
          String[] temp;         
          String delimiter = "�";        
          temp = vPuntoPartidaLlegada.split(delimiter);                
             for(int i=0 ; i < temp.length ; i++){
               log.debug(temp[i]+"\n");
               if(i==0)                   
                 VariablesCaja.vPuntoPartida = temp[i];
               if(i==1)
                 VariablesCaja.vPuntoLlegada =  temp[i];                   
             }
         if(VariablesCaja.vPuntoPartida.trim().equalsIgnoreCase("X") || 
            VariablesCaja.vPuntoLlegada.trim().equalsIgnoreCase("X")){
             VariablesCaja.vPuntoPartida = " ";   
             VariablesCaja.vPuntoLlegada = " ";
         }
          //VariablesCaja.vPuntoPartida = "abcdefghijklmnopqrstuvwxyzabcdefghijklm";
          //VariablesCaja.vPuntoPartida = "Av. 28 de Julio 1370 - Miraflores";
          //VariablesCaja.vPuntoLlegada = "1234567890123456780123456789012345678901234567890a2345678";
          //VariablesCaja.vPuntoLlegada = "Av. Proceres de la Independencia - San Juan de Lurigancho";    
                                       
         /*if(VariablesCaja.vPuntoLlegada.equalsIgnoreCase("X")){
             VariablesCaja.vPuntoLlegada = " ";      
         }   */  
              log.error("vPuntoPartida: "+ VariablesCaja.vPuntoPartida+ 
                        "  vPuntoLlegada: "+VariablesCaja.vPuntoLlegada);     
    String diaMesAno = pFechaBD.substring(0,10);
    String hora = pFechaBD.substring(11,19);
    vPrint.activateCondensed();          
              
    if(VariablesPtoVenta.vIndDirMatriz){
        vPrint.printLine(FarmaPRNUtility.llenarBlancos(46)+ VariablesPtoVenta.vDireccionMatriz ,true);
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(46) + VariablesPtoVenta.vDireccionMatriz ,true);
    }else{
        vPrint.printLine(" ",true);
        vPrintArchivo.printLine(" ",true);  
    }          
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(47) + "     " + pNumDocImpreso.trim() ,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(47) + "     " + pNumDocImpreso.trim() ,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),false);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),70),false);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2) + pDirImpreso.trim(),true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) + pDirImpreso.trim(),true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
   //JMIRANDA 22.08.2011 Cambio para verificar si imprime
   if(UtilityModuloVenta.getIndImprimeCorrelativo()){
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesCaja.vNumPedVta,false);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + VariablesCaja.vNumPedVta,false);
   }else{
       vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) + " ",false);
             vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) + " ",false);          
   }
       
    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(38) + diaMesAno + "       " + hora,true);
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(28) + VariablesCaja.vValTipoCambioPedido,true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(28) + VariablesCaja.vValTipoCambioPedido,true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    /*vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);*/
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    /** JMIRANDA 15.12.09 COMENTADO PARA AGREGAR PUNTO DE PARTIDA Y LLEGADA A LA FACTURA POR VTA INSTITUCIONAL */
    vPrint.printLine(FarmaPRNUtility.llenarBlancos(2)+
                     FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoPartida,40)+
                     "    "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoLlegada,50), true);
          vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2)+
                     FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoPartida,40)+
                     "    "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vPuntoLlegada,50), true);    
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true); 
    int linea = 0;
    double pMontoOld=0,pMontoNew=0,pMontoDescuento=0;
    for (int i=0; i<pDetalleComprobante.size(); i++) {
        vPrint.printLine("  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + " " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(15)).trim(),40) + " " +
                         //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),13) + "  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + FarmaPRNUtility.llenarBlancos(1) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),15) + " " +//nueva columna: numero de lote
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim(),10) + " " +//nueva columna: FECHA VENCIMIENTO
                         
                        //--se cambio la logica de que precio mostrar para ventas a QS en base a un precio
                        //  y esto se mostrara en un descuento en soles
                        //  dubilluz 07.05.2009
                        /*
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                         * */ 
                        getDetallePrecio(VariablesModuloVentas.vTipoPedido,pDetalleComprobante,i)
                         ,true
                        );
                        
        vPrintArchivo.printLine("  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + " " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(15)).trim(),40) + " " +
                         //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),13) + "  " +
                         FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + FarmaPRNUtility.llenarBlancos(1) +
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),15) + " " +//nueva columna: numero de lote
                         FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim(),10) + " " +//nueva columna: FECHA VENCIMIENTO
                         
                        //--se cambio la logica de que precio mostrar para ventas a QS en base a un precio
                        //  y esto se mostrara en un descuento en soles
                        //  dubilluz 07.05.2009
                        /*
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                        FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                         * */ 
                        getDetallePrecio(VariablesModuloVentas.vTipoPedido,pDetalleComprobante,i)
                         ,true
                        );
                       
        if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL))
        {
           pMontoOld += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,5));
           pMontoNew += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,18));
        }      
        
      linea += 1;
    }

    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        linea++;
    }
    

    
      //MODIFICADO POR DVELIZ 13.10.08
      //for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
    //  for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
    //for (int k=0; k<=10; k++)  vPrint.printLine(" ",true);
    //MODIFICADO POR JMIRANDA 16.12.09        
    // TOTAL DE LINEAS - LINEAS IMPRESAS = LINEAS EN BLANCO
    //ConstantsPtoVenta.TOTAL_LINEAS_FACTURA_GUIA
         for (int z=0; z< (ConstantsPtoVenta.TOTAL_LINEAS_FACTURA_GUIA-linea);z++)
              vPrint.printLine(" ",true); 
              log.debug("linea"+linea);    
    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
    {
        vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
        vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
    }
    if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
    {
                    VariablesModuloVentas.vTituloDelivery = "" ;
    }else
                    VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;

    //vPrint.printLine(" ",true);
        
    log.info("Viendo si da descuento...Institucional");
    if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL))
    {
        pMontoDescuento = pMontoNew - pMontoOld;
        if(pMontoDescuento>0){          
          log.info("Descuento S/. "+ pMontoDescuento);
          vPrint.printLine( FarmaPRNUtility.llenarBlancos(40)+FarmaPRNUtility.alinearDerecha("Total Dsto S/."+pMontoDescuento,80),true);
            vPrintArchivo.printLine( FarmaPRNUtility.llenarBlancos(40)+FarmaPRNUtility.alinearDerecha("Total Dsto S/."+pMontoDescuento,80),true);
        }
    }
              
    vPrint.printLine("  SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
          vPrintArchivo.printLine("  SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67),true);
    vPrint.printLine("  REDO:" + pValRedondeo +
                     "  CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                     "  CAJA:" + VariablesCaja.vNumCajaImpreso +
                     "  TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                     "  VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);
    vPrintArchivo.printLine("  REDO:" + pValRedondeo +
                           "  CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                           "  CAJA:" + VariablesCaja.vNumCajaImpreso +
                           "  TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                           "  VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso,true);                     
    vPrint.printLine("  Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery ,true);
          vPrintArchivo.printLine("  Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery ,true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
    vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                        vPrintArchivo.printLine(" ",true);
    /*
              if(!VariablesCaja.vImprimeFideicomizo){
          vPrintArchivo.printLine(" ",true);
                  vPrint.printLine(" ",true);
                  vPrint.printLine(" ",true);
                        vPrintArchivo.printLine(" ",true);
              }*/
    vPrint.printLine("     " +
                     FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(5) +
                     FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(45) +
                     FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(15) +
                     FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(17) +
                     FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
    vPrintArchivo.printLine("     " +
                           FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(5) +
                           FarmaPRNUtility.alinearDerecha(pValTotalDcto,10) + FarmaPRNUtility.llenarBlancos(45) +
                           FarmaPRNUtility.alinearDerecha(pValTotalAfecto,10) + FarmaPRNUtility.llenarBlancos(15) +
                           FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(17) +
                           FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
          /*dubilluz 2011.09.16*/
          if(VariablesCaja.vImprimeFideicomizo){
              vPrint.printLine("",true);
              vPrint.printLine("",true);
              String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
              if(lineas.length>0){
                  for(int i=0;i<lineas.length;i++){
                  vPrint.printLine(""+lineas[i].trim(),true);
                  vPrintArchivo.printLine(""+lineas[i].trim(),true);
                  }
              }
              else{
              vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
              vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
              }
          }
          /*FIN dubilluz 2011.09.16*/          
    vPrint.endPrintService();
          vPrintArchivo.endPrintService();
          
          //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
          DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
          log.debug("Guardando fecha impresion cobro..."+pNumComprobante);           
    
    log.info("Fin al imprimir la factura-guia: " + pNumComprobante);
          VariablesCaja.vEstadoSinComprobanteImpreso="N";               
      }
        catch(Exception e){
                  VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                  log.info("**** Fecha :"+ pFechaBD);
                  log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                  log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                  log.info("**** IP :" + FarmaVariables.vIpPc);
                  log.info("Error al imprimir la factura-guia: "+e);
                  
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
              }         
        
    }
  }

  public static void obtieneInfoVendedor()
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCaja.obtenerInfoVendedor(myArray);
      if(myArray.size() == 0) return;
      VariablesCaja.vNomVendedorImpreso    = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCaja.vApePatVendedorImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
         } catch(SQLException sql){
      //log.error("",sql);
      log.error(null,sql);
    }
  }

    // RHERRERA : OBTIENE LOS SEC COMPROBANTE DE PAGO
    public static void obtieneAgrupaComprobante()
    
    {
      ArrayList myArray = new ArrayList();
      String secCompPago = "";
      VariablesCaja.vArrayList_SecCompPago = new ArrayList();


        try {
            DBCaja.obtieneInfoDetalleAgrupacion(myArray);
        } catch (SQLException e) {
            log.error("",e);
        }
        for(int j=0; j<myArray.size(); j++)
        {
          secCompPago = ((String)((ArrayList)myArray.get(j)).get(1)).trim();
          
          VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
        }
       
    } 


    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus){
        procesoImpresionComprobante(pJDialog, pObjectFocus,false);
        acumuCorre=0;//Incializamos el correlativo de numero de impresion
    }
    
    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus, boolean vIndImpresionAnulado)
    {
        String mensCons = "";
        try
        {
            String secImprLocal = "";
            String resultado = "";

            VariablesCaja.vImprimeFideicomizo = false;
            VariablesCaja.vCadenaFideicomizo = "";//getMensajeFideicomizo();
            
            if(VariablesCaja.vCadenaFideicomizo != null)
                VariablesCaja.vCadenaFideicomizo = VariablesCaja.vCadenaFideicomizo.trim();
            else
                VariablesCaja.vCadenaFideicomizo = "";
            
            if (VariablesCaja.vCadenaFideicomizo.length() > 0)
            {   VariablesCaja.vImprimeFideicomizo = true;
            }
            
            if(// DUBILLUZ 02.11.2015
                ConstantsModuloVenta.TIPO_COMP_BOLETA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
            {
                
                if(UtilityFactElectronica.isActivoFactElectronica()){
                    secImprLocal = DBFactElectronica.getObtieneSecBoleta_electronica(VariablesCaja.vNumPedVta);
                }
                else{
                    secImprLocal = DBCaja.getObtieneSecBoleta();                    
                }
            } 
            else
            if( // DUBILLUZ 25.05.2015
                //ConstantsVentas.TIPO_COMP_BOLETA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped) ||
                ConstantsModuloVenta.TIPO_COMP_TICKET.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
            {
                secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
            }
            else
                // DUBILLUZ 25.05.2016
                if(ConstantsModuloVenta.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
                {
                    if(UtilityFactElectronica.isActivoFactElectronica()){
                        secImprLocal = DBFactElectronica.getObtieneSecFac_electronica(VariablesCaja.vNumPedVta);
                    }
                    else{
                        secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                    }
                    
                }
            else if(
                    //ConstantsVentas.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped) ||
                ConstantsModuloVenta.TIPO_COMP_TICKET_FACT.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
            {
                if(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL.equalsIgnoreCase(VariablesModuloVentas.vTipoPedido))
                {
                    secImprLocal = obtieneSecImprVtaInstitucional(ConstantsModuloVenta.TIPO_COMP_FACTURA);
                    if ("".equalsIgnoreCase(secImprLocal))
                    {   secImprLocal = VariablesCaja.vSecImprLocalFactura;
                    }
                    else
                    {   FarmaUtility.showMessage(pJDialog,
                                                 "Se va a imprimir un documento del tipo Venta Institucional \n " +
                                                 "Cambie de hoja en la impresora de Reportes para proseguir",
                                                 pObjectFocus);
                    }
                }
                else
                {   secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                    //VariablesCaja.vSecImprLocalFactura;
                }
            }
            else if(ConstantsModuloVenta.TIPO_COMP_GUIA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
            {
                secImprLocal = VariablesCaja.vSecImprLocalGuia;
            }
            
            // DUBILLUZ 25.05.2015
            if(!secImprLocal.trim().equalsIgnoreCase("X"))
            {
            resultado = DBCaja.verificaComprobantePago(secImprLocal, VariablesCaja.vNumSecImpresionComprobantes);
            if(ConstantsCaja.RESULTADO_COMPROBANTE_NO_EXITOSO.equalsIgnoreCase(resultado))
            {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog,
                                         "El pedido fue Cobrado pero no se imprimi� Comprobante(s).\nIngrese a la opci�n de Reimpresi�n de Pedido.",
                                         pObjectFocus);
                return;
            }
            else
            {   FarmaUtility.liberarTransaccion();
            }

            }   
            // DUBILLUZ 25.05.2015            

            //cambiando el estado de pedido al estado C -- que es estado IMPRESO y COBRADO
            actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);

            if (VariablesModuloVentas.vEsPedidoDelivery)
            {   actualizarDatosDelivery(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
            }

            VariablesCaja.vNumCuponesImpresos = 0;
            int cantidadCupones = imprimeCupones(pJDialog);
            
            if (cantidadCupones > 0)
            {   VariablesCaja.vNumCuponesImpresos = cantidadCupones;
            }

            // DUBILLUZ 25.05.2015
            if(!secImprLocal.trim().equalsIgnoreCase("X"))
            //if( ConstantsVentas.TIPO_COMP_TICKET.equalsIgnoreCase(VariablesVentas.vTip_Comp_Ped))
            // solo se imprimiera TICKET el resto graba lo ingreado
            {
            

            ArrayList listaNumCompro = new ArrayList(VariablesCaja.vNumSecImpresionComprobantes);
            ArrayList lstSecCompPagoImprimir = new ArrayList();
            
            for (int i = 1; i <= VariablesCaja.vNumSecImpresionComprobantes; i++)
            {
                
                if((ConstantsModuloVenta.TIPO_COMP_BOLETA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped)||ConstantsModuloVenta.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
                    &&
                    UtilityFactElectronica.isActivoFactElectronica()) {
                    String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i - 1))).trim();
                    VariablesCaja.vNumCompImprimir  = DBFactElectronica.obtieneNumCompElectronico_ForUpdate(VariablesCaja.vNumPedVta, 
                                                                                                            secCompPago,
                                                                                                            secImprLocal);
                    
                    listaNumCompro.add(VariablesCaja.vNumCompImprimir);
                    
                    lstSecCompPagoImprimir.add(secCompPago);
                }
                else {
                    if(VariablesCaja.vNumCompBoletaFactura_Impr.trim().length()>0) {
                        log.info("se ingreso el que se va imprimir se graba con ese pedido");
                        //VariablesCaja.vNumCompImprimir = VariablesCaja.vNumCompBoletaFactura_Impr.trim();
                        VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus,
                                                                                      VariablesCaja.vNumCompBoletaFactura_Impr);
                    }                
                    else
                        VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus,
                                                                                      "N");
                        
                    
                    if (VariablesCaja.vNumCompImprimir == null ||
                        "".equalsIgnoreCase(VariablesCaja.vNumCompImprimir)) 
                    {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog,
                                                 "El pedido fue Cobrado pero no se pudo determinar el Numero de Comprobante. Verifique!!!",
                                                 pObjectFocus);
                        return;
                    }

                    //obteniendo el detalle del comprobante a imprimir
                    //el mensaje de error se encuentra dentro del mismo metodo, tb se hace rollback dentro del mismo metodo
                    if (!obtieneDetalleImprComp(pJDialog, String.valueOf(i), pObjectFocus))
                    {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog,
                                                 "El pedido fue Cobrado pero no se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                                 pObjectFocus);
                        return;
                    }

                    String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i - 1))).trim();

                    //obtiene el total del comprobante a imprimir
                    //muestra el mensaje de error dentro del mismo metodo, tb hace rollback dentro del mismo metodo
                    if (!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus))
                    {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog,
                                                 "El pedido fue Cobrado pero no se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                                 pObjectFocus);
                        return;
                    }

                    actualizaComprobanteImpreso(secCompPago, VariablesModuloVentas.vTip_Comp_Ped,VariablesCaja.vNumCompImprimir);
                    actualizaNumComp_Impresora(secImprLocal);

                    //agregando los numero de comprobantes
                    listaNumCompro.add(VariablesCaja.vNumCompImprimir);
                }
                
            }
            
            
            // Esto solo lo hara si es que existen comprobantes a imprimir
            // dubilluz 21.01.2013
            if(listaNumCompro.size()>0)
                FarmaUtility.aceptarTransaccion();

                if((ConstantsModuloVenta.TIPO_COMP_BOLETA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped)||ConstantsModuloVenta.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
                    &&
                    UtilityFactElectronica.isActivoFactElectronica()) {
                    
                    // imprime comprobantes
                    try {
                        DBFactElectronica.pOperaDatosComprobanteElectronico(VariablesCaja.vNumPedVta);
                        FarmaUtility.aceptarTransaccion();
                    } catch (Exception sqle) {
                        sqle.printStackTrace();
                        FarmaUtility.liberarTransaccion();
                    }
                    //VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                    for(int i=0;i<listaNumCompro.size();i++){
                        VariablesCaja.vEstadoSinComprobanteImpreso = 
                            UtilityFactElectronica.imp_comp_electronicos_pedido(VariablesCaja.vNumPedVta,
                                                                                VariablesModuloVentas.vTip_Comp_Ped,
                                                                                listaNumCompro.get(i).toString().trim(),
                                                                                lstSecCompPagoImprimir.get(i).toString().trim()
                                                                                );    
                    }
                    
                    
                    /*FarmaUtility.showMessage(pJDialog,
                                             "Pedido se ha cobrado correctemente y se va imprimir luego",
                                             pObjectFocus);*/
                }
                else{
                    /// inicio

                    for (int i = 0; i < listaNumCompro.size(); i++)
                    {
                        if (!obtieneDetalleImprComp(pJDialog, String.valueOf(i + 1), pObjectFocus))
                        {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(pJDialog,
                                                     "No se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",
                                                     pObjectFocus);
                            return;
                        }

                        String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i))).trim();
                        if (!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus))
                        {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(pJDialog,
                                                     "No se pudo determinar los Totales del Comprobante. Verifique!!!.",
                                                     pObjectFocus);
                            return;
                        }

                        VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());

                        if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)) {
                            VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
                        }

                        if (VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET) 
                            // NO VALIDARA PARA BOLETA DUBILLUZ 25.05.2015
                            /*||
                            VariablesVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_BOLETA)
                            */
                            )
                        {
                            if (!validaImpresioPorIP(FarmaVariables.vIpPc, VariablesModuloVentas.vTip_Comp_Ped, 
                                                     pJDialog,
                                                     pObjectFocus))
                            {
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(pJDialog, "La IP no cuenta con una impresora asignada. Verifique!!!.",
                                                         pObjectFocus);
                                return;
                            }
                        }
                            int valor=i;
                        imprimeComprobantePago(pJDialog,
                                               VariablesCaja.vArrayList_DetalleImpr,
                                               VariablesCaja.vArrayList_TotalesComp, VariablesModuloVentas.vTip_Comp_Ped,
                                               listaNumCompro.get(i).toString(),
                                               vIndImpresionAnulado,
                                               valor);
                        FarmaUtility.aceptarTransaccion();
                    }
                    // DUBILLUZ 27.05.2015
                    /// fin 
                }


            }
            else
                {
                                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                                VariablesCaja.vIndPedidoCobrado = true;
                                FarmaUtility.aceptarTransaccion();
                            }

            if (VariablesModuloVentas.vEsPedidoDelivery){
                imprimeDatosDeliveryLocal(pJDialog, VariablesCaja.vNumPedVta);
            }

            boolean consejo = imprimeConsejos(pJDialog);
            if (consejo) {
                mensCons = "\nRecoger Consejo.";
            }
            imprimeMensajeCampana(pJDialog, VariablesModuloVentas.vNum_Ped_Vta);

            if (VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase("S"))
            {
                String vNumPedDely = DBCaja.obtieneNumPedDelivery(VariablesCaja.vNumPedVta);
                imprimeDatosDelivery(pJDialog, vNumPedDely);
            }

            String DniClienteImpRecord = obtenerDniPedidoAcumulaVenta(VariablesCaja.vNumPedVta);

            if (DniClienteImpRecord.trim().length() > 0)
            {
                VariablesCaja.vIndLineaMatriz = FarmaConstants.INDICADOR_N;
                //??? VariablesCaja.vIndLineaMatriz siempre va a tener el valor de FarmaConstants.INDICADOR_N en este punto
                if (FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesCaja.vIndLineaMatriz))
                {   
                    imprimeUnidRestCampXCliente(pJDialog, DniClienteImpRecord.trim(), VariablesCaja.vNumPedVta);
                }
                else
                {   imprimirUnidAcumCampXCliente(pJDialog, DniClienteImpRecord.trim(), VariablesCaja.vNumPedVta);
                }
            }

            //ERIOS 15.10.2013 Impresion de ticket anulado
            if(vIndImpresionAnulado){
                FarmaUtility.showMessage(pJDialog, "�Pedido Anulado!", pObjectFocus);
                return;
            }
            
            if (!VariablesCaja.vIndPedidoConProdVirtual)
            {
                if (VariablesCaja.vEstadoSinComprobanteImpreso.equalsIgnoreCase("N"))
                {
                    if (cantidadCupones > 0)
                    {
                        FarmaUtility.showMessage(pJDialog, "Pedido Cobrado con �xito. \n" +
                                "Comprobantes Impresos con �xito\n" +
                                "Favor de recoger " + cantidadCupones + " cupones" + mensCons, pObjectFocus);
                    }
                    else
                    {
                        FarmaUtility.showMessage(pJDialog, "Pedido Cobrado con �xito. \n" +
                                "Comprobantes Impresos con �xito " + mensCons, pObjectFocus);
                    }
                }
                else
                {
                    if (cantidadCupones > 0)
                    {   FarmaUtility.showMessage(pJDialog,
                                                 "Pedido Cobrado con �xito." + "\nFavor de recoger " + cantidadCupones +
                                                 " cupones" + mensCons +
                                                 "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora " +
                                                 //VariablesCaja.vRutaImpresora +
                                                 "\nReimprima Comprobante, Correlativo :" + VariablesCaja.vNumPedVta,
                                                 pObjectFocus);
                    }
                    else
                    {
                        FarmaUtility.showMessage(pJDialog,
                                                 "Pedido Cobrado con �xito." + "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora " +
                                                 //VariablesCaja.vRutaImpresora +
                                                 "\nReimprima Comprobante, Correlativo :" + VariablesCaja.vNumPedVta,
                                                 pObjectFocus);
                    }
                }
            }
            
            if(!"".equalsIgnoreCase(VariablesRecetario.strCodigoRecetario))
            {
                imprimeContraseniaRecetario(VariablesCaja.vNumPedVta);
                VariablesRecetario.strCodigoRecetario = "";
            }

            VariablesCaja.vNumCuponesImpresos = 0;
            mensCons = "";

        }
        catch (SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(pJDialog, 
                                    "Error en BD al Imprimir los Comprobantes del Pedido.\n" +
                                    sql, 
                                    pObjectFocus);
            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta_Anul);
        }
        catch (Exception e)
        {
            log.error("",e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(pJDialog, 
                                    "Error en la Aplicacion al Imprimir los Comprobantes del Pedido.\n" +
                                    e,
                                    pObjectFocus);
            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta_Anul);
        }
    }

    private static String obtieneSecImprVtaInstitucional(String pTipComp)  
  {
    
    String secImprVtaInst = "";
    ArrayList myArray = new ArrayList();
    
    try
    {
      DBCaja.obtieneSecuenciaImprVtaInstitucional(myArray, pTipComp);
      if(myArray.size() == 0 || myArray.size() > 1)
      {
        return secImprVtaInst;
      }
      secImprVtaInst = FarmaUtility.getValueFieldArrayList(myArray,0,0);
      log.debug("secImprVtaInst : " + secImprVtaInst);
      return secImprVtaInst;
    
    } catch(SQLException sql)
    {
      secImprVtaInst = "";
      //log.error("",sql);
      
      log.error(null,sql);
        //JMIRANDA 23/07/09 ENVIA ERROR X CORREO
        enviaErrorCorreoPorDB(sql.toString(),null);  
      return secImprVtaInst;
      
    }

  }

  private static String obtieneNumCompPago_ForUpdate(JDialog pJDialog, String pSecImprLocal, Object pObjectFocus,
                                                     String pNumCompIngresado)
  {
    String numCompPago = "";
    ArrayList myArray = new ArrayList();
    try
    {
      /*if(VariablesCaja.vNumPedVta_Anul.trim().length()>1)
      DBCaja.obtieneNumCompTip_ForUpdate(myArray, pSecImprLocal,VariablesCaja.vNumPedVta_Anul);
      else*/
      DBCaja.obtieneNumComp_ForUpdate(myArray, pSecImprLocal,pNumCompIngresado,VariablesCaja.vNumPedVta);
      
        log.debug("VariablesVentas.vTip_Comp_Ped JCORTEZ : " + VariablesModuloVentas.vTip_Comp_Ped);
        log.debug("VariablesCaja.vNumPedVta JCORTEZ : " + VariablesCaja.vNumPedVta);
        
      if(myArray.size() == 0)
      {
        return numCompPago;
      }
      numCompPago = ((String)((ArrayList)myArray.get(0)).get(0)).trim() + "" +
                    ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      log.debug("numCompPago : " + numCompPago);
      return numCompPago;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      numCompPago = "";
      FarmaUtility.showMessage(pJDialog,"Error al validar Agrupacion de Comprobante.",pObjectFocus);
      //log.error("",sql);
      log.error(null,sql);
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                   enviaErrorCorreoPorDB(sql.toString().toString(),null);
      return numCompPago;
    }
  }

  private static boolean obtieneDetalleImprComp(JDialog pJDialog, String pSecGrupoImpr, Object pObjectFocus)
  {
    VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
    boolean  valor = true;
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    try
    {
      DBCaja.obtieneInfoDetalleImpresion(VariablesCaja.vArrayList_DetalleImpr, pSecGrupoImpr);
      if(VariablesCaja.vArrayList_DetalleImpr.size() == 0)
      {
    	//JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
    	FarmaUtility.liberarTransaccion();//JCALLO 19.12.2008
        FarmaUtility.showMessage(pJDialog,
                                 "No se pudo determinar el detalle del Pedido. Verifique!!!.",
                                 pObjectFocus);
        valor = false;
      }
      log.info("VariablesCaja.vArrayList_DetalleImpr : " + VariablesCaja.vArrayList_DetalleImpr.size());
      valor = true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
      log.info("Error al obtener Detalle de Impresion de Comprobante imprimir");
      log.error(null,sql);
      valor =false;
      
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
           enviaErrorCorreoPorDB(sql.toString(),null);     
    }
    
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 4: Det.Comp Pago:"+(tmpT2 - tmpT1)+" milisegundos");
          
    
    return valor;
  } 

  public static boolean obtieneTotalesComprobante(JDialog pJDialog, String pSecCompPago, Object pObjectFocus)
  {
    VariablesCaja.vArrayList_TotalesComp = new ArrayList();
    boolean valor = false;
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    try
    {
      DBCaja.obtieneInfoTotalesComprobante(VariablesCaja.vArrayList_TotalesComp, pSecCompPago);
      if(VariablesCaja.vArrayList_TotalesComp.size() == 0)
      {
    	//JCALLO 19.12.2008 , debido a que no se hacia rollback si no encontraba el detalle del pedido
    	FarmaUtility.liberarTransaccion();//JCALLO 19.12.2008
        FarmaUtility.showMessage(pJDialog,"No se pudo determinar los Totales del Comprobante. Verifique!!!.",pObjectFocus);
        valor = false;
      }
      log.debug("VariablesCaja.vArrayList_TotalesComp : " + VariablesCaja.vArrayList_TotalesComp.size());
      valor = true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al obtener Totales de Comprobante.",pObjectFocus);
      log.error(null,sql);
      valor = false;
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
          enviaErrorCorreoPorDB(sql.toString(),null);
      
    }
    
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 5:Calculo de Total de los Comprobantes:"+(tmpT2 - tmpT1)+" milisegundos");
    
    return valor;
      
  }

  public static void actualizaComprobanteImpreso(String pSecCompPago,
                                                  String pTipCompPago,
                                                  String pNumCompPago) throws Exception
  {
      long tmpT1,tmpT2;
      boolean lanzaError = false;
      tmpT1 = System.currentTimeMillis();
        try {
            DBCaja.actualizaComprobanteImpreso(pSecCompPago, pTipCompPago, pNumCompPago);
        } catch (SQLException sql) {
            
            lanzaError = true;
            //log.error("",sql);
            log.debug("Error de BD " + sql.getMessage());
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : ");
            log.error(null, sql);

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sql.toString(), VariablesCaja.vNumPedVta);

        } catch (Exception e) {
            lanzaError = true;
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumCompPago);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE ACTUALIZAR COMPROBANTE IMPRESO : " + e);

            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(e.toString(), VariablesCaja.vNumPedVta);
        }
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 6: Actualiza Comprobante Impreso:"+(tmpT2 - tmpT1)+" milisegundos");
      
      // No pudo hacer el UPDATE y Se va Forzar que Salga ERROR
      // dubilluz 21.01.2014
      if(lanzaError){
            throw new Exception("Error Al Actualizar el Comprobante Impreso");
        }
      

  }

  private static void actualizaNumComp_Impresora(String pSecImprLocal) throws SQLException
  {
    long tmpT1,tmpT2;
    tmpT1 = System.currentTimeMillis();
    DBCaja.actualizaNumComp_Impresora(pSecImprLocal);
    tmpT2 = System.currentTimeMillis();
    log.debug("Tiempo 7: Actualiza Comprobante Impreso:"+(tmpT2 - tmpT1)+" milisegundos");
  }

  private static String obtieneRutaImpresora(String pSecImprLocal) throws SQLException
  {
    return DBCaja.obtieneRutaImpresoraVenta(pSecImprLocal);
  }

  public static void actualizaEstadoPedido(String pNumPedVta, String pEstPedVta) throws SQLException
  {
    DBCaja.actualizaEstadoPedido(pNumPedVta, pEstPedVta);
  }
  
  /**
   * @AUTHOR  JCORTEZ
   * @SINCE  07.08.09
   * */
    private static void actualizarDatosDelivery(String pNumPedVta, String pEstPedVta) throws SQLException
    {
    
      DBCaja.actualizaDatosDelivery(pNumPedVta, pEstPedVta,
                    VariablesDelivery.vCodCli,VariablesDelivery.vNombreCliente,
                    VariablesDelivery.vNumeroTelefonoCabecera,VariablesDelivery.vDireccion,
                    VariablesDelivery.vNumeroDocumento);
    }

  public static boolean verificaEstadoPedido(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar, Object pObjectFocus)
  {
    String estadoPed = "";
    estadoPed = obtieneEstadoPedido(pJDialog, pNumPedVta);
    log.debug("Estado de Pedido:" + estadoPed);
    if(estadoAEvaluar.equalsIgnoreCase(estadoPed)) return true;
    //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
    //FarmaUtility.liberarTransaccion();
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_SIN_COMPROBANTE_IMPRESO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido fue Cobrado pero no imprimio Comprobante(s).\nIngrese a la opcion de Reimpresion de Pedido.", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE))
    {
      FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!", pObjectFocus);
      return false;
    }
    if(estadoPed.equalsIgnoreCase(""))
    {
      FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!", pObjectFocus);
      return false;
    }
    return true;
  }

  public static void obtieneInfoCajero(String pSecMovCaja)
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCaja.obtenerInfoCajero(myArray, pSecMovCaja);
      if(myArray.size() == 0) return;
      VariablesCaja.vNumCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCaja.vNumTurnoCajaImpreso = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesCaja.vNomCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      VariablesCaja.vApePatCajeroImpreso = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
    } catch(SQLException sql){
      //log.error("",sql);
      log.error(null,sql);
    }
  }

  public static boolean validaAgrupacionComprobante(JDialog pJDialog, Object pObjectFocus)
  {
    ArrayList myArray = new ArrayList();
    String secCompPago = "";
    VariablesCaja.vArrayList_SecCompPago = new ArrayList();
    try
    {
      DBCaja.obtieneInfoDetalleAgrupacion(myArray);
      if(myArray.size() == 0)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"No se pudo determinar la agrupacion de Impresion",pObjectFocus);
        return false;
      }
	  //LLEIVA Se comenta
      /*if(myArray.size() != VariablesCaja.vNumSecImpresionComprobantes)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Hubo error al agrupar el detalle de Impresion.",pObjectFocus);
        return false;
      }*/
      for(int j=0; j<myArray.size(); j++)
      {
        secCompPago = ((String)((ArrayList)myArray.get(j)).get(1)).trim();
        if(secCompPago.equalsIgnoreCase(""))
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,"Hubo error al obtener el Secuencial del Comprobante.",pObjectFocus);
          return false;
        }
        VariablesCaja.vArrayList_SecCompPago.add(secCompPago);
      }
      log.debug("VariablesCaja.vArrayList_SecCompPago : " + VariablesCaja.vArrayList_SecCompPago);
      return true;
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      FarmaUtility.showMessage(pJDialog,"Error al validar Agrupacion de Comprobante.",pObjectFocus);
      //log.error("",sql);
      log.error(null,sql);
      return false;
    }
  }

    //procesoAsignacionComprobante
    public static void procesoAsignacionComprobante(JDialog pJDialog, Object pObjectFocus) throws Exception
    {
        String secImprLocal = "";
        secImprLocal = ConstantsCaja.SEC_IMPR_LOCAL_NOTA_CREDITO;
        log.debug("secImprLocal procesoAsignacionComprobante: " + secImprLocal);
        
        for(int i=1; i<=VariablesCaja.vNumSecImpresionComprobantes; i++)
        {
            VariablesCaja.vNumCompImprimir = obtieneNumCompPago_ForUpdate(pJDialog, secImprLocal, pObjectFocus,"N");
            if("".equalsIgnoreCase(VariablesCaja.vNumCompImprimir))
            {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(pJDialog, 
                                         "No se pudo determinar el Numero de Comprobante. Verifique!!!", 
                                         pObjectFocus);
                return;
            }
            
            //if(!obtieneDetalleImprComp(pJDialog, String.valueOf(i), pObjectFocus)) return;
            String secCompPago = ((String)(VariablesCaja.vArrayList_SecCompPago.get(i-1))).trim();
            log.debug("secCompPago : " + secCompPago);
            //if(!obtieneTotalesComprobante(pJDialog, secCompPago, pObjectFocus)) return;
            //VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);
            actualizaComprobanteImpreso(secCompPago, VariablesModuloVentas.vTip_Comp_Ped, VariablesCaja.vNumCompImprimir);
            actualizaNumComp_Impresora(secImprLocal);
        }
        actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);
        //FarmaUtility.aceptarTransaccion();
        //FarmaUtility.showMessage(pJDialog, "Comprobantes Impresos con Exito",pObjectFocus);
    }

    public static void verificaPedidosPendientes(JDialog pJDialog)
    {
        int cantPedidosPendientes = 0;
        try
        {
            cantPedidosPendientes = DBCaja.cantidadPedidosPendAnulMasivo();
            if(cantPedidosPendientes == 0)
                return;
            FarmaUtility.showMessage(pJDialog, 
                                     "Existen Pedidos Pendientes de cobro con mas de " + 
                                     FarmaVariables.vMinutosPedidosPendientes + 
                                     " minutos.\nEstos pedidos seran anulados.", 
                                     null);
            //DBCaja.anulaPedidosPendientesMasivo(); --antes - ASOSA, 12.07.2010
            DBCaja.anulaPedidosPendientesMasivo_02(); //ASOSA, 12.07.2010
            FarmaUtility.aceptarTransaccion();
        }
        catch(SQLException sql)
        {
            FarmaUtility.liberarTransaccion();
            //log.error("",sql);
            log.error(null,sql);
        }
    }

  public static void imprimePrueba() throws Exception
  {
    FarmaPrintService vPrint = new FarmaPrintService(10,VariablesCaja.vRutaImpresora,false);
    //FarmaPrintService vPrint = new FarmaPrintService(10,"C:\\ImpresoraReporte.txt",false);
    if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");

    String pFechaBaseDatos = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);
    vPrint.activateCondensed();
    String dia = pFechaBaseDatos.substring(0,2);
    String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBaseDatos.substring(3,5)));
    String ano = pFechaBaseDatos.substring(6,10);
    String hora = pFechaBaseDatos.substring(11,19);

    vPrint.activateCondensed();
    vPrint.printLine("",true);
    vPrint.printLine("LOCAL: " + FarmaVariables.vDescCortaLocal,true);
    vPrint.printLine("FECHA: " + dia + " " + mesLetra + " " + ano + "  " + hora,true);
    vPrint.printLine("IMPRESORA - SERIE: " + VariablesCaja.vTipComp,true);
    vPrint.printLine("USUARIO: " + FarmaVariables.vPatUsu + " " + FarmaVariables.vMatUsu + ", " + FarmaVariables.vNomUsu,true);
    vPrint.printLine("CAJA - TURNO: " + VariablesCaja.vNumCaja + " - " + VariablesCaja.vNumTurnoCaja,true);
    vPrint.printLine(" ",true);
    vPrint.deactivateCondensed();

    vPrint.printCondensed("        PRUEBA DE IMPRESORA        ",true);

    vPrint.endPrintService();
  }

  /**
   * Metodo que procesa la venta de tarjetas virtuales.
   * Este metodo se conectara con el proveedor de servicios a traves de su interfase.
   * Creado x LMesia 12/01/2007
   */
  public static void procesaVentaProductoVirtual(JDialog pDialog, Object pObjectFocus) throws SQLException, Exception
  {
    //colocaVariablesVirtuales();
    colocaVariablesVirtualesBprepaid();
    if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA))
    {
      log.debug("entro a venta de recarga virtual");
      obtieneInfoRecargaVirtualBprepaid();
      //obtieneInfoRecargaVirtual();
      if(!validaHostPuertoProveedor())
      {
        limpiaInfoTransaccionTarjVirtuales();
        throw new Exception("Error al obtener host y puerto de Navsat para recarga automatica");
      }
      /* 27.09.2007 ERIOS Se cambio de proveedor (Brightstar). */
      //ventaRecargaVirtualBprepaid();
      //ventaRecargaVirtual();
      /* 14.12.2007 ERIOS Se utliza el metodo generico. */
      ventaRecargaVirtualTX();
      //captura el error de conexion cuando los valores son nulos
      //16.11.2007  dubilluz modificado
      if(VariablesVirtual.respuestaTXBean.getCodigoRespuesta()==null)
      { 
        throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
      }
      //if(VariablesVirtual.respuestaTXBean.getCodigoRespuesta()!=null)
      colocaInfoRecargaVirtualBprepaid();
      
      //Mostramos el mensaje de respuesta del proveedor
      //05.12.2007  dubilluz  modificacion
      if(!validaCodigoRespuestaTransaccion())
      {
         throw new Exception("Error al realizar la transacci�n con el proveedor." + "@"+
                                         VariablesVirtual.respuestaTXBean.getCodigoRespuesta() + " - " + VariablesVirtual.respuestaTXBean.getDescripcion());
      }    

      
    } else if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA))
    {
      log.debug("entro a venta de tarjeta virtual");
      obtieneInfoRecargaVirtualBprepaid();
      //obtieneInfoTarjetaVirtual();
      if(!validaHostPuertoProveedor())
      {
        limpiaInfoTransaccionTarjVirtuales();
        throw new Exception("Error al obtener host y puerto de Navsat para tarjeta virtual");
      }
      /* 27.09.2007 ERIOS Se cambio de proveedor (Brightstar). */
      //ventaTarjetaVirtualBprepaid();
      //ventaTarjetaVirtual();
      /* 14.12.2007 ERIOS Se utiliza el metodo generico. */
      ventaTarjetaVirtualTX();
      colocaInfoTransaccionVirtualBprepaid();
    }
    //colocaInfoTransaccionVirtualBprepaid();
    //colocaInfoTransaccionVitual();
    actualizaInfoPedidoVirtual(pDialog);
    //throw new Exception("NO Error al procesar el pedido virtual - Para prueba");
}

  /**
   * Valida el codigo de respuesta
   * @author dubilluz
   * @since  05.12.2007
   */
   private static boolean validaCodigoRespuestaTransaccion()
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

  
  /**
   *
   * @throws SQLException
   * @deprecated
   */
  private static void colocaVariablesVirtuales() throws SQLException
  {
    /*VariablesVirtual.vCodigoComercio = FarmaUtility.completeWithSymbol(FarmaVariables.vNuRucCia.substring(0,6), 9, "0", "I") + FarmaVariables.vCodLocal;

    //VariablesVirtual.vTipoTarjeta = FarmaUtility.completeWithSymbol(VariablesCaja.vCodProd, 8, "0", "I");//preguntar que info va aca
    VariablesVirtual.vTipoTarjeta = VariablesCaja.vTipoTarjeta;
    VariablesVirtual.vMonto = VariablesCaja.vPrecioProdVirtual.substring(0, VariablesCaja.vPrecioProdVirtual.indexOf(".")) +
                              VariablesCaja.vPrecioProdVirtual.substring(VariablesCaja.vPrecioProdVirtual.indexOf(".") + 1);
    VariablesVirtual.vNumTerminal = FarmaUtility.completeWithSymbol(FarmaVariables.vCodLocal, 4, "0", "I");
    VariablesVirtual.vNumSerie = FarmaVariables.vNuRucCia.substring(0,5) + FarmaVariables.vCodLocal;
    VariablesVirtual.vNumTrace = obtieneNumeroTrace();//preguntar que info va aca
    VariablesVirtual.vNumeroCelular = VariablesCaja.vNumeroCelular;
    VariablesVirtual.vCodigoProv = VariablesCaja.vCodigoProv;
    VariablesVirtual.vNumTraceOriginal = VariablesCaja.vNumeroTraceOriginal;
    VariablesVirtual.vCodAprobacionOriginal = VariablesCaja.vCodAprobacionOriginal;
    log.debug("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
    log.debug("VariablesVirtual.vTipoTarjeta : " + VariablesVirtual.vTipoTarjeta);
    log.debug("VariablesVirtual.vMonto : " + VariablesVirtual.vMonto);
    log.debug("VariablesVirtual.vNumTerminal : " + VariablesVirtual.vNumTerminal);
    log.debug("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    log.debug("VariablesVirtual.vNumTrace : " + VariablesVirtual.vNumTrace);
    log.debug("VariablesVirtual.vNumeroCelular : " + VariablesVirtual.vNumeroCelular);
    log.debug("VariablesVirtual.vCodigoProv : " + VariablesVirtual.vCodigoProv);
    log.debug("VariablesVirtual.vNumTraceOriginal : " + VariablesVirtual.vNumTraceOriginal);
    log.debug("VariablesVirtual.vCodAprobacionOriginal : " + VariablesVirtual.vCodAprobacionOriginal);*/
  }

  /**
   * Guarda los variables para la comunicacion con el proveedor (Brightstar).
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  public static void colocaVariablesVirtualesBprepaid() throws SQLException
  {

    //VariablesVirtual.vCodigoComercio = "MFARMATEST";
    VariablesVirtual.vTipoTarjeta = VariablesCaja.vTipoTarjeta;
    VariablesVirtual.vMonto = VariablesCaja.vPrecioProdVirtual;
    VariablesVirtual.vNumTerminal = FarmaUtility.completeWithSymbol(FarmaVariables.vCodLocal, 13, " ", "D");
    //VariablesVirtual.vNumSerie = "LIM";
    VariablesVirtual.vNumTrace = obtieneNumeroTraceBprepaid();
    VariablesVirtual.vNumeroCelular = VariablesCaja.vNumeroCelular;
    VariablesVirtual.vCodigoProv = FarmaUtility.completeWithSymbol(VariablesCaja.vCodigoProv,10," ","D");
    VariablesVirtual.vNumTraceOriginal = VariablesCaja.vNumeroTraceOriginal;
    VariablesVirtual.vCodAprobacionOriginal = VariablesCaja.vCodAprobacionOriginal;
    VariablesVirtual.vFechaTX = VariablesCaja.vFechaTX;
    VariablesVirtual.vHoraTX = VariablesCaja.vHoraTX;
    //log.debug("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
    log.debug("VariablesVirtual.vTipoTarjeta : " + VariablesVirtual.vTipoTarjeta);
    log.debug("VariablesVirtual.vMonto : " + VariablesVirtual.vMonto);
    log.debug("VariablesVirtual.vNumTerminal : " + VariablesVirtual.vNumTerminal);
    //log.debug("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    log.debug("VariablesVirtual.vNumTrace : " + VariablesVirtual.vNumTrace);
    log.debug("VariablesVirtual.vNumeroCelular : " + VariablesVirtual.vNumeroCelular);
    log.debug("VariablesVirtual.vCodigoProv : " + VariablesVirtual.vCodigoProv);
    log.debug("VariablesVirtual.vNumTraceOriginal : " + VariablesVirtual.vNumTraceOriginal);
    log.debug("VariablesVirtual.vCodAprobacionOriginal : " + VariablesVirtual.vCodAprobacionOriginal);
    log.debug("VariablesVirtual.vFechaTX : " + VariablesVirtual.vFechaTX);
    log.debug("VariablesVirtual.vHoraTX : " + VariablesVirtual.vHoraTX);
  }

  /**
   *
   * @return
   * @throws SQLException
   * @deprecated
   */
  private static String obtieneNumeroTrace() throws SQLException {
    String numTrace = "";
    numTrace = DBCaja.obtieneSecNumeraTrace(4);
    return numTrace;
  }

  /**
   * Obtiene el secuencial de mensajes Bprepaid.
   * @return System Trace
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static String obtieneNumeroTraceBprepaid() throws SQLException {
    String numTrace = "";
    numTrace = FarmaVariables.vCodLocal+DBCaja.obtieneSecNumeraTrace(3);
    return numTrace;
  }

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void ventaTarjetaVirtual() throws Exception
  {

    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());
    log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta());
    log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaNavSatBean.getCodigoPIN());*/
  }

  /**
   * Se encarga de realizar la venta de una Tarjeta Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void ventaTarjetaVirtualBprepaid() throws Exception
  {

    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    log.debug("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    log.debug("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/
  }

/*  private static void colocaInfoTransaccionNavsat() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaNavSatBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaNavSatBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaNavSatBean.getCodigoPIN();
  }
*/

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void colocaInfoTransaccionVitual() throws Exception
  {
    /*VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaNavSatBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaNavSatBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaNavSatBean.getCodigoPIN();*/
  }

  /**
   * Guarda la respuesta desde el TXBean
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static void colocaInfoTransaccionVirtualBprepaid() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = VariablesVirtual.respuestaTXBean.getNumeroTarjeta();
    VariablesVirtual.vNumeroPin = VariablesVirtual.respuestaTXBean.getCodigoPIN();
    VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
    VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
  }

  /**
   * Guarda la respuesta desde el TXBean de Recarga.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 28.09.2007
   */
  private static void colocaInfoRecargaVirtualBprepaid() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = VariablesVirtual.respuestaTXBean.getCodigoRespuesta();
    VariablesVirtual.vDescripcionRespuesta = VariablesVirtual.respuestaTXBean.getDescripcion();
    VariablesVirtual.vNumTrace = VariablesVirtual.respuestaTXBean.getNumeroTrace();
    VariablesVirtual.vCodigoAprobacion = VariablesVirtual.respuestaTXBean.getCodigoAprobacion();
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vFechaTX = VariablesVirtual.respuestaTXBean.getFechaTX();
    VariablesVirtual.vHoraTX = VariablesVirtual.respuestaTXBean.getHoraTX();
    VariablesVirtual.vDatosImprimir = VariablesVirtual.respuestaTXBean.getDatosImprimir();
   
  }

  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void ventaRecargaVirtual() throws Exception
  {
    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                          VariablesVirtual.vNumeroCelular,
                                                                          VariablesVirtual.vCodigoProv,
                                                                          VariablesVirtual.vMonto,
                                                                          VariablesVirtual.vNumTerminal,
                                                                          VariablesVirtual.vNumSerie,
                                                                          VariablesVirtual.vNumTrace,
                                                                          VariablesVirtual.vIPHost,
                                                                          VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());
    log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTarjeta());
    log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaNavSatBean.getCodigoPIN());*/
  }

  /**
   * Se encarga de realizar la venta de una Recarga Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void ventaRecargaVirtualBprepaid() throws Exception
  {
    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                          VariablesVirtual.vNumeroCelular,
                                                                          VariablesVirtual.vCodigoProv,
                                                                          VariablesVirtual.vMonto,
                                                                          VariablesVirtual.vNumTerminal,
                                                                          VariablesVirtual.vNumSerie,
                                                                          VariablesVirtual.vNumTrace,
                                                                          VariablesVirtual.vIPHost,
                                                                          VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    log.debug("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    log.debug("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/

  }
  
  public static void actualizaInfoPedidoVirtual(JDialog pDialog) throws Exception
  { 
    try{
    DBCaja.actualizaInfoPedidoVirtual();
    }
    /*catch(SQLException sql){
     //log.debug("Se cayo !!!!!!***");
     //new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
     //FarmaUtility.showMessage(pDialog, "Hubo un error con la conexion. Intentarlo mas tarde.", null);
     throw new Exception("Error en base datos:" + sql);
    }*/
    catch(Exception ex){
     //log.debug("Se cayo !!!!!!***");
      log.error(null,ex);
     //new Exception("Hubo un error con la conexion. Intentarlo mas tarde.");
     //FarmaUtility.showMessage(pDialog, "Hubo un error con la conexion. Intentarlo mas tarde.", null);
     throw new Exception("Hubo un error con la conexion. Intentarlo mas tarde."+ex);
    }       

  }

  /*private static void obtieneInfoNavsatRecarga() {
		try {
			ArrayList infoNavsatRecarga = DBCaja.obtieneDatosNavsatRecarga();
      log.debug("infoNavsatRecarga : " + infoNavsatRecarga);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 3, 0);
      log.debug("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      log.debug("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      log.error("",sqlException);
		}
	}*/

  /**
   *
   * @deprecated
   */
  private static void obtieneInfoRecargaVirtual() {
		/*try {
			ArrayList infoNavsatRecarga = DBCaja.obtieneDatosNavsatRecarga();
      log.debug("infoNavsatRecarga : " + infoNavsatRecarga);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatRecarga, 3, 0);
      log.debug("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      log.debug("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      log.error("",sqlException);
		}*/
	}

  /**
   * Recupera los valores de conexion.
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   */
  private static void obtieneInfoRecargaVirtualBprepaid()
  {
    try
    {
      ArrayList infoTXRecarga = DBCaja.obtieneDatostRecargaBprepaid();
      log.debug("infoTXRecarga : " + infoTXRecarga);
      VariablesVirtual.vIPHost =
          FarmaUtility.getValueFieldArrayList(infoTXRecarga, 0, 0);
      VariablesVirtual.vPuertoHost =
          FarmaUtility.getValueFieldArrayList(infoTXRecarga, 1, 0);
      //Identificador de canal provisto por Bprepaid
      String vIdentificador = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 2, 0);
      VariablesVirtual.vCodigoComercio = FarmaUtility.completeWithSymbol(vIdentificador,10," ","D");
      //Provincia (Terminal State) del local.
      VariablesVirtual.vNumSerie = FarmaUtility.getValueFieldArrayList(infoTXRecarga, 3, 0);

      log.debug("VariablesVirtual.vIPHost : " +
                         VariablesVirtual.vIPHost);
      log.debug("VariablesVirtual.vPuertoHost : " +
                         VariablesVirtual.vPuertoHost);
      log.debug("VariablesVirtual.vCodigoComercio : " + VariablesVirtual.vCodigoComercio);
      log.debug("VariablesVirtual.vNumSerie : " + VariablesVirtual.vNumSerie);
    }
    catch (SQLException sqlException)
    {
      VariablesVirtual.vCodigoComercio = "";
      VariablesVirtual.vNumSerie = "";
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      //log.error("",sqlException);
      log.error(null,sqlException);
    }
  }

/*  private static void obtieneInfoNavsatTarjeta() {
		try {
			ArrayList infoNavsatTarjeta = DBCaja.obtieneDatosNavsatTarjeta();
      log.debug("infoNavsatTarjeta : " + infoNavsatTarjeta);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 3, 0);
      log.debug("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      log.debug("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      log.error("",sqlException);
		}
	}
  */

  /**
   *
   * @deprecated
   */
  private static void obtieneInfoTarjetaVirtual() {
		/*try {
			ArrayList infoNavsatTarjeta = DBCaja.obtieneDatosNavsatTarjeta();
      log.debug("infoNavsatTarjeta : " + infoNavsatTarjeta);
      VariablesVirtual.vTiempoCXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 0, 0));
      VariablesVirtual.vTiempoTXNavsat = Integer.parseInt(FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 1, 0));
			VariablesVirtual.vIPHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 2, 0);
      VariablesVirtual.vPuertoHost = FarmaUtility.getValueFieldArrayList(infoNavsatTarjeta, 3, 0);
      log.debug("VariablesVirtual.vIPHost : " + VariablesVirtual.vIPHost);
      log.debug("VariablesVirtual.vPuertoHost : " + VariablesVirtual.vPuertoHost);
		} catch (SQLException sqlException) {
      VariablesVirtual.vIPHost = "";
      VariablesVirtual.vPuertoHost = "";
      log.error("",sqlException);
		}*/
	}


/*  private static boolean validaHostPuertoNavsat() {
    boolean result = true;
    if(VariablesVirtual.vIPHost.trim().equalsIgnoreCase("") ||
       VariablesVirtual.vPuertoHost.trim().equalsIgnoreCase(""))
      result = false;
    return result;
	}
*/

  private static boolean validaHostPuertoProveedor() {
    boolean result = true;
    if(VariablesVirtual.vIPHost.trim().equalsIgnoreCase("") ||
       VariablesVirtual.vPuertoHost.trim().equalsIgnoreCase(""))
      result = false;
    return result;
	}

  private static void impresionInfoVirtual(FarmaPrintService pVPrint,
                                           FarmaPrintServiceTicket pVPrintArchivo,
                                           String pTipoProdVirtual,
                                           String pCodigoAprobacion,
                                           String pNumeroTarjeta,
                                           String pNumeroPin,
                                           String pNumeroTelefono,
                                           String pMonto,
                                           String pNumPedido,
                                           String pCodProd)
  {
    if(pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA))
    {
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin, true);
        pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);


    } else if(pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA))
    {
      /*pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Telefono : " + pNumeroTelefono, true);
      pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Monto Recarga   : " + pMonto, true);*/
      /**
       * Imprime los datos de Impresion de Recarga
       * 02.11.2007 dubilluz creacion
       */
      obtieneDescImpresion(pNumPedido,pCodProd);

      ArrayList  array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
      for(int i=0 ; i< array.size() ; i++){
        log.debug(""+array.get(i));
        if(((String)array.get(i)).trim().length()>0){
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) +
                        ((String)(array.get(i))).trim(), true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) +
                          ((String)(array.get(i))).trim(), true);
        }
      }

    }
	}
        
        
    private static void impresionInfoVirtualTicket(FarmaPrintServiceTicket pVPrint,
                                                    FarmaPrintServiceTicket pVPrintArchivo,
                                                     String pTipoProdVirtual,
                                                     String pCodigoAprobacion,
                                                     String pNumeroTarjeta,
                                                     String pNumeroPin,
                                                     String pNumeroTelefono,
                                                     String pMonto,
                                                     String pNumPedido,
                                                     String pCodProd)
    {
              log.debug("TIPO_PROD_VIRTUAL_RECARGA: "+pTipoProdVirtual);
      if(pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA))
      {
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin, true);
          pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);

      } else if(pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA))
      {
          log.debug("TIPO_PROD_VIRTUAL_RECARGA: "+pTipoProdVirtual);
        /*pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Numero Telefono : " + pNumeroTelefono, true);
        pVPrint.printLine(FarmaPRNUtility.llenarBlancos(5) + "Monto Recarga   : " + pMonto, true);*/
        /**
         * Imprime los datos de Impresion de Recarga
         * 02.11.2007 dubilluz creacion
         */
        obtieneDescImpresion(pNumPedido,pCodProd);

        ArrayList  array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
        for(int i=0 ; i< array.size() ; i++){
          log.debug(""+array.get(i));
          if(((String)array.get(i)).trim().length()>0){
          pVPrint.printLine(FarmaPRNUtility.llenarBlancos(2) +
                          ((String)(array.get(i))).trim(), true);
              pVPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(2) +
                              ((String)(array.get(i))).trim(), true);
          }
        }

      }
          }

    /**
     * Carga los datos del producto Virtual
     * @author ERIOS
     * @since 13.06.2013
     * @param pVPrint
     * @param pTipoProdVirtual
     * @param pCodigoAprobacion
     * @param pNumeroTarjeta
     * @param pNumeroPin
     * @param pNumeroTelefono
     * @param pMonto
     * @param pNumPedido
     * @param pCodProd
     */
    public static void impresionInfoVirtualTicket(ArrayList<String> pVPrint, String pTipoProdVirtual,
                                                   String pCodigoAprobacion, String pNumeroTarjeta, String pNumeroPin,
                                                   String pNumeroTelefono, String pMonto, String pNumPedido,
                                                   String pCodProd) {

        if (pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA)) {
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Cod. Aprobacion : " + pCodigoAprobacion);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Tarjeta  : " + pNumeroTarjeta);
            pVPrint.add(FarmaPRNUtility.llenarBlancos(5) + "Numero Pin      : " + pNumeroPin);
        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA)) {
            obtieneDescImpresion(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    pVPrint.add(FarmaPRNUtility.llenarBlancos(2) + ((String)(array.get(i))).trim());
                }
            }
        }
    }
    
  /**
   *
   * @throws Exception
   * @deprecated
   */
  private static void anulaVentaRecargaVirtual() throws Exception
  {
    /*proveedorTarjeta = new MetodosG(VariablesVirtual.vTiempoTXNavsat, VariablesVirtual.vTiempoCXNavsat);
    VariablesVirtual.respuestaNavSatBean =  proveedorTarjeta.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaNavSatBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaNavSatBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaNavSatBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaNavSatBean.getCodigoAprobacion());*/
  }

  /**
   * Realiza la anulacion de una venta de producto virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 27.09.2007
   * @deprecated
   */
  private static void anulaVentaRecargaVirtualBprepaid() throws Exception
  {
    /*proveedorTarjetaBprepaid = new MetodosBprepaid();
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaBprepaid.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost,
                                                                                   VariablesVirtual.vFechaTX,
                                                                                   VariablesVirtual.vHoraTX);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());*/
  }

  /**
   * Metodo que anula la venta de pedidos virtuales.
   * Este metodo se conectara con el proveedor de servicios a traves de su interfase.
   * Creado x LMesia 22/01/2007
   */
  public static void procesaAnulacionVentaProductoVirtual(JDialog pDialog, Object pObjectFocus) throws SQLException, Exception
  {
    //colocaVariablesVirtuales();
     colocaVariablesVirtualesBprepaid();
    if(VariablesCaja.vTipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA))
    {

    /*
      log.info("*** entro a anulacion de venta de recarga virtual");
      obtieneInfoRecargaVirtualBprepaid();
   
      if(!validaHostPuertoProveedor())
      {
        throw new Exception("Error al obtener host y puerto de Navsat para recarga automatica");
      }
    
      anulaVentaRecargaVirtualTX();
      colocaInfoTransaccionVirtualBprepaid();
     
     */
      
    
        ProcesoAnulaci�nRecarga();
        
    } else
    {
      limpiaInfoTransaccionTarjVirtuales();
      throw new Exception("No se puede anular un producto del tipo Tarjeta Virtual");
    }
  }


  private static void ProcesoAnulaci�nRecarga(){

        try {

            //
            //Cierra todas las conexiones remotas
            FarmaConnectionRemoto.closeConnection();

            int codigo = DBCaja.AnularRecargaVirtual();
            log.info("codigo Respuesta Recarga Virtual" + codigo);
            String cantIntentosLectura = 
                DBCaja.cantidadIntentosRespuestaRecarga().trim();
            log.debug("cantIntentosLectura" + cantIntentosLectura);
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                  FarmaConstants.INDICADOR_N);
            TimerRecarga timerTask = new TimerRecarga();
            timerTask.setCantidadIntentos(Integer.parseInt(cantIntentosLectura));
            timerTask.setCodigoSolicitud(codigo);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);

            do {
                //log.debug("indicador TIMER :"+timerTask.getIndicador());
            } while (timerTask.getIndicador().trim().equalsIgnoreCase("I"));

            log.debug("termino el TIMER DE RECARGA");

            log.debug("timerTask.getIndicador():" + timerTask.getIndicador());

            if (timerTask.getIndicador().equals("T")) {
                log.debug("No se encontro respuesta de la recarga");
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                  FarmaConstants.INDICADOR_N);
        } catch (NumberFormatException e) {
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_ADMCENTRAL, 
                                                    FarmaConstants.INDICADOR_N);
          }
          finally {
               FarmaConnectionRemoto.closeConnection();
              log.info("Cierra Conexiones remotas");
          }
         
      
      }
/*  private static void limpiaInfoTransaccionNavsat() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
  }*/
  private static void limpiaInfoTransaccionTarjVirtuales() throws Exception
  {
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vFechaTX = "";
    VariablesVirtual.vHoraTX = "";
  }


  public static void actualizaInfoPedidoVirtualAnulado(String pNumPedOrigen) throws SQLException
  {
    DBCaja.actualizaInfoPedidoAnuladoVirtual(pNumPedOrigen);
  }
  /**
   * Coloca la descripcion de la impresion que el proveedor requiere en la boleta
   * @author dubilluz
   * @since  02.11.2007
   */
  public static void obtieneDescImpresion(String pNumped,String pCodProd)
  {
    try
    {
     DBCaja.obtieneInfImpresionRecarga(VariablesVirtual.vArrayList_InfoProvRecarga,
                                       pNumped,pCodProd);
     log.debug("xxxx : " + VariablesVirtual.vArrayList_InfoProvRecarga);
    }
    catch(SQLException e)
    {
      //log.debug("Ocurrio un error al obtener la informacio del Proveedor :\n " + e);
      log.error(null,e);
    }
  }

  /**
   * Se encarga de realizar la venta de una Tarjeta Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void ventaTarjetaVirtualTX() throws Exception
  {

    proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.VentaTarjetaVirtual(VariablesVirtual.vCodigoComercio,
                                                                                 VariablesVirtual.vTipoTarjeta,
                                                                                 VariablesVirtual.vMonto,
                                                                                 VariablesVirtual.vNumTerminal,
                                                                                 VariablesVirtual.vNumSerie,
                                                                                 VariablesVirtual.vNumTrace,
                                                                                 VariablesVirtual.vIPHost,
                                                                                 VariablesVirtual.vPuertoHost);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
    log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
    log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
    log.debug("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
    log.debug("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());
  }
  
  /**
   * Se encarga de realizar la venta de una Recarga Virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void ventaRecargaVirtualTX() throws Exception
  {
      /*
      proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
      VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                            VariablesVirtual.vNumeroCelular,
                                                                            VariablesVirtual.vCodigoProv,
                                                                            VariablesVirtual.vMonto,
                                                                            VariablesVirtual.vNumTerminal,
                                                                            VariablesVirtual.vNumSerie,
                                                                            VariablesVirtual.vNumTrace,
                                                                            VariablesVirtual.vIPHost,
                                                                            VariablesVirtual.vPuertoHost,
                                                                            FarmaVariables.vCodLocal,
                                                                            VariablesCaja.vNumPedVta);
      
      VariablesVirtual.respuestaTXBean = UtilityRecargaVirtual.VentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                             VariablesVirtual.vNumeroCelular,
                                                                             VariablesVirtual.vCodigoProv,
                                                                             VariablesVirtual.vMonto,
                                                                             VariablesVirtual.vNumTerminal,
                                                                             VariablesVirtual.vNumSerie,
                                                                             VariablesVirtual.vNumTrace,
                                                                             VariablesVirtual.vIPHost,
                                                                             VariablesVirtual.vPuertoHost,
                                                                             FarmaVariables.vCodLocal,
                                                                             VariablesCaja.vNumPedVta);*/
      //
      /*if(VariablesVirtual.respuestaTXBean==null){
          log.info("El bean Respuesta es NULLO por lo que se consultara en XE_999 para ver su recarga si existe o no");
      }*/
      UtilityRecargaVirtual utilRecaVirtual = new UtilityRecargaVirtual();
      utilRecaVirtual.gestionaSolicitudRecarga(VariablesVirtual.vNumeroCelular, VariablesVirtual.vCodigoProv, VariablesVirtual.vMonto, VariablesVirtual.vNumTerminal, 
                                            VariablesVirtual.vNumSerie,  FarmaVariables.vCodLocal,  VariablesCaja.vNumPedVta);
        log.info("acaba metodo UtilityRecargaVirtual.gestionaSolicitudRecarga" );        
      /*                                                                                           
      log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
      log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
      log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
      log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
      log.debug("getNumeroTarjeta(): " + VariablesVirtual.respuestaTXBean.getNumeroTarjeta());
      log.debug("getCodigoPIN(): " + VariablesVirtual.respuestaTXBean.getCodigoPIN());
      log.debug("getFechaTX(): " + VariablesVirtual.respuestaTXBean.getFechaTX());
      log.debug("getHoraTX(): " + VariablesVirtual.respuestaTXBean.getHoraTX());*/


  }
  
  /**
   * Realiza la anulacion de una venta de producto virtual.
   * @throws Exception
   * @author Edgar Rios Navarro
   * @since 14.12.2007
   */
  private static void anulaVentaRecargaVirtualTX() throws Exception
  {
    proveedorTarjetaVirtual = MetodosTXFactory.getMetodosTXVirtual(MetodosTXFactory.METODO_BPCLIENTWS);
    VariablesVirtual.respuestaTXBean =  proveedorTarjetaVirtual.AnulacionVentaRecarga(VariablesVirtual.vCodigoComercio,
                                                                                   VariablesVirtual.vNumeroCelular,
                                                                                   VariablesVirtual.vCodigoProv,
                                                                                   VariablesVirtual.vMonto,
                                                                                   VariablesVirtual.vNumTerminal,
                                                                                   VariablesVirtual.vNumSerie,
                                                                                   VariablesVirtual.vNumTrace,
                                                                                   VariablesVirtual.vCodAprobacionOriginal,
                                                                                   VariablesVirtual.vNumTraceOriginal,
                                                                                   VariablesVirtual.vIPHost,
                                                                                   VariablesVirtual.vPuertoHost,
                                                                                   VariablesVirtual.vFechaTX,
                                                                                   VariablesVirtual.vHoraTX,
                                                                                   FarmaVariables.vCodLocal,
                                                                                   VariablesCaja.vNumPedVta_Anul);
    log.debug("VariablesVirtual.respuestaNavSatBean: " + VariablesVirtual.respuestaTXBean);
    log.debug("getCodigoRespuesta(): " + VariablesVirtual.respuestaTXBean.getCodigoRespuesta());
    log.debug("getNumeroTrace(): " + VariablesVirtual.respuestaTXBean.getNumeroTrace());
    log.debug("getCodigoAprobacion(): " + VariablesVirtual.respuestaTXBean.getCodigoAprobacion());
  }
  
  /**
   * Se evalua la fecha de movimiento de la caja.
   * @param pDialogo
   * @param pObject
   * @return 
   * @author Edgar Rios Navarro
   * @since 07.01.2007
   */
  public static boolean validaFechaMovimientoCaja(JDialog pDialogo,Object pObject)
  {
    try{
        VariablesCaja.vNumCaja = DBCaja.obtenerCajaUsuario();
        VariablesPtoVenta.vNumCaja = VariablesCaja.vNumCaja;
      String fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      String fechaMovCaja = DBCaja.obtieneFechaMovCaja();
      if ( fechaMovCaja.trim().length()>0 && !(fechaMovCaja.substring(0, 5).equalsIgnoreCase(fechaSistema.substring(0, 5))) ) {
       //
        FarmaUtility.showMessage(pDialogo, "Debe CERRAR su caja para empezar un NUEVO DIA.\n" +
          "La Fecha actual no coincide con la Fecha de Apertura de Caja.",pObject);
            return false;
        
          // se cierra la caja de forma automatica y se abre la caja de forma automatica
        /*  FarmaUtility.showMessage(pDialogo, "Se va cerrar y abrir caja de forma automatica.",pObject);
          UtilityModuloVenta.vCierraAperturaCaja();
          FarmaUtility.showMessage(pDialogo, "VALIDAR QUE SE HIZO CIERRE Y APERTURA.",pObject);
          
          return true;*/
        
      }
      return true;
    } catch (Exception sqlException) {
      //log.error("",sqlException);
      log.error(null,sqlException);
      FarmaUtility.showMessage(pDialogo, "Error al obtener la fecha de movimiento de caja.",pObject);
      return false;
    }
  }

    /**
     * Se imprime los consejos asociados al pedido.
     * @author Edgar Rios Navarro
     * @since 09.05.2008
     */
    private static boolean imprimeConsejos(JDialog pDialogo) {
        boolean consejo = false;
        /*
        try {
            String htmlConsejo = DBCaja.obtieneConsejos(VariablesCaja.vNumPedVta, FarmaVariables.vIPBD);

            if (!htmlConsejo.equals("N")) {
                PrintConsejo.imprimirHtml(htmlConsejo, VariablesPtoVenta.vImpresoraActual,
                                          VariablesPtoVenta.vTipoImpTermicaxIp);                
                FarmaUtility.aceptarTransaccion();
                consejo = true; 
            }
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los consejos.", null);
            enviaErrorCorreoPorDB(sqlException.toString(), "\nError al imprimir CONSEJO");

        } catch (Exception e) {
            log.error(null, e);
            FarmaUtility.liberarTransaccion();
            enviaErrorCorreoPorDB(e.toString(), "\nError al imprimir CONSEJO");

        }*/
        return consejo;
    }
  
   /**
   * Se imprime los cierto datos de comanda al cobrar algun pedido de delivery
   * @author Jorge Cortez Alvarez
   * @since 13.06.2008
   */
    //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA
  public static void imprimeDatosDelivery(JDialog pDialogo,String NumPed)
  {
    
    
      try
      {
        //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
       // String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP         
                               
           String vIndImpre = DBCaja.obtieneIndImpresion();
           log.debug("vIndImpre :"+vIndImpre);
            if (!vIndImpre.equals("N"))
            {
              String htmlDelivery = DBCaja.obtieneDatosDelivery(NumPed,FarmaVariables.vIPBD);
			  //log.debug(datos);
              PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            }
          
        
      }catch (SQLException sqlException)
      {
        //log.error("",sqlException);
        log.error(null,sqlException);
        FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de delivery.:"+sqlException.getMessage(), null);
          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            enviaErrorCorreoPorDB(sqlException.toString(),NumPed);
      }
    

  }
  
  /**
     * Metodo que verifica si el pedido tiene cupones para imprimir 
     * @author Diego Ubilluz Carrillo
     * @since  03.07.2008
     * @param pDialogo
     */
  private static int imprimeCupones(JDialog pDialogo){
    ArrayList listaCupones = new ArrayList();
    int cant_cupones_impresos  = 0;/* 
    try{
          DBCaja.obtieneCuponesPedidoImpr(listaCupones,VariablesCaja.vNumPedVta);
          log.debug("Lista cupones .... " + listaCupones );
           
          if(listaCupones.size()>0)
          {
              String cod_cupon = "";
              for(int i=0;i<listaCupones.size();i++){
                  cod_cupon = ((ArrayList)(listaCupones.get(i))).get(0).toString();
                  if(cod_cupon.trim().length()>0)
                     cant_cupones_impresos = cant_cupones_impresos + imprimeCupon(pDialogo,cod_cupon);
              }
          }
       }
       catch (SQLException sqlException)
       {
        //log.error("",sqlException);
        log.error(null,sqlException);
        FarmaUtility.showMessage(pDialogo, 
                                "Error al verificar si tiene cupones el pedido.\n"+
                                 sqlException.getMessage(), null);
        //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
           enviaErrorCorreoPorDB(sqlException.toString(),null);
       }
     */
    return cant_cupones_impresos;
       
   }
   
  
  /**
   * Se imprime cupones que tenga el pedido cobrado
   * @author Diego Ubilluz
   * @since 03.07.2008
   */
   //MARCO FAJARDO 08/04/09 MEJORA TIEMPO DE RESPUESTA DE IMPRESORA TERMICA
  private static int imprimeCupon(JDialog pDialogo,String vCodeCupon)
  {
    int cant_cupones_impresos = 0;
    
      try
      {
        //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
        //String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
        
        int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
              
            
            String vCupon = DBCaja.obtieneImprCupon(VariablesCaja.vNumPedVta, 
                                                    FarmaVariables.vIPBD,
                                                    vCodeCupon);
            if(!vCupon.equals("N"))
            {
            	log.debug("cupon a imprimir : "+vCupon);
            	PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,vCodeCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            	log.debug("despues a imprimir");
            	// -- Proceso autonomo que tiene COMMIT
            	DBCaja.cambiaIndImpresionCupon(VariablesCaja.vNumPedVta,vCodeCupon);
            	cant_cupones_impresos ++;
            }   


      }
      catch (SQLException sqlException)
      {
       //log.error("",sqlException);
        log.error(null,sqlException);
       FarmaUtility.showMessage(pDialogo, 
                               "Error al obtener los consejos.", null);

      }
      
    
    
    return cant_cupones_impresos;
   }   
  
    /**
       * Metodo encargado de anular los cupones en Matriz si estos no fueron 
       * enviados aun por el JOB � crea los cupones con el estado Anulado
       * si estos aun no fueron enviados a Matriz
       * @param pNumPed
       * @author Diego Ubilluz
       * @since  21.08.2008
       */
    public static void anulaCuponesPedido(String pNumPed,JDialog pDialogos,
                                          Object pObject){
        log.debug("Anular Cupones en Matriz");
        log.debug("**INICIO**");/* 
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesAnulados = new ArrayList();
        int COL_COD_CUPON = 0;
        int COL_COD_FECHA_INI = 1;
        int COL_COD_FECHA_FIN = 2;   
        String vEstCuponMatriz = "";
        String vCodCupon = "";
        String vRetorno  = "";
        String vFechIni ="";
        String vFechFin ="";
        String indMultiUso ="";
        boolean vIndModify = false;
        try{
            
            DBCaja.getcuponesPedido(pNumPed,
                                    FarmaConstants.INDICADOR_N,
                                    listaCuponesAnulados,
                                    ConstantsCaja.CONSULTA_CUPONES_ANUL_SIN_PROC_MATRIZ);
            log.debug("**listaCuponesAnulados00**"+ listaCuponesAnulados);
            if(listaCuponesAnulados.size()>0){
                 String vValor = "";
                 vValor = FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                              0,
                                                              COL_COD_CUPON);
                 
                if(!vValor.equalsIgnoreCase("NULO")){
                    VariablesCaja.vIndLinea = 
                                  FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                                 FarmaConstants.INDICADOR_N);
                    
                    
                    //-- Si no hay linea se cierra la conexion con Matriz
                    if(VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                       log.debug("No existe linea se cerrara la conexion ...");
                       FarmaConnectionRemoto.closeConnection();
                       VariablesCaja.vIndLinea = "";
                    }
                    else{
                      //--Si hay conexion se procedera anular los cupones en Matriz
                      //--Y crearlos si no existe pero con estado Anulado
                        for(int i=0;i<listaCuponesAnulados.size();i++){
                            vCodCupon = 
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_CUPON);
                            vFechIni  =
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_FECHA_INI);
                            
                            vFechFin  =
                                FarmaUtility.getValueFieldArrayList(listaCuponesAnulados,
                                                                    i,COL_COD_FECHA_FIN);                          
                            
                            indMultiUso = DBCaja.getIndCuponMultiploUso(pNumPed,
                                                                        vCodCupon).trim();                          
                            
                            log.debug("**indMultiUso**"+ indMultiUso);
                            if(indMultiUso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                               {
                                vIndModify = true;
                                  vEstCuponMatriz = 
                                          DBCaja.getEstadoCuponEnMatriz(vCodCupon, 
                                                                        FarmaConstants.INDICADOR_S).trim();

                                  log.debug("vEstCuponMatriz "+ vEstCuponMatriz);  
                                  //--Si valor de retorno es "0" es porque el cupon
                                  //  no existe asi que se creara en Matriz
                                  if (vEstCuponMatriz.equalsIgnoreCase("0")) {
                                      vRetorno = 
                                              DBCaja.grabaCuponEnMatriz(vCodCupon, 
                                                                        vFechIni, 
                                                                        vFechFin, 
                                                                        FarmaConstants.INDICADOR_N).trim();
                                  }
                                  vRetorno = 
                                          DBCaja.actualizaEstadoCuponEnMatriz(vCodCupon, 
                                                                              FarmaConstants.INDICADOR_N, 
                                                                              FarmaConstants.INDICADOR_N).trim();

                                  //--Si la actualizacion se realizo con exito se actualiza
                                  //  en el local que el cupon ya se proceso en Matriz
                                  if (vRetorno.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                                      DBCaja.actualizaCuponGeneral(vCodCupon.trim(), 
                                                                   ConstantsCaja.CONSULTA_ACTUALIZA_MATRIZ);
                                }
                            }
                            
                        }
                        //--fin de FOR
                        //--Se acepta la transaccion en la Conexion a Matriz y Local
                        if(vIndModify){
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_N);
                        FarmaUtility.aceptarTransaccion();
                        }
                    }
                }

            }
            
        }
        catch(SQLException e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_N);
            log.error("",e);
            FarmaUtility.showMessage(pDialogos, 
                                     "Error al momento de anular cupones en Matriz.\n" + 
                                     e.getMessage(), pObject);          
        }
        finally{
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if(VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
               log.debug("Se cierrara la conexion..");
               FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }
         */
        log.debug("**FIN**");
    }
  
    /**
       * Retorna el numero de cupones emitidos
       * @author DUbilluz
       * @param pNumPed
       * @return
       */
    public static int getCuponesEmitidosUsados(String pNumPed,JDialog pDialogo,
                                               Object pObject){
        String vCodCupon = "";
        int vNumCuponesEmitidos = 0;
        ArrayList listCuponesEmitidos = new ArrayList();
        try{
            //-- inicio validacion cupones
             //Se consulta para obtener los cupones usados en el pedido
            
             DBCaja.getcuponesPedido(pNumPed,
                                     FarmaConstants.INDICADOR_N,
                                     listCuponesEmitidos,
                                     ConstantsCaja.CONSULTA_CUPONES_EMITIDOS_USADOS);
             
             if(listCuponesEmitidos.size()>0){
                 
                  String vValor = "";
                  vValor = FarmaUtility.getValueFieldArrayList(listCuponesEmitidos,
                                                               0,
                                                               0);
                 if(vValor.equalsIgnoreCase("NULO")){
                    return  0;
                 }
                 vNumCuponesEmitidos = listCuponesEmitidos.size();
             }

        }catch(SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(pDialogo, 
                                   "Error al obtener cupones emitidos usados.\n" + 
                                   e.getMessage(), pObject);
        }
        
        return vNumCuponesEmitidos;
    }
    
    /**
     * 
     * @param pSecNumIni
     * @param pSecNumFin
     * @return
     */
    public static boolean isExistsCompProcesoSAP(String pSecNumIni,String pSecNumFin){
        boolean pResultado = false;
        String pCadena = "";
        try{
             pCadena = DBCaja.isExistCompProcesoSAP(pSecNumIni,pSecNumFin);
            if(pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                 pResultado = true;
            else
                 pResultado = false;
            
        }catch(SQLException e)
        {
          log.error("",e);
        }
    
        return pResultado;
    }

    /**
     * @author dubilluz
     * @param secIni
     * @param secFin
     * @return
     */
    public static boolean pValidaComprobantesProcesoSAP(String secIni, 
                                                        String secFin,
                                                        Object pDialogo,
                                                        Object pObjeto,
                                                        Frame pFrame) {
        
        
        if (isExistsCompProcesoSAP(secIni.trim(), secFin.trim())) {
            FarmaUtility.showMessage((JDialog)pDialogo, 
                   "Atenci�n:\n"+
                   "No podr� corregir los correlativos de los comprobantes\n"+
                   "porque alguno de ellos ya han sido transferidos a contabilidad.\n"+
                   "Debe comunicarse con su contador para que justifique\n"+
                   "el motivo para regularizar los comprobantes.",
                                     pObjeto);

            if (!cargaLoginOper(pFrame,pDialogo)) {
                return false;
            }
        }

        return true;
    }
    
    private static boolean cargaLoginOper(Frame pFrame,Object pDialog)
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
        try{
          DlgLogin dlgLogin = new DlgLogin(pFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
          dlgLogin.setVisible(true);
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
        } catch (Exception e)
        {
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
          FarmaVariables.vAceptar = false;
          log.error("",e);
          FarmaUtility.showMessage((JDialog)pDialog,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
        }
        
      return FarmaVariables.vAceptar;
    }
    
    /**
     * Revisa si el pedido es de Delivery para enviar un alerta de Anulacion     * 
     * @author Dubilluz
     * @since  26.11.2008
     * @param pCadena
     */
    public static void alertaPedidoDelivery(String pCadena){
        if(!pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
            String pCodCia = "",pCodLocalDel="",pNumPedDel = "";
            String[] pDatosDel = pCadena.trim().split("%");
            if(pDatosDel.length==5){
                pCodCia = pDatosDel[0].trim();
                pCodLocalDel = pDatosDel[1].trim();
                pNumPedDel = pDatosDel[2].trim();
                
                try{
                    DBCaja.enviaAlertaDelivery(pCodCia,pCodLocalDel,pNumPedDel);
                }catch(SQLException e)
                {
                  //log.error("",e);
                    log.debug("Error de envio de alerta delivery");
                }               
            }
            
        }
    }
    
    /**
     * Validar si existen comprobantes desfasados
     * @author Dubilluz
     * @since  27.11.2008
     * @param pFechaDia
     * @return
     */
    public static boolean validaCompDesfase(String pFechaDia){
        String pRes = "";
        try
        {
            pRes = DBCaja.validaCompDesfase(pFechaDia).trim();
        } catch(SQLException sql)
        {
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
     * 
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaDelPendSinReg(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaDelPendSinReg(pFechaDia).trim();    
        } catch(SQLException sql)
        {
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }

    public static boolean validaAnulPedSinReg(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaAnulPeddSinReg(pFechaDia).trim();    
        } catch(SQLException sql)
        {
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    /**
     * 
     * @author dubilluz
     * @since  28.11.2008
     * @return
     */
    public static boolean validaRegPedManual(String pFechaDia)
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.validaPedidosManualesSinReg(pFechaDia).trim();
        } catch(SQLException sql)
        {
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
     * Prueba Impresora Termica
     * @author Diego Ubilluz
     * @since  01.12.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void pruebaImpresoraTermica(JDialog pDialogo,Object pObject)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      String numAux = "000"+numeroCorrel;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP         
          
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          
          
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{
              
              String vCupon = DBCaja.pruebaImpresoraTermica(pCodCupon);
              log.debug(" prueba de impresion termica...\n"+vCupon);
              
              PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,pCodCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            //}
          //}
          FarmaUtility.showMessage(pDialogo, 
                                  "Se realiz� la prueba de impresi�n, recoja la impresi�n.", pObject);

        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresion.",pObject);

        }
        
      //}
    }
    
    public static void activaCuponesMatriz(String pNumPed, JDialog pDialogos, 
                                           Object pObject) {
        log.debug("activa cupones usados en Matriz");
        log.debug("**INICIO**");
        VariablesCaja.vIndLinea = "";
        ArrayList listaCuponesUsados = new ArrayList();
        int COL_COD_CUPON = 0;
        try {
            VariablesCaja.vIndLinea = 
                    FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, 
                                                   FarmaConstants.INDICADOR_N);
            
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                
                
                DBCaja.getcuponesUsadosPedido(pNumPed, listaCuponesUsados);
                log.debug("**listaCuponesUsados**" + 
                                   listaCuponesUsados);            
                if (listaCuponesUsados.size() > 0) {
                    String vValor = "";
                    for (int i = 0; i < listaCuponesUsados.size(); i++) {
                        vValor = 
                                FarmaUtility.getValueFieldArrayList(listaCuponesUsados, 
                                                                    i, 
                                                                    COL_COD_CUPON);
    
                        DBCaja.activaCuponenMatriz(vValor.trim(),FarmaConstants.INDICADOR_N);
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
                                                              FarmaConstants.INDICADOR_N);
                    }
    
                }
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
                                                  FarmaConstants.INDICADOR_N);
            log.error("",e);
            FarmaUtility.showMessage(pDialogos, 
                                     "Error al momento de activar cupones en Matriz.\n" +
                    e.getMessage(), pObject);
        } finally {
            //Se cierra la conexion si hubo linea esto cuando se consulto a matriz
            if (VariablesCaja.vIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                log.debug("Se cierrara la conexion..");
                FarmaConnectionRemoto.closeConnection();
            }
            VariablesCaja.vIndLinea = "";
        }

        log.debug("**FIN**");
    }
   
    /**
     * obtener DNI del cliente si se trata de una venta que acumula ventas
     * @author JCALLO
     * @param  pNumPed
     * @return
     */
    public static String obtenerDniPedidoAcumulaVenta(String pNumPed) {
        log.debug("ver si el pedido acumula ventas");
        log.debug("**INICIO**");
        
        String Dni = "";
        return Dni;
        /*
        try {
        	Dni = DBCaja.getDniPedidoAcumulaVenta(pNumPed);
        	return Dni.trim();
        } catch (SQLException e) {            
            log.error("",e);
            return "";
        } finally {
        }*/
    }

    /**
     * Analiza si el pedido es de camapana acumulada
     * y revertira todo o confirmara segun sea el caso
     * @author dubilluz
     * @since  19.12.2008
     * @param  pNumPed
     * @param  pDialogos
     * @param  pObject
     */
    public static boolean realizaAccionCampanaAcumulada
                                    (
                                     String pIndLinea,
                                     String pNumPed, JDialog pDialogos,
                                     String pAccion,
                                     Object pObject,
                                     String pIndEliminaRespaldo
                                     ) {
        String sDNI = "";
        String existeRegalo = "";
        try{
            sDNI = DBCaja.getDniFidPedidoCampana(pNumPed).trim();
            log.debug("DNI realizaAccionCAmpanaAcu: "+sDNI);
            if(sDNI.length()>0)
            {
                existeRegalo = DBCaja.getExistRegaloCampanaAcumulada(sDNI,pNumPed);
                log.debug("Existe REGALO: "+existeRegalo);
                /* //Se comento para evitar insertOrigenMatriz e insertCanjMatriz 
                 * //que se encuentra dentro de enviaRegaloMatriz. Todo el Proceso
                 * //de acumulado se hace en local.
                if(existeRegalo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {                   
                    //Si no hay linea no deja cobrar
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                    {   
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                            return false;
                    }
                    else
                    if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        if(pAccion.trim().equalsIgnoreCase(ConstantsCaja.ACCION_COBRO))
                           enviaRegaloMatriz(sDNI,pNumPed);                                      
                }
                */
                //JMIRANDA 17/07/09 
                DBCaja.analizaCanjeLocal(sDNI,pNumPed,pAccion,pIndEliminaRespaldo);
               /* if(pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {   
                DBCaja.analizaCanjeMatriz(sDNI,pNumPed,pAccion);
                }
                */
                //JMIRANDA 16/07/09
               DBCaja.analizaCanjeMatriz(sDNI,pNumPed,pAccion);
                log.debug("TRUE 1 ANALIZACANJEMATRIZ");
                return true;
                                
            }
            else
                log.debug("TRUE 2 ANALIZACANJEMATRIZ");
                return true;
            
        }catch (SQLException e){
            log.error("",e);
            log.debug("Envia error ANALIZACANJEMATRIZ");
            return false;
            
        }
    }
    
    /**
     * Se imprime el las campa�as acumuladas del cliente
     * @author JAVIER CALLO QUISPE
     * @since 12.10.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimeUnidRestCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
 
         //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP        
                
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

                StringBuffer html = new StringBuffer("");
                
                ArrayList listaMatriz = new ArrayList();
                //obteniendo la suma de las unidades acumuladas en las campa�as
                //en la compra
                DBCaja.getListCampRestPremioXCliente(listaMatriz,pDni, pNumPedVta);
             
                //obteniendo los premios que gano en el pedido
                ArrayList listaPremios = new ArrayList();
                DBCaja.getListCampPremiosPedidoCliente(listaPremios,pDni, pNumPedVta);
                
                //obteniendo la cabecera del html
                String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();
                
                //obteniendo la pie del html
                String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();
                
                /**generando el html**/
                html.append(cab_html);
                String auxCodCamp = "";
                boolean flag = false;
                for(int k=0; k<listaMatriz.size() ;k++){//recorriendo por cada campa�a del local
                    
                    auxCodCamp = ((ArrayList)listaMatriz.get(k)).get(0).toString().trim();
                    //buscando si dicha campa�a 
                    flag = false;
                    for(int p = 0 ; p < listaPremios.size() ;p++){
                        if(auxCodCamp.equals( ((ArrayList)listaPremios.get(p)).get(0).toString() ) ){
                            flag = true;
                            listaPremios.remove(p);//quitanto la campa�a
                            break;
                        }
                    }
                    if(flag){
                        html.append("<tr><td><b>Ud. Gan&oacute premio de la campa�a</b><br>");
                    }else{
                        html.append("<tr><td>");
                    }
                    
                    int cant = 0;
                    
                    try{
                        cant = Integer.parseInt(((ArrayList)listaMatriz.get(k)).get(10).toString().trim());
                    }catch(Exception e){
                        cant = 0;
                    }
                    
                    if( cant > 0 ){
                        
                        html.append( ((ArrayList)listaMatriz.get(k)).get(7).toString().trim() )//mensaje
                            .append("<br>")
                            .append("Le faltan ")
                            .append( ((ArrayList)listaMatriz.get(k)).get(10).toString().trim() )
                            .append("&nbsp;&nbsp;")
                            .append( ((ArrayList)listaMatriz.get(k)).get(6).toString().trim() )
                            .append(" de compra para ganar el premio</td></tr> ");
                        
                        
                    }else{
                        html.append( ((ArrayList)listaMatriz.get(k)).get(7).toString().trim() )
                            .append("<br>")
                            .append("Acumul&oacute; ")
                            .append( ((ArrayList)listaMatriz.get(k)).get(9).toString().trim() )
                            .append("&nbsp;&nbsp;")
                            .append( ((ArrayList)listaMatriz.get(k)).get(6).toString().trim() )
                            .append(" en total de sus compras </td></tr> ");
                    
                    }
                    
                    
                }
                
                html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");
                
                
                if (html.toString().length()>0)
                {
                  log.info("htmlImprimir:"+html.toString());
                  PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                }
               // break;
            //}
          //}

        }
        catch (SQLException sqlException)
        {
          //log.error("",sqlException);
          log.error(null,sqlException);
        }catch(Exception e){
      	  log.info("error imprimir consejo:"+e.getMessage());
        }
      //}
    }
    
    /**
     * imprime la cantidad de unidades que acumulo para las campa�as
     * en la compra
     * @author JAVIER CALLO QUISPE
     * @since 22.10.2008
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    private static void imprimirUnidAcumCampXCliente(JDialog pDialogo, String pDni, String pNumPedVta)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
         
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{

              StringBuffer html = new StringBuffer("");
              
              ArrayList listaLocal = new ArrayList();
              //obteniendo la suma de las unidades acumuladas en las campa�as
              //en la compra
              DBCaja.getListCampAcumuladaXCliente(listaLocal,pDni, pNumPedVta);
              
              //obteniendo los premios que gano en el pedido
              ArrayList listaPremios = new ArrayList();
              DBCaja.getListCampPremiosPedidoCliente(listaPremios,pDni, pNumPedVta);
              
              //obteniendo la cabecera del html
              String cab_html = DBCaja.getCabHtmlCampAcumXCliente(pDni).trim();
              
              //obteniendo la pie del html
              String pie_html = DBCaja.getPieHtmlCampAcumXCliente(pNumPedVta).trim();
              
              /**generando el html**/
              html.append(cab_html);
              String auxCodCamp = "";
              boolean flag = false;
              for(int k=0; k<listaLocal.size() ;k++){//recorriendo por cada campa�a del local
                  
                  auxCodCamp = ((ArrayList)listaLocal.get(k)).get(0).toString().trim();
                  //buscando si dicha campa�a 
                  flag = false;
                  for(int p = 0 ; p < listaPremios.size() ;p++){
                      if(auxCodCamp.equals( ((ArrayList)listaPremios.get(p)).get(0).toString() ) ){
                          flag = true;
                          listaPremios.remove(p);//quitanto la campa�a
                          break;
                      }
                  }
                  if(flag){
                    html.append("<tr><td><b>Ud. Gan&oacute premio de la campa�a</b><br>");
                  }else{
                    html.append("<tr><td>");
                  }
                  html.append( ((ArrayList)listaLocal.get(k)).get(7).toString().trim() )
                      .append("<br>")
                      .append("Acumul&oacute; ")
                      .append( ((ArrayList)listaLocal.get(k)).get(9).toString().trim() )
                      .append("&nbsp;&nbsp;")
                      .append( ((ArrayList)listaLocal.get(k)).get(6).toString().trim() )
                      .append(" en su compra</td></tr> ");
              }
              
              html.append("</table>").append(pie_html).append("</td></tr></table></body></html>");
              
              
              if (html.toString().length()>0)
              {
                log.info("htmlImprimir:"+html.toString());
                PrintConsejo.imprimirHtml(html.toString(), VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              }
             // break;
            //}
          //}

        }
        catch (SQLException sqlException)
        {
          //log.error("",sqlException);
          log.error(null,sqlException);
        }catch(Exception e){
          log.info("error imprimir consejo:"+e.getMessage());
        }
      //}
    }
    
    private static void enviaRegaloMatriz(String pDNI,
                                          String pNumPed)throws SQLException{
        
        
        ArrayList listaCanjes = new ArrayList();
        DBCaja.getPedidosCanj(pDNI,pNumPed,listaCanjes);
        
        if(listaCanjes.size()>0){
            String codCampana,fechaPedVta,secPedVta,codProd,cantAtendia,valFrac;
            
            //envia pedidos de regalo
            for(int i=0; i<listaCanjes.size();i++){
                codCampana = FarmaUtility.getValueFieldArrayList(listaCanjes,i,0);
                fechaPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes,i,1);
                secPedVta = FarmaUtility.getValueFieldArrayList(listaCanjes,i,2);
                codProd = FarmaUtility.getValueFieldArrayList(listaCanjes,i,3);
                cantAtendia = FarmaUtility.getValueFieldArrayList(listaCanjes,i,4);
                valFrac = FarmaUtility.getValueFieldArrayList(listaCanjes,i,5);                

                DBCaja.insertCanjMatriz(pDNI,pNumPed,codCampana,secPedVta,
                        codProd, cantAtendia,
                        valFrac,"A",fechaPedVta);  
                
            }
            
            
            ArrayList listaOrigenCanjes = new ArrayList();
            
            DBCaja.getOrigenPedCanj(pDNI,pNumPed,listaOrigenCanjes);
            
            if(listaOrigenCanjes.size()>0){
            //envia pedidos origen
            String codCamp,fechaPed,secPed,codProdCanj,
                   codLocalOrigen,numPedOrigen,SecOrigen,codProdOrigen,
                   cantUso,valFracMin;
                for(int i=0; i<listaOrigenCanjes.size();i++){
                    codCamp = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,0);
                    fechaPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,1);
                    secPed = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,2);
                    codProdCanj = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,3);
                    codLocalOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,4);
                    numPedOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,5);                
                    SecOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,6);                
                    codProdOrigen = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,7);
                    cantUso = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,8);
                    valFracMin = FarmaUtility.getValueFieldArrayList(listaOrigenCanjes,i,9);

                    
                    DBCaja.insertOrigenMatriz(pDNI,pNumPed,
                                            codCamp,secPed,
                                            codProdCanj,
                                            valFracMin,"A",
                                            codLocalOrigen,
                                            numPedOrigen,                                          
                                            SecOrigen,
                                            codProdOrigen,
                                            cantUso,
                                            fechaPed);

                } 
            
            }            
            
        }
        
    }

    /**
    * Se imprime VOUCHER para el remito 
    * @author JCORTEZ
    * @since 14.01.09
    */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeVoucherRemito(JDialog pDialogo,String NumRemito)
    {

     //Impresion en la matricial tal como es en Mifarma
     if(UtilityCajaElectronica.getIndImpreRemito_Matricial())
        imprimeMatricialRemito(pDialogo,NumRemito);
        //impresion en papael Termico
        imprimeTermicaRemito(pDialogo,NumRemito);     
    }
    /**
     * @author Dubilluz 
     * @since  02.05.2012
     * @param pDialogo
     * @param NumRemito
     */
        public static void imprimeMatricialRemito(JDialog pDialogo,String NumRemito){
        VariablesCaja.vRutaImpresora = FarmaVariables.vImprReporte;
        FarmaPrintService vPrint = new FarmaPrintService(26, VariablesCaja.vRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
               VariablesCaja.vEstadoSinComprobanteImpreso="S";
               log.info("**** IP :" + FarmaVariables.vIpPc);
               log.info("ERROR DE IMPRESORA : No se pudo imprimir el remito");
        }
        else
        {
            try {
                String[] pDatos = DBCajaElectronica.getDatosRemitoMatricial(NumRemito).split("@");
                /*
                Remito - 028 - 0010099676
                <<Razon Social y Nombre Local>> Mifarma S.A.C 028-LA MOLINA-PRE_PROD
                <<   Direccion del Local     >> AV. LA MOLINA MZA. J LOTE. 21 URB. RINCONADA DEL LAGO (NO INDICA) LIMA LIMA LA MOLINA
                << El envio se hace para     >> Boveda Prosegur - Banco Citibank
                <<        NUmero de Sobres   >> 61
                <<        Monto Soles        >> 20,486.75
                <<        Monto Dolares      >> 390.00
                */    
                if(pDatos.length==10){
                    String pNombreEmpresaLocal = pDatos[0].trim();
                    String pDirecLocal         = pDatos[1].trim();
                    String pParaBanco          = pDatos[2].trim();
                    String pNumeroSobres  = pDatos[3].trim();
                    String pMontSoles     = pDatos[4].trim();
                    String pMontDolares   = pDatos[5].trim();
                    String pCliente   = pDatos[6].trim();
                    String pFecha   = pDatos[7].trim();
                    String pPrecinto   = pDatos[8].trim();                    
                    String pCuenta   = pDatos[9].trim(); 
                    log.debug("<<<<  pNombreEmpresaLocal >>>>> "+pNombreEmpresaLocal);
                    log.debug("<<<<  pDirecLocal >>>>> "+pDirecLocal);
                    log.debug("<<<<  pParaBanco >>>>> "+pParaBanco);
                    log.debug("<<<<  pNumeroSobres >>>>> "+pNumeroSobres);
                    log.debug("<<<<  pMontSoles >>>>> "+pMontSoles);
                    log.debug("<<<<  pMontDolares >>>>> "+pMontDolares);
                    log.debug("<<<<  pCliente >>>>> "+pCliente);
                    log.debug("<<<<  pFecha >>>>> "+pFecha);                    
                    log.debug("<<<<  pPrecinto >>>>> "+pPrecinto);                                        
                    log.debug("<<<<  pCuenta >>>>> "+pCuenta);                                                            
                    vPrint.activateCondensed();
                    /*
                    vPrint.printLine("linea 1",true);
                    vPrint.printLine("linea 2",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("",true);
                    vPrint.printLine("linea final",true);
*/          
                    //for(int i=0;i<=6;i++)
                    /*for(int i=0;i<=25;i++)
                       vPrint.printLine("----------------------"+i,true);*/
                    
                    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(52)+pFecha,true);// -- 1
                    vPrint.printLine("",true);// -- 1
                    vPrint.printLine("",true);// -- 2
                    vPrint.printLine("",true);// -- 3
                    vPrint.printLine("",true);// -- 4
                    vPrint.printLine("",true);// -- 5
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(52)+"Recaudacion",true);// -- 9
                    vPrint.printLine("",true);// -- 6
                    vPrint.printLine("",true);// -- 6
                    if(FarmaVariables.vCodCia.equals(ConstantsRecaudacion.CODGRUPOCIA_MIFARMA)){
                            vPrint.printLine(FarmaPRNUtility.llenarBlancos(38)+pCuenta,true);// -- 7    
                    }else if(FarmaVariables.vCodCia.equals(ConstantsRecaudacion.CODGRUPOCIA_FASA)){
                        vPrint.printLine("",true);// -- 7
                    }
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38)+pCliente,true);// -- 8
                    vPrint.printLine("",true);// -- 6
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(41)+pNombreEmpresaLocal,true);// -- 10
                    vPrint.printLine("",true);// -- 12
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38)+pParaBanco,true);// -- 11
                    
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38)+pDirecLocal,true);// -- 13
                    vPrint.printLine("",true);// -- 14
                    vPrint.printLine("",true);// -- 12
                    
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(38)+"Cantidad Sobres => "+pNumeroSobres,true);// -- 15
                    vPrint.printLine("",true);// -- 16
                    
                    
                    if(FarmaUtility.getDecimalNumber(pMontSoles) > 0){
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7) +
                                         FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontSoles).trim()+ " NUEVOS SOLES" ,65) + "" + 
                                         FarmaPRNUtility.llenarBlancos(15)+  "S/. "+
                                         FarmaPRNUtility.alinearIzquierda(pMontSoles,15) ,true);// -- 18
                    }else{
                            vPrint.printLine("",true);// -- 17
                        }
                    
                    vPrint.printLine("",true);// -- 19
                    vPrint.printLine("",true);// -- 20
                    if(FarmaUtility.getDecimalNumber(pMontDolares) > 0){
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(7) +
                                         FarmaPRNUtility.alinearIzquierda(valorEnLetras(pMontDolares).trim()+ " DOLARES AMERICANOS" ,65) + "" + 
                                         FarmaPRNUtility.llenarBlancos(15)+ "$/. "+
                                         FarmaPRNUtility.alinearIzquierda(pMontDolares,15) ,true);// -- 21
                    }else{
                            vPrint.printLine("",true);// -- 20    
                        }
                    vPrint.printLine("",true);// -- 22
                    //vPrint.printLine(FarmaPRNUtility.llenarBlancos(20)+pPrecinto,true);// -- 22

                    vPrint.endPrintService();
                }
                
            } catch (Exception e) {
                log.error("",e);
            }
            
        }
    }
    
    private static String valorEnLetras(String pCadena ) {
        
        Double valor = new Double(FarmaUtility.getDecimalNumber(pCadena));
        
        String centavos = "00";
        double doubleValor = Double.parseDouble(valor.toString());
        int numero = valor.intValue();
        int posPunto = String.valueOf(valor).indexOf(".");
        int posComa = String.valueOf(valor).indexOf(",");
        double doubleNumero = Double.parseDouble(String.valueOf(numero));
        if (posPunto > 0 || posComa > 0) {
            if (posPunto > 0)
                centavos = String.valueOf(valor).substring(posPunto + 1);
            if (posComa > 0)
                centavos = String.valueOf(valor).substring(posComa + 1);
        } else
            centavos = "00";

        String cadena = "";
        int millon = 0;
        int cienMil = 0;

        if (numero < 1000000000) {

            if (numero > 999999) {
                millon = (new Double(numero / 1000000)).intValue();
                numero = numero - millon * 1000000;
                cadena += 
                        base(millon, true) + (millon > 1 ? " MILLONES " : " MILLON ");
            }
            if (numero > 999) {
                cienMil = (new Double(numero / 1000)).intValue();
                numero = numero - cienMil * 1000;
                cadena += base(cienMil, false) + " MIL ";
            }

            cadena += base(numero, true);

            if (cadena != null && cadena.trim().length() > 0) {
                cadena += " CON ";
            }

            if (centavos.trim().length() == 1)
                centavos += "0";
            cadena += String.valueOf(centavos) + "/100";

        }

        return cadena.trim() + "";

    }    
    
    public static List getDatosImpresoraTerminca(String pCodGrupoCia, String pCodLocal, String pIpPc) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pIpPc);
        log.info("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)", parametros);

    }
    
    public static void imprimeTermicaRemito(JDialog pDialogo,String NumRemito){
        
        try {
            String nomImpTermica = "";
            String tipoImpTermica = "";

            List lstDatosImpresora =
                getDatosImpresoraTerminca(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, FarmaVariables.vIpPc);
            if (lstDatosImpresora.size() > 0) {
                Map datoImpresora = (HashMap)lstDatosImpresora.get(0);
                tipoImpTermica = (String)datoImpresora.get("TIPO");
                nomImpTermica = (String)datoImpresora.get("IMPRESORA");
            } else {
                throw new Exception("Error al consultar datos de impresora termica.\n Verifique configuraci�n en \"Mantenimiento de Impresoras\".");
            }

            //VariablesPtoVenta.vImpresoraActual = "SALA10";
            VariablesPtoVenta.vTipoImpTermicaxIp = tipoImpTermica;

            PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);
            if (servicio != null) {
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    if (pNameImp.indexOf(nomImpTermica) != -1) {
                        VariablesPtoVenta.vImpresoraActual = impresora;
                    }
                }

            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

        //if(servicio != null)
        //{
          try
          {
            //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            
             //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
            
            //for (int i = 0; i < servicio.length; i++)
            //{
              //PrintService impresora = servicio[i];
              //String pNameImp = impresora.getName().toString().trim(); 
              //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
              //{
              
               String vIndImpre = "S";
              //DBCaja.obtieneIndImpresion();
               log.debug("vIndImpre :"+vIndImpre);
                if (!vIndImpre.equals("N"))
                {
                  //String htmlRemitos = DBCaja.obtieneDatosVoucherRem(NumRemito,FarmaVariables.vIPBD);
                   String htmlRemitos=DBCajaElectronica.getHTML_VOUCHER_REMITO(NumRemito); //ASOSA, 22.04.2010
                  log.debug("htmlRemitos:"+htmlRemitos);
                  
                  PrintConsejo.imprimirHtml(htmlRemitos,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                    FarmaUtility.showMessage(pDialogo, "Se asign� las fechas al nuevo remito con �xito \n" +
                     "Voucher impreso con �xito.", null);
                    //FarmaUtility.showMessage(pDialogo, "Voucher impreso con �xito.",null);            
                  //break;
                }
              //}
            //}
          }catch (SQLException sqlException)
          {
            //log.error("",sqlException);
           /* if(sqlException.getErrorCode() == 06502){
            FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
            }else */{log.error(null,sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
            }
            
          }
        //}        
    }
    
    
    /**
     * Se imprime ticket al consultar recarga virtual 
     * @author Asolis
     * @since 11.02.2009
     */

   // public static void imprimeTicket(JDialog pDialogo,String pNumPedVta,String pFechaVenta,String pProveedor,String pTelefono,int pMonto,String pRespRecarga ,String pComunicado)
   
    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeTicket(JDialog pDialogo,String pNumPedVta,int pMonto)
    
   
    {
     //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

     //if(servicio != null)
     //{
       try
       {
         //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
         
           //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
           
         //for (int i = 0; i < servicio.length; i++)
         //{
           //PrintService impresora = servicio[i];
           //String pNameImp = impresora.getName().toString().trim(); 
           //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
           //{
           
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             {
                
               String htmlTicket = DBCaja.obtieneDatosTicket(pNumPedVta,pMonto);
                  
               log.debug("htmlRemitos:"+htmlTicket);
               PrintConsejo.imprimirHtml(htmlTicket,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                 FarmaUtility.showMessage(pDialogo, "Ticket impreso con �xito.",null);            
               //break;
             }
           //}
         //}
       }catch (SQLException sqlException)
       {
         {log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de Ticket.", null);
         }
         
       }
     //}
    }
    
    public static void bloqueoCajaApertura(String pSecCaja){
        
        try{
            log.debug("Inicio de bloqueo para el cobro...");
            DBCaja.bloqueoCaja(pSecCaja.trim());
           }catch (SQLException e)
          {
            log.debug("Error:"+e);
              FarmaUtility.liberarTransaccion();
          }
        log.debug("Fin de bloqueo continua bloqueado y s");
    }
    
    /**
     * Imprime prueba en nueva clase.
     * @author ERIOS
     * @since 14.06.2013
     * @param pJDialog
     * @param ruta
     * @param nombreTicket
     * @param pSecImpr
     */
    public static void imprimePruebaTermicaPorIP(JDialog   pJDialog,
                                                  String    ruta,
                                                  String   nombreTicket,
                                                 String pSecImpr){        
  
    String vModelo;
    
    try {
         vModelo = DBImpresoras.getModeloImpresora(pSecImpr);   
        ArrayList<String> vPrint = new ArrayList<String>();
        vPrint.add("***************************************");
        vPrint.add("PRUEBA DE IMPRESION TICKETERA");
        vPrint.add("NOMBRE : "+nombreTicket);
        vPrint.add("MODELO : "+vModelo);
        vPrint.add("***************************************");    
        
        ImpresoraTicket ticketera = new ImpresoraTicket();  
     
        ticketera.imprimir(vPrint, vModelo, ruta,false,"", "","","",vPrint);
   
        FarmaUtility.showMessage(pJDialog, 
                                  "Se realiz� la prueba de impresi�n a "+nombreTicket.trim()+
                                  " , recoja la impresi�n. ",null);   
        
    } 

    catch (SQLException e) {
        log.error(null,e);
        FarmaUtility.showMessage(pJDialog,
                               "Error al realizar prueba de impresion."+e.getMessage()+""
                                ,null);

    }
  
   
    }

    /**
     * CENTRA LA CADENA SEGUN EL TAMA�O QUE MANDEN
     * @param pCadena
     * @param pLongitud
     * @param pCaracter
     * @return
     */
    public static String pFormatoLetra(String pCadena,int pLongitud,String pCaracter){
        int pTama�o = pCadena.trim().length();
        int numeroPos = (int)Math.floor((pLongitud - pTama�o)/2);
        String pCadenaNew = "";
        //(pLongitud - pTama�o)/2
        //Math.floor(nD * Math.pow(10,nDec))/Math.pow(10,nDec);
        //log.debug(Math.floor(7/2));
        for(int i=0;i<numeroPos;i++){
            pCadenaNew += pCaracter;
        }
        pCadenaNew += pCadena.trim();
        pTama�o =  pLongitud - pCadenaNew.length();
        
        for(int i=0;i<pTama�o;i++){
            pCadenaNew += pCaracter;
        }
        
        //log.debug("cadena:"+pCadena);
        //log.debug("numeroPos:"+numeroPos);
        //log.debug("pCadenaNew:"+pCadenaNew);
        return  pCadenaNew;
    }    

    private static void imprimeBoletaTicket(JDialog   pJDialog,
                                            String    pFechaBD,
                                            ArrayList pDetalleComprobante,
                                            String    pValTotalNeto,
                                            String    pValRedondeo,
                                            String    pNumComprobante,
                                            String    pNomImpreso,
                                            String    pDirImpreso,
                                            String    pValTotalAhorro,
                                            String    pruta,
                                            boolean   indAnulado,
                                            String    tipoComp,
                                            int       valor) throws Exception {
        //ERIOS 13.06.2013 Imprime en clase generica, que determina el tipo de ticketera.
        ArrayList<String> vPrint = new ArrayList<String>();
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;           
        
            try {
                VariablesCaja.vModeloImpresora= "TMU950";
                log.info("MODELO:"+VariablesCaja.vModeloImpresora);
                
                // datos de tratamiento 19.09.2015
                ArrayList vDatosTratamiento = new ArrayList();
                DBCaja.getDatosTratamiento(vDatosTratamiento,VariablesCaja.vNumPedVta);
                ArrayList<String> vPrintTratamiento = new ArrayList<String>();
                // datos de tratamiento 19.09.2015
                
                ImpresoraTicket ticketera = new ImpresoraTicket();
                ticketera.generarDocumento(pJDialog,                        vPrint,
                                           pNomImpreso,                     pDirImpreso,
                                           pFechaBD,                        pNumComprobante,
                                           pDetalleComprobante,             pValTotalNeto,
                                           pValTotalAhorro,                 pValRedondeo,
                                           VariablesCaja.vModeloImpresora,  indAnulado,
                                           tipoComp,                        valor,
                                           vPrintTratamiento,vDatosTratamiento
                                           );
                boolean bImprimio = ticketera.imprimir(vPrint,
                                                       VariablesCaja.vModeloImpresora,
                                                       VariablesCaja.vRutaImpresora,
                                                       true,
                                                       pNumComprobante,
                                                       "C",
                                                       VariablesCaja.vNumPedVta,
                                                   tipoComp,vPrintTratamiento);    
                //ticketera.abrirGabeta(VariablesCaja.vModeloImpresora,VariablesCaja.vRutaImpresora);
                if(!bImprimio){
                    VariablesCaja.vEstadoSinComprobanteImpreso="S";
                }else{
                    VariablesCaja.vEstadoSinComprobanteImpreso="N";                        
                }
                
            }catch(SQLException sql)
            {
                VariablesCaja.vEstadoSinComprobanteImpreso="S";

                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la boleta 4: " + sql.getMessage());
                log.error(null,sql);
                
                enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            }catch(Exception e){
                e.printStackTrace();
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la boleta 5: "+e);
                
                //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
            } 
        
    }

    /**
     * LLEIVA 24-Ene-2014
     * @param pJDialog
     * @param pNumPedVta
     */
    private static void imprimeFactTicket (JDialog   pJDialog,
                                            String    pFechaBD,
                                            ArrayList pDetalleComprobante,
                                            String    pValTotalNeto,
                                            String    pValRedondeo,
                                            String    pNumComprobante,
                                            String    pNomImpreso,
                                            String    pRUCImpreso,
                                            String    pValTotalAhorro,
                                            String    pruta,
                                            boolean   indAnulado,
                                            String    tipoCompr,
                                            int       valor) throws Exception
    {
        //ERIOS 13.06.2013 Imprime en clase generica, que determina el tipo de ticketera.
        ArrayList<String> vPrint = new ArrayList<String>();
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        FarmaVariables.vNroPedidoNoImp = VariablesCaja.vNumPedVta;           
        
        try
        {
            ImpresoraTicket ticketera = new ImpresoraTicket();
            ticketera.generarDocumento(pJDialog,                        vPrint,
                                        pNomImpreso,                    pRUCImpreso,
                                        pFechaBD,                       pNumComprobante,
                                        pDetalleComprobante,            pValTotalNeto,
                                        pValTotalAhorro,                pValRedondeo,
                                        VariablesCaja.vModeloImpresora, indAnulado,
                                        tipoCompr,                      valor,new ArrayList(),new ArrayList());
            boolean bImprimio = ticketera.imprimir(vPrint,
                                                   VariablesCaja.vModeloImpresora,
                                                   VariablesCaja.vRutaImpresora,
                                                   true,
                                                   pNumComprobante,
                                                   "C",
                                                   VariablesCaja.vNumPedVta,
                                                   tipoCompr,new ArrayList());
			log.debug("��IMPRIMIRO?? : "+bImprimio);
            ticketera.abrirGabeta(VariablesCaja.vModeloImpresora,VariablesCaja.vRutaImpresora);
            if(!bImprimio)
            {   VariablesCaja.vEstadoSinComprobanteImpreso="S";
            }
            else
            {   VariablesCaja.vEstadoSinComprobanteImpreso="N";                        
            }
        }
        catch(SQLException sql)
        {
            VariablesCaja.vEstadoSinComprobanteImpreso="S";
            log.info("**** Fecha :"+ pFechaBD);
            log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al imprimir la boleta 4: " + sql.getMessage());
            log.error(null,sql);
            enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
        }
        catch(Exception e)
        {
            VariablesCaja.vEstadoSinComprobanteImpreso="S";
            log.info("**** Fecha :"+ pFechaBD);
            log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al imprimir la boleta 5: "+e);
            enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
        } 
    }

    /**
     * mfajardo 
     * @param pJDialog
     * @param pNumPedVta
     */
     //mfajardo -imprime mensaje campana- 13.04.2009
    private static void imprimeMensajeCampana(JDialog pJDialog,String pNumPedVta) {

          try
          {
           // String pTipoImp = DBCaja.obtieneTipoImprConsejo();
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :"+vIndImpre);
                if (!vIndImpre.equals("N"))
                {
                    String htmlTicket = DBCaja.ObtieneCampanas(pNumPedVta);
                  log.debug("htmlRemitos:"+htmlTicket);
                  if(!htmlTicket.equals("N"))
                  {
                    PrintConsejo.imprimirHtml(htmlTicket,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                  }
                }
          }catch (Exception sqlException)
          {
              {log.error(null,sqlException);
              FarmaUtility.showMessage(pJDialog, "Error al obtener los datos de Ticket.", null);
                  //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                    enviaErrorCorreoPorDB(sqlException.toString(),"<br>Error Al Obtener Datos de Ticket");
              }
            
          }
    }
    
    /**
     * Imprime Motivo Anulacion de Ticket
     * @param cajero
     * @param turno
     * @param numpedido
     * @param cod_igv
     * @param ruta
     * @param valor
     * @param pIndReimpresion
     * @return
     * @throws Exception
     */
    public static boolean imprimeMensajeTicketAnulacion(String cajero, String turno, 
                                       String numpedido, String cod_igv, 
                                       String ruta, boolean valor, 
                                       String pIndReimpresion) throws Exception
    { 
        //ERIOS 14.06.2013 Imprime anulacion en nueva clase
        String vIndImpre  = "S";
        boolean vResultado = false;
        if (!vIndImpre.equals("N"))
        {
            String htmlTicket = DBCaja.ImprimeMensajeAnulacion(cajero,turno,numpedido,cod_igv,pIndReimpresion);          
            if (!htmlTicket.equals("N"))                      
            {
                ArrayList myArray = null;
                StringTokenizer st = null;
                myArray = new ArrayList();
                st = new StringTokenizer(htmlTicket, "�");

                while (st.hasMoreTokens())
                {   myArray.add(st.nextToken());
                }          
                      
                int cajaUsuario;
                cajaUsuario = DBCaja.obtieneNumeroCajaUsuarioAux();
                VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();
               
                String secImprLocal = VariablesCaja.vSecImprLocalTicket;
                
                try
                {   VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);
                    VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
                }
                catch(Exception e)
                {   log.debug("No se obtuvo una impresora valida", e);
                    return false;
                }

                ArrayList<String> vPrint = new ArrayList<String>();
           
                Calendar fechaJava = new GregorianCalendar();
                int dia=fechaJava.get(Calendar.DAY_OF_MONTH);
                int resto= dia % 2;
                
                if(resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
                {
                    vPrint.add((char)27+"4"); //rojo 
                }
                else
                {
                    vPrint.add((char)27+"5"); //negro
                }
          
                vPrint.add(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulaci�n de Pedido----------");
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal+" - "+FarmaVariables.vDescCortaLocal);
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creaci�n: " + myArray.get(7));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: "+myArray.get(1));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulaci�n: "+myArray.get(2));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Caja: "+myArray.get(3) + " Turno: " + myArray.get(4));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " +myArray.get(5));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + myArray.get(6));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9));
                vPrint.add(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulaci�n de Pedido----------");
            
                ImpresoraTicket ticketera = new ImpresoraTicket();
                String pNumComprobante = myArray.get(8).toString();
                vResultado = ticketera.imprimir(vPrint,
                                                                VariablesCaja.vModeloImpresora,
                                                                VariablesCaja.vRutaImpresora,
                                                                true,
                                                                pNumComprobante,
                                                                "A",
                                                                numpedido,
                                                                "",new ArrayList());
				log.debug("��IMPRIMIRO?? : "+vResultado);
                //DBCaja.actualizaFechaImpr(numpedido,pNumComprobante,"A");
            }
        }
        return vResultado;
    }
    
    private static String getDetallePrecio(String pTipoPedido,ArrayList pListaDetalle,int p){
        String pcadena;
        if(pTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL))
        {
           pcadena = FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(17)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
           FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(18)).trim(),10)                                ;
        }
        else
        {
           pcadena = FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
           FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pListaDetalle.get(p)).get(5)).trim(),10) ;
        }
        
        return pcadena;
    }
    
    
    /**
     * Se valida ip de la maquina para la emision de ticket
     * @author  JCORTEZ
     * @since  09.06.09
     * */
    private static boolean  validaImpresioPorIP(String IP,String TipComp,JDialog pJDialog, Object pObjectFocus){
    boolean valor=false;
    String resp="";
    
        try{    
        resp=DBCaja.validaImpresioPorIP(IP,TipComp);
        if(resp.trim().equalsIgnoreCase("S"))
        valor=true;
        log.debug("ip: impresora:   "+resp);
        }catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.showMessage(pJDialog,"Ocurrio un error al cargar el reporte.\n"+sql.getMessage(),pObjectFocus);
            
              //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                enviaErrorCorreoPorDB(sql.toString(),null);
          }
        return valor;
    }
    
    
    
    /**
       * Valida si existe impresora asociada a la IP desde donde se realizara el cobro
       * @AUTHOR JCORTEZ
       * @SINCE 09.06.09
       */
    public static boolean existeIpImpresora(JDialog pDialog, Object pObjectFocus) {
        boolean existeImpresoraIP = true;
        String Sec="";
          try {
              log.info("******* FarmaVariables.vIpPc : "+FarmaVariables.vIpPc);
              Sec = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
              log.info("******* Secuencial de impresora : "+Sec);
                if(Sec.trim().equalsIgnoreCase("N")){
                    existeImpresoraIP=false;
                    FarmaUtility.showMessage(pDialog,"La IP actual no tiene asignada ninguna impresora. Verifique !!!", pObjectFocus);
                }
          } catch (SQLException sqlException) {
              //log.error("",sqlException);
              log.error(null, sqlException);
              FarmaUtility.showMessage(pDialog,"Error al obtener relacion impresora - IP " + sqlException.getErrorCode(),pObjectFocus);
          }
        log.info("******* existeImpresoraIP: "+existeImpresoraIP);
        return existeImpresoraIP;
    }


    public static void pruebaImpTermicaPersonalizada(JDialog pDialogo,Object pObject,String pNombreImpresora,String pTipo)
    {
      PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      boolean vIndImpresion = false;
      String numAux = "000"+numeroCorrel
          ;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
      if(servicio != null)
      {
        try
        {
        //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
          int cantIntentosLectura = 2;//Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          for (int i = 0; i < servicio.length; i++)
          {
            PrintService impresora = servicio[i];
            String pNameImp = impresora.getName().toString().trim();
            String pNombre = retornaUltimaPalabra(pNameImp,"\\");
            //if (retornaUltimaPalabra(pNameImp,"\\").trim().toUpperCase().indexOf(pNombreImpresora.trim().toUpperCase()) != -1)
            if (pNombre.trim().toUpperCase().equalsIgnoreCase(pNombreImpresora.trim().toUpperCase()))  
            {
              vIndImpresion = true;
              String vCupon = DBCaja.pruebaImpresoraTermica(pCodCupon);
              log.debug("Impresion de imagen"+vCupon);
              log.debug(" prueba de impresion termica a : "+ impresora.getName());
              log.debug(" pNombreImpresora:"+ pNombreImpresora);
              log.debug(" pTipo:"+ pTipo);
                
              PrintConsejo.imprimirCupon(vCupon,impresora,pTipo,pCodCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              FarmaUtility.showMessage(pDialogo, 
                                        "Se realiz� la prueba de impresi�n a "+pNombreImpresora.trim()+
                                        " , recoja la impresi�n.", pObject);
              
              break;
            }
          }
          
            if(!vIndImpresion){
                FarmaUtility.showMessage(pDialogo, 
                                         "No existe la impresora t�rmica "+pNombreImpresora.trim()+
                                          "\nverificar que se encuentre instalada en la PC.", pObject);
            }
        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresion.",pObject);

        }
        
      }
    }
    
    public static void printDefault() {
    
        // tu impresora por default
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        log.debug("Tu impresora por default es: " + service.getName());
    
    }
    
    public static void ImprimirContrasenia(JDialog pDialogo)
    {
      /*PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
     // numeroCorrel++;
      boolean vIndImpresion = false;
     // String numAux = "000"+numeroCorrel;
     // String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
      PrintService nomimpresora = PrintServiceLookup.lookupDefaultPrintService();
      //String nomImpresora=
      if(servicio != null)
      {
        try
        {
          //String pTipoImp = DBCaja.obtieneTipoImprConsejo();//JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          for (int i = 0; i < servicio.length; i++)
          {
             PrintService impresora = servicio[i];
             String pNameImp = impresora.getName().toString().trim();
            // String pNombre = retornaUltimaPalabra(pNameImp,"\\");
            if (retornaUltimaPalabra(pNameImp,"\\").trim().toUpperCase().indexOf(pNombre.trim().toUpperCase()) != -1)
            {           
              vIndImpresion = true;
              String htmlstore = DBCaja.ImprimirContrasenia();
              log.debug("Impresion de imagen"+vCupon);
              log.debug(" prueba de impresion termica a : "+ impresora.getName());
              log.debug(" pNombreImpresora:"+ pNombre);
              log.debug(" pTipo:"+ pTipoImp);
                
              PrintConsejo.imprimirCupon(vCupon,impresora,pTipoImp,pCodCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              FarmaUtility.showMessage(pDialogo, 
                                        "Se envio la impresi�n a "+pNombre+
                                        " , recoja la impresi�n.",null);
              
              break;
            }
          }
          
            if(!vIndImpresion){
                FarmaUtility.showMessage(pDialogo, 
                                         "No existe la impresora t�rmica "+nomimpresora+
                                          "\nverificar que se encuentre instalada en la PC.",null);
            }
        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresion.",null);

        }
        
      }*/
    }
    public static String retornaUltimaPalabra(String pCadena,String pSeparador){
        log.debug(pCadena);
        log.debug(pSeparador);

        
        String pLetra = "";
        String pPalabraOut="";
        for(int i=pCadena.length()-1;i>=0;i--){
            pLetra = pCadena.charAt(i)+"";
            if(pLetra.trim().equalsIgnoreCase(pSeparador.trim())){
                break;
            }
            else{
                pPalabraOut = pLetra + pPalabraOut;
            }
        }
        return pPalabraOut.trim();
    }
    
    public static void enviaErrorCorreoPorDB(String message, String vCorrelativo)  {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
        log.info(" VariablesPtoVenta.vDestEmailErrorImpresion >> "+ VariablesPtoVenta.vDestEmailErrorImpresion);
        log.info(" VariablesPtoVenta.vCorrelativo >> "+ vCorrelativo);
        /*FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                      VariablesPtoVenta.vDestEmailErrorImpresion,
                                      "Error al Imprimir Pedido Completo ",
                                      "Error de Impresi�n StartPrintService",
                                      "Se produjo un error al imprimir un pedido :<br>"+
                                      //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                      "Correlativo : " +vCorrelativo+"<br>"+
                                      "IP : " +FarmaVariables.vIpPc+"<br>"+
                                      "Error: " + message ,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                                      "");*/
        log.info("Error en BD al Imprimir los Comprobantes del Pedido.\n"+message);
        
    }
    
    public static void enviaErrorCorreoPorDB(Exception message, String vCorrelativo)  {
        //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime 
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_ERROR_IMPRESION,
                                      VariablesPtoVenta.vDestEmailErrorImpresion,
                                      "Error al Imprimir Pedido Completo ",
                                      "Error de Impresi�n StartPrintService",
                                      "Se produjo un error al imprimir un pedido :<br>"+
                                      //"Correlativo : " +VariablesCaja.vNumPedVta_Anul+"<br>"+
                                      "Correlativo : " +vCorrelativo+"<br>"+
                                      "IP : " +FarmaVariables.vIpPc+"<br>"+
                                      "Error: " + message ,
                                      //ConstantsCaja.EMAIL_DESTINATARIO_CC_ERROR_IMPRESION
                                      "");
        log.info("Error en BD al Imprimir los Comprobantes del Pedido.\n"+message);
        
    }
    
    
    /**
    * Se imprime la comanda al cobrar un pedido tipo delivery
    * @AUTHOR JCORTEZ
    * @SINCE 07.08.09
    */
    public static void imprimeDatosDeliveryLocal(JDialog pDialogo,String NumPed)
    {
       try
       {
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             { 
               String htmlDelivery = DBCaja.obtieneDatosDeliveryLocal(NumPed,FarmaVariables.vIPBD);
               PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
               //break;
             }
       }catch (SQLException sqlException)
       {
         //log.error("",sqlException);
         log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener datos del pedido delivery Local.", null);
       }
    }
    
    
    /**
     * Se elimina productos regalo de encarte vigente dentro del pedido.
     * @author JCORTEZ
     * @since  13.08.2009
     */
    public static void liberaProdRegalo(String pNumPed,
                                             String pAccion,
                                             String pIndEliminaRespaldo) throws SQLException{//ASOSA,13.07.2010 - agregue el throws y quit el try-catch para que no este cun try-catch dentro de otro
        //try{
        
            //JCORTEZ 13.08.09  Se elimina producto regalo
            log.debug("****************JCORTEZ********************");
            log.debug("pAccion-->"+pAccion);
            log.debug("pIndEliminaRespaldo-->"+pIndEliminaRespaldo);
            //DBCaja.eliminaProdRegalo(pNumPed,pAccion,pIndEliminaRespaldo); antes
            DBCaja.eliminaProdRegalo_02(pNumPed,pAccion,pIndEliminaRespaldo); //ASOSA, 13.07.2010
            //return true;
        /*}catch (SQLException e){
            log.error("",e);
            log.debug("Error al eliminar productos regalo encarte.");
            //return false;
        }*/
    }
    
             
     /**
      * Se imprime cupones regalo 
      * @AUTHOR JCORTEZ
      * @SINCE 18.07.09
      */
     public static void imprimeCuponRegalo(JDialog pDialogo,String vCodeCupon,String Dni)
     {
       int cant_cupones_impresos = 0;
       
         try
         {
           //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
           //String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
           int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
               String vCupon = DBCaja.obtieneImprCuponRegalo(FarmaVariables.vIPBD,vCodeCupon,Dni);
               if(!vCupon.equals("N"))
               {
                   log.debug("cupon regalo a imprimir : "+vCupon);
                   PrintConsejo.imprimirCupon(vCupon,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp,vCodeCupon, cantIntentosLectura);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                   log.debug("despues a imprimir");
                   // -- Proceso autonomo que tiene COMMIT
                   DBCaja.cambiaIndImpresionCupon(VariablesCaja.vNumPedVta,vCodeCupon);
                   cant_cupones_impresos ++;
               }   
         }
         catch (SQLException sqlException)
         {
          //log.error("",sqlException);
           log.error(null,sqlException);
          FarmaUtility.showMessage(pDialogo,"Error al obtener los consejos.", null);
         }
            
    }
    
    /**
     * Se obtiene mensaje de ahorro en comprobantes 
     * @AUTHOR JCORTEZ
     * @SINCE  03.09.2009
     * */
   public static String obtenerMensaAhorro(JDialog pDialogo,String indFid){
    
     String mensaje="";
        try
        {   
         mensaje = DBCaja.obtieneMensajeAhorro(indFid);
        }
        catch (SQLException sqlException)
        {
         log.error("",sqlException);
         ///FarmaUtility.showMessage(pDialogo,"Error al obtener mensaje de descuento.", null);
        }
    return mensaje;
    
    }
    
    /**
     * Se valida guias pendientes
     * @AUTHOR JCORTEZ
     * @SINCE  27.10.2009
     */
    public static boolean validaGuiasPendAlmc()
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.ExistsGuiasPendAlmc().trim();
        } catch(SQLException sql){
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
  
    /**
    * Se imprime VOUCHER para diferencias
    * @author JCHAVEZ
    * @since 23.11.09
    */     
    public static void imprimeVoucherDiferencias(JDialog pDialogo)
    {
     //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);

     //if(servicio != null)
     //{
       try
       {
         //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
         
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP
         
         //for (int i = 0; i < servicio.length; i++)
         //{
           //PrintService impresora = servicio[i];
           //String pNameImp = impresora.getName().toString().trim(); 
           //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
           //{
           
            String vIndImpre = DBCaja.obtieneIndImpresion();
            log.debug("vIndImpre :"+vIndImpre);
             if (!vIndImpre.equals("N"))
             {
               String htmlDiferencias = DBRecepCiega.obtieneDatosVoucherDiferencias();
               log.debug("htmlDiferencias:"+htmlDiferencias);
               PrintConsejo.imprimirHtml(htmlDiferencias,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);
                 FarmaUtility.showMessage(pDialogo, "Voucher impreso con �xito. \n", null);
                 //FarmaUtility.showMessage(pDialogo, "Voucher impreso con �xito.",null);            
               //break;
             }
           //}
         //}
       }catch (SQLException sqlException)
       {
         //log.error("",sqlException);
        /* if(sqlException.getErrorCode() == 06502){
         FarmaUtility.showMessage(pDialogo,"error: Existen demasiados valores para impresion.",null);
         }else */{log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de VOUCHER.", null);
         }
         
       }
     //}

    }  
    /**
     * Se valida guias pendientes de confirmar de locales
     * @AUTHOR JMIRANDA
     * @SINCE  15.12.2009
     */
    public static boolean validaGuiasXConfirmarLocal()
    {
        String pRes = "";
        try
        {
            pRes = DBCaja.ExisteGuiasXConfirmarLocal().trim();
            log.debug("pRes ConfirmarLocal: "+pRes);
        } catch(SQLException sql){
            log.error("",sql);
            pRes = "N";          
        }
        
        if(pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        
        return false;
    }
    
    /**
        * Se imprime VOUCHER por pedido convenio
        * @AUTHOR Jorge Cortez Alvarez
        * @SINCE 07.03.2010
        */
        public static void imprimeDatoConvenio(JDialog pDialogo,
                                                String NumPed,
                                                  String CodConvenio, 
                                                    String CodCli)
        {
           try
           {
                String vIndImpre = DBCaja.obtieneIndImpresion();
                log.debug("vIndImpre :"+vIndImpre);
                 if (!vIndImpre.equals("N"))
                 {
                     log.debug("NumPed :"+NumPed);  
                     log.debug("CodConvenio :"+CodConvenio);  
                     log.debug("CodCli :"+CodCli);  
                   String htmlDelivery = DBCaja.obtieneDatosConvenio(NumPed,CodConvenio,CodCli,
                                                                        FarmaVariables.vIPBD);
                     log.debug(htmlDelivery);  
                   PrintConsejo.imprimirHtml(htmlDelivery,VariablesPtoVenta.vImpresoraActual,VariablesPtoVenta.vTipoImpTermicaxIp);
                 }
           }catch (SQLException sqlException)
           {
             log.error(null,sqlException);
             FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de convenio.", null);
           }
        }
    
    
    /**
     * Prueba Impresora Termica de Stickers
     * @author Juan Quispe
     * @since  28.12.2010
     */
     //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static boolean pruebaImpresoraTermicaStk(JDialog pDialogo,Object pObject,double pCantidadFilas,int r_incio,int r_fin)
    {
      //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
      numeroCorrel++;
      String numAux = "000"+numeroCorrel;
      String pCodCupon = "9999999999"+numAux.substring(numAux.length()-3, numAux.length());
      int cant_cupones_impresos = 0;
        String vCupon=null;
      //if(servicio != null)
      //{
        try
        {
          //String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
          
          //  String pTipoImp = DBCaja.obtieneTipoImprConsejo(); JCHAVEZ 03.07.2009 se coment� para obtener el tipo de impresora por IP         
          
          int cantIntentosLectura = Integer.parseInt(DBCaja.obtieneCantIntentosLecturaImg().trim());
          
          //for (int i = 0; i < servicio.length; i++)
          //{
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();
            
            //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
            //{
              
              vCupon = DBCaja.pruebaImpresoraTermStick(pCodCupon,r_incio,r_fin);
            log.debug(vCupon);
              log.debug(" prueba de impresion termica...");
            //String vCupon = "";
           
            //JSANTIVANEZ 10.01.2011
            //metodo para obtener la ruta de la impresora...
            //si es 'n' no se ha declarado la ip(no hay impresora)
            PrintService temporalNomImpCupon;
            
            temporalNomImpCupon=VariablesPtoVenta.vImpresoraActual;
            
            boolean flag=nombreImpSticker();
            
            if (flag==true)
            
           PrintConsejo.imprimirCuponStick(vCupon,VariablesPtoVenta.vImpresoraSticker,VariablesPtoVenta.vTipoImpTermicaxIp,pCodCupon, cantIntentosLectura,
                                             pCantidadFilas);//JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
              //break;
            //}
          //}
          
          
          //JS
          /*FarmaUtility.showMessage(pDialogo, 
                                  "Se realiz� la prueba de impresi�n, recoja la impresi�n.", pObject);*/
           else
            
                 JOptionPane.showMessageDialog(null, "No se encontr� la impresora sticker :"+                                            
                                               "\nVerifique que tenga instalada la impresora.", 
                                               "Mensaje del Sistema", 
                                               JOptionPane.WARNING_MESSAGE);
                           
          //VariablesPtoVenta.vImpresoraActual=temporalNomImpCupon;

        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
         FarmaUtility.showMessage(pDialogo, 
                                 "Error al realizar prueba de impresi�n.",pObject);

        }
        if (vCupon== null )
            return false;//NO hay datos
        else
                return true;//hay datos
      //}
    }        
    
    //JSANTIVANEZ 10.01.2011
    public static boolean  nombreImpSticker()
    {
        String pNombreImpresora = "";
        String nameImpSticker = ""; //nombre impresora 
        
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        boolean pEncontroImp = false;
      if(servicio != null)
      {
        try
        {
                  
            //devuelve nombre impresora sticker "esticker01"
                  nameImpSticker=DBCaja.obtieneNameImpSticker();
            //VariablesPtoVenta.vTipoImpTermicaxIp = "01"; //tipo epson
                  log.debug("Tipo Impresora :" + VariablesPtoVenta.vTipoImpTermicaxIp);  
                  log.debug("Buscando impresora :"+nameImpSticker);
                  log.debug("impresoras..encontradas...");
                  for (int i = 0; i < servicio.length; i++)
                  {
                    PrintService impresora = servicio[i];
                    String pNameImp = impresora.getName().toString().trim();
                    pNombreImpresora = retornaUltimaPalabra(pNameImp,"\\").trim();
                   
                    //Buscara el nombre.
                    log.debug(i+") pNameImp:"+pNameImp);
                    log.debug(i+") pNombreImpresora:"+pNombreImpresora);
                      log.debug(i+") nameImpSticker:"+nameImpSticker);  
                    log.debug("**************************************");
                    if (pNombreImpresora.trim().toUpperCase().equalsIgnoreCase(nameImpSticker.toUpperCase()))
                    {
                      log.info("Encotr� impresora t�rmica");
                      pEncontroImp = true;
                      VariablesPtoVenta.vImpresoraSticker =  impresora;
                      break;
                    }
                  }
        }
        catch (SQLException sqlException)
        {
          log.error(null,sqlException);
        }
        }
        
        return pEncontroImp;
        /*if(!pEncontroImp){
          JOptionPane.showMessageDialog(null, "No se encontr� la impresora sticker :"+
                                        nameImpSticker+
                                        "\nVerifique que tenga instalada la impresora.", 
                                        "Mensaje del Sistema", 
                                        JOptionPane.WARNING_MESSAGE);
        }*/
    }
    public static String getMensajeFideicomizo(){
        String pCadena = "";
        try {
            pCadena = DBCaja.getMensajeFideicomizo();
        } catch (SQLException e) {
            log.error("",e);
            pCadena = "";
        }
        return pCadena.trim();
    }
    public static String base(int numero, boolean fin) {

        String cadena = "";
        int unidad = 0;
        int decena = 0;
        int centena = 0;

        if (numero < 1000) {

            if (numero > 99) {
                centena = (new Double(numero / 100)).intValue();
                numero = numero - centena * 100;
                if (centena == 1 && numero == 0)
                    cadena += "CIEN ";
                else
                    cadena += centenas(centena) + " ";
            }

            if (numero > 29) {
                decena = (new Double(numero / 10)).intValue();
                numero = numero - decena * 10;
                if (numero > 0)
                    cadena += 
                            decenas(decena) + " Y " + unidad(numero, false) + " ";
                else
                    cadena += decenas(decena) + " ";
            } else {
                cadena += unidad(numero, fin);
            }
        }

        return cadena.trim();

    }    
    public static String centenas(int numero) {

        String[] aCentenas = 
        { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", 
          "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS" };

        return (numero == 0 ? "" : aCentenas[numero - 1]);

    }    
    public static String decenas(int numero) {

        String[] aDecenas = 
        { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", 
          "SETENTA", "OCHENTA", "NOVENTA" };

        return (numero == 0 ? "" : aDecenas[numero - 1]);

    }
    public static String unidad(int numero, boolean fin) {
        String[] aUnidades = 
        { "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", 
          "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", 
          "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", 
          "VEINTIUNO", "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", 
          "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO", 
          "VEINTINUEVE" };
        String cadena = "";

        if (numero > 0) {
            if (numero == 1 && fin)
                cadena = "UNO";
            else
                cadena = aUnidades[numero - 1];
        }

        return cadena.trim();
    }
      public static boolean isPedidoConvenioMFBTL(String pNumPedVta){
        String pCadena = "";
        try {
            pCadena = DBCaja.getIndPedConvMFBTL(pNumPedVta);
        } catch (SQLException e) {
            log.error("",e);
            pCadena = "N";
        }
        if(pCadena.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    /**
     * Imprime constrasenia de Recetario
     * @author ERIOS
     * @since 18.06.2013
     * @param string
     */
    private static void imprimeContraseniaRecetario(String string) throws SQLException {
        HashMap<String,String> hRecetario = new HashMap<String,String>(); 
        
        DBRecetario.getNumeroRecetario(string,hRecetario);
        
        String numRecetario = hRecetario.get("NUM_RECETARIO");
        String estRecetario = hRecetario.get("EST_RECETARIO");
        if(!numRecetario.equals("") && estRecetario.equals("E"))
        {   FacadeRecetario facadeRecetario = new FacadeRecetario();
            facadeRecetario.imprimirContrasenia(numRecetario);
        }
    }
    
    public static void abrirGabeta(Frame pFrame, boolean pLogin){
        try {
            if(pLogin && !cargaLoginAdministrador(pFrame,null)){
                return;
            }
            String secImprLocal = "";
            secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);            
             
            VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());    
            VariablesCaja.vModeloImpresora = DBImpresoras.getModeloImpresora(secImprLocal);
            log.debug("Abrir gabeta");
            log.debug("Impresora: "+VariablesCaja.vRutaImpresora);
            log.debug("Modelo   : "+VariablesCaja.vModeloImpresora );
            ImpresoraTicket ticketera = new ImpresoraTicket();  
            ticketera.abrirGabeta(VariablesCaja.vModeloImpresora,VariablesCaja.vRutaImpresora);
        } catch (SQLException e) {
            log.error("",e);
        }
    }
    
    /**
     * Abre gabeta en los casos de la forma de pago efectivo
     * @author GFONSECA
     * @since 27.12.2013
     * @param pFrame
     * @param pLogin
     * @param strNumPedido
     */
    public static void abrirGabeta(Frame pFrame, boolean pLogin, String strNumPedido){
        ArrayList listaFormaPago = null;
        String strFpago= "";
        boolean fpagoEfectivo = false;
        try {
            //obtener formas de pago del pedido            
            FacadeCaja facadeCaja = new FacadeCaja();
            listaFormaPago = facadeCaja.getFormasPagoPedido(strNumPedido);
            
            for(int i=0; i<listaFormaPago.size(); i++){
                strFpago = FarmaUtility.getValueFieldArrayList(listaFormaPago, i, 0);
                if(strFpago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)
                            || strFpago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES) )
                    fpagoEfectivo = true;
            }
            
            if(fpagoEfectivo){
                log.info("Se abre la gabeta!");
                abrirGabeta(pFrame,pLogin);                
            }else{
                log.info("No se abre la gabeta!");
            }                         
        } catch (Exception e) {
            log.error("",e);
        }
    }
    
    private static boolean cargaLoginAdministrador(Frame pFrame,Object pDialog)
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
        try{
          DlgLogin dlgLogin = new DlgLogin(pFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
          dlgLogin.setVisible(true);
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
        } catch (Exception e)
        {
          FarmaVariables.vNuSecUsu  = numsec ;
          FarmaVariables.vIdUsu  = idusu ;
          FarmaVariables.vNomUsu  = nomusu ;
          FarmaVariables.vPatUsu  = apepatusu ;
          FarmaVariables.vMatUsu  = apematusu ;
          FarmaVariables.vAceptar = false;
          log.error("",e);
          FarmaUtility.showMessage((JDialog)pDialog,"Ocurrio un error al validar rol de usuario \n : " + e.getMessage(),null);
        }
        
      return FarmaVariables.vAceptar;
    }
    
     /**
     * Realiza la impresi�n masiva de tickets de precios de productos.
     * @AUTHOR Christian Vilca Quiros
     * @SINCE 20.09.2013
     */
      public static boolean impresionEnLote(JDialog pDialogo,JTable jTable)
      {
          boolean impresionOk = true;
          ArrayList listaTemp = new ArrayList();
          ArrayList listaFinal = new ArrayList();
          FarmaTableModel model = (FarmaTableModel)jTable.getModel();
          StringBuilder codigos = new StringBuilder();

          try
          {
              log.debug("generando codigos de barra.....");
              for (int i = 0; i < model.data.size(); i++)
              {
                  String codProd = (String)jTable.getValueAt(i, 1);
                  codigos.append(codProd);
                  codigos.append(",");
                  
                  //LLEIVA 01-Abr-2014 Si es multiplo de 20, realizar la consulta
                  if(i % 20 == 0)
                  {   //Env�o la impresion
                      String vCodigos = codigos.toString();
                      listaTemp = new ArrayList();
                      DBCaja.imprimirStickerPorLote(listaTemp,vCodigos.substring(0, vCodigos.length()-1));
                      listaFinal.addAll(listaTemp);
                      codigos = new StringBuilder();
                  }
              }
              //LLEIVA 01-Abr-2014 Si todavia quedan registros, a�adirlos a la lista final
              if(codigos.toString().length()>0)
              {   //Env�o la impresion
                  String vCodigos = codigos.toString();
                  listaTemp = new ArrayList();
                  DBCaja.imprimirStickerPorLote(listaTemp,vCodigos.substring(0, vCodigos.length()-1));
                  listaFinal.addAll(listaTemp);
              }
              
              String html = "";
              //Agregamos el salto de l�nea para separa los registros con los c�digos de los tickets.
              for(int i = 0; i< listaFinal.size();i++)
              {
                  String record = listaFinal.get(i).toString();
                  html = html + "\n" + record;
              }
              
              boolean flag=nombreImpSticker();
              if (flag==true)
                  PrintConsejo.imprimirStickerEnLote(html.replace("]","").replace("[","").replace("?", "\n"),
                                                  VariablesPtoVenta.vImpresoraSticker);
          }
          catch(Exception e)
          {
              log.error("",e);
              JOptionPane.showMessageDialog(null, 
                                            "Ocurri� el siguiente error al imprimir los tickets :\n"+                                            
                                                    e.getMessage(), "Mensaje del Sistema", JOptionPane.WARNING_MESSAGE);   
          }
          return impresionOk;
      }
     
    private static void imprimeFacturaFasa(JDialog   pJDialog,
                                       String    pFechaBD,
                                       ArrayList pDetalleComprobante,
                                       String    pValTotalBruto,
                                       String    pValTotalNeto,
                                       String    pValTotalAfecto,
                                       String    pValTotalDcto,
                                       String    pValTotalIgv,
                                       String    pPorcIgv,
                                       String    pValRedondeo,
                                       String    pNumComprobante,
                                       String    pNomImpreso,
                                       String    pNumDocImpreso,
                                       String    pDirImpreso,
                                       String    pValTotalAhorro,
                                       String    pRuta,
                                       boolean   bol) throws Exception {
       
      log.debug("IMPRIMIR FACTURA No : " + pNumComprobante);
      String indProdVirtual = "";
      int nroArticulos = 0;
     
      //jcortez 06.07.09 Se verifica ruta 
     // if(bol) VariablesCaja.vRutaImpresora=pRuta;
      VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
      //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt",false);
      FarmaPrintService vPrint = new FarmaPrintService(66,VariablesCaja.vRutaImpresora,false);
      
        //JCORTEZ 16.07.09 Se genera archivo linea por linea
        FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
        vPrintArchivo.startPrintService();
        

      log.debug("Ruta : " + VariablesCaja.vRutaImpresora + "factura" + pNumComprobante + ".txt");
        if ( !vPrint.startPrintService() ) {
                       VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                       log.info("**** Fecha :"+ pFechaBD);
                       log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                       log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                       log.info("**** IP :" + FarmaVariables.vIpPc);
                       log.info("ERROR DE IMPRESORA : No se pudo imprimir la factura");
            }
        else{
      
            try{
                  String dia = pFechaBD.substring(0,2);
                  String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3,5)));
                  String ano = pFechaBD.substring(6,10);
                  String hora = pFechaBD.substring(11,19);
                  vPrint.activateCondensed();
                            
                  
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    //LOCAL
                    ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);
                    
                    vPrint.printLine(" " ,true);
                    vPrintArchivo.printLine(" ",true); 
                    vPrint.printLine(" " ,true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" " ,true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(50)+ lstDirecMatriz.get(0).toString()  +FarmaPRNUtility.llenarBlancos(10)+ "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
                    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(50)+ lstDirecMatriz.get(0).toString() + FarmaPRNUtility.llenarBlancos(10)+ "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
                   
                //SENIOR(ES)-SI LA LONGITUD DE NOMBRE IMPRESO ES MAYOR A  40 SE CORTA EN EL ULTIMO ESPACIO EN BLANCO Y LA PALABRA SOBRANTE
                //SE IMPRIME EN EL SIGUIENTE REGLON            
                    if(pNomImpreso.length()>40){
                        int posBlanc=pNomImpreso.lastIndexOf(" ",pNomImpreso.length());//SE OBTIENE LA POSCION EN BLANCO
                        String lastNomImpreso=pNomImpreso.substring(posBlanc, pNomImpreso.length());//SE OBTIENE LA ULTIMA PALABRA
                        pNomImpreso=pNomImpreso.substring(0,posBlanc);
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),40) + lstDirecMatriz.get(1).toString(),true);
                        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),40)+ lstDirecMatriz.get(1).toString(),true);
                      
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(lastNomImpreso.trim(),40) + lstDirecMatriz.get(2).toString(),true);
                        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(lastNomImpreso.trim(),40)+ lstDirecMatriz.get(2).toString(),true);
                            
                    }else{
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),40) + lstDirecMatriz.get(1).toString(),true);
                        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),40)+ lstDirecMatriz.get(1).toString(),true);
                        
                        vPrint.printLine(FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(2).toString(),true);
                        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(2).toString(),true);
                    }         
                
                    //DIRECCION
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60),true);
                    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60),true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    //RUC y FECHA
                    vPrint.printLine(FarmaPRNUtility.llenarBlancos(10) + pNumDocImpreso.trim() + FarmaPRNUtility.llenarBlancos(73) + dia + " de " + mesLetra + " del " + ano + "     " + hora,true);
                    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(10) + pNumDocImpreso.trim() + FarmaPRNUtility.llenarBlancos(73) + dia + " de " + mesLetra + " del " + ano + "     " + hora ,true);
                    // ESPACIOS ENTRE LA CABECERA Y EL DETALLE DE LA FACTURA
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    vPrint.printLine(" ",true);
                    vPrintArchivo.printLine(" ",true);
                    int linea = 0;
                    double pMontoOld = 0,pMontoNew = 0,pMontoDescuento = 0;
                    log.info("" + VariablesModuloVentas.vTipoPedido);          
                            
                    for (int i=0; i<pDetalleComprobante.size(); i++) {
                      nroArticulos++; //= nroArticulos + Integer.parseInt(((ArrayList)pDetalleComprobante.get(i)).get(0).toString());
                      vPrint.printLine(" " +
                                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + FarmaPRNUtility.llenarBlancos(5) +
                                       FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),60) + FarmaPRNUtility.llenarBlancos(3) +
                                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + FarmaPRNUtility.llenarBlancos(5) +
                                       //UNIDAD DE MEDIDA
                                       //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                                       // LABORATORIO
                                       //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(14) +
                                       FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                                         ,true
                                      ); 
                                      
                      vPrintArchivo.printLine(" " +
                                      FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + FarmaPRNUtility.llenarBlancos(5) +
                                      FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),60) + FarmaPRNUtility.llenarBlancos(3) +
                                      FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + FarmaPRNUtility.llenarBlancos(5) +
                                      //UNIDAD DE MEDIDA
                                      //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                                      // LABORATORIO
                                      //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                                      FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(14) +
                                      FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                                        ,true
                                      ); 
                    
                      
                    linea += 1;
                    indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
                    //verifica que solo se imprima un producto virtual en el comprobante
                    if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                      VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                    else
                      VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                    }
            
                  if(VariablesCaja.vIndPedidoConProdVirtualImpresion){
                    vPrint.printLine("", true);
                    vPrintArchivo.printLine("", true);
                    impresionInfoVirtual(vPrint,vPrintArchivo,
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                         VariablesCaja.vNumPedVta,//Se a�adio el parametro
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
                    linea = linea + 4;
                  }
            
                  if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                      linea++;
                  }
            
                  //MODIFICADO POR DVELIZ 13.10.08
                  //
                   if(!VariablesModuloVentas.vEsPedidoConvenio){
                       if(pDetalleComprobante.size() < 10){
                           for (int j=linea; j<10; j++) { 
                               vPrint.printLine(" ",true);
                               vPrintArchivo.printLine(" ",true);
                           }
                       }  
                   }else{
                       for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  vPrint.printLine(" ",true);
                   }
                  //*************************************INFORMACION DEL CONVENIO*************************************************//
                  //*******************************************INICIO************************************************************//
            
                  if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                  {
                    try{
                      log.debug("****Imprimiendo... "+VariablesCaja.vNumPedVta);
                      ArrayList aInfoPedConv = new ArrayList();
                      DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));
            
                      for(int i=0; i<aInfoPedConv.size(); i++){
                        ArrayList registro = (ArrayList) aInfoPedConv.get(i);
                        //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
                        String Ind_Comp=((String)registro.get(8)).trim();
                        if(Ind_Comp.equalsIgnoreCase("N")){
                            log.debug("registro "+registro);
                            vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                                         //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                                         FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                                         ,true);
                                         
                            vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" Titular Cliente: "+((String)registro.get(4)).trim(),60)+" "+
                                            //FarmaPRNUtility.alinearIzquierda("Dscto: "+((String)registro.get(2)).trim()+" %",24)+" "+
                                            FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",25)
                                            ,true);                           
                            /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
                            String vCredDisp = ((String)registro.get(7)).trim();
                            if(vCredDisp.equals("")){
                                vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                                               ,true);
                                vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                                 FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                                                 FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)
                                                 ,true);
                            }else{
                                vPrint.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                               FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                                               FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                                               FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                                               ,true);
                                vPrintArchivo.printLine(//FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                                 FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+((String)registro.get(5)).trim(),60)+" "+
                                                 FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. "+((String)registro.get(6)).trim(),25)+" "+
                                                 FarmaPRNUtility.alinearIzquierda("Cred Disp: S/."+vCredDisp,25)
                                                 ,true);
                            }
                        }
                      }

        }catch(SQLException sql){
          //log.error("",sql);
          log.debug("Error de BD "+ sql.getMessage());
            VariablesCaja.vEstadoSinComprobanteImpreso="S";      
            log.info("**** Fecha :"+ pFechaBD);
            log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al obtener Informacion Pedido Convenio: ");
            log.info("Error al imprimir la factura : " + sql.getMessage());
            log.error(null,sql);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
              enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
        }catch(Exception e){
            VariablesCaja.vEstadoSinComprobanteImpreso="S";      
            log.info("**** Fecha :"+ pFechaBD);
            log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al obtener Informacion Pedido Convenio : ");
            log.info("Error al imprimir la factura: "+e);
            
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
              enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
        }
      }else{
          //dveliz 13.10.08
        //vPrint.printLine(" ",true);
        //vPrint.printLine(" ",true);
        //vPrint.printLine(" ",true);
      }
      //*********************************************FIN*************************************************************//
      //*************************************INFORMACION DEL CONVENIO***********************************************//
      
       //MODIFICADO POR DVELIZ 02.10.08
      //vPrint.printLine(" "+VariablesFidelizacion.vNomClienteImpr, true);
        
        
      //ERIOS 25.07.2008 imprime el monto ahorrado.
      double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
      if(auxTotalDcto > 0){
          log.info("Imprimiendo Ahorro");
          //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
          String obtenerMensaje="";
          String indFidelizado="";
          log.info("Identificando cliente fidelizado");
          if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
              indFidelizado="S";
          }else 
           { indFidelizado="N"; }
          log.info("Fidelizado--> "+indFidelizado);    
          obtenerMensaje=obtenerMensaAhorro(pJDialog,indFidelizado);
           vPrint.printLine(""+obtenerMensaje+" "+" S/. "+pValTotalAhorro,true);
             vPrintArchivo.printLine(""+obtenerMensaje+" S/. "+pValTotalAhorro,true);

    
      }else
      {
        vPrint.printLine(" ",true);
          vPrintArchivo.printLine(" ",true);
      }

      if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
          vPrint.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
          vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",60),true);
      }
      if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
      {
                    VariablesModuloVentas.vTituloDelivery = "" ;
      }else
                    VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;

      vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
      vPrint.printLine(" ",true);
      vPrintArchivo.printLine(" ",true);
                    
      //ERIOS 12.09.2013 Imprime direccion local
      String vLinea = "",vLineaDirecLocal1="",vLineaDirecLocal2="",vLineaDirecLocal3="";      
      if(VariablesPtoVenta.vIndDirLocal){     
          ArrayList lstDirecLocal = FarmaUtility.splitString("NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal, 46);
          vLineaDirecLocal1 = lstDirecLocal.get(0).toString();
          vLineaDirecLocal2 = ((lstDirecLocal.size()>1)?lstDirecLocal.get(1).toString():"");
          vLineaDirecLocal3 = ((lstDirecLocal.size()>2)?lstDirecLocal.get(2).toString():"");
      }           
                
      vLinea = " SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),67);
      vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal1;
      vPrint.printLine(vLinea,true);
      vPrintArchivo.printLine(vLinea,true);
      
      vLinea = " REDO:" + pValRedondeo +
                       " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                       " CAJA:" + VariablesCaja.vNumCajaImpreso +
                       " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                       " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso;
      vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal2;
      vPrint.printLine(vLinea,true);
      vPrintArchivo.printLine(vLinea,true);
      
      vLinea = " Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery;
      vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85)+vLineaDirecLocal3;
      vPrint.printLine(vLinea,true);
      vPrintArchivo.printLine(vLinea,true);
    
        if(!VariablesCaja.vImprimeFideicomizo){
            vPrint.printLine(" ",true);
            vPrintArchivo.printLine(" ",true);
            vPrint.printLine(" ",true);
            vPrintArchivo.printLine(" ",true);
        }
    
        if(VariablesCaja.vImprimeFideicomizo){
            String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
            log.info("********************"+  VariablesCaja.vCadenaFideicomizo+"]");
            if(lineas.length>0){
                for(int i=0;i<lineas.length;i++){
                    if(lineas[i].trim().length() > 0){
                        log.info("******** imprimiendo [" + i + "] : "+ lineas[i].trim());
                        vPrint.printLine(""+lineas[i].trim(),true);
                        vPrintArchivo.printLine(""+lineas[i].trim(),true);        
                    }
                }
            }
        }      

        vPrint.printLine(FarmaPRNUtility.llenarBlancos(95) +FarmaPRNUtility.alinearDerecha(pPorcIgv,6)+ "%",true);                
        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(95) +FarmaPRNUtility.alinearDerecha(pPorcIgv,6)+ "%",true);
                vPrint.printLine(" ",true);
                vPrintArchivo.printLine(" ",true);
        vPrint.printLine("     " +
                        FarmaPRNUtility.alinearDerecha(nroArticulos,10) + FarmaPRNUtility.llenarBlancos(65) +
                         //FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                         "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(25) +
                         "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
         vPrintArchivo.printLine("     " +
                        FarmaPRNUtility.alinearDerecha(nroArticulos,10) + FarmaPRNUtility.llenarBlancos(65) +
                         //FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                         "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalIgv,10) + FarmaPRNUtility.llenarBlancos(25) +
                         "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
                
        //l�neas necesarias para que al imprimir la 2da factura hacia adelante, se imprima en la posici�n correcta.
        vPrint.printLine(" ",true);//Linea 39
        vPrintArchivo.printLine(" ",true);
        vPrint.printLine(" ",true);//Linea 40
        vPrintArchivo.printLine(" ",true);
                
        vPrint.endPrintServiceSinCompletarDelivery();
        vPrintArchivo.endPrintService();
       
        //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
        DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
        log.debug("Guardando fecha impresion cobro..."+pNumComprobante); 
        log.info("Fin al imprimir la factura: " + pNumComprobante);
                VariablesCaja.vEstadoSinComprobanteImpreso="N";      
            }
           catch(Exception e){
                    log.error("",e);
                    VariablesCaja.vEstadoSinComprobanteImpreso="S";      
                    log.info("**** Fecha :"+ pFechaBD);
                    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                    log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                    log.info("**** IP :" + FarmaVariables.vIpPc);
                    log.info("Error al imprimir Factura: " + e);
                    
                    //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
                      enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
                   
            }
       }
    }  
    /**
     * 
     * @author ERIOS
     * @since 10.10.2013
     * @param pJDialog
     * @param pNumPedVta
     * @param estadoAEvaluar
     * @param pObjectFocus
     * @return
     */
    public static boolean verificaEstadoPedidoNuevoCobro(JDialog pJDialog, String pNumPedVta, String estadoAEvaluar, Object pObjectFocus)
    {
      String estadoPed = "";
      estadoPed = obtieneEstadoPedido(pJDialog, pNumPedVta);
      log.debug("Estado de Pedido:" + estadoPed);
      if(estadoAEvaluar.equalsIgnoreCase(estadoPed)) return true;
      //dubilluz 13.10.2011 bloqueo NO SE DEBE LIBERAR DEBIDO A Q YA EXISTE UN BLOQUEO DE STOCK DE PRODUCTOS.
      //FarmaUtility.liberarTransaccion();
      if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_COBRADO))
      {
        FarmaUtility.showMessage(pJDialog, "El pedido No se encuentra pendiente de cobro.Verifique!!!", pObjectFocus);
        return false;
      }
      if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_ANULADO))
      {
        FarmaUtility.showMessage(pJDialog, "El pedido se encuentra Anulado. Verifique!!!", pObjectFocus);
        return false;
      }
      if(estadoPed.equalsIgnoreCase(ConstantsCaja.ESTADO_PENDIENTE))
      {
        FarmaUtility.showMessage(pJDialog, "El pedido se encuentra pendiente de cobro. Verifique!!!", pObjectFocus);
        return false;
      }
      if(estadoPed.equalsIgnoreCase(""))
      {
        FarmaUtility.showMessage(pJDialog, "No se pudo determinar el estado del pedido. Verifique!!!", pObjectFocus);
        return false;
      }
      return true;
    }
    
    /**
     * Se obtiene impresora disponible 
     * @AUTHOR JCORTEZ
     * @SINCE 30.06.09
     * */
    public static void obtieneImpresTicket(JDialog pDialog, Object pObject)
    {
        //JCORTEZ 09.06.09  Se valida relacion maquina - impresora
        if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET))
        {
            try
            {   //String result2 = "";
                //ArrayList res = new ArrayList();
                String DescImpr="";
                String result= DBCaja.getObtieneSecImpPorIP( FarmaVariables.vIpPc);
                //String temp = DBCaja.getObtieneTipoCompPorIP(FarmaVariables.vIpPc,VariablesCaja.vTipComp);
                
                /*if(res!=null && res.size()>0)
                {   
                    if(res.get(0) != null)
                        result2 = res.get(0).toString();
                }*/

                //verifica que el secuencial exista, caso contrario, se validara la asignada y se mostrara 
                //que no tiene niguna asignacion si es el caso. 16.06.09
                String exist="";
                exist=DBCaja.getExistSecImp(VariablesCaja.vSecImprLocalTicket,FarmaVariables.vIpPc);
            
                //if(exist.equalsIgnoreCase("S")){    --existe en local para usar.
                
                if(exist.trim().equalsIgnoreCase("2")){   
                    /*if(VariablesCaja.vTipComp.trim().equalsIgnoreCase(result2.trim()))
                     VariablesCaja.vSecImprLocalTicket=result;
                    else{
                     FarmaUtility.showMessage(this,"El tipo de comprobante es distinto al de la impresora asignada. Verifique!!!",btnListaUsuarioCaja);
                     return;
                    }*/
                     VariablesCaja.vSecImprLocalTicket=result;
                 }else if (exist.equalsIgnoreCase("X")){
                     FarmaUtility.showMessage(pDialog,"No existen impresoras disponibles. No se imprimira comprobante.!!!",pObject);
                     VariablesCaja.vSecImprLocalTicket=exist.trim();
                     //return;
                 }else if(exist.equalsIgnoreCase("1")){
        
                 }else {
                     VariablesCaja.vSecImprLocalTicket=exist.trim();
                 }
                 
               
                
                if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
                 DescImpr=DBCaja.getNombreImpresora(VariablesCaja.vSecImprLocalTicket);
                 VariablesCaja.vDescripImpr=DescImpr;
                }else{
                    VariablesCaja.vDescripImpr="X";
                }
                
                 
             
            }catch(Exception e){
                //log.error("",sql);
                             FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                                           FarmaVariables.vCodLocal,
                                                           //"DUBILLUZ",
                                                           VariablesPtoVenta.vDestEmailErrorAnulacion,
                                                           "Error Anular Get Ruta Ip",
                                                           "Error Anular Pedido",
                                                           "Se obtuvo un errror al obtener la ruta de Ticketera Metodo obtieneImpresTicket() :<br>"+
                                                           "IP : " +FarmaVariables.vIpPc+"<br>"+
                                                           "Error: " + e,
                                                           //"joliva;operador;daubilluz@gmail.com"
                                                           "");
                    //              
              
            }
             
           }
    
    }
    
    public static ArrayList obtieneComprobantesPago(String pNumPedVta) {
        return obtieneComprobantesPago(pNumPedVta, "");
    }
    
    public static ArrayList obtieneComprobantesPago(String pNumPedVta, String pSecCompPago) {
        ArrayList lstComprobantes = new ArrayList();
        try {
            DBCaja.obtieneComprobantePagos(lstComprobantes, 
                                           FarmaVariables.vCodGrupoCia, 
                                           FarmaVariables.vCodLocal, 
                                           pNumPedVta,
                                           pSecCompPago);

        } catch (Exception ex) {
            log.error("", ex);
            lstComprobantes = new ArrayList();
        }
        return lstComprobantes;
    }
    
    public static void getImpresionTicketAnulado(String pNumeroPedido,String pSecuencia,String pCajero,String pTurno)throws Exception  
    {           
           /*ERIOS 2.3.2 Se valida por BBDD
            if( tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET) ||
               tipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT))
           {*/
          boolean vResultado = false,bRes1=false,bRes2=false;
          
          
          if(UtilityFactElectronica.isActivoFactElectronica()){
              String pNumPedVta = pNumeroPedido;//pListaPedidosAnula[i].toString();
              UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();
              ArrayList lstComprobantes = UtilityCaja.obtieneComprobantesPago(pNumPedVta);
              for (int j = 0; j < lstComprobantes.size(); j++) {
                  String tipoCP = ((String)((ArrayList)lstComprobantes.get(j)).get(2)).trim();
                  String secCompPago = ((String)((ArrayList)lstComprobantes.get(j)).get(1)).trim();
                  String nroComprobante = ((String)((ArrayList)lstComprobantes.get(j)).get(4)).trim();
                  
                  bRes1 = true;
                  
                  if(bRes1){
                      
                      String pCadena = DBCaja.getNumPedido_NC(pNumeroPedido);
                      
                      vResultado = impresionElectronico.printDocumento(
                                                                //pNotaCredito, 
                                                                //secCompPago, 
                                                                pCadena.split("@")[0],
                                                                pCadena.split("@")[1],
                                                                false, true);
                      /*if (vResultado) { //LTAVARA 17.10.2014
                          //UtilityCaja.actualizarFechaImpresion(pNumPedVta, secCompPago, tipoCP);
                          UtilityCaja.actualizaEstadoPedido(pNumPedVta, ConstantsCaja.ESTADO_COBRADO);
                      }*/
                  }
              }
          }
          else{
              //// inicio
              //para agregar Fecha Creacion
              Date vFecImpr = new Date();
              String fechaImpresion;
              String ruta="";      
              String DATE_FORMAT = "yyyyMMdd";
                 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                  // log.debug("Today is " + sdf.format(vFecImpr));
                 fechaImpresion =  sdf.format(vFecImpr);                
                          
              //----
              
              ruta=UtilityPtoVenta.obtieneDirectorioComprobantes();
              //ruta=ruta+"T_"+VariablesCaja.vNumPedVta_Anul+"_Anul";
              
              //JMIRANDA 08/07/09
              ruta=ruta+fechaImpresion+"_"+"T_"+pNumeroPedido+"_Anul";
              
              
                 VariablesCaja.vSecuenciaUsoUsuario= pSecuencia;
                 //Solo se emitira comprobante de anulacion si existe secuencial.
                 if(!VariablesCaja.vSecImprLocalTicket.equalsIgnoreCase("X")){
                     
                     
                     //para montos afectos
                     bRes1= UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, 
                                                         pTurno, 
                                                         pNumeroPedido, 
                                                         "00", ruta + "_1.TXT", 
                                                         false, "N");
                     //para montos inafectos
                     bRes2= UtilityCaja.imprimeMensajeTicketAnulacion(pCajero, 
                                                         pTurno, 
                                                         pNumeroPedido, 
                                                         "01", ruta + "_2.TXT", 
                                                         false, "N");
                     
               //JCORTEZ 06.07.09 Se genera archivo de anulacion
               /*
                * Este proceso no es lo correcto.
                * No se pidio que se haga de esta forma
                * SIno que en paralelo linea x linea se imprima y se guarde en el archivo.
                * dubilluz 14.07.2009
               UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0, 
                                                                                    2).toString(), 
                                                         tblUsuariosCaja.getValueAt(0, 
                                                                                    3).toString(), 
                                                         VariablesCaja.vNumPedVta_Anul, 
                                                         "00", 
                                                         ruta + "_1.TXT", 
                                                         true, "N");
               UtilityCaja.imprimeMensajeTicketAnulacion(tblUsuariosCaja.getValueAt(0, 
                                                                                    2).toString(), 
                                                         tblUsuariosCaja.getValueAt(0, 
                                                                                    3).toString(), 
                                                         VariablesCaja.vNumPedVta_Anul, 
                                                         "01", 
                                                         ruta + "_2.TXT", 
                                                         true, "N");
                 */
                 }    
              //// fin
          }
           
           
           if(bRes1||bRes2)
               vResultado = true;
           else
               vResultado = false;
           //return vResultado;
       }

    public static boolean existeStockPedido(JDialog pJDialog, String pNumPedido) 
    {        
        boolean pRes = false;
        String pCadena = "N";
        try
        {   pCadena = DBCaja.getPermiteCobrarPedido(pNumPedido);
        }
        catch (SQLException e)
        {   pCadena = "N";
            log.error("",e);
        }
        
        if(pCadena != null &&
           "S".equalsIgnoreCase(pCadena.trim()))
        {
            pRes = true;
        }
        else
        {   FarmaUtility.showMessage(pJDialog, 
                                     "No puede cobrar el pedido\n" +
                                    "Porque no hay stock suficiente para poder generarlo �\n" +
                                    "Existe un Problema en la fracci�n de productos.", 
                                     null);
        }
        return pRes;
    }

    /**
     * Procesa pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void procesarPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto, JTable tblDetallePago, JTextField txtMontoPagado) {
        log.debug("##################" +
            "   INICIO DEL COBRO DEL PEDIDO " +
            "##################" + VariablesModuloVentas.vNum_Ped_Vta);
            log.debug("1.1   valida Tipo de NUEVO COBRO  ");
		if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
            log.debug("1.1.1.Convenio---Ingresar Nuevo Cobro Convenio");
			DlgProcesarNuevoCobroBTLMF dlgProcesarCobro = 
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setVisible(true);  
			log.debug("Finaliza Cobro Convenio");
        }else{
            log.debug("1.1.2.Generar---Ingresa Nuevo Cobro General");
			DlgProcesarNuevoCobro dlgProcesarCobro = 
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);

            //dlgProcesarCobro.setVisible(true);     
            dlgProcesarCobro.mostrar();  
			log.debug("Finaliza Cobro General");
        }        
    }
    
    /**
     * Anula pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void anularPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto, JTable tblDetallePago, JTextField txtMontoPagado) {
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
            DlgProcesarNuevoCobroBTLMF dlgProcesarCobro = 
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndAnularPedido(true);
            dlgProcesarCobro.setVisible(true);    
        }else{
            DlgProcesarNuevoCobro dlgProcesarCobro = 
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndAnularPedido(true);
            dlgProcesarCobro.setVisible(true);          
        }
    }
    
    /**
     * Finaliza pedido Nuevo Cobro
     * @author ERIOS
     * @since 14.10.2013
     * @param myParentFrame
     * @param lblVuelto
     * @param tblDetallePago
     * @param txtMontoPagado
     */
    public static void finalizarPedidoNuevoCobro(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto, JTable tblDetallePago, JTextField txtMontoPagado,ArrayList vListaTarjeta) {
        log.debug("inicia metodo finalizarPedidoNuevoCobro");
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
            log.debug("finaliza proceso cobro convenio");
			DlgProcesarNuevoCobroBTLMF dlgProcesarCobro = 
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndFinalizaCobro(true);
            dlgProcesarCobro.setVisible(true);   
        }else{
            log.debug("finaliza proceso cobro general");
			DlgProcesarNuevoCobro dlgProcesarCobro = 
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado,vListaTarjeta);
            dlgProcesarCobro.setIndFinalizaCobro(true);
            dlgProcesarCobro.mostrar();
            //dlgProcesarCobro.setVisible(true);           
        }
    }
    
    public static void finalizarPedidoCobroBD(Frame myParentFrame, JDialog pJDialog, JLabel lblVuelto, JTable tblDetallePago, JTextField txtMontoPagado) {
        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
            log.debug("finaliza proceso cobro convenio");
                        DlgProcesarNuevoCobroBTLMF dlgProcesarCobro = 
                new DlgProcesarNuevoCobroBTLMF(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndCobroConvBD(true);
            dlgProcesarCobro.setVisible(true);   
        }else{
            log.debug("finaliza proceso cobro general");
                        DlgProcesarNuevoCobro dlgProcesarCobro = 
                new DlgProcesarNuevoCobro(myParentFrame, "", true, null, 
                                     lblVuelto, tblDetallePago, txtMontoPagado);
            dlgProcesarCobro.setIndCobroBD(true);
            dlgProcesarCobro.setVisible(true);           
        }
    }
    
    public static boolean validacionesCobroPedido(boolean validaTotalCubierto, JDialog pJDialog, Object tblFormasPago)
    {
        try
        {   //valida que haya sido seleccionado un pedido.
            if (FarmaConstants.INDICADOR_N.equalsIgnoreCase(VariablesCaja.vIndPedidoSeleccionado))
                return false;
    
            //validando que se haya cubierta el total del monto del pedido
            if (validaTotalCubierto && !VariablesCaja.vIndTotalPedidoCubierto)
            {   FarmaUtility.showMessage(pJDialog, "El Pedido tiene saldo pendiente de cobro.Verifique!!!", tblFormasPago);
                return false;
            }
    
            //validando que el pedido este en esta PENDIENTE DE COBRO
            if (!UtilityCaja.verificaEstadoPedidoNuevoCobro(pJDialog, VariablesCaja.vNumPedVta,ConstantsCaja.ESTADO_PENDIENTE, null))
            {    return false;
            } 
    
            //Validacion para cajeros
            if (!UtilityCaja.existeCajaUsuarioImpresora(pJDialog, null))
            {   //cerrarVentana(false);
                return false;
            }
           
           /*
            * Validacion de Fecha actual del sistema contra
            * la fecha del cajero que cobrara
            */
           if(!UtilityCaja.validaFechaMovimientoCaja(pJDialog, tblFormasPago))
           {    FarmaUtility.liberarTransaccion();
                return false;
           }
           
           //valida que exista RUC si es FACTURA
           if (ConstantsModuloVenta.TIPO_COMP_FACTURA.equalsIgnoreCase(VariablesModuloVentas.vTip_Comp_Ped))
           {    if (VariablesModuloVentas.vRuc_Cli_Ped!=null &&
                    "".equalsIgnoreCase(VariablesModuloVentas.vRuc_Cli_Ped.trim()))
                {   
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pJDialog, "Debe ingresar el numero de RUC!!!", tblFormasPago);
                    return false;
                }
           }           
    
            if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(pJDialog, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
                if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1"))
                {
                        if(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vMontoSaldo))
                        {
                          FarmaUtility.showMessage(pJDialog,"Saldo insuficiente del Benificiario!!!", tblFormasPago);
                          return false;
                        }
                }
            }/*else{
               //   RHERRERA : ESTA OBCCION ESTA DESHABILITADO - SEGUN LO CONVERSADO CON DUBILLUZ
               //  ESTE METODO VALIDA DNI INVALIDO, RUC INVALIDO, MAXIMO AHORRO POR DIA EN MATIZ
               if(!UtilityFidelizacion.validaPedidoFidelizado(VariablesCaja.vNumPedVta,
                                                                 VariablesVentas.vRuc_Cli_Ped,
                                                                 pJDialog,
                                                                 tblFormasPago,
                                                                 VariablesFidelizacion.vNumTarjeta))
                {    return false;
                }
            
                }*/
           /*
            * Bloqueo de caja
            */
           return true;
        }
        catch(Exception e)
        {   log.error("", e);
            return false;
        }
    }   
    
    public static boolean validaCajaAbierta(JDialog pJDialog) {

        boolean result = true;
        String Indicador = "";
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            log.debug("VariablesCaja.vNumCaja ===> " + VariablesCaja.vNumCaja);
            Indicador = DBCaja.obtieneEstadoCaja();
            if (Indicador.trim().equalsIgnoreCase("N")) {
                FarmaUtility.showMessage(pJDialog,
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
    
    /**
     * VERIFICA LA RUTA A IMPRIMIR
     * @author CHUANES
     * @since 14.03.2014
     * @param pDialog
     * @param pObjectFocus
     * @param tipoDocumento
     */
    public static boolean verificaImpresora(JDialog pDialog, Object pObjectFocus,String tipoDocumento){
        boolean existeImpresora=true;
        String secImprLocal="";
        String ruta="";

        try{
            

            if(ConstantsModuloVenta.TIPO_COMP_BOLETA.equalsIgnoreCase(tipoDocumento))
            {
                secImprLocal =DBCaja.getSecImprBoletaGuia(tipoDocumento.trim());
                ruta=DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if(ruta!=null){
                       if(verificaAccesoImpresora(ruta))               
                       existeImpresora=true;
                       else{
                        existeImpresora=false;   
                        FarmaUtility.liberarTransaccion();   
                            FarmaUtility.showMessage(pDialog,"No se puede acceder a la impresora.\n Ruta : "+ruta+"  Verifique !" , pObjectFocus);   
                              
                        }
                }else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog, "No se pudo determinar la  ruta  de la Impresora para Boletas. Verifique !!!", pObjectFocus);
                          
                }
                
            }
            else if(ConstantsModuloVenta.TIPO_COMP_TICKET.equalsIgnoreCase(tipoDocumento)){
                secImprLocal = DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
                    ruta=DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                            if(ruta!=null){
                                   if(verificaAccesoImpresora(ruta))               
                                   existeImpresora=true;
                                   else{
                                    existeImpresora=false;   
                                    FarmaUtility.liberarTransaccion();   
                                        FarmaUtility.showMessage(pDialog,"No se puede acceder a la impresora.\n Ruta : "+ruta+"  Verifique !" , pObjectFocus);   
                                          
                                    }
                            }else {
                                existeImpresora = false;
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(pDialog, "No se pudo determinar la  ruta  de la Impresora para Ticket. Verifique !!!", pObjectFocus);
                                      
                            }
                        }else if(ConstantsModuloVenta.TIPO_COMP_TICKET_FACT.equalsIgnoreCase(tipoDocumento)) {
                   secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                    ruta=DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                    if(ruta!=null){
                           if(verificaAccesoImpresora(ruta))               
                           existeImpresora=true;
                           else{
                                existeImpresora=false;   
                                FarmaUtility.liberarTransaccion();   
                                FarmaUtility.showMessage(pDialog, "No se puede acceder a la impresora.\n Ruta : "+ruta+"  Verifique !", pObjectFocus);   

                            }
                    }else {
                        existeImpresora = false;
                        FarmaUtility.liberarTransaccion(); 
                        FarmaUtility.showMessage(pDialog, "No se pudo determinar la  ruta  de la Impresora para Ticket Factura. Verifique !!!", pObjectFocus);
                              
                    }
                   
          
            }
            else if(ConstantsModuloVenta.TIPO_COMP_GUIA.equalsIgnoreCase(tipoDocumento))
            {
                secImprLocal = DBCaja.getSecImprBoletaGuia(tipoDocumento.trim());
                ruta=DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                if(ruta!=null){
                       if(verificaAccesoImpresora(ruta))               
                       existeImpresora=true;
                       else{
                            existeImpresora=false;   
                            FarmaUtility.liberarTransaccion();   
                            FarmaUtility.showMessage(pDialog,"No se puede acceder a la impresora.\n Ruta : "+ruta+"  Verifique !", pObjectFocus);   

                        }
                }else {
                    existeImpresora = false;
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(pDialog, "No se pudo determinar la ruta de la Impresora para Guias. Verifique !!!", pObjectFocus);
                          
                }
            }
            else if(ConstantsModuloVenta.TIPO_COMP_FACTURA.equalsIgnoreCase(tipoDocumento) ){
                   secImprLocal = DBCaja.getObtieneSecFacImpPorIP(FarmaVariables.vIpPc);
                   ruta=DBCaja.obtieneRutaImpresoraVenta(secImprLocal);
                   if(ruta!=null){
                          if(verificaAccesoImpresora(ruta))               
                          existeImpresora=true;
                          else{
                               existeImpresora=false;   
                               FarmaUtility.liberarTransaccion();   
                               FarmaUtility.showMessage(pDialog, "No se puede acceder a la impresora.\n Ruta : "+ruta+"  Verifique !", pObjectFocus);   

                           }
                   }else {
                       existeImpresora = false;
                       FarmaUtility.liberarTransaccion(); 
                       FarmaUtility.showMessage(pDialog, "No se pudo determinar la  ruta  de la Impresora para  Factura. Verifique !!!", pObjectFocus);
                             
                   }  
            }
            
        }catch(Exception e){
            existeImpresora=false;
            FarmaUtility.liberarTransaccion();
            e.printStackTrace();
        }
        
        return existeImpresora;
    }
    /**
     * VERIFICA EL ACCESO A LA IMPRESORA A IMPRIMIR
     * @author CHUANES
     * @since 14.03.2014
     * @param ruta
     */
    private static  boolean verificaAccesoImpresora(String ruta){
     
       boolean valorRetorno =false;
        PrintStream ps=null;
              try {
                  FileOutputStream fos = new FileOutputStream(ruta);
                  ps = new PrintStream(fos);
                  if(ps!=null){
                  valorRetorno = true;
                  }else{
                  valorRetorno =false;    
                  }
                  ps.close();
              } catch (FileNotFoundException errFileNotFound) {
                  errFileNotFound.printStackTrace(); 
                  valorRetorno = false;
              } catch (IOException errIO) {
                  valorRetorno = false;
                  errIO.printStackTrace();
              } catch (Exception errException) {
                  valorRetorno = false;
                  errException.printStackTrace();
              }
              return valorRetorno;  
    }

    public static void actualizaPedidoCupon(JDialog pJDialog, String codCamp, String vtaNumPed, String estado,
                                     String indtodos) {

        try {
            DBCaja.actualizaIndImpre(codCamp, vtaNumPed, estado, indtodos);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();

            log.error(null, e);
            FarmaUtility.showMessage(pJDialog, "Ocurrio un error al validar la forma de pago del pedido.\n" +
                    e.getMessage(), null);
        }
    }
    
    /**
     * Se valida los montos por productos que esten en una campa�a para llevarse los cupones ganados
     * @author JCORTEZ
     * @since 03/07/08
     * */
    public static boolean validarFormasPagoCupones(JDialog pJDialog, String numPedVta, JTable tblDetallePago, JLabel lblSaldo, JLabel lblVuelto){
        
        boolean valor=true;
        String codSel,codobtenido="";
        String monto1,monto2,descrip="",desccamp="",indAcep="",codCamp="",numPed="";
        ArrayList array=new ArrayList();
        int maxform=0,cant=0;
        
        if(tblDetallePago.getRowCount()>0 && Double.parseDouble(lblSaldo.getText().trim())==0){
        
          obtieneFormaPagoCampa�a(pJDialog,array,VariablesCaja.vNumPedVta);
          log.debug("array::: "+array);
          if(array.size()>0){   
            for (int j = 0; j < array.size(); j++){
              numPed=((String) ((ArrayList) array.get(j)).get(0)).trim();
              codobtenido=((String) ((ArrayList) array.get(j)).get(1)).trim();
              monto1=((String) ((ArrayList) array.get(j)).get(2)).trim();
              desccamp=((String) ((ArrayList) array.get(j)).get(3)).trim();
              indAcep=((String) ((ArrayList) array.get(j)).get(4)).trim();
              codCamp=((String) ((ArrayList) array.get(j)).get(5)).trim();
              
              for (int i = 0; i < tblDetallePago.getRowCount(); i++){
                maxform=tblDetallePago.getRowCount();//ultima forma de pago restar vuelto
                codSel=((String) tblDetallePago.getValueAt(i,0)).trim();
                descrip=((String) tblDetallePago.getValueAt(i,1)).trim();
                
                if(tblDetallePago.getRowCount()>0){
                
                  if(codSel.trim().equalsIgnoreCase(codobtenido.trim())){
                    monto2=((String) tblDetallePago.getValueAt(i,5)).trim();
                    
                    log.debug("monto pagado :"+Double.parseDouble(monto2));
                    log.debug("monto exacto :"+Double.parseDouble(monto1));
                    
                       if(maxform==i+1){
                         log.debug("leyendo ultima forma de pago");
                         monto2=FarmaUtility.formatNumber(Double.parseDouble(monto2)-Double.parseDouble(lblVuelto.getText().trim()));
                         log.debug("supuesto pago sin vuelto: "+monto2);
                       }
                     
                      cant=cant+1;
                      if(cant==1)
                      actualizaPedidoCupon(pJDialog,codCamp,numPed,"S","N");
                      else if(cant>1)
                      actualizaPedidoCupon(pJDialog,codCamp,numPed,"N","N");
                      
                  }else{
                   log.debug("forma pago diferente");
                   valor=true;
                  }
                }
              }
            }
          }
          procesaCampSinFormaPago(pJDialog,VariablesCaja.vNumPedVta);
        }else{
         valor=true;
        }
    return valor;
    }
    
    /**
    * Se valida la impresion de las campa�as que no tengan forma de de pago relacionadas
    * @author JCORTEZ
    * @since  10.07.08
    * */
    private static void procesaCampSinFormaPago(JDialog pJDialog, String vtaNumPed){
      try
         {
          DBCaja.procesaCampSinFormaPago(vtaNumPed);
          //se comento para evitar problemas de bloqueos anteriores.
          //dubilluz 13.10.2011
          //FarmaUtility.aceptarTransaccion();
         }catch(SQLException e)
         {
           //log.error("",e);
        log.error(null,e);
           FarmaUtility.showMessage(pJDialog,"Ocurrio un error al procesar campa�as sin forma de pago.\n" + e.getMessage(),null);
         }
    }      
    
    /**
    * Se obtiene la informacion de campa�a por pedido
    * @author JCORTEZ
    * @since 03/07/08
    * */
    private static void obtieneFormaPagoCampa�a(JDialog pJDialog, ArrayList array,String vtaNumPed){
    
     String result="";
     array.clear();
        try
          {
           DBCaja.getFormaPagoCampa�a(array,vtaNumPed);
          }catch(SQLException e)
          {
            //log.error("",e);
          log.error(null,e);
            FarmaUtility.showMessage(pJDialog,"Ocurrio un error al validar la forma de pago del pedido.\n" + e.getMessage(),null);
          }
    }

    /**
     * Se valida que el pedido tenga productos de campa�a
     * @author JCORTEZ
     * @since  03.07.2008
     */
    public static String verificaPedidoCamp(JDialog pJDialog, String numPed)
    {
      String resp = "";
      try
        {
          resp = DBCaja.verificaPedidoCamp(numPed);
        }catch(SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(pJDialog,"Ocurrio un error al validar pedido por campa�a.\n" + e.getMessage(),null);
        }
       return resp;     
    }        
    
    
    // RHERRERA:  HACE LLAMADO AL METODO DE ORACLE - MYBATIS
    // DUBILLUZ: USA JDBC para usar la misma conexion en toda la transaccion del cobro.
    // EN RECARGAS usa JDBC
    // COBRO BD    usa JDBC
    // PINPAD      usa IBATIS 
    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 20.04.2014
     */
    public static boolean nvoCobroBD(JDialog pJDialog,String pNumPedido,JTable tblDetallePago) {
        
        boolean m;
        int prueba;
       ///// EJEMPLO 1 ////
        ArrayList a_CodFormaPago = new ArrayList();
        ArrayList a_monto = new ArrayList();
        ArrayList a_CodMoneda = new ArrayList();
        ArrayList a_XXX = new ArrayList();
        ArrayList a_ImpTotal = new ArrayList();
        ArrayList a_NumTarjeta = new ArrayList();
        ArrayList a_FecVecTarjeta = new ArrayList();
        ArrayList a_NomCliTarjeta = new ArrayList();
        ArrayList a_CantCupon = new ArrayList();
        ArrayList a_DniTarjeta = new ArrayList();
        ArrayList a_CodBouch = new ArrayList();
        ArrayList a_CodLote = new ArrayList();
        
        int v_resultado=0;
        
        //////////////////////////////////
        try {
            for (int i = 0; i < tblDetallePago.getRowCount(); i++)
            {
              
                                a_CodFormaPago.add(i,tblDetallePago.getValueAt(i,0));
                                a_monto.add(i,tblDetallePago.getValueAt(i,4));
                                a_CodMoneda.add(i,tblDetallePago.getValueAt(i,6));
                                a_XXX.add(i,tblDetallePago.getValueAt(i,7));
                                a_ImpTotal.add(i,tblDetallePago.getValueAt(i,5));
                                a_NumTarjeta.add(i,tblDetallePago.getValueAt(i,8));
                                a_FecVecTarjeta.add(i,tblDetallePago.getValueAt(i,9));
                                a_NomCliTarjeta.add(i,tblDetallePago.getValueAt(i,10));
                                a_CantCupon.add(i,tblDetallePago.getValueAt(i,2));
                                a_DniTarjeta.add(i,tblDetallePago.getValueAt(i,12));
                                a_CodBouch.add(i,tblDetallePago.getValueAt(i,13));
                                a_CodLote.add(i,tblDetallePago.getValueAt(i,14));
                                
                           
                VariablesCaja.vDescripcionDetalleFormasPago = VariablesCaja.vDescripcionDetalleFormasPago + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 0) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 1) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 3) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 4) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 5) + " , " + 
                                                              FarmaUtility.getValueFieldJTable(tblDetallePago, i, 7) + "<BR>";
            }   
           
            /* FacadeCaja facadeCaja = new FacadeCaja();

            prueba = facadeCaja.pruebaCobro(pNumPedido,
            a_CodFormaPago,
            a_monto,
            a_CodMoneda,
            a_XXX,
            a_ImpTotal,
            a_NumTarjeta,
            a_FecVecTarjeta,
            a_NomCliTarjeta,
            a_CantCupon,
            a_DniTarjeta,
            a_CodBouch,
            a_CodLote
             ); */
            Map mapParametros =
                DBCobroBD.grabarNuevoCobro(pNumPedido, a_CodFormaPago, a_monto, a_CodMoneda, a_XXX, a_ImpTotal,
                                           a_NumTarjeta, a_FecVecTarjeta, a_NomCliTarjeta, a_CantCupon, a_DniTarjeta,
                                           a_CodBouch, a_CodLote);
            
            v_resultado = new Integer(mapParametros.get(getMayuscula("valor_out")).toString());
            VariablesCaja.vmensaje_out = mapParametros.get(getMayuscula("v_error_mensaje_out")).toString();
            VariablesCaja.vNumSecImpresionComprobantes = new Integer(mapParametros.get(getMayuscula("v_nuc_sec_out")).toString());            
            prueba = v_resultado;
            String pIndPedConvenio = (mapParametros.get("C_ESPEDIDOCONVENIO").toString()).substring(0,1);
            
            
           
           
            if (prueba == 0) {
                        m= false;
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(pJDialog, 
                                         "El pedido no puede ser cobrado. \n"+VariablesCaja.vmensaje_out+ 
                                         ". \n" +
                                         "NO CIERRE LA VENTANA.", 
                                         null);
            } else {
                
                if(pIndPedConvenio.equals("S")){
                      boolean  res = cobroConveni_Conexion();
                        if (res)                    {
                                    FarmaUtility.aceptarTransaccion();
                                    m = true;
                                                    }
                        else    {   
                                m = false;
                                FarmaUtility.liberarTransaccion();
                                FarmaUtility.showMessage(pJDialog, 
                                                         "El pedido no puede ser cobrado. \n" +
                                "Error en la conexi�n para el COBRO CONVENIO. \n",
                                                         null);     
                                }
                }
                else{
                    m = true;
                    obtieneAgrupaComprobante();
                    VariablesModuloVentas.vEsPedidoConvenio = false;
                    FarmaUtility.aceptarTransaccion();
                }
                
                
            }   
        } catch (Exception e) {
            log.info("",e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(pJDialog ,e.getMessage(), null); 
           // if(e.getErrorCode()==20000)
           //   FarmaUtility.showMessage(pJDialog ,e.getMessage(), null); 
            m = false;
            
        }
        return m ;
    }
    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 02.05.2014
     */
    private static boolean cobroConveni_Conexion() {
        VariablesModuloVentas.vEsPedidoConvenio = true;
        boolean resultado=true;
        String resMatriz = "";
        boolean esComprobanteCredito =false;                                                                    
        //if (Double.parseDouble(VariablesCaja.vValMontoPagado)>0)
        if (VariablesCaja.vValMontoCredito_2 > 0) 
                {
            esComprobanteCredito=true;                
                }
     try{   
            if (VariablesConvenioBTLMF.vFlgValidaLincreBenef.equals("1") || esComprobanteCredito)
            {
                String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
                if (indConexionRac.equals("S"))
                {
                   resMatriz = DBConvenioBTLMF.grabarTablasTmp();
                        if(resMatriz.equals("S")){
                                        DBConvenioBTLMF.grabarCobrarPedidoRac(FarmaConstants.INDICADOR_S);

                                        DBConvenioBTLMF.actualizaFechaProcesoRac();
                                        resultado = true;
                                                }
                 }
             }
            
            else   resultado  = true;      
         
         
     } catch (SQLException sql) {
              log.error("",sql);
                FarmaUtility.liberarTransaccion();
                resultado= false;
    }
    return resultado;                
    }
    
    public static String getMayuscula(String pCadena){
        return pCadena.toUpperCase();
    }
  public static void grabarNuevoCobro(DlgNuevoCobro pDlg,DlgFormaPago pDlgFP,Frame pFramePadre){
       pDlg.indCobroBD = pDlg.getIndCobraBD();
       
        //ERIOS 19.11.2013 Nuevo "nuevo cobro"
        //1. Valida exito en pago con tarjeta
        if(VariablesCaja.vIndDatosTarjeta && VariablesCaja.vCodVoucher.equals("")&&!pDlg.pEjecutaOldCobro){
            FarmaUtility.showMessage(pDlg, "Verifique el pago con tarjeta.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
            return;
        }
        
        //1.1 VALIDA IMPRESORAS

        
        //2. Valida exito en recarga virtual
        if (VariablesCaja.vIndPedidoConProdVirtual){
            DlgProcesarProductoVirtual dlgProcesarProductoVirtual = new DlgProcesarProductoVirtual(pDlg.myParentFrame,"Virtual",true);
            dlgProcesarProductoVirtual.setTblFormasPago(pDlg.tblFormasPago);
            dlgProcesarProductoVirtual.setTxtNroPedido(pDlg.txtMontoPagado);
            dlgProcesarProductoVirtual.mostrar();
            if(!dlgProcesarProductoVirtual.isVProcesoRecarga()){
                //ERIOS 21.11.2013 Se comenta porque se hace el control en DlgProcesarProductoVirtual
                //FarmaUtility.showMessage(this, "Hubo un error al procesar.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
                if(!pDlg.pEjecutaOldCobro)
                  pDlg.eventoEscape();
                
                return;
            }
        }

        //----   RHERRERA 04/04/2014 : Se comenta para obviar todo el paso del cobro en Java 
//  if ( (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) ||
//       !indCobroBD ) {
        
        if (!pDlg.indCobroBD){ // quitar si falla
        
            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("1. Si vIndPedidoCobrado=False, INGRESA METODO procesarNuevoCobro() ");
                pDlg.procesarNuevoCobro();
            }
            //se guarda valores
            VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();
            if (VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                /* if(pDlgFP.getMyParent()==null)
                    log.debug("null pDlgFP.myParentFrame");
                if(pDlg==null)
                    log.debug("null pDlg");
                if(pDlg.lblVuelto==null)
                    log.debug("null pDlgFP.lblVuelto");
                if(pDlg.tblDetallePago==null)
                    log.debug("null pDlgFP.tblDetallePago");                
                if(pDlg.txtMontoPagado==null)
                    log.debug("null pDlgFP.txtMontoPagado");                                
                 */
                UtilityCaja.finalizarPedidoNuevoCobro(pFramePadre, pDlg, pDlg.lblVuelto, pDlg.tblDetallePago, pDlg.txtMontoPagado,pDlg.vFormaPagoTarjetas);                
            }
        }else {
            //RHERRERA: Se Asume que el vIndPedidoCobrado = False
            VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();

            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                pDlg.procesarCobroBD();
            }
        }
          
          if(VariablesCaja.vIndPedidoCobrado&&!pDlg.pEjecutaOldCobro){
            //ERIOS 21.01.2014 Validacion de error en cobro
           if(FarmaVariables.vAceptar){
            FarmaVariables.vAceptar = false;
            pDlg.pedidoCobrado(null);
            }
            //Si la variable indica que de escape y recalcule  el ahorro del cliente
            //if(VariablesFidelizacion.vRecalculaAhorroPedido){
              //  eventoEscape();
            //}
            VariablesModuloVentas.vProductoVirtual=false;
        }
    }
  
    public static void grabarNuevoCobro_HHSur(DlgNuevoCobro pDlg,DlgFormaPagoHHSur pDlgFP,Frame pFramePadre){
         pDlg.indCobroBD = pDlg.getIndCobraBD();
         
          //ERIOS 19.11.2013 Nuevo "nuevo cobro"
          //1. Valida exito en pago con tarjeta
          if(VariablesCaja.vIndDatosTarjeta && VariablesCaja.vCodVoucher.equals("")&&!pDlg.pEjecutaOldCobro){
              FarmaUtility.showMessage(pDlg, "Verifique el pago con tarjeta.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
              return;
          }
          
          //1.1 VALIDA IMPRESORAS

          
          //2. Valida exito en recarga virtual
          if (VariablesCaja.vIndPedidoConProdVirtual){
              DlgProcesarProductoVirtual dlgProcesarProductoVirtual = new DlgProcesarProductoVirtual(pDlg.myParentFrame,"Virtual",true);
              dlgProcesarProductoVirtual.setTblFormasPago(pDlg.tblFormasPago);
              dlgProcesarProductoVirtual.setTxtNroPedido(pDlg.txtMontoPagado);
              dlgProcesarProductoVirtual.mostrar();
              if(!dlgProcesarProductoVirtual.isVProcesoRecarga()){
                  //ERIOS 21.11.2013 Se comenta porque se hace el control en DlgProcesarProductoVirtual
                  //FarmaUtility.showMessage(this, "Hubo un error al procesar.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
                  if(!pDlg.pEjecutaOldCobro)
                    pDlg.eventoEscape();
                  
                  return;
              }
          }

          //----   RHERRERA 04/04/2014 : Se comenta para obviar todo el paso del cobro en Java 
    //  if ( (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) ||
    //       !indCobroBD ) {
          
          if (!pDlg.indCobroBD){ // quitar si falla
          
              if (!VariablesCaja.vIndPedidoCobrado) {
                  log.debug("1. Si vIndPedidoCobrado=False, INGRESA METODO procesarNuevoCobro() ");
                  pDlg.procesarNuevoCobro();
              }
              //se guarda valores
              VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();
              if (VariablesCaja.vIndPedidoCobrado) {
                  log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                  /* if(pDlgFP.getMyParent()==null)
                      log.debug("null pDlgFP.myParentFrame");
                  if(pDlg==null)
                      log.debug("null pDlg");
                  if(pDlg.lblVuelto==null)
                      log.debug("null pDlgFP.lblVuelto");
                  if(pDlg.tblDetallePago==null)
                      log.debug("null pDlgFP.tblDetallePago");                
                  if(pDlg.txtMontoPagado==null)
                      log.debug("null pDlgFP.txtMontoPagado");                                
                   */
                  UtilityCaja.finalizarPedidoNuevoCobro(pFramePadre, pDlg, pDlg.lblVuelto, pDlg.tblDetallePago, pDlg.txtMontoPagado,pDlg.vFormaPagoTarjetas);                
              }
          }else {
              //RHERRERA: Se Asume que el vIndPedidoCobrado = False
              VariablesCaja.vVuelto = pDlg.lblVuelto.getText().trim();

              if (!VariablesCaja.vIndPedidoCobrado) {
                  log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                  pDlg.procesarCobroBD();
              }
          }
            
            if(VariablesCaja.vIndPedidoCobrado&&!pDlg.pEjecutaOldCobro){
              //ERIOS 21.01.2014 Validacion de error en cobro
             if(FarmaVariables.vAceptar){
              FarmaVariables.vAceptar = false;
              pDlg.pedidoCobrado(null);
              }
              //Si la variable indica que de escape y recalcule  el ahorro del cliente
              //if(VariablesFidelizacion.vRecalculaAhorroPedido){
                //  eventoEscape();
              //}
              VariablesModuloVentas.vProductoVirtual=false;
          }
      }
    
    public static void cargaIP_IMPRIMIR_LIFE(String pNumPedido,String pCodFormaPago) {
            try {
                 DBCaja.cambiaImpresoraTicket(pNumPedido,pCodFormaPago);
                FarmaUtility.aceptarTransaccion();
            } catch (SQLException sqle) {
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
            }
        }
    
    public static boolean isCobroTarjeta(String pNumPedido){
        boolean vResultado = false;
        
        return vResultado;
    }
}
