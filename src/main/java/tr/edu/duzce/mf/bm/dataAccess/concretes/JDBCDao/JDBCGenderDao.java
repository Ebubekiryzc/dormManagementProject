package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.GenderDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

public class JDBCGenderDao extends BaseDaoJDBCRepository<Gender> implements GenderDao {

    public JDBCGenderDao() {
        super(Gender.class);
    }

}
