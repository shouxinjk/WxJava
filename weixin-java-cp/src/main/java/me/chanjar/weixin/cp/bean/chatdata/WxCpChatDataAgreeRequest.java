package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取会话同意情况请求参数
 * https://developer.work.weixin.qq.com/document/path/99530
 * @author Gorgeous
 */
@Data
public class WxCpChatDataAgreeRequest implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  @SerializedName("item")
  private List<UserItem> item;

  /**
   * The type Agree info.
   */
  @Getter
  @Setter
  public static class UserItem implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    /**
     * 内部成员的open_userid
     */
    @SerializedName("open_userid")
    private String openUserid;

    /**
     * 外部成员的external_userid
     */
    @SerializedName("externalUserid")
    private Long external_userid;

    public static WxCpChatDataAgreeRequest.UserItem fromJson(String json) {
      return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAgreeRequest.UserItem.class);
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {
      return WxCpGsonBuilder.create().toJson(this);
    }

  }

  public static WxCpChatDataAgreeRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAgreeRequest.class);
  }

  /**
   * To json string.
   *
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
