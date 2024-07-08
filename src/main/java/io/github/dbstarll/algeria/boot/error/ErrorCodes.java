package io.github.dbstarll.algeria.boot.error;

public final class ErrorCodes {
    private ErrorCodes() {
        // 工具类禁止公共实例化
    }

    public static final int SUCCESS = 0;

    public static final int VALIDATION_FAILED = 400000;
    public static final int INVALID_VERIFY_CODE = 400001;

    public static final int INVALID_ACCESS_TOKEN = 401001;

    public static final int UN_SUBSCRIBE = 403001;

    public static final int UNKNOWN = 500000;
    public static final int UN_CAUGHT = 500001;
    public static final int FREQUENTLY_OBTAIN_VERIFY_CODE = 500103;

    public static final int RBT_API_FAILED = 500201;
}
