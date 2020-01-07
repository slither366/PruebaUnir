package venta.inventario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import common.FarmaConnectionRemoto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.UtilityPtoVenta;

public class DlgTransferenciasRealizadas extends JDialog 
{
  /* ********************************************************************** */
	/*                        DECLARACION PROPIEDADES                         */
	/* ********************************************************************** */
private static final Logger log = LoggerFactory.getLogger(DlgTransferenciasRealizadas.class);
  Frame myParentFrame;
  FarmaTableModel tableModel;
  private String filtro;
  private String CodOrigenTranf;
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JScrollPane scrListaTransferencias = new JScrollPane();
  private JTable tblListaTransferencias = new JTable();
  private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF2 = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();
  private JLabelFunction lblF8 = new JLabelFunction();
  private JButtonLabel lblCantTransfereniasT = new JButtonLabel();
  private JButtonLabel lblCantTransferencias = new JButtonLabel();
  private JLabelFunction lblF6 = new JLabelFunction();
  private JLabelFunction lblF7 = new JLabelFunction();

  /* ********************************************************************** */
	/*                        CONSTRUCTORES                                   */
	/* ********************************************************************** */

  public DlgTransferenciasRealizadas()
  {
    this(null, "", false);
  }

  public DlgTransferenciasRealizadas(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(1072, 445));
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Relacion de Transferencias");
    this.setDefaultCloseOperation(0);
   // this.setBounds(new Rectangle(10, 10, 790, 394));
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
    pnlTitle1.setBounds(new Rectangle(10, 10, 1015, 25));
    scrListaTransferencias.setBounds(new Rectangle(10, 35, 1015, 260));
    tblListaTransferencias.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaTransferencias_keyPressed(e);
        }
      });
    btnRelacionTransferencias.setText("Relacion de Transferencias");
    btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 165, 15));
    btnRelacionTransferencias.setMnemonic('R');
    btnRelacionTransferencias.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRelacionTransferencias_actionPerformed(e);
        }
      });
    lblEsc.setText("[ ESC ] Cerrar");
    lblEsc.setBounds(new Rectangle(690, 340, 90, 20));
    lblF2.setText("[ F2 ] Ver Transferencia");
    lblF2.setBounds(new Rectangle(185, 310, 155, 20));
    lblF1.setText("[ F1 ] Nueva Transferencia");
    lblF1.setBounds(new Rectangle(15, 310, 160, 20));
    lblF8.setText("[ F8 ] Por Confirmar");
    lblF8.setBounds(new Rectangle(610, 310, 155, 20));
    lblCantTransfereniasT.setBounds(new Rectangle(715, 5, 45, 15));
    lblCantTransferencias.setText("Cantidad de Transferencias");
    lblCantTransferencias.setBounds(new Rectangle(545, 5, 160, 15));
    lblF6.setBounds(new Rectangle(350, 310, 117, 19));
    lblF6.setText("[ F6 ] Filtrar");
    lblF7.setBounds(new Rectangle(480, 310, 117, 19));
    lblF7.setText("[ F7 ] Quitar Filtro");
    scrListaTransferencias.getViewport().add(tblListaTransferencias, null);
    jContentPane.add(lblF7, null);
    jContentPane.add(lblF6, null);
    jContentPane.add(lblF8, null);
    jContentPane.add(lblF1, null);
    jContentPane.add(lblF2, null);
    jContentPane.add(lblEsc, null);
    jContentPane.add(scrListaTransferencias, null);
    pnlTitle1.add(lblCantTransferencias, null);
    pnlTitle1.add(lblCantTransfereniasT, null);
    pnlTitle1.add(btnRelacionTransferencias, null);
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
  }
  
  /* ************************************************************************ */
	/*                            METODOS INICIALIZACION                        */
	/* ************************************************************************ */

  private void initTable()
  {
    tableModel = new FarmaTableModel(ConstantsInventario.columnsListaTransferenciasRealizadas,ConstantsInventario.defaultValuesListaTransferenciasRealizadas,0);
    FarmaUtility.initSimpleList(tblListaTransferencias,tableModel,ConstantsInventario.columnsListaTransferenciasRealizadas);
    filtro = "%";
    cargaListaTransferencias();
  }
  
  private void cargaListaTransferencias()
  {
    try
    {
      DBInventario.cargaListaTransferencias(tableModel,filtro);
      lblCantTransfereniasT.setText(""+tblListaTransferencias.getRowCount());
      FarmaUtility.ordenar(tblListaTransferencias,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
    }catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurri� un error al listar las transferencias: \n" + sql.getMessage(),btnRelacionTransferencias);
    }
  }
  
  /* ************************************************************************ */
	/*                            METODOS DE EVENTOS                            */
	/* ************************************************************************ */

  private void btnRelacionTransferencias_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblListaTransferencias);
  }

  private void tblListaTransferencias_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    FarmaUtility.moveFocus(tblListaTransferencias);  
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
  }
  /* ************************************************************************ */
	/*                     METODOS AUXILIARES DE EVENTOS                        */
	/* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaTransferencias,null,0);
    
    if(UtilityPtoVenta.verificaVK_F1(e))
    {
      if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionTransferencias);
      else
      {
          
      VariablesInventario.vArrayTransferenciaProductos = new ArrayList();
      VariablesInventario.vHistoricoLote = true;
      DlgTransferenciasNueva dlgTransferenciasNueva = new DlgTransferenciasNueva(myParentFrame,"",true);
      dlgTransferenciasNueva.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
              //JCORTEZ 28.10.09 Se confirmara automaticamente si es que se generaron e imprimieron guias.
               log.debug(":JCORTEZ:::::vTipoDestino_Transf::::::"+VariablesInventario.vTipoDestino_Transf);
              if(VariablesInventario.vTipoDestino_Transf.equalsIgnoreCase("01")){//solo confirmacion automatica para locales
                  if(validaGuiasEmitidas()){
                      log.debug(":JCORTEZ:::::Confirmando transferencia::::"+VariablesInventario.vNumNotaEs);
                        confirmaTransferencia();
                  }
              }/*else{
                  FarmaUtility.showMessage(this,"No se pudo confirmar la transferencia, Favor de verificar!!!",null);
              }*/
              
            cargaListaTransferencias();
            FarmaVariables.vAceptar = false;
          }
      }
      }else{
         FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opci�n",null);
      }
    }else if(UtilityPtoVenta.verificaVK_F2(e))
    {
      VariablesInventario.vPos = tblListaTransferencias.getSelectedRow();
      VariablesInventario.vNumNota = tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(),0).toString();
      VariablesInventario.vEstadoNota = tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(),4).toString();
      VariablesInventario.vTipoNota = tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(),9).toString();
      VariablesInventario.vTipoNotaOrigen = tblListaTransferencias.getValueAt(tblListaTransferencias.getSelectedRow(),10).toString();
      DlgTransferenciasVer dlgTransferenciasVer = new DlgTransferenciasVer(myParentFrame,"",true);
      dlgTransferenciasVer.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        cargaListaTransferencias();
        FarmaVariables.vAceptar = false;
        FarmaGridUtils.showCell(tblListaTransferencias,VariablesInventario.vPos,VariablesInventario.vPos);
        //tblListaTransferencias.repaint();
      }
    }
    else if(e.getKeyCode() == KeyEvent.VK_F6)
    {
      filtrar();
    }
    else if(e.getKeyCode() == KeyEvent.VK_F7)
    {
      quitarFiltro();
    }
    else if(e.getKeyCode() == KeyEvent.VK_F8)
    {
      if(FarmaVariables.vEconoFar_Matriz)
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionTransferencias);
      else
        funcionF8();
    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
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

  private void funcionF8()
  {
    DlgTransferenciasPorConfirmar dlgTransferenciasPorConfirmar = new DlgTransferenciasPorConfirmar(myParentFrame,"",true);
    dlgTransferenciasPorConfirmar.setVisible(true);
    if(FarmaVariables.vAceptar)
    {
      cargaListaTransferencias();
      FarmaVariables.vAceptar = false;
    }
  }


  private void filtrar()
  {
    if(tblListaTransferencias.getRowCount()>0)
    {
      VariablesInventario.vTipoNota = ConstantsPtoVenta.TIP_NOTA_SALIDA;
      VariablesInventario.vNomInHashtableGuias = ConstantsInventario.NOM_HASTABLE_CMBFILTRO_TRANSF;
      DlgFiltroGuias dlgFiltroGuias = new DlgFiltroGuias(myParentFrame,"", true);      
      dlgFiltroGuias.setVisible(true);
      if(FarmaVariables.vAceptar)
      {
        filtro = VariablesInventario.vCodFiltro;
        cargaListaTransferencias();
        FarmaVariables.vAceptar = false;
      }
    }
  }
  
  private void quitarFiltro()
  {
    filtro = "%";
    cargaListaTransferencias();
  }

    /**
     * @AUTHOR JCORTEZ
     * @SINCE 28.10.09
     * */
   private boolean  validaGuiasEmitidas(){
   
   boolean valor=false;
   String ind="";
   String numTransf="";
     int fila = tblListaTransferencias.getSelectedRow();
       String[] val;
      
   log.debug(":JCORTEZ:::::Confirmando Guias generadas e impresas::::"+numTransf);
    try{
       if(fila > -1){
        numTransf = tblListaTransferencias.getModel().getValueAt(fila,0).toString();
        ind=DBInventario.validaGuiasTransf(numTransf);//si existe guias
        
         log.debug(":JCORTEZ:::ind:::"+ind);
         val = ind.split("�");
           log.debug(":JCORTEZ:::val:::"+val);
           
        if(val[0].trim().equalsIgnoreCase("S"))
          valor=true;
        else
          valor=false;
          
           CodOrigenTranf=val[1].trim(); //local origen
            log.debug(":JCORTEZ:::CodOrigenTranf:::"+CodOrigenTranf);
       }
       }catch(SQLException e) {
           FarmaUtility.liberarTransaccion();
           valor=false;
           
           if(e.getErrorCode()==20101){
             FarmaUtility.showMessage(this,"NO SE PUEDE CONFIRMAR LA TRANSFERENCIA. EXISTEN GUIAS SIN IMPRIMIR. VERIFIQUE!!!",
                    tblListaTransferencias);
           }else if(e.getErrorCode()==20102){
              FarmaUtility.showMessage(this,"LA TRANSFERENCIA NO HA GENERADO GUIAS. VERIFIQUE!!!",tblListaTransferencias);
           }else {
               log.error("",e);
               FarmaUtility.showMessage(this,"Ha ocurrido un error al confirmar.\n"+e,btnRelacionTransferencias);
           }
       }
       
       return valor;
   }
   
    private void confirmaTransferencia() {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
                    int fila = tblListaTransferencias.getSelectedRow();
                    if(fila > -1){
                          try {
                                  //actualizar a estado confirmado el pedido de transferencia
                                  String numTransf =  VariablesInventario.vNumNotaEs.trim();
                                  String codLocalDestino =VariablesInventario.vCodDestino_Transf;// tblListaTransferencias.getModel().getValueAt(fila,7).toString().trim();
                                  String tipoOrigenTransf = tblListaTransferencias.getModel().getValueAt(fila,10).toString().trim();
                                  log.debug("tipoOrigenTransf:"+tipoOrigenTransf+"***");
                                  log.debug("codLocalDestino:"+codLocalDestino+"***");
                                  log.debug("numTransf:"+numTransf+"***");
                                  log.debug("VariablesInventario.vCodDestino_Transf:"+VariablesInventario.vCodDestino_Transf);
                                  
                                  DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf,"I","N");//JCHAVEZ 10122009 graba inicio de confimacion de transferencia en local origen
                                  DBInventario.confirmarTransferencia(numTransf);
                                  DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf,"F","N");//JCHAVEZ 10122009 graba fin de confimacion de transferencia en local origen
                              
                                  FarmaUtility.aceptarTransaccion();
                                  
                                  if(tipoOrigenTransf.equals("01")){//si es TIPO LOCAL
                                          log.debug("verificando si hay linea con matriz");                               
                                          VariablesInventario.vIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                                          log.debug("VariablesInventario.vIndLineaMatriz:"+VariablesInventario.vIndLineaMatriz);
                                          //si hay linea con matriz, se intentara realizar la trasnferencia a matriz y local destino.
                                          //si ocurriera algun error, se realizara solo la confirmacion en local origen
                                          if ( VariablesInventario.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)){
                                                  log.debug("tratando de realizar la transferencia a local destino y matriz con estado L");                                               
                                                  DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf,"I","S");//JCHAVEZ 10122009 graba inicio de confimacion remota de transferencia de local origen a matriz
                                                  String resultado = DBInventario.realizarTransfDestino(  numTransf, 
                                                                                                                                                                  codLocalDestino, 
                                                                                                                                                                  FarmaConstants.INDICADOR_N).trim();
                                                 DBInventario.grabaInicioFinConfirmacionTransferencia(numTransf,"F","S");//JCHAVEZ 10122009 graba fin de confimacion remota de transferencia de local origen a matriz
                                                  log.debug("despues de invocar a matriz RESPUESTA:"+resultado);
                                                  if(resultado.equals(FarmaConstants.INDICADOR_S)){                                                       
                                                          FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                                                                                                    FarmaConstants.INDICADOR_S);
                                                          log.debug("actualizando el estado de la transferencia en local");
                                                          DBInventario.actualizarEstadoTransf(numTransf, "L");
                                                          FarmaUtility.aceptarTransaccion();
                                                  }else{
                                                          FarmaUtility.liberarTransaccionRemota(  FarmaConstants.CONECTION_MATRIZ,
                                                                                                                                          FarmaConstants.INDICADOR_S);
                                                  }
                                                  log.debug("cerrando toda conexion remota");
                                                  FarmaConnectionRemoto.closeConnection();
                                          }
                                  }
                              FarmaUtility.showMessage(this,"Transferencia Confirmada.",btnRelacionTransferencias);
                              FarmaUtility.moveFocus(tblListaTransferencias);
                              //cerrarVentana(true);
                          }catch(SQLException e) {
                              FarmaUtility.liberarTransaccion();
                              log.error("",e);
                              FarmaUtility.showMessage(this,"Ha ocurrido un error al confirmar. Transferencia por confirmar\n"+e,btnRelacionTransferencias);
                          }
                    }
            }else{
                    FarmaUtility.showMessage(this,"No posee privilegios suficientes para acceder a esta opci�n",null);
            }  
    }

}