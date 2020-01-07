select * from mae_medico
    where cod_medico = 1111111111
    
    select * from cc_medico_x_bus
    
     select distinct ME.NUM_CMP ,ME.COD_MEDICO, ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO, me.med_consorcio
        from MAE_MEDICO ME
        WHERE ME.MED_CONSORCIO = '1'
        AND me.num_cmp = '12345'
        and des_ape_medico = 'ZUÑIGA VEGA'
       
    
   select distinct ME.NUM_CMP ,ME.COD_MEDICO, ME.DES_NOM_MEDICO , ME.DES_APE_MEDICO
        from MAE_MEDICO ME
        WHERE ME.MED_CONSORCIO = '1'
        AND NUM_CMP= '12345'
        ORDER BY 3  ASC


 -----------------------------------------------------  
    --AGREGAR NUEVO MEDICO--
  ------------------------------------------------------  
 
      INSERT INTO mae_medico
      (num_cmp,flg_activo, cod_medico,des_nom_medico, des_ape_medico, des_direccion,flg_sexo, fch_nacimiento ,num_doc_iden,med_consorcio)
      VALUES
      ('11111',1,'1111111111','FREDY','ZUÑIGA VEGA','',1,'','74129984',1)
      
  ---------------------------------------------------------------
      --EDITAR MEDICO EXISTENTE--
  ---------------------------------------------------------------    
      
       UPDATE mae_medico MM
       SET mm.num_cmp = 12345,
           mm.flg_activo = 1,
           mm.cod_medico =1111111122,
           mm.des_nom_medico ='asdasds',
           mm.des_ape_medico ='ZUÑIGA VEGA',
           mm.des_direccion = 'jr nevado pancani',
           mm.flg_sexo = 0,
           mm.fch_nacimiento ='',
           mm.num_doc_iden = '12345678',
           mm.med_consorcio = '1'
       WHERE mm.cod_medico=1111111111 


  ---------------------------------------------------------------
      --ELIMINAR MEDICO EXISTENTE--
  ---------------------------------------------------------------
      
         delete from mae_medico mm
          where mm.cod_medico=1111111122
          and mm.num_cmp=12345
