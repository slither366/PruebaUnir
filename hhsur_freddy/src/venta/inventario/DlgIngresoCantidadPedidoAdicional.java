package venta.inventario;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.sql.*;

import javax.swing.JDialog;
import javax.swing.BorderFactory;

import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JTextFieldSanSerif;

import common.*;
import venta.inventario.reference.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgIngresoCantidadPedidoAdicional.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * DVELIZ      10.09.08   Creaci�n<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresoCantidadPedidoAdicional extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadPedidoAdicional.class);

  private Frame myParentFrame;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite pnlBlanco = new JPanelWhite();
  private JPanelHeader pnlHeader = new JPanelHeader();
  private JLabelWhite lblCodProd_t = new JLabelWhite();
    private JLabelWhite lblCodProd = new JLabelWhite();
    private JLabelWhite lblValFrac_t = new JLabelWhite();
  private JLabelWhite lblValFrac = new JLabelWhite();
    private JPanelWhite pnlIngreso = new JPanelWhite();
  private JButtonLabel btnCantidad = new JButtonLabel();
  private JLabelOrange lblStock_t = new JLabelOrange();
  private JLabelOrange lblFecha = new JLabelOrange();
  private JLabelOrange lblStock = new JLabelOrange();
  private JLabelOrange lblCant_t = new JLabelOrange();
  private JTextFieldSanSerif txtCantAdic = new JTextFieldSanSerif();
  private JLabelFunction lblFCerrar = new JLabelFunction();
  private JLabelFunction lblFAceptar = new JLabelFunction();

  /* ********************************************************************** */
	/*                        CONSTRUCTOR                                     */
	/* ********************************************************************** */
  
  public DlgIngresoCantidadPedidoAdicional(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(785, 208));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingreso Cantidad de Promedio de Ventas Mensual (PVM)");
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
    pnlHeader.setBounds(new Rectangle(10, 10, 760, 65));
    lblCodProd_t.setText("Producto");
    lblCodProd_t.setBounds(new Rectangle(5, 10, 60, 20));
        lblCodProd.setText("lblCodProd");
    lblCodProd.setBounds(new Rectangle(65, 10, 550, 20));
    lblCodProd.setFont(new Font("SansSerif", 0, 11));
        lblValFrac_t.setText("Laboratorio.");
    lblValFrac_t.setBounds(new Rectangle(10, 35, 70, 20));
    lblValFrac.setText("lblValFrac");
    lblValFrac.setBounds(new Rectangle(90, 35, 280, 20));
    lblValFrac.setFont(new Font("SansSerif", 0, 11));
      lblValFrac.setVisible(true);
      lblValFrac_t.setVisible(true);
        pnlIngreso.setBounds(new Rectangle(10, 80, 760, 70));
    pnlIngreso.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 2));
    btnCantidad.setText("Ingrese Cantidad:");
    btnCantidad.setBounds(new Rectangle(20, 40, 115, 20));
    btnCantidad.setMnemonic('c');
    btnCantidad.setForeground(new Color(255, 130, 14));
    btnCantidad.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCantidad_actionPerformed(e);
        }
      });
    lblStock_t.setText("Stock del producto al");
    lblStock_t.setBounds(new Rectangle(20, 10, 125, 20));
    lblFecha.setText("lblFecha");
    lblFecha.setBounds(new Rectangle(145, 10, 100, 20));
    lblStock.setBounds(new Rectangle(250, 10, 70, 20));
    lblStock.setHorizontalAlignment(JLabelOrange.RIGHT);
    lblCant_t.setBounds(new Rectangle(325, 10, 65, 20));
    lblCant_t.setText("unidades.");
    txtCantAdic.setBounds(new Rectangle(140, 40, 75, 20));
    txtCantAdic.setLengthText(6);
    txtCantAdic.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtCantAdic_keyTyped(e);
        }

        public void keyPressed(KeyEvent e)
        {
          txtCantAdic_keyPressed(e);
        }
      });
    lblFCerrar.setText("[ ESC ] Cerrar");
    lblFCerrar.setBounds(new Rectangle(670, 155, 100, 20));
    lblFAceptar.setText("[ Enter ] Aceptar");
    lblFAceptar.setBounds(new Rectangle(530, 155, 110, 20));
        pnlHeader.add(lblValFrac, null);
        pnlHeader.add(lblValFrac_t, null);
        pnlHeader.add(lblCodProd_t, null);
        pnlHeader.add(lblCodProd, null);
        pnlIngreso.add(txtCantAdic, null);
        pnlIngreso.add(lblCant_t, null);
        pnlIngreso.add(lblStock, null);
        pnlIngreso.add(lblFecha, null);
        pnlIngreso.add(lblStock_t, null);
        pnlIngreso.add(btnCantidad, null);
        pnlBlanco.add(lblFAceptar, null);
        pnlBlanco.add(lblFCerrar, null);
        pnlBlanco.add(pnlIngreso, null);
        pnlBlanco.add(pnlHeader, null);
    this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
  }
  
  /* *********************************************************** */
  /*                     METODO initialize                       */
  /* *********************************************************** */
  
  private void initialize()
  {
    mostrarDatos();
  }

  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtCantAdic);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  private void txtCantAdic_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  private void btnCantidad_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtCantAdic);
  }
  
  private void txtCantAdic_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCantAdic,e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      ingresarCantidad();
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
  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
  
  private void ingresarCantidad()
  {
    String vCantidad = txtCantAdic.getText();

   if(!FarmaUtility.isInteger(vCantidad))
      FarmaUtility.showMessage(this,"Debe ingresar una cantidad v�lida.",txtCantAdic);
   else
     /*if(FarmaUtility.getDecimalNumber(vCantidad) <= FarmaUtility.getDecimalNumber(VariablesInventario.vCantMax_PedRep) 
        && !vCantidad.equalsIgnoreCase("0") && UtilityInventario.validaCerosIzquierda(vCantidad))
      { */
      
     if(UtilityInventario.validaCerosIzquierda(vCantidad))
     { 
        FarmaUtility.showMessage(this,"La cantidad adicional tiene que ser entera",txtCantAdic);
     }
     else{
          if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro que desea realizar la operaci�n?"))
          {
            VariablesInventario.vCantAdicional = txtCantAdic.getText();
            try
            {
              if(FarmaVariables.vEconoFar_Matriz){
                  return;
              }else{
                  log.debug("Actualizando desde Local");
                  UtilityInventario.guardarCantidadPedidoAdicionalLocal(
                                    VariablesInventario.vCodProd_PedRep,
                                    VariablesInventario.vCantAdicional,
                                    "0",
                                    "N");
                  FarmaUtility.aceptarTransaccion();
                  FarmaUtility.showMessage(this,"Se realiz� la operaci�n satisfactoriamente.",txtCantAdic);
              }
            }
            catch(SQLException sql)
            {
              FarmaUtility.liberarTransaccion();
              FarmaUtility.showMessage(this,"Error al guardar la cantidad adicional: "+sql,txtCantAdic);
            }
            cerrarVentana(true);        
          }
          else
          {
            FarmaUtility.showMessage(this,"Se cancel� la operaci�n.",txtCantAdic);
          }
    }
  }
  
  private void mostrarDatos()
  {
    lblCodProd.setText(VariablesInventario.vCodProd_PedRep+" - "+
                        VariablesInventario.vNomProd_PedRep+" "+
                        VariablesInventario.vUnidMed_PedRep
                        );
    lblValFrac.setText(VariablesInventario.vNomLab_PedRep);
    lblFecha.setText(VariablesInventario.vFechaHora_PedRep);
    lblStock.setText(VariablesInventario.vStkFisico_PedRep);
    //lblValFrac.setText(VariablesInventario.vValFrac_PedRep);
  }
}