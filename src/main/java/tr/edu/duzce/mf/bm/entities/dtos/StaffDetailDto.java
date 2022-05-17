package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;

import java.math.BigDecimal;

@Data
public class StaffDetailDto {
    @TableColumn(name = "individual_user_id")
    private BigDecimal id;
    @TableColumn(name = "username")
    private String username;
    @TableColumn(name = "first_name")
    private String firstName;
    @TableColumn(name = "last_name")
    private String lastName;
    @TableColumn(name = "gender_name")
    private String genderName;
    @TableColumn(name="claim")
    private String claim;
    @TableColumn(name = "date_of_start")
    private String dateOfStart;
    @TableColumn(name = "day_off_limit")
    private BigDecimal dayOffLimit;
    @TableColumn(name = "salary")
    private BigDecimal salary;
}
