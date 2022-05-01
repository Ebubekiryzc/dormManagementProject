package tr.edu.duzce.mf.bm.api.resources;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tr.edu.duzce.mf.bm.business.abstracts.GenderService;
import tr.edu.duzce.mf.bm.business.concretes.GenderManager;
import tr.edu.duzce.mf.bm.core.utilities.results.DataResult;
import tr.edu.duzce.mf.bm.core.utilities.results.Result;
import tr.edu.duzce.mf.bm.dataAccess.concretes.JDBCDao.JDBCGenderDao;
import tr.edu.duzce.mf.bm.entities.concretes.Gender;

import java.util.List;


//@QueryParam(parametre adı) --> QueryString yakalama.
//@MatrixParam(parametre adı) --> ; ile ayrılmış url'deki işaretleri alma

//TODO: Bir tane boş controller açıp orada /{placeholder} diye bir olmayan url'leri yakalayan controller açabiliriz.
@Path(value = "/genders")
@Singleton
@Resource
public class GenderResource {
    private GenderService genderService;

    public GenderResource() {
        genderService = new GenderManager(new JDBCGenderDao());
    }

    @GET
    @RolesAllowed({"admin", "staff"})
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<List<Gender>> getAll() {
        return this.genderService.getAll();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public DataResult<Gender> getById(@PathParam("id") Integer id) {
        return this.genderService.getById(id);
    }

    // TODO: id ile ilgili değişiklikler yapılacak.
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Result add(Gender gender) {
        return this.genderService.add(gender);
    }

    @PUT
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Result update(Gender gender) {
        return this.genderService.update(gender);
    }

    @DELETE
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Result delete(Gender gender) {
        return this.genderService.delete(gender);
    }
}
