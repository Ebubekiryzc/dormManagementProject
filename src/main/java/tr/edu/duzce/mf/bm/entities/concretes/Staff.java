package tr.edu.duzce.mf.bm.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.InheritedId;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "staffs")
public class Staff extends BaseEntity {
    @InheritedId
    @TableColumn(name = "individual_user_id")
    private BigDecimal individualUserId;
    @TableColumn(name = "date_of_start")
    private String dateOfStart;
    @TableColumn(name = "salary")
    private BigDecimal salary;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
