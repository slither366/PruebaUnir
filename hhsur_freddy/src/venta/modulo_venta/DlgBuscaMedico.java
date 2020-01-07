package venta.modulo_venta;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import componentes.gs.componentes.JTextFieldSanSerif;
import java.awt.Rectangle;
import componentes.gs.componentes.JPanelWhite;
import java.awt.GridLayout;
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

public class DlgBuscaMedico extends JDialog 
{
  /* ************************************************************************ */
  /*                        DECLARACION PROPIEDADES                           */
  /* ************************************************************************ */
  private static final Logger log = LoggerFactory.getLogger(DlgBuscaMedico.class); 

  private Frame myParentFrame;
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JButtonLabel lblBusqueda_T = new JButtonLabel();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JTextFieldSanSerif txtBusqueda = 
    new JTextFieldSanSerif();

  /* ************************************************************************ */
  /*                        CONSTRUCTORES                                     */
  /* ************************************************************************ */

  public DlgBuscaMedico()
  {
    this(null, "", false);
  }

  public DlgBuscaMedico(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(426, 138));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Busqueda de Medico");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    lblBusqueda_T.setText("Criterio de Busqueda:");
    lblBusqueda_T.setBounds(new Rectangle(15, 25, 130, 20));
    lblBusqueda_T.setForeground(new Color(255, 130, 14));
    lblBusqueda_T.setMnemonic('C');
    lblF11.setBounds(new Rectangle(195, 70, 95, 20));
    lblF11.setText("[ F11 ] Aceptar");
    lblEsc.setBounds(new Rectangle(305, 70, 100, 20));
    lblEsc.setText("[ ESC ] Cerrar");
        getTxtBusqueda().setBounds(new Rectangle(155, 25, 250, 20));
        getTxtBusqueda().addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
            txtBusqueda_keyPressed(e);
        }
      });
    jContentPane.add(getTxtBusqueda(), null);
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
    FarmaUtility.moveFocus(getTxtBusqueda());
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
        if (venta.reference.UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            seleccionarMedico();
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
        return txtBusqueda;
    }

    public void setTxtBusqueda(JTextFieldSanSerif txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }
}
