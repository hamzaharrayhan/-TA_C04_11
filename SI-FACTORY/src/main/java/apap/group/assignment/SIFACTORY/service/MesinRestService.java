package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import reactor.core.publisher.Mono;
import java.util.List;

public interface MesinRestService {
    List<MesinModel> retrieveListMesin();
    MesinModel getMesinByIdMesin(Long idMesin);
}
