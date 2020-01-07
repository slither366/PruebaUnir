package venta.adm.productos.reference;

import javax.swing.JLabel;

import common.FarmaColumnData;

public class ConstantsMaestrosProductos {
    
    public static final FarmaColumnData columnsListaMaeProducto[] = {
               new FarmaColumnData( "Codigo", 55, JLabel.CENTER ),
               new FarmaColumnData( "Descripcion", 230, JLabel.LEFT ),
               new FarmaColumnData( "Laboratorio", 170, JLabel.LEFT ),
               new FarmaColumnData( "Unidad Entera", 110, JLabel.LEFT ),
               new FarmaColumnData( "Unidad Fracción", 100, JLabel.LEFT ),
               new FarmaColumnData( "Frac", 40, JLabel.RIGHT ),
               new FarmaColumnData( "", 0, JLabel.RIGHT ),//estado      
               new FarmaColumnData( "S/ Fracción ", 100, JLabel.RIGHT),
               new FarmaColumnData( "S/ Entero ", 100, JLabel.RIGHT)
             };
           
           public static final Object[] defaultValuesListaMaeProducto= {" "," ", " ", 
                                                                         " ", " "," ",
                                                                         " "," "," "};
           
    
    public static final FarmaColumnData columnsListaHistPrecios[] = {
               new FarmaColumnData( "Fecha Cambio", 150, JLabel.CENTER ),
               new FarmaColumnData( "Precio Antiguo", 170, JLabel.LEFT ),
               new FarmaColumnData( "Precio Nuevo", 170, JLabel.LEFT ),
               new FarmaColumnData( "Usuario", 110, JLabel.LEFT ),
               new FarmaColumnData( "", 0, JLabel.LEFT )
             };
           
           public static final Object[] defaultValuesListaHistPrecios= {" "," ", " ", 
                                                                         " ", " "};
           
    public static final FarmaColumnData columnsListaFracciones[] = {
               new FarmaColumnData( "UM", 0, JLabel.CENTER ),
               new FarmaColumnData( "Abreviatura", 100, JLabel.CENTER ),
               new FarmaColumnData( "Precio S/", 100, JLabel.CENTER ),
               new FarmaColumnData( "ValFracLocal", 0, JLabel.LEFT ),
               new FarmaColumnData( "PrecioMin", 0, JLabel.LEFT )
             };
           
           public static final Object[] defaultValuesListaFracciones= {" "," ", " ", " "," "};  
           
           
    public static final FarmaColumnData columnsListaProdLote[] = {
        new FarmaColumnData( "Lote", 130, JLabel.LEFT ),
        new FarmaColumnData( "Fecha Venc.", 80, JLabel.CENTER ),
        new FarmaColumnData( "Cantidad", 0, JLabel.CENTER )
    };
    
    public static final Object[] defaultValuesListaProdLote= {" "," ", " "};
}
