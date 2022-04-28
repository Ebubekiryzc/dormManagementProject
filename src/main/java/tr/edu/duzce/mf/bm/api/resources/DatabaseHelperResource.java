package tr.edu.duzce.mf.bm.api.resources;

import com.sun.istack.Nullable;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.DatabaseHelper;
import tr.edu.duzce.mf.bm.business.concretes.*;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCIndividualUserDao;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStaffDao;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStudentDao;


@Path("/database")
@Singleton
@Resource
public class DatabaseHelperResource {
    private DatabaseHelper databaseHelper;

    public DatabaseHelperResource() {
        this.databaseHelper = new OracleDatabaseHelper(
                new UserManager(new JDBCUserDao()),
                new IndividualUserManager(new JDBCIndividualUserDao()),
                new StaffManager(new JDBCStaffDao()),
                new StudentManager(new JDBCStudentDao()),
                new OperationClaimManager(new JDBCOperationClaimDao())
        );
    }

    @GET
    @RolesAllowed({"admin"})
    @Path("/backup")
    @Produces(MediaType.APPLICATION_JSON)
    public Result backupDatabase() {
        var result = databaseHelper.backupDatabase();
        return result;
    }

    @GET
    @RolesAllowed({"admin"})
    @Path("/recover")
    @Produces(MediaType.APPLICATION_JSON)
    public Result recoverDatabase() {
        var result = databaseHelper.recoverDatabase();
        return result;
    }

    @GET
    @PermitAll
    @Path("/export_students")
    @Produces(MediaType.APPLICATION_JSON)
    public Result exportStudents() {
        String publicHome = System.getenv("PUBLIC");
        var result = databaseHelper.exportStudentsFromDatabase(String.format("%s/Documents/ExportedStudents.csv", publicHome));
        return result;
    }

    @GET
    @PermitAll
    @Path("/export_staffs")
    @Produces(MediaType.APPLICATION_JSON)
    public Result exportStaffs(@Nullable String filePath) {
        if (filePath == null) {
            filePath = System.getenv("PUBLIC");
            filePath = String.format("%s/Documents", filePath);
        }
        var result = databaseHelper.exportStaffsFromDatabase(String.format("%s/ExportedStaffs.csv", filePath));
        return result;
    }
}
