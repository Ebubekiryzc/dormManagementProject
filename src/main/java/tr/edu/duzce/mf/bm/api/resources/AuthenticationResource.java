package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.OperationClaimService;
import tr.edu.duzce.mf.bm.business.abstracts.UserService;
import tr.edu.duzce.mf.bm.business.concretes.OperationClaimManager;
import tr.edu.duzce.mf.bm.business.concretes.UserManager;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCOperationClaimDao;
import tr.edu.duzce.mf.bm.core.dataAccess.concretes.JDBCUserDao;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.ErrorDataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.SuccessDataResult;
import tr.edu.duzce.mf.bm.core.utilities.security.jwt.TokenGenerator;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.entities.dtos.StudentRegisterDto;

import java.util.List;

//TODO: login'de şifre kontrolü de yap, hashing işlemlerini burada kullanabilirsin.

@Path("/authenticate")
@Singleton
@Resource
public class AuthenticationResource {

    private UserService userService;
    private OperationClaimService operationClaimService;
    private TokenGenerator tokenGenerator;

    public AuthenticationResource() {
        this.userService = new UserManager(new JDBCUserDao());
        this.operationClaimService = new OperationClaimManager(new JDBCOperationClaimDao());
        this.tokenGenerator= new TokenGenerator();
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public DataResult<String> login(AuthenticateUserDTO userCredentials){
        User user = userService.getByUsername(userCredentials.getUsername()).getEntity();
        if(user!= null){
            List<OperationClaim> roles = operationClaimService.getUserClaims(user).getEntity();
            String accessToken = tokenGenerator.generateToken(roles);
            return new SuccessDataResult<>(accessToken, Messages.OperationSuccessful);
        }else{
            return new ErrorDataResult<>(Messages.UserNotFound);
        }
    }

    // TODO: Buradan devam edilecek. Yapman gereken şey operationclaim için isme göre getirme yazmak. Böylece bu isimdeki yetkinin id değerini getirip atayabileceksin.

    @POST
    @RolesAllowed({"admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    public DataResult<String> registerStudent(StudentRegisterDto studentRegisterDto){
        User user = userService.getByUsername(studentRegisterDto.getAuthenticateUserDTO().getUsername()).getEntity();
        return null;
    }
}
