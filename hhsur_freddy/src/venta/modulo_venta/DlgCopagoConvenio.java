package venta.modulo_venta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgFiltro.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JCORTEZ      17.04.2008   Creaci�n<br>
 * <br>
 * @author Jorge Luis Cortez<br>
 * @version 1.0<br>
 *
 */

public class DlgCopagoConvenio extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgCopagoConvenio.class);
    
  /* ************************************************************************ */
  /*                         DECLARACION PROPIEDADES                          */
  /* ************************************************************************ */
  
  private Frame myParentFrame;
  ArrayList parametros;
  private FarmaTableModel tableModelListaCopago;
  
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnSelec = new JButtonLabel();
  private JScrollPane srcTipo = new JScrollPane();
  private JTable tblListaTipo = new JTable();
  
  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgCopagoConvenio()
  {
    this(null, "", false);
  }

  public DlgCopagoConvenio(Frame parent, String title, boolean modal)
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
      log.error("",e);
    }
  }
  
  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */
 
  private void jbInit()   throws Exception
  {
    this.setSize(new Dimension(329, 176));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setTitle("Seleccione Copago de Convenio ");
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
    
        
    
    
    lblEnter.setBounds(new Rectangle(30, 115, 135, 20));
    lblEnter.setText("[ ENTER ] Elegir");
    lblEsc.setBounds(new Rectangle(175, 115, 140, 20));
    lblEsc.setText("[ ESC ] Escape");
    pnlTitle1.setBounds(new Rectangle(5, 10, 310, 25));
    btnSelec.setText("Seleccione Copago");
    btnSelec.setBounds(new Rectangle(10, 5, 125, 15));
    btnSelec.setMnemonic('S');
    btnSelec.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSelec_actionPerformed(e);
        }
      });
    srcTipo.setBounds(new Rectangle(5, 35, 310, 70));
    tblListaTipo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaTipo_keyPressed(e);
        }
      });
    jContentPane.add(lblEnter, null);
    jContentPane.add(lblEsc, null);
    
    
    srcTipo.getViewport().add(tblListaTipo, null);
    jContentPane.add(srcTipo, null);
    pnlTitle1.add(btnSelec, null);
    jContentPane.add(pnlTitle1, null);
    this.getContentPane().add(jContentPane, null);
 
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    //cargar_cmbTipo();
    initTable();
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */



 private void initTable()
  {
   tableModelListaCopago  = new FarmaTableModel(ConstantsModuloVenta.columnsListaCopago, ConstantsModuloVenta.defaultValuesListaTipoCopago,0);
    FarmaUtility.initSimpleList(tblListaTipo, tableModelListaCopago, ConstantsModuloVenta.columnsListaCopago);
    cargaLista();
  }

  /**
   * Carga los Items  al cmbTipo
   */
  /*private void cargar_cmbTipo(){                   
   //Ubicacion  
   FarmaLoadCVL.loadCVLfromArrays(cmbTipo,ConstantsVentas.NOM_HASTABLE_CMBFILTROTIPO, 
   ConstantsVentas.vCodColumna,ConstantsVentas.vDescColumna,true);
  }*/

  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblListaTipo);
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
      //VariablesVentas.vCodFiltro = FarmaLoadCVL.getCVLCode(ConstantsVentas.NOM_HASTABLE_CMBFILTROTIPO,cmbTipo.getSelectedIndex());
      VariablesConvenioBTLMF.vValorSelCopago=Double.parseDouble(tblListaTipo.getValueAt(tblListaTipo.getSelectedRow(),1).toString());
      VariablesConvenio.vPorcCoPago=String.valueOf(VariablesConvenioBTLMF.vValorSelCopago);
      
      log.debug("Copago Variable : "+VariablesConvenioBTLMF.vValorSelCopago);
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


  private void tblListaTipo_keyPressed(KeyEvent e)
  {
     chkKeyPressed(e);
  }
  
   private void cargaLista(){
    try{
            DBModuloVenta.listaTipoCopago(VariablesConvenioBTLMF.vCodConvenio,tableModelListaCopago);
      if(tblListaTipo.getRowCount()>0)
      {
      FarmaUtility.ordenar(tblListaTipo,tableModelListaCopago,0,FarmaConstants.ORDEN_ASCENDENTE);
      }
    }
    catch(SQLException sql){
      log.error("",sql);
      FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener listado de filtro \n" + sql.getMessage(),tblListaTipo);
    }  
  }

  private void btnSelec_actionPerformed(ActionEvent e)
  {
   FarmaUtility.moveFocus(tblListaTipo);
  }

}