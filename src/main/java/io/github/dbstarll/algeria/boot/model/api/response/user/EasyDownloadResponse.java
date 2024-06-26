package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class EasyDownloadResponse extends BaseResponse {
    private static final long serialVersionUID = 5956122347224505929L;

    @Schema(description = "内容计费信息")
    private List<ContentDownloadInfo> contentDownloadInfo;
}
