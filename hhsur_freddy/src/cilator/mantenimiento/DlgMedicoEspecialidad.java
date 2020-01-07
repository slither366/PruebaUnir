package cilator.mantenimiento;

import cilator.mantenimiento.reference.DBMantenimiento;

import common.FarmaDBUtility;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mifarma.ptoventa.centromedico.DlgADMTrazabilidad;
import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;
import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import oracle.jdeveloper.layout.OverlayLayout2;
import oracle.jdeveloper.layout.PaneConstraints;
import oracle.jdeveloper.layout.PaneLayout;
import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.FrmEconoFar;

import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

public class DlgMedicoEspecialidad extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgADMTrazabilidad.class);
    private JLabel jLabel1 = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jPanel1 = new JPanelWhite();
    private JLabel lblNomMed = new JLabel();
    private JTable jTable1 = new JTable();
    private Button button1 = new Button();
    private JButton btnAgregar = new JButton();
    private JButton btnBorrar = new JButton();
    private JScrollPane scrListaMedicos = new JScrollPane();
    private JTable tblListaMedicos = new JTable();
    private JScrollPane scrListaConsultorio = new JScrollPane();
    private JTable tblListaConsultorio = new JTable();
    private JButton btnGrabar = new JButton();
    private JTextField txtNomMed = new JTextField();
    private static ArrayList parametros;
    private FarmaTableModel tblModelMedicos;
    private FarmaTableModel tblModelConsultorio;
    private String varCodMedico;
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel3 = new JLabel();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel lblContador = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private ButtonGroup radioGroup = new ButtonGroup();
    private JRadioButton rbActivos = new JRadioButton();
    private JRadioButton rbInactivos = new JRadioButton();
    private Label label1 = new Label();
    private final String vEstActivo = "1";
    private final String vEstInactivo = "0";
    private String vEstadoReal;

    //DlgMedicoEspecialidadConsultorio MEC = new DlgMedicoEspecialidadConsultorio();

    public DlgMedicoEspecialidad() {
        this(null, "", false);
    }

    public DlgMedicoEspecialidad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(791, 405));
        this.getContentPane().setLayout(null);
        this.setTitle("Seleccion de Medicos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jLabel1.setText("jLabel1");
        jPanel1.setBounds(new Rectangle(0, 0, 790, 380));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jPanel4.setLayout(null);
        lblNomMed.setText("Apellidos y Nombre :");
        lblNomMed.setBounds(new Rectangle(15, 25, 100, 15));

        lblNomMed.setForeground(SystemColor.window);
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(new Rectangle(525, 20, 85, 25));

        btnAgregar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnAgregar_keyPressed(e);
            }
        });
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAgregar_actionPerformed(e);
            }
        });
        btnBorrar.setText("Borrar");
        btnBorrar.setBounds(new Rectangle(655, 20, 85, 25));

        btnBorrar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBorrar_keyPressed(e);
            }
        });
        btnBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBorrar_actionPerformed(e);
            }
        });
        scrListaMedicos.setBounds(new Rectangle(10, 110, 485, 215));

        scrListaMedicos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                scrListaMedicos_keyPressed(e);
            }
        });
        tblListaMedicos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //tblListaMedicos_mouseClicked(e);
            }

            public void mousePressed(MouseEvent e) {
                tblListaMedicos_mousePressed(e);
            }
        });
        tblListaMedicos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaMedicos_keyPressed(e);
            }
        });
        scrListaConsultorio.setBounds(new Rectangle(500, 110, 270, 210));
        scrListaConsultorio.setSize(new Dimension(270, 215));
        scrListaConsultorio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                scrConsul_keyPressed(e);
            }
        });
        tblListaConsultorio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaConsultorio_keyPressed(e);
            }
        });
        btnGrabar.setText("Salir");
        btnGrabar.setBounds(new Rectangle(615, 330, 150, 35));


        btnGrabar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGrabar_actionPerformed(e);
            }
        });
        txtNomMed.setBounds(new Rectangle(110, 10, 350, 25));

        txtNomMed.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtNomMed_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtNomMed_keyPressed(e);
            }
        });
        jPanel2.setBounds(new Rectangle(10, 10, 760, 70));
        jPanel2.setBackground(new Color(78, 138, 237));
        jLabel2.setText("Lista de medicos");
        jLabel2.setBounds(new Rectangle(15, 90, 115, 15));
        jLabel2.setForeground(SystemColor.window);
        jPanel3.setBounds(new Rectangle(10, 85, 485, 25));
        jPanel3.setBackground(new Color(78, 138, 237));
        jLabel3.setText("Lista de consultorios");
        jLabel3.setBounds(new Rectangle(505, 90, 180, 10));
        jLabel3.setForeground(SystemColor.window);
        jPanel4.setBounds(new Rectangle(500, 85, 270, 25));
        jPanel4.setBackground(new Color(78, 138, 237));
        jLabel4.setText("jLabel4");
        jLabel5.setText("jLabel5");
        lblContador.setForeground(SystemColor.window);
        jLabel7.setText("Total:");
        jLabel7.setForeground(SystemColor.window);

        rbActivos.setText("ACTIVOS");
        rbActivos.setBounds(new Rectangle(110, 45, 86, 18));
        rbActivos.setBackground(new Color(78, 138, 237));
        rbActivos.setFont(new Font("SansSerif", 1, 14));
        rbActivos.setForeground(new Color(255, 255, 255));
        rbActivos.setFocusPainted(false);
        rbActivos.setRequestFocusEnabled(false);
        rbActivos.setMnemonic('A');
        rbActivos.setSelected(true);
        rbActivos.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rbtActivos_stateChanged(e);
            }
        });
        rbActivos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbActivos_keyPressed(e);
            }
        });

        rbInactivos.setText("INACTIVOS");
        rbInactivos.setBounds(new Rectangle(220, 45, 115, 20));
        rbInactivos.setBackground(new Color(78, 138, 237));
        rbInactivos.setFont(new Font("SansSerif", 1, 14));
        rbInactivos.setForeground(new Color(255, 255, 255));
        rbInactivos.setFocusPainted(false);
        rbInactivos.setRequestFocusEnabled(false);
        rbInactivos.setMnemonic('I');
        rbInactivos.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rbtInactivos_stateChanged(e);
            }
        });
        rbActivos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtInactivos_keyPressed(e);
            }
        });

        radioGroup.add(rbActivos);
        radioGroup.add(rbInactivos);
        label1.setText("Filtro:");
        label1.setBounds(new Rectangle(75, 50, 28, 14));
        label1.setForeground(SystemColor.window);
        jPanel1.add(jLabel3, null);
        jPanel1.add(jLabel2, null);
        jPanel1.add(btnGrabar, null);
        scrListaConsultorio.getViewport().add(tblListaConsultorio, null);
        jPanel1.add(scrListaConsultorio, null);
        scrListaMedicos.getViewport().add(tblListaMedicos, null);
        jPanel1.add(scrListaMedicos, null);
        jPanel1.add(btnBorrar, null);
        jPanel1.add(btnAgregar, null);
        jPanel1.add(lblNomMed, null);
        jPanel2.add(label1, null);
        jPanel2.add(rbInactivos, null);
        jPanel2.add(rbActivos, null);
        jPanel2.add(txtNomMed, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel4, null);
        jPanel1.add(jPanel3, null);
        jPanel3.add(jLabel7, null);
        jPanel3.add(lblContador, null);
        this.getContentPane().add(jPanel1, null);
    }

    private void initialize() {
        vEstadoReal = vEstActivo;
        FarmaVariables.vAceptar = false;
        cargarDatosConsultorio();
        cargarDatosListaMedico(vEstActivo);
        VariablesModuloVentas.vPosNew = 0;
        VariablesModuloVentas.vPosOld = 0;
        //busquedaConsulxCmp();
        //Contador();
    }

    //CONSULTORIOS

    private void cargarDatosConsultorio() {
        tblModelConsultorio =
                new FarmaTableModel(ConstantsCentroMedico.columsConsul, ConstantsCentroMedico.defaultValuesConsul, 0);
        FarmaUtility.initSimpleList(tblListaConsultorio, tblModelConsultorio, ConstantsCentroMedico.columsConsul);
        //llenaListaConsultorios();

    }

    //MEDICOS

    private void cargarDatosListaMedico(String estado) {
        tblModelMedicos =
                new FarmaTableModel(ConstantsCentroMedico.columsNomMed, ConstantsCentroMedico.defaultValuesListaMedicos,
                                    0);
        FarmaUtility.initSimpleList(tblListaMedicos, tblModelMedicos, ConstantsCentroMedico.columsNomMed);
        //cargarDatosConsul(estado);
        llenaListaMedicos(estado);
        log.debug("Estado ->" + vEstadoReal);
    }

    private void rbtActivos_stateChanged(ChangeEvent e) {
        if (rbActivos.isSelected()) {
            scrListaConsultorio.setEnabled(true);
            vEstadoReal = vEstActivo;
            llenaListaMedicos(vEstActivo);
        }
    }

    private void rbActivos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtNomMed);
    }

    private void rbtInactivos_stateChanged(ChangeEvent e) {
        if (rbInactivos.isSelected()) {
            scrListaConsultorio.setEnabled(false);
            vEstadoReal = vEstInactivo;
            llenaListaMedicos(vEstInactivo);
        }
    }

    private void rbtInactivos_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtNomMed);
    }

    //Dflores: 20/12/2019

    private void llenaListaMedicos(String estado) {
        try {
            DBMantenimiento.getListaMedicos(tblModelMedicos, estado);
            tblListaMedicos.repaint();
            if (tblListaMedicos.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaMedicos, tblModelMedicos, 1, "asc");

            log.debug("Se Cargo la Lista de Medicos!");
            busquedaConsulxCmp();
            if (tblModelMedicos.data.size() == 0)
                FarmaUtility.showMessage(this, "No se encontraron resultados en la busqueda", txtNomMed);
        } catch (SQLException sqlException) {
            //log.error("", sqlException);
            //FarmaUtility.showMessage(this,"Error al Listar Medicos.\n" + sqlException, txtNomMed);
            /*FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sqlException.getMessage(), txtNomMed);*/
        }
    }

    public void busquedaConsulxCmp() {
        //Variable que retorna la posicion 0
        int pos = tblListaMedicos.getSelectedRow();
        String varCmp = FarmaUtility.getValueFieldArrayList(tblModelMedicos.data, pos, 0);
        if (!varCmp.equals("") && varCmp.length() > 0) {
            // FarmaUtility.showMessage(this, ""+varCmp, null);
            llenaListaConsultorios(varCmp);
        }
    }

    //Dflores: 20/12/2019

    private void llenaListaConsultorios(String vCmp) {
        try {
            DBMantenimiento.getListaConsultorio(tblModelConsultorio, vCmp) /*"64313"*/;
            resfrescarListaConsultorio();
            if (tblListaConsultorio.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaConsultorio, tblModelConsultorio, 0, "asc");

            log.debug("Se Cargo la Lista de Consultorios!");
            if (tblModelMedicos.data.size() == 0)
                FarmaUtility.showMessage(this, "No se encontraron resultados en la busqueda", txtNomMed);
        } catch (SQLException sqlException) {
            log.error("", sqlException);
            //FarmaUtility.showMessage(this,"Error al Listar Medicos.\n" + sqlException, txtNomMed);
            /*FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sqlException.getMessage(), txtNomMed);*/
        }
    }
    /*
    public String uno(){
        String var1=ObtenerVar1();
        return var1;
    }

    public String dos(){
        return ObtenerVar2();
    }
*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNomMed);

    }

    private void txtNomMed_keyReleased(KeyEvent e) {
        if (tblListaMedicos.getRowCount() >= 0 && tblModelMedicos.getRowCount() > 0) {
            if (FarmaGridUtils.buscarDescripcion(e, tblListaMedicos, txtNomMed, 1)) {
                System.out.println("muestra dato");
                busquedaConsulxCmp();
            }
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaMedicos, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP ||
                   e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {

            //DFLORES: 30/12/19
            if (vEstadoReal == vEstActivo) {
                VariablesModuloVentas.vPosNew = tblListaMedicos.getSelectedRow();
                if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                    //actualizaUnidadLotes();
                    busquedaConsulxCmp();
                    VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                } else {
                    if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                        //actualizaUnidadLotes();
                        busquedaConsulxCmp();
                        VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                    }
                }

            }
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblListaMedicos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblListaConsultorio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void scrListaMedicos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

    }

    private void scrConsul_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNomMed_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

    }

    private void btnAgregar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnBorrar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    //METODOS PARA OBTENER POSICION 0 Y 3 DE LA TABLA MEDICOS

    public String ObtenerVar1() {
        //Variable que retorna la posicion 0
        int pos = tblListaMedicos.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(tblModelMedicos.data, pos, 0);
        // FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
        //  FarmaUtility.showMessage(this, ""+condicion, null);
    }

    public String ObtenerVar2() {
        //Variable que retorna la posicion 0
        int pos = tblListaMedicos.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(tblModelMedicos.data, pos, 3);
        //FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
    }

    public String ObtenerIdBus() {
        //Variable que retorna la posicion 0
        int pos = tblListaConsultorio.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(tblModelConsultorio.data, pos, 1);
        //FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
    }

    private void btnGrabar_actionPerformed(ActionEvent e) {
        dispose();
    }

    //BOTON ESPECILIDAD

    private void btnAgregar_actionPerformed(ActionEvent e) {
        DlgMedicoEspecialidadConsultorio dlgMedEspCons =
            new DlgMedicoEspecialidadConsultorio(null, "", true, ObtenerVar1(), ObtenerVar2());
        dlgMedEspCons.setVisible(true);
        busquedaConsulxCmp();
        resfrescarListaConsultorio();
    }

    private void btnBorrar_actionPerformed(ActionEvent e) {
        // DlgMedicoEspecialidadConsultorio MEC = new DlgMedicoEspecialidadConsultorio();
        // FarmaUtility.showMessage(this, ""+ObtenerVar1(), null);
        try {
            DBMantenimiento.ConsulMedicoBorrar(ObtenerVar2(), ObtenerIdBus());
            FarmaUtility.aceptarTransaccion();
            busquedaConsulxCmp();
            resfrescarListaConsultorio();
        } catch (Exception e1) {
            // TODO: Add catch code
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Seleccione un consultorio", null);
        }
    }

    public void Contador() {
        int filas = tblListaMedicos.getRowCount();
        lblContador.setText("" + filas);
        //    return setText(getContador(lblContador,condicion /*"64313"*/));
    }

    public void resfrescarListaConsultorio() {
        tblListaConsultorio.repaint();
    }

    /*private void tblListaMedicos_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && !e.isConsumed()) {
            e.consume();
            //filtroProdEspecialidad();
            busquedaConsulxCmp();
        }
    }*/

    private void tblListaMedicos_mousePressed(MouseEvent e) {
        //FarmaUtility.showMessage(this, ""+vEstadoReal, null);
        if (e.getClickCount() == 1 && !e.isConsumed()) {
            e.consume();
            //filtroProdEspecialidad();
            if (vEstadoReal == vEstActivo)
                busquedaConsulxCmp();

            FarmaUtility.moveFocus(txtNomMed);
        }
    }
}

