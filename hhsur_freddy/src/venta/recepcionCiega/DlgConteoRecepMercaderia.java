package venta.recepcionCiega;

import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

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

import javax.swing.JDialog;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import common.DlgLogin;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaSearch;
import common.FarmaTableModel;

import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.inventariodiario.reference.ConstantsInvDiario;
import venta.recepcionCiega.reference.ConstantsRecepCiega;
import venta.recepcionCiega.reference.DBRecepCiega;
import venta.recepcionCiega.reference.UtilityRecepCiega;
import venta.recepcionCiega.reference.VariablesRecepCiega;
import venta.reference.ConstantsPtoVenta;
import static venta.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.modulo_venta.DlgListaProductos;
import venta.modulo_venta.reference.DBModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgConteoRecepMercaderia extends JDialog {
    private Frame myParentFrame;
        
    FarmaTableModel tableModelProductosConteo;
    
    private JTable tblRelacionProductosConteo = new JTable();
    private JTable myJTable;
    
    private static final Logger log = LoggerFactory.getLogger(DlgConteoRecepMercaderia.class);
    
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButtonFunction jButtonFunction1 = new JButtonFunction();
    //private JPanelTitle jPanelTitleBuscar = new JPanelTitle();
    private JPanelHeader jPanelTitleBuscar = new JPanelHeader();    
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JScrollPane scrProductos = new JScrollPane();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelTitle jPanelTitle3 = new JPanelTitle();
    private JLabelFunction lblCambiarCantidad = new JLabelFunction();
    private JLabelFunction lblEliminar = new JLabelFunction();
    private JLabelFunction lblGuardarConteo = new JLabelFunction();
    private JLabelFunction lblFinalizar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JButtonLabel btnProductos = new JButtonLabel();
    
    //VALORES COLUMNAS
    private static final int Col_Desc_Prod = 0;
    private static final int Col_Unidad = 1;
    private static final int Col_Cant = 2;
    private static final int Col_Lote1 = 3;
    private static final int Col_Fec1 = 4;
    private static final int Col_Lote2 = 5;
    private static final int Col_Fec2 = 6;        
    private static final int Col_Aux_Conteo = 7;    
    private static final int Col_Cod_Barra = 8;
    private static final int Col_Nro_Bloque = 9;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgConteoRecepMercaderia() {
        this(null, "", false);
    }

    public DlgConteoRecepMercaderia(Frame parent, String title, 
                                    boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(643, 435));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
        this.setTitle("Lectura de Productos");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                    public void windowClosing(WindowEvent e)
                    {
                      this_windowClosing(e);
                    }
                });
        tblRelacionProductosConteo.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        tblRelacionProductosConteo_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        tblRelacionProductosConteo_keyPressed(e);
                    }
                });
        jPanelTitleBuscar.setBounds(new Rectangle(5, 5, 625, 40));
        btnBuscar.setText("Ingresar C�digo Barra:");
        btnBuscar.setMnemonic('i');
        btnBuscar.setBounds(new Rectangle(15, 10, 135, 20));
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        
        txtBuscar.setBounds(new Rectangle(165, 10, 170, 20));
        txtBuscar.setLengthText(15);        
        //**--
        txtBuscar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtBuscar_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtBuscar_keyReleased(e);
                    }
                    public void keyTyped(KeyEvent e) {
                        txtBuscar_keyTyped(e);
                    }
                });
        //---
        txtBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //|txtBuscar_actionPerformed(e);
                    }
                });
        jPanelTitle2.setBounds(new Rectangle(5, 50, 625, 20));
        scrProductos.setBounds(new Rectangle(5, 70, 625, 260));
        jPanelTitle3.setBounds(new Rectangle(5, 330, 625, 20));
        lblCambiarCantidad.setBounds(new Rectangle(270, 360, 150, 20));
        lblCambiarCantidad.setText("[ + ] Modificar Cantidad");
        lblCambiarCantidad.setVisible(true);
        lblEliminar.setBounds(new Rectangle(280, 385, 95, 20));
        lblEliminar.setText("[ F5 ] Eliminar");
        lblEliminar.setVisible(false);
        lblGuardarConteo.setBounds(new Rectangle(145, 385, 130, 20));
        lblGuardarConteo.setText("[ F1 ] Guardar Conteo");
        lblGuardarConteo.setVisible(false);
        lblFinalizar.setBounds(new Rectangle(430, 360, 105, 20));
        lblFinalizar.setText("[ F11 ] Finalizar");
        lblFinalizar.setVisible(true);
        lblSalir.setBounds(new Rectangle(545, 360, 85, 20));
        lblSalir.setText("[ Esc ] Salir");
        lblSalir.setVisible(true);


        btnProductos.setText("Productos");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(5, 0, 75, 20));
        btnProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProductos_actionPerformed(e);
                    }
                });
        jPanelTitleBuscar.add(txtBuscar, null);
        jPanelTitleBuscar.add(btnBuscar, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblFinalizar, null);
        jContentPane.add(lblGuardarConteo, null);
        jContentPane.add(lblEliminar, null);
        jContentPane.add(lblCambiarCantidad, null);
        jContentPane.add(jPanelTitle3, null);
        scrProductos.getViewport().add(tblRelacionProductosConteo, null);
        jContentPane.add(scrProductos, null);

        jPanelTitle2.add(btnProductos, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(jPanelTitleBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaProdConteo();
        //cargaLogin();
       /* if(cargaLoginVendedor()){
            log.error("principio");

        }        */  
               
        log.error("principio no");
        //Recibe Nro Recepcion GUIA
        VariablesRecepCiega.vSecRecepGuia = VariablesRecepCiega.vNumIngreso;
        VariablesRecepCiega.vNroBloque = "";
        obtieneNroBloque();
        //ACTUALIZAR NRO BLOQUE
        
        VariablesRecepCiega.vDestEmailCodBarraNoFound = getDestConteoNoEncontrado();
        /*
        if(validaIngresoConteo(VariablesRecepCiega.vSecRecepGuia)){
          updateEstadoRecepMercaderia(ConstantsRecepCiega.EstadoConteo);
          FarmaUtility.aceptarTransaccion();          
        }
        else{
          cerrarVentana(false);
        }
        */
       
    }
    

    private void initTableListaProdConteo() {
        tableModelProductosConteo = 
                new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosConteo, 
                                    ConstantsRecepCiega.defaultValuesListaProductosConteo, 
                                    0);
        FarmaUtility.initSimpleList(tblRelacionProductosConteo, 
                                    tableModelProductosConteo, 
                                    ConstantsRecepCiega.columnsListaProductosConteo);
        //cargaListaProductosXLaboratorio();
        
    }
    
    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
        VariablesRecepCiega.vIndFocoTablaConteo = false;
    }

    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        
        if(!validaIngresoConteo(VariablesRecepCiega.vSecRecepGuia)){
            FarmaUtility.showMessage(this,"Ud no puede seguir contando!\n" +
                "El conteo de esta entrega ya ha sido Finalizada.",null);
            cerrarVentana(false);
        }
            
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      VariablesRecepCiega.vNroBloque = "";
      this.setVisible(false);
      this.dispose();
    }
    
   
    private void chkKeyPressed(KeyEvent e) {
        
        //if(e.getKeyCode() == KeyEvent.VK_ENTER)
        // dubilluz - 04.12.2009
        /*
         * if(e.getKeyChar() == '+')
        {
            //if(VariablesRecepCiega.vIndFocoTablaConteo){   
            log.debug("Apunta a tblRelacionProductosConteo");
            
            log.error("TABLE verifica");
            muestraInfoProd();
            //}
        }
        else         
        */
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                boolean mensSalida = componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro que desea Salir?"
                                               );        
                if(mensSalida){
                try {
                    //Limpiar Variables
                    VariablesRecepCiega.vNroBloque = "";
                    VariablesRecepCiega.contadorFila = 0;
                    VariablesRecepCiega.vLastCodBarra = "";
                    VariablesRecepCiega.vAuxSecProd = "";
                    VariablesRecepCiega.vAuxCodBarra = "";
                    VariablesRecepCiega.vSecConteo = "";
                    VariablesRecepCiega.vLastSecProd = "";
                    VariablesRecepCiega.vIndModificoCantActivo = false;
                    //Validar si cambia a estado revisado ya no cambia a espera                  
                    updateEstadoRecepMercaderia(ConstantsRecepCiega.EstadoEspera);
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(false);
                } catch (SQLException s) {
                      FarmaUtility.liberarTransaccion();
                      log.error("",s);
                      FarmaUtility.showMessage(this,"Error al dar Escape.",null);
                  }
                }
        }        
        else if(venta.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            pFinalizaConteo();            
        }
    }
    
    private void txtBuscar_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
        //FarmaUtility.admitirSoloDigitos(e,txtBuscar,0,this);
    }
    
    private void txtBuscar_keyPressed(KeyEvent e) {
    //FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductosConteo, 
      //                                        txtBuscar, 1);
    //Validar si ingresa nuevo producto
    
    VariablesRecepCiega.vIndFocoTablaConteo = true;
    FarmaGridUtils.aceptarTeclaPresionada(e,tblRelacionProductosConteo,null,0);
        
    //valida el codBarra
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
        //ERIOS 03.07.2013 Limpia la caja de texto        
        limpiaCadenaAlfanumerica(txtBuscar);
        //dubilluz - 04.12.2009
       // if(!VariablesRecepCiega.vIndFocoTablaConteo){
           e.consume();
           
            if (txtBuscar.getText().trim().length()>15){
                FarmaUtility.showMessage(this,"El c�digo de barra ingresado\nsupera el tama�o l�mite.\n�Verifique!.",txtBuscar);
            }
            else{
                log.debug("Ahora SI");
                   
                   String tmpCodBarra = txtBuscar.getText();
                   if ( tmpCodBarra.trim().length()==0 ){               
                       FarmaUtility.showMessage(this,"Ingrese C�digo de Barra.",txtBuscar);
                       return;
                   }
                   else if(tmpCodBarra.trim().length()>6){ 
                       //if(FarmaUtility.isInteger(tmpCodBarra.trim())){
                           ingresaProducto(tmpCodBarra);
                       //}
                       //else{                       
                        //   FarmaUtility.showMessage(this,"S�lo se permiten n�meros.",txtBuscar);
                      // }
                   }
                   else{
                       FarmaUtility.showMessage(this,"Ingrese C�digo de Barra Correcto.",txtBuscar);            
                       FarmaUtility.moveFocus(txtBuscar);
                   }
            }
            
            
            
               
               
           
      // }
               
    } 
    /*
     * dubilluz  - 04.12.2009
     * else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
        VariablesRecepCiega.vIndFocoTablaConteo = true;
        FarmaUtility.moveFocus(tblRelacionProductosConteo);        
    }
    */
        chkKeyPressed(e);
    }    

    
    private void chkKeyReleased(KeyEvent e) {

        FarmaUtility.admitirDigitos(txtBuscar,e);
    }
    
    private boolean cargaLogin()
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        //dlgLogin.setRolUsuario(FarmaConstants.ROL_AUDITORIA);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_CAJERO);  
        dlgLogin.setVisible(true);
        /*FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;*/
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        FarmaVariables.vAceptar = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return FarmaVariables.vAceptar;
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionProductosConteo);
        VariablesRecepCiega.vIndFocoTablaConteo = true;
    }
       
    public void mostrarIngresoCantPrimerConteo(){
        DlgIngresoCantPrimerConteo dlgIngresoCant = new DlgIngresoCantPrimerConteo(myParentFrame,"",true);                
        dlgIngresoCant.setVisible(true);
    }
    private void ingresaProducto(String vCodBarra) {
        VariablesRecepCiega.vIndModificarCant = false;
        VariablesRecepCiega.vIndModificoCantActivo = false;
        //Aqui asignar NRO RECEPCION GUIA
        //VariablesRecepCiega.vSecRecepGuia = "0000000003";
        //seteo variables en N
        VariablesRecepCiega.vIndDeteriorado = "N";
        VariablesRecepCiega.vIndFueraLote = "N";
        VariablesRecepCiega.vIndNoFound = "N";
        log.debug("indNoFound2.1: " + 
                           VariablesRecepCiega.vIndNoFound);
        //VariablesRecepCiega.vTempCodBarra = vCodBarra;  no se utiliza

        validaCodBarra(vCodBarra);
        log.debug("indNoFound2.2: " + 
                           VariablesRecepCiega.vIndNoFound);

        //VariablesRecepCiega.vIndModificarCant = false;
        //JMIRANDA 25.11.09 Se comenta para ingreso automatico
        VariablesRecepCiega.vLastCant = "1";
        //Ingreso Cantidad del Codigo de Barra Barrido           
        //mostrarIngresoCantPrimerConteo();
        try {
            VariablesRecepCiega.vIndAgregaConteo = true;
            if (VariablesRecepCiega.vIndAgregaConteo) {
                VariablesRecepCiega.contadorFila++;
                //almaceno AuxConteo 
                log.debug("contadorFila Antes: " + 
                          VariablesRecepCiega.contadorFila);
                VariablesRecepCiega.vLastSecProd = 
                        String.valueOf(VariablesRecepCiega.contadorFila);
                log.debug("contadorFila: " + VariablesRecepCiega.contadorFila);
                log.debug("LastSecProd: " + VariablesRecepCiega.vLastSecProd);
                //Almaceno codBarra IndNoFound 
                //inserto a BD         
                DBRecepCiega.insertAuxConteo(VariablesRecepCiega.vSecRecepGuia, 
                                             VariablesRecepCiega.contadorFila, 
                                             VariablesRecepCiega.vLastCodBarra, 
                                             VariablesRecepCiega.vLastCant, 
                                             VariablesRecepCiega.vIndDeteriorado, 
                                             VariablesRecepCiega.vIndFueraLote, 
                                             VariablesRecepCiega.vIndNoFound);

                FarmaUtility.aceptarTransaccion();
            }
                    /*
                    * }
                catch(SQLException sql){
                    //error
                    log.error("",sql);
                    log.error("",sql);
        
                 }
                */
            //actualiza orden tabla
            txtBuscar.setText("");
            //Actualiza tblRelacionProductos
            actualizaListaPrimerConteo();

            //pinta la tabla nuevamente
            tblRelacionProductosConteo.repaint();
            FarmaUtility.ordenar(tblRelacionProductosConteo, 
                                 tableModelProductosConteo, Col_Aux_Conteo, 
                                 FarmaConstants.ORDEN_DESCENDENTE);
            tblRelacionProductosConteo.repaint(); //pinta tabla ordenada
            if (VariablesRecepCiega.vIndNoFound.trim().equalsIgnoreCase("S")) {
                //Agregar Mensaje Email JMIRANDA 26.11.09
                DBRecepCiega.enviaErrorCorreoPorDB(null, 
                                                   VariablesRecepCiega.vSecRecepGuia);
                FarmaUtility.showMessage(this, 
                                         "C�digo de Barra No Existe. \n" +
                        "Se enviar� alerta v�a e-mail.", txtBuscar);

                // 27.11.09
                //Coger el ultimo AuxCodBarra de tabla
                VariablesRecepCiega.vAuxCodBarra = 
                        FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo, 
                                                         0, Col_Cod_Barra);
                log.error("VariablesRecepCiega.vAuxCodBarra :" + 
                          VariablesRecepCiega.vAuxCodBarra);

            } else {
                VariablesRecepCiega.vAuxSecProd = 
                        VariablesRecepCiega.vLastSecProd;
            }

        } catch (SQLException sql) {
            //error
            log.error("",sql);
            log.error("",sql);
            //DUBILLUZ  - 14.01.2010
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, 
                                     "Error al momento de agregar el c�digo de barra.\n" +
                    sql.getMessage(), txtBuscar);
        }

    }
            

    private void txtBuscar_keyTyped(KeyEvent e) {        
        FarmaUtility.admitirDigitos(txtBuscar,e);
        //dubilluz - 04.12.2009
        /*
        if(txtBuscar.getText().trim().length()>15){
            FarmaUtility.showMessage(this,"Cantidad Fuera de l�mite!",txtBuscar);
        }
        */
        
        if(e.getKeyChar() == '+')
        {
            //if(VariablesRecepCiega.vIndFocoTablaConteo){   
            log.debug("Apunta a tblRelacionProductosConteo");
            
            log.error("TABLE verifica");
            muestraInfoProd();
            //}
        }        
        
    }
    
    private void validaCodBarra(String vCodBarra){
        /*
        //String codigo = "";
        // revisando codigo de barra
        char primerkeyChar = vCodBarra.charAt(0);
        char segundokeyChar;

        if (vCodBarra.length() > 1)
            segundokeyChar = vCodBarra.charAt(1);
        else
            segundokeyChar = primerkeyChar;

        char ultimokeyChar = vCodBarra.charAt(vCodBarra.length() - 1);
        log.info("productoBuscar:" + vCodBarra);
        //if (vCodBarra.length() > 6 && 
          //  (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && 
            //                                        (!Character.isLetter(ultimokeyChar))))) {
        if( !Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && 
                                                    (!Character.isLetter(ultimokeyChar)))) { 
            //Anterior se almacena LastCodBarra antes de validar
            VariablesRecepCiega.vLastCodBarra = vCodBarra;
            //log.debug("consulta cod barra antes: "+vCodBarra);
            try {
                vCodBarra = DBRecepCiega.obtieneCodigoProductoBarra(vCodBarra.trim());                
                log.info("consulta cod barra despues: "+vCodBarra);
            } catch (SQLException sql) {
                log.error("",sql);
                log.error(null,sql);
            }
             
        }
       ----- */ 
        //Anterior se almacena LastCodBarra antes de validar
        VariablesRecepCiega.vLastCodBarra = vCodBarra;
        //log.debug("consulta cod barra antes: "+vCodBarra);
        try {
            vCodBarra = DBRecepCiega.obtieneCodigoProductoBarra(vCodBarra.trim());                
            log.info("consulta cod barra despues: "+vCodBarra);
        } catch (SQLException sql) {
            log.error("",sql);
            log.error(null,sql);
        }
        
        if (vCodBarra.trim().equalsIgnoreCase("000000")) {
            //FarmaUtility.showMessage(this, "No existe producto relacionado con el Codigo de Barra. Verifique!!!", txtBuscar);            
            //VariablesRecepCiega.vArrayListCodBarraNoEncontrados.add(VariablesRecepCiega.vLastCodBarra);
            VariablesRecepCiega.vIndNoFound = "S";
            log.debug("indNoFound1: "+VariablesRecepCiega.vIndNoFound);
            //Los que no existen no se agregan
            //log.debug("Cod Barra no econtrados:"+VariablesRecepCiega.vArrayListCodBarraNoEncontrados);
            
            //Vuelvo al Registro Anterior
            //VariablesRecepCiega.vAuxCodBarra = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,0,Col_Cod_Barra);
            //log.error("VariablesRecepCiega.vAuxCodBarra :"+VariablesRecepCiega.vAuxCodBarra);
            // Validar secuencial                          
          return;
        }
       
    }
    
    private void obtieneInfoProdEnArrayList(ArrayList pArrayList)
    {
     /* int vFila = tblRelacionProductosConteo.getSelectedRow();
      log.error("vFila: "+vFila);
      String auxConteo = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,7); //secAuxConteo     
      log.debug("Otiene auxConteo: "+auxConteo);
      VariablesRecepCiega.vTempAuxSecConteo = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,9);
      String codBarra = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,8);
      VariablesRecepCiega.vTempDescProd = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,0);
      VariablesRecepCiega.vTempCant = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,2);
      log.debug("nroBloque: "+nroBloque+" -- codBarra:"+codBarra);
        
        //Se desea modificar con enter sobre el �ltimo registro
       if(VariablesRecepCiega.vLastCodBarra.trim().equalsIgnoreCase(codBarra.trim())
          && VariablesRecepCiega.vLastSecProd.trim().equalsIgnoreCase(
                 VariablesRecepCiega.vTempAuxSecConteo.trim())
       ){
          //Puede modificar porque es el �ltimo
          mostrarIngresoCantPrimerConteo();  
          //VariablesRecepCiega.vLastCant 
          log.debug("lasCant: "+VariablesRecepCiega.vLastCant);
        }
       else{
           FarmaUtility.showMessage(this,"Ud. no puede modificar este registro.\n" +
               "S�lo se puede modificar el �ltimo ingresado.",txtBuscar);
       }
       */    
      /*
      try
      {            
          DBRecepCiega.obtieneInfoProductoConteo(pArrayList, VariablesRecepCiega.vLastCodProd);
      } catch(SQLException sql)
      {
        log.error("",sql);
        log.error(null,sql);
        FarmaUtility.showMessage(this,"Error al obtener informacion del producto en conteo. \n" + sql.getMessage(),txtBuscar);
      }*/
    }
    
    private void muestraInfoProd()
    {
      if(tblRelacionProductosConteo.getRowCount() <= 0) return;
      
      ArrayList myArray = new ArrayList();
      //obtieneInfoProdEnArrayList(myArray);
      //log.debug("Tama�o en muestra info: "+myArray.size());
      
      //Para Modificar Cantidad
      int vFila = tblRelacionProductosConteo.getSelectedRow();
      log.error("vFila: "+vFila);
        //VariablesRecepCiega.vTempAuxSecConteo
      VariablesRecepCiega.vTempAuxSecConteo = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,Col_Aux_Conteo); //secAuxConteo     
      log.debug("Otiene vTempAuxSecConteo: "+ VariablesRecepCiega.vTempAuxSecConteo);
      VariablesRecepCiega.vTempNroBloque = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,Col_Nro_Bloque);
      VariablesRecepCiega.vTempCodBarra = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,Col_Cod_Barra);
      VariablesRecepCiega.vTempDescProd = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,Col_Desc_Prod);
      VariablesRecepCiega.vTempCant = FarmaUtility.getValueFieldJTable(tblRelacionProductosConteo,vFila,Col_Cant);
      log.debug("TempnroBloque: "+VariablesRecepCiega.vTempNroBloque+" -- codBarra:"+VariablesRecepCiega.vTempCodBarra);
      
      //Verifica valor del auxiliar si cod barra no existe
      if(!VariablesRecepCiega.vAuxCodBarra.trim().equalsIgnoreCase("")){      
          log.debug("!vAuxCodBarra: "+VariablesRecepCiega.vAuxCodBarra);
          VariablesRecepCiega.vLastCodBarra = VariablesRecepCiega.vAuxCodBarra;
          //seteamos en blanco el auxiliar Despu�s que se reasigna valor a LastCodBarra
          VariablesRecepCiega.vAuxCodBarra = "";           
          
      }
      if(!VariablesRecepCiega.vIndModificoCantActivo){
       //SI ES FALSO PUEDE MODIFICAR   
      
        //Se desea modificar con enter sobre el �ltimo registro, si no se ha ingresado ningun incorrecto
       if(VariablesRecepCiega.vLastCodBarra.trim().equalsIgnoreCase(VariablesRecepCiega.vTempCodBarra.trim())
          && VariablesRecepCiega.vLastSecProd.trim().equalsIgnoreCase(       
                 VariablesRecepCiega.vTempAuxSecConteo.trim())
       ){           
          //Puede modificar porque es el �ltimo
          VariablesRecepCiega.vIndModificarCant = true; 
          mostrarIngresoCantPrimerConteo(); 
            actualizaListaPrimerConteo();
            //VariablesRecepCiega.vIndModificoCantActivo = true;  //YA MODIFICO, YA NO PODRA MODIFICAR
          //VariablesRecepCiega.vLastCant 
          log.debug("Modifica si no hubo incorrectos, lasCant: "+VariablesRecepCiega.vLastCant);
        }
       //Modifica si ingreso incorrecto antes 
       else if(VariablesRecepCiega.vLastCodBarra.trim().equalsIgnoreCase(VariablesRecepCiega.vTempCodBarra.trim())
           && VariablesRecepCiega.vAuxSecProd.trim().equalsIgnoreCase(
                 VariablesRecepCiega.vTempAuxSecConteo.trim())){
           VariablesRecepCiega.vIndModificarCant = true; 
           mostrarIngresoCantPrimerConteo(); 
             actualizaListaPrimerConteo();
           //VariablesRecepCiega.vIndModificoCantActivo = true; //YA MODIFICO, YA NO PODRA MODIFICAR
             log.debug("Modifica si ingreso incorrecto, cantidad: "+VariablesRecepCiega.vLastCant); 
       }
          else{
              /*FarmaUtility.showMessage(this,"Ud. no puede modificar este registro.\n" +
                  "S�lo se puede modificar el �ltimo ingresado.",tblRelacionProductosConteo);
              */
              FarmaUtility.showMessage(this,"Ud. no puede modificar este registro.\n" +
              "S�lo se puede modificar el �ltimo ingresado, una vez modificado no se aceptan m�s cambios.",tblRelacionProductosConteo);
              log.debug("no puede modificar");
          }
      }      
      else{
          FarmaUtility.showMessage(this,"Ud. no puede modificar este registro.\n" +
        "S�lo se puede modificar el �ltimo ingresado, una vez modificado no se aceptan m�s cambios.",tblRelacionProductosConteo);
          log.debug("no puede modificar");
      }
    
      /*
      if(myArray.size() == 1)
      {       
        //descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
       
      } else
      {
       // descUnidPres = "";
       
        //FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtBuscar);
          log.debug("Error al obtener Informacion del Producto");
      }
      //rellenar datos
      //lblUnidad.setText(descUnidPres);      
      //rellenar tabla
       //myJTable.repaint();*/
      
      
      //dubilluz - 04.12.2009 
      setJTable(tblRelacionProductosConteo,txtBuscar);
    }
    
    private void updateEstadoRecepMercaderia(String pEstado) throws SQLException{
        //Utiliza el Secuencial de la recepcion: VariablesRecepCiega.vSecRecepGuia
        DBRecepCiega.actualizaEstadoRecep(VariablesRecepCiega.vSecRecepGuia,
                                          pEstado);
        log.debug("Estado Cabecera, sec:"+VariablesRecepCiega.vSecRecepGuia+" --- "+pEstado);
    }
    
    private String obtieneEstadoRecepCiega(String pSecRecepCiega){
        String rpta = "";
        try{
         rpta = DBRecepCiega.obtieneEstadoRecepCiega(pSecRecepCiega); 
        }catch(SQLException sql){
            log.error("",sql);
            log.error("",sql);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, 
                                     "Error al obtener el estado de la Recepci�n.\n" +
                    sql.getMessage(), txtBuscar);
        }
        return rpta;
    }
    
    private boolean validaIngresoConteo(String pNroRecep){
        boolean rpta = true;
        if(obtieneEstadoRecepCiega(pNroRecep).equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado)){            
            rpta = false;
            //cerrarVentana(false);            
        }
        return rpta;
    }

    private void tblRelacionProductosConteo_keyReleased(KeyEvent e) {
        //
    }
   
    private void tblRelacionProductosConteo_keyPressed(KeyEvent e)
    {   
        /* dubilluz - 04.12.2009
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
          e.consume();
            
            
            log.error("TABLE verifica");            
            //  muestraInfoProd();            
        }
        */
        /*
         * dubilluz - 04.12.2009
        else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){
            VariablesRecepCiega.vIndFocoTablaConteo = false;
            FarmaUtility.moveFocus(txtBuscar);             
        }
        */
        chkKeyPressed(e);
    }
    
    private void actualizaListaPrimerConteo(){
        //Actualiza El listado
        try{
        DBRecepCiega.obtieneListaPrimerConteo(tableModelProductosConteo,
                                              VariablesRecepCiega.vSecRecepGuia, 
                                              VariablesRecepCiega.vNroBloque);
            
              
            }catch(SQLException sql){
                log.error("",sql);
                FarmaUtility.showMessage(this,"Problemas al Mostrar Lista Productos en conteo."+sql.getMessage(),txtBuscar);
            }
    }
    
    public void obtieneNroBloque(){
        try{                
            if(VariablesRecepCiega.vNroBloque.trim().equalsIgnoreCase("")){
                //Consulta el �ltimo bloque desde la BD    
                //Agrego el Nro de Bloque        
                int nroBloque = DBRecepCiega.obtieneNroBloqueConteo(VariablesRecepCiega.vSecRecepGuia);
                log.debug("bloque: "+nroBloque);
                VariablesRecepCiega.vNroBloque = String.valueOf(nroBloque); 
                log.debug("VariablesRecepCiega.vNroBloque: "+VariablesRecepCiega.vNroBloque );
            }
            }catch(SQLException sql){
                log.error("",sql);                
            }
    }    
    
    private boolean cargaLoginAdmLocal()
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
                         
        dlgLogin.setVisible(true);      
         /* if(FarmaVariables.vAceptar){    
            return;
          }else{
            flag = false;
          }*/
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        FarmaVariables.vAceptar = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return FarmaVariables.vAceptar;
    }
    
    private boolean cargaLoginVendedor()
    {
      String numsec = FarmaVariables.vNuSecUsu ;
      String idusu = FarmaVariables.vIdUsu ;
      String nomusu = FarmaVariables.vNomUsu ;
      String apepatusu = FarmaVariables.vPatUsu ;
      String apematusu = FarmaVariables.vMatUsu ;
      
      try{
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
          dlgLogin.setRolUsuario(FarmaConstants.ROL_VENDEDOR);
                         
        dlgLogin.setVisible(true);
       /* FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;*/
      } catch (Exception e)
      {
        FarmaVariables.vNuSecUsu  = numsec ;
        FarmaVariables.vIdUsu  = idusu ;
        FarmaVariables.vNomUsu  = nomusu ;
        FarmaVariables.vPatUsu  = apepatusu ;
        FarmaVariables.vMatUsu  = apematusu ;
        FarmaVariables.vAceptar = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
      }
      return FarmaVariables.vAceptar;
    }
    
    private String getDestConteoNoEncontrado(){
        String email = "";
        try {
                email = DBRecepCiega.getDestinatarioCodBarraNoHallado();
            }catch(SQLException sql){
                log.error("",sql);                
            }        
        return email;        
    }    
        
    private void validaRol(){
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
            dlgLogin.setVisible(true);
              if (FarmaVariables.vAceptar) {
                  //FarmaVariables.dlgLogin = dlgLogin;
                  verificaRolUsuario();                     
                  FarmaVariables.dlgLogin = dlgLogin;
              }
    }
    
    private void verificaRolUsuario(){
        if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
            FarmaVariables.vAceptar = true;            
        }   
        else if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            FarmaVariables.vAceptar = true;            
        }
        else {
            //no debe ingresar
            FarmaVariables.vAceptar = false;
            FarmaUtility.showMessage(this,"Ud. no tiene Permiso para Finalizar el conteo." +
                "\nIngrese Usuarios Correcto.",txtBuscar);
            return;
        }
    }
    
    
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,null,0);
      }
      FarmaUtility.moveFocus(pText);
    }
    
    /**
     * 
     */
    private void pFinalizaConteo() {
        
        //Se bloquea el registro
        UtilityRecepCiega.pBloqueoRecepcion(VariablesRecepCiega.vSecRecepGuia);
        
        String pEstado =
                    UtilityRecepCiega.pEstadoRecepcion(VariablesRecepCiega.vSecRecepGuia);
        
        if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoConteo)&&
           !pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado)
           ) {
            try {
                String indNoContado = 
                    DBRecepCiega.validaRegistroAuxConteo(VariablesRecepCiega.vSecRecepGuia);
                if (indNoContado.trim().equalsIgnoreCase("S")) {
                    validaRol();
                    if (FarmaVariables.vAceptar) {
                        if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                                           "�Est� seguro que desea Finalizar el Conteo?")) {
                            DBRecepCiega.insertConteo(VariablesRecepCiega.vSecRecepGuia);
                            updateEstadoRecepMercaderia(ConstantsRecepCiega.EstadoRevisado);
                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this, 
                                                     "Se Ha Finalizado el Conteo con �xito!", 
                                                     txtBuscar);
                            cerrarVentana(true);
                        } else {
                            return;
                        }
                    }
                } else {
                    FarmaUtility.showMessage(this, 
                                             "A�n no ha contado Productos para est� Recepci�n." + 
                                             "\nIngrese los productos para poder Finalizar. De lo contrario ESC para salir.", 
                                             txtBuscar);
    
    
                }
            } catch (SQLException sql) {
                log.error("",sql);
                log.error("",sql);
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, 
                                         "Error al momento de finalizar conteo.\n" +
                        sql.getMessage(), txtBuscar);
            }
        }
        else {
            String pMensaje = "No puede finalizar el conteo.\n";
            
            if(pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado))                
                pMensaje += "Porque ya se finaliz�.";
            else
                if(pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal))
                    pMensaje += "Porque ya se afect� totalmente.";
                else
                    if(pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal))
                        pMensaje += "Porque ya se afect� parcialmente.";
                    
            FarmaUtility.showMessage(this, 
                                     pMensaje,
                                     txtBuscar);
            
            VariablesRecepCiega.vNroBloque = "";
            VariablesRecepCiega.contadorFila = 0;
            VariablesRecepCiega.vLastCodBarra = "";
            VariablesRecepCiega.vAuxSecProd = "";
            VariablesRecepCiega.vAuxCodBarra = "";
            VariablesRecepCiega.vSecConteo = "";
            VariablesRecepCiega.vLastSecProd = "";
            VariablesRecepCiega.vIndModificoCantActivo = false;
            cerrarVentana(false);            
        }
        
        log.info("Esto es para liberar el bloqueo...de Estado.");        
        FarmaUtility.liberarTransaccion();
    }
    
}
