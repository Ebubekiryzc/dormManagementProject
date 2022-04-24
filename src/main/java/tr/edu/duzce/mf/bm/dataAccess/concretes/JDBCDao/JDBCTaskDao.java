package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.TaskDao;
import tr.edu.duzce.mf.bm.entities.concretes.Task;

public class JDBCTaskDao extends BaseDaoJDBCRepository<Task> implements TaskDao {

    public JDBCTaskDao() {
        super(Task.class);
    }

}
