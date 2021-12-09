package apap.group.assignment.SIFACTORY.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlamatDTO {

    @JsonProperty
    private long id;

    @JsonProperty
    private String alamat;

}
