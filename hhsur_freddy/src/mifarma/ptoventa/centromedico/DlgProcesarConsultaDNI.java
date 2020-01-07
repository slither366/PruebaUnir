package mifarma.ptoventa.centromedico;


import com.gs.mifarma.worker.JDialogProgress;
import common.FarmaUtility;

import dental.DlgProcesarConsultaRUC;

import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import svb.consulta_ruc.Consulta;

public class DlgProcesarConsultaDNI  extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarConsultaDNI.class);
    private DlgADMDatosPaciente dlgDatos;

    public DlgProcesarConsultaDNI() {
        super();
    }
    
    public DlgProcesarConsultaDNI(Frame frame, String string, boolean b,DlgADMDatosPaciente dlg) {
        super(frame, string, b);
        dlgDatos = dlg;
        
    }

    
    public void ejecutaProceso() {
        realizaProceso();
    }
    
    private void realizaProceso() {
        ///FarmaUtility.showMessage(new JDialog(), "Antes del cambio", null);
        
        if(dlgDatos.txtNumDoc.getText().trim().length()>0&&dlgDatos.txtNumDoc.getText().trim().length()==8){
            //EjecutarConsultaRuc ejecutar = new EjecutarConsultaRuc();
            //Compania compania = ejecutar.iniciarConsulta(dlgDatos.txtDni.getText());
            
            String ruc = dlgDatos.txtNumDoc.getText();
            
            Map dictionary = new HashMap();
            
            dictionary = Consulta.DevolverInfo(ruc);
            
            if(dictionary.get("codigo_respuesta").equals("0"))
            {
            if(ruc.length()==8)
            {
               System.out.println(dictionary.get("descripcion_respuesta"));
               System.out.println(dictionary.get("nombres"));
               System.out.println(dictionary.get("apellido_paterno"));
               System.out.println(dictionary.get("apellido_materno"));
               
               dlgDatos.txtNombre.setText((String)dictionary.get("nombres"));
               dlgDatos.txtApePaterno.setText((String)dictionary.get("apellido_paterno"));
               dlgDatos.txtApeMaterno.setText((String)dictionary.get("apellido_materno"));
               
            }else
            {
               System.out.println(dictionary.get("descripcion_respuesta"));
               System.out.println(dictionary.get("razon_social"));
               System.out.println(dictionary.get("domicilio_fiscal"));
               System.out.println(dictionary.get("contribuyente_estado"));
            }
            }else
            {
            System.out.println("La consulta presento ERRORES ");
            }
            
            //FarmaUtility.showMessage(new JDialog(), "compania.getRazonSocial() "+compania.getRazonSocial(), null);
            /*System.out.println("Fin Ruc: "+compania.getRuc());
            System.out.println("Fin TipoContribuyente: "+compania.getTipoContribuyente());
            System.out.println("Fin TipoDocumento: "+compania.getTipoDocumento());
            System.out.println("Fin NombreComercial: "+compania.getNombreComercial());
            System.out.println("Fin FechaInscripcion: "+compania.getFechaInscripcion());
            System.out.println("Fin FechaInicio: "+compania.getFechaInicio());
            System.out.println("Fin EstadoContribuyente: "+compania.getEstadoContribuyente());
            System.out.println("Fin CondicionContribuyente: "+compania.getCondicionContribuyente());
            System.out.println("Fin DireccionFiscal: "+compania.getDireccionFiscal());
            System.out.println("Fin RazonSocial: "+compania.getRazonSocial());
            System.out.println("Fin NroIntentos: " + compania.getNroIntentos());*/
            
            /*
            dlgDatos.txtRazonSocial.setText(compania.getRazonSocial());
            dlgDatos.txtDireccion.setText(compania.getDireccionFiscal());    
            */
        }
        //else
        //FarmaUtility.showMessage(new JDialog(), "Debe de ingresar el número de DNI.", null);
        
    }

}

