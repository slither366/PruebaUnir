package venta.ce.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Date;

import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.VariablesCaja;
import venta.ce.dao.DAOCajaElectronica;
import venta.ce.dao.DAOCajaElectronica;
import venta.ce.dao.FactoryCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FacadeCajaElectronica.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      25.03.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FacadeCajaElectronica {

    private static final Logger log = LoggerFactory.getLogger(FacadeCajaElectronica.class);
    
    private DAOCajaElectronica daoCajaElectronica;
    
    private ArrayList lstDetalle = new ArrayList();
    
    public FacadeCajaElectronica() {
        super();
        daoCajaElectronica = FactoryCajaElectronica.getDAOCajaElectronica(FactoryCajaElectronica.Tipo.MYBATIS);
    }

    public boolean grabarCambioFormaPago(String strNumPedido, ArrayList lstDetallePago, String pVuelto) {
        Date ini = new Date();
        
        lstDetalle = lstDetallePago;
        colocaVueltoDetallePago(pVuelto);
        try {
            daoCajaElectronica.respaldoFormaPagoPedido(strNumPedido);
            daoCajaElectronica.borraFormaPagoPedido(strNumPedido);
            for (int i = 0; i < lstDetallePago.size(); i++) {
                 daoCajaElectronica.grabaFormaPagoPedido(strNumPedido, 
                                                        ((ArrayList)lstDetallePago.get(i)).get(0).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(4).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(6).toString().trim(), 
                                                        VariablesCaja.vValTipoCambioPedido.toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(7).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(5).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(8).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(9).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(10).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(2).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(12).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(13).toString().trim(), 
                                                        ((ArrayList)lstDetallePago.get(i)).get(14).toString().trim()
                                                        );
            }
            daoCajaElectronica.commit();
            VariablesCajaElectronica.indExitoCambioFP = true;
            return true;
        }catch (SQLException ex){
            log.error("",ex);
            VariablesCajaElectronica.indExitoCambioFP = false;
            daoCajaElectronica.rollback();
            return false;
        }
    }

    private void colocaVueltoDetallePago(String pVuelto) {
      if (lstDetalle.size() <= 0){
        return;
      }
      boolean existeSoles = false;
      boolean existeDolares = false;
      int filaSoles = 0;
      int filaDolares = 0;
      for (int i = 0; i < lstDetalle.size(); i++) {
        if(
        (((ArrayList)lstDetalle.get(i)).get(0)).toString().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)
        ){
            existeSoles = true;
            filaSoles = i;
            break;
        }else if((((ArrayList)lstDetalle.get(i)).get(0)).toString().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES)){
            existeDolares = true;
            filaDolares = i;
        }
      }
      if (existeSoles){
          ((ArrayList)lstDetalle.get(filaSoles)).set(7,pVuelto);
      } else if (existeDolares && !existeSoles){
          ((ArrayList)lstDetalle.get(filaDolares)).set(7,pVuelto);
      }
    }
    
    public ArrayList listarETV()
    {   
        ArrayList listaETVs = null;
        try
        {   listaETVs = daoCajaElectronica.getListaETVs();
        }
        catch (Exception ex)
        {   log.error("",ex);
        }
        return listaETVs;
    }
    
    /**
     * Lista cajas aperturadas
     * @author ERIOS
     * @since 2.2.8
     * @param pFechaDiaVenta
     * @return
     */
    public ArrayList<ArrayList<String>> getListaCajasAperturadas(String pFechaDiaVenta){
        ArrayList<ArrayList<String>> lstListado = null;
        try{
            daoCajaElectronica.openConnection();
            lstListado = daoCajaElectronica.getListaCajasAperturadas(pFechaDiaVenta);
            daoCajaElectronica.commit();
        }catch(Exception e){
            daoCajaElectronica.rollback();
            log.error("",e);            
        }
        return lstListado;
    }
}
