package admcentral.packs.reference;

import common.FarmaDBUtility;
import common.FarmaTableModel;

import common.FarmaUtility;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DBPacks.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      15.09.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DBPacks {
    
    private static ArrayList parametros = new ArrayList();
    
    public DBPacks() {
    }
    
    /**listado de packs**/
    public static void listadoPacks(FarmaTableModel pTableModel,
                                    String pIndVerVigente)
      throws SQLException
    {
      pTableModel.clearTable();
      
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      /*parametros.add(cCodOrigen);
      parametros.add(cCodArea);*/
      parametros.add(pIndVerVigente);
      
      System.out.println("LISTADO DE PACKS");
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_F_CUR_LISTA_PACKS(?,?)", 
                                               parametros, false);
    }
    
    /** listado de los productos dentro de un paquete**/
    public static void listadoPaquete(FarmaTableModel pTableModel, String pCodPaquete)
      throws SQLException
    {
      pTableModel.clearTable();
      
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodPaquete);
      /*parametros.add(cCodArea);*/
      
      System.out.println("LISTADO DE PAQUETES cod_paquete:"+pCodPaquete);
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_F_CUR_LISTA_PROD_PAQUETE(?,?)", 
                                               parametros, false);
    }
    
    /**LISTADO DE TODOS LOS PRODUCTOS ACTIVOS **/
    public static void listadoProductos(FarmaTableModel pTableModel)
      throws SQLException
    {
      pTableModel.clearTable();
      
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);      
      
      System.out.println("LISTADO DE PRODUCTOS ACTIVOS");
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_F_CUR_LISTA_PROD(?)", 
                                               parametros, true);
    }
    
    /**
     * INSERTAR PAQUETE
     * **/
    public static String insertarPaquete()
      throws SQLException
    {
      parametros = new ArrayList();
      
      parametros.add(FarmaVariables.vCodGrupoCia);//grupo de la compañia 001
      parametros.add(FarmaVariables.vCodLocal);//codigo del local  009
      parametros.add(FarmaVariables.vIdUsu);//iddel usuario login
      //parametros.add(FarmaVariables.vIdUsu);
      
      System.out.println("INSERTANDO PAQUETE");
      
      return FarmaDBUtility.executeSQLStoredProcedureStrOut("ADMCENTRAL_MANT_PACKS.MANT_P_INSERTA_PAQUETE(?,?,?,?)", 
                                               parametros);
    }
    
    /**
     * INSERTAR PRODUCTO - PAQUETE
     * 
     * **/
    public static void insertarProductoPaquete(String pCodPaquete,
                                               String pCodProducto,
                                               String pCantidad,
                                               String pPorcDcto,
                                               String pValFrac)
      throws SQLException
    {
        
        System.out.println("pCodPaquete: "+pCodPaquete);
        System.out.println("pCodProducto: "+pCodProducto);
        System.out.println("pCantidad: "+pCantidad);
        System.out.println("pPorcDcto: "+pPorcDcto);
        System.out.println("pValFrac: "+pValFrac);
        
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodPaquete);
        parametros.add(pCodProducto);
        parametros.add(new Integer(pCantidad));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pPorcDcto)));
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);      
      
        System.out.println("INSERTANDO PRODUCTO PAQUETE "+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                "ADMCENTRAL_MANT_PACKS.MANT_P_INSERTA_PAQUETE_PROD(?,?,?,?,?,?,?)",
                parametros, false);      
    }
    
    /**
     * INSERTAR PACK
     * 
     * **/
    public static void insertarPack(String pDescCorta,
                                    String pDescLarga,
                                    String pCodPaquete1,
                                    String pCodPaquete2,
                                    String pCantMaxVta,
                                    String pMsgPack,
                                    String pDesde,String pHasta)
      throws SQLException
    {
        parametros = new ArrayList();
        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDescCorta);
        parametros.add(pDescLarga);
        parametros.add(pCodPaquete1);
        parametros.add(pCodPaquete2);
        parametros.add(new Integer(pCantMaxVta));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pMsgPack);

        parametros.add(pDesde);

        parametros.add(pHasta);
      
      
      System.out.println("INSERTANDO PACK");
      
      FarmaDBUtility.executeSQLStoredProcedure(null, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_P_INSERTA_PACK(?,?,?,?,?,?,?,?,?,?,?)", 
                                               parametros, true);
    }
    
    
    /**
     *  Actualizar paquete
     * **/
    public static void actualizarPack(String pCodPack,
                                     String pDescCorta,
                                    String pDescLarga,
                                    String pCodPaquete1,
                                    String pCodPaquete2,
                                    String pEstado,
                                    String pCantMaxVta,
                                    String pMsgPack,
                                    String pDesde,String pHasta)
      throws SQLException
    {
        parametros = new ArrayList();
        
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(pCodPack);
        parametros.add(pDescCorta);
        parametros.add(pDescLarga);
        parametros.add(pCodPaquete1);
        parametros.add(pCodPaquete2);
        parametros.add(pEstado);
        parametros.add(new Integer(pCantMaxVta));
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pMsgPack);
      

        parametros.add(pDesde);

        parametros.add(pHasta);
        
      System.out.println("ACTUALIZA PACK");
      
      FarmaDBUtility.executeSQLStoredProcedure(null, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_P_ACTUALIZA_PACK(?,?,?,?,?,?,?,?,?,?,?,?)", 
                                               parametros, false);
    }
    
    /**
     *  MANT_P_ELIMINA_PAQUETE_PROD
     * **/
    public static void eliminaProdPaquete(String pCodPack,                                     
                                    String pCodPaquete,                                    
                                    String pCodProd)
      throws SQLException
    {
        parametros = new ArrayList();
        
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(pCodPaquete);
        parametros.add(pCodProd);
        
        System.out.println("elimina Producto de paquete");
      
      FarmaDBUtility.executeSQLStoredProcedure(null, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_P_ELIMINA_PAQUETE_PROD(?,?,?)", 
                                               parametros, false);
    }
    
    /**
     * metodo encargado de actualizar datos de un producto de un paquete
     * 
     * MANT_P_ACTUALIZA_PAQUETE_PROD
     * 
     * **/
    public static void actualizarProdPaquete(String pCodPack,
                                    String pCodPaquete,                                    
                                    String pCodProd,
                                    String pCantidad,
                                    String pPorcDcto,
                                    String pValFrac)
      throws SQLException
    {
        parametros = new ArrayList();
        
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(pCodPaquete);
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantidad));
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pPorcDcto)));
        parametros.add(new Integer(pValFrac));
        parametros.add(FarmaVariables.vIdUsu);
        
        System.out.println("actualiza producto de paquete");
      
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                               "ADMCENTRAL_MANT_PACKS.MANT_P_ACTUALIZA_PAQUETE_PROD(?,?,?,?,?,?,?)", 
                                               parametros, false);
    }


    public static void getListaFracciones(FarmaTableModel pTableModel,String pCodProd) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "ADMCENTRAL_MANT_PACKS.LISTA_UNID_FRAC(?,?,?)", parametros,
                                                 false);
    }
}
