package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.*;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.HashingHelper;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.SaltAndPepperModel;
import tr.edu.duzce.mf.bm.core.utilities.security.jwt.TokenGenerator;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.IndividualUserRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StaffRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StudentRegisterDto;

import java.math.BigDecimal;
import java.util.List;

//TODO: Burada user varsa ekleme gibi kontroller henüz yok!

public class AuthManager implements AuthService {
    private UserService userService;
    private IndividualUserService individualUserService;
    private StudentService studentService;
    private StaffService staffService;
    private OperationClaimService operationClaimService;
    private UserOperationClaimService userOperationClaimService;
    private TokenGenerator tokenGenerator;

    public AuthManager(UserService userService, IndividualUserService individualUserService, StudentService studentService, StaffService staffService, OperationClaimService operationClaimService, UserOperationClaimService userOperationClaimService, TokenGenerator tokenGenerator) {
        this.userService = userService;
        this.individualUserService = individualUserService;
        this.studentService = studentService;
        this.staffService = staffService;
        this.operationClaimService = operationClaimService;
        this.userOperationClaimService = userOperationClaimService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public DataResult<String> login(AuthenticateUserDTO userCredentials) {
        String username = userCredentials.getUsername();
        var result = userExist(username);
        if(!result.isSuccess()){
            return new ErrorDataResult<>(Messages.UserNotFound);
        }

        User user = result.getEntity();
        SaltAndPepperModel saltAndPepperModel = new SaltAndPepperModel();
        saltAndPepperModel.setSalt(user.getPasswordSalt());
        saltAndPepperModel.setHash(user.getPasswordHash());

        if(!HashingHelper.verifyHash(userCredentials.getPassword(), saltAndPepperModel)){
            return new ErrorDataResult<>(Messages.PasswordNotMatch);
        }

        List<OperationClaim> roles = operationClaimService.getUserClaims(user).getEntity();
        String accessToken = tokenGenerator.generateToken(roles);
        return new SuccessDataResult<>(accessToken, Messages.OperationSuccessful);
    }

    @Override
    public Result registerStudent(StudentRegisterDto studentRegisterDto) {
        userAdd(studentRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO());
        individualUserAdd(studentRegisterDto.getIndividualUserRegisterDto());

        User user = userService.getByUsername(studentRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO().getUsername()).getEntity();

        Student student = new Student();
        student.setBlockCode(studentRegisterDto.getBlockCode());
        student.setDateOfEntry(studentRegisterDto.getDateOfEntry());
        student.setDepartmentId(studentRegisterDto.getDepartmentId());
        student.setRoomNumber(studentRegisterDto.getRoomNumber());
        student.setIndividualUserId(user.getId());
        var result = studentService.add(student);
        if(result.isSuccess()) {
            addUserOperationClaims(user.getId(), studentRegisterDto.getRoles());
            return new SuccessResult(Messages.OperationSuccessful);
        }
        return result;
    }

    @Override
    public Result registerStaff(StaffRegisterDto staffRegisterDto) {
        userAdd(staffRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO());
        individualUserAdd(staffRegisterDto.getIndividualUserRegisterDto());

        User user = userService.getByUsername(staffRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO().getUsername()).getEntity();

        Staff staff = new Staff();
        staff.setIndividualUserId(user.getId());
        staff.setDateOfStart(staffRegisterDto.getDateOfStart());
        staff.setSalary(staffRegisterDto.getSalary());
        var result = staffService.add(staff);
        if(result.isSuccess()) {
            addUserOperationClaims(user.getId(), staffRegisterDto.getRoles());
            return new SuccessResult(Messages.OperationSuccessful);
        }
        return result;
    }


    private DataResult<User> userExist(String username) {
        User user = userService.getByUsername(username).getEntity();
        if(user == null){
            return new ErrorDataResult<>(null,Messages.UserNotFound);
        }
        return new SuccessDataResult<>(user, Messages.OperationSuccessful);
    }

    private void addUserOperationClaims(BigDecimal userId, List<OperationClaim> roleSet) {
        for (OperationClaim role: roleSet){
            OperationClaim operationClaim = operationClaimService.getByName(role.getName()).getEntity();
            UserOperationClaim userOperationClaim = new UserOperationClaim();
            userOperationClaim.setUserId(userId);
            userOperationClaim.setOperationClaimId(operationClaim.getId());
            userOperationClaimService.add(userOperationClaim);
        }
    }

    private User userAdd(AuthenticateUserDTO authenticateUserDTO)
    {
        SaltAndPepperModel saltAndPepperModel = new SaltAndPepperModel();
        User user = new User();
        HashingHelper.generateHash(authenticateUserDTO.getPassword(),saltAndPepperModel);

        user.setUsername(authenticateUserDTO.getUsername());
        user.setPasswordSalt(saltAndPepperModel.getSalt());
        user.setPasswordHash(saltAndPepperModel.getHash());

        Result result = userService.add(user);
        return user;
    }

    private IndividualUser individualUserAdd(IndividualUserRegisterDto individualUserRegisterDto){
        String username = individualUserRegisterDto.getAuthenticateUserDTO().getUsername();
        User user = userService.getByUsername(username).getEntity();

        IndividualUser individualUser = new IndividualUser();
        individualUser.setUserId(user.getId());
        individualUser.setFirstName(individualUserRegisterDto.getFirstName());
        individualUser.setLastName(individualUserRegisterDto.getLastName());
        individualUser.setDayOffLimit(individualUserRegisterDto.getDayOffLimit());
        individualUser.setGenderId(individualUserRegisterDto.getGenderId());
        Result result = individualUserService.add(individualUser);
        return individualUser;
    }
}
