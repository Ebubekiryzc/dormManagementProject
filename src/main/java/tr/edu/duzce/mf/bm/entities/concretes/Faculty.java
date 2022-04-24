package tr.edu.duzce.mf.bm.entities.concretes;

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
@TableName(value = "faculties")
public class Faculty extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;
    @TableColumn(name = "name")
    private String name;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}