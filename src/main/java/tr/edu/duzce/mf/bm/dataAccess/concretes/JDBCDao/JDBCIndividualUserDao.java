package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;

import tr.edu.duzce.mf.bm.dataAccess.abstracts.IndividualUserDao;

import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;


public class JDBCIndividualUserDao extends BaseDaoJDBCRepository<IndividualUser> implements IndividualUserDao {

    public JDBCIndividualUserDao() {
        super(IndividualUser.class);
    }
}
