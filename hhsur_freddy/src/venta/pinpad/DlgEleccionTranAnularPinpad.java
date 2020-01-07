package venta.pinpad;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.VariablesCaja;
import venta.inventario.DlgTransferenciasRealizadas;
import venta.pinpad.reference.DBPinpad;
import venta.pinpad.reference.UtilityPinpad;
import venta.recetario.reference.UtilityRecetario;
import venta.recetario.reference.VariablesRecetario;
import venta.reference.ConstantsPtoVenta;

import venta.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgEleccionTranAnularPinpad extends JDialog {
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgEleccionTranAnularPinpad.class);
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel jPanel1 = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblNumAutorizacion = new JLabelOrange();
    private JLabelOrange lblNumReferencia = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JTextFieldSanSerif txtNumAutorizacion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumReferencia = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JComboBox cmbTipoTarjeta = new JComboBox();

    private String monto = "";
    private String formaPago = "";
    private JLabelOrange lblMonto = new JLabelOrange();
    private JTextField txtMonto = new JTextField();

    public DlgEleccionTranAnularPinpad() {
        this(null, "", false);
    }

    public DlgEleccionTranAnularPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(400, 330));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Anulaci�n de Transacci�n de Pinpad");
        pnlFondo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 385, 25));
        pnlTitle.setFocusable(false);
        jPanel1.setBounds(new Rectangle(5, 35, 385, 230));
        jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);
        jPanel1.setFocusable(false);
        lblEsc.setText("[ ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(285, 270, 105, 30));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblTipoTarjeta.setText("Tipo Tarjeta:");
        lblTipoTarjeta.setBounds(new Rectangle(20, 30, 140, 20));
        lblTipoTarjeta.setFocusable(false);
        lblNumAutorizacion.setText("Num. Autorizaci�n (AP):");
        lblNumAutorizacion.setBounds(new Rectangle(20, 70, 135, 20));
        lblNumAutorizacion.setFocusable(false);
        lblNumReferencia.setText("Num. Referencia (REF):");
        lblNumReferencia.setBounds(new Rectangle(20, 105, 130, 20));
        lblNumReferencia.setFocusable(false);
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(20, 140, 125, 20));
        lblFecha.setFocusable(false);
        txtNumAutorizacion.setBounds(new Rectangle(160, 70, 180, 20));
        txtNumAutorizacion.setNextFocusableComponent(txtNumReferencia);
        txtNumAutorizacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNumAutorizacion_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNumAutorizacion_keyTyped(e);
                }
            });
        txtNumAutorizacion.setLengthText(6);
        txtNumReferencia.setBounds(new Rectangle(160, 105, 180, 20));
        txtNumReferencia.setNextFocusableComponent(txtFecha);
        txtNumReferencia.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNumReferencia_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNumReferencia_keyTyped(e);
                }
            });
        txtNumReferencia.setLengthText(4);
        txtFecha.setBounds(new Rectangle(160, 140, 120, 20));
        txtFecha.setNextFocusableComponent(txtMonto);
        txtFecha.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFecha_keyReleased(e);
                }
            });
        txtFecha.setLengthText(10);
        lblTitle.setText("Ingrese los datos de la transacci�n a anular");
        lblTitle.setBounds(new Rectangle(25, 0, 260, 25));
        lblTitle.setFocusable(false);
        cmbTipoTarjeta.setBounds(new Rectangle(160, 30, 180, 20));
        cmbTipoTarjeta.setNextFocusableComponent(txtNumAutorizacion);
        cmbTipoTarjeta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTipoTarjeta_keyPressed(e);
                }
            });
        cmbTipoTarjeta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cmbTipoTarjeta_actionPerformed(e);
                }
            });
        lblMonto.setText("Monto:");
        lblMonto.setBounds(new Rectangle(20, 175, 120, 20));
        lblMonto.setFocusable(false);
        txtMonto.setBounds(new Rectangle(160, 175, 120, 20));
        txtMonto.setNextFocusableComponent(cmbTipoTarjeta);
        txtMonto.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    txtMonto_keyTyped(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtMonto_keyPressed(e);
                }
            });
        cmbTipoTarjeta.addItem("Visa");
        cmbTipoTarjeta.addItem("Mastercard");
        lblF11.setText("[ F11 ] Anular");
        lblF11.setBounds(new Rectangle(5, 270, 105, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(jPanel1, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        jPanel1.add(txtMonto, null);
        jPanel1.add(lblMonto, null);
        jPanel1.add(cmbTipoTarjeta, null);
        jPanel1.add(txtFecha, null);
        jPanel1.add(txtNumReferencia, null);
        jPanel1.add(txtNumAutorizacion, null);
        jPanel1.add(lblFecha, null);
        jPanel1.add(lblNumReferencia, null);
        jPanel1.add(lblNumAutorizacion, null);
        jPanel1.add(lblTipoTarjeta, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }
    
    private void chkkeyPressed(KeyEvent e)
    {   if(lblF11.isEnabled() && UtilityPtoVenta.verificaVK_F11(e))
        {
            anular();
        }
        if(lblEsc.isEnabled() && e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
        if(lblEsc.isEnabled() && e.getKeyCode()==KeyEvent.VK_ENTER)
        {   ((JComponent)e.getSource()).transferFocus();
        }
    }
    
    private void anular()
    {
        //18-Nov-2013 por recordarnos si concilia o no.
        if(validarCampos())
        {   //se establece la forma de pago
            String tipoTarj = "";
            if("Visa".equals(cmbTipoTarjeta.getSelectedItem().toString()))
            {   formaPago = ConstantsPtoVenta.FORPAG_VISA_PINPAD;
                tipoTarj="V";
            }
            else if("Mastercard".equals(cmbTipoTarjeta.getSelectedItem().toString()))
            {   formaPago = ConstantsPtoVenta.FORPAG_MC_PINPAD;
                tipoTarj="M";
            }
            else
            {   formaPago = "";
                tipoTarj="";
            }

            //Se verifica si la transacci�n pertenece a un pedido en estado COBRADO
            //consultar la transaccion en BD
            HashMap<String,String> pResulta = new HashMap<String,String>();
            DBPinpad.obtenerMontoTransaccion(tipoTarj, 
                                            txtNumReferencia.getText(), 
                                            txtNumAutorizacion.getText(), 
                                            txtFecha.getText(),
                                            pResulta);
            
            
            if( pResulta != null &&
                 "C".equalsIgnoreCase(pResulta.get("EST_PED_VTA")))
            {
                FarmaUtility.showMessage(this, "ATENCI�N: No se puede anular la transacci�n debido a que posee un pedido cobrado en el sistema\n" +
                                                "Realizar la anulaci�n de pedido en la opci�n: Caja -> Anular Ventas -> Pedido Completo", null);
                //interfazAnulacion();
            }else if( pResulta != null &&
                 "S".equalsIgnoreCase(pResulta.get("EST_PED_VTA"))){
                FarmaUtility.showMessage(this, "El pedido se encuentra pendiene de impresi�n.", null);
            }else
            {
                //llamar a la pantalla de anulacion
                //ERIOS 2.3.1 Se envia monto
                if(pResulta.get("MONTO")!=null && !"FALSE".equals(pResulta.get("MONTO")))
                {   
                    interfazAnulacion(pResulta.get("MONTO"),
                                      txtFecha.getText(),
                                      txtNumReferencia.getText(),
                                      pResulta.get("COD_FORMA_PAGO"),
                                      pResulta.get("NUM_PED_VTA"));
                }
                //LLEIVA 08-Abr-2014 Cambio solicitado Rolando Castro, si no existe una registro de transacci�n
                //                   muestra mensaje y continua con la anulaci�n
                else if(pResulta.get("MONTO")==null || "FALSE".equals(pResulta.get("MONTO")))
                {   
                    if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                        "ATENCI�N:\n"+
                        "El n�mero de autorizaci�n (AP) "+txtNumAutorizacion.getText()+" y Referencia (RF) "+txtNumReferencia.getText()+
                            " ingresado NO se encuentra registrado en el sistema. Verifique.\n"+
                        "�EST� SEGURO DE CONTINUAR?")){
                        interfazAnulacion(txtMonto.getText(),
                                          txtFecha.getText(),
                                          txtNumReferencia.getText(),
                                          formaPago,
                                          "");
                    }                    
                }
                else
                    FarmaUtility.showMessage(this, "Los datos ingresados no corresponden a una transacci�n", null);
            }
        }
    }
    
    private void interfazAnulacion(String monto, String fecha, String numRef, String formaPago, String pNumPedido)
    {
        DlgAnularTransPinpad dlgAnularTransPinpad = new DlgAnularTransPinpad(myParentFrame,"",true);
        dlgAnularTransPinpad.setValores(pNumPedido, monto, fecha, numRef, formaPago, false);
        dlgAnularTransPinpad.setVisible(true);
        if(FarmaVariables.vAceptar)
        {   cerrarVentana(true);
        }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {   FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private boolean validarCampos()
    {
        boolean flag=true;
        if(!txtNumAutorizacion.getText().trim().equals("") &&
            !txtNumReferencia.getText().trim().equals("") &&
            !txtFecha.getText().trim().equals(""))
        {   
            
            if("Mastercard".equals(cmbTipoTarjeta.getSelectedItem().toString()) && 
                txtMonto.getText().trim().equals(""))
            {   
                FarmaUtility.showMessage(this, 
                                    "ERROR: Alguno de los campos se encuentra vacio", 
                                    null);
                flag=false;
            }
            Calendar fecha_ingresada = Calendar.getInstance();
            try
            {   fecha_ingresada.setTime(FarmaUtility.getStringToDate(txtFecha.getText().trim(),
                                                                     "dd/MM/yyyy"));
            }
            catch(Exception ex)
            {   //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha ingresada no es valida", null);
                flag=false;
            }
        }
        else
        {   FarmaUtility.showMessage(this, 
                                    "ERROR: Alguno de los campos se encuentra vacio", 
                                    null);
            flag=false;
        }
        return flag;
    }

    private void txtMonto_keyTyped(KeyEvent e)
    {   FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }
    
    private void cmbTipoTarjeta_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }

    private void txtNumAutorizacion_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }

    private void txtNumReferencia_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }
    
    private void txtFecha_keyPressed(KeyEvent e)
    {   
        chkkeyPressed(e);
    }
    
    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }
    
    private void txtNumAutorizacion_keyTyped(KeyEvent e)
    {   //FarmaUtility.admitirDigitos(txtNumAutorizacion, e);
    }

    private void txtNumReferencia_keyTyped(KeyEvent e)
    {   FarmaUtility.admitirDigitos(txtNumReferencia, e);
    }

    private void cmbTipoTarjeta_actionPerformed(ActionEvent e)
    {
        if("Visa".equals(cmbTipoTarjeta.getSelectedItem().toString()))
        {   
            lblMonto.setVisible(false);
            txtMonto.setVisible(false);
        }
        else if("Mastercard".equals(cmbTipoTarjeta.getSelectedItem().toString()))
        {
            lblMonto.setVisible(true);
            txtMonto.setVisible(true);
        }
    }

    private void txtMonto_keyPressed(KeyEvent e)
    {
        chkkeyPressed(e);
    }
}
