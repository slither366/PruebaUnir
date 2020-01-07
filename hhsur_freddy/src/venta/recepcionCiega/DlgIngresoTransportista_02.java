package venta.recepcionCiega;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import common.DlgLogin;
import common.FarmaConstants;
import common.FarmaLengthText;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.DlgListaMaestros;
import venta.recepcionCiega.reference.*;
import venta.reference.ConstantsPtoVenta;

import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgDatosTransportista_02.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ASOSA 05.04.2010 Creaci�n<br>
 * <br>
 *
 * @author ALFREDO SOSA DORDAN<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresoTransportista_02 extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoTransportista_02.class); 

    private JPanel jContentPane = new JPanel();

    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtBultos = new JTextFieldSanSerif();
    // adcionado 15042014
    private JTextFieldSanSerif txtBandejas = new JTextFieldSanSerif();

    private JLabelWhite lblValor_T = new JLabelWhite();

    private JButtonLabel btnFechaInicial = new JButtonLabel();

    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();

    private JLabelWhite lblFechaFinal_T = new JLabelWhite();


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
    private JButtonLabel lblGlosa = new JButtonLabel();
    private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtcodtrans = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomtrans = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgIngresoTransportista_02() {
        this(null, "", false);
    }

    public DlgIngresoTransportista_02(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            log.error("",e);
        }

    }

    /* ********************************************************************** */
    /* METODO JBINIT */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(529, 267));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso Datos Transportista");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setBackground(Color.white);
        jContentPane.setFont(new Font("SansSerif", 0, 11));
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(470, 236));
        pnlTitle1.setBounds(new Rectangle(5, 10, 510, 195));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setLayout(null);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        /*
        txtPrecintos.setLengthText(6);
        txtPrecintos.setBounds(new Rectangle(155, 130, 135, 20));
        txtPrecintos.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtPrecintos_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtPrecintos_keyPressed(e);
                    }
                });
        */
        txtBandejas.setLengthText(6);
        txtBandejas.setBounds(new Rectangle(195, 130, 135, 20));
        txtBandejas.setText("0");
        txtBandejas.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtBandejas_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                    txtBandejas_keyPressed(e);
                }
                });
        
        txtBultos.setLengthText(6);
        txtBultos.setBounds(new Rectangle(195, 100, 120, 20));
        txtBultos.addKeyListener(new KeyAdapter() {


                    public void keyPressed(KeyEvent e) {
                    txtBultos_keyPressed(e);
                }

                    public void keyTyped(KeyEvent e) {
                        txtBultos_keyTyped(e);
                    }
                });
        // aampuero 14.04.2014
        lblValor_T.setText("N\u00ba Bandejas que se Devuelven:");
        lblValor_T.setForeground(new Color(255, 130, 14));
        lblValor_T.setBounds(new Rectangle(10, 130, 190, 20));
        btnFechaInicial.setText("Nombre completo :");

        btnFechaInicial.setForeground(new Color(255, 130, 14));
        btnFechaInicial.setMnemonic('N');
        btnFechaInicial.setBounds(new Rectangle(10, 40, 105, 20));
        btnFechaInicial.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnFechaInicial_actionPerformed(e);
                    }
                });
        txtNombre.setLengthText(99);
        txtNombre.setBounds(new Rectangle(195, 40, 265, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNombre_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtNombre_keyTyped(e);
                    }
                });
        lblFechaFinal_T.setText("Cantidad Bultos Recibidos:");
        lblFechaFinal_T.setForeground(new Color(255, 130, 14));
        lblFechaFinal_T.setBounds(new Rectangle(10, 100, 190, 20));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(395, 210, 85, 20));
        lblF11.setText("[F11] Aceptar");
        lblF11.setBounds(new Rectangle(295, 210, 95, 20));
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tama�o de digitos
        //  12.09.2008 Dubilluz
        jLabelOrange2.setText("Placa Unidad :");
        jLabelOrange2.setBounds(new Rectangle(10, 70, 125, 20));
        txtPlaca.setBounds(new Rectangle(195, 70, 175, 20));
        txtPlaca.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPlaca_keyPressed(e);
                    }
                });
        txtPlaca.setLengthText(8);
        lblGlosa.setText("Glosa :");
        lblGlosa.setBounds(new Rectangle(10, 160, 75, 15));
        lblGlosa.setForeground(new Color(255, 130, 14));
        txtGlosa.setBounds(new Rectangle(195, 160, 305, 20));
        txtGlosa.setLengthText(2000);
        txtGlosa.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtGlosa_keyPressed(e);
                    }
                });
        jButtonLabel1.setText("Empresa de Transporte:");
        jButtonLabel1.setBounds(new Rectangle(10, 15, 140, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('e');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jButtonLabel1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel1_keyPressed(e);
                    }
                });
        txtcodtrans.setBounds(new Rectangle(195, 15, 35, 20));
        txtcodtrans.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtcodtrans_keyPressed(e);
                    }
                });
        txtnomtrans.setBounds(new Rectangle(235, 15, 185, 20));
        txtnomtrans.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtnomtrans_keyPressed(e);
                    }
                });
        pnlTitle1.add(txtnomtrans, null);
        pnlTitle1.add(txtcodtrans, null);
        pnlTitle1.add(jButtonLabel1, null);
        pnlTitle1.add(txtGlosa, null);
        pnlTitle1.add(lblGlosa, null);
        pnlTitle1.add(txtPlaca, null);
        pnlTitle1.add(jLabelOrange2, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(txtBandejas, new XYConstraints(115, 70, 195, 20));
        pnlTitle1.add(txtBultos, new XYConstraints(115, 45, 195, 20));
        pnlTitle1.add(lblValor_T, new XYConstraints(15, 70, 90, 15));
        pnlTitle1.add(btnFechaInicial, new XYConstraints(15, 20, 90, 15));
        pnlTitle1.add(txtNombre, new XYConstraints(115, 15, 195, 20));
        pnlTitle1.add(lblFechaFinal_T, new XYConstraints(15, 50, 90, 15));
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        //lblDescLocal.setText(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim());
        //jquispe 05.05.2010 lee las veces que imprimira un voucher
        VariablesRecepCiega.vNumImpresiones = UtilityRecepCiega.getNumImpresiones();
        cargarFecha();
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnFechaInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombre);
    }

    private void txtVTa_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtNombre, e);
    }

    private void txtAlmc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtBultos, e);
        FarmaUtility.admitirDigitosDecimales(txtBandejas, e);
    }

    private void txtFechainicial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    /*
    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecintos);
        }
        chkKeyPressed(e);
    }
    */
    // AAMPUERO 14.04.2014
    private void txtFechaFinal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBandejas);
        }
        chkKeyPressed(e);
    }
    private void txtValor_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtcodtrans);
        txtnomtrans.setEnabled(false);
        txtBultos.setText("0");
        txtPrecintos.setText("0");
        cargaLogin();
    }


    private void txtFechaFinal_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtBultos, e);
    }

    private void txtValor_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void txtFecFacturacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void txtFecFacturacion_keyReleased(KeyEvent e) {
        //FarmaUtility.dateComplete(txtFecFacturacion,e);
    }

    private void txtValorProv_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // FarmaUtility.moveFocus(txtFecFacturacion);
        }
        chkKeyPressed(e);
    }

    private void txtValorProv_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            //obtiene datos
            if (datosValidados()) {
                if (JConfirmDialog.rptaConfirmDialog(this, 
                                                   "Est� seguro que desea grabar el transportista ?")) {
                    //obtiene datos transportista
                    getDatosTransportista();
                    try{
                            String vNroRecep = DBRecepCiega.ingresaDatosTrans_02("0",txtcodtrans.getText().trim()).trim();
                            FarmaUtility.aceptarTransaccion();                          
                            UtilityRecepCiega.imprimeVoucherTransportista(this,vNroRecep,txtNombre);    
                            
                        
                        
                            
                            cerrarVentana(true);
                        }catch(SQLException sql){
                            FarmaUtility.liberarTransaccion();
                            log.debug("ERROR: "+sql.getMessage());
                            log.error("",sql);
                            FarmaUtility.showMessage(this,"Error al ingresar los Datos del Transportista.",txtNombre);
                        }
                    //FarmaUtility.showMessage(this,"Datos guardados correctamente.",txtPrecintos);
                    //cerrarVentana(true);
                    //mostrarGuias();
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void getDatosTransportista() {

        VariablesRecepCiega.vNombreTrans = txtNombre.getText().trim();
        
        VariablesRecepCiega.vPlacaUnidTrans = txtPlaca.getText().trim();
        VariablesRecepCiega.vCantBultos = txtBultos.getText().trim();
        /*
         VariablesRecepCiega.vCantPrecintos = txtPrecintos.getText().trim();
        */
        // AAMPUERO 15.04.2014
        VariablesRecepCiega.vCantBandejas = txtBandejas.getText().trim();

        //JMIRANDA 05.03.10
        VariablesRecepCiega.vGlosa = txtGlosa.getText().trim();

        log.debug("VariablesRecepCiega.vNombreTrans " + 
                           VariablesRecepCiega.vNombreTrans);
        log.debug("VariablesRecepCiega.vHoraTrans " + 
                           VariablesRecepCiega.vHoraTrans);
        log.debug("VariablesRecepCiega.vPlacaUnidTrans " + 
                           VariablesRecepCiega.vPlacaUnidTrans);
        log.debug("VariablesRecepCiega.vCantBultos " + 
                           VariablesRecepCiega.vCantBultos);
        log.debug("VariablesRecepCiega.vCantPrecintos " + 
                           VariablesRecepCiega.vCantPrecintos);

    }


    /**
     * Se valida ingreso de quimico (Administrador Local)
     */
    private void cargaLogin() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = 
            new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, 
                                         "El usuario no es el mismo al logueado anteriormente. Verificar!!!", 
                                         txtNombre);
                cerrarVentana(false);
            }

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }


    private void mostrarGuias() {

        log.debug("MOSTRANDO GUIAS");

        DlgGuiasPendientes dlgGuias = 
            new DlgGuiasPendientes(myParentFrame, "", true);
        dlgGuias.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cerrarVentana(true);
            //FarmaVariables.vAceptar = false;
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /* METODOS DE LOGICA DE NEGOCIO */
    /* *********************************************************************** */


    private boolean datosValidados() {
        boolean retorno = true;
        if(txtcodtrans.getText().trim().length()<1 || txtnomtrans.getText().trim().length()<1){//ASOSA, 22.04.2010
            FarmaUtility.showMessage(this,"Ingrese codigo de empresa de transporte",txtcodtrans);
            retorno=false;
        }else if(!verificarCodigo()){//ASOSA, 22.04.2010
            retorno=false;
        }else if(FarmaVariables.vAceptar==false){
            FarmaUtility.showMessage(this,"Seleccione un codigo valido",txtcodtrans);
            retorno=false;
        }else if (txtNombre.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese nombre del transportista.", 
                                     txtNombre);
            retorno = false;
        }  else if (txtPlaca.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese Placa.", txtPlaca);
            retorno = false;
        } else if (txtBultos.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de bultos.", 
                                     txtBultos);
            retorno = false;
        } else if (txtBandejas.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese cantidad de bandejas.", 
                            txtBandejas);
                retorno = false;       
        // AAMPUERO 14.04.2014     
        
        //} else if (txtPrecintos.getText().trim().length() < 1) {
         //   FarmaUtility.showMessage(this, "Ingrese cantidad de precintos.", 
           //                          txtPrecintos);
            //retorno = false;
        
        } else if (!validaBultos()) {
            //FarmaUtility.showMessage(this, "Debe ingresar bultos con valor mayor a cero.", txtBultos);
            retorno = false;
        } else if (!validaBandejas()) {
               //FarmaUtility.showMessage(this, "Debe ingresar bandejas con valor mayor a cero.", txtPrecintos);
            retorno = false;
        }            
        
        // aampuero 14.04.2014 - validaBandejas x validaPrecintos 
        //
        //} else if (!validaPrecintos()) {
            //FarmaUtility.showMessage(this, "Debe ingresar precintos con valor mayor a cero.", txtPrecintos);
        //    retorno = false;
        //} 
        
        /*else if(!txtPlaca.getText().matches("[0-9]+[-]+?[A-Z]*")){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
      }*/

   

        /*  if(!validarPlaca(txtPlaca.getText().trim())&&txtPlaca.getText().trim().length()>0){
          FarmaUtility.showMessage(this, "Formato de Placa no valido.", txtPlaca);
          retorno=false;
          }*/

        return retorno;
    }


    /*public static boolean validarHora(String hora ) {
        log.debug("hora...........");
           boolean b = Pattern.matches("[0-9:]", hora);
        log.debug("b."+b);
           return b;
    }*/

    /*public static boolean validarPlaca( String placa ) {
    log.debug("placa...........");
           boolean b = Pattern.matches("^[a-zA-Z0-9%-]$", placa);
              log.debug("b."+b);
           return b;
          }*/

    //boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

  /*  private boolean validarHora() {
        boolean retorno = true;
        if (!FarmaUtility.isHoraMinutoValida(this, txtHora.getText(), 
                                             "La hora ingresada no tiene el formato correcto")) {
            FarmaUtility.moveFocus(txtHora);
            retorno = false;
        }
        if (FarmaUtility.validarHora(txtHora.getText().trim())) {
            FarmaUtility.showMessage(this, 
                                     "La hora ingresada no tiene el es formato.", 
                                     txtHora);
            retorno = false;
        }
        return retorno;
    }*/

    private boolean validaBultos() {
        boolean valor = false;
        String cantIngreso = txtBultos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) > 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "Ingrese Cantidad de Bultos mayor a cero.", 
                                         txtBultos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", 
                                     txtBultos);
        }

        return valor;
    }

    // AAMPUERO 15.04.2014 - CAMBIA POR PRECINTOS
    private boolean validaBandejas() {
        boolean valor = false;
        String cantIngreso = txtBandejas.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) >= 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "Ingrese Cantidad de Bandejas mayor o igual a cero.", 
                                         txtBandejas);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", 
                                     txtBandejas);
        }
        return valor;
    }
    
    /*
    private boolean validaPrecintos() {
        boolean valor = false;
        String cantIngreso = txtPrecintos.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso)) {
            if (Integer.parseInt(cantIngreso) >= 0) {
                valor = true;
            } else {
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "Ingrese Cantidad de Precintos mayor o igual a cero.", 
                                         txtPrecintos);
            }
        } else {
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar cantidad entera.", 
                                     txtPrecintos);
        }
        return valor;
    }
    */

    private void cargarFecha() {
        try {
            FechaInicio = FarmaSearch.getFechaHoraBD(1);
        } catch (SQLException sql) {
            log.error("",sql);
        }
    }


    private void txtNombre_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPlaca);
        }
        chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBultos);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtBandejas);
        }
        chkKeyPressed(e);
    }

    private void txtBultos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
    }

    
    /*
    private void txtPrecintos_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBultos, e);
        //FarmaUtility.admitirDigitosDecimales(txtPrecintos, e);
    }
    */
    private void txtBandejas_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBandejas, e);
    }

    /*
    private void txtPrecintos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtGlosa);
        }
        chkKeyPressed(e);
    }
    */
    private void txtBandejas_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtGlosa);
        }
        chkKeyPressed(e);
    }
    private void txtNombre_keyTyped(KeyEvent e) {

        FarmaUtility.admitirLetras(txtNombre, e);
    }

    private void txtGlosa_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }

    private void txtcodtrans_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){           
            /*String tipoMaestro = FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBTIPO_TRANSF,cmbTipo.getSelectedIndex());
            if(tipoMaestro.trim().length() == 0)
              FarmaUtility.showMessage(this,"Seleccione un Tipo de Origen.",txtcodtrans);
            else
            {*/
              /*if(tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_LOCAL))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_LOCAL;
              else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_MATRIZ;
              else if (tipoMaestro.equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
                VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;*/
              VariablesPtoVenta.vTipoMaestro="15";
              validaCodigo(txtcodtrans, txtnomtrans, VariablesPtoVenta.vTipoMaestro);  
            //}
              if(!txtcodtrans.getText().trim().equals("")){
                  FarmaUtility.moveFocus(txtNombre);
              }
        }else{
            chkKeyPressed(e);
        }
    }
    
    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
    {
      if(pJTextField_Cod.getText().trim().length() > 0)
      {
        VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
        ArrayList myArray = new ArrayList();
        buscaCodigoListaMaestro(myArray);
        
        if(myArray.size() == 0)
        {
          FarmaUtility.showMessage(this,"Codigo No Encontrado",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        } else if(myArray.size() == 1)
        {
          String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
          String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
          pJTextField_Cod.setText(codigo);
          pJTextField_Desc.setText(descripcion);
          VariablesPtoVenta.vCodMaestro = codigo;
          FarmaVariables.vAceptar = true;
        } else
        {
          FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        }
      } else
      {
        cargaListaMaestros(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
      }
    }
    
    private void buscaCodigoListaMaestro(ArrayList pArrayList)
    {
      try
      {
        DBPtoVenta.buscaCodigoListaMaestro(pArrayList,VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
      } catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurri� un error al buscar c�digo en maestros :\n" + sql.getMessage(),txtcodtrans);
      }
    }
    
    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
    {
      VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
      DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
      dlgListaMaestros.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
        pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
      }
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodtrans);
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomtrans_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    /**
     * Validar por ultima ves el codigo de empresa de transporte
     * @author ASOSA
     * @since 22.04.2010
     * @return
     */
    private boolean verificarCodigo(){
        ArrayList list02=new ArrayList();
        VariablesPtoVenta.vTipoMaestro="15";
        VariablesPtoVenta.vCodMaestro=txtcodtrans.getText().trim();
        buscaCodigoListaMaestro(list02);
        boolean flag=false;
        if(list02.size() == 0){
            FarmaUtility.showMessage(this,"Codigo No Encontrado",txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag=false;
        }else if(list02.size()==1){
            FarmaVariables.vAceptar = true;
            flag=true;
        }else{
            FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag=false;
        }
        return flag;
    }
    
}
