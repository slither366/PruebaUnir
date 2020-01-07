package com.gs.mifarma.worker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


import componentes.gs.componentes.FarmaSegundos;

import common.FarmaUtility;

import componentes.gs.componentes.JPanelWhite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.FrmEconoFar;


/**
 * Ventana de progreso
 * @author ERIOS
 * @since 15.07.2013
 */
public abstract class JDialogProgress extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(JDialogProgress.class);

    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();

    private JFarmaWorker trabajador;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabel jLabel2 = new JLabel();
    private JProgressBar jProgressBar2 = new JProgressBar();

    FarmaSegundos lblHora = new FarmaSegundos();

    public JDialogProgress() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public JDialogProgress(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();

        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(406, 202));
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setTitle("Proceso en ejecuci�n. Espere...");

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        jPanel1.setLayout(borderLayout1);
        jLabel1.setText("Progreso");
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        ImageIcon imagen = new ImageIcon(FrmEconoFar.class.getResource("/venta/imagenes/cargando.gif"));
        jLabel2 = new JLabel(imagen);
        jLabel2.setBounds(new Rectangle(0, 0, 405, 105));

        jPanel1.add(jLabel1, BorderLayout.NORTH);

        lblHora.setText("");
        lblHora.setBounds(new Rectangle(0, 110, 405, 30));
        lblHora.setFont(new Font("MS Reference Sans Serif", 3, 20));
        lblHora.setForeground(new Color(0, 132, 0));
        jPanelWhite1.add(jLabel2, null);


        jPanelWhite1.add(lblHora, null);
        jPanel1.add(jPanelWhite1, BorderLayout.CENTER);
        jPanel1.add(jProgressBar2, BorderLayout.SOUTH);
        this.getContentPane().add(jPanel1, null);
    }

    public void mostrar() {

        lblHora.start();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void cerrar() {
        setVisible(false);
        dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        iniciaProcesoProgressBar();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Acci�n prohibida, esperar que termine el proceso.", null);
    }

    private void iniciaProcesoProgressBar() {
        if (trabajador == null) {
            trabajador = new JFarmaWorker(this, jLabel1, jProgressBar2);

            //Agrego un Listener para el cambio de la propiedad "progress"
            trabajador.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName())) {
                        //jLabel1.setText(evt.getNewValue() + " %");
                        log.debug(evt.getNewValue() + " %");
                    }
                }
            });
        }
        trabajador.execute();
    }


    public abstract void ejecutaProceso();
}
