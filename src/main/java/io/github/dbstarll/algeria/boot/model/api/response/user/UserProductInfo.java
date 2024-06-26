package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class UserProductInfo extends BaseModel {
    private static final long serialVersionUID = -3811896688309669608L;

    private String phoneNumber;
    private String password;
    private List<ProductInfo> productinfos;
}
