package consorcio.reportes;

import common.FarmaConstants;
import common.FarmaUtility;

import componentes.gs.worker.JDialogProgress;

import consorcio.reportes.reference.DBReportesAtencion;

import java.awt.Frame;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesoPlanillaHH extends JDialogProgress{
    String FechaInicio, FechaFin;
    private static final Logger log = LoggerFactory.getLogger(DlgProcesoPlanillaHH.class);
    
      private static Frame myParentFrame;
      
    public DlgProcesoPlanillaHH() {
        super();
    }


    public DlgProcesoPlanillaHH(Frame parent, String title, boolean modal,String FechaInicio,String FechaFin)
    {
    super(parent, title, modal);
      myParentFrame= parent;
      this.FechaInicio = FechaInicio;
        this.FechaFin = FechaFin;
    }
    

    @Override
    public void ejecutaProceso() {


        try {
            DBReportesAtencion.proceso_rtp_planilla(FechaInicio, FechaFin);
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            e.printStackTrace();
        }
    }
}
