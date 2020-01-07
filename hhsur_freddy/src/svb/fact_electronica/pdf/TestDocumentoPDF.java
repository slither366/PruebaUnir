package svb.fact_electronica.pdf;

import svb.fact_electronica.pdf.generadorPDF.GeneradorPDF;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDocumentoPDF {
    
    public static Connection conn = null;
    
    public static void openConnectionOracle() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR DE CREACION DE CONEXION ORACLE");
        }
        String url = "jdbc:oracle:thin:@"+"localhost"+":1521:" + "XE";
        String usuario = "venta_new";// + " as valor por defecto";
        String clave = "venta";
        try {
            conn = DriverManager.getConnection(url, usuario, clave);
        } catch (SQLException ex) {
            System.out.println("ERROR DE CREACION DE CONEXION ORACLE");
        }
        if (conn != null) {
            System.out.println("CONEXION ORACLE EXITOSA");
        } else {
            System.out.println("ERROR DE CONEXION ORACLE");
        }
    }
    
    public static void main(String[] args) throws SQLException{
        openConnectionOracle();
        GeneradorPDF generadorPDF = new GeneradorPDF();
        boolean flagPDF = generadorPDF.crearPDF(conn, "F", "0000002198", "001", "001");
        if(flagPDF){
            System.out.println("Se generó el documento PDF_FB correctamente.");
        }else{
            System.out.println("Error al generar el documento PDF_FB.");
        }
        
        /*
        flagPDF = generadorPDF.crearPDF(conn, "N", "0000000875", "001", "001");
        if(flagPDF){
            System.out.println("Se generó el documento PDF_NT correctamente.");
        }else{
            System.out.println("Error al generar el documento PDF_NT.");
        }*/
    }
}
