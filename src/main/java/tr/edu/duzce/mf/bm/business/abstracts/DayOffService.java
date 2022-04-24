package tr.edu.duzce.mf.bm.business.abstracts;

import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;

import java.util.List;

public interface DayOffService {
    public DataResult<List<DayOff>> getAll();

    public DataResult<DayOff> getById(int id);

    public Result add(DayOff dayOff);

    public Result update(DayOff dayOff);

    public Result delete(DayOff dayOff);
}
