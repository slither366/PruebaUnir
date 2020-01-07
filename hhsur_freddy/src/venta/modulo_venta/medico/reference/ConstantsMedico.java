package venta.modulo_venta.medico.reference;

import javax.swing.JLabel;

import common.FarmaColumnData;

public class ConstantsMedico {
    public ConstantsMedico() {
        super();
    }
    
    public static final FarmaColumnData columnsListaMedicos[] =
    { new FarmaColumnData("CMP", 60, JLabel.CENTER), 
      new FarmaColumnData("Nombre Completo", 320, JLabel.LEFT),
      new FarmaColumnData("Referencia", 320, JLabel.LEFT),
      new FarmaColumnData("idReferencia", 0, JLabel.LEFT),
      new FarmaColumnData("NOM", 0, JLabel.LEFT),
      new FarmaColumnData("PAT", 0, JLabel.LEFT),
      new FarmaColumnData("MAT", 0, JLabel.LEFT)//,
      //new FarmaColumnData("Visitador Asociado", 0, JLabel.LEFT),
      //new FarmaColumnData("codVisitador", 0, JLabel.LEFT)
    
      };
    public static final Object[] defaultValuesListaMedicos = { " ", " "," ", " "," "," "
                                                               //," "," "
                                                               };

    public static final FarmaColumnData columnsListaTecnologos[] =
    { new FarmaColumnData("DNI", 60, JLabel.CENTER), 
      new FarmaColumnData("Nombre Completo", 320, JLabel.LEFT)};
    public static final Object[] defaultValuesListaTecnologos = { " ", " "};

    public static final FarmaColumnData columnsListaVisitador[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), 
      new FarmaColumnData("Nombre Completo", 320, JLabel.LEFT)};
    public static final Object[] defaultValuesListaVisitador = { " ", " "};
}
