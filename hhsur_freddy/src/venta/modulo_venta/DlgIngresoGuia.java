package venta.modulo_venta;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaPRNUtility;
import common.FarmaPrintServiceTicket;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JConfirmDialog;

import venta.recetario.reference.DBRecetario;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.caja.reference.VariablesCaja;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;

import venta.modulo_venta.DlgMensajeImpresion;

import venta.reference.ConstantsPtoVenta;


public class DlgIngresoGuia extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoGuia.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNroComprobante = new JButtonLabel();    
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM="";
    private String vRpta="";
    private boolean estrcm = false;
    private boolean estvta = false;
    private JLabel jLabel1 = new JLabel();
    private JTextField txtFechaEmision = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtRuc_cliente = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JTextField txtRazonSocial_cliente = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JTextField txtPuntoPartida = new JTextField();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JTextField txtMarca_transporte = new JTextField();
    private JLabel jLabel9 = new JLabel();
    private JTextField txtLicencia_transporte = new JTextField();
    private JLabel jLabel10 = new JLabel();
    private JTextField txtRazonSocial_transporte = new JTextField();
    private JLabel jLabel11 = new JLabel();
    private JTextField txtRuc_transporte = new JTextField();
    //private JTextField txtFecha = new JTextField();
    
    private String pNumPedVta = "";
    private JTextField txtDirec_cliente = new JTextField();
    private JComboBox cmbMotivo = new JComboBox();
    private JLabel jLabel13 = new JLabel();
    private JLabel lblFacturaReferencia = new JLabel();

    public DlgIngresoGuia() {
        this(null, "", false,"");
    }

    public DlgIngresoGuia(Frame parent, String title, boolean modal,String pNumPedVta)
    {
        super(parent, title, modal);
        this.pNumPedVta = pNumPedVta;
        try
        {
            MyParentFrame = parent;
            jbInit();
            initialize();
        }
        catch (Exception e)
        {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception 
    {
        this.setSize(new Dimension(762, 447));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Generar Guia - Transferencia");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 735, 365));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtNroComprobante.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Nro. Guia:");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 20, 105, 15));
        lblNroComprobante.setForeground(new Color(0, 114, 169));
        lblNroComprobante.setFocusable(false);
        txtSerie.setBounds(new Rectangle(160, 20, 60, 20));
        txtSerie.setLengthText(3);
        txtSerie.setText("002");
        txtSerie.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtSerie_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                        txtSerie_keyTyped(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(235, 20, 145, 20));
        txtNroComprobante.setLengthText(7);
        txtNroComprobante.setText("00003212");
        txtNroComprobante.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtNroComprobante_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });


        btnF11.setBounds(new Rectangle(15, 385, 117, 20));
        btnEsc.setBounds(new Rectangle(625, 385, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        jLabel1.setText("Fecha de Emisi\u00f3n :");
        jLabel1.setBounds(new Rectangle(20, 45, 110, 25));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 107, 165));
        txtFechaEmision.setBounds(new Rectangle(160, 50, 170, 20));

        txtFechaEmision.setText("14/08/2018");
        txtFechaEmision.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaEmision_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaEmision_keyReleased(e);
                }
            });
        jLabel2.setText("RUC:");
        jLabel2.setBounds(new Rectangle(390, 50, 45, 20));
        jLabel2.setForeground(new Color(0, 107, 165));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        txtRuc_cliente.setBounds(new Rectangle(450, 50, 195, 20));
        txtRuc_cliente.setText("1051845465121");
        txtRuc_cliente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRucA_keyPressed(e);
                }
            });
        jLabel3.setText("Razon Social :");
        jLabel3.setBounds(new Rectangle(25, 75, 120, 20));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setForeground(new Color(0, 107, 165));
        txtRazonSocial_cliente.setBounds(new Rectangle(160, 80, 555, 25));
        txtRazonSocial_cliente.setText("Cliente de prueba ");
        txtRazonSocial_cliente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDestinatario_keyPressed(e);
                }
            });
        jLabel4.setText("Punto de Partida:");
        jLabel4.setBounds(new Rectangle(30, 145, 125, 25));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(new Color(0, 107, 165));
        txtPuntoPartida.setBounds(new Rectangle(160, 145, 555, 25));
        txtPuntoPartida.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPuntoPartida_keyPressed(e);
                }
            });
        jLabel5.setText("Direccion Cliente:");
        jLabel5.setBounds(new Rectangle(30, 115, 120, 20));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(new Color(0, 107, 165));
        jLabel6.setText("Motivo Traslado :");
        jLabel6.setBounds(new Rectangle(30, 180, 105, 20));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(new Color(0, 107, 165));
        jLabel7.setText("Datos de Unidad de Transporte y Conductor :");
        jLabel7.setBounds(new Rectangle(15, 215, 300, 15));
        jLabel7.setFont(new Font("Tahoma", 1, 13));
        jLabel8.setText("Marca y N\u00b0 Vehiculo:");
        jLabel8.setBounds(new Rectangle(30, 245, 130, 15));
        jLabel8.setFont(new Font("SansSerif", 1, 11));
        jLabel8.setForeground(new Color(0, 107, 165));
        txtMarca_transporte.setBounds(new Rectangle(175, 240, 540, 20));
        txtMarca_transporte.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtMarca_keyPressed(e);
                }
            });
        jLabel9.setText("N\u00b0 Licencia de Conducir :");
        jLabel9.setBounds(new Rectangle(30, 270, 140, 15));
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel9.setForeground(new Color(0, 107, 165));
        txtLicencia_transporte.setBounds(new Rectangle(175, 270, 540, 20));
        txtLicencia_transporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextField7_actionPerformed(e);
                }
            });
        txtLicencia_transporte.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtLicencia_keyPressed(e);
                }
            });
        jLabel10.setText("Nombre \u00f3 Razon Social:");
        jLabel10.setBounds(new Rectangle(30, 295, 140, 20));
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        jLabel10.setForeground(new Color(0, 107, 165));
        txtRazonSocial_transporte.setBounds(new Rectangle(175, 300, 540, 20));
        txtRazonSocial_transporte.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRazonSocial_keyPressed(e);
                }
            });
        jLabel11.setText("RUC :");
        jLabel11.setBounds(new Rectangle(100, 330, 60, 20));
        jLabel11.setFont(new Font("SansSerif", 1, 11));
        jLabel11.setForeground(new Color(0, 107, 165));
        txtRuc_transporte.setBounds(new Rectangle(175, 330, 540, 20));
        txtRuc_transporte.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRucB_keyPressed(e);
                }
            });
        txtDirec_cliente.setBounds(new Rectangle(160, 115, 555, 25));
        txtDirec_cliente.setText("Direccion de prueba");
        txtDirec_cliente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPuntoLlegada_keyPressed(e);
                }
            });
        cmbMotivo.setBounds(new Rectangle(160, 180, 520, 30));
        cmbMotivo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbMotivo_keyPressed(e);
                }
            });
        jLabel13.setBounds(new Rectangle(450, 20, 40, 14));
        lblFacturaReferencia.setText("002 - 0000456");
        lblFacturaReferencia.setBounds(new Rectangle(505, 20, 175, 20));
        pnlTitle.add(lblFacturaReferencia, null);
        pnlTitle.add(jLabel13, null);
        pnlTitle.add(cmbMotivo, null);
        pnlTitle.add(txtDirec_cliente, null);
        pnlTitle.add(txtRuc_transporte, null);
        pnlTitle.add(jLabel11, null);
        pnlTitle.add(txtRazonSocial_transporte, null);
        pnlTitle.add(jLabel10, null);
        pnlTitle.add(txtLicencia_transporte, null);
        pnlTitle.add(jLabel9, null);
        pnlTitle.add(txtMarca_transporte, null);
        pnlTitle.add(jLabel8, null);
        pnlTitle.add(jLabel7, BorderLayout.CENTER);
        pnlTitle.add(jLabel6, null);
        pnlTitle.add(jLabel5, null);
        pnlTitle.add(txtPuntoPartida, null);
        pnlTitle.add(jLabel4, null);
        pnlTitle.add(txtRazonSocial_cliente, null);
        pnlTitle.add(jLabel3, null);
        pnlTitle.add(txtRuc_cliente, null);
        pnlTitle.add(jLabel2, null);
        pnlTitle.add(txtFechaEmision, null);
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(txtSerie, null);
        pnlTitle.add(lblNroComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize()
    {
        ArrayList parametros = new ArrayList();        
        FarmaLoadCVL.loadCVLFromSP(cmbMotivo, "cmbMotivo","SVB_GRABA_TRANSF.GET_MOTIVO_TRANSFERENCIA", parametros, false);
        
        cargaDatosGrabados();
        
    }

    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtSerie);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtSerie);        
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {
            grabarGuia();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if(JConfirmDialog.rptaConfirmDialog(this,"¿esta seguro de salir y perder los datos ingresados?"))
                cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private boolean validarDatos()
    {
        boolean flag = true;
        if(txtSerie.getText().trim().length()==0)
            return flag = false;
        
        if(txtNroComprobante.getText().trim().length()==0)
            return flag = false;
        
        return flag;
    }

    private void txtSerie_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            txtSerie.setText(FarmaUtility.caracterIzquierda(txtSerie.getText(), 
                                                                  3, "0"));
            FarmaUtility.moveFocus(txtNroComprobante);
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                  7, "0"));
            FarmaUtility.moveFocus(txtFechaEmision);
        }
        chkKeyPressed(e);
    }
    
    //RUDY LLANTOY 23.05.13 Limpia el formulario y pone el foco en cmbTipoCom
    private void limpiarCampos()
    {
        txtSerie.setText("");
        txtNroComprobante.setText("");
        FarmaUtility.moveFocus(txtSerie);
    }


    private void txtSerie_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtSerie,e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtNroComprobante,e);
    }


    private void jTextField7_actionPerformed(ActionEvent e) {
    }


    private void txtFechaEmision_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtRuc_cliente);
        }
        chkKeyPressed(e);
    }

    private void txtRucA_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtRazonSocial_cliente);
        }
        chkKeyPressed(e);
    }

    private void txtDestinatario_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtPuntoPartida);
        }
        chkKeyPressed(e);
    }

    private void txtPuntoPartida_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDirec_cliente);
        }
        chkKeyPressed(e);
    }

    private void txtPuntoLlegada_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(cmbMotivo);
        }
        chkKeyPressed(e);
    }

    private void txtMotivoTranslado_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtMarca_transporte);
        }
        chkKeyPressed(e);
    }

    private void txtMarca_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtLicencia_transporte);
        }
        chkKeyPressed(e);
    }

    private void txtLicencia_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtRazonSocial_transporte);
        }
        chkKeyPressed(e);
    }

    private void txtRazonSocial_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtRuc_transporte);
        }
        chkKeyPressed(e);
    }

    private void txtRucB_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtSerie);
        }
        chkKeyPressed(e);
    }

    private void grabarGuia() {
        
        String vNumCompPago = "";
        String vNumFacturaBoleta_refe = "";
        String vSecCompPago = "";
        String  vOrdenGuia = "";
        
        ArrayList vLista = new ArrayList();

        try {
            DBCaja.getComprobantes(vLista,pNumPedVta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        for(int a=0;a<vLista.size();a++){
            
            
            vNumFacturaBoleta_refe = FarmaUtility.getValueFieldArrayList(vLista, a, 0);
            vSecCompPago= FarmaUtility.getValueFieldArrayList(vLista, a, 1);
            vOrdenGuia= FarmaUtility.getValueFieldArrayList(vLista, a, 2);
            
            txtSerie.setText(FarmaUtility.caracterIzquierda(txtSerie.getText(), 
                                                                  3, "0"));
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                  7, "0"));
            
            //String vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();        
            //String vNumFacturaBoleta_refe = lblFacturaReferencia.getText();
            
            String vFechaEmision = txtFechaEmision.getText().trim();
            String vRuc_cliente = txtRuc_cliente.getText().trim();
            String vRazonSocial_cliente = txtRazonSocial_cliente.getText().trim();
            String vPartida = txtPuntoPartida.getText().trim();
            String vDireccion_cliente = txtDirec_cliente.getText().trim();
            
            
            
            String vMotivo = //txtMotivoTranslado.getText().trim();
                           FarmaLoadCVL.getCVLDescription("cmbMotivo", 
                                                           FarmaLoadCVL.getCVLCode("cmbMotivo", cmbMotivo.getSelectedIndex()).toString().trim()
                                                           );
                           //FarmaLoadCVL.getCVLCode("cmbMotivo", cmbMotivo.getSelectedIndex()).toString().trim();
            String vMarca = txtMarca_transporte.getText().trim();
            String vLicencia = txtLicencia_transporte.getText().trim();
            String vRazonSocial_transporte = txtRazonSocial_transporte.getText().trim();
            String vRuc_transporte = txtRuc_transporte.getText().trim();

            ArrayList vListaDetalleGuia = new ArrayList();
            String pRuta = "";
            try {
                vNumCompPago = DBCaja.grabaGuiaTransferencia(pNumPedVta,vFechaEmision, vRuc_cliente, vRazonSocial_cliente, vPartida, vDireccion_cliente, vMotivo, vMarca,
                                              vLicencia, vRazonSocial_transporte, vRuc_transporte,vSecCompPago,vOrdenGuia);
                
                FarmaUtility.aceptarTransaccion();
                
                DBCaja.getDetalleParaGuia(vListaDetalleGuia,pNumPedVta,vSecCompPago,vOrdenGuia);
                pRuta = DBCaja.obtieneRutaImpresoraVenta(DBCaja.getObtieneSecGuia());
                
                //FarmaUtility.showMessage(this, "Se grabó la guia correctamente.", txtSerie);
                
            } catch (SQLException sqle) {
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
            }
            String pCodigo = "";
            String pDescripcion = "";
            String pCantidad = "";
            String pLote = "";
            String pUnidad = "";
                               
            if(vListaDetalleGuia.size()>0){
                
                VariablesCaja.vNumCompBoletaFactura_Impr = vNumCompPago;
                    
                    //txtSerie.getText().trim()+"-"+txtNroComprobante.getText().trim();
                
                DlgMensajeImpresion dlgLogin = new DlgMensajeImpresion(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true,
                                                                 ConstantsModuloVenta.TIPO_COMP_GUIA);
                dlgLogin.setVisible(true);
                // imprimir guia
                //pRuta = "D:\\imp\\cotiza_DU.txt";
                FarmaPrintServiceTicket mainPRN = new FarmaPrintServiceTicket(666, pRuta, false);
                    mainPRN.startPrintService();
                    mainPRN.activateCondensed();
                            // comenzando a imprimir
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("                    " + vFechaEmision, true);
                            mainPRN.printLine("              " +vPartida,true);
                            mainPRN.printLine("          "+vDireccion_cliente+"                                                       Nro Guia: "+ vNumCompPago, true);
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("            " +" "+vRazonSocial_cliente+" ", true);
                            mainPRN.printLine("            " +vRuc_cliente+"                                                                          Nro : "+ vNumFacturaBoleta_refe, true);
                            mainPRN.printLine("                  "+vMotivo ,true);                           
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("            " + " ", true);
                            
                            int pCant=13;// CANTIDAD DE LINEAS Q ENTRA AL DETALLE DE GUIA
                            
                            mainPRN.printLine("            " + " ", true);
                
                            for(int i=0;i<vListaDetalleGuia.size();i++){
                                
                                pCodigo = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 0);
                                pDescripcion = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 1);
                                pLote   = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 2);
                                pUnidad = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 3);
                                pCantidad = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 4);
                                
                                mainPRN.printLine("    "+
                                                    FarmaPRNUtility.alinearIzquierda(pCodigo,8)     +
                                                    FarmaPRNUtility.alinearIzquierda(pUnidad,12)     +
                                                    FarmaPRNUtility.alinearIzquierda(pCantidad,8)    +
                                                    FarmaPRNUtility.alinearIzquierda(pDescripcion,80)+
                                                    FarmaPRNUtility.alinearIzquierda(pLote,14)                                                
                                                    
                                                  ,true);
                                
                            }
                            
                            for(int i=0;i<(pCant-vListaDetalleGuia.size());i++){
                                mainPRN.printLine("    .", true);
                            }
                
                            mainPRN.printLine("            " + " ", true);
                            mainPRN.printLine("                "+vRazonSocial_transporte+"                                              "+vRuc_transporte ,true);
                            mainPRN.printLine("             "+vMarca+"                                                          "+vLicencia ,true);

                            //
                            
                    mainPRN.deactivateCondensed();
                    mainPRN.endPrintService();
                    
             

                // fin impr guia
            }

        }
            
        FarmaUtility.showMessage(this, "Se envió  a imprimir correctamente.", txtSerie);    
        cerrarVentana(true);
    }

    private void txtFechaEmision_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaEmision,e);
    }

    private void cmbMotivo_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtMarca_transporte);
        }
        chkKeyPressed(e);
    }

    private void cargaDatosGrabados() {
        
        ArrayList vDatos = new ArrayList();
        /*
        NVL(SUBSTR(C.NUM_COMP_GUIA,1,3),I.NUM_SERIE_LOCAL) || 'Ã' ||
                NVL(SUBSTR(C.NUM_COMP_GUIA,-7),I.NUM_COMP)|| 'Ã' ||
               decode(p.tip_comp_pago,'01','BOLETA','02','FACTURA','COMP')||':'||
               NVL(SUBSTR(p.num_comp_pago,1,3)||'-'||SUBSTR(p.num_comp_pago,-7),' ') || 'Ã' ||
               TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY')|| 'Ã' ||
               nvl(C.RUC_CLI_PED_VTA,' ')|| 'Ã' ||
               nvl(C.NOM_CLI_PED_VTA,' ')|| 'Ã' ||
               nvl(C.DIR_CLI_PED_VTA,' ')
         * */
        try {
            txtSerie.setText("");
            txtNroComprobante.setText("");
            lblFacturaReferencia.setText("");
            txtFechaEmision.setText("");
            txtRuc_cliente.setText("");
            txtRazonSocial_cliente.setText("");
            txtDirec_cliente.setText("");
            DBCaja.getDatosPedidoGuia(pNumPedVta,vDatos);
            
            if(vDatos.size()>0){
                txtSerie.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 0));
                txtNroComprobante.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 1));
                lblFacturaReferencia.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 2));
                txtFechaEmision.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 3));
                txtRuc_cliente.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 4));
                txtRazonSocial_cliente.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 5));
                txtDirec_cliente.setText(FarmaUtility.getValueFieldArrayList(vDatos,0, 6));
            }
                                   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
