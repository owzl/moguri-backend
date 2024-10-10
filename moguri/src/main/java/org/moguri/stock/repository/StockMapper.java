package org.moguri.stock.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.stock.domain.Stock;
import org.moguri.stock.param.StockBuyParam;
import org.moguri.stock.param.StockSellParam;

import java.util.List;

@Mapper
public interface StockMapper {

    List<Stock> findStockByKeyword(@Param("pageRequest") PageRequest pageRequest,
                                   @Param("keyword") String keyword);

    int getTotalCount(@Param("keyword") String keyword);

    void saveTrade(StockBuyParam param);

    void updateTrade(StockSellParam param);


}
