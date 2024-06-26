package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ProductInfo extends BaseModel {
    private static final long serialVersionUID = -7738686444836061833L;

    private String productID;
    private String status;
    private String createTime;
    private String initialCreateTime;
    private String renewMode;
    private String monthFeeEndDate;
    private String isTrialUser;
    private String corpCode;
    private String rentType;
    private String promotionType;
    private String isArrear;
    private String requestPrice;
    private String requestDuration;
    private String portalType;
    private String chargeBeginTime;
    private String policyVersionNumber;
    private String consentTime;
    private String chargedPrice;
}
