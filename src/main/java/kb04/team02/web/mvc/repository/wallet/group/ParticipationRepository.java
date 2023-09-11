package kb04.team02.web.mvc.repository.wallet.group;

@Repository
public interface ParticipationRepository extends JpaRepository <Participation, Long> {
    /**
     * 모임지갑 모임원 리스트 요청
     *  ROWNUM 16
     * 
     * SQL
     * INSERT INTO MEMBER
     * (ID, PASSWORD, NAME, ADDRESS, PHONE_NUMBER, EMAIL, INSERT_DATE, PAY_PASSWORD, BANK_ACCOUNT)
     * VALUES
     * (ID, PASSWORD, NAME, ADDRESS, PHONE_NUMBER, EMAIL, SYSDATE, PAY_PASSWORD, BANK_ACCOUNT);
     * 
     * JPA: ParticipationRepository.save(Participation participation);
     *
    List<DuePayment> findAll();
}
