package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;

import java.util.List;

public interface StaffService {
    public DataResult<List<Staff>> getAll();

    public DataResult<Staff> getById(int id);

    public Result add(Staff staff);

    public Result update(Staff staff);

    public Result delete(Staff staff);
}
