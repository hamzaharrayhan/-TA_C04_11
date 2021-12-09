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

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("category")
    private Integer category;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stock")
    private Integer stock;
}
