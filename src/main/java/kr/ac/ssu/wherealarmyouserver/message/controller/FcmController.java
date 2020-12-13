package kr.ac.ssu.wherealarmyouserver.message.controller;

import kr.ac.ssu.wherealarmyouserver.message.FcmMessage;
import kr.ac.ssu.wherealarmyouserver.message.FirebaseCloudMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class FcmController {
    @Autowired
    private FirebaseCloudMessageService fcmService;


    @PostMapping("/message/{groupUid}")
    public Integer receiveFromClient(@PathVariable("groupUid")String groupUid, @RequestBody String body) {
        try {
            fcmService.sendMessageTo(groupUid,body);
            log.debug("group : "+groupUid+", body : "+body);
            return 200;
        } catch (IOException e) {
            e.printStackTrace();
            return 500;
        }
    }

}
