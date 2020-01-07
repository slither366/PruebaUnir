package venta.psicotropicos;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.util.ArrayList;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.FarmaGridUtils;
import common.FarmaJTable;
import common.FarmaTableModel;
import common.FarmaUtility;

import venta.psicotropicos.reference.ConstantsPsicotropicos;
import venta.psicotropicos.reference.FacadePsicotropicos;
import venta.recetario.reference.ConstantsRecetario;
import venta.recetario.reference.VariablesRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicaci�n : DlgReportePsicotropicos.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LLEIVA      23.Sep.2013   Creaci�n<br>
 * <br>
 * @author Luis Leiva Baz�n<br>
 * @version 1.0<br>
 *
 */
public class DlgReportePsicotropicos extends JDialog {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgReportePsicotropicos.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblCantidad = new JLabelWhite();
    private JLabelWhite lblPeriodo = new JLabelWhite();
    private JLabelWhite lblProducto = new JLabelWhite();
    
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodProducto = new JTextFieldSanSerif();


    private FacadePsicotropicos facade = new FacadePsicotropicos();
    private FarmaTableModel ftm_psicotropico;
    private JTable tbl_psicotropico = new JTable();
    private JLabelWhite lblDatoCantidad = new JLabelWhite();

    public DlgReportePsicotropicos() {
        this(null, "", false);
    }

    public DlgReportePsicotropicos(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception
    {   this.setSize(new Dimension(714, 486));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Registro de Psicotropicos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pnlFondo.setFocusable(false);
        pnlBusqueda.setBounds(new Rectangle(10, 15, 685, 60));
        pnlTitle.setBounds(new Rectangle(10, 80, 685, 20));
        pnlTitle.setFocusable(false);
        pnlResultados.setBounds(new Rectangle(10, 390, 685, 25));
        pnlResultados.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 100, 685, 290));
        jScrollPane1.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(575, 420, 120, 30));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblTitle.setText("Listado de Registros de Psicotropicos");
        lblTitle.setBounds(new Rectangle(15, 0, 240, 20));
        lblTitle.setFocusable(false);
        lblCantidad.setText("Registros:");
        lblCantidad.setBounds(new Rectangle(20, 0, 70, 25));
        lblCantidad.setFocusable(false);
        lblPeriodo.setText("* Periodo:");
        lblPeriodo.setBounds(new Rectangle(130, 0, 65, 30));
        lblPeriodo.setFocusable(false);
        txtFechaIni.setBounds(new Rectangle(200, 5, 100, 20));
        txtFechaIni.setNextFocusableComponent(txtFechaFin);
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFechaIni_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFechaIni_keyPressed(e);
                }
            });
        txtFechaFin.setBounds(new Rectangle(320, 5, 100, 20));
        txtFechaFin.setNextFocusableComponent(txtCodProducto);
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtFechaFin_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtFechaFin_keyPressed(e);
                }
            });
        lblDatoCantidad.setText("0");
        lblDatoCantidad.setBounds(new Rectangle(85, 0, 55, 25));
        lblProducto.setText("* Producto:");
        lblProducto.setBounds(new Rectangle(130, 35, 70, 20));
        lblProducto.setFocusable(false);
        txtCodProducto.setBounds(new Rectangle(200, 35, 100, 20));
        txtCodProducto.setNextFocusableComponent(txtFechaIni);
        txtCodProducto.setLengthText(8);
        txtCodProducto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCodProducto_keyPressed(e);
                }
            });
        lblF11.setText("[ F11 ] Busqueda");
        lblF11.setBounds(new Rectangle(10, 420, 110, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        tbl_psicotropico.setFocusable(false);
        jScrollPane1.getViewport().add(tbl_psicotropico, null);
        pnlFondo.add(jScrollPane1, null);
        pnlResultados.add(lblDatoCantidad, null);
        pnlResultados.add(lblCantidad, null);
        pnlFondo.add(pnlResultados, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        pnlBusqueda.add(txtCodProducto, null);
        pnlBusqueda.add(lblProducto, null);
        pnlBusqueda.add(txtFechaFin, null);
        pnlBusqueda.add(txtFechaIni, null);
        pnlBusqueda.add(lblPeriodo, null);
        pnlFondo.add(pnlBusqueda, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void initialize()
    {   ftm_psicotropico = new FarmaTableModel(ConstantsPsicotropicos.columnsListaPsicotropicos,
                                                ConstantsPsicotropicos.defaultListaPsicotropicos,
                                                0);
        FarmaUtility.initSimpleList(tbl_psicotropico, 
                                    ftm_psicotropico,
                                    ConstantsPsicotropicos.columnsListaPsicotropicos);
        //llenarTablaGuia();
        txtFechaIni.grabFocus();
    }
    
    private void txtFechaIni_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaIni,e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaFin,e);
    }
    
    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */
    private void chkKeyPressed(KeyEvent e)
    {   
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tbl_psicotropico, 
                                              null, 
                                              0);
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {   ((JComponent)e.getSource()).transferFocus();
        }
        else if(venta.reference.UtilityPtoVenta.verificaVK_F11(e))
        {   if(validacion())
                busqueda();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana();
        }
    }

    private void txtFechaIni_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    private void txtCodProducto_keyPressed(KeyEvent e)
    {   FarmaUtility.admitirDigitos(txtCodProducto, e);
        chkKeyPressed(e);
    }
    
    private void cerrarVentana()
    {   this.setVisible(false);
        this.dispose();
    }

    private void busqueda()
    {   
        ftm_psicotropico.clearTable();
        ArrayList<ArrayList<String>> resultado = facade.listaPsicotropicos(txtFechaIni.getText(), 
                                                                           txtFechaFin.getText(), 
                                                                           txtCodProducto.getText());
        
        if(resultado != null && resultado.size()>0)
        {   for(int i = 0; resultado.size() > i; i++)
            {   ArrayList<String> tmpArrayRow = new ArrayList<String>();
                String tmpFechaKardex = ((ArrayList)resultado.get(i)).get(0).toString().trim();
                String tmpDescripcion = ((ArrayList)resultado.get(i)).get(1).toString().trim();
                String tmpTipoCompPago = ((ArrayList)resultado.get(i)).get(2).toString().trim();
                String tmpNumCompPago = ((ArrayList)resultado.get(i)).get(3).toString().trim();
                String tmpProdAnterior = ((ArrayList)resultado.get(i)).get(4).toString().trim();
                String tmpCantMovProd = ((ArrayList)resultado.get(i)).get(5).toString().trim();
                String tmpSTKFinalProd = ((ArrayList)resultado.get(i)).get(6).toString().trim();
                String tmpValFraccProd = ((ArrayList)resultado.get(i)).get(7).toString().trim();
                String tmpNomCliente = ((ArrayList)resultado.get(i)).get(8).toString().trim();
                String tmpNomMedico = ((ArrayList)resultado.get(i)).get(9).toString().trim();
                String tmpTipoDocKardex = ((ArrayList)resultado.get(i)).get(10).toString().trim();
                String tmpGlosa = ((ArrayList)resultado.get(i)).get(11).toString().trim();
                String tmpSecKardex = ((ArrayList)resultado.get(i)).get(12).toString().trim();
                tmpArrayRow.add(tmpFechaKardex);
                tmpArrayRow.add(tmpDescripcion);
                tmpArrayRow.add(tmpTipoCompPago);
                tmpArrayRow.add(tmpNumCompPago);
                tmpArrayRow.add(tmpProdAnterior);
                tmpArrayRow.add(tmpCantMovProd);
                tmpArrayRow.add(tmpSTKFinalProd);
                tmpArrayRow.add(tmpValFraccProd);
                tmpArrayRow.add(tmpNomCliente);
                tmpArrayRow.add(tmpNomMedico);
                tmpArrayRow.add(tmpTipoDocKardex);
                tmpArrayRow.add(tmpGlosa);
                tmpArrayRow.add(tmpSecKardex);
                ftm_psicotropico.insertRow(tmpArrayRow);
            }
            lblDatoCantidad.setText(String.valueOf(resultado.size()));
        }
        else
        {   FarmaUtility.showMessage(this, 
                                    "No se encontraron registros para los parametros de busqueda", 
                                    null);
        }
    }
    
    private boolean validacion()
    {   boolean flag=true;
        
        if(!txtFechaIni.getText().trim().equals("") &&
            !txtFechaFin.getText().trim().equals("") &&
            !txtCodProducto.getText().trim().equals(""))
        {   
            Calendar fecha_inicial = Calendar.getInstance();
            Calendar fecha_final = (Calendar)fecha_inicial.clone();
            try
            {   fecha_inicial.setTime(FarmaUtility.getStringToDate(txtFechaIni.getText().trim(),
                                                                     "dd/MM/yyyy"));
            }
            catch(Exception ex)
            {   //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha inicial ingresada no es valida", null);
                flag=false;
            }
            
            if(flag)
            {
                try
                {   fecha_final.setTime(FarmaUtility.getStringToDate(txtFechaFin.getText().trim(),
                                                                         "dd/MM/yyyy"));
                }
                catch(Exception ex)
                {   //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha final ingresada no es valida", null);
                    flag=false;
                }
            }
            
            if(flag)
            {   if(FarmaUtility.diferenciaEnDias(fecha_inicial, fecha_final) > 0)
                {   flag=false;
                    FarmaUtility.showMessage(this, 
                                            "ERROR: La fecha final debe ser mayor a la fecha inicial", 
                                            null);
                }
            }
            
            if(flag)
            {   if(!facade.verificaProdPsicotropicos(txtCodProducto.getText().trim()))
                {   flag=false;
                    FarmaUtility.showMessage(this, 
                                            "El producto no corresponde a un producto psicotr�pico", 
                                            null);
                }
            }
        }
        else
        {   flag=false; 
            FarmaUtility.showMessage(this, 
                                     "Alguno de los campos se encuentra vacio", 
                                     null);
        }
        return flag;
    }
}
