package venta.ce;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.*;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import common.*;

import venta.DlgListaMaestros;
import venta.ce.reference.ConstantsCajaElectronica;
import venta.ce.reference.DBCajaElectronica;
import venta.ce.reference.VariablesCajaElectronica;
import venta.cliente.reference.ConstantsCliente;
import venta.cliente.reference.DBCliente;
import venta.reference.*;
import venta.caja.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgDatosEfectivoRendido.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ERIOS      17.08.2006   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosEfectivoRendido
  extends JDialog
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgDatosEfectivoRendido.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private FarmaRowEditorModel rowEditorModel;
  private DefaultCellEditor defaultCellEditor;
  private ArrayList aCampos = new ArrayList();
  private FarmaJTable tblLista = new FarmaJTable();
  
  double monto, montoParcial, montoPerdido;
  String fechaOperacion, fechaEmision,fechaVencimiento, ruc,codAutorizacion;
  
  /* ************************************************************************ */
    /*                               CAMPOS DE LA GRILLA                        */
    /* ************************************************************************ */
    
  //001
  private JComboBox cmbSerie = new JComboBox();
  //002
  private JComboBox cmbTipoComp = new JComboBox();
  private String[] compDescripcion = { "BOLETA","FACTURA","TICKETERA" };
  private String[] compValor = { "01","02","05" };
  //003
  private JTextFieldSanSerif txtNumeroComp = new JTextFieldSanSerif();
  //004
  private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
  //006
  private JComboBox cmbTipoBillete = new JComboBox();
  private String[] compDescTipoBillete = { "BILLETE","MONEDA" };
  private String[] compValTipoBillete = { "01","02" };
  //007
  private JComboBox cmbTipoMoneda = new JComboBox();
  private String[] compDescTipoMoneda = { "SOLES","DOLARES" };
  private String[] compValTipoMoneda = { "01","02" };
  //008
  private JComboBox cmbFormaPago = new JComboBox();
  //009
  private JTextFieldSanSerif txtSerieBillete = new JTextFieldSanSerif();
  //010
  private JComboBox cmbEntidadFinanciera = new JComboBox();
  //011
  //private JComboBox cmbNumeroCuenta = new JComboBox();
  private JTextField txtNumeroCuenta = new JTextField();
  JTextField vCodNumeroCuenta = new JTextField();
  //012
  private JTextFieldSanSerif txtFechaHora = new JTextFieldSanSerif();
  //013
  private JTextFieldSanSerif txtNumeroOperacion = new JTextFieldSanSerif();
  //014
  private JTextFieldSanSerif txtNombreAgencia = new JTextFieldSanSerif();    
  //015
  private JTextFieldSanSerif txtFechaEmision = new JTextFieldSanSerif();
  //016
  private JComboBox cmbNomTitular = new JComboBox();
  private String[] compDescNomTitular = { "MIFARMA","OTRO" };
  private String[] compValNomTitular = { "MIFARMA","OTRO" };
  //017
  private JTextFieldSanSerif txtCodAutorizacion = new JTextFieldSanSerif();
  //018
  private JTextFieldSanSerif txtTrabajador = new JTextFieldSanSerif();
  JTextField vCodTrabajador = new JTextField();
  //019
  private JTextFieldSanSerif txtMotivo = new JTextFieldSanSerif();
  //020
  private JTextFieldSanSerif txtMontoParcial = new JTextFieldSanSerif();
  //021
  private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
  //022
  private JTextFieldSanSerif txtRazonSocial = new JTextFieldSanSerif();
  //023
  private JTextFieldSanSerif txtLocal = new JTextFieldSanSerif();
  JTextField vCodLocal = new JTextField();
  //024
  private JComboBox cmbServicio = new JComboBox();
  //025
  private JTextFieldSanSerif txtTrabajadorLocal = new JTextFieldSanSerif();
  JTextField vCodTrabajadorLocal = new JTextField();
  //026
  private JTextFieldSanSerif txtCajero = new JTextFieldSanSerif();
  JTextField vCodCajero = new JTextField();
  //027
  private JTextFieldSanSerif txtNumeroDocumento = new JTextFieldSanSerif();
    
  private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
  
  private JTextFieldSanSerif txtVuelto = new JTextFieldSanSerif();
  
  private JTextFieldSanSerif txtFechaVencimiento = new JTextFieldSanSerif();
  
  private JTextFieldSanSerif txtMontoPerdido = new JTextFieldSanSerif();
  
  private JTextFieldSanSerif txtNumeroPedido = new JTextFieldSanSerif();
  
  private JTextField txtProveedor = new JTextField();
  JTextField vCodProveedor = new JTextField();
    
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JScrollPane scrLista = new JScrollPane();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnLista = new JButtonLabel();
  private JLabelFunction lblF1 = new JLabelFunction();

    
    private JTextFieldSanSerif txtNombrePersonal = new JTextFieldSanSerif();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgDatosEfectivoRendido()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }

  }

  public DlgDatosEfectivoRendido(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      log.error("",e);
    }
  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit()
    throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(417, 306));
    this.setDefaultCloseOperation(0);
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
    lblEsc.setBounds(new Rectangle(305, 245, 95, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(190, 245, 105, 20));
    lblF11.setText("[ F11 ] Aceptar");
    scrLista.setBounds(new Rectangle(10, 30, 390, 205));
    tblLista.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            tblLista_keyPressed(e);
          }
        });
    pnlTitle1.setBounds(new Rectangle(10, 10, 390, 20));
    btnLista.setText("Lista Datos");
    btnLista.setBounds(new Rectangle(5, 0, 105, 20));
    btnLista.setMnemonic('L');
    btnLista.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnLista_actionPerformed(e);
          }
        });
    lblF1.setBounds(new Rectangle(20, 245, 125, 20));
    lblF1.setText("[ F1 ] Ingresar Dato");
    scrLista.getViewport().add(tblLista, null);
    pnlTitle1.add(btnLista, null);
    jContentPane.add(lblF1, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(scrLista, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
      
      txtNombrePersonal.setLengthText(2000);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    this.setTitle("Datos Efectivo Rendido - " + VariablesCajaElectronica.vDescCuadratura);
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTable()
  {
    
    tableModel = new FarmaTableModel(ConstantsCajaElectronica.columnsListaEfectivoRendidoDat, 
                                    ConstantsCajaElectronica.defaultValuesListaEfectivoRendidoDat, 
                                    0,
                                    ConstantsCajaElectronica.editableListaEfectivoRendidoDat,
                                     null);
    //FarmaUtility.initSimpleList(tblLista, tableModel,ConstantsCajaElectronica.columnsListaCuadraturaDat);
    rowEditorModel = new FarmaRowEditorModel();
    tblLista.setAutoCreateColumnsFromModel(false);
    tblLista.setModel(tableModel);
    tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    for (int k=0; k<tableModel.getColumnCount(); k++) {
      TableColumn column = new TableColumn(k, ConstantsCajaElectronica.columnsListaEfectivoRendidoDat[k].m_width);
      tblLista.addColumn(column);
    }
    tblLista.setRowEditorModel(rowEditorModel);
    cargaCampos();
    
  }

  private void cargaCampos() 
  {
    cargaLista();
    setTipoCampo();
    FarmaUtility.setearPrimerRegistro(tblLista,null,0);
    if ( tblLista.getRowCount()>0 ) {
      for (int i=0; i<tblLista.getRowCount(); i++)
      {
        tblLista.setValueAt(tblLista.getValueAt(i,1).toString().trim(),i,1);
        tblLista.changeSelection(0,1,false,false);
      }
    }
    tblLista.repaint();
  }
    
  private void cargaLista()
  {
    try
    {
      DBCajaElectronica.listaCuadraturaCampos(tableModel);
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,"Error al cargar los campos.",tblLista);
    }
    aCampos = tableModel.data;
  }

  /***Este procedimiento determinar� el tipo de dato de cada campo segun lo registrado en la BD. */
  private void setTipoCampo() 
  {
    String codigoCampo = "";
    for (int i=0; i<tblLista.getRowCount(); i++) {
      codigoCampo = tblLista.getValueAt(i,2).toString().trim();
        if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PERSONAL)) 
          getTxtNombrePersonal(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
     else   
      if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_COMPROBANTE)) 
        cmbSerie(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_COMPROBANTE)) 
        cmbTipoComp(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_COMPROBANTE)) 
        txtNumeroComp(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_IMPORTE)) 
        txtMonto(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_DINERO)) 
        getCmbTipoBillete(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA)) 
        getCmbTipoMoneda(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FORMA_PAGO)) 
        getCmbFormaPago(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_DINERO)) 
        getTxtSerieBillete(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_ENTIDAD_FINANCIERA)) 
        getCmbEntidadFinanciera(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA)) 
        getTxtNumeroCuenta(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION)) 
        getTxtFechaHora(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_OPERACION)) 
        getTxtNumeroOperacion(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_AGENCIA)) 
        getTxtNombreAgencia(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FECHA_DOCUMENTO)) 
        getTxtFechaEmision(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TITULAR)) 
        getCmbNombreTitular(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_CODIGO_AUTORIZACION)) 
        getTxtCodAutorizacion(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TRABAJADOR)) 
        getTxtTrabajador(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_GLOSA)) 
        getTxtMotivo(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_IMPORTE_PARCIAL)) 
        getTxtMontoParcial(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_RUC)) 
        getTxtRuc(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_RAZON_SOCIAL)) 
        getTxtRazonSocial(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_LOCAL_NUEVO)) 
        getTxtLocal(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_SERVICIO)) 
        getCmbServicio(i,(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PERSONAL)) 
        getTxtTrabajadorLocal(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PERSONAL)) 
        getTxtCajero(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_DOCUMENTO)) 
        getTxtNumeroDocumento(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PROVEEDOR)) 
         getTxtProveedor(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FECHA_VENCIMIENTO)) 
        getTxtFechaVencimiento(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_DOCUMENTO)) 
        getTxtSerie(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_DOCUMENTO)) 
      cmbTipoComp(i,(String)tblLista.getValueAt(i,4));//combo provisional esto saldra de un qery
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_VUELTO)) 
        getTxtVuelto(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_MONTO_PERDIDO)) 
        getTxtMontoPerdido(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));
      else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_PEDIDO)) 
        getTxtNumeroPedido(i,(String)tblLista.getValueAt(i,3),(String)tblLista.getValueAt(i,4));        
        
    }
  }
  
  /* ************************************************************************ */
  /* SECCION : CREACION DE CAMPOS JTEXTFIELD                                  */
  /* ************************************************************************ */
  private void getTxtNombrePersonal(int pRow, String pTipoCampo, String pInSoloLectura) {
    createJTextField(txtNombrePersonal,pRow,pTipoCampo,pInSoloLectura);
  }  
  private void getTxtVuelto(int pRow, String pTipoCampo, String pInSoloLectura) {
    createJTextField(txtVuelto,pRow,pTipoCampo,pInSoloLectura);
  }
  private void txtNumeroComp(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroComp.setLengthText(7);
   createJTextField(txtNumeroComp,pRow,pTipoCampo,pInSoloLectura);
  }
  private void txtMonto(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtMonto.setLengthText(10);
    createJTextField(txtMonto,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtSerieBillete(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtSerieBillete.setLengthText(30);
   createJTextField(txtSerieBillete,pRow,pTipoCampo,pInSoloLectura);
  }
  
  private void getTxtNumeroCuenta(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroCuenta.setDocument(new FarmaLengthText(30));
    createJTextField(txtNumeroCuenta,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtFechaHora(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtFechaHora.setLengthText(19);
    createJTextField(txtFechaHora,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtNumeroOperacion(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroOperacion.setLengthText(15);
    createJTextField(txtNumeroOperacion,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtNombreAgencia(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNombreAgencia.setLengthText(120);
    createJTextField(txtNombreAgencia,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtFechaEmision(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtFechaEmision.setLengthText(10);
    createJTextField(txtFechaEmision,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtCodAutorizacion(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtCodAutorizacion.setLengthText(14);
    createJTextField(txtCodAutorizacion,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtTrabajador(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtTrabajador.setEditable(false);
    addKeyListenerToTextField2(txtTrabajador,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_TRABAJADOR,vCodTrabajador);
    defaultCellEditor = new DefaultCellEditor(txtTrabajador);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getTxtMotivo(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtMotivo.setLengthText(120);
    createJTextField(txtMotivo,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtMontoParcial(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtMontoParcial.setLengthText(10);
    createJTextField(txtMontoParcial,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtRuc(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtRuc.setLengthText(11);
    createJTextField(txtRuc,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtRazonSocial(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtRazonSocial.setLengthText(30);
    createJTextField(txtRazonSocial,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtLocal(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtLocal.setEditable(false);
    addKeyListenerToTextField2(txtLocal,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_LOCAL,vCodLocal);
    defaultCellEditor = new DefaultCellEditor(txtLocal);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getTxtTrabajadorLocal(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtTrabajadorLocal.setEditable(false);
    addKeyListenerToTextField2(txtTrabajadorLocal,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL,vCodTrabajadorLocal);
    defaultCellEditor = new DefaultCellEditor(txtTrabajadorLocal);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getTxtCajero(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtCajero.setEditable(false);
    addKeyListenerToTextField2(txtCajero,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL,vCodCajero);
    defaultCellEditor = new DefaultCellEditor(txtCajero);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getTxtProveedor(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtProveedor.setEditable(false);
    addKeyListenerToTextField2(txtProveedor,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_PROVEEDOR_CE,vCodProveedor);
    defaultCellEditor = new DefaultCellEditor(txtProveedor);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  
  private void getTxtNumeroDocumento(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroDocumento.setLengthText(30);
    createJTextField(txtNumeroDocumento,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtSerie(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtSerie.setLengthText(4);
    createJTextField(txtSerie,pRow,pTipoCampo,pInSoloLectura);
  }
  private void getTxtFechaVencimiento(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtFechaVencimiento.setLengthText(10);
    createJTextField(txtFechaVencimiento,pRow,pTipoCampo,pInSoloLectura);
  }
  
  private void getTxtMontoPerdido(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtMontoPerdido.setLengthText(10);
    createJTextField(txtMontoPerdido,pRow,pTipoCampo,pInSoloLectura);
  }
  
  private void getTxtNumeroPedido(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroPedido.setLengthText(10);
    createJTextField(txtNumeroPedido,pRow,pTipoCampo,pInSoloLectura);
  }
  
  
  
  private void createJTextField(JTextField pJTextField,
                                int pRow,
                                String pTipoCampo,
                                String pInSoloLectura) {
    addKeyListenerToTextField(pJTextField,pTipoCampo,pInSoloLectura);
    defaultCellEditor = new DefaultCellEditor(pJTextField);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  
  /* ************************************************************************ */
  /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                   */
  /* ************************************************************************ */

  private void cmbTipoComp(int pRow, String pInSoloLectura) {
   createJComboBox(cmbTipoComp,"CMB_TIPO_COMP",compValor,compDescripcion,pRow,pInSoloLectura);
  }
  private void cmbSerie(int pRow, String pInSoloLectura) 
  {
   ArrayList parametros = new ArrayList();
   parametros.add(FarmaVariables.vCodGrupoCia);
   parametros.add(FarmaVariables.vCodLocal);
   FarmaLoadCVL.loadCVLFromSP(cmbSerie, "CMB_SERIE",
                   "PTOVENTA_CE_ERN.CE_LISTA_SERIES_COMPROBANTE(?,?)", parametros,
                   true, true);
   addKeyListenerToComboBox(cmbSerie,pInSoloLectura);
   defaultCellEditor = new DefaultCellEditor(cmbSerie);
   rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getCmbTipoBillete(int pRow, String pInSoloLectura) {
   createJComboBox(cmbTipoBillete,"CMB_TIPO_BILLETE",compValTipoBillete,compDescTipoBillete,pRow,pInSoloLectura);
  }
  private void getCmbTipoMoneda(int pRow, String pInSoloLectura) {
    FarmaLoadCVL.loadCVLfromArrays(cmbTipoMoneda,
                                   "CMB_TIPO_MONEDA",
                                   compValTipoMoneda,
                                   compDescTipoMoneda,
                                   true);
    addKeyListenerToComboBox(cmbTipoMoneda,pInSoloLectura);
    defaultCellEditor = new DefaultCellEditor(cmbTipoMoneda);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getCmbFormaPago(int pRow, String pInSoloLectura) 
  {
   ArrayList parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
   parametros.add(FarmaVariables.vCodLocal);
   FarmaLoadCVL.loadCVLFromSP(cmbFormaPago, "CMB_FORMA_PAGO",
                   "PTOVENTA_CE_ERN.CE_OBTIENE_FORMAS_PAGO(?,?)", parametros,
                   true, true);
   addKeyListenerToComboBox(cmbFormaPago,pInSoloLectura);
   defaultCellEditor = new DefaultCellEditor(cmbFormaPago);
   rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
   
  private void getCmbEntidadFinanciera(int pRow, String pInSoloLectura) {
    ArrayList parametros = new ArrayList();
    FarmaLoadCVL.loadCVLFromSP(cmbEntidadFinanciera, "CMB_ENTIDAD_FINANCIERA",
                    "PTOVENTA_CE_ERN.CE_OBTIENE_FINANCIERA", parametros,
                    false, true);
    addKeyListenerToComboBox(cmbEntidadFinanciera,pInSoloLectura);
    defaultCellEditor = new DefaultCellEditor(cmbEntidadFinanciera);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  private void getCmbNombreTitular(int pRow, String pInSoloLectura) 
  {
    createJComboBox(cmbNomTitular,"CMB_NOMBRE_TITULAR",compValNomTitular,compDescNomTitular,pRow,pInSoloLectura);
  }
  private void getCmbServicio(int pRow, String pInSoloLectura) 
  {
   ArrayList parametros = new ArrayList();
   FarmaLoadCVL.loadCVLFromSP(cmbServicio, "CMB_SERVICIO",
                   "PTOVENTA_CE_ERN.CE_OBTIENE_SERVICIOS", parametros,
                   false , true);
   addKeyListenerToComboBox(cmbServicio,pInSoloLectura);
   defaultCellEditor = new DefaultCellEditor(cmbServicio);
   rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
    
  private void createJComboBox(JComboBox pJComboBox,
                               String pNameComboBox,
                               String[] pValue,
                               String[] pDescription,
                               int pRow,
                               String pInSoloLectura) {
    FarmaLoadCVL.loadCVLfromArrays(pJComboBox,
                                    pNameComboBox,
                                    pValue,
                                    pDescription,
                                    true);
    addKeyListenerToComboBox(pJComboBox,pInSoloLectura);
    defaultCellEditor = new DefaultCellEditor(pJComboBox);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  
   private void addKeyListenerToTextField(final JTextField pJTextField,
                                          final String pTipoCampo,
                                          final String pInSoloLectura) {
     pJTextField.addKeyListener(new KeyAdapter() {
       public void keyTyped(KeyEvent e) {
         if ( pInSoloLectura.equalsIgnoreCase("S") ) {
           e.consume();
         } else {
           if ( pTipoCampo.equalsIgnoreCase("E") )
             FarmaUtility.admitirDigitos(pJTextField,e);
           else if ( pTipoCampo.equalsIgnoreCase("D") )
             FarmaUtility.admitirDigitosDecimales(pJTextField,e);
         }
       }
       public void keyPressed(KeyEvent e) {
         if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
           e.consume();
           if ( (tblLista.getSelectedRow()+1)==tblLista.getRowCount() )
             FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow(),null,0);
           else  FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow()+1,null,0);
           pJTextField.setText(pJTextField.getText().toUpperCase());
         } else tblLista_keyPressed(e);
       }
       public void keyReleased(KeyEvent e) {
         if ( pTipoCampo.equalsIgnoreCase("F") )
         {
           String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
           if(codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION))
             FarmaUtility.dateHourComplete(pJTextField,e);
           else
             FarmaUtility.dateComplete(pJTextField,e);
         }
         
         if ( venta.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
           pJTextField.selectAll();
         } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
           String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
           if( codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA) ){
            cargaNumeroCuenta();
            txtNumeroCuenta.setText(VariablesPtoVenta.vDescMaestro);
           }
           if(codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PROVEEDOR)){
            log.debug("Entro keyReleased");
            txtProveedor.setText("");
            vCodProveedor.setText("");
          }
           moveFocusTo(tblLista.getSelectedRow());
         } else {
           if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
                e.getKeyCode()!=KeyEvent.VK_RIGHT &&
                e.getKeyCode()!=KeyEvent.VK_DELETE &&
                e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
                e.getKeyCode()!=KeyEvent.VK_HOME &&
                (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
             pJTextField.setText(pJTextField.getText().toUpperCase());
         }
       }
     });
   }
  
  private void addKeyListenerToTextField2(final JTextField pJTextField,
                                         final String pTipoCampo,
                                         final String pInSoloLectura,
                                         final String pTipoMaestro,
                                         final JTextField pCodMaestro) {
    pJTextField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        } else {
          if ( pTipoCampo.equalsIgnoreCase("E") )
            FarmaUtility.admitirDigitos(pJTextField,e);
          else if ( pTipoCampo.equalsIgnoreCase("D") )
            FarmaUtility.admitirDigitosDecimales(pJTextField,e);
        }
      }
      public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          e.consume();
          if ( (tblLista.getSelectedRow()+1)==tblLista.getRowCount() )
            FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow(),null,0);
          else  FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow()+1,null,0);
          pJTextField.setText(pJTextField.getText().toUpperCase());
        } else tblLista_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        if ( venta.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
          pJTextField.selectAll();
          //mostrar dialogo
          VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
          VariablesCajaElectronica.vCodEntidadFinanciera = getCodEntidadFinanciera();
          VariablesCajaElectronica.vCodTipoMoneda = getTipoMoneda();
          if(VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DELIVERY_PERDIDO))
          {
            VariablesCajaElectronica.vCodServicio = ConstantsCajaElectronica.CODIGO_SERVICIO_DELIVERY;  
          } else VariablesCajaElectronica.vCodServicio = getCodServicio();
          log.debug("Tipo Maestro: "+pTipoMaestro);
          /**
           * Se vario el listado de maestro si era el tipo de trabajador como esta en la primera condicion
           * mostraria el codigo de RR.HH, y para el resto la lista de maestro existente
           * @author dubilluz
           * @since  22.11.2007
           */
            if(pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR) ||
               pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL) ||
               pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CAJERO)){
              
              DlgListaTrabajadores dlgListaTrabajadores = new DlgListaTrabajadores(myParentFrame, "", true);
              dlgListaTrabajadores.setVisible(true);
              if(FarmaVariables.vAceptar)
              {
                pCodMaestro.setText(VariablesPtoVenta.vCodMaestro);
                pJTextField.setText(VariablesPtoVenta.vDescMaestro);
                log.debug("Codigo Maestro: "+pCodMaestro.getText());
              }                   

           }
           else
           {
              DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
              dlgListaMaestros.setVisible(true);
              if(FarmaVariables.vAceptar)
              {
                pCodMaestro.setText(VariablesPtoVenta.vCodMaestro);
                pJTextField.setText(VariablesPtoVenta.vDescMaestro);
                log.debug("Codigo Maestro: "+pCodMaestro.getText());
              }             
           }
          
          
        } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          moveFocusTo(tblLista.getSelectedRow());
        } 
        else {
          if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
               e.getKeyCode()!=KeyEvent.VK_RIGHT &&
               e.getKeyCode()!=KeyEvent.VK_DELETE &&
               e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
               e.getKeyCode()!=KeyEvent.VK_HOME &&
               (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
            pJTextField.setText(pJTextField.getText().toUpperCase());
        }
      }
    });
  }
  
  private void moveFocusTo(int pRow) {
    FarmaUtility.setearRegistro(tblLista,pRow,null,0);
    tblLista.changeSelection(pRow,1,false,false);
  }
  
  private void addKeyListenerToComboBox(final JComboBox pJComboBox,
                                        final String pInSoloLectura) {
    pJComboBox.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        }
      }
      public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          if(pJComboBox.isPopupVisible()) pJComboBox.setPopupVisible(false);
          else {
          if ( (tblLista.getSelectedRow()+1)==tblLista.getRowCount() ){
            FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow(),null,0);
            }
            else 
            {
              FarmaUtility.setearRegistro(tblLista,tblLista.getSelectedRow()+1,null,0);
            }
          }
        }
      }
      /*public void keyReleased(KeyEvent e) {
        if ( venta.reference.UtilityPtoVenta.verificaVK_F1(e) && pJComboBox.isDisplayable() ) {
          pJComboBox.setPopupVisible(true);
        } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          if(pJComboBox.isPopupVisible()) {
            pJComboBox.setPopupVisible(false);
          }
          else{
            moveFocusTo(tblLista.getSelectedRow());
          }
        } else if ( e.getKeyCode()==KeyEvent.VK_ESCAPE ) {
          e.consume();
          tblLista_keyPressed(e);
        }
      }*/
      
        public void keyReleased(KeyEvent e) {
          if ( venta.reference.UtilityPtoVenta.verificaVK_F1(e) && pJComboBox.isDisplayable() ) {
            pJComboBox.setPopupVisible(true);
          } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
            moveFocusTo(tblLista.getSelectedRow());
          } else if ( e.getKeyCode()==KeyEvent.VK_ESCAPE ) {
            e.consume();
            tblLista_keyPressed(e);
          }
        }  
        
    });
    /*
    pJComboBox.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
      log.debug("Entro addItemListener ");
       String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
       log.debug("codigoCampo : "  + codigoCampo);
       if( codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_ENTIDAD_FINANCIERA) || 
           codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA)){
        log.debug("Entro addItemListener CAMPO_ENTIDAD_FINANCIERA");
        VariablesPtoVenta.vCodMaestro = "" ;
        VariablesPtoVenta.vDescMaestro = "" ;
        tblLista.repaint();
       }
       if(codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA))
       {
         log.debug("Entro addItemListener CAMPO_TIPO_MONEDA");
         getTipoMoneda();
       }
      };
    }
    );*/
  }
    
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(tblLista);
    FarmaUtility.centrarVentana(this);
    initTable();
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  private void btnLista_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblLista);
  }

  private void tblLista_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e, tblLista, null, 0);

    if (venta.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
        return;
      }
      checkComboBoxComponent();
    }else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
        return;
      }
      funcion();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
  
  private void funcion()
  {
    if(validateMandatory())
    {
      String serie, tipoComp, tipoBillete, tipoMoneda, formaPago, serieDocumento,
      numeroCuenta,titular,localNuevo,servicio, trabajador, proveedor,numeroDocumento,vNombrePersonal;
      
      try
      {
        serie = getSerie();
        tipoComp = getTipoComprobante();
        tipoBillete = getTipoBillete();
        tipoMoneda = getTipoMoneda();
        log.debug("tipoMonedafinal : " + tipoMoneda);
        formaPago = getFormaPago();
        numeroCuenta = getCodNumeroCuenta();
        titular = getNombreTitular();
        trabajador = getTrabajador();
        localNuevo = getCodLocal();
        servicio = getCodServicio();
        proveedor = getCodProveedor();
        numeroDocumento = getNumeroDocumento();
        serieDocumento = getSerieDocumento();
          
          vNombrePersonal = getNombrePersonal();  
        
        if(getMonto() && getMontoParcial() && getRuc() && getFechaHora() && getFechaEmision() && getCodAutorizacion()&& getFechaVencimiento() && getMontoPerdido())
        {

            // Se validara si la cuadratura debe de ingresar obligatoriamente motivo
            // dubilluz 01/12/2008 
            if(!ingresaMotivoCuadratura(VariablesCajaElectronica.vCodCuadratura))
              return; 
            
            
          DBCajaElectronica.agregaDatosEfectivoRendido(tipoMoneda, monto, 
          numeroCuenta, fechaOperacion, txtNumeroOperacion.getText().trim(), txtNombreAgencia.getText().trim(),
          fechaEmision, serie, tipoComp, txtNumeroComp.getText().trim(), 
          titular, codAutorizacion, trabajador, 
          txtMotivo.getText().trim(), formaPago,txtSerieBillete.getText().trim(),tipoBillete,
          montoParcial, ruc, txtRazonSocial.getText().trim(), localNuevo,
          servicio, numeroDocumento,proveedor,fechaVencimiento,serieDocumento, montoPerdido, txtNumeroPedido.getText().trim(),
            vNombrePersonal
                                                       );
  
          FarmaUtility.aceptarTransaccion();
          VariablesCajaElectronica.vMotivoCuadratura = "";
          FarmaUtility.showMessage(this,"Datos de Efectivo Rendido grabados correctamente",tblLista);
      cerrarVentana(true);
        }
      }catch(SQLException e)
      {
        FarmaUtility.liberarTransaccion();
        if(e.getErrorCode()>20000)
        {
          FarmaUtility.showMessage(this,e.getMessage().substring(10,e.getMessage().indexOf("ORA-06512")),tblLista);  
        }else
        {
          log.error("",e);
          FarmaUtility.showMessage(this,"Error al grabar la cuadratura.\n"+e,tblLista);
        }      
      }
    }
  }
  
  private boolean validateMandatory() 
  {
   boolean dataExists = true;
   for (int i=0; i<tblLista.getRowCount(); i++) {
     if ( ((String)tblLista.getValueAt(i,5)).trim().equalsIgnoreCase("S") ) {
        if ( ((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_FORMA_PAGO) ) {
          String formaPago = getFormaPago();
          String tipMoneda = getTipoMoneda();
          if( (formaPago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES) && !tipMoneda.equalsIgnoreCase(FarmaConstants.MONEDA_SOLES)) ||
              (formaPago.equals(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES) && !tipMoneda.equalsIgnoreCase(FarmaConstants.MONEDA_DOLARES)))
          {
            dataExists = false;
            moveFocusTo(i);
            FarmaUtility.showMessage(this, "La forma de pago no coincide con el tipo de moneda. Verifique!!!", null );
            break;
          } else continue;
        }
        if ( ((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA) ) {
          if( VariablesPtoVenta.vCodMaestro.equals("") )
          {
            dataExists = false;
            moveFocusTo(i);
            FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                     " no tiene informaci�n.  Verifique !!!", null );
            break;
          } else continue;
        }
        if(((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_SERIE_DINERO))
        {
          String tipDinero = getTipoBillete();
          if(tipDinero.equalsIgnoreCase(FarmaConstants.DINERO_BILLETE)){
            if(txtSerieBillete.getText().equalsIgnoreCase("")){ 
              dataExists = false;
              moveFocusTo(i);
              FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                       " no tiene informaci�n.  Verifique !!!", null );
              break;
            } else continue;
          } else continue;
        }
        if(((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_RAZON_SOCIAL))
        {
          String tipoComprobante = getTipoComprobante();
          log.debug("tipoComprobante : "  + tipoComprobante );
          if(tipoComprobante.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_FACTURA)){
            if(txtRuc.getText().equalsIgnoreCase("") && txtRazonSocial.getText().equalsIgnoreCase("")){
              dataExists = false;
              moveFocusTo(i);
              FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                       " no tiene informaci�n.  Verifique !!!", null );
              break;
            } else continue ; 
          } else continue ; 
        }
        if(((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_RUC))
        {
          String tipoComprobante = getTipoComprobante();
          log.debug("tipoComprobante : "  + tipoComprobante );
          if(tipoComprobante.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_FACTURA)){
            if(txtRuc.getText().equalsIgnoreCase("") && txtRazonSocial.getText().equalsIgnoreCase("")){
              dataExists = false;
              moveFocusTo(i);
              FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                       " no tiene informaci�n.  Verifique !!!", null );
              break;
            } else continue ; 
          } else continue ; 
        }
        if ( ((String)tblLista.getValueAt(i,1)).trim().length()==0 ) {
          dataExists = false;
          moveFocusTo(i);
          FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                    " no tiene informaci�n.  Verifique !!!", null );
          break;
        }
        
         // nombre de personal
         if(((String)tblLista.getValueAt(i,2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_NOMBRE_PERSONAL))
         {
             if(txtNombrePersonal.getText().equalsIgnoreCase("")){ 
               dataExists = false;
               moveFocusTo(i);
               FarmaUtility.showMessage(this, "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,2)).trim()) +
                                        " no tiene informaci�n.  Verifique !!!", null );
               break;
             } else continue;
         }
         
     }
   }
   return dataExists;
  }
  
  private String findFieldDescription(String pFieldValue) 
  {
    String fieldDescription = new String("NO DETERMINADO");
    for (int i=0; i<aCampos.size(); i++) {
      if ( ((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue) ) {
        fieldDescription = ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
        break;
      }
    }
    return fieldDescription;
  }
  
  private void checkComboBoxComponent() {
    String codigoCampo = "";
    codigoCampo = tblLista.getValueAt(tblLista.getSelectedRow(),2).toString().trim();
    tblLista.getEditorComponent().requestFocus(); 
    if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_COMPROBANTE)) 
      cmbSerie.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_COMPROBANTE)) 
      cmbTipoComp.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_DINERO)) 
      cmbTipoBillete.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA)) 
      cmbTipoMoneda.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FORMA_PAGO)) 
      cmbFormaPago.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_ENTIDAD_FINANCIERA)) 
      cmbEntidadFinanciera.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TITULAR)) 
      cmbNomTitular.setPopupVisible(true);
    else if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_SERVICIO)) 
      cmbServicio.setPopupVisible(true);
  }
  
  private String getSerie()
  {
    String serie;
    try
    {
      serie = FarmaLoadCVL.getCVLCode("CMB_SERIE",cmbSerie.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      serie = "";
    }
    
    return serie;
  }

  private String getTipoComprobante()
  {
    String tipoComp;
    try
    {
      tipoComp = FarmaLoadCVL.getCVLCode("CMB_TIPO_COMP",cmbTipoComp.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      tipoComp = "";
    }
    
    return tipoComp;
  }


  private String getTipoBillete()
  {
    String tipoBillete;
    try
    {
      tipoBillete = FarmaLoadCVL.getCVLCode("CMB_TIPO_BILLETE",cmbTipoBillete.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      tipoBillete = "";
    }
    
    return tipoBillete;
  }

  private String getTipoMoneda()
  {
    String tipoMoneda;
    try
    {
      tipoMoneda = FarmaLoadCVL.getCVLCode("CMB_TIPO_MONEDA",cmbTipoMoneda.getSelectedIndex());
      log.debug("tipoMoneda : " + tipoMoneda);
    }catch(ArrayIndexOutOfBoundsException e)
    {
      tipoMoneda = "";
    }
    
    return tipoMoneda;
  }

  private String getFormaPago()
  {
    String formaPago;
    
    try
    {
      formaPago = FarmaLoadCVL.getCVLCode("CMB_FORMA_PAGO",cmbFormaPago.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      formaPago = "";
    }
    
    return formaPago;
  }

  private boolean getMonto()
  {
    boolean retorno;
    String  txtMon = txtMonto.getText().trim();
    double dMon;
    if(txtMon.length() == 0)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar un monto.",tblLista);
    }else if(!FarmaUtility.isDouble(txtMon))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Ingrese un monto correcto.",tblLista);
    }else
    {
      dMon = FarmaUtility.getDecimalNumber(txtMon);
      if(dMon == 0.0)
      {
        retorno = false;
        FarmaUtility.showMessage(this,"No puede ingresar el monto 0.0",tblLista);
      }
      else
      {
        retorno = true;
        monto = dMon;
      }
    }
    
    return retorno;
  }

  private String getCodEntidadFinanciera()
  {
    String codigo;
    
    try
    {
      codigo = FarmaLoadCVL.getCVLCode("CMB_ENTIDAD_FINANCIERA",cmbEntidadFinanciera.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      codigo = "";
    }
    log.debug(codigo);
    return codigo;
  }
  
  private String getCodNumeroCuenta()
  {
    return VariablesPtoVenta.vCodMaestro.trim();
  }
  
  private String getNumeroDocumento()
  {
    String numDocu = txtNumeroDocumento.getText();
    if(numDocu.length()>8)
    {
      log.debug("numDocu.length() : "+ numDocu.length());
      numDocu = numDocu.trim();//.substring(numDocu.length() - 8,numDocu.length());
      log.debug("numDocu : "+ numDocu);
    } else numDocu = numDocu;
    
    return numDocu;
  }
    
  
  private String getSerieDocumento()
  {
    return txtSerie.getText().trim();
  }
  
  private boolean getFechaHora()
  {
    boolean retorno;
    String  txtFecha = txtFechaHora.getText();
    if(txtFecha.length() == 0)
    {
      retorno = true;
      fechaOperacion = "";
    }else if(txtFecha.length() < 19)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de operaci�n en el siguiente formato: dd/MM/yyyy HH24:mm:ss",tblLista);
    }else if(!FarmaUtility.validaFecha(txtFecha, "dd/MM/yyyy HH:mm:ss"))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de operaci�n en el siguiente formato: dd/MM/yyyy HH24:mm:ss",tblLista);
    } else if( !FarmaUtility.isFechaValida(this, txtFecha, "Ingrese una Fecha de operaci�n correcta") )
    {
      retorno = false;
    }
      else
    {
      retorno = true;
      fechaOperacion = txtFecha;
    }
    
    return retorno;
  }
  
  private boolean getFechaEmision()
  {
    boolean retorno;
    String  txtFecha= txtFechaEmision.getText();
    if(txtFecha.length() == 0)
    {
      retorno = true;
      fechaEmision = "";
    }else if(txtFecha.length() < 10)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de emisi�n en el siguiente formato: dd/MM/yyyy",tblLista);
    }else if(!FarmaUtility.validaFecha(txtFecha, "dd/MM/yyyy"))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de emisi�n en el siguiente formato: dd/MM/yyyy",tblLista);
    }else
    {
      retorno = true;
      fechaEmision = txtFecha;
    }
    
    return retorno;
  }
  
  private boolean getFechaVencimiento()
  {
    boolean retorno;
    String  txtFecha= txtFechaVencimiento.getText();
    if(txtFecha.length() == 0)
    {
      retorno = true;
      fechaVencimiento = "";
    }else if(txtFecha.length() < 10)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de emisi�n en el siguiente formato: dd/MM/yyyy",tblLista);
    }else if(!FarmaUtility.validaFecha(txtFecha, "dd/MM/yyyy"))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar la fecha de emisi�n en el siguiente formato: dd/MM/yyyy",tblLista);
    }else
    {
      retorno = true;
      fechaVencimiento = txtFecha;
    }
    
    return retorno;
  }
  
  private String getNombreTitular()
  {
    String codigo;
    
    try
    {
      codigo = FarmaLoadCVL.getCVLCode("CMB_NOMBRE_TITULAR",cmbNomTitular.getSelectedIndex());
    }catch(ArrayIndexOutOfBoundsException e)
    {
      codigo = "";
    }
    
    return codigo;
  }
  
  private String getCodTrabajador()
  {
    return vCodTrabajador.getText().trim();
  }
  
  private boolean getMontoParcial()
  {
    boolean retorno;
    String  txtMon = txtMontoParcial.getText().trim();
    double dMon;
    if(txtMon.length() == 0)
    {
      retorno = true;
      //FarmaUtility.showMessage(this,"Debe ingresar un monto.",tblLista);
    }else if(!FarmaUtility.isDouble(txtMon))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Ingrese un monto correcto.",tblLista);
    }else
    {
      dMon = FarmaUtility.getDecimalNumber(txtMon);
      if(dMon == 0.0)
      {
        retorno = false;
        FarmaUtility.showMessage(this,"No puede ingresar el monto 0.0",tblLista);
      }
      else
      {
        retorno = true;
        montoParcial = dMon;
      }
    }
    return retorno;
  }
    
  private boolean getMontoPerdido()
  {
    boolean retorno;
    String  txtMonPer = txtMontoPerdido.getText().trim();
    double dMonPer;
    if(txtMonPer.length() == 0)
    {
      retorno = true;
      //FarmaUtility.showMessage(this,"Debe ingresar un monto.",tblLista);
    }else if(!FarmaUtility.isDouble(txtMonPer))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Ingrese un monto correcto.",tblLista);
    }else
    {
      dMonPer = FarmaUtility.getDecimalNumber(txtMonPer);
      if(dMonPer == 0.0)
      {
        retorno = false;
        FarmaUtility.showMessage(this,"No puede ingresar el monto 0.0",tblLista);
      }
      else
      {
        retorno = true;
        montoPerdido = dMonPer;
      }
    }
    return retorno;
  }
  
  
  private String getCodLocal()
  {
    return vCodLocal.getText().trim();
  }
  
  private String getCodServicio()
  {
    String codigo;
    log.debug("entro a getCodServicio");
    try
    {
      log.debug("index : " + cmbServicio.getSelectedIndex());
      codigo = FarmaLoadCVL.getCVLCode("CMB_SERVICIO",cmbServicio.getSelectedIndex());
      log.debug("codigo : " + codigo);
    }catch(ArrayIndexOutOfBoundsException e)
    {
      log.debug("se cae en codigo");
      codigo = "";
    }
    
    return codigo;
  }
  
  private String getCodTrabajadorLocal()
  {
    return vCodTrabajadorLocal.getText().trim();
  }
  
  private String getCodCajero()
  {
    return vCodCajero.getText().trim();
  }
  
  private String getCodProveedor()
  {
    return vCodProveedor.getText().trim();
  }
  
  private boolean getRuc()
  {
    boolean retorno = true;
    String sRuc = txtRuc.getText();
    if(sRuc.length() == 0)
    {
      retorno = true;
      ruc = "";
    }else if(sRuc.length() < 11)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar el Ruc, correctamente.",tblLista);
    }else if(verificaRucValido().equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
    {
      FarmaUtility.showMessage(this,"Ingrese un Ruc V�lido.",txtRuc);
      retorno = false;
    }else
    {
      retorno = true;
      ruc = sRuc;
    }
    
    return retorno;
  }
  
  private String verificaRucValido()
  {
    String resultado = "";
    try
    {
      resultado = DBCliente.verificaRucValido(txtRuc.getText().trim());
      return resultado;
    } 
    catch(SQLException sql)
    {
      log.error("",sql);
      return ConstantsCliente.RESULTADO_RUC_INVALIDO;
    }
  } 
  
  private String getTrabajador()
  {
    String trabajador = getCodTrabajador();
    if(trabajador.equals(""))
    {
      trabajador = getCodTrabajadorLocal();
      if(trabajador.equals(""))
      {
        trabajador = getCodCajero();
      }
    }
    return trabajador;
  }
  
  private boolean getCodAutorizacion()
  {
    boolean retorno = true;
    String codigo = txtCodAutorizacion.getText().trim();
    if(codigo.length() == 0)
    {
      retorno = true;
      codAutorizacion = "";
    }else if(codigo.length() < 14)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Debe ingresar el codigo, correctamente.",tblLista);
    }else
    {
      retorno = true;
      codAutorizacion = codigo;
    }
    
    return retorno;
  }
  
  private void cargaNumeroCuenta()
  {
    ArrayList infoCuenta= new ArrayList();
    try
    {
      if(VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEP_VENTA))
      {
        VariablesCajaElectronica.vCodEntidadFinanciera = getCodEntidadFinanciera();
        log.debug("VariablesCajaElectronica.vCodEntidadFinanciera : " + VariablesCajaElectronica.vCodEntidadFinanciera);
        VariablesCajaElectronica.vCodTipoMoneda = getTipoMoneda();      
        infoCuenta = DBCajaElectronica.obtenerNumeroCuenta();
        if(infoCuenta.size()>0)
        {
          VariablesPtoVenta.vCodMaestro =((String)((ArrayList) infoCuenta.get(0)).get(0)).trim();
          VariablesPtoVenta.vDescMaestro =((String)((ArrayList) infoCuenta.get(0)).get(1)).trim();
          log.debug("Numero de Cuenta :" + VariablesPtoVenta.vDescMaestro);  
        }        
      }
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrio un error al buscar el numero de cuenta \n" + sql.getMessage(),tblLista);
    }
  }  

    private boolean ingresaMotivoCuadratura(String pCodCuadratura)  throws SQLException {      
        String pObligaMotivo = DBCajaElectronica.isMotivoCuadratura(pCodCuadratura.trim());      
        if(pObligaMotivo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            DlgMotivoCuadratura dlgMotivo = new DlgMotivoCuadratura(myParentFrame,"",true);
            dlgMotivo.setVisible(true);
            if(FarmaVariables.vAceptar){
                return true;
            }
            else return false;
        }
        
        return true;
    }
    
    private String getNombrePersonal() {
        
        String codigoCampo = "";
        String pNombrePersonal = " ";
        
        if(txtNombrePersonal.getText().trim().length()>0){
            pNombrePersonal = txtNombrePersonal.getText().trim().toUpperCase();
        }
        else{
            pNombrePersonal = ".";
        }
        
        /*
        for(int i=0;i<tblLista.getRowCount();i++){
            codigoCampo = tblLista.getValueAt(i,2).toString().trim();
            if(codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PERSONAL)) {
                pNombrePersonal = tblLista.getValueAt(i,1).toString().trim();
                break;
            }
        }*/
        
        return pNombrePersonal;
    }
  
}
