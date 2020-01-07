package venta.modulo_venta;



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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import java.awt.Desktop;

import java.io.IOException;

import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.DlgIngresoBoletaFactura;


import venta.impresion.DlgImpresionGuia;
import venta.impresion.Reference.ConstantsReportePDF;

import venta.reportes.DlgDetalleRegistroVentas;
import venta.reportes.DlgListaPedAnulNoCob;
import venta.reportes.DlgOrdenar;
import venta.reportes.DlgResumenVenta;

public class DlgListaPedidosCobrados extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgListaPedidosCobrados.class);

  private FarmaTableModel tableModelRegistroVentas;
  private Frame myParentFrame;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  private JPanelTitle pnlTitulo = new JPanelTitle();
  private JPanelTitle pnlResultados = new JPanelTitle();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblRegistroVentas = new JTable();
  private JButtonLabel btnPeriodo = new JButtonLabel();
  private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
  private JButton btnBuscar = new JButton();
    private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF9 = new JLabelFunction();
  private JLabelFunction lblF12 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    public DlgListaPedidosCobrados()
  {
    this(null, "", false);
  }

  public DlgListaPedidosCobrados(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(1034, 444));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Liste pedidos vendidos");
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
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 1000, 30));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 50, 1000, 20));
        pnlTitulo.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 340, 1000, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 70, 1000, 270));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblRegistroVentas_keyPressed(e);
        }
      });
    btnPeriodo.setText("Periodo :");
    btnPeriodo.setBounds(new Rectangle(150, 5, 60, 20));
    btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtFechaIni.setBounds(new Rectangle(220, 5, 101, 19));
    txtFechaIni.setLengthText(10);
    txtFechaIni.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaIni_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
                    txtFechaIni_keyReleased(e);
                }
      });
    txtFechaFin.setBounds(new Rectangle(345, 5, 101, 19));
    txtFechaFin.setLengthText(10);
    txtFechaFin.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtFechaFin_keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
          txtFechaFin_keyReleased(e);
        }
      });
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(475, 5, 95, 20));
    btnBuscar.setMnemonic('b');
    btnBuscar.setFont(new Font("SansSerif", 1, 11));
    btnBuscar.setFocusPainted(false);
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnBuscar_actionPerformed(e);
                    }
      });
        lblF1.setBounds(new Rectangle(10, 370, 105, 20));
    lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setFocusable(false);
        lblF9.setBounds(new Rectangle(300, 370, 100, 20));
    lblF9.setText("[ F9 ] Ordenar");
        lblF9.setFocusable(false);
        lblF12.setBounds(new Rectangle(730, 370, 165, 20));
    lblF12.setText("[ F12 ] Generar Gu\u00eda");
        lblF12.setFocusable(false);
        lblEsc.setBounds(new Rectangle(900, 370, 110, 20));
    lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
    //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        //jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
    tblRegistroVentas.setFocusable(false);
    jScrollPane1.getViewport().add(tblRegistroVentas, null);
    jContentPane.add(jScrollPane1, null);
    jContentPane.add(pnlResultados, null);
        jContentPane.add(pnlTitulo, null);
    jContentPane.add(pnlCriterioBusqueda, null);
    pnlCriterioBusqueda.add(btnBuscar, null);
    pnlCriterioBusqueda.add(txtFechaFin, null);
    pnlCriterioBusqueda.add(txtFechaIni, null);
    pnlCriterioBusqueda.add(btnPeriodo, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ********************************************************************** */
  /*                            METODO INITIALIZE                           */
  /* ********************************************************************** */
  private void initialize()
  {
    initTableListaRegistroVentas();
  };

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaRegistroVentas()
  {
    tableModelRegistroVentas = new FarmaTableModel(ConstantsReporte.columnsListaRegistroGuia,ConstantsReporte.defaultValuesListaRegistroGuia,0);
    FarmaUtility.initSimpleList(tblRegistroVentas,tableModelRegistroVentas,ConstantsReporte.columnsListaRegistroGuia);
  }
    
  /* ********************************************************************** */
  /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
  /* ********************************************************************** */
  
  private void txtFechaIni_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    FarmaUtility.moveFocus(txtFechaFin);
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    cerrarVentana(false);    
    chkKeyPressed(e);
  }

  private void txtFechaFin_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    btnBuscar.doClick();
    
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    cerrarVentana(false);    
    chkKeyPressed(e);
  }

  private void tblRegistroVentas_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnBuscar_actionPerformed(ActionEvent e)
  {
    busqueda();
  }
  
  private void btnPeriodo_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void btnListado_actionPerformed(ActionEvent e)
  {
    if(tblRegistroVentas.getRowCount() > 0)
    {
      FarmaUtility.moveFocus(tblRegistroVentas);
    }
  }
  
  /* ********************************************************************** */
  /*                            METODOS AUXILIARES                          */
  /* ********************************************************************** */
  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, 
                                            tblRegistroVentas, 
                                            null, 
                                            0);
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
    }
    if(UtilityPtoVenta.verificaVK_F1(e))
    {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else 
        {
            VariablesReporte.vCorrelativo = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
            VariablesReporte.vCliente = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),4)).trim();
            //VariablesReporte.vDireccion = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
            VariablesReporte.vRuc = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
            VariablesReporte.vFecha = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),3)).trim();
            VariablesReporte.vHora = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),8)).trim();
            VariablesReporte.vUsuario = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),9)).trim();
            VariablesReporte.vEstado = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),10)).trim();
            VariablesReporte.vNComprobante = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),2)).trim();
            log.debug(VariablesReporte.vCorrelativo +  VariablesReporte.vCliente + VariablesReporte.vDireccion +
            VariablesReporte.vRuc + VariablesReporte.vFecha + VariablesReporte.vHora +  VariablesReporte.vUsuario+
            VariablesReporte.vEstado);
            listadoDetalleVentas();
        }
    }
    else if(UtilityPtoVenta.verificaVK_F2(e))
    {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else
        {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            log.debug(FechaFin+FechaInicio);
            resumenVentas();
        }
    } 
    /**NUEVO!
     * @Autor:  Luis Reque Orellana
     * @Fecha:  23/04/2007
     * */
   
    else if(e.getKeyCode() == KeyEvent.VK_F9)
    {
    if(tblRegistroVentas.getRowCount() <= 0)
    FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ordenar",txtFechaIni);
    else
    muestraVentaOrdenar();
    } 
    else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(tblRegistroVentas.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de guardar",txtFechaIni);
      else;
      //exportarDatos();
    } else if(UtilityPtoVenta.verificaVK_F12(e))
    {
      if(tblRegistroVentas.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de imprmir",txtFechaIni);
      else
      generarGuia();
      
    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(true);
    }
  }

  private void txtFechaIni_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaIni,e);
  }

  private void txtFechaFin_keyReleased(KeyEvent e)
  {
  FarmaUtility.dateComplete(txtFechaFin,e);
  }
  
   private void buscaRegistroVentas(String pFechaInicio, String pFechaFin)
  {
    VariablesReporte.vFechaInicio = pFechaInicio;
    VariablesReporte.vFechaFin = pFechaFin;
    cargaRegistroVentas();
  }
  
   private void cargaRegistroVentas()
  {
    try
    {
      log.debug(VariablesReporte.vFechaInicio);
      log.debug(VariablesReporte.vFechaFin);
      // muestra del operador para ver todos.
      DBReportes.cargaListaRegistroVentas(tableModelRegistroVentas,VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin,"000");
      if(tblRegistroVentas.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        //lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
      FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
     // lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
     // lblRegistros.setText("" + tblRegistroVentas.getRowCount());
      FarmaUtility.moveFocusJTable(tblRegistroVentas);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaIni);
    }
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  private void listadoDetalleVentas()
  {
    DlgDetalleRegistroVentas dlgDetalleRegistroVentas = new DlgDetalleRegistroVentas(myParentFrame, "", true);
    dlgDetalleRegistroVentas.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }
  
  private void resumenVentas()
  {
    DlgResumenVenta dlgResumenVenta = new DlgResumenVenta(myParentFrame,"",true);
    dlgResumenVenta.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
    }
  }
  
  private boolean validarCampos()
  {
    boolean retorno = true;
     if(!FarmaUtility.validarRangoFechas(this,txtFechaIni,txtFechaFin,false,true,true,true))
      retorno = false;
          
    return retorno;
  }
  
    /**
     * Fecha Modificación: 05/01/2007
     * Usuario: Luis Reque
     * Descripción: Se realiza el ordenamiento por Tipo y Nro. de Comprobante a la vez, no por separados.
     * */
  private void muestraVentaOrdenar()
  {
    DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame,"Ordenar",true);
    //String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo", "No.Comprob", "Fecha","Cliente", "Monto"};
    //String [] IND_CAMPO = {"0","1","2","11","4","5"};
    
    String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo y No.Comprob", "Fecha","Cliente", "Monto"};
    String [] IND_CAMPO = {"0","12","11","4","6"};
    
    log.debug("Campo " + IND_DESCRIP_CAMPO[1] );
    VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEREGISTROVENTAS ;
    FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(),
                                   VariablesReporte.vNombreInHashtable,
                                   IND_CAMPO,
                                   IND_DESCRIP_CAMPO,
                                   true);
    dlgOrdenar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      FarmaVariables.vAceptar = false;
      FarmaUtility.ordenar(tblRegistroVentas,tableModelRegistroVentas,VariablesReporte.vCampo,VariablesReporte.vOrden);
      tblRegistroVentas.repaint();
    }
    
  }
  
  private void exportarDatos() {

		File lfFile = new File("C:\\");
		JFileChooser filechooser = new JFileChooser(lfFile);
		filechooser.setDialogTitle("Seleccione el directorio destino");
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this.myParentFrame) != JFileChooser.APPROVE_OPTION)
			return;
		File fileChoosen = filechooser.getSelectedFile();
		String ruta = fileChoosen.getAbsolutePath();

		FarmaUtility.saveFileRuta(ruta,
				ConstantsReporte.columnsListaRegistroVentas,
				tblRegistroVentas, new int[] { 20, 10, 20, 20,40,25,60,20,0,0,0,0 });

		FarmaUtility.showMessage(this, "Los datos se exportaron correctamente",
				txtFechaIni);

	}
  

  
  private void busqueda()
  {
    if(validarCampos()){
      txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
      txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
      String FechaInicio = txtFechaIni.getText().trim();
      String FechaFin = txtFechaFin.getText().trim();
      if (FechaInicio.length() > 0 || FechaFin.length() > 0 )
      {
      char primerkeyCharFI = FechaInicio.charAt(0);
      char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length()-1);
      char primerkeyCharFF = FechaFin.charAt(0);
      char ultimokeyCharFF = FechaFin.charAt(FechaFin.length()-1);
      
        if ( !Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
             !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)){
              buscaRegistroVentas(FechaInicio,FechaFin);
        }
        else
          FarmaUtility.showMessage(this,"Ingrese un formato valido de fechas",txtFechaIni); 
      }
      else
      FarmaUtility.showMessage(this,"Ingrese datos para la busqueda",txtFechaIni);
    
    }
  }

  private void this_windowClosing(WindowEvent e)
  {
  FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }

  /**NUEVO!
   * @Autor:  Luis Reque Orellana
   * @Fecha:  23/04/2007
   * */
  private void cargaListaPedAnulNoCob()
  {
    try
    {
      DBReportes.cargaListaPedidosAnulNoCob(tableModelRegistroVentas,VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin);
      if(tblRegistroVentas.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        //lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
        FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
      //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
      //lblRegistros.setText("" + tblRegistroVentas.getRowCount());
      FarmaUtility.moveFocusJTable(tblRegistroVentas);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaIni);
    }
  }
  
  
    private void generarGuia() 
    {
        if(tblRegistroVentas.getSelectedRow()>= 0){
            
        String vNumPedVta = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(),0);
    
        DlgSeleccionImpGuia dlgReceta = new DlgSeleccionImpGuia(myParentFrame, "", true);
        dlgReceta.setVisible(true);
        if (FarmaVariables.vAceptar) {
           if(dlgReceta.isVImprime()) {
               DlgIngresoGuia dlgResumenPedido = new DlgIngresoGuia(myParentFrame,"",true,vNumPedVta);
               dlgResumenPedido.setVisible(true);
           }
           else {
               ConstantsReportePDF.rutaPDF = "";
               String pNumPedVta = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
               DlgImpresionGuia dlg = new DlgImpresionGuia(myParentFrame, "", true, pNumPedVta);
               dlg.setVisible(true);
               if(FarmaVariables.vAceptar){
                   try {
                        File path = new File (ConstantsReportePDF.rutaPDF);
                        Desktop.getDesktop().open(path);
                   }catch (IOException ex) {
                        ex.printStackTrace();
                   }
               }
           }
            
        }
      } 
    }
}