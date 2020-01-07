package venta.inventario;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;

import componentes.gs.componentes.JPanelTitle;

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

import java.sql.*;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import venta.inventario.reference.*;
import venta.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.cliente.DlgBuscaClienteJuridico;
import venta.cliente.reference.VariablesCliente;

import venta.modulo_venta.DlgIngresoGuia;
import venta.modulo_venta.DlgSeleccionImpGuia;
import venta.modulo_venta.DlgSeleccionMoneda;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

public class DlgGuiaIngresoResumen extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
	private static final Logger log = LoggerFactory.getLogger(DlgGuiaIngresoResumen.class);

  FarmaTableModel tableModelResumenPedido;
  Frame myParentFrame;
  String vEstadoNota;
  public boolean verListaProductos = true;
  public boolean esGuiaRecepcion = false;
  private boolean cabecera = false;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JLabelFunction lblF3 = new JLabelFunction();
  private JScrollPane scrResumenPedido = new JScrollPane();
  private JPanel pnlHeader = new JPanel();
  private JButton btnResumenPedido = new JButton();
  private JTable tblResumenPedido = new JTable();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JLabel lblFechaGuia = new JLabel();
  private JLabel lblTipoComp_T = new JLabel();
  private JLabel lblFechaGuia_T = new JLabel();
  private JLabel lblTipoComp = new JLabel();
  private JLabel lblNroComp_T = new JLabel();
  private JLabel lblNrocomp = new JLabel();
  private JLabel lblTipoGuia = new JLabel();
  private JLabel lblTipoGuia_T = new JLabel();
  private JLabel lblLocal = new JLabel();
  private JLabel lblLocal_T = new JLabel();
  private JLabelFunction lblF9 = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabel lblEstado = new JLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JLabelWhite lblTotal_T = new JLabelWhite();
  private JLabelWhite lblTotal = new JLabelWhite();
  
  public String vTipoMoneda = "01";
    private JButton btnMoneda = new JButton(); // SOLES POR DEFECTO
  
  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgGuiaIngresoResumen()
  {
    this(null, "", false);
  }

  public DlgGuiaIngresoResumen(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
      limpiarDatosCabecera();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  public DlgGuiaIngresoResumen(Frame parent, String title, boolean modal, boolean ver)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    verListaProductos = ver;
    if(VariablesInventario.vTipoNota.equals(ConstantsPtoVenta.TIP_NOTA_RECEPCION)) {
      esGuiaRecepcion = true;
    }    
    try {
      jbInit();
      initialize();
    }
    catch(Exception e) {
      log.error("",e);
    }

  }
  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(1239, 478));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Guia de Ingreso - Resumen Pedido");
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
    jContentPane.setLayout(null);
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(678, 395));
    lblF5.setText("[ F5 ] Borrar Producto");
    lblF5.setBounds(new Rectangle(165, 375, 140, 20));
        lblF5.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF5_mouseClicked(e);
                }
            });
        lblF3.setText("[ F3 ] Insertar Producto");
    lblF3.setBounds(new Rectangle(15, 375, 140, 20));
        lblF3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF3_mouseClicked(e);
                }
            });
        scrResumenPedido.setBounds(new Rectangle(10, 110, 1200, 235));
    scrResumenPedido.setBackground(new Color(255, 130, 14));
    pnlHeader.setBounds(new Rectangle(10, 85, 1200, 25));
    pnlHeader.setBackground(new Color(0, 114, 169));
    pnlHeader.setLayout(null);
    btnResumenPedido.setText("Resumen de Pedido");
    btnResumenPedido.setBounds(new Rectangle(10, 0, 140, 25));
    btnResumenPedido.setBackground(new Color(255, 130, 14));
    btnResumenPedido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnResumenPedido.setBorderPainted(false);
    btnResumenPedido.setContentAreaFilled(false);
    btnResumenPedido.setDefaultCapable(false);
    btnResumenPedido.setFocusPainted(false);
    btnResumenPedido.setFont(new Font("SansSerif", 1, 12));
    btnResumenPedido.setForeground(Color.white);
    btnResumenPedido.setHorizontalAlignment(SwingConstants.LEFT);
    btnResumenPedido.setMnemonic('r');
    btnResumenPedido.setRequestFocusEnabled(false);
    btnResumenPedido.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnResumenPedido_keyPressed(e);
        }
      });
    btnResumenPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnResumenPedido_actionPerformed(e);
        }
      });
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(470, 375, 100, 20));
        lblF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF11_mouseClicked(e);
                }
            });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(1125, 375, 85, 20));
    pnlHeader1.setBounds(new Rectangle(10, 10, 1200, 65));
    lblFechaGuia.setText("-");
    lblFechaGuia.setBounds(new Rectangle(135, 10, 75, 20));
    lblFechaGuia.setFont(new Font("SansSerif", 1, 11));
    lblFechaGuia.setForeground(Color.white);
    lblTipoComp_T.setText("Tipo Comp:");
    lblTipoComp_T.setBounds(new Rectangle(235, 10, 75, 20));
    lblTipoComp_T.setFont(new Font("SansSerif", 1, 11));
    lblTipoComp_T.setForeground(Color.white);
    lblFechaGuia_T.setText("Fecha Creacion Guia:");
    lblFechaGuia_T.setBounds(new Rectangle(10, 10, 120, 20));
    lblFechaGuia_T.setFont(new Font("SansSerif", 1, 11));
    lblFechaGuia_T.setForeground(Color.white);
    lblTipoComp.setText("-");
    lblTipoComp.setBounds(new Rectangle(315, 10, 80, 20));
    lblTipoComp.setFont(new Font("SansSerif", 1, 11));
    lblTipoComp.setForeground(Color.white);
    lblNroComp_T.setText("Nro. Comp:");
    lblNroComp_T.setBounds(new Rectangle(400, 10, 80, 20));
    lblNroComp_T.setFont(new Font("SansSerif", 1, 11));
    lblNroComp_T.setForeground(Color.white);
    lblNrocomp.setText("-");
    lblNrocomp.setBounds(new Rectangle(490, 10, 155, 20));
    lblNrocomp.setFont(new Font("SansSerif", 1, 11));
    lblNrocomp.setForeground(Color.white);
    lblTipoGuia.setText("-");
    lblTipoGuia.setBounds(new Rectangle(135, 35, 210, 20));
    lblTipoGuia.setFont(new Font("SansSerif", 1, 11));
    lblTipoGuia.setForeground(Color.white);
    lblTipoGuia_T.setText("Tipo Guia Entrada:");
    lblTipoGuia_T.setBounds(new Rectangle(10, 35, 110, 20));
    lblTipoGuia_T.setFont(new Font("SansSerif", 1, 11));
    lblTipoGuia_T.setForeground(Color.white);
    lblLocal.setText("-");
    lblLocal.setBounds(new Rectangle(410, 40, 230, 20));
    lblLocal.setFont(new Font("SansSerif", 1, 11));
    lblLocal.setForeground(Color.white);
    lblLocal_T.setText("Local:");
    lblLocal_T.setBounds(new Rectangle(355, 40, 50, 20));
    lblLocal_T.setFont(new Font("SansSerif", 1, 11));
    lblLocal_T.setForeground(Color.white);
    lblF9.setText("[ F9 ] Datos generales");
    lblF9.setBounds(new Rectangle(315, 375, 145, 20));
        lblF9.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF9_mouseClicked(e);
                }
            });
        lblF2.setText("[ F2 ] Anular");
    lblF2.setBounds(new Rectangle(15, 405, 85, 20));
        lblF2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF2_mouseClicked(e);
                }
            });
        lblEstado.setBounds(new Rectangle(535, 40, 95, 20));
    lblEstado.setFont(new Font("SansSerif", 1, 11));
    lblEstado.setForeground(Color.white);
        jPanelTitle1.setBounds(new Rectangle(10, 345, 1200, 20));
    lblTotal_T.setText("TOTAL:");
    lblTotal_T.setBounds(new Rectangle(740, 0, 70, 20));
    lblTotal.setBounds(new Rectangle(815, 0, 85, 20));
    lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotal.setText("0.00");
        btnMoneda.setText("Soles  ( S/  )");
        btnMoneda.setBounds(new Rectangle(970, 15, 215, 35));
        btnMoneda.setFont(new Font("SansSerif", 1, 17));
        btnMoneda.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnMoneda_actionPerformed(e);
                }
            });
        btnMoneda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnMoneda_keyPressed(e);
                }
            });
        btnMoneda.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnMoneda_mouseClicked(e);
                }
            });
        scrResumenPedido.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        pnlHeader1.add(btnMoneda, null);
        pnlHeader1.add(lblEstado, null);
    pnlHeader1.add(lblLocal_T, null);
    pnlHeader1.add(lblLocal, null);
    pnlHeader1.add(lblTipoGuia_T, null);
    pnlHeader1.add(lblTipoGuia, null);
    pnlHeader1.add(lblNrocomp, null);
    pnlHeader1.add(lblNroComp_T, null);
    pnlHeader1.add(lblTipoComp, null);
    pnlHeader1.add(lblFechaGuia_T, null);
    pnlHeader1.add(lblTipoComp_T, null);

    pnlHeader1.add(lblFechaGuia, null);
        jPanelTitle1.add(lblTotal, null);
        jPanelTitle1.add(lblTotal_T, null);
        jContentPane.add(jPanelTitle1, null);
    jContentPane.add(pnlHeader1, null);
        jContentPane.add(lblEsc, null);
        scrResumenPedido.getViewport().add(tblResumenPedido, null);
    jContentPane.add(scrResumenPedido, null);
    pnlHeader.add(btnResumenPedido, null);
    jContentPane.add(pnlHeader, null);
        if(verListaProductos)
    {
      jContentPane.add(lblF11, null);
      jContentPane.add(lblF5, null);
      jContentPane.add(lblF3, null);  
      jContentPane.add(lblF9, null);
    }else if(!esGuiaRecepcion)
      jContentPane.add(lblF2, null);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTableResumenPedido();  
    
    if(!verListaProductos)
      cargarDetalle();
    FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTableResumenPedido() {
    tableModelResumenPedido = new FarmaTableModel(ConstantsInventario.columnsListaResumenPedido,ConstantsInventario.defaultListaResumenPedido,0);
    FarmaUtility.initSimpleList(tblResumenPedido,tableModelResumenPedido,ConstantsInventario.columnsListaResumenPedido);
  }
  
  
  private void cargaListaResumenPedido() {
    tableModelResumenPedido.clearTable();
      
    if(VariablesInventario.vArrayGuiaIngresoProductos.size()>0) {
      ArrayList prods = VariablesInventario.vArrayGuiaIngresoProductos;
      for(int i=0;i<prods.size();i++) {
        tableModelResumenPedido.insertRow(((ArrayList)prods.get(i)));    
      }
      prods = null;
    }
    calcularTotal();
  }

        /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnResumenPedido_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(btnResumenPedido);
  }
  
  private void btnResumenPedido_keyPressed(KeyEvent e) {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e) {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(btnResumenPedido);
    //mostrarListadoProductos();
    if(verListaProductos)
    mostrarCabecera();
    cargaDatoMoneda();
    
  }
  
  private void tblResumenPedido_keyPressed(KeyEvent e)
  {
   chkKeyPressed(e);
  }
  
        /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblResumenPedido,null,0);
    /*if(UtilityPtoVenta.verificaVK_F12(e)){ 
      imprimir();                      
    } else */
    if(UtilityPtoVenta.verificaVK_F2(e) && !esGuiaRecepcion)
    { funcion_F2();
     }
    else if(e.getKeyCode() == KeyEvent.VK_F3)
    {
      evento_f3();
    } else if(e.getKeyCode() == KeyEvent.VK_F5)
    {
        evento_f5();
    } else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
        evento_f9();
    } else if(UtilityPtoVenta.verificaVK_F11(e))
    {
        evento_f11();
    } 
    else if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(verListaProductos)
      {
        if(tblResumenPedido.getSelectedRow()>-1)
        {
          cargarCabecera();
          DlgGuiaIngresoCantidad dlgGuiaIngresoCantidad = new DlgGuiaIngresoCantidad(myParentFrame,"",true,vTipoMoneda);
          dlgGuiaIngresoCantidad.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            actualizarProducto();
            FarmaVariables.vAceptar = false;
          }
          
          vTipoMoneda = dlgGuiaIngresoCantidad.pMoneda;
          
          cargaDatoMoneda();
          
        }else
          FarmaUtility.showMessage(this,"Debe seleccionar un producto",btnResumenPedido);
      }
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  private void cerrarVentana(boolean pAceptar) {
    /**
     * Limpiamos las variables que contienen la informacion de la
     * competencia seleccionada
     * @author dubilluz
     * @since  12.11.2007
     */
    VariablesInventario.vDescripcionCompetencia = "";
    VariablesInventario.vRucCompetencia = "";
    FarmaVariables.vAceptar = pAceptar;
    VariablesInventario.vIndAnularNota = "";
    VariablesInventario.vNumNota = "";
		this.setVisible(false);
    this.dispose();
  }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", btnResumenPedido);
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void mostrarListadoProductos() 
  {
    if (verListaProductos)
    {

        vTipoMoneda = "01";
        
        DlgSeleccionMoneda dlgReceta = new DlgSeleccionMoneda(myParentFrame, "", true);
        dlgReceta.setVisible(true);
        if (FarmaVariables.vAceptar) {
           if(dlgReceta.isVSoles()) 
               vTipoMoneda = "01";
           else
               vTipoMoneda = "02";
               
        }
        
        cargaDatoMoneda();        
        
        //FarmaUtility.showMessage(this, ".. se puede entrar", null);  
        VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

        try {
            DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
        } catch(Exception ef) {
                //e.printStackTrace();
        }
        
      DlgGuiaIngresoProductos dlgGuiaIngresoProductos = new DlgGuiaIngresoProductos(myParentFrame,"",true,vTipoMoneda);
      dlgGuiaIngresoProductos.setVisible(true);
      
      if(FarmaVariables.vAceptar)
      {
        cargaListaResumenPedido();
        FarmaVariables.vAceptar = false;
      }
      
        vTipoMoneda = dlgGuiaIngresoProductos.pMoneda;
      
        cargaDatoMoneda();        
    }
  }
  
  private void borrarProducto()
  {
    String cod = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),0).toString();
    
    for(int i=0;i<VariablesInventario.vArrayGuiaIngresoProductos.size();i++)
    {
      if(((ArrayList)VariablesInventario.vArrayGuiaIngresoProductos.get(i)).contains(cod))
      {
        VariablesInventario.vArrayGuiaIngresoProductos.remove(i);
        break;
      }
    }
    cargaListaResumenPedido();
    tblResumenPedido.repaint();
  }
  
  private void cargarCabecera()
  {
    VariablesInventario.vCodProd = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),0).toString();
    VariablesInventario.vNomProd = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),1).toString();
    VariablesInventario.vUnidMed = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),2).toString();
    VariablesInventario.vNomLab = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),3).toString();
    
    VariablesInventario.vCant = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),4).toString();
    VariablesInventario.vLote = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),7).toString();
    VariablesInventario.vFechaVec = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),8).toString();
    VariablesInventario.vPrecUnit = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),5).toString();
    
    VariablesInventario.vValFrac_Guia = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),9).toString();
    
      VariablesInventario.vDatoAdicional = tblResumenPedido.getValueAt(tblResumenPedido.getSelectedRow(),10).toString();
  }
  
  private void actualizarProducto()
  {
    ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(4,VariablesInventario.vCant);
    ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(7,VariablesInventario.vLote);
    ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(8,VariablesInventario.vFechaVec);
    ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(5,VariablesInventario.vPrecUnit);
    ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(6,VariablesInventario.vTotal);
    
      ((ArrayList)(VariablesInventario.vArrayGuiaIngresoProductos.get(tblResumenPedido.getSelectedRow()))).set(10,VariablesInventario.vDatoAdicional);
    
    cargaListaResumenPedido();
  }
  
  private void mostrarDatos()
  {
    lblFechaGuia.setText(VariablesInventario.vFecGuia);
    lblTipoComp.setText(VariablesInventario.vDescDoc);
    lblNrocomp.setText(VariablesInventario.vNumDoc);
    lblTipoGuia.setText(VariablesInventario.vNomOrigen);
    lblLocal.setText(VariablesInventario.vDescOrigen);
    
      cargaDatoMoneda();
  }

  private boolean validaDatos()
  {
    boolean retorno = true;
    if(!FarmaUtility.validaFecha(lblFechaGuia.getText().trim(),"dd/MM/yyyy"))
    {
      FarmaUtility.showMessage(this,"Debe ingresar los datos de Cabecera.",btnResumenPedido);
      retorno = false;
    }else if(tblResumenPedido.getRowCount()==0)
    {
      FarmaUtility.showMessage(this,"No ha ingresado productos a esta guia.",btnResumenPedido);
      retorno = false;
    }
    
    return retorno;
  }
  
  private boolean grabar()
  {
    boolean retorno;
    try
    {
      String numera = DBInventario.agregarCabeceraGuiaIngreso(VariablesInventario.vFecGuia, VariablesInventario.vTipoDoc,
                                                              VariablesInventario.vNumDoc, 
                                                              VariablesInventario.vTipoOrigen, 
                                                              VariablesInventario.vCodOrigen, 
                                                              tblResumenPedido.getRowCount()+"", 
                                                              FarmaUtility.sumColumTable(tblResumenPedido,6)+"",VariablesInventario.vNombreTienda,
                                                              VariablesInventario.vCiudadTienda,VariablesInventario.vRucTienda,
                                                              
                                                              vTipoMoneda
                                                              
                                                              );
      //log.debug(numera);
      for(int i=0;i<tblResumenPedido.getRowCount();i++)
      {
        DBInventario.agregarDetalleGuiaIngreso(numera,VariablesInventario.vTipoOrigen,
        tblResumenPedido.getValueAt(i,0).toString(),tblResumenPedido.getValueAt(i,5).toString(), 
        tblResumenPedido.getValueAt(i,6).toString(), tblResumenPedido.getValueAt(i,4).toString(), 
        VariablesInventario.vFecGuia,tblResumenPedido.getValueAt(i,8).toString(),
        tblResumenPedido.getValueAt(i,7).toString(),tblResumenPedido.getValueAt(i,9).toString(),
          // 
          tblResumenPedido.getValueAt(i,10).toString()
                                               );
      }
      //FarmaUtility.aceptarTransaccion();
      //FarmaUtility.liberarTransaccion();
      limpiarDatosCabecera();
      retorno = true;
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar los datos del pedido : \n"+sql.getMessage(),btnResumenPedido);
      retorno = false;
    }catch(Exception ex)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",ex);
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar los datos del pedido : \n"+ex.getMessage(),btnResumenPedido);
      retorno = false;
    }
    return retorno;
  }
  
  private void cargarDetalle()
  {
    try
    {
      ArrayList array = new ArrayList();
      array = DBInventario.cargaCabeceraGuiaIngreso(VariablesInventario.vNumNota, VariablesInventario.vNumDoc);
      array = (ArrayList)array.get(0);
      lblFechaGuia.setText(array.get(0).toString());
      lblTipoComp.setText(array.get(1).toString());
      lblNrocomp.setText(array.get(2).toString());
      lblTipoGuia.setText(array.get(3).toString());
      lblLocal.setText(array.get(4).toString());
      vEstadoNota = array.get(5).toString();
      if(vEstadoNota.equals(FarmaConstants.INDICADOR_N))
        lblEstado.setText("ANULADO");
      //log.debug("ESTADO:"+vEstadoNota);  
      DBInventario.cargaDetalleGuiaIngreso(VariablesInventario.vNumNota,VariablesInventario.vNumDoc,VariablesInventario.vTipoNota);
      cargaListaResumenPedido();
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al cargar los datos de la gu�a : \n" +sql.getMessage(),null);
    }
    //JSANTIVANEZ 21.10.2010
    //if (lblTipoGuia.getText().equalsIgnoreCase("LOCAL")) {
     //   lblF2.setVisible(false);
        
   // }
  }
  /**
   * Modificado para enviale un parametro de numero de cotizacion
   * @author dubilluz
   * @since  26.11.2007
   */
  private boolean anular(String pNumNota)
  {
    boolean retorno;
    try
    {
      DBInventario.anularGuiaIngreso(pNumNota);
      //FarmaUtility.aceptarTransaccion();
      retorno = true;
    }catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      //log.debug(e.getMessage().substring(11,e.getMessage().indexOf("ORA-06512")-1));
      if(sql.getErrorCode() == 20001)
        FarmaUtility.showMessage(this,"No existe Stock Disponible para anular esta Guia.",btnResumenPedido);
      else if(sql.getErrorCode() == 20002)
        FarmaUtility.showMessage(this,"La Fracci�n Actual no permite anular esta Guia.\n"+sql,btnResumenPedido);
      else
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al anular.\n"+sql.getMessage(),btnResumenPedido);
      }
      retorno = false;
    }catch(Exception ex)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",ex);
      FarmaUtility.showMessage(this, "Error en la aplicacion al grabar el pedido : \n"+ex,btnResumenPedido);
      retorno = false;
    }
    return retorno; 
  }

  private void limpiarDatosCabecera(){
	  VariablesInventario.vFecGuia = "";
	  VariablesInventario.vTipoDoc = "";
	  VariablesInventario.vDescDoc = "";
	  VariablesInventario.vNumDoc = "";
	  VariablesInventario.vTipoOrigen = "";
	  VariablesInventario.vNomOrigen = "";
	  VariablesInventario.vCodOrigen = "";
	  VariablesInventario.vDescOrigen = "";
  }
  
  private void imprimir() {
  
    if(tblResumenPedido.getRowCount()==0)
    return;
		if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
			return;
		//FarmaPrintService vPrint = new FarmaPrintService(45, FarmaVariables.vImprReporte, true);
		FarmaPrintService vPrint = new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
		if (!vPrint.startPrintService()) {
			FarmaUtility.showMessage(this,
					"No se pudo inicializar el proceso de impresi�n",
					btnResumenPedido);
			return;
		}
		try {
                log.debug("IMPRIMIENDO");
			String fechaActual = FarmaSearch
					.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
			vPrint.setStartHeader();
			vPrint.activateCondensed();
			vPrint.printBold(FarmaPRNUtility.llenarBlancos(27)
					+ " REPORTE GUIA DE INGRESO - RESUMEN DE PEDIDO ", true);
      vPrint.printBold(" ", true);
      vPrint.printBold("Fecha Guia       : " + lblFechaGuia.getText().trim(), true);
      vPrint.printBold("Tipo de Guia     : " + lblTipoGuia.getText().trim(), true);
      vPrint.printBold("Tipo Comprobante : " + lblTipoComp.getText().trim(), true);
      vPrint.printBold("Nro. Comprobante : " + lblNrocomp.getText().trim(), true);
      vPrint.printBold("Local            : " + lblLocal.getText().trim(), true);      
      vPrint.printBold(" ", true);
      vPrint.printLine(
							"===================================================================================================",
							true);
			vPrint
					.printBold(
							"Codigo Descripcion              Unidad       Laboratorio            Cantidad   Pre.U.    Total",
							true);
			vPrint
					.printLine(
							"===================================================================================================",
							true);
			vPrint.deactivateCondensed();
			vPrint.setEndHeader();
			
      for (int i = 0; i < tblResumenPedido.getRowCount(); i++) {

				vPrint.printCondensed(
              FarmaPRNUtility.alinearIzquierda(	(String) tblResumenPedido.getValueAt(i, 0), 6) + " "
						+ FarmaPRNUtility.alinearIzquierda(	(String) tblResumenPedido.getValueAt(i, 1), 24) + " "
						 + FarmaPRNUtility.alinearIzquierda(	(String) tblResumenPedido.getValueAt(i, 2), 12)  + " "
						+ FarmaPRNUtility.alinearIzquierda( (String) tblResumenPedido.getValueAt(i, 3), 22) + " "
						 + FarmaPRNUtility.alinearDerecha(	(String) tblResumenPedido.getValueAt(i, 4), 8) + " "
					  	+ FarmaPRNUtility.alinearDerecha(	(String) tblResumenPedido.getValueAt(i, 5), 8)  + " "
					  	+ FarmaPRNUtility.alinearDerecha( (String) tblResumenPedido.getValueAt(i, 6), 8) + " "
						 , true);
			}
			vPrint.activateCondensed();
			vPrint
					.printLine(
							"===========================================================================================================================",
							true);
			vPrint.printBold("Registros Impresos: "
					+ FarmaUtility.formatNumber(tblResumenPedido.getRowCount(),
							",##0") + FarmaPRNUtility.llenarBlancos(11), true);
			vPrint.deactivateCondensed();
			vPrint.endPrintService();
		} catch (SQLException sql) {
			log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al imprimir : \n" + sql.getMessage(),btnResumenPedido);
		}
    
	}

  private void mostrarCabecera() {
      
     
          DlgGuiaIngresoCabecera dlgGuiaIngresoCabecera = new DlgGuiaIngresoCabecera(myParentFrame,"",true);
          dlgGuiaIngresoCabecera.setVisible(true);
          if(FarmaVariables.vAceptar) {  
            mostrarDatos();
            cabecera = true;
        
            mostrarListadoProductos();
            FarmaVariables.vAceptar = false;
          }      
      
     
    
  }
  
  private boolean verificaComprobante()
  {
    boolean retorno;
    if(!vEstadoNota.equals(FarmaConstants.INDICADOR_N))
    {
      try
      {
        String estadoProcesado = DBInventario.getEstadoProcesoSap(VariablesInventario.vNumNota,ConstantsPtoVenta.TIP_NOTA_INGRESO);
        String estadoProcCierreDia = DBInventario.getEstadoProcesoCierreDia(VariablesInventario.vNumNota,ConstantsPtoVenta.TIP_NOTA_INGRESO);
        if(estadoProcesado.equals(FarmaConstants.INDICADOR_S))
        {
          if(VariablesInventario.vTipoNota.equals(ConstantsInventario.TIP_NOTA_GUIA) && VariablesInventario.vTipoNotaOrigen.equals(ConstantsInventario.TIP_NOTA_ORIGEN_GUIA))
          {
            if(cargaLoginOper()) retorno = true;
            else
            {
              retorno = false;
              FarmaUtility.showMessage(this,"Esta Gu�a ha sido procesado por la interface SAP.\nSolo un Operador de Sistemas puede anularla.",btnResumenPedido);
            }
          } else
          {
          retorno = false;
          FarmaUtility.showMessage(this,"Esta Gu�a ha sido procesado por la interface SAP. No puede ser anulada.",btnResumenPedido);
          }
        }else if (estadoProcCierreDia.equals(FarmaConstants.INDICADOR_S))
        {
          retorno = false;
          FarmaUtility.showMessage(this,"Esta Gu�a ha sido registrado en un Cierre de D�a. No puede ser anulada.",btnResumenPedido);
        }
        else
        {
          retorno = true;
        }
      }catch(SQLException e)
      {
        retorno = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al consultar estado.\n"+e,btnResumenPedido);  
      }
    }else
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Esta Guia Ingreso ya esta anulada.",btnResumenPedido);
    }
    return retorno;
  } 
  
  private void calcularTotal()
  {
    double total = 0.00;
    int cantidad = tblResumenPedido.getRowCount();
    
    if (cantidad > 0)
    {
      for (int i = 0; i < cantidad; i++)
      {
        total += 
            FarmaUtility.getDecimalNumber(tblResumenPedido.getValueAt(i,6).toString().trim());
      }  
    }    
    
    lblTotal.setText(FarmaUtility.formatNumber(total, 2));
  }
  
  private boolean cargaLoginOper()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }
  
  private void funcion_F2(){
      if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
            if(FarmaVariables.vEconoFar_Matriz)
              FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnResumenPedido);
            else
            if(!verListaProductos)
            {
              //JSANTIVANEZ 21.10.2010
              if (!lblTipoGuia.getText().equalsIgnoreCase("LOCAL")) {
                  
                      if(verificaComprobante())
                      {
                        if(JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro de anular la Guia Ingreso?"))
                        {
                          if(anular(VariablesInventario.vNumNota))
                          {
                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this,"Gu�a Ingreso fue anulada.",btnResumenPedido);
                            cerrarVentana(true);
                          }
                        }
                      }
              }    
              
            }
          }    
            else{
             FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opci�n",null);
            }
  }

    private void evento_f11() {

        if(validaDatos() && verListaProductos)
          if (JConfirmDialog.rptaConfirmDialog(this, "Est� seguro de generar la guia de ingreso?"))
          {
            
            
              
            if(grabar())
            {
              //Procedera a anular la cotizacion si se dio la opcion
              //26.11.2007 dubilluz 
              if(VariablesInventario.vIndAnularNota.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                 log.debug("Anulando la cotizacion Nro: " + VariablesInventario.vNumNota_Anular);
                    if(anular(VariablesInventario.vNumNota_Anular))
                    { 
                      FarmaUtility.aceptarTransaccion();
                      FarmaUtility.showMessage(this, "Guia generada satisfactoriamente!", tblResumenPedido);
                      cerrarVentana(true);
                    }
              }
              else{
               FarmaUtility.aceptarTransaccion();
               FarmaUtility.showMessage(this, "Guia generada satisfactoriamente!", tblResumenPedido);
               cerrarVentana(true);
              }
            }
          }
    }

    private void lblF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void evento_f3() {
        if(cabecera)
        mostrarListadoProductos();
    }

    private void lblF3_mouseClicked(MouseEvent e) {
        evento_f3();
    }

    private void evento_f5() {

        if(verListaProductos)
        {
          if(tblResumenPedido.getSelectedRow()>-1)
          {  
            if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de borrar el producto de la guia?"))
              borrarProducto();
          }
          else
            FarmaUtility.showMessage(this,"Debe seleccionar un producto",btnResumenPedido);  
        }
    }

    private void lblF5_mouseClicked(MouseEvent e) {
        evento_f5();
    }

    private void evento_f9() {

        if(verListaProductos)
        {
          if(!cabecera)
            mostrarCabecera();
        }
    }

    private void lblF9_mouseClicked(MouseEvent e) {
        evento_f9();
    }

    private void lblF2_mouseClicked(MouseEvent e) {
        funcion_F2();
    }

    private void cargaDatoMoneda() {
        if(vTipoMoneda.equalsIgnoreCase("01"))
            btnMoneda.setText("SOLES ( S/ )");
        else
            btnMoneda.setText("DOLARES ( $ )");
    }

    private void btnMoneda_actionPerformed(ActionEvent e) {
        
    }

    private void btnMoneda_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnMoneda_mouseClicked(MouseEvent e) {
        DlgSeleccionMoneda dlgReceta = new DlgSeleccionMoneda(myParentFrame, "", true);
        dlgReceta.setVisible(true);
        if (FarmaVariables.vAceptar) {
           if(dlgReceta.isVSoles()) 
               vTipoMoneda = "01";
           else
               vTipoMoneda = "02";
               
        }
        
        cargaDatoMoneda();   
    }
}
