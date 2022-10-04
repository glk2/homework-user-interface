package com.company.homeworkloans.screen.loanapproval;

import com.company.homeworkloans.app.RequestLoanService;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("LoanApproval.browse")
@UiDescriptor("loanapproval-browse.xml")
@LookupComponent("loansTable")
public class LoanApprovalBrowse extends StandardLookup<Loan> {

    @Autowired
    private CollectionContainer<Loan> loansADc;
    @Autowired
    private RequestLoanService requestLoanService;
    @Autowired
    private CollectionLoader<Loan> loansADl;

    @Autowired
    private CollectionLoader<Loan> prevLoansDl;
    @Autowired
    private CollectionContainer<Loan> prevLoansDc;

    @Subscribe("approveBtn")
    public void onApproveBtnClick(Button.ClickEvent event) {
        // Loan loan = loansADc.getItem();
        requestLoanService.approveLoan(loansADc.getItem());
        loansADl.load();
    }

    @Subscribe("rejectBtn")
    public void onRejectBtnClick(Button.ClickEvent event) {
        requestLoanService.rejectLoan(loansADc.getItem());
        loansADl.load();
    }

    @Subscribe(id = "loansADc", target = Target.DATA_CONTAINER)
    public void onLoansADcItemChange(InstanceContainer.ItemChangeEvent<Loan> event) {
        Loan loan = event.getItem();
        if (loan!=null) {
            prevLoansDl.setParameter("client", loan.getClient());
            prevLoansDl.setParameter("loan", loan);

            prevLoansDl.load();
        }
    }



}