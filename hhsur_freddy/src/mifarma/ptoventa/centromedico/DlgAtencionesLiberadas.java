package mifarma.ptoventa.centromedico;

import com.gs.mifarma.componentes.JInputDialog;

import common.DlgLogin;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import javax.swing.SwingConstants;

import common.FarmaColumnData;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import dental.laboratorio.reference.DBMantenimiento;

import java.awt.Component;

import java.awt.Toolkit;

import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import javax.swing.table.DefaultTableCellRenderer;

import mifarma.ptoventa.centromedico.DlgListaProductoReceta.CustomRenderer;
import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;

import venta.caja.DlgMotivoAnularPedido;

import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.modulo_venta.reference.DBModuloVenta;

import venta.modulo_venta.reference.VariablesModuloVentas;

import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;

public class DlgAtencionesLiberadas extends JDialog {

    @SuppressWarnings("compatibility:-112453352585881827")
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DlgAtencionesLiberadas.class);
    //Dflores: 23/08/2019 Menu Derecho
    private JFrame frame1;
    private JTable myJTable;
    private final int COL_COD = 1;
    private final String STAT_EN_CONSULTA = "EN CONSULTA";
    private final String STAT_GRAB_TEMPORAL = "GRABADO TEMPORAL";
    private final String STAT_PEND_TRIAJE = "PEND.TRIAJE";
    private final String STAT_PEND_ATENCION = "PEND.ATENCION";
    private final String STAT_POR_ATENDER = "POR ATENDER";
    private final String STAT_ATENDIDO = "ATENDIDO";
    private String varCodMedico;
    //--
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanelWhite pnlContenedor = new JPanelWhite();
    private JPanelTitle pnlTituloTablaPacientes = new JPanelTitle();
    private JScrollPane scpLiberaciones = new JScrollPane();
    private JTable tblLiberados = new JTable();
    private FarmaTableModel modeltblLiberados;
    private FarmaTableModel modeltblLiberadosClone;
    private FarmaTableModel modeltblLiberadosFiltradoClone;
    private JLabelWhite lblListaPacientes = new JLabelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecIni = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel lblFecIni = new JButtonLabel();
    private JButtonLabel lblFecFin = new JButtonLabel();
    private JRadioButton jRadioButton1 = new JRadioButton();
    //Dflores: 16/08/2019
    private JScrollPane jspEspecialidad = new JScrollPane();
    private JTable tblLaboratorio = new JTable();
    private FarmaTableModel tblModeLaboratorio;
    private JTextField txtEspecialidad = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel lblMensajeFiltro = new JLabel();
    private JButton btnSemaforo0 = new JButton();
    private JPanel jPanelSemaforo0 = new JPanel();
    private JButton btnSemaforo1 = new JButton();
    private JPanel jPanelSemaforo1 = new JPanel();
    private JButton btnSemaforo2 = new JButton();
    private JPanel jPanelSemaforo2 = new JPanel();
    private JButton btnSemaforo3 = new JButton();
    private JPanel jPanelSemaforo3 = new JPanel();
    private JButton btnSemaforo4 = new JButton();
    private JPanel jPanelSemaforo4 = new JPanel();
    private JButton btnSemaforo5 = new JButton();
    private JPanel jPanelSemaforo5 = new JPanel();
    private JButton btnSemaforo6 = new JButton();
    private JPanel jPanelSemaforo6 = new JPanel();
    private JLabel lblSemaforo0 = new JLabel();
    private JLabel lblSemaforo1 = new JLabel();
    private JLabel lblSemaforo2 = new JLabel();
    private JLabel lblSemaforo3 = new JLabel();
    private JLabel lblSemaforo4 = new JLabel();
    private JLabel lblSemaforo5 = new JLabel();
    private JLabel lblSemaforo6 = new JLabel();
    int valGrabadoTemporal = 0;
    int valPendTriaje = 0;
    int valPendAtencion = 0;
    int valPorAtender = 0;
    int valEnConsulta = 0;
    int valAtendido = 0;
    int valTodos = 0;
    Color colRed = new  Color(254, 1, 0);
    Color colFuchsia = new  Color(255, 0, 128);
    Color colOrange = new  Color(255, 128, 0);
    Color colYellow = new Color(254, 192, 0);
    Color colSky = new Color(0, 188, 212);
    Color colGreen = new Color(0, 178, 81);
    Color colBlack = new Color(0,0,0);
    Color colRedLight = new Color(172,194,216);

    public DlgAtencionesLiberadas() {
        this(null, "", false);
    }

    public DlgAtencionesLiberadas(Frame parent, String title, boolean modal) {
        
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void jbInit() throws Exception {
        //this.setSize(new Dimension(935, 360));
        //Dflores: Redimensionado
        this.setSize(new Dimension(1387, 505));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Listado de Atenciones Liberadas");
        
        // TABLA DE LISTA
        pnlContenedor.setSize(new Dimension(1200, 335));
        pnlContenedor.setBackground(colRedLight);
        lblListaPacientes.setText("Lista de Pacientes");
        lblListaPacientes.setBounds(new Rectangle(5, 0, 125, 20));
        lblListaPacientes.setHorizontalAlignment(SwingConstants.LEFT);
        lblListaPacientes.setHorizontalTextPosition(SwingConstants.LEFT);
        //Dflores: 16/08/2019 Redimensionado
        pnlTituloTablaPacientes.setBounds(new Rectangle(10, 70, 1075, 20));
        tblLiberados.setFont(new Font("SansSerif", 0, 11));
        //Dflores: 16/08/2019 Redimensionado
        scpLiberaciones.setBounds(new Rectangle(10, 90, 1075, 285));
        scpLiberaciones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jPanelSemaforo0.add(lblSemaforo0, null);
        pnlContenedor.add(jPanelSemaforo0, null);
        pnlContenedor.add(btnSemaforo0, null);
        jPanelSemaforo1.add(lblSemaforo1, null);
        pnlContenedor.add(jPanelSemaforo1, null);
        pnlContenedor.add(btnSemaforo1, null);
        jPanelSemaforo2.add(lblSemaforo2, null);
        pnlContenedor.add(jPanelSemaforo2, null);
        pnlContenedor.add(btnSemaforo2, null);
        jPanelSemaforo3.add(lblSemaforo3, null);
        pnlContenedor.add(jPanelSemaforo3, null);
        pnlContenedor.add(btnSemaforo3, null);
        jPanelSemaforo4.add(lblSemaforo4, null);
        pnlContenedor.add(jPanelSemaforo4, null);
        pnlContenedor.add(btnSemaforo4, null);
        jPanelSemaforo5.add(lblSemaforo5, null);
        pnlContenedor.add(jPanelSemaforo5, null);
        pnlContenedor.add(btnSemaforo5, null);
        jPanelSemaforo6.add(lblSemaforo6, null);
        pnlContenedor.add(jPanelSemaforo6, null);
        //Dflores: Redimensionado
        pnlContenedor.add(btnSemaforo6, null);
        jspEspecialidad.getViewport().add(tblLaboratorio, null);
        pnlContenedor.add(jspEspecialidad, null);
        pnlTituloTablaPacientes.add(lblListaPacientes, null);
        pnlContenedor.add(pnlTituloTablaPacientes, null);
        scpLiberaciones.getViewport().add(tblLiberados, null);
        pnlContenedor.add(scpLiberaciones, null);
        jPanelHeader1.setBounds(new Rectangle(10, 5, 1340, 60));
        txtFecFin.setBounds(new Rectangle(445, 15, 110, 20));
        txtFecFin.setLengthText(10);
        txtFecFin.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    txtFecFin_keyTyped(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFecFin_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFecFin_keyReleased(e);
                }
            });
        txtFecIni.setBounds(new Rectangle(295, 15, 110, 20));
        txtFecIni.setLengthText(10);
        txtFecIni.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    txtFecIni_keyTyped(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFecIni_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFecIni_keyReleased(e);
                }
            });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(610, 15, 85, 20));
        btnBuscar.setFont(new Font("Dialog", 1, 11));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnBuscar_actionPerformed(e);
                }
            });
        lblFecIni.setText("Rango de Fechas");
        lblFecIni.setMnemonic('R');
        lblFecIni.setBounds(new Rectangle(190, 15, 100, 20));
        lblFecIni.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblFecIni_actionPerformed(e);
                }
            });
        lblFecFin.setText("Hasta");
        lblFecFin.setMnemonic('H');
        lblFecFin.setVisible(false);
        lblFecFin.setBounds(new Rectangle(170, 15, 34, 20));
        lblFecFin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblFecFin_actionPerformed(e);
                }
            });
        jRadioButton1.setText("jRadioButton1");
        //Dflores: Se agrego Scrollpane Especialidad
        jspEspecialidad.setBounds(new Rectangle(1095, 70, 255, 305));
        //Dflores: 16/08/2019 Evento para el tblLaboratorio
        tblLaboratorio.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblLaboratorio_mouseClicked(e);
                }
            });
        txtEspecialidad.setBounds(new Rectangle(940, 15, 235, 20));
        txtEspecialidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEspecialidad_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtEspecialidad_keyReleased(e);
                }
            });
        jLabel1.setText("Especialidad");
        jLabel1.setBounds(new Rectangle(860, 20, 70, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        lblMensajeFiltro.setText("Mensaje de Filtro *");
        lblMensajeFiltro.setBounds(new Rectangle(190, 40, 510, 15));
        lblMensajeFiltro.setForeground(SystemColor.window);
        lblMensajeFiltro.setFont(new Font("SansSerif", 3, 12));
  
        jPanelSemaforo0.setBounds(new Rectangle(580, 385, 70, 30));
        jPanelSemaforo0.setBackground(colRed);
        jPanelSemaforo0.setBorder(BorderFactory.createLineBorder(colRed, 2));
        btnSemaforo0.setText("Grabado Temp.");
        btnSemaforo0.setBounds(new Rectangle(650, 385, 110, 30));
        btnSemaforo0.setBackground(new Color(255, 255, 255));
        btnSemaforo0.setBorder(BorderFactory.createLineBorder(colRed, 2));
        btnSemaforo0.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo0.setForeground(new Color(0, 114, 169));
        btnSemaforo0.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo0_mouseClicked(e);
                }
            });        

        jPanelSemaforo1.setBounds(new Rectangle(10, 385, 70, 30));
        jPanelSemaforo1.setBackground(colFuchsia);
        jPanelSemaforo1.setBorder(BorderFactory.createLineBorder(colFuchsia, 2));
        btnSemaforo1.setText("Pend. Triaje");
        btnSemaforo1.setBounds(new Rectangle(80, 385, 110, 30));
        btnSemaforo1.setBackground(new Color(255, 255, 255));
        btnSemaforo1.setBorder(BorderFactory.createLineBorder(colFuchsia, 2));
        btnSemaforo1.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo1.setForeground(new Color(0, 114, 169));
        btnSemaforo1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo1_mouseClicked(e);
                }
            });
        
        jPanelSemaforo2.setBounds(new Rectangle(390, 385, 70, 30));
        jPanelSemaforo2.setBackground(colYellow);
        jPanelSemaforo2.setBorder(BorderFactory.createLineBorder(colYellow, 2));
        btnSemaforo2.setText("Pend. Atención");
        btnSemaforo2.setBounds(new Rectangle(460, 385, 110, 30));
        btnSemaforo2.setBackground(new Color(255, 255, 255));
        btnSemaforo2.setBorder(BorderFactory.createLineBorder(colYellow, 2));
        btnSemaforo2.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo2.setForeground(new Color(0, 114, 169));
        btnSemaforo2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo2_mouseClicked(e);
                }
            });
        
        jPanelSemaforo3.setBounds(new Rectangle(200, 385, 70, 30));
        jPanelSemaforo3.setBackground(colOrange);
        jPanelSemaforo3.setBorder(BorderFactory.createLineBorder(colOrange, 2));
        btnSemaforo3.setText("Por Atender");
        btnSemaforo3.setBounds(new Rectangle(270, 385, 110, 30));
        btnSemaforo3.setBackground(new Color(255, 255, 255));
        btnSemaforo3.setBorder(BorderFactory.createLineBorder(colOrange, 2));
        btnSemaforo3.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo3.setForeground(new Color(0, 114, 169));
        btnSemaforo3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo3_mouseClicked(e);
                }
            });
        
        jPanelSemaforo4.setBounds(new Rectangle(770, 385, 70, 30));
        jPanelSemaforo4.setBackground(colSky);
        jPanelSemaforo4.setBorder(BorderFactory.createLineBorder(colSky, 2));
        btnSemaforo4.setText("En Consulta");
        btnSemaforo4.setBounds(new Rectangle(840, 385, 110, 30));
        btnSemaforo4.setBackground(new Color(255, 255, 255));
        btnSemaforo4.setBorder(BorderFactory.createLineBorder(colSky, 2));
        btnSemaforo4.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo4.setForeground(new Color(0, 114, 169));
        btnSemaforo4.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo4_mouseClicked(e);
                }
            });
        
        jPanelSemaforo5.setBounds(new Rectangle(960, 385, 70, 30));
        jPanelSemaforo5.setBackground(colGreen);
        jPanelSemaforo5.setBorder(BorderFactory.createLineBorder(colGreen, 2));
        btnSemaforo5.setText("Atendido");
        btnSemaforo5.setBounds(new Rectangle(1030, 385, 110, 30));
        btnSemaforo5.setBackground(new Color(255, 255, 255));
        btnSemaforo5.setBorder(BorderFactory.createLineBorder(colGreen, 2));
        btnSemaforo5.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo5.setForeground(new Color(0, 114, 169));
        btnSemaforo5.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo5_mouseClicked(e);
                }
            });
        
        jPanelSemaforo6.setBounds(new Rectangle(1150, 385, 70, 30));
        jPanelSemaforo6.setBackground(colBlack);
        jPanelSemaforo6.setBorder(BorderFactory.createLineBorder(colBlack, 2));
        btnSemaforo6.setText("Ver Todos");
        btnSemaforo6.setBounds(new Rectangle(1220, 385, 110, 30));
        btnSemaforo6.setBackground(new Color(255, 255, 255));
        btnSemaforo6.setBorder(BorderFactory.createLineBorder(colBlack, 2));
        btnSemaforo6.setFont(new Font("Tahoma", 1, 13));
        btnSemaforo6.setForeground(new Color(0, 114, 169));

        btnSemaforo6.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnSemaforo6_mouseClicked(e);
                }
            });
        lblSemaforo0.setText("0");
        lblSemaforo0.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo0.setForeground(SystemColor.window);        
        lblSemaforo1.setText("0");
        lblSemaforo1.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo1.setForeground(SystemColor.window);
        lblSemaforo2.setText("0");
        lblSemaforo2.setForeground(SystemColor.window);
        lblSemaforo2.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo3.setText("0");
        lblSemaforo3.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo3.setForeground(SystemColor.window);
        lblSemaforo4.setText("0");
        lblSemaforo4.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo4.setForeground(SystemColor.window);
        lblSemaforo5.setText("0");
        lblSemaforo5.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo5.setForeground(SystemColor.window);
        lblSemaforo6.setText("0");
        lblSemaforo6.setFont(new Font("Tahoma", 1, 16));
        lblSemaforo6.setForeground(SystemColor.window);
        jPanelHeader1.add(lblMensajeFiltro, null);
        jPanelHeader1.add(jLabel1, null);
        jPanelHeader1.add(txtEspecialidad, null);
        jPanelHeader1.add(lblFecFin, null);
        jPanelHeader1.add(lblFecIni, null);
        jPanelHeader1.add(btnBuscar, null);
        jPanelHeader1.add(txtFecIni, null);
        jPanelHeader1.add(txtFecFin, null);
        pnlContenedor.add(jPanelHeader1, null);

        // TECLAS DE FUNCION
        //pnlContenedor.add(lblF3, null);
        //pnlContenedor.add(lblF2, null);
        //pnlContenedor.add(lblF1, null);

        //lblEsc.setBounds(new Rectangle(80, 280, 117, 19));
        //Dflores: Reubicado
        pnlContenedor.add(lblF5, null);
        pnlContenedor.add(lblF11, null);
        pnlContenedor.add(lblEsc, null);
        lblEsc.setBounds(new Rectangle(1205, 435, 117, 19));
        lblEsc.setName("ESC");
        lblEsc.setText("[ ESC ] Salir");

        lblF1.setBounds(new Rectangle(210, 280, 117, 19));
        lblF1.setName("F1");
        lblF1.setText("[ F1 ] Anular");

        lblF2.setBounds(new Rectangle(340, 280, 135, 19));
        lblF2.setName("F2");
        lblF2.setText("[ F2 ]  Eliminar Triaje");

        lblF3.setBounds(new Rectangle(485, 280, 135, 19));
        lblF3.setName("F3");
        lblF3.setText("[ F3 ]  Registrar Triaje");
        //Dflores: Reubicado
        lblF5.setBounds(new Rectangle(925, 435, 135, 20));
        lblF5.setName("F5");
        lblF5.setText("[ F5 ] Imprimir Receta");
        //Dflores: Reubicado
        lblF11.setBounds(new Rectangle(1075, 435, 117, 19));
        lblF11.setName("F11");
        lblF11.setText("[ F11 ] VISUALIZAR");
        
        lblF5.setVisible(false);
        lblF11.setVisible(false);

        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        lblF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                lblF11_mouseClicked(e);
            }
        });
        
        lblF5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                lblF5_mouseClicked(e);
            }
        });
        
        tblLiberados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLiberados_keyPressed(e);
            }
        });
    }

    private void txtFecIni_keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (!(Character.isDigit(keyChar) ||  keyChar == e.VK_SLASH))
            e.consume();

    }

    private void txtFecIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFecFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFecIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecIni, e);
    }

    private void txtFecFin_keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (!(Character.isDigit(keyChar) ||  keyChar == e.VK_SLASH))
            e.consume();
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }


    private void lblFecIni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecIni);
    }

    private void lblFecFin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecFin);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarFechas()) {
            cargaListaLiberados(txtFecIni.getText().trim(), txtFecFin.getText().trim());
            actualizarCantSemaforos();
            valTodos = modeltblLiberados.data.size();
            lblSemaforo6.setText(valTodos+"");
            pintaListaxFiltro();
            if(valTodos==0)
                FarmaUtility.showMessage(this, "No se encontraron datos para la siguiente busqueda!","");
            
        }
    }
    
    private boolean validarFechas() {
        //quitando los espacios en blanco si es que las ubiera
        txtFecIni.setText(txtFecIni.getText().trim());
        txtFecFin.setText(txtFecFin.getText().trim());

        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFecIni, txtFecFin, false, true, true, true))
            retorno = false;
        //log.info("retorno : "+retorno);
        return retorno;


    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFecIni);
    }
    
    private void this_windowClosing(WindowEvent e) {
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void initialize(){
        crearTableLiberate();

        cargarDatosEspecialidad();
        actualizarCantSemaforos();
        pintaListaxFiltro();
    }
    
    //Dflores: 16/08/2019
    private void cargarDatosEspecialidad(){
        tblModeLaboratorio = 
                new FarmaTableModel(ConstantsModuloVenta.columnsListaLaboratorio, ConstantsModuloVenta.defaultValuesListaLaboratorio, 
                                    0);
        
        FarmaUtility.initSimpleList(tblLaboratorio, 
                                    tblModeLaboratorio, ConstantsModuloVenta.columnsListaLaboratorio);
        
        muestraLaboratorios(/*this.varCodMedico, VariablesCentroMedico.vVarFiltroListaAtenciones*/" ","VT");
    }
    //Dflores: 16/08/2019
    private void muestraLaboratorios(String codMedico, String filVerTodos) {
        try {
            DBModuloVenta.cargaListaEspecialidad(tblModeLaboratorio, codMedico, filVerTodos);
            tblLaboratorio.repaint();
            
        } catch (Exception sqlException) {
            log.error(null, sqlException);
            FarmaUtility.showMessage(this, 
                                     "Error al Listar laboratorio.\n" +
                    sqlException, txtEspecialidad); //txtProducto
        }
    }
    
    private void crearTableLiberate(){
                                                  FarmaColumnData[] columnasListaEspera = { 
                                                  new FarmaColumnData("User Libera", 100, JLabel.LEFT), //22
                                                  new FarmaColumnData("Fech y hora Libera", 130, JLabel.LEFT), //23  
                                                  new FarmaColumnData("Motivo Libera", 300, JLabel.LEFT), //24                                                  
                                                  //Dflores: 16/08/2019 Redimensionado de campos
                                                  new FarmaColumnData("Fecha Aten.", 90, JLabel.CENTER),       //0
                                                  new FarmaColumnData("Hora Aten.", 90, JLabel.CENTER),       //1
                                                  new FarmaColumnData("Nro HC", 80, JLabel.CENTER),     //2
                                                  new FarmaColumnData("Paciente", 250, JLabel.LEFT),    //3
                                                  new FarmaColumnData("Edad", 50, JLabel.CENTER),       //4
                                                  new FarmaColumnData("Estado", 130, JLabel.CENTER),      //5
                                                  //Dflores: 16/08/2019 Agrego tamaño campo Especialidad
                                                  new FarmaColumnData("Especialidad", 0, JLabel.LEFT),  //6
                                                  new FarmaColumnData("Médico", 250, JLabel.LEFT),        //7
 
                                                  new FarmaColumnData("CodigoPaciente", 0, JLabel.LEFT),//8 
                                                  new FarmaColumnData("COD_ESTADO", 0, JLabel.CENTER),  //9
                                                  new FarmaColumnData("HCFISICA", 0, JLabel.CENTER),    //10
                                                  new FarmaColumnData("NROHCFISICA", 0, JLabel.CENTER), //11
                                                  new FarmaColumnData("NRO_CONSULTA", 0, JLabel.CENTER), //12
                                                  new FarmaColumnData("IND ACTIVO", 0, JLabel.CENTER), //13
                                                  
                                                  new FarmaColumnData("COD_GRUPO_CIA", 0, JLabel.CENTER), //14
                                                  new FarmaColumnData("COD_CIA", 0, JLabel.CENTER), //15
                                                  new FarmaColumnData("COD_LOCAL", 0, JLabel.CENTER), //16
                                                  new FarmaColumnData("NUM_ATENCION", 0, JLabel.CENTER), //17
                                                  new FarmaColumnData("COD_PACIENTE", 0, JLabel.CENTER), //18
                                                  new FarmaColumnData("COD_MEDICO", 0, JLabel.CENTER), //19
                                                  //Dflores: 16/08/2019 Se agrego la Especialidad
                                                  new FarmaColumnData("ID_ESPECIALIDAD", 0, JLabel.LEFT), //20
                                                  new FarmaColumnData("Especialidad", 200, JLabel.LEFT) //21
                                                  };
        
        modeltblLiberados = new FarmaTableModel(columnasListaEspera, UtilityPtoVenta.obtenerDefaultValuesTabla(columnasListaEspera.length),0);
        modeltblLiberadosClone = new FarmaTableModel(columnasListaEspera, UtilityPtoVenta.obtenerDefaultValuesTabla(columnasListaEspera.length),0);
        modeltblLiberadosFiltradoClone = new FarmaTableModel(columnasListaEspera, UtilityPtoVenta.obtenerDefaultValuesTabla(columnasListaEspera.length),0);
        FarmaUtility.initSimpleList(tblLiberados, modeltblLiberados, columnasListaEspera);
        
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecFin = formato.format(calendario.getTime());
        //calendario.add(Calendar.DAY_OF_YEAR, -30); //restando 30 dias
        String fecIni = formato.format(calendario.getTime());

        txtFecIni.setText(fecIni);
        txtFecFin.setText(fecFin);

        cargaListaLiberados(fecIni, fecFin);
    }
    
    private int getNumFilaSeleccionadaTabla(){
        int cantFila = tblLiberados.getRowCount();
        if(cantFila == 0){
            FarmaUtility.showMessage(this, "No hay resultados en la busqueda.", tblLiberados);
            return -1;
        }
        int seleccion = tblLiberados.getSelectedRow();
        if(seleccion == -1){
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblLiberados);
        }
        return seleccion;
    }
    
    private void tblLiberados_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblLiberados, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        /*
        } else if (UtilityPtoVenta.verificaVK_F1(e)) {
            funcionF1();
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {    
            funcionF2();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {    
            funcionF3();
        */
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            funcionF5();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //funcionF11();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        }
    }
    
    private void cargaListaLiberados(String fecIni, String fecFin) {
        try {
            
            UtilityAdmisionMedica.obtenerAtenLiberadas(modeltblLiberados, txtFecIni.getText(), txtFecFin.getText()/*,
                                                      this.varCodMedico,VariablesCentroMedico.vVarFiltroListaAtenciones*/);
            modeltblLiberadosClone.data = modeltblLiberados.data;
            FarmaUtility.moveFocus(txtFecIni);
            
        } catch (Exception/*SQLException*/ sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     btnBuscar);
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void lblF11_mouseClicked(MouseEvent e) {
        funcionF11();
    }
    
    private void lblF5_mouseClicked(MouseEvent e) {
        funcionF5();
    }
    
    private void funcionF5(){
        int selecRow = getNumFilaSeleccionadaTabla();
        if(selecRow != -1){
            String codEstado = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 9);
            if("A".equalsIgnoreCase(codEstado)){
                if(JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de reimprimir la receta?")){
                    FarmaVariables.vAceptar = false;
                    DlgLoginMedico dlgLogin = new DlgLoginMedico(myParentFrame, "", true);
                    dlgLogin.setMostrarMensaje(false);
                    dlgLogin.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        String codMedico = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 19);
                        if(codMedico.equalsIgnoreCase(dlgLogin.getCodMedico())){
                            String nroAtencion = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 17);
                            (new UtilityAtencionMedica()).imprimirRecetaMedica(this, nroAtencion);
                        }else{
                            FarmaUtility.showMessage(this,"No se puede reimprimir, debido a que usted no fue quien realizó la consulta medica", txtFecIni);
                        }
                    }
                }
            }else{
                FarmaUtility.showMessage(this, "Consulta médica aun no ha sido atendida.", txtFecIni);
            }
        }/*else{
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", txtFecIni);
        }*/
    }
    
    private void funcionF11(){
        int selecRow = getNumFilaSeleccionadaTabla();
        if(selecRow != -1){
            String codEstado = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 9);
            if("A".equalsIgnoreCase(codEstado)){
                String codGrupoCia = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 14);
                String codCia = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 15);
                String codLocal = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 16);
                String nroAtencion = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 17);
                String codPaciente = FarmaUtility.getValueFieldArrayList(modeltblLiberados.data, selecRow, 18);
                boolean isImpresion = false;
                (new UtilityAtencionMedica()).verAtencionMedica(myParentFrame, this, codGrupoCia, codCia, codLocal, nroAtencion, codPaciente, isImpresion);
            }else{
                FarmaUtility.showMessage(this, "Solo se muestra de las consultas médicas que ya fueron atendidas.", txtFecIni);
            }
        }/*else{
            FarmaUtility.showMessage(this, "Debe seleccionar un registro.", txtFecIni);
        }*/
    }
    //Dflores: 16/08/2019
    private void txtEspecialidad_keyReleased(KeyEvent e) {
        if(tblLaboratorio.getRowCount() >= 0 && 
            tblModeLaboratorio.getRowCount() > 0) {
            if (FarmaGridUtils.buscarDescripcion(e, tblLaboratorio, txtEspecialidad, 1))
                                                     System.out.println("muestra dato");
                                                 }
    }

    private void tblLaboratorio_mouseClicked(MouseEvent e) {
        //int i= tblProductos.getSelectedRow();
        //txtProducto.setText(FarmaUtility.getValueFieldArrayList(tableModelListaPrecioProductos.data,i, 2));
        if (e.isMetaDown() ){
            // click derecho
        //ascascascas
        /*popup.show(e.getComponent(),
                   e.getX(), e.getY());*/
            //muestraAjusteKardex();
            //muestraModificaProducto
        }
        else{
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();
                filtroProdEspecialidad();
            }
        }
    }
    
    //tblLiberados
    private void filtroProdEspecialidad() {
        int pos= tblLaboratorio.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(tblModeLaboratorio.data, pos, 0);
        //
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos(true);
            //filtrar java
            ArrayList target = modeltblLiberados.data;//tableModelListaPrecioProductos.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(23).toString().toUpperCase().trim();
                if(descProd.equals(condicion)){
                    filteredCollection.add(fila);
                }
            }
                     
            modeltblLiberados.data = filteredCollection;
            modeltblLiberados.fireTableDataChanged();
            tblLiberados.repaint();
            
            if(tblLiberados.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                FarmaUtility.showMessage(this, "No se encontraron datos para la siguiente busqueda!", null);
                clonarListadoProductos(true);
            }else{
                if(tblLiberados.getRowCount()==1)
                    lblMensajeFiltro.setText(tblLiberados.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblLiberados.getRowCount()+" filas para el filtro aplicado");
            }
            
            /*FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_PedidoVenta, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones_temporal, 
                                          0);
            FarmaUtility.ponerCheckJTable(tblProductos, COL_COD, VariablesModuloVentas.vArrayList_Prod_Promociones, 
                                          0);*/
            actualizarCantSemaforos();
            pintaListaxFiltro();
            modeltblLiberadosFiltradoClone.data = modeltblLiberados.data;
        }else{
            clonarListadoProductos(true);
            //lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
            //FarmaUtility.showMessage(this, "No se encontraron datos para la siguiente busqueda!", null);
            actualizarCantSemaforos();
            pintaListaxFiltro();
            modeltblLiberadosFiltradoClone.data = modeltblLiberados.data;
        }
        
        if(tblLiberados.getRowCount()>0)
            FarmaGridUtils.showCell(tblLiberados, 0, 0);
            actualizarCantSemaforos();
            pintaListaxFiltro();
            modeltblLiberadosFiltradoClone.data = modeltblLiberados.data;
    }
    
    private void clonarListadoProductos(Boolean reset) {
        if(reset){
            ArrayList arrayClone = new ArrayList();
            for (int i = 0; i < modeltblLiberadosClone.data.size(); i++) { 
                ArrayList aux = (ArrayList)((ArrayList)modeltblLiberadosClone.data.get(i)).clone();
                arrayClone.add(aux);
            }
            modeltblLiberados.clearTable();
            modeltblLiberados.data = arrayClone;
            tblLiberados.repaint();
            tblLiberados.show();
        }else{
            //MODIFICAR EN ESTE PUNTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REVISANDO
            ArrayList arrayClone = new ArrayList();
            for (int i = 0; i < modeltblLiberados.data.size(); i++) { 
                ArrayList aux = (ArrayList)((ArrayList)modeltblLiberados.data.get(i)).clone();
                arrayClone.add(aux);
            }
            modeltblLiberados.clearTable();
            modeltblLiberados.data = arrayClone;
            tblLiberados.repaint();
            tblLiberados.show();
        }
    }
    
    private void clonarPacientesxSemaforo(String semaforo) {
        ArrayList arrayClone = new ArrayList();
        
        if(semaforo.equals("VER TODOS")){
            modeltblLiberados.clearTable();
            modeltblLiberados.data = modeltblLiberadosFiltradoClone.data;
            tblLiberados.repaint();
            tblLiberados.show();
        }else{
            for (int i = 0; i < modeltblLiberadosFiltradoClone.data.size(); i++) { 
                String estadoConsulta = FarmaUtility.getValueFieldArrayList(modeltblLiberadosFiltradoClone.data, i, 5);
                
                if(estadoConsulta.equals(semaforo)){
                    ArrayList aux = (ArrayList)((ArrayList)modeltblLiberadosFiltradoClone.data.get(i)).clone();
                    arrayClone.add(aux);
                }
            }
            modeltblLiberados.clearTable();
            modeltblLiberados.data = arrayClone;
            tblLiberados.repaint();
            tblLiberados.show();
        }
    }
    
    private void txtEspecialidad_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblLaboratorio,txtEspecialidad, 1);
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(tblLaboratorio.getSelectedRow()>=0)
                filtroProdEspecialidad();
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
            chkKeyPressed(e);
    }
    
    private void actualizarCantSemaforos(){
        contarTipoConsulta();
        lblSemaforo0.setText(Integer.toString(valGrabadoTemporal));
        lblSemaforo1.setText(Integer.toString(valPendTriaje));
        lblSemaforo2.setText(Integer.toString(valPendAtencion));
        lblSemaforo3.setText(Integer.toString(valPorAtender));
        lblSemaforo4.setText(Integer.toString(valEnConsulta));
        lblSemaforo5.setText(Integer.toString(valAtendido));
        //lblSemaforo6.setText(Integer.toString(valTodos));
        lblSemaforo6.setText(tblLiberados.getRowCount()+"");
    }
    
    private void contarTipoConsulta() {
        valGrabadoTemporal = 0;
        valPendTriaje = 0;
        valPendAtencion = 0;
        valPorAtender = 0;
        valEnConsulta = 0;
        valAtendido = 0;
        valTodos = 0;
        ArrayList target = modeltblLiberados.data;//tableModelListaPrecioProductos.data;        
        Iterator iterator = target.iterator();
        valTodos = modeltblLiberadosFiltradoClone.data.size();
        
        while(iterator.hasNext()){
            ArrayList fila = (ArrayList)iterator.next();
            String estadoConsulta = fila.get(8).toString().toUpperCase().trim();
            switch(estadoConsulta){
                case STAT_GRAB_TEMPORAL:
                    valGrabadoTemporal++;
                    break;
                case STAT_PEND_TRIAJE:
                    valPendTriaje++;
                    break;
                case STAT_PEND_ATENCION:
                    valPendAtencion++;
                    break;
                case STAT_POR_ATENDER:
                    valPorAtender++;
                    break;
                case STAT_EN_CONSULTA:
                    valEnConsulta++;
                    break;
                case STAT_ATENDIDO:
                    valAtendido++;
                    break;     
            }
        }
    }
    
    private void btnSemaforo0_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_GRAB_TEMPORAL);
    }

    private void btnSemaforo1_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_PEND_TRIAJE);
    }

    private void btnSemaforo2_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_PEND_ATENCION);
    }

    private void btnSemaforo3_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_POR_ATENDER);
    }

    private void btnSemaforo4_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_EN_CONSULTA);
    }

    private void btnSemaforo5_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo(STAT_ATENDIDO);
    }

    private void btnSemaforo6_mouseClicked(MouseEvent e) {
        filtroProdEspecialidad();
        clonarPacientesxSemaforo("VER TODOS");
    }

    class CustomRenderer extends DefaultTableCellRenderer 
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         
            if(modeltblLiberados.data.size()>0){
                String  valEstado=tblLiberados.getValueAt(row, column).toString().trim();

                setBackground(new Color(255,255,255));
                this.setForeground(Color.BLACK);
                
                switch(valEstado){
                    case STAT_GRAB_TEMPORAL:
                        this.setBackground(colRed);
                        this.setForeground(Color.WHITE);
                        break;
                    case STAT_PEND_TRIAJE:
                        this.setBackground(colFuchsia);
                        this.setForeground(Color.WHITE);
                        break;
                    case STAT_PEND_ATENCION:
                        this.setBackground(colYellow);
                        this.setForeground(Color.WHITE);
                        break;
                    case STAT_POR_ATENDER:
                        this.setBackground(colOrange);
                        this.setForeground(Color.WHITE);                        
                        break;
                    case STAT_EN_CONSULTA:
                        this.setBackground(colSky);
                        this.setForeground(Color.WHITE);
                        break;
                    case STAT_ATENDIDO:
                        this.setBackground(colGreen);
                        this.setForeground(Color.WHITE);
                        break;             
                }
                /*if(isSelected){
                   setBackground(new Color(35,57,145));  
                   setForeground(Color.WHITE);
                }else{
                    if(intValor==0){
                       setBackground(prioridad1);
                    }else{
                        setBackground(Color.WHITE);  
                        setForeground(Color.black);
                    }    
                }*/
            }
            return cellComponent;
        }
    }
    
    public void pintaListaxFiltro(){
        if (tblLiberados.getRowCount() > 0) {
            tblLiberados.getColumnModel().getColumn(8).setCellRenderer(new CustomRenderer()); 
        }
        tblLiberados.getTableHeader().setReorderingAllowed(false);
        tblLiberados.getTableHeader().setResizingAllowed(false);
    }
    
    private boolean isUserAdministradorLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = 
                new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, 
                             true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al validar rol de usuariario \n : " + 
                                     e.getMessage(), null);
        }
        return FarmaVariables.vAceptar;
    }
    
    public boolean validaEstado(String vEstado){
        boolean flat = false;
        /*switch(vEstado){
            case STAT_GRAB_TEMPORAL:
                flat = true;
                break;
            case STAT_PEND_TRIAJE:
                flat = true;
                break;
            case STAT_PEND_ATENCION:
                flat = true;
                break;
            case STAT_POR_ATENDER:
                flat = true;
                break;
        }*/
        
        // por el momento no se validara estados
        // es toda responsabilidad del administrador 
        flat = true;
        return flat;
    }
    

    private void accionLiberar(String pNroAtencion,int selecRow) {
        DlgMotivoLiberarAtencion dlgMotivoLibAtencion = new DlgMotivoLiberarAtencion(myParentFrame,"",true);
        dlgMotivoLibAtencion.setVisible(true);
        if(FarmaVariables.vAceptar){
            try {
                DBAdmisionMedica.liberarAtencion(pNroAtencion,
                                                 FarmaVariables.vNuSecUsu,
                                                 VariablesCentroMedico.vMotivoLiberaAtencion);
                FarmaUtility.aceptarTransaccion();
                modeltblLiberados.deleteRow(selecRow);
                tblLiberados.repaint();
                FarmaUtility.showMessage(this, "Se liberó la atención correctamente.", 
                                         txtFecIni);
            } catch (Exception e) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, 
                                         "Ocurrio un error al intentar Liberar!\n : " + 
                                         e.getMessage(), null);
            }
        }
    }
    

    
}
