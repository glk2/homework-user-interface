package com.company.homeworkloans.screen.client;

import com.company.homeworkloans.screen.requestloan.RequestLoan;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.builder.ScreenBuilder;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {

    @Autowired
    private GroupTable<Client> clientsTable;
    @Autowired
    private Screens screens;

    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private CollectionContainer<Client> clientsDc;


    @Subscribe("requestLoanBtn")
    public void onRequestLoanBtnClick(Button.ClickEvent event) {
        //Client client = ((Client) event.getEntity())
        //screens.create(RequestLoan.class).show();
        //Client client = clientsDc.getItem();
        //screenBuilders.screen(this).withScreenClass(RequestLoan.class).
        //      withOpenMode(OpenMode.DIALOG).show();

        Client client = null;
        if (clientsTable.getSelected().size() != 0) {
            client = clientsTable.getSelected().iterator().next();
        }
        Screen screen = screenBuilders.screen(this).withScreenClass(RequestLoan.class).withOptions(new ClientBrowseOptions(client)).
                withOpenMode(OpenMode.DIALOG).show();
    }

    public class ClientBrowseOptions implements ScreenOptions {

        private Client client;

        public ClientBrowseOptions(Client client) {
            this.client = client;
        }

        public Client getClient() {
            return client;
        }
    }

}

