package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
//	Member findByMemberId(String memberId);
//	Boolean existByUsername(String memberId);
//	Member findByMemberIdAndPassword(String memberId, String password);
//	
//	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String id);
//	Optional<Member> findByEmail(String email);
//	Optional<Member> findByRefreshToken(String refreshToken);

}
