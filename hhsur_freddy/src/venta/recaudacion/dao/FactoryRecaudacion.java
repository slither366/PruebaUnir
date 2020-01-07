package venta.recaudacion.dao;

import venta.ce.dao.DAOCajaElectronica;
import venta.ce.dao.FMCajaElectronica;
import venta.ce.dao.FactoryCajaElectronica;
import venta.ce.dao.MBCajaElectronica;

public class FactoryRecaudacion {
    public enum Tipo {MYBATIS}
    
    public FactoryRecaudacion() {
        super();
    }
    
    public static DAORecaudacion getDAORecaudacion(Tipo tipo) {
        DAORecaudacion dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBRecaudacion();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
