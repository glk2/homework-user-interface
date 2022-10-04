package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.app.RequestLoanService;
import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.screen.client.ClientBrowse;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
public class RequestLoan extends Screen {

    @Autowired
    private EntityComboBox<Client> clientSelector;
    @Autowired
    private RequestLoanService requestLoanService;

    @Autowired
    //<BigDecimal>  не работает
    private TextField<Double> amountSelector;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Autowired
    private CollectionContainer<Client> clientsDc;

//    @Subscribe
//    public void onInit(InitEvent event) {
//        Client client = clientsDc.getItem();
//        if (client != null) {
//            clientSelector.setValue(client);
//        }
//    }

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof ClientBrowse.ClientBrowseOptions) {
            Client client = ((ClientBrowse.ClientBrowseOptions) options).getClient();
            if (client!=null) {
                clientSelector.setValue(client);
            }
        }
    }



    @Subscribe("addRequestBtn")
    public void onAddRequestBtnClick(Button.ClickEvent event) {
        // Client client = clientsDc.getItem();
        if (clientSelector.getValue() == null
                || amountSelector.getValue() == null) {
            notifications.create()
                    .withCaption(messageBundle.getMessage("validation.fieldsNotFilled.message"))
                    .withType(Notifications.NotificationType.WARNING)
                    .show();

            clientSelector.focus();
            return;
        }

//       Не понял как завести встроенный валидатор
        if (amountSelector.getValue() < 0) {
            notifications.create()
                    .withCaption(messageBundle.getMessage("negative.amount"))
                    .withType(Notifications.NotificationType.WARNING)
                    .show();

            clientSelector.focus();
            return;
        }

        requestLoanService.createRequestLoan(clientSelector.getValue(),
                amountSelector.getValue());

        //tasksDl.load();

        clientSelector.setValue(null);
        amountSelector.setValue(null);

        close(StandardOutcome.CLOSE);

    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }


}