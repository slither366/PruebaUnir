package venta.tomainventario;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.*;

import venta.reference.*;
import venta.tomainventario.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaDiferenciasTomaToTal extends JDialog {
    FarmaTableModel tableModelDiferenciasProductos;
    private static final Logger log = LoggerFactory.getLogger(DlgListaDiferenciasTomaToTal.class);

    Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();

    private JPanelTitle jPanelTitle1 = new JPanelTitle();

    private JScrollPane jScrollPane1 = new JScrollPane();

    private JTable tblRelacionDiferenciasProductos = new JTable();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();

    private JButtonLabel btnProductos = new JButtonLabel();

    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabelWhite lblLaboratorio = new JLabelWhite();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaDiferenciasTomaToTal() {
        this(null, "", false);
    }

    public DlgListaDiferenciasTomaToTal(Frame parent, String title, 
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
        this.setSize(new Dimension(795, 393));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Diferencias de Productos");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelHeader1.setBounds(new Rectangle(5, 10, 775, 30));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(5, 45, 775, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(5, 70, 775, 260));
        btnRelacionProductos.setText("Relacion de Diferencias de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 215, 25));
        btnRelacionProductos.setMnemonic('r');
        btnRelacionProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        btnProductos.setText("Productos :");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(30, 5, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProductos_actionPerformed(e);
                    }
                });
        txtProductos.setBounds(new Rectangle(115, 5, 305, 20));
        txtProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtProductos_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtProductos_keyReleased(e);
                    }
                });
        lblF11.setBounds(new Rectangle(545, 340, 120, 20));
        lblF11.setText("[ F12 ] Imprimir");
        lblEscape.setBounds(new Rectangle(670, 340, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        lblF12.setBounds(new Rectangle(305, 340, 225, 20));
        lblF12.setText("[ F11 ] Imprimir Diferencias en Blanco");
        lblLaboratorio.setBounds(new Rectangle(480, 0, 295, 25));
        jScrollPane1.getViewport().add(tblRelacionDiferenciasProductos, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(lblLaboratorio, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaDiferenciasProductos();
    }

    // **************************************************************************
    // M�todos de inicializaci�n
    // **************************************************************************

    private void initTableListaDiferenciasProductos() {
        tableModelDiferenciasProductos = 
                new FarmaTableModel(ConstantsTomaInv.columnsListaDiferenciasTotales, 
                                    ConstantsTomaInv.defaultValuesListaDiferenciasTotales, 
                                    0);
        FarmaUtility.initSimpleList(tblRelacionDiferenciasProductos, 
                                    tableModelDiferenciasProductos, 
                                    ConstantsTomaInv.columnsListaDiferenciasTotales);
        cargaListaDiferenciasProductos();
        
        if (tableModelDiferenciasProductos.data.size()>0){
            FarmaGridUtils.showCell(tblRelacionDiferenciasProductos, 0, 0);
            lblLaboratorio.setText(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasProductos.data,tblRelacionDiferenciasProductos.getSelectedRow(),0));
        }        
        
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblRelacionDiferenciasProductos, 
                                              txtProductos, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblRelacionDiferenciasProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblRelacionDiferenciasProductos, 
                                                    txtProductos.getText().trim(), 
                                                    0, 2))) {
                    FarmaUtility.showMessage(this, 
                                             "Producto No Encontrado seg�n Criterio de B�squeda !!!", 
                                             txtProductos);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (tableModelDiferenciasProductos.data.size()>0){
            lblLaboratorio.setText(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasProductos.data,tblRelacionDiferenciasProductos.getSelectedRow(),0));            
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        } else if (venta.reference.UtilityPtoVenta.verificaVK_F12(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         txtProductos);
            else
                imprimir();
        } else if (venta.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         txtProductos);
            else
                imprimirDiferencias();
        }
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblRelacionDiferenciasProductos, 
                                         txtProductos, 1);

        if (tableModelDiferenciasProductos.data.size()>0){
            lblLaboratorio.setText(FarmaUtility.getValueFieldArrayList(tableModelDiferenciasProductos.data,tblRelacionDiferenciasProductos.getSelectedRow(),0));            
        }

        
    }

    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************

    private void cargaListaDiferenciasProductos() {
        try {
            DBTomaInv.getListaProdsDiferenciasTotales(tableModelDiferenciasProductos);
            if (tblRelacionDiferenciasProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionDiferenciasProductos, 
                                     tableModelDiferenciasProductos, 8, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de de tomas dif");
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener la lista de diferencias :\n" +
                    sql.getMessage(), txtProductos);
        }
    }
    /*
    private void imprimir() {
        if (!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = 
            new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresi�n", 
                                     txtProductos);
            return;
        }
        try {
            String fechaActual = 
                FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.activateCondensed();
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(27) + 
                             " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printLine("Nombre Compa�ia : " + 
                             FarmaVariables.vNomCia.trim(), true);
            vPrint.printLine("Nombre Local : " + 
                             FarmaVariables.vDescLocal.trim(), true);
            vPrint.printLine("Toma #          : " + 
                             VariablesTomaInv.vSecToma.trim(), true);
            vPrint.printLine("Tipo            : " + 
                             VariablesTomaInv.vDescTipoToma.trim(), true);
            vPrint.printLine("Estado          : " + 
                             VariablesTomaInv.vDescEstadoToma.trim(), true);

            vPrint.printLine("Fecha Actual    : " + fechaActual, true);

            
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            ArrayList pListaLaboratorios = new ArrayList();
            String pCodigoLab = "",pCodigoLabAux="";
            for(int i=0;i<tableModelDiferenciasProductos.data.size();i++){
                pCodigoLab = FarmaUtility.getValueFieldArrayList(tableModelDiferenciasProductos.data,i,7).trim();
                if(pListaLaboratorios.size()==0){
                    pListaLaboratorios.add(pCodigoLab.trim());
                }
                else{
                    for(int j=0; j <pListaLaboratorios.size();j++){
                        pCodigoLabAux = pListaLaboratorios.get(0).toString().trim();
                        if(!pCodigoLabAux.equalsIgnoreCase(pCodigoLab)){
                            pListaLaboratorios.add(pCodigoLabAux.trim());
                        }
                    }
                }
            }
            
            log.info("Lista de Laboratorios:"+pListaLaboratorios);
            String pCodigoLabImprimir = "",pCodLabEliminar = "";
            ArrayList pListaProductoLaboratorio = new ArrayList();
            for(int i=0;i<pListaLaboratorios.size();i++){
                pListaProductoLaboratorio = new ArrayList();
                pListaProductoLaboratorio = (ArrayList)tableModelDiferenciasProductos.data.clone();
                pCodigoLabImprimir = pListaLaboratorios.get(i).toString().trim();
                log.info("pCodigoLabImprimir:"+pCodigoLabImprimir);
                for(int j=0;j<pListaProductoLaboratorio.size();j++){
                    pCodLabEliminar = FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,j,7).trim();
                    log.info("pCodLabEliminar:"+pCodLabEliminar);
                    if(!pCodLabEliminar.equalsIgnoreCase(pCodigoLabImprimir)){
                        pListaProductoLaboratorio.remove(j);
                        log.info("elimina lab:"+pCodLabEliminar);
                    }
                }
                
                log.info("pListaProductoLaboratorio:"+pListaProductoLaboratorio.size());
                
                for(int a=0;a<pListaProductoLaboratorio.size();a++){
                    log.info("Linea 1");
                    if(a==0){
                        log.info("Linea 0");
                        vPrint.printLine("Laboratorio     : " +
                                          FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,7).trim() + 
                                         " - " + 
                                        FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,0).trim(), true);
                        vPrint.printLine("=================================================================================================", 
                                         true);
                        vPrint.printBold("C�digo  Descripcion                                 Unidad     Stk Actual   Diferencia    Precio     ", 
                                         true);
                        vPrint.printLine("=================================================================================================", 
                                         true);                        
                    }
                    log.info("Linea 2");
                    
                    vPrint.deactivateCondensed();
                    //vPrint.setEndHeader();
                    //for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); 
                      //   i++) {

                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(
                                                                            FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,1), 
                                                                           8) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,2),
                                                                           44) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,3),  
                                                                           10) + 
                                          " " + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,4), 
                                                                         10) + 
                                          "   " + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,5), 
                                                                         10) + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,6),  
                                                                         10), 
                                          true);
                    //}
                                     
                }
                vPrint.activateCondensed();
                vPrint.printLine("=================================================================================================", 
                                 true);   
            
            }
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            
            
            
            vPrint.printBold("Registros Impresos por todos los Laboratorios: " + 
                             FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), 
                                                       ",##0") + 
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("",sqlerr);
            FarmaUtility.showMessage(this, 
                                     "Ocurri� un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }

    private void imprimirDiferencias() {
        log.info("imprimirDiferencias");
        if (!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        log.info("start Service");
        FarmaPrintService vPrint = 
            new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresi�n", 
                                     txtProductos);
            return;
        }
        log.info("antes del TRY");
        try {
            log.info("antes de fecha actual");
            String fechaActual = 
                FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            log.info("despues de fecha actual");
            //vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(27) + 
                             " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printLine("Nombre Compa�ia : " + 
                             FarmaVariables.vNomCia.trim(), true);
            vPrint.printLine("Nombre Local : " + 
                             FarmaVariables.vDescLocal.trim(), true);
            vPrint.printLine("Toma #          : " + 
                             VariablesTomaInv.vSecToma.trim(), true);
            vPrint.printLine("Tipo            : " + 
                             VariablesTomaInv.vDescTipoToma.trim(), true);
            vPrint.printLine("Estado          : " + 
                             VariablesTomaInv.vDescEstadoToma.trim(), true);
            
            vPrint.printLine("Fecha Actual    : " + fechaActual, true);
            
            log.info("antes del cambio");
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            ArrayList pListaLaboratorios = new ArrayList();
            String pCodigoLab = "",pCodigoLabAux="";
            for(int i=0;i<tableModelDiferenciasProductos.data.size();i++){
                log.info("tableModelDiferenciasProductos:"+i);
                pCodigoLab = FarmaUtility.getValueFieldArrayList(tableModelDiferenciasProductos.data,i,7).trim();
                if(pListaLaboratorios.size()==0){
                    pListaLaboratorios.add(pCodigoLab.trim());
                }
                else{
                    for(int j=0; j <pListaLaboratorios.size();j++){
                        pCodigoLabAux = pListaLaboratorios.get(j).toString().trim();
                        if(!pCodigoLabAux.equalsIgnoreCase(pCodigoLab)){
                            pListaLaboratorios.add(pCodigoLabAux.trim());
                        }
                    }
                }
            }

            log.info("Lista de Laboratorios:"+pListaLaboratorios);
            String pCodigoLabImprimir = "",pCodLabEliminar = "";
            ArrayList pListaProductoLaboratorio = new ArrayList();
            for(int i=0;i<pListaLaboratorios.size();i++){
                    pListaProductoLaboratorio = new ArrayList();
                    pListaProductoLaboratorio = (ArrayList)tableModelDiferenciasProductos.data.clone();
                    pCodigoLabImprimir = pListaLaboratorios.get(i).toString().trim();
                    log.info("pCodigoLabImprimir:"+pCodigoLabImprimir);
                    for(int j=0;j<pListaProductoLaboratorio.size();j++){
                        pCodLabEliminar = FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,j,7).trim();
                        log.info("pCodLabEliminar:"+pCodLabEliminar);
                        if(!pCodLabEliminar.equalsIgnoreCase(pCodigoLabImprimir)){
                            pListaProductoLaboratorio.remove(j);
                            log.info("elimina lab:"+pCodLabEliminar);
                        }
                    }
                    
                    log.info("pListaProductoLaboratorio:"+pListaProductoLaboratorio.size());
                    
                    for(int a=0;a<pListaProductoLaboratorio.size();a++){
                        log.info("Linea 1");
                        if(a==0){
                            log.info("Linea 0");
                            vPrint.printLine("Laboratorio     : " +
                                              FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,7).trim() + 
                                             " - " + 
                                            FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,0).trim(), true);
                            vPrint.printLine("=================================================================================================", 
                                             true);
                            vPrint.printBold("C�digo  Descripcion                                 Unidad       C.Entera/C.Fraccion     ", 
                                             true);
                            vPrint.printLine("=================================================================================================", 
                                             true);
                            vPrint.deactivateCondensed();                      
                        }
                        log.info("Linea 2");
                        
                        vPrint.deactivateCondensed();
    
    
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,1), 
                                                                           8) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,2), 
                                                                           44) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,3), 
                                                                           10) + 
                                          "   " + 
                                          FarmaPRNUtility.alinearDerecha("_________/________", 
                                                                         19), 
                                          true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=================================================================================================", 
                                 true);
            }
            
            vPrint.printBold("Registros Impresos: " + 
                             FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), 
                                                       ",##0") + 
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            
            
            //vPrint.endPrintService();
        } catch (Exception sqlerr) {
            log.error("",sqlerr);
            FarmaUtility.showMessage(this, 
                                     "Ocurri� un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }
    */
    private void imprimir() {
        if (!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "�Seguro que desea imprimir?"))
            return;
        FarmaPrintService vPrint = 
            new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresi�n", 
                                     txtProductos);
            return;
        }
        try {
            String fechaActual = 
                FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            vPrint.activateCondensed();
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(27) + 
                             " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printLine("Nombre Compa�ia : " + 
                             FarmaVariables.vNomCia.trim(), true);
            vPrint.printLine("Nombre Local : " + 
                             FarmaVariables.vDescLocal.trim(), true);
            vPrint.printLine("Toma #          : " + 
                             VariablesTomaInv.vSecToma.trim(), true);
            /*
            vPrint.printLine("Tipo            : " + 
                             VariablesTomaInv.vDescTipoToma.trim(), true);
            vPrint.printLine("Estado          : " + 
                             VariablesTomaInv.vDescEstadoToma.trim(), true);
            */

            vPrint.printLine("Fecha Actual    : " + fechaActual, true);

            
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            ArrayList pListaLaboratorios = new ArrayList();
            String pCodigoLab = "",pCodigoLabAux="";
            
            DBTomaInv.getLabTomaInv(pListaLaboratorios);
            
            log.info("Lista de Laboratorios:"+pListaLaboratorios);
            
            String pCodigoLabImprimir = "",pCodLabEliminar = "";
            ArrayList pListaProductoLaboratorio = new ArrayList();
            
            for(int i=0;i<pListaLaboratorios.size();i++){
                
                pCodigoLabImprimir = FarmaUtility.getValueFieldArrayList(pListaLaboratorios,i,0);
                log.info("pCodigoLabImprimir:"+pCodigoLabImprimir);
                
                vPrint.printLine("Laboratorio     : " +
                                  FarmaUtility.getValueFieldArrayList(pListaLaboratorios,i,0).trim() + 
                                 " - " + 
                                FarmaUtility.getValueFieldArrayList(pListaLaboratorios,i,1).trim(), true);
                vPrint.printLine("=================================================================================================", 
                                 true);
                vPrint.printBold("C�digo  Descripcion                                 Unidad     Stk Actual   Diferencia    Precio     ", 
                                 true);
                vPrint.printLine("=================================================================================================", 
                                 true);                        
                pListaProductoLaboratorio = new ArrayList();
                DBTomaInv.getProductoLabTomaInv(pListaProductoLaboratorio,pCodigoLabImprimir);                
                for(int a=0;a<pListaProductoLaboratorio.size();a++){
                    //log.info("Linea 1");
                    // log.info("Linea 2");
                    
                    vPrint.deactivateCondensed();
                    //vPrint.setEndHeader();
                    //for (int i = 0; i < tblRelacionDiferenciasProductos.getRowCount(); 
                      //   i++) {

                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(
                                                                            FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,1), 
                                                                           8) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,2),
                                                                           44) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,3),  
                                                                           10) + 
                                          " " + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,4), 
                                                                         10) + 
                                          "   " + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,5), 
                                                                         10) + 
                                          FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,6),  
                                                                         10), 
                                          true);
                    //}
                                     
                }
                vPrint.activateCondensed();
                vPrint.printLine("=================================================================================================", 
                                 true);   
            
            }
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            
            
            
            vPrint.printBold("Registros Impresos por todos los Laboratorios: " + 
                             FarmaUtility.formatNumber(tblRelacionDiferenciasProductos.getRowCount(), 
                                                       ",##0") + 
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            FarmaUtility.showMessage(this, 
                                     "Por favor de recoger la Impresi�n", txtProductos);
            
        } catch (Exception sqlerr) {
            log.error("",sqlerr);
            FarmaUtility.showMessage(this, 
                                     "Ocurri� un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }

    private void imprimirDiferencias() {
        log.info("imprimirDiferencias");
        if (!componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                            "Seguro que desea imprimir?"))
            return;
        //FarmaPrintService vPrint = new FarmaPrintService(45,
        log.info("start Service");
        FarmaPrintService vPrint = 
            new FarmaPrintService(66, FarmaVariables.vImprReporte, true);
        if (!vPrint.startPrintService()) {
            FarmaUtility.showMessage(this, 
                                     "No se pudo inicializar el proceso de impresi�n", 
                                     txtProductos);
            return;
        }
        log.info("antes del TRY");
        try {
            log.info("antes de fecha actual");
            String fechaActual = 
                FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            log.info("despues de fecha actual");
            //vPrint.setStartHeader();
            vPrint.activateCondensed();
            vPrint.printLine(FarmaPRNUtility.llenarBlancos(27) + 
                             " REPORTE DE DIFERENCIAS DE PRODUCTOS", true);
            vPrint.printLine("Nombre Compa�ia : " + 
                             FarmaVariables.vNomCia.trim(), true);
            vPrint.printLine("Nombre Local : " + 
                             FarmaVariables.vDescLocal.trim(), true);
            vPrint.printLine("Toma #          : " + 
                             VariablesTomaInv.vSecToma.trim(), true);
            /*
            vPrint.printLine("Tipo            : " + 
                             VariablesTomaInv.vDescTipoToma.trim(), true);
            vPrint.printLine("Estado          : " + 
                             VariablesTomaInv.vDescEstadoToma.trim(), true);
            */
            /*vPrint.printLine("Laboratorio     : " + VariablesTomaInv.vCodLab + 
                             " - " + VariablesTomaInv.vNomLab.trim(), true);
            */
            vPrint.printLine("Fecha Actual    : " + fechaActual, true);
            
            log.info("antes del cambio");
            ///ESTO SE DEBE IMPRIMIR POR BLOQUE DE LABORATORIO
            ArrayList pListaLaboratorios = new ArrayList();
            DBTomaInv.getLabTomaInv(pListaLaboratorios);

            log.info("Lista de Laboratorios:"+pListaLaboratorios);
            String pCodigoLabImprimir = "",pCodLabEliminar = "";
            ArrayList pListaProductoLaboratorio = new ArrayList();
            int cantidadImpresos = 0;
            
            for(int i=0;i<pListaLaboratorios.size();i++){
                    pCodigoLabImprimir = FarmaUtility.getValueFieldArrayList(pListaLaboratorios,i,0).trim() ;
                    log.info("pCodigoLabImprimir:"+pCodigoLabImprimir);
                
                    DBTomaInv.getProductoLabTomaInv(pListaProductoLaboratorio,pCodigoLabImprimir);                
                    
                    log.info("pListaProductoLaboratorio:"+pListaProductoLaboratorio.size());
                    vPrint.printLine("Laboratorio     : " +
                                      FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,i,0).trim() + 
                                     " - " + 
                                    FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,i,1).trim(), true);
                    vPrint.printLine("=================================================================================================", 
                                     true);
                    vPrint.printBold("C�digo  Descripcion                                 Unidad       C.Entera/C.Fraccion     ", 
                                     true);
                    vPrint.printLine("=================================================================================================", 
                                     true);
                    vPrint.deactivateCondensed();
                
                    for(int a=0;a<pListaProductoLaboratorio.size();a++){
                        //log.info("Linea 1");
                        //log.info("Linea 2");
                        
                        vPrint.deactivateCondensed();
    
                    cantidadImpresos ++;
                    vPrint.printCondensed(FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,1), 
                                                                           8) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,2), 
                                                                           44) + 
                                          FarmaPRNUtility.alinearIzquierda(FarmaUtility.getValueFieldArrayList(pListaProductoLaboratorio,a,3), 
                                                                           10) + 
                                          "   " + 
                                          FarmaPRNUtility.alinearDerecha("_________/________", 
                                                                         19), 
                                          true);
                }
                vPrint.activateCondensed();
                vPrint.printLine("=================================================================================================", 
                                 true);
            }
            
            vPrint.printBold("Registros Impresos: " + 
                             FarmaUtility.formatNumber(//tblRelacionDiferenciasProductos.getRowCount(), 
                                                        cantidadImpresos,
                                                       ",##0") + 
                             FarmaPRNUtility.llenarBlancos(11), true);
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            FarmaUtility.showMessage(this, 
                                     "Por favor de recoger la Impresi�n", txtProductos);
        } catch (Exception sqlerr) {
            log.error("",sqlerr);
            FarmaUtility.showMessage(this, 
                                     "Ocurri� un error al imprimir : \n" +
                    sqlerr.getMessage(), txtProductos);
        }
    }


    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblRelacionDiferenciasProductos);
    }

}
