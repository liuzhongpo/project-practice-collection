package com.lzplzp.zk.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author thinking
 * @projectName project-practice-collection
 * @date 2021/2/8 18:02
 */
public class GetDataSample {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");

        String path = "/zkclient-ep";

        //判断节点是否存在
        if (!zkClient.exists(path)) {
            zkClient.createEphemeral(path, "123");
        }

        //注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + " 数据内容被修改，修改后为:" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + " 数据内容被删除");
            }
        });

        //获取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);

        //更新
        zkClient.writeData(path, "456");
        Thread.sleep(1000);

        //删除
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
