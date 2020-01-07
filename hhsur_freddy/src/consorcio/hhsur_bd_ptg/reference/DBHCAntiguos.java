package consorcio.hhsur_bd_ptg.reference;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class DBHCAntiguos {

    public DBHCAntiguos() {

    }

    public List<String> listarHCAntiguos(String codPaciente, String fechaIni, String fechaFin) {
        Connection c = null;
        Statement stmt = null;
        List<String> listaHC = null;
        try {
            c = (new ConexionBDConsorcio()).connection();
            
            String query = "select c.descripcion || 'Ã' ||\n" + 
            "	       t.serie || 'Ã' ||\n" + 
            "	       t.nro_ticket || 'Ã' ||\n" + 
            "	       to_char(t.fecha_emision,'dd/mm/yyyy HH:MI AM') || 'Ã' ||\n" + 
            "	       t.cmp || 'Ã' ||\n" + 
            "	       to_char(cb.fecha_atencion,'dd/mm/yyyy') || 'Ã' ||\n" + 
            "	       (me.ape_paterno || ' ' || me.ape_materno || ', ' || me.nombres) || 'Ã' ||\n" + 
            "	       bu.bus || 'Ã' ||\n" + 
            "	       td.descripcion || 'Ã' ||\n" + 
            "	       t.nro_historia\n" + 
            "	from tickets t \n" + 
            "	inner join consultorios c on t.id_consultorio=c.id_consultorio\n" + 
            "	inner join tablatipo td on t.dventa=td.id_tipo and td.id_tabla='10'\n" + 
            "	inner join cab_cie10 cb on t.nro_historia=cb.nro_historia			  \n" + 
            "	inner join personal me on cb.cmp=me.id_personal\n" + 
            "	inner join buses bu on cb.id_bus=bu.id_bus \n" + 
            "	where t.id_paciente= '" + codPaciente + "'" +  
            "   \nand cb.fecha_atencion >= to_date('" + fechaIni + "', 'dd/mm/yyyy')" +
            "   \nand cb.fecha_atencion <= to_date('" + fechaFin + "', 'dd/mm/yyyy')" +
            "   \norder by cb.fecha_atencion desc;";

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            listaHC = new ArrayList<>();
            while (rs.next()) {
                String registroHC = rs.getString(1);
                listaHC.add(registroHC);
            }
            rs.close();
            stmt.close();
            c.close();
            return listaHC;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

}
