package org.barcord.barcode_service.api.controller.login;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.ApiResponse;
import org.barcord.barcode_service.api.controller.auth.request.AuthRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {

    @PostMapping
    public ApiResponse<String> login(@RequestBody AuthRequest request) {
        return null;
    }

}
