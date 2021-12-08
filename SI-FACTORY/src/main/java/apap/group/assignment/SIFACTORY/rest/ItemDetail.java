package apap.group.assignment.SIFACTORY.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDetail {
//    private String status;

    @JsonProperty("harga")
    private Integer harga;

    @JsonProperty("kategori")
    private Integer kategori;

    @JsonProperty("nama")
    private String nama;

    @JsonProperty("stok")
    private Integer stok;
}
