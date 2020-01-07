package svb.fact_electronica.reference;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;
import venta.caja.reference.VariablesCaja;

public class DBFactElectronica {
    
    
    private static ArrayList parametros;
    private static final Logger log = LoggerFactory.getLogger(DBFactElectronica.class);
    
    
    public DBFactElectronica() {
        super();
    }
    
    // 
    
    public static boolean isActFactElectronica(boolean vIngCompManual) throws SQLException{
      ArrayList parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      log.debug("svb_fe_cobro.is_fact_electronico_local(?,?)"+parametros);
      String pResultado = FarmaDBUtility.executeSQLStoredProcedureStr("svb_fe_cobro.is_fact_electronico_local(?,?)",parametros);
      
      if(vIngCompManual)
        return false;
      else{
          if(pResultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              return true;
          else
              return false;            
      }
      
      
      
      //return false;
    }
    
    public static int agrupaImpresionDetallePedido(String vNumPedido) throws SQLException{
      ArrayList parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(vNumPedido);
      parametros.add(FarmaVariables.vIdUsu);
      return FarmaDBUtility.executeSQLStoredProcedureInt("svb_fe_cobro.FE_AGRUPA_IMPRESION_DETALLE(?,?,?,?)",parametros);
    }
    
    public static String getObtieneSecBoleta_electronica(String vNumPedido) throws SQLException
    {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vNumPedido);
        return FarmaDBUtility.executeSQLStoredProcedureStr("svb_fe_cobro.IMP_GET_SECIMPR_BOLETA_FE(?,?,?)",parametros);
    }

    public static String getObtieneSecFac_electronica(String vNumPedVta_in) throws SQLException{
       ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);       
        parametros.add(vNumPedVta_in);
        return FarmaDBUtility.executeSQLStoredProcedureStr("svb_fe_cobro.IMP_GET_SECIMPR_FAC_FE(?,?,?)",parametros);
    }
    
    
    public static String obtieneNumCompElectronico_ForUpdate(String pNumPedVta,
                                                           String pSecCompPago,
                                                String pSecImprLocal) throws SQLException{
      ArrayList parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pNumPedVta);
      parametros.add(pSecCompPago);
      parametros.add(pSecImprLocal);
      return FarmaDBUtility.executeSQLStoredProcedureStr("svb_fe_cobro.CAJ_OBTIENE_NUM_COMP_PAGO_IMPR(?,?,?,?,?)",parametros);
    }
    
    
    public static void pOperaDatosComprobanteElectronico(String vNumPedVta_in) throws SQLException{
       ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);       
        parametros.add(vNumPedVta_in);
        FarmaDBUtility.executeSQLStoredProcedure(null,"svb_fe_cobro.SET_DATOS_COMP_ELECTRONICO(?,?,?)",parametros,false);
    }
    
    
    public static void getListaOrdenes(String vNumPedVta_in,ArrayList vLista) throws SQLException{
       ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);       
        parametros.add(vNumPedVta_in);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista,"HHC_IMP_ELECTRONICO.GET_LISTA_ORDEN_VTA(?,?,?)",parametros);
    }
    
}
