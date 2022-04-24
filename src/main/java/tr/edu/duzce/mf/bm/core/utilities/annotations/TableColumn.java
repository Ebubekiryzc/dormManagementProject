package tr.edu.duzce.mf.bm.core.utilities.annotations;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {
    public String name();
}
