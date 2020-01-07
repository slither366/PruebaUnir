package cilator.mantenimiento;

import cilator.mantenimiento.reference.ComponenteReceta;
import cilator.mantenimiento.reference.ConstantsRecetas;
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
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class DlgSeleccionComponenteDisponible extends JDialog {    
    
    private Frame myParentFrame;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    
    private JPanelTitle pnlTituloComponentesDisponibles = new JPanelTitle();
    private JButtonLabel jblTituloComponentesDisponibles = new JButtonLabel();
    private JScrollPane scrComponentesDisponibles = new JScrollPane();    
    private FarmaTableModel tblModelComponentesDisponibles;
    private JTable tblComponentesDisponibles = new JTable();
    
    private JLabelFunction lblAgrega = new JLabelFunction();    
    private JLabelFunction lblEscape = new JLabelFunction();
    
    private JPanelHeader pnlProducto = new JPanelHeader();
    private JButtonLabel lblProducto = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtDescripcionProducto = new JTextFieldSanSerif();
    
    public DlgSeleccionComponenteDisponible() {
        this(null, "", false);
    }

    public DlgSeleccionComponenteDisponible(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(594, 360));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Seleccion de Componente para receta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened();
            }
            public void windowClosing(WindowEvent e) {
                this_windowClosing();
            }
        });
        
        pnlProducto.setBounds(new Rectangle(10, 10, 560, 50));
        pnlProducto.setBackground(new Color(0, 107, 165));
        pnlProducto.setLayout(null);
        
        lblProducto.setText("Componente");
        lblProducto.setBounds(new Rectangle(15, 10, 85, 20));

        lblProducto.setFocusable(false);

        txtDescripcionProducto.setBounds(new Rectangle(100, 10, 315, 20));
        txtDescripcionProducto.setLengthText(20);
        txtDescripcionProducto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDescripcionProducto_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtDescripcionProducto_keyReleased(e);
                }
            });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(420, 10, 75, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 0, 11));
        btnBuscar.setBorderPainted(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);


        btnBuscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
            });
        pnlProducto.add(lblProducto, null);


        pnlProducto.add(btnBuscar, null);
        pnlProducto.add(txtDescripcionProducto, null);
        jblTituloComponentesDisponibles.setText("Listado de Componentes Disponibles");
        jblTituloComponentesDisponibles.setBounds(new Rectangle(10, 5, 500, 15));
        jblTituloComponentesDisponibles.setMnemonic('L');
        jblTituloComponentesDisponibles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jblTituloComponentesDisponibles_actionPerformed();
            }
        });
        jblTituloComponentesDisponibles.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblComponentesDisponibles_keyPressed(e);
            }
        });
        pnlTituloComponentesDisponibles.setBounds(new Rectangle(10, 70, 560, 25));
        
        scrComponentesDisponibles.setBounds(new Rectangle(10, 95, 560, 200));

        lblAgrega.setText("[ENTER] Seleccionar");
        lblAgrega.setBounds(new Rectangle(10, 300, 130, 20));

        lblAgrega.setFocusable(false);
        lblEscape.setText("[ESC] Cerrar");
        lblEscape.setBounds(new Rectangle(465, 300, 105, 20));

        scrComponentesDisponibles.getViewport().add(tblComponentesDisponibles, null);
        pnlTituloComponentesDisponibles.add(jblTituloComponentesDisponibles, null);
        jContentPane.add(pnlProducto, null);
        jContentPane.add(pnlTituloComponentesDisponibles, null);
        jContentPane.add(scrComponentesDisponibles, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(lblAgrega, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

     private void initialize() {
        initTables();
        FarmaUtility.moveFocus(tblComponentesDisponibles);
    }
/*
    /* ********************************************************************** */
    /*                            METODO DE INICIALIZACION                    */
    /* ********************************************************************** */

    private void initTables() {        
        tblModelComponentesDisponibles =
                new FarmaTableModel(ConstantsRecetas.columnsListaComponentesDisponibles, 
                                    ConstantsRecetas.defaultValuesListaComponentesDisponibles,0);
        FarmaUtility.initSimpleList(tblComponentesDisponibles, tblModelComponentesDisponibles,
                                    ConstantsRecetas.columnsListaComponentesDisponibles);        
        cargaListaRecetas();
    }
    
    private void cargaListaRecetas() {
       try {
           String codMaterial = VariablesRecetas.admReceta.getReceta().getCodProducto();
           String descComponente = txtDescripcionProducto.getText();
           DBMantenimiento.listaComponentesDisponiblesParaReceta(tblModelComponentesDisponibles, codMaterial, descComponente);
           FarmaUtility.ordenar(tblComponentesDisponibles, tblModelComponentesDisponibles, 1, FarmaConstants.ORDEN_ASCENDENTE);
           removerComponentesEnLista();
       } catch (SQLException e) {
           e.printStackTrace();
           FarmaUtility.showMessage(this, e.getMessage(), null); // agregar algo aqui
       }
    }
    
    private static final int IND_COD_COMPONENTE=0;
    private void removerComponentesEnLista(){
        List<ComponenteReceta> listaComponentes = VariablesRecetas.admReceta.getReceta().getListaComponentes();
        ArrayList<ArrayList<Object>> listaParaRemover = new ArrayList<ArrayList<Object>>();
        Iterator<ArrayList<Object>> it = tblModelComponentesDisponibles.data.iterator();
        while(it.hasNext()){
            ArrayList<Object> al = it.next();
            String codComponente = al.get(IND_COD_COMPONENTE).toString();
            ComponenteReceta cr = new ComponenteReceta(VariablesRecetas.admReceta.getReceta(),codComponente);
            if(listaComponentes.contains(cr)){
                listaParaRemover.add(al);
            }
        }
        tblModelComponentesDisponibles.data.removeAll(listaParaRemover);
        tblComponentesDisponibles.updateUI();
    }
     
    /*
    /* ********************************************************************** */
    /*                            METODOS DE EVENTOS                          */
    /* ********************************************************************** */

    private void tblComponentesDisponibles_keyPressed(KeyEvent e){
        chkKeyPressed(e);
    }
    
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
    
    private void jblTituloComponentesDisponibles_actionPerformed() {
        FarmaUtility.moveFocus(tblComponentesDisponibles);
    }
    
   

    private void chkKeyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_ENTER: seleccionar(); break;
            case KeyEvent.VK_ESCAPE: cerrarVentana(false); break;
            default:;
        }
    } 
    
    private void seleccionar(){
        int indice = tblComponentesDisponibles.getSelectedRow();
        if(indice==-1){
            FarmaUtility.showMessage(this, "Debe seleccionar un componente", null);
            return;
        }
        ComponenteReceta componente = getComponenteSeleccionado();
        VariablesRecetas.componenteRecetaSeleccionado = componente;
        
        DlgMantRecetasComponente mrc = new DlgMantRecetasComponente(myParentFrame,"",true);
        mrc.setVisible(true);
        if(FarmaVariables.vAceptar){
            cerrarVentana(true);
        }
    }
    
    private ComponenteReceta getComponenteSeleccionado(){
        int indice = tblComponentesDisponibles.getSelectedRow();
        String codComponente = tblComponentesDisponibles.getValueAt(indice,0).toString();
        String descComponente = tblComponentesDisponibles.getValueAt(indice,1).toString();
        String descPresentacion = tblComponentesDisponibles.getValueAt(indice,2).toString();
        String valorFraccion = tblComponentesDisponibles.getValueAt(indice,3).toString();
        Receta receta = VariablesRecetas.admReceta.getReceta();
        ComponenteReceta componente = new ComponenteReceta(receta, codComponente);
        componente.setDescComponente(descComponente);
        componente.setDescPresenacion(descPresentacion);
        componente.setValorFraccion(Integer.parseInt(valorFraccion));
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


    private void btnBuscar_actionPerformed(ActionEvent e) {
        cargaListaRecetas();
        FarmaUtility.moveFocus(txtDescripcionProducto);
    }

    private void txtDescripcionProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblComponentesDisponibles, txtDescripcionProducto, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
          e.consume();
          if (tblComponentesDisponibles.getSelectedRow() >= 0) {
            if (!(FarmaUtility.findTextInJTable(tblComponentesDisponibles, txtDescripcionProducto.getText().trim(), 0, 1))) {
              FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtDescripcionProducto);
              return;
            }
          }
        }
        chkKeyPressed(e);
    }

    private void txtDescripcionProducto_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblComponentesDisponibles, txtDescripcionProducto, 1);
    }
}