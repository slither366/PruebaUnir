package venta.modulo_venta.medico;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableComparator;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.util.Collections;
import java.util.Iterator;

import javax.swing.JTextField;

import venta.reference.UtilityPtoVenta;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.reference.VariablesPtoVenta;

public class DlgListaMedicos extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgListaMedicos.class);  
 
  private Frame myParentFrame;
  FarmaTableModel tableModelListaMedicos;
  private JPanelWhite jPanelWhite1 = new JPanelWhite();
  private GridLayout gridLayout1 = new GridLayout();
  private JPanelHeader pnlCliente = new JPanelHeader();
  private JTextFieldSanSerif txtNombreMedico = new JTextFieldSanSerif();
  private JButtonLabel btnNombre = new JButtonLabel();
  private JPanelTitle pnlRelacionCliente = new JPanelTitle();
  private JButtonLabel btnRelacion = new JButtonLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblMedicos = new JTable();
  private JLabelFunction jLabelFunction1 = new JLabelFunction();
  private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
private boolean vTipoDocAsociado = false;
    private JLabelFunction jLabel1 = new JLabelFunction();

    //private JTable myJTable;
    private JPanel jPanel1 = new JPanel();
    private JLabel lblMensajeFiltro = new JLabel();


    public DlgListaMedicos()
  {
    this(null, "", false,false);
  }

  public DlgListaMedicos(Frame parent, String title, boolean modal,boolean vTipoDocAsociado)
  {
    super(parent, title, modal);
    myParentFrame = parent;
      this.vTipoDocAsociado = vTipoDocAsociado;
     try
    {
      jbInit();

        VariablesModuloVentas.vPosNew = 0;
        VariablesModuloVentas.vPosOld = 0;
         
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(811, 713));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Lista de M\u00e9dicos");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    pnlCliente.setBounds(new Rectangle(10, 10, 770, 40));
    txtNombreMedico.setBounds(new Rectangle(95, 10, 360, 20));
    txtNombreMedico.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
                    txtNombreMedico_keyPressed(e);
                    // txtClienteJuridico_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
                    txtNombreMedico_keyReleased(e);
                    //txtClienteJuridico_keyReleased(e);
        }
      });
    btnNombre.setText("<html><center>Nombre \u00f3 CMP :</center></html>");
    btnNombre.setBounds(new Rectangle(20, 5, 65, 35));
    btnNombre.setMnemonic('n');
    btnNombre.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnClienteJuridico_actionPerformed(e);
        }
      });
    pnlRelacionCliente.setBounds(new Rectangle(10, 50, 770, 35));
    btnRelacion.setText("Relacion de Medicos ");
    btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
    btnRelacion.setMnemonic('r');
    btnRelacion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnRelacion_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(10, 85, 770, 525));
    jLabelFunction1.setBounds(new Rectangle(470, 630, 117, 19));
    jLabelFunction1.setText("[ F11 ] Seleccionar");
    
      lblF1.setBounds(new Rectangle(15, 630, 117, 19));
      lblF1.setText("[ F1 ] Filtrar");
    
      lblF12.setBounds(new Rectangle(140, 630, 117, 19));
      lblF12.setText("[ F12 ] Crear");

        jLabel1.setText("[ F3] Modificar");
        jLabel1.setBounds(new Rectangle(270, 630, 135, 20));
        jPanel1.setBounds(new Rectangle(135, 0, 625, 30));
        jPanel1.setLayout(null);
        lblMensajeFiltro.setFont(new Font("Tahoma", 3, 11));
        lblMensajeFiltro.setBounds(new Rectangle(10, 5, 580, 20));
        jLabelFunction2.setBounds(new Rectangle(665, 630, 115, 20));
    jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtNombreMedico, null);
        pnlCliente.add(btnNombre, null);
        jPanelWhite1.add(jLabel1, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblF12, null);
        jScrollPane1.getViewport().add(tblMedicos, null);
        jPanelWhite1.add(jScrollPane1, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jPanel1.add(lblMensajeFiltro, BorderLayout.CENTER);
        pnlRelacionCliente.add(jPanel1, null);
        jPanelWhite1.add(pnlRelacionCliente, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }
  
  private void initialize()
  {
    initTableListaMedicos();
    FarmaVariables.vAceptar = false;
  }
  
  private void initTableListaMedicos()
  {
    tableModelListaMedicos = new FarmaTableModel(ConstantsMedico.columnsListaMedicos, ConstantsMedico.defaultValuesListaMedicos,0);
    FarmaUtility.initSimpleList(tblMedicos,tableModelListaMedicos, ConstantsMedico.columnsListaMedicos);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    cargaListaMedicos();
    FarmaUtility.moveFocus(txtNombreMedico);
  }
  
  private void cargaListaMedicos()
  {
    try
    {
            DBMedico.listaTodosMedicos(tableModelListaMedicos);
      //FarmaUtility.ordenar(tblMedicos,tableModelListaMedicos,1,FarmaConstants.ORDEN_ASCENDENTE); 
      if(tblMedicos.getRowCount() <= 0) {
        FarmaUtility.showMessage(this, "No se encontro ningun Medico para esta Busqueda",txtNombreMedico);
        cerrarVentana(false);
      }
      else {
        FarmaGridUtils.showCell(tblMedicos, 0, 0);
        //FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 1);
      }    
    }catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurio un error al listar los medicos \n " + sql.getMessage(),txtNombreMedico);
    }
  }

  private void txtNombreMedico_keyReleased(KeyEvent e)
  {
    //SE BUSCA QUE EMPIEZA CON EL TEXTO  
    //FarmaGridUtils.buscarDescripcion(e, tblMedicos, txtNombreMedico, 1);
    
    if(tblMedicos.getRowHeight()==0&&txtNombreMedico.getText().trim().length()==0){
        clonarListadoProductos();
        lblMensajeFiltro.setText("Debe de ingresar mas de un caracter para iniciar con la búsqueda");
    }
        
    
    if(e.getKeyChar() != '+'&&
        !(
        (e.getKeyCode() == KeyEvent.VK_UP || 
         e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
        (e.getKeyCode() == KeyEvent.VK_DOWN || 
         e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
        e.getKeyCode() == KeyEvent.VK_ENTER||
        
        (e.getKeyCode()== KeyEvent.VK_F1||
        e.getKeyCode()== KeyEvent.VK_F12||
        e.getKeyCode()== KeyEvent.VK_F3||
        e.getKeyCode()== KeyEvent.VK_F11||
        e.getKeyCode()== KeyEvent.VK_ESCAPE
        )
        )
        ){
        filtroGoogle();
        }
        //log.debug("Caracter");
    else    
    if(tblMedicos.getRowCount() >= 0 && 
        tableModelListaMedicos.getRowCount() > 0 && 
        e.getKeyChar() != '+') {
        if (FarmaGridUtils.buscarDescripcion(e, tblMedicos, txtNombreMedico, 
                                             1) || 
            (e.getKeyCode() == KeyEvent.VK_UP || 
             e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
            (e.getKeyCode() == KeyEvent.VK_DOWN || 
             e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
            e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesModuloVentas.vPosNew = tblMedicos.getSelectedRow();
            if (VariablesModuloVentas.vPosOld == 0 && VariablesModuloVentas.vPosNew == 0) {
                UpdateReleaseProd(e);
                VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
            } else {
                if (VariablesModuloVentas.vPosOld != VariablesModuloVentas.vPosNew) {
                    UpdateReleaseProd(e);
                    VariablesModuloVentas.vPosOld = VariablesModuloVentas.vPosNew;
                }
            }
        }
    }


  }
  
  
  private void chkKeyPressed(KeyEvent e)
  {
   if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    cerrarVentana(false);
   }
   else if (UtilityPtoVenta.verificaVK_F11(e)){
    seleccionaMedico();
    }
      else if (UtilityPtoVenta.verificaVK_F1(e)){
       filtrar();
       }   
      else if (e.getKeyCode() == KeyEvent.VK_F3){
       modificar();
       }   
   else if (UtilityPtoVenta.verificaVK_F12(e)){
    creaMedico();
    }
   else if (e.getKeyCode() == KeyEvent.VK_ENTER){
                //e.consume();
              //busquedaMedico();
                procesoEnter(e);
          }
  }
   private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  private void txtNombreMedico_keyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e, tblMedicos, txtNombreMedico,1);
    //chkKeyPressed(e);
      
      try
      {
          
          if(
              e.getKeyChar() != '+'&&
                              !(
                              (e.getKeyCode() == KeyEvent.VK_UP || 
                               e.getKeyCode() == KeyEvent.VK_PAGE_UP) || 
                              (e.getKeyCode() == KeyEvent.VK_DOWN || 
                               e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || 
                              e.getKeyCode() == KeyEvent.VK_ENTER||
                              
                              (e.getKeyCode()== KeyEvent.VK_F1||
                              e.getKeyCode()== KeyEvent.VK_F12||
                              e.getKeyCode()== KeyEvent.VK_F3||
                              e.getKeyCode()== KeyEvent.VK_F11||
        e.getKeyCode()== KeyEvent.VK_ESCAPE)
                              )
              )
              //filtroGoogle();
              log.debug("Caracter");
          else    {
              FarmaGridUtils.aceptarTeclaPresionada(e, tblMedicos,new JTextField(), 1);
          }
              
          log.debug("Caracter: "+String.valueOf(e.getKeyChar())+"   ASCII: "+String.valueOf(e.getKeyCode()));
          
          /*if (e.getKeyCode() == KeyEvent.VK_ENTER)
          {
           procesoEnter(e);
          } else {
              chkKeyPressed(e);
          }*/
          
          chkKeyPressed(e);
          
          
      } catch (Exception exc) {
          log.error("",exc);
      } finally {
      }

  }

    private void seleccionaMedico() {
        int pos = tblMedicos.getSelectedRow();
       if(pos<0)
       FarmaUtility.showMessage(this, "Por favor de seleccionar una fila.", txtNombreMedico);
       else{
           
           // ingreso de medico del hospital
           
           VariablesModuloVentas.VNUM_CMP_IN = FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            0).trim();
           VariablesModuloVentas.VDATOS_MEDICO_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            1).trim();
           VariablesModuloVentas.VDES_REF_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            2).trim();
           VariablesModuloVentas.VID_REF_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            3).trim();

           VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            8).trim();
           VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                            pos,
                                                                            7).trim();
           
           cerrarVentana(true);     
           
           /*
           
           if(FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 3).toString().trim().length()==0||
              FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 8).toString().trim().length()==0){
               // no tiene referencia se debe solicitar ingresar
               
               DlgAdmMedicos dlgIngMedico = new DlgAdmMedicos(myParentFrame, "", true,
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 0),
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 4),
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 5),
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 6),
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 2),
               FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 3),
                    FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 8),
                    FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pos, 7));
               dlgIngMedico.setVisible(true);
               cargaListaMedicos();
               txtNombreMedico.setText(""); 
           }
           else{
               if(vTipoDocAsociado){
                   VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN = FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    0).trim();
                   VariablesModuloVentas.VDATOS_TECNOLOGO_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    1).trim();

                   VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    8).trim();
                   VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    7).trim();
                   
               }
               else{
                   VariablesModuloVentas.VNUM_CMP_IN = FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    0).trim();
                   VariablesModuloVentas.VDATOS_MEDICO_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    1).trim();
                   VariablesModuloVentas.VDES_REF_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    2).trim();
                   VariablesModuloVentas.VID_REF_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    3).trim();

                   VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    8).trim();
                   VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,
                                                                                    pos,
                                                                                    7).trim();
               }
               
               cerrarVentana(true);               
           }*/

       }
    }

    private void filtrar() {
        String pCadena = txtNombreMedico.getText().trim();
        if(pCadena.length()==0)
            cargaListaMedicos();
        else{
            //SI INGRESO UN VALOR BUSCARA LOS QUE CONTENGAN ESA PALABRA.
            try
            {
                DBMedico.listaBusquedaMedicos(tableModelListaMedicos, pCadena);
              //FarmaUtility.ordenar(tblMedicos,tableModelListaMedicos,2,FarmaConstants.ORDEN_ASCENDENTE); 
              if(tblMedicos.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro ningun Medico para esta Busqueda",txtNombreMedico);
                cerrarVentana(false);
              }
              else {
                FarmaGridUtils.showCell(tblMedicos, 0, 0);
                //FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 1);
              }    
            }catch (SQLException sql)
            {
              log.error("",sql);
              FarmaUtility.showMessage(this,"Ocurio un error al listar los medicos \n " + sql.getMessage(),txtNombreMedico);
            }

        }
    }

    private void creaMedico() {
        DlgAdmMedicos dlgIngMedico = new DlgAdmMedicos(myParentFrame, "", true);
        dlgIngMedico.setVisible(true);
        cargaListaMedicos();
        txtNombreMedico.setText("");
    }

    private void busquedaMedico() {
        // SE BUSCA POR CODIGO

        String codCMP = txtNombreMedico.getText().trim();
        if (UtilityModuloVenta.isNumerico(codCMP)) {
        ArrayList vLista = new ArrayList();
        try {
                DBMedico.getMedicoCMP(vLista,codCMP.trim());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        
        if(vLista.size()==0){
            FarmaUtility.showMessage(this, "No se econtro el médico con el CMP:"+codCMP, txtNombreMedico);
        }
        else{
            if(vLista.size()==1){
                
                VariablesModuloVentas.VNUM_CMP_IN = FarmaUtility.getValueFieldArrayList(vLista,
                                                                              0,
                                                                              0).trim();
                VariablesModuloVentas.VDATOS_MEDICO_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                              0,
                                                                              1).trim();
                VariablesModuloVentas.VDES_REF_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                              0,
                                                                              2).trim();
                VariablesModuloVentas.VID_REF_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                              0,
                                                                              3).trim();
                

                VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                 0,
                                                                                 8).trim();
                VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                 0,
                                                                                 7).trim();
                cerrarVentana(true);
                
                /*
                if(FarmaUtility.getValueFieldArrayList(vLista,0, 3).toString().trim().length()==0||
                  FarmaUtility.getValueFieldArrayList(vLista,0, 8).toString().trim().length()==0){
                    // no tiene referencia se debe solicitar ingresar
                    
                    DlgAdmMedicos dlgIngMedico = new DlgAdmMedicos(myParentFrame, "", true,
                    FarmaUtility.getValueFieldArrayList(vLista,0, 0),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 4),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 5),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 6),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 2),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 3),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 8),
                    FarmaUtility.getValueFieldArrayList(vLista,0, 7));
                    dlgIngMedico.setVisible(true);
                    cargaListaMedicos();
                    txtNombreMedico.setText(""); 
                }
                else
                ///
                if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Esta seguro de ingresar al Médico?\n" +
                    "CMP:"+FarmaUtility.getValueFieldArrayList(vLista,0, 0)+ "  \n"
                   +
                   "Nombre Completo:"+
                   FarmaUtility.getValueFieldArrayList(vLista,0, 1).toString()+" \n"+
                  "Referencia : "+
                    FarmaUtility.getValueFieldArrayList(vLista,0, 2).toString()
                )) 
                {
                    if(vTipoDocAsociado){
                        VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN = FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      0).trim();
                        VariablesModuloVentas.VDATOS_TECNOLOGO_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      1).trim();
                        VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                         0,
                                                                                         8).trim();
                        VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                         0,
                                                                                         7).trim();
                    }
                    else{
                        VariablesModuloVentas.VNUM_CMP_IN = FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      0).trim();
                        VariablesModuloVentas.VDATOS_MEDICO_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      1).trim();
                        VariablesModuloVentas.VDES_REF_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      2).trim();
                        VariablesModuloVentas.VID_REF_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      3).trim();
                        

                        VariablesModuloVentas.VCOD_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                         0,
                                                                                         8).trim();
                        VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                         0,
                                                                                         7).trim();
                        
                    }
                        
                    cerrarVentana(true);
                }*/
            }            
        }
        
        }
    }

    private void modificar() {

        /*String pCMP ,
        String pNomMedico ,
        String pApePat,
        String pApeMat,
        String pIdRef ,
        String pDesRef 
        64313Ã
        ALEX HANS VON DER HEYDE MELGAREJOÃ
        Humanidad SurÃ
        00001
        ALEX HANS Ã
        VON DER HEYDE
        Ã
        MELGAREJO
    */
        int pPos = tblMedicos.getSelectedRow();
        if(pPos>=0){
            String cmp= FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 0);
            if(cmp.equalsIgnoreCase("0")||cmp.equalsIgnoreCase("1")||cmp.equalsIgnoreCase("2")){
                txtNombreMedico.setText(""); 
                FarmaUtility.showMessage(this, "No puede modificar esta filas ya predefinidas", txtNombreMedico);
            }
            else{
                
                DlgAdmMedicos dlgIngMedico = new DlgAdmMedicos(myParentFrame, "", true,
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 0),
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 4),
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 5),
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 6),
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 2),
                FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 3),
                    FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 8),
                    FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 7));
                dlgIngMedico.setVisible(true);
                
                if(FarmaVariables.vAceptar){
                    //cargaListaMedicos();
                    txtNombreMedico.setText(FarmaUtility.getValueFieldArrayList(tableModelListaMedicos.data,pPos, 0));     
                    busquedaMedico();    
                }
            }
            
        }
    }
    // metodos para filtro google 
    private void filtroGoogle() {
        filtrarBusquedaGoogle();
    }
    
    private void clonarListadoProductos() {
        try
        {
                DBMedico.listaTodosMedicos(tableModelListaMedicos);
        }catch (SQLException sql)
        {
          log.error("",sql);
          FarmaUtility.showMessage(this,"Ocurio un error al listar los medicos \n " + sql.getMessage(),txtNombreMedico);
        }

        tableModelListaMedicos.fireTableDataChanged();
        tblMedicos.repaint();
    }
    
    private void setJTable(JTable pJTable) {
        //myJTable = pJTable;
        //txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            //FarmaUtility.setearActualRegistro(pJTable, txtProducto, 2);
            //muestraInfoProd();
        }
        //FarmaUtility.moveFocus(txtProducto);
    }


    private void filtrarBusquedaGoogle() {
        int COL_DESC_PROD = 1;
        
        String condicion = txtNombreMedico.getText().toUpperCase();
        if(!condicion.equals("") && condicion.length() > 0){
            //inicializa el listado
            clonarListadoProductos();
            //filtrar java
            ArrayList target = tableModelListaMedicos.data;        
            ArrayList filteredCollection = new ArrayList();
            
            Iterator iterator = target.iterator();
            while(iterator.hasNext()){
                ArrayList fila = (ArrayList)iterator.next();
                String descProd = fila.get(COL_DESC_PROD).toString().toUpperCase().trim();
                //if(descProd.startsWith(condicion) || descProd.endsWith(condicion)){
                if(descProd.contains(condicion)){
                    filteredCollection.add(fila);
                }
            }
            
            //limpia las tablas auxiliares                
            tableModelListaMedicos.data = filteredCollection;
            VariablesPtoVenta.vInd_Filtro = FarmaConstants.INDICADOR_S;
            tableModelListaMedicos.fireTableDataChanged();
            setJTable(tblMedicos);
            //iniciaProceso(false);
            
            
            if(tblMedicos.getRowCount()==0){
                lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
                clonarListadoProductos();
            }
            else{
                if(tblMedicos.getRowCount()==1)
                    lblMensajeFiltro.setText(tblMedicos.getRowCount()+" fila para el filtro aplicado");
                else
                    lblMensajeFiltro.setText(tblMedicos.getRowCount()+" filas para el filtro aplicado");
            }
            
        }
        else{
            clonarListadoProductos();
            lblMensajeFiltro.setText("No se encontraron datos para el filtro ingresado");
        }
            
    }
    
    private void UpdateReleaseProd(KeyEvent e) {


    }

    private void procesoEnter(KeyEvent keyEvent) {
        busquedaMedico();
    }
}
