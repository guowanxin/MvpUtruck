package tdh.ifm.android.imatch.app.bean;

/**
 * Created by tdh on 2017/5/16.
 */

public class MyMessage {

    private String unReadNumber;
    private String message;
    private String categoryName;
    private String createdTime;
    private String classCd;
    private Integer id;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUnReadNumber() {
        return unReadNumber;
    }

    public void setUnReadNumber(String unReadNumber) {
        this.unReadNumber = unReadNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getClassCd() {
        return classCd;
    }

    public void setClassCd(String classCd) {
        this.classCd = classCd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
