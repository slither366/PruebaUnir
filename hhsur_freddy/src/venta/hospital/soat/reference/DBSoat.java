package venta.hospital.soat.reference;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.StringTokenizer;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBSoat {
    
    private static final Logger log = LoggerFactory.getLogger(DBSoat.class);
    
	public DBSoat() {
	}

	private static ArrayList parametros = new ArrayList();

	public static void getListaAtenciones(FarmaTableModel pTableModel)
			throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);
                //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
		//"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
		pTableModel.clearTable();
		ArrayList myArray = null;
		String myRow = "";
	    StringTokenizer st ;
                myRow = "00010�RIMAC�44324600�DIEGO UBILLUZ�10/08/2015 10:10 AM�ERAMIREZ�ESPERA";
		myArray = new ArrayList();
                st = new StringTokenizer(myRow, "�");
                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }
                pTableModel.insertRow(myArray);

	    myRow = "00020�PACIFICO�915100000�MICHAEL SMITH�10/08/2015 08:00 AM�ERAMIREZ�POR LIQUIDAR";
	    myArray = new ArrayList();
	    st = new StringTokenizer(myRow, "�");
	    while (st.hasMoreTokens()) {
	        myArray.add(st.nextToken());
	    }
	    pTableModel.insertRow(myArray);
		    
		
	}

    public static void getListaRegistroAtenciones(FarmaTableModel pTableModel)
                    throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
            //"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
            pTableModel.clearTable();
            ArrayList myArray = null;
            String myRow = "";
        StringTokenizer st ;
            myRow = "00410�RIMAC�44324600�CARLOS CORDOVA�10/08/2015 10:10 AM�DUBILLUZ�ATENDIDO";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "05020�RIMAC�88551120�JUAN QUISPE�11/08/2015 03:50 PM�DUBILLUZ�ATENDIDO";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);
                
            
    }

    public static void getListaLiquidaciones(FarmaTableModel pTableModel)
                    throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
            //"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
            pTableModel.clearTable();
            ArrayList myArray = null;
            String myRow = "";
        StringTokenizer st ;
            myRow = "00001�01/08/2015�15/08/2015�RIMAC�20/08/2015 10:10 AM�AZAVALA�EMITIDO�10�16,500";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "00002�01/07/2015�20/07/2015�PACIFICO�05/08/2015 03:50 PM�AZAVALA�CANCELADO�5�1,200";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);


        myRow = "00003�01/07/2015�20/07/2015�RIMAC�09/08/2015 06:00 PM�AZAVALA�PROCESADOO�5�36,150";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);
                
            
    }


    public static void getListaAtencionesLiquidar(FarmaTableModel pTableModel)
                    throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
            //"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
            pTableModel.clearTable();
            ArrayList myArray = null;
            String myRow = "";
        StringTokenizer st ;
            myRow = "0�00010�PACIFICO�44324600�DIEGO UBILLUZ�12/08/2015 10:10 AM�ERAMIREZ�POR LIQUIDAR";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "1�00020�PACIFICO�915100000�MICHAEL SMITH�10/08/2015 08:00 AM�DUBILLUZ�POR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);

        myRow = "1�00021�PACIFICO�915100000�JUAN QUISPE�10/08/2015 08:00 AM�DUBILLUZ�POR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);


        myRow = "0�00023�PACIFICO�915100000�ALFREDO SOSA�10/08/2015 08:00 AM�DUBILLUZ�POR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "�");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);
                
            
    }

}
