package venta.inventario;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.DlgListaMaestros;
import venta.cliente.reference.ConstantsCliente;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgTransferenciasTransporte.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      22.03.2005   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasTransporte extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;
    private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasTransporte.class);
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlHeader1 = new JPanelTitle();
  private JButtonLabel btnSenores = new JButtonLabel();
  private JLabelWhite lblRuc = new JLabelWhite();
  private JLabelWhite lblDireccion = new JLabelWhite();
  private JTextFieldSanSerif txtSenores = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JTextFieldSanSerif txtTransportista = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtRucTransportista = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtDireccionTransportista = new JTextFieldSanSerif();
  private JLabelWhite lblDireccionTransportista_T = new JLabelWhite();
  private JLabelWhite lblRucTransportista_T = new JLabelWhite();
  private JLabelWhite lblPlaca_T = new JLabelWhite();
  private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
  private JLabelWhite lblTransportista_T = new JLabelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JPanelHeader pnllHeader1 = new JPanelHeader();
  private JLabelWhite lblFechaEmision = new JLabelWhite();
  private JTextFieldSanSerif txtDestino = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtCodigoDestino = new JTextFieldSanSerif();
  private JComboBox cmbTipo = new JComboBox();
  private JButtonLabel btnTipo_T = new JButtonLabel();
  private JLabelWhite lblFechaEmision_T = new JLabelWhite();
  private JLabelWhite lblDestino_T = new JLabelWhite();
  private JButtonLabel btnMotivo = new JButtonLabel();
  private JComboBox cmbMotivo = new JComboBox();
  private JComboBox cmbDefinicion = new JComboBox();
  private JButtonLabel btnDefinicion = new JButtonLabel();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtFactDevolver = new JTextField();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasTransporte()
  {
    this(null, "", false);
  }

  public DlgTransferenciasTransporte(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      FarmaUtility.centrarVentana(this);
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
    this.setSize(new Dimension(641, 240));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Datos de Transporte");
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
        jContentPane.setFocusable(false);
        pnlHeader1.setBounds(new Rectangle(10, 220, 415, 55));
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(0,114,169), 1));
    pnlHeader1.setBackground(Color.white);
        pnlHeader1.setFocusable(false);
        btnSenores.setText("Se�ores:");
    btnSenores.setBounds(new Rectangle(20, 15, 60, 15));
    btnSenores.setMnemonic('S');
    btnSenores.setForeground(new Color(255, 130, 14));
        btnSenores.setFocusable(false);
        btnSenores.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSenores_actionPerformed(e);
        }
      });
    lblRuc.setText("RUC:");
    lblRuc.setBounds(new Rectangle(20, 48, 70, 15));
    lblRuc.setForeground(new Color(255, 130, 14));
        lblRuc.setFocusable(false);
        lblDireccion.setText("Direcci�n:");
    lblDireccion.setBounds(new Rectangle(20, 80, 70, 15));
    lblDireccion.setForeground(new Color(255, 130, 14));
        lblDireccion.setFocusable(false);
        txtSenores.setBounds(new Rectangle(95, 15, 265, 20));
    txtSenores.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtSenores_keyPressed(e);
        }
      });
    txtRuc.setBounds(new Rectangle(95, 48, 99, 20));
    txtRuc.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtRuc_keyPressed(e);
        }
      });
    txtDireccion.setBounds(new Rectangle(95, 80, 265, 20));
    txtDireccion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDireccion_keyPressed(e);
        }
      });
    pnlTitle1.setBounds(new Rectangle(10, 355, 415, 90));
    pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTitle1.setBackground(Color.white);
        pnlTitle1.setFocusable(false);
        txtTransportista.setBounds(new Rectangle(115, 45, 265, 20));
    txtTransportista.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtTransportista_keyPressed(e);
        }
      });
    txtRucTransportista.setBounds(new Rectangle(115, 15, 100, 20));
    txtRucTransportista.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtRucTransportista_keyPressed(e);
                }

          public void keyReleased(KeyEvent e)
          {
            txtRucTransportista_keyReleased(e);
          }
      });
    txtDireccionTransportista.setBounds(new Rectangle(115, 80, 265, 20));
    txtDireccionTransportista.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDireccionTransportista_keyPressed(e);
        }
      });
    lblDireccionTransportista_T.setText("Direcci�n:");
    lblDireccionTransportista_T.setBounds(new Rectangle(20, 80, 70, 15));
    lblDireccionTransportista_T.setForeground(new Color(255, 130, 14));
        lblDireccionTransportista_T.setFocusable(false);
        lblRucTransportista_T.setText("RUC Transp:");
    lblRucTransportista_T.setBounds(new Rectangle(20, 15, 70, 15));
    lblRucTransportista_T.setForeground(new Color(255, 130, 14));
        lblRucTransportista_T.setFocusable(false);
        lblPlaca_T.setText("Placa:");
    lblPlaca_T.setBounds(new Rectangle(20, 110, 70, 15));
    lblPlaca_T.setForeground(new Color(255, 130, 14));
        lblPlaca_T.setFocusable(false);
        txtPlaca.setBounds(new Rectangle(115, 110, 100, 20));
    txtPlaca.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtPlaca_keyPressed(e);
        }
      });
    lblTransportista_T.setText("Transportista:");
    lblTransportista_T.setBounds(new Rectangle(20, 45, 85, 15));
    lblTransportista_T.setForeground(new Color(255, 130, 14));
        lblTransportista_T.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(340, 165, 85, 20));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(220, 165, 115, 20));
        lblF11.setFocusable(false);
        lblF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF11_mouseClicked(e);
                }
            });
        pnllHeader1.setBounds(new Rectangle(10, 10, 620, 140));
    pnllHeader1.setBackground(Color.white);
    pnllHeader1.setBorder(BorderFactory.createLineBorder(new Color(0, 114, 169), 1));
        pnllHeader1.setFocusable(false);
        lblFechaEmision.setText("09/01/2006");
    lblFechaEmision.setBounds(new Rectangle(315, 15, 70, 15));
    txtDestino.setBounds(new Rectangle(135, 75, 215, 20));
    txtDestino.setEnabled(false);
    txtCodigoDestino.setBounds(new Rectangle(70, 75, 55, 20));
    txtCodigoDestino.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtCodigoDestino_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtCodigoDestino_keyReleased(e);
        }
      });
    cmbTipo.setBounds(new Rectangle(70, 10, 150, 20));
    cmbTipo.setFont(new Font("SansSerif", 1, 11));
    cmbTipo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbTipo_keyPressed(e);
        }
      });
    cmbTipo.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
          cmbTipo_itemStateChanged(e);
          }
        });
    btnTipo_T.setText("Tipo");
    btnTipo_T.setBounds(new Rectangle(20, 10, 30, 15));
    btnTipo_T.setMnemonic('T');
    btnTipo_T.setForeground(new Color(0, 114, 169));
        btnTipo_T.setFocusable(false);
        btnTipo_T.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipo_T_actionPerformed(e);
        }
      });
    lblFechaEmision_T.setText("F. Emisi�n:");
    lblFechaEmision_T.setBounds(new Rectangle(240, 15, 50, 15));
    lblDestino_T.setText("Destino");
    lblDestino_T.setBounds(new Rectangle(20, 75, 50, 15));
    lblDestino_T.setForeground(new Color(0, 114, 169));
        lblDestino_T.setFocusable(false);
        btnMotivo.setText("Motivo:");
    btnMotivo.setBounds(new Rectangle(20, 40, 50, 15));
    btnMotivo.setForeground(new Color(0, 114, 169));
        btnMotivo.setFocusable(false);
        btnMotivo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipo_T_actionPerformed(e);
        }
      });
    cmbMotivo.setBounds(new Rectangle(70, 40, 195, 20));
    cmbMotivo.setFont(new Font("SansSerif", 1, 11));
    cmbMotivo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbMotivo_keyPressed(e);
        }
      });
    cmbDefinicion.setBounds(new Rectangle(70, 110, 145, 20));
    cmbDefinicion.setFont(new Font("SansSerif", 1, 11));
    cmbDefinicion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    cmbDefinicion_keyPressed(e);
                }
      });
    btnDefinicion.setBounds(new Rectangle(20, 110, 50, 15));
    btnDefinicion.setForeground(new Color(0, 114, 169));
    btnDefinicion.setText("Definido");
        btnDefinicion.setFocusable(false);
        btnDefinicion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipo_T_actionPerformed(e);
        }
      });
        jLabel1.setText("N\u00b0 Factura a Devolver :");
        jLabel1.setBounds(new Rectangle(225, 110, 135, 20));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setForeground(new Color(0, 125, 195));
        txtFactDevolver.setBounds(new Rectangle(360, 105, 190, 25));
        txtFactDevolver.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFactDevolver_keyPressed(e);
                }
            });
        pnllHeader1.add(txtFactDevolver, null);
        pnllHeader1.add(btnDefinicion, null);
        pnllHeader1.add(cmbDefinicion, null);
        pnllHeader1.add(cmbMotivo, null);
        pnllHeader1.add(btnMotivo, null);
        pnllHeader1.add(lblFechaEmision, null);
        pnllHeader1.add(txtDestino, null);
        pnllHeader1.add(txtCodigoDestino, null);
        pnllHeader1.add(cmbTipo, null);
        pnllHeader1.add(btnTipo_T, null);
        pnllHeader1.add(lblFechaEmision_T, null);
        pnllHeader1.add(lblDestino_T, null);
        pnllHeader1.add(jLabel1, null);
        pnlHeader1.add(txtDireccion, null);
    pnlHeader1.add(txtRuc, null);
    pnlHeader1.add(txtSenores, null);
    pnlHeader1.add(lblDireccion, null);
    pnlHeader1.add(lblRuc, null);
    pnlHeader1.add(btnSenores, null);
    pnlTitle1.add(lblTransportista_T, null);
    pnlTitle1.add(txtPlaca, null);
    pnlTitle1.add(lblPlaca_T, null);
    pnlTitle1.add(lblRucTransportista_T, null);
    pnlTitle1.add(lblDireccionTransportista_T, null);
    pnlTitle1.add(txtDireccionTransportista, null);
    pnlTitle1.add(txtRucTransportista, null);
    pnlTitle1.add(txtTransportista, null);
        jContentPane.add(pnllHeader1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    //
    txtSenores.setLengthText(30);
    txtCodigoDestino.setLengthText(15);
    //txtDestino.setLengthText(30);
    txtRuc.setLengthText(15);
    txtDireccion.setLengthText(120);
    txtTransportista.setLengthText(30);
    txtRucTransportista.setLengthText(15);
    txtDireccionTransportista.setLengthText(120);
    txtPlaca.setLengthText(15);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCmbTipo();
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCmbTipo()
  {
    ArrayList parametros = new ArrayList(); 
    FarmaLoadCVL.loadCVLFromSP(cmbTipo,ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,"PTOVENTA_INV.INV_GET_TIPO_TRANSFERENCIA",parametros, false); 
    parametros = null;
  }
 //Agregado por Paulo motivos de transferencia interno 
  private void initCmbMotivoInterno()
  {
    cmbDefinicion.removeAllItems();
    ArrayList parametros = new ArrayList(); 
    FarmaLoadCVL.loadCVLFromSP(cmbDefinicion,ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,"PTOVENTA_INV.INV_GET_MOTIVO_TRANS_INTERNO",parametros, false); 
    parametros = null;
  }
  
  private void initCmbMotivo()
  {
    cmbMotivo.removeAllItems();
    if(!FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex()).equals(""))
    {
      ArrayList parametros = new ArrayList(); 
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex()));
      
      FarmaLoadCVL.loadCVLFromSP(cmbMotivo,ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,"PTOVENTA_INV.INV_GET_MOTIVO_TRANSF(?,?)",parametros, false,true); 
      parametros = null;  
    }
    if(FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL))
    {
      muestraMotivoInterno(true);
      initCmbMotivoInterno();
    } else muestraMotivoInterno(false); 
    
    txtCodigoDestino.setText("");
    txtDestino.setText("");
  }
   private void muestraMotivoInterno(boolean valor)
   {
     btnDefinicion.setVisible(valor);
     cmbDefinicion.setVisible(valor);
   }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnTipo_T_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbTipo);
  }
  
  private void cmbTipo_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(cmbMotivo);
      FarmaVariables.vAceptar = false;
    }
    else
      chkKeyPressed(e);
  }
  
  private void cmbTipo_itemStateChanged(ItemEvent e)
  {
    initCmbMotivo();
  }
  
  private void cmbMotivo_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtCodigoDestino);
      FarmaVariables.vAceptar = false;
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtCodigoDestino_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      String tipoMaestro = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex());
      if(tipoMaestro.trim().length() == 0)
        FarmaUtility.showMessage(this,"Seleccione un Tipo de Origen.",cmbTipo);
      else
      {
        if(tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL))
          VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
        else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
          VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_MATRIZ;
        else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
          VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;
          
        validaCodigo(txtCodigoDestino, txtDestino, VariablesPtoVenta.vTipoMaestro);  
      }
   }
    else 
      chkKeyPressed(e);
  }
  
  private void txtCodigoDestino_keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
    {
      //mostrarDatosTransporte();
      FarmaVariables.vAceptar = false;
      if(cmbDefinicion.isVisible()) FarmaUtility.moveFocus(cmbDefinicion);
      else FarmaUtility.moveFocus(txtSenores);
    }
  }
  
  private void btnSenores_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtSenores);
  }
  
  private void txtSenores_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtRuc);
      txtSenores.setText(txtSenores.getText().toUpperCase().trim());
    }
    else
      chkKeyPressed(e);
  }

  private void txtRuc_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtDireccion);
    }
    else
      chkKeyPressed(e);
  }

  private void txtDireccion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtRucTransportista);
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtTransportista_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtDireccionTransportista);
    }
    else
      chkKeyPressed(e);
  }

  private void txtRucTransportista_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_TRANSPORTISTA;
      validaCodigo(txtRucTransportista, txtTransportista, VariablesPtoVenta.vTipoMaestro);
    }
    else
      chkKeyPressed(e);
  }

  private void txtRucTransportista_keyReleased(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
    {
     //mostrarTransportista();
      getDatosTranspRecepCiega();
      FarmaUtility.moveFocus(txtTransportista);
      FarmaVariables.vAceptar = false;
    }
  }
  
  private void txtDireccionTransportista_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtPlaca);
    }
    else
      chkKeyPressed(e);
  }

  private void txtPlaca_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtSenores);
    }
    else
      chkKeyPressed(e);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(cmbTipo);  
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {evento_f11();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {   FarmaUtility.moveFocus(cmbTipo);
        }
    }
  
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
  {
    if(pJTextField_Cod.getText().trim().length() > 0)
    {
      VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
      ArrayList myArray = new ArrayList();
      buscaCodigoListaMaestro(myArray);
      
      if(myArray.size() == 0)
      {
     FarmaUtility.showMessage(this,"Ruc No Encontrado",pJTextField_Cod);
        FarmaVariables.vAceptar = false;
      } else if(myArray.size() == 1)
      {
        String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
        pJTextField_Cod.setText(codigo);
        pJTextField_Desc.setText(descripcion);
        VariablesPtoVenta.vCodMaestro = codigo;
        FarmaVariables.vAceptar = true;
      } else
      {
        FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",pJTextField_Cod);
        FarmaVariables.vAceptar = false;
      }
    } else
    {
      cargaListaMaestros(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
    }
  }
  
  private void buscaCodigoListaMaestro(ArrayList pArrayList)
  {
    try
    {
      DBPtoVenta.buscaCodigoListaMaestro(pArrayList,VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al buscar c�digo en maestros :\n" + sql.getMessage(),cmbTipo);
    }
  }
  
  private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
  {
    VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
    DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
    dlgListaMaestros.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
      pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
    }
  }

  private boolean validarCampos()
  {
    boolean retorno = true;
    
    if(txtFactDevolver.getText().trim().equals(""))
    {
        txtFactDevolver.setText(".");
      //FarmaUtility.showMessage(this,"Debe Ingresar una Factura a Devolver.",txtFactDevolver);
      //retorno = false;
    }
    else
    if(cmbTipo.getSelectedIndex() < 1)
    {
      FarmaUtility.showMessage(this,"Debe seleccionar un Tipo de Transferencia",cmbTipo);
      retorno = false;
    }else if(cmbMotivo.getSelectedIndex() < 1)
    {
      FarmaUtility.showMessage(this,"Debe seleccionar un Motivo de Transferencia",cmbMotivo);
      retorno = false;
    }else if(cmbDefinicion.getSelectedIndex() < 1 && cmbDefinicion.isVisible())
    {
      FarmaUtility.showMessage(this,"Debe seleccionar un Motivo de Transferencia Interna",cmbDefinicion);
      retorno = false;
    }else if(txtCodigoDestino.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar el Codigo de Destino",txtCodigoDestino);
      retorno = false;
    }else if(txtCodigoDestino.getText().trim().equals(FarmaVariables.vCodLocal))
    {
      txtCodigoDestino.setText("");
      txtDestino.setText("");
      FarmaUtility.showMessage(this,"No puede realizar esta transferencia. Modifique el destino.",txtCodigoDestino);
      retorno = false;
      FarmaVariables.vAceptar = false;
    }/* else if(txtSenores.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar la Razon Social",txtSenores);
      retorno = false;
    }
    else if(UtilityInventario.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
    {
      FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRuc);
      retorno = false;
    }
    else if(txtRuc.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Destinatario",txtRuc);
      retorno = false;
    }else if(txtDireccion.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar la Direccion de Destino",txtDireccion);
      retorno = false;
    }else if(txtTransportista.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar el Transportista",txtTransportista);
      retorno = false;
    }else if(txtRucTransportista.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Transportista",txtRucTransportista);
      retorno = false;
    }
    else if(UtilityInventario.verificaRucValido(txtRucTransportista.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
    {
      FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucTransportista);
      retorno = false;
    }
    else if(txtDireccionTransportista.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar la Direccion del Transportista",txtDireccionTransportista);
      retorno = false;
    }else if(txtPlaca.getText().trim().equals(""))
    {
      FarmaUtility.showMessage(this,"Debe ingresar la Placa del Transportista",txtPlaca);
      retorno = false;
    } */
    
    return retorno;
  }
  
  private void cargarCabecera()
  {
    VariablesInventario.vTipoDestino_Transf = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex());
    if(cmbDefinicion.isVisible()) VariablesInventario.vMotivo_Transf_Interno = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO,cmbDefinicion.getSelectedIndex());
    else VariablesInventario.vMotivo_Transf_Interno = "" ;
    VariablesInventario.vMotivo_Transf = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,cmbMotivo.getSelectedIndex());
    log.error("vMotivoTransf: "+VariablesInventario.vMotivo_Transf);
    //JMIRANDA 10.12.09
    // nombre MOTIVO transf larga
    VariablesInventario.vDescMotivo_Transf = FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_TRANSF,VariablesInventario.vMotivo_Transf);          
    VariablesInventario.vDescMotivo_Transf_Larga = obtieneDescripcionLarga(VariablesInventario.vMotivo_Transf.trim(),VariablesInventario.vDescMotivo_Transf.trim());
      log.error("Corta: "+VariablesInventario.vDescMotivo_Transf+
                " Larga: "+VariablesInventario.vDescMotivo_Transf_Larga);
     
    VariablesInventario.vCodDestino_Transf = txtCodigoDestino.getText();
    VariablesInventario.vDestino_Transf = txtSenores.getText();  
    VariablesInventario.vRucDestino_Transf = txtRuc.getText();
    VariablesInventario.vDirecDestino_Transf = txtDireccion.getText();
    VariablesInventario.vTransportista_Transf = txtTransportista.getText(); //
    VariablesInventario.vRucTransportista_Transf = txtRucTransportista.getText();//
    VariablesInventario.vDirecTransportista_Transf = txtDireccionTransportista.getText();//
    VariablesInventario.vPlacaTransportista_Transf = txtPlaca.getText();//
    if(VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
      VariablesInventario.vTransfMatriz = true;
    else
      VariablesInventario.vTransfMatriz = false;
    log.debug("VariablesInventario.vTransfMatriz "+ VariablesInventario.vTransfMatriz);      
    log.debug("VariablesInventario.vTipoDestino_Transf "+ VariablesInventario.vTipoDestino_Transf);      
    VariablesInventario.vFactADevolver = txtFactDevolver.getText().trim();
    
      log.debug("VariablesInventario.vFactADevolver "+ VariablesInventario.vFactADevolver);      
      
  }
  
  private void mostrarDatosTransporte()
  {
    if(!FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
    {
      try
      {
        ArrayList array = new ArrayList();
        DBInventario.getDatosTransporte(array,txtCodigoDestino.getText());
        //log.debug("Array Datos Transp:"+array.size()+"");
        if(array.size()>0)
        {
          array = (ArrayList)array.get(0);
          txtSenores.setText(array.get(0).toString());  
          txtRuc.setText(array.get(1).toString());
          txtDireccion.setText(array.get(2).toString());
          txtTransportista.setText(array.get(3).toString()); 
          txtRucTransportista.setText(array.get(4).toString());
          txtDireccionTransportista.setText(array.get(5).toString());
          txtPlaca.setText(array.get(6).toString());
        }
      }catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurii� un error al obtener datos del transportista : \n" + sql.getMessage(),cmbTipo);
      }
    }
  }

  private void mostrarTransportista()
  {
    txtDireccionTransportista.setText("");
    txtPlaca.setText("");
    try
    {
      ArrayList array = new ArrayList();
      DBInventario.getDatosTransportista(array,txtRucTransportista.getText());
      if(array.size()>0)
      {
        array = (ArrayList)array.get(0);
        txtDireccionTransportista.setText(array.get(0).toString());
        txtPlaca.setText(array.get(1).toString());
      }
    }
    catch (SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this, 
                               "Ha ocurrido un error al obtener datos del transportista.\n" + 
                               e.getMessage(), txtRucTransportista);
    }
  }

  private void cmbDefinicion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtFactDevolver);
    }
    else
      chkKeyPressed(e);
  
  }
  
  /**
   * obtiene el valor para poder saber si se habilitara el Text de Fraccion
   * @author dubilluz
   * @since  15.10.2007
   */
  public void consultamotivo()
  {
   log.debug("VariablesInventario.vMotivo_Transf : " + VariablesInventario.vMotivo_Transf);
   String codTipo =  FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex());
   try
   {
     VariablesInventario.vIndTextFraccion  =  DBInventario.consultaMotivo(VariablesInventario.vMotivo_Transf,codTipo);
   }
   catch (SQLException e)
   {
     log.error("",e);
     FarmaUtility.showMessage(this, 
                              "Ha ocurrido un error cuando se consultaba el motivo.\n" + 
                              e.getMessage(), null);
   }
   
   log.debug("VariablesInventario.vIndTextFraccion : "+VariablesInventario.vIndTextFraccion );
  }
  
  /**
   * OBTIENE DESCRIPCION LARGA DE MOTIVO DE TRANSFERENCIA
   * JMIRANDA 10.12.09
   */
  public String obtieneDescripcionLarga (String pLlaveTabGral, String pDescCorta) {
      String vDescLarga = "";
      try {
      vDescLarga = DBInventario.getObtieneDescLargaTransf(pLlaveTabGral, pDescCorta);
          if(vDescLarga.trim().equalsIgnoreCase("N")){
              vDescLarga = "";
          }
      }
      catch (SQLException sql){
          log.error("",sql);
          vDescLarga = "";
      }
      return vDescLarga;
  }
  
  //Cesar Huanes
  private void getDatosTranspRecepCiega(){
      txtDireccionTransportista.setText("");
      txtPlaca.setText("-");
      try
      {
        ArrayList array = new ArrayList();
      
         DBInventario.getDatosTranspRecepCiega(array,txtRucTransportista.getText()); 
        if(array.size()>0)
        {
          array = (ArrayList)array.get(0);
          String codTransp=array.get(0).toString();
          String rucTransp=array.get(1).toString();
          if(rucTransp.trim()==null ||rucTransp.trim().equals(0) || rucTransp.trim().length()!=11){
              txtRucTransportista.setText("");          
          }
          String direccionTransp=array.get(3).toString();
         txtDireccionTransportista.setText(direccionTransp);
          
          if(codTransp.trim()!=null &&(rucTransp.trim()!=null )){
             txtRucTransportista.setText("");
             txtRucTransportista.setText(rucTransp); 
             txtTransportista.setText(array.get(2).toString());
             txtDireccionTransportista.setText(array.get(3).toString());
           
          }
         
           
        }
      }
      catch (SQLException e)
      {
        log.error("",e);
        FarmaUtility.showMessage(this, 
                                 "Ha ocurrido un error al obtener datos del transportista.\n" + 
                                 e.getMessage(), txtRucTransportista);
      }    
      
  }


    private void txtFactDevolver_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void evento_f11() {

        if(validarCampos())
        {
            cargarCabecera();
            /**
            * Consultara si Habilita el texto de Fraccion
            * de acuerdo al motivo , esta consulta se hace con base de datos
            * @author dubilluz
            * @since  15.10.2007
            */
            consultamotivo(); 
            cerrarVentana(true);
        }
    }
}
