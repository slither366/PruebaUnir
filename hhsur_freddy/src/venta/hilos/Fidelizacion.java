package venta.hilos;

import java.sql.SQLException;

import java.util.Collections;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaTableComparator;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.DBCaja;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fidelizacion extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Fidelizacion.class);

    private int tiempoInactividad;
    String pDNI_in = "";
    Object tipoObjeto = null;
    JDialog pDlg = null;
    boolean indTerminoProceso = false;
    Object pLblMensaje = null;
    // asignar nombre a subproceso, llamando al constructor de la superclase

    public Fidelizacion(String vDNI,JDialog pDialogo,Object pObjFoco,Object pLblMensaje_in) {
        pDNI_in = vDNI;
        tipoObjeto = pObjFoco;
        pDlg = pDialogo;
        pLblMensaje = pLblMensaje_in;
    }

    // el m�todo run es el c�digo a ejecutar por el nuevo subproceso

    public void run() {


        try {
            if (indTerminoProceso) {
                Thread.sleep(0);
            }

            operaproceso(pDNI_in);

        }
        // si el subproceso se interrumpi� durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            log.error("",excepcion);
        }

        // imprimir nombre del subproceso
        log.info(getName() + " termino su inactividad");

    }

    public void operaproceso(String pDNI_in) {
        /*
       SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
       SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
       */
        boolean pResultado = false;

        if (pDNI_in.trim().length() > 0) {

            try {

                pResultado = UtilityFidelizacion.existeDNIenRENIEC(pDNI_in);

                if (!pResultado) {
                    VariablesFidelizacion.vSIN_COMISION_X_DNI = true;
                    ((JLabel)pLblMensaje).setVisible(true);
                    FarmaUtility.showMessage(pDlg, 
                                             "El DNI no es v�lido,NO TENDRA COMISION POR LA VENTA.", 
                                             (JTextField)tipoObjeto);
                } else {
                    VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
                    ((JLabel)pLblMensaje).setVisible(false);
                }

                indTerminoProceso = true;

            } catch (Exception e) {
                log.error("",e);
                indTerminoProceso = true;
            } finally {
                indTerminoProceso = true;
            }
        }
        else{
            indTerminoProceso = true;
            VariablesFidelizacion.vSIN_COMISION_X_DNI = false;
            ((JLabel)pLblMensaje).setVisible(false);
        }

    }

    private void cargaIndImpresionRojoTicket() {
        String pResultado = "";
        try {
            pResultado = DBModuloVenta.getIndImprimeRojo();
            log.info("pResultado:" + pResultado);
            if (pResultado.trim().equalsIgnoreCase("S"))
                VariablesPtoVenta.vIndImprimeRojo = true;
            else
                VariablesPtoVenta.vIndImprimeRojo = false;
        } catch (SQLException err) {
            log.error("",err);
            VariablesPtoVenta.vIndImprimeRojo = false;
        }
    }
}
