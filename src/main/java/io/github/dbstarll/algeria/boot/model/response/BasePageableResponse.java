package io.github.dbstarll.algeria.boot.model.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.StringJoiner;

/**
 * @param <C> type of Content
 * @author dbstar
 */
@Getter
@Setter
public abstract class BasePageableResponse<C> extends BaseModel {
    private static final long serialVersionUID = -5768422714723511579L;

    @Schema(description = "分页信息")
    private PageableData pageable;

    @Schema(description = "the page content")
    private transient List<C> content;

    @Schema(description = "the total amount of elements")
    private long totalElements;

    @Schema(description = "the number of total pages")
    private int totalPages;

    @Schema(description = "the number of elements currently")
    private int numberOfElements;

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner)
                .add("totalElements=" + getTotalElements())
                .add("totalPages=" + getTotalPages())
                .add("numberOfElements=" + getNumberOfElements());
    }
}
