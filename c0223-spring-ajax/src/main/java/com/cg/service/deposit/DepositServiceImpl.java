package com.cg.service.deposit;

import com.cg.model.Deposit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DepositServiceImpl implements IDepositService {
    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deposit save(Deposit deposit) {
        return null;
    }

    @Override
    public void delete(Deposit deposit) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
