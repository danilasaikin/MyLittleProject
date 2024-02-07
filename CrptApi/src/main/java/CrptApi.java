import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CrptApi {

    private final int requestLimit;
    private final TimeUnit timeUnit;
    private final AtomicInteger requestCount;
    private final Object lock = new Object();

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.requestLimit = requestLimit;
        this.timeUnit = timeUnit;
        this.requestCount = new AtomicInteger(0);
    }

    public void createDocument(String documentJson, String signature) {
        try {
            synchronized (lock) {
                while (requestCount.get() >= requestLimit) {
                    lock.wait();
                }
                requestCount.incrementAndGet();
            }

            String url = "https://ismp.crpt.ru/api/v3/lk/documents/create";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(documentJson);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            System.out.println("Код ответа: " + responseCode);
            System.out.println("Ответ: " + response.toString());


            synchronized (lock) {
                requestCount.decrementAndGet();
                lock.notifyAll();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
