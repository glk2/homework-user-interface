package com.company.homeworkloans.app;


import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class RequestLoanService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    public void createRequestLoan(Client client, Double amount) {

        //Все значения будут инициализированы через dataManager
        Loan loan = dataManager.create(Loan.class);
        loan.setAmount(new BigDecimal(amount));
        loan.setClient(client);
        loan.setRequestDate(LocalDate.now());
        loan.setStatus(LoanStatus.REQUESTED);

        dataManager.save(loan);
    }

    public void approveLoan(Loan loan) {

        //Все значения будут инициализированы через dataManager
        if (loan != null) {
            loan.setStatus(LoanStatus.APPROVED);
        }

        dataManager.save(loan);
    }

    public void rejectLoan(Loan loan) {

        //Все значения будут инициализированы через dataManager
        if (loan != null) {
            loan.setStatus(LoanStatus.REJECTED);
        }

        dataManager.save(loan);
    }


}