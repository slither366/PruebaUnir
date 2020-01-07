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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;

import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAdmTecnologos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgAdmTecnologos.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextFieldSanSerif txtCMP = new JTextFieldSanSerif();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblFiltra = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtNombre = new JTextField();
    private JTextField txtApePat = new JTextField();
    private JTextField txtApeMat = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();

    public DlgAdmTecnologos() {
        this(null, "", false);
    }

    public DlgAdmTecnologos(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(435, 206));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Mantenimiento T\u00e9cnico");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 415, 135));
        txtCMP.setBounds(new Rectangle(95, 10, 110, 20));
        txtCMP.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtNombreMedico_keyPressed(e);
                    // txtClienteJuridico_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtNombreMedico_keyReleased(e);
                //txtClienteJuridico_keyReleased(e);
            }
        });
        txtCMP.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtNombreMedico_focusLost(e);
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

        lblFiltra.setBounds(new Rectangle(185, 155, 110, 20));
        lblFiltra.setText("[ F11 ]Grabar");


        jLabel1.setText("Nombre :");
        jLabel1.setBounds(new Rectangle(20, 45, 70, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtNombre.setBounds(new Rectangle(95, 40, 295, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
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
        jLabelFunction2.setBounds(new Rectangle(310, 155, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtApeMat, null);
        pnlCliente.add(txtApePat, null);
        pnlCliente.add(txtNombre, null);
        pnlCliente.add(jLabel1, null);
        pnlCliente.add(txtCMP, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(lblFiltra,null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCMP);
    }
    private void txtNombreMedico_keyReleased(KeyEvent e) {
        
    }
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            grabarMedico();
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtNombreMedico_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }        
        chkKeyPressed(e);
        
    }

    private void txtNombreMedico_focusLost(FocusEvent e) {
        
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
            FarmaUtility.moveFocus(txtCMP);
        }        
        chkKeyPressed(e);         
    }

    private void grabarMedico() {
        if(validarDatosIngresados()){
            //AHORA GRABA LOS DATOS DEL MEDICO
            String pCMP = txtCMP.getText().trim();
            String pNombre = txtNombre.getText().trim();
            String pPaterno = txtApePat.getText().trim();
            String pMaterno = txtApeMat.getText().trim();

            try {
                DBMedico.grabaTecnologoPedido(pCMP, pNombre, pPaterno, pMaterno);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this,"Se grabó con exito el médico",null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                log.info(sqle.getMessage());
            }
            
        }
                                        
    }
    
    private boolean validarDatosIngresados(){
        String pCMP = txtCMP.getText().trim();
        String pNombre = txtNombre.getText().trim();
        String pPaterno = txtApePat.getText().trim();
        String pMaterno = txtApeMat.getText().trim();
        
        if(pCMP.length()>=11||pCMP.length()==0){
            FarmaUtility.showMessage(this, "Debe de ingresar correctamente el CMP",txtCMP);
            return false;
        }
        else{
            if(pNombre.length()>=1000||pNombre.length()==0){
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el NOMBRE",txtNombre);
                return false;
            }
            else
            if(pPaterno.length()>=1000||pPaterno.length()==0){
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Paterno",txtApePat);
                return false;
            }   
            else{
                if(pMaterno.length()>=1000||pMaterno.length()==0){
                    FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Materno",txtApeMat);
                    return false;
                }            
            }
        }
        
        // TODO ES VALIDO
        return true;
    }
        
    
}
