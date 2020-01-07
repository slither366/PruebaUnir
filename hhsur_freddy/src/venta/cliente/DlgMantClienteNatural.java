package venta.cliente;


import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import java.sql.*;
import java.util.*;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import componentes.gs.componentes.JPanelWhite;
import java.awt.GridLayout;
import componentes.gs.componentes.JTextFieldSanSerif;
import componentes.gs.componentes.JPanelHeader;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import javax.swing.BorderFactory;
import componentes.gs.componentes.JButtonLabel;
import java.awt.event.ActionEvent;
import common.FarmaLengthText;
import java.awt.event.KeyEvent;
import componentes.gs.componentes.JLabelFunction;
import java.awt.event.ActionListener;
import common.*;

import componentes.gs.componentes.JConfirmDialog;

import consorcio.VariablesHH;

import dental.DlgProcesarConsultaRUC;

import javax.swing.JLabel;

import javax.swing.JTextField;

import venta.cliente.reference.*;
import java.awt.event.WindowEvent;

import venta.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.medico.reference.DBMedico;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantClienteNatural.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      03.03.2006   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */


public class DlgMantClienteNatural extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgMantClienteNatural.class);

  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JPanelHeader pnlCodigoLaboratorio = new JPanelHeader();
  private JTextFieldSanSerif txtCodigo = new JTextFieldSanSerif();
  private JLabelWhite lblCodigoLaboratorio_T = new JLabelWhite();
  private JPanelTitle pnlDatosLaboratorio = new JPanelTitle();
  private JButtonLabel btnDireccion = new JButtonLabel();
  public JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
  public JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
  private JButtonLabel btnDNI = new JButtonLabel();
  private JButtonLabel btnNombre = new JButtonLabel();
  private JButtonLabel btnApePat = new JButtonLabel();
  private JButtonLabel btnApeMat = new JButtonLabel();
  private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtApePat = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtApeMat = new JTextFieldSanSerif();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtTelefono = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtCorreo = new JTextField();
    private JLabel jLabel3 = new JLabel();
    public JTextField txtRazonSocial = new JTextField();

    public DlgMantClienteNatural()
  {
    this(null, "", false);
  }

  public DlgMantClienteNatural(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
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
  private void initialize() {
    FarmaVariables.vAceptar = false;
  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(692, 307));
    this.getContentPane().setLayout(null);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Creaci\u00f3n de Cliente");
    this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
		});
    jPanelWhite1.setLayout(null);
    jPanelWhite1.setBounds(new Rectangle(0, 0, 695, 300));
    pnlCodigoLaboratorio.setBounds(new Rectangle(5, 5, 680, 30));
   // pnlCodigoLaboratorio.setSize(new Dimension(435, 30));
        pnlCodigoLaboratorio.setLayout(null);
    pnlCodigoLaboratorio.setFont(new Font("SansSerif", 0, 11));
    txtCodigo.setBounds(new Rectangle(115, 5, 55, 20));
    txtCodigo.setFont(new Font("SansSerif", 0, 11));
    txtCodigo.setEnabled(false);
    lblCodigoLaboratorio_T.setText("Código : ");
    lblCodigoLaboratorio_T.setBounds(new Rectangle(5, 5, 55, 20));
    lblCodigoLaboratorio_T.setForeground(Color.white);
    pnlDatosLaboratorio.setBounds(new Rectangle(5, 45, 675, 180));
    pnlDatosLaboratorio.setBackground(Color.white);
    pnlDatosLaboratorio.setFont(new Font("SansSerif", 0, 11));
    pnlDatosLaboratorio.setLayout(null);
    pnlDatosLaboratorio.setBorder(BorderFactory.createLineBorder(new Color(0, 114, 169), 1));
    btnDireccion.setText("Dirección : ");
    btnDireccion.setBounds(new Rectangle(25, 100, 65, 25));
    btnDireccion.setForeground(new Color(0, 114, 169));
    btnDireccion.setMnemonic('d');
    btnDireccion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDireccion_actionPerformed(e);
        }
      });
    txtDni.setBounds(new Rectangle(80, 10, 180, 20));
    txtDni.setFont(new Font("SansSerif", 0, 11));
    txtDni.setDocument(new FarmaLengthText(12));
    txtDni.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtDni_keyPressed(e);
                }
      });
    txtDireccion.setBounds(new Rectangle(90, 105, 555, 20));
    txtDireccion.setFont(new Font("SansSerif", 0, 11));
    txtDireccion.setDocument(new FarmaLengthText(120));
    txtDireccion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtDireccion_keyPressed(e);
                }
      });
    txtDireccion.setDocument(new FarmaLengthText(250));
    btnDNI.setText("DNI o RUC: ");
    btnDNI.setBounds(new Rectangle(15, 5, 75, 25));
    btnDNI.setForeground(new Color(0, 114, 169));
    btnDNI.setMnemonic('n');
    btnDNI.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDNI_actionPerformed(e);
        }
      });
    btnNombre.setText("Nombre : ");
    btnNombre.setBounds(new Rectangle(350, 20, 55, 15));
    btnNombre.setForeground(new Color(0, 114, 169));
    btnNombre.setMnemonic('n');
    btnNombre.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNombre_actionPerformed(e);
        }
      });
    btnApePat.setText("Ape. Pat");
    btnApePat.setBounds(new Rectangle(350, 45, 55, 15));
    btnApePat.setForeground(new Color(0, 114, 169));
    btnApePat.setMnemonic('p');
    btnApePat.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnApellidoPaterno_actionPerformed(e);
        }
      });
    btnApeMat.setText("Ape. Mat");
    btnApeMat.setBounds(new Rectangle(350, 70, 60, 15));
    btnApeMat.setForeground(new Color(0, 114, 169));
    btnApeMat.setMnemonic('m');
    btnApeMat.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnApellidoMaterno_actionPerformed(e);
        }
      });
    txtNombre.setBounds(new Rectangle(410, 15, 230, 20));
    txtNombre.setFont(new Font("SansSerif", 0, 11));
    txtNombre.setDocument(new FarmaLengthText(30));
    txtNombre.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtNombre_keyPressed(e);
                }
      });
    txtNombre.setDocument(new FarmaLengthText(50));
    txtApePat.setBounds(new Rectangle(410, 40, 230, 20));
    txtApePat.setFont(new Font("SansSerif", 0, 11));
    txtApePat.setDocument(new FarmaLengthText(30));
    txtApePat.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtApePat_keyPressed(e);
                }
      });
    txtApePat.setDocument(new FarmaLengthText(50));
    txtApeMat.setBounds(new Rectangle(410, 65, 230, 20));
    txtApeMat.setFont(new Font("SansSerif", 0, 11));
    txtApeMat.setDocument(new FarmaLengthText(30));
    txtApeMat.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtApeMat_keyPressed(e);
                }
      });
    txtApeMat.setDocument(new FarmaLengthText(50));
    lblEsc.setText("[ESC] Cerrar");
    lblEsc.setBounds(new Rectangle(5, 235, 95, 30));
    lblEsc.setFont(new Font("SansSerif", 1, 12));
    lblF1.setText("[F11] Aceptar");
    lblF1.setBounds(new Rectangle(585, 235, 85, 30));
    lblF1.setFont(new Font("SansSerif", 1, 12));
        jLabel1.setText("T\u00e9lefono : ");
        jLabel1.setBounds(new Rectangle(15, 40, 60, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 99, 148));
        txtTelefono.setBounds(new Rectangle(80, 40, 180, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }
            });
        jLabel2.setText("Correo:");
        jLabel2.setBounds(new Rectangle(20, 65, 50, 25));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(new Color(0, 99, 148));
        txtCorreo.setBounds(new Rectangle(80, 70, 245, 20));
        txtCorreo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCorreo_keyPressed(e);
                }
            });
        jLabel3.setText("Raz\u00f3n Social");
        jLabel3.setBounds(new Rectangle(15, 135, 70, 20));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setForeground(new Color(0, 99, 148));
        txtRazonSocial.setBounds(new Rectangle(90, 135, 555, 20));
        txtRazonSocial.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRazonSocial_keyPressed(e);
                }
            });
        pnlDatosLaboratorio.add(txtRazonSocial, null);
        pnlDatosLaboratorio.add(jLabel3, null);
        pnlDatosLaboratorio.add(txtCorreo, null);
        pnlDatosLaboratorio.add(jLabel2, null);
        pnlDatosLaboratorio.add(txtTelefono, null);
        pnlDatosLaboratorio.add(jLabel1, null);
        pnlDatosLaboratorio.add(txtApeMat, null);
        pnlDatosLaboratorio.add(txtApePat, null);
        pnlDatosLaboratorio.add(txtNombre, null);
        pnlDatosLaboratorio.add(btnApeMat, null);
        pnlDatosLaboratorio.add(btnApePat, null);
        pnlDatosLaboratorio.add(btnNombre, null);
        pnlDatosLaboratorio.add(btnDNI, null);
        pnlDatosLaboratorio.add(btnDireccion, null);
        pnlDatosLaboratorio.add(txtDni, null);
        pnlDatosLaboratorio.add(txtDireccion, null);
        pnlCodigoLaboratorio.add(txtCodigo, null);
        pnlCodigoLaboratorio.add(lblCodigoLaboratorio_T, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblEsc, null);
        jPanelWhite1.add(pnlDatosLaboratorio, null);
        jPanelWhite1.add(pnlCodigoLaboratorio, null);
        this.getContentPane().add(jPanelWhite1, null);
  }
  private void this_windowOpened(WindowEvent e) {
  	FarmaUtility.centrarVentana(this);
    
    if(VariablesCliente.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR))
    {
      try
      {
        cargaDatosCliente_Variables();  
      }
      catch (SQLException sql) 
      {
        log.error("",sql);
      }
      
      txtDni.setEditable(false);
      FarmaUtility.moveFocus(txtTelefono);
    }
    else
        FarmaUtility.moveFocus(txtDni);
	}
  private String agregaClienteNatural()
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.agragaClienteNatural (VariablesCliente.vNombre,
                                                  VariablesCliente.vApellidoPat,
                                                  VariablesCliente.vApellidoMat,
                                                  VariablesCliente.vTipoDocumento,
                                                  VariablesCliente.vDni,
                                                  VariablesCliente.vDireccion,
                                                  VariablesCliente.vRazonSocial,
                                                  VariablesCliente.vTelefono,
                                                  VariablesCliente.vCorreo);
                                                  
    log.debug(resultado); 
        FarmaUtility.aceptarTransaccion();
    return resultado;
    } 
    catch(Exception sql)
    {
        FarmaUtility.liberarTransaccion();
      log.error("",sql);
      
      return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }
  private String actualizaClienteNatural()
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.actualizaClienteNatural(VariablesCliente.vCodigo,
                                                    VariablesCliente.vNombre,
                                                    VariablesCliente.vApellidoPat,
                                                    VariablesCliente.vApellidoMat,
                                                    VariablesCliente.vDni,
                                                    VariablesCliente.vDireccion,
                                                    VariablesCliente.vTipoDocumento,
                                                  VariablesCliente.vRazonSocial,
                                                  VariablesCliente.vTelefono,
                                                  VariablesCliente.vCorreo);
        FarmaUtility.aceptarTransaccion();
      return resultado;
        
    } 
    catch(SQLException sql)
    {
      log.error("",sql);
        FarmaUtility.liberarTransaccion();
      return ConstantsCliente.RESULTADO_GRABAR_CLIENTE_ERROR;
    }
  }
  
  private void guardaValoresCliente()
  {
    VariablesCliente.vCodigo = txtCodigo.getText().trim();
    VariablesCliente.vNombre= txtNombre.getText().trim().toUpperCase();
    VariablesCliente.vApellidoPat= txtApePat.getText().trim().toUpperCase();
    VariablesCliente.vApellidoMat= txtApeMat.getText().trim().toUpperCase();
    VariablesCliente.vDni = txtDni.getText().trim();
    
    if(txtDni.isEditable())
    VariablesCliente.vTipoDocumento = ConstantsCliente.TIPO_NATURAL;
    
    VariablesCliente.vDireccion = txtDireccion.getText().trim().toUpperCase();
    VariablesCliente.vTipoBusqueda = ConstantsCliente.TIPO_BUSQUEDA_DNI;
    VariablesCliente.vRuc_RazSoc_Busqueda = txtDni.getText().trim().toUpperCase();
    
      VariablesCliente.vTelefono = txtTelefono.getText().trim().toUpperCase();
      VariablesCliente.vCorreo = txtCorreo.getText().trim().toUpperCase();
    
    if(VariablesCliente.vDni.trim().length()>8) {
        VariablesCliente.vRazonSocial = txtRazonSocial.getText().trim();
    }
    
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCliente.vCodigo);
    myArray.add(VariablesCliente.vNombre);
    myArray.add(VariablesCliente.vApellidoPat);
    myArray.add(VariablesCliente.vApellidoMat);
    myArray.add(VariablesCliente.vDni);
    myArray.add(VariablesCliente.vDireccion);
    VariablesCliente.vArrayList_Cliente_Juridico.clear();
    VariablesCliente.vArrayList_Cliente_Juridico.add(myArray);
    log.debug("",myArray);
  }
  
  private boolean validaDatosClientes()
  {
    if(VariablesCliente.vDni.equalsIgnoreCase("") || VariablesCliente.vDni.length() < 8)
    {
      FarmaUtility.showMessage(this, "Ingrese un DNI o RUC Válido", txtDni);
      return false;
    }
    
    if(txtDni.getText().length()>8){
        if(VariablesCliente.vRazonSocial.equalsIgnoreCase("")){
            FarmaUtility.showMessage(this, "Debe ingresar la razon social correcta", txtNombre);
            return false;
        }
        
        if(VariablesCliente.vDireccion.equalsIgnoreCase(""))
        {
          FarmaUtility.showMessage(this, "Ingrese una Direccion correcta", txtDireccion);
          return false;
        }
        
        
    }
    else{
        if(VariablesCliente.vNombre.equalsIgnoreCase(""))
        {
          FarmaUtility.showMessage(this, "Ingrese Nombre correcto", txtNombre);
          return false;
        }
        if(VariablesCliente.vApellidoPat.equalsIgnoreCase(""))
        {
          FarmaUtility.showMessage(this, "Ingrese Apellido Paterno correcto", txtApePat);
          return false;
        }
        if(VariablesCliente.vApellidoMat.equalsIgnoreCase(""))
        {
          FarmaUtility.showMessage(this, "Ingrese Apellido Materno correcto", txtApeMat);
          return false;
        }    
    }
    
    
    return true;
  }
  
  
  private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
		} 
    else if (UtilityPtoVenta.verificaVK_F11(e)) 
    {
        guardaValoresCliente();
          if(!validaDatosClientes()) return;
            String resultado = "";
            log.debug(VariablesHH.vTipo_Accion);
                if(VariablesHH.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_INSERTAR)){
                    resultado = agregaClienteNatural();
                    log.debug("resultado = " + resultado);
                }
                else if(VariablesHH.vTipo_Accion.equalsIgnoreCase(ConstantsCliente.ACCION_MODIFICAR)){
        
                    resultado = actualizaClienteNatural();
                    log.debug("resultado = " + resultado);
                }
                if(resultado.equalsIgnoreCase(ConstantsCliente.RESULTADO_GRABAR_CLIENTE_EXITO)){
                    FarmaUtility.showMessage(this, "Se grabo el Cliente con Exito", null);
                    cerrarVentana(true);
                } 
                else FarmaUtility.showMessage(this, "Error al grabar el Cliente", txtNombre);
    }
	}
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  private void btnRazonSocial_actionPerformed(ActionEvent e)
  {
  }


  private void txtRazonSocial_keyPressed(KeyEvent e)
  {
      chkKeyPressed(e);
      
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          if(txtDireccion.isEditable()){
              FarmaUtility.moveFocus(txtDireccion);
          }
          else
              if(txtDni.isEditable()){
                  FarmaUtility.moveFocus(txtDni);
              }
          else
              FarmaUtility.moveFocus(txtTelefono);
      }
  }

  private void txtNombre_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        if(txtApePat.isEditable()){
            FarmaUtility.moveFocus(txtApePat);
        }
        else{
            if(txtApeMat.isEditable()){
                FarmaUtility.moveFocus(txtApeMat);
            }   
            else{
                if(txtRazonSocial.isEditable()){
                    FarmaUtility.moveFocus(txtRazonSocial);
                }
                else{
                    if(txtDireccion.isEditable()){
                        FarmaUtility.moveFocus(txtDireccion);
                    }
                    else
                        FarmaUtility.moveFocus(txtDni);
                }
            }
        }
      
    }
    chkKeyPressed(e);
  }

  private void txtApePat_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
       
            if(txtApeMat.isEditable()){
                FarmaUtility.moveFocus(txtApeMat);
            }   
            else{
                if(txtRazonSocial.isEditable()){
                    FarmaUtility.moveFocus(txtRazonSocial);
                }
                else{
                    if(txtDireccion.isEditable()){
                        FarmaUtility.moveFocus(txtDireccion);
                    }
                    else
                        FarmaUtility.moveFocus(txtDni);
                }
            }
    }
    chkKeyPressed(e);
  }
  
  private void txtApeMat_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        
            if(txtRazonSocial.isEditable()){
                FarmaUtility.moveFocus(txtRazonSocial);
            }
            else{
                if(txtDireccion.isEditable()){
                    FarmaUtility.moveFocus(txtDireccion);
                }
                else
                    FarmaUtility.moveFocus(txtDni);
            }
        
    }
    chkKeyPressed(e);
  }

  private void txtDni_keyPressed(KeyEvent e)
   {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        String vDNI = txtDni.getText().trim();
        
        if(vDNI.length()>0){
            if(vDNI.length()==8){
                boolean vResultado = false;
                ArrayList vLista =  new ArrayList();
                try {
                    DBMedico.getPacienteDNI(vLista, vDNI);
                    if(vLista.size()>0){
                        String pDNI = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                        String pNombre = FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                        String pApePat = FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                        String pApeMat = FarmaUtility.getValueFieldArrayList(vLista, 0, 3);
                        String pFecha = FarmaUtility.getValueFieldArrayList(vLista, 0, 4);                
                        String pSexo = FarmaUtility.getValueFieldArrayList(vLista, 0, 5);
                        txtNombre.setText(pNombre);
                        txtApeMat.setText(pApeMat);
                        txtApePat.setText(pApePat);
                        vResultado = true;
                    }
                    else
                       vResultado = false;
                } catch (Exception sqle) {
                    vResultado = false;
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                
                txtRazonSocial.setText("");
                txtRazonSocial.setEditable(false);
                FarmaUtility.moveFocus(txtTelefono);
                
            }
            else{
                
                if(vDNI.length()==11){
                    
                    operacionBusquedaRUC();
                    
                    FarmaUtility.moveFocus(txtTelefono);      
                    
                }else{
                    // no es DNI es RUC
                    txtNombre.setText("");
                    txtApePat.setText("");
                    txtApeMat.setText("");
                    txtNombre.setEditable(false);
                    txtApePat.setEditable(false);
                    txtApeMat.setEditable(false);
                    FarmaUtility.moveFocus(txtTelefono);        
                }
            }
        }
        else{
            FarmaUtility.showMessage(this,"Debe ingresar el numero de documento", txtDni);
        }
        
        
    }
    chkKeyPressed(e);
  }
  
  private void txtDireccion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        if(txtRazonSocial.isEditable()){
            FarmaUtility.moveFocus(txtRazonSocial);
        }
        else{
            if(txtDni.isEditable()){
                FarmaUtility.moveFocus(txtDni);
            }    
            else
                FarmaUtility.moveFocus(txtTelefono);
        }
      
    }
    chkKeyPressed(e);
  }
  
  private void cargaDatosCliente_Variables() throws SQLException
  {
    /*ArrayList myArray = new ArrayList();
    log.debug("VariablesCliente.vCodigo : " + VariablesCliente.vCodigo);
    DBCliente.obtieneInfo_Cli_Natural(myArray);
    log.debug("myArray : " + myArray.size());
    VariablesCliente.vCodigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
    VariablesCliente.vNombre = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
    VariablesCliente.vApellidoPat = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
    VariablesCliente.vApellidoMat = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
    VariablesCliente.vDni = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
    VariablesCliente.vDireccion = ((String)((ArrayList)myArray.get(0)).get(5)).trim();*/
    cargaDatosCliente_Pantalla();
  }
  
  private void cargaDatosCliente_Pantalla()
  {
    txtCodigo.setText(VariablesCliente.vCodigo);
    if(VariablesCliente.vTipoDocumento.equalsIgnoreCase("01")){
        txtNombre.setText(VariablesCliente.vNombre);
        txtRazonSocial.setText("");
    }
    else{
        txtNombre.setText("");
        txtRazonSocial.setText(VariablesCliente.vRazonSocial);
    }
        
    txtApePat.setText(VariablesCliente.vApellidoPat);
    txtApeMat.setText(VariablesCliente.vApellidoMat);
      
      if(VariablesCliente.vTipoDocumento.equalsIgnoreCase("01"))
      txtDni.setText(VariablesCliente.vDni);
      else
      txtDni.setText(VariablesCliente.vRuc);
    
    
    txtDireccion.setText(VariablesCliente.vDireccion);
    txtTelefono.setText(VariablesCliente.vTelefono);
    txtCorreo.setText(VariablesCliente.vCorreo);
    
    if(VariablesCliente.vTipoDocumento.equalsIgnoreCase("01")){
        txtRazonSocial.setEditable(false);
        txtDni.setEditable(false);
        FarmaUtility.moveFocus(txtTelefono);
    }
    else{
        txtNombre.setEditable(false);
        txtApePat.setEditable(false);
        txtApeMat.setEditable(false);
        txtDni.setEditable(false);
        FarmaUtility.moveFocus(txtTelefono);
    }
            
    
  }

  private void btnNombre_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtNombre);
  }

  private void btnApellidoPaterno_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtApePat);
  }

  private void btnApellidoMaterno_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtApeMat);
  }

  private void btnDNI_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtDni);
  }

  private void btnDireccion_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtDireccion);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
          FarmaUtility.moveFocus(txtCorreo);
        }
        chkKeyPressed(e);
    }

    private void txtCorreo_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
          if(txtNombre.isEditable())
              FarmaUtility.moveFocus(txtNombre);
          else{
              if(txtRazonSocial.isEditable())
                  FarmaUtility.moveFocus(txtRazonSocial);
              else
                 FarmaUtility.moveFocus(txtDireccion);
          }
        }
        chkKeyPressed(e);
    }
    
    

    public void operacionBusquedaRUC() {
        if (JConfirmDialog.rptaConfirmDialog(this,"¿Desea hacer la consulta en SUNAT?\n" +
            "Este proceso puede demorar ya que depende del servicio compartido de SUNAT.")) {
            DlgProcesarConsultaRUC dlg = new DlgProcesarConsultaRUC(new JFrame(), "", true,this);
            dlg.mostrar();    
        }
    }
}
