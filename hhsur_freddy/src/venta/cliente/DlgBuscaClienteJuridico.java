package venta.cliente;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;

import javax.swing.*;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JPanelHeader;
import java.awt.Rectangle;
import componentes.gs.componentes.JPanelTitle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JTextFieldSanSerif;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionEvent;

import common.*;
import venta.cliente.reference.*;
import venta.reference.ConstantsPtoVenta;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JRadioButton;

import venta.reference.UtilityPtoVenta;

import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venta.modulo_venta.reference.VariablesModuloVentas;
import consorcio.VariablesHH;
import venta.reference.VariablesPtoVenta;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgBuscaClienteJuridico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      23.02.2006   Creación<br>
 * PAULO       03.03.2006   Modificacion
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgBuscaClienteJuridico extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgBuscaClienteJuridico.class);  

  private Frame myParentFrame;
  ///FarmaTableModel tableModel;
  public FarmaTableModel tableModelListaClienteJuridico;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCliente = new JPanelHeader();
    private JScrollPane scrClienteJuridico = new JScrollPane();
  public JTable tblClienteJuridico = new JTable();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JLabelFunction lblF4 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnClienteJuridico = new JButtonLabel();
  private JTextFieldSanSerif txtClienteJuridico = new JTextFieldSanSerif();
  private JButton btnBuscar = new JButton();
  private JPanel jPanel1 = new JPanel();
  private JRadioButton rbtJuridico = new JRadioButton();
  private JRadioButton rbtNatural = new JRadioButton();
 // private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    
    private ArrayList vListaOriginal = new ArrayList();
    private JLabel jLabel1 = new JLabel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();

    // **************************************************************************
// Constructores
// **************************************************************************
  public DlgBuscaClienteJuridico()
  {
    this(null, "", false);
  }

  public DlgBuscaClienteJuridico(Frame parent, String title, boolean modal)
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

// **************************************************************************
// Método "jbInit()"
// **************************************************************************
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(864, 619));
    this.getContentPane().setLayout(null);
    this.getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Seleccion de clientes");
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
    jContentPane.setLayout(null);
    pnlCliente.setBounds(new Rectangle(5, 10, 840, 40));
        scrClienteJuridico.setBounds(new Rectangle(5, 50, 840, 415));
    tblClienteJuridico.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    tblClienteJuridico_keyPressed(e);
        }
      });
    lblF3.setBounds(new Rectangle(5, 475, 115, 35));
    lblF3.setText("[ F3 ] Crear");
    lblF4.setBounds(new Rectangle(135, 475, 125, 35));
    lblF4.setText("[ F4 ] Modificar");
    lblEsc.setBounds(new Rectangle(730, 480, 100, 35));
    lblEsc.setText("[ Esc ] Cerrar");
        btnClienteJuridico.setText("Cliente :");
    btnClienteJuridico.setBounds(new Rectangle(20, 10, 105, 25));
    btnClienteJuridico.setMnemonic('c');
    btnClienteJuridico.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClienteJuridico_actionPerformed(e);
        }
      });
    txtClienteJuridico.setBounds(new Rectangle(80, 10, 440, 20));
    txtClienteJuridico.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtClienteJuridico_keyPressed(e);
                }

        public void keyReleased(KeyEvent e)
        {
                   txtClienteJuridico_keyReleased(e);
                }
      });
        txtClienteJuridico.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtClienteJuridico_focusLost(e);
                }
            });
        btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(530, 5, 85, 25));
    btnBuscar.setBackground(SystemColor.control);
    btnBuscar.setMnemonic('b');
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.setRequestFocusEnabled(false);
    btnBuscar.setFont(new Font("SansSerif", 1, 12));
    jPanel1.setBounds(new Rectangle(175, 15, 375, 35));
    jPanel1.setBorder(BorderFactory.createTitledBorder(""));
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(null);
      jPanel1.setVisible(false);
    rbtJuridico.setText("JURIDICO");
    rbtJuridico.setBounds(new Rectangle(205, 5, 115, 25));
    rbtJuridico.setBackground(Color.white);
    rbtJuridico.setFont(new Font("SansSerif", 1, 14));
    rbtJuridico.setForeground(new Color(0, 114, 169));
    rbtJuridico.setFocusPainted(false);
    rbtJuridico.setRequestFocusEnabled(false);
    rbtJuridico.setFocusable(false);
    rbtNatural.setText("NATURAL");
    rbtNatural.setBounds(new Rectangle(90, 5, 95, 25));
    rbtNatural.setBackground(Color.white);
    rbtNatural.setFont(new Font("SansSerif", 1, 14));
    rbtNatural.setForeground(new Color(0, 114, 169));
    rbtNatural.setFocusPainted(false);
    rbtNatural.setRequestFocusEnabled(false);
    rbtNatural.setFocusable(false);
    rbtNatural.setSelected(true);
    /*
    lblF5.setBounds(new Rectangle(420, 415, 185, 20));
    lblF5.setText("[ F2 ] Ver Tarjeta Cliente");
    */
        lblF8.setBounds(new Rectangle(570, 480, 150, 35));
    lblF8.setText("[ F11 ] Seleccionar");
        jLabel1.setText("Porfavor de ingresar el DOCUMENTO y presionar ENTER o presionar en BUSCAR");
        jLabel1.setBounds(new Rectangle(5, 520, 845, 35));
        jLabel1.setFont(new Font("SansSerif", 1, 16));
        jLabel1.setForeground(new Color(0, 66, 198));
        jButton1.setText("Sin Documento");
        jButton1.setBounds(new Rectangle(620, 5, 115, 25));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton2.setText("Limpiar");
        jButton2.setBounds(new Rectangle(740, 5, 95, 25));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        jPanel1.add(rbtJuridico, null);
    jPanel1.add(rbtNatural, null);
        //  jContentPane.add(lblF5, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        scrClienteJuridico.getViewport().add(tblClienteJuridico, null);
        jContentPane.add(scrClienteJuridico, null);
        jContentPane.add(pnlCliente, null);
        pnlCliente.add(jButton2, null);
        pnlCliente.add(jButton1, null);
        pnlCliente.add(btnBuscar, null);
        pnlCliente.add(txtClienteJuridico, null);
        pnlCliente.add(btnClienteJuridico, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
// **************************************************************************
// Método "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTableListaClienteJuridico();
    seleccionTipoCliente();
    FarmaVariables.vAceptar = false;
  };

// **************************************************************************
// Métodos de inicialización
// **************************************************************************
  private void initTableListaClienteJuridico()
  {
    tableModelListaClienteJuridico = new FarmaTableModel(ConstantsCliente.columnsListaClientesJuridicos,ConstantsCliente.defaultValuesListaClientesJuridicos,0);
    FarmaUtility.initSimpleList(tblClienteJuridico,tableModelListaClienteJuridico,ConstantsCliente.columnsListaClientesJuridicos);
  }
  
  private void seleccionTipoCliente()
  {
    if(VariablesHH.vTipoBusqueda.equalsIgnoreCase(ConstantsCliente.TIPO_JURIDICO))
    {
      rbtNatural.setSelected(false);
      rbtJuridico.setSelected(true);
    } 
    else
    {
      rbtNatural.setSelected(true);
      rbtJuridico.setSelected(false);
    }  
  }
  private void cambiaTipoCliente()
  {
    if(rbtJuridico.isSelected())
    {
      rbtNatural.setSelected(true);
      rbtJuridico.setSelected(false);
      VariablesHH.vTipoBusqueda = ConstantsCliente.TIPO_JURIDICO;
    } 
    else if(rbtNatural.isSelected())
    {
      rbtNatural.setSelected(false);
      rbtJuridico.setSelected(true);
      VariablesHH.vTipoBusqueda = ConstantsCliente.TIPO_NATURAL;
    }
   }
  
  public void cargaClienteJuridico()
  {
    try
    {
      log.debug(VariablesHH.vRuc_RazSoc_Busqueda);
      log.debug(VariablesHH.vTipoBusqueda);
        FarmaUtility.showMessage(this, "No se puede mostrar todos los pacientes", txtClienteJuridico);
     // DBCliente.cargaListaClienteJuridico(tableModelListaClienteJuridico,VariablesHH.vRuc_RazSoc_Busqueda, VariablesHH.vTipoBusqueda);
      //FarmaUtility.ordenar(tblClienteJuridico, tableModelListaClienteJuridico, 2, FarmaConstants.ORDEN_ASCENDENTE);
    } 
    catch(Exception e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos",null);
      cerrarVentana(false);
    }
  }
  
    public void cargaCliente_inicial()
    {
      try
      {
        log.debug(VariablesHH.vRuc_RazSoc_Busqueda);
        log.debug(VariablesHH.vTipoBusqueda);
        DBCliente.cargaListaCliente_Inicial(tableModelListaClienteJuridico,VariablesHH.vRuc_RazSoc_Busqueda, VariablesHH.vTipoBusqueda);
          vListaOriginal = (ArrayList)(tableModelListaClienteJuridico.data).clone();
        //FarmaUtility.ordenar(tblClienteJuridico, tableModelListaClienteJuridico, 2, FarmaConstants.ORDEN_ASCENDENTE);
      } 
      catch(SQLException e)
      {
        log.error("",e);
        FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos",null);
        cerrarVentana(false);
      }
    }
    
    public void cargaCliente_busqueda()
    {
      String pCadena = txtClienteJuridico.getText().trim().toUpperCase();
      
      if(pCadena.length()>=3){
          boolean isNumero = false;
          
          try {
                double valor = Double.parseDouble(pCadena.trim());
                isNumero = true;
            } catch (Exception nfe) {
                // TODO: Add catch code
                //nfe.printStackTrace();
                isNumero = false;
            }
            
          try
          {
            log.debug(VariablesHH.vRuc_RazSoc_Busqueda);
            log.debug(VariablesHH.vTipoBusqueda);
            if(isNumero)
                DBCliente.cargaListaCliente_documento(tableModelListaClienteJuridico,pCadena);
            else
                DBCliente.cargaListaCliente_palabra(tableModelListaClienteJuridico,pCadena);
            //FarmaUtility.ordenar(tblClienteJuridico, tableModelListaClienteJuridico, 2, FarmaConstants.ORDEN_ASCENDENTE);
            vListaOriginal = (ArrayList)(tableModelListaClienteJuridico.data).clone();
          } 
          catch(SQLException e)
          {
            log.error("",e);
            FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos",null);
            cerrarVentana(false);
          }
      }
      else
        FarmaUtility.showMessage(this, "Para la búsqueda debe colocar minimo 3 caracteres",null);
      
      
    }
    
  
// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.moveFocus(txtClienteJuridico);
    FarmaUtility.centrarVentana(this);
    if (VariablesHH.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S)){
        //cargaClienteJuridico();
        cargaCliente_inicial();
        rbtJuridico.setSelected(true);
        rbtNatural.setSelected(false);
        vListaOriginal = (ArrayList)(tableModelListaClienteJuridico.data).clone();
    }
  }

  private void txtClienteJuridico_keyPressed(KeyEvent e)
  {
   //chkKeyPressed(e);
      try
      {
          
          if(e.getKeyChar() != '+'&&
              !(
              (e.getKeyCode() == KeyEvent.VK_UP || 
               e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
              (e.getKeyCode() == KeyEvent.VK_DOWN || 
               e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
              e.getKeyCode() == KeyEvent.VK_ENTER))
              //filtroGoogle();
              log.debug("Caracter");
          else    
              FarmaGridUtils.aceptarTeclaPresionada(e, tblClienteJuridico,new JTextField(), 2);
          log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));
          
          if (e.getKeyCode() == KeyEvent.VK_ENTER)
          {
           procesoEnter(e);
          } else {
              chkKeyPressed(e);
          }
      } catch (Exception exc) {
          log.error("",exc);
      } finally {
      }
  
  }
  
  private void chkKeyReleased(KeyEvent e)
  {
  }
  
  private void buscaClienteJuridico(String pTipoBusqueda, String pBusqueda)
  {
     VariablesHH.vTipoBusqueda = pTipoBusqueda;
     VariablesHH.vRuc_RazSoc_Busqueda = pBusqueda;
     cargaClienteJuridico();
  }
  
  private void txtClienteJuridico_keyReleased(KeyEvent e)
  {
     //FarmaGridUtils.buscarDescripcion(e, tblClienteJuridico, txtClienteJuridico, 2);
     if(tblClienteJuridico.getRowHeight()==0&&txtClienteJuridico.getText().trim().length()==0){
         clonarListadoProductos();
        // lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
     }
         
     
     if(e.getKeyChar() != '+'&&
         !(
         (e.getKeyCode() == KeyEvent.VK_UP || 
          e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
         (e.getKeyCode() == KeyEvent.VK_DOWN || 
          e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
         e.getKeyCode() == KeyEvent.VK_ENTER)){
         filtroGoogle();
         }
         //log.debug("Caracter");
     else    
     if(tblClienteJuridico.getRowCount() >= 0 && 
         tableModelListaClienteJuridico.getRowCount() > 0 && 
         e.getKeyChar() != '+') {
         if (FarmaGridUtils.buscarDescripcion(e, tblClienteJuridico, txtClienteJuridico, 
                                              2) || 
             (e.getKeyCode() == KeyEvent.VK_UP || 
              e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
             (e.getKeyCode() == KeyEvent.VK_DOWN || 
              e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
             e.getKeyCode() == KeyEvent.VK_ENTER) {
             VariablesModuloVentas.vPosNew = tblClienteJuridico.getSelectedRow();
         }
     }
  }
  
  private void mostrarDatoCliente()
  {
     String nombre = tableModelListaClienteJuridico.getValueAt(tblClienteJuridico.getSelectedRow(),2).toString();
     txtClienteJuridico.setText(nombre);
     log.debug(nombre);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_F3)
    {
     /* if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtClienteJuridico);
      else{    
        if (rbtJuridico.isSelected())
        {
          mantenimientoClienteJuridico(ConstantsCliente.ACCION_INSERTAR);  
        }
        else
          mantenimientoClienteNatural(ConstantsCliente.ACCION_INSERTAR);
      }*/
      mantenimientoClienteNatural(ConstantsCliente.ACCION_INSERTAR);
    } 
    else if(e.getKeyCode() == KeyEvent.VK_F4)
    {
      /*if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtClienteJuridico);
      else {
        if(tblClienteJuridico.getRowCount() <= 0) return;
        if (rbtJuridico.isSelected())
        {
          guardaRegistroCliente();
          mantenimientoClienteJuridico(ConstantsCliente.ACCION_MODIFICAR);  
        }
        else {
          VariablesHH.vCodigo = ((String)tblClienteJuridico.getValueAt(tblClienteJuridico.getSelectedRow(),0)).trim();
          guardaRegistroCliente();
          mantenimientoClienteNatural(ConstantsCliente.ACCION_MODIFICAR);
        }
      }*/
      guardaRegistroCliente();
      mantenimientoClienteNatural(ConstantsCliente.ACCION_MODIFICAR);
    }
    
    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if (UtilityPtoVenta.verificaVK_F11(e))
    {
      guardaRegistroCliente();
      
      //if(VariablesHH.vIndicadorCargaCliente.equals(FarmaConstants.INDICADOR_S))
      // cerrarVentana(true);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

// **************************************************************************
// Metodos de lógica de negocio
// **************************************************************************
  private void guardaRegistroCliente()
   {
    /*if(tblClienteJuridico.getRowCount() > 0){
        VariablesHH.vArrayList_Cliente_Juridico.clear();
        VariablesHH.vArrayList_Cliente_Juridico.add(tableModelListaClienteJuridico.data.get(tblClienteJuridico.getSelectedRow()));
    }*/
      /*
    new FarmaColumnData( "Tipo", 30, JLabel.CENTER ), 0
    new FarmaColumnData( "Codigo", 0, JLabel.CENTER ), 1
    new FarmaColumnData( "Documento", 100, JLabel.CENTER ),2
    new FarmaColumnData( "Cliente", 600, JLabel.LEFT ),3
    new FarmaColumnData( "Teléfono", 80, JLabel.LEFT ),4
    new FarmaColumnData( "Correo", 0, JLabel.LEFT ), 5
    new FarmaColumnData( "Direccion", 0, JLabel.LEFT ), 6 
    new FarmaColumnData( "id_tipo_cli", 0, JLabel.LEFT ) 7*/
        
    if(tblClienteJuridico.getSelectedRow()>=0){
        String pTipo = FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 11);
        VariablesHH.vTipoDocumento = pTipo;
        VariablesHH.vCodigo = FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 1);
        if(pTipo.equalsIgnoreCase("01")){
            //dni
            VariablesHH.vRuc = "";
            VariablesHH.vRazonSocial = "";
            VariablesHH.vTelefono=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 4);
            VariablesHH.vCorreo=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 5);
            VariablesHH.vDni=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 2);
            VariablesHH.vDireccion=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 6);
            VariablesHH.vNombre=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 8);
            VariablesHH.vApellidoPat=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 9);
            VariablesHH.vApellidoMat=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 10);
            
        }
        else{
            //ruc
            VariablesHH.vRuc = FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 2);
            VariablesHH.vRazonSocial = FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 8);
            VariablesHH.vTelefono=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 4);
            VariablesHH.vCorreo=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 5);
            VariablesHH.vDni="";
            VariablesHH.vDireccion=FarmaUtility.getValueFieldArrayList(tableModelListaClienteJuridico.data,tblClienteJuridico.getSelectedRow(), 6);
            VariablesHH.vNombre="";
            VariablesHH.vApellidoPat="";
            VariablesHH.vApellidoMat="";
        }
        
        
        cerrarVentana(true);
    }
    else{
        
        VariablesHH.vCodigo = "";
        VariablesHH.vRuc = "";
        VariablesHH.vRazonSocial = "";
        VariablesHH.vTelefono="";
        VariablesHH.vCorreo="";
        VariablesHH.vDni="";
        VariablesHH.vDireccion="";
        VariablesHH.vNombre="";
        VariablesHH.vApellidoPat="";
        VariablesHH.vApellidoMat="";
        
        FarmaUtility.showMessage(this,"Debe seleccionar un cliente para registrar la venta.", txtClienteJuridico);

    }
      
  }

  private void mantenimientoClienteNatural(String pTipoAccion)
  {
    VariablesHH.vTipo_Accion = pTipoAccion;
    DlgMantClienteNatural dlgMantClienteNatural = new DlgMantClienteNatural(myParentFrame, "", true);
    dlgMantClienteNatural.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
            FarmaVariables.vAceptar = false;
            //cargaClienteJuridico();
            txtClienteJuridico.setText(VariablesHH.vDni);
            cargaCliente_busqueda();
            vListaOriginal = (ArrayList)(tableModelListaClienteJuridico.data).clone();
      }
  }

  private void tblClienteJuridico_keyPressed(KeyEvent e)
  {
   // txtClienteJuridico_keyPressed(e);
  }

  private void btnClienteJuridico_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtClienteJuridico);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  private void btnRelacion_actionPerformed(ActionEvent e)
  {
  FarmaUtility.moveFocusJTable(tblClienteJuridico);
  }

    private void txtClienteJuridico_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtClienteJuridico);
    }

    private void procesoEnter(KeyEvent keyEvent) {
        cargaCliente_busqueda();
    }

    private void filtroGoogle() {
        int COL_DOC_CLIENTE = 2;

        int COL_DESC_CLIENTE = 3;
        
        String condicion = txtClienteJuridico.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelListaClienteJuridico.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descCliente = fila.get(COL_DESC_CLIENTE).toString().toUpperCase().trim();
                String descDocumento = fila.get(COL_DOC_CLIENTE).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(descCliente.contains(condicion)||descDocumento.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModelListaClienteJuridico.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelListaClienteJuridico.fireTableDataChanged();
            setJTable(tblClienteJuridico);
            //iniciaProceso(false);
            
            
            if(tblClienteJuridico.getRowCount()==0){
                //lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                //if(tblClienteJuridico.getRowCount()==1)
                   // lblMensajeFiltro.setText(tblMedicos.getRowCount()+" fila para el filtro aplicado");
                //else
                  //  lblMensajeFiltro.setText(tblMedicos.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            clonarListadoProductos();
            //lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
    }
    
    private void clonarListadoProductos() {
        try
        {
          log.debug(VariablesHH.vRuc_RazSoc_Busqueda);
          log.debug(VariablesHH.vTipoBusqueda);
          //DBCliente.cargaListaClienteJuridico(tableModelListaClienteJuridico,VariablesHH.vRuc_RazSoc_Busqueda, VariablesHH.vTipoBusqueda);
            
            tableModelListaClienteJuridico.data = (ArrayList)(vListaOriginal.clone());
        } 
        catch(Exception e)
        {
          log.error("",e);
          e.printStackTrace();
          FarmaUtility.showMessage(this, "Error al listar Clientes Juridicos",null);
          cerrarVentana(false);
        }
        
        tableModelListaClienteJuridico.fireTableDataChanged();
        tblClienteJuridico.repaint();
        
        
    }
    
    private void setJTable(JTable pJTable) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
        }
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        cargaCliente_inicial();
        rbtJuridico.setSelected(true);
        rbtNatural.setSelected(false);
        vListaOriginal = (ArrayList)(tableModelListaClienteJuridico.data).clone();
        txtClienteJuridico.setText("");
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        txtClienteJuridico.setText("");
        clonarListadoProductos();
    }
}
