# Use

## Strategy pattern

```java
    public static void main(String[] args) {
        int strategy = 1;
        strategyTest(strategy);
    }

    public static void strategyTest(int strategy){
        if (strategy == 1){
            System.out.println(1);
        }else if (strategy == 2){
            System.out.println(2);
        }else if (strategy == 3){
            System.out.println(3);
        }
    }
```

If you have such a piece of code, you can optimize it in the following ways.

```java
    public static void main(String[] args) {
        StrategyContext<String, String> strategyContext = new ConcreteStrategyContext<>();
        int strategy = 1;
        strategyContext.build(String.valueOf(strategy)).execute();
    }

    @Strategy(id = "1")
    public void test001() {
        System.out.println(1);
    }

    @Strategy(id = "2")
    public void test002() {
        System.out.println(2);
    }

    @Strategy(id = "3")
    public void test003() {
        System.out.println(3);
    }
```

You can also write policies by implementing the StrategyHandler class.

```java
package com.sxz.strategy;

import com.sxz.designpattern.annotation.Strategy;
import com.sxz.designpattern.strategy.handler.StrategyHandler;

@Strategy(id = "1")
public class StrategyTest implements StrategyHandler<String, String> {
    @Override
    public void execute() {

    }

    @Override
    public void execute(String arg) {

    }

    @Override
    public String executeAndReturnResults() {
        return null;
    }

    @Override
    public String executeAndReturnResults(String arg) {
        return null;
    }
}
```

If "designpattern-spring-boot-starter" is introduced, you can inject context like this.

```java
    @Resource
    private StrategyContext<?, ?> strategyContext;
```

## Responsibility chain model

The execution order of the specified methods can be realized by means of annotations. The smaller the order value, the sooner the command is executed.

```java
    @Test
    public void test(){
        ChainOfResponsibilityContext<?> context = new ConcreteChainOfResponsibilityContext<>();
        context.build("001").execute();
    }

    @ChainOrder(id = "001", order = 1)
    public void test001() {
        System.out.println("001");
    }

    @ChainOrder(id = "001", order = 2)
    public void test002() {
        System.out.println("002");
    }

    @ChainOrder(id = "001", order = 3)
    public void test003() {
        System.out.println("003");
    }

    @ChainOrder(id = "001", order = 4)
    public void test004() {
        System.out.println("004");
    }

    @ChainOrder(id = "001", order = 0)
    public void test005() {
        System.out.println("005");
    }
```

Of course can also be used by implementing ChainOfResponsibilityHandler class specific link code.

```java
package com.sxz.chain;

import com.sxz.designpattern.annotation.ChainOrder;
import com.sxz.designpattern.chain.handler.ChainOfResponsibilityHandler;

@ChainOrder(id = "001", order = 1)
public class Chain implements ChainOfResponsibilityHandler<String> {
    @Override
    public void handle() {

    }

    @Override
    public void handle(String arg) {

    }
}
```

If "designpattern-spring-boot-starter" is introduced, you can inject context like this.

```java
    @Resource
    private ChainOfResponsibilityContext<?> context;
```

## State mode

The state mode can be used to process related states and control the order in which they are executed.

```java
    @Test
    public void test() {
        StateContext context = new ConcreteStateContext();
        StateHandler stateHandler = context.buildGroup("state-group-001").get("group-001-001");
        //1 1 2 1 3
        stateHandler.handle().handle().next().handle().previous().handle().next().next().handle();

//        context.buildGroup("state-group-001").first()
//                .handle().handle().next().handle().previous().handle().next().next().handle();

//        context.buildGroup("state-group-001").last()
//                .previous().previous().handle().handle().next().handle().previous().handle().next().next().handle();

//        context.buildState("group-001-001")
//                .handle().handle().next().handle().previous().handle().next().next().handle();
    }

    @State(id = "group-001-001", groupId = "state-group-001", order = 1)
    public void first() {
        System.out.println("state-001");
    }

    @State(id = "group-001-002", groupId = "state-group-001", order = 2)
    public void second() {
        System.out.println("state-002");
    }

    @State(id = "group-001-003", groupId = "state-group-001", order = 3)
    public void third() {
        System.out.println("state-003");
    }
```

If "designpattern-spring-boot-starter" is introduced, you can inject context like this.

```java
    @Resource
    private StateContext context;
```


