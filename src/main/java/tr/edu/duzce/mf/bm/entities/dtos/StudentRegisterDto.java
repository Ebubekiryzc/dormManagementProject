package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentRegisterDto {
    private IndividualUserRegisterDto individualUserRegisterDto;

    private Long departmentId;
    private List<OperationClaim> roles;

    private LocalDate dateOfEntry;
    private String blockCode;
    private Integer roomNumber;
}
