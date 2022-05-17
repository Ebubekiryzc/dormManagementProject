package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StaffDeleteDto {
    private BigDecimal individualUserId;

    private String dateOfStart;
    private BigDecimal salary;
}
