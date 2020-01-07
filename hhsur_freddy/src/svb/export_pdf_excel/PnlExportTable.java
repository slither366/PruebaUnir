package svb.export_pdf_excel;

import common.FarmaColumnData;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JTable;

import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;

import java.io.FileNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import venta.modulo_venta.DlgFiltro;
import venta.modulo_venta.DlgIngresoMedicoPedido;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import common.FarmaUtility;

import java.util.Iterator;

public class PnlExportTable extends JPanel {

    ImageIcon icon = new ImageIcon(PnlExportTable.class.getResource("/svb/export_pdf_excel/excel.jpg"));
    Image img = icon.getImage();
    Image img_uno = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon imagenExcel = new ImageIcon(img_uno);

    ImageIcon icon_d = new ImageIcon(PnlExportTable.class.getResource("/svb/export_pdf_excel/pdf.jpg"));
    Image img_d = icon_d.getImage();
    Image img_dos = img_d.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon imagenPDF = new ImageIcon(img_dos);

    private JButton btnExcel = new JButton(imagenExcel);
    private JButton btnPdf = new JButton(imagenPDF);

    public JTable tblExporta;
    public FarmaColumnData[] columnasTable;

    public PnlExportTable() {
        super();
    }

    public PnlExportTable(JTable tblLista, FarmaColumnData[] vColumnas) {
        try {
            tblExporta = tblLista;
            columnasTable = vColumnas;

            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void jbInit() throws Exception {

        //pnlExpReporte.setBounds(new Rectangle(945, 0, 145, 30));
        this.setSize(new Dimension(36, 30));
        setLayout(null);
        btnExcel.setText("");
        btnExcel.setBounds(new Rectangle(0, 0, 35, 30));
        btnExcel.setMargin(new Insets(0, 0, 0, 0));
        btnExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnExcel_actionPerformed(e);
            }
        });
        btnPdf.setText("");
        btnPdf.setBounds(new Rectangle(31, 0, 30, 30));
        btnPdf.setMargin(new Insets(0, 0, 0, 0));
        btnPdf.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPdf.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnPdf_actionPerformed(e);
                }
            });
        //this.add(btnPdf, null);
        this.add(btnExcel, null);
    }

    private void initialize() {
    }

    private void btnExcel_actionPerformed(ActionEvent e) {
        
        if(tblExporta.getRowCount()<=0){
            FarmaUtility.showMessage(new JDialog(), "No se puede exportar un listado vacío", new JTextField());
        }
        else{
            
            try {
                //Populate DefaultTableModel data
                DefaultTableModel dtm = new DefaultTableModel();
                Vector<String> cols = new Vector<String>();

                int cantidad = 0;

                for (int a = 0; a < columnasTable.length; a++) {
                    FarmaColumnData vAux = columnasTable[a];
                    if (vAux.m_width > 0) {
                        dtm.addColumn(vAux.m_title);
                        cantidad++;
                    }

                }

                Vector<String> dtmrow = null;

                dtmrow = new Vector<String>();
                for (int i = 0; i < columnasTable.length; i++) {
                    FarmaColumnData vAux = columnasTable[i];
                    if (vAux.m_width > 0) {
                        dtmrow.add(vAux.m_title);
                    }
                }
                dtm.addRow(dtmrow);

                for (int i = 0; i < tblExporta.getRowCount(); i++) {
                    dtmrow = new Vector<String>();
                    for (int j = 0; j < cantidad; j++) {
                        dtmrow.add(tblExporta.getValueAt(i, j).toString());

                    }
                    dtm.addRow(dtmrow);
                }

                Workbook wb = new HSSFWorkbook();
                CreationHelper createhelper = wb.getCreationHelper();
                Sheet sheet = wb.createSheet("hoja_1");
                Row row = null;
                Cell cell = null;
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    row = sheet.createRow(i);
                    for (int j = 0; j < dtm.getColumnCount(); j++) {
                        cell = row.createCell(j);
                        cell.setCellValue((String)dtm.getValueAt(i, j));
                    }
                }

                autoSizeColumns(wb);
                
                String pRuta = "";
                /*JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("You chose to open this directory: " +
                        chooser.getSelectedFile().getAbsolutePath());
                    pRuta = chooser.getSelectedFile().getAbsolutePath();
                }
                else*/
                    pRuta = "D:\\";
                    
                    
                FileOutputStream out = new FileOutputStream(pRuta+"rpt_vta_x_dia.xls");
                wb.write(out);
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void autoSizeColumns(Workbook workbook) { 
        int numberOfSheets = workbook.getNumberOfSheets(); 
        for (int i = 0; i < numberOfSheets; i++) { 
         Sheet sheet = workbook.getSheetAt(i); 
         if (sheet.getPhysicalNumberOfRows() > 0) { 
          Row row = sheet.getRow(0); 
          Iterator<Cell> cellIterator = row.cellIterator(); 
          while (cellIterator.hasNext()) { 
           Cell cell = cellIterator.next(); 
           int columnIndex = cell.getColumnIndex(); 
           sheet.autoSizeColumn(columnIndex); 
          } 
         } 
        } 
    }

    private void btnPdf_actionPerformed(ActionEvent e) {
        try {
            int count = tblExporta.getRowCount();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:/data.pdf"));
            document.open();
            PdfPTable tab = new PdfPTable(columnasTable.length);
            
             for (int a = 0; a < columnasTable.length; a++) {
                 FarmaColumnData vAux = columnasTable[a];
                 if (vAux.m_width > 0) {
                     tab.addCell(vAux.m_title);
                 }
             }
            
             for (int i = 0; i < tblExporta.getRowCount(); i++) {
                 for (int j = 0; j < columnasTable.length; j++) {
                     tab.addCell(tblExporta.getValueAt(i, j).toString());
                 }
             }
             
            document.add(tab);
            
            document.close();
         }
        catch(Exception v) {
        }
    }

    public Object GetData(JTable table, int row_index, int col_index){
        return table.getModel().getValueAt(row_index, col_index);
    }
}
