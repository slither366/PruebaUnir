package venta.delivery;
import java.awt.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import componentes.gs.componentes.JPanelWhite;
import java.awt.Dimension;
import componentes.gs.componentes.JPanelTitle;
import java.awt.Rectangle;
import componentes.gs.componentes.JTextFieldSanSerif;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import common.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import venta.delivery.*;
import venta.delivery.reference.*;
import javax.swing.JComboBox;
import componentes.gs.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.VariablesModuloVentas;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgBuscaTelefono.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      11.04.2006   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgBuscaTelefono extends JDialog
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgBuscaTelefono.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JComboBox cmbTipotelefono = new JComboBox();
  private JButtonLabel lblTelefono = new JButtonLabel();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgBuscaTelefono()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  public DlgBuscaTelefono(Frame parent, String title, boolean modal)
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
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Telefono");
    this.setSize(new Dimension(312, 123));
    this.setDefaultCloseOperation(0);
    this.addWindowListener(new java.awt.event.WindowAdapter()
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
    pnlTitle1.setBounds(new Rectangle(10, 10, 285, 50));
    txtTelefono.setBounds(new Rectangle(165, 15, 110, 20));
    txtTelefono.setDocument(new FarmaLengthText(8));
    txtTelefono.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtTelefono_keyPressed(e);
        }
      });
    lblEsc.setBounds(new Rectangle(210, 70, 85, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(105, 70, 90, 20));
    lblF11.setText("[ F11 ] Aceptar");
    cmbTipotelefono.setBounds(new Rectangle(85, 15, 65, 20));
    cmbTipotelefono.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          cmbTipotelefono_keyPressed(e);
        }
      });
    lblTelefono.setText("Telefono");
    lblTelefono.setBounds(new Rectangle(5, 20, 75, 15));
    lblTelefono.setMnemonic('T');
    lblTelefono.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          lblTelefono_actionPerformed(e);
        }
      });
    pnlTitle1.add(lblTelefono, null);
    pnlTitle1.add(cmbTipotelefono, null);
    pnlTitle1.add(txtTelefono, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(pnlTitle1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);

  }

  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    cargaCombo();
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

	private void this_windowOpened(WindowEvent e)
  {
    log.debug("Numero Telefonico  : " + VariablesDelivery.vNumeroTelefono);
    FarmaUtility.moveFocus(txtTelefono);
    FarmaUtility.centrarVentana(this);
    //txtTelefono.setText(VariablesDelivery.vNumeroTelefono);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void txtTelefono_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(venta.reference.UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(!validaNumeroTelefono()) return;
      log.debug("VariablesDelivery.vNombreInHashtable : " + VariablesDelivery.vNombreInHashtable);
      VariablesDelivery.vCampo = FarmaLoadCVL.getCVLCode(VariablesDelivery.vNombreInHashtable,cmbTipotelefono.getSelectedIndex());
      log.debug("Tipo telefono *********************: " + VariablesDelivery.vCampo);
      obtieneNumeroTelefono();

    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, "Est� seguro que Desea salir ?"))
        cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar)
	{
    FarmaVariables.vImprTestigo = FarmaConstants.INDICADOR_N ;
    log.debug("FarmaVariables.vImprTestigo : " + FarmaVariables.vImprTestigo);
		FarmaVariables.vAceptar = pAceptar;
        VariablesModuloVentas.vEsPedidoDelivery = false ;
    log.debug("VariablesVentas.vEsPedidoDelivery : " + VariablesModuloVentas.vEsPedidoDelivery);
		this.setVisible(false);
    //FarmaVariables.vAceptar = true;
    this.dispose();
  }

  private void muestraListaClientes()
  {
    DlgListaClientes dlgListaClientes = new DlgListaClientes(myParentFrame,"",true);
    dlgListaClientes.setVisible(true);
  }

  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void obtieneNumeroTelefono()
  {
    VariablesDelivery.vNumeroTelefono = txtTelefono.getText().trim();

    if(!FarmaUtility.isLong(txtTelefono.getText().trim()))
    {
      FarmaUtility.showMessage(this,"Escriba un Numero Telefonico Valido",txtTelefono);
      //return false;
      //DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(myParentFrame,"",true);
      //dlgResumenPedido.setVisible(true);
    } else if  (VariablesDelivery.vNumeroTelefono.length()>3 && VariablesDelivery.vNumeroTelefono.length()<7)
    {
      FarmaUtility.showMessage(this,"Debe escribir un numero telefonico valido",txtTelefono);
    } else
    {
      log.debug("Telefono"+VariablesDelivery.vNumeroTelefono);
      cerrarVentana(true);
    }

  }

  private void cargaCombo()
  {
    String [] IND_DESCRIP_CAMPO = {"Fijo","Movil"};
    String [] IND_CAMPO = {"01","02"};
    VariablesDelivery.vNombreInHashtable = ConstantsDelivery.VNOMBREINHASHTABLEBUSCATELEFONO ;
    FarmaLoadCVL.loadCVLfromArrays(getCmbCampo(),
                                   VariablesDelivery.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
  }

  public JComboBox getCmbCampo()
  {
    return this.cmbTipotelefono ;
  }

  private void lblTelefono_actionPerformed(ActionEvent e)
  {
  FarmaUtility.moveFocus(cmbTipotelefono);
  }

  private void cmbTipotelefono_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtTelefono);
    }
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private boolean validaNumeroTelefono()
  {
    if(!FarmaUtility.isLong(txtTelefono.getText().trim()))
    {
      FarmaUtility.showMessage(this,"Escriba un Numero Telefonico Valido",txtTelefono);
      return false;
      //DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(myParentFrame,"",true);
      //dlgResumenPedido.setVisible(true);
    }
    return true;
  }
}