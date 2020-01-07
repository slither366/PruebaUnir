package mifarma.ptoventa.centromedico;

import com.gs.mifarma.componentes.PopupAlert;
import com.gs.mifarma.componentes.PopupConstants;

import common.FarmaUtility;

import java.awt.Frame;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DBAlertUp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubProcesosAtencionMedica extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SubProcesosAtencionMedica.class);

    private int tiempoInactividad;
    boolean indTerminoProceso = false;
    // asignar nombre a subproceso, llamando al constructor de la superclase
    DlgAtencionMedica myParentFrame;
    int SEGUNDOS_REINTENTO = 15;// 15 segundos

    public SubProcesosAtencionMedica(DlgAtencionMedica parent) {
        myParentFrame = parent;
    }

    // el método run es el código a ejecutar por el nuevo subproceso

    public void run() {
        try {
            Thread.sleep(1000 * 10); //ESPERE INICIALMENTE 10 SEGUNDOS
            while (true) {
                operaproceso();
                Thread.sleep(1000 * SEGUNDOS_REINTENTO);
            }
        } catch (Exception excepcion) {
            log.error("", excepcion);
        }
    }

    public void operaproceso() {
        try {
            // GRABAR CONSULTA AUTOMATICA
            System.out.println("INICIO   GRABA de forma automatica ...");
           // myParentFrame.grabarConsulta(true);
            System.out.println("FINALIZA GRABA de forma automatica ...");
        } catch (Exception e) {
            log.error("", e);
        } finally {
        }

    }
    
    public void pararProceso(){
        System.out.println("detruye hilo");
        stop();
        destroy();
    }

}
