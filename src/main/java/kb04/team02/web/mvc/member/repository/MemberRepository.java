package kb04.team02.web.mvc.member.repository;

import kb04.team02.web.mvc.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
      ROWNUM 3

      SQL
      INSERT INTO MEMBER
      (ID, PASSWORD, NAME, ADDRESS, PHONE_NUMBER, EMAIL, PAY_PASSWORD)
      VALUES
      (ID, PASSWORD, NAME, ADDRESS, PHONE_NUMBER, EMAIL, PAY_PASSWORD)

      JPA: MemberRepository.save(Member member)
     */

    /*
      ROWNUM 4

      SQL
      SELECT PASSWORD
      FROM MEMBER
      WHERE MEMBER_ID = ID // SEQ 값

      JPA: X
     */
    Optional<Member> findById(String id);
   // List<Member> findByGroupId(Long groupId);

    /**
     *
     * @param id 로그인 아이디
     * @param password 로그인 비밀번호
     * @return
     */
    Optional<Member> findByIdAndPassword(String id, String password);

    // memberId로 Member 객체 불러오기
    List<Member> findByMemberId(Long memberId);

    // 초대할 때 전화번호로 검색
    Optional<Member> findByPhoneNumber(String phone);

}
