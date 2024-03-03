package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/02 14:38
 * @Description: 规则树节点PO对象
 */

@Data
public class RuleTreeNodePO {

    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 规则比值 */
    private String ruleValue;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
