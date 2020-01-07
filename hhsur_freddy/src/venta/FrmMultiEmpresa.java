package venta;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.DlgLogin;
import common.FarmaConnection;
import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.reference.ConstantsPtoVenta;
import venta.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.DBMultiEmpresa;
import venta.reference.UtilityPtoVenta;

public class FrmMultiEmpresa extends JFrame
{
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(FrmMultiEmpresa.class);
    FarmaTableModel tableModel;
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrLocales = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JButtonLabel btnListaLocales = new JButtonLabel();
    private JTable tblListaLocales = new JTable();
    private JLabelFunction lblFuncion1 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public FrmMultiEmpresa()
    {
        try
        {   cargaVariablesBD();
            jbInit();
            initialize();
        }
        catch(Exception e)
        {   log.error("",e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception
    {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(625, 327));
        this.setTitle("Locales");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowOpened(WindowEvent e)
            {   this_windowOpened(e);
            }
            
            public void windowClosing(WindowEvent e)
            {   this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 595, 20));
        scrLocales.setBounds(new Rectangle(10, 30, 595, 225));
        tblListaLocales.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {   tblListaLocales_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(490, 260, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(370, 260, 110, 20));
        lblF11.setText("[ F11 ] Aceptar");
        btnListaLocales.setText("Lista Locales");
        btnListaLocales.setBounds(new Rectangle(5, 0, 95, 20));
        btnListaLocales.setMnemonic('L');
        btnListaLocales.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {   btnListaLocales_keyPressed(e);
            }
        });
        btnListaLocales.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   btnListaLocales_actionPerformed(e);
            }
        });
        lblFuncion1.setBounds(new Rectangle(15, 260, 130, 20));
        lblFuncion1.setText("[ F1] Administraci\u00f3n");
        lblFuncion1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    lblFuncion1_mouseClicked(e);
                }
            });
        jContentPane.add(lblFuncion1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        scrLocales.getViewport().add(tblListaLocales, null);
        jContentPane.add(scrLocales, null);
        pnlTitle1.add(btnListaLocales, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    
    private void initialize()
    {
        FarmaVariables.vAceptar = false;
        FarmaVariables.vEconoFar_Matriz = false;
        muestraLogin();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */
    
    private void initTable()
    {   tableModel = new FarmaTableModel(ConstantsPtoVenta.columnsListaLocalMulti,ConstantsPtoVenta.defaultValuesListaLocalMulti,0);
        FarmaUtility.initSimpleList(tblListaLocales,tableModel,ConstantsPtoVenta.columnsListaLocalMulti);
        cargaListaLocales();
    }

    private void cargaListaLocales()
    {
        try
        {   
            DBMultiEmpresa.cargaListaLocalesEmpresa(tableModel);
            //FarmaUtility.ordenar(tblListaLocales,tableModel,0,FarmaConstants.ORDEN_ASCENDENTE);
        }
        catch(SQLException sql)
        {   log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurrió un error al listar los locales : \n" + sql.getMessage(),btnListaLocales);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    private void this_windowOpened(WindowEvent e)
    {   FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaLocales);
    }

    private void this_windowClosing(WindowEvent e)
    {   FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void tblListaLocales_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    private void btnListaLocales_actionPerformed(ActionEvent e)
    {   FarmaUtility.moveFocusJTable(tblListaLocales);
    }
    
    private void btnListaLocales_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e)
    {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales,null,0);
        
        if(UtilityPtoVenta.verificaVK_F11(e)||e.getKeyCode() == KeyEvent.VK_ENTER)
        {   
            funcionF11();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
        /**
        * Nueva Función
        * Autor:   Luis Reque Orellana
        * Fecha:   13/02/2007
        * */
        if(UtilityPtoVenta.verificaVK_F1(e))
        {   funcionF1();
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {   FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    void muestraLogin()
    {
        //if ( readFileProperties() )
       /* if(!validaIpPc())
        {   FarmaUtility.showMessage(this,"Error al obtener la IP de la PC.", null);
            System.exit(0);
        }*/
        log.debug("EN MATRIZ");
        DlgLogin dlgLogin = new DlgLogin(this,"Acceso Ptoventa - Matriz",true);
        dlgLogin.setVisible(true);
        if (!FarmaVariables.vAceptar)
        {   FarmaConnection.closeConnection();
            System.exit(0);
        }
    }

    private boolean validaIpPc()
    {   FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
        if(FarmaVariables.vIpPc.trim().length() == 0)
            return false;
        return true;
    }
    
    private void cargaVariablesBD()
    {
        //    FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
        //FarmaVariables.vClaveBD = ConstantsPtoVenta.CLAVE_BD;
        //FarmaVariables.vSID = ConstantsPtoVenta.SID;
        FarmaVariables.vPUERTO = ConstantsPtoVenta.PUERTO;
    }

    private void funcionF11()
    {   
        
        int pos = tblListaLocales.getSelectedRow();
        
        if(pos>=0){
            cargaLocal();
            //this.setVisible(false);
            FrmEconoFar frame = new FrmEconoFar();
            FarmaUtility.centrarVentana(frame);
            frame.setVisible(true);
            //this.dispose();    
        }
        else{
            FarmaUtility.showMessage(this, "Debe seleccionar un local", tblListaLocales);
        }
        
    }
    
    /**Nuevo método para mostrar el Frame
    * Autor:   Luis Reque
    * Fecha:   13/02/2007
    * */
    private void funcionF1()
    {   FrmAdmMultiEmpresa frame = new FrmAdmMultiEmpresa();
        FarmaUtility.centrarVentana(frame);
        frame.setVisible(true);
    }

    private void cargaLocal()
    {   
        FarmaVariables.vCodCia   = tblListaLocales.getValueAt(tblListaLocales.getSelectedRow(),3).toString().trim();
        FarmaVariables.vCodLocal = tblListaLocales.getValueAt(tblListaLocales.getSelectedRow(),4).toString().trim();
        log.debug("FarmaVariables.vCodCia=" + FarmaVariables.vCodCia);
        log.debug("FarmaVariables.vCodLocal=" + FarmaVariables.vCodLocal);
    }

    private void lblFuncion1_mouseClicked(MouseEvent e) {
        funcionF1();
    }
}
