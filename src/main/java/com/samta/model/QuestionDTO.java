package com.samta.model;

public class QuestionDTO {
    private String question;
    private String answer;
    private CategoryDTO category;

    public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public class CategoryDTO {
        private String title;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@Override
		public String toString() {
			return "CategoryDTO [title=" + title + "]";
		}
        
    }

	@Override
	public String toString() {
		return "QuestionDTO [question=" + question + ", answer=" + answer + ", category=" + category + "]";
	}
	
	
	
}
