package io.github.dbstarll.algeria.boot.model.api.response.tone;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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

    @Schema(description = "SP的RBT的排序号")
    private String index;

    @Schema(description = "RBT所属的CP编码")
    private String cpCode;

    @Schema(description = "RBT所属的集团名称")
    private String corpName;

    @Schema(description = "RBT价格")
    private String price;

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

    @Schema(description = "待审批铃音在Web Portal上的播放URL")
    private String toneAddress;

    @Schema(description = "Web Portal上审核铃音的URL地址")
    private String tonePreListenAddress;

    @Schema(description = "IVR Portal上的彩铃地址")
    private String tonePath;

    @Schema(description = "IVR门户审核铃音的地址")
    private String tonePreListenPath;

    @Schema(description = "RBT的状态")
    private String status;

    @Schema(description = "表类型")
    private String tableType;

    @Schema(description = "排行榜信息")
    private String orderInfo;

    @Schema(description = "相对有效期")
    private String relativeTime;

    @Schema(description = "价格组ID")
    private String priceGroupID;

    @Schema(description = "Language ID")
    private String contentLanguage;

    @Schema(description = "上传RBT的类型")
    private String uploadType;

    @Schema(description = "资源的服务类型")
    private String resourceServiceType;
}
