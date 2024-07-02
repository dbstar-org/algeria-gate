package io.github.dbstarll.algeria.boot.model.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author dbstar
 */
@Getter
@Setter
public final class PageableData extends BaseModel {
    private static final long serialVersionUID = 7410097299985378675L;

    private static final Sort DEFAULT_SORT = Sort.by(Order.desc("createTime"));

    public static final int DEFAULT_PAGE_SIZE = 10;

    @Schema(description = "zero-based page index")
    private int pageNumber;
    @Schema(description = "the size of the page to be returned")
    private int pageSize = DEFAULT_PAGE_SIZE;
    @Schema(description = "the sorting parameters")
    private List<OrderData> sort;

    /**
     * 设置新页码并返回自身.
     *
     * @param newPageNumber zero-based page index
     * @return PageableData.
     */
    public PageableData withPage(final int newPageNumber) {
        setPageNumber(newPageNumber);
        return this;
    }

    /**
     * 设置新排序并返回自身.
     *
     * @param newSort the sorting parameters
     * @return PageableData.
     */
    public PageableData withSort(final OrderData... newSort) {
        return withSort(Arrays.asList(newSort));
    }

    /**
     * 设置新排序并返回自身.
     *
     * @param newSort the sorting parameters
     * @return PageableData.
     */
    public PageableData withSort(final List<OrderData> newSort) {
        setSort(newSort);
        return this;
    }

    /**
     * 转换为Pageable.
     *
     * @return Pageable
     */
    public Pageable toPageable() {
        return toPageable(DEFAULT_SORT);
    }

    /**
     * 转换为Pageable，并设置默认排序.
     *
     * @param defaultSort 默认排序
     * @return Pageable
     */
    public Pageable toPageable(final Sort defaultSort) {
        return PageRequest.of(pageNumber, pageSize, toSort(defaultSort));
    }

    /**
     * 转换为Pageable，并强制覆盖排序.
     *
     * @param forceSort 重新指定排序
     * @return Pageable
     */
    public Pageable toPageableForceSort(final Sort forceSort) {
        return PageRequest.of(pageNumber, pageSize, Optional.ofNullable(forceSort).filter(Sort::isSorted)
                .orElseGet(() -> toSort(DEFAULT_SORT)));
    }

    private Sort toSort(final Sort defaultSort) {
        if (CollectionUtils.isEmpty(sort)) {
            return Optional.ofNullable(defaultSort).filter(Sort::isSorted).orElse(DEFAULT_SORT);
        } else {
            return Sort.by(sort.stream().map(OrderData::toOrder).collect(Collectors.toList()));
        }
    }

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner)
                .add("pageNumber=" + getPageNumber())
                .add("pageSize=" + getPageSize())
                .add("sort=" + getSort());
    }

    /**
     * Creates a new PageableData for the first page (page number 0) given pageSize.
     *
     * @param pageSize the size of the page to be returned, must be greater than 0.
     * @return a new PageableData.
     */
    public static PageableData ofSize(final int pageSize) {
        final PageableData data = new PageableData();
        data.pageSize = pageSize;
        return data;
    }

    /**
     * 将Pageable转换为PageableData.
     *
     * @param pageable Pageable
     * @return PageableData
     */
    public static PageableData of(final Pageable pageable) {
        return PageableData.ofSize(pageable.getPageSize())
                .withPage(pageable.getPageNumber())
                .withSort(pageable.getSort().map(OrderData::of).toList());
    }

    @Getter
    @Setter
    public static final class OrderData extends BaseModel {
        private static final long serialVersionUID = -2954028094331447276L;

        @NotBlank
        @Schema(description = "the property to order for")
        private String property;
        @Schema(description = "the order[ASC|DESC] the property shall be sorted for")
        private String direction;

        private Order toOrder() {
            return new Order(StringUtils.isBlank(direction) ? null : Direction.fromString(direction), property);
        }

        @Override
        protected StringJoiner addToStringEntry(final StringJoiner joiner) {
            return super.addToStringEntry(joiner)
                    .add("property=" + getProperty())
                    .add("direction='" + getDirection() + "'");
        }

        private static OrderData of(final Order order) {
            final OrderData data = new OrderData();
            data.direction = order.getDirection().name();
            data.property = order.getProperty();
            return data;
        }

        /**
         * Creates a new OrderData instance. Takes a single property. Direction defaults to
         * {@link Sort#DEFAULT_DIRECTION}.
         *
         * @param property must not be {@literal null} or empty.
         * @return OrderData
         */
        public static OrderData by(final String property) {
            final OrderData data = new OrderData();
            data.property = property;
            return data;
        }

        /**
         * Creates a new OrderData instance. Takes a single property. Direction is {@link Direction#ASC}.
         *
         * @param property must not be {@literal null} or empty.
         * @return OrderData
         */
        public static OrderData asc(final String property) {
            return of(Order.asc(property));
        }

        /**
         * Creates a new OrderData instance. Takes a single property. Direction is {@link Direction#DESC}.
         *
         * @param property must not be {@literal null} or empty.
         * @return OrderData
         */
        public static OrderData desc(final String property) {
            return of(Order.desc(property));
        }
    }
}
