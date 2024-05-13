package com.shilaeva.dao.implementations;

import com.shilaeva.dao.BankAccountDao;
import com.shilaeva.entities.BankAccount;
import com.shilaeva.executors.Executor;
import com.shilaeva.utils.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountDaoImpl implements BankAccountDao {
    Executor executor;

    public BankAccountDaoImpl() {
        this.executor = new Executor();
    }

    public BankAccount getBankAccountById(long id) throws SQLException {
        return executor.execQuery(buildQueryGetBankAccountById(id), result -> {
            result.next();
            return new BankAccount(result.getLong(1), result.getLong(2), result.getBigDecimal(3));
        });
    }

    private PreparedStatement buildQueryGetBankAccountById(long id) throws SQLException {
        String sql = "select * from bank_accounts where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }

    public BankAccount getBankAccountByUserId(long userId) throws SQLException {
        return executor.execQuery(buildQueryGetBankAccountByUserId(userId), result -> {
            result.next();
            return new BankAccount(result.getLong(1), result.getLong(2), result.getBigDecimal(3));
        });
    }

    private PreparedStatement buildQueryGetBankAccountByUserId(long userId) throws SQLException {
        String sql = "select * from bank_accounts where user_id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);

        return preparedStatement;
    }

    public void insertBankAccount(BankAccount bankAccount) throws SQLException {
        executor.execUpdate(buildQueryInsertBankAccount(bankAccount.getUserId(), bankAccount.getAmount()));
    }

    private PreparedStatement buildQueryInsertBankAccount(long userId, BigDecimal amount) throws SQLException {
        String sql = "insert into bank_accounts (user_id, amount) values (?, ?)";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setBigDecimal(2, amount);

        return preparedStatement;
    }

    public void updateBankAccount(BankAccount bankAccount) throws SQLException {
        executor.execUpdate(buildQueryUpdateBankAccount(bankAccount.getAmount(), bankAccount.getId()));
    }

    private PreparedStatement buildQueryUpdateBankAccount(BigDecimal amount, long id) throws SQLException {
        String sql = "update bank_accounts set amount = ? where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setBigDecimal(1, amount);
        preparedStatement.setLong(2, id);

        return preparedStatement;
    }

    public void deleteBankAccount(long id) throws SQLException {
        executor.execUpdate(buildQueryDeleteBankAccount(id));
    }

    private PreparedStatement buildQueryDeleteBankAccount(long id) throws SQLException {
        String sql = "delete from bank_accounts where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }

    public void createTable() throws SQLException {
        executor.execUpdate(buildQueryCreateTable());
    }

    private PreparedStatement buildQueryCreateTable() throws SQLException {
        String sql = "create table if not exists bank_accounts (" +
                "id bigserial primary key, " +
                "user_id bigint not null, " +
                "amount numeric(19, 2) not null, " +
                "foreign key (user_id) references users (id) on delete cascade)";

        return ConnectionUtil.getConnection().prepareStatement(sql);
    }

    public void deleteTable() throws SQLException {
        executor.execUpdate(buildQueryDeleteTable());
    }

    private PreparedStatement buildQueryDeleteTable() throws SQLException {
        String sql = "drop table if exists bank_accounts";

        return ConnectionUtil.getConnection().prepareStatement(sql);
    }
}
