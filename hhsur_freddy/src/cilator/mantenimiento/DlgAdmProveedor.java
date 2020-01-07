package cilator.mantenimiento;


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


public class DlgAdmProveedor extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgAdmProveedor.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblFiltra = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtNombrePaciente = new JTextField();
    private JTextField txtApePat = new JTextField();
    private JTextField txtApeMat = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JComboBox cmbSexo = new JComboBox();
    private JLabel jLabel7 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JLabel jLabel8 = new JLabel();
    private JTextField jTextField3 = new JTextField();
    private JTextField jTextField4 = new JTextField();
    private JTextField jTextField5 = new JTextField();
    private JTextField jTextField6 = new JTextField();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JComboBox jComboBox1 = new JComboBox();

    public DlgAdmProveedor() {
        this(null, "", false);
    }

    public DlgAdmProveedor(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(877, 305));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Datos de Proveedor");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 850, 230));
        txtDni.setBounds(new Rectangle(60, 10, 170, 20));
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
        btnNombre.setText("RUC:");
        btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });

        lblFiltra.setBounds(new Rectangle(605, 250, 110, 20));
        lblFiltra.setText("[ F11 ]Grabar");


        jLabel1.setText("Nombre :");
        jLabel1.setBounds(new Rectangle(255, 15, 70, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtNombrePaciente.setBounds(new Rectangle(330, 10, 505, 20));
        txtNombrePaciente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombre_keyPressed(e);
                }
            });
        txtApePat.setBounds(new Rectangle(95, 115, 295, 20));
        txtApePat.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApePat_keyPressed(e);
                }
            });
        txtApeMat.setBounds(new Rectangle(495, 115, 295, 20));
        txtApeMat.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApeMat_keyPressed(e);
                }
            });
        jLabel2.setText("Categor\u00eda:");
        jLabel2.setBounds(new Rectangle(5, 120, 80, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel3.setText("Descripci\u00f3n:");
        jLabel3.setBounds(new Rectangle(405, 120, 80, 15));
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel5.setText("Moneda de Pedido :");
        jLabel5.setBounds(new Rectangle(305, 75, 125, 20));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(SystemColor.window);
        cmbSexo.setBounds(new Rectangle(430, 75, 140, 20));
        cmbSexo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbSexo_keyPressed(e);
                }
            });
        jLabel7.setText("Direcci\u00f3n");
        jLabel7.setBounds(new Rectangle(20, 45, 75, 20));
        jLabel7.setForeground(SystemColor.window);
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jTextField1.setBounds(new Rectangle(95, 45, 740, 20));
        jLabel8.setText("Estado :");
        jLabel8.setBounds(new Rectangle(585, 75, 75, 15));
        jLabel8.setForeground(SystemColor.window);
        jLabel8.setFont(new Font("SansSerif", 1, 11));
        jTextField3.setBounds(new Rectangle(95, 150, 260, 20));
        jTextField4.setBounds(new Rectangle(95, 75, 195, 20));
        jTextField5.setBounds(new Rectangle(95, 185, 195, 20));
        jTextField6.setBounds(new Rectangle(320, 185, 180, 20));
        jLabel9.setText("Email:");
        jLabel9.setBounds(new Rectangle(5, 155, 75, 15));
        jLabel9.setForeground(SystemColor.window);
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel10.setText("Telefono:");
        jLabel10.setBounds(new Rectangle(5, 80, 80, 15));
        jLabel10.setForeground(SystemColor.window);
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        jLabel11.setText("Celulares :");
        jLabel11.setBounds(new Rectangle(5, 190, 75, 15));
        jLabel11.setForeground(SystemColor.window);
        jLabel11.setFont(new Font("SansSerif", 1, 11));
        jComboBox1.setBounds(new Rectangle(640, 75, 140, 20));
        jLabelFunction2.setBounds(new Rectangle(745, 250, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(jComboBox1, null);
        pnlCliente.add(jLabel11, null);
        pnlCliente.add(jLabel10, null);
        pnlCliente.add(jLabel9, null);
        pnlCliente.add(jTextField6, null);
        pnlCliente.add(jTextField5, null);
        pnlCliente.add(jTextField4, null);
        pnlCliente.add(jTextField3, null);
        pnlCliente.add(jLabel8, null);
        pnlCliente.add(jTextField1, null);
        pnlCliente.add(jLabel7, null);
        pnlCliente.add(cmbSexo, null);
        pnlCliente.add(jLabel5, null);
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtApeMat, null);
        pnlCliente.add(txtApePat, null);
        pnlCliente.add(txtNombrePaciente, null);
        pnlCliente.add(jLabel1, null);
        pnlCliente.add(txtDni, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(lblFiltra,null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaLoadCVL.loadCVLFromSP(cmbSexo, "cmbSEXO","PTOVENTA_PACIENTE.GET_SEXO", new ArrayList(), false);
        FarmaVariables.vAceptar = false;
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            grabarPaciente();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(buscaDNI(txtDni.getText().trim())){
                txtNombrePaciente.setEnabled(false);
                txtApeMat.setEnabled(false);
                txtApePat.setEnabled(false);
                //txtNacimiento.setEnabled(false);
                cmbSexo.setEnabled(false);
            }
            else
            {
                txtNombrePaciente.setText("");
                txtApeMat.setText("");
                txtApePat.setText("");
                //txtNacimiento.setText("");
                txtNombrePaciente.setEnabled(true);
                txtApeMat.setEnabled(true);
                txtApePat.setEnabled(true);
                cmbSexo.setEnabled(true);
                FarmaUtility.moveFocus(txtNombrePaciente);
            }    
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
        chkKeyPressed(e);         
    }

    private void grabarPaciente() {
        if(validarDatosIngresados()){
            //AHORA GRABA LOS DATOS DEL MEDICO
            String pCMP = txtDni.getText().trim();
            String pNombre = txtNombrePaciente.getText().trim();
            String pPaterno = txtApePat.getText().trim();
            String pMaterno = txtApeMat.getText().trim();
            String pNacimiento = "";//txtNacimiento.getText().trim();
            String pSexo = FarmaLoadCVL.getCVLCode("cmbSEXO", cmbSexo.getSelectedIndex()).toString().trim();
            
            try {
                DBMedico.grabaPaciente(pCMP, pNombre, pPaterno, pMaterno,pNacimiento,pSexo);
                FarmaUtility.aceptarTransaccion();
                VariablesModuloVentas.VPacienteDni = pCMP;
                VariablesModuloVentas.VPacienteNombre = pNombre;
                VariablesModuloVentas.VPacienteAPellidoPat = pPaterno;
                VariablesModuloVentas.VPacienteAPellidoMat = pMaterno;
                VariablesModuloVentas.VPacienteNacimiento = pNacimiento;
                VariablesModuloVentas.VPacienteSexoID= pSexo;
                if(pSexo.trim().equalsIgnoreCase("M"))
                    VariablesModuloVentas.VPacienteSexo= "MASCULINO";
                else
                    VariablesModuloVentas.VPacienteSexo= "FEMENINO";
                FarmaUtility.showMessage(this,"Se grabó con exito el paciente ",null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                log.info(sqle.getMessage());
                FarmaUtility.showMessage(this,"Ocurrió un error al registrar al paciente\n"+sqle.getMessage(),null);
            }
            
        }
                                        
    }
    
    private boolean validarDatosIngresados(){
        String pCMP = txtDni.getText().trim();
        String pNombre = txtNombrePaciente.getText().trim();
        String pPaterno = txtApePat.getText().trim();
        String pMaterno = txtApeMat.getText().trim();
        String pNacimiento = "";//txtNacimiento.getText().trim();
        String pSexo = FarmaLoadCVL.getCVLCode("cmbSEXO", cmbSexo.getSelectedIndex()).toString().trim();
        
        if(pCMP.length()<8||pCMP.length()>19||pCMP.length()==0){
            FarmaUtility.showMessage(this, "Debe de ingresar correctamente el DNI",txtDni);
            return false;
        }
        else{
            if(pNombre.length()>=1000||pNombre.length()==0){
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el NOMBRE",txtNombrePaciente);
                return false;
            }
            else
            if(pPaterno.length()>=1000||pPaterno.length()==0){
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Paterno",txtApePat);
                return false;
            }   
            else
                if(pMaterno.length()>=1000||pMaterno.length()==0){
                    FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Materno",txtApeMat);
                    return false;
                }
               else
                if(pNacimiento.length()>=1000||pMaterno.length()==0){
                    FarmaUtility.showMessage(this, "Debe de ingresar correctamente la fecha de nacimiento (dd/mm/yyyy)",txtApeMat);
                    return false;
                }            
            else
            if(!(pSexo.trim().equalsIgnoreCase("M")||pSexo.trim().equalsIgnoreCase("F"))){
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el sexo del paciente.",txtApeMat);
                return false;
            }            
            
        }
        
        // TODO ES VALIDO
        return true;
    }


    private void txtNacimiento_actionPerformed(ActionEvent e) {
    }

    private boolean buscaDNI(String vDNI) {
        boolean vResultado = false;
        ArrayList vLista =  new ArrayList();
        try {
            DBMedico.getPacienteDNI(vLista, vDNI);
            if(vLista.size()>0){
                String pDNI = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                String pNombre = FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                String pApePat = FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                String pApeMat = FarmaUtility.getValueFieldArrayList(vLista, 0, 3);
                String pFecha = FarmaUtility.getValueFieldArrayList(vLista, 0, 4);                
                String pSexo = FarmaUtility.getValueFieldArrayList(vLista, 0, 5);
                txtNombrePaciente.setText(pNombre);
                txtApeMat.setText(pApeMat);
                txtApePat.setText(pApePat);
               // txtNacimiento.setText(pFecha);
                FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo,"cmbSEXO",pSexo);
                vResultado = true;
            }
            else
               vResultado = false;
        } catch (Exception sqle) {
            vResultado = false;
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        return vResultado;
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
    
    public static void dateCompleteNacimiento(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = "19";
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
        //dateCompleteNacimiento(txtNacimiento,e);
    }
}
