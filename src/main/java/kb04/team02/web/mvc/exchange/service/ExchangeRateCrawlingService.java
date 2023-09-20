package kb04.team02.web.mvc.exchange.service;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.exchange.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ExchangeRateCrawlingService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateCrawlingService.class);

    private final ExchangeRateRepository exchangeRateRepository;

    @Scheduled(cron = "0 0 9 ? * MON-FRI") // 주중 오전 9시에 실행
    public void scrapeExchangeRates() {
        // 오늘 날짜 구하기
        LocalDateTime nowDateTime = LocalDateTime.now();

        try {
            // 웹 페이지 파싱
            Document document = Jsoup.connect("https://obank.kbstar.com/quics?page=C101423").get();
            Elements elements = document.select("#inqueryTable > table:nth-child(2) > tbody > tr");

            // CSV 파일에 데이터 작성
            for (Element element : elements) {
                Elements rows = element.select("td");

                String currencyCode = rows.get(0).text().replace(",", "");
                String tradingBaseRate = rows.get(2).text().replace(",", "");
                String telegraphicTransferBuyingRate = rows.get(3).text().replace(",", "");
                String telegraphicTransferSellingRate = rows.get(4).text().replace(",", "");
                String buyingRate = rows.get(5).text().replace(",", "");
                String sellingRate = rows.get(6).text().replace(",", "");

                String csvRow = String.format("%s,%s,%s,%s,%s,%s,%s",
                        nowDateTime,
                        currencyCode,
                        tradingBaseRate,
                        telegraphicTransferBuyingRate,
                        telegraphicTransferSellingRate,
                        buyingRate,
                        sellingRate);


                if (buyingRate.equalsIgnoreCase("-")) {
                    continue;
                }

                if (currencyCode.equalsIgnoreCase("USD")) {
                    exchangeRateRepository.save(
                            ExchangeRate.builder()
                                    .insertDate(nowDateTime)
                                    .currencyCode(CurrencyCode.USD)
                                    .tradingBaseRate(Double.valueOf(tradingBaseRate))
                                    .telegraphicTransferBuyingRate(Double.valueOf(telegraphicTransferBuyingRate))
                                    .telegraphicTransferSellingRate(Double.valueOf(telegraphicTransferSellingRate))
                                    .buyingRate(Double.valueOf(buyingRate))
                                    .sellingRate(Double.valueOf(sellingRate))
                                    .build()
                    );
                    logger.info(csvRow);
                } else if (currencyCode.equalsIgnoreCase("JPY")) {
                    exchangeRateRepository.save(
                            ExchangeRate.builder()
                                    .insertDate(nowDateTime)
                                    .currencyCode(CurrencyCode.JPY)
                                    .tradingBaseRate(Double.valueOf(tradingBaseRate))
                                    .telegraphicTransferBuyingRate(Double.valueOf(telegraphicTransferBuyingRate))
                                    .telegraphicTransferSellingRate(Double.valueOf(telegraphicTransferSellingRate))
                                    .buyingRate(Double.valueOf(buyingRate))
                                    .sellingRate(Double.valueOf(sellingRate))
                                    .build()
                    );
                    logger.info(csvRow);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
