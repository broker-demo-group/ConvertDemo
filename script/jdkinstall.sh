#!/bin/bash
# jdk install
#sh jdkinstall.sh jdk-***-linux-x64.tar.gz
jdkfile=$1
echo  $jdkfile
if [ -z "$jdkfile" ]
then
    echo "Please specify the jdk file(like [sh jdkinstall.sh jdk-8**-linux-x64.tar.gz]) what you need to install .The
     jdk file can be download in https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

ipath="/usr/local"
installpath=$(cd `dirname $0`; pwd)
j=`whereis java`
java=$(echo ${j} | grep "jdk")

if [[ "$java" != "" ]]
then
    echo "java was installed!"
else
    echo "java not installed!"
    echo;
    echo;
    echo "解压 jdk-*-linux-x64.tar.gz"
    tar -zxvf ${jdkfile} >/dev/null 2>&1
    echo;
    echo;
    cd jdk* && jdkname=`pwd | awk -F '/' '{print $NF}'`
    echo "获取jdk版本: ${jdkname}"
    echo;
    cd ${installpath}
    echo "获取当前目录:${installpath}"
    echo;
    mv ${jdkname} ${ipath}
    echo "转移${jdkname}文件到${ipath}安装目录"
    echo "jdk安装目录:${ipath}/${jdkname}"
    echo;
    sudo !echo "#java jdk" >> /etc/profile
    echo "export JAVA_HOME=${ipath}/${jdkname}" >> /etc/profile
    echo 'export JRE_HOME=${JAVA_HOME}/jre' >> /etc/profile
    echo 'export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib' >> /etc/profile
    echo 'export PATH=${JAVA_HOME}/bin:$PATH' >> /etc/profile
    source /etc/profile > /dev/null 2>&1
    echo "jdk 安装完毕!"
    echo;
    echo "请执行以下命令以使jdk环境生效:"
    echo "source /etc/profile"
fi