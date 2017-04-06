#!/usr/bin/env bash

CLSNAME=com.xxx.xing.Application

#---------------------------------#
# dynamically build the classpath #
#---------------------------------#
THE_CLASSPATH=
for i in `ls lib/*.jar`
do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done

#-----------------#
# run the program #
#-----------------#
exec java -Xmx1024m -Xms1024m -cp ".:${THE_CLASSPATH}" ${CLSNAME}