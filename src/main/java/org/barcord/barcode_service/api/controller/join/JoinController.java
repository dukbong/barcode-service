package org.barcord.barcode_service.api.controller.join;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.ApiResponse;
import org.barcord.barcode_service.api.controller.auth.request.AuthRequest;
import org.barcord.barcode_service.api.service.join.JoinService;
import org.barcord.barcode_service.api.service.join.request.JoinServiceRequest;
import org.barcord.barcode_service.api.service.join.response.JoinResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class JoinController {

    private final JoinService joinServiceImpl;

    @PostMapping
    public ApiResponse<JoinResponse> join(@RequestBody AuthRequest joinRequest) {
        return ApiResponse.ok(joinServiceImpl.join(JoinServiceRequest.createJoinServiceRequest(joinRequest)));
    }

}
