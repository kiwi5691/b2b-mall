# b2b-mall
基于Springboot的B2B商城

#### 项目介绍

- b2b_admin: 是商城后台；
- b2b_common: 是常用工具类；
- b2b_db: 是存放初始数据库语句，dao，model
- b2b_frontend: 是商城前台（TODO）


#### 技术架构
- 后端采用的技术：   Shiro,Druid,Redis,Jedis,Jackson,JavaMail
- 前端采用的技术：        HTML,JavaScript,Css,Thymeleaf

#### 项目特点
- 友好的代码结构及注释，便于阅读及二次开发
- 实现商城前后台，通过Json和模板进行数据交互
- 引入RabbitMQ消息队列，用于邮件和短信发送（TODO）
- 引入redis连接池，高效的的获取实时数据
- 引入了jExcelApi，导出ExcelA报表
- 引入了shiro框架，友好的实现权限-角色
#### 项目环境
- 安装 JDK（1.8+）
- 安装 Maven (3.3.0+)
- 安装Redis服务 (3.0+)
- 安装 MySQL (5.6+)
- 安装 mongodb （4.0+）
- 安装 RabbitMQ （3.7.4）
- 安装 Erlang (20.3) （安装RabbitMQ还需要Erlang）
- 安装 Nginx
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

