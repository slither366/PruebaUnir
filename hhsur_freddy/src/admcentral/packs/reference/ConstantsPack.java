package admcentral.packs.reference;

import common.FarmaColumnData;

import javax.swing.JLabel;


public class ConstantsPack {
    
    
    /**
    * Constantes usadas en el modulo de Mantenimiento de Pack
    * @author Javier Callo
    * @since  15.09.2008
    */ 
    
    public static final FarmaColumnData columnsListaPack[] =
    {   new FarmaColumnData("Codigo", 60, JLabel.CENTER),
        new FarmaColumnData("Descripcion", 240, JLabel.LEFT),
        new FarmaColumnData("Cod_Paq1", 0, JLabel.CENTER),
        new FarmaColumnData("Cod_Paq2", 0, JLabel.CENTER),
        new FarmaColumnData("Estado", 60, JLabel.CENTER),
        new FarmaColumnData("Max_vta", 55, JLabel.LEFT),
    new FarmaColumnData("Desde", 90, JLabel.CENTER),
    new FarmaColumnData("Hasta", 90, JLabel.CENTER),
    new FarmaColumnData("Creado", 120, JLabel.CENTER),
    new FarmaColumnData("Modificado", 120, JLabel.CENTER)
        };
    
    public static final Object[] defaultValuesListaPack =
    { " ", " ", " ", " ", " ", " ", " ", " ", " "," "};
    
    
    /**
    * Constantes usadas en el modulo de Mantenimiento de Pack
    * @author Javier Callo
    * @since  16.09.2008
    */ 
    
    public static final FarmaColumnData columnsListaPaqueteAll[] =
    {   new FarmaColumnData("Codigo", 60, JLabel.CENTER),
        new FarmaColumnData("Descripcion", 240, JLabel.LEFT),
        new FarmaColumnData("Cantidad", 60, JLabel.LEFT),
        new FarmaColumnData("Porc Dcto", 60, JLabel.LEFT),
        new FarmaColumnData("ValFrac", 60, JLabel.LEFT)
        };
    
    public static final Object[] defaultValuesListaPaqueteAll =
    { " ", " ", " ", " ", " "};
    
    /**
    * Constantes usadas en el modulo de Mantenimiento de Pack
    * @author Javier Callo
    * @since  18.09.2008
    */     
    public static final FarmaColumnData columnsListaPaquete[] =
    {   new FarmaColumnData("Codigo", 60, JLabel.CENTER),
        new FarmaColumnData("Descripcion", 240, JLabel.LEFT)
        
        };
    
    public static final Object[] defaultValuesListaPaquete =
    { " ", " "};
    
    
    /**
    * Constantes usadas en el modulo de Mantenimiento de Pack
    * @author Javier Callo
    * @since  16.09.2008
    */ 
    
    public static final FarmaColumnData columnsListaPackProds[] =
    {           
        new FarmaColumnData("  ", 20, JLabel.CENTER),
        new FarmaColumnData("Codigo", 60, JLabel.CENTER),
        new FarmaColumnData("Descripcion", 300, JLabel.LEFT),
        new FarmaColumnData("Marca.", 130, JLabel.LEFT),
        new FarmaColumnData("Valor Max.", 0, JLabel.LEFT),
    new FarmaColumnData("Cantidad", 60, JLabel.LEFT),
    new FarmaColumnData("PorcDcto", 60, JLabel.LEFT),
    new FarmaColumnData("ValFrac.", 0, JLabel.LEFT),
    new FarmaColumnData("Undidad Venta.", 80, JLabel.LEFT)
        };
    
    public static final Object[] defaultValuesListaPackProds =
    { " ", " ", " ", " ", " ", " ", " "," "," "};
    
    
    public static String ACCION_CREAR = "CREAR";
    public static String ACCION_MODIFICAR = "MODIFICAR";
    public static String ACCION_VER = "VER";
    
    public static String ACCION_PP_CREAR = "CREAR_PP";
    public static String ACCION_PP_MODIF = "MODIF_PP";
    
    public static String PAQUETE_1 = "PAQUETE 1";
    public static String PAQUETE_2 = "PAQUETE 2";
    
    /**
     * constantes para ver acciones de creacion (C), modificacion(M) o eliminacion
     * de cada productos por paquete al momento de modificar un pack
     * **/
    
    public static String ACCION_CREAR_PROD_PAQ = "CREAR_PAQUETE";    
    public static String ACCION_MODIFICAR_PROD_PAQ = "MODIFICAR_PAQUETE";
    public static String ACCION_ELIMINAR_PROD_PAQ = "ELIMINAR_PAQUETE";
    public static String ACCION_NINGUNA_PROD_PAQ = "NADA_PAQUETE";
    
    public static final int COL_COD_PP=0;
    public static final int COL_DESC_PP=1;
    public static final int COL_CANT_PP=2;
    public static final int COL_PORC_DCTO_PP=3;
    public static final int COL_VAL_FRAC_PP=4;
    public static final int COL_ACCION_PP=5;
    
    public static final String ACTIVO="A";
    
    
           
    public static final FarmaColumnData columnsListaFracciones[] = {
               new FarmaColumnData( "UM", 0, JLabel.CENTER ),
               new FarmaColumnData( "Abreviatura", 100, JLabel.CENTER ),
               new FarmaColumnData( "Precio S/", 100, JLabel.CENTER ),
               new FarmaColumnData( "ValFracLocal", 0, JLabel.LEFT )
             };
           
           public static final Object[] defaultValuesListaFracciones= {" "," ", " ", " "};  
           
           
    
}
