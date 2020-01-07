package venta.inventario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;
import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgRecepcionProductosGuiasPendientes extends JDialog {
	
        private static final Logger log = LoggerFactory.getLogger(DlgRecepcionProductosGuiasPendientes.class);

        Frame myParentFrame;

	FarmaTableModel tableModel;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelWhite jContentPane = new JPanelWhite();

	private JPanelTitle pnlTitle1 = new JPanelTitle();

	private JScrollPane scrListaGuiasPendientes = new JScrollPane();

	private JTable tblListaGuiasPendientes = new JTable();

	private JButtonLabel btnRelacionGuiasPendientes = new JButtonLabel();

	private JLabelFunction lblEsc = new JLabelFunction();

	private JLabelFunction lblF2 = new JLabelFunction();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgRecepcionProductosGuiasPendientes() {
		this(null, "", false);
	}

	public DlgRecepcionProductosGuiasPendientes(Frame parent, String title,
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
		this.setSize(new Dimension(654, 372));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Lista de Gu�as Pendientes de Proceso");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		pnlTitle1.setBounds(new Rectangle(10, 10, 630, 25));
		scrListaGuiasPendientes.setBounds(new Rectangle(10, 35, 630, 260));
		tblListaGuiasPendientes.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblListaGuiasPendientes_keyPressed(e);
			}
		});
		btnRelacionGuiasPendientes.setText("Relaci�n de Gu�as Pendientes de Proceso");
		btnRelacionGuiasPendientes.setBounds(new Rectangle(5, 5, 105, 15));
		btnRelacionGuiasPendientes.setMnemonic('R');
    btnRelacionGuiasPendientes.setText("Relaci�n de gu�as :");
		btnRelacionGuiasPendientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRelacionGuiasPendientes_actionPerformed(e);
			}
		});
		lblEsc.setText("[ ESC ] Cerrar");
		lblEsc.setBounds(new Rectangle(445, 310, 90, 20));
		lblF2.setText("[ F2 ] Ver Gu�a");
		lblF2.setBounds(new Rectangle(15, 310, 150, 20));
		scrListaGuiasPendientes.getViewport()
				.add(tblListaGuiasPendientes, null);
		jContentPane.add(lblF2, null);
		jContentPane.add(lblEsc, null);
		jContentPane.add(scrListaGuiasPendientes, null);
		pnlTitle1.add(btnRelacionGuiasPendientes, null);
		jContentPane.add(pnlTitle1, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
	}

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************

	private void initialize() {
		common.FarmaVariables.vAceptar = false;
		initTable();
	};

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************

	private void initTable() {
		tableModel = new FarmaTableModel(
				ConstantsInventario.columnsListaGuiasPendientes,
				ConstantsInventario.defaultValuesListaGuiasPendientes, 0);
		FarmaUtility.initSimpleList(tblListaGuiasPendientes, tableModel,
				ConstantsInventario.columnsListaGuiasPendientes);
		cargaListaGuiasPendientes();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void tblListaGuiasPendientes_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void btnRelacionGuiasPendientes_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(tblListaGuiasPendientes);
	}

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaGuiasPendientes);
	}

	private void this_windowClosing(WindowEvent e) {
		FarmaUtility.showMessage(this,
				"Debe presionar la tecla ESC para cerrar la ventana.", null);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (venta.reference.UtilityPtoVenta.verificaVK_F2(e)) {
			if (tieneRegistroSeleccionado(tblListaGuiasPendientes)) {
				if (esRegistroHabil()) {
					cargarRegistroSeleccionado();
					/*DlgRecepcionProductosDetalleGuia dlgRecepcionProductosDetalleGuia = new DlgRecepcionProductosDetalleGuia(
							myParentFrame, "", true);
					dlgRecepcionProductosDetalleGuia.setVisible(true);				
					cargaListaGuiasPendientes();		*/
          DlgRecepcionProductosGuias dlgRecepcionProductosGuias = new DlgRecepcionProductosGuias(myParentFrame, "", true);
          dlgRecepcionProductosGuias.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            cargaListaGuiasPendientes();  
          }
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
		}
	}

	// **************************************************************************
	// Metodos de l�gica de negocio
	// **************************************************************************

	private void cargaListaGuiasPendientes() {
		try {
			DBInventario.getListaGuiasRecep(tableModel);
			if (tblListaGuiasPendientes.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaGuiasPendientes, tableModel, 0,
						FarmaConstants.ORDEN_ASCENDENTE);
			
		} catch (SQLException sql) {
      log.error("",sql);
			log.debug("Ocurri� un error al cargar las gu�as : \n" + sql.getMessage());
		}
	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void cargarRegistroSeleccionado() {
		VariablesInventario.vNumNotaEs = tblListaGuiasPendientes.getValueAt(
				tblListaGuiasPendientes.getSelectedRow(), 0).toString().trim();
		VariablesInventario.vFecCreaNota = tblListaGuiasPendientes.getValueAt(
				tblListaGuiasPendientes.getSelectedRow(), 1).toString().trim();
		VariablesInventario.vEstRecepcion = tblListaGuiasPendientes.getValueAt(
				tblListaGuiasPendientes.getSelectedRow(), 5).toString().trim();
	}

	private boolean esRegistroHabil() {
		boolean rpta = true;
		String est = "";
		est = tblListaGuiasPendientes.getValueAt(
				tblListaGuiasPendientes.getSelectedRow(), 6).toString().trim();
		if (est.equals("C")) {
			rpta = false;
			FarmaUtility.showMessage(this, "La gu�a se encuentra cerrada",
					tblListaGuiasPendientes);
		}
		if (est.equals("N")) {
			rpta = false;
			FarmaUtility.showMessage(this, "La gu�a se encuentra anulada",
					tblListaGuiasPendientes);
		}
		return rpta;
	}
}