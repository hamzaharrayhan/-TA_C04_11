package apap.group.assignment.SIFACTORY.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemModel {

    @JsonProperty
    private String uuid;

    @JsonProperty
    private String nama;

    @JsonProperty
    private Integer harga;

    @JsonProperty
    private Integer stok;

    @JsonProperty
    private String kategori;
}
