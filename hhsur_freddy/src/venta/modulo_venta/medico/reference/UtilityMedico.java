package venta.modulo_venta.medico.reference;

import common.FarmaDBUtility;

import java.awt.Frame;

import javax.swing.JDialog;

import common.FarmaUtility;
import common.FarmaVariables;

import java.util.ArrayList;

import javax.swing.JCheckBox;

import  venta.modulo_venta.reference.VariablesModuloVentas;
import venta.modulo_venta.DlgIngresoMedicoPedido;
import venta.modulo_venta.DlgResumenPedido;
import venta.modulo_venta.medico.DlgDatosPacienteAtencion;

public class UtilityMedico {
    public UtilityMedico() {
        super();
    }
    
    public static void muestraMedicoPedido(Frame myParentFrame){
        DlgIngresoMedicoPedido dlgIngMedico = new DlgIngresoMedicoPedido(myParentFrame, "", true);
        dlgIngMedico.setVisible(true); 
        if(FarmaVariables.vAceptar){
            if(VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN.trim().length()<=1){
                FarmaUtility.showMessage((new JDialog()),
                                         "EL PEDIDO SERA REGISTRADO PARA EL MEDICO :"+"\n"+
                                         " CMP: " + VariablesModuloVentas.VNUM_CMP_IN+"\n"+
                                         " NOMBRES_COMPLETOS: " + VariablesModuloVentas.VDATOS_MEDICO_IN+"\n"+ 
                                         " Y Paciente :"+"\n" +
                                         " DNI: " + VariablesModuloVentas.VPacienteDni+"\n"+
                                        " Nombre: " + VariablesModuloVentas.VPacienteNombre+"\n"+
                                        " Apellidos: " + VariablesModuloVentas.VPacienteAPellidoPat+" " + VariablesModuloVentas.VPacienteAPellidoMat+"\n"+
                                         "Visitador Asociado : "+VariablesModuloVentas.VNOMBRE_VISITADOR_IN,
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
                                        " CMP: " + VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN+"\n"+
                                        " NOMBRES_COMPLETOS: " + VariablesModuloVentas.VDATOS_TECNOLOGO_IN+"\n"+
                                                "Visitador Asociado : "+VariablesModuloVentas.VNOMBRE_VISITADOR_IN
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
    
    
    
    public static String getIdCheckBox(JCheckBox jc){
        if(jc.isSelected())
            return "S";
        else
            return "N";
    }
    
    public static void operaDetallePedidoEnfermera(String pNumPedOrigen,
                                    String pTipoDos,
                                    Frame MyParentFrame) {
        if(pTipoDos.trim().equalsIgnoreCase("V")){
            DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(MyParentFrame,"",true,
                                                                     false,
                                                                     pNumPedOrigen);
            //dlgResumenPedido.setFrameEconoFarFerreteria(MyParentFrame);
            dlgResumenPedido.setVisible(true);
            /*if(FarmaVariables.vAceptar){
                cerrarVentana(true);
            } */           
        }
        else{
            if(pTipoDos.trim().equalsIgnoreCase("D")){
                DlgDatosPacienteAtencion dlgResumenPedido = new DlgDatosPacienteAtencion(MyParentFrame,"",true,
                                                                         pNumPedOrigen);
                //dlgResumenPedido.setFrameEconoFarFerreteria(MyParentFrame);
                dlgResumenPedido.setVisible(true);
               /* if(FarmaVariables.vAceptar){
                    cerrarVentana(true);
                }*/                            
            }

        }
    }

}
