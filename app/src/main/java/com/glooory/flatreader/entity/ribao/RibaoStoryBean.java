package com.glooory.flatreader.entity.ribao;

import com.chad.library.adapter.base.entity.SectionEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Glooory on 2016/9/28 0028 14:25.
 * 知乎日报的日报Story实体类
 */
@Entity(

        nameInDb = "ribao"
)
public class RibaoStoryBean extends SectionEntity {
    /**
     * images : ["http://pic2.zhimg.com/7b2ea76fbdf40f866b043b989204e111.jpg"]
     * multipic : true
     * type : 0
     * id : 8845106
     * ga_prefix : 093016
     * title : 咨询公司制订了特别厉害的战略，可是怎么执行才好
     */
    @Id(autoincrement = true)
    private Long idPrimary;

    @Transient
    private boolean multipic;

    @Transient
    private int type;

    @Property(nameInDb = "NEWS_ID")
    @Unique
    private String id;

    @Transient
    private String ga_prefix;

    @Transient
    private String title;

    @Transient
    private String date;

    @Transient
    private List<String> images;

    public RibaoStoryBean() {
        super(false, null);
    }

    public RibaoStoryBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public RibaoStoryBean(Object o) {
        super(o);
    }

    @Keep
    public RibaoStoryBean(Long idPrimary, String id) {
        super(false, null);
        this.idPrimary = idPrimary;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getIdPrimary() {
        return this.idPrimary;
    }

    public void setIdPrimary(Long _id) {
        this.idPrimary = _id;
    }
}
