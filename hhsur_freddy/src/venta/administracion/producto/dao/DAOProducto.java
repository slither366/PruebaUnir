package venta.administracion.producto.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import venta.reference.DAOTransaccion;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DAOProducto.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * CVILCA      01.10.2013   Creaci�n<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public interface DAOProducto extends DAOTransaccion{
    
    /**
     * Obtiene los datos b�sicos de los productos por descripci�n.
     * @author CVILCA
     * @since 01.10.2013
     */
    public abstract ArrayList obtenerProductosPorDescripcion(String strDescripcion)throws SQLException;
    
    /**
     * Obtiene el c�digo EPL para la impresi�n del sticker de un producto.
     * @author CVILCA
     * @since 02.10.2013
     */
    public abstract String obtenerCodigoEPLPorProducto(String strCodigo)throws SQLException;
    
    
    /**
     * Obtiene el c�digo de barra de un producto
     * @author CHUANES
     * @since 22.11.2013
     */
    public abstract ArrayList obtenerProductoCodigoBarra(String pCodBarra)throws SQLException;
   
}
