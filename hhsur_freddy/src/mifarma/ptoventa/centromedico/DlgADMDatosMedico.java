package mifarma.ptoventa.centromedico;

import common.FarmaDBUtility;
import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.SystemColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mifarma.ptoventa.centromedico.reference.DBAtencionMedica;

public class DlgADMDatosMedico extends JDialog {
    
    
    private JPanelWhite jPanelConte = new JPanelWhite();
    private JPanelHeader jPanelHeader = new JPanelHeader();
    private JLabel lblHeader = new JLabel();
    private JPanel jPanelBody = new JPanel();
    private JLabel lblTipDoc = new JLabel();
    private JLabel lblDocIden = new JLabel();
    private JLabel lblApeComp = new JLabel();
    private JLabel lblNomComp = new JLabel();
    private JLabel lblCamp = new JLabel();
    private JLabel lblCodMed = new JLabel();
    private JLabel lblDirec = new JLabel();
    private JLabel lblSexo = new JLabel();
    private JLabel lblFecNaci = new JLabel();
    private JTextField txtCmp = new JTextField();
    private JTextField txtCodMed = new JTextField();
    private JTextField txtDocIden = new JTextField();
    private JTextField txtApeComp = new JTextField();
    private JTextField txtNomComp = new JTextField();
    private JTextField txtDirec = new JTextField();
    private JComboBox cmbSexo = new JComboBox();
    private JComboBox cmbTipDoc = new JComboBox();
    private JTextField txtFecNaci = new JTextField();
    private JTextField txtEdad = new JTextField();
    private JButton jButton1 = new JButton();

    public DlgADMDatosMedico() {
        this(null, "", false);
    }

    public DlgADMDatosMedico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initCampos();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(704, 444));
        this.getContentPane().setLayout( null );
        this.setTitle("Ingrese Datos del Medico");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        jPanelConte.setBounds(new Rectangle(0, 0, 705, 420));
        jPanelHeader.setBounds(new Rectangle(20, 20, 655, 30));
        jPanelHeader.setBackground(new Color(0, 114, 169));
        lblHeader.setText("Ingreso de datos de Medico");
        lblHeader.setBounds(new Rectangle(20, 10, 185, 15));
        lblHeader.setForeground(SystemColor.window);
        jPanelBody.setBounds(new Rectangle(20, 60, 655, 330));
        jPanelBody.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanelBody.setLayout(null);
        jPanelBody.setBackground(SystemColor.window);
        lblTipDoc.setText("Tipo de Doc :*");
        lblTipDoc.setBounds(new Rectangle(360, 220, 80, 20));
        lblTipDoc.setForeground(Color.red);
        lblDocIden.setText("Documento de Identidad :*");
        lblDocIden.setBounds(new Rectangle(300, 195, 145, 20));
        lblDocIden.setForeground(Color.red);
        lblApeComp.setText("Apellidos Completos :*");
        lblApeComp.setBounds(new Rectangle(20, 60, 115, 20));
        lblApeComp.setForeground(Color.red);
        lblNomComp.setText("Nombres Completos :*");
        lblNomComp.setBounds(new Rectangle(305, 60, 125, 25));
        lblNomComp.setForeground(Color.red);
        lblCamp.setText("N\u00b0 CMP :*");
        lblCamp.setBounds(new Rectangle(20, 15, 75, 20));
        lblCamp.setForeground(Color.red);
        lblCodMed.setText("COD MEDICO :*");
        lblCodMed.setBounds(new Rectangle(220, 15, 95, 20));
        lblCodMed.setForeground(Color.red);
        lblDirec.setText("Direccion :*");
        lblDirec.setBounds(new Rectangle(20, 130, 95, 25));
        lblDirec.setForeground(Color.red);
        lblSexo.setText("Sexo :*");
        lblSexo.setBounds(new Rectangle(390, 130, 95, 20));
        lblSexo.setForeground(Color.red);
        lblFecNaci.setText("Fecha de Nacimiento :*");
        lblFecNaci.setBounds(new Rectangle(20, 190, 125, 30));
        lblFecNaci.setForeground(Color.red);
        txtCmp.setBounds(new Rectangle(80, 15, 125, 20));
        txtCodMed.setBounds(new Rectangle(315, 15, 165, 20));
        txtDocIden.setBounds(new Rectangle(440, 195, 130, 20));
        txtApeComp.setBounds(new Rectangle(20, 90, 255, 20));
        txtNomComp.setBounds(new Rectangle(300, 90, 255, 20));
        txtDirec.setBounds(new Rectangle(20, 155, 355, 20));
        cmbSexo.setBounds(new Rectangle(445, 130, 110, 20));
        cmbSexo.addItem("MASCULINO");
        cmbSexo.addItem("FEMENINO");
        
        cmbTipDoc.setBounds(new Rectangle(440, 220, 110, 20));
        txtFecNaci.setBounds(new Rectangle(150, 195, 130, 20));
        txtEdad.setBounds(new Rectangle(130, 220, 150, 15));
        txtEdad.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        txtEdad.setFont(new Font("Tahoma", 0, 10));

        txtEdad.setEditable(false);
        txtEdad.setEnabled(false);
        jButton1.setText("GRABAR ");
        jButton1.setBounds(new Rectangle(200, 290, 115, 30));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jPanelHeader.add(lblHeader, null);
        jPanelBody.add(jButton1, null);
        jPanelBody.add(txtEdad, null);
        jPanelBody.add(txtFecNaci, null);
        jPanelBody.add(cmbTipDoc, null);
        jPanelBody.add(cmbSexo, null);
        jPanelBody.add(txtDirec, null);
        jPanelBody.add(txtNomComp, null);
        jPanelBody.add(txtApeComp, null);
        jPanelBody.add(txtDocIden, null);
        jPanelBody.add(txtCodMed, null);
        jPanelBody.add(txtCmp, null);
        jPanelBody.add(lblFecNaci, null);
        jPanelBody.add(lblSexo, null);
        jPanelBody.add(lblDirec, null);
        jPanelBody.add(lblCodMed, null);
        jPanelBody.add(lblCamp, null);
        jPanelBody.add(lblNomComp, null);
        jPanelBody.add(lblApeComp, null);
        jPanelBody.add(lblDocIden, null);
        jPanelBody.add(lblTipDoc, null);
        jPanelConte.add(jPanelBody, null);
        jPanelConte.add(jPanelHeader, null);
        this.getContentPane().add(jPanelConte, null);
    }
    
    private void initCampos(){
          //  FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "cmbSexo",paciente.getSexo().trim());
   
        }
    
    public static void insertarDatosMedico(/*String nCmp, String codMed, String Nombres, String Apellidos ,String Direc, String sexo,String fecNaci, String docIden*/) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add("00000");
        parametros.add("9000099999");
        parametros.add("asdasd");
        parametros.add("asdasdsa");
        parametros.add("asdasdas");
        parametros.add("1");
        parametros.add("08/03/2000");
        parametros.add("74129984");

        FarmaDBUtility.executeSQLStoredProcedureStr("HHC_MEDICO.P_GRABA_NUEVO_MEDI(?,?,?,?,?,?,?,?,?,?)",parametros);
    }
 
    public String leerCmp(){
        String cmp;
        return cmp= txtCmp.getText();
    }
    public String leerCodMed(){
        String codMed;
        return codMed= txtCodMed.getText();
    }    
    public String leerNombres(){
        String nombres;
        return nombres= txtNomComp.getText();
    }
    public String leerApellidos(){
        String apellidos;
        return apellidos= txtApeComp.getText();
     
    }
    public String leerDirec(){
        String direc;
        return direc= txtDirec.getText();
     
    }
    public String leerSexo(){  
        String sexo;
         int combo= cmbSexo.getSelectedIndex();
         switch(combo){
        case 1: sexo="0"; //masculino
        default:sexo="1"; //femenino
         }return sexo;
    }
    public String leerFecNaci(){
        String FechaNaci;
        return FechaNaci= txtFecNaci.getText();
     
    }
    public String leerDocIden(){
        String cmp;
        return cmp= txtCmp.getText();
     
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCmp);  
      
    }


    private void jButton1_actionPerformed(ActionEvent e) {

        try {
            DBAtencionMedica.insertarDatosMedico(/*leerCmp(),leerCodMed(),leerNombres(),leerApellidos(),leerDirec(),leerSexo(),leerFecNaci(),leerDocIden()*/);
            
            FarmaUtility.aceptarTransaccion();
            dispose();
        } catch (SQLException f) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Seleccione un consultorio", null);
        }
        
          // 
           
     
    }
}
