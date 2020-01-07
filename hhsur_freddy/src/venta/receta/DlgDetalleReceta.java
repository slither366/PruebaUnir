package venta.receta;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import common.FarmaConstants;
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.delivery.reference.VariablesDelivery;
import venta.receta.reference.ConstantsReceta;
import venta.receta.reference.DBReceta;
import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleReceta extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */

    private static final Logger log = LoggerFactory.getLogger(DlgDetalleReceta.class);

    FarmaTableModel tableModel;
    FarmaTableModel tableModelFormasPago;
    private Frame myParentFrame;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcDetallePedidos = new JScrollPane();
    private JTable tblDetallePedidos = new JTable();
    private JLabelFunction lblESC = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JButtonLabel btlRelacionProductos = new JButtonLabel();

    private boolean indResumenDelivery;
    private boolean indVerDelivery;
    private ArrayList<ArrayList<String>> lstFormasPago;
    private JPanelWhite pnlBotones = new JPanelWhite();
    ArrayList vFilaInput = new ArrayList();
    
    public boolean validaStock = false;
    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */

    public DlgDetalleReceta() {
        this(null, "", false,null);
    }

    public DlgDetalleReceta(Frame parent, String title, boolean modal,ArrayList vFila) {
        super(parent, title, modal);
        myParentFrame = parent;
        vFilaInput=vFila;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    public DlgDetalleReceta(Frame parent, String title, boolean modal,ArrayList vFila,
                            boolean validaStock) {
        super(parent, title, modal);
        myParentFrame = parent;
        vFilaInput=vFila;
        this.validaStock = validaStock;
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
        this.setSize(new Dimension(977, 334));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Detalle");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 975, 310));
        jPanelTitle1.setBounds(new Rectangle(10, 5, 955, 20));
        srcDetallePedidos.setBounds(new Rectangle(10, 25, 955, 220));
        tblDetallePedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetallePedidos_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblDetallePedidos_keyReleased(e);
            }
        });
        lblESC.setBounds(new Rectangle(850, 280, 115, 20));
        lblESC.setText("[ ESC ] Salir");
        jPanelTitle2.setBounds(new Rectangle(10, 245, 955, 25));
        lblF11.setBounds(new Rectangle(735, 280, 110, 20));
        lblF11.setText("[ F11 ] Aceptar");
        btlRelacionProductos.setText("Relacion Productos");
        btlRelacionProductos.setBounds(new Rectangle(10, 0, 110, 20));
        btlRelacionProductos.setMnemonic('R');
        btlRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btlRelacionProductos_actionPerformed(e);
            }
        });
        pnlBotones.setBounds(new Rectangle(5, 545, 735, 20));
        jPanelWhite1.add(pnlBotones, null);
        srcDetallePedidos.getViewport().add(tblDetallePedidos, null);
        jPanelWhite1.add(srcDetallePedidos, null);
        jPanelTitle1.add(btlRelacionProductos, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jPanelTitle2, null);
        jPanelWhite1.add(lblESC, null);
        jPanelWhite1.add(lblF11, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaDetalleVentas();

        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTableListaDetalleVentas() {
        tableModel =
                new FarmaTableModel(ConstantsReceta.columnsListaDetalleVentas, ConstantsReceta.defaultValuesListaDetalleVentas,
                                    0);
        FarmaUtility.initSimpleList(tblDetallePedidos, tableModel, ConstantsReceta.columnsListaDetalleVentas);
        
        cargaDetalleVentas();
        
    }

    private void cargaDetalleVentas() {
        try {
            log.debug(VariablesReporte.vCorrelativo);
            String pCodCia   = vFilaInput.get(7).toString();
            String pCodLocal = vFilaInput.get(8).toString();
            String pNumPed   = vFilaInput.get(0).toString();

            DBReceta.obtieneDetalleRegistroReceta(tableModel, pCodCia,pCodLocal,pNumPed);
            FarmaUtility.ordenar(tblDetallePedidos, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el Detalle de las Ventas : \n" +
                    sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblDetallePedidos);
        log.debug("Numero Comprobante" + VariablesReporte.vNComprobante);
        this.setLocationRelativeTo(null);
        
        if(validaStock){
            if(existeStock()){
                cerrarVentana(true);
            }
            else
                FarmaUtility.showMessage(this, "No hay stock en todos los productos revise.", tblDetallePedidos);    
        }
        
    }
    
    public boolean existeStock(){
        boolean resultado = false;
        
        String pObs = "N";
        
        for(int i=0;i<tableModel.data.size();i++){
            pObs = FarmaUtility.getValueFieldArrayList(tableModel.data,i,7);
            if(!pObs.equalsIgnoreCase("OK")){
                resultado = false;
                return resultado;
            }
        }
        
        resultado = true;
        
        return resultado;
    }
    

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblDetallePedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblDetallePedidos_keyReleased(KeyEvent e) {
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
        } else if (UtilityPtoVenta.verificaVK_F11(e) && lblF11.isVisible()) {
            aceptarResumenDelivery();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */

    private void buscaDetalleVentas(String pCodigo) {
        VariablesReporte.vCorrelativo = pCodigo;
        cargaDetalleVentas();
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

}
