
package com.mogo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author miaogang
 * @date 2020-12-20
 */
@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

    private static final long serialVersionUID = -3908478282318602021L;
    private String title;

    private String icon;

    private Boolean noCache;
}
