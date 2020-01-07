package dental.laboratorio;


import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import dental.laboratorio.reference.DBMantenimiento;
import dental.laboratorio.reference.VariablesMantenimiento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.UtilityPtoVenta;


public class DlgMantProductoUnidad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProductoUnidad.class);
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel lblNombre = new JButtonLabel();
    private JTextFieldSanSerif txtDescripcionPrecio = new JTextFieldSanSerif();
    private JLabelFunction btnF11 = new JLabelFunction();
    private JLabelFunction btnEsc = new JLabelFunction();
    private Frame MyParentFrame;
    private JLabel lblTipoActual = new JLabel();
    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";
    private JButtonLabel lblDireccion = new JButtonLabel();
    private JTextField txtCantidad = new JTextField();
    private JTextField txtPrecioVentaSugerido = new JTextField();
    private JLabel lblTelefono = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtPorcentajeGanancia = new JTextField();


    public int vCantidad;
    public double vCosto;
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JTextField txtPrecioMinimo = new JTextField();
    private JLabel jLabel4 = new JLabel();
    
    private ArrayList vDatoModificar = new ArrayList();
    private JTextField txtPrecioTercero = new JTextField();
    private JLabel jLabel5 = new JLabel();

    public DlgMantProductoUnidad() {
        this(null, "", false, "I", "",0,0);
    }
    
    //(MyParentFrame, "", true, "U", vDato);
    //

    public DlgMantProductoUnidad(Frame parent, String title, boolean modal, String pAccion, ArrayList vDato,int vCantidad,double vCosto) {
        super(parent, title, modal);
        try {
            
            this.vCantidad = vCantidad;
            this.vCosto = vCosto;
            
            pUpdate = true;
            vDatoModificar = vDato;
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgMantProductoUnidad(Frame parent, String title, boolean modal, String pAccion, String pIdUpdate,
                                 int vCantidad,double vCosto) {
        super(parent, title, modal);
        try {
            
            this.vCantidad = vCantidad;
            this.vCosto = vCosto;
            
            if (pAccion.trim().equalsIgnoreCase("I"))
                pInsert = true;
            else if (pAccion.trim().equalsIgnoreCase("U")) {
                this.pId = pIdUpdate;
                pUpdate = true;
            }
            MyParentFrame = parent;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(704, 338));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Mantenimiento Unidad Precio :");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 670, 255));
        pnlTitle.setBackground(Color.white);
        pnlTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle.setFocusable(false);
        txtDescripcionPrecio.setBounds(new Rectangle(215, 55, 130, 20));
        lblNombre.setText("Descripci\u00f3n Unidad:");
        lblNombre.setMnemonic('N');
        lblNombre.setBounds(new Rectangle(5, 50, 120, 15));
        lblNombre.setForeground(new Color(0, 114, 169));
        lblNombre.setFocusable(false);
        txtDescripcionPrecio.setBounds(new Rectangle(135, 45, 245, 20));
        txtDescripcionPrecio.setLengthText(300);


        txtDescripcionPrecio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombreLaboratorio_keyPressed(e);
                }
            });
        btnF11.setBounds(new Rectangle(10, 275, 117, 20));
        btnEsc.setBounds(new Rectangle(290, 275, 117, 19));
        btnF11.setText("[F11] Aceptar");
        btnF11.setFocusable(false);
        btnF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnF11_mouseClicked(e);
            }
        });
        btnEsc.setText("[Esc] Salir");
        btnEsc.setFocusable(false);
        lblTipoActual.setText("Precio Unidad :");
        lblTipoActual.setBounds(new Rectangle(15, 5, 335, 30));
        lblTipoActual.setFont(new Font("SansSerif", 1, 14));
        lblDireccion.setText("Cantidad");
        lblDireccion.setBounds(new Rectangle(20, 80, 105, 20));
        lblDireccion.setForeground(new Color(0, 114, 169));
        lblDireccion.setFont(new Font("SansSerif", 1, 11));
        lblDireccion.setMnemonic('D');
        txtCantidad.setBounds(new Rectangle(135, 80, 245, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDireccion_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtCantidad_keyReleased(e);
                }
            });
        txtPrecioVentaSugerido.setBounds(new Rectangle(135, 150, 245, 20));
        txtPrecioVentaSugerido.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtTelefono_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtPrecioVenta_keyReleased(e);
                }
            });
        lblTelefono.setText("<html><center>Precio Venta Sugerido S/ :</center></html>");
        lblTelefono.setBounds(new Rectangle(40, 145, 85, 35));
        lblTelefono.setFont(new Font("SansSerif", 1, 11));
        lblTelefono.setForeground(new Color(0, 114, 169));
        jLabel1.setText("% Ganancia");
        jLabel1.setBounds(new Rectangle(55, 115, 70, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(0, 107, 165));
        txtPorcentajeGanancia.setBounds(new Rectangle(135, 115, 190, 20));
        txtPorcentajeGanancia.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPorcentajeGanancia_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtPorcentajeGanancia_keyReleased(e);
                }
            });
        jLabel2.setText("<html><center>Precio Minimo Venta S/ :</center></html>");
        jLabel2.setBounds(new Rectangle(35, 195, 95, 30));
        jLabel2.setForeground(new Color(0, 114, 169));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel3.setText("jLabel3");
        txtPrecioMinimo.setBounds(new Rectangle(135, 195, 195, 20));
        txtPrecioMinimo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPrecioMinimo_keyPressed(e);
                }
            });
        jLabel4.setText(".. opcional ..");
        jLabel4.setBounds(new Rectangle(135, 215, 100, 15));
        txtPrecioTercero.setBounds(new Rectangle(445, 185, 195, 30));
        jLabel5.setText("Precio Tercero");
        jLabel5.setBounds(new Rectangle(445, 150, 90, 30));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(new Color(0, 107, 165));
        pnlTitle.add(jLabel5, null);
        pnlTitle.add(txtPrecioTercero, null);
        pnlTitle.add(jLabel4, null);
        pnlTitle.add(txtPrecioMinimo, null);
        pnlTitle.add(jLabel2, null);
        pnlTitle.add(txtPorcentajeGanancia, null);
        pnlTitle.add(jLabel1, null);
        pnlTitle.add(lblTelefono, null);
        pnlTitle.add(txtPrecioVentaSugerido, null);
        pnlTitle.add(txtCantidad, null);
        pnlTitle.add(lblDireccion, null);
        pnlTitle.add(lblTipoActual, null);
        pnlTitle.add(txtDescripcionPrecio, null);
        pnlTitle.add(lblNombre, null);
        jContentPane.add(btnF11, null);
        jContentPane.add(btnEsc, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {

    }

    private void this_windowClosing(WindowEvent e) {
        cerrarVentana(true);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaOpcion();
        FarmaUtility.moveFocus(txtDescripcionPrecio);
    }

    private void cargaOpcion() {
        if (pUpdate) {
            loadData(pId);
        } else {
            if (pInsert) {
                txtDescripcionPrecio.setText("");
            }
        }
    }

    private void loadData(String pIdValor) {
        
        try {
            txtDescripcionPrecio.setText(vDatoModificar.get(0).toString());
            txtCantidad.setText(vDatoModificar.get(1).toString());
            txtPorcentajeGanancia.setText(vDatoModificar.get(2).toString());
            txtPrecioVentaSugerido.setText(vDatoModificar.get(3).toString());
            txtPrecioMinimo.setText(vDatoModificar.get(4).toString());
            txtPrecioTercero.setText(vDatoModificar.get(5).toString());
        } catch (Exception e) {
            // TODO: Add catch code
            //e.printStackTrace();
            FarmaUtility.showMessage(this, "Error al mostrar datos", txtDescripcionPrecio);
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
        grabar();
    }

    private void grabar() {
        
        boolean validacion = validaCampos();
        
        if(validacion){
            String pDescripcionPrecio = txtDescripcionPrecio.getText().trim();
            String pCantidad = txtCantidad.getText().trim();
            String pGanancia = txtPorcentajeGanancia.getText().trim();
            String pPrecioVenta = txtPrecioVentaSugerido.getText().trim();
            String pPrecioTercero = txtPrecioTercero.getText().trim();
            
            if(txtPrecioMinimo.getText().trim().length()==0)
                txtPrecioMinimo.setText(txtPrecioVentaSugerido.getText().trim());
            
            String pPrecioVentaMinimo = txtPrecioMinimo.getText().trim();

            vDatoModificar = new ArrayList();

            try {
                if(pInsert){
                    vDatoModificar.add(pDescripcionPrecio);                    
                    vDatoModificar.add(pCantidad);
                    vDatoModificar.add(pGanancia);
                    vDatoModificar.add(pPrecioVenta);
                    vDatoModificar.add(pPrecioVentaMinimo);
                    vDatoModificar.add(pPrecioTercero);
                    
                    cerrarVentana(true);
                }else if(pUpdate){
                    vDatoModificar.add(pDescripcionPrecio);                    
                    vDatoModificar.add(pCantidad);
                    vDatoModificar.add(pGanancia);
                    vDatoModificar.add(pPrecioVenta);
                    vDatoModificar.add(pPrecioVentaMinimo);
                    vDatoModificar.add(pPrecioTercero);
                    
                    cerrarVentana(true);
                    
                }
                
                
            } catch (Exception sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
                log.info(sqle.getMessage());
                FarmaUtility.showMessage(this, "Ocurrió un error al hacer el ejecutar el proceso.\n" +
                        sqle.getMessage(), null);
                cerrarVentana(false);
            }
        }
        
    }
    
    private boolean validaCampos(){
        String mensaje = "";
        if(txtDescripcionPrecio.getText().trim().length() <= 0){
            mensaje = "Ingresar el campo Descripcion Precio.";
            FarmaUtility.moveFocus(txtDescripcionPrecio);
        }
        if(txtCantidad.getText().trim().length() <= 0){
            if(mensaje.length() > 0){
                mensaje = mensaje + "\nIngresar el campo Cantidad.";
            }else{
                mensaje = "\nIngresar el campo Cantidad.";
            }
            FarmaUtility.moveFocus(txtCantidad);
        }
        if(txtPrecioVentaSugerido.getText().trim().length() <= 0){
            if(mensaje.length() > 0){
                mensaje = mensaje + "\nIngresar el campo Precio de Venta.";
            }else{
                mensaje = "\nIngresar el campo Precio de Venta.";
            }
            FarmaUtility.moveFocus(txtPrecioVentaSugerido);
        }
        
        if(txtPrecioTercero.getText().trim().length() <= 0){
            if(mensaje.length() > 0){
                mensaje = mensaje + "\nIngresar el campo Precio de Tercero.";
            }else{
                mensaje = "\nIngresar el campo Precio de Tercero.";
            }
            FarmaUtility.moveFocus(txtPrecioVentaSugerido);
        }
        if(mensaje.length() > 0){
            JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private void btnF11_mouseClicked(MouseEvent e) {
        evento_f11();
    }

    private void txtNombreLaboratorio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCantidad);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtDireccion_keyPressed(KeyEvent e) {
        
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtPorcentajeGanancia);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtPrecioMinimo);
        }else{
            chkKeyPressed(e);
        }
    }

    private void txtPorcentajeGanancia_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtPrecioVentaSugerido);
        }else{
            chkKeyPressed(e);
        }
    }

    private void calculaPrecioVenta() {
        try {
            int catFrac = Integer.parseInt(txtCantidad.getText().trim());
            double pct = 0;
            try {
                pct = Double.parseDouble(txtPorcentajeGanancia.getText());
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                //nfe.printStackTrace();
                pct = 0;
            }
            String precio = FarmaUtility.formatNumber((vCosto * catFrac / vCantidad)*(1+pct/100), 2).replace(",", "");
            txtPrecioVentaSugerido.setText(precio);
        } catch (Exception nfe) {
            // TODO: Add catch code
            //nfe.printStackTrace();
            txtPrecioVentaSugerido.setText("");
        }
    }

    private void txtCantidad_keyReleased(KeyEvent e) {
        calculaPrecioVenta();
        
    }

    private void txtPorcentajeGanancia_keyReleased(KeyEvent e) {
        calculaPrecioVenta();
    }

    private void txtPrecioVenta_keyReleased(KeyEvent e) {
        calculoPorcentaje();
    }

    private void calculoPorcentaje() {
        try {
            int catFrac = Integer.parseInt(txtCantidad.getText().trim());
            double pPrecioFinal = 0;
            try {
                pPrecioFinal = Double.parseDouble(txtPrecioVentaSugerido.getText());
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                //nfe.printStackTrace();
                pPrecioFinal = 0;
            }
            
            String pct = "";
           
            pct = FarmaUtility.formatNumber(100*(pPrecioFinal*vCantidad  - vCosto*catFrac)/(vCosto * catFrac), 2).replace(",", "");
           
            //String precio = FarmaUtility.formatNumber((vCosto * catFrac / vCantidad)*(1+pct/100), 2).replace(",", "");
            txtPorcentajeGanancia.setText(pct);
        } catch (Exception nfe) {
            // TODO: Add catch code
            //nfe.printStackTrace();
            txtPrecioVentaSugerido.setText("");
        }
        
    }

    private void txtPrecioMinimo_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtDescripcionPrecio);
        }else{
            chkKeyPressed(e);
        }
    }

    public void setVDatoModificar(ArrayList vDatoModificar) {
        this.vDatoModificar = vDatoModificar;
    }

    public ArrayList getVDatoModificar() {
        return vDatoModificar;
    }
}
