package ua.ksa.english.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WordDTO {
    private final UUID dictionaryId;
    private UUID wordId;
    @NotNull
    @Size(max = 255, message = "length 1...255")
    private final String foreign;
    @NotNull
    @Size(max = 255, message = "length 1...255")
    private final String translate;

    @JsonCreator
    public WordDTO(@JsonProperty(value = "dictionaryId") UUID dictionaryId,
                   @JsonProperty(value = "wordId") UUID wordId,
                   @JsonProperty("foreign") String foreign,
                   @JsonProperty("translate") String translate) {
        this.dictionaryId = dictionaryId;
        this.foreign = foreign;
        this.translate = translate;
        this.wordId = wordId;
    }
}
