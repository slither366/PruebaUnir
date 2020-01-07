package svb.fact_electronica.pdf.generadorPDF;

import svb.fact_electronica.pdf.bean.DocumentoCabeceraFB;
import svb.fact_electronica.pdf.bean.DocumentoCabeceraNT;
import svb.fact_electronica.pdf.bean.DocumentoDetalleFB;
import svb.fact_electronica.pdf.bean.DocumentoDetalleNT;
import svb.fact_electronica.pdf.bean.Empresa;
import svb.fact_electronica.pdf.bean.WrapperItemObject;
import svb.fact_electronica.pdf.generadorCode.CodeQR;
import svb.fact_electronica.pdf.impl.GeneradorImpl;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class GeneradorPDF {
    
    private GeneradorImpl generadorImpl = null;

    public GeneradorPDF() {
        generadorImpl = new GeneradorImpl();
    }
    
    public boolean crearPDF(Connection conn, String tipoDocumento, String numPedVta, String codGrupoCia, String codLocal){
        try {
            Empresa empresa = generadorImpl.obtenerDatosEmpresa(conn, codGrupoCia,codLocal);
            if(tipoDocumento.equals("B") || tipoDocumento.equals("F")){
                DocumentoCabeceraFB cabeceraFB = generadorImpl.obtenerDatosCabeceraFB(conn, numPedVta, codGrupoCia, codLocal);
                List<DocumentoDetalleFB> listDetallesFB = generadorImpl.obtenerDatosDetalleFB(conn, numPedVta, codGrupoCia, codLocal);
                generarPDF_FB(empresa, cabeceraFB, listDetallesFB);
            }else {
                DocumentoCabeceraNT cabeceraNT = generadorImpl.obtenerDatosCabeceraNT(conn, numPedVta, codGrupoCia, codLocal);
                List<DocumentoDetalleNT> listDetallesNT = generadorImpl.obtenerDatosDetalleNT(conn, numPedVta, codGrupoCia, codLocal);
                generarPDF_NT(empresa, cabeceraNT, listDetallesNT);
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return false;
    }

    private void generarPDF_FB(Empresa empresa, DocumentoCabeceraFB cabecera, List<DocumentoDetalleFB> listDetalles) {
        try {
            FileInputStream fis = new FileInputStream(empresa.getRuta_plantillas_jasper() + "/" + "documentElectronicFB.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);

            Map<String, Object> map = new HashMap<String, Object>();
            /*-----------------------------------------------*/
            map.put("RUTA_IMAGEN", empresa.getRuta_imagen());
            /*-----------------------------------------------*/
            map.put("RAZON_SOCIAL_EMISOR", empresa.getRazon_social());
            map.put("DIRECCION_EMISOR", empresa.getDireccion_emisor());
            map.put("DEP_PROV_DIST_EMISOR", empresa.getDep_prov_dist_emisor());
            map.put("TELEFONO_EMISOR", empresa.getTelefono_emisor());
            map.put("FAX_EMISOR", empresa.getFax_emisor());
            map.put("WEB_EMISOR", empresa.getWeb_emisor());
            map.put("CORREO_EMISOR", empresa.getCorreo_emisor());
            map.put("RUC_EMISOR", empresa.getRuc_emisor());
            /*-----------------------------------------------*/
            map.put("TITULO_DOCUMENTO", cabecera.getTitulo_documento());
            map.put("NUMERO_DOCUMENTO", cabecera.getNumero_documento());
            /*-----------------------------------------------*/
            map.put("RAZON_SOCIAL_CLIENTE", cabecera.getRazon_social_cliente());
            map.put("DIRECCION_CLIENTE", cabecera.getDireccion_cliente());
            map.put("RUC_CLIENTE", cabecera.getRuc_cliente());
            map.put("MONEDA_PAGO_DOCUMENTO", cabecera.getMoneda_pago_cliente());
            map.put("FECHA_EMISION_DOCUMENTO", cabecera.getFecha_emision_documento());
            map.put("CONDICION_PAGO_DOCUMENTO", cabecera.getCondicion_pago_documento());
            /*-----------------------------------------------*/
            WrapperItemObject itemObject = null;
            List<WrapperItemObject> listProductos = new ArrayList<WrapperItemObject>();
            Map<String, Object> detalleProducto = null;
            for (DocumentoDetalleFB detalleItem : listDetalles) {
                detalleProducto = new HashMap<String, Object>();
                detalleProducto.put("ITEM", detalleItem.getItem());
                detalleProducto.put("CANTIDAD", detalleItem.getCantidad());
                detalleProducto.put("UNIDAD", detalleItem.getUnidad());
                detalleProducto.put("DESCRIPCION", detalleItem.getDescripcion());
                detalleProducto.put("P_UNITARIO", detalleItem.getP_unitario());
                detalleProducto.put("SUBTOTAL", detalleItem.getSubtotal());
                itemObject = new WrapperItemObject();
                itemObject.setLstItemHashMap(detalleProducto);
                listProductos.add(itemObject);
            }
            /*-----------------------------------------------*/
            map.put("MONTO_EXONERADO", cabecera.getMonto_exonerado());
            map.put("MONTO_INAFECTO", cabecera.getMonto_inafecto());
            map.put("MONTO_GRATUITO", cabecera.getMonto_gratuito());
            map.put("MONTO_GRAVADO", cabecera.getMonto_gravado());
            map.put("MONTO_IGV", cabecera.getMonto_igv());
            map.put("MONTO_TOTAL", cabecera.getMonto_total());
            /*-----------------------------------------------*/
            CodeQR codigo = new CodeQR();
            String cadenaQR = empresa.getRuc_emisor() + "|" + 
                            cabecera.getTipo_documento() + "|" + 
                            cabecera.getNumero_documento().replace("-", "|") + "|" + 
                            cabecera.getMonto_igv() + "|" + 
                            cabecera.getMonto_total() + "|" + 
                            cabecera.getFecha_emision_documento() + "|" + 
                            cabecera.getTipo_documento_cliente() + "|" + 
                            cabecera.getRuc_cliente();
            String rutaQR = codigo.generateQR(cadenaQR, 300, 300);
            map.put("CODEQR", rutaQR);
            map.put("MONTO_TOTAL_LETRAS", cabecera.getMonto_total_letras());
            map.put("PORTALFE", empresa.getUrl_portal_fe());
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
            String rutaPDF = empresa.getRuta_pdf_A4() + "/" + empresa.getRuc_emisor() + "_" + cabecera.getNumero_documento() + "_" + fechaFormateada + "_" + horaFormateada + ".pdf";
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutaPDF));
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void generarPDF_NT(Empresa empresa, DocumentoCabeceraNT cabecera, List<DocumentoDetalleNT> listDetalles) {
        try {
            FileInputStream fis = new FileInputStream(empresa.getRuta_plantillas_jasper() + "/" + "documentElectronicNT.jasper");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);

            Map<String, Object> map = new HashMap<String, Object>();
            /*-----------------------------------------------*/
            map.put("RUTA_IMAGEN", empresa.getRuta_imagen());
            /*-----------------------------------------------*/
            map.put("RAZON_SOCIAL_EMISOR", empresa.getRazon_social());
            map.put("DIRECCION_EMISOR", empresa.getDireccion_emisor());
            map.put("DEP_PROV_DIST_EMISOR", empresa.getDep_prov_dist_emisor());
            map.put("TELEFONO_EMISOR", empresa.getTelefono_emisor());
            map.put("FAX_EMISOR", empresa.getFax_emisor());
            map.put("WEB_EMISOR", empresa.getWeb_emisor());
            map.put("CORREO_EMISOR", empresa.getCorreo_emisor());
            map.put("RUC_EMISOR", empresa.getRuc_emisor());
            /*-----------------------------------------------*/
            map.put("TITULO_DOCUMENTO", cabecera.getTitulo_documento());
            map.put("NUMERO_DOCUMENTO", cabecera.getNumero_documento());
            /*-----------------------------------------------*/
            map.put("RAZON_SOCIAL_CLIENTE", cabecera.getRazon_social_cliente());
            map.put("DIRECCION_CLIENTE", cabecera.getDireccion_cliente());
            map.put("RUC_CLIENTE", cabecera.getRuc_cliente());
            map.put("MONEDA_PAGO_DOCUMENTO", cabecera.getMoneda_pago_cliente());
            map.put("FECHA_EMISION_DOCUMENTO", cabecera.getFecha_emision_documento());
            map.put("CONDICION_PAGO_DOCUMENTO", cabecera.getCondicion_pago_documento());
            map.put("DOC_REFERENCIA", cabecera.getDoc_referencia());
            map.put("SUSTENTO_EMISION", cabecera.getSustento_emision());
            map.put("FECHA_DOC_REFERENCIA", cabecera.getFecha_doc_referencia());
            /*-----------------------------------------------*/
            WrapperItemObject itemObject = null;
            List<WrapperItemObject> listProductos = new ArrayList<WrapperItemObject>();
            Map<String, Object> detalleProducto = null;
            for (DocumentoDetalleNT detalleItem : listDetalles) {
                detalleProducto = new HashMap<String, Object>();
                detalleProducto.put("ITEM", detalleItem.getItem());
                detalleProducto.put("CANTIDAD", detalleItem.getCantidad());
                detalleProducto.put("UNIDAD", detalleItem.getUnidad());
                detalleProducto.put("DESCRIPCION", detalleItem.getDescripcion());
                detalleProducto.put("P_UNITARIO", detalleItem.getP_unitario());
                detalleProducto.put("SUBTOTAL", detalleItem.getSubtotal());
                itemObject = new WrapperItemObject();
                itemObject.setLstItemHashMap(detalleProducto);
                listProductos.add(itemObject);
            }
            /*-----------------------------------------------*/
            map.put("SUBTOTAL", cabecera.getSubtotal());
            map.put("MONTO_IGV", cabecera.getMonto_igv());
            map.put("MONTO_TOTAL", cabecera.getMonto_total());
            /*-----------------------------------------------*/
            CodeQR codigo = new CodeQR();
            String cadenaQR = empresa.getRuc_emisor() + "|" + 
                            cabecera.getTipo_documento() + "|" + 
                            cabecera.getNumero_documento().replace("-", "|") + "|" + 
                            cabecera.getMonto_igv() + "|" + 
                            cabecera.getMonto_total() + "|" + 
                            cabecera.getFecha_emision_documento() + "|" + 
                            cabecera.getTipo_documento_cliente() + "|" + 
                            cabecera.getRuc_cliente();
            String rutaQR = codigo.generateQR(cadenaQR, 300, 300);
            map.put("CODEQR", rutaQR);
            map.put("MONTO_TOTAL_LETRAS", cabecera.getMonto_total_letras());
            map.put("PORTALFE", empresa.getUrl_portal_fe());
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
            String rutaPDF = empresa.getRuta_pdf_A4() + "/" + empresa.getRuc_emisor() + "_" + cabecera.getNumero_documento() + "_" + fechaFormateada + "_" + horaFormateada + ".pdf";
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(rutaPDF));
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
