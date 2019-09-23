# b2b-mall
基于dubbo的分布式商城

## 项目介绍


- b2b_admin: 是商城后台
- b2b_common: 是常用工具类
- b2b_db: 是数据库的各种操作
- b2b_frontend: 是前台页面
- b2b_sso: 是单点登录页面
- b2b_dubbo_admin: 是单点登录页面


## 技术架构
- 后端采用的技术：   Shiro,Druid,Redis,Jedis,Jackson,JavaMail,AOP,Quartz,Solr,ZooKeeper,Mongodb,Dubbo
- 前端采用的技术：        HTML,JavaScript,Css,Thymeleaf

## dubbo服务
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



## 项目特点
- 友好的代码结构及注释，便于阅读及二次开发
- 实现商城前后台，通过Json和模板进行数据交互
- 引入ActiveMQ消息队列，用于邮件发送
- 引入redis连接池，高效的的获取实时数据
- 引入了jExcelApi，导出ExcelA报表
- 引入了shiro框架，友好的实现权限-角色
- 引入了quartz，实现任务化日志删除
- 引入了aspect写入登录/操作日志到数据库
- 引入了Solr，搜索应用服务器，方便的查找和搜索
- 内置了dubbo的服务与发现系统，无需再次clone其系统

## 项目环境
- 安装 JDK（1.8+）
- 安装 Maven (3.3.0+)
- 安装Redis服务 (3.0+)
- 安装 MySQL (5.6+)
- 安装 mongodb （4.0+）
- 安装 ActivetMQ （5.15.9） 其版本和JDK对应
- 安装 Nginx
- 安装zookeeper（建议使用docker安装）
- 安装IDE （IDEA）
- 安装Solr（需要配置好中文和添加newCore）

#### 启动顺序
b2b_db ->  b2b_common -> b2b_admin 
### 服务启动顺序
dubbo_sso -> dubbo_frontend -> dubbo_item -> dubbo_order -> dubbo_search -> dubbo_cart-> b2b_sso -> b2b_frontend 

## 配置

#### 服务
b2b-dubbo 前缀的服务中，
resource -> application-dubbo-***.yml
```
dubbo:
  application:
    name: dubbo-frontend
  registry:
    address: zookeeper://127.0.0.1:2181
  scan:
    base-packages: com.b2b.dubbo.frontend.service
  protocol:
    name: dubbo
    port: '20884'
```
其中dubbo的端口需要加上'',如果单纯一个dubbo都会默认20880， 但是多服务不使用''来描述端口则会不匹配。导致端口冲突

#### ActiveMq，Redis，Email
application-common
application-admin 均有activemq的配置。
旗下的redis.properties配置redis。
email.properties配置 stmp，需要到邮箱中申请。
在b2b-dubbo 前缀的服务中，均有redis的配置。因为是多端口服务使用redis。

### 数据库
b2b-db  -> sql 文件夹中执行 sql语句，并且在
application-db.properties 中配置数据库

### 商城页面跳转

因为比较匆忙。所以使用纯html+js来写前端。没有用vue，所以这下对于localhost:***/的使用便变得烦琐起来。
b2b-frontend -> resources -> templates --> module 下的footer和category 都有 对localhsot:***的引用。
b2b-mall-sso 同。

## 小结
本项目是基于dubbo的分布式商城。
毕竟是一个练手，并且要上交的项目。所以会过于繁琐。可以自行修改成非分布式。
单纯的把b2b-dubbo-*** 的 service 移动到其他模块中。
注：分布式模块中的Service注解是dubbo的。
现在dubbo以及由apache管理。不要再用alibaba的包了。

#### 相关截图

- ### 后台
  - 登录
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/login.png)
  - DashBoard
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/bashboard.png)
  - 商品
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/shop.png)
  - 订单
  ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/order.png)
  - 权限
   ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/permission.png)
  - 修改权限
   ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/updatepermission.png)
  - 日志
   ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/log.png)

- ### 相关操作
    - 启动zookeeper
    ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/docker.png)
    - 启动solr
    ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/solr.png)
     - 启动redis
     ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/redis.png)
     - 启动服务
     ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/service.png)
     - 启动工程里的dubbo监控
     ![image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/dubbo.png)

- ### 商城主页
     - 主页
     [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/index.png)
     - 单点登录（8087端口）
     [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/sso.png)
      - 购物车
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/cart.png)
      - 订单
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/morder.png)
      - 输入订单
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/orderinput.png)
      - 支付
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/ordersuccess.png)
      - 搜索页
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/searchpage.png)
      - 搜索
      [image text](https://raw.githubusercontent.com/kiwi5691/b2b-mall/master/screenShot/search.png)
     