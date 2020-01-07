package venta.modulo_venta.medico;

import componentes.gs.componentes.JButtonLabel;
import componentes.gs.componentes.JConfirmDialog;
import componentes.gs.componentes.JLabelFunction;
import componentes.gs.componentes.JPanelHeader;
import componentes.gs.componentes.JPanelTitle;
import componentes.gs.componentes.JPanelWhite;
import componentes.gs.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.FarmaConstants;
import common.FarmaGridUtils;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.reference.UtilityPtoVenta;

import venta.modulo_venta.medico.reference.ConstantsMedico;
import venta.modulo_venta.medico.reference.DBMedico;
import venta.modulo_venta.reference.ConstantsModuloVenta;
import venta.modulo_venta.reference.DBModuloVenta;
import venta.modulo_venta.reference.UtilityModuloVenta;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaVisitadores extends JDialog 
{
    private static final Logger log = LoggerFactory.getLogger(DlgListaVisitadores.class);  
 
  private Frame myParentFrame;
  FarmaTableModel tableModelListaTecnologos;
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
  public DlgListaVisitadores()
  {
    this(null, "", false,false);
  }

  public DlgListaVisitadores(Frame parent, String title, boolean modal,boolean vTipoDocAsociado)
  {
    super(parent, title, modal);
    myParentFrame = parent;
      this.vTipoDocAsociado = vTipoDocAsociado;
     try
    {
      jbInit();
      initialize();
    }
    catch(Exception e)
    {
      log.error("",e);
    }

  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(619, 397));
    this.getContentPane().setLayout(gridLayout1);
    this.setTitle("Lista de Visitadores");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowOpened(WindowEvent e)
        {
          this_windowOpened(e);
        }
      });
    pnlCliente.setBounds(new Rectangle(10, 10, 595, 40));
    txtNombreMedico.setBounds(new Rectangle(95, 10, 295, 20));
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
    btnNombre.setText("<html><center>Nombre \u00f3 Codigo :</center></html>");
    btnNombre.setBounds(new Rectangle(20, 5, 65, 35));
    btnNombre.setMnemonic('n');
    btnNombre.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnClienteJuridico_actionPerformed(e);
        }
      });
    pnlRelacionCliente.setBounds(new Rectangle(10, 60, 595, 25));
    btnRelacion.setText("Relacion de Visitadores ");
    btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
    btnRelacion.setMnemonic('r');
    btnRelacion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         // btnRelacion_actionPerformed(e);
        }
      });
    jScrollPane1.setBounds(new Rectangle(10, 85, 595, 225));
    jLabelFunction1.setBounds(new Rectangle(345, 330, 117, 19));
    jLabelFunction1.setText("[ F11 ] Seleccionar");
    
      lblF1.setBounds(new Rectangle(10, 330, 117, 19));
      lblF1.setText("[ F1 ] Filtrar");
    
      lblF12.setBounds(new Rectangle(175, 330, 117, 19));
      lblF12.setText("[ F12 ] Crear");
    
    jLabelFunction2.setBounds(new Rectangle(495, 330, 115, 20));
    jLabelFunction2.setText("[ ESC ] Cerrar");
    pnlCliente.add(txtNombreMedico, null);
    pnlCliente.add(btnNombre, null);
    pnlRelacionCliente.add(btnRelacion, null);
    jScrollPane1.getViewport().add(tblMedicos, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblF12, null);
        jPanelWhite1.add(jScrollPane1, null);
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
    tableModelListaTecnologos = new FarmaTableModel(ConstantsMedico.columnsListaVisitador, ConstantsMedico.defaultValuesListaVisitador,0);
    FarmaUtility.initSimpleList(tblMedicos,tableModelListaTecnologos, ConstantsMedico.columnsListaVisitador);
  }

  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    cargaListaVisitador();
    FarmaUtility.moveFocus(txtNombreMedico);
  }
  
  private void cargaListaVisitador()
  {
    try
    {
            DBMedico.listaTodosVisitador(tableModelListaTecnologos);
      //FarmaUtility.ordenar(tblMedicos,tableModelListaMedicos,1,FarmaConstants.ORDEN_ASCENDENTE); 
      if(tblMedicos.getRowCount() <= 0) {
        FarmaUtility.showMessage(this, "No se encontro ningun tecnico para esta Busqueda",txtNombreMedico);
        //cerrarVentana(false);
      }
      else {
        FarmaGridUtils.showCell(tblMedicos, 0, 0);
        FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 1);
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
    FarmaGridUtils.buscarDescripcion(e, tblMedicos, txtNombreMedico, 1);
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
   else if (UtilityPtoVenta.verificaVK_F12(e)){
    creaMedico();
    }
   else if (e.getKeyCode() == KeyEvent.VK_ENTER){
          busquedaMedico();
          }
  }
   private void cerrarVentana(boolean pAceptar) {
		FarmaVariables.vAceptar = pAceptar;
		this.setVisible(false);
		this.dispose();
	}

  private void txtNombreMedico_keyPressed(KeyEvent e)
  {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblMedicos, txtNombreMedico,1);
    chkKeyPressed(e);
  }

    private void seleccionaMedico() {
        int pos = tblMedicos.getSelectedRow();
       if(pos<0)
       FarmaUtility.showMessage(this, "Por favor de seleccionar una fila.", txtNombreMedico);
       else{
           
           if(vTipoDocAsociado){
               VariablesModuloVentas.VCOD_VISITADOR_IN = FarmaUtility.getValueFieldArrayList(tableModelListaTecnologos.data,
                                                                                pos,
                                                                                0).trim();
               VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaTecnologos.data,
                                                                                pos,
                                                                                1).trim();
               
           }
           else{
               VariablesModuloVentas.VCOD_VISITADOR_IN = FarmaUtility.getValueFieldArrayList(tableModelListaTecnologos.data,
                                                                                pos,
                                                                                0).trim();
               VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(tableModelListaTecnologos.data,
                                                                                pos,
                                                                                1).trim();
               
           }
           
           cerrarVentana(true);
       }
    }

    private void filtrar() {
        String pCadena = txtNombreMedico.getText().trim();
        if(pCadena.length()==0)
            cargaListaVisitador();
        else{
            //SI INGRESO UN VALOR BUSCARA LOS QUE CONTENGAN ESA PALABRA.
            try
            {
                DBMedico.listaBusquedaVisitador(tableModelListaTecnologos, pCadena);
              //FarmaUtility.ordenar(tblMedicos,tableModelListaMedicos,2,FarmaConstants.ORDEN_ASCENDENTE); 
              if(tblMedicos.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro ningun técnico para esta Busqueda",txtNombreMedico);
                cerrarVentana(false);
              }
              else {
                FarmaGridUtils.showCell(tblMedicos, 0, 0);
                FarmaUtility.setearActualRegistro(tblMedicos, txtNombreMedico, 1);
              }    
            }catch (SQLException sql)
            {
              log.error("",sql);
              FarmaUtility.showMessage(this,"Ocurio un error al listar los técnicos \n " + sql.getMessage(),txtNombreMedico);
            }

        }
    }

    private void creaMedico() {
        DlgAdmVisitador dlgIngMedico = new DlgAdmVisitador(myParentFrame, "", true);
        dlgIngMedico.setVisible(true);
        cargaListaVisitador();
        txtNombreMedico.setText("");
    }

    private void busquedaMedico() {
        // SE BUSCA POR CODIGO

        String codCMP = txtNombreMedico.getText().trim();
        if (UtilityModuloVenta.isNumerico(codCMP)) {
        ArrayList vLista = new ArrayList();
        try {
                DBMedico.getVisitadorDNI(vLista,codCMP.trim());
        } catch (SQLException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        
        if(vLista.size()==0){
            FarmaUtility.showMessage(this, "No se econtro el técnico con el DNI:"+codCMP, txtNombreMedico);
        }
        else{
            if(vLista.size()==1){
                if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Esta seguro de ingresar al Técnico?\n" +
                    "CMP:"+FarmaUtility.getValueFieldArrayList(vLista,0, 0)+ "  \n"
                   +
                   "Nombre Completo:"+
                   FarmaUtility.getValueFieldArrayList(vLista,0, 1).toString()
                )) 
                {
                    if(vTipoDocAsociado){
                        VariablesModuloVentas.VCOD_VISITADOR_IN = FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      0).trim();
                        VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      1).trim();
                        
                    }
                    else{
                        VariablesModuloVentas.VCOD_VISITADOR_IN = FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      0).trim();
                        VariablesModuloVentas.VNOMBRE_VISITADOR_IN= FarmaUtility.getValueFieldArrayList(vLista,
                                                                                      0,
                                                                                      1).trim();
                        
                    }
                        
                    cerrarVentana(true);
                }
            }            
        }
        
        }
    }
}
