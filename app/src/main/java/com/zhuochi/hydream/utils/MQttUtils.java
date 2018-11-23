package com.zhuochi.hydream.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zhuochi.hydream.entity.MyMqttMessage;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQttUtils {

    public static MQttUtils getmQttUtils() {
        return mQttUtils;
    }

    private static MQttUtils mQttUtils;
    private final MqttAndroidClient mqttAndroidClient;
    private Context context;
    private String mqtUrl;

    public static MQttUtils initMQttUtils(Context context, String mqtUrl) {
        if (null == mQttUtils) {
            mQttUtils = new MQttUtils(context, mqtUrl);

        }
        return mQttUtils;
    }

    private MQttUtils(Context context, String mqtUrl) {
        this.context = context;
        this.mqtUrl = mqtUrl;

        if (null == mqtUrl) {
            mqtUrl = "tcp://mqtt.94lihai.com:1883";
        }
        mqttAndroidClient = new MqttAndroidClient(context, mqtUrl, System.currentTimeMillis() + "");
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        try {
            //addToHistory("Connecting to " + serverUri);
            mqttAndroidClient.connect(mqttConnectOptions, context, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    Log.d("lsy", "connect:onSuccess()");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("lsy", exception.getMessage());
                }
            });
            mqttAndroidClient.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    Log.i("lsy", "MqttCallbackExtended:connectComplete()");
                }

                @Override
                public void connectionLost(Throwable cause) {
                    Log.i("lsy", "MqttCallbackExtended:connectionLost()" + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.i("lsy", "MqttCallbackExtended:messageArrived()");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.i("lsy", "MqttCallbackExtended:deliveryComplete()");
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @pram string topic :订阅主题不能为空
     * @pram
     */

    public void subscribeToTopic(String topic, final Handler handler) {
        if (!mqttAndroidClient.isConnected()) {
            Log.i("lsy", "MQtt通信未链接");
            return;
        }
        if (null == topic) {
            Log.i("lsy", "订阅主题不能未空");
            return;
        }
        try {
            mqttAndroidClient.subscribe(topic, 1, context, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("lsy", "+subscribe()+onSuccess()");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("lsy", "+subscribe()+onFailure()");

                }

            }, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    Message msg = new Message();
                    MyMqttMessage myMqttMessage = new MyMqttMessage();
                    myMqttMessage.setMqttMessage(message);
                    myMqttMessage.setTopic(topic);
                    msg.what = Integer.MAX_VALUE;
                    msg.obj = myMqttMessage;
                    handler.sendMessage(msg);

                }
            });
        } catch (Exception e) {
            Log.i("lsy", "MqttException:" + e.getMessage());
        }


    }

    public void destroyResource() {
        if (null == mqttAndroidClient) {
            return;
        }
        mqttAndroidClient.unregisterResources();
    }

}
