package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StaffRegisterDto {
    private IndividualUserRegisterDto individualUserRegisterDto;

    private String roleName;

    private String dateOfStart;
    private BigDecimal salary;
}
