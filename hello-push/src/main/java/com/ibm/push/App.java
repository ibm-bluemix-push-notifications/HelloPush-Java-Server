package com.ibm.push;

import com.ibm.mobilefirstplatform.serversdk.java.push.APNs;
import com.ibm.mobilefirstplatform.serversdk.java.push.ChromeAppExt;
import com.ibm.mobilefirstplatform.serversdk.java.push.ChromeWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM;
import com.ibm.mobilefirstplatform.serversdk.java.push.FirefoxWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.Notification;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushNotifications;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushNotificationsResponseListener;
import com.ibm.mobilefirstplatform.serversdk.java.push.SafariWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.Target;
import com.ibm.mobilefirstplatform.serversdk.java.push.APNs.Builder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMStyle;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.FCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMLights.Builder.FCMLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMStyle.Builder.FCMStyleTypes;
import com.ibm.mobilefirstplatform.serversdk.java.push.Target.Builder.Platform;

import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        PushNotifications.initWithApiKey("YOUR_APPLICATION_ID", "YOUR_APIKEY",
                PushNotifications.US_SOUTH_REGION);

        // Init with appsecret
        // PushNotifications.init("YOUR_APPLICATION_ID", "YOUR_SECRET",
        // PushNotifications.US_SOUTH_REGION);

        sendPush();

    }

    public static void sendPush() {

        Message message = new Message.Builder().alert("20% Off Offer for you").url("www.ibm.com").build();

        // Apns

        // For APNs settings.
        APNs apns = new APNs.Builder().badge(1).interactiveCategory("Accept").iosActionKey("PUSH_OFFER")
                .payload(new JSONObject().put("alert", "20% Off for you")).sound("sound.wav")
                .type(APNSNotificationType.DEFAULT).titleLocKey("OFFER").locKey("REPLYTO")
                .launchImage("launchImage1.png").titleLocArgs(new String[] { "Jenna", "Frank" })
                .locArgs(new String[] { "Jenna", "Frank" }).title("IBM").subtitle("IBM Cloud")
                .attachmentUrl(
                        "https://developer.blackberry.com/native/files/documentation/images/text_messages_icon.png")
                .build();

        // FCM

        FCMStyle fcmstyle = new FCMStyle.Builder().type(FCMStyleTypes.BIGTEXT_NOTIFICATION)
                .text("BIG TEXT NOTIFICATION").title("Big Text Notification")
                .url("https://developer.blackberry.com/native/files/documentation/images/text_messages_icon.png")
                .lines(new String[] { "IBM", "IBM Cloud", "Big Text Notification" }).build();

        FCMLights fcmlights = new FCMLights.Builder().ledArgb(FCMLED.GREEN).ledOffMs(1).ledOnMs(1).build();

        FCM fcm = new FCM.Builder().androidTitle("androidTitle").collapseKey("ping").interactiveCategory("Accept")
                .delayWhileIdle(true).payload(new JSONObject().put("alert", "20% Off for you"))
                .priority(FCMPriority.MIN).sound("mysound.wav").timeToLive(3)
                .icon("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
                .visibility(Visibility.PUBLIC).sync(true).style(fcmstyle).lights(fcmlights).build();

        // Chrome settings
        ChromeWeb chromeWeb = new ChromeWeb.Builder().title("IBM Push Offer").iconUrl(
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
                .timeToLive(3).payload(new JSONObject().put("alert", "20% Off for you")).build();

        // ChromeAppExtension settings.
        // You need to provide a proper icon urlfor chromAppExtension notification to
        // work properly.
        ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey("ping").delayWhileIdle(true)
                .title("IBM Push Offer")
                .iconUrl(
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
                .timeToLive(3).payload(new JSONObject().put("alert", "20% Off for you")).build();

        // Firefox Settings
        FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().title("IBM Offer").iconUrl(
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
                .timeToLive(3).payload(new JSONObject().put("alert", "20% Off for you")).build();

        // Safari Settings. For safari all the three settings are mandatory to set.
        SafariWeb safariWeb = new SafariWeb.Builder().title("IBM Offer").urlArgs(new String[] { "www.IBM.com" })
                .action("View").build();

        Target target = new Target.Builder().platforms(new Platform[] { Platform.APPLE, Platform.GOOGLE,
                Platform.APPEXTCHROME, Platform.WEBCHROME, Platform.WEBSAFARI, Platform.WEBFIREFOX }).build();

        Settings settings = new Settings.Builder().apns(apns).fcm(fcm).chromeWeb(chromeWeb).chromeAppExt(chromeAppExt)
                .firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();

        Notification notification = new Notification.Builder().message(message).settings(settings).target(target)
                .build();

        PushNotifications.send(notification, new PushNotificationsResponseListener() {

            public void onSuccess(int statusCode, String responseBody) {
                System.out.println("Successfully sent push notification! Status code: " + statusCode
                        + " Response body: " + responseBody);
            }

            public void onFailure(Integer statusCode, String responseBody, Throwable t) {
                System.out.println("Failed sent push notification. Status code: " + statusCode + " Response body: "
                        + responseBody);
                if (t != null) {
                    t.printStackTrace();
                }
            }
        });

    }
}
