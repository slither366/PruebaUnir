package venta.caja;


import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.DlgLogin;
import common.FarmaConnection;
import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.cajas.reference.VariablesCajas;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.DBCobroBD;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesVirtual;
import venta.ce.reference.DBCajaElectronica;
import venta.ce.reference.FacadeCajaElectronica;
import venta.ce.reference.VariablesCajaElectronica;
import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.UtilityConvenio;
import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.DlgProcesarCobroBTLMF;
import venta.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import venta.convenioBTLMF.reference.DBConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.delivery.reference.DBDelivery;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.pinpad.DlgInterfacePinpad;
import venta.pinpad.reference.UtilityPinpad;
import venta.recaudacion.DlgProcesarVentaCMR;
import venta.recaudacion.DlgProcesarPagoTerceros;
import venta.recaudacion.reference.ConstantsRecaudacion;
import venta.recaudacion.reference.FacadeRecaudacion;
import venta.recaudacion.reference.UtilityRecaudacion;
import venta.recaudacion.reference.VariablesRecaudacion;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DlgNuevoCobro.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      01.03.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgNuevoCobro extends JDialog  {

    private static final Logger log = LoggerFactory.getLogger(DlgNuevoCobro.class);
  
  /** Almacena el Objeto Frame de la Aplicaci�n - Ventana Principal */
  
    public Frame myParentFrame;

    private FarmaTableModel tableModelDetallePago;
    private FarmaTableModel tableModelFormasPago;

    private boolean indPedirLogueo = true;
    private boolean indCerrarPantallaAnularPed = false;
    private boolean indCerrarPantallaCobrarPed = false;
  
    private double diferencia = 0 ;    
    private int intTipoCobro = 1;    
    private String strCodRecau = "";
    private String tipoRcd = "";    
    private boolean indBorra=false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblRazSoc = new JLabel();
    private JLabel lblRazSoc_T = new JLabel();
    private JPanel jPanel1 = new JPanel();
    
    private JLabel lblTipoTrj = new JLabel();
    
    public JTextField txtMontoPagado = new JTextField();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanel pnlTotales = new JPanel();
    private XYLayout xYLayout5 = new XYLayout();
    private JLabel lblSaldoT = new JLabel();
    private JLabel lblSaldo = new JLabel();
    public JLabel lblVueltoT = new JLabel();
    public JLabel lblVuelto = new JLabel();
    private JLabel lblTipoComprobante = new JLabel();
    private JPanel pnlTotal = new JPanel();
    private XYLayout xYLayout2 = new XYLayout();
    private JLabel lblSolesTotalVenta = new JLabel();
    private JLabel lblTotalVenta = new JLabel();
    private JButton btnMonto = new JButton();
    private JLabel lblMensaje = new JLabel();
    private JLabel lblMsjNumRecarga = new JLabel();

    private JPanel jPanel4 = new JPanel();

    public JTable tblDetallePago = new JTable();
    public JTable tblFormasPago = new JTable();
    
    public JTextField txtMontoPagadoDolares = new JTextField();
    private JButton btnMontoDolares = new JButton();
    private JLabel lblSolesTotalPagar = new JLabel();
    private JLabel lblTotalPagar = new JLabel();
    private JLabelOrange lblValCopago = new JLabelOrange();
    private JLabelOrange lblCopago = new JLabelOrange();
    private JLabelOrange lblPorcCopago = new JLabelOrange();
    private JLabelOrange lblCopagoSoles = new JLabelOrange();
    private JPanelWhite pnlCopago = new JPanelWhite();
    private JButton btnMontoTarjeta = new JButton();
    public JTextField txtMontoTarjeta = new JTextField();
    
    private String strMonedaPagar = ConstantsRecaudacion.EST_CTA_SOLES;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabel lblTipoCambioT1 = new JLabel();
    private JLabel lblMontoEnSolesT1 = new JLabel();
    private JLabel lblMontoEnSoles1 = new JLabel();
    private JLabel lblTipoCambio1 = new JLabel();
    private JPanelWhite jPanelWhite4 = new JPanelWhite();
    private JLabel lblTipoCambioT2 = new JLabel();
    private JLabel lblMontoEnSolesT2 = new JLabel();
    private JLabel lblMontoEnSoles2 = new JLabel();
    private JLabel lblTipoCambio2 = new JLabel();

    ArrayList arrayCodFormaPago = new ArrayList();
    FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
    ArrayList<Object> tmpArrayCabRcd;
    UtilityPtoVenta utilityPtoVenta = new UtilityPtoVenta();
    private JLabel lblTotalConversion = new JLabel();
    private JLabel lblTotConversionT = new JLabel();
    private boolean presionoF11 = false;

    public boolean indCobroBD = false;
    public boolean pEjecutaOldCobro=false;
    public DlgFormaPago dlgFormPago;
    
    public ArrayList vFormaPagoTarjetas =  new ArrayList();
    // **************************************************************************
// Constructores
// **************************************************************************
  /**
  *Constructor
  */
  public DlgNuevoCobro() {
    this(null, "", false);
  }

  /**
  *Constructor
  *@param parent Objeto Frame de la Aplicaci�n.
  *@param title T�tulo de la Ventana.
  *@param modal Tipo de Ventana.
  */
  public DlgNuevoCobro(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    myParentFrame = parent;
    //dlgFormPago = dlgFp;
    try {
      jbInit();
      initialize();
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
    this.setSize(new Dimension(447, 411));
    this.getContentPane().setLayout(borderLayout1);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Cobrar Pedido");
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
    jContentPane.setBackground(Color.white);
    jContentPane.setSize(new Dimension(554, 504));
        jPanel3.setBounds(new Rectangle(10, 135, 420, 35));
    jPanel3.setBackground(new Color(255, 130, 14));
    jPanel3.setBorder(BorderFactory.createTitledBorder(""));
    jPanel3.setLayout(null);
        lblRazSoc.setFont(new Font("SansSerif", 1, 12));
    lblRazSoc.setForeground(new Color(255, 130, 14));
    lblRazSoc_T.setText("Razon Social :");
        lblRazSoc_T.setFont(new Font("SansSerif", 0, 12));
    lblRazSoc_T.setForeground(new Color(255, 130, 14));
      
    lblTipoTrj.setText("");
    lblTipoTrj.setFont(new Font("SansSerif", 0, 12));
    lblTipoTrj.setForeground(new Color(255, 130, 14));

        lblTipoTrj.setBounds(new Rectangle(210, 80, 200, 20));
        jPanel1.setBounds(new Rectangle(10, 180, 420, 110));
    jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(null);

        txtMontoPagado.setText("0.00");
    txtMontoPagado.setHorizontalAlignment(JTextField.RIGHT);
    txtMontoPagado.setBounds(new Rectangle(100, 10, 90, 20));
    txtMontoPagado.setEnabled(true);
    txtMontoPagado.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
                        txtMontoPagado_mouseClicked(e);
                    }
      });
    txtMontoPagado.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtMontoPagado_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMontoPagado_keyTyped(e);
                    }
                });
        lblEsc.setText("[ Esc ]  Cerrar");
    lblEsc.setBounds(new Rectangle(340, 345, 85, 20));
    lblF11.setText("[ F11 ]  Aceptar");
    lblF11.setBounds(new Rectangle(230, 345, 100, 20));
        pnlTotales.setBounds(new Rectangle(10, 295, 420, 40));
    pnlTotales.setFont(new Font("SansSerif", 0, 11));
    pnlTotales.setBackground(new Color(43, 141, 39));
    pnlTotales.setLayout(xYLayout5);
    lblSaldoT.setText("SALDO :  S/.");
    lblSaldoT.setFont(new Font("SansSerif", 1, 13));
    lblSaldoT.setForeground(Color.white);
    lblSaldo.setText("0.00");
    lblSaldo.setFont(new Font("SansSerif", 1, 13));
    lblSaldo.setForeground(Color.white);
    lblSaldo.setHorizontalAlignment(SwingConstants.LEFT);
    lblVueltoT.setText("Vuelto :  S/.");
    lblVueltoT.setFont(new Font("SansSerif", 1, 13));
    lblVueltoT.setForeground(Color.white);
    lblVuelto.setText("0.00");
    lblVuelto.setFont(new Font("SansSerif", 1, 13));
    lblVuelto.setForeground(Color.white);
        lblTipoComprobante.setForeground(new Color(255, 130, 14));
    lblTipoComprobante.setFont(new Font("SansSerif", 1, 12));
        lblTipoComprobante.setText("[COMPROBANTE]");
        pnlTotal.setBounds(new Rectangle(10, 5, 420, 135));
    pnlTotal.setFont(new Font("SansSerif", 0, 11));
    pnlTotal.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
    pnlTotal.setBackground(Color.white);
    pnlTotal.setLayout(xYLayout2);
        lblSolesTotalVenta.setText("0.00");
    lblSolesTotalVenta.setFont(new Font("SansSerif", 1, 13));
    lblSolesTotalVenta.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSolesTotalVenta.setForeground(new Color(255, 130, 14));
        lblTotalVenta.setText("Total Venta : S/.");
    lblTotalVenta.setFont(new Font("SansSerif", 1, 13));
    lblTotalVenta.setForeground(new Color(255, 130, 14));
        btnMonto.setText("Soles : ");
    btnMonto.setBounds(new Rectangle(15, 10, 65, 20));
    btnMonto.setBorderPainted(false);
    btnMonto.setContentAreaFilled(false);
    btnMonto.setDefaultCapable(false);
    btnMonto.setFocusPainted(false);
    btnMonto.setHorizontalAlignment(SwingConstants.LEFT);
    btnMonto.setMnemonic('s');
    btnMonto.setRequestFocusEnabled(false);
    btnMonto.setFont(new Font("SansSerif", 0, 11));
    btnMonto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMonto.setActionCommand("Soles : ");
        btnMonto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnMonto_actionPerformed(e);
        }
      });
        
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("SansSerif", 1, 13));
        lblMensaje.setText("[Mensaje]");
        lblMsjNumRecarga.setForeground(new Color(43, 141, 39));
        lblMsjNumRecarga.setFont(new Font("SansSerif", 1, 17));

        lblMsjNumRecarga.setText("[997368791]");


        txtMontoPagadoDolares.setText("0.00");
        txtMontoPagadoDolares.setHorizontalAlignment(JTextField.RIGHT);
        txtMontoPagadoDolares.setBounds(new Rectangle(100, 45, 90, 20));
        txtMontoPagadoDolares.setEnabled(true);
        txtMontoPagadoDolares.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
                        txtMontoPagado_mouseClicked(e);
                    }
      });
        txtMontoPagadoDolares.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtMontoPagadoDolares_keyPressed(e);
                }

                    public void keyTyped(KeyEvent e) {
                        txtMontoPagadoDolares_keyTyped(e);
                    }
                });
        btnMontoDolares.setText("Dolares : ");
        btnMontoDolares.setBounds(new Rectangle(15, 45, 65, 20));
        btnMontoDolares.setBorderPainted(false);
        btnMontoDolares.setContentAreaFilled(false);
        btnMontoDolares.setDefaultCapable(false);
        btnMontoDolares.setFocusPainted(false);
        btnMontoDolares.setHorizontalAlignment(SwingConstants.LEFT);
        btnMontoDolares.setMnemonic('d');
        btnMontoDolares.setRequestFocusEnabled(false);
        btnMontoDolares.setFont(new Font("SansSerif", 0, 11));
        btnMontoDolares.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMontoDolares.setActionCommand("Soles : ");
        btnMontoDolares.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                    btnMontoDolares_actionPerformed(e);
                }
      });
        lblSolesTotalPagar.setText("0.00");
        lblSolesTotalPagar.setFont(new Font("SansSerif", 1, 13));
        lblSolesTotalPagar.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSolesTotalPagar.setForeground(Color.white);
        lblSolesTotalPagar.setBounds(new Rectangle(240, 10, 65, 20));
        lblTotalPagar.setText("TOTAL A PAGAR : S/.");
        lblTotalPagar.setFont(new Font("SansSerif", 1, 13));
        lblTotalPagar.setForeground(Color.white);
        lblTotalPagar.setBounds(new Rectangle(100, 10, 140, 20));
        lblValCopago.setText("0.00");
        lblValCopago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblValCopago.setFont(new Font("SansSerif", 1, 12));
        lblValCopago.setBounds(new Rectangle(145, 0, 50, 20));
        lblCopago.setText("CoPago");
        lblCopago.setFont(new Font("SansSerif", 1, 12));
        lblCopago.setBounds(new Rectangle(10, 0, 50, 20));
        lblPorcCopago.setText("100");
        lblPorcCopago.setFont(new Font("SansSerif", 1, 12));
        lblPorcCopago.setBounds(new Rectangle(60, 0, 30, 20));
        lblPorcCopago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCopagoSoles.setText("% : S/.");
        lblCopagoSoles.setFont(new Font("SansSerif", 1, 12));
        lblCopagoSoles.setBounds(new Rectangle(95, 0, 35, 20));
        btnMontoTarjeta.setText("Tarjeta : ");
        btnMontoTarjeta.setBounds(new Rectangle(15, 80, 65, 20));
        btnMontoTarjeta.setBorderPainted(false);
        btnMontoTarjeta.setContentAreaFilled(false);
        btnMontoTarjeta.setDefaultCapable(false);
        btnMontoTarjeta.setFocusPainted(false);
        btnMontoTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
        btnMontoTarjeta.setMnemonic('t');
        btnMontoTarjeta.setRequestFocusEnabled(false);
        btnMontoTarjeta.setFont(new Font("SansSerif", 0, 11));
        btnMontoTarjeta.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMontoTarjeta.setActionCommand("Soles : ");
        btnMontoTarjeta.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                    btnMontoTarjeta_actionPerformed(e);
                }
      });
        txtMontoTarjeta.setText("0.00");
        txtMontoTarjeta.setHorizontalAlignment(JTextField.RIGHT);
        txtMontoTarjeta.setBounds(new Rectangle(100, 80, 90, 20));
        txtMontoTarjeta.setEnabled(true);
        txtMontoTarjeta.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
                        txtMontoPagado_mouseClicked(e);
                    }
      });
        txtMontoTarjeta.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtMontoTarjeta_keyPressed(e,false);
                }

                    public void keyTyped(KeyEvent e) {
                        txtMontoTarjeta_keyTyped(e);
                    }
                });
        jPanelWhite1.setBounds(new Rectangle(210, 10, 190, 35));
        lblTipoCambioT1.setText("Tipo Cambio : ");
        lblTipoCambioT1.setBounds(new Rectangle(0, 0, 85, 15));
        lblTipoCambioT1.setForeground(new Color(43, 141, 39));
        lblTipoCambioT1.setFont(new Font("SansSerif", 1, 12));
        lblMontoEnSolesT1.setText("Monto en dolares : ");
        lblMontoEnSolesT1.setBounds(new Rectangle(0, 15, 105, 15));
        lblMontoEnSolesT1.setForeground(Color.black);
        lblMontoEnSolesT1.setFont(new Font("SansSerif", 0, 12));
        lblMontoEnSoles1.setBounds(new Rectangle(110, 15, 75, 15));
        lblMontoEnSoles1.setFont(new Font("SansSerif", 0, 12));
        lblMontoEnSoles1.setForeground(Color.black);
        lblMontoEnSoles1.setText("0.00");
        lblTipoCambio1.setFont(new Font("SansSerif", 1, 12));
        lblTipoCambio1.setForeground(new Color(43, 141, 39));
        lblTipoCambio1.setBounds(new Rectangle(100, 0, 75, 15));
        jPanelWhite4.setBounds(new Rectangle(205, 45, 195, 30));
        lblTipoCambioT2.setText("Tipo Cambio : ");
        lblTipoCambioT2.setBounds(new Rectangle(0, 0, 85, 15));
        lblTipoCambioT2.setForeground(new Color(43, 141, 39));
        lblTipoCambioT2.setFont(new Font("SansSerif", 1, 12));
        lblMontoEnSolesT2.setText("Monto en soles : ");
        lblMontoEnSolesT2.setBounds(new Rectangle(0, 15, 105, 15));
        lblMontoEnSolesT2.setForeground(Color.black);
        lblMontoEnSolesT2.setFont(new Font("SansSerif", 0, 12));
        lblMontoEnSoles2.setBounds(new Rectangle(110, 15, 75, 15));
        lblMontoEnSoles2.setFont(new Font("SansSerif", 0, 12));
        lblMontoEnSoles2.setForeground(Color.black);
        lblMontoEnSoles2.setText("0.00");
        lblTipoCambio2.setFont(new Font("SansSerif", 1, 12));
        lblTipoCambio2.setForeground(new Color(43, 141, 39));
        lblTipoCambio2.setBounds(new Rectangle(100, 0, 75, 15));
        lblTotalConversion.setText("0.00");
        lblTotalConversion.setFont(new Font("SansSerif", 1, 13));
        lblTotalConversion.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalConversion.setForeground(Color.white);
        lblTotalConversion.setBounds(new Rectangle(350, 10, 65, 20));
        lblTotConversionT.setText("US$");
        lblTotConversionT.setFont(new Font("SansSerif", 1, 13));
        lblTotConversionT.setForeground(Color.white);
        lblTotConversionT.setBounds(new Rectangle(310, 10, 40, 20));
        jPanel3.add(lblTotConversionT, null);
        jPanel3.add(lblTotalConversion, null);
        jPanel3.add(lblTotalPagar, null);
        jPanel3.add(lblSolesTotalPagar, null);
        pnlCopago.add(lblCopagoSoles, null);
        pnlCopago.add(lblValCopago, null);
        pnlCopago.add(lblPorcCopago, null);
        pnlCopago.add(lblCopago, null);
        jPanelWhite1.add(lblTipoCambio1, null);
        jPanelWhite1.add(lblMontoEnSoles1, null);
        jPanelWhite1.add(lblMontoEnSolesT1, null);
        jPanelWhite1.add(lblTipoCambioT1, null);
        jPanelWhite4.add(lblTipoCambio2, null);
        jPanelWhite4.add(lblMontoEnSoles2, null);
        jPanelWhite4.add(lblMontoEnSolesT2, null);
        jPanelWhite4.add(lblTipoCambioT2, null);
        jPanel1.add(jPanelWhite4, null);
        jPanel1.add(jPanelWhite1, null);
        jPanel1.add(txtMontoTarjeta, null);
        jPanel1.add(btnMontoTarjeta, null);
        jPanel1.add(btnMontoDolares, null);
        jPanel1.add(txtMontoPagadoDolares, null);
        jPanel1.add(btnMonto, null);
        jPanel1.add(txtMontoPagado, null);
        jPanel1.add(lblTipoTrj, null);
        pnlTotales.add(lblSaldoT, new XYConstraints(45, 10, 90, 20));
        pnlTotales.add(lblSaldo, new XYConstraints(130, 10, 105, 20));
        pnlTotales.add(lblVueltoT, new XYConstraints(235, 10, 80, 20));
        pnlTotales.add(lblVuelto, new XYConstraints(320, 10, 70, 20));
        pnlTotal.add(lblSolesTotalVenta, new XYConstraints(319, 4, 85, 20));
        pnlTotal.add(lblTotalVenta, new XYConstraints(200, 5, 115, 20));
        pnlTotal.add(lblTipoComprobante, new XYConstraints(4, 4, 190, 20));
        pnlTotal.add(lblRazSoc, new XYConstraints(95, 30, 275, 15));
        pnlTotal.add(lblRazSoc_T, new XYConstraints(5, 30, 85, 15));
        pnlTotal.add(lblMensaje, new XYConstraints(4, 49, 410, 40));
        pnlTotal.add(lblMsjNumRecarga, new XYConstraints(9, 99, 125, 20));
        pnlTotal.add(pnlCopago, new XYConstraints(184, 99, 210, 25));
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.getContentPane().add(jPanel4, BorderLayout.NORTH);
        jContentPane.add(jPanel3, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlTotales, null);
        jContentPane.add(pnlTotal, null);
    }

// **************************************************************************
// M�todo "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTableDetallePago();
    
    FarmaVariables.vAceptar=false;
    
    obtenerTipoCambio();
  }

// **************************************************************************
// M�todos de inicializaci�n
// **************************************************************************

  private void initTableDetallePago()
  {
        tableModelDetallePago = new FarmaTableModel(ConstantsCaja.columsListaDetallePago,ConstantsCaja.defaultListaDetallePago,0);
        FarmaUtility.initSimpleList(tblDetallePago,tableModelDetallePago,ConstantsCaja.columsListaDetallePago);
      
        //Nueva tabla
        tableModelFormasPago = new FarmaTableModel(ConstantsCaja.columsListaFormasPago,ConstantsCaja.defaultListaFormasPago,0);
        FarmaUtility.initSimpleList(tblFormasPago,tableModelFormasPago,ConstantsCaja.columsListaFormasPago);
  }


// **************************************************************************
// Metodos de eventos
// **************************************************************************


    public void this_windowOpened(WindowEvent e)
    {
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if(FarmaVariables.vTipCambio==0 || 
            VariablesRecaudacion.vTipoCambioVenta==0 ||
            VariablesRecaudacion.vTipoCambioCompra==0)
        {   FarmaUtility.showMessage(this, 
                                    "ATENCI�N: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acci�n", 
                                    null);
            cerrarVentana(false,null);
        }
        else
        {
            inicializaVariablesVentana();
            if (intTipoCobro == ConstantsCaja.COBRO_PEDIDO) {
                initVariablesCobro(pEjecutaOldCobro);
            } else if (intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA) {
                initVariablesCajaElectronica();
            } else if (intTipoCobro == ConstantsCaja.COBRO_RECAUDACION) {
                initVariablesRecaudacion();
            }
        }
    }

  private void btnMonto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMontoPagado);
  }

  public void txtMontoPagado_keyPressed(KeyEvent e)
  {
    if(pEjecutaOldCobro||e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        obtieneDatosFormaPagoPedidoSoles();
        adicionaDetallePago(txtMontoPagado);
        if(txtMontoPagadoDolares.isEditable()){
            FarmaUtility.moveFocus(txtMontoPagadoDolares);    
        }else if(!txtMontoPagadoDolares.isEditable() && txtMontoTarjeta.isEnabled()){
            if (VariablesCaja.vIndDatosTarjeta) {
                FarmaUtility.moveFocus(txtMontoPagado);
            } else {
                txtMontoTarjeta.setText(calcularRestoParaTarjeta());
                FarmaUtility.moveFocus(txtMontoTarjeta);
            }
        }else if(!txtMontoPagadoDolares.isEditable() && !txtMontoTarjeta.isEnabled()){
            FarmaUtility.moveFocus(txtMontoPagado);
        }
    } else
        chkkeyPressed(e);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }


// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************

    private void chkkeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            //CHUANES 14.03.2014 //Verificamos la ruta y el acceso ala impresora correspondiente a imprimir 
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF){
                //1.Recupera comprobantes por convenio
                String[] comp = UtilityConvenioBTLMF.getComprobantesConvenio(this);
                //2.Itera por cada documento
                for(String pTipoComp : comp){                    
                    if(!UtilityCaja.verificaImpresora(this,null, pTipoComp)) return ;
                }
            }else if(!UtilityCaja.verificaImpresora(this,null, VariablesModuloVentas.vTip_Comp_Ped)){
                return ;
            }
			log.debug("-----PRESIONO [F11]-----");
            funcionF11();            
			log.debug("-----TERMINO PROCESO [F11]-----");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(intTipoCobro == ConstantsCaja.COBRO_PEDIDO){
                eventoEscape();
            }else if(intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA){
                cerrarVentana(false,null);
            }else if(intTipoCobro == ConstantsCaja.COBRO_RECAUDACION){
                cerrarVentanaRecau();
            }
        }
    }

  private void cerrarVentana(boolean pAceptar,DlgFormaPago pDlg)
  {
    FarmaVariables.vAceptar = pAceptar;
    VariablesCaja.vNumPedVta = "";
    if(pAceptar) {
        log.info("cerrar el Nuevo Cobro ...");
        if(pDlg!=null){
            log.info("cerrar DLgFormaPago ...");
            pDlg.cerrarVentana(pAceptar);
        }
    }
    this.setVisible(false);
    this.dispose();
  }


// **************************************************************************
// Metodos de l�gica de negocio
// **************************************************************************

  private void limpiarDatos(){
    lblTipoComprobante.setText("");
    lblRazSoc.setText("");
    
    lblSolesTotalVenta.setText("");
    
    txtMontoPagado.setText("");
    lblSaldo.setText("0.00");
    lblVuelto.setText("0.00");
    VariablesCaja.vIndPedidoSeleccionado = "N";
    VariablesCaja.vIndTotalPedidoCubierto = false;
    VariablesCaja.vIndPedidoCobrado = false;
    lblMensaje.setVisible(false);
    lblMsjNumRecarga.setVisible(false);
    VariablesCaja.vIndPedidoConProdVirtual = false;
    VariablesCaja.vIndPedidoConvenio = "";
    VariablesCaja.vValMontoPagadoTarj = "";
        
    VariablesConvenio.vCodCliente = "";
    VariablesConvenio.vCodConvenio = "" ;
    VariablesConvenio.vValCredDis = 0.00;
    
    VariablesCaja.cobro_Pedido_Conv_Credito = "N";
    VariablesCaja.valorCredito_de_PedActual = 0.0; 
    
    VariablesCaja.monto_forma_credito_ingresado = "0.00";
    VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
    VariablesCaja.usoConvenioCredito = "";
    VariablesConvenio.vIndSoloCredito = "" ;
    
    
    VariablesCaja.vValEfectivo="";
    VariablesCaja.vVuelto="";
      VariablesCaja.vNumCompBoletaFactura_Impr = "";
  }

  private void limpiarPagos(){
  
  tableModelDetallePago.clearTable();
  lblSaldo.setText(lblSolesTotalVenta.getText().trim());
  lblVuelto.setText("0.00");
  VariablesCaja.vIndTotalPedidoCubierto = false;
  VariablesCaja.vIndPedidoCobrado = false;
  
   if(!indBorra){
    cargaDetalleFormaPago(VariablesCaja.vNumPedVta);
      
     ArrayList array = new ArrayList();
     String codsel="",codobt="";
     obtieneDetalleFormaPagoPedido(array,VariablesCaja.vNumPedVta);
     
      if(array.size()>0 && !indBorra){
        
        for (int j = 0; j < array.size(); j++){
          codsel=(((String) ((ArrayList) array.get(j)).get(0)).trim());     
          for (int i = 0; i < tableModelDetallePago.getRowCount(); i++){
            codobt=((String) tableModelDetallePago.getValueAt(i,0)).trim();
            
             if(!codobt.equalsIgnoreCase(codsel)){
               tableModelDetallePago.deleteRow(i);
               
             }
          }
        }
      }else{
       tableModelDetallePago.clearTable();
      }
    verificaMontoPagadoPedido();
    indBorra=false;
   }
   
  }

  public void adicionaDetallePago(JTextField txtMonto)
  {
     
    //Verifica si es igual a 0, negativo o no sea numero  
    if(!validaMontoIngresado(txtMonto))
        return;
     
    if(VariablesCaja.vIndTarjetaSeleccionada && !VariablesCaja.vIndDatosTarjeta)
    {
     
      FarmaUtility.showMessage(this,"La forma de pago requiere datos de la tarjeta. Verifique!!!", txtMonto);
      return;
    }
    //TODO Revisar
    /*if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
    {
      FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMonto);
      return;
    }

    if(VariablesCaja.vIndTarjetaSeleccionada && FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) > VariablesCaja.vMontoMaxPagoTarjeta)
    {
      FarmaUtility.showMessage(this,"El monto ingresado no puede ser mayor al saldo del Pedido. Verifique!!!", txtMonto);
      return;
    }*/
    //Aqui buscara la forma de pago para ser eliminada
    if(!validaCodigoFormaPago())
    {
        eliminarFormaPagoTabla(VariablesCaja.vCodFormaPago);
    }
    operaListaDetallePago();
    verificaMontoPagadoPedido();
  }
  
  
    public void addTarjetasPago(ArrayList vListaTarjetas)
    {
       vFormaPagoTarjetas = (ArrayList)vListaTarjetas.clone();
    }

  private void obtieneDatosFormaPagoPedidoSoles()
  {
    VariablesCaja.vValEfectivo= txtMontoPagado.getText().trim();
    
    VariablesCaja.vCodFormaPago = ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES;
    VariablesCaja.vDescFormaPago = ConstantsCaja.DESC_FORMA_PAGO_SOLES;
    VariablesCaja.vCantidadCupon = "0";
    
    VariablesCaja.vCodMonedaPago = ConstantsCaja.EFECTIVO_SOLES;
    VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, VariablesCaja.vCodMonedaPago);
    VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMontoPagado.getText().trim()));
    if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_DOLARES)){
      VariablesCaja.vValTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) / 
                                                                VariablesRecaudacion.vTipoCambioCompra);
    }else{
        VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
    }
    VariablesCaja.vNumTarjCred = "";
    VariablesCaja.vFecVencTarjCred = "";      
    VariablesCaja.vNomCliTarjCred = "";
    VariablesCaja.vDNITarj = "";    
    //VariablesCaja.vCodVoucher = "";
    //VariablesCaja.vCodLote = "";
      
      lblMontoEnSoles1.setText(VariablesCaja.vValTotalPagado);
  }

  private void operaListaDetallePago()
  {
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCaja.vCodFormaPago);
    myArray.add(VariablesCaja.vDescFormaPago);
    myArray.add(VariablesCaja.vCantidadCupon);
    myArray.add(VariablesCaja.vDescMonedaPago);
    myArray.add(VariablesCaja.vValMontoPagado);
    myArray.add(VariablesCaja.vValTotalPagado);
    myArray.add(VariablesCaja.vCodMonedaPago);
    myArray.add("0.00");//VUELTO
    myArray.add(VariablesCaja.vNumTarjCred);
    
    myArray.add(VariablesCaja.vFecVencTarjCred);
    
    myArray.add(VariablesCaja.vNomCliTarjCred);
    myArray.add("");
    
    myArray.add(VariablesCaja.vDNITarj);    
    myArray.add(VariablesCaja.vCodVoucher);
    myArray.add(VariablesCaja.vCodLote);

    tableModelDetallePago.data.add(myArray);
    tableModelDetallePago.fireTableDataChanged();

  }

  private boolean validaMontoIngresado(JTextField txtMonto)
  {
    String monto = txtMonto.getText().trim();
    if(monto.equalsIgnoreCase("") || monto.length() <= 0)
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar.", txtMonto);
      txtMonto.setText("0.00");
      return false;
    }
    //Se cambia la condicion de <= a < Luigy Terrazos
    if(FarmaUtility.getDecimalNumber(monto) < 0)
    {
      FarmaUtility.showMessage(this, "Ingrese monto a pagar mayor a 0.", txtMonto);
      txtMonto.setText("0.00");
      return false;
    }
    if(FarmaUtility.getDecimalNumber(monto) == 0)
    {
        //Cuando es 0 busca en la tabla si ya esta registrada para eliminarla y recalcular
        eliminarFormaPagoTabla(VariablesCaja.vCodFormaPago);
        verificaMontoPagadoPedido();
        txtMonto.setText("0.00");
        return false;
    }    
    return true;
  }

  private void verificaMontoPagadoPedido()
  {
    
    double montoTotal = 0;
    double montoFormaPago = 0;
    
    for(int i=0; i<tableModelDetallePago.getRowCount(); i++)
    {
      montoFormaPago = FarmaUtility.getDecimalNumber(((String)tableModelDetallePago.getValueAt(i,5)).trim());
      montoTotal = montoTotal + montoFormaPago;
    }
    	
    if( FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) > montoTotal ){
      
      VariablesCaja.vIndTotalPedidoCubierto = false;
      VariablesCaja.vSaldoPedido = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar) - montoTotal);
      VariablesCaja.vValVueltoPedido = "0.00";
    } else{
      
      VariablesCaja.vIndTotalPedidoCubierto = true;
      VariablesCaja.vSaldoPedido = "0.00";      
    if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_SOLES)){
    
      VariablesCaja.vValVueltoPedido = FarmaUtility.formatNumber(montoTotal - FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar));
      
    }else{            
            //Logica para Recaudacion 
            double dSumMontoTotal = 0.00;
            double montoTotalS = 0;
            double montoFormaPagoS = 0;
            double montoTotalD = 0;
            double montoFormaPagoD = 0;
        
            for(int i=0; i<tableModelDetallePago.getRowCount(); i++)
            {
                String codMoneda =  tableModelDetallePago.getValueAt(i, 6).toString();
                
                if(ConstantsCaja.EFECTIVO_DOLARES.equals(codMoneda)){
                    montoFormaPagoD = FarmaUtility.getDecimalNumber(((String)tableModelDetallePago.getValueAt(i,5)).trim());
                }else{
                    montoFormaPagoS = FarmaUtility.getDecimalNumber(((String)tableModelDetallePago.getValueAt(i,5)).trim());
                }                
            }
        if (montoFormaPagoD > 0){
            montoTotalD = montoFormaPagoD - FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
            if (montoTotalD > 0){
               montoTotalD  = montoTotalD * FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido);
               dSumMontoTotal = dSumMontoTotal + montoTotalD;
            }
        }
        if (montoFormaPagoS > 0){
            montoTotalS = (montoFormaPagoS + montoFormaPagoD) - FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar);
            if (montoTotalS > 0){
               montoTotalS  = montoTotalS * VariablesRecaudacion.vTipoCambioCompra;
               dSumMontoTotal = dSumMontoTotal + montoTotalS;
            }
        }
        
            VariablesCaja.vValVueltoPedido = FarmaUtility.formatNumber(dSumMontoTotal);            
        }
    }
    
    lblSaldo.setText(VariablesCaja.vSaldoPedido);
    lblVuelto.setText(VariablesCaja.vValVueltoPedido);
  }

  private boolean validaCodigoFormaPago()
  {
    if(tableModelDetallePago.getRowCount() <= 0) return true;
    
    for(int i=0; i<tableModelDetallePago.getRowCount(); i++)
    {
      String codTmp = ((String)tableModelDetallePago.getValueAt(i,0)).trim();
      if(VariablesCaja.vCodFormaPago.equalsIgnoreCase(codTmp)) return false;
    }
    return true;
  }

  private void limpiaVariablesFormaPago()
  {
    VariablesCaja.vCodFormaPago = "";
    VariablesCaja.vDescFormaPago = "";
    VariablesCaja.vDescMonedaPago = "";
    VariablesCaja.vValMontoPagado = "";
    VariablesCaja.vValMontoCredito = "";
    VariablesCaja.vValTotalPagado = "";
    
  }

  private void anularAcumuladoCanje(){
      try{
          
          String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
          
          boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                 (
                                  pIndLineaMatriz, VariablesModuloVentas.vNum_Ped_Vta,this,
                                  ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                  txtMontoPagado,
                                  FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                  );
          
          
          if (!pRspCampanaAcumulad)
            {
          
              FarmaUtility.liberarTransaccion();
              FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);
            }          
          
          FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
          FarmaUtility.aceptarTransaccion();
          
          cerrarVentana(false,null);
      } catch(Exception sql){
           FarmaUtility.liberarTransaccion();
		   FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);           
           
      }finally{
          FarmaConnectionRemoto.closeConnection();
      }
  }


  public void setIndPedirLogueo(boolean pValor)
  {
    this.indPedirLogueo = pValor;
  }

  public void setIndPantallaCerrarAnularPed(boolean pValor)
  {
    this.indCerrarPantallaAnularPed = pValor;
  }

  public void setIndPantallaCerrarCobrarPed(boolean pValor)
  {
    this.indCerrarPantallaCobrarPed = pValor;
  }

  
  private void colocaFormaPagoDeliveryAutomatico(String pNumPedido)
  {
    try
    {
      DBDelivery.cargaFormaPagoPedidoDelAutomatico(tableModelDetallePago.data, pNumPedido);
      tableModelDetallePago.fireTableDataChanged();
    } catch(SQLException ex)
    {
     
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), txtMontoPagado);
    }
  }
  
  private void colocaFormaPagoPedidoConvenio(String pNumPedido)
  {
    try
    {
      DBCaja.cargaFormaPagoPedidoConvenio(tableModelDetallePago.data, pNumPedido);
    } catch(SQLException ex)
    {
      log.error(null,ex);
      FarmaUtility.showMessage(this,"Error al obtener forma de pago delivery automatico.\n" + ex.getMessage(), txtMontoPagado);
    }
  }
  
  private int cantidadProductosVirtualesPedido(String pNumPedido)
  {
    int cant = 0;
    try
    {
      cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
    } catch(SQLException ex)
    {
      
      log.error(null,ex);
      cant = 0;
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), txtMontoPagado);
    }
    return cant;
  }

    private void evaluaPedidoProdVirtual(String pNumPedido) {
        int cantProdVirtualesPed = 0;
        String tipoProd = "";
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if (cantProdVirtualesPed <= 0) {
            lblMensaje.setText("");
            lblMsjNumRecarga.setText("");
            lblMensaje.setVisible(false);
            lblMsjNumRecarga.setVisible(false);
            VariablesCaja.vIndPedidoConProdVirtual = false;
        } else {

            tipoProd = obtieneTipoProductoVirtual(pNumPedido);
            if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_TARJETA)) {
                lblMensaje.setText("<html>El pedido contiene una Tarjeta Virtual. <br>" +
                                   "Si lo cobra, No podr� ser anulado.</html>");
                lblMsjNumRecarga.setText("");
            } else if (tipoProd.equalsIgnoreCase(ConstantsModuloVenta.TIPO_PROD_VIRTUAL_RECARGA)) {
                //ERIOS 15.11.2013 Se elimina el mensaje de minutos para anular [time_max(pNumPedido)]
                lblMensaje.setText("<html>Recarga Virtual.<br>" + " Telefono: </html>");
                lblMsjNumRecarga.setText("" + num_telefono(pNumPedido));
            } else {
                lblMensaje.setText("");
                lblMsjNumRecarga.setText("");
            }
            lblMensaje.setVisible(true);
            lblMsjNumRecarga.setVisible(true);

            VariablesCaja.vIndPedidoConProdVirtual = true;
        }

    }
  
  private void limpiaVariablesVirtuales()
  {
    VariablesVirtual.vCodigoComercio = "";
    VariablesVirtual.vTipoTarjeta = "";
    VariablesVirtual.vMonto = "";
    VariablesVirtual.vNumTerminal = "";
    VariablesVirtual.vNumSerie = "";
    VariablesVirtual.vNumTrace = "";
    VariablesVirtual.vIPHost = "";
    VariablesVirtual.vPuertoHost = "";
    VariablesVirtual.vNumeroCelular = "";
    VariablesVirtual.vCodigoProv = "";
    VariablesVirtual.vCodigoAprobacion = "";
    VariablesVirtual.vNumeroTarjeta = "";
    VariablesVirtual.vNumeroPin = "";
    VariablesVirtual.vCodigoRespuesta = "";
    VariablesVirtual.vDescripcionRespuesta = "";
    VariablesVirtual.vArrayList_InfoProdVirtual.clear();
    
    VariablesVirtual.vNumTraceOriginal = "";
    VariablesVirtual.vCodAprobacionOriginal = "";
    VariablesVirtual.vFechaTX = "";
    VariablesVirtual.vHoraTX = "";
  }
  
  private String obtieneTipoProductoVirtual(String pNumPedido)
  {
    String tipoProd = "";
    try
    {
      tipoProd = DBCaja.obtieneTipoProductoVirtualPedido(pNumPedido);
    } catch(SQLException ex)
    {
      
      log.error(null,ex);
      tipoProd = "";
      FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), txtMontoPagado);
    }
    return tipoProd;
  }

  private void txtMontoPagado_mouseClicked(MouseEvent e)
  {
    FarmaUtility.showMessage(this,"No puedes usar el mouse en caja. Realice un uso adecuado del sistema",txtMontoPagado);
    indBorra=true;
    limpiarPagos();
    limpiarDatos();  
    limpiaVariablesFormaPago();
  }

  private String  isConvenioCredito(String codConvenio)
  {
    String indCredito ="";
    try
    {
      indCredito = DBCaja.verifica_Credito_Convenio(codConvenio.trim());
    }catch(SQLException sql)
    {
      
      log.error(null,sql);
      FarmaUtility.showMessage(this,"Error en Obntener si da Credito el  Convenio.",null);
      FarmaUtility.moveFocus(txtMontoPagado);
    }
    
    return indCredito;
   }   


 public void initVariables_Auxiliares()
 {
     VariablesFidelizacion.vRecalculaAhorroPedido = false; 
    
    VariablesCaja.cobro_Pedido_Conv_Credito     = "N";
    VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";  
    
    VariablesCaja.arrayPedidoDelivery = new ArrayList();
    VariablesCaja.usoConvenioCredito = "";
    VariablesCaja.valorCredito_de_PedActual = 0.0;
    VariablesCaja.monto_forma_credito_ingresado = "0.00";  
 }

  public String num_telefono(String numPed)
  {
    String num_telefono = "";
    try
      {
         num_telefono = DBCaja.getNumeroRecarga(numPed);
      
      }catch(SQLException e)
      {
        
      log.error(null,e);
        FarmaUtility.showMessage(this,"Ocurrio un error al obtener el numero de telefono de recarga.\n" + e.getMessage(),null);
      }
     return num_telefono;     
  }

  private void cargaDetalleFormaPago(String NumPed){
   ArrayList array = new ArrayList();
   ArrayList myArray = new ArrayList();
   obtieneDetalleFormaPagoPedido(array,NumPed);
   
    if(array.size()>0){
   
     for (int j = 0; j < array.size(); j++){
       myArray.add(((String) ((ArrayList) array.get(j)).get(0)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(1)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(2)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(3)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(4)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(5)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(6)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(7)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(8)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(9)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(10)).trim());      
       myArray.add(((String) ((ArrayList) array.get(j)).get(11)).trim());      
         
       myArray.add("");
       myArray.add("");
       myArray.add("");

       tableModelDetallePago.data.add(myArray);
       tableModelDetallePago.fireTableDataChanged();
     }       
    }
   verificaMontoPagadoPedido();
  }

  private void obtieneDetalleFormaPagoPedido(ArrayList array,String vtaNumPed){
  
   array.clear();
      /*try
        {
         DBCaja.getDetalleFormaPagoCampa�a(array,vtaNumPed);
        }catch(SQLException e)
        {
        
        log.error(null,e);
          FarmaUtility.showMessage(this,"Ocurrio un error al obtener detalle forma pago del pedido.\n" + e.getMessage(),null);
        }*/
  }

  public void pedidoCobrado(DlgFormaPago pDialogo){
      
	if(VariablesCaja.vIndPedidoCobrado){
		log.info("pedido cobrado !");
	    if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
	    		indCerrarPantallaCobrarPed ) {
                
                    if(validaIngresoSobre()){
                        
                        if(JConfirmDialog.rptaConfirmDialog(this, "Ha excedido el importe m�ximo de dinero en su caja. \n" + 
                                                                "Desea hacer entrega de un nuevo sobre?\n")){
                            mostrarIngresoSobres(); 
                        }
                    }
	    	cerrarVentana(true,pDialogo);
	    }
	    indBorra = true;
	    limpiarDatos();
	    limpiarPagos();
	    limpiaVariablesVirtuales();
	    FarmaUtility.moveFocus(txtMontoPagado);
	}
  }
  
    public void eventoEscape()
    {
        log.warn("Ingreso al metodo eventoEscape()");
        if(!VariablesCaja.vNumPedVta.equalsIgnoreCase(""))
        {
            VariablesCaja.vPermiteCampa�a=UtilityCaja.verificaPedidoCamp(this,VariablesCaja.vNumPedVta);
            if(VariablesCaja.vPermiteCampa�a.trim().equalsIgnoreCase("S"))
            {   UtilityCaja.actualizaPedidoCupon(this,"",VariablesCaja.vNumPedVta,"N","S");
            }
        }
		
		obligarAnularTransaccionPinpad();
		
        indBorra=false;
        if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL))
        {
            if(indCerrarPantallaAnularPed && VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                //Se anulara el pedido 
                if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {
                    if(JConfirmDialog.rptaConfirmDialog(this, "La venta ser� anulada. �Desea continuar?"))
                    {
                        try
                        {   //ERIOS 11.10.2013 Nuevo modelo cobro
                            if(VariablesCaja.vIndPedidoCobrado)
                            {   UtilityCaja.anularPedidoNuevoCobro(myParentFrame, this, lblVuelto, tblDetallePago, txtMontoPagado);
                            }
                            else
                            {   DBCaja.anularPedidoPendienteSinRespaldo_02(VariablesCaja.vNumPedVta); 
                            }
                                                                  
                            FarmaUtility.aceptarTransaccion();
                            log.info("Pedido anulado.");
                            FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                            cerrarVentana(true,null);
                        }
                        catch(SQLException sql)
                        {
                            FarmaUtility.liberarTransaccion();
                            //log.error("",sql);
                            log.error(null,sql);
                            if(sql.getErrorCode()==20002)
                                FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                            else if(sql.getErrorCode()==20003)
                                FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                            else    
                                FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                            cerrarVentana(true,null);
                        }
                    }
                    else
                    {   return;
                    }
                }
                else
                {          
                    try
                    {            
                        UtilityCaja.liberaProdRegalo(VariablesCaja.vNumPedVta,
                                                    ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                    FarmaConstants.INDICADOR_S);

                        //ERIOS 11.10.2013 Nuevo modelo cobro
                        if(VariablesCaja.vIndPedidoCobrado)
                        {
                            if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, "La venta ser� anulada. �Desea continuar?"))
                            {
                                UtilityCaja.anularPedidoNuevoCobro(myParentFrame, this, lblVuelto, tblDetallePago, txtMontoPagado); 
                            }
                            else
                                return;
                        }
                        else
                        {   DBCaja.anularPedidoPendienteSinRespaldo_02(VariablesCaja.vNumPedVta);
                        }
                
                        String pIndLineaMatriz = FarmaConstants.INDICADOR_N;
                        
                        boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada(pIndLineaMatriz,
                                                                                                VariablesCaja.vNumPedVta,
                                                                                                this,
                                                                                                ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                                                                txtMontoPagado,
                                                                                                FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                                                                                );

                        if (!pRspCampanaAcumulad)
                        {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                            FarmaConstants.INDICADOR_S);
                        }          
                        if(pIndLineaMatriz.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        {   FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                FarmaConstants.INDICADOR_S);
                        }

                        FarmaUtility.aceptarTransaccion();
                        log.info("Pedido anulado sin quitar respaldo.");
                        cerrarVentana(false,null);
                    }
                    catch(SQLException sql)
                    {
                        FarmaUtility.liberarTransaccion();
                        
                        log.error(null,sql);
                        if(sql.getErrorCode()==20002)
                            FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                        else if(sql.getErrorCode()==20003)
                            FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null);
                        else    
                            FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                        cerrarVentana(true,null);
                    }
                }
            }
            else
                cerrarVentana(false,null);
        }
        else
            cerrarVentana(false,null);      
    }

  private boolean validaIngresoSobre() {
    boolean valor=false;
    String ind="";
    try
         {
         
          ind=DBCaja.permiteIngreSobre(VariablesCaja.vSecMovCaja);
             
          if(ind.trim().equalsIgnoreCase("S")){
           valor=true;
           }
          
         }catch (SQLException sql){
           valor=false;
           log.error("",sql);
           FarmaUtility.showMessage(this,"Ocurrio un error validar ingreso de sobre.\n"+sql.getMessage(),null);
         }
    return valor;
  }

    public boolean existeStockPedido(String pNumPedido) 
    {
        
        boolean pRes = false;
        String pCadena = "N";

        try {
            pCadena = DBCaja.getPermiteCobrarPedido(pNumPedido);
        
        } catch (SQLException e) {
            pCadena = "N";
        
            log.error("",e);
        }
        
        if(pCadena.trim().equalsIgnoreCase("S")) pRes = true;
        
        return pRes;
    }

//Metodos

    private void validaInfoPedido(ArrayList pArrayList)
    {
      if(pArrayList.size() < 1)
      {
        FarmaUtility.showMessage(this, "El Pedido No existe o No se encuentra pendiente de pago", txtMontoPagado);
        VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
        limpiarDatos();
        limpiarPagos();
        return;
      } else if(pArrayList.size() > 1)
      {
        FarmaUtility.showMessage(this, "Se encontro mas de un pedido.\n" +
          "Ponganse en contacto con el area de Sistemas.", txtMontoPagado);
        VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
        limpiarDatos();
        limpiarPagos();
        return;
      } else
      {
          
          if (intTipoCobro == ConstantsCaja.COBRO_PEDIDO) {
              limpiarPagos();
              limpiaVariablesFormaPago();
              VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_S;
              muestraInfoPedido(pArrayList);
              if(tblDetallePago.getRowCount()<1){
                cargaDetalleFormaPago(VariablesCaja.vNumPedVta);        
              }
              verificaMontoPagadoPedido();
          } else if (intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA) {
              VariablesCaja.vCodFormaPago = "";
              VariablesCaja.vDescFormaPago = "";
              VariablesCaja.vDescMonedaPago = "";
              VariablesCaja.vValMontoPagado = "";
              VariablesCaja.vValTotalPagado = "";
              
              muestraInfoPedido(pArrayList);
          } else if (intTipoCobro == ConstantsCaja.COBRO_RECAUDACION) {
              VariablesCaja.vCodFormaPago = "";
              VariablesCaja.vDescFormaPago = "";
              VariablesCaja.vDescMonedaPago = "";
              VariablesCaja.vValMontoPagado = "";
              VariablesCaja.vValTotalPagado = "";
              
              muestraInfoPedidoRecau(pArrayList);              
          }
      }
    }
    
    private void muestraInfoPedido(ArrayList pArrayList)
    {
        VariablesCaja.vNumPedVta = ((String)((ArrayList)pArrayList.get(0)).get(0)).trim();
        if (intTipoCobro == ConstantsCaja.COBRO_PEDIDO)
        {
            if(!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_PENDIENTE, txtMontoPagado))
            {
                VariablesCaja.vIndPedidoSeleccionado = FarmaConstants.INDICADOR_N;
                return;
            }
        }
        else if (intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA)
        {
            if(!UtilityCaja.verificaEstadoPedido(this, VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO, txtMontoPagado))
            {
                return;
            }
        }
        FarmaUtility.liberarTransaccion();
      
        VariablesCaja.vValTotalPagar = ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
        lblSolesTotalVenta.setText(VariablesCaja.vValTotalPagar);     
        
        VariablesCaja.vValTipoCambioPedido = ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
        lblTipoCambio2.setText(VariablesCaja.vValTipoCambioPedido);
        lblTipoCambio1.setText(VariablesRecaudacion.vTipoCambioCompra+"");
        
        //ERIOS 22.01.2014 Calcula totales a pagar
        calculaTotalPagar(VariablesCaja.vValTotalPagar);

        VariablesModuloVentas.vTip_Comp_Ped = ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
        lblTipoComprobante.setText(((String)((ArrayList)pArrayList.get(0)).get(5)).trim());
        VariablesModuloVentas.vNom_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(6)).trim();
        VariablesModuloVentas.vRuc_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(7)).trim();
        VariablesModuloVentas.vDir_Cli_Ped = ((String)((ArrayList)pArrayList.get(0)).get(8)).trim();
        VariablesModuloVentas.vTipoPedido =  ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();

        if(VariablesModuloVentas.vTip_Comp_Ped.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_FACTURA))
            lblRazSoc_T.setText("Razon Social :");
        else
            lblRazSoc_T.setText("Cliente :");

        lblRazSoc.setText(VariablesModuloVentas.vNom_Cli_Ped);
        lblSaldo.setText(VariablesCaja.vValTotalPagar);
        VariablesCaja.vIndDistrGratuita = ((String)((ArrayList)pArrayList.get(0)).get(11)).trim();
        VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        VariablesModuloVentas.vCant_Items_Ped = ((String)((ArrayList)pArrayList.get(0)).get(13)).trim();
        //indicador de Convenio
        VariablesCaja.vIndPedidoConvenio = ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
        VariablesConvenio.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
        VariablesConvenio.vCodCliente = ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
        evaluaPedidoProdVirtual(VariablesCaja.vNumPedVta);
      
        if(VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
            FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar.trim()) <= 0 )
        {
            VariablesCaja.vIndTotalPedidoCubierto = true;
        }
        else
        {
            VariablesCaja.vIndTotalPedidoCubierto = false;
        }
        if(VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            log.info("Es Pedido Delivery");
            colocaFormaPagoDeliveryAutomatico(VariablesCaja.vNumPedVta);
            verificaMontoPagadoPedido();
        }
        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            //Agregado Por FRAMIREZ 16.02.2012
            if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF)
            {
                //VariablesConvenioBTLMF.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
                if(((ArrayList)pArrayList.get(0)).get(16) != null)
                {
                    VariablesConvenioBTLMF.vCodCliente = ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
                }
                //COLOCAR COMPROBANTES A IMPRIMIR
                if(VariablesConvenioBTLMF.vCodConvenio.trim().length()>0)
                {
                    lblTipoComprobante.setText(getMensajeComprobanteConvenio(VariablesConvenioBTLMF.vCodConvenio.trim()));
                }
                adicionaDetallePagoCredito();
            }
            else          
                if(!VariablesCaja.vIndDeliveryAutomatico.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                {
                    log.info("Es Pedido Convenio");
                    colocaFormaPagoPedidoConvenio(VariablesCaja.vNumPedVta);
                    carga_Credito_Convenio();
                    verificaMontoPagadoPedido();
                }
        }
        cargarFormaPago();
    }

    private void btnMontoDolares_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMontoPagadoDolares);
    }

    public void txtMontoPagadoDolares_keyPressed(KeyEvent e) {
        if (pEjecutaOldCobro||e.getKeyCode() == KeyEvent.VK_ENTER) {
            obtieneDatosFormaPagoPedidoDolares();
            adicionaDetallePago(txtMontoPagadoDolares);
            if(txtMontoTarjeta.isEnabled()){
                if (VariablesCaja.vIndDatosTarjeta) {
                    FarmaUtility.moveFocus(txtMontoPagado);
                } else {
                    txtMontoTarjeta.setText(calcularRestoParaTarjeta());
                    FarmaUtility.moveFocus(txtMontoTarjeta);
                }  
            }else if(!txtMontoTarjeta.isEnabled() && txtMontoPagado.isEditable()){
                FarmaUtility.moveFocus(txtMontoPagado);
            }else if(!txtMontoTarjeta.isEnabled() && !txtMontoPagado.isEditable()){
                FarmaUtility.moveFocus(txtMontoPagadoDolares);
            }
        } else
            chkkeyPressed(e);
    }
    
    private void obtieneDatosFormaPagoPedidoDolares()
    {
      VariablesCaja.vValEfectivo= txtMontoPagadoDolares.getText().trim();
      
      VariablesCaja.vCodFormaPago = ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES;
      VariablesCaja.vDescFormaPago = ConstantsCaja.DESC_FORMA_PAGO_DOLARES;
      VariablesCaja.vCantidadCupon = "0";
      
      VariablesCaja.vCodMonedaPago = ConstantsCaja.EFECTIVO_DOLARES;
      VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, VariablesCaja.vCodMonedaPago);
      
      VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText().trim()));
      
      if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_SOLES)){
        VariablesCaja.vValTotalPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado) * 
                                                                    FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido));
      }else{
          VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
      }
      
        lblMontoEnSoles2.setText(VariablesCaja.vValTotalPagado);
    }
    
    private void carga_Credito_Convenio(){
    
    //indicador si es credito convenio
    String indCredConvenio = isConvenioCredito(VariablesConvenio.vCodConvenio);
    
    if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){
            
            if(indCredConvenio.equalsIgnoreCase("S")) {
            
                    //verificar si hay linea con matriz
                 VariablesCaja.vIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                 FarmaConstants.INDICADOR_S).trim();
                 String credito_actual = "0";
                 if(VariablesCaja.vIndLinea.equals(FarmaConstants.INDICADOR_S)){
                         //Se obtiene el porcentaje de la forma de pago
                         VariablesConvenio.vPorcCoPago = obtenerPorcentajeConPago().trim();
                         //si hay linea con matriz
                         //Verirficamos si Tiene Saldo el Cliente..este es el que invocar a un DBLINK CORREGIR a matriz
                         credito_actual = existsCreditoDisponible(VariablesConvenio.vCodCliente,VariablesConvenio.vCodConvenio);
                         double cred_actual   = FarmaUtility.getDecimalNumber(credito_actual.trim());
                         //es el campo de la Tabla Temporal
                         double saldo_grabado_credito = FarmaUtility.getDecimalNumber(tblDetallePago.getValueAt(0, 5).toString().trim());
                         
                         if(cred_actual > 0){
                                 
                            VariablesCaja.valorCredito_de_PedActual = saldo_grabado_credito;      
                             
                             //Muestra los dato del convenio en pantalla
                             pnlCopago.setVisible(true);
                             lblPorcCopago.setText(VariablesConvenio.vPorcCoPago);
                             lblValCopago.setText(FarmaUtility.formatNumber(VariablesCaja.valorCredito_de_PedActual));
                             
                             String vValTotalPagarPantalla = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)-VariablesCaja.valorCredito_de_PedActual);
                             calculaTotalPagar(vValTotalPagarPantalla);
                             
                             if(!validaMontoCredito_Convenio()) return ; 
                             
                             FarmaUtility.moveFocus(txtMontoPagado);
                                 
                         } else {
                                 FarmaUtility.showMessage(this, "El cliente no tiene saldo disponible para la forma de pago por convenio.", txtMontoPagado);
                                 
                         }
             }else{
                     FarmaUtility.showMessage(this, "Error: En este momento no hay linea con matriz.\nNo podra usar la forma de pago por convenio\n" +
                         "Si el problema persiste comunicarse con el operador de sistema", txtMontoPagado);
             }
             
         } else {            
             FarmaUtility.moveFocus(txtMontoPagado);
         }
       }   
    }

    private String existsCreditoDisponible(String codCliente,String codConvenio){
       String resultado = "";
       try
       {
         resultado = DBCaja.getSaldoCredClienteMatriz(codCliente,codConvenio, FarmaConstants.INDICADOR_S);
         FarmaUtility.aceptarTransaccion();
         log.debug("credito del cliente : "+resultado);
       }catch(SQLException ex)
       {
         FarmaUtility.liberarTransaccion();
        //log.error("",ex);
         log.error(null,ex);
        FarmaUtility.showMessage(this,"Error al Obtener credito disponible del Cliente Actual.\n" + ex.getMessage(), txtMontoPagado);
       }    
       return resultado;
    }    
    
    private boolean validaMontoCredito_Convenio(){
     String rpta = "S";
     String indCredConvenio="";
     if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase("S")){
       indCredConvenio = isConvenioCredito(VariablesConvenio.vCodConvenio);
       
      if(indCredConvenio.equalsIgnoreCase("S"))
        { 

        VariablesCaja.monto_forma_credito_ingresado = ""+ VariablesCaja.valorCredito_de_PedActual;
        VariablesCaja.uso_Credito_Pedido_N_Delivery = "S";            
          
       }
      }
      if(rpta.equalsIgnoreCase("N"))
        return false;
      else
        return true;
    }
    
    private void mostrarIngresoSobres(){
    
        DlgIngresoSobre dlgsobre = new DlgIngresoSobre(myParentFrame,"",true);
        dlgsobre.setVisible(true);
        
        if(FarmaVariables.vAceptar){
             cerrarVentana(true,null); 
        }
    }

    public void txtMontoTarjeta_keyPressed(KeyEvent e,boolean Ejecuta) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER||Ejecuta)
        {
            //ERIOS 2.2.8 Valida monto a pagar
            actualizaFormasPago();
            verificaMontoPagadoPedido();
            
            double tmpMontoTarjeta = 0.0;
            double tmpMontoTotal = 0.0;
            double tmpMontoSaldo = 0.0;
            tmpMontoTarjeta = FarmaUtility.getDecimalNumber(txtMontoTarjeta.getText().trim());
            tmpMontoTotal = FarmaUtility.getDecimalNumber(lblSolesTotalPagar.getText().trim());
            tmpMontoSaldo = FarmaUtility.getDecimalNumber(lblSaldo.getText().trim());
            if(tmpMontoTarjeta > 0)
            {   if(tmpMontoTarjeta != tmpMontoSaldo)
                {   FarmaUtility.showMessage(this,"El monto de tarjeta, debe ser el monto del saldo a pagar.",txtMontoTarjeta);
                }
                else
                {   obtieneDatosFormaPagoPedidoTarjeta();
                    //Si es con POS                    
                    if(VariablesPtoVenta.vIndPinpad.equals("N"))
                    {   DlgDatosTarjeta objTC=new DlgDatosTarjeta(myParentFrame,"",true);
                        objTC.setArrayFormPago(arrayCodFormaPago);
                        objTC.setVisible(true);
                    }
                    else
                    //Si es con Pinpad
                    {   
                        DlgDatosTarjetaPinpad objTC=new DlgDatosTarjetaPinpad(myParentFrame,"",true);
                        objTC.setArrayFormPago(arrayCodFormaPago);
                        objTC.setTableModelFormasPago(tableModelFormasPago);
                        objTC.setDlgNuevoCobro(this);
                        objTC.setVisible(true);
                    }
                    if(FarmaVariables.vAceptar)
                    {   VariablesCaja.vIndDatosTarjeta = true;
                        VariablesCaja.vIndTarjetaSeleccionada = true;
                        adicionaDetallePago(txtMontoTarjeta);
                        lblTipoTrj.setText(VariablesCaja.vDescFormaPago);
                        if( (FarmaUtility.getDecimalNumber(txtMontoTarjeta.getText().trim())) 
                           == (FarmaUtility.getDecimalNumber(lblSolesTotalPagar.getText().trim())))
                        {   txtMontoPagado.setEditable(false);
                            txtMontoPagadoDolares.setEditable(false);
                        }
                        txtMontoTarjeta.setEditable(false);
                        //limpiarVariableTarjeta();
                        
                        funcionF11(); 
                    }
                    else
                    {   
                        limpiarVariableTarjeta();
                        txtMontoTarjeta.setText("0.00");
                    }
                    
                    if(txtMontoPagado.isEditable())
                    {   FarmaUtility.moveFocus(txtMontoPagado); 
                    }
                    else if(txtMontoPagadoDolares.isEditable())
                    {   FarmaUtility.moveFocus(txtMontoPagadoDolares); 
                    }

                }    
            }
        } else
          chkkeyPressed(e);
    }

    public void obtieneDatosFormaPagoPedidoTarjeta()
    {
      VariablesCaja.vValEfectivo= txtMontoTarjeta.getText().trim();
      
      //VariablesCaja.vCodFormaPago = ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES;
      //VariablesCaja.vDescFormaPago = ConstantsCaja.FORMA_PAGO
      VariablesCaja.vCantidadCupon = "0";
      
      VariablesCaja.vCodMonedaPago = ConstantsCaja.EFECTIVO_SOLES;
      VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, VariablesCaja.vCodMonedaPago);
      VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMontoTarjeta.getText().trim()));
      VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;      
    }

    private void btnMontoTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMontoTarjeta);        
    }
    
    /**
    * Se muestra en el cuadro de texto de tarjeta, el resto a pagar por el pedido
    * @author Luigy Terrazos
    * @since 19.03.2013
    */     
    private String calcularRestoParaTarjeta(){
        double dblSaldo = 0.0;
        double tmpDblMonto1 = 0.0;
        double tmpDblMonto2 = 0.0;
        double tmpDblMonto3 = 0.0;
        double tmpDblMonto4 = 0.0;
        tmpDblMonto1 = FarmaUtility.getDecimalNumber(lblSolesTotalPagar.getText());
        tmpDblMonto2 = FarmaUtility.getDecimalNumber(txtMontoPagado.getText());
        tmpDblMonto3 = FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText());
        tmpDblMonto4 = FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido);
        dblSaldo = tmpDblMonto1 - tmpDblMonto2 - (tmpDblMonto3 * tmpDblMonto4);
        if(dblSaldo <= 0){
            dblSaldo = 0.0;
        }
        return FarmaUtility.formatNumber(dblSaldo);
    }

    /**
     * Actualiza las formas de pago al momento de pulsar f11
     * @author Luigy Terrazos
     * @since 19.03.2013
     */     
     private void actualizaFormasPago(){
         obtieneDatosFormaPagoPedidoSoles();
         adicionaDetallePago(txtMontoPagado);
         
         obtieneDatosFormaPagoPedidoDolares();
         adicionaDetallePago(txtMontoPagadoDolares);
     } 
    
    /**
     * Elimina la forma de pago de la tabla
     * @author Luigy Terrazos
     * @since 19.03.2013
     * @param codFormPago Codigo de forma de pago
     */     
     private void eliminarFormaPagoTabla(String codFormPago){
         String codobt = "";
         if(tableModelDetallePago.getRowCount() > 0){
             for (int i = 0; i < tableModelDetallePago.getRowCount(); i++){
                codobt=((String) tableModelDetallePago.getValueAt(i,0)).trim();
                if(codobt.equals(codFormPago)){
                  tableModelDetallePago.deleteRow(i);
                }
             }     
         }
     }
    
    /**
    * Limpia las variable de tarjeta
    * @author Luigy Terrazos
    * @since 19.03.2013
    */  
    private void limpiarVariableTarjeta(){
        //ERIOS 27.01.2014 Limpia indicadores
        VariablesCaja.vIndTarjetaSeleccionada = false;
        VariablesCaja.vIndDatosTarjeta = false;
        VariablesCaja.vNumTarjCred = "";
        VariablesCaja.vDNITarj = "";
        VariablesCaja.vCodVoucher = "";
        VariablesCaja.vCodLote = "";
        VariablesCaja.vFecVencTarjCred = "";
        VariablesCaja.vNomCliTarjCred = "";
    }
    
    private void inicializaVariablesVentana(){
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMontoPagado);
        lblMensaje.setVisible(false);
        lblMsjNumRecarga.setVisible(false);
        pnlCopago.setVisible(false);
          
        VariablesCaja.vIndPedidoConProdVirtual = false;
        //Reinicia Variables
        initVariables_Auxiliares();      
        
        limpiarVariableTarjeta();
        
        //ERIOS 22.11.2013 Muestra moneda a pagar
        if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_DOLARES)){
            lblSaldoT.setText("SALDO :  $");
            lblTotalVenta.setText("Total : $");
            lblTotalPagar.setText("TOTAL A PAGAR : $");
            lblTotConversionT.setText("S/.");
            
            jPanelWhite1.setVisible(true);
            jPanelWhite4.setVisible(false);
        }else{
            jPanelWhite1.setVisible(false);
            jPanelWhite4.setVisible(true);
        }
    }
    
    private void initVariablesCobro(boolean pEjecOldCobro){
        if(indPedirLogueo&&!pEjecOldCobro){
          DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_CAJERO);
          dlgLogin.setVisible(true);
          if ( FarmaVariables.vAceptar ) {
            FarmaVariables.dlgLogin = dlgLogin;
            if(!UtilityCaja.existeCajaUsuarioImpresora(this, null)) cerrarVentana(false,null);
            FarmaVariables.vAceptar = false;
            
          } else  cerrarVentana(false,null);
        } else
        {
          if(!UtilityCaja.existeCajaUsuarioImpresora(this, null) || !UtilityCaja.validaFechaMovimientoCaja(this,txtMontoPagado))
          {
            FarmaUtility.showMessage(this, "El Pedido sera Anulado. Vuelva a generar uno nuevo.", null);
            try{
              DBCaja.anularPedidoPendiente(VariablesModuloVentas.vNum_Ped_Vta);
                
                anularAcumuladoCanje();
                VariablesCaja.vCierreDiaAnul=false;
                
              FarmaUtility.aceptarTransaccion();
              FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
              cerrarVentana(true,null);
              return;
            } catch(SQLException sql)
            {
              FarmaUtility.liberarTransaccion();
              log.error("",sql);
              FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
              cerrarVentana(true,null);
              return;
            }
          }
            //Problemas al generar la venta, cuando la variable tiene contenido.   Autor Luigy Terrazos
            VariablesCaja.vFecPedACobrar = "";
            
            buscaPedidoDiario();
            
            if(VariablesFidelizacion.vSIN_COMISION_X_DNI){
                lblMensaje.setText("DNI Inv�lido. No aplica Prog. Atenci�n al Cliente");
                lblMensaje.setVisible(true);
            }
            
            VariablesCaja.vNumPedPendiente = "";
            FarmaVariables.vAceptar = false;
            FarmaUtility.moveFocus(txtMontoPagado);
            
        } 
    }

    private void initVariablesCajaElectronica(){
            buscaPedidoDiario();
            VariablesCaja.vNumPedPendiente = "";
            FarmaVariables.vAceptar = false;
            FarmaUtility.moveFocus(txtMontoPagado);
    }
    
    private void buscaPedidoDiario() {
        ArrayList myArray = new ArrayList();
        try {
            if (intTipoCobro == ConstantsCaja.COBRO_PEDIDO) {
                VariablesCaja.vFecPedACobrar = "";//Problemas al generar la venta, cuando la variable tiene contenido.   Autor Luigy Terrazos
                DBCaja.obtieneInfoCobrarPedido(myArray, VariablesCaja.vNumPedPendiente, VariablesCaja.vFecPedACobrar);
                validaInfoPedido(myArray);
            } else if (intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA) {
                DBCajaElectronica.obtienePedidoaCambiar(myArray, VariablesCajaElectronica.vNumPedVta, VariablesCaja.vFecPedACobrar);  
                validaInfoPedido(myArray);
            } else if (intTipoCobro == ConstantsCaja.COBRO_RECAUDACION) {
                myArray = facadeRecaudacion.obtenerRcdPend(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, strCodRecau);
                validaInfoPedido(myArray);
            } 
        } catch (SQLException sql) {
            log.error(null, sql);
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Pedido.\n" +
                    sql.getMessage(), txtMontoPagado);
        }
    }
    
    /**
     * Obtiene el porcentaje de la forma de pago
     * @author Luigy Terrazos
     * @since 25.03.2013
     */         
    public String obtenerPorcentajeConPago() {
        String resultado = "";
        try
        {
            resultado = DBConvenio.obtenerPorcentajeConPago(VariablesConvenio.vCodConvenio);
        }
        catch(SQLException ex)
        {
            log.error(null,ex);
        }    
        return resultado;        
    }

    private void cambiarFormaPago() {
        boolean blnIndicador = false;
        try{
            FacadeCajaElectronica facade = new FacadeCajaElectronica();
            blnIndicador = facade.grabarCambioFormaPago(VariablesCaja.vNumPedVta , tableModelDetallePago.data, lblVuelto.getText().trim());
            if(blnIndicador){
                cerrarVentana(blnIndicador,null);
            }else{
                FarmaUtility.showMessage(this, "Error realizar la modificaci�n del pedido.\n", txtMontoPagado);
            }
        }catch(Exception e){
            FarmaUtility.showMessage(this, "Error realizar la modificaci�n del pedido.\n" +
                    e.getMessage(), txtMontoPagado);
        }
    }
    
    private boolean validarFormaPago(){
        if(!validaMontoFormaPago()) return false;
        
        actualizaFormasPago();
        verificaMontoPagadoPedido();

        /* Inicio agregado de GFS por motivo de Recaudacion Prestamos Citibank y Rec. Claro*/        
        //Para el caso de recaudacion no se necesita las validaciones de abajo
        //ERIOS 2.2.8 Se quita funcionalidad para Prestamos Citibank
        if( //ConstantsRecaudacion.TIPO_REC_PRES_CITI.equalsIgnoreCase(tipoRcd) || 
            ConstantsRecaudacion.TIPO_REC_CLARO.equalsIgnoreCase(tipoRcd)){
            return true;
        }

        if (!VariablesCaja.vIndTotalPedidoCubierto){
                FarmaUtility.showMessage(this, "El Pedido tiene saldo pendiente de cobro.Verifique!!!", txtMontoPagado);
                return false;
        }
        if(VariablesCaja.vIndDatosTarjeta == false && (Double.parseDouble(txtMontoTarjeta.getText()) != 0)){
            FarmaUtility.showMessage(this, "Ingrese informacion de la tarjeta.", txtMontoTarjeta);
            return false;
        }
        return true;
    }
    
    private boolean validaMontoFormaPago(){
        
        double dblTotalPagar = 0.0;
        double dblMontoSoles = 0.0;
        double dblMontoDolares = 0.0;
        double dblTipoCambio = 0.0;
        double dblMontoTarjeta = 0.0;
        dblTotalPagar = FarmaUtility.getDecimalNumber(lblSolesTotalPagar.getText());
        
        dblMontoTarjeta = FarmaUtility.getDecimalNumber(txtMontoTarjeta.getText());
        dblTipoCambio = FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido);
        
        if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_SOLES)){
            dblMontoSoles = FarmaUtility.getDecimalNumber(txtMontoPagado.getText());
            dblMontoDolares = FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText());
        }else{
            dblMontoSoles = FarmaUtility.getDecimalNumber(txtMontoPagado.getText())*
                                                                VariablesRecaudacion.vTipoCambioCompra;
            dblMontoDolares = FarmaUtility.getDecimalNumber(txtMontoPagadoDolares.getText());
        }
        
        if(strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_SOLES)){
            //Valida tarjeta,soles,dolares
            if (dblMontoTarjeta == dblTotalPagar) {
                if (dblMontoSoles > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagado);
                    return false;
                } else if (dblMontoDolares > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagadoDolares);
                    return false;
                }
            } else if ((dblMontoTarjeta + dblMontoSoles) >= dblTotalPagar) {
                if (dblMontoDolares > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagadoDolares);
                    return false;
                }
            }
        }else{
            //Para recaudacion en dolares
            if (dblMontoTarjeta == dblTotalPagar) {
                if (dblMontoSoles > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagado);
                    return false;
                } else if (dblMontoDolares > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagadoDolares);
                    return false;
                }
            } else if ((dblMontoTarjeta + dblMontoDolares) >= dblTotalPagar) {
                if ( dblMontoSoles > 0) {
                    FarmaUtility.showMessage(this, 
                                             "Al monto a cobrar ya fue cubierto. No puede ingresar esta forma de pago.", 
                                             txtMontoPagado);
                    return false;
                }
            }
        }
        return true;
    }

    private void txtMontoPagado_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMontoPagado,e);
    }

    private void txtMontoPagadoDolares_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMontoPagadoDolares,e);
    }

    private void txtMontoTarjeta_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMontoTarjeta,e);
    }

    public void setIndTipoCobro(int intTipoCobro) {
        this.intTipoCobro = intTipoCobro;
    }
    
    public void setCodRecau(String strCodRecau){
        this.strCodRecau = strCodRecau;
    }

    public void setArrayCabeRcd(ArrayList<Object> arrayCabRcd){
        tmpArrayCabRcd = arrayCabRcd;
        tipoRcd = arrayCabRcd.get(4).toString();
    }
    
    public void setMontoPagado(String ptotventa){
                txtMontoPagado.setText(ptotventa);
             //   lblSaldo.setText(String.valueOf(Double.parseDouble(lblSolesTotalPagar.getText())-Double.parseDouble(txtMontoPagado.getText())));
    }

    public void setMontoPagadoDolares(String ptotventadolares){
                txtMontoPagadoDolares.setText(ptotventadolares);
               // lblSolesTotalVenta.setText(String.valueOf(ptotventadolares*FarmaVariables.vTipCambio));
               // lblSolesTotalPagar.setText(String.valueOf(ptotventadolares*FarmaVariables.vTipCambio));
    }


    public void setNumTarjeta(String pnumtarjeta){
                lblRazSoc.setText(pnumtarjeta);
    }

    public void cargaFormasPagoSinConvenio(String numPed, String indConvenio, String codConvenio, String codCliente){
        if (VariablesModuloVentas.vNum_Ped_Vta.trim().length() > 0) {
            numPed = VariablesModuloVentas.vNum_Ped_Vta.trim();
        } else if (VariablesCaja.vNumPedVta.trim().length() > 0) {
            numPed = VariablesCaja.vNumPedVta.trim();
        }
        try {
            DBCaja.obtieneFormasPagoSinConvenio(tableModelFormasPago, 
                                                indConvenio, codConvenio, 
                                                codCliente, numPed);            
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Sin Convenio.\n" + sql.getMessage(), null);
        }
    }

    public void cargaFormasPagoConvenio(String numPed, String indConvenio, String codConvenio, String codCliente, String valorCredito){
          try{
            DBCaja.obtieneFormasPagoConvenio(tableModelFormasPago,indConvenio,codConvenio,codCliente,numPed,valorCredito);
          } catch(SQLException sql)
          {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Convenio.\n" + sql.getMessage(), null);
          }        
    }

    /**
     * Se agrega logica de Convenios BTLMF
     * @author ERIOS
     * @since 11.06.2013
     * @param codConvenio
     * @return
     */
    private boolean cargaFormasPagoConvenio(String codConvenio) {
          try{
            DBConvenioBTLMF.obtieneFormasPagoConvenio(tableModelFormasPago,codConvenio);
            return true;
          } catch(SQLException sql)
          {
            log.error("",sql);
            FarmaUtility.showMessage(this, "Error al obtener las Formas de Pago Convenio BTLMF.\n" + sql.getMessage(), null);
            return false;
          }
    }
    
    public void habilitarCajas(ArrayList codFormaPago, ArrayList indTarje){
        boolean contFormSol = codFormaPago.contains(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES);
        boolean contFormDol = codFormaPago.contains(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES);
        boolean contFormTrj = indTarje.contains(FarmaConstants.INDICADOR_S);
        txtMontoPagado.setEditable(contFormSol);
        txtMontoPagadoDolares.setEditable(contFormDol);
        txtMontoTarjeta.setEnabled(contFormTrj);
    }
    
    private void cargarFormaPago(){
        //ERIOS 11.06.2013 Se agrega logica de Convenios BTLMF
        String numPed = VariablesCaja.vNumPedVta;
        String indConvenio = VariablesCaja.vIndPedidoConvenio;
        String codConvenio = VariablesConvenio.vCodConvenio;
        String codCliente = VariablesConvenio.vCodCliente;
        String creditoSaldo="";
        String esCredito ="";
        String valorCredito="";
        ArrayList indTarje = new ArrayList();
        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
            !UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null)){
            try {
              esCredito = DBConvenio.obtenerPorcentajeCopago(codConvenio);
            } catch (SQLException e) {
              log.error("",e);
              FarmaUtility.showMessage(this, "Error al obtener el porcentaje de la forma de pago."+e.getMessage(), null);
            }
            if (esCredito.equalsIgnoreCase("S")) {
                try {
                    creditoSaldo = DBConvenio.obtieneConvenioCredito(codConvenio, 
                                                              codCliente, 
                                                              FarmaConstants.INDICADOR_S);
                } catch (SQLException e) {
                    log.error("",e);
                    FarmaUtility.showMessage(this, "Error al obtener el convenio."+e.getMessage(), null);
                }
                if (creditoSaldo.equalsIgnoreCase("S")){
                    valorCredito = "S";
                }else{
                    valorCredito = "N";
                }
            }
            cargaFormasPagoConvenio(numPed, indConvenio, codConvenio, codCliente,valorCredito);
        }else if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
            cargaFormasPagoSinConvenio(numPed, indConvenio, codConvenio, codCliente);
        }else if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            //Agregado Por FRAMIREZ CargaForma de Pago por convenio
            if(UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vCodConvenio.trim().length()>0)
            {
                cargaFormasPagoConvenio(VariablesConvenioBTLMF.vCodConvenio);
            }
        }
        for(int i=0;i < tableModelFormasPago.getRowCount() ;i++){
            arrayCodFormaPago.add(((String) tableModelFormasPago.getValueAt(i,0)).trim());
            indTarje.add(((String) tableModelFormasPago.getValueAt(i,3)).trim());
        }
        habilitarCajas(arrayCodFormaPago,indTarje);
    }
    
    private void initVariablesRecaudacion(){
        if(!UtilityCaja.existeCajaUsuarioImpresora(this, null) || !UtilityCaja.validaFechaMovimientoCaja(this,txtMontoPagado))
        {
            FarmaUtility.showMessage(this, "La Recaudacion sera Anulado. Vuelva a generar uno nuevo.", null);
            //facadeRecaudacion.anularRCDPend(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, strCodRecau, null);
            cerrarVentanaRecau();   
            return;
        }
        buscaPedidoDiario();
        VariablesCaja.vNumPedPendiente = "";
        FarmaVariables.vAceptar = false;
        FarmaUtility.moveFocus(txtMontoPagado);        
    }

    private void muestraInfoPedidoRecau(ArrayList pArrayList) {
        String tMaxAnulRecaudacion="";
        String strTipoRecau="";
        lblRazSoc_T.setText("");    
        lblRazSoc.setText("");
        VariablesConvenio.vCodCliente = "";        
        
        //VariablesRecaudacion.vTipCambioCompra
        VariablesCaja.vValTipoCambioPedido = VariablesRecaudacion.vTipoCambioVenta+"";
        lblTipoCambio2.setText(VariablesCaja.vValTipoCambioPedido);
        lblTipoCambio1.setText(VariablesRecaudacion.vTipoCambioCompra+"");

        VariablesModuloVentas.vTip_Comp_Ped = ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
        lblTipoComprobante.setText(((String)((ArrayList)pArrayList.get(0)).get(5)).trim());
        
        String vValTotalPagar=((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
        String vValTotalSolesPagar=((String)((ArrayList)pArrayList.get(0)).get(2)).trim();
        
        if(ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ||
                                            ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tmpArrayCabRcd.get(4).toString())){
              
            lblRazSoc.setText(tmpArrayCabRcd.get(3).toString());  
            lblRazSoc_T.setText("Cliente :");
                                    
            if( ConstantsRecaudacion.TIPO_REC_CMR.equals(tmpArrayCabRcd.get(4).toString()) ){
                strTipoRecau="CMR";
            }else if (ConstantsRecaudacion.TIPO_REC_RIPLEY.equals(tmpArrayCabRcd.get(4).toString()) ){
                strTipoRecau="Ripley";
            }
            
            VariablesCaja.vValTotalPagar = vValTotalPagar;

            VariablesModuloVentas.vTipoPedido =  ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();
            //ERIOS 21.01.2014 Se quita la funcionalidad de cargar el monto de pago
            /*if(ConstantsCaja.EFECTIVO_SOLES.equals(tmpArrayCabRcd.get(9).toString())){                       
                txtMontoPagado.setText(tmpArrayCabRcd.get(10).toString());
                obtieneDatosFormaPagoPedidoSoles();
                adicionaDetallePago(txtMontoPagado);
                
            }else{
                txtMontoPagadoDolares.setText(tmpArrayCabRcd.get(10).toString());
                obtieneDatosFormaPagoPedidoDolares();
                adicionaDetallePago(txtMontoPagadoDolares);  
               
            }*/                 
            VariablesCaja.vIndPedidoConvenio = ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
            VariablesConvenio.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
                                    
        }else if(ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString())  ||
                ConstantsRecaudacion.TIPO_REC_CITI.equals(tmpArrayCabRcd.get(4).toString()) ){
            
            //VariablesCaja.vValTotalPagar = ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
            //TipoRecaudacion="Prestamos Citibank";  
            
            if( ConstantsRecaudacion.TIPO_REC_PRES_CITI.equals(tmpArrayCabRcd.get(4).toString()) ){
                strTipoRecau="Prestamos Citibank";
            }else if (ConstantsRecaudacion.TIPO_REC_CITI.equals(tmpArrayCabRcd.get(4).toString()) ){
                strTipoRecau="Tarjeta Citibank";
            }

        //}else if(ConstantsRecaudacion.TIPO_REC_CITI.equals(tmpArrayCabRcd.get(4).toString())){
            
            lblRazSoc.setText(tmpArrayCabRcd.get(3).toString());
            lblRazSoc_T.setText("Cliente :"); 
            
            VariablesCaja.vValTotalPagar = vValTotalPagar;

            VariablesModuloVentas.vTipoPedido =  ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();
            //ERIOS 21.01.2014 Se quita la funcionalidad de cargar el monto de pago
            /*if(ConstantsCaja.EFECTIVO_SOLES.equals(tmpArrayCabRcd.get(9).toString())){                       
                txtMontoPagado.setText(tmpArrayCabRcd.get(10).toString());
                obtieneDatosFormaPagoPedidoSoles();
                adicionaDetallePago(txtMontoPagado);
               
            }else{
                txtMontoPagadoDolares.setText(tmpArrayCabRcd.get(10).toString());
                obtieneDatosFormaPagoPedidoDolares();
                adicionaDetallePago(txtMontoPagadoDolares);  
                
            }*/                 
            VariablesCaja.vIndPedidoConvenio = ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
            VariablesConvenio.vCodConvenio = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
            
        }else if(ConstantsRecaudacion.TIPO_REC_CLARO.equals(tmpArrayCabRcd.get(4).toString())){
            
            VariablesCaja.vValTotalPagar = ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();            
            strTipoRecau="Claro";            
        }
        
        lblSolesTotalVenta.setText(VariablesCaja.vValTotalPagar);
        lblSaldo.setText(VariablesCaja.vValTotalPagar);
        calculaTotalPagar(VariablesCaja.vValTotalPagar);  
        
        VariablesCaja.vIndTotalPedidoCubierto = false;
        
        //RLLANTOY.11.07.2013. Muestra mensaje tiempo m�ximo de anulaci�n x tipo de recaudaci�n.
        tMaxAnulRecaudacion=facadeRecaudacion.tiempoMaxAnulacionRecaudacion("RCD");
        lblMensaje.setVisible(true);
        lblMensaje.setText("<html> Recaudaci�n: " + strTipoRecau + 
                           " <br> S�lo podr� anularse dentro de " + tMaxAnulRecaudacion + " minutos. </html>");
        
        ArrayList tmpCodFormRCD = new ArrayList();
        tmpCodFormRCD = facadeRecaudacion.obtenerCodFormsPagoRCD();
        habilitarCajas(tmpCodFormRCD,tmpCodFormRCD);
    }
    
    
    public void grabarRecaudacion(){        
        DlgProcesarPagoTerceros dlgProcesarPagoTerceros = new DlgProcesarPagoTerceros(myParentFrame,"",true);
        dlgProcesarPagoTerceros.procesarPagoTerceros(tmpArrayCabRcd, strCodRecau, tableModelDetallePago, lblVuelto, txtMontoPagado, txtMontoPagadoDolares );
        dlgProcesarPagoTerceros.setStrIndProc(ConstantsRecaudacion.RCD_IND_PROCESO_PAGO);
        dlgProcesarPagoTerceros.mostrar();
        
        if(FarmaVariables.vAceptar){
            cerrarVentana(true,null);
        }                
    }
    
    public void obtenerTipoCambio(){        
        try{
            facadeRecaudacion.obtenerTipoCambio();        
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener Tipo de Cambio del Dia . \n " + 
                                     sql.getMessage(), null);
        }
    }
    
    public void cerrarVentanaRecau(){
        //facadeRecaudacion.anularRCDPend(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal, strCodRecau);
        cerrarVentana(false,null);
    }
    
    /**
     * Se agrego logica de Convenios BTLMF
     * @author ERIOS
     * @since 11.06.2013
     * @param pCodConvenio
     * @return
     */
     public String getMensajeComprobanteConvenio(String pCodConvenio){
         String pCadena = "";
         try {
             double montoPedido = FarmaUtility.getDecimalNumber(lblSolesTotalVenta.getText().trim());

             pCadena = DBConvenioBTLMF.getMsgComprobante(pCodConvenio,montoPedido,VariablesConvenioBTLMF.vValorSelCopago);
         } catch (SQLException e) {
             pCadena = "N";
             log.error("",e);
         }
         return pCadena;
     }
    
    /**
     * Se agrego logica de Convenios BTLMF
     * @author ERIOS
     * @since 11.06.2013 
     */
    private void adicionaDetallePagoCredito()
    {
        double montoCredito = UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)),VariablesCaja.vNumPedVta,"");
        
        if (montoCredito > 0)
        {
              obtieneDatosFormaPagoPedidoCredito();        
              operaListaDetallePago();
              verificaMontoPagadoPedido();
        }
    }    

    /**
     * Se agrego logica de Convenios BTLMF
     * @author ERIOS
     * @since 11.06.2013 
     */
    private void obtieneDatosFormaPagoPedidoCredito()
    {      
      VariablesCaja.vCodFormaPago  = ConstantsConvenioBTLMF.COD_FORMA_PAGO_CREDITO;
      VariablesCaja.vDescFormaPago = UtilityConvenioBTLMF.obtieneFormaPago(this, null, ConstantsConvenioBTLMF.COD_FORMA_PAGO_CREDITO) ;
      VariablesCaja.vCantidadCupon = "0";
      
      VariablesCaja.vCodMonedaPago = ConstantsCaja.EFECTIVO_SOLES;
      VariablesCaja.vDescMonedaPago = FarmaLoadCVL.getCVLDescription(FarmaConstants.HASHTABLE_MONEDA, VariablesCaja.vCodMonedaPago);

      double montoPagar = UtilityConvenioBTLMF.obtieneMontoCredito(this, null, new Double(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)),VariablesCaja.vNumPedVta,"");
      VariablesCaja.vValMontoCredito = new Double(montoPagar).toString();
        
      VariablesCaja.vValMontoPagado = FarmaUtility.formatNumber(montoPagar);
      
        //DJ - RH
        VariablesCaja.vValMontoCredito_2 = montoPagar;
      
      lblValCopago.setText(VariablesCaja.vValMontoPagado);
      
      String porcCopago  = FarmaUtility.formatNumber((FarmaUtility.getDecimalNumber(VariablesCaja.vValMontoPagado)/FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar))*100,"");
      String porcCopagoTemp = porcCopago.replace('.', ' ');
      
        pnlCopago.setVisible(true);
      if (porcCopagoTemp.trim().equals("100"))
      {
        lblCopago.setText("Cr�ditos");
        lblPorcCopago.setText(porcCopago);
      }
      else
      {
        lblCopago.setText("Monto Empr.");
        lblPorcCopago.setText(porcCopago);
      }
        String vValTotalPagarPantalla = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCaja.vValTotalPagar)-montoPagar);
        calculaTotalPagar(vValTotalPagarPantalla);

      VariablesCaja.vValTotalPagado = VariablesCaja.vValMontoPagado;
    }

    /**
     * Cobro de pedido
     * @author ERIOS
     * @since 10.09.2013
     */
    private synchronized void funcionF11() {
        //ERIOS 2.2.9 Sincronizacion de cobro
        if(presionoF11){
            FarmaUtility.showMessage(this, "El pedido se encuentra en proceso de cobro.", null);
            return;
        }else{
            presionoF11 = true;
        }
        if(validarFormaPago()){
            if(intTipoCobro == ConstantsCaja.COBRO_PEDIDO){
                //JCHAVEZ 09.07.2009.sn graba el tiempo dei nicio de cobro
                try{
                    DBCaja.grabaInicioFinProcesoCobroPedido(VariablesCaja.vNumPedVta,"I");
                    log.debug("Grabo el tiempo de inicio de cobro");
                }
                catch(SQLException sql){
                    //FALTA CORREGIR ESTO
                    FarmaUtility.liberarTransaccion(); //ASOSA 22.02.2010            
                    log.error("",sql);
                    log.debug("Error al grabar el tiempo de inicio de cobro");
                }
                //JCHAVEZ 09.07.2009.en graba el tiempo de nicio de cobro
                
                UtilityCaja.grabarNuevoCobro(this,dlgFormPago,myParentFrame);
            }else if(intTipoCobro == ConstantsCaja.COBRO_CAJA_ELECTRONICA){
                cambiarFormaPago();
            }else if(intTipoCobro == ConstantsCaja.COBRO_RECAUDACION){
                grabarRecaudacion();
            }
        }
        presionoF11 = false;
    }
    
    /**
     * Procesa nuevo cobro
     * @author ERIOS
     * @since 10.10.2013
     */
     public void procesarNuevoCobro()
     {        log.debug(" 1.1 ********    NUEVO COBRO    ********");            
          log.debug("*Cobro de Pedido Normal");
          //JCORTEZ 02.07.2008 la generacion de cupones no aplica convenios
          /*VariablesCaja.vPermiteCampa�a = UtilityCaja.verificaPedidoCamp(this,VariablesCaja.vNumPedVta);
          if (VariablesCaja.vPermiteCampa�a.trim().equalsIgnoreCase("S") && 
              tblDetallePago.getRowCount() > 0) {
              UtilityCaja.validarFormasPagoCupones(this,VariablesCaja.vNumPedVta,tblDetallePago,lblSaldo,lblVuelto);
          }*/
                  
         UtilityCaja.procesarPedidoNuevoCobro(myParentFrame, this, lblVuelto, tblDetallePago, txtMontoPagado);

         if (!FarmaVariables.vAceptar) {
             if (VariablesCaja.vCierreDiaAnul) {
                 anularAcumuladoCanje();
                 VariablesCaja.vCierreDiaAnul = false;
             }

             obligarAnularTransaccionPinpad();

             try {
                 DBCaja.anularPedidoPendiente(VariablesModuloVentas.vNum_Ped_Vta);
                 FarmaUtility.aceptarTransaccion();
                 //FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                 cerrarVentana(false,null);
             } catch (SQLException e) {
                 log.error("", e);
             }
         }
           
           if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
               FarmaConnection.closeConnection();
               FarmaConnection.anularConnection();
           }         
          
      }
     
    
    /**
     * Procesa nuevo cobro
     * @author RHERRERA
     * @since 10.04.2014
     */
    public void procesarCobroBD()
    {        log.debug(" 1.1 ********    NUEVO COBRO    ********");            
         log.debug("*Cobro de Pedido Normal");
         //JCORTEZ 02.07.2008 la generacion de cupones no aplica convenios
         VariablesCaja.vPermiteCampa�a = UtilityCaja.verificaPedidoCamp(this,VariablesCaja.vNumPedVta);
         if (VariablesCaja.vPermiteCampa�a.trim().equalsIgnoreCase("S") && 
             tblDetallePago.getRowCount() > 0) {
             UtilityCaja.validarFormasPagoCupones(this,VariablesCaja.vNumPedVta,tblDetallePago,lblSaldo,lblVuelto);
         }
         
         // RHERRERA : SE DECLARAN LAS VARIABLES DE CONEXION
        UtilityCaja.finalizarPedidoCobroBD(myParentFrame, this, lblVuelto, tblDetallePago, txtMontoPagado);
         
        
        if (!FarmaVariables.vAceptar) {
            if (VariablesCaja.vCierreDiaAnul) {
                anularAcumuladoCanje();
                VariablesCaja.vCierreDiaAnul = false;
            }

            obligarAnularTransaccionPinpad();

            try {
                DBCaja.anularPedidoPendiente(VariablesModuloVentas.vNum_Ped_Vta);
                FarmaUtility.aceptarTransaccion();
                //FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                cerrarVentana(false,null);
            } catch (SQLException e) {
                log.error("", e);
            }
        }
          
          if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) {
              FarmaConnection.closeConnection();
              FarmaConnection.anularConnection();
          }         
         
     }
    
   /* private void grabarNuevoCobro(){
        
      //  indCobroBD = getIndCobraBD();
        
        //ERIOS 19.11.2013 Nuevo "nuevo cobro"
        //1. Valida exito en pago con tarjeta
        if(VariablesCaja.vIndDatosTarjeta && VariablesCaja.vCodVoucher.equals("")){
            FarmaUtility.showMessage(this, "Verifique el pago con tarjeta.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
            return;
        }
        
        //2. Valida exito en recarga virtual
        if (VariablesCaja.vIndPedidoConProdVirtual){
            DlgProcesarProductoVirtual dlgProcesarProductoVirtual = new DlgProcesarProductoVirtual(myParentFrame,"Virtual",true);
            dlgProcesarProductoVirtual.setTblFormasPago(tblFormasPago);
            dlgProcesarProductoVirtual.setTxtNroPedido(txtMontoPagado);
            dlgProcesarProductoVirtual.mostrar();
            if(!dlgProcesarProductoVirtual.isVProcesoRecarga()){
                //ERIOS 21.11.2013 Se comenta porque se hace el control en DlgProcesarProductoVirtual
                //FarmaUtility.showMessage(this, "Hubo un error al procesar.\n No puede continuar con el cobro.\n Llame a Mesa de ayuda para verificar el caso.", null);
                this.eventoEscape();
                return;
            }
        }

        //----   RHERRERA 04/04/2014 : Se comenta para obviar todo el paso del cobro en Java 
//  if ( (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) ||
//       !indCobroBD ) {
        
        if (!indCobroBD){ // quitar si falla
            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("1. Si vIndPedidoCobrado=False, INGRESA METODO procesarNuevoCobro() ");
                procesarNuevoCobro();
            }
            //se guarda valores
            VariablesCaja.vVuelto = lblVuelto.getText().trim();
            if (VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                UtilityCaja.finalizarPedidoNuevoCobro(myParentFrame, this, lblVuelto, tblDetallePago, txtMontoPagado);                
            }
        }else {
            //RHERRERA: Se Asume que el vIndPedidoCobrado = False
            VariablesCaja.vVuelto = lblVuelto.getText().trim();

            if (!VariablesCaja.vIndPedidoCobrado) {
                log.debug("2. Si vIndPedidoCobrado=TRUE, INGRESA METODO finalizarPedidoNuevoCobro ");
                procesarCobroBD();
            }
        }
          
          if(VariablesCaja.vIndPedidoCobrado){
            //ERIOS 21.01.2014 Validacion de error en cobro
           if(FarmaVariables.vAceptar){
            FarmaVariables.vAceptar = false;
            pedidoCobrado();
            }
            //Si la variable indica que de escape y recalcule  el ahorro del cliente
            //if(VariablesFidelizacion.vRecalculaAhorroPedido){
              //  eventoEscape();
            //}
            VariablesVentas.vProductoVirtual=false;
        }
    }*/

    public void setStrMonedaPagar(String strMonedaPagar) {
        this.strMonedaPagar = strMonedaPagar;
    }

    public String getStrMonedaPagar() {
        return strMonedaPagar;
    }
    
    /**
     * Muestra el monto a pagar y el valor al tipo de cambio
     * @author ERIOS
     * @since 22.01.2014
     * @param pMonto
     */
    private void calculaTotalPagar(String pMonto) {

        lblSolesTotalPagar.setText(pMonto);

        String strMontoConv = "0.0";
        if (strMonedaPagar.equals(ConstantsRecaudacion.EST_CTA_SOLES)) {
            strMontoConv =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pMonto) / FarmaUtility.getDecimalNumber(VariablesCaja.vValTipoCambioPedido));
        } else {
            strMontoConv =
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(pMonto) * VariablesRecaudacion.vTipoCambioCompra);
        }
        lblTotalConversion.setText(strMontoConv);
    }

    private void obligarAnularTransaccionPinpad()
    {   //LLEIVA 09/Ene/2014 Si el pedido posee transacci�n de Pinpad, intentar la anulaci�n hasta se realice correctamente
        if(VariablesCaja.vIndDatosTarjeta)
        {   try
            {   //LLEIVA 09-Ene-2014 Se agrupo to_do el procedimiento de obligar a anular la transaccion de pinpad
                UtilityPinpad.obligarAnularTransaccionPinpad(myParentFrame,
                                                            "Es obligatoria la anulaci�n de la transacci�n debido a que no se pudo procesar el pedido");
            }
            catch (Exception e)
            {   log.error("",e);
            }
        }
        //FIN LLEIVA
    }

    public void imprimeFormasPago(){
        log.info("****  imprimeFormasPago ****");
        for(int i=0;i<tableModelDetallePago.data.size();i++){
            ArrayList lista = (ArrayList)tableModelDetallePago.data.get(i);
            log.info("****  FILA  **** "+i+" *****");
            for(int j=0;j<lista.size();j++)
                log.info("j="+j+"-"+lista.get(j));
        }
        log.info("****  DETALLE DE TARJETAS  *********");
        for(int i=0;i<vFormaPagoTarjetas.size();i++){
            ArrayList lista = (ArrayList)vFormaPagoTarjetas.get(i);
            log.info("****  FILA  **** "+i+" *****");
            for(int j=0;j<lista.size();j++)
                log.info("j="+j+"-"+lista.get(j));
        }
        
        
    }
    public boolean getIndCobraBD() {
        boolean pvalor = false;
        String pCadena = "";
        try {
            pCadena = DBCobroBD.getIndCobroBD();
        } catch (Exception sqle) {
            // TODO: Add catch code
            pCadena = "N";
            log.info(sqle.getMessage());
        }
        
        if(pCadena.trim().equalsIgnoreCase("S"))
            pvalor = true;
        
        return pvalor;
    }
}

