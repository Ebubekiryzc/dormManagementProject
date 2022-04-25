package tr.edu.duzce.mf.bm.core.entities.concrete;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.math.BigDecimal;

@Data
@TableName(value = "user_operation_claims")
public class UserOperationClaim extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;
    @TableColumn(name="user_id")
    private Long userId;
    @TableColumn(name="operation_claim_id")
    private Long operationClaimId;


    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
