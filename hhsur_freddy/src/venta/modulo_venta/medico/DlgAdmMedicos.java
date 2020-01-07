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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.util.ArrayList;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;

import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.administracion.impresoras.reference.VariablesImpresoras;


public class DlgAdmMedicos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgAdmMedicos.class);

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
    private JLabel jLabel4 = new JLabel();
    private JComboBox cmbReferencia = new JComboBox();
    private JLabel lblReferencia = new JLabel();

    private String pCMP="";
    private String pNomMedico = "";
    private String pApePat = "";
    private String pApeMat = "";
    private String pIdRef = "";
    private String pDesRef = "";
    private String pCodVisitador="";
    private String pNombreVisitador="";
    private boolean vModificar = false;
    private JButton jButton1 = new JButton();
    private JLabel jLabel5 = new JLabel();
    private JLabel lblCodVisitador = new JLabel();
    private JLabel lblNombreVisitador = new JLabel();

    public DlgAdmMedicos() {
        this(null, "", false,"","","","","","","","");
    }

    public DlgAdmMedicos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }
    
    public DlgAdmMedicos(Frame parent, String title, boolean modal,
                                                                    String pCMP ,
                                                                    String pNomMedico ,
                                                                    String pApePat,
                                                                    String pApeMat,
                                                                    String pDesRef,
                                                                    String pIdRef ,
                                                                    String pCodVisitador,
                                                                    String pNombreVisitador 
                         ) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.pCMP = pCMP;
        this.pNomMedico = pNomMedico;
        this.pApePat = pApePat;        
        this.pApeMat = pApeMat;                
        this.pIdRef = pIdRef;
        this.pDesRef = pDesRef;
        this.pCodVisitador= pCodVisitador;
        this.pNombreVisitador=pNombreVisitador;
        vModificar = true;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(440, 250));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Mantenimiento M\u00e9dico");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 415, 165));
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
        btnNombre.setText("CMP:");
        btnNombre.setBounds(new Rectangle(20, 10, 65, 20));
        btnNombre.setMnemonic('n');
        btnNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // btnClienteJuridico_actionPerformed(e);
            }
        });

        lblFiltra.setBounds(new Rectangle(180, 185, 110, 20));
        lblFiltra.setText("[ F11 ]Grabar");


        lblFiltra.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblFiltra_mouseClicked(e);
                }
            });
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
        jLabel4.setText("Referencia");
        jLabel4.setBounds(new Rectangle(5, 130, 75, 15));
        jLabel4.setBackground(SystemColor.window);
        jLabel4.setForeground(SystemColor.window);
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        cmbReferencia.setBounds(new Rectangle(95, 125, 290, 30));
        cmbReferencia.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbReferencia_itemStateChanged(e);
            }
        });
        cmbReferencia.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbReferencia_keyPressed(e);
                }
            });
        lblReferencia.setText("Otros :  ");
        lblReferencia.setBounds(new Rectangle(540, 170, 385, 25));
        lblReferencia.setFont(new Font("SansSerif", 1, 11));
        lblReferencia.setForeground(SystemColor.window);
        jButton1.setText("Seleccione al Visitador");
        jButton1.setBounds(new Rectangle(595, 200, 320, 25));
        jButton1.setFont(new Font("SansSerif", 0, 11));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jButton1_keyPressed(e);
                }
            });
        jLabel5.setText("Visitador :");
        jLabel5.setBounds(new Rectangle(535, 200, 65, 15));
        jLabel5.setForeground(SystemColor.window);
        lblCodVisitador.setBounds(new Rectangle(10, 225, 55, 25));
        lblCodVisitador.setFont(new Font("SansSerif", 1, 11));
        lblCodVisitador.setForeground(SystemColor.window);
        lblNombreVisitador.setBounds(new Rectangle(70, 220, 325, 35));
        lblNombreVisitador.setFont(new Font("SansSerif", 1, 11));
        lblNombreVisitador.setForeground(SystemColor.window);
        jLabelFunction2.setBounds(new Rectangle(305, 185, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(lblNombreVisitador, null);
        pnlCliente.add(lblCodVisitador, null);
        pnlCliente.add(jLabel5, null);
        pnlCliente.add(jButton1, null);
        pnlCliente.add(lblReferencia, null);
        pnlCliente.add(cmbReferencia, null);
        pnlCliente.add(jLabel4, null);
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtApeMat, null);
        pnlCliente.add(txtApePat, null);
        pnlCliente.add(txtNombre, null);
        pnlCliente.add(jLabel1, null);
        pnlCliente.add(txtCMP, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(lblFiltra, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        cargaCombo();
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCMP);
        lblReferencia.setVisible(false);
        if(vModificar){
/*
            private String pCMP="";
            private String pNomMedico = "";
            private String pIdRef = "";
            private String pDesRef = "";
            private boolean vModificar = false;
            */
            txtCMP.setText(pCMP);
            txtNombre.setText(pNomMedico);
            txtApePat.setText(pApePat);
            txtApeMat.setText(pApeMat);
            lblCodVisitador.setText(pCodVisitador);
            lblNombreVisitador.setText(pNombreVisitador);
            FarmaLoadCVL.setSelectedValueInComboBox(cmbReferencia, "cmbReferencia",pIdRef);
            
        }
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
            FarmaUtility.moveFocus(cmbReferencia);
        }
        chkKeyPressed(e);
    }

    private void grabarMedico() {
        if (validarDatosIngresados()) {
            //AHORA GRABA LOS DATOS DEL MEDICO
            String pCMP = txtCMP.getText().trim();
            String pNombre = txtNombre.getText().trim();
            String pPaterno = txtApePat.getText().trim();
            String pMaterno = txtApeMat.getText().trim();
            String pIDReferencia =
                FarmaLoadCVL.getCVLCode("cmbReferencia", cmbReferencia.getSelectedIndex()).toString().trim();
            String pDescReferencia = FarmaLoadCVL.getCVLDescription("cmbReferencia", pIDReferencia).toString().trim();
            
            String pCodVisitador = lblCodVisitador.getText().trim();
            String pNombreVisitador = lblNombreVisitador.getText().trim();
            
            if (pIDReferencia.trim().equalsIgnoreCase("10008")){
                pDescReferencia = lblReferencia.getText().trim();
            }

            try {
                DBMedico.grabaMedicoPedido(pCMP, pNombre, pPaterno, pMaterno, pIDReferencia, pDescReferencia,
                pCodVisitador,pNombreVisitador
                                           );
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se grabó con exito el médico", null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                log.info(sqle.getMessage());
            }

        }

    }

    private boolean validarDatosIngresados() {
        String pCMP = txtCMP.getText().trim();
        String pNombre = txtNombre.getText().trim();
        String pPaterno = txtApePat.getText().trim();
        String pMaterno = txtApeMat.getText().trim();

        if (pCMP.length() >= 11 || pCMP.length() == 0) {
            FarmaUtility.showMessage(this, "Debe de ingresar correctamente el CMP", txtCMP);
            return false;
        } else {
            if (pNombre.length() >= 1000 || pNombre.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el NOMBRE", txtNombre);
                return false;
            } else if (pPaterno.length() >= 1000 || pPaterno.length() == 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Paterno", txtApePat);
                return false;
            } else {
                if (pMaterno.length() >= 1000 || pMaterno.length() == 0) {
                    FarmaUtility.showMessage(this, "Debe de ingresar correctamente el Apellido Materno", txtApeMat);
                    return false;
                }
            }
        }

        // TODO ES VALIDO
        return true;
    }


    private void cargaCombo() {
        ArrayList parametros2 = new ArrayList();

        FarmaLoadCVL.loadCVLFromSP(cmbReferencia, "cmbReferencia", "PTOVENTA_MEDICO.get_lista_referencia", parametros2,
                                   false, true);
    }

    private void cmbReferencia_itemStateChanged(ItemEvent e) {
    }

    private void cmbReferencia_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            lblReferencia.setVisible(false);
            String pIDReferencia =
                FarmaLoadCVL.getCVLCode("cmbReferencia", cmbReferencia.getSelectedIndex()).toString().trim();
            if (pIDReferencia.trim().equalsIgnoreCase("10008")){
                String pCad = FarmaUtility.ShowInput(this, "Ingrese la referencia");
                if(pCad.trim().length()==0){
                    FarmaUtility.showMessage(this, "Por favor ingresar la referencia o seleccione una de la lista.", cmbReferencia);
                }
                else{
                    //es mayor q CERO
                    lblReferencia.setText(pCad.toUpperCase().trim());
                    lblReferencia.setVisible(true);
                }
            }        
            
            FarmaUtility.moveFocus(txtCMP);
        }
        else
            chkKeyPressed(e);
        

    }

    private void jButton1_actionPerformed(ActionEvent e) {
        DlgListaVisitadores dlgIngMedico = new DlgListaVisitadores(myParentFrame, "", true,true);
        dlgIngMedico.setVisible(true);
        if(FarmaVariables.vAceptar){
            lblCodVisitador.setText(VariablesModuloVentas.VCOD_VISITADOR_IN);
            lblNombreVisitador.setText(VariablesModuloVentas.VNOMBRE_VISITADOR_IN);
        }
        else{
           VariablesModuloVentas.VCOD_VISITADOR_IN = "";
            VariablesModuloVentas.VNOMBRE_VISITADOR_IN = "";
            lblCodVisitador.setText("");
            lblNombreVisitador.setText("");
        }
    }

    private void lblFiltra_mouseClicked(MouseEvent e) {
        grabarMedico();
        cerrarVentana(true);
    }

    private void jButton1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
