package com.valunskii.labs.hibernate.basics.dirtychecking;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valunskii.labs.hibernate.basics.BaseTest;
import com.valunskii.labs.hibernate.basics.entity.Account;
import com.valunskii.labs.hibernate.basics.entity.Client;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.event.internal.DefaultFlushEntityEventListener;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;

@DatabaseSetup("/data.xml")
public class DirtyCheckingTest extends BaseTest {

    @Test
    @Commit // flush выполнится на коммите
    public void dirtyChecking() {
        Account account = session.get(Account.class, 10);
        account.setAmount(100);

        Client client = session.get(Client.class, 100);
    }

    @Test
    @Commit // flush выполнится на коммите
    public void dirtyCheckingDisableForQuery() {
        // add @Immutable to Client
        Client client = session.get(Client.class, 100);
        // for queries it is also possible
        //session.createQuery("select c from Client c").setReadOnly(true).list();
    }

//    @Test
//    @Commit // flush выполнится на коммите
//    public void allOperationsAreDirectInStatelessSession() {
//        SessionFactory sf = session.getSessionFactory();
//        StatelessSession statelessSession = sf.openStatelessSession();
//        List<Client> clients = statelessSession
//                .createQuery("select c from Client c").list();
//        clients.forEach(client -> {
//            System.out.println(client.getName());
//            client.setName("TEST");
//            statelessSession.update(client);// direct database low-level operation
//        });
//    }
}
