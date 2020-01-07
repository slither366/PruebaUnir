package venta.receta;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaPRNUtility;
import common.FarmaPrintService;
import common.FarmaPrintServiceTicket;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.awt.Component;
import java.awt.Desktop;

import java.io.IOException;

import java.util.Iterator;

import javax.swing.table.DefaultTableCellRenderer;

import venta.receta.reference.ConstantsReceta;
import venta.reference.UtilityPtoVenta;
import venta.receta.reference.DBReceta;
import venta.receta.reference.VariablesReceta;

import venta.reportes.DlgDetalleRegistroVentas;
import venta.reportes.DlgListaPedAnulNoCob;
import venta.reportes.DlgResumenVenta;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;
import venta.caja.reference.VariablesCaja;

import venta.impresion.DlgImpresionCotizacion;
import venta.impresion.Reference.ConstantsReportePDF;

import venta.modulo_venta.DlgIngresoGuia;
import venta.modulo_venta.DlgMensajeImpresion;
import venta.modulo_venta.DlgSeleccionImpCotiza;
import venta.modulo_venta.DlgSeleccionIngresoPedCotiza;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.ConstantsPtoVenta;
import venta.reference.VariablesPtoVenta;


public class DlgListaCotizaciones extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaCotizaciones.class);

    private FarmaTableModel tableModelRegistroVentas;
    private ArrayList modelBase = new ArrayList();
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRegistroVentas = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabel lblTotalMonto = new JLabel();
    
    public boolean vOperaReceta = false;
    public String vCodCia = "";
    public String vCodLocal = "";
    public String vNumReceta = "";
    private JLabelFunction lblImpresion = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtBusqueda = new JTextField();
    private JLabel lblMensajeFiltro = new JLabel();


    public DlgListaCotizaciones() {
        this(null, "", false);
    }

    public DlgListaCotizaciones(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(891, 597));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Lista Cotizaciones");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 5, 855, 90));
        pnlCriterioBusqueda.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 470, 855, 20));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 110, 855, 360));
        jScrollPane1.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(15, 5, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(85, 5, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(210, 5, 101, 19));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(340, 5, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(90, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setFocusable(false);
        lblF1.setBounds(new Rectangle(10, 515, 105, 20));
        lblF1.setText("[ F1 ] Ver Detalle");
        lblF1.setFocusable(false);
        lblF9.setBounds(new Rectangle(140, 515, 100, 20));
        lblF9.setText("[ F9 ] Ordenar");
        lblF9.setFocusable(false);
        lblEsc.setBounds(new Rectangle(580, 515, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        lblF2.setBounds(new Rectangle(435, 515, 120, 20));
        lblF2.setText("[ F11 ] Procesar");
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        lblF2.setFocusable(false);
        lblTotalMonto.setText("0");
        lblTotalMonto.setBounds(new Rectangle(625, 0, 65, 20));
        lblTotalMonto.setFont(new Font("SansSerif", 1, 11));
        lblTotalMonto.setForeground(Color.white);
        lblTotalMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMonto.setFocusable(false);
        //jContentPane.add(jLabelFunction1, null);
        lblImpresion.setText("[ F12 ] Imprimir");
        lblImpresion.setBounds(new Rectangle(715, 515, 145, 20));
        jLabel1.setText("<html><center>Cliente<br>o Ruc</center></html>");
        jLabel1.setBounds(new Rectangle(25, 30, 50, 30));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(SystemColor.window);
        txtBusqueda.setBounds(new Rectangle(85, 35, 590, 20));
        txtBusqueda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtBusqueda_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtBusqueda_keyReleased(e);
                }
            });
        lblMensajeFiltro.setText(".");
        lblMensajeFiltro.setBounds(new Rectangle(85, 60, 625, 20));
        lblMensajeFiltro.setFont(new Font("SansSerif", 1, 11));
        lblMensajeFiltro.setForeground(SystemColor.window);
        jContentPane.add(lblImpresion, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblF1, null);
        tblRegistroVentas.setFocusable(false);
        jScrollPane1.getViewport().add(tblRegistroVentas, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlResultados.add(lblTotalMonto, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlCriterioBusqueda.add(lblMensajeFiltro, null);
        pnlCriterioBusqueda.add(txtBusqueda, null);
        pnlCriterioBusqueda.add(jLabel1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        tableModelRegistroVentas =
                new FarmaTableModel(ConstantsReceta.columnsListaRegistroReceta, ConstantsReceta.defaultValuesListaRegistroReceta,
                                    0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tableModelRegistroVentas,
                                    ConstantsReceta.columnsListaRegistroReceta);
    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();

        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        cargaListaDefault();
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRegistroVentas.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRegistroVentas);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblRegistroVentas.getSelectedRow() >= 0) {
            
                ArrayList vFila = (ArrayList)tableModelRegistroVentas.data.get(tblRegistroVentas.getSelectedRow());
                DlgDetalleReceta dlgDetalleRegistroVentas = new DlgDetalleReceta(myParentFrame, "", true,vFila,true);
                dlgDetalleRegistroVentas.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    if(JConfirmDialog.rptaConfirmDialog(this, "Se va Operar la cotización Seleccionada. \n" + 
                                                            "Esta Seguro de realizar la acción?")){
                        setVCodCia(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 7));
                        setVCodLocal(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 8));
                        setVNumReceta(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, tblRegistroVentas.getSelectedRow(), 0));
                        setVOperaReceta(true);
                        cerrarVentana(true);
                    }
                    else
                        setVOperaReceta(false);
                }
            } else {
                listadoDetalleVentas();
            }
        } 
        else
        if(e.getKeyCode()==KeyEvent.VK_F12){
            if(tblRegistroVentas.getRowCount() <= 0)
            FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de generar el documento",txtFechaIni);
            else {
                DlgSeleccionImpCotiza dlgReceta = new DlgSeleccionImpCotiza(myParentFrame, "", true);
                dlgReceta.setVisible(true);
                if (FarmaVariables.vAceptar) {
                   if(dlgReceta.isVImprime())
                        imprimirCotizacion();
                   else
                    generarDocumentoPDF();
                       
                }
            }
                
                
              
        }
        else
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                listadoDetalleVentas();
            }
        } else 
           if (e.getKeyCode() == KeyEvent.VK_F9) 
           {
                if (tblRegistroVentas.getRowCount() <= 0)
                    FarmaUtility.showMessage(this, "Debe realizar una busqueda antes de ordenar", txtFechaIni);
                else
                    muestraVentaOrdenar();
           } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
               cerrarVentana(true);
           }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void buscaRegistroCotizaciones(String pFechaInicio, String pFechaFin) {
        VariablesReporte.vFechaInicio = pFechaInicio;
        VariablesReporte.vFechaFin = pFechaFin;
        cargaRegistroCotizacion();
    }

    private void cargaRegistroCotizacion() {
        try {
            log.debug(VariablesReporte.vFechaInicio);
            log.debug(VariablesReporte.vFechaFin);
            DBReceta.cargaListaRegistroRecetas(tableModelRegistroVentas, VariablesReporte.vFechaInicio,
                                                VariablesReporte.vFechaFin);
            
            
            modelBase = (ArrayList)(tableModelRegistroVentas.data.clone());
            
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 0, FarmaConstants.ORDEN_DESCENDENTE);
            //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
            
            pintaProductosSinStock();
            
            
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }
    private void cargaListaDefault() {
        try {
            DBReceta.cargaCotizaDefault(tableModelRegistroVentas);
            modelBase = (ArrayList)(tableModelRegistroVentas.data.clone());
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);
                lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
            }
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 0, FarmaConstants.ORDEN_DESCENDENTE);
            //lblTotalMonto.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblRegistroVentas, 6)));
            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
            pintaProductosSinStock();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listadoDetalleVentas() {
        ArrayList vFila = (ArrayList)tableModelRegistroVentas.data.get(tblRegistroVentas.getSelectedRow());
        DlgDetalleReceta dlgDetalleRegistroVentas = new DlgDetalleReceta(myParentFrame, "", true,vFila);
        dlgDetalleRegistroVentas.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    /**
     * Fecha Modificación: 05/01/2007
     * Usuario: Luis Reque
     * Descripción: Se realiza el ordenamiento por Tipo y Nro. de Comprobante a la vez, no por separados.
     * */
    private void muestraVentaOrdenar() {
        DlgOrdenarReceta dlgOrdenar = new DlgOrdenarReceta(myParentFrame, "Ordenar", true);

        String[] IND_DESCRIP_CAMPO = { "Correlativo", "Fecha", "Medico", "Cliente", "Neto" };
        String[] IND_CAMPO = { "0", "6", "2", "3", "5" };
        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsReporte.VNOMBREINHASHTABLEREGISTROVENTAS;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, VariablesReporte.vCampo,
                                 VariablesReporte.vOrden);
            tblRegistroVentas.repaint();
        }
    }
   
    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    buscaRegistroCotizaciones(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    public void setVOperaReceta(boolean vOperaReceta) {
        this.vOperaReceta = vOperaReceta;
    }

    public boolean isVOperaReceta() {
        return vOperaReceta;
    }

    public void setVCodCia(String vCodCia) {
        this.vCodCia = vCodCia;
    }

    public String getVCodCia() {
        return vCodCia;
    }

    public void setVCodLocal(String vCodLocal) {
        this.vCodLocal = vCodLocal;
    }

    public String getVCodLocal() {
        return vCodLocal;
    }

    public void setVNumReceta(String vNumReceta) {
        this.vNumReceta = vNumReceta;
    }

    public String getVNumReceta() {
        return vNumReceta;
    }
    private void generarDocumentoPDF() {
        if(tblRegistroVentas.getRowCount() <= 0)
        {
            FarmaUtility.showMessage(this,"Ingrese un criterio de Busqueda", txtFechaIni);
            FarmaUtility.moveFocus(txtFechaIni);
        }
        else 
        {
            ConstantsReportePDF.rutaPDF = "";
            String pNumPedVta = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();
            DlgImpresionCotizacion dlg = new DlgImpresionCotizacion(myParentFrame, "", true, pNumPedVta);
            dlg.setVisible(true);
            dlg.toFront();
            if(FarmaVariables.vAceptar){
                try {
                     File path = new File (ConstantsReportePDF.rutaPDF);
                     Desktop.getDesktop().open(path);
                }catch (IOException ex) {
                     ex.printStackTrace();
                }
            }
        }        
    }

    private void txtBusqueda_keyPressed(KeyEvent e) {
        
        if(tblRegistroVentas.getRowHeight()==0&&txtBusqueda.getText().trim().length()==0){
            clonarListadoProductos();
            lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
        }
            
        
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER)){
            filtroGoogle();
            }
    }
    
    //
    private void clonarListadoProductos() {
        ArrayList arrayClone = new ArrayList();
        for (int i = 0; 
             i < modelBase.size(); 
             i++) {
            
            ArrayList aux = 
                (ArrayList)((ArrayList)modelBase.get(i)).clone();
            arrayClone.add(aux);
        }
       // ascasc
        tableModelRegistroVentas.clearTable();
        tableModelRegistroVentas.data = arrayClone;
        tblRegistroVentas.repaint();
        tblRegistroVentas.show();
    }
    
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
    }
    
    private void filtrarBusquedaGoogle() {
        String condicion = txtBusqueda.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelRegistroVentas.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String cliente = fila.get(2).toString().toUpperCase().trim();
                String ruc = fila.get(3).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(cliente.contains(condicion)||ruc.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModelRegistroVentas.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelRegistroVentas.fireTableDataChanged();
            
            if(tblRegistroVentas.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                if(tblRegistroVentas.getRowCount()==1)
                    lblMensajeFiltro.setText(tblRegistroVentas.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblRegistroVentas.getRowCount()+" filas para el filtro aplicado");
            }
        }
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
        
        if(tblRegistroVentas.getRowCount()>0)
            FarmaGridUtils.showCell(tblRegistroVentas, 0, 0);
    }

    private void txtBusqueda_keyReleased(KeyEvent e) {
        if(tblRegistroVentas.getRowHeight()==0&&txtBusqueda.getText().trim().length()==0){
            clonarListadoProductos();
            lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
        }
            
        
        if(e.getKeyChar() != '+'&&
            !(
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER)){
            filtroGoogle();
            }


    }

    private void imprimirCotizacion() {
        String pNumPedVta = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),0)).trim();

        ArrayList vDatos = new ArrayList();
        /*
        NVL(SUBSTR(C.NUM_COMP_GUIA,1,3),I.NUM_SERIE_LOCAL) || 'Ã' ||
                NVL(SUBSTR(C.NUM_COMP_GUIA,-7),I.NUM_COMP)|| 'Ã' ||
               decode(p.tip_comp_pago,'01','BOLETA','02','FACTURA','COMP')||':'||
               NVL(SUBSTR(p.num_comp_pago,1,3)||'-'||SUBSTR(p.num_comp_pago,-7),' ') || 'Ã' ||
               TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY')|| 'Ã' ||
               nvl(C.RUC_CLI_PED_VTA,' ')|| 'Ã' ||
               nvl(C.NOM_CLI_PED_VTA,' ')|| 'Ã' ||
               nvl(C.DIR_CLI_PED_VTA,' ')
         * */
        String vRazSocial_Local = "";
        String vNumRuc_Local = "";
        String vDirRuc_Local = "";
        String vFechaEmi = "";
        String vNumpedVta = "";
        String vRucCli = "";
        String vRazonSocial_cli = "";
        String vDirecCli = "";
        
        String vValorNeto = "";
        String vValorIGV= "";
        String vValorNeto_Sin_Igv = "";
        
        try {
            
            DBCaja.getDatosPedido_Cotizacion(pNumPedVta,vDatos);
            /*
            nvl(p.raz_soc_cia,' ')|| 'Ã' ||
                   nvl(p.num_ruc_cia,' ')|| 'Ã' ||
                   nvl(p.dir_cia,' ')        || 'Ã' ||
                   TO_CHAR(C.FEC_PED_VTA,'DD/MM/YYYY')|| 'Ã' ||
                   c.num_ped_vta|| 'Ã' ||
                   nvl(C.RUC_CLI_PED_VTA,' ')|| 'Ã' ||
                   nvl(C.NOM_CLI_PED_VTA,' ')|| 'Ã' ||
                   nvl(C.DIR_CLI_PED_VTA,' ')
             * */
            if(vDatos.size()>0){
                vRazSocial_Local = (FarmaUtility.getValueFieldArrayList(vDatos,0, 0));
                vNumRuc_Local = (FarmaUtility.getValueFieldArrayList(vDatos,0, 1));
                vDirRuc_Local = (FarmaUtility.getValueFieldArrayList(vDatos,0, 2));
                vFechaEmi = (FarmaUtility.getValueFieldArrayList(vDatos,0, 3));
                vNumpedVta= (FarmaUtility.getValueFieldArrayList(vDatos,0, 4));
                vRucCli = (FarmaUtility.getValueFieldArrayList(vDatos,0, 5));
                vRazonSocial_cli = (FarmaUtility.getValueFieldArrayList(vDatos,0, 6));
                 vDirecCli = (FarmaUtility.getValueFieldArrayList(vDatos,0, 7));
                vValorNeto = (FarmaUtility.getValueFieldArrayList(vDatos,0, 8));
                vValorIGV = (FarmaUtility.getValueFieldArrayList(vDatos,0, 9));
                vValorNeto_Sin_Igv = (FarmaUtility.getValueFieldArrayList(vDatos,0, 10));
            }
               
       
       // graba e imprime cotiza
       ArrayList vListaDetalleGuia = new ArrayList();
       String pRuta = "";
       
           DBCaja.getDetallePara_Cotizacion(vListaDetalleGuia,pNumPedVta);
           pRuta = DBCaja.obtieneRutaImpresoraVenta(DBCaja.getObtieneSecGuia());
     
                          
       if(vListaDetalleGuia.size()>0){
           
           /*DlgMensajeImpresion dlgLogin = new DlgMensajeImpresion(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true,
                                                            ConstantsModuloVenta.TIPO_COMP_GUIA);
           dlgLogin.setVisible(true);*/
           // imprimir guia
          /// pRuta = "D:\\imp\\cotiza.txt";
           FarmaPrintServiceTicket mainPRN = new FarmaPrintServiceTicket(666, pRuta, false);
               mainPRN.startPrintService();
               mainPRN.activateCondensed();
                       // comenzando a imprimir
                       
                       
                       mainPRN.printLine("            " + " ", true);
                       mainPRN.printLine("            " + " ", true);
                       mainPRN.printLine("            " + " ", true);
                       mainPRN.printLine("                                                  " + "COTIZACION", true);
                       mainPRN.printLine("                    " + vRazSocial_Local, true);
                       mainPRN.printLine("               RUC: " + vNumRuc_Local + "   Num:"+vNumpedVta, true);
           mainPRN.printLine("                   Direccion: " + vDirRuc_Local, true);
           mainPRN.printLine("                       "+ "La validez de esta cotizacion solo sera por 24 horas (Sujeto al Tipo de Cambio)" , true);
           mainPRN.printLine("          ", true);
           mainPRN.printLine("         Cliente: " +" "+vRazonSocial_cli+"  Ruc : "+vRucCli, true);
                       mainPRN.printLine("                  Fecha:  " + vFechaEmi+" Nuevos Soles", true);
                       
                       
                       mainPRN.printLine("            " + " ", true);
                       mainPRN.printLine("       Neto " + vValorNeto_Sin_Igv+"       IGV: "+vValorIGV+"        TOTAL COTIZACION: "+vValorNeto, true);
                       

           mainPRN.printLine("            " + " ", true);
           String pCodigo = "";
           String pDescripcion = "";
           String pUnidad = "";
           String pCantidad = "";
           String pPrecioUnit = "";
           String pSubTotal = "";
           
           mainPRN.printLine("    "+
                               FarmaPRNUtility.alinearIzquierda("CODIGO",8)     +
                               FarmaPRNUtility.alinearIzquierda("DESCRIPCION",80)+
                               FarmaPRNUtility.alinearIzquierda("U.M",15)     +
                               FarmaPRNUtility.alinearIzquierda("CANT",8)    +
                               FarmaPRNUtility.alinearIzquierda("P.U",10)  +                                              
                               FarmaPRNUtility.alinearIzquierda("PARCIAL",10)     
                             ,true);

           mainPRN.printLine("            " + " ", true);
                for(int i=0;i<vListaDetalleGuia.size();i++){
                    
                           pCodigo = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 0);
                           pDescripcion = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 1);
                           pUnidad = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 2);
                           pCantidad = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 3);
                           pPrecioUnit   = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 4);
                           pSubTotal = FarmaUtility.getValueFieldArrayList(vListaDetalleGuia, i, 5);
                           
                           mainPRN.printLine("    "+
                                               FarmaPRNUtility.alinearIzquierda(pCodigo,8)     +
                                               FarmaPRNUtility.alinearIzquierda(pDescripcion,80)+
                                               FarmaPRNUtility.alinearIzquierda(pUnidad,15)     +
                                               FarmaPRNUtility.alinearIzquierda(pCantidad,8)    +
                                               FarmaPRNUtility.alinearIzquierda(pPrecioUnit,10)  +                                              
                                               FarmaPRNUtility.alinearIzquierda(pSubTotal,10)     
                                             ,true);
                           
                       }
                       
                      /* for(int i=0;i<(pCant-vListaDetalleGuia.size());i++){
                           mainPRN.printLine("    .", true);
                       }*/
                      mainPRN.printLine("            " + " ", true);
                      mainPRN.printLine( FarmaPRNUtility.alinearDerecha("Neto " + vValorNeto_Sin_Igv+"       IGV: "+vValorIGV+"        TOTAL COTIZACION: "+vValorNeto,131), true);
                       mainPRN.printLine("            " + " ", true);
                       //
                       
               mainPRN.deactivateCondensed();
               mainPRN.endPrintService();
               
           FarmaUtility.showMessage(this, "Se envió  a imprimir correctamente la cotizacion.", txtFechaIni);

           // fin impr guia
       }
       
       
       cerrarVentana(true);  
            
                                   
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    public void pintaProductosSinStock(){
        
        if (tblRegistroVentas.getRowCount() > 0) {
            /*FarmaUtility.ordenar(tblSolicitud, tblModelSolicitud, Constants.COL_NUMERO_DOCUMENTO,
                                 FarmaConstants.ORDEN_ASCENDENTE);*/
            
            int pFila = tblRegistroVentas.getSelectedRow();
            
            
            int cols = tblRegistroVentas.getColumnCount();
            for (int i = 0; i < cols; i++) {
                tblRegistroVentas.getColumnModel().getColumn(i).setCellRenderer(new CustomRenderer());
            }
            
            
            /*tblProductos.clearSelection();
            tblProductos.setRowSelectionInterval(pFila, pFila);*/
            
            //tblProductos.getSelectionModel().setSelectionInterval (0, 0);
            //ConstantsModuloVenta.columnsListaProductos, ConstantsModuloVenta.defaultValuesListaProductos,
            
            
            /*ArrayList rowsWithOtherColor = new ArrayList();
            for(int i = 0; i < tableModelListaPrecioProductos.data.size(); i++){
              if ( (Integer.parseInt(((ArrayList)tableModelListaPrecioProductos.data.get(i)).get(5).toString().trim())) <= 0)
              { //cantguias 8 es 0
                rowsWithOtherColor.add(String.valueOf(i));
              }
            }

            FarmaUtility.initSelectListCleanColumns(tblProductos, tableModelListaPrecioProductos,
               ConstantsModuloVenta.columnsListaProductos,rowsWithOtherColor,Color.white,Color.red,false);*/
            
            
          
        }
        tblRegistroVentas.getTableHeader().setReorderingAllowed(false);
        tblRegistroVentas.getTableHeader().setResizingAllowed(false);
        
        //tblProductos.getSelectionModel().setSelectionInterval (0, 0);

    }
    
    class CustomRenderer extends DefaultTableCellRenderer 
    {
    
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            /* Dflores: genera error
            if(tableModelRegistroVentas.data.size()>0){
                
                // campo para validar
                int  intValor=Integer.parseInt(FarmaUtility.getValueFieldArrayList(tableModelRegistroVentas.data, row, 9).toString().trim());
            
                Color prioridad1 = new Color(249,145,127) ;//ALTA
                //Color prioridad2 = new Color(127,249,154);//MEDIA
                //Color prioridad3 = new Color(249,244,139);//BAJA
    
                //cellComponent.setBackground(Color.WHITE);
            
                if(isSelected){
                   //
                   setBackground(new Color(35,57,145));  
                   setForeground(Color.WHITE);
                }
                else{
                    if(intValor>0){
                       setBackground(prioridad1);
                    }
                    else{
                        setBackground(Color.WHITE);  
                        setForeground(Color.black);
                    }    
                }
            
           }*/
            
           
            /*
            if (row >= table.getRowCount())
                row = table.getRowCount() - 1;
            Rectangle rect = table.getCellRect(row, column, true);
            table.scrollRectToVisible(rect);
            table.clearSelection();
            table.setRowSelectionInterval(row, row);*/
            
            return cellComponent;
            
        
        }
    }      
    
}
