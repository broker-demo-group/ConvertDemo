
wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-amazon2-4.4.15.tgz

tar -zxvf mongodb-linux-x86_64-amazon2-4.4.15.tgz
mv mongodb-linux-x86_64-amazon2-4.4.15 mongodb4
mv mongodb4/ /usr/local/mongodb4/

sudo touch /usr/local/mongodb4/mongodb.conf

echo "storage:
    dbPath: /usr/local/mongodb4/data
systemLog:
    destination: file
    path: /usr/local/mongodb4/log/mongodb.log
    logAppend: true
net:
    bindIp: 0.0.0.0
processManagement:
    fork: true
    " > /usr/local/mongodb4/mongodb.conf

sudo echo PATH=/usr/local/mongodb4/mongodb4/bin:$PATH >>  ~/.bashrc

echo "安装完成 执行命令 mongod -f /usr/local/mongodb4/mongodb.conf 启动服务"