package venta.inventario;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import common.FarmaLoadCVL;
import common.FarmaUtility;
import static common.FarmaUtility.moveFocus;
import common.FarmaVariables;

import venta.DlgListaMaestros;
import venta.cliente.reference.ConstantsCliente;
import venta.inventario.dto.NotaEsCabDTO;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
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
 * ERIOS      26.06.2013   Creaci�n<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionNueva extends JDialog implements KeyListener{

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionNueva.class);
    
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlHeader01 = new JPanelTitle();
    private JButtonLabel btnMotivo = new JButtonLabel();
    private JComboBox cmbMotivo = new JComboBox();
    private JPanelTitle pnlHeader02 = new JPanelTitle();
    private JLabelWhite lblRuc = new JLabelWhite();
    private JTextFieldSanSerif txtRuc = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSenores = new JTextFieldSanSerif();
    private JLabelWhite lblDireccion = new JLabelWhite();
    private JLabelWhite lblDireccionTransportista = new JLabelWhite();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();
    private JPanelTitle pnlHeader03 = new JPanelTitle();
    private JTextFieldSanSerif txtRucTransportista = new JTextFieldSanSerif();
    private JLabelWhite lblProveedor = new JLabelWhite();
    private JTextFieldSanSerif txtTransportista = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccionTransportista = new JTextFieldSanSerif();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //private JTextFieldSanSerif txtDestino = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodigoDestino = new JTextFieldSanSerif();
    private JLabelWhite lblPlaca_T = new JLabelWhite();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();    
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JButtonLabel btnCodigoProv = new JButtonLabel();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JButtonLabel btnTransp = new JButtonLabel();
    
    private NotaEsCabDTO notaEsCabDTO = null;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgDevolucionNueva() {
        this(null, "", false);
    }

    public DlgDevolucionNueva(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
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
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(443, 452));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Datos de la Devolucion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter()
          {
            public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
              this_windowClosing(e);
            }
          });
        
        pnlHeader01.setBounds(new Rectangle(10, 10, 415, 45));
        pnlHeader01.setBackground(Color.white);
        pnlHeader01.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        
        btnMotivo.setText("Motivo:");
        btnMotivo.setBounds(new Rectangle(20, 10, 40, 15));
        btnMotivo.setForeground(new Color(255, 130, 14));

        cmbMotivo.setBounds(new Rectangle(70, 10, 195, 20));
        cmbMotivo.setFont(new Font("SansSerif", 1, 11));
        cmbMotivo.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
              cmbMotivo_keyPressed(e);
            }
          });
        
        pnlHeader02.setBounds(new Rectangle(10, 90, 415, 130));
        pnlHeader02.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader02.setBackground(Color.white);


        txtCodigoDestino.setLengthText(15);
        txtCodigoDestino.setBounds(new Rectangle(120, 10, 55, 20));
        txtCodigoDestino.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtCodigoDestino_keyPressed(e);
                }

            public void keyReleased(KeyEvent e)
            {
              txtCodigoDestino_keyReleased(e);
            }
          });
        
        //txtDestino.setBounds(new Rectangle(135, 40, 215, 20));
        //txtDestino.setEnabled(false);
        
        txtSenores.setLengthText(120);
        txtSenores.setBounds(new Rectangle(120, 40, 265, 20));
        txtSenores.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtSenores_keyPressed(e);
                }
          });    
        txtRuc.setLengthText(15);    
        txtRuc.setBounds(new Rectangle(120, 70, 99, 20));
        txtRuc.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtRuc_keyPressed(e);
                }
          });
        
        txtDireccion.setLengthText(120);
        txtDireccion.setBounds(new Rectangle(120, 100, 265, 20));
        txtDireccion.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtDireccion_keyPressed(e);
                }
          });
        
        lblRuc.setText("RUC:");
        lblRuc.setBounds(new Rectangle(20, 70, 70, 20));
        lblRuc.setForeground(new Color(255, 130, 14));
        
        lblDireccion.setText("Direcci�n:");
        lblDireccion.setBounds(new Rectangle(20, 100, 70, 20));
        lblDireccion.setForeground(new Color(255, 130, 14));
        
        
        pnlHeader03.setBounds(new Rectangle(10, 235, 415, 140));
        pnlHeader03.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader03.setBackground(Color.white);


        txtRucTransportista.setLengthText(15);
        txtRucTransportista.setBounds(new Rectangle(120, 15, 100, 20));
        txtRucTransportista.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                    txtRucTransportista_keyPressed(e);
                }

            public void keyReleased(KeyEvent e){
                    txtRucTransportista_keyReleased(e);
                }
          });        
        
        lblProveedor.setText("Transportista:");
        lblProveedor.setBounds(new Rectangle(20, 45, 85, 15));
        lblProveedor.setForeground(new Color(255, 130, 14));
        
        txtTransportista.setLengthText(120);
        txtTransportista.setBounds(new Rectangle(120, 45, 265, 20));
        txtTransportista.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtTransportista_keyPressed(e);
                }
          });        
        
        lblDireccionTransportista.setText("Direcci�n:");
        lblDireccionTransportista.setBounds(new Rectangle(20, 75, 70, 15));
        lblDireccionTransportista.setForeground(new Color(255, 130, 14));
        
        txtDireccionTransportista.setLengthText(120);
        txtDireccionTransportista.setBounds(new Rectangle(120, 75, 265, 20));
        txtDireccionTransportista.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtDireccionTransportista_keyPressed(e);
                }
          });

        txtPlaca.setLengthText(15);
        txtPlaca.setBounds(new Rectangle(120, 105, 100, 20));
        txtPlaca.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                    txtPlaca_keyPressed(e);
                }
          });

        jPanelTitle1.setBounds(new Rectangle(10, 65, 415, 25));
        jLabelWhite1.setText("Proveedor");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 135, 20));
        btnCodigoProv.setText("Codigo:");
        btnCodigoProv.setBounds(new Rectangle(20, 10, 75, 20));
        btnCodigoProv.setForeground(new Color(255, 130, 14));
        btnCodigoProv.setMnemonic('c');
        btnCodigoProv.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnCodigoProv_actionPerformed(e);
                }
            });
        jLabelOrange1.setText("Se�ores:");
        jLabelOrange1.setBounds(new Rectangle(20, 40, 80, 20));
        btnTransp.setText("RUC Transp:");
        btnTransp.setBounds(new Rectangle(20, 15, 75, 20));
        btnTransp.setForeground(new Color(255, 130, 14));
        btnTransp.setMnemonic('r');
        btnTransp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnTransp_actionPerformed(e);
                }
            });
        lblPlaca_T.setText("Placa:");
        lblPlaca_T.setBounds(new Rectangle(20, 105, 70, 15));
        lblPlaca_T.setForeground(new Color(255, 130, 14));

        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(180, 390, 115, 20));

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(310, 390, 115, 20));

        pnlHeader01.add(cmbMotivo, null);
        pnlHeader01.add(btnMotivo, null);
        //pnlHeader01.add(txtDestino, null);
        pnlHeader02.add(jLabelOrange1, null);
        pnlHeader02.add(btnCodigoProv, null);
        pnlHeader02.add(lblRuc, null);
        pnlHeader02.add(txtRuc, null);
        pnlHeader02.add(txtSenores, null);
        pnlHeader02.add(lblDireccion, null);
        pnlHeader02.add(txtDireccion, null);
        pnlHeader02.add(txtCodigoDestino, null);
        pnlHeader03.add(btnTransp, null);
        pnlHeader03.add(txtPlaca, null);
        pnlHeader03.add(txtRucTransportista, null);
        pnlHeader03.add(lblProveedor, null);
        pnlHeader03.add(txtTransportista, null);
        pnlHeader03.add(lblDireccionTransportista, null);
        pnlHeader03.add(txtDireccionTransportista, null);
        pnlHeader03.add(lblPlaca_T, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(pnlHeader01, null);
        jContentPane.add(pnlHeader02, null);
        jContentPane.add(pnlHeader03, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    
    }
    
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    
    private void initialize(){
      initCmbMotivo();
      FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCmbMotivo(){
        //ERIOS 25.07.2013 Solucion temporal para indicar el motivo de kardex
        //TODO Mejorar la solucion
      cmbMotivo.removeAllItems();
        ArrayList dataArrayList = new ArrayList(); 
        ArrayList data = new ArrayList(); 
        data.add(ConstantsPtoVenta.MOT_KARDEX_SALIDA_PROVEEDOR);
        data.add("DEVOLUCION O/C DIRECTA");
        dataArrayList.add(data);
        FarmaLoadCVL.loadCVLFromArrayList(cmbMotivo, 
                                            ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION, 
                                            dataArrayList, 
                                            true);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    private void this_windowOpened(WindowEvent e){
        moveFocus(cmbMotivo);
      FarmaUtility.centrarVentana(this);
      mostrarDatosCabecera();
     
    }
    
    private void this_windowClosing(WindowEvent e){
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cmbMotivo_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtCodigoDestino);
        FarmaVariables.vAceptar = false;
      }else
        chkKeyPressed(e);
    }

    private void txtRuc_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtDireccion);
      }
      else
        chkKeyPressed(e);
    }
    
    private void btnSenores_actionPerformed(ActionEvent e){
      //moveFocus(txtSenores);
    }    
    
    private void txtSenores_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtRuc);
      }
      else
        chkKeyPressed(e);
    }    
    
    private void txtDireccion_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtRucTransportista);
      }
      else
        chkKeyPressed(e);
    }   
    
    private void txtRucTransportista_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_TRANSPORTISTA;
        validaCodigo(txtRucTransportista, txtTransportista, VariablesPtoVenta.vTipoMaestro);
        mostrarTransportista();
        moveFocus(txtTransportista);
      }else
        chkKeyPressed(e);
    }

    private void btnCodigoProv_actionPerformed(ActionEvent e) {
        moveFocus(txtCodigoDestino);
    }
    
    private void txtRucTransportista_keyReleased(KeyEvent e){
      
    }
    
    private void txtTransportista_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtDireccionTransportista);
      }
      else
        chkKeyPressed(e);
    }    
    
    private void txtDireccionTransportista_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(txtPlaca);
      }
      else
        chkKeyPressed(e);
    }

   @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        chkKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    private void txtCodigoDestino_keyPressed(KeyEvent e){
        
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
         VariablesPtoVenta.vTipoMaestro = ConstantsPtoVenta.LISTA_PROVEEDOR;
         validaCodigo(txtCodigoDestino, txtSenores, VariablesPtoVenta.vTipoMaestro);  
          mostrarDatosProvedor();
          moveFocus(txtSenores);
     }
      else 
        chkKeyPressed(e);
      
    }
    
    private void txtCodigoDestino_keyReleased(KeyEvent e){
    
      /*if(e.getKeyCode() == KeyEvent.VK_ENTER && FarmaVariables.vAceptar)
      {
        mostrarDatosTransporte();
        FarmaVariables.vAceptar = false;
        if(cmbDefinicion.isVisible()) moveFocus(cmbDefinicion);
        else moveFocus(txtSenores);
      }*/
    
    }    

    private void btnTransp_actionPerformed(ActionEvent e) {
        moveFocus(txtRucTransportista);
    }
    
    private void txtPlaca_keyPressed(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
        moveFocus(cmbMotivo);
      }
      else
        chkKeyPressed(e);
    }  
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e){
      if(venta.reference.UtilityPtoVenta.verificaVK_F11(e)){
        if(validarCampos()){
          cargarCabecera();
          getCodProveedor();
          cerrarVentana(true);  
        }
      }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
      }
    }
    
    private void cerrarVentana(boolean pAceptar){
                  FarmaVariables.vAceptar = pAceptar;
                  this.setVisible(false);
                  this.dispose();
    } 
    
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    private void cargarCabecera(){
      
      notaEsCabDTO.setTipoOrigenNotaEs(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR);
      notaEsCabDTO.setTipoMotiNotaEs(ConstantsPtoVenta.MOT_KARDEX_SALIDA_PROVEEDOR);
      notaEsCabDTO.setCodDestinoNotaEs(txtCodigoDestino.getText());
      notaEsCabDTO.setDescEmpresa(txtSenores.getText());
      notaEsCabDTO.setRucEmpresa(txtRuc.getText());
      notaEsCabDTO.setDireEmpresa(txtDireccion.getText());
      notaEsCabDTO.setDescTransp(txtTransportista.getText());
      notaEsCabDTO.setRucTransp(txtRucTransportista.getText());
      notaEsCabDTO.setDirTransp(txtDireccionTransportista.getText());
      notaEsCabDTO.setPlacaTransp(txtPlaca.getText());
        //TODO para que?
      notaEsCabDTO.setMotivodescCorta(FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION,notaEsCabDTO.getTipoMotiNotaEs()));
      //TODO para que?
      notaEsCabDTO.setMotivodescLarga(obtieneDescripcionLarga(notaEsCabDTO.getTipoMotiNotaEs().trim(),notaEsCabDTO.getMotivodescCorta().trim()));

    }    

    public String obtieneDescripcionLarga (String pLlaveTabGral, String pDescCorta) {
        String vDescLarga = "";
        try {
        vDescLarga = DBInventario.getObtieneDescLargaTransf(pLlaveTabGral, pDescCorta);
            if(vDescLarga.trim().equalsIgnoreCase("N")){
                vDescLarga = "";
            }
        }catch (SQLException sql){
            log.error("",sql);
            vDescLarga = "";
        }
        return vDescLarga;
    }       
    
    private void mostrarTransportista(){   
       txtPlaca.setText("-");
      try{
            ArrayList array = new ArrayList();
            DBInventario.getDatosTransportista(array,txtRucTransportista.getText());
         
            if(array.size()>0){
                array = (ArrayList)array.get(0);
                String codTransp=array.get(0).toString().trim();
                String rucTransp=array.get(1).toString().trim();
                String nomTransp=array.get(2).toString().trim();
                String dirTransp=array.get(3).toString().trim();
                if(rucTransp.trim()==null || rucTransp.trim().equals(0) || rucTransp.trim().length()!=11){
                    txtRucTransportista.setText("");
                    txtTransportista.setText(nomTransp);
                    txtDireccionTransportista.setText(dirTransp);
                    
                } 
                if(codTransp!=null && rucTransp!=null){
                txtRucTransportista.setText(rucTransp);
                txtTransportista.setText(nomTransp);
                txtDireccionTransportista.setText(dirTransp);
                }
            }
      }catch (SQLException e){
        log.error("",e);
        FarmaUtility.showMessage(this, 
                                 "Ha ocurrido un error al obtener datos del transportista.\n" + 
                                 e.getMessage(), txtRucTransportista);
      }
    }    
    
    private void mostrarDatosProvedor(){
        if(!FarmaLoadCVL.getCVLCode(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION,cmbMotivo.getSelectedIndex()).equals(ConstantsPtoVenta.LISTA_MAESTRO_PROVEEDOR))
        {
          try
          {
            ArrayList array = new ArrayList();
          
            log.debug("Array Datos Transp:"+array.size()+"");
            DBPtoVenta.getDatosProveedor(array,txtCodigoDestino.getText());
            if(array.size()>0)
            {
              array = (ArrayList)array.get(0);
              
              String codProv=array.get(0).toString();
              String senprov=array.get(1).toString();
              String rucProv=array.get(2).toString();
              String dirProv=array.get(3).toString();
              if(codProv!=null){            
              txtSenores.setText(senprov);  
              txtRuc.setText(rucProv);
              txtDireccion.setText(dirProv);
              }
            }
          }catch(SQLException sql)
          {
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurii� un error al obtener datos del Proveedor : \n" + sql.getMessage(),cmbMotivo);
          }
        }
    }
    
    
    public void getCodProveedor(){
        String codigo,nomProve;
    
         codigo=txtCodigoDestino.getText().trim().toString();
         nomProve=txtSenores.getText().toString();
         
         VariablesInventario.vCodProve=codigo;
        VariablesInventario.vNomProve=nomProve; 
    
    }
    
    
    private void validaCodigo(JTextField codProveedor, JTextField descProv, String pTipoMaestro){
      if(codProveedor.getText().trim().length() > 0){
            VariablesPtoVenta.vCodMaestro = codProveedor.getText().trim();
            ArrayList myArray = new ArrayList();
            //myArray.add(VariablesPtoVenta.vTipoMaestro);
            buscaCodigoListaMaestro(myArray);
            
            if(myArray.size() == 0){
              FarmaUtility.showMessage(this,"Registro No Encontrado",codProveedor);
              FarmaVariables.vAceptar = false;
            }else if(myArray.size() == 1){
              String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
              String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
              codProveedor.setText(codigo);
              descProv.setText(descripcion);   
           
              VariablesPtoVenta.vCodMaestro = codigo;
              FarmaVariables.vAceptar = true;
           }else{
              FarmaUtility.showMessage(this,"Se encontro mas de un registro.Verificar!!!",codProveedor);
              FarmaVariables.vAceptar = false;
           }
      }else{
        cargaListaMaestros(pTipoMaestro,codProveedor, descProv);
       }
    }  
    
    private void cargaListaMaestros(String pTipoMaestro, JTextField codProveedor, JTextField descProv){
          VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
          
          DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
          dlgListaMaestros.setVisible(true);
          
          if(FarmaVariables.vAceptar){
            codProveedor.setText(VariablesPtoVenta.vCodMaestro);
            descProv.setText(VariablesPtoVenta.vDescMaestro);
            VariablesPtoVenta.vIdProv = txtCodigoDestino.getText().trim();
                     
          }
    }
    
  
    
    private void buscaCodigoListaMaestro(ArrayList pArrayList){
      try{
        DBPtoVenta.buscaCodigoListaMaestro(pArrayList,VariablesPtoVenta.vTipoMaestro, VariablesPtoVenta.vCodMaestro);
      } catch(SQLException sql){
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ocurri� un error al buscar c�digo en maestros :\n" + sql.getMessage(),cmbMotivo);
      }
    }    
    
    private boolean validarCampos(){
      boolean retorno = true;
       if(cmbMotivo.getSelectedIndex() < 0){
        FarmaUtility.showMessage(this,"Debe seleccionar un Motivo de Transferencia",cmbMotivo);
        retorno = false;
        
      }else if(txtCodigoDestino.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar el Codigo de Destino",txtCodigoDestino);
        retorno = false;
      }else if(txtCodigoDestino.getText().trim().equals(FarmaVariables.vCodLocal)){
        txtCodigoDestino.setText("");
        txtCodigoDestino.setText("");
        FarmaUtility.showMessage(this,"No puede realizar esta transferencia. Modifique el destino.",txtCodigoDestino);
        retorno = false;
        FarmaVariables.vAceptar = false;
        
      }else if(txtSenores.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar la Razon Social",txtSenores);
        retorno = false;
      }else if(UtilityInventario.verificaRucValido(txtRuc.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)){
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRuc);
        retorno = false;
      }else if(txtRuc.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Destinatario",txtRuc);
        retorno = false;
      }else if(txtDireccion.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion de Destino",txtDireccion);
        retorno = false;
      }else if(txtTransportista.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar el Transportista",txtTransportista);
        retorno = false;
      }else if(txtRucTransportista.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar el Ruc del Transportista",txtRucTransportista);
        retorno = false;
      }else if(UtilityInventario.verificaRucValido(txtRucTransportista.getText().trim()).equalsIgnoreCase(ConstantsCliente.RESULTADO_RUC_INVALIDO)){
        FarmaUtility.showMessage(this,"Ingrese un Ruc Valido.",txtRucTransportista);
        retorno = false;
      }else if(txtDireccionTransportista.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar la Direccion del Transportista",txtDireccionTransportista);
        retorno = false;
      }else if(txtPlaca.getText().trim().equals("")){
        FarmaUtility.showMessage(this,"Debe ingresar la Placa del Transportista",txtPlaca);
        retorno = false;
      }
      
      return retorno;
    }

    void setNotaEsCabDTO(NotaEsCabDTO notaEsCabDTO) {
        this.notaEsCabDTO = notaEsCabDTO;
    }

    private void mostrarDatosCabecera() {
        if(notaEsCabDTO.getTipoMotiNotaEs() != null){
            txtCodigoDestino.setText(notaEsCabDTO.getCodDestinoNotaEs());
            FarmaLoadCVL.setSelectedValueInComboBox(cmbMotivo, ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION, notaEsCabDTO.getTipoMotiNotaEs());
            txtSenores.setText(notaEsCabDTO.getDescEmpresa());
            txtRuc.setText(notaEsCabDTO.getRucEmpresa());
            txtDireccion.setText(notaEsCabDTO.getDireEmpresa());
            txtTransportista.setText(notaEsCabDTO.getDescTransp());
            txtRucTransportista.setText(notaEsCabDTO.getRucTransp());
            txtDireccionTransportista.setText(notaEsCabDTO.getDirTransp());
            txtPlaca.setText(notaEsCabDTO.getPlacaTransp());
              //TODO para que?
            notaEsCabDTO.setMotivodescCorta(FarmaLoadCVL.getCVLDescription(ConstantsInventario.NOM_HASHTABLE_CMBMOTIVO_DEVOLUCION,notaEsCabDTO.getTipoMotiNotaEs()));
            //TODO para que?
            notaEsCabDTO.setMotivodescLarga(obtieneDescripcionLarga(notaEsCabDTO.getTipoMotiNotaEs().trim(),notaEsCabDTO.getMotivodescCorta().trim()));        
        }
    }
}

