package com.sxz.designpattern;

import com.sxz.designpattern.chain.context.ChainOfResponsibilityContext;
import com.sxz.designpattern.chain.context.Impl.ConcreteChainOfResponsibilityContext;
import com.sxz.designpattern.strategy.context.StrategyContext;
import com.sxz.designpattern.strategy.context.impl.ConcreteStrategyContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @description Policy mode automatic assembly class.
 * @author: sxz
 * @create: 2024-11-28 20:13
 **/
@Configuration
public class DesignPatternAutoConfiguration implements EnvironmentPostProcessor {

    private static String packageName;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        packageName = application.getMainApplicationClass().getPackage().getName();
    }

    @Bean
    public ChainOfResponsibilityContext<?> chainOfResponsibilityContext() {
        return new ConcreteChainOfResponsibilityContext<>(packageName);
    }

    @Bean
    public StrategyContext<?, ?> strategyContext() {
        return new ConcreteStrategyContext<>(packageName);
    }
}
