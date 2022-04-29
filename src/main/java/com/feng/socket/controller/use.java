package com.feng.socket.controller;

import com.feng.socket.mapper.SocketMapper;
import com.feng.socket.pojo.Admi;
import com.feng.socket.pojo.User;
import com.feng.socket.pojo.WorkData;
import com.feng.socket.pojo.login.Sockett;
import com.feng.socket.utils.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@CrossOrigin
@RestController
@RequestMapping("/api/use")
public class use {

    @Autowired
    private SocketMapper socketMapper;

    private final Logger log= LoggerFactory.getLogger( getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin
    @PostMapping("/login")
    @ApiModelProperty("用户登录")
    public R login(@RequestBody User use) throws IOException {
        HashMap<String, Object> ap = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        User use1 = socketMapper.use(new User( use.getAccount() , use.getPassword() ));
        System.out.println(use1);
        if (use1 != null){
            map.put("u_id",use1.getU_id().toString());
            map.put("account" , use1.getAccount());
            map.put("email" , use1.getEmail());
            map.put("name" , use1.getName());
            map.put("head",GlobalConstant.BASE_URL +"/ReallyShare/user/"+use1.getU_id()+"/info/"+use1.getHead());
            String token = JWTutils.getToken(map);
            redisTemplate.opsForValue().set(use1.getU_id().toString(),token,30,TimeUnit.DAYS);
            map.put("about",use1.getAbout());
            ap.put("token",token);
            ap.put("user",map);
//            FileUtils.CreateFile(use1.getU_id().toString());
            return R.ok().message("登陆成功").data(ap);
        }
        return R.error().message("查无此人");
    }

    @PostMapping("/use")
    public R getUse(@RequestBody User use){
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(use);
        User use1 = socketMapper.use(new User(use.getU_id()));
        use1.setPassword("");
        use1.setHead(GlobalConstant.BASE_URL +"/ReallyShare/user/"+use1.getU_id()+"/info/"+use1.getHead());
        map.put("user",use1);
        if (use1 != null){
            return R.ok().message("登陆成功").data(map);
        }
        return R.error().message("查无此人");
    }

    @ApiModelProperty("二维码")
    @GetMapping(value = "/QRCode",produces = "image/png")
    @ResponseBody
    public byte[] QRCode(@RequestParam("name") String name) throws Exception {
        final BufferedImage encode = QRCodeUtil.encode(name, "D:\\ReallyShare\\project\\logo.png", true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(encode, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @PostMapping("/loginOut")
    @ApiModelProperty("退出登录")
    @ResponseBody
    public R loginOut() {
        return R.ok().message("退出成功");
    }

    @ApiModelProperty("用户添加")
    @PostMapping("/user")
    @ResponseBody
    public R getUser(@RequestBody User use) throws IOException {
        Integer users = 0;
        if (use.getName() != null){
            users = socketMapper.users(new User(use.getName(),use.getAccount(),use.getPassword(), use.getEmail()));
        }
        User use1 = socketMapper.use(new User( use.getAccount() , use.getPassword() ));
        FileUtils.CreateFile(use1.getU_id().toString());
        return R.ok().message( users > 0?"添加成功":"添加失败");
    }

    private String text;

    @ApiModelProperty("验证码")
    @GetMapping(value = "/VCode",produces = "image/png")
    @ResponseBody
    public byte[] VCode() throws IOException {

        VCodeUtils vCodeUtils = new VCodeUtils();
        BufferedImage image = vCodeUtils.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        text = vCodeUtils.getText();
        log.info("====当前验证码是{}",text);
        return byteArrayOutputStream.toByteArray();
    }

    @ApiModelProperty("验证码结果")
    @GetMapping("Code")
    @ResponseBody
    public R Code(){
        return R.ok().message("添加成功").data("code",text);
    }

    @ApiModelProperty("验证码结果")
    @GetMapping("/Code1")
    @ResponseBody
    public R Past (){
        return R.ok().message("添加成功").data("code",text);
    }

    @CrossOrigin
    @PostMapping("/AdminLogin")
    @ApiModelProperty("用户登录")
    public R AdminLogin(@RequestBody Admi admi) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if(admi != null){
            Admi admin = socketMapper.AdminLogin(new Admi(admi.getA_account(), admi.getA_password()));
            if(admin == null){
                return R.ok().message("登陆失败");
            }
            Map<String, String> map1 = new HashMap<>();
            map1.put("id",admin.getA_id().toString());
            map1.put("name",admin.getA_name());
            map1.put("account",admin.getA_account());
            String token = JWTutils.getToken(map1);
            map.put("token",token);
            admin.setA_password("");
            map.put("admin",admin);
            return R.ok().data(map).message("登陆成功");
        }
        return R.ok().message("登陆失败");
    }

    @CrossOrigin
    @PostMapping("/fuzzySearch")
    @ApiModelProperty("搜索")
    public R fuzzySearch(@RequestBody WorkData workData) {
        Map<String, Object> map = new HashMap<>();
        final List<WorkData> data = socketMapper.workData(workData);
        System.out.println(data.toString());
        final ArrayList<Map> maps = new ArrayList<>();
        data.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("u_id", item.getU_id());
            map1.put("name", item.getName());
            map1.put("account", item.getAccount());
            map1.put("time", item.getTime());
            map1.put("title", item.getTitle());
            map1.put("type", item.getType());
            map1.put("video", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".mp4");
            map1.put("image", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/work/" + item.getW_id() + "/" + item.getW_id() + ".jpg");
            maps.add(map1);
        });
        map.put("list", maps);
        return R.ok().message("成功").data(map);
    }
}
