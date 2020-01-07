package venta.modulo_venta.medico;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;

import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BORRA_DlgListaMedicos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(BORRA_DlgListaMedicos.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextFieldSanSerif txtNombreMedico = new JTextFieldSanSerif();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanelTitle pnlRelacionCliente = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblMedicos = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblFiltra = new JLabelFunction();
    private JLabelFunction lblCrea = new JLabelFunction();

    public BORRA_DlgListaMedicos() {
        this(null, "", false);
    }

    public BORRA_DlgListaMedicos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(561, 373));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Lista de Medicos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 540, 40));
        txtNombreMedico.setBounds(new Rectangle(95, 10, 295, 20));
        txtNombreMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombreMedico_keyPressed(e);
                // txtClienteJuridico_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNombreMedico_keyReleased(e);
                //txtClienteJuridico_keyReleased(e);
            }
        });
        txtNombreMedico.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtNombreMedico_focusLost(e);
                }
            });
        btnNombre.setText("Nombre :");
        btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });
        pnlRelacionCliente.setBounds(new Rectangle(10, 60, 540, 25));
        btnRelacion.setText("Relacion de Medicos ");
        btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnRelacion_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 85, 540, 225));
        jLabelFunction1.setBounds(new Rectangle(285, 315, 140, 20));
        jLabelFunction1.setText("[ ENTER ] Seleccionar");
        
        lblFiltra.setBounds(new Rectangle(10, 315, 110, 20));
        lblFiltra.setText("[ F1 ] Filtrar");
        
        
        lblCrea.setBounds(new Rectangle(135, 315, 140, 20));
        lblCrea.setText("[ F12 ] Crear");

        jLabelFunction2.setBounds(new Rectangle(435, 315, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtNombreMedico, null);
        pnlCliente.add(btnNombre, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jScrollPane1.getViewport().add(tblMedicos, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblFiltra,null);
        jPanelWhite1.add(lblCrea,null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlRelacionCliente, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        initTableListaMedicos();
        FarmaVariables.vAceptar = false;
    }

    private void initTableListaMedicos() {
        tableModelListaMedicos =
                new FarmaTableModel(ConstantsMedico.columnsListaMedicos, ConstantsMedico.defaultValuesListaMedicos, 0);
        FarmaUtility.initSimpleList(tblMedicos, tableModelListaMedicos, ConstantsMedico.columnsListaMedicos);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaListaMedicos();
        FarmaUtility.moveFocus(txtNombreMedico);
    }

    private void cargaListaMedicos() {
        try {
            DBMedico.listaTodosMedicos(tableModelListaMedicos);
            FarmaUtility.ordenar(tblMedicos, tableModelListaMedicos, 2, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblMedicos.getRowCount() <= 0) {
                VariablesModuloVentas.vSeleccionaMedico = false;
                FarmaUtility.showMessage(this, "No se encontró ningún médico para esta búsqueda.", txtNombreMedico);
                cerrarVentana(false);
            } else {
                FarmaGridUtils.showCell(tblMedicos, 0, 0);
                FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 2);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurio un error al listar los medicos \n " + sql.getMessage(),
                                     txtNombreMedico);
        }
    }

    private void cargaListaMedicosFiltro() {
        try {
            String pCadena = "";
            DBMedico.listaTodosMedicos(tableModelListaMedicos);
            FarmaUtility.ordenar(tblMedicos, tableModelListaMedicos, 2, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblMedicos.getRowCount() <= 0) {
                VariablesModuloVentas.vSeleccionaMedico = false;
                FarmaUtility.showMessage(this, "No se encontró ningún médico para esta búsqueda.", txtNombreMedico);
                cerrarVentana(false);
            } else {
                FarmaGridUtils.showCell(tblMedicos, 0, 0);
                FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 2);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurio un error al listar los medicos \n " + sql.getMessage(),
                                     txtNombreMedico);
        }
    }

    private void txtNombreMedico_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblMedicos, txtNombreMedico, 2);
    }
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionaMedico();
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            crearMedico();
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            filtrarLista();
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtNombreMedico_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblMedicos, txtNombreMedico, 2);
        chkKeyPressed(e);
    }

    private void txtNombreMedico_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtNombreMedico);
    }

    private void seleccionaMedico() {
    }

    private void crearMedico() {
        DlgAdmMedicos dlgCrea = new DlgAdmMedicos(myParentFrame, "", true);
        dlgCrea.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
        }        
    }

    private void filtrarLista() {
    }
}
