package venta.receta.Utility;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import venta.modulo_venta.DlgListaProductos;
import venta.receta.Utility.SubProcesos;

public class MiWorker extends SwingWorker<String, Void>{
        
        boolean pEjecutaProceso =  false;
        DlgListaProductos dlgAuxCompra = new DlgListaProductos();
        JDialog dlgAux = new JDialog();
        JLabel lblAuxOut;
        SubProcesos subProc ;// = new SubProcesos();
        JProgressBar jpbProceso;
        int i=0;
        String pCodCia;
        String pCodLocal;
        String pNumReceta;
        public MiWorker(){
            this(null,null,null,null,null,null,null);
        }
        
        public MiWorker(JDialog dlg,DlgListaProductos dlgCompra,JLabel lblAux,JProgressBar jProgBar,
                        String pCodCia,String pCodLocal,String pNumReceta
                        ){
            dlgAux = dlg;
            dlgAuxCompra = dlgCompra;
            lblAuxOut = lblAux;
            jpbProceso = jProgBar;
            this.pCodCia = pCodCia;
            this.pCodLocal=pCodLocal;
            this.pNumReceta=pNumReceta;
            subProc  = new SubProcesos(dlgCompra);
        }
            
            
            protected String doInBackground() throws Exception {
                    
                    while(i<=100 && !isCancelled()&&operacionBD() )
                    {
                   //while(!isCancelled() && operacionBD() ){
                        
                            jpbProceso.setValue(i);
                            setProgress(i);
                            i++;
                            Thread.sleep(100);
                        
                        if(i==100){
                            if(!GlobalProceso.vEjecutaProceso.trim().equalsIgnoreCase("T")){
                                i = 1;
                            }
                        }
                            //Thread.sleep(100);
                    }
                    return "Operacion finalizada";
            }
            
            
            public void done(){
                    try {
                            lblAuxOut.setText(get());
                        if(lblAuxOut.getText().trim().equalsIgnoreCase("Operacion finalizada")){
                            //dlgAuxCompra.muestraDatosTabla();
                            dlgAux.setVisible(false);
                            GlobalProceso.vEjecutaProceso = "0";
                        }
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            }
            
            
            
            public boolean operacionBD(){
                boolean valor = true;
                if(!pEjecutaProceso){
                   pEjecutaProceso = true;
                    subProc.start();
                }
                if(GlobalProceso.vEjecutaProceso.equalsIgnoreCase("0"))
                    valor = true;
                else{
                    if(GlobalProceso.vEjecutaProceso.trim().equalsIgnoreCase("T"))
                        valor = false;
                    else
                        valor = true;
                }
                
                return valor;
            }
    } 

