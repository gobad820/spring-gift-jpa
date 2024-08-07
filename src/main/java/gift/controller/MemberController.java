package gift.controller;

import gift.exception.ForbiddenException;
import gift.model.Member;
import gift.service.MemberService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Member member) {
        return memberService.registerMember(member)
            .map(token -> { // Optional<String>을 mapping -> isPresent면 map 안 실행 // 매개변수 token으로
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Member registered successfully");
                response.put("token", token); // 생성된 토큰도 같이 보내준다.
                return new ResponseEntity<>(response, HttpStatus.OK);
            })
            .orElseGet(() -> { // isEmpty
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Registration failed");
                response.put("errors", Collections.singletonList("email: 올바른 형식의 이메일 주소여야 합니다"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            });
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Member member){
        return memberService.login(member.getEmail(), member.getPassword())
            .map(token -> { // 토큰이 리턴 -> 정상 로그인 됨
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            })
            .orElseThrow(() -> // 토큰 리턴이 안됨 -> 로그인 안됨
                new ForbiddenException("없는 계정입니다")
            );
    }
}
