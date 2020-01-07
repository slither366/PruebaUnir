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

import componentes.gs.componentes.ExpressionValidate;

import componentes.gs.componentes.JTextFieldValidate;

import consorcio.admPetitorio.reference.ConstantsPetitorio;

import consorcio.admPetitorio.reference.DBPetitorio;

import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import venta.caja.reference.UtilityCaja;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;


public class DlgSeleccionaProductos extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionaProductos.class);

  Frame myParentFrame;
  FarmaTableModel modelListaProd,modelListaSustituto;
  String vEstadoNota;
  
  ArrayList vListaProductos = new ArrayList();
  JTable myJTable = new JTable();
    public static int vPosOld=0;
    public static int vPosNew=0;    
    
    
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane jscPrincipio = new JScrollPane();
    private JTextField txtProductos = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextFieldValidate txtPacienteTurno = new JTextFieldValidate(ExpressionValidate.SOLO_NUMERO, true);
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel lblPrincipioActivo = new JLabel();
    private JButton btnSeleccionar = new JButton();
    private JTable tblListaProductos = new JTable();
    private JTable tblListaSustitutos = new JTable();
    private JLabel jLabel3 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    //private JTable tblSustitutos = new JTable();
    
    private String pCodPrincipioActivo = "",
                   pNombrePrincipio = "",
                   pCodPetitorio = "";
    private JLabel lblMensajeFiltro = new JLabel();
    
    public static String pCodProducto = "";
    public static String pCantxTurno = "";

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgSeleccionaProductos()
  {
    this(null, "", false,"","","");
  }

  public DlgSeleccionaProductos(Frame parent, String title, boolean modal,
                                String pCodPrincipioActivo, String pNombrePrincipio,
                                String pCodPetitorio)
  {
    super(parent, title, modal);
    myParentFrame = parent;
    this.pCodPrincipioActivo = pCodPrincipioActivo;
    this.pNombrePrincipio = pNombrePrincipio;
    this.pCodPetitorio = pCodPetitorio;
    try
    {
      jbInit();
      FarmaVariables.vAceptar = false;
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
    this.setSize(new Dimension(1266, 712));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Lista de Productos por Principio Activo");
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
        jscPrincipio.setBounds(new Rectangle(10, 115, 1235, 295));
        txtProductos.setBounds(new Rectangle(75, 50, 620, 25));
        txtProductos.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtProductos_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtProductos_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtProductos_keyTyped(e);
                }
            });
        jLabel1.setText("Producto :");
        jLabel1.setBounds(new Rectangle(10, 50, 85, 25));
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        jLabel2.setText("N\u00b0 Pacientes a los que prescribe dicho medicamento por turno");
        jLabel2.setBounds(new Rectangle(730, 50, 365, 30));
        jLabel2.setFont(new Font("SansSerif", 1, 12));
        txtPacienteTurno.setBounds(new Rectangle(1100, 50, 65, 30));
        txtPacienteTurno.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPacienteTurno_keyPressed(e);
                }
            });
        jPanel2.setBounds(new Rectangle(10, 95, 1235, 20));
        jPanel2.setBackground(new Color(0, 132, 198));
        jPanel2.setLayout(null);
        jPanel3.setBounds(new Rectangle(360, 10, 545, 30));
        jPanel3.setLayout(null);
        lblPrincipioActivo.setText("SELECCION PRINCIPIO ACTIVO");
        lblPrincipioActivo.setBounds(new Rectangle(15, 5, 515, 15));
        lblPrincipioActivo.setFont(new Font("SansSerif", 1, 17));
        lblPrincipioActivo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrincipioActivo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSeleccionar.setText("[Enter] Seleccionar");
        btnSeleccionar.setBounds(new Rectangle(20, 635, 135, 25));
        jLabel3.setText("Productos Sustitutos");
        jLabel3.setBounds(new Rectangle(10, 415, 280, 15));
        jScrollPane1.setBounds(new Rectangle(10, 440, 1235, 175));
        lblMensajeFiltro.setBounds(new Rectangle(5, 5, 960, 15));
        lblMensajeFiltro.setFont(new Font("SansSerif", 0, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        jScrollPane1.getViewport().add(tblListaSustitutos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(btnSeleccionar, null);
        jPanel3.add(lblPrincipioActivo, null);
        jContentPane.add(jPanel3, null);
        jPanel2.add(lblMensajeFiltro, null);
        jContentPane.add(jPanel2, null);
        jContentPane.add(txtPacienteTurno, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(txtProductos, null);
        jscPrincipio.getViewport().add(tblListaProductos, null);
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
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    lblPrincipioActivo.setText(pNombrePrincipio);  
      
    modelListaProd = new FarmaTableModel(ConstantsPetitorio.columnsListaProductos,ConstantsPetitorio.defaultValuesListaProductos,0);
    FarmaUtility.initSimpleList(tblListaProductos,modelListaProd,ConstantsPetitorio.columnsListaProductos);
      tblListaProductos.setName("productos_padre");
      
   modelListaSustituto = new FarmaTableModel(ConstantsPetitorio.columnsListaProductos,ConstantsPetitorio.defaultValuesListaProductos,0);
   FarmaUtility.initSimpleList(tblListaSustitutos,modelListaSustituto,ConstantsPetitorio.columnsListaProductos);
      tblListaSustitutos.setName("productos_hijo");
      
    cargaListaProductos();
    
    FarmaUtility.moveFocus(txtProductos);
  }
  
  private void cargaListaProductos()
  {
    try
    {
        String pFiltroxTruno = "0";
        DBPetitorio.getListaProductos(vListaProductos, pCodPetitorio, pCodPrincipioActivo, pFiltroxTruno);
        llenarListadoProductos();
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n" + sql.getMessage(),txtProductos);
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

    private void jButton1_actionPerformed(ActionEvent e) {
    }
    
    private void filtroGoogle() {
        String condicion = txtProductos.getText().toUpperCase();
        
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            llenarListadoProductos();
            //filtrar java
            ArrayList target = modelListaProd.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String pNomProd = fila.get(2).toString().toUpperCase().trim();
                String pDesGenerico = fila.get(6).toString().toUpperCase().trim().replace(" ", "");                
                if(pNomProd.contains(condicion)||pDesGenerico.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            //limpia las tablas auxiliares                
            modelListaProd.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            modelListaProd.fireTableDataChanged();
            setJTable(tblListaProductos);
            if(tblListaProductos.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                llenarListadoProductos();
            }
            else{
                if(tblListaProductos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblListaProductos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblListaProductos.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblListaProductos.getRowCount()>0)
            FarmaGridUtils.showCell(tblListaProductos, 0, 0);
    }

    private void llenarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; i < vListaProductos.size(); i++) {
            ArrayList aux = (ArrayList)((ArrayList)vListaProductos.get(i)).clone();
            arrayClone.add(aux);
        }
        modelListaProd.clearTable();
        modelListaProd.data = arrayClone;
        tblListaProductos.repaint();
        tblListaProductos.show();
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
        modelListaSustituto.clearTable();
        modelListaSustituto.fireTableDataChanged();
        //carga Productos sustitutos
        int pPos = tblListaProductos.getSelectedRow();
        if(pPos>=0){
            String pCodProd = FarmaUtility.getValueFieldArrayList(modelListaProd.data, pPos, 0);
            try {
                DBPetitorio.getListaSustitutos(modelListaSustituto,pCodProd);
            } catch (Exception e) {
                e.printStackTrace();
            }    
        }
    }

    private void txtProductos_keyReleased(KeyEvent e) {

        if(tblListaProductos.getRowHeight()==0&&txtProductos.getText().trim().length()==0){
            llenarListadoProductos();
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
            if(tblListaProductos.getRowCount() >= 0 && 
               modelListaProd.getRowCount() > 0 && 
                e.getKeyChar() != '+') {
                if ((e.getKeyCode() == KeyEvent.VK_UP || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                    (e.getKeyCode() == KeyEvent.VK_DOWN || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    vPosNew = tblListaProductos.getSelectedRow();
                    if (vPosOld == 0 && vPosNew == 0) {
                        UpdateReleaseProd(e);
                        vPosOld = vPosNew;
                    } else {
                        if (vPosOld != vPosNew) {
                            UpdateReleaseProd(e);
                            vPosOld = vPosNew;
                        }
                    }
                    UpdateReleaseProd(e);
                }
            }
        }      
    }

    private void txtProductos_keyPressed(KeyEvent e) {

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
            /*else    
                FarmaGridUtils.aceptarTeclaPresionada(e, myJTable,new JTextField(), 1);*/
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


    private void txtProductos_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '+') {
                    e.consume();
                    if (myJTable.getName().equalsIgnoreCase("productos_padre")) {
                        setJTable(tblListaSustitutos);
                    } else //if(myJTable.getName().equalsIgnoreCase(ConstantsVentas.NAME_TABLA_PRODUCTOS_ALTERNATIVOS)) {
                    {
                        setJTable(tblListaProductos);
                    }
        }
    }
    
    public void procesoEnter(KeyEvent e){
        int pos = tblListaProductos.getSelectedRow();
            
        if(validaDatos()){
            if(pos>= 0){
                String existeTurnoOld = FarmaUtility.getValueFieldArrayList(modelListaProd.data, pos, 7);
                pCodProducto = FarmaUtility.getValueFieldArrayList(modelListaProd.data, pos, 1);
                pCantxTurno = txtPacienteTurno.getText();
                if(existeTurnoOld.equals("")){
                    cerrarVentana(true);
                }else{
                    FarmaUtility.showMessage(this, "Este producto ya fue seleccionado!", txtProductos);
                }
            }else{
                FarmaUtility.showMessage(this, "Debe seleccionar un registro.", txtProductos);
            }
        }else{
            log.info("Falta llenar el Campo Paciente x Turno! ");
        }
    }

    private boolean validaDatos(){
        //Dflores: 07.10.2019 * PREGUNTAR PORQUE CUANDO LE MANDO txtPacienteTurno lo toma como null!!
        if(UtilityAdmisionMedica.valorCampoEsNulo(txtPacienteTurno,this)){
            //FarmaUtility.showMessage(this, "Falta llenar el Campo Paciente x Turno!" ,txtPacienteTurno);
            return false;
        }
        return Boolean.TRUE;
    }

    private void txtPacienteTurno_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtProductos);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }
}
