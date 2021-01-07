package pl.brzezinski.web_quiz_service.exceptions;

public class WebQuizException  extends RuntimeException{

    public WebQuizException(String exMessage){
        super(exMessage);
    }
}
