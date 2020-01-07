package venta.administracion.fondoSencillo;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import componentes.gs.componentes.JTextFieldSanSerif;

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

import javax.swing.JDialog;

import javax.swing.JFrame;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;

import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.fondoSencillo.reference.ConstantsFondoSencillo;

import venta.administracion.fondoSencillo.reference.DBFondoSencillo;
import venta.administracion.fondoSencillo.reference.VariablesFondoSencillo;
import venta.recepcionCiega.reference.DBRecepCiega;
import venta.recepcionCiega.reference.VariablesRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgAsignarSencilloCajero extends JDialog {
    //Declarando Variables Globales 
    Frame myParentFrame;
    FarmaTableModel tableModelCajeros;
    
    private static final Logger log = LoggerFactory.getLogger(DlgAsignarSencilloCajero.class);
    
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnEscogerCajero = new JButtonLabel();
    private JScrollPane scrListaCajero = new JScrollPane();
    private JTable tblCajeros = new JTable();
    
    private JLabelFunction btnAsignar = new JLabelFunction();
    private JLabelFunction btnSalir = new JLabelFunction();
    private JPanelHeader pnlHead = new JPanelHeader();
    private JButtonLabel btnCajero = new JButtonLabel();
    private JTextFieldSanSerif txtCajero = new JTextFieldSanSerif();
    
    private static final int ColSecUsuCaje = 0;
    private static final int ColNombresCaje = 1;
    private static final int ColApePaternoCaje = 2;
    private static final int ColApeMaternoCaje = 3;
    private static final int ColLoginUsuCaje = 4;

    public DlgAsignarSencilloCajero() {
        this(null, "", false);
    }

    public DlgAsignarSencilloCajero(Frame parent, String title, 
                                    boolean modal) {
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
        //***Valores B�sicos Pantalla ***
        this.setSize(new Dimension(563, 322));
        this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
        this.setTitle("Asignar Fondo de Sencillo a Cajero");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                    public void windowClosing(WindowEvent e)
                    {
                      this_windowClosing(e);
                    }
                });
        //***fin ***

        pnlHead.add(txtCajero, null);
        pnlHead.add(btnCajero, null);
        jContentPane.add(pnlHead, null);
        jContentPane.add(btnSalir, null);

        jContentPane.add(btnAsignar, null);
        scrListaCajero.getViewport().add(tblCajeros, null);
        jContentPane.add(scrListaCajero, null);
        pnlTitulo.add(btnEscogerCajero, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.setBounds(new Rectangle(0, 0, 545, 340));
        pnlTitulo.setBounds(new Rectangle(5, 45, 545, 20));
        btnEscogerCajero.setText("Escoger Cajero");
        //btnEscogerCajero.setMnemonic('e');
        btnEscogerCajero.setBounds(new Rectangle(10, 0, 95, 20));
        btnEscogerCajero.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnEscogerCajero_actionPerformed(e);
                    }
                });
        scrListaCajero.setBounds(new Rectangle(5, 65, 545, 200));
        tblCajeros.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblCajeros_keyPressed(e);
                    }
                });
        
        btnAsignar.setBounds(new Rectangle(5, 270, 117, 20));
        btnAsignar.setText("[Enter] Asignar");
        btnSalir.setBounds(new Rectangle(470, 270, 80, 20));
        btnSalir.setText("[Esc] Salir");
        pnlHead.setBounds(new Rectangle(5, 5, 545, 40));
        btnCajero.setText("Cajero:");
        btnCajero.setMnemonic('c');
        btnCajero.setBounds(new Rectangle(10, 15, 75, 15));
        btnCajero.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCajero_actionPerformed(e);
                    }
                });
        txtCajero.setBounds(new Rectangle(60, 10, 235, 20));

        txtCajero.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtCajero_keyPressed(e);
                    }
                });
       
    }
    
    private void initialize(){
        FarmaVariables.vAceptar = false;
        initTableHistorico();
        //permite que no se muevan las columnas de Jtable
        tblCajeros.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tama�o de la columna del Jtable
        tblCajeros.getTableHeader().setResizingAllowed(false);
        setJTable(tblCajeros,txtCajero);
    }
    
    private void initTableHistorico(){
        tableModelCajeros = 
                new FarmaTableModel(ConstantsFondoSencillo.columnsListaCajerosDisponibles, 
                                    ConstantsFondoSencillo.defaultValuesListaCajerosDisponibles, 
                                    0);
        FarmaUtility.initSimpleList(tblCajeros, 
                                    tableModelCajeros, 
                                    ConstantsFondoSencillo.columnsListaCajerosDisponibles);
        cargaListaCajerosDisponibles();
        FarmaUtility.moveFocus(txtCajero);
    }
    
    
    private void this_windowClosing(WindowEvent e)
    {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtCajero);
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);    
        FarmaUtility.moveFocus(txtCajero);
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;      
      this.setVisible(false);
      this.dispose();
    }
    
    private void chkKeyPressed(KeyEvent e) {
       
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){ 
            e.consume();
            cerrarVentana(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            e.consume();
            funcionEnter();
        }
        else if(venta.reference.UtilityPtoVenta.verificaVK_F11(e)){ 
            e.consume();
            funcionF11();
        }
    }

    private void btnEscogerCajero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblCajeros);        
    }
    
    private void funcionF11(){
        
    }
    private void funcionEnter(){
        if(tblCajeros.getRowCount() <= 0) return;
        
        int vFila = tblCajeros.getSelectedRow();
        log.debug("vFila: "+vFila);
        if (vFila != -1){  
        //String algo = FarmaUtility.getValueFieldJTable(tblCajeros,vFila,Col_Aux_Conteo);
        VariablesFondoSencillo.vSecUsuCajero = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data,vFila,ColSecUsuCaje).trim();
        VariablesFondoSencillo.vNombresCajero = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data,vFila,ColNombresCaje).trim();
        VariablesFondoSencillo.vApePaterno = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data,vFila,ColApePaternoCaje).trim();
        VariablesFondoSencillo.vApeMaterno = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data,vFila,ColApeMaternoCaje).trim();
        VariablesFondoSencillo.vLoginUsuCaje = FarmaUtility.getValueFieldArrayList(tableModelCajeros.data,vFila,ColLoginUsuCaje).trim();
        log.debug("Var.vSecUsuCajero:"+VariablesFondoSencillo.vSecUsuCajero);
        
        //JMIRANDA 04.05.2010 valida que no tenga asignado monto
        
        mostrarMontoFondo();
            if(FarmaVariables.vAceptar){
                //cargaListaCajerosDisponibles();
                cerrarVentana(true);
                }else{
                    cargaListaCajerosDisponibles();
                    setJTable(tblCajeros,txtCajero);
                }
        }
        else {
            FarmaUtility.showMessage(this,"No ha seleccionado ning�n cajero.",txtCajero);
        }
    }

    private void mostrarMontoFondo(){
        DlgAsignarMontoFondoSencillo dlgAsignarMonto = new DlgAsignarMontoFondoSencillo(myParentFrame,"",true);
        dlgAsignarMonto.setVisible(true);
    }
    private void tblCajeros_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblCajeros,null,0);
        chkKeyPressed(e);
    }
    
    private void cargaListaCajerosDisponibles(){
        try {
                DBFondoSencillo.getListaCajerosDisponibles(tableModelCajeros);
                /*if (tblCajeros.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblCajeros, tableModelCajeros, 1,FarmaConstants.ORDEN_ASCENDENTE);
                }   */
        } catch (SQLException sql) {
            log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurri� un error al cargar la lista de Cajeros : \n",txtCajero);   
        }
    }


    private void txtCajero_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblCajeros,txtCajero,1);
        FarmaGridUtils.buscarDescripcion(e,tblCajeros,txtCajero,1);
        chkKeyPressed(e);
    }
  
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,null,0);
      }
      FarmaUtility.moveFocus(pText);
    }

    private void btnCajero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCajero);
        setJTable(tblCajeros,txtCajero);
    }
}
