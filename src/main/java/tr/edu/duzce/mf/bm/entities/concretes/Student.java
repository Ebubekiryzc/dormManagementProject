package tr.edu.duzce.mf.bm.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "students")
public class Student extends BaseEntity {
    @TableColumn(name = "individual_user_id")
    private Long individualUserId;
    @TableColumn(name = "department_id")
    private Long departmentId;

    @TableColumn(name = "date_of_entry")
    private LocalDate dateOfEntry;
    @TableColumn(name = "block_code")
    private String blockCode;
    @TableColumn(name = "room_number")
    private Integer roomNumber;


    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
