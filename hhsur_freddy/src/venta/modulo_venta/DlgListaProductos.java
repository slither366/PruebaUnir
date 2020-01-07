package venta.modulo_venta;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.DlgLogin;
import common.FarmaConnection;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import consorcio.UtilityHHVenta;

import dental.laboratorio.DlgMantProducto;

import dental.laboratorio.reference.DBMantenimiento;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import javax.swing.KeyStroke;

import javax.swing.table.DefaultTableCellRenderer;

import venta.DlgFiltroProductos;
import venta.DlgProcesar;
import venta.FarmaHora;
import venta.FrmEconoFar;
import venta.campAcumulada.DlgListaCampAcumulada;
import venta.campAcumulada.reference.VariablesCampAcumulada;
import venta.campana.reference.VariablesCampana;
import venta.convenio.DlgListaConvenios;
import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.DlgListaConveniosBTLMF;
import venta.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.delivery.DlgListaClientes;
import venta.delivery.reference.VariablesDelivery;
import venta.fidelizacion.reference.AuxiliarFidelizacion;
import venta.fidelizacion.reference.DBFidelizacion;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.hilos.SubProcesosConvenios;
import venta.receta.DlgDetalleReceta;
import venta.receta.DlgListaCotizaciones;
import venta.receta.Utility.DlgPBReceta;
import venta.receta.reference.DBReceta;
import venta.recetario.DlgListaGuiasRM;
import venta.recetario.DlgResumenRecetarioMagistral;
import venta.recetario.reference.VariablesRecetario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import static venta.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloReceta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;

import venta.cliente.DlgBuscaClienteJuridico;

import venta.inventario.DlgAjusteKardex;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.VariablesInventario;

import venta.modulo_venta.campana.DlgSeleccionCampana;

import venta.receta.DlgListaPedidosAnulados;

import venta.reportes.repo_renova.DlgEncuesta;
import venta.reportes.repo_renova.DlgRptPorCliente;
import venta.cliente.reference.*;

import venta.reportes.repo_renova.DlgRptPorClienteProducto;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>r
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProductos.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      28.12.2005   Creación
 * PAULO       28.04.2006   Modificacion <br>
 * DUBILLUZ    14.06.2007   Modificacion <br>
 * ERIOS       01.06.2008   Modificacion <br>
 * DVELIZ      22.08.2008   Modificacion <br>
 * JCALLO      03.03.2009   Modificacion <br>
 * ASOSA       02.02.2010   Modificacion <br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgListaProductos extends JDialog {
    
    // menu derecho
    private JPopupMenu popup;
    private JMenuItem itemModificarProd,itemAjusteStock,itemNuevoProd,itemReporteCliente,itemPromociones,
        itemInactivarProducto;
    //
    
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductos.class);
    private boolean vIndActReceta = false;
    private Frame myParentFrame;

    private JTable myJTable;

    private FarmaTableModel tableModelListaPrecioProductos;
    private FarmaTableModel tblModelListaSustitutos;
    private FarmaTableModel tblModeLaboratorio;

    private String descUnidPres = "";
    private String stkProd = "";
    private String valPrecPres = "";
    private String valFracProd = "";
    private String indProdCong = "";
    //private String valPrecLista = "";
    private String valPrecVta = "";
    private String descUnidVta = "";
    private String indProdHabilVta = "";
    private String porcDscto_1 = "";
    //private String indProdProm = "";

    /**
     * Indicador de Tipo de Producto
     * @author dubilluz
     * @since  22.10.2007
     */
    private String tipoProd = "";
    private String bonoProd = "";

    private int totalItems = 0;
    private double totalVenta = 0;

    private String tempCodBarra = "";

    /**
     * Indicadores de stock en adicional en fraccion del local.
     * @author Edgar Rios Navarro
     * @since 03.06.2008
     */
    private String stkFracLoc = "";
    private String descUnidFracLoc = "";

    /**
     * Columnas de la grilla
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private final int COL_COD = 1;
    private final int COL_DESC_PROD = 2;
    private final int COL_STOCK = 5;
    private final int COL_ORD_LISTA = 18;
    private final int COL_IND_ENCARTE = 19;
    private final int COL_ORIG_PROD = 20;
    private boolean vLost = true;
    //JCORTEZ 23.07.08 
    private final int COL_RES_CUPON = 25;
    private final int COL_RES_ORIG_PROD = 19;
    private final int COL_RES_VAL_FRAC = 10;
    private final int DIG_PROD = 6;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblItems = new JLabel();
    private JLabel lblItems_T = new JLabel();
    private JLabel lblPrecio = new JLabel();
    private JLabel lblPrecio_T = new JLabel();
    private JLabel lblUnidad = new JLabel();
    private JLabel lblUnidad_T = new JLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel jPanel2 = new JPanel();
    private JSeparator jSeparator2 = new JSeparator();
    private JLabel lblDescLab_Alter = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    private JTable tblListaSustitutos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabel lblTotalVenta = new JLabel();
    private JLabel lblTotalVenta_T = new JLabel();
    private JPanel jPanel4 = new JPanel();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblCliente = new JLabelWhite();
    private JLabelWhite lblCliente_T = new JLabelWhite();
    private JLabelWhite lblClienteConv = new JLabelWhite();
    private JLabelWhite lblClienteConv_T = new JLabelWhite();

    private JButtonLabel btnProdAlternativos = new JButtonLabel();

    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelWhite lblMedico = new JLabelWhite();
    private JLabelWhite lblMedicoT = new JLabelWhite();

    private JLabelWhite lblConvenio = new JLabelWhite();
    private JLabelWhite lblConvenioT = new JLabelWhite();


    private JLabel lblProdIgv = new JLabel();
    private JLabelFunction lblF13 = new JLabelFunction();
    private JPanel jPanel5 = new JPanel();
    private JLabel lblProdRefrig = new JLabel();
    private JLabel lblProdCong = new JLabel();
    private JLabel lblProdProm = new JLabel();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabel lblProdEncarte = new JLabel();
    private JLabelWhite lblStockAdic_T = new JLabelWhite();
    private JLabelWhite lblStockAdic = new JLabelWhite();
    private JLabelWhite lblUnidFracLoc = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private Object operaCampañasFid;
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelHeader jPanelHeader0 = new JPanelHeader();

    private JLabel lblMensajeCampaña = new JLabel();
     private JLabelFunction lblF12 = new JLabelFunction();


    private boolean vEjecutaAccionTeclaListado = false;
    private JButtonLabel lblPedDelivery = new JButtonLabel();
    private JPanelTitle pnlPedDelivery = new JPanelTitle();
    //JMIRANDA 16/09/2009
    private JButtonLabel lblMensajeCodBarra = new JButtonLabel();

    private boolean pasoTarjeta = false;
    private JLabel lblDNI_SIN_COMISION = new JLabel();

    String nombCliente = " ";
    String nombConvenio = " ";
    
    
    String pRecetaCodCia    = "";
    String pRecetaCodLocal  = "";
    String pRecetaNumReceta = "";
    private JLabel lblMensajeFiltro = new JLabel();
    private JLabelFunction lblCotizar = new JLabelFunction();
    private JButton btnCliente = new JButton();
    
    private boolean obligaFoco = true;
    private JTextField txtEspecialidad = new JTextField();
    private JLabel jLabel1 = new JLabel();
    
    
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JTable tblLaboratorio = new JTable();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaProductos() {
        this(null, "", false);
    }

    public DlgListaProductos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            
            popup = new JPopupMenu(); //creamos el menu saliente
         
            // itemInactivarProducto
            itemInactivarProducto = new JMenuItem("Inactivar Producto");
            itemInactivarProducto.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     inactivarProducto();
                   }
                 });
            popup.add(itemInactivarProducto);
            
           itemModificarProd = new JMenuItem("Modificar Producto");
                   itemModificarProd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                   itemModificarProd.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                            muestraModificaProducto();
                            
                          }
                        });
                   popup.add(itemModificarProd);

                   itemAjusteStock = new JMenuItem("Ajuste Stock");                   
                   itemAjusteStock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                   itemAjusteStock.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                            muestraAjusteKardex();
                          }
                        });
                   popup.add(itemAjusteStock);
            
            
                   // itemNuevoProd
                   itemNuevoProd = new JMenuItem("Nuevo Producto");
                   itemNuevoProd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                   itemNuevoProd.addActionListener(new ActionListener() {
                          public void actionPerformed(ActionEvent e) {
                            nuevoProducto();
                          }
                        });
                   popup.add(itemNuevoProd);   
            
            itemReporteCliente = new JMenuItem("Reporte por Cliente");
            itemReporteCliente.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     reporte_por_cliente();
                   }

                });
            popup.add(itemReporteCliente);   
            
            itemPromociones = new JMenuItem("Ver Packs");
            itemPromociones.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                     muestraProductoPromocion("N");
                   }

                });
            popup.add(itemPromociones);   
            
            
            
            
            
        } catch (Exception e) {
            log.error("",e);
        }

    }

    public DlgListaProductos(Frame parent, String title, boolean modal,boolean vActReceta) {
        super(parent, title, modal);
        myParentFrame = parent;
        vIndActReceta = vActReceta;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

    public DlgListaProductos(Frame frame, String string, boolean b, boolean b1, FarmaTableModel farmaTableModel) {

    }


    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        //this.setSize(new Dimension(809, 561));
        this.setSize(new Dimension(1237, 651));
        this.getContentPane().setLayout(borderLayout1);
        if(vIsActReceta())
        this.setTitle("Ingreso Medicamentos de Receta");
        else
        this.setTitle("Lista de Productos y Precios");
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setBackground(Color.white);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setBackground(SystemColor.window);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);
        jPanel3.setBounds(new Rectangle(20, 55, 605, 45));
        jPanel3.setBackground(new Color(0, 114, 169));
        jPanel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel3.setLayout(null);
        lblItems.setText("0");
        lblItems.setBounds(new Rectangle(145, 5, 80, 15));
        lblItems.setFont(new Font("SansSerif", 1, 14));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        lblItems_T.setText("Items :");
        lblItems_T.setBounds(new Rectangle(15, 5, 65, 15));
        lblItems_T.setFont(new Font("SansSerif", 1, 14));
        lblItems_T.setForeground(Color.white);
        lblPrecio.setBounds(new Rectangle(90, 25, 130, 15));
        lblPrecio.setFont(new Font("SansSerif", 1, 15));
        lblPrecio.setForeground(Color.white);
        lblPrecio_T.setText("Precio : S/.");
        lblPrecio_T.setBounds(new Rectangle(10, 25, 75, 15));
        lblPrecio_T.setFont(new Font("SansSerif", 1, 13));
        lblPrecio_T.setForeground(Color.white);
        lblUnidad.setBounds(new Rectangle(90, 5, 190, 15));
        lblUnidad.setFont(new Font("SansSerif", 1, 11));
        lblUnidad.setForeground(Color.white);
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(10, 5, 60, 15));
        lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T.setForeground(Color.white);
        lblF2.setText("[ F2 ] Ver Alternativos");
        lblF2.setBounds(new Rectangle(290, 460, 130, 20));
        lblF1.setText("[ F1 ] Info Prod");
        lblF1.setBounds(new Rectangle(160, 575, 130, 35));
        jScrollPane2.setBounds(new Rectangle(1245, 480, 80, 5));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel2.setBounds(new Rectangle(1255, 430, 55, 20));
        jPanel2.setBackground(new Color(0, 114, 169));
        jPanel2.setLayout(null);
        jSeparator2.setBounds(new Rectangle(200, 0, 15, 20));
        jSeparator2.setBackground(Color.black);
        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        lblDescLab_Alter.setBounds(new Rectangle(225, 0, 375, 20));
        lblDescLab_Alter.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Alter.setForeground(Color.white);
        jScrollPane1.setBounds(new Rectangle(20, 100, 875, 440));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlIngresarProductos.setBounds(new Rectangle(20, 0, 1195, 55));
        
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                      1));
        pnlIngresarProductos.setBackground(new Color(0, 114, 169));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(695, 5, 150, 20));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setMnemonic('b');
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
                });
        txtProducto.setBounds(new Rectangle(80, 5, 605, 30));
        txtProducto.setFont(new Font("SansSerif", 1, 11));
        txtProducto.setForeground(new Color(0, 30, 89));
        txtProducto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtProducto_keyPressed(e);

                }

                    public void keyReleased(KeyEvent e) {
                    txtProducto_keyReleased(e);
                }

                    public void keyTyped(KeyEvent e) {
                    txtProducto_keyTyped(e);
                }
                });
        txtProducto.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtProducto_focusLost(e);
                }
            });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(15, 10, 60, 20));
        btnProducto.setMnemonic('p');
        btnProducto.setFont(new Font("SansSerif", 1, 11));
        btnProducto.setDefaultCapable(false);
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setBackground(new Color(50, 162, 65));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setFocusPainted(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setBorderPainted(false);
        btnProducto.setForeground(Color.white);
        btnProducto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProducto_actionPerformed(e);
                    }
                });
        tblProductos.setFont(new Font("SansSerif", 0, 12));
        tblProductos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblProductos_mouseClicked(e);
                }
            });
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(600, 575, 130, 35));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(745, 575, 145, 35));
        lblF12.setBounds(new Rectangle(445, 575, 145, 35));

        lblF12.setText("<html><center>[ F12 ] Ingresar<br> Pedido/Cotizacion</center></html>");
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 575, 135, 35));
        lblTotalVenta.setText("0.00");
        lblTotalVenta.setBounds(new Rectangle(140, 25, 85, 15));
        lblTotalVenta.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta.setForeground(Color.white);
        lblTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalVenta_T.setText("Total venta : S/.");
        lblTotalVenta_T.setBounds(new Rectangle(15, 25, 120, 15));
        lblTotalVenta_T.setFont(new Font("SansSerif", 1, 15));
        lblTotalVenta_T.setForeground(Color.white);
        jPanel4.setBounds(new Rectangle(630, 55, 265, 45));
        jPanel4.setBackground(new Color(0, 114, 169));
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel4.setLayout(null);
        pnlTitle1.setBounds(new Rectangle(1260, 330, 585, 30));
        lblCliente.setBounds(new Rectangle(70, 5, 310, 20));
        lblCliente.setText(" ");
        lblCliente.setFont(new Font("SansSerif", 1, 14));
        lblCliente_T.setText("Cliente:");
        lblCliente_T.setBounds(new Rectangle(5, 5, 60, 20));
        lblCliente_T.setFont(new Font("SansSerif", 1, 14));
        btnProdAlternativos.setText("Productos Sustitutos");
        btnProdAlternativos.setBounds(new Rectangle(10, 0, 150, 20));
        btnProdAlternativos.setMnemonic('S');
        btnProdAlternativos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProdAlternativos_actionPerformed(e);
                    }
                });
        lblF9.setBounds(new Rectangle(565, 485, 150, 20));
        //lblF9.setText("[ F9 ] Asociar Campaña");//lblF9.setText("[ F9 ] Vta. Delivery");//JCALLO 19.12.2008 SE REEMPLAZO PARA OPCION DE CAMP ACUMULADAS
        lblF9.setText("[ F9 ] Camp. Acumulada");
        lblF8.setBounds(new Rectangle(425, 485, 135, 20));
        lblF8.setText("[ F8 ] Dcto por Receta");
        lblMedico.setBounds(new Rectangle(435, 5, 260, 20));
        lblMedico.setText(" ");
        lblMedicoT.setText("Medico:");
        lblMedicoT.setBounds(new Rectangle(390, 5, 45, 20));
        lblProdIgv.setBounds(new Rectangle(125, 0, 95, 20));
        lblProdIgv.setFont(new Font("SansSerif", 1, 11));
        lblProdIgv.setText("SIN IGV");
        lblProdIgv.setBackground(new Color(124, 186, 214));
        lblProdIgv.setOpaque(true);
        lblProdIgv.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdIgv.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdIgv.setForeground(Color.white);
        lblF13.setBounds(new Rectangle(425, 460, 135, 20));
        lblF13.setText("[ F3 ] Vta. Convenio");
        jPanel5.setBounds(new Rectangle(20, 540, 875, 20));
        jPanel5.setBackground(new Color(0, 114, 169));
        jPanel5.setLayout(null);
        lblProdRefrig.setBounds(new Rectangle(480, 0, 95, 20));
        lblProdRefrig.setVisible(true);
        lblProdRefrig.setFont(new Font("SansSerif", 1, 11));
        lblProdRefrig.setText("REFRIG");
        lblProdRefrig.setBackground(new Color(124, 186, 214));
        lblProdRefrig.setOpaque(true);
        lblProdRefrig.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdRefrig.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                               1));
        lblProdRefrig.setForeground(Color.white);
        lblProdCong.setBounds(new Rectangle(15, 0, 95, 20));
        lblProdCong.setVisible(true);
        lblProdCong.setFont(new Font("SansSerif", 1, 11));
        lblProdCong.setText("CONGELADO");
        lblProdCong.setBackground(new Color(124, 186, 214));
        lblProdCong.setOpaque(true);
        lblProdCong.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdCong.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdCong.setForeground(Color.white);
        lblProdProm.setBounds(new Rectangle(240, 0, 220, 20));
        lblProdProm.setFont(new Font("SansSerif", 1, 11));
        lblProdProm.setText("PRODUCTO EN PACK");
        lblProdProm.setBackground(new Color(124, 186, 214));
        lblProdProm.setOpaque(true);
        lblProdProm.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdProm.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblProdProm.setForeground(Color.white);
        lblF4.setBounds(new Rectangle(565, 460, 150, 20));
        if (VariablesPtoVenta.vIndVerStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Stock Locales");
        } else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Recetario Magistral");
        }
        lblProdEncarte.setBounds(new Rectangle(590, 0, 95, 20));
        lblProdEncarte.setVisible(true);
        lblProdEncarte.setFont(new Font("SansSerif", 1, 11));
        lblProdEncarte.setText("ENCARTE");
        lblProdEncarte.setBackground(new Color(124, 186, 214));
        lblProdEncarte.setOpaque(true);
        lblProdEncarte.setHorizontalAlignment(SwingConstants.CENTER);
        lblProdEncarte.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                1));
        lblProdEncarte.setForeground(Color.white);
        lblStockAdic_T.setText("Stock adic.:");
        lblStockAdic_T.setBounds(new Rectangle(5, 0, 65, 20));
        lblStockAdic_T.setForeground(SystemColor.windowText);
        lblStockAdic.setBounds(new Rectangle(75, 0, 40, 20));
        lblStockAdic.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStockAdic.setForeground(new Color(43, 141, 39));
        lblUnidFracLoc.setBounds(new Rectangle(120, 0, 90, 20));
        lblUnidFracLoc.setForeground(new Color(43, 141, 39));
        jPanelWhite1.setBounds(new Rectangle(225, 25, 225, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelHeader1.setBounds(new Rectangle(20, 0, 365, 30));
        lblMensajeCampaña.setBounds(new Rectangle(350, 0, 350, 30));
        //lblMensajeCampaña.setText("   Promoción: \" Acumula tu Compra \"");
        lblMensajeCampaña.setText("   ");
        lblMensajeCampaña.setBackground(Color.white);
        lblMensajeCampaña.setForeground(Color.red);
        lblMensajeCampaña.setFont(new Font("Dialog", 1, 14));
        // lblProdRefrig.setBackground(new Color(44, 146, 24));
        lblMensajeCampaña.setOpaque(true);
        lblMensajeCampaña.setVisible(false);

        lblPedDelivery.setText("[Alt+D] Pedido Delivery");
        lblPedDelivery.setBounds(new Rectangle(0, 0, 135, 20));
        lblPedDelivery.setMnemonic('d');
        lblPedDelivery.setForeground(Color.black);
        lblPedDelivery.setHorizontalAlignment(SwingConstants.CENTER);
        lblPedDelivery.setBackground(Color.black);
        lblPedDelivery.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                1));
        lblPedDelivery.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblPedDelivery_actionPerformed(e);
                    }
                });
        pnlPedDelivery.setBounds(new Rectangle(425, 510, 135, 20));
        pnlPedDelivery.setBackground(SystemColor.control);
        pnlPedDelivery.setBorder(BorderFactory.createTitledBorder(""));
        //JMIRANDA 16/09/2009
        lblMensajeCodBarra.setText("");
        lblMensajeCodBarra.setBounds(new Rectangle(285, 0, 160, 20));
        lblMensajeCodBarra.setFont(new Font("SansSerif", 1, 12));
        lblDNI_SIN_COMISION.setText("DNI Inválido. No aplica Prog. Atención al Cliente");
        lblDNI_SIN_COMISION.setBounds(new Rectangle(380, 0, 320, 30));
        lblDNI_SIN_COMISION.setForeground(new Color(231, 0, 0));
        lblDNI_SIN_COMISION.setFont(new Font("Dialog", 3, 14));
        lblDNI_SIN_COMISION.setBackground(Color.white);
        lblDNI_SIN_COMISION.setOpaque(true);
        lblDNI_SIN_COMISION.setVisible(false);
        /*
         *         lblMensajeCampaña.setBackground(Color.white);
        lblMensajeCampaña.setForeground(Color.red);
         * */
        lblMensajeFiltro.setText("Mensaje de Filtro *");
        lblMensajeFiltro.setBounds(new Rectangle(20, 35, 500, 20));
        lblMensajeFiltro.setBackground(SystemColor.window);
        lblMensajeFiltro.setForeground(SystemColor.window);
        lblMensajeFiltro.setFont(new Font("SansSerif", 3, 12));
        //lblMedico.add(lblMensajeCampaña, null);
        lblCotizar.setText("[ F5 ] Cotizar");
        lblCotizar.setBounds(new Rectangle(310, 575, 120, 35));
        btnCliente.setText("Datos Pedido");
        btnCliente.setBounds(new Rectangle(695, 30, 150, 20));
        /*jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });*/
        btnCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnCliente_actionPerformed(e);
                }
            });
        txtEspecialidad.setBounds(new Rectangle(875, 25, 295, 20));
        txtEspecialidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEspecialidad_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtEspecialidad_keyReleased(e);
                }
            });
        jLabel1.setText("Especialidad");
        jLabel1.setBounds(new Rectangle(875, 5, 105, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jScrollPane3.setBounds(new Rectangle(900, 55, 315, 505));
        tblLaboratorio.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblLaboratorio_mouseClicked(e);
                }
            });
        jButton1.setText("Limpiar Filtro");
        jButton1.setBounds(new Rectangle(1070, 575, 145, 35));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton2.setText("Ver Campa\u00f1as");
        jButton2.setBounds(new Rectangle(900, 575, 155, 35));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        pnlTitle1.add(lblDNI_SIN_COMISION, null);
        pnlTitle1.add(lblMensajeCampaña, null);
        pnlTitle1.add(lblMedicoT, null);
        pnlTitle1.add(lblMedico, null);
        pnlTitle1.add(jPanelHeader1, null);
        jPanelHeader1.add(lblCliente_T, null);
        jPanelHeader1.add(lblCliente, null);
        //JMIRANDA 16/09/2009
        jPanelWhite1.add(lblStockAdic_T, null);
        jPanelWhite1.add(lblStockAdic, null);
        jPanelWhite1.add(lblUnidFracLoc, null);
        jPanel3.add(lblMensajeCodBarra, null);
        jPanel3.add(jPanelWhite1, null);
        jPanel3.add(lblPrecio, null);
        jPanel3.add(lblPrecio_T, null);
        jPanel3.add(lblUnidad, null);
        jPanel3.add(lblUnidad_T, null);
        jScrollPane2.getViewport();
        jPanel2.add(btnProdAlternativos, null);
        jPanel2.add(jSeparator2, null);
        jPanel2.add(lblDescLab_Alter, null);
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(jLabel1, null);
        pnlIngresarProductos.add(txtEspecialidad, null);
        pnlIngresarProductos.add(btnCliente, null);
        pnlIngresarProductos.add(lblMensajeFiltro, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        jPanel4.add(lblTotalVenta_T, null);
        jPanel4.add(lblItems_T, null);
        jPanel4.add(lblItems, null);
        //jContentPane.add(lblF4, null);
        jPanel4.add(lblTotalVenta, null);
        jPanel5.add(lblProdEncarte, null);
        //jContentPane.add(lblF13, null);
        //jContentPane.add(lblF8, null);
        //jContentPane.add(lblF9, null);
        jScrollPane3.getViewport().add(tblLaboratorio, null);
        jContentPane.add(jButton2, null);
        jContentPane.add(jButton1, null);
        jContentPane.add(jScrollPane3, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblCotizar, null);
        jPanel5.add(lblProdProm, null);
        jPanel5.add(lblProdCong, null);
        jPanel5.add(lblProdRefrig, null);
        jPanel5.add(lblProdIgv, null);
        jContentPane.add(jPanel5, null);
        //jContentPane.add(lblF2, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(jPanel4, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(lblF1, null);
        jScrollPane2.getViewport().add(tblListaSustitutos, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel2, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlIngresarProductos, null);
        pnlPedDelivery.add(lblPedDelivery, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //jContentPane.add(pnlPedDelivery, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************
    private boolean vIsActReceta(){
        return vIndActReceta;
    }
    private void initialize() {
        
        
        initTableListaPreciosProductos();
        initTableProductosSustitutos();
        setJTable(tblProductos);
        iniciaProceso(true);
        VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
        //JQUISPE 03.05.2010
        //Inicializo los valores de las posiciones
        VariablesModuloVentas.vPosNew = 0;
        VariablesModuloVentas.vPosOld = 0;

        // Inicio Adicion Delivery 28/04/2006 Paulo
        if (!FarmaVariables.vAceptar) {
            String nombreClienteDeliv = 
                VariablesDelivery.vNombreCliente + " " + 
                VariablesDelivery.vApellidoPaterno + " " + 
                VariablesDelivery.vApellidoMaterno;
            //Modificado por DVELIZ 30.09.08
            //jcallo 02.10.2008
            if (VariablesFidelizacion.vNumTarjeta.trim().length() <= 0) {
                lblCliente.setText(nombreClienteDeliv);
            } else {
                lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + 
                                   VariablesFidelizacion.vApePatCliente + " " + 
                                   VariablesFidelizacion.vApeMatCliente);
            }
            //fin jcallo 02.10.2008
            lblMedico.setText(VariablesModuloVentas.vNombreListaMed);
            FarmaVariables.vAceptar = true;
        }
        // Fin Adicion Delivery 28/04/2006 Paulo
        FarmaVariables.vAceptar = false;

        //Dveliz 26.08.08
        VariablesCampana.vListaCupones = new ArrayList();

        /** JCALLO 01.10.2008**/
        /*if ( VariablesVentas.HabilitarF9.equalsIgnoreCase(
            ConstantsVentas.ACTIVO) ) {
        lblF9.setVisible(true);
    }else if( VariablesVentas.HabilitarF9.equalsIgnoreCase(
            ConstantsVentas.INACTIVO) ){
        lblF9.setVisible(false);
    }*/
        lblF9.setVisible(true);

        if (VariablesPtoVenta.vIndVerStockLocales.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {   //JCHAVEZ 08102009.sn
            lblF4.setVisible(true);
        }
        else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {   lblF4.setVisible(true);
        }
        else
        {   lblF4.setVisible(false);
        }

        try {
            pnlPedDelivery.setVisible(DBModuloVenta.getIndVerPedidoDelivery());
            lblPedDelivery.setVisible(DBModuloVenta.getIndVerPedidoDelivery());
        } catch (SQLException ex) {
            pnlPedDelivery.setVisible(false);
            lblPedDelivery.setVisible(false);
            log.error("",ex);
        }
        //JCHAVEZ 08102009.en
        
        VariablesRecetario.strCodigoRecetario="";
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPreciosProductos() {
        tableModelListaPrecioProductos = 
                new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos, 
                                    0);
        clonarListadoProductos();

        FarmaUtility.initSelectList(tblProductos, 
                                    tableModelListaPrecioProductos, ConstantsModuloVenta.columnsListaProductos);
        tblProductos.setName(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS);
        if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, 
                                 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
    }

    private void initTableProductosSustitutos() {
        tblModelListaSustitutos = 
                new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos, 
                                    0);
        
        FarmaUtility.initSelectList(tblListaSustitutos, 
                                    tblModelListaSustitutos, ConstantsModuloVenta.columnsListaProductos);
        tblListaSustitutos.setName(ConstantsModuloVenta.NAME_TABLA_SUSTITUTOS);
        muestraProductosSustitutos();
        
        
        tblModeLaboratorio = 
                new FarmaTableModel(ConstantsModuloVenta.columnsListaLaboratorio, ConstantsModuloVenta.defaultValuesListaLaboratorio, 
                                    0);
        
        FarmaUtility.initSimpleList(tblLaboratorio, 
                                    tblModeLaboratorio, ConstantsModuloVenta.columnsListaLaboratorio);
        muestraLaboratorios();
        
        
    }

    public void iniciaProceso(boolean pInicializar) {

        for (int i = 0; i < tblProductos.getRowCount(); i++)
            tblProductos.setValueAt(new Boolean(false), i, 0);

        for (int i = 0; 
             i < VariablesModuloVentas.tableModelListaGlobalProductos.getRowCount(); 
             i++)
            VariablesModuloVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false), 
                                                                      i, 0);

        if (pInicializar) {
            VariablesModuloVentas.vArrayList_PedidoVenta = new ArrayList();
            for (int i = 0; 
                 i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); i++)
                VariablesModuloVentas.vArrayList_PedidoVenta.add((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i));
        }

        log.debug("VariablesVentas.vArrayList_PedidoVenta : " + VariablesModuloVentas.vArrayList_PedidoVenta.size());
        ArrayList myArray = new ArrayList();
        for (int i = 0; i < VariablesModuloVentas.vArrayList_PedidoVenta.size(); i++)
            myArray.add((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i));

        for (int i = 0; i < VariablesModuloVentas.vArrayList_Prod_Promociones.size(); 
             i++)
            myArray.add((ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones.get(i));


        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, myArray, 0);
        //if ( !pInicializar )  
        actualizaListaProductosAlternativos();
       // muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);

        muestraProductoPromocion(17, lblProdProm);
        //
        muestraProductoRefrigerado(15, lblProdRefrig);
        //muestraIndTipoProd(16, lblIndTipoProd);

        // JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
        colocaTotalesPedido();

        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        if(!VariablesModuloVentas.is_cotizacion){
            jContentPane.setBackground(Color.WHITE);
        }else{
            jContentPane.setBackground(new Color(255,76,76));
        }
        this.repaint();
        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();
        
        if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
        else lblDNI_SIN_COMISION.setVisible(false);
        
        FarmaUtility.centrarVentana(this);
        vEjecutaAccionTeclaListado = false;
        VariablesModuloVentas.vVentanaListadoProductos = true;

        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
        {
          //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
    	  this.setTitle("Lista de Productos y Precios "+VariablesConvenioBTLMF.vNomConvenio);
    	  log.debug("Nombre Cliente::" +VariablesConvenioBTLMF.vNomCliente);
    	  lblCliente.setText("" +VariablesConvenioBTLMF.vNomCliente);
    	  //lblMedicoT.setIcon(icon);
    	  //lblMedico.setText(" "+VariablesConvenioBTLMF.vNomConvenio );

        }
        else
        {
        evaluaTitulo();
        }
        //evaluaSeleccionMedico();
        lblProdIgv.setVisible(false);
        FarmaUtility.moveFocus(txtProducto);

        if (VariablesModuloVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesModuloVentas.vIndPedConProdVirtual = false;

        if (VariablesModuloVentas.vKeyPress != null) {
            if (VariablesModuloVentas.vCodBarra.trim().length() > 0) {
                txtProducto.setText(VariablesModuloVentas.vCodBarra.trim());
                txtProducto_keyPressed(VariablesModuloVentas.vKeyPress);
            } else if (VariablesModuloVentas.vCodProdBusq.trim().length() > 0) {
                txtProducto.setText(VariablesModuloVentas.vCodProdBusq.trim());
                txtProducto_keyPressed(VariablesModuloVentas.vKeyPress);
            } else {
                txtProducto.setText(VariablesModuloVentas.vKeyPress.getKeyChar() + 
                                    "");
                txtProducto_keyReleased(VariablesModuloVentas.vKeyPress);
            }
        }

        //JCORTEZ 17.04.08
        if (!VariablesModuloVentas.vCodFiltro.equalsIgnoreCase("")) {
            cargaListaFiltro();
        }
        //AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña,"L",txtProducto,this);
    }

    // ************************************************************************************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - Evitar le ejecución de 2 teclas a la vez al momento de comprometer stock 
    // ************************************************************************************************************************************************ 
    
    public boolean isCodigoCampana(String pCadena){
        boolean vValor;
        try {
            vValor = DBModuloVenta.isCodigoCampanaBD(pCadena);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            vValor = false;
        }
        return vValor;
    }
    
    public void procesoEnter(KeyEvent e){
        
        String pValorIngresado =  txtProducto.getText().trim();
        
        if(isCodigoCampana(pValorIngresado)){
            muestraProductoPromocion(pValorIngresado);
        }
        else{
            
            deshabilitaProducto();                    
            String vCadenaOriginal =  txtProducto.getText().trim();
            log.debug("!!!!!!!!!!!!!Cadena Original:"+vCadenaOriginal);
            //ERIOS 03.07.2013 Limpia la caja de texto
            limpiaCadenaAlfanumerica(txtProducto);

            //Agregado por FRAMIREZ 24/11/2011
            //Si es la tarjeta de un cliente, va al modulo de convenio.
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
                UtilityConvenioBTLMF.esTarjetaConvenio(txtProducto.getText()))
            {

                if (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)
                {
                    if (JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de cancelar el pedido por Convenio?"))
                    {
                        VariablesConvenioBTLMF.limpiaVariablesBTLMF();
                        if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0)
                        {
                            FarmaUtility.showMessage(this,
                                         "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                                         "no deben haber productos seleccionados. Verifique!!!",
                                         txtProducto);
                        } else
                        {
                            lblCliente.setText("");
                            lblMedico.setText("");
                            this.setTitle("Lista de Productos y Precios /  IP : " + FarmaVariables.vIpPc);
                            evaluaTitulo();
                            //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
                            UtilityFidelizacion.operaCampañasFidelizacion(" ");
                        }
                    }
                }
                else
                {
                    if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0)
                    {
                        FarmaUtility.showMessage(this,
                                     "Existen Productos Seleccionados. Para iniciar un pedido convenio\n" +
                                     "no deben haber productos seleccionados. Verifique!!!",
                                     txtProducto);
                    }
                    else
                    {   DlgListaConveniosBTLMF convenio = new DlgListaConveniosBTLMF(myParentFrame);
                        convenio.irIngresoDatosConvenio2(txtProducto);

                        if (VariablesConvenioBTLMF.vAceptar)
                        {
                            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
                            
                            //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
                            Map map = UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this);
                            nombConvenio = (String)map.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);
                            VariablesConvenioBTLMF.vFlgImprimeImportes   = (String)map.get(ConstantsConvenioBTLMF.COL_FLG_IMPRIME_IMPORTES);
                            VariablesConvenioBTLMF.vIndVtaComplentaria   = (String)map.get(ConstantsConvenioBTLMF.COL_IND_VTA_COMPLEMENTARIA);
                            VariablesConvenioBTLMF.vFlgValidaLincreBenef =  (String)map.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF);
                            VariablesConvenioBTLMF.vRuc                  =  (String)map.get(ConstantsConvenioBTLMF.COL_RUC);
                            VariablesConvenioBTLMF.vInstitucion          =  (String)map.get(ConstantsConvenioBTLMF.COL_INSTITUCION);
                            VariablesConvenioBTLMF.vDireccion            =  (String)map.get(ConstantsConvenioBTLMF.COL_DIRECCION);
                            VariablesConvenioBTLMF.vNomConvenio = nombConvenio;
                            
                            log.debug("VariablesConvenioBTLMF.vCodConvenio:"+VariablesConvenioBTLMF.vCodConvenio);
                            log.debug("VariablesConvenioBTLMF.vNomConvenio:"+VariablesConvenioBTLMF.vNomConvenio );
                            
                            evaluaTitulo();
                            txtProducto.setText("");
                            FarmaGridUtils.showCell(tblProductos, 0, 0);
                            FarmaUtility.setearActualRegistro(tblProductos, txtProducto, 2);
                            FarmaUtility.moveFocus(txtProducto);
                        }
                        else
                        {
                                evaluaTitulo();
                                VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = false;
                        }
                    }
                }   
            }
            else
            {
                /*
                log.info("Paso Tarjeta : " + pasoTarjeta);
                if (pasoTarjeta)
                {   
                    txtProducto.setText("");
                    pasoTarjeta = false;
                    return;
                }*/

                //String pCadenaOriginal = txtProducto.getText().trim();
                //dubilluz 21.07.2011
                //setFormatoTarjetaCredito(txtProducto.getText().trim());
                //String pCadenaNueva = txtProducto.getText().trim();
                /*if(!pCadenaOriginal.trim().equalsIgnoreCase(pCadenaNueva.trim())&&pCadenaOriginal.trim().length()>0){
            pasoTarjeta = true;
            log.info("Es tarjeta...");
            }
            else{
            log.info("no es tarjeta");
            pasoTarjeta = false;
            }*/
            //log.info("pasoTarjeta:" + pasoTarjeta);


                //ini-Agregado FRAMIREZ
                /*if (VariablesConvenioBTLMF.vCodConvenio.trim().length() >
                    0 && pCadenaNueva.trim().length() == 6) {
                    String pAux =
                        DBConvenioBTLMF.getIndExcluidoConv(pCadenaNueva.trim());
                    if (pAux.equalsIgnoreCase("E")) {
                        log.debug("---Mensaje alerta convenios cobertura----");
                        DlgMensajeCobertura d =
                            new DlgMensajeCobertura(null,
                                                    "Mensaje Covertura",
                                                    true);
                        d.setLocationRelativeTo(myParentFrame);
                        d.setVisible(true);
                    }
                }*/
                //fin-Agregado FRAMIREZ

            try {
                //mfajardo 29/04/09 validar ingreso de productos virtuales   
                if (!VariablesModuloVentas.vProductoVirtual) {
                    e.consume();
                    if (myJTable.getSelectedRow() >= 0) {
                        String productoBuscar = vCadenaOriginal;
                            //txtProducto.getText().trim().toUpperCase();
                        String cadena = txtProducto.getText().trim();

                        //AGREGADO POR DVELIZ 26.09.08
                        //Cambiar en el futuro esto por una consulta a base de datos.
                        String formato = "";
                        if (cadena.trim().length() > 6)
                            formato = cadena.substring(0, 5);
                        /* if (formato.equals("99999")) {
                            if (UtilityFidelizacion.EsTarjetaFidelizacion(cadena)) {
                                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 
                                    0)
                                    FarmaUtility.showMessage(this, 
                                                             "No puede ingresar mas de una tarjeta.", 
                                                             txtProducto);
                                else {
                                    validarClienteTarjeta(cadena);
                                    //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 
                                        0)
                                        UtilityFidelizacion.operaCampañasFidelizacion(cadena);

                                    //DAUBILLUZ -- Filtra los DNI anulados
                                    //25.05.2009
                                    VariablesFidelizacion.vDNI_Anulado = 
                                            UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
                                    VariablesFidelizacion.vAhorroDNI_x_Periodo = 
                                            UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
                                    VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 
                                            UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);

                                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña,"L",txtProducto,this);
                                }
                                //vEjecutaAccionTeclaListado = false;
                                return;
                            } else {
                                FarmaUtility.showMessage(this, 
                                                         "La tarjeta no es valida", 
                                                         null);
                                txtProducto.setText("");
                                FarmaUtility.moveFocus(txtProducto);
                                //vEjecutaAccionTeclaListado = false;                        
                                return;
                            }
                        } */

                        
                        /* if (UtilityVentas.esCupon(cadena, this, 
                                                  txtProducto)) {
                            //agregarCupon(cadena);//metodo reemplazado por lo nuevo
                            //ERIOS 2.3.2 Valida convenio BTLMF
                            if (VariablesVentas.vEsPedidoConvenio ||
                                (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
                            ) {
                                FarmaUtility.showMessage(this, 
                                                         "No puede agregar cupones a un pedido por convenio.", 
                                                         null);
                                return;
                            }
                            validarAgregarCupon(cadena);

                            //vEjecutaAccionTeclaListado = false;

                            log.info("es CUpon :");
                            return;
                        } */
                        //21.08.2008 Daniel Veliz
                        VariablesCampana.vFlag = false;

                        //Dubilluz saber si ingreso una tarjeta y esta en una campaña automatica
                        //para q aparezca la pantalla de fidelizacion
                        //inicio dubilluz 15.07.2011
                        /* boolean pIsTarjetaEnCampana = 
                            UtilityFidelizacion.isTarjetaPagoInCampAutomatica(cadena.trim());
                        if (pIsTarjetaEnCampana) {
                            log.info("es una tarjeta de pago q esta en una campana automatica:");
                            validaIngresoTarjetaPagoCampanaAutomatica(cadena.trim());
                            return;
                        } */
                        //fin dubilluz 15.07.2011

                        if (productoBuscar.length() == 0) {
                            //vEjecutaAccionTeclaListado = false;
                        //    return;
                            if(tblProductos.getSelectedRow()!=-1){
                                //txtProducto.setText(FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,tblProductos.getSelectedRow(),2));
                                cadena = FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,tblProductos.getSelectedRow(),2);
                            }
                        }

                        String codigo = "";
                        if (cadena.length() > 1){
                            
                            // revisando codigo de barra
                            
                            
                            char primerkeyChar = ' ';
                            char segundokeyChar;
                            
                            if(cadena.length()>0){
                                primerkeyChar = cadena.charAt(0);
                            }
                            

                            if (cadena.length() > 1)
                                segundokeyChar = cadena.charAt(1);
                            else
                                segundokeyChar = primerkeyChar;

                            char ultimokeyChar = 
                                cadena.charAt(cadena.length() - 
                                                      1);
                            log.info("productoBuscar:" + 
                                               cadena);
                            
                            // busqueda codigo de barra
                            if (cadena.length() > 6 && 
                                (!Character.isLetter(primerkeyChar) && 
                                 (!Character.isLetter(segundokeyChar) && 
                                  (!Character.isLetter(ultimokeyChar))))) {
                                VariablesModuloVentas.vCodigoBarra = 
                                        cadena;
                                log.info("consulta cod barra antes");
                                productoBuscar = 
                                        DBModuloVenta.obtieneCodigoProductoBarra();
                                log.info("consulta cod barra despues");
                            }
                            
                            

                            log.info("productoBuscar new:" + 
                                               productoBuscar);
                                
                        }
                        
                        //JCORTEZ 23.07.2008
                        ///if (productoBuscar.equalsIgnoreCase("000000")&&UtilityVentas.esCupon(productoBuscar,this,txtProducto)) {
                        if (productoBuscar.equalsIgnoreCase("000000")) {
                            FarmaUtility.showMessage(this, 
                                                     "No existe producto relacionado con el Codigo de Barra. Verifique!!!", 
                                                     txtProducto);
                            //vEjecutaAccionTeclaListado = false;                  
                            return;
                        }

                        for (int k = 0; k < myJTable.getRowCount(); 
                             k++) {
                            codigo = 
                                    ((String)myJTable.getValueAt(k, COL_COD)).trim();
                            if (codigo.equalsIgnoreCase(productoBuscar)) {
                                FarmaGridUtils.showCell(myJTable, k, 
                                                        0);
                                //vEjecutaAccionTeclaListado = false;
                                break;
                            }
                        }

                        int vFila = myJTable.getSelectedRow();
                        String actualCodigo = 
                            FarmaUtility.getValueFieldJTable(myJTable, 
                                                             vFila, 
                                                             COL_COD);
                        String actualProducto = 
                            ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 
                                                          2))).trim().toUpperCase();
                        // Asumimos que codigo de producto ni codigo de barra empiezan con letra
                        //if (Character.isLetter(primerkeyChar)) 
                        /*{
                            txtProducto.setText(actualProducto);
                            productoBuscar = actualProducto;
                        }
                        txtProducto.setText(txtProducto.getText().trim());*/


                        // Comparando codigo y descripcion de la fila actual con el txtProducto
                        if ((actualCodigo.equalsIgnoreCase(productoBuscar)) || 
                            /* || 
                             actualProducto.substring(0, productoBuscar.length()).equalsIgnoreCase(productoBuscar)*/
                            (true)) {
                            //aqui  
                            btnBuscar.doClick();
                            //txtProducto.setText(actualProducto.trim());
                            txtProducto.selectAll();
                        } else if (productoBuscar.trim().length() <= 
                                   DIG_PROD && UtilityModuloVenta.esCupon(productoBuscar, 
                                                         this, 
                                                         txtProducto)) {
                            log.debug("productoBuscar.trim().length() " + 
                                               productoBuscar.trim().length());

                            FarmaUtility.showMessage(this, 
                                                     "Producto No Encontrado según Criterio de Búsqueda !!!", 
                                                     txtProducto);
                            //vEjecutaAccionTeclaListado = false;
                            return;
                        }

                    }
                } // producto virtual   
                else {
                    FarmaUtility.showMessage(this, 
                                             "Ya se selecciono un producto virtual", 
                                             txtProducto);
                } // producto virtual   
            } catch (Exception sql) {
                //log.error("",sql);
                //vEjecutaAccionTeclaListado = false;
                log.error(null, sql);
                FarmaUtility.showMessage(this, 
                                         "Error al buscar el Producto.\n" +
                        sql, txtProducto);
            }
            }
        }
        
        
    }
    private void txtProducto_keyPressed(KeyEvent e)
    {
        //ERIOS 15.01.2014 Correccion para lectura de escaner NCR
        //log.debug("Tecla: "+e.getKeyCode()+" ("+e.getKeyChar()+")");
        /*if(!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE || 
             e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
             e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME )){
            e.consume();
        }*/
        
        try
        {
            
            if(e.getKeyChar() != '+'&&
                !(
                (e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                (e.getKeyCode() == KeyEvent.VK_DOWN || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                e.getKeyCode() == KeyEvent.VK_ENTER||
                
                (e.getKeyCode()== KeyEvent.VK_F5||
                e.getKeyCode()== KeyEvent.VK_F1||
                e.getKeyCode()== KeyEvent.VK_F12)
                )
                )
                //filtroGoogle();
                log.debug("Caracter");
            else    
                //FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 2);
                FarmaGridUtils.aceptarTeclaPresionada(e, myJTable,new JTextField(), 2);
            log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));
            
            /*if (!vEjecutaAccionTeclaListado)
            {
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                 procesoEnter(e);
                } else {
                    vEjecutaAccionTeclaListado = false;
                    chkKeyPressed(e);
                }

            }*/
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
             procesoEnter(e);
            } else {
                chkKeyPressed(e);
            }
        } catch (Exception exc) {
            log.error("",exc);
            //log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            //vEjecutaAccionTeclaListado = false;
        }

        log.info("Fin Enter:" + pasoTarjeta);    }

    /**
     * @Author    Daniel Fernando Veliz La Rosa
     * @Since     15.08.08
     */
    private
    /* private void ingresarDatosCampana(){
      try{
          log.debug(VariablesCampana.vCodCampana);
          DlgListDatosCampana dlgListDatosCampana
                    = new DlgListDatosCampana(myParentFrame, "Datos Campaña", true);
          //FarmaUtility.centrarVentana(dlgListDatosCampana);
          dlgListDatosCampana.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            FarmaVariables.vAceptar = false ;

          }
      }catch(Exception e){
          log.error("",e);;
      }
  }*/
    //JQUISPE  03.05.2010 Cambio para modificar la busqueda de productos en la lista.  
    void txtProducto_keyReleased(KeyEvent e) {
            
            if(tblProductos.getRowHeight()==0&&txtProducto.getText().trim().length()==0){
                clonarListadoProductos();
                iniciaProceso(true);
                lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
            }
                
            
            if(e.getKeyChar() != '+'&&
                !(
                (e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                (e.getKeyCode() == KeyEvent.VK_DOWN || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                e.getKeyCode() == KeyEvent.VK_ENTER||
                (e.getKeyCode()== KeyEvent.VK_F5||
                e.getKeyCode()== KeyEvent.VK_F1||
                e.getKeyCode()== KeyEvent.VK_F12))
            
            ){
                filtroGoogle();
                iniciaProceso(true);
                }
                //log.debug("Caracter");
            else    
            if(tblProductos.getRowCount() >= 0 && 
                tableModelListaPrecioProductos.getRowCount() > 0 && 
                e.getKeyChar() != '+') {
                if (FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 
                                                     2) || 
                    (e.getKeyCode() == KeyEvent.VK_UP || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                    (e.getKeyCode() == KeyEvent.VK_DOWN || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    VariablesModuloVentas.vPosNew = tblProductos.getSelectedRow();
                    if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                        UpdateReleaseProd(e);
                        VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                    } else {
                        if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                            UpdateReleaseProd(e);
                            VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                        }
                    }

                    /*muestraNombreLab(4, lblDescLab_Prod);
            muestraProductoInafectoIgv(11, lblProdIgv);
            muestraProductoRefrigerado(15,lblProdRefrig);
            muestraProductoPromocion(17,lblProdProm);
            muestraIndTipoProd(16,lblIndTipoProd);
            // JCORTEZ 08.04.2008
            muestraProductoEncarte(COL_IND_ENCARTE,lblProdEncarte);

            muestraInfoProd();
            muestraProductoCongelado(lblProdCong);
            if ( !(e.getKeyCode()==KeyEvent.VK_ESCAPE) ) {
            if ( myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS) ) {

              actualizaListaProductosAlternativos();
            }
            }
            colocaTotalesPedido();

            */
                }
            }


            ///---

            /* if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&  VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
            {

            }
            else
            { if(myJTable.getSelectedRow() >= 0){
            if (isExisteProdCampana(myJTable.getValueAt(myJTable.getSelectedRow(),
                                                        COL_COD).toString().trim())) {
                lblMensajeCampaña.setVisible(true);
            } else {
                // dubilluz 26.05.2009
                AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña,"L",txtProducto,this);
                //lblMensajeCampaña.setVisible(false);
            }}
            } */
    }

    private void txtProducto_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ')
        {   if(txtProducto.getText().length()==0){
                    clonarListadoProductos();
                    lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
                }
        }
    }

    private void btnProdAlternativos_actionPerformed(ActionEvent e) {
        setJTable(tblListaSustitutos);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
       // muestraIndTipoProd(16, lblIndTipoProd);
        /**
     * Muestra Promocion
     * @author : dubilluz
     * @since  : 28.06.2007
     */
        muestraProductoPromocion(17, lblProdProm);

        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        setJTable(tblProductos);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
      //  muestraIndTipoProd(16, lblIndTipoProd);
        /**
     * Muestra Promocion
     * @author : dubilluz
     * @since  : 28.06.2007
     */
        muestraProductoPromocion(17, lblProdProm);

        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        verificaCheckJTable();
    }

    private void this_windowClosing(WindowEvent e) {
        cancelaOperacion();
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez 
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e)
    {
        try
        {
            if (!vEjecutaAccionTeclaListado)
            {   
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {   e.consume();
                }
                else if (UtilityPtoVenta.verificaVK_F1(e))
                {   muestraDetalleProducto();
                }
                else if (UtilityPtoVenta.verificaVK_F2(e))
                {
                    
                    //muestraProductosAlternativos();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F5)
                {   //cargaListaFiltro();
                    cambiaCotiza();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F7)
                {  // filtroGoogle();
                quitarFiltro();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F8)
                {
                    //ingresaReceta();
                    //Dubilluz - 06.12.2011
                    //ingresaMedicoFidelizado();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F9)
                {   
                }
                else if (UtilityPtoVenta.verificaVK_F10(e))
                {
                    e.consume();
                    faltacero();
                }
                else if (UtilityPtoVenta.verificaVK_F11(e))
                {
                    VariablesModuloVentas.vIndDireccionarResumenPed = true;
                    VariablesModuloVentas.vIndF11 = true;
                    aceptaOperacion();
                }
                else if (UtilityPtoVenta.verificaVK_F12(e))
                {
                    
                    DlgSeleccionIngresoPedCotiza dlgReceta = new DlgSeleccionIngresoPedCotiza(myParentFrame, "", true);
                    dlgReceta.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                       if(dlgReceta.isVCotiza())
                            ingresoCotizacionNuevo();
                       else
                            ingresoPedidoAnulado();
                           
                    }
                   
                    /*if(!VariablesVentas.vEsPedidoInstitucional)
          {
            if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea iniciar una venta institucional?"))
            {
              if(UtilityVentas.evaluaPedidoInstitucional(this, txtProducto, VariablesVentas.vArrayList_PedidoVenta)){
                evaluaTitulo();
              }
            }
          }
          else
          {
            VariablesVentas.vEsPedidoInstitucional = false;
            evaluaTitulo();
          }*/
                  //  funcionF12("N");
                    
                    
                }
                else if (e.getKeyCode() == KeyEvent.VK_F3)
                {
                    /*if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this,null))
                    {
                        //Agregado FRAMIREZ - 20.10.2011
                        cargarConvenioBTL();
                    }
                    else
                    {   cargaConvenio();
                    }*/
                }
                else if (e.getKeyCode() == KeyEvent.VK_F4)
                {//
                }
                else if (e.getKeyCode() == KeyEvent.VK_F5)
                {
                    cambiaCotiza();
                    /* if (VariablesModuloVentas.vEsPedidoConvenio||(VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)) {
                            
                        FarmaUtility.showMessage(this, 
                                                 "No puede agregar estas promociones a un " + 
                                                 "pedido por convenio.", txtProducto);
                        return;
                    }                    
                    else {
                        
                        //vEjecutaAccionTeclaListado = false;
                        int vFila = myJTable.getSelectedRow();
                        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
                        String indProm = (String)(myJTable.getValueAt(vFila, 17));

                        if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS))
                            VariablesModuloVentas.vCodProdFiltro =
                                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        else
                            VariablesModuloVentas.vCodProdFiltro = "";

                        if (indProm.equalsIgnoreCase("S")) { //if(!valor.booleanValue())
                            muestraPromocionProd(VariablesModuloVentas.vCodProdFiltro);
                            //else
                            //  FarmaUtility.showMessage(this,"El Producto está en una Promoción ya seleccionada",txtProducto);
                        } else
                            muestraPromocionProd(VariablesModuloVentas.vCodProdFiltro);
                    } */
                } /*else if(e.getKeyChar() == '*') {//add jcallo 15/12/2008 campanias acumuladas.
            //veririficar que el producto seleccionado tiene el flag de campanias acumuladas.

            //validar que no sea un pedido por convenio
            if(VariablesVentas.vEsPedidoConvenio)
            {
                  FarmaUtility.showMessage(this,
                                           "No puede asociar clientes a campañas de ventas acumuladas en un " +
                                           "pedido por convenio.", txtProducto);
             //     return;
            }else {//toda la logica para asociar un cliente hacia campañas nuevas

                int rowSelec = myJTable.getSelectedRow();
                if(rowSelec >= 0
                   //&& myJTable.getModel().getValueAt(rowSelec,10).toString().equals("S")
                    ){//validar si el producto seleccionado tiene alguna campaña asociada
                    String auxCodProd = myJTable.getValueAt(rowSelec, COL_COD).toString().trim();
                    asociarCampAcumulada(auxCodProd);

                }
            }

        }*/ else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //vEjecutaAccionTeclaListado = false;
                    cancelaOperacion();
                    // Inicio Adicion Delivery 28/04/2006 Paulo
                    //limpiaVariables();
                    // Fin Adicion Delivery 28/04/2006 Paulo
                } 
                //vEjecutaAccionTeclaListado = false;

            }

        } //try
        catch (Exception exc) {
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
            log.debug(" finally: " + vEjecutaAccionTeclaListado);

        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesModuloVentas.vVentanaListadoProductos = false;
        VariablesModuloVentas.vIndDireccionarResumenPed = pAceptar;
        VariablesModuloVentas.vKeyPress = null;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void muestraNombreLab(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String nombreLab = FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            pLabel.setText(nombreLab);
        }
    }

    private void muestraProductoInafectoIgv(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String inafectoIgv = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            if (FarmaUtility.getDecimalNumber(inafectoIgv) == 0.00)
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }


    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        int vFila = myJTable.getSelectedRow();
        String codProd = 
            FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        //JMIRANDA 22/09/2009 lleno la variable vCod_Prod
        VariablesModuloVentas.vCod_Prod = codProd;
        try {
            //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
            if (VariablesModuloVentas.vEsPedidoConvenio) {
                DBModuloVenta.obtieneInfoProducto(pArrayList, codProd);
            } else {
                DBModuloVenta.obtieneInfoProductoVta(pArrayList, codProd);
                //log.debug("codProd"+codProd);  
            }

        } catch (SQLException sql) {
            //log.error("",sql);
            log.error(null, sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void muestraInfoProd() {
        if (myJTable.getRowCount() <= 0)
            return;

        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        log.debug("Tamaño en muestra info" + myArray.size());

        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            valPrecPres = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            valPrecVta = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            indProdHabilVta = 
                    ((String)((ArrayList)myArray.get(0)).get(7)).trim();
            porcDscto_1 = ((String)((ArrayList)myArray.get(0)).get(8)).trim();
            //log.info("DLGLISTAPRODUCTOS : porcDscto_1 - " + porcDscto_1);
            tipoProd = ((String)((ArrayList)myArray.get(0)).get(9)).trim();
           // muestraIndTipoProd(16, lblIndTipoProd, tipoProd);
            bonoProd = ((String)((ArrayList)myArray.get(0)).get(10)).trim();
            stkFracLoc = FarmaUtility.getValueFieldArrayList(myArray, 0, 11);
            descUnidFracLoc = 
                    FarmaUtility.getValueFieldArrayList(myArray, 0, 12);
        } else {
            stkProd = "";
            descUnidPres = "";
            valFracProd = "";
            valPrecPres = "";
            indProdCong = "";
            valPrecVta = "";
            descUnidVta = "";
            indProdHabilVta = "";
            porcDscto_1 = "";
            tipoProd = "";
            bonoProd = "";
            stkFracLoc = "";
            descUnidFracLoc = "";
            FarmaUtility.showMessage(this, 
                                     "Error al obtener Informacion del Producto", 
                                     txtProducto);
        }
        lblUnidad.setText(descUnidPres);
        lblPrecio.setText(valPrecPres);
        lblStockAdic.setText(stkFracLoc);
        lblUnidFracLoc.setText(descUnidFracLoc);
        myJTable.setValueAt(stkProd, myJTable.getSelectedRow(), 5);
        myJTable.setValueAt(valPrecVta, myJTable.getSelectedRow(), 6);
        myJTable.setValueAt(descUnidVta, myJTable.getSelectedRow(), 3);
        myJTable.setValueAt(bonoProd, myJTable.getSelectedRow(), 7);

        VariablesModuloVentas.vVal_Frac = valFracProd;
        VariablesModuloVentas.vInd_Prod_Habil_Vta = indProdHabilVta;
        VariablesModuloVentas.vPorc_Dcto_1 = porcDscto_1;
        log.info("DLGLISTAPRODUCTOS : VariablesVentas.vPorc_Dcto_1 - " + 
                           porcDscto_1);
        myJTable.repaint();
    }

    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        //txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            //FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
            muestraInfoProd();
        }
        //FarmaUtility.moveFocus(txtProducto);
    }

    private void muestraDetalleProducto() {
        vEjecutaAccionTeclaListado = false;
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesModuloVentas.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesModuloVentas.vDesc_Prod = 
                ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesModuloVentas.vNom_Lab = 
                ((String)(myJTable.getValueAt(vFila, 4))).trim();

        DlgDetalleProducto dlgDetalleProducto = 
            new DlgDetalleProducto(myParentFrame, "", true);
        dlgDetalleProducto.setVisible(true);
    }

    private void muestraIngresoCantidad() {
        if (myJTable.getRowCount() == 0)
            return;
        FarmaVariables.vAceptar = true;
        //JMIRANDA 21/09/2009
        boolean flagContinua = true;

        //log.info("Precio old :" + 
        //                   VariablesConvenio.vVal_Prec_Vta_Conv);
        //log.info("Precio old :" + VariablesConvenio.vCodConvenio);
        //log.info("Precio old :" + VariablesVentas.vCod_Prod);
        //log.info("Precio old :" + VariablesVentas.vVal_Prec_Pub);
        //--ANTES COLOCADO POR JMIRANDA
        //  15.10.2009 jmiranda
        //  cambiado por error encontrado DUBILLUZ
        //guardaInfoProdVariables();

        //log.info("Precio new :" + 
        //                   VariablesConvenio.vVal_Prec_Vta_Conv);

        try {
            //log.debug("vAceptar 1: " + FarmaVariables.vAceptar);

            // Verifica si es obligatorio ingresar codigo de barra 
            //log.debug("vAceptar2 : " + FarmaVariables.vAceptar);
            //log.debug("vCod_Prod: " + VariablesVentas.vCod_Prod);
            if (DBModuloVenta.getIndCodBarra(VariablesModuloVentas.vCod_Prod).equalsIgnoreCase("S") && 
                FarmaVariables.vAceptar && VariablesModuloVentas.vIndEsCodBarra) {

                DlgIngreseCodBarra dlgIngCodBarra = 
                    new DlgIngreseCodBarra(myParentFrame, "", true);
                //dlgIngCodBarra.setVisible(true);
                //valida si se ha insertado cod Barra
                if (UtilityModuloVenta.validaCodBarraLocal(txtProducto.getText().trim())) {
                    dlgIngCodBarra.setVisible(true);
                    flagContinua = VariablesModuloVentas.bIndCodBarra;
                }
                if (VariablesModuloVentas.vCodigoBarra.equalsIgnoreCase(txtProducto.getText().trim())) {
                    flagContinua = true;
                }
                //flagContinua = VariablesVentas.bIndCodBarra; 
                FarmaVariables.vAceptar = flagContinua;
                log.debug("COD_BArra, flagcontinua: " + flagContinua);
            }

            // Verifica si posee Mensaje de Producto
            // VariablesVentas.vMensajeProd = DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
            String vMensajeProd = "N";
                //DBVentas.getMensajeProd(VariablesVentas.vCod_Prod);
            if (!vMensajeProd.equalsIgnoreCase("N") && 
                FarmaVariables.vAceptar) {

                String sMensaje = "";
                sMensaje = UtilityModuloVenta.saltoLineaConLimitador(vMensajeProd);
                //ENVIO vMensajeProd LLAMAR METODO RETORNAR SMENSAJE CON SALTO DE LINEA
                log.debug(sMensaje);
                //flagContinua = JConfirmDialog.rptaConfirmDialogDefaultNo(this,sMensaje);
                //flagContinua = componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,sMensaje);
                //FarmaVariables.vAceptar = flagContinua;
                //log.debug("Mensaje flagContinua: "+flagContinua);                           
                //JMIRANDA 26.03.2010
                FarmaUtility.showMessage(this,"Aa"+sMensaje, txtProducto);
            }

            // Valida ID Usuario 
            String pInd = DBModuloVenta.getIndSolIdUsu(VariablesModuloVentas.vCod_Prod).trim().toUpperCase();
            //if(DBVentas.getIndSolIdUsu(VariablesVentas.vCod_Prod).equalsIgnoreCase("S")
            if (pInd.equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
                //llamar a Usuario para visualizar 
                flagContinua = validaLoginVendedor();
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua: " + flagContinua);
                /*if (!flagContinua){
             } */
            } else {
                if (pInd.equalsIgnoreCase("J") && FarmaVariables.vAceptar) {
                    log.debug("*** Valida Producto Venta Cero");
                    //llamar a Usuario para visualizar 
                    flagContinua = validaLoginQF();
                    FarmaVariables.vAceptar = flagContinua;
                    log.debug("SolId. flagContinua : " + 
                                       flagContinua);
                    /*if (!flagContinua){
                 } */
                }
            }



        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error en Validar Producto: " + sql, 
                                     txtProducto);
            log.error("",sql);
        }

        if (flagContinua) {

            int vFila = myJTable.getSelectedRow();
            VariablesModuloVentas.vCod_Prod = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
            VariablesModuloVentas.vDesc_Prod = 
                    ((String)(myJTable.getValueAt(vFila, 2))).trim();
            VariablesModuloVentas.vNom_Lab = 
                    ((String)(myJTable.getValueAt(vFila, 4))).trim();
            VariablesModuloVentas.vPorc_Igv_Prod = 
                    ((String)(myJTable.getValueAt(vFila, 11))).trim();
            VariablesModuloVentas.vCant_Ingresada_Temp = "0";
            VariablesModuloVentas.vNumeroARecargar = "";
            VariablesModuloVentas.vVal_Prec_Lista_Tmp = "";
            VariablesModuloVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
            VariablesModuloVentas.vTipoProductoVirtual = "";
            VariablesModuloVentas.vVal_Prec_Pub = 
                    ((String)(myJTable.getValueAt(vFila, 6))).trim();
            VariablesModuloVentas.vIndOrigenProdVta = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                     COL_ORIG_PROD);
            VariablesModuloVentas.vPorc_Dcto_2 = "0";
            VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
            VariablesModuloVentas.vCantxDia = "";
            VariablesModuloVentas.vCantxDias = "";


            //Busca precio de convenio BTLMF
            /*
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) &&
            		VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0 &&
                VariablesVentas.vCod_Prod.trim().length() == 6 && VariablesVentas.vEstadoProdConvenio.equals("I"))
            {

                VariablesConvenioBTLMF.vValidaPrecio = true;
                SubProcesosConvenios precConv = new SubProcesosConvenios();
                precConv.start();
            }
            else
            {

            guardaInfoProdVariables();
            }*/
            guardaInfoProdVariables();
            //txtProducto.setText("");
            DlgIngresoCantidad dlgIngresoCantidad = 
                new DlgIngresoCantidad(myParentFrame, "", true,ConstantsPtoVenta.TIPO_VENTA);
            VariablesModuloVentas.vIngresaCant_ResumenPed = false;
            dlgIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                seleccionaProducto();
                VariablesModuloVentas.vIndDireccionarResumenPed = false;
                FarmaVariables.vAceptar = false;
            } else {
                VariablesModuloVentas.vIndDireccionarResumenPed = false;
            }
            //txtProducto.setText("");
        }

    }

    private void colocaTotalesPedido() {
        
        muestraTituloCliente();
        
        calculaTotalVentaPedido();
        totalItems = VariablesModuloVentas.vArrayList_PedidoVenta.size() + VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size() + VariablesModuloVentas.vArrayList_Prod_Promociones.size();
        lblItems.setText("" + totalItems);
        lblTotalVenta.setText(FarmaUtility.formatNumber(totalVenta, 2));
        
        pintaProductosSinStock();
    }

    /**
     * Calcula el monto total de venta
     * @author dubilluz
     * @since  18.06.2007
     */
    private void calculaTotalVentaPedido() {
        totalVenta = 0;
        for (int i = 0; i < VariablesModuloVentas.vArrayList_PedidoVenta.size(); i++)
            totalVenta += 
                    FarmaUtility.getDecimalNumber(((String)((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(7)).trim());

        double totalProd_Prom = 0.00;

        ArrayList aux = new ArrayList();
        for (int i = 0; 
             i < VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size(); 
             i++) {
            aux = 
(ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.get(i);
            //log.debug(FarmaUtility.getDecimalNumber(""+aux.get(6))+"");
            totalProd_Prom += FarmaUtility.getDecimalNumber("" + aux.get(7));
        }

        ArrayList aux2 = new ArrayList();
        for (int i = 0; i < VariablesModuloVentas.vArrayList_Prod_Promociones.size(); 
             i++) {
            aux2 = 
(ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones.get(i);
            //log.debug(FarmaUtility.getDecimalNumber(""+aux.get(6))+"");
            totalProd_Prom += FarmaUtility.getDecimalNumber("" + aux2.get(7));
        }

        totalVenta += totalProd_Prom;
    }

    private void verificaCheckJTable() {
        int vFila = myJTable.getSelectedRow();
        String codigo = 
            FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);

        if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS))
            actualizaListaProductosAlternativos();

        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        if (valor.booleanValue()) {
            if (!buscar(VariablesModuloVentas.vArrayList_Prod_Promociones, codigo) && 
                !buscar(VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                        codigo)) {
                deseleccionaProducto();
            } else {
                FarmaUtility.showMessage(this, 
                                         "El Producto se encuentra en una Promoción", 
                                         txtProducto);
                return;
            }
        } else {
            muestraInfoProd();
            VariablesModuloVentas.vIndProdVirtual = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, 13);

            if (!validaStockDisponible() && 
                !VariablesModuloVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                return;
            }
            if (!validaProductoTomaInventario(vFila)) {
                FarmaUtility.showMessage(this, 
                                         "El Producto se encuentra Congelado por Toma de Inventario", 
                                         txtProducto);
                return;
            }
            if (!validaProductoHabilVenta()) {
                FarmaUtility.showMessage(this, 
                                         "El Producto NO se encuentra hábil para la venta. Verifique!!!", 
                                         txtProducto);
                return;
            }

            VariablesModuloVentas.vIndProdVirtual = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, 13);

            log.debug("VariablesVentas.vIndPedConProdVirtual " + VariablesModuloVentas.vIndPedConProdVirtual);
            if (VariablesModuloVentas.vIndProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S) || VariablesModuloVentas.vIndPedConProdVirtual) {
                //Modificado para que no pueda comprar Nada si hay Promociones
                if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0 || VariablesModuloVentas.vArrayList_Prod_Promociones.size() > 0 || VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size() > 
                    0 || VariablesModuloVentas.vArrayList_Promociones.size() > 0 || VariablesModuloVentas.vArrayList_Promociones_temporal.size() > 
                    0) {
                    log.debug("Se esta validando por compra ");
                    FarmaUtility.showMessage(this, 
                                             "La venta de un Producto Virtual debe ser única por pedido. Verifique!!!", 
                                             txtProducto);
                    return;
                } else {
                    VariablesModuloVentas.vIndProdControlStock = false;
                    VariablesModuloVentas.vIndPedConProdVirtual = true;
                    evaluaTipoProdVirtual();
                    VariablesModuloVentas.vIndPedConProdVirtual = false;
                }
            } else {
                VariablesModuloVentas.vIndProdControlStock = true;
                VariablesModuloVentas.vIndPedConProdVirtual = false;
                //aqui
                muestraIngresoCantidad();
            }
        }

        //txtProducto.selectAll();
       // muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoCongelado(lblProdCong);
        muestraProductoRefrigerado(15, lblProdRefrig);
        /**
     * Diego
     */
        muestraProductoPromocion(17, lblProdProm);
        //muestraIndTipoProd(16, lblIndTipoProd);
        //JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        aceptaOperacion();

    }

    private void seleccionaProducto() {
        int vFila = myJTable.getSelectedRow();
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));

        double auxCantIng = 
            FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada);
        int aux2CantIng = (int)auxCantIng;
        double auxPrecVta = 
            FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta);
        VariablesModuloVentas.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
        VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (VariablesModuloVentas.vIndProdControlStock &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                   aux2CantIng,
                                                   ConstantsVentas.INDICADOR_A,
                                                   ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                   aux2CantIng,
                                                   true,
                                                   this,
                                                   txtProducto,))*/
            !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                aux2CantIng, ConstantsModuloVenta.INDICADOR_A, 
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, aux2CantIng, 
                true, this, txtProducto, ""))
            return;

        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        UtilityModuloVenta.operaProductoSeleccionadoEnArrayList_02(valor, VariablesModuloVentas.secRespStk); //ASOSA, 01.07.2010
        pintaCheckOtroJTable(myJTable, valorTmp);
        colocaTotalesPedido();
    }

    private void deseleccionaProducto() {
        String cantidad = "";
        int vFila = myJTable.getSelectedRow();

        VariablesModuloVentas.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        String indicadorControlStock = FarmaConstants.INDICADOR_S;
        String codigoTmp = "";

        String secRespaldo = ""; //ASOSA, 01.07.2010

        for (int i = 0; i < VariablesModuloVentas.vArrayList_PedidoVenta.size(); 
             i++) {
            codigoTmp = 
                    (String)((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(0);
            if (VariablesModuloVentas.vCod_Prod.equalsIgnoreCase(codigoTmp)) {
                indicadorControlStock = 
                        FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_PedidoVenta, 
                                                            i, 16);
                cantidad = 
                        (String)((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(4);
                secRespaldo = 
                        (String)((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(26); //ASOSA, 01.07.2010
                VariablesModuloVentas.vVal_Frac = 
                        FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_PedidoVenta, 
                                                            i, 10);
                break;
            }
        }

        int aux2CantIng = Integer.parseInt(cantidad);

        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
        VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if (indicadorControlStock.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                    aux2CantIng,
                                                    ConstantsVentas.INDICADOR_A,
                                                    ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                    aux2CantIng,
                                                    true,
                                                    this,
                                                    txtProducto,))*/
            !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                0, ConstantsModuloVenta.INDICADOR_A, 
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 0, true, this, 
                txtProducto, secRespaldo)) {
            return;
        }

        FarmaUtility.setCheckValue(myJTable, false);
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        UtilityModuloVenta.operaProductoSeleccionadoEnArrayList_02(valor, VariablesModuloVentas.secRespStk); //ASOSA, 01.07.2010

        if (VariablesModuloVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesModuloVentas.vIndPedConProdVirtual = false;

        pintaCheckOtroJTable(myJTable, valorTmp);
        //indicadorItems = FarmaConstants.INDICADOR_N;
        colocaTotalesPedido();
    }

    private boolean validaProductoTomaInventario(int pRow) {
        if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return true;
        else
            return false;
    }

    private boolean validaProductoHabilVenta() {
        if (VariablesModuloVentas.vInd_Prod_Habil_Vta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }

    private boolean validaStockDisponible() {
        if (FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) > 0)
            return true;
        else{
            if(FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) == 0 && UtilityModuloVenta.getIndVtaNegativa())
              return true;
          else
            return false;
        }
    }

    private void cancelaOperacion() {
        String codProd = "";
        String codProdTmp = "";
        String indControlStk = "";
        boolean existe = false;

        String secRespaldo = ""; //ASOSA, 01.07.2010

        for (int i = 0; i < VariablesModuloVentas.vArrayList_PedidoVenta.size(); 
             i++) {
            codProd = 
                    (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(0));
            VariablesModuloVentas.vVal_Frac = 
                    FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_PedidoVenta, 
                                                        i, 10);
            String cantidad = 
                (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(4));
            indControlStk = 
                    FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_PedidoVenta, 
                                                        i, 16);

            secRespaldo = 
                    (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(26)); //ASOSA, 01.07.2010

            for (int j = 0; 
                 j < VariablesModuloVentas.vArrayList_ResumenPedido.size(); j++) {
                codProdTmp = 
                        (String)(((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(j)).get(0));

                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                int aux2CantIng = 
                    FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad));
                VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                        aux2CantIng,
                                                        ConstantsVentas.INDICADOR_A,
                                                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                        aux2CantIng,
                                                        true,
                                                        this,
                                                        txtProducto,))*/
                    !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                        //ASOSA, 01.07.2010
                        aux2CantIng, ConstantsModuloVenta.INDICADOR_A, 
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 
                        aux2CantIng, true, this, txtProducto, secRespaldo))
                    return;
            }

            existe = false;
        }

        for (int i = 0; i < VariablesModuloVentas.vArrayList_ResumenPedido.size(); 
             i++) {
            codProd = 
                    (String)(((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i)).get(0));
            VariablesModuloVentas.vVal_Frac = 
                    FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_ResumenPedido, 
                                                        i, 10);
            String cantidad = 
                (String)(((ArrayList)VariablesModuloVentas.vArrayList_ResumenPedido.get(i)).get(4));
            String cantidadTmp = "0";
            indControlStk = 
                    FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_ResumenPedido, 
                                                        i, 16);

            secRespaldo = 
                    (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i)).get(26)); //ASOSA, 01.07.2010

            log.debug("",existe);

            for (int j = 0; j < VariablesModuloVentas.vArrayList_PedidoVenta.size(); 
                 j++) {
                codProdTmp = 
                        (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(j)).get(0));

                if (codProd.equalsIgnoreCase(codProdTmp)) {
                    existe = true;
                    cantidadTmp = 
                            (String)(((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(j)).get(4));
                    break;
                }
            }
            log.debug("",existe);

            if (!existe) {
                int aux2CantIng = 
                    FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad));
                VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                        aux2CantIng,
                                                        ConstantsVentas.INDICADOR_A,
                                                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                        aux2CantIng,
                                                        true,
                                                        this,
                                                        txtProducto,))*/
                    !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                        //ASOSA, 01.07.2010
                        aux2CantIng, ConstantsModuloVenta.INDICADOR_A, 
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 
                        aux2CantIng, true, this, txtProducto, secRespaldo))
                    return;
            } else {
                int aux3CantIng = Integer.parseInt(cantidad);
                int aux4CantIngTmp = Integer.parseInt(cantidadTmp);

                if (aux3CantIng < aux4CantIngTmp) {
                    int aux5CantIng = aux4CantIngTmp - aux3CantIng;
                    VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                    if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                        /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                          aux2CantIng,
                                                          ConstantsVentas.INDICADOR_A,
                                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                          aux2CantIng,
                                                          true,
                                                          this,
                                                          txtProducto,))*/
                        !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                            //ASOSA, 01.07.2010
                            aux5CantIng, ConstantsModuloVenta.INDICADOR_A, 
                            ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 
                            aux3CantIng, true, this, txtProducto, secRespaldo))
                        return;
                } else if (aux3CantIng > aux4CantIngTmp) {
                    int aux5CantIng = aux3CantIng - aux4CantIngTmp;
                    VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                    if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                        /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod, //ANTES; ASOSA, 01.07.2010
                                                          aux2CantIng,
                                                          ConstantsVentas.INDICADOR_A,
                                                          ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                                          aux2CantIng,
                                                          true,
                                                          this,
                                                          txtProducto,))*/
                        !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                            //ASOSA, 01.07.2010
                            aux5CantIng, ConstantsModuloVenta.INDICADOR_A, 
                            ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 
                            aux3CantIng, true, this, txtProducto, secRespaldo))
                        return;
                }
            }
            existe = false;
        }

        cancela_promociones();
        cerrarVentana(false);
    }

    private void aceptaOperacion() {
        log.info("ENTRO A ACEPTAR OPERACION");
        log.debug("<<TCT 3>> Genera Resumen de Pedido ITEMS= " + VariablesModuloVentas.vArrayList_PedidoVenta.size());
        vEjecutaAccionTeclaListado = false;
        VariablesModuloVentas.vArrayList_ResumenPedido = new ArrayList();
        for (int i = 0; i < VariablesModuloVentas.vArrayList_PedidoVenta.size(); i++)
            VariablesModuloVentas.vArrayList_ResumenPedido.add((ArrayList)VariablesModuloVentas.vArrayList_PedidoVenta.get(i));
        //cargar();
        
        for (int i = 0; i < VariablesModuloVentas.vArrayList_Promociones_temporal.size(); i++)
            VariablesModuloVentas.vArrayList_Promociones.add((ArrayList)((ArrayList)VariablesModuloVentas.vArrayList_Promociones_temporal.get(i)).clone());


        VariablesModuloVentas.vArrayList_Promociones_temporal = new ArrayList();

        for (int i = 0;i < VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size(); i++)
            VariablesModuloVentas.vArrayList_Prod_Promociones.add((ArrayList)((ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.get(i)).clone());

        VariablesModuloVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
            //log.debug(VariablesVentas.vArrayList_ResumenPedido.size());

             /*
             * Solo se cerrara si el indicador lo permite
             * dubilluz 11.04.2008
             */
                if (VariablesModuloVentas.vIndDireccionarResumenPed)
                    cerrarVentana(true);
     log.debug("<<TCT 3>> Despues de Carga Resumen Hay Prom= " + VariablesModuloVentas.vArrayList_Promociones.size());           
     
 }


    private void cargaListaFiltro() {
        //ERIOS 29.08.2013 Al filtrar, se muestra "[ F7 ] Quitar Filtro"
        vEjecutaAccionTeclaListado = false;

        if (VariablesModuloVentas.vCodFiltro.equalsIgnoreCase("")) {
            DlgFiltroProductos dlgFiltroProductos = 
                new DlgFiltroProductos(myParentFrame, "", true);
            dlgFiltroProductos.setVisible(true);
        } else {
            muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            log.debug("VariablesPtoVenta.vTipoFiltro  : " + VariablesModuloVentas.vCodFiltro);
            VariablesPtoVenta.vTipoFiltro = VariablesModuloVentas.vCodFiltro;
            VariablesPtoVenta.vCodFiltro = "";
            //polimorfismo
            /*VariablesVentas.vCodFiltro_temp=VariablesVentas.vCodFiltro;
      if(!VariablesVentas.vCodFiltro_temp.equalsIgnoreCase("")){
        filtrarTablaProductos(VariablesVentas.vCodFiltro_temp,VariablesPtoVenta.vTipoFiltro,VariablesPtoVenta.vCodFiltro);
      }*/
            FarmaVariables.vAceptar = true;
        }

        if (FarmaVariables.vAceptar) {
            tblModelListaSustitutos.clearTable();
            txtProducto.setText("");

            filtrarTablaProductos(VariablesPtoVenta.vTipoFiltro, 
                                  VariablesPtoVenta.vCodFiltro);
            setJTable(tblProductos);
            //iniciaProceso(false);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_PedidoVenta, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                          0);

            FarmaVariables.vAceptar = false;
            
           // lblF7.setText("[ F7 ] Quitar Filtro");
        }

        VariablesPtoVenta.vTipoFiltro = "";
        VariablesPtoVenta.vCodFiltro = "";
        VariablesModuloVentas.vCodFiltro = "";
    }


    private void filtrarTablaProductos(String pTipoFiltro, String pCodFiltro) {
        try {
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.fireTableDataChanged();
            DBModuloVenta.cargaListaProductosVenta_Filtro(tableModelListaPrecioProductos, 
                                                     pTipoFiltro, pCodFiltro);
            if (tableModelListaPrecioProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblProductos, 
                                     tableModelListaPrecioProductos, 
                                     COL_ORD_LISTA, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
            //else
            //FarmaUtility.showMessage(this, "Resultado vacio", null);
        } catch (SQLException sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener Lista de Productos Filtrado!!!", 
                                     txtProducto);
        }
    }

    private void muestraProductosAlternativos() {
        vEjecutaAccionTeclaListado = false;
        int vFila = tblProductos.getSelectedRow();

        VariablesModuloVentas.vCodProdOrigen_Alter = 
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD);
        //VariablesVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(tblProductos,vFila,1);
        VariablesModuloVentas.vDesc_Prod = 
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 2);
        VariablesModuloVentas.vNom_Lab = 
                FarmaUtility.getValueFieldJTable(tblProductos, vFila, 4);
        VariablesModuloVentas.vUnid_Vta = lblUnidad.getText();

        DlgProductosAlternativos dlgProductosAlternativos = 
            new DlgProductosAlternativos(myParentFrame, "", true);
        dlgProductosAlternativos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            aceptaOperacion();
            /*log.debug("Se refresca el listado temporal");
      FarmaVariables.vAceptar = false;

      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_PedidoVenta,0);
      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_Prod_Promociones_temporal,0);
      FarmaUtility.ponerCheckJTable(tblProductos,COL_COD,VariablesVentas.vArrayList_Prod_Promociones,0);

      colocaTotalesPedido();

      setJTable(tblProductos);
      FarmaUtility.setearPrimerRegistro(tblProductos,txtProducto,2);
      actualizaListaProductosAlternativos();
      muestraNombreLab(4, lblDescLab_Prod);
      muestraProductoInafectoIgv(11, lblProdIgv);
      muestraProductoPromocion(17,lblProdProm);
      muestraProductoRefrigerado(15,lblProdRefrig);
      muestraIndTipoProd(16,lblIndTipoProd);
      muestraInfoProd();
      muestraProductoCongelado(lblProdCong);*/

        } else {
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
        }

    }

    private void actualizaListaProductosAlternativos() {
        tblModelListaSustitutos.clearTable();
        tblModelListaSustitutos.fireTableDataChanged();
        //ERIOS 09.04.2008 Se muestra los sustitutos para todos los productos.
        //if(esProductoFarma()){
        //muestraProductosAlternativos();
        muestraProductosSustitutos();
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD, VariablesModuloVentas.vArrayList_PedidoVenta, 
                                      0);
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                      0);
        FarmaUtility.ponerCheckJTable(tblListaSustitutos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                      0);
        //}
    }

    private void pintaCheckOtroJTable(JTable pActualJTable, Boolean pValor) {
        //log.debug(pValor.booleanValue());
        if (pActualJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS)) {
            FarmaUtility.setearCheckInRow(tblListaSustitutos, pValor, true, 
                                          true, VariablesModuloVentas.vCod_Prod, 
                                          COL_COD);
            tblListaSustitutos.repaint();
        } else if (pActualJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_SUSTITUTOS)) {
            FarmaUtility.setearCheckInRow(tblProductos, pValor, true, true, VariablesModuloVentas.vCod_Prod, COL_COD);
            tblProductos.repaint();
        }
    }

    private void muestraProductosComplementarios() {
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesModuloVentas.vCodProdOrigen_Comple = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesModuloVentas.vDescProdOrigen_Comple = 
                ((String)(myJTable.getValueAt(vFila, 2))).trim();

        DlgProductosComplementarios dlgProductosComplementarios = 
            new DlgProductosComplementarios(myParentFrame, "", true);
        dlgProductosComplementarios.setVisible(true);
        if (FarmaVariables.vAceptar) {
            log.debug("VariablesVentas.vCodProdComplementario : " + VariablesModuloVentas.vCodProdComplementario);
           // btnRelacionProductos.doClick();
            buscaCodigoEnJtable(VariablesModuloVentas.vCodProdComplementario);
            FarmaVariables.vAceptar = false;
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
        } else
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
    }

    private void buscaCodigoEnJtable(String pCodBusqueda) {
        String codTmp = "";
        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            codTmp = FarmaUtility.getValueFieldJTable(myJTable, i, COL_COD);
            if (codTmp.equalsIgnoreCase(pCodBusqueda)) {
                FarmaGridUtils.showCell(tblProductos, i, 0);
                FarmaUtility.setearRegistro(tblProductos, i, txtProducto, 2);
                FarmaUtility.moveFocus(txtProducto);
                return;
            }
        }
    }

    private void evaluaTitulo() {
        if (VariablesModuloVentas.vEsPedidoDelivery) {
            this.setTitle("Lista de Productos y Precios - Pedido Delivery" + 
                          " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesModuloVentas.vEsPedidoInstitucional) {
            lblCliente.setText("");
            this.setTitle("Lista de Productos y Precios - Pedido Institucional" + 
                          " /  IP : " + FarmaVariables.vIpPc);
        } else if (VariablesModuloVentas.vEsPedidoConvenio) {
            lblCliente.setText("");
            lblCliente.setText(VariablesConvenio.vTextoCliente);
            this.setTitle("Lista de Productos y Precios - Pedido por Convenio: " +
                              VariablesConvenio.vNomConvenio+
                              " /  IP : " + FarmaVariables.vIpPc);
        }
        else if (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1) {
                   lblCliente.setText("");
                   lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
                   this.setTitle("Lista de Productos y Precios - Pedido por Convenio: " +
                                     VariablesConvenioBTLMF.vNomConvenio +
                                     " /  IP : " + FarmaVariables.vIpPc);
        }
        else {
            VariablesConvenio.vCodConvenio = "";
            VariablesConvenio.vArrayList_DatosConvenio.clear();
            // Solo si el no se ingreso tarjeta 
            if (VariablesFidelizacion.vNumTarjeta.trim().length() == 0)
                lblCliente.setText("");
            if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + 
                                   VariablesFidelizacion.vApePatCliente + " " + 
                                   VariablesFidelizacion.vApeMatCliente);

            this.setTitle("Lista de Productos y Precios" + " /  IP : " + 
                            FarmaVariables.vIpPc + " / " + FrmEconoFar.tituloBaseFrame);
        }
    }

    private void evaluaSeleccionMedico() {
        if (VariablesModuloVentas.vSeleccionaMedico) {
            lblF8.setText("[F8] Des. Medico");
        } else {
            lblF8.setText("[F8] Sel. Medico");
        }
    }

    private boolean esProductoFarma() {
        int vFila = myJTable.getSelectedRow();
        String indicador = 
            FarmaUtility.getValueFieldJTable(myJTable, vFila, 12);

        if (indicador.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;
        else
            return false;
    }
    // Inicio Adicion Delivery 28/04/2006 Paulo
    //JCORTEZ  07.08.09 Aun falta completar funcionalidad de mantenimiento de clientes.

    private void generarPedidoDelivery() {
        DlgListaClientes dlgListaClientes = 
            new DlgListaClientes(myParentFrame, "", true);
        dlgListaClientes.setVisible(true);
        log.debug("Indicador Pedido Delivery-->" + VariablesModuloVentas.vEsPedidoDelivery);
        if (FarmaVariables.vAceptar) {
            String nombreCliente = 
                VariablesDelivery.vNombreCliente + " " + VariablesDelivery.vApellidoPaterno + 
                " " + VariablesDelivery.vApellidoMaterno;
            lblCliente.setText(nombreCliente);
            log.debug("************DATOS CLIENTE DELIVERY*******");
            VariablesDelivery.vNombreCliente = 
                    nombreCliente; //Se setea el valor mostrado en pantalla
            log.debug("CodCliente: " + VariablesDelivery.vCodCli);
            log.debug("Nombre: " + VariablesDelivery.vNombreCliente);
            log.debug("Nunero Telf: " + 
                               VariablesDelivery.vNumeroTelefonoCabecera);
            log.debug("Direccion: " + VariablesDelivery.vDireccion);
            log.debug("Nro Documento: " + 
                               VariablesDelivery.vNumeroDocumento);
            FarmaVariables.vAceptar = false;
            VariablesModuloVentas.vEsPedidoDelivery = true;
            FarmaUtility.moveFocus(txtProducto);
            log.debug("Indicador Pedido Delivery-->" + VariablesModuloVentas.vEsPedidoDelivery);
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                evaluaTitulo();
                VariablesModuloVentas.vEsPedidoDelivery = false;
            }
        }
    }

    private void generarPedidoConvenio() {

        DlgListaConvenios dlgListaConvenios = 
            new DlgListaConvenios(myParentFrame, "", true);
        dlgListaConvenios.setVisible(true);
        if (FarmaVariables.vAceptar) {

            log.debug("VariablesConvenio.vArrayList_DatosConvenio Lista Productos: " + 
                      VariablesConvenio.vArrayList_DatosConvenio);
            String nombreCliente = 
                VariablesConvenio.vArrayList_DatosConvenio.get(COL_DESC_PROD).toString();
            String apellidoPat = 
                VariablesConvenio.vArrayList_DatosConvenio.get(3).toString();
            String apellidoMat = 
                VariablesConvenio.vArrayList_DatosConvenio.get(4).toString();
            lblCliente.setText(nombreCliente + " " + apellidoPat + " " + 
                               apellidoMat);
            FarmaVariables.vAceptar = false;
            VariablesModuloVentas.vEsPedidoConvenio = true;

            //jquispe para borrar las campañas cargadas desde el inicio cuando se trata de un campañas sin fidelizar        
            //borro 
            VariablesFidelizacion.vListCampañasFidelizacion.clear();
            VariablesModuloVentas.vArrayList_Cupones.clear();
        } else {
            if (FarmaVariables.vImprTestigo.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                evaluaTitulo();
                VariablesModuloVentas.vEsPedidoConvenio = false;
            }

        }
    }

    //inicio adicion Paulo 15/06/2006

    private void faltacero() {
        vEjecutaAccionTeclaListado = false;
        //if (!validaStockDisponible()) {
        if (true) {
            int vFila = myJTable.getSelectedRow();
            VariablesModuloVentas.vCod_Prod = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);

           /* if (!isExistProdListaFaltaCero(VariablesVentas.vListaProdFaltaCero, 
                                           VariablesVentas.vCod_Prod)) {*/
            if(true){
                try {
                    DBModuloVenta.ingresaStockCero();
                    FarmaUtility.aceptarTransaccion();
                    VariablesModuloVentas.vListaProdFaltaCero.add(VariablesModuloVentas.vCod_Prod);
                    FarmaUtility.showMessage(this, 
                                             "Se ha Registrado el Producto para reposicion", 
                                             txtProducto);
                } catch (SQLException sqlException) {
                    FarmaUtility.liberarTransaccion();
                    if (sqlException.getErrorCode() == 00001) {
                        FarmaUtility.showMessage(this, 
                                                 "Ya registró el producto con Falta Cero.!", 
                                                 txtProducto);
                    } else
                        FarmaUtility.showMessage(this, 
                                                 "Error al insertar en la tabla falta stock.\n" +
                                sqlException, txtProducto);
                    log.error(null, sqlException);
                }
            } else {
                FarmaUtility.showMessage(this, 
                                         "Ya registró el producto con Falta Cero!", 
                                         txtProducto);
            }
        }
    }
    //fin adicion Paulo 15/06/2006
    //inicio adicion paulo 23/06/2006

    private void muestraBuscaMedico() {
        DlgBuscaMedico dlgBuscaMedico = 
            new DlgBuscaMedico(myParentFrame, "", true);
        dlgBuscaMedico.setVisible(true);
    }

    /**
     * Se selecciona el medico y graba receta.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void ingresaReceta() {

        vEjecutaAccionTeclaListado = false;
        if (!VariablesModuloVentas.vSeleccionaMedico) {
            if (validaSeleccionProductos()) {
                muestraBuscaMedico();
                if (FarmaVariables.vAceptar) {
                    lblMedico.setText(VariablesModuloVentas.vNombreListaMed);
                    evaluaSeleccionMedico();
                    cargarReceta();
                } else {
                    limpiaMedico();
                }
            }
        } else {
            if (JConfirmDialog.rptaConfirmDialog(this, 
                                               "¿Está seguro de quitar la referencia a la receta ingresada?")) {
                limpiaMedico();
                evaluaSeleccionMedico();
            }
        }
    }

    /**
     * Se verifica que no haya productos seleccionados.
     * @return <b>true</b> si no hay productos seleccionados.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private boolean validaSeleccionProductos() {
        boolean retorno = true;
        if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0) {
            FarmaUtility.showMessage(this, 
                                     "Existen Productos Seleccionados. Para ingresar una Receta Medica\n" +
                    "no deben haber productos seleccionados. Verifique!!!", 
                    txtProducto);
            retorno = false;
        } else if (VariablesModuloVentas.vArrayList_ResumenPedido.size() > 0) {
            FarmaUtility.showMessage(this, 
                                     "Existen Productos Seleccionados en el Resumen de Pedido. Para ingresar una Receta Medica\n" +
                    "no deben haber productos seleccionados. Verifique!!!", 
                    txtProducto);
            retorno = false;
        }
        return retorno;
    }

    /**
     * Se revierte la seleccion del medico y la receta generada.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void limpiaMedico() {
        lblMedico.setText("");
        VariablesModuloVentas.vArrayList_Medicos.clear();
        VariablesModuloVentas.vCodListaMed = "";
        VariablesModuloVentas.vMatriListaMed = "";
        VariablesModuloVentas.vNombreListaMed = "";
        VariablesModuloReceta.vArrayList_PedidoReceta = new ArrayList();
        VariablesModuloReceta.vArrayList_ResumenReceta = new ArrayList();
        VariablesModuloReceta.vNum_Ped_Rec = "";
        VariablesModuloVentas.vSeleccionaMedico = false;
        VariablesModuloReceta.vVerReceta = false;
    }

    /**
     * Se muestra los productos de la receta, en el resumen de pedido.
     * @author Edgar Rios Navarro
     * @since 06.12.2006
     */
    private void cargarReceta() {
        if (VariablesModuloReceta.vArrayList_ResumenReceta.size() <= 0) {
            log.warn("No hay productos");
        } else {
            ArrayList arrayRow;
            for (int i = 0; 
                 i < VariablesModuloReceta.vArrayList_ResumenReceta.size(); i++) {
                try {
                    arrayRow = 
                            (ArrayList)VariablesModuloReceta.vArrayList_ResumenReceta.get(i);
                    //log.debug(arrayRow);
                    VariablesModuloVentas.vCod_Prod = 
                            arrayRow.get(0).toString().trim();
                    VariablesModuloVentas.vDesc_Prod = 
                            arrayRow.get(1).toString().trim();
                    VariablesModuloVentas.vNom_Lab = 
                            arrayRow.get(9).toString().trim();
                    VariablesModuloVentas.vPorc_Igv_Prod = 
                            arrayRow.get(11).toString().trim();
                    VariablesModuloVentas.vCant_Ingresada = 
                            arrayRow.get(4).toString().trim();
                    VariablesModuloReceta.vVal_Frac = 
                            arrayRow.get(10).toString().trim();
                    VariablesModuloVentas.vIndOrigenProdVta = ConstantsModuloVenta.IND_ORIGEN_REC;
                    VariablesModuloVentas.vPorc_Dcto_2 = "0";
                    log.info("******JCALLO****** CAMPO INDICADOR TRATAMIENTO :" + 
                                       arrayRow.get(13).toString().trim());
                    //VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
                    VariablesModuloVentas.vIndTratamiento = 
                            arrayRow.get(13).toString().trim();
                    if (VariablesModuloVentas.vIndTratamiento.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        VariablesModuloVentas.vTotalPrecVtaTra = 
                                Double.parseDouble(arrayRow.get(7).toString().trim());
                        VariablesModuloVentas.vCantxDia = "1";
                        VariablesModuloVentas.vCantxDias = 
                                "" + VariablesModuloVentas.vCant_Ingresada;
                    } else {
                        VariablesModuloVentas.vCantxDia = "";
                        VariablesModuloVentas.vCantxDias = "";
                    }

                    muestraInfoProd2();

                    if (!validaStockDisponible())
                        continue;
                    if (!validaProductoTomaInventario(0))
                        continue;
                    if (!validaProductoHabilVenta())
                        continue;

                    validaStockActual2();
                    seleccionaProducto2();
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    //log.error("",e);
                    log.error(null, e);
                }
            }
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            aceptaOperacion();
        }
    }

    /**
     * Se obtiene informacion detallada del producto seleccionado en la receta.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void muestraInfoProd2() throws SQLException {
        ArrayList myArray = new ArrayList();
        DBModuloVenta.obtieneInfoProducto(myArray, VariablesModuloVentas.vCod_Prod);
        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            indProdCong = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            indProdHabilVta = 
                    ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            stkProd = "";
            valFracProd = "";
            indProdCong = "";
            indProdHabilVta = "";
            log.warn("Error al obtener Informacion del Producto");
            throw new SQLException("Error al obtener Informacion del Producto");
        }
        VariablesModuloVentas.vVal_Frac = valFracProd;
        VariablesModuloVentas.vInd_Prod_Habil_Vta = indProdHabilVta;

        myArray = new ArrayList();
        DBModuloVenta.obtieneInfoDetalleProducto(myArray, VariablesModuloVentas.vCod_Prod);
        if (myArray.size() == 1) {
            VariablesModuloVentas.vUnid_Vta = 
                    ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesModuloVentas.vVal_Bono = 
                    ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesModuloVentas.vVal_Prec_Lista = 
                    ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesModuloVentas.vUnid_Vta = "";
            VariablesModuloVentas.vVal_Bono = "";
            VariablesModuloVentas.vVal_Prec_Lista = "";
            log.warn("Error al obtener Detalle del Producto");
            throw new SQLException("Error al obtener Detalle del Producto");
        }
    }

    /**
     * Se verifica que la cantidad seleccionada en la receta 
     * no se mayor que el stock del producto.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void validaStockActual2() throws SQLException {
        obtieneStockProducto2();
        int cantReceta = Integer.parseInt(VariablesModuloVentas.vCant_Ingresada);
        int fracReceta = Integer.parseInt(VariablesModuloReceta.vVal_Frac);
        int fracProd = Integer.parseInt(VariablesModuloVentas.vVal_Frac);
        int cantIngreso = (cantReceta * fracProd) / fracReceta;
        if ((Integer.parseInt(VariablesModuloVentas.vStk_Prod) + 0) < (cantIngreso))
            VariablesModuloVentas.vCant_Ingresada = VariablesModuloVentas.vStk_Prod;
        else
            VariablesModuloVentas.vCant_Ingresada = String.valueOf(cantIngreso);
    }

    /**
     * Se obtiene el stock actual del producto.
     * @throws SQLException
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void obtieneStockProducto2() throws SQLException {

        ArrayList myArray = new ArrayList();
        DBModuloVenta.obtieneStockProducto_ForUpdate(myArray, VariablesModuloVentas.vCod_Prod, VariablesModuloVentas.vVal_Frac);
        FarmaUtility.liberarTransaccion();
        //quitar bloqueo de stock fisico 
        //dubilluz 13.10.2011  
        if (myArray.size() == 1) {
            VariablesModuloVentas.vStk_Prod = 
                    ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesModuloVentas.vVal_Prec_Vta = 
                    ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesModuloVentas.vPorc_Dcto_1 = 
                    ((String)((ArrayList)myArray.get(0)).get(2)).trim();
        } else {
            log.warn("Error al obtener Stock del Producto");
            throw new SQLException("Error al obtener Stock del Producto");
        }
    }

    /**
     * Se agrega el producto, en el pedido de venta.
     * @author Edgar Rios Navarro
     * @since 12.12.2006
     */
    private void seleccionaProducto2() {
        VariablesModuloVentas.vTotalPrecVtaProd = 
                (FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada) * 
                 FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta));
        VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
        if ( /*!UtilityVentas.actualizaStkComprometidoProd(VariablesVentas.vCod_Prod,
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                      ConstantsVentas.INDICADOR_A,
                                      ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR,
                                      Integer.parseInt(VariablesVentas.vCant_Ingresada),
                                                    true,
                                                    this,
                                                    txtProducto))*/
            !UtilityModuloVenta.operaStkCompProdResp(VariablesModuloVentas.vCod_Prod,
                //ASOSA, 01.07.2010
                Integer.parseInt(VariablesModuloVentas.vCant_Ingresada), ConstantsModuloVenta.INDICADOR_A, 
                ConstantsPtoVenta.TIP_OPERACION_RESPALDO_SUMAR, 
                Integer.parseInt(VariablesModuloVentas.vCant_Ingresada), true, this, 
                txtProducto, ""))
            return;

        VariablesModuloVentas.vNumeroARecargar = 
                ""; //NUMERO TELEFONICO SI ES RECARGA AUTOMATICA
        VariablesModuloVentas.vIndProdVirtual = 
                FarmaConstants.INDICADOR_N; //INDICADOR DE PRODUCTO VIRTUAL
        VariablesModuloVentas.vTipoProductoVirtual = ""; //TIPO DE PRODUCTO VIRTUAL
        VariablesModuloVentas.vIndProdControlStock = 
                true; //? FarmaConstants.INDICADOR_S : FarmaConstants.INDICADOR_N);//INDICADOR PROD CONTROLA STOCK
        VariablesModuloVentas.vVal_Prec_Lista_Tmp = 
                ""; //PRECIO DE LISTA ORIGINAL SI ES QUE SE MODIFICO
        VariablesModuloVentas.vVal_Prec_Pub = VariablesModuloVentas.vVal_Prec_Vta;

        Boolean valor = new Boolean(true);
        UtilityModuloVenta.operaProductoSeleccionadoEnArrayList_02(valor, VariablesModuloVentas.secRespStk); //ASOSA, 06.07.2010
        colocaTotalesPedido();
        //FarmaUtility.aceptarTransaccion();
    }

    /**
     * Evalua si el producto es del Tipo Virtual y si se puede seleccionar.
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void evaluaTipoProdVirtual() {
        //ERIOS 20.05.2013 Tratamiento de Producto Virtual - Magistral
        
        int row = myJTable.getSelectedRow();
        String tipoProdVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);

        if (tipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA)) {
            muestraIngresoTelefonoMonto();
        } else if (tipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA)) {
            int cantidad_ingresada = muestraIngresoCantidad_Tarjeta_Virtual();
            if (cantidad_ingresada != 0) {
                seleccionaProductoTarjetaVirtual(cantidad_ingresada + " ");
                String 
                valorTarj = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta), 2);
                FarmaUtility.showMessage(this, 
                                         "Se ha seleccionado una tarjeta virtual " + VariablesModuloVentas.vDesc_Prod + 
                                         " de S/. " + valorTarj, txtProducto);
            }
        } else if(tipoProdVirtual.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_MAGISTRAL)){
            muestraIngresoRecetarioMagistral();
        }
    }

    /**o no aca nada es 
     * Muestra pantalla ingreso de numero telefonico y monto de recarga
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void muestraIngresoTelefonoMonto() {
        if (myJTable.getRowCount() == 0)
            return;

        int row = myJTable.getSelectedRow();
        VariablesModuloVentas.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
        VariablesModuloVentas.vDesc_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesModuloVentas.vNom_Lab = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 4);
        VariablesModuloVentas.vUnid_Vta = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 3);
        VariablesModuloVentas.vVal_Prec_Lista = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 10);
        //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesModuloVentas.vVal_Bono = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 7);
        VariablesModuloVentas.vPorc_Igv_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 11);
        VariablesModuloVentas.vTipoProductoVirtual = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 14);
        VariablesModuloVentas.vMontoARecargar_Temp = "0";
        VariablesModuloVentas.vNumeroARecargar = "";
        VariablesModuloVentas.vIndOrigenProdVta = 
                FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
        VariablesModuloVentas.vPorc_Dcto_2 = "0";
        VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesModuloVentas.vCantxDia = "";
        VariablesModuloVentas.vCantxDias = "";

        //mfajardo 29/04/09 validar ingreso de productos virtuales
        VariablesModuloVentas.vProductoVirtual = true;

        DlgIngresoRecargaVirtual dlgIngresoRecargaVirtual = 
            new DlgIngresoRecargaVirtual(myParentFrame, "", true);
        dlgIngresoRecargaVirtual.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesModuloVentas.vVal_Prec_Lista_Tmp = VariablesModuloVentas.vVal_Prec_Lista;
            //VariablesVentas.vVal_Prec_Vta = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesVentas.vMontoARecargar));
            //Ahora se grabara S/1.00 
            //31.10.2007 dubilluz modificacion
            VariablesModuloVentas.vVal_Prec_Vta = 
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(ConstantsModuloVenta.PrecioVtaRecargaTarjeta));
            VariablesModuloVentas.vVal_Prec_Lista = 
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Lista) * 
                                              FarmaUtility.getDecimalNumber(VariablesModuloVentas.vMontoARecargar));
            seleccionaProducto();
            log.debug("VariablesVentas.secRespStkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: " + VariablesModuloVentas.secRespStk);
            //Actualizando el Indicador de la Venta de Tarjeta Virtual Recarga
            VariablesModuloVentas.venta_producto_virtual = true;
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else {
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
            VariablesModuloVentas.vProductoVirtual = false; //ASOSA 01.02.2010
        }

    }

    /**
     * Selecciona el producto y le asigna la cantidad por defecto 1
     * @author Luis Mesia Rivera
     * @since 05.01.2007
     */
    private void seleccionaProductoTarjetaVirtual(String cantidad) {
        if (myJTable.getRowCount() == 0)
            return;

        int row = myJTable.getSelectedRow();
        VariablesModuloVentas.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
        VariablesModuloVentas.vDesc_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesModuloVentas.vNom_Lab = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 4);
        VariablesModuloVentas.vUnid_Vta = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 3);
        VariablesModuloVentas.vVal_Prec_Lista = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 10);
        //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
        VariablesModuloVentas.vVal_Bono = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 7);
        VariablesModuloVentas.vPorc_Igv_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 11);
        VariablesModuloVentas.vVal_Prec_Vta = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 6);
        VariablesModuloVentas.vTipoProductoVirtual = 
                FarmaUtility.getValueFieldJTable(myJTable, row, 14);
        VariablesModuloVentas.vCant_Ingresada = cantidad.trim(); //"1";
        VariablesModuloVentas.vNumeroARecargar = "";
        VariablesModuloVentas.vVal_Prec_Lista_Tmp = "";
        VariablesModuloVentas.vIndOrigenProdVta = 
                FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
        VariablesModuloVentas.vPorc_Dcto_2 = "0";
        VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
        VariablesModuloVentas.vCantxDia = "";
        VariablesModuloVentas.vCantxDias = "";

        seleccionaProducto();
        //Actualizando el Indicador de la Venta de Tarjeta Virtual
        VariablesModuloVentas.venta_producto_virtual = true;
    }

    private void muestraProductoCongelado(JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            //String indProdCong = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
            if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    /**
     * Muestra si tiene promocion
     * @author Dubilluz
     * @since 15.06.2007
     */
    private void muestraProductoPromocion(int pColumna, JLabel pLabel) {
        String descripcion = " ";
        if (myJTable.getRowCount() > 0) {
            String indProdProm = 
                ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 
                                              17))).trim();
            if (indProdProm.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //JCHAVEZ 31092009.sn
                int vFila = myJTable.getSelectedRow();
                if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS)) {

                    VariablesModuloVentas.vCodProdFiltro = 
                            FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                             COL_COD);
                    try {
                        descripcion = DBModuloVenta.getDescPaquete(VariablesModuloVentas.vCodProdFiltro);
                        log.debug("descripcion paquete: " + 
                                           descripcion);
                    } catch (SQLException sqlException) {
                        log.error("",sqlException);
                    }

                }
                pLabel.setText(descripcion);
                //JCHAVEZ 31092009.en
                pLabel.setVisible(true);
            } else
                pLabel.setVisible(false);
        }
    }

    private void muestraProductoRefrigerado(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            String indProdRef = 
                ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 
                                              pColumna))).trim();
            if (indProdRef.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
                pLabel.setVisible(false);
        }
    }

    private void muestraIndTipoProd(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            String indProdRef = 
                ((String)(myJTable.getValueAt(myJTable.getSelectedRow(), 
                                              pColumna))).trim();
            if (indProdRef.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[0]);
            else if (indProdRef.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[1]);
            else if (indProdRef.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[2]);
            else if (indProdRef.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[3]);
            else if (indProdRef.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[4]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[4]);

        }
    }

    /**
     * Muestra si es producto de Encarte
     * @param pColumna
     * @param pLabel
     * @author JCORTEZ
     * @since  08.04.2008
     */
    private void muestraProductoEncarte(int pColumna, JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            int vFila = myJTable.getSelectedRow();
            String indProdEncarte = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, pColumna);
            log.debug("indProdEncarte : " + indProdEncarte);
            if (indProdEncarte.length() > 0) {
                pLabel.setVisible(true);
            } else {
                pLabel.setVisible(false);
            }
        }
    }

    private void guardaInfoProdVariables() {
        log.debug("*************************************************");
        if (VariablesModuloVentas.vEsPedidoConvenio) //Se ha seleccionado un convenio
        {
            //String indControlPrecio = "";
            String mensaje = "";
            try {
                //mensaje = "Error al obtener el indicador de control de precio del producto.\n";
                //indControlPrecio = DBConvenio.obtieneIndPrecioControl(VariablesVentas.vCod_Prod);

                VariablesConvenio.vVal_Prec_Vta_Local = VariablesModuloVentas.vVal_Prec_Pub;

                /* 23.01.2007 ERIOS La validacion de realiza por las listas de exclusion */
                //if(indControlPrecio.equals(FarmaConstants.INDICADOR_N))
                if (true) {
                    mensaje = 
                            "Error al obtener el nuevo precio del producto.\n";
                    VariablesConvenio.vVal_Prec_Vta_Conv = 
                            DBConvenio.obtieneNvoPrecioConvenio(VariablesConvenio.vCodConvenio, VariablesModuloVentas.vCod_Prod, VariablesModuloVentas.vVal_Prec_Pub);
                } else {

                    VariablesConvenio.vVal_Prec_Vta_Conv = VariablesModuloVentas.vVal_Prec_Pub;
                }
            } catch (SQLException sql) {
                //log.error("",sql);
                log.error(null, sql);
                FarmaUtility.showMessage(this, mensaje + sql.getMessage(), 
                                         null);
                FarmaUtility.moveFocus(txtProducto);
            }
        }
    }

    /**
     * Muestra el dialogo de convenios.
     * @author Edgar Rios Navarro
     * @since 24.05.2007
     */



    //ini Agregado FRC
    private  void cargarConvenioBTL() {

        log.debug("-Exe F3 Convenio Nuevo MF - BTL -");
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
        {
            FarmaUtility.showMessage(this,
                                     "No puede elegir un convenio!!!.\n Porque se ha asociado una tarjeta de fidelizacion.",
                                     txtProducto);
            return;
        }        
        
    	if (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)
    	{
	    	if (JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de cancelar el pedido por Convenio?"))
	    	{
	    		VariablesConvenioBTLMF.limpiaVariablesBTLMF();
				if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0)
				{
					FarmaUtility.showMessage(this,
					         "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
					         "no deben haber productos seleccionados. Verifique!!!",
					         txtProducto);

				} else
				{

					lblCliente.setText("");
                                        lblMedico.setText("");
                                                this.setTitle("Lista de Productos y Precios" +
                                                " /  IP : " + FarmaVariables.vIpPc);
                                        evaluaTitulo();
				        //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
				        UtilityFidelizacion.operaCampañasFidelizacion(" ");
				}
		   }
    	}
        else{

            if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0)
            {
                    FarmaUtility.showMessage(this,
                             "Existen Productos Seleccionados. Para iniciar un pedido convenio\n" +
                             "no deben haber productos seleccionados. Verifique!!!",
                             txtProducto);

            } else
            {       log.debug("-Ejecutando F3 VTA CONVENIO BTLMFARMA-");
                    DlgListaConveniosBTLMF loginDlg = new DlgListaConveniosBTLMF(myParentFrame, "", true);
                    loginDlg.setVisible(true);
                    log.debug("Cerramos Convenios"+VariablesConvenioBTLMF.vAceptar+"-"+VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);

                    if (VariablesConvenioBTLMF.vAceptar)
                    {


                            VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
                            //11-DIC-13  TCT Begin
                            log.debug("<<TCT 121>>VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF=>"+VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF);
                            //FarmaUtility.showMessage(this, "Ingreso a Convenio", null);
                            //11-DIC-13  TCT End

                            //ImageIcon icon = new ImageIcon(this.getClass().getResource("logo_mf_btl.JPG"));
                            Map map = UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this);
                            nombConvenio = (String)map.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);
                            VariablesConvenioBTLMF.vFlgImprimeImportes   = (String)map.get(ConstantsConvenioBTLMF.COL_FLG_IMPRIME_IMPORTES);
                            VariablesConvenioBTLMF.vIndVtaComplentaria   = (String)map.get(ConstantsConvenioBTLMF.COL_IND_VTA_COMPLEMENTARIA);
                            VariablesConvenioBTLMF.vFlgValidaLincreBenef =  (String)map.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF);
                            VariablesConvenioBTLMF.vRuc 		 =  (String)map.get(ConstantsConvenioBTLMF.COL_RUC);
                            VariablesConvenioBTLMF.vDireccion            =  (String)map.get(ConstantsConvenioBTLMF.COL_DIRECCION);
                            VariablesConvenioBTLMF.vInstitucion          =  (String)map.get(ConstantsConvenioBTLMF.COL_INSTITUCION);

                            VariablesConvenioBTLMF.vNomConvenio = nombConvenio;

                            log.debug("VariablesConvenioBTLMF.vCodConvenio:"+VariablesConvenioBTLMF.vCodConvenio);
                            log.debug("VariablesConvenioBTLMF.vNomConvenio:"+VariablesConvenioBTLMF.vNomConvenio );

                            /*
                            this.setTitle("Lista de Productos y Precios      "+nombConvenio);
                            lblMedicoT.setText(" ");
                            //lblMedicoT.setIcon(icon);
                            lblMedico.setText(" "+nombConvenio);
                            lblCliente.setText(VariablesConvenioBTLMF.vNomCliente);
                            */

                     }
                    else
                    {
                    	 VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = false;
                    }

                     evaluaTitulo();
                     //SE SELECCIONO CONVENIO
                     if (VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1){
                    	 //si se selecciono convenios debe de quitar campañas de fid. automaticas
                    	 VariablesFidelizacion.vListCampañasFidelizacion = new ArrayList();
                    VariablesModuloVentas.vArrayList_Cupones = new ArrayList();
                     }
            }
        }
    }
    //fin Agregado FRC

    private void cargaConvenio() {

        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            FarmaUtility.showMessage(this, 
                                     "No puede elegir un convenio!!!.\n Porque se ha asociado una tarjeta de fidelizacion.", 
                                     txtProducto);
            return;
        }

        if (!VariablesModuloVentas.vEsPedidoConvenio) {
            if (UtilityModuloVenta.evaluaPedidoConvenio(this, txtProducto, VariablesModuloVentas.vArrayList_PedidoVenta)) {
                if (VariablesModuloVentas.vEsPedidoConvenio)
                    generarPedidoConvenio();

                evaluaTitulo();
            }
        } else {
            if (JConfirmDialog.rptaConfirmDialog(this, 
                                               "¿Esta seguro de cancelar el pedido por Convenio?")) {
                if (VariablesModuloVentas.vArrayList_PedidoVenta.size() > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Existen Productos Seleccionados. Para realizar un Pedido Mostrador\n" +
                            "no deben haber productos seleccionados. Verifique!!!", 
                            txtProducto);
                } else {
                    VariablesConvenio.vCodConvenio = "";
                    VariablesConvenio.vArrayList_DatosConvenio.clear();
                    VariablesConvenio.vTextoCliente = "";
                    VariablesConvenio.vCodTrab = "";
                    VariablesConvenio.vNomCliente = "";
                    lblCliente.setText("");
                    this.setTitle("Lista de Productos y Precios" + 
                                  " /  IP : " + FarmaVariables.vIpPc);
                    VariablesModuloVentas.vEsPedidoConvenio = false;
                    evaluaTitulo();

                    //jquispe 25.07.2011 se agrego la funcionalidad de listar las campañas sin fidelizar
                    UtilityFidelizacion.operaCampañasFidelizacion(" ");
                }
            }
        }
    }


    /**
     * Muestra el Dialogo de Promociones por Producto
     * @author Dubilluz
     * @since  15.06.2007 
     */
    private void muestraPromocionProd(String codigo,String pCodigoBarra) {
        //codigo es el cod del producto q se utilizara
        //para cargar las promociones q se encuentran en la promocion
        VariablesModuloVentas.vCodProdFiltro = codigo;
        //String indPromocion=(String)myJTable.getValueAt(myJTable.getSelectedRow(),17);
        //  if(indPromocion.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) return;
        // else{
        //valida si esta en ArrayListadeProd promocion
        //falta aun

        DlgListaPromocion dlgListPromocion = 
            new DlgListaPromocion(myParentFrame, "", true,pCodigoBarra);
        VariablesModuloVentas.vIngresaCant_ResumenPed = false;
        dlgListPromocion.setVisible(true);

        log.debug("Detalle Promocion" + VariablesModuloVentas.vArrayList_Prod_Promociones);
        if (FarmaVariables.vAceptar) {
            seleccionaProductoPromocion();
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
            aceptaOperacion();

            //JCHAVEZ 29102009 inicio
            try {
                VariablesModuloVentas.vIndAplicaRedondeo = DBModuloVenta.getIndicadorAplicaRedondedo();
            } catch (SQLException ex) {
                log.error("",ex);
            }
            if (VariablesModuloVentas.vIndAplicaRedondeo.equalsIgnoreCase("S")) {
                for (int i = 0; 
                     i < VariablesModuloVentas.vArrayList_Prod_Promociones.size(); 
                     i++) {
                    String codProd = 
                        FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                                            i, 0).toString();
                    double precioUnit = 
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                                                                          i, 
                                                                                          3));
                    double precioVentaUnit = 
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                                                                          i, 
                                                                                          6));
                    double precioVtaTotal = 
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                                                                          i, 
                                                                                          7));
                    double cantidad = 
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                                                                          i, 
                                                                                          4));
                    log.debug("precioVtaTotal: " + precioVtaTotal);
                    log.debug("precioVentaUnit: " + precioVentaUnit);
                    try {
                        double precioVtaTotalRedondeado = DBModuloVenta.getPrecioRedondeado(precioVtaTotal);
                        double precioVentaUnitRedondeado = 
                            precioVtaTotalRedondeado / cantidad;
                        double precioUnitRedondeado = DBModuloVenta.getPrecioRedondeado(precioUnit);
                        log.debug("precioVtaTotalRedondeado: " + 
                                           precioVtaTotalRedondeado);
                        log.debug("precioVentaUnitRedondeado: " + 
                                           precioVentaUnitRedondeado);
                        double pAux2;
                        double pAux5;
                        pAux2 = UtilityModuloVenta.Redondear(precioVentaUnitRedondeado, 
                                                        2);
                        log.debug("pAux2: " + pAux2);
                        if (pAux2 < precioVentaUnitRedondeado) {
                            pAux5 = (pAux2 * Math.pow(10, 2) + 1) / 100;
                            log.debug("pAux5: " + pAux5);
                        } else {
                            pAux5 = pAux2;
                        }


                        String cprecioVtaTotalRedondeado = 
                            FarmaUtility.formatNumber(precioVtaTotalRedondeado, 
                                                      3);
                        String cprecioVentaUnitRedondeado = 
                            FarmaUtility.formatNumber(pAux5); /*precioVentaUnitRedondeado,3*/
                        String cprecioUnitRedondeado = 
                            FarmaUtility.formatNumber(precioUnitRedondeado, 3);
                        ((ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones.get(i)).set(3, 
                                                                                            cprecioUnitRedondeado);
                        ((ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones.get(i)).set(6, 
                                                                                            cprecioVentaUnitRedondeado);
                        ((ArrayList)VariablesModuloVentas.vArrayList_Prod_Promociones.get(i)).set(7, 
                                                                                            cprecioVtaTotalRedondeado);
                        log.debug("precioVtaTotalRedondeado: " + "" + 
                                           cprecioVtaTotalRedondeado);
                        log.debug("precioVentaUnitRedondeado: " + 
                                           cprecioVentaUnitRedondeado);
                        log.debug("precioUnitRedondeado: " + 
                                           cprecioUnitRedondeado);

                    } catch (SQLException ex) {
                        log.error("",ex);
                    }

                    log.debug("codProd: " + codProd);
                    log.debug("precioUnit: " + precioUnit);
                    log.debug("precioVentaUnit: " + precioVentaUnit);
                    log.debug("precioVtaTotal: " + precioVtaTotal);
                    log.debug("cantidad: " + cantidad);
                }
            }
            //JCHAVEZ 29102009 fin
        } else
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
        //  }
        
        FarmaUtility.moveFocus(txtProducto);
    }

    /**
     * Seleccionara los Productos  de la promocion 
     * aceptada
     * @author dubilluz
     * @since  15.06.2007
     */
    private void seleccionaProductoPromocion() {
        int vFila = myJTable.getSelectedRow();
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
        log.debug("<<TCT 4>>vArrayList_Promociones" + VariablesModuloVentas.vArrayList_Promociones);
        log.debug("" + VariablesModuloVentas.vArrayList_Prod_Promociones.size());
        log.debug("" + VariablesModuloVentas.vArrayList_Prod_Promociones);
        FarmaUtility.ponerCheckJTable(myJTable, 1, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                      0);
        String cod_prod_tem = "";
        for (int i = 0;  i < VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size();  i++) {
            cod_prod_tem = 
                    ((String)((ArrayList)(VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.get(i))).get(0)).trim();
            if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS)) {
                FarmaUtility.setearCheckInRow(tblListaSustitutos, valorTmp, 
                                              true, true, cod_prod_tem, 1);
                tblListaSustitutos.repaint();
            } else if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_SUSTITUTOS)) {
                FarmaUtility.setearCheckInRow(tblProductos, valorTmp, true, 
                                              true, cod_prod_tem, 1);
                tblProductos.repaint();
            }
        }
        colocaTotalesPedido();
        //FarmaUtility.ponerCheckJTable(myJTable,1,VariablesVentas.vArrayList_Prod_Promociones,0);  

    }

    /**
     * busca si el codigo esta en el array
     * @author dubilluz
     * @since  19.06.2007
     */
    private boolean buscar(ArrayList array, String codigo) {
        String codtemp;
        for (int i = 0; i < array.size(); i++) {
            codtemp = ((String)(((ArrayList)array.get(i)).get(0))).trim();
            if (codtemp.equalsIgnoreCase(codigo))
                return true;
        }
        return false;
    }


    /**
     * Establece un valor boolean para un objeto en una lista de selección
     * @param pJTable
     * 				Tabla a operar
     * @param pCampoEnJTable
     * 				Campo en la tabla
     * @param pArrayList
     * 				Lista de campos
     * @param pCampoEnArrayList
     * 				Indice del campo en la lista
     */
    public static void quitarCheckJTable(JTable pJTable, int pCampoEnJTable, 
                                         ArrayList pArrayList, 
                                         int pCampoEnArrayList) {
        String myCodigo = "";
        String myCodigoTmp = "";
        for (int i = 0; i < pJTable.getRowCount(); i++) {
            myCodigo = ((String)pJTable.getValueAt(i, pCampoEnJTable)).trim();
            for (int j = 0; j < pArrayList.size(); j++) {
                myCodigoTmp = 
                        ((String)(((ArrayList)pArrayList.get(j)).get(pCampoEnArrayList))).trim();
                if (myCodigo.equalsIgnoreCase(myCodigoTmp))
                    pJTable.setValueAt(new Boolean(false), i, 0);
            }
        }
        pJTable.repaint();
    }

    /**
     * Cancela las Promociones Pedidos
     * @author dubilluz
     * @since  04.07.2007
     */
    private void cancela_promociones() {
        String codProm = "";
        String codProd = "";
        String cantidad = "";
        String indControlStk = "";
        ArrayList aux = new ArrayList();
        ArrayList array_promocion = new ArrayList();
        ArrayList prod_Prom = new ArrayList();

        Boolean valor = new Boolean(true);
        ArrayList agrupado = new ArrayList();
        ArrayList atemp = new ArrayList();
        //log.debug("");
        log.debug("!!!!>Promociona antes de Elimin " + VariablesModuloVentas.vArrayList_Promociones_temporal.size());
        log.debug("!!!!Promociona eli " + VariablesModuloVentas.vArrayList_Promociones_temporal);

        for (int j = 0; 
             j < VariablesModuloVentas.vArrayList_Promociones_temporal.size(); j++) {
            array_promocion = 
                    (ArrayList)(VariablesModuloVentas.vArrayList_Promociones_temporal.get(j));
            codProm = ((String)(array_promocion.get(0))).trim();
            log.debug(">>>>Promociona eli " + codProm);
            codProd = "";
            cantidad = "";
            indControlStk = "";
            aux = new ArrayList();

            prod_Prom = new ArrayList();
            log.debug(">>>>**Detalles " + VariablesModuloVentas.vArrayList_Prod_Promociones_temporal);
            prod_Prom = 
                    detalle_Prom(VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                 codProm);
            log.debug(">>>>**detalle de la prmo  " + j + "  :" + prod_Prom);
            valor = new Boolean(true);
            agrupado = new ArrayList();
            atemp = new ArrayList();

            for (int i = 0; i < prod_Prom.size(); i++) {
                atemp = (ArrayList)(prod_Prom.get(i));
                FarmaUtility.operaListaProd(agrupado, 
                                            (ArrayList)(atemp.clone()), valor, 
                                            0);
            }

            agrupado = agrupar(agrupado);
            log.debug(">>>>**Agrupado " + agrupado);
            String secRespaldo = ""; //ASOSA, 08.07.2010
            for (int i = 0; i < agrupado.size(); 
                 i++) //VariablesVentas.vArrayList_Prod_Promociones.size(); i++)
            {
                log.debug("Entro al for");
                aux = 
(ArrayList)(agrupado.get(i)); //VariablesVentas.vArrayList_Prod_Promociones.get(i));
                //if((((String)(aux.get(18))).trim()).equalsIgnoreCase(codProm)){
                log.debug("Entro");
                codProd = ((String)(aux.get(0))).trim();
                VariablesModuloVentas.vVal_Frac = ((String)(aux.get(10))).trim();
                cantidad = ((String)(aux.get(4))).trim();
                indControlStk = ((String)(aux.get(16))).trim();
                log.debug(indControlStk);
                secRespaldo = 
                        ((String)(aux.get(24))).trim(); //ASOSA, 08.07.2010
                VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                if (indControlStk.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
                    /*!UtilityVentas.actualizaStkComprometidoProd(codProd, //Antes, ASOSA, 01.07.2010
                                                     Integer.parseInt(cantidad),
                                                     ConstantsVentas.INDICADOR_D,
                                                     ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR,
                                                     Integer.parseInt(cantidad),
                                                     false,
                                                     this,
                                                     txtProducto))*/
                    !UtilityModuloVenta.operaStkCompProdResp(codProd,
                        //ASOSA, 01.07.2010
                        Integer.parseInt(cantidad), ConstantsModuloVenta.INDICADOR_D, 
                        ConstantsPtoVenta.TIP_OPERACION_RESPALDO_BORRAR, 
                        Integer.parseInt(cantidad), false, this, txtProducto, 
                        secRespaldo))

                    return;
                //}
            }
            FarmaUtility.aceptarTransaccion();
            /*        removeItemArray(VariablesVentas.vArrayList_Promociones_temporal,codProm,0);
        removeItemArray(VariablesVentas.vArrayList_Prod_Promociones_temporal,codProm,18);*/
        }

        VariablesModuloVentas.vArrayList_Promociones_temporal = new ArrayList();
        VariablesModuloVentas.vArrayList_Prod_Promociones_temporal = new ArrayList();
        log.debug("Resultados despues de Eliminar");
        log.debug("Tamaño de Resumen   :" + VariablesModuloVentas.vArrayList_ResumenPedido.size() + "");
        log.debug("Tamaño de Promocion :" + VariablesModuloVentas.vArrayList_Promociones_temporal.size() + "");
        log.debug("Tamaño de Prod_Promocion :" + VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size() + 
                  "");

    }

    /* ************************************************************************** */
    /*                        Metodos Auxiliares                                  */
    /* ************************************************************************** */

    /**
     * Metodo que elimina items del array ,que sean iguales al paramtro
     * q se le envia , comaprando por campo
     * @author dubilluz
     * @since  20.06.2007
     */
    private void removeItemArray(ArrayList array, String codProm, int pos) {
        String cod = "";
        codProm = codProm.trim();

        for (int i = 0; i < array.size(); i++) {
            cod = ((String)((ArrayList)array.get(i)).get(pos)).trim();
            log.debug(cod + "<<<<<" + codProm);
            if (cod.equalsIgnoreCase(codProm)) {
                array.remove(i);
                i = -1;
            }
        }
    }

    /**
     * Retorna el detalle de una promocion
     * @author dubilluz
     * @since  03.07.2007
     */
    private ArrayList detalle_Prom(ArrayList array, String codProm) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux = new ArrayList();
        String cod_p = "";
        for (int i = 0; 
             i < VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.size(); 
             i++) {
            aux = 
(ArrayList)(VariablesModuloVentas.vArrayList_Prod_Promociones_temporal.get(i));
            cod_p = (String)(aux.get(18));
            log.debug("cod de detal" + cod_p + ">>>>" + codProm);

            if ((cod_p). /*.trim()*/equalsIgnoreCase(codProm)) {
                log.debug("si son iguales ");
                nuevo.add((ArrayList)(aux.clone()));
            }
        }
        return nuevo;
    }

    /**
     * Agrupa productos que esten en ambos paquetes
     * retorna el nuevoa arreglo
     * @author dubilluz
     * @since 27.06.2007
     */
    private ArrayList agrupar(ArrayList array) {
        ArrayList nuevo = new ArrayList();
        ArrayList aux1 = new ArrayList();
        ArrayList aux2 = new ArrayList();
        int cantidad1 = 0;
        int cantidad2 = 0;
        int suma = 0;
        for (int i = 0; i < array.size(); i++) {
            aux1 = (ArrayList)(array.get(i));
            if (aux1.size() < 
                23) { //(((String)(aux1.get(19))).trim()).equalsIgnoreCase("Revisado")){
                for (int j = i + 1; j < array.size(); j++) {
                    aux2 = (ArrayList)(array.get(j));
                    if (aux2.size() < 23) {
                        if ((((String)(aux1.get(0))).trim()).equalsIgnoreCase((((String)(aux2.get(0))).trim()))) {
                            cantidad1 = 
                                    Integer.parseInt(((String)(aux1.get(4))).trim());
                            cantidad2 = 
                                    Integer.parseInt(((String)(aux2.get(4))).trim());
                            suma = cantidad1 + cantidad2;
                            aux1.set(4, suma + "");
                            ((ArrayList)(array.get(j))).add("Revisado");
                        }
                    }
                }
                nuevo.add(aux1);
            }
        }
        log.debug("Agrupado :" + nuevo.size());
        log.debug("Aggrup Elment :" + nuevo);
        return nuevo;
    }

    private void cargaStockLocales()
    {
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesModuloVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesModuloVentas.vDesc_Prod =  ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesModuloVentas.vNom_Lab = ((String)(myJTable.getValueAt(vFila, 4))).trim();
        VariablesModuloVentas.vUnid_Vta = lblUnidad.getText();

        DlgStockLocales dlgStockLocales = new DlgStockLocales(myParentFrame, "", true);
        dlgStockLocales.setVisible(true);
    }

    /**
     * Muestra para un Producto de Tarjeta Virtual
     * @author dubilluz
     * @since  29.08.2007
     */
    private int muestraIngresoCantidad_Tarjeta_Virtual() {
        if (myJTable.getRowCount() == 0)
            return 0;

        ArrayList aux = new ArrayList();
        int vFila = myJTable.getSelectedRow();
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                 COL_COD)); // codigo
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                 2)); //descripcion
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                 4)); //laboratorio
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 3)); //unidad
        aux.add(FarmaUtility.getValueFieldJTable(myJTable, vFila, 6)); //precio
        log.debug("DIEGO UBILLUZ >>  " + aux);

        VariablesModuloVentas.vArrayList_Prod_Tarjeta_Virtual.clear();
        VariablesModuloVentas.vArrayList_Prod_Tarjeta_Virtual = 
                (ArrayList)aux.clone();
        //(myJTable.getSelectedRow())
        aux.clear();
        log.debug("DIEGO UBILLUZ >>  " + aux);
        DlgIngresoCantidadTarjetaVirtual dlgIngresoCantidad = 
            new DlgIngresoCantidadTarjetaVirtual(myParentFrame, "", true);
        dlgIngresoCantidad.setVisible(true);

        if (FarmaVariables.vAceptar) {
            //seleccionaProducto();
            FarmaVariables.vAceptar = false;
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            return VariablesModuloVentas.cantidad_tarjeta_virtual;
        } else {
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
            return 0;
        }
    }

    /**
     * Coloca el tipo de producto 
     * @author dubilluz
     * @since  22.10.2007
     */
    private void muestraIndTipoProd(int pColumna, JLabel pLabel, 
                                    String tipoProd) {
        if (myJTable.getRowCount() > 0) {
            //String indProdRef = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
            if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[0]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[0]);
            else if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[1]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[1]);
            else if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[2]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[2]);
            else if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.IND_TIPO_PROD_COD[3]))
                pLabel.setText(ConstantsModuloVenta.IND_TIPO_PROD_DESC[3]);
        }
    }

    /**
     * Se muestra los productos sustitutos
     * @author Edgar Rios Navarro
     * @since 09.04.2008
     */
    private void muestraProductosSustitutos() {
        try {
            int vFila = tblProductos.getSelectedRow();
            if (vFila >= 0) {
                String codigoProducto = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
               // DBModuloVenta.cargaListaProductosSustitutos(tblModelListaSustitutos, 
               //                                        codigoProducto);
                tblListaSustitutos.repaint();
            }
        } catch (Exception sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, 
                                     "Error al Listar Productos Sustitutos.\n" +
                    sqlException, txtProducto);
        }
    }
    
    private void muestraLaboratorios() {
        try {
            DBModuloVenta.cargaListaLaboratorio(tblModeLaboratorio);
                tblLaboratorio.repaint();
            
        } catch (Exception sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, 
                                     "Error al Listar laboratorio.\n" +
                    sqlException, txtProducto);
        }
    }

    private void quitarFiltro() {
        vEjecutaAccionTeclaListado = false;
        if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            //lblDescFiltro.setText("");
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_N;
            tblModelListaSustitutos.clearTable();
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.fireTableDataChanged();
            clonarListadoProductos();
            setJTable(tblProductos);
            iniciaProceso(false);
            FarmaUtility.moveFocus(txtProducto);
            
           // lblF7.setText("[ F7 ] Filtrar Desc.");
        }
    }

    /**
   * Se filtran los productos dependiendo del filtro del resumen
   * @author JCORTEZ
   * @since 17.04.08
   */
    /* private void filtrarTablaProductosResumen2(String pTipoFiltro,
                                           String pCodFiltro) {
    try {
      tableModelListaPrecioProductos.clearTable();
      tableModelListaPrecioProductos.fireTableDataChanged();
      DBVentas.cargaListaProductosVenta_Filtro2(tableModelListaPrecioProductos,
                                               pTipoFiltro,
                                               pCodFiltro);
      if(tableModelListaPrecioProductos.getRowCount()>0)
      FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos,COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
      //else
      //FarmaUtility.showMessage(this, "Resultado vacio", null);
    } catch (SQLException sqlException) {
        log.error("",sqlException);
      FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Filtrado!!!", txtProducto);
    }
  }*/

    /**
     * Busqueda de cadena en el listado 
     * @param pLista
     * @param pCadena
     * @return
     */
    private boolean isExistProdListaFaltaCero(ArrayList pLista, 
                                              String pCadena) {
        if (pLista.size() > 0) {
            for (int i = 0; i < pLista.size(); i++) {
                if (pLista.get(i).toString().trim().equalsIgnoreCase(pCadena.trim())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Para clonar el listado de productos original.
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < VariablesModuloVentas.tableModelListaGlobalProductos.data.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)VariablesModuloVentas.tableModelListaGlobalProductos.data.get(i)).clone();
            arrayClone.add(aux);
        }
        
        tableModelListaPrecioProductos.clearTable();
        tableModelListaPrecioProductos.data = arrayClone;
        tblProductos.repaint();
        tblProductos.show();
        
        if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, 
                                 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
        
        //tableModelListaPrecioProductos.data = arrayClone;
    }

    /**
     * Se muestra el tratemiento del producto
     * @author JCORTEZ
     * @since 29.05.2008
     */
    private void mostrarTratamiento() {

        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();

        VariablesModuloVentas.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesModuloVentas.vDesc_Prod = 
                ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesModuloVentas.vNom_Lab = 
                ((String)(myJTable.getValueAt(vFila, 4))).trim();
        VariablesModuloVentas.vPorc_Igv_Prod = 
                ((String)(myJTable.getValueAt(vFila, 11))).trim();
        VariablesModuloVentas.vCant_Ingresada_Temp = "0";
        VariablesModuloVentas.vNumeroARecargar = "";
        VariablesModuloVentas.vVal_Prec_Lista_Tmp = "";
        VariablesModuloVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
        VariablesModuloVentas.vTipoProductoVirtual = "";
        //VariablesVentas.vVal_Prec_Pub = ((String)(myJTable.getValueAt(vFila,6))).trim();
        VariablesModuloVentas.vIndOrigenProdVta = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                 COL_ORIG_PROD);
        VariablesModuloVentas.vPorc_Dcto_2 = "0";
        VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_S;
        VariablesModuloVentas.vCantxDia = "";
        VariablesModuloVentas.vCantxDias = "";
        VariablesModuloVentas.vIndModificacion = FarmaConstants.INDICADOR_N;
        //guardaInfoProdVariables();

        DlgTratamiento dlgtratemiento = 
            new DlgTratamiento(myParentFrame, "", true);
        VariablesModuloVentas.vIngresaCant_ResumenPed = false;
        dlgtratemiento.setVisible(true);

        if (FarmaVariables.vAceptar) {
            seleccionaProducto();
            VariablesModuloVentas.vIndDireccionarResumenPed = true;
            FarmaVariables.vAceptar = false;
        } else {
            VariablesModuloVentas.vIndDireccionarResumenPed = false;
        }
    }


    /**
     * metodo de encargado de validar y agregar los cupones
     * @param nroCupon
     * @author Javier Callo Quispe
     * @since 04.03.2009
     */
    private void validarAgregarCupon(String nroCupon) {
        Map mapCupon;
        String codCampCupon = nroCupon.substring(0, 5);
        try {
            mapCupon = DBModuloVenta.getDatosCupon(codCampCupon, nroCupon);
            mapCupon.put("COD_CUPON", nroCupon);
        } catch (SQLException e) {
            log.debug("ocurrio un error al obtener datos del cupon:" + 
                      nroCupon + " error:" + e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al obtener datos del cupon.\n" +
                    e.getMessage() + 
                    "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.", 
                    txtProducto);
            return;
        }
        log.debug("datosCupon:" + mapCupon);
        //Se verifica si el cupon ya fue agregado tambien verifica si la campaña tambien ya esta agregado
        if (UtilityModuloVenta.existeCuponCampana(nroCupon, this, txtProducto))
            return;

        String indMultiuso = mapCupon.get("IND_MULTIUSO").toString().trim();
        boolean obligarIngresarFP = isFormaPagoUso_x_Cupon(codCampCupon);
        boolean yaIngresoFormaPago = false;
        //validacion de cupon en base de datos vigente y todo lo demas   

        //jquispe
        //String vIndFidCupon = "N";//obtiene el ind fid -- codCampCupon
        log.debug("CAMP CUPON:: codCampCupon " + codCampCupon);
        String vIndFidCupon = 
            UtilityFidelizacion.getIndfidelizadoUso(codCampCupon);

        if (vIndFidCupon.trim().equalsIgnoreCase("S")) {

            if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                funcionF12(codCampCupon);
                yaIngresoFormaPago = true;
            }


            if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {

                //Consultara si es necesario ingresar forma de pago x cupon
                // si es necesario solicitará el mismo.
                if (obligarIngresarFP) {
                    if (!yaIngresoFormaPago)
                        funcionF12(codCampCupon);
                }


                if (!UtilityModuloVenta.validarCuponEnBD(nroCupon, this, 
                                                    txtProducto, indMultiuso, 
                                                    VariablesFidelizacion.vDniCliente)) {
                    return;
                } else {
                    //agregando cupon al listado
                    VariablesModuloVentas.vArrayList_Cupones.add(mapCupon);
                    //24.06.2011
                    //Para Reinicializar todas las formas de PAGO.
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                    VariablesModuloVentas.vMensCuponIngre = 
                            "Se ha agregado el cupón " + nroCupon + 
                            " de la Campaña \n" +
                            mapCupon.get("DESC_CUPON").toString() + ".";
                    FarmaUtility.showMessage(this, VariablesModuloVentas.vMensCuponIngre, 
                                             txtProducto);
                }
            }
            txtProducto.setText("");
        } else {

            if (obligarIngresarFP) {
                if (!yaIngresoFormaPago)
                    funcionF12(codCampCupon);
            }

            if (!UtilityModuloVenta.validarCuponEnBD(nroCupon, this, txtProducto, 
                                                indMultiuso, 
                                                VariablesFidelizacion.vDniCliente)) {
                return;
            } else {
                //agregando cupon al listado
                VariablesModuloVentas.vArrayList_Cupones.add(mapCupon);
                //24.06.2011
                //Para Reinicializar todas las formas de PAGO.
                UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                VariablesModuloVentas.vMensCuponIngre = 
                        "Se ha agregado el cupón " + nroCupon + 
                        " de la Campaña \n" +
                        mapCupon.get("DESC_CUPON").toString() + ".";
                FarmaUtility.showMessage(this, VariablesModuloVentas.vMensCuponIngre, 
                                         txtProducto);
            }
            txtProducto.setText("");
        }
    }


    public boolean tieneDatoFormaPagoFidelizado() {
        if (VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S") || 
            (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S") && 
             VariablesFidelizacion.vCodFPagoTarjeta.trim().length() > 0))
            return true;
        else
            return false;
    }

    /**
     * Se agrega el cupon ingresado.
     * @param cadena
     * @author Edgar Rios Navarro
     * @since 02.07.2008
     * @deprecated
     */
    private void agregarCupon(String cadena) {

        if (VariablesModuloVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this, 
                                     "No puede agregar cupones a un pedido por convenio.", 
                                     txtProducto);
            return;
        }


        //JCORTEZ 15.08.08 obtiene indicador de multiuso de la campaña
        String ind_multiuso = "", cod_camp = "";
        ArrayList aux = new ArrayList();
        try {
            DBModuloVenta.obtieneIndMultiuso(aux, cadena);
            if (aux.size() > 0) {
                ind_multiuso = (String)((ArrayList)aux.get(0)).get(1);
                cod_camp = (String)((ArrayList)aux.get(0)).get(0);
            }
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al obtener indicador.\n" +
                    sql.getMessage(), txtProducto);
        }

        //Verifica que no exista en el arreglo
        if (UtilityModuloVenta.validaCupones(cadena, this, txtProducto))
            return;

        //Valida que la campana no haya sido agregado al pedido
        if (UtilityModuloVenta.validaCampanaCupon(cadena, this, txtProducto, 
                                             ind_multiuso, cod_camp))
            return;

        //Valida estructura del cupon en BBDD
        if (!UtilityModuloVenta.validaDatoCupon(cadena, this, txtProducto, 
                                           ind_multiuso))
            return;


        //txtDescProdOculto.setText("");
        txtProducto.setText("");

        // validaPedidoCupon();


        //dveliz 25.08.08

        VariablesCampana.vCodCupon = cadena;
        //ingresarDatosCampana();
        if (VariablesCampana.vListaCupones.size() > 0)
            VariablesCampana.vFlag = true;

        /*if(VariablesCampana.vNumIngreso==1)
     if(!UtilityVentas.validaDatoCupon(cadena,this,txtProducto,ind_multiuso)) return;  */
        //Fin dveliz
    }

    private void validarClienteTarjeta(String cadena) {
        if (VariablesModuloVentas.vEsPedidoConvenio) {
            FarmaUtility.showMessage(this, 
                                     "No puede agregar una tarjeta a un " + 
                                     "pedido por convenio.", txtProducto);
            return;
        }
        UtilityFidelizacion.validaLecturaTarjeta(cadena, myParentFrame);
        if (VariablesFidelizacion.vDataCliente.size() > 0) {
            ArrayList array = 
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            if (VariablesFidelizacion.vIndExisteCliente) {
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + 
                        VariablesFidelizacion.vApePatCliente + " " + 
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            } else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("0"))
                FarmaUtility.showMessage(this, 
                                         "Se agrego el DNI :" + VariablesFidelizacion.vDniCliente, 
                                         null);
            else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("2"))
                /*FarmaUtility.showMessage(this,
                                       "Cliente encontrado con DNI " + VariablesFidelizacion.vDniCliente,
                                       null);*/
                FarmaUtility.showMessage(this, "Bienvenido \n" +
                        VariablesFidelizacion.vNomCliente + " " + 
                        VariablesFidelizacion.vApePatCliente + " " + 
                        VariablesFidelizacion.vApeMatCliente + "\n" +
                        "DNI: " + VariablesFidelizacion.vDniCliente, null);
            else if (VariablesFidelizacion.vIndAgregoDNI.trim().equalsIgnoreCase("1"))
                FarmaUtility.showMessage(this, 
                                         "Se actualizaron los datos del DNI :" + 
                                         VariablesFidelizacion.vDniCliente, 
                                         null);

            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + 
                               VariablesFidelizacion.vApePatCliente + " " + 
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008
            VariablesFidelizacion.vIndAgregoDNI = "";

            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            log.debug("**************************************");
            log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + 
                      VariablesFidelizacion.vDniCliente);
            VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz = new ArrayList();
            log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" + VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz);

            //Ya no validara con Matriz 
            //Campaña Limitante 14.04.2009 DUBILLUZ
            /*VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,          																	FarmaConstants.INDICADOR_N);
          log.debug("************************");
          if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ
          	log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente"+VariablesFidelizacion.vDniCliente);
          	VariablesVentas.vArrayList_CampLimitUsadosMatriz = CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
          	log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz"+VariablesVentas.vArrayList_CampLimitUsadosMatriz);
          }
          */
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 
                0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(), 
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin 
        } else {
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            log.debug("**************************************");
            //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
            VariablesFidelizacion.vIndConexion = FarmaConstants.INDICADOR_N;

            log.debug("************************");
            // if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + 
                      VariablesFidelizacion.vDniCliente);
            VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz = 
                    UtilityFidelizacion.CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);
            log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" + VariablesModuloVentas.vArrayList_CampLimitUsadosMatriz);
            //  }// JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            //cargando las campañas automaticas limitadas en cantidad de usos desde matriz

            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + 
                               VariablesFidelizacion.vApePatCliente + " " + 
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008
        }


        txtProducto.setText("");
    }
/*
    private void mostrarBuscarTarjetaPorDNI() {

        DlgFidelizacionBuscarTarjetas dlgBuscar = 
            new DlgFidelizacionBuscarTarjetas(myParentFrame, "", true);
        dlgBuscar.setVisible(true);
        log.debug("vv DIEGO:" + FarmaVariables.vAceptar);
        log.debug("dat_1:" + VariablesFidelizacion.vDataCliente);
        log.debug(" VariablesFidelizacion.vNomCliente_1:" + 
                           VariablesFidelizacion.vNomCliente);
        log.debug(" VariablesFidelizacion.vDniCliente_1:" + 
                           VariablesFidelizacion.vDniCliente);
        if (FarmaVariables.vAceptar) {
            log.debug("en aceptar");
            log.debug("dat:" + VariablesFidelizacion.vDataCliente);
            ArrayList array = 
                (ArrayList)VariablesFidelizacion.vDataCliente.get(0);
            log.debug("des 1");
            //JCALLO 02.10.2008
            //VariablesFidelizacion.vDniCliente = String.valueOf(array.get(0));
            //seteando los datos del cliente en las variables con los datos del array
            UtilityFidelizacion.setVariablesDatos(array);
            log.debug("des 2");
           
            log.debug(" VariablesFidelizacion.vNomCliente:" + 
                               VariablesFidelizacion.vNomCliente);
            log.debug(" VariablesFidelizacion.vDniCliente:" + 
                               VariablesFidelizacion.vDniCliente);
            FarmaUtility.showMessage(this, "Bienvenido \n" +
                    VariablesFidelizacion.vNomCliente + " " + 
                    VariablesFidelizacion.vApePatCliente + " " + 
                    VariablesFidelizacion.vApeMatCliente + "\n" +
                    "DNI: " + VariablesFidelizacion.vDniCliente, null);
            //dubilluz 19.07.2011 - inicio
            if (VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length() > 
                0) {
                UtilityFidelizacion.grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(), 
                                                      VariablesFidelizacion.vDniCliente);
            }
            //dubilluz 19.07.2011 - fin 


            //jcallo 02.10.2008
            lblCliente.setText(VariablesFidelizacion.vNomCliente + " " + 
                               VariablesFidelizacion.vApePatCliente + " " + 
                               VariablesFidelizacion.vApeMatCliente);
            //fin jcallo 02.10.2008

            //DAUBILLUZ -- Filtra los DNI anulados
            //25.05.2009
            VariablesFidelizacion.vDNI_Anulado = 
                    UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
            VariablesFidelizacion.vAhorroDNI_x_Periodo = 
                    UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
            VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 
                    UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);

            log.info("Variable de DNI_ANULADO: " + 
                     VariablesFidelizacion.vDNI_Anulado);
            log.info("Variable de vAhorroDNI_x_Periodo: " + 
                     VariablesFidelizacion.vAhorroDNI_x_Periodo);
            log.info("Variable de vMaximoAhorroDNIxPeriodo: " + 
                     VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);

            setMensajeDNIFidelizado();
            if (VariablesFidelizacion.vDNI_Anulado) {
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);

                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
                log.debug("**************************************");
                //VariablesFidelizacion.vIndConexion = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                VariablesFidelizacion.vIndConexion = 
                        FarmaConstants.INDICADOR_N;
                log.debug("**************************************");
                //if(VariablesFidelizacion.vIndConexion.equals(FarmaConstants.INDICADOR_S)){//VER SI HAY LINEA CON MATRIZ   //VER SI HAY LINEA CON MATRIZ  JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + 
                          VariablesFidelizacion.vDniCliente);
                VariablesVentas.vArrayList_CampLimitUsadosMatriz = 
                        CampLimitadasUsadosDeMatrizXCliente(VariablesFidelizacion.vDniCliente);

                log.debug("******VariablesVentas.vArrayList_CampLimitUsadosMatriz" + 
                          VariablesVentas.vArrayList_CampLimitUsadosMatriz);
                // } // JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
                //cargando las campañas automaticas limitadas en cantidad de usos desde matriz
            } else {
                log.info("Cliente esta invalidado para descuento...");
            }
        }
    }
*/
    /**
     * metodo encargado de registrar y/o asociar cliente a las campanias de acumulacion
     * @param 
     * @author Javier Callo Quispe
     * @since 15.12.2008
     */
    private void asociarCampAcumulada(String codProd) {
        VariablesCampAcumulada.vCodProdFiltro = codProd;
        log.info("VariablesCampAcumulada.vCodProdFiltro:" + 
                           VariablesCampAcumulada.vCodProdFiltro);

        FarmaVariables.vAceptar = false;

        //lanzar dialogo las campañas por asociar
        DlgListaCampAcumulada dlgListaCampAcumulada = 
            new DlgListaCampAcumulada(myParentFrame, "", true);
        dlgListaCampAcumulada.setVisible(true);

        //cargando las campanas de fidelizacion
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            log.debug("INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..:" + 
                      VariablesFidelizacion.vNumTarjeta);
            UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta.trim());
            log.debug("FIN INVOCANDO CARGAR CAMPAÑAS DEL CLIENTES ..");

            /**mostranto el nombre del cliente **/
            lblCliente.setText(VariablesFidelizacion.vNomCliente); /*+" "
                               +VariablesFidelizacion.vApePatCliente+" "
                               +VariablesFidelizacion.vApeMatCliente);*/
            //VariablesFidelizacion.vApeMatCliente = Variables

            log.debug("imprmiendo todas las variables de fidelizacion");
            log.debug("VariablesFidelizacion.vApeMatCliente:" + 
                      VariablesFidelizacion.vApeMatCliente);
            log.debug("VariablesFidelizacion.vApePatCliente:" + 
                      VariablesFidelizacion.vApePatCliente);
            log.debug("VariablesFidelizacion.vCodCli:" + 
                      VariablesFidelizacion.vCodCli);
            log.debug("VariablesFidelizacion.vCodGrupoCia:" + 
                      VariablesFidelizacion.vCodGrupoCia);
            log.debug("VariablesFidelizacion.vDataCliente:" + 
                      VariablesFidelizacion.vDataCliente);
            log.debug("VariablesFidelizacion.vDireccion:" + 
                      VariablesFidelizacion.vDireccion);
            log.debug("VariablesFidelizacion.vDniCliente:" + 
                      VariablesFidelizacion.vDniCliente);
            log.debug("VariablesFidelizacion.vDocValidos:" + 
                      VariablesFidelizacion.vDocValidos);
            log.debug("VariablesFidelizacion.vEmail:" + 
                      VariablesFidelizacion.vEmail);
            log.debug("VariablesFidelizacion.vFecNacimiento:" + 
                      VariablesFidelizacion.vFecNacimiento);
            log.debug("VariablesFidelizacion.vIndAgregoDNI:" + 
                      VariablesFidelizacion.vIndAgregoDNI);
            log.debug("VariablesFidelizacion.vIndConexion:" + 
                      VariablesFidelizacion.vIndConexion);
            log.debug("VariablesFidelizacion.vIndEstado:" + 
                      VariablesFidelizacion.vIndEstado);
            log.debug("VariablesFidelizacion.vIndExisteCliente:" + 
                      VariablesFidelizacion.vIndExisteCliente);
            log.debug("VariablesFidelizacion.vListCampañasFidelizacion:" + 
                      VariablesFidelizacion.vListCampañasFidelizacion);
            log.debug("VariablesFidelizacion.vNomCliente:" + 
                      VariablesFidelizacion.vNomCliente);
            log.debug("VariablesFidelizacion.vNomClienteImpr:" + 
                      VariablesFidelizacion.vNomClienteImpr);
            log.debug("VariablesFidelizacion.vNumTarjeta:" + 
                      VariablesFidelizacion.vNumTarjeta);
            log.debug("VariablesFidelizacion.vSexo:" + 
                      VariablesFidelizacion.vSexo);
            log.debug("VariablesFidelizacion.vSexoExists:" + 
                      VariablesFidelizacion.vSexoExists);
            log.debug("VariablesFidelizacion.vTelefono:" + 
                      VariablesFidelizacion.vTelefono);
            log.debug("fin de imprmir todas las variables de fidelizacion");
        }

    }

    private boolean isExisteProdCampana(String pCodProd) {
        //lblMensajeCampaña.setVisible(true);
        String pRespta = "N";
        try {
            lblMensajeCampaña.setText("");
            pRespta = DBModuloVenta.existeProdEnCampañaAcumulada(pCodProd, VariablesFidelizacion.vDniCliente);
            if (pRespta.trim().equalsIgnoreCase("E"))
                lblMensajeCampaña.setText("    Cliente inscrito en Campaña Acumulada.");

            if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                lblMensajeCampaña.setText("   Promoción: \" Acumula tu Compra y Gana \"");
        } catch (SQLException e) {
            log.error(null, e);
            pRespta = "N";
        }

        if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        return true;
    }

    /**
     * obtener todas las campañas de fidelizacion automaticas usados en el pedido
     * 
     * */
    /*private ArrayList CampLimitadasUsadosDeMatrizXCliente(String dniCliente) {
        ArrayList listaCampLimitUsadosMatriz = new ArrayList();
        try {
            //listaCampLimitUsadosMatriz = DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);
            listaCampLimitUsadosMatriz = 
                    DBCaja.getListaCampUsadosLocalXCliente(dniCliente); // DBCaja.getListaCampUsadosMatrizXCliente(dniCliente);// JCHAVEZ 27092009. se comentó pues no es necesario que valide ya que se consultará al local
            if (listaCampLimitUsadosMatriz.size() > 0) {
                listaCampLimitUsadosMatriz = 
                        (ArrayList)listaCampLimitUsadosMatriz.get(0);
            }
            log.debug("listaCampLimitUsadosMatriz listaCampLimitUsadosMatriz ===> " + 
                      listaCampLimitUsadosMatriz);
        } catch (Exception e) {
            log.debug("error al obtener las campañas limitadas ya usados por cliente en MATRIZ : " + 
                      e.getMessage());
        }
        return listaCampLimitUsadosMatriz;
    }
    */
/*
    private void setMensajeDNIFidelizado() {
        if (VariablesFidelizacion.vDniCliente.trim().length() > 7 && 
            VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            if (!VariablesFidelizacion.vDNI_Anulado) {
                lblMensajeCampaña.setText("  DNI no afecto a Descuento.");
                lblMensajeCampaña.setVisible(true);
            } else {
                lblMensajeCampaña.setText("");
                lblMensajeCampaña.setVisible(false);
            }

        }
    }
*/

    /**
     * Se valida el rol del usuario 
     * @author JCORTEZ
     * @since 24.07.2009
     * */
    private boolean ValidaRolUsu(String vRol) {

        boolean valor = true;
        String result = "";

        log.debug("FarmaVariables.vNuSecUsu : " + 
                           FarmaVariables.vNuSecUsu);
        try {
            result = DBModuloVenta.verificaRolUsuario(FarmaVariables.vNuSecUsu, vRol);
            log.debug("result : " + result);
            if (result.equalsIgnoreCase("N"))
                valor = false;
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), txtProducto);
        }
        return valor;
    }


    private boolean cargaValidaLogin() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = 
                new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, 
                             true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al validar rol de usuariario \n : " + 
                                     e.getMessage(), null);
        }
        return FarmaVariables.vAceptar;
    }


    private void lblPedDelivery_actionPerformed(ActionEvent e) {

        //JCALLO 19.12.2008 comentado sobre la opcion de ver pedidos delivery..y usarlo para el tema inscribir cliente a campañas acumuladas
        //JCORTEZ 07.08.09 Se habilita nuevamente la opcion de pedido automatico.
        /** JCALLO INHABILITAR F9 02.10.2008* **/
        log.debug("HABILITAR F9 : " + VariablesModuloVentas.HabilitarF9);

        if (VariablesModuloVentas.HabilitarF9.equalsIgnoreCase(ConstantsModuloVenta.ACTIVO)) {
            if (UtilityModuloVenta.evaluaPedidoDelivery(this, txtProducto, VariablesModuloVentas.vArrayList_PedidoVenta)) {
                evaluaTitulo();
                FarmaUtility.moveFocus(txtProducto);
                // Inicio Adicion Delivery 28/04/2006 Paulo
                if (VariablesModuloVentas.vEsPedidoDelivery) {
                    generarPedidoDelivery();
                }
                // Fin Adicion Delivery 28/04/2006 Paulo
            }
        } else
            FarmaUtility.showMessage(this, "Opcion no disponible en local.", 
                                     txtProducto);
    }

    private boolean validaLoginVendedor() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;
        boolean flag = false;
        log.debug("numsec: " + numsec);
        try {
            DlgLogin dlgLogin = 
                new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, 
                             true);
            //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);  
            dlgLogin.setVisible(true);
            log.debug("FarnaVariables.NumSec: " + 
                               FarmaVariables.vNuSecUsu);
            if (FarmaVariables.vAceptar) {
                if (numsec.equalsIgnoreCase(FarmaVariables.vNuSecUsu)) {
                    log.debug("numsec 2: " + numsec);
                    flag = true;
                } else {
                    FarmaUtility.showMessage(this, 
                                             "Ud. ha ingresado un usuario diferente al que inicio la Venta." + 
                                             "\nIngrese Usuario que Inicio venta o vuelva a ingresar otro Usuario.", 
                                             txtProducto);
                    flag = false;
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al validar rol de usuario \n : " + 
                                     e.getMessage(), null);
            flag = false;
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return flag;
    }

    private boolean validaLoginQF() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = 
                new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, 
                             true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al validar rol de usuariario \n : " + 
                                     e.getMessage(), null);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return FarmaVariables.vAceptar;
    }
    //jquispe 03.05.2010 
    //Cambia el proceso de actualizacion en mostrar dagtos del producto.

    private void UpdateReleaseProd(KeyEvent e) {

     //   muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraProductoPromocion(17, lblProdProm);
      //  muestraIndTipoProd(16, lblIndTipoProd);
        // JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);

        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
        if (!(e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS)) {

             actualizaListaProductosAlternativos();
            }
        }
        colocaTotalesPedido();
    }
    
    public void funcionF12(String pCodCampanaCupon) {
        log.debug("Funcion F12");
        
        //No hbra fidelizacion
        /* AuxiliarFidelizacion.funcionF12(pCodCampanaCupon,txtProducto,this.myParentFrame,
                                        lblMensajeCampaña,lblCliente,this,"L",lblDNI_SIN_COMISION);
        
         */
    }

    public boolean isFormaPagoUso_x_Cupon(String codCampCupon) {
        String valor = "N";
        try {
            valor = 
                    DBFidelizacion.isValidaFormaPagoUso_x_Campana(codCampCupon).trim();
        } catch (SQLException e) {
            log.error("",e);
        }

        if (valor.trim().equalsIgnoreCase("S"))
            return true;

        return false;
    }


    private void validaIngresoTarjetaPagoCampanaAutomatica(String nroTarjetaFormaPago) {
        if (isNumerico(nroTarjetaFormaPago)) {
            Map mapCupon;
            boolean obligarIngresarFP = false;
            boolean yaIngresoFormaPago = false;

            VariablesFidelizacion.tmp_NumTarjeta_unica_Campana = 
                    nroTarjetaFormaPago;
            String pExisteAsociado = 
                UtilityFidelizacion.existeDniAsociado(nroTarjetaFormaPago);
            if (pExisteAsociado.trim().equalsIgnoreCase("S")) {
                //VALIDA EL CLIENTE POR TARJETA 12.01.2011
                String cadena = nroTarjetaFormaPago;
                validarClienteTarjeta(cadena);
                //VariablesFidelizacion.vNumTarjeta = cadena.trim();
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                    log.debug("RRRR");
                    UtilityFidelizacion.operaCampañasFidelizacion(cadena);
                    //DAUBILLUZ -- Filtra los DNI anulados
                    //25.05.2009
                    VariablesFidelizacion.vDNI_Anulado = 
                            UtilityFidelizacion.isDniValido(VariablesFidelizacion.vDniCliente);
                    VariablesFidelizacion.vAhorroDNI_x_Periodo = 
                            UtilityFidelizacion.getAhorroDNIxPeriodoActual(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);
                    VariablesFidelizacion.vMaximoAhorroDNIxPeriodo = 
                            UtilityFidelizacion.getMaximoAhorroDnixPeriodo(VariablesFidelizacion.vDniCliente,VariablesFidelizacion.vNumTarjeta);

                    log.info("Variable de DNI_ANULADO: " + 
                             VariablesFidelizacion.vDNI_Anulado);
                    log.info("Variable de vAhorroDNI_x_Periodo: " + 
                             VariablesFidelizacion.vAhorroDNI_x_Periodo);
                    log.info("Variable de vMaximoAhorroDNIxPeriodo: " + 
                             VariablesFidelizacion.vMaximoAhorroDNIxPeriodo);

                    AuxiliarFidelizacion.setMensajeDNIFidelizado(lblMensajeCampaña,"L",txtProducto,this);
                }
            } else {
                if (VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                    funcionF12("N");
                    yaIngresoFormaPago = true;
                }

            }

            //cargar las campañas de tipo automatica
            String cadenaTarjeta = 
                UtilityFidelizacion.getDatosTarjetaFormaPago(nroTarjetaFormaPago.trim());
            String[] datos = cadenaTarjeta.split("@");
            if (datos.length == 2) {
                VariablesFidelizacion.vIndUsoEfectivo = "N";
                VariablesFidelizacion.vIndUsoTarjeta = "S";
                VariablesFidelizacion.vCodFPagoTarjeta = 
                        datos[0].toString().trim();
                VariablesFidelizacion.vNamePagoTarjeta = 
                        datos[1].toString().trim();

                //if(VariablesFidelizacion.vDNI_Anulado)
                //{
                if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                    UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
                VariablesFidelizacion.vNumTarjetaCreditoDebito_Campana = 
                        nroTarjetaFormaPago.trim();
                //}
                txtProducto.setText("");
            }
        }
    }


    public boolean isNumerico(String pCadena) {
        int numero = 0;
        boolean pRes = false;
        try {
            for (int i = 0; i < pCadena.length(); i++) {
                numero = Integer.parseInt(pCadena.charAt(i) + "");
                pRes = true;
            }
        } catch (NumberFormatException e) {
            pRes = false;
        }
        return pRes;
    }

    public void setFormatoTarjetaCredito(String pCadena) {
        
        
        String pCadenaNueva = 
            UtilityFidelizacion.pIsTarjetaVisaRetornaNumero(pCadena).trim();
        
        if(pCadenaNueva.length()>0){
            
            
        if (!pCadenaNueva.trim().equalsIgnoreCase("N")) {
            log.debug("Es tarjeta");
            txtProducto.setText(pCadenaNueva.trim());
            pasoTarjeta = true;             
        } else {
            log.debug("NO ES tarjeta");
            pasoTarjeta = false;
                      }
        
        
        }


    }

    private boolean validaVentaConMenos() {

        if (myJTable.getRowCount() == 0)
            return false;

        FarmaVariables.vAceptar = true;
        boolean flagContinua = true;

        try {
            log.debug("vAceptar 1: " + FarmaVariables.vAceptar);

            // Verifica si es obligatorio ingresar codigo de barra 
            log.debug("vAceptar2 : " + FarmaVariables.vAceptar);
            log.debug("vCod_Prod: " + VariablesModuloVentas.vCod_Prod);
            if (DBModuloVenta.getIndCodBarra(VariablesModuloVentas.vCod_Prod).equalsIgnoreCase("S") && 
                FarmaVariables.vAceptar && VariablesModuloVentas.vIndEsCodBarra) {

                //valida si se ha insertado cod Barra
                if (UtilityModuloVenta.validaCodBarraLocal(txtProducto.getText().trim())) {
                    DlgIngreseCodBarra dlgIngCodBarra = 
                        new DlgIngreseCodBarra(myParentFrame, "", true);
                    dlgIngCodBarra.setVisible(true);
                    flagContinua = VariablesModuloVentas.bIndCodBarra;
                }
                if (VariablesModuloVentas.vCodigoBarra.equalsIgnoreCase(txtProducto.getText().trim())) {
                    flagContinua = true;
                }
                //flagContinua = VariablesVentas.bIndCodBarra; 
                FarmaVariables.vAceptar = flagContinua;
                log.debug("COD_BArra, flagcontinua: " + flagContinua);
            }

            // Verifica si posee Mensaje de Producto
            String vMensajeProd = DBModuloVenta.getMensajeProd(VariablesModuloVentas.vCod_Prod);
            if (!vMensajeProd.equalsIgnoreCase("N") && 
                FarmaVariables.vAceptar) {

                String sMensaje = "";
                sMensaje = UtilityModuloVenta.saltoLineaConLimitador(vMensajeProd);
                //ENVIO vMensajeProd LLAMAR METODO RETORNAR SMENSAJE CON SALTO DE LINEA
                log.debug(sMensaje);
                FarmaUtility.showMessage(this, sMensaje, txtProducto);
            }

            // Valida ID Usuario 
            String pInd = DBModuloVenta.getIndSolIdUsu(VariablesModuloVentas.vCod_Prod).trim().toUpperCase();
            if (pInd.equalsIgnoreCase("S") && FarmaVariables.vAceptar) {
                //llamar a Usuario para visualizar 
                flagContinua = validaLoginVendedor();
                FarmaVariables.vAceptar = flagContinua;
                log.debug("SolId. flagContinua: " + flagContinua);
            } else {
                if (pInd.equalsIgnoreCase("J") && FarmaVariables.vAceptar) {
                    log.debug("*** Valida Producto Venta Cero");
                    //llamar a Usuario para visualizar 
                    flagContinua = validaLoginQF();
                    FarmaVariables.vAceptar = flagContinua;
                    log.debug("SolId. flagContinua : " + 
                                       flagContinua);
                }
            }

        } catch (Exception sql) {
            FarmaUtility.showMessage(this, "Error en Validar Producto: " + sql, 
                                     txtProducto);
            log.error("",sql);
        }

        //if(flagContinua) 
        //Continua con el proceso

        return flagContinua;
    }

    //Dubilluz - 06.12.2011

    public void ingresaMedicoFidelizado() {
        /*
        AuxiliarFidelizacion.ingresoMedico(this.myParentFrame,lblMedico,lblMensajeCampaña,lblCliente,
                                           this,"L",lblDNI_SIN_COMISION,txtProducto);*/
    }

    /**
     * Muestra ventana de registro de Recetario Magistral
     * 
     * @author ERIOS
     * @since 20.05.2013
     */
    private void muestraIngresoRecetarioMagistral() {
        if (VariablesPtoVenta.vIndVerReceMagis.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            if (myJTable.getRowCount() == 0)
                return;

            int row = myJTable.getSelectedRow();
            VariablesModuloVentas.vCod_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, COL_COD);
            VariablesModuloVentas.vDesc_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
            VariablesModuloVentas.vNom_Lab = FarmaUtility.getValueFieldJTable(myJTable, row, 4);
            VariablesModuloVentas.vUnid_Vta = FarmaUtility.getValueFieldJTable(myJTable, row, 3);

            VariablesModuloVentas.vVal_Prec_Lista = FarmaUtility.getValueFieldJTable(myJTable, row, 10);
            //VariablesVentas.vPorc_Dcto_1 = FarmaUtility.getValueFieldJTable(myJTable, row, 2);
            VariablesModuloVentas.vVal_Bono = FarmaUtility.getValueFieldJTable(myJTable, row, 7);
            VariablesModuloVentas.vPorc_Igv_Prod = FarmaUtility.getValueFieldJTable(myJTable, row, 11);

            VariablesModuloVentas.vTipoProductoVirtual = FarmaUtility.getValueFieldJTable(myJTable, row, 14);

            VariablesModuloVentas.vMontoARecargar_Temp = "0";
            VariablesModuloVentas.vNumeroARecargar = "";

            VariablesModuloVentas.vIndOrigenProdVta = FarmaUtility.getValueFieldJTable(myJTable, row, COL_ORIG_PROD);
            VariablesModuloVentas.vPorc_Dcto_2 = "0";
            VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
            VariablesModuloVentas.vCantxDia = "";
            VariablesModuloVentas.vCantxDias = "";

            VariablesModuloVentas.vProductoVirtual = true;
            VariablesRecetario.strCodigoRecetario=null;
            
            DlgResumenRecetarioMagistral dlgResumenRecetarioMagistral = new DlgResumenRecetarioMagistral(myParentFrame, "", true);
            dlgResumenRecetarioMagistral.setVisible(true);
            
            VariablesRecetario.vMapDatosPacienteMedico=null;
            VariablesRecetario.vArrayEsteril=null;
            
            if (FarmaVariables.vAceptar) {
                VariablesModuloVentas.vCant_Ingresada  = VariablesRecetario.strCant_Recetario;
                //VariablesRecetario.strCodigoRecetario = strCodigoRecetario;
                VariablesModuloVentas.vVal_Prec_Lista_Tmp = VariablesRecetario.strPrecioTotal;
                VariablesModuloVentas.vVal_Prec_Vta = VariablesRecetario.strPrecioTotal;
                //FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesRecetario.strPrecioTotal));
                VariablesModuloVentas.vVal_Prec_Lista = VariablesRecetario.strPrecioTotal;
                //FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesRecetario.strPrecioTotal));
                seleccionaProducto();

                VariablesModuloVentas.venta_producto_virtual = true;
                VariablesModuloVentas.vIndDireccionarResumenPed = true;
                FarmaVariables.vAceptar = false;
            } else {
                VariablesModuloVentas.vIndDireccionarResumenPed = false;
                VariablesModuloVentas.vProductoVirtual = false;
            }
        } else{
            FarmaUtility.showMessage(this,"El registro de Recetario Magistral no está habilitado.",null);
        }
    }

    /**
     * Se filtra/quita filtro de busqueda
     * @author ERIOS
     * @since 29.08.2013
     */
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
        /*if (VariablesPtoVenta.vInd_Filtro.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            quitarFiltro();
        }else{
            filtrarBusquedaGoogle();
        }*/
    }
    
    /**
     * Se filtra el listado de productos segun la descripcion que se ingrese
     * @author ERIOS
     * @since 29.08.2013
     */
    private void filtrarBusquedaGoogle() {
        String condicion = txtProducto.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelListaPrecioProductos.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(COL_DESC_PROD).toString().toUpperCase().trim();
                String pCodigo = fila.get(1).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(descProd.contains(condicion)||pCodigo.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModelListaPrecioProductos.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelListaPrecioProductos.fireTableDataChanged();
            setJTable(tblProductos);
            //iniciaProceso(false);
            tblModelListaSustitutos.clearTable();
            
            
            if(tblProductos.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                if(tblProductos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" filas para el filtro aplicado");
            }
            
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_PedidoVenta, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                          0);

            
        }
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblProductos, 0, 0);
    }

    /**
     * 
     * @author ERIOS
     * @since 26.11.2013
     */
    private void deshabilitaProducto() {
        /*txtProducto.setEnabled(false);
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000*2);
                } catch (InterruptedException e) {
                }
                txtProducto.setEnabled(true);
                pasoTarjeta = false;
                FarmaUtility.moveFocus(txtProducto);
            }
        }.start();*/
    }

    
    
    
    private void ingresoCotizacionNuevo() {
        DlgListaCotizaciones dlgReceta = new DlgListaCotizaciones(myParentFrame, "", true);
        dlgReceta.setVisible(true);
        if (FarmaVariables.vAceptar&&dlgReceta.isVOperaReceta()) {
            FarmaVariables.vAceptar = false;
            String pCodCia    = dlgReceta.getVCodCia();
            String pCodLocal  = dlgReceta.getVCodLocal();
            String pNumReceta = dlgReceta.getVNumReceta();
            
            pRecetaCodCia    = pCodCia;
            pRecetaCodLocal  = pCodLocal;
            pRecetaNumReceta = pNumReceta;
            
            FarmaUtility.showMessage(this, "Se va Procesar la Cotización \n"+
                                            "N° "+pNumReceta+"\n"+
                                            "Por favor de esperar que termine.", txtProducto);
            
            DlgPBReceta dlgEspera = new DlgPBReceta(myParentFrame,"",true,this,pCodCia,pCodLocal,pNumReceta);
            dlgEspera.setVisible(true);
            VariablesModuloVentas.vCodCia_Receta = pRecetaCodCia;
            VariablesModuloVentas.vCodLocal_Receta= pRecetaCodLocal;
            VariablesModuloVentas.vNumPedido_Receta= pRecetaNumReceta;
        }
    }
    
        
    private void ingresoPedidoAnulado() {
        DlgListaPedidosAnulados dlgReceta = new DlgListaPedidosAnulados(myParentFrame, "", true);
        dlgReceta.setVisible(true);
        if (FarmaVariables.vAceptar&&dlgReceta.isVOperaReceta()) {
            FarmaVariables.vAceptar = false;
            String pCodCia    = dlgReceta.getVCodCia();
            String pCodLocal  = dlgReceta.getVCodLocal();
            String pNumReceta = dlgReceta.getVNumReceta();
            
            pRecetaCodCia    = pCodCia;
            pRecetaCodLocal  = pCodLocal;
            pRecetaNumReceta = pNumReceta;
            
            FarmaUtility.showMessage(this, "Se va obtener los productos del pedido seleccionado \n"+
                                            "N° "+pNumReceta+"\n"+
                                            "Por favor de esperar que termine.", txtProducto);
            
            DlgPBReceta dlgEspera = new DlgPBReceta(myParentFrame,"",true,this,pCodCia,pCodLocal,pNumReceta);
            dlgEspera.setVisible(true);
            VariablesModuloVentas.vCodCia_Receta = pRecetaCodCia;
            VariablesModuloVentas.vCodLocal_Receta= pRecetaCodLocal;
            VariablesModuloVentas.vNumPedido_Receta= pRecetaNumReceta;
        }
    }
    
    public void procesaReceta(){
        
        txtProducto.setText("Se procesa los productos 1 x 1");
        
        ArrayList vLista = new ArrayList();

        try {
            DBReceta.getDetRecetaOpera(vLista, pRecetaCodCia, pRecetaCodLocal, pRecetaNumReceta);
            if(vLista.size()>0){
                for(int i=0;i<vLista.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(vLista, i, 5).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                        int vFila = -1;
                        for(int j=0;j< tableModelListaPrecioProductos.data.size();j++){ 
                            if(FarmaUtility.getValueFieldArrayList(vLista, i, 0).trim().equalsIgnoreCase(FarmaUtility.getValueFieldArrayList( tableModelListaPrecioProductos.data, j, 1))){
                                    vFila=j;
                                    break;
                                    }
                            }

                        VariablesModuloVentas.vCod_Prod = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        
                        ArrayList myArray = new ArrayList();
                        DBModuloVenta.obtieneInfoProductoVta(myArray, VariablesModuloVentas.vCod_Prod);
                        VariablesModuloVentas.vVal_Frac = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
                        VariablesModuloVentas.vCod_Prod = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        VariablesModuloVentas.vDesc_Prod = 
                                ((String)(myJTable.getValueAt(vFila, 2))).trim();
                        VariablesModuloVentas.vNom_Lab = 
                                ((String)(myJTable.getValueAt(vFila, 4))).trim();
                        VariablesModuloVentas.vPorc_Igv_Prod = 
                                ((String)(myJTable.getValueAt(vFila, 11))).trim();
                        VariablesModuloVentas.vCant_Ingresada_Temp = "0";
                        VariablesModuloVentas.vNumeroARecargar = "";
                        VariablesModuloVentas.vVal_Prec_Lista_Tmp = "";
                        VariablesModuloVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
                        VariablesModuloVentas.vTipoProductoVirtual = "";
                        VariablesModuloVentas.vVal_Prec_Pub = 
                                ((String)(myJTable.getValueAt(vFila, 6))).trim();
                        VariablesModuloVentas.vIndOrigenProdVta = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                                 COL_ORIG_PROD);
                        VariablesModuloVentas.vPorc_Dcto_2 = "0";
                        VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
                        VariablesModuloVentas.vCantxDia = "";
                        VariablesModuloVentas.vCantxDias = "";

                        VariablesModuloVentas.vValFracLocal = VariablesModuloVentas.vVal_Frac;

                        VariablesModuloVentas.vCod_Prod = FarmaUtility.getValueFieldArrayList(vLista, i, 0);
                           DlgIngresoCantidad dlgIngCantidad = new DlgIngresoCantidad(myParentFrame, "", true,ConstantsPtoVenta.TIPO_VENTA);
                        VariablesModuloVentas.vIngresaCant_ResumenPed = false;
                           dlgIngCantidad.setCantInic(Integer.parseInt(FarmaUtility.getValueFieldArrayList(vLista, i, 1)));
                           
                           
                           // fracc
                           // unidad
                           // precio venta UNITARIO
                           VariablesModuloVentas.vCant_Ingresada = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
                           VariablesModuloVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 4);
                           VariablesModuloVentas.vVal_Frac = FarmaUtility.getValueFieldArrayList(vLista, i, 2);
                           VariablesModuloVentas.vUnid_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 3);
                           
                           //dlgIngCantidad.setSize(10, 10);
                           dlgIngCantidad.abreOpera();
                        // fracc
                        // unidad
                        // precio venta UNITARIO
                        VariablesModuloVentas.vCant_Ingresada = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
                        VariablesModuloVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 4);
                        VariablesModuloVentas.vVal_Frac = FarmaUtility.getValueFieldArrayList(vLista, i, 2);
                        VariablesModuloVentas.vUnid_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 3);
                        
                           ////////////selecciona product /////////
                           Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
                           double auxCantIng = 
                               FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada);
                           double auxPrecVta = 
                               FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta);
                        VariablesModuloVentas.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
                        VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                           FarmaUtility.setCheckValue(myJTable, false,vFila);
                           Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
                        UtilityModuloVenta.operaProductoSeleccionadoEnArrayList_02(valor, VariablesModuloVentas.secRespStk); //ASOSA, 01.07.2010
                           pintaCheckOtroJTable(myJTable, valorTmp);
                           colocaTotalesPedido();
                           //FarmaUtility.showMessage(this,"VariablesVentas.vCod_Prod " + VariablesModuloVentas.vCod_Prod,txtProducto);

                        VariablesModuloVentas.vIndDireccionarResumenPed = false;
                           FarmaVariables.vAceptar = false;   
                    }
             }
                VariablesModuloVentas.vIndDireccionarResumenPed = true;
                VariablesModuloVentas.vIndF11 = true;
                
             UtilityModuloVenta.cargaDatoClienteCotizacion(pRecetaCodCia, pRecetaCodLocal, pRecetaNumReceta);   
                
                
             aceptaOperacion();
                
         }
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        VariablesModuloVentas.vIndDireccionarResumenPed = true;
        VariablesModuloVentas.vIndF11 = true;
        aceptaOperacion();
    }
    public void procesaPedidoAnulado(){
        
        txtProducto.setText("Se procesa los productos 1 x 1");
        
        ArrayList vLista = new ArrayList();

        try {
            DBReceta.getDetPedidoAnulaOpera(vLista, pRecetaCodCia, pRecetaCodLocal, pRecetaNumReceta);
            if(vLista.size()>0){
                for(int i=0;i<vLista.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(vLista, i, 5).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                        int vFila = -1;
                        for(int j=0;j< tableModelListaPrecioProductos.data.size();j++){ 
                            if(FarmaUtility.getValueFieldArrayList(vLista, i, 0).trim().equalsIgnoreCase(FarmaUtility.getValueFieldArrayList( tableModelListaPrecioProductos.data, j, 1))){
                                    vFila=j;
                                    break;
                                    }
                            }

                        VariablesModuloVentas.vCod_Prod = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        
                        ArrayList myArray = new ArrayList();
                        DBModuloVenta.obtieneInfoProductoVta(myArray, VariablesModuloVentas.vCod_Prod);
                        VariablesModuloVentas.vVal_Frac = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
                        VariablesModuloVentas.vCod_Prod = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        VariablesModuloVentas.vDesc_Prod = 
                                ((String)(myJTable.getValueAt(vFila, 2))).trim();
                        VariablesModuloVentas.vNom_Lab = 
                                ((String)(myJTable.getValueAt(vFila, 4))).trim();
                        VariablesModuloVentas.vPorc_Igv_Prod = 
                                ((String)(myJTable.getValueAt(vFila, 11))).trim();
                        VariablesModuloVentas.vCant_Ingresada_Temp = "0";
                        VariablesModuloVentas.vNumeroARecargar = "";
                        VariablesModuloVentas.vVal_Prec_Lista_Tmp = "";
                        VariablesModuloVentas.vIndProdVirtual = FarmaConstants.INDICADOR_N;
                        VariablesModuloVentas.vTipoProductoVirtual = "";
                        VariablesModuloVentas.vVal_Prec_Pub = 
                                ((String)(myJTable.getValueAt(vFila, 6))).trim();
                        VariablesModuloVentas.vIndOrigenProdVta = 
                                FarmaUtility.getValueFieldJTable(myJTable, vFila, 
                                                                 COL_ORIG_PROD);
                        VariablesModuloVentas.vPorc_Dcto_2 = "0";
                        VariablesModuloVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
                        VariablesModuloVentas.vCantxDia = "";
                        VariablesModuloVentas.vCantxDias = "";


                        VariablesModuloVentas.vCod_Prod = FarmaUtility.getValueFieldArrayList(vLista, i, 0);
                           DlgIngresoCantidad dlgIngCantidad = new DlgIngresoCantidad(myParentFrame, "", true,ConstantsPtoVenta.TIPO_VENTA);
                        VariablesModuloVentas.vIngresaCant_ResumenPed = false;
                           dlgIngCantidad.setCantInic(Integer.parseInt(FarmaUtility.getValueFieldArrayList(vLista, i, 1)));
                           
                           
                           // fracc
                           // unidad
                           // precio venta UNITARIO
                           VariablesModuloVentas.vCant_Ingresada = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
                           VariablesModuloVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 4);
                           VariablesModuloVentas.vVal_Frac = FarmaUtility.getValueFieldArrayList(vLista, i, 2);
                           VariablesModuloVentas.vUnid_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 3);
                           
                           //dlgIngCantidad.setSize(10, 10);
                           dlgIngCantidad.abreOpera();
                        // fracc
                        // unidad
                        // precio venta UNITARIO
                        VariablesModuloVentas.vCant_Ingresada = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
                        VariablesModuloVentas.vVal_Prec_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 4);
                        VariablesModuloVentas.vVal_Frac = FarmaUtility.getValueFieldArrayList(vLista, i, 2);
                        VariablesModuloVentas.vUnid_Vta = FarmaUtility.getValueFieldArrayList(vLista, i, 3);
                        
                           ////////////selecciona product /////////
                           Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));
                           double auxCantIng = 
                               FarmaUtility.getDecimalNumber(VariablesModuloVentas.vCant_Ingresada);
                           double auxPrecVta = 
                               FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta);
                        VariablesModuloVentas.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
                        VariablesModuloVentas.secRespStk = ""; //ASOSA, 26.08.2010
                           FarmaUtility.setCheckValue(myJTable, false,vFila);
                           Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
                        UtilityModuloVenta.operaProductoSeleccionadoEnArrayList_02(valor, VariablesModuloVentas.secRespStk); //ASOSA, 01.07.2010
                           pintaCheckOtroJTable(myJTable, valorTmp);
                           colocaTotalesPedido();
                           //FarmaUtility.showMessage(this,"VariablesVentas.vCod_Prod " + VariablesModuloVentas.vCod_Prod,txtProducto);

                        VariablesModuloVentas.vIndDireccionarResumenPed = false;
                           FarmaVariables.vAceptar = false;   
                    }
             }
                VariablesModuloVentas.vIndDireccionarResumenPed = true;
                VariablesModuloVentas.vIndF11 = true;
             aceptaOperacion();
                
         }
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        VariablesModuloVentas.vIndDireccionarResumenPed = true;
        VariablesModuloVentas.vIndF11 = true;
        aceptaOperacion();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        /*vLost = false;
        DlgRptPorCliente dlgReceta = new DlgRptPorCliente(myParentFrame,"",true);
        dlgReceta.setVisible(true);
        FarmaUtility.moveFocus(txtProducto);
        vLost = true;*/
        clonarListadoProductos();
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        muestraProductoPromocion("N");
    }
    
    public void muestraAjusteKardex(){
        /*
        new FarmaColumnData( "Sel", 30, JLabel.CENTER ),
        new FarmaColumnData( "Código", 60, JLabel.LEFT ),
        new FarmaColumnData( "Descripción", 330, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 135, JLabel.LEFT ),
        new FarmaColumnData( "Categoría", 150, JLabel.LEFT ),
        new FarmaColumnData( "Stock", 45, JLabel.RIGHT ),
        
         * */
        VariablesInventario.vCodProd         = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);
        VariablesInventario.vDescProd        = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 2);
        
        
        VariablesInventario.vNomLab          = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 4);
        
        VariablesInventario.vCant            = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 5);
        VariablesInventario.vCantFrac        = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 5);
        VariablesInventario.vFrac            = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 9);
        VariablesInventario.vIndProdVirtual  = "N";//FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);
        VariablesInventario.vStock           = Integer.parseInt(FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 5).replace(",", "")) ;
              // = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 9);
        
        ArrayList myArray = new ArrayList();
        try {
            DBModuloVenta.obtieneInfoProductoVta(myArray, VariablesInventario.vCodProd);
            VariablesInventario.vCFraccion = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesInventario.vDescUnidPresent =((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesInventario.vDescUnidFrac    = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            
        } catch (SQLException e) {
            VariablesInventario.vCFraccion  = "1";
            VariablesInventario.vDescUnidPresent = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 3); // mal
            VariablesInventario.vDescUnidFrac    = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 3);// ok
            
        }
        
        
        DlgAjusteKardex dlgAjusteKardex = new DlgAjusteKardex(myParentFrame, "", true);
        dlgAjusteKardex.setVisible(true);
        
        UpdateFilaProd();
        
        //FarmaUtility.moveFocus(txtProducto);
    
    }
    
    public void muestraModificaProducto(){
    
        if(tblProductos.getSelectedRow()>=0){
            String pCodProd = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);    
            String pAccion = "U";
            
            DlgMantProducto dlgMantProducto = new DlgMantProducto(myParentFrame, "", true, pAccion,pCodProd);
            dlgMantProducto.setVisible(true);
            
            if(FarmaVariables.vAceptar){
                cargaProductosDB();
            }
            
        }
        UpdateFilaProd();
        
       // FarmaUtility.moveFocus(txtProducto);
}
  
    public void inactivarProducto(){
    int pos =  tblProductos.getSelectedRow();
        if(tblProductos.getSelectedRow()>=0){
            String pCodProd = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);


            try {
                DBMantenimiento.inactivaProducto(pCodProd);
                FarmaUtility.aceptarTransaccion();
                
                tableModelListaPrecioProductos.data.remove(pos);
                
                //tblProductos.remove (pos);
                
                for(int i=0;i<VariablesModuloVentas.tableModelListaGlobalProductos.data.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.tableModelListaGlobalProductos.data,i,1).trim()
                        .equalsIgnoreCase(pCodProd)) {
                        VariablesModuloVentas.tableModelListaGlobalProductos.data.remove(i);
                    }
                }
                
                tblProductos.repaint();
                
                FarmaUtility.showMessage(this, "Se inactivo el producto",txtProducto);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al inactivar el producto\n"+
                                               e.getMessage(),txtProducto);
            }
        }
        UpdateFilaProd();
        
       // FarmaUtility.moveFocus(txtProducto);
    }
      
    public void nuevoProducto(){
        
            String pCodProd = "N";
            String pAccion = "I";
            
            DlgMantProducto dlgMantProducto = new DlgMantProducto(myParentFrame, "", true, pAccion,pCodProd);
            dlgMantProducto.setVisible(true);
            
        if(FarmaVariables.vAceptar){
            cargaProductosDB();
        }
            
    }
        
        
    private void UpdateFilaProd() {

       // muestraNombreLab(4, lblDescLab_Prod);
        muestraProductoInafectoIgv(11, lblProdIgv);
        muestraProductoRefrigerado(15, lblProdRefrig);
        muestraProductoPromocion(17, lblProdProm);
      //  muestraIndTipoProd(16, lblIndTipoProd);
        // JCORTEZ 08.04.2008
        muestraProductoEncarte(COL_IND_ENCARTE, lblProdEncarte);
        muestraInfoProd();
        muestraProductoCongelado(lblProdCong);
    }

    private void tblProductos_mouseClicked(MouseEvent e) {

        int i= tblProductos.getSelectedRow();
        //txtProducto.setText(FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,i, 2));
        if (e.isMetaDown() ){
            // click derecho
        //ascascascas
        popup.show(e.getComponent(),
                   e.getX(), e.getY());
            //muestraAjusteKardex();
            //muestraModificaProducto
        }
        else{
        if (e.getClickCount() == 2 && !e.isConsumed()) {
             e.consume();
             int vFoco = tblProductos.getSelectedRow();
             //txtFiltroCategoria.setText(""+vFoco);
            if (tblProductos.getSelectedRow() >= 0)
            {  procesoEnter(new KeyEvent( txtProducto, KeyEvent.KEY_PRESSED, 
                                0,                          // When timeStamp
                                0,                          // Modifier
                                KeyEvent.VK_ENTER,      // Key Code
                                KeyEvent.CHAR_UNDEFINED ));
                  FarmaUtility.moveFocus(txtProducto);
             }
             //System.out.println("- "+vFoco);
        }
        }
    }

    private void jButton3_actionPerformed(ActionEvent e) {
        seleccionarPromociones();
    }

    private void seleccionarPromociones() {
        
        DlgSeleccionCampana dlgSelCamp = new DlgSeleccionCampana(myParentFrame, "", true);        
        dlgSelCamp.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        } 
        
        FarmaUtility.moveFocus(txtProducto);
    }

    private void txtProducto_focusLost(FocusEvent e) {
        /*if(obligaFoco)
            FarmaUtility.moveFocus(txtProducto);*/
    }

    private void cambiaCotiza() {
        //FarmaUtility.showMessage(this, "cambiando",txtProducto);
        if(VariablesModuloVentas.is_cotizacion){
            VariablesModuloVentas.is_cotizacion = false;
            jContentPane.setBackground(Color.WHITE);
        }else{
            VariablesModuloVentas.is_cotizacion = true;
            jContentPane.setBackground(new Color(255,76,76));
        }
        this.repaint();
    }
    

    private void reporte_por_cliente() {
        vLost = false;
        String pCodProd = "";
        
        VariablesCliente.vDni = "";
        VariablesCliente.vRuc = "";
        
        if(tblProductos.getSelectedRow()>=0){
            pCodProd = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);    
            
            if(pCodProd.trim().length()==6){
                DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (myParentFrame,"",true);
                VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;
                dlgBuscaClienteJuridico.setVisible(true);   
                
                
                if(VariablesCliente.vDni.trim().length()>0||VariablesCliente.vRuc.trim().length()>0){
                    // busqueda por producto
                    DlgRptPorClienteProducto dlgReceta = new DlgRptPorClienteProducto(myParentFrame,"",true);
                    dlgReceta.setPCodProd(pCodProd);
                    dlgReceta.setPDNI(VariablesCliente.vDni);
                    dlgReceta.setPRUC(VariablesCliente.vRuc);
                    dlgReceta.setVisible(true);
                }
                
            }
        }
        /*DlgRptPorClienteProducto dlgReceta = new DlgRptPorClienteProducto(myParentFrame,"",true);
        dlgReceta.setPCodProd(pCodProd);
        dlgReceta.setVisible(true);*/
        
        
        
        
        FarmaUtility.moveFocus(txtProducto);
        vLost = true;
    }
    
    private void muestraProductoPromocion(String pCodigoBarra){
        //vEjecutaAccionTeclaListado = false;
        int vFila = myJTable.getSelectedRow();
        Boolean valor = (Boolean)(myJTable.getValueAt(vFila, 0));
        String indProm = (String)(myJTable.getValueAt(vFila, 17));

        if (myJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS))
            VariablesModuloVentas.vCodProdFiltro =
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        else
            VariablesModuloVentas.vCodProdFiltro = "";

        if (indProm.equalsIgnoreCase("S")) { //if(!valor.booleanValue())
            muestraPromocionProd(VariablesModuloVentas.vCodProdFiltro,pCodigoBarra);
            //else
            //  FarmaUtility.showMessage(this,"El Producto está en una Promoción ya seleccionada",txtProducto);
        } else
            muestraPromocionProd(VariablesModuloVentas.vCodProdFiltro,pCodigoBarra);
    }
    
    public void pintaProductosSinStock(){
        
        if (tblProductos.getRowCount() > 0) {
            /*FarmaUtility.ordenar(tblSolicitud, tblModelSolicitud, Constants.COL_NUMERO_DOCUMENTO,
                                 FarmaConstants.ORDEN_ASCENDENTE);*/
            
            int pFila = tblProductos.getSelectedRow();
            
            
            int cols = tblProductos.getColumnCount();
            for (int i = 1; i < cols; i++) {
                tblProductos.getColumnModel().getColumn(i).setCellRenderer(new CustomRenderer());
            }
            
            
            /*tblProductos.clearSelection();
            tblProductos.setRowSelectionInterval(pFila, pFila);*/
            
            //tblProductos.getSelectionModel().setSelectionInterval (0, 0);
            //ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,
            
            
            /*ArrayList rowsWithOtherColor = new ArrayList();
            for(int i = 0; i < tableModelListaPrecioProductos.data.size(); i++){
              if ( (Integer.parseInt(((ArrayList)tableModelListaPrecioProductos.data.get(i)).get(5).toString().trim())) <= 0)
              { //cantguias 8 es 0
                rowsWithOtherColor.add(String.valueOf(i));
              }
            }

            FarmaUtility.initSelectListCleanColumns(tblProductos, tableModelListaPrecioProductos,
               ConstantsModuloVenta.columnsListaProductos,rowsWithOtherColor,Color.white,Color.red,false);*/
            
            
          
        }
        tblProductos.getTableHeader().setReorderingAllowed(false);
        tblProductos.getTableHeader().setResizingAllowed(false);
        
        //tblProductos.getSelectionModel().setSelectionInterval (0, 0);

    }

    private void btnCliente_actionPerformed(ActionEvent e) {
        obligaFoco = false;
        
        //UtilityModuloVenta.seleccionCliente(myParentFrame);
        UtilityHHVenta.ingresoDatosPedido(myParentFrame);
        FarmaUtility.moveFocus(txtProducto);
        
        obligaFoco = true;
        muestraTituloCliente();
        FarmaUtility.moveFocus(txtProducto);
    }

    public String getCodProdRecetaSelect() {
        return null;
    }

    private void txtEspecialidad_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblLaboratorio,txtEspecialidad, 1);
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(tblLaboratorio.getSelectedRow()>=0)
                filtroProdEspecialidad();
        }
    }

    private void filtroProdEspecialidad() {
        int pos= tblLaboratorio.getSelectedRow();

        String condicion = FarmaUtility.getValueFieldArrayList(tblModeLaboratorio.data,
                                                               pos, 0);
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelListaPrecioProductos.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(24).toString().toUpperCase().trim();
                if(descProd.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModelListaPrecioProductos.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelListaPrecioProductos.fireTableDataChanged();
            setJTable(tblProductos);
            //iniciaProceso(false);
            tblModelListaSustitutos.clearTable();
            
            
            if(tblProductos.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                if(tblProductos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" filas para el filtro aplicado");
            }
            
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_PedidoVenta, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                          0);

            
        }
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblProductos, 0, 0);
    }

    private void tblLaboratorio_mouseClicked(MouseEvent e) {


        int i= tblProductos.getSelectedRow();
        //txtProducto.setText(FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,i, 2));
        if (e.isMetaDown() ){
            // click derecho
        //ascascascas
        popup.show(e.getComponent(),
                   e.getX(), e.getY());
            //muestraAjusteKardex();
            //muestraModificaProducto
        }
        else{
        if (e.getClickCount() == 2 && !e.isConsumed()) {
             e.consume();
             filtroProdEspecialidad();
            }
        }
    }

    private void txtEspecialidad_keyReleased(KeyEvent e) {
        if(tblLaboratorio.getRowCount() >= 0 && 
            tblModeLaboratorio.getRowCount() > 0) {
            if (FarmaGridUtils.buscarDescripcion(e, tblLaboratorio, txtEspecialidad, 1))
                                                     System.out.println("muestra dato");
                                                 }
    }


    class CustomRenderer extends DefaultTableCellRenderer 
    {
    
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         
            if(tableModelListaPrecioProductos.data.size()>0){
            int  intValor=Integer.parseInt(tblProductos.getValueAt(row, 5).toString().trim());
            
            Color prioridad1 = new Color(249,145,127) ;//ALTA
            //Color prioridad2 = new Color(127,249,154);//MEDIA
            //Color prioridad3 = new Color(249,244,139);//BAJA

            //cellComponent.setBackground(Color.WHITE);
            
            if(isSelected){
               //
               setBackground(new Color(35,57,145));  
               setForeground(Color.WHITE);
            }
            else{
                if(intValor==0){
                   setBackground(prioridad1);
                }
                else{
                    setBackground(Color.WHITE);  
                    setForeground(Color.black);
                }    
            }
            
           }
            
           
            /*
            if (row >= table.getRowCount())
                row = table.getRowCount() - 1;
            Rectangle rect = table.getCellRect(row, column, true);
            table.scrollRectToVisible(rect);
            table.clearSelection();
            table.setRowSelectionInterval(row, row);*/
            
            return cellComponent;
            
        
        }
    }   
    
    
    public void muestraTituloCliente(){
        if((VariablesCliente.vRuc.trim().length()+VariablesCliente.vDni.trim().length())>0){
        
            if(VariablesCliente.vRuc.trim().length()>0){
                this.setTitle("Cliente Seleccionado : "+VariablesCliente.vRuc+" - "+VariablesCliente.vRazonSocial );    
            }
            else{
                this.setTitle("Cliente Seleccionado : "+VariablesCliente.vDni+" - "+VariablesCliente.vNombre+
                              " "+VariablesCliente.vApellidoPat+" " +
                    VariablesCliente.vApellidoMat);    
            }
            
        }
        else{
            this.setTitle("Venta sin cliente Seleccionado");
        }
    }
    
    
    public void cargaProductosDB(){
        //FarmaUtility.showMessage(this, ".. se puede entrar", null);  
        VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

        try {
            DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
        } catch(Exception ef) {
                //e.printStackTrace();
        }
        
        clonarListadoProductos();
    }
}
