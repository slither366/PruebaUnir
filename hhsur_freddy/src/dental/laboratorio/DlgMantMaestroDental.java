package dental.laboratorio;


import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JTextFieldSanSerif;

import dental.laboratorio.reference.ConstantsMantenimiento;
import dental.laboratorio.reference.DBMantenimiento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.VariablesModuloVentas;


public class DlgMantMaestroDental extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantMaestroDental.class);
    Frame myParentFrame;
    FarmaTableModel modelMarca,modelProducto,modelUnidadPrecio,modelLoteProd;

    private ArrayList modelBaseProd = new ArrayList();
    private JTable tblListaLaboratorios = new JTable();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel tabMantLaboratorio = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel tabMantPrecios = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JTextFieldSanSerif txtLaboratorio = new JTextFieldSanSerif();
    private JScrollPane srcListaLaboratorios = new JScrollPane();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblModificar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtProducto = new JTextField();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JTable tblListaUnidadProd = new JTable();
    private JTable tblListaProd = new JTable();
    private JTable tblLoteProd = new JTable();
    private JButton btnAñadirProducto = new JButton();

    public DlgMantMaestroDental() {
        this(null, "", false);
    }

    public DlgMantMaestroDental(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        VariablesModuloVentas.vPosNew = 0;
        VariablesModuloVentas.vPosOld = 0;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1405, 556));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.setTitle("Mantenimiento Marca /  Productos y Precios");
        tabMantLaboratorio.setLayout(null);
        jPanel2.add(txtLaboratorio, null);
        tabMantLaboratorio.add(lblSalir, null);
        tabMantLaboratorio.add(lblModificar, null);
        tabMantLaboratorio.add(lblCrear, null);
        tabMantLaboratorio.add(srcListaLaboratorios, null);
        tabMantLaboratorio.add(jPanel2, null);
        jTabbedPane1.addTab("Marca", tabMantLaboratorio);
        tabMantPrecios.setLayout(null);
        jPanel2.setBounds(new Rectangle(20, 10, 750, 50));
        jPanel2.setBackground(new Color(0, 114, 169));
        jPanel2.setLayout(null);
        txtLaboratorio.setBounds(new Rectangle(20, 15, 675, 20));
        txtLaboratorio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtLaboratorio_keyPressed(e);
                }

            public void keyReleased(KeyEvent e) {
                    txtLaboratorio_keyReleased(e);
                }
        });
        srcListaLaboratorios.setBounds(new Rectangle(20, 60, 750, 390));
        srcListaLaboratorios.getViewport().add(tblListaLaboratorios, null);
        tblListaLaboratorios.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaLaboratorios_keyPressed(e);
            }
        });
        lblCrear.setText("[ F2 ] Crear");
        lblCrear.setBounds(new Rectangle(25, 465, 100, 20));
        lblCrear.setFont(new Font("Tahoma", 1, 11));
        lblCrear.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    lblCrear_mouseClicked(e);
                }
        });
        lblModificar.setText("[ F3 ] Modificar");
        lblModificar.setBounds(new Rectangle(165, 465, 110, 20));
        lblModificar.setFont(new Font("Tahoma", 1, 11));
        lblModificar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                modificar(e);
            }
        });
        lblSalir.setText("[ ESC ] Salir");
        lblSalir.setBounds(new Rectangle(455, 465, 95, 20));
        lblSalir.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setText("jLabel1");
        txtProducto.setBounds(new Rectangle(15, 20, 610, 30));
        txtProducto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtProducto_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtProducto_keyReleased(e);
                }

            });
        jScrollPane1.setBounds(new Rectangle(15, 65, 890, 410));
        jScrollPane2.setBounds(new Rectangle(915, 65, 470, 230));
        jScrollPane3.setBounds(new Rectangle(915, 310, 365, 165));
        tblListaProd.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListaProd_mouseClicked(e);
                }
            });
        btnAñadirProducto.setText("+");
        btnAñadirProducto.setBounds(new Rectangle(855, 40, 45, 20));
        btnAñadirProducto.setFont(new Font("Tahoma", 1, 11));
        btnAñadirProducto.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnAñadirProducto_mouseClicked(e);
                }
            });
        jScrollPane2.getViewport().add(tblListaUnidadProd, null);
        tabMantPrecios.add(btnAñadirProducto, null);
        jScrollPane3.getViewport().add(tblLoteProd, null);
        tabMantPrecios.add(jScrollPane3, null);
        tabMantPrecios.add(jScrollPane2, null);
        jScrollPane1.getViewport().add(tblListaProd, null);
        tabMantPrecios.add(jScrollPane1, null);
        tabMantPrecios.add(txtProducto, null);
        jTabbedPane1.addTab("Productos y Precios", tabMantPrecios);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
    }

    private void initTable() {
        modelMarca =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaLaboratorios, ConstantsMantenimiento.defaultValuesListaLaboratorios,
                                    0);
        FarmaUtility.initSimpleList(tblListaLaboratorios, modelMarca, ConstantsMantenimiento.columnsListaLaboratorios);
        cargaListaMarca();
        
        // ----------------------------------------------
        modelProducto =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaProductos, ConstantsMantenimiento.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSelectList(tblListaProd, modelProducto, ConstantsMantenimiento.columnsListaProductos);
        cargaListaProductos();
        // ----------------------------------------------
        modelUnidadPrecio =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaUnidadPrecio, ConstantsMantenimiento.defaultValuesListaUnidadPrecio,
                                    0);
        FarmaUtility.initSimpleList(tblListaUnidadProd, modelUnidadPrecio, ConstantsMantenimiento.columnsListaUnidadPrecio);
        cargaListaUnidadPrecio();
        // ----------------------------------------------
        modelLoteProd =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaLoteProd, ConstantsMantenimiento.defaultValuesListaLoteProd,
                                    0);
        FarmaUtility.initSimpleList(tblLoteProd, modelLoteProd, ConstantsMantenimiento.columnsListaLoteProd);
        cargaListaLoteProducto();
    }

    private void cargaListaMarca() {
        try {
            DBMantenimiento.getListaLaboratorios(modelMarca);

            if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelMarca, 1, "asc");
            log.debug("Se cargo la lista de Laboratorios");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los marca. \n " + e.getMessage(),
                                     tblListaLaboratorios);
        }
    }
    
    private void cargaListaProductos() {
        try {
            DBMantenimiento.getListaProductos(modelProducto);
            modelBaseProd = (ArrayList)(modelProducto.data.clone());
            /*if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
            log.debug("Se cargo la lista de productos");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los productos. \n " + e.getMessage(),
                                     tblListaLaboratorios);
        }
    }    
    
    private void cargaListaUnidadPrecio() {
        try {
            if(tblListaProd.getRowCount() > 0){
                int pos = tblListaProd.getSelectedRow();
                if(pos != -1){
                    String codProd = tblListaProd.getValueAt(pos, 0).toString();
                    DBMantenimiento.getListaUnidadPrecio(modelUnidadPrecio, codProd);
                }
            }
            
            /*if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
            log.debug("Se cargo la lista de productos");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los productos. \n " + e.getMessage(),
                                     tblListaLaboratorios);
        }
    }       
    
    private void cargaListaLoteProducto() {
        try {
            if(tblListaProd.getRowCount() > 0){
                int pos = tblListaProd.getSelectedRow();
                if(pos != -1){
                    String codProd = tblListaProd.getValueAt(pos, 0).toString();
                    DBMantenimiento.getListaLoteProducto(modelLoteProd, codProd);
                }
            }
            
            /*if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
            log.debug("Se cargo la lista de productos");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los productos. \n " + e.getMessage(),
                                     tblListaLaboratorios);
        }
    }    

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtLaboratorio);
    }

    private void txtLaboratorio_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void txtLaboratorio_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaLaboratorios, txtLaboratorio, 1);
        chkKeyPressed(e);
    }

    private void tblListaLaboratorios_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaLaboratorios, txtLaboratorio, 1);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            accion("I");
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            accion("U");
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            accion("U");
        }             
    }

    public void accion(String pTipo) {
        String pId = "";

        if (pTipo.equalsIgnoreCase("U")) {
            pId = FarmaUtility.getValueFieldArrayList(modelMarca.data, tblListaLaboratorios.getSelectedRow(), 0);
        }

        DlgMantMarca dlg = new DlgMantMarca(this.myParentFrame, "", true, pTipo, pId);
        dlg.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaMarca();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void lblCrear_mouseClicked(MouseEvent e) {
        accion("I");
    }

    private void modificar(MouseEvent e) {
        accion("U");
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProd, null, 0);
        
        /*if(tblListaProd.getRowHeight()==0&&txtProducto.getText().trim().length()==0){
            clonarListadoProductos();
        }
            
        
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER)){
            filtroGoogle();
            }
        actualizaUnidadLotes();*/
        
        if(tblListaProd.getRowHeight()==0&&txtProducto.getText().trim().length()==0){
            clonarListadoProductos();
        }
            
        
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }else 
            if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER)){
            filtroGoogle();
            }
        else{
            if(e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP || 
                e.getKeyCode() == KeyEvent.VK_DOWN || 
                e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
                
                    VariablesModuloVentas.vPosNew = tblListaProd.getSelectedRow();
                    if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                        actualizaUnidadLotes();
                        VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                    } else {
                        if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                            actualizaUnidadLotes();
                            VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                        }
                    }
                
                
                }
           else
            if( e.getKeyCode() == KeyEvent.VK_ENTER){
                e.consume();
                int vFoco = tblListaProd.getSelectedRow();
                //txtFiltroCategoria.setText(""+vFoco);
                if (tblListaProd.getSelectedRow() >= 0)
                {
                   accionProducto(false);
                  FarmaUtility.moveFocus(txtProducto);
                }
                //System.out.println("- "+vFoco);
            }
        }
    }
    

    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < modelBaseProd.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)modelBaseProd.get(i)).clone();
            arrayClone.add(aux);
        }
       // ascasc
        modelProducto.clearTable();
        modelProducto.data = arrayClone;
        tblListaProd.repaint();
        tblListaProd.show();
    }
    
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
    }
    
    private void filtrarBusquedaGoogle() {
        String condicion = txtProducto.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = modelProducto.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String nombre = fila.get(2).toString().toUpperCase().trim();
                String codigo = fila.get(1).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(nombre.contains(condicion)||codigo.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            modelProducto.data = filteredCollection;
            modelProducto.fireTableDataChanged();
            
            if(tblListaProd.getRowCount()==0){
                clonarListadoProductos();
            }
        }
        else{
            clonarListadoProductos();
        }
        
        if(tblListaProd.getRowCount()>0)
            FarmaGridUtils.showCell(tblListaProd, 0, 0);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        /*if(tblListaProd.getRowHeight()==0&&txtProducto.getText().trim().length()==0){
            clonarListadoProductos();
        }
            
        
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER)){
            filtroGoogle();
            }
        else{
            if(e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP || 
                e.getKeyCode() == KeyEvent.VK_DOWN || 
                e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
                
                    VariablesModuloVentas.vPosNew = tblListaProd.getSelectedRow();
                    if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                        actualizaUnidadLotes();
                        VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                    } else {
                        if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                            actualizaUnidadLotes();
                            VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                        }
                    }
                
                
                }
           else
            if( e.getKeyCode() == KeyEvent.VK_ENTER){
                e.consume();
                int vFoco = tblListaProd.getSelectedRow();
                //txtFiltroCategoria.setText(""+vFoco);
                if (tblListaProd.getSelectedRow() >= 0)
                {
                   accionProducto(false);
                  FarmaUtility.moveFocus(txtProducto);
                }
                //System.out.println("- "+vFoco);
            }
        }*/

    }

    private void btnAñadirProducto_mouseClicked(MouseEvent e) {
        accionProducto(true);
    }
    
    public void accionProducto(boolean vAgregar){
        String pCodProd = "N";
        String pAccion = "I";
        if(!vAgregar){
            pAccion = "U";
            if(tblListaProd.getSelectedRow()>=0)
             pCodProd = FarmaUtility.getValueFieldArrayList(modelProducto.data, tblListaProd.getSelectedRow(), 1);    
        }
        DlgMantProducto dlgMantProducto = new DlgMantProducto(myParentFrame, "", true, pAccion,pCodProd);
        dlgMantProducto.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaProductos();
        }
    }

    private void btnEliminarProducto_mouseClicked(MouseEvent e) {
        if(FarmaUtility.rptaConfirmDialogDefaultNo(this, "'¿Seguro que desea eliminar este producto?")){
            try {
                if(tblListaProd.getRowCount() > 0){
                    int pos = tblListaProd.getSelectedRow();
                    if(pos != -1){
                        String codProd = tblListaProd.getValueAt(pos, 0).toString();
                        DBMantenimiento.setEstadoProducto(codProd);
                        FarmaUtility.aceptarTransaccion();
                    }
                }
                cargaListaProductos();
                /*if (tblListaLaboratorios.getRowCount() > 0)
                    FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
                log.debug("Se inactivo correctamente producto seleccionado");
            } catch (SQLException ex) {
                log.error("", ex);
                FarmaUtility.showMessage(this, "Error al inactivar producto seleccionado. \n " + ex.getMessage(),
                                         tblListaLaboratorios);
                FarmaUtility.liberarTransaccion();
            }
            
        }
    }

    private void tblListaProd_mouseClicked(MouseEvent e) {


        int i= tblListaProd.getSelectedRow();
        if (e.isMetaDown() ){
            // click derecho
            //muestraAjusteKardex();
        }
        else{
        if (e.getClickCount() == 2 && !e.isConsumed()) {
             e.consume();
             int vFoco = tblListaProd.getSelectedRow();
             //txtFiltroCategoria.setText(""+vFoco);
            if (tblListaProd.getSelectedRow() >= 0)
            {  
                accionProducto(false);
               FarmaUtility.moveFocus(txtProducto);
             }
             //System.out.println("- "+vFoco);
        }
        }
    }

    private void actualizaUnidadLotes() {
        

        int vFoco = tblListaProd.getSelectedRow();
        if(vFoco>=0){
            ArrayList vListaUnidad = new ArrayList();
            ArrayList vLoteProd = new ArrayList();
            
            try {
                for (int i = modelLoteProd.data.size()-1; i > 0; i--)
                    modelLoteProd.data.remove(i);

                for (int i = modelUnidadPrecio.data.size()-1; i > 0 ; i--)
                    modelUnidadPrecio.data.remove(i); 
                
                if(modelLoteProd.data.size()>0)
                    modelLoteProd.data.remove(0);
                
                if(modelUnidadPrecio.data.size()>0)
                    modelUnidadPrecio.data.remove(0);
                
                DBMantenimiento.getArrayUnidadProducto(vListaUnidad, FarmaUtility.getValueFieldArrayList(modelProducto.data, vFoco,1));
                DBMantenimiento.getArrayLoteProducto(vLoteProd,  FarmaUtility.getValueFieldArrayList(modelProducto.data, vFoco,1));
                
                modelLoteProd.fireTableDataChanged();
                tblLoteProd.repaint();
                
                modelUnidadPrecio.fireTableDataChanged();
                tblListaUnidadProd.repaint();
                
                
                for (int i = 0; i < vLoteProd.size(); i++) {
                    modelLoteProd.data.add(vLoteProd.get(i));
                    modelLoteProd.fireTableDataChanged();
                    tblLoteProd.repaint();
                }

                for (int i = 0; i < vListaUnidad.size(); i++) {
                    modelUnidadPrecio.data.add(vListaUnidad.get(i));
                    modelUnidadPrecio.fireTableDataChanged();
                    tblListaUnidadProd.repaint();
                }
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
        
    }

}
