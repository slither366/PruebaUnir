package venta.hospital.soat.reference;

import javax.swing.JLabel;

import common.FarmaColumnData;

public class ConstantsSoat {
	public ConstantsSoat() {
	}

	public static final FarmaColumnData[] columnsListaAtencionSoat = {
			new FarmaColumnData("N°Atención.", 70, JLabel.CENTER),
			new FarmaColumnData("Aseguradora", 120, JLabel.LEFT),
			new FarmaColumnData("Dni", 180, JLabel.LEFT),
			new FarmaColumnData("Nombre", 120, JLabel.CENTER),
			new FarmaColumnData("Fecha Registro", 135, JLabel.CENTER),
			new FarmaColumnData("Usuario Creador", 100, JLabel.LEFT),
			new FarmaColumnData("Estado", 100, JLabel.CENTER)
	};

	public static final Object[] defaultValuesListaAtencionSoat = { " ", " "," ",
                                                                      " ", " ", " ", 
                                                                      " "};
        
        

    public static final FarmaColumnData[] columnsListaLiquidacion = {
                    new FarmaColumnData("N°Liquidacion.", 70, JLabel.CENTER),
                    new FarmaColumnData("Desde", 100, JLabel.LEFT),
                    new FarmaColumnData("Hasta", 100, JLabel.LEFT),
                    new FarmaColumnData("Empresa Aseguradora", 130, JLabel.CENTER),
                    new FarmaColumnData("Fecha Liquidacion", 135, JLabel.CENTER),
                    new FarmaColumnData("Usuario Creador", 120, JLabel.LEFT),
                    new FarmaColumnData("Estado", 100, JLabel.CENTER),
                    new FarmaColumnData("# Atenciones", 80, JLabel.LEFT),
                    new FarmaColumnData("Monto S/", 100, JLabel.RIGHT)
                    //"00001Ã01/08/2015 - 15/08/2015ÃRIMACÃ20/08/2015 10:10 AMÃAZAVALAÃEMITIDOÃ10Ã1,500"
    };

    public static final Object[] defaultValuesListaLiquidacion = { " ", " "," ",
                                                                  " ", " ", " ",
                                                                  " ", " ", " "};
    
    public static final FarmaColumnData[] columnsListaAtencionSoatLiq = {
                    new FarmaColumnData("Sel.", 25, JLabel.CENTER),
                    new FarmaColumnData("N°Atención.", 70, JLabel.CENTER),
                    new FarmaColumnData("Aseguradora", 120, JLabel.LEFT),
                    new FarmaColumnData("Dni", 80, JLabel.LEFT),
                    new FarmaColumnData("Nombre", 150, JLabel.CENTER),
                    new FarmaColumnData("Fecha Registro", 135, JLabel.CENTER),
                    new FarmaColumnData("Usuario Creador", 100, JLabel.LEFT),
                    new FarmaColumnData("Estado", 100, JLabel.CENTER)
    };

    public static final Object[] defaultValuesListaAtencionSoatLiq = { " "," ", " "," ",
                                                                  " ", " ", " ", 
                                                                  " "};

}