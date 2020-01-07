package dental.laboratorio;


import common.FarmaColumnData;
import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import dental.laboratorio.reference.ConstantsMantenimiento;
import dental.laboratorio.reference.DBMantenimiento;
import dental.laboratorio.reference.VariablesMantenimiento;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;


public class DlgMantProducto extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProducto.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNombre = new JButtonLabel();
    private JTextFieldSanSerif txtDescripcionProducto = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    FarmaTableModel modelUnidadPrecio,modelLoteProd;
    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";
    private JButtonLabel lblDireccion = new JButtonLabel();
    private JTextField txtCosto = new JTextField();
    private JTextField txtUnidadEntera = new JTextField();
    private JLabel lblTelefono = new JLabel();
    private JComboBox cmbFraccionado = new JComboBox();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbEstado = new JComboBox();
    private JLabel jLabel2 = new JLabel();
    private JComboBox cmbMarca = new JComboBox();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JTextField txtPorcGanancia = new JTextField();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JLabel jLabel5 = new JLabel();
    private JButton btnAñadirUnidad = new JButton();
    private JButton btnEliminarUnidad = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel6 = new JLabel();
    private JButton btnAñadirLote = new JButton();
    private JButton btnEliminarLote = new JButton();
    private JTextField txtCantPresentacion = new JTextField();
    private JLabel jLabel7 = new JLabel();
    private JTable tblListaUnidadProd = new JTable();
    private JTable tblLoteProd = new JTable();
    private ArrayList listaUnidad = new ArrayList();
    private ArrayList listaLote = new ArrayList();
    
    private String pCodProd_in = "";

    public DlgMantProducto() {
        this(null, "", false, "I", "N");
    }

    public DlgMantProducto(Frame parent, String title, boolean modal, String pAccion,String pCodProd) {
        super(parent, title, modal);
        try {
            if (pAccion.trim().equalsIgnoreCase("I"))
                pInsert = true;
            else if (pAccion.trim().equalsIgnoreCase("U")) {
                pUpdate = true;
                pCodProd_in = pCodProd;
            }
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(952, 509));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento Producto :");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 925, 420));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtDescripcionProducto.setBounds(new Rectangle(215, 55, 130, 20));
        lblNombre.setText("Descripcion:");
        lblNombre.setMnemonic('N');
        lblNombre.setBounds(new Rectangle(10, 13, 105, 15));
        lblNombre.setForeground(new Color(0, 114, 169));
        lblNombre.setFocusable(false);
        txtDescripcionProducto.setBounds(new Rectangle(100, 10, 745, 20));
        txtDescripcionProducto.setLengthText(300);


        txtDescripcionProducto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombreLaboratorio_keyPressed(e);
                }
            });
        btnF11.setBounds(new Rectangle(810, 440, 117, 20));
        btnEsc.setBounds(new Rectangle(5, 440, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnF11_mouseClicked(e);
            }
        });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        lblDireccion.setText("Costo :");
        lblDireccion.setBounds(new Rectangle(195, 82, 60, 20));
        lblDireccion.setForeground(new Color(0, 114, 169));
        lblDireccion.setFont(new Font("SansSerif", 1, 11));
        lblDireccion.setMnemonic('D');
        txtCosto.setBounds(new Rectangle(245, 82, 100, 20));
        txtCosto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDireccion_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCosto_keyTyped(e);
                }
            });
        txtUnidadEntera.setBounds(new Rectangle(95, 42, 180, 20));
        txtUnidadEntera.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }
            });
        lblTelefono.setText("Unidad Entera:");
        lblTelefono.setBounds(new Rectangle(10, 42, 105, 20));
        lblTelefono.setFont(new Font("SansSerif", 1, 11));
        lblTelefono.setForeground(new Color(0, 114, 169));
        cmbFraccionado.setBounds(new Rectangle(100, 80, 70, 25));
        cmbFraccionado.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbFraccionado_keyPressed(e);
                }
            });
        jLabel1.setText("Fraccionado:");
        jLabel1.setBounds(new Rectangle(10, 80, 85, 25));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 107, 165));
        cmbEstado.setBounds(new Rectangle(635, 40, 145, 25));
        cmbEstado.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbEstado_keyPressed(e);
                }
            });
        jLabel2.setText("Estado:");
        jLabel2.setBounds(new Rectangle(585, 45, 50, 15));
        jLabel2.setForeground(new Color(0, 107, 165));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        cmbMarca.setBounds(new Rectangle(340, 40, 225, 25));
        cmbMarca.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbMarca_keyPressed(e);
                }
            });
        jLabel3.setText("Marca:");
        jLabel3.setBounds(new Rectangle(290, 45, 55, 15));
        jLabel3.setForeground(new Color(0, 107, 165));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setText("% Ganancia");
        jLabel4.setBounds(new Rectangle(360, 85, 65, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(new Color(0, 107, 165));
        txtPorcGanancia.setBounds(new Rectangle(435, 82, 100, 20));
        txtPorcGanancia.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPorcGanancia_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtPorcGanancia_keyTyped(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 150, 535, 245));
        jScrollPane2.setBounds(new Rectangle(560, 150, 355, 245));
        jLabel5.setText("Unidad por Precio");
        jLabel5.setBounds(new Rectangle(10, 8, 110, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(SystemColor.window);
        btnAñadirUnidad.setText("+");
        btnAñadirUnidad.setBounds(new Rectangle(115, 5, 40, 20));
        btnAñadirUnidad.setFont(new Font("SansSerif", 1, 11));
        btnAñadirUnidad.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnAñadirUnidad_mouseClicked(e);
                }
            });
        btnAñadirUnidad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnAñadirUnidad_actionPerformed(e);
                }
            });
        btnAñadirUnidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnAñadirUnidad_keyPressed(e);
                }
            });
        btnEliminarUnidad.setText("-");
        btnEliminarUnidad.setBounds(new Rectangle(160, 5, 40, 20));
        btnEliminarUnidad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnEliminarUnidad_actionPerformed(e);
                }
            });
        jPanel1.setBounds(new Rectangle(10, 120, 535, 30));
        jPanel1.setLayout(null);
        jPanel1.setBackground(new Color(0, 107, 165));
        jPanel2.setBounds(new Rectangle(560, 120, 355, 30));
        jPanel2.setLayout(null);
        jPanel2.setBackground(new Color(0, 107, 165));
        jLabel6.setText("Lote");
        jLabel6.setBounds(new Rectangle(10, 8, 55, 15));
        jLabel6.setForeground(SystemColor.window);
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        btnAñadirLote.setText("+");
        btnAñadirLote.setBounds(new Rectangle(55, 5, 45, 20));
        btnAñadirLote.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    btnAñadirLote_mouseClicked(e);
                }
            });
        btnAñadirLote.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnAñadirLote_actionPerformed(e);
                }
            });
        btnAñadirLote.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnAñadirLote_keyPressed(e);
                }
            });
        btnEliminarLote.setText("-");
        btnEliminarLote.setBounds(new Rectangle(115, 5, 45, 20));
        btnEliminarLote.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnEliminarLote_actionPerformed(e);
                }
            });
        txtCantPresentacion.setBounds(new Rectangle(660, 80, 120, 25));
        txtCantPresentacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCantPresentacion_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCantPresentacion_keyTyped(e);
                }
            });
        jLabel7.setText("Cant Presentaci\u00f3n :");
        jLabel7.setBounds(new Rectangle(550, 80, 110, 25));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel7.setForeground(new Color(0, 107, 165));
        tblListaUnidadProd.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblListaUnidadProd_keyPressed(e);
                }
            });
        tblListaUnidadProd.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblListaUnidadProd_mouseClicked(e);
                }
            });
        tblLoteProd.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblLoteProd_keyPressed(e);
                }
            });
        jPanel1.add(btnEliminarUnidad, null);
        jPanel1.add(btnAñadirUnidad, null);
        jPanel1.add(jLabel5, null);
        jPanel2.add(btnEliminarLote, null);
        jPanel2.add(btnAñadirLote, null);
        jPanel2.add(jLabel6, null);
        pnlTitle.add(jLabel7, null);
        pnlTitle.add(txtCantPresentacion, null);
        pnlTitle.add(jPanel2, null);
        pnlTitle.add(jPanel1, null);
        jScrollPane2.getViewport().add(tblLoteProd, null);
        pnlTitle.add(jScrollPane2, null);
        pnlTitle.add(jScrollPane1, null);
        pnlTitle.add(txtPorcGanancia, null);
        pnlTitle.add(jLabel4, null);
        pnlTitle.add(jLabel3, null);
        pnlTitle.add(cmbMarca, null);
        pnlTitle.add(jLabel2, null);
        pnlTitle.add(cmbEstado, null);
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(cmbFraccionado, null);
        pnlTitle.add(lblTelefono, null);
        pnlTitle.add(txtUnidadEntera, null);
        pnlTitle.add(txtCosto, null);
        pnlTitle.add(lblDireccion, null);
        pnlTitle.add(txtDescripcionProducto, null);
        pnlTitle.add(lblNombre, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        jScrollPane1.getViewport().add(tblListaUnidadProd, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        initTable();
        cargarComboMarca();
        cargarComboEstado();
        cargarComboFraccionado();
        
        if(pUpdate){
            //
            loadDatosProducto();
        }
        else{
            txtPorcGanancia.setText("0");
        }
    }
    
    private void initTable(){
        modelUnidadPrecio =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaUnidadPrecio, ConstantsMantenimiento.defaultValuesListaUnidadPrecio,
                                    0);
        FarmaUtility.initSimpleList(tblListaUnidadProd, modelUnidadPrecio, ConstantsMantenimiento.columnsListaUnidadPrecio);
        //cargaListaUnidadPrecio();
        
        modelLoteProd =
                new FarmaTableModel(ConstantsMantenimiento.columnsListaLoteProd, ConstantsMantenimiento.defaultValuesListaLoteProd,
                                    0);
        FarmaUtility.initSimpleList(tblLoteProd, modelLoteProd, ConstantsMantenimiento.columnsListaLoteProd);
        //cargaListaLoteProducto();
    }
    
    private void cargarComboMarca(){
        ArrayList vLista =  new ArrayList();
        vLista.add(FarmaVariables.vCodGrupoCia);
        vLista.add(FarmaVariables.vCodLocal);
        
        FarmaLoadCVL.loadCVLFromSP(cmbMarca, "LIST_MARCA", "PKG_ADM_PRODUCTOS_DOS.LISTA_MARCA(?,?)",vLista, true,true);  
    }
    
    private void cargarComboEstado(){
        ArrayList vLista =  new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbEstado, "LIST_ESTADO", "PKG_ADM_PRODUCTOS_DOS.LISTA_COMBO_ESTADO",vLista, false);  
    }
    
    private void cargarComboFraccionado(){
        ArrayList vLista =  new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbFraccionado, "LIST_FRACCIONADO", "PKG_ADM_PRODUCTOS_DOS.LISTA_COMBO_FRACCIONADO",vLista, false);  
    }
    
    private void cargaListaUnidadPrecio() {
        try {
            DBMantenimiento.getListaUnidadPrecio(modelUnidadPrecio, "");
            
            /*if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
            log.debug("Se cargo la lista de productos");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los productos. \n " + e.getMessage(),
                                     tblListaUnidadProd);
        }
    }       
    
    private void cargaListaLoteProducto() {
        try {
            DBMantenimiento.getListaLoteProducto(modelLoteProd, "");
            
            /*if (tblListaLaboratorios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaLaboratorios, modelProducto, 1, "asc");*/
            log.debug("Se cargo la lista de productos");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los productos. \n " + e.getMessage(),
                                     tblLoteProd);
        }
    }    

    private void this_windowClosing(WindowEvent e) {
        cerrarVentana(true);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaOpcion();
        FarmaUtility.moveFocus(txtDescripcionProducto);
    }

    private void cargaOpcion() {
        if (pUpdate) {
            loadData(pId);
        } else {
            if (pInsert) {
                txtDescripcionProducto.setText("");
            }
        }
    }

    private void loadData(String pIdValor) {
        try {
            ArrayList vDatos = new ArrayList();
            DBMantenimiento.getLabId(vDatos, pIdValor);
            if (vDatos.size() == 1) {
                // se puede mostrar los datos
                /*lblTipoActual.setText("Codigo / Nombre Laboratorio :" +
                                      ((ArrayList)(vDatos.get(0))).get(0).toString() + " - " +
                                      ((ArrayList)(vDatos.get(0))).get(1).toString());*/
                txtDescripcionProducto.setText(((ArrayList)(vDatos.get(0))).get(1).toString());
                txtCosto.setText(((ArrayList)(vDatos.get(0))).get(2).toString());
                txtUnidadEntera.setText(((ArrayList)(vDatos.get(0))).get(3).toString());
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            evento_f11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void evento_f11() {
        grabar_modificar();
    }

    private void grabar_modificar() {

        String pDescripcion = txtDescripcionProducto.getText().trim();
        String pCosto = txtCosto.getText().trim();
        String pUnidadEntera = txtUnidadEntera.getText().trim();
        String codMarca = FarmaLoadCVL.getCVLCode("LIST_MARCA", cmbMarca.getSelectedIndex()).toString().trim();
        String codEstado = FarmaLoadCVL.getCVLCode("LIST_ESTADO", cmbEstado.getSelectedIndex()).toString().trim();
        String codFraccionado = FarmaLoadCVL.getCVLCode("LIST_FRACCIONADO", cmbFraccionado.getSelectedIndex()).toString().trim();
        String ganancia = txtPorcGanancia.getText().trim();
        String cantPresentacion = "1";
        if(codFraccionado.equals("S")){
            cantPresentacion = txtCantPresentacion.getText().trim();
        }        

        try {
            
            String pCodProd = "";
                
            if(pUpdate)    {
                pCodProd = pCodProd_in;
                pCodProd = DBMantenimiento.grabaProducto(pDescripcion, pCosto, pUnidadEntera, codMarca, codEstado, codFraccionado, ganancia, pInsert, pUpdate,pCodProd,cantPresentacion);
            }
            else
               pCodProd =  DBMantenimiento.grabaProducto(pDescripcion, pCosto, pUnidadEntera, codMarca, codEstado, codFraccionado, ganancia, pInsert, pUpdate,pCodProd,cantPresentacion);
            
            
                
            for(int i=0;i<modelUnidadPrecio.data.size();i++){
                DBMantenimiento.grabaUnidadProd(
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,0),
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,1),
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,2),
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,3),
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,4),
                            FarmaUtility.getValueFieldArrayList(modelUnidadPrecio.data, i,5),
                            pInsert, pUpdate,pCodProd);
            }
            
            for(int i=0;i<modelLoteProd.data.size();i++){
                DBMantenimiento.grabaLoteProd(
                            FarmaUtility.getValueFieldArrayList(modelLoteProd.data, i,0),
                            FarmaUtility.getValueFieldArrayList(modelLoteProd.data, i,1),
                            pInsert, pUpdate,pCodProd);
            }
                
            
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se grabó con exitosamente", null);
            cerrarVentana(true);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            FarmaUtility.liberarTransaccion();
            sqle.printStackTrace();
            log.info(sqle.getMessage());
            FarmaUtility.showMessage(this, "Ocurrió un error al hacer el ejecutar el proceso.\n" +
                    sqle.getMessage(), null);
        }
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void txtNombreLaboratorio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtUnidadEntera);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtPorcGanancia);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(cmbMarca);
        }else{
            chkKeyPressed(e);
        }
    }

    private void btnAñadirUnidad_mouseClicked(MouseEvent e) {
        
    }

    private void btnAñadirLote_mouseClicked(MouseEvent e) {
        
    }

    private void loadDatosProducto() {
        String pCodProd;
        
        String pDatosProducto = "";
        ArrayList vListaUnidad = new ArrayList();
        ArrayList vLoteProd = new ArrayList();


        try {
            pDatosProducto = DBMantenimiento.getProducto(pCodProd_in);
            
            DBMantenimiento.getArrayUnidadProducto(vListaUnidad,pCodProd_in);
            DBMantenimiento.getArrayLoteProducto(vLoteProd,pCodProd_in);
            
            txtDescripcionProducto.setText(pDatosProducto.split("@")[0].toString());
            txtCosto.setText(pDatosProducto.split("@")[1].toString());
            txtUnidadEntera.setText(pDatosProducto.split("@")[2].toString());
            FarmaLoadCVL.setSelectedValueInComboBox(cmbMarca, "LIST_MARCA", pDatosProducto.split("@")[3].toString());
            FarmaLoadCVL.setSelectedValueInComboBox(cmbEstado, "LIST_ESTADO", pDatosProducto.split("@")[4].toString());
            FarmaLoadCVL.setSelectedValueInComboBox(cmbFraccionado, "LIST_FRACCIONADO", pDatosProducto.split("@")[5].toString());        
            txtPorcGanancia.setText(pDatosProducto.split("@")[6].toString());
            txtCantPresentacion.setText(pDatosProducto.split("@")[7].toString());        
            
            for(int i=0;i<vLoteProd.size();i++){
                modelLoteProd.data.add(vLoteProd.get(i));
                modelLoteProd.fireTableDataChanged();
                tblLoteProd.repaint();
            }
            
            for(int i=0;i<vListaUnidad.size();i++){
                modelUnidadPrecio.data.add(vListaUnidad.get(i));
                modelUnidadPrecio.fireTableDataChanged();
                tblListaUnidadProd.repaint();
            }
        } catch (SQLException e) {
            log.info("Error: " + e.getMessage());
        }
        
    }

    private void btnAñadirUnidad_actionPerformed(ActionEvent e) {
        if(permiteAgregar()){
        
            int vCantidad = Integer.parseInt(txtCantPresentacion.getText().trim());
            double  vCosto = Double.parseDouble(txtCosto.getText().trim());
        
            
            DlgMantProductoUnidad dlgMantProductoUnidad 
                = new DlgMantProductoUnidad(MyParentFrame, "", true, "I", "",
                                            vCantidad,vCosto
                                            );
            dlgMantProductoUnidad.setVisible(true);
            if(FarmaVariables.vAceptar){
                //listaUnidad.add(VariablesMantenimiento.datos);
                modelUnidadPrecio.data.add(new ArrayList((ArrayList)(dlgMantProductoUnidad.getVDatoModificar().clone())));
                modelUnidadPrecio.fireTableDataChanged();
                tblListaUnidadProd.repaint();
            }    
        }
        else{
            FarmaUtility.showMessage(this, "Debe de ingresar el costo y cantidad presentacion del producto", txtCosto);
        }
    }
    
    public boolean permiteAgregar(){
        double vCosto;
        int vCantidad;
        
        try {
            vCantidad = Integer.parseInt(txtCantPresentacion.getText().trim());
            vCosto = Double.parseDouble(txtCosto.getText().trim());
            return true;
        } catch (Exception nfe) {
            // TODO: Add catch code
            nfe.printStackTrace();
            return false;
        }
    }

    private void btnAñadirLote_actionPerformed(ActionEvent e) {
        
        DlgMantProductoLote dlgMantProductoLote = new DlgMantProductoLote(MyParentFrame, "", true, "I", "");
        dlgMantProductoLote.setVisible(true);
        if(FarmaVariables.vAceptar){
            //listaLote.add();
            //(ArrayList)(VariablesMantenimiento.datos.clone())
            modelLoteProd.data.add(new ArrayList((ArrayList)(dlgMantProductoLote.getVDatosLote().clone())));
            modelLoteProd.fireTableDataChanged();
            tblLoteProd.repaint();
        }
    }

    private void btnEliminarUnidad_actionPerformed(ActionEvent e) {
        int pos = tblListaUnidadProd.getSelectedRow();
        if(pos>=0){
            modelUnidadPrecio.data.remove(pos);
            modelUnidadPrecio.fireTableDataChanged();
            tblListaUnidadProd.repaint();
        }
    }

    private void btnEliminarLote_actionPerformed(ActionEvent e) {
        int pos = tblLoteProd.getSelectedRow();
        if(pos>=0){
            modelLoteProd.data.remove(pos);
            modelLoteProd.fireTableDataChanged();
            tblLoteProd.repaint();
        }
    }

    private void cmbMarca_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbEstado);
        else    
        chkKeyPressed(e);
    }

    private void cmbEstado_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbFraccionado);
        else    
        chkKeyPressed(e);
    }

    private void cmbFraccionado_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtCosto);
        else    
        chkKeyPressed(e);
    }

    private void txtPorcGanancia_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtCantPresentacion);
        else            
        chkKeyPressed(e);
    }

    private void txtCantPresentacion_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtDescripcionProducto);
        else   
            chkKeyPressed(e);
    }

    private void tblListaUnidadProd_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblLoteProd_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnAñadirUnidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnAñadirLote_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtCosto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDecimales(e,txtCosto,4, this);
    }

    private void txtPorcGanancia_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDecimales(e,txtPorcGanancia, 3, this);
    }

    private void txtCantPresentacion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e,txtCantPresentacion, 4, this);
    }

    private void tblListaUnidadProd_mouseClicked(MouseEvent e) {
        int i= tblListaUnidadProd.getSelectedRow();
        if (e.isMetaDown() ){
            // click derecho
            //muestraAjusteKardex();
        }
        else{
        if (e.getClickCount() == 2 && !e.isConsumed()) {
             e.consume();
             int vFoco = tblListaUnidadProd.getSelectedRow();
             
             //txtFiltroCategoria.setText(""+vFoco);
            if (tblListaUnidadProd.getSelectedRow() >= 0)
            {  
                ArrayList vDato = (ArrayList)modelUnidadPrecio.data.get(i);
                 int vCantidad = Integer.parseInt(txtCantPresentacion.getText().trim());
                 double  vCosto = Double.parseDouble(txtCosto.getText().trim());
                 
                 
                 if(vCantidad >= 1){
                     DlgMantProductoUnidad dlgMantProductoUnidad 
                          = new DlgMantProductoUnidad(MyParentFrame, "", true, "U", vDato,vCantidad,vCosto);
                      dlgMantProductoUnidad.setVisible(true);
                      if(FarmaVariables.vAceptar){
                          modelUnidadPrecio.data.set(i,(ArrayList)dlgMantProductoUnidad.getVDatoModificar().clone());
                          modelUnidadPrecio.fireTableDataChanged();
                          tblListaUnidadProd.repaint();
                      }         
                 }
                 else
                     FarmaUtility.showMessage(this, "Debe ingresar la cantidad de producto.", txtCantPresentacion);
                 
                
                
                FarmaUtility.moveFocus(txtDescripcionProducto);
             }
             //System.out.println("- "+vFoco);
        }
        }
    }
}
