    select *
   -- into vCoincidencia
    from MAE_MEDICO
    where cod_medico =0000094162 
    and id_bus=4564;  
    
    select count(1) 
   -- into vCoincidencia
    from CC_MEDICO_X_BUS
    where num_cmp =58000 and id_bus=4;
    
    SELECT * FROM MAE_MEDICO
    WHERE MED_CONSORCIO= '1'
    SELECT * FROM CC_MEDICO_X_BUS

   ---------------------------------
    ALTER TABLE MAE_MEDICO 
    ADD MED_CONSORCIO CHAR(1) DEFAULT 0
   ---------------------------------
    UPDATE  MAE_MEDICO MM
    SET MM.MED_CONSORCIO= '1'
    where cod_medico in (
      select distinct cod_medico 
      from CC_MEDICO_X_BUS
    )
    
    --------------------------------
    SELECT * FROM mae_medico
    where des_ape_medico = '.'
    
    select distinct ME.NUM_CMP ,ME.COD_MEDICO, ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO
        from MAE_MEDICO ME
        WHERE ME.MED_CONSORCIO = '1'
        ORDER BY 3  ASC
        
         select count(*)
         from mae_medico
         where MED_CONSORCIO= 1
    ------------------------------------------------
    
    
    select   CM.BUS , CM.ID_BUS
          from MAE_MEDICO ME, CC_MEDICO_X_BUS CM, CC_BUS CB, CC_CONSULTORIO CC
          WHERE ME.COD_MEDICO = CM.COD_MEDICO
          AND CM.ID_BUS = CB.ID_BUS
          AND CB.ID_CONSULTORIO = CC.ID_CONSULTORIO
          AND CC.ESTADO = '1'
          AND CM.NUM_CMP = ME.NUM_CMP
          AND me.Cod_Medico =1111111122
    
 
