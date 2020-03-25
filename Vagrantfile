# -*- mode: ruby -*-
# vi: set ft=ruby :
#
# Changes made to support docker as a provider.
# 
Vagrant.require_version '>= 1.4.3'
VAGRANTFILE_API_VERSION = '2'.freeze

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
    ##config.vm.network "private_network", ip: "172.17.0.3"
    ##config.vm.synced_folder "/home/csr/DAB/dabdata", "/host_dabdata"
    config.vm.provider "docker" do |d|
	    d.image = "nishidayuya/docker-vagrant-ubuntu:xenial"
	    d.has_ssh = true
	    ##d.volumes = ["/vagrant/dabdata:/dabdata"]
    end
    config.vm.provider "virtualbox" do |v, override|
	    override.vm.box = "ubuntu/xenial64"
	    v.gui = false
        v.customize ['modifyvm', :id, '--memory', '8192']
    end
    config.ssh.forward_agent = true




    ##config.vm.network "forwarded_port", guest: 8020, host: 8020
    config.vm.define "master1" do |node|
        node.vm.network :private_network, ip: '10.211.55.100', subnet: "10.211.55.0/24"
        node.vm.hostname = 'master1.hadoop.network'
        node.vm.provision :shell, path: 'scripts/setup-ubuntu.sh', name: 'setup-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/setup-java.sh', name: 'setup-java.sh'
        node.vm.provision :shell, path: 'scripts/setup-hadoop-master.sh', name: 'setup-hadoop-master.sh'
        node.vm.provision :shell, path: 'scripts/finalize-ubuntu.sh', name: 'finalise-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/bootstrap-master.sh', run: 'always'
    end

    config.vm.define "worker1" do |node|
        node.vm.network :private_network, ip: '10.211.55.101', subnet: "10.211.55.0/24"
        node.vm.hostname = 'worker1.hadoop.network'
        node.vm.provision :shell, path: 'scripts/setup-ubuntu.sh', name: 'setup-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/setup-java.sh', name: 'setup-java.sh'
        node.vm.provision :shell, path: 'scripts/setup-hadoop-worker.sh', name: 'setup-hadoop-worker.sh'
        node.vm.provision :shell, path: 'scripts/finalize-ubuntu.sh', name: 'finalise-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/bootstrap-worker.sh', run: 'always'
     end

    config.vm.define "worker2" do |node|
        node.vm.network :private_network, ip: '10.211.55.102', subnet: "10.211.55.0/24"
        node.vm.hostname = 'worker2.hadoop.network'
        node.vm.provision :shell, path: 'scripts/setup-ubuntu.sh', name: 'setup-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/setup-java.sh', name: 'setup-java.sh'
        node.vm.provision :shell, path: 'scripts/setup-hadoop-worker.sh', name: 'setup-hadoop-worker.sh'
        node.vm.provision :shell, path: 'scripts/finalize-ubuntu.sh', name: 'finalise-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/bootstrap-worker.sh', run: 'always'
    end

   config.vm.define "worker3" do |node|
        node.vm.network :private_network, ip: '10.211.55.103', subnet: "10.211.55.0/24"
        node.vm.hostname = 'worker3.hadoop.network'
        node.vm.provision :shell, path: 'scripts/setup-ubuntu.sh', name: 'setup-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/setup-java.sh', name: 'setup-java.sh'
        node.vm.provision :shell, path: 'scripts/setup-hadoop-worker.sh', name: 'setup-hadoop-worker.sh'
        node.vm.provision :shell, path: 'scripts/finalize-ubuntu.sh', name: 'finalise-ubuntu.sh'
        node.vm.provision :shell, path: 'scripts/bootstrap-worker.sh', run: 'always'
   end


end
