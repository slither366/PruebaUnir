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
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaPrintServiceTicket;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

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


public class UtilityCajaDentalTayloc {

    private static final Logger log = LoggerFactory.getLogger(UtilityCajaDentalTayloc.class);

    private static int numeroCorrel = 1;
    public static int acumuCorre = 0;

    /**
     * Constructor
     */
    public UtilityCajaDentalTayloc() {
    }

    public static void imprimeBoletaRenova(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                            String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                            String pNomImpreso, String pDirImpreso, String pValTotalAhorro,
                                            String pRuta, boolean bol) throws Exception {
        log.debug("IMPRIMIR BOLETA No : " + pNumComprobante);
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;

        //JCORTEZ 06.07.09 ruta para la genericon de archivo
        // if(bol) VariablesCaja.vRutaImpresora=pRuta;

        //FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora + "boleta" + pNumComprobante + ".txt", false);
        //FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);
        FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);
        
        //JCORTEZ 16.07.09 Se genera archivo linea por linea
        FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
        vPrintArchivo.startPrintService();

        log.debug("Ruta : " + VariablesCaja.vRutaImpresora + "boleta_renova_" + VariablesCaja.vNumPedVta + ".txt");
        //  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
        log.debug("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
        if (!vPrint.startPrintService()) {


            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
        }

        else {
            try {
                vPrint.activateCondensed();
                
                vPrint.printLine("",true);
                vPrint.printLine("",true);
                vPrint.printLine("                    "+
                                                   FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 66)+
                                                   pFechaBD+
                                                  "    ",true);
                vPrint.printLine("                           "+
                                                      FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(), 75),true);
                
                vPrint.printLine("                                                                                          No. "+
                                                                                                                                  pNumComprobante.substring(0, 3) + "-" + 
                                                                                                                                  pNumComprobante.substring(3, 10),true);                
                vPrint.printLine(" ",true);
                vPrint.printLine(" ",true);
                
                int linea = 0;
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    //Agregado por DVELIZ 13.10.08
                    String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
                    log.debug("valor 1:" + valor);
                    if (valor.equals("0.000"))
                        valor = " ";
                    //fin DVELIZ
                    log.debug("Deta " + (ArrayList)pDetalleComprobante.get(i));
                    log.debug("valor 2:" + valor);
                    
                    //vPrint.printLine("_____ i11  999 Producto de atencion numero 1                                         9,999.99    99,999.99   ___",true);
                    //vPrint.printLine("_____ i12  999 Producto de atencion numero 2                                         9,999.99    99,999.99   ___",true);

                    
                    // Desc / Unidad / Lab /  Prec UNit / DCTO / Sub Total
                    
                    vPrint.printLine("               " +
                                     FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                                    3) + "  " +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),
                                                                      27) + " " +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),
                                                                      11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),
                                                                      16) + " " +
                                     FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),
                                                                    9) + " " +
                            //Agregado por DVELIZ 10.10.08
                            FarmaPRNUtility.alinearDerecha(valor, 4) + " " +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),
                                                           10), true);

                    linea += 1;
                }


                if (pDetalleComprobante.size() <= 4) {
                    for (int j = linea; j <= 4; j++) {
                        vPrint.printLine(" ", true);
                        vPrintArchivo.printLine(" ", true);
                    }
                }
                
                //*************************************INFORMACION DEL CONVENIO*************************************************//
                //*******************************************INICIO************************************************************//
                double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
                if (auxTotalDcto > 0) {
                    String obtenerMensaje = "";
                    String indFidelizado = "";
                    log.info("Identificando cliente fidelizado");
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                        indFidelizado = "S";
                    } else {
                        indFidelizado = "N";
                    }
                    log.info("Fidelizado--> " + indFidelizado);
                    obtenerMensaje = UtilityCaja.obtenerMensaAhorro(pJDialog, indFidelizado);
                    vPrint.printLine("" + obtenerMensaje + " " + " S/. " + pValTotalAhorro + "  ",
                                     true);
                    vPrintArchivo.printLine("" + obtenerMensaje + " S/. " + pValTotalAhorro + "  ",
                                            true);

                }
                else{
                    vPrint.printLine("" , true);
                    vPrintArchivo.printLine("" ,   true);
                }
                vPrint.printLine("" , true);
                vPrint.printLine("" , true);
                vPrint.printLine("" , true);
                vPrint.printLine("" , true);

                vPrint.printLine("             Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion +
                                 FarmaPRNUtility.llenarBlancos(11) + VariablesModuloVentas.vTituloDelivery, true);
                vPrint.printLine("" , true);

                /*
                vPrint.printLine("       REDO: " + pValRedondeo + " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " +
                                 VariablesCaja.vApePatCajeroImpreso + " " + " CAJA: " + VariablesCaja.vNumCajaImpreso +
                                 " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso + " VEND: " +
                                 VariablesCaja.vNomVendedorImpreso + " " + VariablesCaja.vApePatVendedorImpreso, true);*/

                vPrint.printLine("                   " +
                                 FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),
                                                                  66) + " " + "     S/ " +
                                 FarmaPRNUtility.alinearDerecha(pValTotalNeto, 10), true);
                
 
                
                //*********************************************FIN*************************************************************//
                //*************************************INFORMACION DEL CONVENIO***********************************************//

                VariablesModuloVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
                VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();
                VariablesModuloVentas.vTituloDelivery = "";
                
                
                vPrint.deactivateCondensed();
                vPrint.endPrintService();
                vPrintArchivo.endPrintService();

                log.info("Fin al imprimir la boleta Renova: " + pNumComprobante);
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";

                //JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
                DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta, pNumComprobante, "C");
                log.debug("Guardando fecha impresion cobro..." + pNumComprobante);
            } catch (SQLException sql) {
                //log.error("",sql);
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.debug("Error de BD " + sql.getMessage());

                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE RENOVA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la boleta RENOVA : " + sql.getMessage());
                log.error(null, sql);
            }

            catch (Exception e) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE RENOVA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la boleta RENOVA: " + e);
            }


        }
    }

    public static void imprimeFactura(JDialog   pJDialog,
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
      //FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora,false);
      ///VariablesCaja.vRutaImpresora = "\\\\192.168.1.6\\BOLETA";
      FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);
      

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
                String mesLetraSOLO=pFechaBD.substring(3,5);
      String ano = pFechaBD.substring(6,10);
      String hora = pFechaBD.substring(11,19);
      vPrint.activateCondensed();
                
                vPrint.printLine("            " + " ", true);
                vPrint.printLine("            " + " ", true);
                vPrint.printLine("            " + " ", true);
                vPrint.printLine("            " + " ", true);  
                //vPrint.printLine("            " + " ", true);
                //vPrint.printLine("            " + " ", true);
                //vPrint.printLine("            " + " ", true);
                
                vPrint.printLine("            " + FarmaPRNUtility.alinearIzquierda(pNomImpreso,200)+
                                 " " +pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10)
                                 , true);
                vPrint.printLine("            " + pNumDocImpreso+"                 "+pFechaBD , true);
                vPrint.printLine("            " +  FarmaPRNUtility.alinearIzquierda(pDirImpreso,100)+
                                 "N-Venta:"+VariablesCaja.vNumPedVta, true);

                vPrint.printLine("            " + " ", true);
                vPrint.printLine("            " + " ", true);

                vPrint.printLine("            " + " ", true);
      
      int linea = 0;
      double pMontoOld = 0,pMontoNew = 0,pMontoDescuento = 0;
      
      
                log.info("" + VariablesModuloVentas.vTipoPedido);          
         double montoAcumulado = 0;
          double montoSinIGV_Calculado = Double.parseDouble(FarmaUtility.formatNumber(Double.parseDouble(pValTotalNeto.trim().replace(",", ""))-
                                                      Double.parseDouble(pValTotalIgv.trim().replace(",", "")),2).replace(",", ""));
                          
      for (int i=0; i<pDetalleComprobante.size(); i++) {
          double pPrecUnit = Double.parseDouble(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim().replace(",","")); 
          double pSubTotal = Double.parseDouble(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim().replace(",",""));
          
          String pPreciUnitSinIGV =  FarmaUtility.formatNumber(pPrecUnit).trim();//FarmaUtility.formatNumber(pPrecUnit/1.18,2).trim();
          String pSubTotalSinIGV  =  FarmaUtility.formatNumber(pSubTotal).trim();//FarmaUtility.formatNumber(pSubTotal/1.18,2).trim();
          
          pMontoOld = montoAcumulado;
          //montoAcumulado += Double.parseDouble(FarmaUtility.formatNumber(pSubTotal/1.18,2).trim().replace(",",""));
          montoAcumulado += Double.parseDouble(FarmaUtility.formatNumber(pSubTotal,2).trim().replace(",",""));
          
          if((i+1)==pDetalleComprobante.size()){
              // es la ultima fila
              if(montoSinIGV_Calculado!=montoAcumulado){
                  pSubTotalSinIGV = FarmaUtility.formatNumber(montoSinIGV_Calculado-(pMontoOld),2).trim();
                  pPreciUnitSinIGV = FarmaUtility.formatNumber(Double.parseDouble(pSubTotalSinIGV.replace(",",""))/
                                                               Double.parseDouble(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).replace(",",""))
                                                               ,2).trim();
              }
          }
            /*
             * valores SIN IGV
          vPrint.printLine("            " + 
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),9)+  " " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim()+"  " +
                            "("+((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim() + ")" ,80)+"      " +
                            FarmaPRNUtility.alinearDerecha(pPreciUnitSinIGV,9)+"      " + 
                            FarmaPRNUtility.alinearDerecha(pSubTotalSinIGV,9) 
                           , true);*/
          
            vPrint.printLine("       " + 
            
            //codigo                 
            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),7)+  " " +
            //nombre
            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),50)+  " " +
            // LOTE
            FarmaPRNUtility.alinearIzquierda("Lote:"+((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),30)+  " " +                             
                             
            //unidad
            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),10)+  " " +
            //cantidad
            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),7)+  " " +
            //prec unit
            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim().replace(",",""),7)+"    " + 
            //sub total
            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim().replace(",",""),7) 
                             , true);          
        linea += 1;
        indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
        //verifica que solo se imprima un producto virtual en el comprobante
        if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
          VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
        else
          VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
      }

      
      if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
          linea++;
      }

      //MODIFICADO POR DVELIZ 13.10.08
      //
        for (int j=linea; j<=ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)  {
            vPrint.printLine(" ",true);      
        }
                
      vPrint.printLine(" ",true);  
                vPrint.printLine(" ",true);  
                vPrint.printLine(" ",true);  
                vPrint.printLine("     SON: " +
                                          FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),66) , true);
                vPrint.printLine("            " + " ", true);
      
      /* ***************************************** */
      /* ***************************************** */
      String pTipoCambioActual = DBCaja.getTipoCambioActual();
      String pTotalDolares =  FarmaUtility.formatNumber(Double.parseDouble(pValTotalNeto.replace(",",""))/Double.parseDouble(pTipoCambioActual),2);
      
      
      vPrint.printLine("                  " + 
                       //FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto),104)+ 
                       FarmaPRNUtility.alinearIzquierda("",73)+ 
                       "  S/"+ 
                       FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(Double.parseDouble(pValTotalNeto.trim().replace(",", ""))-
                                      Double.parseDouble(pValTotalIgv.trim().replace(",", "")),2),10)+

                " "+  "S/." + FarmaPRNUtility.alinearDerecha(pValTotalIgv,9)+ " "+
                      "S/." + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10)
                    , true);
      
               
      String vLinea = " REDO:" + pValRedondeo +
                       " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                       " CAJA:" + VariablesCaja.vNumCajaImpreso +
                       " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                       " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso;
      vLinea = FarmaPRNUtility.alinearIzquierda(vLinea,85);
      //vPrint.printLine(vLinea,true);
      
      vPrint.endPrintService();
       
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
                }
            
          
       }
    }



    public static void main(String[] args) {
        FarmaPrintService vPrint = new FarmaPrintService(24, "\\\\192.168.1.41\\factura", false);
        if ( !vPrint.startPrintService() ) {
        System.out.println("no se puede ");
        }
        else{
            System.out.println("puede ");
            
            vPrint.activateCondensed();
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


            vPrint.endPrintService();
        }
        
    }
    
}
