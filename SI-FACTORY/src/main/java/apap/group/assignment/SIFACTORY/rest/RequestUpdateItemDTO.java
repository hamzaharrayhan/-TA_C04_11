package apap.group.assignment.SIFACTORY.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestUpdateItemDTO {

    @JsonProperty
    private String idItem;

    @JsonProperty
    private Integer idKategori;

    @JsonProperty
    private Integer tambahanStok;

    @JsonProperty
    private Date tanggalRequest;

    @JsonProperty
    private Integer idCabang;
}
