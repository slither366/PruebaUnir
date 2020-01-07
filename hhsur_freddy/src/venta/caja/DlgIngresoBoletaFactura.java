package venta.caja;


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


public class DlgIngresoBoletaFactura extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoBoletaFactura.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNroComprobante = new JButtonLabel();    
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM="";
    private String vRpta="";
    private boolean estrcm = false;
    private boolean estvta = false;
    //private JTextField txtFecha = new JTextField();
    
    private String pTipoComprobante = "01";
    
    public DlgIngresoBoletaFactura() {
        this(null, "", false,"01");
    }

    public DlgIngresoBoletaFactura(Frame parent, String title, boolean modal,String pTipComp)
    {
        super(parent, title, modal);
        try
        {
            pTipoComprobante = pTipComp;
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
        this.setSize(new Dimension(419, 126));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso Comprobante Boleta / Factura");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 55));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtNroComprobante.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Nro. Comprobante:");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 20, 105, 15));
        lblNroComprobante.setForeground(new Color(0, 114, 169));
        lblNroComprobante.setFocusable(false);
        txtSerie.setBounds(new Rectangle(160, 20, 60, 20));
        txtSerie.setLengthText(3);
        txtSerie.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtSerie_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                        txtSerie_keyTyped(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(235, 20, 145, 20));
        txtNroComprobante.setLengthText(7);        
        txtNroComprobante.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNroComprobante_keyPressed(e);
                    }


                public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });


        btnF11.setBounds(new Rectangle(5, 75, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 75, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(txtSerie, null);
        pnlTitle.add(lblNroComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize()
    {
        cargaComprobantePorDefecto();
    }

    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtSerie);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtSerie);        
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {
            if(validarDatos()){
            //RHERRERA 
            txtSerie.setText(FarmaUtility.caracterIzquierda(txtSerie.getText(), 
                                                                  3, "0"));
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                  7, "0"));
            
            String vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim(); 
            VariablesCaja.vNumCompBoletaFactura_Impr=vNumCompPago.trim();
            cerrarVentana(true);
        }
            else
                FarmaUtility.showMessage(this,"Falta Ingresar Información.",txtSerie);
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
    
    private boolean validarDatos()
    {
        boolean flag = true;
        if(txtSerie.getText().trim().length()==0)
            return flag = false;
        
        if(txtNroComprobante.getText().trim().length()==0)
            return flag = false;
        
        return flag;
    }

    private void txtSerie_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            txtSerie.setText(FarmaUtility.caracterIzquierda(txtSerie.getText(), 
                                                                  3, "0"));
            FarmaUtility.moveFocus(txtNroComprobante);
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                  7, "0"));
            FarmaUtility.moveFocus(txtSerie);
        }
        chkKeyPressed(e);
    }
    
    //RUDY LLANTOY 23.05.13 Limpia el formulario y pone el foco en cmbTipoCom
    private void limpiarCampos()
    {
        txtSerie.setText("");
        txtNroComprobante.setText("");
        FarmaUtility.moveFocus(txtSerie);
    }


    private void txtSerie_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtSerie,e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtNroComprobante,e);
    }


    private void cargaComprobantePorDefecto() {
        
        //if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA) || VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA) 
        //   ){
        String pCadena = "";

        try {
            pCadena =DBModuloVenta.getNumComprobanteDefault(pTipoComprobante);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(pCadena.split("@").length>0){
            txtSerie.setText(pCadena.split("@")[0]);
            txtNroComprobante.setText(pCadena.split("@")[1]);    
        }
    }
}
