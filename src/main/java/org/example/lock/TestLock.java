package org.example.lock;

import org.apache.zookeeper.ZooKeeper;
import org.example.config.MyConf;
import org.example.config.ZKUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLock {

    ZooKeeper zk;

    @Before
    public void conn (){
        zk  = ZKUtils.getZk();
    }

    @After
    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Lock(){

        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    // 每一个线程
                    WatchCallBack watchCallBack = new WatchCallBack();
                    watchCallBack.setZk(zk);
                    String threadName = Thread.currentThread().getName();
                    watchCallBack.setThreadName(threadName);
                    // 强锁
                    watchCallBack.tryLock();
                    // 干活
                    System.out.println(threadName + ": working......");
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    // 释放锁
                    watchCallBack.unLock();
                }
            }.start();
        }

        while (true){

        }
    }

}
