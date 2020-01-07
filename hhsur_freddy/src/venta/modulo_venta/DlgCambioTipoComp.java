package venta.modulo_venta;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import componentes.gs.componentes.JTextFieldSanSerif;
import java.awt.Rectangle;
import componentes.gs.componentes.JPanelWhite;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Font;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JButtonLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import componentes.gs.componentes.JLabelFunction;
import java.awt.event.WindowListener;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import common.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.UtilityPtoVenta;

public class DlgCambioTipoComp extends JDialog 
{
  /* ************************************************************************ */
  /*                        DECLARACION PROPIEDADES                           */
  /* ************************************************************************ */
  private static final Logger log = LoggerFactory.getLogger(DlgCambioTipoComp.class); 

  private Frame myParentFrame;
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JButtonLabel lblBusqueda_T = new JButtonLabel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
    private JComboBox cmbTipoComp = new JComboBox();

    /* ************************************************************************ */
  /*                        CONSTRUCTORES                                     */
  /* ************************************************************************ */

  public DlgCambioTipoComp()
  {
    this(null, "", false);
  }

  public DlgCambioTipoComp(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */
        
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(316, 145));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Seleccione el Tipo de Comprobante");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    lblBusqueda_T.setText("Seleccionar el tipo de comprobante que se va emitir:");
    lblBusqueda_T.setBounds(new Rectangle(10, 5, 320, 25));
    lblBusqueda_T.setForeground(new Color(0, 99, 148));
    lblBusqueda_T.setMnemonic('C');
    lblF11.setBounds(new Rectangle(200, 65, 95, 30));
    lblF11.setText("[ F11 ] Aceptar");
    lblEsc.setBounds(new Rectangle(10, 65, 100, 30));
    lblEsc.setText("[ ESC ] Cerrar");
        cmbTipoComp.setBounds(new Rectangle(70, 35, 165, 20));
        cmbTipoComp.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTipoComp_keyPressed(e);
                }
            });

        
        jContentPane.add(cmbTipoComp, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblBusqueda_T, null);
    this.getContentPane().add(jContentPane, null);
        }

  /* ************************************************************************ */
  /*                                 METODO initialize                        */
  /* ************************************************************************ */
        
  private void initialize()
  {    
    FarmaVariables.vAceptar = false;
      cargaCombo();
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */
        
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
         
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
      FarmaUtility.moveFocus(cmbTipoComp); 
      cmbTipoComp.setSelectedIndex(0);
  }
  
  private void txtBusqueda_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
   }
   
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */
        
    private void chkKeyPressed(KeyEvent e)
    {
        if (UtilityPtoVenta.verificaVK_F11(e) ) 
        {
            seleccionaTipoComp();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
        
  private void validaTextoBusqueda()
  {
        VariablesModuloVentas.vMatriculaApe = getTxtBusqueda().getText().trim();
    //Valida si el ingreso es numerico
    if(FarmaUtility.isInteger(VariablesModuloVentas.vMatriculaApe) && VariablesModuloVentas.vMatriculaApe.length() <= 5)
     {
            VariablesModuloVentas.vTipoBusqueda = ConstantsModuloVenta.TIPO_MATRICULA;
            VariablesModuloVentas.vMatriculaApe = FarmaUtility.completeWithSymbol(VariablesModuloVentas.vMatriculaApe,5,"0","I");
      muestraListaMedicos();
     }
     else
  {
            VariablesModuloVentas.vTipoBusqueda = ConstantsModuloVenta.TIPO_APELLIDO;
      if (VariablesModuloVentas.vMatriculaApe.length()< 3 ) 
  {
        FarmaUtility.showMessage(this,"Debe ingresar mas de tres caracteres para realizar la busqueda.",
                                         getTxtBusqueda());
        return ;
        } 
      else 
      muestraListaMedicos();
    }
  }
  
  private void muestraListaMedicos()
  {
    /*DlgListaMedicos dlgListaMedicos = new DlgListaMedicos (myParentFrame,"",true);
    dlgListaMedicos.setVisible(true);
    if ( FarmaVariables.vAceptar ){
        cerrarVentana(true);
    }*/
  }

  /**
   * Verifica el ingreso e indica si se selecciono un medico.
   * @author Edgar Rios
   * @since 06.03.2007
   */
  private void seleccionarMedico()
  {
    validaTextoBusqueda();
    if (FarmaVariables.vAceptar)
    {
            VariablesModuloVentas.vSeleccionaMedico = true;
      cerrarVentana(true);
    }
  }

    public JTextFieldSanSerif getTxtBusqueda() {
        return null;
    }

    public void setTxtBusqueda(JTextFieldSanSerif txtBusqueda) {
        //this.txtBusqueda = txtBusqueda;
    }

    private void seleccionaTipoComp() {
        String vTipoComp = FarmaLoadCVL.getCVLCode("cmbComprobante",cmbTipoComp.getSelectedIndex());
        if(vTipoComp.trim().length()>0){
            VariablesModuloVentas.vTip_Comp_Ped = vTipoComp;
            cerrarVentana(true);
        }
        else
            FarmaUtility.showMessage(this,"Debe seleccionar tipo comprobante", cmbTipoComp);
    }
    
    private void cargaCombo()
    {
        //FarmaLoadCVL.loadCVLfromArrays(cmbTipoComp,
        //                               ConstantsVentas.HASHTABLE_TIPOS_COMPROBANTES,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_CODIGO,
        //                               ConstantsVentas.TIPOS_COMPROBANTES_DESCRIPCION,true);
        
        //LLEIVA 03-Feb-2014 Se realiza la carga desde la BD
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbTipoComp, 
                                   "cmbComprobante",
                                   "PTOVENTA_ADMIN_IMP.IMP_LISTA_TIPOS_COMPROBANTE(?)", 
                                   parametros, 
                                   true);
    }

    private void cmbTipoComp_keyPressed(KeyEvent e) {
        
        chkKeyPressed(e);
    }
}
