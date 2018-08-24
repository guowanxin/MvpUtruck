package tdh.ifm.android.imatch.app.bean;

import java.io.Serializable;

/**
 * Created by tdh on 2017/5/5.
 */

public class RequestFriendList implements Serializable {
  private String searchKey;

  private int page=1;
  private String[] userTypeCds;

  public String[] getUserTypeCds() {
    return userTypeCds;
  }

  public void setUserTypeCds(String[] userTypeCds) {
    this.userTypeCds = userTypeCds;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public String getSearchKey() {
    return searchKey;
  }

  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }
}
