package venta;


import admcentral.packs.DlgListadoPacks;

import cilator.mantenimiento.DlgListaCategoria;
import cilator.mantenimiento.DlgListaMarca;
import cilator.mantenimiento.DlgListaSubCategoria;
import cilator.mantenimiento.DlgMantIgv;

import cilator.mantenimiento.DlgMantKit;

import cilator.mantenimiento.DlgMantTipoDeCambio;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.lang.reflect.Field;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.UIManager;

import javax.swing.UnsupportedLookAndFeelException;

import common.DlgLogin;
import common.FarmaConnection;
import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import dental.laboratorio.DlgMantMaestroDental;

import mifarma.ptoventa.centromedico.DlgADMListaPacientes;

import mifarma.ptoventa.centromedico.domain.VtaCompAtencionMedica;

import mifarma.ptoventa.centromedico.domain.VtaCompAtencionMedicaRAC;

import mifarma.ptoventa.centromedico.domain.VtaPedidoAtencionMedica;

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

import venta.enfermeria.DlgConsultaDocumento;

import venta.fotos_cliente.Prueba;

import venta.reportes.DlgRegistroComision;
import venta.reportes.DlgRegistroComisionDetalle;
import venta.reportes.DlgRegistroOC;
import venta.reportes.DlgRegistroVentasEnfermera;
import venta.reportes.repo_renova.DlgRptPorCliente;

import venta.modulo_venta.DlgListaPedidosCobrados;
import venta.modulo_venta.reference.DBModuloVenta;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : FrmEconoFar.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA      27.12.2005   Creaci�n<br>
 * ERIOS       20.06.2013   Modificacion<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class FrmAdmMultiEmpresa extends JFrame  {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(FrmAdmMultiEmpresa.class);
    
    //public static String tituloBaseFrame=  "SVB - Venta-13.03.2015";
    public static String tituloBaseFrame=  "29.10.2018";
    int ind=0;

    private JPanel pnlEconoFar = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel lblLogo = new JLabel();
    private JButtonLabel btnRevertir = new JButtonLabel();
    private JTextFieldSanSerif txtRevertir = new JTextFieldSanSerif();
    private JLabel lblMensaje = new JLabel("EN PRUEBAS",JLabel.CENTER);    
    
    private JMenuBar mnuPtoVenta = new JMenuBar();

    private JMenuItem mnuVentas_DistribucionGratuita = new JMenuItem();
    private JMenuItem mnuVentas_DeliveryAutomatico = new JMenuItem();
    private JMenuItem mnuVentas_MedidaPresion = new JMenuItem();
    private JMenu mnuVentas_Recargas = new JMenu();    
    private JMenuItem mnuVentas_Correlativo = new JMenuItem();
    private JMenuItem mnuVentas_InvDiario = new JMenuItem();

    private JMenuItem mnuCaja_AnularComprobante = new JMenuItem();
    private JMenuItem mnuCaja_CorreccionComprobantes = new JMenuItem();
    private JMenuItem mnuCaja_ReimpresionPedido = new JMenuItem();
    private JMenu mnuCaja_Utilitarios = new JMenu();
    private JMenuItem mnuCaja_PruebaImpresora = new JMenuItem();
    private JMenu mnuCaja_Pinpad = new JMenu();
    private JMenuItem mnuCaja_Pinpad_Visa = new JMenuItem();
    private JMenuItem mnuCaja_Pinpad_Mastercard = new JMenuItem();    
    private JMenuItem mnuCaja_reporDet_Pinpad_MC = new JMenuItem();
    private JMenuItem mnuCaja_reporTot_Pinpad_MC = new JMenuItem();
    private JMenuItem mnuCaja_Reimpresion_Pinpad_MC = new JMenuItem();
    private JMenuItem mnuCaja_AnulacionTran_Pinpad_Visa = new JMenuItem();
    private JMenuItem mnuCaja_AbrirGabeta = new JMenuItem();
    private JMenuItem mnuCaja_ReimpresionTicketsAnulados = new JMenuItem();
    private JMenu mnuCaja_Recaudacion = new JMenu();
    private JMenuItem mnuCaja_CMR = new JMenuItem();
    private JMenuItem mnuCaja_Citibank = new JMenuItem();
    private JMenuItem mnuCaja_Claro = new JMenuItem();
    private JMenuItem mnuCaja_Prestamos_Citibank = new JMenuItem();
    private JMenuItem mnuCaja_Ripley = new JMenuItem();
    private JMenuItem mnuCaja_Registro_Recaudaciones = new JMenuItem();
    private JMenuItem mnuCaja_Registro_VentaCMR = new JMenuItem();
    
    private JMenu mnuEconoFar_Inventario = new JMenu();
    private JMenuItem mnuInventario_GuiaEntrada = new JMenuItem();
    private JMenuItem mnuInventario_Kardex = new JMenuItem();
    private JMenu mnuInventario_Transferencias = new JMenu();
    private JMenuItem mnuInventario_Transf_local = new JMenuItem();
    private JMenuItem mnuInventario_Transf_guias = new JMenuItem(); 
    private JMenu mnuInventario_Mercaderia = new JMenu();
    private JMenuItem mnuInventario_Recepcion = new JMenuItem();
    private JMenuItem mnuInventario_RecepcionProductos = new JMenuItem();
    private JMenuItem mnuInventario_IngTransportista = new JMenuItem();
    private JMenu mnuInventario_PedidosLocales = new JMenu();
    private JMenuItem mnuInventario_PedidoReposicion = new JMenuItem();
    private JMenuItem mnuInventario_PedidoAdicional  = new JMenuItem();
    private JMenuItem mnuInventario_PedidoEspecial   = new JMenuItem();
    private JMenuItem mnuInventario_Guias = new JMenuItem();
    private JMenuItem mnuInventario_Ajustes = new JMenuItem();
    private JMenuItem mnuInventario_RecepcionLocales = new JMenuItem();
    private JMenu mnuInventario_MercaderiaDirecta = new JMenu();
    private JMenuItem mnuInventario_RecepcionMerDirec = new JMenuItem();
    private JMenuItem mnuInventario_Devolucion = new JMenuItem();
    
    private JMenu mnuEconoFar_TomaInventario = new JMenu();    
    private JMenu mnuTomaInventario_Tradicional = new JMenu();
    private JMenuItem mnuInventario_Inicio = new JMenuItem();
    private JMenuItem mnuInventario_Toma = new JMenuItem();
    private JMenu mnuTomaInventario_Ciclico = new JMenu();
    private JMenuItem mnuInventarioCiclico_Inicio = new JMenuItem();
    private JMenuItem mnuInventarioCiclico_Toma = new JMenuItem();  
    private JMenu mnuTomaInventario_Diario = new JMenu();  
    private JMenuItem mnuInventarioDiario_Toma = new JMenuItem();  
    private JMenuItem mnuInventarioDiario_Diferencia = new JMenuItem();  
    private JMenuItem mnuTomaInventario_Historico = new JMenuItem();
    private JMenuItem mnuTomaInVentario_ItemsXLab = new JMenuItem();
    
    private JMenu mnuEconoFar_Administracion = new JMenu();
    private JMenu mnuAdministracion_Usuarios = new JMenu();
    private JMenuItem mnuUsuarios_CambioUsuario = new JMenuItem();
    private JMenuItem mnuUsuarios_CambioClave = new JMenuItem();
    private JMenuItem mnuMantenimiento_Clientes = new JMenuItem();
    private JMenuItem mnuMantenimiento_Parametros = new JMenuItem();
    private JMenuItem mnuMantenimiento_Carne = new JMenuItem();
    private JMenu mnuAdministracion_MovCaja = new JMenu();
    private JMenuItem mnuMovCaja_RegistrarVentas = new JMenuItem();
    private JMenuItem mnuMovCaja_ConsultarMov = new JMenuItem();
    private JMenuItem mnuAdministracion_RegViajero = new JMenuItem();
    private JMenuItem mnuAdministracion_ControlHoras = new JMenuItem();
    private JMenu mnuAdministracion_Otros = new JMenu();
    private JMenuItem mnuOtros_MantProbisa = new JMenuItem();
    private JMenuItem mnuAdministracion_FondoSencillo = new JMenuItem();
    private JMenu mnuAdministracion_ProdCambiados = new JMenu();
    private JMenuItem mnuProdCambiados_PrecioCambProd = new JMenuItem();
    //01.10.2013 CVILCA
    private JMenuItem mnuProductos_Impresion = new JMenuItem();
    
    private JMenu mnuEconoFar_Reportes = new JMenu();
    private JMenuItem mnuReportes_RegistroVentas = new JMenuItem();
    private JMenu mnuReportes_VentasVendedor = new JMenu();
    private JMenuItem mnuReportes_VentasVendedor_Total = new JMenuItem();
    private JMenuItem mnuReportes_VentasVendedor_Mezon = new JMenuItem();
    private JMenuItem mnuReportes_VentasVendedor_Delivery = new JMenuItem();
    private JMenuItem mnuReportes_VentasVendedor_Institucional = new JMenuItem();
    private JMenuItem mnuReportes_DetalleVentas = new JMenuItem();
    private JMenuItem mnuReportes_ResumenVentaDia = new JMenuItem();
    private JMenuItem mnuReportes_VentasProducto = new JMenuItem();
    private JMenuItem mnuReportes_VentasConvenio = new JMenuItem();
    private JMenuItem mnuReportes_VentasLinea = new JMenuItem();
    private JMenuItem mnuReportes_VentasHora = new JMenuItem();
    private JMenuItem mnuReportes_VentasDiaMes = new JMenuItem();
    private JMenuItem mnuReportes_FaltaCero = new JMenuItem();
    private JMenuItem mnuReportes_ProductosABC = new JMenuItem();
    private JMenuItem mnuReportes_UnidVtaLocal = new JMenuItem();
    private JMenuItem mnuReportes_ProdSinVtaNDias = new JMenuItem();

    private JMenuItem mnuCE_Prosegur = new JMenuItem();
    
    //09.09.2013 wvillagomez
    private JMenu mnuCE_PagoSencilloETV = new JMenu();
    private JMenuItem mnuCE_ReciboSencillo = new JMenuItem();
    private JMenuItem mnuCE_PagoSencillo = new JMenuItem();
    
    private JMenu mnuEconoFar_ControlIngreso = new JMenu();
    private JMenuItem mnuIngreso = new JMenuItem();
    private JMenuItem mnuIngresoTemperatura = new JMenuItem();
    
    private JMenu mnuEconoFar_Salir = new JMenu();
    private JMenuItem mnuSalir_SalirSistema = new JMenuItem();
    
    private JMenu mnuEconoFar_Ayuda = new JMenu();
    private JMenuItem mnuAyuda_AcercaDe = new JMenuItem();
    private JMenuItem mnCaja_ReimpresionLote_Pinpad_MC = new JMenuItem();
    private JMenuItem mnuReportes_ListPsicotropicos = new JMenuItem();
    private JMenuItem mnuReportes_ListRecetario = new JMenuItem();
    private JMenuItem mnuCE_ReprocesaConcil = new JMenuItem();
    private JMenuItem mnuInventario_RecepcionRM = new JMenuItem();
    private JMenuItem prueba = new JMenuItem();
    private JMenuItem mnuInventario_StockNegativo = new JMenuItem();
    private JMenuItem mnuCaja_PedidoPinpad = new JMenuItem();
    private JMenuItem mnuReportes_ListGuias = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JLabel lblLogo_empresa = new JLabel();
    private ImageIcon iconLogo = new ImageIcon("Images/YourCompanyLogo.png");
    private JLabel lblMensajeUsuario = new JLabel();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem5 = new JMenuItem();
    private JMenuItem jMenuItem6 = new JMenuItem();
    private JMenuItem jMenuItem7 = new JMenuItem();
    private JMenuItem jMenuItem8 = new JMenuItem();
    private JMenuItem jMenuItem9 = new JMenuItem();
    private JMenuItem jMenuItem10 = new JMenuItem();
    private JMenuItem jMenuItem11 = new JMenuItem();
    private JMenuItem jMenuItem12 = new JMenuItem();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public FrmAdmMultiEmpresa() {

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

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        
        this.getContentPane().setLayout(borderLayout1);
        
        this.setSize(new Dimension(1037, 600));
        this.setFont(new Font("Bookman Old Style", 2, 17));
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
        mnuVentas_DistribucionGratuita.setText("2. Distribucion Gratuita");
        mnuVentas_DistribucionGratuita.setFont(new Font("SansSerif", 0, 11));
        mnuVentas_DistribucionGratuita.setMnemonic('2');
        mnuVentas_DistribucionGratuita.setEnabled(false);
        mnuVentas_DistribucionGratuita.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuVentas_DistribucionGratuita_actionPerformed(e);
                }
            });
        //mnuEconoFar_Ventas.add(mnuVentas_DistribucionGratuita);
        mnuVentas_DeliveryAutomatico.setText("3. Delivery Autom�tico");
        mnuVentas_DeliveryAutomatico.setMnemonic('3');
        mnuVentas_DeliveryAutomatico.setFont(new Font("SansSerif", 0, 11));
        mnuVentas_DeliveryAutomatico.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuVentas_DeliveryAutomatico_actionPerformed(e);
                }
            });
        //mnuEconoFar_Ventas.add(mnuVentas_DeliveryAutomatico);
        mnuVentas_MedidaPresion.setText("4. Medida Presion");
        mnuVentas_MedidaPresion.setMnemonic('4');
        mnuVentas_MedidaPresion.setFont(new Font("SansSerif", 0, 11));
        mnuVentas_MedidaPresion.setEnabled(false);
        mnuVentas_MedidaPresion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuVentas_MedidaPresion_actionPerformed(e);
                }
            });
        //mnuEconoFar_Ventas.add(mnuVentas_MedidaPresion);
        mnuVentas_Recargas.setText("5. Recargas Telef�nicas");
        mnuVentas_Recargas.setMnemonic('5');
        mnuVentas_Recargas.setFont(new Font("Sanserif", 0, 11));
        mnuVentas_Recargas.setEnabled(false);
        //mnuEconoFar_Ventas.add(mnuVentas_Recargas);
        mnuVentas_Correlativo.setText("1. Correlativo");
        mnuVentas_Correlativo.setMnemonic('1');
        mnuVentas_Correlativo.setFont(new Font("Sanserif", 0, 11));
        mnuVentas_Correlativo.setEnabled(true);
        mnuVentas_Correlativo.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mnuVentas_Correlativo_actionPerformed(e);
                }
            });
        mnuVentas_Recargas.add(mnuVentas_Correlativo);
        mnuVentas_InvDiario.setText("6. Cobro Pedido Inv. Diario");
        mnuVentas_InvDiario.setFont(new Font("SansSerif", 0, 11));
        mnuVentas_InvDiario.setMnemonic('6');
        mnuVentas_InvDiario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem2_actionPerformed(e);
                }
            });
        //mnuEconoFar_Ventas.add(mnuVentas_InvDiario);

        // MODULO CAJA
        mnuCaja_AnularComprobante.setText("2. Anular Comprobante");
        mnuCaja_AnularComprobante.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_AnularComprobante.setActionCommand("Anular Pedidos");
        mnuCaja_AnularComprobante.setMnemonic('2');
        mnuCaja_AnularComprobante.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_AnularComprobante_actionPerformed(e);
                }
            });
        //mnuCaja_AnularVentas.add(mnuCaja_AnularComprobante);
        mnuCaja_CorreccionComprobantes.setText("5. Correcci�n de comprobantes");
        mnuCaja_CorreccionComprobantes.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_CorreccionComprobantes.setMnemonic('5');
        mnuCaja_CorreccionComprobantes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_CorreccionComprobantes_actionPerformed(e);
                }
            });
        //mnuEconoFar_Caja.add(mnuCaja_CorreccionComprobantes);
        mnuCaja_ReimpresionPedido.setText("6. Reimpresion de Pedido");
        mnuCaja_ReimpresionPedido.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_ReimpresionPedido.setMnemonic('6');
        mnuCaja_ReimpresionPedido.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_ReimpresionPedido_actionPerformed(e);
                }
            });
        //mnuEconoFar_Caja.add(mnuCaja_ReimpresionPedido);
        mnuCaja_Utilitarios.setText("7. Utilitarios");
        mnuCaja_Utilitarios.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Utilitarios.setMnemonic('7');
        //mnuEconoFar_Caja.add(mnuCaja_Utilitarios);
        mnuCaja_PruebaImpresora.setText("1. Prueba Impresora");
        mnuCaja_PruebaImpresora.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_PruebaImpresora.setMnemonic('1');
        mnuCaja_PruebaImpresora.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_PruebaImpresora_actionPerformed(e);
                }
            });
        mnuCaja_Utilitarios.add(mnuCaja_PruebaImpresora);
        mnuCaja_Pinpad.setText("2. Prueba Pinpad");
        mnuCaja_Pinpad.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Pinpad_Visa.setText("1. Oper. No Financieras Visa");
        mnuCaja_Pinpad_Visa.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Pinpad_Visa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Pinpad_Visa_actionPerformed(e);
                }
            });
        mnuCaja_Pinpad_Mastercard.setText("2. Cierre Mastercard");
        mnuCaja_Pinpad_Mastercard.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Pinpad_Mastercard.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Pinpad_Mastercard_actionPerformed(e);
                }
            });
        mnuCaja_reporDet_Pinpad_MC.setText("3. Reporte Detallado Mastercard");
        mnuCaja_reporDet_Pinpad_MC.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_reporDet_Pinpad_MC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_reporDet_Pinpad_MC_actionPerformed(e);
                }
            });
        mnuCaja_reporTot_Pinpad_MC.setText("4. Reporte Total Mastercard");
        mnuCaja_reporTot_Pinpad_MC.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_reporTot_Pinpad_MC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_reporTot_Pinpad_MC_actionPerformed(e);
                }
            });
        mnuCaja_Reimpresion_Pinpad_MC.setText("5. Reimpresi�n Voucher Mastercard");
        mnuCaja_Reimpresion_Pinpad_MC.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Reimpresion_Pinpad_MC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Reimpresion_Pinpad_MC_actionPerformed(e);
                }
            });
        mnuCaja_AnulacionTran_Pinpad_Visa.setText("6. Anulaci�n Transacci�n Pinpad");
        mnuCaja_AnulacionTran_Pinpad_Visa.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_AnulacionTran_Pinpad_Visa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_AnulacionTran_Pinpad_Visa_actionPerformed(e);
                }
            });
        mnuCaja_Pinpad.add(mnuCaja_Pinpad_Visa);
        mnuCaja_Pinpad.add(mnuCaja_Pinpad_Mastercard);
        mnuCaja_Pinpad.add(mnuCaja_reporDet_Pinpad_MC);
        mnuCaja_Pinpad.add(mnuCaja_reporTot_Pinpad_MC);
        mnuCaja_Pinpad.add(mnuCaja_Reimpresion_Pinpad_MC);
        mnuCaja_Pinpad.add(mnuCaja_AnulacionTran_Pinpad_Visa);
        mnuCaja_Pinpad.add(mnCaja_ReimpresionLote_Pinpad_MC);
        mnuCaja_Utilitarios.add(mnuCaja_Pinpad);
        mnuCaja_AbrirGabeta.setText("3. Abrir Gabeta");
        mnuCaja_AbrirGabeta.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_AbrirGabeta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_AbrirGabeta_actionPerformed(e);
                }
            });
        mnuCaja_Utilitarios.add(mnuCaja_AbrirGabeta);
        mnuCaja_ReimpresionTicketsAnulados.setText("8. Reimpresi�n de tickets anulados");
        mnuCaja_ReimpresionTicketsAnulados.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_ReimpresionTicketsAnulados.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_ReimpresionTicketsAnulados_actionPerformed(e);
                }
            });
        //mnuEconoFar_Caja.add(mnuCaja_ReimpresionTicketsAnulados);
        mnuCaja_Recaudacion.setText("10.Pago de Terceros");
        mnuCaja_Recaudacion.setFont(new Font("SansSerif", 0, 11));
        //mnuEconoFar_Caja.add(mnuCaja_Recaudacion);
        //mnuEconoFar_Caja.add(mnuCaja_PedidoPinpad);
        mnuCaja_CMR.setText("1. CMR");
        mnuCaja_CMR.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_CMR.setMnemonic('1');
        mnuCaja_CMR.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_CMR_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_CMR);
        mnuCaja_Citibank.setText("2. Citibank");
        mnuCaja_Citibank.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Citibank.setMnemonic('2');
        mnuCaja_Citibank.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem5_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_Citibank);
        mnuCaja_Claro.setText("3. Claro");
        mnuCaja_Claro.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Claro.setMnemonic('3');
        mnuCaja_Claro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Claro_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_Claro);
        mnuCaja_Prestamos_Citibank.setText("4. Prestamos Citibank");
        mnuCaja_Prestamos_Citibank.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Prestamos_Citibank.setMnemonic('4');
        mnuCaja_Prestamos_Citibank.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Prestamos_Citibank_actionPerformed(e);
                }
            });
        //GFonseca 20.09.2013 - opcion de recaudacion Ripley
        mnuCaja_Recaudacion.add(mnuCaja_Prestamos_Citibank);
        mnuCaja_Ripley.setText("5. Ripley");
        mnuCaja_Ripley.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Ripley.setMnemonic('5');
        mnuCaja_Ripley.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Ripley_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_Ripley);

        mnuCaja_Registro_Recaudaciones.setText("6. Registro de pagos");
        mnuCaja_Registro_Recaudaciones.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Registro_Recaudaciones.setMnemonic('6');
        mnuCaja_Registro_Recaudaciones.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Registro_Recaudaciones_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_Registro_Recaudaciones);

        mnuCaja_Registro_VentaCMR.setText("7. Registro Venta CMR");
        mnuCaja_Registro_VentaCMR.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_Registro_VentaCMR.setMnemonic('7');
        mnuCaja_Registro_VentaCMR.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_Registro_Venta_CMR_actionPerformed(e);
                }
            });
        mnuCaja_Recaudacion.add(mnuCaja_Registro_VentaCMR);

        // MODULO INVENTARIO
        mnuEconoFar_Inventario.setText("Inventario");
        mnuEconoFar_Inventario.setMnemonic('i');
        mnuEconoFar_Inventario.setFont(new Font("SansSerif", 0, 11));
        mnuPtoVenta.add(mnuEconoFar_Inventario);
        mnuInventario_GuiaEntrada.setText("1. Guia de Ingreso");
        mnuInventario_GuiaEntrada.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_GuiaEntrada.setMnemonic('1');
        mnuInventario_GuiaEntrada.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_GuiaEntrada_actionPerformed(e);
                }
            });
        mnuEconoFar_Inventario.add(mnuInventario_GuiaEntrada);
        mnuInventario_Kardex.setText("2. Kardex");
        mnuInventario_Kardex.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Kardex.setMnemonic('2');
        mnuInventario_Kardex.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Kardex_actionPerformed(e);
                }
            });
        mnuEconoFar_Inventario.add(mnuInventario_Kardex);
        mnuInventario_Transferencias.setText("3. Transferencias");
        mnuInventario_Transferencias.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Transferencias.setMnemonic('3');
        mnuEconoFar_Inventario.add(mnuInventario_Transferencias);
        mnuInventario_Transf_local.setText("1. Generar Guia.");
        mnuInventario_Transf_local.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Transf_local.setMnemonic('1');
        mnuInventario_Transf_local.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Transf_local_actionPerformed(e);
                }
            });
        mnuInventario_Transferencias.add(mnuInventario_Transf_local);
        mnuInventario_Transf_guias.setText("3. Guias de Salida");
        mnuInventario_Transf_guias.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Transf_guias.setMnemonic('3');
        mnuInventario_Transf_guias.setEnabled(true);
        mnuInventario_Transf_guias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Transf_guias_actionPerformed(e);
                }
            });
        //mnuInventario_Transferencias.add(mnuInventario_Transf_guias);
        mnuInventario_Mercaderia.setText("4. Recepcion Mercaderia");
        mnuInventario_Mercaderia.setDisplayedMnemonicIndex(0);
        mnuInventario_Mercaderia.setMnemonic('4');
        mnuInventario_Mercaderia.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Mercaderia.setFocusable(false);
        //mnuEconoFar_Inventario.add(mnuInventario_Mercaderia);
        mnuInventario_Recepcion.setText("1. Recepcion Ciega ");
        mnuInventario_Recepcion.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Recepcion.setMnemonic('1');
        mnuInventario_Recepcion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Recepcion_actionPerformed(e);
                }
            });
        mnuInventario_Mercaderia.add(mnuInventario_Recepcion);
        mnuInventario_RecepcionProductos.setText("2. Recepci�n de Almac�n");
        mnuInventario_RecepcionProductos.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_RecepcionProductos.setMnemonic('2');
        mnuInventario_RecepcionProductos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem4_actionPerformed(e);
                }
            });
        mnuInventario_Mercaderia.add(mnuInventario_RecepcionProductos);
        mnuInventario_IngTransportista.setText("3. Ingreso Transportista");
        mnuInventario_IngTransportista.setMnemonic('3');
        mnuInventario_IngTransportista.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_IngTransportista.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuIngTransportista_actionPerformed(e);
                }
            });
        mnuInventario_Mercaderia.add(mnuInventario_IngTransportista);
        mnuInventario_PedidosLocales.setText("5. Pedidos Locales");
        mnuInventario_PedidosLocales.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_PedidosLocales.setEnabled(true);
        mnuInventario_PedidosLocales.setMnemonic('4');
        //mnuEconoFar_Inventario.add(mnuInventario_PedidosLocales);
        mnuInventario_PedidoReposicion.setText("1. Pedido Reposici�n");
        mnuInventario_PedidoReposicion.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_PedidoReposicion.setMnemonic('1');
        mnuInventario_PedidoReposicion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_PedidoReposicion_actionPerformed(e);
                }
            });
        mnuInventario_PedidosLocales.add(mnuInventario_PedidoReposicion);
        mnuInventario_PedidoAdicional.setText("2. PVM");
        mnuInventario_PedidoAdicional.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_PedidoAdicional.setMnemonic('1');
        mnuInventario_PedidoAdicional.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuPedidoAdicional_actionPerformed(e);
                }
            });
        mnuInventario_PedidosLocales.add(mnuInventario_PedidoAdicional);
        mnuInventario_PedidoEspecial.setText("3. Pedido Especial");
        mnuInventario_PedidoEspecial.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_PedidoEspecial.setMnemonic('1');
        mnuInventario_PedidoEspecial.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuPedidoEspecial_actionPerformed(e);
                }
            });
        mnuInventario_PedidosLocales.add(mnuInventario_PedidoEspecial);
        mnuInventario_Guias.setText("6. Correccion Guias");
        mnuInventario_Guias.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Guias.setMnemonic('6');
        mnuInventario_Guias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Guias_actionPerformed(e);
                }
            });
        //mnuEconoFar_Inventario.add(mnuInventario_Guias);
        mnuInventario_Ajustes.setText("7. Ajustes por Fecha");
        mnuInventario_Ajustes.setMnemonic('7');
        mnuInventario_Ajustes.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Ajustes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Ajustes_actionPerformed(e);
                }
            });
        //mnuEconoFar_Inventario.add(mnuInventario_Ajustes);
        mnuInventario_RecepcionLocales.setText("8. Recepcion Transferencias");
        mnuInventario_RecepcionLocales.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_RecepcionLocales.setMnemonic('8');
        mnuInventario_RecepcionLocales.setEnabled(true);
        mnuInventario_RecepcionLocales.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_RecepcionLocales_actionPerformed(e);
                }
            });
        //mnuEconoFar_Inventario.add(mnuInventario_RecepcionLocales);
        mnuInventario_MercaderiaDirecta.setText("9. Mercaderia Directa");
        mnuInventario_MercaderiaDirecta.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_MercaderiaDirecta.setMnemonic('9');
        //mnuEconoFar_Inventario.add(mnuInventario_MercaderiaDirecta);
        //mnuEconoFar_Inventario.add(prueba);
        //mnuEconoFar_Inventario.add(mnuInventario_StockNegativo);
        mnuInventario_RecepcionMerDirec.setText("1. Recepcion");
        mnuInventario_RecepcionMerDirec.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_RecepcionMerDirec.setMnemonic('1');
        mnuInventario_RecepcionMerDirec.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_RecepcionMerDirec_actionPerformed(e);
                }
            });
        mnuInventario_MercaderiaDirecta.add(mnuInventario_RecepcionMerDirec);
        mnuInventario_Devolucion.setText("2. Devolucion");
        mnuInventario_Devolucion.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Devolucion.setMnemonic('2');
        mnuInventario_Devolucion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Devolucion_actionPerformed(e);
                }
            });
        mnuInventario_MercaderiaDirecta.add(mnuInventario_Devolucion);

        // MODULO TOMA INVENTARIO
        //mnuInventario_MercaderiaDirecta.add(mnuInventario_RecepcionRM);
        mnuEconoFar_TomaInventario.setText("Toma Inventario");
        mnuEconoFar_TomaInventario.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_TomaInventario.setMnemonic('t');
        //mnuPtoVenta.add(mnuEconoFar_TomaInventario);
        mnuTomaInventario_Tradicional.setText("1. Tradicional");
        mnuTomaInventario_Tradicional.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Tradicional.setMnemonic('1');
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Tradicional);
        mnuInventario_Inicio.setText("1.Inicio");
        mnuInventario_Inicio.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Inicio.setMnemonic('1');
        mnuInventario_Inicio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Inicio_actionPerformed(e);
                }
            });
        mnuTomaInventario_Tradicional.add(mnuInventario_Inicio);
        mnuInventario_Toma.setText("2. Toma");
        mnuInventario_Toma.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Toma.setMnemonic('2');
        mnuInventario_Toma.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_Toma_actionPerformed(e);
                }
            });
        mnuTomaInventario_Tradicional.add(mnuInventario_Toma);
        mnuTomaInventario_Ciclico.setText("2. C�clico");
        mnuTomaInventario_Ciclico.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Ciclico.setMnemonic('2');
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Ciclico);
        mnuInventarioCiclico_Inicio.setText("1. Inicio");
        mnuInventarioCiclico_Inicio.setFont(new Font("SansSerif", 0, 11));
        mnuInventarioCiclico_Inicio.setMnemonic('1');
        mnuInventarioCiclico_Inicio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventarioCiclico_Inicio_actionPerformed(e);
                }
            });
        mnuTomaInventario_Ciclico.add(mnuInventarioCiclico_Inicio);
        mnuInventarioCiclico_Toma.setText("2. Toma");
        mnuInventarioCiclico_Toma.setFont(new Font("SansSerif", 0, 11));
        mnuInventarioCiclico_Toma.setMnemonic('2');
        mnuInventarioCiclico_Toma.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventarioCiclico_Toma_actionPerformed(e);
                }
            });
        mnuTomaInventario_Ciclico.add(mnuInventarioCiclico_Toma);
        mnuTomaInventario_Diario.setText("3. Diario");
        mnuTomaInventario_Diario.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Diario.setMnemonic('3');
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Diario);
        mnuInventarioDiario_Toma.setText("1. Toma");
        mnuInventarioDiario_Toma.setFont(new Font("SansSerif", 0, 11));
        mnuInventarioDiario_Toma.setMnemonic('1');
        mnuInventarioDiario_Toma.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventarioDiario_Toma_actionPerformed(e);
                }
            });
        mnuTomaInventario_Diario.add(mnuInventarioDiario_Toma);
        mnuInventarioDiario_Diferencia.setText("2. Diferencia");
        mnuInventarioDiario_Diferencia.setFont(new Font("SansSerif", 0, 11));
        mnuInventarioDiario_Diferencia.setMnemonic('2');
        mnuInventarioDiario_Diferencia.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventarioDiario_Diferencia_actionPerformed(e);
                }
            });
        mnuTomaInventario_Diario.add(mnuInventarioDiario_Diferencia);
        mnuTomaInventario_Historico.setText("4. Historico");
        mnuTomaInventario_Historico.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Historico.setMnemonic('4');
        mnuTomaInventario_Historico.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuTomaInventario_Historico_actionPerformed(e);
                }
            });
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Historico);
        mnuTomaInVentario_ItemsXLab.setText("5. Items por laboratorio");
        mnuTomaInVentario_ItemsXLab.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInVentario_ItemsXLab.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuTomaInVentario_ItemsXLab_actionPerformed(e);
                }
            });
        mnuEconoFar_TomaInventario.add(mnuTomaInVentario_ItemsXLab);

        // MODULO ADMINISTRACION
        mnuEconoFar_Administracion.setText("Administracion");
        mnuEconoFar_Administracion.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_Administracion.setMnemonic('a');
        mnuEconoFar_Administracion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuAdministracion_Mov_actionPerformed(e);
                }
            });
        mnuPtoVenta.add(mnuEconoFar_Administracion);
        mnuAdministracion_Usuarios.setText("1. Usuarios");
        mnuAdministracion_Usuarios.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_Usuarios.setMnemonic('1');
        mnuEconoFar_Administracion.add(mnuAdministracion_Usuarios);
        mnuUsuarios_CambioUsuario.setText("1. Cambiar de Usuario");
        mnuUsuarios_CambioUsuario.setFont(new Font("SansSerif", 0, 11));
        mnuUsuarios_CambioUsuario.setMnemonic('1');
        mnuUsuarios_CambioUsuario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuUsuarios_CambioUsuario_actionPerformed(e);
                }
            });
        mnuAdministracion_Usuarios.add(mnuUsuarios_CambioUsuario);
        mnuUsuarios_CambioClave.setText("2. Modificar mi Clave");
        mnuUsuarios_CambioClave.setFont(new Font("SansSerif", 0, 11));
        mnuUsuarios_CambioClave.setMnemonic('2');
        mnuUsuarios_CambioClave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuUsuarios_CambioClave_actionPerformed(e);
                }
            });
        mnuAdministracion_Usuarios.add(mnuUsuarios_CambioClave);
        mnuMantenimiento_Clientes.setText("4. Clientes");
        mnuMantenimiento_Clientes.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_Clientes.setMnemonic('4');
        mnuMantenimiento_Clientes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuMantenimiento_Clientes_actionPerformed(e);
                }
            });
        //mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Clientes);
        mnuMantenimiento_Parametros.setText("5. Parametros");
        mnuMantenimiento_Parametros.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_Parametros.setMnemonic('5');
        mnuMantenimiento_Parametros.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuMantenimiento_Parametros_actionPerformed(e);
                }
            });
        //mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Parametros);
        mnuMantenimiento_Carne.setText("6. Carn� Sanidad");
        mnuMantenimiento_Carne.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_Carne.setMnemonic('6');
        mnuMantenimiento_Carne.setEnabled(false);
        mnuMantenimiento_Carne.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuMantenimiento_Carne_actionPerformed(e);
                }
            });
        //mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Carne);
        mnuAdministracion_MovCaja.setText("3. Movimientos Caja");
        mnuAdministracion_MovCaja.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_MovCaja.setMnemonic('3');
        mnuEconoFar_Administracion.add(mnuAdministracion_MovCaja);
        mnuMovCaja_RegistrarVentas.setText("Registrar Ventas");
        mnuMovCaja_RegistrarVentas.setFont(new Font("SansSerif", 0, 11));
        mnuMovCaja_RegistrarVentas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuMovCaja_RegistrarVentas_actionPerformed(e);
                }
            });
        //mnuAdministracion_MovCaja.add(mnuMovCaja_RegistrarVentas);
        mnuMovCaja_ConsultarMov.setText("Consultar Movimientos");
        mnuMovCaja_ConsultarMov.setFont(new Font("SansSerif", 0, 11));
        mnuMovCaja_ConsultarMov.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuMovCaja_ConsultarMov_actionPerformed(e);
                }
            });
        mnuAdministracion_MovCaja.add(mnuMovCaja_ConsultarMov);
        mnuAdministracion_RegViajero.setText("4. Regenerar Viajero");
        mnuAdministracion_RegViajero.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_RegViajero.setMnemonic('4');
        mnuAdministracion_RegViajero.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuAdministracion_RegViajero_actionPerformed(e);
                }
            });
        //mnuEconoFar_Administracion.add(mnuAdministracion_RegViajero);
        mnuAdministracion_ControlHoras.setText("5. Control de Horas");
        mnuAdministracion_ControlHoras.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_ControlHoras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuAdministracion_ControlHoras_actionPerformed(e);
                }
            });
        //mnuEconoFar_Administracion.add(mnuAdministracion_ControlHoras);
        mnuAdministracion_Otros.setText("6. Otros");
        mnuAdministracion_Otros.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_Otros.setMnemonic('5');
        //mnuEconoFar_Administracion.add(mnuAdministracion_Otros);
        mnuOtros_MantProbisa.setText("1. Mant. PROBISA");
        mnuOtros_MantProbisa.setFont(new Font("SansSerif", 0, 11));
        mnuOtros_MantProbisa.setMnemonic('1');
        mnuOtros_MantProbisa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuOtros_MantProbisa_actionPerformed(e);
                }
            });
        mnuAdministracion_Otros.add(mnuOtros_MantProbisa);
        mnuAdministracion_FondoSencillo.setText("7. Fondo de Sencillo");
        mnuAdministracion_FondoSencillo.setFont(new Font("SansSerif", 0, 11));
        mnuAdministracion_FondoSencillo.setMnemonic('7');
        mnuAdministracion_FondoSencillo.setVisible(false);
        mnuAdministracion_FondoSencillo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuFondoSencillo_actionPerformed(e);
                }
            });
        //mnuEconoFar_Administracion.add(mnuAdministracion_FondoSencillo);
        mnuAdministracion_ProdCambiados.setText("8. Producto");
        mnuAdministracion_ProdCambiados.setFont(new Font("SansSerif", 0, 11));
        //mnuEconoFar_Administracion.add(mnuAdministracion_ProdCambiados);
        mnuProdCambiados_PrecioCambProd.setText("1. Precios cambiado");
        mnuProdCambiados_PrecioCambProd.setFont(new Font("SansSerif", 0, 11));
        mnuProdCambiados_PrecioCambProd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuPrecioCambProd_actionPerformed(e);
                }
            });
        mnuAdministracion_ProdCambiados.add(mnuProdCambiados_PrecioCambProd);
        //01.10.2013 CVILCA
        mnuProductos_Impresion.setText("2. Impresi�n de Stickers");
        mnuProductos_Impresion.setFont(new Font("SansSerif", 0, 11));
        mnuProductos_Impresion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuProdImpresion_actionPerformed(e);
                }
            });
        mnuAdministracion_ProdCambiados.add(mnuProductos_Impresion);

        // MODULO REPORTES
        mnuEconoFar_Reportes.setText("Reportes");
        mnuEconoFar_Reportes.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_Reportes.setMnemonic('r');
        mnuPtoVenta.add(mnuEconoFar_Reportes);
        jMenu2.add(jMenuItem5);
        jMenu2.add(jMenuItem6);
        jMenu2.add(jMenuItem7);
        jMenu2.add(jMenuItem8);
        jMenu2.add(jMenuItem9);
        mnuPtoVenta.add(jMenu2);
        mnuReportes_RegistroVentas.setText("1. Registro de Ventas");
        mnuReportes_RegistroVentas.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_RegistroVentas.setMnemonic('1');
        mnuReportes_RegistroVentas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem1_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_RegistroVentas);
        mnuReportes_VentasVendedor.setText("2. Ventas por vendedor");
        mnuReportes_VentasVendedor.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor.setMnemonic('2');
        //mnuEconoFar_Reportes.add(mnuReportes_VentasVendedor);
        mnuReportes_VentasVendedor_Total.setText("1. Ventas Totales");
        mnuReportes_VentasVendedor_Total.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Total.setMnemonic('1');
        mnuReportes_VentasVendedor_Total.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasVendedor_Total_actionPerformed(e);
                }

            });
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Total);
        mnuReportes_VentasVendedor_Mezon.setText("2. Ventas Meson");
        mnuReportes_VentasVendedor_Mezon.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Mezon.setMnemonic('2');
        mnuReportes_VentasVendedor_Mezon.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasVendedor_Mezon_actionPerformed(e);
                }
            });
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Mezon);
        mnuReportes_VentasVendedor_Delivery.setText("3. Ventas Delivery");
        mnuReportes_VentasVendedor_Delivery.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Delivery.setMnemonic('3');
        mnuReportes_VentasVendedor_Delivery.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasVendedor_Delivery_actionPerformed(e);
                }


            });
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Delivery);
        mnuReportes_VentasVendedor_Institucional.setText("4. Ventas Institucional");
        mnuReportes_VentasVendedor_Institucional.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Institucional.setMnemonic('4');
        mnuReportes_VentasVendedor_Institucional.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasVendedor_Institucional_actionPerformed(e);
                }


            });
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Institucional);
        mnuReportes_DetalleVentas.setText("2. Detalle de ventas");
        mnuReportes_DetalleVentas.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_DetalleVentas.setMnemonic('3');
        mnuReportes_DetalleVentas.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_DetalleVentas_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_DetalleVentas);
        mnuReportes_ResumenVentaDia.setText("3. Ventas resumen por d\u00eda");
        mnuReportes_ResumenVentaDia.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ResumenVentaDia.setMnemonic('4');
        mnuReportes_ResumenVentaDia.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ResumenVentaDia_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_ResumenVentaDia);
        mnuReportes_VentasProducto.setText("4. Ventas por producto");
        mnuReportes_VentasProducto.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasProducto.setMnemonic('5');
        mnuReportes_VentasProducto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasProducto_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_VentasProducto);
        mnuReportes_VentasConvenio.setText("6. Ventas por convenio");
        mnuReportes_VentasConvenio.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasConvenio.setMnemonic('6');
        mnuReportes_VentasConvenio.setEnabled(false);
        //mnuEconoFar_Reportes.add(mnuReportes_VentasConvenio);
        mnuReportes_VentasLinea.setText("5. Ventas por l\u00ednea");
        mnuReportes_VentasLinea.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasLinea.setMnemonic('7');
        mnuReportes_VentasLinea.setEnabled(true);
        mnuEconoFar_Reportes.add(mnuReportes_VentasLinea);
        mnuReportes_VentasHora.setText("6. Ventas por hora");
        mnuReportes_VentasHora.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasHora.setMnemonic('8');
        mnuReportes_VentasHora.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasHora_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_VentasHora);
        mnuReportes_VentasDiaMes.setText("7 Ventas por d\u00eda del mes");
        mnuReportes_VentasDiaMes.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasDiaMes.setMnemonic('9');
        mnuReportes_VentasDiaMes.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_VentasDiaMes_actionPerformed(e);
                }
            });
        mnuEconoFar_Reportes.add(mnuReportes_VentasDiaMes);
        mnuEconoFar_Reportes.add(jMenuItem10);
        mnuEconoFar_Reportes.add(jMenuItem11);
        mnuEconoFar_Reportes.add(jMenuItem12);
        mnuReportes_FaltaCero.setText("10. Productos Falta Cero");
        mnuReportes_FaltaCero.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_FaltaCero.setMnemonic('0');
        mnuReportes_FaltaCero.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_FaltaCero_actionPerformed(e);
                }
            });
        //mnuEconoFar_Reportes.add(mnuReportes_FaltaCero);
        mnuReportes_ProductosABC.setText("11. Productos ABC");
        mnuReportes_ProductosABC.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ProductosABC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ProductosABC_actionPerformed(e);
                }
            });
        //mnuEconoFar_Reportes.add(mnuReportes_ProductosABC);
        mnuReportes_UnidVtaLocal.setText("8. Unidad Venta Local");
        mnuReportes_UnidVtaLocal.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_UnidVtaLocal.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_UnidVtaLocal_actionPerformed(e);
                }
            });
        //mnuEconoFar_Reportes.add(mnuReportes_UnidVtaLocal);
        mnuReportes_ProdSinVtaNDias.setText("13. Productos Sin Ventas en 90 Dias");
        mnuReportes_ProdSinVtaNDias.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ProdSinVtaNDias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ProdSinVtaNDias_actionPerformed(e);
                }
            });
        //mnuEconoFar_Reportes.add(mnuReportes_ProdSinVtaNDias);
        mnuReportes_ListPsicotropicos.setText("14. Registro de Psicotropicos");
        mnuReportes_ListPsicotropicos.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ListPsicotropicos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ListPsicotropicos_actionPerformed(e);
                }
            });
        //mnuEconoFar_Reportes.add(mnuReportes_ListPsicotropicos);
        mnuReportes_ListRecetario.setText("15. Kardex de Recetario");
        mnuReportes_ListRecetario.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ListRecetario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ListRecetario_actionPerformed(e);
                }
            });
        mnuCE_ReprocesaConcil.setText("5. Reprocesar conciliaciones");
        mnuCE_ReprocesaConcil.setFont(new Font("SansSerif", 0, 11));
        mnuCE_ReprocesaConcil.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCE_ReprocesaConcil_actionPerformed(e);
                }
            });
        mnuInventario_RecepcionRM.setText("3. Recepcion Recetario Magistral");
        mnuInventario_RecepcionRM.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_RecepcionRM_actionPerformed(e);
                }
            });
        prueba.setText("prueba impresion guia");
        prueba.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    prueba_actionPerformed(e);
                }
            });

        mnuInventario_StockNegativo.setText("10.Stock Negativo");
        mnuInventario_StockNegativo.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_StockNegativo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuInventario_StockNegativo_actionPerformed(e);
                }
            });

        mnuCaja_PedidoPinpad.setText("11.Pedidos pagados con Pinpad");
        mnuCaja_PedidoPinpad.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_PedidoPinpad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCaja_PedidoPinpad_actionPerformed(e);
                }
            });

        mnuReportes_ListGuias.setText("16. Reporte de Gu�as ");
        mnuReportes_ListGuias.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ListGuias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ListGuias_actionPerformed(e);
                }
            });

        // MODULO CAJA ELECTRONICA
        //mnuEconoFar_Reportes.add(mnuReportes_ListPsicotropicos);
        //mnuEconoFar_Reportes.add(mnuReportes_ListRecetario);
        //mnuEconoFar_Reportes.add(mnuReportes_ListGuias);

        jMenuItem2.setText("jMenuItem2");
        lblLogo_empresa.setBounds(new Rectangle(20, 40, 985, 425));
        lblMensajeUsuario.setText("Bienvenido :    Dia : dd/mm/yyyy");
        lblMensajeUsuario.setBounds(new Rectangle(10, 0, 775, 25));
        lblMensajeUsuario.setFont(new Font("Bookman Old Style", 2, 18));
        lblMensajeUsuario.setForeground(new Color(0, 132, 198));
        lblMensajeUsuario.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jMenu2.setText("Mantenimientos");
        jMenuItem5.setText("Tipo de Cambio");
        jMenuItem5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem5_actionPerformed(e);
                }
            });
        jMenuItem6.setText("Valor IGV");
        jMenuItem6.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem6_actionPerformed(e);
                }
            });
        jMenuItem7.setText("Producto");
        jMenuItem7.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem7_actionPerformed(e);
                }
            });
        jMenuItem8.setText("Pack");
        jMenuItem8.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem8_actionPerformed(e);
                }
            });
        jMenuItem9.setText("Cliente /  Proveedor");
        jMenuItem9.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem9_actionPerformed(e);
                }
            });
        jMenuItem10.setText("8. Resumen por Vendedor");
        jMenuItem10.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nuevoVendedor_actionPerformed(e);
                }
            });
        jMenuItem11.setText("9. Resumen por Forma Pago");
        jMenuItem11.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    rptnuevo_formapago_action(e);
                }
            });
        jMenuItem12.setText("10.Resumen por Cliente");
        jMenuItem12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jMenuItem4_actionPerformed(e);
                }
            });
        mnuCE_Prosegur.setText("3. Portavalor");
        mnuCE_Prosegur.setFont(new Font("SansSerif", 0, 11));
        mnuCE_Prosegur.setEnabled(false);
        mnuCE_Prosegur.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCE_Prosegur_actionPerformed(e);
                }
            });
        //mnuEconofar_CajaElectronica.add(mnuCE_Prosegur);

        //09.09.2013 wvillagomez
        mnuCE_PagoSencilloETV.setText("4. Fondo de Sencillo ETV");
        mnuCE_PagoSencilloETV.setFont(new Font("SansSerif", 0, 11));
        mnuCE_PagoSencilloETV.setMnemonic('4');
        //mnuEconofar_CajaElectronica.add(mnuCE_PagoSencilloETV);
        //mnuEconofar_CajaElectronica.add(mnuCE_ReprocesaConcil);
        mnuCE_ReciboSencillo.setText("1. Recibo de Sencillo");
        mnuCE_ReciboSencillo.setFont(new Font("SansSerif", 0, 11));
        mnuCE_ReciboSencillo.setMnemonic('1');
        mnuCE_ReciboSencillo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCE_ReciboSencillo_actionPerformed(e);
                }
            });
        mnuCE_PagoSencilloETV.add(mnuCE_ReciboSencillo);
        mnuCE_PagoSencillo.setText("2. Pago de Sencillo");
        mnuCE_PagoSencillo.setFont(new Font("SansSerif", 0, 11));
        mnuCE_PagoSencillo.setMnemonic('2');
        mnuCE_PagoSencillo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuCE_PagoSencillo_actionPerformed(e);
                }
            });
        mnuCE_PagoSencilloETV.add(mnuCE_PagoSencillo);

        // MODULO CONTROL INGRESO
        mnuEconoFar_ControlIngreso.setText("Control de Ingreso");
        mnuEconoFar_ControlIngreso.setMnemonic('d');
        mnuEconoFar_ControlIngreso.setFont(new Font("SansSerif", 0, 11));
        //mnuPtoVenta.add(mnuEconoFar_ControlIngreso);
        mnuIngreso.setText("1. Ingreso");
        mnuIngreso.setFont(new Font("SansSerif", 0, 11));
        mnuIngreso.setMnemonic('1');
        mnuIngreso.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuIngreso_actionPerformed(e);
                }
            });
        mnuEconoFar_ControlIngreso.add(mnuIngreso);
        mnuIngresoTemperatura.setText("2. Ingreso Temperatura");
        mnuIngresoTemperatura.setFont(new Font("SansSerif", 0, 11));
        mnuIngresoTemperatura.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mmuIngresoTemperatura_actionPerformed(e);
                }
            });
        mnuEconoFar_ControlIngreso.add(mnuIngresoTemperatura);

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
        mnuEconoFar_Salir.add(mnuSalir_SalirSistema);

        mnuEconoFar_Ayuda.setText("?");
        mnuEconoFar_Ayuda.setFont(new Font("SansSerif", 0, 11));
        mnuAyuda_AcercaDe.setText("Acerca de ...");
        mnuAyuda_AcercaDe.setFont(new Font("SansSerif", 0, 11));
        mnuAyuda_AcercaDe.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuAyuda_AcercaDe_actionPerformed(e);
                }
            });
        mnCaja_ReimpresionLote_Pinpad_MC.setText("7. Reimpresi�n Lote Mastercard");
        mnCaja_ReimpresionLote_Pinpad_MC.setFont(new Font("SansSerif", 0, 11));
        mnCaja_ReimpresionLote_Pinpad_MC.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnCaja_ReimpresionLote_Pinpad_MC_actionPerformed(e);
                }
            });
        /*mnuReportes_ListPsicotropicos.setText("14. Registro de Psicotropicos");
        mnuReportes_ListPsicotropicos.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ListPsicotropicos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ListPsicotropicos_actionPerformed(e);
                }
            });*/

        /*mnuReportes_ListRecetario.setText("15. Kardex de Recetario");
        mnuReportes_ListRecetario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ListRecetario_actionPerformed(e);
                }
            });
        mnuReportes_ListRecetario.setFont(new Font("SansSerif", 0, 11));*/

        mnuEconoFar_Ayuda.add(mnuAyuda_AcercaDe);
        //mnuPtoVenta.add(mnuEconoFar_Ayuda);

        // Se desconoce el uso del codigo
        /*lblLogo.setBounds(new Rectangle(615, 395, 160, 145));
        lblLogo.setFont(new Font("SansSerif", 0, 11));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        btnRevertir.setText("Revertirx");
        btnRevertir.setMnemonic('x');
        btnRevertir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnRevertir_actionPerformed(e);
                }
            });
        txtRevertir.setBounds(new Rectangle(0, 540, 70, 10));
        txtRevertir.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        txtRevertir.setLengthText(2);
        txtRevertir.setFont(new Font("SansSerif", 1, 1));
        txtRevertir.setForeground(Color.WHITE);
        txtRevertir.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtRevertir_keyPressed(e);
                }
            });
        lblMensaje.setText("EN PRUEBAS");
        lblMensaje.setBounds(new Rectangle(155, 20, 515, 60));
        lblMensaje.setFont(new Font("Serif", Font.BOLD, 60));
        lblMensaje.setVisible(false);*/
        /*pnlEconoFar.add(lblMensaje, null);
        pnlEconoFar.add(txtRevertir, null);
        pnlEconoFar.add(btnRevertir, null);
        pnlEconoFar.add(lblLogo, null);*/
        pnlEconoFar.add(lblMensajeUsuario, null);
        pnlEconoFar.add(lblLogo_empresa, null);
        this.getContentPane().add(pnlEconoFar, BorderLayout.CENTER);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        eliminaCodBarra();
        eliminaBoletaTxt();

        readFileFarmaVentaProperties();
        cargaIcono();

        //muestraLogin();
        muestraUser();
        inicializa();

        cargaDireccionFiscal();
        obtieneIndDireMat();
        //ERIOS 12.09.2013 Obtiene indicador direccion local
        obtieneIndDireLocal();
        //ERIOS 12.09.2013 Obtiene indicador de registro de venta restringida
        obtieneIndRegistroVentaRestringida();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */
    
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e)
    {
        
        VariablesModuloVentas.tableModelListaGlobalProductos = new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,0);
        
        
        
      DlgProcesar dlgProcesar = new DlgProcesar(this, "", true);
      dlgProcesar.mostrar();
      if(FarmaVariables.vAceptar){
      
      mnuReportes_ProdSinVtaNDias.setText("13. Productos Sin Ventas en "+
                                           VariablesPtoVenta.vNumeroDiasSinVentas.trim()
                                           + " Dias"); 
      }
    }
    
    private void mnuSalir_SalirSistema_actionPerformed(ActionEvent e) {
      salirSistema();
    }


   
    private void mnuCaja_CobrarPedido_actionPerformed(ActionEvent e)
    {
      DlgFormaPago dlgFormaPago = new DlgFormaPago(this,"",true);
      dlgFormaPago.setVisible(true);
      if(!FarmaVariables.vAceptar) verificaRolUsuario();
    }

    private void mnuCaja_AnularComprobante_actionPerformed(ActionEvent e)
    {
      DlgAnularPedidoComprobante dlgAnularPedidoComprobante = new DlgAnularPedidoComprobante(this,"",true);
      dlgAnularPedidoComprobante.setVisible(true);
    }

    private void mnuCaja_AperturarCaja_actionPerformed(ActionEvent e)
    {
                  VariablesCaja.vTipMovCaja = ConstantsCaja.MOVIMIENTO_APERTURA;
                  DlgMovCaja dlgMovCaja = new DlgMovCaja(this, "", true);
                  try {
                          dlgMovCaja.validarParamsUser();
        dlgMovCaja.verificaAperturaCaja();
                          dlgMovCaja.setVisible(true);
                  } catch (SQLException ex) {
        String mensaje="";
        if(ex.getErrorCode()==20011) mensaje="El usuario no posee ninguna caja activa asociada.";
        else if(ex.getErrorCode()==20012) mensaje="La caja del usuario ya se encuentra aperturada.";
        else if(ex.getErrorCode()==20013) mensaje="La caja del usuario ya se encuentra cerrada.";
        else if(ex.getErrorCode()==20020) mensaje="No puede aperturar una caja cuando ya existe una fecha de \n"+
                                                  "cierre de dia de venta registrada para el dia de hoy.";
        else mensaje=ex.getMessage();

                          Object obj = new Object();
                          obj = null;
                          FarmaUtility.showMessage(dlgMovCaja,mensaje, obj);
                          dlgMovCaja.dispose();
                  }
    }

    private void mnuCaja_CerrarCaja_actionPerformed(ActionEvent e)
    {
                  VariablesCaja.vTipMovCaja = ConstantsCaja.MOVIMIENTO_CIERRE;
                  DlgMovCaja dlgMovCaja = new DlgMovCaja(this, "", true);
                  try {
                          dlgMovCaja.validarParamsUser();
                          dlgMovCaja.setVisible(true);
                  } catch (SQLException ex) {
                   String mensaje="";

          if(ex.getErrorCode()==20011){mensaje="El usuario no posee ninguna caja activa asociada.";}
          else if(ex.getErrorCode()==20012){mensaje="La caja del usuario ya se encuentra aperturada.";}
          else if(ex.getErrorCode()==20013){mensaje="La caja del usuario ya se encuentra cerrada.";}
          else {mensaje=ex.getMessage();}

                          Object obj = new Object();
                          obj = null;
                          FarmaUtility.showMessage(dlgMovCaja, mensaje, obj);
                          dlgMovCaja.dispose();
                  }
    }

    private void mnuInventario_GuiaEntrada_actionPerformed(ActionEvent e)
    {
      DlgGuiasIngresosRecibidas dlgGuiasIngresosRecibidas = new DlgGuiasIngresosRecibidas(this,"",true);
      dlgGuiasIngresosRecibidas.setVisible(true);
    }


    private void mnuInventario_Transf_local_actionPerformed(ActionEvent e)
    {
      DlgTransferenciasRealizadas dlgTransferenciasRealizadas = new DlgTransferenciasRealizadas(this,"",true);
      dlgTransferenciasRealizadas.setVisible(true);
    }
    
    private void mnuInventario_Transf_delivery_actionPerformed(ActionEvent e)
    {     /*  DlgListaTransfPendientes dlgListaTranfPendientes = new DlgListaTransfPendientes(this,"",true);
      dlgListaTranfPendientes.setVisible(true); */
    DlgTransferenciasLocal dlgTransferenciasLocal = new DlgTransferenciasLocal(this,"",true);
    dlgTransferenciasLocal.setVisible(true);
    }
    private void mnuInventario_Transf_guias_actionPerformed(ActionEvent e)
    {
   
        DlgListadoGuias dlgGuiasSalida =new DlgListadoGuias(this,"",true);
        dlgGuiasSalida.setVisible(true);
    }

    private void mnuInventario_PedidoReposicion_actionPerformed(ActionEvent e)
    {
        DlgPedidoReposicionRealizados dlgPedidoReposicionRealizados = new DlgPedidoReposicionRealizados(this,"",true);
        dlgPedidoReposicionRealizados.setVisible(true);
    }

    private void jMenuItem4_actionPerformed(ActionEvent e)
    {
          //DlgRecepcionProductosGuias dlgRecepcionProductosGuias = new DlgRecepcionProductosGuias(this,"",true);
          //dlgRecepcionProductosGuias.setVisible(true);      
          DlgRptPorCliente dlgRegistroVentas = new DlgRptPorCliente(this, "", true);
          dlgRegistroVentas.setVisible(true);
    }


    private void jMenuItem4_DIGEMID(ActionEvent e)
    {

      DlgListaProdDIGEMID objDIGEMID=new DlgListaProdDIGEMID(this,"",true); //ASOSA 04.02.2010
      objDIGEMID.setVisible(true);
    }
    private void mnuCaja_ConfigurarCaja_actionPerformed(ActionEvent e)
    {
    int indIpValida=0;
    try
    {
      indIpValida = DBPtoVenta.verificaIPValida();
      if( indIpValida == 0 )
        FarmaUtility.showMessage(this,"La estaci�n actual no se encuentra autorizada para efectuar la operaci�n. ", null);
      else{
        DlgConfiguracionComprobante dlgConfiguracionComprobante = new DlgConfiguracionComprobante(this, "", true);
        dlgConfiguracionComprobante.setVisible(true);
        FarmaVariables.vAceptar = false;
      }
    } catch(SQLException ex)
    {
        log.error("",ex);
      FarmaUtility.showMessage(this,"Error al validar IP de Configuracion de Comprobantes.\n" + ex, null);
      indIpValida=0;
    }
    }

    private void mnuCaja_PedidoCompleto_actionPerformed(ActionEvent e)
    {
    DlgAnularPedido dlgAnularPedido = new DlgAnularPedido(this,"",true);
    dlgAnularPedido.setVisible(true);
    }

    private void mnuAdministracion_Mov_actionPerformed(ActionEvent e)
    {
    DlgMovimientosCaja dlgMovimientosCaja = new DlgMovimientosCaja(this,"",true);
    dlgMovimientosCaja.setVisible(true);
    }
    
    private void mnuTomaInventario_Historico_actionPerformed(ActionEvent e)
    {
      DlgListaHistoricoTomas dlgListaHistoricoTomas = new DlgListaHistoricoTomas(this, "", true);
      dlgListaHistoricoTomas.setVisible(true);
    }


    private void mnuInventario_Kardex_actionPerformed(ActionEvent e)
    {
      DlgKardex dlgKardex = new DlgKardex(this,"",true);
      dlgKardex.setVisible(true);
    }

    private void jMenuItem1_actionPerformed(ActionEvent e)
    {
        
        DlgRegistroVentas dlgRegistroVentas = new DlgRegistroVentas(this, "", true);
        //DlgRptNuevo dlgRegistroVentas = new DlgRptNuevo(this, "", true);
        dlgRegistroVentas.setVisible(true);
}
    
    private void mnuUsuarios_CambioClave_actionPerformed(ActionEvent e)
    {
                DlgCambioClave dlgCambioClave = new DlgCambioClave(this, "", true);
                dlgCambioClave.setVisible(true);
    }

    private void mnuUsuarios_CambioUsuario_actionPerformed(ActionEvent e)
    {
                muestraLoginCambioUser();
                
    this.repaint();
    }

    private void mnuMantenimiento_Usuarios_actionPerformed(ActionEvent e)
    {
                DlgListaUsuarios dlgListaUsuarios = new DlgListaUsuarios(this, "", true);
                dlgListaUsuarios.setVisible(true);
    }

    private void mnuMantenimiento_Cajas_actionPerformed(ActionEvent e)
    {
                DlgListaCajas dlgListaCajas = new DlgListaCajas(this, "", true);
                dlgListaCajas.setVisible(true);
    }

    private void mnuMantenimiento_Impresoras_actionPerformed(ActionEvent e)
    {
                DlgListaImpresoras dlgListaImpresoras = new DlgListaImpresoras(this,"", true);
                dlgListaImpresoras.setVisible(true);
    }

    private void mnuTomaInVentario_ItemsXLab_actionPerformed(ActionEvent e)
    {
    DlgListaLaboratorios  dlgListaLaboratorios = new DlgListaLaboratorios(this,"", true);
                dlgListaLaboratorios.setVisible(true);
    }
    
    private void mnuMovCaja_RegistrarVentas_actionPerformed(ActionEvent e)
    {
      VariablesPtoVenta.vTipOpMovCaja=ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA;
      DlgMovimientosCaja  dlgMovimientosCaja = new DlgMovimientosCaja(this,"", true);
                  dlgMovimientosCaja.setVisible(true);
    }

    private void mnuMovCaja_ConsultarMov_actionPerformed(ActionEvent e)
    {
      VariablesPtoVenta.vTipOpMovCaja=ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_CONSULTA;
      DlgMovimientosCaja  dlgMovimientosCaja = new DlgMovimientosCaja(this,"", true);
                  dlgMovimientosCaja.setVisible(true);
    }

    private void mnuCaja_CorreccionComprobantes_actionPerformed(ActionEvent e)
    {
        DlgVerificacionComprobantes  dlgVerificacionComprobantes = new DlgVerificacionComprobantes(this,"", true);
                  dlgVerificacionComprobantes.setVisible(true);
    }

    private void mnuMantenimiento_Clientes_actionPerformed(ActionEvent e)
    {
    DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (this,"",true);
    VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_N;
    dlgBuscaClienteJuridico.setVisible(true);
    }
    
    private void mnuCaja_ReimpresionPedido_actionPerformed(ActionEvent e)
    {
      DlgPedidosPendientesImpresion dlgPedidosPendientesImpresion = new DlgPedidosPendientesImpresion(this,"", true);
                  dlgPedidosPendientesImpresion.setVisible(true);
    }
    
    private void mnuVentas_DistribucionGratuita_actionPerformed(ActionEvent e)
    {
      distribucionGratuita();
    }
    
    private void mnuReportes_DetalleVentas_actionPerformed(ActionEvent e)
    {
      DlgDetalleVentas  dlgDetalleVentas = new DlgDetalleVentas(this,"", true);
                  dlgDetalleVentas.setVisible(true);
    }
    

    private void  mnuReportes_VentasVendedor_Total_actionPerformed(ActionEvent e){
        
         VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA = ConstantsReporte.ACCION_MOSTRAR_TOTALES;
         
         DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
         dlgVentasPorVendedor.setVisible(true);
         
         
     }
    
      private void mnuReportes_VentasVendedor_Mezon_actionPerformed(ActionEvent e) {
          
          VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_MEZON;
          
          DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
          dlgVentasPorVendedor.setTitle("Ventas por Vendedor Meson");
          dlgVentasPorVendedor.setVisible(true);
      
      }
    
      private void mnuReportes_VentasVendedor_Delivery_actionPerformed(ActionEvent e) {
      
          VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_DELIVERY;
          
          DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
          dlgVentasPorVendedor.setTitle("Ventas por Vendedor Delivery");
          dlgVentasPorVendedor.setVisible(true);
          
          
      }
      
      private void mnuReportes_VentasVendedor_Institucional_actionPerformed(ActionEvent e) 
      {
          VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_INSTITUCIONAL;
          
          DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
          dlgVentasPorVendedor.setTitle("Ventas por Vendedor Institucional");
          dlgVentasPorVendedor.setVisible(true);
              
      }

    private void mnuReportes_VentasProducto_actionPerformed(ActionEvent e)
    {
      DlgVentasPorProducto dlgVentasPorProducto = new DlgVentasPorProducto(this,"", true);
      dlgVentasPorProducto.setVisible(true);
          
       
    }

    private void mnuReportes_VentasDiaMes_actionPerformed(ActionEvent e)
    {
      VariablesReporte.vOrdenar = ConstantsReporte.vVentasDiaMes;
      DlgVentasDiaMes dlgVentasDiaMes=new DlgVentasDiaMes(this,"",true);
      dlgVentasDiaMes.setVisible(true);
    }

    private void mnuReportes_ResumenVentaDia_actionPerformed(ActionEvent e)
    {
        DlgVentasResumenPorDia  dlgVentasResumenPorDia = new DlgVentasResumenPorDia(this,"",true);
        dlgVentasResumenPorDia.setVisible(true);
    }

    private void mnuReportes_VentasHora_actionPerformed(ActionEvent e)
    {
      DlgVentasPorHora dlgVentasPorHora = new DlgVentasPorHora(this,"",true);
      dlgVentasPorHora.setVisible(true);
    }
    
    private void mnuAdministracion_RegViajero_actionPerformed(ActionEvent e)
    {
     DlgProcesaViajero dlgProcesaViajero = new DlgProcesaViajero(this, "", true);
     dlgProcesaViajero.setVisible(true);
    }

    private void mnuInventario_Guias_actionPerformed(ActionEvent e)
    {
      DlgGuiasRemision dlgGuiasRemision = new DlgGuiasRemision(this,"",true);
      dlgGuiasRemision.setVisible(true);
    }

    private void mnuReportes_FaltaCero_actionPerformed(ActionEvent e)
    {
      DlgProductoFaltaCero dlgProductoFaltaCero = new DlgProductoFaltaCero(this,"",true);
      dlgProductoFaltaCero.setVisible(true);
    }

    private void mnuCaja_PruebaImpresora_actionPerformed(ActionEvent e)
    {
      DlgPruebaImpresora dlgPruebaImpresora = new DlgPruebaImpresora(this,"",true);
      dlgPruebaImpresora.setVisible(true);
    }

    private void mnuMantenimiento_Parametros_actionPerformed(ActionEvent e)
    {
      DlgParametros dlgParametros = new DlgParametros(this,"",true);
      dlgParametros.setVisible(true);
    }
    
    
      private void mnuMantenimiento_Carne_actionPerformed(ActionEvent e)
      {
        DlgBuscaTrabajadorLocal dlgBuscaTrab = new DlgBuscaTrabajadorLocal(this,"",true);
        dlgBuscaTrab.setTitle("Lista de Trabajadores Local");
        dlgBuscaTrab.setVisible(true);
      }

    private void mnuReportes_ProductosABC_actionPerformed(ActionEvent e)
    {
      DlgProductosABC dlgProductosABC = new DlgProductosABC(this,"",true);
      dlgProductosABC.setVisible(true);
    }

    private void mnuInventario_Ajustes_actionPerformed(ActionEvent e)
    {
      DlgAjustesporFecha dlgAjustesporFecha = new DlgAjustesporFecha(this,"",true);
      dlgAjustesporFecha.setVisible(true);
    }

    private void mnuInventario_RecepcionLocales_actionPerformed(ActionEvent e)
    {
      DlgTransferenciasLocal dlgTransferenciasLocal = new DlgTransferenciasLocal(this,"",true);
      dlgTransferenciasLocal.setVisible(true);
    }

    private void mnuInventario_Devolucion_actionPerformed(ActionEvent e)
    {
      DlgDevolucionesLista dlgListaDevoluciones = new DlgDevolucionesLista(this,"",true);
      dlgListaDevoluciones.setVisible(true);
    }  
    
      private void mnuInventario_RecepcionMerDirec_actionPerformed(ActionEvent e)
      {
          DlgMercaderiaDirectaBuscar dlgMercaderiaDirectaBuscar = new DlgMercaderiaDirectaBuscar(this,"",true);
          dlgMercaderiaDirectaBuscar.setVisible(true);
      }  

    private void mnuCE_CierreTurno_actionPerformed(ActionEvent e)
    {
      DlgCierreCajaTurno dlgCierreCajaTurno = new DlgCierreCajaTurno(this, "", true);
      dlgCierreCajaTurno.setLocationRelativeTo(null);
      dlgCierreCajaTurno.setVisible(true);
    }

    private void mnuCE_CierreLocal_actionPerformed(ActionEvent e)
    {
      DlgHistoricoCierreDia dlgHistoricoCierreDia = new DlgHistoricoCierreDia(this, "", true);
      dlgHistoricoCierreDia.setVisible(true);
    }

    private void mnuInventarioCiclico_Toma_actionPerformed(ActionEvent e)
    {
      DlgListaTomasInventarioCiclico dlgListaTomasInventarioCiclico = new DlgListaTomasInventarioCiclico(this,"",true);
      dlgListaTomasInventarioCiclico.setVisible(true);
    }

    private void mnuInventario_Inicio_actionPerformed(ActionEvent e)
    {
      DlgInicioToma dlgInicioToma = new DlgInicioToma(this, "", true);
      dlgInicioToma.setVisible(true);
    }

    private void mnuInventario_Toma_actionPerformed(ActionEvent e)
    {
      DlgListaTomasInventario dlgListaTomasInventario = new DlgListaTomasInventario(this, "", true);
      dlgListaTomasInventario.setVisible(true);
    }

    private void mnuInventarioCiclico_Inicio_actionPerformed(ActionEvent e)
    {
      DlgInicioInvCiclico dlgInicioInvCiclico = new DlgInicioInvCiclico(this, "", true);
      dlgInicioInvCiclico.setVisible(true);
    }

    private void mnuVentas_DeliveryAutomatico_actionPerformed(ActionEvent e)
    {
      DlgUltimosPedidos dlgUltimosPedidos = new DlgUltimosPedidos(this, "", true);
      dlgUltimosPedidos.setVisible(true);
    }
    
    private void mnuVentas_MedidaPresion_actionPerformed(ActionEvent e)
    {
      DlgListaMedidaPresion dlgListaMedidaPresion = new DlgListaMedidaPresion(this, "", true);
      dlgListaMedidaPresion.setVisible(true);
    }
    
    private void mnuVentas_Correlativo_actionPerformed(ActionEvent e)
    {
      
        DlgConsultarRecargaCorrelativo_AS dlgRecargaCorrelativo = new DlgConsultarRecargaCorrelativo_AS(this,"Consulta de recarga por correlativo",true);
        dlgRecargaCorrelativo.setVisible(true);
    
    }
    
    private void mnuAdministracion_ControlHoras_actionPerformed(ActionEvent e)
    {
    DlgControlHoras dlgControlHoras= new DlgControlHoras(this, "", true);
    dlgControlHoras.setVisible(true);
    
    }

    private void mnuOtros_MantProbisa_actionPerformed(ActionEvent e)
    {
    DlgListaProbisa dlgListaProbisa = new DlgListaProbisa(this,"",true);
    dlgListaProbisa.setVisible(true);
    }
    
    private void mnuReportes_UnidVtaLocal_actionPerformed(ActionEvent e)
    {
    DlgUnidVtaLocal dlgUnidVtaLocal = new DlgUnidVtaLocal(this,"",true);
    dlgUnidVtaLocal.setVisible(true); 
    }
    
    private void mnuReportes_ProdSinVtaNDias_actionPerformed(ActionEvent e)
    {
    DlgProdSinVtaNDias dlgProdSinVtaNDias = new DlgProdSinVtaNDias(this,"",true);
    dlgProdSinVtaNDias.setVisible(true);
    }
    
    private void mnuPedidoAdicional_actionPerformed(ActionEvent e) {
        DlgPedidoReposicionAdicionalNuevo dlgPedidoAdicional = 
                        new DlgPedidoReposicionAdicionalNuevo(this,"",true);
       dlgPedidoAdicional.setVisible(true);
    }

    private void mnuPedidoEspecial_actionPerformed(ActionEvent e)
      {
        DlgListaPedidosEspeciales dlglistaPE=new DlgListaPedidosEspeciales(this,"",true);
        dlglistaPE.setVisible(true);
      
      }

    private void mmuIngresoTemperatura_actionPerformed(ActionEvent e) {
        DlgHistoricoTemperatura   dlghistorico=new DlgHistoricoTemperatura(this,"",true);
        dlghistorico.setVisible(true);
    }
    
    private void mnuInventarioDiario_Inicio_actionPerformed(ActionEvent e) {
        
        DlgInicioInveDiario   dlgInicioInveDiario=new DlgInicioInveDiario(this,"",true);
        dlgInicioInveDiario.setVisible(true);
        
    }

    private void mnuInventarioDiario_Toma_actionPerformed(ActionEvent e) {
        
        DlgListaTomasInventarioDiario   dlgListaTomasInventarioDiario=new DlgListaTomasInventarioDiario(this,"",true);
        dlgListaTomasInventarioDiario.setVisible(true);
        
    }

    private void mnuInventarioDiario_Diferencia_actionPerformed(ActionEvent e) {
        
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) || validarAsistAudit()) {
        DlgListaDiferenciasToma dlgListaDiferenciasToma=new  DlgListaDiferenciasToma(this,"",true);
        dlgListaDiferenciasToma.setVisible(true);
        }
        else {
            FarmaUtility.showMessage(this, 
                                     "No posee privilegios suficientes para acceder a esta opci�n", 
                                     null);
        }
        
    }

    private void mnuMantenimiento_MaquinaIP_actionPerformed(ActionEvent e) {
    
        DlgListaIPSImpresora DlgIP=new  DlgListaIPSImpresora(this,"",true);
        DlgIP.setVisible(true);
    
    }
    
    private void mnuMantenimiento_ImpresoraTermica_actionPerformed(ActionEvent e) {
        
        DlgListaImpresoraTermCreaMod dlgLstImprTermCreaMod=new  DlgListaImpresoraTermCreaMod(this,"",true);
        dlgLstImprTermCreaMod.setVisible(true);
        
    
    }
    
    private void jMenuItem2_actionPerformed(ActionEvent e) {
        
        DlgPedidoPendienteDiario dlgPedidosPendientes = new DlgPedidoPendienteDiario(this,"",true);
        dlgPedidosPendientes.setVisible(true);        
    }
    
    
    private void reporteFormaPago_actionPerformed(ActionEvent e) {
        
        DlgRptFPagoVendedor dlgPedidosPendientes = new DlgRptFPagoVendedor(this,"",true);
        dlgPedidosPendientes.setVisible(true);        
    }

    private void mnuCaja_ReimpresionTicketsAnulados_actionPerformed(ActionEvent e) {
        
        DlgListaTicketsAnulados dlgListaTicketsAnulados=new  DlgListaTicketsAnulados(this,"",true);
        dlgListaTicketsAnulados.setVisible(true);
    }

    private void mnuInventario_Recepcion_actionPerformed(ActionEvent e) {        
            
            DlgHistoricoRecepcion dlgRecepcion=new  DlgHistoricoRecepcion(this,"",true);
            dlgRecepcion.setVisible(true);
        
       
    }
    
    private void mnuIngreso_actionPerformed(ActionEvent e)
    {
      DlgControlIngreso dlgControlIngreso = new DlgControlIngreso(this,"",true);
      dlgControlIngreso.setVisible(true);  
    }
    
    private void mnuIngTransportista_actionPerformed(ActionEvent e) {
        DlgListaTransportistas dlgListaTransp = new DlgListaTransportistas(this,"",true);
        dlgListaTransp.setVisible(true);
    }
    
    private void mnuFondoSencillo_actionPerformed(ActionEvent e) {
        if (UtilityFondoSencillo.indActivoFondo()) {
            
            DlgListadoCajeros dlgListado = new DlgListadoCajeros(this,"",true);
            dlgListado.setVisible(true);
        } else {
            FarmaUtility.showMessage(this, 
                                     "A�n no se encuentra habilitada est� opci�n en su Local.", 
                                     null);
        }
    }
    
    private void mnuCE_Prosegur_actionPerformed(ActionEvent e) {
        DlgListaRemito dlgListaRem=new DlgListaRemito(this,"",true);
         dlgListaRem.setVisible(true);
    }

    private void mnuCaja_ControlSobresParciales_actionPerformed(ActionEvent e) {
        DlgControlSobres dlgcontrol=new DlgControlSobres(this,"",true);
         dlgcontrol.setVisible(true);
    }

    private void mnuPrecioCambProd_actionPerformed(ActionEvent e) {
        DlgPrecioProdCambiados dlgPrecioCambiad= new DlgPrecioProdCambiados(this,"",true);
        dlgPrecioCambiad.setVisible(true);
    }
    
    //01.10.2013 CVILCA
    private void mnuProdImpresion_actionPerformed(ActionEvent e) {
        DlgProdImpresion dlgProdImpresion= new DlgProdImpresion(this,"",true);
        dlgProdImpresion.setVisible(true);
    }
    
    private void mnuCaja_CMR_actionPerformed(ActionEvent e) {
        DlgRecaudacionCmr dlgRecaudaCmr = new DlgRecaudacionCmr(this,"",true);
        dlgRecaudaCmr.setVisible(true);
    }

    private void jMenuItem5_actionPerformed(ActionEvent e) {
        /*DlgRecaudacionCitibank dlgRecaudCitibank = new DlgRecaudacionCitibank(this,"",true);
        dlgRecaudCitibank.setVisible(true);*/
        
        DlgMantTipoDeCambio dlgResumenPedido = new DlgMantTipoDeCambio(this,"",true);
              dlgResumenPedido.setVisible(true);
    }

    private void mnuCaja_Claro_actionPerformed(ActionEvent e) {
        DlgRecaudacionClaro dlgRecaudaClaro = new DlgRecaudacionClaro(this,"",true);
        dlgRecaudaClaro.setVisible(true);
    }

    private void mnuCaja_Prestamos_Citibank_actionPerformed(ActionEvent e) {
        DlgRecaudacionPrestamosCitibank dlgRecadaPrestaCitibank = new DlgRecaudacionPrestamosCitibank(this,"",true);
        dlgRecadaPrestaCitibank.setVisible(true);
    }
    
    private void mnuCaja_Ripley_actionPerformed(ActionEvent e) {
        DlgRecaudacionRipley dlgRecaudadaRipley = new DlgRecaudacionRipley(this,"",true);
        dlgRecaudadaRipley.setVisible(true);
    }

    private void mnuCaja_Registro_Recaudaciones_actionPerformed(ActionEvent e) {
        DlgRecaudacionesRealizadas dlgRecaudaRealizadas = new DlgRecaudacionesRealizadas(this,"",true);
        dlgRecaudaRealizadas.setTipoCobro(ConstantsRecaudacion.TIPO_COBRO_RECAUDACION);
        dlgRecaudaRealizadas.setVisible(true);
    }
    
    private void mnuCaja_Registro_Venta_CMR_actionPerformed(ActionEvent e) {
        DlgRecaudacionesRealizadas dlgRecaudaRealizadas = new DlgRecaudacionesRealizadas(this,"",true);
        dlgRecaudaRealizadas.setTipoCobro(ConstantsRecaudacion.TIPO_COBRO_VENTA_CMR);
        dlgRecaudaRealizadas.setVisible(true);

    }

    
    private void mnuCaja_Pinpad_Visa_actionPerformed(ActionEvent e)
    {
        DlgTransaccionesPinpad dlgTransaccionesPinpad = new DlgTransaccionesPinpad(this,"",true);
        dlgTransaccionesPinpad.setVisible(true);
    }


    private void mnuCaja_AbrirGabeta_actionPerformed(ActionEvent e) {
        UtilityCaja.abrirGabeta(this,true);            
    }
    
    private void this_windowClosed(WindowEvent e) {
        eliminaCodBarra();    
        eliminaBoletaTxt();
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
  
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
  
  void muestraLogin() {
      
    //if ( readFileProperties() ) 
    if(true)
    {
      /*
      if(!validaNamePc()){
        FarmaUtility.showMessage(this,"Error al obtener el Nombre de la PC.", null);
        salirSistema();
      }
      try{
        int cantSesiones = DBPtoVenta.obtieneCantidadSesiones(FarmaVariables.vNamePc, FarmaVariables.vUsuarioBD);
        if(cantSesiones > 1){
          FarmaUtility.showMessage(this,"Ya existe una aplicacion iniciada en esta PC.\nPor favor comunicarse con el area de Sistemas", null);
          salirSistema();
        }
      } catch(SQLException sql)
      {
        FarmaUtility.showMessage(this,"Error al obtener cantidad de Sesiones Abiertas.\nPor favor comunicarse con el area de Sistemas", null);
        salirSistema();
      }
      */
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
          
          FarmaVariables.dlgLogin = dlgLogin;

          verificaRolUsuario();
            
          //inicio 
          //lapaz dubilluz 17.09.2010
                VariablesModuloVentas.tableModelListaGlobalProductos = new FarmaTableModel(ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,0);
          
          log.info("Inicio de Hilo");
          // crear y nombrar a cada subproceso
          
          
          //SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
          //SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
          
          VariablesModuloVentas.tableModelListaGlobalProductos.clearTable();

          try {
              DBModuloVenta.cargaListaProductosVenta(VariablesModuloVentas.tableModelListaGlobalProductos);
          } catch(Exception ef) {
                  //e.printStackTrace();
          }
          
          //SubProcesos subproceso3 = new SubProcesos("CARGA_IMP_TERMICA" );
          log.debug( "Iniciando subprocesos" );
          //SubProcesoFarmaVenta subproceso4 = new SubProcesoFarmaVenta(FarmaVariables.vNuSecUsu,this);    
                      
          //subproceso1.start();
          //subproceso2.start();
          //subproceso3.start();
          //subproceso4.start();
            
          log.info("Fin de Hilo");
          //fin
          //lapaz dubilluz 17.09.2010
          
          //JCORTEZ 04.09.09 Se valida cambio de clave para el usuario
          String  valida="";
          try{ valida=DBPtoVenta.validaCambioClave(); }catch(SQLException x){ log.error("",x);}

         /* log.info("cambiar password :"+valida);
         if(valida.trim().equalsIgnoreCase("S")){
         
            FarmaUtility.showMessage(this,"Usted debera cambiar su clave para poder entrar el sistema.", null);
            DlgCambioClave dlgcambio= new   DlgCambioClave(this,"",true);
            dlgcambio.setVisible(true);
            
            if(FarmaVariables.vAceptar) {
                FarmaVariables.dlgLogin = dlgLogin;
                recuperaStock();
            }else{
                salirSistema();
            }
            
         } else{
             FarmaVariables.dlgLogin = dlgLogin;
             recuperaStock();
         }*/
       
      }
    } else salirSistema();
  }
  
  private void recuperaStock (){
      log.info("NO SE RECUPERO STOCK COMPROMETIDO DESDE RESPALDO");
      /* try {
        // RECUPERANDO STOCK COMPROMETIDO
        DBPtoVenta.ejecutaRespaldoStock("","",ConstantsPtoVenta.TIP_OPERACION_RESPALDO_EJECUTAR,0,0,"");
        log.info("RECUPERO STOCK COMPROMETIDO DESDE RESPALDO");
        FarmaUtility.aceptarTransaccion();
        verificaRolUsuario();
      } catch (SQLException sqlException) {
        FarmaUtility.liberarTransaccion();
        log.error("",sqlException);
        FarmaUtility.showMessage(this,"Error al recuperar Stock de Respaldo.\nPonganse en contacto con el area de Sistemas", null);
        salirSistema();
      } */
  
  }

  private boolean validaNamePc(){
    FarmaVariables.vNamePc = FarmaUtility.getHostName();
    if(FarmaVariables.vNamePc.trim().length() == 0)
      return false;
    return true;
  }

  private boolean validaIpPc(){
    FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
    if(FarmaVariables.vIpPc.trim().length() == 0)
      return false;
    return true;
  }

    private void muestraUser() {
    
        String addon = " Usuario : ";
        addon = addon + FarmaVariables.vIdUsu;
        tituloBaseFrame = "29.10.2018";
        this.setTitle(tituloBaseFrame + " Sede : " + FarmaVariables.vDescCortaLocal + " | " + addon + " |  IP : " + FarmaVariables.vIpPc);
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

  private void generarPedido() {
    
  }

  private void verificaRolUsuario() { 
      
      Date vFecImpr = new Date();
      String fechaImpresion;
            
      String DATE_FORMAT = "dd/MM/yyyy";
         SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
          // log.debug("Today is " + sdf.format(vFecImpr));
         fechaImpresion =  sdf.format(vFecImpr);  
      
      lblMensajeUsuario.setText("Bienvenido "+ FarmaVariables.vNomUsu+" "+ FarmaVariables.vPatUsu+" "+FarmaVariables.vMatUsu+
                                "  / D�a : "+ fechaImpresion);
      
    opcionesComunes();    
    

    /*
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
        opcionesVendedor();        
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO) ) {
        opcionesCajero();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) ) {
        opcionesAdministrador();
    }
    
    if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CONTABILIDAD)||
        FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES)
      ){
        opcionesProsegur();        
    }        
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ) {
        opcionesAuditoria();      
    }
    
    if ( FarmaVariables.dlgLogin.verificaRol(ConstantsPtoVenta.ROL_ASISTENTE_AUDITORIA) ) {
        opcionesAsistenteAuditoria();
    }
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS) ) {
        opcionesOperador();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR) ) {
        opcionesInventariador();
    }
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES) )
    {
        opcionesReportes();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_INVENTARIO) )
    {
        opcionesLecturaInventario();      
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) )
    {
        opcionesSupervisorVentas();       
    }*/
    
    //bloquearOpcionesPorCompania();
    opcionesOperador();
  }
  
  private void validaOpcionesMenu(boolean pValor) {
      mnuVentas_DeliveryAutomatico.setEnabled(pValor);
      //TODO MedidaPresion
      //TODO Recargas
      //TODO InvDiario
      //mnuVentas_Receta.setEnabled(pValor);
      mnuCaja_AnularComprobante.setEnabled(pValor);
      mnuCaja_CorreccionComprobantes.setEnabled(pValor);
      mnuCaja_ReimpresionPedido.setEnabled(pValor);
      mnuCaja_Utilitarios.setEnabled(pValor);
        mnuCaja_PruebaImpresora.setEnabled(pValor);
        mnuCaja_Pinpad.setEnabled(pValor);
          mnuCaja_Pinpad_Visa.setEnabled(pValor);
          mnuCaja_Pinpad_Mastercard.setEnabled(pValor);
          mnuCaja_reporDet_Pinpad_MC.setEnabled(pValor);
          mnuCaja_reporTot_Pinpad_MC.setEnabled(pValor);
          mnuCaja_Reimpresion_Pinpad_MC.setEnabled(pValor);
          mnuCaja_AnulacionTran_Pinpad_Visa.setEnabled(pValor);
          mnCaja_ReimpresionLote_Pinpad_MC.setEnabled(pValor);
        mnuCaja_AbrirGabeta.setEnabled(pValor);
      //TODO mnuCaja_ReimpresionTicketsAnulados
      //TODO mnuCaja_ControlSobreParciales
      mnuCaja_Recaudacion.setEnabled(pValor);
        mnuCaja_CMR.setEnabled(pValor);
        mnuCaja_Citibank.setEnabled(pValor);
        mnuCaja_Claro.setEnabled(pValor);
        mnuCaja_Prestamos_Citibank.setEnabled(pValor);
        mnuCaja_Registro_Recaudaciones.setEnabled(pValor);
      
    // MODULO INVENTARIO
    mnuEconoFar_Inventario.setEnabled(pValor);
      mnuInventario_GuiaEntrada.setEnabled(pValor);
      mnuInventario_Kardex.setEnabled(pValor);
      mnuInventario_Transferencias.setEnabled(pValor);
        //mnuInventario_Transf_local
        //mnuInventario_Transf_delivery
      //mnuInventario_Mercaderia
        //mnuInventario_Recepcion
        mnuInventario_RecepcionProductos.setEnabled(pValor);
        //mnuInventario_IngTransportista
      //mnuInventario_PedidosLocales
        //mnuInventario_PedidoReposicion
        //mnuInventario_PedidoAdicional
        //mnuInventario_PedidoEspecial
      mnuInventario_Guias.setEnabled(pValor);
      mnuInventario_Ajustes.setEnabled(pValor);
      mnuInventario_RecepcionLocales.setEnabled(pValor);
      mnuInventario_MercaderiaDirecta.setEnabled(pValor);
        //mnuInventario_RecepcionMerDirec
        //mnuInventario_Devolucion      
      
    // MODULO TOMA INVENTARIO
    mnuEconoFar_TomaInventario.setEnabled(pValor);
      mnuTomaInventario_Tradicional.setEnabled(pValor);
        //mnuInventario_Inicio
        //mnuInventario_Toma
      mnuTomaInventario_Ciclico.setEnabled(pValor);
        //mnuInventarioCiclico_Inicio
        //mnuInventarioCiclico_Toma
      //mnuTomaInventario_Diario
        //mnuInventarioDiario_Toma
        //mnuInventarioDiario_Diferencia
      mnuTomaInventario_Historico.setEnabled(pValor);
      mnuTomaInVentario_ItemsXLab.setEnabled(pValor);
      
    // MODULO ADMINISTRACION
    mnuEconoFar_Administracion.setEnabled(pValor);
      mnuAdministracion_Usuarios.setEnabled(pValor);
      	mnuUsuarios_CambioUsuario.setEnabled(pValor);
      	mnuUsuarios_CambioClave.setEnabled(pValor);
      	mnuMantenimiento_Clientes.setEnabled(pValor);
        mnuMantenimiento_Parametros.setEnabled(pValor);
        //mnuMantenimiento_Carne
        //mnuMantenimiento_ImpresoraTermica
      mnuAdministracion_MovCaja.setEnabled(pValor);
      	mnuMovCaja_RegistrarVentas.setEnabled(pValor);
      	mnuMovCaja_ConsultarMov.setEnabled(pValor);
      mnuAdministracion_RegViajero.setEnabled(pValor);
      mnuAdministracion_ControlHoras.setEnabled(pValor);
      mnuAdministracion_Otros.setEnabled(pValor);
        //mnuOtros_MantProbisa
      mnuAdministracion_FondoSencillo.setEnabled(pValor);
      mnuAdministracion_ProdCambiados.setEnabled(pValor);
        mnuProdCambiados_PrecioCambProd.setEnabled(pValor);
                
    // MODULO REPORTES
    mnuEconoFar_Reportes.setEnabled(pValor);
      mnuReportes_RegistroVentas.setEnabled(pValor);
      mnuReportes_VentasVendedor.setEnabled(pValor);
        //mnuReportes_VentasVendedor_Total
        //mnuReportes_VentasVendedor_Mezon
        //mnuReportes_VentasVendedor_Delivery
        //mnuReportes_VentasVendedor_Institucional
      mnuReportes_DetalleVentas.setEnabled(pValor);
      mnuReportes_ResumenVentaDia.setEnabled(pValor);
      mnuReportes_VentasProducto.setEnabled(pValor);
      //mnuReportes_VentasConvenio.setEnabled(pValor);
      //mnuReportes_VentasLinea.setEnabled(pValor);
      mnuReportes_VentasHora.setEnabled(pValor);
      mnuReportes_VentasDiaMes.setEnabled(pValor);
      mnuReportes_FaltaCero.setEnabled(pValor);
      //mnuReportes_ProductosABC
      //mnuReportes_UnidVtaLocal
      //mnuReportes_ProdSinVtaNDias
      
    // MODULO CAJA ELECTRONICA
      mnuCE_Prosegur.setEnabled(pValor);
   
    //MODULO CONTROL INGRESO
    //mnuEconoFar_ControlIngreso
      //mnuIngreso
      mnuIngresoTemperatura.setEnabled(pValor);      
      
      //reportes nuevos
      //menuRptNuevos.setEnabled(pValor);
            
  }

   void muestraLoginCambioUser() {

    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
    dlgLogin.setVisible(true);
      if (FarmaVariables.vAceptar) {
          FarmaVariables.dlgLogin = dlgLogin;
          VariablesCaja.vVerificaCajero = true;
          verificaRolUsuario();
          if(FarmaVariables.vEconoFar_Matriz){
              mnuInventario_PedidoEspecial.setEnabled(false);
          }
              
      }
  }

  private void distribucionGratuita() {
    DlgResumenPedidoGratuito dlgResumenPedidoGratuito = new DlgResumenPedidoGratuito(this,"",true);
    dlgResumenPedidoGratuito.setVisible(true);
    while(FarmaVariables.vAceptar) distribucionGratuita();
    if(!FarmaVariables.vAceptar) verificaRolUsuario();
  }

  private void cargaVariablesBD()
  {
    //FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
    //FarmaVariables.vClaveBD = ConstantsPtoVenta.CLAVE_BD;
    //FarmaVariables.vSID = ConstantsPtoVenta.SID;
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
    
  private void salirSistema()
  {
      eliminaCodBarra(); 
      eliminaBoletaTxt();
    if(FarmaVariables.vEconoFar_Matriz)
      this.dispose();
    else
      System.exit(0);    
  }

  private void salir(WindowEvent e)
  {
    salirSistema();
  }

  private void inicializa(){
      try{
          VariablesPtoVenta.vIndRecepCiega = "N";
              //DBPtoVenta.obtieneIndicadorTipoRecepcionAlmacen();
      }
      catch(Exception sql){
          log.error("",sql);
          FarmaUtility.showMessage(this,"Ocurri� un error al obtener el indicador del tipo de recepci�n de almacen : \n"+ sql.getMessage(), null);
      }
      if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("S")){
          mnuInventario_Mercaderia.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          mnuInventario_Recepcion.setEnabled(true);
      }
      else{
          mnuInventario_Mercaderia.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          mnuInventario_Recepcion.setEnabled(false);
      }
      
      //pruebas de punto de venta
      //if (validaEsOperador()){
          if (validaCantidadPruebasCompleta()){
              lblMensaje.setVisible(true);
              pnlEconoFar.setBackground(Color.ORANGE);
              btnRevertir.setBackground(Color.ORANGE);
              txtRevertir.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
              txtRevertir.setBackground(Color.ORANGE);      
              btnRevertir.setForeground(Color.ORANGE); 
          }
      //}
  }
  
  private void eliminaCodBarra(){
    try{
            UtilityModuloVenta.eliminaImagenesCodBarra();
       }
    catch (Exception e) {
        log.error("",e);
    }  
  }
  
  private void eliminaBoletaTxt(){
      try{
            UtilityModuloVenta.eliminaArchivoTxt();
         }
      catch (Exception e) {
          log.error("",e);
      }  
  }

    private void btnRevertir_actionPerformed(ActionEvent e) {
        
        //JCORTEZ 19.01.10 Se verificara indicador para validar proceso de pruebas.
        if(validaIndicadorPruebas()){
        if (validaEsOperador()){
            DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            dlgLogin.setVisible(true);
                
            if ( FarmaVariables.vAceptar ){
               FarmaUtility.moveFocus(txtRevertir);            
            }   
        }
        }else
            FarmaUtility.showMessage(this,"La opci�n de Revertir no se encuentra activa.",null);       
        
    }

    private boolean validaIndicadorPruebas(){
    
        boolean resutl=false;
        String ind="";
        try {
            ind = DBPtoVenta.obtenerIndReverLocal();
            if((ind.trim()).equalsIgnoreCase("S")){
                resutl=true;
            }
        } catch (SQLException sql) {
            log.error("",sql);
        }
        return resutl;
    
    }
   

    private void txtRevertir_keyPressed(KeyEvent e) {
        log.debug("Entra a revertir");
        log.debug("tecla ini cio: "+e.getKeyCode() );
        
        if (e.getKeyCode() == KeyEvent.VK_INSERT ) {
        
            log.debug(":::JCORTEZ::::VALIDACION PROCESO REVERSION");
            //JCORTEZ 19.01.10 Se verificara indicador para validar proceso de pruebas.
            if(validaIndicadorPruebas()){        
            if (validaEsOperador()){
                DlgLogin dlgLogin1 = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                dlgLogin1.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
                dlgLogin1.setVisible(true);
                    
                if ( FarmaVariables.vAceptar ){
                    if (validaIndicadorRevertirLocal()){
                    //    if (validaCantidadPruebasCompleta()){
                        VariablesPtoVenta.vFechaInicioPruebas = obtieneFechaInicioPrueba();
                        if (!VariablesPtoVenta.vFechaInicioPruebas.equalsIgnoreCase("")){
                            boolean rptaRevertir = false;
                            rptaRevertir = JConfirmDialog.rptaConfirmDialogDefaultNo(null,"Va a empezar a iniciar el proceso de revertir. \n �Est� seguro de revertir las pruebas realizadas en el local nuevo a partir de la fecha "+ VariablesPtoVenta.vFechaInicioPruebas + " ?.\n Si desea continuar acepte.");
                                
                            if (rptaRevertir) 
                            { 
                                if (validaEsOperador()){
                                    
                                    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                                    dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
                                    dlgLogin.setVisible(true);
                                        
                                    if ( FarmaVariables.vAceptar ){
                                        if (validaIndicadorRevertirLocal()){
                                            if (validaCantidadPruebasCompleta()){                                                
                                               revertirPruebasDeLocalNuevo();        
                                            }else{
                                               FarmaUtility.showMessage(this,"Ya no se puede revertir los datos de prueba en el local.\n Ya inici� y finaliz� la sesi�n de pruebas \n Comun�quese con el Operador.",null);       
                                            }
                                                
                                        }else
                                        FarmaUtility.showMessage(this,"Ya no se puede revertir los datos de prueba en el local.\n Comun�quese con el Operador.",null);    
                                        
                                    }else{
                                        FarmaUtility.showMessage(this,"La clave ingresada no es correcta. \n",null);    
                                    }
                                        
                                }
                            }else
                            {
                                return;
                            }
                            // }else{
                            //         FarmaUtility.showMessage(this,"Ya no se puede revertir los datos de prueba en el local.\n Ya inici� y finaliz� la sesi�n de pruebas \n Comun�quese con el Operador.",null);
                            //      }
                          
                        }
                    }else
                       FarmaUtility.showMessage(this,"Ya no se puede revertir los datos de prueba en el local.\n Comun�quese con el Operador.",null);
                }    
                
            }
          }
        } else if (e.getKeyCode() == 36) {
              log.debug(":::JCORTEZ::::VALIDACION PROCESO REVERSION");
              //JCORTEZ 19.01.10 Se verificara indicador para validar proceso de pruebas.
              if(validaIndicadorPruebas()){     
                if (validaEsOperador()){
                    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                    dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
                    dlgLogin.setVisible(true);
                        
                    if ( FarmaVariables.vAceptar ){
                        if (validaIndicadorRevertirLocal()){
                            if (validaCantidadPruebas()){
                                grabaInicioFinPrueba("I");
                                lblMensaje.setVisible(true);
                                pnlEconoFar.setBackground(Color.ORANGE);
                                btnRevertir.setBackground(Color.ORANGE);
                                txtRevertir.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
                                txtRevertir.setBackground(Color.ORANGE);      
                                btnRevertir.setForeground(Color.ORANGE);                        
                            }else {
                                FarmaUtility.showMessage(this,"Ya no puede iniciar la prueba, porque ya se hizo una. \n Comun�quese con el Operador."
                                    , null);
                                return;
                            }
                        }else{
                                FarmaUtility.showMessage(this,"Ya no puede iniciar la prueba, ya se revirti� las pruebas en el local. \n Comun�quese con el Operador."
                                    , null);
                                return;
                        }
                            
                    }   
                }   
              }
            }/*else if (e.getKeyCode() == 35){
                if (validaEsOperador()){
                    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
                    dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
                    dlgLogin.setVisible(true);
                        
                    if ( FarmaVariables.vAceptar ){
                        grabaInicioFinPrueba("F");
                        lblMensaje.setVisible(false);
                        pnlEconoFar.setBackground(Color.WHITE);
                        txtRevertir.setBorder(BorderFactory.createLineBorder(Color.WHITE));                    
                        btnRevertir.setBackground(Color.WHITE);
                        txtRevertir.setBackground(Color.WHITE);
                        btnRevertir.setForeground(Color.WHITE);
                    }   
                }   
            }*/
            
        /*}else
            FarmaUtility.showMessage(this,"La opcion de reversi�n no esta habilitada en el local.",null);    */   
    }
    private boolean validaEsOperador(){
        boolean vResultado=false;
        if (FarmaVariables.vNuSecUsu.equalsIgnoreCase(FarmaConstants.ROL_OPERADOR_SISTEMAS))
            vResultado=true;
        else
            vResultado=false;
        return vResultado;
    }

    private boolean validarPermiteRevertir(){
        boolean flag=false;
        String ind="";
        try {
            ind = DBPtoVenta.obtenerIndReverPermitido();
            if((ind.trim()).equalsIgnoreCase("S")){
                flag=true;
            }
        } catch (SQLException sql) {
            log.error("",sql);
        }
        return flag;
    }
    
    private void revertirPruebasDeLocalNuevo(){
        boolean rptaRevertir = false;
        rptaRevertir = JConfirmDialog.rptaConfirmDialogDefaultNo(null,"�Est� seguro de revertir las pruebas realizadas en el local nuevo  a partir de la fecha "+ VariablesPtoVenta.vFechaInicioPruebas + " ?.\n Si acepta se borrar� toda la informaci�n generada.");
      
        if (rptaRevertir) 
        {           
            if(validarPermiteRevertir()){
                DlgProcesarRevertir vProcesaRevertir = new DlgProcesarRevertir(this,"",true);
                vProcesaRevertir.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    FarmaUtility.showMessage(this,"El proceso de revertir se realiz� con �xito.\n" + "Para continuar salir de la aplicaci�n.\n"
                        , null);
                    grabaInicioFinPrueba("F");
                    lblMensaje.setVisible(false);
                    pnlEconoFar.setBackground(Color.WHITE);
                    txtRevertir.setBorder(BorderFactory.createLineBorder(Color.WHITE));                    
                    btnRevertir.setBackground(Color.WHITE);
                    txtRevertir.setBackground(Color.WHITE);
                    btnRevertir.setForeground(Color.WHITE);
                    
                }else {
                    FarmaUtility.showMessage(this,"Ocurri� un error al revertir, comun�quese con el Operador."
                        , null);
                }
            }else{
                FarmaUtility.showMessage(this,"Ya pasaron m�s de 2 dias de pruebas, no se pueden revertir los cambio",null);
            }
        }else
            return;
    }
    private boolean validaIndicadorRevertirLocal(){
        boolean vResultado=false;
        try{
            vResultado = DBPtoVenta.obtieneIndicadorRevertirLocal();
        }catch(SQLException sql){            
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al obtener el indicador de revertir del local. \n"+ sql.getMessage(), null);
            vResultado=false;        
        }
        return vResultado;
    }
    
    private void grabaInicioFinPrueba(String tipo){
        
        try{
            DBPtoVenta.grabaInicioFinPrueba(tipo);
            FarmaUtility.aceptarTransaccion();
        }catch(SQLException sql){
            if(sql.getErrorCode()==20000){
                log.error("",sql);
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ocurri� un error al grabar inicio fin. \n"+ sql.getMessage(), null);                
            } else{
                log.error("",sql);
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ocurri� un error al grabar inicio fin. \n"+ sql.getMessage(), null);                        
            }
            
        }
    }
    private boolean validaCantidadPruebas(){
        int cantidad=-1;
        try{
            cantidad=DBPtoVenta.obtieneCantidadPruebas();
        }catch(SQLException sql){           
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al verificar inicio de prueba. \n"+ sql.getMessage(), null);                                    
            
        }
        if (cantidad == 0)  return true;
        return false;
    }
    
    private boolean validaCantidadPruebasCompleta(){
        int cantidad=-1;
        try{
            cantidad=0;//DBPtoVenta.obtieneCantidadPruebasCompletas();
        }catch(Exception sql){           
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al verificar inicio y fin de prueba. \n"+ sql.getMessage(), null);                                    
            
        }
        if (cantidad == 1)  return true;
        return false;
    }
    private String obtieneFechaInicioPrueba(){
        String fecha ="";
        try{
            fecha = DBPtoVenta.obtieneFechaInicioDePruebas();
            log.debug("fecha 1: "+fecha);
            if (!fecha.equalsIgnoreCase("N")){
                return fecha;            
            }else{ 
                fecha ="";
                return fecha;
            }    
        }catch(SQLException sql){           
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al obtener la fecha inicial de pruebas. \n"+ sql.getMessage(), null);                                    
            fecha="";
        }
        log.debug("fecha 2: "+fecha);
        return fecha;
    }
    
    /**
     * Recupera la direccion Domicilio Fiscal
     * @author ERIOS
     * @since 06.06.2013
     */
   private void cargaDireccionFiscal(){        
        try {
            ArrayList lstDirecFiscal = DBPtoVenta.obtieneDireccionMatriz();
            VariablesPtoVenta.vDireccionMatriz = FarmaUtility.getValueFieldArrayList(lstDirecFiscal, 0, 0);
            VariablesPtoVenta.vDireccionCortaMatriz = FarmaUtility.getValueFieldArrayList(lstDirecFiscal, 0, 1);
        }
        catch (SQLException sql) {            
            log.warn("Error al obtener la direcci�n de la Direcci�n Fiscal."+sql.getMessage());
        }
    }

    private void obtieneIndDireMat(){        
        try {
            VariablesPtoVenta.vIndDirMatriz = DBPtoVenta.obtieneIndDirMatriz();
        }
        catch (SQLException sql) {            
            log.error("",sql);
        }
    }

    /**
     * Obtiene indicador de Direccion Local
     * @author ERIOS
     * @since 12.09.2013
     */
    public void obtieneIndDireLocal(){        
        try {
            VariablesPtoVenta.vIndDirLocal = DBPtoVenta.obtieneIndDirLocal();            
        }
        catch (SQLException sql) {            
            log.error("",sql);
        }
        log.debug("VariablesPtoVenta.vIndDirLocal="+VariablesPtoVenta.vIndDirLocal);
    }
    
    /**
     * Obtiene indicador de Direccion Local
     * @author ERIOS
     * @since 12.09.2013
     */
    public void obtieneIndRegistroVentaRestringida(){        
        try {
            VariablesPtoVenta.vIndRegistroVentaRestringida = DBPtoVenta.obtieneIndRegistroVentaRestringida();            
        }
        catch (SQLException sql) {            
            log.error("",sql);
        }
        log.debug("VariablesPtoVenta.vIndRegistroVentaRestringida="+VariablesPtoVenta.vIndRegistroVentaRestringida);
    }
    
    private boolean validarAsistAudit(){
        boolean flag=false;
        String ind="";
        try{
            ind=DBInventario.validarAsistenteAuditoria(FarmaVariables.vCodCia,FarmaVariables.vCodLocal,FarmaVariables.vNuSecUsu);
            if(ind.equalsIgnoreCase("S"))flag=true;
        }catch(SQLException sql){
            log.error("",sql);
            FarmaUtility.showMessage(this,"ERROR en validarAsistAudit \n : " + sql.getMessage(),null);
        }
        return flag;
    }

    private boolean validaIndicadorControlSobres(){
        boolean vResultado=false;
        try{
            vResultado = DBCaja.obtieneIndicadorControlSobres();
        }catch(SQLException sql){            
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurri� un error al obtener el indicador control sobre. \n"+ sql.getMessage(), null);
            vResultado=false;        
        }
        return vResultado;
    }

     private boolean isLocalProsegur(){
         String pIndCESeguridad = "";
            boolean pVisible = false;
            try {
                pVisible = DBCaja.obtieneIndicadorProsegur();

            } catch (SQLException sql) {
                pIndCESeguridad = FarmaConstants.INDICADOR_N;
                log.error("",sql);
                FarmaUtility.showMessage(this, 
                                         "Error al grabar forma pago pedido \n" +
                        sql.getMessage(), null);
            }

            return pVisible;
     }

  private void verificaRolUsuario_sinAdmin() {
    opcionesOperador();
    /*
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
        opcionesVendedor();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO) ) {
        opcionesCajero();
    }

    if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CONTABILIDAD)||
        FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES)
      ){
        opcionesProsegur();
    }        
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ) {
        opcionesAuditoria();
    }
    
    if ( FarmaVariables.dlgLogin.verificaRol(ConstantsPtoVenta.ROL_ASISTENTE_AUDITORIA) ) {
        opcionesAsistenteAuditoria();
    }
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS) ) {
        opcionesOperador();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR) ) {
        opcionesInventariador();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES) )
    {
      opcionesReportes();      
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_INVENTARIO) )
    {
        opcionesLecturaInventario();
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) )
    {
        opcionesSupervisorVentas();
    }
    bloquearOpcionesPorCompania();*/
  }

    /**
     * Lee properties para recuperar la version del sistema
     * @author ERIOS
     * @since 19.02.2013
     * @return 
     */
    private boolean readFileFarmaVentaProperties() {
        Properties prop = new Properties();
        try {
            prop.load(FrmAdmMultiEmpresa.class.getResourceAsStream("/farmaventa.properties"));
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
        ImageIcon imageIcono = new ImageIcon(FrmAdmMultiEmpresa.class.getResource("/venta/imagenes/"+strRutaJpg));
        this.setIconImage(imageIcono.getImage());
       // ImageIcon imageIconoLogo = new ImageIcon(FrmAdmMultiEmpresa.class.getResource("/venta/imagenes/logo-renova.jpg"));
        //lblLogo_empresa.setIcon(imageIconoLogo);
        
    }

    /**
     * Habilita opciones del rol Cajero
     * @author ERIOS
     * @since 20.06.2013
     */
    private void opcionesCajero() {

        //mnuEconoFar_Ventas
          mnuVentas_Recargas.setEnabled(true);
        
          //mnuCaja_CorreccionComprobantes.setEnabled(true);
          mnuCaja_ReimpresionPedido.setEnabled(true);
          mnuCaja_Utilitarios.setEnabled(true);
            mnuCaja_PruebaImpresora.setEnabled(true);
            //mnuCaja_Pinpad.setEnabled(true);
              //mnuCaja_Pinpad_Visa.setEnabled(true);
              //mnuCaja_Pinpad_Mastercard.setEnabled(true);
            mnuCaja_AbrirGabeta.setEnabled(true);
         // mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
          mnuCaja_Recaudacion.setEnabled(true);
            mnuCaja_CMR.setEnabled(true);
            mnuCaja_Citibank.setEnabled(true);
            mnuCaja_Claro.setEnabled(true);
            mnuCaja_Prestamos_Citibank.setEnabled(true);
            
        //mnuEconoFar_Inventario
          mnuInventario_PedidosLocales.setEnabled(false);
            if(FarmaVariables.vEconoFar_Matriz){
            mnuInventario_PedidoAdicional.setVisible(false);
            mnuInventario_PedidoEspecial.setVisible(false);
            }       
        
        //mnuEconoFar_Administracion
          mnuAdministracion_ControlHoras.setEnabled(false);
        
          
    }


    private void opcionesComunes() {
        muestraUser();
        validaOpcionesMenu(false);
        
        mnuEconoFar_Administracion.setEnabled(true);      
        mnuAdministracion_Usuarios.setEnabled(true);
        mnuUsuarios_CambioUsuario.setEnabled(true);
        mnuUsuarios_CambioClave.setEnabled(true);
        mnuInventario_IngTransportista.setEnabled(false);
        mnuVentas_MedidaPresion.setEnabled(false);
        mnuAdministracion_FondoSencillo.setVisible(false);

    }

    private void opcionesProsegur() {
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
    }

    private void opcionesAuditoria() {
        mnuEconoFar_TomaInventario.setEnabled(true); 
        mnuInventarioCiclico_Inicio.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
        if(!FarmaVariables.vEconoFar_Matriz) {
        // MODULO INVENTARIO
        mnuEconoFar_Inventario.setEnabled(true);
          mnuInventario_GuiaEntrada.setEnabled(true);
          mnuInventario_Kardex.setEnabled(true);
          mnuInventario_Transferencias.setEnabled(true);
          mnuInventario_MercaderiaDirecta.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          
          mnuInventario_Guias.setEnabled(true);
          mnuInventario_Ajustes.setEnabled(true);
          //Toma Inventario
          mnuTomaInventario_Tradicional.setEnabled(true);
          mnuTomaInventario_Ciclico.setEnabled(true);
          mnuTomaInventario_Historico.setEnabled(true);
          mnuTomaInVentario_ItemsXLab.setEnabled(true);
            mnuInventario_PedidosLocales.setEnabled(true);
            mnuInventario_PedidoAdicional.setVisible(true);
            mnuInventario_PedidoEspecial.setVisible(true);
            
        } else 
        {
          mnuTomaInventario_Tradicional.setEnabled(false);
          mnuTomaInventario_Ciclico.setEnabled(true);
          mnuTomaInventario_Historico.setEnabled(true);
          
            mnuInventario_PedidosLocales.setEnabled(true);
            mnuInventario_PedidoAdicional.setVisible(false);
            mnuInventario_PedidoEspecial.setVisible(false);
        }
    }

    private void opcionesAsistenteAuditoria() {
        mnuEconoFar_TomaInventario.setEnabled(true); 
        mnuInventarioCiclico_Inicio.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
        if(!FarmaVariables.vEconoFar_Matriz) {
        // MODULO INVENTARIO
        mnuEconoFar_Inventario.setEnabled(true);
          mnuInventario_GuiaEntrada.setEnabled(true);
          mnuInventario_Kardex.setEnabled(true);
          mnuInventario_Transferencias.setEnabled(true);
          mnuInventario_MercaderiaDirecta.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          
          mnuInventario_Guias.setEnabled(true);
          mnuInventario_Ajustes.setEnabled(true);
          //Toma Inventario
          mnuTomaInventario_Tradicional.setEnabled(true);
          mnuTomaInventario_Ciclico.setEnabled(true);
          mnuTomaInventario_Historico.setEnabled(true);
          mnuTomaInVentario_ItemsXLab.setEnabled(true);
            mnuInventario_PedidosLocales.setEnabled(true);
            mnuInventario_PedidoAdicional.setVisible(true);
            mnuInventario_PedidoEspecial.setVisible(true);
            
        } else 
        {
          mnuTomaInventario_Tradicional.setEnabled(false);
          mnuTomaInventario_Ciclico.setEnabled(true);
          mnuTomaInventario_Historico.setEnabled(true);
            mnuInventario_PedidosLocales.setEnabled(true);
            mnuInventario_PedidoAdicional.setVisible(false);
            mnuInventario_PedidoEspecial.setVisible(false);
        }
    }

    private void opcionesOperador() {
        validaOpcionesMenu(true);
         // mnuVentas_Receta.setEnabled(true);
          if (UtilityFondoSencillo.indActivoFondo()) {
            mnuAdministracion_FondoSencillo.setVisible(true);
          }
        
            if(UtilityRecepCiega.indHabDatosTransp()){
              mnuInventario_IngTransportista.setEnabled(true);
          }
        mnuInventarioCiclico_Inicio.setEnabled(true);
           
           mnuInventario_PedidosLocales.setEnabled(true);
           if(FarmaVariables.vEconoFar_Matriz){
               mnuInventario_PedidoAdicional.setVisible(false);
               mnuInventario_PedidoEspecial.setVisible(false);
           }
           
           mnuVentas_MedidaPresion.setEnabled(false);
           
           mnuVentas_Recargas.setEnabled(true);

       //   mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
          mnuCE_Prosegur.setEnabled(isLocalProsegur());
          
          if(UtilityFondoSencillo.indActivoFondo()){
              mnuAdministracion_FondoSencillo.setEnabled(true);
              }else{
                mnuAdministracion_FondoSencillo.setEnabled(false);
              }
    }

    private void opcionesInventariador() {
        mnuEconoFar_TomaInventario.setEnabled(true);
          mnuTomaInventario_Tradicional.setEnabled(true);
          mnuInventario_Toma.setEnabled(true);
          mnuTomaInventario_Ciclico.setEnabled(true);
          mnuInventarioCiclico_Inicio.setEnabled(false);
          mnuInventarioCiclico_Toma.setEnabled(true);
          mnuAdministracion_ControlHoras.setEnabled(false);
          mnuInventario_PedidosLocales.setEnabled(true);
          
           mnuInventario_PedidosLocales.setEnabled(true);
           if(FarmaVariables.vEconoFar_Matriz){
               mnuInventario_PedidoAdicional.setVisible(false);
               mnuInventario_PedidoEspecial.setVisible(false);
           }
    }

    private void opcionesReportes() {
        // MODULO INVENTARIO
        mnuEconoFar_Inventario.setEnabled(true);
          mnuInventario_GuiaEntrada.setEnabled(true);
        // MODULO ADMINISTRACION
        mnuEconoFar_Administracion.setEnabled(true);
          mnuAdministracion_Usuarios.setEnabled(false);
            mnuUsuarios_CambioUsuario.setEnabled(false);
            mnuUsuarios_CambioClave.setEnabled(false);
          mnuAdministracion_MovCaja.setEnabled(true);
            mnuMovCaja_ConsultarMov.setEnabled(true);
        // MODULO REPORTES
        mnuEconoFar_Reportes.setEnabled(true);
          mnuReportes_RegistroVentas.setEnabled(true);
          mnuReportes_VentasVendedor.setEnabled(true);
          mnuReportes_DetalleVentas.setEnabled(true);
          mnuReportes_ResumenVentaDia.setEnabled(true);
          mnuReportes_VentasProducto.setEnabled(true);
          mnuReportes_VentasHora.setEnabled(true);
          mnuReportes_VentasDiaMes.setEnabled(true);
          mnuReportes_FaltaCero.setEnabled(true);
        // MODULO CAJA ELECTRONICA
       
          mnuAdministracion_ControlHoras.setEnabled(false);
    }

    private void opcionesLecturaInventario() {
        // MODULO INVENTARIO
        mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
        mnuInventario_Kardex.setEnabled(true);
        mnuInventario_Transferencias.setEnabled(true);
        mnuInventario_MercaderiaDirecta.setEnabled(true);        
        mnuAdministracion_ControlHoras.setEnabled(false);
    }

   
    private void mnuCaja_Pinpad_Mastercard_actionPerformed(ActionEvent e)
    {   DlgCierrePinpad dlgCierrePinpad = new DlgCierrePinpad(this,"",true);
        dlgCierrePinpad.setVisible(true);
    }

    private void mnuCaja_Reimpresion_Pinpad_MC_actionPerformed(ActionEvent e)
    {   DlgReimpresionPinpad dlgReimpresionPinpad = new DlgReimpresionPinpad(this,"",true);
        dlgReimpresionPinpad.setVisible(true);
    }

    private void mnuCaja_reporTot_Pinpad_MC_actionPerformed(ActionEvent e)
    {   DlgReporteTotalPinpad dlgReporteTotalPinpad = new DlgReporteTotalPinpad(this,"",true);
        dlgReporteTotalPinpad.setVisible(true);
    }

    private void mnuCaja_reporDet_Pinpad_MC_actionPerformed(ActionEvent e)
    {   DlgReporteDetalladoPinpad dlgReporteDetalladoPinpad = new DlgReporteDetalladoPinpad(this,"",true);
        dlgReporteDetalladoPinpad.setVisible(true);
    }

    private void mnuCaja_AnulacionTran_Pinpad_Visa_actionPerformed(ActionEvent e)
    {   DlgEleccionTranAnularPinpad dlgEleccionTranAnularPinpad = new DlgEleccionTranAnularPinpad(this,"",true);
        dlgEleccionTranAnularPinpad.setVisible(true);
    }

    private void mnuAyuda_AcercaDe_actionPerformed(ActionEvent e) {
        DlgAcercaDe dlgAcercaDe = new DlgAcercaDe(this,"",true);
        dlgAcercaDe.setVisible(true);
    }

 private void mnuCE_ReciboSencillo_actionPerformed(ActionEvent e) {
        DlgReciboPagoSencillo dlgReciboPagoSencillo = new DlgReciboPagoSencillo(this, "", true, false);
        dlgReciboPagoSencillo.setTitle(ConstantsCajaElectronica.TIT_RECIBO_SENCILLO);
        if(dlgReciboPagoSencillo.permisoAbrirVentana()){
            dlgReciboPagoSencillo.setVisible(true);
        }        
    }

    private void mnuCE_PagoSencillo_actionPerformed(ActionEvent e) {
        DlgReciboPagoSencillo dlgReciboPagoSencillo = new DlgReciboPagoSencillo(this, "", true, true);
        dlgReciboPagoSencillo.setTitle(ConstantsCajaElectronica.TIT_PAGO_SENCILLO);
        if(dlgReciboPagoSencillo.permisoAbrirVentana()){
            dlgReciboPagoSencillo.setVisible(true);
        }   
    }
    
    private void mnCaja_ReimpresionLote_Pinpad_MC_actionPerformed(ActionEvent e)
    {   DlgReimpresionLotePinpad dlgReimpresionLotePinpad = new DlgReimpresionLotePinpad(this,"",true);
        dlgReimpresionLotePinpad.setVisible(true);
    }

    private void mnuReportes_ListPsicotropicos_actionPerformed(ActionEvent e)
    {   DlgReportePsicotropicos dlgReportePsicotropicos = new DlgReportePsicotropicos(this,"",true);
        dlgReportePsicotropicos.setVisible(true);
    }

    private void mnuReportes_ListRecetario_actionPerformed(ActionEvent e)
    {   DlgListaRecetarios dlgListaRecetario = new DlgListaRecetarios(this,"",true);
        dlgListaRecetario.setVisible(true);
    }
    
    private void bloquearOpcionesPorCompania(){
          log.debug("[INICIO] - bloquearOpcionesPorCompania");    
          try{
              FacadeCaja facadeCaja = new FacadeCaja();
              ArrayList lista = facadeCaja.obtenerOpcionesBloqueadas();
              if(lista != null && lista.size() > 0){
                        int indiceEncontrado = -1;
                      for(Field f : getClass().getDeclaredFields()){
                          //if(f.getType().toString().equals(TIPO_VARIABLE_MENUITEM)){
                              String nombre = f.getName();
                              for(int i = 0; i < lista.size();i++){
                                  String nombreBloqueado = ((ArrayList)lista.get(i)).get(0).toString();
                                  if(nombre.equals(nombreBloqueado)){
                                        log.debug("------------bloqueando....-------------------");
                                        log.debug(" nombre : " + f.getName());
                                        JMenuItem object = (JMenuItem)f.get(this);
                                        object.setEnabled(false);
                                        indiceEncontrado = i;
                                        break;
                                    }
                                }
                              if(indiceEncontrado > -1){
                                    lista.remove(indiceEncontrado);
                                    indiceEncontrado = -1;
                                }
                        }    
                  }
          }catch(Exception e){
              log.error("",e);
              }
          log.debug("[FIN] - bloquearOpcionesPorCompania");    
      }

    private void mnuCE_ReprocesaConcil_actionPerformed(ActionEvent e)
    {   DlgDatosTarjetaPinpad.reprocesarConciliaciones();
    }

    private void mnuInventario_RecepcionRM_actionPerformed(ActionEvent e)
    {   /*DlgRecepcionRM dlgRecepcionRM = new DlgRecepcionRM(this,"",true);
        dlgRecepcionRM.setVisible(true);*/
    }

    private void prueba_actionPerformed(ActionEvent e)
    {   try
        {   UtilityInventario.imprimeGuia(null, "15/11/2013", null);
        }
        catch(Exception ex)
        {   log.error("",ex);
        }
    }

    private void mnuInventario_StockNegativo_actionPerformed(ActionEvent e)
    {   
        DlgListadoStockNegativo dlgListadoStockNegativo = new DlgListadoStockNegativo(this,"",true);
        dlgListadoStockNegativo.setVisible(true);
    }

    public void verificaRolUsuNuevo()
    {   verificaRolUsuario();
    }

    //LLEIVA 19-Feb-2014 Menu para listado de pedidos pagados con pinpad
    private void mnuCaja_PedidoPinpad_actionPerformed(ActionEvent e)
    {
        DlgListadoPedidoPinpad dlgListadoPedidoPinpad = new DlgListadoPedidoPinpad(this,"",true);
        dlgListadoPedidoPinpad.setVisible(true);
    }

    private void mnuReportes_ListGuias_actionPerformed(ActionEvent e)
    {
        DlgReporteGuia dlgReporteGuia = new DlgReporteGuia(this,"",true);
        dlgReporteGuia.setVisible(true);
    }

    private void rptNvo01_actionPerformed(ActionEvent e) {
        DlgRptNuevo_01 dlgReporteGuia = new DlgRptNuevo_01(this,"",true);
        dlgReporteGuia.setVisible(true);
    }

   
    private void nuevoVendedor_actionPerformed(ActionEvent e) {
        rptNvo01_actionPerformed(e);
    }

    private void rptnuevo_formapago_action(ActionEvent e) {
        reporteFormaPago_actionPerformed(e);
    }

    private void mnuAtenciones_actionPerformed(ActionEvent e) {
        DlgListaAtenciones dlgListSoatAtencion = new DlgListaAtenciones(this,"",true);
        dlgListSoatAtencion.setVisible(true);    
    }

    private void mnuRegistroAtenciones_actionPerformed(ActionEvent e) {
        //DlgListaRegistroAtencion
        DlgListaRegistroAtencion dlgListSoatAtencion = new DlgListaRegistroAtencion(this,"",true);
        dlgListSoatAtencion.setVisible(true);
    }

    private void mnuLiquidacion_actionPerformed(ActionEvent e) {
        DlgListaLiquidaciones dlgListSoatAtencion = new DlgListaLiquidaciones(this,"",true);
        dlgListSoatAtencion.setVisible(true);
    }

    private void jMenuItem6_actionPerformed(ActionEvent e) {
        
       /* DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(this,"",true);
        dlgResumenPedido.setFrameEconoFar(this);
        VariablesModuloVentas.vIsVtaSoat = true;
        dlgResumenPedido.setVisible(true)*/

        DlgMantIgv dlgResumenPedido = new DlgMantIgv(this,"",true);
        dlgResumenPedido.setVisible(true);

        
    }

    private void jMenuItem7_actionPerformed(ActionEvent e) {
       /* DlgListaMarca dlgResumenPedido = new DlgListaMarca(this,"",true);
        dlgResumenPedido.setVisible(true);*/
        
       DlgMantMaestroDental precios = new DlgMantMaestroDental(this, "", true);
               precios.setVisible(true);

    }

    private void jMenuItem8_actionPerformed(ActionEvent e) {
        //DlgListaCategoria dlgResumenPedido = new DlgListaCategoria(this,"",true);
        //dlgResumenPedido.setVisible(true);

        DlgListadoPacks dlgResumenPedido = new DlgListadoPacks(this,"",true);
        dlgResumenPedido.setVisible(true);
    }

    private void jMenuItem9_actionPerformed(ActionEvent e) {
        //DlgListaSubCategoria dlgResumenPedido = new DlgListaSubCategoria(this,"",true);
        //dlgResumenPedido.setVisible(true);
        DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (this,"",true);
        VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;
        dlgBuscaClienteJuridico.setVisible(true);


    }

    private void jMenuItem10_actionPerformed(ActionEvent e) {
        DlgMantKit dlgResumenPedido = new DlgMantKit(this,"",true);
        dlgResumenPedido.setVisible(true);

 
    }

    private void jMenuItem11_actionPerformed(ActionEvent e) {
        DlgRegistroComision dlgRegistroVentas = new DlgRegistroComision(this, "", true);
        //DlgRptNuevo dlgRegistroVentas = new DlgRptNuevo(this, "", true);
        dlgRegistroVentas.setVisible(true);
    }

    private void jMenuItem12_actionPerformed(ActionEvent e) {
        DlgRegistroComisionDetalle dlgRegistroVentas = new DlgRegistroComisionDetalle(this, "", true);
        //DlgRptNuevo dlgRegistroVentas = new DlgRptNuevo(this, "", true);
        dlgRegistroVentas.setVisible(true);
    }

    private void jMenuItem13_actionPerformed(ActionEvent e) {


        DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (this,"",true);
        VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_N;
        dlgBuscaClienteJuridico.setVisible(true);


    }

    private void jMenuItem14_actionPerformed(ActionEvent e) {
        DlgListaPedidosCobrados dlgResumenPedido = new DlgListaPedidosCobrados(this,"",true);
        dlgResumenPedido.setVisible(true);
    }
}
