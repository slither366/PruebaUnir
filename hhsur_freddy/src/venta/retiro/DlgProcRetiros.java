package venta.retiro;

import componentes.gs.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.awt.event.WindowEvent;

import common.FarmaUtility;
import venta.retiro.reference.DBRetiro;
import venta.retiro.reference.VariablesRetiro;
import java.awt.Rectangle;

import javax.swing.JDialog;

import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgProcRetiros <br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * ASOSA 07.12.2009 Creaci�n<br>
 * <br>
 * @author Alfredo Sosa Dord�n<br>
 * @version 1.0<br>
 *
 */

public class DlgProcRetiros extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProcRetiros.class);

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    /* ********************************************************************** */
          /*                        CONSTRUCTORES                                   */
          /* ********************************************************************** */

    public DlgProcRetiros() {
        this(null, "", false);
    }

    public DlgProcRetiros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /* ************************************************************************ */
          /*                                  METODO jbInit                           */
          /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(235, 103));
        this.getContentPane().setLayout( null );
        this.setTitle("Procesando Informaci�n ...");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 230, 80));
        this.getContentPane().add(jPanelWhite1, null);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
      /*                            METODOS DE EVENTOS                            */
      /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e)  {
       procesarRetiros();
    }
    
    public void procesarRetiros(){
        try{
            DBRetiro.procesarRetiros();
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Proceso finalizado exitosamente",null);
            cerrarVentana(true);            
        }catch(SQLException f)   {
            log.error("",f);
            FarmaUtility.showMessage(this,"ERROR en el proceso de retiro de VB: \n"+f.getMessage(),null);
            FarmaUtility.liberarTransaccion();
            cerrarVentana(false);
        }
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    
}
