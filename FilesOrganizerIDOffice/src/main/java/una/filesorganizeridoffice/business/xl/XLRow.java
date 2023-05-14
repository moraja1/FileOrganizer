package una.filesorganizeridoffice.business.xl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XLRow {
    private final Map<Integer, Object> information = new HashMap<>();

    public void addInfo(Integer column, Object info){
        information.put(column, info);
    }

    public Object getInfo(Integer column){
        return information.get(column);
    }
}
