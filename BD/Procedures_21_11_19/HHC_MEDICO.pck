create or replace package HHC_MEDICO is
TYPE FarmaCursor IS REF CURSOR;
FUNCTION F_LISTA_MED(cCodGrupoCia_in IN CHAR,
                     cCodLocal_in IN CHAR,
                     cEstado IN CHAR)
 RETURN FarmaCursor;

FUNCTION F_LISTA_CONSUL(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                            varCMP IN VARCHAR)
 RETURN FarmaCursor;
 
 FUNCTION F_LISTA_ESPEC(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR)
 RETURN FarmaCursor;
 
 FUNCTION F_LISTA_CONSUL_DISP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           vEspec IN VARCHAR)
 RETURN FarmaCursor;
 
 PROCEDURE P_GRABA_CONSUL_MEDI(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                         vNumCmp IN VARCHAR, vCodMed IN VARCHAR, vIdConsul IN VARCHAR,
                         vIdBus IN VARCHAR, vBus IN VARCHAR);
                         
 PROCEDURE P_ELIMINA_CONSUL_MEDI(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                          vCodMed IN VARCHAR, vIdBus IN VARCHAR);
                          
                    
                       
end HHC_MEDICO;
/
create or replace package body HHC_MEDICO is

FUNCTION F_LISTA_MED(cCodGrupoCia_in IN CHAR,
                     cCodLocal_in IN CHAR,
                     cEstado IN CHAR)
 RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
      select rownum||med.num_cmp || 'Ã' || med.des_ape_medico || 'Ã' || med.des_nom_medico || 'Ã' ||med.cod_medico 
      from (
          select distinct ME.NUM_CMP ,ME.COD_MEDICO, ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO
        from MAE_MEDICO ME
        WHERE 1=1--ME.MED_CONSORCIO = '1'
        AND ME.FLG_ACTIVO = cEstado
        ORDER BY 3  ASC
      ) med;
    
    RETURN curDet;
  END;
  
-------------------------
FUNCTION F_LISTA_CONSUL(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           varCMP IN VARCHAR)
 RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
          select   CM.BUS || 'Ã' || CM.ID_BUS
          from MAE_MEDICO ME, CC_MEDICO_X_BUS CM, CC_BUS CB, CC_CONSULTORIO CC
          WHERE ME.COD_MEDICO = CM.COD_MEDICO
          AND CM.ID_BUS = CB.ID_BUS
          AND CB.ID_CONSULTORIO = CC.ID_CONSULTORIO
          AND CC.ESTADO = '1'
          AND CM.NUM_CMP = ME.NUM_CMP
          AND CM.NUM_CMP = varCMP
         ;
          
    
    RETURN curDet;
  END;
  
-----------------------
FUNCTION F_LISTA_ESPEC(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR)
 RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
         SELECT   CC.DESCRIPCION || 'Ã' || CC.ID_CONSULTORIO FROM CC_CONSULTORIO CC;
    
    RETURN curDet;
  END;
  

 
-----------------------------
FUNCTION F_LISTA_CONSUL_DISP(cCodGrupoCia_in IN CHAR,
                           cCodLocal_in IN CHAR,
                           vEspec IN VARCHAR)
 RETURN FarmaCursor
  IS
    curDet FarmaCursor;
  BEGIN
    OPEN curDet FOR
           SELECT  CB.BUS || 'Ã' || CB.ID_BUS 
           FROM CC_CONSULTORIO CO,CC_BUS CB
           WHERE CO.ID_CONSULTORIO = CB.ID_CONSULTORIO
           AND CO.ESTADO = 1
           AND CO.ID_CONSULTORIO= CB.ID_CONSULTORIO
           AND (CO.Descripcion=vEspec/*cIdConsultorio OR 'VT'=cFilView*/)
            ;
    
    RETURN curDet;
  END;
  
  -----------------------------
  PROCEDURE P_GRABA_CONSUL_MEDI(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                         vNumCmp IN VARCHAR, vCodMed IN VARCHAR, vIdConsul IN VARCHAR,
                         vIdBus IN VARCHAR, vBus IN VARCHAR)

  IS
    cadena varchar2(3000);
    vCoincidencia number;
  BEGIN
    
    select count(1) 
    into vCoincidencia
    from CC_MEDICO_X_BUS
    where num_cmp =vNumCmp and id_bus=vIdBus;
    
    if vCoincidencia = 0 then 
      INSERT INTO CC_MEDICO_X_BUS
      (num_cmp, cod_medico,id_consultorio, id_bus, bus)
      VALUES
      (vNumCmp,vCodMed,vIdConsul,vIdBus, vBus);
    else
      raise_application_error
      (-20010,'Error');
    --
   end if;
END;
 ------
 PROCEDURE P_ELIMINA_CONSUL_MEDI(cCodGrupoCia_in IN CHAR,cCodLocal_in IN CHAR,
                          vCodMed IN VARCHAR, vIdBus IN VARCHAR)

  IS
    cadena varchar2(3000);
    vCoincidencia number;

  BEGIN
    select count(1) 
    into vCoincidencia
    from CC_MEDICO_X_BUS
    where cod_medico =vCodMed 
    and id_bus=vIdBus;  
  
    if  (vCoincidencia = 1) then
      delete from cc_medico_x_bus
      where cod_medico=vCodMed
      and id_Bus=vIdBus;
    else
      raise_application_error
      (-20020,'Error');
      end if;
END; 
 -----------------------------------------------
  
  ---------------------------
    --AGREGAR NUEVO MEDICO--
  --------------------------
  
 end HHC_MEDICO;
/
