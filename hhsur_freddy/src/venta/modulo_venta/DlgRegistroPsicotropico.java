package venta.modulo_venta;


import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JTextFieldSanSerif;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaLengthText;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.DlgListaMaestros;
import venta.campAcumulada.reference.DBCampAcumulada;
import venta.cliente.DlgBuscarDni;
import venta.cliente.DlgRegistroCliente;
import venta.cliente.reference.ConstantsCliente;
import venta.recetario.reference.DBRecetario;
import venta.recetario.reference.VariablesRecetario;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;
import org.apache.ibatis.logging.LogFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosPacienteMedico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgRegistroPsicotropico extends JDialog {
    
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroPsicotropico.class);
    
    private Frame myParentFrame;
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton btnDni = new JButton();
    private JButton btnPaciente = new JButton();
    private JButton btnCmp = new JButton();
    private JButton btnMedico = new JButton();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JTextField txtPaciente = new JTextField();
    private JTextFieldSanSerif txtCmp = new JTextFieldSanSerif();
    private JTextField txtMedico = new JTextField();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    
    private String strDni = "";
    private String strPaciente = "";
    private String strCMP = "";
    private String strNombMed = "";

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgRegistroPsicotropico() {
        this(null, "", false);
    }

    public DlgRegistroPsicotropico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame=parent;
        try {
            jbInit();
            initVariables();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        //this.setSize(new Dimension(498, 485));
        this.setSize(new Dimension(498, 183));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setTitle("Registro de Venta de Medicamentos Psicotrópicos");
        this.addWindowListener(new WindowAdapter() {

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jPanel1.setLayout(null);
        jPanel1.setForeground(Color.white);
        jPanel1.setBackground(Color.white);
        jPanel1.setFocusable(false);
        jPanel2.setBounds(new Rectangle(15, 10, 465, 105));
        jPanel2.setBackground(new Color(43, 141, 39));
        jPanel2.setLayout(null);

        jPanel2.setForeground(new Color(0, 132, 0));
        jPanel2.setFocusable(false);
        btnDni.setText("* DNI :");
        btnDni.setBackground(Color.white);
        btnDni.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDni.setBorderPainted(false);
        btnDni.setContentAreaFilled(false);
        btnDni.setDefaultCapable(false);
        btnDni.setFocusPainted(false);
        btnDni.setFont(new Font("SansSerif", 1, 11));
        btnDni.setForeground(Color.white);
        btnDni.setHorizontalAlignment(SwingConstants.LEFT);
        btnDni.setMnemonic('D');
        btnDni.setRequestFocusEnabled(false);
        btnDni.setBounds(new Rectangle(10, 15, 105, 20));

        btnDni.setFocusable(false);
        btnDni.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    btnDni_actionPerformed(e);
                }
                });
        btnPaciente.setText("* Paciente :");
        btnPaciente.setBackground(Color.white);
        btnPaciente.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPaciente.setBorderPainted(false);
        btnPaciente.setContentAreaFilled(false);
        btnPaciente.setDefaultCapable(false);
        btnPaciente.setFocusPainted(false);
        btnPaciente.setFont(new Font("SansSerif", 1, 11));
        btnPaciente.setForeground(Color.white);
        btnPaciente.setHorizontalAlignment(SwingConstants.LEFT);
        btnPaciente.setRequestFocusEnabled(false);
        btnPaciente.setBounds(new Rectangle(10, 45, 105, 25));
        btnPaciente.setVisible(false);
        btnPaciente.setFocusable(false);
        btnPaciente.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnPaciente_actionPerformed(e);
                    }
                });

        btnCmp.setText("* CMP :");
        btnCmp.setBackground(Color.white);
        btnCmp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCmp.setBorderPainted(false);
        btnCmp.setContentAreaFilled(false);
        btnCmp.setDefaultCapable(false);
        btnCmp.setFocusPainted(false);
        btnCmp.setFont(new Font("SansSerif", 1, 11));
        btnCmp.setForeground(Color.white);
        btnCmp.setHorizontalAlignment(SwingConstants.LEFT);
        btnCmp.setMnemonic('C');
        btnCmp.setRequestFocusEnabled(false);
        btnCmp.setBounds(new Rectangle(10, 50, 105, 20));

        btnCmp.setFocusable(false);
        btnCmp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCmp_actionPerformed(e);
                    }
                });
        btnMedico.setText("* Médico :");
        btnMedico.setBackground(Color.white);
        btnMedico.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMedico.setBorderPainted(false);
        btnMedico.setContentAreaFilled(false);
        btnMedico.setDefaultCapable(false);
        btnMedico.setFocusPainted(false);
        btnMedico.setFont(new Font("SansSerif", 1, 11));
        btnMedico.setForeground(Color.white);
        btnMedico.setHorizontalAlignment(SwingConstants.LEFT);
        btnMedico.setRequestFocusEnabled(false);
        btnMedico.setBounds(new Rectangle(10, 100, 105, 30));
        btnMedico.setVisible(false);
        btnMedico.setFocusable(false);
        btnMedico.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMedico_actionPerformed(e);
                    }
                });
        txtDni.setBounds(new Rectangle(115, 15, 100, 20));
        txtDni.setLengthText(8);
        txtDni.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtDni_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtDni_keyReleased(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtDni_keyTyped(e);
                }
            });
        txtPaciente.setBounds(new Rectangle(115, 45, 315, 20));
        txtPaciente.setVisible(false);
        txtPaciente.setEditable(false);
        txtPaciente.setFocusable(false);
        txtPaciente.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPaciente_keyPressed(e);
                    }
                });
        txtCmp.setBounds(new Rectangle(115, 50, 80, 20));        
        txtCmp.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtCmp_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCmp_keyTyped(e);
                }
            });
        txtMedico.setBounds(new Rectangle(115, 105, 315, 20));
        txtMedico.setFocusable(false);
        txtMedico.setEditable(false);
        txtMedico.setVisible(false);
        txtMedico.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMedico_keyPressed(e);
                    }
                });

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(385, 125, 95, 25));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
        //lblF11.setBounds(new Rectangle(285, 405, 90, 20));
        lblF11.setBounds(new Rectangle(280, 125, 95, 25));

        lblF11.setFocusable(false);
        jTabbedPane1.setBounds(new Rectangle(160, 210, 5, 5));
        jPanel2.add(txtMedico, null);
        jPanel2.add(txtCmp, null);
        jPanel2.add(txtPaciente, null);
        jPanel2.add(txtDni, null);
        jPanel2.add(btnMedico, null);
        jPanel2.add(btnCmp, null);
        jPanel2.add(btnPaciente, null);
        jPanel2.add(btnDni, null);
        jPanel1.add(jTabbedPane1, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(lblEsc, null);
        jPanel1.add(lblF11, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    private void initVariables()
    {   VariablesRecetario.vMapDatosPacienteMedico = new HashMap();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnPaciente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPaciente);
    }

    private void btnTelefono_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtTelefono);
    }

    private void btnCmp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCmp);
    }

    private void btnMedico_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMedico);
    }

    private void txtPaciente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            //FarmaUtility.moveFocus(txtTelefono);
        }else {
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCmp);
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
        chkKeyPressed(e);
    }

    private void txtTelefono_keyReleased(KeyEvent e){
        //FarmaUtility.dateComplete(txtTelefono, e);
    }

    private void txtCmp_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            validaTextoBusquedaMedico();
            //FarmaUtility.moveFocus(txtDni);
        }else {
            chkKeyPressed(e);
        }
        validarBotonF11();
    }

    private void txtMedico_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDni);
        }
        else
        {
            chkKeyPressed(e);
        }
        validarBotonF11();
    }

    private void txtDni_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   //e.consume();
            //FarmaUtility.moveFocus(txtTelefono);
        }
    }
    

    private void txtDni_keyPressed(KeyEvent e)
    {   
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   
            e.consume();
            FarmaVariables.vAceptar = false;
            mostrarBusquedaPaciente();
            
            //LLEIVA 27-Feb-2014 Si se encontro los datos del paciente, pasar al campo del medico 
            if(FarmaVariables.vAceptar)
            {   
                txtCmp.grabFocus();
            }
        }
        else
        {   
            chkKeyPressed(e);
        }
        validarBotonF11();
    }

    private void btnDni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }    

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
        validarBotonF11();
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e)
    {
        //validarBotonF11();
        if(UtilityPtoVenta.verificaVK_F11(e))
        {   
            if( !txtDni.getText().trim().equals("")      &&
                !txtPaciente.getText().trim().equals("") &&
                !txtCmp.getText().trim().equals("")      &&
                !txtMedico.getText().trim().equals(""))
            {
                if( txtDni.getText().equalsIgnoreCase(this.strDni) &&
                    txtCmp.getText().equalsIgnoreCase(this.strCMP))
                {   
                    Map tmpMapDatos = new HashMap();
                    tmpMapDatos.put("DNI", txtDni.getText().trim());
                    tmpMapDatos.put("PACIENTE", txtPaciente.getText().trim());
                    tmpMapDatos.put("CMP", txtCmp.getText().trim());
                    tmpMapDatos.put("MEDICO", txtMedico.getText().trim());
                    VariablesRecetario.vMapDatosPacienteMedico = tmpMapDatos;

                    //GRABAR DATOS
                    if(insertaDatosVentaRestringidos())
                    {
                        cerrarVentana(true);
                    }
                    else
                    {
                        FarmaUtility.showMessage(this, "No se pudo ingresar correctamente los datos", null);
                    }
                }
                else
                    FarmaUtility.showMessage(this, "ATENCIÓN: El dato de DNI y/o CMP ha sido modificado", null);
            }
            else
                FarmaUtility.showMessage(this, 
                                         "ERROR: Alguno de los campos se encuentra vacío", 
                                         null);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar){
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Abre el formulario de Búsqueda de Clientes
     * @author wvillagomez
     * @since 02.09.2013
     */
    private void mostrarBusquedaPaciente()
    {   ArrayList<Object> resultado = new ArrayList<Object>();
        
        String vdni = txtDni.getText().trim();
        if(vdni.length()==8)
        {   try
            {   DBCampAcumulada.getDatosExisteDNI(resultado, vdni);
                if(resultado.size()>0)
                {
                    ArrayList array = (ArrayList)resultado.get(0);
                    this.strDni = array.get(0).toString();
                    this.strPaciente =array.get(1).toString() + " " +
                                        array.get(2).toString()  + " " +
                                        array.get(3).toString();

                    txtDni.setBackground(new Color(255, 130, 14));
                    txtDni.setText(this.strDni);
                    txtPaciente.setText(this.strPaciente.toUpperCase().trim());
                    txtCmp.grabFocus();
                }
                else
                {   FarmaUtility.showMessage(this, "No se encontro el numero de DNI indicado", null);
                    DlgRegistroCliente dlgRegistroCliente = new DlgRegistroCliente(myParentFrame, "", true);
                    dlgRegistroCliente.setDNI(vdni);
                    dlgRegistroCliente.setVisible(true);
                    
                    if (FarmaVariables.vAceptar)
                    {   mostrarBusquedaPaciente();
                    }

                }
            }
            catch(Exception ex)
            {   log.error("",ex);
            }
        }
        else
            FarmaUtility.showMessage(this, "El formato del DNI es incorrecto", null);
    }
    
    private void validaTextoBusquedaMedico()
    {
        VariablesModuloVentas.vMatriculaApe = txtCmp.getText().trim();
        //Valida si el ingreso es numerico
        if(FarmaUtility.isInteger(VariablesModuloVentas.vMatriculaApe) && VariablesModuloVentas.vMatriculaApe.length() <= 5)
        {
            VariablesModuloVentas.vTipoBusqueda = ConstantsModuloVenta.TIPO_MATRICULA;
            VariablesModuloVentas.vMatriculaApe = FarmaUtility.completeWithSymbol(VariablesModuloVentas.vMatriculaApe,5,"0","I");
            muestraListaMedicos();
        }
        else
        {
            VariablesModuloVentas.vTipoBusqueda = ConstantsModuloVenta.TIPO_APELLIDO;
            if (VariablesModuloVentas.vMatriculaApe.length()< 3 ) 
            {
                FarmaUtility.showMessage(this,
                                         "Debe ingresar mas de tres caracteres para realizar la busqueda.",
                                        txtCmp);
                return ;
            } 
            else 
                muestraListaMedicos();
        }
    }
    
    private void muestraListaMedicos()
    {
       /* DlgListaMedicos dlgListaMedicos = new DlgListaMedicos (myParentFrame,"",true);
        dlgListaMedicos.setVisible(true);
        if ( FarmaVariables.vAceptar )
        {
            this.strCMP = VariablesModuloVentas.vMatriListaMed;
            this.strNombMed = VariablesModuloVentas.vNombreListaMed;
            
            txtCmp.setBackground(new Color(255, 130, 14));
            txtCmp.setText(this.strCMP);
            txtMedico.setText(this.strNombMed.toUpperCase());
        }*/
    }
    
    /**
     * Graba en la BD un Registro Psicotropico
     * @author wvillagomez
     * @since 02.09.2013
     * @return boolean
     */
    private boolean insertaDatosVentaRestringidos()
    {
        boolean vRetorno = false;
        try
        {
            DBModuloVenta.insertaDatosVentaRestringidos();
            vRetorno = true;
        }
        catch(SQLException sql)
        {
            log.error("",sql);
        }
        return vRetorno;
    }

    private void txtDni_keyTyped(KeyEvent e)
    {   FarmaUtility.admitirDigitos(txtDni, e);
    }

    private void txtCmp_keyTyped(KeyEvent e)
    {   //FarmaUtility.admitirDigitos(txtCmp, e);
    }
    
    private void validarBotonF11()
    {   if( !txtDni.getText().equalsIgnoreCase("") &&
            !txtCmp.getText().equalsIgnoreCase("") &&
            txtDni.getText().equalsIgnoreCase(this.strDni) &&
            txtCmp.getText().equalsIgnoreCase(this.strCMP))
        {   
            lblF11.setEnabled(true);
        }
        else
            lblF11.setEnabled(false);
    }
}