package tr.edu.duzce.mf.bm.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "lnk_staff_tasks")
public class StaffTask extends BaseEntity {
    @Id
    @TableColumn(name="id")
    private Long id;
    @TableColumn(name = "staff_id")
    private Long staffId;
    @TableColumn(name = "task_id")
    private Long taskId;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
