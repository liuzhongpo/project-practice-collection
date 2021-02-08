package com.lzplzp.zk.zkapi;

import org.apache.zookeeper.*;

import java.util.List;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 15:04
 */
public class GetNoteData implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        /**
         * String connectString 连接地址：IP：端⼝
         * int sessionTimeout 会话超时时间：单位毫秒
         * Watcher watcher 监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetNoteData());
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

        if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            try {
                getNoteChildrenData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println("process execute......");
            try {
                getNoteData();
                getNoteChildrenData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void getNoteChildrenData() throws Exception{
        /**
         * String path,路径
         * boolean watch 是否要启动监听，当⼦节点列表发⽣变化，会触发监听
         */
        List<String> children = zooKeeper.getChildren("/zk-persistent", true);
        System.out.println(children);
    }

    private void getNoteData() throws Exception {
        /**
         * String path,路径
         * boolean watch,是否要启动监听，
         * Stat stat 节点状态信息
         *      null 表示获取最新版本的数据
         */
        byte[] data = zooKeeper.getData("/zk-persistent", true, null);
        System.out.println(new String(data));
    }

}
