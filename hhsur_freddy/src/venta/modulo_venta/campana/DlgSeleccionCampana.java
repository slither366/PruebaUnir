package venta.modulo_venta.campana;

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
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;

import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;

import venta.fidelizacion.reference.UtilityFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;

import venta.modulo_venta.campana.reference.DBCampana;
import venta.modulo_venta.reference.UtilityModuloVenta;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      17.04.2008   Creación<br>
 * <br>
 * @author Jorge Luis Cortez<br>
 * @version 1.0<br>
 *
 */

public class DlgSeleccionCampana extends JDialog
{
  /* ************************************************************************ */
  /*                         DECLARACION PROPIEDADES                          */
  /* ************************************************************************ */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionCampana.class);

  private Frame myParentFrame;
  ArrayList parametros;
  private FarmaTableModel tableModelListaTipo;
  
  private JPanelWhite jContentPane = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnSelec = new JButtonLabel();
  private JScrollPane srcLista = new JScrollPane();
  private JTable tblLista = new JTable();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane jepMensaje = new JEditorPane();

    /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgSeleccionCampana()
  {
    this(null, "", false);
  }

  public DlgSeleccionCampana(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(692, 251));
    this.getContentPane().setLayout(gridLayout1);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setTitle("Campa\u00f1as Vigentes");
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
    
        
    
    
    lblEnter.setBounds(new Rectangle(25, 185, 135, 20));
    lblEnter.setText("[ ENTER ] Elegir");
    lblEsc.setBounds(new Rectangle(170, 185, 140, 20));
    lblEsc.setText("[ ESC ] Escape");
    pnlTitle1.setBounds(new Rectangle(5, 10, 310, 25));
    btnSelec.setText("Lista de Campa\u00f1as Vigentes");
    btnSelec.setBounds(new Rectangle(10, 5, 190, 15));
    btnSelec.setMnemonic('S');
    btnSelec.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSelec_actionPerformed(e);
        }
      });
    srcLista.setBounds(new Rectangle(5, 35, 310, 135));
    tblLista.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaTipo_keyPressed(e);
        }

                public void keyReleased(KeyEvent e) {
                    tblLista_keyReleased(e);
                }
            });


        jScrollPane1.setBounds(new Rectangle(330, 10, 345, 200));
        jScrollPane1.getViewport().add(jepMensaje, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        srcLista.getViewport().add(tblLista, null);
        jContentPane.add(srcLista, null);
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
   tableModelListaTipo  = 
       new FarmaTableModel(ConstantsModuloVenta.columnsListaCampanaVigente, ConstantsModuloVenta.defaultValuesListaCampanaVigente,0);
    FarmaUtility.initSelectList(tblLista, tableModelListaTipo, ConstantsModuloVenta.columnsListaCampanaVigente);
    cargaLista();
      if(tblLista.getSelectedRow()>=0)
          jepMensaje.setText(FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data, 0,3));
      
      //carga datos de campañas
      Map mapAux = new HashMap();//mapa de la campania del listado de cupones
      String campAux= "";
      for(int i=0;i < VariablesModuloVentas.vArrayList_Cupones.size();i++){
          log.debug("i:"+i);
          mapAux = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(i);
          campAux  = ((String)mapAux.get("COD_CAMP_CUPON")).trim()+"";
          
              for(int a=0;a < tblLista.getRowCount();a++){              
                  if(campAux.equalsIgnoreCase(FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data,a,1))){
                     //marca que ya fue elegido
                      Boolean valor = (Boolean)(tblLista.getValueAt(a, 0));
                      FarmaUtility.setearCheckInRow(tblLista, valor, true, true, 
                                                    FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data,a, 1),1);
                      tblLista.repaint();        
                  }    
              }    
          
          }
      
  }

  /**
   * Carga los Items  al cmbTipo
   */
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblLista);
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
      evento_enter();
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
      DBCampana.listaCampanasVigentes(tableModelListaTipo);
      if(tblLista.getRowCount()>0)
      {
      FarmaUtility.ordenar(tblLista,tableModelListaTipo,0,FarmaConstants.ORDEN_ASCENDENTE);
      }
    }
    catch(SQLException sql){
      log.error("",sql);
      FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener listado de filtro \n" + sql.getMessage(),tblLista);
    }  
  }

  private void btnSelec_actionPerformed(ActionEvent e)
  {
   FarmaUtility.moveFocus(tblLista);
  }

    private void evento_enter() {
        if(tblLista.getSelectedRow()>=0){            
            Boolean valor = (Boolean)(tblLista.getValueAt(tblLista.getSelectedRow(), 0));
            FarmaUtility.setearCheckInRow(tblLista, valor, true, true, 
                                          FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data,tblLista.getSelectedRow(), 1),1);
            tblLista.repaint();            
            if(!valor==false){
                //quitar la campaña
                //BORRA las AUTOMATICAS que ya EXISTAN
                ArrayList cuponesValidos = new ArrayList();
                Map mapAux = new HashMap();//mapa de la campania del listado de cupones
                String campAux= "";
                String codCampCupon = "",nroCupon = "";
                log.debug("Limpiando VAriables");
                for(int i=0;i < VariablesModuloVentas.vArrayList_Cupones.size();i++){
                    log.debug("i:"+i);
                    mapAux = (HashMap)VariablesModuloVentas.vArrayList_Cupones.get(i);
                    campAux  = ((String)mapAux.get("COD_CAMP_CUPON")).trim()+"";
                    
                    if(UtilityFidelizacion.isNumero(campAux)){
                    if(campAux.equalsIgnoreCase(FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data,tblLista.getSelectedRow(),1))){
                        VariablesModuloVentas.vArrayList_Cupones.remove(i);
                    }
                    }
                }
            }
            else{
                //colocar la campaña
                select_campana(tblLista.getSelectedRow());
            }
        }

    }
    
    public void select_campana(int pos){
        Map mapCupon;
        String codCampCupon = FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data,pos, 1);// CODIGO DE CAMPAÑA
        try {
            mapCupon = DBModuloVenta.getDatosCupon(codCampCupon, codCampCupon);
            mapCupon.put("COD_CUPON", codCampCupon);
        } catch (SQLException e) {
            log.debug("ocurrio un error al obtener datos de la campaña:" + 
                      codCampCupon + " error:" + e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al obtener datos de la campaña.\n" +
                    e.getMessage() + 
                    "\n Inténtelo Nuevamente\n si persiste el error comuniquese con sistemas.", 
                    tblLista);
            return;
        }
        log.debug("datosCupon:" + mapCupon);
        
        //Se verifica si el cupon ya fue agregado tambien verifica si la campaña tambien ya esta agregado
        if (UtilityModuloVenta.existeCuponCampana(codCampCupon, this, new JTextField()))
            return;

        log.debug("CAMP CUPON:: codCampCupon " + codCampCupon);
        
        VariablesModuloVentas.vArrayList_Cupones.add(mapCupon);
        UtilityFidelizacion.operaCampañasFidelizacion(VariablesFidelizacion.vNumTarjeta);
        VariablesModuloVentas.vMensCuponIngre = 
                        "Se ha agregado la campaña:\n" +
                        mapCupon.get("DESC_CUPON").toString() + ".";
               /* FarmaUtility.showMessage(this, VariablesModuloVentas.vMensCuponIngre, 
                                         new JTextField());*/
    }

    private void tblLista_keyReleased(KeyEvent e) {
        if(tblLista.getSelectedRow()>=0)
            jepMensaje.setText(FarmaUtility.getValueFieldArrayList(tableModelListaTipo.data, tblLista.getSelectedRow(),3));
    }
}
