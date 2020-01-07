package mifarma.ptoventa.centromedico;

import common.FarmaDBUtility;
import common.FarmaLoadCVL;
import common.FarmaUtility;
import common.FarmaVariables;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.ptoventa.centromedico.reference.DBAtencionMedica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAddTipDoc extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgADMBuscarPaciente.class);
    private JButton jButton1 = new JButton();
    private JTextField txtNTipDoc = new JTextField();
    private JLabel lblNTipDoc = new JLabel();
    private JButton btnAgregar = new JButton();

    public DlgAddTipDoc() {
        this(null, "", false);
    }

    public DlgAddTipDoc(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(364, 121));
        this.getContentPane().setLayout( null );
        this.setTitle("Agregar nuevo tipo de documento");
        jButton1.setText("jButton1");
        
        txtNTipDoc.setBounds(new Rectangle(155, 25, 180, 20));
        txtNTipDoc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextField1_actionPerformed(e);
                }

                private void jTextField1_actionPerformed(ActionEvent actionEvent) {
                }
            });
        lblNTipDoc.setText("Ingrese el tipo de documento:");
        lblNTipDoc.setBounds(new Rectangle(10, 25, 150, 20));
        btnAgregar.setText("Agregar");
        btnAgregar.setBounds(new Rectangle(140, 60, 75, 21));


        btnAgregar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnAgregar_actionPerformed(e);
                }
            });
        this.getContentPane().add(btnAgregar, null);
        this.getContentPane().add(lblNTipDoc, null);
        this.getContentPane().add(txtNTipDoc, null);
    }

    private void btnAgregar_actionPerformed(ActionEvent e) {
        TipDoc();

        try {
            DBAtencionMedica.insertarNewTipDoc(TipDoc());
            FarmaUtility.aceptarTransaccion();
            cerrarVentana(true);
        } catch (SQLException f) {
            log.error("", f);
            cerrarVentana(true);
        }
        
    }
    
  
    
    private String TipDoc(){ 
      String tipdoc = txtNTipDoc.getText();
        return tipdoc;
        };
    private void cerrarVentana(boolean pAceptar){
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    public static void insertarNewTipDoc(String pDescDocumento) throws SQLException{
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(pDescDocumento);
        
        FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CME_ADM.CME_INSERT_TIP_DOCUMENTO(?,?,?)",parametros);
    }
    
}
