package venta.inventario;
import componentes.gs.componentes.JLabelFunction;
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
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

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

import dental.laboratorio.DlgMantProducto;
import dental.laboratorio.reference.DBMantenimiento;

import java.awt.Toolkit;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import javax.swing.KeyStroke;

import venta.inventario.reference.*;

import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.DlgSeleccionMoneda;

import venta.modulo_venta.reference.DBModuloVenta;

import venta.reference.VariablesPtoVenta;

public class DlgGuiaIngresoProductos extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgGuiaIngresoProductos.class);   
     
  Frame myParentFrame;
  FarmaTableModel tableModel;
  ArrayList arrayProductos = new ArrayList();
  
  ArrayList arrayProductosTEMP = new ArrayList();

    private ArrayList modelBase = new ArrayList();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JScrollPane scrListaProductos = new JScrollPane();
  private JPanel pnlHeader = new JPanel();
  private JButton btnListaProducto = new JButton();
  private JPanel pnlTitle = new JPanel();
  private JButton btnBuscar = new JButton();
  private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
  private JButton btnProducto = new JButton();
  private JTable tblListaProductos = new JTable();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblMensajeFiltro = new JLabel();
    private JButton btnMoneda = new JButton();
    
    public String pMoneda = "01";

    private JPopupMenu popup;
    private JMenuItem itemModificarProd,itemAjusteStock,itemNuevoProd,
        itemInactivarProducto;

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgGuiaIngresoProductos()
  {
    this(null, "", false,"01");
  }

  public DlgGuiaIngresoProductos(Frame parent, String title, boolean modal,
                                 String pMoneda)
  {
    super(parent, title, modal);
    myParentFrame = parent;
      this.pMoneda = pMoneda;
      cargaDatoMoneda();
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
    this.setSize(new Dimension(1036, 477));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Guia de Ingreso - Lista de Productos");
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
    jContentPane.setSize(new Dimension(594, 405));
    lblEnter.setText("[ ENTER ] Seleccionar/Deseleccionar Producto");
    lblEnter.setBounds(new Rectangle(15, 420, 270, 20));
    scrListaProductos.setBounds(new Rectangle(10, 90, 1005, 320));
    scrListaProductos.setBackground(new Color(255, 130, 14));
    pnlHeader.setBounds(new Rectangle(10, 65, 1005, 25));
    pnlHeader.setBackground(new Color(0, 114, 169));
    pnlHeader.setLayout(null);
    btnListaProducto.setText("Lista de Productos");
    btnListaProducto.setBounds(new Rectangle(10, 0, 145, 25));
    btnListaProducto.setBackground(new Color(255, 130, 14));
    btnListaProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnListaProducto.setBorderPainted(false);
    btnListaProducto.setContentAreaFilled(false);
    btnListaProducto.setDefaultCapable(false);
    btnListaProducto.setFocusPainted(false);
    btnListaProducto.setFont(new Font("SansSerif", 1, 11));
    btnListaProducto.setForeground(Color.white);
    btnListaProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnListaProducto.setRequestFocusEnabled(false);
    pnlTitle.setBounds(new Rectangle(5, 5, 1010, 55));
    pnlTitle.setBackground(new Color(0, 114, 169));
    pnlTitle.setLayout(null);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(680, 10, 115, 25));
    btnBuscar.setFont(new Font("SansSerif", 1, 12));
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.setMnemonic('b');
    btnBuscar.setRequestFocusEnabled(false);
    txtProducto.setBounds(new Rectangle(110, 10, 525, 20));
    txtProducto.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtProducto_keyPressed(e);
                }

        public void keyReleased(KeyEvent e)
        {
                    txtProducto_keyReleased(e);
                }
      });
        txtProducto.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtProducto_focusLost(e);
                }
            });
        btnProducto.setText("Producto :");
    btnProducto.setBounds(new Rectangle(25, 10, 70, 20));
    btnProducto.setFont(new Font("SansSerif", 1, 12));
    btnProducto.setForeground(Color.white);
    btnProducto.setBackground(new Color(43, 141, 39));
    btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnProducto.setBorderPainted(false);
    btnProducto.setContentAreaFilled(false);
    btnProducto.setDefaultCapable(false);
    btnProducto.setFocusPainted(false);
    btnProducto.setMnemonic('p');
    btnProducto.setRequestFocusEnabled(false);
    btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnProducto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnProducto_actionPerformed(e);
        }
      });
    btnProducto.addContainerListener(new ContainerAdapter()
      {
        public void componentRemoved(ContainerEvent e)
        {
          btnProducto_componentRemoved(e);
        }
      });
        tblListaProductos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListaProductos_mouseClicked(e);
                }
            });
        lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(820, 425, 105, 20));
        lblF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF11_mouseClicked(e);
                }
            });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(930, 425, 85, 20));
        lblMensajeFiltro.setBounds(new Rectangle(15, 35, 675, 15));
        lblMensajeFiltro.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        btnMoneda.setBounds(new Rectangle(815, 10, 160, 30));
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
        scrListaProductos.getViewport();
        pnlHeader.add(btnListaProducto, null);
        pnlTitle.add(btnMoneda, null);
        pnlTitle.add(lblMensajeFiltro, null);
        pnlTitle.add(btnBuscar, null);
        pnlTitle.add(txtProducto, null);
        pnlTitle.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
    jContentPane.add(scrListaProductos, null);
    jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle, null);
    }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel      = new FarmaTableModel(ConstantsInventario.columnsListaGuiaIngresoProductos,ConstantsInventario.defaultListaGuiaIngresoProductos,0);
    tableModel.data = VariablesModuloVentas.tableModelListaGlobalProductos.data;
    
    modelBase = (ArrayList)(VariablesModuloVentas.tableModelListaGlobalProductos.data.clone());
    
    FarmaUtility.initSelectList(tblListaProductos,tableModel,ConstantsInventario.columnsListaGuiaIngresoProductos);
    for (int i=0; i < VariablesModuloVentas.tableModelListaGlobalProductos.getRowCount(); i++)
            VariablesModuloVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false),i,0);
    cargaProductosSeleccionados();
    
      if (tableModel.getRowCount() > 0)
          FarmaUtility.ordenar(tblListaProductos, tableModel, 
                               2, 
                               FarmaConstants.ORDEN_ASCENDENTE);
    FarmaGridUtils.showCell(tblListaProductos,0,0);
  }
  
 
  
  private void cargaLista()
  {
    try
    {
      DBInventario.cargaListaProductosGuiaIngreso(tableModel);
      FarmaUtility.ordenar(tblListaProductos,tableModel,2,FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al cargar la lista de productos : \n" + sql.getMessage(),txtProducto);
    }
    
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtProducto);
  }

  private void btnProducto_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtProducto);
  }
  
  private void txtProducto_keyPressed(KeyEvent e) {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, new JTextField(), 2);
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      //e.consume();
      /*if (tblListaProductos.getSelectedRow() >= 0) {
        if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtProducto.getText().trim(), 1, 2)) ) 
        {
          FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtProducto);
          return;
        }
      }*/
    } 
  
    chkKeyPressed(e);
  }


    private void btnProducto_componentRemoved(ContainerEvent e)
  {
    FarmaUtility.moveFocus(txtProducto);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
      cerrarVentana(false);
    //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      seleccionarProducto();
    }else if(UtilityPtoVenta.verificaVK_F11(e)) 
    {
      VariablesInventario.vArrayGuiaIngresoProductos = arrayProductos;
      cerrarVentana(true);
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }
  
  private void cerrarVentana(boolean pAceptar) {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */

  private void seleccionarProducto()
  {
    
      //if(tblListaProductos.getSelectedRow()>=0){
          boolean seleccion = ((Boolean)tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),0)).booleanValue();
          if(!seleccion)
          {
            cargaCabeceraIngreseCantidad();
            DlgGuiaIngresoCantidad dlgGuiaIngresoCantidad = new DlgGuiaIngresoCantidad(myParentFrame,"",true,pMoneda);
            dlgGuiaIngresoCantidad.setVisible(true); 
            if(FarmaVariables.vAceptar)
            {
              agregarProducto();
              FarmaUtility.setCheckValue(tblListaProductos,false);
              
                
                  for(int j=0;j<tblListaProductos.getRowCount();j++)
                  {
                    if(((ArrayList)tableModel.getRow(j)).contains(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1)))
                    {
                      tableModel.setValueAt(new Boolean(true),j,0);
                      break;
                    }
                  }
              
              FarmaVariables.vAceptar = false;
              tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos,VariablesInventario.vPos);
              tblListaProductos.repaint();
              //FarmaUtility.showMessage(this,"muestra", txtProducto);
            }
            
            pMoneda = dlgGuiaIngresoCantidad.pMoneda;
            
            cargaDatoMoneda();
            
            // cerrar productos agregar
            //cerrarVentana(true);
            
          }
          else
          {
            borrarProducto();
            FarmaUtility.setCheckValue(tblListaProductos,false);
          }          
      //}
    
  }

  private void cargaCabeceraIngreseCantidad()
  {
    VariablesInventario.vCodProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1).toString();
    VariablesInventario.vNomProd = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),2).toString();
    VariablesInventario.vUnidMed = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),3).toString();
    VariablesInventario.vNomLab = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),4).toString();
    VariablesInventario.vStkFisico = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),5).toString();
    VariablesInventario.vValFrac_Guia = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString();
    VariablesInventario.vCant = "";
    VariablesInventario.vLote = "";
    VariablesInventario.vFechaVec = "";
    VariablesInventario.vPrecUnit = "";
    VariablesInventario.vDatoAdicional = "";
    VariablesInventario.vPos = tblListaProductos.getSelectedRow();
  }
  
  private void agregarProducto()
  {
    ArrayList array = new ArrayList();
    array.add(VariablesInventario.vCodProd);
    array.add(VariablesInventario.vNomProd);
    array.add(VariablesInventario.vUnidMed);
    array.add(VariablesInventario.vNomLab);
    array.add(VariablesInventario.vCant);
    array.add(VariablesInventario.vPrecUnit);
    array.add(VariablesInventario.vTotal);
    array.add(VariablesInventario.vLote);
    array.add(VariablesInventario.vFechaVec);
    array.add(VariablesInventario.vValFrac_Guia);
    //dubilluz 20181110
    array.add(VariablesInventario.vDatoAdicional);
    arrayProductos.add(array);
    
    arrayProductosTEMP.add(array.clone());
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
    if(VariablesInventario.vArrayGuiaIngresoProductos.size()>0)
    {
      arrayProductos = VariablesInventario.vArrayGuiaIngresoProductos;
      String cod;
      
      /*for(int i=0;i<arrayProductos.size();i++)
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
      }*/
    }
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
        if (tableModel.getRowCount() > 0)
            FarmaUtility.ordenar(tblListaProductos, tableModel, 
                                 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
    }
    
    
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
    }
    
    private void filtrarBusquedaGoogle() {
        String condicion = txtProducto.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModel.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String cliente = fila.get(2).toString().toUpperCase().trim();
                String ruc = fila.get(1).toString().toUpperCase().trim();
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
            
            String cod;
            // arrayProductosTEMP
            for(int i=0;i<arrayProductosTEMP.size();i++)
            {
              cod = ((ArrayList)arrayProductosTEMP.get(i)).get(0).toString();
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
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblListaProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblListaProductos, 0, 0);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        
        
        if(tblListaProductos.getRowHeight()==0&&txtProducto.getText().trim().length()==0){
            clonarListadoProductos();
            lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
        }
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER|| 
            e.getKeyCode() == KeyEvent.VK_F11|| 
            e.getKeyCode() == KeyEvent.VK_ESCAPE
           )){
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
             int vFoco = tblListaProductos.getSelectedRow();
             //txtFiltroCategoria.setText(""+vFoco);
            if (tblListaProductos.getSelectedRow() >= 0)
            {   seleccionarProducto();
                  FarmaUtility.moveFocus(txtProducto);
             }
             //System.out.println("- "+vFoco);
        }
        }
    }

    private void txtProducto_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void lblF11_mouseClicked(MouseEvent e) {
        VariablesInventario.vArrayGuiaIngresoProductos = arrayProductos;
        cerrarVentana(true);
    }
    
    private void cargaDatoMoneda() {
        if(pMoneda.equalsIgnoreCase("01"))
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
               pMoneda = "01";
           else
               pMoneda = "02";
               
        }
        
        cargaDatoMoneda();
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
                
                FarmaUtility.showMessage(this, "Se inactivo el producto",txtProducto);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al inactivar el producto\n"+
                                               e.getMessage(),txtProducto);
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
            tableModel.fireTableDataChanged();
            tblListaProductos.repaint();  
            
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
            tableModel.fireTableDataChanged();
            tblListaProductos.repaint();  
            
            filtroGoogle();
        }
    }
      
}
