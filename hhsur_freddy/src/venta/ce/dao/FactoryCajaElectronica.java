package venta.ce.dao;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FactoryCajaElectronica.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      26.03.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FactoryCajaElectronica {
    
    public enum Tipo {FRAMEWORK, MYBATIS}
    
    public FactoryCajaElectronica() {
        super();
    }

    public static DAOCajaElectronica getDAOCajaElectronica(Tipo tipo) {
        DAOCajaElectronica dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = new FMCajaElectronica();
            break;
        case MYBATIS:
            dao = new MBCajaElectronica();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
