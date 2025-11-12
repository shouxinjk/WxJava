package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取内部群信息请求对象
 * 可通过此接口，获取会话内容存档本企业的内部群信息，包括群主id、群创建时间以及所有群成员的id与加入时间。
 * https://developer.work.weixin.qq.com/document/path/99495
 * @author Gorgeous
 */
@Data
public class WxCpChatDataGroupChatRequest implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  /**
   * 待查询的群id
   */
  @SerializedName("chatid")
  private String chatid;

  public static WxCpChatDataGroupChatRequest fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataGroupChatRequest.class);
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
