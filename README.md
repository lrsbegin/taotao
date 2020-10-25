技术选型
Spring、SpringMVC、Mybatis
JSP、JSTL、jQuery、jQuery plugin、EasyUI、KindEditor（富文本编辑器）、CSS+DIV
Redis（缓存服务器）
Solr（搜索）
httpclient（调用系统服务）
Mysql
Nginx（web服务器）
工程总览
使用了面向服务的架构（Service-Oriented Architecture， SOA）

数据库
mysql作为关系型数据库，提供数据的持久化。
redis作为内存数据库，提供高性能的缓存。
服务层
taotao-rest提供商品信息，内容服务。
taotao-sso提供单点登录服务。
taotao-order提供订单服务。
taotao-search提供商品搜索服务，基于solr索引库。
交互
taotao-portal作为网站的门户，与用户交互。
微信商城之类的app也可以调用服务层的服务，完成门户的功能。
taotao-manager作为后台管理，供管理员完成对网站商品和内容的增删改查。
