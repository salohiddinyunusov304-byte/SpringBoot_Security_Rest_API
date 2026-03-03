package uz.pdp.springboot_security_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorDto {
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("error_code")
    private Integer errorCode;
    @JsonProperty("error_path")
    private String errorPath;
    private LocalDateTime timestamp;

    public ErrorDto(String errorMessage, Integer errorCode, String errorPath) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorPath = errorPath;
        this.timestamp = LocalDateTime.now();
    }
}
