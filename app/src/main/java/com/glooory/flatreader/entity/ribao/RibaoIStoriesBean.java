package com.glooory.flatreader.entity.ribao;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/28 0028 14:18.
 * 知乎日报数据请求时的实体类
 */

public class RibaoIStoriesBean {


    /**
     * date : 20160928
     * stories : [{"images":["http://pic4.zhimg.com/a010159aa6b0e40eaa4c842f42cd8ad7.jpg"],"type":0,"id":8836532,"ga_prefix":"092814","title":"怎样的城市更容易聚集科技人才？"},{"images":["http://pic4.zhimg.com/68daa094d72628707239e28ac7b7c4ef.jpg"],"type":0,"id":8832761,"ga_prefix":"092813","title":"因为爽就上瘾，那人类和小白鼠还有什么区别"},{"images":["http://pic3.zhimg.com/85f8a1705f85460a17a0b7b9708e008a.jpg"],"type":0,"id":8804675,"ga_prefix":"092812","title":"大误 · 早晚都是一家人，别打人家嘛"},{"images":["http://pic1.zhimg.com/0404769bcef8f23a30c3a6a7e8e37fd8.jpg"],"type":0,"id":8835361,"ga_prefix":"092811","title":"从手机标配摄像头开始，人们的生活就这样改变了"},{"images":["http://pic3.zhimg.com/b6a83de5ca4116e30947f0ab9c6b4a9a.jpg"],"type":0,"id":8836416,"ga_prefix":"092810","title":"先纠正一个错误观念，很多很多很多 RNA 不是单链的"},{"title":"简单几步，教你把照片修出那种很「欧美」的感觉","ga_prefix":"092809","images":["http://pic4.zhimg.com/bc46ea6ea037d8c94e87104a10c0e647.jpg"],"multipic":true,"type":0,"id":8834715},{"images":["http://pic2.zhimg.com/c3dcd5161ed29bc420712dbba071f05d.jpg"],"type":0,"id":8836412,"ga_prefix":"092808","title":"给你卡拿去刷，因为银行用信用卡赚钱的方式太多了"},{"images":["http://pic2.zhimg.com/cae5063994bacb84d648b72c922c4249.jpg"],"type":0,"id":8835931,"ga_prefix":"092807","title":"粒子那么小，粒子对撞机是怎么让它们撞在一起的？"},{"images":["http://pic4.zhimg.com/51ec0bedef3e90ac166cf3c1e6b78273.jpg"],"type":0,"id":8836377,"ga_prefix":"092807","title":"想写出水平高一些的论文，这些技巧用得上"},{"images":["http://pic4.zhimg.com/0b51469065e582bf2ee7099a7b29bf8f.jpg"],"type":0,"id":8830933,"ga_prefix":"092807","title":"同为简历造假，「结没结婚」和「六级过没过」还不一样"},{"images":["http://pic1.zhimg.com/63b0c6944bec8be819717ef77f003c70.jpg"],"type":0,"id":8835993,"ga_prefix":"092807","title":"读读日报 24 小时热门 TOP 5 · 竞选辩论，我却在看主持人"},{"images":["http://pic2.zhimg.com/7e3cf3b650dcd97ac2a3b5fc223b54c1.jpg"],"type":0,"id":8809348,"ga_prefix":"092806","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/d96abdb5bb61859332aaf3e593f65eff.jpg","type":0,"id":8836412,"ga_prefix":"092808","title":"给你卡拿去刷，因为银行用信用卡赚钱的方式太多了"},{"image":"http://pic3.zhimg.com/c05a92bde96d9e9f6714479c96101a76.jpg","type":0,"id":8835361,"ga_prefix":"092811","title":"从手机标配摄像头开始，人们的生活就这样改变了"},{"image":"http://pic2.zhimg.com/68533b24ab5c2d85fbf2d2f6c50916e9.jpg","type":0,"id":8830933,"ga_prefix":"092807","title":"同为简历造假，「结没结婚」和「六级过没过」还不一样"},{"image":"http://pic4.zhimg.com/f71ea56c05159634cf425d11f92c4fd7.jpg","type":0,"id":8835993,"ga_prefix":"092807","title":"读读日报 24 小时热门 TOP 5 · 竞选辩论，我却在看主持人"},{"image":"http://pic4.zhimg.com/08ac583f5a1bb79276eeee70a7c1ee7b.jpg","type":0,"id":8834779,"ga_prefix":"092719","title":"发现中国 · 只去故宫颐和园，简直浪费了北京的美"}]
     */

    private String date;
    private List<RibaoStoryBean> stories;
    private List<TopStoriesBean> top_stories;

    public static RibaoIStoriesBean objectFromData(String str) {

        return new Gson().fromJson(str, RibaoIStoriesBean.class);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RibaoStoryBean> getStories() {
        return stories;
    }

    public void setStories(List<RibaoStoryBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }


    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public static TopStoriesBean objectFromData(String str) {

            return new Gson().fromJson(str, TopStoriesBean.class);
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
