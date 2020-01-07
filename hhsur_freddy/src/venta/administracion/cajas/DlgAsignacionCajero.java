package venta.administracion.cajas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;
import common.FarmaTableModel;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgAsignacionCajero extends JDialog {
	
    private static final Logger log = LoggerFactory.getLogger(DlgAsignacionCajero.class);

        Frame myParentFrame;

	FarmaTableModel tableModel;

	private JPanelWhite jContentPane = new JPanelWhite();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanelTitle pnlHeaderRelacionCajeros = new JPanelTitle();

	private JLabelFunction lblSeleccionarCajero = new JLabelFunction();

	private JLabelFunction lblAceptar = new JLabelFunction();

	private JLabelFunction lblSalir = new JLabelFunction();

	private JScrollPane scrListaCajeros = new JScrollPane();

	private JTable tblListaCajeros = new JTable();

  private JButtonLabel btnRelacionCajeros = new JButtonLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgAsignacionCajero() {
		this(null, "", false);
	}

	public DlgAsignacionCajero(Frame parent, String title, boolean modal) {
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
	// Método "jbInit()"
	// **************************************************************************

	private void jbInit() throws Exception {
		this.setSize(new Dimension(519, 257));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Asignación de Cajero");
		jContentPane.setLayout(null);
		pnlHeaderRelacionCajeros.setBounds(new Rectangle(10, 10, 495, 25));
		lblSeleccionarCajero.setBounds(new Rectangle(110, 200, 165, 20));
		lblSeleccionarCajero.setText("[Enter] Seleccionar Cajero");
		lblAceptar.setBounds(new Rectangle(290, 200, 100, 20));
		lblAceptar.setText("[F11] Aceptar");
		lblSalir.setBounds(new Rectangle(405, 200, 95, 20));
		lblSalir.setText("[Esc] Salir");
		scrListaCajeros.setBounds(new Rectangle(10, 35, 495, 155));
    btnRelacionCajeros.setText("Relación de Cajeros:");
    btnRelacionCajeros.setBounds(new Rectangle(10, 5, 150, 15));
    btnRelacionCajeros.setMnemonic('r');
    btnRelacionCajeros.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionCajeros_actionPerformed(e);
        }
      });
		scrListaCajeros.getViewport().add(tblListaCajeros, null);
		jContentPane.add(scrListaCajeros, null);
		jContentPane.add(lblSalir, null);
		jContentPane.add(lblAceptar, null);
		jContentPane.add(lblSeleccionarCajero, null);
    pnlHeaderRelacionCajeros.add(btnRelacionCajeros, null);
		jContentPane.add(pnlHeaderRelacionCajeros, null);
		this.getContentPane().add(jContentPane, BorderLayout.CENTER);
	}

	// **************************************************************************
	// Método "initialize()"
	// **************************************************************************
	private void initialize() {
		initTable();

	};

	// **************************************************************************
	// Métodos de inicialización
	// **************************************************************************

	private void initTable() {
		// tableModel = new
		// FarmaTableModel(columnsListaProductos,defaultValuesListaProductos,0);
		// FarmaUtility.initSelectList(tblProductos,tableModel,columnsListaProductos);
		// cargaListaProductos();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyReleased(KeyEvent e) {
		if (venta.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
		{
		}

		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		} else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e)) {
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

		}

	}
 private void btnRelacionCajeros_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaCajeros);
  }
	// **************************************************************************
	// Metodos de lógica de negocio
	// **************************************************************************
	private void cargaListaCajeros() {
	}

 

}