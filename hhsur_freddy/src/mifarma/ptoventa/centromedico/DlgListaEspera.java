package mifarma.ptoventa.centromedico;

import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import javax.swing.SwingConstants;

import common.FarmaColumnData;
import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaSearch;
import common.FarmaTableModel;
import common.FarmaUtility;

import common.FarmaVariables;

import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;
import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;
import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

public class DlgListaEspera extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaEspera.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    private JPanelWhite pnlContenedor = new JPanelWhite();
    private JPanelTitle pnlTituloTablaPacientes = new JPanelTitle();
    private JScrollPane scpPacientes = new JScrollPane();
    private JTable tblPacientes = new JTable();
    private FarmaTableModel modelTblPacientes;
    private JLabelWhite lblListaPacientes = new JLabelWhite();
    private TipoAtencionCM tipoLista;
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel lblFecha = new JLabel();
    private JLabel lblEtiquetaFecha = new JLabel();
    private JLabelOrange lblHCFisica = new JLabelOrange();
    private JLabelOrange lblEspecialidadPaciente = new JLabelOrange();
    private JLabelOrange lblMedicoPaciente = new JLabelOrange();
    private JLabel txtTieneHCFisica = new JLabel();
    private JLabel txtEspecialidadPaciente = new JLabel();
    private JLabel txtMedicoPaciente = new JLabel();
    private JLabelOrange lblNroHCFisica = new JLabelOrange();
    private JLabel txtNroHCFisica = new JLabel();
    private String pCodMedico = "";
    
    String fecActual="";
    private JComboBox cmbEspecialidad = new JComboBox();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbConsultorio = new JComboBox();
    private JButton jButton1 = new JButton();


    public DlgListaEspera() {
        this(null, "", false, null);
    }

    public DlgListaEspera(Frame parent, String title, boolean modal, TipoAtencionCM tipoLista) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.tipoLista = tipoLista;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void jbInit() throws Exception {
        //this.setSize(new Dimension(935, 360));
        this.setSize(new Dimension(820, 525));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Lista de Espera");
        
        pnlContenedor.setBounds(new Rectangle(0, 0, 675, 405));
        
        // ENCABEZADO

        lblHCFisica.setText("HC.Fisica: ");
        lblHCFisica.setBounds(new Rectangle(15, 95, 85, 20));
        lblHCFisica.setFont(new Font("SansSerif", 1, 12));
        lblHCFisica.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEspecialidadPaciente.setText("Especialidad: ");
        lblEspecialidadPaciente.setBounds(new Rectangle(15, 10, 85, 20));
        lblEspecialidadPaciente.setFont(new Font("SansSerif", 1, 12));
        lblEspecialidadPaciente.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblEspecialidadPaciente.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMedicoPaciente.setText("Medico: ");
        lblMedicoPaciente.setBounds(new Rectangle(15, 70, 85, 20));
        lblMedicoPaciente.setFont(new Font("SansSerif", 1, 12));
        lblMedicoPaciente.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTieneHCFisica.setBounds(new Rectangle(100, 95, 45, 20));
        txtTieneHCFisica.setFont(new Font("SansSerif", 0, 12));
        txtEspecialidadPaciente.setBounds(new Rectangle(1100, 95, 290, 20));
        txtEspecialidadPaciente.setFont(new Font("SansSerif", 0, 12));
        txtMedicoPaciente.setBounds(new Rectangle(100, 70, 455, 20));
        txtMedicoPaciente.setFont(new Font("SansSerif", 0, 12));
        lblNroHCFisica.setText("Nro HC.Fisica:");
        lblNroHCFisica.setBounds(new Rectangle(155, 95, 85, 20));
        lblNroHCFisica.setFont(new Font("SansSerif", 1, 12));
        lblNroHCFisica.setHorizontalAlignment(SwingConstants.RIGHT);
        txtNroHCFisica.setBounds(new Rectangle(245, 95, 145, 20));
        txtNroHCFisica.setFont(new Font("SansSerif", 0, 12));
        cmbEspecialidad.setBounds(new Rectangle(110, 10, 225, 20));
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
        jLabel1.setText("Consultorio");
        jLabel1.setBounds(new Rectangle(20, 45, 90, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 114, 169));
        cmbConsultorio.setBounds(new Rectangle(110, 40, 225, 20));
        cmbConsultorio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbConsultorio_keyPressed(e);
                }
            });
        jButton1.setText("Buscar");
        jButton1.setBounds(new Rectangle(375, 10, 105, 20));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        lblEtiquetaFecha.setText("Fecha");
        lblEtiquetaFecha.setBounds(new Rectangle(375, 45, 40, 20));
        lblEtiquetaFecha.setFont(new Font("SansSerif", 1, 12));
        lblEtiquetaFecha.setForeground(new Color(0, 74, 115));
        lblFecha.setBounds(new Rectangle(420, 45, 130, 20));
        lblFecha.setFont(new Font("SansSerif", 0, 12));


        // TABLA DE LISTA
        pnlContenedor.add(jButton1, null);
        pnlContenedor.add(cmbConsultorio, null);
        pnlContenedor.add(jLabel1, null);
        pnlContenedor.add(cmbEspecialidad, null);
        pnlContenedor.add(txtNroHCFisica, null);
        pnlContenedor.add(lblNroHCFisica, null);
        pnlContenedor.add(txtMedicoPaciente, null);
        pnlContenedor.add(txtEspecialidadPaciente, null);
        pnlContenedor.add(txtTieneHCFisica, null);
        pnlContenedor.add(lblMedicoPaciente, null);
        pnlContenedor.add(lblEspecialidadPaciente, null);
        pnlContenedor.add(lblHCFisica, null);
        pnlContenedor.add(lblEtiquetaFecha, null);
        pnlContenedor.add(lblFecha, null);
        pnlTituloTablaPacientes.add(lblListaPacientes, null);
        pnlContenedor.add(pnlTituloTablaPacientes, null);
        scpPacientes.getViewport().add(tblPacientes, null);
        pnlContenedor.add(scpPacientes, null);
        pnlContenedor.add(lblEsc, null);
        pnlContenedor.add(lblF11, null);
        pnlContenedor.add(lblF5, null);
        pnlContenedor.add(lblF1, null);
        lblListaPacientes.setText("Lista de Pacientes");
        lblListaPacientes.setBounds(new Rectangle(5, 0, 125, 20));
        lblListaPacientes.setHorizontalAlignment(SwingConstants.LEFT);
        lblListaPacientes.setHorizontalTextPosition(SwingConstants.LEFT);
        pnlTituloTablaPacientes.setBounds(new Rectangle(10, 120, 775, 20));
        tblPacientes.setFont(new Font("SansSerif", 0, 11));
        scpPacientes.setBounds(new Rectangle(10, 140, 775, 290));
        scpPacientes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // TECLAS DE FUNCION

        lblEsc.setBounds(new Rectangle(110, 450, 117, 19));
        lblEsc.setName("ESC");
        lblEsc.setText("[ ESC ] Salir");

        lblF1.setBounds(new Rectangle(240, 450, 117, 19));
        lblF1.setName("F1");
        lblF1.setText("[ F1 ] Anular");


        lblF5.setBounds(new Rectangle(365, 450, 117, 19));
        lblF5.setName("F5");
        lblF5.setText("[ F5 ] Actualizar");

        lblF11.setBounds(new Rectangle(495, 450, 117, 19));
        lblF11.setName("F11");
        lblF11.setText("[ F11 ] Atender");

        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        lblF5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                funcionF5();
            }
        });
        
        lblF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                funcionF11();
            }
        });
        
        tblPacientes.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                mostrarTitulo();
            }
            
            public void keyPressed(KeyEvent e) {
                chkKeyPressed(e);
            }
        });
        tblPacientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tblPacientes_mouseClicked(e);
            }
        });
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //cargarDatosPantalla();
        if(tblPacientes.getRowCount()>0){
            FarmaUtility.moveFocusJTable(tblPacientes);
            mostrarTitulo();
        }
    }
    
    private void this_windowClosing(WindowEvent e) {
        //FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
        
    }
    
    private void initialize(){
        crearTablaPaciente();
        try {
            fecActual=FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            //lblFecha.setText(fecActual);
            fecActual = " - Fecha : "+fecActual;
        } catch (Exception e) {
            fecActual = "";
        }
        this.lblEtiquetaFecha.setVisible(false);
        this.lblFecha.setVisible(false);
        
        cmbEspecialidad.setName("CMB_ESPECIALIDAD_TRIAJE");
        cmbConsultorio.setName("CMB_CONSULTORIO_TRIAJE");
        
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
    
    private void crearTablaPaciente(){
        int anchoFecha = 0;
        if(TipoAtencionCM.CONSULTA.equals(tipoLista)){
            anchoFecha = 80;
        }
        FarmaColumnData[] columnasListaEspera = { new FarmaColumnData("Fecha", anchoFecha, JLabel.CENTER),  //0
                                                  new FarmaColumnData("Hora", /*60*/ (80-(anchoFecha/4))  , JLabel.CENTER),       //0
                                                  new FarmaColumnData("Nro HC", /*120*/ (120-(anchoFecha/4)), JLabel.CENTER),     //1
                                                  new FarmaColumnData("Paciente", /*395*/ (375-(anchoFecha/2)), JLabel.LEFT),    //2
                                                  new FarmaColumnData("Edad", 40, JLabel.CENTER),       //3
                                                  new FarmaColumnData("Estado", 140, JLabel.LEFT),      //4
                                                  new FarmaColumnData("Especialidad", 0, JLabel.LEFT),  //5
                                                  new FarmaColumnData("Médico", 0, JLabel.LEFT),        //6
                                                  //KMONCADA
                                                  new FarmaColumnData("CodigoPaciente", 0, JLabel.LEFT),//7 
                                                  new FarmaColumnData("COD_ESTADO", 0, JLabel.CENTER),  //8
                                                  new FarmaColumnData("HCFISICA", 0, JLabel.CENTER),    //9
                                                  new FarmaColumnData("NROHCFISICA", 0, JLabel.CENTER), //10
                                                  new FarmaColumnData("NRO_CONSULTA", 0, JLabel.CENTER) //11
                                                  };
        
        modelTblPacientes = new FarmaTableModel(columnasListaEspera, UtilityPtoVenta.obtenerDefaultValuesTabla(columnasListaEspera.length),0);
        FarmaUtility.initSimpleList(tblPacientes, modelTblPacientes, columnasListaEspera);
                
        /*modelTblPacientes = new FarmaTableModel(ConstantsCentroMedico.columnasListaEspera, UtilityPtoVenta.obtenerDefaultValuesTabla(ConstantsCentroMedico.columnasListaEspera.length),0);
        FarmaUtility.initSimpleList(tblPacientes, modelTblPacientes, ConstantsCentroMedico.columnasListaEspera);*/
        
    }
    
    private void cargarDatosPantalla(){
        
        if(TipoAtencionCM.ADMISION.equals(tipoLista)){
            this.setTitle("Lista de Espera Admisión "+fecActual);
        }else if(TipoAtencionCM.TRIAJE.equals(tipoLista)){
            this.setTitle("Lista de Espera Admisión "+fecActual);
        }else if(TipoAtencionCM.CONSULTA.equals(tipoLista)){
            this.setTitle("Lista de Espera Consulta "+fecActual);
        }
        cargarTablaLista();
    }
    
    private void cargarTablaLista(){
        String tipoEstado = "";
        if(TipoAtencionCM.ADMISION.equals(tipoLista)){
            tipoEstado = ConstantsCentroMedico.LISTA_ESPERA_ADMISION;
        }else if(TipoAtencionCM.TRIAJE.equals(tipoLista)){
            tipoEstado = ConstantsCentroMedico.LISTA_ESPERA_ADMISION;
        }else if(TipoAtencionCM.CONSULTA.equals(tipoLista)){
            tipoEstado = ConstantsCentroMedico.LISTA_ESPERA_CONSULTA;
            lblF1.setVisible(false);
        }
        
        
        String pConsultorio = FarmaLoadCVL.getCVLCode(cmbEspecialidad.getName(), cmbEspecialidad.getSelectedIndex());
        String pBus = FarmaLoadCVL.getCVLCode(cmbConsultorio.getName(), cmbConsultorio.getSelectedIndex());
        
        UtilityAtencionMedica.cargarListaEsperaPacientes(modelTblPacientes, tipoEstado, getPCodMedico(),
                                                         pConsultorio,pBus);
    }
    
    
    
    private int getNumFilaSeleccionadaTabla(){
        int cantFila = tblPacientes.getRowCount();
        if(cantFila == 0){
            FarmaUtility.showMessage(this, "No hay pacientes en espera.", tblPacientes);
            return -1;
        }
        int seleccion = tblPacientes.getSelectedRow();
        if(seleccion == -1){
            FarmaUtility.showMessage(this, "No ha seleccionado un Paciente.", tblPacientes);
        }
        return seleccion;
    }
    
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F1(e) && lblF1.isVisible()) {
            FarmaUtility.showMessage(this, 
                                     "Por favor de comunicarse con el administrador." +
                "Para que pueda liberar la atención.", 
                                     cmbEspecialidad);
            //funcionF1();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            funcionF5();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            funcionF11();
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void tblPacientes_mouseClicked(MouseEvent e) {
        mostrarTitulo();
        if(e.getClickCount()==2){
            funcionF11();
        }
    }
    
    private void mostrarTitulo(){
        int filaSeleccionada = tblPacientes.getSelectedRow();
        String especialidad = "";
        String medico = "";
        String hcFisica = "";
        String nroHCFisica = "";
        if(filaSeleccionada!=-1){
            especialidad = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, filaSeleccionada, 6);
            medico = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, filaSeleccionada, 7);
            hcFisica = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, filaSeleccionada, 10);
            nroHCFisica = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, filaSeleccionada, 11);
        }
        txtEspecialidadPaciente.setText(especialidad);
        txtMedicoPaciente.setText(medico);
        txtTieneHCFisica.setText(hcFisica);
        txtNroHCFisica.setText(nroHCFisica);
        //lblFecha.setText(fecAct);
        
    }
    
    private void funcionF1(){
        int selecRow = getNumFilaSeleccionadaTabla();
        if(selecRow != -1){
            if(JConfirmDialog.rptaConfirmDialog(this, 
                                                "¿Desea anular la solicitud de consulta?")){            
                /*modelTblPacientes.deleteRow(selecRow);
                modelTblPacientes.fireTableDataChanged();
                FarmaUtility.showMessage(this, "Se eliminó correctamente.", tblPacientes);*/
                try{
                    String nroSolicitud = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 12);
                    log.info("nroSolicitud  : "+nroSolicitud);
                    String codRpta = UtilityAdmisionMedica.anularAtencionMedica(nroSolicitud);
                    FarmaUtility.aceptarTransaccion();    
                    
                        
                    try {
                        String pCadena = DBAdmisionMedica.getDatosCompVentaMedica(nroSolicitud);
                        if (!pCadena.equalsIgnoreCase("N")) {
                            String[] vDato = pCadena.split("@");
                            UtilityAtencionMedica.registraEstadoCompAtencionMedica(vDato[0].trim(), vDato[1].trim());
                        }
                    } catch (Exception sqle) {
                        // TODO: Add catch code
                        log.info(sqle.getMessage());
                    }
                        
                    
                    
                    cargarTablaLista();
                }catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                      if (sql.getErrorCode() > 20000) {
                      FarmaUtility.showMessage(this, sql.getMessage().substring(10, sql.getMessage().indexOf("ORA-06512")),
                                               null);
                      } 
                      else {
                      FarmaUtility.showMessage(this, "Error al anular la solicitud de atención.\n" + sql.getMessage(), null);
                      log.error("", sql);
                      }
                          
                }catch(Exception er){
                          FarmaUtility.showMessage(this, "Ocurrió el siguiente error\n "+er.getMessage(), null);  
                          log.error("", er);
                }
                   
            }
            
        }
    }
    
    private void funcionF5(){
        int seleccionado = tblPacientes.getSelectedRow();
        cargarTablaLista();
        if(seleccionado != -1)
            tblPacientes.setRowSelectionInterval(seleccionado, seleccionado);
    }
    
    private void funcionF11(){
        int selecRow = getNumFilaSeleccionadaTabla();
        if(selecRow != -1){
            String codEstado = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 9);
            if(ConstantsCentroMedico.ATE_MEDICA_PEND_TRIAJE.equalsIgnoreCase(codEstado)){
                //FarmaUtility.showMessage(this, "PROGRAMAR PARA ATENCION DE TRIAJE", tblPacientes);
                String nroSolicitud = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 12);
                DlgADMRegistroTriaje dlgRegTri = new DlgADMRegistroTriaje(myParentFrame,"",true,nroSolicitud);
                dlgRegTri.setLocationRelativeTo(myParentFrame);
                dlgRegTri.setVisible(true);                
            }else if(ConstantsCentroMedico.ATE_MEDICA_PEND_ATENCION.equalsIgnoreCase(codEstado)){
                if(JConfirmDialog.rptaConfirmDialog(this, 
                                                    "¿Desea enviar para consulta?")){
                    String nroSolicitud = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 12);
                    UtilityAtencionMedica.actualizarEstadoSolicitudAtencion(this, nroSolicitud, ConstantsCentroMedico.ATE_MEDICA_EN_CONSULTA);                                        
                }
            }else if(ConstantsCentroMedico.ATE_MEDICA_EN_CONSULTA.equalsIgnoreCase(codEstado) || ConstantsCentroMedico.ATE_MEDICA_GUARDADA.equalsIgnoreCase(codEstado)){
                realizarAtencionPaciente();
            }
            cargarTablaLista();
        }else{
            FarmaUtility.showMessage(this, "Centro Medico:\nNo ha seleccionado un paciente.", tblPacientes);
        }
    }

    private void realizarAtencionPaciente(){
        int selecRow = getNumFilaSeleccionadaTabla();
        String codEstado = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 9);
        boolean isAtencionNueva = ConstantsCentroMedico.ATE_MEDICA_EN_CONSULTA.equalsIgnoreCase(codEstado);
        String codPaciente = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 8);//cod paciente
        String nroAtencion = FarmaUtility.getValueFieldArrayList(modelTblPacientes.data, selecRow, 12);//nro atencion
        boolean isImpresion = false;
        (new UtilityAtencionMedica()).realizarAtencionPaciente(myParentFrame, this, codPaciente, nroAtencion, getPCodMedico(), isAtencionNueva, isImpresion);
    }

    public String getPCodMedico() {
        return pCodMedico;
    }

    public void setPCodMedico(String pCodMedico) {
        this.pCodMedico = pCodMedico;
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

    private void cmbConsultorio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(cmbEspecialidad);
        } else {
            chkKeyPressed(e);
        }    
    }

    private void cmbEspecialidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(cmbConsultorio);
        } else {
            chkKeyPressed(e);
        }    
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        cargarDatosPantalla();
    }
}
