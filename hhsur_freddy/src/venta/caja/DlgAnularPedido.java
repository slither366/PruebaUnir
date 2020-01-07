package venta.caja;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JPanelHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.DlgLogin;
import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLengthText;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.caja.reference.VariablesVirtual;
import venta.ce.reference.VariablesCajaElectronica;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.modulo_venta.DlgConsultaXCorrelativo;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import common.*;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JPanelHeader;

import svb.fact_electronica.reference.UtilityFactElectronica;

import venta.caja.reference.UtilityRecargaVirtual;
import venta.recetario.reference.FacadeRecetario;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.DlgConsultaXCorrelativo;
import venta.modulo_venta.reference.UtilityModuloVenta;

public class DlgAnularPedido extends JDialog 
{
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgAnularPedido.class);
         
    FarmaTableModel tableModelCabeceraPedido;
    FarmaTableModel tableModelDetallePedido;
    Frame myParentFrame;
    private boolean vAceptar=false;
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JPanel jPanel4 = new JPanel();
    private JButton btnDetalle = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel3 = new JPanel();
    private JButton btnCabecera = new JButton();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JTextField txtMonto = new JTextField();
    private JLabel lblMonto = new JLabel();
    private JButton btnBuscar = new JButton();
    private JTextField txtCorrelativo = new JTextField();
    private JButton btnCorrelativo = new JButton();
    private JTable tblCabeceraPedido = new JTable();
    private JTable tblDetallePedido = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblMensaje = new JLabelOrange();
    //JMIRANDA 25.08.2011 Fijar Objeto para Focus
    private Object pObj = txtCorrelativo;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgAnularPedido()
    {
        this(null, "", false);
    }

    public DlgAnularPedido(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        myParentFrame = parent;
        try
        {
            jbInit();
            initialize();
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
    this.setSize(new Dimension(787, 390));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Anulacion de Pedidos");
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
    jContentPane.setLayout(null);
    jContentPane.setSize(new Dimension(513, 307));
    jContentPane.setBackground(Color.white);
    jScrollPane2.setBounds(new Rectangle(5, 190, 770, 140));
    jScrollPane2.setBackground(new Color(255, 130, 14));
    jPanel4.setBounds(new Rectangle(5, 170, 770, 20));
    jPanel4.setBackground(new Color(0, 114, 169));
    jPanel4.setLayout(null);
    btnDetalle.setText("Detalle Pedido");
    btnDetalle.setBounds(new Rectangle(15, 0, 115, 20));
    btnDetalle.setMnemonic('d');
    btnDetalle.setBackground(new Color(255, 130, 14));
    btnDetalle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnDetalle.setBorderPainted(false);
    btnDetalle.setContentAreaFilled(false);
    btnDetalle.setDefaultCapable(false);
    btnDetalle.setFocusPainted(false);
    btnDetalle.setForeground(Color.white);
    btnDetalle.setFont(new Font("SansSerif", 1, 11));
    btnDetalle.setHorizontalAlignment(SwingConstants.LEFT);
    btnDetalle.setRequestFocusEnabled(false);
    btnDetalle.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnDetalle_keyPressed(e);
        }
      });
    btnDetalle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDetalle_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(5, 80, 770, 80));
    jScrollPane1.setBackground(new Color(255, 130, 14));
    jPanel3.setBounds(new Rectangle(5, 60, 770, 20));
    jPanel3.setBackground(new Color(0, 114, 169));
    jPanel3.setLayout(null);
    btnCabecera.setText("Cabecera Pedido");
    btnCabecera.setBounds(new Rectangle(15, 0, 115, 20));
    btnCabecera.setMnemonic('c');
    btnCabecera.setBackground(new Color(255, 130, 14));
    btnCabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCabecera.setBorderPainted(false);
    btnCabecera.setContentAreaFilled(false);
    btnCabecera.setDefaultCapable(false);
    btnCabecera.setFocusPainted(false);
    btnCabecera.setForeground(Color.white);
    btnCabecera.setFont(new Font("SansSerif", 1, 11));
    btnCabecera.setHorizontalAlignment(SwingConstants.LEFT);
    btnCabecera.setRequestFocusEnabled(false);
    btnCabecera.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnCabecera_keyPressed(e);
        }
      });
    btnCabecera.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCabecera_actionPerformed(e);
        }
      });
    pnlHeader.setBounds(new Rectangle(5, 5, 770, 45));
    txtMonto.setBounds(new Rectangle(300, 10, 80, 25));
    txtMonto.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtMonto_keyPressed(e);
                }

        public void keyTyped(KeyEvent e)
        {
                        txtMonto_keyTyped(e);
                    }
      });
    lblMonto.setText("Monto :");
    lblMonto.setBounds(new Rectangle(245, 10, 55, 25));
    lblMonto.setFont(new Font("SansSerif", 1, 11));
    lblMonto.setForeground(Color.white);
    btnBuscar.setText("Buscar");
    btnBuscar.setBounds(new Rectangle(480, 10, 90, 25));
    btnBuscar.setMnemonic('s');
    btnBuscar.setRequestFocusEnabled(false);
    btnBuscar.setDefaultCapable(false);
    btnBuscar.setFocusPainted(false);
    btnBuscar.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          btnBuscar_keyPressed(e);
        }
      });
    btnBuscar.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnBuscar_actionPerformed(e);
                    }
      });
    txtCorrelativo.setBounds(new Rectangle(90, 10, 115, 25));
    txtCorrelativo.setFont(new Font("SansSerif", 0, 12));
    txtCorrelativo.addKeyListener(new KeyAdapter()
      {

        public void keyTyped(KeyEvent e)
        {
                        txtCorrelativo_keyTyped(e);
                    }

        public void keyPressed(KeyEvent e)
        {
          txtCorrelativo_keyPressed(e);
        }
      });
    btnCorrelativo.setText("Correlativo :");
    btnCorrelativo.setBounds(new Rectangle(15, 15, 70, 15));
    btnCorrelativo.setMnemonic('r');
    btnCorrelativo.setBackground(new Color(255, 130, 14));
    btnCorrelativo.setBorderPainted(false);
    btnCorrelativo.setContentAreaFilled(false);
    btnCorrelativo.setDefaultCapable(false);
    btnCorrelativo.setFocusPainted(false);
    btnCorrelativo.setForeground(Color.white);
    btnCorrelativo.setHorizontalAlignment(SwingConstants.LEFT);
    btnCorrelativo.setFont(new Font("SansSerif", 1, 11));
    btnCorrelativo.setRequestFocusEnabled(false);
    btnCorrelativo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnCorrelativo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCorrelativo_actionPerformed(e);
        }
      });
        tblCabeceraPedido.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblCabeceraPedido_keyPressed(e);
                    }
                });
        lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(685, 335, 90, 20));
    lblF11.setText("[ F11 ] Anular");
    lblF11.setBounds(new Rectangle(590, 335, 85, 20));
        lblMensaje.setBounds(new Rectangle(10, 340, 475, 35));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("SansSerif", 1, 19));
        jScrollPane2.getViewport();
    jScrollPane1.getViewport();
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(lblMonto, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jScrollPane2.getViewport().add(tblDetallePedido, null);
        jContentPane.add(jScrollPane2, null);
        jPanel4.add(btnDetalle, null);
        jContentPane.add(jPanel4, null);
        jScrollPane1.getViewport().add(tblCabeceraPedido, null);
        jContentPane.add(jScrollPane1, null);
        jPanel3.add(btnCabecera, null);
        jContentPane.add(jPanel3, null);
        //this.getContentPane().add(jContentPane, null);
        jContentPane.add(pnlHeader, null);
        txtCorrelativo.setDocument(new FarmaLengthText(10));
  }
  /* ************************************************************************ */
	/*                                  METODO initialize                       */
	/* ************************************************************************ */

    private void initialize()
    {
        initTableCabeceraPedido();
        initTableDetallePedido();
        VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        FarmaVariables.vAceptar=false;
        lblMensaje.setText("");
        VariablesCaja.vDescripImpr="";
        setearObjetoFocus();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableCabeceraPedido()
    {
        tblCabeceraPedido.getTableHeader().setReorderingAllowed(false);
        tblCabeceraPedido.getTableHeader().setResizingAllowed(false);
        
        tblDetallePedido.getTableHeader().setReorderingAllowed(false);
        tblDetallePedido.getTableHeader().setResizingAllowed(false);
        
        tableModelCabeceraPedido = new FarmaTableModel(ConstantsCaja.columnsListaPedidos,ConstantsCaja.defaultValuesListaPedidos,0);
        FarmaUtility.initSimpleList(tblCabeceraPedido,tableModelCabeceraPedido,ConstantsCaja.columnsListaPedidos);
    }
  
    private void cargaListaPedidos()
    {
        try
        {
            DBCaja.getListaCabeceraPedido(tableModelCabeceraPedido,txtCorrelativo.getText().trim());
            log.info("Listado:"+tableModelCabeceraPedido.data.get(0) );
        }
        catch(SQLException e)
        {
            log.error("",e);
            FarmaUtility.showMessage(this,"Ocurri� un error al listar los pedidos  - \n" + e.getMessage(),pObj);
        }
    }
  
    private void initTableDetallePedido()
    {
        tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsListaProductos,ConstantsCaja.defaultValuesListaProductos,0);
        FarmaUtility.initSimpleList(tblDetallePedido,tableModelDetallePedido,ConstantsCaja.columnsListaProductos);
    }
  
    private void cargaListaProductos()
    {
        try
        {
            DBCaja.getListaDetallePedido(tableModelDetallePedido,txtCorrelativo.getText().trim(),"%","%");
        }
        catch(SQLException e)
        {
            log.error("",e);
            FarmaUtility.showMessage(this,"Ocurri� un error al listar los productos  - \n" + e.getMessage(),pObj);
        }
    }
  
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnCorrelativo_actionPerformed(ActionEvent e)
    {
        //JMIRANDA 22.08.2011
        if(UtilityModuloVenta.getIndImprimeCorrelativo())
        {
            txtCorrelativo.setEnabled(true);
            txtMonto.setEnabled(true);
            FarmaUtility.moveFocus(txtCorrelativo);
        }
        else
        {
            mostrarCorrelativoXComprobante();
        }
    }

  private void txtCorrelativo_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      txtCorrelativo.setText(FarmaUtility.caracterIzquierda(txtCorrelativo.getText(),10,"0"));
      FarmaUtility.moveFocus(txtMonto);
    }
    else
      chkKeyPressed(e);
  }

  private void txtCorrelativo_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCorrelativo,e);
  }

  private void txtMonto_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
      //FarmaUtility.moveFocus(btnBuscar);
      btnBuscar.doClick();
    }
    else
      chkKeyPressed(e);
  }

  private void txtMonto_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitosDecimales(txtMonto,e);
  }
  
    private void btnBuscar_actionPerformed(ActionEvent e)
    {
        lblMensaje.setText("");
        if(validarCampos())
        {
            //Agregado por DVELIZ 05.01.2009
            VariablesCaja.vIndLineaADMCentral = "N";//indicador de linea en N
            evaluaPedidoProdVirtual(txtCorrelativo.getText().trim());//verifica si es un pedido virtual
            /*if(VariablesVirtual.vConProductoVirtual)
            {
                validarConexionADMCentral();//VariablesCaja.vIndLineaADMCentral
            }*/
            //log.debug("JCALLO: antes de buscar pedido VariablesCaja.vIndLineaADMCentral:"+VariablesCaja.vIndLineaADMCentral);
            if(buscarPedido())
            {
                cargaListaPedidos();
                cargaListaProductos();
                //btnCabecera.doClick();
            }
            else
            {
                tableModelCabeceraPedido.clearTable();
                tableModelDetallePedido.clearTable();
                tblCabeceraPedido.removeAll();
                tblDetallePedido.removeAll();
            }
        }
    }
  
  /**
   * @AUTHOR JCORTEZ
   * @SINCE 10.06.09
   * * */
    private boolean validaImprIP()
    {
        boolean valor=true;
        String tipComp="";
        try
        {
            //JCORTEZ 09.06.09  Se obtiene tipo de comrpobante de la relacion maquina - impresora
            tipComp=VariablesCaja.vTipComp.trim();
            if( tipComp.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET) ||
                tipComp.equalsIgnoreCase(ConstantsModuloVenta.TIPO_COMP_TICKET_FACT))
            {
                //VariablesCaja.vSecImprLocalTicket= DBCaja.getObtieneSecImpPorIP(FarmaVariables.vIpPc);
                VariablesCaja.vSecImprLocalTicket= DBCaja.getObtieneSecImpPorOrigen(FarmaVariables.vIpPc,
                                                                                    tipComp,
                                                                                    VariablesCaja.vNumPedVta_Anul);
            
                log.debug("ANULANDO PEDIDO");
                log.debug("JCORTEZ: VariablesCaja.vTipComp--> "+VariablesCaja.vTipComp);
                log.debug("JCORTEZ: VariablesCaja.vSecImprLocalTicket--> "+VariablesCaja.vSecImprLocalTicket);
                if(VariablesCaja.vSecImprLocalTicket.trim().equalsIgnoreCase("N"))
                {
                    FarmaUtility.showMessage(this,"No se cuenta con una impresora asignada de ticket. Verifique!!!",pObj);
                    valor=false;
                }
            }
        }
        catch(Exception e)
        {
            log.error("",e);
            FarmaUtility.showMessage(this,"Error al validar relacion IP - impresora. Verifique!!!",pObj);                
            valor=false;
        }
        return valor;
    }

  private void btnBuscar_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER)
    {
        //JMIRANDA 22.08.2011
        if(UtilityModuloVenta.getIndImprimeCorrelativo()){
            FarmaUtility.moveFocus(tblCabeceraPedido);}
        else{        
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        }
    }
    else
      chkKeyPressed(e);
  }
  
  private void btnCabecera_actionPerformed(ActionEvent e)
  {
    //JMIRANDA 22.08.2011
    if(UtilityModuloVenta.getIndImprimeCorrelativo()){
        FarmaUtility.moveFocus(tblCabeceraPedido);}
    else{        
        FarmaUtility.moveFocusJTable(tblCabeceraPedido);
    }
  }
  
  private void btnCabecera_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblCabeceraPedido,null,0);
    
    chkKeyPressed(e);
  }

  private void btnDetalle_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(btnDetalle);
  }

  private void btnDetalle_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e,tblDetallePedido,null,0);
     chkKeyPressed(e);
  }
  
    private void this_windowOpened(WindowEvent e)
    {    
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCorrelativo);
        cargaLogin();
            
        //Verifica si muestra Nueva pantalla
        if(!validarMostrarCorrelativo() && this.isVisible() )
        {
            mostrarCorrelativoXComprobante();
            FarmaUtility.moveFocus(tblCabeceraPedido);    
        }
    }
  
  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", pObj);
  }
  
  private void tblCabeceraPedido_keyPressed(KeyEvent e) {
        
        chkKeyPressed(e);
  }
  
/* ************************************************************************ */
/*                     METODOS AUXILIARES DE EVENTOS                        */
/* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e)
    {   
        if(UtilityPtoVenta.verificaVK_F11(e))
        {   
            //se pregunta si el pedido fue realizado con Pinpad o POS
            if(true)
            {   //consulta si el pedido puede anularse, si no se encuentra en un Cierre Z
                //se realiza la codificacion para anular la transacci�n en el pinpad correspondiente
                //se obtiene el retorno de la informaci�n del pinpad y se lo guarda
                //se indica que siga el proceso de anulaci�n del pedido
                //si no se puede realizar la anulacion de la transaccion, no se continua con la anulacion del pedido
            }
            validaImprIP();
            if(vAceptar)
            {   if(tblCabeceraPedido.getRowCount()>0)
                {
                    if (VariablesPtoVenta.vIndVerReceMagis.equals(FarmaConstants.INDICADOR_S)) {
                        //LLEIVA 13-Jun-2013 - Se verifica el estado de recetario adjunto al pedido
                        FacadeRecetario facadeRec = new FacadeRecetario();
                        if (facadeRec.getValidadAnulacionRecetarioPedido(txtCorrelativo.getText().trim())) {
                            facadeRec.anularRecetarioPedido(txtCorrelativo.getText().trim());
                            //facadeRec.enviaTramaActualizaRecetFASA(numRecetario, "N")
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "No se puede anular el pedido, debido a que el recetario magistral adjunto\n" +
                                                    "se ecuentra procesandose o anulado", pObj);
                            return;
                        }
                    }
                    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
                    String indConvBTLMF = "N";//UtilityConvenioBTLMF.indConvenioBTLMF(this);
                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(this, null) && indConvBTLMF != null &&
                        indConvBTLMF.equals("S")) {
                        anularPedidoBTLMF();
                    } else {
                        anularPedido();
                    }
                }
                if(FarmaVariables.vAceptar)
                    cerrarVentana(true);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }

  private void cerrarVentana(boolean pAceptar){
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }
  
  /* ************************************************************************ */
	/*                     METODOS DE LOGICA DE NEGOCIO                         */
	/* ************************************************************************ */

    private void cargaLogin()
    {
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar)
        {
            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        }
        else
            cerrarVentana(false);
    }

    private boolean validarCampos()
    {
        boolean retorno=true;
        if(txtCorrelativo.getText().trim().equals(""))
        {
            FarmaUtility.showMessage(this,"Debe ingresar el Correlativo.",txtCorrelativo);
            retorno = false;
        }
        else if(txtMonto.getText().trim().equals(""))
        {
            FarmaUtility.showMessage(this,"Debe ingresar el Monto.",txtMonto);
            retorno = false;
        }
        else if(!FarmaUtility.validateDecimal(this,txtMonto,"Debe ingresar un Monto V�lido.",false))
        {
            retorno = false;
        }
        return retorno;
    }
  
    private boolean buscarPedido()
    {
        VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        boolean retorno=false;
        vAceptar = false;
        try
        {
            //log.debug("jcallo: antes de verificarPedido");
            DBCaja.verificaPedido(txtCorrelativo.getText().trim(),
                                  txtMonto.getText().trim());
            //log.debug("jcallo: despues de verificarpedido");
            //AGREGADO POR DVELIZ 30.12.2008
            //validarConexionADMCentral();
            //obtieneInfoPedidoVirtual();
            //log.debug("JCALLO: VariablesVirtual.vConProductoVirtual:"+VariablesVirtual.vConProductoVirtual);
            if(VariablesVirtual.vConProductoVirtual)
            {
                //log.debug("JCALLO: ... VariablesCaja.vIndLineaADMCentral : "+VariablesCaja.vIndLineaADMCentral);
                //if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_N))
                //{
                //    if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                //                                                "Para anular un pedido sin conexion a MATRIZ " +
                //                                                "logearse como OPERADOR DEL SISTEMA, desea continuar?"))
                //    {
                //        if( cargaLoginAdmLocal() )
                //        {
                //            retorno = true;
                //            vAceptar = true;
                //            VariablesCaja.vIndAnulacionConReclamoNavsat = true;
                //            obtieneInfoPedidoVirtual(); 
                //            VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
                //            return retorno;
                //       }
                //        else
                //        {
                //            FarmaUtility.showMessage(this,"No es posible realizar la operaci�n.",pObj);
                //            return retorno;
                //        }
                //    }
                //    /* FarmaUtility.showMessage(this, "No existe conexion con MATRIZ. Int�ntelo mas tarde.", null);
                //        retorno = false;
                //        vAceptar = false;
                //        return retorno;*/
                //}
                //else
                //{
                //    obtieneInfoPedidoVirtual(); 
                //    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim(); 
                //    //VariablesCaja.vRespuestaExito = obtieneRespuestaRecarga().trim(); 
                //    retorno = true;
                //    vAceptar = true;
                //    return retorno;
                //}
                //FIN DVELIZ 
            
                retorno = validarRecargas();
                VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
                vAceptar = retorno;
            }
            else
            {
                retorno = true;
                vAceptar = true;
            }
            return retorno;
        }
        catch(SQLException e)
        {
            /* //Agregado por DVELIZ 15.12.2008
            validarConexionADMCentral();
            obtieneInfoPedidoVirtual();
            if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_N)){
                FarmaUtility.showMessage(this, "No existe conexion con MATRIZ. Int�ntelo mas tarde.", null);
                retorno = false;
                vAceptar = false;
                return retorno;
            }
            //Fin DVELIZ*/
        
            retorno = false;
            vAceptar = false;
            if(e.getErrorCode()==20001)
            {
                FarmaUtility.showMessage(this,"No se encuentra ningun Pedido con estos datos. �Verifique!",pObj);
            }
            else if(e.getErrorCode()==20002)
            {
                FarmaUtility.showMessage(this,"El Pedido no ha sido cobrado. �No puede Anular este Pedido!",pObj);
            }
            else if(e.getErrorCode()==20003)
            {
                FarmaUtility.showMessage(this,"El Pedido ya est� anulado. �No puede Anular este Pedido!",pObj);
            }
            else if(e.getErrorCode()==20004)
            {
                if(JConfirmDialog.rptaConfirmDialog(this,
                                "El pedido no pueder ser anulado porque cuenta con un producto del tipo Tarjeta Virtual.\n" +
                                "Esta seguro de anular de todas formas?."))
                {
                    if( cargaLoginAdmLocal() )
                    {
                        retorno = true;
                        vAceptar = true;
                        VariablesCaja.vIndAnulacionConReclamoNavsat = true;
                    }
                    else
                    {
                        FarmaUtility.showMessage(this,"No es posible realizar la operaci�n.",pObj);
                    }
                }
            }
            else if(e.getErrorCode()==20005)
            {
                /**
                 * SE OBTIENE EL VALOR DE TIEMPO MAXIMO QUE PASO PARA ANULAR UNA RECARGA VIRTUAL
                 * @AUTHOR DUBILLUZ
                 * @SINCE  09.11.2007
                 * @modificado dveliz 05.01.2009
                 */
                VariablesCaja.vIndPedidoRecargaVirtual = FarmaConstants.INDICADOR_S; 
                VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
                String tiempo_maximo  = time_max(txtCorrelativo.getText().trim()).trim();
                
                //LLEIVA 27-Mar-2014 Se cambio la validaci�n de recarga
                retorno = validarRecargas();
                vAceptar = retorno;
                
                //log.debug("JCALLO : indExitoRecarga : "+indExitoRecarga);
                VariablesCaja.vIndPedidoRecargaVirtual = "";        
                return retorno;
            }
            else if(e.getErrorCode()==20015)
            {
                FarmaUtility.showMessage(this,"El pedido gener� cupones y ya fueron usados. No puede anular el comprobante.",pObj);
            }
            else
            {
                log.error("",e);
                FarmaUtility.showMessage(this,"Ocurri� un error al buscar un pedido  - \n" +e.getMessage(),pObj);
            }
        }
        return retorno;
    }
  
  private void anularPedidoBTLMF()
  {
    cargarVariables();

    int vCantCuponesEmitidosUsados = 0;
     //--Obtiene los cupones emitidos usados
    vCantCuponesEmitidosUsados = UtilityCaja.getCuponesEmitidosUsados(txtCorrelativo.getText().trim(), this, pObj);
    if(vCantCuponesEmitidosUsados > 0)
     {
              FarmaUtility.showMessage(this,"Los cupones que gener� este pedido han sido usados."+
             " Por favor verifique si se debe anular dicho canje.",pObj);
     }



		    if(comprobarFecha())// || comprobarDocumento())
		    {
		      if(!comprobarDocumentoNotaCredito())
		      {
		        FarmaUtility.showMessage(this, "No se puede anular un pedido de tipo Nota de Cr�dito. Verifique!!!",pObj);
		      } else
		      {

		        /**
		        * @AUTHOR JCORTEZ
		        * @SINCE 10.06.09
		        * Se evitara anulara si es que la ip no cuenta con impresora
		        * */
		        if (validaImprIP()){

		          /**
		           * Valida pedido Fidelizado
		           * @author: JCORTEZ
		           * @since: 18/12/2008
		           * */
		           log.debug("VariablesCaja.vIndPedFidelizado : " +VariablesCaja.vIndPedFidelizado);
		           if(VariablesCaja.vIndPedFidelizado.equalsIgnoreCase("S")){
		        	   log.info("es un pedido de un cliente fidelizado ...");
		           //if(validaExistProd()){
		        	   log.debug("aqui verificara si hay linea con matriz...ELLO IMPLICA CUALQUIER CONEXION REMOTA que se tenga se perdera" +
		        	   		"por lo que es PROBABLE que hayas problemas al eliminar recargas virtuales que compro un cliente fidelizado");
		              /*
		              if(ExistLineaMatriz()){//aqui al momento de preguntar por si hay linea con matriz
		                  log.info("ANULA PRUEBA anularPedidofidelizadoMatriz");
		                  if(anularPedidofidelizadoMatriz(FarmaConstants.INDICADOR_N))
		                      //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		                        anular();
		              }else{
		                   log.info("ANULA PRUEBA anularPedidofidelizado");
		                 if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		                  anular();
		               }
		              */
		              //////////
		              log.info("DUBILLUZ - 30.11.2009 - ANULA PRUEBA anularPedidofidelizado");
		              //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
		              //JCORTEZ 18.03.10 se valida pedido antes de procesarlo
		               if(validaPedido(FarmaConstants.INDICADOR_S))
		                 anular();

		           //}
		           }else {
		             //No es fidelizado
		              log.debug("No es fidelizado");
		              anular();
		           }
		        }
		      }
		    }
		    else
		    {
		      if(comprobarProductosRestantes())
		      {
		          if (validaImprIP()){   //JCHAVEZ 10.07.2009.n
		              FarmaUtility.showMessage(this,"Este Pedido correponde a un periodo anterior. \nNo se anular� dicho pedido, pero se generar� una solicitud de Nota de Cr�dito.",pObj);
		              DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame,"",true);
		              dlgNotaCreditoNueva.setVisible(true);
		              if(FarmaVariables.vAceptar)
		              {
		                cerrarVentana(true);
		              }
		          }  //JCHAVEZ 10.07.2009.n
		      }
		    }

    }



  private void anularPedido()
  {
    cargarVariables();
    
    int vCantCuponesEmitidosUsados = 0;
    
    /*
    try
    {
      vCantCuponesUsados = DBCaja.cuponesUsados(txtCorrelativo.getText().trim());
      if(vCantCuponesUsados > 0)
      {
        FarmaUtility.showMessage(this,"Los cupones que gener� este pedido han sido usados."+ 
       " Por favor verifique si se debe anular dicho canje.",txtCorrelativo);
      }
    }
    catch (SQLException e)
    {
      log.error(null,e);
    }
    */
    //--Obtiene los cupones emitidos usados
    vCantCuponesEmitidosUsados = 0;//UtilityCaja.getCuponesEmitidosUsados(txtCorrelativo.getText().trim(),
                                   //                                   this,
                                   //                                   pObj);
    
    if(vCantCuponesEmitidosUsados > 0)
            {
              FarmaUtility.showMessage(this,"Los cupones que gener� este pedido han sido usados."+ 
             " Por favor verifique si se debe anular dicho canje.",pObj);
            }
    
    if(comprobarFecha())// || comprobarDocumento())
    {
      if(!comprobarDocumentoNotaCredito())
      {
        FarmaUtility.showMessage(this, "No se puede anular un pedido de tipo Nota de Cr�dito. Verifique!!!",pObj);
      } else 
      {
      
        /**
        * @AUTHOR JCORTEZ
        * @SINCE 10.06.09
        * Se evitara anulara si es que la ip no cuenta con impresora
        * */
        if (validaImprIP()){   
        
          /**
           * Valida pedido Fidelizado
           * @author: JCORTEZ
           * @since: 18/12/2008
           * */
           log.debug("VariablesCaja.vIndPedFidelizado : " +VariablesCaja.vIndPedFidelizado);
           if(VariablesCaja.vIndPedFidelizado.equalsIgnoreCase("S")){
        	   log.info("es un pedido de un cliente fidelizado ...");
           //if(validaExistProd()){
        	   log.debug("aqui verificara si hay linea con matriz...ELLO IMPLICA CUALQUIER CONEXION REMOTA que se tenga se perdera" +
        	   		"por lo que es PROBABLE que hayas problemas al eliminar recargas virtuales que compro un cliente fidelizado");
              /*
              if(ExistLineaMatriz()){//aqui al momento de preguntar por si hay linea con matriz
                  log.info("ANULA PRUEBA anularPedidofidelizadoMatriz");                  
                  if(anularPedidofidelizadoMatriz(FarmaConstants.INDICADOR_N))
                      //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
                        anular(); 
              }else{
                   log.info("ANULA PRUEBA anularPedidofidelizado");                  
                 if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
                  anular();
               }
              */
              //////////
              log.info("DUBILLUZ - 30.11.2009 - ANULA PRUEBA anularPedidofidelizado");
              //if(anularPedidofidelizado(FarmaConstants.INDICADOR_S))
              //JCORTEZ 18.03.10 se valida pedido antes de procesarlo
              if(validaPedido(FarmaConstants.INDICADOR_S)){
                  
                  if(UtilityFactElectronica.isActivoFactElectronica()){

                      FarmaUtility.showMessage(this,
                                               "Se anular� dicho pedido, pero se generar� una solicitud de Nota de Cr�dito.",
                                               pObj);
                      
                      DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame, "", true);
                      //dlgNotaCreditoNueva.setNCParcial(isNCParcial());
                      //dlgNotaCreditoNueva.setListProdNCParcial(productosNCParcial);
                      dlgNotaCreditoNueva.setVisible(true);

                  }
                  else {
                      anular();     
                  }
              }
                 
              
           //}
           }else {
             //No es fidelizado
              log.debug("No es fidelizado");
              ////
                  
                  if(UtilityFactElectronica.isActivoFactElectronica()){

                      FarmaUtility.showMessage(this,
                                               "Se anular� dicho pedido, pero se generar� una solicitud de Nota de Cr�dito.",
                                               pObj);
                      
                      DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame, "", true);
                      //dlgNotaCreditoNueva.setNCParcial(isNCParcial());
                      //dlgNotaCreditoNueva.setListProdNCParcial(productosNCParcial);
                      dlgNotaCreditoNueva.setVisible(true);

                  }
                  else {
                      anular();     
                  }               
               //////
           }
        }
      }
    }
    else
    {
      if(comprobarProductosRestantes())
      {
          if (validaImprIP()){   //JCHAVEZ 10.07.2009.n
              FarmaUtility.showMessage(this,"Este Pedido correponde a un periodo anterior. \nNo se anular� dicho pedido, pero se generar� una solicitud de Nota de Cr�dito.",pObj);
              DlgNotaCreditoNueva dlgNotaCreditoNueva = new DlgNotaCreditoNueva(myParentFrame,"",true);
              dlgNotaCreditoNueva.setVisible(true);
              if(FarmaVariables.vAceptar)
              {
                cerrarVentana(true);
              }
          }  //JCHAVEZ 10.07.2009.n              
      }
    }
  }
  
  private void cargarVariables()
  {
    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();
    VariablesCaja.vTipComp_Anul = "%";
    VariablesCaja.vNumComp_Anul = "%";
    VariablesCaja.vMonto_Anul = txtMonto.getText().trim(); 
    /*VariablesCaja.vTipComp = tblCabeceraPedido.getValueAt(0,7).toString();
    VariablesVentas.vTip_Ped_Vta = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,8);
    VariablesCaja.vIndDeliveryAutomatico = FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,9);*/
    
    log.debug("::::::::::::SE OBTIENE DATOS ESCONDIDOS::::::::::.");
    VariablesCaja.vTipComp=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,7);
        VariablesModuloVentas.vTip_Ped_Vta=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,8);
      VariablesCaja.vIndDeliveryAutomatico=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,9);
    
    //JCORTEZ 18.12.08
    /*
    VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,10);
    VariablesCaja.vDniCli=FarmaUtility.getValueFieldJTable(tblCabeceraPedido,0,11);
    */
    
    VariablesCaja.vIndPedFidelizado= FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,10);
    VariablesCaja.vDniCli=FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,11);
    //
    log.debug("tipo de pedido : " + VariablesModuloVentas.vTip_Ped_Vta);
    log.debug("DUBILLUZ - 30.11.2009:VariablesCaja.vIndPedFidelizado : " + VariablesCaja.vIndPedFidelizado);
  }

  private boolean comprobarFecha()
  {
    boolean retorno=true;
    /* try
    {
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
      String fecha = tblCabeceraPedido.getValueAt(0,1).toString().substring(0,10);
      log.debug(sysdate+"/"+fecha);
      
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy");
      GregorianCalendar calendario1 = new GregorianCalendar();
      //calendario1.setGregorianChange(date1);
      calendario1.setTime(date1);
      
      Date date2 = FarmaUtility.getStringToDate(fecha,"dd/MM/yyyy");
      GregorianCalendar calendario2 = new GregorianCalendar();
      //calendario2.setGregorianChange(date2);
      calendario2.setTime(date2);
      
      if(calendario1.get(Calendar.YEAR)==calendario2.get(Calendar.YEAR)
          && calendario1.get(Calendar.MONTH)==calendario2.get(Calendar.MONTH))
        retorno = true;
      else
        retorno = false;
      
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurri� un error al comprobar la fecha - \n" +e.getMessage(),pObj);
    } */
    return retorno;
  }
  
  private boolean comprobarDocumento()
  {
    boolean retorno = true;
    String documento = tblCabeceraPedido.getValueAt(0,7).toString();
    if(documento.equals(ConstantsModuloVenta.TIPO_COMP_FACTURA))
    {
      retorno = false;
    }
    return retorno;
  }
  
  private boolean comprobarDocumentoNotaCredito()
  {
    boolean retorno = true;
    String documento = FarmaUtility.getValueFieldArrayList(tableModelCabeceraPedido.data,0,7);
    if(documento.equals(ConstantsModuloVenta.TIPO_COMP_NOTA_CREDITO))
    {
      retorno = false;
    }
    return retorno;
  }
  
  
  private boolean comprobarProductosRestantes()
  {
    boolean retorno = true;
    try
    {
      int cant = DBCaja.getProductosRestantes(txtCorrelativo.getText().trim(),"%");

      if(cant == 0)
      {
        retorno = false;
        FarmaUtility.showMessage(this,"Este Pedido corresponde a un periodo anterior. \nSin embargo, no tiene productos pendientes.",pObj);
      }

    }catch(SQLException e)
    {
      retorno = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurri� un error al comprobar productos restantes  - \n" + e.getMessage(),pObj);
    }
    return retorno;
  }

  private boolean cargaLoginAdmLocal()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      //Se validara por el rol dependiendo del tipo de pedido
      //14.11.2007  dubilluz modificacion
      if(VariablesCaja.vIndPedidoRecargaVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
         dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
         log.debug("Pedido Recarga Virtual..Solo el operador podra anular");
      }
      else{
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        log.debug("Pedido normal..");
      }
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurri� un error al validar rol de usuariario \n : " + e.getMessage(),pObj);
    }
    return FarmaVariables.vAceptar;
  }

/**
  * Obtiene el tiempo maximo para la anulacion de un pedido recarga virtual
  * @author dubilluz
  * @since  09.11.2007
  */
  private String time_max(String pNum_ped)
  {
    String valor = "";
    String num_pedido = pNum_ped;
    try
      {
         valor = DBCaja.getTimeMaxAnulacion(num_pedido);
      
      }catch(SQLException e)
      {
        log.error("",e);
        FarmaUtility.showMessage(this,"Ocurri� un error al obtener tiempo maximo de anulacion de Producto Recarga Virtual.\n" + e.getMessage(),pObj);
      }
     return valor; 
  }
    
   /**
    * Metodo que sirve para validar que la recarga se realizo con exito.
    * @Author DVELIZ
    * @Since 15.12.2008
    * @param pCadena
    * @param pParent
    */
    public String obtieneRespuestaRecarga()
   {
        String retorno = FarmaConstants.INDICADOR_N;
        try
        {
            retorno = DBCaja.obtCodigoRptaProdVirtual();
        }
        catch(SQLException e)
        {
            log.error("",e);
        }
        return retorno;
    }

    private void obtieneInfoPedidoVirtual()
    {
      try
      {
        DBCaja.obtieneInfoPedidoVirtual(VariablesVirtual.vArrayList_InfoProdVirtual,
                                        txtCorrelativo.getText().trim());
        log.debug("vArrayList_InfoProdVirtual : " + VariablesVirtual.vArrayList_InfoProdVirtual);
        //Agregado por DVELIZ 15.12.2008
        if(VariablesVirtual.vArrayList_InfoProdVirtual.size() > 0){
            colocaInfoPedidoVirtual();
        }
      } catch(SQLException sql)
      {
        VariablesVirtual.vArrayList_InfoProdVirtual.clear();
        log.error("",sql);
        FarmaUtility.showMessage(this, "Error al obtener informacion de pedido virtual - \n" + sql.getMessage(), pObj);
      }
    }
    
    private void colocaInfoPedidoVirtual()
    {
        VariablesCaja.vTipoProdVirtual = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 1);
        VariablesCaja.vPrecioProdVirtual = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 2);
        VariablesCaja.vNumeroCelular = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 3);
        VariablesCaja.vCodigoProv = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 4);
        VariablesCaja.vNumeroTraceOriginal = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 5);
        VariablesCaja.vCodAprobacionOriginal = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 6);
        /* 27.09.2007 ERIOS Datos necesarios para Bprepaid */
        VariablesCaja.vFechaTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 8);
        VariablesCaja.vHoraTX = FarmaUtility.getValueFieldArrayList(VariablesVirtual.vArrayList_InfoProdVirtual, 0, 9);
    }
    
    /**
     * Se sigue la secuencia de anulacion
     * */
    private void anular()
    {
        if(validarMostrarCorrelativo())
            FarmaUtility.moveFocus(txtCorrelativo);  
        else 
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        
        DlgDetalleAnularPedido dlgDetalleAnularPedido = new DlgDetalleAnularPedido(myParentFrame,"",true);
        dlgDetalleAnularPedido.setVisible(true);
        
        log.debug("FarmaVariables.vAceptar="+FarmaVariables.vAceptar);
        if(FarmaVariables.vAceptar){
          tableModelCabeceraPedido.clearTable();
          tableModelDetallePedido.clearTable();
          
          tblCabeceraPedido.removeAll();
          tblDetallePedido.removeAll();
          
          txtCorrelativo.setText("");
          txtMonto.setText("");
          log.debug("ok");
          
          //JCORTEZ 16.06.09 imprime mensaje para recoger comprobante de anulacion
          /* if(VariablesCaja.vTipComp.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){
              if(!VariablesCaja.vDescripImpr.equalsIgnoreCase("X")||VariablesCaja.vDescripImpr.equalsIgnoreCase(""))
              lblMensaje.setText("Recoger comprobante en : "+VariablesCaja.vDescripImpr);
           }*/
          
        }         
    }

       /**
        * Valida que exista productos canje y regalos en pedido
        * @author: JCORTEZ
        * @since: 18.12.08
        * */
    private boolean validaExistProd(){
    
    boolean result=false;
       String ind;
       try {
          ind = DBCaja.VerificaProdFidel(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli);
          if(ind.equalsIgnoreCase("S"))
              result=true;
        } catch(SQLException ex)
        {
          log.error("",ex);
          FarmaUtility.showMessage(this,"Error al validar productos.\n" + ex.getMessage(), pObj);
        }
        //DBCaja.anularPedido(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vTipComp_Anul,VariablesCaja.vNumComp_Anul,VariablesCaja.vMonto_Anul,tblUsuariosCaja.getValueAt(tblUsuariosCaja.getSelectedRow(),2).toString(),VariablesCaja.vMotivoAnulacion);
         return result;
    }
    
    /**
    * Verifica si existe linea con PtoventaMatriz
    * @author: JCORTEZ
    * @since: 18.12.08
    * */
    private boolean ExistLineaMatriz(){
    
       VariablesCaja.vIndLineaPtoventaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                     FarmaConstants.INDICADOR_N);
        if(VariablesCaja.vIndLineaPtoventaMatriz.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
          log.debug("Existe linea con PtoVenta Matriz ...");
          VariablesCaja.vIndLineaPtoventaMatriz = FarmaConstants.INDICADOR_S;
           return true;// cambiar
        }else
        return false;
    }
    
    /**
    * Verifica existe productos canje
    * @author: JCORTEZ
    * @since: 18.12.08
    * */
    private boolean existProdCanje(){
    int cant=0;
     boolean result=false;
       try {
          cant = DBCaja.ExistProdCanje(VariablesCaja.vNumPedVta_Anul);
          if(cant>0)  result= true;
          else result= false;
        } catch(SQLException ex)
        {
          log.error("",ex);
          FarmaUtility.showMessage(this,"Error al validar productos canje en pedido.\n" + ex.getMessage(), pObj);
        }
        return result;
    }
    
    /**
     * Se valida pedido antes de procesar anulacion
     * @SUTHOR JCORTEZ
     * @SINCE 18.03.10
     * */
    private boolean validaPedido(String IndLocal){
        
        boolean result=false;
            try {
                log.info(":::::::::::::::::::::::VALIDANDO PEDIDO FIDELIZADO:::::::::::::::::::::");
               DBCaja.anulaPedidoFidelizado(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal);
                 result=true;
             } catch(SQLException ex){ 
                 FarmaUtility.liberarTransaccion();
                if(ex.getErrorCode() == 20004){ 
                  FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
                }else if(ex.getErrorCode() == 20006){ 
                 FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
                }else if(ex.getErrorCode() == 20007){ 
                 FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
                }else{
                    log.error("",ex); 
                    FarmaUtility.showMessage(this,"Error al validar pedido fidelizado.\n" + ex.getMessage(), pObj);
                }
             }
             return result;
    }
    
    /**
     * se anula el pedido en Local
     * @author: JCORTEZ
     * @since: 18.12.08
     * */
    private boolean anularPedidofidelizado(String IndLocal){
    boolean result=false;
        try {
           DBCaja.anulaPedidoFidelizado(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal);
             result=true;
         } catch(SQLException ex){ 
             FarmaUtility.liberarTransaccion();
            if(ex.getErrorCode() == 20001){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que no hay canjes relacionados.\n",pObj);
            }else if(ex.getErrorCode() == 20004){ 
              FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
            }else if(ex.getErrorCode() == 20005){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de su propio canje.\n",pObj);
            }else if(ex.getErrorCode() == 20006){ 
             FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
            }else if(ex.getErrorCode() == 20007){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
            }else{
            log.error("",ex); 
            FarmaUtility.showMessage(this,"Error al anular pedido.\n" + ex.getMessage(), pObj);
            }
         }
         return result;
    }
    
    
    /**
     * Se anula el pedido en Matriz
     * @author: JCORTEZ
     * @since: 18.12.08
     * */
    private boolean anularPedidofidelizadoMatriz(String IndLocal){
    boolean result=false;
        try {
           DBCaja.anulaPedidoFidelizadoMatriz(VariablesCaja.vNumPedVta_Anul,VariablesCaja.vDniCli,IndLocal,FarmaConstants.INDICADOR_N);
             result=true;
             VariablesCaja.vIndCommitRemotaAnul=result;
         } catch(SQLException ex){ 
            VariablesCaja.vIndCommitRemotaAnul=result;
            if(ex.getErrorCode() == 20001){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que no hay canjes relacionados.\n",pObj);
            }else if(ex.getErrorCode() == 20004){ 
              log.debug("Error en PtoVenta Matriz ..."+VariablesCaja.vNumPedVta_Anul+" "+" "+VariablesCaja.vDniCli);
              FarmaUtility.showMessage(this,"Error determinar tipo de pedido fidelizado. Posiblemente no existe pedido.\n",pObj);
            }else if(ex.getErrorCode() == 20005){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de su propio canje.\n",pObj);
            }else if(ex.getErrorCode() == 20006){ 
             FarmaUtility.showMessage(this,"No se puede anular, ya que hay canjes asociados.\n",pObj);
            }else if(ex.getErrorCode() == 20007){ 
             FarmaUtility.showMessage(this,"No se puede anular el pedido, ya que es parte de un canje.\n",pObj);
            }else{
            log.error("",ex); 
            FarmaUtility.showMessage(this,"Error al anular pedido.\n" + ex.getMessage(), pObj);
            }
         }
         return result;
    }

/**
 * @author: DVELIZ
 * @since:  29/12/2008
 */
   /* private void mostrarMensajeRecargaExito() {
        String vRetorno = "";
        validarConexionADMCentral();
        if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_S)){
            try{
                vRetorno = DBCaja.getIndicadorRecargaExito();
                if (vRetorno.equals("1")){
                    if(!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, "La recarga se realizo con exito, desea continuar?")){
                        
                    }
                }
            }catch(SQLException e){
                log.error("",e);
            }
        }
    }*/
    
    private void evaluaPedidoProdVirtual(String pNumPedido)
    {
        int cantProdVirtualesPed = 0;
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if( cantProdVirtualesPed <= 0 )
        {
            VariablesVirtual.vConProductoVirtual = false;
        }
        else
        {
            VariablesVirtual.vConProductoVirtual = true;
        }
        log.info("jcallo: VariablesVirtual.vConProductoVirtual :"+VariablesVirtual.vConProductoVirtual);
    }
    
    private int cantidadProductosVirtualesPedido(String pNumPedido)
    {
      int cant = 0;
      try
      {
        cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
      } catch(SQLException ex)
      {
        log.error("",ex);
        cant = 0;
        FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), pObj);
      }
      return cant;
    }

    /**
     * Valida si se muestra la nueva versi�n para Imprimir o 
     * no Imprimir Correlativo, as� como usar pantalla para Ingresar Numero Comprobante y Monto Neto si es Negativo
     * @author JMIRANDA
     * @since 22.08.2011
     * @return true si imprime correlativo
     */
    private boolean validarMostrarCorrelativo()
    {
        boolean flag = true;
        //si la validacion siguiente es falsa no imprime y debe ingresar nro comprobante
        if(!UtilityModuloVenta.getIndImprimeCorrelativo())
        {
            txtCorrelativo.setEnabled(false);
            txtMonto.setEnabled(false);
            flag = false;
        }
        return flag;
    }
  
    private void mostrarCorrelativoXComprobante()
    {
        DlgConsultaXCorrelativo dlgConsulta = new DlgConsultaXCorrelativo(myParentFrame,"",true);
        dlgConsulta.setVisible(true);
        
        if(FarmaVariables.vAceptar)
        {
            txtCorrelativo.setText(VariablesModuloVentas.vNumPedVta_new);
            txtMonto.setText(VariablesModuloVentas.vMontoNeto_new);
            btnBuscar.doClick();
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
        }
        else
            FarmaUtility.moveFocusJTable(tblCabeceraPedido);
    }
    
    //JMIRANDA 25.08.2011 Setear el Objeto para enfocar despu�s de los mensajes.
    private void setearObjetoFocus(){
        //JMIRANDA 25.08.2011 verificar si se utiliza funcionalidad nueva
               if(validarMostrarCorrelativo())
                   pObj = txtCorrelativo;
               else 
                   pObj = tblCabeceraPedido;
    }
    
    //LLEIVA 27-Mar-2013
    private boolean validarRecargas()
    {
        try
        {   boolean retorno = false;
            UtilityRecargaVirtual urv = new UtilityRecargaVirtual();
            String resp = urv.obtieneMensajeRecargaAnul(txtCorrelativo.getText().trim(), VariablesModuloVentas.vFechaPedVta_new);
            if(resp!=null)                    
               log.info("LLEIVA : obtieneMensajeRecargaAnul : "+resp);
            else
               resp = "ERROR OBTENER MENSAJE RECARGA";
            
            if(!"S".equalsIgnoreCase(resp))
            {
                retorno = false;
                String[] pCadena = resp.split("@");
                String pMsj = "";
                for(int i=0;i<pCadena.length;i++){
                    pMsj = pMsj + pCadena[i] + "\n";
                }
                
                FarmaUtility.showMessage(this, pMsj, pObj);
            }
            else
            {
                retorno = true;
            }
            return retorno;
        }
        catch(Exception e)
        {   log.info("", e);
            FarmaUtility.showMessage(this,
                                    "ERROR BD: No se pudo validar la recarga en la central",
                                    pObj);
            return false;
        }
    }
}
