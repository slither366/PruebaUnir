package mifarma.ptoventa.centromedico;



import componentes.gs.componentes.DocumentFilterTextArea;
import componentes.gs.componentes.FarmaHora;
import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JComboWithCheck;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JNumericField;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import componentes.gs.componentes.OptionComboBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.print.PrinterJob;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;

import java.util.Collections;
import java.util.Date;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.print.DocPrintJob;
import javax.print.PrintService;

import javax.print.PrintServiceLookup;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import javax.swing.border.TitledBorder;

import javax.swing.text.JTextComponent;

import javax.swing.text.PlainDocument;

import common.FarmaColumnData;
import common.FarmaConnection;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaTableComparator;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import con_general.AutoSuggestor;

import mifarma.ptoventa.centromedico.domain.BeanPaciente;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedDiagnostico;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedEnfermedadActual;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedExamenFisico;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedExamenesAuxiliares;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedOtros;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedTrataReceta;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedTrataRecetaDetalle;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedTratamiento;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtMedTriaje;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtencionMedica;
import mifarma.ptoventa.centromedico.domain.historiaclinica.BeanHCAntecedentes;
import mifarma.ptoventa.centromedico.reference.FacadeAtencioMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.reference.UtilityPtoVenta;

public class DlgHHSurAtencionMedica extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaEspera.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    
    private JPanelWhite pnlContenedor = new JPanelWhite();
    
    private JTabbedPane tabpContenedor = new JTabbedPane();
    
    private JPanel pnlEnfermedadActual = new JPanel();
    private JButtonLabel lblMotivoConsulta = new JButtonLabel();
    private JTextFieldSanSerif txtMotivoConsulta = new JTextFieldSanSerif();
    private JButtonLabel lblTiempoEnfermedad = new JButtonLabel();
    private JTextFieldSanSerif txtTiempoEnfermedad = new JTextFieldSanSerif();
    private JButtonLabel lblTipoInformante = new JButtonLabel();
    //private JComboBox cmbTipoInformante = new JComboBox();
    private JComboWithCheck cmbTipoInformante = new JComboWithCheck();
    private JButtonLabel lblFormaInicio = new JButtonLabel();
    private JTextFieldSanSerif txtFormaInicio = new JTextFieldSanSerif();
    private JButtonLabel lblSignos = new JButtonLabel();
    //private JTextFieldSanSerif txtSignos = new JTextFieldSanSerif();
    private JTextArea txtSignos = new JTextArea();
    private JButtonLabel lblRelatoCronologico = new JButtonLabel();
    private JScrollPane scrRelatoCronologico = new JScrollPane();
    private JTextArea txtRelatoCronologico = new JTextArea();
    private JButtonLabel lblSintomas = new JButtonLabel();
    private JTextFieldSanSerif txtSintomas = new JTextFieldSanSerif();
    private JPanel pnlFuncionesBiologicas = new JPanel();
    private JButtonLabel lblApetito = new JButtonLabel();
    //private JComboBox cmbApetito = new JComboBox();
    private JComboWithCheck cmbApetito = new JComboWithCheck();
    private JButtonLabel lblSed = new JButtonLabel();
    //private JComboBox cmbSed = new JComboBox();
    private JComboWithCheck cmbSed = new JComboWithCheck();
    private JButtonLabel lblSuenio = new JButtonLabel();
    //private JComboBox cmbSuenio = new JComboBox();
    private JComboWithCheck cmbSuenio = new JComboWithCheck();
    private JButtonLabel lblOrina = new JButtonLabel();
    //private JComboBox cmbOrina = new JComboBox();
    private JComboWithCheck cmbOrina = new JComboWithCheck();
    private JButtonLabel lblDeposiciones = new JButtonLabel();
    //private JComboBox cmbDeposiciones = new JComboBox();
    private JComboWithCheck cmbDeposiciones = new JComboWithCheck();
    
    private JPanel pnlExamenFisico = new JPanel();
    private JPanel pnlFuncionesVitales = new JPanel();
    private JButtonLabel lblPA = new JButtonLabel();
    private JNumericField txtPA1 = new JNumericField(3, JNumericField.NUMERIC);
    private JLabel lblPA1 = new JLabel();
    private JNumericField txtPA2 = new JNumericField(3, JNumericField.NUMERIC);
    private JLabel lblPA2 = new JLabel();
    private JButtonLabel lblFR = new JButtonLabel();
    private JNumericField txtFR = new JNumericField(3, JNumericField.NUMERIC);
    private JLabel lblFR1 = new JLabel();
    private JButtonLabel lblFC = new JButtonLabel();
    private JNumericField txtFC = new JNumericField(3, JNumericField.NUMERIC);
    private JLabel lblFC1 = new JLabel();
    private JButtonLabel lblT = new JButtonLabel();
    private JNumericField txtT = new JNumericField(3, JNumericField.DECIMAL);
    private JLabel lblT1 = new JLabel();
    private JButtonLabel lblPeso = new JButtonLabel();
    private JNumericField txtPeso = new JNumericField(3, JNumericField.DECIMAL);
    private JLabel lblPeso1 = new JLabel();
    private JButtonLabel lblTalla = new JButtonLabel();
    private JNumericField txtTalla = new JNumericField(3, JNumericField.DECIMAL);
    private JLabel lblTalla1 = new JLabel();
    private JPanel pnlExamenFisicoInter = new JPanel();
    private JButtonLabel lblEstadoGeneral = new JButtonLabel();
    //private JComboBox cmbEstadoGeneral = new JComboBox();
    private JComboWithCheck cmbEstadoGeneral = new JComboWithCheck();
    private JButtonLabel lblEstadoConciencia = new JButtonLabel();
    private JTextFieldSanSerif txtEstadoConciencia = new JTextFieldSanSerif();
    private JButtonLabel lblExamenFisicoDirigido = new JButtonLabel();
    private JScrollPane scrFisicoDirigido = new JScrollPane();
    private JTextArea txtFisicoDirigido = new JTextArea();
    
    private JPanel pnlDiagnostico = new JPanel();
    private JButtonLabel lblDiagnostico = new JButtonLabel();
    private JTextFieldSanSerif txtCodDiagnostico = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescDiagnostico = new JTextFieldSanSerif();
    //private JComboBox cmbTipoDiagnostico = new JComboBox();
    private JComboWithCheck cmbTipoDiagnostico = new JComboWithCheck();
    private JPanelTitle pnlTitleListaDiagnostico = new JPanelTitle();
    private JButtonLabel lblTituloListaDiagnostico = new JButtonLabel();
    private JScrollPane scrListaDiagnostico = new JScrollPane();
    private JTable tblListaDiagnostico = new JTable();
    private FarmaTableModel mdlTblDiagnostico;
    
    private JPanel pnlTratamiento = new JPanel();
    private JButtonLabel lblTratamientoProducto = new JButtonLabel();
    private JTextField txtProductoTratamiento = new JTextField();
    private JButtonLabel lblFrecuenciaTratamiento = new JButtonLabel();
    private JNumericField txtFrecuenciaTratamiento = new JNumericField(2, JNumericField.NUMERIC);
    private JButtonLabel lblDuracionTratamiento = new JButtonLabel();
    private JNumericField txtDuracionTratamiento = new JNumericField(2, JNumericField.NUMERIC);
    private JButtonLabel lblViaAdministracion = new JButtonLabel();
    //private JComboBox cmbViaAdministracion = new JComboBox();
    private JComboWithCheck cmbViaAdministracion = new JComboWithCheck();
    private JPanelTitle pnlTitleListaReceta = new JPanelTitle();
    private JButtonLabel lblTitleListaReceta = new JButtonLabel();
    private JScrollPane scrListaReceta = new JScrollPane();
    private JTable tblListaReceta = new JTable();
    private FarmaTableModel mdlTblReceta;
    private JButtonLabel lblValidezReceta = new JButtonLabel();
    private JTextFieldSanSerif txtValidezReceta = new JTextFieldSanSerif();
    private JButtonLabel lblIndicacionesGeneralesTratamiento = new JButtonLabel();
    private JScrollPane scrIndicacionesGeneralesTratamiento = new JScrollPane();
    private JTextArea txtIndicacionesGeneralesTratamiento = new JTextArea();
    
    private JPanel pnlExamenesAuxiliares = new JPanel();
    private JButtonLabel lblExaAuxLaborotarios = new JButtonLabel();
    private JScrollPane scrExaAuxLaborotarios = new JScrollPane();
    private JTextArea txtExaAuxLaborotarios = new JTextArea();
    private JButtonLabel lblExaAuxImagenologicos = new JButtonLabel();
    private JScrollPane scrExaAuxImagenologicos = new JScrollPane();
    private JTextArea txtExaAuxImagenologicos = new JTextArea();


    private JPanel pnlProcedimiento = new JPanel();
    private JPanel pnlFichaProcedimientos = new JPanel();
    private JPanel pnlFichaImagenes = new JPanel();
    private JPanel pnlFichaLaboratorio = new JPanel();
    private JPanel pnlFichaLaboratorio2 = new JPanel();
    private JButtonLabel lblProcedimiento = new JButtonLabel();
    private JScrollPane scrProcedimiento = new JScrollPane();
    private JTextArea txtProcedimiento = new JTextArea();
    private JButtonLabel lblInterconsulta = new JButtonLabel();
    private JScrollPane scrInterconsulta = new JScrollPane();
    private JTextArea txtInterconsulta = new JTextArea();
    private JButtonLabel lblTransferencia = new JButtonLabel();
    private JScrollPane scrTransferencia = new JScrollPane();
    private JTextArea txtTransferencia = new JTextArea();
    private JButtonLabel lblDescansoMedico = new JButtonLabel();
    private JTextFieldSanSerif txtDescansoMedicoInicio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescansoMedicoFin = new JTextFieldSanSerif();
    private JButtonLabel lblProximaCita = new JButtonLabel();
    private JTextFieldSanSerif txtProximaCita = new JTextFieldSanSerif();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel lblCantidad = new JButtonLabel();
    private JLabelOrange lblSugerido = new JLabelOrange();
    private JNumericField txtCantidadTratamiento = new JNumericField(5, JNumericField.NUMERIC);
    private JLabel lblUnidadRecetada = new JLabel();
    private JButtonLabel lblDosis = new JButtonLabel();
    private JTextFieldSanSerif txtDosis = new JTextFieldSanSerif();
    private JLabelOrange lblTipoDiagnostico = new JLabelOrange();
    private JLabelFunction lblF4 = new JLabelFunction();
    
    private transient BeanAtencionMedica beanAtencionMedica;
    private transient BeanPaciente beanPaciente;
    private String codDiagnosticoAux  = "";
    private String codProdReceta = "";
    private String codRucEmpresa = "";
    private int valMaxFracProdReceta = 0;
    private int valFracProdReceta = 0;
    private ArrayList<ArrayList<String>> lstProductosReceta = new ArrayList<ArrayList<String>>();
    
    private boolean imprimioReceta = false;
    private boolean graboReceta = false;
    
    private boolean modoVisual = false;
    private JNumericField txtCantDiasValidezReceta = new JNumericField(2, JNumericField.NUMERIC);
    private JLabelOrange lblFechaValidezReceta = new JLabelOrange();
    
    private transient UtilityAtencionMedica utility = new UtilityAtencionMedica();
    private JButtonFunction btnAgregarDiagnostico = new JButtonFunction();
    private JButtonFunction btnQuitarDiagnostico = new JButtonFunction();
    private JButtonFunction btnAgregarReceta = new JButtonFunction();
    private JButtonFunction btnQuitarReceta = new JButtonFunction();
    
    private JLabel lblMensajeDiagnostico = new JLabel();
    private ArrayList<ArrayList<String>> lstDiagnostico;
    private int contadorEnter = 0;
    
    private JScrollPane scrPnlTxtSignos = new JScrollPane();
    private boolean atencionNueva;
    private JPanel pnlBotonesFuncion = new JPanel();
    private boolean bandImpresion = false;
    private String atencion = "";
    private JLabel lblSaturacion = new JLabel();
    
    private JNumericField txtSaturacionOxigeno = new JNumericField(4, JNumericField.DECIMAL);
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtCurso = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel lblCodigoTratamiento = new JLabel();
    private JButton jButton1 = new JButton();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanel pnlBotonesFuncion1 = new JPanel();
    private XYLayout xYLayout1 = new XYLayout();
    private JPanel jPanel1 = new JPanel();
    private JCheckBox jCheckBox1 = new JCheckBox();
    private XYLayout xYLayout2 = new XYLayout();
    private JCheckBox jCheckBox2 = new JCheckBox();
    private JCheckBox jCheckBox3 = new JCheckBox();
    private JCheckBox jCheckBox4 = new JCheckBox();
    private JCheckBox jCheckBox5 = new JCheckBox();
    private JCheckBox jCheckBox6 = new JCheckBox();
    private JPanel jPanel2 = new JPanel();
    private XYLayout xYLayout3 = new XYLayout();
    private JCheckBox jCheckBox7 = new JCheckBox();
    private JCheckBox jCheckBox8 = new JCheckBox();
    private JPanel jPanel3 = new JPanel();
    private JCheckBox jCheckBox12 = new JCheckBox();
    private JCheckBox jCheckBox13 = new JCheckBox();
    private JCheckBox jCheckBox14;
    private JCheckBox jCheckBox15 = new JCheckBox();
    private JCheckBox jCheckBox16 = new JCheckBox();
    private JCheckBox jCheckBox17;
    private JCheckBox jCheckBox18 = new JCheckBox();
    private JCheckBox jCheckBox19 = new JCheckBox();
    private JCheckBox jCheckBox110 = new JCheckBox();
    private JCheckBox jCheckBox111 = new JCheckBox();
    private JCheckBox jCheckBox112 = new JCheckBox();
    private JPanel jPanel4 = new JPanel();
    private JCheckBox jCheckBox113 = new JCheckBox();
    private JCheckBox jCheckBox114 = new JCheckBox();
    private JCheckBox jCheckBox115 = new JCheckBox();
    private JCheckBox jCheckBox116 = new JCheckBox();
    private JCheckBox jCheckBox117 = new JCheckBox();
    private JPanel jPanel5 = new JPanel();
    private JLabel jLabel5 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JLabel jLabel6 = new JLabel();
    private JTextField jTextField2 = new JTextField();
    private JPanel jPanel6 = new JPanel();
    private JPanel jPanel7 = new JPanel();
    private JPanel jPanel8 = new JPanel();
    private JCheckBox jCheckBox20 = new JCheckBox();
    private JCheckBox jCheckBox21 = new JCheckBox();
    private JCheckBox jCheckBox22 = new JCheckBox();
    private JCheckBox jCheckBox23 = new JCheckBox();
    private JCheckBox jCheckBox24 = new JCheckBox();
    private JCheckBox jCheckBox25 = new JCheckBox();
    private JCheckBox jCheckBox26 = new JCheckBox();
    private JCheckBox jCheckBox27 = new JCheckBox();
    private JCheckBox jCheckBox28 = new JCheckBox();
    private JCheckBox jCheckBox29 = new JCheckBox();
    private JCheckBox jCheckBox210 = new JCheckBox();
    private JCheckBox jCheckBox211 = new JCheckBox();
    private JCheckBox jCheckBox118 = new JCheckBox();
    private JCheckBox jCheckBox119 = new JCheckBox();
    private JCheckBox jCheckBox212 = new JCheckBox();
    private JCheckBox jCheckBox120 = new JCheckBox();
    private JPanel jPanel9 = new JPanel();
    private JCheckBox jCheckBox1110 = new JCheckBox();
    private JCheckBox jCheckBox1111 = new JCheckBox();
    private JCheckBox jCheckBox1112 = new JCheckBox();
    private JCheckBox jCheckBox1113 = new JCheckBox();
    private JCheckBox jCheckBox1114 = new JCheckBox();
    private JCheckBox jCheckBox1115 = new JCheckBox();
    private JCheckBox jCheckBox1116 = new JCheckBox();
    private JCheckBox jCheckBox1117 = new JCheckBox();
    private JCheckBox jCheckBox1118 = new JCheckBox();
    private JCheckBox jCheckBox1119 = new JCheckBox();
    private JCheckBox jCheckBox11110 = new JCheckBox();
    private JCheckBox jCheckBox11111 = new JCheckBox();
    private JCheckBox jCheckBox11112 = new JCheckBox();
    private JCheckBox jCheckBox11113 = new JCheckBox();
    private JCheckBox jCheckBox11114 = new JCheckBox();
    private JCheckBox jCheckBox11115 = new JCheckBox();
    private JPanel jPanel10 = new JPanel();
    private JPanel jPanel11 = new JPanel();
    private JPanel jPanel12 = new JPanel();
    private JCheckBox jCheckBox121 = new JCheckBox();
    private JCheckBox jCheckBox122 = new JCheckBox();
    private JCheckBox jCheckBox123 = new JCheckBox();
    private JCheckBox jCheckBox11116 = new JCheckBox();
    private JCheckBox jCheckBox11117 = new JCheckBox();
    private JCheckBox jCheckBox11118 = new JCheckBox();
    private JCheckBox jCheckBox11119 = new JCheckBox();
    private JCheckBox jCheckBox111110 = new JCheckBox();
    private JCheckBox jCheckBox11120 = new JCheckBox();
    private JCheckBox jCheckBox11121 = new JCheckBox();
    private JCheckBox jCheckBox11122 = new JCheckBox();
    private JCheckBox jCheckBox11123 = new JCheckBox();
    private JPanel jPanel13 = new JPanel();
    private JPanel jPanel14 = new JPanel();
    private JPanel jPanel16 = new JPanel();
    private JPanel jPanel17 = new JPanel();
    private JPanel jPanel19 = new JPanel();
    private JPanel jPanel20 = new JPanel();
    private JPanel jPanel21 = new JPanel();
    private JPanel jPanel23 = new JPanel();
    private JPanel jPanel25 = new JPanel();
    private JPanel jPanel18 = new JPanel();
    private JPanel jPanel110 = new JPanel();
    private JPanel jPanel111 = new JPanel();
    private JPanel jPanel112 = new JPanel();
    private JPanel jPanel113 = new JPanel();
    private JPanel jPanel114 = new JPanel();
    private JPanel jPanel115 = new JPanel();
    private JPanel jPanel116 = new JPanel();
    private JPanel jPanel117 = new JPanel();
    private JPanel jPanel118 = new JPanel();
    private JLabel jLabel7 = new JLabel();
    private JCheckBox jCheckBox30 = new JCheckBox();
    private JLabel jLabel8 = new JLabel();
    private JCheckBox jCheckBox34 = new JCheckBox();
    private JLabel jLabel9 = new JLabel();
    private JCheckBox jCheckBox39 = new JCheckBox();
    private JCheckBox jCheckBox311 = new JCheckBox();
    private JCheckBox jCheckBox318 = new JCheckBox();
    private JCheckBox jCheckBox3113 = new JCheckBox();
    private JLabel jLabel10 = new JLabel();
    private JCheckBox jCheckBox320 = new JCheckBox();
    private JCheckBox jCheckBox322;
    private JLabel jLabel11 = new JLabel();
    private JCheckBox jCheckBox328 = new JCheckBox();
    private JLabel jLabel12 = new JLabel();
    private JCheckBox jCheckBox3114 = new JCheckBox();
    private JPanel jPanel24 = new JPanel();
    private JPanel jPanel26 = new JPanel();
    private JPanel jPanel27 = new JPanel();
    private JCheckBox jCheckBox329 = new JCheckBox();
    private JLabel jLabel13 = new JLabel();
    private JCheckBox jCheckBox3212 = new JCheckBox();
    private JCheckBox jCheckBox3213 = new JCheckBox();
    private JLabel jLabel14 = new JLabel();
    private JTextField jTextField3 = new JTextField();
    private JCheckBox jCheckBox3115 = new JCheckBox();
    private JCheckBox jCheckBox3116;
    private JCheckBox jCheckBox3117 = new JCheckBox();
    private JCheckBox jCheckBox3118 = new JCheckBox();
    private JCheckBox jCheckBox3119 = new JCheckBox();
    private JCheckBox jCheckBox31110 = new JCheckBox();
    private JCheckBox jCheckBox31111 = new JCheckBox();
    private JCheckBox jCheckBox31112 = new JCheckBox();
    private JCheckBox jCheckBox31113 = new JCheckBox();
    private JCheckBox jCheckBox126 = new JCheckBox();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JPanel jPanel28 = new JPanel();
    private JCheckBox jCheckBox319 = new JCheckBox();
    private JCheckBox jCheckBox3110 = new JCheckBox();
    private JCheckBox jCheckBox31114 = new JCheckBox();
    private JCheckBox jCheckBox31115 = new JCheckBox();
    private JCheckBox jCheckBox31116 = new JCheckBox();
    private JCheckBox jCheckBox31117 = new JCheckBox();
    private JPanel jPanel29 = new JPanel();
    private JPanel jPanel211 = new JPanel();
    private JPanel jPanel212 = new JPanel();
    private JPanel jPanel213 = new JPanel();
    private JPanel jPanel214 = new JPanel();
    private JPanel jPanel215 = new JPanel();
    private JCheckBox jCheckBox31118 = new JCheckBox();
    private JCheckBox jCheckBox31119 = new JCheckBox();
    private JCheckBox jCheckBox311110 = new JCheckBox();
    private JCheckBox jCheckBox311111 = new JCheckBox();
    private JCheckBox jCheckBox311112 = new JCheckBox();
    private JCheckBox jCheckBox311113 = new JCheckBox();
    private JLabel jLabel15 = new JLabel();
    private JTextField jTextField4 = new JTextField();
    private JCheckBox jCheckBox311114 = new JCheckBox();
    private JCheckBox jCheckBox311115 = new JCheckBox();
    private JCheckBox jCheckBox311116 = new JCheckBox();
    private JCheckBox jCheckBox311117 = new JCheckBox();
    private JCheckBox jCheckBox311118 = new JCheckBox();
    private JCheckBox jCheckBox311119 = new JCheckBox();
    private JLabel jLabel17 = new JLabel();
    private JTextField jTextField6 = new JTextField();
    private JCheckBox jCheckBox3111110 = new JCheckBox();
    private JCheckBox jCheckBox3111111 = new JCheckBox();
    private JCheckBox jCheckBox3111112 = new JCheckBox();
    private JCheckBox jCheckBox3111113 = new JCheckBox();
    private JTextField jTextField7 = new JTextField();
    private JLabel jLabel18 = new JLabel();
    private JCheckBox jCheckBox3111114 = new JCheckBox();
    private JCheckBox jCheckBox3111115 = new JCheckBox();
    private JCheckBox jCheckBox3111116 = new JCheckBox();
    private JCheckBox jCheckBox3111117 = new JCheckBox();
    private JCheckBox jCheckBox3111118 = new JCheckBox();
    private JCheckBox jCheckBox3111119 = new JCheckBox();
    private JCheckBox jCheckBox31111110 = new JCheckBox();
    private JCheckBox jCheckBox31111111 = new JCheckBox();
    private JCheckBox jCheckBox31111112 = new JCheckBox();
    private JCheckBox jCheckBox31111113 = new JCheckBox();
    private JTextField jTextField5 = new JTextField();
    private JLabel jLabel16 = new JLabel();
    private JCheckBox jCheckBox3111120 = new JCheckBox();
    private JCheckBox jCheckBox3111121 = new JCheckBox();
    private JCheckBox jCheckBox3111122 = new JCheckBox();
    private JCheckBox jCheckBox3111123 = new JCheckBox();
    private JCheckBox jCheckBox3111124 = new JCheckBox();
    private JCheckBox jCheckBox3111125 = new JCheckBox();
    private JCheckBox jCheckBox3111126 = new JCheckBox();
    private JCheckBox jCheckBox3111127 = new JCheckBox();
    private JCheckBox jCheckBox3111128 = new JCheckBox();
    private JCheckBox jCheckBox3111129 = new JCheckBox();
    private JCheckBox jCheckBox31111210 = new JCheckBox();
    private JCheckBox jCheckBox31111211 = new JCheckBox();
    private JCheckBox jCheckBox31111212 = new JCheckBox();
    private JCheckBox jCheckBox31111213 = new JCheckBox();
    private JCheckBox jCheckBox31111214 = new JCheckBox();
    private JCheckBox jCheckBox31111215 = new JCheckBox();
    private JCheckBox jCheckBox31111216 = new JCheckBox();
    private JCheckBox jCheckBox31111217 = new JCheckBox();
    private JCheckBox jCheckBox31111218 = new JCheckBox();
    private JCheckBox jCheckBox31111219 = new JCheckBox();
    private JCheckBox jCheckBox311112110 = new JCheckBox();
    private JCheckBox jCheckBox311112111 = new JCheckBox();
    private JCheckBox jCheckBox311112112 = new JCheckBox();
    private JCheckBox jCheckBox31111114 = new JCheckBox();
    private JCheckBox jCheckBox31111115 = new JCheckBox();
    private JCheckBox jCheckBox31111116 = new JCheckBox();
    private JCheckBox jCheckBox31111117 = new JCheckBox();
    private JCheckBox jCheckBox31111118 = new JCheckBox();
    private JCheckBox jCheckBox311112113 = new JCheckBox();
    private JCheckBox jCheckBox311112114 = new JCheckBox();
    private JCheckBox jCheckBox311112115;
    private JCheckBox jCheckBox311112116 = new JCheckBox();
    private JCheckBox jCheckBox311120 = new JCheckBox();
    private JCheckBox jCheckBox311112117 = new JCheckBox();
    private JCheckBox jCheckBox311121 = new JCheckBox();
    private JCheckBox jCheckBox311122 = new JCheckBox();
    private JCheckBox jCheckBox311112118 = new JCheckBox();
    private JCheckBox jCheckBox311112119 = new JCheckBox();
    private JCheckBox jCheckBox3111121110 = new JCheckBox();
    private JCheckBox jCheckBox3111121111 = new JCheckBox();
    private JCheckBox jCheckBox3111121112 = new JCheckBox();
    private JCheckBox jCheckBox3111121113 = new JCheckBox();
    private JCheckBox jCheckBox3111121114 = new JCheckBox();
    private JCheckBox jCheckBox3111121115 = new JCheckBox();
    private JCheckBox jCheckBox3111121116 = new JCheckBox();
    private JCheckBox jCheckBox3111121117 = new JCheckBox();
    private JCheckBox jCheckBox3111121118 = new JCheckBox();
    private JCheckBox jCheckBox3111121119 = new JCheckBox();
    private JLabel jLabel19 = new JLabel();
    private JLabel jLabel110;
    private JTextField jTextField8 = new JTextField();
    private JLabel jLabel111 = new JLabel();
    private JPanel jPanel15 = new JPanel();
    private JPanel jPanel22 = new JPanel();
    private JPanel jPanel30 = new JPanel();
    private JPanel jPanel31 = new JPanel();
    private JPanel jPanel32 = new JPanel();
    private JPanel jPanel33 = new JPanel();
    private JPanel jPanel34 = new JPanel();
    private JPanel jPanel35 = new JPanel();
    private JCheckBox jCheckBox9 = new JCheckBox();
    private JCheckBox jCheckBox10 = new JCheckBox();
    private JCheckBox jCheckBox11 = new JCheckBox();
    private JCheckBox jCheckBox124 = new JCheckBox();
    private JCheckBox jCheckBox125 = new JCheckBox();
    private JCheckBox jCheckBox127 = new JCheckBox();
    private JCheckBox jCheckBox128 = new JCheckBox();
    private JCheckBox jCheckBox129 = new JCheckBox();
    private JCheckBox jCheckBox1210 = new JCheckBox();
    private JCheckBox jCheckBox1211 = new JCheckBox();
    private JCheckBox jCheckBox1212 = new JCheckBox();
    private JCheckBox jCheckBox1213 = new JCheckBox();
    private JCheckBox jCheckBox1214 = new JCheckBox();
    private JCheckBox jCheckBox1215 = new JCheckBox();
    private JCheckBox jCheckBox1216 = new JCheckBox();
    private JCheckBox jCheckBox1217 = new JCheckBox();
    private JCheckBox jCheckBox1218 = new JCheckBox();
    private JCheckBox jCheckBox1219 = new JCheckBox();
    private JCheckBox jCheckBox12110 = new JCheckBox();
    private JCheckBox jCheckBox1220;
    private JCheckBox jCheckBox1221 = new JCheckBox();
    private JCheckBox jCheckBox1222 = new JCheckBox();
    private JCheckBox jCheckBox1223 = new JCheckBox();
    private JCheckBox jCheckBox1224 = new JCheckBox();
    private JCheckBox jCheckBox1225 = new JCheckBox();
    private JCheckBox jCheckBox1226 = new JCheckBox();
    private JCheckBox jCheckBox1227 = new JCheckBox();
    private JCheckBox jCheckBox1228;
    private JCheckBox jCheckBox1229 = new JCheckBox();
    private JCheckBox jCheckBox12210 = new JCheckBox();
    private JCheckBox jCheckBox12211 = new JCheckBox();
    private JCheckBox jCheckBox12212 = new JCheckBox();
    private JCheckBox jCheckBox12213 = new JCheckBox();
    private JCheckBox jCheckBox12214 = new JCheckBox();
    private JCheckBox jCheckBox12215;
    private JCheckBox jCheckBox12216 = new JCheckBox();
    private JCheckBox jCheckBox12217 = new JCheckBox();
    private JCheckBox jCheckBox12218 = new JCheckBox();
    private JCheckBox jCheckBox12219 = new JCheckBox();
    private JCheckBox jCheckBox122110 = new JCheckBox();
    private JCheckBox jCheckBox122111 = new JCheckBox();
    private JCheckBox jCheckBox122112 = new JCheckBox();
    private JCheckBox jCheckBox122113 = new JCheckBox();
    private JCheckBox jCheckBox122114 = new JCheckBox();
    private JCheckBox jCheckBox122115 = new JCheckBox();
    private JCheckBox jCheckBox122116 = new JCheckBox();
    private JCheckBox jCheckBox122117 = new JCheckBox();
    private JCheckBox jCheckBox122118 = new JCheckBox();
    private JCheckBox jCheckBox1221110 = new JCheckBox();
    private JCheckBox jCheckBox1221111 = new JCheckBox();
    private JCheckBox jCheckBox1221112 = new JCheckBox();
    private JCheckBox jCheckBox1221113 = new JCheckBox();
    private JCheckBox jCheckBox1221114 = new JCheckBox();
    private JCheckBox jCheckBox1221115 = new JCheckBox();
    private JCheckBox jCheckBox1221116 = new JCheckBox();
    private JCheckBox jCheckBox1221117 = new JCheckBox();
    private JCheckBox jCheckBox1221118 = new JCheckBox();
    private JCheckBox jCheckBox1221119 = new JCheckBox();
    private JCheckBox jCheckBox12211110 = new JCheckBox();
    private JCheckBox jCheckBox12211111 = new JCheckBox();
    private JCheckBox jCheckBox12211112 = new JCheckBox();
    private JCheckBox jCheckBox12211113 = new JCheckBox();
    private JCheckBox jCheckBox12211114 = new JCheckBox();
    private JCheckBox jCheckBox12211115 = new JCheckBox();
    private JCheckBox jCheckBox12211116 = new JCheckBox();
    private JCheckBox jCheckBox12211117 = new JCheckBox();
    private JCheckBox jCheckBox12211118 = new JCheckBox();
    private JCheckBox jCheckBox12211119 = new JCheckBox();
    private JCheckBox jCheckBox12211120 = new JCheckBox();
    private JCheckBox jCheckBox122111110 = new JCheckBox();
    private JCheckBox jCheckBox122111111 = new JCheckBox();
    private JCheckBox jCheckBox122111112 = new JCheckBox();
    private JCheckBox jCheckBox122111113 = new JCheckBox();
    private JCheckBox jCheckBox122111114 = new JCheckBox();
    private JCheckBox jCheckBox122111115 = new JCheckBox();
    private JCheckBox jCheckBox122111116 = new JCheckBox();
    private JCheckBox jCheckBox122111117 = new JCheckBox();
    private JCheckBox jCheckBox122111118 = new JCheckBox();
    private JCheckBox jCheckBox122111119 = new JCheckBox();
    private JCheckBox jCheckBox1221111110 = new JCheckBox();
    private JCheckBox jCheckBox1221111111 = new JCheckBox();
    private JCheckBox jCheckBox1221111112 = new JCheckBox();
    private JCheckBox jCheckBox1221111113 = new JCheckBox();
    private JCheckBox jCheckBox122119;
    private JCheckBox jCheckBox122120 = new JCheckBox();
    private JCheckBox jCheckBox122121 = new JCheckBox();
    private JCheckBox jCheckBox122122 = new JCheckBox();
    private JCheckBox jCheckBox122123 = new JCheckBox();
    private JCheckBox jCheckBox122124 = new JCheckBox();
    private JCheckBox jCheckBox122125 = new JCheckBox();
    private JCheckBox jCheckBox122126 = new JCheckBox();
    private JCheckBox jCheckBox122127 = new JCheckBox();
    private JCheckBox jCheckBox122128 = new JCheckBox();
    private JCheckBox jCheckBox122129 = new JCheckBox();
    private JCheckBox jCheckBox1221210 = new JCheckBox();
    private JCheckBox jCheckBox1221211 = new JCheckBox();
    private JCheckBox jCheckBox1221212 = new JCheckBox();
    private JCheckBox jCheckBox1221213 = new JCheckBox();
    private JCheckBox jCheckBox1221214 = new JCheckBox();
    private JCheckBox jCheckBox1221215 = new JCheckBox();
    private JCheckBox jCheckBox1221216 = new JCheckBox();
    private JCheckBox jCheckBox1221217 = new JCheckBox();
    private JCheckBox jCheckBox1221218 = new JCheckBox();
    private JCheckBox jCheckBox1221219 = new JCheckBox();
    private JCheckBox jCheckBox12212110 = new JCheckBox();
    private JCheckBox jCheckBox12212111 = new JCheckBox();
    private JCheckBox jCheckBox12212112 = new JCheckBox();
    private JCheckBox jCheckBox12212113 = new JCheckBox();
    private JCheckBox jCheckBox12212114 = new JCheckBox();
    private JCheckBox jCheckBox12212115 = new JCheckBox();
    private JCheckBox jCheckBox12212116 = new JCheckBox();
    private JCheckBox jCheckBox12212117 = new JCheckBox();
    private JCheckBox jCheckBox12212118 = new JCheckBox();
    private JCheckBox jCheckBox12212119 = new JCheckBox();
    private JCheckBox jCheckBox122121110 = new JCheckBox();
    private JCheckBox jCheckBox122121111 = new JCheckBox();
    private JCheckBox jCheckBox122121112 = new JCheckBox();
    private JPanel jPanel36 = new JPanel();
    private JCheckBox jCheckBox122121113 = new JCheckBox();
    private JCheckBox jCheckBox122121114 = new JCheckBox();
    private JCheckBox jCheckBox122121115 = new JCheckBox();
    private JCheckBox jCheckBox122121116 = new JCheckBox();
    private JCheckBox jCheckBox122121117 = new JCheckBox();
    private JCheckBox jCheckBox122121118 = new JCheckBox();
    private JCheckBox jCheckBox122121119 = new JCheckBox();
    private JCheckBox jCheckBox1221211110 = new JCheckBox();
    private JCheckBox jCheckBox1221211111 = new JCheckBox();
    private JCheckBox jCheckBox1221211112 = new JCheckBox();
    private JCheckBox jCheckBox1221211113 = new JCheckBox();
    private JCheckBox jCheckBox1221211114 = new JCheckBox();
    private JCheckBox jCheckBox1221211115 = new JCheckBox();
    private JCheckBox jCheckBox1221211116 = new JCheckBox();
    private JCheckBox jCheckBox1221211117 = new JCheckBox();
    private JCheckBox jCheckBox1221211118 = new JCheckBox();
    private JCheckBox jCheckBox1221211119 = new JCheckBox();
    private JCheckBox jCheckBox12212111110 = new JCheckBox();
    private JCheckBox jCheckBox12212111111 = new JCheckBox();
    private JCheckBox jCheckBox12212111112 = new JCheckBox();
    private JCheckBox jCheckBox12212111113 = new JCheckBox();
    private JCheckBox jCheckBox12212111114 = new JCheckBox();
    private JCheckBox jCheckBox12212111115 = new JCheckBox();
    private JCheckBox jCheckBox12212111116 = new JCheckBox();
    private JCheckBox jCheckBox12212111118 = new JCheckBox();
    private JCheckBox jCheckBox12212111119 = new JCheckBox();
    private JCheckBox jCheckBox122121111112 = new JCheckBox();
    private JCheckBox jCheckBox122121111113 = new JCheckBox();
    private JCheckBox jCheckBox122121111114 = new JCheckBox();
    private JCheckBox jCheckBox122121111117;
    private JCheckBox jCheckBox122121111118 = new JCheckBox();
    private JCheckBox jCheckBox122121111119 = new JCheckBox();
    private JCheckBox jCheckBox12212111117 = new JCheckBox();
    private JCheckBox jCheckBox1221211120 = new JCheckBox();
    private JCheckBox jCheckBox1221211121 = new JCheckBox();
    private JCheckBox jCheckBox1221211122 = new JCheckBox();
    private JPanel jPanel37 = new JPanel();
    private JCheckBox jCheckBox1221211123;
    private JCheckBox jCheckBox1221211124 = new JCheckBox();
    private JCheckBox jCheckBox1221211125 = new JCheckBox();
    private JCheckBox jCheckBox1221211126 = new JCheckBox();
    private JCheckBox jCheckBox1221211127 = new JCheckBox();
    private JPanel jPanel38 = new JPanel();
    private JPanel jPanel39 = new JPanel();
    private JCheckBox jCheckBox31 = new JCheckBox();
    private JCheckBox jCheckBox32 = new JCheckBox();
    private JCheckBox jCheckBox33 = new JCheckBox();
    private JCheckBox jCheckBox35 = new JCheckBox();
    private JCheckBox jCheckBox36 = new JCheckBox();
    private JCheckBox jCheckBox37 = new JCheckBox();
    private JCheckBox jCheckBox38 = new JCheckBox();
    private JCheckBox jCheckBox310 = new JCheckBox();
    private JCheckBox jCheckBox312 = new JCheckBox();
    private JCheckBox jCheckBox313 = new JCheckBox();
    private JCheckBox jCheckBox314 = new JCheckBox();
    private JCheckBox jCheckBox315 = new JCheckBox();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel21 = new JLabel();
    private JLabel jLabel22 = new JLabel();
    private JLabel jLabel23 = new JLabel();
    private JLabel jLabel24 = new JLabel();
    private JLabel jLabel25 = new JLabel();
    private JLabel jLabel26 = new JLabel();
    private JLabel jLabel28 = new JLabel();
    private JLabel jLabel29 = new JLabel();
    private JLabel jLabel210 = new JLabel();
    private JLabel jLabel27;
    private JLabel jLabel211 = new JLabel();
    private JLabel jLabel212 = new JLabel();
    private JLabel jLabel213;
    private JLabel jLabel214;
    private JLabel jLabel215 = new JLabel();
    private JLabel jLabel216 = new JLabel();
    private JLabel jLabel217 = new JLabel();
    private JLabel jLabel218 = new JLabel();
    private JLabel jLabel219;
    private JLabel jLabel2110 = new JLabel();
    private JLabel jLabel2111 = new JLabel();
    private JLabel jLabel2112 = new JLabel();
    private JLabel jLabel2113 = new JLabel();
    private JLabel jLabel2114 = new JLabel();
    private JLabel jLabel2115 = new JLabel();
    private JLabel jLabel2116 = new JLabel();
    private JLabel jLabel2117 = new JLabel();
    private JLabel jLabel2118 = new JLabel();
    private JCheckBox jCheckBox1221211128;
    private JCheckBox jCheckBox1221211129 = new JCheckBox();
    private JCheckBox jCheckBox12212111210 = new JCheckBox();
    private JCheckBox jCheckBox12212111211 = new JCheckBox();
    private JCheckBox jCheckBox12212111212 = new JCheckBox();
    private JCheckBox jCheckBox12212111213 = new JCheckBox();
    private JCheckBox jCheckBox12212111214 = new JCheckBox();
    private JCheckBox jCheckBox12212111215 = new JCheckBox();
    private JCheckBox jCheckBox12212111216 = new JCheckBox();
    private JCheckBox jCheckBox12212111217 = new JCheckBox();
    private JCheckBox jCheckBox12212111218 = new JCheckBox();
    private JCheckBox jCheckBox12212111219 = new JCheckBox();
    private JCheckBox jCheckBox122121112110 = new JCheckBox();
    private JCheckBox jCheckBox122121112111 = new JCheckBox();
    private JCheckBox jCheckBox122121112112 = new JCheckBox();
    private JCheckBox jCheckBox122121112113 = new JCheckBox();
    private JCheckBox jCheckBox122121112114 = new JCheckBox();
    private JCheckBox jCheckBox122121112115 = new JCheckBox();
    private JCheckBox jCheckBox122121112116 = new JCheckBox();
    private JCheckBox jCheckBox122121112117 = new JCheckBox();
    private JCheckBox jCheckBox122121112118 = new JCheckBox();
    private JCheckBox jCheckBox122121112119 = new JCheckBox();
    private JCheckBox jCheckBox1221211121110 = new JCheckBox();
    private JCheckBox jCheckBox1221211121111 = new JCheckBox();
    private JCheckBox jCheckBox1221211121112 = new JCheckBox();
    private JCheckBox jCheckBox122121112120 = new JCheckBox();
    private JPanel jPanel40 = new JPanel();
    private JCheckBox jCheckBox12212111220 = new JCheckBox();
    private JCheckBox jCheckBox12212111221 = new JCheckBox();
    private JCheckBox jCheckBox12212111222 = new JCheckBox();
    private JCheckBox jCheckBox12212111223 = new JCheckBox();
    private JCheckBox jCheckBox12212111224 = new JCheckBox();
    private JCheckBox jCheckBox12212111225 = new JCheckBox();
    private JCheckBox jCheckBox12212111226 = new JCheckBox();
    private JCheckBox jCheckBox12212111227 = new JCheckBox();
    private JCheckBox jCheckBox12212111228 = new JCheckBox();
    private JCheckBox jCheckBox12212111229 = new JCheckBox();
    private JCheckBox jCheckBox122121112210 = new JCheckBox();
    private JCheckBox jCheckBox122121112211 = new JCheckBox();
    private JPanel jPanel41 = new JPanel();
    private JLabel jLabel31 = new JLabel();
    private JTextField jTextField9 = new JTextField();
    //private JNumericField txtTalla = new JNumericField(3, JNumericField.DECIMAL);
    
    public DlgHHSurAtencionMedica() {
        this(null, "", false);
    }
        
    public DlgHHSurAtencionMedica(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
            inicialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1122, 665));
        //this.setSize(new Dimension(760, 505));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Consulta Médica");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        /************************************************************************/
        /************************ ENFERDAD ACTUAL *******************************/
        /************************************************************************/
        
        lblMotivoConsulta.setText("Motivo Consulta");
        lblMotivoConsulta.setBounds(new Rectangle(15, 10, 95, 20));
        lblMotivoConsulta.setForeground(new Color(0,99,148));
        lblMotivoConsulta.setToolTipText("se ingresa la causa del problema en la versi\u00f3n que proporciona el paciente");

        txtMotivoConsulta.setToolTipText("se ingresa la causa del problema en la versi\u00f3n que proporciona el paciente");
        txtMotivoConsulta.setLengthText(500);
        //txtMotivoConsulta.addActionListener(new ActionTransferFocus(txtFormaInicio));
        lblSignos.setText("Signos y Sintomas");
        lblSignos.setBounds(new Rectangle(15, 40, 110, 20));
        lblSignos.setForeground(new Color(0,99,148));
        lblSignos.setToolTipText("se ingresa signos y s\u00edntomas t\u00edpicos de la enfermedad");
        
        txtSignos.setToolTipText("se ingresa signos y s\u00edntomas t\u00edpicos de la enfermedad");
        txtSignos.setFont(new Font("SansSerif", 0, 11));
        txtSignos.setLineWrap(true);
        txtSignos.setWrapStyleWord(true);
        txtSignos.setTabSize(2);
        ((PlainDocument) txtSignos.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));
        //txtSignos.setBounds(new Rectangle(130, 70, 545, 20));
        //txtSignos.addActionListener(new ActionTransferFocus(txtFormaInicio));
        //.setBounds(new Rectangle(115, 40, 560, 20));
        //130, 70
        scrPnlTxtSignos.setBounds(new Rectangle(130, 40, 545, 50));


        lblFormaInicio.setText("Forma de Inicio");
        lblFormaInicio.setBounds(new Rectangle(20, 45, 95, 20));
        lblFormaInicio.setForeground(new Color(0,99,148));
        
        txtFormaInicio.setBounds(new Rectangle(115, 45, 565, 20));
        txtFormaInicio.setLengthText(500);
        
        lblTiempoEnfermedad.setText("* Tiempo Enfermedad :");
        lblTiempoEnfermedad.setBounds(new Rectangle(5, 110, 135, 20));
        lblTiempoEnfermedad.setForeground(new Color(198, 0, 0));
        lblTiempoEnfermedad.setToolTipText("Se ingresa el tiempo de evoluci\u00f3n de la enfermedad (d\u00edas)");
        
        txtTiempoEnfermedad.setBounds(new Rectangle(140, 110, 185, 20));
        txtTiempoEnfermedad.setToolTipText("Se ingresa el tiempo de evoluci\u00f3n de la enfermedad");
        //txtTiempoEnfermedad.set(false);
        
        lblTipoInformante.setText("* Tipo Informante");
        lblTipoInformante.setBounds(new Rectangle(340, 110, 110, 20));
        lblTipoInformante.setForeground(new Color(198, 0, 0));
        lblTipoInformante.setToolTipText("Directa: s\u00f3lo el paciente explica sus dolencias y s\u00edntomas, Indirecta: si el acompa\u00f1ante indica las dolencias y s\u00edntomas, Mixta: si el acompa\u00f1ante y el paciente indican las dolencias y s\u00edntomas");
        
        cmbTipoInformante.setBounds(new Rectangle(470, 110, 230, 20));
        cmbTipoInformante.setToolTipText("Directa: s\u00f3lo el paciente explica sus dolencias y s\u00edntomas, Indirecta: si el acompa\u00f1ante indica las dolencias y s\u00edntomas, Mixta: si el acompa\u00f1ante y el paciente indican las dolencias y s\u00edntomas");
        cmbTipoInformante.setSeleccionMultiple(false);
        
        lblRelatoCronologico.setText("Relato Cronol\u00f3gico");
        lblRelatoCronologico.setBounds(new Rectangle(20, 140, 115, 20));
        lblRelatoCronologico.setForeground(new Color(198, 0, 0));
        lblRelatoCronologico.setToolTipText("se registra como se ha presentado y evolucionado la enfermedad en el paciente");
        
        txtRelatoCronologico.setFont(new Font("SansSerif", 0, 11));
        txtRelatoCronologico.setLineWrap(true);
        txtRelatoCronologico.setTabSize(2);
        txtRelatoCronologico.setWrapStyleWord(true);
        txtRelatoCronologico.setToolTipText("se registra como se ha presentado y evolucionado la enfermedad en el paciente");
        ((PlainDocument) txtRelatoCronologico.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));
        
        scrRelatoCronologico.setBounds(new Rectangle(15, 165, 660, 100));

        lblApetito.setText("Apetito");
        lblApetito.setBounds(new Rectangle(10, 20, 45, 20));
        lblApetito.setForeground(new Color(0,99,148));
        lblApetito.setMnemonic('A');
        
        cmbApetito.setBounds(new Rectangle(55, 20, 135, 20));
        cmbApetito.setFont(new Font("SansSerif", 0, 11));
        cmbApetito.setSeleccionMultiple(false);
        
        lblSed.setText("Sed");
        lblSed.setBounds(new Rectangle(10, 55, 40, 20));
        lblSed.setForeground(new Color(0,99,148));
        
        cmbSed.setBounds(new Rectangle(55, 55, 135, 20));
        cmbSed.setFont(new Font("SansSerif", 0, 11));
        cmbSed.setSeleccionMultiple(false);

        lblSuenio.setText("Sue\u00f1o");
        lblSuenio.setBounds(new Rectangle(225, 20, 40, 20));
        lblSuenio.setForeground(new Color(0,99,148));
        
        cmbSuenio.setBounds(new Rectangle(265, 20, 135, 20));
        cmbSuenio.setFont(new Font("SansSerif", 0, 11));
        cmbSuenio.setSeleccionMultiple(false);

        cmbSuenio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cmbSuenio_actionPerformed(e);
                }
            });
        lblOrina.setText("Orina");
        lblOrina.setBounds(new Rectangle(225, 55, 40, 20));
        lblOrina.setForeground(new Color(0,99,148));
        
        cmbOrina.setBounds(new Rectangle(265, 55, 135, 20));
        cmbOrina.setFont(new Font("SansSerif", 0, 11));
        cmbOrina.setSeleccionMultiple(false);
        
        lblDeposiciones.setText("Deposiciones");
        lblDeposiciones.setBounds(new Rectangle(435, 20, 80, 20));
        lblDeposiciones.setForeground(new Color(0,99,148));
        
        cmbDeposiciones.setBounds(new Rectangle(520, 20, 135, 20));
        cmbDeposiciones.setFont(new Font("SansSerif", 0, 11));
        cmbDeposiciones.setSeleccionMultiple(false);
        //cmbDeposiciones.setNextObjTransferFocus(txtPA1);
        
        pnlFuncionesBiologicas.setBounds(new Rectangle(15, 270, 665, 90));
        pnlFuncionesBiologicas.setLayout(null);
        pnlFuncionesBiologicas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,99,148)),
                                                           "Funciones Biológicas",
                                                           TitledBorder.LEFT,
                                                           TitledBorder.DEFAULT_POSITION,
                                                           new Font("SansSerif",Font.PLAIN,12),
                                                           new Color(0,99,148)));

        pnlFuncionesBiologicas.setBackground(SystemColor.window);

        pnlFuncionesBiologicas.add(lblApetito, null);
        cmbApetito.add(txtRelatoCronologico, null);
        pnlFuncionesBiologicas.add(cmbApetito, null);
        pnlFuncionesBiologicas.add(lblSed, null);
        pnlFuncionesBiologicas.add(cmbSed, null);
        pnlFuncionesBiologicas.add(lblSuenio, null);
        cmbSuenio.add(txtMotivoConsulta, null);
        pnlFuncionesBiologicas.add(cmbSuenio, null);
        pnlFuncionesBiologicas.add(lblOrina, null);
        pnlFuncionesBiologicas.add(cmbOrina, null);
        pnlFuncionesBiologicas.add(lblDeposiciones, null);
        pnlFuncionesBiologicas.add(cmbDeposiciones, null);
        pnlEnfermedadActual.setLayout(null);

        pnlEnfermedadActual.setBackground(SystemColor.window);
        //pnlEnfermedadActual.add(txtSignos, null);
        //pnlEnfermedadActual.add(lblSintomas, null);
        //pnlEnfermedadActual.add(txtSintomas, null);
        pnlEnfermedadActual.add(jLabel3, null);
        pnlEnfermedadActual.add(txtCurso, null);
        pnlEnfermedadActual.add(jLabel2, null);
        pnlEnfermedadActual.add(lblMotivoConsulta, null);
        pnlEnfermedadActual.add(lblFormaInicio, null);
        pnlEnfermedadActual.add(txtFormaInicio, null);
        pnlEnfermedadActual.add(lblSignos, null);
        scrPnlTxtSignos.getViewport().add(txtSignos, null);
        pnlEnfermedadActual.add(scrPnlTxtSignos, null);


        /**********************************************************************/
        /************************ EXAMEN FISICO *******************************/
        /**********************************************************************/

        pnlEnfermedadActual.add(lblTiempoEnfermedad, null);
        pnlEnfermedadActual.add(txtTiempoEnfermedad, null);
        pnlEnfermedadActual.add(lblTipoInformante, null);
        pnlEnfermedadActual.add(cmbTipoInformante, null);
        pnlEnfermedadActual.add(lblRelatoCronologico, null);
        pnlEnfermedadActual.add(scrRelatoCronologico, null);
        pnlEnfermedadActual.add(pnlFuncionesBiologicas, null);
        lblPA.setText("P.A.");
        lblPA.setBounds(new Rectangle(10, 30, 25, 20));
        lblPA.setForeground(new Color(0,99,148));
        
        txtPA1.setBounds(new Rectangle(35, 30, 35, 20));
        
        txtPA2.setBounds(new Rectangle(80, 30, 35, 20));
        
        lblPA1.setText("/");
        lblPA1.setBounds(new Rectangle(70, 30, 10, 20));
        lblPA1.setHorizontalAlignment(SwingConstants.CENTER);
        lblPA1.setHorizontalTextPosition(SwingConstants.CENTER);
        
        lblPA2.setText("MMHG");
        lblPA2.setBounds(new Rectangle(115, 30, 35, 20));
        lblPA2.setFont(new Font("SansSerif", 0, 11));
        lblPA2.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblFR.setText("F.R.");
        lblFR.setBounds(new Rectangle(170, 30, 25, 20));
        lblFR.setForeground(new Color(0,99,148));
       
        txtFR.setBounds(new Rectangle(195, 30, 35, 20));
        
        lblFR1.setText("X'");
        lblFR1.setBounds(new Rectangle(230, 30, 15, 20));
        lblFR1.setFont(new Font("SansSerif", 0, 11));
        lblFR1.setHorizontalAlignment(SwingConstants.CENTER);
        lblFR1.setHorizontalTextPosition(SwingConstants.CENTER);
        
        lblFC.setText("F.C.");
        lblFC.setBounds(new Rectangle(265, 30, 20, 20));
        lblFC.setForeground(new Color(0,99,148));
        
        txtFC.setBounds(new Rectangle(285, 30, 35, 20));
        
        lblFC1.setText("X'");
        lblFC1.setBounds(new Rectangle(320, 30, 15, 20));
        lblFC1.setFont(new Font("SansSerif", 0, 11));
        lblFC1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblFC1.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblT.setText("Temp.");
        lblT.setBounds(new Rectangle(350, 30, 35, 20));
        lblT.setForeground(new Color(0,99,148));
        
        txtT.setBounds(new Rectangle(385, 30, 40, 20));
        
        lblT1.setText("\u00b0C");
        lblT1.setBounds(new Rectangle(425, 30, 15, 20));
        lblT1.setFont(new Font("SansSerif", 0, 11));
        lblT1.setHorizontalAlignment(SwingConstants.CENTER);
        lblT1.setHorizontalTextPosition(SwingConstants.CENTER);
        
        lblPeso.setText("Peso");
        lblPeso.setBounds(new Rectangle(455, 30, 30, 20));
        lblPeso.setForeground(new Color(0,99,148));
        
        txtPeso.setBounds(new Rectangle(485, 30, 45, 20));
        
        lblPeso1.setText("Kg");
        lblPeso1.setBounds(new Rectangle(530, 30, 20, 20));
        lblPeso1.setFont(new Font("SansSerif", 0, 11));
        lblPeso1.setHorizontalAlignment(SwingConstants.CENTER);
        lblPeso1.setHorizontalTextPosition(SwingConstants.CENTER);
        
        lblTalla.setText("Talla");
        lblTalla.setBounds(new Rectangle(570, 30, 30, 20));
        lblTalla.setForeground(new Color(0,99,148));
        
        txtTalla.setBounds(new Rectangle(600, 30, 45, 20));
        
        lblTalla1.setText("cm");
        lblTalla1.setBounds(new Rectangle(645, 30, 20, 20));
        lblTalla1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTalla1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTalla1.setFont(new Font("SansSerif", 0, 11));
        
        pnlFuncionesVitales.setBounds(new Rectangle(15, 0, 685, 110));
        pnlFuncionesVitales.setLayout(null);
        pnlFuncionesVitales.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,99,148)),
                                                           "Funciones Vitales",
                                                           TitledBorder.LEFT,
                                                           TitledBorder.DEFAULT_POSITION,
                                                           new Font("SansSerif",Font.PLAIN,12),
                                                           new Color(0,99,148)));

        pnlFuncionesVitales.setBackground(SystemColor.window);


        pnlFuncionesVitales.add(jLabel1, null);
        pnlFuncionesVitales.add(txtSaturacionOxigeno, null);
        pnlFuncionesVitales.add(lblSaturacion, null);

        pnlFuncionesVitales.add(lblPA, null);
        pnlFuncionesVitales.add(txtPA1, null);
        pnlFuncionesVitales.add(lblPA1, null);
        pnlFuncionesVitales.add(txtPA2, null);
        pnlFuncionesVitales.add(lblPA2, null);
        pnlFuncionesVitales.add(lblFR, null);
        pnlFuncionesVitales.add(txtFR, null);
        pnlFuncionesVitales.add(lblFR1, null);
        pnlFuncionesVitales.add(lblFC, null);
        pnlFuncionesVitales.add(txtFC, null);
        pnlFuncionesVitales.add(lblFC1, null);
        pnlFuncionesVitales.add(lblT, null);
        pnlFuncionesVitales.add(txtT, null);
        pnlFuncionesVitales.add(lblT1, null);
        pnlFuncionesVitales.add(lblPeso, null);
        pnlFuncionesVitales.add(txtPeso, null);
        pnlFuncionesVitales.add(lblPeso1, null);
        pnlFuncionesVitales.add(lblTalla, null);
        pnlFuncionesVitales.add(txtTalla, null);
        pnlFuncionesVitales.add(lblTalla1, null);
        lblEstadoGeneral.setText("Estado General");
        lblEstadoGeneral.setBounds(new Rectangle(15, 25, 85, 20));
        lblEstadoGeneral.setForeground(new Color(198, 0, 0));
        
        cmbEstadoGeneral.setBounds(new Rectangle(120, 25, 135, 20));
        cmbEstadoGeneral.setFont(new Font("SansSerif", 0, 11));
        cmbEstadoGeneral.setSeleccionMultiple(false);

        lblEstadoConciencia.setText("Estado Conciencia");
        lblEstadoConciencia.setBounds(new Rectangle(285, 25, 105, 20));
        lblEstadoConciencia.setForeground(new Color(198, 0, 0));
        
        txtEstadoConciencia.setBounds(new Rectangle(390, 25, 215, 20));
        txtEstadoConciencia.setLengthText(500);
        
        lblExamenFisicoDirigido.setText("Examen F\u00edsico Dirigido");
        lblExamenFisicoDirigido.setBounds(new Rectangle(15, 55, 135, 15));
        lblExamenFisicoDirigido.setForeground(new Color(198, 0, 0));
        
        txtFisicoDirigido.setWrapStyleWord(true);
        txtFisicoDirigido.setTabSize(2);
        txtFisicoDirigido.setLineWrap(true);
        txtFisicoDirigido.setFont(new Font("SansSerif", 0, 11));
        ((PlainDocument) txtFisicoDirigido.getDocument()).setDocumentFilter(new DocumentFilterTextArea(2000));
        
        scrFisicoDirigido.setBounds(new Rectangle(15, 75, 600, 155));

        pnlExamenFisicoInter.setBounds(new Rectangle(20, 110, 680, 240));
        pnlExamenFisicoInter.setLayout(null);
        pnlExamenFisicoInter.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,99,148)),
                                                           "Examen Físico",
                                                           TitledBorder.LEFT,
                                                           TitledBorder.DEFAULT_POSITION,
                                                           new Font("SansSerif",Font.PLAIN,12),
                                                           new Color(0,99,148)));

        pnlExamenFisicoInter.setBackground(SystemColor.window);
        pnlExamenFisicoInter.add(lblEstadoGeneral, null);
        pnlExamenFisicoInter.add(cmbEstadoGeneral, null);
        pnlExamenFisicoInter.add(lblEstadoConciencia, null);
        pnlExamenFisicoInter.add(txtEstadoConciencia, null);
        scrFisicoDirigido.getViewport().add(txtFisicoDirigido, null);
        pnlExamenFisicoInter.add(scrFisicoDirigido, null);

        pnlExamenFisicoInter.add(lblExamenFisicoDirigido, null);
        pnlExamenFisico.setLayout(null);

        pnlExamenFisico.setBackground(SystemColor.window);


        /********************************************************************/
        /************************ DIAGNOSTICO *******************************/
        /********************************************************************/


        pnlExamenFisico.add(jLabel4, null);
        pnlExamenFisico.add(pnlFuncionesVitales, null);
        pnlExamenFisico.add(pnlExamenFisicoInter, null);
        lblDiagnostico.setText("Diagn\u00f3stico");
        lblDiagnostico.setBounds(new Rectangle(25, 10, 70, 20));
        lblDiagnostico.setForeground(new Color(0,99,148));
        
        txtCodDiagnostico.setBounds(new Rectangle(25, 30, 660, 20));
        
        txtDescDiagnostico.setBounds(new Rectangle(110, 70, 385, 20));
        txtDescDiagnostico.setEditable(false);
        
        lblTipoDiagnostico.setText("Tipo");
        lblTipoDiagnostico.setBounds(new Rectangle(505, 50, 80, 20));
        
        cmbTipoDiagnostico.setBounds(new Rectangle(505, 70, 180, 20));
        cmbTipoDiagnostico.setFont(new Font("SansSerif", 0, 11));
        cmbTipoDiagnostico.setSeleccionMultiple(false);
        
        lblTituloListaDiagnostico.setText("Lista de Diagnostico");
        lblTituloListaDiagnostico.setBounds(new Rectangle(5, 0, 310, 25));
        lblTituloListaDiagnostico.setMnemonic('D');
        
        pnlTitleListaDiagnostico.setBounds(new Rectangle(15, 105, 675, 30));

        scrListaDiagnostico.setBounds(new Rectangle(15, 135, 675, 225));
        scrListaDiagnostico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlDiagnostico.setLayout(null);
        pnlDiagnostico.setBackground(SystemColor.window);

        /********************************************************************/
        /********************* TRATAMIENTO **********************************/
        /********************************************************************/


        pnlDiagnostico.add(lblMensajeDiagnostico, null);
        pnlDiagnostico.add(txtCodDiagnostico, null);
        pnlDiagnostico.add(lblDiagnostico, null);
        pnlDiagnostico.add(txtDescDiagnostico, null);
        pnlDiagnostico.add(lblTipoDiagnostico, null);
        pnlDiagnostico.add(cmbTipoDiagnostico, null);
        pnlDiagnostico.add(pnlTitleListaDiagnostico, null);
        scrListaDiagnostico.getViewport().add(tblListaDiagnostico, null);
        pnlDiagnostico.add(scrListaDiagnostico, null);

        pnlTitleListaDiagnostico.add(lblTituloListaDiagnostico, null);
        pnlTitleListaDiagnostico.add(btnAgregarDiagnostico, null);
        pnlTitleListaDiagnostico.add(btnQuitarDiagnostico, null);
        lblTratamientoProducto.setText("Producto");
        lblTratamientoProducto.setBounds(new Rectangle(15, 5, 65, 20));
        lblTratamientoProducto.setForeground(new Color(0,99,148));
        
        txtProductoTratamiento.setBounds(new Rectangle(15, 35, 520, 25));

        txtProductoTratamiento.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtProductoTratamiento_keyPressed(e);
                }
            });
        lblSugerido.setBounds(new Rectangle(545, 0, 170, 35));

        lblSugerido.setForeground(new Color(231, 0, 0));
        lblSugerido.setFont(new Font("SansSerif", 1, 12));
        lblSugerido.setBorder(BorderFactory.createTitledBorder("Se vende Sugerido en :"));
        lblSugerido.setHorizontalAlignment(SwingConstants.CENTER);
        lblUnidadRecetada.setBounds(new Rectangle(545, 40, 170, 35));
        //lblUnidadRecetada.setEnabled(false);

        lblUnidadRecetada.setFont(new Font("SansSerif", 1, 12));
        lblUnidadRecetada.setForeground(new Color(0, 99, 0));
        lblUnidadRecetada.setBorder(BorderFactory.createTitledBorder("Unidad a Recetar :"));
        lblUnidadRecetada.setHorizontalAlignment(SwingConstants.CENTER);
        lblFrecuenciaTratamiento.setText("Frecuencia (x d\u00eda)");
        lblFrecuenciaTratamiento.setBounds(new Rectangle(15, 70, 100, 20));
        lblFrecuenciaTratamiento.setForeground(new Color(0,99,148));
        
        txtFrecuenciaTratamiento.setBounds(new Rectangle(15, 90, 95, 20));
        txtFrecuenciaTratamiento.setEnabled(false);

        txtFrecuenciaTratamiento.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFrecuenciaTratamiento_keyPressed(e);
                }
            });
        lblDuracionTratamiento.setText("Duraci\u00f3n (d\u00edas)");
        lblDuracionTratamiento.setBounds(new Rectangle(125, 70, 85, 20));
        lblDuracionTratamiento.setForeground(new Color(0,99,148));
        
        txtDuracionTratamiento.setBounds(new Rectangle(125, 90, 80, 20));
        txtDuracionTratamiento.setEnabled(false);

        txtDuracionTratamiento.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDuracionTratamiento_keyPressed(e);
                }
            });
        lblViaAdministracion.setText("Via Administraci\u00f3n");
        lblViaAdministracion.setForeground(new Color(0,99,148));
        lblViaAdministracion.setBounds(new Rectangle(220, 70, 105, 20));
        
        cmbViaAdministracion.setBounds(new Rectangle(220, 90, 175, 20));
        cmbViaAdministracion.setFont(new Font("SansSerif", 0, 11));
        cmbViaAdministracion.setEnabled(false);
        cmbViaAdministracion.setSeleccionMultiple(false);

        cmbViaAdministracion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbViaAdministracion_keyPressed(e);
                }
            });
        lblDosis.setText("Dosis");
        lblDosis.setBounds(new Rectangle(410, 70, 40, 20));
        lblDosis.setForeground(new Color(0,99,148));
        
        txtDosis.setBounds(new Rectangle(410, 90, 225, 20));
        txtDosis.setEnabled(false);

        txtDosis.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDosis_keyPressed(e);
                }
            });
        lblCantidad.setText("Cantidad");
        lblCantidad.setBounds(new Rectangle(645, 70, 55, 20));
        lblCantidad.setForeground(new Color(0,99,148));
        
        txtCantidadTratamiento.setBounds(new Rectangle(645, 90, 60, 20));
        txtCantidadTratamiento.setEnabled(false);
        
        lblTitleListaReceta.setText("Resumen Receta");
        lblTitleListaReceta.setBounds(new Rectangle(5, 0, 280, 25));
        lblTitleListaReceta.setMnemonic('R');
        
        pnlTitleListaReceta.setBounds(new Rectangle(15, 115, 690, 30));

        scrListaReceta.setBounds(new Rectangle(15, 140, 690, 145));
        scrListaReceta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lblValidezReceta.setText("Validez de Receta (días): ");
        lblValidezReceta.setBounds(new Rectangle(15, 290, 140, 20));
        lblValidezReceta.setForeground(new Color(0,99,148));
        //lblValidezReceta.addActionListener(new ActionTransferFocus(txtValidezReceta));
        
        txtValidezReceta.setBounds(new Rectangle(245, 290, 105, 20));
        //txtValidezReceta.addActionListener(new ActionTransferFocus(txtIndicacionesGeneralesTratamiento));
        
        lblIndicacionesGeneralesTratamiento.setText("Recomendaciones Generales");
        lblIndicacionesGeneralesTratamiento.setBounds(new Rectangle(15, 310, 260, 20));
        lblIndicacionesGeneralesTratamiento.setForeground(new Color(0,99,148));
        
        txtIndicacionesGeneralesTratamiento.setWrapStyleWord(true);
        txtIndicacionesGeneralesTratamiento.setTabSize(2);
        txtIndicacionesGeneralesTratamiento.setFont(new Font("SansSerif", 0, 11));
        txtIndicacionesGeneralesTratamiento.setLineWrap(true);
        
        scrIndicacionesGeneralesTratamiento.setBounds(new Rectangle(15, 335, 685, 55));

        pnlTratamiento.setLayout(null);

        pnlTratamiento.setBackground(SystemColor.window);
        pnlTratamiento.add(lblCodigoTratamiento, null);
        pnlTratamiento.add(lblFechaValidezReceta, null);
        pnlTratamiento.add(txtCantDiasValidezReceta, null);
        pnlTratamiento.add(lblTratamientoProducto, null);
        pnlTratamiento.add(txtProductoTratamiento, null);
        pnlTratamiento.add(lblSugerido, null);
        pnlTratamiento.add(lblUnidadRecetada, null);
        pnlTratamiento.add(lblFrecuenciaTratamiento, null);
        pnlTratamiento.add(txtFrecuenciaTratamiento, null);
        pnlTratamiento.add(lblDuracionTratamiento, null);
        pnlTratamiento.add(txtDuracionTratamiento, null);
        pnlTratamiento.add(lblViaAdministracion, null);
        pnlTratamiento.add(cmbViaAdministracion, null);
        pnlTratamiento.add(lblDosis, null);
        pnlTratamiento.add(txtDosis, null);
        pnlTratamiento.add(lblCantidad, null);
        pnlTratamiento.add(txtCantidadTratamiento, null);
        pnlTratamiento.add(pnlTitleListaReceta, null);
        scrIndicacionesGeneralesTratamiento.getViewport().add(txtIndicacionesGeneralesTratamiento, null);
        pnlTratamiento.add(scrIndicacionesGeneralesTratamiento, null);


        /*************************************************************************************/
        /***************************** EXAMENES AUXILIARES ***********************************/
        /*************************************************************************************/

        pnlTratamiento.add(lblValidezReceta, null);
        pnlTratamiento.add(txtValidezReceta, null);
        scrListaReceta.getViewport().add(tblListaReceta, null);
        pnlTratamiento.add(scrListaReceta, null);
        pnlTratamiento.add(lblIndicacionesGeneralesTratamiento, null);
        pnlTitleListaReceta.add(lblTitleListaReceta, null);
        pnlTitleListaReceta.add(btnAgregarReceta, null);
        pnlTitleListaReceta.add(btnQuitarReceta, null);
        lblExaAuxLaborotarios.setText("Laboratorios");
        lblExaAuxLaborotarios.setBounds(new Rectangle(20, 15, 85, 15));
        lblExaAuxLaborotarios.setForeground(new Color(0,99,148));
        
        txtExaAuxLaborotarios.setWrapStyleWord(true);
        txtExaAuxLaborotarios.setTabSize(2);
        txtExaAuxLaborotarios.setLineWrap(true);
        txtExaAuxLaborotarios.setFont(new Font("SansSerif", 0, 11));
        ((PlainDocument) txtExaAuxLaborotarios.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));
        
        scrExaAuxLaborotarios.setBounds(new Rectangle(20, 35, 670, 125));

        lblExaAuxImagenologicos.setText("Imagenologicos");
        lblExaAuxImagenologicos.setBounds(new Rectangle(20, 170, 105, 15));
        lblExaAuxImagenologicos.setForeground(new Color(0,99,148));
        
        txtExaAuxImagenologicos.setFont(new Font("SansSerif", 0, 11));
        txtExaAuxImagenologicos.setLineWrap(true);
        txtExaAuxImagenologicos.setTabSize(2);
        txtExaAuxImagenologicos.setWrapStyleWord(true);
        ((PlainDocument) txtExaAuxImagenologicos.getDocument()).setDocumentFilter(new DocumentFilterTextArea(250));
        
        scrExaAuxImagenologicos.setBounds(new Rectangle(20, 190, 670, 120));

        pnlExamenesAuxiliares.setLayout(null);

        pnlExamenesAuxiliares.setBackground(SystemColor.window);


        /*********************************************************************/
        /**********************PROCEDIMIENTO***********************************/
        /*********************************************************************/


        pnlExamenesAuxiliares.add(lblExaAuxLaborotarios, null);
        scrExaAuxLaborotarios.getViewport().add(txtExaAuxLaborotarios, null);
        pnlExamenesAuxiliares.add(scrExaAuxLaborotarios, null);
        pnlExamenesAuxiliares.add(lblExaAuxImagenologicos, null);
        scrExaAuxImagenologicos.getViewport().add(txtExaAuxImagenologicos, null);
        pnlExamenesAuxiliares.add(scrExaAuxImagenologicos, null);
        lblProcedimiento.setText("Procedimiento");
        lblProcedimiento.setBounds(new Rectangle(10, 5, 85, 15));
        lblProcedimiento.setForeground(new Color(0,99,148));
        
        txtProcedimiento.setWrapStyleWord(true);
        txtProcedimiento.setTabSize(2);
        txtProcedimiento.setFont(new Font("SansSerif", 0, 11));
        txtProcedimiento.setLineWrap(true);
        ((PlainDocument) txtProcedimiento.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));

        scrProcedimiento.setBounds(new Rectangle(10, 20, 695, 70));
        
        lblInterconsulta.setText("Interconsulta");
        lblInterconsulta.setBounds(new Rectangle(10, 95, 85, 15));
        lblInterconsulta.setForeground(new Color(0,99,148));
        
        txtInterconsulta.setWrapStyleWord(true);
        txtInterconsulta.setTabSize(2);
        txtInterconsulta.setLineWrap(true);
        txtInterconsulta.setFont(new Font("SansSerif", 0, 11));
        ((PlainDocument) txtInterconsulta.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));
        
        scrInterconsulta.setBounds(new Rectangle(10, 115, 695, 95));

        lblTransferencia.setText("Transferencia");
        lblTransferencia.setBounds(new Rectangle(10, 215, 85, 15));
        lblTransferencia.setForeground(new Color(0,99,148));
        
        txtTransferencia.setWrapStyleWord(true);
        txtTransferencia.setTabSize(2);
        txtTransferencia.setLineWrap(true);
        txtTransferencia.setFont(new Font("SansSerif", 0, 11));
        ((PlainDocument) txtTransferencia.getDocument()).setDocumentFilter(new DocumentFilterTextArea(500));
        
        scrTransferencia.setBounds(new Rectangle(10, 230, 695, 95));

        lblDescansoMedico.setText("Descanso M\u00e9dico");
        lblDescansoMedico.setBounds(new Rectangle(40, 340, 105, 20));
        lblDescansoMedico.setForeground(new Color(0,99,148));
        
        txtDescansoMedicoInicio.setBounds(new Rectangle(160, 340, 100, 20));
        
        txtDescansoMedicoFin.setBounds(new Rectangle(275, 340, 100, 20));
        
        lblProximaCita.setText("Fecha Proxima Cita");
        lblProximaCita.setBounds(new Rectangle(430, 340, 110, 20));
        lblProximaCita.setForeground(new Color(0,99,148));
        
        txtProximaCita.setBounds(new Rectangle(545, 340, 120, 20));
        
        pnlProcedimiento.setLayout(null);

        pnlProcedimiento.setBackground(SystemColor.window);
        pnlProcedimiento.add(lblProcedimiento, null);
        scrProcedimiento.getViewport().add(txtProcedimiento, null);
        pnlProcedimiento.add(scrProcedimiento, null);
        pnlProcedimiento.add(lblInterconsulta, null);
        scrInterconsulta.getViewport().add(txtInterconsulta, null);
        pnlProcedimiento.add(scrInterconsulta, null);

        pnlProcedimiento.add(lblTransferencia, null);
        scrTransferencia.getViewport().add(txtTransferencia, null);
        pnlProcedimiento.add(scrTransferencia, null);
        pnlProcedimiento.add(lblDescansoMedico, null);
        pnlProcedimiento.add(txtDescansoMedicoInicio, null);
        pnlProcedimiento.add(txtDescansoMedicoFin, null);
        pnlProcedimiento.add(lblProximaCita, null);
        pnlProcedimiento.add(txtProximaCita, null);
        pnlFichaProcedimientos.setLayout(null);
        pnlFichaProcedimientos.setBackground(SystemColor.window);
        pnlFichaImagenes.setLayout(null);
        pnlFichaImagenes.setBackground(SystemColor.window);
        pnlFichaLaboratorio.setLayout(null);
        pnlFichaLaboratorio.setBackground(SystemColor.window);
        pnlFichaLaboratorio2.setLayout(null);
        pnlFichaLaboratorio2.setBackground(SystemColor.window);

        /************************************************************************/
        /*********************************************************************/


        txtCantDiasValidezReceta.setBounds(new Rectangle(155, 290, 35, 20));
        lblFechaValidezReceta.setText("Fecha:");
        lblFechaValidezReceta.setBounds(new Rectangle(205, 290, 40, 20));
        btnAgregarDiagnostico.setText("Agregar");
        btnAgregarDiagnostico.setBounds(new Rectangle(465, 5, 90, 20));
        btnAgregarDiagnostico.setFont(new Font("SansSerif", 1, 11));
        btnQuitarDiagnostico.setText("Quitar");
        btnQuitarDiagnostico.setBounds(new Rectangle(580, 5, 90, 20));
        btnQuitarDiagnostico.setFont(new Font("SansSerif", 1, 11));
        btnAgregarReceta.setText("Agregar");
        btnAgregarReceta.setBounds(new Rectangle(455, 5, 100, 20));
        btnAgregarReceta.setFont(new Font("SansSerif", 1, 11));
        btnAgregarReceta.setActionCommand("[ + ]");
        btnQuitarReceta.setText("Quitar");
        btnQuitarReceta.setBounds(new Rectangle(585, 5, 100, 20));
        btnQuitarReceta.setFont(new Font("SansSerif", 1, 11));
        lblMensajeDiagnostico.setText("Presione [Enter] para agregar diagnostico");
        lblMensajeDiagnostico.setBounds(new Rectangle(25, 50, 470, 15));

        lblEsc.setText("[ ESC ] Salir");
        lblEsc.setPreferredSize(new Dimension(130, 20));

        lblF4.setText("[ F4 ] Cambiar Panel");
        lblF4.setPreferredSize(new Dimension(130, 20));

        lblF2.setText("[ F2 ] Antecedentes");
        lblF6.setText("[ F6 ] Imprimir HC");
        lblF2.setPreferredSize(new Dimension(130, 20));
        lblF6.setPreferredSize(new Dimension(130, 20));

        lblF5.setText("[ F5 ] Historia Clinica");
        lblF5.setPreferredSize(new Dimension(130, 20));

        lblF11.setText("[ F11 ] Grabar");
        lblF11.setPreferredSize(new Dimension(130, 20));

        tabpContenedor.addTab("pnlEnfermedadActual", pnlEnfermedadActual);
        tabpContenedor.addTab("Examen Físico", pnlExamenFisico);
        tabpContenedor.addTab("Diagnóstico", pnlDiagnostico);
        tabpContenedor.addTab("Tratamiento", pnlTratamiento);
        tabpContenedor.addTab("Examenes Auxiliares", pnlExamenesAuxiliares);
        tabpContenedor.addTab("pnlProcedimiento", pnlProcedimiento);
        tabpContenedor.addTab("Procedimientos", pnlFichaProcedimientos);
        tabpContenedor.addTab("Imagenes", pnlFichaImagenes);
        tabpContenedor.addTab("Laboratorio-1", pnlFichaLaboratorio);
        tabpContenedor.addTab("Laboratorio-2", pnlFichaLaboratorio2);
        jPanel35.add(jCheckBox1221213, null);
        jPanel35.add(jCheckBox1221212, null);
        jPanel35.add(jCheckBox1221211, null);
        jPanel35.add(jCheckBox1221210, null);
        jPanel35.add(jCheckBox122129, null);
        jPanel35.add(jCheckBox122128, null);
        jPanel35.add(jCheckBox122127, null);
        jPanel35.add(jCheckBox122126, null);
        jPanel35.add(jCheckBox122125, null);
        jPanel35.add(jCheckBox122124, null);
        jPanel35.add(jCheckBox122123, null);
        jPanel35.add(jCheckBox122122, null);
        jPanel35.add(jCheckBox122121, null);
        jPanel35.add(jCheckBox122120, null);
        jPanel36.add(jCheckBox12212111117, null);
        jPanel36.add(jCheckBox122121111119, null);
        jPanel36.add(jCheckBox122121111118, null);
        jPanel36.add(jCheckBox122121111114, null);
        jPanel36.add(jCheckBox122121111113, null);
        jPanel36.add(jCheckBox122121111112, null);
        jPanel36.add(jCheckBox12212111119, null);
        jPanel36.add(jCheckBox12212111118, null);
        jPanel36.add(jCheckBox12212111116, null);
        jPanel36.add(jCheckBox12212111115, null);
        jPanel36.add(jCheckBox12212111114, null);
        jPanel36.add(jCheckBox12212111113, null);
        jPanel36.add(jCheckBox12212111112, null);
        jPanel36.add(jCheckBox12212111111, null);
        jPanel36.add(jCheckBox12212111110, null);
        jPanel36.add(jCheckBox1221211119, null);
        jPanel36.add(jCheckBox1221211118, null);
        jPanel36.add(jCheckBox1221211117, null);
        jPanel36.add(jCheckBox1221211116, null);
        jPanel36.add(jCheckBox1221211115, null);
        jPanel36.add(jCheckBox1221211114, null);
        jPanel36.add(jCheckBox1221211113, null);
        jPanel36.add(jCheckBox1221211112, null);
        jPanel36.add(jCheckBox1221211111, null);
        jPanel36.add(jCheckBox1221211110, null);
        jPanel36.add(jCheckBox122121119, null);
        jPanel36.add(jCheckBox122121118, null);
        jPanel36.add(jCheckBox122121117, null);
        jPanel36.add(jCheckBox122121116, null);
        jPanel36.add(jCheckBox122121115, null);
        jPanel36.add(jCheckBox122121114, null);
        jPanel36.add(jCheckBox122121113, null);
        jPanel37.add(jCheckBox1221211127, null);
        jPanel37.add(jCheckBox1221211126, null);
        jPanel37.add(jCheckBox1221211125, null);
        jPanel37.add(jCheckBox1221211124, null);
        jPanel39.add(jLabel2118, null);
        jPanel39.add(jLabel2117, null);
        jPanel39.add(jLabel2116, null);
        jPanel39.add(jLabel2115, null);
        jPanel39.add(jLabel2114, null);
        jPanel39.add(jLabel2113, null);
        jPanel39.add(jLabel2112, null);
        jPanel39.add(jLabel2111, null);
        jPanel39.add(jLabel2110, null);
        jPanel39.add(jLabel218, null);
        jPanel39.add(jLabel217, null);
        jPanel39.add(jLabel216, null);
        jPanel39.add(jLabel215, null);
        jPanel39.add(jLabel212, null);
        jPanel39.add(jLabel211, null);
        jPanel39.add(jLabel210, null);
        jPanel39.add(jLabel29, null);
        jPanel39.add(jLabel28, null);
        jPanel39.add(jLabel26, null);
        jPanel39.add(jLabel25, null);
        jPanel39.add(jLabel24, null);
        jPanel39.add(jLabel23, null);
        jPanel39.add(jLabel22, null);
        jPanel39.add(jLabel21, null);
        jPanel39.add(jLabel20, null);
        jPanel39.add(jCheckBox315, null);
        jPanel39.add(jCheckBox314, null);
        jPanel39.add(jCheckBox313, null);
        jPanel39.add(jCheckBox312, null);
        jPanel39.add(jCheckBox310, null);
        jPanel39.add(jCheckBox38, null);
        jPanel39.add(jCheckBox37, null);
        jPanel39.add(jCheckBox36, null);
        jPanel39.add(jCheckBox35, null);
        jPanel39.add(jCheckBox33, null);
        jPanel39.add(jCheckBox32, null);
        jPanel39.add(jCheckBox31, null);
        pnlFichaLaboratorio.add(jPanel39, null);
        pnlFichaLaboratorio.add(jPanel37, null);
        pnlFichaLaboratorio.add(jPanel36, null);
        pnlFichaLaboratorio.add(jPanel35, null);
        pnlFichaLaboratorio.add(jPanel22, null);
        pnlFichaLaboratorio.add(jPanel15, null);
        jPanel38.add(jCheckBox122121112120, null);
        jPanel38.add(jCheckBox1221211121112, null);
        jPanel38.add(jCheckBox1221211121111, null);
        jPanel38.add(jCheckBox1221211121110, null);
        jPanel38.add(jCheckBox122121112119, null);
        jPanel38.add(jCheckBox122121112118, null);
        jPanel38.add(jCheckBox122121112117, null);
        jPanel38.add(jCheckBox122121112116, null);
        jPanel38.add(jCheckBox122121112115, null);
        jPanel38.add(jCheckBox122121112114, null);
        jPanel38.add(jCheckBox122121112113, null);
        jPanel38.add(jCheckBox122121112112, null);
        jPanel38.add(jCheckBox122121112111, null);
        jPanel38.add(jCheckBox122121112110, null);
        jPanel38.add(jCheckBox12212111219, null);
        jPanel38.add(jCheckBox12212111218, null);
        jPanel38.add(jCheckBox12212111217, null);
        jPanel38.add(jCheckBox12212111216, null);
        jPanel38.add(jCheckBox12212111215, null);
        jPanel38.add(jCheckBox12212111214, null);
        jPanel38.add(jCheckBox12212111213, null);
        jPanel38.add(jCheckBox12212111212, null);
        jPanel38.add(jCheckBox12212111211, null);
        jPanel38.add(jCheckBox12212111210, null);
        jPanel38.add(jCheckBox1221211129, null);
        jPanel40.add(jCheckBox122121112211, null);
        jPanel40.add(jCheckBox122121112210, null);
        jPanel40.add(jCheckBox12212111229, null);
        jPanel40.add(jCheckBox12212111228, null);
        jPanel40.add(jCheckBox12212111227, null);
        jPanel40.add(jCheckBox12212111226, null);
        jPanel40.add(jCheckBox12212111225, null);
        jPanel40.add(jCheckBox12212111224, null);
        jPanel40.add(jCheckBox12212111223, null);
        jPanel40.add(jCheckBox12212111222, null);
        jPanel40.add(jCheckBox12212111221, null);
        jPanel40.add(jCheckBox12212111220, null);
        jPanel41.add(jTextField9, null);
        jPanel41.add(jLabel31, null);
        pnlFichaLaboratorio2.add(jPanel30, null);
        pnlFichaLaboratorio2.add(jPanel41, null);
        pnlFichaLaboratorio2.add(jPanel40, null);
        pnlFichaLaboratorio2.add(jPanel38, null);
        pnlFichaLaboratorio2.add(jPanel34, null);
        pnlFichaLaboratorio2.add(jPanel33, null);
        pnlFichaLaboratorio2.add(jPanel32, null);
        jPanel34.add(jCheckBox1221211122, null);
        jPanel34.add(jCheckBox1221211121, null);
        jPanel34.add(jCheckBox1221211120, null);
        jPanel33.add(jCheckBox12212115, null);
        jPanel33.add(jCheckBox12212114, null);
        jPanel33.add(jCheckBox12212113, null);
        jPanel33.add(jCheckBox12212112, null);
        jPanel33.add(jCheckBox12212111, null);
        jPanel33.add(jCheckBox12212110, null);
        jPanel33.add(jCheckBox1221219, null);
        jPanel33.add(jCheckBox1221218, null);
        jPanel33.add(jCheckBox1221217, null);
        jPanel33.add(jCheckBox1221216, null);
        jPanel33.add(jCheckBox1221215, null);
        jPanel33.add(jCheckBox1221214, null);
        jPanel32.add(jCheckBox122121112, null);
        jPanel32.add(jCheckBox122121111, null);
        jPanel32.add(jCheckBox122121110, null);
        jPanel32.add(jCheckBox12212119, null);
        jPanel32.add(jCheckBox12212118, null);
        jPanel32.add(jCheckBox12212117, null);
        jPanel32.add(jCheckBox12212116, null);
        jPanel30.add(jCheckBox1221111113, null);
        jPanel30.add(jCheckBox1221111112, null);
        jPanel30.add(jCheckBox1221111111, null);
        jPanel30.add(jCheckBox1221111110, null);
        jPanel30.add(jCheckBox122111119, null);
        jPanel30.add(jCheckBox122111118, null);
        jPanel30.add(jCheckBox122111117, null);
        jPanel30.add(jCheckBox122111116, null);
        jPanel30.add(jCheckBox122111115, null);
        jPanel30.add(jCheckBox122111114, null);
        jPanel30.add(jCheckBox122111113, null);
        jPanel30.add(jCheckBox122111112, null);
        jPanel30.add(jCheckBox122111111, null);
        jPanel30.add(jCheckBox122111110, null);
        jPanel30.add(jCheckBox12211120, null);
        jPanel30.add(jCheckBox12211119, null);
        jPanel30.add(jCheckBox12211118, null);
        jPanel30.add(jCheckBox12211117, null);
        jPanel30.add(jCheckBox12211116, null);
        jPanel30.add(jCheckBox12211115, null);
        jPanel30.add(jCheckBox12211114, null);
        jPanel30.add(jCheckBox12211113, null);
        jPanel30.add(jCheckBox12211112, null);
        jPanel30.add(jCheckBox12211111, null);
        jPanel30.add(jCheckBox12211110, null);
        jPanel30.add(jCheckBox1221119, null);
        jPanel30.add(jCheckBox1221118, null);
        jPanel30.add(jCheckBox1221117, null);
        jPanel30.add(jCheckBox1221116, null);
        jPanel30.add(jCheckBox1221115, null);
        jPanel30.add(jCheckBox1221114, null);
        jPanel30.add(jCheckBox1221113, null);
        jPanel30.add(jCheckBox1221112, null);
        jPanel30.add(jCheckBox1221111, null);
        jPanel30.add(jCheckBox1221110, null);
        jPanel30.add(jCheckBox122118, null);
        jPanel30.add(jCheckBox122117, null);
        jPanel30.add(jCheckBox122116, null);
        jPanel30.add(jCheckBox122115, null);
        jPanel30.add(jCheckBox122114, null);
        jPanel30.add(jCheckBox122113, null);
        jPanel30.add(jCheckBox122112, null);
        jPanel30.add(jCheckBox122111, null);
        jPanel30.add(jCheckBox122110, null);
        jPanel30.add(jCheckBox12219, null);
        jPanel30.add(jCheckBox12218, null);
        jPanel30.add(jCheckBox12217, null);
        jPanel30.add(jCheckBox12216, null);
        jPanel30.add(jCheckBox12214, null);
        jPanel22.add(jCheckBox12213, null);
        jPanel22.add(jCheckBox12212, null);
        jPanel22.add(jCheckBox12211, null);
        jPanel22.add(jCheckBox12210, null);
        jPanel22.add(jCheckBox1229, null);
        jPanel22.add(jCheckBox1227, null);
        jPanel22.add(jCheckBox1226, null);
        jPanel22.add(jCheckBox1225, null);
        jPanel22.add(jCheckBox1224, null);
        jPanel22.add(jCheckBox1223, null);
        jPanel22.add(jCheckBox1222, null);
        jPanel22.add(jCheckBox1221, null);
        jPanel15.add(jCheckBox12110, null);
        jPanel15.add(jCheckBox1219, null);
        jPanel15.add(jCheckBox1218, null);
        jPanel15.add(jCheckBox1217, null);
        jPanel15.add(jCheckBox1216, null);
        jPanel15.add(jCheckBox1215, null);
        jPanel15.add(jCheckBox1214, null);
        jPanel15.add(jCheckBox1213, null);
        jPanel15.add(jCheckBox1212, null);
        jPanel15.add(jCheckBox1211, null);
        jPanel15.add(jCheckBox1210, null);
        jPanel15.add(jCheckBox129, null);
        jPanel15.add(jCheckBox128, null);
        jPanel15.add(jCheckBox127, null);
        jPanel15.add(jCheckBox125, null);
        jPanel15.add(jCheckBox124, null);
        jPanel15.add(jCheckBox11, null);
        jPanel15.add(jCheckBox10, null);
        jPanel15.add(jCheckBox9, null);
        jPanel23.add(jCheckBox3111121112, null);
        jPanel23.add(jCheckBox3111121111, null);
        jPanel23.add(jCheckBox3111121110, null);
        jPanel23.add(jCheckBox311112118, null);
        jPanel23.add(jCheckBox311112119, null);
        pnlFichaImagenes.add(jPanel23, null);
        pnlFichaImagenes.add(jPanel21, null);
        pnlFichaImagenes.add(jPanel20, null);
        pnlFichaImagenes.add(jPanel14, null);
        pnlFichaImagenes.add(jPanel13, null);
        jPanel20.add(jCheckBox3111121115, null);
        jPanel20.add(jCheckBox3111121114, null);
        jPanel20.add(jCheckBox3111121113, null);
        jPanel21.add(jCheckBox311122, null);
        jPanel21.add(jCheckBox311121, null);
        jPanel21.add(jCheckBox311112117, null);
        jPanel21.add(jCheckBox311120, null);
        jPanel21.add(jCheckBox311112116, null);
        jPanel21.add(jCheckBox311112114, null);
        jPanel215.add(jLabel19, null);
        jPanel215.add(jTextField4, null);
        jPanel215.add(jLabel15, null);
        jPanel215.add(jCheckBox311113, null);
        jPanel215.add(jCheckBox311112, null);
        jPanel215.add(jCheckBox311111, null);
        jPanel215.add(jCheckBox311110, null);
        jPanel215.add(jCheckBox31119, null);
        jPanel215.add(jCheckBox31118, null);
        jPanel214.add(jCheckBox311118, null);
        jPanel214.add(jCheckBox311117, null);
        jPanel214.add(jCheckBox311116, null);
        jPanel214.add(jCheckBox311115, null);
        jPanel214.add(jCheckBox311114, null);
        jPanel14.add(jPanel215, null);
        jPanel14.add(jPanel214, null);
        jPanel14.add(jPanel213, null);
        jPanel14.add(jPanel212, null);
        jPanel14.add(jPanel29, null);
        jPanel14.add(jPanel19, null);
        jPanel14.add(jPanel211, null);
        jPanel211.add(jCheckBox31111118, null);
        jPanel211.add(jCheckBox31111117, null);
        jPanel211.add(jCheckBox31111116, null);
        jPanel211.add(jCheckBox31111115, null);
        jPanel211.add(jCheckBox31111114, null);
        jPanel212.add(jLabel111, null);
        jPanel212.add(jTextField8, null);
        jPanel212.add(jCheckBox3111121119, null);
        jPanel212.add(jCheckBox3111121118, null);
        jPanel212.add(jCheckBox3111121117, null);
        jPanel212.add(jCheckBox3111121116, null);
        jPanel212.add(jCheckBox311112113, null);
        jPanel212.add(jCheckBox311112112, null);
        jPanel212.add(jCheckBox311112111, null);
        jPanel212.add(jCheckBox311112110, null);
        jPanel212.add(jCheckBox31111219, null);
        jPanel212.add(jCheckBox31111218, null);
        jPanel212.add(jCheckBox31111217, null);
        jPanel212.add(jCheckBox31111216, null);
        jPanel212.add(jCheckBox31111215, null);
        jPanel212.add(jCheckBox31111214, null);
        jPanel212.add(jCheckBox31111213, null);
        jPanel212.add(jCheckBox31111212, null);
        jPanel212.add(jCheckBox31111211, null);
        jPanel212.add(jCheckBox31111210, null);
        jPanel212.add(jCheckBox3111129, null);
        jPanel212.add(jCheckBox3111128, null);
        jPanel212.add(jCheckBox3111127, null);
        jPanel212.add(jCheckBox3111126, null);
        jPanel212.add(jCheckBox3111125, null);
        jPanel212.add(jCheckBox3111124, null);
        jPanel212.add(jCheckBox3111123, null);
        jPanel212.add(jCheckBox3111122, null);
        jPanel212.add(jCheckBox3111121, null);
        jPanel212.add(jCheckBox3111120, null);
        jPanel19.add(jLabel16, null);
        jPanel19.add(jTextField5, null);
        jPanel19.add(jCheckBox31111113, null);
        jPanel19.add(jCheckBox31111112, null);
        jPanel19.add(jCheckBox31111111, null);
        jPanel19.add(jCheckBox31111110, null);
        jPanel19.add(jCheckBox3111119, null);
        jPanel19.add(jCheckBox3111118, null);
        jPanel19.add(jCheckBox3111117, null);
        jPanel19.add(jCheckBox3111116, null);
        jPanel19.add(jCheckBox3111115, null);
        jPanel19.add(jCheckBox3111114, null);
        jPanel213.add(jTextField6, null);
        jPanel213.add(jLabel17, null);
        jPanel213.add(jCheckBox311119, null);
        jPanel29.add(jLabel18, new XYConstraints(1, 79, 45, 15));
        jPanel29.add(jTextField7, new XYConstraints(51, 79, 100, 20));
        jPanel29.add(jCheckBox3111113, new XYConstraints(1, 59, 100, 15));
        jPanel29.add(jCheckBox3111112, new XYConstraints(1, 39, 65, 15));
        jPanel29.add(jCheckBox3111111, new XYConstraints(1, 19, 130, 15));
        jPanel29.add(jCheckBox3111110, new XYConstraints(1, -1, 100, 15));
        jPanel27.add(jCheckBox31117, null);
        jPanel27.add(jCheckBox31116, null);
        jPanel27.add(jCheckBox31115, null);
        jPanel27.add(jCheckBox31114, null);
        jPanel27.add(jCheckBox31113, null);
        jPanel27.add(jCheckBox31112, null);
        jPanel27.add(jCheckBox31111, null);
        jPanel27.add(jCheckBox31110, null);
        jPanel27.add(jCheckBox3119, null);
        jPanel27.add(jCheckBox3118, null);
        jPanel27.add(jCheckBox3117, null);
        jPanel27.add(jCheckBox3115, null);
        jPanel28.add(jCheckBox3110, null);
        jPanel28.add(jCheckBox319, null);
        jPanel13.add(jPanel28, null);
        jPanel13.add(jPanel27, null);
        jPanel13.add(jPanel26, null);
        jPanel13.add(jPanel24, null);
        jPanel118.add(jCheckBox318, null);
        jPanel13.add(jPanel118, null);
        jPanel116.add(jCheckBox311, null);
        jPanel13.add(jPanel116, null);
        jPanel115.add(jCheckBox3114, null);
        jPanel13.add(jPanel115, null);
        jPanel13.add(jPanel113, null);
        jPanel13.add(jPanel112, null);
        jPanel13.add(jPanel111, null);
        jPanel18.add(jCheckBox30, null);
        jPanel13.add(jPanel18, null);
        jPanel13.add(jPanel110, null);
        jPanel114.add(jCheckBox3113, null);
        jPanel13.add(jPanel114, null);
        jPanel26.add(jTextField3, null);
        jPanel26.add(jLabel14, null);
        jPanel26.add(jCheckBox3213, null);
        jPanel26.add(jCheckBox3212, null);
        jPanel24.add(jLabel13, null);
        jPanel24.add(jCheckBox329, null);
        jPanel112.add(jCheckBox320, null);
        jPanel112.add(jLabel10, null);
        jPanel110.add(jLabel12, null);
        jPanel110.add(jCheckBox328, null);
        jPanel110.add(jLabel11, null);
        jPanel113.add(jCheckBox39, null);
        jPanel113.add(jLabel9, null);
        jPanel111.add(jCheckBox34, null);
        jPanel111.add(jLabel8, null);
        jPanel3.add(jCheckBox126, null);
        jPanel3.add(jCheckBox112, null);
        jPanel3.add(jCheckBox111, null);
        jPanel3.add(jCheckBox110, null);
        jPanel3.add(jCheckBox19, null);
        jPanel3.add(jCheckBox18, null);
        jPanel3.add(jCheckBox16, null);
        jPanel3.add(jCheckBox15, null);
        jPanel3.add(jCheckBox13, null);
        jPanel3.add(jCheckBox12, null);
        jPanel4.add(jCheckBox117, null);
        jPanel4.add(jCheckBox116, null);
        jPanel4.add(jCheckBox115, null);
        jPanel4.add(jCheckBox114, null);
        jPanel4.add(jCheckBox113, null);
        jPanel5.add(jButton3, null);
        jPanel5.add(jButton2, null);
        jPanel5.add(jTextField2, null);
        jPanel5.add(jLabel6, null);
        jPanel5.add(jTextField1, null);
        jPanel5.add(jLabel5, null);
        jPanel7.add(jCheckBox210, null);
        jPanel7.add(jCheckBox29, null);
        jPanel7.add(jCheckBox28, null);
        jPanel7.add(jCheckBox27, null);
        jPanel8.add(jCheckBox120, null);
        jPanel8.add(jCheckBox212, null);
        jPanel8.add(jCheckBox119, null);
        jPanel8.add(jCheckBox118, null);
        jPanel8.add(jCheckBox211, null);
        jPanel9.add(jLabel7, null);
        jPanel9.add(jCheckBox11115, null);
        jPanel9.add(jCheckBox11114, null);
        jPanel9.add(jCheckBox11113, null);
        jPanel9.add(jCheckBox11112, null);
        jPanel9.add(jCheckBox11111, null);
        jPanel9.add(jCheckBox11110, null);
        jPanel9.add(jCheckBox1119, null);
        jPanel9.add(jCheckBox1118, null);
        jPanel9.add(jCheckBox1117, null);
        jPanel9.add(jCheckBox1116, null);
        jPanel9.add(jCheckBox1115, null);
        jPanel9.add(jCheckBox1114, null);
        jPanel9.add(jCheckBox1113, null);
        jPanel9.add(jCheckBox1112, null);
        jPanel9.add(jCheckBox1111, null);
        jPanel9.add(jCheckBox1110, null);
        jPanel12.add(jCheckBox11123, null);
        jPanel12.add(jCheckBox11122, null);
        jPanel12.add(jCheckBox11121, null);
        jPanel12.add(jCheckBox11120, null);
        pnlFichaProcedimientos.add(jPanel12, new XYConstraints(660, 400, 195, 130));
        pnlFichaProcedimientos.add(jPanel11, new XYConstraints(870, 420, 200, 105));
        pnlFichaProcedimientos.add(jPanel10, new XYConstraints(280, 365, 335, 155));
        pnlFichaProcedimientos.add(jPanel9, new XYConstraints(695, 135, 370, 245));
        pnlFichaProcedimientos.add(jPanel8, new XYConstraints(460, 180, 215, 150));
        pnlFichaProcedimientos.add(jPanel7, new XYConstraints(10, 200, 220, 130));
        pnlFichaProcedimientos.add(jPanel6, new XYConstraints(10, 345, 220, 205));
        pnlFichaProcedimientos.add(jPanel5, new XYConstraints(695, 15, 370, 95));
        pnlFichaProcedimientos.add(jPanel4, new XYConstraints(250, 135, 180, 160));
        pnlFichaProcedimientos.add(jPanel3, new XYConstraints(460, 10, 215, 155));
        jPanel2.add(jCheckBox8, new XYConstraints(10, 9, 155, 20));
        pnlFichaProcedimientos.add(jPanel2, new XYConstraints(250, 40, 180, 60));
        pnlFichaProcedimientos.add(jPanel1, new XYConstraints(10, 10, 230, 180));
        jPanel11.add(jCheckBox123, null);
        jPanel11.add(jCheckBox122, null);
        jPanel11.add(jCheckBox121, null);
        jPanel10.add(jCheckBox111110, null);
        jPanel10.add(jCheckBox11119, null);
        jPanel10.add(jCheckBox11118, null);
        jPanel10.add(jCheckBox11117, null);
        jPanel10.add(jCheckBox11116, null);
        jPanel6.add(jCheckBox26, null);
        jPanel6.add(jCheckBox25, null);
        jPanel6.add(jCheckBox24, null);
        jPanel6.add(jCheckBox23, null);
        jPanel6.add(jCheckBox22, null);
        jPanel6.add(jCheckBox21, null);
        jPanel6.add(jCheckBox20, null);
        jPanel1.add(jCheckBox6, new XYConstraints(10, 134, 205, 20));
        jPanel1.add(jCheckBox5, new XYConstraints(10, 109, 205, 20));
        jPanel1.add(jCheckBox4, new XYConstraints(10, 84, 205, 20));
        jPanel1.add(jCheckBox3, new XYConstraints(10, 59, 205, 20));
        jPanel1.add(jCheckBox2, new XYConstraints(10, 34, 205, 20));
        jPanel1.add(jCheckBox1, new XYConstraints(10, 9, 205, 20));
        tabpContenedor.setFont(new Font("SansSerif", 0, 11));


        tabpContenedor.setBackground(SystemColor.window);
        tabpContenedor.setBounds(new Rectangle(15, 15, 1090, 575));
        pnlBotonesFuncion.setBounds(new Rectangle(140, 600, 725, 30));
        pnlBotonesFuncion.setBackground(SystemColor.window);

        /*pnlContenedor.add(lblF4, null);
        pnlContenedor.add(lblEsc, null);
        pnlContenedor.add(lblF2, null);
        pnlContenedor.add(lblF5, null);
        pnlContenedor.add(lblF11, null);*/
        lblSaturacion.setText("Saturaci\u00f3n Oxigeno");
        lblSaturacion.setBounds(new Rectangle(395, 70, 115, 15));
        lblSaturacion.setForeground(new Color(0, 99, 148));
        lblSaturacion.setFont(new Font("SansSerif", 1, 11));
        txtSaturacionOxigeno.setBounds(new Rectangle(515, 65, 75, 20));
        jLabel1.setText("%");
        jLabel1.setBounds(new Rectangle(595, 70, 35, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 99, 148));
        jLabel2.setText("Curso");
        jLabel2.setBounds(new Rectangle(35, 75, 65, 20));
        jLabel2.setForeground(new Color(0, 99, 148));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        txtCurso.setBounds(new Rectangle(115, 75, 565, 20));
        jLabel3.setText("( * ) Los datos indicados deben ingresarse de forma obligatoria");
        jLabel3.setBounds(new Rectangle(15, 370, 675, 15));
        jLabel3.setFont(new Font("SansSerif", 1, 12));
        jLabel3.setForeground(new Color(231, 0, 0));
        jLabel4.setText("( * ) Los datos indicados deben ingresarse de forma obligatoria");
        jLabel4.setBounds(new Rectangle(15, 365, 675, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 12));
        jLabel4.setForeground(new Color(231, 0, 0));
        lblCodigoTratamiento.setText("jLabel5");
        lblCodigoTratamiento.setBounds(new Rectangle(105, 10, 85, 15));
        pnlFichaProcedimientos.setLayout(xYLayout1);
        jButton1.setText("jButton1");
        pnlBotonesFuncion1.setBounds(new Rectangle(135, 605, 725, 30));
        pnlBotonesFuncion1.setBackground(SystemColor.window);
        jPanel1.setBorder(BorderFactory.createTitledBorder("PROCEDIMIENTOS CARDIOLOGICOS"));
        jPanel1.setBackground(SystemColor.window);
        jPanel1.setLayout(xYLayout2);
        jPanel1.setFont(new Font("Dialog", 1, 14));
        jCheckBox1.setText("Electrocardiograma (ECG)");
        jCheckBox1.setBackground(SystemColor.window);
        jCheckBox2.setText("Riesgo Quir\u00fargico con ECG");
        jCheckBox2.setBackground(SystemColor.window);
        jCheckBox3.setText("Ecocardiograma Doppler Bidimensional");
        jCheckBox3.setBackground(SystemColor.window);
        jCheckBox4.setText("Holter de 24 horas");
        jCheckBox4.setBackground(SystemColor.window);
        jCheckBox5.setText("Monitoreo ambulatorio de P/A de 24 horas (Mapa)");
        jCheckBox5.setBackground(SystemColor.window);
        jCheckBox6.setText("Prueba de Esfuerzo");
        jCheckBox6.setBackground(SystemColor.window);
        jPanel2.setBorder(BorderFactory.createTitledBorder("PAPANICOLAOU"));
        jPanel2.setLayout(xYLayout3);
        jPanel2.setBackground(SystemColor.window);
        jCheckBox7.setText("jCheckBox7");
        jCheckBox8.setText("Secreci\u00f3n cervico - vaginal");
        jCheckBox8.setBackground(SystemColor.window);
        jPanel3.setBorder(BorderFactory.createTitledBorder("PERFIL FISIOLÓGICO"));
        jPanel3.setBackground(SystemColor.window);
        jPanel3.setLayout(null);
        jCheckBox12.setText("Hemograma");
        jCheckBox12.setBounds(new Rectangle(15, 50, 90, 20));
        jCheckBox12.setBackground(SystemColor.window);
        jCheckBox13.setText("\u00darea");
        jCheckBox13.setBounds(new Rectangle(15, 100, 90, 20));
        jCheckBox13.setBackground(SystemColor.window);
        jCheckBox14.setText("Glucosa");
        jCheckBox15.setText("Glucosa");
        jCheckBox15.setBounds(new Rectangle(15, 75, 90, 20));
        jCheckBox15.setBackground(SystemColor.window);
        jCheckBox16.setText("Creatinina");
        jCheckBox16.setBounds(new Rectangle(15, 125, 90, 20));
        jCheckBox16.setBackground(SystemColor.window);
        jCheckBox17.setText("Glucosa");
        jCheckBox18.setText("\u00c1cido \u00danico");
        jCheckBox18.setBounds(new Rectangle(115, 25, 90, 20));
        jCheckBox18.setBackground(SystemColor.window);
        jCheckBox19.setText("Colesterol");
        jCheckBox19.setBounds(new Rectangle(115, 50, 90, 20));
        jCheckBox19.setBackground(SystemColor.window);
        jCheckBox110.setText("Prote\u00ednas");
        jCheckBox110.setBounds(new Rectangle(115, 75, 90, 20));
        jCheckBox110.setBackground(SystemColor.window);
        jCheckBox111.setText("TGO. TGP.");
        jCheckBox111.setBounds(new Rectangle(115, 100, 90, 20));
        jCheckBox111.setBackground(SystemColor.window);
        jCheckBox112.setText("Ex. Orina");
        jCheckBox112.setBounds(new Rectangle(115, 125, 90, 20));
        jCheckBox112.setBackground(SystemColor.window);
        jPanel4.setBorder(BorderFactory.createTitledBorder("CHEQUEO GINECOLOGICO"));
        jPanel4.setLayout(null);
        jPanel4.setBackground(SystemColor.window);
        jPanel4.setFont(new Font("Dialog", 1, 12));
        jCheckBox113.setText("Ecograf\u00eda P\u00e9lvica");
        jCheckBox113.setBounds(new Rectangle(15, 25, 155, 20));
        jCheckBox113.setBackground(SystemColor.window);
        jCheckBox114.setText("Perfil Hormonal");
        jCheckBox114.setBounds(new Rectangle(15, 50, 155, 20));
        jCheckBox114.setBackground(SystemColor.window);
        jCheckBox115.setText("Perfil Lip\u00eddico");
        jCheckBox115.setBounds(new Rectangle(15, 75, 155, 20));
        jCheckBox115.setBackground(SystemColor.window);
        jCheckBox116.setText("Ex. de orina completo");
        jCheckBox116.setBounds(new Rectangle(15, 100, 155, 20));
        jCheckBox116.setBackground(SystemColor.window);
        jCheckBox117.setText("Papanicolaou");
        jCheckBox117.setBounds(new Rectangle(15, 125, 155, 20));
        jCheckBox117.setBackground(SystemColor.window);
        jPanel5.setBorder(BorderFactory.createTitledBorder("EXAMENES DE PATOLOGIA QUIRURGICA"));
        jPanel5.setBackground(SystemColor.window);
        jPanel5.setLayout(null);
        jLabel5.setText("Biopsia:");
        jLabel5.setBounds(new Rectangle(10, 20, 45, 20));
        jTextField1.setBounds(new Rectangle(50, 20, 235, 20));
        jLabel6.setText("Pieza Operatoria:");
        jLabel6.setBounds(new Rectangle(10, 60, 90, 20));
        jTextField2.setBounds(new Rectangle(100, 55, 185, 20));
        jPanel6.setLayout(null);
        jPanel6.setBorder(BorderFactory.createTitledBorder("CHEQUEO COMPLETO"));
        jPanel6.setBackground(SystemColor.window);
        jPanel7.setBackground(SystemColor.window);
        jPanel7.setBorder(BorderFactory.createTitledBorder("PERFIL PROSTÁTICO"));
        jPanel7.setLayout(null);
        jPanel8.setBackground(SystemColor.window);
        jPanel8.setBorder(BorderFactory.createTitledBorder("PERFIL RENAL"));
        jPanel8.setLayout(null);
        jCheckBox20.setText("Rayos X T\u00f3rax");
        jCheckBox20.setBounds(new Rectangle(15, 25, 195, 20));
        jCheckBox20.setBackground(SystemColor.window);
        jCheckBox21.setText("Hemograma completo");
        jCheckBox21.setBounds(new Rectangle(15, 50, 195, 20));
        jCheckBox21.setBackground(SystemColor.window);
        jCheckBox22.setText("Glucosa");
        jCheckBox22.setBounds(new Rectangle(15, 75, 195, 20));
        jCheckBox22.setBackground(SystemColor.window);
        jCheckBox23.setText("Ex. completo de orina");
        jCheckBox23.setBounds(new Rectangle(15, 100, 195, 20));
        jCheckBox23.setBackground(SystemColor.window);
        jCheckBox24.setText("\u00darea");
        jCheckBox24.setBounds(new Rectangle(15, 125, 195, 20));
        jCheckBox24.setBackground(SystemColor.window);
        jCheckBox25.setText("Creatinina");
        jCheckBox25.setBounds(new Rectangle(15, 150, 195, 20));
        jCheckBox25.setActionCommand("Creatinina");
        jCheckBox25.setBackground(SystemColor.window);
        jCheckBox26.setText("Perfil Lip\u00eddico");
        jCheckBox26.setBounds(new Rectangle(15, 175, 195, 20));
        jCheckBox26.setActionCommand("Riesgo Coronario");
        jCheckBox26.setBackground(SystemColor.window);
        jCheckBox27.setText("PSA (Ag. Prost\u00e1tico)");
        jCheckBox27.setBounds(new Rectangle(15, 25, 195, 20));
        jCheckBox27.setBackground(SystemColor.window);
        jCheckBox28.setText("Ecograf\u00eda (V\u00e9sico - Prost\u00e1tica)");
        jCheckBox28.setBounds(new Rectangle(15, 50, 195, 20));
        jCheckBox28.setBackground(SystemColor.window);
        jCheckBox29.setText("Ex. Orina");
        jCheckBox29.setBounds(new Rectangle(15, 75, 195, 20));
        jCheckBox29.setActionCommand("Ex. Orina");
        jCheckBox29.setBackground(SystemColor.window);
        jCheckBox210.setText("Hemograma");
        jCheckBox210.setBounds(new Rectangle(15, 100, 195, 20));
        jCheckBox210.setBackground(SystemColor.window);
        jCheckBox211.setText("Orina completa");
        jCheckBox211.setBounds(new Rectangle(10, 45, 195, 20));
        jCheckBox211.setBackground(SystemColor.window);
        jCheckBox118.setText("\u00darea");
        jCheckBox118.setBounds(new Rectangle(10, 20, 195, 20));
        jCheckBox118.setBackground(SystemColor.window);
        jCheckBox119.setText("Micrcoalbunaria");
        jCheckBox119.setBounds(new Rectangle(10, 70, 195, 20));
        jCheckBox119.setBackground(SystemColor.window);
        jCheckBox212.setText("Depuraci\u00f3n creatinina (orina 24hrs.)");
        jCheckBox212.setBounds(new Rectangle(10, 95, 195, 20));
        jCheckBox212.setBackground(SystemColor.window);
        jCheckBox120.setText("Hemograma");
        jCheckBox120.setBounds(new Rectangle(10, 120, 195, 20));
        jCheckBox120.setBackground(SystemColor.window);
        jPanel9.setBackground(SystemColor.window);
        jPanel9.setBorder(BorderFactory.createTitledBorder("VACUNAS - Centro de Vacunación Internacional"));
        jPanel9.setLayout(null);
        jCheckBox1110.setText("Fiebre amarilla");
        jCheckBox1110.setBounds(new Rectangle(15, 40, 170, 20));
        jCheckBox1110.setBackground(SystemColor.window);
        jCheckBox1111.setText("Gripe");
        jCheckBox1111.setBounds(new Rectangle(15, 65, 170, 20));
        jCheckBox1111.setBackground(SystemColor.window);
        jCheckBox1112.setText("Vacuna Hepatitis A+B (Twinrix)");
        jCheckBox1112.setBounds(new Rectangle(15, 90, 170, 20));
        jCheckBox1112.setBackground(SystemColor.window);
        jCheckBox1113.setText("Vacuna Hepatitis A");
        jCheckBox1113.setBounds(new Rectangle(15, 115, 170, 20));
        jCheckBox1113.setBackground(SystemColor.window);
        jCheckBox1114.setText("Vacuna Hepatitis B");
        jCheckBox1114.setBounds(new Rectangle(15, 140, 170, 20));
        jCheckBox1114.setBackground(SystemColor.window);
        jCheckBox1115.setText("Difteria, Pertusis, T\u00e9tano");
        jCheckBox1115.setBounds(new Rectangle(15, 165, 170, 20));
        jCheckBox1115.setBackground(SystemColor.window);
        jCheckBox1116.setText("Sarampi\u00f3n, Paperas y Rubeola");
        jCheckBox1116.setBounds(new Rectangle(15, 190, 170, 20));
        jCheckBox1116.setBackground(SystemColor.window);
        jCheckBox1117.setText("Meningitis");
        jCheckBox1117.setBounds(new Rectangle(15, 215, 170, 20));
        jCheckBox1117.setBackground(SystemColor.window);
        jCheckBox1118.setText("Rabia");
        jCheckBox1118.setBounds(new Rectangle(190, 40, 170, 20));
        jCheckBox1118.setBackground(SystemColor.window);
        jCheckBox1119.setText("C\u00f3lera");
        jCheckBox1119.setBounds(new Rectangle(190, 65, 170, 20));
        jCheckBox1119.setBackground(SystemColor.window);
        jCheckBox11110.setText("Polio");
        jCheckBox11110.setBounds(new Rectangle(190, 90, 170, 20));
        jCheckBox11110.setBackground(SystemColor.window);
        jCheckBox11111.setText("Varicela");
        jCheckBox11111.setBounds(new Rectangle(190, 115, 170, 20));
        jCheckBox11111.setBackground(SystemColor.window);
        jCheckBox11112.setText("Tifoidea");
        jCheckBox11112.setBounds(new Rectangle(190, 140, 170, 20));
        jCheckBox11112.setBackground(SystemColor.window);
        jCheckBox11113.setText("RPD");
        jCheckBox11113.setBounds(new Rectangle(190, 165, 170, 20));
        jCheckBox11113.setBackground(SystemColor.window);
        jCheckBox11114.setText("Rotavirus");
        jCheckBox11114.setBounds(new Rectangle(190, 190, 170, 20));
        jCheckBox11114.setBackground(SystemColor.window);
        jCheckBox11115.setText("Neumonia");
        jCheckBox11115.setBounds(new Rectangle(190, 215, 170, 20));
        jCheckBox11115.setBackground(SystemColor.window);
        jPanel10.setLayout(null);
        jPanel10.setBackground(SystemColor.window);
        jPanel10.setBorder(BorderFactory.createTitledBorder("PROCEDIMIENTOS GASTROENTEROLÓGICOS"));
        jPanel11.setLayout(null);
        jPanel11.setBorder(BorderFactory.createTitledBorder("OTROS"));
        jPanel11.setBackground(SystemColor.window);
        jPanel12.setBorder(BorderFactory.createTitledBorder("PERFIL TORCH"));
        jPanel12.setLayout(null);
        jPanel12.setBackground(SystemColor.window);
        jCheckBox121.setText("Audiometr\u00eda");
        jCheckBox121.setBounds(new Rectangle(15, 25, 175, 20));
        jCheckBox121.setBackground(SystemColor.window);
        jCheckBox122.setText("Espirometr\u00eda");
        jCheckBox122.setBounds(new Rectangle(15, 50, 175, 20));
        jCheckBox122.setBackground(SystemColor.window);
        jCheckBox123.setText("Medicina f\u00edsica y rehabilitaci\u00f3n");
        jCheckBox123.setBounds(new Rectangle(15, 75, 175, 20));
        jCheckBox123.setBackground(SystemColor.window);
        jCheckBox11116.setText("Video endoscop\u00eda");
        jCheckBox11116.setBounds(new Rectangle(15, 25, 310, 20));
        jCheckBox11116.setBackground(SystemColor.window);
        jCheckBox11117.setText("Video Colonoscop\u00eda corta (izq.)");
        jCheckBox11117.setBounds(new Rectangle(15, 50, 310, 20));
        jCheckBox11117.setBackground(SystemColor.window);
        jCheckBox11118.setText("Video Colonoscop\u00eda alta (der.)");
        jCheckBox11118.setBounds(new Rectangle(15, 75, 310, 20));
        jCheckBox11118.setActionCommand("Endoscop\u00eda de Fibra");
        jCheckBox11118.setBackground(SystemColor.window);
        jCheckBox11119.setText("Video Proctoscopia");
        jCheckBox11119.setBounds(new Rectangle(15, 100, 310, 20));
        jCheckBox11119.setBackground(SystemColor.window);
        jCheckBox111110.setText("Anoscop\u00eda");
        jCheckBox111110.setBounds(new Rectangle(15, 125, 310, 20));
        jCheckBox111110.setBackground(SystemColor.window);
        jCheckBox11120.setText("Citomegalo Virus (IgG, Igm)");
        jCheckBox11120.setBounds(new Rectangle(15, 25, 170, 20));
        jCheckBox11120.setBackground(SystemColor.window);
        jCheckBox11121.setText("Herpes II (IgG, Igm)");
        jCheckBox11121.setBounds(new Rectangle(15, 50, 170, 20));
        jCheckBox11121.setBackground(SystemColor.window);
        jCheckBox11122.setText("Rubeola (IgG, Igm)");
        jCheckBox11122.setBounds(new Rectangle(15, 75, 170, 20));
        jCheckBox11122.setBackground(SystemColor.window);
        jCheckBox11123.setText("Toxoplasma (IgG, Igm)");
        jCheckBox11123.setBounds(new Rectangle(15, 100, 170, 20));
        jCheckBox11123.setBackground(SystemColor.window);
        jPanel13.setBounds(new Rectangle(5, 0, 370, 505));
        jPanel13.setBackground(SystemColor.window);
        jPanel13.setBorder(BorderFactory.createTitledBorder("ECOGRAFÍA"));
        jPanel13.setLayout(null);
        jPanel14.setBounds(new Rectangle(375, 0, 700, 485));
        jPanel14.setBackground(SystemColor.window);
        jPanel14.setBorder(BorderFactory.createTitledBorder("RADIOGRAFÍA"));
        jPanel14.setLayout(null);
        jPanel19.setBounds(new Rectangle(425, 10, 275, 245));
        jPanel19.setBackground(SystemColor.window);
        jPanel19.setBorder(BorderFactory.createTitledBorder("COLUMNA Y PÉLVIS"));
        jPanel19.setLayout(null);
        jPanel20.setBounds(new Rectangle(645, 495, 355, 55));
        jPanel20.setBackground(SystemColor.window);
        jPanel20.setBorder(BorderFactory.createTitledBorder("DENSITOMETRÍA ÓSEA"));
        jPanel20.setLayout(null);
        jPanel21.setBounds(new Rectangle(135, 505, 215, 40));
        jPanel21.setBackground(SystemColor.window);
        jPanel21.setBorder(BorderFactory.createTitledBorder("VARIOS"));
        jPanel21.setLayout(null);
        jPanel23.setBounds(new Rectangle(375, 490, 230, 55));
        jPanel23.setBackground(SystemColor.window);
        jPanel23.setBorder(BorderFactory.createTitledBorder("PROCEDIMIENTOS ESPECIALES"));
        jPanel23.setLayout(null);
        jPanel18.setBounds(new Rectangle(170, 20, 195, 35));
        jPanel18.setBackground(SystemColor.window);
        jPanel18.setBorder(BorderFactory.createTitledBorder("OBSTÉTRICA"));
        jPanel18.setLayout(null);
        jPanel110.setBounds(new Rectangle(5, 155, 150, 60));
        jPanel110.setBackground(SystemColor.window);
        jPanel110.setBorder(BorderFactory.createTitledBorder("ABDOMEN COMPLETO"));
        jPanel110.setLayout(null);
        jPanel111.setBounds(new Rectangle(10, 60, 145, 45));
        jPanel111.setBackground(SystemColor.window);
        jPanel111.setBorder(BorderFactory.createTitledBorder("PÉLVICA (GINECOLÓGICA)"));
        jPanel111.setLayout(null);
        jPanel112.setBounds(new Rectangle(5, 105, 140, 45));
        jPanel112.setBackground(SystemColor.window);
        jPanel112.setBorder(BorderFactory.createTitledBorder("ABDOMEN SUPERIOR"));
        jPanel112.setLayout(null);
        jPanel113.setBounds(new Rectangle(170, 55, 185, 50));
        jPanel113.setBackground(SystemColor.window);
        jPanel113.setBorder(BorderFactory.createTitledBorder("PÉLVICA"));
        jPanel113.setLayout(null);
        jPanel114.setBounds(new Rectangle(5, 285, 235, 35));
        jPanel114.setBackground(SystemColor.window);
        jPanel114.setBorder(BorderFactory.createTitledBorder("CARDIOLOGÍA"));
        jPanel114.setLayout(null);
        jPanel115.setBounds(new Rectangle(245, 285, 110, 35));
        jPanel115.setBackground(SystemColor.window);
        jPanel115.setBorder(BorderFactory.createTitledBorder("RENAL"));
        jPanel115.setLayout(null);
        jPanel116.setBounds(new Rectangle(170, 110, 200, 35));
        jPanel116.setBackground(SystemColor.window);
        jPanel116.setBorder(BorderFactory.createTitledBorder("TRANSVAGINAL"));
        jPanel116.setLayout(null);
        jPanel117.setBounds(new Rectangle(185, 320, 95, 95));
        jPanel118.setBounds(new Rectangle(10, 20, 140, 35));
        jPanel118.setBackground(SystemColor.window);
        jPanel118.setBorder(BorderFactory.createTitledBorder("MAMAS"));
        jPanel118.setLayout(null);
        jLabel7.setText("(Ni\u00f1os y Adultos)");
        jLabel7.setBounds(new Rectangle(85, 15, 100, 15));
        jLabel7.setForeground(new Color(0, 66, 198));
        jCheckBox30.setText("1er. Trimestre (hasta 12 semenas)");
        jCheckBox30.setBounds(new Rectangle(5, 15, 185, 15));
        jCheckBox30.setBackground(SystemColor.window);
        jLabel8.setText("(Venir con vejiga llena)");
        jLabel8.setBounds(new Rectangle(15, 10, 110, 15));
        jLabel8.setForeground(new Color(0, 66, 198));
        jCheckBox34.setText("\u00datero y Ovarios");
        jCheckBox34.setBounds(new Rectangle(5, 25, 135, 15));
        jCheckBox34.setActionCommand("\u00datero y Ovarios");
        jCheckBox34.setBackground(SystemColor.window);
        jLabel9.setText("(Varones)");
        jLabel9.setBounds(new Rectangle(5, 10, 55, 15));
        jLabel9.setForeground(new Color(0, 66, 198));
        jCheckBox39.setText("Pr\u00f3stata (Pre y Post Miccional)");
        jCheckBox39.setBounds(new Rectangle(5, 30, 170, 15));
        jCheckBox39.setActionCommand("\u00datero y Ovarios");
        jCheckBox39.setBackground(SystemColor.window);
        jCheckBox311.setText("\u00datero y Ovarios, Quistes Ov\u00e1ricos");
        jCheckBox311.setBounds(new Rectangle(5, 15, 190, 15));
        jCheckBox311.setActionCommand("\u00datero y Ovarios");
        jCheckBox311.setBackground(SystemColor.window);
        jCheckBox318.setText("Mama derecha");
        jCheckBox318.setBounds(new Rectangle(5, 15, 125, 15));
        jCheckBox318.setActionCommand("\u00datero y Ovarios");
        jCheckBox318.setBackground(SystemColor.window);
        jCheckBox3113.setText("Ecocardiograma doppler bidimensional");
        jCheckBox3113.setBounds(new Rectangle(5, 15, 225, 15));
        jCheckBox3113.setActionCommand("\u00datero y Ovarios");
        jCheckBox3113.setBackground(SystemColor.window);
        jLabel10.setText("(Venir en ayunas)");
        jLabel10.setBounds(new Rectangle(15, 10, 95, 15));
        jLabel10.setForeground(new Color(0, 66, 198));
        jCheckBox320.setText("Bazo");
        jCheckBox320.setBounds(new Rectangle(5, 25, 125, 15));
        jCheckBox320.setActionCommand("\u00datero y Ovarios");
        jCheckBox320.setBackground(SystemColor.window);
        jLabel11.setText("(Venir en ayunas)");
        jLabel11.setBounds(new Rectangle(15, 10, 95, 15));
        jLabel11.setForeground(new Color(0, 66, 198));
        jCheckBox328.setText("Abd. Superior + Ri\u00f1ones");
        jCheckBox328.setBounds(new Rectangle(5, 25, 140, 15));
        jCheckBox328.setActionCommand("\u00datero y Ovarios");
        jCheckBox328.setBackground(SystemColor.window);
        jLabel12.setText("(No incluye P\u00e9lvica)");
        jLabel12.setBounds(new Rectangle(25, 40, 95, 15));
        jLabel12.setBackground(SystemColor.window);
        jCheckBox3114.setText("Bilateral");
        jCheckBox3114.setBounds(new Rectangle(5, 15, 100, 15));
        jCheckBox3114.setActionCommand("\u00datero y Ovarios");
        jCheckBox3114.setBackground(SystemColor.window);
        jPanel24.setBounds(new Rectangle(170, 230, 185, 55));
        jPanel24.setBackground(SystemColor.window);
        jPanel24.setBorder(BorderFactory.createTitledBorder("APARATO URINARIO"));
        jPanel24.setLayout(null);
        jPanel26.setBounds(new Rectangle(170, 145, 200, 80));
        jPanel26.setBackground(SystemColor.window);
        jPanel26.setBorder(BorderFactory.createTitledBorder("TESTICULAR"));
        jPanel26.setLayout(null);
        jPanel27.setBounds(new Rectangle(5, 325, 365, 180));
        jPanel27.setBackground(SystemColor.window);
        jPanel27.setBorder(BorderFactory.createTitledBorder("OTRAS ECOGRAFÍAS"));
        jPanel27.setLayout(null);
        jCheckBox329.setText("Ri\u00f1ones");
        jCheckBox329.setBounds(new Rectangle(5, 30, 170, 15));
        jCheckBox329.setActionCommand("\u00datero y Ovarios");
        jCheckBox329.setBackground(SystemColor.window);
        jLabel13.setText("(Venir con vejiga llena)");
        jLabel13.setBounds(new Rectangle(5, 10, 110, 15));
        jLabel13.setForeground(new Color(0, 66, 198));
        jCheckBox3212.setText("Testicular bilateral");
        jCheckBox3212.setBounds(new Rectangle(5, 15, 190, 15));
        jCheckBox3212.setActionCommand("\u00datero y Ovarios");
        jCheckBox3212.setBackground(SystemColor.window);
        jCheckBox3213.setText("Test\u00edculo Izquierdo");
        jCheckBox3213.setBounds(new Rectangle(5, 35, 190, 15));
        jCheckBox3213.setActionCommand("\u00datero y Ovarios");
        jCheckBox3213.setBackground(SystemColor.window);
        jLabel14.setText("Otros");
        jLabel14.setBounds(new Rectangle(10, 55, 45, 15));
        jLabel14.setBackground(SystemColor.window);
        jTextField3.setBounds(new Rectangle(40, 55, 155, 20));
        jCheckBox3115.setText("Partes blandas transfontanelar");
        jCheckBox3115.setBounds(new Rectangle(175, 15, 180, 15));
        jCheckBox3115.setActionCommand("\u00datero y Ovarios");
        jCheckBox3115.setBackground(SystemColor.window);
        jCheckBox3116.setText("Cerebral");
        jCheckBox3117.setText("Ecodoppler vascular veoso ambos miembros superiores");
        jCheckBox3117.setBounds(new Rectangle(5, 75, 350, 15));
        jCheckBox3117.setActionCommand("\u00datero y Ovarios");
        jCheckBox3117.setBackground(SystemColor.window);
        jCheckBox3118.setText("Ecodoppler");
        jCheckBox3118.setBounds(new Rectangle(5, 15, 165, 15));
        jCheckBox3118.setActionCommand("\u00datero y Ovarios");
        jCheckBox3118.setBackground(SystemColor.window);
        jCheckBox3119.setText("Ecodoppler vascular venoso un miembro");
        jCheckBox3119.setBounds(new Rectangle(5, 35, 350, 15));
        jCheckBox3119.setActionCommand("\u00datero y Ovarios");
        jCheckBox3119.setBackground(SystemColor.window);
        jCheckBox31110.setText("Ecodoppler vascular arterial de un miembro");
        jCheckBox31110.setBounds(new Rectangle(5, 55, 350, 15));
        jCheckBox31110.setActionCommand("\u00datero y Ovarios");
        jCheckBox31110.setBackground(SystemColor.window);
        jCheckBox31111.setText("Cuatridimensional (4D)");
        jCheckBox31111.setBounds(new Rectangle(175, 115, 180, 15));
        jCheckBox31111.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111.setBackground(SystemColor.window);
        jCheckBox31112.setText("Ecodoppler vascular veoso ambos miembros inferiores");
        jCheckBox31112.setBounds(new Rectangle(5, 95, 350, 15));
        jCheckBox31112.setActionCommand("\u00datero y Ovarios");
        jCheckBox31112.setBackground(SystemColor.window);
        jCheckBox31113.setText("Histerosonograf\u00eda");
        jCheckBox31113.setBounds(new Rectangle(5, 115, 165, 15));
        jCheckBox31113.setActionCommand("\u00datero y Ovarios");
        jCheckBox31113.setBackground(SystemColor.window);
        jCheckBox126.setText("Perfil Fisiol\u00f3gico");
        jCheckBox126.setBounds(new Rectangle(15, 25, 90, 20));
        jCheckBox126.setBackground(SystemColor.window);
        jButton2.setText("BUSCAR");
        jButton2.setBounds(new Rectangle(290, 20, 75, 20));
        jButton3.setText("BUSCAR");
        jButton3.setBounds(new Rectangle(290, 55, 75, 20));
        jPanel28.setBounds(new Rectangle(5, 220, 155, 55));
        jPanel28.setBackground(SystemColor.window);
        jPanel28.setLayout(null);
        jPanel28.setBorder(BorderFactory.createTitledBorder("TRANSRECTAL"));
        jCheckBox319.setText("Pr\u00f3stata");
        jCheckBox319.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox319.setActionCommand("\u00datero y Ovarios");
        jCheckBox319.setBackground(SystemColor.window);
        jCheckBox3110.setText("Residuo Vesical");
        jCheckBox3110.setBounds(new Rectangle(5, 35, 145, 15));
        jCheckBox3110.setActionCommand("\u00datero y Ovarios");
        jCheckBox3110.setBackground(SystemColor.window);
        jCheckBox31114.setText("Partes Blandas cadera de ni\u00f1o");
        jCheckBox31114.setBounds(new Rectangle(5, 135, 165, 15));
        jCheckBox31114.setActionCommand("\u00datero y Ovarios");
        jCheckBox31114.setBackground(SystemColor.window);
        jCheckBox31115.setText("Partes blandas cadera de adulto");
        jCheckBox31115.setBounds(new Rectangle(175, 135, 180, 15));
        jCheckBox31115.setActionCommand("\u00datero y Ovarios");
        jCheckBox31115.setBackground(SystemColor.window);
        jCheckBox31116.setText("Doppler partes blandas");
        jCheckBox31116.setBounds(new Rectangle(5, 155, 165, 15));
        jCheckBox31116.setActionCommand("\u00datero y Ovarios");
        jCheckBox31116.setBackground(SystemColor.window);
        jCheckBox31117.setText("Partes blandas tiroides");
        jCheckBox31117.setBounds(new Rectangle(175, 155, 180, 15));
        jCheckBox31117.setActionCommand("\u00datero y Ovarios");
        jCheckBox31117.setBackground(SystemColor.window);
        jPanel29.setBounds(new Rectangle(10, 335, 165, 120));
        jPanel29.setBackground(SystemColor.window);
        jPanel29.setLayout(null);
        jPanel29.setBorder(BorderFactory.createTitledBorder("TORAX"));
        jPanel211.setBounds(new Rectangle(435, 270, 240, 115));
        jPanel211.setBackground(SystemColor.window);
        jPanel211.setBorder(BorderFactory.createTitledBorder("APARATO DIGESTIVO"));
        jPanel211.setLayout(null);
        jPanel212.setBounds(new Rectangle(180, 0, 240, 480));
        jPanel212.setBackground(SystemColor.window);
        jPanel212.setBorder(BorderFactory.createTitledBorder("EXTREMIDADES"));
        jPanel212.setLayout(null);
        jPanel213.setBounds(new Rectangle(470, 405, 155, 60));
        jPanel213.setBackground(SystemColor.window);
        jPanel213.setBorder(BorderFactory.createTitledBorder("APARATO UROGENITAL"));
        jPanel213.setLayout(null);
        jPanel214.setBounds(new Rectangle(5, 205, 175, 115));
        jPanel214.setBackground(SystemColor.window);
        jPanel214.setBorder(BorderFactory.createTitledBorder("RADIOGRAFÍA"));
        jPanel214.setLayout(null);
        jPanel215.setBounds(new Rectangle(5, 20, 155, 175));
        jPanel215.setBackground(SystemColor.window);
        jPanel215.setBorder(BorderFactory.createTitledBorder("CABEZA Y CUELLO"));
        jPanel215.setLayout(null);
        jCheckBox31118.setText("Cr\u00e1neo F L");
        jCheckBox31118.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox31118.setActionCommand("\u00datero y Ovarios");
        jCheckBox31118.setBackground(SystemColor.window);
        jCheckBox31119.setText("Cr\u00e1neo F L O");
        jCheckBox31119.setBounds(new Rectangle(5, 35, 145, 15));
        jCheckBox31119.setActionCommand("\u00datero y Ovarios");
        jCheckBox31119.setBackground(SystemColor.window);
        jCheckBox311110.setText("Huesos nasales (2)");
        jCheckBox311110.setBounds(new Rectangle(5, 55, 145, 15));
        jCheckBox311110.setActionCommand("\u00datero y Ovarios");
        jCheckBox311110.setBackground(SystemColor.window);
        jCheckBox311111.setText("ATM-Ambos lados");
        jCheckBox311111.setBounds(new Rectangle(5, 75, 145, 15));
        jCheckBox311111.setActionCommand("\u00datero y Ovarios");
        jCheckBox311111.setBackground(SystemColor.window);
        jCheckBox311112.setText("ATM-Unilateral");
        jCheckBox311112.setBounds(new Rectangle(5, 110, 145, 15));
        jCheckBox311112.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112.setBackground(SystemColor.window);
        jCheckBox311113.setText("Senos paranasales (2)");
        jCheckBox311113.setBounds(new Rectangle(5, 130, 145, 15));
        jCheckBox311113.setActionCommand("\u00datero y Ovarios");
        jCheckBox311113.setBackground(SystemColor.window);
        jLabel15.setText("Otros");
        jLabel15.setBounds(new Rectangle(5, 150, 45, 15));
        jLabel15.setBackground(SystemColor.window);
        jTextField4.setBounds(new Rectangle(50, 150, 100, 20));
        jCheckBox311114.setText("Columna Lumbo - Sacra (2)");
        jCheckBox311114.setBounds(new Rectangle(5, 15, 165, 15));
        jCheckBox311114.setActionCommand("\u00datero y Ovarios");
        jCheckBox311114.setBackground(SystemColor.window);
        jCheckBox311115.setText("Columna Sacro - Coxigea (2)");
        jCheckBox311115.setBounds(new Rectangle(5, 35, 165, 15));
        jCheckBox311115.setActionCommand("\u00datero y Ovarios");
        jCheckBox311115.setBackground(SystemColor.window);
        jCheckBox311116.setText("P\u00e9lvis AP (1)");
        jCheckBox311116.setBounds(new Rectangle(5, 55, 165, 15));
        jCheckBox311116.setActionCommand("\u00datero y Ovarios");
        jCheckBox311116.setBackground(SystemColor.window);
        jCheckBox311117.setText("P\u00e9lvis - Bebes (2)");
        jCheckBox311117.setBounds(new Rectangle(5, 75, 165, 15));
        jCheckBox311117.setActionCommand("\u00datero y Ovarios");
        jCheckBox311117.setBackground(SystemColor.window);
        jCheckBox311118.setText("Cadera");
        jCheckBox311118.setBounds(new Rectangle(5, 95, 165, 15));
        jCheckBox311118.setActionCommand("\u00datero y Ovarios");
        jCheckBox311118.setBackground(SystemColor.window);
        jCheckBox311119.setText("Urograf\u00eda excretora (4)");
        jCheckBox311119.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox311119.setActionCommand("\u00datero y Ovarios");
        jCheckBox311119.setBackground(SystemColor.window);
        jLabel17.setText("Otros");
        jLabel17.setBounds(new Rectangle(5, 35, 45, 15));
        jLabel17.setBackground(SystemColor.window);
        jTextField6.setBounds(new Rectangle(50, 35, 100, 20));
        jCheckBox3111110.setText("Rx Torax (1) F");
        jCheckBox3111110.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111110.setBounds(new Rectangle(5, 15, 150, 15));
        jCheckBox3111110.setBackground(SystemColor.window);
        jCheckBox3111111.setText("Rx Torax (2) F y I");
        jCheckBox3111111.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111111.setBounds(new Rectangle(5, 35, 150, 15));
        jCheckBox3111111.setBackground(SystemColor.window);
        jCheckBox3111112.setText("Clav\u00edcula");
        jCheckBox3111112.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111112.setBounds(new Rectangle(5, 55, 150, 15));
        jCheckBox3111112.setBackground(SystemColor.window);
        jCheckBox3111113.setText("Parrilla Costal (2)");
        jCheckBox3111113.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111113.setBounds(new Rectangle(5, 75, 150, 15));
        jCheckBox3111113.setBackground(SystemColor.window);
        jTextField7.setBounds(new Rectangle(55, 95, 100, 20));
        jLabel18.setText("Otros");
        jLabel18.setBackground(SystemColor.window);
        jLabel18.setBounds(new Rectangle(5, 95, 45, 15));
        jCheckBox3111114.setText("Col. Cervical (2)");
        jCheckBox3111114.setBounds(new Rectangle(5, 15, 265, 15));
        jCheckBox3111114.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111114.setBackground(SystemColor.window);
        jCheckBox3111115.setText("Columna Cer. Funcional (2)");
        jCheckBox3111115.setBounds(new Rectangle(5, 35, 265, 15));
        jCheckBox3111115.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111115.setBackground(SystemColor.window);
        jCheckBox3111116.setText("Columna Cer. Oblicuas (2)");
        jCheckBox3111116.setBounds(new Rectangle(5, 55, 265, 15));
        jCheckBox3111116.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111116.setBackground(SystemColor.window);
        jCheckBox3111117.setText("Columna Dorsal (2)");
        jCheckBox3111117.setBounds(new Rectangle(5, 75, 265, 15));
        jCheckBox3111117.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111117.setBackground(SystemColor.window);
        jCheckBox3111118.setText("Columna Dorso Lumbar (2)");
        jCheckBox3111118.setBounds(new Rectangle(5, 95, 265, 15));
        jCheckBox3111118.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111118.setBackground(SystemColor.window);
        jCheckBox3111119.setText("Columna Lumbar (2)");
        jCheckBox3111119.setBounds(new Rectangle(5, 115, 265, 15));
        jCheckBox3111119.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111119.setBackground(SystemColor.window);
        jCheckBox31111110.setText("Columna Lumbo Sacra (2)");
        jCheckBox31111110.setBounds(new Rectangle(5, 135, 265, 15));
        jCheckBox31111110.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111110.setBackground(SystemColor.window);
        jCheckBox31111111.setText("Col. Completa o integral (col. Cervical, dorsal, L-S)");
        jCheckBox31111111.setBounds(new Rectangle(5, 155, 265, 15));
        jCheckBox31111111.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111111.setBackground(SystemColor.window);
        jCheckBox31111112.setText("Pelvis");
        jCheckBox31111112.setBounds(new Rectangle(5, 175, 265, 15));
        jCheckBox31111112.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111112.setBackground(SystemColor.window);
        jCheckBox31111113.setText("Pelvis  bebes");
        jCheckBox31111113.setBounds(new Rectangle(5, 195, 265, 15));
        jCheckBox31111113.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111113.setBackground(SystemColor.window);
        jTextField5.setBounds(new Rectangle(50, 215, 220, 20));
        jLabel16.setText("Otros");
        jLabel16.setBounds(new Rectangle(5, 215, 45, 15));
        jLabel16.setBackground(SystemColor.window);
        jCheckBox3111120.setText("Hombro comparativo");
        jCheckBox3111120.setBounds(new Rectangle(5, 15, 235, 15));
        jCheckBox3111120.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111120.setBackground(SystemColor.window);
        jCheckBox3111121.setText("Hombro");
        jCheckBox3111121.setBounds(new Rectangle(5, 35, 235, 15));
        jCheckBox3111121.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121.setBackground(SystemColor.window);
        jCheckBox3111122.setText("Antebrazo comparativo");
        jCheckBox3111122.setBounds(new Rectangle(5, 55, 235, 15));
        jCheckBox3111122.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111122.setBackground(SystemColor.window);
        jCheckBox3111123.setText("Antebrazo (F-L) Radio-Cubito");
        jCheckBox3111123.setBounds(new Rectangle(5, 75, 235, 15));
        jCheckBox3111123.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111123.setBackground(SystemColor.window);
        jCheckBox3111124.setText("Codo (F-L)");
        jCheckBox3111124.setBounds(new Rectangle(5, 95, 85, 15));
        jCheckBox3111124.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111124.setBackground(SystemColor.window);
        jCheckBox3111125.setText("Codos comparativos");
        jCheckBox3111125.setBounds(new Rectangle(95, 95, 145, 15));
        jCheckBox3111125.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111125.setBackground(SystemColor.window);
        jCheckBox3111126.setText("Mu\u00f1eca");
        jCheckBox3111126.setBounds(new Rectangle(5, 115, 120, 15));
        jCheckBox3111126.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111126.setBackground(SystemColor.window);
        jCheckBox3111127.setText("Mu\u00f1eca (F-L-O)");
        jCheckBox3111127.setBounds(new Rectangle(130, 115, 110, 15));
        jCheckBox3111127.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111127.setBackground(SystemColor.window);
        jCheckBox3111128.setText("Mano unilateral (F-O)");
        jCheckBox3111128.setBounds(new Rectangle(5, 135, 235, 15));
        jCheckBox3111128.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111128.setBackground(SystemColor.window);
        jCheckBox3111129.setText("Manos comparativas");
        jCheckBox3111129.setBounds(new Rectangle(5, 155, 235, 15));
        jCheckBox3111129.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111129.setBackground(SystemColor.window);
        jCheckBox31111210.setText("Muslo");
        jCheckBox31111210.setBounds(new Rectangle(5, 175, 115, 15));
        jCheckBox31111210.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111210.setBackground(SystemColor.window);
        jCheckBox31111211.setText("Muslo comparativos");
        jCheckBox31111211.setBounds(new Rectangle(125, 175, 115, 15));
        jCheckBox31111211.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111211.setBackground(SystemColor.window);
        jCheckBox31111212.setText("Rodilla (F-L-A)");
        jCheckBox31111212.setBounds(new Rectangle(5, 195, 235, 15));
        jCheckBox31111212.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111212.setBackground(SystemColor.window);
        jCheckBox31111213.setText("Rodilla unilateral (F-L)");
        jCheckBox31111213.setBounds(new Rectangle(5, 215, 235, 15));
        jCheckBox31111213.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111213.setBackground(SystemColor.window);
        jCheckBox31111214.setText("Rodillas comparativas");
        jCheckBox31111214.setBounds(new Rectangle(5, 235, 235, 15));
        jCheckBox31111214.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111214.setBackground(SystemColor.window);
        jCheckBox31111215.setText("Pierna (F-L) Tibia y perone");
        jCheckBox31111215.setBounds(new Rectangle(5, 255, 235, 15));
        jCheckBox31111215.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111215.setBackground(SystemColor.window);
        jCheckBox31111216.setText("Piernas comparativas");
        jCheckBox31111216.setBounds(new Rectangle(5, 275, 235, 15));
        jCheckBox31111216.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111216.setBackground(SystemColor.window);
        jCheckBox31111217.setText("Tobillo (F-L)");
        jCheckBox31111217.setBounds(new Rectangle(5, 295, 110, 15));
        jCheckBox31111217.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111217.setBackground(SystemColor.window);
        jCheckBox31111218.setText("Tobillo (F-L-MORTAJA)");
        jCheckBox31111218.setBounds(new Rectangle(5, 315, 235, 15));
        jCheckBox31111218.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111218.setBackground(SystemColor.window);
        jCheckBox31111219.setText("Tobillos comparativos");
        jCheckBox31111219.setBounds(new Rectangle(120, 295, 120, 15));
        jCheckBox31111219.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111219.setBackground(SystemColor.window);
        jCheckBox311112110.setText("Pie (F-L-O)");
        jCheckBox311112110.setBounds(new Rectangle(5, 335, 120, 15));
        jCheckBox311112110.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112110.setBackground(SystemColor.window);
        jCheckBox311112111.setText("Pie (F-O)");
        jCheckBox311112111.setBounds(new Rectangle(130, 335, 110, 15));
        jCheckBox311112111.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112111.setBackground(SystemColor.window);
        jCheckBox311112112.setText("Pies comparativos");
        jCheckBox311112112.setBounds(new Rectangle(5, 355, 105, 15));
        jCheckBox311112112.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112112.setBackground(SystemColor.window);
        jCheckBox31111114.setText("Abdomen simple, pie (1)");
        jCheckBox31111114.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111114.setBounds(new Rectangle(5, 15, 230, 15));
        jCheckBox31111114.setBackground(SystemColor.window);
        jCheckBox31111115.setText("Abdomen simple de Cubito");
        jCheckBox31111115.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111115.setBounds(new Rectangle(5, 35, 230, 15));
        jCheckBox31111115.setBackground(SystemColor.window);
        jCheckBox31111116.setText("Colon doble contraste");
        jCheckBox31111116.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111116.setBounds(new Rectangle(5, 55, 230, 15));
        jCheckBox31111116.setBackground(SystemColor.window);
        jCheckBox31111117.setText("Esofago contrastado");
        jCheckBox31111117.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111117.setBounds(new Rectangle(5, 75, 230, 15));
        jCheckBox31111117.setBackground(SystemColor.window);
        jCheckBox31111118.setText("Est\u00f3mago y Deudeno a doble contraste (6)");
        jCheckBox31111118.setActionCommand("\u00datero y Ovarios");
        jCheckBox31111118.setBounds(new Rectangle(5, 95, 230, 15));
        jCheckBox31111118.setBackground(SystemColor.window);
        jCheckBox311112113.setText("Articulacion coxofemoral comparativa");
        jCheckBox311112113.setBounds(new Rectangle(5, 375, 235, 15));
        jCheckBox311112113.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112113.setBackground(SystemColor.window);
        jCheckBox311112114.setText("Articulacion coxofemoral comparativa");
        jCheckBox311112114.setBounds(new Rectangle(5, 375, 200, 15));
        jCheckBox311112114.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112116.setText("Articulacion coxofemoral comparativa");
        jCheckBox311112116.setBounds(new Rectangle(5, 375, 200, 15));
        jCheckBox311112116.setActionCommand("\u00datero y Ovarios");
        jCheckBox311120.setText("Cuatridimensional (4D)");
        jCheckBox311120.setBounds(new Rectangle(175, 115, 180, 15));
        jCheckBox311120.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112117.setText("Articulacion coxofemoral comparativa");
        jCheckBox311112117.setBounds(new Rectangle(5, 375, 200, 15));
        jCheckBox311112117.setActionCommand("\u00datero y Ovarios");
        jCheckBox311121.setText("Partes blandas tiroides");
        jCheckBox311121.setBounds(new Rectangle(175, 155, 180, 15));
        jCheckBox311121.setActionCommand("\u00datero y Ovarios");
        jCheckBox311122.setText("Fistulograf\u00eda perianal sin contraste");
        jCheckBox311122.setBounds(new Rectangle(10, 15, 195, 20));
        jCheckBox311122.setActionCommand("\u00datero y Ovarios");
        jCheckBox311122.setBackground(SystemColor.window);
        jCheckBox311112118.setText("Pies comparativos");
        jCheckBox311112118.setBounds(new Rectangle(5, 355, 165, 15));
        jCheckBox311112118.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112119.setText("Colposcop\u00eda");
        jCheckBox311112119.setBounds(new Rectangle(5, 15, 90, 15));
        jCheckBox311112119.setActionCommand("\u00datero y Ovarios");
        jCheckBox311112119.setBackground(SystemColor.window);
        jCheckBox3111121110.setText("Electroencefalograma");
        jCheckBox3111121110.setBounds(new Rectangle(100, 15, 125, 15));
        jCheckBox3111121110.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121110.setBackground(SystemColor.window);
        jCheckBox3111121111.setText("Electromiograf\u00eda Miembro");
        jCheckBox3111121111.setBounds(new Rectangle(5, 35, 140, 15));
        jCheckBox3111121111.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121111.setBackground(SystemColor.window);
        jCheckBox3111121112.setText("Infiltraci\u00f3n");
        jCheckBox3111121112.setBounds(new Rectangle(150, 35, 75, 15));
        jCheckBox3111121112.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121112.setBackground(SystemColor.window);
        jCheckBox3111121113.setText("Densitometr\u00eda completa (Columna-cadera-antebrazo)");
        jCheckBox3111121113.setBounds(new Rectangle(5, 15, 345, 15));
        jCheckBox3111121113.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121113.setBackground(SystemColor.window);
        jCheckBox3111121114.setText("Morfometr\u00eda pediatrica");
        jCheckBox3111121114.setBounds(new Rectangle(5, 35, 130, 15));
        jCheckBox3111121114.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121114.setBackground(SystemColor.window);
        jCheckBox3111121115.setText("Densitometr\u00eda \u00f3sea de cuerpo completo");
        jCheckBox3111121115.setBounds(new Rectangle(140, 35, 210, 15));
        jCheckBox3111121115.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121115.setBackground(SystemColor.window);
        jCheckBox3111121116.setText("Mensuraci\u00f3n miembros inf.");
        jCheckBox3111121116.setBounds(new Rectangle(5, 415, 235, 15));
        jCheckBox3111121116.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121116.setBackground(SystemColor.window);
        jCheckBox3111121117.setText("Articulaci\u00f3n coxofemoral unilateral");
        jCheckBox3111121117.setBounds(new Rectangle(5, 395, 235, 15));
        jCheckBox3111121117.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121117.setBackground(SystemColor.window);
        jCheckBox3111121118.setText("Estudio de edad osea-mano");
        jCheckBox3111121118.setBounds(new Rectangle(5, 435, 235, 15));
        jCheckBox3111121118.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121118.setBackground(SystemColor.window);
        jCheckBox3111121119.setText("Calcaneo - Talon (L-A)");
        jCheckBox3111121119.setBounds(new Rectangle(115, 355, 125, 15));
        jCheckBox3111121119.setActionCommand("\u00datero y Ovarios");
        jCheckBox3111121119.setBackground(SystemColor.window);
        jLabel19.setText("(4 proyecciones)");
        jLabel19.setBounds(new Rectangle(25, 90, 85, 15));
        jTextField8.setBounds(new Rectangle(40, 455, 200, 20));
        jLabel111.setText("Otros");
        jLabel111.setBounds(new Rectangle(5, 455, 45, 15));
        jLabel111.setBackground(SystemColor.window);
        jPanel15.setBounds(new Rectangle(270, 40, 180, 305));
        jPanel15.setLayout(null);
        jPanel15.setBorder(BorderFactory.createTitledBorder("HEMATOLOGÍA"));
        jPanel15.setBackground(SystemColor.window);
        jPanel22.setBounds(new Rectangle(825, 40, 175, 200));
        jPanel22.setBorder(BorderFactory.createTitledBorder("ORINA"));
        jPanel22.setLayout(null);
        jPanel22.setBackground(SystemColor.window);
        jPanel30.setBounds(new Rectangle(260, 25, 310, 425));
        jPanel30.setBorder(BorderFactory.createTitledBorder("INMUNOLOGÍA"));
        jPanel30.setLayout(null);
        jPanel30.setBackground(SystemColor.window);
        jPanel32.setBounds(new Rectangle(895, 25, 170, 125));
        jPanel32.setBorder(BorderFactory.createTitledBorder("HECES"));
        jPanel32.setLayout(null);
        jPanel32.setBackground(SystemColor.window);
        jPanel33.setBounds(new Rectangle(900, 195, 160, 200));
        jPanel33.setBorder(BorderFactory.createTitledBorder("MARCADORES TUMORALES"));
        jPanel33.setLayout(null);
        jPanel33.setBackground(SystemColor.window);
        jPanel34.setBounds(new Rectangle(670, 45, 140, 65));
        jPanel34.setBorder(BorderFactory.createTitledBorder("MARCADORES HEPATITIS"));
        jPanel34.setLayout(null);
        jPanel34.setBackground(SystemColor.window);
        jPanel35.setBounds(new Rectangle(510, 75, 155, 230));
        jPanel35.setBorder(BorderFactory.createTitledBorder("MICROBIOLOGÍA"));
        jPanel35.setLayout(null);
        jPanel35.setBackground(SystemColor.window);
        jCheckBox9.setText("\u00c1cido folico");
        jCheckBox9.setBounds(new Rectangle(5, 15, 170, 15));
        jCheckBox9.setBackground(SystemColor.window);
        jCheckBox10.setText("Celulas Le");
        jCheckBox10.setBounds(new Rectangle(5, 30, 170, 15));
        jCheckBox10.setBackground(SystemColor.window);
        jCheckBox11.setText("Const. Corpusculares");
        jCheckBox11.setBounds(new Rectangle(5, 45, 170, 15));
        jCheckBox11.setBackground(SystemColor.window);
        jCheckBox124.setText("Ferritina S\u00e9rica");
        jCheckBox124.setBounds(new Rectangle(5, 60, 170, 15));
        jCheckBox124.setBackground(SystemColor.window);
        jCheckBox125.setText("Gota gruesa (Paludismo)");
        jCheckBox125.setBounds(new Rectangle(5, 75, 170, 15));
        jCheckBox125.setBackground(SystemColor.window);
        jCheckBox127.setText("Grupo sanguineo y RH");
        jCheckBox127.setBounds(new Rectangle(5, 90, 170, 15));
        jCheckBox127.setBackground(SystemColor.window);
        jCheckBox128.setText("Hematocrito");
        jCheckBox128.setBounds(new Rectangle(5, 105, 170, 15));
        jCheckBox128.setBackground(SystemColor.window);
        jCheckBox129.setText("Hemograma automatizado");
        jCheckBox129.setBounds(new Rectangle(5, 135, 170, 15));
        jCheckBox129.setBackground(SystemColor.window);
        jCheckBox1210.setText("Hemoglobina");
        jCheckBox1210.setBounds(new Rectangle(5, 120, 170, 15));
        jCheckBox1210.setBackground(SystemColor.window);
        jCheckBox1211.setText("Hierro s\u00e9rico");
        jCheckBox1211.setBounds(new Rectangle(5, 150, 170, 15));
        jCheckBox1211.setBackground(SystemColor.window);
        jCheckBox1212.setText("Recuento de plaquetas");
        jCheckBox1212.setBounds(new Rectangle(5, 165, 170, 15));
        jCheckBox1212.setBackground(SystemColor.window);
        jCheckBox1213.setText("Reticulocitos");
        jCheckBox1213.setBounds(new Rectangle(5, 180, 170, 15));
        jCheckBox1213.setBackground(SystemColor.window);
        jCheckBox1214.setText("Saturaci\u00f3n de transferrina");
        jCheckBox1214.setBounds(new Rectangle(5, 195, 170, 15));
        jCheckBox1214.setBackground(SystemColor.window);
        jCheckBox1215.setText("Tiempo de coag. y sangra");
        jCheckBox1215.setBounds(new Rectangle(5, 210, 170, 15));
        jCheckBox1215.setBackground(SystemColor.window);
        jCheckBox1216.setText("Tiempo de protombina - INR");
        jCheckBox1216.setBounds(new Rectangle(5, 225, 170, 15));
        jCheckBox1216.setBackground(SystemColor.window);
        jCheckBox1217.setText("Tiempo de trombopastina - TPT");
        jCheckBox1217.setBounds(new Rectangle(5, 240, 170, 15));
        jCheckBox1217.setBackground(SystemColor.window);
        jCheckBox1218.setText("Transferrina");
        jCheckBox1218.setBounds(new Rectangle(5, 255, 170, 15));
        jCheckBox1218.setBackground(SystemColor.window);
        jCheckBox1219.setText("Vitamina B12");
        jCheckBox1219.setBounds(new Rectangle(5, 270, 170, 15));
        jCheckBox1219.setBackground(SystemColor.window);
        jCheckBox12110.setText("Vsg. vel. de sedimentaci\u00f3n");
        jCheckBox12110.setBounds(new Rectangle(5, 285, 170, 15));
        jCheckBox12110.setBackground(SystemColor.window);
        jCheckBox1221.setText("Ac. \u00farico, orina (24hrs)");
        jCheckBox1221.setBounds(new Rectangle(5, 15, 165, 15));
        jCheckBox1221.setBackground(SystemColor.window);
        jCheckBox1222.setText("Calcio orina (24hrs)");
        jCheckBox1222.setBounds(new Rectangle(5, 30, 165, 15));
        jCheckBox1222.setBackground(SystemColor.window);
        jCheckBox1223.setText("Cortisol libre (24hrs)");
        jCheckBox1223.setBounds(new Rectangle(5, 45, 165, 15));
        jCheckBox1223.setBackground(SystemColor.window);
        jCheckBox1224.setText("Electrolitos en orine de 24hrs");
        jCheckBox1224.setBounds(new Rectangle(5, 60, 165, 15));
        jCheckBox1224.setBackground(SystemColor.window);
        jCheckBox1225.setText("F\u00f3sforo orina (24hrs)");
        jCheckBox1225.setBounds(new Rectangle(5, 75, 165, 15));
        jCheckBox1225.setBackground(SystemColor.window);
        jCheckBox1226.setText("Microalbuminuria orina (24hrs)");
        jCheckBox1226.setBounds(new Rectangle(5, 90, 165, 15));
        jCheckBox1226.setBackground(SystemColor.window);
        jCheckBox1227.setText("NTX (Telopeptido)");
        jCheckBox1227.setBounds(new Rectangle(5, 105, 165, 15));
        jCheckBox1227.setBackground(SystemColor.window);
        jCheckBox1229.setText("Orina Completa");
        jCheckBox1229.setBounds(new Rectangle(5, 120, 165, 15));
        jCheckBox1229.setActionCommand("Orina Completa");
        jCheckBox1229.setBackground(SystemColor.window);
        jCheckBox12210.setText("Prote\u00ednas cualitativas");
        jCheckBox12210.setBounds(new Rectangle(5, 135, 165, 15));
        jCheckBox12210.setBackground(SystemColor.window);
        jCheckBox12211.setText("Prote\u00ednas orina (24hrs)");
        jCheckBox12211.setBounds(new Rectangle(5, 150, 165, 15));
        jCheckBox12211.setBackground(SystemColor.window);
        jCheckBox12212.setText("Magnesio de orina (24hrs)");
        jCheckBox12212.setBounds(new Rectangle(5, 165, 165, 15));
        jCheckBox12212.setBackground(SystemColor.window);
        jCheckBox12213.setText("Metanefrina orina (24hrs)");
        jCheckBox12213.setBounds(new Rectangle(5, 180, 165, 15));
        jCheckBox12213.setBackground(SystemColor.window);
        jCheckBox12214.setText("Aglutinaciones en placa");
        jCheckBox12214.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox12214.setBackground(SystemColor.window);
        jCheckBox12216.setText("Ana (Ac antinucleares)");
        jCheckBox12216.setBounds(new Rectangle(155, 15, 150, 15));
        jCheckBox12216.setBackground(SystemColor.window);
        jCheckBox12217.setText("Anca C");
        jCheckBox12217.setBounds(new Rectangle(5, 30, 145, 15));
        jCheckBox12217.setBackground(SystemColor.window);
        jCheckBox12218.setText("Anca P");
        jCheckBox12218.setBounds(new Rectangle(155, 30, 150, 15));
        jCheckBox12218.setBackground(SystemColor.window);
        jCheckBox12219.setText("Anca line Blot");
        jCheckBox12219.setBounds(new Rectangle(5, 45, 145, 15));
        jCheckBox12219.setBackground(SystemColor.window);
        jCheckBox122110.setText("Anti dna nativo (ds)");
        jCheckBox122110.setBounds(new Rectangle(155, 45, 150, 15));
        jCheckBox122110.setBackground(SystemColor.window);
        jCheckBox122111.setText("Anticardiolopina IgG");
        jCheckBox122111.setBounds(new Rectangle(5, 60, 145, 15));
        jCheckBox122111.setBackground(SystemColor.window);
        jCheckBox122112.setText("Anticardiolopina IgM");
        jCheckBox122112.setBounds(new Rectangle(155, 60, 150, 15));
        jCheckBox122112.setBackground(SystemColor.window);
        jCheckBox122113.setText("Antimitocondriales");
        jCheckBox122113.setBounds(new Rectangle(5, 75, 145, 15));
        jCheckBox122113.setBackground(SystemColor.window);
        jCheckBox122114.setText("Antimuslo liso (asma)");
        jCheckBox122114.setBounds(new Rectangle(155, 75, 150, 15));
        jCheckBox122114.setBackground(SystemColor.window);
        jCheckBox122115.setText("Aso (Antiestreptolisina) cuantitativa");
        jCheckBox122115.setBounds(new Rectangle(5, 90, 300, 15));
        jCheckBox122115.setActionCommand("Aso (Antiestreptolisina) cuantitativa");
        jCheckBox122115.setBackground(SystemColor.window);
        jCheckBox122116.setText("2 mercapto etanol");
        jCheckBox122116.setBounds(new Rectangle(5, 105, 145, 15));
        jCheckBox122116.setBackground(SystemColor.window);
        jCheckBox122117.setText("Brucela en palca");
        jCheckBox122117.setBounds(new Rectangle(5, 120, 145, 15));
        jCheckBox122117.setBackground(SystemColor.window);
        jCheckBox122118.setText("Brucela en tubo");
        jCheckBox122118.setBounds(new Rectangle(155, 120, 150, 15));
        jCheckBox122118.setBackground(SystemColor.window);
        jCheckBox1221110.setText("Rosa de bengala");
        jCheckBox1221110.setBounds(new Rectangle(155, 105, 150, 15));
        jCheckBox1221110.setBackground(SystemColor.window);
        jCheckBox1221111.setText("Set de brucela (RB+Brucella+AC)");
        jCheckBox1221111.setBounds(new Rectangle(5, 135, 300, 15));
        jCheckBox1221111.setBackground(SystemColor.window);
        jCheckBox1221112.setText("Westernblot cisticircosis");
        jCheckBox1221112.setBounds(new Rectangle(5, 150, 145, 15));
        jCheckBox1221112.setBackground(SystemColor.window);
        jCheckBox1221113.setText("Cisticercosis AC(Elisa)");
        jCheckBox1221113.setBounds(new Rectangle(155, 150, 150, 15));
        jCheckBox1221113.setBackground(SystemColor.window);
        jCheckBox1221114.setText("Clamidia trachomatis IgG");
        jCheckBox1221114.setBounds(new Rectangle(5, 165, 145, 15));
        jCheckBox1221114.setBackground(SystemColor.window);
        jCheckBox1221115.setText("Clamidia trachomatis IgM");
        jCheckBox1221115.setBounds(new Rectangle(155, 165, 150, 15));
        jCheckBox1221115.setActionCommand("Clamidia trachomatis IgM");
        jCheckBox1221115.setBackground(SystemColor.window);
        jCheckBox1221116.setText("Complemento C3");
        jCheckBox1221116.setBounds(new Rectangle(5, 180, 145, 15));
        jCheckBox1221116.setBackground(SystemColor.window);
        jCheckBox1221117.setText("Complemento C4");
        jCheckBox1221117.setBounds(new Rectangle(155, 180, 150, 15));
        jCheckBox1221117.setBackground(SystemColor.window);
        jCheckBox1221118.setText("Epstein bar virus ebna IgG");
        jCheckBox1221118.setBounds(new Rectangle(5, 195, 145, 15));
        jCheckBox1221118.setBackground(SystemColor.window);
        jCheckBox1221119.setText("Epstein bar virus ebna IgM");
        jCheckBox1221119.setBounds(new Rectangle(155, 195, 150, 15));
        jCheckBox1221119.setBackground(SystemColor.window);
        jCheckBox12211110.setText("Epstein bar virus vca IgG");
        jCheckBox12211110.setBounds(new Rectangle(5, 210, 145, 15));
        jCheckBox12211110.setBackground(SystemColor.window);
        jCheckBox12211111.setText("Epstein bar virus vca IgM");
        jCheckBox12211111.setBounds(new Rectangle(155, 210, 150, 15));
        jCheckBox12211111.setBackground(SystemColor.window);
        jCheckBox12211112.setText("Factor reumatoide (FR)");
        jCheckBox12211112.setBounds(new Rectangle(5, 225, 145, 15));
        jCheckBox12211112.setBackground(SystemColor.window);
        jCheckBox12211113.setText("FTA-ABS");
        jCheckBox12211113.setBounds(new Rectangle(155, 225, 150, 15));
        jCheckBox12211113.setBackground(SystemColor.window);
        jCheckBox12211114.setText("Helicobacter pilory IgG");
        jCheckBox12211114.setBounds(new Rectangle(5, 240, 145, 15));
        jCheckBox12211114.setBackground(SystemColor.window);
        jCheckBox12211115.setText("Helicobacter pilory IgM");
        jCheckBox12211115.setBounds(new Rectangle(155, 240, 150, 15));
        jCheckBox12211115.setBackground(SystemColor.window);
        jCheckBox12211116.setText("Herpes 1 IgM, Ac");
        jCheckBox12211116.setBounds(new Rectangle(5, 255, 145, 15));
        jCheckBox12211116.setBackground(SystemColor.window);
        jCheckBox12211117.setText("Herpes 1 IgG, Ac");
        jCheckBox12211117.setBounds(new Rectangle(155, 255, 150, 15));
        jCheckBox12211117.setBackground(SystemColor.window);
        jCheckBox12211118.setText("Herpes 2IgG, Ac");
        jCheckBox12211118.setBounds(new Rectangle(5, 270, 145, 15));
        jCheckBox12211118.setBackground(SystemColor.window);
        jCheckBox12211119.setText("Herpes 2IgM, Ac");
        jCheckBox12211119.setBounds(new Rectangle(155, 270, 150, 15));
        jCheckBox12211119.setBackground(SystemColor.window);
        jCheckBox12211120.setText("Westernblot para quiste hidatidico");
        jCheckBox12211120.setBounds(new Rectangle(5, 285, 300, 15));
        jCheckBox12211120.setBackground(SystemColor.window);
        jCheckBox122111110.setText("HTLVI - II");
        jCheckBox122111110.setBounds(new Rectangle(5, 300, 145, 15));
        jCheckBox122111110.setBackground(SystemColor.window);
        jCheckBox122111111.setText("Inmunoglobulina A,G,M");
        jCheckBox122111111.setBounds(new Rectangle(5, 315, 145, 15));
        jCheckBox122111111.setBackground(SystemColor.window);
        jCheckBox122111112.setText("Inmunoglobulina E");
        jCheckBox122111112.setBounds(new Rectangle(155, 315, 150, 15));
        jCheckBox122111112.setBackground(SystemColor.window);
        jCheckBox122111113.setText("Panel de alergia 27 alergenos");
        jCheckBox122111113.setBounds(new Rectangle(5, 330, 300, 15));
        jCheckBox122111113.setBackground(SystemColor.window);
        jCheckBox122111114.setText("Perfil ena");
        jCheckBox122111114.setBounds(new Rectangle(155, 300, 150, 15));
        jCheckBox122111114.setBackground(SystemColor.window);
        jCheckBox122111115.setText("Prote\u00edna C reactiva (PCR)");
        jCheckBox122111115.setBounds(new Rectangle(5, 345, 145, 15));
        jCheckBox122111115.setBackground(SystemColor.window);
        jCheckBox122111116.setText("Prueba de embarazo en sangre");
        jCheckBox122111116.setBounds(new Rectangle(5, 360, 300, 15));
        jCheckBox122111116.setBackground(SystemColor.window);
        jCheckBox122111117.setText("HIV 1-2");
        jCheckBox122111117.setBounds(new Rectangle(155, 345, 150, 15));
        jCheckBox122111117.setBackground(SystemColor.window);
        jCheckBox122111118.setText("Rubeola LGG, AC");
        jCheckBox122111118.setBounds(new Rectangle(5, 375, 145, 15));
        jCheckBox122111118.setBackground(SystemColor.window);
        jCheckBox122111119.setText("Rubeola LGM, AC");
        jCheckBox122111119.setBounds(new Rectangle(155, 375, 150, 15));
        jCheckBox122111119.setBackground(SystemColor.window);
        jCheckBox1221111110.setText("Serologicas (RPR/VDRL)");
        jCheckBox1221111110.setBounds(new Rectangle(5, 390, 145, 15));
        jCheckBox1221111110.setBackground(SystemColor.window);
        jCheckBox1221111111.setText("Toxoplasma IGM, AC");
        jCheckBox1221111111.setBounds(new Rectangle(155, 390, 150, 15));
        jCheckBox1221111111.setActionCommand("Toxoplasma IGM, AC");
        jCheckBox1221111111.setBackground(SystemColor.window);
        jCheckBox1221111112.setText("Toxoplasma IGG, AC");
        jCheckBox1221111112.setBounds(new Rectangle(5, 405, 145, 15));
        jCheckBox1221111112.setBackground(SystemColor.window);
        jCheckBox1221111113.setText("Prueba de waller rose");
        jCheckBox1221111113.setBounds(new Rectangle(155, 405, 150, 15));
        jCheckBox1221111113.setActionCommand("");
        jCheckBox1221111113.setBackground(SystemColor.window);
        jCheckBox122119.setBounds(new Rectangle(5, 225, 165, 15));
        jCheckBox122120.setText("Bk en esputo XI");
        jCheckBox122120.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox122120.setBackground(SystemColor.window);
        jCheckBox122121.setText("Coprocultivo");
        jCheckBox122121.setBounds(new Rectangle(5, 45, 145, 15));
        jCheckBox122121.setActionCommand("Coprocultivo");
        jCheckBox122121.setBackground(SystemColor.window);
        jCheckBox122122.setText("Coloracion gram");
        jCheckBox122122.setBounds(new Rectangle(5, 30, 145, 15));
        jCheckBox122122.setBackground(SystemColor.window);
        jCheckBox122123.setText("Cultivo sec. faringea");
        jCheckBox122123.setBounds(new Rectangle(5, 60, 145, 15));
        jCheckBox122123.setBackground(SystemColor.window);
        jCheckBox122124.setText("Cultivo de hongos");
        jCheckBox122124.setBounds(new Rectangle(5, 75, 145, 15));
        jCheckBox122124.setBackground(SystemColor.window);
        jCheckBox122125.setText("Cultivo de sec. vaginal");
        jCheckBox122125.setBounds(new Rectangle(5, 90, 145, 15));
        jCheckBox122125.setBackground(SystemColor.window);
        jCheckBox122126.setText("Cultivo de secreciones");
        jCheckBox122126.setBounds(new Rectangle(5, 105, 145, 15));
        jCheckBox122126.setBackground(SystemColor.window);
        jCheckBox122127.setText("Cultivo de semen");
        jCheckBox122127.setBounds(new Rectangle(5, 120, 145, 15));
        jCheckBox122127.setBackground(SystemColor.window);
        jCheckBox122128.setText("Espermatograma");
        jCheckBox122128.setBounds(new Rectangle(5, 135, 145, 15));
        jCheckBox122128.setBackground(SystemColor.window);
        jCheckBox122129.setText("Hemocultivo");
        jCheckBox122129.setBounds(new Rectangle(5, 150, 145, 15));
        jCheckBox122129.setBackground(SystemColor.window);
        jCheckBox1221210.setText("Hongos directo (KOH)");
        jCheckBox1221210.setBounds(new Rectangle(5, 165, 145, 15));
        jCheckBox1221210.setBackground(SystemColor.window);
        jCheckBox1221211.setText("Estudio de acaros");
        jCheckBox1221211.setBounds(new Rectangle(5, 180, 145, 15));
        jCheckBox1221211.setBackground(SystemColor.window);
        jCheckBox1221212.setText("Urocultivo");
        jCheckBox1221212.setBounds(new Rectangle(5, 195, 145, 15));
        jCheckBox1221212.setBackground(SystemColor.window);
        jCheckBox1221213.setText("Demodex follicullorum");
        jCheckBox1221213.setBounds(new Rectangle(5, 210, 145, 15));
        jCheckBox1221213.setBackground(SystemColor.window);
        jCheckBox1221214.setText("Afp (aLFA FETOPROTEINA)");
        jCheckBox1221214.setBounds(new Rectangle(5, 15, 150, 15));
        jCheckBox1221214.setBackground(SystemColor.window);
        jCheckBox1221215.setText("Beta 2 microblobulina");
        jCheckBox1221215.setBounds(new Rectangle(5, 30, 150, 15));
        jCheckBox1221215.setBackground(SystemColor.window);
        jCheckBox1221216.setText("CA-125(ovario)");
        jCheckBox1221216.setBounds(new Rectangle(5, 45, 150, 15));
        jCheckBox1221216.setBackground(SystemColor.window);
        jCheckBox1221217.setText("CA- 15-3 (mama)");
        jCheckBox1221217.setBounds(new Rectangle(5, 60, 150, 15));
        jCheckBox1221217.setBackground(SystemColor.window);
        jCheckBox1221218.setText("CA 19-9 (pancreas)");
        jCheckBox1221218.setBounds(new Rectangle(5, 75, 150, 15));
        jCheckBox1221218.setBackground(SystemColor.window);
        jCheckBox1221219.setText("CA 549 (ca mama)");
        jCheckBox1221219.setBounds(new Rectangle(5, 90, 150, 15));
        jCheckBox1221219.setBackground(SystemColor.window);
        jCheckBox12212110.setText("CA 72-4 (estomago)");
        jCheckBox12212110.setBounds(new Rectangle(5, 105, 150, 15));
        jCheckBox12212110.setBackground(SystemColor.window);
        jCheckBox12212111.setText("Calcitonina");
        jCheckBox12212111.setBounds(new Rectangle(5, 120, 150, 15));
        jCheckBox12212111.setBackground(SystemColor.window);
        jCheckBox12212112.setText("Cea");
        jCheckBox12212112.setBounds(new Rectangle(5, 135, 150, 15));
        jCheckBox12212112.setBackground(SystemColor.window);
        jCheckBox12212113.setText("Psa libre");
        jCheckBox12212113.setBounds(new Rectangle(5, 150, 150, 15));
        jCheckBox12212113.setBackground(SystemColor.window);
        jCheckBox12212114.setText("Psa total");
        jCheckBox12212114.setBounds(new Rectangle(5, 165, 150, 15));
        jCheckBox12212114.setBackground(SystemColor.window);
        jCheckBox12212115.setText("Cyfra 21.1 (Pulmon)");
        jCheckBox12212115.setBounds(new Rectangle(5, 180, 150, 15));
        jCheckBox12212115.setBackground(SystemColor.window);
        jCheckBox12212116.setText("Coprologico funcional");
        jCheckBox12212116.setBounds(new Rectangle(5, 15, 160, 15));
        jCheckBox12212116.setBackground(SystemColor.window);
        jCheckBox12212117.setText("Parasitos x3 muestras");
        jCheckBox12212117.setBounds(new Rectangle(5, 30, 160, 15));
        jCheckBox12212117.setBackground(SystemColor.window);
        jCheckBox12212118.setText("Parasitos x1 muestra");
        jCheckBox12212118.setBounds(new Rectangle(5, 45, 160, 15));
        jCheckBox12212118.setBackground(SystemColor.window);
        jCheckBox12212119.setText("Parasitos met. concentracion");
        jCheckBox12212119.setBounds(new Rectangle(5, 60, 160, 15));
        jCheckBox12212119.setBackground(SystemColor.window);
        jCheckBox122121110.setText("Reaccion inflamatoria");
        jCheckBox122121110.setBounds(new Rectangle(5, 75, 160, 15));
        jCheckBox122121110.setBackground(SystemColor.window);
        jCheckBox122121111.setText("Test de graham");
        jCheckBox122121111.setBounds(new Rectangle(5, 90, 160, 15));
        jCheckBox122121111.setBackground(SystemColor.window);
        jCheckBox122121112.setText("Thevenon");
        jCheckBox122121112.setBounds(new Rectangle(5, 105, 160, 15));
        jCheckBox122121112.setBackground(SystemColor.window);
        jPanel36.setBounds(new Rectangle(755, 265, 305, 260));
        jPanel36.setBorder(BorderFactory.createTitledBorder("BIOQUÍMICA"));
        jPanel36.setLayout(null);
        jPanel36.setBackground(SystemColor.window);
        jCheckBox122121113.setText("Acido urico");
        jCheckBox122121113.setBounds(new Rectangle(5, 15, 145, 15));
        jCheckBox122121113.setBackground(SystemColor.window);
        jCheckBox122121114.setText("Ada test");
        jCheckBox122121114.setBounds(new Rectangle(155, 15, 145, 15));
        jCheckBox122121114.setBackground(SystemColor.window);
        jCheckBox122121115.setText("Amilasa");
        jCheckBox122121115.setBounds(new Rectangle(5, 30, 145, 15));
        jCheckBox122121115.setBackground(SystemColor.window);
        jCheckBox122121116.setText("Bilirrubina total y frac.");
        jCheckBox122121116.setBounds(new Rectangle(155, 30, 145, 15));
        jCheckBox122121116.setBackground(SystemColor.window);
        jCheckBox122121117.setText("Nitrogeno ureico (Bun)");
        jCheckBox122121117.setBounds(new Rectangle(5, 45, 145, 15));
        jCheckBox122121117.setBackground(SystemColor.window);
        jCheckBox122121118.setText("Calcio ionico");
        jCheckBox122121118.setBounds(new Rectangle(155, 45, 145, 15));
        jCheckBox122121118.setBackground(SystemColor.window);
        jCheckBox122121119.setText("Calcio");
        jCheckBox122121119.setBounds(new Rectangle(5, 60, 145, 15));
        jCheckBox122121119.setBackground(SystemColor.window);
        jCheckBox1221211110.setText("Colesterol HDL");
        jCheckBox1221211110.setBounds(new Rectangle(155, 60, 145, 15));
        jCheckBox1221211110.setBackground(SystemColor.window);
        jCheckBox1221211111.setText("Colesterol LDL");
        jCheckBox1221211111.setBounds(new Rectangle(5, 75, 145, 15));
        jCheckBox1221211111.setBackground(SystemColor.window);
        jCheckBox1221211112.setText("Colesterol total");
        jCheckBox1221211112.setBounds(new Rectangle(155, 75, 145, 15));
        jCheckBox1221211112.setBackground(SystemColor.window);
        jCheckBox1221211113.setText("Colesterol VLDL");
        jCheckBox1221211113.setBounds(new Rectangle(5, 90, 145, 15));
        jCheckBox1221211113.setBackground(SystemColor.window);
        jCheckBox1221211114.setText("CPK - MB");
        jCheckBox1221211114.setBounds(new Rectangle(155, 90, 145, 15));
        jCheckBox1221211114.setBackground(SystemColor.window);
        jCheckBox1221211115.setText("CPK Total creatinina");
        jCheckBox1221211115.setBounds(new Rectangle(5, 105, 145, 15));
        jCheckBox1221211115.setBackground(SystemColor.window);
        jCheckBox1221211116.setText("DHL (Deshidrogenasa lac)");
        jCheckBox1221211116.setBounds(new Rectangle(5, 120, 145, 15));
        jCheckBox1221211116.setBackground(SystemColor.window);
        jCheckBox1221211117.setText("Electrolitos (Ns, K, Cl)");
        jCheckBox1221211117.setBounds(new Rectangle(155, 120, 145, 15));
        jCheckBox1221211117.setBackground(SystemColor.window);
        jCheckBox1221211118.setText("Estudio de l\u00edquidos");
        jCheckBox1221211118.setBounds(new Rectangle(5, 135, 145, 15));
        jCheckBox1221211118.setBackground(SystemColor.window);
        jCheckBox1221211119.setText("Fosfata alcalina");
        jCheckBox1221211119.setBounds(new Rectangle(155, 135, 145, 15));
        jCheckBox1221211119.setBackground(SystemColor.window);
        jCheckBox12212111110.setText("GGTP");
        jCheckBox12212111110.setBounds(new Rectangle(155, 150, 145, 15));
        jCheckBox12212111110.setBackground(SystemColor.window);
        jCheckBox12212111111.setText("Fosforo serico");
        jCheckBox12212111111.setBounds(new Rectangle(5, 150, 145, 15));
        jCheckBox12212111111.setBackground(SystemColor.window);
        jCheckBox12212111112.setText("Glucosa basal");
        jCheckBox12212111112.setBounds(new Rectangle(5, 165, 145, 15));
        jCheckBox12212111112.setBackground(SystemColor.window);
        jCheckBox12212111113.setText("Hemogl. glicosilada A1C");
        jCheckBox12212111113.setBounds(new Rectangle(5, 180, 145, 15));
        jCheckBox12212111113.setBackground(SystemColor.window);
        jCheckBox12212111114.setText("Glucosa post prandial");
        jCheckBox12212111114.setBounds(new Rectangle(155, 165, 145, 15));
        jCheckBox12212111114.setBackground(SystemColor.window);
        jCheckBox12212111115.setText("Lipasa");
        jCheckBox12212111115.setBounds(new Rectangle(155, 180, 145, 15));
        jCheckBox12212111115.setBackground(SystemColor.window);
        jCheckBox12212111116.setText("Proteinas totales y frac");
        jCheckBox12212111116.setBounds(new Rectangle(155, 195, 145, 15));
        jCheckBox12212111116.setBackground(SystemColor.window);
        jCheckBox12212111118.setText("Magnesio");
        jCheckBox12212111118.setBounds(new Rectangle(5, 195, 145, 15));
        jCheckBox12212111118.setBackground(SystemColor.window);
        jCheckBox12212111119.setText("Tolerancia a la glucosa");
        jCheckBox12212111119.setBounds(new Rectangle(5, 210, 145, 15));
        jCheckBox12212111119.setBackground(SystemColor.window);
        jCheckBox122121111112.setText("Transaminasas TGO");
        jCheckBox122121111112.setBounds(new Rectangle(155, 210, 145, 15));
        jCheckBox122121111112.setBackground(SystemColor.window);
        jCheckBox122121111113.setText("Trigliceridos");
        jCheckBox122121111113.setBounds(new Rectangle(155, 225, 145, 15));
        jCheckBox122121111113.setBackground(SystemColor.window);
        jCheckBox122121111114.setText("Urea");
        jCheckBox122121111114.setBounds(new Rectangle(155, 240, 145, 15));
        jCheckBox122121111114.setBackground(SystemColor.window);
        jCheckBox122121111118.setText("Transaminasas TGP");
        jCheckBox122121111118.setBounds(new Rectangle(5, 225, 145, 15));
        jCheckBox122121111118.setBackground(SystemColor.window);
        jCheckBox122121111119.setText("Troponina");
        jCheckBox122121111119.setBounds(new Rectangle(5, 240, 145, 15));
        jCheckBox122121111119.setBackground(SystemColor.window);
        jCheckBox12212111117.setText("CPK - MB");
        jCheckBox12212111117.setBounds(new Rectangle(155, 105, 145, 15));
        jCheckBox12212111117.setBackground(SystemColor.window);
        jCheckBox1221211120.setText("Hepatitis B");
        jCheckBox1221211120.setBounds(new Rectangle(5, 15, 130, 15));
        jCheckBox1221211120.setBackground(SystemColor.window);
        jCheckBox1221211121.setText("Hepatitis A");
        jCheckBox1221211121.setBounds(new Rectangle(5, 30, 130, 15));
        jCheckBox1221211121.setBackground(SystemColor.window);
        jCheckBox1221211122.setText("Hepatitis C");
        jCheckBox1221211122.setBounds(new Rectangle(5, 45, 130, 15));
        jCheckBox1221211122.setBackground(SystemColor.window);
        jPanel37.setBounds(new Rectangle(245, 400, 485, 80));
        jPanel37.setBorder(BorderFactory.createTitledBorder("ANATOMINA PATOLOGICA"));
        jPanel37.setLayout(null);
        jPanel37.setBackground(SystemColor.window);
        jCheckBox1221211124.setText("Biopsia chica con anatomia patologica(piel, gastricax endoscopia, pleura, bronquio , med osea)");
        jCheckBox1221211124.setBounds(new Rectangle(5, 15, 475, 15));
        jCheckBox1221211124.setBackground(SystemColor.window);
        jCheckBox1221211125.setText("Biopsia grande con sospecha oncologica");
        jCheckBox1221211125.setBounds(new Rectangle(5, 30, 475, 15));
        jCheckBox1221211125.setBackground(SystemColor.window);
        jCheckBox1221211126.setText("Biopsia grande (vesicula, apendice,cono leep,cono frio, utero y anexos)");
        jCheckBox1221211126.setBounds(new Rectangle(5, 45, 475, 15));
        jCheckBox1221211126.setBackground(SystemColor.window);
        jCheckBox1221211127.setText("Papanicolaou");
        jCheckBox1221211127.setBounds(new Rectangle(5, 60, 475, 15));
        jCheckBox1221211127.setBackground(SystemColor.window);
        jPanel38.setBounds(new Rectangle(40, 30, 150, 395));
        jPanel38.setBorder(BorderFactory.createTitledBorder("HORMONAS"));
        jPanel38.setLayout(null);
        jPanel38.setBackground(SystemColor.window);
        jPanel39.setBounds(new Rectangle(10, 10, 205, 515));
        jPanel39.setBorder(BorderFactory.createTitledBorder("PERFILES"));
        jPanel39.setLayout(null);
        jPanel39.setBackground(SystemColor.window);
        jCheckBox31.setText("Perfil Coagulaci\u00f3n:");
        jCheckBox31.setBounds(new Rectangle(5, 15, 170, 15));
        jCheckBox31.setBackground(SystemColor.window);
        jCheckBox32.setText("Perfil Pre - Natal:");
        jCheckBox32.setBounds(new Rectangle(5, 55, 170, 15));
        jCheckBox32.setBackground(SystemColor.window);
        jCheckBox33.setText("Perfil Pre - Operatorio:");
        jCheckBox33.setBounds(new Rectangle(5, 105, 170, 15));
        jCheckBox33.setBackground(SystemColor.window);
        jCheckBox35.setText("Hormonal Femenino:");
        jCheckBox35.setBounds(new Rectangle(5, 165, 170, 15));
        jCheckBox35.setBackground(SystemColor.window);
        jCheckBox36.setText("Anemia:");
        jCheckBox36.setBounds(new Rectangle(5, 195, 170, 15));
        jCheckBox36.setBackground(SystemColor.window);
        jCheckBox37.setText("Drogas Abuso:");
        jCheckBox37.setBounds(new Rectangle(5, 235, 170, 15));
        jCheckBox37.setBackground(SystemColor.window);
        jCheckBox38.setText("Perfil Reumatol\u00f3gico");
        jCheckBox38.setBounds(new Rectangle(5, 285, 170, 15));
        jCheckBox38.setBackground(SystemColor.window);
        jCheckBox310.setText("Perfil Tiroideo:");
        jCheckBox310.setBounds(new Rectangle(5, 305, 170, 15));
        jCheckBox310.setBackground(SystemColor.window);
        jCheckBox312.setText("Perfil Hep\u00e1tico:");
        jCheckBox312.setBounds(new Rectangle(5, 335, 170, 15));
        jCheckBox312.setBackground(SystemColor.window);
        jCheckBox313.setText("Chesqueo Pedi\u00e1trico:");
        jCheckBox313.setBounds(new Rectangle(5, 430, 170, 15));
        jCheckBox313.setBackground(SystemColor.window);
        jCheckBox314.setText("Perfil Anemia:");
        jCheckBox314.setBounds(new Rectangle(5, 390, 170, 15));
        jCheckBox314.setBackground(SystemColor.window);
        jCheckBox315.setText("Perfil Lip\u00eddico: ");
        jCheckBox315.setBounds(new Rectangle(5, 470, 170, 15));
        jCheckBox315.setBackground(SystemColor.window);
        jLabel20.setText("TC y Sangr\u00eda, Rec. de plaquetas TP,");
        jLabel20.setBounds(new Rectangle(20, 25, 180, 15));
        jLabel21.setText("TPT, Fibrinogen");
        jLabel21.setBounds(new Rectangle(20, 35, 180, 15));
        jLabel22.setText("Hemograma, Glucosa, Creatinina,");
        jLabel22.setBounds(new Rectangle(20, 65, 185, 15));
        jLabel23.setText("Grupo y Factor Sanguineo RH,");
        jLabel23.setBounds(new Rectangle(20, 75, 180, 15));
        jLabel24.setText("Hemograma, Glucosa, \u00darea,");
        jLabel24.setBounds(new Rectangle(20, 115, 180, 15));
        jLabel25.setText("Creatinina, Grupo Sangu\u00edneo, TC y");
        jLabel25.setBounds(new Rectangle(20, 125, 180, 15));
        jLabel26.setText("FSH, LH, Estradiol, Prolactina");
        jLabel26.setBounds(new Rectangle(20, 175, 180, 15));
        jLabel28.setText("RPR, Orina completa, HIV.");
        jLabel28.setBounds(new Rectangle(20, 85, 180, 15));
        jLabel29.setText("TS, RPR, Orina completa, HIV,");
        jLabel29.setBounds(new Rectangle(20, 135, 180, 15));
        jLabel210.setText("Ag. De superficie, TP.");
        jLabel210.setBounds(new Rectangle(20, 145, 180, 15));
        jLabel211.setText("Frotis, Hierro s\u00e9rico, ferritina,");
        jLabel211.setBounds(new Rectangle(20, 205, 180, 15));
        jLabel212.setText("B12, \u00c1cido F\u00f3lico");
        jLabel212.setBounds(new Rectangle(20, 215, 180, 15));
        jLabel213.setText("B12, \u00c1cido F\u00f3lico");
        jLabel214.setText("B12, \u00c1cido F\u00f3lico");
        jLabel215.setText("En Orina (5), Marihuana, \u00c9xtasis,");
        jLabel215.setBounds(new Rectangle(20, 245, 180, 15));
        jLabel215.setBackground(SystemColor.window);
        jLabel216.setText("Coca\u00edna, Metamfetamina,");
        jLabel216.setBounds(new Rectangle(20, 255, 185, 15));
        jLabel217.setText("Benzodiazepinas");
        jLabel217.setBounds(new Rectangle(20, 265, 180, 15));
        jLabel218.setText("TSH, T3 LIBRE,  T4 LIBRE.");
        jLabel218.setBounds(new Rectangle(20, 315, 180, 15));
        jLabel219.setText("TSH, T3 LIBRE,  T4 LIBRE.");
        jLabel2110.setText("TGO, TGP, Prote\u00ednas Totales y");
        jLabel2110.setBounds(new Rectangle(20, 345, 180, 15));
        jLabel2111.setText("Fracc, Bilirubinas T y F, ");
        jLabel2111.setBounds(new Rectangle(20, 355, 180, 20));
        jLabel2112.setText("F. Alcalina, GGTP");
        jLabel2112.setBounds(new Rectangle(20, 370, 180, 15));
        jLabel2113.setText("Hemograma, Hierro, Fermitina,");
        jLabel2113.setBounds(new Rectangle(20, 400, 180, 15));
        jLabel2114.setText("\u00c1cido F\u00f3lico, Vitamina b12.");
        jLabel2114.setBounds(new Rectangle(20, 410, 180, 15));
        jLabel2115.setText("Rayos X de mano, Par\u00e1sitos,");
        jLabel2115.setBounds(new Rectangle(20, 440, 180, 15));
        jLabel2116.setText("Hemoglobina, Grupo Sanguineo");
        jLabel2116.setBounds(new Rectangle(20, 450, 180, 15));
        jLabel2117.setText("Colesterol total, Col. HDL, Col. LDL,");
        jLabel2117.setBounds(new Rectangle(20, 480, 180, 15));
        jLabel2118.setText("Col. VLDL, Triglic\u00e9ridos");
        jLabel2118.setBounds(new Rectangle(20, 490, 180, 15));
        jCheckBox1221211129.setText("Acth");
        jCheckBox1221211129.setBounds(new Rectangle(5, 15, 140, 15));
        jCheckBox1221211129.setBackground(SystemColor.window);
        jCheckBox12212111210.setText("Ac. Antitiroides (TPO-TG)");
        jCheckBox12212111210.setBounds(new Rectangle(5, 30, 140, 15));
        jCheckBox12212111210.setBackground(SystemColor.window);
        jCheckBox12212111211.setText("Cortisol AM");
        jCheckBox12212111211.setBounds(new Rectangle(5, 45, 140, 15));
        jCheckBox12212111211.setBackground(SystemColor.window);
        jCheckBox12212111212.setText("Cortisol PM");
        jCheckBox12212111212.setBounds(new Rectangle(5, 60, 140, 15));
        jCheckBox12212111212.setBackground(SystemColor.window);
        jCheckBox12212111213.setText("Dheas");
        jCheckBox12212111213.setBounds(new Rectangle(5, 75, 140, 15));
        jCheckBox12212111213.setBackground(SystemColor.window);
        jCheckBox12212111214.setText("Estradiol");
        jCheckBox12212111214.setBounds(new Rectangle(5, 90, 140, 15));
        jCheckBox12212111214.setBackground(SystemColor.window);
        jCheckBox12212111215.setText("Estriol libre");
        jCheckBox12212111215.setBounds(new Rectangle(5, 105, 140, 15));
        jCheckBox12212111215.setBackground(SystemColor.window);
        jCheckBox12212111216.setText("FSH");
        jCheckBox12212111216.setBounds(new Rectangle(5, 120, 140, 15));
        jCheckBox12212111216.setBackground(SystemColor.window);
        jCheckBox12212111217.setText("Hcg Beta Cuantitativo");
        jCheckBox12212111217.setBounds(new Rectangle(5, 135, 140, 15));
        jCheckBox12212111217.setBackground(SystemColor.window);
        jCheckBox12212111218.setText("LH");
        jCheckBox12212111218.setBounds(new Rectangle(5, 195, 140, 15));
        jCheckBox12212111218.setBackground(SystemColor.window);
        jCheckBox12212111219.setText("Hormona del crecimiento");
        jCheckBox12212111219.setBounds(new Rectangle(5, 150, 140, 15));
        jCheckBox12212111219.setBackground(SystemColor.window);
        jCheckBox122121112110.setText("Insulina basal");
        jCheckBox122121112110.setBounds(new Rectangle(5, 165, 140, 15));
        jCheckBox122121112110.setBackground(SystemColor.window);
        jCheckBox122121112111.setText("Insulina post estimulacion");
        jCheckBox122121112111.setBounds(new Rectangle(5, 180, 140, 15));
        jCheckBox122121112111.setBackground(SystemColor.window);
        jCheckBox122121112112.setText("Paratohormona");
        jCheckBox122121112112.setBounds(new Rectangle(5, 210, 140, 15));
        jCheckBox122121112112.setBackground(SystemColor.window);
        jCheckBox122121112113.setText("Peptido C");
        jCheckBox122121112113.setBounds(new Rectangle(5, 225, 140, 15));
        jCheckBox122121112113.setBackground(SystemColor.window);
        jCheckBox122121112114.setText("Progesterona");
        jCheckBox122121112114.setBounds(new Rectangle(5, 240, 140, 15));
        jCheckBox122121112114.setBackground(SystemColor.window);
        jCheckBox122121112115.setText("Prolactina");
        jCheckBox122121112115.setBounds(new Rectangle(5, 255, 140, 15));
        jCheckBox122121112115.setBackground(SystemColor.window);
        jCheckBox122121112116.setText("Prolactina Pool");
        jCheckBox122121112116.setBounds(new Rectangle(5, 270, 140, 15));
        jCheckBox122121112116.setBackground(SystemColor.window);
        jCheckBox122121112117.setText("T3 libre");
        jCheckBox122121112117.setBounds(new Rectangle(5, 285, 140, 15));
        jCheckBox122121112117.setBackground(SystemColor.window);
        jCheckBox122121112118.setText("T3 total (Triodotironina)");
        jCheckBox122121112118.setBounds(new Rectangle(5, 300, 140, 15));
        jCheckBox122121112118.setBackground(SystemColor.window);
        jCheckBox122121112119.setText("T4 libre");
        jCheckBox122121112119.setBounds(new Rectangle(5, 315, 140, 15));
        jCheckBox122121112119.setBackground(SystemColor.window);
        jCheckBox1221211121110.setText("T4 total (Tiroxina)");
        jCheckBox1221211121110.setBounds(new Rectangle(5, 330, 140, 15));
        jCheckBox1221211121110.setBackground(SystemColor.window);
        jCheckBox1221211121111.setText("Testosterona libre");
        jCheckBox1221211121111.setBounds(new Rectangle(5, 345, 140, 15));
        jCheckBox1221211121111.setBackground(SystemColor.window);
        jCheckBox1221211121112.setText("Testosterona total");
        jCheckBox1221211121112.setBounds(new Rectangle(5, 360, 140, 15));
        jCheckBox1221211121112.setBackground(SystemColor.window);
        jCheckBox122121112120.setText("TSH");
        jCheckBox122121112120.setBounds(new Rectangle(5, 375, 140, 15));
        jCheckBox122121112120.setBackground(SystemColor.window);
        jPanel40.setBounds(new Rectangle(630, 175, 225, 200));
        jPanel40.setBorder(BorderFactory.createTitledBorder("PRUEBAS ESPECIALES"));
        jPanel40.setLayout(null);
        jPanel40.setBackground(SystemColor.window);
        jCheckBox12212111220.setText("Antioxidantes totales (TAS)");
        jCheckBox12212111220.setBounds(new Rectangle(5, 15, 215, 15));
        jCheckBox12212111220.setBackground(SystemColor.window);
        jCheckBox12212111221.setText("Beta 2 glicoprote\u00ednas I, AC. IgG");
        jCheckBox12212111221.setBounds(new Rectangle(5, 30, 215, 15));
        jCheckBox12212111221.setBackground(SystemColor.window);
        jCheckBox12212111222.setText("Beta 2 glicoprote\u00ednas I, AC. IgM");
        jCheckBox12212111222.setBounds(new Rectangle(5, 45, 215, 15));
        jCheckBox12212111222.setBackground(SystemColor.window);
        jCheckBox12212111223.setText("Carnitina total - dosaje");
        jCheckBox12212111223.setBounds(new Rectangle(5, 60, 215, 15));
        jCheckBox12212111223.setBackground(SystemColor.window);
        jCheckBox12212111224.setText("Electroforesis de hemoglobina");
        jCheckBox12212111224.setBounds(new Rectangle(5, 75, 215, 15));
        jCheckBox12212111224.setBackground(SystemColor.window);
        jCheckBox12212111225.setText("Homociste\u00edna - Dosaje");
        jCheckBox12212111225.setBounds(new Rectangle(5, 90, 215, 15));
        jCheckBox12212111225.setBackground(SystemColor.window);
        jCheckBox12212111226.setText("Proteinograma electrofor\u00e9tico");
        jCheckBox12212111226.setBounds(new Rectangle(5, 105, 215, 15));
        jCheckBox12212111226.setBackground(SystemColor.window);
        jCheckBox12212111227.setText("Varicela zoster, anticuerpos IgG");
        jCheckBox12212111227.setBounds(new Rectangle(5, 120, 215, 15));
        jCheckBox12212111227.setBackground(SystemColor.window);
        jCheckBox12212111228.setText("Varicela zoster, anticuerpos IgM");
        jCheckBox12212111228.setBounds(new Rectangle(5, 135, 215, 15));
        jCheckBox12212111228.setBackground(SystemColor.window);
        jCheckBox12212111229.setText("Vitamina B1 (Tiamina)");
        jCheckBox12212111229.setBounds(new Rectangle(5, 150, 215, 15));
        jCheckBox12212111229.setBackground(SystemColor.window);
        jCheckBox122121112210.setText("Vitamina D (25 - Hidroxi)");
        jCheckBox122121112210.setBounds(new Rectangle(5, 180, 215, 15));
        jCheckBox122121112210.setBackground(SystemColor.window);
        jCheckBox122121112211.setText("Vitamina D (1,25 Dihidroxi)");
        jCheckBox122121112211.setBounds(new Rectangle(5, 165, 215, 15));
        jCheckBox122121112211.setBackground(SystemColor.window);
        jPanel41.setBounds(new Rectangle(705, 440, 245, 45));
        jPanel41.setBorder(BorderFactory.createTitledBorder("OTROS"));
        jPanel41.setLayout(null);
        jPanel41.setBackground(SystemColor.window);
        jLabel31.setText("Otros:");
        jLabel31.setBounds(new Rectangle(5, 15, 35, 15));
        jTextField9.setBounds(new Rectangle(45, 15, 195, 20));
        pnlBotonesFuncion.add(lblF11, null);
        pnlBotonesFuncion.add(lblF5, null);
        pnlBotonesFuncion.add(lblF2, null);
        pnlBotonesFuncion.add(lblF6, null);
        pnlBotonesFuncion.add(lblF4, null);
        pnlBotonesFuncion.add(lblEsc, null);
        lblF6.setVisible(false);
        lblSignos.setVisible(false);
        scrPnlTxtSignos.setVisible(false);
        pnlContenedor.add(pnlBotonesFuncion, null);
        pnlContenedor.add(tabpContenedor, null);
        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);


    }
    
    private void inicialize(){
        // Inicio ID: 028
        
        /*AutoSuggestor autoSuggestor = new AutoSuggestor(txtProductoTratamiento,lblCodigoTratamiento, 
                                                        this, 
                                                        null, 
                                                        Color.WHITE.brighter(), 
                                                        Color.BLUE, 
                                                        Color.RED, 1f) {
                        @Override
                  public  boolean wordTyped(String typedWord) {
                    // ArrayList listaProd = (ArrayList) VariablesVentas.tableModelListaGlobalProductos.data.get(0);
                    ArrayList<ArrayList> listaProd = new ArrayList<>();
                        
                        for (int i = 0; i < VariablesCentroMedico.tableModelListaProductosEmpresa.data.size(); i++) {
                            ArrayList lista = (ArrayList) VariablesCentroMedico.tableModelListaProductosEmpresa.data.get(i);
                            String nomProducto = lista.get(2).toString().trim();
                            String codProd = lista.get(1).toString().trim();
                            ArrayList vAux = new ArrayList();
                            vAux.add(codProd);
                            vAux.add(nomProducto);
                            
                            listaProd.add(vAux);
                            
                        }
                        
                        setListaDatos(listaProd);
                        return super.wordTyped(typedWord);
                    }
                };*/
        // Fin ID: 028
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMotivoConsulta);
        agregarNombresACampos();
        cargarCombos();
        crearTablas();
        configurarEventosAComponentes();
        mostrarDatosConsulta();
        cargarModoVista();
       
        if(!isModoVisual()){
            HiloCargaProdReceta hilo = new HiloCargaProdReceta();
            hilo.start();
        }
    }
    
    private void configurarCampoTextArea(JTextArea textArea, Component transferFocus){
        if(transferFocus!=null)
            textArea.setNextFocusableComponent(transferFocus);
        textArea.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    contadorEnter++;
                    if(contadorEnter==2){
                        contadorEnter = 0;
                        e.consume();
                        if(((JTextArea)e.getSource()).getNextFocusableComponent() != null)
                            FarmaUtility.moveFocus(((JTextArea)e.getSource()).getNextFocusableComponent());
                    }
                }else{
                    contadorEnter = 0;
                }
            }
        });
        
        textArea.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                contadorEnter = 0;
            }
            public void focusLost(FocusEvent e){
                contadorEnter = 0;
            }
        });
    }
    
    private void configurarEventosAComponentes(){
        
        tabpContenedor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mostrarTabPanel();
            }
        });

        tabpContenedor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                    mostrarTabPanel();
                
            }
        });
        
        lblMotivoConsulta.addActionListener(new ActionTransferFocus(txtMotivoConsulta));
        txtMotivoConsulta.addActionListener(new ActionTransferFocus(txtSignos));
        lblSignos.addActionListener(new ActionTransferFocus(txtSignos));
        configurarCampoTextArea(txtSignos, txtFormaInicio);
        
        lblFormaInicio.addActionListener(new ActionTransferFocus(txtFormaInicio));
        txtFormaInicio.addActionListener(new ActionTransferFocus(txtTiempoEnfermedad));
        
        lblTiempoEnfermedad.addActionListener(new ActionTransferFocus(txtTiempoEnfermedad));
        txtTiempoEnfermedad.addActionListener(new ActionTransferFocus(cmbTipoInformante));        
        
        lblTipoInformante.addActionListener(new ActionTransferFocus(cmbTipoInformante));
        cmbTipoInformante.setNextObjTransferFocus(txtRelatoCronologico);
        
        lblRelatoCronologico.addActionListener(new ActionTransferFocus(txtRelatoCronologico));
        configurarCampoTextArea(txtRelatoCronologico, cmbApetito);
        
        lblApetito.addActionListener(new ActionTransferFocus(cmbApetito));
        cmbApetito.setNextObjTransferFocus(cmbSed);
        
        lblSed.addActionListener(new ActionTransferFocus(cmbSed));
        cmbSed.setNextObjTransferFocus(cmbSuenio);
        
        lblSuenio.addActionListener(new ActionTransferFocus(cmbSuenio));
        cmbSuenio.setNextObjTransferFocus(cmbOrina);
        
        lblOrina.addActionListener(new ActionTransferFocus(cmbOrina));
        cmbOrina.setNextObjTransferFocus(cmbDeposiciones);
        
        lblDeposiciones.addActionListener(new ActionTransferFocus(cmbDeposiciones));       
        
        lblPA.addActionListener(new ActionTransferFocus(txtPA1));
        txtPA1.addActionListener(new ActionTransferFocus(txtPA2));
        txtPA2.addActionListener(new ActionTransferFocus(txtFR));
        
        lblFR.addActionListener(new ActionTransferFocus(txtFR));
        txtFR.addActionListener(new ActionTransferFocus(txtFC));
        
        lblFC.addActionListener(new ActionTransferFocus(txtFC));
        txtFC.addActionListener(new ActionTransferFocus(txtT));
        
        lblT.addActionListener(new ActionTransferFocus(txtT));
        txtT.addActionListener(new ActionTransferFocus(txtPeso
                                                       ));
        lblPeso.addActionListener(new ActionTransferFocus(txtPeso));
        txtPeso.addActionListener(new ActionTransferFocus(txtTalla));
        
        lblTalla.addActionListener(new ActionTransferFocus(txtTalla));
        txtTalla.addActionListener(new ActionTransferFocus(cmbEstadoGeneral));
        
        lblEstadoGeneral.addActionListener(new ActionTransferFocus(cmbEstadoGeneral));
        cmbEstadoGeneral.setNextObjTransferFocus(txtEstadoConciencia);
        
        lblEstadoConciencia.addActionListener(new ActionTransferFocus(txtEstadoConciencia));
        txtEstadoConciencia.addActionListener(new ActionTransferFocus(txtFisicoDirigido));
        
        lblExamenFisicoDirigido.addActionListener(new ActionTransferFocus(txtFisicoDirigido));
        txtFisicoDirigido.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    contadorEnter++;
                    if(contadorEnter==2){
                        contadorEnter = 0;
                        e.consume();
                        tabpContenedor.setSelectedIndex(2); 
                    }
                }else{
                    contadorEnter = 0;
                }
            }
        });
        txtFisicoDirigido.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                contadorEnter = 0;
            }
            public void focusLost(FocusEvent e){
                contadorEnter = 0;
            }
        });
        
        lblDiagnostico.addActionListener(new ActionTransferFocus(txtCodDiagnostico));
        lblDiagnostico.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtCodDiagnostico);
            }
        });

        txtCodDiagnostico.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()!=KeyEvent.VK_ENTER  ||
                   e.getKeyCode() != KeyEvent.VK_DOWN ||
                   e.getKeyCode() != KeyEvent.VK_UP)
                    e.consume();
            }

            public void keyPressed(KeyEvent e) {
                if(
                    (e.getKeyCode() == KeyEvent.VK_ENTER || Character.isLetter(e.getKeyChar()))
                    &&
                    !isModoVisual()){
                    e.consume();
                    mostrarDiagnostico(e);
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
                         e.getKeyCode() == KeyEvent.VK_UP){
                    //ubicarDiagnostico((e.getKeyCode() == KeyEvent.VK_DOWN));
                    FarmaGridUtils.aceptarTeclaPresionada(e, tblListaDiagnostico, null, 0);
                }
            }
        });
        txtCodDiagnostico.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                if(txtCodDiagnostico.isEditable() && txtCodDiagnostico.isEnabled() && !isModoVisual()){
                    lblMensajeDiagnostico.setVisible(true);
                }else
                    lblMensajeDiagnostico.setVisible(false);
            }
            
            public void focusLost(FocusEvent e){
                lblMensajeDiagnostico.setVisible(false);
            }
        });
        //cmbTipoDiagnostico.setNextObjTransferFocus(txtCodDiagnostico);
        cmbTipoDiagnostico.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ADD){
                    e.consume();
                    btnAgregarDiagnostico.doClick();
                }
            }
        });
        lblTituloListaDiagnostico.addActionListener(new ActionTransferFocus(tblListaDiagnostico));
        
        btnAgregarDiagnostico.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnAgregarDiagnostico.isVisible())
                    agregarDiagnostico();
            }
        });
        
        btnQuitarDiagnostico.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnQuitarDiagnostico.isVisible())
                    quitarDiagnostico();
            }
        });

        tblListaDiagnostico.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){ 
                if(e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                    btnQuitarDiagnostico.doClick();
                if(e.getKeyCode() == KeyEvent.VK_ADD)
                    FarmaUtility.moveFocus(txtCodDiagnostico);
            }
        });


        lblTratamientoProducto.addActionListener(new ActionTransferFocus(txtProductoTratamiento));
        txtProductoTratamiento.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                /*if(e.getKeyCode()!=KeyEvent.VK_ENTER)
                    e.consume();*/
            }
            
            public void keyPressed(KeyEvent e){
                mostrarProducto(e);
            }
        });
        
        lblFrecuenciaTratamiento.addActionListener(new ActionTransferFocus(txtFrecuenciaTratamiento));
        
        txtFrecuenciaTratamiento.addActionListener(new ActionTransferFocus(txtDuracionTratamiento));
        
        lblDuracionTratamiento.addActionListener(new ActionTransferFocus(txtDuracionTratamiento));
        
        txtDuracionTratamiento.addActionListener(new ActionTransferFocus(cmbViaAdministracion));
        txtDuracionTratamiento.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                calcularCantidadVtaDosis();
            }
            
            public void focusLost(FocusEvent e){
                calcularCantidadVtaDosis();
            }
        });
        
        lblViaAdministracion.addActionListener(new ActionTransferFocus(cmbViaAdministracion));
        cmbViaAdministracion.setNextObjTransferFocus(txtDosis);
        
        lblDosis.addActionListener(new ActionTransferFocus(txtDosis));
        txtDosis.addActionListener(new ActionTransferFocus(txtCantidadTratamiento));
        
        lblCantidad.addActionListener(new ActionTransferFocus(txtCantidadTratamiento));
        txtCantidadTratamiento.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ADD)
                    btnAgregarReceta.doClick();
            }
        });
        
        lblTitleListaReceta.addActionListener(new ActionTransferFocus(tblListaReceta));
        
        tblListaReceta.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                    btnQuitarReceta.doClick();
                if(e.getKeyCode() == KeyEvent.VK_ADD)
                    FarmaUtility.moveFocus(txtProductoTratamiento);
            }
        });
        
        btnAgregarReceta.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnAgregarReceta.isVisible())
                    agregarProductoAReceta();
            }
        });
        
        btnQuitarReceta.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnQuitarReceta.isVisible()) 
                    quitarProductoAReceta();
            }
        });
        
        lblValidezReceta.addActionListener(new ActionTransferFocus(txtCantDiasValidezReceta));
        txtCantDiasValidezReceta.addActionListener(new ActionTransferFocus(txtIndicacionesGeneralesTratamiento));
        txtCantDiasValidezReceta.addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e){
                calcularFechaValidezReceta();
            }
        });
        
        lblIndicacionesGeneralesTratamiento.addActionListener(new ActionTransferFocus(txtIndicacionesGeneralesTratamiento));
        txtIndicacionesGeneralesTratamiento.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    contadorEnter++;
                    if(contadorEnter==2){
                        contadorEnter = 0;
                        e.consume();
                        tabpContenedor.setSelectedIndex(4); 
                    }
                }else{
                    contadorEnter = 0;
                }
            }
        });
        txtIndicacionesGeneralesTratamiento.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                contadorEnter = 0;
            }
            public void focusLost(FocusEvent e){
                contadorEnter = 0;
            }
        });
        
        
        
        lblExaAuxLaborotarios.addActionListener(new ActionTransferFocus(txtExaAuxLaborotarios));
        lblExaAuxLaborotarios.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtExaAuxLaborotarios);
            }
        });
        
        configurarCampoTextArea(txtExaAuxLaborotarios, txtExaAuxImagenologicos);
        
        lblExaAuxImagenologicos.addActionListener(new ActionTransferFocus(txtExaAuxImagenologicos));        
        
        txtExaAuxImagenologicos.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    contadorEnter++;
                    if(contadorEnter==2){
                        contadorEnter = 0;
                        e.consume();
                        tabpContenedor.setSelectedIndex(5); 
                    }
                }else{
                    contadorEnter = 0;
                }
            }
        });
        txtExaAuxImagenologicos.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                contadorEnter = 0;
            }
            public void focusLost(FocusEvent e){
                contadorEnter = 0;
            }
        });
        
        lblProcedimiento.addActionListener(new ActionTransferFocus(txtProcedimiento));
        lblProcedimiento.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtProcedimiento);
            }
        });
        
        configurarCampoTextArea(txtProcedimiento, txtInterconsulta);
        
        lblInterconsulta.addActionListener(new ActionTransferFocus(txtInterconsulta));
        lblInterconsulta.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtInterconsulta);
            }
        });
        
        configurarCampoTextArea(txtInterconsulta, txtTransferencia);
        
        lblTransferencia.addActionListener(new ActionTransferFocus(txtTransferencia));
        lblTransferencia.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtTransferencia);
            }
        });
        
        configurarCampoTextArea(txtTransferencia, txtDescansoMedicoInicio);
        
        lblDescansoMedico.addActionListener(new ActionTransferFocus(txtDescansoMedicoInicio));
        lblDescansoMedico.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                FarmaUtility.moveFocus(txtDescansoMedicoInicio);
            }
        });
        
        txtDescansoMedicoInicio.addActionListener(new ActionTransferFocus(txtDescansoMedicoFin));
        
        txtDescansoMedicoFin.addActionListener(new ActionTransferFocus(txtProximaCita));
        
        lblProximaCita.addActionListener(new ActionTransferFocus(txtProximaCita));
        
        aplicarFormatoFecha(txtValidezReceta);
        aplicarFormatoFecha(txtDescansoMedicoInicio);
        aplicarFormatoFecha(txtDescansoMedicoFin);
        aplicarFormatoFecha(txtProximaCita);
        
        configurarEventosComunes(pnlContenedor);
    }
    
    
    private void aplicarFormatoFecha(JTextFieldSanSerif textoField){
        textoField.setLengthText(10);
        textoField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                FarmaUtility.dateComplete((JTextField)e.getSource(), e);
            }
        });
        
    }
    
    private void configurarEventosComunes(Component panel){
        if(panel instanceof JTextField)
            panel.addKeyListener(new KeyActionComunes(panel));
        else if(panel instanceof JTable)
            panel.addKeyListener(new KeyActionComunes(panel));
        else if(panel instanceof JComboBox)
            panel.addKeyListener(new KeyActionComunes(panel));
        else if(panel instanceof JTextArea)
            panel.addKeyListener(new KeyActionComunes(panel));
        else if(panel instanceof Container){
            Component[] lst = ((Container)panel).getComponents();
            for(Component component : lst)
                configurarEventosComunes(component);
        }
    }
    
    private void cargarModoVista(){
        if(isModoVisual()){
            lblF11.setVisible(false);
            lblF5.setVisible(false);
            setImprimioReceta(true);
            setGraboReceta(true);
            deshabilitar(pnlContenedor, false);
        }else{
            txtCantDiasValidezReceta.setInt(utility.obtenerCantidadDiasVigencia(this));
            calcularFechaValidezReceta();
        }
        if(isBandImpresion()){
            lblF11.setVisible(false);
            lblF2.setVisible(false);
            lblF4.setVisible(false);
            lblF5.setVisible(false);
            lblF6.setVisible(true);
        }
    }
    
    private void calcularFechaValidezReceta(){
        if(txtCantDiasValidezReceta.getText().trim().length()>0){
            int cantidadDias = txtCantDiasValidezReceta.getInt();
            Calendar calActual = Calendar.getInstance();
            calActual.add(Calendar.DAY_OF_MONTH, cantidadDias);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtValidezReceta.setText(format.format(calActual.getTime()));
        }else{
            txtValidezReceta.setText("");
        }
    }
    
    private void deshabilitar(Component panel, boolean activo){
        if(panel instanceof JComboWithCheck)
            ((JComboWithCheck)panel).setSeleccionable(activo);
        else if(panel instanceof JComboBox)
            ((JComboBox)panel).setEditable(activo);
        else if(panel instanceof JTextComponent){
            ((JTextComponent)panel).setEditable(activo);
            ((JTextComponent)panel).setBackground(new Color(237,237,237));
        }
        else if(panel instanceof JButtonFunction)
            ((JButtonFunction)panel).setVisible(activo);
        else if(panel instanceof Container){
            Component[] lst = ((Container)panel).getComponents();
            for(Component component : lst)
                deshabilitar(component, activo);
        }
    }
    
    private void this_windowClosing(WindowEvent e) {
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        boolean continua = true;
        if(!pAceptar){
            if(isGraboReceta() && !isImprimioReceta()){
                String mensaje = "Consulta Médica:\n"+
                                 "No se completo la impresión de la receta médica\n"+
                                 "¿Desea Cerrar?";
                if(JConfirmDialog.rptaConfirmDialog(this,mensaje))
                    continua = true;
            }
        }
        if(continua){
            FarmaVariables.vAceptar = pAceptar;
            beanAtencionMedica = null;
            this.setVisible(false);
            this.dispose();
        }
    }

    private void mostrarTabPanel() {
        switch(tabpContenedor.getSelectedIndex()){
            case 0: FarmaUtility.moveFocus(txtMotivoConsulta);
                break;
            case 1: FarmaUtility.moveFocus(txtPA1);
                break;
            case 2: FarmaUtility.moveFocus(txtCodDiagnostico);
                break;
            case 3: FarmaUtility.moveFocus(txtProductoTratamiento);
                break;
            case 4: FarmaUtility.moveFocus(txtExaAuxLaborotarios);
                break;
            case 5: FarmaUtility.moveFocus(txtProcedimiento);
                break;
        }
    }
    
    private void cargaCombo(int codCombo, JComboWithCheck combo){
        ArrayList<OptionComboBox> lista = (new UtilityAtencionMedica()).obtenerListaComboCheckBox(codCombo);
        Map<Object, Boolean> optionsCombo = new LinkedHashMap<Object, Boolean>();
        for(int i=0;i<lista.size();i++){
            OptionComboBox option = lista.get(i);
            optionsCombo.put(option, option.isSeleccionado());
        }
        combo.addItems(optionsCombo);
    }
    
    private void cargarCombos(){
        cargaCombo(40, cmbTipoInformante);
        cargaCombo(39, cmbApetito);
        cargaCombo(39, cmbSed);
        cargaCombo(39, cmbSuenio);
        cargaCombo(39, cmbOrina);
        cargaCombo(39, cmbDeposiciones);
        cargaCombo(38, cmbEstadoGeneral);
        cargaCombo(37, cmbTipoDiagnostico);
        cargaCombo(36, cmbViaAdministracion);

        if(isAtencionNueva()){
            cmbApetito.selectItem(1);
            cmbSed.selectItem(1);
            cmbSuenio.selectItem(1);
            cmbOrina.selectItem(1);
            cmbDeposiciones.selectItem(1);
        }
    }
    
    private void crearTablas(){
        FarmaColumnData[] colTblDiagnostico = { new FarmaColumnData("Codigo", 80, JLabel.LEFT),            //0
                                                  new FarmaColumnData("Descripción", 440, JLabel.LEFT),     //1
                                                  new FarmaColumnData("Tipo", 135, JLabel.LEFT),            //2
                                                  new FarmaColumnData("ID_TIPO", 0, JLabel.CENTER),         //3
                                                  new FarmaColumnData("cod_diagnostico", 0, JLabel.CENTER)  //4
                                                  };
        
        mdlTblDiagnostico = new FarmaTableModel(colTblDiagnostico, UtilityPtoVenta.obtenerDefaultValuesTabla(colTblDiagnostico.length),0);
        FarmaUtility.initSimpleList(tblListaDiagnostico, mdlTblDiagnostico, colTblDiagnostico);
        //690 675
        FarmaColumnData[] colTblReceta = { new FarmaColumnData("Cod.", 50, JLabel.CENTER),              //0
                                           new FarmaColumnData("Producto", 250, JLabel.LEFT),           //1
                                           new FarmaColumnData("Tratamiento", 150, JLabel.CENTER),      //2
                                           new FarmaColumnData("Presentación", 0, JLabel.CENTER),       //3
                                           new FarmaColumnData("Frec/día", 0, JLabel.LEFT),             //4
                                           new FarmaColumnData("Tiempo", 0, JLabel.CENTER),             //5
                                           new FarmaColumnData("Via Adm.", 120, JLabel.CENTER),         //6
                                           new FarmaColumnData("Dosis", 100, JLabel.CENTER),            //7
                                           new FarmaColumnData("Cantidad", 0, JLabel.CENTER),           //8
                                           new FarmaColumnData("ValFrac", 0, JLabel.CENTER),            //9
                                           new FarmaColumnData("ID_VIA_ADMINISTRACION", 0, JLabel.CENTER), //10
                                           new FarmaColumnData("RUC_EMPRESA", 0, JLabel.CENTER) //11
                                        };
        mdlTblReceta = new FarmaTableModel(colTblReceta, UtilityPtoVenta.obtenerDefaultValuesTabla(colTblReceta.length),0);
        FarmaUtility.initSimpleList(tblListaReceta, mdlTblReceta, colTblReceta);
    }
    
    private void ubicarDiagnostico(boolean isAbajo){
        int selec = tblListaDiagnostico.getSelectedRow();
        int total = tblListaDiagnostico.getRowCount();
        if(total>0){
            if(selec==-1){
                if(isAbajo)
                    tblListaDiagnostico.setRowSelectionInterval(0, 0);
                else
                    tblListaDiagnostico.setRowSelectionInterval(total-1, total-1);
            }else{
                if(isAbajo){
                    selec = selec+1;
                    if(selec>=total){
                        selec = 0;
                    }
                }else{
                    selec = selec-1;
                    if(selec<0){
                        selec = total-1;
                    }
                }
                tblListaDiagnostico.setRowSelectionInterval(selec, selec);
            }
        }
    }
    
    private void mostrarDiagnostico(KeyEvent e){
        //ArrayList lst = utility.obtenerListaDiagnostico();
        if(lstDiagnostico == null || (lstDiagnostico!=null && lstDiagnostico.size()==0)){
            FarmaUtility.showMessage(this, "Consulta Médica:\n"+
                                           "Lista de Diagnósticos se esta cargando, reintente.", txtCodDiagnostico);
            return;
        }
        DlgListadoCM dlgListado = new DlgListadoCM(myParentFrame, "Lista", true, false, lstDiagnostico,
                                                   e);
        dlgListado.setVisible(true);
        if(FarmaVariables.vAceptar){
            FarmaVariables.vAceptar = false;
            ArrayList lstResultado = dlgListado.getLstResultado();
            if(!lstResultado.isEmpty()){
                ArrayList fila = (ArrayList)lstResultado.get(0);
                txtCodDiagnostico.setText((String)fila.get(0));
                txtDescDiagnostico.setText((String)fila.get(1));
                codDiagnosticoAux = (String)fila.get(2);
                FarmaUtility.moveFocus(cmbTipoDiagnostico);
                txtCodDiagnostico.setEditable(false);
                cmbTipoDiagnostico.selectItem(2);
            }
        }
        
    }
    
    private void agregarDiagnostico(){
        if(cmbTipoDiagnostico.getElementosSeleccionados().size()==0){
            FarmaUtility.showMessage(this, "Diagnóstico: No ha seleccionado Tipo de Diagnóstico", cmbTipoDiagnostico);
            return;
        }
        if(codDiagnosticoAux == null || (codDiagnosticoAux!=null && codDiagnosticoAux.trim().length()==0) ){
            FarmaUtility.showMessage(this, "Diagnóstico: no ha seleccionado un Diagnóstico, valido", cmbTipoDiagnostico);
            return;
        }

        String codigo = (String)cmbTipoDiagnostico.getCodigoElementAt();//FarmaLoadCVL.getCVLCode(cmbTipoDiagnostico.getName(), cmbTipoDiagnostico.getSelectedIndex());
        String descripcion = cmbTipoDiagnostico.getLabelElementAt();//FarmaLoadCVL.getCVLDescription(cmbTipoDiagnostico.getName(), codigo);
        
        ArrayList<String> fila = new ArrayList<String>();
        fila.add(txtCodDiagnostico.getText());
        fila.add(txtDescDiagnostico.getText());
        fila.add(descripcion);
        fila.add(codigo);
        fila.add(codDiagnosticoAux);
        mdlTblDiagnostico.data.add(fila);
        mdlTblDiagnostico.fireTableDataChanged();
        tblListaDiagnostico.repaint();
        codDiagnosticoAux="";
        txtCodDiagnostico.setText("");
        txtDescDiagnostico.setText("");
        txtCodDiagnostico.setEditable(true);
        FarmaUtility.moveFocus(txtCodDiagnostico);
        cmbTipoDiagnostico.selectItem(0);
        
    }
    
    private void quitarDiagnostico(){
        int seleccion = tblListaDiagnostico.getSelectedRow();
        if(seleccion >= 0){
            mdlTblDiagnostico.data.remove(seleccion);
            mdlTblDiagnostico.fireTableDataChanged();
            FarmaUtility.moveFocus(txtCodDiagnostico);
        }else{
            FarmaUtility.showMessage(this, "No ha seleccionado un registro en el Diagnostico", tblListaDiagnostico);
        }
    }
    
    private void mostrarProducto(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(VariablesCentroMedico.tableModelListaProductosEmpresa == null || 
               (VariablesCentroMedico.tableModelListaProductosEmpresa!=null && 
                VariablesCentroMedico.tableModelListaProductosEmpresa.data.size()==0)){
                FarmaUtility.showMessage(this, "Listado de productos para la receta, no se ha cargado aún!!!\n"+
                                               "Reintente por favor, en caso persista comuniquese con Mesa de Ayuda", txtProductoTratamiento);
                return;
            }else{
                DlgListaProductoReceta dlgListaProductos = new DlgListaProductoReceta(myParentFrame, "", true, true);
                dlgListaProductos.setVisible(true);
                if(FarmaVariables.vAceptar){
                    FarmaVariables.vAceptar = false;
                    //String codProd = "";
                    //ArrayList lstDato = (ArrayList)dlgListaProductos.getLstResultado().get(0);
                    String codProd = dlgListaProductos.getCodProdRecetaSelect();
                    ArrayList lstDato = utility.obtenerProductoReceta(codProd);
                    
                    txtProductoTratamiento.setText((String)lstDato.get(1)+" ("+(String)lstDato.get(6)+")");
                    lblSugerido.setText((String)lstDato.get(7));
                    lblUnidadRecetada.setText((String)lstDato.get(2));
                    
                    valMaxFracProdReceta = FarmaUtility.trunc((String)lstDato.get(5));
                    valFracProdReceta = FarmaUtility.trunc((String)lstDato.get(3));
                    codProdReceta = (String)lstDato.get(0);
                    codRucEmpresa = (String)lstDato.get(8);
                    //txtProductoTratamiento.setEditable(false);
                    txtFrecuenciaTratamiento.setEnabled(true);
                    txtDuracionTratamiento.setEnabled(true);
                    cmbViaAdministracion.setEnabled(true);
                    txtCantidadTratamiento.setEnabled(true);
                    txtDosis.setEnabled(true);
                    FarmaUtility.moveFocus(txtFrecuenciaTratamiento);
                    
                }
            }
        }
    }
    
    private void calcularCantidadVtaDosis(){
        String valFrecuencia = txtFrecuenciaTratamiento.getText().trim();
        String valDuracion = txtDuracionTratamiento.getText().trim();
        int frecuenciaDosis, duracionDosis;
        if((valFrecuencia!=null && valFrecuencia.length()>0)&&
            (valDuracion!=null && valDuracion.length()>0)
        ){
            frecuenciaDosis = Integer.parseInt(valFrecuencia);
            duracionDosis = Integer.parseInt(valDuracion);
            int cantidadDosis = frecuenciaDosis * duracionDosis;
            
            BigDecimal bCantidadDosis = new BigDecimal(cantidadDosis);
            BigDecimal bValFrac = new BigDecimal(valFracProdReceta);
            BigDecimal bValMaxFrac = new BigDecimal(valMaxFracProdReceta);
            
            BigDecimal bCantidadFracDeseada = bValFrac.multiply(bCantidadDosis);
            bCantidadFracDeseada = bCantidadFracDeseada.divide(bValMaxFrac, 2, RoundingMode.CEILING);
            //double cantidadFrac = (valFracProdReceta * cantidadDosis)/valMaxFracProdReceta;
            double cantidadFrac = bCantidadFracDeseada.doubleValue();
            if(valMaxFracProdReceta == 1)
                txtCantidadTratamiento.setInt(1);
            else
                txtCantidadTratamiento.setInt((new Double(Math.ceil(cantidadFrac))).intValue());
            
            /*
            if(valMaxFracProdReceta==1)
                txtCantidadTratamiento.setInt(1);
            else
                txtCantidadTratamiento.setInt(cantidadDosis);
            */
        }
    }
    
    private void agregarProductoAReceta(){
        if(cmbViaAdministracion.getElementosSeleccionados().size()==0){
            FarmaUtility.showMessage(this, "Receta: no ha registrado la via de administración del medicamento", cmbViaAdministracion);
            return;
        }
        if(codProdReceta.trim().length()==0){
            FarmaUtility.showMessage(this, "Receta: no ha seleccionado un producto", txtProductoTratamiento);
            return;
        }
        if(txtFrecuenciaTratamiento.getText()==null||(txtFrecuenciaTratamiento.getText()!=null && txtFrecuenciaTratamiento.getText().trim().length()==0)){
            FarmaUtility.showMessage(this, "Receta: no ha registrado la Frecuencia del tratamiento", txtFrecuenciaTratamiento);
            return;
        }
        if(txtDuracionTratamiento.getText()==null||(txtDuracionTratamiento.getText()!=null && txtDuracionTratamiento.getText().trim().length()==0)){
            FarmaUtility.showMessage(this, "Receta: no ha registrado la Duracion del tratamiento", txtDuracionTratamiento);
            return;
        }
        
        if(txtDosis.getText()==null||(txtDosis.getText()!=null && txtDosis.getText().trim().length()==0)){
            FarmaUtility.showMessage(this, "Receta: no ha registrado la Dosis de la toma.", txtDosis);
            return;
        }
        
        if(txtCantidadTratamiento.getText()==null || (txtCantidadTratamiento.getText()!=null && txtCantidadTratamiento.getText().trim().length()==0) || 
            (txtCantidadTratamiento.getText()!=null && txtCantidadTratamiento.getText().trim().length()>0 && FarmaUtility.getDecimalNumber(txtCantidadTratamiento.getText().trim())==0)
        ){
            FarmaUtility.showMessage(this, "Receta: no se ha podido determinar la cantidad del medicamento.", txtCantidadTratamiento);
            return;
        }
        
        
        String codViaAdministracion = (String)cmbViaAdministracion.getCodigoElementAt();
        String descripcion = cmbViaAdministracion.getLabelElementAt();
        String texto = txtFrecuenciaTratamiento.getText()+" veces al día X "+txtDuracionTratamiento.getText()+" días.";
        ArrayList<String> fila = new ArrayList<String>();
        fila.add(codProdReceta);
        fila.add(txtProductoTratamiento.getText());
        fila.add(texto);
        fila.add(lblUnidadRecetada.getText());
        fila.add(txtFrecuenciaTratamiento.getText());
        fila.add(txtDuracionTratamiento.getText());
        fila.add(descripcion);
        fila.add(txtDosis.getText());
        fila.add(txtCantidadTratamiento.getText());
        //fila.add(""+valMaxFracProdReceta);
        fila.add(""+valFracProdReceta);
        fila.add(codViaAdministracion);
        fila.add(codRucEmpresa);
        
        log.info("filafila ");
        log.info(fila+"");
               
        mdlTblReceta.data.add(fila);
        mdlTblReceta.fireTableDataChanged();
        
        txtProductoTratamiento.setText("");
        lblUnidadRecetada.setText("");
        lblSugerido.setText("");
        
        txtFrecuenciaTratamiento.setText("");
        txtDuracionTratamiento.setText("");
        cmbViaAdministracion.selectItem(0);
        txtDosis.setText("");
        txtCantidadTratamiento.setText("");
        
        FarmaUtility.moveFocus(txtProductoTratamiento);
        
        txtFrecuenciaTratamiento.setEnabled(false);
        txtDuracionTratamiento.setEnabled(false);
        cmbViaAdministracion.setEnabled(false);
        txtDosis.setEnabled(false);
        txtCantidadTratamiento.setEnabled(false);
        
    }
    
    private void quitarProductoAReceta(){
        int seleccion = tblListaReceta.getSelectedRow();
        if(seleccion >= 0){
            mdlTblReceta.data.remove(seleccion);
            mdlTblReceta.fireTableDataChanged();
            FarmaUtility.moveFocus(txtProductoTratamiento);
        }else{
            FarmaUtility.showMessage(this, "No ha seleccionado un registro de la receta", tblListaReceta);
        }
    }

    public BeanAtencionMedica getBeanAtencionMedica() {
        return beanAtencionMedica;
    }

    public void setBeanAtencionMedica(BeanAtencionMedica atencionMedica) {
        this.beanAtencionMedica = atencionMedica;
    }
    
    private void agregarNombresACampos(){
        txtMotivoConsulta.setName("Motivo de Consulta");
        txtFormaInicio.setName("Forma de Inicio de Enfermedad");
        txtSintomas.setName("Sintomas de Enfermedad");
        txtCurso.setName("Curso");
        txtSignos.setName("Signos de Enfermedad");
        txtTiempoEnfermedad.setName("Tiempo de Enfermedad");
        txtRelatoCronologico.setName("Relato Cronológico");
        cmbTipoInformante.setName("Tipo de Informante");
        cmbApetito.setName("Func.Biológica Apetito");
        cmbSed.setName("Func.Biológica Sed");
        cmbSuenio.setName("Func.Biológica Sueño");
        cmbOrina.setName("Func.Biológica Orina");
        cmbDeposiciones.setName("Func.Biológica Deposiciones");
        
        txtPA1.setName("Presión Arterial");
        txtPA2.setName("Presión Arterial");
        txtFR.setName("Frecuencia Respiratoria");
        txtFC.setName("Frecuencia Cardiaca");
        txtT.setName("Temperatura");
        txtPeso.setName("Peso");
        txtTalla.setName("Talla");
        
        cmbEstadoGeneral.setName("ESTADO GENERAL");
        txtEstadoConciencia.setName("ESTADO DE CONCIENCIA");
        txtFisicoDirigido.setName("EXAMEN FISICO DIRIGIDO");
        
        tblListaDiagnostico.setName("DIAGNOSTICO");
        cmbTipoDiagnostico.setName("TIPO DE DIAGNOSTICO");
        
        cmbViaAdministracion.setName("VIA ADMINISTRACION");
        tblListaReceta.setName("RECETA");
        txtValidezReceta.setName("VALIDEZ DE RECETA");
        txtIndicacionesGeneralesTratamiento.setName("INDICACIONES GENERALES DEL TRATAMIENTO");
    }
    
    private int getValorIntNulo(JNumericField jText){
        try{
            return jText.getInt();
        }catch(Exception ex){
            return Integer.MIN_VALUE;
        }
    }
    
    private double getValorDoubleNulo(JNumericField jText){
        try{
            return jText.getDouble();
        }catch(Exception ex){
            return -99999.99;
        }
    }
    
    private boolean guardarPanelEnfermedadActual(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(0);
        if(!grabaTemporal){
            //if(valorCampoEsNulo(txtMotivoConsulta)) return false;
            //if(valorCampoEsNulo(txtFormaInicio)) return false;
            //if(valorCampoEsNulo(txtCurso)) return false;
            //if(valorCampoEsNulo(txtSintomas)) return false;
            //if(valorCampoEsNulo(txtSignos)) return false;
            if(valorCampoEsNulo(txtTiempoEnfermedad)) return false;
            if(valorCampoEsNulo(cmbTipoInformante)) return false;
            if(valorCampoEsNulo(txtRelatoCronologico)) return false;
            //if(valorCampoEsNulo(cmbApetito)) return false;
            //if(valorCampoEsNulo(cmbSed)) return false;
            //if(valorCampoEsNulo(cmbSuenio)) return false;
            //if(valorCampoEsNulo(cmbOrina)) return false;
            //if(valorCampoEsNulo(cmbDeposiciones)) return false;
           
            /*if(getValorIntNulo(txtTiempoEnfermedad) == Integer.MIN_VALUE){
                FarmaUtility.showMessage(this, "Consulta Médica: \nValor del campo Tiempo de Enfermedad debe ser mayor o igual a 0 (cero). verifique!!!", txtTiempoEnfermedad);
            }*/
        }
        
        String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
        String codCia = beanAtencionMedica.getCodCia();
        String codLocal = beanAtencionMedica.getCodLocal();
        String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
        
        BeanAtMedEnfermedadActual enfActual = new BeanAtMedEnfermedadActual();
        enfActual.setCodGrupoCia(codGrupoCia);
        enfActual.setCodCia(codCia);
        enfActual.setCodLocal(codLocal);
        enfActual.setNroAtencionMedica(nroAtencion);
        enfActual.setMotivoConsulta(getValorTextoNulo(txtMotivoConsulta));
        enfActual.setFormaInicio(getValorTextoNulo(txtFormaInicio));
        enfActual.setSignos(getValorTextoNulo(txtSignos));
        enfActual.setSintomas(getValorTextoNulo(txtSintomas));
        enfActual.setCurso(getValorTextoNulo(txtCurso));
        enfActual.setTiempoEnfermedad(getValorTextoNulo(txtTiempoEnfermedad));
        enfActual.setTipoInformante(getValorTextoNulo(cmbTipoInformante.getCodigoElementAt()));
        enfActual.setRelatoCronologico(getValorTextoNulo(txtRelatoCronologico));
        enfActual.setTipoApetito(getValorTextoNulo(cmbApetito.getCodigoElementAt()));
        enfActual.setTipoSed(getValorTextoNulo(cmbSed.getCodigoElementAt()));
        enfActual.setTipoSuenio(getValorTextoNulo(cmbSuenio.getCodigoElementAt()));
        enfActual.setTipoOrina(getValorTextoNulo(cmbOrina.getCodigoElementAt()));
        enfActual.setTipoDeposicion(getValorTextoNulo(cmbDeposiciones.getCodigoElementAt()));
        beanAtencionMedica.setEnfermedadActual(enfActual);
        return true;
    }
    
    private boolean guardarPanelTriaje(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(1);
        /*if(!grabaTemporal){
            if(valorCampoEsNulo(txtPA1)) return false;
            if(valorCampoEsNulo(txtPA2)) return false;
            if(valorCampoEsNulo(txtFR)) return false;
            if(valorCampoEsNulo(txtFC)) return false;
            if(valorCampoEsNulo(txtT)) return false;
            if(valorCampoEsNulo(txtPeso)) return false;
            if(valorCampoEsNulo(txtTalla)) return false;
        }*/
        String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
        String codCia = beanAtencionMedica.getCodCia();
        String codLocal = beanAtencionMedica.getCodLocal();
        String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
        BeanAtMedTriaje triaje = null;
        if(getValorIntNulo(txtPA1) == Integer.MIN_VALUE && getValorIntNulo(txtPA2) == Integer.MIN_VALUE && 
           getValorIntNulo(txtFR) == Integer.MIN_VALUE && getValorIntNulo(txtFC) == Integer.MIN_VALUE && 
           getValorDoubleNulo(txtT) == -99999.99 && getValorDoubleNulo(txtPeso) == -99999.99 &&  
           getValorDoubleNulo(txtTalla) == -99999.99){

            triaje = new BeanAtMedTriaje();
            triaje.setCodGrupoCia(codGrupoCia);
            triaje.setCodCia(codCia);
            triaje.setCodLocal(codLocal);
            triaje.setNroAtencionMedica(nroAtencion);
            triaje.setFuncionVitalPA1(getValorIntNulo(txtPA1));
            triaje.setFuncionVitalPA2(getValorIntNulo(txtPA2));
            triaje.setFuncionVitalFR(getValorIntNulo(txtFR));
            triaje.setFuncionVitalFC(getValorIntNulo(txtFC));
            triaje.setFuncionVitalT(getValorDoubleNulo(txtT));
            triaje.setFuncionVitalPeso(getValorDoubleNulo(txtPeso));
            triaje.setFuncionvitalTalla(getValorDoubleNulo(txtTalla));
            triaje.setFuncionvitalTalla(getValorIntNulo(txtSaturacionOxigeno));
        }
        beanAtencionMedica.setTriaje(triaje);
        return true;
    }
    
    private boolean guardarPanelExamenFisico(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(1);
        if(!grabaTemporal){
            if(valorCampoEsNulo(cmbEstadoGeneral)) return false;
            if(valorCampoEsNulo(txtEstadoConciencia)) return false;
            if(valorCampoEsNulo(txtFisicoDirigido)) return false;
        }
        String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
        String codCia = beanAtencionMedica.getCodCia();
        String codLocal = beanAtencionMedica.getCodLocal();
        String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
        
        BeanAtMedExamenFisico exaFisico = new BeanAtMedExamenFisico();
        exaFisico.setCodGrupoCia(codGrupoCia);
        exaFisico.setCodCia(codCia);
        exaFisico.setCodLocal(codLocal);
        exaFisico.setNroAtencionMedica(nroAtencion);
        exaFisico.setEstadoGeneral(getValorTextoNulo(cmbEstadoGeneral.getCodigoElementAt()));
        exaFisico.setEstadoConciencia(getValorTextoNulo(txtEstadoConciencia));
        exaFisico.setExamenFisicoDirigido(getValorTextoNulo(txtFisicoDirigido));
        
        beanAtencionMedica.setExamenFisico(exaFisico);
        return true;
    }
    
    private boolean guardarPanelDiagnostico(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(2);
        //if(!grabaTemporal)
            //if(valorCampoEsNulo(tblListaDiagnostico)) return false;
        
        String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
        String codCia = beanAtencionMedica.getCodCia();
        String codLocal = beanAtencionMedica.getCodLocal();
        String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
        
        ArrayList<BeanAtMedDiagnostico> lstDiagnosticos = new ArrayList<BeanAtMedDiagnostico>();
        for(int i=0; i <tblListaDiagnostico.getRowCount(); i++){
            ArrayList fila = (ArrayList)mdlTblDiagnostico.data.get(i);
            BeanAtMedDiagnostico diagnostico = new BeanAtMedDiagnostico();
            diagnostico.setCodGrupoCia(codGrupoCia);
            diagnostico.setCodCia(codCia);
            diagnostico.setCodLocal(codLocal);
            diagnostico.setNroAtencionMedica(nroAtencion);
            diagnostico.setSecuencial(i+1);
            diagnostico.setCodDiagnostico((String)fila.get(4));
            diagnostico.setTipoDiagnostico((String)fila.get(3));
            lstDiagnosticos.add(diagnostico);
        }
        beanAtencionMedica.setDiagnostico(lstDiagnosticos);
        return true;
    }
    
    private boolean guardarPanelTratamiento(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(3);
        boolean rspta = false;
        BeanAtMedTratamiento tratamiento = null;
        FarmaUtility.moveFocus(txtProductoTratamiento);
        if(!grabaTemporal){
            if(tblListaReceta.getRowCount()==0)
                if(!JConfirmDialog.rptaConfirmDialog(this,"No ha registrado tratamiento\n" +"¿Está seguro de Guardar?"))
                    return false;
                else {
                    rspta = true;
                    setImprimioReceta(true);
                }
            else
                rspta = true;
        }else{
            rspta = !(tblListaReceta.getRowCount()==0);
        }
        
        if(rspta){
            //lstTratamiento = new BeanAtMedTratamiento();
            tabpContenedor.setSelectedIndex(3);
            
            //if(valorCampoEsNulo(tblListaReceta)) return false;
            
            if(valorCampoEsNulo(txtValidezReceta)) return false;
            //if(campoVacio(txtIndicacionesGeneralesTratamiento)) return;
            
            String fechaValidez = txtValidezReceta.getText();
            if(!validarFecha(fechaValidez)){
                FarmaUtility.showMessage(this, "Fecha de Validez invalida", txtValidezReceta);
                return false;
            }
            String indicaciones = txtIndicacionesGeneralesTratamiento.getText();
            
            String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
            String codCia = beanAtencionMedica.getCodCia();
            String codLocal = beanAtencionMedica.getCodLocal();
            String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
            //BeanAtMedTratamiento 
            tratamiento = new BeanAtMedTratamiento();
            tratamiento.setCodGrupoCia(codGrupoCia);
            tratamiento.setCodCia(codCia);
            tratamiento.setCodLocal(codLocal);
            tratamiento.setNroAtencionMedica(nroAtencion);
            tratamiento.setValidezReceta(fechaValidez);
            tratamiento.setIndicacionesGenerales(indicaciones);
            
            BeanAtMedTrataReceta receta = new BeanAtMedTrataReceta();
            receta.setCodGrupoCia(codGrupoCia);
            receta.setCodLocal(codLocal);
            receta.setNroPedidoReceta("");
            receta.setCantidadItems(tblListaReceta.getRowCount());
            ArrayList<BeanAtMedTrataRecetaDetalle> lstDetalle = new ArrayList<BeanAtMedTrataRecetaDetalle>();
            for(int i=0; i< tblListaReceta.getRowCount(); i++){
                ArrayList fila = (ArrayList)mdlTblReceta.data.get(i);
                BeanAtMedTrataRecetaDetalle detalle = new BeanAtMedTrataRecetaDetalle();
                detalle.setCodGrupoCia(codGrupoCia);
                detalle.setCodLocal(codLocal);
                detalle.setNroPedidoReceta("");
                detalle.setSecuencialDetalle(i+1);
                detalle.setCodProducto((String)fila.get(0));
                detalle.setCantidadToma(FarmaUtility.trunc((String)fila.get(8)));
                detalle.setValFraccionamiento(FarmaUtility.trunc((String)fila.get(9)));
                detalle.setUnidadVenta((String)fila.get(3));
                detalle.setFrecuenciaToma(FarmaUtility.trunc((String)fila.get(4)));
                detalle.setDuracionToma(FarmaUtility.trunc((String)fila.get(5)));
                detalle.setCodViaAdministracion(FarmaUtility.trunc((String)fila.get(10)));
                detalle.setDosis((String)fila.get(7));
                detalle.setRucEmpresa((String)fila.get(11));
                lstDetalle.add(detalle);
            }
            receta.setDetalles(lstDetalle);
            tratamiento.setReceta(receta);
        }
        beanAtencionMedica.setTratamiento(tratamiento);
        return true;
    }
    
    private boolean guardarPanelExaAuxiliares(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(4);
        BeanAtMedExamenesAuxiliares exaAuxi = null;
        if( !valorCampoEsNulo(txtExaAuxLaborotarios, false) || !valorCampoEsNulo(txtExaAuxLaborotarios, false) ){
            String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
            String codCia = beanAtencionMedica.getCodCia();
            String codLocal = beanAtencionMedica.getCodLocal();
            String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
            
            exaAuxi = new BeanAtMedExamenesAuxiliares();
            exaAuxi.setCodGrupoCia(codGrupoCia);
            exaAuxi.setCodCia(codCia);
            exaAuxi.setCodLocal(codLocal);
            exaAuxi.setNroAtencionMedica(nroAtencion);
            exaAuxi.setLaboratorio(getValorTextoNulo(txtExaAuxLaborotarios));
            exaAuxi.setImagenes(getValorTextoNulo(txtExaAuxImagenologicos));
        }
        beanAtencionMedica.setExamenesAuxiliares(exaAuxi);
        return true;
    }
    
    private boolean guardarPanelOtros(boolean grabaTemporal){
        tabpContenedor.setSelectedIndex(5);
        BeanAtMedOtros otros = null;
        if( !valorCampoEsNulo(txtProcedimiento, false) || 
            !valorCampoEsNulo(txtInterconsulta, false) || 
            !valorCampoEsNulo(txtTransferencia, false) ||
            !valorCampoEsNulo(txtDescansoMedicoInicio, false) ||
            !valorCampoEsNulo(txtDescansoMedicoFin, false) ||
            !valorCampoEsNulo(txtProximaCita, false)
        ){
            String codGrupoCia = beanAtencionMedica.getCodGrupoCia();
            String codCia = beanAtencionMedica.getCodCia();
            String codLocal = beanAtencionMedica.getCodLocal();
            String nroAtencion = beanAtencionMedica.getNroAtencionMedica();
            
            otros = new BeanAtMedOtros();
            otros.setCodGrupoCia(codGrupoCia);
            otros.setCodCia(codCia);
            otros.setCodLocal(codLocal);
            otros.setNroAtencionMedica(nroAtencion);
            otros.setProcedimiento(getValorTextoNulo(txtProcedimiento));
            otros.setInterconsulta(getValorTextoNulo(txtInterconsulta));
            otros.setTransferencia(getValorTextoNulo(txtTransferencia));
            String iniDescanso = getValorTextoNulo(txtDescansoMedicoInicio);
            String finDescanso = getValorTextoNulo(txtDescansoMedicoFin);
            boolean conFechaIniDescanso = true;
            boolean conFechaFinDescanso = true;
            if(iniDescanso == null || (iniDescanso!=null && iniDescanso.trim().length()==0))
                conFechaIniDescanso = false;
            if(finDescanso == null || (finDescanso!=null && finDescanso.trim().length()==0))
                conFechaFinDescanso = false;
            
            if(!(conFechaFinDescanso || conFechaIniDescanso)){
                iniDescanso = "";
                finDescanso = "";
            }else{
                if( !(conFechaFinDescanso && conFechaIniDescanso) ){
                    FarmaUtility.showMessage(this, "Consulta Médica: favor de validar las fechas de descanso medico, campos vacios", txtDescansoMedicoInicio);
                    return false;
                }else{
                    if(!validarFecha(iniDescanso)){
                        FarmaUtility.showMessage(this, "Consulta Médica: Formato de Fecha de Inicio de descanso invalido", txtDescansoMedicoInicio);
                        return false;
                    }
                    if(!validarFecha(finDescanso)){
                        FarmaUtility.showMessage(this, "Consulta Médica: Formato de Fecha de Fin de descanso invalido", txtDescansoMedicoFin);
                        return false;
                    }
                    Date fechaIniDescanso = obtenerFecha(iniDescanso);
                    Date fechaFinDescanso = obtenerFecha(finDescanso);
                    if(fechaIniDescanso.compareTo(fechaFinDescanso) == 1){
                        FarmaUtility.showMessage(this, "Consulta Médica: Fecha de Descanso Medico fin no puede ser menor ala fecha de inicio ", txtDescansoMedicoFin);
                        return false;
                    }
                }
            }
            otros.setDescansoMedicoInicio(iniDescanso);
            otros.setDescansoMedicoFin(finDescanso);
            
            String fechProxCita = getValorTextoNulo(txtProximaCita);
            boolean conFechaProxCita = true;
            if(fechProxCita == null || (fechProxCita!=null && fechProxCita.trim().length()==0))
                conFechaProxCita = false;
            if(conFechaProxCita){
                if(!validarFecha(fechProxCita)){
                    FarmaUtility.showMessage(this, "Consulta Médica: Formato de Fecha de Proxima Cita invalido!!!", txtProximaCita);
                    return false;
                }else{
                    Date fechaProximaCita = obtenerFecha(fechProxCita);
                    Date fechaActual = Calendar.getInstance().getTime();
                    if(fechaProximaCita.compareTo(fechaActual)<1){
                        FarmaUtility.showMessage(this, "Consulta Médica: Fecha de Proxima Cita tiene que ser mayor a la fecha actual!!!", txtProximaCita);
                        return false;
                    }
                }
            }else{
                fechProxCita = "";
            }
            otros.setProximaCita(fechProxCita);
        }
        beanAtencionMedica.setOtros(otros);
        return true;
    }
    
    private void grabarConsulta(){
        if(isModoVisual()){
            return;
        }
        //int rspta = JConfirmDialog.showOptionDialog(this, "¿Qué acción desea realizar?","Grabar Temporalmente","Finalizar Atencion");
        int rspta = JConfirmDialog.showOptionDialog(this, "¿Finalizo la atención de la Consulta Médica?","Sí, Genera Receta","No, Graba Temporalmente");
        boolean grabaTemporal = false;
        switch(rspta){
            case JOptionPane.YES_OPTION :
                grabaTemporal = false;
                break;
            case JOptionPane.NO_OPTION :
                grabaTemporal = true;
                break;
            case JOptionPane.CLOSED_OPTION: 
                return;
        }
        
        if(!isGraboReceta()){
            boolean guardoPanel = true;
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelEnfermedadActual(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelTriaje(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelExamenFisico(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelDiagnostico(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelTratamiento(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelExaAuxiliares(grabaTemporal);
            if(guardoPanel) guardoPanel = guardoPanel && guardarPanelOtros(grabaTemporal);
            if(guardoPanel){
                setGraboReceta(utility.grabarConsulta(this, beanAtencionMedica, grabaTemporal));
                if(isGraboReceta())
                    FarmaUtility.showMessage(this, "Consulta Grabada con éxito", null);
            }
        }
        
        if(isGraboReceta() && !grabaTemporal){
            if(!isImprimioReceta()){
                setImprimioReceta(imprimirReceta());
            }
            tabpContenedor.setSelectedIndex(0);
            FarmaUtility.moveFocus(txtMotivoConsulta);
            
            if(beanAtencionMedica.getTratamiento().getReceta().getDetalles().size()>0){
                if(isImprimioReceta()){
                    FarmaUtility.showMessage(this, "Receta impresa con exito.", null);
                    cerrarVentana(true);
                }    
            }
            else
                cerrarVentana(true);
                
        }else{
            if(grabaTemporal) cerrarVentana(true);
        }
        
        
    }
    
    private boolean imprimirReceta(){
        if(beanAtencionMedica.getTratamiento().getReceta().getDetalles().size()>0)
            return utility.imprimirRecetaMedica(this, beanAtencionMedica.getNroAtencionMedica());
        return true;
    }
    
    private String getValorTextoNulo(JTextComponent jText){
        if(jText.getText() == null)
            jText.setText("");
        return jText.getText().trim();
    }
    
    private String getValorTextoNulo(Object text){
        if(text == null)
            text = "";
        return (String)text;
    }
    
    private Date obtenerFecha(String fecha){
        String aux = fecha;
        int numDia, numMes, numAnio;
        numDia = numMes = numAnio =0;
        String dia = aux.substring(0,2);
        String mes = aux.substring(3,5);
        String anio = aux.substring(6);
        try{
            numDia = Integer.parseInt(dia);
            numMes = Integer.parseInt(mes);
            numAnio = Integer.parseInt(anio);
        }catch(Exception e){
            return null;
        }
        
        Calendar date = Calendar.getInstance();
        date.set(numAnio, (numMes-1), numDia);
        return date.getTime();
    }
    
    private boolean validarFecha(String fecha){
        boolean valido = true;
        if(fecha.trim().length()!=10)
            valido = false;
        else{
            String aux = fecha;
            int numDia, numMes, numAnio;
            numDia = numMes = numAnio =0;
            String dia = aux.substring(0,2);
            String mes = aux.substring(3,5);
            String anio = aux.substring(6);
            try{
                numDia = Integer.parseInt(dia);
                numMes = Integer.parseInt(mes);
                numAnio = Integer.parseInt(anio);
            }catch(Exception e){
                valido = false;
            }
            if(valido){
                Calendar date = Calendar.getInstance();
                date.set(numAnio, (numMes-1), numDia);
                Calendar dateActual = Calendar.getInstance();
                if(date.getTime().compareTo(dateActual.getTime()) == -1 )
                    valido = false;
            }
        }
        return valido;
    }
    
    private boolean valorCampoEsNulo(Component component){
        return valorCampoEsNulo(component, true);
    }
    
    private boolean valorCampoEsNulo(Component component, boolean mostrarMensaje){
        boolean vacio = false;
        if(component instanceof JTextComponent){
            String texto = ((JTextComponent)component).getText();
            if(texto == null || (texto != null && texto.trim().length() == 0)){
                vacio = true;
                if(mostrarMensaje)
                    FarmaUtility.showMessage(this, "Consulta Médica:\nCampo "+component.getName()+" vació.\nVerifique!!!", component);
            }
        }else if(component instanceof JTable){
            if(((JTable)component).getRowCount() == 0){
                vacio = true;
                if(mostrarMensaje&&component.equals(tblListaDiagnostico)){
                    FarmaUtility.showMessage(this, "Consulta Médica:\nNo ha agregado registro a la lista de "+component.getName()+".\nVerifique!!!", component);
                    if(component.equals(tblListaDiagnostico)){
                        FarmaUtility.moveFocus(txtCodDiagnostico);
                    }
                    //no dejar a nadie agregar
                    /*else if(component.equals(tblListaReceta)){
                        FarmaUtility.moveFocus(txtProductoTratamiento);
                    }*/
                    //no dejar a nadie agregar
                }
            }
        }else if(component instanceof JComboWithCheck){
            if(((JComboWithCheck)component).getElementosSeleccionados().size() == 0){
                vacio = true;
                if(mostrarMensaje)
                    FarmaUtility.showMessage(this, "Consulta Médica:\nNo ha selecciodo un item en el campo "+component.getName()+".\nverifique!!!", component);
            }
        }else if(component instanceof JComboBox){
            if(((JComboBox)component).getSelectedIndex() < 1){
                vacio = true;
                if(mostrarMensaje)
                    FarmaUtility.showMessage(this, "Consulta Médica:\nNo ha selecciodo un item en el campo "+component.getName()+".\nverifique!!!", component);
            }
        
        }
        
        return vacio;
    }
    
    private void mostrarDatosConsulta(){
        
        BeanAtMedTriaje triaje = beanAtencionMedica.getTriaje();
        if(triaje!=null){
            mostrarCampoInt(txtPA1, triaje.getFuncionVitalPA1());
            mostrarCampoInt(txtPA2, triaje.getFuncionVitalPA2());
            mostrarCampoInt(txtFR, triaje.getFuncionVitalFR());
            mostrarCampoInt(txtFC, triaje.getFuncionVitalFC());
            mostrarCampoDouble(txtT, triaje.getFuncionVitalT());
            mostrarCampoDouble(txtPeso, triaje.getFuncionVitalPeso());
            mostrarCampoDouble(txtTalla, triaje.getFuncionvitalTalla());
            mostrarCampoDouble(txtSaturacionOxigeno, triaje.getFuncionvitalSO());
            
          /*  FarmaUtility.showMessage(new JDialog(),
            "campos triaje.getFuncionvitalSO() "+triaje.getFuncionvitalSO()+ "\n"+
                                                 " triaje.getFuncionvitalTalla() "+triaje.getFuncionvitalTalla()+ "\n"+
            " triaje.getFuncionVitalPA1() "+triaje.getFuncionVitalPA1()
                                     ,
                                         new JTextField());*/
            
        }
        
        if(!isAtencionNueva()){
            BeanAtMedEnfermedadActual enfermedadActual = beanAtencionMedica.getEnfermedadActual();
            if(enfermedadActual!=null){
                txtMotivoConsulta.setText(getValorTextoNulo(enfermedadActual.getMotivoConsulta()));
                txtFormaInicio.setText(getValorTextoNulo(enfermedadActual.getFormaInicio()));
                txtSignos.setText(getValorTextoNulo(enfermedadActual.getSignos()));
                txtSintomas.setText(getValorTextoNulo(enfermedadActual.getSintomas()));
                txtCurso.setText(getValorTextoNulo(enfermedadActual.getCurso()));
                txtTiempoEnfermedad.setText(enfermedadActual.getTiempoEnfermedad());
                cmbTipoInformante.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoInformante()));
                txtRelatoCronologico.setText(getValorTextoNulo(enfermedadActual.getRelatoCronologico()));
                cmbApetito.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoApetito()));
                cmbSed.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoSed()));
                cmbSuenio.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoSuenio()));
                cmbOrina.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoOrina()));
                cmbDeposiciones.selectItemIdentificador(getValorTextoNulo(enfermedadActual.getTipoDeposicion()));
            }
        
            BeanAtMedExamenFisico exaFisico = beanAtencionMedica.getExamenFisico();
            if(exaFisico!=null){
                //FarmaLoadCVL.setSelectedValueInComboBox(cmbEstadoGeneral, cmbEstadoGeneral.getName(), getValorTextoNulo(exaFisico.getEstadoGeneral()));
                cmbEstadoGeneral.selectItemIdentificador(getValorTextoNulo(exaFisico.getEstadoGeneral()));
                txtEstadoConciencia.setText(getValorTextoNulo(exaFisico.getEstadoConciencia()));
                txtFisicoDirigido.setText(getValorTextoNulo(exaFisico.getExamenFisicoDirigido()));
            }
            
            ArrayList<BeanAtMedDiagnostico> lstDiagnosticos = beanAtencionMedica.getDiagnostico();
            if(lstDiagnosticos!=null){
                for(int i=0; i<lstDiagnosticos.size();i++){
                    BeanAtMedDiagnostico diagnostico = lstDiagnosticos.get(i);
                    if(diagnostico.getSecuencial()>0){
                        ArrayList<String> fila = new ArrayList<String>();
                        fila.add(diagnostico.getCodCIE());
                        fila.add(diagnostico.getDescripcionDiagnostico());
                        fila.add(diagnostico.getDescTipoDiagnostico());
                        fila.add(diagnostico.getTipoDiagnostico());
                        fila.add(diagnostico.getCodDiagnostico());
                        mdlTblDiagnostico.data.add(fila);
                    }
                }
                mdlTblDiagnostico.fireTableDataChanged();
            }
            
            BeanAtMedTratamiento tratamiento = beanAtencionMedica.getTratamiento();
            if(tratamiento != null){
                BeanAtMedTrataReceta receta = tratamiento.getReceta();
                if(receta.getCantidadItems() > 0){
                    ArrayList<BeanAtMedTrataRecetaDetalle> lstDetalleReceta = receta.getDetalles();
                    for(int i=0; i<lstDetalleReceta.size(); i++){
                        BeanAtMedTrataRecetaDetalle detalle = lstDetalleReceta.get(i);
                        if(detalle.getSecuencialDetalle()>0){
                            codProdReceta = getValorTextoNulo(detalle.getCodProducto());
                            //valMaxFracProdReceta = detalle.getValFraccionamiento();
                            valFracProdReceta = detalle.getValFraccionamiento();
                            txtProductoTratamiento.setText(getValorTextoNulo(detalle.getDescProducto()));
                            lblSugerido.setText(getValorTextoNulo(detalle.getUnidadVenta()));
                            lblUnidadRecetada.setText(getValorTextoNulo(detalle.getUnidadVentaFraccion()));
                            txtFrecuenciaTratamiento.setInt(detalle.getFrecuenciaToma());
                            txtDuracionTratamiento.setInt(detalle.getDuracionToma());
                            //FarmaLoadCVL.setSelectedValueInComboBox(cmbViaAdministracion, cmbViaAdministracion.getName(), (detalle.getCodViaAdministracion()+""));
                            cmbViaAdministracion.selectItemIdentificador(detalle.getCodViaAdministracion());
                            txtDosis.setText(getValorTextoNulo(detalle.getDosis()));
                            txtCantidadTratamiento.setInt(detalle.getCantidadToma());
                            agregarProductoAReceta();
                        }
                    }
                    txtValidezReceta.setText(getValorTextoNulo(tratamiento.getValidezReceta()));
                    txtIndicacionesGeneralesTratamiento.setText(getValorTextoNulo(tratamiento.getIndicacionesGenerales()));
                }
            }
            
            BeanAtMedExamenesAuxiliares exaAuxiliares = beanAtencionMedica.getExamenesAuxiliares();
            if(exaAuxiliares!=null){
                txtExaAuxLaborotarios.setText(getValorTextoNulo(exaAuxiliares.getLaboratorio()));
                txtExaAuxImagenologicos.setText(getValorTextoNulo(exaAuxiliares.getImagenes()));
            }
            
            BeanAtMedOtros otros = beanAtencionMedica.getOtros();
            if(otros!=null){
                txtProcedimiento.setText(getValorTextoNulo(otros.getProcedimiento()));
                txtInterconsulta.setText(getValorTextoNulo(otros.getInterconsulta()));
                txtTransferencia.setText(getValorTextoNulo(otros.getTransferencia()));
                txtDescansoMedicoInicio.setText(getValorTextoNulo(otros.getDescansoMedicoInicio()));
                txtDescansoMedicoFin.setText(getValorTextoNulo(otros.getDescansoMedicoFin()));
                txtProximaCita.setText(getValorTextoNulo(otros.getProximaCita()));
            }
        }
    }

    public boolean isGraboReceta() {
        return graboReceta;
    }

    public void setGraboReceta(boolean graboReceta) {
        this.graboReceta = graboReceta;
    }

    public boolean isImprimioReceta() {
        return imprimioReceta;
    }

    public void setImprimioReceta(boolean imprimioReceta) {
        this.imprimioReceta = imprimioReceta;
    }

    public BeanPaciente getBeanPaciente() {
        return beanPaciente;
    }

    public void setBeanPaciente(BeanPaciente beanPaciente) {
        this.beanPaciente = beanPaciente;
    }

    public boolean isModoVisual() {
        return modoVisual;
    }

    public void setModoVisual(boolean modoVisual) {
        this.modoVisual = modoVisual;
    }

    public boolean isAtencionNueva() {
        return atencionNueva;
    }

    public void setAtencionNueva(boolean atencionNueva) {
        this.atencionNueva = atencionNueva;
    }
    
    private void mostrarCampoInt(JNumericField jText, int valor){
        if(valor != Integer.MIN_VALUE){ 
            jText.setInt(valor);
        }
    }
    
    private void mostrarCampoDouble(JNumericField jText, double valor){
        if(valor != -99999.99){
            jText.setDouble(valor);
        }
    }
    
    private void funcionSalir(){
        if(!isModoVisual()){
            if(JConfirmDialog.rptaConfirmDialog(this, "Si cierra la ventana se borrará los datos registrados\n ¿Desea guardar los datos?"))
                grabarConsulta();
            else
                cerrarVentana(false);
        }else
            cerrarVentana(false);
    }

    private void txtDosis_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ADD)
            btnAgregarReceta.doClick();
        
    }

    private void cmbViaAdministracion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ADD)
            btnAgregarReceta.doClick();
        
    }

    private void txtDuracionTratamiento_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ADD)
            btnAgregarReceta.doClick();
        
    }

    private void txtFrecuenciaTratamiento_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ADD)
            btnAgregarReceta.doClick();
        
    }

    public void setBandImpresion(boolean bandImpresion) {
        this.bandImpresion = bandImpresion;
    }

    public boolean isBandImpresion() {
        return bandImpresion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public String getAtencion() {
        return atencion;
    }

    private void txtProductoTratamiento_keyPressed(KeyEvent e) {
        //ascascas
    }

    private void cmbSuenio_actionPerformed(ActionEvent e) {
    }

    class HiloCargaProdReceta extends Thread{
        @Override
        public void run() {
            //lstProductosReceta = utility.obtenerListadoProductos();
            if(lstDiagnostico == null){
                lstDiagnostico = new ArrayList<ArrayList<String>>();
                lstDiagnostico = utility.obtenerListaDiagnostico();
            }
            if(VariablesCentroMedico.tableModelListaProductosEmpresa == null){
                VariablesCentroMedico.tableModelListaProductosEmpresa = new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos, 0);
                ArrayList lista = utility.obtenerListadoProductos();
                VariablesCentroMedico.tableModelListaProductosEmpresa.data = lista;
                Collections.sort(VariablesCentroMedico.tableModelListaProductosEmpresa.data, new FarmaTableComparator(2, true));
            }
        }
    }
    
    private JDialog getJDialog(){
        return this;
    }

    class KeyActionComunes implements KeyListener{
        private Component obj;
        
        public KeyActionComunes(Component obj){
            this.obj = obj;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(e.getSource() instanceof JComboWithCheck ){
                    if(((JComboWithCheck)e.getSource()).isPermiteEventoEsc())
                        funcionSalir();
                }else{
                    funcionSalir();
                }
            }else if(UtilityPtoVenta.verificaVK_F2(e)){
                // mostrar antecedente del paciente
                //boolean edita = utility.verAntecedentePaciente(myParentFrame, getJDialog(), beanPaciente.getCodLocalAntecedente(), beanAtencionMedica.getCodPaciente(), beanPaciente.getNroHCAntecedente());
                if(beanPaciente.getHistoriaClinia().getBeanAntecedente().getSecuencialHC()==0){
                    BeanHCAntecedentes antecedente = new BeanHCAntecedentes();
                    antecedente.setCodGrupoCia(beanPaciente.getCodGrupoCia());
                    antecedente.setCodLocal(beanPaciente.getCodLocalAntecedente());
                    antecedente.setCodPaciente(beanPaciente.getCodPaciente());
                    antecedente.setSecuencialHC(beanPaciente.getNroHCAntecedente());
                    antecedente.setCodMedico(beanAtencionMedica.getCodMedico());
                    beanPaciente.getHistoriaClinia().setBeanAntecedente(antecedente);
                }
                utility.verAntecedentePaciente(myParentFrame, getJDialog(), beanPaciente);
            }else if(e.getKeyCode() == KeyEvent.VK_F5 && lblF5.isVisible()){
                DlgListadoAtencionesMedicas dlg = new DlgListadoAtencionesMedicas(myParentFrame, "", true);
                dlg.setCodPaciente(beanAtencionMedica.getCodPaciente());
                dlg.setVisible(true);
            }else if(e.getKeyCode() == KeyEvent.VK_F4){
                tabpContenedor.requestFocus();
            }else if(e.getKeyCode() == KeyEvent.VK_F6 && lblF6.isVisible()){
                UtilityAtencionMedica utilAtencionMedica = new UtilityAtencionMedica();
                boolean band = utilAtencionMedica.procesarImpresion(beanAtencionMedica.getCodPaciente(), getAtencion());
                if(!band){
                    FarmaUtility.showMessage((JFrame)myParentFrame, "Error al generar documento pdf de la Historia Clinica.\n"+
                                                       "Reintente, en caso persista el problema comuniquese con Mesa de Ayuda.", null);
                }else{
                    cerrarVentana(true);
                }
            }else if(UtilityPtoVenta.verificaVK_F11(e) && lblF5.isVisible() ){
                grabarConsulta();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
    
    class ActionTransferFocus implements ActionListener{
        private Component objNext;
        
        public ActionTransferFocus(Component objNext){
            this.objNext = objNext;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isModoVisual())
                FarmaUtility.moveFocus(objNext);
            else
                objNext.requestFocus();
            if(objNext instanceof JTable){
                if(((JTable)objNext).getRowCount()>0)
                    ((JTable)objNext).setRowSelectionInterval(0, 0);
            }else if(objNext instanceof JTextArea && isModoVisual()){
                ((JTextArea)objNext).selectAll();
            }
        }
    }
}
