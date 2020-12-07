
package com.mogo.domain.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @description /
* @author miaogang
* @date 2020-12-07
**/
@Data
public class DictDto implements Serializable {

    /** ID */
    private Long dictId;

    /** 字典名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}
