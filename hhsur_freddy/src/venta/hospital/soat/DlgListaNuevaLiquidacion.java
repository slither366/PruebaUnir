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


public class DlgListaNuevaLiquidacion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaNuevaLiquidacion.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaImp = new JPanelTitle();


    private JLabelFunction lblsc = new JLabelFunction();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JTable tblListaImpresoras = new JTable();

    private JButtonLabel btnRelacionImp = new JButtonLabel();
    private JButton btnNuevo = new JButton();
    private JButton btnVer = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cboSeguros = new JComboBox();
    private JButton btnBuscar = new JButton();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaNuevaLiquidacion() {
        this(null, "", false);
    }

    public DlgListaNuevaLiquidacion(Frame parent, String title, boolean modal) {
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
    // M�todo "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(926, 383));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Nueva Liquidaci\u00f3n");
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
        pnlHeaderListaImp.setBounds(new Rectangle(15, 45, 895, 25));
        lblsc.setBounds(new Rectangle(815, 320, 95, 20));
        lblsc.setText("[Esc]Salir");
        scrListaImpresoras.setBounds(new Rectangle(15, 70, 895, 230));
        tblListaImpresoras.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaImpresoras_keyPressed(e);
            }
        });
        btnRelacionImp.setText("Atenciones Pendientes de Liquidar");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 250, 15));
        btnRelacionImp.setMnemonic('r');
        btnRelacionImp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionImp_actionPerformed(e);
            }
        });
        btnNuevo.setText("Grabar Liquidaci\u00f3n");
        btnNuevo.setBounds(new Rectangle(15, 305, 150, 35));
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNuevo_actionPerformed(e);
            }
        });
        btnVer.setText("Ver Datos");
        btnVer.setBounds(new Rectangle(190, 305, 135, 35));
        btnVer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnVer_actionPerformed(e);
                }
            });
        jLabel1.setText("Seguros: ");
        jLabel1.setBounds(new Rectangle(10, 15, 75, 20));
        cboSeguros.setBounds(new Rectangle(90, 15, 205, 20));
        cboSeguros.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cboSeguros_keyPressed(e);
                }
            });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(325, 15, 95, 20));
        jContentPane.add(btnBuscar, null);
        jContentPane.add(cboSeguros, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(btnVer, null);
        jContentPane.add(btnNuevo, null);
        scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        cargaAseguradora();

    };

    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsSoat.columnsListaAtencionSoatLiq, ConstantsSoat.defaultValuesListaAtencionSoatLiq,
                                    0);
        FarmaUtility.initSelectList(tblListaImpresoras, tableModel, ConstantsSoat.columnsListaAtencionSoatLiq);
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
    // Metodos de l�gica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargaListaAtencionesSoat() {

        try {
            DBSoat.getListaAtencionesLiquidar(tableModel);
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
        FarmaUtility.showMessage(this, "Se Grab� con exito la liquidaci�n.\n" +
            "Por favor de Proceder a trabajar la liquidaci�n creada.\n" +
            "Gracias", null);
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
        myRow = "00010�RIMAC�44324600�DIEGO UBILLUZ�10/08/2015 10:10 AM�ERAMIREZ�ESPERA";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        tableModel.insertRow(myArray);

        myRow = "00020�PACIFICO�915100000�MICHAEL SMITH�10/08/2015 08:00 AM�ERAMIREZ�POR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
        myArray.add(st.nextToken());
        }
        tableModel.insertRow(myArray);
        
        myRow = "00210�RIMAC�123456789�Carlos Acevedo�14/08/2015 09:10 AM�DUBILLUZ�ESPERA";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
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
            FarmaUtility.showMessage(this, "Debe de Seleccionar una atenci�n", tblListaImpresoras);
    }
}
