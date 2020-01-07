package admcentral.packs;


import admcentral.packs.reference.ConstantsParametros;

import admcentral.packs.reference.VariablesParametros;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;


/**
* Copyright (c) 2007 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : DlgFiltro.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* JCORTEZ      04.09.2007   Creación<br>
* <br>
* @author Jorge Luis Cortez<br>
* @version 1.0<br>
*
*/

public class DlgFiltro extends JDialog
{
  /* ************************************************************************ */
  /*                         DECLARACION PROPIEDADES                          */
  /* ************************************************************************ */
  
  private Frame myParentFrame;
  ArrayList parametros;
  
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelTitle pnlTitle = new JPanelTitle();
  private JButtonLabel btnSeleccion = new JButtonLabel();
  private JComboBox cmbUbicacion = new JComboBox();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  
  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgFiltro()
  {
    this(null, "", false);
  }

  public DlgFiltro(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    try
    {
      jbInit();
      initialize();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */
 
  private void jbInit()   throws Exception
  {
    this.setSize(new Dimension(395, 136));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setTitle("Filtrar Locales");
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
    
    cmbUbicacion.setBounds(new Rectangle(175, 15, 180, 20));
    cmbUbicacion.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            cmbTipo_keyPressed(e);
          }
        });
        
    btnSeleccion.setText("Seleccione Ubicacion");
    btnSeleccion.setBounds(new Rectangle(10, 10, 160, 25));
    btnSeleccion.setBackground(SystemColor.window);
    btnSeleccion.setForeground(new Color(0, 99, 148));
    btnSeleccion.setMnemonic('S');
    btnSeleccion.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnSeleccion_actionPerformed(e);
          }
        });
    
    
    pnlTitle.setBounds(new Rectangle(5, 10, 375, 55));
    pnlTitle.setBackground(SystemColor.window);
    pnlTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lblEnter.setBounds(new Rectangle(95, 75, 135, 20));
    lblEnter.setText("[ ENTER ] Elegir");
    lblEsc.setBounds(new Rectangle(240, 75, 140, 20));
    lblEsc.setText("[ ESC ] Escape");
    
    
    pnlTitle.add(btnSeleccion, null);
    pnlTitle.add(cmbUbicacion, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(pnlTitle, null);
    jContentPane.add(lblEsc, null);
    this.getContentPane().add(jContentPane, null);
 
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    cargar_cmbTipo();
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  /**
   * Carga los Items  al cmbTipo
   */
  private void cargar_cmbTipo(){                       
   //Ubicacion  
   FarmaLoadCVL.loadCVLfromArrays(cmbUbicacion,ConstantsParametros.NOM_HASTABLE_CMBFILTROUBI, 
   ConstantsParametros.vCodColumna,ConstantsParametros.vDescColumna,true);
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(cmbUbicacion);
  }
  
  private void btnSeleccion_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(cmbUbicacion);
  }

  private void cmbTipo_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.",null);
                             
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      VariablesParametros.vCodFiltro = 
      FarmaLoadCVL.getCVLCode(ConstantsParametros.NOM_HASTABLE_CMBFILTROUBI, cmbUbicacion.getSelectedIndex());
      System.out.println("filtro : "+VariablesParametros.vCodFiltro);
      cerrarVentana(true);
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(true);
    }
  }

  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

}