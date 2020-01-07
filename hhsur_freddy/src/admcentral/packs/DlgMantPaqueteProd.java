package admcentral.packs;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import admcentral.packs.reference.ConstantsPack;
import admcentral.packs.reference.DBPacks;
import admcentral.packs.reference.VariablesPacks;
import admcentral.packs.DlgFiltro;

import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMantPaqueteProd.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      16.09.2008   Creación
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */

public class DlgMantPaqueteProd extends JDialog {
    private static final Log log = LogFactory.getLog(DlgMantPaqueteProd.class);
    Frame myParentFrame;
    boolean vFlagTeclaFx =  false;
    //FarmaTableModel tableModel;
    private final int COL_CHECK = 0;
    private final int COL_COD = 1;
    private final int COL_DESC = 2;
    
    private final int COL_CANT = 5;
    private final int COL_PORC_DCTO = 6;
    private final int COL_VAL_FRAC = 7;

    //private boolean todos = false;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();


    private JPanelHeader pnlHeaderListaLocales = new JPanelHeader();
    
    private JPanelHeader pnlHeaderPaquete = new JPanelHeader();//panel header verde

    private JScrollPane srcListaLocales = new JScrollPane();

    private JTable tblListaProductos = new JTable();


    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelFunction lblEsc = new JLabelFunction();


    private JButtonLabel btnListaLocales = new JButtonLabel();
    
    private JButtonLabel btnListaProdPaquete = new JButtonLabel();


    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    //private JLabelFunction lblF10 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();


    /** fin **/
    

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMantPaqueteProd() {
        this(null, "", false);
    }

    public DlgMantPaqueteProd(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(853, 369));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de paquete producto");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(841, 350));
        pnlHeaderListaLocales.setBounds(new Rectangle(10, 40, 815, 30));
        pnlHeaderListaLocales.setBackground(new Color(0, 107, 165));
        
        
        pnlHeaderPaquete.setBounds(new Rectangle(10, 5, 815, 30));
        //pnlHeaderListaLocales.setBackground(new Color(255, 130, 14));
        
        srcListaLocales.setBounds(new Rectangle(10, 70, 815, 235));
        lblF11.setBounds(new Rectangle(595, 310, 105, 20));
        lblF11.setText("[F11] Aceptar");
        lblEsc.setBounds(new Rectangle(715, 310, 110, 20));
        lblEsc.setText("[Esc] Cerrar");


        btnListaLocales.setText("Seleccione Producto :");
        btnListaLocales.setBounds(new Rectangle(10, 5, 130, 20));
        btnListaLocales.setMnemonic('S');
        btnListaLocales.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnListaLocales_keyPressed(e);
                    }
                });
        btnListaLocales.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnListaLocales_actionPerformed(e);
                    }
                });
        
        
        btnListaProdPaquete.setText("Listado de Productos del : "+VariablesPacks.Paquete);
        btnListaProdPaquete.setBounds(new Rectangle(10, 5, 295, 20));
        
        txtBuscar.setBounds(new Rectangle(155, 5, 230, 20));
        txtBuscar.setFont(new Font("SansSerif", 0, 11));
        txtBuscar.addKeyListener(new KeyAdapter() {

                    public void keyPressed(KeyEvent e) {
                        txtBuscar_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtBuscar_keyReleased(e);
                    }
                });


        /*lblF10.setBounds(new Rectangle(10, 315, 120, 20));
        lblF10.setText("[F4] Filtrar");*/
        
        lblF2.setBounds(new Rectangle(10, 310, 150, 20));
        lblF2.setText("[F2] Modificar Cantidad");

        //jContentPane.add(lblF10, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        
        srcListaLocales.getViewport().add(tblListaProductos, null);
        //srcListaLocales.getViewport().setView(tblListaProductos);
        
        jContentPane.add(srcListaLocales, null);


        jContentPane.add(pnlHeaderListaLocales, null);
        pnlHeaderPaquete.add(btnListaProdPaquete,null);
        jContentPane.add(pnlHeaderPaquete, null);


        pnlHeaderListaLocales.add(txtBuscar, null);
        pnlHeaderListaLocales.add(btnListaLocales, null);
        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setContentPane(jContentPane);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        initDetalle();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        
        VariablesPacks.tblModelListaPaquete = 
                new FarmaTableModel(ConstantsPack.columnsListaPackProds, 
                                    ConstantsPack.defaultValuesListaPackProds, 
                                    0);
        FarmaUtility.initSelectList(tblListaProductos, VariablesPacks.tblModelListaPaquete, 
                                    ConstantsPack.columnsListaPackProds);
        
        
        cargarListaProductos();
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtPorcDesc1_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }

    private void btnListaLocales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void btnListaLocales_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, null, 0);

        if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
            
        } else{
            chkKeyPressed(e);
        }
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 
                                              COL_DESC);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            
            if (VariablesPacks.Paquete.equalsIgnoreCase(
                    ConstantsPack.PAQUETE_1))  {
                
                operaProducto(VariablesPacks.listaPaquete1);
                    
            }else if(VariablesPacks.Paquete.equalsIgnoreCase(
                    ConstantsPack.PAQUETE_2)){
                operaProducto(VariablesPacks.listaPaquete2);
            }
            
        }
        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 
                                         COL_DESC);
    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !vFlagTeclaFx) {
            this.setVisible(false);
            this.dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_F4 && !vFlagTeclaFx) {

            /**
       * Filtramos por ubicacion de local
       * @author JCORTEZ
       * @since  04.09.2007
       * */
            DlgFiltro filtro = new DlgFiltro(myParentFrame, "", true);
            filtro.setVisible(true);
            if (FarmaVariables.vAceptar) {
                //cargarListaLocales();
                FarmaUtility.moveFocus(txtBuscar);
                if (tblListaProductos.getRowCount() < 1) {
                    txtBuscar.setText("");
                    FarmaUtility.showMessage(this, 
                                             "No existe locales en esta ubicacion", 
                                             txtBuscar);
                }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_F2 && !vFlagTeclaFx) {
            int rowSel = tblListaProductos.getSelectedRow();
            if( rowSel > -1 &&
                (
                 (Boolean)tblListaProductos.getValueAt(rowSel,COL_CHECK)
                 //(FarmaUtility.getValueFieldArrayList(VariablesPacks.tblModelListaPaquete.data, rowSel, COL_CHECK))
                ).booleanValue() ){
                if (FarmaUtility.rptaConfirmDialog(
                    this,"Esta Seguro que desea modificar datos del"+
                         "\n Producto:"+tblListaProductos.getValueAt(rowSel,COL_DESC)+
                         "\ndel "+VariablesPacks.Paquete+" ?")) {                  
                    
                    //System.out.println(" paquete al cual pertenece el producto : "+VariablesPacks.Paquete);
                    
                    if(VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_1)){
                        ArrayList lProd;
                        for( int i=0; i <VariablesPacks.listaPaquete1.size(); i++){
                            lProd= (ArrayList)VariablesPacks.listaPaquete1.get(i);
                            
                            //System.out.println("cod_prod L :"+lProd.get(0).toString()+"***");
                            //System.out.println("cod_prod tblL :"+tblListaProductos.getValueAt(rowSel,COL_COD).toString()+"***");
                            
                            if( lProd.get(0).toString().
                                equalsIgnoreCase( tblListaProductos.getValueAt(rowSel,COL_COD).toString() )
                              ) {
                                VariablesPacks.RowProd = rowSel;
                                VariablesPacks.ACCION_PROD_PAQUETE = ConstantsPack.ACCION_PP_MODIF;
                                VariablesPacks.Vg_pp_indice = i;
                                VariablesPacks.Vg_pp_Cantidad = lProd.get(2).toString();
                                VariablesPacks.Vg_pp_Porc_Dcto = lProd.get(3).toString();
                                VariablesPacks.Vg_pp_Val_Frac = lProd.get(4).toString();
                                VariablesPacks.Vg_pp_Accion = lProd.get(5).toString();
                                
                                DlgMantDetPaqProd dlgMantDetPaqProd = new DlgMantDetPaqProd(this.myParentFrame,"",true,
                                                                                            lProd.get(0).toString()
                                                                                            );
                                
                                dlgMantDetPaqProd.setVisible(true);
                                
                                break;
                            }
                        }    
                        
                    } else if(VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_2)){
                        ArrayList lProd;
                        for(int i=0; i <VariablesPacks.listaPaquete2.size() ; i++){
                            lProd= (ArrayList)VariablesPacks.listaPaquete2.get(i);
                            
                            //System.out.println("cod_prod L :"+lProd.get(0).toString()+"***");
                            //System.out.println("cod_prod tblL :"+tblListaProductos.getValueAt(rowSel,COL_COD).toString()+"***");
                            
                            if( lProd.get(0).toString().equalsIgnoreCase(
                                   tblListaProductos.getValueAt(rowSel,COL_COD).toString())){
                                
                                VariablesPacks.RowProd = rowSel;
                                VariablesPacks.ACCION_PROD_PAQUETE = ConstantsPack.ACCION_PP_MODIF;
                                VariablesPacks.Vg_pp_indice = i;
                                VariablesPacks.Vg_pp_Cantidad = lProd.get(2).toString();
                                VariablesPacks.Vg_pp_Porc_Dcto = lProd.get(3).toString();
                                VariablesPacks.Vg_pp_Val_Frac = lProd.get(4).toString();
                                VariablesPacks.Vg_pp_Accion = lProd.get(5).toString();
                                DlgMantDetPaqProd dlgMantDetPaqProd = new DlgMantDetPaqProd(this.myParentFrame,"",true,
                                                                                            lProd.get(0).toString());
                                
                                dlgMantDetPaqProd.setVisible(true);
                                
                                break;
                            }
                        }
                        
                    }
                }                
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F11 && !vFlagTeclaFx) {
            
            if (validarDatos()) {
                
              /*** listado de productos ya seleccionado pasarlo a la tabla del paquete***/
                if( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_1) ){
                    /** limpiando el listado productos del paquete 1 mostrados en manpack**/
                    //System.out.println(" limpiando tabla paquete1 ");
                    VariablesPacks.tblModelListaGlobalPaquete1.clearTable();
                    //System.out.println(" listado de productos y datos de la lista de productos paq 1");
                    for( int i=0;i<VariablesPacks.listaPaquete1.size();i++ ){
                        ArrayList lProd = (ArrayList) VariablesPacks.listaPaquete1.get(i);
                        if( lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_CREAR_PROD_PAQ )
                            || lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_MODIFICAR_PROD_PAQ)
                            || lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_NINGUNA_PROD_PAQ)
                          ){
                            
                            VariablesPacks.tblModelListaGlobalPaquete1.insertRow(lProd);
                        }
                        //System.out.println("cod_prod:"+lProd.get(0).toString());
                        //System.out.println("desc_prod:"+lProd.get(1).toString());
                        //System.out.println("cantidad:"+lProd.get(2).toString());
                        //System.out.println("procDcto:"+lProd.get(3).toString());
                        //System.out.println("valFrac:"+lProd.get(4).toString());
                        //System.out.println("accion:"+lProd.get(5).toString());
                    }
                    
                }else if( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_2) ){
                    /** limpiando el listado productos del paquete2 mostrados en manpack**/
                    //System.out.println(" limpiando tabla paquete2 ");
                    VariablesPacks.tblModelListaGlobalPaquete2.clearTable();
                    //System.out.println(" listado de productos y datos de la lista de productos paq 2");
                    for( int i=0;i<VariablesPacks.listaPaquete2.size();i++ ){
                        ArrayList lProd = (ArrayList) VariablesPacks.listaPaquete2.get(i);
                        if( lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_CREAR_PROD_PAQ )
                            || lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_MODIFICAR_PROD_PAQ)
                            || lProd.get(5).toString().equalsIgnoreCase( ConstantsPack.ACCION_NINGUNA_PROD_PAQ)
                          ){
                            
                            VariablesPacks.tblModelListaGlobalPaquete2.insertRow(lProd);
                        }
                        //System.out.println("cod_prod:"+lProd.get(0).toString());
                        //System.out.println("desc_prod:"+lProd.get(1).toString());
                        //System.out.println("cantidad:"+lProd.get(2).toString());
                        //System.out.println("procDcto:"+lProd.get(3).toString());
                        //System.out.println("valFrac:"+lProd.get(4).toString());
                        //System.out.println("accion:"+lProd.get(5).toString());
                    }
                }
              
                cerrarVentana(false);
                              
            }
            
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargarListaProductos() {
        try {
            DBPacks.listadoProductos(VariablesPacks.tblModelListaPaquete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * metodo initDetalle
     * @author JCALLO
     * @since 23.09.2008
     */
    private void initDetalle() {
        if ( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_1) ) {
            llenarDatosPaquete(VariablesPacks.listaPaquete1);
        }
        else if( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_2) ){
            /** llenar los datos de los productos del paquete**/
            llenarDatosPaquete(VariablesPacks.listaPaquete2);
        }
        
    }
    
    /**
     * metodo encargado de llenar datos del paquete en los productos
     * @author JCALLO
     * @since 23.09.2008
     */
    private void llenarDatosPaquete(List listaPaquete) {
        /** llenar los datos de los productos del paquete**/
        ArrayList lProd ;
        for(int i =0;i<listaPaquete.size();i++){
          lProd = (ArrayList)listaPaquete.get(i);
          if( lProd.get(5).toString().equalsIgnoreCase(ConstantsPack.ACCION_CREAR_PROD_PAQ )
            ||lProd.get(5).toString().equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR_PROD_PAQ )
            ||lProd.get(5).toString().equalsIgnoreCase(ConstantsPack.ACCION_NINGUNA_PROD_PAQ)){
              //recorriendo todo listado de productos para ponerle check
              for(int j=0;j<VariablesPacks.tblModelListaPaquete.getRowCount();j++){
                  /**ver si conincide el codigo buscado**/
                if( VariablesPacks.tblModelListaPaquete.getValueAt(j,COL_COD)
                  .toString().equalsIgnoreCase(lProd.get(0).toString()) ){
                  
                  VariablesPacks.tblModelListaPaquete.setValueAt(
                      new Boolean(true),j,COL_CHECK);
                  
                  VariablesPacks.tblModelListaPaquete.setValueAt(
                      lProd.get(2).toString(),j,COL_CANT);
                  VariablesPacks.tblModelListaPaquete.setValueAt(
                      lProd.get(3).toString(),j,COL_PORC_DCTO);
                  VariablesPacks.tblModelListaPaquete.setValueAt(
                      lProd.get(4).toString(),j,COL_VAL_FRAC);
                  //VariablesPacks.tblModelListaPaquete.setValueAt(lProd.get(4),j,COL_CHECK);
                  
                   break;
                }
              }
          }
        }
    }
    
    /**
     * metodo encargado de cerrar la ventana
     * @author JCALLO
     * @since 23.09.2008
     */
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    
   /* private boolean grabar() {
        boolean retorno=true;
        
        
        
        return retorno;
    }*/
    
    /**
     * metodo encargado validar datos 
     * @author JCALLO
     * @since 16.09.2008
     */
    private boolean validarDatos() {
        boolean retorno = true;
        
        if (VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_1)) {
            if(VariablesPacks.listaPaquete1.size()<1){
                FarmaUtility.showMessage(this, "Se debe Especificar al menos un Producto en el "+ConstantsPack.PAQUETE_1+"!.",txtBuscar);
                retorno = false;
            }
        }
        else if (VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_2)){
            if(VariablesPacks.listaPaquete2.size()<1){
                FarmaUtility.showMessage(this, "Se debe Especificar al menos un Producto en el "+ConstantsPack.PAQUETE_2+"!.",txtBuscar);
                retorno = false;
            }
        }
        
        return retorno;
    }

    
   
    
    /**
     * Se realiza la busqueda por codigo y muestra dialogo
     * de agregar o quitar producto de la lista del paquete
     * @author JCALLO
     * @since 16.09.2008
     */
    private void operaProducto(List listaPaquete) {
        if (tblListaProductos.getRowCount() > 0) {
            if (!(FarmaUtility.findTextInJTable(tblListaProductos, 
                                                txtBuscar.getText().trim(), 
                                                COL_COD, COL_DESC))) {
                FarmaUtility.showMessage(this, 
                                         "Elemento no encontrado según criterio de búsqueda !!!", 
                                         txtBuscar);
                return;
            } else {
                int rowSelec = tblListaProductos.getSelectedRow();
                boolean check = 
                    ((Boolean)tblListaProductos.getValueAt(rowSelec,COL_CHECK)).booleanValue();            
                if (check) {
                    //System.out.println("ENTRO A PREGUNTAR en quitar");                    
                    if (FarmaUtility.rptaConfirmDialog(
                            this,"Esta Seguro que desea quitar el "+
                                 "\nProducto :"+tblListaProductos.getValueAt(rowSelec,COL_DESC)+
                                 "\ndel "+VariablesPacks.Paquete+" ?")) {
                        //quitando el check
                        tblListaProductos.setValueAt(new Boolean(false),rowSelec,COL_CHECK);
                        //limpiando datos de cantidad procentaje y valfrac
                        tblListaProductos.setValueAt(" ",rowSelec,COL_CANT);
                        tblListaProductos.setValueAt(" ",rowSelec,COL_PORC_DCTO);
                        tblListaProductos.setValueAt(" ",rowSelec,COL_VAL_FRAC);
                        
                        /*** QUITANDO PRODUCTO DE LA LISTA DE ACUERDO AL PAQUETE**/
                        
                        ArrayList lProd;
                        for(int i = 0;i<listaPaquete.size();i++){
                            lProd = (ArrayList)listaPaquete.get(i);//obteniendo producto
                            if( lProd.get(0).toString().trim().equalsIgnoreCase(
                                    tblListaProductos.getValueAt(rowSelec,COL_COD).toString()
                                    ) ){
                                
                                /** **/
                                if(lProd.get(5).toString().equalsIgnoreCase(
                                       ConstantsPack.ACCION_NINGUNA_PROD_PAQ)){
                                    lProd.set(5,ConstantsPack.ACCION_ELIMINAR_PROD_PAQ);
                                } else if (lProd.get(5).toString().equalsIgnoreCase(
                                       ConstantsPack.ACCION_CREAR_PROD_PAQ)){
                                    //VariablesPacks.listaPaquete1.remove(i);
                                    listaPaquete.remove(i);
                                } else if(lProd.get(5).toString().equalsIgnoreCase(
                                       ConstantsPack.ACCION_MODIFICAR_PROD_PAQ)){
                                    lProd.set(5,ConstantsPack.ACCION_ELIMINAR_PROD_PAQ);
                                }
                                break;
                            }                            
                        }
                        /**repintando**/
                        tblListaProductos.repaint();
                    }
                }
                else {
                    //System.out.println("ENTRO A MOSTRAR DIALOGO DE DETALLE DE PRODUCTO PAQUETE");
                    seleccionarProducto();
                }
            }
        }
    }
    
    /**
     * metodo seleccionar producto , cuando no esta incluido en tabla
     * productos del paquete
     * @author JCALLO
     * @since 23.09.2008
     * **/
    private void seleccionarProducto() {        
        if(tblListaProductos.getSelectedRow()>=0){
            
            VariablesPacks.RowProd = tblListaProductos.getSelectedRow();
            VariablesPacks.ACCION_PROD_PAQUETE = ConstantsPack.ACCION_PP_CREAR;
            
            DlgMantDetPaqProd dlgMantDetPaqProd = new DlgMantDetPaqProd(this.myParentFrame,"",true,
                                                                        
                                                                        FarmaUtility.getValueFieldArrayList(VariablesPacks.tblModelListaPaquete.data, 
                                                                                                             VariablesPacks.RowProd,
                                                                                                            1)
                                                                        );
            dlgMantDetPaqProd.setVisible(true);   
            tblListaProductos.repaint();        
                       
        }
    }


}
