package consorcio.admPetitorio.reference;

import common.FarmaColumnData;

import javax.swing.JLabel;

public class ConstantsPetitorio {
    public ConstantsPetitorio() {
        super();
    }
    
    public static final FarmaColumnData columnsListaPrincipiosActivos[] = {
              new FarmaColumnData( "Codigo", 0, JLabel.LEFT ),
              new FarmaColumnData( "Principio Activo", 270, JLabel.LEFT )
          };
    
    public static final Object[] defaultValuesListaPrincipiosActivos = {" ", " "};
    
    
    public static final FarmaColumnData columnsListaProductos[] = {
        new FarmaColumnData( "Sel.", 0, JLabel.LEFT ),//Dflores: 0      0
        new FarmaColumnData( "Codigo", 0, JLabel.LEFT ),//Dflores: 0    1
        new FarmaColumnData( "Descripcion", 330, JLabel.LEFT ),         //2
        new FarmaColumnData( "Unidad Entera", 130, JLabel.LEFT ),       //3
        new FarmaColumnData( "Unidad Fraccion", 130, JLabel.LEFT ),     //4
        new FarmaColumnData( "Laboratorio", 220, JLabel.LEFT),          //5
        new FarmaColumnData( "Desc.Genérica", 350, JLabel.LEFT),        //6
        new FarmaColumnData( "Cant x Turno", 100, JLabel.LEFT)          //7
    };
    
    public static final Object[] defaultValuesListaProductos = {" "," ", " ", " ", " ", " ", " "};

    // lista principal de petitorio
    public static final FarmaColumnData columnsListaPetitoriosMedico[] = {
        new FarmaColumnData( "CMP", 90, JLabel.LEFT ),                  //0
        new FarmaColumnData( "Nombre Médico", 300, JLabel.LEFT ),       //1
        new FarmaColumnData( "codPetitorio", 0, JLabel.LEFT ),          //2
        new FarmaColumnData( "Nombre Petitorio", 250, JLabel.LEFT ),    //3
        new FarmaColumnData( "Estado", 50, JLabel.CENTER ),             //4
        new FarmaColumnData( "Fecha Creación", 140, JLabel.RIGHT ),     //5
        new FarmaColumnData( "N° Turnos Semana", 100, JLabel.RIGHT ),   //6
        new FarmaColumnData( "Adj.Otros", 0, JLabel.RIGHT ),          //7
        new FarmaColumnData( "Laboratorios", 0, JLabel.RIGHT )        //8
    };
    
    public static final Object[] defaultValuesListaPetiroriosMedico = {" ", " ", " ", " ", " ", " ", " ", " ", " "};

    


}
