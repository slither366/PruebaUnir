package venta.recetario.dao;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : FactoryRecetario.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      15.04.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class FactoryRecetario {
    
    public enum Tipo {FRAMEWORK, MYBATIS}
    
    public FactoryRecetario() {
        super();
    }
    
    public static DAORecetario getDAORecetario(Tipo tipo){
        DAORecetario dao;
        switch (tipo) {
        case FRAMEWORK:
            dao = null;
            break;
        case MYBATIS:
            dao = new MBRecetario();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
