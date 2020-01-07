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
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import common.FarmaUtility;
import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMensajeUsuario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      03.05.2007   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgSeleccionIngresoPedCotiza
  extends JDialog{
/* ************************************************************************** */
/*                           DECLARACION PROPIEDADES                          */
/* ************************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionIngresoPedCotiza.class);
  
  Frame myParentFrame;
  //FarmaTableModel tableModel;
  
  private String mensaje;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblF11 = new JLabelFunction();
    private JButton btnCotizacion = new JButton();
    private JButton btnPedidoAnulado = new JButton();
    
    public boolean vCotiza = false;
    public boolean vPedido = true;

    /* ************************************************************************** */
/*                              CONSTRUCTORES                                 */
/* ************************************************************************** */

  public DlgSeleccionIngresoPedCotiza(){
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }

  }

  public DlgSeleccionIngresoPedCotiza(Frame parent, String title, boolean modal){
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }

/* ************************************************************************** */
/*                              METODO jbInit                                 */
/* ************************************************************************** */

  private void jbInit()
    throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(451, 228));
    this.setDefaultCloseOperation(0);
    this.setTitle("Mensaje");
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
        jContentPane.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jContentPane_keyPressed(e);
                }
            });
        lblF11.setBounds(new Rectangle(310, 165, 105, 20));
    lblF11.setText("[ Esc ] Cerrar");
        lblF11.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblF11_keyPressed(e);
                }
            });
        btnCotizacion.setText("Cotizaci\u00f3n");
        btnCotizacion.setBounds(new Rectangle(30, 15, 160, 115));
        btnCotizacion.setBackground(new Color(0, 107, 165));
        btnCotizacion.setForeground(new Color(0, 107, 165));
        btnCotizacion.setFont(new Font("SansSerif", 1, 23));
        btnCotizacion.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    btnCotizacion_focusGained(e);
                }
            });
        btnCotizacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnCotizacion_keyPressed(e);
                }
            });
        btnCotizacion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnCotizacion_actionPerformed(e);
                }
            });
        btnPedidoAnulado.setText("Interconsultas");
        btnPedidoAnulado.setBounds(new Rectangle(265, 15, 150, 115));
        btnPedidoAnulado.setFont(new Font("SansSerif", 1, 16));
        btnPedidoAnulado.setForeground(new Color(0, 107, 165));
        btnPedidoAnulado.setBackground(new Color(198, 198, 198));
        btnPedidoAnulado.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    btnPedidoAnulado_focusGained(e);
                }
            });
        btnPedidoAnulado.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnPedidoAnulado_keyPressed(e);
                }
            });
        btnPedidoAnulado.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnPedidoAnulado_actionPerformed(e);
                }
            });
        jContentPane.add(btnPedidoAnulado, null);
        jContentPane.add(btnCotizacion, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

/* ************************************************************************** */
/*                            METODO initialize                               */
/* ************************************************************************** */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
  }

/* ************************************************************************** */
/*                          METODOS INICIALIZACION                            */
/* ************************************************************************** */

/* ************************************************************************** */
/*                            METODOS DE EVENTOS                              */
/* ************************************************************************** */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(btnCotizacion);
    //txtPane1.setEnabled(false);
    FarmaUtility.centrarVentana(this);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }
  
  private void btnMensaje_actionPerformed(ActionEvent e)
  {
    //FarmaUtility.moveFocus(txtOculto);
  }
  
  private void txtOculto_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
/* ************************************************************************** */
/*                       METODOS AUXILIARES DE EVENTOS                        */
/* ************************************************************************** */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,1);

    if (UtilityPtoVenta.verificaVK_F11(e))
    {
      cerrarVentana(false);
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

/* ************************************************************************** */
/*                       METODOS DE LOGICA DE NEGOCIO                         */
/* ************************************************************************** */

  public void setMensaje(String pMensaje)
  {
    mensaje = pMensaje;
  }

    private void btnCotizacion_focusGained(FocusEvent e) {
        btnCotizacion.setBackground(new Color(0, 107, 165));
        btnPedidoAnulado.setBackground(new Color(198, 198, 198));
    }

    private void btnPedidoAnulado_focusGained(FocusEvent e) {
        btnPedidoAnulado.setBackground(new Color(0, 107, 165));
        btnCotizacion.setBackground(new Color(198, 198, 198));
    }

    private void btnCotizacion_keyPressed(KeyEvent e) {
        //e.consume();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT){
            if(btnCotizacion.getBackground()==(new Color(0, 107, 165))){
                FarmaUtility.moveFocus(btnPedidoAnulado);
            }
            else{
                FarmaUtility.moveFocus(btnCotizacion);
            }
                
        }
        else{
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
                cerrarVentana(false);
        }
        
    }

    private void btnPedidoAnulado_keyPressed(KeyEvent e) {
       // e.consume();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT){
            if(btnCotizacion.getBackground()==(new Color(0, 107, 165))){
                FarmaUtility.moveFocus(btnPedidoAnulado);
            }
            else{
                FarmaUtility.moveFocus(btnCotizacion);
            }
                
        }
        else{
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
                cerrarVentana(false);
        }
    }

    private void jContentPane_keyPressed(KeyEvent e) {
       // e.consume();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT){
            if(btnCotizacion.getBackground()==(new Color(0, 107, 165))){
                FarmaUtility.moveFocus(btnPedidoAnulado);
            }
            else{
                FarmaUtility.moveFocus(btnCotizacion);
            }
                
        }
    }

    private void lblF11_keyPressed(KeyEvent e) {
        //e.consume();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_LEFT){
            if(btnCotizacion.getBackground()==(new Color(0, 107, 165))){
                FarmaUtility.moveFocus(btnPedidoAnulado);
            }
            else{
                FarmaUtility.moveFocus(btnCotizacion);
            }
                
        }
    }

    public void setVCotiza(boolean vCotiza) {
        this.vCotiza = vCotiza;
    }

    public boolean isVCotiza() {
        return vCotiza;
    }

    public void setVPedido(boolean vPedido) {
        this.vPedido = vPedido;
    }

    public boolean isVPedido() {
        return vPedido;
    }

    private void btnCotizacion_actionPerformed(ActionEvent e) {
        setVCotiza(true);
        setVPedido(false);
        cerrarVentana(true);
            
    }

    private void btnPedidoAnulado_actionPerformed(ActionEvent e) {
        setVCotiza(false);
        setVPedido(true);
        cerrarVentana(true);
    }
}
