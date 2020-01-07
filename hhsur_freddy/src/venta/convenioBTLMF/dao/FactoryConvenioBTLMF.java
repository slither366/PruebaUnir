package venta.convenioBTLMF.dao;


public class FactoryConvenioBTLMF {
    
    public enum Tipo {MYBATIS};
    
    public FactoryConvenioBTLMF() {
        super();
    }
    
    public static DAOConvenioBTLMF getDAOConvenioBTLMF(Tipo tipo) {
        DAOConvenioBTLMF dao;
        switch (tipo) {
        case MYBATIS:
            dao = new MBConvenioBTLMF();
            break;
        default:
            dao = null;
            break;
        }
        return dao;
    }
}
