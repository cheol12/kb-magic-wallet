package kb04.team02.web.mvc.exchange.service;

import kb04.team02.web.mvc.exchange.dto.GeoCodeDto;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.NoSuchElementException;

@PropertySource("classpath:naver.properties")
@RequiredArgsConstructor
@Service
public class StaticMapService {

    private final BankRepository bankRepository;

    @Value("${naver.client-id}")
    private String clientId;
    @Value("${naver.client-secret}")
    private String clientSecret;

    public GeoCodeDto staticMapImage(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(
                () -> new NoSuchElementException("은행 지점 찾기 실패")
        );

        String street = bank.getAddress().getStreet();
        GeoCodeDto pos = position(street);

        return pos;
    }

    private GeoCodeDto position(String street) {
        // HttpClient 생성
        HttpClient httpClient = HttpClients.createDefault();

        // 요청 URL 설정
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";

        try {
            String query = URLEncoder.encode(street, "UTF-8"); // 검색할 주소

            // GET 요청 생성
            HttpGet httpGet = new HttpGet(url + "?query=" + query);

            // HTTP 헤더 설정
            httpGet.addHeader("X-NCP-APIGW-API-KEY-ID", clientId);
            httpGet.addHeader("X-NCP-APIGW-API-KEY", clientSecret);

            // 요청 보내고 응답 받기
            HttpResponse response = httpClient.execute(httpGet);

            // 응답 내용 출력
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("응답 내용: " + responseBody);

            // 응답 코드 확인
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("응답 코드: " + statusCode);

            return longAndLat(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private GeoCodeDto longAndLat(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray addressesArray = jsonObject.getJSONArray("addresses");

            if (addressesArray.length() > 0) {
                JSONObject addressObject = addressesArray.getJSONObject(0); // 첫 번째 주소 정보 가져오기
                String x = addressObject.getString("x");
                String y = addressObject.getString("y");

                System.out.println("x: " + x);
                System.out.println("y: " + y);

                // 여기에서 x와 y 값을 사용하면 됩니다.
//                return URLEncoder.encode(x + " " + y, "UTF-8");
                return GeoCodeDto.builder().lng(x).lat(y).build();
            } else {
                System.out.println("주소 정보가 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    private String staticMapImage(String pos) {
//        // HttpClient 생성
//        HttpClient httpClient = HttpClients.createDefault();
//
//        // 요청 URL 설정
//        String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster";
//
//        try {
//            // GET 요청 생성
//            HttpGet httpGet = new HttpGet(URLEncoder.encode(url + "?w=300&h=300&markers=type:d|size:mid|pos:" + pos + "|color:0xFFBA01&scale=2", "UTF-8"));
//
//            // HTTP 헤더 설정
//            httpGet.addHeader("X-NCP-APIGW-API-KEY-ID", clientId);
//            httpGet.addHeader("X-NCP-APIGW-API-KEY", clientSecret);
//
//            // 요청 보내고 응답 받기
//            HttpResponse response = httpClient.execute(httpGet);
//
//            // 응답 내용 가져오기
//            String responseBody = EntityUtils.toString(response.getEntity());
//
//            // 이미지 데이터 읽기
//            InputStream inputStream = response.getEntity().getContent();
//
//            // 이미지를 저장할 로컬 경로 지정
//            String localImagePath = "src/main/resources/static/image.jpg";
//
//            // 이미지 파일로 저장
//            try (OutputStream outputStream = new FileOutputStream(localImagePath)) {
//                int bytesRead;
//                byte[] buffer = new byte[1024];
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            }
//
//            System.out.println("이미지 다운로드 완료: " + localImagePath);
//
//            return localImagePath;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
