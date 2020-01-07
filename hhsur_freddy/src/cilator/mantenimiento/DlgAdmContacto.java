package cilator.mantenimiento;


import cilator.mantenimiento.reference.DBMantenimiento;

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

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import common.FarmaConstants;
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


public class DlgAdmContacto extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgAdmContacto.class);

    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblFiltra = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();

    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JTextField txtNombrePaciente = new JTextField();
    private JTextField txtApePat = new JTextField();
    private JTextField txtApeMat = new JTextField();
    private JTextField txtNacimiento = new JTextField();
    private JComboBox cmbSexo = new JComboBox();
    private JTextField txtDireccion = new JTextField();
    private JTextField txtCargo = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JTextField txtCelular_uno = new JTextField();
    private JTextField txtCelular_dos = new JTextField();

    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";

    public DlgAdmContacto() {
       this(null, "", false,"I","");
    }

    public DlgAdmContacto(Frame parent, String title, boolean modal, String pAccion,String pIdUpdate) {
        super(parent, title, modal);
        myParentFrame = parent;
        //String pIdUpdate = "";
        
        if (pAccion.trim().equalsIgnoreCase("I"))
            pInsert = true;
        else if (pAccion.trim().equalsIgnoreCase("U")){
            this.pId = pIdUpdate;
            pUpdate = true;
        }

        
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(970, 265));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Datos de Contacto");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 935, 190));
        txtDni.setBounds(new Rectangle(95, 10, 110, 20));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
                // txtClienteJuridico_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNombreMedico_keyReleased(e);
                //txtClienteJuridico_keyReleased(e);
            }
        });
        btnNombre.setText("DNI:");
        btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });

        lblFiltra.setBounds(new Rectangle(690, 210, 110, 20));
        lblFiltra.setText("[ F11 ]Grabar");


        jLabel1.setText("Nombre :");
        jLabel1.setBounds(new Rectangle(20, 45, 70, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtNombrePaciente.setBounds(new Rectangle(95, 40, 295, 20));
        txtNombrePaciente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNombre_keyPressed(e);
            }
        });
        txtApePat.setBounds(new Rectangle(95, 70, 295, 20));
        txtApePat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApePat_keyPressed(e);
            }
        });
        txtApeMat.setBounds(new Rectangle(95, 100, 295, 20));
        txtApeMat.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtApeMat_keyPressed(e);
            }
        });
        jLabel2.setText("Apellido Pat.");
        jLabel2.setBounds(new Rectangle(5, 75, 80, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel3.setText("Apellido Mat.");
        jLabel3.setBounds(new Rectangle(5, 105, 80, 15));
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel4.setText("Nacimiento ");
        jLabel4.setBounds(new Rectangle(5, 125, 80, 25));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(SystemColor.window);
        txtNacimiento.setBounds(new Rectangle(95, 130, 205, 20));
        txtNacimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNacimiento_actionPerformed(e);
            }
        });
        txtNacimiento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNacimiento_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNacimiento_keyReleased(e);
            }
        });
        jLabel5.setText("Sexo");
        jLabel5.setBounds(new Rectangle(20, 160, 35, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(SystemColor.window);
        cmbSexo.setBounds(new Rectangle(95, 160, 240, 20));
        cmbSexo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbSexo_keyPressed(e);
            }
        });
        jLabel6.setText("dd/mm/yyyy");
        jLabel6.setBounds(new Rectangle(310, 130, 90, 20));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(SystemColor.window);
        jLabel7.setText("Direcci\u00f3n");
        jLabel7.setBounds(new Rectangle(250, 10, 75, 20));
        jLabel7.setForeground(SystemColor.window);
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        txtDireccion.setBounds(new Rectangle(325, 10, 600, 20));
        jLabel8.setText("Cargo:");
        jLabel8.setBounds(new Rectangle(410, 45, 75, 15));
        jLabel8.setForeground(SystemColor.window);
        jLabel8.setFont(new Font("SansSerif", 1, 11));
        txtCargo.setBounds(new Rectangle(505, 40, 260, 20));
        txtEmail.setBounds(new Rectangle(505, 75, 260, 20));
        txtTelefono.setBounds(new Rectangle(505, 115, 195, 20));
        txtCelular_uno.setBounds(new Rectangle(505, 150, 195, 20));
        txtCelular_dos.setBounds(new Rectangle(730, 150, 180, 20));
        jLabel9.setText("Email:");
        jLabel9.setBounds(new Rectangle(415, 80, 75, 15));
        jLabel9.setForeground(SystemColor.window);
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel10.setText("Telefono:");
        jLabel10.setBounds(new Rectangle(415, 120, 80, 15));
        jLabel10.setForeground(SystemColor.window);
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        jLabel11.setText("Celulares :");
        jLabel11.setBounds(new Rectangle(415, 155, 75, 15));
        jLabel11.setForeground(SystemColor.window);
        jLabel11.setFont(new Font("SansSerif", 1, 11));
        jLabelFunction2.setBounds(new Rectangle(830, 210, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(jLabel11, null);
        pnlCliente.add(jLabel10, null);
        pnlCliente.add(jLabel9, null);
        pnlCliente.add(txtCelular_dos, null);
        pnlCliente.add(txtCelular_uno, null);
        pnlCliente.add(txtTelefono, null);
        pnlCliente.add(txtEmail, null);
        pnlCliente.add(txtCargo, null);
        pnlCliente.add(jLabel8, null);
        pnlCliente.add(txtDireccion, null);
        pnlCliente.add(jLabel7, null);
        pnlCliente.add(jLabel6, null);
        pnlCliente.add(cmbSexo, null);
        pnlCliente.add(jLabel5, null);
        pnlCliente.add(txtNacimiento, null);
        pnlCliente.add(jLabel4, null);
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtApeMat, null);
        pnlCliente.add(txtApePat, null);
        pnlCliente.add(txtNombrePaciente, null);
        pnlCliente.add(jLabel1, null);
        pnlCliente.add(txtDni, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(lblFiltra, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaLoadCVL.loadCVLFromSP(cmbSexo, "SEXO_CONTACTO", "PTOVENTA_PACIENTE.GET_SEXO", new ArrayList(), false);
        FarmaVariables.vAceptar = false;
        limpiar();
        cargaOpcion();
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            grabar();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        }
        chkKeyPressed(e);

    }


    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApePat);
        }
        chkKeyPressed(e);
    }

    private void txtApePat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApeMat);
        }
        chkKeyPressed(e);
    }

    private void txtApeMat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNacimiento);
        }
        chkKeyPressed(e);
    }

    private void grabar() {
        if (validarDatosIngresados()) {
            String pDNI = txtDni.getText().trim();
            String pNombre = txtNombrePaciente.getText().trim();
            String pPaterno = txtApePat.getText().trim();
            String pMaterno = txtApeMat.getText().trim();
            String pNacimiento = txtNacimiento.getText().trim();
            String pDireccion  = txtDireccion.getText().trim();
            String pSexo = FarmaLoadCVL.getCVLCode("SEXO_CONTACTO", cmbSexo.getSelectedIndex()).toString().trim();
            String pCargo = txtCargo.getText().trim();
            String pEmail = txtEmail.getText().trim();
            String pTelefono = txtTelefono.getText().trim();
            String pCelular_Uno = txtCelular_uno.getText().trim();
            String pCelular_Dos = txtCelular_dos.getText().trim();
            
            try {
                DBMantenimiento.grabaContacto(pDNI, pNombre, pPaterno, pMaterno, pNacimiento,pDireccion, pSexo,
                                       pCargo,pEmail,pTelefono,pCelular_Uno,pCelular_Dos,pInsert,pUpdate);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se grabó con exitosamente", null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                log.info(sqle.getMessage());
                FarmaUtility.showMessage(this, "Ocurrió un error al hacer el ejecutar el proceso.\n" +
                        sqle.getMessage(), null);
            }

        }

    }

    private boolean validarDatosIngresados() {
        String pCMP = txtDni.getText().trim();
        String pNombre = txtNombrePaciente.getText().trim();
        String pPaterno = txtApePat.getText().trim();
        String pMaterno = txtApeMat.getText().trim();
        String pNacimiento = txtNacimiento.getText().trim();
        String pDireccion  = txtDireccion.getText().trim();
        String pSexo = FarmaLoadCVL.getCVLCode("SEXO_CONTACTO", cmbSexo.getSelectedIndex()).toString().trim();
        String pCargo = txtCargo.getText().trim();
        String pEmail = txtEmail.getText().trim();
        String pTelefono = txtTelefono.getText().trim();
        String pCelular_Uno = txtCelular_uno.getText().trim();
        String pCelular_Dos = txtCelular_dos.getText().trim();
        
        /*if (pCMP.length() < 8 || pCMP.length() > 19 || pCMP.length() == 0) {
            FarmaUtility.showMessage(this, "Debe de ingresar correctamente el DNI", txtDni);
            return false;
        } else {
            if (pNombre.length() >= 1000 || pNombre.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el NOMBRE", txtNombrePaciente);
                return false;
            } else if (pPaterno.length() >= 1000 || pPaterno.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Paterno", txtApePat);
                return false;
            } else if (pMaterno.length() >= 1000 || pMaterno.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Materno", txtApeMat);
                return false;
            } else if (pNacimiento.length() >= 1000 || pMaterno.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente la fecha de nacimiento (dd/mm/yyyy)",
                                         txtApeMat);
                return false;
            } else if (!(pSexo.trim().equalsIgnoreCase("M") || pSexo.trim().equalsIgnoreCase("F"))) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el sexo del paciente.", txtApeMat);
                return false;
            }

        }*/

        // TODO ES VALIDO
        return true;
    }


    private void txtNacimiento_actionPerformed(ActionEvent e) {
    }


    private void txtNacimiento_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbSexo);
        }
        chkKeyPressed(e);
    }

    private void cmbSexo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDni);
        }
        chkKeyPressed(e);
    }

    private void txtNombreMedico_keyReleased(KeyEvent e) {
    }

    private void txtNacimiento_keyReleased(KeyEvent e) {
        dateCompleteNacimiento(txtNacimiento, e);
    }

    public static void dateCompleteNacimiento(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = "19";
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                if ((pJTextField.getText().trim().length() == 2) || (pJTextField.getText().trim().length() == 5)) {
                    pJTextField.setText(pJTextField.getText().trim() + "/");
                    if (pJTextField.getText().trim().length() == 6)
                        pJTextField.setText(pJTextField.getText().trim() + anhoBD);
                }
            }
        } catch (Exception errAnhoBD) {
            errAnhoBD.printStackTrace();
        }
    }
    
    public void limpiar(){
        txtDni.setText("");
        txtNombrePaciente.setText("");
        txtApePat.setText("");
        txtApeMat.setText("");
        txtNacimiento.setText("");
        txtDireccion.setText("");
        txtCargo.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtCelular_uno.setText("");
        txtCelular_dos.setText("");
    }

/*
 
    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";

 * */
    private void cargaOpcion() {
        if(pUpdate){
            loadData(pId);
        }
        else{
            if(pInsert){
                limpiar();
            }
        }
    }

    private void loadData(String pIdValor){
        try {
            ArrayList vDatos = new ArrayList();
            DBMantenimiento.getContactoId(vDatos, pIdValor);
            if(vDatos.size()==1){
                // se puede mostrar los datos
                limpiar();
                txtDni.setText(((ArrayList)(vDatos.get(0))).get(0).toString());
                txtNombrePaciente.setText(((ArrayList)(vDatos.get(0))).get(1).toString());
                txtApePat.setText(((ArrayList)(vDatos.get(0))).get(2).toString());
                txtApeMat.setText(((ArrayList)(vDatos.get(0))).get(3).toString());
                txtNacimiento.setText(((ArrayList)(vDatos.get(0))).get(4).toString());
                FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "SEXO_CONTACTO", ((ArrayList)(vDatos.get(0))).get(5).toString());
                txtDireccion.setText(((ArrayList)(vDatos.get(0))).get(6).toString());
                txtCargo.setText(((ArrayList)(vDatos.get(0))).get(7).toString());
                txtEmail.setText(((ArrayList)(vDatos.get(0))).get(8).toString());
                txtTelefono.setText(((ArrayList)(vDatos.get(0))).get(9).toString());
                txtCelular_uno.setText(((ArrayList)(vDatos.get(0))).get(10).toString());
                txtCelular_dos.setText(((ArrayList)(vDatos.get(0))).get(11).toString());
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
}
