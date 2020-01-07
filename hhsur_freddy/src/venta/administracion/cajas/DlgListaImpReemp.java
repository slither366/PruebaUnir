package venta.administracion.cajas;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import java.sql.*;
import javax.swing.JDialog;
import componentes.gs.componentes.JPanelWhite;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JButtonLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import common.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import venta.administracion.cajas.reference.*;
import venta.administracion.impresoras.reference.*;
import venta.administracion.reference.*;
import venta.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaImpReemp extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgListaImpReemp.class);
Frame myParentFrame;
  
 FarmaTableModel tableModel;

  private JPanelWhite jContentPane = new JPanelWhite();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelTitle pnlHeader1 = new JPanelTitle();
  private JLabelFunction lblOpciones = new JLabelFunction();
  private JLabelFunction lblAceptar = new JLabelFunction();
  private JLabelFunction lblCerrar = new JLabelFunction();
  private JButtonLabel btnRelacionImpresoras = new JButtonLabel();
  private JScrollPane scrListaImpresoras = new JScrollPane();
  private JTable tblListaImpresoras = new JTable();

//**************************************************************************
//Constructores
//**************************************************************************
  public DlgListaImpReemp()
  {
    this(null, "", false);
  }

  public DlgListaImpReemp(Frame parent, String title, boolean modal)
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

//**************************************************************************
//M�todo "jbInit()"
//**************************************************************************

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(656, 233));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Impresoras Disponibles");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    jContentPane.setLayout(null);
    pnlHeader1.setBounds(new Rectangle(5, 5, 640, 25));
    lblOpciones.setBounds(new Rectangle(10, 180, 100, 20));
    lblOpciones.setText("Opciones:");
    lblAceptar.setBounds(new Rectangle(440, 180, 100, 20));
    lblAceptar.setText("[F11] Aceptar");
    lblCerrar.setBounds(new Rectangle(550, 180, 95, 20));
    lblCerrar.setText("[Esc] Cerrar");
    btnRelacionImpresoras.setText("Relaci�n de impresoras");
    btnRelacionImpresoras.setBounds(new Rectangle(10, 5, 135, 15));
    btnRelacionImpresoras.setMnemonic('r');
    btnRelacionImpresoras.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionImpresoras_actionPerformed(e);
        }
      });
    scrListaImpresoras.setBounds(new Rectangle(5, 30, 640, 145));
    tblListaImpresoras.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaImpresoras_keyPressed(e);
        }
      });
    scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
    jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblCerrar, null);
        jContentPane.add(lblAceptar, null);
        jContentPane.add(lblOpciones, null);
        pnlHeader1.add(btnRelacionImpresoras, null);
    jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  //**************************************************************************
//M�todo "initialize()"
//**************************************************************************
  private void initialize()
  {
    initTable();
   
  };
  //**************************************************************************
//M�todos de inicializaci�n
//**************************************************************************



 private void initTable()
  {
    	tableModel = new FarmaTableModel(
				  ConstantsImpresoras.columnsListaImpresoras,
				ConstantsImpresoras.defaultValuesListaImpresoras, 0);
		FarmaUtility.initSimpleList(tblListaImpresoras, tableModel,
				ConstantsImpresoras.columnsListaImpresoras);
		cargarListaImpresoras();
  }



//**************************************************************************
//Metodos de eventos
//**************************************************************************
  
  // **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e ) 
  {
    if (venta.reference.UtilityPtoVenta.verificaVK_F1(e))  // Reservado para ayuda
    {
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e)) 
    {
    if(tieneRegistroSeleccionado(tblListaImpresoras))
     	if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,
					"�Est� seguro que desea efectuar la operaci�n?")) {
				try {
				      cargarRegSeleccionado();
                                      reemplazaImpCaja();
					FarmaUtility.aceptarTransaccion();
					FarmaUtility.showMessage(this,
							"La operaci�n se realiz� correctamente", tblListaImpresoras);
					cerrarVentana(true);
				} catch (SQLException ex) {
                                    
					FarmaUtility.liberarTransaccion();
				    if(ex.getErrorCode() == 20010){
                                        FarmaUtility.showMessage(this,"No se puede asignar este tipo de impresora.",tblListaImpresoras);
                                    }else {
					FarmaUtility.showMessage(this,"Error al listar informacion de la caja: \n"
                                            + ex.getMessage(), tblListaImpresoras);
                                            log.error("",ex);
                                        }
                                            
					
					cerrarVentana(false);
				}
			}
    } 
     else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
    {
     this.cerrarVentana(false);
    }
   
  }
  
//**************************************************************************
//Metodos de l�gica de negocio
//**************************************************************************

  private void cargarListaImpresoras(){
  
  	try {
			 DBCajas.getListaImpresorasReemp(tableModel);
			if (tblListaImpresoras.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaImpresoras, tableModel, 0, "asc");
			log.info("se cargo la lista de impresoras");
		} catch (SQLException e) {
		    log.error("",e);
        FarmaUtility.showMessage(this,"Error al obtener informacion de las impresoras. \n " + e.getMessage(),tblListaImpresoras);
		}

	}
  

  private void this_windowOpened(WindowEvent e)
  {FarmaUtility.centrarVentana(this);
  FarmaUtility.moveFocus(tblListaImpresoras);
  }

  private void tblListaImpresoras_keyPressed(KeyEvent e)
  {chkKeyPressed(e);
  }

  private void btnRelacionImpresoras_actionPerformed(ActionEvent e)
  {FarmaUtility.moveFocus(tblListaImpresoras);
  }
  
  private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}
  	private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;

		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

	private void cargarRegSeleccionado() {
		VariablesCajas.vSecImprLocal2 = tblListaImpresoras.getValueAt(
				tblListaImpresoras.getSelectedRow(), 0).toString().trim();

	}
  
  	private void reemplazaImpCaja() throws SQLException {
		DBCajas.reemplazaImpCaja();

	}
}