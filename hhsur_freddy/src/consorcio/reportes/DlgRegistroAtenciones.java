package consorcio.reportes;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.*;

import consorcio.reportes.reference.ConstantsReporteAtencion;

import consorcio.reportes.reference.DBReportesAtencion;
import consorcio.reportes.reference.VariablesReporteAtencion;

import java.awt.Desktop;

import java.io.IOException;

import java.text.DecimalFormat;

import java.util.ArrayList;

import venta.reference.UtilityPtoVenta;

import venta.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.ConstantsPtoVenta;

import venta.reportes.DlgDetalleRegistroVentas;

public class DlgRegistroAtenciones extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroAtenciones.class);

    private FarmaTableModel tableModelRegistroVentas,tableModelAcumulado;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane scpListaDetalleAtencion = new JScrollPane();
    private JTable tblRegistroVentas = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel pnlListaDetallada = new JPanel();
    private JScrollPane scpListaAgrupadaAtencion = new JScrollPane();
    private JTable tblAgrupaAtencion = new JTable();
    private JPanel pnlListaAgrupada = new JPanel();
    private JComboBox cmbConsultorio = new JComboBox();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbEspecialidad = new JComboBox();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton btnLimpiar = new JButton();
    private JLabel jLabel4 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JLabel lblTiempoRestante = new JLabel();
    private JLabel jLabel6 = new JLabel();

    // Properties of Program.
        private byte centiseconds = 0;
        private byte seconds = 30;
        private short minutes = 0;

        private DecimalFormat timeFormatter;

        private Timer timer;
    private JTextField txtBusqueda = new JTextField();

    public DlgRegistroAtenciones() {
        this(null, "", false);
    }

    public DlgRegistroAtenciones(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            cmbEspecialidad.setName("CMB_CONSULTORIO");
            cmbConsultorio.setName("CMB_BUS");
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1116, 696));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Reporte Registro de Atenciones");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 5, 1090, 70));
        pnlCriterioBusqueda.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 460, 1095, 30));
        pnlResultados.setFocusable(false);
        scpListaDetalleAtencion.setBounds(new Rectangle(5, 50, 1075, 305));
        scpListaDetalleAtencion.setFocusable(false);
        tblRegistroVentas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Fecha Ingreso");
        btnPeriodo.setBounds(new Rectangle(380, 10, 115, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(380, 40, 120, 20));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(525, 40, 120, 20));
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
        btnBuscar.setBounds(new Rectangle(675, 25, 110, 30));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("# Registros :");
        lblRegsitros_T.setBounds(new Rectangle(15, 0, 130, 30));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 15));
        lblRegsitros_T.setForeground(Color.white);
        lblRegsitros_T.setFocusable(false);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(125, 0, 415, 30));
        lblRegistros.setFont(new Font("SansSerif", 1, 15));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.LEFT);
        lblRegistros.setFocusable(false);
        lblEsc.setBounds(new Rectangle(970, 635, 125, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        //jContentPane.add(jLabelFunction1, null);

        jTabbedPane1.setBounds(new Rectangle(10, 75, 1090, 385));
        pnlListaDetallada.setLayout(null);
        scpListaAgrupadaAtencion.setBounds(new Rectangle(5, 0, 1075, 360));
        pnlListaAgrupada.setLayout(null);
        cmbConsultorio.setBounds(new Rectangle(85, 75, 240, 20));
        cmbConsultorio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbConsultorio_keyPressed(e);
                }
            });
        jLabel1.setText("Consultorio");
        jLabel1.setBounds(new Rectangle(10, 80, 70, 15));
        jLabel1.setForeground(new Color(0, 107, 165));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        cmbEspecialidad.setBounds(new Rectangle(85, 45, 240, 20));
        cmbEspecialidad.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbEspecialidad_itemStateChanged(e);
                }
            });
        cmbEspecialidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbEspecialidad_keyPressed(e);
                }
            });
        cmbEspecialidad.setBounds(new Rectangle(90, 5, 275, 25));
        cmbConsultorio.setBounds(new Rectangle(90, 40, 275, 25));
        jLabel2.setText("Especialidad");
        jLabel2.setBounds(new Rectangle(10, 5, 80, 25));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(SystemColor.window);
        jLabel3.setText("Consultorio");
        jLabel3.setBounds(new Rectangle(10, 40, 75, 20));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setForeground(SystemColor.window);
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBounds(new Rectangle(800, 25, 105, 30));
        btnLimpiar.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setText("<html> \u00a1ALERTA!<br> 1) Solo puede visualizar las especialidades asignadas a su usuario.<br> 2) La b\u00fasqueda solo se puede realizar en los equipos asignados por la empresa.<br> 3) Cualquier intento de ingresar un usuario no permitido se enviara una alerta del ingreso NO AUTORIZADO.<br> 4) Tiene un tiempo m\u00e1ximo de 10 Minutos para hacer consultas y luego debera ingresar su usuario y clave.<br> 5) Se recomienda cambiar su clave de usuario cada 30 d\u00edas. </html>");
        jLabel4.setBounds(new Rectangle(10, 490, 1095, 135));
        jLabel4.setFont(new Font("SansSerif", 1, 17));
        jLabel4.setForeground(new Color(231, 0, 0));
        jPanel1.setBounds(new Rectangle(920, 5, 160, 60));
        jPanel1.setLayout(null);
        lblTiempoRestante.setText("10:59");
        lblTiempoRestante.setBounds(new Rectangle(15, 20, 135, 30));
        lblTiempoRestante.setFont(new Font("SansSerif", 1, 18));
        lblTiempoRestante.setForeground(new Color(231, 0, 0));
        lblTiempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel6.setText("Tiempo Sesi\u00f3n");
        jLabel6.setBounds(new Rectangle(15, 5, 125, 15));
        jLabel6.setForeground(new Color(231, 0, 0));
        jLabel6.setFont(new Font("SansSerif", 1, 13));
        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        txtBusqueda.setBounds(new Rectangle(10, 10, 645, 30));
        scpListaDetalleAtencion.getViewport().add(tblRegistroVentas, null);
        pnlListaDetallada.add(txtBusqueda, null);
        pnlListaDetallada.add(scpListaDetalleAtencion, null);
        jTabbedPane1.addTab("Detalle Atenciones", pnlListaDetallada);
        scpListaAgrupadaAtencion.getViewport().add(tblAgrupaAtencion, null);
        pnlListaAgrupada.add(scpListaAgrupadaAtencion, null);
        jTabbedPane1.addTab("Agrupado por Dia", pnlListaAgrupada);
        jContentPane.add(jLabel4, null);
        jContentPane.add(jTabbedPane1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlResultados, null);
        tblRegistroVentas.setFocusable(false);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        // pnlExpReporte.setBounds(new Rectangle(945, 0, 60, 30));
        jPanel1.add(jLabel6, null);
        jPanel1.add(lblTiempoRestante, null);
        pnlCriterioBusqueda.add(jPanel1, null);
        pnlCriterioBusqueda.add(btnLimpiar, null);
        pnlCriterioBusqueda.add(jLabel3, null);
        pnlCriterioBusqueda.add(jLabel2, null);
        pnlCriterioBusqueda.add(cmbEspecialidad, null);
        pnlCriterioBusqueda.add(cmbConsultorio, null);
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
        cargaCombos();
        solicitarClave();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        tableModelRegistroVentas =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRegistroVentas, ConstantsReporteAtencion.defaultValuesListaRegistroVentas,
                                    0);
        FarmaUtility.initSimpleList(tblRegistroVentas, tableModelRegistroVentas,
                                    ConstantsReporteAtencion.columnsListaRegistroVentas);
        TableRowFilterSupport.forTable(tblRegistroVentas).searchable(true).apply();
        
        
        tableModelAcumulado =
                new FarmaTableModel(ConstantsReporteAtencion.columnaListaAtencionAgrupada, ConstantsReporteAtencion.defaultValuesListaAtencionAgrupada,
                                    0);
        FarmaUtility.initSimpleList(tblAgrupaAtencion, tableModelAcumulado,
                                    ConstantsReporteAtencion.columnaListaAtencionAgrupada);
        TableRowFilterSupport.forTable(tblAgrupaAtencion).searchable(true).apply();


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
        
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, txtBusqueda, 0);
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                VariablesReporteAtencion.vCorrelativo =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 0)).trim();
                VariablesReporteAtencion.vCliente =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 4)).trim();
                //VariablesReporteAtencion.vDireccion = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
                VariablesReporteAtencion.vRuc =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 7)).trim();
                VariablesReporteAtencion.vFecha =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 3)).trim();
                VariablesReporteAtencion.vHora =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 8)).trim();
                VariablesReporteAtencion.vUsuario =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 9)).trim();
                VariablesReporteAtencion.vEstado =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 10)).trim();
                VariablesReporteAtencion.vNComprobante =
                        ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(), 2)).trim();
                log.debug(VariablesReporteAtencion.vCorrelativo + VariablesReporteAtencion.vCliente +
                          VariablesReporteAtencion.vDireccion + VariablesReporteAtencion.vRuc +
                          VariablesReporteAtencion.vFecha + VariablesReporteAtencion.vHora +
                          VariablesReporteAtencion.vUsuario + VariablesReporteAtencion.vEstado);
                listadoDetalleVentas();
            }
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
                txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
                String FechaInicio = txtFechaIni.getText().trim();
                String FechaFin = txtFechaFin.getText().trim();
                log.debug(FechaFin + FechaInicio);
                resumenVentas();
            }
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

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin) {
        VariablesReporteAtencion.vFechaInicio = pFechaInicio;
        VariablesReporteAtencion.vFechaFin = pFechaFin;
        cargaRegistroVentas();
    }

    private void cargaRegistroVentas() {
        try {
            log.debug(VariablesReporteAtencion.vFechaInicio);
            log.debug(VariablesReporteAtencion.vFechaFin);
            // muestra del operador para ver todos.
            tblRegistroVentas.setRowSorter(null);
            DBReportesAtencion.cargaListaRegistroVentas(tableModelRegistroVentas,
                                                        VariablesReporteAtencion.vFechaInicio,
                                                        VariablesReporteAtencion.vFechaFin, "000",
            FarmaLoadCVL.getCVLCode("CMB_CONSULTORIO", cmbEspecialidad.getSelectedIndex()),
            FarmaLoadCVL.getCVLCode("CMB_BUS", cmbConsultorio.getSelectedIndex())
                                                        );
            if (tblRegistroVentas.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni);

                lblRegistros.setText("" + tblRegistroVentas.getRowCount());
                return;
            } else {
                FarmaUtility.moveFocus(tblRegistroVentas);
                
                tblAgrupaAtencion.setRowSorter(null);
                DBReportesAtencion.cargaListaAgrupaAtencion(tableModelAcumulado,
                                                            VariablesReporteAtencion.vFechaInicio,
                                                            VariablesReporteAtencion.vFechaFin, "000",
                FarmaLoadCVL.getCVLCode("CMB_CONSULTORIO", cmbEspecialidad.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode("CMB_BUS", cmbConsultorio.getSelectedIndex())
                                                            );
            }
           // FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);

            lblRegistros.setText("" + tblRegistroVentas.getRowCount());
            FarmaUtility.moveFocusJTable(tblRegistroVentas);
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
        DlgDetalleRegistroVentas dlgDetalleRegistroVentas = new DlgDetalleRegistroVentas(myParentFrame, "", true);
        dlgDetalleRegistroVentas.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
    }

    private void resumenVentas() {

    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
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
                    buscaRegistroVentas(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni);

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cmbConsultorio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtFechaIni);
        } else {
            chkKeyPressed(e);
        }  
    }

    private void cmbEspecialidad_itemStateChanged(ItemEvent e) {
        String vCodEspecialidad;
        ArrayList parametros = new ArrayList();
        vCodEspecialidad = FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex());
        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(vCodEspecialidad);
        FarmaLoadCVL.unloadCVL(cmbConsultorio, cmbConsultorio.getName());
        FarmaLoadCVL.loadCVLFromSP(cmbConsultorio, cmbConsultorio.getName(), "HHC_LABORATORIO.GET_CONSULTORIO(?,?,?,?)",
                                   parametros, false, true);
        
        if(cmbConsultorio.getItemCount()==2){
            cmbConsultorio.setSelectedIndex(1);
        }
        
    }

    private void cmbEspecialidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(cmbConsultorio);
        } else {
            chkKeyPressed(e);
        }    
        
    }

    private void cargaCombos() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        FarmaLoadCVL.loadCVLFromSP(cmbEspecialidad, cmbEspecialidad.getName(), "HHC_LABORATORIO.GET_ESPECIALIDAD(?,?,?)",
                                   parametros, false, true);
        
        if(cmbEspecialidad.getItemCount()==2){            
            cmbEspecialidad.setSelectedIndex(1);
            
            /*parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(FarmaVariables.vNuSecUsu);
            String vCodEspecialidad = FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex());
            parametros.add(vCodEspecialidad);
            FarmaLoadCVL.unloadCVL(cmbConsultorio, cmbConsultorio.getName());
            FarmaLoadCVL.loadCVLFromSP(cmbConsultorio, cmbConsultorio.getName(), "HHC_LABORATORIO.GET_CONSULTORIO(?,?,?,?)",
                                       parametros, false, true);*/
            /*if(cmbConsultorio.getItemCount()==1){
                cmbConsultorio.setSelectedIndex(0);
            }*/
        }
            
    }

    private void solicitarClave() {
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaUtility.showMessage(this, 
                "¡ALERTA!\n"+
                "1) Solo puede visualizar las especialidades asignadas a su usuario.\n" +
                "2) La búsqueda solo se puede realizar en los equipos asignados por la empresa.\n" +
                "3) Cualquier intento de ingresar un usuario no permitido se enviara una alerta del ingreso NO AUTORIZADO.\n" +
                "4) Tiene un tiempo máximo de 10 Minutos para hacer consultas y luego debera ingresar su usuario y clave.\n"+
                "5) Se recomienda cambiar su clave de usuario cada 30 días.", cmbEspecialidad);
            
            iniciaTiempo();
            
            lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                                   + timeFormatter.format(seconds) + "."
                                   + timeFormatter.format(centiseconds));
            timer.start();
        }
        else{
            FarmaUtility.showMessage(this, "", cmbEspecialidad);
            cerrarVentana(true);
        }
            
    }

    private void iniciaTiempo() {
        centiseconds = 0;
        seconds = 0;
        minutes = 10;
                        
        timeFormatter = new DecimalFormat("00");
               timer = new Timer(10, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       if (centiseconds > 0) {
                           centiseconds--;
                       } else {
                           if (seconds == 0 && minutes == 0) {
                               timer.stop();
                           } else if (seconds > 0) {
                               seconds--;
                               centiseconds = 99;
                           } else if (minutes > 0) {
                               minutes--;
                               seconds = 59;
                               centiseconds = 99;
                           }
                       }
                       lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                               + timeFormatter.format(seconds) + "."
                               + timeFormatter.format(centiseconds));
                       
                       if((centiseconds + seconds + minutes)==0){
                           //timer.stop();
                           centiseconds = 0;
                           seconds = 0;
                           minutes = 2;
                           lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                                   + timeFormatter.format(seconds) + "."
                                   + timeFormatter.format(centiseconds));
                           timer.stop();
                           solicitarClave();
                       }
                       
                   }
               });

               /*lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                       + timeFormatter.format(seconds) + "."
                       + timeFormatter.format(centiseconds));*/
    }
}
