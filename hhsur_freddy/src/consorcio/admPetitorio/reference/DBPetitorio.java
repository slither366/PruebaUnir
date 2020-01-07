package consorcio.admPetitorio.reference;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import oracle.sql.CHAR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBPetitorio {

    private static final Logger log = LoggerFactory.getLogger(DBPetitorio.class);
    private static ArrayList parametros = new ArrayList();
        
    
    public DBPetitorio() {
        super();
    }

    public static void getListaPrincipioActivo(ArrayList pTableModel) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando a HHC_PETITORIO.F_LISTA_PRIN_ACTIVO(?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,"HHC_PETITORIO.F_LISTA_PRIN_ACTIVO(?,?)",parametros);
    }    
    
    public static String getDatosMedico(String pCodMedico) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodMedico);
      log.debug("invocando a HHC_PETITORIO.F_GET_MEDICO(?,?,?):"+parametros);
     return FarmaDBUtility.executeSQLStoredProcedureStr("HHC_PETITORIO.F_GET_MEDICO(?,?,?)",parametros);
    } 
    
    public static String setCreaNuevoPetitorioCab(String pCodMedico) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodMedico);
      parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a HHC_PETITORIO.F_GRABA_PET_CAB(?,?,?,?):"+parametros);
     return FarmaDBUtility.executeSQLStoredProcedureStr("HHC_PETITORIO.F_GRABA_PET_CAB(?,?,?,?)",parametros);
    } 
    
    public static void setActualizaPetitorioCab(String pCodMedico, String pCodPetitorio,
                                                  String pTurnoSem, String pAdjOtros,
                                                  String cLaboratorio) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodMedico);
      parametros.add(pCodPetitorio);
      parametros.add(pTurnoSem);
      parametros.add(pAdjOtros);
      parametros.add(cLaboratorio);
      log.debug("invocando a HHC_PETITORIO.F_ACTUALIZA_PET_CAB(?,?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.F_ACTUALIZA_PET_CAB(?,?,?,?,?,?,?)",parametros,false);
    } 
    
    public static void creaPetitorioPrincipio(String pCodPetitorio,
                                                String pCodPrincipio) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
      parametros.add(pCodPrincipio);
      parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a HHC_PETITORIO.P_GRABA_PET_PRIN(?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.P_GRABA_PET_PRIN(?,?,?,?,?)",parametros,false);
    }
    
    public static void creaDetallePetitorio(String pCodPetitorio,
                                            String pCodPrincipio,
                                            String pCodProd,
                                            String pCantTurno) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
      parametros.add(pCodPrincipio);
      parametros.add(pCodProd);
      parametros.add(pCantTurno);
      parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a HHC_PETITORIO.P_GRABA_PET_PRIN_DET(?,?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.P_GRABA_PET_PRIN_DET(?,?,?,?,?,?,?)",parametros,false);
    }

    public static void getListaPetitorioPrincipioActivo(ArrayList pTableModel,
                                                        String pCodPetitorio) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
      log.debug("invocando a HHC_PETITORIO.F_LISTA_PET_PRINC(?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,"HHC_PETITORIO.F_LISTA_PET_PRINC(?,?,?)",parametros);
    }
    
    public static void inactivaPetitorio(String pCmpMedico, String pCodPetitorio) throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCmpMedico);
        parametros.add(pCodPetitorio);
        parametros.add(FarmaVariables.vIdUsu);
        //--
        log.debug("invocando a HHC_PETITORIO.P_INACTIVA_PETITORIO(?,?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.P_INACTIVA_PETITORIO(?,?,?,?,?)",parametros,false);
    }
    
    public static void eliminaPrincipioPetitorio(String pCodPetitorio,
                                                String pCodPrincipio) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
        parametros.add(pCodPrincipio);
      parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a HHC_PETITORIO.P_ELIMINA_PET_PRIN(?,?,?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.P_ELIMINA_PET_PRIN(?,?,?,?,?)",parametros,false);
    }
    
    public static void eliminaProdDetallePetitorio(String pCodPetitorio,
                                                   String pCodPrincipio,
                                                   String pCodProd) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
      parametros.add(pCodPrincipio);
      parametros.add(pCodProd);
      log.debug("invocando a HHC_PETITORIO.P_ELIMINA_PET_PRIN_DET(?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_PETITORIO.P_ELIMINA_PET_PRIN_DET(?,?,?,?,?)",parametros,false);
    }    
    
    public static void getListaPetitorioMedico(ArrayList pTableModel,
                                               String pCodMedico) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodMedico);
      log.debug("invocando a HHC_PETITORIO.F_LISTA_PETITORIO_CAB(?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,"HHC_PETITORIO.F_LISTA_PETITORIO_CAB(?,?,?)",parametros);
    } 
    
    public static void getListaProductos(ArrayList pTableModel, String pCodPetitorio,
                                         String pCodPrincipio, String pFiltxTurno) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodPetitorio);
      parametros.add(pCodPrincipio);
      parametros.add(pFiltxTurno);
      log.debug("invocando a HHC_PETITORIO.F_LISTA_PPROD_PRINCIPIO(?,?,?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedureArrayList(pTableModel,"HHC_PETITORIO.F_LISTA_PPROD_PRINCIPIO(?,?,?,?,?)",parametros);
    } 
    
    public static void getListaSustitutos(FarmaTableModel pTableModel,
                                          String pCodProd) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodProd);
      log.debug("invocando a HHC_PETITORIO.F_LISTA_PPROD_SUSTITUTO(?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"HHC_PETITORIO.F_LISTA_PPROD_SUSTITUTO(?,?,?)",parametros,false);
    } 
}
