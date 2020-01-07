package consorcio.admPetitorio;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.DlgLogin;
import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import consorcio.admPetitorio.reference.ConstantsPetitorio;

import consorcio.admPetitorio.reference.DBPetitorio;

import java.awt.Component;

import java.awt.FlowLayout;

import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;

import venta.caja.reference.UtilityCaja;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;


public class DlgNuevoPetitorio extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgNuevoPetitorio.class);

    Frame myParentFrame;
    FarmaTableModel tableModel,modelListaPrincipios, modelListaProd;
    FarmaTableModel tableModelPetitorioMedico;
    ArrayList vListaPrincipios = new ArrayList();
    String vEstadoNota;
    String pCodMedico;
    String pCodPetitorio;
    String estadoPetitorio;
    String pCodPrincipio;
    ArrayList vListaProductos = new ArrayList();
    private JTable myJTablePrincipio;
    
    public static int vPosOld=0;
    public static int vPosNew=0;    
    
    //Dflores: 07/10/2019
    public String pSelectCodigoPrincipio = "";
    public String pSelectDescPrincipio = "";
    public int posSelected = 0;
    public int cantProdInList = 0;
    public int cantPrincInList = 0;
    public String pTurnoxSem="", pAdjOtros="", pLab="";
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane jscPrincipio = new JScrollPane();
    private JScrollPane jscProductos = new JScrollPane();
    private JTextField txtPrincipio = new JTextField();
    private JTextField txtProductos = new JTextField();
    private JTextField txtPetitorio = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtTurnos = new JTextField();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JScrollPane jScrollPane4 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JTextArea txtAdjuntaOtros = new JTextArea();
    private JTextArea txtLaboratorios = new JTextArea();
    private JPanel jPanel3 = new JPanel();
    private JButton btnGrabar = new JButton();
    private JTable tblPrincipioActivo = new JTable();
    private JTable tblListaProductos = new JTable();
    private JLabel lblMensajeFiltroPrincipios = new JLabel();
    private JPanel jpnPlus1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    private JPanel jpnPlus2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    private JPanel jpnPlus3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    private JPanel jpnPlus4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel lblCmp = new JLabel();
    private JLabel lblNomMedico = new JLabel();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgNuevoPetitorio()
  {
    this(null, "", false,"","","", null);
  }

  public DlgNuevoPetitorio(Frame parent, String title, boolean modal, String pCodMedico, 
                           String estadoPetitorio, String codPrincipio,
                           FarmaTableModel tableModelPetitorioMedico)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    //--
    this.pCodMedico = pCodMedico;
    this.estadoPetitorio = estadoPetitorio;
    this.pCodPrincipio = codPrincipio;
    this.tableModelPetitorioMedico = tableModelPetitorioMedico;
    this.pCodPetitorio = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 2);
    this.pTurnoxSem = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 6);
    this.pAdjOtros = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 7);
    this.pLab = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 8);
    //--
    try
    {
      jbInit();
      initialize();
      FarmaUtility.centrarVentana(this);
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
    this.setSize(new Dimension(1285, 712));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Nuevo Petitorio");
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
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(1145, 625, 100, 30));
        jscPrincipio.setBounds(new Rectangle(10, 175, 300, 310));
        jscProductos.setBounds(new Rectangle(315, 175, 940, 310));
        txtPrincipio.setBounds(new Rectangle(10, 105, 300, 25));
        txtPrincipio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPrincipio_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtPrincipio_keyReleased(e);
                }
            });
        txtProductos.setBounds(new Rectangle(315, 105, 930, 25));
        txtPetitorio.setBounds(new Rectangle(105, 15, 395, 25));
        txtPetitorio.setEditable(false);
        txtPetitorio.setFont(new Font("Tahoma", 1, 12));
        jLabel1.setText("Petitorio N\u00b0:");
        jLabel1.setBounds(new Rectangle(30, 15, 115, 25));
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        jLabel2.setText("N\u00b0 Turnos por Semana :");
        jLabel2.setBounds(new Rectangle(10, 70, 135, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 12));
        txtTurnos.setBounds(new Rectangle(150, 70, 50, 20));
        txtTurnos.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTurnos_keyPressed(e);
                }
            });
        jScrollPane3.setBounds(new Rectangle(10, 510, 580, 105));
        jLabel3.setText("Adjuntar Otros : ");
        jLabel3.setBounds(new Rectangle(10, 490, 115, 15));
        jLabel3.setFont(new Font("SansSerif", 1, 12));
        jLabel4.setText("Laboratorios ");
        jLabel4.setBounds(new Rectangle(650, 490, 110, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 12));
        jScrollPane4.setBounds(new Rectangle(650, 510, 600, 100));
        jPanel1.setBounds(new Rectangle(10, 140, 300, 35));
        jPanel1.setBackground(new Color(0, 132, 198));
        jPanel1.setLayout(null);
        /*btnPlus1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });*/
        jPanel2.setBounds(new Rectangle(315, 140, 940, 35));
        jPanel2.setBackground(new Color(0, 132, 198));
        jPanel2.setLayout(null);
        jPanel3.setBounds(new Rectangle(580, 10, 670, 80));
        jPanel3.setLayout(null);


        btnGrabar.setText("Grabar");
        btnGrabar.setBounds(new Rectangle(990, 625, 80, 25));
        btnGrabar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnGrabar_actionPerformed(e);
                }
            });
        lblMensajeFiltroPrincipios.setBounds(new Rectangle(45, 10, 200, 15));
        lblMensajeFiltroPrincipios.setForeground(SystemColor.window);
        lblMensajeFiltroPrincipios.setFont(new Font("SansSerif", 0, 11));
        
        jpnPlus1.setBounds(new Rectangle(10, 5, 30, 25));
        jpnPlus1.setBackground(new Color(0, 165, 0));
        jpnPlus1.setBorder(BorderFactory.createLineBorder(new Color(0, 225, 0), 2));
        jpnPlus1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jpnPlus1_mouseClicked(e);
                }
            });
        JLabel jLabel5 = new JLabel("+");
        jLabel5.setFont(new Font("Tahoma", 1, 17));
        jLabel5.setForeground(SystemColor.window);

        jpnPlus2.setBounds(new Rectangle(260, 5, 30, 25));
        jpnPlus2.setBackground(new Color(202, 0, 42));
        jpnPlus2.setBorder(BorderFactory.createLineBorder(new Color(255, 66, 0), 2));
        jpnPlus2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jpnPlus2_mouseClicked(e);
                }
            });
        JLabel jLabel6 = new JLabel("-");
        jLabel6.setFont(new Font("Tahoma", 1, 17));
        jLabel6.setForeground(SystemColor.window);

        jpnPlus3.setBounds(new Rectangle(10, 5, 30, 25));
        jpnPlus3.setBackground(new Color(0, 165, 0));
        jpnPlus3.setBorder(BorderFactory.createLineBorder(new Color(0, 225, 0), 2));
        jpnPlus3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jpnPlus3_mouseClicked(e);
                }
            });
        JLabel jLabel7 = new JLabel("+");
        jLabel7.setFont(new Font("Tahoma", 1, 17));
        jLabel7.setForeground(SystemColor.window);
        
        jpnPlus4.setBounds(new Rectangle(900, 5, 30, 25));
        jpnPlus4.setBackground(new Color(202, 0, 42));
        jpnPlus4.setBorder(BorderFactory.createLineBorder(new Color(255, 66, 0), 2));
        jpnPlus4.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jpnPlus4_mouseClicked(e);
                }
            });
        JLabel jLabel8 = new JLabel("-");
        jLabel8.setFont(new Font("Tahoma", 1, 17));
        jLabel8.setForeground(SystemColor.window);

        lblNomMedico.setText("Nombre");
        lblNomMedico.setBounds(new Rectangle(75, 45, 545, 15));
        lblNomMedico.setFont(new Font("Tahoma", 1, 12));
        lblCmp.setText("000000");
        lblCmp.setBounds(new Rectangle(65, 15, 65, 15));
        lblCmp.setFont(new Font("Tahoma", 1, 12));
        jLabel10.setText("Nombre:");
        jLabel10.setBounds(new Rectangle(15, 45, 60, 15));
        jLabel10.setFont(new Font("Tahoma", 1, 12));
        jLabel9.setText("CMP:");
        jLabel9.setBounds(new Rectangle(15, 15, 34, 14));
        jLabel9.setFont(new Font("Tahoma", 1, 12));
        jpnPlus1.add(jLabel5);
        jPanel1.add(jpnPlus1, null);
        jPanel1.add(lblMensajeFiltroPrincipios, null);
        jpnPlus2.add(jLabel6);
        jPanel1.add(jpnPlus2, null);

        jpnPlus3.add(jLabel7);
        jPanel2.add(jpnPlus3);
        jpnPlus4.add(jLabel8);
        jPanel2.add(jpnPlus4);

        jContentPane.add(btnGrabar, null);
        jPanel3.add(lblNomMedico, null);
        jPanel3.add(lblCmp, null);
        jPanel3.add(jLabel10, null);
        jPanel3.add(jLabel9, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(jPanel1, null);
        jScrollPane4.getViewport().add(txtLaboratorios, null);
        jContentPane.add(jScrollPane4, null);
        jContentPane.add(jLabel4, null);
        jContentPane.add(jLabel3, null);
        jScrollPane3.getViewport().add(txtAdjuntaOtros, null);
        jContentPane.add(jScrollPane3, null);
        jContentPane.add(txtTurnos, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(txtPetitorio, null);
        jContentPane.add(txtProductos, null);
        jContentPane.add(txtPrincipio, null);
        jscProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(jscProductos, null);      
        jscPrincipio.getViewport().add(tblPrincipioActivo, null);
        tblPrincipioActivo.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblPrincipioActivo_mouseClicked(e);
                }
            });
        tblPrincipioActivo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblPrincipioActivo_keyReleased(e);
            }
        });
        jContentPane.add(jscPrincipio, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
  
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

  private void initialize()
  {
    initTable();
    FarmaVariables.vAceptar = false;
    cargaDatosMedico();
    if(this.estadoPetitorio.equals("crear")){
        creaNuevoPetitorio();
    }else if(this.estadoPetitorio.equals("modificar")){
        setDatosPetitorio();
    }
    
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
      // lista de principios activos
      modelListaPrincipios = new FarmaTableModel(ConstantsPetitorio.columnsListaPrincipiosActivos,ConstantsPetitorio.defaultValuesListaPrincipiosActivos,0);
      FarmaUtility.initSimpleList(tblPrincipioActivo,modelListaPrincipios,ConstantsPetitorio.columnsListaPrincipiosActivos);
      tblPrincipioActivo.getTableHeader().setReorderingAllowed(false);
      tblPrincipioActivo.getTableHeader().setResizingAllowed(false);
      //--
      // lista de productos
      modelListaProd = new FarmaTableModel(ConstantsPetitorio.columnsListaProductos,ConstantsPetitorio.defaultValuesListaProductos,0);
      FarmaUtility.initSimpleList(tblListaProductos,modelListaProd,ConstantsPetitorio.columnsListaProductos);
      tblListaProductos.setName("productos_padre");
      //--
      cargaListaProductos();
      //cargaListaPetitorio();
      //llenarListadoPrincipios();
      setJTablePrincipios(tblPrincipioActivo);
  }
  
    private void llenarListadoPrincipios() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < vListaPrincipios.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)vListaPrincipios.get(i)).clone();
            arrayClone.add(aux);
        }
        modelListaPrincipios.clearTable();
        modelListaPrincipios.data = arrayClone;
        tblPrincipioActivo.repaint();
        tblPrincipioActivo.show();
    }
    
    private void setJTablePrincipios(JTable pJTable) {
        myJTablePrincipio = pJTable;
        //txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            //FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
        }
        //FarmaUtility.moveFocus(txtProducto);
    }
    
    //Dflores: 07/10/2019
    private void cargaListaProductos()
    {
        try
        {
            getDatosPrincipioSelect();
            String vFiltroxTurno = "1";
            vListaProductos.clear();
            //--
            if(!pSelectCodigoPrincipio.equals("")){
                DBPetitorio.getListaProductos(vListaProductos, pCodPetitorio, pSelectCodigoPrincipio, vFiltroxTurno);
                fillListProdSelect();
            }
        }catch(SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtProductos);
        }
    }
    
    //Dflores: 07/10/2019
    private void fillListProdSelect() {
        ArrayList arrayClone = new ArrayList();
        countProdCant();
        for (int i = 0; i < cantProdInList; i++) {
            ArrayList aux = (ArrayList)((ArrayList)vListaProductos.get(i)).clone();
            arrayClone.add(aux);
        }
        modelListaProd.clearTable();
        modelListaProd.data = arrayClone;
        tblListaProductos.repaint();
        tblListaProductos.show();
    }
    
    private int countProdCant(){
        cantProdInList = 0;
        cantProdInList = vListaProductos.size();
        return cantProdInList;
    }
    
    private int countPrinActivoCant(){
        cantPrincInList = 0;
        cantPrincInList = vListaPrincipios.size();
        return cantPrincInList;
    }
  
    private void cargaDatosMedico() {
        try {
            String pDatosMedico = DBPetitorio.getDatosMedico(pCodMedico);
            String[] vLista = pDatosMedico.split("@");
            if(vLista.length==2){
                lblCmp.setText(vLista[0].trim().toUpperCase());
                lblNomMedico.setText(vLista[1].trim().toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    
  }
  
  private void this_windowClosing(WindowEvent e)
  {
   // FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
   cerrarVentana(false);
  }
  
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,null,0);
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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
    
    //Dflores: 07/10/2019
    public void getDatosPrincipioSelect(){
        posSelected = 0;
        posSelected = tblPrincipioActivo.getSelectedRow();
        //--
        if(posSelected<0){
            posSelected = 0;
        }
        if(posSelected>=0){
            pSelectCodigoPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, posSelected, 0);
            pSelectDescPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, posSelected, 1);
        }
    }
    
    private void creaNuevoPetitorio() {
        try {
            pCodPetitorio = DBPetitorio.setCreaNuevoPetitorioCab(pCodMedico);
            txtPetitorio.setText(pCodPetitorio);
            FarmaUtility.moveFocus(txtTurnos);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Se ha creado el petitorio, puede configurar los datos mostrados.",txtTurnos);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo crear el petitorio correctamente.",txtTurnos);
        }
    }
    
    private void setDatosPetitorio(){
        txtPetitorio.setText(pCodPetitorio);
        txtTurnos.setText(pTurnoxSem);
        txtAdjuntaOtros.setText(pAdjOtros);
        txtLaboratorios.setText(pLab);
        //cargaPrincipioActivo(pCodPrincipio,"");
        cargaPrincipioActivo();
        cargaListaProductos();     
    }
    
    private void actualizarPetitorio() {
        try {
            DBPetitorio.setActualizaPetitorioCab(pCodMedico, pCodPetitorio, pTurnoxSem, pAdjOtros, pLab);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Se actualizó el Petitorio N° " + pCodPetitorio,txtPetitorio);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo crear el petitorio correctamente.",txtPetitorio);
        }
    }

    private void cargaPrincipioActivo(String pCodPrincipio, String pDesPrincipio) {
        try {
            DBPetitorio.creaPetitorioPrincipio(pCodPetitorio, pCodPrincipio);
            vListaPrincipios.clear();
            DBPetitorio.getListaPetitorioPrincipioActivo(vListaPrincipios, pCodPetitorio);
            llenarListadoPrincipios();
            FarmaUtility.moveFocus(txtPrincipio);
            FarmaUtility.aceptarTransaccion();
            //FarmaUtility.showMessage(this,"Se ha creado el petitorio, puede configurar los datos mostrados.",txtPetitorio);
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo asignar el principio activo seleccionado.\n"+
                                          e.getMessage(),txtPetitorio);
            FarmaUtility.moveFocus(txtPrincipio);
        }
    }
    
    private void cargaPrincipioActivo() {
        try {
            vListaPrincipios.clear();
            //--
            DBPetitorio.getListaPetitorioPrincipioActivo(vListaPrincipios, pCodPetitorio);
            llenarListadoPrincipios();
            //FarmaUtility.moveFocus(txtPrincipio);
            FarmaUtility.aceptarTransaccion();
            //FarmaUtility.showMessage(this,"Se ha creado el petitorio, puede configurar los datos mostrados.",txtPetitorio);
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo asignar el principio activo seleccionado.\n"+
                                          e.getMessage(),txtPetitorio);
            FarmaUtility.moveFocus(txtPrincipio);
        }
    }

    private void cargaDetallePrincipioAct(String pCodPetitorio,String pCodPrincipio, String pCodProd, String pCantTurno) {
        try {
            DBPetitorio.creaDetallePetitorio(pCodPetitorio,
                                             pCodPrincipio,
                                             pCodProd,
                                             pCantTurno);
            //FarmaUtility.moveFocus(txtPrincipio);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo agregar el detalle del Petitorio seleccionado.\n"+
                                          e.getMessage(),txtProductos);
        }
    }
    
    private void txtPrincipio_keyReleased(KeyEvent e) {
        //--
        if(tblPrincipioActivo.getRowHeight()==0&&txtPrincipio.getText().trim().length()==0){
            llenarListadoPrincipios();
            //iniciaProceso(true);
            lblMensajeFiltroPrincipios.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
        }
        //--    
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER||
            (e.getKeyCode()== KeyEvent.VK_F5||
            e.getKeyCode()== KeyEvent.VK_F1||
            e.getKeyCode()== KeyEvent.VK_F12))
        
        ){
            filtroGooglePrincipio();
            //iniciaProceso(true);
            //log.debug("Caracter");
        }else{
            if(tblPrincipioActivo.getRowCount() >= 0 && 
               modelListaPrincipios.getRowCount() > 0 && 
                e.getKeyChar() != '+') {
                if (FarmaGridUtils.buscarDescripcion(e, myJTablePrincipio, txtPrincipio, 
                                                     2) || 
                    (e.getKeyCode() == KeyEvent.VK_UP || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                    (e.getKeyCode() == KeyEvent.VK_DOWN || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    vPosNew = tblPrincipioActivo.getSelectedRow();
                    if (vPosOld == 0 && vPosNew == 0) {
                        UpdateReleasePrincipio(e);
                        vPosOld = vPosNew;
                    } else {
                        if (vPosOld != vPosNew) {
                            UpdateReleasePrincipio(e);
                            vPosOld = vPosNew;
                        }
                    }
                }
            }
        }        
    }
    
    private void filtroGooglePrincipio() {
        String condicion = txtPrincipio.getText().toUpperCase();
        
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            llenarListadoPrincipios();
            //filtrar java
            ArrayList target = modelListaPrincipios.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                //String pCodigo = fila.get(0).toString().toUpperCase().trim();
                String descProd = fila.get(1).toString().toUpperCase().trim().replace(" ", "");                
                if(descProd.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            //limpia las tablas auxiliares                
            modelListaPrincipios.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            modelListaPrincipios.fireTableDataChanged();
            setJTablePrincipios(tblPrincipioActivo);
            if(tblPrincipioActivo.getRowCount()==0){
                lblMensajeFiltroPrincipios.setText("No se encontraron datos para el filtro ingresado");
                llenarListadoPrincipios();
            }
            else{
                if(tblPrincipioActivo.getRowCount()==1)
                    lblMensajeFiltroPrincipios.setText(tblPrincipioActivo.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltroPrincipios.setText(tblPrincipioActivo.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoPrincipios();
            lblMensajeFiltroPrincipios.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblPrincipioActivo.getRowCount()>0)
            FarmaGridUtils.showCell(tblPrincipioActivo, 0, 0);
    }

    private void txtPrincipio_keyPressed(KeyEvent e) {
        
        try
        {
            if(e.getKeyChar() != '+'&&
                !(
                (e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                (e.getKeyCode() == KeyEvent.VK_DOWN || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                e.getKeyCode() == KeyEvent.VK_ENTER||
                
                (e.getKeyCode()== KeyEvent.VK_F5||
                e.getKeyCode()== KeyEvent.VK_F1||
                e.getKeyCode()== KeyEvent.VK_F12)
                )
            )
            log.debug("Caracter");
            else    
                FarmaGridUtils.aceptarTeclaPresionada(e, myJTablePrincipio,new JTextField(), 1);
                cargaListaProductos();
                //FarmaUtility.showMessage(this,"Presionado Prueba "+pSelectCodigoPrincipio,txtProductos);
                log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));
            
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                procesoEnter(e);
            } else {
                chkKeyPressed(e);
            }
        } catch (Exception exc) {
            log.error("",exc);
            //log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            //vEjecutaAccionTeclaListado = false;
        }
    }
    
    public void procesoEnter(KeyEvent e){
        int pos = tblPrincipioActivo.getSelectedRow();
        if(pos>= 0){
            pSelectCodigoPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 0);
            pSelectDescPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 1);
            
            cerrarVentana(true);
        }
        else
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", txtPrincipio);
    }      
    
    private void UpdateReleasePrincipio(KeyEvent keyEvent) {
    }

    private void tblPrincipioActivo_mouseClicked(MouseEvent e) {
        cargaListaProductos();
        //FarmaUtility.showMessage(this,"Presionado Prueba "+pSelectCodigoPrincipio,txtProductos);
    }
    
    private void tblPrincipioActivo_keyReleased(KeyEvent e) {
        //--
        cargaListaProductos();
    }

    private void txtTurnos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtPrincipio);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }
    
    private void eliminaDetallePrincipioAct(String pCodPetitorio,String pCodPrincipio, String pCodProd) {
        try {
            DBPetitorio.eliminaProdDetallePetitorio(pCodPetitorio,pCodPrincipio,pCodProd);
            //FarmaUtility.moveFocus(txtPrincipio);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo eliminar el producto seleccionado.\n"+
                                          e.getMessage(),txtProductos);
        }
    }

    private void btnGrabar_actionPerformed(ActionEvent e) {
        countProdCant();
        countPrinActivoCant();
        //--
        if( cantPrincInList==0 ){
            FarmaUtility.showMessage(this,"No existe Items Agregados en la Lista Principio Activo!!", txtPrincipio);
        }else if( cantProdInList==0 ){
            FarmaUtility.showMessage(this,"No existe Items Agregados en la Lista Producto!!", txtProductos);
        }else if( validaDatos() ){
            pTurnoxSem=txtTurnos.getText();
            pAdjOtros=txtAdjuntaOtros.getText();
            pLab=txtLaboratorios.getText();
            actualizarPetitorio();
            cerrarVentana(false);
        }else{
            log.info("Falta llenar algun Campo! ");
        }
    }
    
    private boolean validaDatos(){
        //Dflores: 07.10.2019 * PREGUNTAR PORQUE CUANDO LE MANDO txtPacienteTurno lo toma como null!!
        if(UtilityAdmisionMedica.valorCampoEsNulo(txtTurnos,this)){
            //FarmaUtility.showMessage(this, "Falta llenar el Campo Paciente x Turno!" ,txtPacienteTurno);
            return false;
        }
        return Boolean.TRUE;
    }

    private void jpnPlus1_mouseClicked(MouseEvent e) {
        DlgSeleccionaPrincipioActivo dlgListaPac = new DlgSeleccionaPrincipioActivo(myParentFrame, "", true);
        dlgListaPac.setVisible(true);    
        if(FarmaVariables.vAceptar){
            cargaPrincipioActivo(dlgListaPac.pSelectCodigo,
                                 dlgListaPac.pPrincipio);
        }
        FarmaGridUtils.showCell(tblPrincipioActivo, 0, 0);
    }

    private void jpnPlus2_mouseClicked(MouseEvent e) {
        int pos = tblPrincipioActivo.getSelectedRow();
        if(pos<0){
            pos = 0;
        }
        countPrinActivoCant();
        //--
        if(cantPrincInList==0){
            FarmaUtility.showMessage(this,"No existe ningun Producto en la Lista Principio Activo!!",txtPrincipio);
        }else if( pos>=0 && cantProdInList>0){
            String pCodPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 0);
            String pDescPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 1);
            boolean flagContinua =FarmaUtility.rptaConfirmDialogDefaultNo(this, 
                                                    "¿Desea eliminar el principio del petitorio?\n"+
                                                    pDescPrincipio);
            if(flagContinua){

                try {
                    DBPetitorio.eliminaPrincipioPetitorio(pCodPetitorio,pCodPrincipio);
                    FarmaUtility.aceptarTransaccion();
                    vListaPrincipios.clear();
                    DBPetitorio.getListaPetitorioPrincipioActivo(vListaPrincipios, pCodPetitorio);
                    llenarListadoPrincipios();
                    FarmaUtility.moveFocus(txtPrincipio);                    
                    FarmaUtility.showMessage(this, "Se eliminó correctamente el principio activo", txtPrincipio);
                } catch (Exception f) {
                    f.printStackTrace();
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Ocurrió un error al eliminar principio activo\n"+
                                                   f.getMessage(), txtPrincipio);
                }
            }
        }
    }
    
    private void jpnPlus3_mouseClicked(MouseEvent e) {
        int pos = tblPrincipioActivo.getSelectedRow();
        if(pos<0){
            pos = 0;
        }
        //countProdCant();
        countPrinActivoCant();
        //--DFLORES: 07/10/2019 *Solo se permitira 3 productos por Principio Activo
        if(cantPrincInList==0){
            FarmaUtility.showMessage(this,"Por favor Agregar un Principio Activo!",txtPrincipio);
        }else if( (pos>=0) && (cantProdInList<=2) ){
            String pCodPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 0);
            String pDescPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, pos, 1);
            try {
                DlgSeleccionaProductos dlgListaProd = new DlgSeleccionaProductos(myParentFrame, "", true,
                                                                                pCodPrincipio,pDescPrincipio,
                                                                                pCodPetitorio);
                dlgListaProd.setVisible(true); 
                if(FarmaVariables.vAceptar){
                    cargaDetallePrincipioAct(pCodPetitorio, pCodPrincipio, dlgListaProd.pCodProducto,dlgListaProd.pCantxTurno);
                    cargaListaProductos();
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
        }else{
            FarmaUtility.showMessage(this,"Solo se aceptan 3 Productos por Principio Activo!\n",txtProductos);
        }
    }
    
    private void jpnPlus4_mouseClicked(MouseEvent e) {
        int posPrinAct = tblPrincipioActivo.getSelectedRow();
        int posProdSelect = tblListaProductos.getSelectedRow();
        //--
        if( posPrinAct<0 ){
            posPrinAct = 0;
        }
        if( posProdSelect<0 ){
            posProdSelect = 0;
        }
        countProdCant();
        //--
        if(cantProdInList==0){
            FarmaUtility.showMessage(this,"No existe ningun Producto en la Lista Producto!",txtPrincipio);
        }else if( posPrinAct>=0 && posProdSelect>0 ){
            String pCodPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, posPrinAct, 0);
            String pDescPrincipio = FarmaUtility.getValueFieldArrayList(modelListaPrincipios.data, posPrinAct, 1);
            String pCodProducto = FarmaUtility.getValueFieldArrayList(modelListaProd.data, posProdSelect, 1);
            try {
                eliminaDetallePrincipioAct(pCodPetitorio, pCodPrincipio, pCodProducto);
                FarmaUtility.showMessage(this,"Se elimino el Producto "+pDescPrincipio+" de la Lista!.",txtProductos);
                cargaListaProductos();
            } catch (Exception f) {
                f.printStackTrace();
            }
        }else{
            FarmaUtility.showMessage(this,"Solo se aceptan 3 Productos por Principio Activo!\n",txtProductos);
        }  
    }
}
