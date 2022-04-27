package tr.edu.duzce.mf.bm.core.dataAccess.constants;

public class Queries {
    public static String getAll(String tableName) {
        return String.format("Select * from %s order by id", tableName);
    }

    public static String get(String tableName, String id) {
        return String.format("Select * from %s where id=%s", tableName, id);
    }

    public static String add(String tableName, String columnNames,String entity) {
        System.out.println(String.format("Insert Into %s(%s) values(%s)", tableName, columnNames, entity));
        return String.format("Insert Into %s(%s) values(%s)", tableName, columnNames, entity);
    }

    public static String update(String tableName, String entity, String id) {
        return String.format("Update %s set %s where id=%s", tableName, entity, id);
    }

    public static String delete(String tableName, String id) {
        return String.format("Delete from %s where id=%s", tableName, id);
    }

    public static String getByUsername(String tableName, String username) {
        return String.format("Select * from %s where username='%s'", tableName, username);
    }

    public static String getUserClaims(String userClaimTable, String claimTable, String claimTableIdColumn, String userClaimTableOperationClaimIdColumn, String userClaimTableUserIdColumn, String id) {
        return String.format("Select claim_table.* from %s claim_table inner join %s user_claim_table on claim_table.%s=user_claim_table.%s where user_claim_table.%s=%s",
                claimTable,
                userClaimTable,
                claimTableIdColumn,
                userClaimTableOperationClaimIdColumn,
                userClaimTableUserIdColumn,
                id);
    }

    public static String getClaimByName(String claimTable, String nameColumn, String nameValue) {
        return String.format("Select * from %s where %s='%s'",claimTable, nameColumn, nameValue);
    }
}
