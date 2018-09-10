package ua.ksa.english.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class WordDTO {
    private final UUID id;
    private final String english;
    private final String translate;

    @JsonCreator
    public WordDTO(@JsonProperty(value = "id",required = false) UUID id,
                   @JsonProperty("english") String english,
                   @JsonProperty("translate") String translate) {
        this.id = id;
        this.english = english;
        this.translate = translate;
    }
}
