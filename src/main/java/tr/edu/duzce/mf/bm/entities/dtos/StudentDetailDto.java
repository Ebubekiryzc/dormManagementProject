package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;

import java.math.BigDecimal;

@Data
public class StudentDetailDto {
    @TableColumn(name="individual_user_id")
    private BigDecimal id;
    @TableColumn(name="username")
    private String username;
    @TableColumn(name="first_name")
    private String firstName;
    @TableColumn(name="last_name")
    private String lastName;
    @TableColumn(name="gender_name")
    private String genderName;
    @TableColumn(name="date_of_entry")
    private String dateOfEntry;
    @TableColumn(name="day_off_limit")
    private BigDecimal dayOffLimit;

    @TableColumn(name="department_name")
    private String departmentName;
    @TableColumn(name="block_code")
    private String blockCode;
    @TableColumn(name="room_number")
    private BigDecimal roomNumber;
}
