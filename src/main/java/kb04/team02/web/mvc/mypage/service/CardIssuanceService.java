package kb04.team02.web.mvc.mypage.service;

public interface CardIssuanceService {
    public boolean isConnectToWallet(Long memberId, Long walletId);

    public void createCardConnection(Long memberId, Long groupWalletId);

    public void deleteCardConnection();
}
