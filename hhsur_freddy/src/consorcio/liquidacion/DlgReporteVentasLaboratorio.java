package consorcio.liquidacion;

import com.ezware.oxbow.swingbits.table.filter.TableRowFilterSupport;

import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import consorcio.reportes.DlgProcesoLabAnat;
import consorcio.reportes.DlgProcesoVentaHH;
import consorcio.reportes.reference.ConstantsReporteAtencion;
import consorcio.reportes.reference.DBReportesAtencion;
import consorcio.reportes.reference.VariablesReporteAtencion;

import java.awt.BorderLayout;
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

import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.administracion.usuarios.reference.ConstantsUsuarios;

import venta.administracion.usuarios.reference.DBUsuarios;

import venta.reference.UtilityPtoVenta;

public class DlgReporteVentasLaboratorio extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgReporteVentas_01.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButtonLabel lblFechaVenta = new JButtonLabel();
    private JLabel lblEspecialidad = new JLabel();
    private JTextFieldSanSerif txtFechaIni_Atencion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin_Atencion = new JTextFieldSanSerif();
    private JButton btnProcesar = new JButton();
    private JComboBox cmbEspecialidad = new JComboBox();
    private JScrollPane jscListadoVentasLab = new JScrollPane();
    private JTable tblListadoVentasLab = new JTable();
    FarmaTableModel modelListadoVentasLab;
    private Frame myParentFrame ;
    public DlgReporteVentasLaboratorio() {
        this(null, "", false);
    }

    public DlgReporteVentasLaboratorio(Frame parent, String title, boolean modal) {
        super(parent, title, modal);

        myParentFrame = parent;
        try {
            jbInit();
            cargarCombo();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1110, 646));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Reporte Ventas - Laboratorio / Anatom\u00eda Patol\u00f3gica");
        
        //Dflores: Set caracteristics to my Components
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }

                public void windowClosing(WindowEvent e) {
                    this_windowClosing(e);
                }
            });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 1080, 50));
        pnlCriterioBusqueda.setFocusable(false);
        lblEspecialidad.setText("Especialidad");
        lblEspecialidad.setBounds(new Rectangle(10, 15, 80, 25));
        lblEspecialidad.setFont(new Font("SansSerif", 1, 11));
        lblEspecialidad.setForeground(SystemColor.window);
        lblFechaVenta.setText("Fecha Venta");
        lblFechaVenta.setBounds(new Rectangle(395, 15, 115, 20));
        lblFechaVenta.setMnemonic('p');
        lblFechaVenta.setFocusable(false);
        lblFechaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni_Atencion.setBounds(new Rectangle(485, 10, 120, 25));
        txtFechaIni_Atencion.setLengthText(10);
        txtFechaIni_Atencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin_Atencion.setBounds(new Rectangle(620, 10, 120, 25));
        txtFechaFin_Atencion.setLengthText(10);
        txtFechaFin_Atencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnProcesar.setText("PROCESAR");
        btnProcesar.setBounds(new Rectangle(765, 10, 165, 20));
        btnProcesar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //btnProcesar_actionPerformed(e);
                    btnProcesar_actionPerformed(e);
                }
            });
        cmbEspecialidad.setBounds(new Rectangle(90, 10, 275, 25));
        jscListadoVentasLab.setBounds(new Rectangle(10, 75, 1080, 515));
        jscListadoVentasLab.getViewport().add(tblListadoVentasLab, null);
        //--
        
        //Dflores: Add Components to my Panel
        pnlCriterioBusqueda.add(txtFechaFin_Atencion, null);
        pnlCriterioBusqueda.add(txtFechaIni_Atencion, null);
        pnlCriterioBusqueda.add(lblEspecialidad, null);
        pnlCriterioBusqueda.add(lblFechaVenta, null);
        pnlCriterioBusqueda.add(btnProcesar, null);
        //--

        pnlCriterioBusqueda.add(cmbEspecialidad, null);
        jContentPane.add(jscListadoVentasLab, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.setFocusable(false);
        jContentPane.setSize(new Dimension(1484, 413));
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
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
    
    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */
    private void chkKeyPressed(KeyEvent e) {
        
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblRegistroVentas, txtBusqueda, 0);
/*        
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

*/
    }
    
    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni_Atencion, e);
    }
    
    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin_Atencion, e);
    }

    private void lblPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni_Atencion);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarCombo() {
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(
            new String[] { "LABORATORIO y ANATOMIA PATOLOGICA" }); 
        cmbEspecialidad.setModel(cbm);
        cmbEspecialidad.enable(false);
    }

    private void initialize() {
        initTable();
    }

    private void initTable() {
        modelListadoVentasLab = new FarmaTableModel(ConstantsReporteAtencion.columnsListado_LAB_ANAT,ConstantsReporteAtencion.defaultListado_LAB_ANAT, 0);
        FarmaUtility.initSimpleList(tblListadoVentasLab, modelListadoVentasLab,ConstantsReporteAtencion.columnsListado_LAB_ANAT);
    }

    public void cargarListadoVentasLab(String dateIni, String dateFin) {
        try {
                modelListadoVentasLab.data.clear();
                tblListadoVentasLab.repaint();
                log.debug(dateIni);
                log.debug(dateFin);
                DBReportesAtencion.getLista_VENTA_LAB_ANATOMIA(modelListadoVentasLab,dateIni,dateFin);

        } catch (SQLException sql) {
            //log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n" +
                    sql.getMessage(), txtFechaIni_Atencion);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni_Atencion);
    }
    
    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaIni_Atencion, txtFechaFin_Atencion, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void btnProcesar_actionPerformed(ActionEvent e) {
        String FechaInicio_Venta = txtFechaIni_Atencion.getText().trim();
        String FechaFin_venta = txtFechaFin_Atencion.getText().trim();
        
        if (validarCampos()) {
            //cargarListadoVentasLab(FechaInicio_Venta,FechaFin_venta);
            DlgProcesoLabAnat dlgProcesar = new DlgProcesoLabAnat(myParentFrame, "", true,FechaInicio_Venta, FechaFin_venta,this);
            dlgProcesar.mostrar();
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
