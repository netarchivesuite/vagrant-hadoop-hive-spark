#!/bin/bash
# If you restart your VM then the Hadoop/Spark/Hive services will be started by this script.
# Due to the config "node.vm.provision :shell, path: "scripts/bootstrap.sh", run: 'always'" on Vagrantfile



function setupHosts {
	echo "modifying /etc/hosts file"
	echo "10.211.55.100 master1 master1.hadoop.network" >> /etc/nhosts
	echo "10.211.55.101 worker1 worker1.hadoop.network" >> /etc/nhosts
	echo "10.211.55.102 worker2 worker2.hadoop.network" >> /etc/nhosts
	echo "10.211.55.103 worker3 worker3.hadoop.network" >> /etc/nhosts
	echo "127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4" >> /etc/nhosts
	echo "::1         localhost localhost.localdomain localhost6 localhost6.localdomain6" >> /etc/nhosts
	cp /etc/nhosts /etc/hosts
	rm -f /etc/nhosts
}

setupHosts

cp -f /vagrant/ssh/authorized_keys ~/.ssh/authorized_keys
chmod go-rwx -R ~/.ssh/

#systemctl start mysql.service
/vagrant/scripts/start-hadoop-worker.sh	# Starts the namenode/datanode plus yarn.
#/vagrant/scripts/start-hive.sh		# Start hiveserver2 plus metastore service.
#/vagrant/scripts/start-hbase.sh		# Start HBase
#/vagrant/scripts/start-spark.sh		# Start Spark history server.
