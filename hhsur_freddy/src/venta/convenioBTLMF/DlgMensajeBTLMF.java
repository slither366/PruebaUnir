package venta.convenioBTLMF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.FarmaSecurity;
import common.FarmaUtility;
import common.FarmaVariables;
import venta.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgMensajeBTLMF.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * FRAMIREZ      12.11.2011  Creaci�n<br>
 * <br>
 * @author Fredy Ramirez C.<br>
 * @version 1.0<br>
 *
 */

public class DlgMensajeBTLMF extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMensajeBTLMF.class);

     FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JLabel lblClave = new JLabel();

    JPanel pnlArriba = new JPanel();
    JPanel pnlIzquierda = new JPanel();
    JPanel pnlDerecha = new JPanel();
    JPanel pnlAbajo = new JPanel();
    JEditorPane jEditorPaneIzq = new JEditorPane();
    JEditorPane jEditorPaneDec = new JEditorPane();
    JEditorPane jEditorPaneAba = new JEditorPane();
    String htmlIzquierdo = "",htmlDerecho = "",htmlAbajo = "";

    Dimension pantalla = null;

   // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeBTLMF() {
        this(null, "", false);
    }

    Frame myParentFrame;

	public DlgMensajeBTLMF(Frame parent, String title, boolean modal) {
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
    	this.setFocusable(true);
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
        this.addWindowListener(new WindowAdapter()
          {
            public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
              this_windowClosing(e);
            }

          });

      pantalla = Toolkit.getDefaultToolkit().getScreenSize();



      pnlArriba.setBackground(Color.BLUE);
      pnlArriba.setLayout(new GridLayout(1,0));
      pnlArriba.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlArriba_keyPressed(e);
                    }
                });
      jEditorPaneIzq.setEditable(false);
      jEditorPaneIzq.setContentType("text/html");
      //Panel Izquierdo
      pnlIzquierda.setLayout(new GridBagLayout());



      jEditorPaneIzq.setText(htmlIzquierdo);
      jEditorPaneIzq.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jEditorPaneIzq_keyPressed(e);
                    }
                });
      jEditorPaneIzq.setLocation(0,0);

      jEditorPaneIzq.setBorder(BorderFactory.createLineBorder(Color.RED,9));
      pnlIzquierda.add(jEditorPaneIzq);
      jEditorPaneDec.setContentType("text/html");
      jEditorPaneDec.setText(htmlDerecho);
      jEditorPaneDec.setEditable(false);
      jEditorPaneDec.setBorder(BorderFactory.createLineBorder(Color.BLUE,9));

      jEditorPaneAba.setContentType("text/html");
      jEditorPaneAba.setText(htmlAbajo);
      jEditorPaneAba.setEditable(false);
      jEditorPaneAba.setBorder(BorderFactory.createLineBorder(Color.GREEN,9));

      jEditorPaneAba.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jEditorPaneAba_keyPressed(e);
                    }
                });
       this.getContentPane().setLayout(new GridLayout(0,1));
       pnlArriba.add(pnlIzquierda);
       pnlDerecha.setLayout(new GridBagLayout());
      // pnlIzquierda.setBackground(Color.RED);
       pnlIzquierda.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlIzquierda_keyPressed(e);
                    }
                });
       pnlDerecha.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlDerecha_keyPressed(e);
                    }
                });
       jEditorPaneDec.setLayout(new GridLayout());
       jEditorPaneDec.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jEditorPaneDec_keyPressed(e);
                    }
                });
        pnlDerecha.add(jEditorPaneDec);
        pnlArriba.add(pnlDerecha);
        this.getContentPane().add(pnlArriba);
        //Panel Abajo
        pnlAbajo.setLayout(new GridBagLayout());
        pnlAbajo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlAbajo_keyPressed(e);
                    }
                });
        pnlAbajo.add(jEditorPaneAba);
        this.getContentPane().add(pnlAbajo);
        this.setBounds(new Rectangle(0,0,pantalla.width,pantalla.height));
        this.setSize(pantalla);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModal(true);

    }
    // **************************************************************************
    // Initialize
    // **************************************************************************
    private void initialize() {
		// TODO Auto-generated method stub
		cargaMensajes();
	    jEditorPaneDec.setText(htmlDerecho);
	    jEditorPaneAba.setText(htmlAbajo);
	    jEditorPaneIzq.setText(htmlIzquierdo);
	}
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e)
    {
      FarmaUtility.centrarVentana(this);
      jEditorPaneIzq.requestFocus();
    }

    private void this_windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub

    }
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    // **************************************************************************
    // KEY PRESSED
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e)
    {
        log.debug("tecla  +  " + e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            cerrarVentana(true);
        }
    }
    private void pnlArriba_keyPressed(KeyEvent e)
    {
        log.debug("tecla Arriba +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlAbajo_keyPressed(KeyEvent e) {
        log.debug("tecla Abajo +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlIzquierda_keyPressed(KeyEvent e) {
        log.debug("tecla izq +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void pnlDerecha_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneIzq_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneDec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneAba_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }



   // **************************************************************************
   // LOGICA DE NEGOCIO
   // **************************************************************************
   public void cargaMensajes()
   {

	   String nroResolucion = pantalla.width+"x"+pantalla.height;

           String vNombreBenif = VariablesConvenioBTLMF.vNombre + " " +VariablesConvenioBTLMF.vApellidoPat;

           htmlAbajo      = UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio,ConstantsConvenioBTLMF.FLG_DOC_SOLUCION,vNombreBenif,this,null);
           htmlDerecho    = UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio,ConstantsConvenioBTLMF.FLG_DOC_VERIFICACION,vNombreBenif,this,null);
       	   htmlIzquierdo  = UtilityConvenioBTLMF.obtieneDocVerificacion(VariablesConvenioBTLMF.vCodConvenio,ConstantsConvenioBTLMF.FLG_DOC_RETENCION,vNombreBenif,this,null);


	       Map pantallaAbajo = UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(), ConstantsConvenioBTLMF.PANTALLA_POS_ABA, this, null);

	       if(pantallaAbajo != null)
	       {
	         String margenPixelA   = (String)pantallaAbajo.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
	         String margenPixelAH  = (String)pantallaAbajo.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
	         htmlAbajo     = htmlAbajo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_ABA_PIXEL_ANCHO,(pantalla.width-Integer.parseInt(margenPixelA))+"px");
	         htmlAbajo     = htmlAbajo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_ABA_PIXEL_ALTO,(pantalla.height-Integer.parseInt(margenPixelAH))+"px");
	       }

	       Map pantallaDerecha = UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(),ConstantsConvenioBTLMF.PANTALLA_POS_DER, this, null);
	       if(pantallaDerecha != null)
	       {
	         String margenPixelD  = (String)pantallaDerecha.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
	         String margenPixelDH = (String)pantallaDerecha.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
	         htmlDerecho   = htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_DER_PIXEL_ANCHO,(pantalla.width-Integer.parseInt(margenPixelD))+"px");
	         htmlDerecho   = htmlDerecho.replaceAll(ConstantsConvenioBTLMF.PANTALLA_DER_PIXEL_ALTO,(pantalla.height-Integer.parseInt(margenPixelDH))+"px");
	       }

	       Map pantallaIzquierda = UtilityConvenioBTLMF.obtienePantallaMensaje(nroResolucion.trim(), ConstantsConvenioBTLMF.PANTALLA_POS_IZQ, this, null);

	       if(pantallaIzquierda != null)
	       {

	         String margenPixelI  = (String)pantallaIzquierda.get(ConstantsConvenioBTLMF.COL_FACTOR_ANCHO);
	         String margenPixelIH = (String)pantallaIzquierda.get(ConstantsConvenioBTLMF.COL_FACTOR_ALTO);
	         htmlIzquierdo = htmlIzquierdo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ANCHO,(pantalla.width-Integer.parseInt(margenPixelI))+"px");
	         htmlIzquierdo = htmlIzquierdo.replaceAll(ConstantsConvenioBTLMF.PANTALLA_IZQ_PIXEL_ALTO,(pantalla.height-Integer.parseInt(margenPixelIH))+"px");
	       }


   }




}
