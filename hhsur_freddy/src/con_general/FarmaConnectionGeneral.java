package con_general;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FarmaConnectionGeneral {    
    /** Almacena la conexi�n actualmente vigente */
    Connection conn;

    /** String de conexi�n Remota */
    public String connect_string_remota = "";

    //pUsuarioBD,pClaveBD,pIpServBD,pPuertoBD,pSidBD
    public FarmaConnectionGeneral(String pUsuarioBD,
                                  String pClaveBD,
                                  String pIpServBD,
                                  String pPuertoBD,
                                  String pSidBD) {
        /** String de conexi�n modo THIN */

        connect_string_remota =  "jdbc:oracle:thin:" + 
                                            pUsuarioBD + "/" + 
                                            pClaveBD + "@" + 
                                            pIpServBD + ":" + 
                                            pPuertoBD + ":" + 
                                            pSidBD;
        //System.out.println(">>> <<<"+connect_string_remota);

    }
    
    

    

        
    /**
     *Retorna la actual conexi�n de Base de Datos.
     *@return Connection Objeto de conexi�n retornado.
     */
    public Connection getConnection() throws SQLException {
        //System.out.println(">>");
        if (conn == null || conn.isClosed() ) {
            //System.out.println("Abre conexion");
            //System.out.println("Abre conexion Remota..");
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            System.out.println(connect_string_remota);
            //DriverManager.setLoginTimeout(3);
            //conn.
            conn = DriverManager.getConnection(connect_string_remota);
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@EDBDES00","ECVENTA","VENTA");
            
            conn.setAutoCommit(false);
        }
        else{
            //System.out.println("NO Abre conexion");
        }
        return conn;
    }


    public Connection getConnection(int pTimeConection) throws SQLException {
        //String connect_string_thin = "jdbc:oracle:thin:ptoventa/ptoventa_prueba@10.11.1.99:1521:XE";
        System.out.println("Get Connection 2");
        if (conn == null) {
            System.out.println("pTimeConection:"+pTimeConection);
           // System.out.println("connect_string_thin:"+connect_string_thin);
            DriverManager.setLoginTimeout(pTimeConection);
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(connect_string_remota);
            conn.setAutoCommit(false);
        }
        return conn;
    }
    /**
     *Cierra la conexi�n de Base de Datos.
     */
    public void closeConnection() {
        if (conn != null) {
            //System.out.println("Cierre de conexion Remota..");
            try {
                conn.close();
                conn = null;
            } catch (Exception e) {
            }
        }
        return;
    }

    /**
     *Constructor
     */

}