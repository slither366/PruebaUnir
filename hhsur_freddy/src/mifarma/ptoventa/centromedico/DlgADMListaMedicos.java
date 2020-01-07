package mifarma.ptoventa.centromedico;


import componentes.gs.componentes.ExpressionValidate;
import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import componentes.gs.componentes.JTextFieldValidate;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
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

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import common.FarmaColumnData;
import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaGridUtils;
import common.FarmaLoadCVL;
import common.FarmaTableModel;

import common.FarmaUtility;
import common.FarmaVariables;

import mifarma.ptoventa.centromedico.domain.BeanPaciente;
import mifarma.ptoventa.centromedico.domain.VtaCompAtencionMedica;
import mifarma.ptoventa.centromedico.domain.VtaPedidoAtencionMedica;
import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;

import mifarma.ptoventa.centromedico.reference.DBAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAdmisionMedica;
import mifarma.ptoventa.centromedico.reference.UtilityAtencionMedica;
import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;

public class DlgADMListaMedicos extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgADMBuscarPaciente.class);
    private static final long serialVersionUID = -2626325502788986022L;
    private JPanelWhite jContentPane = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlDatos = new JPanelHeader();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblDatos = new JTable();
    FarmaTableModel tableModelListaDatos;
    private JLabelFunction btnEsc = new JLabelFunction();
    private JLabelFunction btnAceptar = new JLabelFunction();
    private Frame MyParentFrame;
    //private VtaCompAtencionMedica vtaCAM;
    //private VtaPedidoAtencionMedica vtaPAM;    
    //private VtaPedidoAtencionMedica vtaPAMBusqueda;
    private String pTipoLista;    
    private JButtonLabel lblNombre = new JButtonLabel();
    
    private JButtonLabel lblTipNumDoc = new JButtonLabel();

    private JButton btnBuscar = new JButton();
    BeanPaciente paciente;
    private JLabel jLabel2 = new JLabel();

    private JTextFieldSanSerif txtApePaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();      
    private JTextFieldSanSerif txtNumDoc =new JTextFieldSanSerif();
    
    private String codigoCMP="";
    private JTextField txtApeMaterno = new JTextField();
    private JButton jButton1 = new JButton();

    /* ************************************************************************ */
    /*                             CONSTRUCTORES                                */
    /* ************************************************************************ */
    public DlgADMListaMedicos() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgADMListaMedicos(Frame parent, String title, boolean modal,String codigoCMP) {
        super(parent, title, modal);
        this.codigoCMP=codigoCMP;
        try {
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    

    /* ************************************************************************ */
    /*                              METODO jbInit                               */
    /* ************************************************************************ */
    private void jbInit() throws Exception {
        this.setSize(new Dimension(722, 485));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Ingrese Datos del M�dico");
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
        pnlDatos.setBounds(new Rectangle(10, 5, 690, 90));
        pnlDatos.setFocusable(false);
    
        
        tblDatos.getTableHeader().setReorderingAllowed(false);
        tblDatos.getTableHeader().setResizingAllowed(false);
        
        lblNombre.setText("Apellidos:");
        lblNombre.setBounds(new Rectangle(15, 50, 70, 15));
        lblNombre.setBackground(Color.white);
        lblNombre.setFocusable(false);
        //lblNombre.setForeground(new Color(255, 90, 33));
        lblNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNombre_actionPerformed(e);
            }
        });
        
        txtApePaterno.setBounds(new Rectangle(80, 50, 160, 20));
        txtApePaterno.setLengthText(20);
        txtApePaterno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtApePaterno_keyPressed(e);
                }
            public void keyTyped(KeyEvent e) {
                   txtApePaterno_keyTyped(e);
            }
        });


        txtNombre.setBounds(new Rectangle(255, 10, 275, 20));
        txtNombre.setLengthText(20);
        txtNombre.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombre_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNombre_keyTyped(e);
                }
            });
        
        
        lblTipNumDoc.setText("CMP");
        lblTipNumDoc.setBounds(new Rectangle(35, 10, 40, 15));
        lblTipNumDoc.setBackground(Color.white);
        lblTipNumDoc.setFocusable(false);
        lblTipNumDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTipNumDoc_actionPerformed(e);
            }
        });


        txtNumDoc.setBounds(new Rectangle(80, 10, 100, 20));
        txtNumDoc.setLengthText(10);
        txtNumDoc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtNumDoc_keyPressed(e);
                }
            public void keyTyped(KeyEvent e) {
                    txtNumDoc_keyTyped(e);
                }
        });

        txtApeMaterno.setBounds(new Rectangle(255, 50, 160, 20));
        txtApeMaterno.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApeMaterno_keyPressed(e);
                }
            });
        jButton1.setText("Limpiar");
        jButton1.setBounds(new Rectangle(545, 50, 90, 20));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(545, 10, 90, 20));
        btnBuscar.setFont(new Font("Dialog", 1, 11));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });


        jLabel2.setText("Nombres:");
        jLabel2.setBounds(new Rectangle(190, 10, 65, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jScrollPane1.setBounds(new Rectangle(10, 95, 690, 300));
        tblDatos.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblDatos_keyPressed(e);
                }
            });
        tblDatos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblDatos_mouseClicked(e);
                }
            });
        btnAceptar.setBounds(new Rectangle(435, 410, 140, 20));
        btnAceptar.setText("[F11] Seleccionar");
        btnAceptar.setFocusable(false);
        btnAceptar.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnAceptar_mouseClicked(e);
                }
            });
        btnEsc.setBounds(new Rectangle(585, 410, 117, 19));
        btnEsc.setText("[ESC] Cerrar");
        btnEsc.setFocusable(false);


        pnlDatos.add(jButton1, null);
        pnlDatos.add(txtApeMaterno, null);
        pnlDatos.add(jLabel2, null);
        pnlDatos.add(btnBuscar, null);
        pnlDatos.add(txtNombre, null);
        pnlDatos.add(lblNombre, null);
        pnlDatos.add(lblTipNumDoc, null);
        pnlDatos.add(txtNumDoc, null);
        pnlDatos.add(txtApePaterno, null);
        jContentPane.add(btnAceptar, null);
        jContentPane.add(btnEsc, null);
        jScrollPane1.getViewport().add(tblDatos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlDatos, null);
        this.getContentPane().add(jContentPane, null);
    }
    
    /* ************************************************************************ */
    /*                           METODO initialize                              */
    /* ************************************************************************ */

    private void initialize(){
            cargaCombo();
            initTable();
      FarmaVariables.vAceptar = false;
    }
      
    /* ************************************************************************ */
    /*                         METODOS INICIALIZACION                           */
    /* ************************************************************************ */

    private void initTable() {
        tableModelListaDatos =
                new FarmaTableModel(ConstantsCentroMedico.columnsListaMedicos, ConstantsCentroMedico.columnsListaMedicos,
                                    0);
        FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos, ConstantsCentroMedico.columnsListaMedicos);
        
        txtNumDoc.setText(codigoCMP);
        if(!(txtNumDoc.getText().trim().equals("")))
        cargaListaMedicos();

    }
    
    private void cargaListaMedicos() {
        try{
            DBAdmisionMedica.listaMedicos(tableModelListaDatos,"",
                                                     txtApePaterno.getText().trim()+" "+txtApeMaterno.getText().trim(),
                                                     txtNombre.getText().trim(),
                                                     txtNumDoc.getText().trim());
            
            if(tableModelListaDatos.data.size()==0)
            FarmaUtility.showMessage(this,"No se encontraron resultados en la busqueda",txtApePaterno);
            
        } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurri� un error al buscar c�digo en maestros :\n" +
                        sql.getMessage(), txtApePaterno);
        }
    }
    
    private void cargaCombo() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
    }
    
    /* ************************************************************************ */
    /*                           METODOS DE EVENTOS                             */
    /* ************************************************************************ */
    
    private void btnBuscar_actionPerformed(ActionEvent e) {
        if(validaDatos()){
            cargaListaMedicos();
            FarmaUtility.moveFocus(txtApePaterno);
        }
    }
    
    public boolean validaDatos(){
        boolean flag=true;
        int cantfiltros=0;
        
        if(!(txtApePaterno.getText().trim().equals(""))){
            cantfiltros++;
        }
        if(!(txtNombre.getText().trim().equals(""))){
            cantfiltros++;
        }
        if(!(txtNumDoc.getText().trim().equals(""))){
            cantfiltros++;
        }
        
        if((cantfiltros<1)){
            FarmaUtility.showMessage(this,"Debe ingresar por los menos un criterio de busqueda",txtApePaterno);
            flag=false;
        }
        
        return flag;
    }

    private void this_windowOpened(WindowEvent e){
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNumDoc);
    }

    private void this_windowClosing(WindowEvent e){
      FarmaUtility.showMessage(this,"Debe presionar la tecla ESC para cerrar la ventana.",txtApePaterno);
    }
    
    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */




    private void chkKeyPressed(KeyEvent e){
      FarmaGridUtils.aceptarTeclaPresionada(e, tblDatos, null, 0);
        
      if (UtilityPtoVenta.verificaVK_F11(e)){
          evento_f11();
      
      }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
        cerrarVentana(false);
      }   
    }
    
    
    private void lblNombre_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(txtNombre);
    }
    private void lblTipNumDoc_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumDoc);
    }
    
    private void txtApePaterno_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApeMaterno);
        }
        chkKeyPressed(e);
    }
    private void txtApeMaterno_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDoc);
        }
        chkKeyPressed(e);
    }
    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtApePaterno);
        }
        chkKeyPressed(e);
    }
    private void txtNumDoc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
              btnBuscar.doClick();
            FarmaUtility.moveFocus(txtNombre);
        }
        chkKeyPressed(e);
    }
    
    private void lblTitulo_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(txtApePaterno);
    }
    
    private void txtApePaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApePaterno, e);
    }
    /*private void txtApeMaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApeMaterno, e);
    }*/
    
    private void txtNombre_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtNombre, e);
    } 
    private void txtNumDoc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumDoc, e);
    }
    
    
    
    private void cerrarVentana(boolean pAceptar){
      FarmaVariables.vAceptar = pAceptar;
      /*if(!(pAceptar))
          VariablesCentroMedico.vCodMedico="";*/
      this.setVisible(false);
      this.dispose();
    }

    /* ************************************************************************ */
    /*                      METODOS DE LOGICA DE NEGOCIO                        */
    /* ************************************************************************ */
    private int getNumFilaSeleccionadaTabla(){
        int cantFila = tblDatos.getRowCount();
        if(cantFila == 0){
            FarmaUtility.showMessage(this, "No hay m�dicos encontrados.", tblDatos);
            FarmaUtility.moveFocus(txtApePaterno);
            return -1;
        }
        int seleccion = tblDatos.getSelectedRow();
        if(seleccion == -1){
            FarmaUtility.showMessage(this, "No ha seleccionado un M�dico.", tblDatos);
            FarmaUtility.moveFocus(txtApePaterno);
        }
        return seleccion;
    }


    private void tblDatos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtApeMaterno_keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumDoc);
        }
        chkKeyPressed(e);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        txtNumDoc.setText("");
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
    }

    private void btnAceptar_mouseClicked(MouseEvent e) {
        evento_f11();
    }
            
    public void evento_f11(){

        int selecRow = getNumFilaSeleccionadaTabla();
        //log.info("apapa");
        if (selecRow!= -1) {
              VariablesCentroMedico.vCodMedico=tblDatos.getValueAt(selecRow, 0).toString().trim();
              VariablesCentroMedico.vCodCMPMedico=tblDatos.getValueAt(selecRow, 1).toString().trim();
              VariablesCentroMedico.vDescMedico=tblDatos.getValueAt(selecRow, 2).toString().trim();
              VariablesCentroMedico.vDescEspecialidad=tblDatos.getValueAt(selecRow, 5).toString().trim();
            //log.info("apapa");
                cerrarVentana(true);
        }
    }

    private void tblDatos_mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            if(tblDatos.getSelectedRow()>=0)
                evento_f11();
        }
    }
}
