package com.kafka_stream_skeleton;

//import com.kafka_stream_skeleton.model.LoginCount;
//import com.kafka_stream_skeleton.model.LoginData;
import com.kafka_stream_skeleton.model.LoginCount;
import com.kafka_stream_skeleton.model.LoginData;
import com.kafka_stream_skeleton.model.proxy.MethodKeyHits;
import com.kafka_stream_skeleton.model.proxy.SubmitBuildMappingRequest;
import com.kafka_stream_skeleton.model.proxy.build.BuildMappingRequest;
import com.kafka_stream_skeleton.model.proxy.build.MethodData;
import com.kafka_stream_skeleton.model.proxy.events.SubmitEventsRequest;
import com.kafka_stream_skeleton.model.proxy.footprints.AppData;
import com.kafka_stream_skeleton.model.proxy.footprints.FileData;
import com.kafka_stream_skeleton.model.proxy.footprints.FootprintsData;
import com.kafka_stream_skeleton.model.proxy.footprints.SubmitFootprintsRequest;
import com.kafka_stream_skeleton.serialization.SerdeBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.*;
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

    private static KafkaStreams buildSealightsStream2() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);

        Serde<SubmitFootprintsRequest> footprintsDataSerde = SerdeBuilder.buildSerde(SubmitFootprintsRequest.class);
        Serde<SubmitEventsRequest> testEventsDataSerde = SerdeBuilder.buildSerde(SubmitEventsRequest.class);
        Serde<BuildMappingRequest> buildMappingDataSerde = SerdeBuilder.buildSerde(BuildMappingRequest.class);

        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, SubmitFootprintsRequest> footprintsStream = builder.stream(FOOTPRINTS_TOPIC, Consumed.with(Serdes.String(), footprintsDataSerde));
        System.out.println("start streaming processing on topic " + FOOTPRINTS_TOPIC);

//        final KStream<String, SubmitEventsRequest> testEventsStream = builder.stream(TEST_EVENTS_TOPIC, Consumed.with(Serdes.String(), testEventsDataSerde));
//        System.out.println("start streaming processing on topic " + TEST_EVENTS_TOPIC);

        final KTable<String, BuildMappingRequest> buildMappingTable = builder.table(BUILD_TOPIC, Consumed.with(Serdes.String(), buildMappingDataSerde));
        KTable<String, Set<String>> flatBuildMappingTable = buildMappingTable.mapValues(buildMapping -> {
            Set<String> methods = new HashSet<>();
            for (com.kafka_stream_skeleton.model.proxy.build.FileData fileData: buildMapping.getFiles()) {
                for (MethodData methodData: fileData.getMethods()) {
                    methods.add(methodData.getUniqueId());
                }
            }
            return methods;
        });

        KStream<String, KeyValue<String, Integer>> flatFootprintsStream = footprintsStream.flatMapValues(footprintsRequest -> {
            Map<String, Integer> methodHits = new HashMap<>();
            for (AppData appData: footprintsRequest.getApps()) {
                for (FileData fileData: appData.getFiles()) {
                    for (FootprintsData footprintsData: fileData.getMethods()) {
                        for (Integer[] hits: footprintsData.getHits()) {
                            Integer count = methodHits.get(footprintsData.getUniqueId());
                            if (count == null) {
                                count = 0;
                            }
                            count += hits[1];
                            methodHits.put(footprintsData.getUniqueId(), count);
                        }
                    }
                }
            }
            ArrayList<KeyValue<String, Integer>> m = new ArrayList<>();
            for (Map.Entry<String, Integer> e: methodHits.entrySet()) {
                m.add(new KeyValue<>(e.getKey(), e.getValue()));
            }
            return m;
        });

        KStream<String, Integer> coveredUniqueIdHits = flatFootprintsStream.leftJoin(flatBuildMappingTable, (methodHit, uniqueIds) -> {
            if (uniqueIds.contains(methodHit.key)) {
                return methodHit.value;
            }
            return 0;
        });

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