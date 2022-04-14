package com.feng.socket.controller;

import com.feng.socket.mapper.SocketMapper;
import com.feng.socket.pojo.*;
import com.feng.socket.pojo.main.LikeWork;
import com.feng.socket.utils.*;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/api/info")
public class info {

    @Autowired
    private SocketMapper socketMapper;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;



    @PostMapping("/Friend")
    @ApiModelProperty("上传好友")
    @ResponseBody
    public R Friend(@RequestBody User use1) {
        final Map<String, Object> map = new HashMap<>();
        List<User> uses = new ArrayList<>();
        List<Friend> friendList = socketMapper.getFriendList(new Friend(use1.getU_id()));
        friendList.forEach((item) -> {
            User use = socketMapper.use(new User(item.getWho()));
            use.setPassword("");
            use.setHead(GlobalConstant.BASE_URL + "/ReallyShare/user/" + use.getU_id() + "/info/" + use.getHead());
            uses.add(use);
        });
        map.put("list", uses);
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/Video")
    @ApiModelProperty("视频")
    @ResponseBody
    public R Video(@RequestParam("file") MultipartFile file,
                   @RequestParam("u_id") String u_id,
                   @RequestParam("text") String title,
                   @RequestParam("value") String value
    ) throws Exception {
        System.out.println(title);
        System.out.println(value);
        final Map<String, Object> map = new HashMap<>();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename1 = UUID.randomUUID().toString();
        String filename = filename1 + suffix;
//        String filename = name + suffix;
        final WorkType workType = socketMapper.getWorkType(new WorkType(null, value, null));
        socketMapper.SetWork(new Work(Integer.parseInt(u_id), title, filename, workType.getWt_id()));
        final List<Work> work = socketMapper.getWork(new Work(filename));
        if (work.size() > 0) {
            final Integer w_id = work.get(0).getW_id();
            FileUtils.CreateWorkFile(u_id, w_id.toString());
            //服务器端保存的文件对象
            File serverFile = new File("D:/ReallyShare/user/" + u_id + "/work/" + w_id + "/" + w_id + suffix);
            //将上传的文件写入到服务器端文件内
            file.transferTo(serverFile);

            ImageUtils.fetchFrame(serverFile.getCanonicalPath(), "D:/ReallyShare/user/" + u_id + "/work/" + w_id + "/" + w_id + ".jpg");
            map.put("file", serverFile.getCanonicalPath().split(":")[1]);
            VideoWatermarkUtils.addTextByGraphics("D:/ReallyShare/user/" + u_id + "/work/" + w_id + "/" + w_id + suffix,"D:/ReallyShare/user/" + u_id + "/work/" + w_id + "/" + w_id+"_S" + suffix,"真享视频");
        }
        return R.ok().message("上传成功").data(map);
    }

    @PostMapping("/like")
    @ApiModelProperty("点赞")
    @ResponseBody
    public R Like1(@RequestBody LikeWork likeWork) {
        final Map<String, Object> map = new HashMap<>();
        log.info("用户：{},作品：{} 状态：" + (likeWork.getLike() ? "点赞" : "取消"), likeWork.getU_id(), likeWork.getW_id());
        if (likeWork.getLike()) {
            socketMapper.likeWork(new Likes(likeWork.getU_id(), likeWork.getW_id()));
            map.put("state", "点赞成功");
        } else {
            socketMapper.unLikeWork(new Likes(likeWork.getU_id(), likeWork.getW_id()));
            map.put("state", "取消点赞");
        }
        return R.ok().data(map);
    }

    @PostMapping("/isLike")
    @ApiModelProperty("是否点赞")
    @ResponseBody
    public R isLike(@RequestBody LikeWork likeWork) {
        Map<String, Object> map = new HashMap<>();
        List<Likes> likeWork1 = socketMapper.isLikeWork(new Likes(likeWork.getU_id(), likeWork.getW_id()));
        if (likeWork1.size() > 0) {
            map.put("state", true);
        } else {
            map.put("state", false);
        }
        return R.ok().data(map);
    }

    @PostMapping("/collect")
    @ApiModelProperty("收藏")
    @ResponseBody
    public R Collect(@RequestBody LikeWork likeWork) {
        final Map<String, Object> map = new HashMap<>();
        log.info("用户：{},作品：{}     ==  " + likeWork.getLike() + "  === 状态：" + (likeWork.getLike() ? "收藏" : "取消"), likeWork.getU_id(), likeWork.getW_id());
        if (likeWork.getLike()) {
            final Integer integer = socketMapper.collectWork(new Collect(likeWork.getU_id(), likeWork.getW_id()));
            System.out.println(integer == 1 ? "成功" : "错误");
            map.put("state", "收藏成功");
        } else {
            socketMapper.unCollectWork(new Collect(likeWork.getU_id(), likeWork.getW_id()));
            map.put("state", "收藏点赞");
        }
        return R.ok().data(map);
    }

    @PostMapping("/isCollect")
    @ApiModelProperty("是否收藏")
    @ResponseBody
    public R isCollect(@RequestBody LikeWork likeWork) {
        Map<String, Object> map = new HashMap<>();
        List<Collect> likeWork1 = socketMapper.isCollectWork(new Collect(likeWork.getU_id(), likeWork.getW_id()));
        if (likeWork1.size() > 0) {
            map.put("state", true);
        } else {
            map.put("state", false);
        }
        return R.ok().data(map);
    }


    @PostMapping("/Attention")
    @ApiModelProperty("关注")
    @ResponseBody
    public R Attention(@RequestBody LikeWork likeWork) {
        final Map<String, Object> map = new HashMap<>();
        log.info("用户：{},用户：{}     ==  " + likeWork.getLike() + "  === 状态：" + (likeWork.getLike() ? "收藏" : "取消"), likeWork.getU_id(), likeWork.getW_id());
        if (likeWork.getLike()) {
            socketMapper.addAttention(new Friend(likeWork.getU_id(), likeWork.getW_id()));
            map.put("state", "收藏成功");
        } else {
            socketMapper.unAttention(new Friend(likeWork.getU_id(), likeWork.getW_id()));
            map.put("state", "收藏点赞");
        }
        return R.ok().data(map);
    }

    @PostMapping("/isAttention")
    @ApiModelProperty("是否收藏")
    @ResponseBody
    public R isAttention(@RequestBody LikeWork likeWork) {
        Map<String, Object> map = new HashMap<>();
        List<Friend> likeWork1 = socketMapper.isAttention(new Friend(likeWork.getU_id(), likeWork.getW_id()));
        if (likeWork1.size() > 0) {
            map.put("state", true);
        } else {
            map.put("state", false);
        }
        return R.ok().data(map);
    }

}
