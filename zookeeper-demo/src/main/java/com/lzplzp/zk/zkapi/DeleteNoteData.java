package com.lzplzp.zk.zkapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 15:04
 */
public class DeleteNoteData implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        /**
         * String connectString 连接地址：IP：端⼝
         * int sessionTimeout 会话超时时间：单位毫秒
         * Watcher watcher 监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNoteData());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * ZooKeeper 客户端和服务端会话的建⽴是⼀个异步的过程,构造⽅法会在处理完客户端初始化⼯作后⽴即返回
     * 在⼤多数情况下，此时并没有真正建⽴好⼀个可⽤的会话，在会话的⽣命周期中处于“CONNECTING”的状态
     *
     * 当服务器建立成功的连接后，会发送一个时间通知，会回调process方法，发送过来SyncConnected事件
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {

        /**
         * zooKeeper.exists(path,watch) :判断节点是否存在
         * zookeeper.delete(path,version) : 删除节点
         */
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()) {

            Stat stat = null;
            try {
                stat = zooKeeper.exists("/zk-persistent/c1", false);
                System.out.println(stat == null?"不存在":"存在");

                if(stat != null) {
                    zooKeeper.delete("/zk-persistent/c1",-1);
                }

                stat = zooKeeper.exists("/zk-persistent/c1", false);
                System.out.println(stat == null?"不存在":"存在");

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }


}
