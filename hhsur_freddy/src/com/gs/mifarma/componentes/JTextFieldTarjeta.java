package com.gs.mifarma.componentes;

import common.FarmaUtility;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Lector de tarjetas
 * @author ERIOS
 * @since 27.09.2013
 */
public class JTextFieldTarjeta extends JTextFieldSanSerif {

    private static final Logger log = LoggerFactory.getLogger(JTextFieldTarjeta.class);

    private int intCountEnter = 0;
    private String strIndLectorBanda = "N";
    private ArrayList<String> listInforTarje = new ArrayList<>();
    private String desc_prod = "";
    private int cont = 0;
    private String infoTarj = "";
    private String bin = "";
    private String strCodFormaPago = "";
    private String strDescFormaPago = "";
    private String nroTarjeta = "";
    private String vNroTarjeta = "";

    
    private JDialog myParentFrame;
    private JLabel jlblTipTarjD = new JLabel();
    //GFONSECA 14/11/2013 Se modifico la clase a JComponent, para asignar a cualquier el foco a cualquier elemento
    private JComponent jComponentNextFocus = new JTextField();
    private ArrayList<String> arrayCodFormaPago = new ArrayList<String>();
    private JLabelFunction lblF11;
    private boolean primerIngreso = false;

    public void setLblTipTarjD(JLabel jlblTipTarjD) {
        this.jlblTipTarjD = jlblTipTarjD;
    }

    public void setCompNextFocus(JComponent jComponentNextFocus) {
        this.jComponentNextFocus = jComponentNextFocus;
    }

    public void setMyParentFrame(JDialog myParentFrame) {
        this.myParentFrame = myParentFrame;
    }

    public JTextFieldTarjeta(int i) {
        super(i);
    }

    public JTextFieldTarjeta() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
    }

    private void this_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            intCountEnter++;
        if (e.getKeyCode() == KeyEvent.VK_B) {
            strIndLectorBanda = "S";
            this.setForeground(Color.WHITE);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (strIndLectorBanda.equals("S") && intCountEnter == 1) {
                String tmpNro = "";
                String tmpNomb = "";
                String tmpFecha = "";
                tmpNro = listInforTarje.get(0);
                tmpNomb = listInforTarje.get(1);
                tmpFecha = listInforTarje.get(2);

                this.setText(tmpNro);
                this.setForeground(Color.BLACK);
                this.setEditable(false);
            }
            if ((strIndLectorBanda.equals("S") && intCountEnter == 2) ||
                (strIndLectorBanda.equals("N") && intCountEnter == 1)) {
                if (buscarInfotarjeta()) {
                    if (validaCampos()) {
                        if (primerIngreso) {
                            FarmaUtility.showMessage(myParentFrame, "Reingrese n�mero.", this);
                            this.setEnabled(false);
                            FarmaUtility.moveFocus(jComponentNextFocus);
                        } else {
                            FarmaUtility.showMessage(myParentFrame, "Tarjeta validada.", this);
                            this.setEnabled(false);
                            jlblTipTarjD.setText(desc_prod);
                            lblF11.setEnabled(true);
                            jComponentNextFocus.requestFocus(true);
                        }
                    } else {
                        inicializarVariables();
                        cont = 0;
                        infoTarj = "";
                        this.setEditable(true);
                        this.setText("");
                    }
                } else {
                    inicializarVariables();
                    cont = 0;
                    infoTarj = "";
                    this.setEditable(true);
                    this.setText("");
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana();
        }
    }

    private boolean buscarInfotarjeta() {
        boolean flag;
        ArrayList<ArrayList<String>> InfoTarjeta = new ArrayList<>();
        String NumTarjeta = this.getText().trim();
        //InfoTarjeta = "";
        if (InfoTarjeta.size() > 0) {
            ArrayList<String> reg = InfoTarjeta.get(0);
            bin = reg.get(0);
            desc_prod = reg.get(1);
            strCodFormaPago = reg.get(2);
            strDescFormaPago = reg.get(3);
            vNroTarjeta = this.getText();
            nroTarjeta = this.getText();
            this.setText("");
            flag = true;
        } else {
            bin = "";
            desc_prod = "";
            strCodFormaPago = "";
            strDescFormaPago = "";
            vNroTarjeta = "";
            nroTarjeta = "";
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Tipo de tarjeta desconocido", this);
        }

        return flag;
    }

    private boolean validaCampos() {
        boolean flag = true;
        if (vNroTarjeta.trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Ingrese Nro. de Tarjeta", this);
        } else if (Double.parseDouble(vNroTarjeta.trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Ingrese Nro. de Tarjeta", this);
            this.setText("");
        } else if (!(vNroTarjeta.trim()).equalsIgnoreCase(nroTarjeta)) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "No debio cambiar el Nro. de Tarjeta", this);
            inicializarVariables();
        } else if (bin.equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Ingrese Nro. de Tarjeta valido", this);
            this.setText("");
        } else if (vNroTarjeta.length() != 16) { //TODO Variable por tipo de tarjeta.
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Ingrese cantidad correcta del Nro. de Tarjeta.", this);
            this.setText("");
        } else if (!validaAlgoritmoLuhn(vNroTarjeta)) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Ingrese correctamente el Nro. de Tarjeta.", this);
        } else if (!hablitarTipoTarjeta()) {
            flag = false;
            FarmaUtility.showMessage(myParentFrame, "Tipo de tarjeta no admitida.", this);
            this.setText("");
        }
        return flag;
    }

    private void inicializarVariables() {
        bin = "";
        desc_prod = "";
        nroTarjeta = "";
        this.setText("");
        infoTarj = "";
        intCountEnter = 0;
        strIndLectorBanda = "N";
    }

    private void cerrarVentana() {
        myParentFrame.setVisible(false);
        myParentFrame.dispose();
    }

    public String getNroTarjeta() {
        return vNroTarjeta;
    }

    private boolean hablitarTipoTarjeta() {
        return (arrayCodFormaPago.size() > 0) ? arrayCodFormaPago.contains(strCodFormaPago) : true;
    }

    public void setArrayFormPago(ArrayList<String> codFormaPago) {
        this.arrayCodFormaPago = codFormaPago;
    }

    /**
     * Activa la etiqueta F11.
     * @author ERIOS
     * @since 2.2.8
     * @param lblF11
     */
    public void setLblF11(JLabelFunction lblF11) {
        this.lblF11 = lblF11;
    }

    public boolean validaAlgoritmoLuhn(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static void main(String[] vars) {
        boolean val = (new JTextFieldTarjeta()).validaAlgoritmoLuhn("4009170000137665");
        if (val)
            ;
    }

    public void setPrimerIngreso(boolean primerIngreso) {
        this.primerIngreso = primerIngreso;
    }
}
