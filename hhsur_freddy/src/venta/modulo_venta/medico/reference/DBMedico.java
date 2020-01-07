package venta.modulo_venta.medico.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import common.FarmaDBUtility;
import common.FarmaTableModel;

import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBMedico {
    private static final Logger log = LoggerFactory.getLogger(DBMedico.class);    
    
    public DBMedico() {
        super();
    }

    public static void grabaMedicoPedido(String vCMP,String pNombre,
                                         String pApePat,String pApeMat,
                                         String pIdRef,String pDescRef,
                                         String pCodVisitador,String pNombreVisitador) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(vCMP);
        parametros.add(pNombre);
        parametros.add(pApePat);
        parametros.add(pApeMat);
        parametros.add(pIdRef);
        parametros.add(pDescRef);
        if(pCodVisitador.trim().length()>0)
            parametros.add(pCodVisitador);
        else
            parametros.add("X");
        if(pNombreVisitador.trim().length()>0)
            parametros.add(pNombreVisitador);
        else
            parametros.add("X");
        log.debug("invocando  a PTOVENTA_MEDICO.GRABA_MEDICO(????):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MEDICO.GRABA_MEDICO(?,?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    
    public static void listaTodosMedicos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        log.debug("invocando  a PTOVENTA_MEDICO.LISTA_TODOS_MEDICOS:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                                 "PTOVENTA_MEDICO.LISTA_TODOS_MEDICOS", parametros,
                                                 false);
    }
    
    public static void getMedicoCMP(ArrayList pLista,String pCmp) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCmp.trim());
        log.debug("invocando  a PTOVENTA_MEDICO.GET_MEDICO_CMP(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_MEDICO.GET_MEDICO_CMP(?)", parametros);
    }
    
    
    public static void listaBusquedaMedicos(FarmaTableModel pTableModel, 
                                            String pCadenaBusqueda) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(pCadenaBusqueda);
        log.debug("invocando  a PTOVENTA_MEDICO.GET_FILTRO_MEDICO_CADENA(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MEDICO.GET_FILTRO_MEDICO_CADENA(?)", parametros,
                                                 false);
    }
    
    public static boolean getIsCMP(String pTipoBusqueda){
        try {
            int vValor = Integer.parseInt(pTipoBusqueda.trim());
            return true;
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            //nfe.printStackTrace();
            return false;
        }
    }
    
    
    public static void getPacienteDNI(ArrayList pLista,String pDNI) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pDNI.trim());
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando  a PTOVENTA_PACIENTE.GET_PACIENTE_DNI(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_PACIENTE.GET_PACIENTE_DNI(?,?,?)", parametros);
    }
    

    public static void grabaPaciente(String vCMP,String pNombre,
                                     String pApePat,String pApeMat,String pFechaNac, String pSexo) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(vCMP);
        parametros.add(pNombre);
        parametros.add(pApePat);
        parametros.add(pApeMat);
        parametros.add(pFechaNac);
        parametros.add(pSexo);
        parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invocando  a PTOVENTA_PACIENTE.GRABA_PACIENTE(????):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PACIENTE.GRABA_PACIENTE(?,?,?,?,?,?,?,?)", parametros,
                                                 false);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    
    public static void grabaTecnologoPedido(String vCMP,String pNombre,
                                         String pApePat,String pApeMat) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(vCMP);
        parametros.add(pNombre);
        parametros.add(pApePat);
        parametros.add(pApeMat);
        log.debug("invocando  a PTOVENTA_MEDICO.GRABA_TECNOLOGO(????):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MEDICO.GRABA_TECNOLOGO(?,?,?,?)", parametros,
                                                 false);
    }

    
    public static void listaTodosTecnologos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        log.debug("invocando  a PTOVENTA_MEDICO.LISTA_TODOS_TECNOLOGOS:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MEDICO.LISTA_TODOS_TECNOLOGOS", parametros,
                                                 false);
    }
    
    public static void getTecnologoDNI(ArrayList pLista,String pCmp) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCmp.trim());
        log.debug("invocando  a PTOVENTA_MEDICO.GET_TECNOLOGO_DNI(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_MEDICO.GET_TECNOLOGO_DNI(?)", parametros);
    }
    
    
    public static void listaBusquedaTecnologos(FarmaTableModel pTableModel, 
                                            String pCadenaBusqueda) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(pCadenaBusqueda);
        log.debug("invocando  a PTOVENTA_MEDICO.GET_FILTRO_TECNOLOGO_CADENA(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MEDICO.GET_FILTRO_TECNOLOGO_CADENA(?)", parametros,
                                                 false);
    }
    /*
    FUNCTION vta_busca_medico(cCodigo_in IN CHAR,
                                  cMatriculaApe_in IN CHAR,
                                  --cApellido_in  IN CHAR,
                                  cTipoBusqueda_in IN CHAR)
        RETURN FarmaCursor
        IS
          curVta FarmaCursor;
          BEGIN
            IF (Ctipobusqueda_In = '1') THEN -- codigo de medico matriculas
               OPEN curVta FOR
               --SELECT md.cdg_apm || 'Ã' ||
               --       md.matricula || 'Ã' ||
               --       md.nombre
               --FROM vta_mae_medico md
               --WHERE --md.cdg_apm = Ccodigo_In
               --      --AND
               --      md.matricula = CmatriculaApe_in;
               SELECT decode(COD_TIPO_COLEGIO,'01','CMP','02','ODO','03','OBS','CMP') || 'Ã' ||
                      me.num_cmp || 'Ã' ||
                      me.des_nom_medico || ' ' || me.des_ape_medico
               FROM mae_medico me
               WHERE me.num_cmp = CmatriculaApe_in;
            ELSIF (Ctipobusqueda_In = '2') THEN -- apellido
            DBMS_OUTPUT.PUT_LINE('ENTRO AL 2');
               OPEN curVta FOR
               --SELECT md.cdg_apm || 'Ã' ||
               --       md.matricula || 'Ã' ||
               --       md.nombre
               --FROM vta_mae_medico md
               --WHERE md.nombre LIKE '%'|| cMatriculaApe_in ||'%';
               SELECT decode(COD_TIPO_COLEGIO,'01','CMP','02','ODO','03','OBS','CMP') || 'Ã' ||
                      me.num_cmp || 'Ã' ||
                      me.des_nom_medico || ' ' || me.des_ape_medico
               FROM mae_medico me
               WHERE me.des_nom_medico LIKE '%'|| cMatriculaApe_in ||'%'
                     or me.des_ape_medico LIKE '%'|| cMatriculaApe_in ||'%';
           END IF;
         RETURN curVta;
      END;
     * */
    public static void grabaVisitadorPedido(String vCMP,String pNombre) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(vCMP);
        parametros.add(pNombre);
        log.debug("invocando  a PTOVENTA_MEDICO.GRABA_VISITADOR(??):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_MEDICO.GRABA_VISITADOR(?,?)", parametros,
                                                 false);
    }

    
    
    public static void listaTodosVisitador(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        log.debug("invocando  a PTOVENTA_MEDICO.LISTA_TODOS_VISITADOR:" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MEDICO.LISTA_TODOS_VISITADOR", parametros,
                                                 false);
    }
    
    public static void getVisitadorDNI(ArrayList pLista,String pCmp) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pCmp.trim());
        log.debug("invocando  a PTOVENTA_MEDICO.GET_TECNOLOGO_COD_VISITADOR(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_MEDICO.GET_TECNOLOGO_COD_VISITADOR(?)", parametros);
    }
    
    
    public static void listaBusquedaVisitador(FarmaTableModel pTableModel, 
                                            String pCadenaBusqueda) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(pCadenaBusqueda);
        log.debug("invocando  a PTOVENTA_MEDICO.GET_FILTRO_VISITADOR_CADENA(?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_MEDICO.GET_FILTRO_VISITADOR_CADENA(?)", parametros,
                                                 false);
    }
}
