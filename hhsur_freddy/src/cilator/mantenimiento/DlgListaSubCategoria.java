package cilator.mantenimiento;

import cilator.mantenimiento.reference.ContantsMantenimiento;
import cilator.mantenimiento.reference.DBMantenimiento;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;


import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.*;


import common.*;

import venta.administracion.usuarios.DlgListaRolesAsignados;
import venta.administracion.usuarios.DlgMantUsuarios;
import venta.administracion.usuarios.reference.*;

import venta.reference.ConstantsPtoVenta;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

public class DlgListaSubCategoria extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaSubCategoria.class);
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlBusqueda = new JPanelTitle();
    private JPanelTitle pnlHeaderUsuarios = new JPanelTitle();
    private JTextFieldSanSerif txtApePaterno = new JTextFieldSanSerif();
    private JScrollPane scrListaUsuarios = new JScrollPane();
    private JTable tblListausuarios = new JTable();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblModificar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaSubCategoria() {
        this(null, "", false);
    }

    public DlgListaSubCategoria(Frame parent, String title, boolean modal) 
    {   super(parent, title, modal);
        myParentFrame = parent;
        /*
        if (pAccion.trim().equalsIgnoreCase("I"))
            pInsert = true;
        else if (pAccion.trim().equalsIgnoreCase("U")){
            this.pId = pIdUpdate;
            pUpdate = true;
        }*/
        
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
        this.setSize(new Dimension(825, 422));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlBusqueda.setBounds(new Rectangle(10, 10, 790, 30));
        pnlHeaderUsuarios.setBounds(new Rectangle(10, 45, 790, 25));
        txtApePaterno.setBounds(new Rectangle(20, 5, 405, 20));
        txtApePaterno.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtApePaterno_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtApePaterno_keyPressed(e);
            }
        });


        scrListaUsuarios.setBounds(new Rectangle(10, 70, 790, 280));
        tblListausuarios.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListausuarios_keyPressed(e);
            }
        });
        lblCrear.setBounds(new Rectangle(10, 355, 75, 20));
        lblCrear.setText("[F2] Crear");
        lblCrear.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                lblCrear_mouseClicked(e);
            }
        });
        lblModificar.setBounds(new Rectangle(90, 355, 95, 20));
        lblModificar.setText("[F3] Modificar");
        lblModificar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                modificar(e);
            }
        });
        lblSalir.setBounds(new Rectangle(715, 360, 75, 20));
        lblSalir.setText("[Esc] Salir");
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        scrListaUsuarios.getViewport().add(tblListausuarios, null);
        jContentPane.add(scrListaUsuarios, null);
        jContentPane.add(pnlHeaderUsuarios, null);
        pnlBusqueda.add(txtApePaterno, null);
        jContentPane.add(pnlBusqueda, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Lista SubCategoria");
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ContantsMantenimiento.columnsListaSubCategoria, ContantsMantenimiento.defaultValuesListaSubCategoria,
                                    0);
        FarmaUtility.initSimpleList(tblListausuarios, tableModel,ContantsMantenimiento.columnsListaSubCategoria);
        cargaLista();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        
        FarmaUtility.moveFocus(txtApePaterno);
    }


    private void txtApePaterno_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void txtApePaterno_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListausuarios, txtApePaterno, 1);

        chkKeyPressed(e);
    }

    private void tblListausuarios_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListausuarios, txtApePaterno, 1);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            accion("I");
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            accion("U");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaLista() {
        try {
            DBMantenimiento.getListaSubCATEGORIAs(tableModel);

            if (tblListausuarios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListausuarios, tableModel, 1, "asc");
            log.debug("se cargo la lista de usuarios");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los usuarios. \n " + e.getMessage(), tblListausuarios);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnApePaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApePaterno);
    }

    private void btnRelacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListausuarios);
    }


    private void lblCrear_mouseClicked(MouseEvent e) {
        accion("I");
    }

    private void modificar(MouseEvent e) {
        accion("U");
    }

    public void accion(String pTipo) {
        String pId = "";
        
        if(pTipo.equalsIgnoreCase("U")){
            pId = FarmaUtility.getValueFieldArrayList(tableModel.data,tblListausuarios.getSelectedRow(),0);
        }
        
        DlgMantSubCategoria dlg = new DlgMantSubCategoria(this.myParentFrame, "", true, pTipo,pId);
        dlg.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaLista();
        }
    }

}
