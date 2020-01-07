package venta.laboratorio;


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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.recetario.reference.DBRecetario;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;


public class DlgBusquedaPedido extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgBusquedaPedido.class);
    @SuppressWarnings("compatibility:936755753537416240")
    private static final long serialVersionUID = -2626325502788986022L;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();    
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblTipoComprobante = new JButtonLabel();
    private JButtonLabel lblNroComprobante = new JButtonLabel();    
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JButtonLabel lblFecha = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JComboBox cmbTipoComp = new JComboBox();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private String eRCM="";
    private String vRpta="";
    private boolean estrcm = false;
    private boolean estvta = false;
    //private JTextField txtFecha = new JTextField();
    private JTextFieldSanSerif  txtFecha  = new JTextFieldSanSerif();    
    private JLabel jLabel1 = new JLabel();
    
    
    private String pFecha_Pedido ="";
    
    public DlgBusquedaPedido() {
        this(null, "", false);
    }

    public DlgBusquedaPedido(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
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
        this.setSize(new Dimension(446, 262));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Consulta de Correlativo Comprobante");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 400, 165));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        lblTipoComprobante.setText("Tipo Comprobante:");
        lblTipoComprobante.setMnemonic('T');
        lblTipoComprobante.setBounds(new Rectangle(20, 20, 110, 15));
        lblTipoComprobante.setBackground(Color.white);
        lblTipoComprobante.setForeground(new Color(0, 114, 169));
        lblTipoComprobante.setFocusable(false);
        lblTipoComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblTipoComprobante_actionPerformed(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(215, 55, 130, 20));
        lblNroComprobante.setText("Nro. Comprobante:");
        lblNroComprobante.setMnemonic('N');
        lblNroComprobante.setBounds(new Rectangle(20, 55, 105, 15));
        lblNroComprobante.setForeground(new Color(0, 114, 169));
        lblNroComprobante.setFocusable(false);
        lblNroComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblNroComprobante_actionPerformed(e);
                    }
                });
        txtSerie.setBounds(new Rectangle(160, 55, 60, 20));
        //txtSerie.setLengthText(3);
        txtSerie.setLengthText(4);
        txtSerie.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtSerie_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                    txtSerie_keyTyped(e);
                }
                });
        txtNroComprobante.setBounds(new Rectangle(235, 55, 145, 20));
        //txtNroComprobante.setLengthText(7);        
        txtNroComprobante.setLengthText(8); //LTAVARA 03.09.2014 se incrementa en 8 para el correlativo del documento electronico
        txtNroComprobante.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtNroComprobante_keyPressed(e);
                }


                public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });
        lblMonto.setText("Monto:");
        lblMonto.setMnemonic('M');
        lblMonto.setBounds(new Rectangle(20, 95, 75, 15));
        lblMonto.setForeground(new Color(0, 114, 169));
        lblMonto.setFocusable(false);
        lblMonto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblMonto_actionPerformed(e);
                    }
                });
        
        lblFecha.setText("Fecha:");
        lblFecha.setMnemonic('F');
        lblFecha.setBounds(new Rectangle(20, 130, 110, 15));
        lblFecha.setForeground(new Color(0, 114, 169));
        lblFecha.setFocusable(false);
        lblFecha.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        lblFecha_actionPerformed(e);
                    }
                });            
            
        txtMonto.setBounds(new Rectangle(160, 95, 145, 19));
        txtMonto.setLengthText(10);
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtMonto_keyPressed(e);
                }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }

            });
        cmbTipoComp.setBounds(new Rectangle(160, 20, 220, 20));
        cmbTipoComp.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbTipoComp_keyPressed(e);
                    }
                });
        cmbTipoComp.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbTipoComp_itemStateChanged(e);
                }
            });
        btnF11.setBounds(new Rectangle(5, 185, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 185, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnF11_mouseClicked(e);
                }
            });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        btnEsc.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnEsc_mouseClicked(e);
                }

            });
        txtFecha.setBounds(new Rectangle(160, 130, 145, 20));
        txtFecha.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFecha_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }
            });
        txtFecha.setLengthText(10);
        jLabel1.setText("dd/mm/yyyy");
        jLabel1.setBounds(new Rectangle(310, 130, 85, 20));
        jLabel1.setForeground(new Color(0, 114, 169));
        jLabel1.setFont(new Font("Tahoma", 1, 12));
        jLabel1.setFocusable(false);
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(txtFecha, null);
        pnlTitle.add(cmbTipoComp, null);
        pnlTitle.add(lblMonto, null);
        pnlTitle.add(lblFecha, null);
        pnlTitle.add(txtMonto, null);
        pnlTitle.add(txtNroComprobante, null);
        pnlTitle.add(txtSerie, null);
        pnlTitle.add(lblNroComprobante, null);
        pnlTitle.add(lblTipoComprobante, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    private void initialize()
    {
        cargaCombo();
    }

    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", cmbTipoComp);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbTipoComp);        
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
        if(UtilityPtoVenta.verificaVK_F11(e))
        {
          funcion_f11();          
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
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
        
        if(txtMonto.getText().trim().length()==0)
            return flag = false;
        
        if(cmbTipoComp.getSelectedObjects().length==0)
            return flag = false;

        if(txtFecha.getText().trim().length()==0)
            return flag = false;        
        
        return flag;
    }

    private void txtSerie_keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            if(txtSerie.getText().length()>0)
                if(txtSerie.getText().length()==4){
                    txtNroComprobante.setLengthText(8);
                    FarmaUtility.moveFocus(txtNroComprobante);    
                    if(txtNroComprobante.getText().trim().length()>0){
                        txtNroComprobante.setText("");
                        FarmaUtility.moveFocus(txtNroComprobante);
                    }
                }
            else
                if(txtSerie.getText().length()==3){
                    txtNroComprobante.setLengthText(7);
                    FarmaUtility.moveFocus(txtNroComprobante);    
                    if(txtNroComprobante.getText().trim().length()>0){
                        txtNroComprobante.setText("");
                        FarmaUtility.moveFocus(txtNroComprobante);
                    }
                }
                    else
                        txtNroComprobante.setLengthText(0);
            
            
            
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                if(txtSerie.getText().trim().length()==4)
                    txtNroComprobante.setText(FarmaUtility.completeWithSymbol(txtNroComprobante.getText(), 8,"0", "I")); 
                else
                   if(txtSerie.getText().trim().length()==3)
                    txtNroComprobante.setText(FarmaUtility.completeWithSymbol(txtNroComprobante.getText(), 7,"0", "I")); 
                FarmaUtility.moveFocus(txtMonto);
            }
            chkKeyPressed(e);
    }

    private void txtMonto_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtFecha);
        }
        chkKeyPressed(e);
    }
    
    //RUDY LLANTOY 23.05.13 Obtiene el correlativo a partir de un pedido normal o RCM
    private void getObtenerCorrelativo()
    {
        String vNumCompPago = "";
        char estado=' '; 
        vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();
        VariablesModuloVentas.numCompPago=vNumCompPago.trim();
        if(VariablesPtoVenta.vIndVerReceMagis.equals(FarmaConstants.INDICADOR_S))
        {
            if(validarRCM(vNumCompPago) )
            {
                estado=eRCM.charAt(0);
                //'ESTADO [A=Pendiente|C=Cobrado|E=Enviado|N=Anulado|G=Guia]'; 
                switch(estado)
                {
                    case 'A': case'P': case 'N':
                        FarmaUtility.showMessage(this," PEDIDO PREPARADO MAGISTRAL ",cmbTipoComp);
                    VariablesModuloVentas.vestRCM=true; 
                        validarVta();
                        break;
                    case 'C': case'E': case'G':
                        FarmaUtility.showMessage(this,
                                                 " PEDIDO PREPARADO MAGISTRAL "+ "\n" + 
                                                 " El pedido no se puede anular, el preparado magistral est� en proceso de fabricaci�n.",
                                                 cmbTipoComp);  
                        limpiarCampos();
                        break;
                    default: break;
                }
            }
        }
        else
        {
            validarVta();
        }
    } 
    
    //RUDY LLANTOY 23.05.13 Limpia el formulario y pone el foco en cmbTipoCom
    private void limpiarCampos()
    {
        cmbTipoComp.setSelectedIndex(0);
        txtSerie.setText("");
        txtNroComprobante.setText("");
        txtMonto.setText("");
        FarmaUtility.moveFocus(cmbTipoComp);
    }

    //RUDY LLANTOY 23.05.13 Valida que sea un pedido de RCM
    private boolean validarRCM(String pNumCompPago)
    {
        boolean est=false;                             
        try
        {
            eRCM=DBRecetario.getEstadoRCM(pNumCompPago);
            getDatosRCM(eRCM);
            est=true;
        }
        catch(Exception sql)
        {
            //log.error("",sql);
            validarVta();
        }
        return  est; 
    } 

    // RUDY LLANTOY 23.05.13 Valida una venta normal que no se RCM
    private void validarVta()
    {
        log.debug("Obtiene Correlativo");
        String vTipoComp = "";
        String vMontoNeto = "";
        String vNumCompPago = "";
        VariablesModuloVentas.vNumPedVta_new = "";
        VariablesModuloVentas.vMontoNeto_new = "";
        
        //vTipoComp = FarmaLoadCVL.getCVLCode(ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES, cmbTipoComp.getSelectedIndex());
        vTipoComp = FarmaLoadCVL.getCVLCode("cmbComprobante",
                                            cmbTipoComp.getSelectedIndex());

        vMontoNeto = txtMonto.getText().trim();
        vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();
        log.debug("vTipoComp"+vTipoComp);
            
        try
        {
            vRpta = DBCaja.getIndImprimirCorrelativo(vTipoComp,vMontoNeto,vNumCompPago,txtFecha.getText().trim());
            if (vRpta.equals("0")) 
            {
                FarmaUtility.showMessage(this,
                                        "El pedido ingresado tiene guias generadas y no se puede ANULAR. Verifique.",
                                        cmbTipoComp);
                limpiarCampos();
            }
            else if (vRpta.equals("-1"))
            {
                FarmaUtility.showMessage(this, "La informaci�n ingresada no es correcta verifique.", cmbTipoComp);
                limpiarCampos();
            }
            else
            {
                // VariablesConvenioBTLMF.vTipoCompPago       =  vTipoComp;
                // VariablesConvenioBTLMF.vNumCompPago        =  vNumCompPago;
                getDatos(vRpta);
            }
            estvta=true;
        }
        catch(Exception sql)
        {
            //log.error("",sql);
            FarmaUtility.showMessage(this,"La informaci�n ingresada no es correcta verifique.",cmbTipoComp);
            //limpiarCampos();
        }
        //getDatos(vRpta);
    }
    
    
    private void getDatos(String pDato)
    {
        String pSeparador = ";";
        pDato.trim();        
        String[] arrayLetra = pDato.split(pSeparador);
        if(arrayLetra.length > 0 && arrayLetra.length == 3)
        {
            /*for (int i = 0; i < arrayLetra.length; i++) {
                    log.debug(arrayLetra[i]);
            }*/
            VariablesModuloVentas.vNumPedVta_new = arrayLetra[0].trim();
            VariablesModuloVentas.vMontoNeto_new = arrayLetra[1].trim();
            VariablesModuloVentas.vFechaPedVta_new = arrayLetra[2].trim();
            log.debug("VariablesVentas.vNumPedVta_new: " + VariablesModuloVentas.vNumPedVta_new +" - " + VariablesModuloVentas.vMontoNeto_new);
            cerrarVentana(true);
        }
    }
        
    //RUDY LLANTOY 23.05.13 Obtiene datos a partir de la llamada del Store 
    private void getDatosRCM(String pDato)
    {
        String pSeparador = ";";
        pDato.trim();        
        String[] arrayLetra = pDato.split(pSeparador);
        if(arrayLetra.length > 0 && arrayLetra.length == 1)
        {
            eRCM=arrayLetra[0].trim();
            log.debug("Estado Preparado Magistral: "+eRCM);
        }
    }

    private void lblTipoComprobante_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(cmbTipoComp);
    }

    private void lblNroComprobante_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtSerie);
    }

    private void lblMonto_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtMonto);
    }

    private void lblFecha_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtFecha);
    }
    
    private void cargaCombo()
    {
        //FarmaLoadCVL.loadCVLfromArrays(cmbTipoComp,
        //                               ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_CODIGO,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_DESCRIPCION,true);
        
        //LLEIVA 03-Feb-2014 Se realiza la carga desde la BD
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbTipoComp, 
                                   "cmbComprobante",
                                   "PTOVENTA_ADMIN_IMP.IMP_LISTA_TIPOS_COMPROBANTE(?)", 
                                   parametros, 
                                   true);
    }

    private void cmbTipoComp_keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String vTipoComp = FarmaLoadCVL.getCVLCode("cmbComprobante",
                                                cmbTipoComp.getSelectedIndex());
            if(vTipoComp.equalsIgnoreCase("01"))
                txtSerie.setText("B001");
            else
                if(vTipoComp.equalsIgnoreCase("02"))
                    txtSerie.setText("F001");
            FarmaUtility.moveFocus(txtSerie);
        }
        chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitosDecimales(txtMonto,e);
    }

    private void txtSerie_keyTyped(KeyEvent e)
    {
        //FarmaUtility.admitirDigitos(txtSerie,e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e)
    {
        FarmaUtility.admitirDigitos(txtNroComprobante,e);
    }

    public boolean isEstrcm()
    {
        return estrcm;
    }

    public void setEstrcm(boolean estrcm)
    {
        this.estrcm = estrcm;
    }

    private void txtFecha_keyReleased(KeyEvent e)
    {
        FarmaUtility.dateComplete(txtFecha,e);
    }

    private void txtFecha_keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(cmbTipoComp);
        }
        chkKeyPressed(e);
    }
    
    // Revisa si el pedido es antiguo a la migracion y lo importa para su anulacion.
    // dubilluz 22.01.2014
    public void cargaPedidoAntesMigracion()
    {
        String vTipoComp = FarmaLoadCVL.getCVLCode(ConstantsModuloVenta.HASHTABLE_TIPOS_COMPROBANTES, 
                                                   cmbTipoComp.getSelectedIndex());
        String vMontoNeto = txtMonto.getText().trim();
        String vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();            
        String vFecha = txtFecha.getText().trim();
            
        if(esPedidoAntesMigracion(vFecha,vTipoComp,vNumCompPago,vMontoNeto))
        {
            // SI ES PEDIDO ANTIGUO antes de la Migracion
            // CREA PEDIDO EN MATRIZ y LOCAL 
            creaPedidoLocalmente(vFecha,vTipoComp,vNumCompPago,vMontoNeto);
        }
    }
    
    public boolean esPedidoAntesMigracion(String pFecha,
                                         String pTipComp,
                                         String pNumComp,
                                         String pMontoComp)
    {
        boolean resultado = false;
        String pCadena = "N";
        try
        {
            pCadena = DBModuloVenta.getEsPedidoAntiguo(pFecha, pTipComp, pNumComp, pMontoComp);
        }
        catch (Exception sqle)
        {
            // TODO: Add catch code
            sqle.printStackTrace();
            pCadena = "N";
        }
        if(FarmaConstants.INDICADOR_S.equalsIgnoreCase(pCadena.trim()))
        {
            log.debug("Es Pedido Antiguo");
            resultado = true;
        }
        else
        {
            log.debug("NO ES PEDIDO ANTIGUO");
        }
        return resultado;
    }
    
    /**
     * @author DUbilluz
     * @since 22.01.2014
     * @param pFecha
     * @param pTipComp
     * @param pNumComp
     * @param pMontoComp
     */
    public void creaPedidoLocalmente(String pFecha,
                                     String pTipComp,
                                     String pNumComp,
                                     String pMontoComp
                                     )
    {
        try
        {
            DBModuloVenta.grabaPedidoAntiguoMigracion(pFecha, pTipComp, pNumComp, pMontoComp);
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_S);
        }
        catch (Exception sqle)
        {
            log.info(sqle.getMessage());
            FarmaUtility.showMessage(this,"Ocurrio un Error en Crear Pedido Localmente \n"+
                                          sqle.getMessage()
                                          ,txtFecha);
            FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                  FarmaConstants.INDICADOR_S);            
        }
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        funcion_f11();
    }

    private void funcion_f11() {

        if(validarDatos()){
        //RHERRERA 
        String vTipoComp = FarmaLoadCVL.getCVLCode("cmbComprobante",
                                            cmbTipoComp.getSelectedIndex());String vMontoNeto = txtMonto.getText().trim();
        String vNumCompPago = txtSerie.getText().trim()+txtNroComprobante.getText().trim();
            VariablesModuloVentas.numCompPago=vNumCompPago.trim();
        String vFecha = txtFecha.getText().trim();
        
        String valor;
             
                try {
                    valor = "FALSE";//DBVentas.validaNumComGuiaConvenio(vFecha,vTipoComp,vNumCompPago);
                   
                    if (valor.equals("TRUE")){
                        
                        FarmaUtility.showMessage(this, "No se puede Anular una Guia", cmbTipoComp);
                        limpiarCampos();
                   
                    }else {
                       
                    //cargaPedidoAntesMigracion();
                    getObtenerCorrelativo();  
           
           }
                } catch (Exception f) {
                    f.printStackTrace();
            FarmaUtility.showMessage(this,"La informaci�n ingresada no es correcta verifique.",cmbTipoComp);
                    
                }
         }
         else
                FarmaUtility.showMessage(this,"Falta Ingresar Informaci�n.",cmbTipoComp);
    }
    

    private void btnEsc_mouseClicked(MouseEvent mouseEvent) {
        cerrarVentana(false);
    }

    private void cmbTipoComp_itemStateChanged(ItemEvent e) {
        
        
    }


    public void setPFecha_Pedido(String pFecha_Pedido) {
        this.pFecha_Pedido = pFecha_Pedido;
    }

    public String getPFecha_Pedido() {
        return pFecha_Pedido;
    }
}
