package una.filesorganizeridoffice.business.api.xl;

import java.util.LinkedList;
import java.util.List;

public final class XLRow {
    private final List<XLCell> row = new LinkedList<>();

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

    public List<XLCell> asList() {
        return new LinkedList<>(row);
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
