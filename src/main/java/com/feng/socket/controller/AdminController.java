package com.feng.socket.controller;


import com.feng.socket.mapper.SocketMapper;
import com.feng.socket.pojo.*;
import com.feng.socket.utils.GlobalConstant;
import com.feng.socket.utils.JWTutils;
import com.feng.socket.utils.R;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Handler;

@CrossOrigin
@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SocketMapper socketMapper;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/pic")
    @ApiModelProperty("作品信息")
    @ResponseBody
    public R pic() {
        final HashMap<String, Object> map = new HashMap<>();
        List<Pic> pic = socketMapper.getPic(new Work());
        map.put("list", pic);
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/map")
    @ApiModelProperty("作品信息")
    @ResponseBody
    public R map() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> typeData = new ArrayList<>();
        ArrayList<Map> maps = new ArrayList<>();
        List<WorkType> type = socketMapper.getType();
        type.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            ArrayList<Integer> xData = new ArrayList<>();
            Date date = new Date();
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd ");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, +1);
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                List<Pic> pic = socketMapper.getPic(new Work(item.getWt_id(), dateF.format(date).toString()));
                if (pic.size() != 0) {
                    xData.add(pic.get(0).getValue());
                } else {
                    xData.add(0);
                }
                if (dateFm.format(date).equals("星期一")) break;
            }
            Collections.reverse(xData);
            map1.put("name", item.getWt_type());
            map1.put("type", "line");
            map1.put("stack", "Total");
            map1.put("data", xData);
            maps.add(map1);
            typeData.add(item.getWt_type());
        });
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            strings.add(dateFm.format(date));
            if (dateFm.format(date).equals("星期一")) break;
        }
        Date date1 = new Date();
        ArrayList<Integer> xData = new ArrayList<>();
        SimpleDateFormat dateFm1 = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFmn1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.add(Calendar.DAY_OF_MONTH, +1);
        for (int i = 0; i < 6; i++) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            date1 = calendar1.getTime();
            Integer timeWorkCount = socketMapper.getTimeWorkCount(new Work(null, dateFmn1.format(date1).toString().trim()));
            xData.add(timeWorkCount);
            if (dateFm1.format(date1).equals("星期一")) break;
        }
        Map<String, Object> map1 = new HashMap<>();
        Collections.reverse(xData);
        map1.put("name", "所有类型");
        map1.put("type", "line");
        map1.put("stack", "Total");
        map1.put("data", xData);
        maps.add(map1);
        typeData.add("所有类型");
        Collections.reverse(strings);
        map.put("timeData", strings);
        map.put("series", maps);
        map.put("typeData", typeData);
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/userManage")
    @ApiModelProperty("用户管理")
    @ResponseBody
    public R userManage(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        List<User> users = socketMapper.userData(user);
        System.out.println(users.toString());
        final ArrayList<Map> maps = new ArrayList<>();
        users.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("u_id", item.getU_id());
            map1.put("name", item.getName());
            map1.put("sex", item.getSex());
            map1.put("account", item.getAccount());
            map1.put("email", item.getEmail());
            map1.put("address", item.getAddress());
            map1.put("head", GlobalConstant.BASE_URL + "/ReallyShare/user/" + item.getU_id() + "/info/" + item.getHead());
            map1.put("adout", item.getAbout());
            maps.add(map1);
        });
        map.put("list", maps);
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/workManage")
    @ApiModelProperty("作品管理")
    @ResponseBody
    public R WorkManage(@RequestBody WorkData workData) {
        Map<String, Object> map = new HashMap<>();
        final List<WorkData> data = socketMapper.workData(workData);
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
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/workTypeManage")
    @ApiModelProperty("作品菜单管理")
    @ResponseBody
    public R WorkTypeManage(@RequestBody WorkTypeData workTypeData) {
        Map<String, Object> map = new HashMap<>();
        List<WorkTypeData> workTypeData1 = socketMapper.workTypeData(workTypeData);
        final ArrayList<Map> maps = new ArrayList<>();
        workTypeData1.forEach((item) -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("wt_id", item.getWt_id());
            map1.put("icon", item.getIcon());
            map1.put("type", item.getType());
            map1.put("state", item.getState() == null ? 0 : item.getState());
            map1.put("router", item.getRouter());
            maps.add(map1);
        });
        map.put("list", maps);
        return R.ok().message("退出成功").data(map);
    }

    @PostMapping("/workTypeUpdate")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R workTypeUpdate(@RequestBody WorkTypeData workTypeData) {
        final Integer update = socketMapper.workTypeUpdate(workTypeData);
        if (update == 1) return R.ok().message("修改成功");
        return R.ok().message("修改失败");
    }

    @PostMapping("/workTypeInsert")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R workTypeInsert(@RequestBody WorkTypeData workTypeData) {
        System.out.println(workTypeData.toString());
        final Integer update = socketMapper.workTypeInsert(workTypeData);
        if (update == 1) return R.ok().message("添加成功");
        return R.ok().message("修改失败");
    }

    @PostMapping("/getAdmi")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R getAdmi(@RequestBody Admi admi) {
        System.out.println(admi.toString());
        final HashMap<String, Object> map = new HashMap<>();
        final List<Admi> admi1 = socketMapper.getAdmi(admi);
        map.put("list",admi1);
        return R.ok().message("查询成功").data(map);
    }

    @PostMapping("/updataAdmi")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R updataAdmi(@RequestBody Admi admi) {
        System.out.println(admi);
        final Integer integer = socketMapper.updateAdmi(admi);
        if (integer == 1) return R.ok().message("添加成功");
        return R.ok().message("修改失败");
    }

    @PostMapping("/getGrade")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R getGrade(@RequestBody Grade grade) {
        System.out.println(grade.toString());
        final HashMap<String, Object> map = new HashMap<>();
        final ArrayList<Map> maps = new ArrayList<>();
        final List<Grade> admi1 = socketMapper.getGrade(grade);
        admi1.forEach((item)->{
            Map<String, Object> map1 = new HashMap<>();
            map1.put("value",item.getG_id());
            map1.put("title",item.getG_class());
            maps.add(map1);
        });
        map.put("list",maps);
        return R.ok().message("查询成功").data(map);
    }

    @PostMapping("/insertAdmi")
    @ApiModelProperty("作品菜单修改")
    @ResponseBody
    public R insertAdmi(@RequestBody Admi admi) {
        System.out.println(admi);
        final Integer integer = socketMapper.insertAdmi(admi);
        if (integer == 1) return R.ok().message("添加成功");
        return R.ok().message("添加失败");
    }

}
