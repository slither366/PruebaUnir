package admcentral.packs;


import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import admcentral.packs.reference.ConstantsPack;
import admcentral.packs.reference.DBPacks;
import admcentral.packs.reference.VariablesPacks;


import common.FarmaGridUtils;
import common.FarmaTableModel;

import common.FarmaUtility;

import common.FarmaVariables;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import venta.adm.productos.reference.ConstantsMaestrosProductos;
import venta.adm.productos.reference.DBMaestrosProductos;

import venta.modulo_venta.reference.VariablesModuloVentas;

//import java.util.Map;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMantDetPaqProd.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      22.09.2008   Creación<br>
 * <br>
 * @author  Javier Callo Quispe <br>
 * @version 1.0<br>
 *
 */

public class DlgMantDetPaqProd extends JDialog {

    private static final long serialVersionUID = 8888050004479960359L;
    
    private static final Log log = LogFactory.getLog(DlgMantDetPaqProd.class);

    Frame myParentFrame;
    boolean vFlagTeclaFx =  false;

    FarmaTableModel tableModel;
    
    private final int COL_CHECK = 0;
    private final int COL_COD = 1;
    private final int COL_DESC = 2;
    private final int COL_CANT = 5;
    private final int COL_PORC_DCTO = 6;
    private final int COL_VAL_FRAC = 7;

    private BorderLayout borderLayout1 = new BorderLayout();
    
    private JPanelWhite jContentPane = new JPanelWhite();


    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelWhite lblCantidad = new JLabelWhite();
    private JLabelWhite lblPorcDcto = new JLabelWhite();
    private JLabelWhite lblValFrac = new JLabelWhite();
    private JLabelWhite lblSimboloPorc = new JLabelWhite();

    /**campo del paquete producto a registrar
     */
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPorcDcto = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtValFrac = new JTextFieldSanSerif();
    private JScrollPane scpUnidad = new JScrollPane();
    private JTable tblUnidades = new JTable();
    
    FarmaTableModel tbmFracciones;
    String pCodProd ;
    private JLabel jLabel1 = new JLabel();
    private JLabel lblSubTotal = new JLabel();
    // **************************************************************************
    // Constructores
    // **************************************************************************

    DlgMantDetPaqProd() {
        this(null, "", false,"");
    }

    public DlgMantDetPaqProd(Frame parent, String title, boolean modal,String pCodProd) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.pCodProd = pCodProd;
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
        this.setSize(new Dimension(648, 226));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle paquete producto");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(546, 203));
        lblF11.setBounds(new Rectangle(15, 155, 105, 20));
        lblF11.setText("[F11] Aceptar");
        lblEsc.setBounds(new Rectangle(400, 160, 110, 20));
        lblEsc.setText("[Esc] Cerrar");

        lblCantidad.setText("Cantidad :");
        lblCantidad.setBounds(new Rectangle(300, 20, 70, 20));
        lblCantidad.setBackground(new Color(255, 130, 14));
        lblCantidad.setForeground(new Color(0, 99, 148));
        
        

        lblPorcDcto.setText("Porcentaje Descuento :");
        lblPorcDcto.setBounds(new Rectangle(300, 50, 135, 20));
        lblPorcDcto.setBackground(new Color(255, 130, 14));
        lblPorcDcto.setForeground(new Color(0, 99, 148));

        lblValFrac.setText("valor Fraccion :");
        lblValFrac.setBounds(new Rectangle(730, 80, 135, 20));
        lblValFrac.setBackground(new Color(255, 130, 14));
        lblValFrac.setForeground(new Color(0, 99, 148));

        lblSimboloPorc.setText("%");
        lblSimboloPorc.setBounds(new Rectangle(515, 50, 15, 20));
        lblSimboloPorc.setBackground(new Color(255, 130, 14));
        lblSimboloPorc.setForeground(new Color(0, 99, 148));


        txtCantidad.setBounds(new Rectangle(445, 20, 60, 20));
        txtCantidad.setFont(new Font("SansSerif", 0, 11));
        txtCantidad.setLengthText(3);
        txtCantidad.addKeyListener(new KeyAdapter() {

                    public void keyPressed(KeyEvent e) {
                    txtCantidad_keyPressed(e);
                }

                    public void keyReleased(KeyEvent e) {
                        //txtBuscar_keyReleased(e);
                    txtCantidad_keyReleased(e);
                }
                    
                    public void keyTyped(KeyEvent e) {
                        txtCantidad_keyTyped(e);
                    }
                    
                });

        txtPorcDcto.setBounds(new Rectangle(445, 50, 60, 20));
        txtPorcDcto.setFont(new Font("SansSerif", 0, 11));
        txtPorcDcto.setLengthText(6);
        txtPorcDcto.addKeyListener(new KeyAdapter() {

                    public void keyPressed(KeyEvent e) {
                        txtPorcDcto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtPorcDcto_keyTyped(e);
                    }

                public void keyReleased(KeyEvent e) {
                    txtPorcDcto_keyReleased(e);
                }
            });

        txtValFrac.setBounds(new Rectangle(875, 80, 60, 20));
        txtValFrac.setFont(new Font("SansSerif", 0, 11));
        txtValFrac.setLengthText(4);
        txtValFrac.addKeyListener(new KeyAdapter() {

                    public void keyPressed(KeyEvent e) {
                        txtValFrac_keyPressed(e);
                    }
                    
                    public void keyTyped(KeyEvent e) {
                        txtValFrac_keyTyped(e);
                    }
                });


        scpUnidad.setBounds(new Rectangle(15, 10, 275, 125));
        jLabel1.setText("Sub Total S/ : ");
        jLabel1.setBounds(new Rectangle(305, 90, 70, 20));
        jLabel1.setFont(new Font("SansSerif", 1, 10));
        jLabel1.setForeground(new Color(0, 99, 148));
        lblSubTotal.setText("99990.00");
        lblSubTotal.setBounds(new Rectangle(390, 90, 135, 25));
        lblSubTotal.setFont(new Font("SansSerif", 1, 12));
        lblSubTotal.setForeground(new Color(0, 99, 148));
        scpUnidad.getViewport().add(tblUnidades, null);
        jContentPane.add(lblSubTotal, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(scpUnidad, null);

        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(txtValFrac, null);
        jContentPane.add(lblValFrac, null);
        jContentPane.add(lblSimboloPorc, null);
        jContentPane.add(txtPorcDcto, null);
        jContentPane.add(lblPorcDcto, null);
        jContentPane.add(txtCantidad, null);
        jContentPane.add(lblCantidad, null);
        this.setContentPane(jContentPane);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
        if(VariablesPacks.ACCION_PROD_PAQUETE.equalsIgnoreCase(
               ConstantsPack.ACCION_PP_MODIF)){
            txtCantidad.setText(VariablesPacks.Vg_pp_Cantidad);
            txtPorcDcto.setText(VariablesPacks.Vg_pp_Porc_Dcto);
            txtValFrac.setText(VariablesPacks.Vg_pp_Val_Frac);
        }
        
        
        tbmFracciones =
                new FarmaTableModel(ConstantsPack.columnsListaFracciones, ConstantsPack.defaultValuesListaFracciones,
                                    0);
        FarmaUtility.initSimpleList(tblUnidades, tbmFracciones, ConstantsPack.columnsListaFracciones);
        
        try {
            DBPacks.getListaFracciones(tbmFracciones, pCodProd);
            if (tblUnidades.getRowCount() > 0) {
                FarmaGridUtils.showCell(tblUnidades, 0, 0);
                txtValFrac.setText(FarmaUtility.getValueFieldArrayList(tbmFracciones.data, 
                                                                       0,
                                                                       3));
            }
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblUnidades,new JTextField(),0);
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //System.out.println("PRECIONO TECLA ENTER EN  txtPorcDcto..");
            FarmaUtility.moveFocus(txtPorcDcto);
        } else {
            chkKeyPressed(e);
        }
    }
   
    private void txtPorcDcto_keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //System.out.println("PRECIONO TECLA ENTER EN  txtPorcDcto..");
            try {
                String auxPorcDcto = txtPorcDcto.getText().trim();
                int ind = auxPorcDcto.indexOf(".");                
                if( ind != -1 ){
                    auxPorcDcto +="000";
                    auxPorcDcto = auxPorcDcto.substring(0,ind+4);
                }                
                txtPorcDcto.setText( FarmaUtility.formatNumber(Double.parseDouble(auxPorcDcto) , "##0.000") );    
                FarmaUtility.moveFocus(txtCantidad);
            } catch (Exception ex) {
                FarmaUtility.showMessage(this, "Porcentaje de Descuento Incorrecto!",txtPorcDcto);
            } finally {
            }
            
        } else {
            chkKeyPressed(e);
        }
    }
    
        
    private void txtValFrac_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //System.out.println("PRECIONO TECLA ENTER EN  txtPorcDcto..");
            FarmaUtility.moveFocus(txtCantidad);
        } else {
            chkKeyPressed(e);
        }
    }
    
    /*** validacion de campos al teclear***/
    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void txtPorcDcto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPorcDcto, e);
    }

    private void txtValFrac_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtValFrac, e);
    }
    /*** FIN validacion de campos al teclear***/


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !vFlagTeclaFx) {
          vFlagTeclaFx = true;
            this.setVisible(false);
            this.dispose();        
          vFlagTeclaFx = false;
      } else if (e.getKeyCode() == KeyEvent.VK_F11 && !vFlagTeclaFx) {
          vFlagTeclaFx = true;
        if (validarDatos()) {
            if (VariablesPacks.ACCION_PROD_PAQUETE.equalsIgnoreCase(
                    ConstantsPack.ACCION_PP_CREAR)) {
                /**seteando valores ingresados hacia la tabla**/
                VariablesPacks.tblModelListaPaquete.setValueAt(
                    new Boolean(true),VariablesPacks.RowProd,COL_CHECK);            
                VariablesPacks.tblModelListaPaquete.setValueAt(
                    txtCantidad.getText().trim().toString(),VariablesPacks.RowProd,COL_CANT);            
                VariablesPacks.tblModelListaPaquete.setValueAt(
                    txtPorcDcto.getText().trim().toString(),VariablesPacks.RowProd,COL_PORC_DCTO);            
                VariablesPacks.tblModelListaPaquete.setValueAt(
                    txtValFrac.getText().trim().toString(),VariablesPacks.RowProd,COL_VAL_FRAC);
                
                /**agregando producto a la lista de productos**/
                ArrayList lProd = new ArrayList(5);                
                lProd.add(ConstantsPack.COL_COD_PP,
                          VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_COD));
                lProd.add(ConstantsPack.COL_DESC_PP,
                          VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_DESC));
                lProd.add(ConstantsPack.COL_CANT_PP,
                          VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_CANT));
                lProd.add(ConstantsPack.COL_PORC_DCTO_PP,
                          VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_PORC_DCTO));
                lProd.add(ConstantsPack.COL_VAL_FRAC_PP,
                          VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_VAL_FRAC));
                
                if ( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_1) ) {                
                    
                    /**para agregar el ultimo campo la accion a realizar en la bd**/
                    if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR)){
                        boolean flagExiste = false;//flag que indica si el producto se encuentra en el lista auxiliar
                        for(int i=0;i<VariablesPacks.listaPaquete1.size();i++){                            
                            ArrayList laux = (ArrayList)VariablesPacks.listaPaquete1.get(i);
                            /** buscando si el producto ya existe en la lista auxiliar de productos **/
                            if(lProd.get(ConstantsPack.COL_COD_PP).toString().equalsIgnoreCase( 
                                   laux.get(ConstantsPack.COL_COD_PP).toString())){
                                if ( laux.get(ConstantsPack.COL_ACCION_PP).toString().equalsIgnoreCase(
                                       ConstantsPack.ACCION_ELIMINAR_PROD_PAQ) ) {//si esta en el estado eliminar tonces pasa a modificar                                                                        
                                    
                                    lProd.set(ConstantsPack.COL_ACCION_PP,ConstantsPack.ACCION_MODIFICAR_PROD_PAQ);                                                                        
                                    VariablesPacks.listaPaquete1.set(i,lProd);
                                    
                                }
                                flagExiste = true;
                                break;
                            }
                            
                        }
                        
                        if( !flagExiste ){
                            lProd.add(5,ConstantsPack.ACCION_CREAR_PROD_PAQ);
                            VariablesPacks.listaPaquete1.add(lProd);
                        }
                        
                    }else if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_CREAR)){
                        lProd.add(5,ConstantsPack.ACCION_CREAR_PROD_PAQ);
                        VariablesPacks.listaPaquete1.add(lProd);
                    }
                }
                else if( VariablesPacks.Paquete.equalsIgnoreCase(ConstantsPack.PAQUETE_2) ){                
                    /**para agregar el ultimo campo la accion a realizar en la bd**/
                    if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR)){
                        boolean flagExiste = false;//flag que indica si el producto se encuentra en el lista auxiliar
                        for(int i=0;i<VariablesPacks.listaPaquete2.size();i++){
                            
                            ArrayList laux = (ArrayList)VariablesPacks.listaPaquete2.get(i);
                            /** buscando si el producto ya existe en la lista auxiliar de productos **/
                            if(lProd.get(ConstantsPack.COL_COD_PP).toString().equalsIgnoreCase( 
                                   laux.get(ConstantsPack.COL_COD_PP).toString())){
                                if ( laux.get(ConstantsPack.COL_ACCION_PP).toString().equalsIgnoreCase(
                                       ConstantsPack.ACCION_ELIMINAR_PROD_PAQ) ) {//si esta en el estado eliminar tonces pasa a modificar                                                                        
                                    lProd.set(ConstantsPack.COL_ACCION_PP,ConstantsPack.ACCION_MODIFICAR_PROD_PAQ);                                    
                                    
                                    VariablesPacks.listaPaquete2.set(i,lProd);                                    
                                }
                                flagExiste = true;
                                break;
                            }
                            
                        }
                        
                        if( !flagExiste ){
                            lProd.add(5,ConstantsPack.ACCION_CREAR_PROD_PAQ);
                            VariablesPacks.listaPaquete2.add(lProd);
                        }
                        
                    }else if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_CREAR)){
                        lProd.add(5,ConstantsPack.ACCION_CREAR_PROD_PAQ);
                        VariablesPacks.listaPaquete2.add(lProd);
                    }
                }          
            }
            else if( VariablesPacks.ACCION_PROD_PAQUETE.equalsIgnoreCase(
                    ConstantsPack.ACCION_PP_MODIF ) ) {
                
                if(VariablesPacks.Paquete.equalsIgnoreCase(
                       ConstantsPack.PAQUETE_1) ) {
                    modificarProductoPaquete(VariablesPacks.listaPaquete1);
                }else if(VariablesPacks.Paquete.equalsIgnoreCase(
                       ConstantsPack.PAQUETE_2) ){
                    modificarProductoPaquete(VariablesPacks.listaPaquete2);
                }
            }
            cerrarVentana(true);
        }
          vFlagTeclaFx = false;
      }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    
    /**
     * metodo encargado de cerrar la ventana actual
     * @author JCALLO
     * @since 16.09.2008
     */
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    /**
     * metodo encargado Modificar datos del paquete producto
     * @author JCALLO
     * @since 16.09.2008
     */
    private void modificarProductoPaquete(List listaPaquete) {
        VariablesPacks.tblModelListaPaquete.setValueAt(
            txtCantidad.getText().trim().toString(),VariablesPacks.RowProd,COL_CANT);            
        VariablesPacks.tblModelListaPaquete.setValueAt(
            txtPorcDcto.getText().trim().toString(),VariablesPacks.RowProd,COL_PORC_DCTO);            
        VariablesPacks.tblModelListaPaquete.setValueAt(
            txtValFrac.getText().trim().toString(),VariablesPacks.RowProd,COL_VAL_FRAC);
                            
        ArrayList lProd = new ArrayList(5);                    
        lProd.add(ConstantsPack.COL_COD_PP,VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_COD));
        lProd.add(ConstantsPack.COL_DESC_PP,VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_DESC));
        lProd.add(ConstantsPack.COL_CANT_PP,VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_CANT));
        lProd.add(ConstantsPack.COL_PORC_DCTO_PP,VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_PORC_DCTO));
        lProd.add(ConstantsPack.COL_VAL_FRAC_PP,VariablesPacks.tblModelListaPaquete.getValueAt(VariablesPacks.RowProd,COL_VAL_FRAC));
        
        if( VariablesPacks.Vg_pp_Accion.equalsIgnoreCase(
                ConstantsPack.ACCION_NINGUNA_PROD_PAQ) ){
            lProd.add(ConstantsPack.COL_ACCION_PP,ConstantsPack.ACCION_MODIFICAR_PROD_PAQ);
        } else if( VariablesPacks.Vg_pp_Accion.equalsIgnoreCase(
                ConstantsPack.ACCION_MODIFICAR_PROD_PAQ ) ) {
            lProd.add(ConstantsPack.COL_ACCION_PP,ConstantsPack.ACCION_MODIFICAR_PROD_PAQ);
        } else if( VariablesPacks.Vg_pp_Accion.equalsIgnoreCase(
                ConstantsPack.ACCION_CREAR_PROD_PAQ ) ) {
            lProd.add(ConstantsPack.COL_ACCION_PP,ConstantsPack.ACCION_CREAR_PROD_PAQ);
        }
        
        listaPaquete.set(VariablesPacks.Vg_pp_indice,lProd);
    }
   
    /**
     * metodo encargado validar datos de ingreso
     * @author JCALLO
     * @since 16.09.2008
     */
    private boolean validarDatos() {
        boolean retorno = true;

        if (txtCantidad.getText().trim().toString().length() < 1) {
            retorno = false;
            FarmaUtility.showMessage(this, " Especificar la Cantidad !", 
                                     txtCantidad);
        } else if (txtPorcDcto.getText().trim().toString().length() < 1) {
            retorno = false;
            FarmaUtility.showMessage(this, 
                                     " Especificar el procentaje de Descuento !", 
                                     txtPorcDcto);
        } else if (txtValFrac.getText().trim().toString().length() < 1) {
            retorno = false;
            FarmaUtility.showMessage(this, " Especificar Valor Fraccion !", 
                                     txtValFrac);
        } else if ( 
            ( new Integer(txtCantidad.getText()).intValue() ) < 1 ) {
            retorno = false;
            FarmaUtility.showMessage(this, " Cantidad debe ser mayor a CERO !", 
                                     txtCantidad);
        } else if ( Double.parseDouble(txtPorcDcto.getText() ) > 100.000 ) {
            retorno = false;
            FarmaUtility.showMessage(this, " Porcentaje Descuento debe ser menor ò igual al 100% !", 
                                     txtPorcDcto);
        } else if ( 
            ( new Integer(txtValFrac.getText()).intValue() ) < 1 ) {
            retorno = false;
            FarmaUtility.showMessage(this, " Valor Fraccion debe ser mayor a CERO.!", 
                                     txtValFrac);
        }

        return retorno;
    }
    
    // opera el sub total
    private void operaprecioSubTotal() {
        int pos = tblUnidades.getSelectedRow();
        
        if(pos>=0){
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText());
                if(cantidad>0){
                
                double precioOriginal = 0.0;
                
                    precioOriginal = FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tbmFracciones.data,
                                                                         pos,
                                                                         2));
               
                
                double descuento = (100 - FarmaUtility.getDecimalNumber(txtPorcDcto.getText().trim())); 

                double pSubTotal = (cantidad * precioOriginal * descuento/100);

                lblSubTotal.setText(
                                        //getNumeroTexto(pSubTotal) + ""
                                        //redondearDecimales(pSubTotal,2)+""
                                        FarmaUtility.getDecimalNumberRedondeado(pSubTotal)+""
                    );
                }
                else{
                    lblSubTotal.setText("");
                    //txtPrecioFinal.setText("");
                }
            } catch (Exception nfe) {
                lblSubTotal.setText("");
                //txtPrecioFinal.setText("");
                // TODO: Add catch code
                //nfe.printStackTrace();
            }
        }
    }

    private void txtCantidad_keyReleased(KeyEvent e) {
        operaprecioSubTotal();
    }

    private void txtPorcDcto_keyReleased(KeyEvent e) {
        operaprecioSubTotal();
    }
}
