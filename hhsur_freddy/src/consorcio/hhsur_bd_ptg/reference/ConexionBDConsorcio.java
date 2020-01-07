package consorcio.hhsur_bd_ptg.reference;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBDConsorcio {
    
    public ConexionBDConsorcio() {
        
    }
    
    public Connection connection(){
        Connection conn = null;
        String urlDatabase =  "jdbc:postgresql://192.168.10.4:5432/humanidad"; 
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDatabase,  "webserver", "oserweb");
        } catch (Exception e) {
            System.out.println("Ocurrio un error al conectar a BD Postgress: "+e.getMessage());
        }
        System.out.println("La conexión se realizo sin problemas a BD Postgress! =) ");
        return conn;
    }
    
}
