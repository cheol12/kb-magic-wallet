package kb04.team02.web.mvc.exchange.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ai_ExchangeServiceImpl {

    ////////////////////////////////////////////////////
    @Override
    public List<Double> getPredictedExchangeRates() {
        List<Double> predictions = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("D:\\kb_final\\kb-magic-wallet\\exchange_rate\\venv\\Scripts\\python.exe", "D:\\kb_final\\kb-magic-wallet\\exchange_rate\\predictUsd.py");

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine(); // 예측값들을 한 줄로 읽어옴
            System.out.println("line = " + line);
            if(line != null) {
                for(String value : line.split(",")) {
                    predictions.add(Double.parseDouble(value.trim()));
                }
            }


            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(predictions);
        return predictions;
    }
    ////////////////////////////////////////////////////////
}
