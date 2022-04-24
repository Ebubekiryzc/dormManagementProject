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
@TableName(value = "individual_users")
public class IndividualUser extends BaseEntity {
    @Id
    @TableColumn(name="user_id")
    private Long userId;
    @TableColumn(name = "gender_id")
    private Long genderId;

    @TableColumn(name = "first_name")
    private String firstName;
    @TableColumn(name = "last_name")
    private String lastLame;
    @TableColumn(name = "day_off_limit")
    private Integer dayOffLimit;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }

}
