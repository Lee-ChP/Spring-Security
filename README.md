# Spring-Security入门到进阶学习
---
## 文件夹说明
+ basic : 超级简单的security入门使用
---
+ loginlogout: 比超级简单难一点点的security的demo，仅仅使用了内存用户做了简单鉴权功能
---
+ loginwithdb: 从数据库获取用户进行校验登录。 前端是抄的。如果初学者想玩复杂一点，记得到更改前端表格的数据绑定，否则无法保存
  + 更新：添加了一个简单的动态权限管理功能
---
+ customedfilter: 自定义一个校验拦截器。前端依旧是抄的，要改实体类属性，记得更改前端数据绑定
---
+ rememberme: 实现一个简单的记住我功能。
---
+ oauth2: 一个简单的oauth2认证demo
---
+ iplogin: 添加一个使用ip登录的demo来理解spring security认证的大概流程