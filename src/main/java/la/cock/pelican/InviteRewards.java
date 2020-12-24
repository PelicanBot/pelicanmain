package la.cock.pelican;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class InviteRewards {
    public InviteRewards() {
        startWebService();
    }

    public static void main(String[] args) {
        staticFiles.location("/public");
        new InviteRewards();
    }

    public void startWebService() {
        OkHttpClient okHttpClient = new OkHttpClient();
        get("/verify/*", (req, res) -> {
            System.out.println(Arrays.toString(req.splat()));
            if (req.splat().length != 1) return readFile("/invalidLink.html");
            //TODO: check if valid link in memory
            if (req.splat().length != 1) return readFile("/invalidLink.html");
            return readFile("/verify.html");
        });
        post("/verify/*", (req, res) -> {
            System.out.println(Arrays.toString(req.splat()));
            Request request = new Request.Builder()
                    .url("https://www.google.com/recaptcha/api/siteverify?secret=6Le4uMAZAAAAAOxeBeh47dcHzpIHC-Rf8p4Fl3nN&response=" + req.queryParams("g-recaptcha-response"))
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .get()
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            if (!jsonObject.getBoolean("success")) return readFile("/invalidCaptcha.html");
            //TODO: check if valid link in memory
            if (!jsonObject.getBoolean("success")) return readFile("/invalidCaptcha.html");
            //TODO: check if VPN, reward the referee
            return readFile("/valid.html");
        });
    }

    public String readFile(String file) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(file)) {
            if (inputStream == null) return null;
            try (InputStreamReader isr = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
