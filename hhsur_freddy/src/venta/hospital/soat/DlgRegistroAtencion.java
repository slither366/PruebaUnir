package venta.hospital.soat;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.impresoras.reference.DBImpresoras;
import venta.administracion.impresoras.reference.VariablesImpresoras;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRegistroAtencion extends JDialog
{
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroAtencion.class);
    private boolean existenDatos;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlDatosImpresora = new JPanelTitle();
    private JLabelWhite lblNroImpresora_T = new JLabelWhite();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JButtonLabel btnDescImpresora = new JButtonLabel();
    private JTextFieldSanSerif txtnombres = new JTextFieldSanSerif();
    private JComboBox cmbSeguro = new JComboBox();
    private JTextFieldSanSerif txtCobertura = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantidadDias = new JTextFieldSanSerif();
    private JButtonLabel btnColaImp = new JButtonLabel();
    private JButtonLabel btnComprobante = new JButtonLabel();
    private JButtonLabel btnSerie = new JButtonLabel();
    private JButtonLabel btnNroComp = new JButtonLabel();
    private JButtonLabel btnModelo = new JButtonLabel();
    private JTextFieldSanSerif txtvalidacion = new JTextFieldSanSerif();
    private JButtonLabel btnNSerie = new JButtonLabel();
    private JButton btnGrabar = new JButton();
    private JButton btnLimpiar = new JButton();
    private JTextField txtDesde = new JTextField();
    private JTextField txtHasta = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private Checkbox ckCartaGarantia = new Checkbox();
    private Checkbox ckDenuncia = new Checkbox();
    private Checkbox ckCopiaDNI = new Checkbox();
    private Checkbox ckCupoAtencion = new Checkbox();
    private Checkbox ckSoatCopia = new Checkbox();
    private Label lblCantidadDoc = new Label();

    private boolean vVisualizar =false;
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel pnlHistoriaClinica = new JPanel();
    private JPanel pnlDiagnostico = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JTextField jTextField2 = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JTextField jTextField3 = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JTextField jTextField4 = new JTextField();
    private JLabel jLabel5 = new JLabel();
    private JTextField jTextField5 = new JTextField();
    private JLabel jLabel6 = new JLabel();
    private JComboBox cmbSexo = new JComboBox();
    private JLabel jLabel7 = new JLabel();
    private JTextField jTextField6 = new JTextField();
    private JButton jButton1 = new JButton();
    private JPanel jPanel2 = new JPanel();
    private JButton jButton2 = new JButton();
    private JTextField jTextField7 = new JTextField();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JTextField jTextField8 = new JTextField();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable jTable1 = new JTable();
    private JLabel jLabel10 = new JLabel();
    private JList jListAsignado = new JList();
    DefaultListModel model = new DefaultListModel();
    private JButton jButton3 = new JButton();
    private JButton jButton4 = new JButton();
    private JLabel jLabel11 = new JLabel();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel12 = new JLabel();
    private JTextField jTextField9 = new JTextField();
    private JButton jButton5 = new JButton();
    private JButton jButton6 = new JButton();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel pnlOtros = new JPanel();
    private JLabel jLabel13 = new JLabel();
    private JTextField jTextField10 = new JTextField();
    private JLabel jLabel14 = new JLabel();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    private JLabel jLabel15 = new JLabel();
    private JPanel pnlServiciosHospital = new JPanel();
    private JLabel jLabel16 = new JLabel();
    private JScrollPane jScrollPane4 = new JScrollPane();
    
    private JList jListServicios = new JList();
    DefaultListModel modelServicios = new DefaultListModel();
    private JScrollPane jScrollPane5 = new JScrollPane();
    DefaultListModel  modelServiciosDetalle= new DefaultListModel();
    private JList jListServicioDetalle = new JList(modelServiciosDetalle);
    
    private JLabel jLabel17 = new JLabel();
    DefaultListModel modelServiciosAgregados = new DefaultListModel();
    private JButton jButton7 = new JButton();
    private JButton jButton8 = new JButton();
    private JScrollPane jScrollPane6 = new JScrollPane();
    private JList jListServicioAgregados = new JList();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgRegistroAtencion()
    {   this(null, "", false);
    }

    public DlgRegistroAtencion(Frame parent, String title, boolean modal)
    {   super(parent, title, modal);
        myParentFrame = parent;
        try
        {   jbInit();
            initialize();
        }
        catch (Exception e)
        {   log.error("",e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************
    
    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(1041, 657));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Registro Atenci\u00f3n - Validaci\u00f3n de Documentos");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jContentPane.setLayout(null);
        jContentPane.setFocusable(false);
        pnlDatosImpresora.setFocusable(false);
        lblNroImpresora_T.setText("<html><center>DNI o Carnet Extranjeria:</html><c/enter>");
        lblNroImpresora_T.setBounds(new Rectangle(10, 10, 100, 30));
        lblNroImpresora_T.setFocusable(false);
        txtDni.setBounds(new Rectangle(105, 15, 160, 20));
        txtDni.setText("44324600");
        txtDni.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDni_keyPressed(e);
                }
            });
        btnDescImpresora.setText("Nombres :");
        btnDescImpresora.setBounds(new Rectangle(35, 45, 60, 20));
        btnDescImpresora.setBorder(BorderFactory.createLineBorder(Color.black,
                    1));
        btnDescImpresora.setMnemonic('d');
        btnDescImpresora.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDescImpresora_actionPerformed(e);
            }
        });
        txtnombres.setBounds(new Rectangle(105, 45, 245, 20));
        cmbSeguro.setBounds(new Rectangle(105, 80, 245, 20));
        cmbSeguro.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbSeguro_keyPressed(e);
                }
            });
        txtCobertura.setBounds(new Rectangle(105, 115, 155, 20));
        txtCantidadDias.setBounds(new Rectangle(105, 185, 105, 20));
        btnColaImp.setText("Vigencia :");
        btnColaImp.setBounds(new Rectangle(30, 145, 70, 20));
        btnColaImp.setMnemonic('i');
        btnColaImp.setFocusable(false);
        btnColaImp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnColaImp_actionPerformed(e);
            }
        });
        btnComprobante.setText("Seguradora :");
        btnComprobante.setBounds(new Rectangle(15, 80, 85, 20));
        btnComprobante.setMnemonic('c');
        btnComprobante.setFocusable(false);
        btnComprobante.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnComprobante_actionPerformed(e);
            }
        });
        btnSerie.setText("Cobertura: S/ ");
        btnSerie.setBounds(new Rectangle(20, 115, 80, 20));
        btnSerie.setFocusable(false);
        btnNroComp.setText("Cantidad d\u00edas:");
        btnNroComp.setBounds(new Rectangle(15, 180, 105, 20));
        btnNroComp.setMnemonic('n');
        btnNroComp.setFocusable(false);
        btnNroComp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNroComp_actionPerformed(e);
            }
        });
        btnModelo.setText("Hasta:");
        btnModelo.setBounds(new Rectangle(215, 145, 50, 20));
        btnModelo.setFocusable(false);
        txtvalidacion.setBounds(new Rectangle(15, 245, 345, 20));
        txtvalidacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtvalidacion_keyPressed(e);
                }
            });
        btnNSerie.setText("Se Valid\u00f3 con :");
        btnNSerie.setBounds(new Rectangle(15, 220, 125, 20));
        btnNSerie.setMnemonic('e');
        btnNSerie.setFocusable(false);
        btnNSerie.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNSerie_actionPerformed(e);
            }
        });
        btnGrabar.setText("Grabar");
        btnGrabar.setBounds(new Rectangle(10, 585, 90, 20));
        btnGrabar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnGrabar_actionPerformed(e);
                }
            });
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBounds(new Rectangle(140, 585, 100, 20));
        btnLimpiar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnLimpiar_actionPerformed(e);
                }
            });
        txtDesde.setBounds(new Rectangle(105, 145, 105, 20));
        txtDesde.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDesde_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtDesde_keyReleased(e);
                }
            });
        txtHasta.setBounds(new Rectangle(255, 145, 105, 20));
        txtHasta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtHasta_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtHasta_keyReleased(e);
                }
            });
        jPanel1.setBounds(new Rectangle(400, 10, 285, 290));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createTitledBorder("Seleccione los Documentos Presentados"));
        ckCartaGarantia.setLabel("Carta Garant\u00eda (Original y Copia)");
        ckCartaGarantia.setBounds(new Rectangle(25, 35, 200, 20));
        ckCartaGarantia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCartaGarantia_itemStateChanged(e);
                }
            });
        ckDenuncia.setLabel("Denuncia Policial");
        ckDenuncia.setBounds(new Rectangle(25, 75, 145, 20));
        ckDenuncia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckDenuncia_itemStateChanged(e);
                }
            });
        ckCopiaDNI.setLabel("Copia DNI");
        ckCopiaDNI.setBounds(new Rectangle(25, 115, 145, 20));
        ckCopiaDNI.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCopiaDNI_itemStateChanged(e);
                }
            });
        ckCupoAtencion.setLabel("Cupo de Atenci\u00f3n");
        ckCupoAtencion.setBounds(new Rectangle(25, 155, 140, 20));
        ckCupoAtencion.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCupoAtencion_itemStateChanged(e);
                }
            });
        ckSoatCopia.setLabel("SOAT  (Copia)");
        ckSoatCopia.setBounds(new Rectangle(25, 200, 145, 20));
        ckSoatCopia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckSoatCopia_itemStateChanged(e);
                }
            });
        lblCantidadDoc.setText("Se Presentaron :   10  Documentos.");
        lblCantidadDoc.setBounds(new Rectangle(15, 225, 255, 55));
        lblCantidadDoc.setFont(new Font("Arial", 1, 15));
        lblCantidadDoc.setBackground(SystemColor.window);
        lblCantidadDoc.setForeground(new Color(255, 33, 33));
        jTabbedPane1.setBounds(new Rectangle(10, 65, 1005, 510));
        pnlHistoriaClinica.setLayout(null);
        pnlHistoriaClinica.setBackground(new Color(0, 114, 169));
        pnlDiagnostico.setLayout(null);
        pnlDiagnostico.setBackground(new Color(0, 114, 169));
        jLabel1.setText("DNI:");
        jLabel1.setBounds(new Rectangle(25, 10, 75, 30));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 12));
        jTextField1.setBounds(new Rectangle(65, 15, 150, 20));
        jTextField1.setText("44324600");
        jLabel2.setText("Nombres :");
        jLabel2.setBounds(new Rectangle(20, 55, 65, 15));
        jLabel2.setForeground(SystemColor.window);
        jTextField2.setBounds(new Rectangle(75, 55, 305, 20));
        jTextField2.setText("Diego Armando");
        jLabel3.setText("Apellidos :");
        jLabel3.setBounds(new Rectangle(15, 85, 65, 15));
        jLabel3.setForeground(SystemColor.window);
        jTextField3.setBounds(new Rectangle(75, 85, 305, 20));
        jTextField3.setText("Ubilluz Carrillo");
        jLabel4.setText("Correo Electronico :");
        jLabel4.setBounds(new Rectangle(15, 115, 105, 15));
        jLabel4.setForeground(SystemColor.window);
        jTextField4.setBounds(new Rectangle(125, 115, 205, 20));
        jTextField4.setText("daubilluz@hotmail.com");
        jLabel5.setText("Telefono:");
        jLabel5.setBounds(new Rectangle(20, 145, 80, 15));
        jLabel5.setForeground(SystemColor.window);
        jTextField5.setBounds(new Rectangle(125, 145, 205, 20));
        jTextField5.setText("940197014");
        jLabel6.setText("Sexo :");
        jLabel6.setBounds(new Rectangle(25, 185, 34, 14));
        jLabel6.setForeground(SystemColor.window);
        cmbSexo.setBounds(new Rectangle(125, 180, 165, 20));
        jLabel7.setText("Fecha Nacimiento:");
        jLabel7.setBounds(new Rectangle(15, 215, 90, 15));
        jLabel7.setForeground(SystemColor.window);
        jTextField6.setBounds(new Rectangle(125, 215, 165, 20));
        jTextField6.setText("16/06/2015");
        jButton1.setText("Salir");
        jButton1.setBounds(new Rectangle(935, 590, 75, 21));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jPanel2.setBounds(new Rectangle(515, 20, 245, 230));
        jPanel2.setBorder(BorderFactory.createTitledBorder("Foto "));
        jButton2.setText("Cambiar Foto :");
        jButton2.setBounds(new Rectangle(785, 95, 135, 20));
        jTextField7.setBounds(new Rectangle(95, 20, 80, 20));
        jLabel8.setText("CIE 10");
        jLabel8.setBounds(new Rectangle(15, 20, 55, 15));
        jLabel8.setForeground(SystemColor.window);
        jLabel9.setText("Descripci\u00f3n :");
        jLabel9.setBounds(new Rectangle(10, 50, 85, 20));
        jLabel9.setForeground(SystemColor.window);
        jTextField8.setBounds(new Rectangle(95, 50, 250, 20));
        jScrollPane1.setBounds(new Rectangle(15, 105, 460, 320));
        jLabel10.setText("Maestro de Diagnosticos:");
        jLabel10.setBounds(new Rectangle(15, 90, 150, 15));
        jLabel10.setForeground(SystemColor.window);
        //jListAsignado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jButton3.setText(">>");
        jButton3.setBounds(new Rectangle(515, 200, 75, 45));
        jButton3.setFont(new Font("Tahoma", 1, 17));
        jButton4.setText("<<");
        jButton4.setBounds(new Rectangle(515, 260, 75, 45));
        jButton4.setFont(new Font("Tahoma", 1, 17));
        jLabel11.setText("Diagnosticos Asignados :");
        jLabel11.setBounds(new Rectangle(625, 75, 330, 15));
        jLabel11.setForeground(SystemColor.window);
        jPanel3.setBounds(new Rectangle(10, 10, 555, 50));
        jPanel3.setLayout(null);
        jPanel3.setBackground(new Color(231, 231, 231));
        jLabel12.setText("N\u00b0 Atenci\u00f3n:");
        jLabel12.setBounds(new Rectangle(5, 5, 85, 30));
        jLabel12.setFont(new Font("Tahoma", 1, 13));
        jTextField9.setBounds(new Rectangle(105, 5, 140, 30));
        jTextField9.setFont(new Font("Tahoma", 1, 12));
        jButton5.setText("Cambiar");
        jButton5.setBounds(new Rectangle(260, 10, 95, 20));
        jButton6.setText("BUSCAR");
        jButton6.setBounds(new Rectangle(405, 10, 125, 25));
        jButton6.setFont(new Font("Tahoma", 1, 13));
        jScrollPane2.setBounds(new Rectangle(630, 105, 355, 325));
        pnlOtros.setLayout(null);
        pnlOtros.setBackground(new Color(0, 114, 169));
        jLabel13.setText("Ingresar Datos Vehiculo");
        jLabel13.setBounds(new Rectangle(10, 10, 240, 25));
        jLabel13.setForeground(SystemColor.window);
        jLabel13.setFont(new Font("Tahoma", 1, 12));
        jTextField10.setBounds(new Rectangle(90, 45, 225, 20));
        jLabel14.setText("Placa :");
        jLabel14.setBounds(new Rectangle(25, 40, 60, 25));
        jLabel14.setForeground(SystemColor.window);
        jScrollPane3.setBounds(new Rectangle(90, 120, 760, 290));
        jLabel15.setText("Ingrese Datos Adicionales para la Liquidaci\u00f3n :");
        jLabel15.setBounds(new Rectangle(25, 85, 315, 25));
        jLabel15.setForeground(SystemColor.window);
        jLabel15.setFont(new Font("Tahoma", 1, 12));
        pnlServiciosHospital.setBackground(new Color(0, 114, 169));
        pnlServiciosHospital.setLayout(null);
        jLabel16.setText("Seleccione el Servicios :");
        jLabel16.setBounds(new Rectangle(10, 10, 180, 20));
        jLabel16.setFont(new Font("Tahoma", 1, 11));
        jLabel16.setForeground(SystemColor.window);
        jScrollPane4.setBounds(new Rectangle(10, 35, 240, 215));
        jListServicios.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jListServicios_mouseClicked(e);
                }
            });
        jListServicios.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jListServicios_mouseClicked(e);
                }
            });
        jListServicios.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    jListServicios_propertyChange(e);
                }
            });
        jScrollPane5.setBounds(new Rectangle(325, 35, 525, 215));
        jLabel17.setText("Servicios Agregados a la Atenci\u00f3n");
        jLabel17.setBounds(new Rectangle(15, 265, 310, 20));
        jLabel17.setForeground(SystemColor.window);
        jLabel17.setFont(new Font("Tahoma", 1, 12));
        jButton7.setText("Quitar");
        jButton7.setBounds(new Rectangle(835, 300, 75, 35));
        jButton8.setText("Agregar");
        jButton8.setBounds(new Rectangle(865, 35, 85, 35));
        jScrollPane6.setBounds(new Rectangle(20, 290, 795, 150));
        jTabbedPane1.addTab("Datos Recepción", pnlDatosImpresora);
        pnlHistoriaClinica.add(jButton2, null);
        pnlHistoriaClinica.add(jPanel2, null);
        pnlHistoriaClinica.add(jTextField6, null);
        pnlHistoriaClinica.add(jLabel7, null);
        pnlHistoriaClinica.add(cmbSexo, null);
        pnlHistoriaClinica.add(jLabel6, null);
        pnlHistoriaClinica.add(jTextField5, null);
        pnlHistoriaClinica.add(jLabel5, null);
        pnlHistoriaClinica.add(jTextField4, null);
        pnlHistoriaClinica.add(jLabel4, null);
        pnlHistoriaClinica.add(jTextField3, null);
        pnlHistoriaClinica.add(jLabel3, null);
        pnlHistoriaClinica.add(jTextField2, null);
        pnlHistoriaClinica.add(jLabel2, null);
        pnlHistoriaClinica.add(jTextField1, null);
        pnlHistoriaClinica.add(jLabel1, null);
        jTabbedPane1.addTab("Historia Clínica", pnlHistoriaClinica);
        jScrollPane1.getViewport().add(jTable1, null);
        jListAsignado = new JList(model);
        jScrollPane2.getViewport().add(jListAsignado, null);
        pnlDiagnostico.add(jScrollPane2, null);
        pnlDiagnostico.add(jLabel11, null);
        pnlDiagnostico.add(jButton4, null);
        pnlDiagnostico.add(jButton3, null);
        pnlDiagnostico.add(jLabel10, null);
        pnlDiagnostico.add(jScrollPane1, null);
        pnlDiagnostico.add(jTextField8, null);
        pnlDiagnostico.add(jLabel9, null);
        pnlDiagnostico.add(jLabel8, null);
        pnlDiagnostico.add(jTextField7, null);
        jTabbedPane1.addTab("Diagnóstico", pnlDiagnostico);
        jScrollPane3.getViewport().add(jTextArea1, null);
        pnlOtros.add(jLabel15, null);
        pnlOtros.add(jScrollPane3, null);
        pnlOtros.add(jLabel14, null);
        pnlOtros.add(jTextField10, null);
        pnlOtros.add(jLabel13, null);
        jTabbedPane1.addTab("Adicionales", pnlOtros);
        
        jListServicios =new JList(modelServicios);
        
        jScrollPane4.getViewport().add(jListServicios, null);
        
        jListServicioAgregados = new JList(modelServiciosAgregados);
        
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
              public void valueChanged(ListSelectionEvent listSelectionEvent) {
                cargaSeleccion();
                
              }

            private void cargaSeleccion() {
                int vPos = jListServicios.getSelectedIndex();
                String pCadena = modelServicios.get(vPos).toString();
                
                modelServiciosDetalle.removeAllElements();
                
                if(pCadena.toUpperCase().equalsIgnoreCase("RAYOS X DIGITAL")){

                    modelServiciosDetalle.addElement("SJM0336 ANTEBRAZO COMPARATIVO (4 PLACAS) ");
                    modelServiciosDetalle.addElement("SJM0335 ANTEBRAZO (RADIO-CUBITO F-L) ");
                    modelServiciosDetalle.addElement("SJM03398 ARCO CIGOMATICO (CHIRTZ-WATLER) ");
                    modelServiciosDetalle.addElement("SJM03396 ARCO PLANTAR (1) ");
                }
                else
                    if(pCadena.toUpperCase().equalsIgnoreCase("ANATOMIA PATOLOGICA")){

                        modelServiciosDetalle.addElement("SJM00133 ANTICUERPOS ANTICITOPLASMATICOS ");
                        modelServiciosDetalle.addElement("SJM00140 ASPIRADO DE MEDULA OSEA (AMO) LECTURA");
                    }
                else
                    if(pCadena.toUpperCase().equalsIgnoreCase("CIRUGIA CABEZA Y CUELLO")){

                        modelServiciosDetalle.addElement("SJM00311 CONSULTA");
                        modelServiciosDetalle.addElement("SJM00313 CURACION GRANDE");
                        modelServiciosDetalle.addElement("SJM00311 BLEFAROPLASTIAS");
                        modelServiciosDetalle.addElement("SJM00313 CURACION GRANDE");
                        modelServiciosDetalle.addElement("SJM00311 EXCERESIS TUMORAL");
                        modelServiciosDetalle.addElement("SJM00311 EXCERESIS TUMORAL");
                        modelServiciosDetalle.addElement("SJM00313 CURACION GRANDE");

                    }
                else
                {
                        modelServiciosDetalle.addElement("SJM00311 CONSULTA");
                        
                    }
                    
                
                jListServicioDetalle.repaint();
            }
        };
            jListServicios.addListSelectionListener(listSelectionListener);
        
        jScrollPane5.getViewport().add(jListServicioDetalle, null);
        jScrollPane6.getViewport().add(jListServicioAgregados, null);
        pnlServiciosHospital.add(jScrollPane6, null);
        pnlServiciosHospital.add(jButton8, null);
        pnlServiciosHospital.add(jButton7, null);
        pnlServiciosHospital.add(jLabel17, null);
        pnlServiciosHospital.add(jScrollPane5, null);
        pnlServiciosHospital.add(jScrollPane4, null);
        pnlServiciosHospital.add(jLabel16, null);
        jTabbedPane1.addTab("Servicios Hospital", pnlServiciosHospital);
        jPanel3.add(jButton6, null);
        jPanel3.add(jButton5, null);
        jPanel3.add(jTextField9, null);
        jPanel3.add(jLabel12, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(jButton1, null);
        jContentPane.add(jTabbedPane1, null);
        jContentPane.add(btnLimpiar, null);
        jContentPane.add(btnGrabar, null);
        jPanel1.add(lblCantidadDoc, null);
        jPanel1.add(ckSoatCopia, null);
        jPanel1.add(ckCupoAtencion, null);
        jPanel1.add(ckCopiaDNI, null);
        jPanel1.add(ckDenuncia, null);
        jPanel1.add(ckCartaGarantia, null);
        pnlDatosImpresora.add(jPanel1, null);
        pnlDatosImpresora.add(txtHasta, null);
        pnlDatosImpresora.add(txtDesde, null);
        pnlDatosImpresora.add(btnNSerie, null);
        pnlDatosImpresora.add(txtvalidacion, null);
        pnlDatosImpresora.add(btnModelo, null);
        pnlDatosImpresora.add(btnNroComp, null);
        pnlDatosImpresora.add(btnSerie, null);
        pnlDatosImpresora.add(btnComprobante, null);
        pnlDatosImpresora.add(btnColaImp, null);
        pnlDatosImpresora.add(txtCantidadDias, null);
        pnlDatosImpresora.add(txtCobertura, null);
        pnlDatosImpresora.add(cmbSeguro, null);
        pnlDatosImpresora.add(txtnombres, null);
        pnlDatosImpresora.add(btnDescImpresora, null);
        pnlDatosImpresora.add(txtDni, null);
        pnlDatosImpresora.add(lblNroImpresora_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //AGREGADO 20/06/2006 ERIOS
        txtnombres.setLengthText(30);
        txtnombres.setText("Diego Ubilluz");
        txtnombres.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtnombres_keyPressed(e);
                }
            });
        txtCobertura.setLengthText(7);
        txtCobertura.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCobertura_keyPressed(e);
                }
            });
        txtCantidadDias.setLengthText(120);
        txtCantidadDias.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCantidadDias_keyPressed(e);
                }
            });
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize()
    {
        FarmaVariables.vAceptar = false;
        initCombos();
        limpiarDatos();
        vRefrescaCantidad();
        
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initCombos()
    {
        initCmbSeguradora();
    }

    private void initCmbSeguradora()
    {
        /*ArrayList parametros2 = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbSeguro,
                                    "cmbAseguradora",
                                    "HH_SOAT.F_GET_CMB_ASEGURADORA", 
                                    parametros2,
                                    false, 
                                    true);*/
        cmbSeguro.addItem("Seleccione");
        cmbSeguro.addItem("Rimac");
        cmbSeguro.addItem("Pacifico");
        cmbSeguro.addItem("Otros");
    }
        
    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
            cargaDatos();
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtDescImpresora_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(existenDatos)
                FarmaUtility.moveFocus(txtCobertura);
            else
                FarmaUtility.moveFocus(cmbSeguro);
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCantidadDias);
        }
        chkKeyPressed(e);
    }

    private void txtColaImpresion_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }
    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCobertura, e);
    }

    private void btnDescImpresora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnombres);
    }

    private void btnComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSeguro);
    }


    private void btnNroComp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCobertura);
    }

    private void btnColaImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidadDias);
    }

    private void btnNSerie_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtvalidacion);
    }
    
    private void txtSerieImpr_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }
    
    // **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
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

    private void limpiarDatos() {
            txtDni.setText("");
            txtnombres.setText("");
            txtCobertura.setText("");
            txtCantidadDias.setText("");
            txtDesde.setText("");
            txtHasta.setText("");
            cmbSeguro.setSelectedItem("");
            txtvalidacion.setText("");
            FarmaUtility.moveFocus(txtDni);
            ckCartaGarantia.setState(false);
            ckCopiaDNI.setState(false);
            ckCupoAtencion.setState(false);
            ckSoatCopia.setState(false);
            ckDenuncia.setState(false);
            vRefrescaCantidad();
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }

    private void txtnombres_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(cmbSeguro);
        }
        chkKeyPressed(e);
    }

    private void cmbSeguro_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCobertura);
        }
        chkKeyPressed(e);
    }

    private void txtCobertura_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDesde);
        }
        chkKeyPressed(e);
    }

    private void txtDesde_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtHasta);
        }
        chkKeyPressed(e);
    }

    private void txtHasta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCantidadDias);
        }
        chkKeyPressed(e);
    }

    private void txtCantidadDias_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtvalidacion);
        }
        chkKeyPressed(e);
    }

    private void txtvalidacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDni);
        }
        chkKeyPressed(e);
    }

    private void ckCartaGarantia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void vRefrescaCantidad() {
        int vCantidad = 0;
        if(ckCartaGarantia.getState())vCantidad ++;
        if(ckDenuncia.getState())vCantidad ++;
        if(ckCopiaDNI.getState())vCantidad ++;
        if(ckCupoAtencion.getState())vCantidad ++;
        if(ckSoatCopia.getState())vCantidad ++;
        lblCantidadDoc.setText("Se Presentaron :   "+vCantidad+  " Documentos.");
    }

    private void ckDenuncia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckCopiaDNI_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckCupoAtencion_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckSoatCopia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void btnLimpiar_actionPerformed(ActionEvent e) {
        limpiarDatos();
    }

    private void btnGrabar_actionPerformed(ActionEvent e) {
        grabarDatosAtencion();
    }

    private void grabarDatosAtencion() {
            if (JConfirmDialog.rptaConfirmDialog(this,
                            "¿Esta seguro de grabar la atención nueva?"))  {
                    FarmaUtility.showMessage(this, "Se grabó éxito la atención \n" +
                        "Nº de Atención : 000210 ", txtCantidadDias);
                    cerrarVentana(true);
                }
    }

    private void txtDesde_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtDesde,e);
    }

    private void txtHasta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtHasta,e);
    }

    public void setVVisualizar(boolean vVisualizar) {
        this.vVisualizar = vVisualizar;
    }

    public boolean isVVisualizar() {
        return vVisualizar;
    }

    private void cargaDatos() {
        txtDni.setText("123456789");
        txtnombres.setText("Carlos Acevedo");
        txtCobertura.setText("6500");
        txtCantidadDias.setText("10");
        txtDesde.setText("13/08/2015");
        txtHasta.setText("23/08/2015");
        cmbSeguro.setSelectedItem(2);
        txtvalidacion.setText("Se valido con la Lic. Veronica");
        FarmaUtility.moveFocus(txtDni);
        ckCartaGarantia.setState(true);
        ckCopiaDNI.setState(true);
        ckCupoAtencion.setState(false);
        ckSoatCopia.setState(false);
        ckDenuncia.setState(false);
        vRefrescaCantidad();
        
        cmbSexo.addItem("Masculino");
        cmbSexo.addItem("Femenino");
        
        
        model.addElement("I713	- RUPTURA DE ANEURISMA DE LA AORTA ABDOMINAL");
        model.addElement("K011	- DIENTES IMPACTADOS");
        jListAsignado.repaint();
        
        
        modelServicios.addElement("ANATOMIA PATOLOGICA");
        modelServicios.addElement("CARDIOLOGÍA");
        modelServicios.addElement("CIRUGIA CARDIOVASCULAR");
        modelServicios.addElement("CIRUGIA DE CABEZA Y CUELLO");
        modelServicios.addElement("CIRUGIA GENERAL");
        modelServicios.addElement("CIRUGIA PEDIATRICA");
        modelServicios.addElement("CIRUGIA PLASTICA");
        modelServicios.addElement("DENSITOMETRIA");
        modelServicios.addElement("ECOGRAFÍA");
        modelServicios.addElement("ENDOCRINOLOGÍA");
        modelServicios.addElement("GASTROENTEROLOGÍA");
        modelServicios.addElement("GERIATRIA");
        modelServicios.addElement("GINECOLOGÍA");
        modelServicios.addElement("HEMATOLOGÍA");
        modelServicios.addElement("INFERTILIDAD");
        modelServicios.addElement("LABORATORIO");
        modelServicios.addElement("MEDICINA ESTETICA");
        modelServicios.addElement("MEDICINA GENERAL");
        modelServicios.addElement("NEUMOLOGÍA");
        modelServicios.addElement("NEUROLOGÍA");
        modelServicios.addElement("NUTRICIÓN");
        modelServicios.addElement("ODONTOLOGÍA");
        modelServicios.addElement("OFTALMOLOGÍA");
        modelServicios.addElement("OTORRINOLARINGOLOGÍA");
        modelServicios.addElement("PEDIATRIA");
        modelServicios.addElement("PODOLOGÍA");
        modelServicios.addElement("PSICOLOGÍA");
        modelServicios.addElement("PSIQUIATRIA");
        modelServicios.addElement("RAYOS X DIGITAL");
        modelServicios.addElement("RESONANCIA");
        modelServicios.addElement("REUMATOLOGÍA");
        modelServicios.addElement("TERAPIA DEL DOLOR");
        modelServicios.addElement("TERAPIA FISICA Y REHABILITACIÓN");
        modelServicios.addElement("TOMOGRAFÍA");
        modelServicios.addElement("TOPICO DE URGENCIAS");
        modelServicios.addElement("TRAUMATOLOGÍA");
        modelServicios.addElement("UROLOGÍA");

        jListServicios.repaint();
        
        
        
        modelServiciosAgregados.addElement("RAYOS X DIGITAL / SJM0333 / ABDOMEN DE PIE");
        modelServiciosAgregados.addElement("RAYOS X DIGITAL SJM03311 BRAZO (HUMERO FRONTAL Y LATERAL)");
        
        jListServicioAgregados.repaint();
        
        
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        cerrarVentana(false);
    }

    private void jListServicios_mouseClicked(MouseEvent e) {
        

    }

    private void jListServicios_propertyChange(PropertyChangeEvent e) {
        
    }
}
