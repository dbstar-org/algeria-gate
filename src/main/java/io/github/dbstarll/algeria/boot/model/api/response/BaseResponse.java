package io.github.dbstarll.algeria.boot.model.api.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class BaseResponse extends BaseModel {
    private static final long serialVersionUID = 8730985039155524776L;

    public static final String RETURN_CODE_SUCCESS = "000000";

    @Schema(description = "返回码")
    private String returnCode;

    @Schema(description = "The resultCode")
    private String resultCode;

    @Schema(description = "结果信息")
    private String resultInfo;

    @Schema(description = "The operation id")
    private String operationID;

    @Schema(description = "The transaction ID")
    private String transactionID;

    @Schema(description = "eventClassName")
    private String eventClassName;

    /**
     * 检查returnCode是否为成功.
     *
     * @return 调用是否成功
     */
    public final boolean success() {
        return RETURN_CODE_SUCCESS.equals(returnCode);
    }

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner)
                .add("returnCode=" + getReturnCode())
                .add("resultCode=" + getResultCode())
                .add("resultInfo=" + getResultInfo())
                .add("operationID=" + getOperationID())
                .add("transactionID=" + getTransactionID());
    }
}
