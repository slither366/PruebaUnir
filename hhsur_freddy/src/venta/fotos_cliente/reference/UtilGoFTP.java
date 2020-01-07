package venta.fotos_cliente.reference;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilGoFTP {
    private static final Logger log = LoggerFactory.getLogger(UtilGoFTP.class);
   
    public static void main(String[] args) {
        /*FTPClient ftpClient = new FTPClient();
        log.info("jajaja");
        try {
           ftpClient.connect("192.168.1.9", 21);
           ftpClient.login("boris", "boris");
           ftpClient.enterLocalPassiveMode();
           ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
        log.info("COMIENZA A CARGAR");
            
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }*/
        /*
        sube_ftp("c:\\copy"+"\\"+"jaja.txt",
                    "jaja.txt",
                    "/",
                    "ftp_prueba",
                    "ftp_prueba",
                    21,
                    "192.168.1.5");
        
        baja_ftp("c:\\copy\\descarga",
                 "jaja.txt",
                 "ftp_prueba",
                 "ftp_prueba",
                 21,
                 "192.168.1.5");*/
        File dir=new File("C:\\copy\\casa"); 

        if(dir.exists()) {
        System.out.println("ya exite el directorio!!");
        }
        else{
        dir.mkdir();
        System.out.println("No existia el directorio, Pero ahora a sido creado!!");

        }
        
    /*} catch (Exception e) {
        e.printStackTrace();
        }*/
    }

    public static boolean sube_ftp(String directorioFile, 
                                      String pNombreArchivo,
                                      String directorioFtp,
                                      String usuarioFtp,
                                      String passwordFtp, 
                                      int puertoFtp,
                                      String servidorFtp){
       
             boolean flag=false;            
             FTPClient ftpClient = new FTPClient();
             try {
                ftpClient.connect(servidorFtp, puertoFtp);
                ftpClient.login(usuarioFtp, passwordFtp);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                
                File file = new File(directorioFile);
                String fileRemote = pNombreArchivo;
                ftpClient.changeWorkingDirectory(directorioFtp);
                
                InputStream inputStream = new FileInputStream(file);                     
                log.info("COMIENZA A CARGAR");
                OutputStream outputStream = ftpClient.storeFileStream(fileRemote);            
                if(outputStream!=null){
                log.info("LOG OUT OK");
                byte[] bytesIn = new byte[4096];
                int read = 0;
                 while ((read = inputStream.read(bytesIn)) != -1) {
                     outputStream.write(bytesIn, 0, read);
                 }
                 inputStream.close();
                 outputStream.close();
                 
                 boolean completed = ftpClient.completePendingCommand();
                 if (completed) {
                     log.info("carga exitosa");  
                     flag=true;                    
                 }
                }else{
                    log.info("LOG OUT ERROR");
                   flag=false; 
                }
                    
             } catch (IOException ex) {                 
                 flag=false;
                 log.error("Error : "+ex.getMessage());

             } finally {
                 try {
                     if (ftpClient.isConnected()) {
                         ftpClient.logout();
                         ftpClient.disconnect();
                     }
                 } catch (IOException ex) {
                     log.error("Error : "+ex.getMessage());
                 }
             }
             return flag;
         }
    
   

    
    public static void baja_ftp(String pRutaDestino,
                                String pFile,
                                String usuarioFtp,
                                String passwordFtp, 
                                int puertoFtp,
                                String servidorFtp){ 
        boolean flag=false;            
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(servidorFtp, puertoFtp);
            ftpClient.login(usuarioFtp, passwordFtp);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            /*
            File localfile = new File(pRutaDestino +"\\"+ "copia.txt");
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localfile));
            boolean success = ftpClient.retrieveFile("/" + pFile, outputStream);
            outputStream.close();*/
            File localfile = new File(pRutaDestino +"\\"+ pFile);
            InputStream inputStream = ftpClient.retrieveFileStream("/" + pFile);
            log.info("COMIENZA A DESCARGAR");
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localfile));
            if(outputStream!=null){
            log.info("LOG OUT OK");
            byte[] bytesIn = new byte[4096];
            int read = 0;
             while ((read = inputStream.read(bytesIn)) != -1) {
                 outputStream.write(bytesIn, 0, read);
             }
             inputStream.close();
             outputStream.close();            
            
                boolean completed = ftpClient.completePendingCommand();
                if (completed) {
                    log.info("descarga exitosa");  
                    flag=true;                    
                }
                }else{
                   log.info("LOG OUT ERROR");
                  flag=false; 
                }
            
            
            /*
            if (success){
                System.out.println("descarga de manera exitosa");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                log.error("Error : "+ex.getMessage());
            }
        }
        
    }
   
   
}
