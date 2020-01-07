package venta.fotos_cliente;


import common.FarmaUtility;
import componentes.gs.componentes.JPanelWhite;

//import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.net.URL;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;

//import common.FarmaUtility;

import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.fotos_cliente.reference.DBFotos;
import venta.fotos_cliente.reference.UtilGoFTP;
import venta.fotos_cliente.reference.UtilityFotos;
import venta.fotos_cliente.reference.VariablesFotos;


public class Prueba extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(Prueba.class);

    Frame myParentFrame;
    private JPanelWhite contentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel pblPrincipal = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JTabbedPane jtpPestanas = new JTabbedPane();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel pnlsubir = new JPanel();
    private JPanel pnlBuscar = new JPanel();

    /*panelVisorMiniaturas pm;
    panelVisualizador pv;*/
    private JPanel jPanel1 = new JPanel();
    private panelVisorMiniaturas pm = new panelVisorMiniaturas(this);
    private panelVisualizador pv =new panelVisualizador(this);
    private JPanel jPanel4 = new JPanel();
    private JPanel pnlabajo = new JPanel();
    private JButton btnatras = new JButton();
    private JButton btnadelante = new JButton();
    private BorderLayout borderLayout5 = new BorderLayout();
    private JScrollPane jspVizor = new JScrollPane();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtdni = new JTextField();
    private JButton btnbuscardni = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtdatos = new JTextArea();
    JTree tree ;
    private JScrollPane jspListaImagenes;
    DefaultMutableTreeNode main = new DefaultMutableTreeNode("Principal");
    DefaultTreeModel modelo ;
    private JLabel jLabel2 = new JLabel();
    private JTextField txtDNI_SUBIR = new JTextField();
    private JButton btnBuscar = new JButton();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea txtDatos_Subida = new JTextArea();
    private JPanel pnlFiles = new JPanel();
    private JButton btnSubirArchivo = new JButton();
    JFileChooser fileChooser = new JFileChooser();
    private BorderLayout borderLayout6 = new BorderLayout();
    private BorderLayout borderLayout7 = new BorderLayout();
    
    Panel_PreVizor p = new Panel_PreVizor(null);
    private JButton jButton3 = new JButton();
    private JButton btnLimpiar_subir = new JButton();
    private JButton btnLimpiar_Vizor = new JButton();
    private JLabel lblRutaImagen = new JLabel();
    private JLabel lblNameFile = new JLabel();


    //**************************************************************************
    //Constructores
    //**************************************************************************

    public Prueba() {
       // this(null, "", false);
    }

    public Prueba(Dialog dialog, String string, boolean b) {
        super(dialog, string, b);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    public Prueba(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    //**************************************************************************
    //Método "jbInit()"
    //**************************************************************************

    private void jbInit() throws Exception {
        //DefaultMutableTreeNode main = new DefaultMutableTreeNode("Principal");
        modelo = new DefaultTreeModel(main);
        tree = new JTree(modelo);
        jspListaImagenes = new JScrollPane(tree);
        tree.addMouseListener(new MouseAdapter() {
              public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
              }
            });
        
        this.setSize(new Dimension(1188, 744));
        this.setDefaultCloseOperation(0);
        this.setTitle("Mensaje");
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }

                public void windowClosing(WindowEvent e) {
                    this_windowClosing(e);
                }
            });
        contentPane.setLayout(borderLayout2);
        pblPrincipal.setBackground(new Color(231, 231, 231));
        pblPrincipal.setLayout(borderLayout3);
        pnlsubir.setLayout(null);
        pnlsubir.setBackground(SystemColor.window);
        pnlBuscar.setLayout(null);
        pnlBuscar.setBackground(SystemColor.window);
        jPanel1.setBounds(new Rectangle(10, 5, 325, 160));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pm.setBounds(new Rectangle(90, 10, 565, 65));
        pm.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 2));
        //pv.setLayout(borderLayout5);
        //pv.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 2));
        pv.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        pnlabajo.setBounds(new Rectangle(380, 585, 745, 85));
        pnlabajo.setLayout(null);
        pnlabajo.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pnlabajo.setBackground(new Color(198, 214, 255));
        btnatras.setText("<<");
        btnatras.setBounds(new Rectangle(15, 20, 60, 45));
        btnatras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnatras_actionPerformed(e);
                }
            });
        btnadelante.setText(">>");
        btnadelante.setBounds(new Rectangle(670, 15, 60, 50));
        btnadelante.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnadelante_actionPerformed(e);
                }
            });
        //jspVizor.setBounds(new Rectangle(185, 85, 475, 200));
        jPanel2.setBounds(new Rectangle(345, 10, 820, 565));
        jPanel2.setLayout(borderLayout4);
        jPanel2.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jLabel1.setText("DNI");
        jLabel1.setBounds(new Rectangle(10, 20, 34, 14));
        txtdni.setBounds(new Rectangle(55, 20, 210, 20));
        btnbuscardni.setText("jButton1");
        btnbuscardni.setBounds(new Rectangle(275, 10, 35, 35));
        btnbuscardni.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnbuscardni_actionPerformed(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 50, 305, 100));
        jspListaImagenes.setBounds(new Rectangle(10, 210, 325, 455));
        jLabel2.setText("DNI :");
        jLabel2.setBounds(new Rectangle(25, 30, 34, 14));
        txtDNI_SUBIR.setBounds(new Rectangle(60, 20, 190, 30));
        btnBuscar.setText("jButton1");
        btnBuscar.setBounds(new Rectangle(265, 20, 30, 30));
        btnBuscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
            });
        jScrollPane2.setBounds(new Rectangle(20, 70, 300, 210));
        txtDatos_Subida.setText("Cliente :  Armando Rojas Cayo");
        pnlFiles.setBounds(new Rectangle(375, 25, 765, 585));
        pnlFiles.setLayout(borderLayout6);
        pnlFiles.setBorder(BorderFactory.createTitledBorder("Seleccione su archivo"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
              fileChooser
                       .setDialogTitle("Browse naar de  locatie waar je de gesorteerde bestanden wil zetten en klik op \"OPEN\"");
        fileChooser.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                       System.out.println("File selected: " + fileChooser.getSelectedFile());
                    }
                    fileChooser_actionPerformed(e);
                }
              });
        //lblMuestraImagen.setText("jLabel3");
        p.setBounds(new Rectangle(20, 315, 310, 295));
        p.setBorder(BorderFactory.createTitledBorder("Muestra de Imagen Seleccionada"));
        p.setLayout(borderLayout7);
        jButton3.setText("jButton3");
        btnLimpiar_subir.setText("Limpiar B\u00fasqueda");
        btnLimpiar_subir.setBounds(new Rectangle(20, 285, 125, 25));
        btnLimpiar_subir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnLimpiar_actionPerformed(e);
                }
            });
        btnLimpiar_Vizor.setText("Limpiar B\u00fasqueda");
        btnLimpiar_Vizor.setBounds(new Rectangle(15, 175, 125, 25));
        btnLimpiar_Vizor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnLimpiar_Vizor_actionPerformed(e);
                }
            });
        lblRutaImagen.setBounds(new Rectangle(315, 635, 745, 30));
        lblNameFile.setText("jLabel3");
        lblNameFile.setBounds(new Rectangle(385, 665, 140, 15));
        btnSubirArchivo.setText("Subir Archivo");
        btnSubirArchivo.setBounds(new Rectangle(105, 625, 125, 30));
        btnSubirArchivo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        pnlsubir.add(lblNameFile, null);
        pnlsubir.add(lblRutaImagen, null);
        pnlsubir.add(btnLimpiar_subir, null);
        pnlsubir.add(btnSubirArchivo, null);
        pnlsubir.add(p, null);
        pnlFiles.add(fileChooser, BorderLayout.CENTER);
        pnlsubir.add(pnlFiles, null);
        jScrollPane2.getViewport().add(txtDatos_Subida, null);
        pnlsubir.add(jScrollPane2, null);
        pnlsubir.add(btnBuscar, null);
        pnlsubir.add(txtDNI_SUBIR, null);
        pnlsubir.add(jLabel2, null);
        //jspVizor.getViewport().add(pv, null);
        

        ChangeListener changeListener = new ChangeListener() {
          public void stateChanged(ChangeEvent changeEvent) {
            eventoCambioPestana(changeEvent);
          } 
        };
        jtpPestanas.addChangeListener(changeListener);

        pnlabajo.add(btnadelante, null);
        pnlabajo.add(btnatras, null);
        pnlabajo.add(pm, null);
        pnlBuscar.add(btnLimpiar_Vizor, null);
        pnlBuscar.add(jspListaImagenes, null);
        jPanel2.add(pv, BorderLayout.CENTER);
        pnlBuscar.add(jPanel2, null);
        pnlBuscar.add(pnlabajo, null);
        pnlBuscar.add(jPanel1, null);
        jScrollPane1.getViewport().add(txtdatos, null);
        jPanel1.add(jScrollPane1, null);
        jPanel1.add(btnbuscardni, null);
        jPanel1.add(txtdni, null);
        jPanel1.add(jLabel1, null);
        jtpPestanas.addTab("Subir", pnlsubir);
        jtpPestanas.addTab("Visualizar", pnlBuscar);
        pblPrincipal.add(jtpPestanas, BorderLayout.CENTER);
        contentPane.add(pblPrincipal, BorderLayout.CENTER);


        this.getContentPane().add(contentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initialize() {
        //FarmaVariables.vAceptar = false;
        FarmaUtility.centrarVentana(this);

    };

    //**************************************************************************
    //Método "initialize()"
    //**************************************************************************

    private void initTable() {
    }

    //**************************************************************************
    //Métodos de inicialización
    //**************************************************************************

    //**************************************************************************
    //Metodos de eventos
    //**************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        iniSubida();
        System.out.println("4123456");
        // Construccion del arbol
        // Construccion de los datos del arbol
        
    }

    public static void main(String[] args) {
        //JInputDialog.showInputDialog(null,"¿Cuál es su edad?");
        Prueba optioPane = new Prueba(new JDialog(), "", true);
        optioPane.setVisible(true);
    }

    //**************************************************************************
    //Metodos auxiliares de eventos
    //**************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        //FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        cerrarVentana(false);
    }
    
    public panelVisorMiniaturas getPm() {
     return pm;
    }

    public void setPm(panelVisorMiniaturas pm) {
     this.pm = pm;
    }

    public panelVisualizador getPv() {
     return pv;
    }

    public void setPv(panelVisualizador pv) {
     this.pv = pv;
    }

    private void btnatras_actionPerformed(ActionEvent e) {
        pm.anteriorImagen();
        pm.quitarBorder();
    }

    private void btnadelante_actionPerformed(ActionEvent e) {
        pm.siguienteImagen();
        pm.quitarBorder();
    }

    private void btnbuscardni_actionPerformed(ActionEvent e) {
        
        //ingresa y busca el DNI
        String pDNI = txtdni.getText();
        if(pDNI.trim().length()>0){
            if(UtilityFotos.cargaDatosDNI(pDNI)){
                txtdatos.append("DNI:"+pDNI+"\n");
                txtdatos.append("Nombre :"+VariablesFotos.pVal_Nombre+"\n");
                txtdatos.append("Ape.Paterno :"+VariablesFotos.pVal_ApePat+"\n");
                txtdatos.append("Ape.Materno :"+VariablesFotos.pVal_ApeMat+"\n");
                btnbuscardni.setEnabled(false);
                btnadelante.setEnabled(true);
                btnatras.setEnabled(true);
                btnLimpiar_Vizor.setEnabled(true);
                cargaImagenesDNI(pDNI);
                FarmaUtility.showMessage(this,"Se cargaron las imagenes que se tienen del DNI Ingresado.", txtdni);
            }
            else
            FarmaUtility.showMessage(this,"No se pudo cargar los datos del cliente.\n" +
                "Puede no existir en el sistema como atención", txtDNI_SUBIR);
        }
        else{
            FarmaUtility.showMessage(this,"Debe de ingresar el DNI ", txtDNI_SUBIR);
        }
        
    }

    private void cargaDatosDni(String pDNI) {
        //cargaRutaImagenes(pDNI);
    }

    private void cargaRutaImagenes(String pDNI) {
        System.out.println("4123456");
        // Construccion del arbol
        // Construccion de los datos del arbol
        DefaultMutableTreeNode anio_01 = new DefaultMutableTreeNode("2015");
        DefaultMutableTreeNode anio_02 = new DefaultMutableTreeNode("2016");
        modelo.insertNodeInto(anio_01, main, 0);
        modelo.insertNodeInto(anio_02, main, 1);

        DefaultMutableTreeNode diciembre   = new DefaultMutableTreeNode("diciembre");
        modelo.insertNodeInto(diciembre, anio_01, 0);

        DefaultMutableTreeNode ima01   = new DefaultMutableTreeNode("15.Dic_Pierna");
        modelo.insertNodeInto(ima01, diciembre, 0);
        
        DefaultMutableTreeNode enero  = new DefaultMutableTreeNode("enero");
        DefaultMutableTreeNode febrero = new DefaultMutableTreeNode("febrero");

        modelo.insertNodeInto(enero, anio_02, 0);
        DefaultMutableTreeNode ima02   = new DefaultMutableTreeNode("08.En_brazo");
        DefaultMutableTreeNode ima03   = new DefaultMutableTreeNode("15.En_brazo");
        DefaultMutableTreeNode ima04   = new DefaultMutableTreeNode("20.En_brazo");


        modelo.insertNodeInto(ima02, enero, 0);
        modelo.insertNodeInto(ima03, enero, 1);
        modelo.insertNodeInto(ima04, enero, 2);



        modelo.insertNodeInto(febrero, anio_02, 1);



        DefaultMutableTreeNode ima05   = new DefaultMutableTreeNode("11.Feb_mancha");
        DefaultMutableTreeNode ima06   = new DefaultMutableTreeNode("15.Feb_cicatriz");
        modelo.insertNodeInto(ima05, febrero, 0);
        modelo.insertNodeInto(ima06, febrero, 1);
        
        tree.expandRow(0);
        tree.repaint();
        jspListaImagenes.repaint();

                
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        subirArchivo();
    }

    private void muestraImagen(String pCadena) {
        String path = pCadena;
            //"C:\\DUBILLUZ_20141123\\Datos_Personales\\muestra.jpg";  
        ImageIcon icon = new ImageIcon(path);
        p.setImagen(icon);
        this.repaint();
    }

    private void fileChooser_actionPerformed(ActionEvent e) {
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        
        if(selectedFile.getAbsolutePath().toUpperCase().indexOf(".JPG")!=-1)
            try {
                lblRutaImagen.setText("");
                lblNameFile.setText("");
                muestraImagen(selectedFile.getAbsolutePath());
                lblRutaImagen.setText(selectedFile.getAbsolutePath());
                lblNameFile.setText(selectedFile.getName());
            } catch (Exception e1) {
                // TODO: Add catch code
                e1.printStackTrace();
            }
    }

    private void iniSubida() {
        
        iniPestanaUno();
        
    }
    
    public void eventoCambioPestana(ChangeEvent changeEvent) {
        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
        int index = sourceTabbedPane.getSelectedIndex();
        System.out.println(index +" changed to: " + sourceTabbedPane.getTitleAt(index));
        if(index==0){
            iniPestanaUno();
        }
        else{
            iniPestanaDos();        
        }
    }

    private void iniPestanaUno() {
        VariablesFotos.limpiaVariables();
        btnSubirArchivo.setEnabled(false);
        fileChooser.setEnabled(false);
        pnlFiles.setEnabled(false);
        txtDatos_Subida.setEnabled(false);
        txtDatos_Subida.setText("");
        btnLimpiar_subir.setVisible(false);
        btnBuscar.setVisible(true);
        btnBuscar.setEnabled(true);
        txtDNI_SUBIR.setEnabled(true);
        txtDNI_SUBIR.setText("");
        ImageIcon icon = new ImageIcon();
        p.setImagen(icon);
        this.repaint();
        FarmaUtility.moveFocus(txtDNI_SUBIR);
    }
    
    private void iniPestanaDos() {
        VariablesFotos.limpiaVariables();
        btnLimpiar_Vizor.setEnabled(false);
        txtdatos.setText("");
        txtdatos.setEnabled(false);
        txtdni.setText("");
        pm.setTotalImagenes(new ArrayList(),0);
        btnadelante.setEnabled(false);
        btnatras.setEnabled(false);
        btnbuscardni.setEnabled(true);
        try {
            for (int i = 0; i < 1000; i++)
                main.remove(i);
        } catch (Exception e) {
            // TODO: Add catch code
            //e.printStackTrace();
        }
        modelo.reload();
        try {
            getPv().setImagen(null);
            getPv().repaint();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        p.repaint();
        
        pnlBuscar.show();
        pnlBuscar.repaint();
        pv.show();
        pv.repaint();
        jPanel2.show();
        jPanel2.repaint();
        this.show();
        this.repaint();
        
        FarmaUtility.moveFocus(txtdni);
    }
    
    public void doMouseClicked(MouseEvent me) {
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp != null){
            //txtdni.setText(tp.toString());
            //[Principal, 2016, enero]
            log.info("@ "+tp.toString().trim());
            String[] pCadena = (tp.toString().trim().replace("]","").replace("[", "")).split(",");
            log.info(tp.toString().trim().replace("]","").replace("[", ""));
            String pVer = tp.toString().trim().replace("]","").replace("[", "").trim();
            String[] pVerLis = pVer.split(",");
            log.info("pVerLis:"+pVerLis);
            log.info("pVerLis:"+pVerLis.length);
                if(pCadena.length==3){
                    //es valido esta en la opcion de mes
                    String pAnio = pCadena[1];
                    String pMes  = pCadena[2];
                    cargaImagenMesAnio(pAnio,pMes,VariablesFotos.pVal_DNI);
                }
        }
        
      }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        //ingresa y busca el DNI
        String pDNI = txtDNI_SUBIR.getText();
        if(pDNI.trim().length()>0){
            if(UtilityFotos.cargaDatosDNI(pDNI)){
                txtDatos_Subida.append("DNI:"+pDNI+"\n");
                txtDatos_Subida.append("Nombre :"+VariablesFotos.pVal_Nombre+"\n");
                txtDatos_Subida.append("Ape.Paterno :"+VariablesFotos.pVal_ApePat+"\n");
                txtDatos_Subida.append("Ape.Materno :"+VariablesFotos.pVal_ApeMat+"\n");
                txtDNI_SUBIR.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnSubirArchivo.setEnabled(true);
                btnLimpiar_subir.setVisible(true);
                FarmaUtility.showMessage(this,"Por favor de Elegir la imagen y presionar SUBIR.", txtDNI_SUBIR);
            }
            else
            FarmaUtility.showMessage(this,"No se pudo cargar los datos del cliente.\n" +
                "Puede no existir en el sistema como atención", txtDNI_SUBIR);
        }
        else{
            FarmaUtility.showMessage(this,"Debe de ingresar el DNI ", txtDNI_SUBIR);
        }
    }

    private void btnLimpiar_actionPerformed(ActionEvent e) {
        iniPestanaUno();
    }

    private void btnLimpiar_Vizor_actionPerformed(ActionEvent e) {
        iniPestanaDos();
    }

    private void subirArchivo() {
        String pRuta = lblRutaImagen.getText().trim();
        String pDNI  = VariablesFotos.pVal_DNI;
        String pNameFile = lblNameFile.getText().trim();
        if(pDNI.trim().length()==0)
             FarmaUtility.showMessage(this, "Debe de Ingresar el DNI y buscarlo", txtDNI_SUBIR);
        else{
            if(pRuta.trim().length()>0&&pNameFile.trim().length()>0){
                String pRuta_subida = pRuta.trim(); 
                //String[] CadenaRuta = pRuta_subida.trim().split("\\");
                String pNombreFile_subida = pNameFile; 
                
                if(pNombreFile_subida.trim().length()>0&&pRuta_subida.trim().length()>0&&pNombreFile_subida.toUpperCase().indexOf(".JPG")!=-1){
                    try {
                        UtilityFotos.cargaValoresFTP();
                        UtilGoFTP.sube_ftp(pRuta_subida, pNombreFile_subida, 
                                           "/", 
                                           VariablesFotos.vUSU_SERV_FTP, 
                                           VariablesFotos.vCLAVE_SERV_FTP ,
                                           VariablesFotos.vPUERTO_SERV_FTP,
                                           VariablesFotos.vIP_SERV_FTP
                                           );
                        DBFotos.grabarImg_x_DNI(pDNI, pNombreFile_subida, pRuta_subida);
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this,"Se grabó con exito la imagen para el DNI.",txtDNI_SUBIR);
                        iniPestanaUno();
                        
                    } catch (SQLException e) {
                        e.printStackTrace();
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this,"Ocurrio un error al subir la imagen",txtDNI_SUBIR);
                    }
                }
            }
            else{
                FarmaUtility.showMessage(this, "Debe de seleccionar una Imagen con extension .JPG", txtDNI_SUBIR);
            }
        }
    }


    private void cargaImagenesDNI(String vDNI) {
        pm.reseImage();
        //tree.setModel(null);
        try {
            for (int i = 0; i < 1000; i++)
                main.remove(i);
        } catch (Exception e) {
            // TODO: Add catch code
            //e.printStackTrace();
        }
        modelo.reload();
        if(UtilityFotos.cargaListaImgxDNI(vDNI)){
            pm.reseImage();
            if(VariablesFotos.vListaImgxDNI.size()>0){
                //DefaultMutableTreeNode main = new DefaultMutableTreeNode("Principal");
                //main = new DefaultMutableTreeNode("Principal");
                //modelo = new DefaultTreeModel(main);
                //tree = new JTree(modelo);
                try {
                    //VariablesFotos.vListaImgxDNI
                    ArrayList pListAnio = new ArrayList();
                    ArrayList pListMes_Anio = new ArrayList();
                    ArrayList pListIma_Mes = new ArrayList();
                    
                    DBFotos.getListaAnio_x_DNI(pListAnio, vDNI);
                    
                    for (int i = 0; i < pListAnio.size(); i++) {
                        String pAnio = FarmaUtility.getValueFieldArrayList(pListAnio,i,0);
                        DefaultMutableTreeNode anio = new DefaultMutableTreeNode(pAnio);
                        modelo.insertNodeInto(anio, main, i);
                        DBFotos.getListaMesxDni_ANIO(pListMes_Anio, vDNI, pAnio);
                        for (int j = 0; j < pListMes_Anio.size(); j++) {
                            String pMes = FarmaUtility.getValueFieldArrayList(pListMes_Anio,j,0);
                            DefaultMutableTreeNode mes = new DefaultMutableTreeNode(FarmaUtility.getValueFieldArrayList(pListMes_Anio,j,1));
                            modelo.insertNodeInto(mes,anio, j);
                            DBFotos.getListaImagenesxDni_MES(pListIma_Mes, vDNI, pAnio,pMes);
                            for (int m = 0; m < pListIma_Mes.size(); m++) {
                                String pNameImg = FarmaUtility.getValueFieldArrayList(pListIma_Mes,m,0);
                                DefaultMutableTreeNode img = new DefaultMutableTreeNode(pNameImg);
                                modelo.insertNodeInto(img,mes, m);
                                try {
                                    boolean vCreaDir = false;
                                    File dir=new File("C:\\copy"); 
                                    if(dir.exists()) {
                                    System.out.println("ya exite el directorio!!");
                                        vCreaDir = true;
                                    }
                                    else{
                                    dir.mkdir();
                                        vCreaDir = true;
                                    System.out.println("No existia el directorio, Pero ahora a sido creado!!");

                                    }
                                    if(vCreaDir){
                                        UtilityFotos.cargaValoresFTP();
                                        UtilGoFTP.baja_ftp("c:\\copy",
                                                           pNameImg, 
                                                           VariablesFotos.vUSU_SERV_FTP, 
                                                           VariablesFotos.vCLAVE_SERV_FTP ,
                                                           VariablesFotos.vPUERTO_SERV_FTP,
                                                           VariablesFotos.vIP_SERV_FTP
                                                           );
                                        /*UtilGoFTP.baja_ftp("c:\\copy\\descarga", "jaja.txt", "ftp_prueba", "ftp_prueba", 21,
                                                 "192.168.1.5");*/
                                    }
                                    else{
                                        FarmaUtility.showMessage(this, "Por favor debe crear la carpeta C:\\copy",txtdni);
                                    }

                                } catch (Exception e) {
                                    // TODO: Add catch code
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    tree.expandRow(0);
                    tree.repaint();
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void cargaImagenMesAnio(String pAnio,String pMes,String pDni){
        try {
            ArrayList vDatos = new ArrayList();
            DBFotos.getListaImagenesxDni_MES(vDatos, pDni, pAnio, pMes);
            pm.setTotalImagenes(vDatos,vDatos.size());
            pm.show();
            pm.repaint();
            pnlabajo.show();
            pnlabajo.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
