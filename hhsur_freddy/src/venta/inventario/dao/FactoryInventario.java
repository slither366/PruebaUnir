package venta.inventario.dao;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FactoryRecetario.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LRUIZ      17.05.2013   Creaci�n<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */
public class FactoryInventario {
    
    public enum Tipo {FRAMEWORK, MYBATIS}
    
    public FactoryInventario() {
        super();
    }
    
    public static DAOInventario getDAOInventario(Tipo tipo){
        DAOInventario dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBInventario();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
