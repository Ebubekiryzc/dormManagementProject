package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.dtos.StaffRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentRegisterDto;

public interface AuthService {
    public DataResult<String> login(AuthenticateUserDTO authenticateUserDTO);

    public Result registerStudent(StudentRegisterDto studentRegisterDto);
    public Result registerStaff(StaffRegisterDto staffRegisterDto);
}
