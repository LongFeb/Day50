package cn.nyse.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 默认自动转换 ，也可以通过@Table设置转换对应关系
 * 如果一致或能驼峰命名转换的可以省略
 */
@Table(name = "app_version")
public class AppVersion {
    //标记为主键
    @Id
    @Column(name = "id")  //设置属性和表字段对应关系  如果一致或能驼峰命名转换的可以省略
    @KeySql(useGeneratedKeys = true)//设置返回数据库自增主键值
    private Long id;

    //tkmapper 自动转换属性和表字段之间关系  根据完全匹配或者符合驼峰命名转换匹配
    private Integer platform;
    @Column(name = "version_no")
    private String versionNo;

    private Integer forceUpdate;

    private String downPath;

    private Float size;

    private String appExplain;

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "CTT")
    private Date createDate;

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "CTT")
    private Date updateDate;

    private String delFlag;

    private String createBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath == null ? null : downPath.trim();
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getAppExplain() {
        return appExplain;
    }

    public void setAppExplain(String appExplain) {
        this.appExplain = appExplain == null ? null : appExplain.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", platform=" + platform +
                ", versionNo='" + versionNo + '\'' +
                ", forceUpdate=" + forceUpdate +
                ", downPath='" + downPath + '\'' +
                ", size=" + size +
                ", appExplain='" + appExplain + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", delFlag='" + delFlag + '\'' +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}