package com.feng.socket.mapper;

import com.feng.socket.pojo.*;
import com.feng.socket.pojo.main.LikeWork;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SocketMapper {
    User getUse(User user);

    Integer users(User user);//添加用户

    User use(User user);//查询用户

    List<WorkType> getType();//查询作品类型

    WorkType getWorkType(WorkType workType);

    List<Friend> getFriendList(Friend friend);//查询好友

    Integer SetWork(Work work);//添加作品

    List<Work> getWork(Work work);//查询作品

    List<Work> RandomWork(Work work);//随即十条作品

    Integer likeWork(Likes likes); //点赞

    Integer unLikeWork(Likes likes);//取消点赞

    List<Likes> isLikeWork(Likes likes);//是否点赞

    Integer collectWork(Collect collect);//收藏

    Integer unCollectWork(Collect collect);//取消收藏

    List<Collect> isCollectWork(Collect collect);//是否收藏

    Work OneWork(Work work);  //作品详情

    Integer addAttention(Friend friend);//添加好友

    Integer unAttention(Friend friend);//取消关注

    List<Friend> isAttention(Friend friend);//是否是好友

    Integer LIkeWorkCount(Likes likes);//总点赞数

    Integer collectWorkCount(Collect collect);//总收藏数

    Integer AttentionCount(Friend friend);//总关注数

    List<Work> useLikeWork(Integer integer);//喜欢作品

    List<Work> useCollectWork(Collect collect);//收藏作品

    Admi AdminLogin(Admi admi);//管理员登录

    List<Pic> getPic(Work work);//图表信息

    Integer getTimeWorkCount(Work work);//今天作品数量

    List<User> userData(User user);//查询用户

    List<WorkData> workData(WorkData workData);//作品查询

    List<WorkTypeData> workTypeData(WorkTypeData workTypeData); //作品类新房

    Integer workTypeUpdate(WorkTypeData workTypeData);//作品菜单修改

    Integer workTypeInsert(WorkTypeData workTypeData);//作品菜单修改

    List<Admi> getAdmi(Admi admi);//所有管理员

    Integer updateAdmi(Admi admi);//更新管理员

    Integer insertAdmi(Admi admi); //添加管理员

    List<Grade> getGrade(Grade grade);//所有权限

    List<Work> relateWork(Work work);//关注作品

    List<WorkData> searchWorkData(WorkData workData);//
}
