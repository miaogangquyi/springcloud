
package com.mogo.domain.qc;

import lombok.Data;
import java.util.List;
import com.mogo.annotation.Query;

/**
* @author miaogang
* @date 2020-12-07
**/
@Data
public class DictQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
