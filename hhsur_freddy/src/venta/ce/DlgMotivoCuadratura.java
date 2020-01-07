package venta.ce;

import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;

import java.awt.event.KeyEvent;

import java.sql.*;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import common.*;

import venta.caja.reference.*;
import venta.ce.DlgCierreDia.*;
import venta.ce.reference.*;
import venta.ce.reference.DBCajaElectronica.*;
import venta.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgCierreDia.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      24.08.2006   Creaci�n<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class DlgMotivoCuadratura extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMotivoCuadratura.class); 

    private Frame myParentFrame;
    private FarmaTableModel tableModelFormasPago;
    private FarmaTableModel tableModelCuadraturas;
    private FarmaTableModel tableModelEfectivoRecaudado;
    private FarmaTableModel tableModelEfectivoRendido;

    private ArrayList myArray = new ArrayList();
    private ArrayList arrayDesfasados = new ArrayList();
    private ArrayList myArrayV = new ArrayList();
    private ArrayList arrayVirtuales = new ArrayList();

    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlMotivo = new JPanelTitle();
    private JTextArea txtMotivo = new JTextArea();
    private JButtonLabel btnObs = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMotivoCuadratura() {
        this(null, "", false);
    }

    public DlgMotivoCuadratura(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(769, 135));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Ingreso de Motivo Cuadratura");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        lblF11.setBounds(new Rectangle(540, 80, 95, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblEsc.setBounds(new Rectangle(660, 80, 85, 20));
        lblEsc.setText("[ ESC ] Salir");
        pnlMotivo.setBounds(new Rectangle(10, 10, 735, 60));
        pnlMotivo.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        txtMotivo.setDocument(new FarmaLengthText(290));
        txtMotivo.setRows(2);
        txtMotivo.setSelectionEnd(200);
        txtMotivo.setSelectionStart(200);
        txtMotivo.setTabSize(20);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);

        txtMotivo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMotivo_keyPressed(e);
                    }
                });
        btnObs.setText("Motivo:");
        btnObs.setMnemonic('b');
        btnObs.setBounds(new Rectangle(5, 20, 55, 25));
        
        jScrollPane1.setBounds(new Rectangle(55, 5, 675, 45));
        jScrollPane1.getViewport().add(txtMotivo, null);
        pnlMotivo.add(jScrollPane1, null);
        pnlMotivo.add(btnObs, null);
        jContentPane.add(pnlMotivo, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMotivo);
    }

    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (venta.reference.UtilityPtoVenta.verificaVK_F11(e))
            funcion11();
        else    
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
    }

  
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void funcion11(){
        String motivo =  txtMotivo.getText().trim();
        if(motivo.trim().length()>0){
            VariablesCajaElectronica.vMotivoCuadratura = motivo;
            cerrarVentana(true);
        }else
        FarmaUtility.showMessage(this,"Debe de ingresar un motivo." +
                                       "\nNo ser� posible realizar esta operaci�n.",
                                 txtMotivo);
            
    }


    private void txtMotivo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
