package org.moguri.stock.repository;

import org.apache.ibatis.annotations.Mapper;
import org.moguri.common.response.PageRequest;
import org.moguri.stock.domain.Stock;
import org.moguri.stock.param.StockBuyParam;
import org.moguri.stock.param.StockSellParam;

import java.util.List;

@Mapper
public interface StockMapper {
    List<Stock> findStockByKeyword(PageRequest pageRequest, String name);

    void saveTrade(StockBuyParam param);

    void updateTrade(StockSellParam param);

    int getTotalCount();
}
