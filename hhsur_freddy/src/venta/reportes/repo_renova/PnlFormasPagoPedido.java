package venta.reportes.repo_renova;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;

import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PnlFormasPagoPedido extends JPanel {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(PnlFormasPagoPedido.class);
    FarmaTableModel tableModelComprobantes;
    FarmaTableModel tableModelDetalleComprobantes;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcComprobantes = new JScrollPane();
    private JTable tblComprobantes = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JButtonLabel btnComprobantes = new JButtonLabel();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public PnlFormasPagoPedido() {
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(757, 130));
        this.setLayout(gridLayout1);
        jPanelTitle1.setBounds(new Rectangle(5, 10, 730, 25));
        srcComprobantes.setBounds(new Rectangle(5, 30, 730, 95));
        tblComprobantes.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblComprobantes_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblComprobantes_keyPressed(e);
            }


        });
        jLabelFunction1.setBounds(new Rectangle(615, 210, 117, 19));
        jLabelFunction1.setText("[ESC]Escape");
        btnComprobantes.setText("Formas de Pago");
        btnComprobantes.setBounds(new Rectangle(10, 5, 100, 15));
        btnComprobantes.setMnemonic('F');
        btnComprobantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnComprobantes_actionPerformed(e);
            }
        });
        srcComprobantes.getViewport().add(tblComprobantes, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(srcComprobantes, null);
        jPanelTitle1.add(btnComprobantes, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaComprobantes();
        FarmaVariables.vAceptar = false;
        cargarFormasPago();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaComprobantes() {
        tableModelComprobantes =
                new FarmaTableModel(ConstantsReporte.columnsListaFormasPago, ConstantsReporte.defaultValuesListaFormasPago,
                                    0);
        FarmaUtility.initSimpleList(tblComprobantes, tableModelComprobantes, ConstantsReporte.columnsListaFormasPago);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void tblComprobantes_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblComprobantes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /** Permite mover el foco a la tabla comprobantesw y tabla detalle de comprobantes
     * @author: JCORTEZ
     * @since: 04/07/07
     */
    private void btnComprobantes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblComprobantes);
    }

    private void tblDetalleComprobantes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */


    private void cargarFormasPago() {
        try {
            log.debug(VariablesReporte.vCorrelativo);
            DBReportes.cargaListaFormasPago(tableModelComprobantes, VariablesReporte.vCorrelativo);
            FarmaUtility.ordenar(tblComprobantes, tableModelComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(new JDialog(), "Error al listar las Formas de Pago:\n " + sql.getMessage(), null);
            cerrarVentana(false);
        }
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
    }
}
