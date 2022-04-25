package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;

@Data
public class IndividualUserRegisterDto {
    private AuthenticateUserDTO authenticateUserDTO;
    private Long genderId;

    private String firstName;
    private String lastName;
    private Integer dayOffLimit;
}
