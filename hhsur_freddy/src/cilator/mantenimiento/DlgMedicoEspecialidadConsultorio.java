package cilator.mantenimiento;

import cilator.mantenimiento.reference.DBMantenimiento;

import common.FarmaDBUtility;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
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

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.ptoventa.centromedico.DlgADMTrazabilidad;
import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.reference.VariablesModuloVentas;

public class DlgMedicoEspecialidadConsultorio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgADMTrazabilidad.class);
    private JTextField txtNomMed = new JTextField();
    private JButton btnGrabar = new JButton();
    private JScrollPane scrConsultorio = new JScrollPane();
    private JPanelWhite jPanel1 = new JPanelWhite();
    private JTextField txtEspecialidad = new JTextField();
    private JLabel lblEspecialidad = new JLabel();
    private JScrollPane scrEspecialidad = new JScrollPane();
    //private JScrollPane scrConsultorio = new JScrollPane();
    private JTable tblEspecialidad = new JTable();
    private JTable tblConsultorio = new JTable();
    private JButton btnContinuar = new JButton();
    private JLabel lblConsultorios = new JLabel();
    private static ArrayList parametros;
    private FarmaTableModel modelEspecialidad;
    private FarmaTableModel modelConsultorio;
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel4 = new JPanel();
    String posiCero;
    String posiTres;

    //DlgMedicoEspecialidad ME = new DlgMedicoEspecialidad();


    public DlgMedicoEspecialidadConsultorio() {
        this(null, "", false, "", "");
    }

    public DlgMedicoEspecialidadConsultorio(Frame parent, String title, boolean modal, String var1, String var2) {
        super(parent, title, modal);
        try {
            posiCero = var1;
            posiTres = var2;
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void jbInit() throws Exception {
        this.setSize(new Dimension(716, 399));
        this.getContentPane().setLayout(null);
        this.setTitle("Seleccion de Especialidades");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        txtNomMed.setBounds(new Rectangle(115, 20, 350, 25));
        btnGrabar.setText("Grabar");
        btnGrabar.setBounds(new Rectangle(350, 305, 125, 20));
        scrConsultorio.setBounds(new Rectangle(495, 65, 270, 215));
        jPanel1.setBounds(new Rectangle(0, 0, 715, 375));
        txtEspecialidad.setBounds(new Rectangle(95, 20, 315, 25));

        txtEspecialidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtEspecialidad_keyReleased(e);
            }
            public void keyPressed(KeyEvent e) {
                txtEspecialidad_keyPressed(e);
            }
        });
        lblEspecialidad.setText("Especialidad :");
        lblEspecialidad.setBounds(new Rectangle(20, 20, 75, 25));
        lblEspecialidad.setBackground(SystemColor.window);
        lblEspecialidad.setForeground(SystemColor.window);
        scrEspecialidad.setBounds(new Rectangle(5, 85, 385, 215));

        scrConsultorio.setBounds(new Rectangle(420, 85, 275, 145));

        tblEspecialidad.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //tblEspecialidad_mouseClicked(e);
            }
            public void mousePressed(MouseEvent e) {
                tblEspecialidad_mouseClicked(e);
            }
        });
        tblEspecialidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblEspecialidad_keyPressed(e);
            }
        });
        tblConsultorio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblConsultorio_keyPressed(e);
            }
        });
        btnContinuar.setText("GRABAR");
        btnContinuar.setBounds(new Rectangle(300, 320, 175, 30));
        btnContinuar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnContinuar_actionPerformed(e);
            }
        });
        lblConsultorios.setText("Lista de consultorios disponibles");
        lblConsultorios.setBounds(new Rectangle(435, 65, 175, 20));
        lblConsultorios.setForeground(SystemColor.window);
        jPanel2.setBounds(new Rectangle(5, 10, 690, 45));
        jPanel2.setBackground(new Color(0, 114, 169));
        jPanel3.setBounds(new Rectangle(5, 65, 385, 20));
        jPanel3.setBackground(new Color(0, 114, 169));
        jLabel1.setText("Lista de especialidades");
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setBounds(new Rectangle(20, 70, 130, 10));
        jPanel4.setBounds(new Rectangle(420, 65, 275, 20));
        jPanel4.setBackground(new Color(0, 114, 169));
        scrConsultorio.getViewport();
        jPanel1.add(lblConsultorios, null);
        jPanel1.add(btnContinuar, null);
        scrConsultorio.getViewport().add(tblConsultorio, null);
        jPanel1.add(scrConsultorio, null);
        scrEspecialidad.getViewport().add(tblEspecialidad, null);
        jPanel1.add(scrEspecialidad, null);
        jPanel1.add(lblEspecialidad, null);
        jPanel1.add(txtEspecialidad, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(jPanel4, null);
        this.getContentPane().add(jPanel1, null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtEspecialidad);
    }

    private void initialize() {
        cargarEspecialidades();
        cargarDatosConsul();
        busquedaConsulxEspec();
        VariablesModuloVentas.vPosNew = 0;
        VariablesModuloVentas.vPosOld = 0;
    }
    
    //Dflores: 20/12/2019
    private void llenaListaConsultorios(String vCodEspecialidad) {
        try {
            DBMantenimiento.getListConsultoriosEspec(modelConsultorio, vCodEspecialidad/*"64313"*/);
            //resfrescarListaConsultorio();
            if (tblConsultorio.getRowCount() > 0)
                FarmaUtility.ordenar(tblConsultorio, modelConsultorio, 0, "asc");              
            
            log.debug("Se Cargo la Lista de Consultorios!");
            //if(tblModelMedicos.data.size()==0)
                //FarmaUtility.showMessage(this,"No se encontraron resultados en la busqueda",txtNomMed);
        } catch (SQLException sqlException) {
            //log.error("", sqlException);
            //FarmaUtility.showMessage(this,"Error al Listar Medicos.\n" + sqlException, txtNomMed);
            /*FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sqlException.getMessage(), txtNomMed);*/
        }
    }

    //ESPECIALIDADES

    public static void getListadoEspecialidades(FarmaTableModel modelEspecialidad) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);

        FarmaDBUtility.executeSQLStoredProcedure(modelEspecialidad, "HHC_MEDICO.F_LISTA_ESPEC(?,?)", parametros, false);
    }

    private void cargarEspecialidades() {
        modelEspecialidad =
                new FarmaTableModel(ConstantsCentroMedico.columsEspec, ConstantsCentroMedico.defaultValuesEspec, 0);

        FarmaUtility.initSimpleList(tblEspecialidad, modelEspecialidad, ConstantsCentroMedico.columsEspec);

        try {
            getListadoEspecialidades(modelEspecialidad);
            if (tblEspecialidad.getRowCount() > 0)
                FarmaUtility.ordenar(tblEspecialidad, modelEspecialidad, 1, "asc");
            busquedaConsulxEspec();
        } catch (SQLException e) {
        }
    }

    //CONSULTORIOS

    private void cargarDatosConsul() {
        modelConsultorio =
                new FarmaTableModel(ConstantsCentroMedico.columsConsul2, ConstantsCentroMedico.defaultValuesConsul2,
                                    0);

        FarmaUtility.initSimpleList(tblConsultorio, modelConsultorio, ConstantsCentroMedico.columsConsul2);

        /* try {
                getListaConsul2(modelConsultorio,"1");
            } catch (SQLException e) {
            }*/
    }


    private void tblEspecialidad_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && !e.isConsumed()) {
            e.consume();
            //filtroProdEspecialidad();
            busquedaConsulxEspec();
        }
    }

    ///Dflores: 02/01/2020
    public void busquedaConsulxEspec() {
        //Variable que retorna la posicion 0
        int pos = tblEspecialidad.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(modelEspecialidad.data, pos, 0);
        if (!condicion.equals("") && condicion.length() > 0) {
            // FarmaUtility.showMessage(this, ""+condicion, null);
            llenaListaConsultorios(condicion);
        }
    }

    private void txtEspecialidad_keyReleased(KeyEvent e) {
        if (tblEspecialidad.getRowCount() >= 0 && modelEspecialidad.getRowCount() > 0) {
            if (FarmaGridUtils.buscarDescripcion(e, tblEspecialidad, txtEspecialidad, 0)) {
                System.out.println("muestra dato");
                busquedaConsulxEspec();
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtEspecialidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblEspecialidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblConsultorio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    public void DocActual() {

    }
    
    public String validacionConsul() {
        DlgMedicoEspecialidad ME = new DlgMedicoEspecialidad();
        String cmp = ME.ObtenerVar1();
        return cmp;
    }

    private void btnContinuar_actionPerformed(ActionEvent e) {
        DlgMedicoEspecialidad ME = new DlgMedicoEspecialidad();
        /* FarmaUtility.showMessage(this, ""+posiCero, null);
            FarmaUtility.showMessage(this, ""+posiTres, null);
            FarmaUtility.showMessage(this, ""+ObtenerVar3(), null);
            FarmaUtility.showMessage(this, ""+getCodConsultorio(), null);
        */
        try {
            if (getNomConsultorio() != "") {
                /*
                   int dialogResult = JOptionPane.showConfirmDialog(null, "¿Estas seguro de guardar?", "Seguridad", 1);
                    if(dialogResult == JOptionPane.YES_OPTION)*/
                if (JConfirmDialog.rptaConfirmDialog(this, "¿Esta seguro de grabar?")) {
                    DBMantenimiento.insertarConsulMedico(posiCero, posiTres, getCodEspecialidad(), getCodConsultorio(),
                                                         getNomConsultorio());
                    FarmaUtility.aceptarTransaccion();
                    //ME.resfrescar();
                    dispose();
                }
            } else {
                FarmaUtility.showMessage(this, "Seleccione un consultorio", null);
            }
        } catch (SQLException f) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "El medico ya esta asignado al consultorio seleccionado.", txtEspecialidad);
        }
    }
    
    //OBTENER POSICON 0 DE TABLA CONSULTORIOS
    public String getNomConsultorio() {
        //Variable que retorna la posicion 0
        int pos = tblConsultorio.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(modelConsultorio.data, pos, 0);
        //FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
    }
    
    //OBTENER POSICION 1 DE TABLA CONSULTORIOS
    public String getCodConsultorio() {
        //Variable que retorna la posicion 0
        int pos = tblConsultorio.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(modelConsultorio.data, pos, 1);
        //FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
    }    
    
    //OBTENER POSICION 1 DE TABLA ESPECIALIDADES
    public String getCodEspecialidad() {
        //Variable que retorna la posicion 0
        int pos = tblEspecialidad.getSelectedRow();
        String condicion = FarmaUtility.getValueFieldArrayList(modelEspecialidad.data, pos, 1);
        //FarmaUtility.showMessage(this, ""+condicion, null);
        return condicion;
    }
    
    //CERRAR VENTANA
    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblEspecialidad, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
            }else if(e.getKeyCode() == KeyEvent.VK_UP || 
                 e.getKeyCode() == KeyEvent.VK_PAGE_UP || 
                e.getKeyCode() == KeyEvent.VK_DOWN || 
                e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
               
                    VariablesModuloVentas.vPosNew = tblEspecialidad.getSelectedRow();
                    if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                        //actualizaUnidadLotes();
                        busquedaConsulxEspec();
                        VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                    } else {
                        if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                            //actualizaUnidadLotes();
                            busquedaConsulxEspec();
                            VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                        }
                    }
                }
    }

}
