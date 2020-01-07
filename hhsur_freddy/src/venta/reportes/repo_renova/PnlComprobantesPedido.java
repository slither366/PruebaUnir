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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;

//import venta.electronico.UtilityImpCompElectronico;

import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PnlComprobantesPedido extends JPanel {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(PnlComprobantesPedido.class);
    FarmaTableModel tableModelComprobantes;
    FarmaTableModel tableModelDetalleComprobantes;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcComprobantes = new JScrollPane();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane srcDetalleComprobante = new JScrollPane();
    private JTable tblComprobantes = new JTable();
    private JTable tblDetalleComprobantes = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction lblReimprimir = new JLabelFunction();
    private JButtonLabel btnComprobantes = new JButtonLabel();
    private JButtonLabel btnDetalleComprobante = new JButtonLabel();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public PnlComprobantesPedido() {
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
        this.setSize(new Dimension(746, 128));
        this.setLayout(gridLayout1);
        jPanelTitle1.setBounds(new Rectangle(5, 10, 720, 25));
        srcComprobantes.setBounds(new Rectangle(5, 30, 720, 95));
        jPanelTitle2.setBounds(new Rectangle(5, 195, 720, 25));
        jScrollPane2.setBounds(new Rectangle(0, 25, 685, 210));
        srcDetalleComprobante.setBounds(new Rectangle(5, 220, 720, 210));
        tblComprobantes.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblComprobantes_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblComprobantes_keyPressed(e);
            }
        });
        tblDetalleComprobantes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalleComprobantes_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(605, 435, 117, 19));
        jLabelFunction1.setText("[ESC]Escape");

        lblReimprimir.setBounds(new Rectangle(455, 435, 117, 19));
        lblReimprimir.setText("[F11] Re-imprimir");

        btnComprobantes.setText("Comprobantes");
        btnComprobantes.setBounds(new Rectangle(10, 5, 100, 15));
        btnComprobantes.setMnemonic('c');
        btnComprobantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnComprobantes_actionPerformed(e);
            }
        });
        btnDetalleComprobante.setText("Detalle del Comprobante");
        btnDetalleComprobante.setBounds(new Rectangle(10, 5, 160, 20));
        btnDetalleComprobante.setMnemonic('D');
        btnDetalleComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalleComprobante_actionPerformed(e);
            }
        });
        jPanelTitle2.add(btnDetalleComprobante, null);
        jPanelTitle2.add(jScrollPane2, null);
        srcDetalleComprobante.getViewport().add(tblDetalleComprobantes, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblReimprimir, null);
        jPanelWhite1.add(srcDetalleComprobante, null);
        jPanelWhite1.add(jPanelTitle2, null);
        srcComprobantes.getViewport().add(tblComprobantes, null);
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
        lblReimprimir.setVisible(false);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaComprobantes() {
        tableModelComprobantes =
                new FarmaTableModel(ConstantsReporte.columnsListaComprobantes, ConstantsReporte.defaultValuesListaComprobantes,
                                    0);
        FarmaUtility.initSimpleList(tblComprobantes, tableModelComprobantes,
                                    ConstantsReporte.columnsListaComprobantes);
    }

    private void initTableListaDetalleComprobantes() {
        tableModelDetalleComprobantes =
                new FarmaTableModel(ConstantsReporte.columnsListaComprobantesDetalle, ConstantsReporte.defaultValuesListaComprobantesDetalle,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleComprobantes, tableModelDetalleComprobantes,
                                    ConstantsReporte.columnsListaComprobantesDetalle);
        buscaComprobantes(VariablesReporte.vCorrelativo);        
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            if (tblComprobantes.getRowCount() > 0) {
                VariablesReporte.vNComprobante = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 1)).trim();
                log.info("variables reporte vNComprobante" + VariablesReporte.vNComprobante);
                cargaDetalleComprobantesVenta();
            }
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

    private void btnDetalleComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblDetalleComprobantes);
    }

    private void tblDetalleComprobantes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void listaDetalleComprobantes() {
        if (tblComprobantes.getRowCount() > 0) {
            initTableListaDetalleComprobantes();
            cargaDetalleComprobantesVenta();
        }
    }

    private void buscaComprobantes(String pCodigo) {
        VariablesReporte.vCorrelativo = pCodigo;
        cargaComprobantesVenta();
    }

    public void cargaComprobantesVenta() {
        try {
            log.info(VariablesReporte.vCorrelativo);
            DBReportes.obtieneComprobantes_Venta(tableModelComprobantes, VariablesReporte.vCorrelativo);
            if (tblComprobantes.getRowCount() > 0)
                FarmaUtility.ordenar(tblComprobantes, tableModelComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(new JDialog(), "Error al listar los Comprobantes Venta:\n " + sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cargaDetalleComprobantesVenta() {
        try{
            String tipcCompr = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 10)).trim();
            log.info("Nro de comprobantes" + " " + VariablesReporte.vNComprobante);
            DBReportes.obtieneComprobantes_Venta_Detalle(tableModelDetalleComprobantes, VariablesReporte.vNComprobante, 
                                                         VariablesReporte.vCorrelativo);
            FarmaUtility.ordenar(tblDetalleComprobantes, tableModelDetalleComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
            // KMONCADA 15.12.2014
            String flgCompElectronico = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 12)).trim();
            if ("1".equalsIgnoreCase(flgCompElectronico)){
                lblReimprimir.setVisible(true);
            }else{
                lblReimprimir.setVisible(false);
            }
        }catch (Exception sql){
            log.error("", sql);
            FarmaUtility.showMessage(new JDialog(), "Error al listar los Detalles de los Comprobantes Venta :\n" + sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(new JDialog(), "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /**
     * Pedir clave de operador para que pueda anular la recarga virtual.
     * @author ASOSA
     * @since 24/07/2014
     * @return
     */
    private boolean cargarLogeoAdmLocal() {
        return false;
    }


}
