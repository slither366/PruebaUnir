package venta.caja;

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
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;
import venta.caja.reference.*;

import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.*;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JButtonLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import venta.convenio.reference.DBConvenio;

import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;

import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgPedidosPendientesImpresion extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgPedidosPendientesImpresion.class);  
    
  private Frame myParentFrame;
  private FarmaTableModel tableModelDetallePedido;
  private FarmaTableModel tableModelListaPendientesImpresion;
  private String numeroPedido = "";
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JScrollPane scrPendientes = new JScrollPane();
  private JPanel pnlRelacion = new JPanel();
  private XYLayout xYLayout2 = new XYLayout();
  private JScrollPane scrDetalle = new JScrollPane();
  private JPanel pnlItems = new JPanel();
  private XYLayout xYLayout3 = new XYLayout();
  private JButton btnDetalle = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JTable tblDetalle = new JTable();
  private JTable tblListaPendientes = new JTable();
  private JButtonLabel btnCabecera = new JButtonLabel();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();

//**************************************************************************
//Constructores
//**************************************************************************

  public DlgPedidosPendientesImpresion()
  {
    this(null, "", false);
  }

  public DlgPedidosPendientesImpresion(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();
    } catch(Exception e) {
      log.error("",e);
    }

  }
//**************************************************************************
//M�todo "jbInit()"
//**************************************************************************

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(656, 395));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Pedidos No Impresos");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(657, 361));
    jContentPane.setBackground(Color.white);
    jContentPane.setForeground(Color.white);
    lblF11.setText("[ F11] ReImprimir");
    lblF11.setBounds(new Rectangle(410, 335, 115, 20));
    scrPendientes.setFont(new Font("SansSerif", 0, 11));
    scrPendientes.setBounds(new Rectangle(15, 40, 615, 100));
    scrPendientes.setBackground(new Color(255, 130, 14));
    pnlRelacion.setBackground(new Color(255, 130, 14));
    pnlRelacion.setLayout(xYLayout2);
    pnlRelacion.setFont(new Font("SansSerif", 0, 11));
    pnlRelacion.setBounds(new Rectangle(15, 15, 615, 25));
    scrDetalle.setFont(new Font("SansSerif", 0, 11));
    scrDetalle.setBounds(new Rectangle(15, 170, 615, 140));
    scrDetalle.setBackground(new Color(255, 130, 14));
    pnlItems.setBackground(new Color(255, 130, 14));
    pnlItems.setFont(new Font("SansSerif", 0, 11));
    pnlItems.setLayout(xYLayout3);
    pnlItems.setBounds(new Rectangle(15, 145, 615, 25));
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
    jLabel3.setBounds(new Rectangle(15, 315, 70, 15));
    lblEsc.setText("[ Esc ]  Cerrar");
    lblEsc.setBounds(new Rectangle(535, 335, 95, 20));
    tblDetalle.setFont(new Font("SansSerif", 0, 11));
    tblDetalle.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblDetalle_keyPressed(e);
        }
      });
    tblListaPendientes.setFont(new Font("SansSerif", 0, 11));
    tblListaPendientes.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaPendientes_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          tblListaPendientes_keyReleased(e);
        }
      });
    btnCabecera.setText("Pedidos Pendientes de Impresion :");
    btnCabecera.setMnemonic('p');
    btnCabecera.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCabecera_actionPerformed(e);
        }
      });
    jLabelFunction1.setBounds(new Rectangle(25, 335, 195, 20));
    jLabelFunction1.setText("[ F2 ] Configurar Comprobantes");
    scrPendientes.getViewport();
    scrDetalle.getViewport();
    pnlItems.add(btnDetalle, new XYConstraints(10, 5, 125, 15));
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    scrPendientes.getViewport().add(tblListaPendientes, null);
    jContentPane.add(jLabelFunction1, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(scrPendientes, null);
    pnlRelacion.add(btnCabecera, new XYConstraints(10, 5, 210, 15));
    jContentPane.add(pnlRelacion, null);
    scrDetalle.getViewport().add(tblDetalle, null);
    jContentPane.add(scrDetalle, null);
    jContentPane.add(pnlItems, null);
    jContentPane.add(jLabel3, null);
    jContentPane.add(lblEsc, null);
  }
  
//**************************************************************************
//M�todo "initialize()"
//**************************************************************************

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    initTableListaPendientes();
    initTableDetallePedido();
  }

//**************************************************************************
//M�todos de inicializaci�n
//**************************************************************************
  private void initTableListaPendientes()
  {
    tableModelListaPendientesImpresion = new FarmaTableModel(ConstantsCaja.columnsListaPendientesImpresion,ConstantsCaja.defaultListaPendientesImpresion,0);
    FarmaUtility.initSimpleList(tblListaPendientes,tableModelListaPendientesImpresion,ConstantsCaja.columnsListaPendientesImpresion);
    cargaListaPendientes();
  }

  private void initTableDetallePedido()
  {
    if(tblListaPendientes.getRowCount()>0){
      tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsDetallePedidoNoImpreso,ConstantsCaja.defaultDetallePedidoNoImpreso,0);
      FarmaUtility.initSimpleList(tblDetalle,tableModelDetallePedido,ConstantsCaja.columnsDetallePedidoNoImpreso);
      cargaDetallePedido(obtieneNumeroPedido());
    }
  }

//**************************************************************************
//Metodos de eventos
//**************************************************************************
  private void tblListaPendientes_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void tblDetalle_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblListaPendientes);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  private void btnCabecera_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaPendientes);
  }

  private void btnDetalle_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblDetalle);
  }

//**************************************************************************
//Metodos auxiliares de eventos
//**************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if(venta.reference.UtilityPtoVenta.verificaVK_F2(e))
    {
      configuracionComprobante();;
    }
    else if(venta.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      reimprimirPedido();
    }
  }
//**************************************************************************
//Metodos de l�gica de negocio
//**************************************************************************
  private void cargaListaPendientes()
  {
    
   	try {
			DBCaja.obtieneListaPedidosNoImpresos(tableModelListaPendientesImpresion);
			if (tblListaPendientes.getRowCount() > 0)
				FarmaUtility.ordenar(tblListaPendientes, tableModelListaPendientesImpresion, 2, FarmaConstants.ORDEN_DESCENDENTE);
      else if(tableModelDetallePedido != null){
        tableModelDetallePedido.clearTable();
        tableModelDetallePedido.fireTableDataChanged();
      }
		} catch (SQLException e) {
      log.error("",e);
			FarmaUtility.showMessage(this,"Error al obtener lista de pedidos no impresos . \n " + e.getMessage(),tblDetalle);
		}
  }

  private void cargaDetallePedido(String pNumPedVta)
  {
   	try {
			DBCaja.obtieneDetallePedidoNoImpreso(tableModelDetallePedido, pNumPedVta);
			if (tblDetalle.getRowCount() > 0)
				FarmaUtility.ordenar(tblDetalle, tableModelDetallePedido, 0, FarmaConstants.ORDEN_ASCENDENTE);
		} catch (SQLException e) {
			log.error("",e);
      FarmaUtility.showMessage(this,"Error al obtener detalle pedido no impreso. \n " + e.getMessage(),tblDetalle);
		}
  }

  private void tblListaPendientes_keyReleased(KeyEvent e)
  {
    if(tieneRegistroSeleccionado(tblListaPendientes))
      cargaDetallePedido(obtieneNumeroPedido());
  }

  private boolean tieneRegistroSeleccionado(JTable pTabla) {
		boolean rpta = false;
		if (pTabla.getSelectedRow() != -1) {
			rpta = true;
		}
		return rpta;
	}

 	private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}
  
  private String obtieneNumeroPedido()
  {
    numeroPedido = ((String)tblListaPendientes.getValueAt(tblListaPendientes.getSelectedRow(),2)).trim();
    return numeroPedido;
  }
  
    private void reimprimirPedido()
    {
        if(!tieneRegistroSeleccionado(tblListaPendientes))
            return;
        try
        {
            if(!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                        "Para Reimprimir un pedido, debe configurar correctamente\nlos comprobantes. Esta seguro de continuar?"))
                return;
            int filaActual = 0;
            filaActual = tblListaPendientes.getSelectedRow();
            VariablesCaja.vNumPedVta = ((String)tblListaPendientes.getValueAt(filaActual, 2)).trim();
            VariablesCaja.vNumSecImpresionComprobantes = Integer.parseInt(((String)tblListaPendientes.getValueAt(filaActual, 6)).trim());
            VariablesCaja.vSecMovCaja = ((String)tblListaPendientes.getValueAt(filaActual, 7)).trim();
            VariablesModuloVentas.vTip_Comp_Ped = ((String)tblListaPendientes.getValueAt(filaActual, 8)).trim();
            VariablesCaja.vValTipoCambioPedido = ((String)tblListaPendientes.getValueAt(filaActual, 9)).trim();
            //RHERRERA: OBTIENE CODIGO DE CONVEIO
            VariablesConvenioBTLMF.vCodConvenio =((String)tblListaPendientes.getValueAt(filaActual, 11)).trim();
            VariablesConvenioBTLMF.vNomConvenio=((String)tblListaPendientes.getValueAt(filaActual, 12)).trim();

            try
            {
                UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                VariablesCaja.vNumCaja = VariablesCaja.vNumCajaImpreso;
                if(!UtilityCaja.validaAgrupacionComprobante(this, tblListaPendientes))
                    return;
                if(!UtilityCaja.existeImpresorasVenta(this, tblListaPendientes))
                    return;
                //UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
                //LLEIVA 01-Abr-2014 Se verifica si el pedido es por convenio
                if("S".equalsIgnoreCase(DBCaja.getIndPedConvenio(VariablesCaja.vNumPedVta)))
                    UtilityConvenioBTLMF.procesoImpresionComprobante(this, null);
                else
                    UtilityCaja.procesoImpresionComprobante(this, tblListaPendientes);
                cargaListaPendientes();
            }
            catch (SQLException sql)
            {
                FarmaUtility.liberarTransaccion();
                log.error("",sql);
                FarmaUtility.showMessage(this, 
                                         "Error al reimprimir pedido.\n" + sql.getMessage(), 
                                         tblListaPendientes);
            }
        }
        catch (Exception e)
        {
            FarmaUtility.liberarTransaccion();
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Error en la Aplicacion al ReImprimir el Pedido.\n" + e.getMessage(), 
                                     tblListaPendientes);
        }
    }
  
  private void configuracionComprobante()
  {
    int indIpValida=0;
    try
    {
      indIpValida =  DBPtoVenta.verificaIPValida();
      if( indIpValida == 0 )
        FarmaUtility.showMessage(this,"La estaci�n actual no se encuentra autorizada para efectuar la operaci�n. ", null);
      else{
    DlgConfiguracionComprobante dlgConfiguracionComprobante = new DlgConfiguracionComprobante(myParentFrame,"",true);
    dlgConfiguracionComprobante.setVisible(true);
    if(FarmaVariables.vAceptar) FarmaVariables.vAceptar = false;
  }
    } catch(SQLException ex)
    {
      log.error("",ex);
      FarmaUtility.showMessage(this,"Error al validar IP de Configuracion de Comprobantes.\n" + ex.getMessage(), null);
      indIpValida=0;
    }
  
  
  
  
  
   
  }
  
}


