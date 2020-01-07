package svb.fact_electronica.reference;

import common.FarmaUtility;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;


import svb.imp_fe.electronico.UtilityImpCompElectronico;

import venta.caja.reference.VariablesCaja;

import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.reference.UtilityPtoVenta;

public class UtilityFactElectronica {
    public UtilityFactElectronica() {
        super();
    }
    
    public static boolean isActivoFactElectronica(){
        boolean valor = false;
        try {
            if(VariableFactElectronica.isFactElectronica)
                valor =DBFactElectronica.isActFactElectronica(false);
            else
                valor =DBFactElectronica.isActFactElectronica(true);
            
        } catch (SQLException e) {
            valor = false;
        }
        return valor;
    }   
    
    public static String imp_comp_electronicos_pedido(String pNumPedVta,String pTipCompPago,String pNumComprobante,String pSecCompPago){
        
        String pSinImprimir = "S";
        
        pSinImprimir = "N";

        try {
            String ruta = UtilityPtoVenta.obtieneDirectorioComprobantes();
            
            Date vFecImpr = new Date();
            String fechaImpresion;

            String DATE_FORMAT = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            // log.debug("Today is " + sdf.format(vFecImpr));
            fechaImpresion = sdf.format(vFecImpr);
            
            
            if(pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA)){
                ruta = ruta + fechaImpresion + "_" + "B_E_" +pNumPedVta + "_" + pNumComprobante + ".TXT";
            }
            if(pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
                ruta = ruta + fechaImpresion + "_" + "F_E_" + pNumPedVta+ "_" + pNumComprobante + ".TXT";
            }
            
            if(pTipCompPago.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_NOTA_CREDITO)){
                ruta = ruta + fechaImpresion + "_" + "NC_E_" + pNumPedVta+ "_" + pNumComprobante + ".TXT";
            }
            
            UtilityImpCompElectronico impresionElectronico = new UtilityImpCompElectronico();
            impresionElectronico.setRutaFileTestigo(ruta);
            boolean vValor = impresionElectronico.printDocumento(pNumPedVta,pSecCompPago, false, true);
            
            if(vValor){
                
                ArrayList vListOrdenes = new ArrayList();
                DBFactElectronica.getListaOrdenes(pNumPedVta,vListOrdenes);
                if(vListOrdenes.size()>0){
                    for(int i=0;i<vListOrdenes.size();i++){
                        impresionElectronico.printDato_para_Laboratorio(pNumPedVta,pSecCompPago, false, true,
                                                                        FarmaUtility.getValueFieldArrayList(vListOrdenes,i,0));
                        
                    }
                }
                //SE INDICO QUE SE IMPRIMA COPIA DE CAJERO
                //impresionElectronico.printDato_para_Cajero(pNumPedVta,pSecCompPago, false, true);
            }

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            FarmaUtility.showMessage(new JDialog(),"Error al imprimir los comprobantes electronicos\n"+e.getMessage(),null);
        }
        
        return pSinImprimir;
        
    }
}
