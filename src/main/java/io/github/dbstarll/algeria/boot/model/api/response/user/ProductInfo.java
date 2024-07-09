package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ProductInfo extends BaseModel {
    private static final long serialVersionUID = -7738686444836061833L;

    @Schema(description = "产品代码")
    private String productID;
    @Schema(description = "用户业务状态")
    private String status;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "初始创建时间")
    private String initialCreateTime;
    @Schema(description = "指示是自动还是手动续订服务")
    private String renewMode;
    @Schema(description = "向用户收取服务月费的截止日期")
    private String monthFeeEndDate;
    @Schema(description = "表示用户是付费产品用户还是试用产品用户")
    private String isTrialUser;
    @Schema(description = "在callshine平台中创建的公司callshine的公司代码")
    private String corpCode;
    @Schema(description = "服务费用类型")
    private String rentType;
    @Schema(description = "订购产品服务时应用的促销类型")
    private String promotionType;
    @Schema(description = "表示用户是否拖欠月费")
    private String isArrear;
    @Schema(description = "在订购期间请求的价格")
    private String requestPrice;
    @Schema(description = "订购服务的持续时间/周期天数")
    private String requestDuration;
    @Schema(description = "根据用户状态订购/退订/暂停/恢复产品服务的渠道")
    private String portalType;
    @Schema(description = "收取产品月租的开始日期")
    private String chargeBeginTime;
    @Schema(description = "指示用户已同意的产品服务的隐私策略版本号")
    private String policyVersionNumber;
    @Schema(description = "表示用户同意产品服务同意书的时间")
    private String consentTime;
    @Schema(description = "产品月租费用")
    private String chargedPrice;
}
