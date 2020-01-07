package venta.reportes.reference;


import java.util.*;
import java.io.*;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import java.util.Iterator;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.record.BottomMarginRecord.*;

import java.io.*;


public class setm {
    static final double LeftMargin = 1.25;
    static final double RightMargin = 1.25;
    static final short TopMargin = 1;
    static final short BottomMargin = 1;

    public setm() {
    try {
        String dataDir = "D:\\word\\";
    FileInputStream fis = new FileInputStream(dataDir+"sample.doc");
    // POIFSFileSystem fs = new POIFSFileSystem(fis);
    HWPFDocument doc = new HWPFDocument(fis);
    RecordInputStream r = new RecordInputStream(fis);

    BottomMarginRecord bm = new BottomMarginRecord(r);
    TopMarginRecord tm = new TopMarginRecord(r);
    RightMarginRecord rm = new RightMarginRecord(r);
    LeftMarginRecord lm = new LeftMarginRecord(r);

    bm.setMargin(BottomMargin);
    tm.setMargin(TopMargin);
    lm.setMargin(LeftMargin);
    rm.setMargin(RightMargin);

    } catch (Exception e) {
    e.printStackTrace();
    }
    }
    
        public static void main(String[] args) {

        setm ob = new setm();

        }
    }