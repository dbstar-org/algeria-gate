package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SubscribeProduct extends BaseModel {
    private static final long serialVersionUID = 4157216207858459446L;

    @Schema(description = "phoneNumber")
    private String phoneNumber;

    @Schema(description = "返回码")
    private String resultCode;

    @Schema(description = "业务订购后的帐户状态")
    private String status;

    @Schema(description = "serviceID")
    private String serviceID;
}
