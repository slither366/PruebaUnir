package venta.campana.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import common.FarmaDBUtility;
import common.FarmaTableModel;

import common.FarmaVariables;

import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.VariablesConvenio;

import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBCampana {

    private static final Logger log = LoggerFactory.getLogger(DBConvenio.class);
    
    private static ArrayList parametros = new ArrayList();

    public DBCampana() {
    }
    
    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  22.08.08
     * @param pTableModel
     * @throws SQLException
     */
    public static void listaCamposCampana(FarmaTableModel pTableModel) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(VariablesCampana.vCodCampana);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                "PTOVENTA_CAMP.CAMP_LISTA_CAMPANA_CAMPOS(?)",parametros,false);
    }
    
    /**
     * @Author Daniel Fernando Veliz La Rosa
     * @Since  22.08.08
     * @param pDniCliente
     * @param pNomcliente
     * @param pApePatCliente
     * @param pApeMatCliente
     * @param pTelefono
     * @param pNumCMP
     * @param pMedico
     * @param pSexo
     * @param pFecNacimiento
     * @throws SQLException
     */
    public static void agregarClienteCampana(String pCodCampana,
                                             String pCodCupon,
                                             String pCodCli,
                                             String pDniCliente,
                                             String pNomcliente,
                                             String pApePatCliente,
                                             String pApeMatCliente,
                                             String pTelefono,
                                             String pNumCMP,
                                             String pMedico,
                                             String pSexo, 
                                             String pFecNacimiento) throws SQLException
    {
      parametros = new ArrayList();
      
      //Codigos
      parametros.add(VariablesCampana.vCodGrupoCia);
      parametros.add(pCodCampana);
      parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodCupon);
      parametros.add(pCodCli);
      
      //Datos del cliente
      parametros.add(pDniCliente);
      parametros.add(pNomcliente);
      parametros.add(pApePatCliente);
      parametros.add(pApeMatCliente);
      parametros.add(pTelefono);
      parametros.add(pNumCMP);
      parametros.add(pMedico);
      parametros.add(pSexo);
      parametros.add(pFecNacimiento);
      
      //Usuario creador
       parametros.add(FarmaVariables.vNomUsu);
      FarmaDBUtility.executeSQLStoredProcedure(null,
         "PTOVENTA_CAMP.CAMP_AGREGAR_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
         ,parametros,false);
    }
}
