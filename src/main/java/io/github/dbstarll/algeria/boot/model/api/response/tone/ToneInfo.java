package io.github.dbstarll.algeria.boot.model.api.response.tone;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class ToneInfo extends BaseModel {
    private static final long serialVersionUID = 4857005713583063968L;

    @Schema(description = "Internal RBT ID")
    private String toneID;

    @Schema(description = "RBT的展示编号")
    private String toneCode;

    @Schema(description = "RBT的长编码")
    private String toneCodeLong;

    @Schema(description = "RBT名称")
    private String toneName;

    @Schema(description = "RBT名称首字母")
    private String toneNameLetter;

    @Schema(description = "歌手名")
    private String singerName;

    @Schema(description = "歌手的性别")
    private String singerSex;

    @Schema(description = "歌手名的首字母")
    private String singerNameLetter;

    @Schema(description = "RBT与运营商约定的歌曲语种")
    private String language;

    @Schema(description = "上传铃音时指定的铃音类型")
    private String category;

    @Schema(description = "RBT分类的名称")
    private String categoryName;

    @Schema(description = "SP的RBT的排序号")
    private String index;

    @Schema(description = "RBT所属的CP编码")
    private String cpCode;

    @Schema(description = "RBT所属的CP名称")
    private String cpName;

    @Schema(description = "RBT所属的集团编号")
    private String corpCode;

    @Schema(description = "RBT所属的集团名称")
    private String corpName;

    @Schema(description = "RBT价格")
    private String price;

    @Schema(description = "总价")
    private String totalPrice;

    @Schema(description = "DIY用户的电话号码")
    private String diyUserPhoneNumber;

    @Schema(description = "PLUS用户的电话号码")
    private String plusUserPhoneNumber;

    @Schema(description = "铃音更新时间")
    private String updateTime;

    @Schema(description = "RBT下载次数")
    private String orderTimes;

    @Schema(description = "RBT设置次数")
    private String setTimes;

    @Schema(description = "审批RBT的时间")
    private String checkTime;

    @Schema(description = "RBT的失效日期")
    private String toneValidDay;

    @Schema(description = "铃音上传时间")
    private String uploadTime;

    @Schema(description = "RBT的描述信息")
    private String info;

    @Schema(description = "待审批铃音在Web Portal上的播放URL")
    private String toneAddress;

    @Schema(description = "Web Portal上审核铃音的URL地址")
    private String tonePreListenAddress;

    @Schema(description = "IVR文件服务器的铃音名称播放地址。用于播放RBT名称的介绍")
    private String toneNameAddress;

    @Schema(description = "Web Portal上歌手名称信息的播放地址")
    private String singerNameAddress;

    @Schema(description = "IVR Portal上的彩铃地址")
    private String tonePath;

    @Schema(description = "IVR门户审核铃音的地址")
    private String tonePreListenPath;

    @Schema(description = "IVR门户中，播放铃音名称简介的地址")
    private String toneNamePath;

    @Schema(description = "IVR门户歌手名简介播放地址")
    private String singerNamePath;

    @Schema(description = "audio目录下设备和铃音存放的相对路径")
    private String deviceAndUrl;

    @Schema(description = "RBT的状态")
    private String status;

    @Schema(description = "表类型")
    private String tableType;

    @Schema(description = "个人铃音库中铃音的编号")
    private String personID;

    @Schema(description = "排行榜信息")
    private String orderInfo;

    @Schema(description = "起始偏移量")
    private String offset;

    @Schema(description = "EndOffset，查询铃音群组中的铃音时返回")
    private String endOffset;

    @Schema(description = "拒绝原因，该参数仅在审批RBT不通过时使用")
    private String rejectReason;

    @Schema(description = "相对有效期")
    private String relativeTime;

    @Schema(description = "RBT在排行榜中的排名")
    private String taxisIndex;

    @Schema(description = "用户在RBT排行榜中下载RBT的次数")
    private String taxisToneDownInfo;

    @Schema(description = "RBT使用的结束日期")
    private String availableDateTime;

    @Schema(description = "审批请求的类型")
    private String askType;

    @Schema(description = "更新时间。表示待审批的记录被更新的次数")
    private String modifyID;

    @Schema(description = "该RBT是否处于待审批标志位")
    private String remark;

    @Schema(description = "铃音的原始下载时间或首次下载时间")
    private String initialDownTime;

    @Schema(description = "允割或不")
    private String cutFlag;

    @Schema(description = "音乐ID")
    private String musicID;

    @Schema(description = "启用日期")
    private String enabledDate;

    @Schema(description = "下载时间")
    private String downTime;

    @Schema(description = "价格组ID")
    private String priceGroupID;

    @Schema(description = "RBT下载价格")
    private String tariffPrice;

    @Schema(description = "RBT的续订模式")
    private String reOrderMode;

    @Schema(description = "歌词版权公司的ID")
    private String lyricCompanyID;

    @Schema(description = "歌词版权的到期日期")
    private String lyricValidDay;

    @Schema(description = "音乐的版权公司ID")
    private String tuneCompanyID;

    @Schema(description = "音乐版权的到期日期")
    private String tuneValidDay;

    @Schema(description = "唱片版权公司的ID")
    private String recordCompanyID;

    @Schema(description = "录制版权的到期日期")
    private String recordValidDay;

    @Schema(description = "Web文件服务器的URL地址，用于播放待审批铃音")
    private String webTonePreListenAddress;

    @Schema(description = "待审批的铃音在AIP上的路径")
    private String aipTonePreListenAddress;

    @Schema(description = "当前操作的原因")
    private String reason;

    @Schema(description = "Language ID")
    private String contentLanguage;

    @Schema(description = "上传RBT的类型")
    private String uploadType;

    @Schema(description = "待审批铃音的AIP URL地址")
    private String aipTonePreListenURL;

    @Schema(description = "资源的服务类型")
    private String resourceServiceType;

    @Schema(description = "复制Live RBT的远程路径")
    private String fileSyncPath;

    @Schema(description = "复制Live RBT内容类型的时间间隔")
    private String fileSyncInterval;

    @Schema(description = "Live RBT开始时间")
    private String beginTime;

    @Schema(description = "Live RBT结束时间")
    private String endTime;

    @Schema(description = "该RBT作为免费RBT被下载的次数")
    private String freeOrderTimes;

    @Schema(description = "CP分成比例")
    private String cpRevSharePercentage;

    @Schema(description = "聚合共享")
    private String aggregatorShare;

    @Schema(description = "CP的类型")
    private String cpType;

    @Schema(description = "推流URL")
    private String streamingURL;

    @Schema(description = "服务代码")
    private String serviceID;

    @Schema(description = "限制或不限制铃音")
    private String unlimitFlag;

    @Schema(description = "表示内容允许的最大下载次数")
    private String maxDownloadLimit;

    @Schema(description = "日费或普通计费铃音")
    private String dailyChargeFlag;

    @Schema(description = "被操作实体的类型")
    private String resourceType;

    @Schema(description = "当triggerType = 1时，为下载内容支付服务费和下载费的群主的群组成员标识")
    private String groupID;

    @Schema(description = "设置铃音盒中铃音的状态")
    private String settingToneStatus;

    @Schema(description = "用户的号码")
    private String phoneNumber;

    @Schema(description = "用户个人铃音库中彩铃的状态")
    private String inboxStatus;

    @Schema(description = "产品订购密钥")
    private String productOrderKey;

    @Schema(description = "费率")
    private String rate;

    @Schema(description = "门户类型")
    private String operator;

    @Schema(description = "商户ID")
    private String merchantID;

    @Schema(description = "PASS的ID")
    private String passID;

    @Schema(description = "RBT的价格。【外部系统产品ID】")
    private String productID;

    @Schema(description = "歌手信息")
    private List<SingerInfo> singerInfos;

    @Schema(description = "Web图像路径")
    private String webImagePath;

    @Schema(description = "wap图片路径")
    private String wapImagePath;

    @Schema(description = "用户是否同意将自己上传的DIY彩铃下载/复制/赠送给其他用户")
    private String userConsent;

    @Schema(description = "下载RBT时使用的关键字")
    private String keyword;

    @Schema(description = "直播彩铃远程同步FTP用户名")
    private String ftpUserName;

    @Schema(description = "直播彩铃远程同步FTP密码")
    private String ftpPassword;

    @Schema(description = "彩铃价格对应的唯一产品id")
    private String chargeProductID;

    @Schema(description = "内容操作时，短信内容和短信头中的smAccessCode")
    private String smAccessCode;

    @Schema(description = "当传入的审批类型参数为1或3时，根据toneCode查询铃音时，系统管理员的处理动作")
    private String adminAct;

    @Schema(description = "是否自动延长铃音的绝对有效期")
    private String extendAbsoluteDateFlag;

    @Schema(description = "待审批HD彩铃在Web Portal上的播放地址。系统管理员在审批HD RBT时，可以试听HD RBT")
    private String hdToneAddress;

    @Schema(description = "HD RBT address")
    private String hdTonePath;

    @Schema(description = "高清RBT地址")
    private String isrc;

    @Schema(description = "全局发布标识符")
    private String grid;

    @Schema(description = "RBT的显示代码")
    private String shortCode;

    @Schema(description = "贷记下载")
    private String creditDownload;

    @Schema(description = "操作重试次数")
    private String retryCount;

    @Schema(description = "最近续订操作重试时间")
    private String lastRenewTryTime;

    @Schema(description = "tone替换次数")
    private String replaceCount;

    @Schema(description = "PersonalLib铃音状态变更时间")
    private String changeTime;

    @Schema(description = "折现率")
    private String requestDiscount;

    @Schema(description = "续订操作失败重试计数")
    private String renewFailureCount;

    @Schema(description = "表示音乐盒可以使用微计费进行计费")
    private String isMicroCharge;

    @Schema(description = "表示音乐盒可以使用debitCharging进行计费")
    private String isDebtCharge;

    @Schema(description = "铃音盒月租结束日期")
    private String monthFeeEndDay;

    @Schema(description = "铃音盒月租开始日期")
    private String nextMonthStartDate;

    @Schema(description = "续订操作成功数")
    private String renewContinueCount;

    @Schema(description = "计费类型")
    private String accountType;

    @Schema(description = "议定价格")
    private String contractPrice;

    @Schema(description = "最近一次成功计费操作的有效时长")
    private String lastChargedDuration;

    @Schema(description = "最近一次成功计费的价格")
    private String lastChargedPrice;

    @Schema(description = "计费价格或计费通话时间值")
    private String multiAccountValue;

    @Schema(description = "内容的原始价格")
    private String requestPrice;

    @Schema(description = "内容的原始时长")
    private String requestDuration;

    @Schema(description = "内容的宽限期")
    private String gracePeriod;
}
