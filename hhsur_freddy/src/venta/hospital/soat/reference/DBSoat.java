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
                myRow = "00010ÃRIMACÃ44324600ÃDIEGO UBILLUZÃ10/08/2015 10:10 AMÃERAMIREZÃESPERA";
		myArray = new ArrayList();
                st = new StringTokenizer(myRow, "Ã");
                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }
                pTableModel.insertRow(myArray);

	    myRow = "00020ÃPACIFICOÃ915100000ÃMICHAEL SMITHÃ10/08/2015 08:00 AMÃERAMIREZÃPOR LIQUIDAR";
	    myArray = new ArrayList();
	    st = new StringTokenizer(myRow, "Ã");
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
            myRow = "00410ÃRIMACÃ44324600ÃCARLOS CORDOVAÃ10/08/2015 10:10 AMÃDUBILLUZÃATENDIDO";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "Ã");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "05020ÃRIMACÃ88551120ÃJUAN QUISPEÃ11/08/2015 03:50 PMÃDUBILLUZÃATENDIDO";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
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
            myRow = "00001Ã01/08/2015Ã15/08/2015ÃRIMACÃ20/08/2015 10:10 AMÃAZAVALAÃEMITIDOÃ10Ã16,500";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "Ã");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "00002Ã01/07/2015Ã20/07/2015ÃPACIFICOÃ05/08/2015 03:50 PMÃAZAVALAÃCANCELADOÃ5Ã1,200";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);


        myRow = "00003Ã01/07/2015Ã20/07/2015ÃRIMACÃ09/08/2015 06:00 PMÃAZAVALAÃPROCESADOOÃ5Ã36,150";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
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
            myRow = "0Ã00010ÃPACIFICOÃ44324600ÃDIEGO UBILLUZÃ12/08/2015 10:10 AMÃERAMIREZÃPOR LIQUIDAR";
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "Ã");
            while (st.hasMoreTokens()) {
                myArray.add(st.nextToken());
            }
            pTableModel.insertRow(myArray);

        myRow = "1Ã00020ÃPACIFICOÃ915100000ÃMICHAEL SMITHÃ10/08/2015 08:00 AMÃDUBILLUZÃPOR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);

        myRow = "1Ã00021ÃPACIFICOÃ915100000ÃJUAN QUISPEÃ10/08/2015 08:00 AMÃDUBILLUZÃPOR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);


        myRow = "0Ã00023ÃPACIFICOÃ915100000ÃALFREDO SOSAÃ10/08/2015 08:00 AMÃDUBILLUZÃPOR LIQUIDAR";
        myArray = new ArrayList();
        st = new StringTokenizer(myRow, "Ã");
        while (st.hasMoreTokens()) {
            myArray.add(st.nextToken());
        }
        pTableModel.insertRow(myArray);
                
            
    }

}
