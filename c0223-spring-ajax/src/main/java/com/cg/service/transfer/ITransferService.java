package com.cg.service.transfer;

import com.cg.model.Transfer;
import com.cg.model.dto.history.HistoryTransferResDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer,Long> {
    List<HistoryTransferResDTO> findAllTransfer();
}
