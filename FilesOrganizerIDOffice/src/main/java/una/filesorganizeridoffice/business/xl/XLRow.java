package una.filesorganizeridoffice.business.xl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XLRow {
    private final List<XLCell> row = new ArrayList<>();

    public void addXlCell(XLCell cell){
        row.add(cell);
    }
    public int getCellCount(){
        return row.size();
    }

    public XLCell getLastCell(){
        return row.get(row.size()-1);
    }

    public XLCell getCell(int idx){
        return row.get(idx);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (XLCell c: row) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
