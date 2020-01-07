package venta.otros;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import common.FarmaConnectionRemoto;
import common.FarmaConstants;
import common.FarmaJTable;
import common.FarmaLoadCVL;
import common.FarmaRowEditorModel;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.otros.reference.ConstantsOtros;
import venta.otros.reference.DBOtros;
import venta.otros.reference.UtilityOtros;
import venta.otros.reference.VariablesOtros;
import venta.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import venta.fidelizacion.reference.DBFidelizacion;
import venta.fidelizacion.reference.VariablesFidelizacion;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgRegDatosCliente.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JCALLO      23.10.2008   Creaci�n<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 * 
 */

public class DlgRegDatosCliente extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgRegDatosCliente.class);

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private FarmaRowEditorModel rowEditorModel;  
    private DefaultCellEditor defaultCellEditor;
    private ArrayList aCampos = new ArrayList();
    private FarmaJTable tblLista = new FarmaJTable();

    private final int COL_DATO = 1;
    private final int COL_COD = 2;
    private final int COL_TIPO_DATO = 3;
    private final int COL_SOLO_LECTURA = 4;
    private final int COL_IND_OBLI = 5;    

    /* ************************************************************************ */
    /*                               CAMPOS DE LA GRILLA                        */
    /* ************************************************************************ */

    private JTextFieldSanSerif txtNomCliente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumeroDocumento = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoMaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoPaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtEmail = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

    private JComboBox cmbSexo = new JComboBox();
    private String[] compDescripcion = { "MASCULINO", "FEMENINO" };
    private String[] compValor = { "M", "F" };

    private JTextFieldSanSerif txtFechNac = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSexo = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    //private JLabelFunction lblF1 = new JLabelFunction();

    private String mensajeESC = "";
    private String pCodigoCampoAux = "";  
    /*****/
    private final


    int COL_DNI = 0;
    private final int COL_APE_PAT = 1;
    private final int COL_APE_MAT = 2;
    private final int COL_NOM_CLI = 3;
    private final int COL_FEC_NAC = 4;
    private final int COL_SEX_CLI = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_EMAIL = 8;
    private JLabelFunction lblF2 = new JLabelFunction();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRegDatosCliente() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public DlgRegDatosCliente(Frame parent, String title, boolean modal) {
        super(parent, title, modal);        
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jContentPane.setSize(new Dimension(520, 272));
        this.setSize(new Dimension(530, 313));
        this.setDefaultCloseOperation(0);
        this.setTitle("Datos del Cliente ");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        lblEsc.setBounds(new Rectangle(420, 244, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(285, 244, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 505, 205));
        tblLista.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblLista_keyPressed(e);
                    }
                });
        pnlTitle1.setBounds(new Rectangle(10, 10, 505, 20));
        btnLista.setText("Lista Datos");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnLista_actionPerformed(e);
                    }
                });
        lblF2.setBounds(new Rectangle(20, 245, 125, 20));
        lblF2.setText("[ F2 ] Ingresar Dato");
        /*lblf1.setBounds(new Rectangle(20, 244, 120, 20));
        lblf1.setVisible(true);*/
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblF2, null);
        //jContentPane.add(lblf1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        txtNomCliente.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtNombre_keyTyped(e);
                    }
                });
        //txtNomCliente.setText("");

        txtApellidoPaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtApellidoPaterno_keyTyped(e);
                    }
                });

        txtApellidoMaterno.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtApellidoMaterno_keyTyped(e);
                    }
                });

        

        txtNumeroDocumento.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtNumeroDocumento_keyTyped(e);
                    }
                });

        txtTelefono.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtTelefono_keyTyped(e);
                    }
                });

        txtDireccion.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtDireccion_keyTyped(e);
                    }
                });

        txtFechNac.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtFechaNacimiento_keyTyped(e);
                    }
                });

        txtSexo.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtSexo_keyTyped(e);
                    }


                });
        cmbSexo.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtSexo_keyTyped(e);
                    }
                });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                                  METODO initialize                     */
    /* ********************************************************************** */

    private void initialize() {
    	FarmaVariables.vAceptar = false;
        try{        
            VariablesOtros.vDocValidos = DBOtros.obtenerParamDocIden();
        }catch(SQLException e){
            log.debug("error : "+e);
            FarmaUtility.showMessage(this, "Error al obtener Parametro de Doc validos !", tblLista);
        }
        log.debug("longitud de docs validos : "+VariablesOtros.vDocValidos);
        
        //cargan la variable indicador de linea con matriz
        VariablesOtros.vIndLineaMatriz = FarmaUtility.getIndLineaOnLine(
										 FarmaConstants.CONECTION_MATRIZ,
										 FarmaConstants.INDICADOR_N).trim();
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {

        tableModel = 
                new FarmaTableModel(ConstantsOtros.columnsListaDatosClienteMedPresion, 
                					ConstantsOtros.defaultValuesListaDatosClienteMedPresion, 
                                    0, 
                                    ConstantsOtros.editableListaDatosClienteMedPresion, 
                                    null);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = 
                new TableColumn(k, ConstantsOtros.columnsListaDatosClienteMedPresion[k].m_width);
            tblLista.addColumn(column);
        }

        tblLista.setRowEditorModel(rowEditorModel);
        cargaCampos();
        // Verifica si no tiene campos para ingresar acepta el convenio
        if (tblLista.getRowCount() == 0) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         null);
                return;
            }
            //funcionF11();
        }
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        tblLista.repaint();
        //cargaDatosCliente();
        //  tblLista.setValueAt("11s",1,1);//VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
        //tblLista.show();
    }

    private int buscaPosFila(String pCodigoCampo) {
        String codAux = "";
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codAux = tableModel.getValueAt(i, 2).toString().trim();// FarmaUtility.getValueFieldJTable(tblLista, i, 2);
            if (pCodigoCampo.trim().equalsIgnoreCase(codAux.trim()))
                return i;
        }
        return -1;
    }

    private void cargaLista() {
        try {
            DBOtros.listarCamposClienteMedPresion(tableModel);
            if (tableModel.data.size() == 0) {
                cerrarVentana(true);
            }
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", 
                                     tblLista);
        }
        /****/
        /**limpiando datos de columnda datos del cliente*/
        for(int i=0;i<tableModel.getRowCount();i++){
            tableModel.setValueAt("",i,1);
        }
        /****/
        aCampos = tableModel.data;
    }

    /**
     * Este procedimiento determinar� el tipo de dato de cada campo segun 
     * lo registrado en la BD.
     */
    private void setTipoCampo() {
        String codigoCampo = "";
        String vTipoDato;
        String vIndSoloLec;
        //Dveliz 26.08.08
        String vIndOblig;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            //codigoCampo = tblLista.getValueAt(i, COL_COD).toString().trim();
        	codigoCampo = tableModel.getValueAt(i, COL_COD).toString().trim();
            vTipoDato = tableModel.getValueAt(i, COL_TIPO_DATO).toString().trim();
                    //FarmaUtility.getValueFieldJTable(tblLista, i, COL_TIPO_DATO);
            vIndSoloLec = tableModel.getValueAt(i, COL_SOLO_LECTURA).toString().trim();
                    //FarmaUtility.getValueFieldJTable(tblLista, i, COL_SOLO_LECTURA);

            //dveliz 26.08.08
            vIndOblig = tableModel.getValueAt(i, COL_IND_OBLI).toString().trim();
                    //FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);
            if (vIndOblig.equals("S")) {
            	VariablesOtros.vFlagMandatory = true;
            }
            //fin dveliz

            //dveliz 27.08.08
            if (i == 0) {
                if (codigoCampo.equals(ConstantsOtros.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNumeroDocumento);
                } else if (codigoCampo.equals(ConstantsOtros.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoPaterno);
                } 
                else if (codigoCampo.equals(ConstantsOtros.APEMAT_CLIENTE)) {
                                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                                    FarmaUtility.moveFocus(txtApellidoMaterno);
                                } 
                else if (codigoCampo.equals(ConstantsOtros.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNomCliente);
                } 
                else if (codigoCampo.equals(ConstantsOtros.EMAIL_CLIENTE)) {
                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoMaterno);
                } else if (codigoCampo.equals(ConstantsOtros.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtTelefono);
                } else if (codigoCampo.equals(ConstantsOtros.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtFechNac);
                } else if (codigoCampo.equals(ConstantsOtros.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtDireccion);
                } else if (codigoCampo.equals(ConstantsOtros.SEXO_CLIENTE)) {
                    VariablesOtros.vSexoExists = true;
                    getCmbSexo(i);
                    FarmaUtility.moveFocus(cmbSexo);
                }
            } else {
                if (codigoCampo.equals(ConstantsOtros.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.APEMAT_CLIENTE)) {
                    getTxtApellidoMaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsOtros.SEXO_CLIENTE)) {
                    VariablesOtros.vSexoExists = true;
                    getCmbSexo(i);
                }
                else if (codigoCampo.equals(ConstantsOtros.EMAIL_CLIENTE)) {
                                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                } 
            }
        }
    }

    private void getTxtNumeroDocumento(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        //txtNumeroDocumento.setLengthText(8);
    	txtNumeroDocumento.setText("");
        createJTextField(txtNumeroDocumento, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoPaterno(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        txtApellidoPaterno.setLengthText(30);
        txtApellidoPaterno.setText("");
        createJTextField(txtApellidoPaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoMaterno(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        txtApellidoMaterno.setLengthText(30);
        txtApellidoMaterno.setText("");
        createJTextField(txtApellidoMaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtTelefono(int i, String vTipoDato, String vIndSoloLec) {
        txtTelefono.setLengthText(10);
        txtTelefono.setText("");
        createJTextField(txtTelefono, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtFechaNacimiento(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        txtFechNac.setLengthText(10);//dd/mm/yyyy
        txtFechNac.setText("");
        createJTextField(txtFechNac, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtNombre(int i, String vTipoDato, String vIndSoloLec) {
        txtNomCliente.setLengthText(30);
        txtNomCliente.setText("");
        createJTextField(txtNomCliente, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtEmail(int i, String vTipoDato, String vIndSoloLec) {
        txtEmail.setLengthText(70);
        txtEmail.setText("");
        createJTextField(txtEmail, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtDireccion(int i, String vTipoDato, String vIndSoloLec) {
        txtDireccion.setLengthText(100);
        txtDireccion.setText("");
        createJTextField(txtDireccion, i, vTipoDato, vIndSoloLec);
    }

    public void getCmbSexo(int i) {        
        createJComboBox(cmbSexo, i);
    }

    private void createJTextField(JTextField pJTextField, int pRow, 
                                  String pTipoCampo, String pInSoloLectura) {

        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ********************************************************************** */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                 */
    /* ********************************************************************** */

    private void createJComboBox(JComboBox pJComboBox, int pRow) {

        createJComboBoxAux(cmbSexo, "CMB_SEXO", compValor, compDescripcion, 
                           pRow);

        //addKeyListenerJComboBox(pJComboBox);
        //defaultCellEditor = new DefaultCellEditor(pJComboBox);
        //rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
    }
    
    private void createJComboBoxAux(JComboBox pJComboBox, String pNameComboBox, 
                                    String[] pValue, String[] pDescription, 
                                    int pRow) {
        FarmaLoadCVL.loadCVLfromArrays(pJComboBox, pNameComboBox, pValue, 
                                       pDescription, false);
        addKeyListenerJComboBox(pJComboBox);
        defaultCellEditor = new DefaultCellEditor(pJComboBox);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }
    
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void addKeyListenerToTextField(final JTextField pJTextField, 
                                           final String pTipoCampo, 
                                           final String pInSoloLectura) {
        pJTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {                        
                        if (pInSoloLectura.equalsIgnoreCase("S")) {
                            
                            e.consume();
                        } else {                            
                            if (pTipoCampo.equalsIgnoreCase("E"))
                                FarmaUtility.admitirDigitos(pJTextField, e);
                            else if (pTipoCampo.equalsIgnoreCase("D"))
                                FarmaUtility.admitirDigitosDecimales(pJTextField, 
                                                                     e);
                            else if (pTipoCampo.equalsIgnoreCase("F")){                                
                                FarmaUtility.dateComplete(pJTextField, e);
                            }
                        }
                    }

                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            e.consume();
                            int vFila = tblLista.getSelectedRow();
                            
                            pCodigoCampoAux = tableModel.getValueAt(vFila, 2).toString().trim();//FarmaUtility.getValueFieldJTable(tblLista,vFila,2);
                            
                            //VERIFICAR SI EL ENTER SE TECLEO EN EL CAMPO DNI
                            if(pCodigoCampoAux.trim().equalsIgnoreCase(ConstantsOtros.DNI_CLIENTE)){
                                String pCadena = pJTextField.getText().trim();                                
                                //log.info("entrando a buscar datos de cliente por dni");
                                if(UtilityOtros.validarDocIndentificacion(pCadena,VariablesOtros.vDocValidos)){
                                    buscaDatosCliente(pCadena);
                                }else{
                                    //FarmaUtility.showMessage(this,"Doc de Identificacion no valido !",txtNumeroDocumento);
                                    JOptionPane.showMessageDialog(null, "Doc de Identificacion no valido !", 
                                                                  "Mensaje del Sistema", 
                                                                  JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                                //log.info("moviendo el cursor al siguiente texto o comnbo"+vFila);
                                
                                pJTextField.setText(pJTextField.getText().toUpperCase());
                                
                                /*
                                for(int i = 0; i<tblLista.getRowCount();i++){
                                	tblLista.changeSelection(i, 1, false, false);
                                }*/
                                
                                
                                if ((vFila + 1) == tblLista.getRowCount()){
                                	//log.info("move focus : 0 ");
                                    moveFocusTo(0);
                                }else{
                                	//log.info("move focus : "+(vFila+1));
                                	moveFocusTo(vFila+1);
                                } 
                                
                            }
                            else
                            {
                            	pJTextField.setText(pJTextField.getText().toUpperCase());
                            	if ((vFila + 1) == tblLista.getRowCount())                                
                                    moveFocusTo(0);
                                else{                                
                                	moveFocusTo(vFila+1);
                                }
                                
                            }
                        } else
                            tblLista_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {

                        if (pTipoCampo.equalsIgnoreCase("F")) {
                            FarmaUtility.dateComplete(pJTextField, e);
                        }

                        if (venta.reference.UtilityPtoVenta.verificaVK_F1(e) && 
                            pJTextField.isDisplayable()) {
                            pJTextField.selectAll();
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            /*if ((vFila + 1) < tblLista.getRowCount())
                                moveFocusTo(vFila);*/

                        } else {
                            if (e.getKeyCode() != KeyEvent.VK_LEFT && 
                                e.getKeyCode() != KeyEvent.VK_RIGHT && 
                                e.getKeyCode() != KeyEvent.VK_DELETE && 
                                e.getKeyCode() != KeyEvent.VK_BACK_SPACE && 
                                e.getKeyCode() != KeyEvent.VK_HOME && 
                                (!Character.isLetter(e.getKeyChar()) && 
                                 !Character.isWhitespace(e.getKeyChar()) && 
                                 !Character.isDigit(e.getKeyChar())))
                                pJTextField.setText(pJTextField.getText().toUpperCase());

                        }
                    }
                });
    }

    private void addKeyListenerJComboBox(final JComboBox pJComboBox) {

        pJComboBox.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {

                    }

                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (pJComboBox.isPopupVisible())
                                pJComboBox.setPopupVisible(false);
                            else {
                            	int Fila = tblLista.getSelectedRow();
                            	
                                if ((Fila + 1) == 
                                    tblLista.getRowCount()) {
                                	
                                	moveFocusTo(0);
                                    /*FarmaUtility.setearRegistro(tblLista, 
                                                                tblLista.getSelectedRow(), 
                                                                null, 0);*/
                                } else {
                                	moveFocusTo(Fila+1);
                                	
                                    /*FarmaUtility.setearRegistro(tblLista, 
                                                                tblLista.getSelectedRow() + 
                                                                1, null, 0);*/
                                }
                            }
                        }
                    }

                    public void keyReleased(KeyEvent e) {
                        if (venta.reference.UtilityPtoVenta.verificaVK_F2(e) && 
                            pJComboBox.isDisplayable()) {
                            pJComboBox.setPopupVisible(true);
                        /*} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (pJComboBox.isPopupVisible()) {
                                pJComboBox.setPopupVisible(false);
                            }*/
                        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            e.consume();
                            tblLista_keyPressed(e);
                        }
                    }
                });

    }

    private void moveFocusTo(int pRow) {
        /*FarmaUtility.setearRegistro(tblLista, pRow, null, 0);
        tblLista.changeSelection(pRow, 1, false, false);*/
    	
    	tblLista.changeSelection(pRow, 1, false, false);
    	
    	String cod_campo = tableModel.getValueAt(pRow, 2).toString().trim();
    	log.debug("CAMPO "+cod_campo+" - "+ConstantsOtros.DNI_CLIENTE);
    	if( cod_campo.equalsIgnoreCase(ConstantsOtros.DNI_CLIENTE) ){    		
    		FarmaUtility.moveFocus(txtNumeroDocumento);    		
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.NOMBRE_CLIENTE) ){    		
    		FarmaUtility.moveFocus(txtNomCliente);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.APEPAT_CLIENTE) ){    		
    		FarmaUtility.moveFocus(txtApellidoPaterno);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.APEMAT_CLIENTE) ){
    		FarmaUtility.moveFocus(txtApellidoMaterno);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.TELEFONO_CLIENTE) ){
    		FarmaUtility.moveFocus(txtTelefono);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.DIREC_CLIENTE) ){
    		FarmaUtility.moveFocus(txtDireccion);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.SEXO_CLIENTE) ){
    		FarmaUtility.moveFocus(txtSexo);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.FECHA_NAC_CLIENTE) ){
    		FarmaUtility.moveFocus(txtFechNac);
    	}else if( cod_campo.equalsIgnoreCase(ConstantsOtros.EMAIL_CLIENTE) ){
    		FarmaUtility.moveFocus(txtEmail);
    	}    	
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        initTable();
        //FarmaUtility.moveFocus(tblLista);
        
        /*****/
        //seteando valores de la tabla con "";
        
        /*****/
        
        tblLista.changeSelection(0, 1, false, false);
        
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ********************************************************************** */
    /*                     METODOS AUXILIARES DE EVENTOS                      */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
       if (venta.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         null);
                return;
            }
            funcionF11();                 
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mensajeESC = "�Est� seguro salir de la ventana?";
            if (JOptionPane.showConfirmDialog(this, mensajeESC, 
                                              "Mensaje de Confirmacion - Mifarma", 
                                              JOptionPane.YES_NO_OPTION) == 
                0) {
            	
                VariablesOtros.vIndExisteCliente = false;
                cerrarVentana(false);
                
            }
            if (tblLista.getRowCount() > 0) {
               

                FarmaUtility.moveFocus(tblLista);

            }

        }
    }

    public void txtNombre_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtNomCliente, e);
    }

    public void txtApellidoPaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoPaterno, e);
    }

    private void txtSexo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtSexo, e);
    }

    public void txtApellidoMaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoMaterno, e);
    }

    public void txtEmail_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirLetras(txtEmail, e);
    }

    public void txtNumeroDocumento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
    }

    public void txtDireccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetrasNumeros(txtDireccion, e);
    }

    public void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }

    public void txtFechaNacimiento_keyTyped(KeyEvent e) {        
        FarmaUtility.dateComplete(txtFechNac, e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        if(!pAceptar){
        	this.limpiar();
        }
        this.setVisible(false);
        this.dispose();
    }

    private void limpiar() {
        VariablesOtros.vDataCliente = new ArrayList();
        VariablesOtros.vDniCliente = "";
        VariablesOtros.vApePatCliente = "";
        VariablesOtros.vApeMatCliente = "";
        VariablesOtros.vNomCliente = "";
        VariablesOtros.vFecNacimiento = "";
        VariablesOtros.vSexo = ""; //cmbSexo.getSelectedItem().toString().trim();//txtSexo.getText().trim();               
        VariablesOtros.vDireccion = "";
        VariablesOtros.vTelefono = "";


        VariablesOtros.vIndEstado = "";
        
        VariablesOtros.Usu_Confir = "";
        VariablesOtros.Tip_doc = "";
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcionF11() {
    	
    	//se agrego esta linea por si el usuario cambio a ultima hora el DNI
    	if(txtNumeroDocumento.isEditable()){
    		log.debug("Validando datos del cliente ya que pudo haber cambiado el nro DNI.");
    		buscaDatosCliente(txtNumeroDocumento.getText().trim());
    	}
    	
        tblLista.changeSelection(0, 0, true, false);
        if (validateMandatory()){//validando todos los datos obligatorios
        	cargaVariables();
        	//registrando el cliente tanto en local como en matriz
        	//consiste en insertar/actualizar segun corresponda
            //registrarClienteEnLocalYMatriz();
        	try {            
        		
        		DBOtros.insertarClienteLocal();        		
        		if(VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)){
    	            DBOtros.insertarClienteEnMatriz(FarmaConstants.INDICADOR_N);
    	            //commi en matriz
    	            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
    						  							  FarmaConstants.INDICADOR_N);
        		}
        		//commit en local
        		FarmaUtility.aceptarTransaccion();
        		//si hay linea con matriz registrarlo
                cerrarVentana(true);
            } catch (SQLException e) {
            	FarmaUtility.liberarTransaccion();
            	if(VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)){
            		FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, 
    					  							      FarmaConstants.INDICADOR_N);
            		FarmaConnectionRemoto.closeConnection();
            	}
            	log.error("",e);
                FarmaUtility.showMessage(this, "ERROR:"+e, txtNumeroDocumento);
            }
        }
        
    }
    
    /**
     * metodo encargado de buscar datos de matriz, si la hay actualizar o insertar en local
     * @param pDni
     */
    private static void buscaMatrizActualizaDatosLocal(String pDni){

        ArrayList array = new ArrayList();
        
        try {
            
            DBOtros.getDatosExisteDNI_Matriz(array,pDni, FarmaConstants.INDICADOR_N);
            log.debug("Datos Cli en Matriz "+ array);
            log.debug("Tam size:"+array.size());
            
            /**verificando si hay datos del client en matriz**/
            if( array.size()>0 ) {
                
            	VariablesOtros.vDniCliente = FarmaUtility.getValueFieldArrayList(array,0,0).trim();   
            	VariablesOtros.vApePatCliente = FarmaUtility.getValueFieldArrayList(array,0,1).trim();
            	VariablesOtros.vApeMatCliente =FarmaUtility.getValueFieldArrayList(array,0,2).trim();
            	VariablesOtros.vNomCliente =FarmaUtility.getValueFieldArrayList(array,0,3).trim();
            	VariablesOtros.vFecNacimiento =FarmaUtility.getValueFieldArrayList(array,0,4).trim();
            	VariablesOtros.vSexo = FarmaUtility.getValueFieldArrayList(array,0,5).trim();
            	VariablesOtros.vDireccion = FarmaUtility.getValueFieldArrayList(array,0,6).trim();
            	VariablesOtros.vTelefono =FarmaUtility.getValueFieldArrayList(array,0,7).trim();
               
            	VariablesOtros.vIndEstado  = "A";
                //Este metodo de se encargara de insertar y/o actualizar
            	//cliente en local
                DBOtros.insertarClienteLocal();
            }
            VariablesOtros.vDniCliente = "";
            VariablesOtros.vApePatCliente = "";
            VariablesOtros.vApeMatCliente = "";
            VariablesOtros.vNomCliente = "";
            VariablesOtros.vFecNacimiento = "";
            VariablesOtros.vSexo = "";
            VariablesOtros.vDireccion = "";
            VariablesOtros.vTelefono = "";
            
        } catch (SQLException e) {
            log.error("",e);
        }
    }
    
    
    
    private boolean buscaDatosCliente(String pDNI){
      if(UtilityOtros.validarDocIndentificacion(pDNI.trim(), VariablesOtros.vDocValidos)){
        log.debug("Datos del cliente " +  VariablesOtros.vDataCliente);
        
        ArrayList vListaDatosDNI = new ArrayList();
        ArrayList vListaCampos = new ArrayList();
        String vDNI = "", vApePat = "", vApeMat = "", vNom = "", vFecNac = 
            "", vSexCli = "", vDirCli = "", vTlfCli = "", vEma = "";

        try {
        	DBOtros.getDatosExisteDNI(vListaDatosDNI, pDNI);//obtener datos del cliente
          
          	if (vListaDatosDNI.size() < 1) {//si no hay datos del cliente          		
          		log.debug("VariablesOtros.vIndLineaMatriz:"+VariablesOtros.vIndLineaMatriz);
          		if(VariablesOtros.vIndLineaMatriz.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){//busca en matriz los datos
          			log.debug("busca datos de cliente en matriz e inserta o actualiza en local");
          			buscaMatrizActualizaDatosLocal(pDNI);
          			log.debug("busca datos del cliente en local");
          			vListaDatosDNI = new ArrayList();
          			DBOtros.getDatosExisteDNI(vListaDatosDNI, pDNI);                                  
          		}
            }
            
            if (vListaDatosDNI.size() > 0 ) {//si hay datos de cliente
              DBOtros.getCamposCliente(vListaCampos);
              //log.debug("vListaCampos " + vListaCampos);
              //log.debug("vListaDatosDNI " + vListaDatosDNI);

              vDNI = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
              vApePat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT);
              vApeMat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT);
              vNom = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
              //vNom = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
              vFecNac = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC);
              vSexCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
              vDirCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIR_CLI);
              vTlfCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TLF_CLI);
              vEma = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);
              
              int pos = -1;
              
        	  if (!vDNI.equalsIgnoreCase("N")) {//si tiene el Nro de DNI
        		  pos = buscaPosFila(ConstantsOtros.DNI_CLIENTE);
	              if (pos >= 0) {
	                tblLista.setValueAt(vDNI, pos, 1);
	                txtNumeroDocumento.setText(vDNI);
	                txtNumeroDocumento.setEditable(false);
	              }
              }
              
              if (!vApePat.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.APEPAT_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vApePat, pos, 1);
            		  txtApellidoPaterno.setText(vApePat);
            		  txtApellidoPaterno.setEditable(false);
            	  }	
              }
              
              if (!vApeMat.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.APEMAT_CLIENTE);
                  if (pos >= 0) {
                	  tblLista.setValueAt(vApeMat, pos, 1);
                	  txtApellidoMaterno.setText(vApeMat);
                	  txtApellidoMaterno.setEditable(false);
                  }
              }
              
              if (!vNom.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.NOMBRE_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vNom, pos, 1);
            		  txtNomCliente.setText(vNom);
            		  txtNomCliente.setEditable(false);
            	  }
              }

              if (!vEma.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.EMAIL_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vEma, pos, 1);
            		  txtEmail.setText(vEma);
            		  txtEmail.setEditable(false);
            	  }
              }
              
              if (!vFecNac.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.FECHA_NAC_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vFecNac, pos, 1);
            		  txtFechNac.setText(vFecNac);
            		  txtFechNac.setEditable(false);
            	  }
              }
              
              if (!vSexCli.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.SEXO_CLIENTE);
            	  if (pos >= 0) {            		  
            		  FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "CMB_SEXO", vSexCli);
            		  tblLista.setValueAt(FarmaLoadCVL.getCVLDescription("CMB_SEXO", vSexCli),pos,1);
            		  cmbSexo.setEditable(false);
            		  cmbSexo.setEnabled(false);
            		  txtSexo.setEditable(false);            		  
            		  }
              	  }
              if (!vDirCli.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.DIREC_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vDirCli, pos, 1);
            		  txtDireccion.setText(vDirCli);
            		  txtDireccion.setEditable(false);
            	  }
              }

              if (!vTlfCli.equalsIgnoreCase("N")) {
            	  pos = buscaPosFila(ConstantsOtros.TELEFONO_CLIENTE);
            	  if (pos >= 0) {
            		  tblLista.setValueAt(vTlfCli, pos, 1);
            		  txtTelefono.setText(vTlfCli);
            		  txtTelefono.setEditable(false);
            	  }
              }
              tblLista.repaint();
              VariablesOtros.vIndExisteCliente = true;
              return true;
            }
            else 
                return false;


        } catch (SQLException e) {
            log.error("",e);
        }        
        
        return false;
    }
    else
        return false;
   }
    
    /**
     * metodo encargado de validar los campos que son obligatorios especificar para registrar al cliente
     * @author jcallo
     * */
    private boolean validateMandatory() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = tableModel.getValueAt(i, COL_IND_OBLI).toString().trim();
            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = tableModel.getValueAt(i, COL_COD).toString().trim();
                vDato = tableModel.getValueAt(i, COL_DATO).toString().trim();
                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    FarmaUtility.showMessage(this, 
                                             "Campo " + findFieldDescription(vCodCampo) + 
                                             " no tiene informaci�n.  Verifique !!!", 
                                             null);
                    break;
                }

                if (vCodCampo.equals(ConstantsOtros.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " contiene un fecha invalida.  Verifique !!!", 
                                                 null);
                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsOtros.DNI_CLIENTE)) {
                    if ( !UtilityOtros.validarDocIndentificacion(vDato.trim(),VariablesOtros.vDocValidos) ) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " es invalido.  Verifique !!!", 
                                                 null);
                        break;
                    }
                }
                
                if (vCodCampo.equals(ConstantsOtros.EMAIL_CLIENTE)) {
                    
                    if (!UtilityOtros.validarEmail(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " invalido.Verifique !!!", 
                                                 null);
                        break;
                    }
                }
                
            }
        }
        if(dataExists){
            VariablesOtros.vDniCliente = txtNumeroDocumento.getText().trim();
            VariablesOtros.vNomCliente = txtNomCliente.getText().trim();
            VariablesOtros.vFecNacimiento = txtFechNac.getText().trim();
            dataExists = validaDocumento();
        }
        return dataExists;
    }
    
    private String findFieldDescription(String pFieldValue) {
        String fieldDescription = "NO DETERMINADO";
        for (int i = 0; i < aCampos.size(); i++) {
            if (((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue)) {
                fieldDescription = 
                        ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
                break;
            }
        }
        return fieldDescription;
    }
    
    /**
     * metodo encargado de vargar los datos en las variablesOtros para poder registrar los datos correctamente
     * @author jcallo
     * **/
    private void cargaVariables() {
        
        //log.info("dni"+txtNumeroDocumento.getText().trim());
        //log.info("apepat"+txtApellidoPaterno.getText().trim());
        
        VariablesOtros.vDniCliente = txtNumeroDocumento.getText().trim();
        VariablesOtros.vApePatCliente = txtApellidoPaterno.getText().trim();
        VariablesOtros.vApeMatCliente = txtApellidoMaterno.getText().trim();
        VariablesOtros.vNomCliente = txtNomCliente.getText().trim();
        VariablesOtros.vEmail = txtEmail.getText().trim();
        VariablesOtros.vFecNacimiento = txtFechNac.getText().trim();
        VariablesOtros.vSexo = getSexoCliente();
        VariablesOtros.vDireccion = txtDireccion.getText().trim();
        VariablesOtros.vTelefono = txtTelefono.getText().trim();
        VariablesOtros.vIndEstado = "A";

        if (VariablesOtros.vApePatCliente.trim().length() == 0)
        	VariablesOtros.vApePatCliente = "N";
        if (VariablesOtros.vApeMatCliente.trim().length() == 0)
        	VariablesOtros.vApeMatCliente = "N";
        if (VariablesOtros.vNomCliente.trim().length() == 0)
        	VariablesOtros.vNomCliente = "N";
        if (VariablesOtros.vFecNacimiento.trim().length() == 0)
        	VariablesOtros.vFecNacimiento = "N";
        if (VariablesOtros.vSexo.trim().length() == 0)
        	VariablesOtros.vSexo = "N";
        if (VariablesOtros.vDireccion.trim().length() == 0)
        	VariablesOtros.vDireccion = "N";
        if (VariablesOtros.vTelefono.trim().length() == 0)
        	VariablesOtros.vTelefono = "N";
        if (VariablesOtros.vEmail.trim().length() == 0)
        	VariablesOtros.vEmail = "N";
    }
    
    private String getSexoCliente() {
        String tipoSexo;
        try {
        	//log.info("indice del combo : "+cmbSexo.getSelectedIndex());
            tipoSexo = 
                    FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
        	log.info("error :"+e);
            tipoSexo = "";
        }
        
        //log.info("sexo del cliente : "+tipoSexo);

        return tipoSexo;
    }
    /**
     * @autor dubilluz
     * @since 04.10.2009
     * @return
     */
    public boolean validaDocumento(){
        boolean resultado = false;
        VariablesOtros.vDniCliente_bk = txtNumeroDocumento.getText().trim();
        VariablesOtros.vFecNacimiento_bk = txtFechNac.getText().trim();
        boolean pValor = false;
        String pValidaReniec ;
        try {
            pValidaReniec = DBFidelizacion.getIndValidaReniec().trim();
            
            pValor = DBOtros.isValidaDocumento(VariablesOtros.vDniCliente_bk, 
                                             VariablesOtros.vFecNacimiento_bk);
            
            log.debug("pValor-->"+pValor);
            log.debug("pValidaReniec-->"+pValidaReniec);
            
            if(pValidaReniec.trim().equalsIgnoreCase("S")){
                if(pValor){//El dni no esta en reniec entonces debe de validarse el documento
                    DlgFidelizacionValidaDocumento pDlgValida = 
                                new DlgFidelizacionValidaDocumento(myParentFrame,"",true);
                    pDlgValida.setVisible(true);
                    
                    log.debug("Cerrar pantalla.."+FarmaVariables.vAceptar);
                    
                    if(FarmaVariables.vAceptar){
                        resultado = true;//el documento se valido
                    }
                    else{
                        moveFocusTo(tblLista.getRowCount()-1);
                        resultado = false;
                            //o dio escape o cancelo validacion
                    }
                        
                    }
                    else{
                        resultado = true;
                    }
            }
            else{
                resultado = true;
            }
            
        } catch (SQLException e) {
            resultado = true;
            log.info(e.getMessage());
        } finally {

        }
        
        return resultado;
    }
}
