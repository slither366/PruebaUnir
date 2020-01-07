package venta.receta;


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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.awt.Component;
import java.awt.Desktop;

import java.io.IOException;

import javax.swing.table.DefaultTableCellRenderer;

import venta.receta.reference.ConstantsReceta;
import venta.reference.UtilityPtoVenta;
import venta.receta.reference.DBReceta;
import venta.receta.reference.VariablesReceta;

import venta.reportes.DlgDetalleRegistroVentas;
import venta.reportes.DlgListaPedAnulNoCob;
import venta.reportes.DlgResumenVenta;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.impresion.DlgImpresionCotizacion;
import venta.impresion.Reference.ConstantsReportePDF;


public class DlgListaPedidosAnulados extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaPedidosAnulados.class);

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
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabel lblTotalMonto = new JLabel();
    
    public boolean vOperaReceta = false;
    public String vCodCia = "";
    public String vCodLocal = "";
    public String vNumReceta = "";
    private JLabel jLabel1 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    //private JLabelFunction lblImpresion = new JLabelFunction();


    public DlgListaPedidosAnulados() {
        this(null, "", false);
    }

    public DlgListaPedidosAnulados(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(891, 597));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Lista de Interconsultas");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 15, 855, 60));
        pnlCriterioBusqueda.setFocusable(false);
        pnlTitulo.setBounds(new Rectangle(10, 80, 855, 20));
        pnlTitulo.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 470, 855, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 100, 855, 370));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(15, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(85, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(210, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(340, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
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
        lblF1.setBounds(new Rectangle(10, 515, 105, 20));
        lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setFocusable(false);
        lblF9.setBounds(new Rectangle(140, 515, 100, 20));
        lblF9.setText("[ F9 ] Ordenar");
        lblF9.setFocusable(false);
        lblEsc.setBounds(new Rectangle(580, 515, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        lblF2.setBounds(new Rectangle(435, 515, 120, 20));
        lblF2.setText("[ F11 ] Procesar");
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblF2.setFocusable(false);
        lblTotalMonto.setText("0");
        lblTotalMonto.setBounds(new Rectangle(625, 0, 65, 20));
        lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
        lblTotalMonto.setForeground(Color.white);
        lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMonto.setFocusable(false);
        //jContentPane.add(jLabelFunction1, null);
        //lblImpresion.setText("[ F12 ] Pdf Cotizaci\u00f3n");
        //lblImpresion.setBounds(new Rectangle(715, 515, 145, 20));
        //jContentPane.add(lblImpresion, null);
        jLabel1.setText("Dni o Ruc Paciente:");
        jLabel1.setBounds(new Rectangle(15, 35, 105, 20));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jTextField1.setBounds(new Rectangle(125, 35, 150, 20));
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
        tblRegistroVentas.setFocusable(false);
        jScrollPane1.getViewport().add(tblRegistroVentas, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        jContentPane.add(pnlTitulo, null);
        pnlResultados.add(lblTotalMonto, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlCriterioBusqueda.add(jTextField1, null);
        pnlCriterioBusqueda.add(jLabel1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        tableModelRegistroVentas =
                new FarmaTableModel(ConstantsReceta.columnsListaRegistroReceta, ConstantsReceta.defaultValuesListaRegistroReceta,
                                    0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tableModelRegistroVentas,
                                    ConstantsReceta.columnsListaRegistroReceta);
    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        cargaListaDefault();
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRegistroVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRegistroVentas);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblRegistroVentas.getSelectedRow() >= 0) {
                if(JConfirmDialog.rptaConfirmDialog(this, "Se va agregar el detalle del pedido seleccionado. \n" + 
                                                        "Esta Seguro de realizar la acción?")){
                    setVCodCia(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 7));
                    setVCodLocal(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 8));
                    setVNumReceta(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 0));
                    setVOperaReceta(true);
                    cerrarVentana(true);
                }
                else
                    setVOperaReceta(false);
            } else {
                listadoDetalleVentas();
            }
        } 
        else
        if(e.getKeyCode()==KeyEvent.VK_F12){
           /* if(tblRegistroVentas.getRowCount() <= 0)
            FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
            else
              generarDocumentoPDF();*/
        }
        else
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                listadoDetalleVentas();
            }
        } else 
           if (e.getKeyCode() == KeyEvent.VK_F9) 
           {
                if (tblRegistroVentas.getRowCount() <= 0)
                    FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
                else
                    muestraVentaOrdenar();
           } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
               cerrarVentana(true);
           }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void buscaRegistroCotizaciones(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroCotizacion();
    }

    private void cargaRegistroCotizacion() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            DBReceta.cargaListaRegistroPedidosAnulados(tableModelRegistroVentas, VariablesReporte.vFechaInicio,
                                                VariablesReporte.vFechaFin);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 0, FarmaConstants.ORDEN_DESCENDENTE);
            //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
            
            pintaProductosSinStock();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }
    private void cargaListaDefault() {
        try {
            DBReceta.cargaPedidoAnulaDefault(tableModelRegistroVentas);
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 0, FarmaConstants.ORDEN_DESCENDENTE);
            //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
            pintaProductosSinStock();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listadoDetalleVentas() {
        ArrayList vFila = (ArrayList)tableModelRegistroVentas.data.get(tblRegistroVentas.getSelectedRow());
        DlgDetalleReceta dlgDetalleRegistroVentas = new DlgDetalleReceta(myParentFrame, "", true,vFila);
        dlgDetalleRegistroVentas.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    /**
     * Fecha Modificación: 05/01/2007
     * Usuario: Luis Reque
     * Descripción: Se realiza el ordenamiento por Tipo y Nro. de Comprobante a la vez, no por separados.
     * */
    private void muestraVentaOrdenar() {
        DlgOrdenarReceta dlgOrdenar = new DlgOrdenarReceta(myParentFrame, "Ordenar", true);

        String[] IND_DESCRIP_CAMPO = { "Correlativo", "Fecha", "Medico", "Cliente", "Neto" };
        String[] IND_CAMPO = { "0", "6", "2", "3", "5" };
        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEREGISTROVENTAS;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblRegistroVentas.repaint();
        }
    }
   
    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    buscaRegistroCotizaciones(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    public void setVOperaReceta(boolean vOperaReceta) {
        this.vOperaReceta = vOperaReceta;
    }

    public boolean isVOperaReceta() {
        return vOperaReceta;
    }

    public void setVCodCia(String vCodCia) {
        this.vCodCia = vCodCia;
    }

    public String getVCodCia() {
        return vCodCia;
    }

    public void setVCodLocal(String vCodLocal) {
        this.vCodLocal = vCodLocal;
    }

    public String getVCodLocal() {
        return vCodLocal;
    }

    public void setVNumReceta(String vNumReceta) {
        this.vNumReceta = vNumReceta;
    }

    public String getVNumReceta() {
        return vNumReceta;
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
            dlg.toFront();
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
    
    public void pintaProductosSinStock(){
        
        if (tblRegistroVentas.getRowCount() > 0) {
            /*FarmaUtility.ordenar(tblSolicitud, tblModelSolicitud, Constants.COL_NUMERO_DOCUMENTO,
                                 FarmaConstants.ORDEN_ASCENDENTE);*/
            
            int pFila = tblRegistroVentas.getSelectedRow();
            
            
            int cols = tblRegistroVentas.getColumnCount();
            for (int i = 0; i < cols; i++) {
                tblRegistroVentas.getColumnModel().getColumn(i).setCellRenderer(new CustomRenderer());
            }
            
            
            /*tblProductos.clearSelection();
            tblProductos.setRowSelectionInterval(pFila, pFila);*/
            
            //tblProductos.getSelectionModel().setSelectionInterval (0, 0);
            //ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,
            
            
            /*ArrayList rowsWithOtherColor = new ArrayList();
            for(int i = 0; i < tableModelListaPrecioProductos.data.size(); i++){
              if ( (Integer.parseInt(((ArrayList)tableModelListaPrecioProductos.data.get(i)).get(5).toString().trim())) <= 0)
              { //cantguias 8 es 0
                rowsWithOtherColor.add(String.valueOf(i));
              }
            }

            FarmaUtility.initSelectListCleanColumns(tblProductos, tableModelListaPrecioProductos,
               ConstantsModuloVenta.columnsListaProductos,rowsWithOtherColor,Color.white,Color.red,false);*/
            
            
          
        }
        tblRegistroVentas.getTableHeader().setReorderingAllowed(false);
        tblRegistroVentas.getTableHeader().setResizingAllowed(false);
        
        //tblProductos.getSelectionModel().setSelectionInterval (0, 0);

    }
    
    class CustomRenderer extends DefaultTableCellRenderer 
    {
    
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         
            if(tableModelRegistroVentas.data.size()>0){
                
            // campo para validar
               int  intValor=Integer.parseInt(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, row, 9).toString().trim());
            
            Color prioridad1 = new Color(249,145,127) ;//ALTA
            //Color prioridad2 = new Color(127,249,154);//MEDIA
            //Color prioridad3 = new Color(249,244,139);//BAJA

            //cellComponent.setBackground(Color.WHITE);
            
            if(isSelected){
               //
               setBackground(new Color(35,57,145));  
               setForeground(Color.WHITE);
            }
            else{
                if(intValor>0){
                   setBackground(prioridad1);
                }
                else{
                    setBackground(Color.WHITE);  
                    setForeground(Color.black);
                }    
            }
            
           }
            
           
            /*
            if (row >= table.getRowCount())
                row = table.getRowCount() - 1;
            Rectangle rect = table.getCellRect(row, column, true);
            table.scrollRectToVisible(rect);
            table.clearSelection();
            table.setRowSelectionInterval(row, row);*/
            
            return cellComponent;
            
        
        }
    }      

}
