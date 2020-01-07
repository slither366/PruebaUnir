package venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesVirtual;
import venta.convenio.reference.DBConvenio;
import venta.convenio.reference.VariablesConvenio;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import componentes.gs.componentes.JPanelWhite;

import venta.reference.DBPtoVenta;
import venta.reference.VariablesPtoVenta;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgProcesarRevertir.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JCHAVEZ      21.12.2009   Creaci�n<br>
 * ASOSA        12.01.2010   Modificaci�n<br>
 * <br>
 * @author Jenny Ch�vez<br>
 * @version 1.0<br>
 *
 */
public class DlgProcesarRevertir extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarRevertir.class);

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
  
    public DlgProcesarRevertir(Frame parent, String title, boolean modal)
    {
	super(parent, title, modal);
	myParentFrame = parent;
	
	try
	{   FarmaVariables.vAceptar=false;  
            jbInit();
	}
	catch (Exception e)
	{   log.error("",e);
	}
    }

    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(238, 104));
        this.getContentPane().setLayout(null);
        this.setTitle("Procesando Informaci�n . . .");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {   this_windowOpened(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    void this_windowOpened(WindowEvent e)
    { 
        FarmaUtility.centrarVentana(this);
        procesar();
    }

    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
  
    /**
     * Valida que halla conexion con Matriz
     * @author ASOSA
     * Fecha: 12.01.2010
     * @return boolean
     */
    public String validarConexionMatriz()
    {
        String vIndLineaMatriz = "N";
        try
        {   FarmaConnectionRemoto.closeConnection();            
            vIndLineaMatriz=FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            return vIndLineaMatriz;
        }
        catch(Exception e)
        {   FarmaUtility.liberarTransaccion();
            log.error("",e);
        }
        return vIndLineaMatriz;
    }
  
    private void procesar()
    {
        try
        {   DBPtoVenta.reviertePruebasEnLocalNuevo(validarConexionMatriz()); 
            DBPtoVenta.actualizaIndicadorRevertir();
            FarmaUtility.aceptarTransaccion();
            cerrarVentana(true);
        }
        catch(SQLException sql)
        {   if(sql.getErrorCode()==20000)
            {   FarmaUtility.liberarTransaccion();
                log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurri� un error al revertir las pruebas. \n"+ sql.getMessage(), null);
                cerrarVentana(false);
            }
            else
            {   FarmaUtility.liberarTransaccion();
                log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurri� un error al revertir las pruebas. \n"+ sql.getMessage(), null);
                cerrarVentana(false);
            }
        }
    }
}