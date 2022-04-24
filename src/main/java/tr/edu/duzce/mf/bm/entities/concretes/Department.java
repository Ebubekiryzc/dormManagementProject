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
@TableName(value = "departments")
public class Department extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;
    @TableColumn(name = "faculty_id")
    private Long facultyId;
    @TableColumn(name = "name")
    private String name;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
