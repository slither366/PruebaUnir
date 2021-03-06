package cilator.mantenimiento;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.recetario.reference.DBRecetario;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.caja.reference.VariablesCaja;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;


public class DlgMantTipoDeCambio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantTipoDeCambio.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNroComprobante = new JButtonLabel();
    private JTextFieldSanSerif txtTipoActual = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM="";
    private String vRpta="";
    private boolean estrcm = false;
    private boolean estvta = false;
    private JLabel lblTipoActual = new JLabel();
    //private JTextField txtFecha = new JTextField();

    public DlgMantTipoDeCambio() {
        this(null, "", false);
    }

    public DlgMantTipoDeCambio(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        try
        {
            MyParentFrame = parent;
            jbInit();
            initialize();
        }
        catch (Exception e)
        {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception 
    {
        this.setSize(new Dimension(419, 163));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento Tipo de Cambio :");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 85));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtTipoActual.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Actualizar por S / :");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 50, 105, 15));
        lblNroComprobante.setForeground(new Color(0, 114, 169));
        lblNroComprobante.setFocusable(false);
        txtTipoActual.setBounds(new Rectangle(135, 45, 170, 20));
        txtTipoActual.setLengthText(7);        
        txtTipoActual.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtNroComprobante_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });


        btnF11.setBounds(new Rectangle(5, 105, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 105, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnF11_mouseClicked(e);
                }
            });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        lblTipoActual.setText("Tipo de Cambio actual es :  S/ 3.30");
        lblTipoActual.setBounds(new Rectangle(15, 5, 335, 30));
        lblTipoActual.setFont(new Font("SansSerif", 1, 11));
        pnlTitle.add(lblTipoActual, null);
        pnlTitle.add(txtTipoActual, null);
        pnlTitle.add(lblNroComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize()
    {
        
    }

    private void this_windowClosing(WindowEvent e)
    {
        cerrarVentana(true);
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtTipoActual);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        try {
            lblTipoActual.setText("Tipo de Cambio actual es :  S/ "+DBCaja.getTipoCambioActual());
        } catch (SQLException f) {
        }
        FarmaUtility.moveFocus(txtTipoActual);        
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {
            evento_f11();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void txtNroComprobante_keyTyped(KeyEvent e)
    {
       // FarmaUtility.admitirDigitos(txtTipoActual,e);
    }


    private void evento_f11() {
        double vTipoCambio = 0;
        boolean vValor = false;
        try {
            vTipoCambio = Double.parseDouble(txtTipoActual.getText().trim());
            vValor = true;
        } catch (Exception nfe) {
            // TODO: Add catch code
           // nfe.printStackTrace();
           vValor = false;
        }
        if(!vValor){
            FarmaUtility.showMessage(this,"Por favor de colocar un valor correcto",txtTipoActual);
        }
        else{
            String cambioa;
            try {
                DBCaja.setTipoCambio(FarmaUtility.formatNumber(vTipoCambio, 2).replace(",",""));
                FarmaUtility.aceptarTransaccion();
                lblTipoActual.setText("Tipo de Cambio actual es :  S/ "+DBCaja.getTipoCambioActual());
                FarmaUtility.showMessage(this,"Se hizo el cambio correctamente\n.Por favor de salir de todo el sistema y volver a ingresar",txtTipoActual);
            } catch (SQLException e) {
                FarmaUtility.showMessage(this,"Error al hacer el cambio .\n"+e.getMessage(),txtTipoActual);
            }
            
        }
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
