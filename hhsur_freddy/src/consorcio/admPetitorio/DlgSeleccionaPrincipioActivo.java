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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

import java.util.Iterator;

import javax.swing.JTextField;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.campana.reference.VariablesCampana;

import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;

import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;

import static venta.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import venta.reference.VariablesPtoVenta;


public class DlgSeleccionaPrincipioActivo extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgSeleccionaPrincipioActivo.class);

  Frame myParentFrame;
  FarmaTableModel modelLista;
  ArrayList modelListaOriginal = new ArrayList();
  String vEstadoNota;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane jscPrincipio = new JScrollPane();
    private JTextField txtPrincipio = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private JButton jButton5 = new JButton();
    private JTable tblPrincipioActivo = new JTable();
    private JLabel lblMensajeFiltro = new JLabel();
    
    // para filtro google
    private JTable myJTable;
    public static int vPosOld=0;
    public static int vPosNew=0;    
    
    public static String pSelectCodigo = "";
    public static String pPrincipio = "";

    /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgSeleccionaPrincipioActivo()
  {
    this(null, "", false);
  }

  public DlgSeleccionaPrincipioActivo(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    myParentFrame = parent;
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
    this.setSize(new Dimension(410, 609));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Principios Activos");
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
    lblEsc.setBounds(new Rectangle(340, 520, 100, 30));
        jscPrincipio.setBounds(new Rectangle(10, 80, 380, 430));
        txtPrincipio.setBounds(new Rectangle(10, 20, 375, 25));
        txtPrincipio.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtPrincipio_focusLost(e);
                }
            });
        txtPrincipio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtPrincipio_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtPrincipio_keyPressed(e);
                }
            });
        jPanel1.setBounds(new Rectangle(10, 50, 380, 30));
        jPanel1.setBackground(new Color(0, 132, 198));
        jPanel1.setLayout(null);
        jButton5.setText("[Enter] Seleccionar");
        jButton5.setBounds(new Rectangle(10, 520, 135, 25));
        lblMensajeFiltro.setBounds(new Rectangle(5, 10, 365, 15));
        lblMensajeFiltro.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        jContentPane.add(jButton5, null);
        jPanel1.add(lblMensajeFiltro, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(txtPrincipio, null);
        jscPrincipio.getViewport().add(tblPrincipioActivo, null);
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
    modelLista = new FarmaTableModel(ConstantsPetitorio.columnsListaPrincipiosActivos,ConstantsPetitorio.defaultValuesListaPrincipiosActivos,0);
    FarmaUtility.initSimpleList(tblPrincipioActivo,modelLista,ConstantsPetitorio.columnsListaPrincipiosActivos);
    tblPrincipioActivo.getTableHeader().setReorderingAllowed(false);
    tblPrincipioActivo.getTableHeader().setResizingAllowed(false);    
    cargaListaPetitorio();
    llenarListadoProductos();
    setJTable(tblPrincipioActivo);
  }
  
  private void cargaListaPetitorio()
  {
    try
    {
      DBPetitorio.getListaPrincipioActivo(modelListaOriginal);
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de principios activos : \n" + sql.getMessage(),txtPrincipio);
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

    private void jButton1_actionPerformed(ActionEvent e) {
    }

    private void txtPrincipio_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtPrincipio);
    }

    private void txtPrincipio_keyReleased(KeyEvent e) {
        
        if(tblPrincipioActivo.getRowHeight()==0&&txtPrincipio.getText().trim().length()==0){
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
            if(tblPrincipioActivo.getRowCount() >= 0 && 
               modelLista.getRowCount() > 0 && 
                e.getKeyChar() != '+') {
                if (FarmaGridUtils.buscarDescripcion(e, myJTable, txtPrincipio, 
                                                     2) || 
                    (e.getKeyCode() == KeyEvent.VK_UP || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                    (e.getKeyCode() == KeyEvent.VK_DOWN || 
                     e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    vPosNew = tblPrincipioActivo.getSelectedRow();
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
        String condicion = txtPrincipio.getText().toUpperCase();
        
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            llenarListadoProductos();
            //filtrar java
            ArrayList target = modelLista.data;        
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
            modelLista.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            modelLista.fireTableDataChanged();
            setJTable(tblPrincipioActivo);
            if(tblPrincipioActivo.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                llenarListadoProductos();
            }
            else{
                if(tblPrincipioActivo.getRowCount()==1)
                    lblMensajeFiltro.setText(tblPrincipioActivo.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblPrincipioActivo.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            llenarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblPrincipioActivo.getRowCount()>0)
            FarmaGridUtils.showCell(tblPrincipioActivo, 0, 0);
    }

    private void llenarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < modelListaOriginal.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)modelListaOriginal.get(i)).clone();
            arrayClone.add(aux);
        }
        modelLista.clearTable();
        modelLista.data = arrayClone;
        tblPrincipioActivo.repaint();
        tblPrincipioActivo.show();
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
            pSelectCodigo = FarmaUtility.getValueFieldArrayList(modelLista.data, pos, 0);
            pPrincipio = FarmaUtility.getValueFieldArrayList(modelLista.data, pos, 1);
            
            cerrarVentana(true);
        }
        else
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", txtPrincipio);
    }    
}
