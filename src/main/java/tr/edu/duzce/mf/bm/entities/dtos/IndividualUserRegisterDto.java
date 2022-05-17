package tr.edu.duzce.mf.bm.entities.dtos;

import lombok.Data;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;

import java.math.BigDecimal;

@Data
public class IndividualUserRegisterDto {
    private AuthenticateUserDTO authenticateUserDTO;
    private String genderName;

    private String firstName;
    private String lastName;
    private BigDecimal dayOffLimit;
}
