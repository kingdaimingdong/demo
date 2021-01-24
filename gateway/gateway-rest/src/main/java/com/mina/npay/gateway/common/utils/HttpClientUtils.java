package com.mina.npay.gateway.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * http请求工具类
 * @author daimingdong
 */
@Slf4j
public class HttpClientUtils {

    //  private static final HttpClient client         = new HttpClient(new SimpleHttpConnectionManager());
    private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
    private static HttpClient                         client;
    static {
        //每主机最大连接数

        client = new HttpClient(httpConnectionManager);
        client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(32);
        //总最大连接数
        client.getHttpConnectionManager().getParams().setMaxTotalConnections(256);
        //超时时间 3sec
        client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
        client.getHttpConnectionManager().getParams().setSoTimeout(6000);
        //client.getHttpConnectionManager().getParams().setTcpNoDelay(true);
        //client.getHttpConnectionManager().getParams().setLinger(1000);

    }

    /**
     * post请求
     * @param url
     * @param json
     * @return null if http status code is not 200
     * @throws RuntimeException if exception
     */
    public static JSONObject doPostJson(String url, JSONObject json){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json;charset=UTF-8");
            org.apache.http.Header header = new BasicHeader("businessType", "01");
            post.setEntity(s);
            post.setHeader(header);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());
                log.info("http client revice resp succ,httpStatus={}",res.getStatusLine().getStatusCode());
                response = JSONObject.fromObject(result);
            }else{
                log.warn("http client send not success,res code:{}",res.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            log.warn("http client exception",e);
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * @return
     */
    public static HttpClient getHttpClient() {
        return client;
    }

    /**
     * 用法： HttpRequestProxy hrp = new HttpRequestProxy();
     * hrp.doRequest("http://www.163.com",null,null,"utf-8");
     *
     * @param url 请求的资源ＵＲＬ
     * @param postData POST请求时form表单封装的数据 没有时传null
     * @param header request请求时附带的头信息(header) 没有时传null
     * @param encoding response返回的信息编码格式 没有时传null
     * @return response返回的文本数据
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static String doRequest(String url, Map postData, Map header, String encoding) throws Exception {
        String responseString = null;
        //头部请求信息
        Header[] headers = initHeader(header);
        if (postData != null) {
            //post方式请求
            responseString = executePost(url, postData, encoding, headers);
        } else {
            //get方式 请求
            responseString = executeGet(url, encoding, headers);
        }

        return responseString;
    }

    /**
     * @param url
     * @return
     * @throws Exception
     */
    public static String doRequest(String url) throws Exception {
        String responseString = null;
        //get方式 请求
        responseString = executeGet(url);

        return responseString;
    }

    /**
     * get方式 请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static String executeGet(String url) throws Exception {
        return executeGet(url,"utf-8",null);
    }

    //get方式 请求
    private static String executeGet(String url, String encoding, Header[] headers) throws Exception {
        String responseString = "";
        GetMethod getRequest = new GetMethod(url.trim());
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                getRequest.setRequestHeader(headers[i]);
            }
        }
        try {
            responseString = executeMethod(getRequest, encoding);
        } finally {
            getRequest.releaseConnection();
        }
        return responseString;
    }

    //post方式请求
    @SuppressWarnings("rawtypes")
    private static String executePost(String url, Map postData, String encoding, Header[] headers) throws Exception {
        String responseString = "";
        PostMethod postRequest = new PostMethod(url.trim());
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                postRequest.setRequestHeader(headers[i]);
            }
        }
        Set entrySet = postData.entrySet();
        int dataLength = entrySet.size();
        NameValuePair[] params = new NameValuePair[dataLength];
        int i = 0;
        for (Iterator itor = entrySet.iterator(); itor.hasNext();) {
            Map.Entry entry = (Map.Entry) itor.next();
            params[i++] = new NameValuePair(entry.getKey().toString(), entry.getValue().toString());
        }
        postRequest.setRequestBody(params);
        try {
            responseString = executeMethod(postRequest, encoding);
        } finally {
            postRequest.releaseConnection();
        }
        return responseString;
    }

    //请求头部信息
    @SuppressWarnings("rawtypes")
    private static Header[] initHeader(Map header) {
        Header[] headers = null;
        if (header != null) {
            Set entrySet = header.entrySet();
            int dataLength = entrySet.size();
            headers = new Header[dataLength];
            int i = 0;
            for (Iterator itor = entrySet.iterator(); itor.hasNext();) {
                Map.Entry entry = (Map.Entry) itor.next();
                headers[i++] = new Header(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return headers;
    }

    //调用并获取返回
    private static String executeMethod(HttpMethod request, String encoding) throws Exception {
        String responseContent = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        try {
            Long start = System.currentTimeMillis();
            request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
            getHttpClient().executeMethod(request);
            if (encoding != null) {
                responseStream = request.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuffer tempStr = new StringBuffer();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                responseContent = tempStr.toString();
            } else {
                responseContent = request.getResponseBodyAsString();
            }
            Header locationHeader = request.getResponseHeader("location");
            //返回代码为302,301时，表示页面己经重定向，则重新请求location的url，这在
            //一些登录授权取cookie时很重要
            //TODO 如果需要处理重定向请求，请在下面代码中改造
            if (locationHeader != null) {
                String redirectUrl = locationHeader.getValue();
                log.info("redirectUrl:{}", redirectUrl);
                //                doRequest(redirectUrl, null, null, null);
            }
        } finally {
            IOUtils.closeQuietly(rd);
            IOUtils.closeQuietly(responseStream);
        }
        return responseContent;
    }

    //调用并获取返回
    private static String executeMethod(HttpMethod request) throws Exception {
        return executeMethod(request,"utf-8");
    }

    /**
     * 特殊请求数据,这样的请求往往会出现redirect本身而出现递归死循环重定向 所以单独写成一个请求方法
     * 比如现在请求的url为：http://localhost:8080/demo/index.jsp 返回代码为302
     * 头部信息中location值为:http://localhost:8083/demo/index.jsp
     * 这时httpclient认为进入递归死循环重定向，抛出CircularRedirectException异常
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doSpecialRequest(String url, int count, String encoding) throws Exception {
        String str = null;
        InputStream responseStream = null;
        BufferedReader rd = null;
        GetMethod getRequest = new GetMethod(url);
        //关闭httpclient自动重定向动能
        getRequest.setFollowRedirects(false);
        try {

            client.executeMethod(getRequest);
            Header header = getRequest.getResponseHeader("location");
            if (header != null) {
                //请求重定向后的ＵＲＬ，count同时加1
                this.doSpecialRequest(header.getValue(), count + 1, encoding);
            }
            //这里用count作为标志位，当count为0时才返回请求的URL文本,
            //这样就可以忽略所有的递归重定向时返回文本流操作，提高性能
            if (count == 0) {
                getRequest = new GetMethod(url);
                getRequest.setFollowRedirects(false);
                client.executeMethod(getRequest);
                responseStream = getRequest.getResponseBodyAsStream();
                rd = new BufferedReader(new InputStreamReader(responseStream, encoding));
                String tempLine = rd.readLine();
                StringBuilder tempStr = new StringBuilder();
                String crlf = System.getProperty("line.separator");
                while (tempLine != null) {
                    tempStr.append(tempLine);
                    tempStr.append(crlf);
                    tempLine = rd.readLine();
                }
                str = tempStr.toString();
            }
        } finally {
            getRequest.releaseConnection();
            IOUtils.closeQuietly(rd);
            IOUtils.closeQuietly(responseStream);
        }
        return str;
    }

}
