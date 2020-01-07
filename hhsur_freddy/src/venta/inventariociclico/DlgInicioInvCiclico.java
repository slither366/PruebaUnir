package venta.inventariociclico;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;
import venta.reference.ConstantsPtoVenta;


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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import venta.inventariociclico.reference.ConstantsInvCiclico;
import venta.inventariociclico.reference.DBInvCiclico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgInicioInvCiclico.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      23.10.2006   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgInicioInvCiclico
  extends JDialog
{
  /* ************************************************************************ */
  /*                        DECLARACION PROPIEDADES                           */
  /* ************************************************************************ */
  FarmaTableModel tableModelProductosToma;
  Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgInicioInvCiclico.class);

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrPane1 = new JScrollPane();
  private JTable tblRelacionProductos = new JTable();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
  private JButtonLabel btnProductos = new JButtonLabel();
  private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelWhite lblItems_T = new JLabelWhite();
  private JLabelWhite lblItems = new JLabelWhite();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF12 = new JLabelFunction();

  /* ************************************************************************ */
  /*                          CONSTRUCTORES                                   */
  /* ************************************************************************ */

  public DlgInicioInvCiclico()
  {
    this(null, "", false);
  }

  public DlgInicioInvCiclico(Frame parent, String title, boolean modal)
  {
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

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(786, 416));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Productos Inventario Ciclico");
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }
        });
    pnlHeader1.setBounds(new Rectangle(5, 10, 770, 50));
    pnlHeader1.setLayout(null);
    pnlTitle1.setBounds(new Rectangle(5, 65, 770, 25));
    pnlTitle1.setLayout(null);
    scrPane1.setBounds(new Rectangle(5, 90, 770, 260));
    btnRelacionProductos.setText("Relacion de Productos");
    btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 25));
    btnProductos.setText("Productos :");
    btnProductos.setMnemonic('p');
    btnProductos.setBounds(new Rectangle(30, 15, 65, 20));
    btnProductos.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnProductos_actionPerformed(e);
          }
        });
    txtProductos.setBounds(new Rectangle(115, 15, 315, 20));
    txtProductos.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtProductos_keyPressed(e);
          }

          public void keyReleased(KeyEvent e)
          {
            txtProductos_keyReleased(e);
          }
        });
    lblF3.setBounds(new Rectangle(140, 360, 110, 20));
    lblF3.setText("[ F3 ] Insertar");
    lblEsc.setBounds(new Rectangle(665, 360, 105, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF5.setBounds(new Rectangle(260, 360, 120, 20));
    lblF5.setText("[ F5 ] Borrar");
    lblF11.setBounds(new Rectangle(390, 360, 117, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblItems_T.setText("Items:");
    lblItems_T.setBounds(new Rectangle(655, 0, 50, 25));
    lblItems.setText("1000");
    lblItems.setBounds(new Rectangle(695, 0, 35, 25));
    lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
    lblF1.setBounds(new Rectangle(10, 360, 117, 20));
    lblF1.setText("[ F1 ] Inicializar");
    lblF12.setBounds(new Rectangle(535, 360, 120, 20));
    lblF12.setText("[ F12 ] Imprimir");
    jContentPane.add(lblF1, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblF5, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(lblF3, null);
    scrPane1.getViewport().add(tblRelacionProductos, null);
    jContentPane.add(scrPane1, null);
    pnlTitle1.add(lblItems, null);
    pnlTitle1.add(lblItems_T, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
    pnlHeader1.add(txtProductos, null);
    pnlHeader1.add(btnProductos, null);
    jContentPane.add(pnlHeader1, null);
    jContentPane.add(lblF12, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTableListaProductos()
  {
    tableModelProductosToma = 
        new FarmaTableModel(ConstantsInvCiclico.columnsListaProductosInicio, 
                            ConstantsInvCiclico.defaultValuesListaProductosInicio, 
                            0);
    FarmaUtility.initSimpleList(tblRelacionProductos, 
                                tableModelProductosToma, 
                                ConstantsInvCiclico.columnsListaProductosInicio);
    cargaListaProductosInicio();
  }

  private void cargaListaProductosInicio()
  {
    try
    {
      DBInvCiclico.getListaProdsInicio(tableModelProductosToma);
      if (tblRelacionProductos.getRowCount() > 0)
      {
        FarmaUtility.ordenar(tblRelacionProductos, tableModelProductosToma, 2, FarmaConstants.ORDEN_ASCENDENTE);
      }
      lblItems.setText(tblRelacionProductos.getRowCount() + "");
    }
    catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, 
                               "Ocurri� un error al obtener la lista de productos : \n" + 
                               sql.getMessage(), txtProductos);
    }
  }
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtProductos);
    inicializarInvCiclico();
    initTableListaProductos();
  }

  private void btnProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtProductos);
  }

  private void txtProductos_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductos, 
                                          txtProductos, 1);


    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {


      e.consume();
      if (tblRelacionProductos.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblRelacionProductos, 
                                            txtProductos.getText().trim(), 
                                            0, 1)))
        {
          FarmaUtility.showMessage(this, 
                                   "Producto No Encontrado seg�n Criterio de B�squeda !!!", 
                                   txtProductos);
          return;
        }
      }
    }

    chkKeyPressed(e);
  }

  private void txtProductos_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, tblRelacionProductos, txtProductos, 
                                     1);
  }

  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
      else{
        inicializarInvCiclico();
        cargaListaProductosInicio();
      }
    }
    else if (e.getKeyCode() == KeyEvent.VK_F3)
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
      else    
        insertar();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
      else    
        borrarProducto();
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
      else    
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA))  
        {
          aceptar();  
        } else 
        {
          FarmaUtility.showMessage(this,"El usuario no tiene el rol para iniciar una toma de inventario c�clico",txtProductos);
        }      
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F12(e))
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtProductos);
      else    
        imprimir();
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

  /**
   * Inicializa los productos para un nuevo inventario ciclico.
   */
  private void inicializarInvCiclico()
  {
    try
    {
      DBInvCiclico.initInventarioCiclico();
      FarmaUtility.aceptarTransaccion();
    }
    catch (SQLException e)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",e);
      FarmaUtility.showMessage(this, 
                               "Ha ocurrido un error al inicializar el inventario.\n" + 
                               e.getMessage(), txtProductos);
    }
  }

  /**
   * Inserta nuevos productos a la toma.
   */
  private void insertar()
  {
    DlgProductosInvCiclico dlgProductosInvCiclico = 
      new DlgProductosInvCiclico(myParentFrame, "", true);
    dlgProductosInvCiclico.setVisible(true);
    if (FarmaVariables.vAceptar)
    {
      cargaListaProductosInicio();
      FarmaVariables.vAceptar = false;
    }
  }

  /**
   * Borra el producto seleccionado de la toma de inventario ciclico.
   */
  private void borrarProducto()
  {
    String codProd;
    int row = tblRelacionProductos.getSelectedRow();
    if (row > -1)
    {
      if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                         "�Est� seguro de borrar el producto?"))
        try
        {
          codProd = 
              tblRelacionProductos.getValueAt(row, 0).toString().trim();
          DBInvCiclico.borrarProducto(codProd);
          FarmaUtility.aceptarTransaccion();
          cargaListaProductosInicio();
        }
        catch (SQLException e)
        {
          FarmaUtility.liberarTransaccion();
          log.error("",e);
          FarmaUtility.showMessage(this, 
                                   "Error al borrar producto.\n" + e.getMessage(), 
                                   txtProductos);
        }

    }
  }

  /**
   * Grabar nuevo inventario ciclico.
   */
  private void aceptar()
  {
    if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                       "�Est� seguro de grabar el inventario c�clico?"))
      try
      {
        DBInvCiclico.grabarInventarioCiclico();
        FarmaUtility.aceptarTransaccion();
        FarmaUtility.showMessage(this, "El inventario se grab� correctamente.", 
                                 txtProductos);
        cerrarVentana(true);
      }
      catch (SQLException e)
      {
        FarmaUtility.liberarTransaccion();
        log.error("",e);
        FarmaUtility.showMessage(this, 
                                 "Ha ocurrido un error al grabar la toma.\n" + 
                                 e.getMessage(), txtProductos);
      }
  }

    private void imprimir() {
		if (!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
			return;
		 FarmaPrintService vPrint = new FarmaPrintService(66,
				FarmaVariables.vImprReporte, true);
		if (!vPrint.startPrintService()) {
			FarmaUtility.showMessage(this,
					"No se pudo inicializar el proceso de impresi�n",
					txtProductos);
			return;
		}
		try {
			String fechaActual = FarmaSearch
					.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
			vPrint.setStartHeader();
			vPrint.activateCondensed();
			vPrint.printBold(FarmaPRNUtility.llenarBlancos(27)
					+ " REPORTE DE PRODUCTOS PARA INVENTARIO CICLICO", true);
      vPrint.printBold("Nombre Compa�ia : " + FarmaVariables.vNomCia.trim(), true);            
      vPrint.printBold("Nombre Local : " + FarmaVariables.vCodLocal + " - "+ FarmaVariables.vDescLocal.trim(), true);            
      vPrint.printBold("Fecha Actual    : " + fechaActual, true);
          
		
			vPrint
					.printLine(
							"======================================================================================================================================",
							true);
			vPrint
					.printBold(
							"Codigo  Descripcion                               Laboratorio                   Unid Presentacion Unidad Venta   Stock      Stk Fisico",
							true);
			vPrint
					.printLine(
							"======================================================================================================================================",
							true);
			vPrint.deactivateCondensed();
			vPrint.setEndHeader();
			for (int i = 0; i < tblRelacionProductos.getRowCount(); i++) {

				vPrint.printCondensed(
        FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 0), 8)+ 
        FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 1), 42)+ 
        FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 2), 29)+ " " + 
        FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 3), 18)+ 
        FarmaPRNUtility.alinearIzquierda((String) tblRelacionProductos.getValueAt(i, 4), 15)+ 
        FarmaPRNUtility.alinearDerecha((String) tblRelacionProductos.getValueAt(i, 5), 10)+
        FarmaPRNUtility.alinearIzquierda(" __________", 11),true);
			}
			vPrint.activateCondensed();
			vPrint.printLine("==============================================================================================================================",true);
			vPrint.printBold("Registros Impresos: " + FarmaUtility.formatNumber(tblRelacionProductos.getRowCount(),",##0") + FarmaPRNUtility.llenarBlancos(11), true);
      vPrint.printLine(" ",true);
      vPrint.printLine(" ",true);
      vPrint.printLine(" ",true);
      vPrint.printLine(FarmaPRNUtility.llenarBlancos(33)+"__________________________"+FarmaPRNUtility.llenarBlancos(10)+"_________________________",true);
      vPrint.printLine(FarmaPRNUtility.llenarBlancos(33)+"  Nombre y Firma del QF"+FarmaPRNUtility.llenarBlancos(10)+"   Nombre y Firma del Auditor",true);        
			vPrint.deactivateCondensed();
			vPrint.endPrintService();
		} catch (Exception sqlerr) {
			log.error("",sqlerr);
      FarmaUtility.showMessage(this,"Ocurri� un error al imprimir : \n" + sqlerr.getMessage(),txtProductos);
		}
	}
  
}
