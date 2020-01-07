package venta.inventario;

import componentes.gs.componentes.JConfirmDialog;

import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;

import java.sql.*;
import java.util.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JPanelTitle;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import common.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import venta.inventario.reference.*;
import venta.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgTransferenciasLocal.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      25.07.2006   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgTransferenciasLocal extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasLocal.class); 

  Frame myParentFrame;
  FarmaTableModel tableModel;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaTransferencias = new JScrollPane();
  private JTable tblListaTransferencias = new JTable();
  private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF9 = new JLabelFunction();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasLocal()
  {
    this(null, "", false);
  }

  public DlgTransferenciasLocal(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(759, 349));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Relacion de Transferencias");
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
    pnlTitle1.setBounds(new Rectangle(10, 10, 710, 25));
    scrListaTransferencias.setBounds(new Rectangle(10, 35, 710, 230));
    btnRelacionTransferencias.setText("Relacion de Transferencias");
    btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 165, 15));
    btnRelacionTransferencias.setMnemonic('R');
    btnRelacionTransferencias.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnRelacionTransferencias_keyPressed(e);
        }
      });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(375, 275, 90, 20));
    lblF2.setText("[ F2 ] Ver Transferencia");
    lblF2.setBounds(new Rectangle(10, 275, 155, 20));
        lblF2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF2_mouseClicked(e);
                }
            });
        lblF9.setText("[ F9 ] Aceptar Transferencia");
    lblF9.setBounds(new Rectangle(175, 275, 190, 20));
        lblF9.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF9_mouseClicked(e);
                }
            });
        scrListaTransferencias.getViewport().add(tblListaTransferencias, null);
    jContentPane.add(lblF9, null);
    jContentPane.add(lblF2, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(scrListaTransferencias, null);
    pnlTitle1.add(btnRelacionTransferencias, null);
    jContentPane.add(pnlTitle1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaTransfLocal,ConstantsInventario.defaultValuesListaTransfLocal,0);
    FarmaUtility.initSimpleList(tblListaTransferencias,tableModel,ConstantsInventario.columnsListaTransfLocal);
    cargaListaTransferencias();
  }
  
  private void cargaListaTransferencias()
  {
    try
    {
      DBInventario.cargaListaTransfLocal(tableModel);
      FarmaUtility.ordenar(tblListaTransferencias,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al listar las transferencias: \n" + sql.getMessage(),btnRelacionTransferencias);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnRelacionTransferencias_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(btnRelacionTransferencias);  
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
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaTransferencias,null,0);
    
    if(UtilityPtoVenta.verificaVK_F2(e))
    {
      funcionF2();
    }else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionTransferencias);
      else {
       if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
        funcionF9();
       }
       else
       {
        FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opci�n",null);
       }
      }
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private void funcionF2()
  {
    VariablesInventario.vPos = tblListaTransferencias.getSelectedRow();
    if(VariablesInventario.vPos > -1)
    {
      VariablesInventario.vNumNota = tblListaTransferencias.getValueAt(VariablesInventario.vPos,0).toString();
      VariablesInventario.vCodOrigen = tblListaTransferencias.getValueAt(VariablesInventario.vPos,5).toString();
      DlgTransferenciasLocalVer dlgTransferenciasLocalVer = new DlgTransferenciasLocalVer(myParentFrame,"",true);
      dlgTransferenciasLocalVer.setVisible(true);
    }
  }
  private void funcionF9()
  {
    VariablesInventario.vPos = tblListaTransferencias.getSelectedRow();
    if(VariablesInventario.vPos > -1)
    {
      VariablesInventario.vNumNota = tblListaTransferencias.getValueAt(VariablesInventario.vPos,0).toString();
      VariablesInventario.vCodOrigen = tblListaTransferencias.getValueAt(VariablesInventario.vPos,5).toString();
      
      if(JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro de Aceptar la Transferencia?"))
      {
        try
        {
          DBInventario.aceptaTransfLocal(VariablesInventario.vNumNota,VariablesInventario.vCodOrigen);
          FarmaUtility.showMessage(this,"Transferencia aceptada.",btnRelacionTransferencias);
          
        }catch(SQLException e)
        {
          FarmaUtility.liberarTransaccion();
          log.error("",e);
          FarmaUtility.showMessage(this,"Ha ocurrido un error al aceptar la transferencia.\n"+e,btnRelacionTransferencias);
          
        }
        cargaListaTransferencias();
      }
    }
  }

    private void lblF9_mouseClicked(MouseEvent e) {
        if(FarmaVariables.vEconoFar_Matriz)
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionTransferencias);
        else {
         if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
          funcionF9();
         }
         else
         {
          FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opci�n",null);
         }
        }
    }

    private void lblF2_mouseClicked(MouseEvent e) {
        funcionF2();
    }
}
