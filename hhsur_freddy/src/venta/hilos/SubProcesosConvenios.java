package venta.hilos;

import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.modulo_venta.reference.UtilityModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubProcesosConvenios extends Thread{
    private static final Logger log = LoggerFactory.getLogger(SubProcesosConvenios.class);

    public SubProcesosConvenios() {
    }

    public void run() {
        try {
            if (!VariablesConvenioBTLMF.vValidaPrecio) {
                Thread.sleep(0);
            } else {
                log.debug("INICIO HILO CONVENIOS");
                VariablesConvenioBTLMF.vNew_Prec_Conv = 
                        UtilityConvenioBTLMF.Conv_Buscar_Precio();
                VariablesConvenioBTLMF.vValidaPrecio = false;
                log.debug("VariablesConvenioBTLMF.vNew_Prec_Conv:" + 
                                   VariablesConvenioBTLMF.vNew_Prec_Conv);
                log.debug("FIN HILO CONVENIOS");
            }
        }
        // si el subproceso se interrumpi� durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            log.error("",excepcion);
        }

    }
}
