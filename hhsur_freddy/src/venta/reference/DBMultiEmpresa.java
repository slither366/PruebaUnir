package venta.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.reference.VariablesAdministracion;
import venta.ce.reference.VariablesCajaElectronica;
import venta.convenio.reference.VariablesConvenio;
import venta.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBPtoVenta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      22.02.2006   Creación<br>
 * ASOSA       13.01.2010   Modificación<br>
 * ASOSA       05.04.2010   Modificación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class DBMultiEmpresa 
{
  
  private static final Logger log = LoggerFactory.getLogger(DBMultiEmpresa.class);
  
  private static ArrayList parametros = new ArrayList();

  public DBMultiEmpresa()
  {
  }
  
    public static void cargaListaLocalesEmpresa(FarmaTableModel pTableModel) throws SQLException {
    
        //ERIOS 06.03.2013 Se agrega el parametros vCodCia
        ArrayList parametros = new ArrayList();    
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"SVB_MULTI_EMPRESA.lista_locales_empresas(?)",parametros,false);
     
    }  
  
}
