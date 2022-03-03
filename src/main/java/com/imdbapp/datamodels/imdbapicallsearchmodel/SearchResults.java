package com.imdbapp.datamodels.imdbapicallsearchmodel;

import java.util.List;


public class SearchResults {
    public String searchType;
    public String expression;
    public List<Result> results;
    public String errorMessage;


    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean resultsIsNull(){
        return this.results.isEmpty();
    }
}
