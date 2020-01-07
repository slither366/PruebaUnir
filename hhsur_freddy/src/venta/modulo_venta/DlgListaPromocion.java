package venta.modulo_venta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.reference.UtilityPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaPromocion extends JDialog 
{

  /* ************************************************************************ */
  /*                          DECLARACION PROPIEDADES                         */
  /* ************************************************************************ */
  private static final Logger log = LoggerFactory.getLogger(DlgListaPromocion.class);

  Frame myParentFrame;
  private FarmaTableModel tableModelListaPromociones;
  private final int COL_COD = 0;
  private final int COL_DESC = 1;
  private final int COL_PREC_PACK = 2;
  private final int COL_STK_PACK = 3;
  private final int COL_IND_PERMITIDO = 5;  
  private final int COL_DESL = 6;  
  private final int COL_CODP1 = 7;
  private final int COL_CODP2 = 8;
  private final int COL_DESC_LARGA = 9; 
  
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private JPanelHeader pnlCliente1 = new JPanelHeader();
  private JTextFieldSanSerif txtPromocion = new JTextFieldSanSerif();
  private JButtonLabel btnPromocion = new JButtonLabel();
  private JPanelTitle jPanelTitle1 = new JPanelTitle();
  private JLabelFunction lblAceptar = new JLabelFunction();
  private JButtonLabel btnListado = new JButtonLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblPromociones = new JTable();
  private JLabelFunction lblAceptar1 = new JLabelFunction();
    private JTextArea txtDescPromocion = new JTextArea();
  private JScrollPane jScrollPane2 = new JScrollPane();
  JPanel pnlStock2 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  private JButtonLabel btnDescripcion = new JButtonLabel();
    
 private String pCodCampanaCarga = "N";

  /* ************************************************************************ */
  /*                          CONSTRUCTORES                                   */
  /* ************************************************************************ */

  public DlgListaPromocion()
  {
   this(null, "", false);
  }

  public DlgListaPromocion(Frame parent, String title, boolean modal)
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
  
    public DlgListaPromocion(Frame parent, String title, boolean modal,String pCodCampana)
    {
      super(parent, title, modal);
      myParentFrame = parent;
      try
      {
        jbInit();
        initialize();
        pCodCampanaCarga = pCodCampana;
      }
      catch (Exception e)
      {
        log.error("",e);
      }
    }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(823, 505));
    this.setTitle("Listado de Packs");
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
    pnlCliente1.setBounds(new Rectangle(10, 10, 790, 40));
    txtPromocion.setBounds(new Rectangle(45, 5, 435, 30));
    txtPromocion.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          txtPromocion_keyReleased(e);
        
        }
        public void keyPressed(KeyEvent e)
        {
         txtPromocion_keyPressed(e);
        }
      });
   tblPromociones.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblPromociones_keyPressed(e);
        }
      });
    lblAceptar1.setBounds(new Rectangle(705, 445, 95, 20));
    lblAceptar1.setText("[ESC] Salir ");
        txtDescPromocion.setEnabled(false);
    txtDescPromocion.setFont(new Font("Arial", 0, 21));
    jScrollPane2.setBounds(new Rectangle(10, 275, 790, 155));
    pnlStock2.setBounds(new Rectangle(10, 250, 790, 25));
    pnlStock2.setFont(new Font("SansSerif", 0, 11));
    pnlStock2.setBackground(new Color(0, 114, 169));
    pnlStock2.setLayout(xYLayout4);
    pnlStock2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pnlStock2.setForeground(Color.white);
    btnDescripcion.setText("Descripción");
    btnDescripcion.setMnemonic('1');
      
    btnPromocion.setText("Pack");
    btnPromocion.setBounds(new Rectangle(20, 20, 65, 20));
    btnPromocion.setMnemonic('P');
    btnPromocion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPromocion_actionPerformed(e);
         // btnEmpresa_actionPerformed(e);
        }
      });
    jPanelTitle1.setBounds(new Rectangle(10, 60, 790, 25));
    jPanelTitle1.setLayout(null);
    lblAceptar.setBounds(new Rectangle(585, 445, 110, 20));
    lblAceptar.setText("[ENTER] Aceptar");
    btnListado.setText("Listado de Packs");
    btnListado.setBounds(new Rectangle(5, 0, 145, 25));
    btnListado.setMnemonic('L');
    btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListado_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(10, 85, 790, 155));
    pnlStock2.add(btnDescripcion, new XYConstraints(5, 0, 70, 20));
    jPanelWhite1.add(pnlStock2, null);
    jScrollPane2.getViewport().add(txtDescPromocion, null);
    jPanelWhite1.add(jScrollPane2, null);
        jPanelWhite1.add(lblAceptar1, null);
        jScrollPane1.getViewport().add(tblPromociones, null);
    jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(lblAceptar, null);
        jPanelTitle1.add(btnListado, null);
    jPanelWhite1.add(jPanelTitle1, null);
    jPanelWhite1.add(btnPromocion, null);
    pnlCliente1.add(txtPromocion, null);
    jPanelWhite1.add(pnlCliente1, null);
    this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
        VariablesModuloVentas.ACCION = VariablesModuloVentas.ACCION_CREAR;
    FarmaVariables.vAceptar= false;
    initTableListaPromociones();
    
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
  
  private void initTableListaPromociones()
  {
    tableModelListaPromociones = new FarmaTableModel(ConstantsModuloVenta.columnsListaPromociones, ConstantsModuloVenta.defaultValuesListaPromociones,COL_COD);
    FarmaUtility.initSimpleList(tblPromociones,tableModelListaPromociones, ConstantsModuloVenta.columnsListaPromociones);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */


  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtPromocion);
    ConstantsModuloVenta.ESTADO_LISTADO="P";
    listarPromociones(pCodCampanaCarga);
    FarmaUtility.setearPrimerRegistro(tblPromociones,txtPromocion,COL_DESC);
    
    cargaCampanaBusqueda();
  }

  private void this_windowClosing(WindowEvent e)
  {
   FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.",null);
        ConstantsModuloVenta.ESTADO_LISTADO="P";
  }
  

  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */
  
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
            ConstantsModuloVenta.ESTADO_LISTADO="P";
      cerrarVentana(false);
    }
     else if(UtilityPtoVenta.verificaVK_F1(e))
    {
       listarPromociones("N");
    }
     else if(UtilityPtoVenta.verificaVK_F11(e))
    {
      //mostrarDetallePromocion();
    }
    
  }
   private void txtPromocion_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblPromociones, txtPromocion,COL_DESC);
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    { 
     e.consume();      
      mostrarDetallePromociones();
    }
    else chkKeyPressed(e);
  }
    private void txtPromocion_keyReleased(KeyEvent e)
  {
   FarmaGridUtils.buscarDescripcion(e,tblPromociones,txtPromocion,COL_DESC);
   mostrarDetallePromocion();
  }
  private void btnPromocion_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtPromocion);
  }
  
   private void btnListado_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblPromociones);
  }
  private void tblPromociones_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
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
 
 /**
  * Se lista todas las Promociones en las que participa es un producto
  */
  private void listarPromociones(String vBusqueda)
  {
    try
    {
      //DBVentas.listaPromociones(tableModelListaPromociones);
      /*if(ConstantsModuloVenta.ESTADO_LISTADO.equalsIgnoreCase("T"))
      {
                DBModuloVenta.listaPromociones(tableModelListaPromociones);
        lblVerTodos.setText("[F1] Ver Por Producto");
        txtPromocion.setText("");
                ConstantsModuloVenta.ESTADO_LISTADO="P";
      }
      else
      {
                DBModuloVenta.listaPromocionesPorProducto(tableModelListaPromociones, VariablesModuloVentas.vCodProdFiltro);
       lblVerTodos.setText("[F1] Ver todas");
                ConstantsModuloVenta.ESTADO_LISTADO="T";
       }*/
        
      DBModuloVenta.listaPromociones(tableModelListaPromociones,vBusqueda);
     // lblVerTodos.setText("[F1] Ver Por Producto");
      txtPromocion.setText("");
      ConstantsModuloVenta.ESTADO_LISTADO="P";
        
      if(tblPromociones.getRowCount()>0)
      {
        FarmaUtility.ordenar(tblPromociones,tableModelListaPromociones,COL_COD,FarmaConstants.ORDEN_ASCENDENTE);
      }
    }catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrio un error al listar las Promociones.\n"+sql.getMessage(),txtPromocion);
    }
  }

 /**
  * Se incova al formulario Detalle de promociones, que contiene el detalle de los paquetes
  */
  private void mostrarDetallePromociones()
  {
  
   int row = tblPromociones.getSelectedRow();
   String codprom=FarmaUtility.getValueFieldJTable(tblPromociones,row,COL_COD);
        VariablesModuloVentas.vCod_Prom=codprom;
        VariablesModuloVentas.vDesc_Prom=FarmaUtility.getValueFieldJTable(tblPromociones,row,COL_DESL);
          
    if (row > -1) {
     String indAplicable = FarmaUtility.getValueFieldJTable(tblPromociones,row,COL_IND_PERMITIDO);
     
     if(indAplicable.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                VariablesModuloVentas.vCodProm=codprom;
     log.debug("<<TCT 12>> Antes del detalle Promocion");
     DlgDetallePromocion dlgdetalleprom=new DlgDetallePromocion(myParentFrame,"",true);
     dlgdetalleprom.setVisible(true); 
     log.debug("<<TCT 12>> Despues del detalle Promocion");
     if(FarmaVariables.vAceptar)
     {
      cerrarVentana(true);
     }
     else{
         //si da escape y fue cargado automaticamente
         //se debe cerrar
         if(!pCodCampanaCarga.equalsIgnoreCase("N"))
            cerrarVentana(false);
     }
     }
     else
     FarmaUtility.showMessage(this,"La promocion " + VariablesModuloVentas.vDesc_Prom+
                                " no se puede seleccionar.",txtPromocion);
     
    }
  }
  
  
  /**
   * Se muestra el detalle de la promocion 
   * @author JCORTEZ
   * @since 08.04.2008
   */  
  private void mostrarDetallePromocion()
  {    
    if(tblPromociones.getRowCount()>0){
    txtDescPromocion.setLineWrap(true);
    txtDescPromocion.setWrapStyleWord(true);
    int vFila = tblPromociones.getSelectedRow();
    //log.debug("<<TCT 12>> Detalle Promocion: "+tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString());
    txtDescPromocion.setText(tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString());
            VariablesModuloVentas.vDescProm=tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString();
   /* String msg ="<html>";
    String[] cadena = VariablesVentas.vDescProm.split("&");
    //txtDescPromocion.setText("<html>sdsd<br>sssssdsd<br></html>");
      for(int i=0;i<cadena.length;i++)
      {
        msg += cadena[i] + "<br>";
      }
    msg += "</html>";
    lblDescProm.setText(msg);*/
    }
  }


    private void cargaCampanaBusqueda() {
        if(tblPromociones.getRowCount()>0) {
            if(!pCodCampanaCarga.equalsIgnoreCase("N")){
                txtDescPromocion.setLineWrap(true);
                txtDescPromocion.setWrapStyleWord(true);
                int vFila = tblPromociones.getSelectedRow();
                //log.debug("<<TCT 12>> Detalle Promocion: "+tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString());
                txtDescPromocion.setText(tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString());
                        VariablesModuloVentas.vDescProm=tblPromociones.getValueAt(vFila,COL_DESC_LARGA).toString();
                mostrarDetallePromociones();
            }
        }
    }
}
