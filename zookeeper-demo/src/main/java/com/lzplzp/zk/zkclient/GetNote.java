package com.lzplzp.zk.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 16:35
 */
public class GetNote {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 创建⼀个zkClient实例来进⾏连接
         * 注意：zkClient通过对zookeeperAPI内部包装，将这个异步的会话创建过程同步化了
         */
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建了....");

        List<String> children = zkClient.getChildren("/zkclient");
        System.out.println(children);

        /**
         * 客户端可以对⼀个不存在的节点进⾏⼦节点变更的监听
         *
         * ⼀旦客户端对⼀个节点注册了⼦节点列表变更监听之后，那么当该节点的⼦节点列表发⽣变更时，服务端都会通知客户端，并将最新的⼦节点列表发送给客户端
         *
         * 该节点本身的创建或删除也会通知到客户端。
         */
        zkClient.subscribeChildChanges("/zkclient-get", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(s+" 子节点变化后为"+list);
            }
        });

        zkClient.createPersistent("/zkclient-get");
        Thread.sleep(1000);
        zkClient.createPersistent("/zkclient-get/c1");
        Thread.sleep(1000);
        zkClient.delete("/zkclient-get/c1");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
