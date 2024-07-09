package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class UserProductInfo extends BaseModel {
    private static final long serialVersionUID = -3811896688309669608L;

    @Schema(description = "手机号码")
    private String phoneNumber;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "产品信息")
    private List<ProductInfo> productinfos;
}
