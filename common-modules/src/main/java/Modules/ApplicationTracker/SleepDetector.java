package Modules.ApplicationTracker;

import Modules.notifications.TelegramNotificationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SleepDetector {

    private static final long presentTiming = 12 * 60 * 1000;
    private static boolean isNotified=false;

    @Autowired
    private TelegramNotificationsService telegramNotificationsService;

    private void scheduleSleepingMessage(Class<?> className){

        long presentSystemTime = System.currentTimeMillis();
        long lastRequestTim = ActivityTrackerFilter.lastRequestTime;

        if((presentSystemTime - lastRequestTim) > presentSystemTime){
            if(!isNotified){
                log.info("Server sleeping message enabled..., server name: {}", className.getSimpleName());
                String message = """
                        😴 *FunMarket %s Server Going to Sleep *
                        
                        🕘 *Time:* %s
                        ⚠️ *Reason:* No activity detected from <b>(12 mins)</b> in Render
                        
                        💤 Auto wake on next request
                        """.formatted(className.getSimpleName(), TelegramNotificationsService.telegramTimeNow());
                telegramNotificationsService.telegramMessage(message);
                isNotified=true;
            }else{
                isNotified=false;
            }
        }
    }

    public void sendSleepingNotification(Class<?> className){
        scheduleSleepingMessage(className);
    }
}
