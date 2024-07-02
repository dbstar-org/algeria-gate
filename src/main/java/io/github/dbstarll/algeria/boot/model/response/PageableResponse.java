package io.github.dbstarll.algeria.boot.model.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @param <T> contentType
 * @author dbstar
 */
public final class PageableResponse<T> extends BaseModel {
    private static final long serialVersionUID = 6142635926681194577L;

    private final transient Page<T> page;

    private PageableResponse(final Page<T> page) {
        this.page = page;
    }

    /**
     * Returns the page content as List.
     *
     * @return the page content
     */
    @Schema(description = "the page content")
    public List<T> getContent() {
        return page.getContent();
    }

    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    @Schema(description = "the total amount of elements")
    public long getTotalElements() {
        return page.getTotalElements();
    }

    /**
     * Returns the number of total pages.
     *
     * @return the number of total pages
     */
    @Schema(description = "the number of total pages")
    public int getTotalPages() {
        return page.getTotalPages();
    }

    /**
     * Returns the number of elements currently on this Slice.
     *
     * @return the number of elements currently on this Slice.
     */
    @Schema(description = "the number of elements currently")
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    /**
     * 获取分页信息.
     *
     * @return 分页信息
     */
    @Schema(description = "分页信息")
    public PageableData getPageable() {
        return PageableData.of(page.getPageable());
    }

    /**
     * 将Page转换为PageableResponse.
     *
     * @param page Page
     * @param <T>  contentType
     * @return PageableResponse
     */
    public static <T> PageableResponse<T> of(final Page<T> page) {
        return new PageableResponse<>(page);
    }
}
