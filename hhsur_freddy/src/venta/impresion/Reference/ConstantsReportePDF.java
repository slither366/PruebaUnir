package venta.impresion.Reference;

import common.FarmaColumnData;

import javax.swing.JLabel;

public class ConstantsReportePDF {
    public ConstantsReportePDF() {
    }
    
    public static String rutaPDF = "";

    public static final FarmaColumnData columnsListaDatosEmp[] =
    { new FarmaColumnData("EMPRESA", 0, JLabel.CENTER), 
      new FarmaColumnData("RUC", 0, JLabel.CENTER),
      new FarmaColumnData("DIRECCION", 0, JLabel.CENTER), 
      new FarmaColumnData("TELEFONO", 0, JLabel.CENTER)};

    public static final Object[] defaultValuesListaDatosEmp = { " ", " ", " ", " " };
    
    public static final FarmaColumnData columnsListaDatosCab[] =
    { new FarmaColumnData("NRO_DOCUMENTO", 0, JLabel.CENTER), 
      new FarmaColumnData("DATOS_CLIENTE", 0, JLabel.CENTER),
      new FarmaColumnData("RUC_CLIENTE", 0, JLabel.CENTER), 
      new FarmaColumnData("VENDEDOR", 0, JLabel.CENTER),
      new FarmaColumnData("MONEDA", 0, JLabel.CENTER),
      new FarmaColumnData("VALOR_VENTA", 0, JLabel.LEFT), 
      new FarmaColumnData("IGV", 0, JLabel.LEFT),
      new FarmaColumnData("NETO", 0, JLabel.LEFT),
      new FarmaColumnData("FECHA", 0, JLabel.LEFT)};

    public static final Object[] defaultValuesListaDatosCab = { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaDatosDet[] =
    { new FarmaColumnData("ITEM", 0, JLabel.CENTER), 
      new FarmaColumnData("CODIGO", 0, JLabel.CENTER),
      new FarmaColumnData("DESCRIPCION", 0, JLabel.CENTER), 
      new FarmaColumnData("UNIDAD", 0, JLabel.CENTER),
      new FarmaColumnData("CANTIDAD", 0, JLabel.LEFT), 
      new FarmaColumnData("P_UNITARIO", 0, JLabel.LEFT),
      new FarmaColumnData("SUBTOTAL", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaDatosDet = { " ", " ", " ", " ", " ", " ", " " };
}
