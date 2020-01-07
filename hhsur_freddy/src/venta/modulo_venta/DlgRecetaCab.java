package venta.modulo_venta;


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
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecetaCab extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRecetaCab.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNroComprobante = new JButtonLabel();    
    private JTextFieldSanSerif txtDniMedico = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JTextFieldSanSerif txtNombreMedico = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM="";
    private String vRpta="";
    private boolean estrcm = false;
    private boolean estvta = false;
    //private JTextField txtFecha = new JTextField();
    private JTextFieldSanSerif  txtNombrePaciente  = new JTextFieldSanSerif();
    private String vNumPedidoVta = "";
    public DlgRecetaCab() {
        this(null, "", false,"");
    }

    public DlgRecetaCab(Frame parent, String title, boolean modal,String vNumPed)
    {
        super(parent, title, modal);
        try
        {
            MyParentFrame = parent;
            vNumPedidoVta = vNumPed;
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
        this.setSize(new Dimension(668, 164));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso Datos de Receta");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 655, 100));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        lblNroComprobante.setText("Dni M\u00e9dico");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 15, 105, 15));
        lblNroComprobante.setForeground(new Color(0, 114, 169));
        lblNroComprobante.setFocusable(false);
        lblNroComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblNroComprobante_actionPerformed(e);
                    }
                });
        txtDniMedico.setBounds(new Rectangle(155, 15, 115, 20));
        txtDniMedico.setLengthText(10);
        txtDniMedico.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtSerie_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                    txtSerie_keyTyped(e);
                }
                });
        lblMonto.setText("Nombre M\u00e9dico");
        lblMonto.setMnemonic('M');
        lblMonto.setBounds(new Rectangle(20, 45, 120, 15));
        lblMonto.setForeground(new Color(0, 114, 169));
        lblMonto.setFocusable(false);
        lblMonto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblMonto_actionPerformed(e);
                    }
                });
        
        lblFecha.setText("Nombre Paciente :");
        lblFecha.setMnemonic('F');
        lblFecha.setBounds(new Rectangle(20, 70, 110, 15));
        lblFecha.setForeground(new Color(0, 114, 169));
        lblFecha.setFocusable(false);
        lblFecha.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblFecha_actionPerformed(e);
                    }
                });            
            
        txtNombreMedico.setBounds(new Rectangle(155, 45, 490, 20));
        txtNombreMedico.setLengthText(1000);
        txtNombreMedico.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtMonto_keyPressed(e);
                }
            });
        btnF11.setBounds(new Rectangle(5, 115, 117, 20));
        btnEsc.setBounds(new Rectangle(545, 115, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        txtNombrePaciente.setBounds(new Rectangle(155, 70, 490, 20));
        txtNombrePaciente.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFecha_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }
            });
        txtNombrePaciente.setLengthText(1000);
        pnlTitle.add(txtNombrePaciente, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(lblFecha, null);
        pnlTitle.add(txtNombreMedico, null);
        pnlTitle.add(txtDniMedico, null);
        pnlTitle.add(lblNroComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize()
    {
        cargaCombo();
    }

    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtDniMedico);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDniMedico); 
        //FarmaUtility.showMessage(this, "Abrio la Ventana..", txtDniMedico);
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {
            if(validarDatos()){
                grabaDatosReceta(txtDniMedico.getText().trim(),txtNombreMedico.getText().trim(),txtNombrePaciente.getText().trim());
                FarmaUtility.showMessage(this,"Se grabo la receta para el Médico con DNI ingresado.",txtDniMedico);
                cerrarVentana(true);
            }
            else
                FarmaUtility.showMessage(this,"Falta Ingresar Información.",txtDniMedico);
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
        if(txtDniMedico.getText().trim().length()==0)
            return flag = false;
        
        if(txtNombreMedico.getText().trim().length()==0)
            return flag = false;
        
        
        if(txtNombrePaciente.getText().trim().length()==0)
            return flag = false;        
        
        return flag;
    }

    private void txtSerie_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtNombreMedico);
        }
        chkKeyPressed(e);
    }


    private void txtMonto_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtNombrePaciente);
        }
        chkKeyPressed(e);
    }

    
    //RUDY LLANTOY 23.05.13 Limpia el formulario y pone el foco en cmbTipoCom
    private void limpiarCampos()
    {
        
        txtDniMedico.setText("");
        txtNombreMedico.setText("");
        FarmaUtility.moveFocus(txtDniMedico);
    }


    // RUDY LLANTOY 23.05.13 Valida una venta normal que no se RCM
    private void validarVta()
    {
        log.debug("Obtiene Correlativo");
        String vTipoComp = "";
        String vMontoNeto = "";
        String vNumCompPago = "";
        VariablesModuloVentas.vNumPedVta_new = "";
        VariablesModuloVentas.vMontoNeto_new = "";
        //getDatos(vRpta);
    }

    private void lblNroComprobante_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtDniMedico);
    }

    private void lblMonto_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtNombreMedico);
    }

    private void lblFecha_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtNombrePaciente);
    }
    
    private void cargaCombo()
    {
        //FarmaLoadCVL.loadCVLfromArrays(cmbTipoComp,
        //                               ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_CODIGO,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_DESCRIPCION,true);
        
        //LLEIVA 03-Feb-2014 Se realiza la carga desde la BD
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
    }


    
    private void txtSerie_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtDniMedico,e);
    }

    

    public boolean isEstrcm()
    {
        return estrcm;
    }

    public void setEstrcm(boolean estrcm)
    {
        this.estrcm = estrcm;
    }

    private void txtFecha_keyReleased(KeyEvent e)
    {
        FarmaUtility.dateComplete(txtNombrePaciente,e);
    }

    private void txtFecha_keyPressed(KeyEvent e)
    {
        chkKeyPressed(e);
    }


    private void grabaDatosReceta(String vDni, String vNombreMedico, String vNombrePaciente) {
        // vNumPedidoVta
        try {
            DBModuloVenta.grabarCabRecetaNuevo(vNumPedidoVta,vDni,vNombreMedico,vNombrePaciente);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
        }
    }
}
