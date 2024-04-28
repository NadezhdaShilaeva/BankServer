package com.shilaeva.dao.implementations;

import com.shilaeva.dao.BankAccountDao;
import com.shilaeva.entities.BankAccount;
import com.shilaeva.executors.Executor;

import java.sql.SQLException;

public class BankAccountDaoImpl implements BankAccountDao {
    Executor executor;

    public BankAccountDaoImpl() {
        this.executor = new Executor();
    }

    public BankAccount getBankAccountById(long id) throws SQLException {
        return executor.execQuery("select * from bank_accounts where id = " + id, result -> {
            result.next();
            return new BankAccount(result.getLong(1), result.getLong(2), result.getBigDecimal(3));
        });
    }

    public BankAccount getBankAccountByUserId(long userId) throws SQLException {
        return executor.execQuery("select * from bank_accounts where user_id = " + userId, result -> {
            result.next();
            return new BankAccount(result.getLong(1), result.getLong(2), result.getBigDecimal(3));
        });
    }

    public void insertBankAccount(BankAccount bankAccount) throws SQLException {
        executor.execUpdate("insert into bank_accounts (user_id, amount) values (" +
                bankAccount.getUserId() + ", " + bankAccount.getAmount() + ")");
    }

    public void updateBankAccount(BankAccount bankAccount) throws SQLException {
        executor.execUpdate("update bank_accounts set amount = " + bankAccount.getAmount()
                + " where id = " + bankAccount.getId());
    }

    public void deleteBankAccount(long id) throws SQLException {
        executor.execUpdate("delete from bank_accounts where id = " + id);
    }

    public void createTable() throws SQLException {
        executor.execUpdate(
                "create table if not exists bank_accounts (" +
                        "id bigserial primary key, " +
                        "user_id bigint not null, " +
                        "amount numeric(19, 2) not null, " +
                        "foreign key (user_id) references users (id) on delete cascade)");
    }

    public void deleteTable() throws SQLException {
        executor.execUpdate("drop table if exists bank_accounts");
    }
}
