package venta.recepcionCiega;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;

import venta.reportes.DlgOrdenar;
import venta.reportes.reference.*;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;
import javax.swing.JComboBox;

import venta.recepcionCiega.reference.*;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgDetalleGuia.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JCORTEZ 16.11.2009 Creaci�n<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgDetalleGuia extends JDialog {
	Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleGuia.class);

	FarmaTableModel tableModel;
    private JTable myJTable;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelWhite jContentPane = new JPanelWhite();

	private JPanelHeader pnlHeader1 = new JPanelHeader();

	private JLabelWhite lblGuia_T = new JLabelWhite();

	private JLabelWhite lblFecha_T = new JLabelWhite();

	private JLabelWhite lblGuia = new JLabelWhite();

	private JLabelWhite lblFecha = new JLabelWhite();

	private JLabelWhite lblProductos_T = new JLabelWhite();

	private JLabelWhite lblItems_T = new JLabelWhite();

	private JLabelWhite lblItems = new JLabelWhite();

	private JLabelWhite lblProductos = new JLabelWhite();

	private JLabelWhite lblStock_T = new JLabelWhite();

	private JLabelWhite lblEstado_T = new JLabelWhite();

	private JLabelWhite lblEstado = new JLabelWhite();

	private JLabelWhite lblStock = new JLabelWhite();

	private JButtonLabel btnBuscar = new JButtonLabel();

	private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();

	private JPanelTitle pnlTitle1 = new JPanelTitle();

	private JScrollPane scrListaProductos = new JScrollPane();

	private JLabelFunction lblEsc = new JLabelFunction();


    private JButtonLabel btnRelacionProductos = new JButtonLabel();

	private JTable tblListaProductos = new JTable();

    // **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgDetalleGuia() {
		this(null, "", false);
	}

	public DlgDetalleGuia(Frame parent, String title,
			boolean modal) {
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
			FarmaUtility.centrarVentana(this);
		} catch (Exception e) {
			log.error("",e);
		}
	}

	// **************************************************************************
	// M�todo "jbInit()"
	// **************************************************************************

	private void jbInit() throws Exception {
		this.setSize(new Dimension(652, 392));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Detalle de Gu�a");
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		pnlHeader1.setBounds(new Rectangle(5, 10, 635, 75));
		lblGuia_T.setText("Gu�a:");
		lblGuia_T.setBounds(new Rectangle(10, 5, 40, 15));
		lblFecha_T.setText("Fecha:");
		lblFecha_T.setBounds(new Rectangle(10, 25, 45, 15));
		lblGuia.setText("000001");
		lblGuia.setBounds(new Rectangle(55, 5, 105, 15));
		lblGuia.setFont(new Font("SansSerif", 0, 11));
		lblFecha.setText("09/01/2006 00:00:00");
		lblFecha.setBounds(new Rectangle(55, 25, 110, 15));
		lblFecha.setFont(new Font("SansSerif", 0, 11));
		lblProductos_T.setText("Productos:");
		lblProductos_T.setBounds(new Rectangle(175, 25, 65, 15));
		lblItems_T.setText("Items:");
		lblItems_T.setBounds(new Rectangle(175, 5, 60, 15));
		lblItems.setText("1");
		lblItems.setBounds(new Rectangle(225, 5, 70, 15));
		lblItems.setFont(new Font("SansSerif", 0, 11));
		lblProductos.setText("0");
		lblProductos.setBounds(new Rectangle(240, 25, 70, 15));
		lblProductos.setFont(new Font("SansSerif", 0, 11));
		lblStock_T.setText("Stock:");
		lblStock_T.setBounds(new Rectangle(325, 5, 45, 15));
		lblEstado_T.setText("Estado:");
		lblEstado_T.setBounds(new Rectangle(325, 25, 50, 15));
		lblEstado.setText("Pendiente");
		lblEstado.setBounds(new Rectangle(370, 25, 70, 15));
		lblEstado.setFont(new Font("SansSerif", 0, 11));
		lblStock.setText("Afecta Stock");
		lblStock.setBounds(new Rectangle(370, 5, 70, 15));
		lblStock.setFont(new Font("SansSerif", 0, 11));
		btnBuscar.setText("Buscar :");
		btnBuscar.setBounds(new Rectangle(10, 45, 55, 15));
		btnBuscar.setMnemonic('B');
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscar_actionPerformed(e);
			}
		});
		txtBuscar.setBounds(new Rectangle(65, 45, 295, 20));
		txtBuscar.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				txtBuscar_keyPressed(e);
			}
			public void keyReleased(KeyEvent e) {
				txtBuscar_keyReleased(e);
			}
		});
		pnlTitle1.setBounds(new Rectangle(5, 90, 635, 25));
		scrListaProductos.setBounds(new Rectangle(5, 115, 635, 220));
		lblEsc.setText("[ ESC ] Cerrar");
		lblEsc.setBounds(new Rectangle(555, 340, 85, 20));
        btnRelacionProductos.setText("Relaci�n de Productos Seleccionados");
		btnRelacionProductos.setBounds(new Rectangle(5, 5, 215, 15));
        pnlHeader1.add(txtBuscar, null);
		pnlHeader1.add(btnBuscar, null);
		pnlHeader1.add(lblStock, null);
		pnlHeader1.add(lblEstado, null);
		pnlHeader1.add(lblEstado_T, null);
		pnlHeader1.add(lblStock_T, null);
		pnlHeader1.add(lblProductos, null);
		pnlHeader1.add(lblItems, null);
		pnlHeader1.add(lblItems_T, null);
		pnlHeader1.add(lblProductos_T, null);
		pnlHeader1.add(lblFecha, null);
		pnlHeader1.add(lblGuia, null);
		pnlHeader1.add(lblFecha_T, null);
		pnlHeader1.add(lblGuia_T, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaProductos, null);
		pnlTitle1.add(btnRelacionProductos, null);
		jContentPane.add(pnlTitle1, null);
		jContentPane.add(pnlHeader1, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************

	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTable();
	    setJTable(tblListaProductos);
	}

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************

	private void initTable() {
		tableModel = new FarmaTableModel(
				ConstantsRecepCiega.columnsListaDetalleGuia,
				ConstantsRecepCiega.defaultValuesListaDetalleGuia, 0);
		FarmaUtility.initSimpleList(tblListaProductos, tableModel,
				ConstantsRecepCiega.columnsListaDetalleGuia);
	          tblListaProductos.getTableHeader().setReorderingAllowed(false);
	          tblListaProductos.getTableHeader().setResizingAllowed(false);
		cargaListaProductos();
                
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void btnBuscar_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(txtBuscar);
	}

	private void txtBuscar_keyPressed(KeyEvent e) {
		FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar,
				1);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			if (tblListaProductos.getSelectedRow() >= 0) {
				if (!(FarmaUtility.findTextInJTable(tblListaProductos,
						txtBuscar.getText().trim(), 0, 1))) {
					FarmaUtility
							.showMessage(
									this,
									"Producto No Encontrado seg�n Criterio de B�squeda !!!",
									txtBuscar);
					return;
				} 
			}
		}
		chkKeyPressed(e);
	}

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(txtBuscar);
		mostrarDatos();
	}

	private void txtBuscar_keyReleased(KeyEvent e) {
		FarmaUtility.ponerMayuscula(e, txtBuscar);
		FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
	}

	private void this_windowClosing(WindowEvent e) {
		FarmaUtility.showMessage(this,
				"Debe presionar la tecla ESC para cerrar la ventana.", null);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
  
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F2(e))
    { 
    }
    else if (e.getKeyCode() == KeyEvent.VK_F9)
    {
      }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
			//this.setVisible(false);
			 cerrarVentana(false);
		}
	}

    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************
     private void setJTable(JTable pJTable) {
       myJTable = pJTable;
       txtBuscar.setText("");
       if(pJTable.getRowCount() > 0){
         FarmaGridUtils.showCell(pJTable, 0, 0);
         FarmaUtility.setearActualRegistro(pJTable, txtBuscar, 2);
       }
       FarmaUtility.moveFocus(txtBuscar);
     }
    private void cargaListaProductos() {
            try {                
            log.debug("VariablesRecepCiega.vNumNotaEst "+VariablesRecepCiega.vNumNotaEst);
            log.debug("VariablesRecepCiega.vNumGuia "+VariablesRecepCiega.vNumGuia);
            DBRecepCiega.getListaDetGuias(tableModel,VariablesRecepCiega.vNumNotaEst,VariablesRecepCiega.vNumGuia);
            /* if (tblListaProductos.getRowCount() > 0)
            {
                FarmaUtility.ordenar(tblListaProductos, tableModel, 12,
                FarmaConstants.ORDEN_ASCENDENTE);
                verificaGuiaCerrada();
            }*/
    } catch (SQLException sql) {
              log.error("",sql);
              FarmaUtility.showMessage(this,"Ocurri� un error al cargar el detalle de la guia : \n",txtBuscar);;    
            }
    }

    private void mostrarDatos() {
        lblGuia.setText(VariablesRecepCiega.vNumGuia.trim());
        lblItems.setText("" + tblListaProductos.getRowCount());
        //lblProductos.setText(VariablesRecepCiega.vCantProds);
         if(tblListaProductos.getRowCount()>0)
         lblProductos.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaProductos,4), 0));
         
        lblFecha.setText(VariablesRecepCiega.vFecCreaNota);
        lblEstado.setText(VariablesRecepCiega.vEstado);
    }

  private void cargarRegistroSeleccionado()
  {
		VariablesInventario.vSelectedRow = tblListaProductos.getSelectedRow();
    VariablesInventario.vCodProd = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     0).toString().trim();
    VariablesInventario.vDescProd = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     1).toString().trim();
    VariablesInventario.vDescUnidPresent = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     2).toString().trim();
    VariablesInventario.vNomLab = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     3).toString().trim();
    VariablesInventario.vCantGuia = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     4).toString().trim();
    VariablesInventario.vValorFrac = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     9).toString().trim();
    VariablesInventario.vStkFis = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     10).toString().trim();
    VariablesInventario.vSecDetNota = 
        tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 
                                     11).toString().trim();

	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void setearValores() {
    try
    {
      DBInventario.setearValores();
      FarmaUtility.aceptarTransaccion();
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al actualizar el registro.\n"+ sql.getMessage(),txtBuscar);
    }
	}

    private boolean esRegistroHabil() {
            boolean rpta = true;
            String est = "";
            est = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),
                            7).toString().trim();
            if (est.equals("S")) {
                    rpta = false;
                    FarmaUtility.showMessage(this,
                                    "El producto ya se encuentra afectado", tblListaProductos);
            }
            return rpta;
    }

    private void cerrarVentana(boolean pAceptar) {
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
    }

  private void actualizarRegUnico() throws SQLException
  {
		cargarRegistroSeleccionado();
		DBInventario.actualizaRegistroUnico();
  }
    
  private void muestraVentaOrdenar()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    String[] IND_CAMPO = {"12", "1"};
    String[] IND_DESCRIP_CAMPO = {"Pagina","Descripcion"};
    log.debug("Campo " + IND_DESCRIP_CAMPO[1] );
    VariablesReporte.vNombreInHashtable = ConstantsInventario.HASHTABLE_ORDENAR_RECEPCION ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesReporte.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblListaProductos,tableModel,VariablesReporte.vCampo,VariablesReporte.vOrden);
      tblListaProductos.repaint();
    }    
  }
    
  private void verificaGuiaCerrada()
  {
    boolean verifica = true;
    for(int i =0;i<tblListaProductos.getRowCount();i++)
    {
      if(tblListaProductos.getValueAt(i,7).toString().trim().equals("N"))
      {
        verifica = false;
        break;
      }
    }
    if(verifica)
    {
      FarmaUtility.showMessage(this,"Gu�a Cerrada",txtBuscar);
      cerrarVentana(true);
    }
  }
}