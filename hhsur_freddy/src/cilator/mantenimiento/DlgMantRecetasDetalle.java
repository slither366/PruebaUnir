package cilator.mantenimiento;

import cilator.mantenimiento.reference.ComponenteReceta;
import cilator.mantenimiento.reference.ConstantsRecetas;
import cilator.mantenimiento.reference.DBMantenimiento;
import cilator.mantenimiento.reference.ModoOperacionRecetaComponente;

import cilator.mantenimiento.reference.Receta;
import cilator.mantenimiento.reference.VariablesRecetas;

import common.FarmaConstants;
import common.FarmaSearch;
import common.FarmaTableModel;

import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DlgMantRecetasDetalle extends JDialog {    
    private static final Log log = LogFactory.getLog(DlgMantRecetasDetalle.class);
    private Frame myParentFrame;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlTituloComponentes = new JPanelTitle();
    private JButtonLabel jblTituloComponentes = new JButtonLabel();
    private JScrollPane scrComponentes = new JScrollPane();    
    private FarmaTableModel tblModelComponentes;
    private JTable tblComponentes = new JTable();

    private JLabelFunction lblAgrega = new JLabelFunction();
    private JLabelFunction lblEdita = new JLabelFunction();
    private JLabelFunction lblElimina = new JLabelFunction();
    private JLabelFunction lblGuarda = new JLabelFunction();
    private JLabelFunction lblEscape = new JLabelFunction();
    
    private JPanelHeader pnlProducto = new JPanelHeader();
    private JButtonLabel lblProducto = new JButtonLabel();
    private JTextFieldSanSerif txtCodigoProducto = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescripcionProducto = new JTextFieldSanSerif();


    public DlgMantRecetasDetalle() {
        this(null, "", false);
    }

    public DlgMantRecetasDetalle(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(680, 360));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Kit - Detalle");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened();
            }
            public void windowClosing(WindowEvent e) {
                this_windowClosing();
            }
        });
        
        pnlProducto.setBounds(new Rectangle(10, 10, 645, 50));
        pnlProducto.setBackground(new Color(0, 107, 165));
        pnlProducto.setLayout(null);
        
        lblProducto.setText("Producto final");
        lblProducto.setBounds(new Rectangle(10, 10, 90, 20));

        lblProducto.setFocusable(false);
        txtCodigoProducto.setBounds(new Rectangle(105, 10, 65, 20));
        txtCodigoProducto.setEditable(false);
        txtCodigoProducto.setFocusable(false);
        txtCodigoProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtCodigoProducto_keyPressed(e);
                }
        });

        txtDescripcionProducto.setBounds(new Rectangle(175, 10, 395, 20));
        txtDescripcionProducto.setEditable(false);
        txtDescripcionProducto.setFocusable(false);
        txtDescripcionProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtDescripcionProducto_keyPressed(e);
                }
        });

        pnlProducto.add(lblProducto, null);
        pnlProducto.add(txtCodigoProducto, null);
        pnlProducto.add(txtDescripcionProducto, null);
        jblTituloComponentes.setText("Listado de Componentes");
        jblTituloComponentes.setBounds(new Rectangle(10, 5, 575, 15));
        jblTituloComponentes.setMnemonic('L');
        jblTituloComponentes.setFocusable(false);
        pnlTituloComponentes.setBounds(new Rectangle(10, 60, 645, 25));

        pnlTituloComponentes.setFocusable(false);
        scrComponentes.setBounds(new Rectangle(10, 85, 645, 200));

        tblComponentes.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblComponentes_keyPressed(e);
                }
            });
        lblAgrega.setText("[F1] Nuevo");
        lblAgrega.setBounds(new Rectangle(10, 290, 105, 20));
        lblAgrega.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblAgrega_keyPressed(e);
                }
            });
        lblEdita.setText("[F3] Editar");
        lblEdita.setBounds(new Rectangle(120, 290, 105, 20));
        lblEdita.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblEdita_keyPressed(e);
                }
            });
        lblElimina.setText("[F5] Eliminar");
        lblElimina.setBounds(new Rectangle(230, 290, 105, 20));
        lblElimina.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblElimina_keyPressed(e);
                }
            });
        
        lblGuarda.setText("[F11] Guardar");
        lblGuarda.setBounds(new Rectangle(340, 290, 105, 20));
        lblGuarda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblElimina_keyPressed(e);
                }
            });
        
        lblEscape.setText("[ESC] Cerrar");
        lblEscape.setBounds(new Rectangle(550, 290, 105, 20));

        lblEscape.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblEscape_keyPressed(e);
                }
            });
        jContentPane.add(pnlProducto, null);
        pnlTituloComponentes.add(jblTituloComponentes, null);
        jContentPane.add(pnlTituloComponentes, null);
        scrComponentes.getViewport().add(tblComponentes, null);
        jContentPane.add(scrComponentes, null);
        jContentPane.add(lblAgrega, null);
        jContentPane.add(lblEdita, null);
        jContentPane.add(lblElimina, null);
        jContentPane.add(lblGuarda, null);
        jContentPane.add(lblEscape, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

     private void initialize() {
        initTables();
        txtCodigoProducto.setText(VariablesRecetas.admReceta.getReceta().getCodProducto());
        txtDescripcionProducto.setText(VariablesRecetas.admReceta.getReceta().getDescProducto());
    }
/*
    /* ********************************************************************** */
    /*                            METODO DE INICIALIZACION                    */
    /* ********************************************************************** */

    private void initTables() {        
        tblModelComponentes =
                new FarmaTableModel(ConstantsRecetas.columnsListaComponentes, 
                                    ConstantsRecetas.defaultValuesListaComponentes,0);
        FarmaUtility.initSimpleList(tblComponentes, tblModelComponentes,
                                    ConstantsRecetas.columnsListaComponentes);        
        cargaListaComponentes();
        copiarComponentesDesdeTablaALista();
    }
    
    private static final int IND_COD_COMPONENTE=0;
    private static final int IND_DESC_COMPONENTE=1;
    private static final int IND_DESC_PRESENTACION=2;
    private static final int IND_CANTIDAD=3;
    private static final int IND_VALOR_FRACCION=4;
    private static final int IND_IND_VTA_STOCK=5;
    
    private void copiarComponentesDesdeTablaALista(){
        Receta receta = VariablesRecetas.admReceta.getReceta();
        
        Iterator<ArrayList> it = tblModelComponentes.data.iterator();
        while(it.hasNext()){
            ArrayList al = it.next();
            ComponenteReceta cr = new ComponenteReceta(receta, (String) al.get(IND_COD_COMPONENTE));
            cr.setCantidad(Integer.parseInt(al.get(IND_CANTIDAD).toString()));
            cr.setDescComponente((String) al.get(IND_DESC_COMPONENTE));
            cr.setDescPresenacion((String) al.get(IND_DESC_PRESENTACION));
            cr.setValorFraccion(Integer.parseInt(al.get(IND_VALOR_FRACCION).toString()));
            cr.setIndVtaStock((String) al.get(IND_IND_VTA_STOCK));
            VariablesRecetas.admReceta.agregarComponente(cr);
        }
    }
    
    private void cargaTablaDesdeLista(){
        List<ComponenteReceta> listaComponentes = VariablesRecetas.admReceta.getReceta().getListaComponentes();
        ArrayList<ArrayList<Object>> lista = new ArrayList<ArrayList<Object>>(listaComponentes.size());
        for(ComponenteReceta cr: listaComponentes){
            ArrayList<Object> al= new ArrayList<Object>(5);
            al.add(""+cr.getCodComponente());
            al.add(""+cr.getDescComponente());
            al.add(""+cr.getDescPresenacion());
            al.add(""+cr.getCantidad());
            al.add(""+cr.getValorFraccion());
            al.add(""+cr.getIndVtaStock());
            lista.add(al);
        }        
        tblModelComponentes.data.clear();
        tblModelComponentes.data.addAll(lista);
        tblComponentes.updateUI();
    }
    
    private void cargaListaComponentes() {
       try {           
           String codMaterial = VariablesRecetas.admReceta.getReceta().getCodProducto();
           DBMantenimiento.listaComponentesPorReceta(tblModelComponentes, codMaterial);
           FarmaUtility.ordenar(tblComponentes, tblModelComponentes, 0, FarmaConstants.ORDEN_DESCENDENTE);           
       } catch (SQLException e) {
           e.printStackTrace();
           FarmaUtility.showMessage(this, e.getMessage(), null); // agregar algo aqui
       }
    }
     
    /*
    /* ********************************************************************** */
    /*                            METODOS DE EVENTOS                          */
    /* ********************************************************************** */
    
    private void tblComponentes_keyPressed(KeyEvent e){
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
    
    private void cargaFechaSistema() {
        try {
            VariablesRecetas.fechaSys = FarmaUtility.getStringToDate(FarmaSearch.getFechaHoraBD(1), "dd/MM/yyyy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_F1: agregar(); break;
            case KeyEvent.VK_F3: editar(); break;
            case KeyEvent.VK_F5: eliminar(); break;
            case KeyEvent.VK_F11: guardar(); break;
            case KeyEvent.VK_ESCAPE: mostrarAdvertencia(); break;
            default:;
        }
    }
    
    private void mostrarAdvertencia() {
        if (FarmaUtility.rptaConfirmDialogDefaultNo(this, "¿Esta seguro de salir?\nAsegúrese de grabar los cambios con F11")) {
            cerrarVentana(false);
        }         
    }
    
    private void guardar(){
        boolean rpta = FarmaUtility.rptaConfirmDialog(this, "Desea guardar los cambios");
        if(rpta){
            try {
                DBMantenimiento.guardarListaComponentesReceta(VariablesRecetas.admReceta.getReceta());
                //FarmaUtility.showMessage(this, "Componentes guardados satisfactoriamente", null);
                FarmaUtility.aceptarTransaccion();
                cerrarVentana(true);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "No se pudo gurdar los cambios", null);
                log.error("ERROR AL GUARDAR COMPONENTES DE RECETA: "+e.getMessage(), e);
            }
        }
    }
    
    private void agregar(){
        limpiarSeleccionComponente();
        VariablesRecetas.modoOperacionRecetaComponente = ModoOperacionRecetaComponente.REGISTRO;
        DlgSeleccionComponenteDisponible disponibles = new DlgSeleccionComponenteDisponible(myParentFrame,"",true);
        disponibles.setVisible(true);
        if(FarmaVariables.vAceptar){
            cargaTablaDesdeLista();
            FarmaUtility.showMessage(this,"Componente agregado",null);
        }
    }
    
    private void limpiarSeleccionComponente(){ 
        VariablesRecetas.componenteRecetaSeleccionado = null;
        VariablesRecetas.modoOperacionRecetaComponente = null;
    }
    
    private void editar(){
        limpiarSeleccionComponente();
        if(evaluacionEdicion()){
            VariablesRecetas.modoOperacionRecetaComponente = ModoOperacionRecetaComponente.EDICION;
            VariablesRecetas.componenteRecetaSeleccionado = getComponenteAEditar();
            DlgMantRecetasComponente componente = new DlgMantRecetasComponente(myParentFrame,"",true);
            componente.setVisible(true);
            if(FarmaVariables.vAceptar){                
                cargaTablaDesdeLista();
                FarmaUtility.showMessage(this,"Componente actualizado",null);
            }
        }
    }
    
    private boolean evaluacionEdicion(){
        int indice = tblComponentes.getSelectedRow();
        if(indice==-1){
            FarmaUtility.showMessage(this, "Debe seleccionar un componente", null);
            return false;
        }
        return true;
    }
    
    
    private void eliminar(){
        limpiarSeleccionComponente();
        if(evaluacionDeEliminacion()){
            ComponenteReceta cr = getComponenteAEliminar();            
            VariablesRecetas.admReceta.removerComponente(cr);
            cargaTablaDesdeLista();
            FarmaUtility.showMessage(this, "Componente eliminado", null);
        }
    }
    
    private boolean evaluacionDeEliminacion(){        
        int indice = tblComponentes.getSelectedRow();
        if(indice==-1){
            FarmaUtility.showMessage(this, "Debe seleccionar un componente", null);
            return false;
        }        
        return FarmaUtility.rptaConfirmDialog(this, "Desea eliminar el componente");
    }
    
    private ComponenteReceta getComponenteAEliminar(){
        int indice = tblComponentes.getSelectedRow();
        Receta receta = VariablesRecetas.admReceta.getReceta();
        String codComponente = tblComponentes.getValueAt(indice,0).toString();
        return new ComponenteReceta(receta, codComponente);
    }
    
    private ComponenteReceta getComponenteAEditar(){
        int indice = tblComponentes.getSelectedRow();
        String codComponente=tblComponentes.getValueAt(indice,0).toString();
        String descComponente=tblComponentes.getValueAt(indice,1).toString();
        int cantidad=Integer.parseInt(tblComponentes.getValueAt(indice,3).toString());
        int valorFraccion=Integer.parseInt(tblComponentes.getValueAt(indice,4).toString());
        String indVtaStock=tblComponentes.getValueAt(indice,5).toString();
        Receta receta = VariablesRecetas.admReceta.getReceta();
        ComponenteReceta componente = new ComponenteReceta(receta, codComponente);
        componente.setCantidad(cantidad);
        componente.setDescComponente(descComponente);
        componente.setValorFraccion(valorFraccion);
        componente.setIndVtaStock(indVtaStock);
        return componente;
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    /* ********************************************************************** */
    /*                    METODOS DE LOGICA DE NEGOCIO                         */
    /* *********************************************************************** */

    private void txtCodigoProducto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtDescripcionProducto_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblAgrega_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblEdita_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblElimina_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblEscape_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
