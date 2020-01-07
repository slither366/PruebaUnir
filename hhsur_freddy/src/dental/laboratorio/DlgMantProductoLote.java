package dental.laboratorio;


import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import dental.laboratorio.reference.DBMantenimiento;
import dental.laboratorio.reference.VariablesMantenimiento;

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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;


public class DlgMantProductoLote extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProductoLote.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNombre = new JButtonLabel();
    private JTextFieldSanSerif txtNumLote = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private JLabel lblTipoActual = new JLabel();
    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";
    private JButtonLabel lblDireccion = new JButtonLabel();
    private JTextField txtFechaVencimiento = new JTextField();
    
    private ArrayList vDatosLote = new ArrayList();

    public DlgMantProductoLote() {
        this(null, "", false, "I", "");
    }

    public DlgMantProductoLote(Frame parent, String title, boolean modal, String pAccion, String pIdUpdate) {
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
        this.setSize(new Dimension(429, 219));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento Lote :");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 130));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtNumLote.setBounds(new Rectangle(215, 55, 130, 20));
        lblNombre.setText("Num. Lote :");
        lblNombre.setMnemonic('N');
        lblNombre.setBounds(new Rectangle(20, 50, 105, 15));
        lblNombre.setForeground(new Color(0, 114, 169));
        lblNombre.setFocusable(false);
        txtNumLote.setBounds(new Rectangle(135, 45, 245, 20));
        txtNumLote.setLengthText(300);


        txtNumLote.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombreLaboratorio_keyPressed(e);
                }
            });
        btnF11.setBounds(new Rectangle(10, 150, 117, 20));
        btnEsc.setBounds(new Rectangle(295, 150, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnF11_mouseClicked(e);
            }
        });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        lblTipoActual.setText("Lote Producto :");
        lblTipoActual.setBounds(new Rectangle(15, 5, 335, 30));
        lblTipoActual.setFont(new Font("SansSerif", 1, 14));
        lblDireccion.setText("Fecha Venc.");
        lblDireccion.setBounds(new Rectangle(20, 80, 105, 20));
        lblDireccion.setForeground(new Color(0, 114, 169));
        lblDireccion.setFont(new Font("SansSerif", 1, 11));
        lblDireccion.setMnemonic('D');
        txtFechaVencimiento.setBounds(new Rectangle(135, 80, 245, 20));
        txtFechaVencimiento.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDireccion_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaVencimiento_keyReleased(e);
                }
            });
        pnlTitle.add(txtFechaVencimiento, null);
        pnlTitle.add(lblDireccion, null);
        pnlTitle.add(lblTipoActual, null);
        pnlTitle.add(txtNumLote, null);
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
        vDatosLote = new ArrayList();
        FarmaUtility.centrarVentana(this);
        cargaOpcion();
        FarmaUtility.moveFocus(txtNumLote);
    }

    private void cargaOpcion() {
        if (pUpdate) {
            loadData(pId);
        } else {
            if (pInsert) {
                txtNumLote.setText("");
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
                txtNumLote.setText(((ArrayList)(vDatos.get(0))).get(1).toString());
                txtFechaVencimiento.setText(((ArrayList)(vDatos.get(0))).get(2).toString());
                
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
        
        boolean validacion = validaCampos();
        
        if(validacion){
            String pNumLote = txtNumLote.getText().trim();
            String pFechaVencimiento = txtFechaVencimiento.getText().trim();

            try {
                if(pInsert){
                    
                    vDatosLote.add(pNumLote);
                    vDatosLote.add(pFechaVencimiento);
                    
                    cerrarVentana(true);
                }else if(pUpdate){
                    //DBMantenimiento.setEstadoProducto("");
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "Se grabó con exitosamente", null);
                }
                
                cerrarVentana(true);
            } catch (Exception sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                log.info(sqle.getMessage());
                FarmaUtility.showMessage(this, "Ocurrió un error al hacer el ejecutar el proceso.\n" +
                        sqle.getMessage(), null);
                cerrarVentana(false);
            }
        }
    }
    
    private boolean validaCampos(){
        String mensaje = "";
        if(txtNumLote.getText().trim().length() <= 0){
            mensaje = "Ingresar el campo Num. Lote.";
            FarmaUtility.moveFocus(txtNumLote);
        }
        if(txtFechaVencimiento.getText().trim().length() <= 0){
            if(mensaje.length() > 0){
                mensaje = mensaje + "\nIngresar el campo Cantidad.";
            }else{
                mensaje = "\nIngresar el campo Fecha Vencimiento.";
            }
            FarmaUtility.moveFocus(txtFechaVencimiento);
        }
        if(mensaje.length() > 0){
            JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void txtNombreLaboratorio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtFechaVencimiento);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtNumLote);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtFechaVencimiento_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaVencimiento,e);
    }

    public void setVDatosLote(ArrayList vDatosLote) {
        this.vDatosLote = vDatosLote;
    }

    public ArrayList getVDatosLote() {
        return vDatosLote;
    }
}
