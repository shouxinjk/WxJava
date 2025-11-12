package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 授权成员
 * https://developer.work.weixin.qq.com/document/path/99641
 * @author Gorgeous
 */
@Data
public class WxCpChatDataAuthUser implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 授权成员的userid
   */
  @SerializedName("userid")
  private String userid;

  /**
   * 生效的版本列表，1：内部会话；2：内外部会话；3：内外部会话以及语音通话
   */
  @SerializedName("edition_list")
  private Integer[] editionList;

  public static WxCpChatDataAuthUser fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAuthUser.class);
  }

  /**
   * To json string.
   * @return the string
   */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
