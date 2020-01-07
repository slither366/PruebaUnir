package venta;


import componentes.gs.componentes.JLabelFunction;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JLabelFunction;

import venta.administracion.producto.DlgProdImpresion;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgFiltroProductos.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      22.02.2006   Creaci�n<br>
 * ERIOS       07.01.2012   Modificacion<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DlgFiltroProductos extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgFiltroProductos.class);
  private String tipoFiltro;
  private Frame myParentFrame;
  private FarmaTableModel tableModelListaFiltro;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jContentPane = new JPanel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JPanel pnlRelacionFiltros = new JPanel();
  private JLabel lblRelacionFiltros = new JLabel();
  private JPanel pnlIngresarProductos = new JPanel();
  private JComboBox cmbCategoriaFiltro = new JComboBox();
  private JButton btnCategoria = new JButton();
  private JTextField txtProducto = new JTextField();
  private JButton btnProducto = new JButton();
  private JTable tblFiltro = new JTable();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JComboBox cmbTipoProd = new JComboBox();
  private JButton btnTipoProd = new JButton();

// **************************************************************************
// Constructores
// **************************************************************************
  public DlgFiltroProductos()
  {
    this(null, "", false);
  }

  public DlgFiltroProductos(Frame parent, String title, boolean modal)
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
// M�todo "jbInit()"
// **************************************************************************
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(453, 430));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Filtro de Productos");
    this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
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
    jContentPane.setBackground(Color.white);
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(623, 335));
    jContentPane.setForeground(Color.white);
    lblEnter.setText("[ ENTER ] Seleccionar");
    lblEnter.setBounds(new Rectangle(15, 345, 130, 35));
    jScrollPane1.setBounds(new Rectangle(15, 150, 415, 185));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    pnlRelacionFiltros.setBounds(new Rectangle(15, 130, 415, 20));
    pnlRelacionFiltros.setBackground(new Color(0, 114, 169));
    pnlRelacionFiltros.setLayout(null);
    lblRelacionFiltros.setText("Relacion de Filtros");
    lblRelacionFiltros.setBounds(new Rectangle(5, 0, 145, 20));
    lblRelacionFiltros.setFont(new Font("SansSerif", 1, 11));
    lblRelacionFiltros.setForeground(Color.white);
    pnlIngresarProductos.setBounds(new Rectangle(15, 10, 415, 115));
    pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(0,114,169), 1));
    pnlIngresarProductos.setBackground(Color.white);
    pnlIngresarProductos.setLayout(null);
    pnlIngresarProductos.setForeground(new Color(0, 114, 169));
    cmbCategoriaFiltro.setBounds(new Rectangle(115, 10, 220, 30));
    cmbCategoriaFiltro.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    cmbCategoriaFiltro_keyPressed(e);
                }
      });
        cmbCategoriaFiltro.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbCategoriaFiltro_itemStateChanged(e);
                }
            });
        btnCategoria.setText("Categoria :");
    btnCategoria.setBounds(new Rectangle(10, 10, 75, 20));
    btnCategoria.setMnemonic('c');
    btnCategoria.setRequestFocusEnabled(false);
    btnCategoria.setFocusPainted(false);
    btnCategoria.setDefaultCapable(false);
    btnCategoria.setBorderPainted(false);
    btnCategoria.setBackground(new Color(43, 141, 39));
    btnCategoria.setHorizontalAlignment(SwingConstants.LEFT);
    btnCategoria.setFont(new Font("SansSerif", 1, 11));
    btnCategoria.setForeground(new Color(0, 114, 169));
    btnCategoria.setHorizontalTextPosition(SwingConstants.LEADING);
    btnCategoria.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCategoria.setContentAreaFilled(false);
    btnCategoria.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCategoria_actionPerformed(e);
        }
      });
    txtProducto.setBounds(new Rectangle(115, 80, 220, 30));
    txtProducto.setFont(new Font("SansSerif", 1, 11));
    txtProducto.setForeground(new Color(32, 105, 29));
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
    btnProducto.setText("Filtro :");
    btnProducto.setBounds(new Rectangle(10, 80, 60, 25));
    btnProducto.setMnemonic('f');
    btnProducto.setFont(new Font("SansSerif", 1, 11));
    btnProducto.setDefaultCapable(false);
    btnProducto.setRequestFocusEnabled(false);
    btnProducto.setBackground(new Color(50, 162, 65));
    btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnProducto.setFocusPainted(false);
    btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
    btnProducto.setContentAreaFilled(false);
    btnProducto.setBorderPainted(false);
    btnProducto.setForeground(new Color(0, 114, 169));
    btnProducto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnProducto_actionPerformed(e);
        }
      });
    tblFiltro.setFont(new Font("SansSerif", 0, 12));
    tblFiltro.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblFiltro_keyPressed(e);
        }
      });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(320, 345, 110, 35));
    cmbTipoProd.setBounds(new Rectangle(115, 45, 145, 30));
    cmbTipoProd.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    cmbTipoProd_keyPressed(e);
                }
      });
        cmbTipoProd.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbTipoProd_itemStateChanged(e);
                }
            });
        btnTipoProd.setText("Tipo Producto :");
    btnTipoProd.setBounds(new Rectangle(10, 50, 95, 20));
    btnTipoProd.setMnemonic('t');
    btnTipoProd.setRequestFocusEnabled(false);
    btnTipoProd.setFocusPainted(false);
    btnTipoProd.setDefaultCapable(false);
    btnTipoProd.setBorderPainted(false);
    btnTipoProd.setBackground(new Color(43, 141, 39));
    btnTipoProd.setHorizontalAlignment(SwingConstants.LEFT);
    btnTipoProd.setFont(new Font("SansSerif", 1, 11));
    btnTipoProd.setForeground(new Color(0, 114, 169));
    btnTipoProd.setHorizontalTextPosition(SwingConstants.LEADING);
    btnTipoProd.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnTipoProd.setContentAreaFilled(false);
    btnTipoProd.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnTipoProd_actionPerformed(e);
        }
      });
    jScrollPane1.getViewport();
        pnlIngresarProductos.add(btnTipoProd, null);
        pnlIngresarProductos.add(cmbTipoProd, null);
        pnlIngresarProductos.add(cmbCategoriaFiltro, null);
    pnlIngresarProductos.add(btnCategoria, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblFiltro, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(lblRelacionFiltros, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarProductos, null);
  }
  
// **************************************************************************
// M�todo "initialize()"
// **************************************************************************
  private void initialize()
  {
    initTable();
    cargaCombo();
    cargaListaFiltros();
    FarmaVariables.vAceptar = false;
  }

// **************************************************************************
// M�todos de inicializaci�n
// **************************************************************************
  private void initTable()
  {
    tableModelListaFiltro = new FarmaTableModel(ConstantsPtoVenta.columnsListaFiltro,ConstantsPtoVenta.defaultValuesListaFiltro,0);
    FarmaUtility.initSimpleList(tblFiltro,tableModelListaFiltro,ConstantsPtoVenta.columnsListaFiltro);
  }
  
  private void cargaCombo()
  {
    FarmaLoadCVL.loadCVLfromArrays(cmbCategoriaFiltro,ConstantsPtoVenta.HASHTABLE_CATEGORIAS_FILTRO_PRODUCTO,ConstantsPtoVenta.FILTROS_PRODUCTOS_CODIGO,ConstantsPtoVenta.FILTROS_PRODUCTOS_DESCRIPCION,true);
    FarmaLoadCVL.loadCVLfromArrays(cmbTipoProd,ConstantsPtoVenta.HASHTABLE_TIPO_PRODUCTO_FILTRO,ConstantsPtoVenta.FILTROS_TIPO_PRODUCTO_CODIGO,ConstantsPtoVenta.FILTROS_TIPO_PRODUCTO_DESCRIPCION,true);
  }

// **************************************************************************
// Metodos de eventos
// **************************************************************************
  private void tblFiltro_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtProducto);
  }
  
  private void txtProducto_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblFiltro, txtProducto, 1);
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      e.consume();
      if (tblFiltro.getSelectedRow() >= 0)
      {
        if (!(FarmaUtility.findTextInJTable(tblFiltro, txtProducto.getText().trim(), 0, 1)) ) 
        {
          FarmaUtility.showMessage(this,"Filtro No Encontrado seg�n Criterio de B�squeda !!!", txtProducto);
          return;
        } else{
          guardaValoresFiltro();
          cerrarVentana(true);
        }
      }
    }
    chkKeyPressed(e);
  }

  private void cmbCategoriaFiltro_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.HASHTABLE_CATEGORIAS_FILTRO_PRODUCTO, cmbCategoriaFiltro.getSelectedIndex()).equalsIgnoreCase("3")){
        cargaListaFiltros();
        cmbTipoProd.setEnabled(false);
        FarmaUtility.moveFocus(txtProducto);
      } else{
        cmbTipoProd.setEnabled(true);
        FarmaUtility.moveFocus(cmbTipoProd);
      }
    }
    else
      chkKeyPressed(e);
  }
  
  private void cmbTipoProd_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      cargaListaFiltros();
      FarmaUtility.moveFocus(txtProducto);
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtProducto_keyReleased(KeyEvent e)
  {
    FarmaGridUtils.buscarDescripcion(e, tblFiltro, txtProducto, 1);
  }
  
  private void btnProducto_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtProducto);
  }
  
  private void btnTipoProd_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbTipoProd);
  }
  
  private void btnCategoria_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbCategoriaFiltro);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

// **************************************************************************
// Metodos auxiliares de eventos
// **************************************************************************
  private void chkKeyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
    else if(UtilityPtoVenta.verificaVK_F11(e))
    {
      guardaValoresFiltro();
      cerrarVentana(true);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

// **************************************************************************
// Metodos de l�gica de negocio
// **************************************************************************
  
  private void cargaListaFiltros()
  {
    String codigoCategoria = FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.HASHTABLE_CATEGORIAS_FILTRO_PRODUCTO,cmbCategoriaFiltro.getSelectedIndex());
    String tipoProducto = FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.HASHTABLE_TIPO_PRODUCTO_FILTRO,cmbTipoProd.getSelectedIndex());
    try
    {
      DBPtoVenta.cargaListaFiltro(tableModelListaFiltro,codigoCategoria, tipoProducto);
      FarmaUtility.ordenar(tblFiltro, tableModelListaFiltro, 1, FarmaConstants.ORDEN_ASCENDENTE);
      tipoFiltro = codigoCategoria;
    } catch(SQLException e)
    {
      log.error("",e);
    }
  }
  
  private void guardaValoresFiltro()
  {
    VariablesPtoVenta.vCodFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(),0)).trim();
    VariablesPtoVenta.vDescFiltro = ((String)tblFiltro.getValueAt(tblFiltro.getSelectedRow(),1)).trim();
    VariablesPtoVenta.vTipoFiltro = tipoFiltro;
    VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
    VariablesPtoVenta.vDesc_Cat_Filtro = (String)cmbCategoriaFiltro.getSelectedItem();
  }

    private void cmbCategoriaFiltro_itemStateChanged(ItemEvent e) {
        if(cmbCategoriaFiltro.getSelectedIndex()>=0&this.isVisible()){
            cargaListaFiltros();
            FarmaUtility.moveFocus(txtProducto);            
        }

    }

    private void cmbTipoProd_itemStateChanged(ItemEvent e) {
        if(cmbTipoProd.getSelectedIndex()>=0){
        cargaListaFiltros();
        FarmaUtility.moveFocus(txtProducto);
        }
             
    }
}
