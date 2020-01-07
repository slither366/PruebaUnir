package venta.administracion.impresoras;


///public class DlgListaImpresoraTermCreaMod {
  


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.impresoras.reference.ConstantsImpresoras;
import venta.administracion.impresoras.reference.DBImpresoras;
import venta.administracion.impresoras.reference.VariablesImpresoras;
import venta.caja.reference.PrintConsejo;
import venta.caja.reference.UtilityCaja;
import venta.reference.ConstantsPtoVenta;
import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//JMIRANDA 25/06/2009
public class DlgListaImpresoraTermCreaMod extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaImpresoraTermCreaMod.class);
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaImp = new JPanelTitle();

    private JLabelFunction lblCrear = new JLabelFunction();

    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblsc = new JLabelFunction();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JTable tblListaImpresoras = new JTable();

    private JButtonLabel btnRelacionImp = new JButtonLabel();
    //private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaImpresoraTermCreaMod() {
        this(null, "", false);
    }

    public DlgListaImpresoraTermCreaMod(Frame parent, String title, 
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

    // **************************************************************************
    // M�todo "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(489, 315));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento de Impresoras T�rmicas");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setLayout(null);
        pnlHeaderListaImp.setBounds(new Rectangle(10, 10, 465, 25));
        lblCrear.setBounds(new Rectangle(170, 260, 95, 20));
        lblCrear.setText("[F2] Crear");
        lblModificar.setBounds(new Rectangle(270, 260, 105, 20));
        lblModificar.setText("[F3] Modificar");
        lblsc.setBounds(new Rectangle(380, 260, 95, 20));
        lblsc.setText("[Esc]Salir");
        scrListaImpresoras.setBounds(new Rectangle(10, 35, 465, 215));
        tblListaImpresoras.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaImpresoras_keyPressed(e);
                    }
                });
        btnRelacionImp.setText("Relaci�n de Impresoras");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
        btnRelacionImp.setMnemonic('r');
        btnRelacionImp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionImp_actionPerformed(e);
                    }
                });
        //lblF1.setBounds(new Rectangle(10, 260, 135, 20));
        //lblF1.setText("[ F1 ] Prueba Ticket");
        lblF5.setBounds(new Rectangle(10, 260, 155, 20));
        lblF5.setText("[ F5 ] Prueba de Consejo");
        scrListaImpresoras.getViewport().add(tblListaImpresoras, null);
        //jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    }

    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************

    private void initTable() {
        tableModel = 
                new FarmaTableModel(ConstantsImpresoras.columnsListaImpresorasTermicas, 
                                    ConstantsImpresoras.defaultValuesListaImpresorasTermicas, 
                                    0);
        FarmaUtility.initSimpleList(tblListaImpresoras, tableModel, 
                                    ConstantsImpresoras.columnsListaImpresorasTermicas);
        cargaListaImpresoras();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        VariablesPtoVenta.vEjecutaAccionTecla = false;
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaImpresoras);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (!VariablesPtoVenta.vEjecutaAccionTecla) {
            VariablesPtoVenta.vEjecutaAccionTecla = true;
            //JMIRANDA 25/06/2009
            /*if (venta.reference.UtilityPtoVenta.verificaVK_F1(e)) {
                //JCORTEZ 17/03/09
              
            } else if*/
            if (venta.reference.UtilityPtoVenta.verificaVK_F2(e)) {
                if (FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this, 
                                             ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                             btnRelacionImp);
                else {
                    VariablesImpresoras.limpiarImpresoraTermica();
                    
                    DlgDatosImpresoraTermica dlgDatosImpresoraTermica = 
                        new DlgDatosImpresoraTermica(myParentFrame, "", true);
                    dlgDatosImpresoraTermica.setVisible(true);
                    if (FarmaVariables.vAceptar) {
                        cargaListaImpresoras();
                        FarmaVariables.vAceptar = false;
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {

                //JCORTEZ 14.04.09
                //JMIRANDA MODIFICADO 24/06/2009

                if (FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this, 
                                             ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                             btnRelacionImp);
                else {
                    if (tieneRegistroSeleccionado(this.tblListaImpresoras)) {
//setea variables
                        cargarImpresoraSeleccionada();
                        DlgDatosImpresoraTermica dlgDatosImpresoraTermica = 
                            new DlgDatosImpresoraTermica(this.myParentFrame, "", 
                                                   true);
                        dlgDatosImpresoraTermica.setVisible(true);
                        if (FarmaVariables.vAceptar) {
//chequear                            
                            cargaListaImpresoras();
                            FarmaVariables.vAceptar = false;
                        }
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                /*if (tieneRegistroSeleccionado(this.tblListaImpresoras)) {

                                    if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                    "�Esta seguro de cambiar el estado a la impresora?")) {
                                            cargarImpresoraSeleccionada();
                                            try {
                                                    cambiarEstadoSeleccionado();
                                                    FarmaUtility.aceptarTransaccion();
                                                    cargaListaImpresoras();
                                                    FarmaUtility.showMessage(this,
                                                                    "La operaci�n se realiz� correctamente",
                                                                    tblListaImpresoras);
                                            } catch (SQLException ex) {
                                                    FarmaUtility.liberarTransaccion();
                                                    FarmaUtility.showMessage(this,
                                                                    "Ocurri� un error en la transacci�n: "
                                                                                    + ex.getMessage(), tblListaImpresoras);
                                                    log.error("",ex);
                                            }
                                    }
                            }*/

            } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                //log.debug("tableModel.data:"+tableModel.data);
                UtilityCaja.pruebaImpTermicaPersonalizada(this, 
                                                          tblListaImpresoras, 
                                                          FarmaUtility.getValueFieldArrayList(tableModel.data, 
                                                                                              tblListaImpresoras.getSelectedRow(), 
                                                                                              1),
                FarmaUtility.getValueFieldArrayList(tableModel.data, 
                                                    tblListaImpresoras.getSelectedRow(), 
                                                    4));
                     //PrintConsejo.imprimirCupon("hola","CONSEJO",002,"", "");
                     // JMIRANDA  26/06/2009
                
            } else if (e.getKeyCode() == KeyEvent.VK_F6) {

                //JCORTEZ 04.06.09
                //Se mostrara lista de IP relacionadas a la impresora
                /* VariablesImpresoras.vTipoComp = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
            VariablesImpresoras.vSecImpr = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();
            VariablesImpresoras.vNumSerie = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 3).toString().trim();

            if ( VariablesImpresoras.vTipoComp.equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)){ //solo para tipo ticket
                DlgListaIPSImpresora dlgip =new DlgListaIPSImpresora(this.myParentFrame,"",true);
                dlgip.setVisible(true);
            }else
                FarmaUtility.showMessage(this,"Opcion no habilitada para esta impresora.",tblListaImpresoras);
            */
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }
            VariablesPtoVenta.vEjecutaAccionTecla = false;
        }

    }

    private void btnRelacionImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaImpresoras);
    }
    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void cargarImpresoraSeleccionada() {
        //SETEAR VARIABLES IMPRESORA TERMICA
        
        VariablesImpresoras.vSecImprLocalTerm = 
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 
                                              0).toString().trim();

        VariablesImpresoras.vDescImprLocalTerm = 
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 
                                              1).toString().trim();

        VariablesImpresoras.vMarcaImprLocalTerm = 
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 
                                              2).toString().trim();

        VariablesImpresoras.vEstImprLocalTerm = 
                tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 
                                              3).toString().trim().toUpperCase();        
        
    }

    private void cambiarEstadoSeleccionado() throws SQLException {
        DBImpresoras.cambiaEstadoImpresora(FarmaVariables.vCodGrupoCia, 
                                           FarmaVariables.vCodLocal, 
                                           VariablesImpresoras.vSecImprLocal);

    }

    private void cargaListaImpresoras() {
    //JMIRANDA MODIFICACION 25/06/2009
        try {
            DBImpresoras.getListaImpresorasTermicas(tableModel);
            if (tblListaImpresoras.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaImpresoras, tableModel, 0, "asc");
            log.debug("se cargo la lista de impresoras");
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener lista de impresoras. \n " + 
                                     e.getMessage(), tblListaImpresoras);
        }
    }

    private void tblListaImpresoras_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        /**
         * Agregado.
         * @author Rudy Llantoy
         * @since 17.05.2013
         */
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
