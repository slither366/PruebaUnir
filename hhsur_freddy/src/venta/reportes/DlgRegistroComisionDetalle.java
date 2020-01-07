package venta.reportes;

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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import common.*;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import venta.reference.UtilityPtoVenta;
import venta.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.DBModuloVenta;

public class DlgRegistroComisionDetalle extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroComisionDetalle.class);

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
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabel lblTotalMonto = new JLabel();
    private JButton jButton1 = new JButton();

    public DlgRegistroComisionDetalle()
  {
    this(null, "", false);
  }

  public DlgRegistroComisionDetalle(Frame parent, String title, boolean modal)
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
    this.setTitle("Registro de Ventas x Comision");
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
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 1090, 30));
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
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
    //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblTotalMonto.setText("0");
    lblTotalMonto.setBounds(new Rectangle(1015, 0, 65, 20));
    lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
    lblTotalMonto.setForeground(Color.white);
    lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMonto.setFocusable(false);
        //jContentPane.add(jLabelFunction1, null);
        jButton1.setText("Exportar");
        jButton1.setBounds(new Rectangle(810, 545, 130, 20));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jContentPane.add(jButton1, null);
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
    tableModelRegistroVentas = new FarmaTableModel(ConstantsReporte.columnsListaRegistroVentasComisionDetalle,
                                                   ConstantsReporte.defaultValuesListaRegistroVentasComisionDetalle,0);
    FarmaUtility.initSimpleList(tblRegistroVentas,tableModelRegistroVentas,ConstantsReporte.columnsListaRegistroVentasComisionDetalle);
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
     funcionF12();
      
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
      DBReportes.cargaListaRegistroVentasComisionDetalle(tableModelRegistroVentas,VariablesReporte.vFechaInicio, VariablesReporte.vFechaFin,"000");
      FarmaUtility.aceptarTransaccion();  
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

    private void funcionF12() {
        if(tblRegistroVentas.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
        else
          generarDocumentoWord();
    }

    private void lblF12_mouseClicked(MouseEvent e) {
        if(tblRegistroVentas.getRowCount() <= 0)
        FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
        else
          generarDocumentoWord();
    }

    private void tblRegistroVentas_mouseClicked(MouseEvent e) {
        if (e.isMetaDown() ){
            // click derecho
            funcionF12();
        }
    }

    private void generarArchivo(String vNumPedVta) {

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
                    
                        UtilityWordHC.genera_hc(pRuta,pFechaImpresion, pTipo, pNumComp, pDNI, pNombre,
                                            pApellidoPat, pApellidoMat, pFecNac, pEdad,
                                            pPeso, pSexo, pDireccion, pTelefono,
                                            pEmail, pOcupacion, pCaso, pMedico, pExamenes,
                                                vDatosGenerales,vDatosCancer,vDatosOperaciones,vDatosEstudiosExamenes,
                                                vDatosResponsable,vDatosIndEnfermera);
                                    
                }
        } catch (Exception e) {
                e.printStackTrace();
                log.info(e.getMessage());
                FarmaUtility.showMessage(this, "error al generar word "+
                                               e.getMessage(), txtFechaFin);
        }    

    }
    
    public static void exportExcel (JTable jTable1, String Location) throws FileNotFoundException, IOException {

                   HSSFWorkbook fWorkbook = new HSSFWorkbook();
                   HSSFSheet fSheet = fWorkbook.createSheet("new Sheet");
                   HSSFFont sheetTitleFont = fWorkbook.createFont();
                   HSSFCellStyle cellStyle = fWorkbook.createCellStyle();
                   sheetTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                   //sheetTitleFont.setColor();
                   TableModel model = jTable1.getModel();

                   //Get Header
                   TableColumnModel tcm = jTable1.getColumnModel();
                   HSSFRow hRow = fSheet.createRow((short) 0);
                   for(int j = 0; j < tcm.getColumnCount(); j++) {                       
                      HSSFCell cell = hRow.createCell((short) j);
               cell.setCellValue(tcm.getColumn(j).getHeaderValue().toString());
                      cell.setCellStyle(cellStyle);
                   }

                   //Get Other details
                   for (int i = 0; i < model.getRowCount(); i++) {
                       HSSFRow fRow = fSheet.createRow((short) i+1);
                       for (int j = 0; j < model.getColumnCount(); j++) {
                           
                           if(j==18||j==19||j==20){
                               HSSFCell cell = fRow.createCell((short) j);
                               try {
                                    cell.setCellValue(Float.parseFloat(model.getValueAt(i, j).toString().replace(",", "")));
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cell.setCellStyle(cellStyle);
                                } catch (Exception e) {
                                    // TODO: Add catch code
                                    e.printStackTrace();
                                }
                           }
                           else{
                               HSSFCell cell = fRow.createCell((short) j);
                               try {
                                    cell.setCellValue(model.getValueAt(i, j).toString());
                                    cell.setCellStyle(cellStyle);
                                } catch (Exception e) {
                                    // TODO: Add catch code
                                    e.printStackTrace();
                                }
                           }
                           

                       }
                   }
               FileOutputStream fileOutputStream;
               fileOutputStream = new FileOutputStream(Location);
       try (BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)) {
           fWorkbook.write(bos);
       }
               fileOutputStream.close();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                String pNombre = FarmaUtility.ShowInput(this,"Nombre Archivo");
                if(pNombre.trim().length()>0){
                    exportExcel(tblRegistroVentas,selectedFile.getAbsolutePath()+"\\"+pNombre.trim()+".xls");
                    FarmaUtility.showMessage(this, "Se exporto el reporte correctamente.", txtFechaIni);
                }
                else
                    FarmaUtility.showMessage(this, "No puede exportar sin ingresar el nonbre del archivo", txtFechaIni);
            } catch (Exception f) {
                FarmaUtility.showMessage(this, "Error."+f.getMessage(), txtFechaIni);
                f.printStackTrace();
            }
        }
    }
}
