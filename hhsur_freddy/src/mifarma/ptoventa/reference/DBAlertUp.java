package mifarma.ptoventa.reference;

import java.util.ArrayList;

import common.FarmaDBUtility;
import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBAlertUp {

    private final Logger log = LoggerFactory.getLogger(DBAlertUp.class);

    private ArrayList parametros = new ArrayList();

    public DBAlertUp() {
    }

    public ArrayList getAlertaMensajes() throws Exception {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "FARMA_ALERTUP.F_CUR_ALERTA_MENSAJES(?,?)",
                                                          parametros);
        return pOutParams;
    }
    
    public ArrayList getAlertaMensajesCotizacionPrecio() throws Exception {
        ArrayList pOutParams = new ArrayList();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "FARMA_ALERTUP.F_CUR_ALERTA_MSJ_COTIZ_PRECIO(?,?)",
                                                          parametros);
        return pOutParams;
    }
    
    
}
