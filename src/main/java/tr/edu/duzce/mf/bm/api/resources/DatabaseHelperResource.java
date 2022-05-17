package tr.edu.duzce.mf.bm.api.resources;

import com.sun.istack.Nullable;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import tr.edu.duzce.mf.bm.business.abstracts.DatabaseHelper;
import tr.edu.duzce.mf.bm.business.abstracts.GenderService;
import tr.edu.duzce.mf.bm.business.abstracts.IndividualUserService;
import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.business.concretes.*;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessDataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessResult;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


@Path("/database")
@Singleton
@Resource
public class DatabaseHelperResource {
    private DatabaseHelper databaseHelper;

    public DatabaseHelperResource() {
        UserService userService = new UserManager(new JDBCUserDao());
        IndividualUserService individualUserService = new IndividualUserManager(new JDBCIndividualUserDao());
        GenderService genderService = new GenderManager(new JDBCGenderDao());
        this.databaseHelper = new OracleDatabaseHelper(userService, individualUserService, new StaffManager(new JDBCStaffDao(), individualUserService, userService, genderService, new OperationClaimManager(new JDBCOperationClaimDao()), new UserOperationClaimManager(new JDBCUserOperationClaimDao())), new StudentManager(new JDBCStudentDao(), individualUserService, userService, new DepartmentManager(new JDBCDepartmentDao()), genderService), genderService, new OperationClaimManager(new JDBCOperationClaimDao()));
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
    @Path("/export_genders")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportGenders() {
        try {
            String userHome = System.getProperty("user.home");
            String filePath = String.format("%s/ExportedGenders.csv", userHome);
            databaseHelper.exportGendersFromDatabase(filePath);
            File file = new File(filePath);

            filePath = String.format("http://127.0.0.1:8081/%s", "ExportedGenders.csv");
            SuccessDataResult<String> result = new SuccessDataResult<>(filePath, Messages.ReportCreated);

            String headerValue = "attachment; filename=\"" + file.getName() + "\"";
            return Response.ok(file, MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).entity(result).build();
        } catch (Exception exception) {
            var result = new ErrorResult(exception.getMessage() + "/76 StudentResource");
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
    }

    @POST
    @Path("/import_genders")
    @RolesAllowed({"admin"})
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response upload_genders(@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition formDataContentDisposition) {
        Result result = new SuccessResult(Messages.OperationSuccessful);
        try {
            String path = String.format("%s/files/%s", System.getProperty("user.home"), formDataContentDisposition.getFileName());
            Result uploadResult = databaseHelper.uploadData(inputStream, path);

            Result businessRules = BusinessRules.check(uploadResult);
            if (!businessRules.isSuccess())
                return Response.status(Response.Status.BAD_REQUEST).entity(businessRules.getMessage()).build();

            result = databaseHelper.importGendersToDatabase(path);

        } catch (Exception exception) {
            System.out.println(String.format("%s/114 DatabaseHelperResource", exception.getMessage()));
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                System.out.println(String.format("%s/119 DatabaseHelperResource", exception.getMessage()));
            }
        }
        return Response.status(Response.Status.OK).entity(result.getMessage()).build();
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
