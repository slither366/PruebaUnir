package dental;

import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JConfirmDialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import venta.caja.reference.VariablesCaja;

import venta.cliente.DlgMantClienteNatural;

import venta.modulo_venta.reference.DBModuloVenta;

public class DlgDescuentoPedido extends JDialog {
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtDescuento = new JTextField();
    private JLabel lblPrecioOrignal = new JLabel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JLabel lblPrecioFinal = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    
    public double pNetoOriginal = 0.0;
    private JLabel jLabel2 = new JLabel();
    private JLabel lblValorDescuento = new JLabel();


    public double porcentaje = 0.0;
    public double pDescuento = 0.0;
    public double pPrecioFinal = 0.0;
    private JLabel jLabel3 = new JLabel();
    private JTextField txtMontoDescuento = new JTextField();

    public DlgDescuentoPedido()
    {
    this(null, "", false);
    }

    public DlgDescuentoPedido(Frame parent, String title, boolean modal)
    {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      
    }

    }    
    
    

    private void jbInit() throws Exception {
        
        this.addWindowListener(new WindowAdapter() {
                            public void windowOpened(WindowEvent e) {
                                    this_windowOpened(e);
                            }


                
            });
        
        this.setSize(new Dimension(485, 278));
        jPanel1.setLayout(null);
        jPanel1.setBackground(SystemColor.window);
        jLabel1.setText("% Descuento");
        jLabel1.setBounds(new Rectangle(15, 65, 85, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        txtDescuento.setBounds(new Rectangle(100, 60, 135, 20));
        txtDescuento.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    txtDescuento_keyTyped(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtDescuento_keyPressed(e);
                }


                public void keyReleased(KeyEvent e) {
                    txtDescuento_keyReleased(e);
                }
            });
        txtDescuento.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtDescuento_focusLost(e);
                }
            });
        lblPrecioOrignal.setText("S/  999,999.00");
        lblPrecioOrignal.setBounds(new Rectangle(25, 110, 190, 25));
        lblPrecioOrignal.setFont(new Font("SansSerif", 1, 13));
        jButton1.setText("[F11] - Aceptar");
        jButton1.setBounds(new Rectangle(25, 190, 125, 20));
        jButton1.setFont(new Font("SansSerif", 1, 11));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton2.setText("[ESC] -  Salir");
        jButton2.setBounds(new Rectangle(325, 190, 105, 20));
        jButton2.setFont(new Font("SansSerif", 1, 11));
        lblPrecioFinal.setText("S/ 999,999.00");
        lblPrecioFinal.setBounds(new Rectangle(220, 80, 250, 35));
        lblPrecioFinal.setFont(new Font("Tahoma", 1, 26));
        lblPrecioFinal.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("Neto Original :");
        jLabel4.setBounds(new Rectangle(25, 90, 85, 15));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setText("Neto con Descuento:");
        jLabel5.setBounds(new Rectangle(255, 55, 165, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setText("Descuento S/ ");
        jLabel2.setBounds(new Rectangle(255, 120, 80, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        lblValorDescuento.setText("jLabel3");
        lblValorDescuento.setBounds(new Rectangle(255, 150, 180, 25));
        lblValorDescuento.setFont(new Font("SansSerif", 1, 14));
        jLabel3.setText("Descuento Soles");
        jLabel3.setBounds(new Rectangle(10, 20, 100, 25));
        jLabel3.setFont(new Font("SansSerif", 1, 11));
        txtMontoDescuento.setBounds(new Rectangle(115, 20, 120, 20));
        txtMontoDescuento.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    txtMontoDescuento_keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                    txtMontoDescuento_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtMontoDescuento_keyTyped(e);
                }
            });
        jPanel1.add(txtMontoDescuento, null);
        jPanel1.add(jLabel3, null);
        jPanel1.add(lblValorDescuento, null);
        jPanel1.add(jLabel2, null);
        jPanel1.add(jLabel5, null);
        jPanel1.add(jLabel4, null);
        jPanel1.add(lblPrecioFinal, null);
        jPanel1.add(jButton2, null);
        jPanel1.add(jButton1, null);
        jPanel1.add(lblPrecioOrignal, null);
        jPanel1.add(txtDescuento, null);
        jPanel1.add(jLabel1, null);
        this.getContentPane().add(jPanel1, null);
        
        
        
    }

    private void this_windowOpened(WindowEvent windowEvent) {
        FarmaUtility.centrarVentana(this);
        lblValorDescuento.setText("");
        lblPrecioOrignal.setText("S/ "+pNetoOriginal);
        lblPrecioFinal.setText("");
        calculoDescuento();
        
    }
    
    

    private void txtDescuento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e,txtDescuento, 100,this);
    }

    public void setPNetoOriginal(double pNetoOriginal) {
        this.pNetoOriginal = pNetoOriginal;
    }

    public double getPNetoOriginal() {
        return pNetoOriginal;
    }


    private void txtDescuento_keyReleased(KeyEvent e) {
        calculoDescuento();
    }

    private void calculoDescuento() {
        
        /*double porcentaje = 0.0;
        double pDescuento = 0.0;
        double pPrecioFinal = 0.0;*/
        
        if(txtDescuento.getText().trim().length()>0){
            try {
                porcentaje = Double.parseDouble(txtDescuento.getText().trim());
            } catch (Exception nfe) {
                // TODO: Add catch code
                //nfe.printStackTrace();
                porcentaje = 0.0;
            }
            // PERMITIR INCREMENTAR EL PRECIO DE VENTA
            //if(porcentaje>0&&porcentaje<=100){
            if(true){
                
                pDescuento = fijarNumero(pNetoOriginal*porcentaje/100,1) ;
                
                pPrecioFinal = fijarNumero(pNetoOriginal- pDescuento,1);
                
                lblPrecioFinal.setText("S/ "+
                                       FarmaUtility.formatNumber(pPrecioFinal)
                                       );
                
                lblValorDescuento.setText("S/ "+
                                       FarmaUtility.formatNumber(pDescuento)
                                       );
            }
        }
        else{
            
            porcentaje = 0.0;
            pDescuento = 0;
            pPrecioFinal = pNetoOriginal;
            
            lblPrecioFinal.setText("S/ "+
                                   FarmaUtility.formatNumber(pNetoOriginal)
                                   );
            
            lblValorDescuento.setText("S/ "+
                                   FarmaUtility.formatNumber(0)
                                   );
        }
        
        
        
    }
    
    public double fijarNumero(double numero, int digitos) {
         double resultado;
         resultado = numero * Math.pow(10, digitos);
         resultado = Math.round(resultado);
         resultado = resultado/Math.pow(10, digitos);
         return resultado;
     }

    private void txtDescuento_focusLost(FocusEvent e) {
        //FarmaUtility.moveFocus(txtDescuento);
    }
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    private void txtDescuento_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtMontoDescuento);
            txtDescuento.setText("");
        }
        else 
            if(e.getKeyCode()==KeyEvent.VK_F11){
            evento_f11();
        }
        else
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                cerrarVentana(false);
            }
    }

    private void txtMontoDescuento_keyReleased(KeyEvent e) {
        if(txtMontoDescuento.getText().trim().length()>0){
            txtDescuento.setText("");
            double pPctoDescuento = 0.0;
            double pDescuentoSOles = Double.parseDouble(txtMontoDescuento.getText().trim());
            pDescuento = fijarNumero(100*(pDescuentoSOles/pNetoOriginal),1) ;
            txtDescuento.setText(FarmaUtility.formatNumber(pDescuento).replace(",",""));
            calculoDescuento();    
        }
        
    }

    private void txtMontoDescuento_keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtDescuento);
            txtMontoDescuento.setText("");
        }
        else 
            if(e.getKeyCode()==KeyEvent.VK_F11){
            evento_f11();
        }
        else
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                cerrarVentana(false);
            }
    }

    private void txtMontoDescuento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e,txtMontoDescuento, 100,this);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        evento_f11();
    }
    
    private void evento_f11(){

        if (JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de aplicar el descuento indicado?")) {
            try {
                DBModuloVenta.procesaDescuentoPedido(
                VariablesCaja.vNumPedVta,
                FarmaUtility.formatNumber(pDescuento).replace(",","").trim()
                );
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se aplico el descuento al pedido.", txtDescuento);
                cerrarVentana(true);
            } catch (Exception f) {
                f.printStackTrace();
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al grabar el descuento indicado\n"+
                                               f.getMessage(), txtDescuento);
                cerrarVentana(false);
            }
        }
    }
}
