package com.crsri.mes.util.dingtalk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.constant.DingTalkURLConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

/**
 * 〈一句话功能简述〉<br>
 * 〈钉钉工作通知工具类〉
 *
 * @author zcj
 * @date 2018/12/3 9:55
 * @since 1.0.0
 */
public class DingTalkMessageUtil {

    public static ServerResponse sendOAMessage(JSONObject json){
        //发送钉钉工作通知，参考：https://open-doc.dingtalk.com/microapp/serverapi2/pgoxpy
        //创建发送钉钉工作通知的客户端
        DingTalkClient client = new DefaultDingTalkClient(DingTalkURLConstant.MESSAGE_ASYNCSEND);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //设置接收人列表
        String receiver = json.getString("receiver");
        request.setUseridList(receiver);
        request.setAgentId(DingTalkConstant.AGENTID);
        request.setToAllUser(false);
        request.setHttpMethod("POST");

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        //发送OA消息
        //钉钉OA消息的格式 参考：https://open-doc.dingtalk.com/microapp/serverapi2/ye8tup
        msg.setMsgtype("oa");
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        //设置消息头部标题
        //消息的头部标题 (向普通会话发送时有效，向企业会话发送时会被替换为微应用的名字)，所以这里设置背景颜色遮盖
        msg.getOa().getHead().setBgcolor("#ddd");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        msg.getOa().getBody().setForm(list);
        //发送人
        msg.getOa().getBody().setTitle("收到一条CK-MES的工作通知");
        OapiMessageCorpconversationAsyncsendV2Request.Form sender = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        sender.setKey("发送人:");
        sender.setValue(json.getString("sender"));

        msg.getOa().getBody().getForm().add(sender);

        //消息内容：
        OapiMessageCorpconversationAsyncsendV2Request.Form message = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        message.setKey("消息内容：");
        message.setValue(json.getString("message"));
        msg.getOa().getBody().getForm().add(message);
        
        //发送时间
        OapiMessageCorpconversationAsyncsendV2Request.Form time = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        time.setKey("发送时间：");
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setValue(simpleDateFormat.format(new Date()));
        msg.getOa().getBody().getForm().add(time);
        request.setMsg(msg);
        //获取token
        String accessToken = AccessTokeUtil.getToken();
        try {
            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
            return ServerResponse.createBySuccess();
        } catch (ApiException e) {
            return ServerResponse.createByFailMessage("消息发送失败");
        }
    }
}
