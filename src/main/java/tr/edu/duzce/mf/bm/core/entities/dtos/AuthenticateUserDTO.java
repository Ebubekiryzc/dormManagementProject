package tr.edu.duzce.mf.bm.core.entities.dtos;

import lombok.Data;

@Data
public class AuthenticateUserDTO {
    private String username;
    private String password;
}
