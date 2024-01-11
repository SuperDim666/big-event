package org.zixiangxu.utils;/*
 * @author Zixiang Xu
 * @date 2024/1/4 0:36
 */


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.InputStream;

public class AliOSSUtil {

    // Endpoint of Aliyun OSS Bucket
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    // Get access credentials from environment variables
    //
    // Before running this code example,
    // make sure that the environment variables OSS_ACCESS_KEY_ID and OSS_ACCESS_KEY_SECRET are set
    // EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    @SuppressWarnings("SpellCheckingInspection")
    private static final String ACCESS_KEY_ID = "LTAI5tQmMHzXbcjayU6MS4LF";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String ACCESS_KEY_SECRET = "wJCjvpSiPCwxIzM0WZOnn9TjHlyJ6x";

    // Bucket name
    private static final String BUCKETNAME = "zixiangxu-big-event";

    public static String uploadFile(String objectName, InputStream in) {

        // Create an instance of OSSClient using ACCESS_KEY_ID and ACCESS_KEY_SECRET
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String url = "";
        try {

            // Creates a PutObjectRequest object
            // new ByteArrayInputStream(content.getBytes())
            PutObjectRequest
                putObjectRequest = new PutObjectRequest(BUCKETNAME, objectName, in);

            // If you need to set the storage type and access rights when uploading,
            // please refer to the following sample code:
            //
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // Upload content
            ossClient.putObject(putObjectRequest);
            // URL Component: https://{bucket_name}.{regional_node}/objectName
            url = "https://"+
                BUCKETNAME+"."+
                ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+ "/"+
                objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}