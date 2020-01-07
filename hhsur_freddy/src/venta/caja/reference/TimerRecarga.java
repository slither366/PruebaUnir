package venta.caja.reference;

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.TimerTask;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venta.caja.reference.*;
import common.FarmaUtility;

public class TimerRecarga extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(TimerRecarga.class);

    int cant = 1;
    String indicador = 
        "I"; //I:inicio proceso; D: Existe respuesta Disponible , T: termino Tarea
    int cantidadIntentos = 3;
    
    int codigoSolicitud = 0;

    public String getIndicador() {
        return indicador;
    }

    public void setCantidadIntentos(int cantItentos) {
        if (cantItentos > 0) {
            cantidadIntentos = cantItentos;
        } else {
            cantidadIntentos = 1;
        }
    }
    
    public void setCodigoSolicitud(int codigo) {
        codigoSolicitud = codigo;
    }
    
    

    
    public void run() {
        log.info("intento respuesta de recarga:"+ 
                 " - intento nro" + cant);
        
        if (cant++ > cantidadIntentos) {
            log.info("termino el timer de intento de obtiene de respuesta de recarga");
            indicador = "T";
            VariablesVirtual.vCodigoRespuesta = null;
            cancel();
        }
        try {
            ArrayList infoProductoRecargaVirtual = 
                DBCaja.ObtieneRespuestaAnulacion(codigoSolicitud);
           
            
            if (infoProductoRecargaVirtual.size() > 0) {
                log.info("Se obtuvo respuesta " + infoProductoRecargaVirtual);           
                VariablesVirtual.vCodigoRespuesta = FarmaUtility.getValueFieldArrayList(infoProductoRecargaVirtual, 
                                                            0, 2);
                 log.info("Se obtuvo respuesta de solicitud virtual" +  VariablesVirtual.vCodigoRespuesta );
                 
                indicador = "D";
                cancel();
            }
      
        } catch (SQLException e) {
            log.error("",e);
        }

    }


}
