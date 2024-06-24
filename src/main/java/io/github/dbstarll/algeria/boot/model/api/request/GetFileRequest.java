package io.github.dbstarll.algeria.boot.model.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class GetFileRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -5145180727790207024L;

    @NotBlank
    @Schema(description = "铃音ID")
    private String resourceID;
}
