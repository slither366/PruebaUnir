package venta.inventario;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
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
import java.awt.SystemColor;
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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import dental.laboratorio.DlgMantProducto;
import dental.laboratorio.reference.DBMantenimiento;

import java.awt.Toolkit;

import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import javax.swing.KeyStroke;

import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.VariablesPtoVenta;


public class DlgTransferenciasListaProductos extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasListaProductos.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;
  ArrayList arrayProductos = new ArrayList();

    private ArrayList modelBase = new ArrayList();
  private String stkProd = "";
  private String indProdCong = "";
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JButtonLabel btnNuscar = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
  private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JLabel lblMensajeFiltro = new JLabel();
    

    private JPopupMenu popup;
    private JMenuItem itemModificarProd,itemAjusteStock,itemNuevoProd,
        itemInactivarProducto;
    

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasListaProductos()
  {
    this(null, "", false);
  }

  public DlgTransferenciasListaProductos(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();

        popup = new JPopupMenu(); //creamos el menu saliente
        
        // itemInactivarProducto
        itemInactivarProducto = new JMenuItem("Inactivar Producto");
        itemInactivarProducto.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 inactivarProducto();
               }
             });
        popup.add(itemInactivarProducto);
        
        itemModificarProd = new JMenuItem("Modificar Producto");
               itemModificarProd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
               itemModificarProd.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                        muestraModificaProducto();
                      }
                    });
               popup.add(itemModificarProd);

               itemAjusteStock = new JMenuItem("Ajuste Stock");                   
               itemAjusteStock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
               itemAjusteStock.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                        muestraAjusteKardex();
                      }
                    });
               popup.add(itemAjusteStock);
        
        
               // itemNuevoProd
               itemNuevoProd = new JMenuItem("Nuevo Producto");
               itemNuevoProd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
               itemNuevoProd.addActionListener(new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                        nuevoProducto();
                      }
                    });
               popup.add(itemNuevoProd);   
        
      FarmaUtility.centrarVentana(this);
    }
    catch(Exception e)
    {
      log.error("",e);
    }
  }

  /* ************************************************************************ */
	/*                                  METODO jbInit                           */
	/* ************************************************************************ */

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(766, 438));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista de Productos de Transferencia");
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 745, 55));
    pnlTitle1.setBounds(new Rectangle(10, 70, 745, 25));
    scrListaProductos.setBounds(new Rectangle(10, 95, 745, 270));
    scrListaProductos.setBackground(new Color(255, 130, 14));
        tblListaProductos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListaProductos_mouseClicked(e);
                }
            });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(665, 375, 90, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(555, 375, 100, 20));
    lblEnter.setText("[ ENTER ] Seleccionar");
    lblEnter.setBounds(new Rectangle(15, 375, 195, 20));
    btnNuscar.setText("Buscar:");
    btnNuscar.setBounds(new Rectangle(15, 10, 55, 15));
    btnNuscar.setMnemonic('B');
    btnNuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNuscar_actionPerformed(e);
        }
      });
    txtBuscar.setBounds(new Rectangle(75, 10, 330, 20));
    txtBuscar.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtBuscar_keyPressed(e);
                }

        public void keyReleased(KeyEvent e)
        {
                    txtBuscar_keyReleased(e);
                }
      });
    btnRelacionProductos.setText("Relacion de Productos");
    btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
    btnRelacionProductos.setMnemonic('R');
    btnRelacionProductos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionProductos_actionPerformed(e);
        }
      });
        lblMensajeFiltro.setBounds(new Rectangle(15, 35, 545, 15));
        lblMensajeFiltro.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(scrListaProductos, null);
    pnlTitle1.add(btnRelacionProductos, null);
    jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblMensajeFiltro, null);
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnNuscar, null);
        jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
    setJTable(tblListaProductos,txtBuscar);
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
      
      
      tblListaProductos.getTableHeader().setReorderingAllowed(false);
      tblListaProductos.getTableHeader().setResizingAllowed(false);
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaProductosTransferencias,ConstantsInventario.defaultValuesListaProductosTransferencias,0);
    tableModel.data = VariablesModuloVentas.tableModelListaGlobalProductos.data;
      
    modelBase = (ArrayList)(VariablesModuloVentas.tableModelListaGlobalProductos.data.clone());
      
    FarmaUtility.initSelectList(tblListaProductos,tableModel,ConstantsInventario.columnsListaProductosTransferencias);
    
    for (int i=0; i < VariablesModuloVentas.tableModelListaGlobalProductos.getRowCount(); i++)
            VariablesModuloVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false),i,0);  
    
      if(!UtilityInventario.indNuevaTransf())
        cargaProductosSeleccionados();
      
      FarmaGridUtils.showCell(tblListaProductos,0,0);
  }
  
  private void cargaListaProductos()
  {
    try
    {
      DBInventario.cargaListaProductosTransferencia(tableModel);
      FarmaUtility.ordenar(tblListaProductos,tableModel,2,FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar los productos : \n" + sql.getMessage(),txtBuscar);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnNuscar_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtBuscar);
  }

  private void btnRelacionProductos_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaProductos);
  }

    private void txtBuscar_keyPressed(KeyEvent e)
    {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, new JTextField(), 2);
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   limpiaCadenaAlfanumerica(txtBuscar);
        }*/
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            evento_enter(e);
        } */
        chkKeyPressed(e);
             
        
    }

    private String buscaCodBarra()
    {   UtilityPtoVenta.limpiaCadenaAlfanumerica(txtBuscar); 
        //pCodigoBarra = productoBuscar;

        String productoBuscar="";
        try
        {   productoBuscar = DBModuloVenta.obtieneCodigoProductoBarra(txtBuscar.getText());
            return productoBuscar;
        }
        catch (SQLException q)
        {
            log.error("",q);
            return "000000";
        }
    }
    
  private void txtBuscar_keyReleased(KeyEvent e)
  {
    /*FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,2);
    if(tblListaProductos.getSelectedRow()>-1)
      muestraInfoProd();*/
    
    
    if(tblListaProductos.getRowHeight()==0&&txtBuscar.getText().trim().length()==0){
        clonarListadoProductos();
        lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
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
      if(tblListaProductos.getSelectedRow()>-1)
        muestraInfoProd();
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtBuscar); 
    if(UtilityInventario.indNuevaTransf()){
      if(VariablesInventario.vKeyPress!=null)
      {
       if(VariablesInventario.vBusquedaProdTransf.trim().length()>0)
       {
         txtBuscar.setText(VariablesInventario.vBusquedaProdTransf.trim());
         //txtBuscar.selectAll();
         log.debug("Entra busca: "+VariablesInventario.vKeyPress.toString());
           txtBuscar_keyReleased(VariablesInventario.vKeyPress);
           txtBuscar_keyPressed(VariablesInventario.vKeyPress);      
       }
       else {
           txtBuscar.setText(VariablesInventario.vKeyPress.getKeyChar()+"");
           //txtBuscar.selectAll();
           txtBuscar_keyReleased(VariablesInventario.vKeyPress);
           txtBuscar_keyPressed(VariablesInventario.vKeyPress);
       }
       /*
       else if(VariablesInventario.vBusquedaProdTransf.trim().length()>0){
         txtBuscar.setText(VariablesInventario.vBusquedaProdTransf.trim());
           txtBuscar.selectAll();
         log.debug("Entra busca vKeyPress: "+VariablesInventario.vKeyPress.toString());
         txtBuscar_keyPressed(VariablesInventario.vKeyPress);     
       }
       else
       {
         txtBuscar.setText(VariablesVentas.vKeyPress.getKeyChar() + "");
           txtBuscar.selectAll();
           log.debug("Entra nombre: "+VariablesInventario.vKeyPress.toString());
         txtBuscar_keyReleased(VariablesInventario.vKeyPress);     
       }      
*/
      }
    }
    
    txtBuscar.setText("");
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtBuscar);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      log.info("diego...");  
     seleccionarProducto();
    }
    if(UtilityPtoVenta.verificaVK_F11(e))
    {
   
    VariablesInventario.vArrayTransferenciaProductos = arrayProductos;
    cerrarVentana(true);
            
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de cerrar la ventana.\n" +
                                         "Asegúrese de presionar F11 para grabar todos los datos antes de salir?")) 
      {
      //if(!ConstantsInventario.IND_CAMBIO_DU)
        if(!UtilityInventario.indNuevaTransf()){
         cancelaOperacion();
        }        
      cerrarVentana(false);
      }
    }
  }

  private void cerrarVentana(boolean pAceptar)
	{
		FarmaVariables.vAceptar = pAceptar;
                VariablesInventario.vKeyPress = null;
                limpiarVariables(); 
		this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private void seleccionarProducto()
  {
    boolean seleccion = ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0)).booleanValue();
    log.info("1. seleccion"+seleccion);
    if(!seleccion)
    {         log.info("2");
      if(!validaStockDisponible())
      {
        return;
      }
      if(!validaProductoTomaInventario(tblListaProductos.getSelectedRow()))
      {
        FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario", txtBuscar);
        return;
      }
      
      cargaCabeceraIngreseCantidad();
        log.info("Abrir ingreso CAnt");
      DlgTransferenciasIngresoCantidad dlgTransferenciasIngresoCantidad = new DlgTransferenciasIngresoCantidad(myParentFrame,"",true);
      dlgTransferenciasIngresoCantidad.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        seleccionaProducto(); 
        agregarProducto_02(VariablesInventario.secRespStk);
        FarmaUtility.setCheckValue(tblListaProductos, false);
        FarmaVariables.vAceptar = false;
        tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos,VariablesInventario.vPos);
          if(UtilityInventario.indNuevaTransf()){
              //Como si fuera F11
              VariablesInventario.vArrayTransferenciaProductos = arrayProductos;
              cerrarVentana(true);   
          }
      }
    }
    else
    {
      deseleccionaProducto();
      borrarProducto();
      FarmaUtility.setCheckValue(tblListaProductos,false);
    }
    
  }
  
  private void cargaCabeceraIngreseCantidad()
  {
    VariablesInventario.vCodProd_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    VariablesInventario.vNomProd_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),2).toString();
    VariablesInventario.vUnidMed_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),3).toString();
    VariablesInventario.vNomLab_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    VariablesInventario.vStkFisico_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
    VariablesInventario.vValFrac_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
    VariablesInventario.vPrecVta_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),6).toString();
    

    //JCORTEZ 20.07.09  Se elimina la coma del formato precio
     double aux=0.00;
    log.debug("**********JCORTEZ......Precio producto-->"+VariablesInventario.vPrecVta_Transf);
    
      aux+=FarmaUtility.getDecimalNumber((String)(VariablesInventario.vPrecVta_Transf.trim()));
      VariablesInventario.vPrecVta_Transf=aux+"";
      
      log.debug("**********JCORTEZ.2.....Precio producto-->"+VariablesInventario.vPrecVta_Transf);
      
    VariablesInventario.vCant_Transf = "";
    VariablesInventario.vLote_Transf = "";
    VariablesInventario.vFechaVec_Transf = "";
    
    VariablesInventario.vPos = tblListaProductos.getSelectedRow();
    
    //Si es a matriz
    if(VariablesInventario.vTipoDestino_Transf.equals(ConstantsPtoVenta.LISTA_MAESTRO_MATRIZ))
    {
      try
      {
        VariablesInventario.vHistoricoLote = DBInventario.getHistoricoLote(VariablesInventario.vCodProd_Transf);
      }catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener el histórico del lote : \n"+ sql.getMessage(),txtBuscar);
      } 
    }
  }
  
  private void agregarProducto()
  {
    ArrayList array = new ArrayList();
    array.add(VariablesInventario.vCodProd_Transf);
    array.add(VariablesInventario.vNomProd_Transf);
    array.add(VariablesInventario.vUnidMed_Transf);
    array.add(VariablesInventario.vNomLab_Transf);
    
    array.add(VariablesInventario.vCant_Transf);
    array.add(VariablesInventario.vPrecVta_Transf);
    array.add(VariablesInventario.vFechaVec_Transf);
    
    array.add(VariablesInventario.vLote_Transf);
    
    array.add(VariablesInventario.vStkFisico_Transf);
    array.add(VariablesInventario.vValFrac_Transf);
    array.add(VariablesInventario.vTotal_Transf);
    array.add(VariablesInventario.vFechaHora_Transf);
    
    arrayProductos.add(array);
  }
  
  private void borrarProducto()
  {
    String cod = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    
    for(int i=0;i<arrayProductos.size();i++)
    {
      if(((ArrayList)arrayProductos.get(i)).contains(cod))
      {
        arrayProductos.remove(i);
        break;
      }
    }
  }
  
  private void cargaProductosSeleccionados()
  {
    if(VariablesInventario.vArrayTransferenciaProductos.size()>0)
    {

      arrayProductos=VariablesInventario.vArrayTransferenciaProductos;
      String cod;
      for(int i=0;i<arrayProductos.size();i++)
      {
        cod = ((ArrayList)arrayProductos.get(i)).get(0).toString();
        for(int j=0;j<tblListaProductos.getRowCount();j++)
        {
          if(((ArrayList)tableModel.getRow(j)).contains(cod))
          {
            tableModel.setValueAt(new Boolean(true),j,0);
            break;
          }
        }
      }
    }
  }
  
  /*private void seleccionaProducto()
  {
    actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf,Integer.parseInt(VariablesInventario.vCant_Transf),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(VariablesInventario.vCant_Transf));
  }
  */
  //JQuispe Cambios 13.04.2010
  private void seleccionaProducto()
  {      
      //if(!actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf,Integer.parseInt(VariablesInventario.vCant_Transf),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(VariablesInventario.vCant_Transf))) antes
      if(!actualizaStkComprometidoProd_02(VariablesInventario.vCodProd_Transf,  //-ASOSA, 14.07.2010
                                          Integer.parseInt(VariablesInventario.vCant_Transf),
                                          ConstantsInventario.INDICADOR_A, 
                                          ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, 
                                          Integer.parseInt(VariablesInventario.vCant_Transf),
                                          "")) 
          log.debug("Error al Seleccionar Producto");
  }
  
  
  private boolean validaStockDisponible()
  {
    if(FarmaUtility.isInteger(stkProd) && Integer.parseInt(stkProd) > 0) 
      return true;
    else 
      return false;
  }
  
  private boolean validaProductoTomaInventario(int pRow)
  {
    if(indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) return true;
    else return false;
  }
  
  private void muestraInfoProd()
  {
    if(tblListaProductos.getRowCount() <= 0) return;
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    if(myArray.size() == 1)
    {
      stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      indProdCong = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesInventario.vFechaHora_Transf = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      VariablesInventario.vValFrac_Transf = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
      
    } else
    {
      stkProd = "";
      indProdCong = "";
      FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtBuscar);
    }
    tblListaProductos.setValueAt(stkProd, tblListaProductos.getSelectedRow(), 5);
    tblListaProductos.setValueAt(indProdCong, tblListaProductos.getSelectedRow(), 8);
    
    tblListaProductos.repaint();
  }
  
  private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
  {
    if(tblListaProductos.getSelectedRow()>-1)
      try
      {
        String codProd = ((String)(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1))).trim();
        DBInventario.obtieneInfoProducto(pArrayList, codProd);
      } catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurrió un error al obtener información del producto : \n" + sql.getMessage(),txtBuscar);
      }
  }
  
 /* 
  private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) {
    try 
    {
      DBInventario.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
      DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto,"", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesInventario.vValFrac_Transf),ConstantsPtoVenta.MODULO_TRANSFERENCIA);
      FarmaUtility.aceptarTransaccion();
    }catch (SQLException sql) 
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,txtBuscar);
    }
  }
  */  
  //JQuispe 14.04.2010 Cambios Transferencias
    private boolean actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo) {
      try 
      {
        DBInventario.actualizaStkComprometidoProd(pCodigoProducto,pCantidadStk,pTipoStkComprometido);
        DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto,"", pTipoRespaldoStock, pCantidadRespaldo, Integer.parseInt(VariablesInventario.vValFrac_Transf),ConstantsPtoVenta.MODULO_TRANSFERENCIA);
        FarmaUtility.aceptarTransaccion();
        return true;
      }catch (SQLException sql) 
      {
        FarmaUtility.liberarTransaccion();
        log.error("",sql);
        FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,txtBuscar);
        return false;
      }
    }
  
  /*private void deseleccionaProducto()
  {
    String cantidad = "";
    VariablesInventario.vCodProd_Transf = ((String)(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1))).trim();
    for(int i=0;i<arrayProductos.size();i++)
    {
      if(((ArrayList)arrayProductos.get(i)).contains(VariablesInventario.vCodProd_Transf))
      {
        cantidad = ((ArrayList)arrayProductos.get(i)).get(4).toString();
        break;
      }
    }
    //log.debug("Cantidad a descontar:"+cantidad);
    actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf,Integer.parseInt(cantidad),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
  }*/
   
  //    JQuispe Cambios 13.04.2010 
    
  private void deseleccionaProducto()
  {
    String cantidad = "";
    VariablesInventario.vCodProd_Transf = ((String)(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1))).trim();
    String secRespaldo=""; //ASOSA, 14.07.2010
    for(int i=0;i<arrayProductos.size();i++)
    {
      if(((ArrayList)arrayProductos.get(i)).contains(VariablesInventario.vCodProd_Transf))
      {
        cantidad = ((ArrayList)arrayProductos.get(i)).get(4).toString();
        secRespaldo = ((ArrayList)arrayProductos.get(i)).get(12).toString(); //ASOSA, 14.07.2010
        break;
      }
    }
    //log.debug("Cantidad a descontar:"+cantidad);
    /*if(!actualizaStkComprometidoProd(VariablesInventario.vCodProd_Transf, antes
                                     Integer.parseInt(cantidad),
                                     ConstantsInventario.INDICADOR_D, 
                                     ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                     Integer.parseInt(cantidad)))*/
        if(!actualizaStkComprometidoProd_02(VariablesInventario.vCodProd_Transf,  //-ASOSA, 14.07.2010
                                            0,
                                            ConstantsInventario.INDICADOR_D, 
                                            ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                            0,
                                            secRespaldo.trim()))
        log.debug("Error al Deseleccionar un Producto");
}   
  /*
    
  private void cancelaOperacion()
  {
    String codProd = "";
    String codProdTmp = "";
    boolean existe = false;
    for(int i=0; i<arrayProductos.size(); i++) {
      codProd = (String)(((ArrayList)arrayProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)arrayProductos.get(i)).get(4));
      for(int j=0; j<VariablesInventario.vArrayTransferenciaProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(j)).get(0));
        if(codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          break;
        }
      }
      if(!existe) actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));
      existe = false;
    }
    for(int i=0; i<VariablesInventario.vArrayTransferenciaProductos.size(); i++) {
      codProd = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(4));
      String cantidadTmp = "";
      for(int j=0; j<arrayProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)arrayProductos.get(j)).get(0));
        if (codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          cantidadTmp = (String)(((ArrayList)arrayProductos.get(j)).get(4));
          break;
        }
      }
      if(!existe) 
        actualizaStkComprometidoProd(codProd, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));
      else{
        if(Integer.parseInt(cantidad) < Integer.parseInt(cantidadTmp))
          actualizaStkComprometidoProd(codProd, (Integer.parseInt(cantidadTmp) - Integer.parseInt(cantidad)),ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, Integer.parseInt(cantidad));
        else if(Integer.parseInt(cantidad) > Integer.parseInt(cantidadTmp))
          actualizaStkComprometidoProd(codProd, (Integer.parseInt(cantidad) - Integer.parseInt(cantidadTmp)),ConstantsInventario.INDICADOR_A, ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, Integer.parseInt(cantidad));
      }
      existe = false;
    }
  } 
    
  */  
  //JQUISPE Cambios 13.04.2010
  private void cancelaOperacion()
  {
    String codProd = "";
    String codProdTmp = "";
    boolean existe = false;
    String secRespaldo=""; //ASOSA, 14.07.2010
    for(int i=0; i<arrayProductos.size(); i++) {
      codProd = (String)(((ArrayList)arrayProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)arrayProductos.get(i)).get(4));
        secRespaldo = (String)(((ArrayList)arrayProductos.get(i)).get(12)); //ASOSA, 14.07.2010
      for(int j=0; j<VariablesInventario.vArrayTransferenciaProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(j)).get(0));
        if(codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          break;
        }
      }
      if(!existe) /*actualizaStkComprometidoProd(codProd, antes
                                               FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                               ConstantsInventario.INDICADOR_D, 
                                               ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                               FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));*/
            actualizaStkComprometidoProd_02(codProd,  //-ASOSA, 14.07.2010
                                            0,
                                            ConstantsInventario.INDICADOR_D, 
                                            ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                            0,
                                            secRespaldo.trim());
      existe = false;
    }
    for(int i=0; i<VariablesInventario.vArrayTransferenciaProductos.size(); i++) {
      codProd = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(0));
      String cantidad = (String)(((ArrayList)VariablesInventario.vArrayTransferenciaProductos.get(i)).get(4));
      String cantidadTmp = "";
      for(int j=0; j<arrayProductos.size(); j++) {
        codProdTmp = (String)(((ArrayList)arrayProductos.get(j)).get(0));
        if (codProd.equalsIgnoreCase(codProdTmp)) {
          existe = true;
          cantidadTmp = (String)(((ArrayList)arrayProductos.get(j)).get(4));
          break;
        }
      }
      if(!existe) 
        /*actualizaStkComprometidoProd(codProd,     antes
                                     FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                     ConstantsInventario.INDICADOR_A, 
                                     ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, 
                                     FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)));*/
        actualizaStkComprometidoProd_02(codProd,        //-ASOSA, 14.07.2010
                                        FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                        ConstantsInventario.INDICADOR_A, 
                                        ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, 
                                        FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                        "");
      else{
        if(Integer.parseInt(cantidad) < Integer.parseInt(cantidadTmp))
          /*actualizaStkComprometidoProd(codProd,   antes
                                       (Integer.parseInt(cantidadTmp) - Integer.parseInt(cantidad)),
                                       ConstantsInventario.INDICADOR_D, 
                                       ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                       Integer.parseInt(cantidad));*/
          actualizaStkComprometidoProd_02(codProd,        //-ASOSA, 14.07.2010
                                          0,
                                          ConstantsInventario.INDICADOR_D, 
                                          ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 
                                          0,
                                          "");
        else if(Integer.parseInt(cantidad) > Integer.parseInt(cantidadTmp))
          /*actualizaStkComprometidoProd(codProd, 
                                       (Integer.parseInt(cantidad) - Integer.parseInt(cantidadTmp)),
                                       ConstantsInventario.INDICADOR_A, 
                                       ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, 
                                       Integer.parseInt(cantidad));*/
          actualizaStkComprometidoProd_02(codProd,        //-ASOSA, 14.07.2010
                                          (Integer.parseInt(cantidad) - Integer.parseInt(cantidadTmp)),
                                          ConstantsInventario.INDICADOR_A, 
                                          ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR, 
                                          FarmaUtility.trunc(FarmaUtility.getDecimalNumber(cantidad)),
                                          "");
      }
      existe = false;
    }
  }
  /**
     * 
     * 
     * @param pJTable
     * @param pText
     */
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,pText,2);
      }
      FarmaUtility.moveFocus(pText);
    }
    
    private void limpiarVariables(){
        VariablesInventario.vBusquedaProdTransf = "";
    }
    
    //-----------------------=ASOSA=-------------------------->>
    
    private void agregarProducto_02(String secRespaldo)
    {
      ArrayList array = new ArrayList();
      array.add(VariablesInventario.vCodProd_Transf); //0
      array.add(VariablesInventario.vNomProd_Transf); //1
      array.add(VariablesInventario.vUnidMed_Transf); //2
      array.add(VariablesInventario.vNomLab_Transf); //3
      
      array.add(VariablesInventario.vCant_Transf); //4
      array.add(VariablesInventario.vPrecVta_Transf); //5
      array.add(VariablesInventario.vFechaVec_Transf); //6
      
      array.add(VariablesInventario.vLote_Transf); //7
      
      array.add(VariablesInventario.vStkFisico_Transf); //8
      array.add(VariablesInventario.vValFrac_Transf); //9
      array.add(VariablesInventario.vTotal_Transf); //10
      array.add(VariablesInventario.vFechaHora_Transf); //11
      array.add(VariablesInventario.secRespStk); //12
      
      arrayProductos.add(array);
    }
    
      private boolean actualizaStkComprometidoProd_02(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido, String pTipoRespaldoStock, int pCantidadRespaldo, String secRespaldo) {
          VariablesInventario.secRespStk="0";
          boolean flag=true;
          return flag;
        /*try 
        {
          
          VariablesInventario.secRespStk=""; //ASOSA, 26.08.2010
            VariablesInventario.secRespStk=DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                              String.valueOf(pCantidadStk),
                                                                              VariablesInventario.vValFrac_Transf,
                                                                              secRespaldo,
                                                                              ConstantsPtoVenta.MODULO_TRANSFERENCIA);
            boolean flag=true;
            if(VariablesInventario.secRespStk.trim().equalsIgnoreCase("N")){
                FarmaUtility.liberarTransaccion();
                flag=false;
            }else{
                FarmaUtility.aceptarTransaccion();
                flag=true;
            }
          return flag;
        }catch (SQLException sql) 
        {
          FarmaUtility.liberarTransaccion();
          log.error("",sql);
          FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,txtBuscar);
          return false;
        }
*/
      }

    private void evento_enter(KeyEvent e) {
        //ERIOS 03.07.2013 Limpia la caja de texto
        
        //e.consume();
        if (tblListaProductos.getSelectedRow() >= 0)
        {
            e.consume();
            String productoBuscar = txtBuscar.getText().trim().toUpperCase();
            String pCodigoBusqueda =  txtBuscar.getText().trim();
            
            //si se pulsa ENTER se verifica si la caja de texto tiene el mismo texto que la descripcion del registro seleccionado
            if(FarmaUtility.findTextInJTable(tblListaProductos,pCodigoBusqueda, 1, 2))
            {   //si se encuentra en la descripcion, mostrar el detalle
                seleccionarProducto();
            }
            //si no son iguales, buscar por codigo
        /*    else if (FarmaUtility.findTextInJTable(tblListaProductos,pCodigoBusqueda, 1, 2) )
            {   //si se encuentra en el codigo, mostrar el detalle
                seleccionarProducto();
            }*/
            //si no se enceuntra codigo, buscar por codigo de barras
            else if(!"000000".equalsIgnoreCase(buscaCodBarra()))
            {   //si se encuentra el cod de barras, ubicarlo en la tabla y mostrar el detalle
                String temp = buscaCodBarra();
                FarmaUtility.findTextInJTable(tblListaProductos,temp, 1, 2);
                seleccionarProducto();
            }
            //si no se encuentra codigo de barras, buscar por otro metodo
            //si no se encuentra mostrar error o mostrar la ventanita del seleccionado?
            else
            {   FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
            }
            
            /*
            String cadena = txtBuscar.getText().trim();
            String pCodigoBarra;
            String codigo = "";
            // revisando codigo de barra
            char primerkeyChar = productoBuscar.charAt(0);
            char segundokeyChar;
            boolean pCambioDeBusqueda = false;
            if(productoBuscar.length() > 1)
                segundokeyChar = productoBuscar.charAt(1);
            else
                segundokeyChar = primerkeyChar;
        
            char ultimokeyChar = productoBuscar.charAt(productoBuscar.length()-1);
            log.info("productoBuscar:"+productoBuscar);
            if ( productoBuscar.length()>6 && 
                 (!Character.isLetter(primerkeyChar) && 
                  (!Character.isLetter(segundokeyChar) && (!Character.isLetter(ultimokeyChar)))))
            {
                limpiaCadenaAlfanumerica(txtBuscar);
                pCodigoBarra = productoBuscar;
                log.info("codigo Barra:"+pCodigoBarra);
                try
                {
                    productoBuscar = 
                    DBVentas.obtieneCodigoProductoBarra(pCodigoBarra);
                }
                catch (SQLException q)
                {
                    log.error("",q);
                    productoBuscar = "000000";
                }
                log.info("codigo producto:"+productoBuscar);
                pCambioDeBusqueda = true; 
            }
        
            if (productoBuscar.equalsIgnoreCase("000000"))
            {
                FarmaUtility.showMessage(this, "No existe producto relacionado con el Código de Barra. Verifique!!!", txtBuscar);
                return;
            }    

            
        
            if(pCambioDeBusqueda)
            {   pCodigoBusqueda = productoBuscar.trim();
            }
            
            if ( pCodigoBusqueda.length()==6)
            {   limpiaCadenaAlfanumerica(txtBuscar);
                if (!(FarmaUtility.findTextInJTable(tblListaProductos,pCodigoBusqueda, 1, 2)) ) 
                {
                    FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
            }else{
                if (!(FarmaUtility.findTextInJTable(tblListaProductos,pCodigoBusqueda, 2, 2)) ) 
                {
                    FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
            }
            */
        }
        FarmaUtility.moveFocus(txtBuscar);
    }
    
    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < modelBase.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)modelBase.get(i)).clone();
            arrayClone.add(aux);
        }
       // ascasc
        tableModel.clearTable();
        tableModel.data = arrayClone;
        tblListaProductos.repaint();
        tblListaProductos.show();
    }
    
    
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
    }
    
    private void filtrarBusquedaGoogle() {
        String condicion = txtBuscar.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModel.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String cliente = fila.get(1).toString().toUpperCase().trim();
                String ruc = fila.get(2).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(cliente.contains(condicion)||ruc.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModel.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModel.fireTableDataChanged();
            
            if(tblListaProductos.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                if(tblListaProductos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblListaProductos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblListaProductos.getRowCount()+" filas para el filtro aplicado");
            }
        }
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblListaProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblListaProductos, 0, 0);
    }
    
    
    public void muestraModificaProducto(){
    
        if(tblListaProductos.getSelectedRow()>=0){
            String pCodProd = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 1);    
            String pAccion = "U";
            
            DlgMantProducto dlgMantProducto = new DlgMantProducto(myParentFrame, "", true, pAccion,pCodProd);
            dlgMantProducto.setVisible(true);
            
            if(FarmaVariables.vAceptar){
                VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

                try {
                    DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
                } catch(Exception ef) {
                        //e.printStackTrace();
                }
                tableModel.data = VariablesModuloVentas.tableModelListaGlobalProductos.data;
                modelBase = (ArrayList)(VariablesModuloVentas.tableModelListaGlobalProductos.data.clone());
                tableModel.fireTableDataChanged();
                tblListaProductos.repaint();  
                  
                filtroGoogle();
            }
            
        }
        UpdateFilaProd();
        
       // FarmaUtility.moveFocus(txtProducto);
    }
    
    public void inactivarProducto(){
    int pos =  tblListaProductos.getSelectedRow();
        if(tblListaProductos.getSelectedRow()>=0){
            String pCodProd = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 1);


            try {
                DBMantenimiento.inactivaProducto(pCodProd);
                FarmaUtility.aceptarTransaccion();
                
                tableModel.data.remove(pos);
                
                //tblProductos.remove (pos);
                
                for(int i=0;i<VariablesModuloVentas.tableModelListaGlobalProductos.data.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(VariablesModuloVentas.tableModelListaGlobalProductos.data,i,1).trim()
                        .equalsIgnoreCase(pCodProd)) {
                        VariablesModuloVentas.tableModelListaGlobalProductos.data.remove(i);
                    }
                }
                
                tblListaProductos.repaint();
                
                FarmaUtility.showMessage(this, "Se inactivo el producto",txtBuscar);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al inactivar el producto\n"+
                                               e.getMessage(),txtBuscar);
            }
        }
        UpdateFilaProd();
        
       // FarmaUtility.moveFocus(txtProducto);
    }
      
    public void nuevoProducto(){
        
            String pCodProd = "N";
            String pAccion = "I";
            
            DlgMantProducto dlgMantProducto = new DlgMantProducto(myParentFrame, "", true, pAccion,pCodProd);
            dlgMantProducto.setVisible(true);
            
        if(FarmaVariables.vAceptar){
            VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

            try {
                DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
            } catch(Exception ef) {
                    //e.printStackTrace();
            }
            tableModel.data = VariablesModuloVentas.tableModelListaGlobalProductos.data;
              
            modelBase = (ArrayList)(VariablesModuloVentas.tableModelListaGlobalProductos.data.clone());
            
            filtroGoogle();
        }
            
    }
    
    
    public void muestraAjusteKardex(){
        /*
        new FarmaColumnData( "Sel", 30, JLabel.CENTER ),
        new FarmaColumnData( "Código", 60, JLabel.LEFT ),
        new FarmaColumnData( "Descripción", 330, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 135, JLabel.LEFT ),
        new FarmaColumnData( "Categoría", 150, JLabel.LEFT ),
        new FarmaColumnData( "Stock", 45, JLabel.RIGHT ),
        
         * */
        VariablesInventario.vCodProd         = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 1);
        VariablesInventario.vDescProd        = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 2);
        
        
        VariablesInventario.vNomLab          = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 4);
        
        VariablesInventario.vCant            = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 5);
        VariablesInventario.vCantFrac        = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 5);
        VariablesInventario.vFrac            = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 9);
        VariablesInventario.vIndProdVirtual  = "N";//FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), COL_COD);
        VariablesInventario.vStock           = Integer.parseInt(FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 5).replace(",", "")) ;
              // = FarmaUtility.getValueFieldJTable(myJTable, tblProductos.getSelectedRow(), 9);
        
        ArrayList myArray = new ArrayList();
        try {
            DBModuloVenta.obtieneInfoProductoVta(myArray, VariablesInventario.vCodProd);
            VariablesInventario.vCFraccion = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            VariablesInventario.vDescUnidPresent =((String)((ArrayList)myArray.get(0)).get(1)).trim();
            VariablesInventario.vDescUnidFrac    = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
            
        } catch (SQLException e) {
            VariablesInventario.vCFraccion  = "1";
            VariablesInventario.vDescUnidPresent = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 3); // mal
            VariablesInventario.vDescUnidFrac    = FarmaUtility.getValueFieldJTable(tblListaProductos, tblListaProductos.getSelectedRow(), 3);// ok
            
        }
        
        
        DlgAjusteKardex dlgAjusteKardex = new DlgAjusteKardex(myParentFrame, "", true);
        dlgAjusteKardex.setVisible(true);
        
        UpdateFilaProd();
        
        //FarmaUtility.moveFocus(txtProducto);
    
    }
        

    private void UpdateFilaProd() {

        if(FarmaVariables.vAceptar){
            VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

            try {
                DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
            } catch(Exception ef) {
                    //e.printStackTrace();
            }
            tableModel.data = VariablesModuloVentas.tableModelListaGlobalProductos.data;
              
            modelBase = (ArrayList)(VariablesModuloVentas.tableModelListaGlobalProductos.data.clone());
            filtroGoogle();
        }
    }

    private void tblListaProductos_mouseClicked(MouseEvent e) {

        int i= tblListaProductos.getSelectedRow();
        //txtProducto.setText(FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,i, 2));
        if (e.isMetaDown() ){
            // click derecho
            //muestraAjusteKardex();
           // FarmaUtility.showMessage(this, "No puede hacer click derecho", txtProducto);
           popup.show(e.getComponent(),
                      e.getX(), e.getY());
        }
        else{
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                 e.consume();
                 //
             }
        }
    }
}
