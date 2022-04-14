package com.feng.socket.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feng.socket.mapper.SocketMapper;
import com.feng.socket.pojo.Friend;
import com.feng.socket.pojo.WebSocket.Code;
import com.feng.socket.pojo.WebSocket.Fa;
import com.feng.socket.pojo.WebSocket.Online;
import com.feng.socket.pojo.WebSocket.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


@ServerEndpoint(value = "/web/{username}")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    public static SocketMapper socketMapper;

    /**
     * 记录当前在线连接数
     */
    public static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    public static Map<String, Session> saoma = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen        //连接成功时调用
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {
        log.info("{}:连接成功 session:{}", username,session);
        sessionMap.put(username, session);
        if (!isInteger(username)) {
            saoma.put(username,session);
            saoma.get(username).getBasicRemote().sendText("{\"state\":\"测试\"}");
            return;
        }
        Online(username, "grayscale(0%)");
        OnlineFriends(username, session);
    }

    @OnClose    //链接关闭时调用
    public void onClose(Session session, @PathParam("username") String username) {
        log.info("{}:推出链接", username);
        sessionMap.remove(username);
        if (!isInteger(username)) {
            return;
        }
        Online(username, "grayscale(100%)");
    }

    @OnMessage        //连接成功后客户端发过来的消息
    public void onMessage(String message, Session session,
                          @PathParam("username") String username) throws IOException {
        log.info("内容：{}，是：{}", message, session);
        if (!isInteger(username)) {
            Fa fa = JSONObject.parseObject(message, Fa.class);
            System.out.println(fa);
            if(fa.getName() != null){
                System.out.println(saoma.get(fa.getName()).toString());
                saoma.get(fa.getName()).getBasicRemote().sendText(message);
            }
            return;
        }
        Socket socket = JSONObject.parseObject(message, Socket.class);
        send(socket);
    }

    @OnError    //连接错误时调用
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 格式化给用户发消息
     */
    private void send(Socket socket) {
        if ("sb".equals(socket.getType())) {
//            final User use = socketMapper.use(new User(1));
            System.out.println("给" + socket.getTo() + "发");
//            System.out.println(socket.getTo());
            sendMessage(socket, sessionMap.get(socket.getTo()));
        } else if ("broadcast".equals(socket.getType())) {
            System.out.println("给所有人发");
            sendAllMessage(socket, socket.getFrom());
        }
    }

    /**
     * 获取自己在线的好友
     */
    private void OnlineFriends(String username, Session session) throws IOException {
        if (sessionMap.size() < 1) {
            return;
        }
        List<String> onlineFriend = new ArrayList<>();
        List<Friend> friendList = socketMapper.getFriendList(new Friend(Integer.parseInt(username)));
        for (Friend itmeFriend : friendList) {
            for (String itmeMap : sessionMap.keySet()) {
                if (itmeFriend.getWho().toString().equals(itmeMap)) {
                    onlineFriend.add(itmeMap);
                }
            }
        }
        List<Online> onlines = new ArrayList<>();
        for (String item : onlineFriend) {
            onlines.add(new Online("grayscale(0%)", item));
        }
        session.getBasicRemote().sendText(JSON.toJSONString(
                new Code<List<Online>>(2, onlines)));
    }

    private List<Session> Screen(String username) {
        if (sessionMap.size() < 1) {
            return null;
        }
        List<Session> onlineFriend = new ArrayList<>();
        List<Friend> friendList = socketMapper.getFriendList(new Friend(Integer.parseInt(username)));
        for (Friend itmeFriend : friendList) {
            for (String itmeMap : sessionMap.keySet()) {
                if (itmeFriend.getWho().toString().equals(itmeMap)) {
                    onlineFriend.add(sessionMap.get(itmeMap));
                }
            }
        }
        return onlineFriend;
    }

    /**
     * 连接时给好友推送在线
     */
    private void Online(String username, String state) {
        List<Session> onlineFriend = Screen(username);
        if (onlineFriend == null) return;
        onlineFriend.forEach(itme -> {   //给所有在线的好友发送me已经上线
            try {
                itme.getBasicRemote().sendText(JSON.toJSONString(
                        new Code<Online>(1, new Online(state, username))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(Socket socket, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), socket.getMessage());
            toSession.getBasicRemote().sendText(JSON.toJSONString(new Code<Socket>(0, socket)));
        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(Socket socket, String from) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), socket.getMessage());
                if (sessionMap.get(from) == session) {
                    continue;
                }
                session.getBasicRemote().sendText(JSON.toJSONString(new Code<Socket>(0, socket)));
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }


    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
