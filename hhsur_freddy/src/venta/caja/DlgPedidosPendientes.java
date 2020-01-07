package venta.caja;

import componentes.gs.componentes.JConfirmDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.VariablesCaja;
import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import venta.caja.reference.UtilityCaja;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgPedidosPendientes extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgPedidosPendientes.class);
    
	Frame myParentFrame;
	FarmaTableModel tableModelDetallePedido;
	FarmaTableModel tableModelListaPendientes;
	private BorderLayout borderLayout1 = new BorderLayout();
	private JPanel jContentPane = new JPanel();
	ActionMap actionMap1 = new ActionMap();
	JLabelFunction lblF11 = new JLabelFunction();
	JLabelFunction lblEnter = new JLabelFunction();
	JScrollPane scrPendientes = new JScrollPane();
	JPanel pnlRelacion = new JPanel();
	XYLayout xYLayout2 = new XYLayout();
	JScrollPane scrDetalle = new JScrollPane();
	JPanel pnlItems = new JPanel();
	XYLayout xYLayout3 = new XYLayout();
	JButton btnDetalle = new JButton();
	JLabel jLabel3 = new JLabel();
	JLabelFunction lblEsc = new JLabelFunction();
	JTable tblDetalle = new JTable();
	JTable tblListaPendientes = new JTable();
	private JButtonLabel btnPedidosPendeintes = new JButtonLabel();
    private JButton jButton1 = new JButton();
    // JLabel lblModo = new FarmaBlinkJLabel();
	// JLabel lblTipoPedido = new JLabel();

	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgPedidosPendientes() {
		this(null, "", false);
	}

	public DlgPedidosPendientes(Frame parent, String title, boolean modal) {
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
		this.setSize(new Dimension(917, 567));
		this.getContentPane().setLayout(borderLayout1);
		this.setTitle("Pedidos Pendientes");
		this.setFont(new Font("SansSerif", 0, 11));
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				this_windowOpened(e);
			}
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		jContentPane.setLayout(null);
		jContentPane.setSize(new Dimension(657, 361));
		jContentPane.setBackground(Color.white);
		jContentPane.setForeground(Color.white);
		lblF11.setText("[ F5] Anular");
		lblF11.setBounds(new Rectangle(255, 490, 100, 20));
		lblEnter.setText("[ ENTER ]  Seleccionar Pedido");
		lblEnter.setBounds(new Rectangle(70, 490, 180, 20));
		scrPendientes.setFont(new Font("SansSerif", 0, 11));
		scrPendientes.setBounds(new Rectangle(10, 40, 875, 200));
		scrPendientes.setBackground(new Color(255, 130, 14));
		pnlRelacion.setBackground(new Color(0, 114, 169));
		pnlRelacion.setLayout(xYLayout2);
		pnlRelacion.setFont(new Font("SansSerif", 0, 11));
		pnlRelacion.setBounds(new Rectangle(10, 15, 875, 25));
		scrDetalle.setFont(new Font("SansSerif", 0, 11));
		scrDetalle.setBounds(new Rectangle(10, 275, 875, 190));
		scrDetalle.setBackground(new Color(255, 130, 14));
		pnlItems.setBackground(new Color(0, 114, 169));
		pnlItems.setFont(new Font("SansSerif", 0, 11));
		pnlItems.setLayout(xYLayout3);
		pnlItems.setBounds(new Rectangle(10, 250, 875, 25));
		btnDetalle.setText("Detalle del Pedido :");
		btnDetalle.setFont(new Font("SansSerif", 1, 11));
		btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
		btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnDetalle.setBackground(new Color(43, 141, 39));
		btnDetalle.setForeground(Color.white);
		btnDetalle.setRequestFocusEnabled(false);
		btnDetalle.setMnemonic('d');
		btnDetalle.setBorderPainted(false);
		btnDetalle.setContentAreaFilled(false);
		btnDetalle.setDefaultCapable(false);
		btnDetalle.setFocusPainted(false);
    btnDetalle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalle_actionPerformed(e);
        }
      });
		jLabel3.setText("Opciones :");
		jLabel3.setFont(new Font("SansSerif", 1, 11));
		jLabel3.setBounds(new Rectangle(15, 470, 70, 15));
		lblEsc.setText("[ Esc ]  Cerrar");
		lblEsc.setBounds(new Rectangle(550, 490, 95, 20));
		tblDetalle.setFont(new Font("SansSerif", 0, 11));
		tblDetalle.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblDetalle_keyPressed(e);
			}
		});
		tblListaPendientes.setFont(new Font("SansSerif", 0, 11));
		tblListaPendientes.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				tblListaPendientes_keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
                    tblListaPendientes_keyReleased(e);
                }
		});
    btnPedidosPendeintes.setText("Pedidos Pendientes de Cobranza :");
    btnPedidosPendeintes.setMnemonic('p');
    btnPedidosPendeintes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPedidosPendeintes_actionPerformed(e);
        }
      });
        jButton1.setText("Actualizar");
        jButton1.setBounds(new Rectangle(785, 490, 105, 30));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        scrPendientes.getViewport();
		scrDetalle.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(jButton1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrPendientes.getViewport().add(tblListaPendientes, null);
		jContentPane.add(scrPendientes, null);
    pnlRelacion.add(btnPedidosPendeintes, new XYConstraints(10, 5, 245, 15));
		jContentPane.add(pnlRelacion, null);
		scrDetalle.getViewport().add(tblDetalle, null);
		jContentPane.add(scrDetalle, null);
        pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
        jContentPane.add(pnlItems, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(lblEsc, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************

	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTableListaPendientes();
		cargarRegSeleccionado();
		initTableDetallePedido();
	};

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************
	private void initTableListaPendientes() {
		tableModelListaPendientes = new FarmaTableModel(ConstantsCaja.columnsListaPendientes,ConstantsCaja.defaultListaPendientes, 0);
		FarmaUtility.initSimpleList(tblListaPendientes, tableModelListaPendientes,ConstantsCaja.columnsListaPendientes);
		cargaListaPendientes();
	};

	private void initTableDetallePedido() {
		if (tblListaPendientes.getRowCount() > 0) {
			tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsDetallePedido,ConstantsCaja.defaultDetallePedido, 0);
			FarmaUtility.initSimpleList(tblDetalle, tableModelDetallePedido,ConstantsCaja.columnsDetallePedido);
			cargaDetallePedido();
		}
	};

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void tblListaPendientes_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void tblDetalle_keyPressed(KeyEvent e) {
		chkKeyPressed(e);
	}

	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(tblListaPendientes);
               /* if(FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
                {
                    lblEnter.setVisible(false);
                    lblF11.setVisible(false);
                }*/
	}

 private void btnPedidosPendeintes_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaPendientes);
  }

  private void btnDetalle_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblDetalle);
  }
	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            /*if(!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
                            {
                                seleccionaPedido();    
                            }*/
		    seleccionaPedido(); 
		} else if (e.getKeyCode() == KeyEvent.VK_F5) {
                            /*if(!FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
                            {
                                anularPedidoPendiente();    
                            }*/
                            anularPedidoPendiente();    
          
        }
    }

	// **************************************************************************
	// Metodos de l�gica de negocio
	// **************************************************************************
	private void cargaListaPendientes() {
		try {
			DBCaja.getListaPedidosPendientes(tableModelListaPendientes);
			if (tblListaPendientes.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaPendientes,tableModelListaPendientes, 13,FarmaConstants.ORDEN_DESCENDENTE);
			log.debug("se cargo la lista de cabeceras");
		} catch (SQLException e) {
      log.error("",e);
			FarmaUtility.showMessage(this,"Error al listar pedidos pendientes. \n " + e.getMessage(),tblDetalle);
		}
	}

	private void cargaDetallePedido() {
		log.debug("cargaDetallePedido");
		tableModelDetallePedido.clearTable();
		tblDetalle.repaint();
		tblDetalle.removeAll();
		try {
			DBCaja.getListaDetallePedido(tableModelDetallePedido);
			if (tblDetalle.getRowCount() > 0)
				FarmaUtility.ordenar(tblDetalle, tableModelDetallePedido, 0,FarmaConstants.ORDEN_ASCENDENTE);
			log.debug("se cargo la lista de detalle");
		} catch (SQLException e) {
			log.error("",e);
			FarmaUtility.showMessage(this,"Error al cargra lista detalle pedido. \n " + e.getMessage(),tblDetalle);
		}
	}

	private void tblListaPendientes_keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
			if (tieneRegistroSeleccionado(tblListaPendientes)) {
				cargarRegSeleccionado();
				cargaDetallePedido();
			}
	}

	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;

		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void cargarRegSeleccionado() {
		if (tieneRegistroSeleccionado(tblListaPendientes))
			VariablesCaja.vNumPedVta = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(), 2).toString().trim();

	}

	private void cambiaEstadoPedido(String pNumPed, String pEst) throws SQLException {
		DBCaja.cambiarEstadoPed(pNumPed, pEst);
	}

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

	private void this_windowClosing(WindowEvent e) {
		FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.", null);
	}

  private void seleccionaPedido()
  {
    if(tblListaPendientes.getRowCount() <= 0) return;
    VariablesCaja.vNumPedVta = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),2).toString().trim();
    VariablesCaja.vNumPedPendiente = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),1).toString().trim();
    VariablesCaja.vFecPedACobrar = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),12).toString().trim();
   
    VariablesCaja.vIndConvenio = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),14).toString().trim();
    VariablesCaja.vCodConvenio = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),15).toString().trim();
    VariablesCaja.vCodCliLocal = tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),16).toString().trim();
    
    cerrarVentana(true);
  }

  private void anularPedidoPendiente()
  {
    if(tblListaPendientes.getRowCount()>0 && tblListaPendientes.getSelectedRow()>-1)
    {
      if (JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro que desea efectuar la operaci�n?")) 
      {
        try{
          cargarRegSeleccionado();
          DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta);
            ///-- inicio de validacion de Campa�a 
            // DUBILLUZ 19.12.2008
            String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
            boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                   (
                                    pIndLineaMatriz,
                                    VariablesCaja.vNumPedVta,this,
                                    ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                    lblEsc,
                                    FarmaConstants.INDICADOR_N
                                    );
            
            if (!pRspCampanaAcumulad)
              {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                        FarmaConstants.INDICADOR_S);
              }          
          
          FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                FarmaConstants.INDICADOR_S);
          FarmaUtility.aceptarTransaccion();
          //FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(this,"La operaci�n se realiz� correctamente",lblEsc);          
          //cerrarVentana(true);
        }
        catch(SQLException ex){
          FarmaUtility.liberarTransaccion();
            if(ex.getErrorCode()==20002)
              FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
            else
                if(ex.getErrorCode()==20003)
                  FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                else    
                  FarmaUtility.showMessage(this,"Ocurri� un error al anular pedido pendiente : \n"+ ex.getMessage(), lblEsc);
          log.error("",ex);
          //cerrarVentana(false);
        }
        finally
        {
          cargaListaPendientes();           
          tableModelDetallePedido.clearTable();
          tblDetalle.repaint();
        }        
      }
    }
  }

    private void jButton1_actionPerformed(ActionEvent e) {
        cargaListaPendientes();
        if (tblListaPendientes.getRowCount() > 0) {
            if (tieneRegistroSeleccionado(tblListaPendientes)) {
                    cargarRegSeleccionado();
                    cargaDetallePedido();
            }
        }
    }
}
