package com.cg.repository;

import com.cg.model.Transfer;
import com.cg.model.dto.history.HistoryTransferResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {



}
