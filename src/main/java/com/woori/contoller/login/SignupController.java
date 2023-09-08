package com.woori.contoller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woori.dto.APIResponse;
import com.woori.dto.user.RegisterRequestDto;
import com.woori.dto.user.SignupAccountRequestDto;
import com.woori.dto.user.SignupAccountResponseDto;
import com.woori.service.account.AccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	private AccountService accountService;
	
	@ApiOperation(value = "관리자 페이지", notes = "API 설명 부분 : 사용자 정보 조회")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 404, message = "404 에러 발생"),
		@ApiResponse(code = 500, message = "500 에러 발생")
	})
	
	@PostMapping("/account")
	public ResponseEntity<APIResponse<?>> verifyAccount(
			@RequestBody SignupAccountRequestDto requestDto) {
		SignupAccountResponseDto responseDto = accountService.verifyAccount(requestDto);
		
		if (requestDto != null) {
            return ResponseEntity.ok(APIResponse.success(responseDto));
        } else {
        	return ResponseEntity.ok(APIResponse.failbyRequest("비밀번호가 일치하지 않습니다"));
        	//return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다");
        }
	}
	
	@GetMapping("/check")
	public ResponseEntity<APIResponse<?>> userIdCheck(@RequestHeader String userId){
	    boolean available = accountService.userIdCheck(userId);
	    if(available) {
	        return ResponseEntity.ok(APIResponse.success(""));
	    } else {
	    	return ResponseEntity.ok(APIResponse.failbyRequest("사용불가한 아이디입니다."));
	    }
	}
	
	@PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequestDto requestDto) {
        try {

            accountService.SignUp(requestDto);
            return ResponseEntity.ok("Successfully registered!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
		