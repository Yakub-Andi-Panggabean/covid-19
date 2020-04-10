package com.merbuana.covid19.model.response;

import java.util.Date;

public class CovidCaseResponse {

    private int numberOfConfirmedCase;
    private int numberOfRecoveredCase;
    private int numberOfFatalityCase;
    private Date lastUpdate;

    public CovidCaseResponse(Date lastUpdate, int numberOfConfirmedCase, int numberOfRecoveredCase, int numberOfFatalityCase) {
        this.lastUpdate = lastUpdate;
        this.numberOfConfirmedCase = numberOfConfirmedCase;
        this.numberOfRecoveredCase = numberOfRecoveredCase;
        this.numberOfFatalityCase = numberOfFatalityCase;
    }

    public int getTotalCases() {
        return numberOfFatalityCase + numberOfFatalityCase + numberOfConfirmedCase;
    }

    public int getNumberOfTreatmentCase() {
        return numberOfConfirmedCase - (numberOfFatalityCase + numberOfRecoveredCase);
    }

    public int getNumberOfConfirmedCase() {
        return numberOfConfirmedCase;
    }

    public int getNumberOfRecoveredCase() {
        return numberOfRecoveredCase;
    }

    public int getNumberOfFatalityCase() {
        return numberOfFatalityCase;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
