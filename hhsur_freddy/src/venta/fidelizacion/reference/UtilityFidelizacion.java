package venta.fidelizacion.reference;

import java.awt.Frame;

import java.sql.SQLException;
import javax.swing.JDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityLectorTarjeta;
import venta.caja.reference.VariablesCaja;
import venta.fidelizacion.DlgFidelizacionClientes;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.DlgMedicoCampana;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityFidelizacion {

    private static final Logger log = LoggerFactory.getLogger(UtilityFidelizacion.class);
    
    public UtilityFidelizacion() {
    }
    
    /**
     * Valida la tarjeta en el momento mismo de la lectura de esta
     * @Author DVELIZ
     * @Since 30.09.08
     * @param vCodTarjeta
     * @param pParent
     */
    public static void validaLecturaTarjeta(String vCodTarjeta,
                                            Frame pParent){
        String indExisteLocal   = "";
        
        try{
            //Verifica si existe cliente asociado a la tarjeta en el local
            //Si es 0: no existe
            //Si es 1: existe pero le faltan datos
            //Si es 2: existe con datos completos
            indExisteLocal = DBFidelizacion.validaClienteLocal(vCodTarjeta);
            
           
           /* validarConexionMatriz();
            
            if (indExisteLocal.equalsIgnoreCase("1") || 
                indExisteLocal.equalsIgnoreCase("0")) {
                if (VariablesFidelizacion.vIndConexion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    actualizaDatosCliente(vCodTarjeta);
                    // vuelve a validar la tarjeta 
                    log.debug("volver a validar los datos despues de actualizar");
                    indExisteLocal = DBFidelizacion.validaClienteLocal(vCodTarjeta);
                }
            }*/
            VariablesFidelizacion.vIndConexion="N";
                
            if(!indExisteLocal.equals("0")){
                cargoProcesoSegunIndExiste(indExisteLocal, vCodTarjeta,pParent);
                VariablesFidelizacion.vIndAgregoDNI = indExisteLocal.trim();
                return;
            }
            
            /*No se realizar� la accion en Matriz
             * dubilluz 26/07/2009
            if(VariablesFidelizacion.vIndConexion.equals("S")){
                log.debug("Voy a validar en matriz");
                validaTarjetaMatriz(vCodTarjeta,FarmaConstants.INDICADOR_N,
                                VariablesFidelizacion.vIndConexion,pParent);
            }else{*/
                muestraInterfazDatosCliente(vCodTarjeta.trim(),pParent);
            //}
            
            VariablesFidelizacion.vIndAgregoDNI = indExisteLocal.trim();  
            
            
        }catch(SQLException e){
            log.error("",e);
        }
        finally{
            if(VariablesFidelizacion.vIndConexion.trim().length()>0)
            FarmaConnectionRemoto.closeConnection();
            VariablesFidelizacion.vIndConexion = "";
        }
    }
    
    /**
     * Metodo de validacion general de la tarjeta de fidelizacion
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @return
     */
    public static boolean EsTarjetaFidelizacion(String pCadena){
        boolean retorno = false;
        int pTamano = pCadena.length();        
        if(pTamano > 1)
        {
           //se valida el codigo de barra a nivel de local 
            if(isNumerico(pCadena) && 
               pTamano == 13 && 
               validaCodBarraLocal(pCadena) &&
               validaTarjetaLocal(pCadena))
            {
              retorno = true;
            }
        }    
        
        return retorno;
    }
    
    
    /**
     * Valida que si el numero leido es numerico
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pcadena
     * @return
     */
    public static boolean isNumerico(String pcadena) { 
      char vCaracter ;
      if(pcadena.length()==0)
       return false;
      for(int i=0;i<pcadena.length();i++)
      {
        vCaracter = pcadena.charAt(i);
        if(Character.isLetter(vCaracter))
           return false;
      }
      return true;
    }
    
    public static boolean isNumericoBest(String pcadena){
        int numero = 0;
        boolean retorno = true;
        if(pcadena.length()==0)
         return false;
        try{
            numero = Integer.parseInt(pcadena);
        }catch(Exception e){
            retorno = false;
        }
        
        return retorno;
    }
    
    /**
     * Valida si el numero leido es codigo de barra
     * @Author DVELIZ
     * @Since 30.09.08
     * @param cadena
     * @return
     */
    private static boolean validaCodBarraLocal(String cadena){
    
        boolean retorno=true;
        String valida="";
        
        try{
          valida= DBFidelizacion.verificaCodBarraLocal(cadena);
          if(valida.equalsIgnoreCase("S")){
          retorno=false;
          log.debug("El codigo de barra "+cadena+" existe en local");
          }else{
          log.debug("El codigo de barra "+cadena+" No existe en local");
          }
        }catch(SQLException e){
           log.error("",e);
        }
        return retorno;
    }
    
    /**
     * Valida tarjeta en local
     * @Author DVELIZ
     * @Since 30.09.08
     * @param cadena
     * @return
     */
    private static boolean validaTarjetaLocal(String cadena){
        boolean retorno=false;
        String valida="";
        
        try{
            valida = DBFidelizacion.validaTarjetaLocal(cadena);
            if(valida.equals("1")){
                retorno=true;
                log.debug("La tarjeta "+cadena+" existe en local");
            }else{
                log.debug("La tarjeta "+cadena+" No existe en local");
            }
        }catch(SQLException e){
            log.error("",e);
        }
        
        return retorno;
    }
    
    /**
     * Valida la tarjeta en matriz
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pTarjeta
     * @param pIndCloseConecction
     * @param pIndLinea
     * @param pParent
     * @return
     */
    private static boolean validaTarjetaMatriz(String pTarjeta,
                                     String pIndCloseConecction,
                                     String  pIndLinea,Frame pParent){
        String indExisteMatriz  = ""; 
        boolean retorno = false;
        
        try{
            indExisteMatriz = DBFidelizacion.validaTarjetaMatriz(pTarjeta,
                                                        pIndCloseConecction);
            
            if(indExisteMatriz.equals("0")){
                log.debug("TARJETA LIBRE AGREGAR CLIENTE");
                muestraInterfazDatosCliente(pTarjeta.trim(),pParent);
                DBFidelizacion.obtieneInfoCliente(
                                pTarjeta, VariablesFidelizacion.vDataCliente);
                retorno = true;
            }else{
                cargoProcesoSegunIndExisteMatriz(indExisteMatriz, pTarjeta,pParent);
                retorno = true;
            }
        }
        catch(SQLException e)
        {
            //cierra la conexion
            FarmaConnectionRemoto.closeConnection();
            retorno =  false;
            log.error("",e);
            log.error(null,e);
            log.info("Error al validar tarjeta en Matriz");
        }
        return retorno;           
    }
    
    /**
     * Valida la carga de interfaces segun el indiccador de existencia
     * @Author DVELIZ
     * @Since 30.09.08
     * @param vIndExiste
     * @param pTarjeta
     * @param pParent
     */
    public static void cargoProcesoSegunIndExiste(String vIndExiste,
                                                  String pTarjeta,
                                                  Frame pParent){
    	log.debug("indicador de existe cliente :"+vIndExiste);
        if(vIndExiste.equals("2")){
            //muestra la informacion en DlgListaProductos
            log.debug("PASO DE FRENTE A MOSTRAR DATOS EN LISTADO");
            VariablesFidelizacion.vDataCliente = new ArrayList();
            try {
                DBFidelizacion.obtieneInfoCliente(
                            pTarjeta, VariablesFidelizacion.vDataCliente);
                VariablesFidelizacion.vNumTarjeta = pTarjeta.trim();
                log.debug("",VariablesFidelizacion.vDataCliente);
            } catch (SQLException e) {
               log.error("",e);
            }
            FarmaVariables.vAceptar = true;
        } else if(vIndExiste.equals("1")){
            //muestra la interfaz DlgFidelizacionClientes
            log.debug("FALTA DATOS, INGRESALOS");
            muestraInterfazDatosCliente(pTarjeta.trim(),pParent);
    
           
        }
    }

    public static void cargoProcesoSegunIndExisteMatriz(String vIndExiste,
                                                  String pTarjeta,
                                                  Frame pParent){
        if(vIndExiste.equals("2")){
            //muestra la informacion en DlgListaProductos
            log.debug("PASO DE FRENTE A MOSTRAR DATOS EN LISTADO");
            VariablesFidelizacion.vDataCliente = new ArrayList();
            try {
                DBFidelizacion.obtieneInfoClienteMatriz(pTarjeta, 
                    VariablesFidelizacion.vDataCliente,
                    VariablesFidelizacion.vIndConexion);
                    
                VariablesFidelizacion.vNumTarjeta = pTarjeta.trim();
                log.debug("cargando datos de matriz a local"+VariablesFidelizacion.vDataCliente);
                
                ArrayList array = (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
                VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
                VariablesFidelizacion.vIndEstado = "A";
                DBFidelizacion.insertarClienteFidelizacion();
            } catch (SQLException e) {
               log.error("",e);
            }
        } else if(vIndExiste.equals("1")){
            //muestra la interfaz DlgFidelizacionClientes
            log.debug("FALTA DATOS, INGRESALOS");
            muestraInterfazDatosCliente(pTarjeta.trim(),pParent);

        }
    }
    /**
     * Metodo que muestra la interfaz de ingreso de datos de cliente
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void muestraInterfazDatosCliente(String pCadena,Frame pParent) {

        try {
            DlgFidelizacionClientes dlgFormulario = 
                new DlgFidelizacionClientes(pParent,"",true,pCadena);
            dlgFormulario.setVisible(true);
            //VariablesFidelizacion.vNumTarjeta = cadena.trim();
            if (FarmaVariables.vAceptar) {
                VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                try {
                    DBFidelizacion.obtieneInfoCliente(
                                pCadena, VariablesFidelizacion.vDataCliente);
                    VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                } catch (SQLException e) {
                    log.error("",e);
                }
                
                VariablesFidelizacion.vNumTarjeta = pCadena.trim();
                VariablesFidelizacion.vDniCliente = 
                        FarmaUtility.getValueFieldArrayList(VariablesFidelizacion.vDataCliente, 
                                                            0, 0);
            }
            log.info("VariablesFidelizacion.vDataCliente >>>:"+VariablesFidelizacion.vDataCliente);
            
        } catch (Exception e) {
            log.error("",e);
        }

    }
    
    
    /**
     * Metodo que sirve para validar que existe conexion en matriz
     * @Author DVELIZ
     * @Since 30.09.08
     * @param pCadena
     * @param pParent
     */
    public static void validarConexionMatriz(){
        VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_MATRIZ,
                                     FarmaConstants.INDICADOR_N);
                                        
        if(VariablesFidelizacion.vIndConexion.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           log.debug("No existe linea se cerrara la conexion ...");
           FarmaConnectionRemoto.closeConnection();
           //VariablesFidelizacion.vIndConexion = "";
           
          // muestraInterfazDatosCliente(pCadena.trim(),pParent);
        }
    }

    /**     
     * Procedimiento encargado de procesar las campa�as que tiene asociado
     * @author dubilluz
     * @since  26.09.2008
     * @param pLista
     * @param pNumTarjeta
     * @deprecated se reemplazo por el metodo obtenerCampaniasxFidelizacion
     */
    /*
    public static ArrayList getCampxFid(String pNumTarjeta) {
        ArrayList listaCampaTarj = new ArrayList();

        try {

            DBFidelizacion.obtenerCampaniasXFidelizacion(pNumTarjeta);

        } catch (SQLException e) {
            log.error("",e);
            return null;
        }

        return listaCampaTarj;
    }  
    */
    
    /**     
     * Procedimiento encargado de obtener el listado de campa�as automaticas
     * @author JCALLO
     * @since  03.03.2009
     * @param  List listaCampaTarj
     * @param pNumTarjeta
     */
    public static List obtenerCampaniasxFidelizacion(String pNumTarjeta) {
        List listaCampaTarj = new ArrayList();
        try {
            listaCampaTarj = DBFidelizacion.obtenerCampaniasXFidelizacion(pNumTarjeta);
        } catch (SQLException e) {
        	listaCampaTarj = new ArrayList();
            log.error("",e);           
        }
        return listaCampaTarj;
    }
    
    public static boolean isNumero(String cadena){
        double num;
        boolean res;
        try {
            num = Double.parseDouble(cadena.trim());
            res = true;
        } catch (NumberFormatException e) {
            res = false;
        }
        
        return res;
    }
    
    /**
     * METODO ENCARGADO DE AGREGAR LAS CAMPA�AS AUTOMATICAS A LAS CAMPA�AS CUPON NORMALES
     * en resumen agregar a la lista VariablesVentas.vArrayList_Cupones las campa�as automaticas
     * por fidelizacion
     * @param cadena
     */
    public static void operaCampa�asFidelizacion(String nroTarjetaCliente) {        
        //ERIOS 21.01.2014 Se muestra mensaje de error
    try{
        VariablesFidelizacion.vListCampa�asFidelizacion = new ArrayList();
        //Obteniendo la lista de campa�as de fidelizacion
        //el resultado es un LIST con una coleccion de HASHMAP
        VariablesFidelizacion.vListCampa�asFidelizacion = obtenerCampaniasxFidelizacion(nroTarjetaCliente);
        
        //BORRA las AUTOMATICAS que ya EXISTAN
        ArrayList cuponesValidos = new ArrayList();
        Map mapAux = new HashMap();//mapa de la campania del listado de cupones
        String campAux= "";
        String codCampCupon = "",nroCupon = "";
        log.debug("Limpiando VAriables");
        //ESTO ES PARA ELIMINAR LAS CAMPA�AS AUTOMATIACAS YA CARGADAS
        //PORQUE AHORA SE PUEDE HACER F12 VARIAS VECES.
        for(int i=0;i < VariablesModuloVentas.vArrayList_Cupones.size();i++){
            log.debug("i:"+i);
            mapAux = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(i);
            campAux  = ((String)mapAux.get("COD_CAMP_CUPON")).trim()+"";
            if(isNumero(campAux)){
                log.debug("campAux:"+campAux);
                //cuponesValidos.add(mapAux);
                log.debug("ANTES");
                nroCupon = ((String)mapAux.get("COD_CUPON")).trim()+"";
                log.debug("despues");
                codCampCupon = nroCupon.substring(0, 5);
                try{
                    mapAux = new HashMap();
                    mapAux = DBModuloVenta.getDatosCupon(codCampCupon, nroCupon);
                    mapAux.put("COD_CUPON", nroCupon);
                } catch (SQLException e) {
                    //cuponesValidos.add(mapAux);
                }
                log.debug("agrega "+i);
                cuponesValidos.add(mapAux);
            }
            else
               log.debug("no es cupon");
        }
        
        log.debug("XXX cuponesValidos:"+cuponesValidos);

            VariablesModuloVentas.vArrayList_Cupones = new ArrayList();
            VariablesModuloVentas.vArrayList_Cupones = (ArrayList)cuponesValidos.clone(); 
        
        log.debug("Cuantas Campa�as Fideizadas Auto:"+VariablesFidelizacion.vListCampa�asFidelizacion.size());
        log.debug("VariablesVentas.vArrayList_Cupones:" + VariablesModuloVentas.vArrayList_Cupones);
        log.debug("Cuantas Campa�as Ya Cargadas:" + VariablesModuloVentas.vArrayList_Cupones.size());
        log.debug("agregando las campa�as automaticas al listado de cupones");
        if (VariablesFidelizacion.vListCampa�asFidelizacion.size() > 0) {
        	
        	Map mapCampFid = new HashMap();//mapa de la campania de fidelizacion
        	Map mapCampCup = new HashMap();//mapa de la campania del listado de cupones
        	
        	String codCampFid = "";//codigo de campania de fidelizacion
        	String CodCampCup = "";//codigo de campania de cupon de la lista general
        	
            boolean existe = false;
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion.size():"+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            for (int i = 0; i < VariablesFidelizacion.vListCampa�asFidelizacion.size();i++) {
            	
            	mapCampFid = (HashMap)VariablesFidelizacion.vListCampa�asFidelizacion.get(i);
            	codCampFid  = ((String)mapCampFid.get("COD_CAMP_CUPON")).trim();
            	//log.debug("mapCampFid:"+mapCampFid);
                for (int j = 0;j < VariablesModuloVentas.vArrayList_Cupones.size(); j++) {
                	mapCampCup = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(j);
                	CodCampCup  = ((String)mapCampCup.get("COD_CAMP_CUPON")).trim();
                    if (codCampFid.equalsIgnoreCase(CodCampCup)) {//ver si ya existe
                    	existe = true;
                        break;
                    }
                }
                
                if (!existe) {      
                	//agregando campania fidelizacion a la lista de cupones
                        VariablesModuloVentas.vArrayList_Cupones.add(mapCampFid);
                    //log.debug("despues de agregar VariablesVentas.vArrayList_Cupones:"+VariablesVentas.vArrayList_Cupones);
                }
            }
            log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesModuloVentas.vArrayList_Cupones.size());
        }
    }catch(Exception e){
        log.error("",e);
        //Envia correo
        FarmaUtility.enviaCorreoPorBD(FarmaVariables.vCodGrupoCia,
                                      FarmaVariables.vCodLocal,
                                      VariablesPtoVenta.vDestEmailErrorCobro,
                                      "Error de Fidelizacion",
                                      "Error de Fidelizacion",
                                      "Error al operar campa�as de fidelizacion" + "<br>"+
                                      "IP PC: " + FarmaVariables.vIpPc + "<br>"+ 
                                      "Error: " + e ,
                                      "");
        FarmaUtility.showMessage((new JDialog()), "Ha ocurrido un error al recuperar las Campa�as de Fidelizaci�n.\n" +
            "Comun�quese con Mesa de Ayuda.\n"+e.toString(), null);
    }
    }
    
    private static void actualizaDatosCliente(String pCodTarjeta){

        ArrayList array = new ArrayList();
        
        try {
            DBFidelizacion.obtieneInfoClienteMatriz(pCodTarjeta, array, "N");
            log.debug("pCodTarjeta "+ array);
            VariablesFidelizacion.vDniCliente = FarmaUtility.getValueFieldArrayList(array,0,0).trim();
            VariablesFidelizacion.vNumTarjeta =  pCodTarjeta.trim();   
            VariablesFidelizacion.vApePatCliente = FarmaUtility.getValueFieldArrayList(array,0,1).trim();
            VariablesFidelizacion.vApeMatCliente =FarmaUtility.getValueFieldArrayList(array,0,2).trim();
            VariablesFidelizacion.vNomCliente =FarmaUtility.getValueFieldArrayList(array,0,3).trim();
            VariablesFidelizacion.vFecNacimiento =FarmaUtility.getValueFieldArrayList(array,0,4).trim();
            VariablesFidelizacion.vSexo = FarmaUtility.getValueFieldArrayList(array,0,5).trim();
            VariablesFidelizacion.vDireccion = FarmaUtility.getValueFieldArrayList(array,0,6).trim();
            VariablesFidelizacion.vTelefono =FarmaUtility.getValueFieldArrayList(array,0,7).trim();
            /*
            A.DNI_CLI           || '�' ||
                      nvl(A.APE_PAT_CLI ,'N')      || '�' ||
                      nvl(A.APE_MAT_CLI ,'N')      || '�' ||
                      nvl(A.NOM_CLI     ,'N')      || '�' ||
                     nvl(''|| A.FEC_NAC_CLI  ,'N')     || '�' ||
                     nvl( A.SEXO_CLI  ,'N')        || '�' ||
                     nvl( A.DIR_CLI  ,'N')         || '�' ||
                     nvl(''|| A.FONO_CLI,'N')
             * */
            
           /* if(VariablesFidelizacion.vApePatCliente.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vApePatCliente = null; 
            if(VariablesFidelizacion.vApeMatCliente.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vApeMatCliente = null; 
            if(VariablesFidelizacion.vNomCliente.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vNomCliente = null; 
            if(VariablesFidelizacion.vFecNacimiento.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vFecNacimiento = null; 
            if(VariablesFidelizacion.vSexo.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vSexo = null; 
            if(VariablesFidelizacion.vDireccion.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vDireccion = null; 
            if(VariablesFidelizacion.vTelefono.trim().equalsIgnoreCase("N"))
                VariablesFidelizacion.vTelefono = null; 
            */
            VariablesFidelizacion.vIndEstado  = "A";
            //Este metodo de se encargara de insertar y/o actualizar
            DBFidelizacion.insertarClienteFidelizacion();
            setVariables();
        } catch (SQLException e) {
            log.error("",e);
        }
    }
    
    
    public static void setVariables(){
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vNumTarjeta =  "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vSexo = "";
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vTelefono = "";
        VariablesFidelizacion.vEmail = "";
        VariablesFidelizacion.vIndExisteCliente = false;
    }
    
    
    /**
     * metodo encargado de setear los datos 
     * del arreglo en las variables del cliente
     * @autor jcallo
     * @since 02.10.2008
     * */
    public static void setVariablesDatos(ArrayList lDatosCliente){
        int tam = lDatosCliente.size();
        int ind = 0;        
        if(ind<tam){
            VariablesFidelizacion.vDniCliente = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vDniCliente = "";
            ind++;
        }
        /*if(ind<tam){
            VariablesFidelizacion.vNumTarjeta = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vNumTarjeta = "";
            ind++;
        }*/
        if(ind<tam){
            VariablesFidelizacion.vApePatCliente = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vApePatCliente = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vApeMatCliente = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vApeMatCliente = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vNomCliente = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vNomCliente = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vFecNacimiento = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vFecNacimiento = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vSexo = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vSexo = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vDireccion = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vDireccion = "";
            ind++;
        }
        if(ind<tam){
            VariablesFidelizacion.vTelefono = lDatosCliente.get(ind).toString();
            ind++;
        }else{
            VariablesFidelizacion.vTelefono = "";
            ind++;
        }
        
    }
    
    /**
     * Metodo encargado de validar formato de correo.
     *@autor jcallo
     *@since 02.10.2008
     */
    public static boolean validarEmail( String email ) { 
     
     boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);
     
     return b;
    }
    
    /**
     * Metodo encargado de validacion del documento de identificacion ( DNI, CARNE DE EXTRANJERIA)     * 
     *@autor jcallo
     *@since 06.10.2008
     */
    public static boolean validarDocIndentificacion( String docIden ) { 
        log.info("doc a validar :"+docIden);
        log.info("validar con :"+VariablesFidelizacion.vDocValidos);
        boolean flag = false;       
        String paramDocVal = VariablesFidelizacion.vDocValidos;
        if(paramDocVal != null ){
            String valores[] = paramDocVal.split(",");
            log.info("valores :  "+valores.toString());
            for( int i =0; i<valores.length ; i++ ){
                //log.debug("izq : "+Integer.parseInt( valores[i].trim() )+" doc: "+docIden+", docIden length: "+docIden.length());
                if( Integer.parseInt( valores[i].trim() ) == docIden.length() ){
                    log.info("ok");
                    flag = true;
                    break;
                }
            }
        }
       
     return flag;
    }
    
    
    public static boolean validarDocIdentidad(String codIde){
        /*boolean flag=false;
        int codNum=Integer.parseInt(codIde);
        if(codIde!=null && codNum>0 ){
            
            if(codIde.length()==8){
                flag=true;
                    log.info("validado");
                }
            else if(codIde.length()<=7 || codIde.length()>=9){
                log.info("invalido");
                flag=false;
            }
            
            }
        log.info("DNI nullo");
        return flag;*/
        
        //LLEIVA 01-Abr-2014 Se utilizara la funcion de FV 1.0
        return validarDocIndentificacion(codIde);
    }
    
    /**
     * Metodo encargado de validar que el parametro, es numero de 13 digitos
     * ademas que no es un codigo de barra
     * ademas que el numero de tarjeta este disponible
     * @Author JCALLO
     * @Since 30.09.08
     * @param pCadena
     * @return
     */
    public static boolean esTarjetaFidelizacionDisponible(String pCadena){
        boolean retorno = false;
        int pTamano = pCadena.length();        
        if(pTamano > 1)
        {
           //se valida el codigo de barra a nivel de local 
            if(isNumerico(pCadena) && //es numerico
               pTamano == 13 && //es de 13 digitos
               validaCodBarraLocal(pCadena) &&//no es codigo de barra
               tarjetaDisponibleLocal(pCadena))//y la tarjeta esta disponible
            {
              retorno = true;
            }
        }    
        
        return retorno;
    }
    
    /**
     * verifica si la tarjeta es valida y esta dispoble para ser usado
     * @Author JCALLO
     * @Since 18.12.08
     * @param cadena
     * @return
     */
    private static boolean tarjetaDisponibleLocal(String cadena){
        boolean retorno=false;
        String valida="";
        
        try{
            valida = DBFidelizacion.isTarjetaDisponibleLocal(cadena).trim();
            if(valida.equals("S")){
                log.debug("La tarjeta "+cadena+" esta disponible para ser usado");
                retorno=true;
            }else{
                log.debug("La tarjeta "+cadena+" ya esta asigando a un cliente en local");
            }
        }catch(SQLException e){
            log.error("",e);
        }
        
        return retorno;
    }
    
    /**
     * Genera el ean13 de la nueva tarjeta del local.
     */
    public static String generaNuevaTarjeta(String vPrefijo,
                                            String vCodLocal){
                                            
        String vConcatenado = vPrefijo.trim() + vCodLocal.trim();
        String vNuevaTarjeta = "";
        try{
            vNuevaTarjeta = DBFidelizacion.generaNuevaTarjetaFidelizacion(vConcatenado);
        }catch(SQLException e){
            log.error("",e);
            vNuevaTarjeta = "-1";
        }
        
        return vNuevaTarjeta;
                                                
    }
   
   
   /**
    * Inserta la nueva tarjeta de fidelizacion en matriz
    * @author      dveliz
    * @since       14.02.2009 
    */
    
    public static void insertarTarjetaMatriz(String vNumTarjeta,
                                             String vDNICliente){
                                             
        validarConexionMatriz();
        if(VariablesFidelizacion.vIndConexion.equals("S")){
            try{
                DBFidelizacion.insertarNuevaTarjetaFidelizacionMatriz(
                        vNumTarjeta,
                        vDNICliente,
                        VariablesFidelizacion.vIndConexion);
                log.info("Se inserto con exito la nueva tarjeta de fidelizacion en matriz");
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
            }catch(SQLException e){
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
                log.info("Error la insertar la nueva tarjeta de fidelizacion en matriz");
                log.error("",e);
            }
        }else{
            log.info("No hay linea para insertar la nueva tarjeta de fidelizacion en matriz");
        }
        
    }
   
   /**
     * Trae la tarjeta de matriz a Local
     * @author Dubilluz
     * @since  17.02.2009
     */
   public static void creaTarjetaLocal(String vCodTarjeta, String vIndConexion) {

       ArrayList vDatos = new ArrayList();
       String pRespuesta = "S";
       try
       {
           pRespuesta = DBFidelizacion.getExisteTarjeta(vCodTarjeta.trim()).trim();
           
           if(pRespuesta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
               DBFidelizacion.getDatosTarjeta(vDatos, vCodTarjeta);
               
               if(vDatos.size()>0)
               {
                   
                  DBFidelizacion.insertTarjeta(vCodTarjeta,
                                               FarmaUtility.getValueFieldArrayList(vDatos,0,1),//DNI
                                               FarmaUtility.getValueFieldArrayList(vDatos,0,2)//local
                                              );
                  FarmaUtility.aceptarTransaccion();
               }
               log.debug("Se inserto la tarjeta en Local");
           }
           
           
       } catch (SQLException e) {
           log.debug("Error en insertar la tarjeta en Matriz");
           log.error("",e);
           FarmaUtility.aceptarTransaccion();
       }

   }
   
   /**
     * Obtiene el DNI valido o NO
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
   public static boolean isDniValido(String pDni){
       boolean pResultado = true;
       String pStrRes;
       try
       {
           pStrRes = DBFidelizacion.isDNI_Anulado(pDni.trim()).trim();
           
           if(pStrRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
               pResultado = false;
               log.info("Cliente no esta permitido para descuento");
           }
           else{
               pResultado = true;
               log.info("Cliente SI aplica DESCUENTO");
           }
           
       } catch (Exception e) {
           log.debug("Error al obtener");
           log.info("Cliente SI aplica DESCUENTO");
           log.error("",e);
           pResultado = true;
       }
       return pResultado;
   }
   
    public static boolean isRUCValido(String pRUC){
        boolean pResultado = true;
        String pStrRes;
        try
        {
            pStrRes = DBFidelizacion.isRUC_Anulado(pRUC.trim()).trim();
            
            if(pStrRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                pResultado = false;
                log.info("RUC no esta permitido para descuento");
            }
            else{
                pResultado = true;
                log.info("RUC SI aplica DESCUENTO");
            }
            
        } catch (Exception e) {
            log.debug("Error al obtener");
            log.info("RUC SI aplica DESCUENTO");
            log.error("",e);
            pResultado = true;
        }
        return pResultado;
    }
   
   /**
     * Retorna Ahorro DNI x Periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
    public static double getAhorroDNIxPeriodoActual(String pDni,String ptarj){
        double pResultado = 0;
        String pStrRes;
        try
        {
            pResultado = DBFidelizacion.getAhorroDNIxPeriodo(pDni.trim(),ptarj.trim());
        } catch (Exception e)
        {
            log.debug("Error al obtener ahorro actual al periodo x DNI");
            log.error("",e);
            pResultado = 0;
        }
        return pResultado;
    }  
    /**
     * Retorna el maximo ahorro DNI x Periodo
     * @author DUBILLUZ
     * @since  28.05.2009
     * @param pDni
     * @return
     */
    // dubilluz 01.06.2012
    public static double getMaximoAhorroDnixPeriodo(String pDni,String pTarjeta){
        double pResultado = 0;
        String pStrRes;
        try
        {
            // getMaximoAhorroDNIxPeriodo
            pResultado = DBFidelizacion.getMaximoAhorroDNI_NEW(pDni,pTarjeta);
        } catch (Exception e)
        {
            log.debug("Error al obtener Maximo Ahorro x DNI");
            log.error("",e);
            pResultado = 0;
        }
        return pResultado;
    }
        
    public static double getMaxUnidDctoProdCampana(String pCodCampana,String pCodProd){
        double pResultado = 0;
        String pStrRes;
        try
        {
            pResultado = DBFidelizacion.getMaxUnidDctoProdCampana(pCodCampana,pCodProd);
        } catch (Exception e)
        {
            log.debug("Error al obtener Maximo Unidades de Dcto Producto");
            log.error("",e);
            pResultado = -1;
        }
        log.info("Maximo de Dcto Unid Prod Campana: pCodProd: " + pCodProd+" -pCodCampana: "+ pCodCampana +" -MaxUnid: "+pResultado);
        return pResultado;
    }
    
    /**
     * Este procedimiento valida el pedido Fidelizado
     * como el DNi valido, RUC valido o Maximo DCTO x DNI
     * @author DUBILLUZ
     * @since  29.05.2009
     * @param pCodCampana
     * @param pCodProd
     * @return
     */
    public static boolean validaPedidoFidelizado(String pNumPed,String pRuc,Object pDialogo,Object pObjeto,String ptarj){
        boolean pResultado = false;
        String pRes = "";
        String pMensaje  = "";
        String pValidaMatriz = "";
        String ipLinea = "";
        try
        {
            
            pValidaMatriz = DBFidelizacion.isValidaMatrizDescuento(pNumPed);
            if(pValidaMatriz.trim().length()>1){
                //Se obtuvo un codigo de campana q valida  la tarjeta unica.
                ipLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                
                if(ipLinea.trim().equalsIgnoreCase("S")){
                    pRes = DBFidelizacion.isValidaPedidoFidelizadoMatriz(pNumPed,pRuc,VariablesFidelizacion.vDniCliente);
                }
                else{
                    //no hay linea SOLO LOCALMENTE
                    pRes = DBFidelizacion.isValidaPedidoFidelizado(pNumPed,pRuc,ptarj);
                }
            }
            else{
                //VALIDA LOCALMENTE
                pRes = DBFidelizacion.isValidaPedidoFidelizado(pNumPed,pRuc,ptarj);
            }
            
            if(pRes.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                pResultado = true;
            }else {
                if (pRes.trim().equalsIgnoreCase("N_DNI")) {
                    pMensaje = "DNI no afecto a Descuento";// "No puede cobrar el pedido con descuentos para el DNI ingresado.";
                } else {
                    if (pRes.trim().equalsIgnoreCase("N_RUC")) {
                        pMensaje = "Los descuentos son para venta con\n"+
                                   "boleta de venta y para consumo\n"+
                                   "personal. El RUC ingresado queda\n"+
                                   "fuera de la promoci�n de descuento.";//"No puede cobrar el pedido con descuentos para el RUC ingresado.";
                        
                    } else {
                        if (pRes.trim().equalsIgnoreCase("N_DCTO"))
                        pMensaje = "DNI excedi� el m�ximo\n"+
                                   "de descuento permitido.\n"+
                                   "Debe recalcular la venta.";//"No puede cobrar el pedido porque el Cliente excedi� el m�ximo descuento Diario.";
                        VariablesFidelizacion.vRecalculaAhorroPedido = true;
                    }
                }
                pResultado = false;
                log.error(pMensaje);
                FarmaUtility.showMessage((JDialog)pDialogo,pMensaje.trim(),pObjeto);
            }

        } catch (Exception e)
        {
            log.debug("Error al validar el Pedido Fidelizado");
            log.error("",e);
            pResultado = true;
        }
        return pResultado;
    }
    
   /* //jquispe 23.04.2010
    public static void operaCampa�asSinFidelizar()
    { 
        try{
        VariablesFidelizacion.vListCampa�asFidelizacion=  DBFidelizacion.obtenerCampaniasAutSinFidelizacion();
        //operaCampa�asSinFidelizar()
        }catch(SQLException sql)
        { log.error("",sql);
        }
        
        log.debug(VariablesFidelizacion.vListCampa�asFidelizacion.size());
    }*/
    
    public static double getDescuentoPersonalizadoProdCampana(String pCodCampana,String pCodProd){
        double pResultado = 0;
        String pStrRes;
        try
        {
            pResultado = DBFidelizacion.getDctoPersonalizadoCampanaProd(pCodCampana,pCodProd);
        } catch (Exception e)
        {
            log.debug("Error al obtener el descuento personalizado por Campana y producto");
            log.error("",e);
            pResultado = -1;
        }
        log.info("Descuento PersonalizadoProd Campana: pCodProd: " + pCodProd+" -pCodCampana: "+ pCodCampana +" - Descuento: "+pResultado);
        return pResultado;
    }
    
    /**
         * Consulta para saber si la tarjeta ingresada esta en una campa�a automatica
         * @param cadena
         * @return
         */
        public static boolean isTarjetaPagoInCampAutomatica(String cadena){
        
            boolean retorno=true;
            String valida="";
            
            if (isNumericoTarjeta(cadena)) {
                try{
                  valida= DBFidelizacion.isTarjetaFp_CampAutomatica(cadena);
                  if(valida.equalsIgnoreCase("N")){
                  retorno=false;
                  log.debug("la tarjeta no esta asociada a ninguna forma de pago de campana automatica");
                  }else{
                  log.debug("Tarjeta asociada a una campana automatica con esa forma de pago");
                  }
                }catch(SQLException e){
                   log.error("",e);
                }
            }
            else
                retorno = false;
            return retorno;
        }
        
        public static boolean isNumericoTarjeta(String pCadena){
            int numero = 0;
            boolean pRes = false;
            try {
                for(int i=0;i<pCadena.length();i++){
                    numero = Integer.parseInt(pCadena.charAt(i)+"");
                    pRes = true;    
                }
                
            } catch (NumberFormatException e) {
                pRes = false;
            }
            return pRes;
        }
        
        /**
         * 
         */
        public static String getDatosTarjetaFormaPago(String pTarjetaIngresada){
            //getDatosTarjetaIngresada
            String datosFormaPago = "N";
            try {
                datosFormaPago =  DBFidelizacion.getDatosTarjetaIngresada(pTarjetaIngresada);
                } catch (SQLException e) {
                log.error("",e);
                datosFormaPago = "N";
            } finally {
                
            }
            return datosFormaPago.trim();
        }    
    
    /**
     * Dubilluz
     * 19.07.2011
     */
    public static void grabaTarjetaUnica(String pTarjetaPago,String pdni)
    {
        log.debug("graba tarjeta y dni a la campana"+pTarjetaPago+"-"+pdni);
        try
        {
            DBFidelizacion.grabaTarjetaUnica(pTarjetaPago, pdni);
            FarmaUtility.aceptarTransaccion();
            VariablesFidelizacion.vNumTarjeta = pTarjetaPago;
            //log.debug("(pdni.trim().indexOf(\"TU\")"+pdni.trim().indexOf("TU")); 
            if(isContieneLetra(pdni)){
                //todos son NUMEROS ahora se validar� la campa�a para invertir de acuerdo al 
                //flag y la cadena INICIAL
               String pCadenaInvertir  =  DBFidelizacion.getCadenaInvierteDNI("N",pTarjetaPago.trim()).trim(); 
               boolean cambioDNI = false;
               if(pCadenaInvertir!=null){
                   if(!pCadenaInvertir.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                       log.debug("cambiando DNI a invertir y coloca el FLAG:<<"+pCadenaInvertir+">>");
                       VariablesFidelizacion.vDniCliente = pCadenaInvertir.trim()+invertirLetra(pdni);
                       cambioDNI = true; 
                   }
               }
               
               if(!cambioDNI){
                   VariablesFidelizacion.vDniCliente = pdni.trim(); 
                   log.debug("no ha cambiado el DNI ..se coloca el mismo DNI");
               }
            }
            else
               VariablesFidelizacion.vDniCliente = pdni.trim(); 
        }catch (Exception e)
        {
            log.error("",e);
            FarmaUtility.liberarTransaccion();
        }
    }
    public static boolean isContieneLetra(String pCadena){
        boolean pResultado = false;
        char letra = ' ';
        
        for(int i=0;i<pCadena.length();i++){
            letra = pCadena.charAt(i);
            if(!Character.isDigit(letra)){
                log.debug("exite letra");
                return false;
                
            }
        }
        
        log.debug("todos son numeros");
        return true;
    }
    
    public static String invertirLetra(String pCadena)
    {
        int tamano =  pCadena.length()-1;
        char letra = ' ';
        String CadenaInversa = "";
        for(int i=tamano;i>=0;i--){
            letra = pCadena.charAt(i);
            log.debug("letra:"+letra);
            CadenaInversa =CadenaInversa+ ""+letra;
        }
        log.debug("CadenaInversa:"+CadenaInversa);
        return CadenaInversa;
    }
    
    public static String existeDniAsociado(String pCadena){
        String pcadena = "";
        try {
            pcadena = DBFidelizacion.existeTarjetaDNI(pCadena).trim();
        } catch (SQLException e) {

        }
        return pcadena;
    }
    
    
    /**
     * Recibe la primera Trama
     * @author Dubilluz
     * @since  21.07.2011
     * @return
     */
    public static String pIsTarjetaVisaRetornaNumero(String pCadena){
        /*
         * Trama de Ejemplo 
         *  
            %B4919148070794606&JULIO OLIVA J      -     &1509101137250000000000742000000_
            �4919148070794606�15091011372574200000_
         * */
        
        try{
            log.debug("pCadena:"+pCadena);
            log.debug("pCadena.indexOf(\"%\"):"+pCadena.indexOf("%"));
            //String pResultado  = "N";     
            String pCadenNueva = "N";
            if(pCadena.length()>10){
                ArrayList infTarjeta = new ArrayList();
                UtilityLectorTarjeta utilityLectorTarjeta = new UtilityLectorTarjeta();
                infTarjeta = utilityLectorTarjeta.capturaTeclasLector(null,pCadena);
                pCadenNueva = infTarjeta.get(0).toString();
                if(pCadenNueva.equals("")){
                    pCadenNueva = "N";
                }
            }
            return pCadenNueva;
        }catch(Exception e)
        {
            log.debug("error al leer tarjeta unica ,"+e.getMessage());
            //pCadenNueva = "N";
            return "N";
        }
        
        //return pCadenNueva;
    }
  
  
    //jquispe 26.07.2011
    //Devuelve el indicador de fidelizacion de una campa�a
    public static String getIndfidelizadoUso(String pCodCampania)                                                                      
    {      
    try{
        return DBFidelizacion.obtenerIndFidUso(pCodCampania);                                 
      } catch (SQLException e) {      
        return "N";           
     }
    }
    
    /**
     * dubilluz 06.12.2011
     * @return
     */
    public static String getPermiteIngresoMedido()
    {      
    try{
        return DBFidelizacion.permiteIngresoMedico();
      } catch (SQLException e) {      
        return "N";           
     }
    }
    /**
     * dubilluz 06.12.2011
     * @return
     */
    public static String getExisteMedido(Frame pParent,String pCodMedido)
    {
     String pExiste = "N";
     List listaMed = new ArrayList();
     String pPrimeraFila = "";
    try{
        listaMed = DBFidelizacion.busquedaMedicos(pCodMedido.trim());
        if(listaMed.size()>0){
            pPrimeraFila  = (((HashMap)listaMed.get(0)).get("NUM_CMP")).toString().trim();
            if(pPrimeraFila.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            // NO EXISTEN MEDICOS CON EL NUMERO INDICADO
                pExiste = "N";
            else{
                ///////////////////////////////////
                if(listaMed.size()==1){
                    pExiste = "S";
                    // Se va seleccionar autom�ticamente el medico Ingresado
                    HashMap map = (HashMap)listaMed.get(0);
                    VariablesFidelizacion.V_NUM_CMP = ((String)map.get("NUM_CMP")).trim();            
                    VariablesFidelizacion.V_NOMBRE = ((String)map.get("NOMBRE")).trim();            
                    VariablesFidelizacion.V_DESC_TIP_COLEGIO = ((String)map.get("DESC_TIP_COLEGIO")).trim();            
                    VariablesFidelizacion.V_OLD_TIPO_COLEGIO = ((String)map.get("DESC_TIP_COLEGIO")).trim();            
                    VariablesFidelizacion.V_TIPO_COLEGIO = ((String)map.get("TIPO_COLEGIO")).trim();
                    VariablesFidelizacion.V_COD_MEDICO = ((String)map.get("COD_MEDICO")).trim();
                    
                    JOptionPane.showMessageDialog(pParent,"M�dico Seleccionado:\n" +
                                                       "CMP:"+VariablesFidelizacion.V_NUM_CMP+"\n"+
                                                       VariablesFidelizacion.V_DESC_TIP_COLEGIO+ "\n"+
                                                       VariablesFidelizacion.V_NOMBRE,
                                                                      "Mensaje del Sistema", 
                                                                      JOptionPane.WARNING_MESSAGE
                                                           );
                    
                }
                ///////////////////////////////////
                else
                {
                        DlgMedicoCampana dlgLista = new DlgMedicoCampana(pParent,"",true,(ArrayList)listaMed);
                        dlgLista.setVisible(true);
                        if(FarmaVariables.vAceptar){
                            pExiste = "S";
                            JOptionPane.showMessageDialog(pParent,"M�dico Seleccionado:\n" +
                                                               "CMP:"+VariablesFidelizacion.V_NUM_CMP+"\n"+
                                                               VariablesFidelizacion.V_DESC_TIP_COLEGIO + "\n"+
                                                               VariablesFidelizacion.V_NOMBRE,
                                                                      "Mensaje del Sistema", 
                                                                      JOptionPane.WARNING_MESSAGE
                                                                   );
                        }
                        else{
                            pExiste = "NO_SELECCIONO";
                        }
                }
            }
            
        }
      } catch (SQLException e) {      
        log.error("",e);  
         pExiste = "N";
     }
        return pExiste;
    }
    
    /**
     * dubilluz 06.12.2011
     * @param pNumTarjeta
     * @return
     */
    public static void agregaCampanaMedicoAuto(String pNumTarjeta,String pMedico,String pMedicoOld) {
        List listaCampaTarj = new ArrayList();
        List listaCampaTarjOld = new ArrayList();
        try {
            listaCampaTarj = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta,pMedico);
            listaCampaTarjOld = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta,pMedicoOld);
        } catch (SQLException e) {
                listaCampaTarj = new ArrayList();
            log.error("",e);           
        }
        
        //quita las campnas del anterior medico ingresado
        log.debug("** inicio quitar antiguas *** ");
        if(listaCampaTarjOld.size()>0){
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesModuloVentas.vArrayList_Cupones.size());
            
            Map map = new HashMap();
            Map mapAux = new HashMap();
            String codCamp = "",codCampAux = "";
            for(int i=0;i<listaCampaTarjOld.size() ;i++){
                mapAux = (HashMap)listaCampaTarjOld.get(i);
                codCampAux  = ((String)mapAux.get("COD_CAMP_CUPON")).trim();
                // elimina del VariablesFidelizacion.vListCampa�asFidelizacion
                for(int j=0;j< VariablesFidelizacion.vListCampa�asFidelizacion.size() ;j++){
                    map = new HashMap();
                    map = (HashMap)VariablesFidelizacion.vListCampa�asFidelizacion.get(j);
                    codCamp  = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if(codCampAux.trim().equalsIgnoreCase(codCamp)){
                        VariablesFidelizacion.vListCampa�asFidelizacion.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: "+ codCampAux);
                    }
                }
                // elimina del VariablesVentas.vArrayList_Cupones
                for(int j = 0;j < VariablesModuloVentas.vArrayList_Cupones.size(); j++){
                        map = new HashMap();
                        map = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(j);
                        codCamp  = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if(codCampAux.equalsIgnoreCase(codCamp)){
                        VariablesModuloVentas.vArrayList_Cupones.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: "+ codCampAux);
                    }
                }
            }
            
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesModuloVentas.vArrayList_Cupones.size());
        }            
        log.debug("** FIN quitar antiguas *** ");    
        // agrega las campanas dl nuevo medico ingresado    
        if(listaCampaTarj.size()>0){
            log.debug("Agrega las campanas del medico ingresado..."+listaCampaTarj.size());
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            for(int i=0;i<listaCampaTarj.size() ;i++){
            VariablesFidelizacion.vListCampa�asFidelizacion.add(listaCampaTarj.get(i));
            }
            log.debug("Despues  ..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());

            
                    
                    Map mapCampFid = new HashMap();//mapa de la campania de fidelizacion
                    Map mapCampCup = new HashMap();//mapa de la campania del listado de cupones
                    
                    String codCampFid = "";//codigo de campania de fidelizacion
                    String CodCampCup = "";//codigo de campania de cupon de la lista general
                    
                log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesModuloVentas.vArrayList_Cupones.size());    
                boolean existe = false;
                for (int i = 0; i < listaCampaTarj.size();i++) {
                    mapCampFid = (HashMap)listaCampaTarj.get(i);
                    codCampFid  = ((String)mapCampFid.get("COD_CAMP_CUPON")).trim();
                    for (int j = 0;j < VariablesModuloVentas.vArrayList_Cupones.size(); j++) {
                            mapCampCup = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(j);
                            CodCampCup  = ((String)mapCampCup.get("COD_CAMP_CUPON")).trim();
                        if (codCampFid.equalsIgnoreCase(CodCampCup)) {//ver si ya existe
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {      
                        log.debug("agregando  mapCampFid:"+mapCampFid+", a VariablesVentas.vArrayList_Cupones:" + VariablesModuloVentas.vArrayList_Cupones);
                    VariablesModuloVentas.vArrayList_Cupones.add(mapCampFid);
                    }
                }
                log.debug("VariablesVentas.vArrayList_Cupones.size():" + VariablesModuloVentas.vArrayList_Cupones.size());
            
            
        }
    }


    public static void quitarCampanaMedico(String pNumTarjeta,String pMedicoOld) {
        List listaCampaTarj = new ArrayList();
        List listaCampaTarjOld = new ArrayList();
        try {
            listaCampaTarjOld = DBFidelizacion.getCampanaMedicoAuto(pNumTarjeta,pMedicoOld);
        } catch (SQLException e) {
                listaCampaTarj = new ArrayList();
            log.error("",e);           
        }
        
        //quita las campnas del anterior medico ingresado
        log.debug("** inicio quitar antiguas *** ");
        if(listaCampaTarjOld.size()>0){
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesModuloVentas.vArrayList_Cupones.size());
            
            Map map = new HashMap();
            Map mapAux = new HashMap();
            String codCamp = "",codCampAux = "";
            for(int i=0;i<listaCampaTarjOld.size() ;i++){
                mapAux = (HashMap)listaCampaTarjOld.get(i);
                codCampAux  = ((String)mapAux.get("COD_CAMP_CUPON")).trim();
                // elimina del VariablesFidelizacion.vListCampa�asFidelizacion
                for(int j=0;j< VariablesFidelizacion.vListCampa�asFidelizacion.size() ;j++){
                    map = new HashMap();
                    map = (HashMap)VariablesFidelizacion.vListCampa�asFidelizacion.get(j);
                    codCamp  = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if(codCampAux.trim().equalsIgnoreCase(codCamp)){
                        VariablesFidelizacion.vListCampa�asFidelizacion.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: "+ codCampAux);
                    }
                }
                // elimina del VariablesVentas.vArrayList_Cupones
                for(int j = 0;j < VariablesModuloVentas.vArrayList_Cupones.size(); j++){
                        map = new HashMap();
                        map = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(j);
                        codCamp  = ((String)map.get("COD_CAMP_CUPON")).trim();
                    if(codCampAux.equalsIgnoreCase(codCamp)){
                        VariablesModuloVentas.vArrayList_Cupones.remove(j);
                        log.debug("ELIMINANDO::DE ARREGLO::: "+ codCampAux);
                    }
                }
            }
            
            log.debug("VariablesFidelizacion.vListCampa�asFidelizacion..."+VariablesFidelizacion.vListCampa�asFidelizacion.size());
            log.debug("VariablesVentas.vArrayList_Cupones ..." + VariablesModuloVentas.vArrayList_Cupones.size());
        }            
        log.debug("** FIN quitar antiguas *** ");    
        // agrega las campanas dl nuevo medico ingresado    
    }
    
    /**
     * Dubilluz - 01.06.2012
     * @param pCodCampana
     * @param pDni
     * @param pTarj
     * @return
     */
    public static boolean getPermiteCampanaTarj(String pCodCampana , String pDni, String pTarj){
        boolean pRes = false;
        ArrayList lista = new ArrayList();
        try {
            DBFidelizacion.getCampTarjetaEspecial(lista, pDni, pTarj);
        } catch (SQLException e) {
             log.error("",e);
        }   
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                String pCadena = ((ArrayList)lista.get(i)).get(0).toString().trim();
                log.info("pCadena>>"+pCadena);
                if (!pCadena.equalsIgnoreCase("N")) {
                    if (pCodCampana.trim().equalsIgnoreCase(pCadena)) {
                        pRes = true;
                    }
                }
            }
        } else
            pRes = true;

        return pRes;
    }
    /**
     * Verifica si existe el DNI ingresado en la BD de RENIEC
     * @author dubilluz
     * @param pDNI
     * @return
     */
    public static boolean existeDNIenRENIEC(String pDNI){
        boolean pValor = false;
        try {
            if(DBFidelizacion.getDatoDNIReniec(pDNI).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                pValor = false;
            else
                pValor = true;
        } catch (SQLException e) {
            pValor  = false;
            log.error("",e);
        }
        return pValor ;
    }
    
    public static void limpiaVariablesMedico(){
        ///////////////////////////////////////////////
        VariablesFidelizacion.V_NUM_CMP = "";
        VariablesFidelizacion.V_NOMBRE = "";
        VariablesFidelizacion.V_DESC_TIP_COLEGIO = "";
        VariablesFidelizacion.V_TIPO_COLEGIO = "";
        VariablesFidelizacion.V_COD_MEDICO = "";
        VariablesFidelizacion.vColegioMedico = "";
        ///////////////////////////////////////////////        
    }
    
    public static ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            listaCampLimitUsadosMatriz = 
                    DBCaja.getListaCampUsadosLocalXCliente(dniCliente);
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
    
    
    public static boolean  getIndComisionnew() {
        boolean bFlag = false;
        String vValor = "";
        try{
        
        vValor = DBFidelizacion.getIndicadorComision();
            if(vValor.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                bFlag= true;
                }
            else{
                  bFlag= false;
                }
            
        }catch(SQLException e)        
        {
            e.getMessage();
            log.error("",e);
            
        }
    
        return bFlag;
    }  
      
  
}
