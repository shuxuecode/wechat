package org.weixin.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.core.constant.Globals;

/**
 * 微信多媒体上传
 * @author zsx
 *
 */
public class MediaUtil {
	public static void main(String[] args) {
		String json = "{\"type\":\"image\",\"media_id\":\"df5Qx_GqYkyd1PfTuRNm1rMjVvKG6uSF489ykSRH1KBH9jDPsueoq2__QaDDPTR7\",\"created_at\":1409189870}";
		JSONObject obj = JSON.parseObject(json);
		String str = obj.getString("media_id");
		System.out.println(str == null);
		//getMedia_idByURL("http://bcs.duapp.com/wechatimages%2Fnews%2F%E6%9E%97%E8%82%AF%E5%85%AC%E5%9B%AD.jpg", "-mQWkZDSQU-zm6bpgJu909796QpZl1LG51rlQbpe6rOJa_JCH3kmukOTG6pztA3AN29VWgqhYqNmWEil7ibFAA");
		
		
	}
	
	/**
	 * 根据图片地址获取media_id
	 * @param url
	 * @param access_token
	 * @return
	 */
	public static String getMedia_idByURL(String url, String access_token){
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(url));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			String result = fileUpload(access_token, "image", UUID.randomUUID().toString() + ".jpg", is);
			JSONObject obj = JSON.parseObject(result);
			//System.out.println({"errcode":40001,"errmsg":"invalid credential"});
			if (obj.get("media_id") != null) {
				System.out.println(obj.get("media_id").toString());
				return obj.get("media_id").toString();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
     * 上传文件 
     * @param access_token
     * @param type  图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
     * @param filepath 磁盘完整路径从盘符开始 DEMO C:\\wx\\upload\\esa\\123456.jpg
     * @return 成功/失败 返回json串
     * {
		    "type": "image",
		    "media_id": "df5Qx_GqYkyd1PfTuRNm1rMjVvKG6uSF489ykSRH1KBH9jDPsueoq2__QaDDPTR7",
		    "created_at": 1409189870
		}
    */
    public static String fileUpload(String access_token,String type, String filename, InputStream is ) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716";
        String urlStr = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+access_token+"&type="+type;
        try {
        	URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            String  contentType = "image/jpeg";

            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\""
                    + "media" + "\"; filename=\"" + filename
                    + "\"\r\n");
            strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());

            //文件体
            DataInputStream in = new DataInputStream(is);
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
            	out.write(bufferOut, 0, bytes);
            }
            in.close();

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBufReturn = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
            	strBufReturn.append(line).append("\n");
            }
            res = strBufReturn.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
//          	log.error("发送POST请求出错。" + urlStr);
          	e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        System.out.println("上传文件返回JSON结果["+res+"]");
        return res;
    }
}
