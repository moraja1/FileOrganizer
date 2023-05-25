package una.filesorganizeridoffice.business.xl.annotations;

import java.lang.annotation.Repeatable;

@Repeatable(XLCellSetValue.class)
public @interface XLCellColumn {
    public String processOf();
    public String column();
}
