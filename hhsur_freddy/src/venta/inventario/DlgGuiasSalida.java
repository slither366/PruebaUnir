package venta.inventario;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.DlgListaMaestros;
import venta.cliente.reference.ConstantsCliente;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.FacadeInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DlgDevolucionNueva.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * CHUANES      12.12.2013   Creaci�n<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 2.0<br>
 *
 */
public class DlgGuiasSalida extends JDialog  implements KeyListener {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgGuiasSalida.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlHeader = new JPanelTitle();//Panel de cabecera
    private JPanelTitle pnlDesti = new JPanelTitle();//Panel de destinatario
    private JPanelTitle pnlTransp = new JPanelTitle();//panel de Transportista
    //Labels
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelWhite lblLocal = new JLabelWhite();
    private JLabelWhite lblDirLocal = new JLabelWhite();
    private JLabelWhite lblRucDesti = new JLabelWhite();
    private JLabelWhite lblDirDesti = new JLabelWhite();
    private JLabelWhite lblNomTransp = new JLabelWhite();
    private JLabelWhite lblDirTransp = new JLabelWhite();
    private JLabelWhite lblPlaTransp = new JLabelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //Texts
    private JTextFieldSanSerif txtFecha=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtLocal=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirLocal=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSenores=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtRucDestino=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirDestino=new JTextFieldSanSerif();  
    private JTextFieldSanSerif txtRucTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNomTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDirTransp=new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPlacTransp=new JTextFieldSanSerif();
    private JButtonLabel lblSenores_T = new JButtonLabel();
    private JButtonLabel lblRuc_T = new JButtonLabel();
    
    private FacadeInventario facadeInventario  = new FacadeInventario();
    
    public DlgGuiasSalida(){
        this(null, "", false);
    }
    public DlgGuiasSalida(Frame parent, String title, boolean modal) {
        super(parent,title,modal);
        myParentFrame = parent;
        try
        {
         this.addKeyListener(this);

          setFocusable(true);
          jbInit();
          initialize();
          FarmaUtility.centrarVentana(this);
        }
        catch(Exception e){
          log.error("",e);
        }
    }
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    
    private void initialize(){
      
      FarmaVariables.vAceptar = false;
      getFecha();
      getDatosLocal();
    }
    
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(443, 513));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Guias de Salida");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter(){
            public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
            this_windowClosing(e);
            }
        });
        
        pnlHeader.setBounds(new Rectangle(10, 10, 415, 140));
        pnlHeader.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader.setBackground(Color.white);
        pnlHeader.setFocusable(false);
        
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(20, 10, 60, 15));
        lblFecha.setForeground(new Color(255, 130, 14));
        lblFecha.setFocusable(false);
        
        lblLocal.setText("Local:");
        lblLocal.setBounds(new Rectangle(20, 40, 60, 15));
        lblLocal.setForeground(new Color(255, 130, 14));
        lblLocal.setFocusable(false);
    
        lblDirLocal.setText("Direccion:");
        lblDirLocal.setBounds(new Rectangle(20, 75, 60, 15));
        lblDirLocal.setForeground(new Color(255, 130, 14));
        lblDirLocal.setFocusable(false);
        
        txtFecha.setBounds(new Rectangle(90, 10, 75, 20));
        txtFecha.setEnabled(false);
        txtLocal.setBounds(new Rectangle(90, 40, 265, 20));
        txtLocal.setEnabled(false);
        txtDirLocal.setBounds(new Rectangle(90, 75,265, 20));
        txtDirLocal.setEnabled(false);


        pnlHeader.add(lblFecha, null);
        pnlHeader.add(lblLocal, null);
        pnlHeader.add(lblDirLocal, null);
        pnlHeader.add(txtFecha, null);
        pnlHeader.add(txtLocal, null);
        pnlHeader.add(txtDirLocal, null);
        
        
        
        
        pnlDesti.setBounds(new Rectangle(10, 160, 415, 115));
        pnlDesti.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDesti.setBackground(Color.white);
        pnlDesti.setFocusable(false);


        lblRucDesti.setText("Ruc:");
        lblRucDesti.setBounds(new Rectangle(20, 48, 70, 15));
        lblRucDesti.setForeground(new Color(255, 130, 14));
        lblRucDesti.setFocusable(false);
        
        lblDirDesti.setText("Direcci�n:");
        lblDirDesti.setBounds(new Rectangle(20, 80, 70, 15));
        lblDirDesti.setForeground(new Color(255, 130, 14));
        lblDirDesti.setFocusable(false);
        
        txtSenores.setBounds(new Rectangle(95, 15, 265, 20));
        txtSenores.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtSenores_keyPressed(e);
        }
      });
        txtRucDestino.setBounds(new Rectangle(95, 48, 99, 20));  
        txtRucDestino.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtRuc_keyPressed(e);
        }
      });
        txtDirDestino.setBounds(new Rectangle(95, 80, 265, 20));
       txtDirDestino.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtDireccion_keyPressed(e);
        }
      });

        pnlDesti.add(lblSenores_T, null);
        pnlDesti.add(txtDirDestino, null);
        pnlDesti.add(txtRucDestino, null);
        pnlDesti.add(txtSenores, null);
        pnlDesti.add(lblDirDesti, null);
        pnlDesti.add(lblRucDesti,null);


        pnlTransp.setBounds(new Rectangle(10, 285, 415, 160));   
        pnlTransp.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTransp.setBackground(Color.white);
        pnlTransp.setFocusable(false);
        
        lblNomTransp.setText("Transportista:"); 
        lblNomTransp.setBounds(new Rectangle(20, 45, 85, 15));
        lblNomTransp.setForeground(new Color(255, 130, 14));
        lblNomTransp.setFocusable(false);


        lblDirTransp.setText("Direccion:");
        lblDirTransp.setBounds(new Rectangle(20, 80, 70, 15)); 
        lblDirTransp.setForeground(new Color(255, 130, 14));
        lblDirTransp.setFocusable(false);
        
        lblPlaTransp.setText("Placa"); 
        lblPlaTransp.setBounds(new Rectangle(20, 110, 70, 15));
        lblPlaTransp.setForeground(new Color(255, 130, 14));
        lblPlaTransp.setFocusable(false);
        
        txtRucTransp.setBounds(new Rectangle(115, 15, 100, 20));
        txtRucTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtRucTransportista_keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
        txtRucTransportista_keyRelease(e);
        }
      });
        txtNomTransp.setBounds(new Rectangle(115, 45, 265, 20));
        txtNomTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtTransportista_keyPressed(e);
        }
      });
        txtDirTransp.setBounds(new Rectangle(115, 80, 265, 20));
        txtDirTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDireccionTransportista_keyPressed(e);
        }
      });
        txtPlacTransp.setBounds(new Rectangle(115, 110, 100, 20));
        txtPlacTransp.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
         txtPlaca_keyPressed(e);
        }
      });

        pnlTransp.add(lblRuc_T, null);
        pnlTransp.add(lblNomTransp, null);
        pnlTransp.add(lblDirTransp, null);
        pnlTransp.add(lblPlaTransp, null);
        pnlTransp.add(lblNomTransp, null);
        pnlTransp.add(txtRucTransp, null);
        pnlTransp.add(txtNomTransp, null);
        pnlTransp.add(txtDirTransp, null);
        pnlTransp.add(txtPlacTransp, null);
        
            
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(335, 455, 85, 20));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 455, 115, 20));
        lblF11.setFocusable(false);
        
        
        jContentPane.add(pnlHeader,null);
        jContentPane.add(pnlDesti,null);
        jContentPane.add(pnlTransp,null);
        jContentPane.add(lblF11,null);
        jContentPane.add(lblEsc,null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        
        txtRucDestino.setLengthText(15);
        txtSenores.setLengthText(120);
        txtDirDestino.setLengthText(120);
        txtRucTransp.setLengthText(15);
        txtNomTransp.setLengthText(120);
        txtDirTransp.setLengthText(120);
        txtPlacTransp.setLengthText(15);
        lblSenores_T.setText("Se\u00f1ores:");
        lblSenores_T.setBounds(new Rectangle(20, 15, 65, 20));
        lblSenores_T.setForeground(new Color(255, 130, 14));
        lblSenores_T.setMnemonic('S');
        lblSenores_T.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblSenores_actionPerformed(e);
                }
            });
        lblRuc_T.setText("RUC:");
        lblRuc_T.setBounds(new Rectangle(20, 15, 65, 20));
        lblRuc_T.setForeground(new Color(255, 130, 14));
        lblRuc_T.setMnemonic('R');
        lblRuc_T.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblRuc_actionPerformed(e);
                }
            });

    }
    
    
    
    /* ************************************************************************ */
          /*                            METODOS DE EVENTOS                            */
          /* ************************************************************************ */
          @Override
          public void keyTyped(KeyEvent e) {
             // chkKeyPressed(e);
          }

        @Override
          public void keyPressed(KeyEvent e) {
              chkKeyPressed(e);
          }

          @Override
          public void keyReleased(KeyEvent e) {
          }
    
    
    private void txtSenores_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtRucDestino);
        txtSenores.setText(txtSenores.getText().toUpperCase().trim());
      }
      else
        chkKeyPressed(e);
    }

    private void txtRuc_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtDirDestino);
      }
      else
        chkKeyPressed(e);
    }

    private void txtDireccion_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtRucTransp);
      }
      else
        chkKeyPressed(e);
    }
    
    private void txtRucTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_TRANSPORTISTA;
        validaCodigo(txtRucTransp, txtNomTransp, VariablesPtoVenta.vTipoMaestro);
      }
      else
        chkKeyPressed(e);
    }
    
    private void txtRucTransportista_keyRelease(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
        {   cargarTransportista();
            FarmaUtility.moveFocus(txtNomTransp);
            FarmaVariables.vAceptar = false;
         
        }
      
    }
    
    private void txtTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtDirTransp);
      }
      else
        chkKeyPressed(e);
    }
    private void txtDireccionTransportista_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtPlacTransp);
      }
      else
        chkKeyPressed(e);
    }

    private void txtPlaca_keyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        FarmaUtility.moveFocus(txtSenores);
      }
      else
        chkKeyPressed(e);
    }
    
    private void this_windowOpened(WindowEvent e)
    {
      FarmaUtility.centrarVentana(this);
      FarmaUtility.moveFocus(txtSenores);  
     
    }
    
    private void this_windowClosing(WindowEvent e)
    {
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void lblSenores_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSenores);
    }

    private void lblRuc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtRucTransp);
    }
    
    /* ************************************************************************ */
          /*                     METODOS AUXILIARES DE EVENTOS                        */
          /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e){
      if(UtilityPtoVenta.verificaVK_F11(e)){
          if(validarCampos()){
             abrirModal();
              if(FarmaVariables.vAceptar){
                  generaGuiaSalida(); 
              }          
          }
      }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
      }
    }
      
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void abrirModal(){
        DlgGuiaModal dlgGuiaModal=new DlgGuiaModal(this.myParentFrame,"",true);
        dlgGuiaModal.setVisible(true);
    
    }

    private void getFecha(){
        String vfecha=null;
        try
        {
            vfecha=FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            if(vfecha!=null){
                txtFecha.setText(vfecha.trim().substring(0, 10));
            }
       
        }catch(SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema :\n"+sql.getMessage(),null);
        }
       
      
    }
    
    private void getDatosLocal(){
        txtLocal.setText(FarmaVariables.vDescLocal);
        txtDirLocal.setText(FarmaVariables.vDescCortaDirLocal);
    }
   
    private void  getDatosVista(){
        
       VariablesInventario.vFecha=txtFecha.getText().trim();
       VariablesInventario.vDescLocal=txtLocal.getText().trim();
       VariablesInventario.vDireLocal=txtDirLocal.getText().trim();
       VariablesInventario.vRucDestina=txtRucDestino.getText().trim();
       VariablesInventario.vNomDesctina=txtSenores.getText().trim();
       VariablesInventario.vDireDestina=txtDirDestino.getText().trim();
       VariablesInventario.vRucTransp=txtRucTransp.getText().trim();
       VariablesInventario.vNomTransp=txtNomTransp.getText().trim();
       VariablesInventario.vDireTransp=txtDirTransp.getText().trim();
       VariablesInventario.vPlacTransp=txtPlacTransp.getText().trim();
        
    }
    
    private void  saveDatos() {
        boolean vFlag;

          try {
              vFlag = facadeInventario.grabarGuiaRemision(this);
              if(!vFlag){
                  FarmaUtility.showMessage(this, "Registro grabado." + VariablesInventario.vNumGuia.trim(), null);
                  //4. Imprimir guia                    
                  UtilityInventario.cargaCabecera(this,null,VariablesInventario.vNumNotaEs);
                  UtilityInventario.procesoImpresionGuias(this ,null , VariablesInventario.vTipoFormatoImpresion, true);
                  facadeInventario.confirmarDevolucion(VariablesInventario.vNumNotaEs);
                  cerrarVentana(true);
              }else{  
                  FarmaUtility.showMessage(this, "Error Fallo la Transacc�on " + VariablesInventario.vNumGuia.trim(), null);                 
              }        
          }catch(Exception ex){
              log.error("",ex);
          }         
    }
    
    

    private void generaGuiaSalida() {
        if (JConfirmDialog.rptaConfirmDialog(this,"�Est� seguro de generar la transferencia?")) {
            FarmaVariables.vAceptar = false;
            DlgListaImpresorasInv dlgListaImpresorasInv = new DlgListaImpresorasInv(this.myParentFrame, "", true);
            dlgListaImpresorasInv.setVisible(true);
            if (!FarmaVariables.vAceptar) {
                return;
            }
            if (grabar()) {
               
                cerrarVentana(true);
            }
        }
    }

    private boolean grabar() {   
        getDatosVista();
        saveDatos();        
        return true;
    }
    //CHUANES 28.03.2014-Modificacion
    private void cargarTransportista(){
        txtNomTransp.setFocusable(true);//permite que se ponga el cursor miestras haya datos.
        try
        {
         ArrayList array = new ArrayList();
        //Se debe setear directamente la variable  codigo de transp. y no el valor de la caja de texto de ruc
           DBInventario.getDatosTranspRecepCiega(array, VariablesPtoVenta.vCodMaestro); 
           if(array.size()>0){
                array=(ArrayList)array.get(0);
                //Obtenemos los datos principales del array para hacer validaciones
                String codTransp=array.get(0).toString();
                String rucTransp=array.get(1).toString();
                if(codTransp.trim()!=null){
                    if(rucTransp.trim().length()==11){
                      limpiaDatosTrans();//se limpia las cajas de texto  para que no se quede pegado el dato anterior
                    //Pasamos directamente los datos del array 
                    txtRucTransp.setText(array.get(1).toString());
                    txtNomTransp.setText(array.get(2).toString());
                    txtDirTransp.setText(array.get(3).toString());
                    }
                }

           }else{
               limpiaDatosTrans();
               FarmaUtility.showMessage(this,"No se encontro datos para el registro seleccionado.Verificar!!!",null);  
               if(txtRucTransp.isCursorSet()){//verificamos si esta el cursor
                txtNomTransp.setFocusable(false); //negamos que se posicione el cursor hasta que se ingrese un valor no nullo
               }
           }
           
        }
        catch (SQLException e)
        {
          log.error("",e);
          FarmaUtility.showMessage(this, 
                                   "Ha ocurrido un error al obtener datos del transportista.\n" + 
                                   e.getMessage(), txtRucTransp);
        }    
        
    }
    private void limpiaDatosTrans(){
        txtRucTransp.setText("");
        txtNomTransp.setText("");
        txtDirTransp.setText("");
        txtPlacTransp.setText("-");
    }
    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro)
    {
      if(pJTextField_Cod.getText().trim().length() > 0)
      {
        VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
        ArrayList myArray = new ArrayList();
        buscaCodigoListaMaestro(myArray);
        
        if(myArray.size() == 0)
        {
       FarmaUtility.showMessage(this,"Ruc No Encontrado",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        } else if(myArray.size() == 1)
       {
          String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
          String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
          //el seteo de codigo hace que se quede pegado dicho valor en la caja de texto
          //tampoco es necesario se descripcion
         // pJTextField_Cod.setText(codigo);
          //pJTextField_Desc.setText(descripcion);
          VariablesPtoVenta.vCodMaestro = codigo;
          FarmaVariables.vAceptar = true;
        } else 
        {
          FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",pJTextField_Cod);
          FarmaVariables.vAceptar = false;
        }
      } else
      {
        cargaListaMaestros(pTipoMaestro,pJTextField_Cod, pJTextField_Desc);
      }
    }
    
    private void buscaCodigoListaMaestro(ArrayList pArrayList)
    {
      try
      {
        DBPtoVenta.buscaCodigoListaMaestro(pArrayList,VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
      } catch(SQLException sql)
      {
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurri� un error al buscar ruc en maestros :\n" +sql.getMessage(),null);
      }
    }
    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc)
    {
      VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
      DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
      dlgListaMaestros.setVisible(true);
      if(FarmaVariables.vAceptar)
      { //No se debe setear el valor del codigo de transp ya que este se queda pegado y no es necesario . 
       // pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
       pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
      }
    }
    
    private boolean validarCampos()
    {
      boolean retorno = true;
     
   if(txtSenores.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Razon Social",txtSenores);
        retorno = false;
      }
      else if(UtilityInventario.verificaRucValido(txtRucDestino.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
      {
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucDestino);
        retorno = false;
      }
      else if(txtRucDestino.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Destinatario",txtRucDestino);
        retorno = false;
      }else if(txtDirDestino.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion de Destino",txtDirDestino);
        retorno = false;
      }else if(txtNomTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Transportista",txtNomTransp);
        retorno = false;
      }else if(txtRucTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Transportista",txtRucTransp);
        retorno = false;
      }
      else if(UtilityInventario.verificaRucValido(txtRucTransp.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO))
      {
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucTransp);
        retorno = false;
      }
      else if(txtDirTransp.getText().trim().equals(""))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion del Transportista",txtDirTransp);
        retorno = false;
      }else if(txtPlacTransp.getText().trim().equals("-"))
      {
        FarmaUtility.showMessage(this,"Debe ingresar la Placa del Transportista",txtPlacTransp);
        retorno = false;
      }
      
      return retorno;
    }

}
