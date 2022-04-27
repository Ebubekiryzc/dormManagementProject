package tr.edu.duzce.mf.bm.entities.concretes;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "day_offs")
public class DayOff extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private BigDecimal id;
    @TableColumn(name = "user_id")
    private BigDecimal userId;
    @TableColumn(name = "date_of_start")
    private String dateOfStart;
    @TableColumn(name = "date_of_end")
    private String dateOfEnd;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
