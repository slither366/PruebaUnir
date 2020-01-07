package venta.inventario;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
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
import javax.swing.*;

import java.sql.*;

import java.util.ArrayList;

import javax.swing.JDialog;

import common.FarmaLengthText;
import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.caja.reference.VariablesCaja;
import venta.inventario.reference.*;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import txtCantidad.setBounds;

public class DlgModNroComp extends JDialog
{
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgModNroComp.class);

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JLabelFunction lblAceptar = new JLabelFunction();

    private JLabelFunction lblIgnorar = new JLabelFunction();

    private JButtonLabel btnNroComp = new JButtonLabel();

    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JButtonLabel btncant = new JButtonLabel();
    
    private JButtonLabel lblFormato = new JButtonLabel();

    private JComboBox cmbCambioFormatoImp = new JComboBox();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgModNroComp()
    {
        this(null, "", false);
    }

    public DlgModNroComp(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        myParentFrame = parent;
        try
        {
            jbInit();
            initialize();
        } catch (Exception e)
        {
            log.error("",e);
        }
    }

    // **************************************************************************
    // M�todo "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(311, 268));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Modificacion de N�mero de Comprobante");
        this.addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {
                this_windowOpened(e);
            }
        });
        jContentPane.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(10, 10, 280, 185));            
        
        lblFormato.setText("Seleccione Formato de Impresi�n :");
        lblFormato.setBounds(new Rectangle(15, 125, 200, 20));
        lblFormato.setMnemonic('i');    
        
        lblAceptar.setBounds(new Rectangle(95, 210, 95, 20));
        lblAceptar.setRequestFocusEnabled(false);
        lblAceptar.setText("[F11] Aceptar");
        
        lblIgnorar.setBounds(new Rectangle(195, 210, 95, 20));
        lblIgnorar.setRequestFocusEnabled(false);
        lblIgnorar.setText("[ESC] Ignorar");
        
        btnNroComp.setText("Ingrese el nuevo n�mero de comprobante :");
        btnNroComp.setBounds(new Rectangle(15, 10, 260, 20));
        btnNroComp.setMnemonic('i');
        btnNroComp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNroComp_actionPerformed(e);
            }
        });
        txtNroComprobante.setBounds(new Rectangle(60, 40, 155, 20));
        txtNroComprobante.setDocument(new FarmaLengthText(7));
        txtNroComprobante.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                txtNroComprobante_keyPressed(e);
            }

            public void keyTyped(KeyEvent e)
            {
                txtNroComprobante_keyTyped(e);
            }
        });
        
        cmbCambioFormatoImp.setBounds(new Rectangle(60, 150, 155, 20));
        cmbCambioFormatoImp.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            cmbCambioFormatoImp_keyPressed(e);
          }
        });
        
    txtCantidad.setBounds(new Rectangle(60, 95, 155, 20));
    txtCantidad.setDocument(new FarmaLengthText(7));
    txtCantidad.setText("0");
    txtCantidad.setHorizontalAlignment(JTextField.RIGHT);
    txtCantidad.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtCantidad_keyPressed(e);
        }
      });
    btncant.setText("Ingrese la cantidad a imprimir:");
    btncant.setBounds(new Rectangle(15, 65, 180, 20));
    btncant.setMnemonic('i');
    btncant.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNroComp_actionPerformed(e);
        }
      });
                    
        
        jContentPane.add(lblIgnorar, null);
        jContentPane.add(lblAceptar, null);
        jPanelTitle1.add(btncant, null);
        jPanelTitle1.add(txtCantidad, null);
        jPanelTitle1.add(txtNroComprobante, null);
        jPanelTitle1.add(btnNroComp, null);
        jPanelTitle1.add(lblFormato, null);
        jPanelTitle1.add(cmbCambioFormatoImp, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************
    private void initialize()
    {      
      cargaComboIndModelo_FormatoImpresion();
    }
    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************
    private void cargaComboIndModelo_FormatoImpresion()
    {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        FarmaLoadCVL.loadCVLFromSP(cmbCambioFormatoImp,ConstantsPtoVenta.NOM_HASTABLE_CMBFORMATO_IMPRESION,"Farma_Gral.GET_LISTA_FORMATO_IMPRESION(?,?)", parametros,true, true);             
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void btnNroComp_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(txtNroComprobante);
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        mostrarDatos();
        FarmaUtility.moveFocus(txtNroComprobante);
    }

  private void txtNroComprobante_keyPressed(KeyEvent e){
    if(e.getKeyCode()==KeyEvent.VK_ENTER)
    {// Adicion Paulo
     //txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText().trim(),7,"0"));
     FarmaUtility.moveFocus(txtCantidad);
     //Fin Adicion Paulo
    }
        chkKeyPressed(e);
  }
        
  private void txtNroComprobante_keyTyped(KeyEvent e){
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }

  private void txtCantidad_keyPressed(KeyEvent e)
    {
    if(e.getKeyCode()==KeyEvent.VK_ENTER)
    {// Adicion Paulo
     FarmaUtility.moveFocus(cmbCambioFormatoImp);
     //Fin Adicion Paulo
    }
        chkKeyPressed(e);   
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    private void chkKeyPressed(KeyEvent e)
    {

        if (venta.reference.UtilityPtoVenta.verificaVK_F11(e))
        {
            if (datosValidados())
            {
                txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText().trim(),7,"0"));
                
                log.debug("VariablesInventario.vTipoFormatoImpresion : " + VariablesInventario.vTipoFormatoImpresion);
                log.debug("farmaload: " + FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.NOM_HASTABLE_CMBFORMATO_IMPRESION,cmbCambioFormatoImp.getSelectedIndex()));
                log.debug("cmbFormatoImp: " + cmbCambioFormatoImp.getSelectedIndex());
                
                VariablesInventario.vTipoFormatoImpresion=Integer.parseInt(FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.NOM_HASTABLE_CMBFORMATO_IMPRESION,cmbCambioFormatoImp.getSelectedIndex()));  
                
                if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, "Est� seguro de realizar la operaci�n?"))
                {
                    //VariablesCaja.vNumCompImprimir = VariablesCaja.vNumComp.substring(0, 3) + txtNroComprobante.getText().trim();
                    //log.debug("VariablesCaja.vNumCompImprimir(corregido)=" + VariablesCaja.vNumCompImprimir);
                    //inicio Adicion Paulo
                    VariablesInventario.vCant = txtCantidad.getText().trim();
                    log.debug("VariablesInventario.vCant : " + VariablesInventario.vCant);
                    //Fin Adicion Paulo
                    if(guardarNumComp())
                    cerrarVentana(true);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
          
           VariablesCaja.vNumCompImprimir=VariablesCaja.vNumComp.replaceAll("-","");     
           log.debug("VariablesCaja.vNumCompImprimir(default)=" + VariablesCaja.vNumCompImprimir );
           e.consume();
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************

    private void mostrarDatos()
    {
        if (!VariablesCaja.vNumComp.trim().equals(""))
        {
            log.debug("VariablesCaja.vNumComp=" + VariablesCaja.vNumComp);
            txtNroComprobante.setText(VariablesCaja.vNumComp.substring(4, VariablesCaja.vNumComp.length()));
        } else
        {
            txtNroComprobante.setText("");
        }
        FarmaUtility.moveFocus(txtNroComprobante);
    }

    private boolean datosValidados()
    {
        boolean rpta = true;
        String cantidad = txtCantidad.getText().trim();

        if (txtNroComprobante.getText().length() == 0)
        {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe ingresar un Numero de Comprobante ", txtNroComprobante);
        }
        //Adicion Paulo
        if(!FarmaUtility.isInteger(cantidad) || Integer.parseInt(cantidad) < 0 )
        {
            FarmaUtility.showMessage(this, "Ingrese una cantidad valida", txtCantidad);
            return false;
        }
        //Fin Adicion Paulo
        return rpta;
    }
    
    private boolean guardarNumComp()
    {
      boolean retorno;
      try
      {
        DBInventario.reConfiguraCaja( VariablesCaja.vSecImprLocalGuia, txtNroComprobante.getText().trim());
        FarmaUtility.aceptarTransaccion();
        retorno = true;
      }catch(SQLException e)
      {
        FarmaUtility.liberarTransaccion();
        retorno = false;
        log.error("",e);
        FarmaUtility.showMessage(this,"No se grab� el nuevo n�mero de comprobante.\n"+e,txtNroComprobante);
      }
      return retorno;
    }

    private void cmbCambioFormatoImp_keyPressed(KeyEvent e) {
        
        
       if(e.getKeyCode()==KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtNroComprobante);
       else
            chkKeyPressed(e);
                
    }
  
}