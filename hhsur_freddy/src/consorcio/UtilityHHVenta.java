package consorcio;

import common.FarmaConstants;

import common.FarmaUtility;

import common.FarmaVariables;

import componentes.gs.componentes.JConfirmDialog;

import java.awt.Frame;

import javax.swing.JDialog;

import mifarma.ptoventa.centromedico.DlgADMListaPacientes;
import mifarma.ptoventa.centromedico.domain.VtaCompAtencionMedica;
import mifarma.ptoventa.centromedico.domain.VtaPedidoAtencionMedica;

import mifarma.ptoventa.centromedico.reference.ConstantsCentroMedico;

import venta.cliente.DlgBuscaClienteJuridico;
import venta.cliente.reference.VariablesCliente;

import venta.modulo_venta.DlgIngresoMedicoPedido;
import venta.modulo_venta.reference.VariablesModuloVentas;

public class UtilityHHVenta {
    public UtilityHHVenta() {
        super();
    }
    public static void ingresoDatosPedido(Frame myFrame){
        DlgIngresoMedicoPedido dlgIngMedico = new DlgIngresoMedicoPedido(myFrame, "", true);
               dlgIngMedico.setVisible(true); 
               if(FarmaVariables.vAceptar){
                   if(VariablesModuloVentas.VNUM_CMP_ASOCIADO_IN.trim().length()<=1){
                       FarmaUtility.showMessage((new JDialog()),
                                                "EL PEDIDO SERA REGISTRADO PARA EL MEDICO :"+"\n"+
                                                " CMP: " + VariablesModuloVentas.VNUM_CMP_IN+"\n"+
                                                " NOMBRES_COMPLETOS: " + VariablesModuloVentas.VDATOS_MEDICO_IN+"\n"+ 
                                                " Y Paciente :"+"\n" +
                                                " DNI: " + VariablesModuloVentas.VPacienteDni+"\n"+
                                               " Nombre: " + VariablesModuloVentas.VPacienteNombre+"\n"+
                                               " Apellidos: " + VariablesModuloVentas.VPacienteAPellidoPat+" " + VariablesModuloVentas.VPacienteAPellidoMat,
                                                null);
                   }
                   else{
                       FarmaUtility.showMessage((new JDialog()),
                                                "EL PEDIDO SERA REGISTRADO PARA EL MEDICO :"+"\n"+
                                                " CMP: " + VariablesModuloVentas.VNUM_CMP_IN+"\n"+
                                                " NOMBRES_COMPLETOS: " + VariablesModuloVentas.VDATOS_MEDICO_IN+"\n"+ 
                                                " Y Paciente :"+"\n" +
                                                " DNI: " + VariablesModuloVentas.VPacienteDni+"\n"+
                                               " Nombre: " + VariablesModuloVentas.VPacienteNombre+"\n"+
                                               " Apellidos: " + VariablesModuloVentas.VPacienteAPellidoPat+" " + VariablesModuloVentas.VPacienteAPellidoMat + "\n"+
                                                " *********************************************************************** \n"+
                                                " Médico Asociado :\n "+
                                               " CMP: " + 
                                                //VariablesModuloVentas.VNUM_CMP_ASOCIADO_IN  +
                                                "\n"+
                                               " NOMBRES_COMPLETOS: " + ""
                                                //VariablesModuloVentas.VDATOS_MEDICO_ASOCIADO_IN
                                                ,
                                                                        /*
                                                VariablesVentas.VNUM_CMP_IN
                                                VariablesVentas.VDATOS_MEDICO_IN
                                                 * */
                                                null);                
                   }

               }
               else{
                   //VariablesVentas.VNUM_CMP_IN = "";
                   //VariablesVentas.VDATOS_MEDICO_IN = "";
                   VariablesModuloVentas.limpiarDatosMedicoPaciente();
               }
    }
    
        
    public static boolean seleccionClienteComprobante(Frame myParentFrame){
        boolean vValidaIngreso = false;
        
        // Consulta si desea cambiar el dato de cliente
        //if((VariablesCliente.vDni.trim().length()+VariablesCliente.vRuc.trim().length())>0){
        if(VariablesHH.vCodigo.length()>0){    
            if(JConfirmDialog.rptaConfirmDialog(myParentFrame,"¿Desea cambiar de cliente para la venta?")){
                
                VariablesHH.vCodigo = "";
                VariablesHH.vRuc = "";
                VariablesHH.vRazonSocial = "";
                VariablesHH.vTelefono="";
                VariablesHH.vCorreo="";
                VariablesHH.vDni="";
                VariablesHH.vDireccion="";
                VariablesHH.vNombre="";
                VariablesHH.vApellidoPat="";
                VariablesHH.vApellidoMat="";
                
            }
            else{
                return true;
            }
        }
        
        // si acepta la seleccion 
        
        DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (myParentFrame,"",true);
        VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_S;
        dlgBuscaClienteJuridico.setVisible(true);   
        
        if(!FarmaVariables.vAceptar){
            VariablesHH.vCodigo = "";
            VariablesHH.vRuc = "";
            VariablesHH.vRazonSocial = "";
            VariablesHH.vTelefono="";
            VariablesHH.vCorreo="";
            VariablesHH.vDni="";
            VariablesHH.vDireccion="";
            VariablesHH.vNombre="";
            VariablesHH.vApellidoPat="";
            VariablesHH.vApellidoMat="";
            return false;
        }
        else{
           
            return true;
        }
        
        
        
    }
    
  
    public static boolean seleccionPaciente(Frame myFrame){
        DlgADMListaPacientes dlgListSoatAtencion = new DlgADMListaPacientes(myFrame,"",true,
                                                                            new VtaCompAtencionMedica(),
                                                                            new VtaPedidoAtencionMedica(),
                                                                            ConstantsCentroMedico.TIPO_BUSQUEDA_PACIENTE);
        dlgListSoatAtencion.setVisible(true);
        
        if(FarmaVariables.vAceptar)
        {
            return true;
            /*
            txtDniPaciente.setText(VariablesModuloVentas.VPacienteDni);
            txtNombrePaciente.setText(VariablesModuloVentas.VPacienteNombre);
            txtApellidoPacientePaterno.setText(VariablesModuloVentas.VPacienteAPellidoPat);
            txtApellidoPacienteMaterno.setText(VariablesModuloVentas.VPacienteAPellidoMat);
            txtNacimientoPaciente.setText(VariablesModuloVentas.VPacienteNacimiento);
            txtSexoPaciente.setText(VariablesModuloVentas.VPacienteSexo);
            */
        }
        else
        {   
            
            VariablesModuloVentas.VPacienteDni = "";
            VariablesModuloVentas.VPacienteNombre = "";
            VariablesModuloVentas.VPacienteAPellidoPat = "";
            VariablesModuloVentas.VPacienteAPellidoMat = "";
            VariablesModuloVentas.VPacienteNacimiento = "";
            VariablesModuloVentas.VPacienteSexo = "";
            VariablesModuloVentas.VPacienteSexoID="";
            
            return false;
            /*
            VariablesModuloVentas.VPacienteDni = "";
            VariablesModuloVentas.VPacienteNombre = "";
            VariablesModuloVentas.VPacienteAPellidoPat = "";
            VariablesModuloVentas.VPacienteAPellidoMat = "";
            VariablesModuloVentas.VPacienteNacimiento = "";
            VariablesModuloVentas.VPacienteSexo = "";
            VariablesModuloVentas.VPacienteSexoID="";
            txtDniPaciente.setText("");
            txtNacimientoPaciente.setText("");
            txtSexoPaciente.setText("");
            txtNombrePaciente.setText("");
            txtApellidoPacientePaterno.setText("");
            txtApellidoPacienteMaterno.setText("");
            */
        }
        
    }
}
