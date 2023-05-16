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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (XLCell c: row) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
