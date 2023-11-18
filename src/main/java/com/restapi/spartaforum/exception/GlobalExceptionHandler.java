package com.restapi.spartaforum.exception;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    // 리소스 조회 불가 예외 처리
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ErrorDetails handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                        WebRequest webRequest) {
//        return new ErrorDetails(new Date(), exception.getMessage(),
//                webRequest.getDescription(false), "Resource Not Found :(");
//    }
//
//    // 잘못된 인자 예외 처리
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ErrorDetails handleIllegalArgumentException(IllegalArgumentException exception,
//                                                       WebRequest webRequest) {
//        return new ErrorDetails(new Date(), exception.getMessage(),
//                webRequest.getDescription(false), "Illegal Argument Has Received :(");
//    }
//
//    // 글로벌 예외 처리
//    @ExceptionHandler(Exception.class)
//    public ErrorDetails handleGlobalException(Exception exception,
//                                              WebRequest webRequest) {
//        return new ErrorDetails(new Date(), exception.getMessage(),
//                webRequest.getDescription(false), "Unknown Exception Has Occured :(");
//    }
//}
