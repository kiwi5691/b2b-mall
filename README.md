# b2b-mall
基于dubbo的分布式商城

#### 项目介绍


- b2b_admin: 是商城后台
- b2b_common: 是常用工具类
- b2b_db: 是数据库的各种操作
- b2b_frontend: 是前台页面

### dubbo服务
|服务|模块|
|----|----|
|b2b-dubbo-sso|单点登录|
|b2b-dubbo-search|搜索|
|b2b-dubbo-order|订单|
|b2b-dubbo-cart|购物车|
|b2b-dubbo-frontend|前台页面|
|b2b-dubbo-item|商品|

## web应用

|web|模块|地址|
|----|----|----|
|b2b-admin|管理员页面|http://localhost:8081|
|b2b-frontend|商城页面|http://localhost:8088|
|b2b-mall-sso|登录页面|http://localhost:8087|
|b2b-mall-dubbo-admin|dubbo服务与发现页面|http://localhost:7001|

#### 技术架构
- 后端采用的技术：   Shiro,Druid,Redis,Jedis,Jackson,JavaMail,AOP,Quartz
- 前端采用的技术：        HTML,JavaScript,Css,Thymeleaf,Vue.js

#### 项目特点
- 友好的代码结构及注释，便于阅读及二次开发
- 实现商城前后台，通过Json和模板进行数据交互
- 引入ActiveMQ消息队列，用于邮件发送
- 引入redis连接池，高效的的获取实时数据
- 引入了jExcelApi，导出ExcelA报表
- 引入了shiro框架，友好的实现权限-角色
- 引入了quartz，实现任务化日志删除
- 引入了aspect写入登录/操作日志到数据库
- 内置了dubbo的服务与发现系统，无需再次clone其系统
#### 项目环境
- 安装 JDK（1.8+）
- 安装 Maven (3.3.0+)
- 安装Redis服务 (3.0+)
- 安装 MySQL (5.6+)
- 安装 mongodb （4.0+）
- 安装 ActivetMQ （5.15.9） 其版本和JDK对应
- 安装 Nginx
- 安装zookeeper（建议使用docker安装）
- 安装IDE （IDEA）
#### 启动顺序
b2b_db ->  b2b_common -> b2b_admin -> b2b_frontend 
#### 相关截图

- ### 后台

  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/login.png)
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/bashboard.png)
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/shop.png)
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/order.png)
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/kuaidi.png)
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/person.png)

