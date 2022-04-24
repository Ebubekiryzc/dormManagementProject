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
@TableName(value = "tasks")
public class Task extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;
    @TableColumn(name = "task")
    private String task;
    @TableColumn(name = "description")
    private String description;
    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
