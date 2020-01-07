package admcentral.packs.reference;

import javax.swing.JLabel;

import common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsParametros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * MHUAYTA 06.02.2006 Creación<br>
 * <br>
 *
 * @author Manuel Huayta Rojas<br>
 * @version 1.0<br>
 *
 */
public class

ConstantsParametros
{
  public ConstantsParametros()
  {
  }

  public static final FarmaColumnData columnsListaLineas[] = //grupo rep
  { new FarmaColumnData("Código", 60, JLabel.CENTER), 
    new FarmaColumnData("Descripción", 190, JLabel.LEFT), 
    new FarmaColumnData("Min", 80, JLabel.RIGHT), 
    new FarmaColumnData("Max", 80, JLabel.RIGHT), 
    new FarmaColumnData("Num. Días", 120, JLabel.RIGHT), 
    new FarmaColumnData("", 0, JLabel.RIGHT) };

  public static final Object[] defaultValuesListaLineas =
  { " ", " ", " ", " ", " ", " " };

  public static final FarmaColumnData columnsListaLocales[] = //cia

  { new FarmaColumnData("Local", 60, JLabel.CENTER), 
    new FarmaColumnData("Descripción", 190, JLabel.LEFT), 
    new FarmaColumnData("Min", 80, JLabel.RIGHT), 
    new FarmaColumnData("Max", 80, JLabel.RIGHT), 
    new FarmaColumnData("Num. Días", 120, JLabel.RIGHT), 
    new FarmaColumnData("", 0, JLabel.RIGHT) };

  public static final Object[] defaultValuesListaLocales =
  { " ", " ", " ", " ", " ", " " };

  public static final FarmaColumnData columnsListaProductos[] =
  { new FarmaColumnData("Codigo", 60, JLabel.LEFT), 
    new FarmaColumnData("Descripción", 190, JLabel.LEFT), 
    new FarmaColumnData("Min", 80, JLabel.RIGHT), 
    new FarmaColumnData("Max", 80, JLabel.RIGHT), 
    new FarmaColumnData("Num. Días", 120, JLabel.RIGHT) };

  public static final Object[] defaultValuesListaProductos =
  { " ", " ", " ", " ", " ", " " };

  // ERIOS
  public static final FarmaColumnData columnsListaProductosIndicadores[] =
  { new FarmaColumnData("Codigo", 50, JLabel.LEFT), 
    new FarmaColumnData("Descripcion", 250, JLabel.LEFT), 
    new FarmaColumnData("Unidad", 100, JLabel.LEFT), 
    new FarmaColumnData("Laboratorio", 200, JLabel.LEFT), 
    new FarmaColumnData("Estado", 60, JLabel.CENTER) };

  public static final Object[] defaultValuesListaProductosIndicadores =
  { " ", " ", " ", " ", " " };

  public static final FarmaColumnData columnsListaLocalesIndicadores[] =
  { new FarmaColumnData("Sel", 30, JLabel.CENTER), 
    new FarmaColumnData("Codigo", 50, JLabel.CENTER), 
    new FarmaColumnData("Descripcion", 200, JLabel.LEFT), 
    new FarmaColumnData("Stock", 80, JLabel.RIGHT), 
    new FarmaColumnData("Reponer", 50, JLabel.CENTER), 
    new FarmaColumnData("Cant. Exhib.", 70, JLabel.RIGHT) };

  public static final Object[] defaultValuesListaLocalesIndicadores =
  { " ", " ", " ", " ", " ", " " };
  
  
   /**
     * Variables para el Filtro de ubicacion
     * @author  JCORTEZ
     * @since   04.09.2007
     * */
      public static String NOM_HASTABLE_CMBFILTROUBI = "CMB_FILTRO_UBI";
      public static final String[] vCodColumna =
      { "%", "N", "S" };
      public static final String[] vDescColumna =
      { "Todos", "Lima", "Provincia" };
}
