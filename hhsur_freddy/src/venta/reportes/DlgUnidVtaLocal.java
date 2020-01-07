package venta.reportes;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Frame;

import java.sql.SQLException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import venta.DlgFiltroProductos;
import venta.reference.*;
import venta.reportes.reference.*;
import common.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgUnidVtaLocal.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      02.05.2007   Creaci�n<br>
 * <br>
 * @author  Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class DlgUnidVtaLocal extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgUnidVtaLocal.class);  

  private Frame myParentFrame;
  private FarmaTableModel tableModelListado;
  private BorderLayout borderLayout1 = new BorderLayout();

  private JPanelWhite pnlBlanco = new JPanelWhite();
  private JPanelTitle pnlTitle = new JPanelTitle();
  private JButtonLabel btnListado = new JButtonLabel();
  private JScrollPane scrListado = new JScrollPane();
  private JTable tblListado = new JTable();
  private JPanelTitle pnlTitle2 = new JPanelTitle();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JButtonLabel btnBuscar = new JButtonLabel();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF7 = new JLabelFunction();
  private JLabelWhite lblTotalRegistros = new JLabelWhite();
  private JLabelWhite lblTotalRegistros_T = new JLabelWhite();
  private JLabelWhite lblFiltro = new JLabelWhite();
  private JLabelFunction lblF6 = new JLabelFunction();
  
  public DlgUnidVtaLocal()
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

  public DlgUnidVtaLocal(Frame parent, String title, boolean modal) {
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

  // **************************************************************************
  // M�todo "jbInit()"
  // **************************************************************************

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(686, 357));
    this.setTitle("Unidad Venta Local");
    this.getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
      
      public void windowClosing(WindowEvent e)
      {
        this_windowClosing(e);
      }
		});
    pnlTitle.setBounds(new Rectangle(10, 65, 660, 20));
    btnListado.setText("Listado");
    btnListado.setBounds(new Rectangle(15, 0, 90, 20));
    
    scrListado.setBounds(new Rectangle(10, 85, 660, 190));
    scrListado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlTitle2.setBounds(new Rectangle(10, 275, 660, 20));
    jLabelFunction1.setBounds(new Rectangle(575, 305, 95, 20));
    jLabelFunction1.setText("[ ESC ] Cerrar");
    pnlHeader1.setBounds(new Rectangle(10, 10, 660, 40));
    txtBuscar.setBounds(new Rectangle(90, 10, 355, 20));
    txtBuscar.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtBuscar_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
          txtBuscar_keyReleased(e);
        }
      });
    btnBuscar.setText("Buscar por:");
    btnBuscar.setBounds(new Rectangle(15, 10, 70, 20));
    btnBuscar.setMnemonic('B');
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnBuscar_actionPerformed(e);
        }
      });
    lblF5.setBounds(new Rectangle(10, 305, 145, 20));
    lblF5.setText("[ F5 ] Filtrar Productos");
    lblF7.setBounds(new Rectangle(300, 305, 105, 20));
    lblF7.setText("[ F7 ] Ordenar");
    lblTotalRegistros.setText("100");
    lblTotalRegistros.setBounds(new Rectangle(555, 0, 70, 20));
    lblTotalRegistros_T.setText("Total de Registros:");
    lblTotalRegistros_T.setBounds(new Rectangle(440, 0, 115, 20));
    lblFiltro.setText("Todos los Laboratorios");
    lblFiltro.setBounds(new Rectangle(125, 0, 395, 20));
    lblF6.setText("[ F6 ] Eliminar Filtro");
    lblF6.setBounds(new Rectangle(160, 305, 135, 20));
    pnlHeader1.add(txtBuscar, null);
    pnlHeader1.add(btnBuscar, null);
    pnlBlanco.add(lblF6, null);
    pnlBlanco.add(lblF7, null);
    pnlBlanco.add(lblF5, null);
    pnlBlanco.add(pnlHeader1, null);
    pnlBlanco.add(jLabelFunction1, null);
    pnlBlanco.add(pnlTitle2, null);
    pnlTitle2.add(lblTotalRegistros_T, null);
    pnlTitle2.add(lblTotalRegistros, null);
    scrListado.getViewport().add(tblListado, null);
    pnlBlanco.add(scrListado, null);
    pnlTitle.add(lblFiltro, null);
    pnlTitle.add(btnListado, null);
    pnlBlanco.add(pnlTitle, null);
    this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
  }
  
  // **************************************************************************
  // M�todo "initialize()"
  // **************************************************************************

  private void initialize()
  {
    initTable();
    cargaListaProductos();
  }

  // **************************************************************************
  // M�todos de inicializaci�n
  // **************************************************************************
  
  private void initTable()
  {
    tableModelListado = new FarmaTableModel(ConstantsReporte.columnsListaUnidVtaLocal,ConstantsReporte.defaultValuesListaUnidVtaLocal,0);
    FarmaUtility.initSimpleList(tblListado,tableModelListado,ConstantsReporte.columnsListaUnidVtaLocal);
  }
  
	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************

  private void this_windowOpened(WindowEvent e)
  {
		FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtBuscar);
	}
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void txtBuscar_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblListado, txtBuscar,1);
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
			e.consume();
			if (tblListado.getSelectedRow() >= 0)
      {
				if (!(FarmaUtility.findTextInJTable(tblListado,txtBuscar.getText().trim(), 0, 0)))
        {
					FarmaUtility.showMessage(this,"Producto No Encontrado seg�n Criterio de B�squeda !!!",txtBuscar);
					return;
				}
			}
		}
    else if (e.getKeyCode() == KeyEvent.VK_F5)
    {
      cargaListaFiltro();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F6)
    {
      if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
				VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
				tableModelListado.clearTable();
				cargaListaProductos();
				FarmaUtility.moveFocus(txtBuscar);
			}
    }
    else if (e.getKeyCode() == KeyEvent.VK_F7)
    {
      muestraOrdenar();
    }
		else
      chkKeyPressed(e);
  }

  private void txtBuscar_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, tblListado, txtBuscar, 1);
  }

  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
  }
  
  // **************************************************************************
  // Metodos auxiliares de eventos
  // **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  // **************************************************************************
  // Metodos de l�gica de negocio
  // **************************************************************************

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  private void cargaListaProductos()
  {
    try 
    {
			DBReportes.cargaListaUnidadVtaLocal(tableModelListado);
			if (tblListado.getRowCount() > 0)
				FarmaUtility.ordenar(tblListado, tableModelListado, 1,FarmaConstants.ORDEN_ASCENDENTE);
			log.debug("Se carg� la lista de prods");
		}
    catch (SQLException sql)
    {
			FarmaUtility.showMessage(this,"Ocurri� un error al cargar la lista de productos : \n" + sql.getMessage(),txtBuscar);
		}
		lblTotalRegistros.setText("" + tblListado.getRowCount());
		lblFiltro.setText("Todos los productos");
  }
  
  private void cargaListaFiltro()
  {
		DlgFiltroProductos dlgFiltroProductos = new DlgFiltroProductos(myParentFrame, "", true);
		dlgFiltroProductos.setVisible(true);
		
    if (FarmaVariables.vAceptar)
    {
			tableModelListado.clearTable();
			txtBuscar.setText("");
			filtrarTablaProductos();
			FarmaVariables.vAceptar = false;
			VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
		}
	}
  
	private void filtrarTablaProductos()
  {
		try
    {
			tableModelListado.clearTable();
			DBReportes.cargaListaUnidadVtaLocalXFiltro(tableModelListado);
			FarmaUtility.ordenar(tblListado, tableModelListado, 2,FarmaConstants.ORDEN_ASCENDENTE);
		}
    catch (SQLException sql)
    {
			log.error("",sql);
			FarmaUtility.showMessage(this,"Error al filtrar la lista de productos : \n" + sql.getMessage(),txtBuscar);
		}
		lblTotalRegistros.setText("" + tblListado.getRowCount());
		mostrarDatosFiltro();
	}
  
	private void mostrarDatosFiltro()
  {
		lblFiltro.setText("Fitro: " + VariablesPtoVenta.vDesc_Cat_Filtro+ " : " + VariablesPtoVenta.vDescFiltro);
	}
  
  private void muestraOrdenar()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    String [] IND_DESCRIP_CAMPO = {"C�digo", "Descripci�n","Laboratorio","Stock"};
    String [] IND_CAMPO = {"0","1","3","4"};
    VariablesReporte.vNombreInHashtable =   ConstantsReporte.VNOMBREINHASHTABLEUNIDVTALOCAL ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesReporte.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblListado,tableModelListado,VariablesReporte.vCampo,VariablesReporte.vOrden);
      tblListado.repaint();
    }
  }
}