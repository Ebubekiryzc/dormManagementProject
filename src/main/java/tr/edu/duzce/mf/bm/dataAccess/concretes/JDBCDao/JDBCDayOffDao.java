package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.DayOffDao;
import tr.edu.duzce.mf.bm.entities.concretes.DayOff;

public class JDBCDayOffDao extends BaseDaoJDBCRepository<DayOff> implements DayOffDao {

    public JDBCDayOffDao() {
        super(DayOff.class);
    }
}
