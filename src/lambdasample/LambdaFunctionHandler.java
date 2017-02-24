package lambdasample;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class LambdaFunctionHandler implements RequestHandler<S3Event, Object> {

    @Override
    public Object handleRequest(S3Event input, Context context) {
        S3Sample obj = new S3Sample();
        AmazonS3Client s3Client = new AmazonS3Client
        		(new DefaultAWSCredentialsProviderChain());        

        for (S3EventNotificationRecord record : input.getRecords()) {
            String s3Key = record.getS3().getObject().getKey();
            String s3Bucket = record.getS3().getBucket().getName();
            context.getLogger().log("found id: " + s3Bucket+" "+s3Key);
            // retrieve s3 object
            S3Object object = s3Client.getObject(new GetObjectRequest(s3Bucket, s3Key));
            try{
            obj.WritetoS3Bucket("raviksampleoutput", object);
            } catch (IOException e) {}
        }               
        
        

        // TODO: implement your handler
        return null;
    }

}
