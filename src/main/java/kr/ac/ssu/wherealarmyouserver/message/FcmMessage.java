package kr.ac.ssu.wherealarmyouserver.message;

import lombok.*;

import javax.annotation.Nonnull;

@Builder
@Value
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @Value
    public static class Message {
        private String topic;
        private AndroidConfig android;

        @RequiredArgsConstructor
        @Getter
        public static class Notification {
            @NonNull
            String title;
            @NonNull
            String body;
            String click_action = "action.GROUP_ALARM_REGISTER";
        }
        @Builder
        @Value
        public static class AndroidConfig {
            private Notification notification;
            private Boolean direct_boot_ok = true;
            private Data data;
        }

        @Builder
        @Value
        public static class Data{
            private String topic;
            private String alarmUid;
        }
    }
}
