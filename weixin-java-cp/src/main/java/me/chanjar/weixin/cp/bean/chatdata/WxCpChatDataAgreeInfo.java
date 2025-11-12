package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取会话同意情况返回对象
 * https://developer.work.weixin.qq.com/document/path/99530
 * @author Gorgeous
 */
@Data
public class WxCpChatDataAgreeInfo implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 错误码
   */
  @SerializedName("errcode")
  private Integer errcode;

  /**
   * 错误码说明
   */
  @SerializedName("errmsg")
  private String errmsg;

  /**
   * chatid对应的群创建者，open_userid
   */
  @SerializedName("creator")
  private String creator;

  /**
   * chatid对应的群创建时间
   */
  @SerializedName("room_create_time")
  private Long roomCreateTime;

  @SerializedName("members")
  private List<AgreeInfo> members;

  /**
   * The type Agree info.
   */
  @Getter
  @Setter
  public static class AgreeInfo implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    /**
     * 同意状态改变的具体时间，utc时间
     */
    @SerializedName("status_change_time")
    private Long statusChangeTime;

    @SerializedName("agree_status")
    private String agreeStatus;

    /**
     * From json agree info.
     *
     * @param json the json
     * @return the agree info
     */
    public static WxCpChatDataAgreeInfo.AgreeInfo fromJson(String json) {
      return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAgreeInfo.AgreeInfo.class);
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

  public static WxCpChatDataAgreeInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAgreeInfo.class);
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
