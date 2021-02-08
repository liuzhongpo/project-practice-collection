package com.lzplzp.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 16:35
 */
public class CreateSession {

    public static void main(String[] args) {

        /**
         * 创建⼀个zkClient实例来进⾏连接
         * 注意：zkClient通过对zookeeperAPI内部包装，将这个异步的会话创建过程同步化了
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建了....");
    }
}
