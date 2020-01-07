package dental.laboratorio.reference;


import common.FarmaColumnData;

import javax.swing.JLabel;

public class ConstantsMantenimiento {

    public ConstantsMantenimiento() {
        //Constructor de la clase ConstantsMantenimiento
    }

    public static final FarmaColumnData[] columnsListaLaboratorios =
    { new FarmaColumnData("Código", 90, JLabel.CENTER), new FarmaColumnData("Nombre", 175, JLabel.LEFT), 
      new FarmaColumnData("Dirección", 173, JLabel.LEFT), new FarmaColumnData("Teléfono", 75, JLabel.LEFT) };

    public static final Object[] defaultValuesListaLaboratorios = { " ", " ", " ", " " };
    

    public static final FarmaColumnData[] columnsListaProductos =
    { new FarmaColumnData("Estado", 20, JLabel.CENTER), 
        new FarmaColumnData("Código", 60, JLabel.CENTER), 
      new FarmaColumnData("Nombre Producto", 440, JLabel.LEFT), 
      new FarmaColumnData("Costo", 75, JLabel.LEFT) ,
      new FarmaColumnData("Frac.", 40, JLabel.LEFT) ,
        new FarmaColumnData("%Gan.", 60, JLabel.LEFT) ,
        new FarmaColumnData("Estado", 50, JLabel.LEFT),
    new FarmaColumnData("Marca", 160, JLabel.LEFT),
    };

    public static final Object[] defaultValuesListaProductos = {" ", " ", " ", " ", " ", " ", " " , " "};
    
    
    public static final FarmaColumnData[] columnsListaUnidadPrecio =
    { 

      new FarmaColumnData("Unidad", 90, JLabel.LEFT), 
      new FarmaColumnData("#Cantidad", 80, JLabel.LEFT), 
        new FarmaColumnData("%Ganancia", 80, JLabel.LEFT), 
      new FarmaColumnData("S/ Precio Sugerido", 80, JLabel.LEFT), 
    new FarmaColumnData("S/ Precio Minimo", 80, JLabel.LEFT), 
    new FarmaColumnData("S/ Precio Tercero", 80, JLabel.LEFT), 
      
    };

    public static final Object[] defaultValuesListaUnidadPrecio = { " ", " ", " "," "," "," "};
    

    
    public static final FarmaColumnData[] columnsListaLoteProd =
    { 
      new FarmaColumnData("N° Lote", 110, JLabel.LEFT), 
      new FarmaColumnData("Fecha Vencimiento", 60, JLabel.LEFT),  
    };

    public static final Object[] defaultValuesListaLoteProd = { " ", " "};
    


}
