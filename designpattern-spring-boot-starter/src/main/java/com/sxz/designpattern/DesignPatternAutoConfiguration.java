package com.sxz.designpattern;

import com.sxz.designpattern.chain.context.ChainOfResponsibilityContext;
import com.sxz.designpattern.chain.context.Impl.ConcreteChainOfResponsibilityContext;
import com.sxz.designpattern.strategy.context.StrategyContext;
import com.sxz.designpattern.strategy.context.impl.ConcreteStrategyContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description Policy mode automatic assembly class.
 * @author: sxz
 * @create: 2024-11-28 20:13
 **/
@Configuration
public class DesignPatternAutoConfiguration {

    @Bean
    public ChainOfResponsibilityContext<?> chainOfResponsibilityContext() {
        return new ConcreteChainOfResponsibilityContext<>();
    }

    @Bean
    public StrategyContext<?, ?> strategyContext() {
        return new ConcreteStrategyContext<>();
    }
}
