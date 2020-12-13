package kr.ac.ssu.wherealarmyouserver.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FirebaseCloudMessageService {
    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/where-alarm-you/messages:send";
    private final ObjectMapper objectMapper;

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void sendMessageTo(String topic, String body) throws IOException {
        String message = makeMessage(topic, body);
        log.info("message : " + message);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String topic, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(FcmMessage.Message.builder()
//                        .notification(new Notification(title, body))
//                        .build()
//                )

                .validate_only(false)
                .message(FcmMessage.Message.builder()
                        .android(FcmMessage.Message.AndroidConfig.builder()
                                .notification(new FcmMessage.Message.Notification("그룹 알람 등록! " + topic, "알람 UID : " + body))
                                .data(FcmMessage.Message.Data.builder()
                                        .alarmUid(body)
                                        .topic(topic).build())
                                .build())
                        .topic(topic).build())
                .build();


        return objectMapper.writeValueAsString(fcmMessage);
    }


}
