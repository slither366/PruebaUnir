package cilator.mantenimiento.reference;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBMantenimiento {
    private static final Logger log = LoggerFactory.getLogger(DBMantenimiento.class);

    private static ArrayList parametros = new ArrayList();

    public DBMantenimiento() {
    }

    public static void getListaContactos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_CONT.LISTA_CONTACTOS(?,?)", parametros,
                                                 false);
    }

    public static void getContactoId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_CONT.GET_CONTACTO(?)", parametros);
    }

    public static void grabaContacto(String pDNI, String pNombre, String pPaterno, String pMaterno, String pNacimiento,
                                     String pDireccion, String pSexo, String pCargo, String pEmail, String pTelefono,
                                     String pCelular_Uno, String pCelular_Dos, boolean pInsert,
                                     boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pDNI);
        parametros.add(pNombre);
        parametros.add(pPaterno);
        parametros.add(pMaterno);
        parametros.add(pNacimiento);
        parametros.add(pDireccion);
        parametros.add(pSexo);
        parametros.add(pCargo);
        parametros.add(pEmail);
        parametros.add(pTelefono);
        parametros.add(pCelular_Uno);
        parametros.add(pCelular_Dos);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        log.info("PTOVENTA_ADMIN_CONT.CONT_ACCION_I_U(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_ADMIN_CONT.CONT_ACCION_I_U(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    ///////////////////////////////////////////////
    public static void getListaTipos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_PROD.LISTA_TIPOS(?,?)", parametros,
                                                 false);
    }

    public static void getTipoId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_PROD.GET_TIPO(?)", parametros);
    }

    public static void grabaTipo(String pTipo, String pIdTipo, boolean pInsert, boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipo);
        parametros.add(pIdTipo);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        log.info("PTOVENTA_ADMIN_PROD.TIPO_ACCION_I_U(?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_PROD.TIPO_ACCION_I_U(?,?,?,?,?,?)", parametros,
                                                 false);
    }
    
    //////////////////////////////////////////////////////////////////////////////
    public static void getListaMARCAs(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_PROD.LISTA_MARCAS(?,?)", parametros,
                                                 false);
    }

    public static void getMARCAId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_PROD.GET_MARCA(?)", parametros);
    }

    public static void grabaMARCA(String pMARCA, String pIdMARCA, boolean pInsert, boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pMARCA);
        parametros.add(pIdMARCA);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        log.info("PTOVENTA_ADMIN_PROD.MARCA_ACCION_I_U(?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_PROD.MARCA_ACCION_I_U(?,?,?,?,?,?)", parametros,
                                                 false);
    }    
    //////////////////////////////////////////////////////////////////////////////
    
    public static void getListaCATEGORIAs(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_PROD.LISTA_CATEGORIAS(?,?)", parametros,
                                                 false);
    }

    public static void getCATEGORIAId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_PROD.GET_CATEGORIA(?)", parametros);
    }

    public static void grabaCATEGORIA(String pCATEGORIA, String pIdCATEGORIA, boolean pInsert, boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCATEGORIA);
        parametros.add(pIdCATEGORIA);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        log.info("PTOVENTA_ADMIN_PROD.CATEGORIA_ACCION_I_U(?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_PROD.CATEGORIA_ACCION_I_U(?,?,?,?,?,?)", parametros,
                                                 false);
    }    
    //////////////////////////////////////////////////////////////////////////////   
    public static void getListaSubCATEGORIAs(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_PROD.LISTA_SUB_CATEGORIAS(?,?)", parametros,
                                                 false);
    }

    public static void getSubCATEGORIAId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_PROD.GET_CATEGORIA(?)", parametros);
    }

    public static void grabaSubCATEGORIA(String pIdCategoria,String pSubCATEGORIA, String pIdSubCATEGORIA, boolean pInsert, boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pIdCategoria);
        parametros.add(pSubCATEGORIA);
        parametros.add(pIdSubCATEGORIA);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        log.info("PTOVENTA_ADMIN_PROD.SUB_CATEGORIA_ACCION_I_U(?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_PROD.SUB_CATEGORIA_ACCION_I_U(?,?,?,?,?,?,?)", parametros,
                                                 false);
    }        
    
    //////////////////////////////////////////////////////////////////////////////   
    public static void getListaProductos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_ADMIN_PROD.VTA_LISTA_PROD(?,?)", parametros,
                                                 false);
    }

    public static void getProducto(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PTOVENTA_ADMIN_PROD.GET_PRODUCTO(?)", parametros);
    }

    public static void grabaPRODUCTO(String pDescripcion,
                                     String pDescAuxiliar,
                                     String pCodBarra,
                                     String pCtdStkMinimo,
                                     String pCosto,
                                     String pPrecio,
                                     String vIsFracc,
                                     String pCtdFraccion,
                                     String pCtdStkMaximo,
                                     String pTipo,
                                     String pMarca,
                                     String pCategoria,
                                     String pSubCategoria,
                                     String  pCodProd,
                                     boolean pInsert, 
                                     boolean pUpdate,
                                     String  pIndTipoConsumo) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDescripcion);
        parametros.add(pDescAuxiliar);
        parametros.add(pCodBarra);
        
        parametros.add(pCtdStkMinimo);
        parametros.add(pCtdStkMaximo);
        parametros.add(pCosto);
        parametros.add(pPrecio);
        parametros.add(vIsFracc);
        
        parametros.add(pCtdFraccion);
        parametros.add(pTipo);
        parametros.add(pMarca);
        parametros.add(pCategoria);
        parametros.add(pSubCategoria);
        
        parametros.add(pCodProd);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        parametros.add(pIndTipoConsumo);
        log.info("PTOVENTA_ADMIN_PROD.PRODUCTO_ACCION_I_U(" +
                 "?,?,?,?,?," +
                 "?,?,?,?,?," +
                 "?,?,?,?,?," +
                 "?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_PROD.PRODUCTO_ACCION_I_U(" +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?"+
                                                 ")", parametros,
                                                 false);
    }       

    public static void listaRecetas(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.info("PKG_ADMCENTRAL_RECETAS.OBTENER_RECETAS(?)-->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADMCENTRAL_RECETAS.OBTENER_RECETAS(?)", parametros,
                                                 false);
    }
    
    public static void listaComponentesDisponiblesParaReceta(FarmaTableModel pTableModel, String codMaterial, String descComponente) throws SQLException {
        pTableModel.clearTable();
        
        if(descComponente == null || descComponente.isEmpty()){
            descComponente = "%";
        }else{
            descComponente = "%"+descComponente+"%";
        }
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codMaterial);
        parametros.add(descComponente);        
        log.info("PKG_ADMCENTRAL_RECETAS.OBTENER_COMPTES_DISP_RECETA(?,?,?)-->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADMCENTRAL_RECETAS.OBTENER_COMPTES_DISP_RECETA(?,?,?)", parametros,
                                                 false);
    }
    
    public static void listaComponentesPorReceta(FarmaTableModel pTableModel, String codMaterial) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codMaterial);
        log.info("PKG_ADMCENTRAL_RECETAS.OBTENER_COMPTES_RECETA(?,?)-->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADMCENTRAL_RECETAS.OBTENER_COMPTES_RECETA(?,?)", parametros,
                                                 false);
    }
    
    private static void agregarComponenteReceta(ComponenteReceta cr) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(cr.getReceta().getCodProducto());
        parametros.add(cr.getCodComponente());
        parametros.add(FarmaVariables.vIdUsu);       
        parametros.add(cr.getCantidad());
        parametros.add(cr.getValorFraccion());
        parametros.add(cr.getIndVtaStock());
        log.info("PKG_ADMCENTRAL_RECETAS.AGREGAR_COMPONENTE(?,?,?,?,?,?,?)-->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PKG_ADMCENTRAL_RECETAS.AGREGAR_COMPONENTE(?,?,?,?,?,?,?)", parametros, false);
    }
    
    private static void eliminarComponentesReceta(Receta receta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(receta.getCodProducto());
        log.info("PKG_ADMCENTRAL_RECETAS.ELIMINAR_COMPTES_RECETA(?,?)-->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PKG_ADMCENTRAL_RECETAS.ELIMINAR_COMPTES_RECETA(?,?)", parametros, false);
    }

    
    public static void guardarListaComponentesReceta(Receta receta) throws SQLException {
        log.info("REGISTRO DE COMPONENTES INICIADO");
        eliminarComponentesReceta(receta);
        List<ComponenteReceta> componentes = receta.getListaComponentes();
        for(ComponenteReceta cr : componentes){
            agregarComponenteReceta(cr);
        }
        log.info("REGISTRO DE COMPONENTES FINALIZADO");
    }        
    //MANTENIMIENTO MEDICO X ESPEC
    public static void getListaConsultorio(FarmaTableModel tblModelConsultorio, String vCMP) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(vCMP);
    
      FarmaDBUtility.executeSQLStoredProcedure(tblModelConsultorio,"HHC_MEDICO.F_LISTA_CONSUL(?,?,?)", parametros, false);
    }
    public static void getListaMedicos(FarmaTableModel tblModelMedicos, String estado) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(estado);
      FarmaDBUtility.executeSQLStoredProcedure(tblModelMedicos,"hhc_medico.f_lista_medicos(?,?,?)", parametros, false);
    }
    //MANTENIMIENTO CONSUL X ESPEC
   
    //GRABAR CONSULTORIO X MEDICO
    public static void insertarConsulMedico(String pNumCmp, String pCodMed, String pCodConsul, String pIdBus, String pBus) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(pNumCmp);
        parametros.add(pCodMed);
        parametros.add(pCodConsul);
        parametros.add(pIdBus);
        parametros.add(pBus);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_MEDICO.P_GRABA_CONSUL_MEDI(?,?,?,?,?,?,?)",parametros,false);
    }
    public static void ConsulMedicoBorrar(String pCodMed, String pIdBus) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(pCodMed);
        parametros.add(pIdBus);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,"HHC_MEDICO.P_ELIMINA_CONSUL_MEDI(?,?,?,?)",parametros,false);
    }
    
    public static void getListConsultoriosEspec(FarmaTableModel modelConsultorio, String vESPEC) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vESPEC);
        FarmaDBUtility.executeSQLStoredProcedure(modelConsultorio, "HHC_MEDICO.F_LISTA_CONSUL_DISP(?,?,?)", parametros,
                                                 false);
    }
}
