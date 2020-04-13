package com.vincenttho.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @className:com.vincenttho.common.model.PageRequest
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/8     VincentHo       v1.0.0        create
 */
@Data
@ApiModel("分页信息")
public class PageCondition {

    @ApiModelProperty("当前页码，默认1")
    private int currentPage = 1;

    @ApiModelProperty("一页显示记录数")
    private int pageSize = 10;

    @ApiModelProperty("排序方式：ASC-升序，DESC-降序")
    private Sort.Direction sortDirection;

    @ApiModelProperty("字段名")
    private String sortColumnName;

    public Pageable getPageable() {
        Sort sort = null;
        if(this.sortDirection != null && Strings.isNotBlank(this.sortColumnName)) {
            sort = new Sort(this.sortDirection, this.sortColumnName);
        } else {
            sort = new Sort(Sort.Direction.DESC, "createDate");
        }
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, sort);
        return pageable;
    }

}