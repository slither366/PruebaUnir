package venta.modulo_venta;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.DlgLogin;
import common.FarmaBlinkJLabel;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.DlgFormaPago;
import venta.caja.DlgSeleccionTipoComprobante;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.recaudacion.reference.FacadeRecaudacion;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgResumenPedidoGratuito.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      20.03.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgResumenPedidoGratuito extends JDialog {

  private String fechaPedido = "";
    private static final Logger log = LoggerFactory.getLogger(DlgResumenPedidoGratuito.class);

  /** Almacena el Objeto Frame de la Aplicaci�n - Ventana Principal */
  private Frame myParentFrame;

  /** Almacena el Objeto TableModel de los Productos del Pedido */
  private FarmaTableModel tableModelResumenPedidoGratuito;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JPanel pnlTotalesD = new JPanel();
  private XYLayout xYLayout6 = new XYLayout();
  private JLabel lblTotalD = new JLabel();
  private JLabel lblTotalS = new JLabel();
  private JLabel lblTotalDT = new JLabel();
  private JLabel lblTotalST = new JLabel();
  private JLabel lblRedondeoT = new JLabel();
  private JLabel lblRedondeo = new JLabel();
  private JLabel lblOpciones = new JLabel();
  private JPanel pnlTotalesT = new JPanel();
  private XYLayout xYLayout5 = new XYLayout();
  private JLabel lblDsctoPorc = new JLabel();
  private JLabel lblTotalesT = new JLabel();
  private JLabel lblBrutoT = new JLabel();
  private JLabel lblBruto = new JLabel();
  private JLabel lblDsctoT = new JLabel();
  private JLabel lblDscto = new JLabel();
  private JLabel lblIGVT = new JLabel();
  private JLabel lblIGV = new JLabel();
  private JScrollPane scrProductos = new JScrollPane();
  private JPanel pnlProductos = new JPanel();
  private XYLayout xYLayout2 = new XYLayout();
  private JLabel lblTipoPedido = new FarmaBlinkJLabel();
  private JButton btnRelacionProductos = new JButton();
  private JLabel lblItemsT = new JLabel();
  private JLabel lblItems = new JLabel();
  private JPanel pnlAtencion = new JPanel();
  private XYLayout xYLayout4 = new XYLayout();
  private JLabel lblUltimoPedido = new JLabel();
  private JLabel lblUltimoPedidoT = new JLabel();
  private JLabel lblVendedor = new JLabel();
  private JLabel lblNombreVendedor = new JLabel();
  private JLabel lblTipoCambio = new JLabel();
  private JLabel lblFecha = new JLabel();
  private JLabel lblTipoCambioT = new JLabel();
  private JLabel lblFechaT = new JLabel();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF8 = new JLabelFunction();
  private JTable tblProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JLabelWhite lblCliente_T = new JLabelWhite();
  private JLabelWhite lblCliente = new JLabelWhite();


// **************************************************************************
// Constructores
// **************************************************************************
  /**
  *Constructor
  */
  public DlgResumenPedidoGratuito() {
    this(null, "", false);
  }

  /**
  *Constructor
  *@param parent Objeto Frame de la Aplicaci�n.
  *@param title T�tulo de la Ventana.
  *@param modal Tipo de Ventana.
  */
  public DlgResumenPedidoGratuito(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    myParentFrame = parent;
    try {
      jbInit();
      initialize();
//      lblItems.setText("0");
      lblBruto.setText("0.00");
      lblDscto.setText("0.00");
      lblIGV.setText("0.00");
      lblTotalS.setText("0.00");
      lblTotalD.setText("0.00");
    } catch(Exception e) {
      log.error("",e);
    }
  }

// **************************************************************************
// M�todo "jbInit()"
// **************************************************************************
  /**
  *Implementa la Ventana con todos sus Objetos
  */
  private void jbInit() throws Exception {
    this.setSize(new Dimension(744, 500));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Resumen de Pedido - Distribucion Gratuita");
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
    this.addWindowListener(new java.awt.event.WindowAdapter()
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
    jContentPane.setBackground(Color.white);
    jContentPane.setSize(new Dimension(742, 423));
    pnlTotalesD.setFont(new Font("SansSerif", 0, 12));
    pnlTotalesD.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlTotalesD.setLayout(xYLayout6);
    pnlTotalesD.setBounds(new Rectangle(10, 355, 715, 35));
    pnlTotalesD.setBackground(new Color(43, 141, 39));
    lblTotalD.setText("9,990.00");
    lblTotalD.setFont(new Font("SansSerif", 1, 14));
    lblTotalD.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalD.setForeground(Color.white);
    lblTotalS.setText("99,990.00");
    lblTotalS.setFont(new Font("SansSerif", 1, 14));
    lblTotalS.setForeground(Color.white);
    lblTotalS.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotalDT.setText("US$");
    lblTotalDT.setFont(new Font("SansSerif", 1, 14));
    lblTotalDT.setForeground(Color.white);
    lblTotalST.setText("TOTAL : S/.");
    lblTotalST.setFont(new Font("SansSerif", 1, 14));
    lblTotalST.setForeground(Color.white);
    lblRedondeoT.setText("Redondeo S/.");
    lblRedondeoT.setFont(new Font("SansSerif", 1, 14));
    lblRedondeoT.setForeground(Color.white);
    lblRedondeo.setText("-0.00");
    lblRedondeo.setFont(new Font("SansSerif", 1, 14));
    lblRedondeo.setForeground(Color.white);
    lblOpciones.setText("Opciones :");
    lblOpciones.setFont(new Font("SansSerif", 1, 11));
    lblOpciones.setBounds(new Rectangle(10, 395, 65, 15));
    pnlTotalesT.setFont(new Font("SansSerif", 0, 12));
    pnlTotalesT.setBackground(new Color(255, 130, 14));
    pnlTotalesT.setLayout(xYLayout5);
    pnlTotalesT.setBounds(new Rectangle(10, 330, 715, 25));
    lblDsctoPorc.setText("(00.00%)");
    lblDsctoPorc.setFont(new Font("SansSerif", 1, 12));
    lblDsctoPorc.setForeground(Color.white);
    lblTotalesT.setText("Totales :");
    lblTotalesT.setFont(new Font("SansSerif", 1, 12));
    lblTotalesT.setHorizontalAlignment(SwingConstants.LEFT);
    lblTotalesT.setForeground(Color.white);
    lblBrutoT.setText("Bruto :");
    lblBrutoT.setFont(new Font("SansSerif", 1, 12));
    lblBrutoT.setForeground(Color.white);
    lblBruto.setText("99,990.00");
    lblBruto.setFont(new Font("SansSerif", 1, 12));
    lblBruto.setForeground(Color.white);
    lblBruto.setHorizontalAlignment(SwingConstants.LEFT);
    lblDsctoT.setText("Dscto :");
    lblDsctoT.setFont(new Font("SansSerif", 1, 12));
    lblDsctoT.setForeground(Color.white);
    lblDscto.setText("990.00");
    lblDscto.setFont(new Font("SansSerif", 1, 12));
    lblDscto.setForeground(Color.white);
    lblDscto.setHorizontalAlignment(SwingConstants.LEFT);
    lblIGVT.setText("I.G.V. :");
    lblIGVT.setFont(new Font("SansSerif", 1, 12));
    lblIGVT.setForeground(Color.white);
    lblIGV.setText("9,990.00");
    lblIGV.setFont(new Font("SansSerif", 1, 12));
    lblIGV.setForeground(Color.white);
    lblIGV.setHorizontalAlignment(SwingConstants.LEFT);
    scrProductos.setFont(new Font("SansSerif", 0, 12));
    scrProductos.setBounds(new Rectangle(10, 110, 715, 220));
    scrProductos.setBackground(new Color(255, 130, 14));
    pnlProductos.setFont(new Font("SansSerif", 0, 12));
    pnlProductos.setLayout(xYLayout2);
    pnlProductos.setBackground(new Color(255, 130, 14));
    pnlProductos.setBounds(new Rectangle(10, 85, 715, 25));
    lblTipoPedido.setFont(new Font("SansSerif", 1, 11));
    lblTipoPedido.setForeground(Color.white);
    btnRelacionProductos.setText("Relacion de Productos :");
    btnRelacionProductos.setFont(new Font("SansSerif", 1, 11));
    btnRelacionProductos.setForeground(Color.white);
    btnRelacionProductos.setBackground(new Color(255, 130, 14));
    btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnRelacionProductos.setHorizontalAlignment(SwingConstants.LEFT);
    btnRelacionProductos.setRequestFocusEnabled(false);
    btnRelacionProductos.setMnemonic('r');
    btnRelacionProductos.setBorderPainted(false);
    btnRelacionProductos.setContentAreaFilled(false);
    btnRelacionProductos.setDefaultCapable(false);
    btnRelacionProductos.setFocusPainted(false);
    btnRelacionProductos.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnRelacionProductos_keyPressed(e);
        }
      });
    lblItemsT.setText("items");
    lblItemsT.setFont(new Font("SansSerif", 1, 11));
    lblItemsT.setForeground(Color.white);
    lblItems.setText("0");
    lblItems.setFont(new Font("SansSerif", 1, 11));
    lblItems.setForeground(Color.white);
    lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
    pnlAtencion.setFont(new Font("SansSerif", 0, 11));
    pnlAtencion.setLayout(xYLayout4);
    pnlAtencion.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlAtencion.setBackground(new Color(43, 141, 39));
    pnlAtencion.setBounds(new Rectangle(10, 45, 715, 35));
    lblUltimoPedido.setFont(new Font("SansSerif", 0, 11));
    lblUltimoPedido.setForeground(Color.white);
    lblUltimoPedidoT.setText("Ult. Pedido :");
    lblUltimoPedidoT.setFont(new Font("SansSerif", 0, 11));
    lblUltimoPedidoT.setForeground(Color.white);
    lblVendedor.setText("Vendedor :");
    lblVendedor.setFont(new Font("SansSerif", 0, 11));
    lblVendedor.setForeground(Color.white);
    lblNombreVendedor.setFont(new Font("SansSerif", 1, 11));
    lblNombreVendedor.setForeground(Color.white);
    lblTipoCambio.setFont(new Font("SansSerif", 1, 11));
    lblTipoCambio.setForeground(Color.white);
    lblFecha.setFont(new Font("SansSerif", 1, 11));
    lblFecha.setForeground(Color.white);
    lblTipoCambioT.setText("Tipo Cambio :");
    lblTipoCambioT.setFont(new Font("SansSerif", 0, 11));
    lblTipoCambioT.setForeground(Color.white);
    lblFechaT.setText("Fecha :");
    lblFechaT.setFont(new Font("SansSerif", 0, 11));
    lblFechaT.setForeground(Color.white);
    lblF3.setText("[ F3 ]  Insertar Producto");
    lblF3.setBounds(new Rectangle(290, 420, 145, 20));
    lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
    lblEnter.setBounds(new Rectangle(10, 445, 185, 20));
    lblF5.setText("[ F5 ]  Borrar Producto");
    lblF5.setBounds(new Rectangle(440, 420, 140, 20));
    lblF1.setText("[ F1 ]  Generar Boleta");
    lblF1.setBounds(new Rectangle(10, 420, 135, 20));
    lblF2.setText("[ F4 ]  Generar Factura");
    lblF2.setBounds(new Rectangle(150, 420, 135, 20));
    lblF8.setText("[ F8 ]  Impulso");
    lblF8.setBounds(new Rectangle(585, 420, 140, 20));
    tblProductos.setFont(new Font("SansSerif", 0, 12));
    tblProductos.addKeyListener(new java.awt.event.KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblProductos_keyPressed(e);
        }
      });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(640, 445, 85, 20));
    pnlTitle1.setBounds(new Rectangle(10, 10, 715, 30));
    lblCliente_T.setText("Cliente:");
    lblCliente_T.setBounds(new Rectangle(10, 5, 55, 20));
    lblCliente.setBounds(new Rectangle(60, 5, 340, 20));
    pnlTotalesD.add(lblTotalD, new XYConstraints(615, 5, 80, 20));
    pnlTotalesD.add(lblTotalS, new XYConstraints(460, 5, 95, 20));
    pnlTotalesD.add(lblTotalDT, new XYConstraints(575, 5, 35, 20));
    pnlTotalesD.add(lblTotalST, new XYConstraints(375, 5, 80, 20));
    pnlTotalesD.add(lblRedondeoT, new XYConstraints(10, 5, 110, 20));
    pnlTotalesD.add(lblRedondeo, new XYConstraints(125, 5, 65, 20));
    pnlTotalesT.add(lblDsctoPorc, new XYConstraints(380, 5, 65, 15));
    pnlTotalesT.add(lblTotalesT, new XYConstraints(10, 5, 65, 15));
    pnlTotalesT.add(lblBrutoT, new XYConstraints(85, 5, 50, 15));
    pnlTotalesT.add(lblBruto, new XYConstraints(145, 5, 75, 15));
    pnlTotalesT.add(lblDsctoT, new XYConstraints(240, 5, 50, 15));
    pnlTotalesT.add(lblDscto, new XYConstraints(295, 5, 70, 15));
    pnlTotalesT.add(lblIGVT, new XYConstraints(485, 5, 45, 15));
    pnlTotalesT.add(lblIGV, new XYConstraints(540, 5, 95, 15));
    scrProductos.getViewport();
    pnlProductos.add(lblTipoPedido, new XYConstraints(325, 5, 320, 15));
    pnlProductos.add(btnRelacionProductos, new XYConstraints(10, 5, 145, 15));
    pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
    pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
    pnlAtencion.add(lblUltimoPedido, new XYConstraints(655, 10, 40, 15));
    pnlAtencion.add(lblUltimoPedidoT, new XYConstraints(585, 10, 70, 15));
    pnlAtencion.add(lblVendedor, new XYConstraints(245, 10, 70, 15));
    pnlAtencion.add(lblNombreVendedor, new XYConstraints(315, 10, 245, 15));
    pnlAtencion.add(lblTipoCambio, new XYConstraints(205, 10, 40, 15));
    pnlAtencion.add(lblFecha, new XYConstraints(60, 10, 70, 15));
    pnlAtencion.add(lblTipoCambioT, new XYConstraints(130, 10, 80, 15));
    pnlAtencion.add(lblFechaT, new XYConstraints(10, 10, 50, 15));
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    pnlTitle1.add(lblCliente, null);
    pnlTitle1.add(lblCliente_T, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(pnlTotalesD, null);
    jContentPane.add(lblOpciones, null);
    jContentPane.add(pnlTotalesT, null);
    scrProductos.getViewport().add(tblProductos, null);
    jContentPane.add(scrProductos, null);
    jContentPane.add(pnlProductos, null);
    jContentPane.add(pnlAtencion, null);
    jContentPane.add(lblF3, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblF5, null);
    jContentPane.add(lblF1, null);
    jContentPane.add(lblF2, null);
    jContentPane.add(lblF8, null);
    //this.getContentPane().add(jContentPane, null);
  }
  
// **************************************************************************
// M�todo "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTableResumenPedido();
    limpiaValoresPedido();
    FarmaVariables.vAceptar = false;
  }
  
// **************************************************************************
// M�todos de inicializaci�n
// **************************************************************************
  private void initTableResumenPedido()
  {
    tableModelResumenPedidoGratuito = new FarmaTableModel(ConstantsModuloVenta.columnsListaResumenPedido, ConstantsModuloVenta.defaultValuesListaResumenPedido,0);
    FarmaUtility.initSimpleList(tblProductos,tableModelResumenPedidoGratuito, ConstantsModuloVenta.columnsListaResumenPedido);
  }

  private void cargaLogin()
  {
    DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
    dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
    dlgLogin.setVisible(true);
    if ( FarmaVariables.vAceptar ) {
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) &&
           !UtilityCaja.existeCajaUsuarioImpresora(this, null) ) {
        cerrarVentana(false);
      } else {
        FarmaVariables.dlgLogin = dlgLogin;
        FarmaVariables.vAceptar = false;
        agregarProducto();
      }
    } else  cerrarVentana(false);
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
    private void this_windowOpened(WindowEvent e)
    {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if(FarmaVariables.vTipCambio==0)
        {   FarmaUtility.showMessage(this, 
                        "ATENCI�N: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acci�n", 
                        null);
            cerrarVentana(false);
        }
        else
        {
            FarmaUtility.centrarVentana(this);
            obtieneInfoPedido();
            lblFecha.setText(fechaPedido);
            lblTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
            cargaLogin();
            lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " +
                                      FarmaVariables.vPatUsu.trim() + " " +
                                      FarmaVariables.vMatUsu.trim());
            colocaUltimoPedidoDiarioVendedor();
            FarmaUtility.moveFocus(tblProductos);
        }
    }

  private void tblProductos_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void btnRelacionProductos_keyPressed(KeyEvent e)
  {
    FarmaUtility.moveFocus(tblProductos);    
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      muestraIngresoCantidad();
    } else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      muestraDetalleProducto();
    } else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
      eliminaProductoResumenPedido();
    } else if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      agregarProducto();
    } else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
      grabarPedidoVenta(ConstantsModuloVenta.TIPO_COMP_FACTURA);
    } else if(venta.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      grabarPedidoVenta(ConstantsModuloVenta.TIPO_COMP_BOLETA);
    } else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
      if(UtilityModuloVenta.evaluaPedidoDelivery(this, tblProductos, VariablesModuloVentas.vArrayList_ResumenPedido))
        evaluaTitulo();
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cancelaOperacion();
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

// **************************************************************************
// Metodos de l�gica de negocio
// **************************************************************************  
  private void agregarProducto() {
    DlgListaProductosGratuitos dlgListaProductosGratuitos = new DlgListaProductosGratuitos(myParentFrame,"",true);
    dlgListaProductosGratuitos.setVisible(true);
    operaResumenPedido();
    FarmaVariables.vAceptar = false;
  }
  
  private void operaResumenPedido() {
    // borramos todo de la tabla
    while(tableModelResumenPedidoGratuito.getRowCount()>0)
      tableModelResumenPedidoGratuito.deleteRow(0);
    tableModelResumenPedidoGratuito.fireTableDataChanged();
    tblProductos.repaint();
    // cargamos los productos desde el ArrayList de Productos
    for(int i=0; i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); i++) {
      tableModelResumenPedidoGratuito.insertRow((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i));
      tableModelResumenPedidoGratuito.fireTableDataChanged();
    }
    tblProductos.repaint();
    calculaTotalesPedido();
    FarmaUtility.setearPrimerRegistro(tblProductos,null,0);
  }
  
  private void muestraDetalleProducto()
  {
    if(tblProductos.getRowCount() == 0) return;
        VariablesModuloVentas.vCod_Prod = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),0))).trim();
        VariablesModuloVentas.vDesc_Prod = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),1))).trim();
        VariablesModuloVentas.vNom_Lab = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(),9))).trim();
    DlgDetalleProducto dlgDetalleProducto = new DlgDetalleProducto(myParentFrame,"",true);
    dlgDetalleProducto.setVisible(true);
  }
  
  private void cancelaOperacion() {
    String codProd = "";
    String cantidad = "";
    for(int i=0; i<tblProductos.getRowCount(); i++){
      codProd = ((String)(tblProductos.getValueAt(i,0))).trim();
      cantidad = ((String)(tblProductos.getValueAt(i,4))).trim();
      if(!actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad), ConstantsModuloVenta.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad)))
        return;
    }
    inicializaArrayList();
    cerrarVentana(false);
  }
  
  private void inicializaArrayList() {
        VariablesModuloVentas.vArrayList_PedidoVenta = new ArrayList();
        VariablesModuloVentas.vArrayList_ResumenPedido = new ArrayList();
  }
  
  private boolean actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) {
    try {
            DBModuloVenta.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
      DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesModuloVentas.vVal_Frac),ConstantsPtoVenta.MODULO_VENTAS);
      FarmaUtility.aceptarTransaccion();
      return true;
    } catch (SQLException sql) {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\nPonganse en contacto con el area de Sistemas.\nError - " + sql.getMessage(), tblProductos);
      return false;
    }
  }
  
  private void calculaTotalesPedido(){
    if (tblProductos.getRowCount()<=0){
      lblBruto.setText("0.00");
      lblDscto.setText("0.00");
      lblDsctoPorc.setText("(0.00%)");
      lblIGV.setText("0.00");
      lblTotalS.setText("0.00");
      lblTotalD.setText("0.00");
      lblRedondeo.setText("0.00");
      lblItems.setText("0");
      return;
    }
    double totalBruto = 0.00;
    double totalDscto = 0.00;
    double totalIGV = 0.00;
    double totalNeto = 0.00;
    double redondeo = 0.00;
    int cantidad = 0;
    for (int i=0; i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); i++) {
      log.debug("", VariablesModuloVentas.vArrayList_ResumenPedido.get(i));
      cantidad = Integer.parseInt((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i)).get(4));
      //totalBruto += FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(3)) * cantidad;
      //totalIGV   += FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(12));
      //totalNeto += FarmaUtility.getDecimalNumber((String)((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i)).get(7));
    }
    //totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2));
    //totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2));
    //totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2));
    //totalDscto = (totalBruto - totalNeto);
    //totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2));
    log.debug("********************************");
    log.debug("totalBruto 2 : " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto,2)));
    log.debug("totalNeto 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto,2)));
    log.debug("totalDscto 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto,2)));
    log.debug("totalIGV 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2)));
    //redondeo = FarmaUtility.getNuevoRedondeo(totalNeto);
    log.debug("redondeo : " + redondeo);
    //totalNeto = totalNeto + redondeo;
    //log.debug("totalNeto Neto : " + FarmaUtility.formatNumber(totalNeto));
    lblBruto.setText(FarmaUtility.formatNumber(totalBruto));
    lblDscto.setText(FarmaUtility.formatNumber(totalDscto));
    lblDsctoPorc.setText("("+FarmaUtility.formatNumber(totalDscto*100/totalBruto)+"%)");
    lblIGV.setText(FarmaUtility.formatNumber(totalIGV));
    lblTotalS.setText(FarmaUtility.formatNumber(totalNeto));//colocando total 0.00
    lblTotalD.setText(FarmaUtility.formatNumber(totalNeto/FarmaVariables.vTipCambio));//obtener el tipo de cambio del dia
    lblRedondeo.setText(FarmaUtility.formatNumber(redondeo));
    lblItems.setText(String.valueOf(VariablesModuloVentas.vArrayList_ResumenPedido.size()));
  }
  
  private void muestraIngresoCantidad()
  {
   
  }
  
  private void seleccionaProducto(int pFila)
  {
        VariablesModuloVentas.vTotalPrecVtaProd = 0.00;//colocando subtotal en 0.00
    int cantIngresada = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada));
    int cantIngresada_old = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada_Temp));
    if ( cantIngresada_old > cantIngresada ) {
      if(!actualizaStkComprometidoProd(VariablesModuloVentas.vCod_Prod,(cantIngresada_old-cantIngresada), ConstantsModuloVenta.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR, cantIngresada))
        return;
    } else if ( cantIngresada_old<cantIngresada ) {
      if(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vStk_Prod)) == 0)
        FarmaUtility.showMessage(this, "No existe Stock disponible. Verifique!!!", tblProductos);
      else {
        if(!actualizaStkComprometidoProd(VariablesModuloVentas.vCod_Prod,(cantIngresada-cantIngresada_old), ConstantsModuloVenta.INDICADOR_A, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR, cantIngresada))
          return;
      }
    }
    //liberando registros
    FarmaUtility.liberarTransaccion();
    operaProductoEnJTable(pFila);
    operaProductoEnArrayList(pFila);
    calculaTotalesPedido();
  }
  
  private void operaProductoEnJTable(int pFila)
  {
    tblProductos.setValueAt(VariablesModuloVentas.vCant_Ingresada, pFila, 4);//cantidad ingresada
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vPorc_Dcto_1),2), pFila, 5);//PORC DCTO 1
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta),3), pFila, 6);//PRECIO DE VENTA
    //log.debug(" FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2) " + FarmaUtility.formatNumber(VariablesVentas.vTotalPrecVtaProd,2));
    tblProductos.setValueAt(FarmaUtility.formatNumber(VariablesModuloVentas.vTotalPrecVtaProd,2), pFila, 7);//Total Precio Vta
    String valIgv = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta) / ( 1 + (FarmaUtility.getDecimalNumber(VariablesModuloVentas.vPorc_Igv_Prod) / 100)))) * FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada));
    //log.debug(valIgv);
        VariablesModuloVentas.vVal_Igv_Prod = valIgv;
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Igv_Prod),2), pFila, 12);//Total Igv Producto
    tblProductos.repaint();
  }
  
  private void operaProductoEnArrayList(int pFila)
  {
    ((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).set(4, VariablesModuloVentas.vCant_Ingresada);
    ((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).set(5, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vPorc_Dcto_1),2));
    ((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).set(6, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta),3));
    ((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).set(7, FarmaUtility.formatNumber(VariablesModuloVentas.vTotalPrecVtaProd,2));
    ((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).set(12, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Igv_Prod),2));
  }
  
  private void eliminaProductoResumenPedido()
  {
    int filaActual = tblProductos.getSelectedRow();
    if (filaActual >= 0) {
      if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, "Seguro de eliminar el Producto del Pedido?")){
        String codProd = ((String)(tblProductos.getValueAt(filaActual,0))).trim();
        String cantidad = ((String)(tblProductos.getValueAt(filaActual,4))).trim();
        if(!actualizaStkComprometidoProd(codProd,Integer.parseInt(cantidad), ConstantsModuloVenta.INDICADOR_D, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad)))
          return;
                VariablesModuloVentas.vArrayList_ResumenPedido.remove(filaActual);
        tableModelResumenPedidoGratuito.deleteRow(filaActual);
        tblProductos.repaint();
        calculaTotalesPedido();
        //lblItems.setText(String.valueOf(VariablesVentas.vArrayList_ResumenPedido.size()));
        if (tableModelResumenPedidoGratuito.getRowCount() > 0) {
          if (filaActual>0) filaActual--;
          FarmaGridUtils.showCell(tblProductos, filaActual,0);
        }
      }
    } else {
      FarmaUtility.showMessage(this, "Debe seleccionar un Producto", tblProductos);
    }
  }
  
  private void obtieneInfoPedido()
  {
    String fechaTipoCambio = ""; //tipo cambio de la fecha actual
    try
    {
      fechaPedido = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        (new FacadeRecaudacion()).obtenerTipoCambio();
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al obtener Tipo de Cambio del Dia. \n " + sql.getMessage(), null);
    }
  }
  
  private void grabarPedidoVenta(String pTipComp) {
    if(VariablesModuloVentas.vArrayList_ResumenPedido.size() <= 0) return;
    try
    {
            VariablesModuloVentas.vTip_Comp_Ped = pTipComp;
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ||
         ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL) && VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA) ) )
      {
        muestraPedidoDetallado();
        if(!FarmaVariables.vAceptar)
        {
          FarmaUtility.liberarTransaccion();
          return;
        } else
        {
          if( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
              !componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,"Esta seguro de realizar el pedido?") )
          {
            FarmaUtility.liberarTransaccion();
            return;
          }
        }
      }
            VariablesModuloVentas.vNum_Ped_Vta = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO,10);
            VariablesModuloVentas.vNum_Ped_Diario = obtieneNumeroPedidoDiario();
      log.debug("VariablesVentas.vNum_Ped_Vta : " + VariablesModuloVentas.vNum_Ped_Vta);
      log.debug("VariablesVentas.vNum_Ped_Diario : " + VariablesModuloVentas.vNum_Ped_Diario);
      if(VariablesModuloVentas.vNum_Ped_Vta.trim().length() == 0 || VariablesModuloVentas.vNum_Ped_Diario.trim().length() == 0)
        throw new SQLException("Error al obtener Numero de Pedido","Error",9001);
      //coloca valores de variables de cabecera de pedido
      guardaVariablesPedidoCabecera();
      log.debug("GUARDO VALORES DE PEDIDO CABECERA");
            //DBModuloVenta.grabarCabeceraPedido();
      log.debug("GRABO CABECERA DE PEDIDO");
      int cont = 0;
      for(int i=0; i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); i++)
      {
        cont = i;
        grabarDetallePedido(pTipComp, i);
      }
      log.debug("GRABO DETALLE DE PEDIDO : " + (cont+1) + " PRODUCTOS");
      FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO);
      // actualizaci�n de los Stock's.
      /*for(int i=0; i<tblProductos.getRowCount(); i++) {
        DBVentas.actualizaStkProd_Fis_Comp((String)tblProductos.getValueAt(i,0),
                                           (String)tblProductos.getValueAt(i,4));
      }*/
      //log.debug("ACTUALIZO STOCK PRODUCTOS");
      for(int i=0; i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); i++)
      {
        String codProd = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i)).get(0)).trim();
        DBPtoVenta.ejecutaRespaldoStock(codProd, VariablesModuloVentas.vNum_Ped_Vta, ConstantsPtoVenta.TIP_OPERACION_RESPALDO_ACTUALIZAR_PEDIDO, 0, 0,ConstantsPtoVenta.MODULO_VENTAS);
      }
      log.debug("ACTUALIZO RESPALDO NUMERO PEDIDO");
      FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
      FarmaUtility.aceptarTransaccion();
      if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) ) {
        VariablesCaja.vNumPedPendiente = VariablesModuloVentas.vNum_Ped_Diario;
        muestraCobroPedido();
      } else  muestraPedidoRapido();
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error en Base de Datos al grabar el pedido.\n" + sql.getMessage(),tblProductos);
    } catch(Exception exc)
    {
      FarmaUtility.liberarTransaccion();
        log.error("",exc);
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar el pedido.\n" + exc.getMessage(),tblProductos);
    }
  }
  
  private void grabarDetallePedido(String pTiComprobante, int pFila) throws Exception {
        VariablesModuloVentas.vSec_Ped_Vta_Det = String.valueOf(pFila + 1);
        VariablesModuloVentas.vCod_Prod = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(0)).trim();
        VariablesModuloVentas.vCant_Ingresada = String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(4)).trim())));
        VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(6)).trim();
        VariablesModuloVentas.vTotalPrecVtaProd = FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(7)).trim());
        VariablesModuloVentas.vPorc_Dcto_1 = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(5)).trim()));
        VariablesModuloVentas.vPorc_Dcto_Total = VariablesModuloVentas.vPorc_Dcto_1;//cuando es pedido normal, el descuento total siempre es el descuento 1
        VariablesModuloVentas.vVal_Total_Bono = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(8)).trim()));
        VariablesModuloVentas.vVal_Frac = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(10)).trim();
        VariablesModuloVentas.vEst_Ped_Vta_Det = ConstantsModuloVenta.ESTADO_PEDIDO_DETALLE_ACTIVO;
        VariablesModuloVentas.vSec_Usu_Local = FarmaVariables.vNuSecUsu;
        VariablesModuloVentas.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(3)).trim()));
        VariablesModuloVentas.vVal_Igv_Prod = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(11)).trim();
        VariablesModuloVentas.vUnid_Vta = ((String)((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(pFila)).get(2)).trim();
        DBModuloVenta.grabarDetallePedido();
  }
  
  private String obtieneNumeroPedidoDiario() throws SQLException {

    String feModNumeracion = DBModuloVenta.obtieneFecModNumeraPed();
    String feHoyDia = "";
    String numPedDiario = "";
    if ( !(feModNumeracion.trim().length()>0) )  throw new SQLException("Ultima Fecha Modificacion de Numeraci�n Diaria del Pedido NO ES VALIDA !!!","Error",0001);
    else {
      feHoyDia = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      feHoyDia = feHoyDia.trim().substring(0,2);
      feModNumeracion = feModNumeracion.trim().substring(0,2);
      if ( Integer.parseInt(feHoyDia)!=Integer.parseInt(feModNumeracion) ) {
        FarmaSearch.inicializaNumeracionNoCommit(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
        numPedDiario = "0001";
      } else {
        // Obtiene el Numero de Atencion del D�a y hace SELECT FOR UPDATE.
        numPedDiario = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO,4);
      }
    }
    return numPedDiario;
  }
  
  private void guardaVariablesPedidoCabecera()
  {
        VariablesModuloVentas.vIndDistrGratuita = FarmaConstants.INDICADOR_S;
        VariablesModuloVentas.vVal_Bruto_Ped = lblBruto.getText().trim();
        VariablesModuloVentas.vVal_Neto_Ped = lblTotalS.getText().trim();
        VariablesModuloVentas.vVal_Redondeo_Ped = lblRedondeo.getText().trim();
        VariablesModuloVentas.vVal_Igv_Ped = lblIGV.getText().trim();
        VariablesModuloVentas.vVal_Dcto_Ped = lblDscto.getText().trim();
    if(VariablesModuloVentas.vEsPedidoDelivery)
            VariablesModuloVentas.vTip_Ped_Vta = ConstantsModuloVenta.TIPO_PEDIDO_DELIVERY;//verificar q tipo de pedido es...
    else
            VariablesModuloVentas.vTip_Ped_Vta = ConstantsModuloVenta.TIPO_PEDIDO_MESON;//verificar q tipo de pedido es...
        VariablesModuloVentas.vCant_Items_Ped = lblItems.getText().trim();
        VariablesModuloVentas.vEst_Ped_Vta_Cab = ConstantsModuloVenta.ESTADO_PEDIDO_PENDIENTE;
  }
  
  private void muestraPedidoRapido() {
    DlgNumeroPedidoGenerado dlgNumeroPedidoGenerado = new DlgNumeroPedidoGenerado(myParentFrame,"",true);
    dlgNumeroPedidoGenerado.setVisible(true);
    if ( FarmaVariables.vAceptar ){
      FarmaVariables.vAceptar = false;
      cerrarVentana(true);
    }
  }
  
  private void limpiaValoresPedido()
  {

        VariablesModuloVentas.vCod_Prod = "";
        VariablesModuloVentas.vDesc_Prod = "";
        VariablesModuloVentas.vNom_Lab = "";
        VariablesModuloVentas.vUnid_Vta = "";
        VariablesModuloVentas.vVal_Prec_Vta = "";
        VariablesModuloVentas.vPorc_Dcto_1 = "";
        VariablesModuloVentas.vVal_Bono = "";
        VariablesModuloVentas.vDesc_Acc_Terap = "";
        VariablesModuloVentas.vStk_Prod = "";
        VariablesModuloVentas.vStk_Prod_Fecha_Actual = "";
        VariablesModuloVentas.vVal_Frac = "";
        VariablesModuloVentas.vVal_Prec_Lista = "";
        VariablesModuloVentas.vVal_Igv_Prod = "";
        VariablesModuloVentas.vPorc_Igv_Prod = "";
        VariablesModuloVentas.vEst_Ped_Vta_Cab = "";

        VariablesModuloVentas.vCant_Ingresada = "";
        VariablesModuloVentas.vCant_Ingresada_Temp = "";
        VariablesModuloVentas.vTotalPrecVtaProd = 0;

        VariablesModuloVentas.vArrayList_PedidoVenta = new ArrayList();
        VariablesModuloVentas.vArrayList_ResumenPedido = new ArrayList();

        VariablesModuloVentas.vNum_Ped_Vta = "";
        VariablesModuloVentas.vVal_Bruto_Ped = "";
        VariablesModuloVentas.vVal_Neto_Ped = "";
        VariablesModuloVentas.vVal_Redondeo_Ped = "";
        VariablesModuloVentas.vVal_Igv_Ped = "";
        VariablesModuloVentas.vVal_Dcto_Ped = "";

        VariablesModuloVentas.vNom_Cli_Ped = "";
        VariablesModuloVentas.vDir_Cli_Ped = "";
        VariablesModuloVentas.vRuc_Cli_Ped = "";
        VariablesModuloVentas.vTip_Comp_Ped = "";
        VariablesModuloVentas.vCant_Items_Ped = "";
        VariablesModuloVentas.vNum_Ped_Diario = "";
        VariablesModuloVentas.vTip_Ped_Vta = "";
        VariablesModuloVentas.vCod_Cli_Local = "";
        VariablesModuloVentas.vSec_Ped_Vta_Det = "";
        VariablesModuloVentas.vPorc_Dcto_Total = "";
        VariablesModuloVentas.vEst_Ped_Vta_Det = "";
        VariablesModuloVentas.vVal_Total_Bono = "";
        VariablesModuloVentas.vSec_Usu_Local = "";

        VariablesModuloVentas.vEsPedidoDelivery = false;
        VariablesModuloVentas.vIngresaCant_ResumenPed = false;
  }
  
  private void colocaUltimoPedidoDiarioVendedor()
  {
    String pedDiario = "";
    try
    {
      pedDiario = DBModuloVenta.obtieneUltimoPedidoDiario();
      if(pedDiario.equalsIgnoreCase("0000")) pedDiario = "----";
      lblUltimoPedido.setText(pedDiario);
    } catch(SQLException sql)
    {
      log.error("",sql);
      lblUltimoPedido.setText("----");
      FarmaUtility.showMessage(this,"Error al obtener el ultimo pedido diario del vendedor. \n" + sql.getMessage(),tblProductos);
    }
  }
  
  private void muestraPedidoDetallado()
  {
    DlgSeleccionTipoComprobante dlgSeleccionTipoComprobante = new DlgSeleccionTipoComprobante(myParentFrame,"",true);
    dlgSeleccionTipoComprobante.setVisible(true);
  }

  private void muestraCobroPedido()
  {
    DlgFormaPago dlgFormaPago = new DlgFormaPago(myParentFrame,"",true);
    dlgFormaPago.setIndPedirLogueo(false);
    dlgFormaPago.setIndPantallaCerrarAnularPed(true);
    dlgFormaPago.setIndPantallaCerrarCobrarPed(true);
    dlgFormaPago.setVisible(true);
    if ( FarmaVariables.vAceptar ){
      FarmaVariables.vAceptar = false;
      cerrarVentana(true);
    } 
  }

  private void evaluaTitulo()
  {
    if(VariablesModuloVentas.vEsPedidoDelivery)
      this.setTitle("Resumen de Pedido - Distribucion Gratuita - Pedido Delivery");
    else
      this.setTitle("Resumen de Pedido - Distribucion Gratuita");
  }

}