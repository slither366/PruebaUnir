package venta.administracion.producto;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.producto.reference.ConstantsPrecios;
import venta.administracion.producto.reference.FacadeProducto;
import venta.caja.reference.PrintConsejo;
import venta.caja.reference.UtilityCaja;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProdImpresion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProdImpresion.class);
    private Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JScrollPane scrListaPrecProdCamb = new JScrollPane();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private FacadeProducto facadeProducto = new FacadeProducto();

    public DlgProdImpresion() {
        this(null, "", false);
    }

    public DlgProdImpresion(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(552, 375));
        this.getContentPane().setLayout( null );
        this.setTitle("Impresi�n de Stickers de precios");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 525, 50));
        jPanelWhite1.setBounds(new Rectangle(0, 0, 550, 350));
        scrListaPrecProdCamb.setBounds(new Rectangle(10, 70, 525, 240));
        lblEsc.setBounds(new Rectangle(420, 320, 117, 19));
        lblEsc.setText("[ ESC ] Salir");
        lblImprimir.setBounds(new Rectangle(290, 320, 117, 19));
        lblImprimir.setText("[ F11 ] Imprimir");
        
        jButtonLabel1.setText("Producto :");
        jButtonLabel1.setBounds(new Rectangle(15, 20, 75, 15));
        jButtonLabel1.setMnemonic('p');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        
        txtProducto.setBounds(new Rectangle(95, 15, 295, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtProducto_keyPressed(e);
                }
                });
       
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(415, 10, 80, 25));
        btnBuscar.setMnemonic('b');
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        btnBuscar.addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e){
                    txtProducto_keyPressed(e);
                }
            });
        btnBuscar.addFocusListener(new FocusListener(){

                @Override
                public void focusGained(FocusEvent e) {
                }

                @Override
                public void focusLost(FocusEvent e) {
                    FarmaUtility.moveFocus(txtProducto);
                }
            });
        scrListaPrecProdCamb.getViewport().add(tblProductos, null);
        jPanelWhite1.add(lblEsc, null);

        jPanelWhite1.add(lblImprimir, null);
        jPanelWhite1.add(scrListaPrecProdCamb, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelTitle1.add(txtProducto, null);
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(btnBuscar, null);
        this.getContentPane().add(jPanelWhite1, null);
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(this.txtProducto);
    }
    
    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.txtProducto);
    }
    
    private void txtProducto_keyPressed(KeyEvent e) {
      
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos,txtProducto, 1);
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
        
            validarBusqueda();
            
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                cerrarVentana();    
        }else if(venta.reference.UtilityPtoVenta.verificaVK_F11(e)){
                imprimirSticker();
        }
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        validarBusqueda();
    }

    private void cerrarVentana(){
      this.setVisible(false);
      this.dispose();
    }
    
    private void initialize(){
      FarmaVariables.vAceptar = false;
      initTable();  
    }
    private void initTable(){
        tableModel = new FarmaTableModel(ConstantsPrecios.columnsProductosImpresion, ConstantsPrecios.defaultValuesProductosImpresion,0);
        FarmaUtility.initSimpleList(tblProductos, tableModel,ConstantsPrecios.columnsProductosImpresion);
        tblProductos.getTableHeader().setReorderingAllowed(false);
    }
    
    private void buscarProductos(){
        tableModel.clearTable();
        tableModel.data = new ArrayList();
            if(!validBusqDesc(txtProducto.getText().trim())){
            return; 
            }
            try{
                ArrayList lista = facadeProducto.obtenerProductosPorDescripcion(txtProducto.getText().trim());    
                if(lista.size() > 0){
                    tableModel.data = lista;
                }else{
                        FarmaUtility.showMessage(this, "No se encontraron productos con la descripci�n ingresada.", null);
                    }
            }catch(Exception e){
                log.error("ocurri� el siguiente error al buscar productos : ",e);
                FarmaUtility.showMessage(this, 
                    "Error al buscar los productos. \n " + e.getMessage(), txtProducto);
                }
            
            
        
        limpiarCampo();
    }
 
    private void imprimirSticker(){
        int selectedRow = tblProductos.getSelectedRow();   
        if(selectedRow >= 0){
            String codigo = tblProductos.getValueAt(selectedRow, 0).toString();
            try {
                String codigoEPL = facadeProducto.obtenerCodigoEPLPorProducto(codigo);
                codigoEPL = codigoEPL.replace("]","").replace("[","").replace("?", "\n");
                
                boolean flag=UtilityCaja.nombreImpSticker();
                if (flag==true){
                    PrintConsejo.imprimirStickerEnLote(codigoEPL, VariablesPtoVenta.vImpresoraSticker);
                 
                }
            } catch (Exception e) {
                log.error("Ocurri� el siguiente error al imprimir el sticker : " ,e);
                FarmaUtility.showMessage(this, 
                    "Ocurri� un error al obtener los datos del producto \n " + e.getMessage(), txtProducto);
            }
            
        }
    } 
     
     
    private void buscarProductoCodigoBarra(String pCodBarra){
        tableModel.clearTable();
        tableModel.data = new ArrayList();
        if(!validBusqCodBarra(pCodBarra)){
            return;
        }
            try{
                ArrayList lista = facadeProducto.obtenerProductosCodigoBarra(pCodBarra);  
                if(lista.size() > 0){
                    tableModel.data = lista;
                    tblProductos.setRowSelectionInterval(0, 0);//selecciona el registro bucado
                    imprimirSticker();
                    tblProductos.clearSelection();
                }else{
                        FarmaUtility.showMessage(this, "No se encontraron productos con El codigo de Barra ingresada.", null);
                    }
            }catch(Exception e){
                log.error("ocurri� el siguiente error al buscar productos : ",e);
                FarmaUtility.showMessage(this, 
                    "Error al buscar los productos. \n " + e.getMessage(), txtProducto);
                }
            
            limpiarCampo();
        
    }
    private void limpiarCampo(){
        txtProducto.setText("");
    }
    
    private void validarBusqueda()
    {
    
        String cadena=txtProducto.getText().trim();

        try{
        //CHUANES  01.04.2014   
        //CONDICIONAMOS  QUE SE INGRESE 3 CARACTERES COMO MINIMO DE FORMA GENERAL    
        if(!validBusqGeneral(cadena)){
               return; 
        }
        char primerCar=cadena.charAt(0);
        char segundCar=cadena.charAt(1);
        char ultimoCar=cadena.charAt(cadena.length()-1);
            log.info("Primer caracter"+primerCar);
            log.info("Segundo caracter"+segundCar);
            log.info("Tercer caracter"+ultimoCar);
      //Se condiciona si es una descripc�on de producto o un codigo de barra
        if(Character.isLetter(primerCar) && Character.isLetter(segundCar) && Character.isLetter(ultimoCar)){ 
           
            buscarProductos();

        }else if(Character.isDigit(primerCar) && Character.isLetter(segundCar) && Character.isLetter(ultimoCar)){
             buscarProductos();
             }else 
            {
                cadena=UtilityPtoVenta.getCadenaAlfanumerica(cadena); //valida sin caracteres especiales
                cadena=UtilityPtoVenta.getCodBarraSinCarControl(cadena);//validacion de los EAN 
                buscarProductoCodigoBarra(cadena); 
            }
           
        }catch(Exception e){
                e.printStackTrace();
            }
    
    }
    /**
     * Valida la busqueda por descripci�n 
     * @author CHUANES
     * @since 01.04.201
     * @param cadena
     * @return
     * @throws Exception
     */
    private boolean validBusqDesc(String cadena){
        boolean flag;
        if(cadena.length()>=3){
            flag=true;
        }else{
            flag=false;
            FarmaUtility.showMessage(this, "Busqueda por descripci�n minimo 3 caracteres", null);   
        }
        return flag;
    }
    /**
     * Valida la busqueda por codigo de barra 
     * @author CHUANES
     * @since 01.04.2014
     * @param cadena
     * @return
     * @throws Exception
     */


    private boolean validBusqCodBarra(String cadena){
        boolean flag;
        if(cadena.length()>=7){
            flag=true;
        }else{
            flag=false;
            FarmaUtility.showMessage(this, "Busqueda por codigo de barra minimo 7 caracteres", null);   
        }
        return flag;
        
    }
    /**
     * Valida la busqueda general 
     * @author CHUANES
     * @since 01.04.201
     * @param cadena
     * @return
     * @throws Exception
     */

    private  boolean validBusqGeneral(String cadena){
        boolean flag;
        if(cadena.length()>=3){
            flag=true;
        }else{
            tableModel.clearTable();
            flag=false;
            FarmaUtility.showMessage(this, "Debe Ingresar minimo 3 caracteres", null);   
        }
        return flag;
         
    }
    
   

}
