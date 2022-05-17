package tr.edu.duzce.mf.bm.core.entities.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.annotations.Id;
import tr.edu.duzce.mf.bm.core.utilities.annotations.InheritedId;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseEntity implements Serializable {
    protected String getNonePrimaryKeyFieldsToString() {
        String fieldsStr = "";
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Id.class) != null || field.getAnnotation(InheritedId.class) != null) continue;

                field.setAccessible(true);

                String columnName = field.getName();
                if (field.getAnnotation(TableColumn.class) != null) {
                    columnName = field.getAnnotation(TableColumn.class).name();
                }

                String value = field.get(this).toString();

                if (field.getType().getSimpleName().equals("String")) {
                    value = String.format("\'%s\'", value);
                }

                if (field.getType().getSimpleName().equals("byte[]")) {
                    List<String> byteValue = new ArrayList<>();
                    byte[] bytes = (byte[]) field.get(this);
                    for (byte b : bytes) {
                        byteValue.add(Integer.toBinaryString(b & 255 | 256).substring(1));
                    }
                    value = String.format("'%s'", byteValue.stream().collect(Collectors.joining("")));
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
