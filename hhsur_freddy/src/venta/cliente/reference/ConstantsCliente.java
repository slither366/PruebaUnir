package venta.cliente.reference;

import javax.swing.JLabel;
import common.*;
import common.FarmaColumnData;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicaci�n : ConstantsCliente.java<br>
* <br>
* Hist�rico de Creaci�n/Modificaci�n<br>
* LMESIA      23.02.2006   Creaci�n<br>
* <br>
* @author Luis Mesia Rivera<br>
* @version 1.0<br>
*
*/

public class ConstantsCliente {

  public ConstantsCliente() {
  }
  
  //Lista de Clientes Juridicos
  public static final FarmaColumnData columnsListaClientesJuridicos[] = {
    new FarmaColumnData( "Tipo", 30, JLabel.CENTER ), // 0
    new FarmaColumnData( "Codigo", 0, JLabel.CENTER ),// 1
    new FarmaColumnData( "Documento", 100, JLabel.CENTER ), // 2
    new FarmaColumnData( "Cliente", 600, JLabel.LEFT ),// 3
    new FarmaColumnData( "Tel�fono", 80, JLabel.LEFT ), // 4
    new FarmaColumnData( "Correo", 0, JLabel.LEFT ),// 5
    new FarmaColumnData( "Direccion", 0, JLabel.LEFT ), //6
    new FarmaColumnData( "id_tipo_cli", 0, JLabel.LEFT )//7
    //nom//8
    //ape//9
    //ape// 10
    // tipop 11

  };	
	public static final Object[] defaultValuesListaClientesJuridicos = {" "," "," "," "," "," "," "," "," "," "," "," "};
  
  public static final FarmaColumnData columnsListaTarjetasClientes[] = {
    new FarmaColumnData( "Operador", 70, JLabel.CENTER ),
    new FarmaColumnData( "Numero", 100, JLabel.LEFT ),
    new FarmaColumnData( "Nombre Tarjeta", 150, JLabel.LEFT ),
    new FarmaColumnData( "Fecha Vencimiento", 150 , JLabel.LEFT ),
    new FarmaColumnData( "Codigo", 1 , JLabel.LEFT ),
    new FarmaColumnData( "Mes", 1, JLabel.LEFT ),
    new FarmaColumnData( "A�o", 1 , JLabel.LEFT ),
  
  };	
	public static final Object[] defaultValuesListaTarjetasClientes = {" "," "," "," "," "," "," "};
  
  public static final String TIPO_BUSQUEDA_RUC = "1";
  public static final String TIPO_BUSQUEDA_RAZSOC = "2";
  public static final String TIPO_BUSQUEDA_DNI = "3";
  public static final String TIPO_BUSQUEDA_NOMBRE = "4";
  public static final String TIPO_JURIDICO = "02";
  public static final String TIPO_NATURAL = "01";
  public static final String RESULTADO_GRABAR_CLIENTE_EXITO = "1";
  public static final String RESULTADO_GRABAR_CLIENTE_ERROR = "2";
  public static final String RESULTADO_RUC_VALIDO = "TRUE";
  public static final String RESULTADO_RUC_INVALIDO = "FALSE";
  public static final String ACCION_INSERTAR = "I";
  public static final String ACCION_MODIFICAR = "M";

}
 
