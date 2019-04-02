1. 修改xml文件中的数据库连接信息
2. 进入当前目录执行以下命令：
    java -jar mybatis-generator.jar -configfile generator.xml -overwrite
    或直接执行generator.bat
3. mapper、xml文件会生成到mapper目录下
4. 实体、example生成到model目录下