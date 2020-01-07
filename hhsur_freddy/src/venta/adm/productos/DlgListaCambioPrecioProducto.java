package venta.adm.productos;


import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelOrange;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;

import venta.adm.productos.reference.ConstantsMaestrosProductos;

import venta.adm.productos.reference.DBMaestrosProductos;

import common.DlgLogin;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.DlgFiltroProductos;
import venta.inventario.DlgAjusteKardex;
import venta.inventario.DlgMovimientoKardex;
import venta.inventario.reference.ConstantsInventario;
import venta.inventario.reference.DBInventario;
import venta.inventario.reference.VariablesInventario;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.reference.VariablesPtoVenta;
import venta.modulo_venta.DlgStockLocales;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaCambioPrecioProducto extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaCambioPrecioProducto.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtPrecioFraccion = new JTextFieldSanSerif();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaHistPreciosProductos = new JTable();
    private JPanelTitle pnlTitle2 = new JPanelTitle();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction jLabelFunction5 = new JLabelFunction();
    private JLabelFunction jLabelFunction6 = new JLabelFunction();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();


    private JLabelWhite lblTotalRegistros_T = new JLabelWhite();

    private JLabelWhite lblTotalRegistros = new JLabelWhite();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JLabel lblCodProd = new JLabel();
    private JLabel lblDescProd = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel lblLaboratrio = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel lblUnidEntero = new JLabel();
    private JLabel lblUnidFraccion = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel lblValFracc = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JTextField txtPrecioEntero = new JTextField();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel lblActualPrecFrac = new JLabel();
    private JLabel lblActualPrecEntero = new JLabel();
    private String pCodProd = "";
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaCambioPrecioProducto() {
        this(null, "", false, "");
    }

    public DlgListaCambioPrecioProducto(Frame parent, String title, boolean modal, String pCodProd) {
        super(parent, title, modal);
        myParentFrame = parent;
        setPCodProd(pCodProd);
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(717, 563));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Cambio de Precio ");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 690, 205));
        pnlTitle1.setBounds(new Rectangle(5, 225, 695, 30));
        btnBuscar.setText("Nuevo Precio Fracci\u00f3n S/ :");
        btnBuscar.setBounds(new Rectangle(10, 110, 170, 30));
        btnBuscar.setMnemonic('B');
        btnBuscar.setFont(new Font("SansSerif", 1, 13));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtPrecioFraccion.setBounds(new Rectangle(185, 115, 205, 20));
        txtPrecioFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtBuscar_keyPressed(e);
                }

            public void keyReleased(KeyEvent e) {
                    txtBuscar_keyReleased(e);
                }
        });
        txtPrecioFraccion.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtPrecioFraccion_focusLost(e);
                }
            });
        scrListaProductos.setBounds(new Rectangle(5, 255, 695, 220));
        tblListaHistPreciosProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
        pnlTitle2.setBounds(new Rectangle(5, 475, 695, 15));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(610, 495, 90, 20));
        lblF1.setText("[ F11 ] Grabar Nuevo Precio S/");
        lblF1.setBounds(new Rectangle(5, 500, 175, 20));
        jLabelFunction5.setText("jLabelFunction1");
        jLabelFunction5.setBounds(new Rectangle(20, 325, 100, 20));
        jLabelFunction6.setText("jLabelFunction1");
        jLabelFunction6.setBounds(new Rectangle(110, 375, 100, 20));
        btnRelacionProductos.setText("Hist\u00f3rico Cambios de Precios");
        btnRelacionProductos.setBounds(new Rectangle(5, 10, 205, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblTotalRegistros_T.setText("Total de Registros:");
        lblTotalRegistros_T.setBounds(new Rectangle(15, 5, 115, 20));
        lblTotalRegistros_T.setVisible(false);
        lblTotalRegistros.setText("100");
        lblTotalRegistros.setBounds(new Rectangle(150, 5, 70, 20));
        lblTotalRegistros.setVisible(false);
        lblF8.setText("[ F8 ] Excedente");
        lblF8.setBounds(new Rectangle(450, 390, 105, 20));
        lblF9.setText("[ F9 ] Excesos");
        lblF9.setBounds(new Rectangle(570, 390, 100, 20));
        lblF4.setBounds(new Rectangle(195, 400, 145, 20));
        lblF4.setText("[ F4 ] Ajuste Diferencias");
        lblF12.setText("[ F12 ]  Stock Locales");
        lblF12.setBounds(new Rectangle(295, 390, 135, 20));
        lblCodProd.setText("001254");
        lblCodProd.setBounds(new Rectangle(10, 5, 45, 20));
        lblCodProd.setForeground(SystemColor.window);
        lblCodProd.setFont(new Font("SansSerif", 1, 13));
        lblDescProd.setText("Apronax");
        lblDescProd.setBounds(new Rectangle(80, 5, 420, 20));
        lblDescProd.setForeground(SystemColor.window);
        lblDescProd.setFont(new Font("SansSerif", 1, 13));
        jLabel3.setText("Laboratorio : ");
        jLabel3.setBounds(new Rectangle(10, 30, 105, 15));
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setFont(new Font("SansSerif", 1, 13));
        lblLaboratrio.setText("PepeSolucion");
        lblLaboratrio.setBounds(new Rectangle(130, 30, 420, 15));
        lblLaboratrio.setForeground(SystemColor.window);
        lblLaboratrio.setFont(new Font("SansSerif", 1, 13));
        jLabel5.setText("Unidad Entero :");
        jLabel5.setBounds(new Rectangle(10, 55, 105, 15));
        jLabel5.setForeground(SystemColor.window);
        jLabel5.setFont(new Font("SansSerif", 1, 13));
        jLabel6.setText("Unidad Fracci\u00f3n : ");
        jLabel6.setBounds(new Rectangle(10, 80, 125, 15));
        jLabel6.setForeground(SystemColor.window);
        jLabel6.setFont(new Font("SansSerif", 1, 13));
        lblUnidEntero.setText("Caja x 30 Comprimidos");
        lblUnidEntero.setBounds(new Rectangle(145, 55, 455, 15));
        lblUnidEntero.setForeground(SystemColor.window);
        lblUnidEntero.setFont(new Font("SansSerif", 1, 13));
        lblUnidFraccion.setText("Comprimido");
        lblUnidFraccion.setBounds(new Rectangle(145, 80, 205, 15));
        lblUnidFraccion.setForeground(SystemColor.window);
        lblUnidFraccion.setFont(new Font("SansSerif", 1, 13));
        jLabel9.setText("Valor Fracci\u00f3n  :");
        jLabel9.setBounds(new Rectangle(335, 85, 145, 15));
        jLabel9.setForeground(SystemColor.window);
        jLabel9.setFont(new Font("SansSerif", 1, 13));
        lblValFracc.setText("30");
        lblValFracc.setBounds(new Rectangle(460, 85, 60, 15));
        lblValFracc.setForeground(SystemColor.window);
        lblValFracc.setFont(new Font("SansSerif", 1, 13));
        jLabel11.setText("Nuevo Precio Entero S/ : ");
        jLabel11.setBounds(new Rectangle(10, 155, 170, 15));
        jLabel11.setForeground(SystemColor.window);
        jLabel11.setFont(new Font("SansSerif", 1, 13));
        txtPrecioEntero.setBounds(new Rectangle(185, 155, 205, 20));
        txtPrecioEntero.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtPrecioEntero_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtPrecioEntero_keyPressed(e);
                }
            });
        jLabel12.setText("Precio Fracci\u00f3n Actual : ");
        jLabel12.setBounds(new Rectangle(415, 115, 185, 20));
        jLabel12.setForeground(SystemColor.window);
        jLabel12.setFont(new Font("SansSerif", 1, 13));
        jLabel13.setText("Precio Entero Actual : ");
        jLabel13.setBounds(new Rectangle(415, 155, 145, 20));
        jLabel13.setForeground(SystemColor.window);
        jLabel13.setFont(new Font("SansSerif", 1, 13));
        lblActualPrecFrac.setText("S/ 25.65");
        lblActualPrecFrac.setBounds(new Rectangle(575, 120, 85, 15));
        lblActualPrecFrac.setForeground(SystemColor.window);
        lblActualPrecFrac.setFont(new Font("SansSerif", 1, 13));
        lblActualPrecEntero.setText("S/ 769.5");
        lblActualPrecEntero.setBounds(new Rectangle(575, 160, 95, 15));
        lblActualPrecEntero.setForeground(SystemColor.window);
        lblActualPrecEntero.setFont(new Font("SansSerif", 1, 13));
        lblF1.add(jLabelFunction6, null);
        jContentPane.add(lblF1, null);
        lblEsc.add(jLabelFunction5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle2, null);
        scrListaProductos.getViewport().add(tblListaHistPreciosProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        pnlTitle2.add(lblTotalRegistros, null);
        pnlTitle2.add(lblTotalRegistros_T, null);
        pnlHeader1.add(lblActualPrecEntero, null);
        pnlHeader1.add(lblActualPrecFrac, null);
        pnlHeader1.add(jLabel13, null);
        pnlHeader1.add(jLabel12, null);
        pnlHeader1.add(txtPrecioEntero, null);
        pnlHeader1.add(jLabel11, null);
        pnlHeader1.add(lblValFracc, null);
        pnlHeader1.add(jLabel9, null);
        pnlHeader1.add(lblUnidFraccion, null);
        pnlHeader1.add(lblUnidEntero, null);
        pnlHeader1.add(jLabel6, null);
        pnlHeader1.add(jLabel5, null);
        pnlHeader1.add(lblLaboratrio, null);
        pnlHeader1.add(jLabel3, null);
        pnlHeader1.add(lblDescProd, null);
        pnlHeader1.add(lblCodProd, null);
        pnlHeader1.add(txtPrecioFraccion, null);
        pnlHeader1.add(btnBuscar, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        cargaDatosProducto();
        tableModel =
                new FarmaTableModel(ConstantsMaestrosProductos.columnsListaHistPrecios, ConstantsMaestrosProductos.defaultValuesListaHistPrecios,
                                    0);
        FarmaUtility.initSimpleList(tblListaHistPreciosProductos, tableModel, ConstantsMaestrosProductos.columnsListaHistPrecios);
        cargaListaHistPrecios();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaHistPreciosProductos);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtPrecioFraccion);
        txtPrecioEntero.setEnabled(false);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPrecioFraccion);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaHistPreciosProductos, txtPrecioFraccion, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if(isNumero(txtPrecioFraccion.getText().trim()))
                {
            double vPrecio = Double.parseDouble(txtPrecioFraccion.getText().trim());
            txtPrecioEntero.setText(""+FarmaUtility.getDecimalNumberRedondeado(vPrecio*(Integer.parseInt(lblValFracc.getText().trim()))));
            }    
        }
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaHistPreciosProductos, txtPrecioFraccion, 1);
        if(isNumero(txtPrecioFraccion.getText().trim()))
            {

                double vPrecio = Double.parseDouble(txtPrecioFraccion.getText().trim());
                txtPrecioEntero.setText(""+FarmaUtility.getDecimalNumberRedondeado(vPrecio*(Integer.parseInt(lblValFracc.getText().trim()))));
                 
            }
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            cambiaPrecio();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                          "¿Esta seguro de salir y no hacer cambios?"))
            cerrarVentana(false);
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaHistPrecios() {
        try {
            DBMaestrosProductos.getListaHistPrecios(tableModel,lblCodProd.getText().trim());
            if (tblListaHistPreciosProductos.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaHistPreciosProductos, tableModel, 4, FarmaConstants.ORDEN_DESCENDENTE);

            tblListaHistPreciosProductos.getTableHeader().setReorderingAllowed(false);
            tblListaHistPreciosProductos.getTableHeader().setResizingAllowed(false);


            log.debug("se cargo la lista de prods");
        } catch (SQLException sql) {
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), txtPrecioFraccion);
        }
        lblTotalRegistros.setText("" + tblListaHistPreciosProductos.getRowCount());
    }

    private void mostrarDatosFiltro() {
    }


    private void cargaDatosProducto() {
        try {

            lblCodProd.setText("");
            lblDescProd.setText("");
            lblLaboratrio.setText("");
            lblUnidEntero.setText("");
            lblUnidFraccion.setText("");
            lblActualPrecFrac.setText("");
            lblActualPrecEntero.setText("");
            lblValFracc.setText("");
            
            String[] vCadena = DBMaestrosProductos.getDatoProducto(getPCodProd()).split("@");
            if(vCadena.length>5){

                lblCodProd.setText(getPCodProd());
                lblDescProd.setText(vCadena[1]);
                lblLaboratrio.setText(vCadena[2]);
                lblUnidEntero.setText(vCadena[3]);
                lblUnidFraccion.setText(vCadena[4]);
                lblValFracc.setText(vCadena[5]);
                lblActualPrecFrac.setText(vCadena[6]);
                lblActualPrecEntero.setText(vCadena[7]);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public void setPCodProd(String pCodProd) {
        this.pCodProd = pCodProd;
    }

    public String getPCodProd() {
        return pCodProd;
    }

    private void txtPrecioEntero_keyReleased(KeyEvent e) {
    }

    private void txtPrecioEntero_keyPressed(KeyEvent e) {
       
    }

    private void cambiaPrecio() {
        String pPrecioFracc  = txtPrecioFraccion.getText().trim();
        String pPrecioEntero = txtPrecioEntero.getText().trim();
        if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                             "Se va cambiar el precio a :\n" +
            "Precio Venta Fracción : " + pPrecioFracc +"\n"+
            "Precio Venta Entero   : " + pPrecioEntero +"\n"+
            "¿Esta seguro de efectuar el cambio?"))
        {
            try {
                DBMaestrosProductos.cambiaPrecioProductoCadena(getPCodProd(), Double.parseDouble(pPrecioFracc.trim()));
                FarmaUtility.aceptarTransaccion();
                cargaListaHistPrecios();
                FarmaUtility.showMessage(this, "Se cambiaron los precios en todos los sitemas.", txtPrecioEntero);
            } catch (Exception sqle) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Ocurrió un error al querer cambiar el precio en todos los sistemas.\n"+
                                               sqle.getMessage(), txtPrecioEntero);
                sqle.printStackTrace();
            } 
        }
    }

    private void txtPrecioFraccion_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtPrecioFraccion);
    }

    private boolean isNumero(String pCadena) {
        try {
            Double pValor = Double.parseDouble(pCadena.trim());
            return true;
        } catch (Exception nfe) {
            //nfe.printStackTrace();
            return false;
        }
    }
}
