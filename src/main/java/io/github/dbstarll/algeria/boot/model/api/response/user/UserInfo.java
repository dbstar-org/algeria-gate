package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class UserInfo extends BaseModel {
    private static final long serialVersionUID = -4859419837870892178L;

    @Schema(description = "所有符合条件的业务订购关系数组")
    private List<ServiceOrder> serviceOrders;

    @Schema(description = "用户的MSISDN号码")
    private String phoneNumber;

    @Schema(description = "用户姓名")
    private String name;

    @Schema(description = "用户的E-mail地址")
    private String email;

    @Schema(description = "表示用户是否允许别人复制自己的RBT")
    private String isUseCopy;

    @Schema(description = "用户状态")
    private String status;

    @Schema(description = "Subscriber type")
    private String type;

    @Schema(description = "用户品牌ID")
    private String brand;

    @Schema(description = "用户品牌名称")
    private String brandName;

    @Schema(description = "用户区域")
    private String region;

    @Schema(description = "用户区域名称")
    private String regionName;

    @Schema(description = "注册时间")
    private String registerTime;

    @Schema(description = "用户类型，欠费或未欠费")
    private String isArrear;

    @Schema(description = "集团ID")
    private String corpID;

    @Schema(description = "集团代码")
    private String corpCode;

    @Schema(description = "集团名称")
    private String corpName;

    @Schema(description = "门户类型")
    private String operator;

    @Schema(description = "是否播放带背景音的RBT")
    private String bgTone;

    @Schema(description = "用户品牌类型")
    private String subCosID;

    @Schema(description = "用户业务结束时间")
    private String reserveTime;

    @Schema(description = "用户RBT的播放方式")
    private String playMode;

    @Schema(description = "用户网络类型")
    private String netKind;

    @Schema(description = "总积分")
    private String totalLoyaltyPonints;

    @Schema(description = "模式的ID")
    private String moodModeID;

    @Schema(description = "收取月租的结束日期")
    private String monthFeeEndDay;

    @Schema(description = "用户的LRN")
    private String lrn;

    @Schema(description = "订户的名字")
    private String firstName;

    @Schema(description = "订户的姓氏")
    private String lastName;

    @Schema(description = "订阅者的国籍")
    private String nationality;

    @Schema(description = "订户的出生日期")
    private String dateOfBirth;

    @Schema(description = "订户的性别")
    private String sex;

    @Schema(description = "订户所在城市")
    private String city;

    @Schema(description = "用户配置文件的初始创建时间")
    private String initialCreateTime;

    @Schema(description = "收取月租的开始日期")
    private String chargeMonthfeeBeginTime;

    @Schema(description = "收取月租的当月费用")
    private String chargeMonthfeeAmount;

    @Schema(description = "收取月租的结束日期")
    private String chargeMonthfeeEndTime;

    @Schema(description = "当前本地ID")
    private String currentLocal;

    @Schema(description = "限制或不限制铃音")
    private String unlimitFlag;

    @Schema(description = "指示是否已收到外部系统请求的回调")
    private String httpCallBackFlag;

    @Schema(description = "音乐俱乐部ID")
    private String musicClubId;

    @Schema(description = "音乐俱乐部积分")
    private String musicClubPoints;

    @Schema(description = "在triggerType = 1时，为其群组成员下载的内容支付服务费和下载费的群主的ID")
    private String groupID;

    @Schema(description = "用户服务的续订模式")
    private String renewMode;

    @Schema(description = "短信预通知标志")
    private String preNoticeFlag;

    @Schema(description = "当前状态更新时间")
    private String updateStatusTime;

    @Schema(description = "语言ID")
    private String languageID;

    @Schema(description = "锁定状态，表示帐户是否被锁定")
    private String isLocked;

    @Schema(description = "操作员被锁定的时间")
    private String lockedTime;

    @Schema(description = "操作员第一次解锁失败的时间")
    private String firstFailTime;

    @Schema(description = "锁定类型")
    private String lockType;

    @Schema(description = "锁定帐户的原因")
    private String lockReason;

    @Schema(description = "表示外部系统的计费帐户类型")
    private String accountType;

    @Schema(description = "计费价格或计费通话时间值")
    private String multiAccountValue;

    @Schema(description = "表示用户同意的RBT业务的隐私策略版本号")
    private String policyVersionNumber;

    @Schema(description = "表示用户同意RBT业务的时间")
    private String consentTime;

    @Schema(description = "用户的上次登录时间")
    private String lastLoginTime;
}
