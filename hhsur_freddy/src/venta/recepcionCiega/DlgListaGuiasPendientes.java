package venta.recepcionCiega;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.recepcionCiega.reference.ConstantsRecepCiega;
import venta.recepcionCiega.reference.DBRecepCiega;
import venta.recepcionCiega.reference.VariablesRecepCiega;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import venta.recepcionCiega.reference.ConstantsRecepCiega;
import venta.recepcionCiega.reference.DBRecepCiega;
import venta.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaGuiasPendientes extends JDialog {
    
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgListaGuiasPendientes.class);

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel btnRelacionGuiasPendientes = new JButtonLabel();
    private JPanelHeader pnlObservacion = new JPanelHeader();
    private JLabelWhite lblObservacion1 = new JLabelWhite();
    private JLabelWhite lblObservacion2 = new JLabelWhite();
    private JScrollPane srcListaGuias = new JScrollPane();
    private JTable tblListaGuias = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();


    // **************************************************************************
    // Constructores
    // **************************************************************************
    public DlgListaGuiasPendientes() {
        this(null, "", false);     
    }

    public DlgListaGuiasPendientes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
               jbInit();
               initialize();
               FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
               log.error("",e);
        }

    }
  
    private void jbInit() throws Exception {
        
        this.setSize(new Dimension(442, 300));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Guias  Pendientes");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }

                public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                }
        });
        
        pnlTitle.setBounds(new Rectangle(10, 50, 415, 25));
        btnRelacionGuiasPendientes.setText("Relacion de Guias Pendientes");
        btnRelacionGuiasPendientes.setBounds(new Rectangle(10, 5, 285, 15));
        btnRelacionGuiasPendientes.setMnemonic('R');
        btnRelacionGuiasPendientes.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionGuiasPendientes_actionPerformed(e);
                    }
                });
        pnlObservacion.setBounds(new Rectangle(10, 10, 415, 40));
        lblObservacion1.setText("Los productos de las siguientes gu�as no han si contadas, por lo tanto");
        lblObservacion1.setBounds(new Rectangle(15, 5, 395, 15));
        lblObservacion2.setText("ser�n eliminadas de la recepci�n");
        lblObservacion2.setBounds(new Rectangle(15, 20, 325, 15));
        srcListaGuias.setBounds(new Rectangle(10, 75, 415, 160));
        tblListaGuias.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaGuias_keyPressed(e);
                    }
                });
        lblEsc.setBounds(new Rectangle(310, 245, 117, 19));
        lblEsc.setText("[ Esc ] Salir ");
        pnlObservacion.add(lblObservacion2, null);
        pnlObservacion.add(lblObservacion1, null);
        srcListaGuias.getViewport().add(tblListaGuias, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(srcListaGuias, null);
        jContentPane.add(pnlObservacion, null);
        pnlTitle.add(btnRelacionGuiasPendientes, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************
    private void initialize() {
        FarmaVariables.vAceptar = false;
            initTable();
    }
    
    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************
    private void initTable() {
            tableModel = new FarmaTableModel(
                            ConstantsRecepCiega.columnsListaGuiasPendientes,
                            ConstantsRecepCiega.defaultcolumnsListaGuiasPendientes, 0);
            FarmaUtility.initSimpleList(tblListaGuias, tableModel,
                            ConstantsRecepCiega.columnsListaGuiasPendientes);
            cargaListaGuias();
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowOpened(WindowEvent e) {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(this.tblListaGuias);
    }
    private void this_windowClosing(WindowEvent e) {
            FarmaUtility.showMessage(this,
                            "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    private void tblListaGuias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
       
    private void chkKeyPressed(KeyEvent e) {    
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }
    
    private void btnRelacionGuiasPendientes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.tblListaGuias);
    }
    
    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************
    public void cargaListaGuias(){
        try {
                DBRecepCiega.getListaGuiasPendientes(tableModel);
                if (tblListaGuias.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblListaGuias, tableModel, 1,FarmaConstants.ORDEN_ASCENDENTE);
       
                }
        } catch (SQLException sql) {
            log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurri� un error al cargar la lista de gu�as : \n",null);   
        }
    }

    private void cerrarVentana(boolean pAceptar) {
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
    }


}
