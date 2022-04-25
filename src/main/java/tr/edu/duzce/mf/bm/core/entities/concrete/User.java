package tr.edu.duzce.mf.bm.core.entities.concrete;

import lombok.*;
import tr.edu.duzce.mf.bm.core.entities.abstracts.BaseEntity;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value="users")
public class User extends BaseEntity {
    @Id
    @TableColumn(name = "id")
    private Long id;

    @TableColumn(name="username")
    private String username;

    @TableColumn(name="password_hash")
    private byte[] passwordHash;

    @TableColumn(name="password_salt")
    private byte[] passwordSalt;

    @TableColumn(name="status")
    private boolean status;

    @Override
    public String toString() {
        return super.getNonePrimaryKeyFieldsToString();
    }
}
