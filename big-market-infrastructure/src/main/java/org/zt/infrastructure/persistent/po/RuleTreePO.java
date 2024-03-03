package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/02 14:34
 * @Description: 规则树PO对象
 */

@Data
public class RuleTreePO {

    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;
    /** 规则树名称 */
    private String treeName;
    /** 规则树描述 */
    private String treeDesc;
    /** 规则树根入口规则 */
    private String treeRootRuleKey;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
