package svb.imp_fe.electronico;


import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaPrintService;
import common.FarmaPrintServiceTicket;
import common.FarmaUtility;
import common.FarmaVariables;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;


import mifarma.ptoventa.reference.VariablesPtoVenta;

import svb.imp_fe.electronico.impresion.FacadeComprobanteElectronico;
import svb.imp_fe.electronico.impresion.dao.ConstantesDocElectronico;
import svb.imp_fe.electronico.impresion.reference.DBComprobanteElectronico;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerFarma.FarmaPrinterFacade;
import printerUtil.FarmaPrinterConstants;

import venta.administracion.impresoras.reference.DBImpresoras;

import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;

import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesVirtual;

import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ImprimeComprobanteElectronico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES     01.09.2014  Creación<br>
 * <br>
 * @author Cesar Alfredo Huanes Bautista<br>
 * @version 1.0<br>
 *
 */
public class UtilityImpCompElectronico {
    private static final Logger log = LoggerFactory.getLogger(UtilityImpCompElectronico.class);
    FacadeComprobanteElectronico facade = new FacadeComprobanteElectronico();
    // KMONCADA 13.01.2015 DATOS DE IMPRESORA TERMICA
    private String tipoImpTermica;
    private String nomImpTermica;
    private String rutaImpTermica;
    private String rutaFileTestigo;


    public UtilityImpCompElectronico()throws Exception {
        super();
        cargarDatosImpresoras();
        rutaImpTermica = getRuta();
        
    }
    
    /**
     * @author KMONCADA
     * @since 12.05.2016
     * @param tipoImpTermica
     * @param nomImpTermica
     * @throws Exception
     */
    public UtilityImpCompElectronico(String tipoImpTermica, String nomImpTermica)throws Exception {
        this.tipoImpTermica = tipoImpTermica;
        this.nomImpTermica = nomImpTermica;
        this.rutaImpTermica = getRuta();
        
    }
    
    /**
     * Metodo que obtiene datos de la impresora termica configurada a la IP.
     * 
     * @since 13.01.2015 KMONCADA
     * @throws Exception
     */
    private void cargarDatosImpresoras()throws Exception{
        List lstDatosImpresora = getDatosImpresoraTerminca(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, FarmaVariables.vIpPc);
        if(lstDatosImpresora.size()>0){
            Map datoImpresora  = (HashMap)lstDatosImpresora.get(0);
            tipoImpTermica = (String)datoImpresora.get("TIPO");
            nomImpTermica = (String)datoImpresora.get("IMPRESORA");
        }else{
            throw new Exception("Error al consultar datos de impresora termica.\n Verifique configuración en \"Mantenimiento de Impresoras\".");
        }
        
    }
    
    /**
     * Imprimir texto de experto de ahorro
     * @author ASOSA
     * @since 10/03/2015
     * @kind PTOSYAYAYAYA
     * @param printer
     * @param codMarca
     */
   

    public boolean printDocumento(String pNumPedidoVta, String pSecCompPago, boolean isReimpresion, boolean isCobro) throws Exception {
        List listComanda = new ArrayList();
        listComanda = DBComprobanteElectronico.getDatosImpresion_Comprobante(FarmaVariables.vVersion, pNumPedidoVta, pSecCompPago, isReimpresion);
        /*
        for(int i=0;i<listComanda.size();i++){
            System.out.println("["+i+"] "+listComanda.get(i).toString());
            
        }
        */
        VariablesFidelizacion.numPedVtaTextExpert = pNumPedidoVta;
        VariablesFidelizacion.secCompPagoTextExpert = pSecCompPago;
        
        if (listComanda.size() > 0) {
            //boolean rest = impresionDocumentoEnTermica(listComanda, true, rutaFileTestigo, isCobro);
            boolean rest = impresionTermica(listComanda,rutaFileTestigo);
            if (!rest) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";               
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                //calculaPapelTermicoVta(listComanda);
            }
        } else {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            throw new Exception("Alerta Local:\n" +
                    "La cadena para Impresión es vacía." + "Por favor llamar a Mesa de Ayuda.");
        }
        boolean pImpResult = true; 
        
        
        
        
        return pImpResult;
    }
    
    /**
     * @author CCASTILLO
     * @since 11.05.2016
     * @param List
     * @return 
     */
    
    public static void calculaPapelTermicoVta(List listComanda){
        
        log.debug("calculaPapelTermicoVta inicio");
        
        double longitud=0,longitudTotal=0,l_aux=0;
        String saltoLinea="N";
        String tipoImpresora = VariablesPtoVenta.vTipoImpTermicaxIp;
        
        try{
            for (int i = 0; i < listComanda.size(); i++) {
                    saltoLinea=(String)((HashMap)listComanda.get(i)).get("SALTO_LINEA");
                    longitud=Double.parseDouble((String)((HashMap)listComanda.get(i)).get("LON_PTERMICO"));
                
                if(saltoLinea.equalsIgnoreCase("S")){
                     if(l_aux>longitud)
                         longitud=l_aux;
                    l_aux=0;   
                }else{ //Si no hay salto de linea esperar a la proxima linea
                    l_aux=longitud;
                    longitud=0;
                }
                    longitudTotal=longitudTotal+longitud;
            }
            
            log.debug("calculaPapelTermicoVta longitud parcial : "+longitudTotal);
            log.debug("calculaPapelTermicoVta tipo impresora   :  "+tipoImpresora);
            
            if(longitudTotal!=0){

                if(tipoImpresora.trim().equalsIgnoreCase("01")){
                    longitudTotal=longitudTotal+2.5; // última linea
                    longitudTotal=longitudTotal+13;  // primera linea (intervalo entre 10 y 13) margen  +-3 mm
                }else if (tipoImpresora.trim().equalsIgnoreCase("02")){ 
                    longitudTotal=longitudTotal+4;  // última linea
                    longitudTotal=longitudTotal+6;  // primera linea (intervalo entre 10 y 13) margen  +-3 mm  
                }
                
                longitudTotal=longitudTotal/10;  // pasar de milimetros a centimetro
                //DBVentas.actKardexPapelTermico(longitudTotal);
            }
            
            log.debug("calculaPapelTermicoVta fin - La longitud del papel termico es : "+longitudTotal);
        }catch(Exception ex){
            log.error("Error calculaPapelTermicoVta : ",ex);
        }
    }
    
    
    /**
     * @author CCASTILLO
     * @since 11.05.2016
     * @param String
     * @return 
     */
    
    public static void calculaPapelTermicoVtaHtml(String evaluaTexto){
        
        try{
            log.debug("calculaPapelTermicoVtaHtml texto a evaluar:\n"+evaluaTexto);
            int tamanio=0;
            String txtaux="";
            double ltotal=0,lparcial=0,nfilas=0;
            int max_letras=29;
            double max_longitud_papel=21.000;
            
            evaluaTexto=evaluaTexto.replace(""+(char)160, ""); //quita el valor &nbps; porque es espacio en blanco
            String [] cadenas = evaluaTexto.split("\n"); // divide la cadena por saltos de linea
            
    
            for(int i = 0; i<cadenas.length; i++){
                
                if(!(cadenas[i].trim().length()==0)){
                    log.debug(i+"* "+cadenas[i]);
                
                    tamanio=cadenas[i].length();
                    log.debug("txtaux : "+txtaux+" ---------  tamanio : "+tamanio);
                        if(tamanio!=0){
                            lparcial=2.4;                               // inicio del reglón
                            nfilas=tamanio/max_letras;                  // tamanio max permitido por linea = 29;
                            lparcial=lparcial+3*(nfilas+1)+2*(nfilas);  // Se agrega 3mm por linea y 2mm por espacio de separación
                            lparcial=lparcial+2.4;                      //fin del reglón
                            
                            ltotal=ltotal+lparcial;
                        } 
                }
            }   
            log.debug("calculaPapelTermicoVtaHtml parcial : "+ltotal);
            ltotal=ltotal+15;  // inicio de la pagina margen de error +-3mm
            ltotal=ltotal+1.8; // fin de la pagina
            
            log.debug("calculaPapelTermicoVtaHtml ltotal : "+ltotal);
            ltotal=ltotal/10; // pasar de milimetros a centimetros
            ltotal=(double) Math.round(ltotal * 1000) / 1000; //redondeo a 3 cifras;
            
            log.debug("calculaPapelTermicoVtaHtml ltotal(centimetros redondeo) : "+ltotal);
            if(ltotal>max_longitud_papel){
                ltotal=max_longitud_papel;
                log.debug("calculaPapelTermicoVtaHtml ltotal(excedio la lon max permitida y se redujo a ) : "+ltotal);
            }
            //DBVentas.actKardexPapelTermico(ltotal);
            
        }catch(Exception ex){
            log.error("Error calculaPapelTermicoVtaHtml :  ",ex);
        }
    }
      

    public boolean impresionDocumentoEnTermica(List listComanda, boolean isPonerLogo, String pRutaFile, boolean isCobro                                               
                                               ) throws Exception {

        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }

        if (!printer.startPrintService()) {
            throw new Exception("(Warrion) No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Termica por favor.");
        } else {
            String codMarca = "";
            printer.inicializate(); //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
            
            /*
            if (isPonerLogo) {
                codMarca =
                    DBComprobanteElectronico.getMarcaLocal(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
                if (codMarca == null) {
                    throw new Exception("Error al consultar Marca del Local");
                }
                imprimirTextoExpert(printer, codMarca, vPrintArchivo, pRutaFile);   //ASOSA - 10/03/2015 - PTOSYAYAYAYA
                // IMPRESION DE LOGO DE MARCA
                printer.printLogo(codMarca);
            }
            */
            
            Map mapComanda = new HashMap();
            String valor, tamanio, alineacion, bold, ajuste;
            boolean isNegrita = false;
            for (int i = 0; i < listComanda.size(); i++) {
                mapComanda = (HashMap)listComanda.get(i);
                valor = reemplazarCaracterRaros(((String)mapComanda.get("VALOR")));
                tamanio = ((String)mapComanda.get("TAMANIO"));
                alineacion = ((String)mapComanda.get("ALINEACION"));
                bold = ((String)mapComanda.get("BOLD"));
                //isNegrita = (!"N".equalsIgnoreCase(bold))?true: false;
                if (!"N".equalsIgnoreCase(bold))
                    isNegrita = true;
                else
                    isNegrita = false;
                ajuste = ((String)mapComanda.get("AJUSTE"));
                int lineado = 0;
                if ("S".equalsIgnoreCase(ajuste)) {
                    lineado = 1;
                }

                if ("-".equalsIgnoreCase(valor)) {
                    printer.printLineDotted(30);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                } else {
                    printer.printGeneral(valor, tamanio, alineacion, isNegrita, lineado);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine(valor, true); //
                    }
                }
            }
            //imprimirTextoExpert(printer, codMarca);   //ASOSA - 10/03/2015 - PTOSYAYAYAYA - DECIDIERON NO PONERLO ACA, PORSIAKSO LO COMENTO PORQ CAPAS SE LES OCURRE VOLVER A PONERLO
            // FIN DE IMPRESION
            printer.endPrintService(isCobro);
            if (pRutaFile != null) {
                vPrintArchivo.endPrintService();
            }

            /*** CCASTILLO 05/05/2016 ***/
            //Descuento del papel termico
            calculaPapelTermicoVta(listComanda);
            /*** CCASTILLO 05/05/2016 ***/
            
            return true;
        }
    }

    private String reemplazarCaracterRaros(String pText) {
        if (pText != null) {
            pText = pText.replaceAll("Á", "A");
            pText = pText.replaceAll("É", "E");
            pText = pText.replaceAll("Í", "I");
            pText = pText.replaceAll("Ó", "O");
            pText = pText.replaceAll("Ú", "U");
            pText = pText.replaceAll("á", "a");
            pText = pText.replaceAll("é", "e");
            pText = pText.replaceAll("í", "i");
            pText = pText.replaceAll("ó", "o");
            pText = pText.replaceAll("ú", "u");
            pText = pText.replaceAll("ñ", "n");
            pText = pText.replaceAll("Ñ", "N");
            pText = pText.replaceAll("°", "");
        }
        return pText;
    }

    /**
     * Prueba de gabeta con facturacion electronica
     * @since 13.01.2015 KMONCADA
     */
    public void abrirGabeta(){
        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); 
        log.info("inicio abrir gabeta electronico");
        printer.startPrintService();
        printer.abrirGabeta();
        log.info("fin abrir gabeta electronico");
    }
    
    public String getNombreTermica() throws SQLException {
        //String nombre = DBImpresoras.getNombreImpTermica();

        //return nombre;
        return this.nomImpTermica;
    }

    public String getModelo() throws SQLException {
        /*String modelo = DBImpresoras.getModeloImpTermica();
        return modelo;*/
        return this.tipoImpTermica;
    }


    /**
     * CHUANES
     * Obtiene la ruta de impresora Termica
     * 08/09/2014
     * */
    public String getRuta() throws SQLException {
        String ruta = null;
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);

        if (servicio != null) {
            try {
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pRuta = impresora.getName().toString().trim();
                    String pNombre = UtilityCaja.retornaUltimaPalabra(pRuta, "\\");

                    if (pNombre.trim().toUpperCase().equalsIgnoreCase(this.getNombreTermica().trim().toUpperCase())) {
                        boolean resultado = pRuta.startsWith("\\");
                        if (resultado) {
                            ruta = pRuta;
                        } else {
                            ruta = "\\" + "\\" + FarmaVariables.vIpPc + "\\" + pRuta;
                        }

                    }
                }

            } catch (Exception e) {
                log.error("", e);
            }
        }
        return ruta;
    }

    /**
     * CHUANES
     * Imprime Datos de Recarga Virtual
     * 08/09/2014
     * */
    public void prinInforVirtual(FarmaPrinterFacade printer, String pTipoProdVirtual, String pCodigoAprobacion,
                                 String pNumeroTarjeta, String pNumeroPin, String pNumeroTelefono, String pMonto,
                                 String pNumPedido, String pCodProd, FarmaPrintServiceTicket vPrintArchivo) {

       /* if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            printer.printLine(ConstantesDocElectronico.CODAPROB + pCodigoAprobacion);
            vPrintArchivo.printLine(ConstantesDocElectronico.CODAPROB + pCodigoAprobacion, true);
            printer.printLine(ConstantesDocElectronico.NUMTARJETA + pNumeroTarjeta);
            vPrintArchivo.printLine(ConstantesDocElectronico.NUMTARJETA + pNumeroTarjeta, true);
            printer.printLine(ConstantesDocElectronico.NUMPIN + pNumeroPin);
            vPrintArchivo.printLine(ConstantesDocElectronico.NUMPIN + pNumeroPin, true);
        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            UtilityCaja.obtieneDescImpresion(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    printer.printLine(((String)(array.get(i))).trim());
                    vPrintArchivo.printLine(((String)(array.get(i))).trim(), true);
                }
            }
        }*/

    }
    

    public void prinDatosCliente(FarmaPrinterFacade printer, FarmaPrintServiceTicket vPrintArchivo) {
        printer.printLineDotted(30);
        printer.printLineCenter(ConstantesDocElectronico.DATOSCLIENTE);
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.NOMBRE + "______________________________________________");
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.DNI + "_________________________________________________");
        printer.stepLine();
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.FIRMA + "_______________________________________________");


        vPrintArchivo.printLine("--------------------------------------------------------------------", true);
        vPrintArchivo.printLine(ConstantesDocElectronico.DATOSCLIENTE, true);
        vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRE + "______________________________________________",
                                true);
        vPrintArchivo.printLine(ConstantesDocElectronico.DNI + "_________________________________________________",
                                true);
        vPrintArchivo.printLine(ConstantesDocElectronico.FIRMA + "_______________________________________________",
                                true);


    }

    private boolean imprimirTMU950DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            return false;
        } else {

            //Abrimos caja
            //27+112+0+25+250
            vPrint.printLine((char)27 + "p" + (char)0 + (char)25 + (char)250, false);

            vPrint.endPrintServiceSinCompletar();

            return true;
        }
    }

    private boolean imprimirTSP700DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            return false;
        } else {

            //Abrimos caja
            //Star      TSP-700 27,07,11,55,07
            vPrint.printLine("" + (char)27 + (char)7 + (char)11 + (char)55 + (char)7, false);

            vPrint.endPrintServiceSinCompletarDelivery();

            return true;
        }
    }
    
    public void setRutaFileTestigo(String rutaFileTestigo) {
        this.rutaFileTestigo = rutaFileTestigo;
    }
    
    public boolean impresionDataQR_PDF(List listDocumento, String pRutaFile) throws Exception {
        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }
        if (!printer.startPrintService()) {
            throw new Exception("No se pudo iniciar la Impresión del Documento.\nError cuando se imprime en codigo QR" +
                                "\nVerifique su impresora Termica por favor.");
        } else {
            //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
            printer.inicializate(); 
            
            for (int i = 0; i < listDocumento.size(); i++) {
                log.info("LINEA --> "+(HashMap)listDocumento.get(i));
                printer.printer((HashMap)listDocumento.get(i));
                if ("-".equalsIgnoreCase(((String)((HashMap)listDocumento.get(i)).get("VALOR")))) {
                    printer.printLineDotted(30);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                } else {
                    
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine(((String)((HashMap)listDocumento.get(i)).get("VALOR")), true); //
                    }
                }
            }
            //printer.printCodeQR("1042282628|Richard|Nicola|Argumedo|Loja|978523838|Movistar|33|4|");
            printer.endPrintService();
            if (pRutaFile != null) {
                vPrintArchivo.endPrintService();
            }
        }
        
        /*** CCASTILLO 05/05/2016 ***/
        //Descuento del papel termico
        calculaPapelTermicoVta(listDocumento);
        /*** CCASTILLO 05/05/2016 ***/
        return true;
    }
    
    public boolean impresionTermica(List listDocumento, String pRutaFile)throws Exception {
        for (int j=0;j<1;j++){
            FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
            FarmaPrintServiceTicket vPrintArchivo = null;
            if (pRutaFile != null) {
                vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
                vPrintArchivo.startPrintService();
            }
            //if (true) {
            if (!printer.startPrintService()) {
                throw new Exception("(Advertencia) No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Termica por favor.");
            } else {            
                //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
                printer.inicializate(); 
                
                for (int i = 0; i < listDocumento.size(); i++) {
                    log.info("LINEA --> "+(HashMap)listDocumento.get(i));
                    printer.printer((HashMap)listDocumento.get(i));
                    if ("-".equalsIgnoreCase(((String)((HashMap)listDocumento.get(i)).get("VALOR")))) {
                        printer.printLineDotted(30);
                        if (pRutaFile != null) {
                            vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                    true);
                        }
                    } else {
                        
                        if (pRutaFile != null) {
                            vPrintArchivo.printLine(((String)((HashMap)listDocumento.get(i)).get("VALOR")), true); //
                        }
                    }
                }
                printer.endPrintService();
                if (pRutaFile != null) {
                    vPrintArchivo.endPrintService();
                }
            }
            
            /*** CCASTILLO 05/05/2016 ***/
            //Descuento del papel termico
            //calculaPapelTermicoVta(listDocumento);
            /*** CCASTILLO 05/05/2016 ***/
        }
        
        return true;
    }
    
    public boolean impresionMatricial(List listDocumento, String pRutaFile){
        boolean isImprimio = true;
        FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }
        if (!vPrint.startPrintService()) {
            isImprimio = false;
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
        }else{
            vPrint.activateCondensed();
            for (int i = 0; i < listDocumento.size(); i++) {
                String linea = (String)((HashMap)listDocumento.get(i)).get("VALOR");
                vPrint.printLine(linea, true);
                if (pRutaFile != null) {
                    vPrintArchivo.printLine(linea, true); //
                }
            }
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            vPrintArchivo.endPrintService();
            VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            isImprimio = true;
        }
        return isImprimio;
    }
    /**
     * Realiza la justificacion de la linea, segun el tamaño de la letra.
     * @author KMONCADA 
     * @since 01.06.2015
     * @param valor VALOR A JUSTIFICAR
     * @param tamanio tamaño de la fuente a imprimir
     * @return
     */
    private ArrayList aplicarJustificacion(String valor, String tamanio){
        ArrayList lstValor = new ArrayList();
        int tamanioTexto = Integer.parseInt(tamanio);
        int cantidadCaracteres = 64;
        
        switch(tamanioTexto){
            case 0 : cantidadCaracteres = 64; break;
            case 1 : cantidadCaracteres = 48; break;
            case 2 : cantidadCaracteres = 24; break;
            case 3 : cantidadCaracteres = 16; break;
            case 4 : cantidadCaracteres = 12; break;
            case 5 : cantidadCaracteres = 9; break;
            case 6 : cantidadCaracteres = 8; break;
        }
        
        String val = valor;
        if(val.length() > cantidadCaracteres){
            String aux = val;
            String lineaAux = "";
            String palabra = "";
            while(aux.length()!=0){
                int espacioBlanco = aux.indexOf(" ");
                if(espacioBlanco!=-1){
                    palabra = aux.substring(0,espacioBlanco);
                    if((lineaAux.length()+palabra.length()) < cantidadCaracteres){
                        if(lineaAux.length()==0){
                            lineaAux = palabra;
                        }else{
                            lineaAux = lineaAux+" "+palabra;
                        }
                    }else{
                        //lstValor.add(lineaAux);
                        lstValor.add(finalizaJustifica(lineaAux,cantidadCaracteres));
                        lineaAux = palabra;
                    }
                    aux = aux.substring(espacioBlanco+1);
                }else{
                    lineaAux = lineaAux+" "+aux;
                    lstValor.add(finalizaJustifica(lineaAux,cantidadCaracteres));
                    aux = "";
                }
            }
        }else{
            lstValor.add(valor);
        }
        return lstValor;    
    }
    
    /**
     * Completa con espacios entre las palabras para ocupar el espacio de la linea segun la cantidad de caracteres
     * por el tamaño de la letra a imprimir
     * @author KMONCADA
     * @since 01.06.2015
     * @param valor valor a justificar
     * @param cantidadCaracteres cantidad por cada linea 
     * @return texto completado con espacios
     */
    private String finalizaJustifica(String valor, int cantidadCaracteres){
        String letra = valor;
        if(letra.trim().length() != cantidadCaracteres){
            int diferencia = cantidadCaracteres - letra.trim().length();
            String espaciosAnt = "";
            String espacios = " ";
            String palabras[] = letra.split(" ");
            System.err.println(palabras.length);
            int posFinal=0;
            System.err.println("diferencia "+diferencia );
            while (diferencia>0){
                letra = "";
                posFinal=0;
                espaciosAnt = espaciosAnt+" ";
                espacios = espacios+" ";
                for(int i=0;i<palabras.length;i++){
                    if(i==0){
                        letra = palabras[i];
                    }else{
                        letra = letra + espacios + palabras[i];
                        diferencia--;
                        if(diferencia<1){
                            break;
                        }
                    }
                    posFinal = i;
                }
            }
            if((posFinal+1)!=palabras.length){
                System.err.println("no llego ");
                for(int k=(posFinal+2);k<palabras.length;k++){
                    letra = letra + espaciosAnt + palabras[k]; 
                }
            }
        }
            return letra;
    }
    
    
    /// metodos agregados
    public static List getDatosImpresoraTerminca(String pCodGrupoCia, String pCodLocal, String pIpPc) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        parametros.add(pIpPc);
        log.info("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("PTOVENTA_CAJ.GET_IMPRESORA_TERMICA(?,?,?)", parametros);

    }
    
    public boolean printDato_para_Cajero(String pNumPedidoVta, String pSecCompPago, boolean isReimpresion, boolean isCobro) throws Exception {
        List listComanda = new ArrayList();
        listComanda = DBComprobanteElectronico.getDatosImpresion_Comp_Cajero(FarmaVariables.vVersion, pNumPedidoVta, pSecCompPago, isReimpresion);
        /*
        for(int i=0;i<listComanda.size();i++){
            System.out.println("["+i+"] "+listComanda.get(i).toString());
            
        }
        */
        VariablesFidelizacion.numPedVtaTextExpert = pNumPedidoVta;
        VariablesFidelizacion.secCompPagoTextExpert = pSecCompPago;
        
        if (listComanda.size() > 0) {
            //boolean rest = impresionDocumentoEnTermica(listComanda, true, rutaFileTestigo, isCobro);
            boolean rest = impresionTermica(listComanda,rutaFileTestigo);
            if (!rest) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";               
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                //calculaPapelTermicoVta(listComanda);
            }
        } else {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            throw new Exception("Alerta Local:\n" +
                    "La cadena para Impresión es vacía." + "Por favor llamar a Mesa de Ayuda.");
        }
        boolean pImpResult = true; 
        
        
        
        
        return pImpResult;
    }
    public boolean printDato_para_Laboratorio(String pNumPedidoVta, String pSecCompPago, boolean isReimpresion, boolean isCobro,
                                              String pNumOrdenVta) throws Exception {
        List listComanda = new ArrayList();
        listComanda = DBComprobanteElectronico.getDatosImpresion_Comp_Laboratorio(FarmaVariables.vVersion, pNumPedidoVta, pSecCompPago, isReimpresion,
                                                                                  pNumOrdenVta);
        /*
        for(int i=0;i<listComanda.size();i++){
            System.out.println("["+i+"] "+listComanda.get(i).toString());
            
        }
        */
        VariablesFidelizacion.numPedVtaTextExpert = pNumPedidoVta;
        VariablesFidelizacion.secCompPagoTextExpert = pSecCompPago;
        
        if (listComanda.size() > 0) {
            //boolean rest = impresionDocumentoEnTermica(listComanda, true, rutaFileTestigo, isCobro);
            boolean rest = impresionTermica(listComanda,rutaFileTestigo);
            if (!rest) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";               
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                //calculaPapelTermicoVta(listComanda);
            }
        } else {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            throw new Exception("Alerta Local:\n" +
                    "La cadena para Impresión es vacía." + "Por favor llamar a Mesa de Ayuda.");
        }
        boolean pImpResult = true; 
        
        
        
        
        return pImpResult;
    }

}
