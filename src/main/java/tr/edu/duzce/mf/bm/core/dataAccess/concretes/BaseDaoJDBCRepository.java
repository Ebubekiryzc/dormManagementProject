package tr.edu.duzce.mf.bm.core.dataAccess.concretes;

import lombok.Getter;
import lombok.Setter;
import tr.edu.duzce.mf.bm.core.dataAccess.DatabaseConnection;
import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Queries;
import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.core.utilities.exceptions.NotExistInDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseDaoJDBCRepository<TEntity> implements BaseDao<TEntity> {

    @Getter
    @Setter
    private Class<TEntity> entityClass;

    @Getter
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public BaseDaoJDBCRepository(Class<TEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<TEntity> getAll() {
        List<TEntity> entityList = new ArrayList<>();
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(Queries.getAll(entityClass.getAnnotation(TableName.class).value()));
            while (resultSet.next()) {
                TEntity entity = entityClass.getDeclaredConstructor().newInstance();
                loadResultSetIntoObject(resultSet, entity);
                entityList.add(entity);
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException exception) {
            System.err.println(exception.getMessage() + "/45 BaseDaoJDBCRepository");
        }
        return entityList;

    }

    @Override
    public TEntity getById(String id) {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(Queries.get(entityClass.getAnnotation(TableName.class).value(), id));
            while (resultSet.next()) {
                TEntity entity = entityClass.getDeclaredConstructor().newInstance();
                loadResultSetIntoObject(resultSet, entity);
                return entity;
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException exception) {
            System.err.println(exception.getMessage() + "/65 BaseDaoJDBCRepository");
        }
        return null;
    }

    @Override
    public boolean add(TEntity tEntity) {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            String tableName = entityClass.getAnnotation(TableName.class).value();

            statement.executeUpdate(Queries.add(tableName, getNonPKFieldNames(tEntity), getNonPKFieldValues(tEntity)));
        } catch (Exception exception) {
            System.err.println(exception.getMessage() + "/80 BaseDaoJDBCRepository");
            return false;
        }
        return true;
    }

    @Override
    public boolean update(TEntity tEntity) {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            Field idField = getIdField(tEntity);

            String tableName = entityClass.getAnnotation(TableName.class).value();

            ResultSet resultSet = statement.executeQuery(Queries.update(tableName, tEntity.toString(), getFieldStringValue(idField, tEntity)));
            if (!resultSet.next()) {
                throw new NotExistInDatabase();
            }
        } catch (SQLException | NoSuchElementException | IllegalAccessException | NotExistInDatabase exception) {
            System.err.println(exception.getMessage() + "/99 BaseDaoJDBCRepository");
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(TEntity tEntity) {
        try {
            Statement statement = getDatabaseConnection().getConnection().createStatement();
            Field idField = getIdField(tEntity);

            String tableName = entityClass.getAnnotation(TableName.class).value();

            ResultSet resultSet = statement.executeQuery(Queries.delete(tableName, getFieldStringValue(idField, tEntity)));
            if (!resultSet.next()) {
                throw new NotExistInDatabase();
            }
        } catch (SQLException | NoSuchElementException | IllegalAccessException | NotExistInDatabase exception) {
            System.err.println(exception.getMessage() + "/118 BaseDaoJDBCRepository");
            return false;
        }
        return true;
    }

    protected void loadResultSetIntoObject(ResultSet resultSet, TEntity entity) throws SQLException, IllegalAccessException {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);

            String column = field.getName();
            if (field.getAnnotation(TableColumn.class) != null) {
                column = field.getAnnotation(TableColumn.class).name();
            }

            Object value = resultSet.getObject(column);
            Class<?> type = field.getType();

            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                value = boxed.cast(value);
            }

            if (value.getClass().getName().equals("org.postgresql.jdbc.PgArray")) {
                String[] byteValues = value.toString().replace("{", "").replace("}", "").split(",");
                byte[] bytes = new byte[byteValues.length];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) Integer.parseInt(byteValues[i], 2);
                }
                value = bytes;
            }else if (field.getType().getSimpleName().equals("byte[]")){
                String[] text = divideTextForEachNChar(value.toString(),8);
                byte[] bytes = new byte[text.length];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) Integer.parseInt(text[i], 2);
                }
                value = bytes;
            }

            if(value.getClass().getSimpleName().equals("Timestamp")){
                value = value.toString();
            }

            field.set(entity, value);
        }
    }

    private String[] divideTextForEachNChar(String text, int n) {
        String[] returningText = new String[text.length() / n];
        for (int i = 0; i < text.length() / n; i++) {
            returningText[i] = text.substring(i * n, (i + 1) * n);
        }
        return returningText;
    }

    private boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String message = String.format("class '%s' is not a primitive type.", type.getName());
            throw new IllegalArgumentException(message);
        }
    }

    private String getNonPKFieldNames(TEntity entity) {
        String fields = "";
        try {
            for (var field : entity.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Id.class) != null) continue;

                String fieldName = field.getName();
                if (field.getAnnotation(TableColumn.class).name() != null) {
                    fieldName = field.getAnnotation(TableColumn.class).name();
                }
                fields = fields.concat(String.format("%s,", fieldName));
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage() + "/183 BaseDaoJDBCRepository");
        }
        fields = fields.substring(0, fields.length() - 1);
        return fields;
    }

    private String getNonPKFieldValues(TEntity entity) {
        String fields = "";
        try {
            for (var field : entity.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Id.class) != null) continue;

                field.setAccessible(true);
                String value = getFieldStringValue(field, entity);
                fields = fields.concat(String.format("%s,", value));
            }
        } catch (IllegalAccessException exception) {
            System.err.println(exception.getMessage() + "/201 BaseDaoJDBCRepository");
        }
        fields = fields.substring(0, fields.length() - 1);
        return fields;
    }


    private Field getIdField(TEntity entity) throws NoSuchElementException {
        Field field = null;
        field = Stream.of(entityClass.getDeclaredFields()).filter(idField -> idField.isAnnotationPresent(Id.class)).findFirst().get();
        field.setAccessible(true);
        return field;
    }

    private String getFieldStringValue(Field field, TEntity entity) throws IllegalAccessException {
        Object value = field.get(entity);
        if (value == null) return null;

        Class<?> type = field.getType();

        if (isPrimitive(type)) {
            Class<?> boxed = boxPrimitiveClass(type);
            value = boxed.cast(value);
        }

        if (type.getSimpleName().equals("String")) {
            value = String.format("\'%s\'", value.toString());
        } else if (type.getSimpleName().equals("byte[]")) {
            List<String> byteValue = new ArrayList<>();
            byte[] bytes = (byte[]) field.get(entity);
            for (byte b : bytes) {
                byteValue.add(Integer.toBinaryString(b & 255 | 256).substring(1));
            }
            value = String.format("'%s'", byteValue.stream().collect(Collectors.joining("")));
        }

        return value.toString();
    }
}
