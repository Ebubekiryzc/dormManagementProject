package tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao;

import tr.edu.duzce.mf.bm.core.dataAccess.concretes.BaseDaoJDBCRepository;

import tr.edu.duzce.mf.bm.core.dataAccess.constants.Queries;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableColumn;
import tr.edu.duzce.mf.bm.core.utilities.annotations.TableName;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.IndividualUserDao;

import tr.edu.duzce.mf.bm.entities.concretes.Gender;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCIndividualUserDao extends BaseDaoJDBCRepository<IndividualUser> implements IndividualUserDao {

    public JDBCIndividualUserDao() {
        super(IndividualUser.class);
    }

}
