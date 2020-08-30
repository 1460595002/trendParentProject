trendParentProject 趋势投资系统
前端
HTML+CSS+Javascript、JSON、AJAX、JQuery、Bootstrap、Vue.js、chartjs

框架部分
Spring
SpringMVC
SpringBoot
中间件
redis
rabbitMQ
开发使用的工具
Intellij IDEA2020.1
Maven 3.6
分布式
SpringCloud


采用分布式和集群的优点
单个服务功能内聚度高、复杂性低，方便拆分和管理
单独部署，独立开发
扩容增量拓展容易，例如：如果数据微服务不够用，那么新增一个就可以达到缓解一定程度性能上的压力
有各种监控，方便对每个微服务的监管控制
微服务和对应端口总结
eureka-server（注册中心） 8761

third-part-index-data-project（第三方指数数据） 8090

index-gather-store-service（指数收集提供服务） 8001

index-codes-service（指数代码服务） 8011，8012，8013

index-data-service（指数数据服务） 8021，8022，8023

index-zuul-service（zuul网关） 8031

trend-trading-backtest-view（趋势投资回测视图） 8041，8042，8043

trend-trading-backtest-service（趋势投资回测服务） 8051，8052，8053

index-config-server（客户端配置） 8060

index-hystrix-dashboard（断路器监控） 8070

index-turbine（断路器聚合监控） 8080

第三方工具
redis（redis缓存） 6379

zipkin（微服务调用路径监控） 9411

rabbitMQ（消息队列） 5672