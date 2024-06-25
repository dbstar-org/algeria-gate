package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ServiceOrder extends BaseModel {
    private static final long serialVersionUID = -9006007496782468609L;

    @Schema(description = "订购关系唯一标识定制关系表主键")
    private String orderID;

    @Schema(description = "用户电话号码")
    private String phoneNumber;

    @Schema(description = "服务ID")
    private String serviceID;

    @Schema(description = "服务价格（订阅中）")
    private String price;

    @Schema(description = "订购关系生效时间")
    private String startTime;

    @Schema(description = "订购关系结束时间")
    private String endTime;

    @Schema(description = "服务状态")
    private String serviceStatus;

    @Schema(description = "服务品牌ID")
    private String serviceBrand;

    @Schema(description = "用户配置文件的初始创建时间")
    private String initialCreateTime;

    @Schema(description = "用户业务结束时间")
    private String reserveTime;

    @Schema(description = "表示用户是否欠费")
    private String isArrear;

    @Schema(description = "收取月租的结束日期")
    private String monthFeeEndDay;

    @Schema(description = "注册时间")
    private String registerTime;

    @Schema(description = "上次状态更新时间")
    private String upstatusTime;

    @Schema(description = "上次修改品牌Id的时间")
    private String brandModifyDate;

    @Schema(description = "用户上次下载特定服务的时间")
    private String userDownTime;

    @Schema(description = "操作员类型")
    private String operator;

    @Schema(description = "在triggerType = 1时，为其群组成员下载的内容支付服务费和下载费的群主的ID")
    private String groupID;

    @Schema(description = "收取月租的开始日期")
    private String chargeMonthfeeBeginTime;

    @Schema(description = "收取月租的当月费用")
    private String chargeMonthfeeAmount;

    @Schema(description = "收取月租的结束日期")
    private String chargeMonthfeeEndTime;

    @Schema(description = "用户服务的续订模式")
    private String renewMode;

    @Schema(description = "用户的宽限状态")
    private String graceStatus;

    @Schema(description = "表示外部系统的计费帐户类型")
    private String accountType;

    @Schema(description = "表示计费价格或计费通话时间值")
    private String multiAccountValue;

    @Schema(description = "表示用户同意的RBT业务的隐私策略版本号")
    private String policyVersionNumber;

    @Schema(description = "表示用户同意RBT业务的时间")
    private String consentTime;
}
