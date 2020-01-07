package mifarma.ptoventa.centromedico;

import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JPasswordField;

import common.FarmaLoadCVL;
import common.FarmaUtility;

import common.FarmaVariables;

import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.FacadeAtencioMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgLoginMedico extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgLoginMedico.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanelWhite pnlContenedor = new JPanelWhite();
    private JLabelOrange lblNroCMP = new JLabelOrange();
    private JLabelOrange lblClaveDNI = new JLabelOrange();
    private JTextFieldSanSerif txtNroCMP = new JTextFieldSanSerif();
    private JPasswordField txtNroDNI = new JPasswordField();
    private JButtonFunction btnSalir = new JButtonFunction();
    private JButtonFunction btnAceptar = new JButtonFunction();
    
    private String codMedico = "";
    private boolean mostrarMensaje = false;
    
    private String pNombreMedico = "";

    public DlgLoginMedico() {
        this(null, "", false);
    }
    

    public DlgLoginMedico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(276, 158));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Acceso a Consulta M�dica");
        pnlContenedor.add(btnAceptar, null);
        pnlContenedor.add(btnSalir, null);
        pnlContenedor.add(txtNroDNI, null);
        pnlContenedor.add(txtNroCMP, null);
        pnlContenedor.add(lblClaveDNI, null);
        pnlContenedor.add(lblNroCMP, null);
        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblNroCMP.setText("Nro Colegio");
        lblNroCMP.setBounds(new Rectangle(20, 20, 80, 20));
        lblNroCMP.setFont(new Font("SansSerif", 1, 12));
        lblClaveDNI.setText("Nro DNI:");
        lblClaveDNI.setBounds(new Rectangle(20, 55, 80, 20));
        lblClaveDNI.setFont(new Font("SansSerif", 1, 12));
        txtNroCMP.setBounds(new Rectangle(100, 20, 140, 20));
        txtNroCMP.setText("64313");
        txtNroCMP.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                txtNroCmpPressed(e);
            }
        });
        
        txtNroDNI.setBounds(new Rectangle(100, 55, 140, 20));
        txtNroDNI.setFont(new Font("SansSerif", 0, 11));
        txtNroDNI.setText("42797911");
        txtNroDNI.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                    txtNroDniPressed(e);
                }
        });
        
        
        btnSalir.setText("Salir");
        btnSalir.setBounds(new Rectangle(30, 95, 100, 20));
        btnSalir.setFont(new Font("SansSerif", 1, 11));
        btnAceptar.setText("Aceptar");
        btnAceptar.setBounds(new Rectangle(140, 95, 100, 20));
        btnAceptar.setFont(new Font("SansSerif", 1, 11));
        btnAceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                validarDatos();
            }
        });
    }

    private void initialize() {
        FacadeAtencioMedica facade = new FacadeAtencioMedica();
       // ArrayList lst = facade.obtenerListaTipoColegiatura();
        //FarmaLoadCVL.loadCVLFromArrayList(cmbTipoColegiatura, cmbTipoColegiatura.getName(), lst, true);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroCMP);
    }
    
    private void this_windowClosing(WindowEvent e) {
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
        cerrarVentana(false);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void txtNroDniPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    
    private void txtNroCmpPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroDNI);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    
    private void validarDatos(){
        String nroCMP = txtNroCMP.getText();
        String claveDni = (new String(txtNroDNI.getPassword())).trim();
       // String tipoColegio = FarmaLoadCVL.getCVLCode(cmbTipoColegiatura.getName(), cmbTipoColegiatura.getSelectedIndex());
       // String descTipoColegio = FarmaLoadCVL.getCVLDescription(cmbTipoColegiatura.getName(), tipoColegio);
        if(nroCMP== null || (nroCMP!=null && nroCMP.trim().length()==0)){
            FarmaUtility.showMessage(this, "Debe de ingresar su n�mero de colegiatura.", txtNroCMP);
            return;
        }
        if(claveDni== null || (claveDni!=null && claveDni.trim().length()==0)){
            FarmaUtility.showMessage(this, "Debe de ingresar su n�mero de DNI.", txtNroDNI);
            return ;
        }
        String codMedico = "";
        try{
            codMedico = (new UtilityAtencionMedica()).validarDatosAccesoConsulta("", nroCMP, claveDni);
            setCodMedico(codMedico);
            ArrayList pArrayList = new ArrayList();
            try{
                DBAdmisionMedica.buscarporCodigoMedico(pArrayList, codMedico, "", "", nroCMP);
                String apellido = ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
                String nombre = ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
                setPNombreMedico(nombre+" "+apellido);
                String especialidad = ((String)((ArrayList)pArrayList.get(0)).get(5)).trim();
                if(isMostrarMensaje())
                    FarmaUtility.showMessage(this, "Bienvenido Doctor(a):\n"+
                                                   apellido+" "+nombre+"\n", txtNroCMP);
            }catch(Exception e){
                if(isMostrarMensaje())
                    FarmaUtility.showMessage(this, "Bienvenido Doctor(a)", txtNroCMP);
            }
            cerrarVentana(true);
        }catch(Exception ex){
            if(ex.getCause() instanceof SQLException){
                if (((SQLException)ex.getCause()).getErrorCode()==20510){
                    FarmaUtility.showMessage(this, "No se ha encontrado el Nro de Colegiatura\n"+
                                                   "Colegio: "+""+".\nVerifique!!!", txtNroCMP);
                }else if (((SQLException)ex.getCause()).getErrorCode()==20511){
                    FarmaUtility.showMessage(this, "Se ha encontrado m�s de un registro con los datos:\n"+
                                                   "Colegio: "+""+"\n"+
                                                   "Nro Colegio: "+nroCMP, txtNroCMP);
                }else if (((SQLException)ex.getCause()).getErrorCode()==20512){
                    FarmaUtility.showMessage(this, "Medico no se encuentra activo.", txtNroCMP);
                }else if (((SQLException)ex.getCause()).getErrorCode()==20513){
                    FarmaUtility.showMessage(this, "Medico no se encuentra habilitado en el sistema\npara realizara atenciones m�dicas", txtNroCMP);
                }else if (((SQLException)ex.getCause()).getErrorCode()==20514){
                    FarmaUtility.showMessage(this, "Clave err�nea, verifique!!!", txtNroDNI);
                }else{
                    log.error("", ex);
                    FarmaUtility.showMessage(this, "Error en el proceso de validaci�n de datos.\n"+
                                                   "Reintente, si problema persiste comuniquese con Mesa de Ayuda", txtNroCMP);
                }
            }else{
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error inesperado!!!.\n"+
                                               "Reintente, si problema persiste comuniquese con Mesa de Ayuda", txtNroCMP);
            }
            
        }
    }

    public String getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(String codMedico) {
        this.codMedico = codMedico;
    }

    public boolean isMostrarMensaje() {
        return mostrarMensaje;
    }

    public void setMostrarMensaje(boolean mostrarMensaje) {
        this.mostrarMensaje = mostrarMensaje;
    }


    public void setPNombreMedico(String pNombreMedico) {
        this.pNombreMedico = pNombreMedico;
    }

    public String getPNombreMedico() {
        return pNombreMedico;
    }
}
