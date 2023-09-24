import numpy as np
import pandas as pd
from tensorflow.keras.models import load_model
from sklearn.preprocessing import MinMaxScaler

SEQ_LEN = 9
PREDICT_DAYS = 7

# 모델 불러오기
model = load_model('exchange_rate\\jpy_model.h5')

# 데이터 불러오기 및 전처리
data = pd.read_excel('exchange_rate\\jpy.xlsx')
scaler = MinMaxScaler()
scaled_data = scaler.fit_transform(data['jpy'].values.reshape(-1, 1))

# 최근 SEQ_LEN일의 데이터 불러오기
last_seq = scaled_data[-SEQ_LEN:].reshape(1, SEQ_LEN, 1)

predictions = []

for _ in range(PREDICT_DAYS):
    # 예측 수행
    predicted_value = model.predict(last_seq, verbose=0)

    # 예측 값을 predictions 리스트에 추가
    predictions.append(predicted_value[0][0])

    # 새로운 예측 값을 last_seq에 추가하고 가장 오래된 값을 제거
    new_seq = np.append(last_seq, predicted_value)
    last_seq = new_seq[1:].reshape(1, SEQ_LEN, 1)

# 원래 스케일로 복원
predictions_rescaled = scaler.inverse_transform(np.array(predictions).reshape(-1, 1))

# for i, value in enumerate(predictions_rescaled):
#     print(f"Day {i+1}: {value[0]}")

# 파싱하기 편하게 변경
# ex) "1125.56,1127.34,1126.78,1125.90,1126.20,1126.50,1127.12"
print(','.join([str(value[0]) for value in predictions_rescaled]))
