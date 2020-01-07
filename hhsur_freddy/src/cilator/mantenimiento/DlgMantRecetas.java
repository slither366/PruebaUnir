package cilator.mantenimiento;

import cilator.mantenimiento.reference.AdministracionReceta;
import cilator.mantenimiento.reference.ContantsMantenimiento;

import cilator.mantenimiento.reference.DBMantenimiento;

import cilator.mantenimiento.reference.Receta;

import cilator.mantenimiento.reference.VariablesRecetas;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DlgMantRecetas extends JDialog {    
    
    private Frame myParentFrame;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel jblTitulo = new JButtonLabel();
    private JScrollPane scrListaRecetas = new JScrollPane();    
    private FarmaTableModel tableModelListaRecetas;
    private JTable tblListaRecetas = new JTable();
    
    private JLabelFunction lblEditar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();   

    public DlgMantRecetas() {
        this(null, "", false);
    }

    public DlgMantRecetas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ********************************************************************** */
    /*                            METODO JBINIT                               */
    /* ********************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(610, 310));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Kit");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened();
            }
            public void windowClosing(WindowEvent e) {
                this_windowClosing();
            }
        });
        
        pnlTitulo.setBounds(new Rectangle(10, 10, 570, 25));
        scrListaRecetas.setBounds(new Rectangle(10, 35, 570, 200));
        lblEditar.setText("[F3] Editar kit");
        lblEditar.setBounds(new Rectangle(10, 240, 105, 20));
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(470, 240, 90, 20));

        jblTitulo.setText("Listado de productos finales");
        jblTitulo.setBounds(new Rectangle(10, 5, 195, 15));
        jblTitulo.setMnemonic('L');
        jblTitulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed();
            }
        });
        jblTitulo.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            tblListaRecetas_keyPressed(e);
          }
        });
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEditar, null);
        scrListaRecetas.getViewport().add(tblListaRecetas, null);
        tblListaRecetas.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            tblListaRecetas_keyPressed(e);
          }
        });
        jContentPane.add(scrListaRecetas, null);
        pnlTitulo.add(jblTitulo, null);
        jContentPane.add(pnlTitulo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableRecetasLista();
        FarmaUtility.moveFocusJTable(tblListaRecetas);
    }

    /* ********************************************************************** */
    /*                            METODO DE INICIALIZACION                    */
    /* ********************************************************************** */

    private void initTableRecetasLista() {
        tableModelListaRecetas =
                new FarmaTableModel(ContantsMantenimiento.columnsListaRecetas, ContantsMantenimiento.defaultValuesListaRecetas,
                                    0);
        FarmaUtility.initSimpleList(tblListaRecetas, tableModelListaRecetas,
                                    ContantsMantenimiento.columnsListaRecetas);
        cargaListaRecetas();
    }

    private void cargaListaRecetas() {
        try {
            DBMantenimiento.listaRecetas(tableModelListaRecetas);
            FarmaUtility.ordenar(tblListaRecetas, tableModelListaRecetas, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.showMessage(this, e.getMessage(), null); // agregar algo aqui
        }
    }

    /* ********************************************************************** */
    /*                            METODOS DE EVENTOS                          */
    /* ********************************************************************** */

    private void tblListaRecetas_keyPressed(KeyEvent e){
      chkKeyPressed(e);
    }
    
    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */


    private void this_windowOpened() {
        cargaFechaSistema();
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing() {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaRecetas, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_F3) {            
            editarProducto();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    
    private Receta obtenerRecetaSeleccionada(){
        int indice = tblListaRecetas.getSelectedRow();       
        String codigoProducto=tblListaRecetas.getValueAt(indice,0).toString();
        String descripcionProducto=tblListaRecetas.getValueAt(indice,1).toString();
        return new Receta(codigoProducto, descripcionProducto);        
    }
    
    private void editarProducto(){
        limpiarSeleccionProducto();
        int indice = tblListaRecetas.getSelectedRow();
        if(indice==-1){
            FarmaUtility.showMessage(this, "Debe seleccionar una kit.", null);
            return;
        }
        Receta r = obtenerRecetaSeleccionada();
        VariablesRecetas.admReceta = new AdministracionReceta(r);
        DlgMantRecetasDetalle dlgdetalle=new DlgMantRecetasDetalle(myParentFrame,"",true);
        dlgdetalle.setVisible(true);         
        if(FarmaVariables.vAceptar){     
            VariablesRecetas.admReceta = null;
            cargaListaRecetas();
            FarmaUtility.showMessage(this, "Componentes guardados satisfactoriamente", null);            
            FarmaUtility.moveFocus(jblTitulo);
        }
    }
    
    private void limpiarSeleccionProducto(){
        VariablesRecetas.admReceta = null;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void jButtonLabel1_actionPerformed() {
        FarmaUtility.moveFocus(tblListaRecetas);
    }
    
    private void cargaFechaSistema() {
        try {
            VariablesRecetas.fechaSys = FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1), "dd/MM/yyyy");
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    /* ********************************************************************** */
    /*                    METODOS DE LOGICA DE NEGOCIO                         */
    /* *********************************************************************** */    
}

