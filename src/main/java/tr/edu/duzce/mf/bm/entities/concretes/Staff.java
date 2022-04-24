package tr.edu.duzce.mf.bm.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "staffs")
public class Staff extends BaseEntity {
    @Id
    @TableColumn(name = "individual_user_id")
    private BigDecimal individualUserId;
    @TableColumn(name = "date_of_start")
    private LocalDate dateOfStart;
    @TableColumn(name = "salary")
    private Integer salary;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
