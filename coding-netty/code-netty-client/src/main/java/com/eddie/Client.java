package com.eddie;

import com.eddie.client.WebsocketClient;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author eddie.lys
 * @since 2024/4/7
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        WebsocketClient websocketClient = new WebsocketClient("ws://127.0.0.1:19081/socket.io?channel-auth-user=666");
        websocketClient.doConnect();

        Scanner scanner = new Scanner(System.in); // 创建一个Scanner对象，用于从控制台接收用户输入

        while (true) { // 无限循环，直到用户输入exit命令
            System.out.print("请输入信息（exit退出）:"); // 向控制台输出提示信息
            String message = scanner.nextLine(); // 从控制台读取用户输入的行，并存储在message变量中
            websocketClient.sendMessage(message);
            if (message.equals("exit")) { // 如果用户输入的信息是exit，则退出循环
                break;
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }

}
