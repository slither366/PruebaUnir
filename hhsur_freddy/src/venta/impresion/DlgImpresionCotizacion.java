package venta.impresion;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import venta.impresion.Reference.WrapperItemObject;

import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import net.sf.jasperreports.view.JasperViewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.impresion.Reference.ConstantsReportePDF;

import venta.impresion.Reference.DBReporte;


public class DlgImpresionCotizacion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgImpresionCotizacion.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelDatosCab;
    private JTable tblDatosCab = new JTable();
    private FarmaTableModel tableModelDatosDet;
    private JTable tblDatosDet = new JTable();
    private FarmaTableModel tableModelDatosEmp;
    private JTable tblDatosEmp = new JTable();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JButtonLabel btnContacto = new JButtonLabel();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();

    private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();
    private String pNumPedVta = "";

    public DlgImpresionCotizacion() {
       this(null, "", false,"");
    }
    
    public DlgImpresionCotizacion(Frame parent, String title, boolean modal, String pNumPedVta) {
        super(parent, title, modal);
        myParentFrame = parent;
        
        this.pNumPedVta = pNumPedVta;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(450, 153));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Informaci\u00f3n Adicional de impresion de Cotizaciones");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 415, 70));
        txtGlosa.setBounds(new Rectangle(95, 25, 295, 20));
        txtGlosa.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtAtencion_keyPressed(e);
                }
            });
        btnContacto.setText("GLOSA :");
        btnContacto.setBounds(new Rectangle(20, 25, 70, 20));
        btnContacto.setMnemonic('N');

        btnContacto.setActionCommand("ATENCION :");
        btnContacto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblImprimir.setBounds(new Rectangle(185, 95, 110, 20));
        lblImprimir.setText("[ F11 ]Imprimir");

        lblSalir.setBounds(new Rectangle(310, 95, 115, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtGlosa, null);
        pnlCliente.add(btnContacto, null);
        jPanelWhite1.add(lblSalir, null);
        jPanelWhite1.add(lblImprimir, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        limpiar();
        initTableListaDatosEmp();
        initTableListaDatosCab();
        initTableListaDatosDet();
    }  
    
    private void initTableListaDatosEmp(){
        tableModelDatosEmp = new FarmaTableModel(ConstantsReportePDF.columnsListaDatosEmp, ConstantsReportePDF.defaultValuesListaDatosEmp,0);
        FarmaUtility.initSimpleList(tblDatosEmp,tableModelDatosEmp, ConstantsReportePDF.columnsListaDatosEmp);
    }  
    
    private void initTableListaDatosCab(){
        tableModelDatosCab = new FarmaTableModel(ConstantsReportePDF.columnsListaDatosCab, ConstantsReportePDF.defaultValuesListaDatosCab,0);
        FarmaUtility.initSimpleList(tblDatosCab,tableModelDatosCab, ConstantsReportePDF.columnsListaDatosCab);
    }  
    
    private void initTableListaDatosDet(){
        tableModelDatosDet = new FarmaTableModel(ConstantsReportePDF.columnsListaDatosDet, ConstantsReportePDF.defaultValuesListaDatosDet,0);
        FarmaUtility.initSimpleList(tblDatosDet,tableModelDatosDet, ConstantsReportePDF.columnsListaDatosDet);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtGlosa);
        try {
            String datosAdicionales = DBReporte.obtenerDatosAdicionales(pNumPedVta);
            String[] datosObtenidos = datosAdicionales.split("Ã");
            if(datosObtenidos.length > 0){
                txtGlosa.setText(datosObtenidos[0].toString());
            }
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            imprimir();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void imprimir() {
        String pGlosa = txtGlosa.getText().trim();

        boolean b = JConfirmDialog.rptaConfirmDialog(new JDialog(), "Seguro que desea generar documento PDF?");
        if(b){
            guardarDatosAdicionales(pGlosa);
            generarArchivoPDF(pGlosa);
            cerrarVentana(true);
        }
    }
    
    private void guardarDatosAdicionales(String pGlosa){
        try {
            DBReporte.guardarDatosAdicionales(pGlosa, pNumPedVta);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
        }
    }
    
    public void limpiar(){
        txtGlosa.setText("");
    }
    
    private void generarArchivoPDF(String pGlosa){
        try {
            DBReporte.obtenerDatosReporteJasperEmp(tableModelDatosEmp);
            DBReporte.obtenerDatosReporteJasperCab(tableModelDatosCab,pNumPedVta);
            DBReporte.obtenerDatosReporteJasperDet(tableModelDatosDet,pNumPedVta);
            generarPDF_Cotizacion(pGlosa);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void generarPDF_Cotizacion(String pGlosa){
        String rutaJasper = "C:\\farmaventa\\jasper\\";
        try{
            FileInputStream fis = new FileInputStream(rutaJasper+"Cotizacion_Dental_Tayloc.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);            
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            
            Map<String,Object> map = new HashMap<String, Object>();
            /*-----------------------------------------------*/
            map.put("NRO_DOCUMENTO", ((String)tblDatosCab.getValueAt(0,0)).trim());
            /*-----------------------------------------------*/
            map.put("EMPRESA", ((String)tblDatosEmp.getValueAt(0, 0)).trim());
            map.put("RUC", ((String)tblDatosEmp.getValueAt(0, 1)).trim());
            map.put("DIRECCION", ((String)tblDatosEmp.getValueAt(0, 2)).trim());
            String fechaDoc = ((String)tblDatosCab.getValueAt(0, 8)).trim();
            int longitudFecha = ((String)tblDatosCab.getValueAt(0, 8)).trim().length();
            map.put("FECHA", fechaDoc.substring(0, longitudFecha - 8));
            map.put("HORA", fechaDoc.substring(longitudFecha - 8, longitudFecha));
            /*-----------------------------------------------*/
            map.put("DATOS_CLIENTE", ((String)tblDatosCab.getValueAt(0,1)).trim());
            map.put("RUC_CLIENTE", ((String)tblDatosCab.getValueAt(0,2)).trim());
            map.put("VENDEDOR", ((String)tblDatosCab.getValueAt(0,3)).trim());
            map.put("GLOSA", pGlosa);
            map.put("MONEDA", ((String)tblDatosCab.getValueAt(0,4)).trim());
            /*-----------------------------------------------*/
            WrapperItemObject itemObject = null;
            List<WrapperItemObject> listProductos = new ArrayList<WrapperItemObject>();
            Map<String, Object> detalleProducto = null;
            for(int i = 0; i < tableModelDatosDet.getRowCount(); i++){
                detalleProducto = new HashMap<String, Object>();
                detalleProducto.put("ITEM", tblDatosDet.getValueAt(i, 0));
                detalleProducto.put("CODIGO", tblDatosDet.getValueAt(i, 1));
                detalleProducto.put("DESCRIPCION", tblDatosDet.getValueAt(i, 2));
                detalleProducto.put("UNIDAD", tblDatosDet.getValueAt(i, 3));
                detalleProducto.put("CANTIDAD", tblDatosDet.getValueAt(i, 4));
                detalleProducto.put("P_UNITARIO", tblDatosDet.getValueAt(i, 5));
                detalleProducto.put("SUBTOTAL", tblDatosDet.getValueAt(i, 6));
                itemObject = new WrapperItemObject();
                itemObject.setLstItemHashMap(detalleProducto);
                listProductos.add(itemObject);
            }
            /*-----------------------------------------------*/
            map.put("VALOR_VENTA", (String)tblDatosCab.getValueAt(0,7));
            map.put("IGV", (String)tblDatosCab.getValueAt(0,6));
            map.put("TOTAL_GRAL", (String)tblDatosCab.getValueAt(0,5));
            /*-----------------------------------------------*/
            map.put("TELEFONO", ((String)tblDatosEmp.getValueAt(0, 3)).trim());
            /*-----------------------------------------------*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanCollectionDataSource(listProductos));
            JRExporter exporter = new JRPdfExporter(); 
            
            Date date = new Date();
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String[] fec = fecha.format(date).split("/");
            String fechaFormateada = fec[2] + fec[1] + fec[0];
            DateFormat hora = new SimpleDateFormat("HH:mm:ss");
            String[] hor = hora.format(date).split(":");
            String horaFormateada = hor[0] + hor[1] + hor[2];
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            String rutaPDF = "C:/farmaventa/Doc_Generados/CT_"+((String)tblDatosCab.getValueAt(0,0)).trim() + "_" + fechaFormateada + "_" + horaFormateada + ".pdf";
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutaPDF)); 
            exporter.exportReport();
            
            //no se colocara con jaspert por este se visualiza en el frame y no al nivel del jdialog
            //JasperViewer.viewReport(jasperPrint, false);
            
            ConstantsReportePDF.rutaPDF = rutaPDF;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void txtAtencion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtGlosa);
        }else{
            chkKeyPressed(e);
        }
    }
    
}
