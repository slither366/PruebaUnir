package con_general;


import common.FarmaConnectionRemoto;
import common.FarmaConstants;

import common.FarmaTableModel;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.StringTokenizer;

import oracle.jdbc.OracleTypes;


//import javax.swing.*;


public class FarmaDBUtilityGeneral {

    //private static ArrayList parametros = new ArrayList();


    //Variable que almacenara las cadena de la coneccion seleccionada
    // public static String cadena_conection = "";
    FarmaConnectionGeneral con = null;
    String pUsuBd ,pClaveBd,pIpServBd,pPuertoBD,pSidBd;
    
    //UsuDB,ClaveDB,IpDB,PuertoDB,SidDB
    public FarmaDBUtilityGeneral(String pUsuarioBD,
                                  String pClaveBD,
                                  String pIpServBD,
                                  String pPuertoBD,
                                  String pSidBD){
        
        con = new FarmaConnectionGeneral(pUsuarioBD,pClaveBD,pIpServBD,pPuertoBD,pSidBD);
        
        this.pUsuBd = pUsuarioBD;
        this.pClaveBd = pClaveBD;
        this.pIpServBd = pIpServBD;
        this.pPuertoBD = pPuertoBD;
        this.pSidBd = pSidBD;
        
    }
    
    
    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * M�todo usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin par�metros y estos retornan un objeto del tipo OracleTypes.CURSOR
     * 2. Ejecutar Stored Procedures con o sin par�metros que no retornan nada,
     * s�lo son ejecutados en el Servidor de Base de Datos Se asume que cuando
     * el par�metro pTableModel es nulo se trata de un procedimiento que no
     * retornar� nada.
     *
     * @param pTableModel
     *             Table Model usado con el JTable como modelo.
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (par�metros) usados en el Stored
     *            Procedure.
     * @param pWithCheck
     *             Variable boolean que indica si en el JTable existir� una
     *            columna de Selecci�n.
     */
    public void executeSQLStoredProcedure(FarmaTableModel pTableModel, 
                                                 String pStoredProcedure, 
                                                 ArrayList pParameters, 
                                                 boolean pWithCheck,
                                                 String pIndCloseConecction) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;
        //Cargando la Coneccion Seleccionada
        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);
        ///////
        //con = new FarmaConnectionGeneral(pUsuBd ,pClaveBd,pIpServBd,pPuertoBD,pSidBd);
        
        if (pTableModel != null) {
            stmt = 
((Connection)con.getConnection()).prepareCall("{ call ? := " + 
                                              pStoredProcedure + " }");
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
        } else {
            numeroParametro = 1;
            stmt = 
((Connection)con.getConnection()).prepareCall("{ call " + pStoredProcedure + 
                                              " }");
        }
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como par�metro.
        //System.out.println(pParameters);
        for (int i = 0; i < pParameters.size(); i++) {
            //System.out.println("String +"+pParameters.get(i));
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            //System.out.println("integer +");
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            
            //System.out.println("do +");
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        
        /*
        if(((Connection)con.getConnection()).isClosed())
            con = new FarmaConnectionGeneral(pUsuBd ,pClaveBd,pIpServBd,pPuertoBD,pSidBd);
        */
        stmt.execute();
        if (pTableModel != null) {
            // Carga la Data en el TableModel para ser usado como modelo para el
            // JTable.
            // El OracleTypes.CURSOR obtenido es "depurado" para obtener cada
            // una de las
            // columnas ... cada registro viene como un String en el que los
            // campos est�n
            // concatenados por el character "�".
            pTableModel.clearTable();
            ResultSet results = (ResultSet)stmt.getObject(1);
            ArrayList myArray = null;
            String myRow = "";
            StringTokenizer st = null;
            pTableModel.clearTable();
            while (results.next()) {
                myRow = results.getString(1);
                myArray = new ArrayList();
                st = new StringTokenizer(myRow, "�");
                if (pWithCheck)
                    myArray.add(new Boolean(false));
                while (st.hasMoreTokens()) {
                    myArray.add(st.nextToken());
                }
                pTableModel.insertRow(myArray);
            }
            results.close();
            if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            {
                results = null;
            }
        }
        stmt.close();
        
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * M�todo usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin par�metros y estos retornan un objeto del tipo OracleTypes.CHAR
     *
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (par�metros) usados en el Stored
     *            Procedure.
     * @return String Objeto String devuelto por la ejecuci�n del Stored
     *         Procedure.
     */
    public  String executeSQLStoredProcedureStr(String pStoredProcedure, 
                                                      ArrayList pParameters,
                                                      String pIndCloseConecction) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        String valorRetorno = null;
        int numeroParametro = 2;

        //System.out.println("Pool de Conexiones >>" + Pool_Conexiones);

        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //System.out.println("La elegida "+cadena_conection );
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);

        CallableStatement stmt = 
            ((Connection)con.getConnection()).prepareCall("{ call ? := " + 
                                                          pStoredProcedure + 
                                                          " }");
        stmt.registerOutParameter(1, OracleTypes.CHAR);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        valorRetorno = (String)stmt.getObject(1);
        stmt.close();
                
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }
        
        return valorRetorno;
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * M�todo usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin par�metros y estos retornan un objeto del tipo OracleTypes.INTEGER
     *
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (par�metros) usados en el Stored
     *            Procedure.
     * @return int integer devuelto por la ejecuci�n del Stored
     *         Procedure.
     */
    public  int executeSQLStoredProcedureInt(String pStoredProcedure, 
                                                   ArrayList pParameters,
                                                   String pIndCloseConecction) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int valorRetorno = 0;
        int numeroParametro = 2;

        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);

        CallableStatement stmt = 
            ((Connection)con.getConnection()).prepareCall("{ call ? := " + 
                                                          pStoredProcedure + 
                                                          " }");
        stmt.registerOutParameter(1, OracleTypes.INTEGER);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        Object obj = stmt.getObject(1);
        if (obj instanceof BigDecimal)
            valorRetorno = ((BigDecimal)stmt.getObject(1)).intValue();
        else if (obj instanceof Integer)
            valorRetorno = ((Integer)stmt.getObject(1)).intValue();
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }        

        return valorRetorno;
    }

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * M�todo usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin par�metros y estos retornan un objeto del tipo OracleTypes.DOUBLE
     *
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (par�metros) usados en el Stored
     *            Procedure.
     * @return double double devuelto por la ejecuci�n del Stored
     *         Procedure.
     */
    public  double executeSQLStoredProcedureDouble(String pStoredProcedure, 
                                                         ArrayList pParameters, 
                                                         int pPosConection,
                                                         String pIndCloseConecction) throws SQLException {
        // Disparamos una excepci�n si el query que llega como par�metro es
        // nulo.
        // Esto es para evitar tener problemas con el Statement.
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        double valorRetorno = 0.00;
        int numeroParametro = 2;

        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);

        CallableStatement stmt = 
            ((Connection)con.getConnection()).prepareCall("{ call ? := " + 
                                                          pStoredProcedure + 
                                                          " }");
        stmt.registerOutParameter(1, OracleTypes.DOUBLE);
        // Setear los par�metros seg�n el tipo de Objecto almacenado en el
        // ArrayList
        // que viene como par�metro.
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        Object obj = stmt.getObject(1);
        if (obj instanceof BigDecimal)
            valorRetorno = ((BigDecimal)stmt.getObject(1)).doubleValue();
        else if (obj instanceof Double)
            valorRetorno = ((Double)stmt.getObject(1)).doubleValue();
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }        
        return valorRetorno;
    }

    /**
     * Ejecuta un Stored Procedure de la Base de Datos.
     * @param pArrayList Arreglo de Respuesta
     * @param pStoredProcedure Stored Procedure
     * @param pParameters Arreglo de Par�metros
     * @throws SQLException
     */
    public  void executeSQLStoredProcedureArrayList(ArrayList pArrayList, 
                                                          String pStoredProcedure, 
                                                          ArrayList pParameters, 
                                                          String pIndCloseConecction) throws SQLException {
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        int numeroParametro = 2;
        CallableStatement stmt;

        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);
        /*if(con==null)
            System.out.println("es nulo la conexion");
        else
            System.out.println("NO ES nulo la conexion");*/
            
        stmt = 
((Connection)con.getConnection()).prepareCall("{ call ? := " + pStoredProcedure + 
                                              " }");
        stmt.registerOutParameter(1, OracleTypes.CURSOR);
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        stmt.execute();
        ResultSet results = (ResultSet)stmt.getObject(1);
        ArrayList myArray = null;
        String myRow = "";
        StringTokenizer st = null;
        while (results.next()) {
            myRow = results.getString(1);
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens())
                myArray.add(st.nextToken());
            pArrayList.add(myArray);
        }
        results.close();
        
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))        
        results = null;
        
        stmt.close();
        
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }
        
    }

   

    /**
     * Ejecuta un determinado Stored Procedure almacenado en la Base de Datos.
     * M�todo usado en los siguientes casos : 1. Ejecutar Stored Procedures con
     * o sin par�metros y estos retornan un objeto del tipo OracleTypes.CHAR
     *
     * @param pStoredProcedure
     *             Nombre del Stored Procedure ha ser ejecutado
     * @param pParameters
     *             Arreglo de Objetos (par�metros) usados en el Stored
     *            Procedure.
     * @return String Objeto String devuelto por la ejecuci�n del Stored
     *         Procedure.
     * @author Diego Ubilluz
     * @since  18.08.2008
     */
    public  String executeSQLStoredProcedureStrOut(String pStoredProcedure, 
                                                      ArrayList pParameters, 
                                                      int pPosConection,
                                                      String pIndCloseConecction) throws SQLException {
        
        if (pStoredProcedure == null || pStoredProcedure.trim().length() == 0)
            throw new SQLException("Expresion del Stored Procedure no Definido", 
                                   "FarmaError", 9002);
        String valorRetorno = null;
        int numeroParametro = 1;

        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);

        CallableStatement stmt = 
            ((Connection)con.getConnection()).prepareCall("{ call  " + 
                                                          pStoredProcedure + 
                                                          " }");
        
        for (int i = 0; i < pParameters.size(); i++) {
            if (pParameters.get(i) instanceof String)
                stmt.setString(numeroParametro, (String)pParameters.get(i));
            if (pParameters.get(i) instanceof Integer)
                stmt.setInt(numeroParametro, 
                            Integer.parseInt(((Integer)pParameters.get(i)).toString()));
            if (pParameters.get(i) instanceof Double)
                stmt.setDouble(numeroParametro, 
                               Double.parseDouble(((Double)pParameters.get(i)).toString()));
            numeroParametro += 1;
        }
        
        stmt.registerOutParameter(numeroParametro, OracleTypes.CHAR);
        
        stmt.execute();
        
        valorRetorno = (String)stmt.getObject(numeroParametro);
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
        }
        
        return valorRetorno;
    }

    /**
     * Obtiene un valor de la Base de Datos.
     * @param tableName Nombre de la Tabla
     * @param fieldCode Campo
     * @param whereCondition Condici�n
     * @return String - Valor de la Consulta.
     * @throws SQLException
     */
    public  String getValueAt(String tableName, String fieldCode, 
                                    String whereCondition, 
                                    int pPosConection,
                                    String pIndCloseConecction) throws SQLException {
        String valor = "";
        String query = 
            "SELECT " + fieldCode + " FROM " + tableName + " WHERE " + 
            whereCondition + "";
        // System.out.println("QUERY="+query);
        //cadena_conection = Pool_Conexiones.get(pPosConection).toString().trim();
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);

        Statement stmt = ((Connection)con.getConnection()).createStatement();
        ResultSet results = stmt.executeQuery(query);
        if (results.next()) {
            valor = results.getString(1);
        }
        if (results.next())
            throw new SQLException("Existe m�s de 1 registro", "FarmaError", 
                                   9000);
        results.close();
        
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))        
        results = null;
        
        stmt.close();
        
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
            
        }

        
        if (valor == null)
            valor = "";
        return valor;
    }

    /**
     * Obtiene un valor de la Base de Datos.
     * @param vQuery Sentencia para ejecutar
     * @param pPosConection  Para determinar que conexion Seleccionara
     * @return String - Valor de la Consulta.
     * @throws SQLException
     */
    public  String executeSQLQuery(String vQuery, 
                                         String pIndCloseConecction) throws SQLException {

        String valorRetorno = null;
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);
        Statement stmt = 
            ((Connection)con.getConnection()).createStatement();
        ResultSet results = stmt.executeQuery(vQuery.trim());
        while (results.next()) {
            valorRetorno = results.getString(1);
        }
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con = null;
            con.closeConnection();
        }
        
        return valorRetorno;
    }

    public  String executeSQL(String vQuery, 
                                         String pIndCloseConecction) throws SQLException {

        String valorRetorno = null;
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);
        if(con==null){
            con = new FarmaConnectionGeneral(pUsuBd,pClaveBd,pIpServBd,pPuertoBD,pSidBd);    
        }
        
        Statement stmt = 
            ((Connection)con.getConnection()).createStatement();
        System.out.println("pIpServBd>>"+pIpServBd);
        stmt.executeQuery(vQuery.trim());
        stmt.close();
        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con.closeConnection();
            con = null;
        }
        
        return valorRetorno;
    }    


    
    /**
     * Obtiene un valor de la Base de Datos.
     * @param vQuery Sentencia para ejecutar
     * @param pPosConection  Para determinar que conexion Seleccionara
     * @return String - Valor de la Consulta.
     * @throws SQLException
     */
    public  String executeSQLQueryWithTimeOut(String vQuery, 
                                         int pPosConection,
                                         String pIndCloseConecction,
                                                    int pTimeOutConection) throws SQLException {

        String valorRetorno = null;
        //FarmaConnectionRemoto con = new FarmaConnectionRemoto(pPosConection);
        Statement stmt = 
            ((Connection)FarmaConnectionRemoto.getConnection(pTimeOutConection)).createStatement();
        ResultSet results = stmt.executeQuery(vQuery.trim());
        while (results.next()) {
            valorRetorno = results.getString(1);
        }
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con = null;
            con.closeConnection();
        }
        
        return valorRetorno;
    }
    
    
    public void cerrarConexion(){
        if(con!=null){
            con.closeConnection();
            con = null;
            con = new FarmaConnectionGeneral(pUsuBd,pClaveBd,pIpServBd,pPuertoBD,pSidBd);    
        }
    }

    public  ArrayList executeSQLQueryArrayList(String vQuery, 
                                         String pIndCloseConecction) throws SQLException {

        Statement stmt = 
            ((Connection)con.getConnection()).createStatement();
        ResultSet results = stmt.executeQuery(vQuery.trim());
        ArrayList myArray = null,pArrayList=new ArrayList();
                String myRow = "";
                StringTokenizer st = null;        
        while (results.next()) {
            //valorRetorno = results.getString(1);
            myRow = results.getString(1);
            myArray = new ArrayList();
            st = new StringTokenizer(myRow, "�");
            while (st.hasMoreTokens())
                myArray.add(st.nextToken());
            pArrayList.add(myArray);            
        }
        stmt.close();

        if(pIndCloseConecction.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            stmt = null;
            con = null;
            con.closeConnection();
        }
        
        return pArrayList;
    }
}