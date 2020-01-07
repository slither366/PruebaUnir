package venta.reportes.repo_renova;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import common.*;

import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.delivery.reference.VariablesDelivery;
import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reportes.DlgComprobantes;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDatosGeneralPedido extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgDatosGeneralPedido.class);

    FarmaTableModel tableModel;
    FarmaTableModel tableModelFormasPago;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelWhite lblDireccion = new JLabelWhite();
    private JLabelWhite lblRuc = new JLabelWhite();
    private JLabelWhite lblNoPedido = new JLabelWhite();
    private JLabelWhite lblNoPedidoT = new JLabelWhite();
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelWhite lblFechaT = new JLabelWhite();
    private JLabelWhite lblAtencion = new JLabelWhite();
    private JLabelWhite lblAtencionT = new JLabelWhite();
    private JLabelWhite lblRucT = new JLabelWhite();
    private JLabelWhite lblDireccionT = new JLabelWhite();
    private JLabelWhite lblClienteT = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcDetallePedidos = new JScrollPane();
    private JTable tblDetallePedidos = new JTable();
    private JLabelWhite lblHoraT = new JLabelWhite();
    private JLabelWhite lblHora = new JLabelWhite();
    private JLabelFunction lblESC = new JLabelFunction();
    private JLabelWhite lblCodigolocal = new JLabelWhite();
    private JLabelWhite lblDescLocal = new JLabelWhite();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblIgvMonto = new JLabelWhite();
    private JLabelWhite lblBruto2 = new JLabelWhite();
    private JLabelWhite lblTotalMonto = new JLabelWhite();
    private JLabelWhite lblBruto3 = new JLabelWhite();
    private JLabelWhite lblItems = new JLabelWhite();
    private JLabelWhite lblitem = new JLabelWhite();
    private JButtonLabel btlRelacionProductos = new JButtonLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private boolean indResumenDelivery;
    private boolean indVerDelivery;
    private ArrayList<ArrayList<String>> lstFormasPago;
    private JPanelWhite pnlBotones = new JPanelWhite();
    private JLabelOrange lblComprobante = new JLabelOrange();
    private PnlComprobantesPedido pnlComprobante = new PnlComprobantesPedido();
    private PnlFormasPagoPedido pnlFormaPago = new PnlFormasPagoPedido();

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */
    public DlgDatosGeneralPedido() {
        this(null, "", false);
    }

    public DlgDatosGeneralPedido(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                              METODO jbInit                               */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1324, 497));
        this.getContentPane().setLayout(borderLayout1);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Reporte por Cliente");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });        
        jPanelWhite1.setBounds(new Rectangle(0, 0, 750, 605));
        jPanelHeader1.setBounds(new Rectangle(10, 15, 625, 120));
        jPanelHeader1.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jPanelHeader1.setBackground(Color.white);
        lblCliente.setText("Cliente : ");
        lblCliente.setBounds(new Rectangle(5, 10, 75, 20));
        lblCliente.setForeground(new Color(0, 99, 148));
        lblDireccion.setText("Direcci\u00f3n : ");
        lblDireccion.setBounds(new Rectangle(5, 30, 75, 20));
        lblDireccion.setForeground(new Color(0, 99, 148));
        lblRuc.setText("Ruc: ");
        lblRuc.setBounds(new Rectangle(5, 50, 75, 20));
        lblRuc.setForeground(new Color(0, 99, 148));
        lblNoPedido.setText("No Pedido : ");
        lblNoPedido.setBounds(new Rectangle(5, 70, 75, 20));
        lblNoPedido.setForeground(new Color(0, 99, 148));
        lblNoPedidoT.setText("000");
        lblNoPedidoT.setBounds(new Rectangle(85, 70, 345, 20));
        lblNoPedidoT.setForeground(new Color(0, 99, 148));
        lblFecha.setText("Fecha :");
        lblFecha.setBounds(new Rectangle(5, 90, 75, 20));
        lblFecha.setForeground(new Color(0, 99, 148));
        lblFechaT.setBounds(new Rectangle(85, 90, 65, 20));
        lblFechaT.setForeground(new Color(0, 99, 148));
        lblAtencion.setText("Atendido por :");
        lblAtencion.setBounds(new Rectangle(455, 90, 85, 20));
        lblAtencion.setForeground(new Color(0, 99, 148));
        lblAtencionT.setBounds(new Rectangle(545, 90, 75, 20));
        lblAtencionT.setForeground(new Color(0, 99, 148));
        lblRucT.setText("00000000");
        lblRucT.setBounds(new Rectangle(85, 50, 345, 20));
        lblRucT.setForeground(new Color(0, 99, 148));
        lblDireccionT.setBounds(new Rectangle(85, 30, 345, 20));
        lblDireccionT.setForeground(new Color(0, 99, 148));
        lblClienteT.setBounds(new Rectangle(85, 10, 345, 20));
        lblClienteT.setForeground(new Color(0, 99, 148));
        lblEstado.setText("Cobrado");
        lblEstado.setBounds(new Rectangle(550, 50, 80, 20));
        lblEstado.setForeground(new Color(0, 99, 148));
        jPanelTitle1.setBounds(new Rectangle(640, 15, 665, 20));
        srcDetallePedidos.setBounds(new Rectangle(640, 35, 665, 360));
        srcDetallePedidos.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    srcDetallePedidos_keyPressed(e);
                }
            });
        tblDetallePedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    tblDetallePedidos_keyPressed(e);
                }

            public void keyReleased(KeyEvent e) {
                tblDetallePedidos_keyReleased(e);
            }
        });
        lblHoraT.setText("0");
        lblHoraT.setBounds(new Rectangle(300, 90, 80, 20));
        lblHoraT.setForeground(new Color(0, 99, 148));
        lblHora.setText("Hora :");
        lblHora.setBounds(new Rectangle(255, 90, 45, 20));
        lblHora.setForeground(new Color(0, 99, 148));
        lblESC.setBounds(new Rectangle(1190, 435, 115, 20));
        lblESC.setText("[ ESC ] Salir");
        lblESC.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblESC_mouseClicked(e);
                }
            });
        lblCodigolocal.setText("0");
        lblCodigolocal.setBounds(new Rectangle(455, 10, 35, 20));
        lblCodigolocal.setForeground(new Color(0, 99, 148));
        lblCodigolocal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescLocal.setBounds(new Rectangle(500, 10, 115, 20));
        lblDescLocal.setForeground(new Color(0, 99, 148));
        jPanelTitle2.setBounds(new Rectangle(640, 395, 665, 25));
        lblIgvMonto.setBounds(new Rectangle(490, 5, 55, 15));
        lblIgvMonto.setText("0");
        lblBruto2.setText("IGV S/");
        lblBruto2.setBounds(new Rectangle(440, 5, 40, 15));
        lblTotalMonto.setBounds(new Rectangle(615, 5, 50, 15));
        lblTotalMonto.setText("0");
        lblBruto3.setText("Total S/");
        lblBruto3.setBounds(new Rectangle(555, 5, 50, 15));
        lblItems.setBounds(new Rectangle(55, 5, 40, 15));
        lblItems.setText("0");
        lblitem.setText("Items");
        lblitem.setBounds(new Rectangle(10, 5, 40, 15));
        btlRelacionProductos.setText("Relacion Productos");
        btlRelacionProductos.setBounds(new Rectangle(10, 0, 110, 20));
        btlRelacionProductos.setMnemonic('R');
        btlRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btlRelacionProductos_actionPerformed(e);
            }
        });
        pnlBotones.setBounds(new Rectangle(5, 545, 735, 20));
        lblComprobante.setText("");
        lblComprobante.setBounds(new Rectangle(455, 30, 145, 20));
        pnlComprobante.setBounds(new Rectangle(10, 300, 730, 125));
        pnlFormaPago.setBounds(new Rectangle(10, 445, 740, 120));
        pnlComprobante.setBounds(new Rectangle(10, 305, 730, 140));
        pnlComprobante.setBounds(new Rectangle(0, 135, 635, 140));
        pnlFormaPago.setBounds(new Rectangle(5, 270, 630, 155));
        jPanelHeader1.add(lblComprobante, null);
        jPanelHeader1.add(lblDescLocal, null);
        jPanelHeader1.add(lblCodigolocal, null);
        jPanelHeader1.add(lblHora, null);
        jPanelHeader1.add(lblHoraT, null);
        jPanelHeader1.add(lblEstado, null);
        jPanelHeader1.add(lblClienteT, null);
        jPanelHeader1.add(lblDireccionT, null);
        jPanelHeader1.add(lblRucT, null);
        jPanelHeader1.add(lblAtencionT, null);
        jPanelHeader1.add(lblAtencion, null);
        jPanelHeader1.add(lblFechaT, null);
        jPanelHeader1.add(lblFecha, null);
        jPanelHeader1.add(lblNoPedidoT, null);
        jPanelHeader1.add(lblNoPedido, null);
        jPanelHeader1.add(lblRuc, null);
        jPanelHeader1.add(lblDireccion, null);
        jPanelHeader1.add(lblCliente, null);
        jPanelWhite1.add(pnlComprobante, null);
        jPanelWhite1.add(pnlBotones, null);
        srcDetallePedidos.getViewport().add(tblDetallePedidos, null);
        jPanelWhite1.add(srcDetallePedidos, null);
        jPanelTitle1.add(btlRelacionProductos, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelWhite1.add(lblESC, null);
        jPanelWhite1.add(pnlFormaPago, null);
        jPanelTitle2.add(lblitem, null);
        jPanelTitle2.add(lblItems, null);
        jPanelTitle2.add(lblBruto3, null);
        jPanelTitle2.add(lblTotalMonto, null);
        jPanelTitle2.add(lblBruto2, null);
        jPanelTitle2.add(lblIgvMonto, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblDetallePedidos);
        FarmaUtility.moveFocus(tblDetallePedidos);
        log.debug("Numero Comprobante" + VariablesReporte.vNComprobante);
        cargaVariables();
        mostrarUsuarioVenta();
        pnlComprobante.cargaComprobantesVenta();
        listadoFormasPago();
        this.setLocationRelativeTo(null);
        FarmaUtility.centrarVentana(this);
    }
    private void this_windowClosing(WindowEvent e) {
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaDetalleVentas();
        initTableListaFormasPago();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTableListaDetalleVentas() {
        tableModel =
                new FarmaTableModel(ConstantsReporte.columnsListaDetalleVentas, ConstantsReporte.defaultValuesListaDetalleVentas,
                                    0);
        FarmaUtility.initSimpleList(tblDetallePedidos, tableModel, ConstantsReporte.columnsListaDetalleVentas);
    }

    private void initTableListaFormasPago() {
    }

    private void cargaDetalleVentas() {
        try {
            log.debug(VariablesReporte.vCorrelativo);
            DBReportes.obtieneDetalleRegistroVentas(tableModel, VariablesReporte.vCorrelativo);
            FarmaUtility.ordenar(tblDetallePedidos, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(new JDialog(), "Error al listar el Detalle de las Ventas : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    
    private void tblDetallePedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetallePedidos_keyReleased(KeyEvent e) {
        mostrarUsuarioVenta();
    }

    private void tblListaFormasPago_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btlRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblDetallePedidos);
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    private void buscaDetalleVentas(String pCodigo) {
        VariablesReporte.vCorrelativo = pCodigo;
        cargaDetalleVentas();
    }
    
    private void listadoComprobanteVenta() {
        
    }
    /*private void listadoComprobanteVenta() {
        DlgComprobantes dlgComprobantes = new DlgComprobantes(myParentFrame, "", true);
        dlgComprobantes.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }*/

    /**  Lista las formas de pago del Pedido
     * @author: JCORTEZ
     * @since:  04/08/2007
     */
    private void listadoFormasPago() {
        /*DlgFormasPago dlgformaspago = new DlgFormasPago(myParentFrame, "", true);
        dlgformaspago.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }*/
    }

    private void cargaVariables() {

        lblClienteT.setText(VariablesReporte.vCliente);
        lblDireccionT.setText(VariablesReporte.vDireccion);
        lblRucT.setText(VariablesReporte.vRuc);
        lblNoPedidoT.setText(VariablesReporte.vCorrelativo);
        lblFechaT.setText(VariablesReporte.vFecha.substring(0, 10));
        lblHoraT.setText(VariablesReporte.vHora);
        //lblAtencionT.setText(VariablesReporte.vUsuario);
        lblEstado.setText(VariablesReporte.vEstado);
        lblCodigolocal.setText(FarmaVariables.vCodLocal);
        lblDescLocal.setText(FarmaVariables.vDescLocal);
        buscaDetalleVentas(VariablesReporte.vCorrelativo);
       // lblMontoBruto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 8)).trim());
       // lblDctoMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 9)).trim());
        lblIgvMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 10)).trim());
        lblTotalMonto.setText(((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 11)).trim());
        lblItems.setText("" + tblDetallePedidos.getRowCount());


        //ERIOS 2.4.4 Se muestra datos delivery
        /*{
            this.setSize(new Dimension(750, 470));
            pnlBotones.setLocation(5, 405);
        }*/
    }

    private void mostrarUsuarioVenta() {
        String usuarioVenta;
        int total = tblDetallePedidos.getRowCount();
        if (total > 0) {
            usuarioVenta = ((String)tblDetallePedidos.getValueAt(tblDetallePedidos.getSelectedRow(), 12)).trim();
            lblAtencionT.setText(usuarioVenta);
        } else {
            usuarioVenta = "";
        }
    }

    private void imprimir() {
        if (indResumenDelivery) {
            log.info("aca imprimir comanda");
            UtilityCaja.imprimeDatosDelivery(new JDialog(), VariablesReporte.vCorrelativo);
        } else {
            if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
                return;

            //FarmaPrintService vPrint = new FarmaPrintService(45, FarmaVariables.vImprReporte, true);
            FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
            log.debug(FarmaVariables.vImprReporte);
            if (!vPrint.startPrintService()) {
                FarmaUtility.showMessage(new JDialog(), "No se pudo inicializar el proceso de impresión", tblDetallePedidos);
                return;
            }

            try {
                String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
                String campoAlt = "________";

                vPrint.setStartHeader();
                vPrint.activateCondensed();
                vPrint.printBold(FarmaPRNUtility.llenarBlancos(40) + " REPORTE  DETALLE DE PEDIDO", true);
                vPrint.printBold("Nombre Compañia : " + FarmaVariables.vNomCia, true);
                vPrint.printBold("Fecha : " + fechaActual, true);
                vPrint.printBold("Fecha y Hora Pedido: " + VariablesReporte.vFecha.substring(0, 10) + " " +
                                 VariablesReporte.vHora, true);
                vPrint.printBold("CORR : " + VariablesReporte.vCorrelativo, true);
                vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);

                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.printBold("Codigo Cant   Descripcion                  Unidad     Lab.                 P.Unit    Dscto      Total",
                                 true);
                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.deactivateCondensed();
                vPrint.setEndHeader();

                for (int i = 0; i < tblDetallePedidos.getRowCount(); i++) {
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 0),
                                                                           6) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 1),
                                                                           6) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 2),
                                                                           28) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 3),
                                                                           10) + " " +
                                          FarmaPRNUtility.alinearIzquierda((String)tblDetallePedidos.getValueAt(i, 4),
                                                                           18) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 5),
                                                                         8) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 6),
                                                                         8) + " " +
                                          FarmaPRNUtility.alinearDerecha((String)tblDetallePedidos.getValueAt(i, 7),
                                                                         10), true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=====================================================================================================",
                                 true);
                vPrint.printBold("Total Monto S/. : " + FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(), 83),
                                 true);
                vPrint.printBold("Registros Impresos: " +
                                 FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(tblDetallePedidos.getRowCount(),
                                                                                          ",##0"), 10), true);
                vPrint.deactivateCondensed();
                vPrint.endPrintService();
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(new JDialog(), "Ocurrió un error al imprimir : \n" +
                        sql.getMessage(), null);
            }
        }
    }

    public void setIndResumenDelivery(boolean indResumenDelivery) {
        this.indResumenDelivery = indResumenDelivery;
    }

    public void setIndVerDelivery(boolean indVerDelivery) {
        this.indVerDelivery = indVerDelivery;
    }

    public void setLstFormasPago(ArrayList<ArrayList<String>> lstFormasPago) {
        this.lstFormasPago = lstFormasPago;
    }

    private void aceptarResumenDelivery() {

        cerrarVentana(true);
    }


    public void setLblComprobante(String pComprobante) {
        lblComprobante.setText(pComprobante);
    }
    

    private void srcDetallePedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblESC_mouseClicked(MouseEvent e) {
        cerrarVentana(false);
    }
}
