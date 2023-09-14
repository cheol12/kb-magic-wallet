package kb04.team02.web.mvc.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;

@Repository
public interface PersonalWalletRepository extends JpaRepository<PersonalWallet, Long> {
	
	/**
	 * row 5-1
	 * 
	 * SELECT PERSONAL_WALLET_ID, BALANCE FROM PERSOVAL_WALLET WHERE MEMBER_ID = [SESSION.MEMBER_ID]
	 * 
	 */
	PersonalWallet findByMember(Member member);

	/**
	 * row 7-1
	 *
	 * UPDATE PERSONAL_WALLET SET BALANCE = BALANCE + [충전 금액] WHERE MEMBER_ID = [SESSION.MEMBER_ID]
	 *
	 * 	JPA : PersonalWalletRepository.save(PersonalWalletDTO personalWalletDTO);
	 */

	/**
	 * row 9
	 *
	 * update personal_wallet set balance  = [balance - 환불 금액] where personal_wallet_id = [session.personal_wallet_id]
	 *
	 * JPA : PersonalWalletRepository.save(PersonalWalletDTO personalWalletDTO);
	 */

}
