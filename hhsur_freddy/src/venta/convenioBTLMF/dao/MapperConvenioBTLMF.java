package venta.convenioBTLMF.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface MapperConvenioBTLMF {
    
    @Select(value="{call #{listado, mode=OUT, jdbcType=CURSOR, resultMap=resultado} := " +
        " PTOVENTA_MATRIZ_CONV_BTLMF.BTLMF_F_CUR_LISTA_BENIFICIARIO(" +
            "#{CCODGRUPOCIA_IN}," + 
            "#{CCODLOCAL_IN}," + 
            "#{CSECUSULOCAL_IN}," + 
            "#{VCODCONVENIO_IN}," + 
            "#{VBENIFICIARIO_IN}" + 
            ")}")
    @Options(statementType = StatementType.CALLABLE)
    void listaBenefRemoto(Map<String,Object> mapParametros);
}
