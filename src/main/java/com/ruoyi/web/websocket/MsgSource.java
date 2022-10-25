package com.ruoyi.web.websocket;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
 
 
/**
 * 消息产生源，使用一个线程来模拟消息的产生，5秒一个消息。
 * 我实现了ApplicationRunner接口，这样项目一旦启动就有消息产生了
 *
 * @author cloudgyb
 * @since 2022/4/4 21:27
 */
/*
@Component
public class MsgSource implements ApplicationRunner {
    private final WebSocketHandler webSocket;
 
    public MsgSource(WebSocketHandler webSocket) {
        this.webSocket = webSocket;
    }
 
    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            while (true) {
                webSocket.pushMsg("来自末端设备车辆-----经度：38.452,纬度：98.652,高度：80.5,油量：3600");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}*/
