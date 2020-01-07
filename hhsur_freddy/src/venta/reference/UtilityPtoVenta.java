package venta.reference;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import java.util.Map;


import venta.reference.BeanResultado;

import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;

import venta.inventario.DlgGuiasIngresosRecibidas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityPtoVenta {
    
    static final Logger log = LoggerFactory.getLogger(UtilityPtoVenta.class);

    UtilityCaja utilityCaja = new UtilityCaja();
    
    public static boolean permiteAccion(){
        boolean vRes = true;
        /*
         * //pendiente de parametrizar estos puntos
        if(FarmaVariables.vIdUsu.toUpperCase().trim().equalsIgnoreCase("AZAVALA")){
           JOptionPane.showMessageDialog(null,  
                                          "No tienes permiso para ver el detalle", 
                                          "Acceso Prohibido",
                                         JOptionPane.ERROR_MESSAGE);
            vRes = false;
        } */  
          return vRes;
    }
    
    public List<BeanResultado> retornaBean(List<Map<String, Object>> resultList) {
        List<BeanResultado> pListado = new ArrayList<>();
        for(Map<String, Object> mpReg:resultList){
            String strResultado = mpReg.get("1").toString();
            BeanResultado beanResultado = new BeanResultado();
            beanResultado.setStrResultado(strResultado);
            pListado.add(beanResultado);
        }
        return pListado;
    }
    
    public ArrayList<ArrayList<String>> parsearResultadoMatriz(List<BeanResultado> pListado) {
        return parsearResultadoMatriz(pListado, "�");
    }
    
    public ArrayList<ArrayList<String>> parsearResultadoFasaProd(List<BeanResultado> pListado) {
        return parsearResultadoMatriz(pListado, "?");
    }
    
    public ArrayList<ArrayList<String>> parsearResultadoMatriz(List<BeanResultado> listado, String pSeparador){
        
        List<BeanResultado> listResultado = listado;
        ArrayList<ArrayList<String>> arrayResultado = new ArrayList<ArrayList<String>>();
        ArrayList<String> tmpArray = null;
        StringTokenizer st = null;
        String tmpRes = "";
        
        for(BeanResultado beanResultado:listResultado){
            tmpRes = beanResultado.getStrResultado();
            tmpArray = new ArrayList<String>();
            if (tmpRes != null) {
                st = new StringTokenizer(tmpRes, "�");
                while (st.hasMoreTokens()){
                        tmpArray.add(st.nextToken());
                }
            }
            arrayResultado.add(tmpArray);
        }
        return arrayResultado;
    }
    
    public void parsearResultado(List<BeanResultado> pListado, FarmaTableModel pTableModel, boolean pWithCheck){
        String tmpRes = "";
        ArrayList<Object> tmpArray = null;
        StringTokenizer st = null;
        pTableModel.clearTable();
        for(BeanResultado beanResultado:pListado){
            tmpRes = beanResultado.getStrResultado();
            tmpArray = new ArrayList<Object>();
            if (tmpRes != null) {
                st = new StringTokenizer(tmpRes, "�");
                if (pWithCheck)
                    tmpArray.add(Boolean.valueOf(false));
                while (st.hasMoreTokens()){
                    tmpArray.add(st.nextToken());
                }
            }
            pTableModel.insertRow(tmpArray);
        }        
    }
    
    public ArrayList parsearResultadoColum(List listado){
        
        List<BeanResultado> listResultado = listado;
        ArrayList arrayResultado = new ArrayList();
        String tmpRes = "";
        
        for(BeanResultado beanResultado:listResultado){
            tmpRes = beanResultado.getStrResultado();
            arrayResultado.add(tmpRes);
        }
        return arrayResultado;
    }
    
    public void llenarTabla(FarmaTableModel tblDetalle, ArrayList tmpArrayDetalle){
        if(tmpArrayDetalle.size() < 0){return;}
        for(int i = 0; i < tmpArrayDetalle.size() ; i++){
            tblDetalle.insertRow((ArrayList)tmpArrayDetalle.get(i));
        }
    }
    
    public boolean validarCampTxtField(JDialog pDialog, JTextField txtText){
        //ERIOS 2.2.8 Validacion de monto
        if (!utilityCaja.existeCajaUsuarioImpresora(pDialog, null)){
            return false;
        }
        if(txtText != null){
            if(txtText.getText().trim().equals("")){
                FarmaUtility.showMessage(pDialog, "Ingrese la cantidad correcta digitos." , txtText);
                return false;
            }        
            double dCuantoPaga = FarmaUtility.getDecimalNumber(txtText.getText());
            if(dCuantoPaga<=0){
                FarmaUtility.showMessage(pDialog, "Ingrese un monto v�lido." , txtText);
                return false;
            }
        }
        return true;
    }
    
    public boolean validarCampTxtFieldTrj(JDialog pDialog, JTextField txtText){
        if(txtText != null){
            if(txtText.getText().trim().length() != 16){
                FarmaUtility.showMessage(pDialog, "Ingrese la cantidad correcta digitos de su tarjeta." , txtText);
                return false;
            }        
        }
        return true;    
    }
    
    public String rutaImpresoraTicket(){
        String tmpRuta = "";
        try{
            tmpRuta = DBCaja.obtieneRutaImpresoraVenta(VariablesCaja.vSecImprLocalTicket);
        }catch(SQLException sqlE){
            log.error("RUTA ERROR TICKET "+sqlE.getMessage());
        }
        return tmpRuta;
    }

    /**
     * Retorna la ruta de la carpeta de comprobantes
     * @author ERIOS
     * @since 25.06.2013
     * @return
     * @throws SQLException
     */
    public static String obtieneDirectorioComprobantes() throws SQLException {
        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();
        Path dir = Paths.get(carpetaRaiz, carpetaComprobantes);
        return dir.toString()+File.separator;
    }    
    
    /**
     * Retorna la ruta de la carpeta de imagenes
     * @author ERIOS
     * @since 25.06.2013
     * @return
     * @throws SQLException
     */
    public static String obtieneDirectorioImagenes() throws SQLException {
        String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
        String carpetaComprobantes = DBPtoVenta.getDirectorioImagenes();
        Path dir = Paths.get(carpetaRaiz, carpetaComprobantes);
        return dir.toString()+File.separator;
    }

    /**
     * Limpia la caja de texto.
     * Metodo implementado para los lectores de codigos de barra NCR.
     * @author ERIOS
     * @since 03.07.2013
     * @param txtProducto
     */
    public static void limpiaCadenaAlfanumerica(JTextField txtProducto) {
        String prodTemp = txtProducto.getText();
        //prodTemp = getCadenaAlfanumerica(prodTemp);
        prodTemp = getCodBarraSinCarControl(prodTemp);
        txtProducto.setText(prodTemp);
    }

    /**
     * Elimina caracteres especiales de la cadena.
     * @author LLEIVA
     * @since 03.07.2013
     * @param prodTemp
     * @return
     */
    public static String getCadenaAlfanumerica(String codProd)
    {
        String codProdTemp = "";
        if (codProd.length() > 0)
        {
            Pattern patron = Pattern.compile("[^0-9 a-zA-Z.%&_^-]"); //|^-|^.|^+|^\\/|^&|^\\#|^\\%");
            Matcher encaja = patron.matcher(codProd);
            codProdTemp = encaja.replaceAll(""); 
        }
        
        return codProdTemp;
    }
    
    /**
     * Elimina caracteres especiales de la cadena.
     * @author LLEIVA
     * @since 03.07.2013
     * @param prodTemp
     * @return
     */
    public static boolean validarNumero(String cadena)
    {
        boolean flag=false;
        if (cadena.length() > 0)
        {
            Pattern patron = Pattern.compile("^\\d+$"); //|^-|^.|^+|^\\/|^&|^\\#|^\\%");
            Matcher encaja = patron.matcher(cadena);
            flag = encaja.matches();
        }
        
        return flag;
    } 
    
    /**
     * Devuelve el codigo de barras sin el caracter de identificacion al inicio de la cadena
     * @author LLEIVA
     * @since 22.Nov.2013
     * @param cadena
     * @return
     */
    public static String getCodBarraSinCarControl(String cadena)
    {   String codProdTemp = "";//cadena;
        try
        {   if(cadena != null && cadena.length()>2)
            {   
                //se obtienen los dos primero caracteres para verificar el caracter de control
                int cont1 = cadena.substring(0, 1).codePointAt(0);
                int cont2 = cadena.substring(1, 2).codePointAt(0);

                //verifica si el primer caracter es F, el segundo es F y tama�o 9(8+1) con solo numeros, entonces es un EAN-8
                if((cont1 == 70 && cont2 == 70) && (cadena.length()>=9))
                {   if(validarNumero(cadena.substring(2, 10)))
                        codProdTemp = cadena.substring(2, 10);
                    else
                        codProdTemp = cadena;
                }
                //verifica si el primer caracter es ?
                else if (cont1 == 9824)
                {   if(validarNumero(cadena.substring(1, cadena.length())))
                        codProdTemp = cadena.substring(1, cadena.length());
                    else
                        codProdTemp = cadena;
                }
                //verifica si el primer caracter es F y tama�o 14(13+1), entonces es un EAN-13
                else if((cont1 == 70) && (cadena.length()>=14))
                {   if(validarNumero(cadena.substring(1, 14)))
                        codProdTemp = cadena.substring(1, 14);
                    else
                        codProdTemp = cadena;
                }
                //verifica si el primer caracter es A y tama�o 13(12+1), entonces es un UPC-A
                else if((cont1 == 65) && (cadena.length()>=13))
                {   if(validarNumero(cadena.substring(1, 13)))
                        codProdTemp = cadena.substring(1, 13);
                    else
                        codProdTemp = cadena;
                }
                //verifica si el primer caracter es E y tama�o 9(8+1), entonces es un UPC-E
                else if((cont1 == 69) && (cadena.length()>=9))
                {   if(validarNumero(cadena.substring(1, 9)))
                        codProdTemp = cadena.substring(1, 9);
                    else
                        codProdTemp = cadena;
                }
                //verifica si el primer caracter es f, a, b, r, N entonces es un Code 128, Code 39,
                //Interleaved 2 de 5, Databar � Codabar, que tienen longitud variable
                /*else if(cont1 == 102 ||
                        cont1 == 97  ||
                        cont1 == 98  ||
                        cont1 == 114 ||
                        cont1 == 78)
                {   codProdTemp = cadena.substring(1, cadena.length());
                }*/
                else
                {   codProdTemp = cadena;
                }
            }
        }
        catch(Exception e)
        {   log.error("", e);
            codProdTemp = "";
        }
        finally
        {   return codProdTemp;
        }
    }

    /**
     * Verifica tecla pulsada F1 - NCR
     * @author ERIOS
     * @since 03.07.2013
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F1(KeyEvent keyEvent) {
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F1){
            return true;
        }
        return false;
    } 
    
    /**
     * Verifica tecla pulsada F2 - NCR
     * @author ERIOS
     * @since 03.07.2013
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F2(KeyEvent keyEvent) {
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F2){
            return true;
        }
        return false;
    }
    
    /**
     * Verifica tecla pulsada F10 - NCR
     * @author ERIOS
     * @since 03.07.2013
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F10(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F10){
            return true;
        }
        return false;
    }
    
    
    /**
     * Verifica tecla pulsada F11 - NCR
     * @author ERIOS
     * @since 03.07.2013
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F11(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F11){
            return true;
        }
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_F1)
                return true;
        }
        /*if ((m & (InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK)) != 0) {
          log.debug("ctrl ");
        }
        if ((m & (InputEvent.META_DOWN_MASK | InputEvent.META_MASK)) != 0) {
          log.debug("meta ");
        }
        if ((m & (InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK)) != 0) {
          log.debug("alt ");
        }
        if ((m & (InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON1_MASK)) != 0) {
          log.debug("button1 ");
        }
        if ((m & (InputEvent.BUTTON2_DOWN_MASK | InputEvent.BUTTON2_MASK)) != 0) {
          log.debug("button2 ");
        }
        if ((m & (InputEvent.BUTTON3_DOWN_MASK | InputEvent.BUTTON3_MASK)) != 0) {
          log.debug("button3 ");
        }*/
        return false;
    }

    /**
     * Verifica tecla pulsada F12 - NCR
     * @author ERIOS
     * @since 03.07.2013
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F12(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F12){
            return true;
        }
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_F2)
                return true;
        }
        return false;
    }

    /**
     * Verifica conexion con servidores en Matriz
     * @author ERIOS
     * @since 21.11.2013
     * @param pConexion
     * @return
     * @throws Exception
     */
    @Deprecated
    public static boolean verificaConexionMatriz(int pConexion) throws Exception {
        boolean bRpt = true;
        String vConexion = FarmaUtility.getIndLineaOnLine(pConexion, FarmaConstants.INDICADOR_S);
        if(vConexion.equals(FarmaConstants.INDICADOR_N)){
            bRpt = false;
            //ERIOS 2.2.8 Envia correo
            FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal,
                                          VariablesPtoVenta.vDestEmailErrorCobro,
                                          "Error de Conexion Central",
                                          "Error de Comunicacion BBDD",
                                          "�No hay conexi�n con Matriz" + "<br>"+ 
                                          "Int�ntelo nuevamente." + "<br>"+ 
                                          "Si persiste el error, llame a Mesa de Ayuda." + "<br>"+ 
                                          "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                          "Error: " + "Conexion :"+pConexion ,
                                          "");
            throw new Exception("�No hay conexi�n con Matriz!\n" +
                "Int�ntelo nuevamente.\n" +
                "Si persiste el error, llame a Mesa de Ayuda. ");                
        }
        return bRpt;
    }
    
    /**
     * Obtiene la fecha actual de la Base de Datos en formato ddMMYYYY
     * @author LLEIVA
     * @since 07.Feb.2014
     * @param pConexion
     * @return
     * @throws Exception
     */
    public static String fechaActualDDMMYYYY() throws Exception
    {
        String tmpFecha = "";
        try
        {
            tmpFecha = DBCaja.fechaActualDDMMYYYY();
        }
        catch(SQLException sqlE)
        {
            log.error("ERROR FECHA ACTUAL: "+sqlE.getMessage());
        }
        return tmpFecha;
    }

    /**
     * Desdoble de mensaje de error de base de datos
     * @author AAMPUERO
     * @since 08.04.2014
     * @param mensageErrorBd
     */
    public static void mensajeErrorBd(JDialog pDialog, String msgOracle, Object pObject) {

        /**
              DESDOBLE DE LINEAS X CODIGO DE MENSAJES BD-ORACLE
              AAMPUERO - 04-04-2014
             */

        String pmensaje = "";
        String[] splits = msgOracle.split("ORA-");

        if (msgOracle.indexOf("ORA-") > 0)

            for (int i = 0; i < splits.length; i++) {
                // titulo del mensaje
                if (i == 0)
                    pmensaje = pmensaje.trim() + splits[i];
                else
                    pmensaje = pmensaje.trim() + "\n" +
                            "ORA-" + splits[i];
            }
        else
            pmensaje = msgOracle;

        FarmaUtility.showMessage(pDialog, pmensaje, pObject);
    }

    public static Object[] obtenerDefaultValuesTabla(int longitud){
        Object[] defaultValues = new Object[longitud];
        for(int i=0; i<longitud;i++){
            defaultValues[i] = " ";
        }
        return defaultValues;
    }
    
    public static boolean verificaVK_F3(KeyEvent keyEvent) {
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F3) {
            return true;
        }
        return false;
    }
    
    /**
     * Verifica tecla pulsada F4
     * @author JMONZALVE
     * @since 19.01.2018
     * @param keyEvent
     * @return
     */
    public static boolean verificaVK_F4(KeyEvent keyEvent) {
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_F4) {
            return true;
        }
        return false;
    }

    
}
