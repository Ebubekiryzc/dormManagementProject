package tr.edu.duzce.mf.bm.business.concretes;

import tr.edu.duzce.mf.bm.business.abstracts.*;
import tr.edu.duzce.mf.bm.core.dataAccess.constants.Messages;
import tr.edu.duzce.mf.bm.core.entities.concrete.OperationClaim;
import tr.edu.duzce.mf.bm.core.entities.concrete.User;
import tr.edu.duzce.mf.bm.core.entities.concrete.UserOperationClaim;
import tr.edu.duzce.mf.bm.core.entities.dtos.AuthenticateUserDTO;
import tr.edu.duzce.mf.bm.core.utilities.business.BusinessRules;
import tr.edu.duzce.mf.bm.core.utilities.results.*;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.HashingHelper;
import tr.edu.duzce.mf.bm.core.utilities.security.hashing.SaltAndPepperModel;
import tr.edu.duzce.mf.bm.dataAccess.abstracts.StaffDao;
import tr.edu.duzce.mf.bm.entities.concretes.IndividualUser;
import tr.edu.duzce.mf.bm.entities.concretes.Staff;
import tr.edu.duzce.mf.bm.entities.concretes.Student;
import tr.edu.duzce.mf.bm.entities.dtos.IndividualUserRegisterDto;
import tr.edu.duzce.mf.bm.entities.dtos.StaffDetailDto;
import tr.edu.duzce.mf.bm.entities.dtos.StaffRegisterDto;

import java.util.List;

public class StaffManager implements StaffService {


    private StaffDao staffDao;
    private IndividualUserService individualUserService;
    private UserService userService;
    private GenderService genderService;

    private OperationClaimService operationClaimService;
    private UserOperationClaimService userOperationClaimService;

    public StaffManager(StaffDao staffDao, IndividualUserService individualUserService, UserService userService, GenderService genderService, OperationClaimService operationClaimService, UserOperationClaimService userOperationClaimService) {
        this.staffDao = staffDao;
        this.individualUserService = individualUserService;
        this.userService = userService;
        this.genderService = genderService;
        this.operationClaimService = operationClaimService;
        this.userOperationClaimService = userOperationClaimService;
    }

    @Override
    public DataResult<List<Staff>> getAll() {
        return new SuccessDataResult<>(staffDao.getAll(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getAllStaffDetails() {
        return new SuccessDataResult<>(staffDao.getAllStaffDetails(), Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByFullName(String firstName, String lastName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByFullName(firstName, lastName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByFirstName(String firstName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByFirstName(firstName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<List<StaffDetailDto>> getByLastName(String lastName) {
        List<StaffDetailDto> staffList = staffDao.getStaffDetailsByLastName(lastName);
        if (staffList == null) return new ErrorDataResult<>(null, Messages.OperationFailed);
        return new SuccessDataResult<>(staffList, Messages.OperationSuccessful);
    }

    @Override
    public DataResult<Staff> getById(int id) {
        Staff staff = staffDao.getById(String.valueOf(id));
        var result = BusinessRules.check(isNull(staff));
        if (!result.isSuccess()) {
            return (ErrorDataResult<Staff>) result;
        }
        return new SuccessDataResult<>(staff, Messages.OperationSuccessful);
    }

    @Override
    public Result add(Staff staff) {
        boolean isAdded = staffDao.add(staff);
        if (isAdded) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result update(StaffRegisterDto staffRegisterDto) {
        var totalResult = BusinessRules.check(userUpdate(staffRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO()), individualUserUpdate(staffRegisterDto.getIndividualUserRegisterDto()));
        if (!totalResult.isSuccess()) {
            return totalResult;
        }

        User user = userService.getByUsername(staffRegisterDto.getIndividualUserRegisterDto().getAuthenticateUserDTO().getUsername()).getEntity();

        Staff staff = new Staff();
        staff.setIndividualUserId(user.getId());
        staff.setDateOfStart(staffRegisterDto.getDateOfStart());
        staff.setSalary(staffRegisterDto.getSalary());

        boolean isUpdated = staffDao.update(staff);

        if (isUpdated) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    @Override
    public Result delete(Staff staff) {
        boolean isDeleted = staffDao.delete(staff);
        if (isDeleted) return new SuccessResult(Messages.OperationSuccessful);
        return new ErrorResult(Messages.OperationFailed);
    }

    private DataResult<Staff> isNull(Staff staff) {
        if (staff == null) {
            return new ErrorDataResult<>(Messages.OperationFailed);
        }
        return new SuccessDataResult<>(Messages.OperationSuccessful);
    }

    private Result userUpdate(AuthenticateUserDTO authenticateUserDTO) {
        if (authenticateUserDTO.getPassword().equals("")) {
            return new SuccessResult();
        }

        SaltAndPepperModel saltAndPepperModel = new SaltAndPepperModel();
        User user = userService.getByUsername(authenticateUserDTO.getUsername()).getEntity();
        HashingHelper.generateHash(authenticateUserDTO.getPassword(), saltAndPepperModel);

        user.setUsername(authenticateUserDTO.getUsername());
        user.setPasswordSalt(saltAndPepperModel.getSalt());
        user.setPasswordHash(saltAndPepperModel.getHash());

        Result result = userService.update(user);
        return result;
    }

    private Result individualUserUpdate(IndividualUserRegisterDto individualUserRegisterDto) {
        String username = individualUserRegisterDto.getAuthenticateUserDTO().getUsername();
        User user = userService.getByUsername(username).getEntity();

        IndividualUser individualUser = new IndividualUser();
        individualUser.setUserId(user.getId());
        individualUser.setFirstName(individualUserRegisterDto.getFirstName());
        individualUser.setLastName(individualUserRegisterDto.getLastName());
        individualUser.setDayOffLimit(individualUserRegisterDto.getDayOffLimit());
        individualUser.setGenderId(genderService.getByName(individualUserRegisterDto.getGenderName()).getEntity().getId());
        Result result = individualUserService.update(individualUser);
        return result;
    }
}
