package cilator.mantenimiento.reference;

import componentes.gs.componentes.JTextFieldSanSerif;

import javax.swing.JLabel;
import javax.swing.JTextField;

import common.FarmaColumnData;

public class ContantsMantenimiento 
{
  public ContantsMantenimiento()
  {
  }
    public static final FarmaColumnData columnsListaRecetas[] = { 
        new FarmaColumnData("Código", 60, JLabel.CENTER), 
        new FarmaColumnData("Descripción", 308, JLabel.LEFT),
        new FarmaColumnData("Presentación", 0, JLabel.LEFT),
        new FarmaColumnData("Cant.Comp.", 70, JLabel.RIGHT)
    };
    
    public static final Object[] defaultValuesListaRecetas = { " ", " ", " ",""};
    
        
  public static final FarmaColumnData[] columnsListaControlHoras = {
      new FarmaColumnData("Fecha      Hora  ", 130, JLabel.CENTER),
      //new FarmaColumnData("Indicador", 165, JLabel.LEFT),
      new FarmaColumnData("Motivo", 220, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT),
  };

  public static final Object[] defaultValuesListaControlHoras = { " ", " "};
  
  
    public static final FarmaColumnData[] columnsListaContacto = {
    new FarmaColumnData("DNI.", 70, JLabel.CENTER),
    new FarmaColumnData("Ap. Paterno", 120, JLabel.LEFT),
    new FarmaColumnData("Ap. Materno", 120, JLabel.LEFT),
    new FarmaColumnData("Nombres", 150, JLabel.LEFT),
    new FarmaColumnData("Fec.Nacimiento", 96, JLabel.LEFT),
    new FarmaColumnData("Sexo", 50, JLabel.LEFT),
    new FarmaColumnData("direcc", 120, JLabel.LEFT),
    new FarmaColumnData("Email", 100, JLabel.LEFT),
    new FarmaColumnData("Telf", 96, JLabel.LEFT),
    new FarmaColumnData("Celular 1", 96, JLabel.LEFT),
    new FarmaColumnData("Celular 2",96, JLabel.LEFT),
    };

     public static final Object[] defaultValuesListaContacto = {" "," ",
     " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " "};
     
     
     // tipo
     public static final FarmaColumnData[] columnsListaTipo = {
     new FarmaColumnData("Codigo.", 90, JLabel.CENTER),
     new FarmaColumnData("Nombre", 300, JLabel.LEFT)
     };

      public static final Object[] defaultValuesListaTipo = {" "," "};     
      
      
    // marca
    public static final FarmaColumnData[] columnsListaMarca = {
    new FarmaColumnData("Codigo.", 90, JLabel.CENTER),
    new FarmaColumnData("Nombre", 300, JLabel.LEFT)
    };

     public static final Object[] defaultValuesListaMarca = {" "," "}; 

    // categoria
    public static final FarmaColumnData[] columnsListaCategoria = {
    new FarmaColumnData("Codigo.", 90, JLabel.CENTER),
    new FarmaColumnData("Nombre", 300, JLabel.LEFT)
    };

     public static final Object[] defaultValuesListaCategoria = {" "," "}; 

    // sub categoria
    public static final FarmaColumnData[] columnsListaSubCategoria = {
    new FarmaColumnData("Codigo.", 90, JLabel.CENTER),
    new FarmaColumnData("Sub Categoria", 300, JLabel.LEFT),
    new FarmaColumnData("Categoria", 300, JLabel.LEFT)
    };

     public static final Object[] defaultValuesListaSubCategoria = {" "," "}; 
     
     ////////// producto //////////////
     public static final FarmaColumnData columnsListaProductos[] = {
       new FarmaColumnData( "Código", 60, JLabel.LEFT ), // 1
       new FarmaColumnData( "Descripción", 330, JLabel.LEFT ), // 2
       new FarmaColumnData( "Marca",120, JLabel.CENTER ), // 3
       new FarmaColumnData( "Tipo",120, JLabel.CENTER ),// 4
       new FarmaColumnData( "Categoría", 150, JLabel.CENTER ), // 5
       new FarmaColumnData( "Sub.Categoria", 135, JLabel.CENTER ), // 6
       new FarmaColumnData( "S/ .",75, JLabel.RIGHT ),// 7
       new FarmaColumnData( "$/ .",75, JLabel.RIGHT ),// 8
       new FarmaColumnData( "Modo Producto", 135, JLabel.CENTER ) // 6
     };

     public static final Object[] defaultValuesListaProductos =
     { " ", " ", " ", " ", " ", 
       " ", " ", " ", " ", " "};     

}