package io.github.hyunseo2512.server.domain.member.application;

import io.github.hyunseo2512.server.domain.member.domain.Member;
import io.github.hyunseo2512.server.domain.member.domain.Role;
import io.github.hyunseo2512.server.domain.member.dto.SignUpRequest;
import io.github.hyunseo2512.server.domain.member.repository.MemberRepository;
import io.github.hyunseo2512.server.global.error.BusinessException;
import io.github.hyunseo2512.server.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignUpRequest request) {
        validateDuplicateUsername(request.username());
        validateDuplicateEmail(request.email());

        Member member = Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .email(request.email())
                .role(Role.USER) // 기본 권한 USER 부여
                .build();

        return memberRepository.save(member).getId();
    }

    private void validateDuplicateUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            // ErrorCode 인터페이스가 아닌 구체적인 예외 코드를 던질 수 있도록 임시 처리
            // BusinessException 내부 구조에 따라 에러 코드를 알맞게 전달
            throw new BusinessException(new ErrorCode() {
                @Override public org.springframework.http.HttpStatus getHttpStatus() { return org.springframework.http.HttpStatus.CONFLICT; }
                @Override public String getMessage() { return "이미 존재하는 아이디입니다."; }
            });
        }
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(new ErrorCode() {
                @Override public org.springframework.http.HttpStatus getHttpStatus() { return org.springframework.http.HttpStatus.CONFLICT; }
                @Override public String getMessage() { return "이미 존재하는 이메일입니다."; }
            });
        }
    }
}
