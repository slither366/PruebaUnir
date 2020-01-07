package venta.administracion.cajas;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.administracion.cajas.reference.DBCajas;
import venta.administracion.cajas.reference.VariablesCajas;
import venta.reference.ConstantsPtoVenta;
import venta.reference.VariablesPtoVenta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgListaMaestros.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA 26.01.2006 Creaci�n<br>
 * <br>
 *
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgListaUsuariosCaja extends JDialog {
	
        private static final Logger log = LoggerFactory.getLogger(DlgListaUsuariosCaja.class);  
          
        private Frame myParentFrame;

	private FarmaTableModel tableModel;

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jContentPane = new JPanel();

	private JLabelFunction lblEnter = new JLabelFunction();

	private JScrollPane scrListaUsuarios = new JScrollPane();

	private JPanel pnlRelacionFiltros = new JPanel();

	private JPanel pnlIngresarProductos = new JPanel();

	private JTextField txtDescripcion = new JTextField();

	private JButton btnDescripcion = new JButton();

	private JTable tblMaestro = new JTable();

	private JLabelFunction lblEsc = new JLabelFunction();

	private JButtonLabel btnRelacionUsuarios = new JButtonLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************
	public DlgListaUsuariosCaja() {
		this(null, "", false);
	}

	public DlgListaUsuariosCaja(Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		myParentFrame = parent;
		try {
			jbInit();
			initialize();
		} catch (Exception e) {
		    log.error("",e);
		}

	}

	// **************************************************************************
	// M�todo "jbInit()"
	// **************************************************************************
	private void jbInit() throws Exception {
		this.setSize(new Dimension(447, 366));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Lista de Usuarios");
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
		});
		jContentPane.setBackground(Color.white);
		jContentPane.setLayout(null);
		jContentPane.setSize(new Dimension(623, 335));
		jContentPane.setForeground(Color.white);
		lblEnter.setText("[ ENTER ] Seleccionar");
		lblEnter.setBounds(new Rectangle(200, 310, 130, 20));
		scrListaUsuarios.setBounds(new Rectangle(15, 80, 405, 220));
		scrListaUsuarios.setBackground(new Color(255, 130, 14));
		pnlRelacionFiltros.setBounds(new Rectangle(15, 60, 405, 20));
		pnlRelacionFiltros.setBackground(new Color(0, 114, 169));
		pnlRelacionFiltros.setLayout(null);
		pnlIngresarProductos.setBounds(new Rectangle(15, 10, 405, 40));
		pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(
				new Color(0,114,169), 1));
		pnlIngresarProductos.setBackground(Color.white);
		pnlIngresarProductos.setLayout(null);
		pnlIngresarProductos.setForeground(Color.orange);
		txtDescripcion.setBounds(new Rectangle(105, 10, 240, 20));
		txtDescripcion.setFont(new Font("SansSerif", 1, 11));
		txtDescripcion.setForeground(new Color(32, 105, 29));
		txtDescripcion.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				txtDescripcion_keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				txtDescripcion_keyReleased(e);
			}
		});
		btnDescripcion.setText("Descripcion :");
		btnDescripcion.setBounds(new Rectangle(15, 10, 85, 20));
		btnDescripcion.setMnemonic('d');
		btnDescripcion.setFont(new Font("SansSerif", 1, 11));
		btnDescripcion.setDefaultCapable(false);
		btnDescripcion.setRequestFocusEnabled(false);
		btnDescripcion.setBackground(new Color(50, 162, 65));
		btnDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnDescripcion.setFocusPainted(false);
		btnDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		btnDescripcion.setContentAreaFilled(false);
		btnDescripcion.setBorderPainted(false);
		btnDescripcion.setForeground(new Color(0, 114, 169));
		btnDescripcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDescripcion_actionPerformed(e);
			}
		});
		tblMaestro.setFont(new Font("SansSerif", 0, 12));
		tblMaestro.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblMaestro_keyPressed(e);
			}
		});
		lblEsc.setText("[ ESC ] Cerrar");
		lblEsc.setBounds(new Rectangle(335, 310, 85, 20));
		btnRelacionUsuarios.setText("Relacion de Usuarios");
		btnRelacionUsuarios.setBounds(new Rectangle(10, 0, 150, 20));
		btnRelacionUsuarios.setMnemonic('r');
    btnRelacionUsuarios.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionUsuarios_actionPerformed(e);
        }
      });
		scrListaUsuarios.getViewport();
		pnlIngresarProductos.add(txtDescripcion, null);
		pnlIngresarProductos.add(btnDescripcion, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
		jContentPane.add(lblEsc, null);
		jContentPane.add(lblEnter, null);
		scrListaUsuarios.getViewport().add(tblMaestro, null);
		jContentPane.add(scrListaUsuarios, null);
		pnlRelacionFiltros.add(btnRelacionUsuarios, null);
		jContentPane.add(pnlRelacionFiltros, null);
		jContentPane.add(pnlIngresarProductos, null);
		// this.getContentPane().add(jContentPane, null);
	}

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************
	private void initialize() {
		initTable();
	};

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************
	private void initTable() {
		tableModel = new FarmaTableModel(
				ConstantsPtoVenta.columnsListaMaestros,
				ConstantsPtoVenta.defaultValuesListaMaestros, 0);
		FarmaUtility.initSimpleList(tblMaestro, tableModel,
				ConstantsPtoVenta.columnsListaMaestros);
		cargaListaUsuarios();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void tblMaestro_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void this_windowOpened(WindowEvent e) {

		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(txtDescripcion);
	}

	private void txtDescripcion_keyPressed(KeyEvent e) {
		FarmaGridUtils.aceptarTeclaPresionada(e, tblMaestro, txtDescripcion, 1);
		// FarmaGridUtils.buscarCodigo_KeyPressed(e, this, tblMaestro,
		// txtDescripcion, 0);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			if (tblMaestro.getSelectedRow() >= 0) {
				if (!(FarmaUtility.findTextInJTable(tblMaestro, txtDescripcion
						.getText().trim(), 0, 1))) {
					FarmaUtility
							.showMessage(
									this,
									"Maestro No Encontrado seg�n Criterio de B�squeda !!!",
									txtDescripcion);
					return;
				}
			}
		}
		chkKeyPressed(e);
	}

	private void txtDescripcion_keyReleased(KeyEvent e) {
		FarmaGridUtils.buscarDescripcion(e, tblMaestro, txtDescripcion, 1);
	}

	private void btnDescripcion_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(txtDescripcion);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			guardaValoresUsuario();
			cerrarVentana(true);
		}
	}

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	// **************************************************************************
	// Metodos de l�gica de negocio
	// **************************************************************************

	private void cargaListaUsuarios() {
		String tipoMaestro = VariablesPtoVenta.vTipoMaestro;
		try {
			DBCajas.getListaUsuarios(tableModel);
			// agregando la primera linea en blanco
			ArrayList myArray = new ArrayList();
			// myArray.add("");
			// myArray.add("");
			// tableModel.insertRow(myArray);
			FarmaUtility.ordenar(tblMaestro, tableModel, 1,
					FarmaConstants.ORDEN_ASCENDENTE);
		} catch (SQLException e) {
		    log.error("",e);
      FarmaUtility.showMessage(this,"Error al obtener lista de usuarios. \n " + e.getMessage(),txtDescripcion);
		}
	}

	private void guardaValoresUsuario() {
		VariablesCajas.vSecUsuAsig = ((String) tblMaestro.getValueAt(tblMaestro
				.getSelectedRow(), 0)).trim();
		VariablesCajas.vSecUsuAsig = ((String) tblMaestro.getValueAt(tblMaestro
				.getSelectedRow(), 1)).trim();
	}

  private void btnRelacionUsuarios_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblMaestro);
  }

}
