package mifarma.ptoventa.centromedico;

import componentes.gs.componentes.JLabelFunction;

import componentes.gs.componentes.JLabelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import common.*;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import venta.*;

import venta.modulo_venta.reference.ConstantsModuloVenta;

import venta.reference.*;

import venta.caja.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgMotivoLiberarAtencion extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgMotivoLiberarAtencion.class);
    
    Frame myParentFrame;
    

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JButton btnMotivoAnulacion = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextArea txtMotivo = new JTextArea();
    private JLabelWhite lblMensaje = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMotivoLiberarAtencion() {
        this(null, "", false);
    }

    public DlgMotivoLiberarAtencion(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

   
    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(434, 279));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Liberaci\u00f3n de Atenci\u00f3n");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(435, 208));
        jContentPane.setLayout(null);
        jPanel1.setBounds(new Rectangle(20, 20, 390, 30));
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel1.setBackground(new Color(0, 114, 169));
        jPanel1.setLayout(null);
        btnMotivoAnulacion.setText("Motivo de Liberaci\u00f3n");
        btnMotivoAnulacion.setBounds(new Rectangle(10, 5, 225, 20));
        btnMotivoAnulacion.setMnemonic('l');
        btnMotivoAnulacion.setBackground(new Color(0, 114, 169));
        btnMotivoAnulacion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 
                                                                      0));
        btnMotivoAnulacion.setBorderPainted(false);
        btnMotivoAnulacion.setContentAreaFilled(false);
        btnMotivoAnulacion.setDefaultCapable(false);
        btnMotivoAnulacion.setFocusPainted(false);
        btnMotivoAnulacion.setHorizontalAlignment(SwingConstants.LEFT);
        btnMotivoAnulacion.setRequestFocusEnabled(false);
        btnMotivoAnulacion.setForeground(Color.white);
        btnMotivoAnulacion.setFont(new Font("SansSerif", 1, 11));
        btnMotivoAnulacion.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnMotivoAnulacion_keyPressed(e);
                    }
                });
        btnMotivoAnulacion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMotivoAnulacion_actionPerformed(e);
                    }
                });

        txtMotivo.setBounds(new Rectangle(0, 0, 400, 123));
        txtMotivo.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                        txtMotivo_keyPressed(e);
                    }

                    
                });
        lblMensaje.setBounds(new Rectangle(20, 180, 330, 25));
        lblMensaje.setForeground(Color.red);
        lblMensaje.setFont(new Font("Arial", 1, 15));
        jScrollPane1.setBounds(new Rectangle(20, 50, 390, 125));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(325, 205, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(215, 205, 100, 20));

        jPanel1.add(btnMotivoAnulacion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jScrollPane1.getViewport();
        jScrollPane1.getViewport();
        jContentPane.add(lblMensaje, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel1, null);
        jScrollPane1.getViewport().add(txtMotivo, null);
        jContentPane.add(jScrollPane1, null);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
     
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtMotivo_keyPressed(KeyEvent e) {
        
          chkKeyPressed(e);
          
    }
  
    private void btnMotivoAnulacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnMotivoAnulacion);
    }

    private void btnMotivoAnulacion_keyPressed(KeyEvent e) {
   
        chkKeyPressed(e);
    }



    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMotivo);
        lblMensaje.setText("");
        VariablesCentroMedico.vMotivoLiberaAtencion = "";
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

  
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

   
    
    private void chkKeyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        VariablesCaja.vMotivoAnulacion="";
        cerrarVentana(false);
      }
      else if(UtilityPtoVenta.verificaVK_F11(e))
      {
          log.debug(" Aparece el metodo :");
                   
          ValidarCampo();
      }
    }
    private void ValidarCampo(){
       
    log.debug(" FarmaVariables.vAceptar " +  FarmaVariables.vAceptar);
             
          if (txtMotivo.getText().trim().length() > 0){
                log.debug("ingreso el motivo");
               
            
            VariablesCentroMedico.vMotivoLiberaAtencion =txtMotivo.getText();
                log.debug("DlgMotivoAnularVariablesCaja.vMotivoAnulacion:" + VariablesCentroMedico.vMotivoLiberaAtencion);
                cerrarVentana(true);
               // FarmaVariables.vAceptar =true;
            }
        
          else 
          {
            FarmaUtility.showMessage(this, "Debe ingresar el motivo de la liberaci�n.", null);
            FarmaVariables.vAceptar =false;
            
          }
        }
   
}
