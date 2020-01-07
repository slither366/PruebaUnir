package cilator.impresion;


import cilator.impresion.Reference.WrapperItemObject;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.DBModuloVenta;

import venta.reference.VariablesPtoVenta;

import venta.reportes.reference.ConstantsReporte;


public class DlgImpresionOrdenCompra extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgImpresionOrdenCompra.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelDatosCab;
    private JTable tblDatosCab = new JTable();
    private FarmaTableModel tableModelDatosDet;
    private JTable tblDatosDet = new JTable();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JButtonLabel btnContacto = new JButtonLabel();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JLabel btnCotizacion = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel6 = new JLabel();

    private JTextFieldSanSerif txtContacto = new JTextFieldSanSerif();
    private JTextField txtCotizacion = new JTextField();
    private JTextField txtCondicion = new JTextField();
    private JTextField txtFecEntrega = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private String pNumPedVta = "";

    public DlgImpresionOrdenCompra() {
       this(null, "", false,"");
    }
    
    public DlgImpresionOrdenCompra(Frame parent, String title, boolean modal, String pNumPedVta) {
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
        this.setSize(new Dimension(450, 265));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Informaci\u00f3n Adicional de impresion de Orden de Compra");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 415, 190));
        txtContacto.setBounds(new Rectangle(95, 10, 295, 20));
        txtContacto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtContacto_keyPressed(e);
                }
            });
        btnContacto.setText("CONTACTO :");
        btnContacto.setBounds(new Rectangle(20, 10, 70, 20));
        btnContacto.setMnemonic('N');

        btnContacto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblImprimir.setBounds(new Rectangle(185, 210, 110, 20));
        lblImprimir.setText("[ F11 ]Imprimir");

        btnCotizacion.setText("COTIZACION :");
        btnCotizacion.setBounds(new Rectangle(15, 45, 75, 15));
        btnCotizacion.setForeground(SystemColor.window);
        btnCotizacion.setFont(new Font("Tahoma", 1, 11));
        btnCotizacion.setHorizontalAlignment(SwingConstants.RIGHT);
        txtCotizacion.setBounds(new Rectangle(95, 40, 295, 20));
        txtCotizacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCotizacion_keyPressed(e);
                }
            });
        txtCondicion.setBounds(new Rectangle(95, 100, 295, 20));
        txtCondicion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCondicion_keyPressed(e);
                }
            });
        jLabel2.setText("TELEFONO :");
        jLabel2.setBounds(new Rectangle(30, 75, 60, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("CONDICION :");
        jLabel3.setBounds(new Rectangle(20, 105, 70, 15));
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("FEC. ENTREGA :");
        jLabel4.setBounds(new Rectangle(5, 125, 85, 25));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(SystemColor.window);
        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        txtFecEntrega.setBounds(new Rectangle(95, 130, 220, 20));
        txtFecEntrega.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFecEntrega_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFecEntrega_keyReleased(e);
                }
            });
        jLabel6.setText("dd/mm/yyyy");
        jLabel6.setBounds(new Rectangle(320, 130, 70, 20));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(SystemColor.window);
        txtTelefono.setBounds(new Rectangle(95, 70, 295, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtTelefono_keyReleased(e);
                }
            });
        lblSalir.setBounds(new Rectangle(310, 210, 115, 20));
        lblSalir.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtTelefono, null);
        pnlCliente.add(jLabel6, null);
        pnlCliente.add(txtFecEntrega, null);
        pnlCliente.add(jLabel4, null);
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtCondicion, null);
        pnlCliente.add(txtCotizacion, null);
        pnlCliente.add(btnCotizacion, null);
        pnlCliente.add(txtContacto, null);
        pnlCliente.add(btnContacto, null);
        jPanelWhite1.add(lblSalir, null);
        jPanelWhite1.add(lblImprimir, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        limpiar();
        initTableListaDatosCab();
        initTableListaDatosDet();
    }  
    
    private void initTableListaDatosCab(){
        //tableModelDatosCab = new FarmaTableModel(ConstantsReporte.columnsListaDatosCab,ConstantsReporte.defaultValuesListaDatosCab,0);
        //FarmaUtility.initSimpleList(tblDatosCab,tableModelDatosCab,ConstantsReporte.columnsListaDatosCab);
    }  
    
    private void initTableListaDatosDet(){
        //tableModelDatosDet = new FarmaTableModel(ConstantsReporte.columnsListaDatosDet,ConstantsReporte.defaultValuesListaDatosDet,0);
        //FarmaUtility.initSimpleList(tblDatosDet,tableModelDatosDet,ConstantsReporte.columnsListaDatosDet);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtContacto);
        try {
            String datosAdicionales ="";// DBModuloVenta.obtenerDatosAdicionales(pNumPedVta);
            String[] datosObtenidos = datosAdicionales.split("Ã");
            if(datosObtenidos.length > 0){
                txtContacto.setText(datosObtenidos[5].toString());
                txtCotizacion.setText(datosObtenidos[6].toString());
                txtTelefono.setText(datosObtenidos[2].toString());
                txtCondicion.setText(datosObtenidos[3].toString());
                txtFecEntrega.setText(datosObtenidos[7].toString());
            }
        } catch (Exception f) {
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
        String pContacto = txtContacto.getText().trim();
        String pCotizacion = txtCotizacion.getText().trim();
        String pCondicion = txtCondicion.getText().trim();
        String pFecEntrega = txtFecEntrega.getText().trim();
        String pTelefono = txtTelefono.getText().trim();
        
        boolean b = JConfirmDialog.rptaConfirmDialog(this, "Seguro que desea generar documento PDF?");
        if(b){
            guardarDatosAdicionales(pContacto, pCotizacion, pTelefono, pCondicion, pFecEntrega);
            generarArchivoPDF(pContacto, pCotizacion, pTelefono, pCondicion, pFecEntrega);
            cerrarVentana(true);
        }
    }
    
    private void guardarDatosAdicionales(String pContacto, String pCotizacion, 
                                         String pTelefono, String pCondicion, String pFecEntrega){
        try {
          //  DBModuloVenta.guardarDatosAdicionales("","",pTelefono,pCondicion,"",pNumPedVta,pContacto,pCotizacion,pFecEntrega);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
        }
    }
    
    public void limpiar(){
        txtContacto.setText("");
        txtCotizacion.setText("");
        txtCondicion.setText("");
        txtFecEntrega.setText("");
        txtTelefono.setText("");
    }
    
    private void generarArchivoPDF(String pContacto, String pCotizacion, 
                                   String pTelefono, String pCondicion, String pFecEntrega){
        try {
     //       DBModuloVenta.obtenerDatosReporteJasperCab(tableModelDatosCab,pNumPedVta);
       //     DBModuloVenta.obtenerDatosReporteJasperDet(tableModelDatosDet,pNumPedVta);
            generarPDF_Orden_Compra(pContacto,pCotizacion,pTelefono,pCondicion,pFecEntrega);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void generarPDF_Orden_Compra(String pContacto,String pCotizacion,
                                                String pTelefono,String pCondicion,String pFecEntrega){
        String rutaJasper = "C:\\farmaventa\\jasper\\";
        try{
            FileInputStream fis = new FileInputStream(rutaJasper+"Orden_Compra.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);            
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("RUC", "20498873457");
            map.put("DIRECCION", "Prol. Av. Perú con Tomas Valle Mz G-37 Lt. 41");
            map.put("DIR_DIST", "BOCANEGRA - CALLAO");
            map.put("TELEFAX", "575 - 5732");
            map.put("NEXTEL", "833 * 5832");
            /*-----------------------------------------------*/
            map.put("NRO_DOCUMENTO", ((String)tblDatosCab.getValueAt(0,0)).trim());
            /*-----------------------------------------------*/
            map.put("DATOS_CLIENTE", ((String)tblDatosCab.getValueAt(0,1)).trim());
            map.put("DIRECCION_CLIENTE", ((String)tblDatosCab.getValueAt(0,2)).trim());
            map.put("CONTACTO_CLIENTE", pContacto);
            map.put("COTIZACION", pCotizacion);
            map.put("RUC_CLIENTE", ((String)tblDatosCab.getValueAt(0,3)).trim());
            map.put("TELEFONO_CLIENTE", pTelefono);
            map.put("CONDICION_PAGO", pCondicion);
            map.put("FEC_ENTREGA", pFecEntrega);
            /*-----------------------------------------------*/
            WrapperItemObject itemObject = null;
            List<WrapperItemObject> listProductos = new ArrayList<WrapperItemObject>();
            Map<String, Object> detalleProducto = null;
            for(int i = 0; i < tableModelDatosDet.getRowCount(); i++){
                detalleProducto = new HashMap<String, Object>();
                detalleProducto.put("ITEM", tblDatosDet.getValueAt(i, 0));
                detalleProducto.put("UNIDAD", tblDatosDet.getValueAt(i, 1));
                detalleProducto.put("DESCRIPCION", tblDatosDet.getValueAt(i, 2));
                detalleProducto.put("MATERIAL", tblDatosDet.getValueAt(i, 3));
                detalleProducto.put("CANTIDAD", tblDatosDet.getValueAt(i, 4));
                detalleProducto.put("P_UNITARIO", tblDatosDet.getValueAt(i, 5));
                detalleProducto.put("VALOR_NETO", tblDatosDet.getValueAt(i, 6));
                itemObject = new WrapperItemObject();
                itemObject.setLstItemHashMap(detalleProducto);
                listProductos.add(itemObject);
            }
            /*-----------------------------------------------*/
            map.put("SUBTOTAL", (String)tblDatosCab.getValueAt(0,4));
            map.put("IGV", (String)tblDatosCab.getValueAt(0,5));
            map.put("MONTO_OTRO", "-");
            map.put("TOTAL_GRAL", (Double.parseDouble(tblDatosCab.getValueAt(0,4)+"") + 
                                  Double.parseDouble(tblDatosCab.getValueAt(0,5)+"")) + "");
            /*-----------------------------------------------*/
            map.put("RECEPCION_LINEA1", "Entrega Oficina CILATOR SAC");
            map.put("RECEPCION_LINEA2", "Direccion: Prol. Av Peru con Tomas Valle Mz. G37 Lt. 41");
            map.put("RECEPCION_LINEA3", "A.A.H.H. Bocanegra - Sector 5 // Frente Hospital Chalaco");
            /*-----------------------------------------------*/
            map.put("NOMBRE_FIRMA", "Mario La Torre Crespo");
            map.put("SENDER_LOGO_PATH", this.getClass().getResourceAsStream("C:/farmaventa/imagenes/logo.png"));
            /*-----------------------------------------------*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanCollectionDataSource(listProductos));
            JRExporter exporter = new JRPdfExporter(); 
            
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            Date date = new Date();
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String[] fec = fecha.format(date).split("/");
            String fechaFormateada = fec[2] + fec[1] + fec[0];
            DateFormat hora = new SimpleDateFormat("HH:mm:ss");
            String[] hor = hora.format(date).split(":");
            String horaFormateada = hor[0] + hor[1] + hor[2];
            String rutaPDF = "C:/farmaventa/Doc_Generados/OC_"+((String)tblDatosCab.getValueAt(0,0)).trim() + "_" + fechaFormateada + "_" + horaFormateada + ".pdf";
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutaPDF)); 
            exporter.exportReport();
           // VariablesPtoVenta.rutaPDF = rutaPDF;
            //JasperViewer.viewReport(jasperPrint, true); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void txtContacto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCotizacion);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtCotizacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtTelefono);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCondicion);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtCondicion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecEntrega);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtFecEntrega_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtContacto);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtFecEntrega_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecEntrega,e);
    }

    private void txtTelefono_keyReleased(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }
}
