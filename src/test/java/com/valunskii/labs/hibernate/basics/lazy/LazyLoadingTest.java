package com.valunskii.labs.hibernate.basics.lazy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valunskii.labs.hibernate.basics.BaseTest;
import com.valunskii.labs.hibernate.basics.entity.Account;
import com.valunskii.labs.hibernate.basics.entity.Client;
import org.junit.Test;

import java.util.List;

@DatabaseSetup("/data.xml")
public class LazyLoadingTest extends BaseTest {

    @Test
    public void lazyCollection() { // пример от клиента к аккаунту
        Client client = session.get(Client.class, 100); // достаем клиента по id
        System.out.println("<- got client"); // достается Client без Accounts
        List<Account> accounts = client.getAccounts(); // ничего не происходит
        System.out.println("<- got accounts");
        accounts.size(); // вот здесь hibernate полезет вторым запросом за Accounts клиента
        System.out.println("<- got accounts size");
    }

    @Test
    public void lazyEntity() { // пример от аккаунта к клиенту
        Account account = session.get(Account.class, 10);
        System.out.println("<- got account");
        Client client = account.getClient();
        System.out.println("<- got client");
        client.getId();
        System.out.println("<- got client id");
    }

    @Test
    public void loadMethod() {
        Client client = session.load(Client.class, 100); // запроса в базу нет
        System.out.println("<- got client");
        client.getAge(); // только сейчас пошел запрос
        System.out.println("<- got age");
    }
}
