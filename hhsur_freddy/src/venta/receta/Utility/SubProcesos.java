package venta.receta.Utility;

import venta.receta.Utility.GlobalProceso;
import venta.receta.Utility.MiWorker;

import venta.modulo_venta.DlgListaProductos;

import common.FarmaConstants;


public class SubProcesos extends Thread {
   private int tiempoInactividad;
   String pNombre = "";
   boolean indTerminoProceso = false;
   MiWorker mWork;
   DlgListaProductos dlgAux;
   
   // asignar nombre a subproceso, llamando al constructor de la superclase

    /**
     * @param dlgCompra
     */
    public SubProcesos(DlgListaProductos dlgCompra)
   {
       pNombre = "a";
       dlgAux = dlgCompra;
   }        
    
   // el método run es el código a ejecutar por el nuevo subproceso
   public void run()
   { 
     try {
          if(indTerminoProceso){
              Thread.sleep(0);
          }
          operaproceso(pNombre);
      } 
      // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
      catch ( InterruptedException excepcion ) {
         excepcion.printStackTrace();
      }
      // imprimir nombre del subproceso
      System.err.println(" termino su inactividad" );
       //mWork.
      
   } 
   
   public void operaproceso(String pNombre_2){
       /*
       SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
       SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
       */
       try {
           
               System.err.println("inicio proceso prueba");
               //DBVentas.cargaListaProductosVenta(VariablesVentas.tableModelListaGlobalProductos);    
               //Collections.sort(VariablesVentas.tableModelListaGlobalProductos.data,new FarmaTableComparator(2,true));
               GlobalProceso.vEjecutaProceso = "S";
               dlgAux.procesaReceta();
               try {
                   Thread.sleep(1000*2);
               } catch (InterruptedException e) {
               }
               //dlgAux.iniciaListadoPantalla(); 
               GlobalProceso.vEjecutaProceso = "T";
               System.err.println("fin proceso prueba");
               indTerminoProceso = true;
           
       }
       //catch (SQLException e) {
       catch (Exception e) {
           e.printStackTrace();
           indTerminoProceso = true;
       }
       finally {
           indTerminoProceso = true;
       }
   }
} 