package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取授权存档的成员列表查询对象
 * https://developer.work.weixin.qq.com/document/path/99641
 * @author Gorgeous
 */
@Data
public class WxCpChatDataAuthUserRequest implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 上一次调用时返回的next_cursor，第一次拉取可以不填
   */
  @SerializedName("cursor")
  private String cursor;

  /**
   * 本次查询返回的最大条数。不超过1000，默认200条
   */
  @SerializedName("limit")
  private Integer limit;

  public static WxCpChatDataAuthUserRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataAuthUserRequest.class);
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
