package venta.pinpad;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.VariablesCaja;
import venta.delivery.reference.ConstantsDelivery;
import venta.pinpad.mastercard.HiloProcesoPinpadVentaMC;
import venta.pinpad.mastercard.ManejadorTramaRetornoMC;
import venta.pinpad.reference.ConstantsPinpad;
import venta.pinpad.reference.DBPinpad;
import venta.pinpad.visa.ManejadorTramaRetorno;
import venta.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListadoPedidoPinpad extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgListadoPedidoPinpad.class);
    
    private FarmaTableModel tableModelPedidoPinpad;
    private JTable tblListaPedidoPinpad = new JTable();
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFinal = new JTextFieldSanSerif();
    private JButton btnBusqueda = new JButton();
    private JLabelWhite lblFechaBusq = new JLabelWhite();
    private JLabelWhite lblListado = new JLabelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();

    public DlgListadoPedidoPinpad() {
        this(null, "", false);
    }

    public DlgListadoPedidoPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(698, 436));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Listado de Pedidos con transacciones de Pinpad");
        pnlFondo.setFocusable(false);
        pnlBusqueda.setBounds(new Rectangle(10, 15, 675, 60));
        pnlBusqueda.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 85, 675, 25));
        pnlTitle.setFocusable(false);
        lblEsc.setText("[ Esc] Cerrar");
        lblEsc.setBounds(new Rectangle(580, 380, 105, 25));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 110, 675, 265));
        jScrollPane1.setFocusable(false);
        jScrollPane1.getViewport().add(tblListaPedidoPinpad, null);
        txtFechaInicial.setBounds(new Rectangle(125, 20, 110, 20));
        txtFechaInicial.setNextFocusableComponent(txtFechaFinal);
        txtFechaInicial.setLengthText(10);
        txtFechaInicial.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaInicial_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaInicial_keyReleased(e);
                }
            });
        txtFechaFinal.setBounds(new Rectangle(265, 20, 100, 20));
        txtFechaFinal.setNextFocusableComponent(btnBusqueda);
        txtFechaFinal.setLengthText(10);
        txtFechaFinal.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaFinal_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaFinal_keyReleased(e);
                }
            });
        btnBusqueda.setText("Buscar");
        btnBusqueda.setBounds(new Rectangle(395, 20, 90, 20));
        btnBusqueda.setDisplayedMnemonicIndex(0);
        btnBusqueda.setMnemonic('b');
        btnBusqueda.setNextFocusableComponent(txtFechaInicial);
        btnBusqueda.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnBusqueda_actionPerformed(e);
                }
            });
        btnBusqueda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnBusqueda_keyPressed(e);
                }
            });
        lblFechaBusq.setText("Fecha:");
        lblFechaBusq.setBounds(new Rectangle(55, 20, 70, 20));
        lblFechaBusq.setFocusable(false);
        lblListado.setText("Pedidos con transacciones de Pinpad");
        lblListado.setBounds(new Rectangle(15, 0, 320, 25));
        lblListado.setFocusable(false);
        lblF11.setText("[ F11 ] Reimprimir voucher");
        lblF11.setBounds(new Rectangle(10, 380, 175, 25));
        lblF11.setFocusable(false);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(jScrollPane1, null);
        pnlFondo.add(lblEsc, null);
        pnlTitle.add(lblListado, null);
        pnlFondo.add(pnlTitle, null);
        pnlBusqueda.add(lblFechaBusq, null);
        pnlBusqueda.add(btnBusqueda, null);
        pnlBusqueda.add(txtFechaFinal, null);
        pnlBusqueda.add(txtFechaInicial, null);
        pnlFondo.add(pnlBusqueda, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void initialize()
    {
        tableModelPedidoPinpad = new FarmaTableModel(ConstantsPinpad.columnsListaPedidoPinpad,
                                                     ConstantsPinpad.defaultValuesListaPedidoPinpad,
                                                     0);
        FarmaUtility.initSimpleList(tblListaPedidoPinpad, 
                                    tableModelPedidoPinpad, 
                                    ConstantsPinpad.columnsListaPedidoPinpad);
    }

    private void txtFechaInicial_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaFinal_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    private void btnBusqueda_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void btnBusqueda_actionPerformed(ActionEvent e)
    {   busqueda();
        ((JComponent)e.getSource()).transferFocus();
    }
    
    private void chkKeyPressed(KeyEvent e)
    {   
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblListaPedidoPinpad, 
                                              null, 
                                              0);

        if (UtilityPtoVenta.verificaVK_F11(e))
        {   //imprime voucher
            String oper = FarmaUtility.getValueFieldJTable(tblListaPedidoPinpad, 
                                                            tblListaPedidoPinpad.getSelectedRow(), 
                                                            7);
            String ped  = FarmaUtility.getValueFieldJTable(tblListaPedidoPinpad, 
                                                            tblListaPedidoPinpad.getSelectedRow(), 
                                                            0);
            imprimirVoucher(oper, ped);
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   //realizar busquedas
            if(e.getSource()==btnBusqueda)
            {   busqueda();
            }
            ((JComponent)e.getSource()).transferFocus();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void imprimirVoucher(String operador, String pedido)
    {   String textoImprOrig = "";
        String textoImprCopia = "";
        if("MC".equalsIgnoreCase(operador))
        {   
            ManejadorTramaRetornoMC mtrm = new ManejadorTramaRetornoMC();
            textoImprOrig = DBPinpad.impVoucherTrans("M",pedido,"O");
            //textoImprCopia = DBPinpad.impVoucherTrans("M",pedido,"C");
            
            log.debug(textoImprOrig);
            mtrm.imprVoucher( textoImprOrig );
            
            //se imprimen los vouchers copia
            //log.debug(textoImprCopia);
            //mtrm.imprVoucher( textoImprCopia );
        }
        else if("VISA".equalsIgnoreCase(operador))
        {   //se imprimen los vouchers original
            ManejadorTramaRetorno mtr = new ManejadorTramaRetorno();
            
            textoImprOrig = DBPinpad.impVoucherTrans("V",pedido,"O");
            textoImprCopia = DBPinpad.impVoucherTrans("V",pedido,"C");
            
            log.debug(textoImprOrig);
            mtr.imprVoucher( textoImprOrig );
            
            //se imprimen los vouchers copia
            log.debug(textoImprCopia);
            mtr.imprVoucher( textoImprCopia );
        }
        FarmaUtility.showMessage(this, "Se envio a reimprimir el voucher", null);
    }
    
    private void busqueda()
    {   if(validar())
        {   DBPinpad.listaPedidoPinpad(tableModelPedidoPinpad, 
                                        txtFechaInicial.getText(), 
                                        txtFechaFinal.getText());
            if(tableModelPedidoPinpad.getRowCount()>0)
                tblListaPedidoPinpad.setRowSelectionInterval(0, 0);
        }
    }
    
    private boolean validar()
    {
        boolean flag=true;
        if (!"".equals(txtFechaInicial.getText().trim()) &&
            !"".equals(txtFechaFinal.getText().trim()))
        {   
            Calendar fecha_ini = Calendar.getInstance();
            Calendar fecha_fin = Calendar.getInstance();
            try
            {   fecha_ini.setTime(FarmaUtility.getStringToDate(txtFechaInicial.getText().trim(),
                                                                     "dd/MM/yyyy"));
            }
            catch(Exception ex)
            {   //si no se puede obtener un Date, la fecha es incorrecta
                flag = false;
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial no es valida", null);
            }
            if(flag)
            {   try
                {   fecha_fin.setTime(FarmaUtility.getStringToDate(txtFechaFinal.getText().trim(),
                                                                         "dd/MM/yyyy"));
                }
                catch(Exception ex)
                {   //si no se puede obtener un Date, la fecha es incorrecta
                    flag = false;
                    FarmaUtility.showMessage(this, "ERROR: La fecha final no es valida", null);
                }
            }
            if(flag)
            {   if(FarmaUtility.diferenciaEnDias(fecha_ini, fecha_fin) > 0)
                {   flag=false;
                    FarmaUtility.showMessage(this, 
                                            "ERROR: La fecha final debe ser mayor a la fecha inicial", 
                                            null);
                }
            }
        }
        return flag;
    }

    private void txtFechaInicial_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaInicial,e);
    }

    private void txtFechaFinal_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaFinal,e);
    }
}
