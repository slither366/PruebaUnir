package mifarma.ptoventa.centromedico.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import common.FarmaDBUtility;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.convenio.reference.VariablesConvenio;

public class DBAtencionMedica {
    
    public DBAtencionMedica() {
        super();
    }
    
    public static ArrayList obtenerListadoPacientesEspera(String pCodGrupoCia, String pCodEstado, String pCodMedico,
                                                          String pConsultorio,String pBus) throws SQLException {
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodEstado);
        parametros.add(pCodMedico);
        parametros.add(pConsultorio);
        parametros.add(pBus);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado,
                                                          " CENTRO_MEDICO.F_LISTA_ESPERA(?,?,?,?,?)", parametros);
        return resultado;
    }
    
    public static ArrayList obtenerListaTipoInformante()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " CENTRO_MEDICO.F_LST_CMB_TIPO_INFORMANTE", parametros);
        return resultado;
    }
    
    public static ArrayList obtenerListaEstadoFuncionesBiologicas()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " CENTRO_MEDICO.F_LST_CMB_FUNC_BIOLOGICAS", parametros);
        return resultado;
    }
    
    public static ArrayList obtenerListaEstadoGeneralExaFisico()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " CENTRO_MEDICO.F_LST_CMB_EXA_FISICO_ESTADO", parametros);
        return resultado;
    }
    
    public static ArrayList obtenerListaTipoDiagnostico()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " CENTRO_MEDICO.F_LST_CMB_TIPO_DIAGNOSTICO", parametros);
        return resultado;
    }
    
    public static ArrayList obtenerListaViaAdministracion()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " CENTRO_MEDICO.F_LST_CMB_VIA_ADMINISTRACION", parametros);
        return resultado;
    }
    
    public static List obtenerListaDiagnostico()throws Exception{
        ArrayList parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureListMap(" CENTRO_MEDICO.F_LISTAR_DIAGNOSTICO", parametros);
    }
    
    public static List obtenerProductoReceta()throws Exception{
        ArrayList parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureListMap(" CENTRO_MEDICO.F_LISTAR_DIAGNOSTICO", parametros);
    }
    
    public static String grabarAnexos(
            String pObsAnexo,
            String pRutaLocal,
            String pRutaServidor,
            String pNomFile,
            String pExtFile,
            String pNumAtenMed
        )throws SQLException {
        
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pObsAnexo);
        parametros.add(pRutaLocal);
        parametros.add(pRutaServidor);
        parametros.add(pNomFile);
        parametros.add(pExtFile);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pNumAtenMed);

        String nomAnexo = FarmaDBUtility.executeSQLStoredProcedureStr("HHSUR_CME_FILE_ANEXO.F_GRABAR_ANEXOS(?,?,?,?,?,?,?,?,?,?)", parametros);
        
        return nomAnexo;
    }
    
    public static void errorGrabarAnexo(
                            String pCodAnexo,
                            String pObsAnexo,
                            String pRutaLocal,
                            String pRutaServidor,
                            String pNomFile,
                            String pExtFile
        ) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodAnexo);
        parametros.add(pObsAnexo);
        parametros.add(pRutaLocal);
        parametros.add(pRutaServidor);
        parametros.add(pNomFile);
        parametros.add(pExtFile);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,"HHSUR_CME_FILE_ANEXO.P_MOVER_ANEXOS_ERROR(?,?,?,?,?,?,?,?,?)",parametros,false);
    }
    
    public static void cambiarEstadoAnexo(String pCodAnexo) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodAnexo);
        parametros.add(FarmaVariables.vIdUsu);
        FarmaDBUtility.executeSQLStoredProcedure(null,"HHSUR_CME_FILE_ANEXO.P_CAMBIAR_ESTADO_ANEXO(?,?,?,?)",parametros,false);
    }
    
    public static ArrayList obtenerAccesoFTPAnexos()throws Exception{
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado, " HHSUR_CME_FILE_ANEXO.OBTENER_ACCESO_FTP(?,?)", parametros);
        return resultado;
    }
    
    
    public static ArrayList obtieneCodAnexo() throws SQLException 
    {
        ArrayList resultado = new ArrayList();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(resultado,"HHSUR_CME_FILE_ANEXO.OBTIENE_ULTIMO_ANEXO_GRABADO(?,?)", parametros);
        
        return resultado;
    }
    
    public static String obtieneDescripcionDocumento(String pTipDoc) throws SQLException 
    {
        String resultado = "";
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipDoc);
        resultado = FarmaDBUtility.executeSQLStoredProcedureStr("HHSUR_CME_FILE_ANEXO.OBTENER_DESC_TIP_DOC(?,?,?)", parametros);
        
        return resultado;
    }
    
    public static String obtieneEdad(String pFecNac) throws SQLException 
    {
        String resultado = "";
        ArrayList parametros = new ArrayList();
        parametros.add(pFecNac);
        resultado = FarmaDBUtility.executeSQLStoredProcedureStr("edades(?)", parametros);
        
        return resultado;
    }
    
    public static void insertarNewTipDoc(String pDescDocumento) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(pDescDocumento);
        
        //FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CME_ADM.CME_INSERT_TIP_DOCUMENTO(?,?,?)",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CME_ADM.CME_INSERT_TIP_DOCUMENTO(?,?,?)",parametros,false);
    }
    public static void insertarDatosMedico(/*String nCmp, String codMed, String Nombres, String Apellidos ,String Direc, String sexo,String fecNaci, String docIden*/) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add("00000");
        parametros.add("9000099999");
        parametros.add("asdasd");
        parametros.add("asdasdsa");
        parametros.add("asdasdas");
        parametros.add("1");
        parametros.add("08/03/2000");
        parametros.add("74129984");

        FarmaDBUtility.executeSQLStoredProcedureStr("HHC_MEDICO.P_GRABA_NUEVO_MEDI(?,?,?,?,?,?,?,?,?,?)",parametros);
    }
        
}
