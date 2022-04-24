package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;

import java.time.LocalDate;

@Data
public class StudentRegisterDto {
    private AuthenticateUserDTO authenticateUserDTO;
    private Long genderId;
    private Long departmentId;

    private String firstName;
    private String lastName;
    private Integer dayOffLimit;

    private LocalDate dateOfEntry;
    private String blockCode;
    private Integer roomNumber;
}
