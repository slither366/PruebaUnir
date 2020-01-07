package venta.hospital.soat;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.hospital.soat.reference.ConstantsSoat;

import venta.hospital.soat.reference.DBSoat;

import venta.administracion.impresoras.DlgDatosImpresoras;
import venta.administracion.impresoras.reference.ConstantsImpresoras;
import venta.administracion.impresoras.reference.DBImpresoras;
import venta.administracion.impresoras.reference.VariablesImpresoras;
import venta.caja.reference.UtilityCaja;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaLiquidaciones extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaLiquidaciones.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaImp = new JPanelTitle();


    private JLabelFunction lblsc = new JLabelFunction();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JTable tblListaImpresoras = new JTable();

    private JButtonLabel btnRelacionImp = new JButtonLabel();
    private JTextField jTextField1 = new JTextField();
    private JButton btnNuevo = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cboSeguros = new JComboBox();
    private JButton btnBuscar = new JButton();
    private JTextField jTextField2 = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaLiquidaciones() {
        this(null, "", false);
    }

    public DlgListaLiquidaciones(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1077, 383));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Resumen de Liquidaciones");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderListaImp.setBounds(new Rectangle(15, 45, 1040, 25));
        lblsc.setBounds(new Rectangle(960, 315, 95, 20));
        lblsc.setText("[Esc]Salir");
        scrListaImpresoras.setBounds(new Rectangle(15, 70, 1040, 230));
        tblListaImpresoras.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaImpresoras_keyPressed(e);
            }
        });
        btnRelacionImp.setText("Liquidaciones Generadas");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
        btnRelacionImp.setMnemonic('r');
        btnRelacionImp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionImp_actionPerformed(e);
            }
        });
        jTextField1.setBounds(new Rectangle(90, 10, 110, 20));
        jTextField1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jTextField1_keyPressed(e);
                }
            });
        btnNuevo.setText("Nueva Liquidaci\u00f3n");
        btnNuevo.setBounds(new Rectangle(20, 305, 155, 30));
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuevo_actionPerformed(e);
            }
        });
        jLabel1.setText("Seguros: ");
        jLabel1.setBounds(new Rectangle(425, 15, 60, 15));
        cboSeguros.setBounds(new Rectangle(500, 10, 205, 20));
        cboSeguros.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cboSeguros_keyPressed(e);
                }
            });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(725, 10, 95, 20));
        jTextField2.setBounds(new Rectangle(280, 10, 120, 20));
        jLabel2.setText("Desde");
        jLabel2.setBounds(new Rectangle(20, 15, 55, 15));
        jLabel3.setText("Hasta");
        jLabel3.setBounds(new Rectangle(215, 15, 50, 15));
        jButton1.setText("Ingresar Pagos ");
        jButton1.setBounds(new Rectangle(235, 305, 135, 30));
        jButton2.setText("Facturar");
        jButton2.setBounds(new Rectangle(440, 305, 90, 30));
        jButton3.setText("Exportar Fact.Previa");
        jButton3.setBounds(new Rectangle(610, 305, 155, 30));
        jContentPane.add(jButton3, null);
        jContentPane.add(jButton2, null);
        jContentPane.add(jButton1, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(jTextField2, null);
        jContentPane.add(btnBuscar, null);
        jContentPane.add(cboSeguros, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(btnNuevo, null);
        jContentPane.add(jTextField1, null);
        scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        cargaAseguradora();

    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsSoat.columnsListaLiquidacion, ConstantsSoat.defaultValuesListaLiquidacion,
                                    0);
        FarmaUtility.initSimpleList(tblListaImpresoras, tableModel, ConstantsSoat.columnsListaLiquidacion);
        cargaListaAtencionesSoat();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        VariablesPtoVenta.vEjecutaAccionTecla = false;
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaImpresoras);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnRelacionImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaImpresoras);
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargaListaAtencionesSoat() {

        try {
            DBSoat.getListaLiquidaciones(tableModel);
            if (tblListaImpresoras.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaImpresoras, tableModel, 0, "asc");
            log.debug("se cargo la lista de impresoras");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de impresoras. \n " + e.getMessage(),
                                     tblListaImpresoras);
        }
    }

    private void tblListaImpresoras_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnNuevo_actionPerformed(ActionEvent e) {
        registraNuevo();
    }

    private void registraNuevo() {
        DlgListaNuevaLiquidacion dlgAtencion = new DlgListaNuevaLiquidacion(myParentFrame, "", true);
        dlgAtencion.setVisible(true);
    }

    private void cargaAseguradora() {
        /*ArrayList parametros2 = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cboSeguros,
                                    "cmbAseguradora",
                                    "HH_SOAT.F_GET_CMB_ASEGURADORA()", 
                                    parametros2,
                                    false, 
                                    true);*/
        cboSeguros.addItem("Seleccione");

        cboSeguros.addItem("Rimac");

        cboSeguros.addItem("Pacifico");

        cboSeguros.addItem("Otros");
    }

    private void jTextField1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cboSeguros_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void listaNuevoRegistro() {
        tableModel.clearTable();
        ArrayList myArray = null;
        String myRow = "";
        StringTokenizer st ;
        myRow = "00010ÃRIMACÃ44324600ÃDIEGO UBILLUZÃ10/08/2015 10:10 AMÃERAMIREZÃESPERA";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        tableModel.insertRow(myArray);

        myRow = "00020ÃPACIFICOÃ915100000ÃMICHAEL SMITHÃ10/08/2015 08:00 AMÃERAMIREZÃPOR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
        myArray.add(st.nextToken());
        }
        tableModel.insertRow(myArray);
        
        myRow = "00210ÃRIMACÃ123456789ÃCarlos AcevedoÃ14/08/2015 09:10 AMÃDUBILLUZÃESPERA";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
        myArray.add(st.nextToken());
        }
        tableModel.insertRow(myArray);
    }

    private void btnVer_actionPerformed(ActionEvent e) {
        int fila = tblListaImpresoras.getSelectedRow();
        if(fila>=0){
            DlgNuevaAtencion dlgAtencion = new DlgNuevaAtencion(myParentFrame, "", true);
            dlgAtencion.setVVisualizar(true);
            dlgAtencion.setVisible(true);
        }
        else
            FarmaUtility.showMessage(this, "Debe de Seleccionar una atención", tblListaImpresoras);
    }
}
