package com.samta.model;

public class ReturnNextQuestionDTO {
	
	private String answer;
	
	private ReturnQuestionDTO rq;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ReturnQuestionDTO getRq() {
		return rq;
	}

	public void setRq(ReturnQuestionDTO rq) {
		this.rq = rq;
	}

}
