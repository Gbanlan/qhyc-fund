

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.qhyc.fund.uitl.HttpUtils;
import com.qhyc.fund.uitl.JsonResult;
import com.qhyc.fund.uitl.JsonUtils;



public class TestHttpClient {

	public static void main(String[] args) {
//		test();
		String key="f68ef96ef4654abea17b93b048316c62";
		String  realname="高茂2";
		String idcard ="360781199203060054";
        StringBuffer url=new StringBuffer();
        url.append("http://api.avatardata.cn/IdCardCertificate/Verify");
        url.append("?key=");
        url.append(key);
        url.append("&realname=");
        url.append(realname);
        url.append("&idcard=");
        url.append(idcard);
        
		try {
			String res = HttpUtils.getUrl(url.toString());
			System.out.println(res);
			JSONObject jsonObject = JSONObject.parseObject(res);
			if("Succes".equals(jsonObject.get("reason"))){
				String code=(String)jsonObject.getJSONObject("result").getString("code");
				if("1000".equals(code)){
					System.out.println(code);
				}else{
					String message=(String)jsonObject.getJSONObject("result").getString("message");
					System.out.println(message);
				}
			}else{
				System.out.println(jsonObject.getString("reason"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}


}
