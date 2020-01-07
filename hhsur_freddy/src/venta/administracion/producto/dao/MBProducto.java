package venta.administracion.producto.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.FarmaVariables;

import venta.reference.BeanResultado;
import venta.reference.MyBatisUtil;
import venta.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : MBProducto.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * CVILCA      01.10.2013   Creaci�n<br>
 * <br>
 * @author Christian Vilca Quiros<br>
 * @version 1.0<br>
 *
 */
public class MBProducto implements DAOProducto {
    
    private static final Logger log = LoggerFactory.getLogger(MBProducto.class);
    
    private SqlSession sqlSession;
    private MapperProducto mapper;
    UtilityPtoVenta utilityPtoVenta =new UtilityPtoVenta();
        
    @Override
    public void commit() {
        sqlSession.commit(true);
    }

    @Override
    public void rollback() {
        sqlSession.rollback(true);
    }

    @Override
    public void openConnection() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperProducto.class);
    }
    
    public void close(){
        sqlSession.close();
    }
    
    
    
    /**
     * Obtiene los datos b�sicos de los productos por descripci�n.
     * @author CVILCA
     * @since 01.10.2013
     */
    public ArrayList obtenerProductosPorDescripcion(String strDescripcion)throws SQLException{   
        ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);    
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("vDescripcion",strDescripcion);
        try{               
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperProducto.class);
            mapper.obtenerProductosPorDescripcion(mapParametros);
            List<BeanResultado> listaResultado = (List<BeanResultado>) mapParametros.get("listado");
            lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);            
        }catch(Exception e){   
            log.error("",e);
            lista=null;
        }finally{   
            sqlSession.close();
        }
        return lista;
    }
    
    /**
     * Obtiene el c�digo EPL para la impresi�n del sticker de un producto.
     * @author CVILCA
     * @since 02.10.2013
     */
    public String obtenerCodigoEPLPorProducto(String strCodigo)throws SQLException{
       Map<String,Object> mapParametros = new HashMap<String,Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);    
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cCodProd",strCodigo);
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        mapper = sqlSession.getMapper(MapperProducto.class);
        mapper.obtenerCodigoEPLPorProducto(mapParametros);
        return mapParametros.get("epl").toString();
    }

    @Override
    public ArrayList obtenerProductoCodigoBarra(String pCodBarra) {
        ArrayList lista = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("cCodGrupoCia", FarmaVariables.vCodGrupoCia);    
        mapParametros.put("cCodLocal", FarmaVariables.vCodLocal);
        mapParametros.put("cCodBarra",pCodBarra);
        try{               
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(MapperProducto.class);
            mapper.obtenerProductoCodigobarra(mapParametros);
            List<BeanResultado> listaResultado = (List<BeanResultado>) mapParametros.get("listado");
            lista = utilityPtoVenta.parsearResultadoMatriz(listaResultado);            
        }catch(Exception e){   
            log.error("",e);
            lista=null;
        }finally{   
            sqlSession.close();
        }
        return lista;
    }
}
