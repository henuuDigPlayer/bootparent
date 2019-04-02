#!/bin/sh

set -e

basepath=$(cd `dirname $0`; pwd)
cd ${basepath}


rm -fr target
mkdir target
java  -Dfile.encoding=UTF-8 -Djava.ext.dirs=.. -jar mybatis-generator.jar -configfile generator.xml -overwrite