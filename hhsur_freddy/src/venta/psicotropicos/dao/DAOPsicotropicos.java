package venta.psicotropicos.dao;

import java.util.ArrayList;

import venta.reference.DAOTransaccion;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DAOPsicotropicos.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LLEIVA      23.Sep.2013   Creaci�n<br>
 * <br>
 * @author Luis Leiva Baz�n<br>
 * @version 1.0<br>
 *
 */
public interface DAOPsicotropicos extends DAOTransaccion
{
    /**
     * Obtiene el listado de psicotropicos de acuerdo a un determinado periodo de tiempo
     * @author LLEIVA
     * @since 23.Sep.2013
     */ 
     public ArrayList obtenerListadoPsicotropicos(String fecha_inicial,
                                                  String fecha_final,
                                                  String cod_prod);
    
    public String verificaProdPsicotropicos(String cod_prod);
}
