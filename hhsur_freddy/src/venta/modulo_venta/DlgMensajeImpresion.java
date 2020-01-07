package venta.modulo_venta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

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

import venta.modulo_venta.reference.DBModuloVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.VariablesCaja;


public class DlgMensajeImpresion extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMensajeImpresion.class);
    @SuppressWarnings("compatibility:5305877824751256928")
    private static final long serialVersionUID = 1L;

    FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JLabel lblClave = new JLabel();

    JPanel pnlAbajo = new JPanel();
    JEditorPane jEditorPaneAba = new JEditorPane();
    String htmlIzquierdo = "",htmlDerecho = "",htmlAbajo = "";

    Dimension pantalla = null;

    String pTipComp = "";
    
   // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeImpresion() {
        this(null, "", false,"");
    }

    Frame myParentFrame;

	public DlgMensajeImpresion(Frame parent, String title, boolean modal,String pTipCo) {
        super(parent, "ALERTA DE IMPRESION", modal);
        myParentFrame = parent;
        pTipComp = pTipCo;
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

      jEditorPaneAba.setContentType("text/html");
      jEditorPaneAba.setText(htmlAbajo);
      jEditorPaneAba.setEditable(false);
      jEditorPaneAba.setBorder(BorderFactory.createLineBorder(Color.ORANGE,9));

      jEditorPaneAba.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jEditorPaneAba_keyPressed(e);
                    }
                });
       this.getContentPane().setLayout(new GridLayout(0,1));


       pnlAbajo.setLayout(new GridLayout());
       pnlAbajo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jEditorPaneDec_keyPressed(e);
                    }
                });
        //Panel Abajo
        pnlAbajo.setLayout(new GridBagLayout());
        pnlAbajo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlAbajo_keyPressed(e);
                    }
                });
        pnlAbajo.add(jEditorPaneAba);
        this.getContentPane().add(pnlAbajo);
        //  this.setBounds(new Rectangle(50,50,pantalla.width-690,pantalla.height-160));
              this.setSize(pantalla.width, pantalla.height);  
        //this.setSize(pantalla);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModal(true);

    }
    // **************************************************************************
    // Initialize
    // **************************************************************************
    private void initialize() {
		// TODO Auto-generated method stub
		cargaMensajes();
	    //jEditorPaneDec.setText(htmlDerecho);
	    jEditorPaneAba.setText(htmlAbajo);
	    //jEditorPaneIzq.setText(htmlIzquierdo);
	}
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e)
    {
      FarmaUtility.centrarVentana(this);
      jEditorPaneAba.requestFocus();
    }

    private void this_windowClosing(WindowEvent e) {
           

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

        	if(componentes.gs.componentes.JConfirmDialog.rptaConfirmDialog(this,"Esta seguro que va imprimir?"))
        	{
               cerrarVentana(true);
            }


        }
    }


    private void pnlAbajo_keyPressed(KeyEvent e) {
        log.debug("tecla Abajo +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void jEditorPaneDec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneAba_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }



   // **************************************************************************
   // LOGICA DE NEGOCIOd
   // **************************************************************************
   public void cargaMensajes()

   {
                     Map map =obtieneMsgImpresion(this, null,pTipComp);
		     htmlAbajo = map.get("UNO").toString()+map.get("DOS")+map.get("TRES")+map.get("CUATRO");

   }
   
    public static Map obtieneMsgImpresion(JDialog pDialogo,
            Object pObjeto,String pTipComp)
    {
                        Map resTipo= null;

                        try
                        {
                                resTipo = DBModuloVenta.obtieneMsgImp(pTipComp);

                        } catch (SQLException sql)
                        {
                                log.error("",sql);
                                FarmaUtility.showMessage(pDialogo,
                                "Error al obtener el mensaje de Impresion" +
                                sql.getMessage(), pObjeto);
                        }

                return resTipo;
    }


}
