package tr.edu.duzce.mf.bm.entities.concretes;

import jakarta.json.bind.annotation.JsonbVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.entities.abstracts.BaseEntity;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "Genders")
public class Gender extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private BigDecimal id;
    @TableColumn(name = "name")
    private String name;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
