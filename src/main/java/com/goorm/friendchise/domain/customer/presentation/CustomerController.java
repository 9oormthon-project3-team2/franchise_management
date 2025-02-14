package com.goorm.friendchise.domain.customer.presentation;


import com.goorm.friendchise.domain.customer.application.CustomerService;
import com.goorm.friendchise.domain.customer.dto.request.CustomerCreateRequest;
import com.goorm.friendchise.domain.customer.dto.request.CustomerLoginRequest;
import com.goorm.friendchise.domain.customer.dto.request.CustomerRecommendStoreRequest;
import com.goorm.friendchise.domain.customer.dto.request.UpdatePasswordRequest;
import com.goorm.friendchise.domain.customer.dto.response.CustomerDetailResponse;
import com.goorm.friendchise.domain.customer.dto.response.CustomerPersistResponse;
import com.goorm.friendchise.global.auth.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController
{
    private final CustomerService customerService;


    @PostMapping("/register")
    public ResponseEntity<CustomerPersistResponse> register(@RequestBody @Valid CustomerCreateRequest request) {
        CustomerPersistResponse response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid CustomerLoginRequest request) {
        TokenResponse response = customerService.login(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/detail")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetail(@RequestParam String username) {
        CustomerDetailResponse response = customerService.detail(username);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequest request)
    {
        customerService.updatePassword(request.newPassword());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/nearest-store")
    public ResponseEntity<String> findNearestStore(@RequestBody @Valid CustomerRecommendStoreRequest request) {
        String storeAddress = customerService.findNearestStoreWithCache(request);
        return ResponseEntity.ok(storeAddress);
    }
}
