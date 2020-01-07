package svb.fact_electronica.pdf.bean;

import java.util.Map;

public class WrapperItemObject {
    
    private Map<String,Object> lstItemHashMap;

    public void setLstItemHashMap(Map<String, Object> lstItemHashMap) {
        this.lstItemHashMap = lstItemHashMap;
    }

    public Map<String, Object> getLstItemHashMap() {
        return lstItemHashMap;
    }
}
