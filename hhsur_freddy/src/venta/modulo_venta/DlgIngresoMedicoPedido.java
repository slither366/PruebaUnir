package venta.modulo_venta;
import java.awt.Frame;
import java.awt.Dimension;

import java.awt.SystemColor;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import componentes.gs.componentes.JTextFieldSanSerif;
import java.awt.Rectangle;
import componentes.gs.componentes.JPanelWhite;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Font;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JButtonLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import componentes.gs.componentes.JLabelFunction;
import java.awt.event.WindowListener;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import common.*;

import consorcio.UtilityHHVenta;

import consorcio.VariablesHH;

import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.UtilityPtoVenta;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import venta.FrmEconoFar;

import venta.modulo_venta.medico.DlgAdmPaciente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import svb.fact_electronica.reference.UtilityFactElectronica;

import venta.cliente.DlgBuscaClienteJuridico;
import venta.cliente.reference.VariablesCliente;

import venta.modulo_venta.medico.DlgListaMedicos;
import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.reference.ConstantsPtoVenta;

public class DlgIngresoMedicoPedido extends JDialog 
{
  /* ************************************************************************ */
  /*                        DECLARACION PROPIEDADES                           */
  /* ************************************************************************ */
  private static final Logger log = LoggerFactory.getLogger(DlgIngresoMedicoPedido.class); 

  private Frame myParentFrame;
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
    private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();

    private JTextField txtCmp = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtNombreCompleto = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JTextField txtDniPaciente = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel txtNombrePaciente = new JLabel();
    private JLabel txtApellidoPacientePaterno = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel txtNacimientoPaciente = new JLabel();
    private JLabel txtSexoPaciente = new JLabel();
    private JPanel jPanel2 = new JPanel();
    ImageIcon imageMedico = new ImageIcon(DlgIngresoMedicoPedido.class.getResource("/venta/imagenes/medico.jpg"));
    private JButton btnMedico = new JButton(imageMedico);
    ImageIcon imagePaciente = new ImageIcon(DlgIngresoMedicoPedido.class.getResource("/venta/imagenes/paciente.jpg"));
    private JButton jButton2 = new JButton(imagePaciente);
    //medico_asociado
    ImageIcon imagemedico_asociado = new ImageIcon(DlgIngresoMedicoPedido.class.getResource("/venta/imagenes/medico_asociado.jpg"));

    private JLabel jLabel11 = new JLabel();
    private JLabelFunction jLabel6 = new JLabelFunction();
    private JLabelFunction jLabel7 = new JLabelFunction();
    private JLabelFunction jLabel9 = new JLabelFunction();
    private JLabel txtApellidoPacienteMaterno = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JButton btnMedicoAsociado = new JButton(imagemedico_asociado);
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel12 = new JLabel();
    private JLabel lblCMP_MedicoAsociado = new JLabel();
    private JLabel jLabel14 = new JLabel();
    private JLabel lblNombre_MedicoAsociado = new JLabel();
    private JPanel pnlComprobante = new JPanel();
    private JLabel lblDocumento = new JLabel();
    private JLabel lblNombres = new JLabel();
    private JTextField txtRuc = new JTextField();
    private JPanel pnlClienteRUC = new JPanel();
    private JTextField txtRazonSocial = new JTextField();
    private JLabel jLabel19 = new JLabel();
    private JTextField txtDireccion = new JTextField();
    private JLabel jLabel20 = new JLabel();
    private JTextField txtEmail = new JTextField();
    private JRadioButton rbTicket = new JRadioButton();
    private JRadioButton rbBoleta = new JRadioButton();
    private JRadioButton rbFactura = new JRadioButton();
    private JButton btnSelectCliente = new JButton();
    
    ///////////////////////////////////////////////////////////////////
    private ButtonGroup buttonGroup;


    /* ************************************************************************ */
  /*                        CONSTRUCTORES                                     */
  /* ************************************************************************ */

  public DlgIngresoMedicoPedido()
  {
    this(null, "", false);
  }

  public DlgIngresoMedicoPedido(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */
        
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(871, 515));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Datos de Pedido");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
        lblF11.setBounds(new Rectangle(705, 170, 135, 35));
    lblF11.setText("[ F11 ] Aceptar");
        lblF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF11_mouseClicked(e);
                }
            });
        lblEsc.setBounds(new Rectangle(705, 225, 135, 35));
    lblEsc.setText("[ ESC ] Cerrar");
        txtCmp.setBounds(new Rectangle(60, 20, 130, 20));
        txtCmp.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCmp_keyPressed(e);
                }
            });
        jLabel1.setText("CMP:");
        jLabel1.setBounds(new Rectangle(20, 25, 30, 15));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel2.setText("Nombre Completo");
        jLabel2.setBounds(new Rectangle(20, 55, 110, 15));
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        txtNombreCompleto.setBounds(new Rectangle(20, 80, 490, 20));
        txtNombreCompleto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombreCompleto_keyPressed(e);
                }
            });
        jLabel3.setText("DNI:");
        jLabel3.setBounds(new Rectangle(15, 20, 35, 15));
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        txtDniPaciente.setBounds(new Rectangle(55, 20, 165, 20));
        jPanel1.setBounds(new Rectangle(10, 165, 530, 115));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));
        jLabel4.setText("Nombre:");
        jLabel4.setBounds(new Rectangle(15, 50, 50, 15));
        jLabel4.setFont(new Font("Tahoma", 1, 11));
        jLabel5.setText("Apellidos:");
        jLabel5.setBounds(new Rectangle(10, 75, 55, 15));
        jLabel5.setFont(new Font("Tahoma", 1, 11));
        txtNombrePaciente.setText("Debe Ingresar Nombre");
        txtNombrePaciente.setBounds(new Rectangle(70, 50, 345, 15));
        txtApellidoPacientePaterno.setText("Debe Ingresar Apellidos");
        txtApellidoPacientePaterno.setBounds(new Rectangle(70, 75, 185, 15));
        jLabel8.setText("Nacimiento:");
        jLabel8.setBounds(new Rectangle(245, 20, 70, 15));
        jLabel8.setFont(new Font("Tahoma", 1, 11));
        txtNacimientoPaciente.setText("00/00/0000");
        txtNacimientoPaciente.setBounds(new Rectangle(325, 20, 65, 15));
        txtSexoPaciente.setText("Masculino");
        txtSexoPaciente.setBounds(new Rectangle(420, 20, 100, 15));
        txtSexoPaciente.setFont(new Font("Tahoma", 1, 11));
        jPanel2.setBounds(new Rectangle(10, 10, 530, 125));
        jPanel2.setLayout(null);
        jPanel2.setBorder(BorderFactory.createTitledBorder("Datos de Médico"));


        btnMedico.setBounds(new Rectangle(550, 5, 130, 135));
        btnMedico.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnMedico_actionPerformed(e);
                }
            });
        jButton2.setBounds(new Rectangle(550, 155, 130, 120));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        jLabel11.setText("Para ingresar los datos de M\u00e9dico y Paciente, hacer CLICK en las im\u00e1genes.");
        jLabel11.setBounds(new Rectangle(15, 285, 655, 35));
        jLabel11.setFont(new Font("SansSerif", 3, 15));
        jLabel11.setForeground(new Color(198, 0, 0));
        jLabel6.setText("[ F1 ] Ingreso M\u00e9dico");
        jLabel6.setBounds(new Rectangle(705, 10, 135, 35));
        jLabel7.setText("[ F2 ] Ingreso Paciente");
        jLabel7.setBounds(new Rectangle(705, 60, 135, 35));
        jLabel9.setText("[ F5 ] Limpiar");
        jLabel9.setBounds(new Rectangle(705, 120, 135, 35));
        jLabel9.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jLabel9_mouseClicked(e);
                }
            });
        txtApellidoPacienteMaterno.setText("jLabel10");
        txtApellidoPacienteMaterno.setBounds(new Rectangle(285, 75, 215, 15));
        jLabel10.setText("M\u00e9dico Asociado:");
        jLabel10.setBounds(new Rectangle(1225, 50, 190, 15));
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        btnMedicoAsociado.setBounds(new Rectangle(1250, 80, 210, 175));
        btnMedicoAsociado.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnMedicoAsociado_actionPerformed(e);
                }
            });
        btnMedicoAsociado.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnMedicoAsociado_keyPressed(e);
                }
            });
        jPanel3.setBounds(new Rectangle(1205, 270, 305, 110));
        jPanel3.setLayout(null);
        jPanel3.setBorder(BorderFactory.createTitledBorder("Datos Médico Asociado"));
        jLabel12.setText("CMP:");
        jLabel12.setBounds(new Rectangle(15, 20, 40, 14));
        jLabel12.setFont(new Font("SansSerif", 1, 11));
        lblCMP_MedicoAsociado.setBounds(new Rectangle(55, 20, 105, 15));
        jLabel14.setText("Nombre Completo :");
        jLabel14.setBounds(new Rectangle(10, 50, 200, 15));
        jLabel14.setFont(new Font("SansSerif", 1, 11));
        lblNombre_MedicoAsociado.setBounds(new Rectangle(20, 80, 255, 15));
        pnlComprobante.setBounds(new Rectangle(15, 325, 160, 125));
        pnlComprobante.setLayout(null);
        pnlComprobante.setBorder(BorderFactory.createTitledBorder("Seleccion de Tipo de Comprobante"));
        lblDocumento.setText("RUC : ");
        lblDocumento.setBounds(new Rectangle(5, 10, 80, 15));
        lblDocumento.setFont(new Font("SansSerif", 1, 11));
        lblNombres.setText("Razon Social");
        lblNombres.setBounds(new Rectangle(10, 40, 75, 15));
        lblNombres.setFont(new Font("SansSerif", 1, 11));
        txtRuc.setBounds(new Rectangle(90, 10, 180, 20));
        txtRuc.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRuc_keyPressed(e);
                }
            });
        pnlClienteRUC.setBounds(new Rectangle(180, 325, 660, 125));
        pnlClienteRUC.setLayout(null);
        txtRazonSocial.setBounds(new Rectangle(90, 35, 550, 20));
        txtRazonSocial.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRazonSocial_keyPressed(e);
                }
            });
        jLabel19.setText("Direcci\u00f3n:");
        jLabel19.setBounds(new Rectangle(15, 65, 70, 15));
        jLabel19.setFont(new Font("SansSerif", 1, 11));
        txtDireccion.setBounds(new Rectangle(90, 60, 550, 20));
        txtDireccion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDireccion_keyPressed(e);
                }
            });
        jLabel20.setText("Email:");
        jLabel20.setBounds(new Rectangle(35, 95, 50, 15));
        jLabel20.setFont(new Font("SansSerif", 1, 11));
        txtEmail.setBounds(new Rectangle(90, 90, 195, 20));
        txtEmail.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEmail_keyPressed(e);
                }
            });
        rbTicket.setText("Ticket");
        rbTicket.setBounds(new Rectangle(35, 25, 86, 18));
        rbTicket.setFont(new Font("SansSerif", 1, 13));
        rbTicket.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    rbTicket_actionPerformed(e);
                }
            });
        rbTicket.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    rbTicket_keyPressed(e);
                }
            });
        rbBoleta.setText("Boleta");
        rbBoleta.setBounds(new Rectangle(35, 55, 86, 18));
        rbBoleta.setFont(new Font("SansSerif", 1, 13));
        rbBoleta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    rbBoleta_actionPerformed(e);
                }
            });
        rbBoleta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    rbBoleta_keyPressed(e);
                }
            });
        rbFactura.setText("Factura");
        rbFactura.setBounds(new Rectangle(35, 85, 86, 18));
        rbFactura.setFont(new Font("SansSerif", 1, 13));
        rbFactura.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    rbFactura_actionPerformed(e);
                }
            });
        rbFactura.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    rbFactura_keyPressed(e);
                }
            });
        btnSelectCliente.setText("Seleccionar Cliente");
        btnSelectCliente.setBounds(new Rectangle(275, 10, 175, 20));
        btnSelectCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnSelectCliente_actionPerformed(e);
                }
            });
        btnSelectCliente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnSelectCliente_keyPressed(e);
                }
            });
        jPanel1.add(txtApellidoPacienteMaterno, null);
        jPanel1.add(txtSexoPaciente, null);
        jPanel1.add(txtNacimientoPaciente, null);
        jPanel1.add(jLabel8, null);
        jPanel1.add(txtApellidoPacientePaterno, null);
        jPanel1.add(txtNombrePaciente, null);
        jPanel1.add(jLabel5, null);
        jPanel1.add(jLabel4, null);
        jPanel1.add(txtDniPaciente, null);
        jPanel1.add(jLabel3, null);
        jPanel2.add(txtCmp, null);
        jPanel2.add(jLabel1, null);
        jPanel2.add(txtNombreCompleto, null);
        jPanel2.add(jLabel2, null);
        jPanel3.add(lblNombre_MedicoAsociado, null);
        jPanel3.add(jLabel14, null);
        jPanel3.add(lblCMP_MedicoAsociado, null);
        jPanel3.add(jLabel12, null);
        pnlClienteRUC.add(btnSelectCliente, null);
        pnlClienteRUC.add(txtEmail, null);
        pnlClienteRUC.add(jLabel20, null);
        pnlClienteRUC.add(txtDireccion, null);
        pnlClienteRUC.add(jLabel19, null);
        pnlClienteRUC.add(txtRazonSocial, null);


        pnlClienteRUC.add(txtRuc, null);
        pnlClienteRUC.add(lblDocumento, null);
        pnlClienteRUC.add(lblNombres, null);
        buttonGroup = new ButtonGroup();
            buttonGroup.add(rbTicket);
            buttonGroup.add(rbBoleta);
            buttonGroup.add(rbFactura);


        pnlComprobante.add(rbFactura, null);
        pnlComprobante.add(rbBoleta, null);
        pnlComprobante.add(rbTicket, null);
        jContentPane.add(pnlClienteRUC, null);
        jContentPane.add(pnlComprobante, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(btnMedicoAsociado, null);
        jContentPane.add(jLabel10, null);
        jContentPane.add(jLabel9, null);
        jContentPane.add(jLabel7, null);
        jContentPane.add(jLabel6, null);
        jContentPane.add(jLabel11, null);
        jContentPane.add(jButton2, null);
        jContentPane.add(btnMedico, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, null);
        }

  /* ************************************************************************ */
  /*                                 METODO initialize                        */
  /* ************************************************************************ */
        
  private void initialize()
  {    
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
        
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
         
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    //muestraLista();
    //FarmaUtility.moveFocus(txtCmp);
    limpiaBloqueo();
    
    setDatosPanelCliente();
    
    seleccionarTipComp();
  }

   
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */
        
   
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
    private void txtCmp_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtNombreCompleto);
        }
        chkKeyPressed(e);
    }

    private void txtNombreCompleto_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCmp);
        }
        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_F11){
            eventoF11();
        }
        else if(e.getKeyCode()==KeyEvent.VK_F1){
            ingresoMedico();
        }
        else if(e.getKeyCode()==KeyEvent.VK_F2){
            ingresoPaciente();
        }        
        else if(e.getKeyCode()==KeyEvent.VK_F5){
            limpiar();
        }        
        else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
    }

    private void muestraListaMedico() {
        DlgListaMedicos dlgIngMedico = new DlgListaMedicos(myParentFrame, "", true,false);
        dlgIngMedico.setVisible(true);
        if(FarmaVariables.vAceptar){
            txtCmp.setText(VariablesModuloVentas.VNUM_CMP_IN);
            txtNombreCompleto.setText(VariablesModuloVentas.VDATOS_MEDICO_IN);
            
        }
        else{
            VariablesModuloVentas.VNUM_CMP_IN = "";
            VariablesModuloVentas.VDATOS_MEDICO_IN = "";
            txtCmp.setText("");
            txtNombreCompleto.setText("");
        }
        
        /*
        // VALIDACIONES
        if(VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("2")){
            jLabel11.setText("El DNI del paciente es OPCIONAL");
        }
        else
        if(VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("0") || VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("1")
        ){//SIN RECETA MEDICA
            jLabel11.setText("El DNI del paciente NO ES OBLIGATORIO");
        }
        else{
            jLabel11.setText("El DNI del paciente ES OBLIGATORIO");
        } */
        
        jLabel11.setText("El DNI del paciente ES OBLIGATORIO");
        
        //cerrarVentana(true);
    }

    private void btnMedico_actionPerformed(ActionEvent e) {
        ingresoMedico();
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        ingresoPaciente();
    }

    private void limpiaBloqueo() {
        txtCmp.setText("");
        txtNombreCompleto.setText("");
        txtDniPaciente.setText("");
        txtNacimientoPaciente.setText("");
        txtSexoPaciente.setText("");
        txtNombrePaciente.setText("");
        txtApellidoPacientePaterno.setText("");
        txtApellidoPacienteMaterno.setText("");
        txtCmp.setEditable(false);
        txtNombreCompleto.setEditable(false);
        txtDniPaciente.setEditable(false);
        FarmaUtility.moveFocus(txtCmp);
    }
    
    public void muestraListaPaciente(){
    
        boolean vValor = UtilityHHVenta.seleccionPaciente(myParentFrame);
        
        if(!vValor){
            txtDniPaciente.setText("");
            txtNacimientoPaciente.setText("");
            txtSexoPaciente.setText("");
            txtNombrePaciente.setText("");
            txtApellidoPacientePaterno.setText("");
            txtApellidoPacienteMaterno.setText("");
            FarmaUtility.showMessage(this, "Debe ingresar el paciente que se atenderá", btnSelectCliente);
        }
        else{
            txtDniPaciente.setText(VariablesModuloVentas.VPacienteDni);
            txtNombrePaciente.setText(VariablesModuloVentas.VPacienteNombre);
            txtApellidoPacientePaterno.setText(VariablesModuloVentas.VPacienteAPellidoPat);
            txtApellidoPacienteMaterno.setText(VariablesModuloVentas.VPacienteAPellidoMat);
            txtNacimientoPaciente.setText(VariablesModuloVentas.VPacienteNacimiento);
            txtSexoPaciente.setText(VariablesModuloVentas.VPacienteSexo);
            if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase("")){
                FarmaUtility.moveFocus(rbBoleta);
            }else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET)){
                FarmaUtility.moveFocus(rbTicket);
            } else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA)){
                FarmaUtility.moveFocus(rbBoleta);
            } else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
                FarmaUtility.moveFocus(rbFactura);
            }
        }
        /*
        DlgAdmPaciente dlgIngPaciente = new DlgAdmPaciente(myParentFrame, "", true);
        dlgIngPaciente.setVisible(true);
        if(FarmaVariables.vAceptar){
            txtDniPaciente.setText(VariablesModuloVentas.VPacienteDni);
            txtNombrePaciente.setText(VariablesModuloVentas.VPacienteNombre);
            txtApellidoPacientePaterno.setText(VariablesModuloVentas.VPacienteAPellidoPat);
            txtApellidoPacienteMaterno.setText(VariablesModuloVentas.VPacienteAPellidoMat);
            txtNacimientoPaciente.setText(VariablesModuloVentas.VPacienteNacimiento);
            txtSexoPaciente.setText(VariablesModuloVentas.VPacienteSexo);
        }
        else{
            VariablesModuloVentas.VPacienteDni = "";
            VariablesModuloVentas.VPacienteNombre = "";
            VariablesModuloVentas.VPacienteAPellidoPat = "";
            VariablesModuloVentas.VPacienteAPellidoMat = "";
            VariablesModuloVentas.VPacienteNacimiento = "";
            VariablesModuloVentas.VPacienteSexo = "";
            VariablesModuloVentas.VPacienteSexoID="";
            txtDniPaciente.setText("");
            txtNacimientoPaciente.setText("");
            txtSexoPaciente.setText("");
            txtNombrePaciente.setText("");
            txtApellidoPacientePaterno.setText("");
            txtApellidoPacienteMaterno.setText("");
        }
        FarmaUtility.moveFocus(txtCmp);*/
        //cerrarVentana(true);
      }
    
    public boolean aceptaDatos(){
        
        if(rbTicket.isSelected()||rbBoleta.isSelected()||rbFactura.isSelected()){
              cargaTipoComprobante_ClienteComp();
            VariablesModuloVentas.imprimeDatosMedicoPaciente();

              // VALIDACIONES
              if(VariablesModuloVentas.VNUM_CMP_IN.trim().length()>0 && VariablesModuloVentas.VDATOS_MEDICO_IN.trim().length()>0 ) {
              ///////// valida tratamiento
                  if(VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("2")){
                      jLabel11.setText("El DNI del paciente es OPCIONAL");
                      return true;
                  }
                  else
                  if(VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("0") || VariablesModuloVentas.VNUM_CMP_IN.trim().equalsIgnoreCase("1")
                  ){//SIN RECETA MEDICA
                      jLabel11.setText("El DNI del paciente NO ES OBLIGATORIO");
                      return true;
                  }
                  else{
                      if(VariablesModuloVentas.VPacienteDni.trim().length()>0 && VariablesModuloVentas.VPacienteNombre.trim().length()>0 && VariablesModuloVentas.VPacienteAPellidoPat.trim().length()>0 && VariablesModuloVentas.VPacienteAPellidoMat.trim().length()>0 && VariablesModuloVentas.VPacienteNacimiento.trim().length()>0 && VariablesModuloVentas.VPacienteSexo.trim().length()>0 )
                      return true;
                      else{
                          FarmaUtility.showMessage(this,"Por favo de ingresar los datos del paciente que son obligatorios", txtCmp);
                          return false;                    
                      }
                  } 
              }
              else{
              FarmaUtility.showMessage(this,"Por favo de ingresar Médico y Paciente", txtCmp);
              return false;
              }
            
            //return true;
            
          }
            else{
                FarmaUtility.showMessage(this,"Por favo de seleccionar el tipo de comprobante.", txtCmp);
                return false;
                }
        
        }

    private void ingresoMedico() {
        FarmaUtility.moveFocus(txtCmp);
        muestraListaMedico();
    }

    private void ingresoPaciente() {
        FarmaUtility.moveFocus(txtDniPaciente);
        muestraListaPaciente();
    }

    private void limpiar() {
        limpiaBloqueo();
        VariablesModuloVentas.limpiarDatosMedicoPaciente();
    }

    private void jLabel9_mouseClicked(MouseEvent e) {
        limpiar();
    }

    private void btnMedicoAsociado_actionPerformed(ActionEvent e) {
        DlgListaMedicos dlgIngMedico = new DlgListaMedicos(myParentFrame, "", true,true);
        dlgIngMedico.setVisible(true);
        if(FarmaVariables.vAceptar){
            lblCMP_MedicoAsociado.setText(VariablesModuloVentas.VNUM_CMP_ASOCIADO_IN);
            lblNombre_MedicoAsociado.setText(VariablesModuloVentas.VDATOS_MEDICO_ASOCIADO_IN);
            
        }
        else{
           VariablesModuloVentas.VNUM_CMP_ASOCIADO_IN = "";
            VariablesModuloVentas.VDATOS_MEDICO_ASOCIADO_IN = "";
            txtCmp.setText("");
            txtNombreCompleto.setText("");
        }
        FarmaUtility.moveFocus(txtCmp);
    }

    private void btnMedicoAsociado_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void setDatosPanelCliente() {
        txtRuc.setEnabled(false);
        txtRazonSocial.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtEmail.setEnabled(false);
        btnSelectCliente.setEnabled(false);
        
        if(UtilityFactElectronica.isActivoFactElectronica())
            rbTicket.setEnabled(false);
        
        txtRuc.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        
        
            
        // set medico    
        txtCmp.setText(VariablesModuloVentas.VNUM_CMP_IN);
        txtNombreCompleto.setText(VariablesModuloVentas.VDATOS_MEDICO_IN);
        
        // set paciente
        txtDniPaciente.setText(VariablesModuloVentas.VPacienteDni);
        txtNombrePaciente.setText(VariablesModuloVentas.VPacienteNombre);
        txtApellidoPacientePaterno.setText(VariablesModuloVentas.VPacienteAPellidoPat);
        txtApellidoPacienteMaterno.setText(VariablesModuloVentas.VPacienteAPellidoMat);
        txtNacimientoPaciente.setText(VariablesModuloVentas.VPacienteNacimiento);
        txtSexoPaciente.setText(VariablesModuloVentas.VPacienteSexo);
        
        // set tip comp
        if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET)){
            rbTicket.setSelected(true);
        }
        else
            if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA)){
                rbBoleta.setSelected(true);
            }
            else
                if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
                   rbBoleta.setSelected(true);
                }
        
        
        if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
            VariablesModuloVentas.vRuc_Cli_Ped = VariablesHH.vRuc;
            VariablesModuloVentas.vNom_Cli_Ped = VariablesHH.vRazonSocial;
            VariablesModuloVentas.vDir_Cli_Ped = VariablesHH.vDireccion;
        }
        else{
            if(VariablesHH.vCodCliLocal.trim().length()==0){
                VariablesModuloVentas.vRuc_Cli_Ped =  VariablesModuloVentas.VPacienteDni;
                VariablesModuloVentas.vNom_Cli_Ped = VariablesModuloVentas.VPacienteNombre+" "+
                                                     VariablesModuloVentas.VPacienteAPellidoPat+ " "+
                                                     VariablesModuloVentas.VPacienteAPellidoMat;
                VariablesModuloVentas.vDir_Cli_Ped = ".";        
            }
        }
        
        
        txtRuc.setText(VariablesHH.vRuc);    
        txtRazonSocial.setText(VariablesHH.vRazonSocial);    
        txtDireccion.setText(VariablesHH.vDireccion);    
        txtEmail.setText(VariablesHH.vCorreo); 
        
        
    }

    private void rbFactura_actionPerformed(ActionEvent e) {
        pnlClienteRUC.setEnabled(true);
        txtRuc.setEnabled(true);
        txtRazonSocial.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtEmail.setEnabled(true);
        btnSelectCliente.setEnabled(true);
        
        lblDocumento.setText("RUC:");
        lblNombres.setText("Razon Social:");
        
        VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_FACTURA; // JHAMRC
        
    }

    private void rbBoleta_actionPerformed(ActionEvent e) {
        
        pnlClienteRUC.setEnabled(true);
        txtRuc.setEnabled(true);
        txtRazonSocial.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtEmail.setEnabled(true);
        btnSelectCliente.setEnabled(true);
        
        lblDocumento.setText("Documento:");
        lblNombres.setText("Nombres:");
        
        VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_BOLETA; // JHAMRC
        /*
        pnlClienteRUC.setEnabled(false);
        txtRuc.setEnabled(false);
        txtRazonSocial.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtEmail.setEnabled(false);
        btnSelectCliente.setEnabled(false);
        VariablesModuloVentas.vRuc_Cli_Ped = "";
        VariablesModuloVentas.vNom_Cli_Ped = "";
        VariablesModuloVentas.vDir_Cli_Ped = "";

        txtRuc.setText("");
        txtRazonSocial.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");*/
    }

    private void rbTicket_actionPerformed(ActionEvent e) {
        pnlClienteRUC.setEnabled(false);
        txtRuc.setEnabled(false);
        txtRazonSocial.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtEmail.setEnabled(false);
        btnSelectCliente.setEnabled(false);
        

        VariablesHH.vCodigo = "";
        VariablesHH.vRuc = "";
        VariablesHH.vRazonSocial = "";
        VariablesHH.vTelefono="";
        VariablesHH.vCorreo="";
        VariablesHH.vDni="";
        VariablesHH.vDireccion="";
        VariablesHH.vNombre="";
        VariablesHH.vApellidoPat="";
        VariablesHH.vApellidoMat="";
        
        txtRuc.setText("");
        txtRazonSocial.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        
        VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_TICKET; // JHAMRC
    }
    
    private void seleccionarTipComp(){ //JHAMRC
        // set tip comp
        if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase("")){
            rbBoleta.setSelected(true);
            rbBoleta.doClick();
        }else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET)){
            rbTicket.setSelected(true);
            rbTicket.doClick();
        } else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_BOLETA)){
            rbBoleta.setSelected(true);
            rbBoleta.doClick();
        } else if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
            rbFactura.setSelected(true);
            rbFactura.doClick();
        }
    }

    private void btnSelectCliente_actionPerformed(ActionEvent e) {
        
        boolean vValor = UtilityHHVenta.seleccionClienteComprobante(myParentFrame);
        if(vValor){
            txtRuc.setText(VariablesHH.vRuc);    
            txtRazonSocial.setText(VariablesHH.vRazonSocial);    
            txtDireccion.setText(VariablesHH.vDireccion);    
            txtEmail.setText(VariablesHH.vCorreo);     
        }
        else{
            VariablesHH.vTipoBusqueda = "";
            VariablesHH.vRuc_RazSoc_Busqueda = "";
            VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;

            VariablesHH.vCodigo = "";
            VariablesHH.vRuc = "";
            VariablesHH.vRazonSocial = "";
            VariablesHH.vTelefono="";
            VariablesHH.vCorreo="";
            VariablesHH.vDni="";
            VariablesHH.vDireccion="";
            VariablesHH.vNombre="";
            VariablesHH.vApellidoPat="";
            VariablesHH.vApellidoMat="";
    
        }
            
        // solo pide estos datos si es FACTURA
        /*DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico(myParentFrame, "", true);
        dlgBuscaClienteJuridico.setVisible(true);    
        if(FarmaVariables.vAceptar){
            txtRuc.setText(VariablesHH.vRuc);    
            txtRazonSocial.setText(VariablesHH.vRazonSocial);    
            txtDireccion.setText(VariablesHH.vDireccion);    
            txtEmail.setText(VariablesHH.vCorreo);        
        }*/
        
            
        /*
        if(VariablesCliente.vDni.trim().length()==8){
        VariablesModuloVentas.vRuc_Cli_Ped = VariablesCliente.vDni;
        VariablesModuloVentas.vNom_Cli_Ped = VariablesCliente.vNombre+" "+VariablesCliente.vApellidoPat+ " "+VariablesCliente.vApellidoMat;
        VariablesModuloVentas.vDir_Cli_Ped = VariablesCliente.vDireccion;
        }
        else{
            VariablesModuloVentas.vRuc_Cli_Ped = VariablesCliente.vRuc;
            VariablesModuloVentas.vNom_Cli_Ped = VariablesCliente.vRazonSocial;
            VariablesModuloVentas.vDir_Cli_Ped = VariablesCliente.vDireccion;    
        }
        */
        
    }

    public void cargaTipoComprobante_ClienteComp() {
        
        if(rbTicket.isSelected())
         VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_TICKET;    
        else
            if(rbBoleta.isSelected())
             VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_BOLETA;    
            else
                if(rbFactura.isSelected())
                 VariablesModuloVentas.vTip_Comp_Ped = ConstantsModuloVenta.TIPO_COMP_FACTURA;    
        
        if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA)){
            VariablesModuloVentas.vRuc_Cli_Ped = VariablesHH.vRuc;
            VariablesModuloVentas.vNom_Cli_Ped = VariablesHH.vRazonSocial;
            VariablesModuloVentas.vDir_Cli_Ped = VariablesHH.vDireccion;
        }
        else{
            if(VariablesHH.vCodCliLocal.trim().length()==0){
                VariablesModuloVentas.vRuc_Cli_Ped =  VariablesModuloVentas.VPacienteDni;
                VariablesModuloVentas.vNom_Cli_Ped = VariablesModuloVentas.VPacienteNombre+" "+
                                                     VariablesModuloVentas.VPacienteAPellidoPat+ " "+
                                                     VariablesModuloVentas.VPacienteAPellidoMat;
                VariablesModuloVentas.vDir_Cli_Ped = ".";        
            }
        }
        
        
       
        
    }

    private void eventoF11() {

        if(aceptaDatos())
            cerrarVentana(true);
        //VariablesVentas.VNUM_CMP_IN = txtCmp.getText();
        //VariablesVentas.VDATOS_MEDICO_IN = txtNombreCompleto.getText();
    }

    private void lblF11_mouseClicked(MouseEvent e) {

        if(aceptaDatos())
            cerrarVentana(true);
        //VariablesVentas.VNUM_CMP_IN = txtCmp.getText();
        //VariablesVentas.VDATOS_MEDICO_IN = txtNombreCompleto.getText();
    }

    private void txtRuc_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtRazonSocial_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtEmail_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void rbFactura_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void rbBoleta_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void rbTicket_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnSelectCliente_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
