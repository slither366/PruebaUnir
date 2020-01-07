package venta.caja;

import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;
import venta.caja.reference.ConstantsSobres;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesSobre;
import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilitySobres;

import venta.reference.UtilityPtoVenta;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesSobre;
import venta.ce.reference.ConstantsCajaElectronica;
import venta.ce.reference.DBCajaElectronica;
import venta.ce.reference.UtilityCajaElectronica;
import venta.ce.reference.VariablesCajaElectronica;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.modulo_venta.reference.DBModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgIngresoSobreParcial.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * DUBILLUZ    07.06.2010   Creaci�n<br>
 * <br>
 * @AUTHOR Diego Armando Ubilluz Carrillo<br>
 * @VERSION 2.0<br>
 *
 */
public class DlgIngresoSobreParcial extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoSobreParcial.class);

  private FarmaTableModel tableModelLista;
  private Frame myParentFrame;

  private final int COL_FEC_REM = 0;
  private final int COL_NUM_REM = 1;
  
  private final int COL_ORDEN = 8;
                                
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblSobres = new JTable();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblRegistros = new JLabel();


    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JLabel lblRegistros1 = new JLabel();
    private JComboBox cmbMoneda = new JComboBox();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblTotal = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange lblNomCajero = new JLabelOrange();
    private JLabelOrange jLabelOrange3 = new JLabelOrange();
    private JLabelOrange lblTipoCambio = new JLabelOrange();
    private JLabelOrange jLabelOrange5 = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JLabelOrange jLabelOrange7 = new JLabelOrange();
    private JLabelOrange lblCaja = new JLabelOrange();
    private JLabelWhite T_lblTotalVenta = new JLabelWhite();
    private JLabelWhite lblTotalVenta = new JLabelWhite();

    public DlgIngresoSobreParcial()
  {
    this(null, "", false);
  }

  public DlgIngresoSobreParcial(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(591, 159));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Ingreso Sobres Parciales");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 570, 90));
        pnlCriterioBusqueda.setBackground(SystemColor.activeCaptionText);
        pnlTitulo.setBounds(new Rectangle(5, 145, 570, 20));
    pnlResultados.setBounds(new Rectangle(5, 265, 570, 20));
    jScrollPane1.setBounds(new Rectangle(5, 165, 570, 100));
    tblSobres.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblVentasVendedor_keyPressed(e);
        }
      });
    btnPeriodo.setText("Monto :");
    btnPeriodo.setBounds(new Rectangle(15, 55, 60, 20));
    btnPeriodo.setMnemonic('p');
        btnPeriodo.setForeground(new Color(0, 114, 169));
        btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtMonto.setBounds(new Rectangle(80, 55, 110, 20));
    txtMonto.setLengthText(6);
        txtMonto.setText("0.00");
        txtMonto.addKeyListener(new KeyAdapter()
      {
                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }
                });
        btnListado.setText("Listado de Sobres :");
    btnListado.setBounds(new Rectangle(5, 0, 345, 20));
    btnListado.setMnemonic('l');
        btnListado.setToolTipText("Listado de Sobres");
        btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnListado_actionPerformed(e);
                    }
      });
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(445, 0, 65, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
    lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(515, 0, 30, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);


        lblEsc.setBounds(new Rectangle(480, 100, 90, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
    //lblF10.setText("[ F8 ]Guardar Archivo");
    lblRegistros1.setText("0");
    lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
    lblRegistros1.setFont(new Font("SansSerif", 1, 11));
    lblRegistros1.setForeground(Color.white);
    lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        //jContentPane.add(lblF10, null);
        cmbMoneda.setBounds(new Rectangle(80, 30, 110, 20));
        cmbMoneda.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbMoneda_keyPressed(e);
                    }
                });
        jLabelWhite1.setText("Total Ingreso :  S/.");
        jLabelWhite1.setBounds(new Rectangle(250, 0, 105, 20));
        jLabelWhite1.setToolTipText("Total Ingreso");
        lblTotal.setText("0");
        lblTotal.setBounds(new Rectangle(360, 0, 70, 20));
        jLabelFunction1.setBounds(new Rectangle(355, 100, 115, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        jButtonLabel1.setText("Moneda :");
        jButtonLabel1.setBounds(new Rectangle(15, 30, 60, 20));
        jButtonLabel1.setMnemonic('M');
        jButtonLabel1.setForeground(new Color(0, 114, 169));
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jLabelOrange1.setText("Cajero :");
        jLabelOrange1.setBounds(new Rectangle(15, 5, 65, 20));
        lblNomCajero.setText(".");
        lblNomCajero.setBounds(new Rectangle(80, 0, 150, 30));
        jLabelOrange3.setText("Tipo Cambio :");
        jLabelOrange3.setBounds(new Rectangle(230, 30, 80, 20));
        lblTipoCambio.setText(".");
        lblTipoCambio.setBounds(new Rectangle(320, 30, 60, 20));
        jLabelOrange5.setText("Fecha :");
        jLabelOrange5.setBounds(new Rectangle(230, 55, 55, 20));
        lblFecha.setText(".");
        lblFecha.setBounds(new Rectangle(320, 55, 115, 20));
        jLabelOrange7.setText("Mov Caja :");
        jLabelOrange7.setBounds(new Rectangle(220, 5, 60, 20));
        lblCaja.setText("1");
        lblCaja.setBounds(new Rectangle(280, 10, 75, 15));
        T_lblTotalVenta.setText("Total Venta Acumulada : S/.");
        T_lblTotalVenta.setBounds(new Rectangle(5, 0, 155, 20));
        T_lblTotalVenta.setToolTipText("Total Venta Acumulada : S/.");
        lblTotalVenta.setText("0");
        lblTotalVenta.setBounds(new Rectangle(165, 0, 80, 20));
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jScrollPane1.getViewport().add(tblSobres, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        jContentPane.add(pnlTitulo, null);
        pnlTitulo.add(btnListado, null);
        pnlTitulo.add(lblRegistros, null);
        pnlTitulo.add(lblRegsitros_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlResultados.add(lblTotalVenta, null);
        pnlResultados.add(T_lblTotalVenta, null);
        pnlResultados.add(lblTotal, null);
        pnlResultados.add(jLabelWhite1, null);
        pnlResultados.add(lblRegistros1, null);
        pnlCriterioBusqueda.add(lblCaja, null);
        pnlCriterioBusqueda.add(jLabelOrange7, null);
        pnlCriterioBusqueda.add(lblFecha, null);
        pnlCriterioBusqueda.add(jLabelOrange5, null);
        pnlCriterioBusqueda.add(lblTipoCambio, null);
        pnlCriterioBusqueda.add(jLabelOrange3, null);
        pnlCriterioBusqueda.add(lblNomCajero, null);
        pnlCriterioBusqueda.add(jLabelOrange1, null);
        pnlCriterioBusqueda.add(jButtonLabel1, null);
        pnlCriterioBusqueda.add(cmbMoneda, null);
        pnlCriterioBusqueda.add(txtMonto, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
    initTableListaVentasVendedor();
    lblTotalVenta.setVisible(false); //INI ASOSA, 03.06.2010
    T_lblTotalVenta.setVisible(false); //FIN ASOSA, 03.06.2010
  }

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaVentasVendedor()
  {
      

      tblSobres.getTableHeader().setReorderingAllowed(false);
      tblSobres.getTableHeader().setResizingAllowed(false);
      
      tableModelLista = new FarmaTableModel(ConstantsCaja.columnsListaSobresTmp,ConstantsCaja.defaultValuesListaSobresTmp,0);
      FarmaUtility.initSimpleList(tblSobres,tableModelLista,ConstantsCaja.columnsListaSobresTmp);
      cargaCombo();
      log.info("Antes del metodo");
      VariablesSobre.vValTipoCambioPedido = FarmaUtility.formatNumber(UtilitySobres.getTipodeCambio());
      log.info("Final");
    }

    private void cargaCombo()
    {
      FarmaLoadCVL.loadCVLfromArrays(cmbMoneda,ConstantsSobres.HASHTABLE_MONEDASOBRES,
                                        ConstantsSobres.MONEDAS_COD,
                                        ConstantsSobres.MONEDAS_DESC,true);
    } 
  
    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        
        //ERIOS 15.01.2014 Valida Caja abierta
        if(!UtilityCaja.existeCajaUsuarioImpresora(null,null)){
            cerrarVentana(false);
            return;
        }
        
        //LLEIVA 03-Ene-2014 Si el tipo de cambio es cero, no permitir continuar
        if(FarmaVariables.vTipCambio==0)
        {   FarmaUtility.showMessage(this, 
                                  "ATENCI�N: No se pudo obtener el tipo de cambio actual\nNo se puede continuar con la acci�n", 
                                  null);
            cerrarVentana(false);
        }
        else
        {   
            FarmaUtility.moveFocus(cmbMoneda);
            lblRegistros.setText(tblSobres.getRowCount()+"");
            lblTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblSobres,4)));
            lblNomCajero.setText(FarmaVariables.vIdUsu);
            lblCaja.setText(VariablesSobre.vSecMovCaja);
            lblTipoCambio.setText(VariablesSobre.vValTipoCambioPedido);
            log.info("VariablesCaja.vValTipoCambioPedido:"+VariablesCaja.vValTipoCambioPedido);
            //log.info("FarmaVariables.vTipCambio:"+FarmaVariables.vTipCambio);      
            cargarFecha();
            T_lblTotalVenta.setVisible(false);
            lblTotalVenta.setVisible(false);
            
            if(Double.parseDouble(VariablesSobre.vValTipoCambioPedido)<=0)
            {   FarmaUtility.showMessage(this, "No se puede obtener el tipo de cambio Correcto.", null);
                cerrarVentana(false);
            }
        }
    }
  
  
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */

  private void tblVentasVendedor_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

    private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtMonto);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblSobres.getRowCount() > 0)
    {
      FarmaUtility.moveFocus(tblSobres);
    }
  }
    
    private void this_windowClosing(WindowEvent e)
    {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
  
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    if(UtilityPtoVenta.verificaVK_F11(e)){
        agregaSobreParcial();
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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
    
    private  void cargarFecha(){
      try{
        String FechaInicio=FarmaSearch.getFechaHoraBD(2);
        lblFecha.setText(FechaInicio);
      }catch(SQLException sql){
        log.error("",sql);
      }
    }
    
    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    
    }
    
    private void cmbMoneda_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                VariablesSobre.vCodTipoMon = FarmaLoadCVL.getCVLCode(ConstantsSobres.HASHTABLE_MONEDASOBRES,cmbMoneda.getSelectedIndex());
                VariablesSobre.vDescTipoMon = FarmaLoadCVL.getCVLDescription(ConstantsSobres.HASHTABLE_MONEDASOBRES, VariablesSobre.vCodTipoMon);
                FarmaUtility.moveFocus(txtMonto);
                log.debug("COD TIPO MONEDA-->"+VariablesSobre.vCodTipoMon);
                log.debug("DESC TIPO MONEDA-->"+VariablesSobre.vDescTipoMon);
            }
            else
              chkKeyPressed(e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
    
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            agregaSobreParcial();            
        } else
          chkKeyPressed(e);  
    
    }
    
    private boolean ingresoSobre(){
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblSobres);
          return false;
        }
        return adicionaDetallePago();
    }
    
  private boolean adicionaDetallePago()
  {
    if (validaMontoIngresado()) {
        if (obtieneDatosFormaPago()) {
            return true;
        }
        else{
            return false;
        }
    }
    else{
        return false;
    }
        
  }
    
    
    private boolean obtieneDatosFormaPago() {
        //ERIOS 04.02.2014 Se lee combo de moneda
        boolean valor = true;
        log.debug("*****OBTIENE DATOS PARA INGRESAR");
        VariablesSobre.vCodTipoMon = 
            FarmaLoadCVL.getCVLCode(ConstantsSobres.HASHTABLE_MONEDASOBRES, 
                                    cmbMoneda.getSelectedIndex());
        if (VariablesSobre.vCodTipoMon.equalsIgnoreCase(ConstantsCaja.EFECTIVO_SOLES)) {
            VariablesSobre.vCodFormaPagoTmp = 
                    ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES;
            VariablesSobre.vDescFormaPagoTmp = "EFECTIVO SOLES";
        } else if (VariablesSobre.vCodTipoMon.equalsIgnoreCase(ConstantsCaja.EFECTIVO_DOLARES)) {
            VariablesSobre.vCodFormaPagoTmp = 
                    ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES;
            VariablesSobre.vDescFormaPagoTmp = "EFECTIVO DOLARES";
        }
        
        
        log.debug(" DUBILLUZ ::::txtMonto.getText()" + 
                           FarmaUtility.getDecimalNumber(txtMonto.getText().trim()));
        //VariablesCaja.vValMontoPagadoTmp = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(txtMonto.getText().trim()));
        VariablesSobre.vValMontoPagadoTmp = 
                FarmaUtility.getDecimalNumber(txtMonto.getText().trim()) + "";
        log.debug("JCORTEZ :::: VariablesSobre.vValMontoPagadoTmp" + 
                           VariablesSobre.vValMontoPagadoTmp);

        if (VariablesSobre.vCodTipoMon.equalsIgnoreCase(FarmaConstants.CODIGO_MONEDA_SOLES)){
            VariablesSobre.vValTotalPagadoTmp = 
                    VariablesSobre.vValMontoPagadoTmp;
            log.debug("ingreso soles");
        }
        else{
            VariablesSobre.vValTotalPagadoTmp = 
                    FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesSobre.vValMontoPagadoTmp) * 
                                              FarmaUtility.getDecimalNumber(VariablesSobre.vValTipoCambioPedido));
            log.debug("dolares");
            log.debug("FarmaUtility.getDecimalNumber(VariablesSobre.vValMontoPagadoTmp):"+FarmaUtility.getDecimalNumber(VariablesSobre.vValMontoPagadoTmp));
            log.debug("FarmaUtility.getDecimalNumber(VariablesSobre.vValTipoCambioPedido):"+FarmaUtility.getDecimalNumber(VariablesSobre.vValTipoCambioPedido));
        }


        log.debug(" 1.VariablesSobre.vCodFormaPagoTmp" + 
                           VariablesSobre.vCodFormaPagoTmp);
        log.debug("2.VariablesSobre.vIndTarjetaSeleccionada" + 
                           VariablesSobre.vDescFormaPagoTmp);
        
        log.debug("5.VariablesSobre.vValMontoPagadoTmp" + 
                           VariablesSobre.vValMontoPagadoTmp);
        log.debug("6.VariablesSobre.vValTotalPagadoTmp" + 
                           VariablesSobre.vValTotalPagadoTmp);

        //validar monto ingresado no mayor al existente en ventas
        try {
            String sTotal = lblTotal.getText().trim();
            String TotalVentas = 
                DBCaja.getMontoVentas(VariablesSobre.vSecMovCaja);

            T_lblTotalVenta.setVisible(true);
            lblTotalVenta.setVisible(true);
            lblTotalVenta.setText(TotalVentas);

            double dTotal = Double.parseDouble(sTotal);
            //double dIngreso = Double.parseDouble(VariablesSobre.vValTotalPagadoTmp);
            double dIngreso = FarmaUtility.getDecimalNumber(VariablesSobre.vValTotalPagadoTmp); //ASOSA, 16.06.2010
            log.debug("TotalVentas: " + TotalVentas);
            double dVentas = FarmaUtility.getDecimalNumber(TotalVentas.trim());
            log.debug("Ventas turno: " + dVentas);
            log.debug("Total Acumulado: " + dTotal);
            log.debug("Total Ingreso: " + dIngreso);
            if ((dTotal + dIngreso) > (dVentas)) {
                // NO DEBE DE VALIDAR NADA
                // dubilluz
                /*
                valor = false;
                FarmaUtility.showMessage(this, 
                                         "El monto total acumulado supera las ventas realizadas. Verifique!!!", 
                                         txtMonto);
                */
                valor = true;
            }
        } catch (SQLException e) {
            log.error("",e);
        }

        return valor;
    }
    
    private boolean validaMontoIngresado()
    {
      //log.debug("VariablesSobre.vIndTarjetaSeleccionada valida monto : " + VariablesSobre.vIndTarjetaSeleccionada);
      String monto = txtMonto.getText().trim();
      
      if(monto.equalsIgnoreCase("") || monto.length() <= 0)
      {
        FarmaUtility.showMessage(this, "Ingrese un monto.", monto);
        return false;
      }
      if(!FarmaUtility.isDouble(monto))
      {
        FarmaUtility.showMessage(this, "Ingrese un monto v�lido.", monto);
        return false;
      }
      if(FarmaUtility.getDecimalNumber(monto) == 0)
      {
        FarmaUtility.showMessage(this, "Ingrese un monto mayor a 0.", monto);
        return false;
      }
      if(FarmaUtility.getDecimalNumber(monto) < 0)
      {
         FarmaUtility.showMessage(this, "El monto ingresado es negativo.", monto);
          return false;
      }

      return true;
    }
    
    private void agregaSobreParcial() {
        if(!validaMontoIngresado()){
            return;
        }
        int cant = tblSobres.getRowCount();
        if (validaIngresoSobre() || cant < 1) {
          if(JConfirmDialog.rptaConfirmDialog(this,"�Esta seguro de agregar el sobre?"))
          {
            if (cargaValidaLogin()) {
                if(ingresoSobre()){
                   lblRegistros.setText(tblSobres.getRowCount() + "");
                   cerrarVentana(true);
                }
            }
            else {
                FarmaUtility.showMessage(this, "Necesita autorizaci�n del Jefe del Local.", 
                                         cmbMoneda);
            }
          }
        } else
            FarmaUtility.showMessage(this, "No puede agregar m�s sobres.", 
                                     cmbMoneda);
    }
    
    
    private void jButtonLabel1_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(cmbMoneda);
    }

    private boolean validaIngresoSobre() {
      boolean valor=false;
      String ind="";
      try
           {
           log.debug("VariablesSobre.vSecMovCaja-->"+VariablesSobre.vSecMovCaja);
            ind=DBCaja.permiteIngreMasSobre();
               log.debug("indPermite mas de un Sobres-->"+ind);
            if(ind.trim().equalsIgnoreCase("S")){
             valor=true;
             }
            
           }catch (SQLException sql){
             valor=false;
             log.error("",sql);
             FarmaUtility.showMessage(this,"Ocurrio un error validar ingreso de sobre.\n"+sql.getMessage(),null);
           }
      return valor;
    }
    
    private boolean cargaValidaLogin()
    {
      VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = "";  
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      boolean  rpta=false;
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        VariablesCajaElectronica.pSecUsu_APRUEBA_SOBRE = FarmaVariables.vNuSecUsu;
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
          rpta=FarmaVariables.vAceptar;
         // return rpta;
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        rpta = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return rpta;
    }
}
