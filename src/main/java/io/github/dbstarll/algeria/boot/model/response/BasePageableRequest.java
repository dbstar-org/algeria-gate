package io.github.dbstarll.algeria.boot.model.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * @param <T> Bean extends BasePageRequest
 * @author dbstar
 */
public abstract class BasePageableRequest<T extends BasePageableRequest<T>> extends BaseModel {
    private static final long serialVersionUID = -1678156749200893840L;

    @Schema(description = "分页信息")
    private PageableData pageable;

    /**
     * 获取分页信息.
     *
     * @return 分页信息
     */
    public final PageableData getPageable() {
        return pageable;
    }

    /**
     * 设置分页信息.
     *
     * @param pageable 分页信息
     */
    public final void setPageable(final PageableData pageable) {
        this.pageable = pageable;
    }

    /**
     * 设置分页信息并返回自身.
     *
     * @param newPageable 分页信息
     * @return Bean extends BasePageRequest
     */
    @SuppressWarnings("unchecked")
    public final T withPageable(final PageableData newPageable) {
        setPageable(newPageable);
        return (T) this;
    }

    /**
     * 转换为Pageable.
     *
     * @return Pageable
     */
    public final Pageable toPageable() {
        final PageableData pageableData = Optional.ofNullable(pageable).orElseGet(PageableData::new);
        return Optional.ofNullable(forceSort()).filter(Sort::isSorted)
                .map(pageableData::toPageableForceSort)
                .orElseGet(() -> pageableData.toPageable(defaultSort()));
    }

    /**
     * 设置强制覆盖排序.
     *
     * @return Sort
     */
    protected Sort forceSort() {
        return null;
    }

    /**
     * 设置默认排序.
     *
     * @return Sort
     */
    protected Sort defaultSort() {
        return null;
    }

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("pageable=" + getPageable());
    }
}
