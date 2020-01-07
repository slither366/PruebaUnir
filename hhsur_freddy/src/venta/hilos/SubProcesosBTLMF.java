package venta.hilos;

import componentes.gs.worker.JDialogProgress;

import java.awt.Frame;

import java.sql.SQLException;

import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JFrame;

import common.FarmaConstants;
import common.FarmaTableComparator;
import common.FarmaUtility;

import common.FarmaVariables;

import venta.caja.reference.DBCaja;
import venta.convenioBTLMF.reference.DBConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubProcesosBTLMF extends JDialogProgress {
    private static final Logger log = LoggerFactory.getLogger(SubProcesosBTLMF.class);  

    private int tiempoInactividad;
    String pNombre = "";
    boolean indTerminoProceso = false;
    JFrame pdialog = new JFrame();
    JDialog dialog;


    // asignar nombre a subproceso, llamando al constructor de la superclase

    public SubProcesosBTLMF(String nombre, Frame pdialog, JDialog jdialog) {
        pNombre = nombre;
        dialog = jdialog;
    }

    // el m�todo run es el c�digo a ejecutar por el nuevo subproceso

    public void run() {


        try {
            if (indTerminoProceso) {
                Thread.sleep(0);
            }

            operaproceso(pNombre);


        }
        // si el subproceso se interrumpi� durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            log.error("",excepcion);
        }

        // imprimir nombre del subproceso
        log.info(getName() + " termino su inactividad");

    }


    public void operaproceso(String pNombre_2) {
        try {


            if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CONS_SALD_CREDITO_BENIF")) {
                log.info("inicio Consulta Saldo Credito Benificiario");

                if (UtilityConvenioBTLMF.consultarSaldCreditoBenif(pdialog).equals("S")) {
                    FarmaUtility.showMessage(pdialog,
                                             "El Saldo Cr�dito del Benificiario es Insuficiente!!",
                                             "");

                }
                log.info("fin Consulta Saldo Credito Benificiario");
                indTerminoProceso = true;
            } else {
                if (pNombre_2.trim().toUpperCase().equalsIgnoreCase("CARGA_LISTA_PRECIOS_CONV")) {
                    //DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
                    /*log.debug("inicio Consulta cargar precios por conv");
                    //metodo que carga los precios de convenios
                    cargarListaPrecios();
                    log.debug("fin Consulta cargar precios por conv");*/
                    indTerminoProceso = true;
                    /*if(VariablesConvenioBTLMF.vDatosPreciosConv.size()==0)
                        FarmaUtility.showMessage(pdialog,
                                                 "No se cargo los precios de Convenio "+VariablesConvenioBTLMF.vCodConvenio+",Verificar!!",
                                                 "");*/
                }
            }
        } catch (Exception e) {
            log.error("",e);
            indTerminoProceso = true;
        } finally {
            indTerminoProceso = true;
        }
    }


//DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
/*public void cargarListaPrecios() {
        try {

            VariablesConvenioBTLMF.vDatosPreciosConv.clear();

            log.debug("Entro a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio);
            DBConvenioBTLMF.cargarListaPrecios(VariablesConvenioBTLMF.vDatosPreciosConv);
            log.debug("Termino de cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Tama�o: " +
                               VariablesConvenioBTLMF.vDatosPreciosConv.size());


        } catch (SQLException e) {
            log.debug("Error a cargar Lista Precios Convenio-->" +
                               VariablesConvenioBTLMF.vCodConvenio +
                               ", Error-->" + e.getMessage());
            log.error("",e);
        }
    }
*/


    @Override
    public void ejecutaProceso() {
        operaproceso(pNombre);
    }
}
