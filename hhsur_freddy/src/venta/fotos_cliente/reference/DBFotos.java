package venta.fotos_cliente.reference;


import common.FarmaDBUtility;

import common.FarmaVariables;

import java.sql.SQLException;
import java.util.ArrayList;
//import common.FarmaDBUtility;
//import common.FarmaVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBFotos {
    
    private static final Logger log = LoggerFactory.getLogger(DBFotos.class);
    public DBFotos() {
        super();
    }
    
    
    public static void cargaDatosLoginFTP(ArrayList vDatos) throws SQLException {
        
        ArrayList parametros = new ArrayList();
        log.info("invocando a SV_IMG_CLIENTE.F_DATO_FTP():" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_DATO_FTP()",
                                                          parametros);
        // FarmaConnection.getConnection().close();
    }
    
    public static void datosCliente(ArrayList vDatos,String pDNI) throws SQLException {
        
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        log.info("invocando a SV_IMG_CLIENTE.F_DATO_CLIENTE(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_DATO_CLIENTE(?)",
                                                          parametros);
    }
    
    public static void grabarImg_x_DNI(String pDNI,
                                       String pNombreFile,
                                       String RutaImg
                                       ) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        parametros.add(pNombreFile);
        parametros.add(RutaImg);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("invocando a SV_IMG_CLIENTE.P_GRABA_IMG_X_DNI(?,?,?," +
                                                               "?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"SV_IMG_CLIENTE.P_GRABA_IMG_X_DNI(?,?,?," +
                                                 "?)",parametros,false);
        // FarmaConnection.getConnection().close();
    }
    
    public static void cambiaEstadoFileImg(String pDNI,String pNombreFile) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        parametros.add(pNombreFile);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("invocando a SV_IMG_CLIENTE.P_CAMBIA_ESTADO_IMG(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"SV_IMG_CLIENTE.P_CAMBIA_ESTADO_IMG(?,?,?)",parametros,false);
        // FarmaConnection.getConnection().close();
    } 
    
    public static void getListaImagenesxDni(ArrayList vDatos,
                                            String    pDNI) throws SQLException {
        
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        log.info("invocando a SV_IMG_CLIENTE.F_GET_IMG_X_DNI(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_GET_IMG_X_DNI(?)",
                                                          parametros);
        // FarmaConnection.getConnection().close();
    }

    public static void getListaAnio_x_DNI(ArrayList vDatos,
                                            String    pDNI) throws SQLException {
        
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        log.info("invocando a SV_IMG_CLIENTE.F_GET_IMG_X_DNI_ANIO(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_GET_IMG_X_DNI_ANIO(?)",
                                                          parametros);
        // FarmaConnection.getConnection().close();
    }
    
    public static void getListaMesxDni_ANIO(ArrayList vDatos,
                                            String    pDNI,
                                            String pAnio) throws SQLException {
        
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        parametros.add(pAnio);
        log.info("invocando a SV_IMG_CLIENTE.F_GET_IMG_X_DNI_MES_ANIO(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_GET_IMG_X_DNI_MES_ANIO(?,?)",
                                                          parametros);
        // FarmaConnection.getConnection().close();
    }
    
    public static void getListaImagenesxDni_MES(ArrayList vDatos,
                                            String    pDNI,
                                                String pAnio,
                                                String pMes) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI);
        parametros.add(pAnio);
        parametros.add(pMes);
        log.info("invocando a SV_IMG_CLIENTE.F_GET_IMG_X_DNI(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vDatos,
                                                          "SV_IMG_CLIENTE.F_GET_IMG_X_DNI_IMG_x_MES(?,?,?)",
                                                          parametros);
        // FarmaConnection.getConnection().close();
    }
}
