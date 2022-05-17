package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;

import java.math.BigDecimal;

@Data
public class DayOffDetailDto {
    @TableColumn(name = "id")
    private BigDecimal id;
    @TableColumn(name = "first_name")
    private String firstName;
    @TableColumn(name = "last_name")
    private String lastName;
    @TableColumn(name = "date_of_start")
    private String dateOfStart;
    @TableColumn(name = "date_of_end")
    private String dateOfEnd;
}
