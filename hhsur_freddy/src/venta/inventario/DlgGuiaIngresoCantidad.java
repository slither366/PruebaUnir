package venta.inventario;
import componentes.gs.componentes.JButtonLabel;
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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;

import javax.swing.JTextField;

import common.*;

import venta.inventario.reference.*;
import venta.reference.*;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.DlgSeleccionMoneda;

public class DlgGuiaIngresoCantidad extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgGuiaIngresoCantidad.class);

  Frame myParentFrame;
  private boolean productoFraccionado = false;
  
  private String descUnidPres = "";
  private String stkProd = "";
  private String valFracProd = "";
  private String descUnidVta = "";
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlHeader1 = new JPanelHeader();
  private JLabelWhite lblDescripcion_T = new JLabelWhite();
  private JLabelWhite lblStockActual_T = new JLabelWhite();
  private JLabelWhite lblLaboratorio_T = new JLabelWhite();
  private JLabelWhite lblStockActual = new JLabelWhite();
  private JLabelWhite lblLaboratorio = new JLabelWhite();
  private JLabelWhite lblDescripcion = new JLabelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtPrecioUnitario = new JTextFieldSanSerif();
  private JLabelWhite lblPrecioUnitario_T = new JLabelWhite();
  private JTextFieldSanSerif txtFechaVencimiento = new JTextFieldSanSerif();
  private JLabelWhite lblFechaVenc_T = new JLabelWhite();
  private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
  private JLabelWhite lblLote = new JLabelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JTextFieldSanSerif txtFraccion = 
    new JTextFieldSanSerif();
    private JLabelOrange lblValorFrac_T = new JLabelOrange();
  private JLabelOrange lblValorFrac = new JLabelOrange();
  private JLabelOrange lblUnidPresent_T = new JLabelOrange();
  private JLabelOrange lblUnidVenta_T = new JLabelOrange();
  private JLabelOrange lblUnidPresent = new JLabelOrange();
  private JLabelOrange lblUnidVenta = new JLabelOrange();
    private JLabel jLabel1 = new JLabel();
    private JLabel lblSubTotal = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtAdicional = new JTextField();
    private JButton btnMoneda = new JButton();

public String pMoneda = "01";
    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgGuiaIngresoCantidad()
  {
    this(null, "", false,"01");
  }

  public DlgGuiaIngresoCantidad(Frame parent, String title, boolean modal,String pMoneda)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    this.pMoneda = pMoneda;
    try
    {
      jbInit();
      initialize();
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
    this.setSize(new Dimension(438, 422));
    this.getContentPane().setLayout(null);
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Ingrese Cantidad Solicitada");
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
    pnlHeader1.setBounds(new Rectangle(10, 10, 415, 160));
    pnlHeader1.setBackground(Color.white);
    pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(0, 114, 169), 1));
    lblDescripcion_T.setText("Descripción:");
    lblDescripcion_T.setBounds(new Rectangle(10, 15, 75, 15));
    lblDescripcion_T.setForeground(new Color(0, 114, 169));
    lblStockActual_T.setText("Stock Actual:");
    lblStockActual_T.setBounds(new Rectangle(10, 60, 75, 15));
    lblStockActual_T.setForeground(new Color(0, 114, 169));
    lblLaboratorio_T.setText("Marca:");
    lblLaboratorio_T.setBounds(new Rectangle(10, 35, 70, 15));
    lblLaboratorio_T.setForeground(new Color(0, 114, 169));
    lblStockActual.setBounds(new Rectangle(90, 60, 55, 15));
    lblStockActual.setFont(new Font("SansSerif", 0, 11));
    lblStockActual.setForeground(new Color(0, 114, 169));
    lblLaboratorio.setBounds(new Rectangle(90, 35, 280, 15));
    lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
    lblLaboratorio.setForeground(new Color(0, 114, 169));
    lblDescripcion.setBounds(new Rectangle(90, 15, 280, 15));
    lblDescripcion.setFont(new Font("SansSerif", 0, 11));
    lblDescripcion.setForeground(new Color(0, 114, 169));
    pnlTitle1.setBounds(new Rectangle(10, 180, 415, 155));
    pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(0, 114, 169), 1));
    pnlTitle1.setBackground(Color.white);
        txtEntero.setBounds(new Rectangle(240, 95, 90, 20));
    txtEntero.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
            txtEntero_keyPressed(e);
          }

        public void keyTyped(KeyEvent e)
        {
          txtEntero_keyTyped(e);
        }

                public void keyReleased(KeyEvent e) {
                    txtEntero_keyReleased(e);
                }
            });
    txtPrecioUnitario.setBounds(new Rectangle(90, 80, 115, 20));
    txtPrecioUnitario.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtPrecioUnitario_keyPressed(e);
                }

        public void keyTyped(KeyEvent e)
        {
          txtPrecioUnitario_keyTyped(e);
        }

                public void keyReleased(KeyEvent e) {
                    txtPrecioUnitario_keyReleased(e);
                }
            });
    lblPrecioUnitario_T.setText("Precio Unit.");
    lblPrecioUnitario_T.setBounds(new Rectangle(10, 75, 70, 15));
    lblPrecioUnitario_T.setForeground(new Color(0, 114, 169));
    txtFechaVencimiento.setBounds(new Rectangle(90, 50, 115, 20));
    txtFechaVencimiento.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaVencimiento_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaVencimiento_keyReleased(e);
        }
      });
    lblFechaVenc_T.setText("Fecha Venc.");
    lblFechaVenc_T.setBounds(new Rectangle(10, 45, 70, 15));
    lblFechaVenc_T.setForeground(new Color(0, 114, 169));
    txtLote.setBounds(new Rectangle(90, 20, 115, 20));
    txtLote.setLengthText(15);
    txtLote.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtLote_keyPressed(e);
        }
      });
    lblLote.setText("Lote/Serie");
    lblLote.setBounds(new Rectangle(10, 20, 70, 15));
    lblLote.setForeground(new Color(0, 114, 169));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(340, 355, 85, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblF11.setBounds(new Rectangle(230, 355, 105, 20));
        lblF11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF11_mouseClicked(e);
                }
            });
        txtFraccion.setBounds(new Rectangle(240, 125, 90, 20));
    txtFraccion.setLengthText(6);
    txtFraccion.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtFraccion_keyPressed(e);
          }

          public void keyTyped(KeyEvent e)
          {
            txtFraccion_keyTyped(e);
          }

                public void keyReleased(KeyEvent e) {
                    txtFraccion_keyReleased(e);
                }
            });
        lblValorFrac_T.setText("Valor Frac:");
    lblValorFrac_T.setBounds(new Rectangle(195, 60, 70, 15));
    lblValorFrac.setBounds(new Rectangle(275, 60, 55, 15));
    lblValorFrac.setFont(new Font("SansSerif", 0, 11));
    lblUnidPresent_T.setText("Unidad Entero:");
    lblUnidPresent_T.setBounds(new Rectangle(10, 100, 79, 15));
    lblUnidVenta_T.setText("Unidad Fracc.:");
    lblUnidVenta_T.setBounds(new Rectangle(15, 130, 79, 15));
    lblUnidPresent.setBounds(new Rectangle(100, 100, 125, 15));
    lblUnidPresent.setFont(new Font("SansSerif", 0, 11));
    lblUnidVenta.setBounds(new Rectangle(105, 130, 125, 15));
    lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
        jLabel1.setText("Sub Total");
        jLabel1.setBounds(new Rectangle(280, 30, 80, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        jLabel1.setForeground(new Color(0, 99, 148));
        lblSubTotal.setText("9999990.00");
        lblSubTotal.setBounds(new Rectangle(275, 60, 105, 25));
        lblSubTotal.setFont(new Font("SansSerif", 1, 12));
        lblSubTotal.setForeground(new Color(0, 99, 148));
        jLabel2.setText("Adicional");
        jLabel2.setBounds(new Rectangle(10, 115, 60, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(new Color(0, 107, 165));
        txtAdicional.setBounds(new Rectangle(90, 115, 150, 20));
        txtAdicional.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtAdicional_keyPressed(e);
                }
            });
        btnMoneda.setBounds(new Rectangle(250, 105, 155, 35));
        btnMoneda.setFont(new Font("SansSerif", 1, 15));
        btnMoneda.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnMoneda_mouseClicked(e);
                }
            });
        btnMoneda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnMoneda_keyPressed(e);
                }
            });
        btnMoneda.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnMoneda_actionPerformed(e);
                }
            });
        pnlHeader1.add(lblUnidVenta, null);
        pnlHeader1.add(lblUnidPresent, null);
        pnlHeader1.add(lblUnidVenta_T, null);
        pnlHeader1.add(lblUnidPresent_T, null);
        pnlHeader1.add(lblValorFrac, null);
        pnlHeader1.add(lblValorFrac_T, null);
        pnlHeader1.add(lblDescripcion, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblStockActual, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblStockActual_T, null);
        pnlHeader1.add(lblDescripcion_T, null);
        pnlHeader1.add(txtEntero, null);
        pnlHeader1.add(txtFraccion, null);
        pnlTitle1.add(btnMoneda, null);
        pnlTitle1.add(txtAdicional, null);
        pnlTitle1.add(jLabel2, null);
        pnlTitle1.add(lblSubTotal, null);
        pnlTitle1.add(jLabel1, null);
        pnlTitle1.add(lblLote, null);
        pnlTitle1.add(txtLote, null);
        pnlTitle1.add(lblFechaVenc_T, null);
        pnlTitle1.add(txtFechaVencimiento, null);
        pnlTitle1.add(lblPrecioUnitario_T, null);
        pnlTitle1.add(txtPrecioUnitario, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    //
    txtEntero.setLengthText(6);    
    txtFechaVencimiento.setLengthText(10);
    txtPrecioUnitario.setLengthText(9);//<100000.000
  }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initCabecera();
    FarmaVariables.vAceptar = false;
    
    cargaDatoMoneda();
  }

  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initCabecera()
  {
    muestraInfoProd();
    
    lblDescripcion.setText(VariablesInventario.vNomProd);
    try
    {
      lblStockActual.setText(DBInventario.getStkDisponibleProd(VariablesInventario.vCodProd));
    }catch(SQLException sql)
    {
      lblStockActual.setText(VariablesInventario.vStkFisico);  
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener el stock disponible del producto : \n" + sql.getMessage(),txtEntero);
    }
    
    lblLaboratorio.setText(VariablesInventario.vNomLab);
    lblValorFrac.setText(VariablesInventario.vValFrac_Guia);
    
    if(!VariablesInventario.vValFrac_Guia.equals("1"))
      productoFraccionado = true;
      
    if(productoFraccionado)
    {
      int cant = 0;
      if(!VariablesInventario.vCant.trim().equals(""))
      {
        cant= Integer.parseInt(VariablesInventario.vCant.trim());
      }
      int frac = Integer.parseInt(VariablesInventario.vValFrac_Guia.trim());
      
      int valFrac = cant%frac;
      int valCant = cant/frac;
      txtEntero.setText(valCant+"");
      txtFraccion.setText(valFrac+"");
    }else
    {
      txtEntero.setText(VariablesInventario.vCant);
      txtFraccion.setText("0");
      txtFraccion.setEditable(false); 
    }
    
    txtLote.setText(VariablesInventario.vLote);
    txtFechaVencimiento.setText(VariablesInventario.vFechaVec);
    
    if(VariablesInventario.vPrecUnit.trim().equals(""))
    {
     txtPrecioUnitario.setText("0.00");
    }
    else
    {
    txtPrecioUnitario.setText(VariablesInventario.vPrecUnit);
        
        
    txtAdicional.setText(VariablesInventario.vDatoAdicional);    
  }
    
  }
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnEntero_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtEntero);
  }

  private void this_windowOpened(WindowEvent e)
  {
      VariablesInventario.vCant = "";
      VariablesInventario.vLote = "";
      VariablesInventario.vFechaVec = "";
      VariablesInventario.vPrecUnit = "";
      VariablesInventario.vTotal = "";
      
      VariablesInventario.vDatoAdicional = "";
          
      lblSubTotal.setText(""+VariablesInventario.vTotal);
      
    FarmaUtility.centrarVentana(this);
    if(!productoFraccionado)
    {
      txtFraccion.setEditable(false);
    }
    FarmaUtility.moveFocus(txtEntero);
    
    cargaCosto(VariablesInventario.vCodProd);
    
  }

  private void txtEntero_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      if(!txtFraccion.isEditable())
      {
        FarmaUtility.moveFocus(txtLote);
      }else
      {
        FarmaUtility.moveFocus(txtFraccion);
      }
    }
    else
      chkKeyPressed(e);  
  }

  private void txtEntero_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtEntero,e);
  }
  
  private void txtFraccion_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtLote);
    }
    else
      chkKeyPressed(e); 
  }
  
  private void txtFraccion_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtFraccion,e);
  }
  
  private void txtLote_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      FarmaUtility.moveFocus(txtFechaVencimiento);
    }
    else
      chkKeyPressed(e);
  }
  
  private void txtFechaVencimiento_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(txtPrecioUnitario);
    else
      chkKeyPressed(e);    
  }

  private void txtFechaVencimiento_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaVencimiento,e);
  }

  private void txtPrecioUnitario_keyPressed(KeyEvent e)
  {
    cargaPrecioEntero();  
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
      FarmaUtility.moveFocus(txtAdicional);
    else
      chkKeyPressed(e);    
  }
    
  private void txtPrecioUnitario_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitosDecimales(txtPrecioUnitario,e);
  }
  
  private void this_windowClosing(WindowEvent e)
  {
   // FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
      cerrarVentana(false);
  }
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if(UtilityPtoVenta.verificaVK_F11(e))
    {
      aceptaCantidadIngresada();      
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

  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

  private boolean validarCampos()
  {
    boolean retorno = true;
    if(txtEntero.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la Cantidad.",txtEntero);
      retorno = false;
    }else if(txtFraccion.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la fraccion.",txtFraccion);
      retorno = false;
    }/*else if(txtLote.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar el Lote.",txtLote);
      retorno = false;
    }/*else if(txtFechaVencimiento.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar la Fecha de Vencimiento.",txtFechaVencimiento);
      retorno = false;
    }*//*else if(!FarmaUtility.validaFecha(txtFechaVencimiento.getText().trim(),"dd/MM/yyyy"))
    {
      FarmaUtility.showMessage(this,"Corrija la Fecha de Vencimiento.",txtFechaVencimiento);
      retorno = false;
    }*/else if(txtPrecioUnitario.getText().trim().length() == 0)
    {
      FarmaUtility.showMessage(this,"Debe Ingresar el Precio Unitario.",txtPrecioUnitario);
      retorno = false;
    }else if(!FarmaUtility.validateDecimal(this,txtPrecioUnitario,"Corrija el Precio Unitario.",false))
      retorno = false;
    else if(Double.parseDouble(txtPrecioUnitario.getText().trim()) >= 100000.000)
    {
      FarmaUtility.showMessage(this,"El Precio Unitario, no debe ser mayor de 99,999.999 .",txtPrecioUnitario);
      retorno = false;
    }
    /*else{
      try
      {
        String fecha = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
        Date sysdate = FarmaUtility.getStringToDate(fecha,"dd/MM/yyyy"),
            fechaVec = FarmaUtility.getStringToDate(txtFechaVencimiento.getText().trim(),"dd/MM/yyyy");
        if(!sysdate.before(fechaVec))
        {
          FarmaUtility.showMessage(this,"La Fecha de Vencimiento debe ser posterior a la fecha de hoy: "+fecha+" ."
          ,txtFechaVencimiento);
          retorno = false;
        }  
      }catch(SQLException sql)
      {
       log.error("",sql);
       FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema : \n" + sql.getMessage(),txtEntero);
      }
      
    }  */
    
    if(VariablesInventario.vTipoOrigen.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA))
      if(Double.parseDouble(txtPrecioUnitario.getText()) < 0.01)
      {
        FarmaUtility.showMessage(this,"Debe ingresar un Precio Mayor de 0.01",txtPrecioUnitario);
        retorno = false;
      }
    return retorno;
  }
  
  private void cargarDatos()
  {
    VariablesInventario.vCant = ""+VariablesInventario.vCantIngreso;
    VariablesInventario.vLote = txtLote.getText().trim();
    VariablesInventario.vFechaVec = txtFechaVencimiento.getText().trim();
    VariablesInventario.vPrecUnit = txtPrecioUnitario.getText().trim();
    // dubilluz 20181110
    VariablesInventario.vDatoAdicional = txtAdicional.getText().trim();
          
    int cant = Integer.parseInt(VariablesInventario.vCant);
    double precio = Double.parseDouble(VariablesInventario.vPrecUnit);
    VariablesInventario.vTotal = (cant*precio)+"";
  }

  private void aceptaCantidadIngresada()
  {
    if(validarCampos())
      {
        if(!validaCantIngreso())
        {
          FarmaUtility.showMessage(this,"La cantidad a ingresar excede el valor máximo de ingreso.",txtEntero);          
          return;
        }
        cargarDatos();
        cerrarVentana(true);  
      }
  }
  
  private boolean validaCantIngreso()
  {
    boolean valor = true;
    
    String cantidad, fraccion; 
    cantidad = txtEntero.getText().trim();
    fraccion = txtFraccion.getText().trim();
    
    if(fraccion.equals(""))
      fraccion = "0";
    
    VariablesInventario.vCantIngreso = 
        Integer.parseInt(cantidad) * Integer.parseInt(VariablesInventario.vValFrac_Guia.trim()) + Integer.parseInt(fraccion);  
    
    if(VariablesInventario.vCantIngreso > 999999)
    {
      valor = false;
    }
    
    return valor;
  }

  private void muestraInfoProd()
  {
    
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    
    if(myArray.size() == 1)
    {
      stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();    
    } else
    {
      stkProd = "";
      descUnidPres = "";
      valFracProd = "";
      descUnidVta = "";      
      FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtEntero);
    }
    
    lblStockActual.setText(stkProd);    
    lblUnidPresent.setText(descUnidPres);
    VariablesInventario.vValFrac_Guia = valFracProd;
    //lblValorFrac.setText(valFracProd);
    lblUnidVenta.setText(descUnidVta);
  }
  
  private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
  {
    String codProd = VariablesInventario.vCodProd;
    try
    {
            DBModuloVenta.obtieneInfoProducto(pArrayList, codProd);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Error al obtener informacion del producto en Arreglo. \n" + sql.getMessage(),txtEntero);
    }
  }

    private void txtPrecioEntero_keyPressed(KeyEvent e) {
   }

    private void cargaPrecioEntero() {
        
    }
    
    private void cargaSubTotal(){
        try {
            if (!validaCantIngreso()) {
                FarmaUtility.showMessage(this, "La cantidad a ingresar excede el valor máximo de ingreso.", txtEntero);
                return;
            } else {
                // calcular el sub total
                VariablesInventario.vCant = "" + VariablesInventario.vCantIngreso;
                VariablesInventario.vLote = txtLote.getText().trim();
                VariablesInventario.vFechaVec = txtFechaVencimiento.getText().trim();
                VariablesInventario.vPrecUnit = txtPrecioUnitario.getText().trim();
                VariablesInventario.vDatoAdicional = txtAdicional.getText().trim();


                int cant = Integer.parseInt(VariablesInventario.vCant);
                double precio = Double.parseDouble(VariablesInventario.vPrecUnit);
                VariablesInventario.vTotal = (cant * precio) + "";

                lblSubTotal.setText("" + VariablesInventario.vTotal);
            }
        } catch (Exception nfe) {
            // TODO: Add catch code
            //nfe.printStackTrace();
        }
    }

    private void txtEntero_keyReleased(KeyEvent e) {
            cargaSubTotal();
    }

    private void txtFraccion_keyReleased(KeyEvent e) {
        cargaSubTotal();
    }

    private void txtPrecioUnitario_keyReleased(KeyEvent e) {
        cargaSubTotal();
    }

    private void lblF11_mouseClicked(MouseEvent e) {
        aceptaCantidadIngresada(); 
    }

    private void txtAdicional_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
          FarmaUtility.moveFocus(txtEntero);
        else
          chkKeyPressed(e);    
    }

    private void cargaCosto(String vCodProd) {
        String pCosto = "";
        try {
            pCosto = DBInventario.getPrecioCosto(vCodProd,pMoneda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        txtPrecioUnitario.setText(pCosto.trim());
    }
    
    private void cargaDatoMoneda() {
        if(pMoneda.equalsIgnoreCase("01"))
            btnMoneda.setText("SOLES ( S/ )");
        else
            btnMoneda.setText("DOLARES ( $ )");
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
        
        cargaCosto(VariablesInventario.vCodProd);
        
        cargaSubTotal();
    }

    private void btnMoneda_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnMoneda_actionPerformed(ActionEvent e) {
    }
}
