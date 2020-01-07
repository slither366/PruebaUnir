package venta.caja.reference;

import venta.caja.DlgProcesarNuevoCobro;
import venta.otros.reference.ConstantsOtros;
import venta.recaudacion.DlgProcesarPagoTerceros;

public class HiloProceso extends Thread {
    private DlgProcesarNuevoCobro dialogNuevoCobro;
    private DlgProcesarPagoTerceros dialogPagoTerceros;
    private int tipoDialog;
    private boolean procesando;
    
    public HiloProceso (DlgProcesarNuevoCobro dialogNuevoCobro){
        this.dialogNuevoCobro = dialogNuevoCobro;
        this.tipoDialog = ConstantsOtros.TIPO_DIALOG_NUEVO_COBRO;
    }
    
    public HiloProceso (DlgProcesarPagoTerceros dialogPagoTerceros){
        this.dialogPagoTerceros = dialogPagoTerceros;
        this.tipoDialog = ConstantsOtros.TIPO_DIALOG_PAGO_TERCEROS;
    }
    
    public void run(){
        switch(tipoDialog){
        case ConstantsOtros.TIPO_DIALOG_NUEVO_COBRO:
           // dialogNuevoCobro.realizarProcesos();
            break;
        case ConstantsOtros.TIPO_DIALOG_PAGO_TERCEROS:
            dialogPagoTerceros.realizarProcesos();
            break;
        case ConstantsOtros.TIPO_DIALOG_VENTA_CMR:
            dialogPagoTerceros.realizarProcesos();
            break;
        }
        
    }

    public void setProcesando(boolean procesando) {
        this.procesando = procesando;
    }
    
}
