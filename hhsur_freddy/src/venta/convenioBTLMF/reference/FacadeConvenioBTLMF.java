package venta.convenioBTLMF.reference;

import java.util.ArrayList;

import common.FarmaTableModel;

import venta.convenioBTLMF.dao.DAOConvenioBTLMF;
import venta.convenioBTLMF.dao.FactoryConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacadeConvenioBTLMF {
    
    private static final Logger log = LoggerFactory.getLogger(FacadeConvenioBTLMF.class);
    
    private DAOConvenioBTLMF daoConvenioBTLMF;
    
    public FacadeConvenioBTLMF(){
        super();
        daoConvenioBTLMF = FactoryConvenioBTLMF.getDAOConvenioBTLMF(FactoryConvenioBTLMF.Tipo.MYBATIS);
    }
    
    public ArrayList<ArrayList<String>> listarBeneficRemoto(FarmaTableModel tableModelListaDatos)
    {   ArrayList<ArrayList<String>> lstListado = null;
        try
        {   lstListado = daoConvenioBTLMF.listaBenefRemoto();
            
            tableModelListaDatos.clearTable();
            tableModelListaDatos.data = lstListado;
        }
        catch (Exception e)
        {   log.error("",e);
        }
        return lstListado;
    }
}
