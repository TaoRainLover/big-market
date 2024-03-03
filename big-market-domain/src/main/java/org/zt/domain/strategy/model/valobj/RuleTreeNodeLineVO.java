package org.zt.domain.strategy.model.valobj;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:14
 * @Description: 规则连线：规则树节点指向线对象，用于衔接 from->to 节点链路关系
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeLineVO {
    // 规则树ID
    private String treeId;
    // 规则 key 节点 from
    private String ruleNodeFrom;
    // 规则 key 节点 to
    private String ruleNodeTo;
    // 限定类型：1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围]
    private RuleLimitTypeVO ruleLimitType;
    // 限定值 (到下一个节点)
    private RuleLogicCheckTypeVO ruleLimitValue;
}
