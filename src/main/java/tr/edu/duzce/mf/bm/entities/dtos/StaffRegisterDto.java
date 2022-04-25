package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;

import java.time.LocalDate;
import java.util.List;

@Data
public class StaffRegisterDto {
    private IndividualUserRegisterDto individualUserRegisterDto;

    private List<OperationClaim> roles;

    private LocalDate dateOfStart;
    private Integer salary;
}
