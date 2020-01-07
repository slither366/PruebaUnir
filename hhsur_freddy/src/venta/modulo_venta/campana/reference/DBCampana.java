package venta.modulo_venta.campana.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaDBUtilityRemoto;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.recetario.reference.VariablesRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.VariablesCaja;


public class DBCampana
{
  private static final Logger log = LoggerFactory.getLogger(DBCampana.class);
  
  private static ArrayList parametros = new ArrayList();
  private static List prmts = new ArrayList();

  public DBCampana(){
  }
  
    public static void listaCampanasVigentes(FarmaTableModel pTableModel) throws SQLException {
      pTableModel.clearTable();
      ArrayList prmtros = new ArrayList();
        prmtros.add(FarmaVariables.vCodGrupoCia);
        prmtros.add(FarmaVariables.vCodLocal);
        prmtros.add(VariablesFidelizacion.vNumTarjeta.trim());
        //datos de forma de PAGO
        prmtros.add(VariablesFidelizacion.vIndUsoEfectivo.trim());
        prmtros.add(VariablesFidelizacion.vIndUsoTarjeta.trim());
        prmtros.add(VariablesFidelizacion.vCodFPagoTarjeta.trim());
      
      log.debug("invocando a SVB_PROMOCION.FID_F_CUR_CAMP_X_TARJETA_NEW(?,?,?,?,?,?):"+prmtros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"SVB_PROMOCION.FID_F_CUR_CAMP_X_TARJETA_NEW(?,?,?,?,?,?)",prmtros,true);
    }
    
}
