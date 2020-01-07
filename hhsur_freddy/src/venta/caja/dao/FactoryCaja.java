package venta.caja.dao;

import venta.reference.TipoImplementacionDAO;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FactoryCaja.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      16.07.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FactoryCaja {
    public static DAOCaja getDAOCaja(TipoImplementacionDAO tipo) {
        DAOCaja dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBCaja();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
