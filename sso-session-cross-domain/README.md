* Session跨域 

    所谓Session跨域就是摒弃了系统（Tomcat）提供的Session，而使用自定义的类似Session的机制来保存客户端数据的一种解决方案。
    比如：通过设置cookie的domain来实现cookie的跨域传递，在cookie中传递一个自定义的session_id。这个session_id是客户端的唯一标记，
        将这个标记作为key，将客户端需要保存的数据作为value，在服务端进行保存（数据库保存或NoSQL保存）。这种机制就是Session的跨域解决。
        
    什么是跨域：  
        客户端请求的时候，请求的服务器，不是同一个IP，端口，域名，主机名的时候，都称为跨域。
    
    什么是域：
        在应用模型中，一个完整的，有独立访问路径的功能集合称为一个域。
        如：百度称为一个应用或系统。百度下有若干的域。
            如：搜索引擎（www.baidu.com），百度贴吧（tie.baidu.com），百度知道（zhidao.baidu.com），百度地图（map.baidu.com）等。域信息，有时也称为多级域名。域的划分： 以IP，端口，域名，主机名为标准，实现划分。
        
    localhost / 127.0.0.1
    
    使用cookie跨域共享，是session跨域的一种解决方案。
    jsessionid是和servlet绑定的httpsession的唯一标记。
    
    cookie应用 - new Cookie("", "").  
    request.getCookies() -> cookie[] -> 迭代找到需要使用的cookie
    response.addCookie().
    cookie.setDomain() - 为cookie设定有效域范围。
    cookie.setPath() - 为cookie设定有效URI范围。

* Spring Session共享

    spring-session技术是spring提供的用于处理集群会话共享的解决方案，
        spring-session技术是将用户session数据保存到三方存储容器中，如：mysql，redis等。
        
    spring-session技术是解决同域名下的多服务器集群session共享问题的，不能解决跨域session共享问题。   
     
    使用：配置一个Spring提供的Filter，实现数据的拦截保存，并转换为spring-session需要的会话对象。
            必须提供一个数据库的表格信息（由spring-session提供，找spring-session-jdbc.jar/org/springframework/session/jdbc/*.sql,
            根据具体的数据库找对应的SQL文件，做表格的创建）。
    
    spring-session表：保存客户端session对象的表格。
    spring-session-attributes表：保存客户端session中的attributes属性数据的表格。
    spring-session框架，是结合Servlet技术中的HTTPSession完成的会话共享机制。在代码中是直接操作HttpSession对象的
        步骤：
            1.客户端请求
            2.Tomcat，根据具体的应用名称分发到具体的应用中
            3.过滤器Filter，DelegatingFilterProxy。工作原理：过滤所有的请求，控制HttpSession和数据库中的数据，实现统一。
            4.JdbcHttpSessionConfiguration。是具体的用于实现HttpSession和数据库数据统一的工具。实现增删改查功能的具体逻辑。
            通过配置的形式定义Session的有效期等。Configuration对象不需要定义id或者name，因为在DelegatingFilterProxy中是byType获取Configuration对象。
            5.数据库
    
* Nginx Session共享
    做反向代理服务器，可以为反向代理的服务器集群做集群管理和负载均衡。
    正向代理：对客户端已知，对服务端透明的代理应用，称为正向代理。如：翻墙工具。
    反向代理：对服务器已知，对客户端透明的代理应用，称为反向代理。如：nginx。
    Nginx服务器一旦安装，一般提供7*24小时服务，建议安装在服务器中（如：Unix、Linux）
    Nginx是一个C语言开发的应用服务器。可以提供的服务有：静态WEB服务（Apache http server），邮件代理服务器，虚拟机，反向代理服务器。
    Nginx应用体积非常小，对CPU和内存的要求也很低。且对负载能力有非常好的体现。核心功能是应用自主开发，
        很多的附属功能都是借助其他的应用实现，如：SSL协议的解析-opensll，perl库（正则）的解析-perl包实现。
        
    Nginx安装成功后，在安装位置有三个目录。sbin/conf/html。sbin是可执行文件，html是nginx提供的默认静态页面，conf是配置文件目录。
    
    nginx的ip_hash技术能够将某个ip的请求定向到同一台后端，这样一来这个ip下的某个客户端和某个后端就能建立起稳固的session，
        ip_hash是在upstream配置中定义的。







