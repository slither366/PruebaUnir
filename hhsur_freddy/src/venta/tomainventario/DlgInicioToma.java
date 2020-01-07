package venta.tomainventario;

import componentes.gs.componentes.JConfirmDialog;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.tomainventario.reference.ConstantsTomaInv;
import venta.tomainventario.reference.DBTomaInv;
import venta.tomainventario.reference.VariablesTomaInv;
import common.*;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import venta.reference.*;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JTextFieldSanSerif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgInicioToma extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgInicioToma.class);

    FarmaTableModel tableModelListaLaboratorios;
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaLaboratorio = new JTable();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnLaboratorio = new JButtonLabel();
    private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();
    private JButtonLabel btnRelacionLaboratorios = new JButtonLabel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JButtonLabel lblCantidadSeleccion = new JButtonLabel();
	// **************************************************************************
	// Constructores
	// **************************************************************************

	public DlgInicioToma() {
		this(null, "", false);
	}

	public DlgInicioToma(Frame parent, String title, boolean modal) {
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

    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(456, 394));
        this.getContentPane().setLayout(null);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Inicio de Toma");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 75, 425, 225));
        jScrollPane1.setFocusable(false);
        jPanelHeader1.setBounds(new Rectangle(10, 15, 425, 30));
        jPanelHeader1.setFocusable(false);
        jPanelTitle1.setBounds(new Rectangle(10, 50, 425, 25));
        jPanelTitle1.setFocusable(false);
        btnLaboratorio.setText("Laboratorio :");
        btnLaboratorio.setBounds(new Rectangle(15, 5, 80, 20));
        btnLaboratorio.setMnemonic('l');
        btnLaboratorio.setFocusable(false);
        btnLaboratorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLaboratorio_actionPerformed(e);
			}
		});
        txtLaboratorio.setBounds(new Rectangle(100, 5, 310, 20));
        txtLaboratorio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                        txtLaboratorio_keyPressed(e);
                }
                public void keyReleased(KeyEvent e) {
                        txtLaboratorio_keyReleased(e);
                }
        });
        btnRelacionLaboratorios.setText("Relacion de Laboratorios");
        btnRelacionLaboratorios.setBounds(new Rectangle(10, 5, 165, 15));
        btnRelacionLaboratorios.setMnemonic('r');
        btnRelacionLaboratorios.setFocusable(false);
        btnRelacionLaboratorios.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionLaboratorios_actionPerformed(e);
        }
      });
        lblEnter.setBounds(new Rectangle(10, 310, 135, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setFocusable(false);
        lblF8.setBounds(new Rectangle(305, 310, 130, 20));
        lblF8.setText("[ F8 ] Tipo Laboratorio");
        lblF8.setFocusable(false);
        lblF5.setBounds(new Rectangle(150, 310, 150, 20));
        lblF5.setText("[ F5 ] Limpiar Selecci�n");
        lblF5.setFocusable(false);
        lblF11.setBounds(new Rectangle(150, 335, 150, 20));
        lblF11.setText("[ F11 ] Aceptar Toma");
        lblEscape.setBounds(new Rectangle(305, 335, 130, 20));
        lblEscape.setText("[ Esc ] Cerrar");
        lblCantidadSeleccion.setText("0");
        lblCantidadSeleccion.setBounds(new Rectangle(350, 5, 55, 15));
        lblCantidadSeleccion.setMnemonic('r');
        lblCantidadSeleccion.setFocusable(false);
        jPanelHeader1.add(txtLaboratorio, null);
        jPanelHeader1.add(btnLaboratorio, null);
        jPanelTitle1.add(lblCantidadSeleccion, null);
        jPanelTitle1.add(btnRelacionLaboratorios, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        tblListaLaboratorio.setFocusable(false);
        jScrollPane1.getViewport().add(tblListaLaboratorio, null);
        jContentPane.add(jScrollPane1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

	// **************************************************************************
	// M�todo "initialize()"
	// **************************************************************************

	private void initialize() {
        FarmaVariables.vAceptar = false;
		initTableListaLaboratorios();
	}

	// **************************************************************************
	// M�todos de inicializaci�n
	// **************************************************************************

	private void initTableListaLaboratorios() {
		tableModelListaLaboratorios = new FarmaTableModel(
				ConstantsTomaInv.columnsListaLaboratorios,
				ConstantsTomaInv.defaultValuesListaLaboratorios, 0);
		FarmaUtility.initSelectList(tblListaLaboratorio,
				tableModelListaLaboratorios,
				ConstantsTomaInv.columnsListaLaboratorios);
		cargaListaLaboratorios();
	}

	// **************************************************************************
	// Metodos de eventos
	// **************************************************************************
	private void this_windowOpened(WindowEvent e) {
		FarmaUtility.centrarVentana(this);
		FarmaUtility.moveFocus(txtLaboratorio);
		cantidadseleccion();
		lblCantidadSeleccion.setText (""+0);
		VariablesTomaInv.listaLabsSeleccion.clear();
	}

	private void txtLaboratorio_keyPressed(KeyEvent e) {
		FarmaGridUtils.aceptarTeclaPresionada(e, tblListaLaboratorio,txtLaboratorio, 2);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			if (tblListaLaboratorio.getSelectedRow() >= 0) {
				if (!(FarmaUtility.findTextInJTable(tblListaLaboratorio,
						txtLaboratorio.getText().trim(), 1, 2))) {
					FarmaUtility.showMessage(this,	"Laboratorio No Encontrado seg�n Criterio de B�squeda !!!",txtLaboratorio);
					return;
				} else {
					FarmaUtility.setCheckValue(tblListaLaboratorio, false);
					refrescarlistaProductosSeleccion();
					cantidadseleccion();
				}
			}
		}
		chkKeyPressed(e);
	}

  /*
   * 23/03/2006 Paulo Metodo creado para conseguir la cantidad de items seleccionados
   */
  private void cantidadseleccion()
  { 
    int contador = 0 ;
    contador = VariablesTomaInv.listaLabsSeleccion.size();
    lblCantidadSeleccion.setText (""+contador);
    //VariablesTomaInv.listaLabsSeleccion.clear();  
  }

	private void btnLaboratorio_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(txtLaboratorio);
	}

	// **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
		} else if (e.getKeyCode() == KeyEvent.VK_F5) {
			deseleccionarTodos();
			cantidadseleccion();
			FarmaUtility.moveFocus(txtLaboratorio);
		} else if (e.getKeyCode() == KeyEvent.VK_F8) {
			//seleccionarTodos();
      /*
     * Fecha        Usuario     Comentario
     * 22/03/2006   Paulo       llama a la nueva funcionalidad q carga los tipos de laboratorio
     */
			listadoSeleccionTipoLaboratorio();
		} else if (UtilityPtoVenta.verificaVK_F11(e)) {
      /**
       * VAlidacion si el Usuario tiene el Rol Auditor
       * @author : dubilluz
       * @since  : 20.07.2007
       */
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtLaboratorio);
      else{
        if (cargaLogin()){
        //VariablesTomaInv.vIdUsu_Toma = FarmaVariables.vIdUsu;
        // refrescarlistaProductosSeleccion();
        int cantLabs = 0;
        String tipToma = "";
        cantLabs = VariablesTomaInv.listaLabsSeleccion.size();
        if (cantLabs == tblListaLaboratorio.getRowCount()) {
          tipToma = "T";
        } else {
          tipToma = "P";
        }
        if (cantLabs > 0) {
          if (JConfirmDialog.rptaConfirmDialog(this,"�Esta seguro de realizar la operacion?")) {
            String numGen = "";
            try {
              numGen = DBTomaInv.generarCabTomaInv("" + cantLabs,tipToma,VariablesTomaInv.vIdUsu_Toma);				
              if (tipToma.equals("T")) {
                  //Modificado 
                  log.debug("usuario q se logueo T >>"+VariablesTomaInv.vIdUsu_Toma);
                  DBTomaInv.generarDetTotTomaInv(numGen,VariablesTomaInv.vIdUsu_Toma);
              } else {
                String[] tmp = new String[3];
                for (int i = 0; i < cantLabs; i++) {
                  tmp = (String[]) VariablesTomaInv.listaLabsSeleccion.get(i);
                  //Modificado
                  log.debug("usuario q se logueo >>"+VariablesTomaInv.vIdUsu_Toma);
                  DBTomaInv.generarDetTomaInv(numGen, tmp[1],VariablesTomaInv.vIdUsu_Toma);
                }
              }
              FarmaUtility.aceptarTransaccion();
              //DUBILLUZ - 07.01.2010
              if(tipToma.equals("T"))
              {
                log.info("Actualizara los indices por Toma TOTAL");  
                log.info("inicio");  
                DBTomaInv.actualizarInidices();
                log.info("Fin");  
              }
              //FIN - DUBILLUZ - 07.01.2010  
              deseleccionarTodos();
              FarmaUtility.showMessage(this,"La operaci�n se realiz� correctamente",txtLaboratorio);
              cerrarVentana(true);
            } catch (SQLException sql) {
                String mensaje="";
                FarmaUtility.liberarTransaccion();
                log.error("",sql);            
                if(sql.getErrorCode()==20017){
                mensaje="No se puede generar una toma de inventario de un laboratorio mientras exista stock comprometido.";}
                else
                {
                  mensaje="Ocurri� un error al iniciar la toma :\n" + sql.getMessage();
                }
              FarmaUtility.showMessage(this, mensaje, txtLaboratorio);
            }
          }
        } else {
            FarmaUtility.showMessage(this,"Debe seleccionar al menos un laboratorio",txtLaboratorio);
          }
      VariablesTomaInv.vIdUsu_Toma = "";
        }
      } 
    }
	}

	// **************************************************************************
	// Metodos de l�gica de negocio
	// **************************************************************************

	private void cargaListaLaboratorios() {
		try {
			DBTomaInv.getListaLabs(tableModelListaLaboratorios);
			if (tblListaLaboratorio.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaLaboratorio,tableModelListaLaboratorios, 2,FarmaConstants.ORDEN_ASCENDENTE);
			log.debug("se cargo la lista de det aboratorios");
		} catch (SQLException sql) {
			log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurr� un error al obtener la lista de laboratorios : \n"+sql.getMessage(),txtLaboratorio);
		}
	}

	private void txtLaboratorio_keyReleased(KeyEvent e) {
		chkKeyReleased(e);
	}

	private void chkKeyReleased(KeyEvent e) {
		FarmaGridUtils.buscarDescripcion(e, tblListaLaboratorio,txtLaboratorio, 2);
	}

	private void refrescarlistaProductosSeleccion() {
		int selectedRow = tblListaLaboratorio.getSelectedRow();
		Boolean valor = (Boolean) tblListaLaboratorio.getValueAt(selectedRow, 0);
		if (valor.booleanValue()) {
			// Si se ha marcado
			VariablesTomaInv.listaLabsSeleccion.add(new String[] {
							"" + selectedRow,
							tblListaLaboratorio.getValueAt(selectedRow, 1).toString(),
							tblListaLaboratorio.getValueAt(selectedRow, 2).toString() });
		} else {
			// si se ha desmarcado
			String codEliminar = tblListaLaboratorio.getValueAt(selectedRow, 1).toString();
			String[] tmp = new String[3];
			for (int i = 0; i < VariablesTomaInv.listaLabsSeleccion.size(); i++) {
				tmp = (String[]) VariablesTomaInv.listaLabsSeleccion.get(i);
				if (codEliminar.equals(tmp[1])) {
					VariablesTomaInv.listaLabsSeleccion.remove(i);
					break;
				}
			}
		}
	}

	private void deseleccionarTodos() {
		if (tblListaLaboratorio.getRowCount() > 0) {
			VariablesTomaInv.listaLabsSeleccion = new ArrayList();
			for (int i = 0; i < tblListaLaboratorio.getRowCount(); i++) {
				tblListaLaboratorio.setValueAt(new Boolean(false), i, 0);
			}
			tblListaLaboratorio.repaint();
		}
	}

	private void seleccionarTodos() {
		if (tblListaLaboratorio.getRowCount() > 0) {  
			//VariablesTomaInv.listaLabsSeleccion = new ArrayList();
      VariablesTomaInv.listaLabsSeleccion.clear();
			for (int i = 0; i < tblListaLaboratorio.getRowCount(); i++) {
				VariablesTomaInv.listaLabsSeleccion.add(new String[] { "" + i,
						tblListaLaboratorio.getValueAt(i, 1).toString(),
						tblListaLaboratorio.getValueAt(i, 2).toString() });
            tblListaLaboratorio.setValueAt(new Boolean(true), i, 0);
			}
			tblListaLaboratorio.repaint();
      cantidadseleccion();
		}
	}

  /*22/03/2006 Paulo Metodo Creado para la selecion de laboratorio
  */
  private void seleccionaLaboratorios()
  {
   // deseleccionarTodos();
    if(VariablesTomaInv.vLaboratorio.equals(ConstantsTomaInv.TIPO_FARMA))
    {
    if (tblListaLaboratorio.getRowCount() > 0 ) {
			//VariablesTomaInv.listaLabsSeleccion = new ArrayList();
			for (int i = 0; i < tblListaLaboratorio.getRowCount(); i++) {
          if(((String)(tblListaLaboratorio.getValueAt(i,3))).equals("01") && !((Boolean)tblListaLaboratorio.getValueAt(i,0)).booleanValue()) {
                VariablesTomaInv.listaLabsSeleccion.add(new String[] { "" + i,
                tblListaLaboratorio.getValueAt(i, 1).toString(),
                tblListaLaboratorio.getValueAt(i, 2).toString() });
                tblListaLaboratorio.setValueAt(new Boolean(true), i, 0);        
          }
        }
			}
			tblListaLaboratorio.repaint();
   }
    else if (VariablesTomaInv.vLaboratorio.equals(ConstantsTomaInv.TIPO_NO_FARMA)) 
    {
      if (tblListaLaboratorio.getRowCount() > 0 ) {
			//VariablesTomaInv.listaLabsSeleccion = new ArrayList();
			for (int i = 0; i < tblListaLaboratorio.getRowCount(); i++) {
          if(((String)(tblListaLaboratorio.getValueAt(i,3))).equals("02") && !((Boolean)tblListaLaboratorio.getValueAt(i,0)).booleanValue()) {
                VariablesTomaInv.listaLabsSeleccion.add(new String[] { "" + i,
                tblListaLaboratorio.getValueAt(i, 1).toString(),
                tblListaLaboratorio.getValueAt(i, 2).toString() });
                tblListaLaboratorio.setValueAt(new Boolean(true), i, 0);
          }
      }
			tblListaLaboratorio.repaint();
     // cantidadseleccion();
      }
    }
    else
    seleccionarTodos();
    cantidadseleccion();
  }

	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  /*
   * Fecha        Usuario     Comentario
   * 22/03/2006   Paulo       Implementar la funcionalidad de Seleccion de tipo de Laboratorio
   */
  private void listadoSeleccionTipoLaboratorio()
  {
    log.debug(VariablesTomaInv.vLaboratorio);
    DlgSeleccionTipoLaboratorio dlgSeleccionTipoLaboratorio = new DlgSeleccionTipoLaboratorio(myParentFrame, "", true);
    dlgSeleccionTipoLaboratorio.setVisible(true);
    
    if(FarmaVariables.vAceptar)
    {
      log.debug(VariablesTomaInv.vLaboratorio);
      FarmaVariables.vAceptar = false;
      seleccionaLaboratorios();
    }
  }

  private void btnRelacionLaboratorios_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaLaboratorio);
  }
/**
 * 
 */
 
  private boolean cargaLogin()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      /**
       * @author : dubilluz
       * @since  : 19.07.2007
       */
      VariablesTomaInv.vIdUsu_Toma = FarmaVariables.vIdUsu;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }
}