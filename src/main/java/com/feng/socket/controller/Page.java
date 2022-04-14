package com.feng.socket.controller;


import com.feng.socket.mapper.SocketMapper;
import com.feng.socket.pojo.*;
import com.feng.socket.pojo.main.LikeWork;
import com.feng.socket.utils.*;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/page")
class Page {


    @Autowired
    private SocketMapper socketMapper;

    @PostMapping("/navZ")
    @ApiModelProperty("菜单")
    public R navZ() {
        HashMap<String, Object> map = new HashMap<>();
        List<WorkType> type = socketMapper.getType();
        map.put("list", type);
        return R.ok().message("成功").data(map);
    }

    @PostMapping("/offer")
    @ApiModelProperty("推荐作品")
    @ResponseBody
    public R videos(@RequestBody Work work) {
        final Map<String, Object> map = new HashMap<>();
        final ArrayList<Object> objects = new ArrayList<>();
        final List<Work> randomWork = socketMapper.RandomWork(new Work(null, null, null, work.getWt_id()));
        Iterables.forEach(randomWork, (index, item) -> {
            String video = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".mp4";
            String image = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg";
            final Map<String, Object> map1 = new HashMap<>();
            String uuid = UUID.randomUUID().toString();
            final User use1 = socketMapper.use(new User(item.getU_id()));
            map1.put("video", video);
            map1.put("image", image);
            map1.put("id", uuid);
            map1.put("w_id", item.getW_id());
            map1.put("wt_id", item.getWt_id());
            use1.setHead(GlobalConstant.BASE_URL + "/ReallyShare/user/" + use1.getU_id() + "/info/" + use1.getHead());
            map1.put("user", use1);
            map1.put("title", item.getTitle());
            objects.add(map1);
        });
        map.put("list", objects);
        map.put("length", objects.size());
        return R.ok().message("成功").data(map);
    }

    @PostMapping("/relateWork")
    @ApiModelProperty("推荐作品")
    @ResponseBody
    public R relateWork(@RequestBody Work work) {

        final Map<String, Object> map = new HashMap<>();
        final ArrayList<Object> objects = new ArrayList<>();
        final List<Work> randomWork = socketMapper.relateWork(new Work(work.getU_id(), null, null, null));
        Iterables.forEach(randomWork, (index, item) -> {
            String video = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".mp4";
            String image = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg";
            final Map<String, Object> map1 = new HashMap<>();
            String uuid = UUID.randomUUID().toString();
            final User use1 = socketMapper.use(new User(item.getU_id()));
            map1.put("video", video);
            map1.put("image", image);
            map1.put("id", uuid);
            map1.put("w_id", item.getW_id());
            map1.put("wt_id", item.getWt_id());
            use1.setHead(GlobalConstant.BASE_URL + "/ReallyShare/user/" + use1.getU_id() + "/info/" + use1.getHead());
            map1.put("user", use1);
            map1.put("title", item.getTitle());
            objects.add(map1);
        });
        map.put("list", objects);
        map.put("length", objects.size());
        return R.ok().message("成功").data(map);
    }

    @PostMapping("/video/{n}")
    @ApiModelProperty("推荐作品")
    @ResponseBody
    public R video(@PathVariable("n") Integer n) {
        System.out.println(n);
        HashMap<String, Object> map = new HashMap<>();

        List<Work> work = socketMapper.getWork(new Work(n));
        String video = GlobalConstant.BASE_URL + "/ReallyShare/user/" + work.get(0).getU_id() + "/work/" + work.get(0).getW_id() + "/" + work.get(0).getW_id() + ".mp4";
        String image = GlobalConstant.BASE_URL + "/ReallyShare/user/" + work.get(0).getU_id() + "/work/" + work.get(0).getW_id() + "/" + work.get(0).getW_id() + ".jpg";
        final Map<String, Object> map1 = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        final User use1 = socketMapper.use(new User(work.get(0).getU_id()));
        map.put("video", video);
        map.put("image", image);
        map.put("id", uuid);
        map.put("w_id", work.get(0).getW_id());
        use1.setHead(GlobalConstant.BASE_URL + "/ReallyShare/user/" + use1.getU_id() + "/info/" + use1.getHead());
        map.put("user", use1);
        map.put("title", work.get(0).getTitle());
        return R.ok().message("成功").data(map);
    }

    @PostMapping("/likeCount")
    @ApiModelProperty("获赞")
    @ResponseBody
    public R LIkeCount(@RequestBody Likes likes) {
        Map<String, Object> map = new HashMap<>();
        Integer integer = socketMapper.LIkeWorkCount(new Likes(likes.getMe(), likes.getW_id()));
        System.out.println(integer);
        map.put("count", integer);
        return R.ok().data(map);
    }

    @PostMapping("/collectCount")
    @ApiModelProperty("收藏")
    @ResponseBody
    public R CollectCount(@RequestBody Collect collect) {
        System.out.println(new Collect(collect.getMe(), collect.getW_id()).toString());
        Map<String, Object> map = new HashMap<>();
        Integer integer = socketMapper.collectWorkCount(new Collect(collect.getMe(), collect.getW_id()));
        System.out.println(integer);
        map.put("count", integer);
        return R.ok().data(map);
    }

    @PostMapping("/friendCount")
    @ApiModelProperty("关注")
    @ResponseBody
    public R FriendCount(@RequestBody Friend friend) {
        System.out.println(new Friend(friend.getMe(), friend.getWho()).toString());
        Map<String, Object> map = new HashMap<>();
        Integer integer = socketMapper.AttentionCount(new Friend(friend.getMe(), friend.getWho()));
        System.out.println(integer);
        map.put("count", integer);
        return R.ok().data(map);
    }

    @PostMapping("/useLike/{n}")
    @ResponseBody
    public R useLike(@PathVariable("n") Integer n) {
        System.out.println(n);
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map> objects = new ArrayList<>();
        final List<Work> works = socketMapper.useLikeWork(n);
        works.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("w_id", item.getW_id());
            map1.put("image", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg");
            objects.add(map1);
        });
        map.put("list", objects);
        return R.ok().data(map);
    }

    @PostMapping("/MyWork")
    @ResponseBody
    public R IWork(@RequestBody Work work1) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map> objects = new ArrayList<>();
        final List<Work> work = socketMapper.getWork(work1);
        work.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("w_id", item.getW_id());
            map1.put("image", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg");
            objects.add(map1);
        });
        map.put("list", objects);
        return R.ok().data(map);
    }

    @PostMapping("/useCollect")
    @ResponseBody
    public R useCollect(@RequestBody Collect collect) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map> objects = new ArrayList<>();
        final List<Work> work = socketMapper.useCollectWork(collect);
        System.out.println(work.toString());
        work.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("w_id", item.getW_id());
            map1.put("image", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg");
            objects.add(map1);
        });
        map.put("list", objects);
        return R.ok().data(map);
    }


    @PostMapping("/searchWorkData")
    @ApiModelProperty("推荐作品")
    @ResponseBody
    public R searchWorkData(@RequestBody WorkData workData) {
        System.out.println(workData);
        Map<String, Object> map = new HashMap<>();
        final List<WorkData> data = socketMapper.searchWorkData(workData);
        final ArrayList<Map> maps = new ArrayList<>();
        Iterables.forEach(data, (index, item) -> {
            String video = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".mp4";
            String image = GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg";
            final Map<String, Object> map1 = new HashMap<>();
            String uuid = UUID.randomUUID().toString();
            final User use1 = socketMapper.use(new User(item.getU_id()));
            map1.put("video", video);
            map1.put("image", image);
            map1.put("id", uuid);
            map1.put("w_id", item.getW_id());
            use1.setHead(GlobalConstant.BASE_URL + "/ReallyShare/user/" + use1.getU_id() + "/info/" + use1.getHead());
            map1.put("user", use1);
            map1.put("title", item.getTitle());
            maps.add(map1);
        });
        map.put("list", maps);
        return R.ok().message("退出成功").data(map);
    }


    @GetMapping(value = "/download/{u_id}/{w_id}/{account}")
    public String downloadFile(@PathVariable("u_id") String u_id,@PathVariable("account") String account, @PathVariable("w_id") String w_id, HttpServletResponse response) throws Exception {

        String fileName = w_id + "_S.mp4";
        String filePath = "D:\\ReallyShare\\user\\" + u_id + "\\work\\" + w_id + "\\" + fileName;
        JSONObject result = new JSONObject();
        File file = new File(filePath);
        if(!file.exists()){
            String fileName1 = w_id + ".mp4";
            String filePath1 = "D:\\ReallyShare\\user\\" + u_id + "\\work\\" + w_id + "\\" + fileName1;
            VideoWatermarkUtils.addTextByGraphics(filePath1,filePath,"真享:"+account);
            file = new File(filePath);
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        byte[] readBytes = FileCopyUtils.copyToByteArray(file);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(readBytes);
        return result.toString();
    }
}
