package mifarma.ptoventa.centromedico;

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
import javax.swing.table.DefaultTableCellRenderer;
import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;
import venta.DlgProcesar;
import venta.campana.reference.VariablesCampana;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.delivery.DlgListaClientes;
import venta.delivery.reference.VariablesDelivery;
import venta.fidelizacion.reference.AuxiliarFidelizacion;
import venta.fidelizacion.reference.DBFidelizacion;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.recetario.reference.VariablesRecetario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import static venta.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import venta.reference.VariablesPtoVenta;
import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloReceta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venta.modulo_venta.DlgIngresoCantidad;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;


public class DlgListaProductoReceta extends JDialog {
    
    private String codProdRecetaSelect = "";
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductoReceta.class);
    private boolean vIndActReceta = false;
    private Frame myParentFrame;

    private JTable myJTable;

    private FarmaTableModel tableModelListaPrecioProductos;
    private FarmaTableModel tblModelListaSustitutos;
    private FarmaTableModel tblModeLaboratorio;
    private FarmaTableModel tableModelListaProductosFiltrado;

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
    private JLabel lblUnidad = new JLabel();
    private JLabel lblUnidad_T = new JLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel jPanel2 = new JPanel();
    private JSeparator jSeparator2 = new JSeparator();
    private JLabel lblDescLab_Alter = new JLabel();
    private JScrollPane scpProductos = new JScrollPane();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    private JTable tblListaSustitutos = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
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


    private JLabelFunction lblF13 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private Object operaCampañasFid;
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelHeader jPanelHeader0 = new JPanelHeader();

    private JLabel lblMensajeCampaña = new JLabel();


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

    private boolean obligaFoco = true;
    private JTextField txtEmpresa = new JTextField();
    private JLabel jLabel1 = new JLabel();
    
    
    private JScrollPane srcEmpresa = new JScrollPane();
    private JTable tblLaboratorio = new JTable();
    
    //Dflores: 02/09/19
    private int posSelectEmpresa = 0;
    private String rucSelectEmpresa = "0";

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaProductoReceta() {
        this(null, "", false);
    }

    public DlgListaProductoReceta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            
            
        } catch (Exception e) {
            log.error("",e);
        }

    }

    public DlgListaProductoReceta(Frame parent, String title, boolean modal,boolean vActReceta) {
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



    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        //this.setSize(new Dimension(809, 561));
        this.setSize(new Dimension(1223, 768));
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
        lblItems.setBounds(new Rectangle(145, 10, 80, 15));
        lblItems.setFont(new Font("SansSerif", 1, 14));
        lblItems.setForeground(Color.white);
        lblItems.setHorizontalAlignment(SwingConstants.RIGHT);
        lblItems_T.setText("Items :");
        lblItems_T.setBounds(new Rectangle(15, 10, 65, 15));
        lblItems_T.setFont(new Font("SansSerif", 1, 14));
        lblItems_T.setForeground(Color.white);
        lblUnidad.setBounds(new Rectangle(90, 5, 190, 30));
        lblUnidad.setFont(new Font("SansSerif", 1, 11));
        lblUnidad.setForeground(Color.white);
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(10, 5, 55, 25));
        lblUnidad_T.setFont(new Font("SansSerif", 1, 11));
        lblUnidad_T.setForeground(Color.white);
        lblF2.setText("[ F2 ] Ver Alternativos");
        lblF2.setBounds(new Rectangle(290, 460, 130, 20));
        jScrollPane2.setBounds(new Rectangle(20, 465, 1180, 190));
        jScrollPane2.setBackground(new Color(255, 130, 14));
        jPanel2.setBounds(new Rectangle(20, 445, 1180, 20));
        jPanel2.setBackground(new Color(0, 114, 169));
        jPanel2.setLayout(null);
        jSeparator2.setBounds(new Rectangle(200, 0, 15, 20));
        jSeparator2.setBackground(Color.black);
        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        lblDescLab_Alter.setBounds(new Rectangle(225, 0, 375, 20));
        lblDescLab_Alter.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Alter.setForeground(Color.white);
        scpProductos.setBounds(new Rectangle(20, 100, 1180, 345));
        scpProductos.setBackground(new Color(255, 130, 14));
        pnlIngresarProductos.setBounds(new Rectangle(20, 0, 1180, 55));
        
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                      1));
        pnlIngresarProductos.setBackground(new Color(0, 114, 169));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(695, 5, 150, 25));
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
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(605, 665, 130, 35));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(750, 665, 145, 35));

        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(20, 665, 135, 35));
        jPanel4.setBounds(new Rectangle(630, 55, 570, 45));
        jPanel4.setBackground(new Color(0, 114, 169));
        jPanel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel4.setLayout(null);
        pnlTitle1.setBounds(new Rectangle(1685, 330, 160, 30));
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
        lblF13.setBounds(new Rectangle(425, 460, 135, 20));
        lblF13.setText("[ F3 ] Vta. Convenio");
        lblF4.setBounds(new Rectangle(565, 460, 150, 20));
        if (VariablesPtoVenta.vIndVerStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Stock Locales");
        } else if (VariablesPtoVenta.vIndVerReceMagis.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            lblF4.setText("[ F4 ] Recetario Magistral");
        }
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
        /*jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });*/
        txtEmpresa.setBounds(new Rectangle(1420, 30, 295, 20));
        txtEmpresa.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtEmpresa_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtEmpresa_keyPressed(e);
                }
            });
        jLabel1.setText("Empresa");
        jLabel1.setBounds(new Rectangle(1420, 10, 105, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        srcEmpresa.setBounds(new Rectangle(1440, 75, 225, 600));
        tblLaboratorio.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblLaboratorio_mouseClicked(e);
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
        jPanel3.add(lblMensajeCodBarra, null);
        jPanel3.add(lblUnidad, null);
        jPanel3.add(lblUnidad_T, null);
        jScrollPane2.getViewport();
        jPanel2.add(btnProdAlternativos, null);
        jPanel2.add(jSeparator2, null);
        jPanel2.add(lblDescLab_Alter, null);
        scpProductos.getViewport();
        pnlIngresarProductos.add(lblMensajeFiltro, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(btnProducto, null);
        //jContentPane.add(lblF4, null);
        jPanel4.add(lblItems_T, null);
        jPanel4.add(lblItems, null);
        //jContentPane.add(lblF13, null);
        //jContentPane.add(lblF8, null);
        //jContentPane.add(lblF9, null);
        //jContentPane.add(lblF2, null);
        srcEmpresa.getViewport().add(tblLaboratorio, null);
        jContentPane.add(srcEmpresa, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(jPanel4, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jPanel3, null);
        jScrollPane2.getViewport().add(tblListaSustitutos, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(jPanel2, null);
        scpProductos.getViewport().add(tblProductos, null);
        scpProductos.getViewport().add(txtProducto, null);
        jContentPane.add(scpProductos, null);
        jContentPane.add(pnlIngresarProductos, null);
        jContentPane.add(txtEmpresa, null);
        jContentPane.add(jLabel1, null);
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
        VariablesCentroMedico.vPosNew = 0;
        VariablesCentroMedico.vPosOld = 0;

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
                new FarmaTableModel(ConstantsCentroMedico.columnsListaProdEmpresa, ConstantsCentroMedico.defaultValuesListaProdEmpresa, 
                                    0);
        tableModelListaProductosFiltrado =
                new FarmaTableModel(ConstantsCentroMedico.columnsListaProdEmpresa, ConstantsCentroMedico.defaultValuesListaProdEmpresa, 
                            0);
        
        llenarListadoProductos(this.rucSelectEmpresa);

        FarmaUtility.initSelectList(tblProductos, 
                                    tableModelListaPrecioProductos, ConstantsCentroMedico.columnsListaProdEmpresa);
        
        tblProductos.setName(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS);
        if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, 
                                 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
    }

    private void initTableProductosSustitutos() {
        tblModelListaSustitutos = 
                new FarmaTableModel(ConstantsCentroMedico.columnsListaProdEmpresa, ConstantsCentroMedico.defaultValuesListaProdEmpresa, 
                                    0);
        
        FarmaUtility.initSelectList(tblListaSustitutos, 
                                    tblModelListaSustitutos, ConstantsCentroMedico.columnsListaProdEmpresa);
        tblListaSustitutos.setName(ConstantsModuloVenta.NAME_TABLA_SUSTITUTOS);
        muestraProductosSustitutos();
        
        
        tblModeLaboratorio = 
                new FarmaTableModel(ConstantsCentroMedico.columnsListaEmpresa, ConstantsCentroMedico.defaultValuesListaEmpresa, 
                                    0);
        
        FarmaUtility.initSimpleList(tblLaboratorio, 
                                    tblModeLaboratorio, ConstantsCentroMedico.columnsListaEmpresa);
        muestraLaboratorios();
        
        
    }

    public void iniciaProceso(boolean pInicializar) {

        for (int i = 0; i < tblProductos.getRowCount(); i++)
            tblProductos.setValueAt(new Boolean(false), i, 0);

        ArrayList myArray = new ArrayList();
        FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, myArray, 0);
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
       
        this.repaint();
        // DUBILLUZ 04.02.2013
        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();
        
        if(VariablesFidelizacion.vSIN_COMISION_X_DNI)lblDNI_SIN_COMISION.setVisible(true);
        else lblDNI_SIN_COMISION.setVisible(false);
        
        FarmaUtility.centrarVentana(this);
        vEjecutaAccionTeclaListado = false;
        
        
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
        FarmaUtility.moveFocus(txtProducto);
        
        if (VariablesModuloVentas.vKeyPress != null) {
             String vKeyPress = VariablesModuloVentas.vKeyPress.getKeyChar() + "";
             txtProducto.setText(vKeyPress.trim() + "");
             txtProducto_keyReleased(VariablesModuloVentas.vKeyPress);
        }

    }

    // ************************************************************************************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - Evitar le ejecución de 2 teclas a la vez al momento de comprometer stock 
    // ************************************************************************************************************************************************ 

    public void procesoEnter(KeyEvent e){
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
            if (true) {
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
                            VariablesCentroMedico.vCodigoBarra = 
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
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
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

        //log.info("Fin Enter:" + pasoTarjeta);    
    }

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
                llenarListadoProductos(this.rucSelectEmpresa);
                iniciaProceso(true);
                lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
            }
            //--    
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
                //log.debug("Caracter");
            }else{
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
                        VariablesCentroMedico.vPosNew = tblProductos.getSelectedRow();
                        if (VariablesCentroMedico.vPosOld == 0 && VariablesCentroMedico.vPosNew == 0) {
                            UpdateReleaseProd(e);
                            VariablesCentroMedico.vPosOld = VariablesCentroMedico.vPosNew;
                        } else {
                            if (VariablesCentroMedico.vPosOld != VariablesCentroMedico.vPosNew) {
                                UpdateReleaseProd(e);
                                VariablesCentroMedico.vPosOld = VariablesCentroMedico.vPosNew;
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
                    llenarListadoProductos(this.rucSelectEmpresa);
                    lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
                }
        }
    }

    private void btnProdAlternativos_actionPerformed(ActionEvent e) {
        setJTable(tblListaSustitutos);
      
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
                else if (UtilityPtoVenta.verificaVK_F2(e))
                {
                    
                    //muestraProductosAlternativos();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F5)
                {   //cargaListaFiltro();
                    cambiaCotiza();
                }
                else if (e.getKeyCode() == KeyEvent.VK_F7)
                {
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
                    /* if (VariablesCentroMedico.vEsPedidoConvenio||(VariablesConvenioBTLMF.vCodConvenio != null && VariablesConvenioBTLMF.vCodConvenio.trim().length()>1)) {
                            
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
                            VariablesCentroMedico.vCodProdFiltro =
                                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
                        else
                            VariablesCentroMedico.vCodProdFiltro = "";

                        if (indProm.equalsIgnoreCase("S")) { //if(!valor.booleanValue())
                            muestraPromocionProd(VariablesCentroMedico.vCodProdFiltro);
                            //else
                            //  FarmaUtility.showMessage(this,"El Producto está en una Promoción ya seleccionada",txtProducto);
                        } else
                            muestraPromocionProd(VariablesCentroMedico.vCodProdFiltro);
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
        VariablesCentroMedico.vCod_Prod = codProd;
        try {
            
                DBModuloVenta.obtieneInfoProductoVta(pArrayList, codProd);
        } catch (SQLException sql) {
            //log.error("",sql);
            log.error(null, sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        //txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            //FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
            pintaProductosSinStock();
        }
        //FarmaUtility.moveFocus(txtProducto);
    }

    private void muestraDetalleProducto() {
        vEjecutaAccionTeclaListado = false;
        if (myJTable.getRowCount() == 0)
            return;

        int vFila = myJTable.getSelectedRow();
        VariablesCentroMedico.vCod_Prod = 
                FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
        VariablesCentroMedico.vDesc_Prod = 
                ((String)(myJTable.getValueAt(vFila, 2))).trim();
        VariablesCentroMedico.vNom_Lab = 
                ((String)(myJTable.getValueAt(vFila, 4))).trim();

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
            String vMensajeProd = "N";
            if (!vMensajeProd.equalsIgnoreCase("N") && 
                FarmaVariables.vAceptar) {

                String sMensaje = "";
                sMensaje = UtilityModuloVenta.saltoLineaConLimitador(vMensajeProd);
                log.debug(sMensaje);
                FarmaUtility.showMessage(this,"Aa"+sMensaje, txtProducto);
            }

            // Valida ID Usuario 
            String pInd = DBModuloVenta.getIndSolIdUsu(VariablesCentroMedico.vCod_Prod).trim().toUpperCase();
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
            VariablesCentroMedico.vCod_Prod = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);
            VariablesCentroMedico.vDesc_Prod = 
                    ((String)(myJTable.getValueAt(vFila, 2))).trim();
            VariablesCentroMedico.vNom_Lab = 
                    ((String)(myJTable.getValueAt(vFila, 4))).trim();
            VariablesCentroMedico.vPorc_Igv_Prod = 
                    ((String)(myJTable.getValueAt(vFila, 11))).trim();
            VariablesCentroMedico.vCant_Ingresada_Temp = "0";

            guardaInfoProdVariables();
            DlgIngresoCantidad dlgIngresoCantidad = 
                new DlgIngresoCantidad(myParentFrame, "", true,ConstantsPtoVenta.TIPO_VENTA);
            dlgIngresoCantidad.setVisible(true);
            if (FarmaVariables.vAceptar) {
                seleccionaProducto();
                FarmaVariables.vAceptar = false;
            } 
            //txtProducto.setText("");
        }

    }

    /**
     * Calcula el monto total de venta
     * @author dubilluz
     * @since  18.06.2007
     */
    private void calculaTotalVentaPedido() {
    }

    private void verificaCheckJTable() {
        int vFila = myJTable.getSelectedRow();
        String codigo = FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD)+'@'+
                        FarmaUtility.getValueFieldJTable(myJTable, vFila, 23);

        codProdRecetaSelect = codigo;
        cerrarVentana(true);
        
    }

    private void seleccionaProducto() {
        int vFila = myJTable.getSelectedRow();
        Boolean valorTmp = (Boolean)(myJTable.getValueAt(vFila, 0));

        double auxCantIng = 
            FarmaUtility.getDecimalNumber(VariablesCentroMedico.vCant_Ingresada);
        int aux2CantIng = (int)auxCantIng;
        double auxPrecVta = 
            FarmaUtility.getDecimalNumber(VariablesCentroMedico.vVal_Prec_Vta);
        VariablesCentroMedico.vTotalPrecVtaProd = (auxCantIng * auxPrecVta);
    }

    private boolean validaProductoTomaInventario(int pRow) {
        if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
            return true;
        else
            return false;
    }

    private boolean validaProductoHabilVenta() {
        if (VariablesCentroMedico.vInd_Prod_Habil_Vta.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
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
        cerrarVentana(false);
    }

    private void aceptaOperacion() {
        log.info("ENTRO A ACEPTAR OPERACION");
        vEjecutaAccionTeclaListado = false;
        cerrarVentana(true);
     
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

    private void actualizaListaProductosAlternativos() {
        tblModelListaSustitutos.clearTable();
        tblModelListaSustitutos.fireTableDataChanged();
    }

    private void pintaCheckOtroJTable(JTable pActualJTable, Boolean pValor) {
        //log.debug(pValor.booleanValue());
        if (pActualJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_PRODUCTOS)) {
            FarmaUtility.setearCheckInRow(tblListaSustitutos, pValor, true, 
                                          true, VariablesCentroMedico.vCod_Prod, 
                                          COL_COD);
            tblListaSustitutos.repaint();
        } else if (pActualJTable.getName().equalsIgnoreCase(ConstantsModuloVenta.NAME_TABLA_SUSTITUTOS)) {
            FarmaUtility.setearCheckInRow(tblProductos, pValor, true, true, VariablesCentroMedico.vCod_Prod, COL_COD);
            tblProductos.repaint();
        }
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
        
    }

    private void evaluaSeleccionMedico() {
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

    
    //inicio adicion Paulo 15/06/2006

    private void faltacero() {
        vEjecutaAccionTeclaListado = false;
        //if (!validaStockDisponible()) {
        if (true) {
            int vFila = myJTable.getSelectedRow();
            VariablesCentroMedico.vCod_Prod = 
                    FarmaUtility.getValueFieldJTable(myJTable, vFila, COL_COD);

           /* if (!isExistProdListaFaltaCero(VariablesVentas.vListaProdFaltaCero, 
                                           VariablesVentas.vCod_Prod)) {*/
            if(true){
                try {
                    DBModuloVenta.ingresaStockCero();
                    FarmaUtility.aceptarTransaccion();
                    VariablesCentroMedico.vListaProdFaltaCero.add(VariablesCentroMedico.vCod_Prod);
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
    
    private void muestraProductoCongelado(JLabel pLabel) {
        if (myJTable.getRowCount() > 0) {
            //String indProdCong = ((String)(myJTable.getValueAt(myJTable.getSelectedRow(),pColumna))).trim();
            if (indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                pLabel.setVisible(true);
            else
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
    }

    /**
     * Muestra el dialogo de convenios.
     * @author Edgar Rios Navarro
     * @since 24.05.2007
     */

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
            DBModuloVenta.cagaListaEmpresas(tblModeLaboratorio);
            if(tblLaboratorio.getRowCount()>0)
            FarmaGridUtils.showCell(tblLaboratorio, 0, 0);
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
            llenarListadoProductos(this.rucSelectEmpresa);
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
    private void llenarListadoProductos(String vRuc) {
        /*
        if(vRuc.equals("0")){
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.data = VariablesCentroMedico.tableModelListaProductosEmpresa.data;
            tblLaboratorio.repaint();
            tblLaboratorio.show();
        }else{        
            ArrayList arrayClone = new ArrayList();
            for (int i = 0; i < VariablesCentroMedico.tableModelListaProductosEmpresa.data.size(); i++) {
                String nextRuc = FarmaUtility.getValueFieldArrayList(VariablesCentroMedico.tableModelListaProductosEmpresa.data, i, 23);
                //--
                if(nextRuc.equals(vRuc)){
                    ArrayList aux = 
                        (ArrayList)((ArrayList)VariablesCentroMedico.tableModelListaProductosEmpresa.data.get(i)).clone();
                    arrayClone.add(aux);
                }
            }
            //--
            tableModelListaPrecioProductos.clearTable();
            tableModelListaPrecioProductos.data = arrayClone;
            tblProductos.repaint();
            tblProductos.show();
        }*/
        
        tableModelListaPrecioProductos.clearTable();
        tableModelListaPrecioProductos.data = VariablesCentroMedico.tableModelListaProductosEmpresa.data;
        tblLaboratorio.repaint();
        tblLaboratorio.show();
        //DFLORES
        /*if (tableModelListaPrecioProductos.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductos, 
                                 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);*/
        
        //tableModelListaPrecioProductos.data = arrayClone;
    }


    public boolean tieneDatoFormaPagoFidelizado() {
        if (VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S") || 
            (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S") && 
             VariablesFidelizacion.vCodFPagoTarjeta.trim().length() > 0))
            return true;
        else
            return false;
    }

   public static ArrayList vListaProdFaltaCero = new ArrayList();
    
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
    private void lblPedDelivery_actionPerformed(ActionEvent e) {

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

    }
    
    public void funcionF12(String pCodCampanaCupon) {
        log.debug("Funcion F12");
        
        //No hbra fidelizacion
        /* AuxiliarFidelizacion.funcionF12(pCodCampanaCupon,txtProducto,this.myParentFrame,
                                        lblM11ensajeCampaña,lblCliente,this,"L",lblDNI_SIN_COMISION);
        
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

    //Dubilluz - 06.12.2011

    public void ingresaMedicoFidelizado() {
        /*
        AuxiliarFidelizacion.ingresoMedico(this.myParentFrame,lblMedico,lblMensajeCampaña,lblCliente,
                                           this,"L",lblDNI_SIN_COMISION,txtProducto);*/
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
            llenarListadoProductos(this.rucSelectEmpresa);//SIEMPRE INICIALIZA CON EL LISTADO PRINCIPAL
            //filtrar java
            ArrayList target = tableModelListaPrecioProductos.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(COL_DESC_PROD).toString().toUpperCase().trim().replace(" ", "");
                String pCodigo = fila.get(1).toString().toUpperCase().trim();
                String pCodRUC = fila.get(23).toString().toUpperCase().trim();
                String pDesGenerico = fila.get(7).toString().toUpperCase().trim().replace(" ", "");
                /*if(pCodigo.equalsIgnoreCase("135476")){
                    System.out.println(descProd.contains(condicion));
                    System.out.println(pCodigo.contains(condicion));
                    System.out.println(pDesGenerico.contains(condicion));
                }*/
                /*if(this.rucSelectEmpresa.equalsIgnoreCase("0")){
                    if(descProd.contains(condicion)||pCodigo.contains(condicion)||pDesGenerico.contains(condicion)){
                        filteredCollection.add(fila);
                    }
                }else{
                    if( (descProd.contains(condicion)||pCodigo.contains(condicion)||pDesGenerico.contains(condicion))
                        //&&pCodRUC.equals(this.rucSelectEmpresa)
                    ){
                        filteredCollection.add(fila);
                    }
                }*/
                if(descProd.contains(condicion)||pCodigo.contains(condicion)||pDesGenerico.contains(condicion)){
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
                //Dflores: 06.09.2019 *Cuando no se encuentran similar vuelve a llenar contodo x eso comment!
                llenarListadoProductos(this.rucSelectEmpresa);
            }
            else{
                if(tblProductos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblProductos.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoProductos(this.rucSelectEmpresa);
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
                
                for(int i=0;i<VariablesCentroMedico.tableModelListaProductosEmpresa.data.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(VariablesCentroMedico.tableModelListaProductosEmpresa.data,i,1).trim()
                        .equalsIgnoreCase(pCodProd)) {
                        VariablesCentroMedico.tableModelListaProductosEmpresa.data.remove(i);
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

    }


    private void cambiaCotiza() {
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
        
        FarmaUtility.moveFocus(txtProducto);
    }

    private void tblLaboratorio_mouseClicked(MouseEvent e) {
        if(e.getClickCount()==1){
            txtProducto.setText("");
            filtrar_prod_empresa();
            FarmaUtility.moveFocus(txtProducto);
        }
    }

    private void filtrar_prod_empresa() {
        this.posSelectEmpresa = tblLaboratorio.getSelectedRow();
        this.rucSelectEmpresa =  FarmaUtility.getValueFieldArrayList(tblModeLaboratorio.data,this.posSelectEmpresa, 0);
        
        if(this.rucSelectEmpresa.equalsIgnoreCase("0")){
            // ver todos los productos
            llenarListadoProductos("0");
            filtroGoogle();
            lblMensajeFiltro.setText("Todos los productos");
        }
        else{
            String condicion = txtProducto.getText().toUpperCase();
            if(!condicion.equals("") && condicion.length() > 0){
                //inicializa el listado
                llenarListadoProductos(this.rucSelectEmpresa);
                //filtrar java
                ArrayList target = tableModelListaPrecioProductos.data;        
                ArrayList filteredCollection = new ArrayList();
                
                Iterator iterator = target.iterator();
                while(iterator.hasNext()){
                    ArrayList fila = (ArrayList)iterator.next();
                    String valor_ruc = fila.get(23).toString().toUpperCase().trim();
                    
                    String descProd = fila.get(COL_DESC_PROD).toString().toUpperCase().trim().replace(" ", "");
                    String desGenerico = fila.get(7).toString().toUpperCase().trim().replace(" ", "");
                    String pCodigo = fila.get(1).toString().toUpperCase().trim();
                    /*
                    if((descProd.contains(condicion) || pCodigo.contains(condicion)||desGenerico.contains(condicion))
                       &&valor_ruc.contains(this.rucSelectEmpresa)
                    ){
                        filteredCollection.add(fila);
                    }*/
                    if(descProd.contains(condicion)||pCodigo.contains(condicion)||desGenerico.contains(condicion)){
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
                    llenarListadoProductos(this.rucSelectEmpresa);
                }
                else{
                    if(tblProductos.getRowCount()==1)
                        lblMensajeFiltro.setText(tblProductos.getRowCount()+" fila para el filtro aplicado");
                    else
                        lblMensajeFiltro.setText(tblProductos.getRowCount()+" filas para el filtro aplicado");
                }
                
            }
            else{
                llenarListadoProductos(this.rucSelectEmpresa);
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
            }
        }
        

        
        
        if(tblProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblProductos, 0, 0);
    }

    private void txtProducto_focusLost(FocusEvent e) {
    }

    private void txtEmpresa_keyReleased(KeyEvent e) {
        if(tblLaboratorio.getRowCount() >= 0 && tblModeLaboratorio.getRowCount() > 0) {
            if (FarmaGridUtils.buscarDescripcion(e, tblLaboratorio, txtEmpresa, 1)){
                                                     System.out.println("muestra dato");
            }
        }
    }

    private void txtEmpresa_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(tblLaboratorio.getSelectedRow()>=0){
                txtProducto.setText("");
                filtrar_prod_empresa();
            }
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
            chkKeyPressed(e);
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
    
    public void cargaProductosDB(){
        //FarmaUtility.showMessage(this, ".. se puede entrar", null);  
        VariablesCentroMedico.tableModelListaProductosEmpresa.clearTable();

        try {
            DBModuloVenta.cargaListaProductosVenta(VariablesCentroMedico.tableModelListaProductosEmpresa);
        } catch(Exception ef) {
                //e.printStackTrace();
        }
        
        llenarListadoProductos(this.rucSelectEmpresa);
    }
    
    
    public String getCodProdRecetaSelect(){
        return this.codProdRecetaSelect;
    }
}
