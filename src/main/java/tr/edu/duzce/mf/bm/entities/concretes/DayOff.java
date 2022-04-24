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
@TableName(value = "day_offs")
public class DayOff extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;
    @TableColumn(name = "individual_user_id")
    private Long individualUserId;
    @TableColumn(name = "date_of_start")
    private LocalDate dateOfStart;
    @TableColumn(name = "date_of_end")
    private LocalDate dateOfEnd;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
