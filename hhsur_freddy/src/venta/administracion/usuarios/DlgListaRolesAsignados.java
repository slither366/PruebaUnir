package venta.administracion.usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.administracion.usuarios.reference.ConstantsUsuarios;
import venta.administracion.usuarios.reference.DBUsuarios;
import venta.administracion.usuarios.reference.VariablesUsuarios;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import venta.reference.ConstantsPtoVenta;

import venta.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaRolesAsignados extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaRolesAsignados.class);
    
	Frame myParentFrame;

	FarmaTableModel tableModel;

	private JPanelWhite jContentPane = new JPanelWhite();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelTitle pnlDatosUsuario = new JPanelTitle();

	private JPanelTitle pnlHeaderRoles = new JPanelTitle();

	private JScrollPane scrListaRoles = new JScrollPane();

	private JTable tblListaRoles = new JTable();

	private JLabelFunction lblSeleccionarRol = new JLabelFunction();


	private JLabelFunction lblSalir = new JLabelFunction();

	private JLabelWhite lblNroSec_T = new JLabelWhite();

	private JLabelWhite lblNroSec = new JLabelWhite();

	private JLabelWhite lblUsuario_T = new JLabelWhite();

	private JLabelWhite lblUsuario = new JLabelWhite();

	private JButtonLabel btnRelacionRoles = new JButtonLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgListaRolesAsignados() {
		this(null, "", false);
	}

	public DlgListaRolesAsignados(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(450, 310));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Lista de Roles Asignados");
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
		});
		jContentPane.setLayout(null);
		pnlDatosUsuario.setBounds(new Rectangle(10, 10, 425, 75));
		pnlDatosUsuario.setBorder(BorderFactory.createLineBorder(new Color(0,
				114, 169), 1));
		pnlDatosUsuario.setBackground(Color.white);
		pnlHeaderRoles.setBounds(new Rectangle(10, 90, 425, 25));
		scrListaRoles.setBounds(new Rectangle(10, 115, 425, 135));
		tblListaRoles.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblListaRoles_keyPressed(e);
			}
		});
		lblSeleccionarRol.setBounds(new Rectangle(215, 255, 130, 20));
		lblSeleccionarRol.setText("[F2] Seleccionar Rol");
		lblSalir.setBounds(new Rectangle(355, 255, 75, 20));
		lblSalir.setText("[Esc] Salir");
		lblNroSec_T.setText("Nro. Sec :");
		lblNroSec_T.setBounds(new Rectangle(10, 10, 55, 20));
		lblNroSec_T.setForeground(new Color(0, 114, 169));
		lblNroSec.setText("00022");
		lblNroSec.setBounds(new Rectangle(70, 10, 300, 20));
		lblNroSec.setForeground(new Color(0, 114, 169));
		lblUsuario_T.setText("Usuario :");
		lblUsuario_T.setBounds(new Rectangle(10, 40, 55, 20));
		lblUsuario_T.setForeground(new Color(0, 114, 169));
		lblUsuario.setText("ALCANTARA MEDINA, VILMA LORENA");
		lblUsuario.setBounds(new Rectangle(70, 40, 300, 20));
		lblUsuario.setForeground(new Color(0, 114, 169));
		btnRelacionRoles.setText("Relacion de Roles Asginados");
		btnRelacionRoles.setBounds(new Rectangle(5, 0, 205, 20));
		btnRelacionRoles.setMnemonic('r');
    btnRelacionRoles.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionRoles_actionPerformed(e);
        }
      });
		jContentPane.add(lblSalir, null);
    jContentPane.add(lblSeleccionarRol, null);
		scrListaRoles.getViewport().add(tblListaRoles, null);
		jContentPane.add(scrListaRoles, null);
		pnlHeaderRoles.add(btnRelacionRoles, null);
		jContentPane.add(pnlHeaderRoles, null);
		pnlDatosUsuario.add(lblUsuario, null);
		pnlDatosUsuario.add(lblUsuario_T, null);
		pnlDatosUsuario.add(lblNroSec, null);
		pnlDatosUsuario.add(lblNroSec_T, null);
		jContentPane.add(pnlDatosUsuario, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	 this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
  }

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************

	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTable();
		cargarDatos();
		cargarRoles();
	};

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************

	private void initTable() {
		tableModel = new FarmaTableModel(
				ConstantsUsuarios.columnsListaRolesAsignados,
				ConstantsUsuarios.defaultValuesListaRolesAsignados, 0);
		FarmaUtility.initSimpleList(tblListaRoles, tableModel,
				ConstantsUsuarios.columnsListaRolesAsignados);
		cargaLista();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaRoles);
	}

	private void tblListaRoles_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************

	private void chkKeyPressed(KeyEvent e) {

		if (UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
		{
		} else if (UtilityPtoVenta.verificaVK_F2(e)) {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionRoles);
      else {
        DlgListaRoles dlgListaRoles = new DlgListaRoles(this.myParentFrame,"", true);
        dlgListaRoles.setVisible(true);
        if (FarmaVariables.vAceptar) {
          cargaLista();
                    FarmaVariables.vAceptar = false;
        }
      }	
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
		}

	}

	// **************************************************************************
	// Metodos de l�gica de negocio
	// **************************************************************************

	private void cargaLista() {

		try {
			DBUsuarios.getListaRolesAsignados(tableModel);

			if (tblListaRoles.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaRoles, tableModel, 0, "asc");
			log.debug("se cargo la lista de roles asignados");
		} catch (SQLException e) {
		    log.error("",e);
        FarmaUtility.showMessage(this,"Error al obtener roles asignados a los usuarios. \n " + e.getMessage(),tblListaRoles);
		}

	}

	private void cargarDatos() {
		lblNroSec.setText(VariablesUsuarios.vSecUsuLocal);
		lblUsuario.setText(VariablesUsuarios.vApePat + " "
				+ VariablesUsuarios.vApeMat + " , "
				+ VariablesUsuarios.vNombres);

	}

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private void cargarRoles() {
		VariablesUsuarios.listaRoles = new ArrayList();

		for (int i = 0; i < tblListaRoles.getRowCount(); i++) {
			VariablesUsuarios.listaRoles.add(new String[] {
					tblListaRoles.getValueAt(i, 0).toString(),
					tblListaRoles.getValueAt(i, 1).toString() });
		}

	}

  private void this_windowClosing(WindowEvent e)
  { FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnRelacionRoles_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaRoles);
  }

}
