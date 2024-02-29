package org.zt.domain.strategy.service.rule.chain;

/**
 * @author: Tao
 * @Date: 2024/02/29 13:41
 * @Description:
 */

public abstract class AbstractLogicChain implements ILogicChain{
    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();
}
