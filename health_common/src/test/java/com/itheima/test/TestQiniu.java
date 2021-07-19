package com.itheima.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;




public class TestQiniu {

    //上传本地文件
    @Test
    public void uploadFile(){

        //构造一个带指定Zone对象的配置类
        Configuration cfg=new Configuration(Zone.zone0());

        //...其他参数参考类注释
        UploadManager uploadManager=new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        String accessKey="BMFks1Z4RNtqaZ3uloYZduYnSswkNKxJe1uPGMWZ";
        String secretKey="w2TL_vld2xM-koWEIoN1gSbscFz8-xgvexSBYL-2";
        String bucket="caiciji";
        //如果是windows情况下，格式是D:\\qiniu\\test.png,可支持中文;
        String localFilePage="C:\\Users\\工作台\\Pictures\\Feedback\\{98897B22-86FA-44B1-BA04-F8922B52B044}\\1.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key=null;
        Auth auth=Auth.create(accessKey,secretKey);
        String upToken=auth.uploadToken(bucket);

        try {
            Response response=uploadManager.put(localFilePage,key,upToken);

            //解析上传成功的结果
            DefaultPutRet putRet=new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());

            try {
                System.out.println(r.bodyString());
            } catch (QiniuException e) {
                //ignore
            }
        }
    }

    //删除空间中的文件
    @Test
    public void deleteFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg=new Configuration(Zone.zone0());
        //其他参数参考类注释
        String accessKey="BMFks1Z4RNtqaZ3uloYZduYnSswkNKxJe1uPGMWZ";
        String secretKey="w2TL_vld2xM-koWEIoN1gSbscFz8-xgvexSBYL-2";
        String bucket="caiciji";
        String key="FnDqKNIXkzAYZDohmLp0avq_9gYg";
        Auth auth=Auth.create(accessKey,secretKey);
        BucketManager bucketManager=new BucketManager(auth,cfg);

        try {
            bucketManager.delete(bucket,key);
        } catch (QiniuException ex) {

            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
