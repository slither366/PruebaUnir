package venta.hospital.soat;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JLabelWhite;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JPanel;
import javax.swing.JTextField;

import common.FarmaConstants;
import common.FarmaLoadCVL;
import common.FarmaSearch;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.administracion.impresoras.reference.DBImpresoras;
import venta.administracion.impresoras.reference.VariablesImpresoras;
import venta.reference.ConstantsPtoVenta;
import venta.reference.UtilityPtoVenta;
import venta.modulo_venta.reference.ConstantsModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgNuevaAtencion extends JDialog
{
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgNuevaAtencion.class);
    private boolean existenDatos;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlDatosImpresora = new JPanelTitle();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelWhite lblNroImpresora_T = new JLabelWhite();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JButtonLabel btnDescImpresora = new JButtonLabel();
    private JTextFieldSanSerif txtnombres = new JTextFieldSanSerif();
    private JComboBox cmbSeguro = new JComboBox();
    private JTextFieldSanSerif txtCobertura = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCantidadDias = new JTextFieldSanSerif();
    private JButtonLabel btnColaImp = new JButtonLabel();
    private JButtonLabel btnComprobante = new JButtonLabel();
    private JButtonLabel btnSerie = new JButtonLabel();
    private JButtonLabel btnNroComp = new JButtonLabel();
    private JButtonLabel btnModelo = new JButtonLabel();
    private JTextFieldSanSerif txtvalidacion = new JTextFieldSanSerif();
    private JButtonLabel btnNSerie = new JButtonLabel();
    private JButton btnGrabar = new JButton();
    private JButton btnLimpiar = new JButton();
    private JTextField txtDesde = new JTextField();
    private JTextField txtHasta = new JTextField();
    private JPanel jPanel1 = new JPanel();
    private Checkbox ckCartaGarantia = new Checkbox();
    private Checkbox ckDenuncia = new Checkbox();
    private Checkbox ckCopiaDNI = new Checkbox();
    private Checkbox ckCupoAtencion = new Checkbox();
    private Checkbox ckSoatCopia = new Checkbox();
    private Label lblCantidadDoc = new Label();

    private boolean vVisualizar =false;
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgNuevaAtencion()
    {   this(null, "", false);
    }

    public DlgNuevaAtencion(Frame parent, String title, boolean modal)
    {   super(parent, title, modal);
        myParentFrame = parent;
        try
        {   jbInit();
            initialize();
        }
        catch (Exception e)
        {   log.error("",e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************
    
    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(721, 395));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Registro Atenci\u00f3n - Validaci\u00f3n de Documentos");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jContentPane.setLayout(null);
        jContentPane.setFocusable(false);
        pnlDatosImpresora.setBounds(new Rectangle(5, 10, 695, 310));
        pnlDatosImpresora.setFocusable(false);
        lblSalir.setBounds(new Rectangle(515, 335, 105, 20));
        lblSalir.setText("[Esc] Salir");
        lblSalir.setFocusable(false);
        lblNroImpresora_T.setText("<html><center>DNI o Carnet Extranjeria:</html><c/enter>");
        lblNroImpresora_T.setBounds(new Rectangle(10, 10, 100, 30));
        lblNroImpresora_T.setFocusable(false);
        txtDni.setBounds(new Rectangle(105, 15, 160, 20));
        txtDni.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDni_keyPressed(e);
                }
            });
        btnDescImpresora.setText("Nombres :");
        btnDescImpresora.setBounds(new Rectangle(35, 45, 60, 20));
        btnDescImpresora.setBorder(BorderFactory.createLineBorder(Color.black,
                    1));
        btnDescImpresora.setMnemonic('d');
        btnDescImpresora.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDescImpresora_actionPerformed(e);
            }
        });
        txtnombres.setBounds(new Rectangle(105, 45, 245, 20));
        cmbSeguro.setBounds(new Rectangle(105, 80, 245, 20));
        cmbSeguro.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbSeguro_keyPressed(e);
                }
            });
        txtCobertura.setBounds(new Rectangle(105, 115, 155, 20));
        txtCantidadDias.setBounds(new Rectangle(105, 185, 105, 20));
        btnColaImp.setText("Vigencia :");
        btnColaImp.setBounds(new Rectangle(30, 145, 70, 20));
        btnColaImp.setMnemonic('i');
        btnColaImp.setFocusable(false);
        btnColaImp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnColaImp_actionPerformed(e);
            }
        });
        btnComprobante.setText("Seguradora :");
        btnComprobante.setBounds(new Rectangle(15, 80, 85, 20));
        btnComprobante.setMnemonic('c');
        btnComprobante.setFocusable(false);
        btnComprobante.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnComprobante_actionPerformed(e);
            }
        });
        btnSerie.setText("Cobertura: S/ ");
        btnSerie.setBounds(new Rectangle(20, 115, 80, 20));
        btnSerie.setFocusable(false);
        btnNroComp.setText("Cantidad d\u00edas:");
        btnNroComp.setBounds(new Rectangle(15, 180, 105, 20));
        btnNroComp.setMnemonic('n');
        btnNroComp.setFocusable(false);
        btnNroComp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNroComp_actionPerformed(e);
            }
        });
        btnModelo.setText("Hasta:");
        btnModelo.setBounds(new Rectangle(215, 145, 50, 20));
        btnModelo.setFocusable(false);
        txtvalidacion.setBounds(new Rectangle(15, 245, 345, 20));
        txtvalidacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtvalidacion_keyPressed(e);
                }
            });
        btnNSerie.setText("Se Valid\u00f3 con :");
        btnNSerie.setBounds(new Rectangle(15, 220, 125, 20));
        btnNSerie.setMnemonic('e');
        btnNSerie.setFocusable(false);
        btnNSerie.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNSerie_actionPerformed(e);
            }
        });
        btnGrabar.setText("Grabar");
        btnGrabar.setBounds(new Rectangle(5, 330, 90, 20));
        btnGrabar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnGrabar_actionPerformed(e);
                }
            });
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBounds(new Rectangle(155, 330, 100, 20));
        btnLimpiar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnLimpiar_actionPerformed(e);
                }
            });
        txtDesde.setBounds(new Rectangle(105, 145, 105, 20));
        txtDesde.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDesde_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtDesde_keyReleased(e);
                }
            });
        txtHasta.setBounds(new Rectangle(255, 145, 105, 20));
        txtHasta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtHasta_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtHasta_keyReleased(e);
                }
            });
        jPanel1.setBounds(new Rectangle(400, 10, 285, 290));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createTitledBorder("Seleccione los Documentos Presentados"));
        ckCartaGarantia.setLabel("Carta Garant\u00eda (Original y Copia)");
        ckCartaGarantia.setBounds(new Rectangle(25, 35, 200, 20));
        ckCartaGarantia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCartaGarantia_itemStateChanged(e);
                }
            });
        ckDenuncia.setLabel("Denuncia Policial");
        ckDenuncia.setBounds(new Rectangle(25, 75, 145, 20));
        ckDenuncia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckDenuncia_itemStateChanged(e);
                }
            });
        ckCopiaDNI.setLabel("Copia DNI");
        ckCopiaDNI.setBounds(new Rectangle(25, 115, 145, 20));
        ckCopiaDNI.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCopiaDNI_itemStateChanged(e);
                }
            });
        ckCupoAtencion.setLabel("Cupo de Atenci\u00f3n");
        ckCupoAtencion.setBounds(new Rectangle(25, 155, 140, 20));
        ckCupoAtencion.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckCupoAtencion_itemStateChanged(e);
                }
            });
        ckSoatCopia.setLabel("SOAT  (Copia)");
        ckSoatCopia.setBounds(new Rectangle(25, 200, 145, 20));
        ckSoatCopia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    ckSoatCopia_itemStateChanged(e);
                }
            });
        lblCantidadDoc.setText("Se Presentaron :   10  Documentos.");
        lblCantidadDoc.setBounds(new Rectangle(15, 225, 255, 55));
        lblCantidadDoc.setFont(new Font("Arial", 1, 15));
        lblCantidadDoc.setBackground(SystemColor.window);
        lblCantidadDoc.setForeground(new Color(255, 33, 33));
        jContentPane.add(btnLimpiar, null);
        jContentPane.add(btnGrabar, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(pnlDatosImpresora, null);
        jPanel1.add(lblCantidadDoc, null);
        jPanel1.add(ckSoatCopia, null);
        jPanel1.add(ckCupoAtencion, null);
        jPanel1.add(ckCopiaDNI, null);
        jPanel1.add(ckDenuncia, null);
        jPanel1.add(ckCartaGarantia, null);
        pnlDatosImpresora.add(jPanel1, null);
        pnlDatosImpresora.add(txtHasta, null);
        pnlDatosImpresora.add(txtDesde, null);
        pnlDatosImpresora.add(btnNSerie, null);
        pnlDatosImpresora.add(txtvalidacion, null);
        pnlDatosImpresora.add(btnModelo, null);
        pnlDatosImpresora.add(btnNroComp, null);
        pnlDatosImpresora.add(btnSerie, null);
        pnlDatosImpresora.add(btnComprobante, null);
        pnlDatosImpresora.add(btnColaImp, null);
        pnlDatosImpresora.add(txtCantidadDias, null);
        pnlDatosImpresora.add(txtCobertura, null);
        pnlDatosImpresora.add(cmbSeguro, null);
        pnlDatosImpresora.add(txtnombres, null);
        pnlDatosImpresora.add(btnDescImpresora, null);
        pnlDatosImpresora.add(txtDni, null);
        pnlDatosImpresora.add(lblNroImpresora_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //AGREGADO 20/06/2006 ERIOS
        txtnombres.setLengthText(30);
        txtnombres.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtnombres_keyPressed(e);
                }
            });
        txtCobertura.setLengthText(7);
        txtCobertura.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCobertura_keyPressed(e);
                }
            });
        txtCantidadDias.setLengthText(120);
        txtCantidadDias.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCantidadDias_keyPressed(e);
                }
            });
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize()
    {
        FarmaVariables.vAceptar = false;
        initCombos();
        limpiarDatos();
        vRefrescaCantidad();
        
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initCombos()
    {
        initCmbSeguradora();
    }

    private void initCmbSeguradora()
    {
        /*ArrayList parametros2 = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbSeguro,
                                    "cmbAseguradora",
                                    "HH_SOAT.F_GET_CMB_ASEGURADORA", 
                                    parametros2,
                                    false, 
                                    true);*/
        cmbSeguro.addItem("Seleccione");
        cmbSeguro.addItem("Rimac");
        cmbSeguro.addItem("Pacifico");
        cmbSeguro.addItem("Otros");
    }
        
    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
        if(isVVisualizar()){
            cargaDatos();
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtDescImpresora_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(existenDatos)
                FarmaUtility.moveFocus(txtCobertura);
            else
                FarmaUtility.moveFocus(cmbSeguro);
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCantidadDias);
        }
        chkKeyPressed(e);
    }

    private void txtColaImpresion_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }
    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCobertura, e);
    }

    private void btnDescImpresora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnombres);
    }

    private void btnComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSeguro);
    }


    private void btnNroComp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCobertura);
    }

    private void btnColaImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidadDias);
    }

    private void btnNSerie_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtvalidacion);
    }
    
    private void txtSerieImpr_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }
    
    // **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

    private void limpiarDatos() {
            txtDni.setText("");
            txtnombres.setText("");
            txtCobertura.setText("");
            txtCantidadDias.setText("");
            txtDesde.setText("");
            txtHasta.setText("");
            cmbSeguro.setSelectedItem("");
            txtvalidacion.setText("");
            FarmaUtility.moveFocus(txtDni);
            ckCartaGarantia.setState(false);
            ckCopiaDNI.setState(false);
            ckCupoAtencion.setState(false);
            ckSoatCopia.setState(false);
            ckDenuncia.setState(false);
            vRefrescaCantidad();
    }

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtnombres);
        }
        chkKeyPressed(e);
    }

    private void txtnombres_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(cmbSeguro);
        }
        chkKeyPressed(e);
    }

    private void cmbSeguro_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCobertura);
        }
        chkKeyPressed(e);
    }

    private void txtCobertura_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDesde);
        }
        chkKeyPressed(e);
    }

    private void txtDesde_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtHasta);
        }
        chkKeyPressed(e);
    }

    private void txtHasta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtCantidadDias);
        }
        chkKeyPressed(e);
    }

    private void txtCantidadDias_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtvalidacion);
        }
        chkKeyPressed(e);
    }

    private void txtvalidacion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDni);
        }
        chkKeyPressed(e);
    }

    private void ckCartaGarantia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void vRefrescaCantidad() {
        int vCantidad = 0;
        if(ckCartaGarantia.getState())vCantidad ++;
        if(ckDenuncia.getState())vCantidad ++;
        if(ckCopiaDNI.getState())vCantidad ++;
        if(ckCupoAtencion.getState())vCantidad ++;
        if(ckSoatCopia.getState())vCantidad ++;
        lblCantidadDoc.setText("Se Presentaron :   "+vCantidad+  " Documentos.");
    }

    private void ckDenuncia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckCopiaDNI_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckCupoAtencion_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void ckSoatCopia_itemStateChanged(ItemEvent e) {
        vRefrescaCantidad();
    }

    private void btnLimpiar_actionPerformed(ActionEvent e) {
        limpiarDatos();
    }

    private void btnGrabar_actionPerformed(ActionEvent e) {
        grabarDatosAtencion();
    }

    private void grabarDatosAtencion() {
            if (componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,
                            "¿Esta seguro de grabar la atención nueva?"))  {
                    FarmaUtility.showMessage(this, "Se grabó éxito la atención \n" +
                        "Nº de Atención : 000210 ", txtCantidadDias);
                    cerrarVentana(true);
                }
    }

    private void txtDesde_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtDesde,e);
    }

    private void txtHasta_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtHasta,e);
    }

    public void setVVisualizar(boolean vVisualizar) {
        this.vVisualizar = vVisualizar;
    }

    public boolean isVVisualizar() {
        return vVisualizar;
    }

    private void cargaDatos() {
        txtDni.setText("123456789");
        txtnombres.setText("Carlos Acevedo");
        txtCobertura.setText("6500");
        txtCantidadDias.setText("10");
        txtDesde.setText("13/08/2015");
        txtHasta.setText("23/08/2015");
        cmbSeguro.setSelectedItem(2);
        txtvalidacion.setText("Se valido con la Lic. Veronica");
        FarmaUtility.moveFocus(txtDni);
        ckCartaGarantia.setState(true);
        ckCopiaDNI.setState(true);
        ckCupoAtencion.setState(false);
        ckSoatCopia.setState(false);
        ckDenuncia.setState(false);
        vRefrescaCantidad();
    }
}
