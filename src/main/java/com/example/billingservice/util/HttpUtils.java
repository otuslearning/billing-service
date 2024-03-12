package com.example.billingservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpUtils {
    public static final String HEADER_ACCOUNT_GUID = "X-Account-Guid";
    public static final String REQUEST_ATTRIBUTE_CONTEXT = "requestContext";
}
