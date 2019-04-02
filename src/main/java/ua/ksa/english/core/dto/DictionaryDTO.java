package ua.ksa.english.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDTO {
    protected UUID dictionaryId;
    @Size(max = 255, min = 3, message = "name mast contain 3...255 characters")
    protected String name;
}
