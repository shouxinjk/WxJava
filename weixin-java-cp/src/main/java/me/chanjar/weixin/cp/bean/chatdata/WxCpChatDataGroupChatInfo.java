package me.chanjar.weixin.cp.bean.chatdata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取内部群信息返回对象
 * https://developer.work.weixin.qq.com/document/path/99495
 * @author Gorgeous
 */
@Data
public class WxCpChatDataGroupChatInfo implements Serializable {
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
  private String roomCreateTime;

  /**
   * chatid对应的群成员列表
   * "members": [
   *         {
   *             "memberid": "woAAAAAAAA", //chatid群成员的id，open_userid、external_userid、机器人id
   *             "jointime": 1592361605, //chatid群成员的入群时间
   * 			       "type": 1, //chatid群成员的类型 1：员工 2：外部联系人 3：机器人
   *         }
   *     ]
   */
  @SerializedName("members")
  private List<Member> members;

  @Getter
  @Setter
  public static class Member implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("memberid")
    private String memberid;

    @SerializedName("jointime")
    private Long jointime;

    @SerializedName("type")
    private Integer type;

    public static WxCpChatDataGroupChatInfo.Member fromJson(String json) {
      return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataGroupChatInfo.Member.class);
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

  public static WxCpChatDataGroupChatInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpChatDataGroupChatInfo.class);
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
