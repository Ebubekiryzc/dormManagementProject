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
@TableName(value = "individual_users")
public class IndividualUser extends BaseEntity {
    @TableColumn(name="user_id")
    private BigDecimal userId;
    @TableColumn(name = "gender_id")
    private BigDecimal genderId;

    @TableColumn(name = "first_name")
    private String firstName;
    @TableColumn(name = "last_name")
    private String lastName;
    @TableColumn(name = "day_off_limit")
    private BigDecimal dayOffLimit;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }

}
