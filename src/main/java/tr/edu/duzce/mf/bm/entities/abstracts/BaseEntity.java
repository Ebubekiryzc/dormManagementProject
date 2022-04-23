package tr.edu.duzce.mf.bm.entities.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;

import java.lang.reflect.Field;

public abstract class BaseEntity {

    // TODO: PK olmadığını kontrol et.
    protected String getNonePrimaryKeyFieldsToString() {
        String fieldsStr = "";

        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                String columnName = field.getName();
                if (field.getAnnotation(TableColumn.class) != null) {
                    columnName = field.getAnnotation(TableColumn.class).name();
                }

                String value = field.get(this).toString();

                if (field.getType().getSimpleName().equals("String")) {
                    value = String.format("\'%s\'", value);
                    // name='Kadın' - name=Kadın
                }

                fieldsStr = fieldsStr.concat(
                        String.format("%s=%s,", columnName, value)
                );
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return (fieldsStr.substring(0, fieldsStr.length() - 1));
    }
}
