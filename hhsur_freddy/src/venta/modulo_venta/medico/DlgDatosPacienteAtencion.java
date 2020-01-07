package venta.modulo_venta.medico;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;

import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.medico.reference.UtilityMedico;


public class DlgDatosPacienteAtencion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosPacienteAtencion.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanel jPanel1 = new JPanel();
    
    private JCheckBox cbAyunas = new JCheckBox();
    private JCheckBox cbClaustrofobia = new JCheckBox();
    private JCheckBox cbMarcapasos = new JCheckBox();
    private JCheckBox cbImMetalicos = new JCheckBox();
    private JCheckBox cbValvulaCardiaca = new JCheckBox();
    private JCheckBox cbProtesisMetalica = new JCheckBox();
    private JCheckBox cbBombaMecanica = new JCheckBox();
    private JCheckBox cbImplanteOidos = new JCheckBox();
    private JCheckBox cbMaquillaje = new JCheckBox();
    private JCheckBox cbDispositivoIntrauterino = new JCheckBox();
    private JCheckBox cbEmbarazada= new JCheckBox();
    private JCheckBox cbSospechaEmbarazada = new JCheckBox();
    private JCheckBox cbPeriodoLactancia = new JCheckBox();
    private JCheckBox cbFuma = new JCheckBox();
    private JCheckBox cbConsumeBebida = new JCheckBox();
    private JCheckBox cbFarmaCodependientes = new JCheckBox();
    private JCheckBox cbEnfermedadCorazoon = new JCheckBox();
    private JCheckBox cbConvulsiones = new JCheckBox();
    private JCheckBox cbAfeccionPulmonares = new JCheckBox();
    private JCheckBox cbProblemasRenales = new JCheckBox();
    private JCheckBox cbAzucarSangre = new JCheckBox();
    private JCheckBox cbCancer = new JCheckBox();
    private JTextField txtDetalleCancer = new JTextField();
    private JCheckBox cbRadioterapia = new JCheckBox();
    private JCheckBox cbQuimioterapia = new JCheckBox();
    private JCheckBox cbBranquiterapia = new JCheckBox();
    private JTextField txtOtroTratamiento = new JTextField();
    private JTextField txtFechaUltimoTratamiento = new JTextField();
    private JCheckBox cbOperado = new JCheckBox();
    private JTextField txtTipoOperacion = new JTextField();
    private JTextField txtFechaOperacion = new JTextField();
    private JCheckBox cbRecimiendoTratamiento = new JCheckBox();
    private JCheckBox cbIntervencionCerebral = new JCheckBox();
    private JCheckBox cbIntervencionOjos = new JCheckBox();
    private JCheckBox cbRM = new JCheckBox();
    private JCheckBox cbTC = new JCheckBox();
    private JCheckBox cbCretinina = new JCheckBox();
    private JCheckBox cbOtroExamen = new JCheckBox();
    private JTextField txtOtroExamen = new JTextField();
    private JCheckBox cbRealizoExamenAnteriormente = new JCheckBox();
    private JCheckBox cbAlergicoAlgo = new JCheckBox();
    private JTextArea taAlergias = new JTextArea();
    private JTextField txtResponsable = new JTextField();
    private JTextField txtDNI = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JTextArea taImpresionDiagnostica = new JTextArea();

    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel11 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel4 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JCheckBox cbReaccionContraste = new JCheckBox();
    private String pNumPedVta="";
    private JLabel jLabel4 = new JLabel();

    public DlgDatosPacienteAtencion() {
        this(null, "", false,"");
    }

    public DlgDatosPacienteAtencion(Frame parent, String title, boolean modal,
                                    String pNumPedVta) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.pNumPedVta = pNumPedVta;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1226, 578));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Datos Paciente");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 5, 1205, 510));
        pnlCliente.setBackground(new java.awt.Color(0, 132, 198));
        btnNombre.setText("Datos Generales ");
        btnNombre.setBounds(new Rectangle(15, 10, 170, 20));
        btnNombre.setMnemonic('n');
        btnNombre.setFont(new Font("SansSerif", 1, 14));
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });


        cbAyunas.setText("\u00bfEl paciente est\u00e1 en ayunas?");
        cbAyunas.setBounds(new Rectangle(170, 15, 230, 20));
        cbClaustrofobia.setText("Sufre de Claustrofobia");
        cbClaustrofobia.setBounds(new Rectangle(420, 15, 200, 20));
        jPanel1.setBounds(new Rectangle(15, 40, 875, 465));
        jPanel1.setBorder(BorderFactory.createTitledBorder("Tiene usted :"));
        jPanel1.setLayout(null);
        jPanel1.setBackground(SystemColor.window);
        cbMarcapasos.setText("Marcapasos");
        cbImMetalicos.setText("Implantes Metálicos");
        cbValvulaCardiaca.setText("Válvula Cardiáca");
        cbProtesisMetalica.setText("Prótesis Metálica");
        cbBombaMecanica.setText("Bomba Mecánica");
        cbImplanteOidos.setText("Implante de Oídos");
        cbMaquillaje.setText("Maquillaje permanente");
        cbDispositivoIntrauterino.setText("Dispositivo Intrauterino");
        cbEmbarazada.setText("Embarazada");
        cbSospechaEmbarazada.setText("Sospecha de Embarazo");
        cbPeriodoLactancia.setText("Periodo de lactancia");
        cbFuma.setText("Fuma");
        cbConsumeBebida.setText("Consume Bebida Alcohólica");
        cbFarmaCodependientes.setText("Farmacodependientes");
        cbEnfermedadCorazoon.setText("Enfermedades del corazón");
        cbConvulsiones.setText("Convulsiones");
        cbAfeccionPulmonares.setText("Afecciones Pulmonares");
        cbProblemasRenales.setText("Problemas Renales");//DETALLE EL TIPO DE CANCER
        cbAzucarSangre.setText("Azúcar en la sangre");//DETALLE EL TIPO DE CANCER
        
        
        cbMarcapasos.setBounds(new Rectangle(25, 25, 110, 20));
        cbImMetalicos.setBounds(new Rectangle(25, 45, 145, 20));
        cbValvulaCardiaca.setBounds(new Rectangle(25, 65, 110, 20));
        cbProtesisMetalica.setBounds(new Rectangle(25, 85, 110, 20));
        cbBombaMecanica.setBounds(new Rectangle(25, 105, 110, 20));
        cbImplanteOidos.setBounds(new Rectangle(25, 125, 160, 20));
        cbMaquillaje.setBounds(new Rectangle(25, 145, 135, 20));
        cbDispositivoIntrauterino.setBounds(new Rectangle(25, 165, 160, 20));
        cbEmbarazada.setBounds(new Rectangle(25, 185, 110, 20));
        cbSospechaEmbarazada.setBounds(new Rectangle(25, 205, 165, 20));
        cbPeriodoLactancia.setBounds(new Rectangle(25, 225, 190, 20));
        cbFuma.setBounds(new Rectangle(25, 245, 110, 20));
        cbConsumeBebida.setBounds(new Rectangle(25, 265, 170, 20));
        cbFarmaCodependientes.setBounds(new Rectangle(25, 285, 170, 20));
        cbEnfermedadCorazoon.setBounds(new Rectangle(25, 305, 170, 20));
        cbConvulsiones.setBounds(new Rectangle(25, 325, 110, 20));
        cbAfeccionPulmonares.setBounds(new Rectangle(25, 345, 175, 20));
        cbProblemasRenales.setBounds(new Rectangle(25, 365, 175, 20));
        cbAzucarSangre.setBounds(new Rectangle(25, 385, 170, 20));

        jPanel2.setBounds(new Rectangle(225, 15, 630, 105));
        jPanel2.setBorder(BorderFactory.createTitledBorder("Datos por Cáncer"));
        jPanel2.setLayout(null);
        jPanel2.setBackground(new java.awt.Color(231, 231, 231));
        cbCancer.setText("C\u00e1ncer ");
        cbCancer.setBounds(new Rectangle(25, 410, 65, 20));
        jLabel7.setText("Detalle:");
        jLabel7.setBounds(new Rectangle(25, 435, 50, 20));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        txtDetalleCancer.setBounds(new Rectangle(95, 430, 690, 20));
        jLabel8.setText("Recibe usted tratamiento de :");
        jLabel8.setBounds(new Rectangle(20, 20, 195, 15));
        jLabel8.setFont(new Font("SansSerif", 1, 13));
        cbRadioterapia.setText("Radioterapia");
        cbRadioterapia.setBounds(new Rectangle(10, 40, 120, 20));
        cbQuimioterapia.setText("Quimioterapia");
        cbQuimioterapia.setBounds(new Rectangle(140, 40, 100, 20));
        cbBranquiterapia.setText("Braquiterapia");
        cbBranquiterapia.setBounds(new Rectangle(250, 40, 100, 20));
        jLabel9.setText("Otros:");
        jLabel9.setBounds(new Rectangle(365, 40, 35, 20));
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        txtOtroTratamiento.setBounds(new Rectangle(415, 40, 210, 20));
        jLabel10.setText("Fecha \u00daltimo tratamiento ");
        jLabel10.setBounds(new Rectangle(25, 75, 140, 15));
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        txtFechaUltimoTratamiento.setBounds(new Rectangle(170, 70, 140, 20));
        txtFechaUltimoTratamiento.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFechaUltimoTratamiento_keyReleased(e);
                }
            });
        jPanel3.setBounds(new Rectangle(225, 140, 635, 275));
        jPanel3.setBorder(BorderFactory.createTitledBorder("Operaciones y Otros"));
        jPanel3.setLayout(null);
        cbOperado.setText("Operado");
        cbOperado.setBounds(new Rectangle(20, 30, 80, 18));
        jLabel11.setText("Tipo de Operaci\u00f3n ");
        jLabel11.setBounds(new Rectangle(105, 30, 120, 20));
        jLabel11.setFont(new Font("SansSerif", 1, 11));
        txtTipoOperacion.setBounds(new Rectangle(230, 30, 155, 20));
        jLabel12.setText("Fecha :");
        jLabel12.setBounds(new Rectangle(395, 30, 40, 20));
        jLabel12.setFont(new Font("SansSerif", 1, 11));
        txtFechaOperacion.setBounds(new Rectangle(455, 30, 130, 20));
        txtFechaOperacion.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFechaOperacion_keyReleased(e);
                }
            });
        cbRecimiendoTratamiento.setText("Recibiendo alg\u00fan tratamiento");
        cbRecimiendoTratamiento.setBounds(new Rectangle(20, 60, 170, 20));
        cbRecimiendoTratamiento.setFont(new Font("Tahoma", 0, 10));
        cbIntervencionCerebral.setText("Intervenci\u00f3n Quir\u00fargias cerebrales");
        cbIntervencionCerebral.setBounds(new Rectangle(205, 60, 210, 20));
        cbIntervencionCerebral.setFont(new Font("Tahoma", 0, 10));
        cbIntervencionOjos.setText("Intervenci\u00f3n Quir\u00fargicas en los Ojos ");
        cbIntervencionOjos.setBounds(new Rectangle(425, 60, 215, 20));
        cbIntervencionOjos.setFont(new Font("Tahoma", 0, 10));
        jLabel13.setText("Estudios anteriores / ex\u00e1menes complementarios ");
        jLabel13.setBounds(new Rectangle(15, 95, 475, 15));
        jLabel13.setFont(new Font("SansSerif", 1, 13));
        cbRM.setText("RM");
        cbRM.setBounds(new Rectangle(35, 120, 45, 20));
        cbTC.setText("TC");
        cbTC.setBounds(new Rectangle(100, 120, 40, 20));
        cbCretinina.setText("Creatinina");
        cbCretinina.setBounds(new Rectangle(155, 120, 80, 18));
        cbOtroExamen.setText("Otro");
        cbOtroExamen.setBounds(new Rectangle(250, 120, 55, 20));
        txtOtroExamen.setBounds(new Rectangle(315, 120, 210, 20));
        cbRealizoExamenAnteriormente.setText("\u00bfSe realiz\u00f3 un estudio anteriormente con contraste?");
        cbRealizoExamenAnteriormente.setBounds(new Rectangle(20, 155, 280, 20));
        cbAlergicoAlgo.setText("\u00bfEs usted al\u00e9rgico a alg\u00fan medicamento, alimento u otro ?");
        cbAlergicoAlgo.setBounds(new Rectangle(20, 190, 315, 20));
        jScrollPane1.setBounds(new Rectangle(335, 170, 270, 100));
        jPanel4.setBounds(new Rectangle(900, 20, 285, 475));
        jPanel4.setLayout(null);
        jPanel4.setBorder(BorderFactory.createTitledBorder("Datos Adicionales del Paciente"));
        jPanel4.setBackground(SystemColor.window);
        jLabel1.setText("Responsable de Paciente");
        jLabel1.setBounds(new Rectangle(10, 35, 140, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        txtResponsable.setBounds(new Rectangle(10, 55, 265, 20));
        jLabel2.setText("DNI");
        jLabel2.setBounds(new Rectangle(10, 75, 110, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        txtDNI.setBounds(new Rectangle(10, 90, 210, 20));
        jLabel3.setText("Tel\u00e9fono");
        jLabel3.setBounds(new Rectangle(10, 115, 100, 15));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        txtTelefono.setBounds(new Rectangle(10, 130, 210, 20));
        jLabel5.setText("Impresi\u00f3n Diagn\u00f3stica");
        jLabel5.setBounds(new Rectangle(5, 180, 250, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 12));
        jScrollPane2.setBounds(new Rectangle(5, 215, 275, 255));
        jButton1.setText("Grabar");
        jButton1.setBounds(new Rectangle(985, 520, 75, 21));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton2.setText("Cerrar");
        jButton2.setBounds(new Rectangle(1120, 520, 75, 21));
        cbReaccionContraste.setText("\u00bfPresent\u00f3 alguna reacci\u00f3n al contraste?");
        cbReaccionContraste.setBounds(new Rectangle(20, 230, 290, 20));
        jLabel4.setText("Detalle:");
        jLabel4.setBounds(new Rectangle(335, 150, 165, 15));
        jPanel2.add(txtFechaUltimoTratamiento, null);
        jPanel2.add(jLabel10, null);
        jPanel2.add(txtOtroTratamiento, null);
        jPanel2.add(jLabel9, null);
        jPanel2.add(cbBranquiterapia, null);
        jPanel2.add(cbQuimioterapia, null);
        jPanel2.add(cbRadioterapia, null);
        jPanel2.add(jLabel8, null);
        jPanel3.add(jLabel4, null);
        jScrollPane1.getViewport().add(taAlergias, null);
        jPanel3.add(jScrollPane1, null);
        jPanel3.add(cbAlergicoAlgo, null);
        jPanel3.add(cbRealizoExamenAnteriormente, null);
        jPanel3.add(txtOtroExamen, null);
        jPanel3.add(cbOtroExamen, null);
        jPanel3.add(cbCretinina, null);
        jPanel3.add(cbTC, null);
        jPanel3.add(cbRM, null);
        jPanel3.add(jLabel13, null);
        jPanel3.add(cbIntervencionOjos, null);
        jPanel3.add(cbIntervencionCerebral, null);
        jPanel3.add(cbRecimiendoTratamiento, null);
        jPanel3.add(txtFechaOperacion, null);
        jPanel3.add(jLabel12, null);
        jPanel3.add(txtTipoOperacion, null);
        jPanel3.add(jLabel11, null);
        jPanel3.add(cbOperado, null);
        jPanel3.add(cbReaccionContraste, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(cbMarcapasos, null);
        jPanel1.add(cbImMetalicos, null);
        jPanel1.add(cbValvulaCardiaca, null);
        jPanel1.add(cbProtesisMetalica, null);
        jPanel1.add(cbBombaMecanica, null);
        jPanel1.add(cbImplanteOidos, null);
        jPanel1.add(cbMaquillaje, null);
        jPanel1.add(cbDispositivoIntrauterino, null);
        jPanel1.add(cbEmbarazada, null);
        jPanel1.add(cbSospechaEmbarazada, null);
        jPanel1.add(cbPeriodoLactancia, null);
        jPanel1.add(cbFuma, null);
        jPanel1.add(cbConsumeBebida, null);
        jPanel1.add(cbFarmaCodependientes, null);
        jPanel1.add(cbEnfermedadCorazoon, null);
        jPanel1.add(cbConvulsiones, null);
        jPanel1.add(cbAfeccionPulmonares, null);

        jPanel1.add(cbProblemasRenales, null);
        jPanel1.add(cbAzucarSangre, null);
        jPanel1.add(cbCancer, null);
        jPanel1.add(jLabel7, null);
        jPanel1.add(txtDetalleCancer, null);
        jScrollPane2.getViewport().add(taImpresionDiagnostica, null);
        taAlergias.setLineWrap(true);
        taImpresionDiagnostica.setLineWrap(true);
        jPanel4.add(jScrollPane2, null);
        jPanel4.add(jLabel5, null);
        jPanel4.add(txtTelefono, null);
        jPanel4.add(jLabel3, null);
        jPanel4.add(txtDNI, null);
        jPanel4.add(jLabel2, null);
        jPanel4.add(txtResponsable, null);
        jPanel4.add(jLabel1, null);
        pnlCliente.add(jPanel4, null);
        pnlCliente.add(jPanel1, null);
        pnlCliente.add(cbClaustrofobia, null);
        pnlCliente.add(cbAyunas, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jButton2, null);
        jPanelWhite1.add(jButton1, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        //FarmaLoadCVL.loadCVLFromSP(cmbReaccionContraste, "cmbReaccionContraste","PTOVENTA_PACIENTE.GET_SEXO", new ArrayList(), false);
        FarmaVariables.vAceptar = false;
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        getDatosGrabados(pNumPedVta);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
           // grabarPaciente();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

   
    
    public static void dateCompleteNacimiento(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = "20";
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                if ((pJTextField.getText().trim().length() == 2) || 
                    (pJTextField.getText().trim().length() == 5)) {
                    pJTextField.setText(pJTextField.getText().trim() + "/");
                    if (pJTextField.getText().trim().length() == 6)
                        pJTextField.setText(pJTextField.getText().trim() + 
                                            anhoBD);
                }
            }
        } catch (Exception errAnhoBD) {
            errAnhoBD.printStackTrace();
        }
    }

    private void txtNacimiento_keyReleased(KeyEvent e) {
        dateCompleteNacimiento(txtFechaOperacion,e);
    }

    public void grabarYModificar(){
            try {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(FarmaVariables.vIdUsu);

        parametros.add(UtilityMedico.getIdCheckBox(cbAyunas));
        parametros.add(UtilityMedico.getIdCheckBox(cbClaustrofobia));
        parametros.add(UtilityMedico.getIdCheckBox(cbMarcapasos));
        parametros.add(UtilityMedico.getIdCheckBox(cbImMetalicos));
        parametros.add(UtilityMedico.getIdCheckBox(cbValvulaCardiaca));
        parametros.add(UtilityMedico.getIdCheckBox(cbProtesisMetalica));
        parametros.add(UtilityMedico.getIdCheckBox(cbBombaMecanica));
        parametros.add(UtilityMedico.getIdCheckBox(cbImplanteOidos));
        parametros.add(UtilityMedico.getIdCheckBox(cbMaquillaje));
        parametros.add(UtilityMedico.getIdCheckBox(cbDispositivoIntrauterino));
        parametros.add(UtilityMedico.getIdCheckBox(cbEmbarazada));
        parametros.add(UtilityMedico.getIdCheckBox(cbSospechaEmbarazada));
        parametros.add(UtilityMedico.getIdCheckBox(cbPeriodoLactancia));
        parametros.add(UtilityMedico.getIdCheckBox(cbFuma));
        parametros.add(UtilityMedico.getIdCheckBox(cbConsumeBebida));
        parametros.add(UtilityMedico.getIdCheckBox(cbFarmaCodependientes));
        parametros.add(UtilityMedico.getIdCheckBox(cbEnfermedadCorazoon));
        parametros.add(UtilityMedico.getIdCheckBox(cbConvulsiones));
        parametros.add(UtilityMedico.getIdCheckBox(cbAfeccionPulmonares));
        parametros.add(UtilityMedico.getIdCheckBox(cbProblemasRenales));
        parametros.add(UtilityMedico.getIdCheckBox(cbAzucarSangre));
        parametros.add(UtilityMedico.getIdCheckBox(cbCancer));
        if(txtDetalleCancer.getText().trim().length()>=0)
            parametros.add(txtDetalleCancer.getText());
        else
            parametros.add(" ");
        parametros.add(UtilityMedico.getIdCheckBox(cbRadioterapia));
        parametros.add(UtilityMedico.getIdCheckBox(cbQuimioterapia));
        parametros.add(UtilityMedico.getIdCheckBox(cbBranquiterapia));
        if(txtOtroTratamiento.getText().trim().length()>=0)
            parametros.add(txtOtroTratamiento.getText());
        else
            parametros.add(" ");

        if(txtFechaUltimoTratamiento.getText().trim().length()>=0)
            parametros.add(txtFechaUltimoTratamiento.getText());
        else
            parametros.add(" ");                               
        parametros.add(UtilityMedico.getIdCheckBox(cbOperado));
        if(txtTipoOperacion.getText().trim().length()>=0)
            parametros.add(txtTipoOperacion.getText());
        else
            parametros.add(" ");                
        if(txtFechaOperacion.getText().trim().length()>=0)
            parametros.add(txtFechaOperacion.getText());
        else
            parametros.add(" ");                                
        parametros.add(UtilityMedico.getIdCheckBox(cbRecimiendoTratamiento));
        parametros.add(UtilityMedico.getIdCheckBox(cbIntervencionCerebral));
        parametros.add(UtilityMedico.getIdCheckBox(cbIntervencionOjos));
        parametros.add(UtilityMedico.getIdCheckBox(cbRM));
        parametros.add(UtilityMedico.getIdCheckBox(cbTC));
        parametros.add(UtilityMedico.getIdCheckBox(cbCretinina));
        parametros.add(UtilityMedico.getIdCheckBox(cbOtroExamen));
        if(txtOtroExamen.getText().trim().length()>=0)
            parametros.add(txtOtroExamen.getText());
        else
            parametros.add(" ");                        
        parametros.add(UtilityMedico.getIdCheckBox(cbRealizoExamenAnteriormente));
        parametros.add(UtilityMedico.getIdCheckBox(cbAlergicoAlgo));
        if(taAlergias.getText().trim().length()>=0)
            parametros.add(taAlergias.getText());
        else
            parametros.add(" ");                        
        if(txtResponsable.getText().trim().length()>=0)
            parametros.add(txtResponsable.getText());
        else
            parametros.add(" ");                        
        if(txtDNI.getText().trim().length()>=0)
            parametros.add(txtDNI.getText());
        else
            parametros.add(" ");                        
        if(txtTelefono.getText().trim().length()>=0)
            parametros.add(txtTelefono.getText());
        else
            parametros.add(" ");                        
        parametros.add(UtilityMedico.getIdCheckBox(cbReaccionContraste));
        if(taImpresionDiagnostica.getText().trim().length()>=0)
            parametros.add(taImpresionDiagnostica.getText());
        else
            parametros.add(" ");                        
        log.debug("invocando  a PKG_VTA_ENFERMERA.P_GRABA_VTA_ENFERMERA(????):" + parametros);
        
            FarmaDBUtility.executeSQLStoredProcedure(null, "PKG_VTA_ENFERMERA.P_GRABA_VTA_ENFERMERA(?,?,?,?," +
                                                     "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                                                     ")", parametros,
                                                 false);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se grabó exitosamente.", txtDetalleCancer);
                cerrarVentana(true);
        } catch (Exception e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al grabar :\n"+e.getMessage(),txtDetalleCancer);
        }
    }
    
    public void vGetDatosGrabados(ArrayList vLista){
        try {
            ArrayList parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(pNumPedVta);
            FarmaDBUtility.executeSQLStoredProcedureArrayList(vLista, "PKG_VTA_ENFERMERA.F_GET_DATOS_ENFERMERA(?,?,?)",
                                                              parametros);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }


    private void jButton1_actionPerformed(ActionEvent e) {
        if(FarmaUtility.rptaConfirmDialogDefaultNo(this, "¿Está seguro de grabar los datos ingresados?"))
            grabarYModificar();
    }

    private void txtFechaUltimoTratamiento_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaUltimoTratamiento, e);
    }

    private void txtFechaOperacion_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaOperacion, e);
    }

    private void getDatosGrabados(String vNumPedVta) {
        ArrayList vLista = new ArrayList();
        vGetDatosGrabados(vLista);
        if(vLista.size()>0){
            setValMensaje(cbAyunas,FarmaUtility.getValueFieldArrayList(vLista, 0,0));
            setValMensaje(cbClaustrofobia,FarmaUtility.getValueFieldArrayList(vLista, 0,1));
            setValMensaje(cbMarcapasos,FarmaUtility.getValueFieldArrayList(vLista, 0,2));
            setValMensaje(cbImMetalicos,FarmaUtility.getValueFieldArrayList(vLista, 0,3));
            setValMensaje(cbValvulaCardiaca,FarmaUtility.getValueFieldArrayList(vLista, 0,4));
            setValMensaje(cbProtesisMetalica,FarmaUtility.getValueFieldArrayList(vLista, 0,5));
            setValMensaje(cbBombaMecanica,FarmaUtility.getValueFieldArrayList(vLista, 0,6));
            setValMensaje(cbImplanteOidos,FarmaUtility.getValueFieldArrayList(vLista, 0,7));
            setValMensaje(cbMaquillaje,FarmaUtility.getValueFieldArrayList(vLista, 0,8));
            setValMensaje(cbDispositivoIntrauterino,FarmaUtility.getValueFieldArrayList(vLista, 0,9));
            setValMensaje(cbEmbarazada,FarmaUtility.getValueFieldArrayList(vLista, 0,10));
            setValMensaje(cbSospechaEmbarazada,FarmaUtility.getValueFieldArrayList(vLista, 0,11));
            setValMensaje(cbPeriodoLactancia,FarmaUtility.getValueFieldArrayList(vLista, 0,12));
            setValMensaje(cbFuma,FarmaUtility.getValueFieldArrayList(vLista, 0,13));
            setValMensaje(cbConsumeBebida,FarmaUtility.getValueFieldArrayList(vLista, 0,14));
            setValMensaje(cbFarmaCodependientes,FarmaUtility.getValueFieldArrayList(vLista, 0,15));
            setValMensaje(cbEnfermedadCorazoon,FarmaUtility.getValueFieldArrayList(vLista, 0,16));
            setValMensaje(cbConvulsiones,FarmaUtility.getValueFieldArrayList(vLista, 0,17));
            setValMensaje(cbAfeccionPulmonares,FarmaUtility.getValueFieldArrayList(vLista, 0,18));
            setValMensaje(cbProblemasRenales,FarmaUtility.getValueFieldArrayList(vLista, 0,19));
            setValMensaje(cbAzucarSangre,FarmaUtility.getValueFieldArrayList(vLista, 0,20));
            setValMensaje(cbCancer,FarmaUtility.getValueFieldArrayList(vLista, 0,21));
            setValMensaje(txtDetalleCancer,FarmaUtility.getValueFieldArrayList(vLista, 0,22));
            setValMensaje(cbRadioterapia,FarmaUtility.getValueFieldArrayList(vLista, 0,23));
            setValMensaje(cbQuimioterapia,FarmaUtility.getValueFieldArrayList(vLista, 0,24));
            setValMensaje(cbBranquiterapia,FarmaUtility.getValueFieldArrayList(vLista, 0,25));
            setValMensaje(txtOtroTratamiento,FarmaUtility.getValueFieldArrayList(vLista, 0,26));
            setValMensaje(txtFechaUltimoTratamiento,FarmaUtility.getValueFieldArrayList(vLista, 0,27));
            setValMensaje(cbOperado,FarmaUtility.getValueFieldArrayList(vLista, 0,28));
            setValMensaje(txtTipoOperacion,FarmaUtility.getValueFieldArrayList(vLista, 0,29));
            setValMensaje(txtFechaOperacion,FarmaUtility.getValueFieldArrayList(vLista, 0,30));
            setValMensaje(cbRecimiendoTratamiento,FarmaUtility.getValueFieldArrayList(vLista, 0,31));
            setValMensaje(cbIntervencionCerebral,FarmaUtility.getValueFieldArrayList(vLista, 0,32));
            setValMensaje(cbIntervencionOjos,FarmaUtility.getValueFieldArrayList(vLista, 0,33));
            setValMensaje(cbRM,FarmaUtility.getValueFieldArrayList(vLista, 0,34));
            setValMensaje(cbTC,FarmaUtility.getValueFieldArrayList(vLista, 0,35));
            setValMensaje(cbCretinina,FarmaUtility.getValueFieldArrayList(vLista, 0,36));
            setValMensaje(cbOtroExamen,FarmaUtility.getValueFieldArrayList(vLista, 0,37));
            setValMensaje(txtOtroExamen,FarmaUtility.getValueFieldArrayList(vLista, 0,38));
            setValMensaje(cbRealizoExamenAnteriormente,FarmaUtility.getValueFieldArrayList(vLista, 0,39));
            setValMensaje(cbAlergicoAlgo,FarmaUtility.getValueFieldArrayList(vLista, 0,40));
            setValMensaje(taAlergias,FarmaUtility.getValueFieldArrayList(vLista, 0,41));
            setValMensaje(txtResponsable,FarmaUtility.getValueFieldArrayList(vLista, 0,42));
            setValMensaje(txtDNI,FarmaUtility.getValueFieldArrayList(vLista, 0,43));
            setValMensaje(txtTelefono,FarmaUtility.getValueFieldArrayList(vLista, 0,44));
            setValMensaje(cbReaccionContraste,FarmaUtility.getValueFieldArrayList(vLista, 0,45));
            setValMensaje(taImpresionDiagnostica,FarmaUtility.getValueFieldArrayList(vLista, 0,46));

            FarmaUtility.showMessage(this, "Se muestran los datos que ya estan grabados hasta la fecha.", null);
        }
    }
    
    public void setValMensaje(Object jc,String pValor){
        
        if(jc instanceof JCheckBox){
            if(pValor.trim().equalsIgnoreCase("S")){
                ((JCheckBox)jc).setSelected(true);
            }
            else
                ((JCheckBox)jc).setSelected(false);    
        }
        else{
            if(jc instanceof JTextField){
                if(pValor.trim().equalsIgnoreCase("N")){
                    ((JTextField)jc).setText("");
                }
                else
                    ((JTextField)jc).setText(pValor);    
            }
            else{
                if(jc instanceof JTextArea){
                    if(pValor.trim().equalsIgnoreCase("N")){
                        ((JTextArea)jc).setText("");
                    }
                    else
                        ((JTextArea)jc).setText(pValor);    
                }
            }
        }
            
            
        
    }
    
    
}
