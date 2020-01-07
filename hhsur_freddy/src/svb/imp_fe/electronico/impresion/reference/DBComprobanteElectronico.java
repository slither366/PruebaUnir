package svb.imp_fe.electronico.impresion.reference;

import common.FarmaDBUtility;
import common.FarmaVariables;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBComprobanteElectronico {

    private static final Logger log = LoggerFactory.getLogger(DBComprobanteElectronico.class);

    public static List getDatosImpresion_Comprobante(String pVersionFV, String numPedidoVta, String secCompPago,
                                         boolean isReimpresion) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        parametros.add(secCompPago);
        parametros.add(pVersionFV);
        if (isReimpresion)
            parametros.add("S");
        else
            parametros.add("N");
        /*if(VariablesPuntos.frmPuntos!=null){ //validacion cuando el objete no fue construido ltavara 01/04/2015
            parametros.add((VariablesPuntos.frmPuntos.getBeanTarjeta() == null) ? 0 : VariablesPuntos.frmPuntos.getBeanTarjeta().getAhorroTotal());    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }else{
            parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }*/
        parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        //KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
        /*if(VariablesPuntos.frmPuntos != null){
            if(VariablesPuntos.frmPuntos.getBeanTarjeta() != null){
                if(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni()!=null){
                    parametros.add(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni());
                }else{
                    parametros.add(" ");
                }
            }else{
                parametros.add(" ");
            }
        }else{
            parametros.add(" ");
        }*/
        parametros.add(" ");
        //log.info("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)" + parametros);
        //return FarmaDBUtility.executeSQLStoredProcedureListMap("FARMA_EPOS.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)", parametros);
        
            
        log.info("HHC_IMP_ELECTRONICO.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("HHC_IMP_ELECTRONICO.IMP_COMP_ELECT(?,?,?,?,?,?,?,?)", parametros);

    }

    public static String getMarcaLocal(String pCodGrupoCia, String pCodLocal) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(pCodGrupoCia);
        parametros.add(pCodLocal);
        log.info("FARMA_EPOS.GET_MARCA_LOCAL(?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_EPOS.GET_MARCA_LOCAL(?,?)", parametros);
    }
    
    public static List getDatosImpresion_Comp_Laboratorio(String pVersionFV, String numPedidoVta, String secCompPago,
                                         boolean isReimpresion,
                                                          String pNumOrdenVta) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        parametros.add(secCompPago);
        parametros.add(pVersionFV);
        if (isReimpresion)
            parametros.add("S");
        else
            parametros.add("N");
        /*if(VariablesPuntos.frmPuntos!=null){ //validacion cuando el objete no fue construido ltavara 01/04/2015
            parametros.add((VariablesPuntos.frmPuntos.getBeanTarjeta() == null) ? 0 : VariablesPuntos.frmPuntos.getBeanTarjeta().getAhorroTotal());    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }else{
            parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }*/
        parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        //KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
        /*if(VariablesPuntos.frmPuntos != null){
            if(VariablesPuntos.frmPuntos.getBeanTarjeta() != null){
                if(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni()!=null){
                    parametros.add(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni());
                }else{
                    parametros.add(" ");
                }
            }else{
                parametros.add(" ");
            }
        }else{
            parametros.add(" ");
        }*/
        parametros.add(" ");
        parametros.add(pNumOrdenVta);
        log.info("HHC_IMP_ELECTRONICO.IMP_COMP_LABORATORIO(?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("HHC_IMP_ELECTRONICO.IMP_COMP_LABORATORIO(?,?,?,?,?,?,?,?,?)", parametros);

    }
    
    public static List getDatosImpresion_Comp_Cajero(String pVersionFV, String numPedidoVta, String secCompPago,
                                         boolean isReimpresion) throws Exception {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(numPedidoVta);
        parametros.add(secCompPago);
        parametros.add(pVersionFV);
        if (isReimpresion)
            parametros.add("S");
        else
            parametros.add("N");
        /*if(VariablesPuntos.frmPuntos!=null){ //validacion cuando el objete no fue construido ltavara 01/04/2015
            parametros.add((VariablesPuntos.frmPuntos.getBeanTarjeta() == null) ? 0 : VariablesPuntos.frmPuntos.getBeanTarjeta().getAhorroTotal());    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }else{
            parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        }*/
        parametros.add(0);    //ASOSA - 24/03/2015 - PTOSYAYAYAYA - CONSECUENCIAS DE REPETIDOS CAMBIOS.
        //KMONCADA 23.06.2015 NUMERO DE DOCUMENTO DE LA TARJETA DE PUNTOS
        /*if(VariablesPuntos.frmPuntos != null){
            if(VariablesPuntos.frmPuntos.getBeanTarjeta() != null){
                if(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni()!=null){
                    parametros.add(VariablesPuntos.frmPuntos.getBeanTarjeta().getDni());
                }else{
                    parametros.add(" ");
                }
            }else{
                parametros.add(" ");
            }
        }else{
            parametros.add(" ");
        }*/
        parametros.add(" ");
        log.info("HHC_IMP_ELECTRONICO.IMP_COMP_CAJERO(?,?,?,?,?,?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureListMap("HHC_IMP_ELECTRONICO.IMP_COMP_CAJERO(?,?,?,?,?,?,?,?)", parametros);

    }
}
