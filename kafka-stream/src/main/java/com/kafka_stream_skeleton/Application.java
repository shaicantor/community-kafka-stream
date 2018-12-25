package com.kafka_stream_skeleton;

//import com.kafka_stream_skeleton.model.LoginCount;
//import com.kafka_stream_skeleton.model.LoginData;
import com.kafka_stream_skeleton.model.LoginCount;
import com.kafka_stream_skeleton.model.LoginData;
import com.kafka_stream_skeleton.model.proxy.MethodKeyHits;
import com.kafka_stream_skeleton.model.proxy.events.SubmitEventsRequest;
import com.kafka_stream_skeleton.serialization.SerdeBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Application {

    private static final String APPLICATION_ID = System.getenv("APPLICATION_ID");
    private static final String INPUT_TOPIC = System.getenv("INPUT_TOPIC");
    private static final String OUTPUT_TOPIC = System.getenv("OUTPUT_TOPIC");
    private static final String BOOTSTRAP_SERVER = System.getenv("KAFKA_URL");


    private static final String BUILD_TOPIC = System.getenv("BUILD_TOPIC");
    private static final String FOOTPRINTS_TOPIC = System.getenv("FOOTPRINTS_TOPIC");
    private static final String TEST_EVENTS_TOPIC = System.getenv("TEST_EVENTS_TOPIC");
    private static final String HITS_TOPICS = System.getenv("HITS_TOPICS");

    public static void main(final String[] args) {


        final KafkaStreams streams = buildStream();
        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static KafkaStreams buildSealightsStream() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);

        Serde<Long> footprintsDataSerde = SerdeBuilder.buildSerde(Long.class);
        Serde<SubmitEventsRequest> testEventsDataSerde = SerdeBuilder.buildSerde(SubmitEventsRequest.class);
        Serde<String> buildMappingDataSerde = SerdeBuilder.buildSerde(String.class);

        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, Long> footprintsStream = builder.stream(FOOTPRINTS_TOPIC, Consumed.with(Serdes.String(), footprintsDataSerde));
        System.out.println("start streaming processing on topic " + FOOTPRINTS_TOPIC);

        final KStream<String, SubmitEventsRequest> testEventsStream = builder.stream(TEST_EVENTS_TOPIC, Consumed.with(Serdes.String(), testEventsDataSerde));
        System.out.println("start streaming processing on topic " + TEST_EVENTS_TOPIC);

        final KStream<String, String> buildMappingTable = builder.stream(BUILD_TOPIC, Consumed.with(Serdes.String(), buildMappingDataSerde));

        KTable<String, Long> methodHits = footprintsStream
                .leftJoin(buildMappingTable, (hits, methodKey) -> new MethodKeyHits(methodKey, hits))
                .map((uniqueId, methodKeyHits) -> new KeyValue<String, Long>(methodKeyHits.getMethodKey(), methodKeyHits.getHits()))
                .reduceByKey((firstHits, secondHits) -> firstHits + secondHits);
    }


    private static KafkaStreams buildStream() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);

        Serde<LoginData> loginDataSerde = SerdeBuilder.buildSerde(LoginData.class);


        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, LoginData> source = builder.stream(INPUT_TOPIC, Consumed.with(Serdes.String(), loginDataSerde));


        System.out.println("start streaming processing on topic " + INPUT_TOPIC);

        KTable<Windowed<String>, Long> counts = source
                .filter((key, value) -> value != null)
                .map((key, value) -> new KeyValue<>(value.getUserName(), value))
                .groupByKey(Serialized.with(Serdes.String(), loginDataSerde))
                .windowedBy(TimeWindows.of(TimeUnit.SECONDS.toMillis(30)))
                .count();


        final Serde<String> stringSerde = Serdes.String();

        Serde<LoginCount> loginCountSerde = SerdeBuilder.buildSerde(LoginCount.class);

        counts
                .toStream()
                .map((windowed,count) ->
                        new KeyValue<>(windowed.key(),new LoginCount(windowed.key(),
                                count,windowed.window().start(),windowed.window().end())))
                .to(OUTPUT_TOPIC, Produced.with(stringSerde, loginCountSerde));


        System.out.println("Streaming processing will produce results to topic " + OUTPUT_TOPIC);

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

}