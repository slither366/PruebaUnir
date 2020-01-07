package mifarma.ptoventa.centromedico;

import common.FarmaTableModel;

import componentes.gs.componentes.JButtonFunction;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
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

import java.security.Key;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import common.FarmaColumnData;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaUtility;
import common.FarmaVariables;
import con_general.ConstantesGeneral_CNX;
import con_general.FarmaDBUtilityGeneral;
import java.util.ArrayList;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

import venta.reportes.reference.VariablesReporte;

public class DlgListadoAtencionesMedicas extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaEspera.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout = new BorderLayout();
    
    private JPanelWhite pnlContenedor = new JPanelWhite();
    private JButtonLabel btnFechas = new JButtonLabel();
    private JTextFieldSanSerif txtFechaInicio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JPanelTitle pnlTitleLista = new JPanelTitle();
    private JButtonLabel btnTablaListado = new JButtonLabel();
    private JScrollPane scrPanListado = new JScrollPane();
    private JTable tblListado = new JTable();
    private FarmaTableModel mdlTblListado;
    //INI JMONZALVE 07022018
    private JPanelTitle pnlTitleLista_Antiguo = new JPanelTitle();
    private JButtonLabel btnTablaListado_Antiguo = new JButtonLabel();
    private JScrollPane scrPanListado_Antiguo = new JScrollPane();
    private JTable tblListado_Antiguo = new JTable();
    private FarmaTableModel mdlTblListado_Antiguo;
    //FIN JMONZALVE 07022018
    private JButton btnBuscar = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelFunction lblEsc_Antiguo = new JLabelFunction();
    private JLabelFunction lblF11_Antiguo = new JLabelFunction();
    
    private String codPaciente;
    private boolean bandImpresiones = false;
    private JLabel lblMensajeConexion = new JLabel();

    public DlgListadoAtencionesMedicas() {
        this(null, "", false);
    }

    public DlgListadoAtencionesMedicas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(887, 621));
        this.getContentPane().setLayout(borderLayout);
        this.setTitle("Historia Clinica");
        btnFechas.setText("Fechas de búsqueda:");
        btnFechas.setMnemonic('F');
        btnFechas.setBounds(new Rectangle(155, 20, 120, 20));
        btnFechas.setForeground(new Color(255, 130, 14));
        
        txtFechaInicio.setBounds(new Rectangle(285, 20, 110, 20));
        
        txtFechaFin.setBounds(new Rectangle(410, 20, 110, 20));
        
        btnBuscar.setText("Buscar");
        btnBuscar.setMnemonic('B');
        btnBuscar.setBounds(new Rectangle(540, 20, 85, 20));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        
        btnTablaListado.setText("Listado HC Nuevo");
        btnTablaListado.setMnemonic('N');
        btnTablaListado.setBounds(new Rectangle(5, 0, 125, 20));
        
        btnTablaListado_Antiguo.setText("Listado HC Antiguos");
        btnTablaListado_Antiguo.setMnemonic('A');
        btnTablaListado_Antiguo.setBounds(new Rectangle(5, 0, 125, 20));

        pnlTitleLista.setBounds(new Rectangle(15, 55, 835, 20));
        pnlTitleLista.add(btnTablaListado, null);
        
        pnlTitleLista_Antiguo.setBounds(new Rectangle(15, 320, 835, 20));

        scrPanListado.setBounds(new Rectangle(15, 75, 835, 165));
        scrPanListado.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrPanListado.getViewport().add(tblListado, null);
        
        scrPanListado_Antiguo.setBounds(new Rectangle(15, 340, 835, 165));
        scrPanListado_Antiguo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblListado_Antiguo.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListado_Antiguo_mouseClicked(e);
                }
            });
        lblEsc.setBounds(new Rectangle(545, 255, 100, 20));
        lblEsc.setText("[ ESC ] Salir");
        
        lblEsc_Antiguo.setBounds(new Rectangle(550, 525, 100, 20));
        lblEsc_Antiguo.setText("[ ESC ] Salir");
        
        lblF11.setBounds(new Rectangle(655, 255, 185, 20));
        lblF11.setText("[ F11 ] Ver HC Nuevo");
        
        lblF11_Antiguo.setBounds(new Rectangle(665, 525, 185, 20));
        lblF11_Antiguo.setText("[ F11 ] Ver HC Antiguo");

        lblMensajeConexion.setText("No hay conexi\u00f3n con central, solo se muestra informaci\u00f3n local");
        lblMensajeConexion.setBounds(new Rectangle(15, 525, 505, 20));
        lblMensajeConexion.setFont(new Font("SansSerif", 1, 11));
        lblMensajeConexion.setForeground(Color.red);
        lblMensajeConexion.setVisible(false);

        pnlContenedor.add(lblMensajeConexion, null);
        pnlContenedor.add(lblF11, null);
        pnlContenedor.add(lblEsc, null);
        pnlContenedor.add(lblF11_Antiguo, null);
        pnlContenedor.add(lblEsc_Antiguo, null);
        pnlContenedor.add(btnBuscar, null);
        pnlContenedor.add(scrPanListado, null);
        pnlContenedor.add(pnlTitleLista, null);
        scrPanListado_Antiguo.getViewport().add(tblListado_Antiguo, null);
        pnlContenedor.add(scrPanListado_Antiguo, null);
        pnlTitleLista_Antiguo.add(btnTablaListado_Antiguo, null);
        pnlContenedor.add(pnlTitleLista_Antiguo, null);
        pnlContenedor.add(txtFechaFin, null);
        pnlContenedor.add(txtFechaInicio, null);

        pnlContenedor.add(btnFechas, null);
        this.getContentPane().add(pnlContenedor, BorderLayout.CENTER);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicio);
        if(getCodPaciente()== null || (getCodPaciente()!=null&& getCodPaciente().trim().length()==0)){
            FarmaUtility.showMessage(this, "Historial Antecedente:\nNo se ha cargado el codigo del paciente.", null);
            cerrarVentana(false);
        }
        cargarDatosFechas();
        /*if(isBandImpresiones()){
            txtFechaInicio.setEditable(false);
            txtFechaFin.setEditable(false);
            txtFechaInicio.setText("");
            txtFechaFin.setText("");
        }*/
    }
    
    private void this_windowClosing(WindowEvent e) {

    }
    
    private void initialize(){
        crearTabla();
        crearTablaAntiguo();
        agregarEventosComponentes();
    }
    
    private void crearTabla(){
        FarmaColumnData[] columnas = { new FarmaColumnData("FECHA", 75, JLabel.CENTER),            //0
                                       new FarmaColumnData("CENTRO MEDICO", 150, JLabel.LEFT),      //1
                                       new FarmaColumnData("NUM.COLEGIO", 120, JLabel.CENTER),      //2
                                       new FarmaColumnData("MEDICO", 220, JLabel.LEFT),             //3
                                       new FarmaColumnData("ESPECIALIDAD", 190, JLabel.LEFT),       //4
                                       new FarmaColumnData("COD_GRUPO_CIA", 0, JLabel.LEFT),        //5
                                       new FarmaColumnData("COD_CIA", 0, JLabel.CENTER),            //6
                                       new FarmaColumnData("COD_LOCAL", 0, JLabel.CENTER),          //7
                                       new FarmaColumnData("NUM_ATENCION", 0, JLabel.CENTER)        //8
                                     };
        
        mdlTblListado = new FarmaTableModel(columnas, UtilityPtoVenta.obtenerDefaultValuesTabla(columnas.length),0);
        FarmaUtility.initSimpleList(tblListado, mdlTblListado, columnas);
    }
    
    private void crearTablaAntiguo(){
        FarmaColumnData[] columnas = { new FarmaColumnData("ESPECIALIDAD", 200, JLabel.CENTER),          //0
                                       new FarmaColumnData("SERIE", 0, JLabel.LEFT),                   //1
                                       new FarmaColumnData("TICKET", 0, JLabel.CENTER),                //2
                                       new FarmaColumnData("FECHA EMISION", 0, JLabel.LEFT),          //3
                                       new FarmaColumnData("CMP", 0, JLabel.LEFT),                      //4
                                       new FarmaColumnData("FECHA ATENCION", 100, JLabel.LEFT),         //5
                                       new FarmaColumnData("MEDICO", 210, JLabel.CENTER),               //6
                                       new FarmaColumnData("BUS", 150, JLabel.CENTER),                    //7
                                       new FarmaColumnData("TIPO DOCUMENTO", 0, JLabel.CENTER),         //8
                                       new FarmaColumnData("HISTORIA CLINICA", 150, JLabel.CENTER)       //9
                                     };
        
        mdlTblListado_Antiguo = new FarmaTableModel(columnas, UtilityPtoVenta.obtenerDefaultValuesTabla(columnas.length),0);
        FarmaUtility.initSimpleList(tblListado_Antiguo, mdlTblListado_Antiguo, columnas);
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
        
        txtFechaInicio.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                FarmaUtility.dateComplete((JTextField)e.getSource(), e);
            }
            
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    FarmaUtility.moveFocus(txtFechaFin);
                }else{
                    chkKeyPressed(e);
                }
            }
        });
        
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                FarmaUtility.dateComplete((JTextField)e.getSource(), e);
            }
            
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    FarmaUtility.moveFocus(txtFechaInicio);
                    btnBuscar.doClick();
                }else{
                    chkKeyPressed(e);
                }
            }
        });
        
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });
        
        tblListado.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                chkKeyPressed(e);
            }
        });
        
        tblListado.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                mostrarAtencionMedica();
            }
        });
    }
    
    private void chkKeyPressed(KeyEvent e){
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListado, null, 0);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }else if(UtilityPtoVenta.verificaVK_F11(e)){
            mostrarAtencionMedica();
        }
    }

    private void cargarDatosFechas(){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        txtFechaFin.setText(formatoFecha.format(calendar.getTime()));
        calendar.add(Calendar.MONTH, -1);
        txtFechaInicio.setText(formatoFecha.format(calendar.getTime()));
        btnBuscar.doClick();
    }
    
    private void realizarBusqueda(){
        String fechaInicio = txtFechaInicio.getText();
        String fechaFin = txtFechaFin.getText();
        
        if(fechaInicio == null || (fechaInicio!=null && fechaInicio.trim().length()==0)){
            if(!isBandImpresiones()){
                FarmaUtility.showMessage(this, "No ha ingresado Fecha Inicial, para realizar la búsqueda.", txtFechaInicio);
                return;
            }
        }
        if(fechaFin == null || (fechaFin!=null && fechaFin.trim().length()==0)){
            if(!isBandImpresiones()){
                FarmaUtility.showMessage(this, "No ha ingresado Fecha Final, para realizar la búsqueda.", txtFechaFin);
                return;
            }
        }
        
        if(!validarFecha(fechaInicio)){
            if(!isBandImpresiones()){
                FarmaUtility.showMessage(this, "Formato de Fecha de Inicio invalido (no puede ser mayor a la actual)", txtFechaInicio);
                return;
            }
        }
        if(!validarFecha(fechaFin)){
            if(!isBandImpresiones()){
                FarmaUtility.showMessage(this, "Formato de Fecha fin invalido (no puede ser mayor a la actual)", txtFechaFin);
                return;
            }
        }
        Date dateInicio = obtenerFecha(fechaInicio);
        Date dateFin = obtenerFecha(fechaFin);
        
        if(dateFin.compareTo(dateInicio) == -1){
            FarmaUtility.showMessage(this, "Fecha de inicio no puede ser mayor a la Fecha Fin ", txtFechaFin);
            return;
        }
        
        int rspta = (new UtilityAtencionMedica()).listarAtencionesMedicas(this, mdlTblListado, codPaciente, fechaInicio, fechaFin);
        if(rspta==0){
            lblMensajeConexion.setText("");
            FarmaUtility.ordenar(tblListado, mdlTblListado, 0, FarmaConstants.ORDEN_DESCENDENTE);
        }else if(rspta==1){
            lblMensajeConexion.setText("No hay conexi\u00f3n con central, solo se muestra informaci\u00f3n local");
            FarmaUtility.ordenar(tblListado, mdlTblListado, 0, FarmaConstants.ORDEN_DESCENDENTE);
        }else if(rspta==2){
            lblMensajeConexion.setText("No se encontraron resultados en central, solo se muestra informaci\u00f3n local");
            FarmaUtility.ordenar(tblListado, mdlTblListado, 0, FarmaConstants.ORDEN_DESCENDENTE);
        }
        lblMensajeConexion.setVisible(true);
        //cargarTablaAntiguo("SJM62789", fechaInicio, fechaFin);
        
        cargarTablaAntiguo(codPaciente, fechaInicio, fechaFin);
        
        
        if(tblListado.getRowCount()>0)
            FarmaUtility.moveFocusJTable(tblListado);
        FarmaUtility.moveFocus(txtFechaInicio);
    }
    
    private void cargarTablaAntiguo(String codPaciente, String fechaIni, String fechaFin){
        
        /*List<String> listaHCAntiguos = (new DBHCAntiguos()).listarHCAntiguos(codPaciente, fechaIni, fechaFin);
        (new UtilityHCAntiguos()).parsearResultado(listaHCAntiguos, mdlTblListado_Antiguo, false);
        FarmaUtility.ordenar(tblListado_Antiguo, mdlTblListado_Antiguo, 0, FarmaConstants.ORDEN_DESCENDENTE);*/
        
        try {
            FarmaDBUtilityGeneral dbGeneral =
                new FarmaDBUtilityGeneral(ConstantesGeneral_CNX.user,
                                          ConstantesGeneral_CNX.clave, 
                                          ConstantesGeneral_CNX.ip, 
                                          ConstantesGeneral_CNX.puerto,
                                          ConstantesGeneral_CNX.sid);
            ArrayList parametros = new ArrayList();
            parametros.add(codPaciente);
            parametros.add(fechaIni);
            parametros.add(fechaFin);
            dbGeneral.executeSQLStoredProcedure(mdlTblListado_Antiguo, "HHC_VER_ATENCIONES.F_LISTA_CAB_ATENCIONES(?,?,?)",
                                                parametros, false, "N");
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
    }
    
    private void mostrarAtencionMedica(){
        int indSeleccionado = tblListado.getSelectedRow();
        if(indSeleccionado != -1){
            String codGrupoCia = FarmaUtility.getValueFieldArrayList(mdlTblListado.data, indSeleccionado, 5);
            String codCia = FarmaUtility.getValueFieldArrayList(mdlTblListado.data, indSeleccionado, 6);
            String codLocal = FarmaUtility.getValueFieldArrayList(mdlTblListado.data, indSeleccionado, 7);
            String nroAtencion = FarmaUtility.getValueFieldArrayList(mdlTblListado.data, indSeleccionado, 8);
            boolean isImpresion = true;
            (new UtilityAtencionMedica()).verAtencionMedica(myParentFrame, this, codGrupoCia, codCia, codLocal, nroAtencion, getCodPaciente(), isImpresion);
            btnBuscar.doClick();
        }else{
            FarmaUtility.showMessage(this, "Historial Antecedentes:\nDebe seleccionar un registro para mostrarlo.", txtFechaInicio);
        }
    }
    
    private Date obtenerFecha(String fecha){
        String aux = fecha;
        int numDia, numMes, numAnio;
        numDia = numMes = numAnio =0;
        String dia = aux.substring(0,2);
        String mes = aux.substring(3,5);
        String anio = aux.substring(6);
        try{
            numDia = Integer.parseInt(dia);
            numMes = Integer.parseInt(mes);
            numAnio = Integer.parseInt(anio);
        }catch(Exception e){
            return null;
        }
        
        Calendar date = Calendar.getInstance();
        date.set(numAnio, (numMes-1), numDia);
        return date.getTime();
    }
    
    private boolean validarFecha(String fecha){
        boolean valido = true;
        if(fecha.trim().length()!=10)
            valido = false;
        else{
            String aux = fecha;
            int numDia, numMes, numAnio;
            numDia = numMes = numAnio =0;
            String dia = aux.substring(0,2);
            String mes = aux.substring(3,5);
            String anio = aux.substring(6);
            try{
                numDia = Integer.parseInt(dia);
                numMes = Integer.parseInt(mes);
                numAnio = Integer.parseInt(anio);
            }catch(Exception e){
                valido = false;
            }
            if(valido){
                Calendar date = Calendar.getInstance();
                date.set(numAnio, (numMes-1), numDia);
                Calendar dateActual = Calendar.getInstance();
                if(date.getTime().compareTo(dateActual.getTime()) == 1 )
                    valido = false;
            }
        }
        return valido;
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

    private void tblListado_Antiguo_mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            int vFoco = tblListado_Antiguo.getSelectedRow();
            //txtFiltroCategoria.setText(""+vFoco);
            if(vFoco >= 0)
            { 
                String pNumHistoria = FarmaUtility.getValueFieldArrayList(mdlTblListado_Antiguo.data, vFoco, 9);
                DlgListadoVerHC_Old dlgListaEspera = new DlgListadoVerHC_Old(myParentFrame,"",true,pNumHistoria);
                dlgListaEspera.setVisible(true);
            }
        }
    }
}
