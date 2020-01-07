package consorcio.hhsur_bd_ptg;

import consorcio.hhsur_bd_ptg.reference.ConexionBDConsorcio;

import java.sql.Connection;

public class Test {
    public static void main(String[] args){
        ConexionBDConsorcio conexion = new ConexionBDConsorcio();
        Connection conn = null;
        conn = conexion.connection();
        if(conn != null){
            System.out.println("Conexion exitosa");
        }else{
            System.out.println("Error de Conexion");
        }
    }
}
