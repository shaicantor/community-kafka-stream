package com.kafka_stream_skeleton.model.proxy.events;

/**
 * Created by Nadav on 7/5/2016.
 */
public interface IQueueSenderEvents {
    boolean onBeforeSend();
    void onFailedToSendDataDuringShutdown();
}
