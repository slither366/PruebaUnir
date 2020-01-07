package venta.convenioBTLMF.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.FarmaVariables;

import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.reference.BeanResultado;
import venta.reference.MyBatisUtil;
import venta.reference.UtilityPtoVenta;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MBConvenioBTLMF implements DAOConvenioBTLMF{
    
    private static final Logger log = LoggerFactory.getLogger(MBConvenioBTLMF.class);
    private SqlSession sqlSession = null;
    UtilityPtoVenta utilityPtoVenta =new UtilityPtoVenta();
    private MapperConvenioBTLMF mapperTrsscSix = null;

    public ArrayList<ArrayList<String>> listaBenefRemoto(){        
        List<BeanResultado> tmpLista = null;
        ArrayList tmpArray = new ArrayList();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("CCODGRUPOCIA_IN",FarmaVariables.vCodGrupoCia);
        mapParametros.put("CCODLOCAL_IN",FarmaVariables.vCodLocal);
        mapParametros.put("CSECUSULOCAL_IN",FarmaVariables.vNuSecUsu);
        mapParametros.put("VCODCONVENIO_IN",VariablesConvenioBTLMF.vCodConvenio);
        mapParametros.put("VBENIFICIARIO_IN",VariablesConvenioBTLMF.vDescDiagnostico);
                
        try{   
            sqlSession = MyBatisUtil.getRACSqlSessionFactory().openSession();
            mapperTrsscSix = sqlSession.getMapper(MapperConvenioBTLMF.class);
            mapperTrsscSix.listaBenefRemoto(mapParametros);
            tmpLista = (List<BeanResultado>)mapParametros.get("listado");
            tmpArray = utilityPtoVenta.parsearResultadoMatriz(tmpLista);
        }catch(Exception e){   
            log.error("",e);
            tmpArray=null;
        }finally{   
            sqlSession.close();
        }
        return tmpArray;
    }
}
