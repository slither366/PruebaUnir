package venta.pinpad.reference;

import venta.caja.reference.PrintConsejo;
import venta.pinpad.visa.HiloProcesoAnularPinpadVisa;

import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiloImpresion extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(HiloImpresion.class);
    public String textoImpr = "";
    
    public void run()
    {   //si existe algun problema con la impresora, el flujo de cobro de pedido continua
        log.debug("Texto a IMPRIMIR POR TRANSACCION:---->   ",textoImpr.toString());
    
        PrintConsejo.imprimirHtml(textoImpr.toString(),
                                  VariablesPtoVenta.vImpresoraActual,
                                  VariablesPtoVenta.vTipoImpTermicaxIp);
        log.debug("******----Termino imprimir voucher----******");
    }
}
