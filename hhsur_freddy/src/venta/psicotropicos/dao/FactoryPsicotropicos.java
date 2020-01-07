package venta.psicotropicos.dao;

import venta.reference.TipoImplementacionDAO;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FactoryPsicotropicos.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LLEIVA      23.Sep.2013   Creaci�n<br>
 * <br>
 * @author Luis Leiva Baz�n<br>
 * @version 1.0<br>
 *
 */
public class FactoryPsicotropicos
{
    public enum Tipo {FRAMEWORK, MYBATIS}
    
    public static DAOPsicotropicos getDAOPsicotropicos(Tipo tipo)
    {
        DAOPsicotropicos dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBPsicotropicos();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
