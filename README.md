数据同步工具
1.项目采用spirngboot和netty框架以及多数据源等技术来实现
2.该项目通过读取binlog日志，获取数据库数据改变时的sql，并根据相关的数据同步配置，将sql通过netty服务端发送给需要同步的客户端，netty客户端在拿到sql之后，
通过拆分sql，获取相关的数据库连接标识，从而获取相应的数据库连接JdbcTemplete,执行sql，从而达到将数据同步到目标数据库的目的。
3.该项目需配置两个数据库，一个主库（TDCP），用于支撑软件运行，一个配置库(SYNC_CONF)，用于配置数据源、客户端以及数据同步的相关配置，无论启多少服务，共用一个
配置库，保证配置信息共用
4.此版本数据同步，采用统一配置，代码生成已适配，ruoyi包名未修改，
已支持基于binlog的数据增量同步，暂时不支持异构数据同步，
支持基于逻辑备份的导入导出，支持数据同步节点配置信息可视化查看
访问地址：http://localhost:8002/dblog/monitor/
5.支持浏览数据库数据
6.如何开启binlog日志，打开神通数据库安装目录，打开配置文件ShenTongDataSource->admin->OSRDB.conf，将属性ENABLE_BINLOG设置为true
默认值为false，修改完成后，重启神通数据库实例
7.查询binlog日志文件的sql：select logfile_name, file_size from v_sys_binary_logs
    logfile_name:binlog日志文件名称
    file_size:可以看作时该日志文件的最大位移
8.查询日志内容的sql:SELECT * FROM show_binlog_events(file, 0, 0, 0) 
    参数说明:
            file:文件名称                 字符串类型
            0(第一个):开启的偏移量          数值类型
            0(第二个):跳过的时间数量         数值类型
            0(第三个):要查询的数据量          数值类型
    例:SELECT * FROM show_binlog_events('D:\ShenTong\odbs\OSRDB\OSRDB-bin.000002', 0, 0, 100000)
        


