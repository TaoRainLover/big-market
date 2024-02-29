package org.zt.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zt.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import org.zt.types.common.Constants;

import java.util.ArrayList;

/**
 * @author: Tao
 * @Date: 2024/02/28 14:19
 * @Description:
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {
    private String ruleModels;

    public String[] raffleCenterRuleModelList() {
        ArrayList<String> ruleModelList = new ArrayList<>();
        String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue : ruleModelValues) {
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)) {
                ruleModelList.add(ruleModelValue);
            }
        }

        return ruleModelList.toArray(new String[0]);
    }
}
