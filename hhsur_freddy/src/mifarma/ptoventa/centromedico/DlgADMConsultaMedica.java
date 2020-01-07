package mifarma.ptoventa.centromedico;


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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import mifarma.ptoventa.centromedico.DlgHistoriaClinicaAntecedentes.HiloCargaListaDiagnostico;
import mifarma.ptoventa.centromedico.domain.VtaPedidoAtencionMedica;
import mifarma.ptoventa.centromedico.domain.atencionmedica.BeanAtencionMedica;
import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;
import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;
import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;
import mifarma.ptoventa.reference.DBPtoVenta;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.DBCaja;

import venta.reference.UtilityPtoVenta;


public class DlgADMConsultaMedica extends JDialog {
    
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */    
    
    private static final Logger log = LoggerFactory.getLogger(DlgADMConsultaMedica.class);
    private static final long serialVersionUID = -2626325502788986022L;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    
    private JButtonLabel lblMedico = new JButtonLabel();
    private JTextFieldSanSerif txtCodMedico = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescMedico = new JTextFieldSanSerif(); //txtDescMedico
    
    private JButtonLabel lblConsulta = new JButtonLabel();
    private JTextFieldSanSerif txtCodConsulta = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDescConsulta = new JTextFieldSanSerif();
    
    private JButtonLabel lblFecha = new JButtonLabel();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif(); 
    
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    
    private BeanAtencionMedica ateMedica;
    private JTextFieldSanSerif txtDescEspecialidad = new JTextFieldSanSerif();
    private JButtonLabel lblEspecialidad = new JButtonLabel();
    private JButtonLabel lblTipoAtencion = new JButtonLabel();
    private JComboBox cmbTipAtencion = new JComboBox();
    
    private ArrayList<ArrayList<String>> lstConsulta;
    private JComboBox cmbEspecialidad = new JComboBox();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbConsultorio = new JComboBox();


    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */
    public DlgADMConsultaMedica() {
        super();
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgADMConsultaMedica(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            MyParentFrame = parent;
            jbInit();
            cmbEspecialidad.setName("CMB_CONSULTORIO");
            cmbConsultorio.setName("CMB_BUS");
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    
    /* ************************************************************************ */
    /*                              METODO jbInit                               */
    /* ************************************************************************ */


    private void jbInit() throws Exception {
        this.setSize(new Dimension(477, 203));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Datos de Consulta M�dica");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter(){
              public void windowOpened(WindowEvent e){
                this_windowOpened(e);
              }

              public void windowClosing(WindowEvent e){
                this_windowClosing(e);
              }
            });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(20, 10, 495, 165));
        pnlTitle.setBackground(Color.white);
        //pnlTitle.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        pnlTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlTitle.setFocusable(false);
        
        //lblMedico.setText("M�dico:                *");
        lblMedico.setText("M�dico :");
        lblMedico.setMnemonic('M');
        lblMedico.setBounds(new Rectangle(15, 10, 60, 20));
        lblMedico.setBackground(Color.white);
        lblMedico.setForeground(new Color(0, 107, 165));
        lblMedico.setFocusable(true);
        
        lblMedico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblMedico_actionPerformed(e);
            }
        });
        txtCodMedico.setBounds(new Rectangle(85, 10, 80, 20));
        txtCodMedico.setLengthText(10);
        txtCodMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtCodMedico_keyPressed(e);
                }
            public void keyTyped(KeyEvent e) {
                    txtCodMedico_keyTyped(e);
                }
            
        });
        
        txtDescMedico.setBounds(new Rectangle(170, 10, 250, 20));
        txtDescMedico.setLengthText(30);
        txtDescMedico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescMedico_keyPressed(e);
            }
            public void keyTyped(KeyEvent e) {
                //txtNombre_keyTyped(e);
            }
        });
        txtDescMedico.setEditable(false);
        //txtDescMedico.setEnabled(false);
        
        
        txtDescEspecialidad.setBounds(new Rectangle(715, 185, 335, 20));
        //txtDescEspecialidad.setEnabled(false);
        txtDescEspecialidad.setEditable(false);
        
        
        lblEspecialidad.setText("Especialidad :");
        lblEspecialidad.setMnemonic('E');
        lblEspecialidad.setBounds(new Rectangle(5, 45, 80, 20));
        lblEspecialidad.setBackground(Color.white);
        lblEspecialidad.setForeground(new Color(0, 107, 165));
        lblEspecialidad.setFocusable(false);
        lblEspecialidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEspecialidad_actionPerformed(e);
            }
        });
        
        
        //lblConsulta.setText("Consulta:       *");
        lblConsulta.setText("Consulta :       ");
        lblConsulta.setMnemonic('C');
        lblConsulta.setBounds(new Rectangle(710, 135, 120, 20));
        lblConsulta.setBackground(Color.white);
        lblConsulta.setForeground(new Color(255, 90, 33));
        lblConsulta.setFocusable(false);
        lblConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEspecialidad_actionPerformed(e);
            }
        });
        
        txtCodConsulta.setBounds(new Rectangle(855, 135, 45, 20));
        txtCodConsulta.setLengthText(3);
        txtCodConsulta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtCodConsulta_keyPressed(e);
                }
            public void keyTyped(KeyEvent e) {
                txtCodConsulta_keyTyped(e);
            }
        });
        
        txtDescConsulta.setBounds(new Rectangle(910, 135, 250, 20));
        txtDescConsulta.setLengthText(30);
        txtDescConsulta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescConsulta_keyPressed(e);
            }
            public void keyTyped(KeyEvent e) {
                //txtDescEspecialidad_keyTyped(e);
            }
        });
        txtDescConsulta.setEditable(false);
        //txtDescConsulta.setEnabled(false);
        
        
        lblTipoAtencion.setText("Tipo Atenci�n  :");
        lblTipoAtencion.setBounds(new Rectangle(20, 155, 100, 15));
        lblTipoAtencion.setMnemonic('T');
        lblTipoAtencion.setBackground(Color.white);
        lblTipoAtencion.setForeground(new Color(255, 90, 33));
        lblTipoAtencion.setFocusable(false);
        lblTipoAtencion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTipoAtencion_actionPerformed(e);
            }
        });
        
        
        
        
        cmbTipAtencion.setBounds(new Rectangle(165, 150, 170, 20));
        cmbTipAtencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    cmbTipAtencion_keyPressed(e);
                }
        });
        

        //lblFecha.setText("Fecha:                   *");
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
        jLabel1.setText("Consultorio");
        jLabel1.setBounds(new Rectangle(10, 80, 70, 15));
        jLabel1.setForeground(new Color(0, 107, 165));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        cmbConsultorio.setBounds(new Rectangle(85, 75, 240, 20));
        cmbConsultorio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbConsultorio_keyPressed(e);
                }
            });
        lblFecha.setText("Fecha :");
        //lblFecha.setMnemonic('M');
        lblFecha.setBounds(new Rectangle(720, 75, 90, 20));
        lblFecha.setBackground(Color.white);
        lblFecha.setForeground(new Color(0, 107, 165));
        lblFecha.setFocusable(false);
        lblFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecha_actionPerformed(e);
            }
        });
        
        txtFecha.setBounds(new Rectangle(790, 75, 130, 20));
        txtFecha.setLengthText(10);
        //txtFecha.setEditable(false);
        txtFecha.setEditable(false);
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecha_keyPressed(e);
            }
            public void keyTyped(KeyEvent e) {
                //txtFecha_keyTyped(e);
            }
            public void keyReleased(KeyEvent e) {
                  txtFecha_keyReleased(e);
            }
        });



        btnF11.setBounds(new Rectangle(5, 110, 115, 20));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnEsc.setBounds(new Rectangle(310, 110, 115, 20));
        btnEsc.setText("[ESC] Cerrar");
        btnEsc.setFocusable(false);
        jPanelHeader1.setBounds(new Rectangle(125, 235, 1, 1));
        jLabelWhite1.setText("jLabelWhite1");


        


        /*pnlTitle.add(cmbTipAtencion, null);
        pnlTitle.add(lblTipoAtencion, null);*/
        pnlTitle.add(cmbConsultorio, null);
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(cmbEspecialidad, null);
        pnlTitle.add(lblMedico, null);
        pnlTitle.add(txtCodMedico, null);
        pnlTitle.add(txtDescMedico, null);
        pnlTitle.add(lblEspecialidad, null);
        pnlTitle.add(btnF11, null);
        pnlTitle.add(btnEsc, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(pnlTitle, null);
        jContentPane.add(txtDescConsulta, null);
        jContentPane.add(txtCodConsulta, null);
        jContentPane.add(lblConsulta, null);
        jContentPane.add(txtDescEspecialidad, null);
        jContentPane.add(txtFecha, null);
        jContentPane.add(lblFecha, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }
    
    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize(){
          //initTable();
          cargaCombo();
          
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
            
        /*FarmaLoadCVL.loadCVLFromSP(cmbConsultorio, cmbConsultorio.getName(), "HHC_LABORATORIO.GET_CONSULTORIO(?)",
                                   parametros, false, true);*/
    
      FarmaVariables.vAceptar = false;
    }
    
    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    /*private void initTable() {
          tableModel =
                  new FarmaTableModel(ConstantsPtoVenta.columnsListaFiltro, ConstantsPtoVenta.defaultValuesListaFiltro,
                                      0);
          FarmaUtility.initSimpleList(tblFiltro, tableModel, ConstantsPtoVenta.columnsListaFiltro);
      }*/
    
    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e){
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCodMedico);
        
    }

    private void this_windowClosing(WindowEvent e){
      FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.",txtCodMedico);
    }
    
    
    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void cargaCombo() {
        /*ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cmbTipAtencion, "cmbTipAtencion", "PTOVENTA_CME_ADM.CME_LISTA_TIPOS_SOL_MEDICA(?)",
                                   parametros, true, true);*/
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecIni = formato.format(calendario.getTime());
        txtFecha.setText(fecIni);
    }

    private void chkKeyPressed(KeyEvent e){
      if (UtilityPtoVenta.verificaVK_F11(e)){
                  //log.info("Se presiono F11");                 
                  funcion_f11();
      }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
      }
    }
    
    private void funcion_f11(){
        if(validarDatos()){
            //String vTipAten = FarmaLoadCVL.getCVLCode("cmbTipAtencion", cmbTipAtencion.getSelectedIndex());
            ateMedica=new BeanAtencionMedica();
            ateMedica.setCodMedico(VariablesCentroMedico.vCodMedico/*txtCodMedico.getText().trim()*/);
            ateMedica.setCodEspecialidad(1);//Integer.parseInt(txtCodConsulta.getText().trim()));
            ateMedica.setCodPaciente(VariablesCentroMedico.vCodPaciente);
            ateMedica.setEstado(ConstantsCentroMedico.ATE_MEDICA_PEND_TRIAJE);
            ateMedica.setCodTipAtencion("");
            
            ateMedica.setPIdConsultorio(FarmaLoadCVL.getCVLCode("CMB_CONSULTORIO", cmbEspecialidad.getSelectedIndex()));
            ateMedica.setPIdBus(FarmaLoadCVL.getCVLCode("CMB_BUS", cmbConsultorio.getSelectedIndex()));
            
            try{
            String codRpta = UtilityAdmisionMedica.insertAtencionMedica(ateMedica);
            FarmaUtility.aceptarTransaccion();

                UtilityAtencionMedica.registraEstadoCompAtencionMedica(VariablesCentroMedico.vCodLocalVtaComprobante, VariablesCentroMedico.vNumPedVtaComprobante);
                
            FarmaUtility.showMessage(this,"Se registr� la solicitud m�dica correctamente.",txtCodMedico);
            cerrarVentana(true);
            }catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    if (sql.getErrorCode() > 20000) {
                    FarmaUtility.showMessage(this, sql.getMessage().substring(10, sql.getMessage().indexOf("ORA-06512")),
                                             txtCodMedico);
                    } 
                    else {
                    FarmaUtility.showMessage(this, "Error al registrar la solicitud m�dica.\n" + sql.getMessage(), txtCodMedico);
                    log.error("", sql);
                    }
                        
            }catch(Exception er){
                    FarmaUtility.showMessage(this, "Ocurri� el siguiente error\n "+er.getMessage(), txtCodMedico);  
                    log.error("", er);
            }
        }
    }
    
    private boolean validarDatos(){
        boolean flag=true;
        String valMedEspecialidad = "";
       
        try {
           valMedEspecialidad = DBCaja.getEspecialidadxCMPMed(VariablesCentroMedico.vCodCMPMedico);
        
        } catch (SQLException e) {
           valMedEspecialidad = "";
        }
        
        if(txtCodMedico.getText().trim().equals("")){
            FarmaUtility.showMessage(this,"El c�digo del m�dico es obligatorio",txtCodMedico);
            flag=false;
        }else if(txtDescMedico.getText().trim().equals("")){
            FarmaUtility.showMessage(this,"C�digo de m�dico no encontrado",txtCodMedico);
            flag=false;
        }else if(cmbEspecialidad.getSelectedItem().equals("")){
            FarmaUtility.showMessage(this,"Especialidad no seleccionada",txtCodMedico);
            flag=false;
        }else if(cmbConsultorio.getSelectedItem().equals("")){
            FarmaUtility.showMessage(this,"Consultorio no seleccionado",txtCodMedico);
            flag=false;
        }else if(  validarBus()==false /*!valMedEspecialidad.equals(cmbEspecialidad.getSelectedItem())*/ ){
            FarmaUtility.showMessage(this,"El M�dico Ingresado no Pertenece a la Especialidad Seleccionada!",txtCodMedico);
            flag=false;
        }else{
            if(!(JConfirmDialog.rptaConfirmDialog(this, "�Desea grabar la solicitud de atenci�n?"))){ 
                flag=false;
            }
        }
        
        return flag;
    }
    
    private boolean validarBus(){
        boolean flag=false;
        ArrayList<String> listaEspecMedi = new ArrayList<String>();
   
        try {
           DBCaja.getTipoPrueba(listaEspecMedi,VariablesCentroMedico.vCodCMPMedico);
        } catch (SQLException e) {
        }
        
         for (int i=0;i<listaEspecMedi.size();i++) 
       /* for(String lista: listaEspecMedi)*/{
            listaEspecMedi.get(i).toString();
            /*if(.equals(cmbEspecialidad.getSelectedItem().toString())){
                flag=true;
            }  */
        }
      
    
        return flag;
    }
    private void txtCodMedico_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            
            /*if(!(txtCodMedico.getText().trim().equals("")))
            txtCodMedico.setText(FarmaUtility.completeWithSymbol(txtCodMedico.getText().trim(),10 , "0", "I").toUpperCase());*/
            
            //VariablesPtoVenta.vTipoMaestro = "CME01";
            //ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO
            validaCodigo(txtCodMedico, 
                         txtDescMedico,
                         txtDescEspecialidad, 
                         VariablesPtoVenta.vTipoMaestro, 
                         ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO);
            /*if (!txtCodMedico.getText().trim().equals("")) {
                FarmaUtility.moveFocus(txtCodMedico);
            }*/
        } else {
            chkKeyPressed(e);
        }    
    }
    
    private void txtCodConsulta_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesPtoVenta.vTipoMaestro = "CME02";
            validaCodigo(txtCodConsulta, txtDescConsulta,new JTextField(), VariablesPtoVenta.vTipoMaestro, ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_ATENCION);
            /*if (!txtCodConsulta.getText().trim().equals("")) {
                FarmaUtility.moveFocus(txtCodConsulta);
            }*/
        } else {
            chkKeyPressed(e);
        }    
    }
    
    
    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_DescI,JTextField pJTextField_DescII, 
                              String pTipoMaestro,String tipobusqueda) {
        if (pJTextField_Cod.getText().trim().length() > 0) {
            VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
            ArrayList myArray = new ArrayList();
            boolean flagFind=true;
            
            if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO)){
                if(VariablesCentroMedico.vCodMedico.trim().length()!=0){
                    if(JConfirmDialog.rptaConfirmDialog(this, "�Desea buscar un nuevo m�dico?")){
                        buscaCodigoListaMaestro(myArray,tipobusqueda);
                    }else{
                        FarmaUtility.moveFocus(txtCodConsulta);
                        flagFind=false;
                    }
                }
                else
                buscaCodigoListaMaestro(myArray,tipobusqueda);

            }
            else
            buscaCodigoListaMaestro(myArray,tipobusqueda);

            if (myArray.size() == 0 && flagFind) {
                FarmaUtility.showMessage(this, "C�digo No Encontrado", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
                //pJTextField_Cod.setText("");
                pJTextField_DescI.setText("");
                pJTextField_DescII.setText("");
                FarmaUtility.moveFocus(pJTextField_Cod);
                if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO))
                    VariablesCentroMedico.vCodMedico="";
                
            } else if (myArray.size() == 1 && flagFind) {
                if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_ATENCION)){ // busqueda de atenci�n
                        String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                        String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                        pJTextField_Cod.setText(codigo);
                        pJTextField_DescI.setText(descripcion);
                        VariablesPtoVenta.vCodMaestro = codigo;
                        FarmaUtility.moveFocus(/*cmbTipAtencion*/cmbEspecialidad);
                }else if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO)){ 
                        String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                        String codigoCMP = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                        String descripcion = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
                        String descripcionII = ((String)((ArrayList)myArray.get(0)).get(5)).trim();
                        pJTextField_Cod.setText(codigoCMP);
                        pJTextField_DescI.setText(descripcion);
                        
                    VariablesCentroMedico.vCodMedico=codigo;
                    VariablesCentroMedico.vCodCMPMedico=codigoCMP;
                    VariablesCentroMedico.vDescMedico=descripcion;
                    VariablesCentroMedico.vDescEspecialidad=descripcionII;
                        
                        if(descripcionII.trim().equalsIgnoreCase("MEDICINA GENERAL")){
                            txtCodConsulta.setEnabled(false);
                            txtCodConsulta.setText("237");
                            txtDescConsulta.setText("MEDICINA GENERAL");
                        }
                        else{
                            txtCodConsulta.setEnabled(true);
                            txtCodConsulta.setText("");
                            txtDescConsulta.setText("");
                            FarmaUtility.moveFocus(cmbEspecialidad);    
                        }
                        //VariablesPtoVenta.vCodMaestro = codigoCMP;
                        pJTextField_DescII.setText(descripcionII);
                        
                }
                FarmaVariables.vAceptar = true;
            } else if(flagFind)/*if (myArray.size() >= 2)*/ { //>2
                if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_ATENCION)){
                    FarmaUtility.showMessage(this, "Se encontro m�s de un registro.Verificar!!!", pJTextField_Cod);
                    FarmaUtility.moveFocus(pJTextField_Cod);
                    FarmaVariables.vAceptar = false;
                }else if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO)){ 
                    DlgADMListaMedicos dlgListaMedicos = new DlgADMListaMedicos(MyParentFrame, "", true,pJTextField_Cod.getText().trim());
                    dlgListaMedicos.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        pJTextField_Cod.setText(VariablesCentroMedico.vCodCMPMedico);
                        pJTextField_DescI.setText(VariablesCentroMedico.vDescMedico);
                        pJTextField_DescII.setText(VariablesCentroMedico.vDescEspecialidad);
                        
                        if(VariablesCentroMedico.vDescEspecialidad.trim().equalsIgnoreCase("MEDICINA GENERAL")){
                            txtCodConsulta.setEnabled(false);
                            txtCodConsulta.setText("237");
                            txtDescConsulta.setText("MEDICINA GENERAL");
                        }
                        else{
                            txtCodConsulta.setEnabled(true);
                            txtCodConsulta.setText("");
                            txtDescConsulta.setText("");
                            FarmaUtility.moveFocus(txtCodConsulta);    
                        }
                    }/*else{
                        pJTextField_Cod.setText("");
                        pJTextField_DescI.setText("");
                        pJTextField_DescII.setText("");
                    }*/
                }
            }
        } else {
            cargaListaMaestros(pTipoMaestro, pJTextField_Cod, pJTextField_DescI,pJTextField_DescII,tipobusqueda);
        }
    }
    
    private void buscaCodigoListaMaestro(ArrayList pArrayList,String tipobusqueda) {
        try {
            if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_ATENCION)){
                //DBPtoVenta.buscaCodigoListaMaestro(pArrayList, VariablesPtoVenta.vTipoMaestro,VariablesPtoVenta.vCodMaestro);
                DBAdmisionMedica.buscarporCodigoConsulta(pArrayList,txtCodConsulta.getText().trim());
            }else if(tipobusqueda.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO)){
                //log.info("Cargar lista por codigo maestro");
                //DBAdmisionMedica.buscarporCodigoMedico(pArrayList,txtCodMedico.getText().trim(),"","","");
                DBAdmisionMedica.buscarporCodigoMedico(pArrayList,"","","",txtCodMedico.getText().trim());
            }

            
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurri� un error al buscar c�digo en maestros :\n" +
                    sql.getMessage(), txtCodMedico);
        }
    }
    
    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc,
                                    JTextField pJTextField_DescII,String tipoMaestro) {
        if(tipoMaestro.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_ATENCION)){
            lstConsulta=(new UtilityAdmisionMedica()).obtenerListaConsultas();
            DlgListadoCM dlgListado = new DlgListadoCM(MyParentFrame, "Lista de Consultas", true, false, lstConsulta);
            dlgListado.setVisible(true);
            if(FarmaVariables.vAceptar){
                FarmaVariables.vAceptar = false;
                ArrayList lstResultado = dlgListado.getLstResultado();
                if(!lstResultado.isEmpty()){
                    pJTextField_Cod.setText(((String)((ArrayList)lstResultado.get(0)).get(0)).trim());
                    pJTextField_Desc.setText(((String)((ArrayList)lstResultado.get(0)).get(1)).trim());
                    FarmaUtility.moveFocus(/*cmbTipAtencion*/txtCodMedico);
                }else{
                    pJTextField_Cod.setText("");
                    pJTextField_Desc.setText("");
                }
            }else{
                    pJTextField_Cod.setText("");
                    pJTextField_Desc.setText("");
            }
            
            
        }else if(tipoMaestro.equals(ConstantsCentroMedico.TIPO_BUSQUEDA_MAE_MEDICO)){
            DlgADMListaMedicos dlgListaMedicos = new DlgADMListaMedicos(MyParentFrame, "", true,"");
            dlgListaMedicos.setVisible(true);
            if (FarmaVariables.vAceptar) {
                pJTextField_Cod.setText(VariablesCentroMedico.vCodCMPMedico);
                pJTextField_Desc.setText(VariablesCentroMedico.vDescMedico);
                pJTextField_DescII.setText(VariablesCentroMedico.vDescEspecialidad);
                if(VariablesCentroMedico.vDescEspecialidad.trim().equalsIgnoreCase("MEDICINA GENERAL")){
                    txtCodConsulta.setEnabled(false);
                    txtCodConsulta.setText("237");
                    txtDescConsulta.setText("MEDICINA GENERAL");
                }
                else{
                    txtCodConsulta.setEnabled(true);
                    txtCodConsulta.setText("");
                    txtDescConsulta.setText("");
                    FarmaUtility.moveFocus(txtCodConsulta);    
                }                
                FarmaUtility.moveFocus(cmbEspecialidad);
            }/*else{
                pJTextField_Cod.setText("");
                pJTextField_Desc.setText("");
                pJTextField_DescII.setText("");
            }*/
        }
    }
    
    private void txtDescMedico_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtDescConsulta_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    private void txtFecha_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }
     
    private void cmbTipAtencion_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCodMedico);
        }
        chkKeyPressed(e);
    }
    
    private void lblMedico_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodMedico);
    } 
    
    private void txtCodMedico_keyTyped(KeyEvent e) {
        if(!(e.getKeyCode() == KeyEvent.VK_ENTER||
            e.getKeyCode() == KeyEvent.VK_DELETE))
           // e.consume();
        FarmaUtility.admitirDigitos(txtCodMedico, e);
    }
    private void txtCodConsulta_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodConsulta, e);
    }
    
    private void lblEspecialidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodConsulta);
    }
    private void lblTipoAtencion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipAtencion);
    }
    
    private void lblFecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }
    

    private void cerrarVentana(boolean pAceptar){
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */


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

    private void cmbConsultorio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCodMedico);
        } else {
            chkKeyPressed(e);
        }   
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        
            cmbEspecialidad.setSelectedIndex(1);
    }
}
