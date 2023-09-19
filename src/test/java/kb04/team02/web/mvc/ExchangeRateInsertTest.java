package kb04.team02.web.mvc;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.exchange.repository.ExchangeRateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Commit
public class ExchangeRateInsertTest {

    @Autowired
    private ExchangeRateRepository repository;

    @Test
    @DisplayName("insertRate")
    public void insertRate() throws Exception {
        //given
        //반환용 리스트
        List<List<String>> ret = new ArrayList<List<String>>();
        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("src/main/java/kb04/team02/web/mvc/exchange/service/result.csv"));
            //Charset.forName("UTF-8");
            String line = "";
            int cnt = 0;
            LocalDate nowUSD = LocalDate.now();
            LocalDate nowJPY = LocalDate.now();
            System.out.println("nowUSD = " + nowUSD);
            System.out.println("nowJPY = " + nowJPY);
            while ((line = br.readLine()) != null) {
                //CSV 1행을 저장하는 리스트
                List<String> tmpList = new ArrayList<String>();
                String array[] = line.split(",");
                //배열에서 리스트 반환
                tmpList = Arrays.asList(array);

                if (tmpList.get(1).equals("JPY") || tmpList.get(1).equals("USD")) {
                    cnt++;
                    System.out.println(tmpList);
                    ret.add(tmpList);
                }
            }
            System.out.println("cnt/2 = " + cnt / 2);

            for (int i = cnt - 1; i >= 0; --i) {
                List<String> curr = ret.get(i);
                if (curr.get(1).equals("USD")) {
                    System.out.println(curr.get(2));
                    System.out.println(curr.get(2).replaceAll(",", ""));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
                    System.out.println("curr.get(0) = " + curr.get(0));
                    LocalDateTime time = LocalDateTime.parse(curr.get(0), formatter);
                    System.out.println("time = " + time);
                    Double tradingBaseRate = Double.parseDouble(curr.get(2).replaceAll(",", ""));
                    Double telegraphic_transfer_buying_rate = Double.parseDouble(curr.get(3).replaceAll(",", ""));
                    Double telegraphic_transfer_selling_rate = Double.parseDouble(curr.get(4).replaceAll(",", ""));
                    Double buying_rate = Double.parseDouble(curr.get(5).replaceAll(",", ""));
                    Double selling_rate = Double.parseDouble(curr.get(6).replaceAll(",", ""));
                    repository.save(ExchangeRate.builder()
                            .insertDate(time)
                            .currencyCode(CurrencyCode.USD)
                            .tradingBaseRate(tradingBaseRate)
                            .telegraphicTransferBuyingRate(telegraphic_transfer_buying_rate)
                            .telegraphicTransferSellingRate(telegraphic_transfer_selling_rate)
                            .buyingRate(buying_rate)
                            .sellingRate(selling_rate)
                            .build());
//                    nowUSD = nowUSD.minusDays(1);
                }
                if (curr.get(1).equals("JPY")) {
                    System.out.println(curr.get(2));
                    System.out.println(curr.get(2).replaceAll(",", ""));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
                    System.out.println("curr.get(0) = " + curr.get(0));
                    LocalDateTime time = LocalDateTime.parse(curr.get(0), formatter);
                    Double tradingBaseRate = Double.parseDouble(curr.get(2).replaceAll(",", ""));
                    Double telegraphic_transfer_buying_rate = Double.parseDouble(curr.get(3).replaceAll(",", ""));
                    Double telegraphic_transfer_selling_rate = Double.parseDouble(curr.get(4).replaceAll(",", ""));
                    Double buying_rate = Double.parseDouble(curr.get(5).replaceAll(",", ""));
                    Double selling_rate = Double.parseDouble(curr.get(6).replaceAll(",", ""));
                    repository.save(ExchangeRate.builder()
                            .insertDate(time)
                            .currencyCode(CurrencyCode.JPY)
                            .tradingBaseRate(tradingBaseRate/100)
                            .telegraphicTransferBuyingRate(telegraphic_transfer_buying_rate/100)
                            .telegraphicTransferSellingRate(telegraphic_transfer_selling_rate/100)
                            .buyingRate(buying_rate)
                            .sellingRate(selling_rate)
                            .build());
//                    nowJPY = nowJPY.minusDays(1);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //when

        //then

    }
}
