package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StudentRegisterDto {
    private IndividualUserRegisterDto individualUserRegisterDto;

    private BigDecimal departmentId;
    private List<OperationClaim> roles;

    private String dateOfEntry;
    private String blockCode;
    private BigDecimal roomNumber;
}
