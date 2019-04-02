rd /s /q target
md target
java  -Dfile.encoding=UTF-8 -Djava.ext.dirs=.. -jar mybatis-generator.jar -configfile generator.xml -overwrite                                                  