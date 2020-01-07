package venta.administracion.impresoras.reference;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;

import common.FarmaConstants;
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaPrintServiceTicket;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.convenio.reference.DBConvenio;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.recaudacion.reference.FacadeRecaudacion;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ImpresoraTicket.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      13.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ImpresoraTicket
{    
    private static final Logger log = LoggerFactory.getLogger(ImpresoraTicket.class);
    private int anchoLinea;
    private final int ANCHO_LINEA_DEFAULT = 42;
    public static final int ANCHO_LINEA__TM4950 = 40;
        
    public ImpresoraTicket()
    {
        super();
        this.anchoLinea = ANCHO_LINEA_DEFAULT;        
    }
    
    public ImpresoraTicket(int pAnchoLinea)
    {
        super();
        this.anchoLinea = pAnchoLinea;        
    }
    
    public boolean imprimir(ArrayList<String> pTexto, 
                            String pModelo, 
                            String pRutaImpresora, 
                            boolean vImprimeTestigo, 
                            String pNumComprobante, 
                            String pIndActualizaImpr, 
                            String strNumPedido,
                            String tipoComp,
                            ArrayList<String> pTextoTratamiento)
    {
        boolean vImpr=false;
        if(pModelo == null)
            pModelo = " ";
        log.info("¿¿pModelo?? : "+pModelo);
        switch(pModelo)
        {
            case "TMU950":  vImpr = imprimirTMU950(pTexto,pRutaImpresora,strNumPedido,pTextoTratamiento);
                            break; 
            case "TMU950DK":vImpr = imprimirTMU950DK(pRutaImpresora);
                            break; 
            default:        vImpr = imprimirGenerico(pTexto,pRutaImpresora);
                            break;
        }
		log.info("¿¿IMPRIMIRO?? : "+vImpr);        
        if(vImprimeTestigo)
        {
            try
            {
                /*String strNumPedido="";
                if(pIndActualizaImpr.equals("C")){
                    strNumPedido=VariablesCaja.vNumPedVta;
                }else if(pIndActualizaImpr.equals("A")){
                    strNumPedido=VariablesCaja.vNumPedVta_Anul;
                }*/
                DBCaja.actualizaFechaImpr(strNumPedido,pNumComprobante,pIndActualizaImpr);
            }
            catch (SQLException e)
            {
                log.error("",e);
            }
            imprimirTestigo(pTexto, pNumComprobante, pIndActualizaImpr, tipoComp);            
        }
		log.info("retornas: "+vImpr);
        return vImpr;
    }
    
    private boolean imprimirTMU950(ArrayList<String> pTextos, String pRutaImpresora,
                                   String pNumPedido,ArrayList<String> pTextosTratamiento
                                   ) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            //
            //Contrario, retorna 8, avanza 10
            //Espacios de retorno de papel
            /*for(int i=0;i<8;i++){
                vPrint.printLine((char)27+"e"+(char)1,false);
            }*/
            //Character spacing
            //vPrint.printLine((char)27+" "+(char)6,false);
            
            //Font
            //vPrint.printLine((char)27+"!"+(char)1,false);
            
            //Metodo de cortar manualmente el texto
            /*for(String linea:pTextos){
                ArrayList<String> tmpLinea = FarmaUtility.splitString(linea, 40);
                for(String tmp:tmpLinea){
                    String cadena = FarmaPRNUtility.alinearIzquierda(tmp, 40);
                    vPrint.printLine(cadena+cadena,true);    
                }
            }*/
            //Metodo seteando la impresora a PARALELO(Receipt, Journal)
            //vPrint.printLine((char)27+"z"+(char)1,false);
            vPrint.activateCondensed();
            for(String linea:pTextos){
                vPrint.printLine(linea,true);    
            }
            //Espacios para correr el papel
            for(int i=0;i<10;i++){
                vPrint.printLine((char)27+"d"+(char)1,false);
            }
            vPrint.printLine((char)27+"z"+(char)0,false);
            vPrint.printLine((char)27+"i",false);
            // FIN imp0rimir TICKET TRAMAMIENTO
            vPrint.endPrintServiceSinCompletar();



            //vPrint.printLine((char)27+"z"+(char)0,false);
            
            //Espacios para correr el papel
            //for(int i=0;i<2;i++){
            //    vPrint.printLine((char)27+"d"+(char)1,false);
            //}
            
            //Cotar papel
            //vPrint.printLine((char)27+"i",false);  
            //vPrint.endPrintServiceSinCompletar();
            
            //vPrint.printLine((char)27+"z"+(char)0,false);
            //vPrint.printLine((char)27+"i",false);
            // FIN imp0rimir TICKET TRAMAMIENTO
            //vPrint.endPrintServiceSinCompletar();


            ///////////// IMPRIMIR TRATAMIENTO
            // INICIO imprimir TICKET TRAMAMIENTO
            // RENOVA no imprime TRATAMIENTO.
            pTextosTratamiento = new ArrayList();
            if(pTextosTratamiento.size()>0){
            FarmaPrintService vPrintTrat = new FarmaPrintService(66,pRutaImpresora, false);
            if ( !vPrintTrat.startPrintService() ) 
            return false;
                else{
                        vPrintTrat.printLine((char)27+"z"+(char)1,false);
                    for(String linea:pTextosTratamiento){
                        vPrintTrat.printLine(linea,true);    
                    }
                        vPrintTrat.printLine((char)27+"z"+(char)0,false);
                    vPrintTrat.printLine((char)27+"i",false);             
                    // FIN imp0rimir TICKET TRAMAMIENTO 
                    vPrintTrat.endPrintServiceSinCompletar();
                }
            }
            // FIN imprimir TICKET TRAMAMIENTO
            
            
            
            return true;
        }
    }

    public boolean abrirGabeta(String pModelo, String pRutaImpresora){    
        boolean vImpr=false;
        if(pModelo == null) pModelo = " ";
        switch(pModelo){
        case "TMU950":
            vImpr = imprimirTMU950DK(pRutaImpresora);
           break; 
        case "TSP700":
            vImpr = imprimirTSP700DK(pRutaImpresora);
           break; 
        default:
            break;
        }
        
        return vImpr;
    }
    
    private boolean imprimirTMU950DK(String pRutaImpresora) {
        log.info(">>> pRutaImpresora imprimirTMU950DK>>>"+pRutaImpresora);
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            
            //Abrimos caja
            //27+112+0+25+250
            vPrint.printLine((char)27+"p"+(char)0+(char)25+(char)250,false);
            
            vPrint.endPrintServiceSinCompletar();
            
            return true;
        }
    }
    
    private boolean imprimirTSP700DK(String pRutaImpresora) {
        log.info(">>> pRutaImpresora imprimirTSP700DK>>>"+pRutaImpresora);
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            
            //Abrimos caja
            //Star      TSP-700 27,07,11,55,07
            vPrint.printLine(""+(char)27+(char)7+(char)11+(char)55+(char)7,false);
            
            vPrint.endPrintServiceSinCompletarDelivery();
            
            return true;
        }
    }
    
    private boolean imprimirGenerico(ArrayList<String> pTextos, String pRutaImpresora) {

        log.info(">>> pRutaImpresora imprimirGenerico>>>"+pRutaImpresora);
        FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{            
            log.info(">>> Exito imprimirGenerico>>>"+pRutaImpresora);
            for(String linea:pTextos){
                vPrint.printLine(linea,true);    
            }            
            vPrint.endPrintService();
            return true;
        }
    }
    
    private boolean imprimirTestigo(ArrayList<String> pTextos,
                                    String pNumComprobante, 
                                    String pIndActualizaImpr,
                                    String tipoComp)
    {
        String ruta;
        try
        {
            ruta=UtilityPtoVenta.obtieneDirectorioComprobantes();
        }
        catch (SQLException e)
        {
            ruta="";
        }
        Date vFecImpr = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaImpresion =  sdf.format(vFecImpr);                
        
        String vNombreTestigo;
        if(pIndActualizaImpr.equals("C"))
        {
            if("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo=ruta+fechaImpresion+"_TF_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
            else
                vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        }
        else if(pIndActualizaImpr.equals("A"))
        {
            if("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo=ruta+fechaImpresion+"_TF_"+VariablesCaja.vNumPedVta_Anul+"_"+pNumComprobante+"_Anul.TXT";
            else
                vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta_Anul+"_"+pNumComprobante+"_Anul.TXT";
        }
        else
        {
            vNombreTestigo=ruta+fechaImpresion+"_T_Comprobante.TXT";
        }
        
        return imprimirGenerico(pTextos, vNombreTestigo);
    }
    
    public static void main(String[] args){
        ArrayList<String> texto = new ArrayList<String>();
        String ruta = "\\\\192.168.1.41\\ticket";
        texto.add("***************************************1");
        texto.add("PRUEBA DE IMPRESION TICKETERA");
        texto.add("NOMBRE : "+"DIEGO UBILLUZ");
        texto.add(" ");
        texto.add("***************************************2");  
        texto.add("Esto es una prueba de la impresora de ticket's.");  
        texto.add("***************************************0");  
        
        ImpresoraTicket ticketera = new ImpresoraTicket();
        ticketera.imprimir(texto, "TMU950", ruta, false, "", "","","",texto);
        //ticketera.abrirGabeta("TSP700",ruta);
    }
    
    public void generarDocumento(JDialog pJDialog,
                                 ArrayList<String> vPrint, 
                                 String pNomImpreso, 
                                 String pRUCImpreso,
                                 String pFechaBD, 
                                 String pNumComprobante, 
                                 ArrayList pDetalleComprobante, 
                                 String pValTotalNeto,
                                 String pValTotalAhorro, 
                                 String pValRedondeo, 
                                 String pModelo,
                                 boolean vIndImpresionAnulado,
                                 String tipoCompr,
                                 int    valor,
                                 //dubilluz 19.09.2015
                                 ArrayList<String> vPrintTratamiento,
                                 ArrayList vDatosTratamiento
                                 ) throws SQLException
    {
        
        // carga datos d tratamiento
        String pDniCli = "";
        String pNombreCli = "";
        String pNumCmp =  "";
        String pNombreMedico = "";
        boolean vIndTratamiento = false;
        if(vDatosTratamiento.size()>0){
            log.info(vDatosTratamiento+"");
            pDniCli    = FarmaUtility.getValueFieldArrayList(vDatosTratamiento, 0, 0).trim();
            pNombreCli = FarmaUtility.getValueFieldArrayList(vDatosTratamiento, 0, 1).trim();
            pNumCmp = FarmaUtility.getValueFieldArrayList(vDatosTratamiento, 0, 2).trim();
            pNombreMedico = FarmaUtility.getValueFieldArrayList(vDatosTratamiento,0, 3).trim();
            log.info("pNumCmp-- "+pNumCmp);
            int vCod = Integer.parseInt(pNumCmp.trim());
            if(vCod>=3)
                vIndTratamiento = true;
            else 
                vIndTratamiento = false;
        }
        // vDatosTratamiento

        
        switch(pModelo)
        {
            case "TMU950":  anchoLinea = ANCHO_LINEA__TM4950;
                            break; 
            default:        anchoLinea = ANCHO_LINEA_DEFAULT;
                            break;
        }
        
        Calendar fechaJava = Calendar.getInstance();        
        int dia=fechaJava.get(Calendar.DAY_OF_MONTH);
        int resto= dia % 2;
        /*
        if (resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
            vPrint.add((char)27+"4");  //rojo
        else
            vPrint.add((char)27+"5");  //negro
        */
        //DATOS DE CABECERA
        //vPrint.add(centrarLinea("BOTICAS "+VariablesPtoVenta.vNombreMarcaCia));
        
        if("06".equalsIgnoreCase(tipoCompr))
            vPrint.add(centrarLinea("FACTURA - "+ VariablesPtoVenta.vRazonSocialCia));
        else
            //vPrint.add(centrarLinea("TICKET-"+ VariablesPtoVenta.vRazonSocialCia));
            vPrint.add(centrarLinea("TICKET"));
        vPrint.add(centrarLinea(VariablesPtoVenta.vRazonSocialCia));
        vPrint.add(centrarLinea("RUC: "+FarmaVariables.vNuRucCia));
        //vPrint.add(centrarLinea("RUC: "+FarmaVariables.vDire));
        //log.info(VariablesPtoVenta.vDireccionCortaMatriz);
        log.info(""+anchoLinea);
        String strDir1 = VariablesPtoVenta.vDireccionCortaMatriz;//.substring(0, anchoLinea);
        //String strDir2 = VariablesPtoVenta.vDireccionCortaMatriz.substring(anchoLinea);
        vPrint.add(centrarLinea(strDir1.trim()));
        //vPrint.add(centrarLinea(strDir2.trim()));
        /*
        if(UtilityVentas.getIndImprimeCorrelativo())
        {   vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia+"          "+"CORR. "+VariablesCaja.vNumPedVta));     
        }
        else
        {   vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia));     
        }*/
        
        //vPrint.add("T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal);     
        //vPrint.add("Local - : "+FarmaVariables.vCodLocal);
        //FarmaUtility.
        //DATOS DEL TICKET
        switch(pModelo)
        {   case "TMU950":  datosTicketTMU950(pJDialog,             vPrint,
                                              pFechaBD,             pNumComprobante,
                                              pNomImpreso,          pRUCImpreso,
                                              pDetalleComprobante,  pValTotalNeto,
                                              pValTotalAhorro,      pValRedondeo,
                                              valor,
                                              //19.09.2015
                                              vIndTratamiento,pNumCmp,pNombreMedico,pDniCli,pNombreCli,
                                              vPrintTratamiento
                                              );
                            break; 
            default:        datosTicketDefault(pJDialog,            vPrint,
                                               pFechaBD,            pNumComprobante,
                                               pNomImpreso,         pRUCImpreso,
                                               pDetalleComprobante, pValTotalNeto,
                                               pValTotalAhorro,     pValRedondeo);
                            break;
        }
                      
        //DATOS PIE DE PAGINA
        int pos= VariablesCaja.vFormasPagoImpresion.indexOf("Tipo Cambio: ");
        String tcambio,fpago;
        String pCajero = "CJ: " + FarmaVariables.vIdUsu ;
        vPrint.add(pCajero);
        vPrint.add("  ");
        
        //vPrint.add(" ");
        if(pNomImpreso != null && pNomImpreso.trim().length()>0)
        {
            vPrint.add("RAZON SOCIAL:"+pNomImpreso.trim());     
        }
        if(pRUCImpreso != null && pRUCImpreso.trim().length()>0)
        {
            vPrint.add("RUC:"+pRUCImpreso.trim());     
        }
        
        if (pos != -1)
        {
            tcambio = VariablesCaja.vFormasPagoImpresion.substring(pos);
            fpago = VariablesCaja.vFormasPagoImpresion.substring(0, pos - 1);
            
            //CVILCA 28.10.2013
            vPrint.add(tcambio );                    
        }
        
        /*
        vPrint.add(FarmaPRNUtility.llenarBlancos(10) + VariablesVentas.vTituloDelivery );
        
        String msgCumImpresos = " ";
        if(VariablesCaja.vNumCuponesImpresos>0)
        {
            String msgNumCupon = "";
            if(VariablesCaja.vNumCuponesImpresos==1)
            {   msgNumCupon = "CUPON";
            }
            else
            {   msgNumCupon = "CUPONES";
            }
            msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+msgNumCupon;
        }
        
        if(VariablesCaja.vNumCuponesImpresos>0)
        {   vPrint.add(centrarLinea(msgCumImpresos,"*"));                   
        }*/
        vPrint.add(centrarLinea("No se aceptan devoluciones de dinero fuera." ));
        vPrint.add(centrarLinea("de las 24 horas siguientes a la compra."));
        vPrint.add(centrarLinea("Cualquier observacion es indispensable presentar comprobante."));   
        /*vPrint.add(centrarLinea("Cambio de mercadería únicamente dentro" ));
        vPrint.add(centrarLinea("de las 48 horas siguientes a la compra."));
        vPrint.add(centrarLinea("Indispensable presentar comprobante."));                
        *///vPrint.add(" ");                
        //vPrint.add(centrarLinea("Las devoluciones proceden dentro de ." ));
        //vPrint.add(centrarLinea("las 48 Horas de emitido el ticket." ));
        //vPrint.add(centrarLinea("Conservesu ticket para cualquier Reclamo." ));
        
        if(VariablesCaja.vImprimeFideicomizo)
        {
            String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
            String pCadena = "";
            if(lineas.length>0)
            {
                for(int i=0;i<lineas.length;i++)
                {   pCadena += lineas[i] + " ";
                }                        
                vPrint.add(""+pCadena.trim());                        
            }
            else
            {
                vPrint.add(""+VariablesCaja.vCadenaFideicomizo.trim());                        
            }
        }
        
        //LLEIVA 04-Oct-2013 Modificacion
        vPrint.add(centrarLinea("--","-"));
        //ERIOS 28.10.2013 Imprime pagina web
        /*if(FarmaConstants.INDICADOR_S.equalsIgnoreCase(VariablesPtoVenta.vIndImprWeb))
        {
            if(FarmaVariables.vCodCia.equals("001"))
                vPrint.add(centrarLinea("www.mifarma.com.pe","*"));
            else if(FarmaVariables.vCodCia.equals("002"))
                vPrint.add(centrarLinea("www.fasa.com.pe","*"));
            else if(FarmaVariables.vCodCia.equals("003"))
                vPrint.add(centrarLinea("www.btl.com.pe","*"));
        }*/
        //ERIOS 12.09.2013 Imprime central delivery        
        /*String mensaje=DBCaja.obtieneMensajeTicket();
        if(!mensaje.equalsIgnoreCase("N"))
        {
            vPrint.add(centrarLinea(mensaje));
        }*/
        
        if(vIndImpresionAnulado)
        {
            vPrint.add(centrarLinea("=","="));
            vPrint.add(centrarLinea("...COMPROBANTE ANULADO...","*"));
            vPrint.add(centrarLinea("=","="));
        }
        //ERIOS 2.4.0 Impresion de version
        //vPrint.add(VariablesPtoVenta.vVersion);
    }
    
    private String centrarLinea(String pCadena){
        return  centrarLinea(pCadena," ");
    }
    
    public static String centrarLinea(String pCadena,int anchoLinea){
        return  (new ImpresoraTicket(anchoLinea)).centrarLinea(pCadena," ");
    }
    
    public static String centrarLinea(String pCadena,String pCaracter,int anchoLinea){
        return (new ImpresoraTicket(anchoLinea)).centrarLinea(pCadena,pCaracter);
    }
    
    private String centrarLinea(String pCadena,String pCaracter){
        int pTamaño = pCadena.trim().length();
        int numeroPos = (int)Math.floor((anchoLinea - pTamaño)/2);
        String pCadenaNew = "";
        for(int i=0;i<numeroPos;i++){
            pCadenaNew += pCaracter;
        }
        pCadenaNew += pCadena.trim();
        pTamaño =  anchoLinea - pCadenaNew.length();
        
        for(int i=0;i<pTamaño;i++){
            pCadenaNew += pCaracter;
        }
        
        return  pCadenaNew;
    }

    private void datosTicketDefault(JDialog pJDialog,               ArrayList<String> vPrint, 
                                    String pFechaBD,                String pNumComprobante, 
                                    String pNomImpreso,             String pRUCImpreso,
                                    ArrayList pDetalleComprobante,  String pValTotalNeto, 
                                    String pValTotalAhorro,         String pValRedondeo) throws SQLException
    {
        String pTipoTICKETERA = "";
        if(pNumComprobante.substring(0,3).trim().equalsIgnoreCase("OO1"))
            pTipoTICKETERA = "20";
        else
            pTipoTICKETERA = "";
        
        vPrint.add("Correlativo:  "+VariablesCaja.vNumPedVta);
        
        vPrint.add(centrarLinea("Serie: "+
                                FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket+" - "+pTipoTICKETERA,20)+"    " + 
                                FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim()));  
        
        vPrint.add("Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16));
        
        vPrint.add("------------------------------------------");     
        vPrint.add("Cant.    Descripcion     Dscto   Importe");     
        vPrint.add("------------------------------------------");      
        
        int linea = 0;                    
        for (int i=0; i<pDetalleComprobante.size(); i++){
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        
            if(valor.equals("0.000")) valor = " ";
        
            double valor1 =  (UtilityModuloVenta.Redondear(FarmaUtility.getDecimalNumber(valor),2));
        
            if(valor1==0.0){
                valor = "";
            }else{
                valor = Double.toString(valor1);
            }
        
            vPrint.add("" +
                             UtilityCaja.pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + 
                             "       "+ 
                             //UNIDAD                            
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                             //LAB                             
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                             //AHORRO         
                             FarmaPRNUtility.alinearDerecha(valor,5) + "  " +
                             //PRECIO
                             FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                             );                                
                     
                                               
            linea += 1;
            String indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
            //verifica que solo se imprima un producto virtual en el comprobante
            if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
            else
              VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        }

        //  RECARGAS VIRTUALES
        if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
        {
        
            vPrint.add("");                  
            
            UtilityCaja.impresionInfoVirtualTicket(vPrint,
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                 VariablesCaja.vNumPedVta,//Se añadio el parametro
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
            linea = linea + 4;                    
        }
        
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
          linea++;
        }

        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            try
            {
                ArrayList aInfoPedConv = new ArrayList();
        
                DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

                vPrint.add("------------------------------------------");        
        
                for(int i=0; i<aInfoPedConv.size(); i++)
                {
                    ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        
                    String Ind_Comp=((String)registro.get(8)).trim();
                    if(Ind_Comp.equalsIgnoreCase("N")){
                        vPrint.add(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                                         FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20));
        
                        String vCredDisp = ((String)registro.get(7)).trim();
                        if(vCredDisp.equals(""))
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));                                                   
                        }else
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));
                            vPrint.add("Cred Disp: S/."+vCredDisp);                                    
                        } 
                    } 
                }
            }catch(SQLException sql)
            {
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al obtener informacion del Pedido Convenio ");
                log.info("Error al imprimir la BOLETA 2: ");
                log.error(null,sql);
            
                UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            }catch(Exception e){
            
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la BOLETA 3: "+e);
            
                UtilityCaja.enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
            }
        }
        
        double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);

        VariablesModuloVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.add(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10));                    
        }

        if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesModuloVentas.vTituloDelivery = "" ;
        } else
            VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
        if(auxTotalDcto > 0)
        {            
            String obtenerMensaje="";
            String indFidelizado="";
        
            if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
                indFidelizado="S";
            }else 
            {   indFidelizado="N"; }
        
            obtenerMensaje=UtilityCaja.obtenerMensaAhorro(pJDialog,indFidelizado);
            vPrint.add(""+obtenerMensaje+" "+"S/. "+pValTotalAhorro);                    
        }        
        
        vPrint.add("------------------------------------------");
        vPrint.add("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto);
        vPrint.add("==========================================");  
        //CVILCA 28.10.2013 - INICIO
        // COLOCANDO LAS FORMAS DE PAGO
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        ArrayList listaFP = new ArrayList();    
        Double vuelto = new Double(0);
        try{
            listaFP = facadeRecaudacion.obtenerDetallePedidoFomasPago(VariablesCaja.vNumPedVta);    
        }catch(Exception e){
            log.error("",e);
            }
        if(listaFP != null && listaFP.size() > 0){
            for(int i = 0; i < listaFP.size(); i++){
                
                String formaPago = ((ArrayList)listaFP.get(i)).get(0).toString();
                String importe = ((ArrayList)listaFP.get(i)).get(1).toString();
                log.info("forma de pago : " + formaPago);
                log.info("importe : " + importe);
                vPrint.add(FarmaPRNUtility.alinearIzquierda(formaPago,20) + FarmaPRNUtility.alinearDerecha(importe,15));
                vuelto = vuelto + FarmaUtility.getDecimalNumber(((ArrayList)listaFP.get(i)).get(2).toString().trim());
            }
        }
        log.info("vuelto : " + vuelto);
        vPrint.add("                   -----------------------");
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Total a pagar",20)+ "S/. "+FarmaPRNUtility.alinearDerecha(pValTotalNeto,11));
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Vuelto",20)+ "S/. " +FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(vuelto),11));
        //CVILCA 28.10.2013 - FIN
    }
    
    private void datosTicketTMU950( JDialog pJDialog,               ArrayList<String> vPrint, 
                                    String pFechaBD,                String pNumComprobante, 
                                    String pNomImpreso,             String pRUCImpreso,
                                    ArrayList pDetalleComprobante,  String pValTotalNeto,
                                    String pValTotalAhorro,         String pValRedondeo,
                                    int     valortick,
                                    boolean vIndTratamiento,
                                    String pCmp,String pNombreMedico,
                                    String DniCli,String pNombreCli,
                                    ArrayList vPrintTratamiento
                                    ) throws SQLException
    {
        

        //String pCmp,String pNombreMedico,
        //String DniCli,String pNombreCli
        String pTipoTICKETERA = "";
        if(pNumComprobante.substring(0,3).trim().equalsIgnoreCase("OO1"))
            pTipoTICKETERA = "20";
        else
            pTipoTICKETERA = "";

        vPrint.add("Correlativo:  "+VariablesCaja.vNumPedVta);

        vPrint.add("Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket+" - "+pTipoTICKETERA,20) + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),13));        
        vPrint.add("Fec:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro:"+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16));
        
        if(vIndTratamiento){
            vPrint.add("Recetado por :");    
            vPrint.add("CMP:"+pCmp+FarmaPRNUtility.llenarBlancos(1));
            vPrint.add("Nombre Medico:"+pNombreMedico);
            
            vPrintTratamiento.add("***************************************");
            vPrintTratamiento.add("       RESUMEN DE TRATAMIENTO          ");
            vPrintTratamiento.add("***************************************");
            vPrintTratamiento.add("Recetado por :");    
            vPrintTratamiento.add("CMP:"+pCmp+FarmaPRNUtility.llenarBlancos(1));
            vPrintTratamiento.add("Nombre Medico:"+pNombreMedico);
            vPrintTratamiento.add("Paciente:");
            vPrintTratamiento.add("Dni:"+DniCli+FarmaPRNUtility.llenarBlancos(1));
            vPrintTratamiento.add("Nombre Completo:"+pNombreCli);
            vPrintTratamiento.add("--------------------------------------");     
            vPrintTratamiento.add("Cant.     Descripcion                   ");     
            vPrintTratamiento.add("--------------------------------------");     
            for (int i=0; i<pDetalleComprobante.size(); i++){
                String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
                if(valor.equals("0.000")) valor = " ";
                double valor1 =  (UtilityModuloVenta.Redondear(FarmaUtility.getDecimalNumber(valor),2));
                if(valor1==0.0){
                    valor = "";
                }else{
                    valor = Double.toString(valor1);
                }
                vPrintTratamiento.add("" +
                                 centrarLinea(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9)+ " " +
                                 FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + "   "
                           );
                vPrintTratamiento.add("  " +
                                 //UNIDAD                            
                                 FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                                 //LAB                             
                                 FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                                 //AHORRO         
                                 FarmaPRNUtility.alinearDerecha(" ",5) + " " +
                                 //PRECIO
                                 FarmaPRNUtility.alinearDerecha(" ",10)
                                 );                                
            }
            vPrintTratamiento.add("--------------------------------------");     
            vPrintTratamiento.add(pFechaBD+"  -  "+pNumComprobante);
            vPrintTratamiento.add("Sede "+FarmaVariables.vCodLocal+"  -  "+VariablesCaja.vNumPedVta);
            vPrintTratamiento.add("***************************************");
        }
        
        if(DniCli.trim().length()>0){
            vPrint.add("Cliente:");
            vPrint.add("Dni:"+DniCli+FarmaPRNUtility.llenarBlancos(1));
            vPrint.add("Nombre Completo:"+pNombreCli);

        }
        
        vPrint.add("----------------------------------------");     
        vPrint.add("Cant.     Descripcion    Dscto   Importe");     
        vPrint.add("----------------------------------------");     
        
        int linea = 0;                    
        for (int i=0; i<pDetalleComprobante.size(); i++){
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        
            if(valor.equals("0.000")) valor = " ";
        
            double valor1 =  (UtilityModuloVenta.Redondear(FarmaUtility.getDecimalNumber(valor),2));
        
            if(valor1==0.0){
                valor = "";
            }else{
                valor = Double.toString(valor1);
            }
        
            vPrint.add("" +
                             centrarLinea(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9)+ " " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + "   "
                       );
            vPrint.add("  " +
                             //UNIDAD                            
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                             //LAB                             
                             //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                             //AHORRO         
                             //FarmaPRNUtility.alinearDerecha(valor,5) + " " +
                             
                             FarmaPRNUtility.alinearIzquierda("Lote:"+((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim(),14) + " "+
                             //PRECIO
                             FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                             );                                
                     
                                               
            linea += 1;
            String indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
            //verifica que solo se imprima un producto virtual en el comprobante
            if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
            else
              VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        }
        
        //  RECARGAS VIRTUALES
        if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
        {
        
            vPrint.add("");                  
            
            UtilityCaja.impresionInfoVirtualTicket(vPrint,
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                 VariablesCaja.vNumPedVta,//Se añadio el parametro
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
            linea = linea + 4;                    
        }
        
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
          linea++;
        }

        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            try
            {
                ArrayList aInfoPedConv = new ArrayList();
        
                DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

                vPrint.add("----------------------------------------");        
        
                for(int i=0; i<aInfoPedConv.size(); i++)
                {
                    ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        
                    String Ind_Comp=((String)registro.get(8)).trim();
                    if(Ind_Comp.equalsIgnoreCase("N")){
                        vPrint.add(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                                         FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20));
        
                        String vCredDisp = ((String)registro.get(7)).trim();
                        if(vCredDisp.equals(""))
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));                                                   
                        }else
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));
                            vPrint.add("Cred Disp: S/."+vCredDisp);                                    
                        } 
                    } 
                }
            }catch(SQLException sql)
            {
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al obtener informacion del Pedido Convenio ");
                log.info("Error al imprimir la BOLETA 2: ");
                log.error(null,sql);
            
                UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            }catch(Exception e){
            
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la BOLETA 3: "+e);
            
                UtilityCaja.enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
            }
        }
        
        double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);

        VariablesModuloVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.add(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10));                    
        }

        if(VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_MESON) || VariablesModuloVentas.vTipoPedido.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesModuloVentas.vTituloDelivery = "" ;
        } else
            VariablesModuloVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
        if(auxTotalDcto > 0)
        {            
            String obtenerMensaje="";
            String indFidelizado="";
        
            if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
                indFidelizado="S";
            }else 
            {   indFidelizado="N"; }
        
            obtenerMensaje=UtilityCaja.obtenerMensaAhorro(pJDialog,indFidelizado);
            vPrint.add(""+obtenerMensaje+" "+"S/. "+pValTotalAhorro);                    
        }        
        
        vPrint.add("----------------------------------------");
        vPrint.add("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto);
        vPrint.add("========================================");

        //CVILCA 28.10.2013 - INICIO
        // COLOCANDO LAS FORMAS DE PAGO
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        ArrayList listaFP = new ArrayList();    
        Double vuelto = new Double(0);
        try{
            listaFP = facadeRecaudacion.obtenerDetallePedidoFomasPago(VariablesCaja.vNumPedVta);    
        }catch(Exception e){
            log.error("",e);
            }
            
            //RHERRERA
            String asterix="";
            if (valortick >0)
                    asterix="(*)" ;
        
        //////////////////////////////////////////////////    
        if(listaFP != null && listaFP.size() > 0){
            for(int i = 0; i < listaFP.size(); i++){
                
                
                String formaPago = ((ArrayList)listaFP.get(i)).get(0).toString()+asterix;
                String importe = ((ArrayList)listaFP.get(i)).get(1).toString();
                log.info("forma de pago : " + formaPago);
                log.info("importe : " + importe);
                vPrint.add(FarmaPRNUtility.alinearIzquierda(formaPago,20) + FarmaPRNUtility.alinearDerecha(importe,15));
                vuelto = vuelto + FarmaUtility.getDecimalNumber(((ArrayList)listaFP.get(i)).get(2).toString().trim());
            }
        }
        log.info("vuelto : " + vuelto);
        vPrint.add("                   ---------------------");
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Total a pagar",20)+ "S/. " +FarmaPRNUtility.alinearDerecha(pValTotalNeto,11));
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Vuelto"+asterix,20)+ "S/. " +FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(vuelto),11));
        //CVILCA 28.10.2013 - FIN
    }
}
