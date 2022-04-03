package tr.edu.duzce.mf.bm.core.dataAccess.constants;

public class Queries {
    public static String getAll(String tableName) {
        return String.format("Select * from %s order by id", tableName);
    }

    public static String get(String tableName, int id) {
        return String.format("Select * from %s where id=%d", tableName, id);
    }

    public static String add(String tableName, String entity) {
        return String.format("Insert Into %s values(%s)", tableName, entity);
    }

    public static String update(String tableName, String entity, String id) {
        return String.format("Update %s set %s where id=%s", tableName, entity, id);
    }

    public static String delete(String tableName, String id) {
        return String.format("Delete from %s where id=%s", tableName, id);
    }
}
