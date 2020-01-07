package admcentral.packs.reference;

import java.sql.SQLException;

import java.util.ArrayList;

//import mifarma.admcentral.fraccion.reference.VariablesFraccion;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBParametros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * MHUAYTA 06.02.2006 Creación<br>
 * <br>
 *
 * @author Manuel Huayta Rojas<br>
 * @version 1.0<br>
 *
 */
public class

DBParametros
{

  private static ArrayList parametros = new ArrayList();

  public DBParametros()
  {
  }

  public static void getListaLocales(FarmaTableModel pTableModel,String vCod)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(vCod);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "ADMCENTRAL_PAR.PAR_LISTA_LOCALES(?)", 
                                             parametros, false);
  }

  public static void modificaParametros(String pCodGrupoCia, 
                                        String pCodLocal, 
                                        String pCodGrupoRep, 
                                        String pNumMin, String pNumMax, 
                                        String pNumRot, String pTipo)
    throws SQLException
  {

    parametros = new ArrayList();
    parametros.add(pCodGrupoCia);
    parametros.add(pCodLocal);
    parametros.add(pCodGrupoRep);
    parametros.add(pNumMin);
    parametros.add(pNumMax);
    parametros.add(pNumRot);
    parametros.add(pTipo);
    FarmaDBUtility.executeSQLStoredProcedure(null, 
                                             "ADMCENTRAL_PAR.PAR_MODIFICA_PARAMETROS(?,?,?,?,?,?,?)", 
                                             parametros, false);

  }

  public static void getListaLineas(FarmaTableModel pTableModel)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
   // parametros.add(VariablesFraccion.COD_LOCAL);

    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "ADMCENTRAL_PAR.PAR_LISTA_LINEAS(?)", 
                                             parametros, false);
  }

  // ERIOS

  public static void listarPrecioProducto(FarmaTableModel pTableModel)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "ADMCENTRAL_PAR.PAR_LISTA_PRECIO_PRODUCTO(?,?)", 
                                             parametros, false);
  }

  public static void listarPrecioProductoFiltro(FarmaTableModel pTableModel, 
                                                String tipoFiltro, 
                                                String codFiltro)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(tipoFiltro);
    parametros.add(codFiltro);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "ADMCENTRAL_PAR.PAR_LISTA_PRECIO_PROD_FILTRO(?,?,?,?)", 
                                             parametros, false);
  }

  public static void listarLocalesIndicadores(FarmaTableModel pTableModel,String vCod)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesParametros.vCod_Prod);
     parametros.add(vCod);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "ADMCENTRAL_PAR.PAR_LISTA_LOCALES_IND(?,?,?)", 
                                             parametros, true);
  }

  public static void actualizaIndicadoresLocal(String codLocal, 
                                               String indReponer, 
                                               String cantExhib)
    throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesParametros.vCod_Prod);
    parametros.add(codLocal);
    parametros.add(indReponer);
    parametros.add(new Double(cantExhib));
    parametros.add(FarmaVariables.vIdUsu);
    FarmaDBUtility.executeSQLStoredProcedure(null, 
                                             "ADMCENTRAL_PAR.PAR_ACTUALIZA_IND_LOCAL(?,?,?,?,?,?)", 
                                             parametros, true);
  }
}
