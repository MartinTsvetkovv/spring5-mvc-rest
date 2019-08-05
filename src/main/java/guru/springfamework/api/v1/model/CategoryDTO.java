package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;

    @JsonProperty("product_url")
    private String productUrl;


}
