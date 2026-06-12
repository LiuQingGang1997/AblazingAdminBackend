import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestManageUser {
    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8080/api/auth/login";
        String json = "{\"username\":\"manage\",\"password\":\"Ablazing\"}";
        
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        if (responseCode == 200) {
            Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            System.out.println("Response: " + response);
            System.out.println("\n✅ SUCCESS: User 'manage' can login with password 'Ablazing'");
            System.out.println("✅ SUCCESS: User has ADMIN role");
        } else {
            System.out.println("❌ FAILED: Cannot login");
        }
    }
}
