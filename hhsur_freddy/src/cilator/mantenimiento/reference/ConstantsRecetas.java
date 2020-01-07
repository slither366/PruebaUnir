package cilator.mantenimiento.reference;

import common.FarmaColumnData;

import javax.swing.JLabel;


public class ConstantsRecetas {
    
    public ConstantsRecetas() {
    }

    public static final FarmaColumnData columnsListaRecetas[] = { 
        new FarmaColumnData("C�digo", 60, JLabel.CENTER), 
        new FarmaColumnData("Descripci�n", 295, JLabel.LEFT),
        new FarmaColumnData("Presentaci�n", 120, JLabel.LEFT),
        new FarmaColumnData("Cant.Comp.", 70, JLabel.RIGHT)
    };
    
    public static final Object[] defaultValuesListaRecetas = { " ", " ", " ",""};
    
    public static final FarmaColumnData columnsListaComponentesDisponibles[] = { 
        new FarmaColumnData("C�digo", 60, JLabel.CENTER), 
        new FarmaColumnData("Descripci�n", 295, JLabel.LEFT),
        new FarmaColumnData("Presentaci�n", 120, JLabel.LEFT),
        new FarmaColumnData("Fracci�n", 60, JLabel.LEFT)
    };
    
    public static final Object[] defaultValuesListaComponentesDisponibles = { " ", " ", " ", " "};
    
    public static final FarmaColumnData columnsListaComponentes[] = {      
        new FarmaColumnData("C�digo", 60, JLabel.CENTER), 
        new FarmaColumnData("Descripci�n", 240, JLabel.LEFT),
        new FarmaColumnData("Presentaci�n", 120, JLabel.LEFT),
        new FarmaColumnData("Cantidad", 50, JLabel.RIGHT),
        new FarmaColumnData("Fracci�n", 50, JLabel.RIGHT),
        new FarmaColumnData("Ind Venta", 100, JLabel.RIGHT),
    };

    public static final Object[] defaultValuesListaComponentes = { " ", " ", " "," "," "," "};
    
    
    public static final String[] INDICADOR_TIPOVTASTOCK_CODIGO = {"MANDATORIO","PRESCINDIBLE"};
    public static final String[] INDICADOR_TIPOVTASTOCK_DESCRIPCION = {"MANDATORIO","PRESCINDIBLE"};

}
