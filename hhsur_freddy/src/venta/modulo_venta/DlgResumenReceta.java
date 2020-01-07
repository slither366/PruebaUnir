package venta.modulo_venta;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;

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

import common.FarmaBlinkJLabel;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.recaudacion.reference.FacadeRecaudacion;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloReceta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgResumenReceta.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS  06.12.2006  Creaci�n
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgResumenReceta
  extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgResumenReceta.class);

  private String fechaPedido = "";

  /**Almacena el Objeto Frame de la Aplicaci�n - Ventana Principal
   */
  private Frame myParentFrame;

  /**Almacena el Objeto TableModel de los Productos del Pedido
   */
  private FarmaTableModel tableModelResumenReceta;

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
  private JLabel lblVendedor = new JLabel();
  private JLabel lblNombreVendedor = new JLabel();
  private JLabel lblTipoCambio = new JLabel();
  private JLabel lblFecha = new JLabel();
  private JLabel lblTipoCambioT = new JLabel();
  private JLabel lblFechaT = new JLabel();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF8 = new JLabelFunction();
  private JTable tblProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JLabelFunction lblF11 = new JLabelFunction();


  // **************************************************************************
  // Constructores
  // **************************************************************************

  /**
   * Constructor
   */
  public DlgResumenReceta()
  {
    this(null, "", false);
  }

  /**
   * Constructor
   * @param <b>parent</b> Objeto Frame de la Aplicaci�n.
   * @param <b>title</b> T�tulo de la Ventana.
   * @param <b>modal</b> Tipo de Ventana.
   */
  public DlgResumenReceta(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      //      lblItems.setText("0");
      lblBruto.setText("0.00");
      lblDscto.setText("0.00");
      lblIGV.setText("0.00");
      lblTotalS.setText("0.00");
      lblTotalD.setText("0.00");
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }

  // **************************************************************************
  // M�todo "jbInit()"
  // **************************************************************************

  /**
   * Implementa la Ventana con todos sus Objetos
   */
  private void jbInit()
    throws Exception
  {
    this.setSize(new Dimension(744, 505));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Resumen de Receta");
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    lblRedondeoT.setText("Red. S/.");
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
    btnRelacionProductos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 
                                                                   0));
    btnRelacionProductos.setHorizontalAlignment(SwingConstants.LEFT);
    btnRelacionProductos.setRequestFocusEnabled(false);
    btnRelacionProductos.setMnemonic('r');
    btnRelacionProductos.setBorderPainted(false);
    btnRelacionProductos.setContentAreaFilled(false);
    btnRelacionProductos.setDefaultCapable(false);
    btnRelacionProductos.setFocusPainted(false);
    btnRelacionProductos.addKeyListener(new KeyAdapter()
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
    lblF3.setBounds(new Rectangle(200, 420, 145, 20));
    lblEnter.setText("[ ENTER ]  Cambiar Cantidad");
    lblEnter.setBounds(new Rectangle(10, 420, 185, 20));
    lblF5.setText("[ F5 ]  Borrar Producto");
    lblF5.setBounds(new Rectangle(350, 420, 140, 20));
    lblF8.setText("[ F8 ]  Impulso");
    lblF8.setBounds(new Rectangle(495, 420, 140, 20));
    tblProductos.setFont(new Font("SansSerif", 0, 12));
    tblProductos.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            tblProductos_keyPressed(e);
          }
        });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(640, 445, 85, 20));
    pnlTitle1.setBounds(new Rectangle(10, 10, 715, 30));
    lblF11.setBounds(new Rectangle(520, 445, 117, 20));
    lblF11.setText("[ F11 ] Aceptar");
    pnlTotalesD.add(lblTotalD, new XYConstraints(615, 5, 80, 20));
    pnlTotalesD.add(lblTotalS, new XYConstraints(460, 5, 95, 20));
    pnlTotalesD.add(lblTotalDT, new XYConstraints(575, 5, 35, 20));
    pnlTotalesD.add(lblTotalST, new XYConstraints(375, 5, 80, 20));
    pnlTotalesD.add(lblRedondeoT, new XYConstraints(10, 5, 70, 20));
    pnlTotalesD.add(lblRedondeo, new XYConstraints(80, 5, 65, 20));
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
    pnlProductos.add(btnRelacionProductos, 
                     new XYConstraints(10, 5, 145, 15));
    pnlProductos.add(lblItemsT, new XYConstraints(185, 5, 40, 15));
    pnlProductos.add(lblItems, new XYConstraints(150, 5, 30, 15));
    pnlAtencion.add(lblVendedor, new XYConstraints(245, 10, 70, 15));
    pnlAtencion.add(lblNombreVendedor, 
                    new XYConstraints(315, 10, 245, 15));
    pnlAtencion.add(lblTipoCambio, new XYConstraints(205, 10, 40, 15));
    pnlAtencion.add(lblFecha, new XYConstraints(60, 10, 70, 15));
    pnlAtencion.add(lblTipoCambioT, new XYConstraints(130, 10, 80, 15));
    pnlAtencion.add(lblFechaT, new XYConstraints(10, 10, 50, 15));
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    jContentPane.add(lblF11, null);
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
    jContentPane.add(lblF8, null);
    //this.getContentPane().add(jContentPane, null);
  }

  // **************************************************************************
  // M�todo "initialize()"
  // **************************************************************************

  private void initialize()
  {
    initTableResumenPedido();
    FarmaVariables.vAceptar = false;
    if(!VariablesModuloReceta.vVerReceta)
    {
      limpiaValoresPedido();
    }
  }

  // **************************************************************************
  // M�todos de inicializaci�n
  // **************************************************************************

  private void initTableResumenPedido()
  {
    tableModelResumenReceta = new FarmaTableModel(ConstantsModuloVenta.columnsListaResumenPedido, ConstantsModuloVenta.defaultValuesListaResumenPedido, 0);FarmaUtility.initSimpleList(tblProductos, tableModelResumenReceta, ConstantsModuloVenta.columnsListaResumenPedido);
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
            FarmaUtility.moveFocus(tblProductos);
            FarmaUtility.centrarVentana(this);
            obtieneInfoPedido();
            lblFecha.setText(fechaPedido);
            lblTipoCambio.setText(FarmaUtility.formatNumber(FarmaVariables.vTipCambio));
            lblNombreVendedor.setText(FarmaVariables.vNomUsu.trim() + " " + FarmaVariables.vPatUsu.trim() + " " +FarmaVariables.vMatUsu.trim());
            if(!VariablesModuloReceta.vVerReceta)
            {   agregarProducto();
            }
            else
            {   operaResumenPedido();
            }
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
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  // **************************************************************************
  // Metodos auxiliares de eventos
  // **************************************************************************

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if(!VariablesModuloReceta.vVerReceta)
        muestraIngresoCantidad();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F8)
    {
      muestraDetalleProducto();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(!VariablesModuloReceta.vVerReceta)
        eliminaProductoResumenPedido();
    }
    else if (e.getKeyCode() == KeyEvent.VK_F3)
    {
      if(!VariablesModuloReceta.vVerReceta)
        agregarProducto();
    }
    else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(!VariablesModuloReceta.vVerReceta)
      {
        if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                           "Est� seguro de grabar la Receta?"))
          grabarReceta();
      }
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      if(!VariablesModuloReceta.vVerReceta)
      {
        if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                           "Est� seguro que Desea salir de la Receta?"))
          cancelaOperacion();
      }else
      {
        cerrarVentana(false);
      }
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

  private void anulaDataPrueba()
  {
    tableModelResumenReceta.clearTable();
    lblBruto.setText("0.00");
    lblDscto.setText("0.00");
    lblDsctoPorc.setText("0.00");
    lblIGV.setText("0.00");
    lblTotalS.setText("0.00");
    lblTotalD.setText("0.00");
    lblItems.setText("0");
  }

  private void agregarProducto()
  {
    DlgListaProductosReceta dlgListaProductosReceta = 
      new DlgListaProductosReceta(myParentFrame, "", true);
    dlgListaProductosReceta.setVisible(true);
    operaResumenPedido();
    FarmaVariables.vAceptar = false;
  }

  private void operaResumenPedido()
  {
    // borramos todo de la tabla
    while (tableModelResumenReceta.getRowCount() > 0)
      tableModelResumenReceta.deleteRow(0);
    tableModelResumenReceta.fireTableDataChanged();
    tblProductos.repaint();
    // cargamos los productos desde el ArrayList de Productos
    for (int i=0; i < VariablesModuloReceta.vArrayList_ResumenReceta.size(); i++)
    {
      tableModelResumenReceta.insertRow((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i));
      tableModelResumenReceta.fireTableDataChanged();
    }
    tblProductos.repaint();
    calculaTotalesPedido();
    FarmaUtility.setearPrimerRegistro(tblProductos, null, 0);
  }

  private void muestraDetalleProducto()
  {
    if (tblProductos.getRowCount() == 0)
      return;
        VariablesModuloVentas.vCod_Prod = 
        ((String) (tblProductos.getValueAt(tblProductos.getSelectedRow(), 
                                           0))).trim();
        VariablesModuloVentas.vDesc_Prod = 
        ((String) (tblProductos.getValueAt(tblProductos.getSelectedRow(), 
                                           1))).trim();
        VariablesModuloVentas.vNom_Lab = 
        ((String) (tblProductos.getValueAt(tblProductos.getSelectedRow(), 
                                           9))).trim();
    DlgDetalleProducto dlgDetalleProducto = 
      new DlgDetalleProducto(myParentFrame, "", true);
    dlgDetalleProducto.setVisible(true);
  }

  private void cancelaOperacion()
  {
    inicializaArrayList();
    cerrarVentana(false);
  }

  private void inicializaArrayList()
  {
        VariablesModuloReceta.vArrayList_PedidoReceta = new ArrayList();
        VariablesModuloReceta.vArrayList_ResumenReceta = new ArrayList();
  }

  private void calculaTotalesPedido()
  {
    if (tblProductos.getRowCount() <= 0)
    {
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
    for (int i = 0; i < VariablesModuloReceta.vArrayList_ResumenReceta.size(); 
      i++)
    {
      //log.debug(VariablesReceta.vArrayList_ResumenReceta.get(i));
      cantidad = Integer.parseInt((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i)).get(4));
      totalBruto += FarmaUtility.getDecimalNumber((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i)).get(3)) * 
          cantidad;
      totalIGV += FarmaUtility.getDecimalNumber((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i)).get(12));
      totalNeto += FarmaUtility.getDecimalNumber((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i)).get(7));
    }
    totalBruto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto, 
                                                                         2));
    totalNeto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto, 
                                                                        2));
    totalIGV = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV, 
                                                                       2));
    totalDscto = (totalBruto - totalNeto);
    totalDscto = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto, 
                                                                         2));
    /*log.debug("totalBruto 3 : " + totalBruto);
    log.debug("totalNeto : " + totalNeto);
    log.debug("totalDscto : " + totalDscto);
    log.debug("totalIGV : " + totalIGV);*/
    //log.debug("********************************");
    //log.debug("totalBruto 2 : " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalBruto, 2)));log.debug("totalNeto 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalNeto, 2)));log.debug("totalDscto 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalDscto, 2)));log.debug("totalIGV 2: " + FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(totalIGV,2)));
    redondeo = FarmaUtility.getNuevoRedondeo(totalNeto);
    //log.debug("redondeo : " + redondeo);
    totalNeto = totalNeto + redondeo;
    //log.debug("totalNeto Neto : " + FarmaUtility.formatNumber(totalNeto));
    lblBruto.setText(FarmaUtility.formatNumber(totalBruto));
    lblDscto.setText(FarmaUtility.formatNumber(totalDscto));
    lblDsctoPorc.setText("(" + FarmaUtility.formatNumber(totalDscto * 100 / 
                                                         totalBruto) + 
        "%)");
    lblIGV.setText(FarmaUtility.formatNumber(totalIGV));
    lblTotalS.setText(FarmaUtility.formatNumber(totalNeto));
    lblTotalD.setText(FarmaUtility.formatNumber(totalNeto / FarmaVariables.vTipCambio)); //obtener el tipo de cambio del dia
    lblRedondeo.setText(FarmaUtility.formatNumber(redondeo));
    lblItems.setText(String.valueOf(VariablesModuloReceta.vArrayList_ResumenReceta.size()));
  }

  private void muestraIngresoCantidad()
  {
    if (tblProductos.getRowCount() == 0)
      return;
    int filaActual;
    filaActual = tblProductos.getSelectedRow();
        VariablesModuloReceta.vCod_Prod = 
        ((String) (tblProductos.getValueAt(filaActual, 0))).trim();
        VariablesModuloReceta.vDesc_Prod = 
        ((String) (tblProductos.getValueAt(filaActual, 1))).trim();
        VariablesModuloReceta.vNom_Lab = 
        ((String) (tblProductos.getValueAt(filaActual, 9))).trim();
        VariablesModuloReceta.vCant_Ingresada_Temp = 
        ((String) (tblProductos.getValueAt(filaActual, 4))).trim();
        VariablesModuloReceta.vVal_Frac = 
        ((String) (tblProductos.getValueAt(filaActual, 10))).trim();
        VariablesModuloReceta.vPorc_Igv_Prod = 
        ((String) (tblProductos.getValueAt(filaActual, 11))).trim();
        VariablesModuloReceta.vPorc_Dcto_1 = 
        ((String) (tblProductos.getValueAt(filaActual, 5))).trim();
        VariablesModuloReceta.vVal_Prec_Vta = 
        ((String) (tblProductos.getValueAt(filaActual, 6))).trim();
    //log.debug("VariablesReceta.vPorc_Igv_Prod : " + VariablesReceta.vPorc_Igv_Prod);
    DlgIngresoCantidadReceta dlgIngresoCantidadReceta = 
      new DlgIngresoCantidadReceta(myParentFrame, "", true);
    dlgIngresoCantidadReceta.setVisible(true);
    if (FarmaVariables.vAceptar)
    {
      seleccionaProducto(filaActual);
      FarmaVariables.vAceptar = false;
    }
  }

  private void seleccionaProducto(int pFila)
  {
        VariablesModuloReceta.vTotalPrecVtaProd = 
        (FarmaUtility.getDecimalNumber(VariablesModuloReceta.vCant_Ingresada) * 
         FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta));

    //liberando registros
    //FarmaUtility.liberarTransaccion();
    operaProductoEnJTable(pFila);
    operaProductoEnArrayList(pFila);
    calculaTotalesPedido();
  }

  private void operaProductoEnJTable(int pFila)
  {
    tblProductos.setValueAt(VariablesModuloReceta.vCant_Ingresada, pFila, 
        4); //cantidad ingresada
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vPorc_Dcto_1), 
          2), pFila, 5); //PORC DCTO 1
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta), 
          3), pFila, 6); //PRECIO DE VENTA
    //log.debug(" FarmaUtility.formatNumber(VariablesReceta.vTotalPrecVtaProd,2) " + FarmaUtility.formatNumber(VariablesReceta.vTotalPrecVtaProd,2));
    tblProductos.setValueAt(FarmaUtility.formatNumber(VariablesModuloReceta.vTotalPrecVtaProd, 
          2), pFila, 7); //Total Precio Vta
    String valIgv = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta) - (FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta) / 
        (1 + (FarmaUtility.getDecimalNumber(VariablesModuloReceta.vPorc_Igv_Prod) / 100)))) *FarmaUtility.getDecimalNumber(VariablesModuloReceta.vCant_Ingresada));
    //log.debug(valIgv);
        VariablesModuloReceta.vVal_Igv_Prod = valIgv;
    tblProductos.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Igv_Prod), 
          2), pFila, 12); //Total Igv Producto
    tblProductos.repaint();
  }

  private void operaProductoEnArrayList(int pFila)
  {
    ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).set(4, VariablesModuloReceta.vCant_Ingresada);
    ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).set(5, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vPorc_Dcto_1), 
          2));
    ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).set(6, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta), 
          3));
    ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).set(7, FarmaUtility.formatNumber(VariablesModuloReceta.vTotalPrecVtaProd, 
          2));
    ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).set(12, FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Igv_Prod), 
          2));
  }

  private void eliminaProductoResumenPedido()
  {
    int filaActual = tblProductos.getSelectedRow();
    if (filaActual >= 0)
    {
      if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                         "Seguro de eliminar el Producto del Pedido?"))
      {
        /*String codProd =
          ((String) (tblProductos.getValueAt(filaActual, 0))).trim();
        String cantidad =
          ((String) (tblProductos.getValueAt(filaActual, 4))).trim();*/

                VariablesModuloReceta.vArrayList_ResumenReceta.remove(filaActual);
        tableModelResumenReceta.deleteRow(filaActual);
        tblProductos.repaint();
        calculaTotalesPedido();
        //lblItems.setText(String.valueOf(VariablesReceta.vArrayList_ResumenReceta.size()));
        if (tableModelResumenReceta.getRowCount() > 0)
        {
          if (filaActual > 0)
            filaActual--;
          FarmaGridUtils.showCell(tblProductos, filaActual, 0);
        }
      }
    }
    else
    {
      FarmaUtility.showMessage(this, "Debe seleccionar un Producto", 
                               tblProductos);
    }
  }

  private void obtieneInfoPedido()
  {
    String fechaTipoCambio = ""; //tipo cambio de la fecha actual
    try
    {
      fechaPedido = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      (new FacadeRecaudacion()).obtenerTipoCambio();
    }
    catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, 
                               "Error al obtener Tipo de Cambio del Dia . \n " + 
                               sql.getMessage(), null);
    }
  }

  private synchronized void grabarReceta()
  {
    if (VariablesModuloReceta.vArrayList_ResumenReceta.size() <= 0)
    {
      FarmaUtility.showMessage(this, 
                               "No hay Productos Seleccionados. Verifique!!!", 
                               tblProductos);
      return;
    }
    try
    {
            VariablesModuloReceta.vNum_Ped_Rec = FarmaSearch.getNuSecNumeracion(FarmaConstants.COD_NUMERA_RECETA,10);
      log.debug("VariablesReceta.vNum_Ped_Rec : " + VariablesModuloReceta.vNum_Ped_Rec);
      guardaVariablesPedidoCabecera();
      //log.debug("GUARDO VALORES DE RECETA CABECERA");
            DBModuloVenta.grabarCabeceraReceta();
      log.debug("GRABO CABECERA DE RECETA");
      int cont = 0;
      for (int i = 0; i < VariablesModuloReceta.vArrayList_ResumenReceta.size(); 
        i++)
      {
        cont = i;
        grabarDetallePedido(i);
      }
      log.debug("GRABO DETALLE DE RECETA : " + (cont + 1) + " PRODUCTOS");
      FarmaSearch.setNuSecNumeracionNoCommit(FarmaConstants.COD_NUMERA_RECETA);
      
      FarmaUtility.aceptarTransaccion();
      cerrarVentana(true);
    }
    catch (SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this, 
                               "Error en Base de Datos al grabar el pedido.\n" + 
                               sql, tblProductos);
    }
    catch (Exception exc)
    {
      FarmaUtility.liberarTransaccion();
        log.error("",exc);
      FarmaUtility.showMessage(this, 
                               "Error en la aplicacion al grabar el pedido.\n" + 
                               exc.getMessage(), tblProductos);
    }
  }

  private void grabarDetallePedido( int pFila)
    throws Exception
  {
        VariablesModuloReceta.vSec_Ped_Vta_Det = String.valueOf(pFila + 1);
        VariablesModuloReceta.vCod_Prod = ((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(0)).trim();
        VariablesModuloReceta.vCant_Ingresada = String.valueOf(FarmaUtility.trunc(FarmaUtility.getDecimalNumber(((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(4)).trim())));
        VariablesModuloReceta.vVal_Prec_Vta = ((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(6)).trim();
        VariablesModuloReceta.vTotalPrecVtaProd = FarmaUtility.getDecimalNumber(((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(7)).trim());
        VariablesModuloReceta.vPorc_Dcto_1 = String.valueOf(FarmaUtility.getDecimalNumber(((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(5)).trim()));
        VariablesModuloReceta.vPorc_Dcto_Total = VariablesModuloReceta.vPorc_Dcto_1; //cuando es pedido normal, el descuento total siempre es el descuento 1
        VariablesModuloReceta.vVal_Frac = ((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(10)).trim();
        VariablesModuloReceta.vEst_Ped_Vta_Det = ConstantsModuloVenta.ESTADO_PEDIDO_DETALLE_ACTIVO;
        VariablesModuloReceta.vVal_Prec_Lista = String.valueOf(FarmaUtility.getDecimalNumber(((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(3)).trim()));
        VariablesModuloReceta.vVal_Igv_Prod = ((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(11)).trim();
        VariablesModuloReceta.vUnid_Vta = ((String) ((ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(pFila)).get(2)).trim();
        DBModuloVenta.grabarDetalleReceta();
  }

  private void guardaVariablesPedidoCabecera()
  {
        VariablesModuloReceta.vVal_Bruto_Ped = lblBruto.getText().trim();
        VariablesModuloReceta.vVal_Neto_Ped = lblTotalS.getText().trim();
        VariablesModuloReceta.vVal_Redondeo_Ped = lblRedondeo.getText().trim();
        VariablesModuloReceta.vVal_Igv_Ped = lblIGV.getText().trim();
        VariablesModuloReceta.vVal_Dcto_Ped = lblDscto.getText().trim();
        VariablesModuloReceta.vCant_Items_Ped = lblItems.getText().trim();
        VariablesModuloReceta.vEst_Ped_Vta_Cab = ConstantsModuloVenta.ESTADO_PEDIDO_DETALLE_ACTIVO;
  }

  private void limpiaValoresPedido()
  {
        VariablesModuloReceta.vCod_Prod = "";
        VariablesModuloReceta.vDesc_Prod = "";
        VariablesModuloReceta.vNom_Lab = "";
        VariablesModuloReceta.vUnid_Vta = "";
        VariablesModuloReceta.vVal_Prec_Vta = "";
        VariablesModuloReceta.vPorc_Dcto_1 = "";
        VariablesModuloReceta.vDesc_Acc_Terap = "";
        VariablesModuloReceta.vStk_Prod = "";
        VariablesModuloReceta.vStk_Prod_Fecha_Actual = "";
        VariablesModuloReceta.vVal_Frac = "";
        VariablesModuloReceta.vVal_Prec_Lista = "";
        VariablesModuloReceta.vVal_Igv_Prod = "";
        VariablesModuloReceta.vPorc_Igv_Prod = "";
        VariablesModuloReceta.vEst_Ped_Vta_Cab = "";

        VariablesModuloReceta.vCant_Ingresada = "";
        VariablesModuloReceta.vCant_Ingresada_Temp = "";
        VariablesModuloReceta.vTotalPrecVtaProd = 0;

        VariablesModuloReceta.vArrayList_PedidoReceta = new ArrayList();
        VariablesModuloReceta.vArrayList_ResumenReceta = new ArrayList();

        VariablesModuloReceta.vNum_Ped_Rec = "";
        VariablesModuloReceta.vVal_Bruto_Ped = "";
        VariablesModuloReceta.vVal_Neto_Ped = "";
        VariablesModuloReceta.vVal_Redondeo_Ped = "";
        VariablesModuloReceta.vVal_Igv_Ped = "";
        VariablesModuloReceta.vVal_Dcto_Ped = "";

        VariablesModuloReceta.vCant_Items_Ped = "";
        VariablesModuloReceta.vSec_Ped_Vta_Det = "";
        VariablesModuloReceta.vPorc_Dcto_Total = "";
        VariablesModuloReceta.vEst_Ped_Vta_Det = "";
    
  }

}
