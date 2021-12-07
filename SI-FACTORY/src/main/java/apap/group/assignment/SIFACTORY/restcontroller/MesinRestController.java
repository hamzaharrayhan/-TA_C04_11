package apap.group.assignment.SIFACTORY.restcontroller;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.service.MesinRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class MesinRestController {

    @Autowired
    private MesinRestService mesinRestService;

    @GetMapping(value = "/mesin/{idMesin}")
    private MesinModel retrieveMesin(
            @PathVariable("idMesin") Long idMesin
    ){
        try{
            return mesinRestService.getMesinByIdMesin(idMesin);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID Mesin " + String.valueOf(idMesin) + "Not Found."
            );
        }
    }

    @GetMapping(value = "/list-mesin")
    private List<MesinModel> retrieveListMesin() {
        return mesinRestService.retrieveListMesin();
    }

}
