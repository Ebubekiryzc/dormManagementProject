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
@TableName(value = "students")
public class Student extends BaseEntity {
    @InheritedId
    @TableColumn(name = "individual_user_id")
    private BigDecimal individualUserId;
    @TableColumn(name = "department_id")
    private BigDecimal departmentId;

    @TableColumn(name = "date_of_entry")
    private String dateOfEntry;
    @TableColumn(name = "block_code")
    private String blockCode;
    @TableColumn(name = "room_number")
    private BigDecimal roomNumber;


    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
