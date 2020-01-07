package venta.ce;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.ce.reference.*;
import venta.caja.reference.*;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgCuadraturas.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      31.07.2006   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgCuadraturas extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgCuadraturas.class);
  
  Frame myParentFrame;
  FarmaTableModel tableModel; 
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF10 = new JLabelFunction();
  private JScrollPane scrLista = new JScrollPane();
  private JTable tblLista = new JTable();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnLista = new JButtonLabel();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */
  
  public DlgCuadraturas()
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

  public DlgCuadraturas(Frame parent, String title, boolean modal)
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
    this.setTitle("Cuadraturas");
    this.setSize(new Dimension(491, 298));
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
    lblEsc.setBounds(new Rectangle(340, 245, 95, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF10.setBounds(new Rectangle(10, 245, 130, 20));
    lblF10.setText("[ Enter ] Ver Detalle");
    scrLista.setBounds(new Rectangle(10, 30, 450, 205));
    tblLista.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            tblLista_keyPressed(e);
          }
        });
    pnlTitle1.setBounds(new Rectangle(10, 10, 450, 20));
    btnLista.setText("Lista Cuadraturas");
    btnLista.setBounds(new Rectangle(5, 0, 105, 20));
    btnLista.setMnemonic('L');
    btnLista.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnLista_actionPerformed(e);
          }
        });
    scrLista.getViewport().add(tblLista, null);
    pnlTitle1.add(btnLista, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(scrLista, null);
    jContentPane.add(lblF10, null);
    jContentPane.add(lblEsc, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    initTable();
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */
  
  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsCajaElectronica.columnsListaCuadraturas,ConstantsCajaElectronica.defaultValuesListaCuadraturas,0);
    FarmaUtility.initSimpleList(tblLista,tableModel,ConstantsCajaElectronica.columnsListaCuadraturas);
    cargaLista();
  }
  
  private void cargaLista()
  {
    try
    {
      DBCajaElectronica.listaCuadraturas(tableModel,VariablesCajaElectronica.vSecMovCaja);
      FarmaUtility.ordenar(tblLista,tableModel,0,FarmaConstants.ORDEN_ASCENDENTE);
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,"",btnLista);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */
	
	private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(tblLista);
    FarmaUtility.centrarVentana(this);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  private void btnLista_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblLista);
  }

  private void tblLista_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */
  
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      //UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
      //if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
        //funcion();
        verDetalleCuadratura();
      //else
      //FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblLista);
    } else if (UtilityPtoVenta.verificaVK_F2(e))
    {
      //verDetalleCuadratura();
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
 private void funcion()
  {
    int row = tblLista.getSelectedRow();
    if(row > -1)
    {
      String tipCuadratura = tblLista.getValueAt(row,3).toString().trim();
      if(tipCuadratura.equals("01"))
      {
        VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row,0).toString().trim();
        VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row,1).toString().trim();
        DlgDatosCuadratura dlgDatosCuadratura = new DlgDatosCuadratura(myParentFrame,"",true);
        dlgDatosCuadratura.setVisible(true);
        cargaLista();
      }//Paulo
      else if(tipCuadratura.equals("02"))
      {
        VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row,0).toString().trim();
        VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row,1).toString().trim();
        DlgListaCuadratura dlgListaCuadratura = new DlgListaCuadratura(myParentFrame,"",true);
        dlgListaCuadratura.setVisible(true);
        cargaLista();
      }//paulo
    }
  }
  
  private void verDetalleCuadratura()
  {
    int row  = tblLista.getSelectedRow();
      if (row > -1)
      {
        VariablesCajaElectronica.vTipCuadratura = tblLista.getValueAt(row,3).toString().trim();
        VariablesCajaElectronica.vCodCuadratura = tblLista.getValueAt(row,0).toString().trim();
        VariablesCajaElectronica.vDescCuadratura = tblLista.getValueAt(row,1).toString().trim();
        DlgListaEliminacionCuadratura dlgListaEliminacionCuadratura = new DlgListaEliminacionCuadratura(myParentFrame,"",true);
        dlgListaEliminacionCuadratura.setVisible(true);
      cargaLista();
      }
  }
   
}
