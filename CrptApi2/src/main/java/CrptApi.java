
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CrptApi {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final Semaphore semaphore;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.semaphore = new Semaphore(requestLimit);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::releaseSemaphore, 0, 1, timeUnit);
    }

    public void createDocument(Document document, String signature) {
        try {
            semaphore.acquire();
            String json = objectMapper.writeValueAsString(document);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url("https://ismp.crpt.ru/api/v3/lk/documents/create")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    // Метод для регулярного освобождения разрешений в семафоре
    private void releaseSemaphore() {
        semaphore.release(semaphore.availablePermits());
    }

    static class Document {
        // Определение полей документа в соответствии с требованиями API
        private String participantInn;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
    }
}
