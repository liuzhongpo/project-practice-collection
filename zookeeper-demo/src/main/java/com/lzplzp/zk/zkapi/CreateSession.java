package com.lzplzp.zk.zkapi;

import org.apache.zookeeper.*;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 15:04
 */
public class CreateSession implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        /**
         * String connectString 连接地址：IP：端⼝
         * int sessionTimeout 会话超时时间：单位毫秒
         * Watcher watcher 监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateSession());
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
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            System.out.println("process execute......");
            //创建节点
            try {
                createNodeSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void createNodeSync() throws Exception {
        /**
         * final String path：节点创建的路径
         * byte[] data,  节点创建要保存的数据，是个byte类型的
         * List<ACL> acl, 节点创建的权限信息(4种类型)
         *      ANYONE_ID_UNSAFE : 表示任何⼈
         *      AUTH_IDS ：此ID仅可⽤于设置ACL。它将被客户机验证的ID替换
         *      OPEN_ACL_UNSAFE ：这是⼀个完全开放的ACL(常⽤)-->world:anyone
         *      CREATOR_ALL_ACL ：此ACL授予创建者身份验证ID的所有权限
         * CreateMode createMode
         *      PERSISTENT：持久节点
         *      PERSISTENT_SEQUENTIAL：持久顺序节点
         *      EPHEMERAL：临时节点
         *      EPHEMERAL_SEQUENTIAL：临时顺序节点
         */
        String nodePersistent = zooKeeper.create("/zk-persistent", "持久节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String nodeEphemeral = zooKeeper.create("/zk-Ephemeral", "临时节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        String nodePersistentSeq = zooKeeper.create("/zk-persistent-seq", "持久顺序节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println(nodePersistent);
        System.out.println(nodeEphemeral);
        System.out.println(nodePersistentSeq);
    }
}
