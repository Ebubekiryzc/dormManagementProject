package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;
import tr.edu.duzce.mf.bm.entities.dtos.StaffRegisterDto;

import java.util.List;

public interface StaffService {
    public DataResult<List<Staff>> getAll();

    public DataResult<List<StaffDetailDto>> getAllStaffDetails();

    public DataResult<List<StaffDetailDto>> getByFullName(String firstName, String lastName);

    public DataResult<List<StaffDetailDto>> getByFirstName(String firstName);

    DataResult<List<StaffDetailDto>> getByLastName(String lastName);

    public DataResult<Staff> getById(int id);

    public Result add(Staff staff);

    public Result update(StaffRegisterDto staff);

    public Result delete(Staff staff);

}
