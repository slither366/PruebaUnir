package consorcio.admPetitorio;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;

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


public class DlgAdmPetitorio extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgAdmPetitorio.class);

    Frame myParentFrame;
    FarmaTableModel tableModelPetitorioMedico, modelListaPrincipios, modelListaProd,
                    tableModelPetitorioxPos;
    ArrayList arrayListaPetitorios = new ArrayList();
    ArrayList vListaPrincipios = new ArrayList();
    String vEstadoNota;
    ArrayList vListaProductos = new ArrayList();
    String estadoPetitorio = "";
    
    // para filtro google
    private JTable myJTable;
    private JTable myJTablePrincipio;
    public static int vPosOld=0;
    public static int vPosNew=0;
    public int cantProdInList = 0;
    
    public int posSelected = 0;
    public String pSelectCmpMedico = "";
    public String pSelectCodPetitorio = "";
    public String pAdjuntarOtro = "";
    public String pLaboratorio = "";
    public String pSelectCodigoPrincipio = "";
    public String pSelectDescPrincipio = "";
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane jscListaPetitorios = new JScrollPane();
    private JTable tblListaPetitorio = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane jscListaPrincipios = new JScrollPane();
    private JScrollPane jscListaProductos = new JScrollPane();
    private JButton btnCrearPetitorio = new JButton();
    private JButton btnModificar = new JButton();
    private JButton btnEliminar = new JButton();
    private JTextField txtPrincipio = new JTextField();
    private JTextField txtProductos = new JTextField();
    private JTextField txtPetitorio = new JTextField();
    private JTextArea txtLaboratorios = new JTextArea();
    private JLabel jLabel4 = new JLabel();
    private JTextArea txtAdjuntarOtros = new JTextArea();
    private JLabel jLabel3 = new JLabel();
    private JTable tblPrincipioActivo = new JTable();
    //private JTable tblProductos = new JTable();
    
    private String pCodMedico = "";
    private JPanel jPanel1 = new JPanel();
    private JLabel lblCmp = new JLabel();
    private JLabel lblNombreMedico = new JLabel();
    private JLabel lblMensajeFiltro = new JLabel();
    private JLabel lblMensajeFiltro1 = new JLabel();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JPanelTitle pnlTitle3 = new JPanelTitle();
    private JLabel lblMensajeFiltro2 = new JLabel();
    private JLabel lblMensajeFiltro3 = new JLabel();
    private JTable tblListaProductos = new JTable();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgAdmPetitorio()
  {
    this(null, "", false,"");
  }

  public DlgAdmPetitorio(Frame parent, String title, boolean modal,String pCodMedico)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    this.pCodMedico = pCodMedico;
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
    this.setSize(new Dimension(1283, 760));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Administraci\u00f3n de Petitorios M\u00e9dicos");
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
        pnlTitle1.setBounds(new Rectangle(10, 45, 1005, 35));
    jscListaPetitorios.setBounds(new Rectangle(10, 80, 1005, 215));
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(1150, 695, 100, 30));
        jscListaPrincipios.setBounds(new Rectangle(10, 380, 300, 175));
        jscListaProductos.setBounds(new Rectangle(330, 380, 925, 175));
        btnCrearPetitorio.setText("Crear Petitorio");
        btnCrearPetitorio.setBounds(new Rectangle(1065, 80, 155, 30));
        btnCrearPetitorio.setFont(new Font("Tahoma", 1, 12));
        btnCrearPetitorio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnCrear_actionPerformed(e);
                }
            });
        btnModificar.setText("Modificar Petitorio");
        btnModificar.setBounds(new Rectangle(1065, 140, 155, 35));
        btnModificar.setFont(new Font("Tahoma", 1, 12));
        btnModificar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnModificar_actionPerformed(e);
                }
            });
        btnEliminar.setText("Eliminar Petitorio");
        btnEliminar.setBounds(new Rectangle(1065, 200, 155, 35));
        btnEliminar.setFont(new Font("Tahoma", 1, 12));
        btnEliminar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnEliminar_actionPerformed(e);
                }
            });
        txtPrincipio.setBounds(new Rectangle(10, 315, 300, 25));
        txtPrincipio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtPrincipio_keyReleased(e);
                }
                
                public void keyPressed(KeyEvent e) {
                   txtPrincipio_keyPressed(e);
                }
            });        
        txtProductos.setBounds(new Rectangle(330, 315, 925, 25));
        txtPetitorio.setBounds(new Rectangle(10, 15, 600, 25));
        txtPetitorio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtPetitorio_keyReleased(e);
                }

                /*public void keyTyped(KeyEvent e) {
                    txtPetitorio_keyTyped(e);
                }*/

                public void keyPressed(KeyEvent e) {
                    txtPetitorio_keyPressed(e);
                }
            });
        txtLaboratorios.setBounds(new Rectangle(650, 590, 600, 95));
        txtLaboratorios.setEditable(false);
        txtLaboratorios.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        txtLaboratorios.setBackground(new java.awt.Color(231, 231, 231));
        jLabel4.setText("Laboratorios ");
        jLabel4.setBounds(new Rectangle(650, 570, 110, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 12));
        txtAdjuntarOtros.setBounds(new Rectangle(10, 590, 580, 95));
        txtAdjuntarOtros.setEditable(false);
        txtAdjuntarOtros.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        txtAdjuntarOtros.setBackground(new java.awt.Color(231, 231, 231));
        jLabel3.setText("Adjuntar Otros : ");
        jLabel3.setBounds(new Rectangle(10, 570, 115, 15));
        jLabel3.setFont(new Font("SansSerif", 1, 12));
        jPanel1.setBounds(new Rectangle(630, 5, 635, 35));
        jPanel1.setLayout(null);
        lblCmp.setText("0000000");
        lblCmp.setBounds(new Rectangle(55, 5, 70, 25));
        lblCmp.setFont(new Font("Tahoma", 1, 13));
        lblNombreMedico.setText("Nombre Completo M\u00e9dico");
        lblNombreMedico.setBounds(new Rectangle(205, 5, 420, 25));
        lblNombreMedico.setFont(new Font("Tahoma", 1, 13));
        lblMensajeFiltro.setBounds(new Rectangle(5, 15, 615, 15));
        lblMensajeFiltro.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        lblMensajeFiltro1.setBounds(new Rectangle(10, 5, 810, 15));
        lblMensajeFiltro1.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro1.setForeground(SystemColor.window);
        pnlTitle2.setBounds(new Rectangle(10, 345, 300, 35));
        pnlTitle3.setBounds(new Rectangle(330, 345, 925, 35));
        pnlTitle3.setBackground(new java.awt.Color(0, 114, 169));
        lblMensajeFiltro2.setBounds(new Rectangle(5, 20, 280, 15));
        lblMensajeFiltro2.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro2.setForeground(SystemColor.window);
        lblMensajeFiltro3.setBounds(new Rectangle(10, 20, 595, 15));
        lblMensajeFiltro3.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro3.setForeground(SystemColor.window);
        jLabel1.setText("LISTADO DE PETITORIOS");
        jLabel1.setBounds(new Rectangle(410, 10, 165, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel2.setText("PRINCIPIO ACTIVO");
        jLabel2.setBounds(new Rectangle(90, 5, 110, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel5.setText("LISTADO DE PRODUCTOS");
        jLabel5.setBounds(new Rectangle(425, 5, 160, 15));
        jLabel5.setForeground(SystemColor.window);
        jLabel5.setFont(new Font("Tahoma", 1, 11));
        jLabel6.setText("CMP:");
        jLabel6.setBounds(new Rectangle(10, 5, 35, 25));
        jLabel6.setFont(new Font("Tahoma", 1, 12));
        jLabel7.setText("Nombre:");
        jLabel7.setBounds(new Rectangle(140, 5, 60, 25));
        jLabel7.setFont(new Font("Tahoma", 1, 12));
        jPanel1.add(jLabel7, null);
        jPanel1.add(jLabel6, null);
        jPanel1.add(lblNombreMedico, null);
        jPanel1.add(lblCmp, null);
        pnlTitle3.add(jLabel5, null);
        pnlTitle3.add(lblMensajeFiltro3, null);
        jContentPane.add(pnlTitle3, null);
        pnlTitle2.add(jLabel2, null);
        pnlTitle2.add(lblMensajeFiltro2, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(txtAdjuntarOtros, null);
        jContentPane.add(jLabel4, null);
        jContentPane.add(txtLaboratorios, null);
        jContentPane.add(txtPetitorio, null);
        jContentPane.add(txtProductos, null);
        jContentPane.add(txtPrincipio, null);
        jContentPane.add(btnEliminar, null);
        jContentPane.add(btnModificar, null);
        jContentPane.add(btnCrearPetitorio, null);
        jscListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(jscListaProductos, null);
        jscListaPrincipios.getViewport().add(tblPrincipioActivo, null);
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
        jContentPane.add(jscListaPrincipios, null);
        jContentPane.add(lblEsc, null);
        jscListaPetitorios.getViewport().add(tblListaPetitorio, null);
        tblListaPetitorio.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListaPetitorio_mouseClicked(e);
                }
            });
        tblListaPetitorio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    tblListaPetitorio_keyReleased(e);
                }
            });
        jContentPane.add(jscListaPetitorios, null);
        pnlTitle1.add(jLabel1, null);
        pnlTitle1.add(lblMensajeFiltro, null);
        jContentPane.add(pnlTitle1, null);
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
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    //Iniciar Listado Petitorio
    tableModelPetitorioMedico = new FarmaTableModel(ConstantsPetitorio.columnsListaPetitoriosMedico,ConstantsPetitorio.defaultValuesListaPetiroriosMedico,0);
    FarmaUtility.initSimpleList(tblListaPetitorio,tableModelPetitorioMedico,ConstantsPetitorio.columnsListaPetitoriosMedico);
    cargaListaPetitorioMedico();
    //--
    //Iniciar Listado Principios Activos
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
    //cargaListaProductos();
    tableModelPetitorioxPos = new FarmaTableModel(ConstantsPetitorio.columnsListaPetitoriosMedico,ConstantsPetitorio.defaultValuesListaPetiroriosMedico,0);
    //FarmaUtility.initSimpleList(tblListaPetitorio,tableModelPetitorioxPos,ConstantsPetitorio.columnsListaPetitoriosMedico);
  }
  
  private void cargaListaPetitorioMedico()
  {
    try
    {
      DBPetitorio.getListaPetitorioMedico(arrayListaPetitorios,pCodMedico);
      llenarListadoPetitorios();
      setJTable(tblListaPetitorio);
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la listar petitorios : \n" + sql.getMessage(),txtPetitorio);
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
    //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    cerrarVentana(false);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblPrincipioActivo,null,0);
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

    private void btnCrear_actionPerformed(ActionEvent e) {
        estadoPetitorio = "crear";
        DlgNuevoPetitorio dlgListaPac = new DlgNuevoPetitorio(myParentFrame, "", true,
                                                              pCodMedico, estadoPetitorio,
                                                              "", null);
        dlgListaPac.setVisible(true);
        cargaListaPetitorioMedico();
        cargaPrincipioActivo();
        cargaListaProductos();
    }

    private void cargaDatosMedico() {
        try {
            String pDatosMedico = DBPetitorio.getDatosMedico(pCodMedico);
            String[] vLista = pDatosMedico.split("@");
            if(vLista.length==2){
                lblCmp.setText(vLista[0].trim().toUpperCase());
                lblNombreMedico.setText(vLista[1].trim().toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void txtPetitorio_keyReleased(KeyEvent e) {
        if(tblListaPetitorio.getRowHeight()==0&&txtPetitorio.getText().trim().length()==0){
            llenarListadoPetitorios();
            //iniciaProceso(true);
            lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
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
            filtroGoogle();
            //iniciaProceso(true);
            //log.debug("Caracter");
        }else{
            if(tblListaPetitorio.getRowCount() >= 0 && 
               tableModelPetitorioMedico.getRowCount() > 0 && 
                e.getKeyChar() != '+') {
                if (FarmaGridUtils.buscarDescripcion(e, myJTable, txtPetitorio, 
                                                     2) || 
                    (e.getKeyCode() == KeyEvent.VK_UP || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                    (e.getKeyCode() == KeyEvent.VK_DOWN || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    vPosNew = tblListaPetitorio.getSelectedRow();
                    if (vPosOld == 0 && vPosNew == 0) {
                        UpdateReleaseProd(e);
                        vPosOld = vPosNew;
                    } else {
                        if (vPosOld != vPosNew) {
                            UpdateReleaseProd(e);
                            vPosOld = vPosNew;
                        }
                    }
                }
            }
        }    
    }
    
    private void filtroGoogle() {
        String condicion = txtPetitorio.getText().toUpperCase();
        
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            llenarListadoPetitorios();
            //filtrar java
            ArrayList target = tableModelPetitorioMedico.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                //String pCodigo = fila.get(0).toString().toUpperCase().trim();
                String medico = fila.get(1).toString().toUpperCase().trim().replace(" ", "");                
                String petitorio = fila.get(3).toString().toUpperCase().trim().replace(" ", "");                
                if(medico.contains(condicion)||petitorio.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            //limpia las tablas auxiliares                
            tableModelPetitorioMedico.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelPetitorioMedico.fireTableDataChanged();
            setJTable(tblListaPetitorio);
            if(tblListaPetitorio.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                llenarListadoPetitorios();
            }
            else{
                if(tblListaPetitorio.getRowCount()==1)
                    lblMensajeFiltro.setText(tblListaPetitorio.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblListaPetitorio.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoPetitorios();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblListaPetitorio.getRowCount()>0)
            FarmaGridUtils.showCell(tblListaPetitorio, 0, 0);
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
                lblMensajeFiltro2.setText("No se encontraron datos para el filtro ingresado");
                llenarListadoPrincipios();
            }
            else{
                if(tblPrincipioActivo.getRowCount()==1)
                    lblMensajeFiltro2.setText(tblPrincipioActivo.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro2.setText(tblPrincipioActivo.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoPrincipios();
            lblMensajeFiltro2.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblPrincipioActivo.getRowCount()>0)
            FarmaGridUtils.showCell(tblPrincipioActivo, 0, 0);
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

    private void llenarListadoPetitorios() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < arrayListaPetitorios.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)arrayListaPetitorios.get(i)).clone();
            arrayClone.add(aux);
        }
        tableModelPetitorioMedico.clearTable();
        tableModelPetitorioMedico.data = arrayClone;
        tblListaPetitorio.repaint();
        tblListaPetitorio.show();
    }
    
    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        //txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            //FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
        }
        //FarmaUtility.moveFocus(txtProducto);
    }
    
    private void UpdateReleaseProd(KeyEvent keyEvent) {
    }

    private void txtPetitorio_keyTyped(KeyEvent e) {
    }

    private void procesoEnter(KeyEvent keyEvent) {
    }

    /* ************************************************************************ */
    /*                  METODOS DE EVENTOS LISTA X (PETITORIO)                  */
    /* ************************************************************************ */
    private void txtPetitorio_keyPressed(KeyEvent e) {
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
                FarmaGridUtils.aceptarTeclaPresionada(e, myJTable,new JTextField(), 1);
                cargaPrincipioActivo();
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
    
    //Dflores: 07/10/2019
    public void getDatosPetitorioSelect(){
        posSelected = 0;
        posSelected = tblListaPetitorio.getSelectedRow();
        //--
        if(posSelected<0){
            posSelected = 0;
        }
        if(posSelected>=0){
            pSelectCmpMedico = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 0);
            pSelectCodPetitorio = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 2);
            pAdjuntarOtro = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 7);
            pLaboratorio = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posSelected, 8);
        }
    }
    
    private void tblListaPetitorio_keyReleased(KeyEvent e) {
        //--
        cargaPrincipioActivo();
        cargaListaProductos();
        txtAdjuntarOtros.setText(pAdjuntarOtro);
        txtLaboratorios.setText(pLaboratorio);
    }
    
    private void tblListaPetitorio_mouseClicked(MouseEvent e) {
        cargaPrincipioActivo();
        cargaListaProductos();
        txtAdjuntarOtros.setText(pAdjuntarOtro);
        txtLaboratorios.setText(pLaboratorio);
        //FarmaUtility.showMessage(this,"Presionado Prueba "+pSelectCodigoPrincipio,txtProductos);
    }
    
    private void cargaPrincipioActivo() {
        try {
            getDatosPetitorioSelect();
            vListaPrincipios.clear();
            //--
            DBPetitorio.getListaPetitorioPrincipioActivo(vListaPrincipios, pSelectCodPetitorio);
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
    
    private void llenarListadoPrincipios() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; i < vListaPrincipios.size(); i++) {
            ArrayList aux = (ArrayList)((ArrayList)vListaPrincipios.get(i)).clone();
            arrayClone.add(aux);
        }
        modelListaPrincipios.clearTable();
        modelListaPrincipios.data = arrayClone;
        tblPrincipioActivo.repaint();
        tblPrincipioActivo.show();
    }

    /* ************************************************************************ */
    /*             METODOS DE EVENTOS LISTA X (PRINCIPIO ACTIVO)                */
    /* ************************************************************************ */
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
                FarmaGridUtils.aceptarTeclaPresionada(e, myJTable,new JTextField(), 1);
                
            log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));
            
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                procesoEnter(e);
            } else {
                chkKeyPressedTblPrincipio(e);
            }
        } catch (Exception exc) {
            log.error("",exc);
            //log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            //vEjecutaAccionTeclaListado = false;
        }
    }
    
    private void txtPrincipio_keyReleased(KeyEvent e) {
        //--
        if(tblPrincipioActivo.getRowHeight()==0&&txtPrincipio.getText().trim().length()==0){
            llenarListadoPrincipios();
            //iniciaProceso(true);
            lblMensajeFiltro2.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
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
                if (FarmaGridUtils.buscarDescripcion(e, myJTablePrincipio, txtPrincipio, 2) || 
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
    
    private void UpdateReleasePrincipio(KeyEvent keyEvent) {
    }
    
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
    
    private void cargaListaProductos()
    {
        try
        {
            getDatosPrincipioSelect();
            String vFiltroxTurno = "1";
            vListaProductos.clear();
            //--
            if(!pSelectCodigoPrincipio.equals("")){
                DBPetitorio.getListaProductos(vListaProductos, pSelectCodPetitorio, pSelectCodigoPrincipio, vFiltroxTurno);
            }
            fillListProdSelect();
        }catch(SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtProductos);
        }
    }
    
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
    
    private void chkKeyPressedTblPrincipio(KeyEvent e)
    {
      FarmaGridUtils.aceptarTeclaPresionada(e,tblPrincipioActivo,null,0);
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        cerrarVentana(false);
      }
    }
    
    private void tblPrincipioActivo_keyReleased(KeyEvent e) {
        //--
        cargaListaProductos();
    }
    
    private void tblPrincipioActivo_mouseClicked(MouseEvent e) {
        cargaListaProductos();
        //FarmaUtility.showMessage(this,"Presionado Prueba "+pSelectCodigoPrincipio,txtProductos);
    }

    private void btnEliminar_actionPerformed(ActionEvent e) {
        int posPetitorioSel = tblListaPetitorio.getSelectedRow();
        //--
        if( posPetitorioSel<0 ){
            posPetitorioSel = 0;
        }
        //--
        if( posPetitorioSel>=0 ){
            String pCmpMedico = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posPetitorioSel, 0);
            String pCodPetitorio = FarmaUtility.getValueFieldArrayList(tableModelPetitorioMedico.data, posPetitorioSel, 2);
            try {
                eliminaDetallePrincipioAct( pCmpMedico, pCodPetitorio );
                FarmaUtility.showMessage(this,"Se inactivo el Petitorio "+pCodPetitorio+
                                              " del Médico con CMP " + pCmpMedico,txtPetitorio);
                llenarListadoPetitorios();
                cerrarVentana(false);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }else{
            FarmaUtility.showMessage(this,"Solo se aceptan 3 Productos por Principio Activo!\n",txtProductos);
        }    
    }
    
    private void eliminaDetallePrincipioAct(String pCmpMedico,String pCodPetitorio) {
        try {
            DBPetitorio.inactivaPetitorio(pCmpMedico, pCodPetitorio);
            //FarmaUtility.moveFocus(txtPrincipio);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"No se pudo inactivar el Petitorio.\n"+
                                          e.getMessage(),txtProductos);
        }
    }

    private void btnModificar_actionPerformed(ActionEvent e) {
        estadoPetitorio = "modificar";
        int pos = tblListaPetitorio.getSelectedRow();
        getListadoPetitorioxPos(pos);
        DlgNuevoPetitorio dlgListaPac = new DlgNuevoPetitorio(myParentFrame, "", true,
                                                              pCodMedico, estadoPetitorio,
                                                              pSelectCodigoPrincipio, tableModelPetitorioxPos);
        dlgListaPac.setVisible(true);
        cargaListaPetitorioMedico();
        cargaPrincipioActivo();
        cargaListaProductos();
    }
    
    private void getListadoPetitorioxPos(int pos) {
        ArrayList arrayClone = new ArrayList();
        //--
        ArrayList aux = (ArrayList)((ArrayList)arrayListaPetitorios.get(pos)).clone();
        arrayClone.add(aux);
        //--
        tableModelPetitorioxPos.clearTable();
        tableModelPetitorioxPos.data = arrayClone;
        //tblPrincipioActivo.repaint();
        //tblPrincipioActivo.show();
    }
}
