package com.getgo.test;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by denisr on 7/29/2016.
 */
public class HazelcastTest {


    public static void main(String[]  args) throws InterruptedException {
        Config cfg = new Config();
        cfg.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        cfg.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");
        cfg.getNetworkConfig().getInterfaces().setEnabled(true).addInterface("127.0.0.*");
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);

        BlockingQueue<MyTask> queue = hazelcastInstance.getQueue("tasks");
        queue.put(new MyTask());
        MyTask task = queue.take();
        System.out.println("Got task back "+task);
        boolean offered = queue.offer(new MyTask(), 10, TimeUnit.SECONDS);
        task = queue.poll(5, TimeUnit.SECONDS);
        if (task != null) {
            //process task
        }
    }

}
