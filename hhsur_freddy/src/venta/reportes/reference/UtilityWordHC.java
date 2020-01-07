package venta.reportes.reference;

import common.FarmaPRNUtility;
import common.FarmaUtility;

import java.awt.Desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;

import java.math.BigInteger;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import venta.FrmEconoFar;

public class UtilityWordHC {
    public UtilityWordHC() {
        super();
    }

    public static void main(String[] args) {
        prueba_01();
    }


    private static void prueba_01() {
        

        
        try {
            //creaBlanco();
            //ejemploDos();
            //conBorde();
            //conTabla();
            //conEstilo();
            //conImagen();
            //grabaFile();
            /*
            String pNumComp,
                                             String pDNI,
                                             String pNombre,
                                             String pApellidoPat,
                                             String pApellidoMat,
                                             String pFecNac,
                                             String pEdad,
                                             String pPeso,
                                             String pSexo,
                                             String pDireccion,
                                             String pTelefono,
                                             String pEmail,
                                             String pOcupacion,
                                             String pCaso
             * */
            genera_hc_preliminar("d:\\word","12/01/2017","F","013-0000076","44324600","Diego","Ubilluz","Carrillo","16/06/1987","30","130 Kg","Masculino","mi casa",
                      "94019701","sasdas@,casas.com","no se q hago","caso2017","Doctor Gogo"," todos los examenes");

           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void creaBlanco() throws Exception {
        //Blank Document
         XWPFDocument document= new XWPFDocument(); 
         //Write the Document in file system
         FileOutputStream out = new FileOutputStream(new File("D:\\word\\createdocument.docx"));
         document.write(out);
         out.close();
         System.out.println("createdocument.docx written successully");
    }
    
    public static void ejemploDos() throws Exception {
        //Blank Document
        XWPFDocument document= new XWPFDocument(); 
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("D:\\word\\createparagraph.docx"));
          
        //create Paragraph
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run=paragraph.createRun();
        run.setText("At tutorialspoint.com, we strive hard to " +
           "provide quality tutorials for self-learning " +
           "purpose in the domains of Academics, Information " +
           "Technology, Management and Computer ProgrammingLanguages.");
                          
        document.write(out);
        out.close();
        System.out.println("createparagraph.docx written successfully");
    }

    public static void conBorde() throws Exception{
        //Blank Document
             XWPFDocument document= new XWPFDocument(); 
               
             //Write the Document in file system
             FileOutputStream out = new FileOutputStream(new File("D:\\word\\applyingborder.docx"));
               
             //create paragraph
             XWPFParagraph paragraph = document.createParagraph();
               
             //Set bottom border to paragraph
             paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
               
             //Set left border to paragraph
             paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
               
             //Set right border to paragraph
             paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
               
             //Set top border to paragraph
             paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
               
             XWPFRun run=paragraph.createRun();
                run.setText("At tutorialspoint.com, we strive hard to " +
                "provide quality tutorials for self-learning " +
                "purpose in the domains of Academics, Information " +
                "Technology, Management and Computer Programming " +
                "Languages.");
               
             document.write(out);
             out.close();
             System.out.println("applyingborder.docx written successully");
          
    }

    public static void conTabla() throws Exception{
        //Blank Document
              XWPFDocument document= new XWPFDocument();
                
              //Write the Document in file system
              FileOutputStream out = new FileOutputStream(new File("D:\\word\\create_table.docx"));
                
              //create table
              XWPFTable table = document.createTable();
                        
              //create first row
              XWPFTableRow tableRowOne = table.getRow(0);
              tableRowOne.getCell(0).setText("col one, row one");
              tableRowOne.addNewTableCell().setText("col two, row one");
              tableRowOne.addNewTableCell().setText("col three, row one");
                        
              //create second row
              XWPFTableRow tableRowTwo = table.createRow();
              tableRowTwo.getCell(0).setText("col one, row two");
              tableRowTwo.getCell(1).setText("col two, row two");
              tableRowTwo.getCell(2).setText("col three, row two");
                        
              //create third row
              XWPFTableRow tableRowThree = table.createRow();
              tableRowThree.getCell(0).setText("col one, row three");
              tableRowThree.getCell(1).setText("col two, row three");
              tableRowThree.getCell(2).setText("col three, row three");
                
              document.write(out);
              out.close();
              System.out.println("create_table.docx written successully");
    }

    public static void conEstilo() throws Exception{
        //Blank Document
              XWPFDocument document= new XWPFDocument(); 
                
              //Write the Document in file system
              FileOutputStream out = new FileOutputStream(new File("D:\\word\\fontstyle.docx"));
                
              //create paragraph
              XWPFParagraph paragraph = document.createParagraph();
              //Set Bold an Italic
              XWPFRun paragraphOneRunOne = paragraph.createRun();
              paragraphOneRunOne.setBold(true);
              paragraphOneRunOne.setItalic(true);
              paragraphOneRunOne.setText("Font Style");
        
              paragraphOneRunOne.addBreak();
                
              //Set text Position
              XWPFRun paragraphOneRunTwo = paragraph.createRun();
              paragraphOneRunTwo.setText("Font Style two");
              paragraphOneRunTwo.setTextPosition(100);
         
              //Set Strike through and Font Size and Subscript
              XWPFRun paragraphOneRunThree = paragraph.createRun();
              paragraphOneRunThree.setStrike(true);
              paragraphOneRunThree.setFontSize(20);
              //paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
              paragraphOneRunThree.setText(" Different Font Styles");
                
              document.write(out);
              out.close();
              System.out.println("fontstyle.docx written successully");
    }
    
    public static void conImagen() throws Exception{
        // The path to the documents directory.
            String dataDir = "D:\\word\\";
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            
            /*XSSFPrintSetup printSetup = (XSSFPrintSetup) doc.getXWPFDocument().get  sheet.getPrintSetup();
           printSetup.setHeaderMargin(0.5D);
           printSetup.setFooterMargin(0.5D);*/
           
            String imgFileCabecera = dataDir + "cabecera.png";
            String imgFilePie = dataDir + "pie.png";
        
            XWPFRun r = p.createRun();
            
            //doc.getpage getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
        
            int format = XWPFDocument.PICTURE_TYPE_PNG;
            r.addBreak();
            r.addPicture(new FileInputStream(imgFileCabecera), format, imgFileCabecera, Units.toEMU(550), Units.toEMU(100)); // 200x200 pixels
            
            r.addPicture(new FileInputStream(imgFilePie), format, imgFilePie, Units.toEMU(550), Units.toEMU(100)); // 200x200 pixels
            
            
            r.addBreak(BreakType.PAGE);
            FileOutputStream out = new FileOutputStream(dataDir + "Apache_ImagesInDoc.docx");
            doc.write(out);
            out.close();
         
            System.out.println("Process Completed Successfully");
    }
    
    public static void genera_hc(
                                 String pRuta,
                                 String pFechaImpresion,
                                 String pTipo,
                                 String pNumComp,
                                 String pDNI,
                                 String pNombre,
                                 String pApellidoPat,
                                 String pApellidoMat,
                                 String pFecNac,
                                 String pEdad,
                                 String pPeso,
                                 String pSexo,
                                 String pDireccion,
                                 String pTelefono,
                                 String pEmail,
                                 String pOcupacion,
                                 String pCaso,
                                 String pMedico,
                                 String pExamenes,
                                 ArrayList vDatosGenerales,
                                 ArrayList vDatosCancer,
                                 ArrayList vDatosOperaciones,
                                 ArrayList vDatosEstudiosExamenes,
                                 ArrayList vDatosResponsable,
                                 ArrayList vDatosIndEnfermera
                                 ) throws Exception{
        // The path to the documents directory.
            String dataDir = "D:\\imagenes\\";
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            
            /*XSSFPrintSetup printSetup = (XSSFPrintSetup) doc.getXWPFDocument().get  sheet.getPrintSetup();
           printSetup.setHeaderMargin(0.5D);
           printSetup.setFooterMargin(0.5D);*/
           
            String imgFileCabecera = dataDir + "cabecera.jpg";
                                    //FrmEconoFar.class.getResource("/venta/imagenes/cabecera.jpg").getPath();
            String imgFilePie = dataDir + "pie.jpg";
                                    //FrmEconoFar.class.getResource("/venta/imagenes/pie.jpg").getPath();
        
            XWPFRun r = p.createRun();
            
            //doc.getpage getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
        
            int format = XWPFDocument.PICTURE_TYPE_JPEG;
            r.addPicture(new FileInputStream(imgFileCabecera), format, imgFileCabecera, Units.toEMU(500), Units.toEMU(100)); // 200x200 pixels
            //agrega datos de DNI
            //Set Strike through and Font Size and Subscript
            r.setFontSize(9);
            //paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
            r.setText("Fecha : "+pFechaImpresion +"                                   DNI : "+pDNI);
            r.addBreak();
            r.setText("Nombres y Apellidos : "+pNombre+" "+pApellidoPat+" "+pApellidoMat);
            r.addBreak();
            r.setText("Fecha Nacimiento  :"+pFecNac+ "         Edad : "+pEdad+"        Peso :"+ pPeso+"    Sexo : "+pSexo);
            r.addBreak();
            r.setText("Dirección : "+pDireccion+" "+"                                                Teléfono:"+" "+pTelefono);
            r.addBreak();
            r.setText("Referido por Dr(a). "+pMedico);
            r.addBreak();
            r.setText("Tipo de examen . "+pExamenes);            
            r.addBreak();
            r.setText("Email :"+pEmail+"                                     Ocupación :"+pOcupacion);
            r.addBreak();
            r.setText("Caso : "+pCaso+"                                                     COMPROBANTE :"+pNumComp);
            
            r.addBreak();
            r.addBreak();
            
            int pPos = 1;
            for(int a=0;a < vDatosGenerales.size();a++){
                if(a==0){
                    r.setText("---------------------------       DATOS   GENERALES QUE PRESENTA         ---------------------------------");

                    r.addBreak();
                }
                
                r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosGenerales,a,0),30));
                if(pPos == 3){
                    r.addBreak();
                    pPos = 0;
                }
                pPos++;
            }
            
             pPos = 1;
                    for(int a=0;a < vDatosCancer.size();a++){
                        if(a==0){
                            r.setText("---------------------------       DATOS  DE CANCER         ---------------------------------");

                            r.addBreak();
                        }
                        
                        r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosCancer,a,0),200));
                        if(pPos == 1){
                            r.addBreak();
                            pPos = 0;
                        }
                        pPos++;
                    }
            
        pPos = 1;
               for(int a=0;a < vDatosOperaciones.size();a++){
                   if(a==0){
                       r.setText("---------------------------       DATOS  DE OPERACIONES         ---------------------------------");

                       r.addBreak();
                   }
                   
                   r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosOperaciones,a,0),200));
                   if(pPos == 1){
                       r.addBreak();
                       pPos = 0;
                   }
                   pPos++;
               }        
               
        pPos = 1;
               for(int a=0;a < vDatosEstudiosExamenes.size();a++){
                   if(a==0){
                       r.setText("-------- ESTUDIOS ANTERIORES / EXAMENES COMPLEMENTARIOS -----------------------------");

                       r.addBreak();
                   }
                   
                   r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosEstudiosExamenes,a,0),200));
                   if(pPos == 1){
                       r.addBreak();
                       pPos = 0;
                   }
                   pPos++;
               }      

        pPos = 1;
               for(int a=0;a < vDatosResponsable.size();a++){
                   if(a==0){
                       r.setText("---------------------------       RESPONSABLE DE PACIENTE -----------------------------");

                       r.addBreak();
                   }
                   
                   r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosResponsable,a,0),200));
                   if(pPos == 1){
                       r.addBreak();
                       pPos = 0;
                   }
                   pPos++;
               }   
               
        
        
        pPos = 1;
               for(int a=0;a < vDatosIndEnfermera.size();a++){
                   if(a==0){
                       r.setText("--------- Indicaciones de la Enfermera o Tecnologo Medico -----------------------------");

                       r.addBreak();
                   }
                   
                   r.setText(FarmaPRNUtility.alinearIzquierda("* "+FarmaUtility.getValueFieldArrayList(vDatosIndEnfermera,a,0),200));
                   if(pPos == 1){
                       r.addBreak();
                       pPos = 0;
                   }
                   pPos++;
               }  
        
            r.addBreak();
            r.addBreak();
            r.setText("     _____________________________                            _____________________________");
            r.addBreak();
            r.setText("      Firma del familiar/responsable                                  Firma del Paciente");
        
            //r.addPicture(new FileInputStream(imgFilePie), format, imgFilePie, Units.toEMU(500), Units.toEMU(400)); // 200x200 pixels
            //r.addBreak(BreakType.PAGE);
            FileOutputStream out = new FileOutputStream(pRuta+"\\" +pTipo+"_" +pDNI+"_"+pNumComp+".docx");
            doc.write(out);
            out.close();
            
            try {
                  Desktop desktop = null;
                  if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                  }
                   desktop.open(new File(pRuta+"\\" +pTipo+"_" +pDNI+"_"+pNumComp+".docx"));
                } catch (IOException ioe) {
                  ioe.printStackTrace();
                }
            
            System.out.println("Process Completed Successfully");
    }
    
    public static void grabaFile() throws Exception{
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph tmpParagraph = document.createParagraph();
        
        XWPFRun tmpRun = tmpParagraph.createRun();
        tmpRun.setText("Apache Sample Content for Word file.");
        
        FileOutputStream fos = new FileOutputStream("D:\\word\\Apache_SaveDoc.doc");
        document.write(fos);
        fos.close();
        
       System.out.println("Process Completed Successfully");
    }
    
    public static void setSingleLineSpacing(XWPFParagraph para) {
        //XWPFParagraph
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) ppr = para.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }
    
    public static void genera_hc_preliminar(
                                 String pRuta,
                                 String pFechaImpresion,
                                 String pTipo,
                                 String pNumComp,
                                 String pDNI,
                                 String pNombre,
                                 String pApellidoPat,
                                 String pApellidoMat,
                                 String pFecNac,
                                 String pEdad,
                                 String pPeso,
                                 String pSexo,
                                 String pDireccion,
                                 String pTelefono,
                                 String pEmail,
                                 String pOcupacion,
                                 String pCaso,
                                 String pMedico,
                                 String pExamenes
                                 ) throws Exception{
        // The path to the documents directory.
            String dataDir = "D:\\imagenes\\";
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            
           /* XSSFPrintSetup printSetup = (XSSFPrintSetup) doc.getXWPFDocument().get;
                //.getsheet.getPrintSetup();
           printSetup.setHeaderMargin(0.5D);
           printSetup.setFooterMargin(0.5D);*/
            
            String imgFileCabecera = dataDir + "cabecera.jpg";
                                    //FrmEconoFar.class.getResource("/venta/imagenes/cabecera.jpg").getPath();
            String imgFilePie = dataDir + "pie.jpg";
                                    //FrmEconoFar.class.getResource("/venta/imagenes/pie.jpg").getPath();
        
            XWPFRun r = p.createRun();
            
            //doc.getpage getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
            setSingleLineSpacing(p);
            
            
            int format = XWPFDocument.PICTURE_TYPE_JPEG;
            r.addPicture(new FileInputStream(imgFileCabecera), format, imgFileCabecera, Units.toEMU(500), Units.toEMU(100)); // 200x200 pixels
            //agrega datos de DNI
            //Set Strike through and Font Size and Subscript
            r.setFontSize(9);
            //paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
            r.addBreak();
            r.setText("Fecha : "+pFechaImpresion +"                                   DNI : "+pDNI);
            r.addBreak();
            r.setText("Apellidos y Nombres : "+pApellidoPat+" "+pApellidoMat + " "+pNombre);
            r.addBreak();
            r.setText("Fecha Nacimiento  :"+pFecNac+ "         Edad : "+pEdad+"        Peso :"+ pPeso+"    Sexo : "+pSexo);
            r.addBreak();
            r.setText("Dirección : "+pDireccion+" "+"                                                Teléfono:"+" "+pTelefono);
            r.addBreak();
            r.setText("Referido por Dr(a). "+pMedico);
            r.addBreak();
            r.setText("Tipo de examen . "+pExamenes);            
            r.addBreak();
            r.setText("Email :"+pEmail+"                                     Ocupación :"+pOcupacion);
            r.addBreak();
            r.setText("Caso : "+pCaso+"                                                     COMPROBANTE :"+pNumComp);
            
            r.addBreak();

            r.addPicture(new FileInputStream(imgFilePie), format, imgFileCabecera, Units.toEMU(500), Units.toEMU(550)); // 200x200 pixels            
            
            //r.addPicture(new FileInputStream(imgFilePie), format, imgFilePie, Units.toEMU(500), Units.toEMU(400)); // 200x200 pixels
            //r.addBreak(BreakType.PAGE);
            FileOutputStream out = new FileOutputStream(pRuta+"\\" +pTipo+"_" +pDNI+"_"+pNumComp+".docx");
            doc.write(out);
            out.close();
            
            try {
                  Desktop desktop = null;
                  if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                  }
                   desktop.open(new File(pRuta+"\\" +pTipo+"_" +pDNI+"_"+pNumComp+".docx"));
                } catch (IOException ioe) {
                  ioe.printStackTrace();
                }
            
            System.out.println("Process Completed Successfully");
    }
}
