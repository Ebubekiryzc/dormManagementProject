package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDeleteDto {
    private BigDecimal individualUserId;
    private String departmentName;

    private String dateOfEntry;
    private String blockCode;
    private BigDecimal roomNumber;

}
