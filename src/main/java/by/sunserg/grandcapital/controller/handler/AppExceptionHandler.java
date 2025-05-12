package by.sunserg.grandcapital.controller.handler;

import by.sunserg.grandcapital.controller.exception.AppErrorResponse;
import by.sunserg.grandcapital.controller.exception.DataNotFoundException;
import by.sunserg.grandcapital.controller.exception.MoneyTransferException;
import by.sunserg.grandcapital.controller.exception.NotUniqueDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppErrorResponse handlerDataNotFoundException(DataNotFoundException exception) {
        return new AppErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());
    }

    @ExceptionHandler({NotUniqueDataException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppErrorResponse handlerNotUniqueDataException(NotUniqueDataException exception) {
        return new AppErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage());
    }

    @ExceptionHandler({MoneyTransferException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorResponse handlerMoneyTransferException(MoneyTransferException exception) {
        return new AppErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage());
    }
}
