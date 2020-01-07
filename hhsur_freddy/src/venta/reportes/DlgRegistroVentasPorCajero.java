package venta.reportes;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import java.awt.Desktop;

import java.io.IOException;

import java.util.ArrayList;

import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import svb.export_pdf_excel.PnlExportTable;

import svb.fact_electronica.pdf.generadorPDF.GeneradorPDF;

import venta.caja.reference.VariablesCaja;

import venta.impresion.DlgImpresionCotizacion;

import venta.impresion.DlgImpresionGuia;

import venta.modulo_venta.reference.DBModuloVenta;

import venta.reference.VariablesPtoVenta;
import venta.impresion.Reference.ConstantsReportePDF;

public class DlgRegistroVentasPorCajero extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroVentasPorCajero.class);

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
  private JButtonLabel btnListado = new JButtonLabel();
  private JLabel lblRegsitros_T = new JLabel();
  private JLabel lblRegistros = new JLabel();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabel lblTotalMonto = new JLabel();
  private JLabelFunction lblVerAnulNoCob = new JLabelFunction();
    private PnlExportTable pnlExpReporte = new PnlExportTable(tblRegistroVentas,ConstantsReporte.columnsListaRegistroVentas);
    private JComboBox cmbCajero = new JComboBox();


    public DlgRegistroVentasPorCajero()
  {
    this(null, "", false);
  }

  public DlgRegistroVentasPorCajero(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(1125, 616));
    this.getContentPane().setLayout(borderLayout1);
     this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE  );
    this.setTitle("Registro de Ventas");
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
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 5, 1090, 40));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 50, 1090, 20));
        pnlTitulo.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 515, 1090, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 70, 1090, 445));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblRegistroVentas_keyPressed(e);
        }
      });
    btnPeriodo.setText("Periodo :");
    btnPeriodo.setBounds(new Rectangle(15, 10, 60, 20));
    btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPeriodo_actionPerformed(e);
        }
      });
    txtFechaIni.setBounds(new Rectangle(85, 10, 101, 19));
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
    txtFechaFin.setBounds(new Rectangle(210, 10, 101, 19));
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
    btnBuscar.setBounds(new Rectangle(665, 10, 95, 20));
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
    btnListado.setText("Listado de Registro de Ventas");
    btnListado.setBounds(new Rectangle(10, 0, 200, 20));
    btnListado.setMnemonic('l');
        btnListado.setFocusable(false);
        btnListado.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnListado_actionPerformed(e);
        }
      });
    lblRegsitros_T.setText("Registros :");
    lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
    lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
    lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblRegistros.setText("0");
    lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
    lblRegistros.setFont(new Font("SansSerif", 1, 11));
    lblRegistros.setForeground(Color.white);
    lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setFocusable(false);
        lblF1.setBounds(new Rectangle(5, 545, 105, 20));
    lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setFocusable(false);
        lblF9.setBounds(new Rectangle(485, 545, 100, 20));
    lblF9.setText("[ F9 ] Ordenar");
        lblF9.setFocusable(false);
        lblEsc.setBounds(new Rectangle(990, 545, 110, 20));
    lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        lblF2.setBounds(new Rectangle(155, 545, 120, 20));
    lblF2.setText("[ F2 ] Ver Resumen");
   // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
    //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblF2.setFocusable(false);
        lblTotalMonto.setText("0");
    lblTotalMonto.setBounds(new Rectangle(1015, 0, 65, 20));
    lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
    lblTotalMonto.setForeground(Color.white);
    lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMonto.setFocusable(false);
        lblVerAnulNoCob.setBounds(new Rectangle(285, 545, 140, 20));
    lblVerAnulNoCob.setText("[ F6 ] Ver Anul. No Cob.");
    //jContentPane.add(jLabelFunction1, null);
        lblVerAnulNoCob.setFocusable(false);

        pnlExpReporte.setBounds(new Rectangle(935, 0, 40, 30));
        cmbCajero.setBounds(new Rectangle(345, 10, 305, 20));
        jContentPane.add(lblVerAnulNoCob, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
        tblRegistroVentas.setFocusable(false);
        tblRegistroVentas.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblRegistroVentas_mouseClicked(e);
                }
            });
        jScrollPane1.getViewport().add(tblRegistroVentas, null);
    jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlResultados.add(lblTotalMonto, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlTitulo.add(btnListado, null);
    jContentPane.add(pnlTitulo, null);
    jContentPane.add(pnlCriterioBusqueda, null);
        pnlCriterioBusqueda.add(cmbCajero, null);
        pnlCriterioBusqueda.add(pnlExpReporte, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        // pnlExpReporte.setBounds(new Rectangle(945, 0, 60, 30));
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
    
    cargaCajeros();
  };

  /* ********************************************************************** */
  /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
  /* ********************************************************************** */
  private void initTableListaRegistroVentas()
  {
    tableModelRegistroVentas = new FarmaTableModel(ConstantsReporte.columnsListaRegistroVentas,ConstantsReporte.defaultValuesListaRegistroVentas,0);
    FarmaUtility.initSimpleList(tblRegistroVentas,tableModelRegistroVentas,ConstantsReporte.columnsListaRegistroVentas);
      TableRowFilterSupport.forTable(tblRegistroVentas).searchable(true).apply();
      
      
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
       FarmaUtility.moveFocus(cmbCajero);
        //btnBuscar.doClick();
    
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
    else if(e.getKeyCode() == KeyEvent.VK_F6)
    {
      DlgListaPedAnulNoCob dlgListaPedAnul = new DlgListaPedAnulNoCob(myParentFrame,"",true);
      dlgListaPedAnul.setVisible(true);
    }
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
        else{
          gneraPdf_electronico();  
        }
        //imprimir();
      
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
      tblRegistroVentas.setRowSorter(null);
      DBReportes.cargaListaRegistroVentas_X_CAJERO(tableModelRegistroVentas,
                                          VariablesReporte.vFechaInicio, 
                                          VariablesReporte.vFechaFin,
                                          "000",
                                          FarmaLoadCVL.getCVLCode("CMB_CAJERO", cmbCajero.getSelectedIndex())
                                           );
      if(tblRegistroVentas.getRowCount() <= 0){
        FarmaUtility.showMessage(this,"No se encontro resultados para la busqueda", txtFechaIni);
        lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
      FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
      lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
      lblRegistros.setText("" + tblRegistroVentas.getRowCount());
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
    
    String [] IND_DESCRIP_CAMPO = {"Correlativo", "Tipo y No.Comprob", "Fecha","Cliente", "Monto","Medico Asociado"};
    String [] IND_CAMPO = {"0","12","11","4","6","13"};
    
    log.info("Campo " + IND_DESCRIP_CAMPO[1] );
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
  
  private void imprimir() {
		if (!JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea imprimir?"))
			return;

		//FarmaPrintService vPrint = new FarmaPrintService(45,
		FarmaPrintService vPrint = new FarmaPrintService(66,
				FarmaVariables.vImprReporte, true);
               log.debug(FarmaVariables.vImprReporte);
		if (!vPrint.startPrintService()) {
			FarmaUtility.showMessage(this,
					"No se pudo inicializar el proceso de impresión",
					txtFechaIni);
			return;
		}

		try {

			String fechaActual = FarmaSearch
					.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
			String campoAlt = "________";

			vPrint.setStartHeader();
			vPrint.activateCondensed();
			vPrint.printBold(FarmaPRNUtility.llenarBlancos(40)
					+ " REPORTE  DE REGISTRO DE VENTAS", true);
          vPrint.printBold("Nombre Compañia: " + FarmaVariables.vNomCia, true);  
			vPrint.printBold("Fecha: " + fechaActual, true);
      vPrint.printBold("Fecha Inicial: " + VariablesReporte.vFechaInicio, true);
      vPrint.printBold("Fecha Final: " + VariablesReporte.vFechaFin, true);
      vPrint.printBold("Local: " + FarmaVariables.vDescLocal, true);
			vPrint
					.printLine(
							"========================================================================================================================",
							true);
			vPrint
					.printBold(
							"NroPedido   Tipo  NroComprobante  Fecha                Cliente          Estado             Monto   Ruc       ",
							true);
			vPrint
					.printLine(
							"=========================================================================================================================",
							true);
			vPrint.deactivateCondensed();
			vPrint.setEndHeader();
			for (int i = 0; i < tblRegistroVentas.getRowCount(); i++) {

				vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i, 0), 11)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,1), 5)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,2), 15)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,3), 20)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,4), 16)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,5), 12)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,6), 13)+" "+
        FarmaPRNUtility.alinearIzquierda((String) tblRegistroVentas.getValueAt(i,7), 12) , true);
			}

			vPrint.activateCondensed();
			vPrint
					.printLine(
							"==========================================================================================================================",
							true);
			vPrint.printBold("Registros Impresos: "
					+ FarmaPRNUtility.alinearDerecha(
              FarmaUtility.formatNumber(tblRegistroVentas
							.getRowCount(), ",##0"),10)
          + FarmaPRNUtility.alinearDerecha(lblTotalMonto.getText(),66)
					+ FarmaPRNUtility.llenarBlancos(11), true);
			vPrint.deactivateCondensed();
			vPrint.endPrintService();
		} catch (SQLException ex) {
			log.error("",ex);
                        //showMessage(this,"Ocurrió un error al imprimir : \n"+ex.getMessage(),null);			
		}
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
        lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
        lblRegistros.setText("" + tblRegistroVentas.getRowCount());
        return ;
      }else{
        FarmaUtility.moveFocus(tblRegistroVentas);
      }
      FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);
      lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas,6)));
      lblRegistros.setText("" + tblRegistroVentas.getRowCount());
      FarmaUtility.moveFocusJTable(tblRegistroVentas);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaIni);
    }
  }

    private void generarDocumentoWord() {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else 
        {
            String pNumPedVta = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
            generarArchivo(pNumPedVta);
            FarmaUtility.showMessage(this, "Se generó el documento correctamente.\n"+
                                           "Nº Venta : "+pNumPedVta, txtFechaIni);
        }        
    }
    
    private void generarDocumentoPDF() {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else 
        {
            ConstantsReportePDF.rutaPDF = "";
            String pNumPedVta = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
            DlgImpresionCotizacion dlg = new DlgImpresionCotizacion(myParentFrame, "", true, pNumPedVta);
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

    private void generarDocumentoPDF_GUIA() {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else 
        {
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

    private void funcionF12() {
        if(tblRegistroVentas.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
        else
          generarDocumentoPDF();
    }

    private void lblF12_mouseClicked(MouseEvent e) {
        if(tblRegistroVentas.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
        else
          generarDocumentoPDF();
    }

    private void tblRegistroVentas_mouseClicked(MouseEvent e) {
        if (e.isMetaDown() ){
            // click derecho
            funcionF12();
        }
    }

    private void generarArchivo(String vNumPedVta) {
            //word_antes(vNumPedVta);
            imprime_historial(vNumPedVta);
    }


    private void word_antes(String vNumPedVta) {

        try {
        String pRuta = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           log.info("You chose to open this directory: " +
                chooser.getSelectedFile().getAbsolutePath());
            pRuta =  chooser.getSelectedFile().getAbsolutePath();
        }
        log.info("pRuta: " +
             chooser.getSelectedFile().getAbsolutePath());
            if(pRuta.trim().length()>0){
                
                ArrayList vLista = new ArrayList();
                DBModuloVenta.getDatoWord(vLista,vNumPedVta);
                
                String pFechaImpresion = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                String pTipo=  FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                String pNumComp=  FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                String pDNI=  FarmaUtility.getValueFieldArrayList(vLista, 0, 3);
                String pNombre=  FarmaUtility.getValueFieldArrayList(vLista, 0, 4);
                String pApellidoPat=  FarmaUtility.getValueFieldArrayList(vLista, 0, 5);
                String pApellidoMat=  FarmaUtility.getValueFieldArrayList(vLista, 0, 6);
                String pFecNac=  FarmaUtility.getValueFieldArrayList(vLista, 0, 7);
                String pEdad=  FarmaUtility.getValueFieldArrayList(vLista, 0, 8);
                String pPeso=  FarmaUtility.getValueFieldArrayList(vLista, 0, 9);
                String pSexo=  FarmaUtility.getValueFieldArrayList(vLista, 0, 10);
                String pDireccion=  FarmaUtility.getValueFieldArrayList(vLista, 0, 11);
                String pTelefono=  FarmaUtility.getValueFieldArrayList(vLista, 0, 12);
                String pEmail=  FarmaUtility.getValueFieldArrayList(vLista, 0, 13);
                String pOcupacion=  FarmaUtility.getValueFieldArrayList(vLista, 0, 14);
                String pCaso=  FarmaUtility.getValueFieldArrayList(vLista, 0, 15);
                String pMedico=  FarmaUtility.getValueFieldArrayList(vLista, 0, 16);
                String pExamenes=  FarmaUtility.getValueFieldArrayList(vLista, 0, 17);


                ArrayList vDatosGenerales = new ArrayList();
                DBModuloVenta.getDatoGenerales(vDatosGenerales,vNumPedVta);

                ArrayList vDatosCancer = new ArrayList();
                DBModuloVenta.getDatoCancer(vDatosCancer,vNumPedVta);

                ArrayList vDatosOperaciones = new ArrayList();
                DBModuloVenta.getDatoOperaciones(vDatosOperaciones,vNumPedVta);

                
                ArrayList vDatosEstudiosExamenes = new ArrayList();
                DBModuloVenta.getDatoExamenesEstudios(vDatosEstudiosExamenes,vNumPedVta);

                ArrayList vDatosResponsable = new ArrayList();
                DBModuloVenta.getDatoResponsable(vDatosResponsable,vNumPedVta);

                ArrayList vDatosIndEnfermera = new ArrayList();
                DBModuloVenta.getDatoInformeMedico(vDatosIndEnfermera,vNumPedVta);
                
                    UtilityWordHC.genera_hc_preliminar(pRuta,pFechaImpresion, pTipo, pNumComp, pDNI, pNombre,
                                        pApellidoPat, pApellidoMat, pFecNac, pEdad,
                                        pPeso, pSexo, pDireccion, pTelefono,
                                        pEmail, pOcupacion, pCaso, pMedico, pExamenes);
                                
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            FarmaUtility.showMessage(this, "error al generar word "+
                                           e.getMessage(), txtFechaFin);
        }

    }
    

    private void imprime_historial(String vNumPedVta) {
        
        // COMO PRUEBA
        
        VariablesCaja.vRutaImpresora = "\\\\192.168.1.6\\BOLETA";
        
        FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("ERROR DE IMPRESORA : No se pudo imprimir hostorial");
        }
        else {
            try 
            {
                            vPrint.activateCondensed();
                            
                        ArrayList vLista = new ArrayList();
                        DBModuloVenta.getDatoWord(vLista,vNumPedVta);
                        
                        String pFechaImpresion = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                        String pTipo=  FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                        String pNumComp=  FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                        String pDNI=  FarmaUtility.getValueFieldArrayList(vLista, 0, 3);
                        String pNombre=  FarmaUtility.getValueFieldArrayList(vLista, 0, 4);
                        String pApellidoPat=  FarmaUtility.getValueFieldArrayList(vLista, 0, 5);
                        String pApellidoMat=  FarmaUtility.getValueFieldArrayList(vLista, 0, 6);
                        String pFecNac=  FarmaUtility.getValueFieldArrayList(vLista, 0, 7);
                        String pEdad=  FarmaUtility.getValueFieldArrayList(vLista, 0, 8);
                        String pPeso=  FarmaUtility.getValueFieldArrayList(vLista, 0, 9);
                        String pSexo=  FarmaUtility.getValueFieldArrayList(vLista, 0, 10);
                        String pDireccion=  FarmaUtility.getValueFieldArrayList(vLista, 0, 11);
                        String pTelefono=  FarmaUtility.getValueFieldArrayList(vLista, 0, 12);
                        String pEmail=  FarmaUtility.getValueFieldArrayList(vLista, 0, 13);
                        String pOcupacion=  FarmaUtility.getValueFieldArrayList(vLista, 0, 14);
                        String pCaso=  FarmaUtility.getValueFieldArrayList(vLista, 0, 15);
                        String pMedico=  FarmaUtility.getValueFieldArrayList(vLista, 0, 16);
                        String pExamenes=  FarmaUtility.getValueFieldArrayList(vLista, 0, 17);


                        for(int i=0;i<10;i++)
                            vPrint.printLine("",true);
                            
                        vPrint.printLine("                             "+FarmaPRNUtility.alinearDerecha(pFechaImpresion,90),true);
                        vPrint.printLine("                                    ",true);
                        vPrint.printLine("                             "+FarmaPRNUtility.alinearDerecha(pDNI,90),true);
                       
                        vPrint.printLine("                                            "+pNombre+ " "+pApellidoPat+ " "+pApellidoMat,true);
                        vPrint.printLine("                                    ",true);
                        vPrint.printLine("                                        "+FarmaPRNUtility.alinearIzquierda(pFecNac,30)+"     "+
                                                                                FarmaPRNUtility.alinearIzquierda(pEdad,30)+ " "+
                                                                                FarmaPRNUtility.alinearIzquierda(pPeso,30),true);
                        vPrint.printLine("                                    ",true);
                        vPrint.printLine("                                "+FarmaPRNUtility.alinearIzquierda(pDireccion,70)+ "      "+pTelefono,true);
                        vPrint.printLine("                                             "+pMedico,true);  
                        vPrint.printLine("                                    ",true);
                        vPrint.printLine("                                          "+pExamenes,true);
                        vPrint.printLine("                              "+FarmaPRNUtility.alinearIzquierda(pEmail,40)+"                          * "+pOcupacion,true);  

                        vPrint.printLine("                                    ",true);
                        vPrint.printLine("                              "+FarmaPRNUtility.alinearIzquierda(pCaso,40)+"                        * "+pTipo+" "+pNumComp ,true);
                            
                        vPrint.deactivateCondensed();
                        vPrint.endPrintService();
                
                FarmaUtility.showMessage(this,"Por favor de recoger la impresión",txtFechaIni);
                             
                    } catch (Exception sql) {
                        //log.error("",sql);
                        log.debug("Error de BD " + sql.getMessage());

                        log.info("Error al imprimir la HISTORIA CLINICA : " + sql.getMessage());
                        log.error(null, sql);
                    }

        } 
            
        }

    private void gneraPdf_electronico() {
        
        try {
            int posicion = tblRegistroVentas.getSelectedRow();
            

            if (posicion >= 0) {
                
                String pTipo = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, posicion, 1);
                String pPedido = FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, posicion, 0);
                
                
                GeneradorPDF generadorPDF = new GeneradorPDF();
                boolean flagPDF =
                    generadorPDF.crearPDF(FarmaConnection.getConnection(), 
                                          pTipo, 
                                          pPedido, 
                                          FarmaVariables.vCodGrupoCia,
                                          FarmaVariables.vCodLocal);
                if (flagPDF) {
                    System.out.println("Se generó el documento PDF_FB correctamente.");
                    FarmaUtility.showMessage(this, "se genero el pdf correctamente", txtFechaIni);
                } else {
                    System.out.println("Error al generar el documento PDF_FB.");
                    FarmaUtility.showMessage(this, "error al generar el archivo", txtFechaIni);
                }
            }
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            FarmaUtility.showMessage(this, "Fallo la generacion : "+sqle.getMessage(), txtFechaIni);
        }
        
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        if(tblRegistroVentas.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de imprmir",txtFechaIni);
        else{
            generarDocumentoPDF_GUIA();
        }
    }

    private void cargaCajeros() {
        cmbCajero.setName("CMB_CAJERO");
        ArrayList parametros = new ArrayList();
        //parametros.add(FarmaVariables.vCodGrupoCia);
        //parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.unloadCVL(cmbCajero, cmbCajero.getName());
        FarmaLoadCVL.loadCVLFromSP(cmbCajero, cmbCajero.getName(),
                                   "HHSUR_NEW_RPT_CONSULTORIO.GET_LISTA_CAJAS",
                                   parametros, false, true);
    }
}
