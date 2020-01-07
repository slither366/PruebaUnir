package cilator.mantenimiento;

import cilator.mantenimiento.reference.ConstantsRecetas;
import cilator.mantenimiento.reference.ModoOperacionRecetaComponente;
import cilator.mantenimiento.reference.VariablesRecetas;

import common.FarmaLoadCVL;

import common.FarmaSearch;
import common.FarmaUtility;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import common.FarmaVariables;
public class DlgMantRecetasComponente extends JDialog {    
    
    private static final Log log = LogFactory.getLog(DlgMantRecetasComponente.class);
    private Frame myParentFrame;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelFunction lblAgrega = new JLabelFunction();
    private JLabelFunction lblEscape = new JLabelFunction();
    
    private JPanelHeader pnlProducto = new JPanelHeader();
    private JButtonLabel lblProducto = new JButtonLabel();
    private JButtonLabel lblComponente = new JButtonLabel();
    
    private JTextFieldSanSerif txtCodigoProducto = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescripcionProducto = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodigoComponente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescripcionComponente = new JTextFieldSanSerif();

    private JButtonLabel lblCantidadReceta = new JButtonLabel();
    private JButtonLabel lblIndVtaStock = new JButtonLabel();
    private JButtonLabel lblValorFraccion = new JButtonLabel();
    
    private JTextFieldSanSerif txtCantidadReceta = new JTextFieldSanSerif();
    private JComboBox cmbIndVtaStock = new JComboBox();
    private JTextFieldSanSerif txtValorFraccion = new JTextFieldSanSerif();
    
    private boolean entradasSonValidas = false;
    
    public DlgMantRecetasComponente() {
        this(null, "", false);
    }

    public DlgMantRecetasComponente(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(451, 271));
        this.getContentPane().setLayout(borderLayout1);        
        this.setTitle("Mantenimiento de Componente de Receta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened();
            }
            public void windowClosing(WindowEvent e) {
                this_windowClosing();
            }
        });
        
        pnlProducto.setBounds(new Rectangle(10, 10, 415, 70));
        pnlProducto.setBackground(new Color(0, 107, 165));
        pnlProducto.setLayout(null);
        
        lblProducto.setText("Receta");
        lblProducto.setBounds(new Rectangle(15, 10, 45, 20));
        lblProducto.setFocusable(false);
        
        txtCodigoProducto.setBounds(new Rectangle(95, 10, 65, 20));
        txtCodigoProducto.setEditable(false);
        txtCodigoProducto.setFocusable(false);        
        
        txtDescripcionProducto.setBounds(new Rectangle(165, 10, 225, 20));
        txtDescripcionProducto.setEditable(false);
        txtDescripcionProducto.setFocusable(false);        
        
        lblComponente.setText("Componente");
        lblComponente.setBounds(new Rectangle(15, 35, 85, 20));        
        lblComponente.setFocusable(false);
        
        txtCodigoComponente.setBounds(new Rectangle(95, 35, 65, 20));
        txtCodigoComponente.setEditable(false);
        txtCodigoComponente.setFocusable(false);        
        
        txtDescripcionComponente.setBounds(new Rectangle(165, 35, 225, 20));
        txtDescripcionComponente.setEditable(false);
        txtDescripcionComponente.setFocusable(false);        

        pnlProducto.add(lblProducto, null);
        pnlProducto.add(txtDescripcionProducto, null);
        pnlProducto.add(txtCodigoProducto, null);
        pnlProducto.add(lblComponente, null);
        pnlProducto.add(txtDescripcionComponente, null);
        pnlProducto.add(txtCodigoComponente, null);


        lblCantidadReceta.setText("Cantidad");
        lblCantidadReceta.setBounds(new Rectangle(30, 105, 60, 20));
        lblCantidadReceta.setForeground(SystemColor.windowText);
        lblCantidadReceta.setFocusable(false);
        
        lblIndVtaStock.setText("Ind. Venta");
        lblIndVtaStock.setBounds(new Rectangle(30, 135, 60, 20));
        lblIndVtaStock.setForeground(SystemColor.windowText);
        lblIndVtaStock.setFocusable(false);
        
        lblValorFraccion.setText("Valor Fraccion");
        lblValorFraccion.setBounds(new Rectangle(205, 105, 85, 20));
        lblValorFraccion.setForeground(SystemColor.windowText);
        lblValorFraccion.setFocusable(false);
        
        txtCantidadReceta.setBounds(new Rectangle(90, 105, 90, 20));
        txtCantidadReceta.setLengthText(6);
        txtCantidadReceta.setNextFocusableComponent(cmbIndVtaStock);
        txtCantidadReceta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtCantidadReceta_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCantidadReceta_keyTyped(e);
                }
            });

        txtCantidadReceta.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    txtCantidadReceta_focusGained(e);
                }
            });
        
        cmbIndVtaStock.setBounds(new Rectangle(90, 135, 130, 20));


        cmbIndVtaStock.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    cmbIndVtaStock_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    cmbIndVtaStock_keyPressed(e);
                }
            });
        txtValorFraccion.setBounds(new Rectangle(295, 105, 110, 20));
        txtValorFraccion.setLengthText(4);
        txtValorFraccion.setEditable(false);


        txtValorFraccion.setFocusable(false);
        lblAgrega.setText("[F11] Aceptar");
        lblAgrega.setBounds(new Rectangle(10, 205, 105, 20));
        lblAgrega.setFocusable(false);
        lblEscape.setText("[ESC] Cerrar");
        lblEscape.setBounds(new Rectangle(320, 205, 105, 20));

        lblEscape.setFocusable(false);
        jContentPane.add(pnlProducto, null);
        jContentPane.add(lblAgrega, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(txtValorFraccion, null);
        jContentPane.add(lblValorFraccion, null);
        jContentPane.add(cmbIndVtaStock, null);
        jContentPane.add(lblIndVtaStock, null);
        jContentPane.add(txtCantidadReceta, null);
        jContentPane.add(lblCantidadReceta, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        cargaIndVtaStock();
        pintarReferencias();        
        establecerModo();
    }
    
    private void cargaIndVtaStock(){
        FarmaLoadCVL.loadCVLfromArrays(cmbIndVtaStock, "cmbIndVtaStock", 
                                   ConstantsRecetas.INDICADOR_TIPOVTASTOCK_CODIGO, 
                                   ConstantsRecetas.INDICADOR_TIPOVTASTOCK_DESCRIPCION, 
                                   true);
        FarmaLoadCVL.setSelectedValueInComboBox(cmbIndVtaStock, "cmbIndVtaStock", "M");
    }
     
    private void establecerModo(){        
        if(VariablesRecetas.modoOperacionRecetaComponente == ModoOperacionRecetaComponente.REGISTRO){
             this.setTitle("Registro de Componente de Receta");
         }else if(VariablesRecetas.modoOperacionRecetaComponente == ModoOperacionRecetaComponente.EDICION){
             this.setTitle("Actualización de Componente de Receta");
         }
     }
    
    private void pintarReferencias(){        
        txtCodigoProducto.setText(VariablesRecetas.componenteRecetaSeleccionado.getReceta().getCodProducto());
        txtDescripcionProducto.setText(VariablesRecetas.componenteRecetaSeleccionado.getReceta().getDescProducto());
        txtCodigoComponente.setText(VariablesRecetas.componenteRecetaSeleccionado.getCodComponente());
        txtDescripcionComponente.setText(VariablesRecetas.componenteRecetaSeleccionado.getDescComponente());
        txtCantidadReceta.setText(""+VariablesRecetas.componenteRecetaSeleccionado.getCantidad());
        txtValorFraccion.setText(""+VariablesRecetas.componenteRecetaSeleccionado.getValorFraccion());
        if(VariablesRecetas.componenteRecetaSeleccionado.getIndVtaStock()!=null)
            FarmaLoadCVL.setSelectedValueInComboBox(cmbIndVtaStock, "cmbIndVtaStock", 
                                                    VariablesRecetas
                                                    .componenteRecetaSeleccionado
                                                    .getIndVtaStock()
                                                    .trim());
    }
/*
    /* ********************************************************************** */
    /*                            METODO DE INICIALIZACION                    */
    /* ********************************************************************** */

    
 
     
    /*
    /* ********************************************************************** */
    /*                            METODOS DE EVENTOS                          */
    /* ********************************************************************** */
    

   
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
            case KeyEvent.VK_F11: registrarComponente(); break;
            case KeyEvent.VK_ESCAPE: cerrarVentana(false); break;
            default: ;
        }
    }
    
    private void pasarDatosAComponente(){
        int cantidad = Integer.parseInt(txtCantidadReceta.getText());
        int valorFraccion = Integer.parseInt(txtValorFraccion.getText());              
        String indVtaStock = FarmaLoadCVL.getCVLCode("cmbIndVtaStock", cmbIndVtaStock.getSelectedIndex());
        VariablesRecetas.componenteRecetaSeleccionado.setCantidad(cantidad);        
        VariablesRecetas.componenteRecetaSeleccionado.setValorFraccion(valorFraccion);
        VariablesRecetas.componenteRecetaSeleccionado.setIndVtaStock(indVtaStock);
    }
    
    private void registrarComponente(){
        validarEntradas();
        if(entradasSonValidas){
            if(VariablesRecetas.modoOperacionRecetaComponente == ModoOperacionRecetaComponente.REGISTRO){
                agregar();
            }else if(VariablesRecetas.modoOperacionRecetaComponente == ModoOperacionRecetaComponente.EDICION){
                editar();
            }
            log.info(VariablesRecetas.componenteRecetaSeleccionado);
        }
    }
    
    private void validarEntradas(){
        entradasSonValidas = false;
        String cant = txtCantidadReceta.getText();
        String vf = txtValorFraccion.getText();
        if(cant.isEmpty() || vf.isEmpty()){
            FarmaUtility.showMessage(this, "Debe ingresar cantidad y Valor de fraccion.", null);
            return;
        }
        try{
            if(Integer.parseInt(cant)>0 && Integer.parseInt(vf)>0){
                entradasSonValidas = true;
            }else{
                FarmaUtility.showMessage(this, "Cantidad y Valor de Fraccion deben ser mayores a O.", null);
            }    
        }catch(NumberFormatException e){
            FarmaUtility.showMessage(this, "Debe ingresar sólo dígitos.", null);
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
    }
    /* ********************************************************************** */
    /*                    METODOS DE LOGICA DE NEGOCIO                         */
    /* *********************************************************************** */

    private void txtCantidadReceta_keyTyped(KeyEvent e) {
        if(!Character.isDigit(e.getKeyChar()))
            e.consume();
    }



    private void txtCantidadReceta_keyPressed(KeyEvent e) {
        if(e.getKeyChar() == e.VK_ENTER){
            FarmaUtility.moveFocus(cmbIndVtaStock);
        }else{
            chkKeyPressed(e);
        }        
    }

    

    private void agregar() {
        boolean rpta = true;
        if(rpta){
            agregarComponente();
        }
    }
    
    private void agregarComponente(){
        pasarDatosAComponente();
        VariablesRecetas.admReceta.agregarComponente(VariablesRecetas.componenteRecetaSeleccionado);
        cerrarVentana(true);
    }
    
    private void editar() {
        boolean rpta = true;
        if(rpta){
            editarComponente();
        }        
    }
    
    private void editarComponente(){
        pasarDatosAComponente();
        VariablesRecetas.admReceta.editarComponente(VariablesRecetas.componenteRecetaSeleccionado);
        cerrarVentana(true); 
    }

    private void txtCantidadReceta_focusGained(FocusEvent e) {
        txtCantidadReceta.selectAll();
    }


    private void cmbIndVtaStock_keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            e.consume();
        }else if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
          if(cmbIndVtaStock.isPopupVisible()) 
            cmbIndVtaStock.setPopupVisible(false);
          else 
            chkKeyPressed(e);
        }else{
          chkKeyPressed(e);
        }
    }

    private void cmbIndVtaStock_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCantidadReceta);
        }else{
          chkKeyPressed(e);
        }
    }
}
