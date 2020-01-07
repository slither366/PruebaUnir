package admcentral.packs;


import admcentral.packs.DlgMantPaqueteProd;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import admcentral.packs.reference.ConstantsPack;
import admcentral.packs.reference.DBPacks;
import admcentral.packs.reference.VariablesPacks;

import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMantConvenio.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      16.09.2008   Creación
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */

public class DlgMantPack extends JDialog {
    
    private static final Log log = LogFactory.getLog(DlgMantPack.class);
    
    //variables para identificar la columna de la tabla
    /*private final int COL_CHECK=0;
    private final int COL_COD=1;
    private final int COL_DESC=2;
    private final int COL_LAB=2;
    private final int COL_CANT_MAX_VTA=3;*/
    
    boolean vFlagTeclaFx =  false;
    
    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JPanelWhite pnlDatosGen = new JPanelWhite();
    
    private JPanelWhite pnlPaquete1 = new JPanelWhite();
    private JPanelWhite pnlPaquete2 = new JPanelWhite();
    private JPanelTitle pnlTitleDatos = new JPanelTitle();
    private JButtonLabel btnDatosGen = new JButtonLabel();
    private JPanelTitle pnlTitlePaq1 = new JPanelTitle();
    private JPanelTitle pnlTitlePaq2 = new JPanelTitle();
    private JButtonLabel btnPaquete1 = new JButtonLabel();
    private JButtonLabel btnPaquete2 = new JButtonLabel();
    
    private JLabelFunction lblFAceptar = new JLabelFunction();    
    private JLabelFunction lblFSalir = new JLabelFunction();    
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    
    private JTextFieldSanSerif txtDescCorta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescLarga = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantMaxVta = new JTextFieldSanSerif();

    private JButtonLabel btnDescCorta = new JButtonLabel();
    private JButtonLabel btnDescLarga = new JButtonLabel();    
    private JButtonLabel btnCantMax = new JButtonLabel();
    private JButtonLabel btnMsgPack = new JButtonLabel();
    
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    
    private JScrollPane scrListaPaq1 = new JScrollPane();
    private JTable tblListaPaq1 = new JTable();
    
    private JScrollPane scrListaPaq2 = new JScrollPane();
    private JTable tblListaPaq2 = new JTable();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtDesde = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtHasta = new JTextField();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtMsgPack = new JTextArea();

    // **************************************************************************
    // Constructor
    // **************************************************************************

    public DlgMantPack(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(629, 553));
        this.setTitle("Mantenimiento de Packs");
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlDatosGen.setBounds(new Rectangle(10, 25, 605, 185));
        pnlDatosGen.setBorder(BorderFactory.createTitledBorder(""));
                
        pnlPaquete1.setBounds(new Rectangle(10, 230, 615, 125));
        pnlPaquete1.setBorder(BorderFactory.createTitledBorder(""));
        
        pnlPaquete2.setBounds(new Rectangle(10, 380, 610, 120));
        pnlPaquete2.setBorder(BorderFactory.createTitledBorder(""));
        
        pnlTitleDatos.setBounds(new Rectangle(10, 5, 600, 20));
        btnDatosGen.setText("Datos Generales");
        btnDatosGen.setBounds(new Rectangle(15, 0, 125, 20));
        btnDatosGen.setMnemonic('d');
        pnlTitlePaq1.setBounds(new Rectangle(10, 210, 595, 20));
        pnlTitlePaq2.setBounds(new Rectangle(10, 360, 595, 20));
        btnPaquete1.setText("PAQUETE 1: Listado de Produtos");
        btnPaquete1.setBounds(new Rectangle(15, 0, 220, 20));
        btnPaquete1.setMnemonic('1');
        btnPaquete1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              btnPaquete1_actionPerformed(e);
          }
        });
        
        btnPaquete2.setText("PAQUETE 2: Listado de Produtos");
        btnPaquete2.setBounds(new Rectangle(15, 0, 215, 20));
        btnPaquete2.setMnemonic('2');
        btnPaquete2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              btnPaquete2_actionPerformed(e);
          }
        });
        
        lblFAceptar.setBounds(new Rectangle(380, 505, 100, 20));
        lblFAceptar.setText("[ F11 ] Aceptar");
        lblFAceptar.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblFAceptar_mouseClicked(e);
                }
            });
        lblFSalir.setBounds(new Rectangle(495, 505, 95, 20));
        lblFSalir.setText("[ ESC ] Salir");

        lblFSalir.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblFSalir_mouseClicked(e);
                }
            });
        lblF1.setBounds(new Rectangle(10, 505, 175, 20));
        lblF1.setText("[ F1 ] Agregar Prod. Paquete 1");

        lblF1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF1_mouseClicked(e);
                }
            });
        lblF2.setBounds(new Rectangle(195, 505, 175, 20));
        lblF2.setText("[ F2 ] Agregar Prod. Paquete 2");

        lblF2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblF2_mouseClicked(e);
                }
            });
        txtDescCorta.setBounds(new Rectangle(125, 5, 240, 20));
        txtDescCorta.setLengthText(30);
        txtDescCorta.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtDescCorta_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtDescCorta_keyTyped(e);
                    }
                });
        
        txtDescLarga.setBounds(new Rectangle(125, 35, 460, 20));
        txtDescLarga.setLengthText(30);
        txtDescLarga.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtDescLarga_keyPressed(e);
                }

                    public void keyTyped(KeyEvent e) {
                        txtDescLarga_keyTyped(e);
                    }
                });
        
        txtCantMaxVta.setBounds(new Rectangle(505, 155, 90, 20));
        txtCantMaxVta.setLengthText(3);
        txtCantMaxVta.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtCantMaxVta_keyPressed(e);
                }

                    public void keyTyped(KeyEvent e) {
                        txtCantMaxVta_keyTyped(e);
                    }
                });


        btnDescCorta.setText("Descripción Corta:");
        btnDescCorta.setBounds(new Rectangle(10, 5, 110, 20));
        btnDescCorta.setMnemonic('D');
        btnDescCorta.setForeground(new Color(0, 107, 165));
        btnDescCorta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDescCorta_actionPerformed(e);
                    }
                });
        
        btnDescLarga.setText("Descripción Larga:");
        btnDescLarga.setBounds(new Rectangle(10, 30, 110, 20));
        btnDescLarga.setMnemonic('L');
        btnDescLarga.setForeground(new Color(0, 107, 165));
        btnDescLarga.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDescLarga_actionPerformed(e);
                    }
                });
        
        btnCantMax.setText("Cant. Max Venta:");
        btnCantMax.setBounds(new Rectangle(405, 155, 110, 20));
        btnCantMax.setMnemonic('C');
        btnCantMax.setForeground(new Color(0, 107, 165));
        btnCantMax.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCantMax_actionPerformed(e);
                    }
                });
        
        btnMsgPack.setText("Mensaje Pack:");
        btnMsgPack.setBounds(new Rectangle(10, 50, 80, 20));
        btnMsgPack.setMnemonic('M');
        btnMsgPack.setForeground(new Color(0, 107, 165));
        btnMsgPack.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMsgPack_actionPerformed(e);
                    }
                });
        
        
        
        /**agregando los lbl**/
        jScrollPane1.getViewport().add(txtMsgPack, null);
        pnlDatosGen.add(jScrollPane1, null);
        pnlDatosGen.add(txtHasta, null);
        pnlDatosGen.add(jLabel2, null);
        pnlDatosGen.add(txtDesde, null);
        pnlDatosGen.add(btnDescCorta, null);
        pnlDatosGen.add(btnDescLarga, null);

        /**agregando los textfields**/
        pnlDatosGen.add(btnCantMax, null);
        pnlDatosGen.add(btnMsgPack, null);


        pnlDatosGen.add(txtDescCorta, null);
        pnlDatosGen.add(txtDescLarga, null);
        pnlDatosGen.add(txtCantMaxVta, null);
        pnlDatosGen.add(jLabel1, null);
        pnlBlanco.add(lblFSalir, null);
        pnlBlanco.add(lblFAceptar, null);

        pnlBlanco.add(lblF1, null);
        pnlBlanco.add(lblF2, null);
        pnlTitlePaq1.add(btnPaquete1, null);
        pnlBlanco.add(pnlTitlePaq1, null);
        pnlTitlePaq2.add(btnPaquete2, null);
        pnlBlanco.add(pnlTitlePaq2, null);
        pnlTitleDatos.add(btnDatosGen, null);
        pnlBlanco.add(pnlTitleDatos, null);

        /*** paquete 1**/
        scrListaPaq1.getViewport().add(tblListaPaq1, null);
        pnlPaquete1.add(scrListaPaq1, null);
        pnlBlanco.add(pnlPaquete1, null);
        scrListaPaq2.getViewport().add(tblListaPaq2, null);
        pnlPaquete2.add(scrListaPaq2, null);
        pnlBlanco.add(pnlPaquete2, null);
        scrListaPaq1.setBounds(new Rectangle(5, 5, 590, 115));
        scrListaPaq1.setBackground(new Color(255, 130, 14));
        tblListaPaq1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblListaPaq1_keyPressed(e);
                }
            });


        /*** paquete 2 ***/
        scrListaPaq2.setBounds(new Rectangle(5, 5, 585, 110));
        scrListaPaq2.setBackground(new Color(255, 130, 14));
        tblListaPaq2.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblListaPaq2_keyPressed(e);
                }
            });


        jLabel1.setText("Desde :");
        jLabel1.setBounds(new Rectangle(15, 160, 45, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 107, 165));
        txtDesde.setBounds(new Rectangle(60, 155, 135, 20));
        txtDesde.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDesde_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtDesde_keyReleased(e);
                }
            });
        jLabel2.setText("Hasta :");
        jLabel2.setBounds(new Rectangle(205, 160, 50, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(new Color(0, 107, 165));
        txtHasta.setBounds(new Rectangle(255, 155, 135, 20));
        txtHasta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtHasta_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtHasta_keyReleased(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 70, 585, 80));
        txtMsgPack.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtMsgPack_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtMsgPack_keyReleased(e);
                }
            });
        pnlBlanco.add(pnlDatosGen, null);
        //this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
        this.setContentPane(pnlBlanco);
        pnlBlanco.setSize(new Dimension(629, 553));
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        
        initTable();
        
        cargaListaPaquetes();
        
        
        
        /** seleccionado el primer elemento**/
        if(tblListaPaq1.getRowCount()>0){                  
            tblListaPaq1.changeSelection(0,0,true,false);
        }
        if(tblListaPaq2.getRowCount()>0){                  
            tblListaPaq2.changeSelection(0,0,true,false);
        }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************
    private void initTable()
    {
        //System.out.println(" inicializando tables ... ");
        
        VariablesPacks.tblModelListaGlobalPaquete1 = new FarmaTableModel(ConstantsPack.columnsListaPaqueteAll,
                                       ConstantsPack.defaultValuesListaPaqueteAll,0);              
        //FarmaUtility.initSelectList(tblListaPaq1,tableModelPaq1,ConstantsAdministracion.columnsListaPaquete);
        FarmaUtility.initSimpleList(tblListaPaq1,VariablesPacks.tblModelListaGlobalPaquete1,ConstantsPack.columnsListaPaqueteAll);
        
        VariablesPacks.tblModelListaGlobalPaquete2 = new FarmaTableModel(ConstantsPack.columnsListaPaqueteAll,
                                       ConstantsPack.defaultValuesListaPaqueteAll,0);              
        //FarmaUtility.initSelectList(tblListaPaq2,tableModelPaq2,ConstantsAdministracion.columnsListaPaquete);
        FarmaUtility.initSimpleList(tblListaPaq2,VariablesPacks.tblModelListaGlobalPaquete2,ConstantsPack.columnsListaPaqueteAll);
        
        /** inicializando listado paquetes**/
        VariablesPacks.listaPaquete1 = new ArrayList();
        VariablesPacks.listaPaquete2 = new ArrayList();
        /** fin inicializando listado paquetes**/
        
        //System.out.print(" OK ");
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescCorta);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    /*** teclas comodines ALT+TECLA NEMOTECNICA**/
    private void btnDescCorta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescCorta);
    }
    
    private void btnDescLarga_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescLarga);
    }
    
    private void btnPorcDcto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantMaxVta);
    }
    
    private void btnCantMax_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantMaxVta);
    }
    
    private void btnMsgPack_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMsgPack);
    }
    
    private void btnPaquete1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPaq1);
    }
    
    private void btnPaquete2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPaq2);
    }
    
    /*** fin teclas comodines ALT+TECLA NEMOTECNICA**/

    /**eventos en los textos**/
    private void txtDescCorta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescLarga);
        } else
            chkKeyPressed(e);
    }

    private void txtDescLarga_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtMsgPack);
        } else
            chkKeyPressed(e);
    }

    private void txtCantMaxVta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescCorta);
        } else
            chkKeyPressed(e);
    }
    
    private void txtMsgPack_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtDesde);
        } else
            chkKeyPressed(e);
    }
    /** fin eventos en los textos**/

    /*** validacion de campos al teclear***/
    private void txtDescCorta_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtDescCorta, e);
    }

    private void txtDescLarga_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtDescLarga, e);
    }

    private void txtCantMaxVta_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantMaxVta, e);
    }

    private void txtMsgPack_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirLetras(txtMsgPack, e);
    }
    /*** FIN validacion de campos al teclear***/
    
    
    private void tblListaPaq1_keyPressed(KeyEvent e) {
        
    }
    
    private void tblListaPaq2_keyPressed(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtFecFin, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !vFlagTeclaFx) {
           eventoEscape();
        } else if ( !VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_VER)) {
          if (e.getKeyCode() == KeyEvent.VK_F11 && !vFlagTeclaFx) {
              eventoF11();
          } else if (e.getKeyCode() == KeyEvent.VK_F1 && !vFlagTeclaFx) {//agregar producto en paquete1
            eventoF1();
          } else if (e.getKeyCode() == KeyEvent.VK_F2 && !vFlagTeclaFx) {//agregar producto en paquete1
            eventoF2();
          }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public void muestraDetalleProductoPaqueteNuevo()
    {           
        DlgMantPaqueteProd DlgMantPaqueteProd = new DlgMantPaqueteProd(this.myParentFrame,"",true);
        
        DlgMantPaqueteProd.setVisible(true);        
        if(FarmaVariables.vAceptar)
        {           
           //listarPatologias();
        }
    }


    private void cargaListaPaquetes() {
        try {
            
            //System.out.println("cargando mant pack ACCION:"+VariablesPacks.ACCION);
            
            if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR)){
                DBPacks.listadoPaquete(VariablesPacks.tblModelListaGlobalPaquete1,VariablesPacks.Vg_Cod_Paq1);
                DBPacks.listadoPaquete(VariablesPacks.tblModelListaGlobalPaquete2,VariablesPacks.Vg_Cod_Paq2);                
                
                /**
                 * seteando los valores auxiliares
                 * **/
                
                ArrayList lProd ;
                
                for(int i=0;i<VariablesPacks.tblModelListaGlobalPaquete1.getRowCount();i++){
                    lProd = new ArrayList();
                    
                    lProd.add( 0 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,0));
                    lProd.add( 1 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,1));
                    lProd.add( 2 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,2));
                    lProd.add( 3 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,3));
                    lProd.add( 4 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,4));
                    lProd.add( 5 ,ConstantsPack.ACCION_NINGUNA_PROD_PAQ);
                    
                    VariablesPacks.listaPaquete1.add(i,lProd);
                    
                }
                
                for(int i=0;i<VariablesPacks.tblModelListaGlobalPaquete2.getRowCount();i++){
                    lProd = new ArrayList();
                    
                    lProd.add( 0 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,0));
                    lProd.add( 1 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,1));
                    lProd.add( 2 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,2));
                    lProd.add( 3 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,3));
                    lProd.add( 4 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,4));
                    lProd.add( 5 ,ConstantsPack.ACCION_NINGUNA_PROD_PAQ);
                    
                    VariablesPacks.listaPaquete2.add(i,lProd);
                }
                
                
                
                
                /**
                 * seteando los valores del detalle del pack
                 * **/
                
                txtDescCorta.setText(VariablesPacks.Vg_Desc_Corta);
                txtDescLarga.setText(VariablesPacks.Vg_Desc_Larga);
                txtCantMaxVta.setText(VariablesPacks.Vg_Cant_Max);
                txtMsgPack.setText(VariablesPacks.Vg_Msg_Pack);
                
            } else if(VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_VER)){
                
                lblF1.setVisible(false);
                lblF2.setVisible(false);
                lblFAceptar.setVisible(false);
                
                DBPacks.listadoPaquete(VariablesPacks.tblModelListaGlobalPaquete1,VariablesPacks.Vg_Cod_Paq1);
                DBPacks.listadoPaquete(VariablesPacks.tblModelListaGlobalPaquete2,VariablesPacks.Vg_Cod_Paq2);                
                
                /**
                 * seteando los valores auxiliares
                 * **/
                
                ArrayList lProd ;
                
                for(int i=0;i<VariablesPacks.tblModelListaGlobalPaquete1.getRowCount();i++){
                    lProd = new ArrayList();
                    
                    lProd.add( 0 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,0));
                    lProd.add( 1 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,1));
                    lProd.add( 2 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,2));
                    lProd.add( 3 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,3));
                    lProd.add( 4 ,VariablesPacks.tblModelListaGlobalPaquete1.getValueAt(i,4));
                    
                    VariablesPacks.listaPaquete1.add(i,lProd);
                    
                }
                
                for(int i=0;i<VariablesPacks.tblModelListaGlobalPaquete2.getRowCount();i++){
                    lProd = new ArrayList();
                    
                    lProd.add( 0 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,0));
                    lProd.add( 1 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,1));
                    lProd.add( 2 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,2));
                    lProd.add( 3 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,3));
                    lProd.add( 4 ,VariablesPacks.tblModelListaGlobalPaquete2.getValueAt(i,4));
                    
                    VariablesPacks.listaPaquete2.add(i,lProd);
                }
                
                
                
                
                /**
                 * seteando los valores del detalle del pack
                 * **/
                
                txtDescCorta.setText(VariablesPacks.Vg_Desc_Corta);
                txtDescLarga.setText(VariablesPacks.Vg_Desc_Larga);
                txtCantMaxVta.setText(VariablesPacks.Vg_Cant_Max);
                txtMsgPack.setText(VariablesPacks.Vg_Msg_Pack);
                
                txtDescCorta.setEditable(false);
                txtDescLarga.setEditable(false);
                txtCantMaxVta.setEditable(false);
                txtMsgPack.setEditable(false);
                
            }
        } catch (SQLException sql) {            
            sql.printStackTrace();
            FarmaUtility.showMessage(this, "Error al crear convenio: \n" +
                    sql.getMessage(), txtDescCorta);
        }
    }

    private boolean validaIngreso() {
        if (txtDescCorta.getText().trim().length()<1) {
            FarmaUtility.showMessage(this, 
                                 "Debe ingresar la descripción corta del Pack.", 
                                 txtDescCorta);
            return false;
        } else if(txtDescLarga.getText().trim().length()<1) {
            FarmaUtility.showMessage(this,
                                 "Debe ingresar la descripción larga del Pack.",
                                  txtDescLarga);
            return false;
        } else if (txtCantMaxVta.getText().trim().length()<1) {
            FarmaUtility.showMessage(this, 
                                     "Debe ingresar Cantidad Maxima de Venta.", 
                                     txtCantMaxVta);
            return false;
        } else if (txtMsgPack.getText().trim().length()<1) {
            FarmaUtility.showMessage(this, 
                                     "Debe ingresar Mensaje del Pack!.", 
                                     txtMsgPack);
            return false;
        } else if (VariablesPacks.tblModelListaGlobalPaquete1.getRowCount()<1) {
            FarmaUtility.showMessage(this, 
                                     "Debe especificar al menos un producto en el "+ConstantsPack.PAQUETE_1+"!.", 
                                     txtMsgPack);
            return false;
        } else if (VariablesPacks.tblModelListaGlobalPaquete2.getRowCount()<1) {
            FarmaUtility.showMessage(this, 
                                     "Debe especificar al menos un producto en el "+ConstantsPack.PAQUETE_2+"!.", 
                                     txtMsgPack);
            return false;
        } else {
            return true;
        }
    }

    
    private void crearPack() {
        try {
            //System.out.println("EMPEZANDO A INSERTAR EL PACK ...");
            
            //System.out.println("1.- INSERTANDO EL PRIMER PAQUETE 1...");            
            String codPaq1 = DBPacks.insertarPaquete().trim();
            //System.out.print(" OK");
            //System.out.println("2.- INSERTANDO EL PRIMER PAQUETE 2...");
            String codPaq2 = DBPacks.insertarPaquete().trim();
            //System.out.print(" OK");            
            //System.out.println("3.- INSERTANDO LOS PRODUCTOS DEL PAQUETE 1 : "+codPaq1+"**");
            ArrayList lProd;
            for(int i=0;i<VariablesPacks.listaPaquete1.size();i++){
                
                lProd = (ArrayList)VariablesPacks.listaPaquete1.get(i);
                
                DBPacks.insertarProductoPaquete( codPaq1, 
                                                 lProd.get(0).toString(),
                                                 lProd.get(2).toString(),
                                                 lProd.get(3).toString(),
                                                 lProd.get(4).toString());    
                
            }
            
            //System.out.print(" OK");
            
            //System.out.println("4.- INSERTANDO LOS PRODUCTOS DEL PAQUETE 2...");
            for(int i=0;i<VariablesPacks.listaPaquete2.size();i++){
                
                lProd = (ArrayList)VariablesPacks.listaPaquete2.get(i);
                
                DBPacks.insertarProductoPaquete( codPaq2, 
                                                 lProd.get(0).toString(),
                                                 lProd.get(2).toString(),
                                                 lProd.get(3).toString(),
                                                 lProd.get(4).toString());    
                
            }
            
            //System.out.print(" OK");
            
            //System.out.println("5.- INSERTANDO EL PACK...");
            DBPacks.insertarPack(txtDescCorta.getText().trim(),
                                 txtDescLarga.getText().trim(),
                                 codPaq1,codPaq2,txtCantMaxVta.getText().trim(),
                                 txtMsgPack.getText().trim(),
                                 txtDesde.getText().trim(),txtHasta.getText().trim()
                                 );
            //System.out.print(" OK");
            
            //System.out.println("TERMINO INSERCION DE PACK ... ok");
            //System.out.println("HACIENDO COMMIT ...");
            
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, 
                                     "Se realizó la operación satisfactoriamente.", 
                                     txtDescCorta);
            
            //System.out.print(" OK");            
            //System.out.println("LIMPIANDO VARIABLE Y CERRANDO LA VENTANA");
            
            limpiaVariables();
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this, "Error al crear el PACK: \n" +
                    sql.getMessage(), txtDescCorta);
        }
    }

    
    private void actualizarPack() {
        try{
            //System.out.println("ACTUALIZANDO PACK");
            ArrayList lProd;
            
            //System.out.println("ACTUALIZANDO productos del paquete 1:"+VariablesPacks.Vg_Cod_Paq1);
            for(int i=0;i<VariablesPacks.listaPaquete1.size();i++){
                
                lProd = (ArrayList)VariablesPacks.listaPaquete1.get(i);
                
                if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_ELIMINAR_PROD_PAQ)){
                    //System.out.println("... eliminando ... producto paquete cod_pak:"+VariablesPacks.Vg_Cod_Pack+" cod_paq1:"+
                                       //VariablesPacks.Vg_Cod_Paq1+" cod_prod:"+
                                         //      lProd.get(ConstantsPack.COL_COD_PP).toString());
                    DBPacks.eliminaProdPaquete(VariablesPacks.Vg_Cod_Pack,
                                               VariablesPacks.Vg_Cod_Paq1,
                                               lProd.get(ConstantsPack.COL_COD_PP).toString());
                } else if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR_PROD_PAQ)){
                    //System.out.println("... modificar ... producto paquete :"+VariablesPacks.Vg_Cod_Pack+" cod_paq1:"+
                                       /*VariablesPacks.Vg_Cod_Paq1+" cod_prod:"+
                                       lProd.get(ConstantsPack.COL_COD_PP).toString()+" cantidad:"+
                                       lProd.get(ConstantsPack.COL_CANT_PP).toString()+" cantidad:"+
                                       lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString()+" cantidad:"+
                                       lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString()+"***");*/
                    DBPacks.actualizarProdPaquete(VariablesPacks.Vg_Cod_Pack,
                                               VariablesPacks.Vg_Cod_Paq1,
                                               lProd.get(ConstantsPack.COL_COD_PP).toString(),
                                               lProd.get(ConstantsPack.COL_CANT_PP).toString(),
                                               lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString(),
                                               lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString());
                } else if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_CREAR_PROD_PAQ)){
                    
                    //System.out.println("... insertando ... producto, cod_paq:"+VariablesPacks.Vg_Cod_Paq1+" cod_prod:"+
                                       /*lProd.get(ConstantsPack.COL_COD_PP)+" cant:"+
                                       lProd.get(ConstantsPack.COL_CANT_PP).toString()+" porcdcto:"+
                                       lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString()+" val_Frac:"+
                                       lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString());*/
                    
                    DBPacks.insertarProductoPaquete(VariablesPacks.Vg_Cod_Paq1,
                                                    lProd.get(ConstantsPack.COL_COD_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_CANT_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString() );
                }
            }
            //System.out.println("ACTUALIZANDO productos del paquete 2:"+VariablesPacks.Vg_Cod_Paq1);
            for(int i=0;i<VariablesPacks.listaPaquete2.size();i++){
                
                lProd = (ArrayList)VariablesPacks.listaPaquete2.get(i);
                
                if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_ELIMINAR_PROD_PAQ)){
                    //System.out.println("... eliminando ... producto paquete cod_pak:"+VariablesPacks.Vg_Cod_Pack+" cod_paq1:"+
                                       /*VariablesPacks.Vg_Cod_Paq2+" cod_prod:"+
                                               lProd.get(ConstantsPack.COL_COD_PP).toString());*/
                    DBPacks.eliminaProdPaquete(VariablesPacks.Vg_Cod_Pack,
                                               VariablesPacks.Vg_Cod_Paq2,
                                               lProd.get(ConstantsPack.COL_COD_PP).toString());
                } else if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR_PROD_PAQ)){
                    //System.out.println("... modificar ... producto paquete :"+VariablesPacks.Vg_Cod_Pack+" cod_paq1:"+
                                       /*VariablesPacks.Vg_Cod_Paq2+" cod_prod:"+
                                               lProd.get(ConstantsPack.COL_COD_PP).toString());*/
                    DBPacks.actualizarProdPaquete(VariablesPacks.Vg_Cod_Pack,
                                               VariablesPacks.Vg_Cod_Paq2,
                                               lProd.get(ConstantsPack.COL_COD_PP).toString(),
                                               lProd.get(ConstantsPack.COL_CANT_PP).toString(),
                                               lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString(),
                                               lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString());
                } else if( lProd.get(ConstantsPack.COL_ACCION_PP).toString()
                    .equalsIgnoreCase(ConstantsPack.ACCION_CREAR_PROD_PAQ)){
                    
                    //System.out.println("... insertando ... producto, cod_paq:"+VariablesPacks.Vg_Cod_Paq1+" cod_prod:"+
                                       /*lProd.get(ConstantsPack.COL_COD_PP)+" cant:"+
                                       lProd.get(ConstantsPack.COL_CANT_PP).toString()+" porcdcto:"+
                                       lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString()+" val_Frac:"+
                                       lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString());*/
                    
                    DBPacks.insertarProductoPaquete(VariablesPacks.Vg_Cod_Paq2,
                                                    lProd.get(ConstantsPack.COL_COD_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_CANT_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_PORC_DCTO_PP).toString(),
                                                    lProd.get(ConstantsPack.COL_VAL_FRAC_PP).toString() );
                }
            }
            
            //System.out.print(" OK .. ACTUALIZANDO LOS DATOS DEL PACK");
            
            //System.out.print("cod_pak "+VariablesPacks.Vg_Cod_Pack+" desc_corta:"+
                             /*txtDescCorta.getText().trim()+" desc_larga:"+
                             txtDescLarga.getText().trim()+" cod_paq1:"+
                             VariablesPacks.Vg_Cod_Paq1+" cod_paq2:"+
                             VariablesPacks.Vg_Cod_Paq2+" estado:"+
                             ConstantsPack.ACTIVO+" CantMaxVta:"+
                             txtCantMaxVta.getText().trim()+" MsgPack:"+
                             txtMsgPack.getText().trim()+"***");*/
            
            DBPacks.actualizarPack(VariablesPacks.Vg_Cod_Pack,
                                   txtDescCorta.getText().trim(),
                                   txtDescLarga.getText().trim(),
                                   VariablesPacks.Vg_Cod_Paq1,
                                   VariablesPacks.Vg_Cod_Paq2,
                                   ConstantsPack.ACTIVO,
                                   txtCantMaxVta.getText().trim(),
                                   txtMsgPack.getText().trim(),
                                 txtDesde.getText().trim(),txtHasta.getText().trim()
                                   );
            
            
            
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, 
                                     "Se realizó la operación satisfactoriamente.", 
                                     txtDescCorta);
            limpiaVariables();
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Error al actualizar los datos del Pack: \n" +
                    sql.getMessage(), txtDescCorta);
        }
    }

    private void limpiaVariables() {
        /*VariablesConvenio.vDescCorta = "";
        VariablesConvenio.vDescLarga = "";
        VariablesConvenio.vPorcDscto = "";
        VariablesConvenio.vPorcCoPago = "";
        VariablesConvenio.vDiaIniFact = "";
        VariablesConvenio.vFecIniConv = "";
        VariablesConvenio.vFecFinConv = "";
        VariablesConvenio.vSecVigConv = "";
        VariablesConvenio.ACCION = "";*/
        //System.out.println("AQUI SE DEBERIA DE LIMPIAR TODAS LAS VARIABLES USADAS !");
    }

    private void txtMsgPack_keyReleased(KeyEvent e) {
    }

    private void txtDesde_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtHasta);
        } else
            chkKeyPressed(e);
    }

    private void txtHasta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantMaxVta);
        } else
            chkKeyPressed(e);
    }

    private void txtDesde_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtDesde,e);
    }

    private void txtHasta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtHasta,e);
    }

    private void eventoF11() {

        vFlagTeclaFx = true;
        if (validaIngreso()) {
          if (VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_CREAR)) {
              if (FarmaUtility.rptaConfirmDialog(this, 
                                                 "¿Está seguro que desea crear el Pack?")) {
                  //guardaVariables();
                  crearPack();
                  //cargaListaPaquetes();
                  VariablesPacks.vAceptar = true;
                  
              } else
                  FarmaUtility.showMessage(this, 
                                           "Se canceló la operación.", 
                                           txtDescCorta);
          } else if (VariablesPacks.ACCION.equalsIgnoreCase(ConstantsPack.ACCION_MODIFICAR)) {
              if (FarmaUtility.rptaConfirmDialog(this, 
                                                 "¿Está seguro que desea actualizar los datos del Pack?")) {
                  //guardaVariables();
                  actualizarPack();
                  VariablesPacks.vAceptar = true;
              } else
                  FarmaUtility.showMessage(this, 
                                           "Se canceló la operación.", 
                                           txtDescCorta);
          }
        }
        vFlagTeclaFx = false;
    }

    private void lblFAceptar_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
            eventoF11();
    }

    private void eventoF1() {
        vFlagTeclaFx = true;
        VariablesPacks.Paquete = ConstantsPack.PAQUETE_1;
        muestraDetalleProductoPaqueteNuevo();
        vFlagTeclaFx = false;
    }

    private void lblF1_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
         eventoF1();
    }

    private void eventoF2() {
        vFlagTeclaFx = true;
        VariablesPacks.Paquete = ConstantsPack.PAQUETE_2;
        muestraDetalleProductoPaqueteNuevo();
        vFlagTeclaFx = false;
    }

    private void lblF2_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
         eventoF2();
    }

    private void eventoEscape() {
        vFlagTeclaFx = true;
        VariablesPacks.vAceptar = false;
        limpiaVariables();
        cerrarVentana(false);
        vFlagTeclaFx = false;
    }

    private void lblFSalir_mouseClicked(MouseEvent e) {
        if(!vFlagTeclaFx)
         eventoEscape();
    }
}
