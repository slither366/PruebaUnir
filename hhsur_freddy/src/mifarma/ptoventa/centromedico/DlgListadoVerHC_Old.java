package mifarma.ptoventa.centromedico;

import common.FarmaTableModel;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelWhite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.ScrollPaneConstants;

import common.FarmaColumnData;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import con_general.ConstantesGeneral_CNX;
import con_general.FarmaDBUtilityGeneral;

import consorcio.hhsur_bd_ptg.reference.DBHCAntiguos;
import consorcio.hhsur_bd_ptg.reference.UtilityHCAntiguos;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mifarma.ptoventa.centromedico.reference.FacadeAtencioMedica;

import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;
//import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

import venta.reportes.reference.VariablesReporte;

public class DlgListadoVerHC_Old extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaEspera.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    
    private JPanelWhite pnlContenedor = new JPanelWhite();
    private FarmaTableModel mdlTblListado;
    //INI JMONZALVE 07022018
    private ArrayList vListaDatos;
    //FIN JMONZALVE 07022018

    private JLabelFunction lblEsc_Antiguo = new JLabelFunction();

    private String codPaciente;
    private boolean bandImpresiones = false;
    private JLabel lblMensajeConexion = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtHC_old = new JTextArea();
    
    private String pNumHistorial = "";
    public DlgListadoVerHC_Old() {
        this(null, "", false,"");
    }

    public DlgListadoVerHC_Old(Frame parent, String title, boolean modal,String pNumHistorial) {
        super(parent, title, modal);
        this.pNumHistorial = pNumHistorial;
        
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(881, 788));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Informaci\u00f3n de Historia Clinica");


        lblEsc_Antiguo.setBounds(new Rectangle(755, 720, 100, 20));
        lblEsc_Antiguo.setText("[ ESC ] Salir");


        lblEsc_Antiguo.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblEsc_Antiguo_mouseClicked(e);
                }
            });
        lblMensajeConexion.setText("No hay conexi\u00f3n con central, solo se muestra informaci\u00f3n local");
        lblMensajeConexion.setBounds(new Rectangle(15, 525, 505, 20));
        lblMensajeConexion.setFont(new Font("SansSerif", 1, 11));
        lblMensajeConexion.setForeground(Color.red);
        lblMensajeConexion.setVisible(false);

        jScrollPane1.setBounds(new Rectangle(15, 5, 845, 705));
        jScrollPane1.getViewport().add(txtHC_old, null);
        pnlContenedor.add(jScrollPane1, null);
        pnlContenedor.add(lblMensajeConexion, null);

        pnlContenedor.add(lblEsc_Antiguo, null);
        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        
        /*if(getCodPaciente()== null || (getCodPaciente()!=null&& getCodPaciente().trim().length()==0)){
            FarmaUtility.showMessage(this, "Historial Antecedente:\nNo se ha cargado el codigo del paciente.", null);
            cerrarVentana(false);
        }*/
    }
    
    private void this_windowClosing(WindowEvent e) {

    }
    
    private void initialize(){
        
        try {
            FarmaDBUtilityGeneral dbGeneral =
                new FarmaDBUtilityGeneral(ConstantesGeneral_CNX.user,
                                          ConstantesGeneral_CNX.clave, 
                                          ConstantesGeneral_CNX.ip, 
                                          ConstantesGeneral_CNX.puerto,
                                          ConstantesGeneral_CNX.sid);
            ArrayList parametros = new ArrayList();
            parametros.add(pNumHistorial);
            vListaDatos = new ArrayList();
            dbGeneral.executeSQLStoredProcedureArrayList(vListaDatos, "HHC_VER_ATENCIONES.F_LISTA_DET_ATENCIONES(?)",
                                                         parametros, "N");
            
            for(int i=0;i<vListaDatos.size();i++){
                txtHC_old.append(FarmaUtility.getValueFieldArrayList(vListaDatos, i, 0));
                txtHC_old.append("\n");
            }
            txtHC_old.append("\n");
            txtHC_old.append("\n");
            vListaDatos = new ArrayList();
            dbGeneral.executeSQLStoredProcedureArrayList(vListaDatos, "HHC_VER_ATENCIONES.F_LISTA_DET_DIAGN(?)",
                                                         parametros, "N");
            
            for(int i=0;i<vListaDatos.size();i++){
                txtHC_old.append(FarmaUtility.getValueFieldArrayList(vListaDatos, i, 0));
                txtHC_old.append("\n");
            }
            
            
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        agregarEventosComponentes();
    }
     
    private void agregarEventosComponentes(){
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
       
    }
    
    

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public String getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(String codPaciente) {
        this.codPaciente = codPaciente;
    }

    public void setBandImpresiones(boolean bandImpresiones) {
        this.bandImpresiones = bandImpresiones;
    }

    public boolean isBandImpresiones() {
        return bandImpresiones;
    }

    private void lblEsc_Antiguo_mouseClicked(MouseEvent e) {
        cerrarVentana(true);
    }
}
