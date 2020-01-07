package venta;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.lang.reflect.Field;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.UIManager;

import javax.swing.UnsupportedLookAndFeelException;

import venta.adm.productos.DlgListaMaeProductos;

import common.DlgLogin;
import common.FarmaConnection;
import common.FarmaConstants;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.hospital.soat.DlgListaAtenciones;

import venta.hospital.soat.DlgListaLiquidaciones;
import venta.hospital.soat.DlgListaRegistroAtencion;

import venta.administracion.DlgMovimientosCaja;
import venta.administracion.DlgProcesaViajero;
import venta.administracion.cajas.DlgListaCajas;
import venta.administracion.fondoSencillo.DlgListadoCajeros;
import venta.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import venta.administracion.impresoras.DlgListaIPSImpresora;
import venta.administracion.impresoras.DlgListaImpresoraTermCreaMod;
import venta.administracion.impresoras.DlgListaImpresoras;
import venta.administracion.mantenimiento.DlgControlHoras;
import venta.administracion.mantenimiento.DlgParametros;
import venta.administracion.otros.DlgListaProbisa;
import venta.administracion.producto.DlgPrecioProdCambiados;
import venta.administracion.producto.DlgProdImpresion;
import venta.administracion.usuarios.DlgBuscaTrabajadorLocal;
import venta.administracion.usuarios.DlgCambioClave;
import venta.administracion.usuarios.DlgListaUsuarios;
import venta.caja.DlgAnularPedido;
import venta.caja.DlgAnularPedidoComprobante;
import venta.caja.DlgConfiguracionComprobante;
import venta.caja.DlgControlSobres;
import venta.caja.DlgDatosTarjetaPinpad;
import venta.caja.DlgFormaPago;
import venta.caja.DlgListaRemito;
import venta.caja.DlgListaTicketsAnulados;
import venta.caja.DlgMovCaja;
import venta.caja.DlgPedidosPendientesImpresion;
import venta.caja.DlgPruebaImpresora;
import venta.caja.DlgVerificacionComprobantes;
import venta.caja.reference.ConstantsCaja;
import venta.caja.reference.DBCaja;
import venta.caja.reference.FacadeCaja;
import venta.caja.reference.UtilityCaja;
import venta.caja.reference.VariablesCaja;
import venta.ce.DlgCierreCajaTurno;
import venta.ce.DlgHistoricoCierreDia;
import venta.ce.DlgReciboPagoSencillo;
import venta.ce.reference.ConstantsCajaElectronica;
import venta.cliente.DlgBuscaClienteJuridico;
import venta.cliente.reference.VariablesCliente;
import venta.controlingreso.DlgControlIngreso;
import venta.controlingreso.DlgHistoricoTemperatura;
import venta.delivery.DlgUltimosPedidos;
import venta.hilos.SubProcesoFarmaVenta;
import venta.hilos.SubProcesos;
import venta.inventario.DlgAjustesporFecha;
import venta.inventario.DlgDevolucionesLista;
import venta.inventario.DlgGuiasIngresosRecibidas;
import venta.inventario.DlgGuiasRemision;
import venta.inventario.DlgKardex;
import venta.inventario.DlgListaPedidosEspeciales;
import venta.inventario.DlgListadoGuias;
import venta.inventario.DlgMercaderiaDirectaBuscar;
import venta.inventario.DlgPedidoReposicionAdicionalNuevo;
import venta.inventario.DlgPedidoReposicionRealizados;
import venta.inventario.DlgRecepcionProductosGuias;
import venta.inventario.DlgTransferenciasLocal;
import venta.inventario.DlgTransferenciasRealizadas;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.UtilityInventario;
import venta.inventario.transfDelivery.DlgListaTransfPendientes;
import venta.inventariociclico.DlgInicioInvCiclico;
import venta.inventariociclico.DlgListaTomasInventarioCiclico;
import venta.inventariodiario.DlgInicioInveDiario;
import venta.inventariodiario.DlgListaDiferenciasToma;
import venta.inventariodiario.DlgListaTomasInventarioDiario;
import venta.inventariodiario.DlgPedidoPendienteDiario;
import venta.otros.DlgAcercaDe;
import venta.otros.DlgListaMedidaPresion;
import venta.pinpad.DlgCierrePinpad;
import venta.pinpad.DlgEleccionTranAnularPinpad;
import venta.pinpad.DlgListadoPedidoPinpad;
import venta.pinpad.DlgReimpresionLotePinpad;
import venta.pinpad.DlgReimpresionPinpad;
import venta.pinpad.DlgReporteDetalladoPinpad;
import venta.pinpad.DlgReporteTotalPinpad;
import venta.pinpad.DlgTransaccionesPinpad;
import venta.psicotropicos.DlgReportePsicotropicos;
import venta.recaudacion.DlgRecaudacionCitibank;
import venta.recaudacion.DlgRecaudacionClaro;
import venta.recaudacion.DlgRecaudacionCmr;
import venta.recaudacion.DlgRecaudacionPrestamosCitibank;
import venta.recaudacion.DlgRecaudacionRipley;
import venta.recaudacion.DlgRecaudacionesRealizadas;
import venta.recaudacion.reference.ConstantsRecaudacion;
import venta.recaudacion.reference.FacadeRecaudacion;
import venta.recepcionCiega.DlgHistoricoRecepcion;
import venta.recepcionCiega.DlgListaTransportistas;
import venta.recepcionCiega.reference.UtilityRecepCiega;
import venta.recetario.DlgListaRecetarios;
import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.reportes.DlgDetalleVentas;
import venta.reportes.DlgProdSinVtaNDias;
import venta.reportes.DlgProductoFaltaCero;
import venta.reportes.DlgProductosABC;
import venta.reportes.DlgRegistroVentas;
import venta.reportes.DlgRptNuevo_01;
import venta.reportes.DlgReporteGuia;
import venta.reportes.DlgRptFPagoVendedor;
import venta.reportes.DlgUnidVtaLocal;
import venta.reportes.DlgVentasDiaMes;
import venta.reportes.DlgVentasPorHora;
import venta.reportes.DlgVentasPorProducto;
import venta.reportes.DlgVentasPorVendedor;
import venta.reportes.DlgVentasResumenPorDia;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.VariablesReporte;
import venta.tomainventario.DlgInicioToma;
import venta.tomainventario.DlgListaHistoricoTomas;
import venta.tomainventario.DlgListaLaboratorios;
import venta.tomainventario.DlgListaTomasInventario;
import venta.modulo_venta.DlgConsultarRecargaCorrelativo_AS;
import venta.modulo_venta.DlgListaProdDIGEMID;
import venta.modulo_venta.DlgResumenPedido;
import venta.modulo_venta.DlgResumenPedidoGratuito;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;
import venta.stocknegativo.DlgListadoStockNegativo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FrmEconoFar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * ERIOS       20.06.2013   Modificacion<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class FrmAdministracion extends JFrame  {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(FrmAdministracion.class);
    
    //public static String tituloBaseFrame=  "SVB - Venta-13.03.2015";
    public static String tituloBaseFrame=  "SVB - Venta-01.09.2015";
    int ind=0; 
    JLabel statusBar = new JLabel();
    
    private JPanel pnlEconoFar = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel lblLogo = new JLabel();
    private JButtonLabel btnRevertir = new JButtonLabel();
    private JTextFieldSanSerif txtRevertir = new JTextFieldSanSerif();
    private JLabel lblMensaje = new JLabel("EN PRUEBAS",JLabel.CENTER);    
    
    private JMenuBar mnuPtoVenta = new JMenuBar();
    
    private JMenu mnuEconoFar_Ventas = new JMenu();


    //01.10.2013 CVILCA


    //09.09.2013 wvillagomez


    private JMenu mnuEconoFar_Salir = new JMenu();
    private JMenuItem mnuSalir_SalirSistema = new JMenuItem();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public FrmAdministracion() {

        try {
            cargaVariablesBD();
            jbInit();
            /*menuRptNuevos.setVisible(false);
            resVendedor.setVisible(false);
            resFormaPago.setVisible(false);
            menuRptNuevos.setEnabled(false);
            resVendedor.setEnabled(false);
            resFormaPago.setEnabled(false);*/
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void cargaVariablesBD()
    {
      FarmaVariables.vPUERTO = ConstantsPtoVenta.PUERTO;
          try {
              (new FacadeRecaudacion()).obtenerTipoCambio();
          } catch (SQLException sql) {
              log.error("", sql);
              FarmaUtility.showMessage(this, 
                                       "Error al obtener Tipo de Cambio del Dia . \n " + 
                                       sql.getMessage(), null);
          }
    }
      
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void salir(WindowEvent e)
    {
      salirSistema();
    }
    private void this_windowClosed(WindowEvent e) {
     
    }
    private void jbInit() throws Exception {
        
        this.getContentPane().setLayout(borderLayout1);
        
        this.setSize(new Dimension(800, 600));
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
                            salir(e);
                        }

                        public void windowClosed(WindowEvent e) {
                            this_windowClosed(e);
                        }
                    });
    
        pnlEconoFar.setLayout(null);
        pnlEconoFar.setFont(new Font("SansSerif", 0, 11));
        pnlEconoFar.setBackground(Color.white);    
        
        mnuPtoVenta.setFont(new Font("SansSerif", 0, 11));
        this.setJMenuBar(mnuPtoVenta);
        
        // MODULO VENTAS
        mnuEconoFar_Ventas.setText("Mantenimientos");
        mnuEconoFar_Ventas.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_Ventas.setMnemonic('v');
        jMenu1.add(jMenuItem1);
        mnuEconoFar_Ventas.add(jMenu1);
        mnuPtoVenta.add(mnuEconoFar_Ventas);

        // SALIR
        mnuEconoFar_Salir.setText("Salir");
        mnuEconoFar_Salir.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_Salir.setMnemonic('s');
        mnuPtoVenta.add(mnuEconoFar_Salir);
        mnuSalir_SalirSistema.setText("Salir del Sistema");
        mnuSalir_SalirSistema.setFont(new Font("SansSerif", 0, 11));
        mnuSalir_SalirSistema.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuSalir_SalirSistema_actionPerformed(e);
                }
            });
        jMenu1.setText("Productos");
        jMenuItem1.setText("Precios Venta");
        jMenuItem1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem1_actionPerformed(e);
                }
            });
        mnuEconoFar_Salir.add(mnuSalir_SalirSistema);
        statusBar.setText("Copyright (c) 2014 - 2020");
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.getContentPane().add(pnlEconoFar, BorderLayout.CENTER);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        readFileFarmaVentaProperties();
        cargaIcono();
        muestraLogin();
        muestraUser();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */
    
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e)
    {
        if(!FarmaVariables.vIdUsu.trim().toUpperCase().equalsIgnoreCase("CSAYAN")){
           FarmaUtility.showMessage(new JDialog(),"Usted no tiene permiso para acceder al sistema de administración", null);
            System.exit(0);  
        }   
    }

    private boolean readFileFarmaVentaProperties() {
        Properties prop = new Properties();
        try {
            prop.load(FrmEconoFar.class.getResourceAsStream("/farmaventa.properties"));
            //ERIOS 2.2.9 Se guarda los datos de la version
            VariablesPtoVenta.vNombreModulo = "FarmaVenta";
            VariablesPtoVenta.vVersion = prop.getProperty("version");
            VariablesPtoVenta.vCompilacion = prop.getProperty("compilacion");
            DlgProcesar.setVersion();
            
            tituloBaseFrame = VariablesPtoVenta.vNombreModulo+" "+VariablesPtoVenta.vVersion+" - "+VariablesPtoVenta.vCompilacion;
            log.info("Version: "+tituloBaseFrame);           
        } catch (IOException e) {
            log.error("",e);
            return false;
        }        
        return true;
    }
    
    void muestraLogin() {
        
      //if ( readFileProperties() ) 
      if(true)
      {
        obtieneInfoLocal();
        //obtener info del local
        if(!validaIpPc()){
          FarmaUtility.showMessage(this,"Error al obtener la IP de la PC.", null);
          salirSistema();
        }
        String mensajeLogin = "Acceso "+tituloBaseFrame;
          
      DlgLogin dlgLogin = new DlgLogin(this,mensajeLogin,true);
      dlgLogin.setVisible(true);
        if (!FarmaVariables.vAceptar) {
          FarmaConnection.closeConnection();
          salirSistema();
        } else {
                VariablesModuloVentas.tableModelListaGlobalProductos = new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,0);
            
        }
      } else salirSistema();
    }
    
    public void obtieneInfoLocal() {
        try {
            ArrayList infoLocal = DBPtoVenta.obtieneDatosLocal();
            FarmaVariables.vDescCortaLocal = ((String)((ArrayList)infoLocal.get(0)).get(0)).trim();
            FarmaVariables.vDescLocal = ((String)((ArrayList)infoLocal.get(0)).get(1)).trim();
            FarmaVariables.vTipLocal = ((String)((ArrayList)infoLocal.get(0)).get(2)).trim();
            FarmaVariables.vTipCaja = ((String)((ArrayList)infoLocal.get(0)).get(3)).trim();
            
            FarmaVariables.vNomCia = ((String)((ArrayList)infoLocal.get(0)).get(4)).trim();
            FarmaVariables.vNuRucCia = ((String)((ArrayList)infoLocal.get(0)).get(5)).trim();
            FarmaVariables.vMinutosPedidosPendientes = ((String)((ArrayList)infoLocal.get(0)).get(6)).trim();
            FarmaVariables.vImprReporte = ((String)((ArrayList)infoLocal.get(0)).get(7)).trim();
            FarmaVariables.vIndHabilitado = ((String)((ArrayList)infoLocal.get(0)).get(8)).trim();
            FarmaVariables.vDescCortaDirLocal = ((String)((ArrayList)infoLocal.get(0)).get(9)).trim();
            
            VariablesPtoVenta.vRazonSocialCia = DBPtoVenta.obtieneRazSoc();
            VariablesPtoVenta.vTelefonoCia = DBPtoVenta.obtieneTelfCia();
            VariablesPtoVenta.vNombreMarcaCia = DBPtoVenta.obtieneNombreMarcaCia();
        } catch (SQLException sqlException) {

            log.error("",sqlException);
        }
    }


    private boolean validaIpPc(){
      FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
      if(FarmaVariables.vIpPc.trim().length() == 0)
        return false;
      return true;
    }
    /**
     * Lee y carga el icono del sistema.
     * @author ERIOS
     * @since 27.02.2013
     */
    private void cargaIcono(){
        String strRutaJpg="IconSVB.jpg";
        try{
            strRutaJpg = DBPtoVenta.obtieneRutaImagen();
        } catch (Exception e) {
            log.error("",e);
            log.debug("Problemas al carga el icono");
            strRutaJpg = "IconSVB.jpg";
        } 
        ImageIcon imageIcono = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/"+strRutaJpg));
        this.setIconImage(imageIcono.getImage());
    }
    private void mnuSalir_SalirSistema_actionPerformed(ActionEvent e) {
      salirSistema();
    }
      
    private void salirSistema()
    {
       if(FarmaVariables.vEconoFar_Matriz)
        this.dispose();
      else
        System.exit(0);    
    }
    
    private void muestraUser() {
        String addon = " Usu.Actual : ";
        addon = addon + FarmaVariables.vIdUsu;
        tituloBaseFrame = "Administración General 01.09.2015 ";
        this.setTitle(tituloBaseFrame + " /  Local : " + FarmaVariables.vDescCortaLocal + " / " + addon + " /  IP : " + FarmaVariables.vIpPc);
    }
    
    private void jMenuItem6_actionPerformed(ActionEvent e) {
        
        DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(this,"",true);
        //dlgResumenPedido.setFrameEconoFar(this);
        VariablesModuloVentas.vIsVtaSoat = true;
        dlgResumenPedido.setVisible(true);
        
    }

    private void jMenuItem1_actionPerformed(ActionEvent e) {
        DlgListaMaeProductos dlgMaeProductos = new DlgListaMaeProductos(this,"",true);
        dlgMaeProductos.setVisible(true);

    }
}
