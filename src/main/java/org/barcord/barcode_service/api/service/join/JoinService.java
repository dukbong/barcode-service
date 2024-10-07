package org.barcord.barcode_service.api.service.join;

import org.barcord.barcode_service.api.service.join.request.JoinServiceRequest;
import org.barcord.barcode_service.api.service.join.response.JoinResponse;

public interface JoinService {

    // 회원가입
    JoinResponse join(JoinServiceRequest joinServiceRequest);

}
