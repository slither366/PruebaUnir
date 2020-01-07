package venta.reportes.repo_renova;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.*;

import javax.swing.ImageIcon;

import venta.reference.UtilityPtoVenta;
import venta.reportes.DlgDetalleRegistroVentas;
import venta.reportes.DlgListaPedAnulNoCob;
import venta.reportes.DlgOrdenar;
import venta.reportes.DlgRegistroVentas;
import venta.reportes.DlgResumenVenta;
import venta.reportes.reference.ConstantsReporte;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.modulo_venta.DlgIngresoMedicoPedido;


public class DlgEncuesta  extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgEncuesta.class);
    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    ImageIcon imageUno = new ImageIcon(DlgEncuesta.class.getResource("/venta/imagenes/pancarta.jpg"));
    private JButton btnOpcionUno = new JButton(imageUno);
    ImageIcon imageDos = new ImageIcon(DlgEncuesta.class.getResource("/venta/imagenes/porHospital.jpg"));
    private JButton btnOpcionDos = new JButton(imageDos);
    ImageIcon imageTres = new ImageIcon(DlgEncuesta.class.getResource("/venta/imagenes/porRevista.jpg"));
    private JButton btnOpcionTres = new JButton(imageTres);
    ImageIcon imageCuatro = new ImageIcon(DlgEncuesta.class.getResource("/venta/imagenes/porunamigo.jpg"));
    private JButton btnOpcionCuatro = new JButton(imageCuatro);
    ImageIcon imageCinco = new ImageIcon(DlgEncuesta.class.getResource("/venta/imagenes/porotro.jpg"));
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JButton btnOtros = new JButton(imageCinco);
    private JLabel jLabel6 = new JLabel();


    public DlgEncuesta() {
        this(null, "", false);
    }

    public DlgEncuesta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(1134, 599));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Encuesta para Cliente");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setFocusable(false);
        // jLabelFunction1.setBounds(new Rectangle(280, 370, 130, 20));
        //jLabelFunction1.setText("[ F8 ] Guardar Archivo");
        //jContentPane.add(jLabelFunction1, null);
        btnOpcionUno.setText("jButton1");
        btnOpcionUno.setBounds(new Rectangle(35, 80, 245, 160));
        btnOpcionUno.setHorizontalTextPosition(SwingConstants.CENTER);
        btnOpcionUno.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnOpcionUno_actionPerformed(e);
                }
            });
        btnOpcionDos.setText("jButton2");
        btnOpcionDos.setBounds(new Rectangle(365, 85, 320, 155));
        btnOpcionDos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnOpcionDos_actionPerformed(e);
                }
            });
        btnOpcionTres.setText("jButton3");
        btnOpcionTres.setBounds(new Rectangle(780, 80, 315, 170));
        btnOpcionTres.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnOpcionTres_actionPerformed(e);
                }
            });
        btnOpcionCuatro.setText("jButton4");
        btnOpcionCuatro.setBounds(new Rectangle(65, 325, 360, 185));
        btnOpcionCuatro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnOpcionCuatro_actionPerformed(e);
                }
            });
        jLabel1.setText("<html><center>\u00bfComo se enter\u00f3 de nuestros servicios?</center></html>");
        jLabel1.setBounds(new Rectangle(35, 0, 735, 45));
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setFont(new Font("SansSerif", 1, 26));
        jLabel1.setForeground(new Color(0, 165, 0));
        jLabel2.setText("<html><center>.. Por Publicidad en las Calles ..</center></html>");
        jLabel2.setBounds(new Rectangle(0, 55, 320, 20));
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setFont(new Font("SansSerif", 1, 16));
        jLabel2.setForeground(new Color(0, 82, 255));
        jLabel3.setText("<html><center> .. Por avisos en el Hospital..</center></html>");
        jLabel3.setBounds(new Rectangle(360, 60, 330, 15));
        jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setFont(new Font("SansSerif", 1, 15));
        jLabel3.setForeground(new Color(0, 82, 255));
        jLabel4.setText("<html><center>.. Por Revistas o Peri\u00f3dicos ..</center></html>");
        jLabel4.setBounds(new Rectangle(760, 45, 340, 20));
        jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setFont(new Font("SansSerif", 1, 15));
        jLabel4.setForeground(new Color(0, 74, 231));
        jLabel5.setText("<html><center>.. Por Recomendaci\u00f3n..</center></html>");
        jLabel5.setBounds(new Rectangle(35, 275, 360, 25));
        jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setFont(new Font("SansSerif", 1, 15));
        jLabel5.setForeground(new Color(0, 74, 231));
        btnOtros.setBounds(new Rectangle(650, 325, 130, 180));
        btnOtros.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnOtros_actionPerformed(e);
                }
            });
        jLabel6.setText(".. Por Otras Formas ..");
        jLabel6.setBounds(new Rectangle(640, 285, 165, 15));
        jLabel6.setForeground(new Color(0, 74, 231));
        jLabel6.setFont(new Font("SansSerif", 1, 15));
        jContentPane.add(jLabel6, null);
        jContentPane.add(btnOtros, null);
        jContentPane.add(jLabel5, null);
        jContentPane.add(jLabel4, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(btnOpcionCuatro, null);
        jContentPane.add(btnOpcionTres, null);
        jContentPane.add(btnOpcionDos, null);
        jContentPane.add(btnOpcionUno, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaRegistroVentas();
    };

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaRegistroVentas() {
    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

   

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
    }


    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe seleccionar una opción, es obligatorio.", null);
    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void grabaOpcion(String pMensaje,String pTipoOpcion,String pCadena){
        if(JConfirmDialog.rptaConfirmDialogDefaultNo(this,"Esta seguro de seleccionar:\n"+pMensaje+
                                                          "¿desea continuar?"))
            {
            try {
                DBEncuesta.grabaSeleccionEncuesta(pTipoOpcion, pCadena);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Se grabó con exito la encuesta.\n" +
                                                "Gracias.", null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                sqle.printStackTrace();
                FarmaUtility.showMessage(this, "Ocurrió un eror:\n" +
                                                sqle.getMessage(), null);
            }
             }
    }

    private void btnOpcionUno_actionPerformed(ActionEvent e) {
        grabaOpcion(" por publicidad en las calles ","01","");
    }

    private void btnOpcionDos_actionPerformed(ActionEvent e) {
        grabaOpcion(" por medio del hospital ","02","");
    }

    private void btnOpcionTres_actionPerformed(ActionEvent e) {
        grabaOpcion(" por medio de periodicos o revistas ","03","");
    }

    private void btnOpcionCuatro_actionPerformed(ActionEvent e) {
        grabaOpcion(" por recomendación ","04","");
    }

    private void btnOtros_actionPerformed(ActionEvent e) {
        String pDesc = FarmaUtility.ShowInput(this,"¿Como se enteró de nuestros servicios?");
        if(pDesc.trim().length()>0)
            grabaOpcion(" * "+pDesc+"* ","05",pDesc);
        else
            FarmaUtility.showMessage(this, "Debe colocar una descrición breve como lo explica el cliente.", null);

    }
}
