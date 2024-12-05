package com.sxz.designpattern;

import com.sxz.designpattern.chain.context.ChainOfResponsibilityContext;
import com.sxz.designpattern.chain.context.Impl.ConcreteChainOfResponsibilityContext;
import com.sxz.designpattern.context.PackageContext;
import com.sxz.designpattern.state.context.StateContext;
import com.sxz.designpattern.state.context.impl.ConcreteStateContext;
import com.sxz.designpattern.strategy.context.StrategyContext;
import com.sxz.designpattern.strategy.context.impl.ConcreteStrategyContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author suxz
 **/
@Configuration
public class DesignPatternAutoConfiguration implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String packageName = application.getMainApplicationClass().getPackage().getName();
        PackageContext.setPackage(packageName);
    }

    @Bean
    public ChainOfResponsibilityContext<?> chainOfResponsibilityContext() {
        return new ConcreteChainOfResponsibilityContext<>();
    }

    @Bean
    public StrategyContext<?, ?> strategyContext() {
        return new ConcreteStrategyContext<>();
    }

    @Bean
    public StateContext stateContext() {
        return new ConcreteStateContext();
    }
}
