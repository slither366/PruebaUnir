package consorcio.liquidacion;

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
import javax.swing.JCheckBox;
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

import consorcio.reportes.DlgProcesoPlanillaHH;
import consorcio.reportes.reference.ConstantsReporteAtencion;

import consorcio.reportes.reference.DBReportesAtencion;
import consorcio.reportes.reference.VariablesReporteAtencion;

import java.awt.Desktop;

import java.io.FileOutputStream;
import java.io.IOException;

import java.net.InetAddress;

import java.net.UnknownHostException;

import java.text.DateFormat;
import java.text.DecimalFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import venta.reference.UtilityPtoVenta;

import venta.reportes.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.DlgProcesar;

import venta.reference.ConstantsPtoVenta;

import venta.reportes.DlgDetalleRegistroVentas;

public class DlgReporteTotalAtenciones extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgReporteTotalAtenciones.class);

    private FarmaTableModel modelRpt_01,modelRpt_02,modelRpt_03,modelRpt_04,modelRpt_05;
    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane scpListaDetalleAtencion = new JScrollPane();
    private JTable tblRpt_02 = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni_Atencion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin_Atencion = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel pnlListaDetallada = new JPanel();
    private JScrollPane scpListaAgrupadaAtencion = new JScrollPane();
    private JTable tblRpt_01 = new JTable();
    private JPanel pnlListaAgrupada = new JPanel();
    private JComboBox cmbConsultorio = new JComboBox();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbEspecialidad = new JComboBox();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton btnLimpiar = new JButton();

    // Properties of Program.
        private byte centiseconds = 0;
        private byte seconds = 30;
        private short minutes = 0;

        private DecimalFormat timeFormatter;

        private Timer timer;
    private JTextField txtIniVenta = new JTextField();
    private JTextField txtFinVenta = new JTextField();
    private JLabel jLabel5 = new JLabel();
    private JComboBox cmbMedico = new JComboBox();
    private JLabel jLabel6 = new JLabel();
    private JComboBox cmbServicio = new JComboBox();
    private JLabel jLabel7 = new JLabel();
    private JComboBox cmbTurno = new JComboBox();
    private JLabel jLabel8 = new JLabel();
    private JComboBox cmbCondicion = new JComboBox();
    private JButton jButton1 = new JButton();
    private JPanel pnlComprobante = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRpt_03 = new JTable();
    private JButton jButton2 = new JButton();
    private JPanel pnlProdNormal = new JPanel();
    private JPanel pnlProdEspecial = new JPanel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JTable tblProdNormal = new JTable();
    private JTable tblProdEspecial = new JTable();

    public DlgReporteTotalAtenciones() {
        this(null, "", false);
    }

    public DlgReporteTotalAtenciones(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            cmbEspecialidad.setName("CMB_CONSULTORIO");
            cmbConsultorio.setName("CMB_BUS");
            cmbMedico.setName("CMB_MEDICO");
            cmbServicio.setName("CMB_SERVICIO");
            cmbTurno.setName("CMB_TURNO");
            cmbCondicion.setName("CMB_CONDICION");
            initialize();
            
           
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1486, 738));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Reporte Total Atencion / Producci\u00f3n");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 1455, 155));
        pnlCriterioBusqueda.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 630, 1455, 30));
        pnlResultados.setFocusable(false);
        scpListaDetalleAtencion.setBounds(new Rectangle(5, 0, 1435, 435));
        scpListaDetalleAtencion.setFocusable(false);
        tblRpt_02.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRegistroVentas_keyPressed(e);
            }
        });
        btnPeriodo.setText("Fecha Atenci\u00f3n");
        btnPeriodo.setBounds(new Rectangle(380, 10, 115, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.setFocusable(false);
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni_Atencion.setBounds(new Rectangle(485, 10, 120, 20));
        txtFechaIni_Atencion.setLengthText(10);
        txtFechaIni_Atencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin_Atencion.setBounds(new Rectangle(620, 10, 120, 20));
        txtFechaFin_Atencion.setLengthText(10);
        txtFechaFin_Atencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("2 ) Buscar");
        btnBuscar.setBounds(new Rectangle(1100, 15, 110, 25));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
        });
        lblEsc.setBounds(new Rectangle(1340, 670, 125, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        //jContentPane.add(jLabelFunction1, null);

        jTabbedPane1.setBounds(new Rectangle(10, 170, 1455, 460));
        pnlListaDetallada.setLayout(null);
        scpListaAgrupadaAtencion.setBounds(new Rectangle(5, 0, 1435, 425));
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
        jLabel3.setBounds(new Rectangle(15, 40, 75, 20));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setForeground(SystemColor.window);
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBounds(new Rectangle(1100, 65, 110, 25));
        btnLimpiar.setFont(new Font("SansSerif", 1, 11));
        txtIniVenta.setBounds(new Rectangle(485, 45, 120, 20));
        txtIniVenta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jTextField1_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtIniVenta_keyReleased(e);
                }
            });
        txtFinVenta.setBounds(new Rectangle(620, 45, 120, 20));
        txtFinVenta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaFin_Venta_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFinVenta_keyReleased(e);
                }
            });
        jLabel5.setText("M\u00e9dico");
        jLabel5.setBounds(new Rectangle(20, 75, 70, 15));
        jLabel5.setForeground(SystemColor.window);
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        cmbMedico.setBounds(new Rectangle(90, 75, 650, 25));
        cmbMedico.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbMedico_keyPressed(e);
                }
            });
        jLabel6.setText("Servicio");
        jLabel6.setBounds(new Rectangle(15, 120, 70, 15));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(SystemColor.window);
        cmbServicio.setBounds(new Rectangle(90, 115, 650, 25));
        cmbServicio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbServicio_keyPressed(e);
                }
            });
        jLabel7.setText("Turno");
        jLabel7.setBounds(new Rectangle(795, 15, 50, 15));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel7.setForeground(SystemColor.window);
        cmbTurno.setBounds(new Rectangle(840, 10, 210, 25));
        cmbTurno.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTurno_keyPressed(e);
                }
            });
        jLabel8.setText("Condici\u00f3n");
        jLabel8.setBounds(new Rectangle(775, 50, 60, 15));
        jLabel8.setFont(new Font("SansSerif", 1, 11));
        jLabel8.setForeground(SystemColor.window);
        cmbCondicion.setBounds(new Rectangle(840, 50, 210, 25));
        cmbCondicion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbCondicion_keyPressed(e);
                }
            });
        jButton1.setText("Exportar Excel");
        jButton1.setBounds(new Rectangle(1100, 115, 125, 20));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        pnlComprobante.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(5, 10, 1430, 420));
        jButton2.setText("1) PROCESAR RESUMEN");
        jButton2.setBounds(new Rectangle(840, 100, 165, 20));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        pnlProdNormal.setLayout(null);
        pnlProdEspecial.setLayout(null);
        jScrollPane2.setBounds(new Rectangle(15, 5, 1425, 425));
        jScrollPane3.setBounds(new Rectangle(10, 5, 1425, 430));
        scpListaAgrupadaAtencion.getViewport().add(tblRpt_01, null);
        pnlListaAgrupada.add(scpListaAgrupadaAtencion, null);
        jTabbedPane1.addTab("Agrupado por Dia", pnlListaAgrupada);

        scpListaDetalleAtencion.getViewport().add(tblRpt_02, null);
        pnlListaDetallada.add(scpListaDetalleAtencion, null);
        jTabbedPane1.addTab("Agrupado por Servicio", pnlListaDetallada);
        jTabbedPane1.addTab("Serv.Normal", pnlProdNormal);
        jTabbedPane1.addTab("Serv.Especial", pnlProdEspecial);
        
        jScrollPane1.getViewport().add(tblRpt_03, null);
        pnlComprobante.add(jScrollPane1, null);
        jTabbedPane1.addTab("Detallado", pnlComprobante);
        jScrollPane2.getViewport().add(tblProdNormal, null);
        pnlProdNormal.add(jScrollPane2, null);
        jScrollPane3.getViewport().add(tblProdEspecial, null);
        pnlProdEspecial.add(jScrollPane3, null);
        jContentPane.add(jTabbedPane1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlResultados, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        tblRpt_02.setFocusable(false);
        // pnlExpReporte.setBounds(new Rectangle(945, 0, 60, 30));
        pnlCriterioBusqueda.add(jButton2, null);
        pnlCriterioBusqueda.add(jButton1, null);
        pnlCriterioBusqueda.add(cmbCondicion, null);
        pnlCriterioBusqueda.add(jLabel8, null);
        pnlCriterioBusqueda.add(cmbTurno, null);
        pnlCriterioBusqueda.add(jLabel7, null);
        pnlCriterioBusqueda.add(cmbServicio, null);
        pnlCriterioBusqueda.add(jLabel6, null);
        pnlCriterioBusqueda.add(cmbMedico, null);
        pnlCriterioBusqueda.add(jLabel5, null);
        pnlCriterioBusqueda.add(txtFinVenta, null);
        pnlCriterioBusqueda.add(txtIniVenta, null);
        pnlCriterioBusqueda.add(btnLimpiar, null);
        pnlCriterioBusqueda.add(jLabel3, null);
        pnlCriterioBusqueda.add(jLabel2, null);
        pnlCriterioBusqueda.add(cmbEspecialidad, null);
        pnlCriterioBusqueda.add(cmbConsultorio, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin_Atencion, null);
        pnlCriterioBusqueda.add(txtFechaIni_Atencion, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        /* jLabel4.setVisible(false);
        txtIniVenta.setVisible(false);
        txtFinVenta.setVisible(false);*/
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
        cargaCombos();
        //solicitarClave();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
        
        
        modelRpt_01 =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_01, ConstantsReporteAtencion.defaultValuesListaRpt_01,
                                    0);
        FarmaUtility.initSimpleList(tblRpt_01, modelRpt_01,
                                    ConstantsReporteAtencion.columnsListaRpt_01);
        TableRowFilterSupport.forTable(tblRpt_01).searchable(true).apply();
        
        
        modelRpt_02 =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_02, ConstantsReporteAtencion.defaultValuesListaRpt_02,
                                    0);
        FarmaUtility.initSimpleList(tblRpt_02, modelRpt_02,
                                    ConstantsReporteAtencion.columnsListaRpt_02);
        TableRowFilterSupport.forTable(tblRpt_02).searchable(true).apply();
        
        modelRpt_03 =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_03, ConstantsReporteAtencion.defaultValuesListaRpt_03,
                                    0);
        FarmaUtility.initSimpleList(tblRpt_03, modelRpt_03,
                                    ConstantsReporteAtencion.columnsListaRpt_03);
        TableRowFilterSupport.forTable(tblRpt_03).searchable(true).apply();
        

        modelRpt_04 =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_04, ConstantsReporteAtencion.defaultValuesListaRpt_04,
                                    0);
        FarmaUtility.initSimpleList(tblProdNormal, modelRpt_04,
                                    ConstantsReporteAtencion.columnsListaRpt_04);
        TableRowFilterSupport.forTable(tblProdNormal).searchable(true).apply();

        modelRpt_05 =
                new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_05, ConstantsReporteAtencion.defaultValuesListaRpt_05,
                                    0);
        FarmaUtility.initSimpleList(tblProdEspecial, modelRpt_05,
                                    ConstantsReporteAtencion.columnsListaRpt_05);
        TableRowFilterSupport.forTable(tblProdEspecial).searchable(true).apply();        

    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin_Atencion);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();

        else if */
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void tblRegistroVentas_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni_Atencion);
        
       txtIniVenta.setVisible(false);
       txtFinVenta.setVisible(false);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni_Atencion);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRpt_02.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRpt_02);
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, txtBusqueda, 0);
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        }
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            if (tblRpt_02.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni_Atencion);
                FarmaUtility.moveFocus(txtFechaIni_Atencion);
            } else {
                VariablesReporteAtencion.vCorrelativo =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 0)).trim();
                VariablesReporteAtencion.vCliente =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 4)).trim();
                //VariablesReporteAtencion.vDireccion = ((String)tblRegistroVentas.getValueAt(tblRegistroVentas.getSelectedRow(),7)).trim();
                VariablesReporteAtencion.vRuc =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 7)).trim();
                VariablesReporteAtencion.vFecha =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 3)).trim();
                VariablesReporteAtencion.vHora =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 8)).trim();
                VariablesReporteAtencion.vUsuario =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 9)).trim();
                VariablesReporteAtencion.vEstado =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 10)).trim();
                VariablesReporteAtencion.vNComprobante =
                        ((String)tblRpt_02.getValueAt(tblRpt_02.getSelectedRow(), 2)).trim();
                log.debug(VariablesReporteAtencion.vCorrelativo + VariablesReporteAtencion.vCliente +
                          VariablesReporteAtencion.vDireccion + VariablesReporteAtencion.vRuc +
                          VariablesReporteAtencion.vFecha + VariablesReporteAtencion.vHora +
                          VariablesReporteAtencion.vUsuario + VariablesReporteAtencion.vEstado);
                listadoDetalleVentas();
            }
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblRpt_02.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Ingrese un criterio de Busqueda", txtFechaIni_Atencion);
                FarmaUtility.moveFocus(txtFechaIni_Atencion);
            } else {
                txtFechaIni_Atencion.setText(txtFechaIni_Atencion.getText().trim().toUpperCase());
                txtFechaFin_Atencion.setText(txtFechaFin_Atencion.getText().trim().toUpperCase());
                String FechaInicio = txtFechaIni_Atencion.getText().trim();
                String FechaFin = txtFechaFin_Atencion.getText().trim();
                log.debug(FechaFin + FechaInicio);
                resumenVentas();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni_Atencion, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin_Atencion, e);
    }

    private void buscaRegistroVentas(String pFechaInicio, String pFechaFin,
                                     String pIniVenta,String pFinVenta) {
        VariablesReporteAtencion.vFechaInicio = pFechaInicio;
        VariablesReporteAtencion.vFechaFin = pFechaFin;
        cargaRegistroVentas(pIniVenta,pFinVenta);
    }

    private void cargaRegistroVentas(String pIniVenta,String pFinVenta) {
        try {
            
            modelRpt_01.data.clear();
            modelRpt_02.data.clear();
            modelRpt_03.data.clear();
            modelRpt_04.data.clear();
            modelRpt_05.data.clear();
            
            tblRpt_01.repaint();            
            tblRpt_02.repaint();            
            tblRpt_03.repaint();            
            tblProdNormal.repaint();            
            tblProdEspecial.repaint();
            
            log.debug(VariablesReporteAtencion.vFechaInicio);
            log.debug(VariablesReporteAtencion.vFechaFin);
            // muestra del operador para ver todos.
            tblRpt_01.setRowSorter(null);
            DBReportesAtencion.getLista_rpt_01(modelRpt_01,
                                                        VariablesReporteAtencion.vFechaInicio,
                                                        VariablesReporteAtencion.vFechaFin, 
                                                        "000",
            FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex()),
            FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex()),
                                                        pIniVenta,
                                                        pFinVenta,
            FarmaLoadCVL.getCVLCode(cmbMedico.getName(), cmbMedico.getSelectedIndex()),
            FarmaLoadCVL.getCVLCode(cmbServicio.getName(), cmbServicio.getSelectedIndex()),
            FarmaLoadCVL.getCVLCode(cmbTurno.getName(), cmbTurno.getSelectedIndex()),
            FarmaLoadCVL.getCVLCode(cmbCondicion.getName(), cmbCondicion.getSelectedIndex())
                                                        );
            if (tblRpt_01.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro resultados para la busqueda", txtFechaIni_Atencion);

               // lblRegistros.setText("" + tblRpt_01.getRowCount());
                return;
            } else {
                
                tblRpt_02.setRowSorter(null);
                DBReportesAtencion.getLista_rpt_02(modelRpt_02,
                                                            VariablesReporteAtencion.vFechaInicio,
                                                            VariablesReporteAtencion.vFechaFin, 
                                                            "000",
                FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex()),
                                                            pIniVenta,
                                                            pFinVenta,
                FarmaLoadCVL.getCVLCode(cmbMedico.getName(), cmbMedico.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbServicio.getName(), cmbServicio.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbTurno.getName(), cmbTurno.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbCondicion.getName(), cmbCondicion.getSelectedIndex())
                                                            );

                tblRpt_03.setRowSorter(null);
                DBReportesAtencion.getLista_rpt_03(modelRpt_03,
                                                            VariablesReporteAtencion.vFechaInicio,
                                                            VariablesReporteAtencion.vFechaFin, 
                                                            "000",
                FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex()),
                                                            pIniVenta,
                                                            pFinVenta,
                FarmaLoadCVL.getCVLCode(cmbMedico.getName(), cmbMedico.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbServicio.getName(), cmbServicio.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbTurno.getName(), cmbTurno.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbCondicion.getName(), cmbCondicion.getSelectedIndex())
                                                            );
                
                tblProdNormal.setRowSorter(null);
                DBReportesAtencion.getLista_rpt_04(modelRpt_04,
                                                            VariablesReporteAtencion.vFechaInicio,
                                                            VariablesReporteAtencion.vFechaFin, 
                                                            "000",
                FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex()),
                                                            pIniVenta,
                                                            pFinVenta,
                FarmaLoadCVL.getCVLCode(cmbMedico.getName(), cmbMedico.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbServicio.getName(), cmbServicio.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbTurno.getName(), cmbTurno.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbCondicion.getName(), cmbCondicion.getSelectedIndex())
                                                            );
                
                
                tblProdEspecial.setRowSorter(null);
                DBReportesAtencion.getLista_rpt_05(modelRpt_05,
                                                            VariablesReporteAtencion.vFechaInicio,
                                                            VariablesReporteAtencion.vFechaFin, 
                                                            "000",
                FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex()),
                                                            pIniVenta,
                                                            pFinVenta,
                FarmaLoadCVL.getCVLCode(cmbMedico.getName(), cmbMedico.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbServicio.getName(), cmbServicio.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbTurno.getName(), cmbTurno.getSelectedIndex()),
                FarmaLoadCVL.getCVLCode(cmbCondicion.getName(), cmbCondicion.getSelectedIndex())
                                                            );
                
                
            }
           // FarmaUtility.ordenar(tblRegistroVentas, tableModelRegistroVentas, 3, FarmaConstants.ORDEN_ASCENDENTE);

            //slblRegistros.setText("" + tblRpt_01.getRowCount());
            FarmaUtility.moveFocusJTable(tblRpt_01);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni_Atencion);
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
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni_Atencion, txtFechaFin_Atencion, false, true, true, true))
            retorno = false;

        return retorno;
    }


    private void busqueda() {
        if (validarCampos()) {
            
            txtFechaIni_Atencion.setText(txtFechaIni_Atencion.getText().trim().toUpperCase());
            txtFechaFin_Atencion.setText(txtFechaFin_Atencion.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni_Atencion.getText().trim();
            String FechaFin = txtFechaFin_Atencion.getText().trim();
            
            txtIniVenta.setText(txtIniVenta.getText().trim().toUpperCase());
            txtFinVenta.setText(txtFinVenta.getText().trim().toUpperCase());
            String FechaInicio_Venta = txtIniVenta.getText().trim();
            String FechaFin_venta = txtFinVenta.getText().trim();
            
            boolean pPermiteBuscar = false;
            
            boolean pFechaVenta = false;
            boolean pFechaAtencion = false;
            
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    pPermiteBuscar = true;
                    pFechaAtencion = true;
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas de atencion", txtFechaIni_Atencion);
            }/* else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni_Atencion);*/
            
            /*
            if (FechaInicio_Venta.length() > 0 || FechaFin_venta.length() > 0) {
                char primerkeyCharFI = FechaInicio_Venta.charAt(0);
                char ultimokeyCharFI = FechaInicio_Venta.charAt(FechaInicio_Venta.length() - 1);
                char primerkeyCharFF = FechaFin_venta.charAt(0);
                char ultimokeyCharFF = FechaFin_venta.charAt(FechaFin_venta.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    pPermiteBuscar = true;
                    pFechaVenta = true;
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas de venta", txtFinVenta);
            }
    */
            pFechaVenta = true;
            if(pFechaAtencion==false||pFechaVenta==false){
                FarmaUtility.showMessage(this, "Debe de ingresar un rango de fecha", txtFechaIni_Atencion);
            }
            else
                if(pPermiteBuscar){
                    FechaInicio_Venta = "X";
                    FechaFin_venta  = "X";
                    buscaRegistroVentas(FechaInicio, FechaFin,FechaInicio_Venta,FechaFin_venta);
                }


        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void cmbConsultorio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtFechaIni_Atencion);
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
        
        // CARGA LOS SERVICIOS
        
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vCodEspecialidad);
        FarmaLoadCVL.unloadCVL(cmbServicio, cmbServicio.getName());
        FarmaLoadCVL.loadCVLFromSP(cmbServicio, cmbServicio.getName(), "HHSUR_NEW_RPT_CONSULTORIO.get_prod_servicios(?,?,?)",
                                   parametros, false, true);
        
        
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
        
        parametros = new ArrayList();
                parametros.add(FarmaVariables.vCodGrupoCia);
                parametros.add(FarmaVariables.vCodLocal);
                parametros.add(FarmaVariables.vNuSecUsu);
                FarmaLoadCVL.loadCVLFromSP(cmbTurno, cmbTurno.getName(), "HHSUR_NEW_RPT_CONSULTORIO.GET_TURNO(?,?,?)",
                                           parametros, false, true);
                
        parametros = new ArrayList();
                parametros.add(FarmaVariables.vCodGrupoCia);
                parametros.add(FarmaVariables.vCodLocal);
                parametros.add(FarmaVariables.vNuSecUsu);
                FarmaLoadCVL.loadCVLFromSP(cmbCondicion, cmbCondicion.getName(), "HHSUR_NEW_RPT_CONSULTORIO.GET_CONDICION(?,?,?)",
                                           parametros, false, true);       
                
        parametros = new ArrayList();
                parametros.add(FarmaVariables.vCodGrupoCia);
                parametros.add(FarmaVariables.vCodLocal);
                parametros.add(FarmaVariables.vNuSecUsu);
                FarmaLoadCVL.loadCVLFromSP(cmbMedico, cmbMedico.getName(), "HHSUR_NEW_RPT_CONSULTORIO.GET_MEDICO(?,?,?)",
                                           parametros, false, true);                  
            
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
            
           /* lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                                   + timeFormatter.format(seconds) + "."
                                   + timeFormatter.format(centiseconds));*/
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
                      /* lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                               + timeFormatter.format(seconds) + "."
                               + timeFormatter.format(centiseconds));*/
                       
                       if((centiseconds + seconds + minutes)==0){
                           //timer.stop();
                           centiseconds = 0;
                           seconds = 0;
                           minutes = 2;
                          /* lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                                   + timeFormatter.format(seconds) + "."
                                   + timeFormatter.format(centiseconds));-**/
                           timer.stop();
                           solicitarClave();
                       }
                       
                   }
               });

               /*lblTiempoRestante.setText(timeFormatter.format(minutes) + ":"
                       + timeFormatter.format(seconds) + "."
                       + timeFormatter.format(centiseconds));*/
    }

    private void cmbMedico_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void cmbServicio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void cmbTurno_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void cmbCondicion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void jTextField1_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_Venta_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtIniVenta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtIniVenta, e);
    }

    private void txtFinVenta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFinVenta, e);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        
        if(tblRpt_01.getRowCount()<=0){
            FarmaUtility.showMessage(new JDialog(), "No se puede exportar un listado vacío", new JTextField());
        }
        else{
            /*
            modelRpt_01 =
                    new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_01, ConstantsReporteAtencion.defaultValuesListaRpt_01,
                                        0);
            FarmaUtility.initSimpleList(tblRpt_01, modelRpt_01,
                                        ConstantsReporteAtencion.columnsListaRpt_01);
            TableRowFilterSupport.forTable(tblRpt_01).searchable(true).apply();
            
            
            modelRpt_02 =
                    new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_02, ConstantsReporteAtencion.defaultValuesListaRpt_02,
                                        0);
            FarmaUtility.initSimpleList(tblRpt_02, modelRpt_02,
                                        ConstantsReporteAtencion.columnsListaRpt_02);
            TableRowFilterSupport.forTable(tblRpt_02).searchable(true).apply();
            
            modelRpt_03 =
                    new FarmaTableModel(ConstantsReporteAtencion.columnsListaRpt_03, ConstantsReporteAtencion.defaultValuesListaRpt_03,
                                        0);
            FarmaUtility.initSimpleList(tblRpt_03, modelRpt_03,
                                        ConstantsReporteAtencion.columnsListaRpt_03);
            TableRowFilterSupport.forTable(tblRpt_03).searchable(true).apply();
            
             * */
            try {
                //Populate DefaultTableModel data
                DefaultTableModel dtm = new DefaultTableModel();
                DefaultTableModel dtm_2 = new DefaultTableModel();
                DefaultTableModel dtm_3 = new DefaultTableModel();
                DefaultTableModel dtm_4 = new DefaultTableModel();
                DefaultTableModel dtm_5 = new DefaultTableModel();
                Vector<String> cols = new Vector<String>();

                int cantidad = 0;

                for (int a = 0; a < ConstantsReporteAtencion.columnsListaRpt_01.length; a++) {
                    FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_01[a];
                    if (vAux.m_width > 0) {
                        dtm.addColumn(vAux.m_title);
                        cantidad++;
                    }
                }
                Vector<String> dtmrow = null;
                dtmrow = new Vector<String>();
                for (int i = 0; i < ConstantsReporteAtencion.columnsListaRpt_01.length; i++) {
                    FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_01[i];
                    if (vAux.m_width > 0) {
                        dtmrow.add(vAux.m_title);
                    }
                }
                dtm.addRow(dtmrow);
                for (int i = 0; i < tblRpt_01.getRowCount(); i++) {
                    dtmrow = new Vector<String>();
                    for (int j = 0; j < cantidad; j++) {
                        dtmrow.add(tblRpt_01.getValueAt(i, j).toString());

                    }
                    dtm.addRow(dtmrow);
                }
                
                
                //// reporte 2
                cantidad = 0;

                    for (int a = 0; a < ConstantsReporteAtencion.columnsListaRpt_02.length; a++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_02[a];
                        if (vAux.m_width > 0) {
                            dtm_2.addColumn(vAux.m_title);
                            cantidad++;
                        }
                    }
                    Vector<String> dtmrow_2 = null;
                    dtmrow_2 = new Vector<String>();
                    for (int i = 0; i < ConstantsReporteAtencion.columnsListaRpt_02.length; i++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_02[i];
                        if (vAux.m_width > 0) {
                            dtmrow_2.add(vAux.m_title);
                        }
                    }
                    dtm_2.addRow(dtmrow_2);
                    for (int i = 0; i < tblRpt_02.getRowCount(); i++) {
                        dtmrow_2 = new Vector<String>();
                        for (int j = 0; j < cantidad; j++) {
                            dtmrow_2.add(tblRpt_02.getValueAt(i, j).toString());

                        }
                        dtm_2.addRow(dtmrow_2);
                    }


                //// reporte 4
                cantidad = 0;

                    for (int a = 0; a < ConstantsReporteAtencion.columnsListaRpt_04.length; a++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_04[a];
                        if (vAux.m_width > 0) {
                            dtm_4.addColumn(vAux.m_title);
                            cantidad++;
                        }
                    }
                    Vector<String> dtmrow_4 = null;
                    dtmrow_4 = new Vector<String>();
                    for (int i = 0; i < ConstantsReporteAtencion.columnsListaRpt_04.length; i++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_04[i];
                        if (vAux.m_width > 0) {
                            dtmrow_4.add(vAux.m_title);
                        }
                    }
                    dtm_4.addRow(dtmrow_4);
                    for (int i = 0; i < tblProdNormal.getRowCount(); i++) {
                        dtmrow_4 = new Vector<String>();
                        for (int j = 0; j < cantidad; j++) {
                            dtmrow_4.add(tblProdNormal.getValueAt(i, j).toString());

                        }
                        dtm_4.addRow(dtmrow_4);
                    }            

                //// reporte 5
                cantidad = 0;

                    for (int a = 0; a < ConstantsReporteAtencion.columnsListaRpt_05.length; a++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_05[a];
                        if (vAux.m_width > 0) {
                            dtm_5.addColumn(vAux.m_title);
                            cantidad++;
                        }
                    }
                    Vector<String> dtmrow_5 = null;
                    dtmrow_5 = new Vector<String>();
                    for (int i = 0; i < ConstantsReporteAtencion.columnsListaRpt_05.length; i++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_05[i];
                        if (vAux.m_width > 0) {
                            dtmrow_5.add(vAux.m_title);
                        }
                    }
                    dtm_5.addRow(dtmrow_5);
                    for (int i = 0; i < tblProdEspecial.getRowCount(); i++) {
                        dtmrow_5 = new Vector<String>();
                        for (int j = 0; j < cantidad; j++) {
                            dtmrow_5.add(tblProdEspecial.getValueAt(i, j).toString());

                        }
                        dtm_5.addRow(dtmrow_5);
                    }            
                
                
                //// reporte 3
                cantidad = 0;

                    for (int a = 0; a < ConstantsReporteAtencion.columnsListaRpt_03.length; a++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_03[a];
                        if (vAux.m_width > 0) {
                            dtm_3.addColumn(vAux.m_title);
                            cantidad++;
                        }
                    }
                    Vector<String> dtmrow_3 = null;
                    dtmrow_3 = new Vector<String>();
                    for (int i = 0; i < ConstantsReporteAtencion.columnsListaRpt_03.length; i++) {
                        FarmaColumnData vAux = ConstantsReporteAtencion.columnsListaRpt_03[i];
                        if (vAux.m_width > 0) {
                            dtmrow_3.add(vAux.m_title);
                        }
                    }
                    dtm_3.addRow(dtmrow_3);
                    for (int i = 0; i < tblRpt_03.getRowCount(); i++) {
                        dtmrow_3 = new Vector<String>();
                        for (int j = 0; j < cantidad; j++) {
                            dtmrow_3.add(tblRpt_03.getValueAt(i, j).toString());

                        }
                        dtm_3.addRow(dtmrow_3);
                    }                




                Workbook wb = new HSSFWorkbook();
                CreationHelper createhelper = wb.getCreationHelper();
                
                Sheet sheet = wb.createSheet("ReporteDiario");
                Row row = null;
                Cell cell = null;
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    row = sheet.createRow(i);
                    for (int j = 0; j < dtm.getColumnCount(); j++) {
                        cell = row.createCell(j);
                        cell.setCellValue((String)dtm.getValueAt(i, j));
                    }
                }
                
                Sheet sheet_2 = wb.createSheet("Productos.Total");
                Row row_2 = null;
                Cell cell_2 = null;
                for (int i = 0; i < dtm_2.getRowCount(); i++) {
                    row_2 = sheet_2.createRow(i);
                    for (int j = 0; j < dtm_2.getColumnCount(); j++) {
                        cell_2 = row_2.createCell(j);
                        cell_2.setCellValue((String)dtm_2.getValueAt(i, j));
                    }
                }
                
                Sheet sheet_4 = wb.createSheet("Productos.Normal");
                Row row_4 = null;
                Cell cell_4 = null;
                for (int i = 0; i < dtm_4.getRowCount(); i++) {
                    row_4 = sheet_4.createRow(i);
                    for (int j = 0; j < dtm_4.getColumnCount(); j++) {
                        cell_4 = row_4.createCell(j);
                        cell_4.setCellValue((String)dtm_4.getValueAt(i, j));
                    }
                }     
                

                Sheet sheet_5 = wb.createSheet("Productos.Especial");
                Row row_5 = null;              
                Cell cell_5 = null;
                for (int i = 0; i < dtm_5.getRowCount(); i++) {
                    row_5 = sheet_5.createRow(i);
                    for (int j = 0; j < dtm_5.getColumnCount(); j++) {
                        cell_5 = row_5.createCell(j);
                        cell_5.setCellValue((String)dtm_5.getValueAt(i, j));
                    }
                }                
                


                Sheet sheet_3 = wb.createSheet("ReporteDetallado");
                Row row_3 = null;
                Cell cell_3 = null;
                for (int i = 0; i < dtm_3.getRowCount(); i++) {
                    row_3 = sheet_3.createRow(i);
                    for (int j = 0; j < dtm_3.getColumnCount(); j++) {
                        cell_3 = row_3.createCell(j);
                        cell_3.setCellValue((String)dtm_3.getValueAt(i, j));
                    }
                }      
                autoSizeColumns(wb);
                
                String pRuta = "";
                /*JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);          

                int returnVal = chooser.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("You chose to open this directory: " +
                        chooser.getSelectedFile().getAbsolutePath());
                    pRuta = chooser.getSelectedFile().getAbsolutePath();
                }
                else*/
                pRuta = "D:\\"+"planilla_"+getIP()+"_"+getDiaHora()+".xls";
                    
                    
                FileOutputStream out = new FileOutputStream(pRuta);
                wb.write(out);
                out.close();
                
                abrirArchivo(pRuta);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void abrirArchivo(String pRutaFile) {
      //ruta del archivo en el pc
      String file = new String(pRutaFile);
                    //"E:\\pruebaArchivo\\ArchivoPrueba.xlsx"); 
       
     try{ 
       //definiendo la ruta en la propiedad file
       Runtime.getRuntime().exec("cmd /c start "+file);
         
       }catch(IOException e){
          e.printStackTrace();
       } 
      }
    
    public String getDiaHora(){
        String pCadena="";
        Date date = new Date();
        //Caso 1: obtener la hora y salida por pantalla con formato:
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Hora: "+hourFormat.format(date));
        //Caso 2: obtener la fecha y salida por pantalla con formato:
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Fecha: "+dateFormat.format(date));
        //Caso 3: obtenerhora y fecha y salida por pantalla con formato:
        DateFormat hourdateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("Hora y fecha: "+hourdateFormat.format(date));
        
        pCadena = hourdateFormat.format(date);
            return pCadena;
    }
    
    public String getIP(){
        String IP ="";
        try {
           IP = InetAddress.getLocalHost().getHostAddress();
           //direccionIP.setText(IP);
          } catch (UnknownHostException e) {
              IP = FarmaVariables.vIpPc;
           e.printStackTrace();
          }
        return IP;
    }
    
    
    public void autoSizeColumns(Workbook workbook) { 
        int numberOfSheets = workbook.getNumberOfSheets(); 
        for (int i = 0; i < numberOfSheets; i++) { 
         Sheet sheet = workbook.getSheetAt(i); 
         if (sheet.getPhysicalNumberOfRows() > 0) { 
          Row row = sheet.getRow(0); 
          Iterator<Cell> cellIterator = row.cellIterator(); 
          while (cellIterator.hasNext()) { 
           Cell cell = cellIterator.next(); 
           int columnIndex = cell.getColumnIndex(); 
           sheet.autoSizeColumn(columnIndex); 
          } 
         } 
        } 
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        
        if (validarCampos()) {
            
            txtFechaIni_Atencion.setText(txtFechaIni_Atencion.getText().trim().toUpperCase());
            txtFechaFin_Atencion.setText(txtFechaFin_Atencion.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni_Atencion.getText().trim();
            String FechaFin = txtFechaFin_Atencion.getText().trim();
            
            txtIniVenta.setText(txtIniVenta.getText().trim().toUpperCase());
            txtFinVenta.setText(txtFinVenta.getText().trim().toUpperCase());
            String FechaInicio_Venta = txtIniVenta.getText().trim();
            String FechaFin_venta = txtFinVenta.getText().trim();
            
            boolean pPermiteBuscar = false;
            
            boolean pFechaVenta = false;
            boolean pFechaAtencion = false;
            
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    pPermiteBuscar = true;
                    pFechaAtencion = true;
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas de atencion", txtFechaIni_Atencion);
            }/* else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaIni_Atencion);*/
            
            /*
            if (FechaInicio_Venta.length() > 0 || FechaFin_venta.length() > 0) {
                char primerkeyCharFI = FechaInicio_Venta.charAt(0);
                char ultimokeyCharFI = FechaInicio_Venta.charAt(FechaInicio_Venta.length() - 1);
                char primerkeyCharFF = FechaFin_venta.charAt(0);
                char ultimokeyCharFF = FechaFin_venta.charAt(FechaFin_venta.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    pPermiteBuscar = true;
                    pFechaVenta = true;
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas de venta", txtFinVenta);
            }
        */
            pFechaVenta = true;
            if(pFechaAtencion==false||pFechaVenta==false){
                FarmaUtility.showMessage(this, "Debe de ingresar un rango de fecha", txtFechaIni_Atencion);
            }
            else
                if(pPermiteBuscar){
                    FechaInicio_Venta = "X";
                    FechaFin_venta  = "X";
                    //buscaRegistroVentas(FechaInicio, FechaFin,FechaInicio_Venta,FechaFin_venta);
                    DlgProcesoPlanillaHH dlgProcesar = new DlgProcesoPlanillaHH(myParentFrame, "", true,FechaInicio, FechaFin);
                    dlgProcesar.mostrar();
                }


        }
        
    }
}
