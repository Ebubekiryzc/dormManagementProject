package tr.edu.duzce.mf.bm.dataAccess.abstracts;

import tr.edu.duzce.mf.bm.core.dataAccess.abstracts.BaseDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;
import tr.edu.duzce.mf.bm.entities.dtos.DayOffDetailDto;

import java.util.List;

public interface DayOffDao extends BaseDao<DayOff> {
    public List<DayOffDetailDto> getDayOffsWithAccessLevel(String role, String showOnlyActive);

    public DayOff getDayOffWithUserIdAtRange(String dateOfStart, String dateOfEnd, String userId);

    public List<DayOff> getDayOffsByUserId(String userId);
    public List<DayOff> getActiveDayOffsByUserId(String userId);

}
