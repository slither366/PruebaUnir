package dental.laboratorio;


import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import dental.laboratorio.reference.DBMantenimiento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
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
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import venta.reference.UtilityPtoVenta;


public class DlgMantMarca extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantMarca.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNombre = new JButtonLabel();
    private JTextFieldSanSerif txtNombreLaboratorio = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private JLabel lblTipoActual = new JLabel();
    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";
    private JButtonLabel lblDireccion = new JButtonLabel();
    private JTextField txtDireccion = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JLabel lblTelefono = new JLabel();

    public DlgMantMarca() {
        this(null, "", false, "I", "");
    }

    public DlgMantMarca(Frame parent, String title, boolean modal, String pAccion, String pIdUpdate) {
        super(parent, title, modal);
        try {
            if (pAccion.trim().equalsIgnoreCase("I"))
                pInsert = true;
            else if (pAccion.trim().equalsIgnoreCase("U")) {
                this.pId = pIdUpdate;
                pUpdate = true;
            }
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(442, 254));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento Marca :");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 160));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtNombreLaboratorio.setBounds(new Rectangle(215, 55, 130, 20));
        lblNombre.setText("Nuevo Nombre :");
        lblNombre.setMnemonic('N');
        lblNombre.setBounds(new Rectangle(20, 50, 105, 15));
        lblNombre.setForeground(new Color(0, 114, 169));
        lblNombre.setFocusable(false);
        txtNombreLaboratorio.setBounds(new Rectangle(135, 45, 245, 20));
        txtNombreLaboratorio.setLengthText(300);


        txtNombreLaboratorio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombreLaboratorio_keyPressed(e);
                }
            });
        btnF11.setBounds(new Rectangle(5, 185, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 185, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    btnF11_mouseClicked(e);
                }
        });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        lblTipoActual.setText("Nueva Marca  :");
        lblTipoActual.setBounds(new Rectangle(15, 5, 335, 30));
        lblTipoActual.setFont(new Font("SansSerif", 1, 14));
        lblDireccion.setText("Nueva Direccion :");
        lblDireccion.setBounds(new Rectangle(20, 80, 105, 20));
        lblDireccion.setForeground(new Color(0, 114, 169));
        lblDireccion.setFont(new Font("SansSerif", 1, 11));
        lblDireccion.setMnemonic('D');
        txtDireccion.setBounds(new Rectangle(135, 80, 245, 20));
        txtDireccion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDireccion_keyPressed(e);
                }
            });
        txtTelefono.setBounds(new Rectangle(135, 115, 245, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }
            });
        lblTelefono.setText("Nuevo Tel\u00e9fono :");
        lblTelefono.setBounds(new Rectangle(20, 115, 105, 20));
        lblTelefono.setFont(new Font("SansSerif", 1, 11));
        lblTelefono.setForeground(new Color(0, 114, 169));
        pnlTitle.add(lblTelefono, null);
        pnlTitle.add(txtTelefono, null);
        pnlTitle.add(txtDireccion, null);
        pnlTitle.add(lblDireccion, null);
        pnlTitle.add(lblTipoActual, null);
        pnlTitle.add(txtNombreLaboratorio, null);
        pnlTitle.add(lblNombre, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {

    }

    private void this_windowClosing(WindowEvent e) {
        cerrarVentana(true);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaOpcion();
        FarmaUtility.moveFocus(txtNombreLaboratorio);
    }

    private void cargaOpcion() {
        if (pUpdate) {
            loadData(pId);
        } else {
            if (pInsert) {
                txtNombreLaboratorio.setText("");
            }
        }
    }

    private void loadData(String pIdValor) {
        try {
            ArrayList vDatos = new ArrayList();
            DBMantenimiento.getLabId(vDatos, pIdValor);
            if (vDatos.size() == 1) {
                // se puede mostrar los datos
                lblTipoActual.setText("Codigo / Nombre Laboratorio :" +
                                      ((ArrayList)(vDatos.get(0))).get(0).toString() + " - " +
                                      ((ArrayList)(vDatos.get(0))).get(1).toString());
                txtNombreLaboratorio.setText(((ArrayList)(vDatos.get(0))).get(1).toString());
                txtDireccion.setText(((ArrayList)(vDatos.get(0))).get(2).toString());
                txtTelefono.setText(((ArrayList)(vDatos.get(0))).get(3).toString());
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            evento_f11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void evento_f11() {
        grabar();
    }

    private void grabar() {

        String pNombreLab = txtNombreLaboratorio.getText().trim();
        String pDireccionLab = txtDireccion.getText().trim();
        String pTelefonoLab = txtTelefono.getText().trim();

        try {
            DBMantenimiento.grabaLaboratorio(pNombreLab, pDireccionLab, pTelefonoLab, pId, pInsert, pUpdate);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se grabó con exitosamente", null);
            cerrarVentana(true);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            FarmaUtility.liberarTransaccion();
            sqle.printStackTrace();
            log.info(sqle.getMessage());
            FarmaUtility.showMessage(this, "Ocurrió un error al hacer el ejecutar el proceso.\n" +
                    sqle.getMessage(), null);
        }
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void txtNombreLaboratorio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtDireccion);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtTelefono);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtNombreLaboratorio);
        }else{
            chkKeyPressed(e);
        }
    }
}
