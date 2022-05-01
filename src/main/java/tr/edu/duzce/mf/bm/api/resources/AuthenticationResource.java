package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tr.edu.duzce.mf.bm.business.abstracts.AuthService;
import tr.edu.duzce.mf.bm.business.abstracts.OperationClaimService;
import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.business.concretes.*;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserOperationClaimDao;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.core.utilities.security.jwt.TokenGenerator;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCIndividualUserDao;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStaffDao;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCStudentDao;
import tr.edu.duzce.mf.bm.entities.dtos.StaffRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentRegisterDto;


//TODO: Kayıt işlemlerindeki yetkiler tekrardan tanımlanmalı --> PRODUCTION
//TODO: Öğrenci ve görevlileri getirirken bir DTO yazıp bütün ilgili entity bilgileri getirilebilir.

@Path("/authenticate")
@Resource
public class AuthenticationResource {

    private AuthService authService;

    public AuthenticationResource() {
        this.authService = new AuthManager(
                new UserManager(new JDBCUserDao()),
                new IndividualUserManager(new JDBCIndividualUserDao()),
                new StudentManager(new JDBCStudentDao()),
                new StaffManager(new JDBCStaffDao()),
                new OperationClaimManager(new JDBCOperationClaimDao()),
                new UserOperationClaimManager(new JDBCUserOperationClaimDao()),
                new TokenGenerator()
        );
    }


    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticateUserDTO userCredentials) {
        Response response = Response.status(Response.Status.OK).entity(authService.login(userCredentials)).build();
        return response;
    }


    @POST
    @Path("/register_student")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result registerStudent(StudentRegisterDto studentRegisterDto) {
        return authService.registerStudent(studentRegisterDto);
    }

    @POST
    @Path("/register_staff")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result registerStaff(StaffRegisterDto staffRegisterDto) {
        return authService.registerStaff(staffRegisterDto);
    }
}
