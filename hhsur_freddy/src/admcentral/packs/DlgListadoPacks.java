package admcentral.packs;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaUtility;
import common.FarmaVariables;

import admcentral.packs.reference.ConstantsPack;
import admcentral.packs.reference.DBPacks;
import admcentral.packs.reference.VariablesPacks;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import admcentral.packs.DlgMantPack;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaPacks.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      15.09.2008   Creación<br>
 * <br>
 * @author  Javier Callo Quispe <br>
 * @version 1.0<br>
 *
 */
public class DlgListadoPacks extends JDialog {
    
    /* ********************************************************************** */
    /*                            PROPIEDADES                                 */
    /* ********************************************************************** */
    
    private static final Log log = LogFactory.getLog(DlgListadoPacks.class);
    
    boolean vFlagTeclaFx =  false;
    private Frame myParentFrame;    
    FarmaTableModel tableModel;//listado principal
    FarmaTableModel tableModelPaq1;//listado principal
    FarmaTableModel tableModelPaq2;//listado principal
    
    
    //variables para identificar la columna de la tabla
    private final int COL_COD=0;
    private final int COL_DESC_LARGA=1;
    private final int COL_PAQ1=2;
    private final int COL_PAQ2=3;
    //private final int COL_ESTADO=4;
    private final int COL_CANT_MAX_VTA=5;
    private final int COL_DESC_CORTA=6;
    private final int COL_MSG_PACK=7;
    
    // los lbl de las teclas comodines
    private JLabelFunction lblESC = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    
    
    private BorderLayout borderLayout1 = new BorderLayout();//layout principal
    private JPanelWhite jContentPane = new JPanelWhite();//panel de fondo
    private JPanelHeader pnlHeader1 = new JPanelHeader();//panel header verde
    private JPanelTitle pnlTitle1 = new JPanelTitle();//panel title naranja princ
    
    private JPanelTitle pnlPaquete1 = new JPanelTitle();//panel paq 1
    private JPanelTitle pnlPaquete2 = new JPanelTitle();//panel paq 2
    
    private JButtonLabel btnBuscar = new JButtonLabel();//lbl Seleccione Pack
    private JButtonLabel btnPacks = new JButtonLabel();//lbl Seleccione Pack
    
    private JButtonLabel btnPaq1 = new JButtonLabel();//lbl Seleccione Pack
    private JButtonLabel btnPaq2 = new JButtonLabel();//lbl Seleccione Pack
    //private JButton btnBuscar = new JButton();//boton buscar
    private JTextFieldSanSerif txtPack = new JTextFieldSanSerif();//campo texto
    
    
    private JScrollPane scrListaPacks = new JScrollPane();
    private JTable tblListaPacks = new JTable();
    
    private JScrollPane scrListaPaq1 = new JScrollPane();
    private JTable tblListaPaq1 = new JTable();
    
    private JScrollPane scrListaPaq2 = new JScrollPane();
    private JTable tblListaPaq2 = new JTable();
    private JCheckBox filtroVigentes = new JCheckBox();
    private JButton btnBuscar_Dos = new JButton();


    /* ********************************************************************** */
    /*                            CONSTRUCTORES                               */
    /* ********************************************************************** */

    public DlgListadoPacks()
    {
      this(null, "", false);
    }

    public DlgListadoPacks(Frame parent, String title, boolean modal)
    {
      super(parent, title, modal);
      myParentFrame = parent;
      try
      {
        jbInit();
        initialize();
          
          filtroVigentes.setSelected(true);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    
    /* ********************************************************************** */
    /*                           METODO jbInit                                */
    /* ********************************************************************** */
    private void jbInit() throws Exception
    {
      this.setSize(new  Dimension(1074, 473));
      this.getContentPane().setLayout(borderLayout1);
      this.setTitle("Mantenimiento de Packs");
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
      
      pnlHeader1.setBounds(new Rectangle(5, 10, 1035, 50));
      pnlTitle1.setBounds(new Rectangle(5, 65, 1035, 25));
      
      pnlPaquete1.setBounds(new Rectangle(5, 280, 500, 25));
      pnlPaquete2.setBounds(new Rectangle(520, 280, 520, 25));
      
      //btnPaq1
      
      /** label de texto buscar*/
      btnBuscar.setText("Seleccione Pack:");
      btnBuscar.setBounds(new Rectangle(5, 15, 95, 20));
      btnBuscar.setMnemonic('S');
      btnBuscar.setFont(new Font("SansSerif", 1, 11));
      btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
      });
      
      /** label titulo de listado de packs*/
      btnPacks.setText("Listado de Packs:");
      btnPacks.setBounds(new Rectangle(5, 0, 160, 20));
      btnPacks.setMnemonic('P');
      btnPacks.setFont(new Font("SansSerif", 1, 11));
      btnPacks.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            btnPacks_actionPerformed(e);
        }
      });
      
        /** label titulo de listado de paq1*/
        btnPaq1.setText("Productos Paquete 1:");
        btnPaq1.setBounds(new Rectangle(5, 0, 140, 20));
        btnPaq1.setMnemonic('1');
        btnPaq1.setFont(new Font("SansSerif", 1, 11));
        btnPaq1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              btnPaq1_actionPerformed(e);
          }
        });
        
        /** label titulo de listado de paq2*/
        btnPaq2.setText("Productos Paquete 2:");
        btnPaq2.setBounds(new Rectangle(10, 0, 160, 20));
        btnPaq2.setMnemonic('2');
        btnPaq2.setFont(new Font("SansSerif", 1, 11));
        btnPaq2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              btnPaq2_actionPerformed(e);
          }
        });
      
      txtPack.setBounds(new Rectangle(110, 15, 500, 20));
      txtPack.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtPack_keyPressed(e);
          }

          public void keyReleased(KeyEvent e)
          {
            txtPack_keyReleased(e);
          }
        });
              
        scrListaPacks.setBounds(new Rectangle(5, 90, 1035, 185));
        scrListaPacks.setBackground(new Color(255, 130, 14));
        tblListaPacks.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
                tblListaPacks_keyPressed(e);
            }
          });

        /*** paquete 1**/
        scrListaPaq1.setBounds(new Rectangle(5, 305, 500, 110));
        scrListaPaq1.setBackground(new Color(255, 130, 14));
        tblListaPaq1.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
             // tblListaPacks_keyPressed(e);
            }
          });


        /*** paquete 2 ***/
        scrListaPaq2.setBounds(new Rectangle(520, 305, 520, 110));
        scrListaPaq2.setBackground(new Color(255, 130, 14));
        tblListaPaq2.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
               //tblListaPacks_keyPressed(e);
            }
          });


        filtroVigentes.setText("Ver Vigentes");
        filtroVigentes.setBounds(new Rectangle(620, 15, 105, 20));
        filtroVigentes.setFont(new Font("SansSerif", 1, 11));
        filtroVigentes.setForeground(SystemColor.window);
        filtroVigentes.setBackground(new Color(0, 114, 169));
        btnBuscar_Dos.setText("Buscar");
        btnBuscar_Dos.setBounds(new Rectangle(730, 15, 70, 20));
        btnBuscar_Dos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnBuscar_Dos_actionPerformed(e);
                }
            });
        lblF2.setBounds(new Rectangle(100, 430, 117, 19));
        lblF2.setText("[ F2 ] Ver Detalle");
        lblF2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF2_mouseClicked(e);
                }
            });
        lblESC.setBounds(new Rectangle(755, 425, 90, 20));
        lblESC.setText("[ ESC ] Salir");

        lblESC.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblESC_mouseClicked(e);
                }
            });
        lblF1.setBounds(new Rectangle(5, 430, 90, 20));
        lblF1.setText("[ F1 ] Nuevo");

        lblF1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF1_mouseClicked(e);
                }
            });
        lblF5.setBounds(new Rectangle(220, 430, 90, 20));
        lblF5.setText("[ F5 ] Modificar");


        lblF5.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF5_mouseClicked(e);
                }
            });
        jContentPane.setSize(new Dimension(1072, 473));
        pnlHeader1.add(btnBuscar_Dos, null);
        pnlHeader1.add(filtroVigentes, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(txtPack, null);


        scrListaPacks.getViewport().add(tblListaPacks, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(scrListaPacks, null);
        scrListaPaq1.getViewport().add(tblListaPaq1, null);
        jContentPane.add(scrListaPaq1, null);
        scrListaPaq2.getViewport().add(tblListaPaq2, null);
        jContentPane.add(scrListaPaq2, null);
        pnlTitle1.add(btnPacks, null);
        jContentPane.add(pnlTitle1, null);

        pnlPaquete1.add(btnPaq1,null);
        jContentPane.add(pnlPaquete1, null);

        pnlPaquete2.add(btnPaq2,null);
        jContentPane.add(pnlPaquete2, null);
        jContentPane.add(pnlHeader1, null);

        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblESC, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        this.setContentPane(jContentPane);
    }
    
    
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    private void initialize()
    {   
      initTable();
      
      FarmaVariables.vAceptar = false;
      FarmaUtility.moveFocus(txtPack);
      
      cargaListaPacks();
      
      
      
      
      
    }
    
    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable()
    {
        //System.out.println(" inicializando table ... ");
        tableModel = new FarmaTableModel(ConstantsPack.columnsListaPack,
                                       ConstantsPack.defaultValuesListaPack,0);              
        FarmaUtility.initSimpleList(tblListaPacks,tableModel,ConstantsPack.columnsListaPack);        
        
        
        tableModelPaq1 = new FarmaTableModel(ConstantsPack.columnsListaPaquete,
                                       ConstantsPack.defaultValuesListaPaquete,0);              
        FarmaUtility.initSimpleList(tblListaPaq1,tableModelPaq1,ConstantsPack.columnsListaPaquete);        
        
        tableModelPaq2 = new FarmaTableModel(ConstantsPack.columnsListaPaquete,
                                       ConstantsPack.defaultValuesListaPaquete,0);              
        FarmaUtility.initSimpleList(tblListaPaq2,tableModelPaq2,ConstantsPack.columnsListaPaquete);        
        
        //System.out.print(" OK ");
    }
    
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    /**
     *  Cargamos el listado de packs 
     */
    private void cargaListaPacks(){
        try
         {
            if(filtroVigentes.isSelected())
                DBPacks.listadoPacks(tableModel,"S");      
            else
                DBPacks.listadoPacks(tableModel,"N");      
            FarmaUtility.ordenar(tblListaPacks,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
             /** seleccionado el primer elemento**/
             if(tableModel.getRowCount()>0){                        
               tblListaPacks.setRowSelectionInterval(0,0);
             }
             
             cargaListaPaquetes();
         }catch(SQLException sql)
         {
          sql.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrio un error al listar los packs.\n"+sql.getMessage(),txtPack);
         }
    }
    
    /**
     *  Cargamos el listado de productos del paquete1 
     */
    private void cargaListaPaquetes(){
        try
         {
            if (tblListaPacks.getSelectedRow()>-1)  {
                int row = tblListaPacks.getSelectedRow();
                if( tableModel.getRowCount() > 0 ){
                    String codPaq1 = tableModel.getValueAt(row,2).toString();
                    String codPaq2 = tableModel.getValueAt(row,3).toString();
                    
                    //System.out.println("cargando paquetes codPaq_1:"+codPaq1+",  cod_pàq2:"+codPaq2);
                    
                    //cargando listado de paquete 1
                    DBPacks.listadoPaquete( tableModelPaq1,codPaq1 );
                    //cargando listado de paquete 2
                    DBPacks.listadoPaquete( tableModelPaq2,codPaq2 );
                }                    
            }
            
            
            
         }catch(SQLException sql)
         {
          sql.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrio un error al listar los packs.\n"+sql.getMessage(),txtPack);
         }
    }
    
    
    private void verPack()
    {
        
        VariablesPacks.Vg_Cod_Pack = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_COD).toString();
        VariablesPacks.Vg_Cod_Paq1 = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_PAQ1).toString();
        VariablesPacks.Vg_Cod_Paq2 = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_PAQ2).toString();
        
        VariablesPacks.Vg_Desc_Corta = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_DESC_CORTA ).toString();
        VariablesPacks.Vg_Desc_Larga = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_DESC_LARGA ).toString();
        VariablesPacks.Vg_Cant_Max = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_CANT_MAX_VTA ).toString();
        VariablesPacks.Vg_Msg_Pack = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_MSG_PACK ).toString();
        
        DlgMantPack dlgMantPack = new DlgMantPack(this.myParentFrame,"",true);
        dlgMantPack.setVisible(true);        
        /*if(FarmaVariables.vAceptar)
        {           
           cargaListaPacks();
        }*/
    }
    
    private void modificarPack()
    {
        
        VariablesPacks.Vg_Cod_Pack = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_COD).toString();
        VariablesPacks.Vg_Cod_Paq1 = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_PAQ1).toString();
        VariablesPacks.Vg_Cod_Paq2 = tblListaPacks.getValueAt(tblListaPacks.getSelectedRow(),COL_PAQ2).toString();
        
        VariablesPacks.Vg_Desc_Corta = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_DESC_CORTA ).toString();
        VariablesPacks.Vg_Desc_Larga = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_DESC_LARGA ).toString();
        VariablesPacks.Vg_Cant_Max = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_CANT_MAX_VTA ).toString();
        VariablesPacks.Vg_Msg_Pack = tableModel.getValueAt(tblListaPacks.getSelectedRow(),COL_MSG_PACK ).toString();
        
        DlgMantPack dlgMantPack = new DlgMantPack(this.myParentFrame,"",true);
        dlgMantPack.setVisible(true);        
        if(FarmaVariables.vAceptar)
        {           
           cargaListaPacks();
        }
    }
    
    private void nuevoPack()
    {
         DlgMantPack dlgMantPack = new DlgMantPack(this.myParentFrame,"",true);
         dlgMantPack.setVisible(true);        
         if(FarmaVariables.vAceptar)
         {           
            cargaListaPacks();
         }
    }
    
    
    /* ********************************************************************** */
    /*                            METODOS DE EVENTOS                          */
    /* ********************************************************************** */
    
    private void this_windowOpened(WindowEvent e)
    {
      FarmaUtility.centrarVentana(this);
      /** seleccionado el primer elemento**/
        /*if(tableModel.getRowCount()>0){                  
            tblListaPacks.changeSelection(0,0,true,false);
        }*/
      FarmaUtility.moveFocus(txtPack);
    }
    
    private void this_windowClosing(WindowEvent e)
    {
   //  FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.",null);
    evento_esc();
    }
    
    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPack);
    }
    
    private void btnPacks_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPack);
    }
    
    private void btnPaq1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPaq1);
    }
    
    private void btnPaq2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPaq2);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

      private void chkKeyPressed(KeyEvent e)
      {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !vFlagTeclaFx)
        {
          evento_esc();
        }else if(e.getKeyCode() == KeyEvent.VK_F1 && !vFlagTeclaFx) {
            evento_f1();
        }else if(e.getKeyCode() == KeyEvent.VK_F2 && !vFlagTeclaFx) {
            evento_F2();
        }else if(e.getKeyCode() == KeyEvent.VK_F5 && !vFlagTeclaFx) {
         evento_f5();
        }
      }
    
    private void txtPack_keyPressed(KeyEvent e)
    {
     FarmaGridUtils.aceptarTeclaPresionada(e,tblListaPacks, txtPack,COL_DESC_LARGA);
     
     if (e.getKeyCode() == KeyEvent.VK_ENTER)
     {
       //System.out.println("EVENTO ENTER");
       e.consume();
       if (tblListaPacks.getSelectedRow() >-1)
       {
        if(txtPack.getText().trim().length()>0){
        
         /*if(FarmaUtility.isInteger(txtPack.getText().trim())){
          txtPack.setText(FarmaUtility.completeWithSymbol(txtPack.getText(), DIG_COD, "0", "I")); */
          FarmaUtility.findTextInJTable(tblListaPacks, txtPack.getText().trim(),COL_COD, COL_DESC_LARGA);
         }
        
         if(!(FarmaUtility.findTextInJTable(tblListaPacks, txtPack.getText().trim(),COL_COD, COL_DESC_LARGA)) ) //COL_TIP
         {
            FarmaUtility.showMessage(this,"Equipos No Encontrado según Criterio de Búsqueda !!!", txtPack);
         }else{
            cargaListaPaquetes();
         }
        //}
       }
      } 
      chkKeyPressed(e);
    }

    private void txtPack_keyReleased(KeyEvent e)
    {
     if(FarmaGridUtils.buscarDescripcion(e, tblListaPacks, txtPack, 1)
       ||e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_DOWN||
       e.getKeyCode()==KeyEvent.VK_PAGE_DOWN||e.getKeyCode()==KeyEvent.VK_PAGE_UP||
       e.getKeyCode()==KeyEvent.VK_ENTER){
       //System.out.println(".. cargar lista paquetes ... ");
       cargaListaPaquetes();
       //System.out.println(".. fin cargar lista paquetes ... ");
     }      
    }
    
    
    private void tblListaPacks_keyPressed(KeyEvent e)
    {
        
    }
    
    
      
    private void cerrarVentana(boolean pAceptar) 
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnBuscar_Dos_actionPerformed(ActionEvent e) {
        cargaListaPacks();
    }


    private void evento_f1() {
        //System.out.println("entro a crear y mostrar la pantalla de registro de nuevo pack");
        vFlagTeclaFx = true;
        VariablesPacks.ACCION = ConstantsPack.ACCION_CREAR;
        nuevoPack();
        vFlagTeclaFx = false;
        //System.out.println("FIN MUESTRA DETALLE PACK");
    }

    private void evento_F2() {
        //System.out.println("**** ver detalle pack ***");
        vFlagTeclaFx = true;            
        VariablesPacks.ACCION = ConstantsPack.ACCION_VER;
        verPack();
        vFlagTeclaFx = false;
        //System.out.println("FIN MUESTRA DETALLE PACK");
    }

    private void evento_f5() {
        //System.out.println("entro a crear y mostrar la pantalla de MODIFICAR de pack");
        vFlagTeclaFx = true;
        VariablesPacks.ACCION = ConstantsPack.ACCION_MODIFICAR;
        modificarPack();
        vFlagTeclaFx = false;
        //System.out.println("FIN MUESTRA DETALLE PACK");
    }

    private void lblF5_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
            evento_f5();
    }

    private void lblF2_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
            evento_F2();
    }

    private void lblF1_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
            evento_f1();
    }

    private void evento_esc() {
        vFlagTeclaFx = true;
        cerrarVentana(false);
        vFlagTeclaFx = false;
    }

    private void lblESC_mouseClicked(MouseEvent e) {
        evento_esc();
    }
}
