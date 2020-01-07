package venta.modulo_venta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;

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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.DlgLogin;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLengthText;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.text.DecimalFormat;

import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.reference.ConstantsPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import venta.modulo_venta.reference.UtilityModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.adm.productos.reference.ConstantsMaestrosProductos;

import venta.adm.productos.reference.DBMaestrosProductos;

import venta.modulo_venta.reference.VariablesModuloReceta;

import venta.receta.reference.DBReceta;

import venta.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoCantidad.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      28.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresoCantidad extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidad.class);

    private int cantInic = 0;
    // Bjct, 27-DIC-12, si precio es cero no Vender
    private boolean vbPrecProdOk = true;
    // Bjct, 27-DIC-12


    /** Objeto Frame de la Aplicación */
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    JPanel pnlStock = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    JLabel lblUnidades = new JLabel();
    JLabel lblStock = new JLabel();
    JLabel lblStockTexto = new JLabel();
    JPanel pnlDetalleProducto = new JPanel();
    XYLayout xYLayout1 = new XYLayout();
    JTextField txtPrecioVenta = new JTextField();
    JLabel lblUnidadT = new JLabel();
    JLabel lblDescripcionT = new JLabel();
    JLabel lblCodigoT = new JLabel();
    JLabel lblLaboratorio = new JLabel();
    JLabel lblDcto = new JLabel();
    JLabel lblLaboratorioT = new JLabel();
    JLabel lblDscto = new JLabel();
    public JTextField txtCantidad = new JTextField();
    JLabel lblUnidad = new JLabel();
    JLabel lblDescripcion = new JLabel();
    JLabel lblCodigo = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnPrecioVta = new JButtonLabel();
    private JLabelOrange lblPrecVtaConv = new JLabelOrange();
    private JLabelOrange lblProdCupon = new JLabelOrange();
    private JLabelOrange lblPrecioProdCamp = new JLabelOrange();

    private double pPrecioFidelizacion = 0.0;
    private JLabel jLabel1 = new JLabel();
    private JLabel lblMensajeCampaña = new JLabel();
    private JTextField txtPrecioVentaRedondeado = new JTextField();
    private JTextField txtSubTotal = new JTextField();
    private JNumericField txtPrecioFinal = new JNumericField();
    private JButtonLabel jLabel2 = new JButtonLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton jButton1 = new JButton();

    private boolean vIndRecetaAutomatico;
    private int vTipo = 0;
    int CantidadInicial=0;
    private String vNumPedRegulariza="";
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtDescAuxiliar = new JTextArea();
    private JLabel jLabel4 = new JLabel();
    private JScrollPane srcFracciones = new JScrollPane();
    private JTable tblFracciones = new JTable();
    FarmaTableModel tbmFracciones,tbmLote;
    private JLabel lblAlertaPrecio = new JLabel();
    
    private double pPrecioBase = 0.0;
    private JScrollPane srpLote = new JScrollPane();
    private JLabel jLabel5 = new JLabel();
    private JTable tblLote = new JTable();
    private JLabel jLabel6 = new JLabel();
    private JLabel lblCosto = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel lblMargen = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel lblUtilidad = new JLabel();
    private JPanel pnlUtilidad = new JPanel();
    private JLabel jLabel9 = new JLabel();
    private JTextField txtLote = new JTextField();
    private JButton btnCambioPrecio = new JButton();


    private boolean vAutorizaCambioPrecio = false;
    
    private boolean isIngresoExterno = false;
    private JButton btnHistorico = new JButton();

    private boolean vPermiteVerPrecioMinimo = false;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    /**
     *Constructor
     */
    
    public DlgIngresoCantidad() {
        this(null, "", false,0);
    }
    
    
    public DlgIngresoCantidad(Frame parent, String title, boolean modal,int vTipo) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.vTipo = vTipo;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgIngresoCantidad(Frame parent, String title, boolean modal,boolean vIndRecetaAutomatico) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.vIndRecetaAutomatico = vIndRecetaAutomatico; 
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    /**
     *Constructor
     *@param parent Objeto Frame de la Aplicación.
     *@param title Título de la Ventana.
     *@param modal Tipo de Ventana.
     */
    public DlgIngresoCantidad(Frame parent, String title, boolean modal,int vTipo,String vNumPedRegulariza) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.vTipo = vTipo;
        this.vNumPedRegulariza=vNumPedRegulariza;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    /**
     *Implementa la Ventana con todos sus Objetos
     */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(955, 495));
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(360, 331));
        jContentPane.setBackground(Color.white);
        pnlStock.setBounds(new Rectangle(15, 10, 920, 40));
        pnlStock.setFont(new Font("SansSerif", 0, 11));
        pnlStock.setBackground(new Color(0, 114, 169));
        pnlStock.setLayout(xYLayout2);
        pnlStock.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlStock.setForeground(Color.white);
        lblUnidades.setText("unidades");
        lblUnidades.setFont(new Font("SansSerif", 1, 14));
        lblUnidades.setForeground(Color.white);
        lblStock.setText("10");
        lblStock.setFont(new Font("SansSerif", 1, 15));
        lblStock.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStock.setForeground(Color.white);
        lblStockTexto.setText("Stock del Producto al");
        lblStockTexto.setFont(new Font("SansSerif", 0, 12));
        lblStockTexto.setForeground(Color.white);
        pnlDetalleProducto.setBounds(new Rectangle(15, 55, 920, 390));
        pnlDetalleProducto.setLayout(xYLayout1);
        pnlDetalleProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDetalleProducto.setFont(new Font("SansSerif", 0, 11));
        pnlDetalleProducto.setBackground(Color.white);
        txtPrecioVenta.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioVenta.setFont(new Font("SansSerif", 1, 11));
        txtPrecioVenta.setEnabled(false);
        txtPrecioVenta.setText("13.20");
        //se cambio para que muestre precio 01.09.2015
        txtPrecioVenta.setVisible(false);
        txtPrecioVenta.setText("0");
        txtPrecioVenta.setSize(new Dimension(481, 354));
        txtPrecioVenta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioVenta_keyPressed(e);
            }
        });
        lblUnidadT.setText("Unidad");
        lblUnidadT.setFont(new Font("SansSerif", 1, 11));
        lblDescripcionT.setText("Descripcion");
        lblDescripcionT.setFont(new Font("SansSerif", 1, 11));
        lblCodigoT.setText("Codigo");
        lblCodigoT.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setText("COLLIERE S.A.");
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblDcto.setText("10.00");
        lblDcto.setHorizontalAlignment(SwingConstants.LEFT);
        lblDcto.setFont(new Font("SansSerif", 0, 11));
        lblDcto.setBounds(new Rectangle(970, 180, 10, 20));
        lblLaboratorioT.setText("Marca :");
        lblLaboratorioT.setFont(new Font("SansSerif", 1, 11));
        lblDscto.setText("% Dcto. :");
        lblDscto.setFont(new Font("SansSerif", 1, 11));
        lblDscto.setBounds(new Rectangle(975, 180, 20, 20));
        txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
        txtCantidad.setDocument(new FarmaLengthText(6));
        txtCantidad.setText("0");
        txtCantidad.setFont(new Font("SansSerif", 1, 11));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtCantidad_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }

                public void keyReleased(KeyEvent e) {
                    txtCantidad_keyReleased(e);
                }
            });
        txtCantidad.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtCantidad_focusLost(e);
                }
            });
        lblUnidad.setText(" ");
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setText(" ");
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setText(" ");
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblEsc.setText("[ ESC ] Cerrar");
        btnCantidad.setText("Cantidad :");
        btnCantidad.setForeground(Color.black);
        btnCantidad.setMnemonic('c');
        btnCantidad.setFont(new Font("Tahoma", 1, 12));
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        btnPrecioVta.setText("Precio Costo en Sistema : S/.");
        btnPrecioVta.setFont(new Font("SansSerif", 1, 15));
        btnPrecioVta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPrecioVta_actionPerformed(e);
            }
        });
        lblPrecVtaConv.setForeground(Color.black);
        lblProdCupon.setBounds(new Rectangle(255, 480, 285, 15));
        lblPrecioProdCamp.setForeground(Color.red);
        lblPrecioProdCamp.setFont(new Font("SansSerif", 1, 15));
        lblPrecioProdCamp.setBounds(new Rectangle(825, 95, 305, 30));
        jLabel1.setText("jLabel1");
        lblMensajeCampaña.setVisible(false);
        lblMensajeCampaña.setForeground(Color.red);
        lblMensajeCampaña.setFont(new Font("Dialog", 1, 13));
        lblMensajeCampaña.setText("     Producto se encuentra en campaña  \" Acumula tu Compra y Gana !\"");
        txtPrecioVentaRedondeado.setHorizontalAlignment(JTextField.RIGHT);
        txtPrecioVentaRedondeado.setFont(new Font("SansSerif", 1, 11));
        txtPrecioVentaRedondeado.setEnabled(false);
        txtPrecioVentaRedondeado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioVenta_keyPressed(e);
            }
        });
        txtPrecioVentaRedondeado.setVisible(true);
        txtSubTotal.setFont(new Font("Tahoma", 0, 15));
        txtSubTotal.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtSubTotal_keyReleased(e);
                }
            });
        txtPrecioFinal.setFont(new Font("Tahoma", 0, 13));

        txtPrecioFinal.setMaxLength(10); //Set maximum length             
        txtPrecioFinal.setPrecision(2); //Set precision (1 in your case)              
        txtPrecioFinal.setAllowNegative(false); //Set false to disable negatives
        
        txtPrecioFinal.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtPrecioFinal_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtPrecioFinal_keyPressed(e);
                }
            });
        jLabel2.setText("PU S/ :");
        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setForeground(SystemColor.windowText);
        jLabel2.setMnemonic('p');
        jLabel2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jLabel2_actionPerformed(e);
                }
            });
        jLabel3.setText("Sub Total : ");
        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jButton1.setText("F11 - Aceptar");
        jButton1.setFont(new Font("Tahoma", 1, 11));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(965, 360, 30, 75));
        pnlStock.add(lblUnidades, new XYConstraints(230, 10, 75, 20));
        pnlStock.add(lblStock, new XYConstraints(140, 5, 80, 30));
        pnlStock.add(lblStockTexto, new XYConstraints(9, 9, 125, 20));
        pnlStock.add(btnPrecioVta, new XYConstraints(314, 9, 340, 20));
        srcFracciones.getViewport().add(tblFracciones, null);
        srpLote.getViewport().add(tblLote, null);
        pnlUtilidad.add(lblUtilidad, null);
        pnlUtilidad.add(jLabel8, null);
        pnlUtilidad.add(lblMargen, null);
        pnlUtilidad.add(jLabel7, null);
        pnlUtilidad.add(lblCosto, null);
        pnlUtilidad.add(jLabel6, null);
        pnlDetalleProducto.add(btnHistorico, new XYConstraints(754, 39, 150, 20));
        pnlDetalleProducto.add(btnCambioPrecio, new XYConstraints(784, 9, 110, 20));
        pnlDetalleProducto.add(txtLote, new XYConstraints(364, 249, 140, 20));
        pnlDetalleProducto.add(jLabel9, new XYConstraints(309, 254, 50, 15));
        pnlDetalleProducto.add(pnlUtilidad, new XYConstraints(24, 224, 260, 110));
        pnlDetalleProducto.add(jLabel5, new XYConstraints(564, 39, 195, 15));
        pnlDetalleProducto.add(srpLote, new XYConstraints(564, 69, 330, 295));
        pnlDetalleProducto.add(lblAlertaPrecio, new XYConstraints(349, 154, 215, 25));
        pnlDetalleProducto.add(srcFracciones, new XYConstraints(24, 79, 300, 130));


        //Agregado Por FRAMIREZ 11.01.2012 oculta el precio de una venta normal
        pnlDetalleProducto.add(lblPrecVtaConv, new XYConstraints(99, 184, 80, 15));
        pnlDetalleProducto.add(btnCantidad, new XYConstraints(344, 84, 80, 20));
        pnlDetalleProducto.add(lblUnidadT, new XYConstraints(14, 39, 50, 20));
        pnlDetalleProducto.add(lblDescripcionT, new XYConstraints(204, 4, 70, 20));
        pnlDetalleProducto.add(lblCodigoT, new XYConstraints(19, 4, 55, 20));
        pnlDetalleProducto.add(lblLaboratorio, new XYConstraints(369, 39, 280, 20));
        pnlDetalleProducto.add(lblLaboratorioT, new XYConstraints(314, 39, 80, 20));
        pnlDetalleProducto.add(txtCantidad, new XYConstraints(439, 84, 105, 25));
        pnlDetalleProducto.add(lblUnidad, new XYConstraints(69, 39, 230, 20));
        pnlDetalleProducto.add(lblDescripcion, new XYConstraints(279, 4, 385, 20));
        pnlDetalleProducto.add(lblCodigo, new XYConstraints(69, 4, 55, 20));
        pnlDetalleProducto.add(lblMensajeCampaña, new XYConstraints(-1, 224, 500, 25));


        pnlDetalleProducto.add(jLabel2, new XYConstraints(369, 119, 55, 25));
        pnlDetalleProducto.add(txtPrecioFinal, new XYConstraints(439, 124, 105, 25));
        pnlDetalleProducto.add(jLabel3, new XYConstraints(359, 179, 80, 20));
        pnlDetalleProducto.add(txtSubTotal, new XYConstraints(439, 179, 105, 30));
        pnlDetalleProducto.add(jButton1, new XYConstraints(9, 349, 145, 25));
        pnlDetalleProducto.add(lblEsc, new XYConstraints(434, 344, 115, 30));
        btnPrecioVta.setVisible(true);


        log.debug("¿VariablesConvenioBTLMF.vCodConvenio?" + VariablesConvenioBTLMF.vCodConvenio);


        //Agregado Por FRAMIREZ 11.01.2012 oculta el precio de una venta normal
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            //if(VariablesVentas.vEstadoProdConvenio.equals("I"))

            txtPrecioVentaRedondeado.setVisible(false);
            txtPrecioVenta.setVisible(false);
            //T_lblPrecVtaConv.setVisible(true);

        } else {
            //T_lblPrecVtaConv.setVisible(false);
            //01.09.2015
            pnlDetalleProducto.add(txtPrecioVentaRedondeado, new XYConstraints(109, 159, 60, 30));
            //pnlDetalleProducto.add(txtPrecioVenta, new XYConstraints(109, 134, 60, 20));
        }


        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        txtDescAuxiliar.setLineWrap(true);
        txtDescAuxiliar.setBounds(new Rectangle(980, 450, 170, 75));
        jLabel4.setText("Descripci\u00f3n Auxiliar :");
        jLabel4.setBounds(new Rectangle(980, 290, 235, 30));
        jLabel4.setFont(new Font("SansSerif", 1, 12));
        tblFracciones.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblFracciones_mouseClicked(e);
                }
            });
        lblAlertaPrecio.setForeground(new Color(231, 0, 0));
        lblAlertaPrecio.setFont(new Font("SansSerif", 3, 12));
        jLabel5.setText("Seleccione Lote (*) - Opcional");
        jLabel5.setFont(new Font("Tahoma", 1, 12));
        tblLote.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblLote_mouseClicked(e);
                }
            });
        jLabel6.setText("Costo S/ :");
        jLabel6.setFont(new Font("SansSerif", 1, 15));
        jLabel6.setBounds(new Rectangle(10, 10, 75, 25));
        lblCosto.setText("999999900.00");
        lblCosto.setFont(new Font("SansSerif", 1, 15));
        lblCosto.setBounds(new Rectangle(85, 10, 170, 25));
        jLabel7.setText("Margen S/ :");
        jLabel7.setFont(new Font("SansSerif", 1, 15));
        jLabel7.setBounds(new Rectangle(10, 45, 90, 25));
        lblMargen.setText("99999990.00");
        lblMargen.setFont(new Font("SansSerif", 1, 15));
        lblMargen.setBounds(new Rectangle(105, 45, 150, 25));
        jLabel8.setText("Utilidad % :");
        jLabel8.setFont(new Font("SansSerif", 1, 15));
        jLabel8.setBounds(new Rectangle(10, 85, 85, 20));
        lblUtilidad.setText("15.10 %");
        lblUtilidad.setFont(new Font("SansSerif", 1, 15));
        lblUtilidad.setBounds(new Rectangle(105, 85, 125, 20));
        pnlUtilidad.setLayout(null);
        pnlUtilidad.setBackground(SystemColor.window);
        pnlUtilidad.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jLabel9.setText("Lote * :");
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel9.setForeground(new Color(0, 107, 165));
        btnCambioPrecio.setText("Descuento");
        btnCambioPrecio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        btnHistorico.setText("Hist\u00f3rico Precio Venta");
        btnHistorico.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnHistorico_actionPerformed(e);
                }
            });
        jContentPane.add(jLabel4, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlStock, null);
        jContentPane.add(pnlDetalleProducto, null);
        jContentPane.add(lblDcto, null);
        jContentPane.add(lblDscto, null);
        jContentPane.add(lblPrecioProdCamp, null);
        jContentPane.add(lblProdCupon, null);
        jContentPane.add(txtDescAuxiliar, null);


        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        btnCambioPrecio.setVisible(false);
        tbmFracciones =
                new FarmaTableModel(ConstantsMaestrosProductos.columnsListaFracciones, ConstantsMaestrosProductos.defaultValuesListaFracciones,
                                    0);
        FarmaUtility.initSimpleList(tblFracciones, tbmFracciones, ConstantsMaestrosProductos.columnsListaFracciones);
        

        tbmLote =
                new FarmaTableModel(ConstantsMaestrosProductos.columnsListaProdLote, 
                                    ConstantsMaestrosProductos.defaultValuesListaProdLote,
                                    0);
        FarmaUtility.initSimpleList(tblLote, tbmLote, 
                                    ConstantsMaestrosProductos.columnsListaProdLote);
        
        vPermiteVerPrecioMinimo = permiteVerPrecioMinimo() ;
        
        if(validaLoginCambioPrecio()){
            btnCambioPrecio.setVisible(true);
            txtPrecioFinal.setEnabled(false);
            vAutorizaCambioPrecio = false;
           
        }
        else{
            txtPrecioFinal.setEnabled(true);
            btnCambioPrecio.setVisible(false);
            vAutorizaCambioPrecio = true;
            if(vPermiteVerPrecioMinimo)
                lblAlertaPrecio.setVisible(true);
            else
                lblAlertaPrecio.setVisible(false);
        }
        
    }

    public void abreOpera() {
        muestraInfoDetalleProd();
        evaluaTipoPedido();
        //JCORTEZ 17.04.08
        lblDscto.setVisible(false);
        lblDcto.setVisible(false);
        muestraMaxProdCupon();
        calculoNuevoPrecio();
        lblMensajeCampaña.setVisible(false);
        aceptaCantidadIngresada();
    }
    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        try {
            //ERIOS 06.06.2008 Solución temporal para evitar la venta sugerida por convenio
            if (VariablesModuloVentas.vEsPedidoConvenio) {
                DBModuloVenta.obtieneInfoDetalleProducto(pArrayList, VariablesModuloVentas.vCod_Prod);
            } else {
                DBModuloVenta.obtieneInfoDetalleProductoVta(pArrayList, VariablesModuloVentas.vCod_Prod);
            }

        } catch (SQLException sql) {
            log.error(null, sql.fillInStackTrace());
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private void cargaListaFracciones() {
        try {
            String pTipoVenta = "";
            if(VariablesModuloVentas.is_cotizacion)
                pTipoVenta = "C";
            else
                pTipoVenta = "V";
            
            DBMaestrosProductos.getListaFracciones(tbmFracciones,VariablesModuloVentas.vCod_Prod,pTipoVenta);
            if (tblFracciones.getRowCount() > 0) {
                FarmaGridUtils.showCell(tblFracciones, 0, 0);
            }
            log.debug("se cargo la lista de prods");
        } catch (SQLException sql) {
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), tblFracciones);
        }
    }

    private void muestraInfoDetalleProd() {
        
        // CARGA FRACCIONAMIENTO Y ETC
        cargaListaFracciones();
        // carga lote producto
        cargaListaLoteProducto();
        //  
        
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesModuloVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesModuloVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            if ((!VariablesModuloVentas.vEsPedidoDelivery && !VariablesModuloVentas.vEsPedidoInstitucional) ||
                !VariablesModuloVentas.vIngresaCant_ResumenPed) {
                //JCORTEZ 11/04/08 no se actualiza el precio y descuento si es producto  oferta
                //if(!VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)||!VariablesVentas.vEsProdOferta)

                // Segun gerencia se debe seguir la misma logica para todos los productos.
                if (VariablesModuloVentas.vVentanaListadoProductos) {
                    log.debug("SETEANDO DESCUENTO");
                    VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                    VariablesModuloVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();

                } else {
                    if (UtilityModuloVenta.isAplicoPrecioCampanaCupon(lblCodigo.getText().trim(),
                                                                 FarmaConstants.INDICADOR_S)) {
                        if (!VariablesModuloVentas.vVentanaOferta) {
                            log.debug("SETEANDO DESCUENTO");
                            VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                            VariablesModuloVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
                        }
                    }
                }

                log.info("DlgIngresoCantidad: VariablesVentas.vPorc_Dcto_1 - " + VariablesModuloVentas.vPorc_Dcto_1);
                log.debug("VariablesVentas.vPorc_Dcto_2 : " + VariablesModuloVentas.vPorc_Dcto_2);
            }
            VariablesModuloVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesModuloVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesModuloVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesModuloVentas.vStk_Prod = "0";
            VariablesModuloVentas.vDesc_Acc_Terap = "";
            VariablesModuloVentas.vStk_Prod_Fecha_Actual = "";
            VariablesModuloVentas.vVal_Prec_Vta = "";
            VariablesModuloVentas.vUnid_Vta = "";
            VariablesModuloVentas.vPorc_Dcto_1 = "";
            VariablesModuloVentas.vVal_Prec_Lista = "";
            VariablesModuloVentas.vNom_Lab = "";
            VariablesModuloVentas.vDesc_Prod = "";
            VariablesModuloVentas.vCod_Prod = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }


        lblStock.setText("" + (Integer.parseInt(VariablesModuloVentas.vStk_Prod) + cantInic));
        //lblStock.setText(VariablesVentas.vStk_Prod);
        lblCodigo.setText(VariablesModuloVentas.vCod_Prod);
        lblDescripcion.setText(VariablesModuloVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesModuloVentas.vNom_Lab);
        lblUnidad.setText(VariablesModuloVentas.vUnid_Vta);
        
            txtPrecioVenta.setText(VariablesModuloVentas.vVal_Prec_Vta); //JCHAVEZ 29102009 se cambio txtPrecioVenta por txtPrecioVentaOculto
               
        //////////////////////////////////////////////////////////////////////////////////////////////////////////    
        
        //btnPrecioVta.setText("Precio Venta Sistema : S/.  " + VariablesModuloVentas.vVal_Prec_Vta);
        
        // enrique 20180908
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////            
        txtPrecioVenta.setVisible(false);
        txtPrecioVentaRedondeado.setVisible(false);
        
            
        lblDcto.setText(VariablesModuloVentas.vPorc_Dcto_1);
        txtCantidad.setText("" + cantInic);
        //JCHAVEZ 29102009 inicio
        try {
            //double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));  antes

            // Bjct, 27-DIC-12, setar flag de precio correcto
            /*String vsPrecMinVta=DBVentas.getPrecioMinimoVta();
        if (vsPrecMinVta.equalsIgnoreCase("N")){
            FarmaUtility.showMessage(this,"Error, No se puede Leer el Valor Mínimo de Venta...",txtCantidad);
            return;
            }*/
            double vdPrecOrigonal = FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta);
            double vdPrecMinVta = vdPrecOrigonal; //Double.valueOf(vsPrecMinVta).doubleValue();

            if (vdPrecOrigonal < vdPrecMinVta) {
                vbPrecProdOk = false;
            }
            // Ejct, 27-DIC-12

            double precVtaRedondeadoNum = DBModuloVenta.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta)); //ASOSA, 18.06.2010

            String precVtaRedondeadoStr =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaRedondeadoNum), 3);
            if (!VariablesConvenio.vVal_Prec_Vta_Conv.trim().equalsIgnoreCase("")) {
                log.debug("VariablesConvenio.vVal_Prec_Vta_Conv: " + VariablesConvenio.vVal_Prec_Vta_Conv);
                double precVtaConvRedondeadoNum = DBModuloVenta.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesConvenio.vVal_Prec_Vta_Conv));
                String precVtaConvRedondeadoStr =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaConvRedondeadoNum), 3);
                lblPrecVtaConv.setText(precVtaConvRedondeadoStr); //JCHAVEZ 29102009 se cambio VariablesConvenio.vVal_Prec_Vta_Conv por precVtaConvRedondeadoStr

            }
            this.txtPrecioVentaRedondeado.setText(precVtaRedondeadoStr);
            
            
            if(VariablesModuloVentas.vIsVtaSoat)
                FarmaUtility.moveFocus(txtPrecioVentaRedondeado);
            
        } catch (SQLException sql) {
            log.error("", sql);
        }
        //JCHAVEZ 29102009 fin

        if (!VariablesModuloVentas.vEsPedidoConvenio || VariablesModuloVentas.vIndOrigenProdVta.equals(ConstantsModuloVenta.IND_ORIGEN_OFER)) {
           // T_lblPrecVtaConv.setVisible(false);
            lblPrecVtaConv.setVisible(false);
        }


    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtPrecioVenta_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantidad);
        }
    }
    

    private void txtCantidad_keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_UP) {
            double precioFracSelec = 0;
            if(tblFracciones.getSelectedRow()>=0)
                precioFracSelec = Double.parseDouble(getPrecioSeleccionado().trim());
            else
                precioFracSelec = FarmaUtility.getDecimalNumber(txtPrecioVenta.getText().trim());
                       
            txtPrecioFinal.setNumber(precioFracSelec); 
            setPPrecioBase(precioFracSelec);
                        
            if(getPPrecioMin()>FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim())){
               lblAlertaPrecio.setText("Debe ser Mayor a S/ "+getPPrecioMin());
            }
            else
                lblAlertaPrecio.setText("");
        }
        operaprecioNuevo();
        
        calculaInfoGanancia();
    }
    

    private void txtCantidad_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblFracciones,new JTextField(),0);
        aceptar_transaccion(e);
    }

    private void this_windowOpened(WindowEvent e) {
        
        VariablesModuloVentas.vValFracLocal = VariablesModuloVentas.vVal_Frac;
        
        if(VariablesPtoVenta.vPermiteVerUtilidad)
          pnlUtilidad.setVisible(true);
        else
          pnlUtilidad.setVisible(false);
          
        lblCosto.setText("");
        lblMargen.setText("");
        lblUtilidad.setText("");
        
        VariablesModuloVentas.vLoteProd  = "";
        this.setResizable(false);
        //txtPrecioFinal.setEditable(false);
        txtSubTotal.setEditable(false);
        log.debug("VariablesVentas.vVal_Prec_Vta;" + VariablesModuloVentas.vVal_Prec_Vta);
        FarmaUtility.centrarVentana(this);
        this.setLocation(this.getX(), this.getY() - 75);
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

            muestraInfoDetalleProd_Btl();

        } else {
            muestraInfoDetalleProd();
        }
        evaluaTipoPedido();
        //JCORTEZ 17.04.08
        lblDscto.setVisible(false);
        lblDcto.setVisible(false);


        
        muestraMaxProdCupon();
        calculoNuevoPrecio();
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {

        } else {
            ///---
            /* if(isExisteProdCampana(VariablesVentas.vCod_Prod)){
        lblMensajeCampaña.setVisible(true);
    }
    else*/
            lblMensajeCampaña.setVisible(false);
            ///---
        }
        
        if(VariablesModuloVentas.vIsVtaSoat)
            FarmaUtility.moveFocus(txtPrecioVentaRedondeado);
        
        
       

        txtDescAuxiliar.setText(getDatoAuxiliar(VariablesModuloVentas.vCod_Prod));
        
        double precioOriginal = 0.0;        
        precioOriginal = FarmaUtility.getDecimalNumber(txtPrecioVenta.getText().trim());
        txtPrecioFinal.setNumber(precioOriginal);
        
        cambiaCotiza();
        
        double precioFracSelec = 0;
        
            
        if(vPermiteVerPrecioMinimo)
            btnPrecioVta.setText("Precio Costo : S/.  " + getPrecioCosto(VariablesModuloVentas.vCod_Prod));
        else
            btnPrecioVta.setText("");

        precioFracSelec = Double.parseDouble(getPrecioSeleccionado().trim());
        /*if(tblFracciones.getSelectedRow()>=0)
            precioFracSelec = Double.parseDouble(getPrecioSeleccionado().trim());
        else
            precioFracSelec = FarmaUtility.getDecimalNumber(txtPrecioVenta.getText().trim());
        */
        setPPrecioBase(precioFracSelec);
                   
        txtPrecioFinal.setNumber(precioFracSelec);
        
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnPrecioVta_actionPerformed(ActionEvent e) {
        if (VariablesModuloVentas.vEsPedidoDelivery || VariablesModuloVentas.vEsPedidoInstitucional)
            FarmaUtility.moveFocus(txtPrecioVenta);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        else
            if (e.getKeyCode()==KeyEvent.VK_F11){
                evento_enter_final();
            }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) > 0)
            valor = true;
        return valor;
    }

    private boolean validaPrecioIngreso() {
        boolean valor = false;
        String precioIngreso = txtPrecioVenta.getText().trim();
        if (FarmaUtility.isDouble(precioIngreso) && FarmaUtility.getDecimalNumber(precioIngreso) > 0)
            valor = true;
        return valor;
    }

    private boolean validaStockActual() {
        boolean valor = false;
        obtieneStockProducto();
        String cantIngreso = txtCantidad.getText().trim();
        String pValFrac  = getFracSeleccionado();
        
        double pCantidadSolicitada = Integer.parseInt(txtCantidad.getText().trim())*(Integer.parseInt(VariablesModuloVentas.vValFracLocal))/
                     Integer.parseInt(pValFrac);
        
        
        //if ((Integer.parseInt(VariablesModuloVentas.vStk_Prod) + cantInic) >= Integer.parseInt(cantIngreso))
        if ((Integer.parseInt(VariablesModuloVentas.vStk_Prod) + cantInic) >= pCantidadSolicitada)
            valor = true;
        return valor;
    }

    public void aceptaCantidadIngresada() {
        VariablesModuloVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_N;
        boolean pAactivoVtaNegativa = false;
         
        if (!validaCantidadIngreso()) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
            return;
        }
        
        if (!validaStockActual() && !isIngresoExterno) {
            FarmaUtility.liberarTransaccion();


            if (!permiteVtaNegativaTipoCompra()) {
                FarmaUtility.showMessage(this, "La cantidad ingresada no puede ser mayor al Stock del Producto.",
                                         txtCantidad);
                lblStock.setText("" + (Integer.parseInt(VariablesModuloVentas.vStk_Prod) + cantInic));
                return;
            } else {
                pAactivoVtaNegativa = true;
            }
        }


        VariablesModuloVentas.vCant_Ingresada = txtCantidad.getText().trim();
        
        /*if(vIndRecetaAutomatico){
            VariablesModuloVentas.vVal_Prec_Vta = txtPrecioVenta.getText().trim();
        }
        else{
            VariablesModuloVentas.vVal_Prec_Vta = txtPrecioFinal.getText().trim();
        }*/
        
        //2016.12.06
        if(txtPrecioFinal.getText().trim().length()==0){
            operaprecioNuevo();
        }
        
        
        VariablesModuloVentas.vVal_Prec_Vta = txtPrecioFinal.getText().trim();
        
        // GUARDA LO SELECCIONADO
        VariablesModuloVentas.vVal_Frac=getFracSeleccionado();
        VariablesModuloVentas.vUnid_Vta=getUnidadSeleccionado();
        
        VariablesModuloVentas.vLoteProd = getNumLote();

        cerrarVentana(true);
    }

    private void obtieneStockProducto_ForUpdate(ArrayList pArrayList) {
        try {
            DBModuloVenta.obtieneStockProducto_ForUpdate(pArrayList, VariablesModuloVentas.vCod_Prod, VariablesModuloVentas.vVal_Frac);
            FarmaUtility.liberarTransaccion();
            //quitar bloqueo de stock fisico
            //dubilluz 13.10.2011
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            //quitar bloqueo de stock fisico
            //dubilluz 13.10.2011
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener stock del producto. \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private void obtieneStockProducto() {
        ArrayList myArray = new ArrayList();
        obtieneStockProducto_ForUpdate(myArray);
        if (myArray.size() == 1) {
            VariablesModuloVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesModuloVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            log.info("DlgIngresoCantidad : VariablesVentas.vPorc_Dcto_1 (2) - " + VariablesModuloVentas.vPorc_Dcto_1);
        } else {
            FarmaUtility.showMessage(this, "Error al obtener Stock del Producto", null);
            cerrarVentana(false);
        }
    }

    private void evaluaTipoPedido() {
        if (!VariablesModuloVentas.vEsPedidoDelivery && !VariablesModuloVentas.vEsPedidoInstitucional) {
            /*if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) && FarmaVariables.vIndHabilitado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
      {
      }*/
            txtPrecioVenta.setEnabled(false);
            FarmaUtility.moveFocus(txtCantidad);
        }
    }

    private void calculoNuevoDescuento() {
        double precioLista = FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Lista);
        double precioVenta = FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta);
        double porcDcto = (1 - (precioVenta / precioLista));
        VariablesModuloVentas.vPorc_Dcto_1 = FarmaUtility.formatNumber(porcDcto, 2);
    }

    private void muestraMaxProdCupon() {
        String vCodCamp;
        String vIndPordCamp;
        String vIndTipoCupon;
        double vCantProdMax;
        //ArrayList cupon = new ArrayList();
        Map mapaCupon = new HashMap();

        lblProdCupon.setVisible(false);

        try {
            for (int j = 0; j < VariablesModuloVentas.vArrayList_Cupones.size(); j++) {
                mapaCupon = (Map)VariablesModuloVentas.vArrayList_Cupones.get(j);
                vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString(); //cupon.get(1).toString();
                vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString(); //cupon.get(2).toString();
                vCantProdMax =
                        FarmaUtility.getDecimalNumber(mapaCupon.get("UNID_MAX_PROD").toString()); //FarmaUtility.getDecimalNumber(cupon.get(6).toString());
                vIndPordCamp = DBModuloVenta.verificaProdCamp(vCodCamp, VariablesModuloVentas.vCod_Prod);
                /*if (vIndPordCamp.equals(FarmaConstants.INDICADOR_S)) {
                    if (vIndTipoCupon.equalsIgnoreCase("P")) {
                        lblProdCupon.setText("Máximo " + vCantProdMax + " unidades para aplicar el descuento.");
                        lblProdCupon.setVisible(true);
                    }
                    break;
                }*/
            }
        } catch (SQLException e) {
            log.error(null, e);
        }
    }

    private boolean isCampanaFidelizacion(String pCodCupon) {
        int i = pCodCupon.trim().indexOf("F");
        if (i == -1)
            return false;
        return true;
    }
    
    
    public String getPrecioSeleccionado(){
        if(tblFracciones.getSelectedRow()>=0)
           return FarmaUtility.getValueFieldArrayList(tbmFracciones.data, tblFracciones.getSelectedRow(), 2).trim();
        else
            return "0";
    }
    
    public String getFracSeleccionado(){
        if(tblFracciones.getSelectedRow()>=0)
            return FarmaUtility.getValueFieldArrayList(tbmFracciones.data, tblFracciones.getSelectedRow(), 3).trim();
        else
            return VariablesModuloVentas.vValFracLocal;
    }
    
    public String getUnidadSeleccionado(){
        if(tblFracciones.getSelectedRow()>=0)
            return FarmaUtility.getValueFieldArrayList(tbmFracciones.data, tblFracciones.getSelectedRow(), 1).trim();
        else
            return VariablesModuloVentas.vUnid_Vta;
    }
    
    /**
     * corregir este metodo ya que en su momento DUBILLUZ
     * hizo la logicade mostrar el primero encontrado
     ***/
    public void calculoNuevoPrecio() {
        String vCodCamp;
        String vNvoPrecio;
        String vIndTipoCupon;
        double vNvoPrecioRedondeado; //JCHAVEZ 29102009
        //ArrayList cupon = new ArrayList();
        Map mapaCupon = new HashMap();
        //boolean vIndFidelizacion =  false;
        String vIndFidelizacion = "N";
        pPrecioFidelizacion = 0.0;
        lblPrecioProdCamp.setVisible(false);

        String vPrecioVenta = "";

        try {
            for (int j = 0; j < VariablesModuloVentas.vArrayList_Cupones.size(); j++) {
                mapaCupon = (Map)VariablesModuloVentas.vArrayList_Cupones.get(j);
                vCodCamp = mapaCupon.get("COD_CAMP_CUPON").toString(); //cupon.get(1).toString();
                vIndTipoCupon = mapaCupon.get("TIP_CUPON").toString(); //cupon.get(2).toString();
                vIndFidelizacion = mapaCupon.get("IND_FID").toString(); /*false;
                                                                     *
            														vIndFidelizacion = isCampanaFidelizacion(cupon.get(0).toString());*/
                vPrecioVenta = txtPrecioVenta.getText().trim();

                if (vIndFidelizacion.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    vNvoPrecio = DBModuloVenta.getNuevoPrecio(VariablesModuloVentas.vCod_Prod, vCodCamp, vPrecioVenta);
                    // vNvoPrecioRedondeado= DBVentas.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //JCHAVEZ 29102009
                    if (!vNvoPrecio.equals(FarmaConstants.INDICADOR_N)) {
                        vNvoPrecioRedondeado = DBModuloVenta.getPrecioRedondeado(Double.parseDouble(vNvoPrecio.trim())); //JCHAVEZ 29102009
                        if (vIndTipoCupon.equalsIgnoreCase("P")) {
                            pPrecioFidelizacion = Double.parseDouble(vNvoPrecio.trim());
                            log.debug("JCHAVEZ pPrecioFidelizacion: sin redondeo " + pPrecioFidelizacion);
                            lblPrecioProdCamp.setText("Prec. Fidelizado. : S/. " +
                                                      vNvoPrecioRedondeado); //JCHAVEZ 29102009 se cambio vNvoPrecio por vNvoPrecioRedondeado
                            lblPrecioProdCamp.setVisible(true);
                        }
                        break;
                    }
                }


            }

            //05-DIC-13, TCT , Obtener precio Fijo de Producto y Comparar  con Fidelizado  -- Begin
            // Lectura de Datos de Campaña con el Mejor Precio de Promocion (el mas bajo)
            if (!VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                Map mapCampPrec = new HashMap();
                /*try {
         mapCampPrec = DBVentas.getDatosCampPrec(VariablesVentas.vCod_Prod);


     } catch (Exception e) {

         FarmaUtility.showMessage(this,
                                  "Ocurrio un error al obtener datos de la Campaña por Precio.\n" +
                 e.getMessage() +
                 "\n Inténtelo Nuevamente\n si persiste el error comuniquese con operador de sistemas.",
                 null);

     }*/
                log.debug("######## TCT 20: mapCampPrec:" + mapCampPrec);

                // Verificar si el Precio de Campaña x Precio es < al Precio Fidelizado => Calcular Nuevamente
                if (mapCampPrec.size() > 0) {
                    double vd_Val_Prec_Prom;
                    double vd_Prec_Fideliz = 99999.0;
                    int vi_Val_Frac_Vta = Integer.parseInt(VariablesModuloVentas.vVal_Frac);
                    //int vi_Val_Frac_Sug = Integer.parseInt(VariablesVentas.vVal_Frac_Sug);

                    String vs_Val_Prec_Prom = (String)mapCampPrec.get("VAL_PREC_PROM_ENT");
                    String vs_Cod_Camp_Prec = (String)mapCampPrec.get("COD_CAMP_CUPON");
                    log.debug("###### TCT 21 : vs_Val_Prec_Prom: " + vs_Val_Prec_Prom);
                    /*
       if (VariablesVentas.vVal_Frac_Sug.length()>0) {
           vi_Val_Frac_Vta=Integer.parseInt(VariablesVentas.vVal_Frac_Sug);
       }
       */
                    vd_Val_Prec_Prom = FarmaUtility.getDecimalNumber(vs_Val_Prec_Prom) / vi_Val_Frac_Vta;
                    if (lblPrecioProdCamp.getText().length() > 0) {
                        vd_Prec_Fideliz =
                                pPrecioFidelizacion; //Double.parseDouble(lblPrecioProdCamp.getText().trim());
                    }

                    log.debug("###### TCT 25 : vd_Val_Prec_Prom: " + vd_Val_Prec_Prom);

                    if (vd_Val_Prec_Prom < vd_Prec_Fideliz) {
                        lblPrecioProdCamp.setText("Prec. Fijo Campaña : S/. " + vd_Val_Prec_Prom);
                        lblPrecioProdCamp.setVisible(true);
                    }


                }
            }
            //05-DIC-13, TCT , Obtener precio Fijo de Producto y Comparar  con Fidelizado  -- End


        } catch (SQLException e) {
            log.error(null, e);
        }
    }
    /*boolean vIndFidelizacion;
  // [FA0001, A0001, P, 10, 10, 0, 1, 100, N, 0000000100000001P 99990.000],
  vIndFidelizacion = isCampanaFidelizacion(cupon.get(0));
   * */

    private String getAnalizaPrecio(String pPrecioVenta, double pNvoPrecioFid) {
        String pResultado = "";

        //jcallo se quito las comas del precio de los productos
        pPrecioVenta = pPrecioVenta.replaceAll(",", "");

        double pPrecio = Double.parseDouble(pPrecioVenta.trim());
        log.debug("pPrecio:" + pPrecio);
        log.debug("pNvoPrecioFid:" + pNvoPrecioFid);
        log.debug("VariablesVentas.vIndAplicaPrecNuevoCampanaCupon:" + VariablesModuloVentas.vIndAplicaPrecNuevoCampanaCupon);
        if (pNvoPrecioFid > 0) {
            if (pPrecio >= pNvoPrecioFid) {
                /*Se comento este metodo porque no funcionaba para el caso
                 * de productos fraccionados
                 * asi que por lo visto no existe diferencia salvo
                */
                /*
                try
                {
                  pResultado = DBVentas.getPrecioNormal(VariablesVentas.vCod_Prod);

                }catch(SQLException e)
                {
                  log.error(null,e);
                } */
                VariablesModuloVentas.vIndAplicaPrecNuevoCampanaCupon = FarmaConstants.INDICADOR_S;
                pResultado = "" + pPrecio;
            } else {
                pResultado = "" + pPrecio;

            }
        } else
            pResultado = "" + pPrecioVenta;
        pResultado = pResultado.trim();
        log.debug("pResultado:" + pResultado);

        return pResultado;
    }


    private boolean isExisteProdCampana(String pCodProd) {
        //lblMensajeCampaña.setVisible(true);
        String pRespta = "N";
        try {
            lblMensajeCampaña.setText("");
            pRespta = DBModuloVenta.existeProdEnCampañaAcumulada(pCodProd, VariablesFidelizacion.vDniCliente);
            if (pRespta.trim().equalsIgnoreCase("E"))
                lblMensajeCampaña.setText("    Cliente ya está inscrito en campaña Acumula tu Compra y Gana");

            if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                lblMensajeCampaña.setText("  Producto se encuentra en la campaña \" Acumula tu Compra y Gana\"");
        } catch (SQLException e) {
            log.error(null, e);
            pRespta = "N";
        }

        if (pRespta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return false;

        return true;
    }


    private void muestraInfoDetalleProd_Btl() {

        log.debug("metodo:muestraInfoDetalleProd_Btl");
        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);
        if (myArray.size() == 1) {
            VariablesModuloVentas.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            VariablesModuloVentas.vStk_Prod_Fecha_Actual = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            if ((!VariablesModuloVentas.vEsPedidoDelivery && !VariablesModuloVentas.vEsPedidoInstitucional) ||
                !VariablesModuloVentas.vIngresaCant_ResumenPed) {

                //JCORTEZ 11/04/08 no se actualiza el precio y descuento si es producto  oferta
                //if(!VariablesVentas.vIndOrigenProdVta.equals(ConstantsVentas.IND_ORIGEN_OFER)||!VariablesVentas.vEsProdOferta)

                // Segun gerencia se debe seguir la misma logica para todos los productos.
                if (VariablesModuloVentas.vVentanaListadoProductos) {
                    log.debug("SETEANDO DESCUENTO");
                    VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                    VariablesModuloVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();

                } else {
                    if (UtilityModuloVenta.isAplicoPrecioCampanaCupon(lblCodigo.getText().trim(),
                                                                 FarmaConstants.INDICADOR_S)) {
                        if (!VariablesModuloVentas.vVentanaOferta) {
                            log.debug("SETEANDO DESCUENTO");
                            VariablesModuloVentas.vVal_Prec_Vta = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                            VariablesModuloVentas.vPorc_Dcto_1 = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
                        }
                    }
                }


                log.debug("VariablesVentas.vVal_Prec_Vta_XXXXXXX: " + VariablesModuloVentas.vVal_Prec_Vta);
                log.debug("VariablesVentas.vVentanaListadoProductos: " + VariablesModuloVentas.vVentanaListadoProductos);

                log.info("DlgIngresoCantidad: VariablesVentas.vPorc_Dcto_1 - " + VariablesModuloVentas.vPorc_Dcto_1);
                log.debug("VariablesVentas.vPorc_Dcto_2 : " + VariablesModuloVentas.vPorc_Dcto_2);
            }
            VariablesModuloVentas.vUnid_Vta = ((String)((ArrayList)myArray.get(0)).get(4)).trim();
            VariablesModuloVentas.vVal_Bono = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
            VariablesModuloVentas.vVal_Prec_Lista = ((String)((ArrayList)myArray.get(0)).get(7)).trim();
        } else {
            VariablesModuloVentas.vStk_Prod = "0";
            VariablesModuloVentas.vDesc_Acc_Terap = "";
            VariablesModuloVentas.vStk_Prod_Fecha_Actual = "";
            VariablesModuloVentas.vVal_Prec_Vta = "";
            VariablesModuloVentas.vUnid_Vta = "";
            VariablesModuloVentas.vPorc_Dcto_1 = "";
            VariablesModuloVentas.vVal_Prec_Lista = "";
            VariablesModuloVentas.vNom_Lab = "";
            VariablesModuloVentas.vDesc_Prod = "";
            VariablesModuloVentas.vCod_Prod = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", null);
            cerrarVentana(false);
        }


        lblStock.setText("" + (Integer.parseInt(VariablesModuloVentas.vStk_Prod) + cantInic));
        //lblStock.setText(VariablesVentas.vStk_Prod);
        lblCodigo.setText(VariablesModuloVentas.vCod_Prod);
        lblDescripcion.setText(VariablesModuloVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesModuloVentas.vNom_Lab);
        lblUnidad.setText(VariablesModuloVentas.vUnid_Vta);

        String PrecioNew = VariablesConvenioBTLMF.vNew_Prec_Conv.toString();
        log.debug("Precio PrecioNew:" + PrecioNew);


        log.debug("VARIABLE EN MEMORIA CONVENIO:" + VariablesConvenio.vVal_Prec_Vta_Conv);

        log.debug("Precio Normal:" + VariablesModuloVentas.vVal_Prec_Vta);
        log.debug("Precio Conven:" + VariablesConvenioBTLMF.vNew_Prec_Conv);

        //        if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0)
        //        {
        //           if (VariablesVentas.vEstadoProdConvenio.equals("P"))
        //           {
        //         	  VariablesConvenioBTLMF.vNew_Prec_Conv = VariablesVentas.vVal_Prec_Vta;
        //           }
        //           //Valida el Monto Precio convenio
        //           if(UtilityConvenioBTLMF.esMontoPrecioCero(VariablesConvenioBTLMF.vNew_Prec_Conv,this))
        //           {
        //        	   cerrarVentana(false);
        //           }
        //        }
        //rsachun
        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio != null &&
            VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
            if ((VariablesModuloVentas.vEstadoProdConvenio.equals("P")) ||
                (VariablesModuloVentas.vEstadoProdConvenio.equals("I"))) {
                log.debug("Estado:" + VariablesModuloVentas.vEstadoProdConvenio);
            } else {
                VariablesConvenioBTLMF.vNew_Prec_Conv = VariablesModuloVentas.vVal_Prec_Vta;
            }
            //Valida el Monto Precio convenio
            if (UtilityConvenioBTLMF.esMontoPrecioCero(VariablesConvenioBTLMF.vNew_Prec_Conv, this)) {
                cerrarVentana(false);
            }
        }

        txtPrecioVenta.setText(VariablesModuloVentas.vVal_Prec_Vta); //JCHAVEZ 29102009 se cambio txtPrecioVenta por txtPrecioVentaOculto


        //lblDcto.setText(VariablesVentas.vPorc_Dcto_1);
        txtCantidad.setText("" + cantInic);
        //JCHAVEZ 29102009 inicio
        try {
            //double precVtaRedondeadoNum = DBVentas.getPrecioRedondeado(Double.parseDouble(VariablesVentas.vVal_Prec_Vta));  antes
            double precVtaRedondeadoNum = DBModuloVenta.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta)); //ASOSA, 18.06.2010

            String precVtaRedondeadoStr =
                FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaRedondeadoNum), 3);

            VariablesConvenio.vVal_Prec_Vta_Conv = VariablesConvenioBTLMF.vNew_Prec_Conv;
            /*UtilityConvenioBTLMF.Conv_Buscar_Precio()*/;
            /*DBConvenioBTLMF.obtieneNvoPrecioConvenio_btlmf(VariablesConvenioBTLMF.vCodConvenio,
                                                                                           VariablesVentas.vCod_Prod,
                                                                                           VariablesVentas.vVal_Prec_Pub);*/

            if (VariablesConvenio.vVal_Prec_Vta_Conv != null &&
                !VariablesConvenio.vVal_Prec_Vta_Conv.trim().equalsIgnoreCase("")) {
                log.debug("VariablesConvenio.vVal_Prec_Vta_Conv: " + VariablesConvenio.vVal_Prec_Vta_Conv);
                double precVtaConvRedondeadoNum = DBModuloVenta.getPrecioRedondeado(FarmaUtility.getDecimalNumber(VariablesConvenio.vVal_Prec_Vta_Conv));
                String precVtaConvRedondeadoStr =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber("" + precVtaConvRedondeadoNum), 3);

                lblPrecVtaConv.setText(precVtaConvRedondeadoStr); //JCHAVEZ 29102009 se cambio VariablesConvenio.vVal_Prec_Vta_Conv por precVtaConvRedondeadoStr

            }
            log.debug("precVtaRedondeadoStr:" + precVtaRedondeadoStr);

            this.txtPrecioVentaRedondeado.setText(precVtaRedondeadoStr);


        } catch (SQLException sql) {
            log.error("", sql);
        }
        //JCHAVEZ 29102009 fin
    }
    
    public boolean permiteVtaNegativaTipoCompra() {
        if(VariablesModuloVentas.is_cotizacion){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean permiteVtaNegativa() {
        boolean pResultado = false;
        // Consultara si Permite Venta Negativa en la configuracion del local.
        //VariablesVentas.vCod_Prod,VariablesVentas.vVal_Frac
        String pConsulta = "";

        try {
            pConsulta = DBModuloVenta.getPermiteVtaNegativa(VariablesModuloVentas.vCod_Prod, txtCantidad.getText().trim(), VariablesModuloVentas.vVal_Frac);
            FarmaUtility.liberarTransaccion();
        } catch (Exception nfe) {
            log.error("", nfe);
        }
        log.debug(">>><<< " + pConsulta);
        if (pConsulta.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            pResultado = false;
        else {
            String[] pListaDatos = pConsulta.trim().split("@");
            //Aqui se colocara lo programado y solicitado     >>
            //47 STREET AT SPRAY FEELING@                     >> 1
            //Usted, desea vender 80 unidades y su stock @    >> 2
            //En Sistema es de 76@                            >> 3
            //¿Desea Vender en Negativo?@                     >> 4
            //Consulte a su Jefe de Local@                    >> 5
            //4                                               >> 6
            String pLineaUno = pListaDatos[0].toString().trim();
            String pLineaDos = pListaDatos[1].toString().trim();
            String pLineaTres = pListaDatos[2].toString().trim();
            String pLineaCuatro = pListaDatos[3].toString().trim();
            String pLineaCinco = pListaDatos[4].toString().trim();
            String pLineaSeis = pListaDatos[5].toString().trim();

            boolean pRespuesta = JConfirmDialog.rptaConfirmDialogDefaultNo(this, pLineaUno + "\n" +
                    "\n" +
                    pLineaDos + "\n" +
                    pLineaTres);

            if (pRespuesta) {
                //pResultado = true;
                // Ingresara los codigo de barra de los tdos los productos
                // las N veces Posible
                DlgIngCodBarraNegativa dlgIngresoCantidad =
                    new DlgIngCodBarraNegativa(myParentFrame, "", true, VariablesModuloVentas.vCod_Prod, pLineaUno,
                                               Integer.parseInt(txtCantidad.getText().trim()));

                dlgIngresoCantidad.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //CLAVE DE QF
                    //if (cargaValidaLogin(VariablesModuloVentas.vCod_Prod, pLineaUno, Integer.parseInt(pLineaSeis.trim()))) {
                      //  log.debug("GRABA SOLICITUD");
                        // ingresa solicitud de VENTA NEGATIVA
                        grabaSolicitud();
                        pResultado = true;
                    //}
                }
            } else {
                pResultado = false;
            }


        }

        return pResultado;
    }

    public void grabaSolicitud() {
        //VariablesVentas.vCodSolicitudVtaNegativa = "";

        try {
            VariablesModuloVentas.vCodSolicitudVtaNegativa = DBModuloVenta.getGrabaSolcitudVtaNegativa(VariablesModuloVentas.vCodSolicitudVtaNegativa, VariablesModuloVentas.vQFApruebaVTANEGATIVA, VariablesModuloVentas.vCod_Prod, txtCantidad.getText().trim(), VariablesModuloVentas.vVal_Frac);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception nfe) {
            FarmaUtility.liberarTransaccion();
            log.error("", nfe);
        }

    }

    public void setCantInic(int cantInic) {
        isIngresoExterno = true;
        this.cantInic = cantInic;
    }

    public int getCantInic() {
        return cantInic;
    }
    
    public String getNumeroTexto(double vValor){
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        String numberAsString = decimalFormat.format(vValor);
        return numberAsString.trim();
    }
    
    public double redondearDecimales(double valorInicial, int numeroDecimales) {
            double parteEntera, resultado;
            resultado = valorInicial;
            parteEntera = Math.floor(resultado);
            resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
            resultado=Math.round(resultado);
            resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
            return resultado;
        }
    
    private void operaprecioNuevo() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if(cantidad>0){
            
            double precioOriginal = 0.0;
            
                precioOriginal = txtPrecioFinal.getDouble();
                    
                    //FarmaUtility.getDecimalNumber(txtPrecioVenta.getText().trim());
           
            
            

            double pSubTotal = cantidad * precioOriginal;

            txtSubTotal.setText(
                                    //getNumeroTexto(pSubTotal) + ""
                                    //redondearDecimales(pSubTotal,2)+""
                                    FarmaUtility.getDecimalNumberRedondeado(pSubTotal)+""
                );
            
                    /*txtPrecioFinal.setText(
                                            //getNumeroTexto(precioOriginal)
                                           //);
                        txtPrecioVenta.getText().trim()
                        );*/

               // txtPrecioFinal.setNumber(precioOriginal);
            }
            else{
                txtSubTotal.setText("");
                //txtPrecioFinal.setText("");
            }
        } catch (NumberFormatException nfe) {
            txtSubTotal.setText("");
            //txtPrecioFinal.setText("");
            // TODO: Add catch code
            //nfe.printStackTrace();
        }
        
    }
    
    private void txtSubTotal_keyReleased(KeyEvent e) {
        operaSubTotalNuevo();
        calculaInfoGanancia();
    }
    
    private void operaSubTotalNuevo() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if(cantidad>0){
            double precioOriginal = 0;
            
            double precioFracSelec = 0;
            
            if(tblFracciones.getSelectedRow()>=0)
                precioFracSelec = Double.parseDouble(getPrecioSeleccionado().trim());
            else
                precioFracSelec = FarmaUtility.getDecimalNumber(txtPrecioVenta.getText().trim());
            
             precioOriginal = precioFracSelec;
            
            double pSubTotal = cantidad * precioOriginal;
            
            double pSubTotalNuevo = Double.parseDouble(txtSubTotal.getText());
            //FarmaUtility.showMessage(this,"pSubTotalNuevo "+pSubTotalNuevo+ " - cantidad "+cantidad, txtSubTotal);
            txtPrecioFinal.setText(FarmaUtility.getDecimalNumberRedondeado(pSubTotalNuevo/cantidad)+"");
            }
            else{
                txtSubTotal.setText("");
                txtPrecioFinal.setText("");
            }
        } catch (Exception nfe) {
            txtSubTotal.setText("");
            txtPrecioFinal.setText("");
            // TODO: Add catch code
            //nfe.printStackTrace();
        }
        
    }


    private void jButton1_actionPerformed(ActionEvent e) {
        // Bjct, 27-DIC-12, si producto tiene precio inferior al minimo then error
        evento_enter_final();
    }
    
    private String getDatoAuxiliar(String vCodProd) {
        String pDescAuxiliar = "";
        
        return pDescAuxiliar;
    }

    private void txtCantidad_focusLost(FocusEvent e) {
        //FarmaUtility.moveFocus(txtCantidad);
    }

    private void jLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPrecioFinal);
    }

    private void txtPrecioFinal_keyReleased(KeyEvent e) {
        operaPrecioUnit_Nuevo();
        
        if(getPPrecioMin()>FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim())){
           lblAlertaPrecio.setText("Debe ser Mayor a S/ "+getPPrecioMin());
        }
        else
            lblAlertaPrecio.setText("");
        
        calculaInfoGanancia();
    }
    
    private void operaPrecioUnit_Nuevo() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if(cantidad>0){
            double precioOriginal = FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim());
            double pSubTotal = cantidad * precioOriginal;
            double pSubTotalNuevo = pSubTotal;
            //FarmaUtility.showMessage(this,"pSubTotalNuevo "+pSubTotalNuevo+ " - cantidad "+cantidad, txtSubTotal);
            txtSubTotal.setText(FarmaUtility.getDecimalNumberRedondeado(pSubTotalNuevo)+"");
            }
            else{
                txtSubTotal.setText("");
                txtPrecioFinal.setText("");
            }
        } catch (Exception nfe) {
            txtSubTotal.setText("");
            txtPrecioFinal.setText("");
            // TODO: Add catch code
            //nfe.printStackTrace();
        }
        
    }

    private void aceptar_transaccion(KeyEvent e) {
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtPrecioFinal.isEnabled())
                FarmaUtility.moveFocus(txtPrecioFinal);
        }

    }

    private void txtPrecioFinal_keyPressed(KeyEvent e) {
        //aceptar_transaccion(e);
        
        chkKeyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantidad);
        }
        
        /*if(getPPrecioBase()>FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim())){
           lblAlertaPrecio.setText("Debe ser Mayor a S/ "+getPPrecioBase());
        }
        else
            lblAlertaPrecio.setText("");*/
    }

    private void evento_enter_final() {
        // Bjct, 27-DIC-12, si producto tiene precio inferior al minimo then error
        if (!vbPrecProdOk) {
            FarmaUtility.showMessage(this,
                                     "No se puede Vender un Producto con Precio inferior al Mínimo Vigente...",
                                     txtCantidad);
            return;
        }
        
        
        if(getPPrecioMin()>FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim())){
        //if(getPPrecioBase()>FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim())){
            if(vPermiteVerPrecioMinimo){
                lblAlertaPrecio.setText("Debe ser Mayor a S/ "+getPPrecioMin());
                 FarmaUtility.showMessage(this,
                                          "No se puede Vender el producto menor a S/ "+getPPrecioMin(),
                                          txtCantidad);     
            }
            else{
                FarmaUtility.showMessage(this,
                                         "No se puede vender el producto a ese precio.",txtCantidad);
            }
           
            return;
        }
        else
            lblAlertaPrecio.setText("");
        
        // Ejct, 27-DIC-12, si producto tiene precio inferior al minimo then error
        aceptaCantidadIngresada();
    }
    
    private void cambiaCotiza() {
        if(!VariablesModuloVentas.is_cotizacion){
            jContentPane.setBackground(Color.WHITE);
        }else{
            jContentPane.setBackground(new Color(255,76,76));
        }
    }

    public void setPPrecioBase(double pPrecioBase) {
        this.pPrecioBase = pPrecioBase;
    }

    public double getPPrecioBase() {
        return pPrecioBase;
    }
    
    private void cargaListaLoteProducto() {
        try {
            DBMaestrosProductos.getListaLoteProducto(tbmLote,VariablesModuloVentas.vCod_Prod);
            if (tblLote.getRowCount() > 0) {
                FarmaGridUtils.showCell(tblLote, 0, 0);
            }
            log.debug("se cargo la lista de prods");
        } catch (SQLException sql) {
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), tblLote);
        }
    }
    
    public String getNumLote(){
        
        String pValor = txtLote.getText().trim().toUpperCase();
        
        if(pValor.length()>0){
            return pValor;
        }
        else{
            if(tblLote.getSelectedRow()>=0)
            return FarmaUtility.getValueFieldArrayList(tbmLote.data, tblLote.getSelectedRow(), 0).trim();
            else
                return "S/L";    
        }
        
        
        
        
    }
    public String getFecVencimiento(){
        if(tblLote.getSelectedRow()>=0)
        return FarmaUtility.getValueFieldArrayList(tbmLote.data, tblLote.getSelectedRow(), 1).trim();
        else
            return "N";
    }

    private String getPrecioCosto(String vCodProd) {
        String pCostoProd="";
        try {
            pCostoProd = DBModuloVenta.getCostoProducto(vCodProd,"0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pCostoProd;
    }
    
    private void calculaInfoGanancia() {
        lblCosto.setText("0.00");
        lblMargen.setText("0.00");
        lblUtilidad.setText("0.00");
        int cantidad=0;
        int valFrac = 0;
        double precioVenta=0;
        String codProd="";
        double vCostoProducto = 0;
        
        
        double vCosto_Total_prod = 0;
        double vVenta_Total_prod = 0;
        double vMargen_Total_prod = 0;
            

            log.debug("--------------------------------------------------------------------");
            codProd = lblCodigo.getText().trim();
            cantidad    =  Integer.parseInt(txtCantidad.getText().trim().replace(",",""));
            valFrac     = Integer.parseInt(getFracSeleccionado());
            precioVenta =  FarmaUtility.getDecimalNumber(txtPrecioFinal.getText().trim().replace(",",""));

            try {
                
                String pCosto = DBModuloVenta.getCostoProducto(codProd,FarmaUtility.formatNumber(valFrac*1.0).replace(",","").trim());
                vCostoProducto = Double.parseDouble(pCosto);
            } catch (SQLException e) {
                e.printStackTrace();
                vCostoProducto= 0;
            }
            
            
            vCosto_Total_prod = cantidad*vCostoProducto;
            vVenta_Total_prod = cantidad*precioVenta;    
            vMargen_Total_prod = (cantidad*precioVenta-cantidad*vCostoProducto);
                
                
        lblCosto.setText(FarmaUtility.formatNumber(vCosto_Total_prod));
        lblMargen.setText(FarmaUtility.formatNumber(vVenta_Total_prod));
        if(vCosto_Total_prod==0)
            lblUtilidad.setText("100%");
        else
        lblUtilidad.setText(FarmaUtility.formatNumber(100*(vMargen_Total_prod/vCosto_Total_prod),2)+" %");
    }

    private double getPPrecioMin() {
        
        double pPrecioMinimo  = 0;
        
        try {
            pPrecioMinimo =
                    Double.parseDouble(FarmaUtility.getValueFieldArrayList(tbmFracciones.data, tblFracciones.getSelectedRow(),
                                                                           4).trim().replace(",", ""));
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            //nfe.printStackTrace();
            pPrecioMinimo = 0;
        }
        
        if(pPrecioMinimo>0)
            return pPrecioMinimo;
        else
            return pPrecioBase;
    }

    private void tblFracciones_mouseClicked(MouseEvent e) {
        e.consume();
        FarmaUtility.showMessage(this,"No puede seleccionar la unidad con el mouse.", txtCantidad);
    }

    private void tblLote_mouseClicked(MouseEvent e) {
        if(tblFracciones.getRowCount()>0){
            int pos = tblFracciones.getRowCount();
            
            txtLote.setText(FarmaUtility.getValueFieldArrayList(tbmLote.data, pos, 0));
            //txtFecha.setText(FarmaUtility.getValueFieldArrayList(tbmLote.data, pos, 1));
            
        }
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        if(isUserAdministradorLocal()){
            // aurtoriza VENTA PRECIO MINIMO
            vAutorizaCambioPrecio = true;
            txtPrecioFinal.setEnabled(true);
            FarmaUtility.moveFocus(txtPrecioFinal);
        }
        else{
            vAutorizaCambioPrecio = false;
            txtPrecioFinal.setEnabled(false);
            FarmaUtility.showMessage(this, "No tiene habilitado esta opción",txtCantidad);
            FarmaUtility.moveFocus(txtCantidad);
        }
            
            
    }
    
    
    private boolean isUserAdministradorLocal() {
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


    private boolean validaLoginCambioPrecio() {
        try {
            return DBModuloVenta.isValidaAdmCambioPrecio();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    private boolean permiteVerPrecioMinimo() {
        try {
            return DBModuloVenta.isValidoVerPrecioMinimo();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    private void btnHistorico_actionPerformed(ActionEvent e) {
        
        UtilityModuloVenta.busquedaHistoricoPrecio(myParentFrame,VariablesModuloVentas.vCod_Prod);
        
        FarmaUtility.moveFocus(txtCantidad);
    }
}
