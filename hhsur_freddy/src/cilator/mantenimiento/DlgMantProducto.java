package cilator.mantenimiento;


import cilator.mantenimiento.reference.DBMantenimiento;

import common.FarmaLoadCVL;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMantProducto extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantProducto.class);

    private Frame myParentFrame;
    FarmaTableModel tableModelListaMedicos;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblFiltra = new JLabelFunction();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtDescripcion = new JTextField();
    private JTextField txtCtdStkMaximo = new JTextField();
    private JTextField txtPrecio = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JTextField txtCodBarra = new JTextField();
    private JTextField txtCtdStkMinimo = new JTextField();
    private JTextField txtCosto = new JTextField();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JCheckBox jcbIsFraccionado = new JCheckBox();
    private JTextField txtCtdFraccion = new JTextField();
    private JLabel jLabel13 = new JLabel();
    private JComboBox cmbCategoria = new JComboBox();
    private JComboBox cmbSubCategoria = new JComboBox();
    private JComboBox cmbTipo = new JComboBox();
    private JComboBox cmbMarca = new JComboBox();

    public boolean pInsert = false;
    public boolean pUpdate = false;
    private String pId = "";
    private JComboBox cmbTipoConsumo = new JComboBox();
    private JLabel jLabel14 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtDescAuxiliar = new JTextArea();

    public DlgMantProducto() {
        this(null, "", false,"I","");
    }

    public DlgMantProducto(Frame parent, String title, boolean modal, String pAccion,String pIdUpdate) {
        super(parent, title, modal);
        myParentFrame = parent;
        if (pAccion.trim().equalsIgnoreCase("I"))
            pInsert = true;
        else if (pAccion.trim().equalsIgnoreCase("U")){
            this.pId = pIdUpdate;
            pUpdate = true;
        }
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(877, 533));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Mantenimiento de Producto");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlCliente.setBounds(new Rectangle(10, 10, 850, 450));

        lblFiltra.setBounds(new Rectangle(600, 465, 110, 20));
        lblFiltra.setText("[ F11 ]Grabar");


        jLabel1.setText("Descripci\u00f3n :");
        jLabel1.setBounds(new Rectangle(10, 15, 80, 15));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtDescripcion.setBounds(new Rectangle(95, 10, 735, 20));
        txtDescripcion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombre_keyPressed(e);
                }
            });
        txtCtdStkMaximo.setBounds(new Rectangle(475, 215, 220, 20));
        txtCtdStkMaximo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApePat_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCtdStkMaximo_keyTyped(e);
                }
            });
        txtPrecio.setBounds(new Rectangle(145, 255, 165, 20));
        txtPrecio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApeMat_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtPrecio_keyTyped(e);
                }
            });
        jLabel2.setText("Cantidad Stock M\u00e1ximo:");
        jLabel2.setBounds(new Rectangle(330, 220, 140, 15));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel3.setText("Precio S/ :");
        jLabel3.setBounds(new Rectangle(65, 260, 65, 15));
        jLabel3.setForeground(SystemColor.window);
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel5.setText("Categor\u00eda :");
        jLabel5.setBounds(new Rectangle(25, 330, 95, 20));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(SystemColor.window);
        jLabel7.setText("Descripci\u00f3n Auxiliar");
        jLabel7.setBounds(new Rectangle(15, 45, 125, 20));
        jLabel7.setForeground(SystemColor.window);
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel8.setText("Sub Categor\u00eda :");
        jLabel8.setBounds(new Rectangle(430, 330, 100, 20));
        jLabel8.setForeground(SystemColor.window);
        jLabel8.setFont(new Font("SansSerif", 1, 11));
        txtCodBarra.setBounds(new Rectangle(145, 290, 260, 20));
        txtCodBarra.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCodBarra_keyPressed(e);
                }
            });
        txtCtdStkMinimo.setBounds(new Rectangle(145, 215, 165, 20));
        txtCtdStkMinimo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCtdStkMinimo_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCtdStkMinimo_keyTyped(e);
                }
            });
        txtCosto.setBounds(new Rectangle(410, 255, 195, 20));
        txtCosto.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCosto_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCosto_keyTyped(e);
                }
            });
        jLabel9.setText("C\u00f3digo de Barra:");
        jLabel9.setBounds(new Rectangle(30, 295, 100, 15));
        jLabel9.setForeground(SystemColor.window);
        jLabel9.setFont(new Font("SansSerif", 1, 11));
        jLabel10.setText("Cantidad Stock M\u00ednimo:");
        jLabel10.setBounds(new Rectangle(5, 215, 140, 20));
        jLabel10.setForeground(SystemColor.window);
        jLabel10.setFont(new Font("SansSerif", 1, 11));
        jLabel11.setText("Costo S/ :");
        jLabel11.setBounds(new Rectangle(350, 255, 75, 20));
        jLabel11.setForeground(SystemColor.window);
        jLabel11.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setText("Tipo :");
        jLabel4.setBounds(new Rectangle(40, 370, 45, 15));
        jLabel4.setForeground(SystemColor.window);
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setText("Marca:");
        jLabel6.setBounds(new Rectangle(460, 370, 50, 20));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(SystemColor.window);
        jLabel12.setText("Es Fraccionado :");
        jLabel12.setBounds(new Rectangle(15, 405, 90, 20));
        jLabel12.setFont(new Font("SansSerif", 1, 11));
        jLabel12.setForeground(SystemColor.window);
        jcbIsFraccionado.setBounds(new Rectangle(110, 405, 20, 20));
        txtCtdFraccion.setBounds(new Rectangle(245, 400, 125, 25));
        txtCtdFraccion.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    txtCtdFraccion_keyTyped(e);
                }
            });
        jLabel13.setText("Cantidad Fracci\u00f3n:");
        jLabel13.setBounds(new Rectangle(140, 405, 110, 20));
        jLabel13.setFont(new Font("SansSerif", 1, 11));
        jLabel13.setForeground(SystemColor.window);
        cmbCategoria.setBounds(new Rectangle(95, 330, 290, 25));
        cmbCategoria.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                    cmbCategoria_itemStateChanged(e);
                }
            });
        cmbCategoria.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbCategoria_keyPressed(e);
                }
            });
        cmbSubCategoria.setBounds(new Rectangle(520, 330, 300, 25));
        cmbSubCategoria.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbSubCategoria_keyPressed(e);
                }
            });
        cmbTipo.setBounds(new Rectangle(95, 365, 290, 25));
        cmbTipo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTipo_keyPressed(e);
                }
            });
        cmbMarca.setBounds(new Rectangle(520, 365, 305, 25));
        cmbMarca.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbMarca_keyPressed(e);
                }
            });
        cmbTipoConsumo.setBounds(new Rectangle(520, 415, 165, 20));
        cmbTipoConsumo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTipoConsumo_keyPressed(e);
                }
            });
        jLabel14.setText("Tipo. Consumo : ");
        jLabel14.setBounds(new Rectangle(405, 415, 100, 20));
        jLabel14.setForeground(SystemColor.window);
        jLabel14.setFont(new Font("Tahoma", 1, 11));
        jScrollPane1.setBounds(new Rectangle(130, 50, 705, 140));
        txtDescAuxiliar.setLineWrap(true);
        txtDescAuxiliar.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDescAuxiliar_keyPressed(e);
                }
            });
        jLabelFunction2.setBounds(new Rectangle(740, 465, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        jScrollPane1.getViewport().add(txtDescAuxiliar, null);
        pnlCliente.add(jScrollPane1, null);
        pnlCliente.add(jLabel14, null);
        pnlCliente.add(cmbTipoConsumo, null);
        pnlCliente.add(cmbMarca, null);
        pnlCliente.add(cmbTipo, null);
        pnlCliente.add(cmbSubCategoria, null);
        pnlCliente.add(cmbCategoria, null);
        pnlCliente.add(jLabel13, null);
        pnlCliente.add(txtCtdFraccion, null);
        pnlCliente.add(jcbIsFraccionado, null);
        pnlCliente.add(jLabel12, null);
        pnlCliente.add(jLabel6, null);
        pnlCliente.add(jLabel4, null);
        pnlCliente.add(jLabel11, null);
        pnlCliente.add(jLabel10, null);
        pnlCliente.add(jLabel9, null);
        pnlCliente.add(txtCosto, null);
        pnlCliente.add(txtCtdStkMinimo, null);
        pnlCliente.add(txtCodBarra, null);
        pnlCliente.add(jLabel8, null);
        pnlCliente.add(jLabel7, null);
        pnlCliente.add(jLabel5, null);
        pnlCliente.add(jLabel3, null);
        pnlCliente.add(jLabel2, null);
        pnlCliente.add(txtPrecio, null);
        pnlCliente.add(txtCtdStkMaximo, null);
        pnlCliente.add(txtDescripcion, null);
        pnlCliente.add(jLabel1, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(lblFiltra, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        //FarmaLoadCVL.loadCVLFromSP(cmbSexo, "cmbSEXO","PTOVENTA_PACIENTE.GET_SEXO", new ArrayList(), false);
        FarmaVariables.vAceptar = false;
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaDatosProducto();
        cargaOpcion();
        //FarmaUtility.moveFocus(txtDni);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            grabarProducto();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    
    private void txtNombre_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescAuxiliar);
        }        
        chkKeyPressed(e);        
    }

    private void txtApePat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecio);
        }        
        chkKeyPressed(e);        
    }

    private void txtApeMat_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCosto);
        }        
        chkKeyPressed(e);         
    }

    private void grabarProducto() {
        if(validarDatosIngresados()){
            String pDescripcion = txtDescripcion.getText().trim();
            String pDescAuxiliar = txtDescAuxiliar.getText().trim();
            String pCodBarra = txtCodBarra.getText().trim();
            String pCtdStkMinimo = txtCtdStkMinimo.getText().trim();
            String pCtdStkMaximo = txtCtdStkMaximo.getText().trim();
            String pCosto = txtCosto.getText().trim();
            String pPrecio = txtPrecio.getText().trim();
            String pCtdFraccion = txtCtdFraccion.getText().trim();
            String vIsFracc = "";
            if(jcbIsFraccionado.isSelected())
                vIsFracc = "S";
            else
                vIsFracc = "N";
            String pTipo = FarmaLoadCVL.getCVLCode("CMB_TIPO", cmbTipo.getSelectedIndex()).toString().trim();
            String pMarca = FarmaLoadCVL.getCVLCode("CMB_MARCA", cmbMarca.getSelectedIndex()).toString().trim();
            String pCategoria = FarmaLoadCVL.getCVLCode("CMB_CATEGORIA", cmbCategoria.getSelectedIndex()).toString().trim();
            String pSubCategoria = FarmaLoadCVL.getCVLCode("CMB_SUB_CATEGORIA", cmbSubCategoria.getSelectedIndex()).toString().trim();
            if(!jcbIsFraccionado.isSelected())
                pCtdFraccion = "1";
            
            String pTipoConsumo = "";
            /*if(!jcbIsFraccionado.isSelected())
                pTipoConsumo = "N";
            else*/
                
                pTipoConsumo = FarmaLoadCVL.getCVLCode("CMB_TIPO_CONSUMO", cmbTipoConsumo.getSelectedIndex()).toString().trim();
            
            try {
                DBMantenimiento.grabaPRODUCTO(pDescripcion,
                                     pDescAuxiliar,
                                     pCodBarra,
                                     pCtdStkMinimo,
                                     pCosto,
                                     pPrecio,
                                     vIsFracc,
                                     pCtdFraccion,
                                     pCtdStkMaximo,
                                     pTipo,
                                     pMarca,
                                     pCategoria,
                                     pSubCategoria ,
                                     pId,
                                     pInsert,
                                     pUpdate,
                                     pTipoConsumo);
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this,"Se grabó el producto correctamente",null);
                cerrarVentana(true);
            } catch (SQLException sqle) {
                // TODO: Add catch code
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ocurrió un error al registrar al paciente\n"+sqle.getMessage(),null);
            }
            
        }
                                        
    }
    
    public static void dateCompleteNacimiento(JTextField pJTextField, KeyEvent e) {
        try {
            String anhoBD = "19";
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                if ((pJTextField.getText().trim().length() == 2) || 
                    (pJTextField.getText().trim().length() == 5)) {
                    pJTextField.setText(pJTextField.getText().trim() + "/");
                    if (pJTextField.getText().trim().length() == 6)
                        pJTextField.setText(pJTextField.getText().trim() + 
                                            anhoBD);
                }
            }
        } catch (Exception errAnhoBD) {
            errAnhoBD.printStackTrace();
        }
    }


    private void cmbCategoria_itemStateChanged(ItemEvent itemEvent) {
        cargaSubCategoria();
    }

    private void cargaDatosProducto() {
        ArrayList param = new ArrayList();
        param.add(FarmaVariables.vCodGrupoCia);
        param.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbMarca, "CMB_MARCA","PTOVENTA_ADMIN_PROD.LISTA_MARCAS(?,?)",param, false);
        FarmaLoadCVL.loadCVLFromSP(cmbTipo, "CMB_TIPO","PTOVENTA_ADMIN_PROD.LISTA_TIPOS(?,?)",param, false);
        FarmaLoadCVL.loadCVLFromSP(cmbCategoria, "CMB_CATEGORIA","PTOVENTA_ADMIN_PROD.LISTA_CATEGORIAS(?,?)",param, false);
        param = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbTipoConsumo, "CMB_TIPO_CONSUMO","PTOVENTA_ADMIN_PROD.LISTA_TIPOS_CONSUMO",param, false);
    }
    
    private void cargaSubCategoria() {
        ArrayList param = new ArrayList();
        param.add(FarmaVariables.vCodGrupoCia);
        param.add(FarmaVariables.vCodLocal);
        String pIdCategoria = FarmaLoadCVL.getCVLCode("CMB_CATEGORIA", cmbCategoria.getSelectedIndex()).toString().trim();
        param.add(pIdCategoria);
        try {
            FarmaLoadCVL.unloadCVL(cmbSubCategoria,  "CMB_SUB_CATEGORIA");
            FarmaLoadCVL.loadCVLFromSP(cmbSubCategoria, "CMB_SUB_CATEGORIA",
                                       "PTOVENTA_ADMIN_PROD.LISTA_SUB_X_CATEG(?,?,?)", param, false,true);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }
    
    private void cargaOpcion() {
        if(pUpdate){
            loadData(pId);
        }
        else{
            if(pInsert){
                txtDescripcion.setText("");
                txtDescAuxiliar.setText("");
                txtCodBarra.setText("");
                txtCtdStkMinimo.setText("");
                txtCosto.setText("");
                txtCtdFraccion.setText("");
            }
        }
    }
    
    private void loadData(String pIdValor){
        try {
            ArrayList vDatos = new ArrayList();
            DBMantenimiento.getProducto(vDatos, pIdValor);
            if(vDatos.size()==1){
                /*
                0 p.cod_prod  || 'Ã' ||
                1 nvl(DESC_PROD,' ')  || 'Ã' ||
                2 nvl(IND_PROD_FRACCIONABLE,'N')       || 'Ã' ||
                3 nvl(trim(to_char(VAL_MAX_FRAC,'99990')),'1')         || 'Ã' ||
                4 nvl(ID_TIPO,'0')     || 'Ã' ||
                5 nvl(ID_MARCA,'0')  || 'Ã' ||
                6 nvl(ID_CATEGORIA,'0')  || 'Ã' ||
                7 nvl(ID_SUB_CATEGORIA,'0')    || 'Ã' ||
                8 nvl(DESC_PROD_AUX,' ')       || 'Ã' ||
                9 nvl(trim(to_char(CTD_STK_MIN,'99990')),' ')          || 'Ã' ||
               10 nvl(trim(to_char(CTD_STK_MAX,'99990')),' ')          || 'Ã' ||
               11 nvl(trim(to_char(VAL_PREC_VTA_PROD,'99990.99')),' ')         || 'Ã' ||
               12 nvl(trim(to_char(VAL_COSTO,'99990.99')),' ')         || 'Ã' ||
               13 nvl(COD_BARRA,' ')   
                * */
                // se puede mostrar los datos
                txtDescripcion.setText(((ArrayList)(vDatos.get(0))).get(1).toString());
                if((((ArrayList)(vDatos.get(0))).get(2).toString()).equalsIgnoreCase("S"))
                    jcbIsFraccionado.setSelected(true);
                else
                    jcbIsFraccionado.setSelected(false);
                txtCtdFraccion.setText(((ArrayList)(vDatos.get(0))).get(3).toString());
                
                FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo,"CMB_TIPO",((ArrayList)(vDatos.get(0))).get(4).toString());
                FarmaLoadCVL.setSelectedValueInComboBox(cmbMarca,"CMB_MARCA",((ArrayList)(vDatos.get(0))).get(5).toString());
                FarmaLoadCVL.setSelectedValueInComboBox(cmbCategoria,"CMB_CATEGORIA",((ArrayList)(vDatos.get(0))).get(6).toString());
                FarmaLoadCVL.setSelectedValueInComboBox(cmbSubCategoria,"CMB_SUB_CATEGORIA",((ArrayList)(vDatos.get(0))).get(7).toString());
                
                FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoConsumo,"CMB_TIPO_CONSUMO",((ArrayList)(vDatos.get(0))).get(14).toString());
                
                txtDescAuxiliar.setText(((ArrayList)(vDatos.get(0))).get(8).toString());
                txtCtdStkMinimo.setText(((ArrayList)(vDatos.get(0))).get(9).toString());
                txtCtdStkMaximo.setText(((ArrayList)(vDatos.get(0))).get(10).toString());
                txtPrecio.setText(((ArrayList)(vDatos.get(0))).get(11).toString());
                txtCosto.setText(((ArrayList)(vDatos.get(0))).get(12).toString());
                txtCodBarra.setText(((ArrayList)(vDatos.get(0))).get(13).toString());
                
                FarmaUtility.moveFocus(txtDescripcion);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
    }
    

    private boolean validarDatosIngresados(){
        String pDescripcion = txtDescripcion.getText().trim();
        String pDescAuxiliar = txtDescAuxiliar.getText().trim();
        String pCodBarra = txtCodBarra.getText().trim();
        String pCtdStkMinimo = txtCtdStkMinimo.getText().trim();
        String pCosto = txtCosto.getText().trim();
        String pCtdFraccion = txtCtdFraccion.getText().trim();
        String vIsFracc = "";
        if(jcbIsFraccionado.isSelected())
            vIsFracc = "S";
        else
            vIsFracc = "N";
        
        String pTipo = FarmaLoadCVL.getCVLCode("CMB_TIPO", cmbTipo.getSelectedIndex()).toString().trim();
        String pMarca = FarmaLoadCVL.getCVLCode("CMB_MARCA", cmbMarca.getSelectedIndex()).toString().trim();
        String pCategoria = FarmaLoadCVL.getCVLCode("CMB_CATEGORIA", cmbCategoria.getSelectedIndex()).toString().trim();
        String pSubCategoria = FarmaLoadCVL.getCVLCode("CMB_SUB_CATEGORIA", cmbSubCategoria.getSelectedIndex()).toString().trim();
        
        // Validacion de fraccionamiento 
        if(jcbIsFraccionado.isSelected()){
            if(pCtdFraccion.trim().length()>0){
                int valor = 0;
                try {
                    valor = Integer.parseInt(pCtdFraccion.trim());
                } catch (NumberFormatException nfe) {
                    FarmaUtility.showMessage(this, "La cantidad del fraccionamiento debe ser un número entero mayor o igual que 1.",txtCtdFraccion);
                    return false;
                }
            }
            else{
                FarmaUtility.showMessage(this, "Debe de ingresar la cantidad del fraccionamiento",txtCtdFraccion);
                return false;
            }
        }
        
        if(pDescripcion.trim().length()==0){
            FarmaUtility.showMessage(this, "Debe de ingresar una descripción para el producto.",txtDescripcion);
            return false;
        }
           
        return true;
    }

    private void txtDescAuxiliar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCtdStkMinimo);
        }        
        chkKeyPressed(e);  
    }

    private void txtCtdStkMinimo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCtdStkMaximo);
        }        
        chkKeyPressed(e);  
    }

    private void txtCosto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCodBarra);
        }        
        chkKeyPressed(e); 
    }

    private void txtCodBarra_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbCategoria);
        }        
        chkKeyPressed(e); 
    }

    private void cmbCategoria_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbSubCategoria);
        }        
        chkKeyPressed(e); 
    }

    private void cmbSubCategoria_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbTipo);
        }        
        chkKeyPressed(e); 
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbMarca);
        }        
        chkKeyPressed(e); 
    }

    private void cmbMarca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbTipoConsumo);
        }        
        chkKeyPressed(e); 
    }

    private void cmbTipoConsumo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescripcion);
        }        
        chkKeyPressed(e); 
    }

    private void txtCtdStkMinimo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCtdStkMinimo, e);
    }

    private void txtCtdStkMaximo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCtdStkMaximo, e);
    }

    private void txtPrecio_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecio, e);
    }

    private void txtCosto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtCosto, e);
    }

    private void txtCtdFraccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtCtdFraccion, e);
    }
}
