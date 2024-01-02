package me.chanjar.weixin.mp.builder.kefu;
import me.chanjar.weixin.common.api.WxConsts.KefuMsgType;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.builder.kefu.BaseBuilder;

public final class LinkBuilder extends BaseBuilder<LinkBuilder> {

	  private String title;
	  private String desc;
	  private String url;
	  private String thumbMediaId;

	  public LinkBuilder() {
	    this.msgType = "link";
	  }


	  public LinkBuilder title(String title) {
	    this.title = title;
	    return this;
	  }

	  public LinkBuilder desc(String desc) {
	    this.desc = desc;
	    return this;
	  }


	  public LinkBuilder url(String url) {
	    this.url = url;
	    return this;
	  }


	  public LinkBuilder thumbMediaId(String thumbMediaId) {
	    this.thumbMediaId = thumbMediaId;
	    return this;
	  }


	  @Override
	  public WxMpKefuMessage build() {
	    WxMpKefuMessage m = super.build();
	    m.setTitle(this.title);
	    m.setDesc(this.desc);
	    m.setUrl(this.url);
	    m.setThumbMediaId(this.thumbMediaId);
	    return m;
	  }


	}
