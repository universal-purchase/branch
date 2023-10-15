package com.chocolate.blogsch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(            400,    "E001001",      "error.invalid.input.value"),
    UNAUTHORIZED(                   401,    "E001002",      "error.unauthorized"),
    FORBIDDEN(                      403,    "E001003",      "error.forbidden"),
    NOT_FOUND(                      404,    "E001004",      "error.not.found"),
    METHOD_NOT_ALLOWED(             405,    "E001005",      "error.method.not.allowed"),
    INTERNAL_SERVER_ERROR(          500,    "E001006",     "error.internal.server.error"),
    SERVICE_UNAVAILABLE(            503,    "E001007",     "error.service.unavailable"),

    // 비즈니스 에러
    SERVICE_CUSTOM_MESSAGE(         400,    "B001001",     ""),
    RESOURCE_NOT_FOUND(             404,    "B001002",     "error.resource.not.found"),
    RESOURCE_ALREADY_EXISTS(        409,    "B001003",     "error.resource.already.exists"),
    DUPLICATE_KEY(                  409,    "B001004",     "error.duplicate.key"),
    API_SERVICE_UNAVAILABLE(        503,    "B001005",     "error.api.service.unavailable"),
    CANNOT_DELETE_ASSOCIATED_RESOURCE_EXISTS(     409,    "B001006",     "error.cannot.delete.associated.resource.exists"),
    URI_PATH_AND_BODY_NOT_MATCH(    400,    "B001007",     "error.uri.path.and.body.not.match"),
    FILE_UPLOAD_FAILED(              500,    "B001008",      "error.file.upload.failed"),

    // 브로드캐스트 B101001 ~ B101999
    DEPLOYED_BDI_NOT_EXISTS(409, "B101001", "error.deployed.bdi.not.exists"),
    ENCODED_BDI_NOT_EXISTS(409, "B101002", "error.encoded.bdi.not.exists"),


    // 패키지 B102001 ~ B102999
    ALREADY_CREATED_OTHER_PACKAGE(  409,    "B102001",      "error.already.created.other.package"),
    CANNOT_DELETE_PACKAGE_EXISTS(   409,    "B102002",      "error.cannot.delete.package.exists"),

    // 서비스 채널 B103001 ~ B103999
    SERVICE_CHANNEL_NOT_EXISTS(      404,    "B103001",     "error.service.channel.not.exists"),

    // 가입자 B104001 ~ B104999
    CANNOT_DELETE_STB_USER_EXISTS(409, "B104001", "error.cannot.delete.stb.user.exists"),

    // 채널개편 작업 B105001 ~ B105999
    DUPLICATED_CHANNEL_LOGICAL_NUMBER(409, "B105001", "error.duplicated.channel.logical.number"),

    ;

    private int status;
    private String code;
    private String message;

}
