package view;

import java.util.HashMap;
import java.util.Map;
import model.PhieuDatVe;

public class SharedData {
    public static final Map<String, PhieuDatVe> mockPhieuDat = new HashMap<>();
    public static int phieuIdCounter = 1000;
    public static String directSaleSearchKey = null;
}
