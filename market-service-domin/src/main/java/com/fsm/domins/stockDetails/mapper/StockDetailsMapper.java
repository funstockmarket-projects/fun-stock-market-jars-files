package com.fsm.domins.stockDetails.mapper;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.StockDetailsBO;
import com.fsm.domainsMapping.constantsBO.StockExchangeBO;
import com.fsm.domainsMapping.constantsBO.StockStatusBO;
import com.fsm.domins.holdings.constants.StockExchange;
import com.fsm.domins.stockDetails.constaunts.StockStatus;
import com.fsm.domins.stockDetails.models.StockDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockDetailsMapper {

    private static final Logger log = LoggerFactory.getLogger(StockDetailsMapper.class);

    /**
     * Converts StockDetailsBO (Business Object) to StockDetails (Entity)
     * @param bo the business object to convert
     * @return the mapped entity
     */
    public static StockDetails bOToStockDetails(StockDetailsBO bo) {
        if (bo == null) {
            throw new IllegalArgumentException("StockDetailsBO cannot be null");
        }

        return new StockDetails(
                bo.getStockUuid(),
                bo.getStockId(),
                bo.getStockSymbol(),
                bo.getStockName(),
                StockExchange.valueOf(bo.getExchangeName().getExchangeCode()) ,
                StockStatus.valueOf(bo.getStockStatus().getStatus()),
                bo.getUpdateOrModifiedDateTime()
        );
    }

    /**
     * Converts StockDetails (Entity) to StockDetailsBO (Business Object)
     * @param entity the entity to convert
     * @return the mapped business object
     */
    public static StockDetailsBO stockDetailsToBO(StockDetails entity) {
        if (entity == null) {
            throw new IllegalArgumentException("StockDetails cannot be null");
        }

        StockDetailsBO bo = new StockDetailsBO();
        bo.setStockUuid(entity.stockUuid());
        bo.setStockId(entity.stockId());
        bo.setStockSymbol(entity.stockSymbol());
        bo.setStockName(entity.stockName());
        bo.setExchangeName(StockExchangeBO.valueOf(entity.exchangeName().getExchangeCode()));
        bo.setStockStatus(StockStatusBO.valueOf(entity.stockStatus().getStatus()));
        bo.setUpdateOrModifiedDateTime(entity.updateOrModifiedDateTime());

        return bo;
    }
}
